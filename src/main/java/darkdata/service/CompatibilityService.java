package darkdata.service;

import darkdata.model.kb.candidate.Candidate;
import darkdata.model.kb.compatibility.CompatibilityAssertion;

import java.util.List;

/**
 * @author szednik
 */

public interface CompatibilityService<T extends CompatibilityAssertion,S extends Candidate> {

    List<T> computeCompatibilities(S candidate);
}
