# Delivery-Wide AI Artifact Assessment

All approved and candidate catalog occurrences are retained. Producer status grants no acceptance.

| Measure | Numerator | Denominator | Percent | Status |
|---|---:|---:|---:|---|
| candidateReach | 353 | 601 | 58.74 | STRUCTURAL_CANDIDATE_ONLY |
| physicalCompleteChainCandidateReach | 327 | 601 | 54.41 | STRUCTURAL_CANDIDATE_ONLY |
| semanticConfirmedCoverage | None | 601 | None | NOT_ASSESSED_AUTHORIZED_DECISIONS_REQUIRED |
| executedValidationCoverage | None | 601 | None | NOT_ASSESSED_EXECUTION_EVIDENCE_REQUIRED |
| locationAwareCandidateReach | 359 | 601 | 59.73 | STRUCTURAL_CANDIDATE_ONLY |
| locationAwarePhysicalCompleteChainCandidateReach | 329 | 601 | 54.74 | STRUCTURAL_CANDIDATE_ONLY |
| supplementCandidateReach | 306 | 363 | 84.3 | STRUCTURAL_CANDIDATE_ONLY |
| supplementPhysicalCompleteChainCandidateReach | 243 | 363 | 66.94 | STRUCTURAL_CANDIDATE_ONLY |
| extendedCandidateReach | 665 | 964 | 68.98 | STRUCTURAL_CANDIDATE_ONLY |
| extendedPhysicalCompleteChainCandidateReach | 572 | 964 | 59.34 | STRUCTURAL_CANDIDATE_ONLY |
| extendedSemanticConfirmedCoverage | None | 964 | None | NOT_ASSESSED_AUTHORIZED_DECISIONS_REQUIRED |

## Population And Triage

Files inventoried: 33014. Physical file kinds: {"AUXILIARY":18,"CATALOG":4,"METADATA":16496,"PAYLOAD":16496}.

Recursive field shapes inspected for 32992 payload/metadata JSON files; scalar occurrences by kind: {"METADATA":6690680,"PAYLOAD":544240}. No field values were exported.

Declared payload/metadata pairs: 17193 ({"PAIRED_AFTER_EXPLICIT_LAYOUT_PREFIX":17193}). Unreferenced payloads: 0; unreferenced metadata sidecars: 0; payloads without valid sidecars: 0.

Unique IDs: {"BR":8470,"TC":15625,"TS":11356}. Retained occurrences: {"BR":8470,"TC":15625,"TS":11356}.

Content-conflicting IDs: 0. TCs with clean complete physical routes: 15625. TCs without such a route: 0.

Candidate-reached Test BRs without clean complete routes: 26. These are structural follow-up queues, not proof of missing business coverage.

## Location-Aware Matching

Supplemental segment-free Test BRs: 363 {"ELEMENT_DEFINITION":232,"MESSAGE_LOCATION":131}. The original measures above are unchanged; location-aware and extended measures are reported separately.

AI BRs previously NOT_MATCHABLE_SEGMENT_UNRESOLVED: 637; resolution {"ELEMENT_DEFINITION_ONLY_LOCATION_UNKNOWN":12,"NON_SEGMENT_LOCATION_CANDIDATE":377,"SEGMENT_SCOPED_ELEMENT_SEGMENT_OMITTED":248}; with a location-aware candidate: 637. SEGMENT_SCOPED_ELEMENT_SEGMENT_OMITTED rows are AI defect candidates in segment-omission-findings.jsonl.

## Outstanding Work

Anchor proposals require source-backed interpretation; candidate pair decisions require authorized source review. Physical file/object checks are not full payload or wire validation. Auxiliary matrices/archives remain unassessed. Semantic confirmation and executed validation remain unavailable.

See summary.json and the hashed JSONL ledgers for occurrence, conflict, chain, anchor and review evidence. This is not full-message, semantic or host certification.
