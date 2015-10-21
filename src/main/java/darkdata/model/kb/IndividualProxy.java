package darkdata.model.kb;

import org.apache.jena.ontology.Individual;

/**
 * @author szednik
 */
public class IndividualProxy {

    private Individual individual;

    protected IndividualProxy(Individual individual) {
        this.individual = individual;
    }

    public Individual getIndividual() {
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
