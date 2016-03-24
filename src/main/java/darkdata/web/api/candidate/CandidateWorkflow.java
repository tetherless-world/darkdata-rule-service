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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateWorkflow)) return false;

        CandidateWorkflow that = (CandidateWorkflow) o;

        return Double.compare(that.getScore(), getScore()) == 0
                && (getWorkflow() != null ? getWorkflow().equals(that.getWorkflow()) : that.getWorkflow() == null
                && (getFeature() != null ? getFeature().equals(that.getFeature()) : that.getFeature() == null));

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getWorkflow() != null ? getWorkflow().hashCode() : 0;
        result = 31 * result + (getFeature() != null ? getFeature().hashCode() : 0);
        temp = Double.doubleToLongBits(getScore());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
