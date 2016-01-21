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

        String varId = variable.getProduct()+"_"+variable.getVersion()+"_"+variable.getVariable();
        String varURI = "urn:variable/"+varId;

        darkdata.model.kb.DataVariable var = variableRepository.createDataVariable(ontModel, varURI).get();

        String datasetID = variable.getProduct()+"_"+variable.getVersion();
        String datasetURI = "urn:dataset/"+datasetID;

        Dataset dataset = datasetRepository.createDataset(ontModel, datasetURI).get();
        dataset.setShortName(variable.getProduct());
        Assert.isTrue(dataset.getShortName().isPresent(), "dataset should have a short name");

        var.setShortName(variable.getVariable());
        Assert.isTrue(var.getShortName().isPresent(), "variable should have a short name");

        var.setDataset(dataset);
        Assert.isTrue(var.getDataset().isPresent(), "variable should have an associated dataset");

        return Optional.of(var);
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    public void setOntModel(OntModel ontModel) {
        this.ontModel = ontModel;
    }
}
