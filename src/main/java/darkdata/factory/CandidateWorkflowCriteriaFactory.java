package darkdata.factory;

import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.web.api.RecommendationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class CandidateWorkflowCriteriaFactory {

    private static final Logger logger = LoggerFactory.getLogger(CandidateWorkflowCriteriaFactory.class);

    public Optional<CandidateWorkflowCriteria> get(RecommendationRequest request) {

        CandidateWorkflowCriteria criteria = new CandidateWorkflowCriteria();

        Optional.ofNullable(request.getEvent())
                .ifPresent(criteria::setEvent);

        Optional.ofNullable(request.getDataVariableList())
                .ifPresent(criteria::setVariables);

        Optional.ofNullable(request.getCategories())
                .ifPresent(criteria::setCategories);

        return Optional.of(criteria);
    }
}
