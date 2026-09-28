# Segment 108 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 108 — Loyalty Card Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9.1.2, 11.2.1, 11.2.2, 12, 12.7, 13.2  
**Oracle:** [segment-108-rule-catalog.json](coverage/segment-108-rule-catalog.json) (24 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 108 uses a **3-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG108-R-005` | Segment 108 maximum length is 142 alphanumeric characters | 12.7 | 84 | SPEC_DERIVED |
| `SEG108-R-006` | Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date,… | 12.7 | — | SPEC_DERIVED |
| `SEG108-R-007` | All fields are separated by Field Separators, including a separator following the last field; empty fields still send the separator | 12.7 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 108 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Lifecycle and Response Correlation

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG108-R-024` | Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout | 11.2.2 | — | REVIEW_REQUIRED |

These rules require **paired messages** in the test data. A test-control flag asserting "correlated" or "echoed" is not evidence.

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 108 is not closeable while these remain open:

- **P-02** (SEG108-R-013): Section 10.9.3 describes 9-10 distinct loyalty advice functions (including 'Reversal of coupon redeem' and 'Reversal of points redeemed') but Element 143 only documents 8 Update Code values (A,C,E,I,P,S,T,U). Which code(s) represent the two reversal functions?
- **P-03** (SEG108-R-024): User indicated Segment 108 (or loyalty data) DOES appear in some response scenario(s), contradicting the literal Section 11.2.2 text, but could not yet specify which scenario(s) or field layout. Needs a follow-up answer with a section/page reference before SEG108-R-024 can be enforced with confidence.
- **P-04** (receipt/print-data, not segment-108 wire format): Appendix K Table 008 (Loyalty Information - Version 1) and Table 010 (Loyalty Information - Version 2), referenced by Section 10.9.4 for loyalty receipts, are not deeply transcribed in this KB pass. Are these in scope for Segment 108 training, or a separate Appendix K workstream?
- **P-07** (AI-artifacts): Location of AI-generated Segment 108 BR/TS/TC/TD packages (none supplied as of this training pass).
- **P-08** (test-data): Availability of real Segment 108 sample JSONs, or approval to continue with synthesized .synthetic.json fixtures.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 3 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
