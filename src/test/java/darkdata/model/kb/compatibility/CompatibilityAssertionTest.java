package darkdata.model.kb.compatibility;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.repository.CompatibilityAssertionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class CompatibilityAssertionTest {

    @Autowired
    private CandidateWorkflowRepository candidateRepository;

    @Autowired
    private CompatibilityAssertionRepository assertionRepository;

    @Test
    public void testGetValue() {

        CompatibilityAssertion assertion = assertionRepository.createCompatibilityAssertion("urn:assertion/testGetValue").get();
        Assert.assertNotNull(assertion);

        assertion.setValue(CompatibilityValue.SLIGHT);
        Optional<CompatibilityValue> result = assertion.getValue();
        Assert.assertTrue(result.isPresent());
        Assert.assertNotNull(result.get().getIndividual());
        Assert.assertEquals(CompatibilityValue.SLIGHT, result.get());
    }

    @Test
    public void testGetConfidence() {

        CompatibilityAssertion assertion = assertionRepository.createCompatibilityAssertion("urn:/assertion/testGetConfidence").get();
        Assert.assertNotNull(assertion);

        assertion.setConfidence(0.42D);
        Optional<Double> result = assertion.getConfidence();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(Double.valueOf(0.42D), result.get());
    }

    @Test
    public void testGetCandidate() {

        CandidateWorkflow candidate = candidateRepository.createCandidateWorkflow("urn:candidate/testGetCandidate").get();
        Assert.assertNotNull(candidate);

        CompatibilityAssertion assertion = candidate.createCompatibilityAssertion("urn:assertion/testGetCandidate").get();
        Assert.assertNotNull(assertion);

        Optional<CandidateWorkflow> result = assertion.getCandidate();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(candidate, result.get());
    }
}
