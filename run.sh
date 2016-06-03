#!/usr/bin/env bash
java -jar -Xmx2048m -XX:MaxPermSize=512m darkdata-rule-service.jar >> rules.log
