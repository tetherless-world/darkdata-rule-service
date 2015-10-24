package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.api.web.datavariable.DataVariable;
import darkdata.model.api.web.event.eonet.Event;
import darkdata.model.api.web.event.eonet.EventCategory;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class GenerateCandidateWorkflowServiceTest {

    @Autowired
    private GenerateCandidateWorkflowService service;

    private Event getTestEvent() {
        EventCategory category = new EventCategory("Volcanoes","Volcanoes");
        return new Event("test", "test", "test", "test", Arrays.asList(category), null);
    }

    private List<DataVariable> getTestVariables() {
        //return Arrays.asList(new DataVariable("var","var", null), null);
        return Arrays.asList(new DataVariable("test","test","test","test"));
    }

    private CandidateWorkflowCriteria getTestCriteria() {
        Event event = getTestEvent();
        List<DataVariable> variables = getTestVariables();
        return new CandidateWorkflowCriteria(event, variables);
    }

    @Test
    public void testGenerate() {
        CandidateWorkflowCriteria criteria = getTestCriteria();
        Instant start = Instant.now();
        List<CandidateWorkflow> candidates = service.generate(criteria);
        Instant end = Instant.now();
        Duration diff = Duration.between(start, end);
        System.out.println("time: "+diff.toMillis()+"ms");

        Assert.assertFalse(candidates.isEmpty());

//        for(CandidateWorkflow candidate : candidates) {
//            System.out.println(candidate.getEvent().get().getIndividual().getURI());
//            //System.out.println(candidate.getPhysicalFeature().get().getURI());
//            System.out.println(candidate.getService().get().getIndividual().getURI());
//            System.out.println("========================");
//        }
    }
}
