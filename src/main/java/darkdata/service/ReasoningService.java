package darkdata.service;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;

/**
 * @author szednik
 */
public interface ReasoningService {

    InfModel reason(OntModel m);
}
