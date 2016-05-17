package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

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

    public boolean hasConcreteFeatureClass() {
        return this.getIndividual().listRDFTypes(false).toList().stream()
                .filter(t -> !t.isAnon())
                .filter(t -> !t.equals(RDFS.Resource))
                .filter(t -> !t.equals(OWL.Thing))
                .filter(t -> !t.equals(DarkData.PhysicalManifestation))
                .filter(DarkData.PhysicalManifestation::hasSubClass)
                .findAny().isPresent();
    }

}
