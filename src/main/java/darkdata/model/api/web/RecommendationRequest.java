package darkdata.model.api.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.api.web.datavariable.DataVariable;
import darkdata.model.api.web.event.eonet.Event;

import java.util.List;

/**
 * @author szednik
 */
public class RecommendationRequest {
    @JsonProperty(value = "event")
    private Event event;

    @JsonProperty("data_variables")
    private List<DataVariable> dataVariableList;

    public RecommendationRequest(List<DataVariable> dataVariableList) {
    this.dataVariableList = dataVariableList;
    }

    public List<DataVariable> getDataVariableList() {
        return dataVariableList;
    }

    public void setDataVariableList(List<DataVariable> dataVariableList) {
        this.dataVariableList = dataVariableList;
    }


    public RecommendationRequest() {
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }



}
