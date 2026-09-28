# Segment 141 (Moneris Day End Batch Close (Request) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.28 (page 12-65/279)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved — see [SME/TBA Input Register](segment-141-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-141-sme-tba-learning-note.md) · [Flow](segment-141-flow.md) · [AI-vs-Test Comparison](segment-141-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-141-rule-catalog.json) · [SME/TBA Input Register](segment-141-sme-tba-input-register.md)

## Segment Definition

Closes out each pay point to Moneris at day end; sent once per pay point. All 14 fields explicitly Field-Separator delimited (clearest wire-format documentation in the Moneris family) and Device-sourced.

## Rule Set (3 rules)

Once-per-pay-point applicability, full field separation, uniform Device sourcing.

## Do-Not-Assume Rules

1. Do not send more than once per pay point per day.
2. Do not assume Segment 139's less-clear separator documentation applies identically here.
