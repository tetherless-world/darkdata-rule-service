package darkdata.web.api.datavariable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;


/**
 * @author szednik
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DataVariable {

    @JsonProperty(value = "id")
    private String identifier;

    @JsonProperty(value = "product")
    private String product;

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "variable")
    private String variable;

    @JsonProperty(value = "keywords")
    private List<String> keywords;

    @JsonProperty(value = "modifiers")
    private List<DataVariableModifier> modifiers = Collections.<DataVariableModifier>emptyList();

    public DataVariable() { }

    public DataVariable(String identifier) {
        this.identifier = identifier;
    }

    public DataVariable(String identifier, String product, String version, String variable, List<String> keywords) {
        this.identifier = identifier;
        this.product = product;
        this.version = version;
        this.variable = variable;
        this.keywords = keywords;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeyword(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<DataVariableModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<DataVariableModifier> modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataVariable)) return false;

        DataVariable variable1 = (DataVariable) o;

        return getIdentifier() != null ? getIdentifier().equals(variable1.getIdentifier()) : variable1.getIdentifier() == null
                && (getProduct() != null ? getProduct().equals(variable1.getProduct()) : variable1.getProduct() == null
                && (getVersion() != null ? getVersion().equals(variable1.getVersion()) : variable1.getVersion() == null
                && (getVariable() != null ? getVariable().equals(variable1.getVariable()) : variable1.getVariable() == null
                && (getModifiers() != null ? getModifiers().equals(variable1.getModifiers()) : variable1.getModifiers() == null))));
    }

    @Override
    public int hashCode() {
        int result = getIdentifier() != null ? getIdentifier().hashCode() : 0;
        result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        result = 31 * result + (getVariable() != null ? getVariable().hashCode() : 0);
        result = 31 * result + (getModifiers() != null ? getModifiers().hashCode() : 0);
        return result;
    }
}
