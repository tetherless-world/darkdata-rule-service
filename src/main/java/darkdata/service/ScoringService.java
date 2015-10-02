package darkdata.service;

import darkdata.model.Candidate;
import darkdata.model.CandidateScore;

/**
 * @author szednik
 */

public interface ScoringService<S extends CandidateScore,T extends Candidate> {

    S score(T candidate);
}
