package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Phenomena;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
    public void testCreateHurricane() {
        String uri = "urn:"+ UUID.randomUUID().toString();
        Optional<Phenomena> event = repository.createEvent(uri, DarkData.Hurricane);
        Assert.assertTrue(event.isPresent());
        Assert.assertEquals(uri, event.get().getIndividual().getURI());
        Assert.assertEquals(DarkData.Hurricane, event.get().getIndividual().getOntClass(true));

        Stream.of(event.get())
                .map(Phenomena::getIndividual)
                .flatMap(e -> e.listPropertyValues(DarkData.physicalManifestation).toList().stream())
                .map(v -> ((OntResource) v.asResource()))
                .flatMap(v -> v.listRDFTypes(false).toList().stream())
                .filter(t -> !t.isAnon() && !t.equals(DarkData.PhysicalManifestation))
                .filter(DarkData.PhysicalManifestation::hasSubClass)
                .forEach(t -> System.out.println(t.getURI()));
    }

    @Test
    public void testCreateVolcanicEruption() {
        String uri = "urn:"+ UUID.randomUUID().toString();
        Optional<Phenomena> event = repository.createEvent(uri, DarkData.VolcanicEruption);
        Assert.assertTrue(event.isPresent());
        Assert.assertEquals(uri, event.get().getIndividual().getURI());
        Assert.assertEquals(DarkData.VolcanicEruption, event.get().getIndividual().getOntClass(true));

        Stream.of(event.get())
                .map(Phenomena::getIndividual)
                .flatMap(e -> e.listPropertyValues(DarkData.physicalManifestation).toList().stream())
                .map(v -> ((OntResource) v.asResource()))
                .flatMap(v -> v.listPropertyValues(DarkData.observableProperty).toList().stream())
                .map(RDFNode::asResource)
                .filter(t -> !t.isAnon())
                .forEach(v -> System.out.println(v.getURI()));
    }
}
