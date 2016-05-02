package darkdata.transformers;

import darkdata.model.ontology.DarkData;
import darkdata.web.api.candidate.CandidateWorkflow;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.workflow.Workflow;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Component
public class CandidateWorkflowConverter {

    @Autowired
    private DataVariableConverter variableConverter;

    private static final Logger logger = LoggerFactory.getLogger(CandidateWorkflowConverter.class);

    public Optional<CandidateWorkflow> convert(Model model, Resource candidate) {

        if (candidate == null) {
            return Optional.empty();
        }

        Workflow workflow = new Workflow();

        double score = getScore(model, candidate).orElse(0D);

        String service = getService(model, candidate).orElse("UNKNOWN");
        workflow.setService(service);

        List<DataVariable> vars = getDataFields(model, candidate).stream()
                .map(v -> variableConverter.convert(model, v))
                .map(Optional::get)
                .collect(Collectors.toList());
        workflow.setVariables(vars);

        String feature = getFeature(model, candidate).orElse("UNKNOWN");

        return Optional.of(new CandidateWorkflow(workflow, feature, score));
    }

    private Optional<String> getFeature(Model model, Resource candidate) {
        Resource feature = model.listObjectsOfProperty(candidate, DarkData.candidateFeature).next().asResource();
        return model.listObjectsOfProperty(feature, RDF.type).toList().stream()
                .map(RDFNode::asResource)
                .filter(r -> !r.isAnon())
                .map(Resource::getURI)
                .filter(u -> !u.equals(OWL.Thing.getURI()))
                .filter(u -> !u.equals(RDFS.Resource.getURI()))
                .filter(u -> !u.equals(DarkData.PhysicalManifestation.getURI()))
                .findFirst();
    }

    private List<Resource> getDataFields(Model model, Resource candidate) {
        return model.listObjectsOfProperty(candidate, DarkData.candidateVariable)
                .toList().stream()
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }

    private Optional<String> getService(Model model, Resource candidate) {
        return Optional.ofNullable(model.listObjectsOfProperty(candidate, DarkData.candidateService).next().asResource())
                .map(r -> model.listObjectsOfProperty(r, DCTerms.identifier).next().asLiteral().getString());
    }

    private Optional<Double> getScore(Model model, Resource candidate) {
        return Optional.ofNullable(model.listObjectsOfProperty(candidate, DarkData.candidateScore))
                .filter(NodeIterator::hasNext)
                .map(NodeIterator::next)
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble);
    }
}
