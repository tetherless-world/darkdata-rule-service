package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.repository.DataVariableRepository;
import darkdata.repository.EventRepository;
import darkdata.repository.G4ServiceRepository;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
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

    @Autowired
    private DataVariableRepository variableRepository;

    @Autowired
    private RuleBasedReasoningService compatibilityRulesReasoningService;

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

        final InfModel inf = compatibilityRulesReasoningService.reason(m);

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(inf, candidate);
        Assert.assertFalse(assertions.isEmpty());

        assertions.stream()
                .forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
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

        final InfModel inf = compatibilityRulesReasoningService.reason(m);

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(inf, candidate);
        Assert.assertFalse(assertions.isEmpty());

        assertions.stream()
                //.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
                .forEach(a -> System.out.println(a.getValue().get().getIdentifier().get()));
    }

    @Test
    public void testComputeCompatibility_Hurricane() {

        OntModel m = datasource.createOntModel();

        CandidateWorkflow candidate = repository.createCandidateWorkflow(m, "urn:candidate/rules/testComputeCompatibility_Hurricane")
                .orElseThrow(() -> new RuntimeException("could not create candidate workflow"));

        Phenomena hurricane = eventRepository.createEvent(m, "urn:event/testComputeCompatibility_Hurricane", DarkData.Hurricane)
                .orElseThrow(() -> new RuntimeException("could not create event"));

        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs")
                .orElseThrow(() -> new RuntimeException("could not create service"));

        DataVariable variable = variableRepository.createDataVariable(m, "urn:variable/testComputeCompatibility_Hurricane")
                .orElseThrow(() -> new RuntimeException("could not create data variable"));

        Individual halfHourly = m.createIndividual("http://darkdata.tw.rpi.edu/data/time-interval/half-hourly", DarkData.TimeInterval);
        variable.getIndividual().addProperty(DarkData.timeInterval, halfHourly);

        Assert.assertEquals(m, candidate.getIndividual().getOntModel());

        candidate.setEvent(hurricane);
        candidate.setService(arAvTs);
        candidate.addVariable(variable);

        List<PhysicalFeature> features = candidate.getEvent()
                .orElseThrow(() -> new RuntimeException("could not get candidate event"))
                .getPhysicalFeatures();

        Assert.assertFalse("features is empty", features.isEmpty());

        // for test purposes this candidate will focus on only the windfields feature
        PhysicalFeature windFields = features.stream()
                .filter(f -> f.hasType(DarkData.WindFields))
                .findFirst().orElseThrow(() -> new RuntimeException("could not find expected feature"));

        candidate.setFeature(windFields);

        final InfModel inf = compatibilityRulesReasoningService.reason(m);
        List<CompatibilityAssertion> assertions = service.computeCompatibilities(inf, candidate);
        Assert.assertFalse(assertions.isEmpty());
    }

    @Test
    public void testComputeCompatibility_Hurricane_2() {

        OntModel m = datasource.createOntModel();

        CandidateWorkflow candidate = repository.createCandidateWorkflow(m, "urn:candidate/rules/testComputeCompatibility_Hurricane")
                .orElseThrow(() -> new RuntimeException("could not create candidate workflow"));

        Phenomena hurricane = eventRepository.createEvent(m, "urn:event/testComputeCompatibility_Hurricane", DarkData.Hurricane)
                .orElseThrow(() -> new RuntimeException("could not create event"));

        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs")
                .orElseThrow(() -> new RuntimeException("could not retrieve service"));

        DataVariable variable = variableRepository.getByIdentifier("MAT1NXSLV_5_2_0_UV10M_mag")
                .orElseThrow(() -> new RuntimeException("could not retrieve datafield"));

        Assert.assertEquals(m, candidate.getIndividual().getOntModel());

        candidate.setEvent(hurricane);
        candidate.setService(arAvTs);
        candidate.addVariable(variable);

        List<PhysicalFeature> features = candidate.getEvent()
                .orElseThrow(() -> new RuntimeException("could not get candidate event"))
                .getPhysicalFeatures();

        Assert.assertFalse("features is empty", features.isEmpty());

        // for test purposes this candidate will focus on only the windfields feature
        PhysicalFeature windFields = features.stream()
                .filter(f -> f.hasType(DarkData.WindFields))
                .findFirst().orElseThrow(() -> new RuntimeException("could not find expected feature"));

        candidate.setFeature(windFields);

        final InfModel inf = compatibilityRulesReasoningService.reason(m);
        List<CompatibilityAssertion> assertions = service.computeCompatibilities(inf, candidate);
        Assert.assertFalse(assertions.isEmpty());
    }

}
