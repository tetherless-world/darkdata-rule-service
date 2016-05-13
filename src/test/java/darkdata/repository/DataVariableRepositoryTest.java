package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.kb.DataVariable;
import darkdata.model.ontology.DarkData;
import org.apache.jena.rdf.model.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class DataVariableRepositoryTest {

    @Autowired
    private DataVariableRepository repository;

    @Test
    public void testGetByIdentifier() {
        final String SERVICE_IDENTIFIER = "MYD08_D3_6_Cirrus_Reflectance_Mean";
        Optional<DataVariable> datafield = repository.getByIdentifier(SERVICE_IDENTIFIER);
        Assert.assertTrue("service is missing", datafield.isPresent());
        Assert.assertTrue("service identifier is missing", datafield.get().getIdentifier().isPresent());
        Assert.assertEquals("service identifier does not match expected value", SERVICE_IDENTIFIER, datafield.get().getIdentifier().get());
    }

    @Test
    public void testGetByIdentifier_2() {
        final String SERVICE_IDENTIFIER = "MAT1NXSLV_5_2_0_UV10M_mag";
        Optional<DataVariable> datafield = repository.getByIdentifier(SERVICE_IDENTIFIER);
        Assert.assertTrue("datafield is missing", datafield.isPresent());
        Assert.assertTrue("datafield identifier is missing", datafield.get().getIdentifier().isPresent());
        Assert.assertEquals("datafeild identifier does not match expected value", SERVICE_IDENTIFIER, datafield.get().getIdentifier().get());

        Optional<Resource> timeInterval = datafield.map(DataVariable::getIndividual)
                .map(i -> i.getPropertyResourceValue(DarkData.timeInterval))
                .map(Resource::asResource);
        Assert.assertTrue(timeInterval.isPresent());

        Optional<Resource> spatialResolution = datafield.map(DataVariable::getIndividual)
                .map(i -> i.getPropertyResourceValue(DarkData.spatialResolution))
                .map(Resource::asResource);
        Assert.assertTrue(spatialResolution.isPresent());
    }

    @Test
    public void testListInstances() {
        List<DataVariable> datafields = repository.list();
        Assert.assertNotNull(datafields);
        Assert.assertFalse("datafield list should not be empty", datafields.isEmpty());
    }

}
