# Conexxus Payment System Product Codes (PSPC) — ATL105 Reference Training

> Training memory for the Test Solution. This note records what ATL105 2026-4 delegates to the external
> Conexxus PSPC document, what that document says, and how the Test Solution must apply it.
> Companion documents: [BR Coverage](BR%20Coverage.md), [Test Coverage](Test%20Coverage.md),
> [Validation Strategy](Validation%20Strategy.md).

## Why this exists

The ATL105 specification was trained before this external reference was available. ATL105 2026-4 says
"For a full list of values supported in this field, please refer to the Conexxus PSPC reference document"
(Appendix K-27), but the document itself was missing. The gap was recorded as register item
[SEG111-SME-012](../../../registers/atl105-communication-register.json) (training issue `APPI-T049-Q010`).
The document has now been supplied and ingested. SEG111-SME-012 is `ANSWERED`.

**2026-10-27 update:** a second copy, PSPC **v3.7** (September 4, 2015), was supplied as `PaymentSystemProductCodes_3.pdf`. ATL105 2026-4 does not say which PSPC version applies, so both versions are now trained. The two versions disagree on 62 codes. Unless a version is stated, Table 034 is judged against both, and any code where their verdicts differ is `REVIEW_REQUIRED`.

## Sources

| Source | Version | Location |
|---|---|---|
| Conexxus Payment System Product Codes | 4.1, May 12, 2022 (ASC X9.104 Part 2 registry, Conexxus is Registration Authority) | [PDF](../../specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf), [line-anchored text](../../specs/extracted_text_pspc_v4.1.txt) |
| Conexxus Payment System Product Codes | 3.7, September 4, 2015 (the minor release before the 4.x reuse of deprecated codes) | [PDF](../../specs/Conexxus_Payment_System_Product_Codes_Version_3.7_2015-09-04.pdf), [line-anchored text](../../specs/extracted_text_pspc_v3.7.txt) |
| BUYPASS ATL105 Message Format Specifications | 2026-4, October 09, 2026 | [extracted_text_new.txt](../../specs/extracted_text_new.txt) |
| BUYPASS ATL105 Message Format Specifications | 2026-3 (Appendix F oracle baseline) | [extracted_text.txt](../../specs/extracted_text.txt) |

The PSPC text was extracted with `pypdf` into the same `--- PAGE N ---` layout as the ATL105 extracts. SHA-256 of the supplied PDFs:
- v4.1: `D2FB4F47D2C3AD3D77FBAA2FAF19FB15148799BDEC3B968E05D57ADDA15C1C19`
- v3.7: `3BFE56DAF181CD25661787CDD61BFE921A89DDE68B179EAB13CFEC32FB0F4B21`

## Where ATL105 2026-4 depends on PSPC

| ATL105 2026-4 location | Lines | What it says | Training effect |
|---|---|---|---|
| Appendix K-27, Table ID 034 note | 36323-36324 | Full list of supported values: refer to the Conexxus PSPC reference document | **Gap filled.** Table 034 product codes are resolved against PSPC v3.7 and v4.1. With no version stated, a code is accepted only when both versions agree. |
| Appendix K-26, Table ID 034 Host-based Purchase Restriction | 36291-36310 | Table ID n3 `034`; Table Data `ans32`, variable up to 32 bytes; received only if the merchant sent Purchase Restriction Method `2` or `3` and the network returned a value; Visa Fleet EMV 2.0 and MasterCard Enhanced Fleet EMV only | Structural and gating rules |
| Appendix I-46, Table 049 Sub Table ID 18 Purchase Restriction Method | 31849-31882 | `0` Neither, `1` Chip-Based Only, `2` Host-Based Only, `3` Both; mandatory for MC Enhanced Fleet (MCF/MGF) authorization, pre-auth and completion; if absent BUYPASS defaults to no restrictions supported | Request opt-in for Table 034 |
| Appendix I-47 availability notice | 31895-31898 | MasterCard Enhanced functionality expected in First Data production no earlier than June 2026 | Informational; certification only before then |
| Section 13.2 Element 118 description | 24752-24754 | When Element 116 is `034`, Host-based Purchase Restriction is included | Additional Information Indicator selector |
| Appendix F-1 "Valid Payment Systems Product Codes" | 28593-28612 | Appendix F codes are the valid entries for Element 77 (Segments 102/157) | **Element 77 stays governed by Appendix F**, not by PSPC |

Appendix F is byte-identical between 2026-3 and 2026-4 (521 normalized body lines), so the existing
[Appendix F oracle](../../../../../src/main/resources/atl105/appendix-f-product-code-oracle.json) remains valid for 2026-4.

