package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.DataVariable;
import darkdata.model.kb.Dataset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void testGetShortName() {
        DataVariable variable = DataVariableTestHarness.createDataVariable("urn:variable/testGetShortName");
        Assert.assertNotNull(variable);

        variable.setShortName("shortNameTest");
        Optional<String> result = variable.getShortName();

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("shortNameTest", result.get());
    }

    @Test
    public void testGetDatset() {
        DataVariable variable = DataVariableTestHarness.createDataVariable("urn:variable/testGetDatset");
        Assert.assertNotNull(variable);

        Dataset dataset = DatasetTestHarness.createDataset("urn:dataset/testGetDatset");
        Assert.assertNotNull(dataset);

        variable.setDataset(dataset);
        Optional<Dataset> result = variable.getDataset();
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(dataset, result.get());
    }
}
