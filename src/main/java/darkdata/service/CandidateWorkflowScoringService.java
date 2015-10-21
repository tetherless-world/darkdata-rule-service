package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowScore;
import org.springframework.stereotype.Service;

/**
 * @author szednik
 */

@Service
public class CandidateWorkflowScoringService
        implements ScoringService<CandidateWorkflowScore, CandidateWorkflow>{

    @Override
    public CandidateWorkflowScore score(CandidateWorkflow candidate) {
        return new CandidateWorkflowScore("noop", candidate, 0d);
    }
}
