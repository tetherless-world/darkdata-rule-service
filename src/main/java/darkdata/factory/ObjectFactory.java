package darkdata.factory;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import java.util.Optional;

/**
 * @author szednik
 */
interface ObjectFactory<T, M extends Model, C extends Resource> {

    Optional<T> get(final M model, final C source);
}
