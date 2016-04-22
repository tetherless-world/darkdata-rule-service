package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.kb.VersionedDataProduct;
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
public class VersionedDataProductRepositoryTest {

    @Autowired
    private DatasetRepository repository;

    @Test
    public void testGetByShortName() {
        final String SHORTNAME = "MYD08_D3.6";
        Optional<VersionedDataProduct> versionedDataProduct = repository.getByShortName(SHORTNAME);
        Assert.assertTrue("versioned data product is missing", versionedDataProduct.isPresent());
        Assert.assertTrue("versioned data product short name is missing", versionedDataProduct.get().getShortName().isPresent());
        Assert.assertEquals("versioned data product short name does not match expected value", SHORTNAME, versionedDataProduct.get().getShortName().get());
    }

    @Test
    public void testList() {
        List<VersionedDataProduct> versionedDataProductList = repository.list();
        Assert.assertNotNull(versionedDataProductList);
        Assert.assertFalse(versionedDataProductList.isEmpty());
    }
}
