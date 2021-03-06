package darkdata.web.api.event.eonet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author szednik
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Event {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "categories")
    private List<EventCategory> categories;

    @JsonProperty(value = "description")
    private String description;

    public Event() { }

    public Event(String id,
                 String title,
                 String link,
                 String description,
                 List<EventCategory> categories) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.categories = categories;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategories(List<EventCategory> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public List<EventCategory> getCategories() {
        return categories;
    }
}
