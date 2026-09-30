# Segment 118 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 118 — Proprietary Data Load Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.7.6.1, 11.7.7, 12.16, 12.16.1, 12.16.2, 12.16.3, 12.16.4, 12.16.5, AppendixE, Chapter13-Element174, Chapter13-Element176, Chapter13-Element177, Chapter13-Element182, Chapter13-Element83  
**Oracle:** [segment-118-rule-catalog.json](coverage/segment-118-rule-catalog.json) (30 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 118 uses a **4-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG118-R-002` | Segment 118 has a maximum length of 3,800 alphanumeric characters (01-3,800) in a Request | 12.16 | — | SPEC_DERIVED |
| `SEG118-R-003` | Segment 118 has a maximum length of 3,800 alphanumeric characters (01-3,800) in a Response | 12.16 | — | SPEC_DERIVED |
| `SEG118-R-005` | In a Proprietary Data Load Request, all of fields 1-13 are separated by Field Separators, including unpopulated fields, and a Field Separator follows field 13; in a Proprietary Data Load Response, there are no Field Separators between fields (positional) | 12.16 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 118 catalog states **no** trailing-optional-field omission allowance. Do not port Segment 100's trailing-suffix rule to this segment without SME confirmation.

## Repeating Sections

The catalog describes repeating content. Repetitions frequently use different separator rules from the fixed fields; validate the repetition boundary separately from the fixed-field separators.

## Separator-Free Content

At least one catalog rule states that separators are absent within part of this segment. A parser that expects a separator between every field will mis-parse it.

## Lifecycle and Response Correlation

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG118-R-025` | A Proprietary Data Load Response contains Data Section 1 (Response Code 83 pos.1 len.1, Download Indicator 30 pos.2 len.1, Initiation Date 45 pos.3 len.6, Initiation Time 46 pos.9 len.4, Sequence Number 86 pos.13 len.6, all Required) and Data Section 3 (field 6, Segment 118, Required) | 11.7.7 | 83,30,45,46,86 | SPEC_DERIVED |
| `SEG118-R-026` | Response Code (Element 83) values specific to the Proprietary Data Load context: H (Approved, proprietary data retrieval more pending), O (Approved, proprietary data load more pending), T (Approved, proprietary data load, no more data pending), U (Declined, proprietary data load, no more data pending), X (Declined, proprietary data load, proceed to next pending Prompt Code), Y (Approved, proprietary data load, proceed to next pending Prompt Code) | Chapter13-Element83 | 83 | SPEC_DERIVED |
| `SEG118-R-030` | Response Code (Element 83) values V (Declined, Totals with Proprietary Custom Receipt Text data pending) and W (Approved, Totals with Proprietary Custom Receipt Text pending) indicate that a Prompt Code 901 (Custom Receipt Text) load is pending, analogous to the Host Discount D/E/M/N pattern | Chapter13-Element83 | 83 | SPEC_DERIVED |

These rules require **paired messages** in the test data. A test-control flag asserting "correlated" or "echoed" is not evidence.

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 118 is not closeable while these remain open:

- **P-01** (SEG118-R-009): Provide the Information Byte (Element 44) value catalog for Segment 118 and the single-message/multimessage continuation behavior it triggers.
- **P-02** (SEG118-R-019): Confirm whether Receipt Text Data (Element 170, len 20) is a fixed 20-byte slot padded when shorter, or a variable field whose actual length is given solely by the preceding Receipt Text Data Length (169).
- **P-03** (AI-artifacts): Confirm that the 107 Segment 118 entries filtered from test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json are the authoritative AI-generated requirement set to certify against, or provide an updated/approved AI artifact package.
- **P-04** (test-data): Provide sanitized, converter-ready Proprietary Data Load request/response examples for each of the five Prompt Code variants (901-905), or approve clearly labelled synthetic fixtures for preliminary training.

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 4 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
