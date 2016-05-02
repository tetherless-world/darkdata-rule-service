package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.ontology.DarkData;
import darkdata.transformers.DataVariableAPI2KBConverter;
import darkdata.transformers.EventConverter;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.Event;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */

@Service
public class GenerateCandidateWorkflowService
        implements CandidateFactory<Model, CandidateWorkflowCriteria> {

    @Autowired
    private DataVariableAPI2KBConverter dataVariableAPI2KBConverter;

    @Autowired
    private EventConverter eventConverter;

    @Autowired
    private DarkDataDatasource datasource;

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
    public Model generateCandidates(CandidateWorkflowCriteria criteria) {
        final OntModel m = ModelFactory.createOntologyModel();
        final OntModel owl = datasource.createOntModel();
        owl.addSubModel(m);
        getRequestCriteria(m, criteria);
        return candidateGenerationReasoningService.reason(owl);
    }

    // TODO put this in new service? (RequestCriteriaFactory) which returns Individual (in Model m)?
    private Individual getRequestCriteria(final OntModel m, final CandidateWorkflowCriteria criteria) {
        Individual req = m.createIndividual(DarkData.RequestCriteria);

        // make sure the event exists and has categories
        Event event = Optional.ofNullable(criteria.getEvent())
                .orElseGet(() -> { Event e = new Event(); e.setCategories(criteria.getCategories()); return e; });

        // convert to event RDF and add to criteria RDF
        eventConverter.convert(m, event)
                .ifPresent(e -> req.addProperty(DarkData.criteriaEvent, e.getIndividual()));

        // add variables (if any)
        criteria.getVariables().stream()
                .map(v -> dataVariableAPI2KBConverter.convert(m, v))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(v -> req.addProperty(DarkData.criteriaDataField, v.getIndividual()));

        return req;
    }
}
