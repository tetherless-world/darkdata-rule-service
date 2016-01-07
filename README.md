darkdata-rule-service
=====================

[![Build Status](https://travis-ci.org/tetherless-world/darkdata-rule-service.svg)](https://travis-ci.org/tetherless-world/darkdata-rule-service)


Dark Data Rule Service

## Build & Test

``mvn test``

## Install

``mvn package``

## Run

``java -jar darkdata-rule-service-0.0.1-SNAPSHOT.jar``

## Service Test

Check that the service has been successfully started

``curl localhost:8095/advisor/status``

Get a workflow recommendation using an example input file

``curl -XPOST -H "Content-Type: application/json" localhost:8095/advisor/recommendation --data-binary @examples/request/request.json``