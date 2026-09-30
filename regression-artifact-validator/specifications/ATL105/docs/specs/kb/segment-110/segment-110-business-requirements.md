# Segment 110 Business Requirements

**Segment:** 110 — Check Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.3.1, 12.9, Appendix D, Appendix I-17, Chapter 13  
**Oracle:** [segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json) (20 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 110 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 110 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 12 |
| serialization | 3 |
| compatibility | 2 |
| applicability | 1 |
| structure | 1 |
| interdependency | 1 |
| **Total** | **20** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG110-001 | applicability | Check Data Segment is confirmed as Field No. 4 of Data Section 3 in the ECA/TeleCheck Service Transaction Request, Required, alongside Segment 111 (Field 5, Required) and Segment 113 (Field 6); per SME decision (2026-09-26), it is also treated as valid in any… | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG110-R-001` §11.3.1,12.9 | SPEC_DERIVED |
| BR-SEG110-002 | serialization | Segment 110 has a maximum length of 168 alphanumeric characters (001-168) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG110-R-002` §12.9 | SPEC_DERIVED |
| BR-SEG110-003 | structure | Segment 110 originates at the device | The segment's structural position and composition match the rule. | `SEG110-R-003` §12.9 | SPEC_DERIVED |
| BR-SEG110-004 | field | Segment Type (Element 85) is fixed value 110 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-004` §12.9 | SPEC_DERIVED |
| BR-SEG110-005 | field | Segment Length (Element 84) is required and identifies the segment's serialized length including Segment Type and Field Separators | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-005` §12.9 | SPEC_DERIVED |
| BR-SEG110-006 | serialization | The twelve Segment 110 fields are ordered as defined in Section 12.9: Segment Type, Segment Length, MICR Data, Driver's License, State Code, Date of Birth, Check Type, Check Number, Customer Phone Number, Customer Last Name, Check Issue Date, Alternate MICR I… | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG110-R-006` §12.9 | SPEC_DERIVED |
| BR-SEG110-007 | serialization | Every Segment 110 field is separated by a Field Separator, including empty fields, which still send the Field Separator | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG110-R-007` §12.9 | SPEC_DERIVED |
| BR-SEG110-008 | field | MICR Data (Element 122) is required, alphanumeric, maximum 50 bytes, and required on all check transactions; when raw MICR data exceeds 50 bytes, only the first 50 bytes (left to right) are placed here and the remainder is expected in Extended MICR Data (Elem… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-008` §12.9 | REVIEW_REQUIRED |
| BR-SEG110-009 | field | Driver's License (Element 123) is conditional, alphanumeric, maximum 40 bytes, and required on all manually entered check transactions; the machine-testable trigger that distinguishes a manually entered check transaction from a MICR-read one is not defined in… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-009` §12.9 | REVIEW_REQUIRED |
| BR-SEG110-010 | interdependency | State Code (Element 124) is conditional, fixed 2 alphanumeric characters, required when Driver's License (Element 123) is included, and must be one of the 76 alphabetical codes in Appendix D (US states, DC, US territories, military designations, and Canadian… | The dependent values agree with each other. | `SEG110-R-010` §Appendix D | SPEC_DERIVED |
| BR-SEG110-011 | field | Date of Birth (Element 125) is conditional, numeric, fixed 8 digits (MMDDYYYY); the extracted Segment 110 table marks it conditional but states no explicit trigger separate from the Driver's License/State Code identification bundle | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-011` §12.9 | REVIEW_REQUIRED |
| BR-SEG110-012 | field | Check Type (Element 126) is required, alphanumeric, fixed 1 character, and must be one of the documented codes: P (Personal) or C (Company) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-012` §12.9 | SPEC_DERIVED |
| BR-SEG110-013 | field | Check Number (Element 127) is conditional, alphanumeric, maximum 8 bytes, and required for manually keyed check data; it shares the same undefined manually-entered trigger as Driver's License | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-013` §12.9 | REVIEW_REQUIRED |
| BR-SEG110-014 | field | Customer Phone Number (Element 128) is optional, numeric, maximum 10 digits | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-014` §12.9 | SPEC_DERIVED |
| BR-SEG110-015 | field | Customer Last Name (Element 129) is optional, alphanumeric, maximum 24 bytes | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-015` §12.9 | SPEC_DERIVED |
| BR-SEG110-016 | field | Check Issue Date (Element 130) is optional, numeric, fixed 8 digits (MMDDYYYY) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-016` §12.9 | SPEC_DERIVED |
| BR-SEG110-017 | field | Alternate MICR IND, documented in Section 12.9 as Segment 110 field 12 using element number 239, is optional, fixed 1 byte, and its only documented value is 'Y' (Alternate/RAW TOAD MICR format is being sent); this element number is separately defined in the C… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-017` §12.9 | REVIEW_REQUIRED |
| BR-SEG110-018 | field | Two MICR encodings are documented in narrative text: Full MICR Line TAC format (example T999999999A999999999999999999C999999) and Full MICR Line RAW TOAD format (symbol substitution using T, O, A, D); Alternate MICR IND = 'Y' signals RAW TOAD/ALB1 format, but… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG110-R-018` §12.9 | REVIEW_REQUIRED |
| BR-SEG110-019 | compatibility | MICR Data (Element 122) is cross-referenced from Account Number (Element 2) of the Standard Message Data Segment (100); the extract states MICR data 'is also included' there but does not state a byte-identity or co-validation rule between the two representati… | Only the permitted companion segments / message families carry this segment. | `SEG110-R-019` §Chapter 13 | REVIEW_REQUIRED |
| BR-SEG110-020 | compatibility | The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format co… | Only the permitted companion segments / message families carry this segment. | `SEG110-R-020` §Appendix I-17 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG110-NEG-001 | `SEG110-R-001` | MUT-005 required field omitted | Validation error citing SEG110-R-001 |
| BR-SEG110-NEG-002 | `SEG110-R-002` | MUT-003 length violation | Validation error citing SEG110-R-002 |
| BR-SEG110-NEG-003 | `SEG110-R-003` | MUT-010 structural requirement | Validation error citing SEG110-R-003 |
| BR-SEG110-NEG-004 | `SEG110-R-004` | MUT-001 wrong fixed value | Validation error citing SEG110-R-004 |
| BR-SEG110-NEG-005 | `SEG110-R-005` | MUT-001 wrong fixed value | Validation error citing SEG110-R-005 |
| BR-SEG110-NEG-006 | `SEG110-R-006` | MUT-001 wrong fixed value | Validation error citing SEG110-R-006 |
| BR-SEG110-NEG-007 | `SEG110-R-007` | MUT-010 structural / separator violation | Validation error citing SEG110-R-007 |
| BR-SEG110-NEG-008 | `SEG110-R-008` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-009 | `SEG110-R-009` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-010 | `SEG110-R-010` | MUT-005 required field omitted | Validation error citing SEG110-R-010 |
| BR-SEG110-NEG-011 | `SEG110-R-011` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-012 | `SEG110-R-012` | MUT-005 required field omitted | Validation error citing SEG110-R-012 |
| BR-SEG110-NEG-013 | `SEG110-R-013` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-014 | `SEG110-R-014` | MUT-003 length violation | Validation error citing SEG110-R-014 |
| BR-SEG110-NEG-015 | `SEG110-R-015` | MUT-003 length violation | Validation error citing SEG110-R-015 |
| BR-SEG110-NEG-016 | `SEG110-R-016` | MUT-003 length violation | Validation error citing SEG110-R-016 |
| BR-SEG110-NEG-017 | `SEG110-R-017` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-018 | `SEG110-R-018` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-019 | `SEG110-R-019` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG110-NEG-020 | `SEG110-R-020` | MUT-003 length violation | Validation error citing SEG110-R-020 |

## Requirements that must not be certified yet

- `BR-SEG110-008` (`SEG110-R-008`) — REVIEW_REQUIRED
- `BR-SEG110-009` (`SEG110-R-009`) — REVIEW_REQUIRED
- `BR-SEG110-011` (`SEG110-R-011`) — REVIEW_REQUIRED
- `BR-SEG110-013` (`SEG110-R-013`) — REVIEW_REQUIRED
- `BR-SEG110-017` (`SEG110-R-017`) — REVIEW_REQUIRED
- `BR-SEG110-018` (`SEG110-R-018`) — REVIEW_REQUIRED
- `BR-SEG110-019` (`SEG110-R-019`) — REVIEW_REQUIRED

## Open SME items

- **P-02** (SEG110-R-008): Confirm the data segment that carries Extended MICR Data (Element 137) when raw MICR data exceeds 50 bytes, and whether Segment 110 test packages must always be paired with that segment's fixture when the overflow condition applies.
- **P-03** (SEG110-R-009, SEG110-R-013): Define the machine-testable condition (a POS entry-mode flag, prompt code, or other field) that marks a check transaction as 'manually entered' or 'manually keyed', which triggers the Driver's License, State Code, and Check Number requirements.
- **P-04** (SEG110-R-011): Confirm whether Date of Birth is required whenever Driver's License/State Code are present (as BR-130-3/BR-365-4 suggest for Certegy transactions) or is governed by a separate, narrower trigger.
- **P-06** (SEG110-R-018, SEG110-R-020): Section 12.9 gives one TAC/RAW TOAD example, and Appendix I-17 (Segment 111, Variable Information Indicator 024) independently confirms four named MICR Type format codes ('T$','18','09','19') with one example each. Provide a machine-checkable grammar per code, or an approved sample set, sufficient to validate MICR Data content rather than only its length.
- **P-07** (SEG110-R-019): Confirm whether MICR Data in Segment 110 and Account Number in Segment 100 must match byte-for-byte when both are present, or whether they are independently populated representations.
- **P-08** (AI-artifacts): Confirm that the 94 Segment 110 entries filtered from test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json are the authoritative AI-generated requirement set to certify against, or provide an updated/approved AI artifact package.
- **P-09** (test-data): Provide sanitized, converter-ready Check Data Segment request examples for MICR-read and manually keyed check transactions (personal and company), or approve clearly labelled synthetic fixtures for preliminary training.

## Implementation traceability

- Rule catalog: [coverage/segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json)
- Validator: `Segment110PayloadValidator`
- Tests: `Segment110PayloadValidatorTest`, `Segment110RuleCatalogLoaderTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
