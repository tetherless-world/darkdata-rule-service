package darkdata.model.kb.candidate;

import darkdata.model.kb.Phenomena;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.RDFNode;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author szednik
 */
public class CandidateWorkflow extends Candidate {

    public CandidateWorkflow(Individual individual) {
        super(individual);
    }

    public void setEvent(Phenomena event) {
        getIndividual().setPropertyValue(DarkData.candidateEvent, event.getIndividual());
    }

    public Optional<Phenomena> getEvent() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.candidateEvent))
                .filter(RDFNode::isURIResource)
                .map(RDFNode::asResource)
                .map(r -> getIndividual().getOntModel().getIndividual(r.getURI()))
                .map(Phenomena::new)
                .findFirst();
    }

    public void setService(G4Service service) {
        getIndividual().setPropertyValue(DarkData.candidateService, service.getIndividual());
    }

    public Optional<G4Service> getService() {
        return Stream.of(getIndividual().getPropertyResourceValue(DarkData.candidateService))
                .filter(RDFNode::isURIResource)
                .map(RDFNode::asResource)
                .map(r -> getIndividual().getOntModel().getIndividual(r.getURI()))
                .map(G4Service::new)
                .findFirst();
    }
}
