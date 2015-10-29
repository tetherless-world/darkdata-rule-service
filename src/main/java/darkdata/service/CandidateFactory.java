package darkdata.service;

import darkdata.model.kb.candidate.Candidate;
import darkdata.model.kb.candidate.CandidateCriteria;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;

import java.util.List;

/**
 * @author szednik
 */

public interface CandidateFactory<T extends Candidate,C extends CandidateWorkflowCriteria> {

    List<T> generate(C criteria);
}
