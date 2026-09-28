# Segment 146 (Enhanced Fleet Response Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.32 (pages 12-81/295 to 300)
**Item Progress:** Item 1 in progress; 0 of 4 SME items resolved — see [SME/TBA Input Register](segment-146-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-146-sme-tba-learning-note.md) · [Flow](segment-146-flow.md) · [AI-vs-Test Comparison](segment-146-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-146-rule-catalog.json) · [SME/TBA Input Register](segment-146-sme-tba-input-register.md)

## Segment Definition

Response counterpart to Segment 145: 6-table catalog (001 Flags/002 Non-Fuel Limits/003 Fuel Limits/004 Prompt Formats/005 Customer Info/010 Additional Response Data). Table 004 uses a rich edit-mask grammar; Table 002 is absent for Comdata.

## Rule Set (5 rules)

Segment Type/Length sourcing (provisional), 6-table catalog, Comdata Table-002 exclusion, edit-mask grammar (scope provisional), Appendix F product codes (scope provisional).

## `[PROVISIONAL]` Items — All 4 Open

See [SME/TBA Input Register](segment-146-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not include Table 002 in a Comdata response.
2. Do not assume simple length-only validation suffices for Table 004's edit-mask grammar.
3. Do not fabricate Appendix F product code values without confirmation.
