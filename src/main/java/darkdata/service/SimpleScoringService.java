package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowScore;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Service
public class SimpleScoringService implements CandidateWorkflowScoringService {

    @Resource(name = "simpleWeights")
    private Properties weights;

    @Override
    public CandidateWorkflowScore score(CandidateWorkflow candidate) {
        List<CompatibilityAssertion> assertions = candidate.getCompatibilityAssertions();
        Double score = generateScore(assertions);
        String id = UUID.randomUUID().toString();
        return new CandidateWorkflowScore(id, candidate, score);
    }

    private Double generateScore(List<CompatibilityAssertion> assertions) {
        Map<CompatibilityValue, DoubleSummaryStatistics> statisticsMap = getGroupedCompatibilitySummaries(assertions);
        return statisticsMap.entrySet().stream()
                .map(e -> getWeight(e.getKey()) * e.getValue().getAverage())
                .collect(Collectors.summingDouble(v -> v));
    }

    private Map<CompatibilityValue, List<CompatibilityAssertion>> groupAssertions(List<CompatibilityAssertion> assertions) {
        return assertions.stream()
                .collect(Collectors.groupingBy(a -> a.getValue().get()));
    }

    private Map<CompatibilityValue, DoubleSummaryStatistics> getGroupedCompatibilitySummaries(List<CompatibilityAssertion> assertions) {
        return groupAssertions(assertions).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(CompatibilityAssertion::getConfidence)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.summarizingDouble(a -> a))));
    }

    private Double getWeight(CompatibilityValue value) {
        String weightIdentifier = value.getIdentifier().orElse("DEFAULT");
        return Double.valueOf(weights.getOrDefault(weightIdentifier, 0).toString());
    }
}
