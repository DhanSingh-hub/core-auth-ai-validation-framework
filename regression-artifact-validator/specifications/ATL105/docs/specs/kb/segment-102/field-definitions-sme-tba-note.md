# Segment 102 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 102 — Segment 102  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.3, 12.30, Appendix F  
**Oracle:** [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) (25 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 102 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (8)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG102-R-001` | Segment Type is 102 | 12.3 | 85 | SPEC_DERIVED |
| `SEG102-R-003` | Service Level uses an allowed value | 12.3 | 87 | SPEC_DERIVED |
| `SEG102-R-004` | Number of Products is 01-10 and zero-padded | 12.3 | 62 | SPEC_DERIVED |
| `SEG102-R-010` | Product Code is a valid value per Appendix F | Appendix F | 77 | REVIEW_REQUIRED |
| `SEG102-R-011` | Unit of Measure uses an allowed value | 12.3 | 106 | SPEC_DERIVED |
| `SEG102-R-012` | Quantity preserves the assumed-decimal-place leading digit | 12.3 | 81 | SPEC_DERIVED |
| `SEG102-R-013` | Unit Price preserves the assumed-decimal-place leading digit | 12.3 | 107 | SPEC_DERIVED |
| `SEG102-R-014` | Product Amount is present for every product entry | 12.3 | 76 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 87 | Service Level | A | 1 byte | Fixed length of one alpha character | Value Description F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present. 0–9 Reserved for private use. O… |
| 62 | Number of Products | N | 2 bytes | Fixed length of two digits | 01–10 |
| 77 | Product Code | N | 3 bytes | Fixed length of three digits | Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes. |
| 106 | Unit of Measure | A | 1 byte | Fixed length of one alpha character | Value Description C Case/Carton G Gallon H Hours I Imperial Gallons K Kilogram L Liter M Charging Minutes (EV) P Pound Q Quart U Unit W Kilowatt per… |
| 81 | Quantity | N | 9 bytes | Variable length of up to nine digits with a maximum of three assumed decimal places | 00000000. 01–399999999 Examples: 010 = 10, where 0 is the number of assumed decimal places and 10 represents the quantity of 10. 34170 = 4.170, where… |
| 107 | Unit Price | N | 9 bytes | Variable length of up to nine digits with a maximum of three assumed decimal places | 000000.000–999999.999 For an example of this data element, please see Appendix B. POS Purchase Example. |
| 76 | Product Amount | N | 12 bytes | Variable length of up to 12 digits with two assumed decimal places | 1–999999999999 |

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
  -> field position in Segment 102
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 102
  -> source: ATL105 2026-3 §12.3 (SEG102-R-001)
  -> a violating payload shall fail validation citing SEG102-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment102PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG102-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
