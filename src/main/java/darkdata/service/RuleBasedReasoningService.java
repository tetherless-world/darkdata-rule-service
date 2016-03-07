package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author szednik
 */

public class RuleBasedReasoningService implements ReasoningService {

    private Resource ruleset;

    @Autowired
    private DarkDataDatasource datasource;

    private GenericRuleReasoner reasoner;

    public RuleBasedReasoningService(Resource ruleset) {
        this.ruleset = ruleset;
    }

    @PostConstruct
    public void init() throws IOException {
        try (InputStream is = this.ruleset.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            this.reasoner = new GenericRuleReasoner(Rule.parseRules(Rule.rulesParserFromReader(reader)));
            reasoner.bindSchema(datasource.getSchema());
            reasoner.setDerivationLogging(true);
        }
    }

    @Override
    public InfModel reason(OntModel m) {
        InfModel ruleInf = ModelFactory.createInfModel(reasoner, m);
        ruleInf.add(datasource.getSchema());
        ruleInf.prepare();
        return ruleInf;
    }
}
