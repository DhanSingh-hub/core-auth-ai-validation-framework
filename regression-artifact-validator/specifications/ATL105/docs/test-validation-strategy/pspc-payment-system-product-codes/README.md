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

## Sources

| Source | Version | Location |
|---|---|---|
| Conexxus Payment System Product Codes | 4.1, May 12, 2022 (ASC X9.104 Part 2 registry, Conexxus is Registration Authority) | [PDF](../../specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf), [line-anchored text](../../specs/extracted_text_pspc_v4.1.txt) |
| BUYPASS ATL105 Message Format Specifications | 2026-4, October 09, 2026 | [extracted_text_new.txt](../../specs/extracted_text_new.txt) |
| BUYPASS ATL105 Message Format Specifications | 2026-3 (Appendix F oracle baseline) | [extracted_text.txt](../../specs/extracted_text.txt) |

The PSPC text was extracted with `pypdf` into the same `--- PAGE N ---` layout as the ATL105 extracts. The SHA-256 of the
supplied PDF is `D2FB4F47D2C3AD3D77FBAA2FAF19FB15148799BDEC3B968E05D57ADDA15C1C19`.

## Where ATL105 2026-4 depends on PSPC

| ATL105 2026-4 location | Lines | What it says | Training effect |
|---|---|---|---|
| Appendix K-27, Table ID 034 note | 36323-36324 | Full list of supported values: refer to the Conexxus PSPC reference document | **Gap filled.** Table 034 product codes are resolved against PSPC v4.1. |
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
3. **Classify against PSPC v4.1.** Return the category and the assignment state:
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
6. **Emit a source-backed result.** Every result carries the code, the PSPC row, the version `4.1`, and the ATL105 anchor. `transactionEligibility` is always `NOT_EVALUATED`.

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
| [pspc-product-code-oracle.json](../../../../../src/main/resources/atl105/pspc-product-code-oracle.json) | 413 line-anchored PSPC rows, v4.1 release-note codes, ATL105 2026-4 anchors, Appendix F version check |
| [PaymentSystemProductCodeOracle](../../../../../src/main/java/com/coreauth/validator/canonical/PaymentSystemProductCodeOracle.java) | Evaluator. Modes: `CLASSIFICATION`, `ELEMENT_77_CROSSWALK`, `HOST_PURCHASE_RESTRICTION` |
| [GeneratePaymentSystemProductCodeCoverage](../../../../../src/main/java/com/coreauth/validator/coverage/GeneratePaymentSystemProductCodeCoverage.java) | Source parser and independent chain/crosswalk generator |
| [pspc-product-code-chain-package.json](../../../test-output/test-json/pspc-product-code-chain-package.json) | 420 BRs, 2,032 TS/TC/TD chains |
| [PaymentSystemProductCodeOracleTest](../../../../../src/test/java/com/coreauth/validator/PaymentSystemProductCodeOracleTest.java) | Source fidelity, anchors, examples, crosswalk, Table 034 and full chain replay |

## Open items (not inferred)

- **PSPC version binding:** ATL105 names no PSPC version. v4.1 is the supplied copy. An SME should confirm it is the version the networks use (SEG111-SME-012, `ANSWERED`).
- **Table 034 packing:** ATL105 does not define how PSPC codes are packed in the `ans32` Table Data (separators, count, or fixed 3-byte groups).
- **Visa scope (SEG111-SME-003):** Visa Fleet EMV 2.0 is named by Appendix K-26, while the Appendix I-46 Sub Table 18 note restricts the indicator to MasterCard Enhanced Fleet.
- **Merchant eligibility:** Dynamic Card Table and merchant configuration eligibility for any product code is not evaluated.

## Regeneration

Run from `regression-artifact-validator`:

```powershell
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage'
mvn '-Dtest=PaymentSystemProductCodeOracleTest' test
```
