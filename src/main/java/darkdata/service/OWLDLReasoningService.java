package darkdata.service;

import darkdata.datasource.DarkDataDatasource;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author szednik
 */

@Service
public class OWLDLReasoningService implements ReasoningService {

    @Autowired
    private DarkDataDatasource datasource;

    @Override
    public OntModel reason(OntModel m) {
        OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF, m);
        inf.addSubModel(datasource.getSchema());
        inf.prepare();
        return inf;
    }

}
