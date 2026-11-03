# PSPC Validation Strategy — ATL105 2026-4 Conexxus Payment System Product Codes

> How the Test Solution validates anything that depends on the Conexxus PSPC reference, including AI Solution artifacts.
> Memory: [README](README.md). Coverage: [BR Coverage](BR%20Coverage.md), [Test Coverage](Test%20Coverage.md).
> Programme-level strategy: [Core Auth Regression Test Validation Strategy](../Core-Auth-Regression-Test-Validation-Strategy.md).

## 1. Authority hierarchy

| Field / context | Primary authority | PSPC role |
|---|---|---|
| Element 77 Product Code (Segment 102 / 157) | ATL105 Appendix F (2026-3 = 2026-4, verified identical) | Informative only. Divergence is flagged, never substituted. |
| Appendix K Table ID 034 Host-based Purchase Restriction values | Conexxus PSPC (v3.7 and v4.1 supplied; version not bound by ATL105) | **Normative**: ATL105 Appendix K-27 delegates the value list to it |
| Table 034 structure, opt-in and program scope | ATL105 Appendix K-26 and Appendix I-46/47 | None |
| Segment 102 composition (fuel order, ten products, amount reconciliation) | ATL105 Section 12.3 | None |
| Merchant eligibility (Dynamic Card Table) | Merchant/processor configuration | None |

When sources disagree, keep both claims and return `REVIEW_REQUIRED`. Never pick one silently.

## 2. Validation rules

| Rule | Applies to | Check | Outcome on violation | Source |
|---|---|---|---|---|
| VR-PSPC-01 | Any PSPC code token | String of exactly three ASCII digits; leading zeroes preserved | FAIL | Appendix F-1, PSPC introduction |
| VR-PSPC-02 | PSPC classification | Code resolves to exactly one row of the cited version (413 v4.1 rows or 394 v3.7 rows); return category, assignment state and description verbatim | FAIL if the artifact states a different description or state for that version | PSPC v4.1 pages 7-18; v3.7 pages 4-15 |
| VR-PSPC-03 | Reservation states | Keep `RESERVED_CONEXXUS`, `RESERVED_PROPRIETARY` and `RESERVED_PREAUTHORIZATION` distinct from `ASSIGNED` and from each other | FAIL if a reserved code is presented as a defined product | PSPC |
| VR-PSPC-04 | Deprecated codes: v4.1 `011`-`015`, `041`; v3.7 `006`-`010`, `016`-`018`, `025`, `035`, `037`-`040`, `052`-`061`, `303` | Treat as deprecated in that version; do not present as current products | REVIEW_REQUIRED | PSPC v4.1 page 4; v3.7 pages 3-4 |
| VR-PSPC-05 | Element 77 | Judge against Appendix F. A PSPC description for a divergent code (142 codes for v4.1, 104 for v3.7) is not evidence. | REVIEW_REQUIRED (FAIL if the artifact replaces an Appendix F meaning with a PSPC meaning in a collision code) | Appendix F-1 |
| VR-PSPC-06 | Table 034 Table ID | Exactly `034` | FAIL | K-26 |
| VR-PSPC-07 | Table 034 Table Data | 1-32 printable `ans` bytes | FAIL | K-26 |
| VR-PSPC-08 | Table 034 presence | Request carried Table 049 Sub Table 18 = `2` or `3`; absent → BUYPASS defaults to no restrictions | FAIL if 034 is present without opt-in | K-26, I-46 |
| VR-PSPC-09 | Table 034 program | MasterCard Enhanced Fleet EMV → evaluate. Visa Fleet EMV 2.0 → REVIEW_REQUIRED (SEG111-SME-003). Any other program → FAIL. | as stated | K-26, I-46 |
| VR-PSPC-10 | Table 034 values | Assigned PSPC code → PASS; `000` → FAIL; deprecated or reserved → REVIEW_REQUIRED | as stated | K-27 + PSPC |
| VR-PSPC-11 | Table 034 packing | Parse only an explicit code list from a network contract or test fixture; never infer grouping from digits | REVIEW_REQUIRED | K-26 (silent) |
| VR-PSPC-12 | Version binding | Every PSPC-based verdict states the version used: `3.7`, `4.1`, or `UNBOUND`. Unbound Table 034 codes are judged against both supplied versions. | REVIEW_REQUIRED if an artifact cites an unsupplied PSPC version, or uses one version's meaning for a version-sensitive code without stating the version | SEG111-SME-012 |
| VR-PSPC-15 | Version-sensitive codes | The 62 codes whose state or meaning changed between v3.7 and v4.1 (see the [version diff](../../../test-output/test-json/pspc-version-diff-v3.7-v4.1.csv)) stay REVIEW_REQUIRED unless the version is bound | REVIEW_REQUIRED | PSPC v3.7 vs v4.1 |
| VR-PSPC-13 | MC availability | MasterCard Enhanced Fleet Table 034 evidence before June 2026 is certification-only | Note in report | I-47 |
| VR-PSPC-14 | Eligibility | Classification never implies transaction eligibility (`transactionEligibility: NOT_EVALUATED`) | FAIL if an artifact claims acceptance from classification alone | Appendix F-1, Section 13.2 Element 77 |

## 3. Validating AI Solution artifacts

Apply this order to every AI BR/TS/TC/TD that mentions product codes, PSPC, Element 77, Element 116 value `034`, Table 049 Sub Table 18, or Host-based Purchase Restriction:

