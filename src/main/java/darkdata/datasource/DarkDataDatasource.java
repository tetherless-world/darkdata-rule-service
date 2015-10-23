package darkdata.datasource;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author szednik
 */

@Service
public class DarkDataDatasource {

    private OntModel ontModel;

    @Value("classpath:rdf/darkdata.ttl")
    Resource darkDataOntology;

    @Value("classpath:rdf/sciencekeywords.ttl")
    Resource gcmdScienceKeywords;

    final String GCMD_SCIENCE_KEYWORDS_BASE = "http://gcmdservices.gsfc.nasa.gov/kms/concepts/concept_scheme/sciencekeywords";

    private static InputStream getInputStream(Resource resource) {
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void setup() {

        OntModel schema = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        schema.read(getInputStream(darkDataOntology), DarkData.getURI(),"TURTLE");

        Model scienceKeywords = ModelFactory.createDefaultModel();
        scienceKeywords.read(getInputStream(gcmdScienceKeywords), GCMD_SCIENCE_KEYWORDS_BASE, "TURTLE");

        //ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF, schema);
        ontModel = ModelFactory.createOntologyModel();
        ontModel.addSubModel(schema);
        ontModel.addSubModel(scienceKeywords);
        ontModel.prepare();
    }

    public OntModel getOntModel() {
        return ontModel;
    }
}
