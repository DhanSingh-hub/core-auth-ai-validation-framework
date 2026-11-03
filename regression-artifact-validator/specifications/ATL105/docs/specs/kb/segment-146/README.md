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

## Executed Chapter 12 continuation

[EnhancedFleetPromptValidator](../../../../../../src/main/java/com/coreauth/validator/canonical/EnhancedFleetPromptValidator.java)
now applies masks to observed captures rather than checking grammar alone.
`history.fleet.capturedResponses` is a text-valued token map;
`priorPromptFormats` supplies concrete formats for `?`, and
`subsequentResponses` records sent tokens for `Z`. Missing evidence remains a
review, malformed evidence is invalid. `O` permits omitted captures but does
not bypass `Z`'s prohibition on subsequent sending.

Execution covers A/B, N, I, TN/TS, inclusive M/X bounds, exact V, literal-position
P, optional O and capture-without-resend Z. M/X without an observed TN/TS type
is not marked passed. Authorizer enumerations and individual value caps are
shared with the request path.

Response sublayout checks cover decimal nonfuel/fuel limits, Appendix F fuel
classification, fixed customer name/city/state positions, and published
additional-response labels/caps. Complete observed `fuelProducts` entries
use text `productCode` and `amount`, with boolean `fuelProductsComplete`;
`nonfuelProducts` uses text `category` and `amount` with boolean
`nonfuelProductsComplete`. Repeated products are summed before comparison
with returned limits. An unlisted product does not acquire an invented zero
limit or eligibility decision. Duplicate response labels remain reviewable;
no last-value or summed-limit policy is invented.

Actual TLV/array intake and authoritative parent history are wired through
[Chapter12ActualPayloadAdapter](../../../../../../src/main/java/com/coreauth/validator/canonical/Chapter12ActualPayloadAdapter.java).
See the [canonical execution report](../../../../test-output/test-json/chapter-12-training-evidence.json).
Observed-history equality does not authenticate host/configuration state or
prove complete transaction correlation. Full segment/chapter closure and
the existing SME approvals remain open.

## Do-Not-Assume Rules

1. Do not include Table 002 in a Comdata response.
2. Do not assume simple length-only validation suffices for Table 004's edit-mask grammar.
3. Do not fabricate Appendix F product code values without confirmation.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-146-rule-catalog.json) (5 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 146 |
|---|---|
| SME/TBA learning note | [Learning note](segment-146-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-146-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-146-business-requirements.md](segment-146-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-146-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-146-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-146-ai-vs-test-requirement-comparison.md) |
