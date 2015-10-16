package darkdata.service;

import darkdata.model.CandidateWorkflow;
import darkdata.model.CandidateWorkflowCriteria;
import darkdata.model.datavariable.DataVariable;
import darkdata.model.event.eonet.Event;
import darkdata.model.event.eonet.EventCategory;
import darkdata.repository.G4ServiceRepository;
import darkdata.repository.PhenomenaRepository;
import darkdata.repository.PhysicalFeatureRepository;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    /*
     * Generate candidate workflows
     */
    @Override
    public List<CandidateWorkflow> generate(CandidateWorkflowCriteria criteria) {

        Event event = criteria.getEvent();
        List<DataVariable> variables = criteria.getVariables();
        List<Individual> g4services = g4ServiceRepository.listInstances();

        List<CandidateWorkflow> candidateWorkflows = new ArrayList<>();

        List<EventCategory> categories = event.getCategories();
        for( EventCategory category : categories) {
            String topic = category.getText();
            List<OntClass> phenomenaList = phenomenaRepository.listClassesByTopic(topic);
            for(OntClass phenomena : phenomenaList) {
                List<OntClass> features = featureRepository.listPhysicalManifestationOfPhenomena(phenomena);
                for(OntClass feature : features) {
                    for(DataVariable variable: variables) {
                        for (Individual g4service : g4services) {

                            String uuid = UUID.randomUUID().toString();
                            CandidateWorkflow candidate = new CandidateWorkflow(uuid, criteria);

                            candidate.setPhenomena(phenomena);
                            candidate.setPhysicalFeature(feature);
                            candidate.setService(g4service);

                            // TODO add variables in separate following component?
                            // 1-variable for some services
                            // 2-variables for comparison services
                            candidate.setVariables(Arrays.asList(variable));

                            candidateWorkflows.add(candidate);
                        }
                    }
                }
            }
        }

        return candidateWorkflows;
    }
}