## Model decision sequence

1. **Identify the field.**
   - Element 77 Product Code (Segments 102/157): use ATL105 Appendix F. Do not use PSPC.
   - Table 034 Host-based Purchase Restriction (Element 116 = `034`): use PSPC.
   - Any other field: neither oracle applies.
2. **Validate the code token.** A PSPC code is a string of exactly three ASCII digits. Preserve leading zeroes.
3. **Classify against the bound PSPC version.** Use `referenceVersion` `3.7` or `4.1`. If none is given, classification uses `4.1`, the latest supplied version. Return the category and the assignment state:
   - `ASSIGNED`
   - `DEPRECATED`
   - `RESERVED_CONEXXUS`
   - `RESERVED_PROPRIETARY`
   - `RESERVED_PREAUTHORIZATION`
   - `UNUSED`

   Do not merge "Reserved for Conexxus Future Use" with "Reserved for Proprietary Use". They have different owners. A product name that contains "Proprietary" (`568` Proprietary Prepaid Card Activate, `569` Reload) is an *assigned* code, not a reservation.
4. **Apply the Table 034 gates in order:**
   - Table ID is `034`.
   - Table Data is 1-32 printable bytes.
   - The request carried Sub Table 18 `2` or `3`.
   - The program is MasterCard Enhanced Fleet EMV or Visa Fleet EMV 2.0. Visa stays `REVIEW_REQUIRED` under [SEG111-SME-003](../../../registers/atl105-communication-register.json).
5. **Resolve value semantics.**
   - `ASSIGNED`: PASS.
   - `000`: FAIL.
   - `DEPRECATED` or any reserved state: REVIEW_REQUIRED. Deprecated codes may be reused by Conexxus, and a later PSPC version may assign reserved codes.
   - If no explicit code list is supplied: REVIEW_REQUIRED. ATL105 does not define how codes are packed in the `ans32` Table Data.
   - **Version binding:**
     - If the payload states `referenceVersion`, judge only against that version.
     - If not, judge against v3.7 and v4.1. When the per-version outcomes differ, return REVIEW_REQUIRED with `versionSensitive: true` (SEG111-SME-012). For example, `052` is deprecated in v3.7 but assigned in v4.1.
     - An unsupported version is FAIL.
6. **Emit a source-backed result.** Every result carries the code, the PSPC row(s), the version used (`3.7`, `4.1` or `UNBOUND`), and the ATL105 anchor. `transactionEligibility` is always `NOT_EVALUATED`.

## PSPC v4.1 knowledge

| Category | Range | Rows | Assigned | Deprecated | Reserved Conexxus | Reserved proprietary | Preauth only |
|---|---|---:|---:|---:|---:|---:|---:|
| Not Used | 000 | 1 | 0 | 0 | 0 | 0 | 0 |
| Dispensed Motor Fuels and Additives | 001-099 | 65 | 53 | 6 | 12 | 23 | 5 |
| Vehicle Products / Services | 100-149, 650-699 | 57 | 54 | 0 | 30 | 16 | 0 |
| Aviation Fuels | 150-174 | 11 | 9 | 0 | 10 | 6 | 0 |
| Aviation Products / Services | 175-224 | 42 | 40 | 0 | 2 | 8 | 0 |
| Marine Fuels | 225-249 | 10 | 8 | 0 | 11 | 6 | 0 |
| Marine Products / Services | 250-299 | 8 | 6 | 0 | 26 | 18 | 0 |
| Other Dispensed Fuels and Metered Products | 300-399 | 42 | 39 | 0 | 43 | 18 | 0 |
| Merchandise | 400-559, 591-599 | 90 | 59 | 0 | 73 | 37 | 0 |
| Prepaid and Bill Pay (Secondary Network) | 560-590 | 18 | 16 | 0 | 8 | 7 | 0 |
| Packaged Fuels | 600-624 | 9 | 7 | 0 | 7 | 11 | 0 |
| Reserved for Conexxus Future Use | 625-649, 720-799 | 2 | 0 | 0 | 105 | 0 | 0 |
| Cannabinoids | 700-719 | 3 | 1 | 0 | 17 | 2 | 0 |
| Reserved for Proprietary Use | 800-899 | 1 | 0 | 0 | 0 | 100 | 0 |
| Negative Transactions | 900-949 | 28 | 26 | 0 | 16 | 8 | 0 |
| Administrative | 950-999 | 26 | 24 | 0 | 18 | 8 | 0 |
| **Total** | **000-999** | **413** | **342** | **6** | **378** | **268** | **5** |

