package darkdata.model.api.web.candidate;

import darkdata.model.api.web.workflow.Workflow;
import darkdata.model.api.web.workflow.WorkflowTestHarness;

/**
 * @author szednik
 */
public class CandidateWorkflowTestHarness {

    public static CandidateWorkflow createCandidateWorkflow() {
        Workflow workflow = WorkflowTestHarness.createWorkflow_ArAvTs();
        return new CandidateWorkflow(workflow, 2.543);
    }
}
