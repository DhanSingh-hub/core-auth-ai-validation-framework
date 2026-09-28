# Segment 139 (Moneris Day End Batch Balance (Request) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.26 (page 12-62/276)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved — see [SME/TBA Input Register](segment-139-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-139-sme-tba-learning-note.md) · [Flow](segment-139-flow.md) · [AI-vs-Test Comparison](segment-139-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-139-rule-catalog.json) · [SME/TBA Input Register](segment-139-sme-tba-input-register.md)

## Segment Definition

Retrieves debit totals for the previous period from Moneris, feeding into the day-end batch close (Segment 141). Part of the 139→140→141→142 Moneris day-end sequence.

## Rule Set (4 rules)

Applicability (precedes batch close), Segment Type fixed 139, required fields (Terminal Identifier, SPDH Header, Moneris Terminal/Merchant ID, Batch Number, Language Indicator), separator behavior (provisional).

## `[PROVISIONAL]` Items — Both Open

See [SME/TBA Input Register](segment-139-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not assume Segment 139 can be used independently of the 139→140→141→142 sequence.
2. Do not assume full or absent Field Separator delimiting without SME confirmation.
