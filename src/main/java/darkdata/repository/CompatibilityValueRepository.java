package darkdata.repository;

import darkdata.model.ontology.DarkData;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

/**
 * @author szednik
 */

@Service
public class CompatibilityValueRepository {

    public Resource getCompatibilityValue(Model m, Resource assertion) {
        return m.listObjectsOfProperty(assertion, DarkData.compatibilityValue)
                .toList().stream()
                .map(RDFNode::asResource)
                .findAny().orElse(DarkData.indifferent_compatibility);
    }
}
