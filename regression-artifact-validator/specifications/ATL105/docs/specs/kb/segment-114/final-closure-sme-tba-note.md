# Segment 114 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 114 — SKU Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 11.2.1, 11.2.2, 11.3.1, 11.9.1, 12.13, 13.2  
**Oracle:** [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 114 uses a **4-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG114-R-005` | Segment 114 maximum length is 1010 alphanumeric characters (001-1010/a-z/A-Z) | 12.13 | 84 | SPEC_DERIVED |
| `SEG114-R-006` | Field order is Segment Type, Segment Length, SKU Data (only 3 fields; no field-ordering irregularity exists for this segment, unlike Segment 108) | 12.13 | — | SPEC_DERIVED |
| `SEG114-R-007` | All fields are separated by Field Separators; a Field Separator follows Field No. 3 (the last field); when a field is not populated, still send the Field Separator | 12.13 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 114 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Repeating Sections

The catalog describes repeating content. Repetitions frequently use different separator rules from the fixed fields; validate the repetition boundary separately from the fixed-field separators.

## Lifecycle and Response Correlation

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG114-R-013` | Segment 114 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout | 11.2.2 | — | SPEC_DERIVED |

These rules require **paired messages** in the test data. A test-control flag asserting "correlated" or "echoed" is not evidence.

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 114 is not closeable while these remain open:

_No open provisional items are linked to these rules._

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 4 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
