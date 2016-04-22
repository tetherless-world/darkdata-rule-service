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
public class DataProduct extends IndividualProxy {

    public static final OntClass CLASS = DarkData.DataProduct;

    public DataProduct(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
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

    public List<VersionedDataProduct> getVersionedDataProducts() {
        final OntModel m = getIndividual().getOntModel();
        return getIndividual().listPropertyValues(DCTerms.hasVersion)
                .toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(VersionedDataProduct::new)
                .collect(Collectors.toList());
    }

}
