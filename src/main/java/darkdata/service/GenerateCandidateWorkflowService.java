package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.kb.g4.G4Service;
import darkdata.repository.CandidateWorkflowRepository;
import darkdata.repository.EventRepository;
import darkdata.repository.G4ServiceRepository;
import darkdata.repository.PhenomenaRepository;
import darkdata.transformers.DataVariableAPI2KBConverter;
import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.EventCategory;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class GenerateCandidateWorkflowService
        implements CandidateFactory<CandidateWorkflow, CandidateWorkflowCriteria> {

    @Autowired
    private PhenomenaRepository phenomenaRepository;

    @Autowired
    private G4ServiceRepository g4ServiceRepository;

    @Autowired
    private CandidateWorkflowRepository candidateWorkflowRepository;

    @Autowired
    private DataVariableAPI2KBConverter dataVariableAPI2KBConverter;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DarkDataDatasource datasource;

    /**
     * Generate candidate workflows
     * @param criteria candidate workflow criteria
     * @return List of CandidateWorkflow objects
     * @see CandidateWorkflow
     */
    @Override
    public List<CandidateWorkflow> generate(CandidateWorkflowCriteria criteria) {

        List<CandidateWorkflow> candidateWorkflows = new ArrayList<>();

        String eventLink = criteria.getEvent().getLink();
        List<DataVariable> variables = criteria.getVariables();
        List<G4Service> g4services = g4ServiceRepository.listInstances();

        List<OntClass> phenomenaList = criteria.getEvent().getCategories().stream()
                .map(EventCategory::getText)
                .map(phenomenaRepository::listClassesByTopic)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        // create OntModel with OWL DL reasoning for candidates
        OntModel m = datasource.createOntModel();

        for(OntClass phenomenaClass : phenomenaList) {
            for (G4Service g4service : g4services) {
                for (DataVariable variable : variables) {

                    Phenomena event = eventRepository.createEvent(m, eventLink, phenomenaClass).get();

                    for(PhysicalFeature feature : event.getPhysicalFeatures()) {

                        darkdata.model.kb.DataVariable var = dataVariableAPI2KBConverter.convert(variable).get();

                        String candidate_uri = "urn:candidate-workflow/" + UUID.randomUUID().toString();
                        CandidateWorkflow candidate = candidateWorkflowRepository.createCandidateWorkflow(m, candidate_uri).get();

                        candidate.setEvent(event);
                        candidate.setFeature(feature);
                        candidate.setService(g4service);
                        candidate.addVariable(var);

                        // TODO add variables in separate following component?
                        // 1-variable for some services
                        // 2-variables for comparison services

                        candidateWorkflows.add(candidate);
                    }
                }
            }
        }

        m.prepare();
        return candidateWorkflows;
    }
}
