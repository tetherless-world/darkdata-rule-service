package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try (InputStream is = this.ruleset.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            this.reasoner = new GenericRuleReasoner(Rule.parseRules(Rule.rulesParserFromReader(reader)));
            reasoner.bindSchema(datasource.getSchema());
            reasoner.setDerivationLogging(true);
        }
    }

    public GenericRuleReasoner getReasoner() {
        return reasoner;
    }

    @Override
    public List<CompatibilityAssertion> computeCompatibilities(CandidateWorkflow candidate) {
        OntModel m = candidate.getIndividual().getOntModel();
        InfModel ruleInf = ModelFactory.createInfModel(getReasoner(), m);
        ruleInf.add(datasource.getSchema());

        ruleInf.prepare();

        // TODO run these 5 computations in parallel
        computeStrongCompatibilityAssertions(ruleInf, candidate);
        computeSomeCompatibilityAssertions(ruleInf, candidate);
        computeSlightCompatibilityAssertions(ruleInf, candidate);
        computeIndifferentCompatibilityAssertions(ruleInf, candidate);
        computeNegativeCompatibilityAssertions(ruleInf, candidate);

        return candidate.getCompatibilityAssertions();

    }

    public void computeStrongCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.strongCompatibilityFor, CompatibilityValue.STRONG);
    }

    public void computeSomeCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.someCompatibilityFor, CompatibilityValue.SOME);
    }

    public void computeSlightCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.slightCompatibilityFor, CompatibilityValue.SLIGHT);
    }

    public void computeIndifferentCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.indifferentCompatibilityFor, CompatibilityValue.INDIFFERENT);
    }

    public void computeNegativeCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.negativeCompatibilityFor, CompatibilityValue.NEGATIVE);
    }

    private void computeAssertions(InfModel ruleInf,
                                   CandidateWorkflow candidate,
                                   OntProperty compatibilityProperty,
                                   CompatibilityValue compatibilityValue) {

        Optional<PhysicalFeature> feature = candidate.getFeature();
        Optional<G4Service> service = candidate.getService();

        OntModel m = candidate.getIndividual().getOntModel();

        if (!feature.isPresent() || !service.isPresent()) { return; }

        Stream.of(feature.get())
                .flatMap(f -> ruleInf.listObjectsOfProperty(compatibilityProperty).toList().stream())
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .filter(service.get().getBestForCharacteristics()::contains)
                .distinct()
                .map(f -> m.createIndividual(DarkData.CompatibilityAssertion))
                .map(CompatibilityAssertion::new)
                .peek(a -> a.setValue(compatibilityValue))
                .peek(candidate::addCompatibilityAssertion)
                .collect(Collectors.toList());
    }

}
