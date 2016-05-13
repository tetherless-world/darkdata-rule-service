package darkdata.service;

import darkdata.DarkDataApplication;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.compatibility.CompatibilityValue;
import darkdata.model.ontology.DarkData;
import darkdata.repository.CandidateWorkflowRepository;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

/**
 * @author szednik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class SimpleScoringServiceTest {

    @Autowired
    private SimpleScoringService scoringService;

    @Autowired
    private CandidateWorkflowRepository candidateRepository;

    @Test
    public void testScore() {

        OntModel m = ModelFactory.createOntologyModel();

        CandidateWorkflow candidate = candidateRepository.createCandidateWorkflow(m, "urn:candidate/testScore").get();
        Assert.assertNotNull(candidate);

        CompatibilityAssertion assertion1 = candidate.createCompatibilityAssertion("urn:assertion1").get();
        assertion1.setValue(CompatibilityValue.SLIGHT);
        assertion1.setConfidence(0.5d);

        CompatibilityAssertion assertion2 = candidate.createCompatibilityAssertion("urn:assertion2").get();
        assertion2.setValue(CompatibilityValue.SOME);
        assertion2.setConfidence(1d);

        CompatibilityAssertion assertion3 = candidate.createCompatibilityAssertion("urn:assertion3").get();
        assertion3.setValue(CompatibilityValue.SOME);
        assertion3.setConfidence(0.5d);

        CompatibilityAssertion assertion4 = candidate.createCompatibilityAssertion("urn:assertion4").get();
        assertion4.setValue(CompatibilityValue.SOME);
        assertion4.setConfidence(0.5d);

        Resource scoredCandidate = scoringService.score(m, candidate.getIndividual());
        Assert.assertNotNull(scoredCandidate);
        Assert.assertTrue(scoredCandidate.hasProperty(DarkData.candidateScore));

        Optional<Double> score = Optional.ofNullable(m.getOntResource(scoredCandidate))
                .map(r -> r.getPropertyValue(DarkData.candidateScore))
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble);

        Assert.assertTrue("score not present", score.isPresent());
        double expected = (2d*3d)/Math.sqrt(3d)+(.5d*1d)/Math.sqrt(1d);
        Assert.assertEquals(expected, score.get(), 0d);
    }

    @Test
    public void testScore_2() {

        OntModel m = ModelFactory.createOntologyModel();

        CandidateWorkflow candidate = candidateRepository.createCandidateWorkflow(m, "urn:candidate/testScore").get();
        Assert.assertNotNull(candidate);

        CompatibilityAssertion assertion1 = candidate.createCompatibilityAssertion("urn:assertion1").get();
        assertion1.setValue(CompatibilityValue.STRONG);
        assertion1.setConfidence(0.5d);

        CompatibilityAssertion assertion2 = candidate.createCompatibilityAssertion("urn:assertion2").get();
        assertion2.setValue(CompatibilityValue.STRONG);
        assertion2.setConfidence(0.5d);

        CompatibilityAssertion assertion3 = candidate.createCompatibilityAssertion("urn:assertion3").get();
        assertion3.setValue(CompatibilityValue.STRONG);
        assertion3.setConfidence(0.5d);

        CompatibilityAssertion assertion4 = candidate.createCompatibilityAssertion("urn:assertion4").get();
        assertion4.setValue(CompatibilityValue.STRONG);
        assertion4.setConfidence(0.5d);

        CompatibilityAssertion assertion5 = candidate.createCompatibilityAssertion("urn:assertion5").get();
        assertion5.setValue(CompatibilityValue.SLIGHT);
        assertion5.setConfidence(1d);

        CompatibilityAssertion assertion6 = candidate.createCompatibilityAssertion("urn:assertion6").get();
        assertion6.setValue(CompatibilityValue.STRONG);
        assertion6.setConfidence(1d);

        Resource scoredCandidate = scoringService.score(m, candidate.getIndividual());
        Assert.assertNotNull(scoredCandidate);
        Assert.assertTrue(scoredCandidate.hasProperty(DarkData.candidateScore));

        Optional<Double> score = Optional.ofNullable(m.getOntResource(scoredCandidate))
                .map(r -> r.getPropertyValue(DarkData.candidateScore))
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble);

        Assert.assertTrue("score not present", score.isPresent());
        double expected = (3d*5d)/Math.sqrt(5)+(1d*1d)/Math.sqrt(1d);
        Assert.assertEquals(expected, score.get(), 0d);
    }
}
