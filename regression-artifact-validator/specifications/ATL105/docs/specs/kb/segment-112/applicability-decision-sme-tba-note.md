# Segment 112 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 112 — Additional Information Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.11, 13  
**Oracle:** [segment-112-rule-catalog.json](coverage/segment-112-rule-catalog.json) (10 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 112 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG112-R-004` | Segment 112 appears only at the end of a Financial Transaction response | 12.11 | — | SPEC_DERIVED |
| `SEG112-R-010` | Segment 112 is required in a Financial Transaction Response only when Element 115 (Additional Information Data Segment Flag) equals 1 | 13 | 115 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 115 | Additional Information Data Segment Flag | N | 1 byte | Fixed length of one digit | Value Description 0 No Additional Information Data Segment follows. 1 An Additional Information Data Segment follows. |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which message families may carry Segment 112, and in which Data Section?
2. Is Segment 112 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 112 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 112 ever legitimate, and how should that be diagnosed?

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
Segment 112 appears only at the end of a Financial Transaction response
  -> source: ATL105 2026-3 §12.11 (SEG112-R-004)
  -> a violating payload shall fail validation citing SEG112-R-004
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment112PayloadValidator` now enforces the response flag/presence relationship, Segment Type, declared length, triad shape and shared 984/990/999 bounds. The transaction-flow coordinator rejects Segment 112 in requests and Segment 111 in responses. Table-specific semantics remain partial; see the [combined supplemental-information flow](../supplemental-information-111-112-flow.md).

## Review Checklist

- Is every rule traced to its source anchor (`SEG112-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
