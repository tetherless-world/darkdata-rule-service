package darkdata.service;

import darkdata.model.Candidate;

/**
 * @author szednik
 */

public interface CandidateFactory<T extends Candidate,C> {

    T[] generate(C criteria);
}
