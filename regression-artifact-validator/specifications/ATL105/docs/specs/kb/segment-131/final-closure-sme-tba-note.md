# Segment 131 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 131 — EMV Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.2-EMV, 12.11, 12.20, 12.21  
**Oracle:** [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 131 uses a **4-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG131-R-005` | Segment 131 maximum length is 3,834 alphanumeric characters (001-3834/a-z/A-Z) | 12.21 | 84 | REVIEW_REQUIRED |
| `SEG131-R-006` | Segment 131 uses NO Field Separators at all — it is fixed-length/positional. When a field is not populated, the next field immediately follows with no delimiter | 12.21 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 131 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Repeating Sections

The catalog describes repeating content. Repetitions frequently use different separator rules from the fixed fields; validate the repetition boundary separately from the fixed-field separators.

## Separator-Free Content

At least one catalog rule states that separators are absent within part of this segment. A parser that expects a separator between every field will mis-parse it.

## Lifecycle and Response Correlation

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG131-R-007` | CA Public Key File Checksum (Element 187) is required and is explicitly echoed from the Request (Segment 130's CA Public Key File Checksum) | 12.21 | 187 | SPEC_DERIVED |

These rules require **paired messages** in the test data. A test-control flag asserting "correlated" or "echoed" is not evidence.

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 131 is not closeable while these remain open:

- **P-01** (SEG131-R-001): Section 12.21 states Segment 131 appears in 'Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response layout table (also corroborated by the AI Solution Team's own statement) places it at Field No. 17/18/19/20 of Data Section No. 2. Which governs, or do these describe two different message contexts?
- **P-02** (SEG131-R-005): Confirm 3,834 is the sole authoritative maximum length for Segment 131 — an earlier, OCR-uncertain reading of a response layout table suggested a possible shorter figure that could not be confidently transcribed.
- **P-03** (SEG131-R-008): Confirm whether EMV Chip Data Length/EMV Chip Data's 'Source: Device' notation in Segment 131's field table is a transcription artifact (values echoed from the device's request) or intentional, given Segment 131 as a whole originates at BUYPASS.
- **P-04** (AI-artifacts, test-data): No dedicated segment-131 AI Solution BR/TS/TC/TD package or Test Team core-structure package exists (unlike Segment 130). Provide the location of one, or approve continued use of synthesized `.synthetic.json` fixtures.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 4 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
