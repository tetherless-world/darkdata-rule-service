package darkdata.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.datavariable.DataVariable;

import java.util.List;

/**
 * @author szednik
 */
public class RecommendationRequest {

    @JsonProperty("event")
    private Event event;

    @JsonProperty("data_variables")
    private List<DataVariable> dataVariableList;

    public RecommendationRequest() { }

    public RecommendationRequest(Event event, List<DataVariable> dataVariableList) {
        this.event = event;
        this.dataVariableList = dataVariableList;
    }

    public List<DataVariable> getDataVariableList() {
        return dataVariableList;
    }

    public void setDataVariableList(List<DataVariable> dataVariableList) {
        this.dataVariableList = dataVariableList;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
