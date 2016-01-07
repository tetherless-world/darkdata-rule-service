package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import jena.schemagen;
import org.apache.jena.ontology.OntModel;
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
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.CandidateWorkflow))
                .map(CandidateWorkflow::new);
    }

    public Optional<CandidateWorkflow> createCandidateWorkflow(OntModel m, String uri) {
        return Optional.ofNullable(m.createIndividual(uri, DarkData.CandidateWorkflow))
                .map(CandidateWorkflow::new);
    }

}
