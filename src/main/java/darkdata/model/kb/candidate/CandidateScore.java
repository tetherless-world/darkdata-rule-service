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
}
