package darkdata.model.kb;

/**
 * @author szednik
 */
public abstract class Thing {

    private String id;
    private String label;

    public Thing(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
