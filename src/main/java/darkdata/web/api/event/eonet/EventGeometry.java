package darkdata.web.api.event.eonet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author szednik
 */

public class EventGeometry {


    @JsonProperty(value = "date")
    String date;

    @JsonProperty(value = "type")
    String type;

    @JsonProperty(value = "coordinates")
    List<Double> coordinates;

    public EventGeometry() { }

    public EventGeometry(String type, String date, List<Double> coordinates) {
        this.date = date;
        this.type = type;
        this.coordinates = coordinates;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }
}
