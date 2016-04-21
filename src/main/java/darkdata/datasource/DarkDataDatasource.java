package darkdata.datasource;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author szednik
 */

public class DarkDataDatasource {

    private OntModel ontModel;
    private OntModel schema;

    private List<Resource> ontologies;
    private List<Resource> data;

    public DarkDataDatasource(List<Resource> ontologies, List<Resource> data) {
        this.ontologies = ontologies;
        this.data = data;
    }

    @PostConstruct
    public void setup() {

        final List<Model> dataModels = new ArrayList<>();

        schema = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

        for(Resource ontology: ontologies) {
            try(InputStream is = ontology.getInputStream()) {
                schema.read(is, null, "TURTLE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Resource dataModel: data) {
            try(InputStream is = dataModel.getInputStream()) {
                Model _model = ModelFactory.createDefaultModel();
                _model.read(is, null, "TURTLE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ontModel = ModelFactory.createOntologyModel();
        ontModel.addSubModel(schema);

        for(Model model: dataModels) {
            ontModel.addSubModel(model);
        }

        ontModel.prepare();
    }

    public OntModel getSchema() {
        return schema;
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    // TODO factoryMethod for each process?
    public OntModel createOntModel() {
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        m.addSubModel(ontModel);
        return m;
    }
}
