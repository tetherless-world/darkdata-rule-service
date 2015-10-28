package darkdata.web.api.event.eonet;

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

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class EventTest {

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/EONET_224.json")
    private Resource eonet_224;

    @Test
    public void testCanSerialize() {
        Assert.assertTrue(mapper.canSerialize(Event.class));
    }

    @Test
    public void testSerializeEvent() throws Exception {
        Event event = EventTestHarness.createEvent_EONET_224();
        Assert.assertNotNull(event);
        ObjectNode serialized_pojo = mapper.valueToTree(event);
        JsonNode expected = mapper.readTree(eonet_224.getInputStream());
        Assert.assertEquals("expect does not equal serialized pojo", expected, serialized_pojo);
    }

}
