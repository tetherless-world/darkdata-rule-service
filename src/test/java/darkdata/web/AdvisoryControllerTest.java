package darkdata.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import darkdata.DarkDataApplication;
import darkdata.service.RecommendationService;
import darkdata.web.api.RecommendationRequest;
import darkdata.web.api.RecommendationResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
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
                .andExpect(jsonPath("$.candidates[0].workflow.service", is("ArAvTs")))
                .andExpect(jsonPath("$.candidates[0].workflow.data_variables", hasSize(1)))
                .andExpect(jsonPath("$.candidates[0].workflow.data_variables[0].product", is("MYD08_D3")))
                .andExpect(jsonPath("$.candidates[0].workflow.data_variables[0].version", is("6")))
                .andExpect(jsonPath("$.candidates[0].workflow.data_variables[0].variable", is("Cirrus_Reflectance_Mean")))
                .andExpect(jsonPath("$.candidates[0].score", is(2.543D)))
                .andExpect(jsonPath("$.criteria.event.id", is("EONET_224")))
                .andExpect(jsonPath("$.criteria.event.title", is("Hurricane Olaf")))
                .andExpect(jsonPath("$.criteria.event.link", is("http://eonet.sci.gsfc.nasa.gov/api/v2/events/EONET_224")))
                .andExpect(jsonPath("$.criteria.event.categories", hasSize(1)))
                .andExpect(jsonPath("$.criteria.event.categories[0].title", is("Severe Storms")))
                .andExpect(jsonPath("$.criteria.data_variables", hasSize(2)))
                .andExpect(jsonPath("$.criteria.data_variables[0].id", is("MYD08_D3_6_Cirrus_Reflectance_Mean")))
                .andExpect(jsonPath("$.criteria.data_variables[1].id", is("MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean")))
        ;

    }

    @Test
    public void testBadRequest() throws Exception {
        mockMvc.perform(post("/advisor/recommendation")
                .content("{\"bad-data\": \"true\"}")
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


