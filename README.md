darkdata-rule-service
=====================

[![Build Status](https://travis-ci.org/tetherless-world/darkdata-rule-service.svg)](https://travis-ci.org/tetherless-world/darkdata-rule-service)


Dark Data Rule Service

## Build & Test

``mvn test``

## Install

``mvn package``

## Run

This project using Spring Boot so it will build an executable JAR file that when run with the ``java -jar`` command will start an embedded tomcat instance on port 8095.

``java -jar -Xmx2048m -XX:MaxPermSize=512m darkdata-rule-service.jar >> rules.log &``

Alternatively you can use the provided ``run.sh`` script to start the service.

## Service Test

Check that the service has been successfully started

``curl localhost:8095/advisor/status``

The status of the currently deployed service can be checked by visiting https://darkdata.tw.rpi.edu/advisor/status.

Get a workflow recommendation using an example input file

``curl -XPOST -H "Content-Type: application/json" localhost:8095/advisor/recommendation --data-binary @examples/eonet_224/request.json``

## API

The advisor service currently publishes a single endpoint that accepts a JSON payload describing the event or event categories and optionally datafields (called data variables) and returns a JSON response that includes a ranked list of candidate Giovanni visualizations.

The current deployed service endpoint is available at ``https://darkdata.tw.rpi.edu/advisor/recommendation``

### The JSON request payload

The service request payload is a simple JSON document with either event or event category information and optionally a list of data variable objects.  If the service request does not contain either event or event category information the service will response with HTTP status 400 **BAD REQUEST**.

Event descriptions are based on EONETv2 JSON event descriptions.  

Data variables are objects with id, which is equivalent with the datafieldId in Giovanni.  Data variable objects can also have product, version, and variable attributes (not included in the examples below) but these attributes are deprecated and support may be removed in a future version of the service.

#### Event request with variables

```
{
  "event": {
    "id": "EONET_224",
    "title": "Hurricane Olaf",
    "link": "http://eonet.sci.gsfc.nasa.gov/api/v2/events/EONET_224",
    "categories": [
      {
        "id": 10,
        "title": "Severe Storms"
      }
    ],
    "geometries": [
      {
        "date": "2015-10-15T00:00:00Z",
        "type": "Point",
        "coordinates": [ [ -117.10, 9.90 ] ]
      }
    ]
  },
  "data_variables": [
    {
      "id": "MAT1NXSLV_5_2_0_UV10M_mag"
    },
    {
      "id": "MYD08_D3_6_Cirrus_Reflectance_Mean"
    },
    {
      "id": "MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean"
    }
  ]
}
```

#### event-categories request with no data variables

```
{
  "event-categories" : [
    {
      "id": 10,
      "title": "Severe Storms"
    }
  ]
}
```

### The service response

The service response is a JSON document comprised of a list of candidate objects and the original request which is included as 'criteria'.

Candidate objects are comprised of a workflow object, feature of interest, and a score.  Currently a workflow object contains the service name and the data variable(s) to pass to the service.  The feature of interest is the physical manifestation of the phenomena that was considered when ranking the candidate workflow.  The score is a numerical indication of how relevant the service believes the candidate workflow is; higher scores indicate a greater believed relevance and the list of candidates are sorted by descreasing score.

```
{
  "candidates" : [ {
    "workflow" : {
      "service" : "QuCl",
      "data_variables" : [ {
        "id" : "MAT1NXSLV_5_2_0_UV10M_mag"
      } ]
    },
    "feature" : "http://www.purl.org/twc/ns/darkdata#Precipitation",
    "score" : 11.0233910753223
  }, {
    "workflow" : {
      "service" : "ArAvTs",
      "data_variables" : [ {
        "id" : "MAT1NXSLV_5_2_0_UV10M_mag"
      } ]
    },
    "feature" : "http://www.purl.org/twc/ns/darkdata#Precipitation",
    "score" : 10.960155108391485
  }, {
    "workflow" : {
      "service" : "MpAn",
      "data_variables" : [ {
        "id" : "MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean"
      } ]
    },
    "feature" : "http://www.purl.org/twc/ns/darkdata#HurricaneEye",
    "score" : 2.82842712474619
  }, {
    "workflow" : {
      "service" : "HvLt",
      "data_variables" : [ {
        "id" : "MYD08_D3_6_Cirrus_Reflectance_Mean"
      } ]
    },
    "feature" : "http://www.purl.org/twc/ns/darkdata#WindFields",
    "score" : 2.82842712474619
  } ],
  "criteria" : {
    "event" : {
      "id" : "EONET_224",
      "title" : "Hurricane Olaf",
      "link" : "http://eonet.sci.gsfc.nasa.gov/api/v2/events/EONET_224",
      "categories" : [ {
        "id" : 10,
        "title" : "Severe Storms"
      } ]
    },
    "data_variables" : [ {
      "id" : "MAT1NXSLV_5_2_0_UV10M_mag"
    }, {
      "id" : "MYD08_D3_6_Cirrus_Reflectance_Mean"
    }, {
      "id" : "MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean"
    } ]
  }
}
```
