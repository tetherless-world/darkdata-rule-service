package darkdata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.datavariable.DataVariable;
import darkdata.model.event.Event;

import java.util.List;

/**
 * @author szednik
 */
public class RecommendationRequest {

    @JsonProperty(value = "event")
    Event event;

    @JsonProperty(value = "data_variables")
    List<DataVariable> variables;
}
