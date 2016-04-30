package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.ontology.DarkData;
import darkdata.transformers.CandidateWorkflowConverter;
import darkdata.transformers.Request2CandidateCriteriaConverter;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import darkdata.web.api.candidate.CandidateWorkflow;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class RecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    @Autowired
    private GenerateCandidateWorkflowService generateCandidateWorkflowService;

    @Autowired
    private SimpleScoringService scoringService;

    @Autowired
    private CandidateWorkflowConverter candidateWorkflowConverter;

    @Autowired
    private Request2CandidateCriteriaConverter request2CandidateCriteriaConverter;

    @Autowired
    private RuleBasedReasoningService compatibilityRulesReasoningService;

    private double SCORE_THRESHOLD = 0.5;

    public RecommendationResponse getRecommendation(RecommendationRequest request) {
        CandidateWorkflowCriteria criteria = request2CandidateCriteriaConverter.convert(request);

        Model m = generateCandidateWorkflowService.generateCandidates(criteria);
        List<Resource> candidates = getCandidateWorkflows(m);

        OntModel n = ModelFactory.createOntologyModel();
        n.addSubModel(m);

        final InfModel inf = compatibilityRulesReasoningService.reason(n);

        List<CandidateWorkflow> sortedScoredCandidates = getSortedScoredCandidates(inf, candidates);
        return new RecommendationResponse(request, sortedScoredCandidates);
    }

    private List<CandidateWorkflow> getSortedScoredCandidates(Model m, List<Resource> candidates) {
        candidateWorkflowConverter.setModel(m);
        return candidates.stream()
                .map(c -> scoringService.score(m, c))
                .filter(r -> getScore(m, r).orElse((double) -1) > SCORE_THRESHOLD)
                .map(candidateWorkflowConverter::convert)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .sorted(Comparator.comparing(CandidateWorkflow::getScore).reversed())
                .collect(Collectors.toList());
    }

    private Optional<Double> getScore(Model m, Resource candidate) {
        return m.listObjectsOfProperty(candidate, DarkData.candidateScore)
                .toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble)
                .findAny();
    }

    private List<Resource> getCandidateWorkflows(Model m) {
        return m.listResourcesWithProperty(RDF.type, DarkData.CandidateWorkflow).toList();
    }
}
