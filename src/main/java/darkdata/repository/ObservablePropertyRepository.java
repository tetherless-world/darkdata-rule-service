package darkdata.repository;

import darkdata.model.ontology.DarkData;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class ObservablePropertyRepository {

    public List<Resource> listObservablePropertiesOfFeature(Resource feature) {
        return feature.listProperties(DarkData.observableProperty)
                .toList().stream()
                .map(Statement::getObject)
                .map(RDFNode::asResource)
                .collect(Collectors.toList());
    }

}
