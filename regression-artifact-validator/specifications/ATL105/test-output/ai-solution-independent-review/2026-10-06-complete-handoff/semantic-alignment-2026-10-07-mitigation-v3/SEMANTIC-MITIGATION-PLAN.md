# ATL105 Semantic Alignment Mitigation Plan

SME review is deferred, not approved. This tracker separates automated checks, producer fixes, independent rule review and external host prerequisites.

| ID | Priority | Lane/owner | Stage | Action | Count | Status |
|---|---|---|---|---|---:|---|
| MIT-001 | P0 | AUTO_VERIFIABLE / Test Solution automation | INTAKE/GRAPH | Preserve and verify delivery/catalog/TD identity | 42420 | VERIFIED_FOR_CURRENT_FROZEN_INPUTS |
| MIT-002 | P0 | PRODUCER_EVIDENCE / AI Solution producer | BR/TS/TC | Resolve unattributed cases | 155 | OPEN_REVIEW_DEFERRED |
| MIT-003 | P0 | PRODUCER_DATA / AI Solution producer | TC/TD | Restore missing test-data files | 27 | OPEN_REVIEW_DEFERRED |
| MIT-004 | P1 | TEST_ASSERTION / AI Solution + Test Solution | TC | Add explicit BR-linked TC assertions | 14408 | OPEN_REVIEW_DEFERRED |
| MIT-005 | P1 | EXPECTED_OUTCOME / Test Solution + authorized oracle owner | TC/TD | Define request assertions or authoritative outcomes | 21123 | OPEN_REVIEW_DEFERRED |
| MIT-006 | P1 | INDEPENDENT_RULE_BASELINE / Test Solution rule owners / SME | BR | Complete the independent 601-rule meaning baseline | 593 | OPEN_REVIEW_DEFERRED |
| MIT-007 | P1 | INDEPENDENT_RULE_BASELINE / Test Solution rule owners / SME | BR | Review curated source assertions | 8 | OPEN_REVIEW_DEFERRED |
| MIT-008 | P1 | AI_BR_ALIGNMENT / Test Solution comparison + SME | BR | Compare AI BR meaning against independent baseline | 6887 | OPEN_REVIEW_DEFERRED |
| MIT-009 | P1 | SCENARIO_INTENT / Test Solution comparison + SME | TS | Verify scenario exercises BR conditions and behavior | 12679 | OPEN_REVIEW_DEFERRED |
| MIT-010 | P1 | NEGATIVE_EFFECTIVENESS / Test Solution validation | TC/TD | Pair negatives with valid controls and one intended mutation | 2006 | OPEN_REVIEW_DEFERRED |
| MIT-011 | P1 | TD_DEPENDENCIES / AI Solution producer + Test Solution validation | TD | Resolve physical TD provisional/unavailable dependencies | 21210 | OPEN_REVIEW_DEFERRED |
| MIT-012 | P1 | TD_PREDICATE / Test Solution validation | TD | Expand source-grounded field/companion validators | 5981 | OPEN_REVIEW_DEFERRED |
| MIT-013 | P2 | LIFECYCLE / Test Solution comparison + oracle owner | TS/TC/TD | Validate flow roles and transaction correlation | 89 | OPEN_REVIEW_DEFERRED |
| MIT-014 | P2 | WIRE_COMPATIBILITY / Protocol/test environment owner | TD | Capture and validate actual full-message wire bytes | 21237 | BLOCKED_EXTERNAL_EVIDENCE |
| MIT-015 | P2 | HOST_EXECUTION / Authorized processor/test host owner | EXECUTION | Run qualified controls in an authorized host environment | 21123 | BLOCKED_EXTERNAL_EVIDENCE |
| MIT-016 | P0 | PRODUCER_OR_SCOPE_REVIEW / AI Solution producer + Test Solution rule owner | TS/TC | Resolve scenarios without a case | 3732 | OPEN_REVIEW_DEFERRED |
| MIT-017 | P0 | PRODUCER_EVIDENCE / AI Solution producer | BR/TS/TC/TD | Reconcile the late matrix row/edge discrepancy | 63 | BLOCKED_PRODUCER_EVIDENCE |
| MIT-018 | P1 | PREDICATE_TRIAGE / Test Solution validation | TD | Triage bounded predicate failures by test intent | 56 | OPEN_REVIEW_DEFERRED |

## Acceptance criteria

