package darkdata.model.kb.g4;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.DCTerms;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author szednik
 */
public class G4Service extends IndividualProxy {

    public static OntClass CLASS = DarkData.Visualization;

    public G4Service(Individual individual) {
        super(individual);
    }

    /**
     * Returns identifier string for service
     * @return Optional identifier string
     */
    public Optional<String> getIdentifier() {
        return Stream.of(getIndividual().getPropertyValue(DCTerms.identifier))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(RDFNode::toString)
                .findAny();
    }
}
