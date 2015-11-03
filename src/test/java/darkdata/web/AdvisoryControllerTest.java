package darkdata.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import darkdata.DarkDataApplication;
import darkdata.service.RecommendationService;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import darkdata.web.api.candidate.CandidateWorkflow;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
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
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
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



//    @Autowired
//    private List<CandidateWorkflow> candidates;

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
        // TODO create a response with content and match content at end of test
        RecommendationRequest recommendationRequest = mapper.readValue(IOUtils.toString(request.getInputStream()), RecommendationRequest.class);
        //System.out.println(recommendationRequest);
        //RecommendationResponse response = new RecommendationResponse(recommendationRequest);
        RecommendationResponse recommendationResponse = mapper.readValue(IOUtils.toString(response.getInputStream()), RecommendationResponse.class);
        Mockito.when(recommendationService.getRecommendation(any(RecommendationRequest.class)))
                .thenReturn(recommendationResponse);

        String response_1 = mapper.writeValueAsString(recommendationResponse);
        System.out.println(response_1);
        final String request_string = IOUtils.toString(request.getInputStream());
        //System.out.println(request_string);
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
                .andExpect(jsonPath("$.candidates[*].workflow[*].data_variables[*].modifiers", contains(Arrays.asList(empty()))))
                .andExpect(jsonPath("$.candidates[*].workflow[*].shape", containsInAnyOrder("shp_30")))
                .andExpect(jsonPath("$.candidates[*].workflow[*].dataKeyword", contains(Arrays.asList(empty()))))
                .andExpect(jsonPath("$.candidates[*].score", containsInAnyOrder(2.543D)))
                .andExpect(jsonPath("$").value(hasKey("criteria")))
                .andExpect(jsonPath("$.criteria[*]").value(hasKey("event")))
                .andExpect(jsonPath("$.criteria[*].event[*].id", is("EONET_224")))
                .andExpect(jsonPath("$.criteria[*].event[*].title", is("Hurricane Olaf")))
                .andExpect(jsonPath("$.criteria[*].event[*].link", is("http://eonet.sci.gsfc.nasa.gov/api/v1/events/EONET_224")))
                .andExpect(jsonPath("$.criteria[*].event[*].category", hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event[*].category[*].-domain", containsInAnyOrder("Severe Storms")))
                .andExpect(jsonPath("$.criteria[*].event[*].category[*].#text", containsInAnyOrder("Severe Storms")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry",hasSize(1)))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry[*].date",containsInAnyOrder("2015-10-15T00:00:00Z")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry[*].type",containsInAnyOrder("Point")))
                .andExpect(jsonPath("$.criteria[*].event[*].geometry[*].coordinates", contains(Collections.singletonList(Arrays.asList(-117.10d, 9.90d)))))
                .andExpect(jsonPath("$.criteria[*].data_variables", hasSize(2)))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].product",hasItem("MYD08_D3")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].version", hasItem("51")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].variable", containsInAnyOrder("Cirrus_Reflectance_Mean", "Cloud_Optical_Thickness_Liquid_Mean")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].keyword", containsInAnyOrder("ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE", "ATMOSPHERE->CLOUDS->CLOUD LIQUIDWATER/ICE")))
                .andExpect(jsonPath("$.criteria[*].data_variables[*].modifiers", containsInAnyOrder(Arrays.asList(), Arrays.asList())));

    }
}


