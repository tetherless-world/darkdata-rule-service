package darkdata.model.kb;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class PhenomenaTestHarness {

    public static Phenomena createPhenomena(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual(uri, DarkData.Phenomena);
        return new Phenomena(g);
    }

    public static Phenomena createHurricane(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual(uri, DarkData.Hurricane);
        return new Phenomena(g);
    }

    public static Phenomena createVolcanicEruption(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual g = m.createIndividual(uri, DarkData.VolcanicEruption);
        return new Phenomena(g);
    }
}
