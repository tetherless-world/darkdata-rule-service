package darkdata.model.kb;

import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author szednik
 */
public class DataVariable extends IndividualProxy {

    public static final OntClass CLASS = DarkData.DataVariable;

    public DataVariable(Individual individual) {
        super(individual);
    }

    public void setShortName(String shortName) {
        getIndividual().setPropertyValue(DarkData.shortName, ResourceFactory.createPlainLiteral(shortName));
    }

    public Optional<String> getShortName() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.shortName))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(RDFNode::toString)
                .findAny();
    }

    public void setDataset(Dataset dataset) {
        getIndividual().setPropertyValue(DarkData.dataset, dataset.getIndividual());
    }

    public Optional<Dataset> getDataset() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.dataset))
                .map(r -> (OntResource) r)
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(Dataset::new)
                .findFirst();
    }

}
