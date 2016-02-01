package darkdata.web.api.event.eonet;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author szednik
 */

public class EventCategory {

    @JsonProperty(value = "id")
    int id;

    @JsonProperty(value = "title")
    String title;

    public EventCategory() { }

    public EventCategory(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
