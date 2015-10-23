package darkdata.model.kb.compatibility;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author szednik
 */
public class CompatibilityAssertion extends IndividualProxy {

    public final static OntClass CLASS = DarkData.CompatibilityAssertion;

    public CompatibilityAssertion(Individual individual) {
        super(individual);
    }

    public Optional<CandidateWorkflow> getCandidate() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.candidate))
                .map(r -> getIndividual().getOntModel().getIndividual(r.getURI()))
                .map(CandidateWorkflow::new)
                .findFirst();
    }

    public void setCandidateWorkflow(CandidateWorkflow candidate) {
        getIndividual().setPropertyValue(DarkData.candidate, candidate.getIndividual());
    }

    public void setValue(CompatibilityValue value) {
        getIndividual().setPropertyValue(DarkData.compatibilityValue, value.getIndividual());
    }

    public Optional<CompatibilityValue> getValue() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.compatibilityValue))
                .map(r -> getIndividual().getOntModel().createIndividual(r.getURI(), DarkData.CompatibilityValue))
                .map(CompatibilityValue::new)
                .findFirst();
    }

    public void setConfidence(double confidence) {
        getIndividual().setPropertyValue(DarkData.assertionConfidence, ResourceFactory.createTypedLiteral(confidence));
    }

    public Optional<Double> getConfidence() {
        return Stream.of(getIndividual().getPropertyValue(DarkData.assertionConfidence))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble)
                .findAny();
    }
}
