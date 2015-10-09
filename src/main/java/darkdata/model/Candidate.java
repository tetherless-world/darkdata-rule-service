package darkdata.model;

import java.util.Optional;

/**
 * @author szednik
 */

public class Candidate {

    private String id;
    private Optional<CompatibilityAssertion[]> compatibilityAssertions = Optional.empty();
    private Optional<CandidateScore> score = Optional.empty();

    public Candidate(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCompatibilityAssertions(CompatibilityAssertion[] compatibilityAssertions) {
        this.compatibilityAssertions = Optional.ofNullable(compatibilityAssertions);
    }

    public Optional<CompatibilityAssertion[]> getCompatibilityAssertions() {
        return compatibilityAssertions;
    }

    public void setScore(CandidateScore score) {
        this.score = Optional.ofNullable(score);
    }

    public Optional<CandidateScore> getScore() {
        return score;
    }

}
