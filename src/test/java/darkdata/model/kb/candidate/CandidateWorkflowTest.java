package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhenomenaTestHarness;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.kb.g4.G4ServiceTestHarness;
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

        CandidateWorkflow candidate = CandidateWorkflowTestHarness.createCandidateWorkflow("urn:candidate/testGetEvent");
        Assert.assertNotNull(candidate);

        Phenomena hurricane = PhenomenaTestHarness.createHurricane("urn:event/testGetEvent");
        Assert.assertNotNull(hurricane);

        candidate.setEvent(hurricane);
        Optional<Phenomena> result = candidate.getEvent();

        Assert.assertTrue("result has no value", result.isPresent());
        Assert.assertEquals(hurricane, result.get());
    }

    @Test
    public void testGetService() {

        CandidateWorkflow candidate = CandidateWorkflowTestHarness.createCandidateWorkflow("urn:candidate/testGetService");
        Assert.assertNotNull(candidate);

        G4Service service = G4ServiceTestHarness.createService("urn:service/testGetService");
        Assert.assertNotNull(candidate);

        candidate.setService(service);
        Optional<G4Service> result = candidate.getService();

        Assert.assertTrue("result has no value", result.isPresent());
        Assert.assertEquals(service, result.get());
    }

}
