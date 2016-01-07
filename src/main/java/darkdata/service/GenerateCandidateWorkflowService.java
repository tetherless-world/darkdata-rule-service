package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Dataset;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.kb.candidate.CandidateWorkflowCriteria;
import darkdata.model.kb.g4.G4Service;
import darkdata.repository.*;
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

//    @Autowired
//    private PhysicalFeatureRepository featureRepository;

    @Autowired
    private G4ServiceRepository g4ServiceRepository;

    @Autowired
    private CandidateWorkflowRepository candidateWorkflowRepository;

    @Autowired
    private DataVariableRepository variableRepository;

    @Autowired
    private DatasetRepository datasetRepository;

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

        // create OntModel for candidates
        OntModel m = datasource.createOntModel();

        for(OntClass phenomenaClass : phenomenaList) {
            for (G4Service g4service : g4services) {
                for (DataVariable variable : variables) {

                    darkdata.model.kb.DataVariable var = transform(variable);

                    String candidate_uri = "urn:candidate-workflow/" + UUID.randomUUID().toString();
                    CandidateWorkflow candidate = candidateWorkflowRepository.createCandidateWorkflow(m, candidate_uri).get();

                    Phenomena event = eventRepository.createEvent(m, eventLink, phenomenaClass).get();
                    candidate.setEvent(event);

                    candidate.setService(g4service);
                    candidate.addVariable(var);

                    // TODO add variables in separate following component?
                    // 1-variable for some services
                    // 2-variables for comparison services

                    candidateWorkflows.add(candidate);
                }
            }
        }

        m.prepare();
        return candidateWorkflows;
    }

    // TODO put into own transformer class
    private darkdata.model.kb.DataVariable transform(DataVariable variable) {

        String varId = variable.getProduct()+"_"+variable.getVersion()+"_"+variable.getVariable();
        String varURI = "urn:variable/"+varId;

        darkdata.model.kb.DataVariable var = variableRepository.createDataVariable(varURI).get();

        String datasetID = variable.getProduct()+"_"+variable.getVersion();
        String datasetURI = "urn:dataset/"+datasetID;

        Dataset dataset = datasetRepository.createDataset(datasetURI).get();
        dataset.setShortName(variable.getProduct());

        var.setShortName(variable.getVariable());
        var.setDataset(dataset);

        return var;
    }
}
