# PSPC Test Coverage — ATL105 2026-4 Conexxus Payment System Product Codes

> Test-level coverage for the PSPC reference. BR view: [BR Coverage](BR%20Coverage.md).
> Rules applied: [Validation Strategy](Validation%20Strategy.md). Memory: [README](README.md).

## Executable evidence

| Item | Value |
|---|---|
| Test class | [PaymentSystemProductCodeOracleTest](../../../../../src/test/java/com/coreauth/validator/PaymentSystemProductCodeOracleTest.java) |
| Evaluator under test | [PaymentSystemProductCodeOracle](../../../../../src/main/java/com/coreauth/validator/canonical/PaymentSystemProductCodeOracle.java) |
| Expected-label producer | [GeneratePaymentSystemProductCodeCoverage](../../../../../src/main/java/com/coreauth/validator/coverage/GeneratePaymentSystemProductCodeCoverage.java) (separate implementation; derived from the source transcription, not from evaluator output) |
| Chain package | [pspc-product-code-chain-package.json](../../../test-output/test-json/pspc-product-code-chain-package.json) |
| Crosswalk report | [pspc-appendix-f-crosswalk.csv](../../../test-output/test-json/pspc-appendix-f-crosswalk.csv) |
| Last run | 6 tests, 0 failures, 0 errors. Regression neighbours also green: `AppendixFProductCodeOracleTest` (5), `AppendixKTableCatalogTest` (3), `CommunicationRegisterTest` (8). |

Command (from `regression-artifact-validator`):

```powershell
mvn '-Dtest=PaymentSystemProductCodeOracleTest' test
```

## Test methods

| Test | What it proves |
|---|---|
| `committedOracleMatchesEveryPspcSourceRowAndCoversAllCodesOnce` | Committed oracle equals a fresh parse of the PSPC text. Each of the 413 rows' descriptions and codes appears on its anchored lines. Codes `000`-`999` occur exactly once. Assignment row counts are fixed. |
| `atl105ReferencesResolveInVersion2026Dash4AndAppendixFIsUnchanged` | All six ATL105 2026-4 anchors contain their quoted literal. Appendix F bodies in 2026-3 and 2026-4 are identical (521 lines). |
| `classifiesSourceDefinedCategoriesAssignmentsAndExceptions` | 25 handpicked boundary and exception codes; malformed tokens fail; the ten v4.1 release-note codes are assigned |
| `element77CrosswalkKeepsAppendixFAuthoritativeAndFlagsDivergence` | 12 relation examples covering all eight relation types. Appendix F stays the Element 77 authority. The `895` conflict is preserved. |
| `table034HostPurchaseRestrictionAppliesGatingStructureAndPspcValues` | Visa review gate (SEG111-SME-003), MC availability notice, opt-in, the 32-byte boundary, the missing-code-list path and the `000` failure |
| `chainPackageReplaysEveryBrScenarioCaseAndTestData` | Committed package and CSV equal the regenerated versions. Every BR → TS → TC → TD link resolves. All 2,032 TDs are replayed through the evaluator, and every expected field matches. |

## TD inventory

| Mode | TDs | PASS | REVIEW_REQUIRED | FAIL |
|---|---:|---:|---:|---:|
| `CLASSIFICATION` (per code) | 1,000 | 1,000 | 0 | 0 |
| `CLASSIFICATION` (format) | 11 | 3 | 0 | 8 |
| `ELEMENT_77_CROSSWALK` | 1,000 | 858 | 142 | 0 |
| `HOST_PURCHASE_RESTRICTION` | 21 | 5 | 6 | 10 |
| **Total** | **2,032** | **1,866** | **148** | **18** |

A classification `PASS` means the source category, assignment and description were preserved. For example, `PASS` on `850` confirms it is reserved for proprietary use; it does not make `850` a valid product.

## Table 034 test data

All fixtures use `tableId` `034` unless noted. Program `MC` = `MASTERCARD_ENHANCED_FLEET_EMV`; `VISA` = `VISA_FLEET_EMV_2_0`.

