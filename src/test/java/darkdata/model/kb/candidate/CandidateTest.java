package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityAssertionTestHarness;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author szednik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class CandidateTest {

    @Test
    public void testGetCompatibilityAssertions() {

        CandidateWorkflow candidate = CandidateWorkflowTestHarness.createCandidateWorkflow("urn:candidate/testGetCompatibilityAssertions");
        CompatibilityAssertion assertion1 = CompatibilityAssertionTestHarness.createCompatibilityAssertion("urn:assertion1");
        CompatibilityAssertion assertion2 = CompatibilityAssertionTestHarness.createCompatibilityAssertion("urn:assertion2");

        candidate.addCompatibilityAssertion(assertion1);
        candidate.addCompatibilityAssertion(assertion2);

        List<CompatibilityAssertion> results = candidate.getCompatibilityAssertions();

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(assertion1));
        Assert.assertTrue(results.contains(assertion2));
    }
}
