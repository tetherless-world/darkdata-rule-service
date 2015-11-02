package darkdata.web.api.event.eonet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author szednik
 */

public class EventGeometry {

    @JsonProperty(value = "type")
    String type;

    @JsonProperty(value = "date")
    String date;

    @JsonProperty(value = "coordinates")
    List<List<Double>> coordinates;

    public EventGeometry() { }

    public EventGeometry(String type, String date, List<List<Double>> coordinates) {
        this.type = type;
        this.date = date;
        this.coordinates = coordinates;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
}
