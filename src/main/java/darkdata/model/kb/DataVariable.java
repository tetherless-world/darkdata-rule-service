package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

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

    public void setShortName(String shortName) {
        getIndividual().setPropertyValue(DarkData.shortName, ResourceFactory.createPlainLiteral(shortName));
    }

    public Optional<String> getShortName() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DarkData.shortName))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
    }

    public void setDataset(Dataset dataset) {
        getIndividual().setPropertyValue(DarkData.dataset, dataset.getIndividual());
    }

    public Optional<Dataset> getDataset() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.dataset))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(Resource::getURI)
                .map(m::getIndividual)
                .map(Dataset::new);
    }

}
