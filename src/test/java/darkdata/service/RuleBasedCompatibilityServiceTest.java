package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.repository.EventRepository;
import darkdata.repository.G4ServiceRepository;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        CandidateWorkflow candidate = repository.createCandidateWorkflow("urn:candidate/rules/testComputeCompatibility").get();
        Phenomena volcanicEruption = eventRepository.createEvent("urn:event/testComputeCompatibility", DarkData.VolcanicEruption).get();
        G4Service arAvTs = serviceRepository.getByIdentifier("ArAvTs").get();
//        G4Service arAvTs = serviceRepository.getByIdentifier("HvLt").get();

        candidate.setEvent(volcanicEruption);
        candidate.setService(arAvTs);

        OntModel m = candidate.getIndividual().getOntModel();
        OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF, m);
        inf.addSubModel(datasource.getSchema());
        inf.prepare();

//        List<Statement> stmts = inf.listStatements().filterDrop(m::contains).toList();
//        m.add(stmts);

        List<PhysicalFeature> features = getInferredFeatures(inf, volcanicEruption);
        Assert.assertNotNull(features);
        Assert.assertFalse(features.isEmpty());

        // For this test just get the the 1st feature, in future compute compatibilities for all features
        PhysicalFeature feature = features.get(0);

        candidate.setFeature(feature);

        InfModel ruleInf = ModelFactory.createInfModel(service.getReasoner(), inf);
        ruleInf.add(datasource.getSchema());

        ruleInf.prepare();

        // TODO run these 5 computations in parallel
        computeStrongCompatibilityAssertions(ruleInf, candidate);
        computeSomeCompatibilityAssertions(ruleInf, candidate);
        computeSlightCompatibilityAssertions(ruleInf, candidate);
        computeIndifferentCompatibilityAssertions(ruleInf, candidate);
        computeNegativeCompatibilityAssertions(ruleInf, candidate);

        System.out.println("\nprinting out confidence assertions for "+arAvTs.getIdentifier().get());
        candidate.getCompatibilityAssertions().stream()
                .forEach(a -> System.out.println(a.getValue().get().getIdentifier().get()));

//
//        Optional<CompatibilityAssertion> assertion = service.computeCompatibility(deductions, null, arAvTs);
//        Assert.assertTrue(assertion.isPresent());
    }

    private List<PhysicalFeature> getInferredFeatures(OntModel m, Phenomena event) {
        return m.getIndividual(event.getIndividual().getURI())
                .listPropertyValues(DarkData.physicalManifestation).toList().stream()
                .filter(RDFNode::isResource)
                .map(r -> (OntResource) r.asResource())
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(PhysicalFeature::new)
                .collect(Collectors.toList());
    }

    private void computeStrongCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.strongCompatibilityFor, CompatibilityValue.STRONG);
    }

    private void computeSomeCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.someCompatibilityFor, CompatibilityValue.SOME);
    }

    private void computeSlightCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.slightCompatibilityFor, CompatibilityValue.SLIGHT);
    }

    private void computeIndifferentCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.indifferentCompatibilityFor, CompatibilityValue.INDIFFERENT);
    }

    private void computeNegativeCompatibilityAssertions(InfModel ruleInf, CandidateWorkflow candidate) {
        computeAssertions(ruleInf, candidate, DarkData.negativeCompatibilityFor, CompatibilityValue.NEGATIVE);
    }

    private void computeAssertions(InfModel ruleInf,
                                   CandidateWorkflow candidate,
                                   OntProperty compatibilityProperty,
                                   CompatibilityValue compatibilityValue) {

        Optional<PhysicalFeature> feature = candidate.getFeature();
        Assert.assertTrue("feature is missing", feature.isPresent());

        Optional<G4Service> service = candidate.getService();
        Assert.assertTrue("g4 service is missing", service.isPresent());

        Stream.of(feature.get())
                .flatMap(f -> ruleInf.listObjectsOfProperty(compatibilityProperty).toList().stream())
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .filter(service.get().getBestForCharacteristics()::contains)
                .distinct()
                .map(f -> candidate.createCompatibilityAssertion("urn:assertion/"+ UUID.randomUUID().toString()))
                .map(Optional::get)
                .forEach(a -> a.setValue(compatibilityValue));
    }

}
