package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
