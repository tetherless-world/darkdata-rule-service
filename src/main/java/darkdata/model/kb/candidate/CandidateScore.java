package darkdata.model.kb.candidate;

import org.apache.jena.rdf.model.Resource;

/**
 * @author szednik
 */
public class CandidateScore {

    private String id;
    private Double score;
    private Resource candidate;

    public CandidateScore(String id, Resource candidate, Double score) {
        this.id = id;
        this.score = score;
        this.candidate = candidate;
    }

    public String getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public Resource getCandidate() {
        return candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateScore)) return false;

        CandidateScore that = (CandidateScore) o;

        return getScore().equals(that.getScore());

    }

    @Override
    public int hashCode() {
        return getScore().hashCode();
    }
}
