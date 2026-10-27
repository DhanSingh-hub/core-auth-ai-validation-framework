# PSPC BR Coverage — ATL105 2026-4 Conexxus Payment System Product Codes

> Business-requirement coverage for the Conexxus PSPC reference ingested into the ATL105 Test Solution.
> Training memory: [README](README.md). Test-level detail: [Test Coverage](Test%20Coverage.md).
> Validation rules: [Validation Strategy](Validation%20Strategy.md).
> Machine-readable sources: [v4.1 package](../../../test-output/test-json/pspc-product-code-chain-package.json),
> [v3.7 package](../../../test-output/test-json/pspc-v3.7-product-code-chain-package.json).

## Summary

| Metric | v4.1 package | v3.7 package | Total |
|---|---:|---:|---:|
| Reference version | 4.1 (2022-05-12) | 3.7 (2015-09-04) | — |
| Business requirements (BR) | 421 | 396 | **817** |
| BRs with at least one TS → TC → TD chain | 421 (100%) | 396 (100%) | 817 (100%) |
| Test scenarios / cases / data | 2,043 each | 3,000 each | **5,043** each |
| PSPC codes covered by classification TDs | 1,000 of 1,000 | 1,000 of 1,000 | — |
| Element 77 crosswalk TDs (REVIEW_REQUIRED) | 1,000 (142) | 1,000 (104) | — |
| Version-diff TDs (version-sensitive) | — | 1,000 (62) | — |
| Table 034 TDs | 32 | — | 32 |
| SME-approved BRs | 0 | 0 | 0 |
| Package status | `PARTIALLY_COVERED` | `PARTIALLY_COVERED` | — |

The ATL105 anchor version is 2026-4. The previously missing reference, Appendix K-27 Table ID 034 → PSPC, is now resolved (SEG111-SME-012 `ANSWERED`). ATL105 doesn't bind a PSPC version, so both supplied versions are trained.

"Covered" means that an authored BR has executable, source-anchored test data, and that `PaymentSystemProductCodeOracleTest` replays it with no failures. It is **not** SME approval or certification of production eligibility. All BRs remain `REVIEW_REQUIRED` until an SME signs off.

## ATL105 reference → BR traceability

| ATL105 2026-4 anchor | Previously | BRs now covering it |
|---|---|---|
| Appendix K-27 (lines 36323-36324): "refer to the Conexxus PSPC reference document" | Missing document; values not trainable | `BR-PSPC-T034-VALUES`, `BR-PSPC-T034-VERSION`, `BR-PSPC-VERSION-DIFF`, plus 413 `BR-PSPC-ROW-*` (v4.1) and 394 `BR-PSPC37-ROW-*` (v3.7) |
| Appendix K-26 (36291-36310): Table 034 layout, opt-in and program scope | Catalog title only (`AppendixKTableCatalog`) | `BR-PSPC-T034-STRUCTURE`, `BR-PSPC-T034-OPT-IN`, `BR-PSPC-T034-PROGRAM`, `BR-PSPC-T034-PACKING` |
| Appendix I-46 (31849-31882): Table 049 Sub Table 18 Purchase Restriction Method | Table 049 knowledge, review-gated | `BR-PSPC-T034-OPT-IN`, `BR-PSPC-T034-PROGRAM` |
| Section 13.2 Element 118 (24752-24754): Element 116 value `034` | Value catalog BR `AI-112-001-034` (presence only) | Linked through `BR-PSPC-T034-STRUCTURE` |
| Appendix F-1 (28593-28612): Element 77 authority | Appendix F oracle (2026-3) | `BR-PSPC-XWALK-E77`, `BR-PSPC37-XWALK-E77`, `BR-PSPC-FORMAT`; 2026-4 identity check |

## BR catalog

### Table 034 Host-based Purchase Restriction (v4.1 package)

| BR | Requirement | Source | TDs | PASS / REVIEW / FAIL | Status |
|---|---|---|---:|---|---|
| `BR-PSPC-T034-STRUCTURE` | Table ID is `034`; Table Data is printable `ans`, 1-32 bytes | K-26 | 5 | 1 / 0 / 4 | REVIEW_REQUIRED |
| `BR-PSPC-T034-OPT-IN` | Table 034 is received only when the request carried Sub Table 18 = `2` or `3`. If absent, BUYPASS defaults to no restrictions. | K-26, I-46 | 3 | 0 / 0 / 3 | REVIEW_REQUIRED |
| `BR-PSPC-T034-PROGRAM` | Applies only to MasterCard Enhanced Fleet EMV and Visa Fleet EMV 2.0. Visa is review-gated by SEG111-SME-003. | K-26, I-46 | 2 | 0 / 1 / 1 | REVIEW_REQUIRED |
| `BR-PSPC-T034-VALUES` | Carried codes resolve against PSPC: assigned → PASS; `000` → FAIL; deprecated/reserved → REVIEW | K-27 + PSPC | 8 | 2 / 4 / 2 | REVIEW_REQUIRED |
| `BR-PSPC-T034-PACKING` | ATL105 does not define how codes are packed in Table Data; without an explicit code list, value semantics stay REVIEW_REQUIRED | K-26, K-27 | 1 | 0 / 1 / 0 | REVIEW_REQUIRED |
| `BR-PSPC-T034-VERSION` | With no PSPC version bound, a code is judged against v3.7 and v4.1, and a disagreement is REVIEW_REQUIRED. A stated version is judged against that version only. An unsupported version is FAIL. | K-27 + PSPC v3.7/v4.1 | 13 | 6 / 6 / 1 | REVIEW_REQUIRED |
| **Total** | | | **32** | **9 / 12 / 11** | |

### Element 77 crosswalk, version diff and format

