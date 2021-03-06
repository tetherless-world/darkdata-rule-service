package darkdata.web.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import darkdata.DarkDataApplication;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.datavariable.DataVariableTestHarness;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventTestHarness;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author anirudhprabhu
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class RecommendationRequestTest {

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/request.json")
    private Resource request;

    @Test
    public void testCanSerialize() {
        Assert.assertTrue(mapper.canSerialize(RecommendationRequest.class));
    }

    private List<DataVariable> createTestDataVariableList() {
        DataVariable d1 = DataVariableTestHarness.createVariable_MYD08_D3_6_Cirrus_Reflectance_Mean();
        DataVariable d2 = DataVariableTestHarness.createVariable_MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean();
        return Arrays.asList(d1, d2);
    }

    @Test
    public void testSerializeRecommendationRequest() throws IOException {
        Event event = EventTestHarness.createEvent_EONET_224();
        List<DataVariable> variables = createTestDataVariableList();
        RecommendationRequest recommendationRequest = new RecommendationRequest(event, variables);
        Assert.assertNotNull(recommendationRequest);
        ObjectNode serialized_pojo = mapper.valueToTree(recommendationRequest);
        JsonNode expected = mapper.readTree(request.getInputStream());
        Assert.assertEquals("expect does not equal serialized pojo", expected, serialized_pojo);
    }

    @Test
    public void testDeserializeRecommendationRequest() throws IOException {
        RecommendationRequest recommendationRequest = mapper.readValue(request.getInputStream(), RecommendationRequest.class);
        Assert.assertNotNull(recommendationRequest);
    }

}
