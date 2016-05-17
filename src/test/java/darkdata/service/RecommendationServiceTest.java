package darkdata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import darkdata.DarkDataApplication;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

/**
 * @author szednik
 */

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecommendationServiceTest {

    @Autowired
    private RecommendationService service;

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/request.json")
    private Resource request;

    @Value("classpath:json/request.only-event-categories.json")
    private Resource eventTypeOnlyRequest;

    @Value("classpath:json/request.only-event.json")
    private Resource eventOnlyRequest;

    @Value("classpath:json/request.event-categories-and-vars.json")
    private Resource eventCategoriesAndVariablesRequest;

    @Value("classpath:json/EONET_200.json")
    private Resource eonet200;

    @Value("classpath:json/EONET_397.json")
    private Resource eonet397;

    @Test
    public void testGetRecommendation() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(request.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetRecommendation_eventTypeOnly() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(eventTypeOnlyRequest.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetRecommendation_eventOnly() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(eventOnlyRequest.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetRecommendation_eventCategoriesAndVariables() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(eventCategoriesAndVariablesRequest.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
    }

    @Test
    public void testEONET200() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(eonet200.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
        mapper.writeValue(System.out, response);
    }

    @Test
    public void testEONET397() throws IOException {
        RecommendationRequest requestObj = mapper.readValue(IOUtils.toString(eonet397.getInputStream()), RecommendationRequest.class);
        Assert.assertNotNull(requestObj);
        RecommendationResponse response = service.getRecommendation(requestObj);
        Assert.assertNotNull(response);
        mapper.writeValue(System.out, response);
    }
}
