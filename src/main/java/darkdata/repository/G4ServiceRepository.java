package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.springframework.beans.factory.annotation.Autowired;
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
    // TODO cache this
    public List<G4Service> listInstances() {
        return datasource.getOntModel()
                .getOntClass(DarkData.Visualization.getURI())
                .listInstances()
                .toList()
                .stream()
                .filter(c -> !c.isAnon())
                .map(OntResource::asIndividual)
                .map(G4Service::new)
                .collect(Collectors.toList());
    }

    public Optional<G4Service> getByURI(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getIndividual(uri))
                .map(G4Service::new);
    }

    public Optional<G4Service> getByIdentifier(String identifier) {
        return datasource.getOntModel()
                .listSubjectsWithProperty(DCTerms.identifier, ResourceFactory.createTypedLiteral(identifier))
                .toList()
                .stream()
                .filter(c -> !c.isAnon())
                .map(c -> getByURI(c.getURI()).get())
                .findAny();
    }

    public Optional<G4Service> createService(String uri) {
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.Visualization))
                .map(G4Service::new);
    }

}
