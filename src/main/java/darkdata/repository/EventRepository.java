package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class EventRepository {

    @Autowired
    private DarkDataDatasource datasource;

    @Autowired
    private PhenomenaRepository phenomenaRepository;

    /**
     * Returns an instance of the specified phenomena subclass
     * @param uri the URI for the created instance
     * @param phenomena the type of the created instance, should be a phenomena subclass
     * @return Optional object containing the created instance
     * @see Individual
     */
    public Optional<Individual> createEvent(String uri, OntClass phenomena) {

        Optional<OntClass> phenomenaClass = phenomenaRepository.getClass(phenomena.getURI());
        if(!phenomenaClass.isPresent()
                || !phenomenaRepository.listSubclasses().contains(phenomenaClass.get())) {
            return Optional.empty();
        }

        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        m.addSubModel(datasource.getOntModel().getBaseModel());
        try {
            return Optional.ofNullable(m.createIndividual(uri, phenomena));
        } finally {
            m.removeSubModel(datasource.getOntModel());
        }
    }

    /**
     * Returns a List of Individuals with rdf:type dd:Phenomena
     * @return List of Individual objects
     * @see Individual
     */
    public List<Individual> listEvents() {
        return datasource.getOntModel()
                .getOntClass(DarkData.Phenomena.getURI())
                .listInstances()
                .toList()
                .stream()
                .filter(c -> !c.isAnon())
                .map(OntResource::asIndividual)
                .collect(Collectors.toList());
    }

}
