# Segment 123 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 123 — NFC Payment Tokenization Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.19, AppendixY  
**Oracle:** [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) (11 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 123 uses a **3-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG123-R-002` | Segment Length (Element 84) includes Segment Type's length and Field Separators, Device-sourced | 12.19 | 84 | SPEC_DERIVED |
| `SEG123-R-004` | All fields are Field-Separator-delimited, including unpopulated fields (the separator is still sent) | 12.19 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 123 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Lifecycle and Response Correlation

_No `lifecycle` or `response` rules are catalogued for Segment 123. Closure is therefore structural only._

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 123 is not closeable while these remain open:

- **P-01** (SEG123-R-011): Confirm the UCAF Security Level Code '21' requirement scope: does it apply whenever Segment 123 TAVV and Segment 111 Table ID 36 UCAF co-occur, or only for specific card brands?
- **P-02** (AI-artifacts, test-data): No dedicated Segment 123 AI or Test package was located despite an existing branch (Segment_123_LLM_Training) with no committed content. Provide one, or approve synthesized fixtures.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 3 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
