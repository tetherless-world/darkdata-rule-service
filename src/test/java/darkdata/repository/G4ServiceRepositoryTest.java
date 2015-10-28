package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.kb.g4.G4Service;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class G4ServiceRepositoryTest {

    @Autowired
    private G4ServiceRepository repository;

    @Test
    public void testListSubclasses() {
        List<OntClass> g4serviceClasses = repository.listSubclasses();
        Assert.assertNotNull(g4serviceClasses);
        Assert.assertFalse(g4serviceClasses.isEmpty());
        g4serviceClasses.stream().map(Resource::getURI).forEach(System.out::println);
    }

    @Test
    public void testListInstances() {
        List<G4Service> g4services = repository.listInstances();
        Assert.assertNotNull(g4services);
        Assert.assertFalse(g4services.isEmpty());
        g4services.stream().map(G4Service::getIndividual).map(Resource::getURI).forEach(System.out::println);
    }

    @Test
    public void testGetByURI() {
        final String SERVICE_URI = "http://www.purl.org/twc/ns/darkdata#DiArAvTs";
        Optional<G4Service> service = repository.getByURI(SERVICE_URI);
        Assert.assertTrue(service.isPresent());
        Assert.assertEquals(SERVICE_URI, service.get().getIndividual().getURI());
    }

    @Test
    public void testGetByIdentifier() {
        final String SERVICE_IDENTIFIER = "DiArAvTs";
        Optional<G4Service> service = repository.getByIdentifier(SERVICE_IDENTIFIER);
        Assert.assertTrue("service is missing", service.isPresent());
        Assert.assertTrue("service identifier is missing", service.get().getIdentifier().isPresent());
        Assert.assertEquals("service identifier does not match expected value", SERVICE_IDENTIFIER, service.get().getIdentifier().get());
    }
}
