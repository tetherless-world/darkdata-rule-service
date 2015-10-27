package darkdata.model.kb.candidate;

/**
 * @author szednik
 */
public class CandidateScore {

    private String id;
    private Double score;
    private Candidate candidate;

    public CandidateScore(String id, Candidate candidate, Double score) {
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

    public Candidate getCandidate() {
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
