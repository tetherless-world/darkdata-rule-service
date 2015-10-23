package darkdata.service;

import darkdata.model.kb.candidate.Candidate;

import java.util.List;

/**
 * @author szednik
 */

public interface CandidateFactory<T extends Candidate,C> {

    List<T> generate(C criteria);
}
