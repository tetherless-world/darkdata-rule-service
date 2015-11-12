package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CandidateWorkflowRepository;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
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
public class RandomCompatibilityServiceTest {

    @Autowired
    private RandomCompatibilityService compatibilityService;

    @Autowired
    private CandidateWorkflowRepository repository;

    @Test
    public void testComputeCompatibilities() {
        CandidateWorkflow candidate = repository.createCandidateWorkflow("urn:candidate/random/testComputeCompatibilites").get();
        List<CompatibilityAssertion> assertions = compatibilityService.computeCompatibilities(candidate);
        Assert.assertFalse(assertions.isEmpty());
        assertions.stream().forEach(p -> System.out.println(p.getValue().get().getIndividual().getURI()+"\t"+p.getConfidence().get()));
    }

}
