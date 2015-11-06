package darkdata.model.kb.compatibility;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;

/**
 * @author szednik
 */
public class CompatibilityValue extends IndividualProxy {

    public final static OntClass CLASS = DarkData.CompatibilityValue;

    public CompatibilityValue(Individual individual) {
        super(individual);
        individual.addOntClass(CLASS);
    }

    public static CompatibilityValue NEGATIVE = new CompatibilityValue(DarkData.negative_compatibility);
    public static CompatibilityValue INDIFFERENT = new CompatibilityValue(DarkData.indifferent_compatibility);
    public static CompatibilityValue SLIGHT = new CompatibilityValue(DarkData.slight_compatibility);
    public static CompatibilityValue SOME = new CompatibilityValue(DarkData.some_compatibility);
    public static CompatibilityValue STRONG = new CompatibilityValue(DarkData.strong_compatibility);
}
