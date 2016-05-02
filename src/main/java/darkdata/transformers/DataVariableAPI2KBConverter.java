package darkdata.transformers;

import darkdata.model.kb.VersionedDataProduct;
import darkdata.repository.DataVariableRepository;
import darkdata.repository.DatasetRepository;
import darkdata.web.api.datavariable.DataVariable;
import org.apache.jena.ontology.OntModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */

@Component
public class DataVariableAPI2KBConverter {

    @Autowired
    private DataVariableRepository variableRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataVariableAPI2KBConverter.class);

    public Optional<darkdata.model.kb.DataVariable> convert(OntModel m, DataVariable variable) {

        if(variable == null) {
            return Optional.empty();
        }

        String varId = (variable.getIdentifier() != null) ?
                variable.getIdentifier() :
                variable.getProduct()+"_"+variable.getVersion()+"_"+variable.getVariable();

        Optional<darkdata.model.kb.DataVariable> var = variableRepository.getByIdentifier(varId);
        if(var.isPresent()) {
            return var;
        }

        String varURI = "http://darkdata.tw.rpi.edu/data/datafield/"+varId;
        Optional<darkdata.model.kb.DataVariable> var2 = variableRepository.createDataVariable(m, varURI);

        Optional<VersionedDataProduct> dataset = getDataset(m, variable.getProduct(), variable.getVersion());
        var2.ifPresent(v -> v.setShortName(variable.getVariable()));
        dataset.ifPresent(d -> var2.ifPresent(v -> v.setDataset(d)));

        return var2;
    }

    private Optional<VersionedDataProduct> getDataset(OntModel m, String shortname, String version) {

        String datasetID = shortname+"."+version;
        Optional<VersionedDataProduct> dataset = datasetRepository.getByShortName(datasetID);

        if(!dataset.isPresent()) {
            String datasetURI = "http://darkdata.tw.rpi.edu/data/versioned-product/"+datasetID;
            dataset = datasetRepository.createDataset(m, datasetURI);
            dataset.ifPresent(d -> d.setShortName(shortname));
            dataset.ifPresent(d -> d.setVersion(version));
        }

        return dataset;
    }
}
