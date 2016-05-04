package darkdata.service;

import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import org.apache.jena.rdf.model.Model;

/**
 * @author szednik
 */

interface CandidateFactory<T extends Model, C extends CandidateWorkflowCriteria> {

    T generateCandidates(C criteria);
}
