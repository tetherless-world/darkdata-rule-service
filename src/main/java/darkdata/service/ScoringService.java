package darkdata.service;

import org.apache.jena.rdf.model.Resource;

/**
 * @author szednik
 */

interface ScoringService<S extends Resource,T extends Resource> {

    S score(T candidate);
}
