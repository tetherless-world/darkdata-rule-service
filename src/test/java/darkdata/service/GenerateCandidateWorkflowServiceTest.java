package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.repository.*;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventTestHarness;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Resource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenerateCandidateWorkflowServiceTest {

    @Autowired
    private GenerateCandidateWorkflowService service;

    @Autowired
    private CandidateWorkflowRepository candidateRepository;

    @Autowired
    private PhysicalFeatureRepository featureRepository;

    @Autowired
    private ObservablePropertyRepository observablePropertyRepository;

    @Autowired
    private G4ServiceRepository serviceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DataVariableRepository variableRepository;

    private CandidateWorkflowCriteria getTestCriteria() {
        Event event = EventTestHarness.createEvent_EONET_224();
        List<DataVariable> variables = Collections.singletonList(new DataVariable("MAT1NXSLV_5_2_0_UV10M_mag"));
        return new CandidateWorkflowCriteria(event, variables);
    }

    @Ignore
    @Test
    public void testGenerate() {
        CandidateWorkflowCriteria criteria = getTestCriteria();
        InfModel m = service.generateCandidates(criteria);
        List<Resource> candidates = candidateRepository.getCandidateWorkflows(m);
        Assert.assertFalse(candidates.isEmpty());

        for(Resource candidate: candidates) {
            Optional<Resource> feature = featureRepository.getPhysicalFeatureFromCandidate(candidate);
            Assert.assertTrue(feature.isPresent());

            List<Resource> observableProperties = feature
                    .map(f -> observablePropertyRepository.listObservablePropertiesOfFeature(f))
                    .orElse(Collections.emptyList());

            Assert.assertFalse(observableProperties.isEmpty());

            Optional<Resource> service = serviceRepository.getServiceFromCandidate(candidate);
            Assert.assertTrue(service.isPresent());

            Optional<Resource> event = eventRepository.getEventFromCandidate(candidate);
            Assert.assertTrue(event.isPresent());

            List<Resource> variables = variableRepository.listVariablesFromCandidate(candidate);
            Assert.assertEquals(1, variables.size());
        }
    }
}
