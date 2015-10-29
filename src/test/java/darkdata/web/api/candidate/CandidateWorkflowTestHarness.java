package darkdata.web.api.candidate;

import darkdata.web.api.workflow.Workflow;
import darkdata.web.api.workflow.WorkflowTestHarness;

/**
 * @author szednik
 */
public class CandidateWorkflowTestHarness {

    public static CandidateWorkflow createCandidateWorkflow() {
        Workflow workflow = WorkflowTestHarness.createWorkflow_ArAvTs();
        return new CandidateWorkflow(workflow, 2.543);
    }
}
