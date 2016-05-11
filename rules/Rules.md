Rules
=====

## [Candidate Generation Ruleset](candidate-generation/generate_candidates.rules)

Rules that generate candidate workflow resources based on events and datafields (aka variables) in requests.

example:
```
[one-var-candidates:
 (?criteria rdf:type dd:RequestCriteria),
 (?criteria dd:criteriaDataField ?datafield),
 (?criteria dd:criteriaEvent ?event),
 (?service rdf:type dd:OneVariableVisualization),
 (?event dd:physicalManifestation ?feature),
 makeSkolem(?candidate, ?criteria, ?service, ?event, ?feature, ?datafield)
 ->
 (?candidate rdf:type dd:CandidateWorkflow),
 (?candidate dd:candidateEvent ?event),
 (?candidate dd:candidateService ?service),
 (?candidate dd:candidateVariable ?datafield),
 (?candidate dd:candidateFeature ?feature),
 (?criteria dd:candidate ?candidate)
]
```

These rules currently generate a candidate workflow for every variable in the datafield in the request and every service known to the knowledge base.

## [Compatibility Assertion Rulesets](compatibility-assertion-generation)

Rulesets containing rules that generate compatibility assertions based on the combination of some criteria from the candidate.

### [characteristic compatibility ruleset](compatibility-assertion-generation/generate_candidates.rules)

Generates compatibility assertions for service/feature combinations based on the feature characteristics the service is best for visualizing and the applicability of the feature characteristic to analyzing the feature

example:
```
[strong-compatibility:
 (?candidate dd:candidateFeature ?feature),
 (?feature dd:strongCompatibilityFor ?characteristic),
 (?candidate dd:candidateService ?service),
 (?service dd:bestFor ?characteristic),
 makeSkolem(?assertion, ?service, ?feature, ?characteristic)
 ->
 (?candidate dd:compatibilityAssertion ?assertion),
 (?assertion rdf:type dd:CompatibilityAssertion),
 (?assertion dd:compatibilityValue dd:strong_compatibility),
 (?assertion dd:assertionConfidence "1.0"^^xsd:double),
 (?assertion dd:basisForAssertion <urn:rule/characteristic_compatibility/strong-compatibility>),
 (?assertion dd:evidenceForAssertion ?characteristic),
 (?assertion dd:evidenceForAssertion ?feature)
]
```

### [time interval ruleset](compatibility-assertion-generation/time_interval.rules)

Generates compatibility assertions for event/time interval pairings.

example:

```
[hurricane-hourly:
 (?candidate dd:candidateEvent ?event),
 (?event rdf:type dd:Hurricane),
 (?candidate dd:candidateVariable ?variable),
 (?variable dd:timeInterval ?timeInterval),
 equal(?timeInterval, <http://darkdata.tw.rpi.edu/data/time-interval/hourly>),
 makeSkolem(?assertion, dd:Hurricane, ?timeInterval)
 ->
 (?candidate dd:compatibilityAssertion ?assertion),
 (?assertion rdf:type dd:CompatibilityAssertion),
 (?assertion dd:compatibilityValue dd:strong_compatibility),
 (?assertion dd:assertionConfidence "0.5"^^xsd:double),
 (?assertion dd:basisForAssertion <urn:rule/time_interval/hurricane-hourly>)
]
```