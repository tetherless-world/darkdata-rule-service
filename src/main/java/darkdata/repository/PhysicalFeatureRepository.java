package darkdata.repository;

import darkdata.datasource.DarkDataDatasource;
import darkdata.model.ontology.DarkData;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author szednik
 */

@Service
public class PhysicalFeatureRepository {

    @Autowired
    private DarkDataDatasource datasource;

    /**
     * Returns a class with the given URI
     * @param uri the URI of the class to get
     * @return Optional object containing the OntClass (or empty if no class is found)
     * @see OntClass
     */
    public Optional<OntClass> getClass(String uri) {
        return Optional.ofNullable(datasource.getOntModel().getOntClass(uri));
    }

    /**
     * Returns a dd:PhysicalFeature subclass with the given label
     * @param label the label of the class to get
     * @return Optional object containing the OntClass (or empty if no class is found)
     * @see OntClass
     */
    @Cacheable("features")
    public Optional<OntClass> getClassByLabel(String label) {
        return listSubclasses().stream()
                .filter(c -> c.getLabel("en").equals(label))
                .findAny();
    }

    /**
     * Returns a list of subclasses of type dd:PhysicalManifestation
     * @return List of OntClass objects for subclasses of dd:PhysicalManifestation
     * @see OntClass
     */
    @Cacheable("features")
    public List<OntClass> listSubclasses() {
        return datasource.getOntModel()
                .getOntClass(DarkData.PhysicalManifestation.getURI())
                .listSubClasses()
                .toList()
                .stream()
                .filter(c->!c.isAnon())
                .collect(Collectors.toList());
    }

    /**
     * Returns list of physical manifestation classes for the given phenomena subclass
     * @param phenomena the phenomena subclass associated with the returned physical manifestations
     * @return List of OntClass objects for physical manifestations inferred for the given phenomena subclass
     * @see OntClass
     */
    @Cacheable("features")
    public List<OntClass> listPhysicalManifestationOfPhenomena(OntClass phenomena) {
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        m.addSubModel(datasource.getOntModel());
        try {
            Individual i = m.createIndividual(phenomena).asIndividual();

            return i.listPropertyValues(DarkData.physicalManifestation).toList()
                    .stream()
                    .map(v -> ((OntResource) v.asResource()))
                    .flatMap(v -> v.listRDFTypes(false).toList().stream())
                    .filter(t -> !t.isAnon() && !t.equals(DarkData.PhysicalManifestation))
                    .filter(DarkData.PhysicalManifestation::hasSubClass)
                    .map(t -> m.getOntClass(t.getURI()))
                    .collect(Collectors.toList());
        } finally {
            m.removeSubModel(datasource.getOntModel());
        }
    }

    public Optional<Resource> getPhysicalFeatureFromCandidate(Resource candidate) {
        return Optional.ofNullable(candidate.getPropertyResourceValue(DarkData.candidateFeature));
    }

//    public Optional<PhysicalFeature> createPhysicalFeature(String uri, OntClass featureClass) {
//        return Optional.ofNullable(datasource.getOntModel().createIndividual(uri, featureClass))
//                .map(PhysicalFeature::new);
//    }
//
//    public Optional<PhysicalFeature> createPhysicalFeature(OntModel m, String uri, OntClass featureClass) {
//        return Optional.ofNullable(m.createIndividual(uri, featureClass))
//                .map(PhysicalFeature::new);
//    }
//
//    public Optional<PhysicalFeature> createPhysicalFeature(OntModel m, OntClass featureClass) {
//        return Optional.ofNullable(m.createIndividual(featureClass))
//                .map(PhysicalFeature::new);
//    }
}
