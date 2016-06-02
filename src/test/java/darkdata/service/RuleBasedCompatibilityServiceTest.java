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
import org.apache.jena.ontology.OntClass;
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

    private void testComputeCompatibilityOfEventType(String label, OntClass phenomenaClass) {

        OntModel m = datasource.createOntModel();

        CandidateWorkflow candidate = repository.createCandidateWorkflow(m, "urn:candidate/rules/testComputeCompatibility_"+label)
                .orElseThrow((() -> new RuntimeException("Could not create candidate")));

        Phenomena event = eventRepository.createEvent(m, "urn:event/testComputeCompatibility_"+label, phenomenaClass)
                .orElseThrow(() -> new RuntimeException("Could not create event"));

        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs")
                .orElseThrow(() -> new RuntimeException("Could not retrieve service"));

        Assert.assertEquals(m, candidate.getIndividual().getOntModel());

        candidate.setEvent(event);
        candidate.setService(arAvTs);

        m.prepare();

        List<PhysicalFeature> features = candidate.getEvent()
                .map(Phenomena::getPhysicalFeatures)
                .orElseThrow(() -> new RuntimeException("Could not get event features"));

        Assert.assertFalse("features is empty", features.isEmpty());

        PhysicalFeature feature = features.stream()
                .filter(PhysicalFeature::hasConcreteFeatureClass)
                .findFirst().orElseThrow(() -> new RuntimeException("could not find expected feature"));

        candidate.setFeature(feature);

        final InfModel inf = compatibilityRulesReasoningService.reason(m);

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(inf, candidate);
        Assert.assertFalse(assertions.isEmpty());

        assertions.stream()
                .forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    private void testComputeCompatibilityOfEventType(String label, OntClass phenomenaClass, String datafieldId) {

        OntModel m = datasource.createOntModel();

        CandidateWorkflow candidate = repository.createCandidateWorkflow(m, "urn:candidate/rules/testComputeCompatibility_"+label)
                .orElseThrow((() -> new RuntimeException("Could not create candidate")));

        Phenomena event = eventRepository.createEvent(m, "urn:event/testComputeCompatibility_"+label, phenomenaClass)
                .orElseThrow(() -> new RuntimeException("Could not create event"));

        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs")
                .orElseThrow(() -> new RuntimeException("Could not retrieve service"));

        DataVariable variable = variableRepository.getByIdentifier(datafieldId)
                .orElseThrow(() -> new RuntimeException("could not retrieve datafield"));

        Assert.assertEquals(m, candidate.getIndividual().getOntModel());

        candidate.setEvent(event);
        candidate.setService(arAvTs);
        candidate.addVariable(variable);

        m.prepare();

        List<PhysicalFeature> features = candidate.getEvent()
                .map(Phenomena::getPhysicalFeatures)
                .orElseThrow(() -> new RuntimeException("Could not get event features"));

        Assert.assertFalse("features is empty", features.isEmpty());

        PhysicalFeature feature = features.stream()
                .filter(PhysicalFeature::hasConcreteFeatureClass)
                .findFirst().orElseThrow(() -> new RuntimeException("could not find expected feature"));

        candidate.setFeature(feature);

        final InfModel inf = compatibilityRulesReasoningService.reason(m);

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(inf, candidate);
        Assert.assertFalse(assertions.isEmpty());

        assertions.stream()
                .forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_SevereStorm() {
        testComputeCompatibilityOfEventType("SevereStorm", DarkData.SevereStorm);
    }
    
    @Test
    public void testComputeCompatibility_Flood() {
        testComputeCompatibilityOfEventType("Flood", DarkData.Flood);
    }

    @Test
    public void testComputeCompatibility_Wildfire() {
        testComputeCompatibilityOfEventType("Wildfire", DarkData.Wildfire);
    }

    @Test
    public void testComputeCompatibility_VolcanicEruption() {
        testComputeCompatibilityOfEventType("VolcanicEruption", DarkData.VolcanicEruption);
    }

    @Test
    public void testComputeCompatibility_DustAndHaze() {
        testComputeCompatibilityOfEventType("DustAndHaze", DarkData.DustAndHaze);
    }

    // TODO add compatibility rules to catch basic drought with no variables
//    @Test
//    public void testComputeCompatibility_Drought() {
//        testComputeCompatibilityOfEventType("Drought", DarkData.Drought);
//    }

    @Test
    public void testComputeCompatibility_Hurricane() {
        testComputeCompatibilityOfEventType("Hurricane", DarkData.Hurricane);
    }

    @Test
    public void testComputeCompatibility_Hurricane_and_variable() {
        testComputeCompatibilityOfEventType("Hurricane_and_variable", DarkData.Hurricane, "MAT1NXSLV_5_2_0_UV10M_mag");
    }

}
