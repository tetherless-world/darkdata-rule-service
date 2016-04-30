package darkdata.model.kb.candidate;

import org.apache.jena.rdf.model.Resource;

/**
 * @author szednik
 */
public class CandidateWorkflowScore extends CandidateScore {

    public CandidateWorkflowScore(String id, Resource candidate, Double score) {
        super(id, candidate, score);
    }
}
