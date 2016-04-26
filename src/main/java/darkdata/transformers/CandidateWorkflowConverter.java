package darkdata.transformers;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.candidate.CandidateScore;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import darkdata.web.api.workflow.Workflow;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

        Assert.isTrue(candidate.getService().isPresent(), "candidate must have a service");

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

        Assert.isTrue(candidate.getFeature().isPresent(), "candidate must have a physical feature");

        // TODO there is an issue here with getting the most specific feature type URI
        String feature = candidate.getFeature()
                .map(IndividualProxy::getIndividual)
                .map(i -> i.listRDFTypes(true))
                .map(ExtendedIterator::toList)
                .get().stream()
                .map(Resource::getURI)
                .filter(u -> !u.equals(OWL.Thing.getURI()))
                .filter(u -> !u.equals(DarkData.PhysicalManifestation.getURI()))
                .findFirst().get();

        Assert.hasText(feature);

        return Optional.of(new darkdata.web.api.candidate.CandidateWorkflow(workflow, feature, score));

    }
}
