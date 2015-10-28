package darkdata.model.kb.candidate;

import darkdata.model.kb.DataVariable;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class DataVariableTestHarness {

    public static DataVariable createDataVariable(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual d = m.createIndividual(uri, DarkData.DataVariable);
        return new DataVariable(d);
    }
}
