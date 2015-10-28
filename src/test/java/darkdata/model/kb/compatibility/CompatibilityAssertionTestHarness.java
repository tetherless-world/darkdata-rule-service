package darkdata.model.kb.compatibility;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class CompatibilityAssertionTestHarness {

    public static CompatibilityAssertion createCompatibilityAssertion(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual a = m.createIndividual(uri, DarkData.CompatibilityAssertion);
        return new CompatibilityAssertion(a);
    }
}
