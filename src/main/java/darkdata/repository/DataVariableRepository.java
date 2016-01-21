package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.ontology.DarkData;
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
