package darkdata.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Created by anirudhprabhu on 11/3/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration

public class RecommendationResponseTest {

    @Autowired
    private ObjectMapper mapper;



    @Value("classpath:json/response.json")
    private Resource response;

    @Test
    public void testCanSerialize() {
        Assert.assertTrue(mapper.canSerialize(RecommendationResponse.class));
    }

    @Test
    public void testDeserializeRecommendationResponse() throws IOException {
        RecommendationResponse recommendationResponse = mapper.readValue(response.getInputStream(), RecommendationResponse.class);
        Assert.assertNotNull(recommendationResponse);
    }

}
