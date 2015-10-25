package darkdata.model.api.web.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.api.web.datavariable.DataVariable;

import java.util.Collections;
import java.util.List;

/**
 * @author szednik
 */

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
    private List<DataVariable> variables;

    @JsonProperty("dataKeyword")
    private List<String> keywords = Collections.<String>emptyList();

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
}
