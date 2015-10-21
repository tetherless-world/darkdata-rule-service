package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.candidate.CandidateWorkflow;
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
public class CandidateWorkflowRepository {

    @Autowired
    private DarkDataDatasource datasource;

    /**
     * Returns a candidate workflow object
     * @param uri the URI for the created instance
     * @return Optional object containing the created candidate workflow proxy object
     * @see CandidateWorkflow
     */
    public Optional<CandidateWorkflow> createCandidateWorkflow(String uri) {

        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        m.addSubModel(datasource.getOntModel().getBaseModel());
        try {
            return Optional.ofNullable(m.createIndividual(uri, DarkData.CandidateWorkflow)).map(CandidateWorkflow::new);
        } finally {
            m.removeSubModel(datasource.getOntModel());
        }
    }

}
