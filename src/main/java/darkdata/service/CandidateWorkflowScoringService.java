package darkdata.service;

import darkdata.model.CandidateWorkflow;
import darkdata.model.CandidateWorkflowScore;
import org.springframework.stereotype.Service;

/**
 * @author szednik
 */

@Service
public class CandidateWorkflowScoringService
        implements ScoringService<CandidateWorkflowScore, CandidateWorkflow>{

    @Override
    public CandidateWorkflowScore score(CandidateWorkflow candidate) {
        return new CandidateWorkflowScore(candidate, 0d);
    }
}
