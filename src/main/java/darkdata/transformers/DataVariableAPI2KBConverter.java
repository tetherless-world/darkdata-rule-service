package darkdata.transformers;

import darkdata.model.kb.Dataset;
import darkdata.repository.DataVariableRepository;
import darkdata.repository.DatasetRepository;
import darkdata.web.api.datavariable.DataVariable;
import org.apache.jena.ontology.OntModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author szednik
 */

@Component
public class DataVariableAPI2KBConverter implements Converter<DataVariable, Optional<darkdata.model.kb.DataVariable>> {

    @Autowired
    private DataVariableRepository variableRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    private OntModel ontModel;

    private static final Logger logger = LoggerFactory.getLogger(DataVariableAPI2KBConverter.class);

    @Override
    public Optional<darkdata.model.kb.DataVariable> convert(DataVariable variable) {

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
        Optional<darkdata.model.kb.DataVariable> var2 = variableRepository.createDataVariable(ontModel, varURI);

        Optional<Dataset> dataset = getDataset(variable.getProduct(), variable.getVersion());
        var2.ifPresent(v -> v.setShortName(variable.getVariable()));
        dataset.ifPresent(d -> var2.ifPresent(v -> v.setDataset(d)));

        return var2;
    }

    private Optional<Dataset> getDataset(String shortname, String version) {

        String datasetID = shortname+"."+version;
        Optional<Dataset> dataset = datasetRepository.getByIdentifier(datasetID);

        if(!dataset.isPresent()) {
            String datasetURI = "http://darkdata.tw.rpi.edu/data/versioned-product/"+datasetID;
            dataset = datasetRepository.createDataset(ontModel, datasetURI);
            dataset.ifPresent(d -> d.setShortName(shortname));
//            dataset.ifPresent(d -> d.setVersion(version));
        }

        return dataset;
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    public void setOntModel(OntModel ontModel) {
        this.ontModel = ontModel;
    }
}
