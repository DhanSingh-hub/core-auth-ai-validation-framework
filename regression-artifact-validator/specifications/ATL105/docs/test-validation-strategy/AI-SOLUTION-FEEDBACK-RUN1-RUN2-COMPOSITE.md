# Feedback to AI Solution Team: 2026-09-23 Composite Run1 + Run2 Delivery

## Delivery interpretation

The Test Solution now treats these folders as one composite delivery:

- `Run1`: requirement derivation and scenario-generation stage
- `Run2`: materialized scenario Markdown/traceability records, test cases, QE-shaped test data, readable output, and traceability matrix

This relationship is verified, not assumed: all 6,473 Run1 approved requirement IDs and statements match the 6,473 requirements embedded in Run2 traceability exactly.

## What worked well

1. Producer-local requirement IDs are stable across delivery parts.
2. Run2 preserves requirement-to-scenario-to-test-case links in one traceability registry.
3. The delivery covers all 49 segment families at extraction level.
4. Run2 contains 11,196 scenarios, 13,082 test cases, and 8,945 physically resolvable test-data files when flow variants are included.
5. The readable Markdown outputs are useful for manual review.

## Required corrections

### 1. Publish one composite delivery manifest

Add a manifest at the date folder that declares both parts and their roles:

```json
{
  "deliveryId": "ATL105-AI-2026-09-23-RUN1-RUN2",
  "specification": "ATL105",
  "specificationVersion": "2026-3",
  "sourceDocumentSha256": "<sha256>",
  "parts": [
    {"path": "Run1", "role": "requirements-and-scenarios"},
    {"path": "Run2", "role": "test-cases-test-data-traceability-and-readable-output"}
  ]
}
```

Every downstream artifact should carry `deliveryId` and the source artifact IDs it consumes.

### 2. Correct specification-version generation

The delivery names the 2026-3 PDF, but generator code and output metadata declare `2025-3`. The Test Solution verified this as a hardcoded metadata defect and applied an external hash-bound correction. Future deliveries must:

- derive version from the source manifest rather than a code constant;
- emit the source PDF SHA-256;
- fail generation when filename, manifest version, and source hash disagree;
- use the same version in requirements, scenarios, cases, data, and traceability.

### 3. Publish the standalone scenario catalog

The received `Run1` folder contains the requirement catalog but no standalone scenario artifact, although Run1 is the requirement-and-scenario-generation part of the composite delivery. Materialized scenarios are available through Run2 traceability and Markdown. Please publish the machine-readable scenario catalog as an explicit composite-delivery artifact containing:

- scenario ID;
- complete requirement ID list, not only the first/anchor requirement;
- source anchors;
- segment and transaction context;
- scenario type;
- review status and review reasons;
- confidence and provenance;
- flow ID and step IDs for multi-leg scenarios.

### 4. Replace scripted approval with review-state semantics

Run1 labels the requirement catalog `APPROVED`, but also records `approval_note: Scripted approval (no SME review)`.

Run1 contains:

- 6,473 requirements;
- 3,820 flagged for review;
- 1,071 low-confidence requirements;
- 27 elements pending LLM phrasing.

Use `GENERATED`, `READY_FOR_REVIEW`, `REVIEW_REQUIRED`, and `SME_APPROVED` as distinct states. Do not use `APPROVED` for automated promotion without human approval evidence.

### 5. Complete source anchors at every level

Use the shared identity:

```text
specification | version | section | segment | element | rule
```

Carry the same anchor through BR -> TS -> TC -> TD. Producer-local IDs must remain separate from Test Solution IDs. Semantic aliases may produce review candidates but must not produce automatic confirmation.

### 6. Repair test-data accounting

Run2 reports 8,856 test cases with data files. Independent physical resolution finds 8,945 files: 89 additional flow-case payloads use variant/step filenames and are omitted from the reported count.

Count data by actual resolved files and publish:

- exact file path;
- SHA-256;
- test-case ID;
- flow step ID where applicable;
- whether the file exists;
- whether it is executable, provisional, or awaiting client values.

### 7. Align QE payload schemas with the agreed contract

Current payload results:

