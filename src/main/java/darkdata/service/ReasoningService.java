package darkdata.service;

import org.apache.jena.ontology.OntModel;

/**
 * @author szednik
 */
public interface ReasoningService {

    OntModel reason(OntModel m);
}
