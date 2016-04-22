package darkdata.model.kb.candidate;

import darkdata.model.kb.VersionedDataProduct;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class DatasetTestHarness {

    public static VersionedDataProduct createDataset(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual d = m.createIndividual(uri, DarkData.Dataset);
        return new VersionedDataProduct(d);
    }
}
