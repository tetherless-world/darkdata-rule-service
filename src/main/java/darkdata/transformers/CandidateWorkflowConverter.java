package darkdata.transformers;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.candidate.CandidateScore;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.web.api.workflow.Workflow;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class CandidateWorkflowConverter implements Converter<CandidateWorkflow, Optional<darkdata.web.api.candidate.CandidateWorkflow>> {

    @Autowired
    private G4ServiceConverter g4ServiceConverter;

    @Autowired
    private DataVariableConverter variableConverter;

    @Override
    public Optional<darkdata.web.api.candidate.CandidateWorkflow> convert(CandidateWorkflow candidate) {

        if (candidate == null) {
            return Optional.empty();
        }

        double score = candidate.getScore()
                .map(CandidateScore::getScore)
                .orElse(0d);

        Workflow workflow = new Workflow();

        candidate.getService()
                .flatMap(g4ServiceConverter::convert)
                .ifPresent(workflow::setService);

        // TODO workflow.setStartTime("");

        // TODO workflow.setEndTime("");

        // TODO workflow.setBoundingBox("");

        // TODO support multiple variables
        candidate.getVariable()
                .flatMap(variableConverter::convert)
                .map(Arrays::asList)
                .ifPresent(workflow::setVariables);

        // TODO workflow.setShape("");

        // TODO workflow.setKeywords([]);

        String feature = candidate.getFeature()
                .map(IndividualProxy::getIndividual)
                .map(OntResource::getRDFType)
                .map(Resource::getURI)
                .get();

        return Optional.of(new darkdata.web.api.candidate.CandidateWorkflow(workflow, feature, score));

    }
}
