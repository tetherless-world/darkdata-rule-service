package darkdata.model.kb.candidate;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Dataset;
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
public class DatasetTest {

    @Autowired
    private DatasetRepository datasetRepository;

    @Test
    public void testGetShortName() {

        Dataset dataset = datasetRepository.createDataset("urn:dataset/testGetShortName").get();
        Assert.assertNotNull(dataset);

        dataset.setShortName("shortNameTest");
        Optional<String> result = dataset.getShortName();

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("shortNameTest", result.get());
    }

    @Test
    public void testGetLongName() {

        Dataset dataset = datasetRepository.createDataset("urn:dataset/testGetLongName").get();
        Assert.assertNotNull(dataset);

        dataset.setLongName("longNameTest");
        Optional<String> result = dataset.getLongName();

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("longNameTest", result.get());
    }

    // TODO testGetVariables
}
