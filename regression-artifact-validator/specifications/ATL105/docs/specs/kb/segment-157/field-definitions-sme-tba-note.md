# Segment 157 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 157 — Adjusted Product Code Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.41  
**Oracle:** [segment-157-rule-catalog.json](coverage/segment-157-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 157 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG157-R-003` | Segment 157 does not allow any product codes above '899' except for '955' (Cash Back); if any other code above 899 is present, the transaction will be declined | 12.41 | 77 | SPEC_DERIVED |
| `SEG157-R-005` | The total of Adjusted Product Amounts in the segment must equal the total of Element 41 (Fuel Purchase Amount) + Element 58 (Nonfuel Amount) + Element 99 (Tax Amount) + Element 17 (Cash Amount) in Segment 100 | 12.41 | 41,58,99,17 | SPEC_DERIVED |
| `SEG157-R-006` | Tax, discount, and coupon amounts are already accounted for in the individual Adjusted Product Amounts; separate tax/discount/coupon product codes must NOT be included in the segment | 12.41 | — | SPEC_DERIVED |
| `SEG157-R-007` | Multi-fuel support: BUYPASS can accept transactions with multiple Fuel Type Codes in one transaction (e.g., Diesel + DEF + Reefer); the primary fuel must be the very first product code in the segment | 12.41 | — | SPEC_DERIVED |
| `SEG157-R-009` | Segment Type fixed 157, Segment Length 3 digits, Service Level (Element 87), Number of Products (Element 62, 2 digits), then per-product: Product Code (77, max 3 including 899-cap/955-exception), Unit of Measure (106),… | 12.41 | 87,62,77,106,81,107… | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 77 | Product Code | N | 3 bytes | Fixed length of three digits | Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes. |
| 41 | Fuel Purchase Amount | N | 8 bytes | Variable length of up to eight digits with two assumed decimal places Note: A maximum length of 8 bytes is allowed for American Express cards only. A… | 1–99999999 |
| 58 | Nonfuel Amount | N | 8 bytes | Variable length of up to eight digits with two assumed decimal places Note: A maximum length of 8 bytes is allowed for American Express cards only. A… | 1–99999999 |
| 99 | Tax Amount | N | 8 bytes | Variable length of up to eight digits with two assumed decimal places Note: A maximum length of 8 bytes is allowed for American Express cards only. A… | 1–9999999 |
| 17 | Cash Amount | N | 8 bytes | Variable length of up to eight digits with two assumed decimal places | 1–99999999 |
| 87 | Service Level | A | 1 byte | Fixed length of one alpha character | Value Description F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present. 0–9 Reserved for private use. O… |
| 62 | Number of Products | N | 2 bytes | Fixed length of two digits | 01–10 |
| 106 | Unit of Measure | A | 1 byte | Fixed length of one alpha character | Value Description C Case/Carton G Gallon H Hours I Imperial Gallons K Kilogram L Liter M Charging Minutes (EV) P Pound Q Quart U Unit W Kilowatt per… |
| 81 | Quantity | N | 9 bytes | Variable length of up to nine digits with a maximum of three assumed decimal places | 00000000. 01–399999999 Examples: 010 = 10, where 0 is the number of assumed decimal places and 10 represents the quantity of 10. 34170 = 4.170, where… |
| 107 | Unit Price | N | 9 bytes | Variable length of up to nine digits with a maximum of three assumed decimal places | 000000.000–999999.999 For an example of this data element, please see Appendix B. POS Purchase Example. |
| 76 | Product Amount | N | 12 bytes | Variable length of up to 12 digits with two assumed decimal places | 1–999999999999 |

## Catalog Notes

- `SEG157-R-009` — Quantity/Unit Price encoding is a genuinely error-prone rule: '25' representing 0.05 at 2 decimal places is explicitly called out as INVALID — the value must always occupy the full digit-width including leading zeros for the assumed decimal positions.

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
  -> field position in Segment 157
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment 157 does not allow any product codes above '899' except for '955' Cash Back if any other code above 899 is present, the transaction will be declined
  -> source: ATL105 2026-3 §12.41 (SEG157-R-003)
  -> a violating payload shall fail validation citing SEG157-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment157PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG157-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
