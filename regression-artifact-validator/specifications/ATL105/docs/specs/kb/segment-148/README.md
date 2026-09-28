# Segment 148 (WEX Available Product Fleet Information Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.33 (page 12-87/301)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved — see [SME/TBA Input Register](segment-148-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-148-sme-tba-learning-note.md) · [Flow](segment-148-flow.md) · [AI-vs-Test Comparison](segment-148-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-148-rule-catalog.json) · [SME/TBA Input Register](segment-148-sme-tba-input-register.md)

## Segment Definition

Sends WEX fuel-restriction information to the terminal in a WEX Financial Transaction response. Available Product Information is a fixed 17-character sub-structure with 9 documented fuel-restriction codes.

## Rule Set (4 rules)

WEX-response-only applicability, Segment Type/Length, max-length reconciliation (provisional), fixed sub-structure format.

## Do-Not-Assume Rules

1. Do not certify a maximum length without resolving the 23/17/999 discrepancy.
