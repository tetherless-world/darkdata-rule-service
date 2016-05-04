package darkdata.factory;

import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.ontology.DarkData;
import darkdata.web.api.event.eonet.Event;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author szednik
 */
@Service
public class CandidateWorkflowCriteriaResourceFactory implements ResourceFactory<Individual, OntModel, CandidateWorkflowCriteria> {

    @Autowired
    private EventResourceFactory eventResourceFactory;

    @Autowired
    private DataVariableResourceFactory dataVariableResourceFactory;

    @Override
    public Optional<Individual> get(final OntModel model, final CandidateWorkflowCriteria criteria) {

        Individual req = model.createIndividual(DarkData.RequestCriteria);

        // make sure the event exists and has categories
        Event event = Optional.ofNullable(criteria.getEvent())
                .orElseGet(() -> { Event e = new Event(); e.setCategories(criteria.getCategories()); return e; });

        // get to event RDF and add to criteria RDF
        eventResourceFactory.get(model, event)
                .ifPresent(e -> req.addProperty(DarkData.criteriaEvent, e));

        // add variables (if any)
        criteria.getVariables().stream()
                .map(v -> dataVariableResourceFactory.get(model, v))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(v -> req.addProperty(DarkData.criteriaDataField, v));

        return Optional.ofNullable(req);
    }
}
