package darkdata.model.ontology;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * Vocabulary definitions from src/main/resources/rdf/darkdata.ttl
 * @author Auto-generated by schemagen on 12 Oct 2015 15:13
 */
public class DarkData {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.purl.org/twc/ns/darkdata#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final OntClass AerosolOpticalDepth = m_model.createClass("http://www.purl.org/twc/ns/darkdata#AerosolOpticalDepth");
    
    public static final OntClass AshPlume = m_model.createClass("http://www.purl.org/twc/ns/darkdata#AshPlume");
    
    public static final OntClass AtmosphericConcentration = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#AtmosphericConcentration");

    public static final OntClass Column_SO2 = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Column_SO2");
    
    /** <p>Representation of the measurement in a data file</p> */
    public static final OntClass DataVariable = m_model.createClass("http://www.purl.org/twc/ns/darkdata#DataVariable");
    
    /** <p>Observation whose result is a data variable</p> */
    public static final OntClass DataVariableObservation = m_model.createClass("http://www.purl.org/twc/ns/darkdata#DataVariableObservation");
    
    public static final OntClass Drought = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Drought");
    
    public static final OntClass DustAndHaze = m_model.createClass("http://www.purl.org/twc/ns/darkdata#DustAndHaze");
    
    public static final OntClass DustStorm = m_model.createClass("http://www.purl.org/twc/ns/darkdata#DustStorm");
    
    /** <p>Related to all manner of shaking and displacement. Certain aftermath of earthquakes 
     *  may also be found under landslides and floods.</p>
     */
    public static final OntClass Earthquake = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Earthquake");
    
    public static final OntClass ElevatedSurfaceTemperature = m_model.createClass("http://www.purl.org/twc/ns/darkdata#ElevatedSurfaceTemperature");
    
    public static final OntClass Emission_SO2 = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Emission_SO2");
    
    public static final OntClass Event = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Event");
    
    public static final OntClass Flood = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Flood");
    
    public static final OntClass Histogram = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Histogram");
    
    public static final OntClass Hovmoller = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Hovmoller");
    
    public static final OntClass Hurricane = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Hurricane");
    
    public static final OntClass HurricaneEye = m_model.createClass("http://www.purl.org/twc/ns/darkdata#HurricaneEye");
    
    /** <p>Related to landslides and variations thereof: mudslides, avalanche.</p> */
    public static final OntClass Landslide = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Landslide");
    
    /** <p>Events that have been human-induced and are extreme in their extent.</p> */
    public static final OntClass ManMade = m_model.createClass("http://www.purl.org/twc/ns/darkdata#ManMade");
    
    public static final OntClass ObservableProperty = m_model.createClass("http://www.purl.org/twc/ns/darkdata#ObservableProperty");
    
    /** <p>An observable occurrence of particular physical (as opposed to dynamic or 
     *  synoptic) significance within the atmosphere</p>
     */
    public static final OntClass Phenomena = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Phenomena");

    public static final OntClass ShortDurationPhenomena = m_model.createClass("http://www.purl.org/twc/ns/darkdata#ShortDurationPhenomena");
    
    public static final OntClass PhysicalManifestation = m_model.createClass("http://www.purl.org/twc/ns/darkdata#PhysicalManifestation");
    
    public static final OntClass Radiance = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Radiance");
    
    public static final OntClass Rainrate = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Rainrate");
    
    /** <p>Related to all ice that resides on oceans and lakes, including sea and lake 
     *  ice (permanent and seasonal) and icebergs.</p>
     */
    public static final OntClass SeaAndLakeIce = m_model.createClass("http://www.purl.org/twc/ns/darkdata#SeaAndLakeIce");
    
    public static final OntClass SeasonalTimeSeries = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#SeasonalTimeSeries");

    public static final OntClass SevereStorm = m_model.createClass("http://www.purl.org/twc/ns/darkdata#SevereStorm");
    
    /** <p>Related to snow events, particularly extreme/anomalous snowfall in either 
     *  timing or extent/depth.</p>
     */
    public static final OntClass Snow = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#Snow");

    public static final OntClass Temperature = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#Temperature");

    /** <p>Related to anomalous land temperatures, either heat or cold.</p> */
    public static final OntClass TemperatureExtreme = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#TemperatureExtreme");

    public static final OntClass TimeAveragedMap = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#TimeAveragedMap");

