package darkdata.web.api.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.web.api.workflow.Workflow;

/**
 * @author szednik
 */

public class CandidateWorkflow {

    @JsonProperty("workflow")
    private Workflow workflow;

    @JsonProperty("feature")
    private String feature;

    @JsonProperty("score")
    double score;

    public CandidateWorkflow() { }

    public CandidateWorkflow(Workflow workflow, String feature, double score) {
        this.workflow = workflow;
        this.score = score;
        this.feature = feature;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
