package darkdata.service;

import darkdata.model.kb.candidate.Candidate;
import darkdata.model.kb.compatibility.CompatibilityAssertion;

/**
 * @author szednik
 */

public interface CompatibilityService<T extends CompatibilityAssertion,S extends Candidate> {

    T[] computeCompatibilities(S candidate);
}
