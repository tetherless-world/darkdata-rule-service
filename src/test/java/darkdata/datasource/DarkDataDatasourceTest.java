package darkdata.datasource;

import darkdata.DarkDataApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class DarkDataDatasourceTest {

    @Autowired
    private DarkDataDatasource datasource;

    @Test
    public void contextLoads() { }

    @Test
    public void testDatasource() {
        Assert.assertNotNull(datasource);
    }

    @Test
    public void testGetOntModel() {
        Assert.assertNotNull(datasource.getOntModel());
    }
}
