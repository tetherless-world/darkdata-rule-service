package darkdata.web;

import darkdata.model.web.Recommendation;
import darkdata.model.web.RecommendationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author szednik
 */

@RestController
@RequestMapping("/advisor")
public class AdvisoryController {

    @RequestMapping(value = "/recommendation", method = RequestMethod.POST)
    public ResponseEntity<Recommendation> recommendation(RecommendationRequest payload) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
