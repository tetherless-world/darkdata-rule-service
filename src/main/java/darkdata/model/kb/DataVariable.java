package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCTerms;

import java.util.Optional;

/**
 * @author szednik
 */
public class DataVariable extends IndividualProxy {

    public static final OntClass CLASS = DarkData.DataVariable;

    public DataVariable(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public Optional<String> getIdentifier() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DCTerms.identifier))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
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

    public void setDataset(VersionedDataProduct versionedDataProduct) {
        getIndividual().setPropertyValue(DCTerms.isPartOf, versionedDataProduct.getIndividual());
    }

    public Optional<VersionedDataProduct> getVersionedDataProduct() {
        final OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DCTerms.isPartOf))
                .map(m::getOntResource)
                .map(VersionedDataProduct::new);
    }

}
