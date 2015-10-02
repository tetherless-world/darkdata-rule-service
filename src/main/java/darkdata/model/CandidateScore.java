package darkdata.model;

/**
 * @author szednik
 */
public class CandidateScore {

    private Double score;
    private Candidate candidate;

    public CandidateScore(Candidate candidate, Double score) {
        this.score = score;
        this.candidate = candidate;
    }

    public Double getScore() {
        return score;
    }

    public Candidate getCandidate() {
        return candidate;
    }
}
