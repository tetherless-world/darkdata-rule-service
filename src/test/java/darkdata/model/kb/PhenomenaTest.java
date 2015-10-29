package darkdata.model.kb;

import darkdata.DarkDataApplication;
import darkdata.model.kb.coverage.Geometry;
import darkdata.model.kb.coverage.GeometryTestHarness;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void testGetGeometries() {
        Geometry geometry = GeometryTestHarness.createGeometry("urn:phenomena/testGetGeometries/geometry");
        Assert.assertNotNull(geometry);

        Phenomena phenomena = PhenomenaTestHarness.createPhenomena("urn:phenomena/testGetGeometries");
        Assert.assertNotNull(phenomena);

        phenomena.addGeometry(geometry);
        List<Geometry> result = phenomena.getGeometries();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(geometry, result.get(0));
    }
}
