package darkdata.web;

import darkdata.DarkDataApplication;
import darkdata.service.RecommendationService;
import darkdata.web.api.RecommendationResponse;
import org.apache.http.HttpStatus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;



/**
 * @author anirudhprabhu
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration("server.port=0")
//@IntegrationTest("server.port=0")
//@DirtiesContext

public class AdvisoryControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private RecommendationService recommendationServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

/*
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
*/
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void findAll_Responses() throws Exception {


        mockMvc.perform(get("/advisor/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am Up."));
//
//                .andExpect(jsonPath("$",hasSize(1)))
//                .andExpect(jsonPath("$[0].service",is("ArAvTs")))
//                .andExpect(jsonPath("$[0].start_time",is("2015-01-01T00:00:00Z")))
//                .andExpect(jsonPath("$[0].end_time",is("2015-04-30T23:59:59Z")))
//                .andExpect(jsonPath("$[0].bbox",is("180,-5,180,5")));

    }

    @Test
    public void findAll_Responses_forPost() throws Exception {
        mockMvc.perform(post("/advisor/recommendation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.candidates", hasSize(1)))
                .andExpect(jsonPath("$.candidates[*].workflow", hasSize(1)))
                .andExpect(jsonPath("$.candidates[*].workflow[*].service", containsInAnyOrder("ArAvTs")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].start_time",containsInAnyOrder("2015-01-01T00:00:00Z")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].end_time",containsInAnyOrder("2015-04-30T23:59:59Z")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].bbox",containsInAnyOrder("180,-5,180,5")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables",hasSize(1)))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].product",containsInAnyOrder("MYD08_D3")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].version",containsInAnyOrder("51")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].variable",containsInAnyOrder("Cirrus_Reflectance_Mean")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].keyword",containsInAnyOrder("ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].modifiers",isEmptyOrNullString()))
                .andExpect(jsonPath("$.candidates[*].workflow[*].shape", containsInAnyOrder("shp_30")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].dataKeyword",isEmptyOrNullString()))
                .andExpect(jsonPath("$.candidates[*].score", containsInAnyOrder(2.543D)))
                .andExpect(jsonPath("$.criteria",hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event",hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event[*].id",containsInAnyOrder("EONET_224")))
                .andExpect(jsonPath("$.criteria[*].event[*].title", containsInAnyOrder("Hurricane Olaf")))
                .andExpect(jsonPath("$.criteria[*].event[*].link", containsInAnyOrder("http://eonet.sci.gsfc.nasa.gov/api/v1/events/EONET_224")))
                .andExpect(jsonPath("$.criteria[*].event[*].category", hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event[*].category[*].-domain", containsInAnyOrder("Severe Storms")))
                .andExpect(jsonPath("$.criteria[*].event[*].category[*].#text", containsInAnyOrder("Severe Storms")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry",hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry[*].date",containsInAnyOrder("2015-10-15T00:00:00Z")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry[*].type",containsInAnyOrder("Point")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry[*].coordinates",containsInAnyOrder(-117.10D,9.90D)))
                .andExpect(jsonPath("$.criteria[*].data_variables",hasSize(2)))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].product",containsInAnyOrder("MYD08_D3")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].version", containsInAnyOrder("51")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].variable", containsInAnyOrder("Cirrus_Reflectance_Mean","Cloud_Optical_Thickness_Liquid_Mean")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].keyword", containsInAnyOrder("ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE","ATMOSPHERE->CLOUDS->CLOUD LIQUIDWATER/ICE")));



    }



    }


