package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */

@Service
public class RuleBasedCompatibilityService implements CandidateWorkflowCompatibilityService {

    @Value("classpath:rules/some.rules")
    private Resource ruleset;

    @Autowired
    private DarkDataDatasource datasource;

    private GenericRuleReasoner reasoner;

    @PostConstruct
    public void init() throws IOException {
        try(InputStream is = this.ruleset.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            this.reasoner = new GenericRuleReasoner(Rule.parseRules(Rule.rulesParserFromReader(reader)));
            reasoner.bindSchema(datasource.getSchema());
            reasoner.setDerivationLogging(true);
        }
    }

    public GenericRuleReasoner getReasoner() {
        return reasoner;
    }

    public Model getDeductionsModel(OntModel m) {
        InfModel inf = ModelFactory.createInfModel(reasoner, m);
        inf.prepare();
        return inf.getDeductionsModel();
    }

    @Override
    public List<CompatibilityAssertion> computeCompatibilities(CandidateWorkflow candidate) {
        OntModel m = candidate.getIndividual().getOntModel();
        Model deductions = getDeductionsModel(m);

        List<PhysicalFeature> features = candidate.getCandidatePhysicalFeatures();
        G4Service service = candidate.getService().get();

        return null;
    }

    public Optional<CompatibilityAssertion> computeCompatibility(@NotNull Model m, @NotNull PhysicalFeature feature, @NotNull G4Service service) {

        List<org.apache.jena.rdf.model.Resource> serviceBestForCharacteristics = service.getBestForCharacteristics();

        serviceBestForCharacteristics.stream()
                .filter(c -> feature.getIndividual().hasProperty(DarkData.strongCompatibilityFor,c))
                .forEach(c -> System.out.println(c.getURI()+" has strong compatibility with"+feature.getIndividual().getURI()));

        // TODO ...

        return Optional.empty();
    }

}
