# Segment 120 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 120 — Print Data 2 Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.18, 13-66  
**Oracle:** [segment-120-rule-catalog.json](coverage/segment-120-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 120 uses a **4-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG120-R-004` | Print Data must not exceed 999 characters AND total serialized Segment 120 length (Segment Type + Segment Length + Print Data + separators) must not exceed 1,009 alphanumeric characters -- both caps enforced independently | 12.18 | - | REVIEW_REQUIRED |
| `SEG120-R-005` | Exactly one Field Separator appears between Field 1 and Field 2, and exactly one between Field 2 and Field 3; there is no trailing separator after Print Data | 12.18 | - | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 120 catalog references trailing-field handling — see the serialization rules above for the exact allowance.

## Lifecycle and Response Correlation

_No `lifecycle` or `response` rules are catalogued for Segment 120. Closure is therefore structural only._

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 120 is not closeable while these remain open:

- **P-02-RESIDUAL** (SEG120-R-004): The field table lists Print Data's own max length as 999, but 1,009 (total) minus 3 (Segment Type) minus 4 (Segment Length, now confirmed fixed-width) minus 2 (separators) = 1,000, not 999 -- a 1-character arithmetic gap between the per-field cap and the total-segment cap. Is 999 a typo for 1,000, is the total cap actually 1,010 (matching the SKU Data Segment's analogous 4-digit-length pattern, '…
- **P-03** (SEG120-R-008): BR-263-5 (excluded from the AI Solution's own SEG-120 bucket, tagged UNASSIGNED) describes the '\' line-delimiter convention used only for Blackhawk phone activation/recharge receipt text. Should the envelope validator check for well-formed '\' delimiters inside Print Data, or is that content-specific business logic that belongs to a separate Blackhawk/loyalty module, analogous to how Segment 111…
- **P-04** (SEG120-R-006): Neither the extracted spec text nor the AI-generated requirements state whether Segment 120 may appear more than once per response message. Should cardinality be constrained to exactly one occurrence (by analogy to the confirmed Segment 101 rule SEG101-R-026), or left unconstrained until confirmed?

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 4 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
