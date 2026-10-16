# Message Semantic Triage and Execution Readiness

This is a preliminary pair review for the 44 same-category Message-to-Message candidates. It is evidence-linked triage, not an authorized semantic decision register. All pair decisions remain OPEN_REVIEW_REQUIRED.

| Preliminary relation | Pair count |
|---|---:|
| PARTIAL_FORMAT_WIDTH_OVERLAP | 7 |
| POTENTIAL_LENGTH_SCOPE_CONFLICT | 1 |
| PRELIMINARY_PARTIAL_OR_COMPOSITE_OVERLAP | 7 |
| PRELIMINARY_SAME_SUBRULE_OVERLAP | 10 |
| RELATED_CONTEXT_DIFFERENT_OBLIGATION | 12 |
| RELATED_FIELD_DIFFERENT_OBLIGATION | 5 |
| SOURCE_ANCHOR_CONFLICT_REVIEW | 2 |
| **Total pairs triaged** | **44** |

## Offline Predicate Execution

The full-delivery field/structure runner has results for 30 of 30 distinct Test BR IDs in this 44-pair candidate set. 4 rules produced bounded PASS/FAIL results over 29745 per-rule AI candidate cases; pair-candidate outcome rows are {"FAIL": 32621, "PASS": 23998}.

Across all 243 Message Test BRs, 242 are in the 601-rule runner baseline; 28 have decisive bounded results. Run states: {'NO_RESULT_FROM_THIS_RUNNER': 204, 'BOUND_NOT_EXERCISED': 10, 'DECISIVE_PREDICATE_RESULT_OBSERVED': 28}. Outcome rows on AI payloads: {"FAIL": 130124, "PASS": 188322, "REVIEW_REQUIRED": 96}. These are predicate outputs on AI payloads, not executions of Test Solution fixtures and not semantic match decisions. A FAIL is a failing bounded predicate observation, not a whole-case verdict.

| Runner binding | Distinct Test BRs |
|---|---:|
| OUTSIDE_THIS_RUNNER_SCOPE | 20 |
| PROFILE_BACKED_LIMITED_EXECUTION | 5 |
| REVIEW_REQUIRED_NO_BOUND_EXECUTOR | 5 |

Test Message `BR-RULE-*` chains remain REVIEW_REQUIRED placeholders. The 58 exact-anchor alternative Test Data vectors are also REVIEW_REQUIRED; no approved Message Test fixture is execution-ready. The aggregate package validator found complete links (zero BR->TS, TS->TC, or TC->TD gaps) but `executionReady=false`, because link integrity does not establish validated payloads or SME approval.

The package-level chain validator found zero BR->TS, TS->TC or TC->TD link gaps and 1,119 EXECUTABLE-readiness Test Data records overall (473 BR chains), but correctly reports executionReady=false. No official Message BR-rule chain or exact-anchor Message alternative has EXECUTABLE Test Data.

SemanticEvidenceValidatorTest (14), CanonicalTraceabilityValidatorTest (46), and the isolated Test Solution JSON/canonical traceability test (1) passed. The broader TestSolutionJsonFormatTest also has an unrelated failure on 22 Appendix AE/AF KB rule-catalog rows missing its required `ruleId/title/sourceAnchor` shape.

Formal semantic adjudication still requires authorized source interpretation and complete claims/quote evidence. Host/Test-fixture execution is blocked by the absent execution-ready Message fixture and request/response oracle. No `MATCH`, `PARTIAL`, or `NOT_MATCH` is recorded as final.