| BR | Package | Requirement | TDs | Outcome split | Status |
|---|---|---|---:|---|---|
| `BR-PSPC-XWALK-E77` | v4.1 | Element 77 stays governed by ATL105 Appendix F; PSPC v4.1 divergence is flagged, never substituted | 1,000 | 858 PASS / 142 REVIEW_REQUIRED | REVIEW_REQUIRED |
| `BR-PSPC37-XWALK-E77` | v3.7 | Same rule against PSPC v3.7 | 1,000 | 896 PASS / 104 REVIEW_REQUIRED | REVIEW_REQUIRED |
| `BR-PSPC-VERSION-DIFF` | v3.7 | Every code whose state or meaning changed between v3.7 and v4.1 is version-sensitive | 1,000 | 938 PASS (stable) / 62 REVIEW_REQUIRED (sensitive) | REVIEW_REQUIRED |
| `BR-PSPC-FORMAT` | v4.1 | A PSPC code is exactly three ASCII digits | 11 | 3 PASS / 8 FAIL | REVIEW_REQUIRED |

Element 77 crosswalk relation counts:

| Relation | v3.7 | v4.1 |
|---|---:|---:|
| DESCRIPTION_MATCH | 235 | 221 |
| NEITHER_ASSIGNED | 645 | 621 |
| APPENDIX_F_PROPRIETARY_ASSIGNMENT | 17 | 17 |
| BOTH_UNUSED | 1 | 1 |
| DESCRIPTION_DIFFERS | 63 | 83 |
| PSPC_ONLY_ASSIGNED | 14 | 38 |
| APPENDIX_F_ONLY_ASSIGNED | 0 | 13 |
| PSPC_DEPRECATED | 25 | 6 |

Version-diff relation counts (v3.7 → v4.1):

| Relation | Codes |
|---|---:|
| UNCHANGED | 905 |
| RENAMED | 33 |
| NEWLY_ASSIGNED | 24 |
| REUSED_AFTER_DEPRECATION | 12 |
| DEPRECATED_TO_RESERVED | 13 |
| NEWLY_DEPRECATED | 6 |
| STATE_CHANGED | 7 |

### PSPC source rows (`BR-PSPC-ROW-<startCode>` for v4.1, `BR-PSPC37-ROW-<startCode>` for v3.7)

Each PSPC source row is one BR. The BR preserves the row's exact description, category and assignment state, anchored to its page and line. Each code in the row's range has its own classification TD.

| Category | Range | v4.1 row BRs | v3.7 row BRs | Classification TDs per version |
|---|---|---:|---:|---:|
| Not Used | 000 | 1 | 1 | 1 |
| Dispensed Motor Fuels and Additives | 001-099 | 65 | 72 | 99 |
| Vehicle Products / Services | 100-149, 650-699 | 57 | 55 | 100 |
| Aviation Fuels | 150-174 | 11 | 9 | 25 |
| Aviation Products / Services | 175-224 | 42 | 42 | 50 |
| Marine Fuels | 225-249 | 10 | 9 | 25 |
| Marine Products / Services | 250-299 | 8 | 8 | 50 |
| Other Dispensed Fuels and Metered Products | 300-399 | 42 | 36 | 100 |
| Merchandise | 400-559, 591-599 | 90 | 90 | 169 |
| Prepaid and Bill Pay | 560-590 | 18 | 18 | 31 |
| Packaged Fuels | 600-624 | 9 | 9 | 25 |
| Reserved for Conexxus Future Use | 625-649, 720-799 (v3.7: 700-799) | 2 | 2 | 105 / 125 |
| Cannabinoids (v4.1 only) | 700-719 | 3 | — | 20 / — |
| Reserved for Proprietary Use | 800-899 | 1 | 1 | 100 |
| Negative Transactions | 900-949 | 28 | 18 | 50 |
| Administrative | 950-999 | 26 | 24 | 50 |
| **Total** | | **413** | **394** | **1,000** |

## What changed in coverage

| Area | Before ingestion | After v4.1 ingestion | After v3.7 ingestion (2026-10-27) |
|---|---|---|---|
| Table 034 value list | Not trainable; external reference missing | 1,000 v4.1 codes classified | 1,000 v3.7 codes also classified; unbound codes judged against both versions |
| Table 034 BRs | Title-level catalog entry only | 5 BRs, 21 TDs | 6 BRs, 32 TDs (new `BR-PSPC-T034-VERSION`) |
| Element 77 vs external standard | Not assessed | v4.1 crosswalk, 142 divergent | v3.7 crosswalk, 104 divergent; Appendix F shown to follow the numbering from before the v3.5 deprecations |
| Version drift | Not assessed | — | 1,000-code diff; 62 version-sensitive codes |
| Appendix F version currency | 2026-3 only | Verified identical in 2026-4 | Unchanged |
| SEG111-SME-012 | OPEN | ANSWERED (v4.1) | ANSWERED (v3.7 + v4.1; binding still open) |

## Remaining BR gaps

| Gap | Why it is not closed | Register |
|---|---|---|
| PSPC version binding | ATL105 names no version; v3.7 and v4.1 disagree on 62 codes | SEG111-SME-012 |
| Table 034 packing format | ATL105 is silent on separators, count and grouping inside `ans32` | SEG111-SME-012 |
| Visa Fleet EMV 2.0 applicability | Appendix K-26 vs Appendix I-46 conflict | SEG111-SME-003 |
| Divergent Element 77 meanings (104 / 142 codes) | Requires a BUYPASS SME ruling if PSPC meanings are ever accepted for Element 77 | — |
| Merchant / Dynamic Card Table eligibility | Configuration evidence not available | — |
| AI Solution artifacts | Not yet compared against these BRs | — |
