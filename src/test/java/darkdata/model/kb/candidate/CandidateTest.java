package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.repository.CandidateWorkflowRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CandidateWorkflowRepository repository;

    @Test
    public void testGetCompatibilityAssertions() {

        CandidateWorkflow candidate = repository.createCandidateWorkflow("urn:candidate/testGetCompatibilityAssertions").get();
        Assert.assertNotNull(candidate);

        CompatibilityAssertion assertion1 = candidate.createCompatibilityAssertion("urn:assertion1").get();
        Assert.assertNotNull(assertion1);

        CompatibilityAssertion assertion2 = candidate.createCompatibilityAssertion("urn:assertion2").get();
        Assert.assertNotNull(assertion2);

        List<CompatibilityAssertion> results = candidate.getCompatibilityAssertions();

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(assertion1));
        Assert.assertTrue(results.contains(assertion2));
    }
}
