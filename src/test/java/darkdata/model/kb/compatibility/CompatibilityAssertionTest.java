package darkdata.model.kb.compatibility;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
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
public class CompatibilityAssertionTest {

    @Test
    public void testGetValue() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:foo", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion = new CompatibilityAssertion(c);
        Assert.assertNotNull(assertion);

        assertion.setValue(CompatibilityValue.SLIGHT);
        Optional<CompatibilityValue> result = assertion.getValue();
        Assert.assertTrue(result.isPresent());
        Assert.assertNotNull(result.get().getIndividual());
        Assert.assertEquals(CompatibilityValue.SLIGHT, result.get());
    }

    @Test
    public void testGetConfidence() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:foo", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion = new CompatibilityAssertion(c);
        Assert.assertNotNull(assertion);

        assertion.setConfidence(0.42D);
        Optional<Double> result = assertion.getConfidence();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(Double.valueOf(0.42D), result.get());
    }

    @Test
    public void testGetCandidate() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:foo", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion = new CompatibilityAssertion(c);
        Assert.assertNotNull(assertion);

        Individual cw = m.createIndividual("urn:bar", DarkData.CandidateWorkflow);
        CandidateWorkflow candidate = new CandidateWorkflow(cw);

        assertion.setCandidateWorkflow(candidate);
        Optional<CandidateWorkflow> result = assertion.getCandidate();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(candidate, result.get());
    }
}
