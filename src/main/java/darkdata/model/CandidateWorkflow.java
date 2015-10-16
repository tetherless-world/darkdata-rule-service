package darkdata.model;

import darkdata.model.datavariable.DataVariable;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */
public class CandidateWorkflow extends Candidate {

    private CandidateWorkflowCriteria criteria;
    private Optional<OntClass> phenomena;
    private Optional<OntClass> physicalFeature;
    private Optional<Individual> observableProperty;
    private Optional<Individual> service;
    private Optional<Individual> event;
    private List<DataVariable> variables = new ArrayList<>();

    public CandidateWorkflow(String id, CandidateWorkflowCriteria criteria) {
        super(id);
        this.criteria = criteria;
    }

    public void setPhenomena(OntClass phenomena) {
        this.phenomena = Optional.ofNullable(phenomena);
    }

    public void setPhysicalFeature(OntClass physicalFeature) {
        this.physicalFeature = Optional.ofNullable(physicalFeature);
    }

    public void setObservableProperty(Individual observableProperty) {
        this.observableProperty = Optional.ofNullable(observableProperty);
    }

    public void setService(Individual service) {
        this.service = Optional.ofNullable(service);
    }

    public void setEvent(Individual event) {
        this.event = Optional.ofNullable(event);
    }

    public void setVariables(List<DataVariable> variables) {
        this.variables = variables;
    }

    public CandidateWorkflowCriteria getCriteria() {
        return criteria;
    }

    public Optional<OntClass> getPhenomena() {
        return phenomena;
    }

    public Optional<OntClass> getPhysicalFeature() {
        return physicalFeature;
    }

    public Optional<Individual> getObservableProperty() {
        return observableProperty;
    }

    public Optional<Individual> getService() {
        return service;
    }

    public Optional<Individual> getEvent() {
        return event;
    }

    public List<DataVariable> getVariables() {
        return variables;
    }
}
