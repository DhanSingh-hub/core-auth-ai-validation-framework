# Offline Technical Validation Coverage

Only bounded predicates are counted; source matching and host execution remain unassessed.

Field-runner input population: 21711 metadata sidecars, 21711 payload files, 21711 paired messages, 0 metadata without payload, and 0 payloads without metadata (SINGLE_DIRECTORY).

| Measure | Numerator | Denominator | Percent |
|---|---:|---:|---:|
| limitedOrPartialExecutorBoundCoverage | 58 | 601 | 9.65 |
| decisivePredicateRuleCoverage | 41 | 601 | 6.82 |
| runnerScopeDecisiveRuleCoverage | 41 | 326 | 12.58 |
| caseAnyDecisivePredicateCoverage | 14396 | 21227 | 67.82 |
| aiBrLinkedCandidatePredicateCoverage | 208 | 6880 | 3.02 |
| semanticConfirmedCoverage | N/A | N/A | NOT_ASSESSED_AUTHORIZED_DECISIONS_REQUIRED |
| hostExecutionCoverage | N/A | N/A | NOT_EXECUTED |

Observation source: AI_METADATA_FIELDS_WITH_PHYSICAL_ENVELOPE_AND_STRUCTURE_CHECKS.

- Field values are metadata-derived; physical envelope/structure checks are bounded. No direct-field or wire certification.
- Candidate input directory only; other artifact tiers/directories are not executed by this runner.
- Source-profile binding can implement only part of a composite BR.
- FAIL may be the intended negative target. No independent whole-case expected-outcome agreement is inferred.
- Source interpretations, authorized matches and host execution remain separate gates.
