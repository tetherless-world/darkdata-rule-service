package darkdata.model.kb.g4;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author szednik
 */
public class G4Service extends IndividualProxy {

    public static OntClass CLASS = DarkData.Visualization;

    public G4Service(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    /**
     * Returns identifier string for service
     * @return Optional identifier string
     */
    public Optional<String> getIdentifier() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DCTerms.identifier))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
    }

    public List<Resource> getBestForCharacteristics() {
        return getIndividual().listPropertyValues(DarkData.bestFor)
                .toList().stream()
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }
}
