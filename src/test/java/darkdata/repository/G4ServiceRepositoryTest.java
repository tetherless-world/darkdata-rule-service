package darkdata.repository;

import darkdata.DarkDataApplication;
import org.apache.jena.ontology.Individual;
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
        List<Individual> g4services = repository.listInstances();
        Assert.assertNotNull(g4services);
        Assert.assertFalse(g4services.isEmpty());
        g4services.stream().map(Resource::getURI).forEach(System.out::println);
    }
}
