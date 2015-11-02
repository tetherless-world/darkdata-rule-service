package darkdata.web.api.datavariable;

/**
 * @author szednik
 */
public class DataVariableModifier {

    String name;
    String value;

    public DataVariableModifier() { }

    public DataVariableModifier(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
