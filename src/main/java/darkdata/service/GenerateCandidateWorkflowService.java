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
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Logger logger = LoggerFactory.getLogger(GenerateCandidateWorkflowService.class);

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

        List<OntClass> phenomenaList = getPhenomenaClasses(criteria.getEvent().getCategories());

        // create OntModel with OWL DL reasoning for candidates
        final OntModel inf = datasource.createOntModel();

        // create OntModel with no reasoning
        final OntModel m = ModelFactory.createOntologyModel();

        dataVariableAPI2KBConverter.setOntModel(m);

        List<Phenomena> events = phenomenaList.stream()
                .map(p -> eventRepository.createEvent(m, eventLink, p).get())
                .collect(Collectors.toList());

        inf.addSubModel(m);
        inf.prepare();

        for(Phenomena event : events) {
            List<PhysicalFeature> features = event.getPhysicalFeatures(inf);
            for(PhysicalFeature feature : features) {
                for (G4Service g4service : g4services) {
                    for (DataVariable variable : variables) {

                        String uri = "urn:candidate-workflow/" + UUID.randomUUID().toString();
                        List<CandidateWorkflow> newCandidates = Stream.of(uri)
                                .map(u -> candidateWorkflowRepository.createCandidateWorkflow(m, u).get())
                                .peek(c -> logger.debug("created candidate {}", c.getIndividual().getURI()))
                                .peek(c -> c.setEvent(event))
                                .peek(c -> c.setFeature(feature))
                                .peek(c -> c.setService(g4service))
                                .peek(c -> {
                                    darkdata.model.kb.DataVariable var = dataVariableAPI2KBConverter.convert(variable).get();
                                    c.addVariable(var);
                                })
                                .collect(Collectors.toList());

                        // TODO what to do in situation where service takes 2 variables?
                        candidateWorkflows.addAll(newCandidates);
                    }
                }
            }
        }

        m.prepare();
        return candidateWorkflows;
    }

    /**
     * Use EONET event category text to determine Phenomena classes
     * @param categories List of EventCategory
     * @return List of OntClass (subclasses of dd:Phenomena)
     */
    public List<OntClass> getPhenomenaClasses(List<EventCategory> categories) {
        return categories.stream()
                .map(EventCategory::getTitle)
                .map(phenomenaRepository::listClassesByTopic)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
