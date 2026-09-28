# Segment 120 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 120 — Print Data 2 Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.18, 13-66  
**Oracle:** [segment-120-rule-catalog.json](coverage/segment-120-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 120 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG120-R-001` | Segment Type is 120 | 12.18 | 85 | SPEC_DERIVED |
| `SEG120-R-002` | Segment Length is present, exactly 4 digits (zero-padded), and equals the segment's actual encoded length including the Segment Type field and Field Separators | 13-66 | 84 | SPEC_DERIVED |
| `SEG120-R-003` | Print Data is required and must be present whenever Segment 120 is included | 12.18 | 152 | SPEC_DERIVED |
| `SEG120-R-008` | Print Data may contain '\' as a line delimiter between lines of receipt text for Blackhawk phone activation/recharge | 12.18 | 152 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 152 | Print Data | ANS | 900 bytes | Variable length of up to 900 alphanumeric and special characters |  |

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
  -> field position in Segment 120
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 120
  -> source: ATL105 2026-3 §12.18 (SEG120-R-001)
  -> a violating payload shall fail validation citing SEG120-R-001
```

## Open Provisional Items

- **P-03** (SEG120-R-008): BR-263-5 (excluded from the AI Solution's own SEG-120 bucket, tagged UNASSIGNED) describes the '\' line-delimiter convention used only for Blackhawk phone activation/recharge receipt text. Should the envelope validator check for well-formed '\' delimiters inside Print Data, or is that content-specific business logic that belongs to a separate Blackhawk/loyalty module, analogous to how Segment 111…

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment120PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG120-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
