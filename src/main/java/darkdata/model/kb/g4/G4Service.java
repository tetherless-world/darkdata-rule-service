package darkdata.model.kb.g4;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;

/**
 * @author szednik
 */
public class G4Service extends IndividualProxy {

    public static OntClass CLASS = DarkData.Visualization;

    public G4Service(Individual individual) {
        super(individual);
    }
}
