package darkdata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.datavariable.DataVariable;
import darkdata.model.event.eonet.Event;

import java.util.List;

/**
 * @author szednik
 */
public class CandidateWorkflowCriteria {

    @JsonProperty(value = "event")
    Event event;

    @JsonProperty(value = "data_variables")
    List<DataVariable> variables;

    public Event getEvent() {
        return event;
    }

    public List<DataVariable> getVariables() {
        return variables;
    }
}
