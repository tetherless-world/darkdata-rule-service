package darkdata.model.kb;

import darkdata.model.kb.coverage.Geometry;
import darkdata.model.ontology.DarkData;
import darkdata.model.ontology.GeoSparql;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
public class Phenomena extends IndividualProxy {

    public static final OntClass CLASS = DarkData.Phenomena;

    public Phenomena(Individual individual) {
        super(individual);
    }

    public List<PhysicalFeature> getPhysicalFeatures() {
        return getIndividual()
                .listPropertyValues(DarkData.physicalManifestation).toList().stream()
                .filter(RDFNode::isResource)
                .map(r -> (OntResource) r.asResource())
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(PhysicalFeature::new)
                .collect(Collectors.toList());
    }

    public List<Geometry> getGeometries() {
        return getIndividual()
                .listPropertyValues(DarkData.geometry).toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(r -> getIndividual().getOntModel().getIndividual(r.getURI()))
                .map(Geometry::new)
                .collect(Collectors.toList());
    }

    public void addGeometry(Geometry geometry) {
        getIndividual().setPropertyValue(DarkData.geometry, geometry.getIndividual());
    }

    public Optional<Geometry> createGeometry(String uri) {
        Optional<Geometry> geometry =  Optional.ofNullable(getIndividual().getOntModel().createIndividual(uri, GeoSparql.Geometry))
                .map(Geometry::new);
        geometry.ifPresent(this::addGeometry);
        return geometry;
    }

}
