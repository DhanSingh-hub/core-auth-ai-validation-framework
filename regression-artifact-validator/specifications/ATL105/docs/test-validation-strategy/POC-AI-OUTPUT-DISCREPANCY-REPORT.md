# POC AI Output Discrepancy Report

## Scope

This review covers the approved intermediate output from the POC pipeline at:

`C:\Users\F5H46GZ\Downloads\POC-DEMO\POC-DEMO\core-auth-test-generation-platform\src\pipeline`

Reviewed artifacts:

- `step5_requirements/approved/requirement_catalog.json`
- `scenarios/approved/approved_scenarios.json`
- `test_generation/approved/test_case_catalog.json`
- POC generator sources under `scenarios/`, `test_generation/`, and `agents/`

The POC files were not modified. The findings below compare the POC output with the canonical artifact contract and the requested ATL105 intake contract.

## Executive Summary

The POC currently produces a large, approved intermediate catalog, not a final independent test solution package. It is useful as a derivation and review work product, but it cannot yet be consumed as the final BR -> TS -> TC -> test-data package because the artifacts are not independently packaged, human-readable, converter-ready, or consistently approved.

## Findings

| ID | Severity | Discrepancy | Evidence | Required correction |
|---|---|---|---|---|
| POC-001 | Blocker | No separate JSON test-data artifact exists per test case. | The approved catalog contains 22,468 test cases and no case has `test_data_file`, `testDataFile`, `test_data_files`, `testDataFiles`, `data_file`, or `dataFile`. | Emit one JSON file per test case, for example `test-data/TC-0001.json`, and link it from the corresponding TC by stable ID. |
| POC-002 | Blocker | Test data is not in the required converter-ready ATL105 shape. | All 22,468 cases have a `request`, but zero expose a `Financial Request` wrapper and zero expose a `Financial Response` wrapper. | Define and validate the exact JSON-to-ATL105 input contract before approval. The file must contain the complete message context required by the converter, not only internal segment dictionaries. |
| POC-003 | Blocker | BR, TS, and TC are not human-readable deliverables. | The approved catalogs are machine-oriented JSON arrays. Test cases have no `title`, `description`, or `human_readable` field. | Generate readable Markdown, Word, Excel, or PDF views for BRs, TSs, and TCs while retaining machine-readable source artifacts. |
| POC-004 | Blocker | One catalog is used instead of independently addressable test-case packages. | `test_case_catalog.json` has only `test_cases`, `total_approved`, and `state`; it has no package manifest, data-file index, or per-case artifact links. | Produce a package manifest plus one independently addressable BR, TS, TC, and TD linkage for every delivered case. |
| POC-005 | Critical | A single test case combines unrelated segment domains. | `TC-0001` request keys include `Standard Message Data Segment`, `Fleet Data Segment`, `Product Code Data Segment`, `Purchase Card Data Segment`, `Variable Information Data Segment`, and `CA Public Key File Data Segment`. | Build each case from the selected message context and include only applicable segments. Reject cross-domain segment composition unless the source explicitly requires it. |
| POC-006 | Critical | Approved status conflicts with review status. | `approved_scenarios.json` reports `state = APPROVED`, while `flagged_for_review = 7191`; all 7,191 scenarios are flagged. The requirement catalog reports 3,681 approved requirements with 679 flagged. | Separate `candidate`, `review-required`, `approved-for-human-review`, and `approved-for-execution` states. Do not label an artifact execution-ready while unresolved review flags remain. |
| POC-007 | Critical | Source specification version does not match the validation baseline. | POC generator/composer sources use `SPEC_VERSION = "2025-3"`; the validator baseline is ATL105 `2026-3`. | Make specification name and version explicit in the manifest and every source anchor. Block package validation when the versions do not match the selected baseline. |
| POC-008 | Critical | Scenario oracle authority is insufficient for final certification. | Generated scenarios use Level C chatbot-sourced response oracles and explicitly require PDF validation. | Treat chatbot-derived values as review candidates only. Promote values to executable expectations only after verification against an authoritative ATL105 source and record the source anchor. |
| POC-009 | Critical | Constraint filtering is not enforcing structured mutual exclusion. | The POC constraint filter is documented as a pass-through because structured mutual-exclusion rules are not parsed. | Parse constraints into executable predicates and reject cases that violate field applicability, segment applicability, or mutually exclusive conditions. |
| POC-010 | High | The generated request is an internal provenance dictionary, not a final test-data payload. | Field values are wrapped in objects containing `value`, `method`, `source_page`, `element_no`, and `flags`; the request is keyed by segment display names rather than the converter's canonical envelope. | Keep provenance in sidecar metadata or a defined metadata section, and emit a separate canonical payload accepted by the converter. |
| POC-011 | High | Cryptographic and security-sensitive evidence is incomplete. | The data factory deliberately does not synthesize real EMV cryptograms, PIN blocks, or keys. | Mark these cases as requiring external fixtures or manual execution evidence. Do not present omitted or placeholder cryptographic values as executable test data. |
| POC-012 | High | Response packaging is incomplete. | Every test case has `expected_response`, but no case has a canonical `Financial Response` artifact or a separately linked response fixture. | Package expected response assertions in the canonical response model and link them to the TC and TD. Distinguish field assertions, response-code assertions, and full response fixtures. |
| POC-013 | Medium | Test-case catalog metadata is incomplete. | The catalog has no `total_test_cases` key; the actual array contains 22,468 cases. | Emit authoritative counts for every artifact type and validate counts against the serialized arrays before approval. |
| POC-014 | Medium | Provenance and reproducibility metadata are incomplete at package level. | The catalog has no visible package manifest containing generator revision, source-document hash, schema version, ATL105 version, or generation timestamp. | Add a manifest with package ID, specification/version, schema version, generator revision, source hashes, artifact counts, and approval history. |

