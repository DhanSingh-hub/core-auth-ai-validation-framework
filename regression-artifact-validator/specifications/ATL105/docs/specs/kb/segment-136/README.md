# Segment 136 (Moneris Data (Response) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.25 (page 12-61/275)
**Item Progress:** Item 1 in progress; 0 of 4 SME items resolved — see [SME/TBA Input Register](segment-136-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-136-sme-tba-learning-note.md) · [Flow](segment-136-flow.md) · [AI-vs-Test Comparison](segment-136-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-136-rule-catalog.json) · [SME/TBA Input Register](segment-136-sme-tba-input-register.md)

## Segment Definition

Response-side Moneris data segment, used for both financial transactions and Key Load transactions.

## Rule Set (5 rules)

Applicability (Moneris financial/Key Load), Segment Type fixed 136 (sourcing provisional), Segment Length, separator behavior (provisional), Moneris Data TLV field (Appendix V scope provisional).

## `[PROVISIONAL]` Items — All 4 Open

See [SME/TBA Input Register](segment-136-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not assume Segment 136's separator behavior mirrors Segment 135's exactly — it is less detailed in the spec.
2. Do not silently "correct" the Device-sourced Segment Type/Length fields without SME confirmation.
3. Do not fabricate Appendix V TLV sub-field rules.
