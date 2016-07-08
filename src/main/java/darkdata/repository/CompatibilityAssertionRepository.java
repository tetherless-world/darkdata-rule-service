package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class CompatibilityAssertionRepository {

    @Autowired
    private DarkDataDatasource datasource;

    public Optional<CompatibilityAssertion> createCompatibilityAssertion(String uri) {
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.CompatibilityAssertion))
                .map(CompatibilityAssertion::new);
    }

    public List<Resource> getCompatibilityAssertionsForCandidate(Model m, Resource candidate) {
        return m.listObjectsOfProperty(candidate, DarkData.compatibilityAssertion)
                .toList().stream()
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }

}
