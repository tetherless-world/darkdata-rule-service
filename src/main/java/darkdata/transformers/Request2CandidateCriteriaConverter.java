package darkdata.transformers;

import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.service.RecommendationService;
import darkdata.web.api.RecommendationRequest;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class Request2CandidateCriteriaConverter implements Converter<RecommendationRequest, CandidateWorkflowCriteria> {

    private static final Logger logger = LoggerFactory.getLogger(Request2CandidateCriteriaConverter.class);

    @Override
    public CandidateWorkflowCriteria convert(RecommendationRequest request) {

        StopWatch watch = new Slf4JStopWatch("Request2CandidateCriteriaConverter::convert");

        CandidateWorkflowCriteria criteria = new CandidateWorkflowCriteria();

        Optional.ofNullable(request.getEvent())
                .ifPresent(criteria::setEvent);

        Optional.ofNullable(request.getDataVariableList())
                .ifPresent(criteria::setVariables);

        Optional.ofNullable(request.getCategories())
                .ifPresent(criteria::setCategories);

        watch.stop();
        return criteria;
    }
}
