package darkdata.transformers;

import darkdata.model.ontology.DarkData;
import darkdata.web.api.candidate.CandidateWorkflow;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.workflow.Workflow;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Component
public class CandidateWorkflowConverter implements Converter<Resource, Optional<CandidateWorkflow>> {

    @Autowired
    private DataVariableConverter variableConverter;

    private static final Logger logger = LoggerFactory.getLogger(CandidateWorkflowConverter.class);

    private Model model;

    public Optional<CandidateWorkflow> convert(Resource candidate) {

        if (candidate == null) {
            return Optional.empty();
        }

        Workflow workflow = new Workflow();

        double score = getScore(candidate).orElse(0D);

        String service = getService(candidate).orElse("UNKNOWN");
        workflow.setService(service);

        variableConverter.setModel(model);

        List<DataVariable> vars = getDataFields(candidate).stream()
                .map(variableConverter::convert)
                .map(Optional::get)
                .collect(Collectors.toList());
        workflow.setVariables(vars);

        String feature = getFeature(candidate).orElse("UNKNOWN");

        return Optional.of(new CandidateWorkflow(workflow, feature, score));
    }

    private Optional<String> getFeature(Resource candidate) {
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

    private List<Resource> getDataFields(Resource candidate) {
        return model.listObjectsOfProperty(candidate, DarkData.candidateVariable)
                .toList().stream()
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }

    private Optional<String> getService( Resource candidate) {
        return Optional.ofNullable(model.listObjectsOfProperty(candidate, DarkData.candidateService).next().asResource())
                .map(r -> model.listObjectsOfProperty(r, DCTerms.identifier).next().asLiteral().getString());
    }

    private Optional<Double> getScore(Resource candidate) {
        return Optional.ofNullable(model.listObjectsOfProperty(candidate, DarkData.candidateScore))
                .filter(NodeIterator::hasNext)
                .map(NodeIterator::next)
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
