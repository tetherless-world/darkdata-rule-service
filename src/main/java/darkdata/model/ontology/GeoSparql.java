package darkdata.model.ontology;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * @author szednik
 */
public class GeoSparql {

    private static OntModel m_model = ModelFactory.createOntologyModel();

    public static final String NS = "http://www.opengis.net/ont/geosparql#";

    public static String getURI() {return NS;}

    public static final Resource NAMESPACE = m_model.createResource( NS );

    public static final OntClass Geometry = m_model.createClass( "http://www.opengis.net/ont/geosparql#Geometry" );

    public static final OntProperty hasGeometry = m_model.createOntProperty( "http://www.opengis.net/ont/geosparql#hasGeometry" );

    public static final DatatypeProperty asWKT = m_model.createDatatypeProperty( "http://www.opengis.net/ont/geosparql#asWKT" );

}
