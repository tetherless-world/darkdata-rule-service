package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.CandidateWorkflow;
import darkdata.model.CandidateWorkflowCriteria;
import darkdata.model.datavariable.DataVariable;
import darkdata.model.event.eonet.Event;
import darkdata.model.event.eonet.EventCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        return Arrays.asList(new DataVariable("var","var", null), null);
    }

    private CandidateWorkflowCriteria getTestCriteria() {
        Event event = getTestEvent();
        List<DataVariable> variables = getTestVariables();
        return new CandidateWorkflowCriteria(event, variables);
    }

    @Test
    public void testGenerate() {
        CandidateWorkflowCriteria criteria = getTestCriteria();
        List<CandidateWorkflow> candidates = service.generate(criteria);
        Assert.assertFalse(candidates.isEmpty());

        for(CandidateWorkflow candidate : candidates) {
            System.out.println(candidate.getPhenomena().get().getURI());
            System.out.println(candidate.getPhysicalFeature().get().getURI());
            System.out.println(candidate.getService().get().getURI());
            System.out.println("========================");
        }
    }
}
