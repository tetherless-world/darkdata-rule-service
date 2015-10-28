package darkdata.service;

import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventCategory;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.kb.g4.G4Service;
import darkdata.repository.*;
import org.apache.jena.ontology.OntClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author szednik
 */

@Service
public class GenerateCandidateWorkflowService
        implements CandidateFactory<CandidateWorkflow, CandidateWorkflowCriteria> {

    @Autowired
    private PhenomenaRepository phenomenaRepository;

    @Autowired
    private PhysicalFeatureRepository featureRepository;

    @Autowired
    private G4ServiceRepository g4ServiceRepository;

    @Autowired
    private CandidateWorkflowRepository candidateWorkflowRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Generate candidate workflows
     * @param criteria candidate workflow criteria
     * @return List of CandidateWorkflow objects
     * @see CandidateWorkflow
     */
    @Override
    public List<CandidateWorkflow> generate(CandidateWorkflowCriteria criteria) {

        Event event = criteria.getEvent();
        List<DataVariable> variables = criteria.getVariables();
        List<G4Service> g4services = g4ServiceRepository.listInstances();
        List<CandidateWorkflow> candidateWorkflows = new ArrayList<>();

        // TODO re-write using a Java8 stream?

        List<EventCategory> categories = event.getCategories();
        for( EventCategory category : categories) {
            String topic = category.getText();
            List<OntClass> phenomenaList = phenomenaRepository.listClassesByTopic(topic);
            for(OntClass phenomena : phenomenaList) {
                List<OntClass> features = featureRepository.listPhysicalManifestationOfPhenomena(phenomena);
                for(OntClass feature : features) {
                    for(DataVariable variable: variables) {
                        for (G4Service g4service : g4services) {

                            String candidate_uri = "urn:candidate-workflow/"+UUID.randomUUID().toString();
                            CandidateWorkflow candidate = candidateWorkflowRepository.createCandidateWorkflow(candidate_uri).get();

                            Phenomena event2 = eventRepository.createEvent(event.getLink(), phenomena).get();
                            candidate.setEvent(event2);

                            //candidate.setPhenomena(phenomena);
                            //candidate.setPhysicalFeature(feature);
                            candidate.setService(g4service);

                            // TODO add variables in separate following component?
                            // 1-variable for some services
                            // 2-variables for comparison services
                            //candidate.setVariables(Arrays.asList(variable));

                            candidateWorkflows.add(candidate);
                        }
                    }
                }
            }
        }

        return candidateWorkflows;
    }
}
