package darkdata.model;

/**
 * @author szednik
 */
public class CompatibilityAssertion {

    private String id;
    // assertion info

    private Candidate candidate;

    public CompatibilityAssertion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
