# Combined Supplemental Information Flow

**Specification:** BUYPASS ATL105 2026-3<br>
**Scope:** Request Segment 111 / Appendix I and response Segment 112 / Appendix K<br>
**Status:** `IN_PROGRESS`; shared envelope calculation and directional dispatch implemented, table semantics and certification incomplete.

## Transaction Flow

```text
Financial Transaction Request
  -> optional Segment 111
     -> Segment 111 envelope (type, computed length, repeated triads, caps)
     -> Appendix I predicate by Element 111 selector for implemented Tables 001-009
     -> unsupported/context-dependent table results remain NOT_ASSERTABLE or REVIEW_REQUIRED

Financial Transaction Response
  -> required Element 115 flag (0 = Segment 112 absent; 1 = Segment 112 follows)
  -> optional Segment 112, only when Element 115 = 1
     -> Segment 112 envelope (type, computed length, repeated triads, caps)
     -> Appendix K selector classification by Element 116
     -> bounded Element 118 layout predicates for Tables 001, 003, 004
     -> remaining table content stays NOT_ASSERTABLE / REVIEW_REQUIRED
```

`AdditionalInformationTransactionFlowValidator` owns the direction boundary and coordinates the segment validators. `Run2PayloadBatchValidator` dispatches request-side Segment 111 and response-side Segment 112 through this flow. A standalone validator remains available for each segment's focused checks.

## Shared Mechanics, Separate Contracts

`IndicatorLengthValueSectionValidator` calculates repeated Indicator/Length/Value triad lengths and validates numeric three-digit indicators/lengths, value presence, length equality, and caller-supplied per-value/aggregate limits. Segment-specific validators retain their own caps, rule IDs, message direction, source anchors, and layout semantics:

| Context | Selector / length / value | Max value | Repeated section | Segment max | Direction |
|---|---|---:|---:|---:|---|
| Segment 111 / Appendix I | Elements 111 / 112 / 113 | 982 bytes per Chapter 13 | 991 characters | 999 characters | Financial Transaction Request, conditional |
| Segment 112 / Appendix K | Elements 116 / 117 / 118 | 984 bytes | 990 bytes | 999 characters | Financial Transaction Response; Element 115 gated |

The shared helper covers common arithmetic only; it does not make Appendix I and K Table IDs interchangeable. The Table 005 examples are different: Appendix I POS Entry Mode versus Appendix K ECA/TeleCheck Trace ID.

## Element 118 Context Boundary

Appendix K's Element 118 is Additional Information in Segment 112. Element 118 is also used in EMV Additional Information groups in Segments 130 and 131 with distinct companion indicators, lengths, repetition rules and byte caps. The combined flow does not reuse Segment 112 rules for those EMV contexts.

## Current Implementation Boundary

- Appendix I source knowledge contains 78 defined top-level tables, but the logical predicate validator covers only Tables 001-009. Bounded ASCII wire checks do not certify all Appendix I layouts, contexts, nested tables or full messages.
- Appendix K selector recognition covers 43 assigned IDs, reserved `002`, and unlisted gaps `014`, `015`, `033`. `AppendixKDataLayoutValidator` checks Table 001 numeric width, Table 003 AVS/NVS framing, and Table 004 one-character results with network-aware values where context is supplied.
- Appendix K assigned tables other than 001/003/004 remain `NOT_ASSERTABLE` for Element 118 contents. Tables 001/003/004 predicates are representation-scoped and do not establish transaction eligibility, issuer outcome, or full table semantics.
- Element 115 and Segment 112 presence are checked together; catalog ownership and broader companion/family applicability remain `REVIEW_REQUIRED` under `SEG112-SME-003`.
- Section 11.1.2 lists a 349 maximum for the Segment 112 Financial Transaction Response field, while Section 12.11 gives the segment a 999-character maximum. `SEG112-SME-008` is open; values over 349 and at most 999 emit a source-conflict review warning and cannot certify response-context compliance.
- Section 12.11 says Segment 112 appears at the end of the response, while Section 11.1.2 permits Segment 115 after it. `SEG112-SME-009` / `SEG115-SME-004` remain open; the flow does not enforce their relative ordering.
- Segment 111 cardinality and source conflicts remain `REVIEW_REQUIRED` under `SEG111-SME-001`, `SEG111-SME-002`, and `SEG111-SME-121` where applicable.
- Independent AI intake, semantic matching, physical response fixtures, full-message validation, mutation coverage and SME/TBA certification are not complete for both flows.

## Completion Gates

1. Implement source-backed logical rules for every Appendix I and Appendix K layout, including nested tables and conditions; do not infer external/network semantics from the selector name.
2. Add synthetic request/response fixtures with Data Section, Segment 63 counts where applicable, exact separators and calculated lengths.
3. Cover absent/present segments, Element 115 values, multi-triad boundaries, max lengths, invalid/reserved selectors, and response-order constraints.
4. Validate the two flows independently against immutable AI deliveries and report `CONFIRMED`, `REVIEW_REQUIRED`, `MISSING`, and `AI_ONLY` decisions from canonical anchors.
5. Resolve source/business questions with Test Team/SME evidence. Keep `training-status.json` `IN_PROGRESS` until every required gate has evidence and review.

## Validation

Focused regression suites: `IndicatorLengthValueSectionValidatorTest`, `Segment111PayloadValidatorTest`, `Segment112PayloadValidatorTest`, `AppendixKDataLayoutValidatorTest`, `AdditionalInformationTransactionFlowValidatorTest`, and `Run2SegmentValidationTest`.