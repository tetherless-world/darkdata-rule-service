package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class G4ServiceRepository {

    @Autowired
    private DarkDataDatasource datasource;

    /**
     * Returns a List of subclass of dd:Visualization
     * @return List of OntClass objects
     * @see OntClass
     */
    @Cacheable("services")
    public List<OntClass> listSubclasses() {
        return datasource.getOntModel()
                .getOntClass(DarkData.Visualization.getURI())
                .listSubClasses()
                .toList();
    }

    /**
     * Returns a List of Individuals with rdf:type dd:Visualization
     * @return List of Individual objects
     * @see Individual
     */

    @Cacheable("services")
    public List<G4Service> listInstances() {
        return datasource.getOntModel()
                .listIndividuals(DarkData.Visualization)
                .toList().stream()
                .map(G4Service::new)
                .collect(Collectors.toList());
    }

    @Cacheable("services")
    public Optional<G4Service> getByURI(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getIndividual(uri))
                .map(G4Service::new);
    }

    @Cacheable("services")
    public Optional<G4Service> getByIdentifier(String identifier) {
        return datasource.getOntModel()
                .listIndividuals(DarkData.Visualization)
                .toList().stream()
                .filter(i -> i.hasProperty(DCTerms.identifier, ResourceFactory.createTypedLiteral(identifier)))
                .map(G4Service::new)
                .findAny();
    }

    @CacheEvict(value = "services", allEntries = true)
    public Optional<G4Service> createService(String uri) {
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.Visualization))
                .map(G4Service::new);
    }

}
