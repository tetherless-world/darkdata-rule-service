package darkdata.model.kb.candidate;

import darkdata.model.kb.DataVariable;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhysicalFeature;
import darkdata.model.kb.g4.G4Service;
import darkdata.model.ontology.DarkData;
import org.apache.jena.graph.BlankNodeId;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.AnonId;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */
public class CandidateWorkflow extends Candidate {

    public static final OntClass CLASS = DarkData.CandidateWorkflow;

    public CandidateWorkflow(OntResource individual) {
        super(individual);
        individual.addRDFType(CLASS);
    }

    public void setEvent(Phenomena event) {
        getIndividual().setPropertyValue(DarkData.candidateEvent, event.getIndividual());
        getIndividual().getOntModel().add(event.getIndividual().listProperties());
    }

    public Optional<Phenomena> getEvent() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidateEvent))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(Phenomena::new);
    }

    public void setService(G4Service service) {
        getIndividual().setPropertyValue(DarkData.candidateService, service.getIndividual());
        getIndividual().getOntModel().add(service.getIndividual().listProperties());
    }

    public Optional<G4Service> getService() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidateService))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(G4Service::new);
    }

    public List<PhysicalFeature> getCandidatePhysicalFeatures() {
        return getEvent()
                .map(Phenomena::getPhysicalFeatures)
                .orElse(Collections.<PhysicalFeature>emptyList());
    }

    public void addVariable(DataVariable variable) {
        getIndividual().setPropertyValue(DarkData.candidateVariable, variable.getIndividual());
        getIndividual().getOntModel().add(variable.getIndividual().listProperties());
    }

    public Optional<DataVariable> getVariable() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidateVariable))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(DataVariable::new);
    }

    public List<DataVariable> listVariables() {
        return Collections.emptyList();
    }

    public void setFeature(PhysicalFeature feature) {
        getIndividual().setPropertyValue(DarkData.candidateFeature, feature.getIndividual());
        getIndividual().getOntModel().add(feature.getIndividual().listProperties());
    }

    public Optional<PhysicalFeature> getFeature() {
        OntModel m = getIndividual().getOntModel();
        return Optional.ofNullable(getIndividual().getPropertyResourceValue(DarkData.candidateFeature))
                .filter(RDFNode::isResource)
                .map(RDFNode::asResource)
                .map(m::getOntResource)
                .map(PhysicalFeature::new);
    }
}
