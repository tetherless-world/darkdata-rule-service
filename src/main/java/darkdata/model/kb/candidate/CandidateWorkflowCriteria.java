package darkdata.model.kb.candidate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author szednik
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CandidateWorkflowCriteria extends CandidateCriteria {

    @JsonProperty(value = "event")
    Event event;

    @JsonProperty(value = "data_variables")
    List<DataVariable> variables = new ArrayList<>();

    @JsonProperty(value = "event-categories")
    List<EventCategory> categories = new ArrayList<>();

    public CandidateWorkflowCriteria() { }

    public CandidateWorkflowCriteria(Event event, List<DataVariable> variables) {
        this.event = event;
        this.variables = variables;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<DataVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<DataVariable> variables) {
        this.variables = variables;
    }

    public List<EventCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<EventCategory> categories) {
        this.categories = categories;
    }
}
