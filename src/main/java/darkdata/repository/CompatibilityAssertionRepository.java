package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
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

    public Optional<CompatibilityAssertion> createCandidateAssertion(String uri) {

        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        m.addSubModel(datasource.getOntModel().getBaseModel());
        try {
            return Optional.ofNullable(m.createIndividual(uri, DarkData.CompatibilityAssertion)).map(CompatibilityAssertion::new);
        } finally {
            m.removeSubModel(datasource.getOntModel());
        }
    }

}
