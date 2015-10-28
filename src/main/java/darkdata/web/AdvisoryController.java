package darkdata.web;

import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import darkdata.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RecommendationService recommendationService;

    @RequestMapping(value = "/recommendation", method = RequestMethod.POST)
    public ResponseEntity<RecommendationResponse> recommendation(RecommendationRequest payload) {
        RecommendationResponse recommendationResponse = recommendationService.getRecommendation(payload);
        return new ResponseEntity<>(recommendationResponse, HttpStatus.OK);
    }
}
