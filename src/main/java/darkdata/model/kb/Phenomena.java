package darkdata.model.kb;

import darkdata.model.kb.coverage.Geometry;
import darkdata.model.ontology.DarkData;
import darkdata.model.ontology.GeoSparql;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
public class Phenomena extends IndividualProxy {

    public static final OntClass CLASS = DarkData.Phenomena;

    public Phenomena(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
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

    public List<PhysicalFeature> getPhysicalFeatures(OntModel m) {
        return m.getOntResource(this.getIndividual())
                .listPropertyValues(DarkData.physicalManifestation).toList().stream()
                .filter(RDFNode::isResource)
                .map(r -> (OntResource) r.asResource())
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(PhysicalFeature::new)
                .collect(Collectors.toList());
    }

    public List<Geometry> getGeometries() {
        OntModel m = getIndividual().getOntModel();
        return getIndividual()
                .listPropertyValues(DarkData.geometry).toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(Resource::getURI)
                .map(m::getIndividual)
                .map(Geometry::new)
                .collect(Collectors.toList());
    }

    public void addGeometry(Geometry geometry) {
        getIndividual().setPropertyValue(DarkData.geometry, geometry.getIndividual());
    }

    public Optional<Geometry> createGeometry(String uri) {
        OntModel m = getIndividual().getOntModel();
        Optional<Geometry> geometry =  Optional.ofNullable(m.createIndividual(uri, GeoSparql.Geometry))
                .map(Geometry::new);
        geometry.ifPresent(this::addGeometry);
        return geometry;
    }

}
