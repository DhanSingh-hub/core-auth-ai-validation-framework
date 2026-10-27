# PSPC BR Coverage — ATL105 2026-4 Conexxus Payment System Product Codes

> Business-requirement coverage for the Conexxus PSPC reference ingested into the ATL105 Test Solution.
> Training memory: [README](README.md). Test-level detail: [Test Coverage](Test%20Coverage.md).
> Validation rules: [Validation Strategy](Validation%20Strategy.md).
> Machine-readable source: [pspc-product-code-chain-package.json](../../../test-output/test-json/pspc-product-code-chain-package.json).

## Summary

| Metric | Value |
|---|---:|
| Reference ingested | Conexxus PSPC v4.1 (2022-05-12) |
| ATL105 version anchored | 2026-4 |
| Previously missing ATL105 reference now resolved | Appendix K-27 Table ID 034 → PSPC (SEG111-SME-012 `ANSWERED`) |
| Business requirements (BR) | 420 |
| BRs with at least one TS → TC → TD chain | 420 (100%) |
| Test scenarios / cases / data | 2,032 / 2,032 / 2,032 |
| PSPC codes covered by classification TDs | 1,000 of 1,000 (`000`-`999`) |
| Element 77 crosswalk TDs | 1,000 of 1,000 |
| SME-approved BRs | 0 |
| Package status | `PARTIALLY_COVERED` |

"Covered" means that an authored BR has executable, source-anchored test data, and that `PaymentSystemProductCodeOracleTest` replays it with no failures. It is **not** SME approval or certification of production eligibility. All BRs remain `REVIEW_REQUIRED` until an SME signs off.

## ATL105 reference → BR traceability

| ATL105 2026-4 anchor | Previously | BRs now covering it |
|---|---|---|
| Appendix K-27 (lines 36323-36324): "refer to the Conexxus PSPC reference document" | Missing document; values not trainable | `BR-PSPC-T034-VALUES`, plus 413 `BR-PSPC-ROW-*` |
| Appendix K-26 (36291-36310): Table 034 layout, opt-in and program scope | Catalog title only (`AppendixKTableCatalog`) | `BR-PSPC-T034-STRUCTURE`, `BR-PSPC-T034-OPT-IN`, `BR-PSPC-T034-PROGRAM`, `BR-PSPC-T034-PACKING` |
| Appendix I-46 (31849-31882): Table 049 Sub Table 18 Purchase Restriction Method | Table 049 knowledge, review-gated | `BR-PSPC-T034-OPT-IN`, `BR-PSPC-T034-PROGRAM` |
| Section 13.2 Element 118 (24752-24754): Element 116 value `034` | Value catalog BR `AI-112-001-034` (presence only) | Linked through `BR-PSPC-T034-STRUCTURE` |
| Appendix F-1 (28593-28612): Element 77 authority | Appendix F oracle (2026-3) | `BR-PSPC-XWALK-E77`, `BR-PSPC-FORMAT`; 2026-4 identity check |

## BR catalog

### Table 034 Host-based Purchase Restriction

| BR | Requirement | Source | TDs | PASS / REVIEW / FAIL | Status |
|---|---|---|---:|---|---|
| `BR-PSPC-T034-STRUCTURE` | Table ID is `034`; Table Data is printable `ans`, 1-32 bytes | K-26 | 5 | 1 / 0 / 4 | REVIEW_REQUIRED |
| `BR-PSPC-T034-OPT-IN` | Table 034 is received only when the request carried Sub Table 18 = `2` or `3`. If absent, BUYPASS defaults to no restrictions. | K-26, I-46 | 3 | 0 / 0 / 3 | REVIEW_REQUIRED |
| `BR-PSPC-T034-PROGRAM` | Applies only to MasterCard Enhanced Fleet EMV and Visa Fleet EMV 2.0. Visa is review-gated by SEG111-SME-003. | K-26, I-46 | 2 | 0 / 1 / 1 | REVIEW_REQUIRED |
| `BR-PSPC-T034-VALUES` | Carried codes resolve against PSPC v4.1: assigned → PASS; `000` → FAIL; deprecated/reserved → REVIEW | K-27 + PSPC | 10 | 4 / 4 / 2 | REVIEW_REQUIRED |
| `BR-PSPC-T034-PACKING` | ATL105 does not define how codes are packed in Table Data; without an explicit code list, value semantics stay REVIEW_REQUIRED | K-26, K-27 | 1 | 0 / 1 / 0 | REVIEW_REQUIRED |

