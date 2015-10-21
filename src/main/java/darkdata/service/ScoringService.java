package darkdata.service;

import darkdata.model.kb.candidate.Candidate;
import darkdata.model.kb.candidate.CandidateScore;

/**
 * @author szednik
 */

public interface ScoringService<S extends CandidateScore,T extends Candidate> {

    S score(T candidate);
}
