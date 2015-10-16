package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Phenomena;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class PhenomenaRepositoryTest {

    @Autowired
    private PhenomenaRepository repository;

    @Test
    public void testListSubclasses() {
        List<OntClass> phenomenaList = repository.listSubclasses();
        Assert.assertNotNull("list is null", phenomenaList);
        Assert.assertFalse("list is empty", phenomenaList.isEmpty());
        phenomenaList.stream().map(Resource::getURI).forEach(System.out::println);
    }

    @Test
    public void testGetClass() {
        Optional<OntClass> hurricane = repository.getClass(DarkData.Hurricane.getURI());
        Assert.assertTrue("class not found", hurricane.isPresent());
        Assert.assertEquals("URIs do not match", DarkData.Hurricane.getURI(), hurricane.get().getURI());
    }

    @Test
    public void testGetClassByLabel() {
        Optional<OntClass> hurricane = repository.getClassByLabel("Hurricane");
        Assert.assertTrue("class not found", hurricane.isPresent());
        Assert.assertEquals("URIs do not match", DarkData.Hurricane.getURI(), hurricane.get().getURI());
    }

    @Test
    public void testListClassesByTopic() {
        List<OntClass> topicSevereStorms = repository.listClassesByTopic("Severe Storms");
        Assert.assertNotNull("list is null", topicSevereStorms);
        Assert.assertFalse("list is empty", topicSevereStorms.isEmpty());
        topicSevereStorms.stream().map(Resource::getURI).forEach(System.out::println);
    }
}
