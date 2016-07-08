package darkdata.model.kb.compatibility;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.candidate.Candidate;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.*;

import java.util.Optional;

/**
 * @author szednik
 */
public class CompatibilityAssertion extends IndividualProxy {

    public final static OntClass CLASS = DarkData.CompatibilityAssertion;

    public CompatibilityAssertion(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public Optional<CandidateWorkflow> getCandidate() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidate))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(CandidateWorkflow::new);
    }

    public void setCandidate(Candidate candidate) {
        getIndividual().setPropertyValue(DarkData.candidate, candidate.getIndividual());
    }

    public void setValue(CompatibilityValue value) {
        getIndividual().setPropertyValue(DarkData.compatibilityValue, value.getIndividual());
        // TODO for the moment we have to do this or the score does not show up
        getIndividual().getOntModel().add(value.getIndividual().listProperties().toList());
    }

    public Optional<CompatibilityValue> getValue() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.compatibilityValue))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(CompatibilityValue::new);
    }

    public Optional<CompatibilityValue> createValue() {
        OntModel m = getIndividual().getOntModel();
        Optional<CompatibilityValue> v = Optional.ofNullable(m.createIndividual(DarkData.CompatibilityValue))
                .map(CompatibilityValue::new);
        v.ifPresent(this::setValue);
        return v;
    }

    public void setConfidence(double confidence) {
        getIndividual().setPropertyValue(DarkData.assertionConfidence, ResourceFactory.createTypedLiteral(confidence));
    }

    public Optional<Double> getConfidence() {
        return Optional.ofNullable(getIndividual().getPropertyValue(DarkData.assertionConfidence))
                .filter(RDFNode::isLiteral)
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble);
    }

    public static Double getConfidence(Model m, Resource assertion) {
        return m.listObjectsOfProperty(assertion, DarkData.assertionConfidence)
                .toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getDouble)
                .findAny().orElse(0.5D);
    }
}
