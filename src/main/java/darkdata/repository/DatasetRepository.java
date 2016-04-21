package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.Dataset;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author szednik
 */

@Service

public class DatasetRepository {

    @Autowired
    private DarkDataDatasource datasource;

    public Optional<Dataset> createDataset(String uri) {
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.Dataset))
                .map(Dataset::new);
    }

    public Optional<Dataset> createDataset(OntModel m, String uri) {
        return Optional.ofNullable(m.createIndividual(uri, DarkData.Dataset))
                .map(Dataset::new);
    }

    public Optional<Dataset> getByURI(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getIndividual(uri))
                .map(Dataset::new);
    }

    public Optional<Dataset> getByIdentifier(String identifier) {
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
}
