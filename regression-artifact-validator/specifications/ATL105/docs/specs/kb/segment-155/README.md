# Segment 155 (Real Time Account Updater Response Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.39 (page 12-96/310 to 313)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-155-sme-tba-learning-note.md) · [Flow](segment-155-flow.md) · [AI-vs-Test Comparison](segment-155-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-155-rule-catalog.json) · [SME/TBA Input Register](segment-155-sme-tba-input-register.md)

## Segment Definition

First Data Auth Optimizer account-updater response; fixed-length, no separators; gated by Segment 111's Account Updater Request Indicator; mostly Visa/MasterCard-only sub-tables.

## Rule Set (6 rules)

Auth-Optimizer-gated applicability, no separators, cross-segment field gating, Card Status enumeration, VAU Result Code enumeration (provisional gap), missing Source annotation.

## Do-Not-Assume Rules

1. Do not treat Segment 155 as independently triggered — it depends on Segment 111.
2. Do not insert Field Separators anywhere.
