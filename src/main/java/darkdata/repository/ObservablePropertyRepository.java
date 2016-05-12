package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class ObservablePropertyRepository {

    @Autowired
    private DarkDataDatasource datasource;

    /** Returns a class with the given URI
     * @param uri the URI of the class to get
     * @return Optional object containing the OntClass (or empty if no class is found)
     * @see OntClass
     */
    public Optional<OntClass> getClass(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getOntClass(uri));
    }

    /**
     * Returns a dd:PhysicalFeature subclass with the given label
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
     * Returns a list of dd:ObservableProperty subclasses associated with the given science keyword
     * @param scienceKeyword science keyword associated with observable property subclass
     * @return List of OntClass objects
     * @see OntClass
     */
    public List<OntClass> listClassesByScienceKeyword(String scienceKeyword) {
        return listSubclasses()
                .stream()
                .filter(c -> c.hasProperty(DarkData.scienceKeyword, scienceKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of subclasses of type dd:ObservableProperty
     * @return List of OntClass objects
     * @see OntClass
     */
    @Cacheable("observableProperties")
    public List<OntClass> listSubclasses() {
        return datasource.getOntModel()
                .getOntClass(DarkData.ObservableProperty.getURI())
                .listSubClasses()
                .toList()
                .stream()
                .filter(c->!c.isAnon())
                .collect(Collectors.toList());
    }

    public List<Resource> listObservablePropertiesOfFeature(Resource feature) {
        return feature.listProperties(DarkData.observableProperty)
                .toList().stream()
                .map(Statement::getObject)
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }

}
