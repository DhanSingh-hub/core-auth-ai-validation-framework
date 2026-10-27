# PSPC Test Coverage — ATL105 2026-4 Conexxus Payment System Product Codes

> Test-level coverage for the PSPC reference (v3.7 and v4.1). BR view: [BR Coverage](BR%20Coverage.md).
> Rules applied: [Validation Strategy](Validation%20Strategy.md). Memory: [README](README.md).

## Executable evidence

| Item | Value |
|---|---|
| Test class | [PaymentSystemProductCodeOracleTest](../../../../../src/test/java/com/coreauth/validator/PaymentSystemProductCodeOracleTest.java) |
| Evaluator under test | [PaymentSystemProductCodeOracle](../../../../../src/main/java/com/coreauth/validator/canonical/PaymentSystemProductCodeOracle.java) |
| Expected-label producer | [GeneratePaymentSystemProductCodeCoverage](../../../../../src/main/java/com/coreauth/validator/coverage/GeneratePaymentSystemProductCodeCoverage.java) (separate implementation; derived from the source transcriptions, not from evaluator output) |
| Chain packages | [v4.1](../../../test-output/test-json/pspc-product-code-chain-package.json), [v3.7](../../../test-output/test-json/pspc-v3.7-product-code-chain-package.json) |
| Crosswalk and diff reports | [v4.1 vs Appendix F](../../../test-output/test-json/pspc-appendix-f-crosswalk.csv), [v3.7 vs Appendix F](../../../test-output/test-json/pspc-v3.7-appendix-f-crosswalk.csv), [v3.7 → v4.1 diff](../../../test-output/test-json/pspc-version-diff-v3.7-v4.1.csv) |
| Last run (2026-10-27) | 7 tests, 0 failures, 0 errors. All 5,043 TDs replayed. Regression neighbours `AppendixFProductCodeOracleTest`, `AppendixKTableCatalogTest`, `CommunicationRegisterTest`, `RepositoryStructureTest` and `TrainingStatusConsistencyTest` are also green. |

Command (from `regression-artifact-validator`):

```powershell
mvn '-Dtest=PaymentSystemProductCodeOracleTest' test
```

## Test methods

| Test | What it proves |
|---|---|
| `committedOraclesMatchEveryPspcSourceRowAndCoverAllCodesOnce` | For both v4.1 (413 rows) and v3.7 (394 rows): the committed oracle equals a fresh parse, every row's description and codes appear on its anchored lines, codes `000`-`999` occur exactly once, and the assignment row counts are fixed. |
| `atl105ReferencesResolveInVersion2026Dash4AndAppendixFIsUnchanged` | Both oracles carry the six ATL105 2026-4 anchors, and each anchor's literal is re-read from the extract. Both list supplied versions `3.7` and `4.1`. Appendix F 2026-3 equals 2026-4 (521 lines). |
| `classifiesSourceDefinedCategoriesAssignmentsAndExceptions` | 40 version-specific boundary and exception codes (25 v4.1, 15 v3.7). Default version is 4.1; unsupported versions fail. The release-note codes of each version are assigned in that version. |
| `element77CrosswalkKeepsAppendixFAuthoritativeAndFlagsDivergence` | 18 relation examples across both versions; Appendix F stays the authority. |
| `versionDiffIdentifiesCodesWhoseMeaningChangedBetweenSuppliedVersions` | 13 diff examples covering every relation, fixed relation counts (905/33/24/12/13/6/7), and the committed diff CSV equal to the regenerated one. |
| `table034HostPurchaseRestrictionAppliesGatingStructureVersionAndPspcValues` | Visa review gate, MC availability notice, opt-in, the 32/33-byte boundary, the missing code list, the `000` failure, unbound `052` → REVIEW_REQUIRED (`versionSensitive`, SEG111-SME-012), bound `052` (4.1 PASS, 3.7 REVIEW_REQUIRED), and unbound `325` PASS. |
| `chainPackagesReplayEveryBrScenarioCaseAndTestData` | Both committed packages and both crosswalk CSVs equal the regenerated versions. Every BR → TS → TC → TD link resolves, every BR has a TD, and all 2,043 + 3,000 TDs replay through the evaluator with every expected field matching. |

## TD inventory

