# AI-DEV Defect Report: TransArmor Key Load Payloads Missing Data Segment 116

**Report ID:** ATL105-AIDEV-2026-10-22-TRANSARMOR-EMPTY-TD  
**Assigned to:** AI-Dev (test-data composer/serializer and readiness-gate owners)  
**Suggested severity:** Major — proposed for triage  
**Source delivery:** `181026 Run\181026 Run`  
**Specification baseline:** ATL105 2026-3  
**Independent assessment:** `eg1021-v2`, QEF-015  
**Disposition:** Investigate and correct generation/readiness behavior; confirm applicable source rules and scenario intent before regenerating.
**Issue status:** Raised with AI-Dev (reported by requester on 2026-10-22); external ticket/reference ID not recorded here.

## Summary

The delivery-wide check flagged **167 of 16,496** physical test-data payloads for having no substantive data-segment content. Review of every flagged payload found the same pattern:

- Message root is `TransArmor Key Load Request`.
- No data-segment object is serialized in the payload.
- The associated test-case request names `TransArmor Load Data Segment`, but its field map is empty.
- Metadata records the missing lead segment and identifies Data Segment 116 as required.
- All 167 test cases are marked `completeness_status=COMPLETE` and `verification.status=PASS`.
- None declares a `negative_class` or `violated_element_name`.

This is stronger evidence than a generic “empty data” warning: the required segment is absent while the artifacts report completion and pass. The test cases are therefore not credible executable request fixtures in their current form. The metadata itself is producer-generated evidence; confirm it against the specification and the intended use of each case.

## Specification evidence

The checked ATL105 2026-3 extracted specification states on page 13-52:

> For TransArmor PKI Encryption and Tokenization Load Request messages, the following data element and data segment always follow this data element: Element No. 63, Number of Segments; Data Segment No. 116, TransArmor Load Data Segment.

The same source excerpt also identifies Data Segment 116 in the Data Section 3 description. The delivery’s metadata records the absence as `ENVELOPE_LEAD_SEGMENT_MISSING` and cites an ATTR-63 source anchor. AI-Dev/QA should confirm the exact citation mapping and applicability for each scenario; do not rely solely on the metadata assertion.

## Population and impact

| Measure | Result |
|---|---:|
| Flagged physical payloads | 167 |
| Distinct affected test cases | 167 |
| Message family | TransArmor Key Load Request (all 167) |
| Data-segment objects in flagged payloads | 0 in every payload |
| TC completeness / verification | COMPLETE / PASS for all 167 |
| Declared negative class / violated element | None for all 167 |
| Other QEF payload-rule failures overlapping this population | QEF-023 on these rows |

These findings are separate from the Segment Length / Segment Type report. The empty-segment payloads did not overlap those QEF-018/QEF-021 failure populations.

## Representative example

**TC-003224 / SC-3518 / TD-TC-003224.json**

- TC request contains the segment entry `TransArmor Load Data Segment` with **zero fields**.
- Serialized TD has the `TransArmor Key Load Request` envelope but **no data-segment object**.
- Metadata says Data Segment 116 is missing.
- TC reports `COMPLETE` and `PASS`; no negative class or violated element is declared.

The annex includes every affected TC/TS/BR/TD identity, hashes for payload and metadata, producer status, field-count observation and triage disposition. Payload field values are not included.

## Requested AI-Dev action

1. Confirm the cited ATL105 rule and message-family applicability for every affected scenario.
2. Trace why the composer emits a request-segment declaration with zero fields and then serializes no segment.
3. Add a readiness/completeness gate: do not mark a required request segment `COMPLETE` or `PASS` when it has no usable fields or is absent from the serialized payload.
4. For each TC, either:
   - generate a source-valid Segment 116 fixture that satisfies the intended test objective, or
   - explicitly mark the case review-required / not executable if its values require external input or a source decision.
5. If any case intentionally tests an absent segment, declare that negative objective explicitly and provide an independent expected rejection oracle; verify no unrelated missing-data defects confound it.
6. Add regression coverage for both the valid TransArmor request path and the missing-required-segment rejection path.
7. Regenerate the affected outputs and rerun the full population check; provide a hash-bound disposition for all 167 rows.

## Acceptance criteria

- Every annex row is dispositioned as `SOURCE_APPLICABILITY_CONFIRMED`, `INTENTIONAL_NEGATIVE_CONFIRMED`, `UNINTENDED_DEFECT_CONFIRMED`, or `NEEDS_SOURCE_REVIEW`, with reviewer and evidence.
- No TC marked executable/complete has an empty required Segment 116 request or omits it from its payload.
- Intentional negative cases state the precise mutation and expected outcome, and do not receive a positive readiness/pass claim.
- Producer regression tests cover both valid and intentionally invalid requests.
- The regenerated delivery is independently re-audited; remaining exceptions are explicit and hash-bound.

## Evidence

- [Affected payload/test-case annex](./defect-report-transarmor-empty-payloads-2026-10-22.csv) — 167 rows, no raw payload values.
- [Delivery-wide evidence summary](./eg1021-v2/summary.json) — full semantic correctness remains unproven; this report is a targeted structural/source-evidence finding.

Annex contains 167 rows.  
SHA-256: `cfedcb4b94d7a9a22e957815d81a92a669aa47ff2f6c7726cce9ec6fba8ba920`
