package darkdata.model.kb.api.web.event.eonet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import darkdata.DarkDataApplication;
import darkdata.model.api.web.event.eonet.Event;
import darkdata.model.api.web.event.eonet.EventCategory;
import darkdata.model.api.web.event.eonet.EventGeometry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        Event event = createTestEvent();
        Assert.assertNotNull(event);
        //System.out.println(mapper.writeValueAsString(event));

        ObjectNode serialized_pojo = mapper.valueToTree(event);
        JsonNode expected = mapper.readTree(eonet_224.getInputStream());
        Assert.assertEquals("expect does not equal serialized pojo", expected, serialized_pojo);
    }

    private Event createTestEvent() {
        String id = "EONET_224";
        String title = "Hurricane Olaf";
        String link = "http://eonet.sci.gsfc.nasa.gov/api/v1/events/EONET_224";
        String description = "";

        String category_domain = "Severe Storms";
        String category_text = "Severe Storms";

        String geometry_type = "Point";
        String geometry_date = "2015-10-15T00:00:00Z";
        List<List<Double>> geometry_coordinates = Collections.singletonList(Arrays.asList(-117.10d, 9.90d));

        EventCategory category = new EventCategory(category_domain, category_text);
        EventGeometry geometry = new EventGeometry(geometry_type, geometry_date, geometry_coordinates);

        List<EventCategory> categoryList = Collections.singletonList(category);
        List<EventGeometry> geometryList = Collections.singletonList(geometry);

        return new Event(id, title, link, description,categoryList, geometryList);
    }
}
