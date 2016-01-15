package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.repository.EventRepository;
import darkdata.repository.G4ServiceRepository;
import org.apache.jena.ontology.OntModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RuleBasedCompatibilityServiceTest {

    @Autowired
    private RuleBasedCompatibilityService service;

    @Autowired
    private CandidateWorkflowRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private G4ServiceRepository serviceRepository;

    @Autowired
    private DarkDataDatasource datasource;

    @Test
    public void testComputeCompatibility() {

        OntModel m = datasource.createOntModel();

        CandidateWorkflow candidate = repository.createCandidateWorkflow(m, "urn:candidate/rules/testComputeCompatibility").get();
        Phenomena volcanicEruption = eventRepository.createEvent(m, "urn:event/testComputeCompatibility", DarkData.VolcanicEruption).get();
        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs").get();

        Assert.assertEquals(m, candidate.getIndividual().getOntModel());

        candidate.setEvent(volcanicEruption);
        candidate.setService(arAvTs);

        List<PhysicalFeature> features = candidate.getEvent().get().getPhysicalFeatures();
        Assert.assertNotNull("features is null", features);
        Assert.assertFalse("features is empty", features.isEmpty());

        // for test purposes this candidate will focus on the 1st feature inferred for the given event
        candidate.setFeature(features.get(0));

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(candidate);
        Assert.assertFalse(assertions.isEmpty());

        assertions.stream()
                .forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
                //.forEach(a -> System.out.println(a.getValue().get().getIdentifier().get()));
    }

    @Test
    public void testComputeCompatibility_SevereStorm() {

        OntModel m = datasource.createOntModel();

        CandidateWorkflow candidate = repository.createCandidateWorkflow(m, "urn:candidate/rules/testComputeCompatibility_SevereStorm").get();
        Phenomena volcanicEruption = eventRepository.createEvent(m, "urn:event/testComputeCompatibility", DarkData.SevereStorm).get();
        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs").get();

        Assert.assertEquals(m, candidate.getIndividual().getOntModel());

        candidate.setEvent(volcanicEruption);
        candidate.setService(arAvTs);

        List<PhysicalFeature> features = candidate.getEvent().get().getPhysicalFeatures();
        Assert.assertNotNull("features is null", features);
        Assert.assertFalse("features is empty", features.isEmpty());

        // for test purposes this candidate will focus on the 1st feature inferred for the given event
        candidate.setFeature(features.get(0));

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(candidate);
        Assert.assertFalse(assertions.isEmpty());

        assertions.stream()
                //.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
                .forEach(a -> System.out.println(a.getValue().get().getIdentifier().get()));
    }

}
