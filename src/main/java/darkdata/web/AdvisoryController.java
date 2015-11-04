package darkdata.web;

import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import darkdata.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<RecommendationResponse> recommendation(@RequestBody RecommendationRequest payload) {
        try {
            validate(payload);
            RecommendationResponse recommendationResponse = recommendationService.getRecommendation(payload);
            return new ResponseEntity<>(recommendationResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validate(RecommendationRequest request) throws IllegalArgumentException {
        Assert.notNull(request, "payload is null");
        Assert.notNull(request.getEvent(), "event is null");
        Assert.notNull(request.getDataVariableList(), "data variable list is null");
        Assert.notEmpty(request.getDataVariableList(), "data variable list is empty");
    }

    @RequestMapping(value = "/status",method = RequestMethod.GET)
    public ResponseEntity<String> status(){
        return new ResponseEntity<>("I am Up.", HttpStatus.OK);
    }
}
