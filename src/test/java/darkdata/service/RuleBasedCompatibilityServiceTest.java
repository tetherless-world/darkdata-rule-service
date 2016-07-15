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

    private List<CompatibilityAssertion> computeCompatibilityAssertionsOfEventType(String label, OntClass phenomenaClass) {

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

        candidate.getEvent()
                .map(Phenomena::getPhysicalFeatures)
                .orElseThrow(() -> new RuntimeException("Could not get event features"))
                .stream()
                .filter(PhysicalFeature::hasConcreteFeatureClass)
                .findFirst()
                .ifPresent(candidate::setFeature);

        final InfModel inf = compatibilityRulesReasoningService.reason(m);
        return service.computeCompatibilities(inf, candidate);
    }

    private List<CompatibilityAssertion> computeCompatibilityAssertionsOfEventType(String label, OntClass phenomenaClass, String datafieldId) {

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

        candidate.getEvent()
                .map(Phenomena::getPhysicalFeatures)
                .orElseThrow(() -> new RuntimeException("Could not get event features"))
                .stream()
                .filter(PhysicalFeature::hasConcreteFeatureClass)
                .findFirst()
                .ifPresent(candidate::setFeature);

        final InfModel inf = compatibilityRulesReasoningService.reason(m);
        return service.computeCompatibilities(inf, candidate);
    }

    @Test
    public void testComputeCompatibility_SevereStorm() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("SevereStorm", DarkData.SevereStorm);
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }
    
    @Test
    public void testComputeCompatibility_Flood() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("Flood", DarkData.Flood);
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_Wildfire() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("Wildfire", DarkData.Wildfire);
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_VolcanicEruption() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("VolcanicEruption", DarkData.VolcanicEruption);
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_DustAndHaze() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("DustAndHaze", DarkData.DustAndHaze);
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_Drought() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("Drought", DarkData.Drought);
        Assert.assertNotNull(assertions);
        //Assert.assertFalse(assertions.isEmpty());
        //assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_ManMade() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("ManMade", DarkData.ManMade);
        Assert.assertNotNull(assertions);
        //Assert.assertFalse(assertions.isEmpty());
        //assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_WaterColor() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("WaterColor", DarkData.WaterColor);
        Assert.assertNotNull(assertions);
        //Assert.assertFalse(assertions.isEmpty());
        //assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_Hurricane() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("Hurricane", DarkData.Hurricane);
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

    @Test
    public void testComputeCompatibility_Hurricane_and_variable() {
        List<CompatibilityAssertion> assertions = computeCompatibilityAssertionsOfEventType("Hurricane_and_variable", DarkData.Hurricane, "MAT1NXSLV_5_2_0_UV10M_mag");
        Assert.assertFalse(assertions.isEmpty());
        assertions.forEach(a -> Assert.assertTrue(a.getValue().isPresent()));
    }

}
