package darkdata.web.api.workflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.web.api.datavariable.DataVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author szednik
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Workflow {

    @JsonProperty("service")
    private String service;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("bbox")
    private String boundingBox;

    @JsonProperty("data_variables")
    private List<DataVariable> variables = new ArrayList<>();

    @JsonProperty("dataKeyword")
    private List<String> keywords = new ArrayList<>();

    @JsonProperty("shape")
    private String shape;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<DataVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<DataVariable> variables) {
        this.variables = variables;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workflow)) return false;

        Workflow workflow = (Workflow) o;

        return getService() != null ? getService().equals(workflow.getService()) : workflow.getService() == null
                && (getStartTime() != null ? getStartTime().equals(workflow.getStartTime()) : workflow.getStartTime() == null
                && (getEndTime() != null ? getEndTime().equals(workflow.getEndTime()) : workflow.getEndTime() == null
                && (getBoundingBox() != null ? getBoundingBox().equals(workflow.getBoundingBox()) : workflow.getBoundingBox() == null
                && (getVariables() != null ? getVariables().equals(workflow.getVariables()) : workflow.getVariables() == null
                && (getKeywords() != null ? getKeywords().equals(workflow.getKeywords()) : workflow.getKeywords() == null
                && (getShape() != null ? getShape().equals(workflow.getShape()) : workflow.getShape() == null))))));

    }

    @Override
    public int hashCode() {
        int result = getService() != null ? getService().hashCode() : 0;
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getBoundingBox() != null ? getBoundingBox().hashCode() : 0);
        result = 31 * result + (getVariables() != null ? getVariables().hashCode() : 0);
        result = 31 * result + (getKeywords() != null ? getKeywords().hashCode() : 0);
        result = 31 * result + (getShape() != null ? getShape().hashCode() : 0);
        return result;
    }
}
