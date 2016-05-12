package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.factory.CandidateWorkflowCriteriaResourceFactory;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author szednik
 */

@Service
public class GenerateCandidateWorkflowService
        implements CandidateFactory<InfModel, CandidateWorkflowCriteria> {

    @Autowired
    private DarkDataDatasource datasource;

    @Autowired
    private CandidateWorkflowCriteriaResourceFactory candidateCriteriaResourceFactory;

    @Autowired
    private RuleBasedReasoningService candidateGenerationReasoningService;

    private static final Logger logger = LoggerFactory.getLogger(GenerateCandidateWorkflowService.class);

    /**
     * Generate candidate workflows and add them to the returned Model
     * @param criteria candidate workflow criteria
     * @return Model
     * @see CandidateWorkflowCriteria
     * @see Model
     */
    @Override
    public InfModel generateCandidates(CandidateWorkflowCriteria criteria) {
        final OntModel m = ModelFactory.createOntologyModel();
        final OntModel owl = datasource.createOntModel();
        owl.addSubModel(m);
        candidateCriteriaResourceFactory.get(m, criteria);
        return candidateGenerationReasoningService.reason(owl);
    }
}
