package darkdata.model.kb.candidate;

import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author szednik
 */
public class CandidateWorkflowTestHarness {

    public static CandidateWorkflow createCandidateWorkflow(String uri) {
        OntModel m = ModelFactory.createOntologyModel();
        Individual c = m.createIndividual(uri, DarkData.CandidateWorkflow);
        return new CandidateWorkflow(c);
    }
}
