package darkdata.model.event;

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
}
