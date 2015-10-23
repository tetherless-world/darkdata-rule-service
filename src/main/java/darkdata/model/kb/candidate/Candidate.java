package darkdata.model.kb.candidate;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

public class Candidate extends IndividualProxy {

    public Candidate(Individual individual) {
        super(individual);
    }

    public void addCompatibilityAssertion(CompatibilityAssertion assertion) {
        getIndividual().addProperty(DarkData.compatibilityAssertion, assertion.getIndividual());
    }

    public List<CompatibilityAssertion> getCompatibilityAssertions() {
        return getIndividual().listPropertyValues(DarkData.compatibilityAssertion).toList().stream()
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(r -> getIndividual().getOntModel().getIndividual(r.getURI()))
                .map(CompatibilityAssertion::new)
                .collect(Collectors.toList());
    }
}
