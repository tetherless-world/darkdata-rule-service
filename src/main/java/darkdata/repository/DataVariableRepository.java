package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Resource;
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
public class DataVariableRepository {

    @Autowired
    private DarkDataDatasource datasource;

    public Optional<DataVariable> createDataVariable(String uri) {
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.DataVariable))
                .map(DataVariable::new);
    }

    public Optional<DataVariable> createDataVariable(OntModel m, String uri) {
        return Optional.ofNullable(m.createIndividual(uri, DarkData.DataVariable))
                .map(DataVariable::new);
    }

    public Optional<DataVariable> getByURI(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getIndividual(uri))
                .map(DataVariable::new);
    }

    public Optional<DataVariable> getByIdentifier(String identifier) {
        return datasource.getOntModel()
                .listSubjectsWithProperty(DCTerms.identifier, ResourceFactory.createTypedLiteral(identifier))
                .toList()
                .stream()
                .filter(c -> !c.isAnon())
                .map(Resource::getURI)
                .map(this::getByURI)
                .map(Optional::get)
                .findAny();
    }

    public List<DataVariable> listDataVariables() {
        return datasource.getOntModel()
                .getOntClass(DarkData.DataVariable.getURI())
                .listInstances()
                .toList()
                .stream()
                .filter(c -> !c.isAnon())
                .map(OntResource::asIndividual)
                .map(DataVariable::new)
                .collect(Collectors.toList());
    }
}
