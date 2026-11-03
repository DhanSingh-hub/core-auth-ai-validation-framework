# Segment 136 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 136 — Moneris Data (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.25, AppendixV  
**Oracle:** [segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 136 uses a **3-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG136-R-004` | A Field Separator follows the segment (i.e., a single trailing separator after the whole Segment 136, unlike Segment 135's per-field pattern) | 12.25 | — | REVIEW_REQUIRED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 136 catalog references trailing-field handling — see the serialization rules above for the exact allowance.

## Lifecycle and Response Correlation

_No `lifecycle` or `response` rules are catalogued for Segment 136. Closure is therefore structural only._

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 136 is not closeable while these remain open:

- **P-01** (SEG136-R-002): Confirm whether Segment Type/Segment Length Source 'Device' is intentional for this Moneris response segment or a transcription error (mirrors the Segment 131 pattern).
- **P-02** (SEG136-R-004): Confirm whether Field Separators also appear between fields 1-2 and 2-3 in Segment 136, or only as a single trailing separator after the whole segment.
- **P-04** (AI-artifacts, test-data): No dedicated Segment 136 AI or Test Team package was located. Provide one, or approve synthesized fixtures.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 3 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