The Assigned through Preauth-only columns count individual codes, plus `000` Not Used. Every code from 000 to 999 is classified exactly once.

### Source rules to remember

- **v4.1 additions:**
  - Taxable diesel: `052` B1, `053` Additized Diesel #2, `054` Additized Diesel #3, `061` Renewable Diesel (Biodiesel 6%-20%).
  - Non-taxable off-road: `333` through `338`.
  - Codes reused here had been deprecated in v3.7.
- **Deprecated codes:** `011`-`015` and `041` are marked `<Deprecated>`. Conexxus may reuse deprecated values, so systems should migrate to alternate codes.
- **`032`/`033`:** only for existing implementations limited to `001`-`099`. The preferred codes are `311`-`320`, and `032`/`033` are likely to be deprecated.
- **`071`-`075`:** reserved for preauthorization use only.
- **Dyed fuel:** use the non-taxable code for the product. "Dyed" is not a PSPC term.
- **Auto and commercial diesel:** they share codes. Use the service code to distinguish delivery. Taxable and non-taxable fuel from one dispenser are separate line items.
- **Cannabinoids (`700`-`719`):** recommended only where the product is legal.
- **Discount ownership:**
  - `900`-`904`: merchant at the POS.
  - `915`-`919`: Local Discounts, merchant.
  - `920`-`924`: POS/Loyalty provider. "POS/Loyalty Reserved Discount N" rows are *assigned*, not reservations.
- **New codes:** may not be available in all environments. The merchant or vendor must confirm adoption across issuers and networks.

## PSPC v3.7 knowledge

v3.7 (September 4, 2015, 15 pages) was a backwards-compatible minor release. It said deprecated codes would be reused in a later major release, and v4.x did that.

| Category | Range | Rows | Assigned | Deprecated | Reserved Conexxus | Reserved proprietary |
|---|---|---:|---:|---:|---:|---:|
| Not Used | 000 | 1 | 0 | 0 | 0 | 0 |
| Dispensed Motor Fuels and Additives | 001-099 | 72 | 47 | 24 | 0 | 28 |
| Vehicle Products / Services | 100-149, 650-699 | 55 | 52 | 0 | 32 | 16 |
| Aviation Fuels | 150-174 | 9 | 7 | 0 | 12 | 6 |
| Aviation Products / Services | 175-224 | 42 | 40 | 0 | 2 | 8 |
| Marine Fuels | 225-249 | 9 | 7 | 0 | 12 | 6 |
| Marine Products / Services | 250-299 | 8 | 6 | 0 | 26 | 18 |
| Other Dispensed Fuels and Metered Products | 300-399 | 36 | 33 | 1 | 48 | 18 |
| Merchandise | 400-559, 591-599 | 90 | 59 | 0 | 73 | 37 |
| Prepaid and Bill Pay (Secondary Network) | 560-590 | 18 | 16 | 0 | 8 | 7 |
| Packaged Fuels | 600-624 | 9 | 7 | 0 | 7 | 11 |
| Reserved for Conexxus Future Use | 625-649, 700-799 | 2 | 0 | 0 | 125 | 0 |
| Reserved for Proprietary Use | 800-899 | 1 | 0 | 0 | 0 | 100 |
| Negative Transactions | 900-949 | 18 | 16 | 0 | 26 | 8 |
| Administrative | 950-999 | 24 | 22 | 0 | 20 | 8 |
| **Total** | **000-999** | **394** | **312** | **25** | **391** | **271** |

Every code from 000 to 999 is classified exactly once. v3.7 has no Cannabinoids table and no preauthorization-only range: `071`-`098` are all proprietary.

- **v3.7 additions** (release notes): `325`-`329` off-road non-taxable gasoline, `330` Recreational Fuel (90 octane), `331` Hydrogen H35, `332` Hydrogen H70.
- **Deprecated in v3.7** (since v3.5):
  - `006`-`010` methanol blends
  - `016`-`018` leaded fuels
  - `025` M-85
  - `035` Biodiesel Blend Off-Road
  - `037`-`040` methanol blends
  - `052`-`061` Ultra Low Sulfur diesel and biodiesel
  - `303` Bottled Propane
- **Source quirk:** row `179` (Aircraft Work Order) is wrapped in the PDF text layer. The description and the code are on separate lines; the parser joins them and records a `sourceNote`.

## PSPC v3.7 → v4.1 version diff

