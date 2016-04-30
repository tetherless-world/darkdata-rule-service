package darkdata.transformers;

import darkdata.web.api.datavariable.DataVariable;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class DataVariableConverter implements Converter<Resource, Optional<DataVariable>> {

    private static final Logger logger = LoggerFactory.getLogger(DataVariableConverter.class);

    private Model model;

    @Override
    public Optional<DataVariable> convert(Resource resource) {

        if(resource == null) {
            return Optional.empty();
        }

        DataVariable datafield = new DataVariable();

        String identifier = getIdentifier(resource).orElse("UNKNOWN");
        datafield.setIdentifier(identifier);

        // TODO shortName
        // TODO version
        // TODO keywords

        return Optional.of(datafield);
    }

    private Optional<String> getIdentifier(Resource datafield) {
        return Optional.ofNullable(model.listObjectsOfProperty(datafield, DCTerms.identifier).next().asLiteral().getString());
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
