package darkdata.repository;

import darkdata.DarkDataApplication;
import org.apache.jena.ontology.OntClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author szednik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
public class ObservablePropertyRepositoryTest {

    @Autowired
    private ObservablePropertyRepository repository;

    @Test
    public void testListSubclasses() {
        List<OntClass> observablePropertyClasses = repository.listSubclasses();
        Assert.assertNotNull(observablePropertyClasses);
        Assert.assertFalse(observablePropertyClasses.isEmpty());
    }
}
