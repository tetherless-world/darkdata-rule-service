package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.Phenomena;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class PhenomenaRepository {

    @Autowired
    private DarkDataDatasource datasource;

    /**
     * Returns a class with the given URI
     * @param uri the URI of the class to get
     * @return Optional object containing the OntClass (or empty if no class is found)
     * @see OntClass
     */
    public Optional<OntClass> getClass(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getOntClass(uri));
    }

    /**
     * Returns a dd:Phenomena subclass with the given label
     * @param label the label of the class to get
     * @return Optional object containing the OntClass (or empty if no class is found)
     * @see OntClass
     */
    public Optional<OntClass> getClassByLabel(String label) {
        return listSubclasses().stream()
                .filter(c -> c.getLabel("en").equals(label))
                .findAny();
    }

    /**
     * Returns a list of dd:Phenomena subclasses associated with the given topic
     * @param topic EONET category name (or similar) associated with phenomena subclass
     * @return List of OntClass objects
     * @see OntClass
     */
    public List<OntClass> listClassesByTopic(String topic) {
        return listSubclasses()
                .stream()
                .filter(c -> c.hasProperty(DarkData.topic, topic))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of subclasses of type dd:Phenomena
     * @return List of OntClass objects
     * @see OntClass
     */
    public List<OntClass> listSubclasses() {
        return datasource.getOntModel()
                .getOntClass(DarkData.Phenomena.getURI())
                .listSubClasses()
                .toList()
                .stream()
                .filter(c->!c.isAnon())
                .collect(Collectors.toList());
    }
}
