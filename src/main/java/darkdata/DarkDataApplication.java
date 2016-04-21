package darkdata;

import darkdata.datasource.DarkDataDatasource;
import darkdata.service.RuleBasedReasoningService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author szednik
 */

@SpringBootApplication
public class DarkDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkDataApplication.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true);
        builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return builder;
    }

    @Value("classpath:simple-weights.properties")
    private Resource simpleWeightsResource;

    @Bean
    public PropertiesFactoryBean simpleWeights() {
        PropertiesFactoryBean p = new PropertiesFactoryBean();
        p.setLocation(simpleWeightsResource);
        return p;
    }

    @Value("classpath:rules/characteristic_compatibility.rules")
    private Resource basicRules;

    @Value("classpath:rules/time_interval.rules")
    private Resource timeIntervalRules;

    @Value("classpath:rdf/sciencekeywords.ttl")
    private Resource gcmdScienceKeywords;

    @Value("classpath:rdf/darkdata.ttl")
    Resource darkDataOntology;

    @Bean
    public DarkDataDatasource datasource() {
        List<Resource> ontologies = Collections.singletonList(darkDataOntology);
        List<Resource> dataModels = Collections.singletonList(gcmdScienceKeywords);
        return new DarkDataDatasource(ontologies, dataModels);
    }

    @Bean
    public RuleBasedReasoningService ruleBasedReasoningService() {
        List<Resource> rulesets = Arrays.asList(basicRules, timeIntervalRules);
        return new RuleBasedReasoningService(rulesets);
    }
}
