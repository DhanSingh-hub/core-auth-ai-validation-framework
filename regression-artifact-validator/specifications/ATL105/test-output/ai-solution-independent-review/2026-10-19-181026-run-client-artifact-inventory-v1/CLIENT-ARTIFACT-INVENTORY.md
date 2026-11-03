# 181026 Run: Client Artifact Inventory

**Intake:** Run3, captured from the `181026 Run` delivery  
**Inventory date:** 2026-10-19  
**Status:** File-level inventory complete; file hashes reconciled to the verified intake manifest.

## Executive Summary

The delivery contains **33,012 files** totaling **1,702,218,214 bytes**. The Run3 intake manifest records 33,012 source files and 33,012 copied files, with no missing paths, extra paths, or hash mismatches. This inventory lists relative paths, file classes, sizes, SHA-256 hashes, and a plain-language description of intended use. It does not include payload or metadata field values.

The delivery is organized around candidate business requirements, scenario records, test-case records, test-data payloads and sidecars, plus traceability and generation-support files. “Intended use” is inferred from the directory, filename, catalog role, and structural type; it is not a claim of business approval or test certification.

## File Groups

| Artifact group | Files |
|---|---:|
| Business-requirement catalog | 1 |
| Candidate-scenario catalog | 1 |
| Generation cache | 1 |
| Pipeline traceability support | 1 |
| Python bytecode cache | 8 |
| Scenario catalog | 1 |
| Scenario-generation support code | 4 |
| Test-case catalog | 1 |
| Test-data metadata sidecar | 16,496 |
| Test-data payload | 16,496 |
| Traceability matrix (machine-readable) | 1 |
| Traceability matrix (readable) | 1 |

## Delivery Roots

| Relative root | Files |
|---|---:|
| `Requirement candidates/` | 2 |
| `qe_shaped_test_data/` | 32,993 |
| `scenarios/` | 14 |
| `test_case_candidates.json/` | 1 |
| `traceability_matrix.md/` | 1 |
| `traceability_matrix_full.json/` | 1 |

## What the Main Artifacts Are For

| Relative path | Purpose | Qualification |
|---|---|---|
| `Requirement candidates/requirement_candidates.json` | Candidate BR statements used by downstream scenario/test generation | Candidate tier; not an approved BR catalog |
| `scenarios/scenarios/approved/approved_scenarios.json` | Scenario records associated with the candidate requirements | Producer label is `DEV_APPROVED`; producer note says scripted approval without SME review |
| `scenarios/scenarios/candidates/candidate_scenarios.json` | Candidate scenario output tier | Empty catalog in this delivery |
| `test_case_candidates.json` | Candidate test cases, links, and declared test-data references | Candidate records and producer completeness labels are not independent acceptance |
| `traceability_matrix.md` | Readable BR-to-scenario-to-test relationship view | Producer-generated traceability; does not establish independent semantic coverage |
| `traceability_matrix_full.json` | Machine-readable form of the producer traceability matrix | Same limitation as the Markdown matrix |
| `qe_shaped_test_data/qe_shaped_test_data/TD-TC-*.json` | Test-data payloads, including step-named transaction-leg payloads | Payload values were not exported or semantically certified in this inventory |
| `qe_shaped_test_data/qe_shaped_test_data/TD-TC-*.meta.json` | Sidecar metadata accompanying payload files | Metadata field values were not exported or semantically certified |
| `scenarios/scenarios/*.py` | Scenario generation and flow-expansion support code | Source tooling, not generated test output |
| `scenarios/scenarios/__pycache__/*.pyc` | Python interpreter bytecode caches | Incidental support files; not intended test inputs |

The CSV contains an entry for **every inventoried file**, including exact relative path, byte size, SHA-256, artifact group, intended use, and client note. It deliberately excludes contents and field values.

## Client-Use Boundaries

- Candidate BRs, scenarios, and test cases should be presented as generated artifacts, not approved requirements or certified tests.
- The scenario catalog's `DEV_APPROVED` producer state does not represent SME sign-off.
- Traceability files are navigation aids from the producer; their presence does not prove source completeness or semantic equivalence.
- Payload and sidecar files are inventoried by name and hash only. This report does not certify data correctness, protocol/wire validity, host behavior, or test execution.
- The file inventory is based on the complete v3 structural inventory cross-checked against the verified Run3 intake manifest. No payload values are included.

## Evidence

- [Complete file-level CSV inventory](181026-RUN-FILE-INVENTORY.csv)
- [Machine-readable summary](summary.json)
- [Run3 intake manifest](../../test-input/ai-solution/runs/2026-10-19/Run3-intake-manifest.json)
- [Delivery-wide structural audit](../2026-10-19-181026-run-delivery-wide-assessment-v3/REPORT.md)

**Use limitation:** This is a file inventory and intended-use guide for client orientation, not a semantic-coverage report, SME approval, execution report, or certification.
