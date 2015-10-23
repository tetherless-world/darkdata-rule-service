package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.g4.G4Service;
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
public class CandidateWorkflowTest {

    @Test
    public void testGetEvent() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:foo", DarkData.CandidateWorkflow);
        CandidateWorkflow candidate = new CandidateWorkflow(c);

        Individual e = m.createIndividual("urn:bar", DarkData.Hurricane);
        Phenomena event = new Phenomena(e);

        candidate.setEvent(event);
        Optional<Phenomena> result = candidate.getEvent();

        Assert.assertTrue("result has no value", result.isPresent());
        Assert.assertEquals(event, result.get());
    }

    @Test
    public void testGetService() {
        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:foo", DarkData.CandidateWorkflow);
        CandidateWorkflow candidate = new CandidateWorkflow(c);

        Individual s = m.createIndividual("urn:bar", DarkData.Hovmoller);
        G4Service service = new G4Service(s);

        candidate.setService(service);
        Optional<G4Service> result = candidate.getService();

        Assert.assertTrue("result has no value", result.isPresent());
        Assert.assertEquals(service, result.get());
    }

}
