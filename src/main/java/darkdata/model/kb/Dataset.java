package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author szednik
 */
public class Dataset extends IndividualProxy {

    public static final OntClass CLASS = DarkData.Dataset;

    protected Dataset(Individual individual) {
        super(individual);
    }

    public List<DataVariable> getVariables() {
        return getIndividual().listPropertyValues(DarkData.variable).toList().stream()
                .filter(RDFNode::isResource)
                .map(r -> (OntResource) r.asResource())
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(DataVariable::new)
                .collect(Collectors.toList());
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

    public void setLongName(String longName) {
        getIndividual().setPropertyValue(DarkData.longName, ResourceFactory.createPlainLiteral(longName));
    }

    public Optional<String> getLongName() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.longName))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(RDFNode::toString)
                .findAny();
    }
}
