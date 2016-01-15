package darkdata.transformers;

import darkdata.model.kb.DataVariable;
import darkdata.model.kb.Dataset;
import darkdata.model.kb.IndividualProxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class DataVariableConverter implements Converter<DataVariable, Optional<darkdata.web.api.datavariable.DataVariable>> {

    @Override
    public Optional<darkdata.web.api.datavariable.DataVariable> convert(DataVariable dataVariable) {

        if(dataVariable == null) {
            return Optional.empty();
        }

        darkdata.web.api.datavariable.DataVariable var = new darkdata.web.api.datavariable.DataVariable();

        dataVariable.getDataset()
                .flatMap(Dataset::getShortName)
                .ifPresent(var::setProduct);

        dataVariable.getShortName()
                .ifPresent(var::setVariable);

        dataVariable.getDataset()
                .map(IndividualProxy::getIndividual)
                .map(Resource::getURI)
                .flatMap(this::getVersionFromDatasetURI)
                .ifPresent(var::setVersion);

        return Optional.of(var);
    }

    public Optional<String> getVersionFromDatasetURI(String uri) {

        if(StringUtils.isBlank(uri)) {
            return Optional.empty();
        }

        int index = uri.lastIndexOf('_');

        if(index == -1) {
            return Optional.empty();
        }

        String version = uri.substring(index+1);
        return Optional.of(version);
    }
}