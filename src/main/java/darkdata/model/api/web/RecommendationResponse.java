package darkdata.model.api.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.api.web.candidate.CandidateWorkflow;

import java.util.List;

/**
 * @author szednik
 */
public class RecommendationResponse {

    @JsonProperty("criteria")
    private RecommendationRequest request;

    @JsonProperty("candidates")
    private List<CandidateWorkflow> candidates;

    public RecommendationResponse() { }

    public RecommendationRequest getRequest() {
        return request;
    }

    public void setRequest(RecommendationRequest request) {
        this.request = request;
    }

    public List<CandidateWorkflow> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateWorkflow> candidates) {
        this.candidates = candidates;
    }
}