| Package | Mode | Version in payload | TDs | PASS | REVIEW_REQUIRED | FAIL |
|---|---|---|---:|---:|---:|---:|
| v4.1 | `CLASSIFICATION` (per code) | default 4.1 | 1,000 | 1,000 | 0 | 0 |
| v4.1 | `CLASSIFICATION` (format) | default 4.1 | 11 | 3 | 0 | 8 |
| v4.1 | `ELEMENT_77_CROSSWALK` | default 4.1 | 1,000 | 858 | 142 | 0 |
| v4.1 | `HOST_PURCHASE_RESTRICTION` | unbound / 3.7 / 4.1 / 9.9 | 32 | 9 | 12 | 11 |
| v3.7 | `CLASSIFICATION` (per code) | 3.7 | 1,000 | 1,000 | 0 | 0 |
| v3.7 | `ELEMENT_77_CROSSWALK` | 3.7 | 1,000 | 896 | 104 | 0 |
| v3.7 | `VERSION_DIFF` | 3.7 vs 4.1 | 1,000 | 938 | 62 | 0 |
| | **Total** | | **5,043** | **4,704** | **320** | **19** |

A classification `PASS` means the source category, assignment and description were preserved. For example, `PASS` on `850` confirms it is reserved for proprietary use; it does not make `850` a valid product.

## Table 034 test data

All fixtures use `tableId` `034` unless noted. Program `MC` = `MASTERCARD_ENHANCED_FLEET_EMV`; `VISA` = `VISA_FLEET_EMV_2_0`. Version `—` means unbound, so the code is judged against both v3.7 and v4.1.

| TD (`TD-PSPC-…`) | BR | Table Data | Sub Table 18 | Program | productCodes | Version | Expected | Reason |
|---|---|---|---|---|---|---|---|---|
| `T034-MC-HOST-ONLY` | VALUES | `001002` | 2 | MC | 001, 002 | — | PASS | Assigned in both versions |
| `T034-MC-CHIP-AND-HOST` | VALUES | `062` | 3 | MC | 062 | — | PASS | DEF assigned in both |
| `T034-MAX-32` | STRUCTURE | 32 bytes | 2 | MC | 001 | — | PASS | Upper length boundary |
| `T034-V41-ADDED-338` | VERSION | `338` | 2 | MC | 338 | 4.1 | PASS | v4.1 addition |
| `T034-V37-338` | VERSION | `338` | 2 | MC | 338 | 3.7 | REVIEW_REQUIRED | Reserved for Conexxus in v3.7 |
| `T034-UNBOUND-338` | VERSION | `338` | 2 | MC | 338 | — | REVIEW_REQUIRED | Versions disagree |
| `T034-CBD-700` | VERSION | `700` | 2 | MC | 700 | 4.1 | PASS | Cannabidiol in v4.1 |
| `T034-UNBOUND-700` | VERSION | `700` | 2 | MC | 700 | — | REVIEW_REQUIRED | Reserved in v3.7 |
| `T034-V41-052` | VERSION | `052` | 2 | MC | 052 | 4.1 | PASS | B1 Diesel in v4.1 |
| `T034-V37-052` | VERSION | `052` | 2 | MC | 052 | 3.7 | REVIEW_REQUIRED | Deprecated ULSD #1 in v3.7 |
| `T034-UNBOUND-052` | VERSION | `052` | 2 | MC | 052 | — | REVIEW_REQUIRED | Reused after deprecation |
| `T034-V37-325` | VERSION | `325` | 2 | MC | 325 | 3.7 | PASS | v3.7 addition |
| `T034-UNBOUND-325` | VERSION | `325` | 2 | MC | 325 | — | PASS | Assigned in both (renamed) |
| `T034-V37-041` | VERSION | `041` | 2 | MC | 041 | 3.7 | PASS | Assigned in v3.7 |
| `T034-V41-041` | VERSION | `041` | 2 | MC | 041 | 4.1 | REVIEW_REQUIRED | Deprecated in v4.1 |
| `T034-UNSUPPORTED-VERSION` | VERSION | `001` | 2 | MC | 001 | 9.9 | FAIL | Not a supplied PSPC version |
| `T034-VISA-FLEET` | PROGRAM | `001` | 2 | VISA | 001 | — | REVIEW_REQUIRED | SEG111-SME-003 |
| `T034-PACKING-UNSPECIFIED` | PACKING | `001002` | 2 | MC | — | — | REVIEW_REQUIRED | No explicit code list |
| `T034-DEPRECATED-011` | VALUES | `011` | 2 | MC | 011 | 4.1 | REVIEW_REQUIRED | Deprecated in v4.1 |
| `T034-RESERVED-CONEXXUS-006` | VALUES | `006` | 2 | MC | 006 | — | REVIEW_REQUIRED | Deprecated (v3.7) / reserved (v4.1) |
| `T034-RESERVED-PREAUTH-071` | VALUES | `071` | 2 | MC | 071 | — | REVIEW_REQUIRED | Proprietary (v3.7) / preauthorization only (v4.1) |
| `T034-RESERVED-PROPRIETARY-850` | VALUES | `850` | 2 | MC | 850 | — | REVIEW_REQUIRED | Proprietary in both |
| `T034-UNUSED-000` | VALUES | `000` | 2 | MC | 000 | — | FAIL | Not Used |
| `T034-BAD-CODE-FORMAT` | VALUES | `1` | 2 | MC | 1 | — | FAIL | Not three digits |
| `T034-PRM-0` | OPT-IN | `001` | 0 | MC | 001 | — | FAIL | Neither chip nor host |
| `T034-PRM-1` | OPT-IN | `001` | 1 | MC | 001 | — | FAIL | Chip-based only |
| `T034-PRM-ABSENT` | OPT-IN | `001` | absent | MC | 001 | — | FAIL | BUYPASS default: no restrictions |
| `T034-NON-FLEET-PROGRAM` | PROGRAM | `001` | 2 | `AMEX_CREDIT` | 001 | — | FAIL | Not a fleet program |
| `T034-WRONG-TABLE-ID` | STRUCTURE | `001` (table `035`) | 2 | MC | 001 | — | FAIL | Wrong Table ID |
| `T034-OVER-32` | STRUCTURE | 33 bytes | 2 | MC | 001 | — | FAIL | Exceeds `ans32` |
| `T034-EMPTY` | STRUCTURE | empty | 2 | MC | 001 | — | FAIL | No data |
| `T034-NON-PRINTABLE` | STRUCTURE | `001<TAB>002` | 2 | MC | 001 | — | FAIL | Non-`ans` byte |

