package darkdata.service;

import darkdata.model.ontology.DarkData;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Service
public class SimpleScoringService {

    @javax.annotation.Resource(name = "simpleWeights")
    private Properties weights;

    private static final Logger logger = LoggerFactory.getLogger(SimpleScoringService.class);

    public Resource score(Model m, Resource candidate) {
        Double score = computeCandidateScore(m, candidate);
        m.addLiteral(candidate, DarkData.candidateScore, score.doubleValue());
        return candidate;
    }

    private Double computeCandidateScore(Model m, Resource candidate) {
        return generateScore(m, getCompatibilityAssertionsForCandidate(m, candidate));
    }

    private Double generateScore(Model m, List<Resource> assertions) {
        Map<Resource, DoubleSummaryStatistics> statisticsMap = getGroupedCompatibilitySummaries(m, assertions);
        return statisticsMap.entrySet().stream()
                .map(e -> (getWeight(m, e.getKey()) * e.getValue().getSum()) / Math.sqrt(e.getValue().getCount()))
                .collect(Collectors.summingDouble(v -> v));
    }

    private Map<Resource, DoubleSummaryStatistics> getGroupedCompatibilitySummaries(Model m, List<Resource> assertions) {
        return groupAssertions(m, assertions).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> summarizeConfidence(m, e.getValue())));
    }

    private DoubleSummaryStatistics summarizeConfidence(Model m, List<Resource> assertions) {
        return assertions.stream()
                .map(a -> getConfidence(m, a))
                .collect(Collectors.summarizingDouble(a -> a));
    }

    private Double getConfidence(Model m, Resource assertion) {
        return m.listObjectsOfProperty(assertion, DarkData.assertionConfidence)
                .toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble)
                .findAny().orElse(0.5D);
    }

    private Map<Resource, List<Resource>> groupAssertions(Model m, List<Resource> assertions) {
        return assertions.stream()
                .collect(Collectors.groupingBy(a -> getCompatibilityValue(m, a)));
    }

    private Resource getCompatibilityValue(Model m, Resource assertion) {
        return m.listObjectsOfProperty(assertion, DarkData.compatibilityValue)
                .toList().stream()
                .map(RDFNode::asResource)
                .findAny().orElse(DarkData.indifferent_compatibility);
    }

    private Double getWeight(Model m, Resource compatibilityValue) {
        String weightIdentifier = getCompatibilityValueIdentifier(m, compatibilityValue);
        return Double.valueOf(weights.getOrDefault(weightIdentifier, 0).toString());
    }

    private String getCompatibilityValueIdentifier(Model m, Resource compatibilityValue) {
        return m.listObjectsOfProperty(compatibilityValue, DCTerms.identifier)
                .toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getString)
                .findAny().orElse("DEFAULT");
    }

    private List<Resource> getCompatibilityAssertionsForCandidate(Model m, Resource candidate) {
        return m.listObjectsOfProperty(candidate, DarkData.compatibilityAssertion)
                .toList().stream()
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }
}
