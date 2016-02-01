package darkdata.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import darkdata.DarkDataApplication;
import darkdata.service.RecommendationService;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * @author anirudhprabhu
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DarkDataApplication.class)
@WebAppConfiguration("server.port=0")
public class AdvisoryControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;


    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private AdvisoryController advisoryController;

    @Value("classpath:json/request.json")
    private Resource request;

    @Value("classpath:json/response.json")
    private Resource response;

    @Autowired
    private ObjectMapper mapper;



    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(advisoryController).build();
    }

    @Test
    public void testAdvisorGetForStatus() throws Exception {
        mockMvc.perform(get("/advisor/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am Up."));
    }

    @Test
    public void testAdvisorPostForRecommendation() throws Exception {

        RecommendationResponse recommendationResponse = mapper.readValue(IOUtils.toString(response.getInputStream()), RecommendationResponse.class);
        Mockito.when(recommendationService.getRecommendation(any(RecommendationRequest.class)))
                .thenReturn(recommendationResponse);

        mapper.writeValueAsString(recommendationResponse);
        final String request_string = IOUtils.toString(request.getInputStream());
        mockMvc.perform(post("/advisor/recommendation")
                .content(request_string)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.candidates", hasSize(1)))
                .andExpect(jsonPath("$.candidates[*].workflow", hasSize(1)))
                .andExpect(jsonPath("$.candidates[*].workflow[*].service", containsInAnyOrder("ArAvTs")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].start_time", containsInAnyOrder("2015-01-01T00:00:00Z")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].end_time", containsInAnyOrder("2015-04-30T23:59:59Z")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].bbox",containsInAnyOrder("180,-5,180,5")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables",hasSize(1)))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].product",containsInAnyOrder("MYD08_D3")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].version",containsInAnyOrder("51")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].variable",containsInAnyOrder("Cirrus_Reflectance_Mean")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].keyword",containsInAnyOrder("ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE")))
//                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].modifiers", contains(Arrays.asList(empty()))))
                .andExpect(jsonPath("$.candidates[*].workflow[*].shape", containsInAnyOrder("shp_30")))
//                .andExpect(jsonPath("$.candidates[*].workflow[*].dataKeyword", contains(Arrays.asList(empty()))))
                .andExpect(jsonPath("$.candidates[*].score", containsInAnyOrder(2.543D)))
                .andExpect(jsonPath("$").value(hasKey("criteria")))
                .andExpect(jsonPath("$.criteria[*]").value(hasKey("event")))
                .andExpect(jsonPath("$.criteria[*].event[*].id", is("EONET_224")))
                .andExpect(jsonPath("$.criteria[*].event[*].title", is("Hurricane Olaf")))
                .andExpect(jsonPath("$.criteria[*].event[*].link", is("http://eonet.sci.gsfc.nasa.gov/api/v2/events/EONET_224")))
                .andExpect(jsonPath("$.criteria[*].event[*].categories", hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event[*].categories[0].title", is("Severe Storms")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometries",hasSize(1))) //really 52, but abbreviated for testing
                .andExpect(jsonPath("$.criteria[*].event[*].geometries[0].date",is("2015-10-15T00:00:00Z")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometries[0].type",is("Point")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometries[0].coordinates", is(Arrays.asList(-117.10d, 9.90d))))
                .andExpect(jsonPath("$.criteria[*].data_variables", hasSize(2)))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].product",hasItem("MYD08_D3")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].version", hasItem("51")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].variable", containsInAnyOrder("Cirrus_Reflectance_Mean", "Cloud_Optical_Thickness_Liquid_Mean")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].keyword", containsInAnyOrder("ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE", "ATMOSPHERE->CLOUDS->CLOUD LIQUIDWATER/ICE")))
//                .andExpect(jsonPath("$.criteria[*].data_variables[*].modifiers", containsInAnyOrder(Arrays.asList(), Arrays.asList())));
        ;

    }

    @Test
    public void testBadRequest() throws Exception {
        mockMvc.perform(post("/advisor/recommendation")
                .content("{\n" +
                        "    \"glossary\": {\n" +
                        "        \"title\": \"example glossary\",\n" +
                        "\t\t\"GlossDiv\": {\n" +
                        "            \"title\": \"S\",\n" +
                        "\t\t\t\"GlossList\": {\n" +
                        "                \"GlossEntry\": {\n" +
                        "                    \"ID\": \"SGML\",\n" +
                        "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
                        "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
                        "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
                        "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
                        "\t\t\t\t\t\"GlossDef\": {\n" +
                        "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                        "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
                        "                    },\n" +
                        "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }
    

    @Test
    public void testInternalError() throws Exception {
        final String request_string = IOUtils.toString(request.getInputStream());

        Mockito.when(recommendationService.getRecommendation(any(RecommendationRequest.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/advisor/recommendation")
                .content(request_string).contentType(contentType))
                .andExpect(status().isInternalServerError());
    }

}


