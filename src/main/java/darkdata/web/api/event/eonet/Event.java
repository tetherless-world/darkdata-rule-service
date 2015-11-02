package darkdata.web.api.event.eonet;

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

    public Event() { }

    public Event(String id,
                 String title,
                 String link,
                 String description,
                 List<EventCategory> categories,
                 List<EventGeometry> geometry) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.categories = categories;
        this.geometry = geometry;
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

    public void setGeometry(List<EventGeometry> geometry) {
        this.geometry = geometry;
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

    public List<EventGeometry> getGeometry() {
        return geometry;
    }
}
