package darkdata.factory;

import darkdata.model.ontology.DarkData;
import darkdata.web.api.candidate.CandidateWorkflow;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.workflow.Workflow;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
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
public class CandidateWorkflowObjectFactory implements ObjectFactory<CandidateWorkflow, Model, Resource> {

    @Autowired
    private DataVariableObjectFactory dataVariableObjectFactory;

    private static final Logger logger = LoggerFactory.getLogger(CandidateWorkflowObjectFactory.class);

    @Override
    public Optional<CandidateWorkflow> get(final Model model, final Resource candidate) {

        if (candidate == null) {
            return Optional.empty();
        }

        Workflow workflow = new Workflow();

        double score = getScore(model, candidate).orElse(0D);

        String service = getService(model, candidate).orElse("UNKNOWN");
        workflow.setService(service);

        List<DataVariable> vars = getDataFields(model, candidate).stream()
                .map(v -> dataVariableObjectFactory.get(model, v))
                .map(Optional::get)
                .collect(Collectors.toList());
        workflow.setVariables(vars);

        String feature = getFeature(model, candidate).orElse("UNKNOWN");

        return Optional.of(new CandidateWorkflow(workflow, feature, score));
    }

    private Optional<String> getFeature(final Model model, final Resource candidate) {

        Optional<Resource> feature = model.listObjectsOfProperty(candidate, DarkData.candidateFeature)
                .toList().stream()
                .map(RDFNode::asResource)
                .findAny();

        if(!feature.isPresent()) {
            return Optional.empty();
        }

        return model.listObjectsOfProperty(feature.get(), RDF.type).toList().stream()
                .map(RDFNode::asResource)
                .filter(r -> !r.isAnon())
                .map(Resource::getURI)
                .filter(u -> !u.equals(OWL.Thing.getURI()))
                .filter(u -> !u.equals(RDFS.Resource.getURI()))
                .filter(u -> !u.equals(DarkData.PhysicalFeature.getURI()))
                .findAny();
    }

    private List<Resource> getDataFields(final Model model, final Resource candidate) {
        return model.listObjectsOfProperty(candidate, DarkData.candidateVariable)
                .toList().stream()
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }

    private Optional<String> getService(final Model model, final Resource candidate) {
        return model.listObjectsOfProperty(candidate, DarkData.candidateService)
                .toList().stream()
                .map(RDFNode::asResource)
                .flatMap(r -> model.listObjectsOfProperty(r, DCTerms.identifier)
                        .toList().stream()
                        .map(RDFNode::asLiteral)
                        .map(Literal::getString)
                        .collect(Collectors.toList()).stream())
                .findAny();
    }

    private Optional<Double> getScore(final Model model, final Resource candidate) {
        return model.listObjectsOfProperty(candidate, DarkData.candidateScore)
                .toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble)
                .findAny();
    }
}
