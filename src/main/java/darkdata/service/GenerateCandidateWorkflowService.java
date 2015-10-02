package darkdata.service;

import darkdata.model.CandidateWorkflow;
import darkdata.model.CandidateWorkflowCriteria;
import org.springframework.stereotype.Service;

/**
 * @author szednik
 */

@Service
public class GenerateCandidateWorkflowService
        implements CandidateFactory<CandidateWorkflow, CandidateWorkflowCriteria> {

    @Override
    public CandidateWorkflow[] generate(CandidateWorkflowCriteria criteria) {
        return new CandidateWorkflow[0];
    }
}