## Additional Quality Signals

- Approved requirements: 3,681.
- Requirements flagged for review: 679.
- Approved scenarios: 7,191.
- Scenarios flagged for review: 7,191.
- Approved test cases: 22,468.
- Test-case steps: 22,468, indicating one step per case in the inspected catalog.
- Scenario mix: 2,679 positive and 212 negative scenarios in the approved scenario metadata.
- The request payload includes conditional omissions and untrusted or ambiguous value flags. Those flags are valuable diagnostic metadata, but they must prevent execution approval when the resulting payload is incomplete or ambiguous.

## Minimum Acceptance Contract for the Next POC Output

The next output should be rejected unless all of the following are true:

1. A package manifest identifies ATL105 and the selected version, schema version, generator revision, source hashes, and artifact counts.
2. BR, TS, and TC are available both as readable deliverables and as structured data.
3. Every TC has a stable ID and links to exactly one or more explicit TD files.
4. Every TD is a separate JSON file named or indexed by test-case ID.
5. Every TD passes the JSON-to-ATL105 converter preflight and contains the canonical request envelope.
6. Each case contains only the segments and fields applicable to its message context.
7. Expected response assertions are structured and linked to the TC; response-only fields are not silently placed in request data.
8. Review flags, low-confidence values, unresolved constraints, and non-executable cryptographic placeholders are surfaced as blocking status, not hidden in metadata.
9. BR -> TS -> TC -> TD traceability is complete, with source anchors for ATL105 version, section, element, and rule.
10. The approved state means approved for the stated execution purpose; intermediate and human-review states are distinct.

## Conclusion

The POC output should remain classified as an intermediate derivation/review package. The largest gaps are not cosmetic: the absence of per-test-case JSON data and the absence of a converter-ready ATL105 request prevent execution and independent validation. The mixed-domain request composition and the contradiction between `APPROVED` and universal review flags also make the current approval state unsafe to interpret as final.