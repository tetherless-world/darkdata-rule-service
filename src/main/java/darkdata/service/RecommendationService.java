package darkdata.service;

import darkdata.model.kb.candidate.CandidateScore;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.workflow.Workflow;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.kb.g4.G4Service;
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

    @Autowired
    private GenerateCandidateWorkflowService generateCandidateWorkflowService;

    @Autowired
    private RandomCompatibilityService compatibilityService;

    @Autowired
    private SimpleScoringService scoringService;

    @Autowired
    private OWLDLReasoningService reasoningService;

    private double SCORE_THRESHOLD = 0;

    public RecommendationResponse getRecommendation(RecommendationRequest request) {

        CandidateWorkflowCriteria criteria = getCriteria(request);
        List<CandidateWorkflow> candidates = generateCandidateWorkflowService.generate(criteria);

        List<darkdata.web.api.candidate.CandidateWorkflow> sortedScoredCandidates = candidates.stream()
                .map(this::computeCompatibilities)
                .map(this::computeCandidateScore)
                .filter(c -> c.getScore().isPresent())
                .filter(c -> c.getScore().get().getScore() > SCORE_THRESHOLD)
                .map(this::transform)
                .sorted(Comparator.comparing(darkdata.web.api.candidate.CandidateWorkflow::getScore).reversed())
                .collect(Collectors.toList());

        return new RecommendationResponse(request, sortedScoredCandidates);
    }

    public CandidateWorkflowCriteria getCriteria(RecommendationRequest request) {
        Event event = request.getEvent();
        List<DataVariable> variables = request.getDataVariableList();
        return new CandidateWorkflowCriteria(event, variables);
    }

    public CandidateWorkflow computeCompatibilities(CandidateWorkflow c) {
        compatibilityService.computeCompatibilities(c).stream().forEach(c::addCompatibilityAssertion);
        return c;
    }

    public CandidateWorkflow computeCandidateScore(CandidateWorkflow c) {
        c.setScore(scoringService.score(c));
        return c;
    }

    // TODO write custom Transform<T,S> class
    public darkdata.web.api.candidate.CandidateWorkflow transform(CandidateWorkflow c) {

        double score = c.getScore()
                .map(CandidateScore::getScore)
                .orElse(0d);

        Workflow workflow = new Workflow();

        c.getService()
                .map(G4Service::getIdentifier)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(workflow::setService);

        // TODO workflow.setStartTime("");

        // TODO workflow.setEndTime("");

        // TODO workflow.setBoundingBox("");

        // TODO workflow.setDataVariables([]);

        // TODO workflow.setShape("");

        // TODO workflow.setKeywords([]);

        return new darkdata.web.api.candidate.CandidateWorkflow(workflow, score);
    }
}
