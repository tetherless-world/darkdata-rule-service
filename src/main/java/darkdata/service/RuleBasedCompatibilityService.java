package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author szednik
 */

@Service
public class RuleBasedCompatibilityService implements CandidateWorkflowCompatibilityService {

    @Autowired
    private RuleBasedReasoningService ruleBasedReasoningService;

    @Override
    public List<CompatibilityAssertion> computeCompatibilities(CandidateWorkflow candidate) {
        OntModel m = candidate.getIndividual().getOntModel();
        InfModel ruleInf = ruleBasedReasoningService.reason(m);
        copyAssertionsToCandidateModel(ruleInf, candidate);
        return candidate.getCompatibilityAssertions();
    }

    private void copyAssertionsToCandidateModel(InfModel ruleInf, CandidateWorkflow candidate) {

//        Optional<PhysicalFeature> feature = candidate.getFeature();
//        Optional<G4Service> service = candidate.getService();
//        if(!feature.isPresent() || !service.isPresent()) { return; }

        OntModel m = candidate.getIndividual().getOntModel();

        ruleInf.listObjectsOfProperty(candidate.getIndividual(), DarkData.compatibilityAssertion)
                .toList()
                .stream()
                .map(RDFNode::asResource)
                .peek(a -> candidate.getIndividual().addProperty(DarkData.compatibilityAssertion, a))
                .forEach(n -> m.add(n.listProperties().toList()));
    }

}
