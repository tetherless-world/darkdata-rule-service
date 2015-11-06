package darkdata.model.kb.compatibility;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.candidate.Candidate;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.Optional;

/**
 * @author szednik
 */
public class CompatibilityAssertion extends IndividualProxy {

    public final static OntClass CLASS = DarkData.CompatibilityAssertion;

    public CompatibilityAssertion(Individual individual) {
        super(individual);
        individual.addOntClass(CLASS);
    }

    public Optional<CandidateWorkflow> getCandidate() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidate))
                .map(Resource::getURI)
                .map(m::getIndividual)
                .map(CandidateWorkflow::new);
    }

    public void setCandidate(Candidate candidate) {
        getIndividual().setPropertyValue(DarkData.candidate, candidate.getIndividual());
    }

    public void setValue(CompatibilityValue value) {
        getIndividual().setPropertyValue(DarkData.compatibilityValue, value.getIndividual());
        getIndividual().getOntModel().add(value.getIndividual().listProperties().toList());
    }

    public Optional<CompatibilityValue> getValue() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.compatibilityValue))
                .map(Resource::getURI)
                .map(m::getIndividual)
                .map(CompatibilityValue::new);
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
}
