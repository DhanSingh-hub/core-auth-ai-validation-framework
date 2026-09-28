# Segment 135 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 135 — Moneris Data (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 12.24, AppendixV  
**Oracle:** [segment-135-rule-catalog.json](coverage/segment-135-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 135 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (3)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG135-R-002` | Segment Type is fixed value 135, sourced at the Device | 12.24 | 85 | SPEC_DERIVED |
| `SEG135-R-003` | Segment Length Indicator is 3 digits, identifies the segment's length including Segment Type's length only (NOT explicitly including Field Separators, unlike most other segments' Segment Length description) | 12.24 | 84 | REVIEW_REQUIRED |
| `SEG135-R-005` | Moneris Data (Element 213) is required, max 100 characters, in <tag><len><data> TLV-like format; full layout documented in Appendix V (Moneris Data layouts) | 12.24,AppendixV | 213 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 213 | Moneris Data | AN | 100 bytes | Variable Length up to 100 bytes. | Any alpha-numeric characters, formatted in <tag><len><data> format. |

## Catalog Notes

- `SEG135-R-003` — PROVISIONAL: the wording difference ('including Segment Type's length' vs the usual 'including Segment Type's length and Field Separators') may be intentional or a drafting inconsistency. Pending SME confirmation (SEG135-SME-001).
- `SEG135-R-005` — PROVISIONAL: Appendix V (Moneris Data layouts) has not been transcribed into this KB pass. Pending SME confirmation (SEG135-SME-002) on scope.

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
  -> field position in Segment 135
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 135, sourced at the Device
  -> source: ATL105 2026-3 §12.24 (SEG135-R-002)
  -> a violating payload shall fail validation citing SEG135-R-002
```

## Open Provisional Items

- **P-01** (SEG135-R-003): Does Segment 135's Segment Length Indicator include Field Separators in its count, or only Segment Type's length as literally stated?
- **P-02** (SEG135-R-005): Is Appendix V (Moneris Data layouts, defining the <tag><len><data> sub-structures) in scope for this training pass?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment135PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG135-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
