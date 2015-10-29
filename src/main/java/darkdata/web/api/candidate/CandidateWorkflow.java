package darkdata.web.api.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.web.api.workflow.Workflow;

/**
 * @author szednik
 */

public class CandidateWorkflow {

    @JsonProperty("visualization")
    private Workflow workflow;

    @JsonProperty("score")
    double score;

    public CandidateWorkflow() { }

    public CandidateWorkflow(Workflow workflow, double score) {
        this.workflow = workflow;
        this.score = score;
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
}