    public static final OntClass UserDefinedClimatology = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#UserDefinedClimatology");

    public static final OntClass VerticalProfile = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#VerticalProfile");

    public static final OntClass Visualization = m_model.createClass( "http://www.purl.org/twc/ns/darkdata#Visualization");

    /** <p>Related to both the physical effects of an eruption (rock, ash, lava) and 
     *  the atmospheric (ash and gas plumes).</p>
     */
    public static final OntClass VolcanicEruption = m_model.createClass("http://www.purl.org/twc/ns/darkdata#VolcanicEruption");
    
    /** <p>Related to events that alter the appearance of water: phytoplankton, red tide, 
     *  algae, sediment, whiting, etc.</p>
     */
    public static final OntClass WaterColor = m_model.createClass("http://www.purl.org/twc/ns/darkdata#WaterColor");
    
    public static final OntClass Wildfire = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Wildfire");
    
    public static final OntClass WindFields = m_model.createClass("http://www.purl.org/twc/ns/darkdata#WindFields");
    
    public static final OntClass ZonalMeans = m_model.createClass("http://www.purl.org/twc/ns/darkdata#ZonalMeans");
    
    public static final Individual aerosol_optical_depth_thickness = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#aerosol_optical_depth_thickness", DarkData.AerosolOpticalDepth);
    
    public static final Individual atmospheric_concentration_SO2 = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#atmospheric_concentration_SO2",DarkData.AtmosphericConcentration);
    
    public static final Individual infrared_radiance = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#infrared_radiance", DarkData.Radiance);
    
    public static final Individual surface_temperature = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#surface_temperature", DarkData.Temperature);
    
    public static final Individual visible_radiance = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#visible_radiance", DarkData.Radiance);

    public static final OntProperty physicalManifestation = m_model.createOntProperty( "http://www.purl.org/twc/ns/darkdata#physicalManifestation" );

    public static final AnnotationProperty topic = m_model.createAnnotationProperty( "http://www.purl.org/twc/ns/darkdata#topic");

    public static final AnnotationProperty scienceKeyword = m_model.createAnnotationProperty( "http://www.purl.org/twc/ns/darkdata#scienceKeyword");

    public static final ObjectProperty observableProperty = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#observableProperty" );

    public static final OntClass CandidateWorkflow = m_model.createClass("http://www.purl.org/twc/ns/darkdata#CandidateWorkflow");

    public static final ObjectProperty candidate = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#candidate" );

    public static final ObjectProperty candidateEvent = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#candidateEvent" );

    public static final OntClass CompatibilityAssertion = m_model.createClass("http://www.purl.org/twc/ns/darkdata#CompatibilityAssertion");

    public static final ObjectProperty compatibilityValue = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#compatibilityValue" );

    public static final OntClass CompatibilityValue = m_model.createClass("http://www.purl.org/twc/ns/darkdata#CompatibilityValue");

    public static final Individual slight_compatibility = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#slight_compatibility", DarkData.CompatibilityValue);

    public static final Individual some_compatibility = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#some_compatibility", DarkData.CompatibilityValue);

    public static final Individual strong_compatibility = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#strong_compatibility", DarkData.CompatibilityValue);

    public static final Individual indifferent_compatibility = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#indifferent_compatibility", DarkData.CompatibilityValue);

    public static final Individual negative_compatibility = m_model.createIndividual("http://www.purl.org/twc/ns/darkdata#negative_compatibility", DarkData.CompatibilityValue);

    public static final ObjectProperty candidateService = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#candidateService" );

    public static final OntClass Dataset = m_model.createClass("http://www.purl.org/twc/ns/darkdata#Dataset");

    public static final ObjectProperty variable = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#variable" );

    public static final ObjectProperty dataset = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#dataset" );

    public static final DatatypeProperty shortName = m_model.createDatatypeProperty( "http://www.purl.org/twc/ns/darkdata#shortName" );

    public static final DatatypeProperty longName = m_model.createDatatypeProperty( "http://www.purl.org/twc/ns/darkdata#longName" );

    public static final DatatypeProperty assertionConfidence = m_model.createDatatypeProperty( "http://www.purl.org/twc/ns/darkdata#assertionConfidence" );

    public static final ObjectProperty compatibilityAssertion = m_model.createObjectProperty( "http://www.purl.org/twc/ns/darkdata#compatibilityAssertion" );

}
