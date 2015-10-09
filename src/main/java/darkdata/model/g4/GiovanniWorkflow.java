package darkdata.model.g4;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author szednik
 */
public class GiovanniWorkflow {

    @JsonProperty(value = "service")
    GiovanniService service;

    public GiovanniWorkflow(GiovanniService service) {
        this.service = service;
    }

    public GiovanniService getService() {
        return service;
    }
}
