package darkdata.model.datavariable;

import java.util.List;

/**
 * @author szednik
 */

public class DataVariable {

    String id;
    String title;

    List<DataVariableModifier> modifiers;

    public DataVariable(String id, String title, List<DataVariableModifier> modifiers) {
        this.id = id;
        this.title = title;
        this.modifiers = modifiers;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<DataVariableModifier> getModifiers() {
        return modifiers;
    }
}
