package darkdata.transformers;

import darkdata.web.api.datavariable.DataVariable;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */
@Component
public class DataVariableConverter {

    private static final Logger logger = LoggerFactory.getLogger(DataVariableConverter.class);

    public Optional<DataVariable> convert(Model m, Resource resource) {

        if(resource == null) {
            return Optional.empty();
        }

        DataVariable datafield = new DataVariable();

        String identifier = getIdentifier(m, resource).orElse("UNKNOWN");
        datafield.setIdentifier(identifier);

        // TODO shortName
        // TODO version
        // TODO keywords

        return Optional.of(datafield);
    }

    private Optional<String> getIdentifier(Model m, Resource datafield) {
        return Optional.ofNullable(m.listObjectsOfProperty(datafield, DCTerms.identifier).next().asLiteral().getString());
    }
}
