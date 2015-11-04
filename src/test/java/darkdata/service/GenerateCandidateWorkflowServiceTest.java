package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;
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
        return new Event("test", "test", "test", "test", Collections.singletonList(category), null);
    }

    private List<DataVariable> getTestVariables() {
        return Collections.singletonList(new DataVariable("test", "test", "test", "test"));
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
    }
}
