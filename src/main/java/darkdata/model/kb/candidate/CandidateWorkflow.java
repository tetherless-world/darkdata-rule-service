package darkdata.model.kb.candidate;

import darkdata.model.kb.Phenomena;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.Optional;

/**
 * @author szednik
 */
public class CandidateWorkflow extends Candidate {

    public static final OntClass CLASS = DarkData.CandidateWorkflow;

    public CandidateWorkflow(Individual individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public void setEvent(Phenomena event) {
        getIndividual().setPropertyValue(DarkData.candidateEvent, event.getIndividual());
    }

    public Optional<Phenomena> getEvent() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidateEvent))
                .filter(RDFNode::isURIResource)
                .map(RDFNode::asResource)
                .map(Resource::getURI)
                .map(m::getIndividual)
                .map(Phenomena::new);
    }

    public void setService(G4Service service) {
        getIndividual().setPropertyValue(DarkData.candidateService, service.getIndividual());
    }

    public Optional<G4Service> getService() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidateService))
                .filter(RDFNode::isURIResource)
                .map(RDFNode::asResource)
                .map(Resource::getURI)
                .map(m::getIndividual)
                .map(G4Service::new);
    }
}
