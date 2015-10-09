package darkdata.model;

import darkdata.model.kb.ObservableProperty;
import darkdata.model.kb.Phenomena;
import darkdata.model.kb.PhysicalFeature;

import java.util.Optional;

/**
 * @author szednik
 */
public class CandidateWorkflow extends Candidate {

    // Service
    private Optional<Phenomena> phenomena;
    private Optional<PhysicalFeature> physicalFeature;
    private Optional<ObservableProperty> observableProperty;

    // Variables

    public CandidateWorkflow(String id) {
        super(id);
    }
}