- **MIT-001 Preserve and verify delivery/catalog/TD identity:** Every used catalog/payload/metadata file matches its frozen manifest hash and every metadata-to-TC/TS join is exact. Blocker: `NONE_FOR_FROZEN_RUN`. Linked SME queries: 0.
- **MIT-002 Resolve unattributed cases:** Supply source-justified BR attribution or explicitly classify as non-BR-scope; no keyword or arithmetic mapping. Blocker: `MISSING_BR_EDGES`. Linked SME queries: 155.
- **MIT-003 Restore missing test-data files:** Provide complete request/metadata artifacts for each case and verify hashes/joins; mark unsupported cases blocked, never pass. Blocker: `MISSING_PHYSICAL_TD`. Linked SME queries: 27.
- **MIT-004 Add explicit BR-linked TC assertions:** Each linked case asserts the specific BR obligation/condition; assertion has a source/context link and validator mapping. Blocker: `TC_OBJECTIVE_ABSENT`. Linked SME queries: 14,408.
- **MIT-005 Define request assertions or authoritative outcomes:** Every case declares validated PASS/FAIL assertions or explicit request-only scope; host outcomes require an approved oracle/state fixture. Blocker: `EXPECTED_RESPONSE_ABSENT`. Linked SME queries: 21,123.
- **MIT-006 Complete the independent 601-rule meaning baseline:** Atomic interpretation includes applicability, conditions, required behavior, exceptions and evidence; rule is independently reviewed. Blocker: `SOURCE_MEANING_NOT_CURATED`. Linked SME queries: 593.
- **MIT-007 Review curated source assertions:** Review entire rule scope, not code-value fragments; source-backed candidates remain nonapproved until reviewed. Blocker: `CURATED_ASSERTIONS_REVIEW_REQUIRED`. Linked SME queries: 8.
- **MIT-008 Compare AI BR meaning against independent baseline:** Record confirmed/partial/missing/unassessed for each in-scope independent BR with evidence; many-to-many is allowed, ambiguity stays open. Blocker: `NO_COMPLETE_INDEPENDENT_INTERPRETATIONS`. Linked SME queries: 6,887.
- **MIT-009 Verify scenario exercises BR conditions and behavior:** Trace each scenario condition/action/outcome to the confirmed BR interpretation, including negative and lifecycle context. Blocker: `TS_INTENT_UNPROVEN`. Linked SME queries: 12,679.
- **MIT-010 Pair negatives with valid controls and one intended mutation:** Complete supported control passes; one isolated mutation violates its intended rule; unrelated checks stay valid. Blocker: `NO_VALID_CONTROL_OR_FULL_SCOPE`. Linked SME queries: 2,006.
- **MIT-011 Resolve physical TD provisional/unavailable dependencies:** Review metadata provisional values, unavailable fields and unresolved segments against rule applicability; distinguish counts of occurrences from unique defects. Blocker: `PROVISIONAL_OR_UNRESOLVED_METADATA`. Linked SME queries: 21,096.
- **MIT-012 Expand source-grounded field/companion validators:** Implement field and companion checks against independently interpreted rules, actual payload, correct location and applicable context. Blocker: `PREDICATE_NOT_SUPPORTED`. Linked SME queries: 6,010.
- **MIT-013 Validate flow roles and transaction correlation:** Verify original/follow-up message roles, applicability, sequence/approval reuse and expected outcomes across physical legs. Blocker: `LIFECYCLE_ORACLE_OR_CONTEXT`. Linked SME queries: 89.
- **MIT-014 Capture and validate actual full-message wire bytes:** Bind supported network framing, TPDU, Section 1, companion segments, separators and lengths to actual captured bytes. Blocker: `WIRE_CAPTURE_AND_PROFILE_MISSING`. Linked SME queries: 0.
- **MIT-015 Run qualified controls in an authorized host environment:** Approved host/profile, state fixture, authoritative oracle, actual request/response, environment identity, replayable logs and approval. Blocker: `AUTHORIZED_HOST_AND_ORACLE_MISSING`. Linked SME queries: 0.
- **MIT-016 Resolve scenarios without a case:** Generate a BR-linked TC or provide a source-backed, explicitly out-of-scope disposition for every no-TC scenario. Blocker: `SCENARIO_WITHOUT_TC`. Linked SME queries: 3,732.
- **MIT-017 Reconcile the late matrix row/edge discrepancy:** Supply complete matrix JSON, exact source catalog hashes and the 63 explicit edges; independently validate each linked artifact and orphan definition. Blocker: `COMPLETE_PRODUCER_GRAPH_NOT_SUPPLIED`. Linked SME queries: 0.
- **MIT-018 Triage bounded predicate failures by test intent:** Classify each failure as intended isolated negative, unrelated defect or unresolved rule/context; never count intended negative FAIL as test failure or auto-ignore it. Blocker: `INTENT_AND_FULL_SCOPE_ADJUDICATION`. Linked SME queries: 6,010.

## Automatic pass rule

ONLY_EVIDENCE_BACKED_SUPPORTED_ASSERTIONS_MAY_PASS; MISSING/UNSUPPORTED=NOT_ASSESSED

Automated PASS applies only to supported, evidence-backed assertions. It does not approve business meaning, waive review, or certify host execution.