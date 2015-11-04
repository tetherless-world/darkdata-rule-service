package darkdata.web.api.datavariable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;


/**
 * @author szednik
 */

public class DataVariable {

    @JsonProperty(value = "product")
    String product;

    @JsonProperty(value = "version")
    String version;

    @JsonProperty(value = "variable")
    String variable;

    @JsonProperty(value = "keyword")
    String keyword;

    @JsonProperty(value = "modifiers")
    List<DataVariableModifier> modifiers = Collections.<DataVariableModifier>emptyList();

    public DataVariable() { }

    public DataVariable(String product, String version, String variable, String keyword) {
        this.product = product;
        this.version = version;
        this.variable = variable;
        this.keyword = keyword;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<DataVariableModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<DataVariableModifier> modifiers) {
        this.modifiers = modifiers;
    }
}
