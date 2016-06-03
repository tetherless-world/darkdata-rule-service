package darkdata.web.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.EventCategory;

import java.util.List;

/**
 * @author szednik
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RecommendationRequest {

    @JsonProperty("event")
    private Event event;

    @JsonProperty("data_variables")
    private List<DataVariable> dataVariableList;

    @JsonProperty(value = "event-categories")
    private List<EventCategory> categories;

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

    public List<EventCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<EventCategory> categories) {
        this.categories = categories;
    }
}
