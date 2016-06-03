package darkdata;

import darkdata.datasource.DarkDataDatasource;
import darkdata.service.RuleBasedReasoningService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
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

    @Value("classpath:recommendation-service.properties")
    private Resource recommendationServicePropertiesResource;

    @Bean
    public PropertiesFactoryBean recommendationServiceProperties() {
        PropertiesFactoryBean p = new PropertiesFactoryBean();
        p.setLocation(recommendationServicePropertiesResource);
        return p;
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("datafields"),
                new ConcurrentMapCache("products"),
                new ConcurrentMapCache("services"),
                new ConcurrentMapCache("features"),
                new ConcurrentMapCache("phenomena"),
                new ConcurrentMapCache("observableProperties")
        ));
        return cacheManager;
    }

    @Value("classpath:rules/characteristic_compatibility.rules")
    private Resource basicRules;

    @Value("classpath:rules/time_interval.rules")
    private Resource timeIntervalRules;

    @Value("classpath:rules/measurement_Li.rules")
    private Resource liMeasurementRules;

    @Value("classpath:rules/measurement_Shen.rules")
    private Resource shenMeasurementRules;

    @Value("classpath:rules/spatial_resolution_Li.rules")
    private Resource liSpatialResolutionRules;

    @Value("classpath:rules/spatial_resolution_Shen.rules")
    private Resource shenSpatialResolutionRules;

    @Value("classpath:rules/best_for_feature.rules")
    private Resource bestForFeatureRules;

//    @Value("classpath:rdf/sciencekeywords.ttl")
//    private Resource gcmdScienceKeywords;

    @Value("classpath:rdf/datafields.ttl")
    private Resource datafields;

    @Value("classpath:rdf/darkdata.ttl")
    Resource darkDataOntology;

    @Bean
    public DarkDataDatasource datasource() {
        List<Resource> ontologies = Collections.singletonList(darkDataOntology);
        List<Resource> dataModels = Collections.singletonList(datafields);
        return new DarkDataDatasource(ontologies, dataModels);
    }

    @Bean
    public RuleBasedReasoningService compatibilityRulesReasoningService() {
        List<Resource> rulesets = Arrays.asList(
                basicRules,
                timeIntervalRules,
                liMeasurementRules,
                shenMeasurementRules,
                liSpatialResolutionRules,
                shenSpatialResolutionRules,
                bestForFeatureRules);
        return new RuleBasedReasoningService(rulesets);
    }

    @Value("classpath:rules/generate_candidates.rules")
    private Resource candidateGenerationRules;

    @Bean
    public RuleBasedReasoningService candidateGenerationReasoningService() {
        List<Resource> rulesets = Collections.singletonList(candidateGenerationRules);
        return new RuleBasedReasoningService(rulesets);
    }
}
