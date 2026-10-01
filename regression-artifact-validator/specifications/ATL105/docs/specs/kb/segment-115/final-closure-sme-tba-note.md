# Segment 115 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** 115 — Print Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9, 11.1.2, 11.1.2-EMV, 11.2.2, 12.14, 13.2  
**Oracle:** [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Why Closure Is Separate

Field rules prove each value is individually valid. Closure proves the **whole segment** can be parsed without positional drift, and that it belongs to a coherent message exchange.

## Segment Length Encoding

Segment 115 uses a **4-digit** Segment Length (Element 84). Element 84's definition permits four digits only for Segments 103, 114, 115, 118, 120, 130 and 131; every other segment uses three.

## Serialization Rules From The Catalog

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG115-R-005` | Segment 115 maximum length is 1,009 alphanumeric characters (01-1,009/a-z/A-Z) | 12.14 | 84 | REVIEW_REQUIRED |
| `SEG115-R-006` | Field order is Segment Type, Segment Length, Print Data (only 3 fields; same shape as Segment 114) | 12.14 | — | SPEC_DERIVED |
| `SEG115-R-007` | Only two Field Separators exist in Segment 115: one between Field Nos. 1 and 2, and one between Field Nos. 2 and 3. There is NO trailing Field Separator after Field No. 3 (Print Data) — unlike every other segment documented so far (108, 114), which explicitly send a trailing separator | 12.14 | — | SPEC_DERIVED |

## Empty Fields and Trailing Fields

An empty field in the middle of a separator-delimited segment still occupies a position: its separator must remain, or every later field shifts.

The Segment 115 catalog references trailing-field handling — see the serialization rules above for the exact allowance.

## Lifecycle and Response Correlation

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG115-R-012` | Segment 115 is plausibly the wire-format vehicle for 'Loyalty Print Data' returned during a Segment 108 Account Inquiry (Update Code I) or Totals Report (Update Code T) flow, inferred from the shared Loyalty Information Version = 2 trigger condition | 10.9,11.1.2 | — | REVIEW_REQUIRED |
| `SEG115-R-013` | Whether Segment 115 can appear in the Loyalty Card Transaction Response (Section 11.2.2, which states it mirrors the generic Financial Transaction Response) is inferred but not explicitly restated for Segment 115 | 11.2.2 | — | REVIEW_REQUIRED |

These rules require **paired messages** in the test data. A test-control flag asserting "correlated" or "echoed" is not evidence.

## Certification Meaning

- **Serialization rules** prove the segment parses without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent exchange.
- Anything requiring production keys, certified kernels, or live host behaviour is explicitly out of scope rather than silently assumed.

## Closure Gate

Segment 115 is not closeable while these remain open:

- **P-01** (SEG115-R-005): Section 12.14 and the AI Solution Team's BR-249-3 both state max length 1,009, but the Financial Transaction Response layout table (Section 11.1.2) states 910 for Field No. 17/18/19. Which governs?
- **P-02** (SEG115-R-008): Section 12.14's own field table states Print Data (Element 152) length 999, but the internal 13-data-elements.md reference states 900 bytes max. Which governs?
- **P-03** (SEG115-R-010): Does 'no other data segments are contained in the Financial Transaction Response' (Section 12.14) mean Segment 115 excludes Segment 112, or only that nothing besides 112 and 115 can appear? The Section 11.1.2 layout table depicts both as independently-conditional in the same response.
- **P-04** (SEG115-R-012, SEG115-R-013): Is Segment 115 confirmed as the wire-format vehicle for Segment 108's 'Loyalty Print Data' (Account Inquiry / Totals Report responses), and can Segment 115 appear in the Loyalty Card Transaction Response? Mirrors Segment 108's still-open SEG108-SME-003.
- **P-05** (Element 115 semantics): What are the complete valid values of Element 115 (Additional Information Data Segment Flag)? The internal reference table lists only 0 (none follows) and 1 (follows), but the narrative describes two independently conditional segments (112 and 115) governed by the same flag plus a second condition (Loyalty Information Version = 2). Please confirm the exact decision logic.
- **P-06** (AI-artifacts, test-data): No dedicated segment-115 AI Solution BR/TS/TC/TD package or real Financial-Transaction-Response sample JSON has been located as of this training pass (only cross-references inside other segments' packages, all mismatched to the wrong segment). Provide the location of a dedicated Segment 115 AI artifact package and real response sample data, or approve continued use of synthesized `.synthetic.json…

## SME/TBA Review Questions

- Does every empty non-trailing field keep its separator?
- Is the Segment Length encoded with exactly 4 digits and does it include the Segment Type and separators?
- Are repeating or separator-free regions handled by their own rule?
- Do lifecycle rules have genuine paired messages in the test data?
- Are all provisional items above either resolved or kept at `REVIEW_REQUIRED`?
