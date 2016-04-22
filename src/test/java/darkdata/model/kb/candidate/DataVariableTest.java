package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.VersionedDataProduct;
import darkdata.repository.DataVariableRepository;
import darkdata.repository.DatasetRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class DataVariableTest {

    @Autowired
    private DataVariableRepository dataVariableRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Test
    public void testGetShortName() {

        DataVariable variable = dataVariableRepository.createDataVariable("urn:variable/testGetShortName").get();
        Assert.assertNotNull(variable);

        variable.setShortName("shortNameTest");
        Optional<String> result = variable.getShortName();

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("shortNameTest", result.get());
    }

    @Test
    public void testGetDatset() {
        DataVariable variable = dataVariableRepository.createDataVariable("urn:variable/testGetDatset").get();
        Assert.assertNotNull(variable);

        VersionedDataProduct versionedDataProduct = datasetRepository.createDataset("urn:dataset/testGetDatset").get();
        Assert.assertNotNull(versionedDataProduct);

        variable.setDataset(versionedDataProduct);
        Optional<VersionedDataProduct> result = variable.getVersionedDataProduct();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(versionedDataProduct, result.get());
    }
}
