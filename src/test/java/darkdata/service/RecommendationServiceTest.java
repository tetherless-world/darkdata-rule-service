package darkdata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import darkdata.DarkDataApplication;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import darkdata.web.api.candidate.CandidateWorkflow;
import org.apache.commons.io.IOUtils;
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
public class RecommendationServiceTest {

    @Autowired
    private RecommendationService service;

    @Autowired
    private CandidateWorkflowRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/request.json")
    private Resource request;

    @Test
    public void testTransform() {
        darkdata.model.kb.candidate.CandidateWorkflow c = repository.createCandidateWorkflow("urn:candidate/testTransform").get();
        CandidateWorkflow result = service.transform(c);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetRecommendation() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(request.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
    }
}