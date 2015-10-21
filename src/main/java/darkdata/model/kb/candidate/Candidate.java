package darkdata.model.kb.candidate;

import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.kb.IndividualProxy;
import org.apache.jena.ontology.Individual;

import java.util.List;

/**
 * @author szednik
 */

public class Candidate extends IndividualProxy {

    public Candidate(Individual individual) {
        super(individual);
    }

    public List<CompatibilityAssertion> getCompatibilityAssertions() {
        //this.getIndividual().getOntModel();
        return null;
    }
}
