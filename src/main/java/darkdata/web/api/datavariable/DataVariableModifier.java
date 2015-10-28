package darkdata.web.api.datavariable;

/**
 * @author szednik
 */
public class DataVariableModifier {

    String name;
    String value;

    public DataVariableModifier(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
