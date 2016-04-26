package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDFS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
public class VersionedDataProduct extends IndividualProxy {

    public static final OntClass CLASS = DarkData.VersionedDataProduct;

    public VersionedDataProduct(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public List<DataVariable> getVariables() {
        final OntModel m = getIndividual().getOntModel();
        return getIndividual().listPropertyValues(DCTerms.hasPart)
                .toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(DataVariable::new)
                .collect(Collectors.toList());
    }

    public void setShortName(String shortName) {
        getIndividual().setPropertyValue(RDFS.label, ResourceFactory.createPlainLiteral(shortName));
    }

    public Optional<String> getShortName() {
        return Optional.ofNullable(getIndividual().getPropertyValue(RDFS.label))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
    }

    public void setVersion(String version) {
        getIndividual().setPropertyValue(DarkData.version, ResourceFactory.createPlainLiteral(version));
    }

    public Optional<String> getVersion() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DarkData.version))
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

    public Optional<DataProduct> getDataProduct() {
        final OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DCTerms.isVersionOf))
                .map(m::getOntResource)
                .map(DataProduct::new);
    }
}
