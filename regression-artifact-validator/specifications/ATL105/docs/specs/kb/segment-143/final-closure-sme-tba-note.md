# Segment 143 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 143 — Tax by Product Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.30  
**Oracle:** [segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 143 uses a **3-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG143-R-008` | A dual-delimiter scheme is used: a Field Separator ('▲') follows the LAST tax amount for each product and signals moving to the next product (or end of segment for the last product); a Tax by Product Field Delimiter ('\… | 12.30 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 143 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Repeating Sections

The catalog describes repeating content. Repetitions frequently use different separator rules from the fixed fields; validate the repetition boundary separately from the fixed-field separators.

## Lifecycle and Response Correlation

_No `lifecycle` or `response` rules are catalogued for Segment 143. Closure is therefore structural only._

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 143 is not closeable while these remain open:

- **P-01** (SEG143-R-007): Are GST/HST/PST the only valid Tax Type values, or do non-Canadian jurisdictions use additional documented values not captured in this section?
- **P-02** (AI-artifacts, test-data): No dedicated Segment 143 AI or Test package was located. Provide one, or approve synthesized fixtures using the worked examples already in Section 12.30 (5 examples provided).

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 3 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
