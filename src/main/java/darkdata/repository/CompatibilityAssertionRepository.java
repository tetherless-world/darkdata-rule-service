package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
