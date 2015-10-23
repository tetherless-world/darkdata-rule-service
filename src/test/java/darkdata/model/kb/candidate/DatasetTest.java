package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Dataset;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class DatasetTest {

    @Test
    public void testGetShortName() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual d = m.createIndividual("urn:dataset", DarkData.Dataset);
        Dataset dataset = new Dataset(d);
        Assert.assertNotNull(dataset);

        dataset.setShortName("shortNameTest");
        Optional<String> result = dataset.getShortName();

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("shortNameTest", result.get());
    }

    @Test
    public void testGetLongName() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual d = m.createIndividual("urn:dataset", DarkData.Dataset);
        Dataset dataset = new Dataset(d);
        Assert.assertNotNull(dataset);

        dataset.setLongName("longNameTest");
        Optional<String> result = dataset.getLongName();

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("longNameTest", result.get());
    }
}
