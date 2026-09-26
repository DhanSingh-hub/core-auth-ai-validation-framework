# Segment 153 (Network Token Data Request Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.38 (page 12-93/307 to 309)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-153-sme-tba-learning-note.md) · [Flow](segment-153-flow.md) · [AI-vs-Test Comparison](segment-153-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-153-rule-catalog.json) · [SME/TBA Input Register](segment-153-sme-tba-input-register.md)

## Segment Definition

TLV-of-TLVs container (mirrors Segment 145/146) with 6 fixed-format Network Token sub-tables.

## Rule Set (3 rules)

Multi-sub-segment structure, Segment Type/Length, 6-table catalog (Element 239, shared number with Segments 145/146 — independent meaning).

## Do-Not-Assume Rules

1. Do not conflate Element 239 here with Segments 145/146's Element 239.
