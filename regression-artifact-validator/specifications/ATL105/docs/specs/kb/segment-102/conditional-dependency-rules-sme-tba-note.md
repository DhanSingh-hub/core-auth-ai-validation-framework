# Segment 102 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** 102 — Segment 102  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.3, 12.30, Appendix F  
**Oracle:** [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) (25 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `partial-approval-*` (a conditional feature with cross-field consequences).

## Core Idea

Conditional rules are where Segment 102 validation most often fails silently: a field that is correct in isolation can be wrong because of the value of another field or another segment.

## Specification-Derived Rules (8)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG102-R-005` | Number of Products matches the actual serialized product entry count | 12.3 | 62 | SPEC_DERIVED |
| `SEG102-R-007` | Fuel products are always the first products in the segment | 12.3 | 77 | REVIEW_REQUIRED |
| `SEG102-R-008` | For EV charging transactions the EV product code is first | 12.3 | 77 | REVIEW_REQUIRED |
| `SEG102-R-009` | A unique Product Code is sent for each type of fuel purchase | 12.3 | 77 | SPEC_DERIVED |
| `SEG102-R-015` | Sum of Product Amounts reconciles with Segment 100 fuel, nonfuel, tax, and cash amounts | 12.3 | 76 | SPEC_DERIVED |
| `SEG102-R-016` | Tax-coded product totals are reflected in Segment 100 Tax Amount | 12.3 | 99 | SPEC_DERIVED |
| `SEG102-R-017` | Fuel merchants send both fuel and nonfuel product data | 12.3 | — | REVIEW_REQUIRED |
| `SEG102-R-024` | Multi-fuel OTR transactions list the primary fuel first | 12.3 | 77 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 62 | Number of Products | N | 2 bytes | Fixed length of two digits | 01–10 |
| 77 | Product Code | N | 3 bytes | Fixed length of three digits | Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes. |
| 76 | Product Amount | N | 12 bytes | Variable length of up to 12 digits with two assumed decimal places | 1–999999999999 |
| 99 | Tax Amount | N | 8 bytes | Variable length of up to eight digits with two assumed decimal places Note: A maximum length of 8 bytes is allowed for American Express cards only. A… | 1–9999999 |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which fields are Conditional, and what exact condition makes each one required?
2. Which values must agree with another field in Segment 102?
3. Which values must agree with Segment 100 or another companion segment?
4. Is the dependency stated in the specification, or inferred and therefore provisional?
5. What is the expected outcome when the dependency is violated — reject, decline, or ignore?

## TBA Dependency Chain

```text
triggering field / segment value
  -> conditional field requirement
  -> cross-field agreement
  -> cross-segment agreement
  -> validator outcome
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Number of Products matches the actual serialized product entry count
  -> source: ATL105 2026-3 §12.3 (SEG102-R-005)
  -> a violating payload shall fail validation citing SEG102-R-005
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

- Is every rule traced to its source anchor (`SEG102-R-005`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
