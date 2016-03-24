package darkdata.transformers;

import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.web.api.RecommendationRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class Request2CandidateCriteriaConverter implements Converter<RecommendationRequest, CandidateWorkflowCriteria> {

    @Override
    public CandidateWorkflowCriteria convert(RecommendationRequest request) {

        CandidateWorkflowCriteria criteria = new CandidateWorkflowCriteria();

        Optional.ofNullable(request.getEvent())
                .ifPresent(criteria::setEvent);

        Optional.ofNullable(request.getDataVariableList())
                .ifPresent(criteria::setVariables);

        Optional.ofNullable(request.getCategories())
                .ifPresent(criteria::setCategories);

        return criteria;
    }
}
