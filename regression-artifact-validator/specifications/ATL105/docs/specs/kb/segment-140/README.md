# Segment 140 (Moneris Day End Batch Balance (Response) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.27 (page 12-63/277)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved — see [SME/TBA Input Register](segment-140-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-140-sme-tba-learning-note.md) · [Flow](segment-140-flow.md) · [AI-vs-Test Comparison](segment-140-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-140-rule-catalog.json) · [SME/TBA Input Register](segment-140-sme-tba-input-register.md)

## Segment Definition

Response to Segment 139: reiterates the first 6 request fields, then appends Moneris debit/credit/correction totals. Richest field count (14) of any segment in this training batch.

## Rule Set (3 rules)

Reiteration-plus-totals structure, mixed Device/Moneris field sourcing, signed decimal dollar-value format (`+/-9(16)v99`).

## Do-Not-Assume Rules

1. Do not assume uniform field sourcing — Segment 140 genuinely alternates between Device (echo) and Moneris (new data).
2. Do not validate dollar-value fields as plain numerics — they use a signed, implied-decimal format.
