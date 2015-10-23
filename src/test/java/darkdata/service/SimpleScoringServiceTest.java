package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowScore;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.model.ontology.DarkData;
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

import java.util.UUID;

/**
 * @author szednik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class SimpleScoringServiceTest {

    @Autowired
    private SimpleScoringService scoringService;

    @Test
    public void testScore() {

        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual("urn:foo", DarkData.CandidateWorkflow);
        CandidateWorkflow candidate = new CandidateWorkflow(c);

        Individual a1 = m.createIndividual("urn:assertion1", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion1 = new CompatibilityAssertion(a1);
        assertion1.setValue(CompatibilityValue.SLIGHT);
        assertion1.setConfidence(0.42d);

        Individual a2 = m.createIndividual("urn:assertion2", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion2 = new CompatibilityAssertion(a2);
        assertion2.setValue(CompatibilityValue.SOME);
        assertion2.setConfidence(0.63d);

        Individual a3 = m.createIndividual("urn:assertion3", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion3 = new CompatibilityAssertion(a3);
        assertion3.setValue(CompatibilityValue.SOME);
        assertion3.setConfidence(0.47d);

        Individual a4 = m.createIndividual("urn:assertion4", DarkData.CompatibilityAssertion);
        CompatibilityAssertion assertion4 = new CompatibilityAssertion(a4);
        assertion4.setValue(CompatibilityValue.SOME);
        assertion4.setConfidence(0.59d);

        candidate.addCompatibilityAssertion(assertion1);
        candidate.addCompatibilityAssertion(assertion2);
        candidate.addCompatibilityAssertion(assertion3);
        candidate.addCompatibilityAssertion(assertion4);

        CandidateWorkflowScore score = scoringService.score(candidate);
        Assert.assertNotNull(score);
        System.out.println("score: "+score.getScore());
    }
}
