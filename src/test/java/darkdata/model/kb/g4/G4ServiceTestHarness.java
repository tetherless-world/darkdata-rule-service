package darkdata.model.kb.g4;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class G4ServiceTestHarness {

    public static G4Service createService(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual s = m.createIndividual(uri, DarkData.Hovmoller);
        return new G4Service(s);
    }
}
