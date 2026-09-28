# Segment DL8 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.49  
**Oracle:** [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment DL8 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL8-R-001` | Segment DL8 contains EMV floor limits (as RID) for the terminal; inclusion in a table load depends on a 'Special' flag set at the terminal level; the data is maintained at the BUYPASS Host, and changes to it set the tab… | 12.49 | — | SPEC_DERIVED |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which Segment DL8 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment DL8)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment DL8 contains EMV floor limits as RID for the terminal inclusion in a table load depends on a 'Special' flag set at the terminal level the data is maintained at the BUYPASS Host, and changes to it set the table download flag for all terminals with the Special flag
  -> source: ATL105 2026-3 §12.49 (SEGDL8-R-001)
  -> a violating payload shall fail validation citing SEGDL8-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL8PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL8-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
