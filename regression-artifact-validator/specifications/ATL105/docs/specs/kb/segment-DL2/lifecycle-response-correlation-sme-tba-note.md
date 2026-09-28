# Segment DL2 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL2 — Dial String Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.43  
**Oracle:** [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment DL2 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL2-R-003` | Secondary Phone Number block (fields 8-11) mirrors the Primary block's structure, terminated by a Dial String Terminator fixed 'F' (field 12); the secondary number is used only after all primary-number retry attempts ar… | 12.43 | — | REVIEW_REQUIRED |

## Catalog Notes

- `SEGDL2-R-003` — PROVISIONAL: the detailed error-recovery logic is defined in a separate 'Asynchronous Communications Protocol Specifications' document not available in this KB pass. Pending SME confirmation (SEGDL2-SME-001) on scope.

## SME Reasoning

Ask:

1. Which Segment DL2 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment DL2)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Secondary Phone Number block fields 8-11 mirrors the Primary block's structure, terminated by a Dial String Terminator fixed 'F' field 12 the secondary number is used only after all primary-number retry attempts are exhausted per the Asynchronous Communications Protocol Specifications error-recovery logic
  -> source: ATL105 2026-3 §12.43 (SEGDL2-R-003)
  -> a violating payload shall fail validation citing SEGDL2-R-003
```

## Open Provisional Items

- **P-01** (SEGDL2-R-003): Is the Asynchronous Communications Protocol Specifications document (defining primary-to-secondary phone fallback logic) in scope for this training pass?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL2PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL2-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
