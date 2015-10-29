package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowScore;
import darkdata.model.kb.candidate.CandidateWorkflowTestHarness;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityAssertionTestHarness;
import darkdata.model.kb.compatibility.CompatibilityValue;
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

    @Test
    public void testScore() {

        CandidateWorkflow candidate = CandidateWorkflowTestHarness.createCandidateWorkflow("urn:foo");
        Assert.assertNotNull(candidate);

        CompatibilityAssertion assertion1 = CompatibilityAssertionTestHarness.createCompatibilityAssertion("urn:assertion1");
        assertion1.setValue(CompatibilityValue.SLIGHT);
        assertion1.setConfidence(0.42d);

        CompatibilityAssertion assertion2 = CompatibilityAssertionTestHarness.createCompatibilityAssertion("urn:assertion2");
        assertion2.setValue(CompatibilityValue.SOME);
        assertion2.setConfidence(0.63d);

        CompatibilityAssertion assertion3 = CompatibilityAssertionTestHarness.createCompatibilityAssertion("urn:assertion3");
        assertion3.setValue(CompatibilityValue.SOME);
        assertion3.setConfidence(0.47d);

        CompatibilityAssertion assertion4 = CompatibilityAssertionTestHarness.createCompatibilityAssertion("urn:assertion4");
        assertion4.setValue(CompatibilityValue.SOME);
        assertion4.setConfidence(0.59d);

        candidate.addCompatibilityAssertion(assertion1);
        candidate.addCompatibilityAssertion(assertion2);
        candidate.addCompatibilityAssertion(assertion3);
        candidate.addCompatibilityAssertion(assertion4);

        CandidateWorkflowScore score = scoringService.score(candidate);
        Assert.assertNotNull(score);
        System.out.println("score: "+score.getScore());
    }
}
