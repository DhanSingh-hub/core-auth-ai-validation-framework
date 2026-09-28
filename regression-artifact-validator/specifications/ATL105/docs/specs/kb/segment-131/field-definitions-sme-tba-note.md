# Segment 131 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 131 — EMV Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.2-EMV, 12.11, 12.20, 12.21  
**Oracle:** [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 131 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (3)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG131-R-003` | Segment Type is fixed value 131, sourced at the Host (contrast Segment 130's device-sourced Segment Type) | 12.21 | 85 | SPEC_DERIVED |
| `SEG131-R-004` | Segment Length is 4 digits, one of exactly seven segments across the specification requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131) | 12.21 | 84 | SPEC_DERIVED |
| `SEG131-R-008` | EMV Chip Data Length (Element 189) and EMV Chip Data (Element 190) are required, mirroring Segment 130's fields, but the field table lists their Source as 'Device' even though Segment 131 as a whole originates at BUYPASS | 12.21 | 189,190 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 189 | EMV Chip Data Length | N | 3 bytes | Fixed length of three digits | 000—999 |
| 190 | EMV Chip Data | ANSB | 999 bytes | Variable length of 999 characters, preceded by three digit length indicator All fields after the table length are in binary format. | The components of EMV data elements are described below: Component Name: Tag Each sub element or Tag can be either one or two bytes long. To determin… |

## Catalog Notes

- `SEG131-R-008` — PROVISIONAL: likely a transcription artifact (the values are echoed from the device's own request data), similar to a previously-resolved sourcing inconsistency in Segment 112 (SEG112-SME-001). Pending SME confirmation (SEG131-SME-002) before treating 'Source: Device' as literal for a Host-originated segment.

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
  -> field position in Segment 131
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 131, sourced at the Host contrast Segment 130's device-sourced Segment Type
  -> source: ATL105 2026-3 §12.21 (SEG131-R-003)
  -> a violating payload shall fail validation citing SEG131-R-003
```

## Open Provisional Items

- **P-03** (SEG131-R-008): Confirm whether EMV Chip Data Length/EMV Chip Data's 'Source: Device' notation in Segment 131's field table is a transcription artifact (values echoed from the device's request) or intentional, given Segment 131 as a whole originates at BUYPASS.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment131PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG131-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
