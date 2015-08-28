package darkdata.model;

import darkdata.model.datavariable.DataVariable;
import darkdata.model.event.Event;

import java.util.List;

/**
 * @author szednik
 */
public class RecommendationRequest {

    Event event;
    List<DataVariable> variables;
}