1. **Route by field** using the authority hierarchy above. An AI artifact that validates Element 77 against PSPC is a defect (`AIF` feedback). The exception is when it explicitly says it is comparing against PSPC.
2. **Check the citation.** Expect ATL105 2026-4 Appendix K-26/K-27 (Table 034), Appendix I-46 (Sub Table 18), Appendix F-1 (Element 77), and the PSPC version plus page/row (v3.7 or v4.1). A claim without a source anchor is `REVIEW_REQUIRED`.
3. **Compare the labels** with the Test Solution oracle. Evaluate the AI's TD payload with `PaymentSystemProductCodeOracle` and compare outcomes and descriptions. Score these independently:
   - code classification
   - context gating (opt-in, program)
   - abstention (REVIEW_REQUIRED when the source is silent)

   A plausible rationale does not override a wrong label.
4. **Look for the known traps:**
   - The `052`-`061` meaning collision (ULSD in Appendix F and deprecated in v3.7, but B1/Additized/Renewable Diesel in PSPC v4.1).
   - Using the v4.1 meaning of a code that v4.1 reused after deprecation (`016`-`018`, `037`-`040`, `052`-`054`, `060`, `061`) without stating the version.
   - Treating v4.1-only codes (`156`, `157`, `231`, `333`-`338`, `658`, `659`, `700`, `915`-`924`, `971`, `972`) as valid under v3.7.
   - `016`-`018`, `037`-`040` and `151`-`153` meaning collisions.
   - `568`/`569`/`920`-`924` mistaken for reservations.
   - `071`-`075` treated as ordinary fuel.
   - `800`-`899` treated as Conexxus products. They are proprietary; BUYPASS uses `800`-`809` and `890`-`896`.
   - Visa Table 034 given a definite PASS/FAIL.
   - Table Data digits silently split into 3-byte codes.
5. **Record the outcome.**
   - Confirmed defects go to `AIF-*`.
   - Questions needing SME authority go to `SEG111-SME-*` / `SEG112-SME-*` in the [communication register](../../../registers/atl105-communication-register.json).
   - Then regenerate the views with `GenerateCommunicationRegisterViews`.

## 4. Independence and evidence rules

- **Separate implementations:** expected labels come from `GeneratePaymentSystemProductCodeCoverage`, which parses the source transcription. Verdicts come from `PaymentSystemProductCodeOracle`. The test fails if they disagree.
- **Line-anchored source:** every PSPC row carries its PDF page and its line in [extracted_text_pspc_v4.1.txt](../../specs/extracted_text_pspc_v4.1.txt) or [extracted_text_pspc_v3.7.txt](../../specs/extracted_text_pspc_v3.7.txt). Every ATL105 rule cites 2026-4 line numbers that the test re-reads.
- **Synthetic data only:** no PAN, track or real merchant data appears in fixtures.
- **Producer labels never count:** AI producer labels (OK/APPROVED/COMPLETE) never enter Test Solution approval or execution counts.

## 5. SME gates

| Gate | Blocks | Register |
|---|---|---|
| Confirm which PSPC version (v3.7, v4.1 or later) the networks apply to Table 034 | Promoting Table 034 value BRs from REVIEW_REQUIRED to approved; resolving the 62 version-sensitive codes | SEG111-SME-012 |
| Define the Table 034 packing format (per network) | Parsing raw Table Data into codes | SEG111-SME-012 |
| Visa Fleet EMV 2.0 Sub Table 18 scope | Definite Visa Table 034 verdicts | SEG111-SME-003 |
| Rule on Element 77 divergent meanings if AI/network evidence uses PSPC names | 104 (v3.7) / 142 (v4.1) crosswalk REVIEW_REQUIRED codes | — |
| Appendix F `895`/`896` overview vs detail conflict | FSA/HRA classification | Appendix F source conflict |

## 6. Maintenance when PSPC changes

1. Put the new PDF beside the current ones in `docs/specs/` and extract it to a new `extracted_text_pspc_v<version>.txt` in the same page-marked format.
2. Add a `Source` entry (version, date, text, PDF, oracle, chain and crosswalk paths, ID prefix) to `GeneratePaymentSystemProductCodeCoverage.SOURCES`. Set the expected release-note code count, and load the new oracle in `PaymentSystemProductCodeOracle`. Review any new reservation phrasing; the generator throws on unclassified "Undefined"/"Reserved for" text.
3. Regenerate the oracles, packages, crosswalks and the version diff. Review the crosswalk and diff CSVs, especially codes reused after deprecation. Update these documents and the README.
4. Run `PaymentSystemProductCodeOracleTest`, `AppendixFProductCodeOracleTest` and `CommunicationRegisterTest`.

## 7. Exit criteria for this reference

| Criterion | Status |
|---|---|
| Missing reference ingested, line-anchored and tested | Met (v3.7 and v4.1) |
| Every PSPC code `000`-`999` classified once per version with a TD | Met |
| Every ATL105 2026-4 PSPC dependency anchored and re-verified | Met |
| Table 034 structure, opt-in, program, values, packing and version BRs have TDs | Met |
| Element 77 crosswalk for all codes and both versions | Met |
| Version diff for all codes | Met |
| SME confirmation of version binding and packing | **Open** |
| Visa applicability resolved | **Open** |
| AI Solution Table 034 / PSPC artifacts compared | **Open** (none delivered yet) |
