package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCTerms;
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

    @Cacheable("datafields")
    public Optional<DataVariable> getByIdentifier(String identifier) {
        return datasource.getOntModel()
                .listIndividuals(DarkData.DataVariable)
                .toList().stream()
                .filter(i -> i.hasProperty(DCTerms.identifier, ResourceFactory.createTypedLiteral(identifier)))
                .map(DataVariable::new)
                .findFirst();
    }

    @Cacheable("datafields")
    public List<DataVariable> list() {
        return datasource.getOntModel()
                .listIndividuals(DarkData.DataVariable)
                .toList().stream()
                .map(DataVariable::new)
                .collect(Collectors.toList());
    }
}
