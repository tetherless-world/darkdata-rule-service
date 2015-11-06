package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.repository.EventRepository;
import darkdata.repository.G4ServiceRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CandidateWorkflowRepository candidateWorkflowRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private G4ServiceRepository serviceRepository;

    @Test
    public void testGetEvent() {

        CandidateWorkflow candidate = candidateWorkflowRepository.createCandidateWorkflow("urn:candidate/testGetEvent").get();
        Assert.assertNotNull(candidate);

        Phenomena hurricane = eventRepository.createEvent("urn:event/testGetEvent", DarkData.Hurricane).get();
        Assert.assertNotNull(hurricane);

        candidate.setEvent(hurricane);
        Optional<Phenomena> result = candidate.getEvent();

        Assert.assertTrue("result has no value", result.isPresent());
        Assert.assertEquals(hurricane, result.get());
    }

    @Test
    public void testGetService() {

        CandidateWorkflow candidate = candidateWorkflowRepository.createCandidateWorkflow("urn:candidate/testGetService").get();
        Assert.assertNotNull(candidate);

        G4Service service = serviceRepository.createService("urn:service/testGetService").get();
        Assert.assertNotNull(candidate);

        candidate.setService(service);
        Optional<G4Service> result = candidate.getService();

        Assert.assertTrue("result has no value", result.isPresent());
        Assert.assertEquals(service, result.get());
    }

}
