package darkdata.repository;

import darkdata.model.kb.Phenomena;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author szednik
 */

@Service
public class PhenomenaRepository {

    // TODO replace with repository based on external datasource (file or endpoint)

    private static final Map<String,Phenomena> phenomenaMap;
    static {
        phenomenaMap = new HashMap<>();
        phenomenaMap.put("hurricane", new Phenomena("hurricane", "Hurricane"));
        phenomenaMap.put("volcanic_eruption", new Phenomena("volcanic_eruption", "Volcanic Eruption"));
        phenomenaMap.put("dust_smoke_haze", new Phenomena("dust_smoke_haze","Dust, Smoke, Haze"));
        phenomenaMap.put("flood", new Phenomena("flood", "Flood"));
        phenomenaMap.put("drought", new Phenomena("drought", "Drought"));
        phenomenaMap.put("earthquake", new Phenomena("earthquake", "Earthquake"));
        phenomenaMap.put("wildfire", new Phenomena("wildfire", "Wildfire"));
        phenomenaMap.put("severe_storm", new Phenomena("severe_storm", "Severe Storm"));
    }

    public Collection<Phenomena> list() {
        return phenomenaMap.values();
    }

    public Optional<Phenomena> get(String id) {
        return Optional.ofNullable(phenomenaMap.get(id));
    }
}