## Format test data (`BR-PSPC-FORMAT`)

PASS: `001`, `999`, `000`. Code `000` is well-formed; its *classification* is Not Used.

FAIL: `1`, `0001`, `01A`, ` 001`, empty string, JSON `null`, JSON number `1`, Arabic-Indic digits `٠٠١`.

## Boundary and negative coverage

| Dimension | Covered by |
|---|---|
| First/last code (`000`, `999`) in both versions | Classification + format TDs |
| Every row boundary (413 v4.1 rows, 394 v3.7 rows) | 2,000 per-code classification TDs |
| Category transitions, including the v3.7 `700`-`799` reservation vs the v4.1 Cannabinoids table | Per-code classification TDs |
| Wrapped source rows (v4.1 `333`, `338`; v3.7 `179`) and the v4.1 `000` layout quirk | Source fidelity test |
| Assigned names containing "Reserved"/"Proprietary" (`568`, `569`, `920`-`924`) | Classification examples |
| Every version-diff relation, including reuse after deprecation | 1,000 diff TDs + 13 examples |
| Table 034 bound vs unbound version, unsupported version | Table 034 VERSION TDs |
| Table 034 length 32/33, empty, non-printable | Table 034 TDs |
| Opt-in `0`/`1`/absent vs `2`/`3` | Table 034 TDs |
| Program MC / Visa / non-fleet | Table 034 TDs |
| Assigned / unused / deprecated / three reservation types in Table 034 | Table 034 TDs |

## Not covered (by design)

- Real host responses, wire captures or converter round-trips for Table 034.
- Packing of multiple PSPC codes inside Table Data. This is network-defined and absent from ATL105.
- Which PSPC version a network actually applies. This needs an SME (SEG111-SME-012).
- Merchant Dynamic Card Table eligibility.
- AI Solution artifacts carrying Table 034 or PSPC claims. Compare them against these packages when they arrive.
