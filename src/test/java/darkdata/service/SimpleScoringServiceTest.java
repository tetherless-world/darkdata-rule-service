package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowScore;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.repository.CandidateWorkflowRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author szednik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class SimpleScoringServiceTest {

    @Autowired
    private SimpleScoringService scoringService;

    @Autowired
    private CandidateWorkflowRepository candidateRepository;

    @Test
    public void testScore() {

        CandidateWorkflow candidate = candidateRepository.createCandidateWorkflow("urn:candidate/testScore").get();
        Assert.assertNotNull(candidate);

        CompatibilityAssertion assertion1 = candidate.createCompatibilityAssertion("urn:assertion1").get();
        assertion1.setValue(CompatibilityValue.SLIGHT);
        assertion1.setConfidence(0.42d);

        CompatibilityAssertion assertion2 = candidate.createCompatibilityAssertion("urn:assertion2").get();
        assertion2.setValue(CompatibilityValue.SOME);
        assertion2.setConfidence(0.63d);

        CompatibilityAssertion assertion3 = candidate.createCompatibilityAssertion("urn:assertion3").get();
        assertion3.setValue(CompatibilityValue.SOME);
        assertion3.setConfidence(0.47d);

        CompatibilityAssertion assertion4 = candidate.createCompatibilityAssertion("urn:assertion4").get();
        assertion4.setValue(CompatibilityValue.SOME);
        assertion4.setConfidence(0.59d);

        CandidateWorkflowScore score = scoringService.score(candidate);
        Assert.assertNotNull(score);
        Assert.assertEquals(Double.valueOf(2.53d), score.getScore());
    }
}
