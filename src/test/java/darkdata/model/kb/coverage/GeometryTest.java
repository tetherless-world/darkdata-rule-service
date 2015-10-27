package darkdata.model.kb.coverage;

import darkdata.DarkDataApplication;
import darkdata.model.ontology.GeoSparql;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author szednik
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class GeometryTest {

    @Test
    public void testCreateGeometry() {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual("urn:geometry/test", GeoSparql.Geometry);
        Geometry geometry = new Geometry(g);
        Assert.assertNotNull(geometry);
    }

    @Test
    public void testGetStartDateTime() {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual("urn:geometry/test", GeoSparql.Geometry);
        Geometry geometry = new Geometry(g);
        Assert.assertNotNull(geometry);

        LocalDateTime date = LocalDateTime.parse("2015-10-15T00:00:00Z", DateTimeFormatter.ISO_DATE_TIME);
        geometry.setStartDateTime(date);
        Optional<String> result = geometry.getStartDateTime();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(date.format(DateTimeFormatter.ISO_DATE_TIME), result.get());
    }

    @Test
    public void testSetStartDateTime_String() {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual("urn:geometry/test", GeoSparql.Geometry);
        Geometry geometry = new Geometry(g);
        Assert.assertNotNull(geometry);

        geometry.setStartDateTime("2015-10-15T00:00:00Z");
        Optional<String> result = geometry.getStartDateTime();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("2015-10-15T00:00:00", result.get());
    }

    @Test
    public void testGetEndDateTime() {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual("urn:geometry/test", GeoSparql.Geometry);
        Geometry geometry = new Geometry(g);
        Assert.assertNotNull(geometry);

        LocalDateTime date = LocalDateTime.parse("2015-10-15T00:00:00Z", DateTimeFormatter.ISO_DATE_TIME);
        geometry.setEndDateTime(date);
        Optional<String> result = geometry.getEndDateTime();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(date.format(DateTimeFormatter.ISO_DATE_TIME), result.get());
    }

    @Test
    public void testSetEndDateTime_String() {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual("urn:geometry/test", GeoSparql.Geometry);
        Geometry geometry = new Geometry(g);
        Assert.assertNotNull(geometry);

        geometry.setEndDateTime("2015-10-15T00:00:00Z");
        Optional<String> result = geometry.getEndDateTime();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("2015-10-15T00:00:00", result.get());
    }
}
