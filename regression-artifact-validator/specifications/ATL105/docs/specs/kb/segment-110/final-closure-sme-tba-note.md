# Segment 110 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 110 — Check Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.3.1, 12.9, Appendix D, Appendix I-17, Chapter 13  
**Oracle:** [segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json) (20 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 110 uses a **3-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG110-R-002` | Segment 110 has a maximum length of 168 alphanumeric characters (001-168) | 12.9 | — | SPEC_DERIVED |
| `SEG110-R-006` | The twelve Segment 110 fields are ordered as defined in Section 12.9: Segment Type, Segment Length, MICR Data, Driver's License, State Code, Date of Birth, Check Type, Check Number, Customer Phone Number, Customer Last… | 12.9 | — | SPEC_DERIVED |
| `SEG110-R-007` | Every Segment 110 field is separated by a Field Separator, including empty fields, which still send the Field Separator | 12.9 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 110 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Lifecycle and Response Correlation

_No `lifecycle` or `response` rules are catalogued for Segment 110. Closure is therefore structural only._

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 110 is not closeable while these remain open:

- **P-02** (SEG110-R-008): Confirm the data segment that carries Extended MICR Data (Element 137) when raw MICR data exceeds 50 bytes, and whether Segment 110 test packages must always be paired with that segment's fixture when the overflow condition applies.
- **P-03** (SEG110-R-009, SEG110-R-013): Define the machine-testable condition (a POS entry-mode flag, prompt code, or other field) that marks a check transaction as 'manually entered' or 'manually keyed', which triggers the Driver's License, State Code, and Check Number requirements.
- **P-04** (SEG110-R-011): Confirm whether Date of Birth is required whenever Driver's License/State Code are present (as BR-130-3/BR-365-4 suggest for Certegy transactions) or is governed by a separate, narrower trigger.
- **P-06** (SEG110-R-018, SEG110-R-020): Section 12.9 gives one TAC/RAW TOAD example, and Appendix I-17 (Segment 111, Variable Information Indicator 024) independently confirms four named MICR Type format codes ('T$','18','09','19') with one example each. Provide a machine-checkable grammar per code, or an approved sample set, sufficient to validate MICR Data content rather than only its length.
- **P-07** (SEG110-R-019): Confirm whether MICR Data in Segment 110 and Account Number in Segment 100 must match byte-for-byte when both are present, or whether they are independently populated representations.
- **P-08** (AI-artifacts): Confirm that the 94 Segment 110 entries filtered from test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json are the authoritative AI-generated requirement set to certify against, or provide an updated/approved AI artifact package.
- **P-09** (test-data): Provide sanitized, converter-ready Check Data Segment request examples for MICR-read and manually keyed check transactions (personal and company), or approve clearly labelled synthetic fixtures for preliminary training.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 3 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
