package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.VersionedDataProduct;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service

public class DatasetRepository {

    @Autowired
    private DarkDataDatasource datasource;

    public Optional<VersionedDataProduct> createDataset(String uri) {
        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, DarkData.Dataset))
                .map(VersionedDataProduct::new);
    }

    public Optional<VersionedDataProduct> createDataset(OntModel m, String uri) {
        return Optional.ofNullable(m.createIndividual(uri, DarkData.Dataset))
                .map(VersionedDataProduct::new);
    }

    public Optional<VersionedDataProduct> getByURI(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getIndividual(uri))
                .map(VersionedDataProduct::new);
    }

    public Optional<VersionedDataProduct> getByShortName(String shortName) {
        return datasource.getOntModel()
                .listIndividuals(DarkData.VersionedDataProduct)
                .toList().stream()
                .filter(i -> i.hasProperty(RDFS.label, ResourceFactory.createTypedLiteral(shortName)))
                .map(VersionedDataProduct::new)
                .findFirst();
    }

    public List<VersionedDataProduct> list() {
        return datasource.getOntModel()
                .listIndividuals(DarkData.VersionedDataProduct)
                .toList().stream()
                .map(VersionedDataProduct::new)
                .collect(Collectors.toList());
    }
}
