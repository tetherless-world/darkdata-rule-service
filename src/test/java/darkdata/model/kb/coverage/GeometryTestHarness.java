package darkdata.model.kb.coverage;

import darkdata.model.ontology.GeoSparql;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class GeometryTestHarness {

    public static Geometry createGeometry(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual(uri, GeoSparql.Geometry);
        return new Geometry(g);
    }
}
