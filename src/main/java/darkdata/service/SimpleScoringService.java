package darkdata.service;

import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CompatibilityAssertionRepository;
import darkdata.repository.CompatibilityValueRepository;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Service
public class SimpleScoringService implements ScoringService<Resource,Resource> {

    @javax.annotation.Resource(name = "simpleWeights")
    private Properties weights;

    @Autowired
    private CompatibilityAssertionRepository compatibilityAssertionRepository;

    @Autowired
    private CompatibilityValueRepository compatibilityValueRepository;

    private static final Logger logger = LoggerFactory.getLogger(SimpleScoringService.class);

    public Resource score(Model m, Resource candidate) {
        Double score = computeCandidateScore(m, candidate);
        m.addLiteral(candidate, DarkData.candidateScore, score.doubleValue());
        return candidate;
    }

    protected Double computeNaMax(Model m, List<Resource> assertions) {
        return groupAssertions(m, assertions).entrySet().stream()
                .map(Map.Entry::getValue)
                .mapToDouble(List::size)
                .max().orElse(0d);
    }

    protected Double computeConfidenceMax(Model m, List<Resource> assertions) {
        return summarizeConfidence(m, assertions).getMax();
    }

    protected Double normalizeScore(Double rawScore) {
        return Math.log10(rawScore);
    }

    protected Double computeRawScoreForCompatibilityValue(Model m, Resource compatibilityValue, DoubleSummaryStatistics confidenceStats, Double naMax, Double maxConfidence) {
        double numAssertions = confidenceStats.getCount();
        double sumConfidence = confidenceStats.getSum();
        double weight = getWeight(m, compatibilityValue);
        return (sumConfidence / maxConfidence * weight) * (numAssertions / naMax);
    }

    private Double computeCandidateScore(Model m, Resource candidate) {
        List<Resource> assertions = compatibilityAssertionRepository.getCompatibilityAssertionsForCandidate(m, candidate);
        return generateScore(m, assertions);
    }

    private Double generateScore(Model m, List<Resource> assertions) {
        Double naMax = computeNaMax(m, assertions);
        Double confidenceMax = computeConfidenceMax(m, assertions);

        Map<Resource, DoubleSummaryStatistics> statisticsMap = getGroupedCompatibilitySummaries(m, assertions);

        double rawScore =  statisticsMap.entrySet().stream()
                .mapToDouble(e -> computeRawScoreForCompatibilityValue(m, e.getKey(), e.getValue(), naMax, confidenceMax))
                .sum();

        return normalizeScore(rawScore);
    }

    private Map<Resource, DoubleSummaryStatistics> getGroupedCompatibilitySummaries(Model m, List<Resource> assertions) {
        return groupAssertions(m, assertions).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> summarizeConfidence(m, e.getValue())));
    }

    private DoubleSummaryStatistics summarizeConfidence(Model m, List<Resource> assertions) {
        return assertions.stream()
                .map(a -> CompatibilityAssertion.getConfidence(m, a))
                .collect(Collectors.summarizingDouble(a -> a));
    }

    private Map<Resource, List<Resource>> groupAssertions(Model m, List<Resource> assertions) {
        return assertions.stream()
                .collect(Collectors.groupingBy(a -> compatibilityValueRepository.getCompatibilityValue(m, a)));
    }

    private Double getWeight(Model m, Resource compatibilityValue) {
        String weightIdentifier = CompatibilityValue.getIdentifier(m, compatibilityValue);
        return Double.valueOf(weights.getOrDefault(weightIdentifier, 0).toString());
    }

}
