# Segment 151 (Incomm OTC Market Basket Data (Request) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.36 (page 12-91/305)
**Item Progress:** Item 1 in progress; 0 of 4 SME items resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-151-sme-tba-learning-note.md) · [Flow](segment-151-flow.md) · [AI-vs-Test Comparison](segment-151-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-151-rule-catalog.json) · [SME/TBA Input Register](segment-151-sme-tba-input-register.md)

## Segment Definition

InComm OTC Market Basket request data; can appear in any Data Section 3 field slot. Full DV/PI dataset format is in an external document not yet transcribed into this KB.

## Rule Set (5 rules)

Any-field-position structure, max-length inconsistency (provisional), full-field-separation, origin conflict (provisional), external-format-document dependency (provisional).

## Do-Not-Assume Rules

1. Do not fabricate DV/PI dataset internal structure.
2. Do not assume Device or Host origin without confirmation.
