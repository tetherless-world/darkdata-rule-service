package darkdata.model.kb.compatibility;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DCTerms;

import java.util.Optional;

/**
 * @author szednik
 */
public class CompatibilityValue extends IndividualProxy {

    public final static OntClass CLASS = DarkData.CompatibilityValue;

    static public String getIdentifier(Model m, Resource compatibilityValue) {
        return m.listObjectsOfProperty(compatibilityValue, DCTerms.identifier)
                .toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getString)
                .findAny().orElse("DEFAULT");
    }

    public CompatibilityValue(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public CompatibilityValue(OntResource individual, String identifier) {
        super(individual);
        individual.addRDFType(CLASS);
        individual.setPropertyValue(DCTerms.identifier, ResourceFactory.createPlainLiteral(identifier));
    }

    public Optional<String> getIdentifier() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DCTerms.identifier))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getString);
    }

    public void setIdentifier(String identifier) {
        getIndividual().setPropertyValue(DCTerms.identifier, ResourceFactory.createPlainLiteral(identifier));
    }

    public static CompatibilityValue NEGATIVE = new CompatibilityValue(DarkData.negative_compatibility, "NEGATIVE");
    public static CompatibilityValue INDIFFERENT = new CompatibilityValue(DarkData.indifferent_compatibility, "INDIFFERENT");
    public static CompatibilityValue SLIGHT = new CompatibilityValue(DarkData.slight_compatibility, "SLIGHT");
    public static CompatibilityValue SOME = new CompatibilityValue(DarkData.some_compatibility, "SOME");
    public static CompatibilityValue STRONG = new CompatibilityValue(DarkData.strong_compatibility, "STRONG");
}
