package darkdata.model.kb;

import darkdata.DarkDataApplication;
import darkdata.model.kb.coverage.Geometry;
import darkdata.model.ontology.DarkData;
import darkdata.repository.EventRepository;
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
public class PhenomenaTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testGetGeometries() {
        Phenomena event = eventRepository.createEvent("urn:phenomena/testGetGeometries", DarkData.Hurricane).get();
        Assert.assertNotNull(event);

        Geometry geometry = event.createGeometry("urn:phenomena/testGetGeometries/geometry").get();
        Assert.assertNotNull(geometry);

        List<Geometry> result = event.getGeometries();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(geometry, result.get(0));
    }
}
