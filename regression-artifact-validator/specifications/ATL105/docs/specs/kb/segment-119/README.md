# Segment 119 (Totals with Proprietary Data Load Data Segment) â€” Knowledge Base

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../../../SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Training Status:** Specification-grounded baseline complete; manual policy gates remain `REVIEW_REQUIRED`; Run1 contains no `llm_phrased` Segment 119 requirements, so AI artifact coverage is currently unavailable rather than inferred.

## Learning Module Index

- [Segment Training Methodology](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md)
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
| Origin | Device; Host Discount Timestamp is identified as host-sourced | Section 12.17 |
| Segment Type | Fixed `119` | Element 85 / Section 12.17 |
| Prompt Code | Fixed `990` | Element 78 / Section 12.17 |
| Length | `001-493` alphanumeric; Element 84 is 3 digits | Element 84 / Section 12.17 |
| Selection | Only when an approved proprietary-data-load totals selection applies | Manual policy gate `SEG119-R-031` |

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

- Fields 1-17 are Field Separator-delimited.
- A Field Separator follows Field 17.
- Empty fields in fields 1-17 retain their separators.
- Fields 18-20 are not separated by Field Separators inside each card bucket.
- A Field Separator follows the final occurrence of Card Type Total Amount.
- Segment Length includes Segment Type and the applicable Field Separators.

## Card-Bucket Rules

The specification defines up to 20 card buckets in a fixed order. Card types 1-15 appear in all approved responses. Card types 16-20 appear only when data occurs. Categories marked with `*` are non-financial and their amounts are excluded from Grand Total.

## Training Methodology Status

| Item | Status | Evidence |
|---:|---|---|
| 1 Coverage Closure | Complete baseline | 36-rule catalog and field inventory |
| 2 AI Artifact Comparison | Blocked | Run1 has zero `llm_phrased` Segment 119 requirements; no standalone AI package supplied |
| 3 Test-Data Independence | Synthetic baseline permitted | User approved synthetic fixtures; none currently supplied |
| 4 Traceability Matrix | Baseline defined | Requires Segment 119 BR/TS/TC/TD package |
| 5 Mutation Definition | Catalog categories defined | Field value, omission, separator, bucket, and boundary mutations |
| 6 Mutation Execution | Not executable yet | No Segment 119 Test Solution fixtures/classes exist |
| 7 Validator Enhancement | Not started | Depends on fixture and mutation execution |
| 8 Consolidated Sign-Off | Review-gated | Manual policy gates and real artifacts remain open |

## Manual Input Register

- `SEG119-R-031`: exact merchant/device rule selecting Segment 119 instead of ordinary Totals Request;
- `SEG119-R-032`: request-response matching policy and lifecycle completion;
- `SEG119-R-033`: Grand Total/card-bucket reconciliation policy;
- `SEG119-R-034`: retry, duplicate, timeout, and failure behavior;
- `SEG119-R-035`: merchant settlement cutoff and timezone behavior;
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
