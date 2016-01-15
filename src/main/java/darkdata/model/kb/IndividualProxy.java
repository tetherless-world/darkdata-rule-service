package darkdata.model.kb;

import org.apache.jena.ontology.OntResource;

/**
 * @author szednik
 */

public class IndividualProxy {

    private OntResource individual;

    protected IndividualProxy(OntResource individual) {
        this.individual = individual;
    }

    public OntResource getIndividual() {
        return individual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndividualProxy)) return false;

        IndividualProxy that = (IndividualProxy) o;

        return getIndividual().equals(that.getIndividual());

    }

    @Override
    public int hashCode() {
        return getIndividual().hashCode();
    }
}
