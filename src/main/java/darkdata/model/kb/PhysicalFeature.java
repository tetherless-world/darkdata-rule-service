package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
public class PhysicalFeature extends IndividualProxy {

    public static final OntClass CLASS = DarkData.PhysicalManifestation;

    public PhysicalFeature(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public List<ObservableProperty> observableProperties() {
        OntModel m = getIndividual().getOntModel();
        return getIndividual()
                .listPropertyValues(DarkData.observableProperty).toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(ObservableProperty::new)
                .collect(Collectors.toList());
    }
}
