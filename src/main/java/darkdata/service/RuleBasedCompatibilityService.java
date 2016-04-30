package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.RDFNode;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author szednik
 */

@Service
public class RuleBasedCompatibilityService {

    private static final Logger logger = LoggerFactory.getLogger(RuleBasedCompatibilityService.class);

    public List<CompatibilityAssertion> computeCompatibilities(final InfModel ruleInf, CandidateWorkflow candidate) {
//        StopWatch watch = new Slf4JStopWatch("RuleBasedCompatibilityService::computeCompatibilities");
        final OntModel m = candidate.getIndividual().getOntModel();
        ruleInf.getDeductionsModel()
                .listObjectsOfProperty(candidate.getIndividual(), DarkData.compatibilityAssertion)
                .toList()
                .stream()
                .map(RDFNode::asResource)
                .peek(a -> candidate.getIndividual().addProperty(DarkData.compatibilityAssertion, a))
                .forEach(n -> m.add(n.listProperties().toList()));
//        watch.stop();
        return candidate.getCompatibilityAssertions();
    }

}
