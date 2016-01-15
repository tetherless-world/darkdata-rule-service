package darkdata.web.api.candidate;

import darkdata.model.ontology.DarkData;
import darkdata.web.api.workflow.Workflow;
import darkdata.web.api.workflow.WorkflowTestHarness;

/**
 * @author szednik
 */
public class CandidateWorkflowTestHarness {

    public static CandidateWorkflow createCandidateWorkflow() {
        Workflow workflow = WorkflowTestHarness.createWorkflow_ArAvTs();
        String feature = DarkData.AshPlume.getURI();
        return new CandidateWorkflow(workflow, feature, 2.543);
    }
}
