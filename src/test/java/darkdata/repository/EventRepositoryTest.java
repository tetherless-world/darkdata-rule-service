package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;
import java.util.UUID;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class EventRepositoryTest {

    @Autowired
    private EventRepository repository;

    @Test
    public void testCreateIndividual() {
        String uri = "urn:"+ UUID.randomUUID().toString();
        Optional<Individual> event = repository.createEvent(uri, DarkData.Hurricane);
        Assert.assertTrue(event.isPresent());
        Assert.assertEquals(uri, event.get().getURI());
        Assert.assertEquals(DarkData.Hurricane, event.get().getOntClass(true));

        /*
        event.get().listPropertyValues(DarkData.physicalManifestation)
                .toList()
                .stream()
                .map(v -> ((OntResource) v.asResource()))
                .flatMap(v -> v.listRDFTypes(false).toList().stream())
                .filter(t -> !t.isAnon() && !t.equals(DarkData.PhysicalManifestation))
                .filter(DarkData.PhysicalManifestation::hasSubClass)
                .forEach(t -> System.out.println(t.getURI()));
        */
    }
}
