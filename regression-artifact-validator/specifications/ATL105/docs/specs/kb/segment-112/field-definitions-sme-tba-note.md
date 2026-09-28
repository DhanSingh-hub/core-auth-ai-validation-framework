# Segment 112 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 112 — Additional Information Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.11, 13  
**Oracle:** [segment-112-rule-catalog.json](coverage/segment-112-rule-catalog.json) (10 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 112 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG112-R-001` | Segment Type is 112 | 12.11 | 85 | SPEC_DERIVED |
| `SEG112-R-002` | Segment Length identifies the segment's total length including Segment Type and Field Separators | 12.11 | 84 | SPEC_DERIVED |
| `SEG112-R-007` | Additional Information Indicator (Element 116) identifies the type of additional information being transmitted | 12.11 | 116 | SPEC_DERIVED |
| `SEG112-R-008` | Additional Information Length (Element 117) identifies the length of the following Additional Information (Element 118) | 12.11 | 117 | SPEC_DERIVED |
| `SEG112-R-009` | Additional Information (Element 118) is variable length and carries the value identified by its paired Indicator/Length | 12.11 | 118 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 116 | Additional Information Indicator | N | 3 bytes | Fixed length of 3 digits | Code Description 001 Balance information for gift card, EBT card, credit card, or phone card transactions 002 Reserved 003 Address Verification Servi… |
| 117 | Additional Information Length | N | 3 bytes | Fixed length of 3 digits | 001–985 |
| 118 | Additional Information | AN | 984 bytes | Variable length of up to 984 alphanumeric characters Note: Please refer to section 12.11, “Additional Information Data Segment,” for conditions affec… | 001–984 a–z A–Z Please refer to Appendix K. Additional Information Data Layouts. |

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
  -> field position in Segment 112
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 112
  -> source: ATL105 2026-3 §12.11 (SEG112-R-001)
  -> a violating payload shall fail validation citing SEG112-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment112PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG112-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
