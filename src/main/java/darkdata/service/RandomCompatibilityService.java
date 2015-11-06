package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.repository.CompatibilityAssertionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author szednik
 */

@Service
public class RandomCompatibilityService implements CompatibilityService<CompatibilityAssertion, CandidateWorkflow> {

    @Autowired
    private CompatibilityAssertionRepository compatibilityAssertionRepository;

    /**
     * for each candidate workflow generate 5 random compatibility assertions
     * @param candidate candidate workflow
     * @return List of CompatibilityAssertion objects
     */
    @Override
    public List<CompatibilityAssertion> computeCompatibilities(CandidateWorkflow candidate) {

        List<CompatibilityAssertion> assertions = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            String uri = "urn:compatibility-assertion/" + UUID.randomUUID().toString();

            CompatibilityValue compatibilityValue = getRandomCompatibilityValue();
            double confidence = getRandomCompatibilityConfidence();

            Optional<CompatibilityAssertion> assertion = compatibilityAssertionRepository.createCompatibilityAssertion(uri);
            assertion.ifPresent(a -> a.setCandidate(candidate));
            assertion.ifPresent(a -> a.setValue(compatibilityValue));
            assertion.ifPresent(a -> a.setConfidence(confidence));
            assertion.ifPresent(assertions::add);
        }

        return assertions;
    }

    private CompatibilityValue getRandomCompatibilityValue() {
        Random random = new Random();
        int r = random.nextInt(5);

        switch (r) {
            case 0:
                return CompatibilityValue.NEGATIVE;
            case 1:
                return CompatibilityValue.INDIFFERENT;
            case 2:
                return CompatibilityValue.SLIGHT;
            case 3:
                return CompatibilityValue.SOME;
            case 4:
                return CompatibilityValue.STRONG;
            default:
                return CompatibilityValue.INDIFFERENT;
        }
    }

    private double getRandomCompatibilityConfidence() {
        Random random = new Random();
        return random.nextDouble();
    }
}
