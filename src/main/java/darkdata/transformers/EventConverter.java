package darkdata.transformers;

import darkdata.model.kb.Phenomena;
import darkdata.repository.EventRepository;
import darkdata.repository.PhenomenaRepository;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventCategory;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Component
public class EventConverter implements Converter<Event,Phenomena> {

    @Autowired
    private PhenomenaRepository phenomenaRepository;

    @Autowired
    private EventRepository eventRepository;

    private OntModel ontModel;

    private static final Logger logger = LoggerFactory.getLogger(EventConverter.class);

    @Override
    public Phenomena convert(Event event) {

        StopWatch watch = new Slf4JStopWatch("EventConverter::convert");

        Optional<String> eventLink = Optional.ofNullable(event).map(Event::getLink);

        List<EventCategory> categories = Optional.ofNullable(event)
                .map(Event::getCategories)
                .orElse(Collections.emptyList());

        List<OntClass> phenomenaList = getPhenomenaClasses(categories);

        Phenomena phenomena = eventRepository
                .createEvent(ontModel, eventLink.orElseGet(this::generateEventURI))
                .get();

        phenomenaList.stream()
                .forEach(p -> phenomena.getIndividual().addRDFType(p));

        watch.stop();
        return phenomena;
    }

    /**
     * Use EONET event category text to determine Phenomena classes
     * @param categories List of EventCategory
     * @return List of OntClass (subclasses of dd:Phenomena)
     */
    public List<OntClass> getPhenomenaClasses(List<EventCategory> categories) {
        StopWatch watch = new Slf4JStopWatch("EventConverter::getPhenomenaClasses");
        List<OntClass> classes = categories.stream()
                .map(EventCategory::getTitle)
                .map(phenomenaRepository::listClassesByTopic)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        watch.stop();
        return classes;
    }

    private String generateEventURI() {
        return "urn:event/"+ UUID.randomUUID().toString();
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    public void setOntModel(OntModel ontModel) {
        this.ontModel = ontModel;
    }
}
