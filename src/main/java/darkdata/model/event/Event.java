package darkdata.model.event;

import java.util.List;

/**
 * @author szednik
 */

public class Event {

    String id;
    String title;
    String link;
    String description;

    List<EventCategory> categories;
    List<EventGeometry> geometry;
}
