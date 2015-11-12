package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;

/**
 * @author szednik
 */
public class ObservableProperty extends IndividualProxy {

    public static final OntClass CLASS = DarkData.ObservableProperty;

    public ObservableProperty(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }
}
