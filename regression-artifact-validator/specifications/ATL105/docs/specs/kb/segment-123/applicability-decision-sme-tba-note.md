# Segment 123 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 123 — NFC Payment Tokenization Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.19, AppendixY  
**Oracle:** [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) (11 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 123 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (3)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG123-R-003` | Segment 123 maximum length is 186 alphanumeric characters; originates at the Device | 12.19 | — | SPEC_DERIVED |
| `SEG123-R-005` | Segment 123 is required on all initial and recurring transactions involving tokenized data, and on transactions that include MasterCard Token/DSRP or Visa TAVV data | 12.19 | — | SPEC_DERIVED |
| `SEG123-R-011` | When both MasterCard DSRP cryptogram and SecureCode/Identity Check 3DS AAV are present in the same request, the AAV must be carried in Segment 111 Table ID 36 (UCAF) and the Token/DSRP cryptogram must be carried in Segment 123 Element 237 (TAVV) — the two fields are not interchangeable and both may be required simultaneously | 12.19,AppendixY | 237 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 237 | TAVV Cryptogram | AN | 28 bytes | Fixed length of 28 bytes. | — |

## Catalog Notes

- `SEG123-R-011` — When both are present, UCAF Security Level Code positions 1-2 must equal '21' (Channel Encrypted) per the cross-reference at line ~36013.

## SME Reasoning

Ask:

1. Which message families may carry Segment 123, and in which Data Section?
2. Is Segment 123 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 123 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 123 ever legitimate, and how should that be diagnosed?

## TBA Dependency Chain

```text
transaction / message family
  -> Data Section placement
  -> inclusion condition
  -> companion-segment set
  -> Element 63 (Number of Segments) count where applicable
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment 123 maximum length is 186 alphanumeric characters originates at the Device
  -> source: ATL105 2026-3 §12.19 (SEG123-R-003)
  -> a violating payload shall fail validation citing SEG123-R-003
```

## Open Provisional Items

- **P-01** (SEG123-R-011): Confirm the UCAF Security Level Code '21' requirement scope: does it apply whenever Segment 123 TAVV and Segment 111 Table ID 36 UCAF co-occur, or only for specific card brands?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment123PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG123-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