| TD | BR | Table Data | Sub Table 18 | Program | productCodes | Expected | Reason |
|---|---|---|---|---|---|---|---|
| `TD-PSPC-T034-MC-HOST-ONLY` | VALUES | `001002` | 2 | MC | 001, 002 | PASS | Assigned Regular, Mid/Plus |
| `TD-PSPC-T034-MC-CHIP-AND-HOST` | VALUES | `062` | 3 | MC | 062 | PASS | Assigned DEF |
| `TD-PSPC-T034-MAX-32` | STRUCTURE | 32 bytes | 2 | MC | 001 | PASS | Upper length boundary |
| `TD-PSPC-T034-V41-ADDED-338` | VALUES | `338` | 2 | MC | 338 | PASS | v4.1 addition |
| `TD-PSPC-T034-CBD-700` | VALUES | `700` | 2 | MC | 700 | PASS | Assigned Cannabidiol |
| `TD-PSPC-T034-VISA-FLEET` | PROGRAM | `001` | 2 | VISA | 001 | REVIEW_REQUIRED | SEG111-SME-003 |
| `TD-PSPC-T034-PACKING-UNSPECIFIED` | PACKING | `001002` | 2 | MC | — | REVIEW_REQUIRED | No explicit code list |
| `TD-PSPC-T034-DEPRECATED-011` | VALUES | `011` | 2 | MC | 011 | REVIEW_REQUIRED | Deprecated |
| `TD-PSPC-T034-RESERVED-CONEXXUS-006` | VALUES | `006` | 2 | MC | 006 | REVIEW_REQUIRED | Reserved for Conexxus |
| `TD-PSPC-T034-RESERVED-PREAUTH-071` | VALUES | `071` | 2 | MC | 071 | REVIEW_REQUIRED | Preauthorization only |
| `TD-PSPC-T034-RESERVED-PROPRIETARY-850` | VALUES | `850` | 2 | MC | 850 | REVIEW_REQUIRED | Proprietary |
| `TD-PSPC-T034-UNUSED-000` | VALUES | `000` | 2 | MC | 000 | FAIL | Not Used |
| `TD-PSPC-T034-BAD-CODE-FORMAT` | VALUES | `1` | 2 | MC | 1 | FAIL | Not three digits |
| `TD-PSPC-T034-PRM-0` | OPT-IN | `001` | 0 | MC | 001 | FAIL | Neither chip nor host |
| `TD-PSPC-T034-PRM-1` | OPT-IN | `001` | 1 | MC | 001 | FAIL | Chip-based only |
| `TD-PSPC-T034-PRM-ABSENT` | OPT-IN | `001` | absent | MC | 001 | FAIL | BUYPASS default: no restrictions |
| `TD-PSPC-T034-NON-FLEET-PROGRAM` | PROGRAM | `001` | 2 | `AMEX_CREDIT` | 001 | FAIL | Not a fleet program |
| `TD-PSPC-T034-WRONG-TABLE-ID` | STRUCTURE | `001` (table `035`) | 2 | MC | 001 | FAIL | Wrong Table ID |
| `TD-PSPC-T034-OVER-32` | STRUCTURE | 33 bytes | 2 | MC | 001 | FAIL | Exceeds `ans32` |
| `TD-PSPC-T034-EMPTY` | STRUCTURE | empty | 2 | MC | 001 | FAIL | No data |
| `TD-PSPC-T034-NON-PRINTABLE` | STRUCTURE | `001<TAB>002` | 2 | MC | 001 | FAIL | Non-`ans` byte |

## Format test data (`BR-PSPC-FORMAT`)

PASS: `001`, `999`, `000`. Code `000` is well-formed; its *classification* is Not Used.

FAIL: `1`, `0001`, `01A`, ` 001`, empty string, JSON `null`, JSON number `1`, Arabic-Indic digits `٠٠١`.

## Boundary and negative coverage

| Dimension | Covered by |
|---|---|
| First/last code (`000`, `999`) | Classification + format TDs |
| Every row boundary (413 rows) | 1,000 per-code classification TDs |
| Category transitions (e.g. `099`/`100`, `559`/`560`, `590`/`591`, `624`/`625`, `649`/`650`, `699`/`700`, `719`/`720`, `799`/`800`, `899`/`900`, `949`/`950`) | Per-code classification TDs |
| Wrapped source rows (`333`, `338`) and the `000` layout quirk | Source fidelity test |
| Assigned names containing "Reserved"/"Proprietary" (`568`, `569`, `920`-`924`) | Classification examples |
| Table 034 length 32/33, empty, non-printable | Table 034 TDs |
| Opt-in `0`/`1`/absent vs `2`/`3` | Table 034 TDs |
| Program MC / Visa / non-fleet | Table 034 TDs |
| Assigned / unused / deprecated / three reservation types in Table 034 | Table 034 TDs |

## Not covered (by design)

- Real host responses, wire captures or converter round-trips for Table 034.
- Packing of multiple PSPC codes inside Table Data. This is network-defined and absent from ATL105.
- Merchant Dynamic Card Table eligibility.
- AI Solution artifacts carrying Table 034 or PSPC claims. Compare them against this package when they arrive.
