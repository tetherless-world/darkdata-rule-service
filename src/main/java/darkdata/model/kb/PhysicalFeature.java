package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
public class PhysicalFeature extends IndividualProxy {

    public static final OntClass CLASS = DarkData.PhysicalManifestation;

    public PhysicalFeature(Individual individual) {
        super(individual);
    }

    public List<ObservableProperty> observableProperties() {
        return getIndividual()
                .listPropertyValues(DarkData.observableProperty).toList().stream()
                .filter(RDFNode::isResource)
                .map(r -> (OntResource) r.asResource())
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(ObservableProperty::new)
                .collect(Collectors.toList());
    }
}