The per-code diff is in [pspc-version-diff-v3.7-v4.1.csv](../../../test-output/test-json/pspc-version-diff-v3.7-v4.1.csv).

| Relation | Codes | Version-sensitive | Codes affected |
|---|---:|---|---|
| `UNCHANGED` | 905 | No | Same state and meaning. A reserved range whose wording changed but whose reservation type didn't counts as unchanged. |
| `RENAMED` | 33 | No | Assigned in both with reworded names (e.g. `001` Unleaded Regular → Regular) |
| `NEWLY_ASSIGNED` | 24 | **Yes** | `156`, `157`, `231`, `333`-`338`, `658`, `659`, `700`, `915`-`924`, `971`, `972` |
| `REUSED_AFTER_DEPRECATION` | 12 | **Yes** | `016`-`018`, `037`-`040`, `052`-`054`, `060`, `061` (new products on old numbers) |
| `DEPRECATED_TO_RESERVED` | 13 | **Yes** | `006`-`010`, `025`, `035`, `055`-`059`, `303` |
| `NEWLY_DEPRECATED` | 6 | **Yes** | `011`-`015`, `041` (ethanol 5.7%/7.7% blends) |
| `STATE_CHANGED` | 7 | **Yes** | `071`-`075` (proprietary → preauthorization only), `718`, `719` (Conexxus → proprietary) |

62 codes are version-sensitive. Any Table 034 code in that set stays `REVIEW_REQUIRED` until the PSPC version is bound.

## Appendix F lineage

Comparing Appendix F against v3.7 shows that **Appendix F follows the numbering from before the v3.5 deprecations**:
- Every product that v3.7 deprecates is still an assigned product in Appendix F.
- 14 of those 25 deprecated v3.7 rows carry exactly the Appendix F description, for example `052` Ultra Low Sulfur #1 and `025` M-85.
- 10 more name the same product with different wording, for example `006` Unleaded Methanol 5.7%.
- Only `035` differs: Appendix F has "Other fuel (nontaxable)", v3.7 has Biodiesel Blend Off-Road.
- Against v3.7 there are no Appendix F-only assignments; against v4.1 there are 13.

The v3.7 crosswalk is in [pspc-v3.7-appendix-f-crosswalk.csv](../../../test-output/test-json/pspc-v3.7-appendix-f-crosswalk.csv).

| Relation (Element 77) | v3.7 codes | v4.1 codes |
|---|---:|---:|
| `DESCRIPTION_MATCH` | 235 | 221 |
| `NEITHER_ASSIGNED` | 645 | 621 |
| `APPENDIX_F_PROPRIETARY_ASSIGNMENT` | 17 | 17 |
| `BOTH_UNUSED` | 1 | 1 |
| `DESCRIPTION_DIFFERS` | 63 | 83 |
| `PSPC_ONLY_ASSIGNED` | 14 | 38 |
| `APPENDIX_F_ONLY_ASSIGNED` | 0 | 13 |
| `PSPC_DEPRECATED` | 25 | 6 |
| **REVIEW_REQUIRED total** (includes `895`/`896` conflict) | **104** | **142** |

Consequence: for Element 77 codes `052`-`054`, `060` and `061`, the Appendix F meaning (Ultra Low Sulfur) is the meaning v3.7 deprecated. The PSPC v4.1 meaning (B1, Additized and Renewable Diesel) is a later reuse. Never apply the v4.1 meaning to Element 77.

## Appendix F vs PSPC crosswalk (Element 77)

ATL105 Appendix F is an older BUYPASS/PCATS copy of the same numbering. The per-code crosswalk is in
[pspc-appendix-f-crosswalk.csv](../../../test-output/test-json/pspc-appendix-f-crosswalk.csv).

| Relation | Codes | Element 77 outcome |
|---|---:|---|
| `DESCRIPTION_MATCH` | 221 | PASS |
| `NEITHER_ASSIGNED` | 621 | PASS |
| `APPENDIX_F_PROPRIETARY_ASSIGNMENT` (BUYPASS debit surcharge `800`-`809` and FSA/HRA `890`-`896` inside PSPC proprietary `800`-`899`) | 17 | PASS, except `895`/`896` keep the Appendix F source conflict |
| `BOTH_UNUSED` (`000`) | 1 | PASS |
| `DESCRIPTION_DIFFERS` | 83 | REVIEW_REQUIRED |
| `PSPC_ONLY_ASSIGNED` | 38 | REVIEW_REQUIRED |
| `APPENDIX_F_ONLY_ASSIGNED` | 13 | REVIEW_REQUIRED |
| `PSPC_DEPRECATED` | 6 | REVIEW_REQUIRED |

