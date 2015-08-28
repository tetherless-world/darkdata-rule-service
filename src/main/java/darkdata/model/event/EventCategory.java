package darkdata.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author szednik
 */

public class EventCategory {

    @JsonProperty(value = "-domain")
    String domain;

    @JsonProperty(value = "#text")
    String text;
}
