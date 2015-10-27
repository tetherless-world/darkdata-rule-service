package darkdata.model.kb.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.api.web.datavariable.DataVariable;
import darkdata.model.api.web.event.eonet.Event;

import java.util.List;

/**
 * @author szednik
 */
public class CandidateWorkflowCriteria extends CandidateCriteria {

    @JsonProperty(value = "event")
    Event event;

    @JsonProperty(value = "data_variables")
    List<DataVariable> variables;

    public CandidateWorkflowCriteria(Event event, List<DataVariable> variables) {
        this.event = event;
        this.variables = variables;
    }

    public Event getEvent() {
        return event;
    }

    public List<DataVariable> getVariables() {
        return variables;
    }
}
