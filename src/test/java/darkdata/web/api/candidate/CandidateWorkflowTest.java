package darkdata.web.api.candidate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import darkdata.DarkDataApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class CandidateWorkflowTest {

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/candidateWorkflow.json")
    private Resource candidate;

    @Test
    public void testCanSerialize() {
        Assert.assertTrue(mapper.canSerialize(CandidateWorkflow.class));
    }

    @Test
    public void testSerializeCandidateWorkflow() throws IOException {
        CandidateWorkflow candidateWorkflow = CandidateWorkflowTestHarness.createCandidateWorkflow();
        Assert.assertNotNull(candidateWorkflow);
        ObjectNode serialized_pojo = mapper.valueToTree(candidateWorkflow);
        JsonNode expected = mapper.readTree(candidate.getInputStream());
        Assert.assertEquals("expect does not equal serialized pojo", expected, serialized_pojo);
    }

    @Test
    public void testDeserializeCandidateWorkflow() throws IOException {
        CandidateWorkflow result = mapper.readValue(candidate.getInputStream(), CandidateWorkflow.class);
        Assert.assertNotNull(result);
    }
}
