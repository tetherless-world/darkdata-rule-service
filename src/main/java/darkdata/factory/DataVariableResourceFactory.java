package darkdata.factory;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.VersionedDataProduct;
import darkdata.repository.DataVariableRepository;
import darkdata.repository.DatasetRepository;
import darkdata.web.api.datavariable.DataVariable;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */

@Component
public class DataVariableResourceFactory implements ResourceFactory<OntResource, OntModel, DataVariable> {

    @Autowired
    private DataVariableRepository variableRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataVariableResourceFactory.class);

    @Override
    public Optional<OntResource> get(final OntModel model, final DataVariable variable) {

        if(variable == null) {
            return Optional.empty();
        }

        String varId = (variable.getIdentifier() != null) ?
                variable.getIdentifier() :
                variable.getProduct()+"_"+variable.getVersion()+"_"+variable.getVariable();

        Optional<darkdata.model.kb.DataVariable> var = variableRepository.getByIdentifier(varId);

        if(var.isPresent()) {
            return var.map(IndividualProxy::getIndividual);
        }

        String varURI = "http://darkdata.tw.rpi.edu/data/datafield/"+varId;
        Optional<darkdata.model.kb.DataVariable> var2 = variableRepository.createDataVariable(model, varURI);

        Optional<VersionedDataProduct> dataset = getDataset(model, variable.getProduct(), variable.getVersion());
        var2.ifPresent(v -> v.setShortName(variable.getVariable()));
        dataset.ifPresent(d -> var2.ifPresent(v -> v.setDataset(d)));

        return var2.map(IndividualProxy::getIndividual);
    }

    private Optional<VersionedDataProduct> getDataset(final OntModel m, final String shortname, final String version) {

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
