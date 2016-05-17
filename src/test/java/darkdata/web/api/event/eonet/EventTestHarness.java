package darkdata.web.api.event.eonet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author szednik
 */
public class EventTestHarness {

    public static Event createEvent_EONET_224() {

        String id = "EONET_224";
        String title = "Hurricane Olaf";
        String link = "http://eonet.sci.gsfc.nasa.gov/api/v2/events/EONET_224";
        String description = "";

        int category_id = 10;
        String category_title = "Severe Storms";

//        String geometry_type = "Point";
//        String geometry_date = "2015-10-15T00:00:00Z";
//        List<List<Double>> geometry_coordinates = Collections.singletonList(Arrays.asList(-117.10d, 9.90d));

        EventCategory category = new EventCategory(category_id, category_title);
//        EventGeometry geometry = new EventGeometry(geometry_type, geometry_date, geometry_coordinates);

        List<EventCategory> categoryList = Collections.singletonList(category);
//        List<EventGeometry> geometryList = Collections.singletonList(geometry);

        return new Event(id, title, link, description, categoryList);
    }
}