- Segment 100: 1,934 applicable payloads passed preflight.
- Segment 101: 250 applicable payloads failed validation.
- Segment 111: 2,965 applicable payloads failed validation.
- Segments 103, 104, 108, 109, and 113: linked data exists, but no payload contains a detectable segment object in the expected schema.
- Segments 102 and 105: Test Solution validators still need implementation, so compliance cannot yet be certified.

Observed naming differences include `Fleet Segment` versus `Fleet Data Segment`, and `Variable Info Segment` versus `Variable Information Data Segment`. Publish and validate one versioned QE payload schema before generation.

### 8. Improve full-chain completeness

Against the current 235-rule independent denominator for ten trained segments:

- confirmed BR coverage: 30/235 = 12.8%;
- confirmed full-chain coverage: 25/235 = 10.6%;
- 70 rules require mapping review;
- 135 rules have no mapped AI coverage;
- no trained segment is execution-ready.

The AI team should prioritize missing rules and broken BR -> TS -> TC -> TD chains rather than increasing raw artifact counts.

### 9. Build Loyalty Card Transaction Requests to the Section 11.2.1 layout

Added 2026-09-29. Evidence: `specifications/ATL105/reports/coverage/FTR-Structure-Validation-TestCases.csv`.

A Loyalty Card Transaction Request contains Segment 100 (required), Segment 108 (required, Data Section 3 Field No. 4), and optionally Segment 114 (Field No. 5). Element 63 is therefore 2 or 3.

- 2,112 test cases are labelled as Loyalty Card Transactions. 2,026 of them contain only Segment 100, and no test case among all 22,789 contains Segment 108.
- The other 86 Loyalty test cases have no segments (4), Segment 114 alone (2), or unrelated segments such as 133 and 140-144.
- 9 test cases contain Segment 114 without Segment 100 or 108, across seven message families; some repeat Segment 114 up to four times.
- The message family is labelled two ways: `Loyalty Card Transaction` (1,057) and `Loyalty Card Transaction Request` (1,055). Use `Loyalty Card Transaction Request`.

Not a defect: `REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST` (Segment 114 in a Financial Transaction Request). The Chapter 12 segment/transaction matrix supports it; the Test Solution is confirming this with the SME (SEG114-SME-002).

### 10. Build ECA/TeleCheck Service Transaction Requests to the Section 11.3.1 layout

Added 2026-09-29. Evidence: `specifications/ATL105/reports/coverage/FTR-Structure-Validation-TestCases.csv`.

An ECA/TeleCheck Service Transaction Request contains Segment 100, Segment 110 (Check Data, Field No. 4), Segment 111 (Variable Information, Field No. 5, maximum 20 bytes in this message), and the conditional Segment 113 (Field No. 6). Element 63 is therefore 3 or 4. Use the message name `ECA/TeleCheck Service Transaction Request`.

- 1,699 test cases are labelled as ECA/TeleCheck Service Transaction Requests. 1,656 of them contain only Segments 100 and 111, so none of those carries the required Segment 110.
- Only 2 contain Segment 110 or 113 (TC-3119 has only Segment 110, TC-3122 only Segment 113), and neither contains Segment 100. No test case has the complete 100 + 110 + 111 layout.
- The remaining 41 have no segments (2) or carry unrelated segments (for example 131-143) instead.
- For voids, use an Appendix G transaction type (8, Purchase Reversal/Void) and populate the ECA/TeleCheck Trace ID. Whether ECA/TeleCheck voids are supported at all is being confirmed with the SME (SEG113-SME-010).

## Requested next-delivery acceptance package

Please provide:

1. Composite delivery manifest with hashes.
2. Approved requirement catalog with honest review status.
3. Standalone scenario catalog.
4. Test-case catalog.
5. Test-data manifest with physical file hashes.
6. Full traceability matrix.
7. Versioned schemas for every artifact type.
8. Generation summary recomputed from physical artifacts.
9. Known-gap list and client-value dependencies.
10. Machine-readable change log from the previous delivery.

## Test Solution evidence available

The Test Solution will return:

- ten segment crosswalks;
- ten validation evidence files;
- per-segment Markdown and HTML reports;
- weighted executive report;
- SME/remediation queue;
- hash-bound specification-version resolution;
- composite-delivery provenance verification.
