package darkdata.factory;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.kb.Phenomena;
import darkdata.repository.EventRepository;
import darkdata.repository.PhenomenaRepository;
import darkdata.web.api.event.eonet.Event;
import darkdata.web.api.event.eonet.EventCategory;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author szednik
 */
@Component
public class EventResourceFactory implements ResourceFactory<OntResource, OntModel, Event> {

    @Autowired
    private PhenomenaRepository phenomenaRepository;

    @Autowired
    private EventRepository eventRepository;

    private static final Logger logger = LoggerFactory.getLogger(EventResourceFactory.class);

    @Override
    public Optional<OntResource> get(final OntModel model, final Event event) {

        Optional<String> eventLink = Optional.ofNullable(event).map(Event::getLink);

        List<EventCategory> categories = Optional.ofNullable(event)
                .map(Event::getCategories)
                .orElse(Collections.emptyList());

        List<OntClass> phenomenaList = getPhenomenaClasses(categories);

        Optional<Phenomena> phenomena = eventRepository
                .createEvent(model, eventLink.orElseGet(this::generateEventURI));

        phenomena.ifPresent(e -> phenomenaList.stream()
                .forEach(p -> e.getIndividual().addRDFType(p)));

        return phenomena.map(IndividualProxy::getIndividual);
    }

    /**
     * Use EONET event category text to determine Phenomena classes
     * @param categories List of EventCategory
     * @return List of OntClass (subclasses of dd:Phenomena)
     */
    private List<OntClass> getPhenomenaClasses(final List<EventCategory> categories) {
        return categories.stream()
                .map(EventCategory::getTitle)
                .map(phenomenaRepository::listClassesByTopic)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private String generateEventURI() {
        return "urn:event/"+ UUID.randomUUID().toString();
    }
}
