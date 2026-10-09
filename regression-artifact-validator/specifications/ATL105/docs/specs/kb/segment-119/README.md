# Segment 119 (Totals with Proprietary Data Load Data Segment) â€” Knowledge Base

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-Item Framework)
**Training Status:** Specification-grounded baseline complete; manual policy gates remain `REVIEW_REQUIRED`; Run1 contains no `llm_phrased` Segment 119 requirements, so AI artifact coverage is currently unavailable rather than inferred.

## Learning Module Index

- [8-Item Framework (Training Handbook)](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#test-solution-implementation-8-item-framework)
- [Reusable Segment Training Questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)
- [Segment 119 End-to-End Flow](segment-119-flow.md)
- [Segment 119 SME/TBA Learning Note](segment-119-sme-tba-learning-note.md)
- [Totals Request Structure Note](totals-request-structure-sme-tba-note.md)
- [Totals Request Structure Flow](totals-request-structure-flow.md)
- [Card-Bucket Totals Note](card-bucket-totals-sme-tba-note.md)
- [Card-Bucket Totals Flow](card-bucket-totals-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Sequence/Lifecycle Note](sequence-lifecycle-sme-tba-note.md)
- [Sequence/Lifecycle Flow](sequence-lifecycle-flow.md)
- [Coverage Closure](coverage/README.md)
- [Final Closure Note](final-closure-sme-tba-note.md)
- [Final Closure Flow](final-closure-flow.md)
- [SME/TBA Input Register](segment-119-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-119-ai-vs-test-requirement-comparison.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Authoritative Rule Catalog](coverage/segment-119-rule-catalog.json)
- [AI-vs-Test Coverage Report](../../../../test-output/ai-artifacts/coverage-reports/POC-AI-Segment-119-BR-Coverage-Ratio-Report.html)

## Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 119 | ATL105 Sections 11.4.1.2 and 12.17 |
| Segment name | Totals with Proprietary Data Load Data Segment | Section 12.17 |
| Purpose | Request Totals and provide current Card Table Version and Host Discount data | Section 12.17 |
| Placement | Data Section 3, Field No. 3 | Sections 11.4.1.2 and 12.17 |
| Data Section 2 | Absent from the Totals with Proprietary Data Load Request | Section 11.4.1.2 |
| Number of Segments (Element 63) | Fixed `01`: Segment 119 is the only segment (`SEG119-R-037`) | Section 11.4.1.2 |
| Origin | Device; Host Discount Timestamp is identified as host-sourced | Section 12.17 |
| Segment Type | Fixed `119` | Element 85 / Section 12.17 |
| Prompt Code | Fixed `990` | Element 78 / Section 12.17 |
| Length | `001-493` alphanumeric; Element 84 is 3 digits. PROVISIONAL: the Section 11.4.1.2 table gives 389 (`SEG119-R-038`, SEG119-SME-008) | Element 84 / Section 12.17 |
| Selection | Only when an approved proprietary-data-load totals selection applies | Manual policy gate `SEG119-R-031` |

The Section 11.4.1.2 text names "Data Segment No. 116" for this message. Its layout table, Section 12.17 and the Chapter 12 matrix all give Segment 119, and Segment 116 is the TransArmor Load Data Segment (Section 12.15), so 116 is treated as a typo.

## Field Layout

| # | Element | Name | Length | Status |
|---:|---:|---|---:|---|
| 1 | 85 | Segment Type | 3 | Required, fixed `119` |
| 2 | 84 | Segment Length | 3 | Required, `001-493` |
| 3 | 44 | Information Byte | 1 | Required |
| 4 | 102 | Terminal Identifier | Variable | Required |
| 5 | 78 | Prompt Code | 3 | Required, fixed `990` |
| 6 | 32 | Employee Number | 4 | Conditional |
| 7 | 65 | Password | 6 | Conditional |
| 8 | 105 | Totals Date | 6 | Required |
| 9 | 43 | Hardware Version | 4 | Required |
| 10 | 96 | Software Version | 8 | Required |
| 11 | 39 | Firmware Version | 8 | Required |
| 12 | 86 | Sequence Number | 6 | Required |
| 13 | 176 | Device Card Table Version | 35 | Required |
| 14 | 179 | Host Discount Timestamp | 12, `CCYYMMDDHHMM` | Required |
| 15 | 20 | Currency Code | 3 | Optional |
| 16 | 42 | Grand Total | 8 | Required |
| 17 | 13 | Card Label | 4 | Required per bucket |
| 18 | 16 | Card Type Total Count | 5 | Required per bucket |
| 19 | 15 | Card Type Total Amount | 8 | Required per bucket |

## Serialization Rules

### Executable Chapter 12 numbered-observation continuation

The [numbered-observation validator](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment119ObservationValidator.java)
is dispatched by the shared Chapter 12 adapter and Chapter 11 envelope. It
checks required fields, source enums/widths, nonzero sequence/counts, calendar
and timestamp validity, Appendix D terminal state and Appendix L currency,
ordered left-aligned N4 card labels and numeric count/amount triples. Merchant
assignment, authentic versions and original host timestamp provenance are not
inferred from their lexical form.

Use `cardBuckets` as an ordered array of objects with numbered text keys
`13`, `16`, `15`. The Section 12.17 label list includes `HD` and contains
19 named labels despite the prose allowing up to 20 buckets; no twentieth
label is invented. Approved-response mandatory/optional bucket prose is not
applied as mandatory request inclusion. Request counts use Chapter 13's
`00001-99999` domain.

Supply observed conditional policy as explicit booleans in `context`:
`employeeNumberRequired`, `passwordRequired`, `totalsRequired`.
Absent policy remains review-required; malformed controls are invalid.
With `context.bucketCoverageComplete: true`, Grand Total is compared to the
sum of supplied financial buckets, excluding `AO`, `SV1`, `SV3`, `SV4`, `HD`.
Without complete scope, no total equality is inferred.

Optional `serializedSegment` is always measured against N3 Segment Length
as ASCII bytes. Explicit `context.wireProfile: "TABLE_17_19"` selects the
table-derived layout: 16 prefix fields, each followed by FS, then concatenated
four/five/eight-byte bucket triples, and a final FS. This profile is checked
for exact order and retained empty-field separators, but its source framing
conflict remains a separate review. Without a selected profile, the validator
does not guess delimiter semantics. Both the 389/493 cap conflict and 17-19/
18-20 field numbering conflict remain review-required.

Chapter 11 supplies authoritative family, direction and observed placement;
contradictory child values are invalid and inputs are not mutated.
The old named-payload `TotalsRequestPayloadValidator` remains unchanged;
its new explicit adapter/evidence overload adds actual field/history checks
while preserving the old default behavior. This continuation does not claim
complete lifecycle closure.

[Independent fixtures and predicate probes](../../../../test-output/test-json/chapter-12-segment-observations.json)
provide 20 Segment 119 executions (two header observations plus 18 predicates).
[Persistent execution evidence](../../../../test-output/test-json/chapter-12-training-evidence.json)
cross-checks exact existing Test anchors for all 20, without approval.
[Boundary and integration tests](../../../../../../src/test/java/com/coreauth/validator/Segment119ObservationValidatorTest.java)
also exercise all 19 listed labels in a measured 453-byte segment, cap
boundaries, swapped/duplicated buckets, nonfinancial exclusion, malformed
dates/controls and source conflict reviews. The covering 51-test gate passed.

**Status: bounded execution added; full Segment 119 coverage remains incomplete.**
Activity-window/lifecycle, selection/retry/cutoff and complete actual producer
mapping remain implementation/evidence work, separate from genuine source
conflicts and deferred approvals.

### Actual named payload and observed-history integration

[Segment119ActualPayloadAdapter](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment119ActualPayloadAdapter.java)
maps actual named fields and every `CategoryTotals` occurrence without copying
metadata values. It also supports a single flat `CardLabel`/`CardTypeTotalCount`/
`CardTypeTotalAmount` triple; supplying both array and flat bucket forms is
invalid rather than silently selecting one. Logical short Card Labels are
space-padded only in the derived observation, never in the input. Unknown
actual field aliases remain explicit reviews; missing/nontext required fields
are not replaced by metadata.

Pass this adapter and an observed evidence object to
`TotalsRequestPayloadValidator.validatePayload(payload, adapter, evidence)` to
enable strict Segment 119 field/history validation. The one-argument overload
retains its legacy behavior. `Atl105AiElementIntake` executes this adapter for
every actual Segment 119 occurrence when its existing semantic mode is enabled
(`--chapter13-semantics`); default metadata-only mode remains unchanged.
The selected source-family root stays authoritative.

Evidence `history.totals` is passed to the existing Chapter 13 processing-history
validator. It assesses activity-date selection, request/response correlation,
settlement ending/roll and reset/accumulation observations using explicit full
dates, booleans and amounts. It does not authenticate those observations or
invent a settlement calendar. See the [Chapter 13 history guide](../13-data-elements.md)
for field shapes. Actual request direction is authoritative; contradictory or
malformed history controls are invalid. Absent/partial history remains review.

Six additional independent actual-payload probes cover valid/invalid timestamp,
zero sequence, nontext repeated count and observed settlement-response selection
and mismatch. The existing Chapter 12 canonical generator executes the actual
adapter for these payloads, preserving target status separately from whole status.
The package now contains **48 BR / 48 TS / 72 TC / 72 TD records**, including
**26 Segment 119 executions**, all with exact unapproved existing Test candidates.
These totals are not whole-segment or whole-chapter closure.

Final focused integration validation passed **87 tests**. Full regression
during the batch ran **2,501 tests**, with **2,499 passing and the two known
appendix failures**, before the final flat-bucket guard; that last guard was
covered by the focused gate. The real 37-case AI batch was rerun and all 74
producer inputs remained hash-identical. See the
[detailed chapter report](../chapter-coverage-gap-register.md) for measured
Segment 119 findings, actual-AI provenance and remaining scope.

- Fields 1-17 are Field Separator-delimited.
- A Field Separator follows Field 17.
- Empty fields in fields 1-17 retain their separators.
- Fields 18-20 are not separated by Field Separators inside each card bucket.
- A Field Separator follows the final occurrence of Card Type Total Amount.
- Segment Length includes Segment Type and the applicable Field Separators.

PROVISIONAL (`SEG119-R-039`, SEG119-SME-009): the rules above follow the Section 12.17 note (Fields 1-17 separated, bucket Fields 18-20). The table above it numbers the bucket as Fields 17-19, which leaves 16 separated fields.

## Card-Bucket Rules

The specification defines up to 20 card buckets in a fixed order. Card types 1-15 appear in all approved responses. Card types 16-20 appear only when data occurs. Categories marked with `*` are non-financial and their amounts are excluded from Grand Total.

## Training Methodology Status

| Item | Status | Evidence |
|---:|---|---|
| 1 Coverage Closure | Complete baseline | 39-rule catalog and field inventory |
| 2 AI Artifact Comparison | Blocked | Run1 has zero `llm_phrased` Segment 119 requirements; no standalone AI package supplied |
| 3 Test-Data Independence | Synthetic baseline permitted | User approved synthetic fixtures; message-level fixtures in `test-input/ai-solution/test-data/segment-119/message/` |
| 4 Traceability Matrix | Baseline defined | Requires Segment 119 BR/TS/TC/TD package |
| 5 Mutation Definition | Catalog categories defined | Field value, omission, separator, bucket, and boundary mutations |
| 6 Mutation Execution | Not executable yet | No Segment 119 Test Solution fixtures/classes exist |
| 7 Validator Enhancement | Message level only | `TotalsRequestPayloadValidator` checks the Section 11.4.1.2 structure; no field-level Segment 119 validator yet |
| 8 Consolidated Sign-Off | Review-gated | Manual policy gates and real artifacts remain open |

## Manual Input Register

- `SEG119-R-031`: exact merchant/device rule selecting Segment 119 instead of ordinary Totals Request;
- `SEG119-R-032`: request-response matching policy and lifecycle completion;
- `SEG119-R-033`: Grand Total/card-bucket reconciliation policy;
- `SEG119-R-034`: retry, duplicate, timeout, and failure behavior;
- `SEG119-R-035`: merchant settlement cutoff and timezone behavior;
- SEG119-SME-008: maximum length, 493 or 389;
- SEG119-SME-009: card-bucket field numbering, 17-19 or 18-20;
- real Segment 119 AI BR/TS/TC/TD package;
- real Segment 119 test data or approval of synthetic fixture provenance.

Production certification is blocked until these gates are resolved.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-119-rule-catalog.json) (36 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 119 |
|---|---|
| SME/TBA learning note | [Learning note](segment-119-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-119-flow.md) |
| Topic deep-dives | [card-bucket-totals](card-bucket-totals-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [sequence-lifecycle](sequence-lifecycle-sme-tba-note.md) · [totals-request-structure](totals-request-structure-sme-tba-note.md) |
| Topic flows | [card-bucket-totals](card-bucket-totals-flow.md) · [field-definitions](field-definitions-flow.md) · [sequence-lifecycle](sequence-lifecycle-flow.md) · [totals-request-structure](totals-request-structure-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-119-business-requirements.md](segment-119-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-119-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-119-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-119-ai-vs-test-requirement-comparison.md) |