These codes have a **material meaning collision**. An AI artifact that cites a PSPC description for Element 77 must be routed to review:

- `006`-`010`: methanol blends in Appendix F; reserved in PSPC.
- `016`-`018`: leaded fuels vs Green Gasoline.
- `025`: M-85 vs reserved.
- `033`: Refrigerator Fuel vs Off-Road.
- `035`: Other fuel (nontaxable) vs reserved.
- `037`-`040`: methanol blends vs ethanol 10%/15%.
- `052`-`054` and `060`/`061`: Ultra Low Sulfur products vs B1, Additized and Renewable Diesel, which PSPC v4.1 reused.
- `055`-`059`: Ultra Low Sulfur Biodiesel blends vs reserved.
- `151`-`153`: Aviation Fuel 1/2/3 vs Regular/Premium/JP8.
- `303`: Bottled propane vs reserved.

These codes are assigned only in PSPC v4.1. Appendix F lists them as undefined or reserved:

- `004`, `005`, `021`, `030`, `031`, `034`
- `156`, `157`, `231`
- `325`-`338`, `658`, `659`, `700`
- `915`-`924`, `971`, `972`

The remaining `DESCRIPTION_DIFFERS` rows are wording-only (plurals, "Non-Taxable", "Vehicle" vs "Auto"). They stay REVIEW_REQUIRED because the harness does not decide semantic equivalence.

## Executable memory

| Artifact | Purpose |
|---|---|
| [pspc-product-code-oracle.json](../../../../../src/main/resources/atl105/pspc-product-code-oracle.json) | v4.1: 413 line-anchored PSPC rows, release-note codes, ATL105 2026-4 anchors, Appendix F version check |
| [pspc-product-code-oracle-v3.7.json](../../../../../src/main/resources/atl105/pspc-product-code-oracle-v3.7.json) | v3.7: 394 line-anchored PSPC rows, release-note codes, the same ATL105 anchors |
| [PaymentSystemProductCodeOracle](../../../../../src/main/java/com/coreauth/validator/canonical/PaymentSystemProductCodeOracle.java) | Evaluator. Modes: `CLASSIFICATION`, `ELEMENT_77_CROSSWALK`, `VERSION_DIFF`, `HOST_PURCHASE_RESTRICTION`; optional `referenceVersion` (`3.7` or `4.1`) |
| [GeneratePaymentSystemProductCodeCoverage](../../../../../src/main/java/com/coreauth/validator/coverage/GeneratePaymentSystemProductCodeCoverage.java) | Version-aware source parser and independent chain, crosswalk and diff generator |
| [pspc-product-code-chain-package.json](../../../test-output/test-json/pspc-product-code-chain-package.json) | v4.1 package: 421 BRs, 2,043 TS/TC/TD chains (including 32 Table 034 cases) |
| [pspc-v3.7-product-code-chain-package.json](../../../test-output/test-json/pspc-v3.7-product-code-chain-package.json) | v3.7 package: 396 BRs, 3,000 TS/TC/TD chains (classification, Element 77 crosswalk, version diff) |
| [pspc-version-diff-v3.7-v4.1.csv](../../../test-output/test-json/pspc-version-diff-v3.7-v4.1.csv) | Per-code v3.7 → v4.1 relation |
| [PaymentSystemProductCodeOracleTest](../../../../../src/test/java/com/coreauth/validator/PaymentSystemProductCodeOracleTest.java) | 7 tests: source fidelity for both versions, anchors, examples, crosswalks, version diff, Table 034 and a full replay of 5,043 TDs |

## Open items (not inferred)

- **PSPC version binding:** ATL105 names no PSPC version. v3.7 and v4.1 are the supplied copies, and 62 codes differ between them. An SME should confirm which version the networks apply to Table 034 (SEG111-SME-012, `ANSWERED`).
- **Table 034 packing:** ATL105 does not define how PSPC codes are packed in the `ans32` Table Data (separators, count, or fixed 3-byte groups).
- **Visa scope (SEG111-SME-003):** Visa Fleet EMV 2.0 is named by Appendix K-26, while the Appendix I-46 Sub Table 18 note restricts the indicator to MasterCard Enhanced Fleet.
- **Merchant eligibility:** Dynamic Card Table and merchant configuration eligibility for any product code is not evaluated.

## Regeneration

Run from `regression-artifact-validator`:

```powershell
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage'
mvn '-Dtest=PaymentSystemProductCodeOracleTest' test
```
