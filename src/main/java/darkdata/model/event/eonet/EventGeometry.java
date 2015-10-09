package darkdata.model.event.eonet;

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
    List<List<Long>> coordinates;

    public EventGeometry(String type, String date, List<List<Long>> coordinates) {
        this.type = type;
        this.date = date;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public List<List<Long>> getCoordinates() {
        return coordinates;
    }
}
