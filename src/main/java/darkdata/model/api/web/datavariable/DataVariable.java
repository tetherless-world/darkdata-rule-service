package darkdata.model.api.web.datavariable;

import java.util.List;

/**
 * @author szednik
 */

public class DataVariable {

    String name;
    String datasetId;

    String description;
    String scienceKeyword;

    List<DataVariableModifier> modifiers;

    public DataVariable(String name, String datasetId, List<DataVariableModifier> modifiers) {
        this.modifiers = modifiers;
        this.name = name;
        this.datasetId = datasetId;
    }

    public void setScienceKeyword(String scienceKeyword) {
        this.scienceKeyword = scienceKeyword;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getScienceKeyword() {
        return scienceKeyword;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public List<DataVariableModifier> getModifiers() {
        return modifiers;
    }
}
