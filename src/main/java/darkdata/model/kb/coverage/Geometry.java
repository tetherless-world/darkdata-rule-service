package darkdata.model.kb.coverage;

import darkdata.model.kb.IndividualProxy;
import darkdata.model.ontology.DarkData;
import darkdata.model.ontology.GeoSparql;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author szednik
 */
public class Geometry extends IndividualProxy {

    public Geometry(Individual individual) {
        super(individual);
    }

    public Optional<String> getWKT() {
        return Optional.empty();
    }

    public void setWKT(String wkt) {
        getIndividual().setPropertyValue(GeoSparql.asWKT, ResourceFactory.createPlainLiteral(wkt));
    }

    // TODO setWKT with coordinate system

    public void setStartDateTime(LocalDateTime startDateTime) {
        Literal date = ResourceFactory.createTypedLiteral(startDateTime.format(DateTimeFormatter.ISO_DATE_TIME), XSDDatatype.XSDdateTime);
        getIndividual().setPropertyValue(DarkData.startDateTime, date);
    }

    public void setStartDateTime(String startDateTime) {
        LocalDateTime parsedLocalDate = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_DATE_TIME);
        setStartDateTime(parsedLocalDate);
    }

    public Optional<String> getStartDateTime() {
        return getIndividual().listPropertyValues(DarkData.startDateTime).toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getString)
                .findAny();
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        Literal date = ResourceFactory.createTypedLiteral(endDateTime.format(DateTimeFormatter.ISO_DATE_TIME), XSDDatatype.XSDdateTime);
        getIndividual().setPropertyValue(DarkData.endDateTime, date);
    }

    public void setEndDateTime(String endDateTime) {
        LocalDateTime parsedLocalDate = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_DATE_TIME);
        setEndDateTime(parsedLocalDate);
    }

    public Optional<String> getEndDateTime() {
        return getIndividual().listPropertyValues(DarkData.endDateTime).toList().stream()
                .map(RDFNode::asLiteral)
                .map(Literal::getString)
                .findAny();
    }
}
