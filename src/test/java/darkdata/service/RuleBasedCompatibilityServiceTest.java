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
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.stream.Collectors;

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
        setInferredFeature(candidate);

        List<CompatibilityAssertion> assertions = service.computeCompatibilities(candidate);
        Assert.assertFalse(assertions.isEmpty());

        System.out.println("\nprinting out confidence assertions for "+arAvTs.getIdentifier().get());
        assertions.stream()
                .forEach(a -> System.out.println(a.getValue().get().getIdentifier().get()));
    }

    private void setInferredFeature(CandidateWorkflow candidate) {
        Phenomena event = candidate.getEvent().get();
        OntModel inf = reason(candidate);
        List<PhysicalFeature> features = getInferredFeatures(inf, event);
        Assert.assertNotNull(features);
        Assert.assertFalse(features.isEmpty());

        // For this test just get the the 1st feature, in future compute compatibilities for all features
        PhysicalFeature feature = features.get(0);
        candidate.setFeature(feature);
    }

    private OntModel reason(CandidateWorkflow candidate) {
        OntModel m = candidate.getIndividual().getOntModel();
        OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF, m);
        inf.addSubModel(datasource.getSchema());
        inf.prepare();
        return inf;
    }

    private List<PhysicalFeature> getInferredFeatures(OntModel m, Phenomena event) {
        return m.getOntResource(event.getIndividual())
                .listPropertyValues(DarkData.physicalManifestation).toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(PhysicalFeature::new)
                .collect(Collectors.toList());
    }

}
