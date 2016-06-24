package darkdata.service;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

/**
 * @author szednik
 */

interface ScoringService<S extends Resource,T extends Resource> {

    S score(Model m, T candidate);
}
