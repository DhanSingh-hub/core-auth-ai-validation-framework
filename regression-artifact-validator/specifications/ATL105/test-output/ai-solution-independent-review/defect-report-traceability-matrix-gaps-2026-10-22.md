# AI-DEV Defect Report: JSON Traceability Matrix Omits Catalog Records and Links

**Report ID:** ATL105-AIDEV-2026-10-22-MATRIX-RECONCILIATION  
**Assigned to:** AI-Dev (traceability-matrix generator/packaging owners)  
**Suggested severity:** Major — proposed for triage  
**Source delivery:** `181026 Run\181026 Run`  
**Specification baseline:** ATL105 2026-3  
**Assessment:** `eg1021-v2`  
**Status:** Reported for AI-Dev triage; external ticket/reference ID not provided.

## Summary

The independent reconciliation compared direct BR/TS/TC catalog identities and declared TC-to-TD paths against **both** supported representations in `traceability_matrix_full.json`: the nested `requirements` tree and `flat_rows`.

The same missing catalog records/links were observed in each representation:

| Missing from each JSON representation | Distinct catalog records/links |
|---|---:|
| Scenario IDs (TS) | 144 |
| Test-case IDs (TC) | 144 |
| BR-to-TS-to-TC scenario/case edges (`TS_TC`) | 144 |
| Catalog-declared TC-to-TD edges (`TC_TD`) | 1,015 |

There were **zero matrix-only identities or edges** in the reconciliation. The validator produced **2,894 representation-specific discrepancy rows** because it reports the same four categories independently for both the nested and flat representations. Deduplicating those parallel rows gives **1,447 distinct catalog-to-matrix discrepancies** (144 + 144 + 144 + 1,015); these are not 1,447 proven underlying product defects.

This report treats the results as **matrix reconciliation gaps requiring triage**, not proof that the direct catalogs or test cases are semantically correct. If the matrix is intended to be a complete view of these catalogs, the omissions make traceability/coverage reporting incomplete. If the matrix intentionally filters records (for example by lifecycle or approval state), that scope must be explicit, documented, and consistently reflected in the matrix contract/reporting.

## Representative evidence

The direct TC catalog contains **TC-001631**, assigned to **SC-1939**, and declares `qe_shaped_test_data/TD-TC-001631.json`. The independent physical assessment resolved the declaration under the delivery's explicit nested layout prefix, found the corresponding payload and metadata, and recorded `COMPLETE_PHYSICAL_DECLARATIONS` with no physical-path issues.

The matrix discrepancy ledger records SC-1939, TC-001631, their TS-to-TC edge and the TC-to-TD declaration as `CATALOG_ONLY` for the nested representation; the flat representation records the corresponding omissions too. This illustrates a matrix omission, not an absent payload.

## Impact

- Matrix-based consumers may not see 144 scenarios and 144 test cases that are present in direct catalogs.
- Traceability from scenario to test case is absent for 144 catalog edges in the matrix.
- 1,015 direct TC-to-TD declarations are not represented as matrix links, making test-data navigation and matrix-derived complete-chain/coverage claims incomplete.
- Both matrix views agree with each other about the omissions, but agreement between views does not reconcile either view to the direct catalogs.
- This check reconciles identity/edge sets only. It does not certify matrix row multiplicity, ordering, coverage/status assertions, Markdown/physical traceability exports, or semantic correctness.

## Requested AI-Dev action

1. Confirm the intended inclusion scope and lifecycle filters for the matrix against all direct catalog tiers.
2. Investigate the matrix generation/packaging path for omitted scenario IDs, test-case IDs, TS-to-TC edges and TC-to-TD declarations.
3. If all catalog rows are in scope, regenerate the nested and flat matrix views from the same authoritative direct catalog relationships.
4. If some rows are intentionally excluded, publish the scope/filter rule and an explicit exclusion disposition for every omitted record/link; do not silently treat exclusions as complete coverage.
5. Add regression tests that compare the nested and flat matrix identities/edges against direct catalogs and declared TD paths, including approved/candidate tiers and multi-leg TD declarations.
6. Re-run the full frozen delivery reconciliation and provide hash-bound outputs. Resolve every discrepancy or record a reviewed, justified exclusion.

## Acceptance criteria

- Every in-scope BR, TS, TC and catalog-declared TD edge is present in both matrix representations, or has an explicit, reviewed out-of-scope disposition.
- Nested and flat representations reconcile to each other and to the documented catalog scope.
- No matrix coverage/completeness claim counts omitted catalog records or links as covered.
- Regression coverage detects removed scenario IDs, case IDs, TS-to-TC edges and TC-to-TD declarations.
- The regenerated delivery, input catalogs and matrix outputs are hash-bound and the delivery-wide reconciliation is rerun.

## Evidence files

- [Deduplicated catalog-to-matrix discrepancy annex](./defect-report-traceability-matrix-gaps-2026-10-22.csv) — one row per distinct discrepancy, retaining whether the nested and flat views each omit it.
- [Representation-specific discrepancy ledger](./eg1021-v2/matrix-discrepancies.jsonl) — validator output with separate rows for each matrix representation.
- [Evidence-gate summary](./eg1021-v2/summary.json) — counts, inputs and limitations.

Deduplicated annex: 1,447 rows (144 TS identities; 144 TC identities; 144 TS-to-TC edges; 1,015 TC-to-TD edges).  
SHA-256: `b4b60c2ca29cb334ddd95062861adb0045028613e3e17b7c20e9d0649c1d433f`
