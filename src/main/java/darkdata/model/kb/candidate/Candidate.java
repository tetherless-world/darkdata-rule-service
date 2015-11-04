package darkdata.model.kb.candidate;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.compatibility.CompatibilityAssertion;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

public class Candidate extends IndividualProxy {

    private CandidateScore score;

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

    public Optional<CompatibilityAssertion> createCompatibilityAssertion(String uri) {
        Optional<CompatibilityAssertion> assertion = Optional.ofNullable(getIndividual().getOntModel().createIndividual(uri, DarkData.CompatibilityAssertion))
                .map(CompatibilityAssertion::new);
        assertion.ifPresent(this::addCompatibilityAssertion);
        assertion.ifPresent(a -> a.setCandidate(this));
        return assertion;
    }

    public void setScore(CandidateScore score) {
        this.score = score;
    }

    public Optional<CandidateScore> getScore() {
        return Optional.ofNullable(this.score);
    }
}
