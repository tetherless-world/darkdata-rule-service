package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
public class Dataset extends IndividualProxy {

    public static final OntClass CLASS = DarkData.Dataset;

    public Dataset(Individual individual) {
        super(individual);
        individual.addOntClass(CLASS);
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
        return Optional.ofNullable(getIndividual().getPropertyValue(DarkData.shortName))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
    }

    public void setLongName(String longName) {
        getIndividual().setPropertyValue(DarkData.longName, ResourceFactory.createPlainLiteral(longName));
    }

    public Optional<String> getLongName() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DarkData.longName))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
    }
}
