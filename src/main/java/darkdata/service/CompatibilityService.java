package darkdata.service;

import darkdata.model.Candidate;
import darkdata.model.CompatibilityAssertion;

/**
 * @author szednik
 */

public interface CompatibilityService<T extends CompatibilityAssertion,S extends Candidate> {

    T[] computeCompatibilities(S candidate);
}
