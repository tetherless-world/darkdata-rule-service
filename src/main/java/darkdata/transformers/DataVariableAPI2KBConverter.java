package darkdata.transformers;

import darkdata.model.kb.Dataset;
import darkdata.repository.DataVariableRepository;
import darkdata.repository.DatasetRepository;
import darkdata.web.api.datavariable.DataVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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

    // do I need to set m?

    @Override
    public Optional<darkdata.model.kb.DataVariable> convert(DataVariable variable) {

        if(variable == null) {
            return Optional.empty();
        }

        String varId = variable.getProduct()+"_"+variable.getVersion()+"_"+variable.getVariable();
        String varURI = "urn:variable/"+varId;

        darkdata.model.kb.DataVariable var = variableRepository.createDataVariable(varURI).get();

        String datasetID = variable.getProduct()+"_"+variable.getVersion();
        String datasetURI = "urn:dataset/"+datasetID;

        Dataset dataset = datasetRepository.createDataset(datasetURI).get();
        dataset.setShortName(variable.getProduct());

        var.setShortName(variable.getVariable());
        var.setDataset(dataset);

        return Optional.of(var);
    }
}
