package darkdata.model.kb.compatibility;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.candidate.CandidateWorkflow;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;

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

    public Optional<CandidateWorkflow> candidate() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.candidate))
                .map(r -> (OntResource) r)
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(CandidateWorkflow::new)
                .findFirst();
    }

    public void setCandidateWorkflow(CandidateWorkflow candidate) {
        getIndividual().setPropertyValue(DarkData.candidate, candidate.getIndividual());
    }

    public void setCompatibilityValue(CompatibilityValue value) {
        getIndividual().setPropertyValue(DarkData.compatibilityValue, value.getIndividual());
    }

    public Optional<CompatibilityValue> compatibilityValue() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.compatibilityValue))
                .map(r -> (OntResource) r)
                .filter(OntResource::isIndividual)
                .map(OntResource::asIndividual)
                .map(CompatibilityValue::new)
                .findFirst();
    }
}
