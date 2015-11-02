package darkdata.web.api.event.eonet;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author szednik
 */

public class EventCategory {

    @JsonProperty(value = "-domain")
    String domain;

    @JsonProperty(value = "#text")
    String text;

    public EventCategory() { }

    public EventCategory(String domain, String text) {
        this.domain = domain;
        this.text = text;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDomain() {
        return domain;
    }

    public String getText() {
        return text;
    }
}