### Element 77 crosswalk and format

| BR | Requirement | TDs | Outcome split | Status |
|---|---|---:|---|---|
| `BR-PSPC-XWALK-E77` | Element 77 stays governed by ATL105 Appendix F; PSPC divergence is flagged, never substituted | 1,000 | 858 PASS (consistent) / 142 REVIEW_REQUIRED (divergent) | REVIEW_REQUIRED |
| `BR-PSPC-FORMAT` | A PSPC code is exactly three ASCII digits | 11 | 3 PASS / 8 FAIL | REVIEW_REQUIRED |

Crosswalk relation counts:

| Relation | Codes |
|---|---:|
| DESCRIPTION_MATCH | 221 |
| NEITHER_ASSIGNED | 621 |
| APPENDIX_F_PROPRIETARY_ASSIGNMENT | 17 |
| BOTH_UNUSED | 1 |
| DESCRIPTION_DIFFERS | 83 |
| PSPC_ONLY_ASSIGNED | 38 |
| APPENDIX_F_ONLY_ASSIGNED | 13 |
| PSPC_DEPRECATED | 6 |

### PSPC source rows (`BR-PSPC-ROW-<startCode>`)

Each PSPC source row is one BR. The BR preserves the row's exact description, category and assignment state, anchored to its page and line. Each code in the row's range has its own classification TD.

| Category | Range | Row BRs | Classification TDs (codes) |
|---|---|---:|---:|
| Not Used | 000 | 1 | 1 |
| Dispensed Motor Fuels and Additives | 001-099 | 65 | 99 |
| Vehicle Products / Services | 100-149, 650-699 | 57 | 100 |
| Aviation Fuels | 150-174 | 11 | 25 |
| Aviation Products / Services | 175-224 | 42 | 50 |
| Marine Fuels | 225-249 | 10 | 25 |
| Marine Products / Services | 250-299 | 8 | 50 |
| Other Dispensed Fuels and Metered Products | 300-399 | 42 | 100 |
| Merchandise | 400-559, 591-599 | 90 | 169 |
| Prepaid and Bill Pay | 560-590 | 18 | 31 |
| Packaged Fuels | 600-624 | 9 | 25 |
| Reserved for Conexxus Future Use | 625-649, 720-799 | 2 | 105 |
| Cannabinoids | 700-719 | 3 | 20 |
| Reserved for Proprietary Use | 800-899 | 1 | 100 |
| Negative Transactions | 900-949 | 28 | 50 |
| Administrative | 950-999 | 26 | 50 |
| **Total** | | **413** | **1,000** |

## What changed in coverage

| Area | Before ingestion | After ingestion |
|---|---|---|
| Table 034 value list | Not trainable; external reference missing | 1,000 PSPC codes classified; Table 034 value BR executable |
| Table 034 structure, opt-in and program | Title-level catalog entry only | 4 BRs with 11 positive and negative TDs |
| Element 77 vs external standard | Not assessed | 1,000-code crosswalk; 142 divergent codes flagged |
| Appendix F version currency | 2026-3 only | Verified identical in 2026-4 |
| SEG111-SME-012 | OPEN | ANSWERED (version binding still to be confirmed by an SME) |

## Remaining BR gaps

| Gap | Why it is not closed | Register |
|---|---|---|
| PSPC version binding | ATL105 names no version; v4.1 is the supplied copy | SEG111-SME-012 |
| Table 034 packing format | ATL105 is silent on separators, count and grouping inside `ans32` | SEG111-SME-012 |
| Visa Fleet EMV 2.0 applicability | Appendix K-26 vs Appendix I-46 conflict | SEG111-SME-003 |
| Divergent Element 77 meanings (142 codes) | Requires a BUYPASS SME ruling if PSPC meanings are ever accepted for Element 77 | — |
| Merchant / Dynamic Card Table eligibility | Configuration evidence not available | — |
| AI Solution artifacts | Not yet compared against these BRs | — |
