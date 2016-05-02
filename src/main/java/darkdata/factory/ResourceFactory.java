package darkdata.factory;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import java.util.Optional;

/**
 * @author szednik
 */
interface ResourceFactory<T extends Resource, M extends Model, C> {

    Optional<T> get(final M model, final C source);
}
