# Complete Structural Analysis: 181026 Run

**Status:** COMPLETE_STRUCTURAL_INVENTORY_REVIEW_REQUIRED_SEMANTIC_AND_EXECUTION  
**Intake:** `runs/2026-10-19/Run3`  
**Provenance:** 33,012 files / 1,702,218,214 bytes; SHA-256 verified with zero missing, extra, or mismatched paths.

## Executive Summary

The complete delivery inventory contains 8,470 requirement candidates, 11,356 approved-scenario records, and 15,625 test-case candidates. There are no content-conflicting IDs or broken BR/TS/TC links within the catalogs supplied. The approved-scenario label is `DEV_APPROVED`, with the producer note “Scripted approval (no SME review)”; approved BR and TC catalogs were not supplied.

The primary structural blocker is TD path resolution. All 17,193 declared payload paths fail exact catalog-relative resolution. The files exist in the intake under one additional `qe_shaped_test_data/` directory; this alternative match is diagnostic only and receives no route credit. Thus 0/15,625 TCs have a complete declared physical route. Structural Test-BR candidate reach is 353/601 (58.74%); semantic and executed coverage remain NOT_ASSESSED.

## Populations

| Catalog | Count | Producer state / qualification |
|---|---:|---|
| Requirement candidates | 8,470 | EXTRACTED; 4,886 flagged for review |
| Approved scenarios | 11,356 | DEV_APPROVED label; scripted approval, no SME review; 10,003 flagged |
| Candidate scenarios | 0 | EXTRACTED; 11,367 skipped duplicates |
| Test-case candidates | 15,625 | 100 newly composed; 15,355 skipped duplicates; 4,752 deferred response-side |

Producer completeness classifications are recorded in `analysis.json`; they are not independent acceptance. The intake contains 16,496 physical payload JSON files and 16,496 metadata sidecars. All payloads have sidecars and all sidecars have payloads. Recursive shape scanning covered 32,992 files and exported no field values.

## Matrix Reconciliation

The matrix reports 6,070/8,470 requirements as fully traced (71.7%). That is producer-internal traceability, not independent source coverage. Matrix BR IDs match all 8,470 requirement-catalog IDs and the direct BR→TS and BR→TC edges reconcile. However, 144 scenarios and 144 TCs are not present in requirement-rooted matrix rows; they are scenario-only branches without a requirement link. Matrix and catalog link counts are in `traceability-matrix-reconciliation.json`.

| Link | Catalog edges | Matrix edges | Difference |
|---|---:|---:|---:|
| BR → TS | 16,715 | 16,715 | 0 |
| TS → TC | 15,625 | 15,481 | 144 |
| BR → TC | 20,812 | 20,812 | 0 |

## TD Path Findings

| Measure | Result |
|---|---:|
| Declared TD path occurrences | 17,193 |
| Exact catalog-relative paths resolved | 0 |
| Match after adding one extra `qe_shaped_test_data/` directory (diagnostic only) | 17,193 |
| Unresolved after that diagnostic transform | 0 |
| Complete physical TC routes | 0/15,625 |
| Unreferenced physical payloads / sidecars under exact declarations | 16,496 / 16,496 |

The directory-prefix observation is not a fallback rule. Correct the producer’s TD declarations or regenerate the delivery, then verify a new immutable intake before granting physical-chain reach.

## Test-BR Candidate Reach

The independent Test Solution baseline contains 601 source-derived BRs. 353 have at least one structural source-anchor candidate (58.74%); this is review triage only. 0 have a complete physical candidate route. Anchor-disposition counts and source-hash-bound records are retained in the delivery-wide audit. No candidate is confirmed as semantically equivalent.

## Required Follow-Up

1. Correct the TD catalog-relative path layout and reissue a hash-verified intake; preserve Run3 unchanged.
2. Review the 144 scenario-only branches and associated TCs; do not infer BR links.
3. Supply approved BR and TC catalogs for approved-tier comparison.
4. Adjudicate source anchors and semantics through authorized review; retain semantic and execution measures as NOT_ASSESSED.
5. Separately qualify payload semantics, wire captures, response oracles, and host behavior; this structural report does not certify them.

## Evidence

- [Delivery-wide audit report](../2026-10-19-181026-run-delivery-wide-assessment-v3/REPORT.md)
- [Structured analysis](analysis.json)
- [Matrix reconciliation](traceability-matrix-reconciliation.json)
- `manifest.json` binds this report and its inputs by SHA-256.
