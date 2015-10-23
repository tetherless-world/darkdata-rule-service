package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.Phenomena;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
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

    public Optional<DataVariable> createDataVariable(String uri, OntClass variableClass) {
        // TODO add check that variableClass is a subclass of DataVariable
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        m.addSubModel(datasource.getOntModel().getBaseModel());
        try {
            return Optional.ofNullable(m.createIndividual(uri, variableClass)).map(DataVariable::new);
        } finally {
            m.removeSubModel(datasource.getOntModel());
        }
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
