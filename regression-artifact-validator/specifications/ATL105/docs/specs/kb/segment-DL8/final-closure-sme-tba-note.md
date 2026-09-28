# Segment DL8 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.49  
**Oracle:** [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment DL8 uses a **3-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

_The Segment DL8 catalog contains no `serialization` rules. Separator behaviour must therefore be confirmed against the Section 12 layout note for this segment before a closure validator is written._

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment DL8 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Repeating Sections

The catalog describes repeating content. Repetitions frequently use different separator rules from the fixed fields; validate the repetition boundary separately from the fixed-field separators.

## Lifecycle and Response Correlation

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL8-R-001` | Segment DL8 contains EMV floor limits (as RID) for the terminal; inclusion in a table load depends on a 'Special' flag set at the terminal level; the data is maintained at the BUYPASS Host, and changes to it set the tab… | 12.49 | — | SPEC_DERIVED |

These rules require **paired messages** in the test data. A test-control flag asserting "correlated" or "echoed" is not evidence.

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment DL8 is not closeable while these remain open:

- **P-01** (AI-artifacts, test-data): No dedicated Segment DL8 AI or Test package was located. Provide one, or approve synthesized fixtures.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 3 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
