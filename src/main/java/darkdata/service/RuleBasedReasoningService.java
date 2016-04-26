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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author szednik
 */

public class RuleBasedReasoningService implements ReasoningService {

    private List<Resource> rulesets;

    @Autowired
    private DarkDataDatasource datasource;

    private GenericRuleReasoner reasoner;

    public RuleBasedReasoningService(List<Resource> rulesets) {
        this.rulesets = rulesets;
    }

    @PostConstruct
    public void init() throws IOException {

        List<Rule> rules = new ArrayList<>();

        for(Resource ruleset: this.rulesets) {
            try (InputStream is = ruleset.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                List<Rule> newRules = Rule.parseRules(Rule.rulesParserFromReader(reader));
                rules.addAll(newRules);
            }
        }

        this.reasoner = new GenericRuleReasoner(rules);
        reasoner.bindSchema(datasource.getSchema());
        reasoner.setDerivationLogging(true);
    }

    @Override
    public InfModel reason(OntModel m) {
        InfModel ruleInf = ModelFactory.createInfModel(reasoner, m);
        ruleInf.add(datasource.getSchema());
        ruleInf.prepare();
        return ruleInf;
    }
}
