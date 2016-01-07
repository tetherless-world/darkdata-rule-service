package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Phenomena;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
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
    public Optional<Phenomena> createEvent(String uri, OntClass phenomena) {

        Optional<OntClass> phenomenaClass = phenomenaRepository.getClass(phenomena.getURI());
        if(!phenomenaClass.isPresent()
                || !phenomenaRepository.listSubclasses().contains(phenomenaClass.get())) {
            return Optional.empty();
        }

        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, phenomena))
                .map(Phenomena::new);
    }

    public Optional<Phenomena> createEvent(OntModel m, String uri, OntClass phenomena) {

        Optional<OntClass> phenomenaClass = phenomenaRepository.getClass(phenomena.getURI());
        if(!phenomenaClass.isPresent()
                || !phenomenaRepository.listSubclasses().contains(phenomenaClass.get())) {
            return Optional.empty();
        }

        return Optional.ofNullable(m.createIndividual(uri, phenomena))
                .map(Phenomena::new);
    }

    /**
     * Returns a List of Individuals with rdf:type dd:Phenomena
     * @return List of Individual objects
     * @see Individual
     */
    public List<Phenomena> listEvents() {
        return datasource.getOntModel()
                .getOntClass(DarkData.Phenomena.getURI())
                .listInstances()
                .toList()
                .stream()
                .filter(c -> !c.isAnon())
                .map(OntResource::asIndividual)
                .map(Phenomena::new)
                .collect(Collectors.toList());
    }

}
