# Segment 109 Business Requirements

**Segment:** 109 — Electronic Mail Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.11, 10.11.1, 10.11.2, 11.5.1, 11.5.2, 12.8  
**Oracle:** [segment-109-rule-catalog.json](coverage/segment-109-rule-catalog.json) (22 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 109 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 109 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 9 |
| applicability | 3 |
| serialization | 3 |
| structure | 2 |
| interdependency | 2 |
| response | 2 |
| compatibility | 1 |
| **Total** | **22** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG109-001 | structure | An Electronic Mail Request contains Data Section 1 (Elements 55 and 63) and Segment 109 as its only segment, in Field No. 3; it has no Segment 100 | The segment's structural position and composition match the rule. | `SEG109-R-001` §11.5.1 | SPEC_DERIVED |
| BR-SEG109-002 | structure | Data Section 1 contains required Message Format Version Identifier (Element 55) and Number of Segments (Element 63), separated and followed by a Field Separator | The segment's structural position and composition match the rule. | `SEG109-R-002` §11.5.1 | SPEC_DERIVED |
| BR-SEG109-003 | applicability | Segment 109 is in Field No. 3 and is sent only for transactions requiring electronic mail | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG109-R-003` §11.5.1 | REVIEW_REQUIRED |
| BR-SEG109-004 | field | Segment Type is fixed value 109 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-004` §12.8 | SPEC_DERIVED |
| BR-SEG109-005 | field | Segment Length is three numeric characters and includes Segment Type and Field Separators | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-005` §12.8 | SPEC_DERIVED |
| BR-SEG109-006 | serialization | Segment 109 maximum length is 232 alphanumeric characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG109-R-006` §12.8 | SPEC_DERIVED |
| BR-SEG109-007 | serialization | Fields are ordered as defined in Section 12.8 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG109-R-007` §12.8 | SPEC_DERIVED |
| BR-SEG109-008 | serialization | Every Segment 109 field is separated by a Field Separator, including empty fields | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG109-R-008` §12.8 | SPEC_DERIVED |
| BR-SEG109-009 | field | Information Byte is required and one numeric character | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-009` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-010 | field | Terminal Identifier is required and uses the approved device identifier format | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-010` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-011 | compatibility | Prompt Code is required and must be 981 for standard retrieval, 996 for proprietary-card-data retrieval, or 995 for submission | Only the permitted companion segments / message families carry this segment. | `SEG109-R-011` §10.11 | REVIEW_REQUIRED |
| BR-SEG109-012 | field | Block Number is required and three numeric characters | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-012` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-013 | field | Employee Number, when populated, is numeric with maximum length four | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-013` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-014 | field | Password, when populated, is alphanumeric with maximum length six and must be masked in fixtures | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-014` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-015 | field | Sequence Number is required and six numeric characters | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-015` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-016 | field | Local Time, Extract Date, and Extract Time, when populated, are numeric with lengths four, six, and four respectively | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG109-R-016` §12.8 | REVIEW_REQUIRED |
| BR-SEG109-017 | interdependency | Text Data Length, when populated, is three numeric characters and represents Text Data length | The dependent values agree with each other. | `SEG109-R-017` §12.8 | SPEC_DERIVED |
| BR-SEG109-018 | interdependency | Text Data, when populated, is alphanumeric with maximum length 150 and agrees with Text Data Length | The dependent values agree with each other. | `SEG109-R-018` §12.8 | SPEC_DERIVED |
| BR-SEG109-019 | applicability | Retrieval requests use Prompt Code 981, or 996 when retrieving proprietary card data, and allow up to 750 bytes of print data per request | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG109-R-019` §10.11.1 | REVIEW_REQUIRED |
| BR-SEG109-020 | applicability | Submission requests use Prompt Code 995, contain up to 217 bytes of data, and receive a confirmation response | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG109-R-020` §10.11.2 | REVIEW_REQUIRED |
| BR-SEG109-021 | response | Electronic Mail Response is variable length, positional, and contains no Field Separators | The response carries the stated values in the stated positions. | `SEG109-R-021` §11.5.2 | SPEC_DERIVED |
| BR-SEG109-022 | response | Electronic Mail Response contains required Response Code, Download Indicator, Initiation Date, Initiation Time, Sequence Number, and Block Number; optional Mail Text Data Length and Mail Text Data are correlated | The response carries the stated values in the stated positions. | `SEG109-R-022` §11.5.2 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG109-NEG-001 | `SEG109-R-001` | MUT-010 structural requirement | Validation error citing SEG109-R-001 |
| BR-SEG109-NEG-002 | `SEG109-R-002` | MUT-005 required field omitted | Validation error citing SEG109-R-002 |
| BR-SEG109-NEG-003 | `SEG109-R-003` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-004 | `SEG109-R-004` | MUT-001 wrong fixed value | Validation error citing SEG109-R-004 |
| BR-SEG109-NEG-005 | `SEG109-R-005` | MUT-001 wrong fixed value | Validation error citing SEG109-R-005 |
| BR-SEG109-NEG-006 | `SEG109-R-006` | MUT-003 length violation | Validation error citing SEG109-R-006 |
| BR-SEG109-NEG-007 | `SEG109-R-007` | MUT-010 structural requirement | Validation error citing SEG109-R-007 |
| BR-SEG109-NEG-008 | `SEG109-R-008` | MUT-010 structural / separator violation | Validation error citing SEG109-R-008 |
| BR-SEG109-NEG-009 | `SEG109-R-009` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-010 | `SEG109-R-010` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-011 | `SEG109-R-011` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-012 | `SEG109-R-012` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-013 | `SEG109-R-013` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-014 | `SEG109-R-014` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-015 | `SEG109-R-015` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-016 | `SEG109-R-016` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-017 | `SEG109-R-017` | MUT-003 length violation | Validation error citing SEG109-R-017 |
| BR-SEG109-NEG-018 | `SEG109-R-018` | MUT-003 length violation | Validation error citing SEG109-R-018 |
| BR-SEG109-NEG-019 | `SEG109-R-019` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-020 | `SEG109-R-020` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG109-NEG-021 | `SEG109-R-021` | MUT-003 length violation | Validation error citing SEG109-R-021 |
| BR-SEG109-NEG-022 | `SEG109-R-022` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG109-003` (`SEG109-R-003`) — REVIEW_REQUIRED
- `BR-SEG109-009` (`SEG109-R-009`) — REVIEW_REQUIRED
- `BR-SEG109-010` (`SEG109-R-010`) — REVIEW_REQUIRED
- `BR-SEG109-011` (`SEG109-R-011`) — REVIEW_REQUIRED
- `BR-SEG109-012` (`SEG109-R-012`) — REVIEW_REQUIRED
- `BR-SEG109-013` (`SEG109-R-013`) — REVIEW_REQUIRED
- `BR-SEG109-014` (`SEG109-R-014`) — REVIEW_REQUIRED
- `BR-SEG109-015` (`SEG109-R-015`) — REVIEW_REQUIRED
- `BR-SEG109-016` (`SEG109-R-016`) — REVIEW_REQUIRED
- `BR-SEG109-019` (`SEG109-R-019`) — REVIEW_REQUIRED
- `BR-SEG109-020` (`SEG109-R-020`) — REVIEW_REQUIRED
- `BR-SEG109-022` (`SEG109-R-022`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG109-R-003): Confirm every transaction/event that requires an Electronic Mail Request, and whether Segment 109 is ever permitted outside the Section 11.5 envelope.
- **P-02** (SEG109-R-009): Provide the Information Byte value catalog and the single-message/multimessage behavior for each value.
- **P-03** (SEG109-R-010): Provide the terminal identifier format, allowed lengths, and approved synthetic representation for this environment.
- **P-04** (SEG109-R-011): Confirm whether 981, 996, and 995 are the complete permitted Prompt Code set and identify any implementation-specific restrictions.
- **P-05** (SEG109-R-012): Define Block Number allocation, first/last block values, continuation behavior, and retry behavior.
- **P-06** (SEG109-R-013, SEG109-R-014): State when Employee Number and Password are required, optional, prohibited, and how the password is secured or represented in test data.
- **P-07** (SEG109-R-015, SEG109-R-022): Confirm request/response sequence correlation, response-code meanings, Download Indicator mapping, and handling of approvals, declines, pending retrieval, and retries.
- **P-08** (SEG109-R-016): Define when Local Time, Extract Date, and Extract Time must be included and their timezone/date semantics.
- **P-09** (SEG109-R-019, SEG109-R-020): Confirm whether the 750-byte retrieval print-data and 217-byte submission-data limits map directly to Text Data, response Mail Text Data, multi-block aggregation, or another payload representation.
- **P-10** (AI-artifacts): Provide the location of AI-generated Segment 109 BR, TS, TC, and TD artifacts, or upload them for producer-neutral comparison.
- **P-11** (test-data): Provide sanitized, converter-ready retrieval and submission request/response examples, or approve clearly labelled synthetic fixtures for preliminary training.

## Implementation traceability

- Rule catalog: [coverage/segment-109-rule-catalog.json](coverage/segment-109-rule-catalog.json)
- Validator: `Segment109PayloadValidator`
- Tests: `Segment109PayloadValidatorTest`, `Segment109RuleCatalogLoaderTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
