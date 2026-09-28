# Segment 139 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 139 — Moneris Day End Batch Balance (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.26  
**Oracle:** [segment-139-rule-catalog.json](coverage/segment-139-rule-catalog.json) (4 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 139 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG139-R-002` | Segment Type is fixed value 139, Segment Length is 3 digits, both sourced at the Device | 12.26 | 85,84 | SPEC_DERIVED |
| `SEG139-R-003` | Terminal Identifier (Element 102), SPDH Header (Element 206), Moneris Terminal Identifier (Element 207), Moneris Merchant ID (Element 208), Batch Number (Element 214), and Language Indicator (Element 215) are all requir… | 12.26 | 102,206,207,208,214… | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 102 | Terminal Identifier | AN | 22 bytes | Variable length up to 22 alphanumeric characters | Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel. For the TransArmor PKI Encryption and Tokenization Load… |
| 206 | SPDH Header | AN | 48 bytes | Fixed length of 48 bytes. | Refer to Appendix V (Moneris Data layouts) for further details. |
| 207 | Moneris Terminal Identifier | AN | 8 bytes | Fixed length of 8 bytes. | — |
| 208 | Moneris Merchant ID | AN | 13 bytes | Fixed length of 13 bytes. | — |
| 214 | Batch Number | AN | 3 bytes | Fixed length of 3 bytes. | Number from 000 to 999 |
| 215 | Language Indicator | AN | 1 byte | Fixed length of 1 byte. | Refer to Appendix V for the Moneris Language Indicator Matrix. |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which element number does each field carry, and does the payload preserve it?
2. Is the value fixed-length or variable-length, and is the length measured in bytes or characters?
3. Is the field Required, Optional, or Conditional, and what triggers the condition?
4. Does the valid-value set come from Chapter 13 or from an appendix table?
5. Is any value cardholder- or key-sensitive and therefore synthetic-only in test data?

## TBA Dependency Chain

```text
element definition (Chapter 13)
  -> field position in Segment 139
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 139, Segment Length is 3 digits, both sourced at the Device
  -> source: ATL105 2026-3 §12.26 (SEG139-R-002)
  -> a violating payload shall fail validation citing SEG139-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment139PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG139-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
