# Segment 123 Business Requirements

**Segment:** 123 — NFC Payment Tokenization Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.19, AppendixY  
**Oracle:** [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) (11 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 123 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 123 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 6 |
| serialization | 2 |
| structure | 1 |
| applicability | 1 |
| compatibility | 1 |
| **Total** | **11** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG123-001 | field | Segment Type is fixed value 123 (Element 85), Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG123-R-001` §12.19 | SPEC_DERIVED |
| BR-SEG123-002 | serialization | Segment Length (Element 84) includes Segment Type's length and Field Separators, Device-sourced | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG123-R-002` §12.19 | SPEC_DERIVED |
| BR-SEG123-003 | structure | Segment 123 maximum length is 186 alphanumeric characters; originates at the Device | The segment's structural position and composition match the rule. | `SEG123-R-003` §12.19 | SPEC_DERIVED |
| BR-SEG123-004 | serialization | All fields are Field-Separator-delimited, including unpopulated fields (the separator is still sent) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG123-R-004` §12.19 | SPEC_DERIVED |
| BR-SEG123-005 | applicability | Segment 123 is required on all initial and recurring transactions involving tokenized data, and on transactions that include MasterCard Token/DSRP or Visa TAVV data | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG123-R-005` §12.19 | SPEC_DERIVED |
| BR-SEG123-006 | field | Token PAN Suffix (Element 197, 4 chars, Conditional, Issuer/Authorizer-sourced) — the last 4 digits of the cardholder PAN — is returned in the response only if supplied by the authorizer | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG123-R-006` §12.19 | SPEC_DERIVED |
| BR-SEG123-007 | field | CAVV, Revised Format (Element 195, 20 chars, Optional, Device-sourced) identifies Verified By Visa data with an Authentication Tracking Number (ATN) replacing XID | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG123-R-007` §12.19 | SPEC_DERIVED |
| BR-SEG123-008 | field | Cryptogram Token Data (Element 202) length is 28 or 56 bytes — Block A alone, or Block A and B combined | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG123-R-008` §12.19 | SPEC_DERIVED |
| BR-SEG123-009 | field | SafeKey Data (Element 203, 58 chars) is composed of a fixed 'SK' indicator (2 bytes), AEVV (28 bytes), and AESK Transaction Identifier (28 bytes); unused 28-byte portions must be space-filled rather than omitted | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG123-R-009` §12.19 | SPEC_DERIVED |
| BR-SEG123-010 | field | TAVV Cryptogram (Element 237, 28 bytes, base64 alphabet a-z/A-Z/0-9/+/=// ) may only be populated by the merchant during the transaction request, not the response | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG123-R-010` §12.19 | SPEC_DERIVED |
| BR-SEG123-011 | compatibility | When both MasterCard DSRP cryptogram and SecureCode/Identity Check 3DS AAV are present in the same request, the AAV must be carried in Segment 111 Table ID 36 (UCAF) and the Token/DSRP cryptogram must be carried in Segment 123 Element 237 (TAVV) — the two fie… | Only the permitted companion segments / message families carry this segment. | `SEG123-R-011` §12.19,AppendixY | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG123-NEG-001 | `SEG123-R-001` | MUT-001 wrong fixed value | Validation error citing SEG123-R-001 |
| BR-SEG123-NEG-002 | `SEG123-R-002` | MUT-001 wrong fixed value | Validation error citing SEG123-R-002 |
| BR-SEG123-NEG-003 | `SEG123-R-003` | MUT-003 length violation | Validation error citing SEG123-R-003 |
| BR-SEG123-NEG-004 | `SEG123-R-004` | MUT-010 structural / separator violation | Validation error citing SEG123-R-004 |
| BR-SEG123-NEG-005 | `SEG123-R-005` | MUT-005 required field omitted | Validation error citing SEG123-R-005 |
| BR-SEG123-NEG-006 | `SEG123-R-006` | MUT-003 length violation | Validation error citing SEG123-R-006 |
| BR-SEG123-NEG-007 | `SEG123-R-007` | MUT-010 structural requirement | Validation error citing SEG123-R-007 |
| BR-SEG123-NEG-008 | `SEG123-R-008` | MUT-003 length violation | Validation error citing SEG123-R-008 |
| BR-SEG123-NEG-009 | `SEG123-R-009` | MUT-003 length violation | Validation error citing SEG123-R-009 |
| BR-SEG123-NEG-010 | `SEG123-R-010` | MUT-003 length violation | Validation error citing SEG123-R-010 |
| BR-SEG123-NEG-011 | `SEG123-R-011` | MUT-005 required field omitted | Validation error citing SEG123-R-011 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (SEG123-R-011): Confirm the UCAF Security Level Code '21' requirement scope: does it apply whenever Segment 123 TAVV and Segment 111 Table ID 36 UCAF co-occur, or only for specific card brands?
- **P-02** (AI-artifacts, test-data): No dedicated Segment 123 AI or Test package was located despite an existing branch (Segment_123_LLM_Training) with no committed content. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
