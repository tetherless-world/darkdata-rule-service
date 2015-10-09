package darkdata.repository;

import darkdata.DarkDataApplication;
import darkdata.model.kb.Phenomena;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.Optional;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class PhenomenaRepositoryTest {

    @Autowired
    private PhenomenaRepository repository;

    @Test
    public void testList() {
        Collection<Phenomena> phenomenaCollection = repository.list();
        Assert.assertFalse(phenomenaCollection.isEmpty());
    }

    @Test
    public void testGet() {
        Optional<Phenomena> phenomena = repository.get("hurricane");
        Assert.assertTrue(phenomena.isPresent());
        Assert.assertEquals("hurricane", phenomena.get().getId());
    }
}
