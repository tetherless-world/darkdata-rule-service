package darkdata.transformers;

import darkdata.model.kb.DataProduct;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.VersionedDataProduct;
import darkdata.model.kb.IndividualProxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class DataVariableConverter implements Converter<DataVariable, Optional<darkdata.web.api.datavariable.DataVariable>> {

    private static final Logger logger = LoggerFactory.getLogger(DataVariableConverter.class);

    @Override
    public Optional<darkdata.web.api.datavariable.DataVariable> convert(DataVariable dataVariable) {

        if(dataVariable == null) {
            return Optional.empty();
        }

        logger.trace("in DataVariableConverter::convert with {}", dataVariable.getIndividual().getURI());

        darkdata.web.api.datavariable.DataVariable var = new darkdata.web.api.datavariable.DataVariable();

        Assert.isTrue(dataVariable.getIdentifier().isPresent(), "kb data variable is missing identifier");
        dataVariable.getIdentifier().ifPresent(var::setIdentifier);
        Assert.hasText(var.getIdentifier(), "api data variable is missing identifier");

        dataVariable.getVersionedDataProduct()
                .flatMap(VersionedDataProduct::getDataProduct)
                .flatMap(DataProduct::getShortName)
                .ifPresent(var::setProduct);

        dataVariable.getShortName()
                .ifPresent(var::setVariable);

        Assert.isTrue(dataVariable.getVersionedDataProduct().isPresent(), "data variable does not have dataset");

        dataVariable.getVersionedDataProduct()
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
