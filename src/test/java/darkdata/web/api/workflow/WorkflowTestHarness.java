package darkdata.web.api.workflow;

import darkdata.web.api.datavariable.DataVariable;
import darkdata.web.api.datavariable.DataVariableTestHarness;

import java.util.Collections;

/**
 * @author szednik
 */
public class WorkflowTestHarness {

    public static Workflow createWorkflow_ArAvTs() {
        Workflow workflow = new Workflow();
        workflow.setService("ArAvTs");
        workflow.setStartTime("2015-01-01T00:00:00Z");
        workflow.setEndTime("2015-04-30T23:59:59Z");
        workflow.setBoundingBox("180,-5,180,5");
        workflow.setShape("shp_30");
        DataVariable variable = DataVariableTestHarness.createVariable_MYD08_D3_51_Cirrus_Reflectance_Mean();
        workflow.setVariables(Collections.singletonList(variable));
        return workflow;
    }
}
