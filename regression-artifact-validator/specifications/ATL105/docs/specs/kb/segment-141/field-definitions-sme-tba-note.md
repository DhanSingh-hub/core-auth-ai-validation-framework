# Segment 141 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 141 — Moneris Day End Batch Close (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.28  
**Oracle:** [segment-141-rule-catalog.json](coverage/segment-141-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 141 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG141-R-003` | Segment Type fixed 141, Segment Length 3 digits, both Device-sourced; all 14 fields (Terminal Identifier, SPDH Header, Moneris Terminal/Merchant ID, Batch Number, Language Indicator, Number/Dollar totals for debits/cred… | 12.28 | 85,84,102,206,207,2… | SPEC_DERIVED |

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
| 217 | Number of Debits | N | 4 bytes | Fixed length of 4 bytes. | 0001-9999 |
| 218 | Debit Dollar Value | AN | 19 bytes | Fixed length of 19 bytes with leading +/- and two implied decimal places. | (+/-)000000000000000000-(+/-)999999999999999999 |
| 219 | Number of Credits | N | 4 bytes | Fixed length of 4 bytes. | 0001-9999 |
| 220 | Credit Dollar Value | AN | 19 bytes | Fixed length of 19 bytes with leading +/- and two implied decimal places. | (+/-)000000000000000000-(+/-)999999999999999999 |
| 221 | Number of Corrections | N | 4 bytes | Fixed length of 4 bytes. | 0001-9999 |
| 222 | Corrections Dollar Value | AN | 19 bytes | Fixed length of 19 bytes with leading +/- and two implied decimal places. | (+/-)000000000000000000-(+/-)999999999999999999 |

## Catalog Notes

- `SEG141-R-003` — Contrast Segment 140 (response), where sourcing alternates between Device and Moneris — the request-side Segment 141 is uniformly Device-sourced.

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
  -> field position in Segment 141
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type fixed 141, Segment Length 3 digits, both Device-sourced all 14 fields Terminal Identifier, SPDH Header, Moneris Terminal/Merchant ID, Batch Number, Language Indicator, Number/Dollar totals for debits/credits/corrections are Device-sourced
  -> source: ATL105 2026-3 §12.28 (SEG141-R-003)
  -> a violating payload shall fail validation citing SEG141-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment141PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG141-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
