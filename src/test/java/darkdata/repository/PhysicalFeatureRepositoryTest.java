package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class PhysicalFeatureRepositoryTest {

    @Autowired
    private PhysicalFeatureRepository repository;

    @Test
    public void testListSubclasses() {
        List<OntClass> features = repository.listSubclasses();
        Assert.assertNotNull("list is null", features);
        Assert.assertFalse("list is empty", features.isEmpty());
        features.stream().map(Resource::getURI).forEach(System.out::println);
    }

    @Test
    public void testGetClass() {
        Optional<OntClass> hurricaneEye = repository.getClass(DarkData.HurricaneEye.getURI());
        Assert.assertTrue("class not found", hurricaneEye.isPresent());
        Assert.assertEquals("URIs do not match", DarkData.HurricaneEye.getURI(), hurricaneEye.get().getURI());
    }

    @Test
    public void testGetClassByLabel() {
        Optional<OntClass> hurricaneEye = repository.getClassByLabel("Hurricane Eye");
        Assert.assertTrue("class not found", hurricaneEye.isPresent());
        Assert.assertEquals("URIs do not match", DarkData.HurricaneEye.getURI(), hurricaneEye.get().getURI());
    }

    @Test
    public void testListPhysicalManifestationOfPhenomena() {
        Instant start = Instant.now();
        List<OntClass> features = repository.listPhysicalManifestationOfPhenomena(DarkData.Hurricane);
        Instant end = Instant.now();
        Duration diff = Duration.between(start, end);
        System.out.println("time: "+diff.toMillis()+"ms");

        Assert.assertNotNull("list is null", features);
        Assert.assertFalse("list is empty", features.isEmpty());
        features.stream().map(Resource::getURI).forEach(System.out::println);
    }
}
