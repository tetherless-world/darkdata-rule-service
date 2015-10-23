package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;

/**
 * @author szednik
 */
public class ObservableProperty extends IndividualProxy {

    public static final OntClass CLASS = DarkData.ObservableProperty;

    public ObservableProperty(Individual individual) {
        super(individual);
    }
}
