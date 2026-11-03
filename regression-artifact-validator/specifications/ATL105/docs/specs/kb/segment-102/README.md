# Segment 102 Learning Module

This folder is the focused learning and analysis module for the ATL105 Product Code Data Segment (Segment 102), structured the same way as the [Segment 100 module](../segment-100/README.md).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

## Contents

- [SME and Technical Business Analysis Note](segment-102-sme-tba-learning-note.md)
- [Segment 102 End-to-End Flow](segment-102-flow.md)
- [SME/TBA Input Register](segment-102-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-102-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Appendix F Product Code Training](../segment-100/appendix-f-product-code-training.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Actual matched904 applicability continuation

Actual941/991 entries trigger structured source13348–13491 checks through
`ProductActualPayloadAdapter` and opt-in actual intake. Observed
`context.hostDiscount` carries a captured actual904118, successful-load Boolean,
retained new timestamp, selected record, matched current device/sequence/local
clock and matched BIN-table entry bound to actual AccountNumber. Received904
physical checks are reused, not duplicated. Metadata never supplies absent
current products/identities.

Executed predicates bind successful904/timestamp, received eligible and emitted
product codes, interior calendar window and observed card type. Missing
observations remain review; malformed or contradictory evidence is invalid
even alongside missing identity fields. Exact interval endpoints/2400 and
merchant timezone conversion remain review. No941/914 arithmetic, generic BIN
extraction, quantity conversion or rounding formula is invented.26 canonical
executions are also covered by `Chapter12FleetDiscountLoadIntegrationTest`;
see the current [gap register](../chapter-coverage-gap-register.md).

The next continuation binds independently observed904 application evidence to
actual purchased product fields and received eligible product/flat discount.
For991 prepay, Element172's explicit99v999 representation permits an exact
before/after unit-price delta check, using the existing ATL105 decimal-prefix
oracle. Captured applied quantity is bounded by receivedN3 quantity limit only
when its independently observed limit unit matches the actual purchased unit.
Unknown/different units remain review; no unit conversion or discount-total
multiplication is inferred. Missing evidence reviews and malformed observed
values invalidate even beside missing context. Source13356-13357's941 postpay
transaction-amount inclusion remains ordinary unreconciled implementation,
separate from the existing received-load/report-code binding.

## [PROVISIONAL] Items

- P-01: Appendix F now has a 380-row source oracle and 1,013 independent classification/format TD chains. Codes `895`/`896` remain source-conflicted; transaction eligibility/configuration and full Segment 102 validation remain separate gates (`SEG102-SME-001`). See the linked training note.
- P-02: Fuel/EV-products-first ordering rules pending confirmation (`SEG102-SME-002`).
- P-03: Multi-fuel OTR primary-fuel-first rule pending confirmation (`SEG102-SME-003`).
- P-04: Segment 143 product-order consistency rule pending confirmation (`SEG102-SME-004`).
- P-05: No dedicated Segment 102 AI/Test package located (`SEG102-SME-005`).

## Scope

This module covers:

- Segment 102 layout, field values, and serialization rules
- Fuel/EV product-ordering rules within the segment
- Reconciliation between Segment 102 itemized amounts and Segment 100 aggregate amounts
- Mutual exclusivity with Segment 157 and non-applicability to Comdata cards
- Business-requirement, scenario, test-case, and test-data analysis for Segment 102

It does not claim that Segment 102 alone represents every fuel/product transaction. Segment 100 remains the required core; Segment 102 is a conditional Section 3 companion added when the flow requires itemized product data.

## Executable Chapter 12 physical and actual-payload continuation

The shared [product validator](../../../../../../src/main/java/com/coreauth/validator/canonical/ProductSegmentPayloadValidator.java)
now checks actual optional `SerializedSegment` data: measured ASCII length,
the 381-character segment/370-character product-body caps, the two header Field
Separators, literal backslash product delimiters and final Field Separator
(`0x1C`). It compares wire content against actual logical fields and preserves
empty fields rather than accepting a reconstructed wire as producer evidence.
Quantity and Unit Price retain their decimal-prefix checks and nine-character
caps. Observed `context.primaryFuelCode` and boolean `context.evCharging`
activate bounded primary/EV-first checks; known nonfuel codes cannot be
declared primary fuel. Missing merchant/primary provenance remains review.

The [actual payload adapter](../../../../../../src/main/java/com/coreauth/validator/canonical/ProductActualPayloadAdapter.java)
accepts `Products` arrays or one flat product, including observed spellings
`NumberofProducts` and `UnitofMeasure`. Conflicting aliases or simultaneous
flat/array forms are invalid. It normalizes an actual companion
`NonFuelAmount` spelling without injecting metadata values; actual Comdata
card identity cannot be overridden by contradictory context.

Semantic AI intake executes every actual 102/157 occurrence, even with no
metadata field declarations. Five Segment 102 executions persist in the
[canonical chapter evidence](../../../../test-output/test-json/chapter-12-training-evidence.json):
one/multiple-product measured wires, wrong delimiter, wrong length and
ambiguous representations. Full source-facet/merchant applicability and the
source-example amount-reconciliation conflict are not certified by these
bounded executions.

## Authoritative Neighboring Knowledge

- [Segment compatibility matrix](../segment-compatibility-matrix.md)
- [Financial transaction request sections](../11-financial-transaction-request-sections.md)
- [Segment 100 module](../segment-100/README.md)
- [Segment 103 EBT SME note](../segment-103-ebt-sme-note.md)
- [Canonical artifact contract](../../../canonical-artifact-contract.md)

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-102-rule-catalog.json) (25 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 102 |
|---|---|
| SME/TBA learning note | [Learning note](segment-102-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-102-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-102-business-requirements.md](segment-102-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-102-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-102-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-102-ai-vs-test-requirement-comparison.md) |
