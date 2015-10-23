package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
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

import java.util.List;

/**
 * @author szednik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class CandidateTest {

    @Test
    public void testGetCompatibilityAssertions() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:candidate", DarkData.CandidateWorkflow);
        CandidateWorkflow candidate = new CandidateWorkflow(c);

        Individual a1 = m.createIndividual("urn:assertion1", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion1 = new CompatibilityAssertion(a1);

        Individual a2 = m.createIndividual("urn:assertion2", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion2 = new CompatibilityAssertion(a2);

        candidate.addCompatibilityAssertion(assertion1);
        candidate.addCompatibilityAssertion(assertion2);

        List<CompatibilityAssertion> results = candidate.getCompatibilityAssertions();

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(assertion1));
        Assert.assertTrue(results.contains(assertion2));
    }
}
