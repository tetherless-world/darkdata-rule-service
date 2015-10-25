package darkdata.model.api.web.candidate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import darkdata.DarkDataApplication;
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
public class CandidateWorkflowTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCanSerialize() {
        Assert.assertTrue(mapper.canSerialize(CandidateWorkflow.class));
    }

    @Test
    public void testSerializeCandidateWorkflow() throws JsonProcessingException {
        CandidateWorkflow candidateWorkflow = CandidateWorkflowTestHarness.createCandidateWorkflow();
        Assert.assertNotNull(candidateWorkflow);
        System.out.println(mapper.writeValueAsString(candidateWorkflow));
    }
}
