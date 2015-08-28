package darkdata.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author szednik
 */

public class Event {

    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "title")
    String title;

    @JsonProperty(value = "link")
    String link;

    @JsonProperty(value = "description")
    String description;

    @JsonProperty(value = "category")
    List<EventCategory> categories;

    @JsonProperty(value = "geometry")
    List<EventGeometry> geometry;
}
