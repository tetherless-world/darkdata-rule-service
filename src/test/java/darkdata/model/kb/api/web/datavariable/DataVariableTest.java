package darkdata.model.kb.api.web.datavariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import darkdata.DarkDataApplication;
import darkdata.model.api.web.datavariable.DataVariable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration
/**
 * Created by anirudhprabhu on 10/24/15.
 */
public class DataVariableTest {
    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/MOD08_D3_051_Cirrus_Reflectance_Mean.json")
    private Resource mod08_d3_051_crm;

    @Test
    public void testCanSerialize() {
        Assert.assertTrue(mapper.canSerialize(DataVariable.class));
    }
    private DataVariable createDataVariable() {
        return new DataVariable("MYD08_D3","51","Cirrus_Reflectance_Mean","ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE");
    }

    @Test
    public void testSerializeDataVariable() throws IOException {
        DataVariable dataVariable = createDataVariable();
        Assert.assertNotNull(dataVariable);
        //System.out.println(mapper.writeValueAsString(dataVariable));
        ObjectNode serialized_pojo = mapper.valueToTree(dataVariable);
        JsonNode expected = mapper.readTree(mod08_d3_051_crm.getInputStream());
        Assert.assertEquals("expect does not equal serialized pojo", expected, serialized_pojo);
    }
}
