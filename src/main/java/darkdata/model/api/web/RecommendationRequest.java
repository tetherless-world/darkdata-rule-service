package darkdata.model.api.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkdata.model.api.web.event.eonet.Event;

/**
 * @author szednik
 */
public class RecommendationRequest {
    @JsonProperty(value = "event")

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
