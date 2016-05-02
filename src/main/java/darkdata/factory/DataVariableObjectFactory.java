package darkdata.factory;

import darkdata.web.api.datavariable.DataVariable;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
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
public class DataVariableObjectFactory implements ObjectFactory<DataVariable, Model, Resource> {

    private static final Logger logger = LoggerFactory.getLogger(DataVariableObjectFactory.class);

    @Override
    public Optional<DataVariable> get(final Model model, final Resource resource) {

        if(resource == null) {
            return Optional.empty();
        }

        DataVariable datafield = new DataVariable();

        String identifier = getIdentifier(model, resource).orElse("UNKNOWN");
        datafield.setIdentifier(identifier);

        // TODO shortName
        // TODO version
        // TODO keywords

        return Optional.of(datafield);
    }

    private static Optional<String> getIdentifier(final Model m, final Resource datafield) {
        return m.listObjectsOfProperty(datafield, DCTerms.identifier).toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getString)
                .findAny();
    }
}
