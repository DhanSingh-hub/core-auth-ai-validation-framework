# Conexxus PSPC Training Record — ATL105 2026-4 External Reference

> Complete record of how the Test Solution was trained on the Conexxus Payment System Product Codes (PSPC):
> the documents ATL105 referred to but did not include during the original ATL105 training. Use this record to
> resume, audit or repeat the training.
>
> Programme strategy: [Core Auth Regression Test Validation Strategy](Core-Auth-Regression-Test-Validation-Strategy.md#test-solution-training-procedure-and-memory).
> Detailed memory and coverage: [PSPC README](pspc-payment-system-product-codes/README.md),
> [BR Coverage](pspc-payment-system-product-codes/BR%20Coverage.md),
> [Test Coverage](pspc-payment-system-product-codes/Test%20Coverage.md),
> [Validation Strategy](pspc-payment-system-product-codes/Validation%20Strategy.md).

## 1. Why this training was needed

The Test Solution was trained on BUYPASS ATL105 Message Format Specifications 2026-4 (`docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf`, extracted to `docs/specs/extracted_text_new.txt`). In Appendix K-27, ATL105 says:

> "For a full list of values supported in this field, please refer to the Conexxus PSPC reference document."

That document was not available, so the Table ID 034 value list could not be trained. The gap was recorded as register item `SEG111-SME-012` (training issue `APPI-T049-Q010`).

## 2. Documents supplied and ingested

| Supplied file | Identified as | Pages | SHA-256 | Stored as | Ingested |
|---|---|---:|---|---|---|
| `private___resources_PaymentSystemProductCodes.pdf` | Conexxus PSPC **v4.1**, May 12, 2022 | 18 | `D2FB4F47D2C3AD3D77FBAA2FAF19FB15148799BDEC3B968E05D57ADDA15C1C19` | [PDF](../specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf), [text](../specs/extracted_text_pspc_v4.1.txt) | 2026-10-27 |
| `PaymentSystemProductCodes_3.pdf` | Conexxus PSPC **v3.7**, September 4, 2015 | 15 | `3BFE56DAF181CD25661787CDD61BFE921A89DDE68B179EAB13CFEC32FB0F4B21` | [PDF](../specs/Conexxus_Payment_System_Product_Codes_Version_3.7_2015-09-04.pdf), [text](../specs/extracted_text_pspc_v3.7.txt) | 2026-10-27 |

Both are Conexxus publications. Conexxus is the ASC X9 Registration Authority for the X9.104 Part 2 product codes. Each PDF was extracted with `pypdf` into page-marked text (`--- PAGE N ---`) so that every trained row can cite its page and line.

## 3. Where ATL105 2026-4 needs PSPC

| ATL105 2026-4 anchor | Lines in `extracted_text_new.txt` | What ATL105 says | What was missing | How it is now filled |
|---|---|---|---|---|
| Appendix K-27, Table ID 034 note | 36323-36324 | Full value list: see the Conexxus PSPC document | The whole value list | Codes `000`-`999` classified in both PSPC versions |
| Appendix K-26, Table ID 034 Host-based Purchase Restriction | 36291-36310 | Table ID n3 `034`; Table Data `ans32` (1-32 bytes); only when the request opted in with Method `2`/`3` and the network returned a value; Visa Fleet EMV 2.0 and MC Enhanced Fleet EMV only | Meaning of the codes carried | Structure, opt-in and program gates + PSPC value semantics |
| Appendix I-46, Table 049 Sub Table 18 Purchase Restriction Method | 31849-31882 | `0` none, `1` chip, `2` host, `3` both; mandatory for MC Enhanced Fleet (MCF/MGF); if absent BUYPASS defaults to no restrictions | — | Opt-in gate for Table 034 |
| Appendix I-47 availability notice | 31895-31898 | MC Enhanced not in First Data production before June 2026 | — | Reported as a notice |
| Section 13.2 Element 118 | 24752-24754 | Element 116 = `034` carries Host-based Purchase Restriction | — | Selector linked to Table 034 BRs |
| Appendix F-1 "Valid Payment Systems Product Codes" | 28593-28612 | Appendix F codes are the valid Element 77 entries | How Appendix F relates to the industry list | Per-code crosswalk with both PSPC versions; Appendix F stays authoritative |

ATL105 does **not** say which PSPC version applies. Appendix F (Element 77) is identical in 2026-3 and 2026-4 (521 normalized lines), so the existing Appendix F oracle remains valid.

## 4. Training steps executed

### 4.1 PSPC v4.1 (first document)

| # | Step | Result |
|---|---|---|
| 1 | Identified the document and hashed it | Conexxus PSPC v4.1, 18 pages |
| 2 | Searched both ATL105 versions for `PSPC`, `Conexxus`, `PCATS`, `Payment System Product`, `Purchase Restriction` | 6 dependencies anchored (section 3) |
| 3 | Diffed Appendix F, Table 034 and Sub Table 18 between 2026-3 and 2026-4 | No content change; only the line numbers moved |
| 4 | Stored the PDF and the page-marked text under `docs/specs/` | 728 lines |
| 5 | Prototyped the parser in Python, then implemented it in Java with fail-fast guards | 413 rows; every code `000`-`999` exactly once |
| 6 | Classified assignment states | `ASSIGNED`, `DEPRECATED`, `RESERVED_CONEXXUS`, `RESERVED_PROPRIETARY`, `RESERVED_PREAUTHORIZATION`, `UNUSED` |
| 7 | Fixed two misclassifications caught by the guards | `920`-`924` "POS/Loyalty Reserved Discount" and `568`/`569` "Proprietary Prepaid Card" are **assigned** names, not reservations |
| 8 | Captured the release-note additions | `052`, `053`, `054`, `061`, `333`-`338` |
| 9 | Built the independent validator and the chain generator | Element 77 crosswalk: 142 REVIEW_REQUIRED of 1,000 |
| 10 | Marked the register item `ANSWERED`, updated training status, and linked the KB | SEG111-SME-012 ANSWERED |

### 4.2 PSPC v3.7 (second document)

| # | Step | Result |
|---|---|---|
| 1 | Identified `PaymentSystemProductCodes_3.pdf` | Conexxus PSPC **v3.7** (September 4, 2015), not a copy of v4.1 |
| 2 | Decided how to handle two versions | ATL105 binds no version, so both are trained. An unbound verdict must hold in both versions, otherwise REVIEW_REQUIRED. |
| 3 | Stored the PDF and the page-marked text | 596 lines |
| 4 | Made the parser version-aware | Per-version `Source` record (paths, ID prefix). Lone-code wrapped rows handled (v3.7 `179` Aircraft Work Order). Version-specific category: v3.7 reserves `700`-`799`, with no Cannabinoids table. Release-note codes parsed generically. |
| 5 | Parsed v3.7 | 394 rows; every code `000`-`999` exactly once; release-note additions `325`-`332` |
| 6 | Built the v3.7 → v4.1 version diff | 905 unchanged, 33 renamed, **62 version-sensitive** |
| 7 | Built the v3.7 crosswalk against Appendix F | 104 REVIEW_REQUIRED of 1,000; no Appendix F-only assignments |
| 8 | Extended the validator | `referenceVersion` (`3.7`/`4.1`); new `VERSION_DIFF` mode; unbound Table 034 judged against both versions with a `versionSensitive` flag |
| 9 | Extended the chain packages | v4.1 package 421 BRs / 2,043 TDs (32 Table 034 cases, new `BR-PSPC-T034-VERSION`); new v3.7 package 396 BRs / 3,000 TDs |
| 10 | Rewrote the test class | 7 tests: both versions' source fidelity, anchors, examples, crosswalks, diff, Table 034, replay of all 5,043 TDs |
| 11 | Updated memory | Register (SEG111-SME-012 resolution and evidence), `training-status.json`, PSPC README/BR/Test/Validation docs, this record, the strategy, the handbook and the KB notes |

## 5. Knowledge learned

### 5.1 Version comparison

| | v3.7 (2015) | v4.1 (2022) |
|---|---:|---:|
| Source rows | 394 | 413 |
| Assigned codes | 312 | 342 |
| Deprecated codes | 25 | 6 |
| Reserved Conexxus / proprietary / preauthorization codes | 391 / 271 / 0 | 378 / 268 / 5 |
| Release-note additions | `325`-`332` | `052`-`054`, `061`, `333`-`338` |
| Cannabinoids table | none (`700`-`799` reserved) | `700`-`719` (`700` CBD) |
| Element 77 crosswalk REVIEW_REQUIRED | 104 | 142 |

### 5.2 Version-sensitive codes (62)

| Relation | Codes | Meaning |
|---|---|---|
| `REUSED_AFTER_DEPRECATION` (12) | `016`-`018`, `037`-`040`, `052`-`054`, `060`, `061` | v3.7 deprecated product; v4.1 assigns a **different** product to the number |
| `NEWLY_ASSIGNED` (24) | `156`, `157`, `231`, `333`-`338`, `658`, `659`, `700`, `915`-`924`, `971`, `972` | Reserved in v3.7, assigned in v4.1 |
| `DEPRECATED_TO_RESERVED` (13) | `006`-`010`, `025`, `035`, `055`-`059`, `303` | Deprecated in v3.7, reserved in v4.1 |
| `NEWLY_DEPRECATED` (6) | `011`-`015`, `041` | Assigned in v3.7, deprecated in v4.1 |
| `STATE_CHANGED` (7) | `071`-`075`, `718`, `719` | Proprietary → preauthorization only; Conexxus → proprietary |

### 5.3 Appendix F lineage

Appendix F follows the PCATS numbering from **before** the PSPC v3.5 deprecations:
- Every product that v3.7 marks deprecated is still an assigned product in Appendix F.
- 14 match its description exactly (for example `052` Ultra Low Sulfur #1, `025` M-85).
- 10 name the same product in different words.
- Only `035` differs.

For Element 77, the codes v4.1 reused after deprecation therefore carry their **old** meaning. They must never be read with the v4.1 meaning.

### 5.4 Rules the Test Solution now applies

1. **Element 77:** judge against Appendix F only. PSPC is informative, and divergences are REVIEW_REQUIRED.
2. **Table 034 values:**
   - Assigned → PASS.
   - `000` → FAIL.
   - Deprecated or reserved → REVIEW_REQUIRED.
   - No explicit code list → REVIEW_REQUIRED (packing undefined).
3. **Table 034 version:**
   - A stated version is judged against that version.
   - Unbound means judging against v3.7 and v4.1. If they disagree, REVIEW_REQUIRED with `versionSensitive: true`.
   - An unsupported version is FAIL.
4. **Table 034 gates:** Table ID `034`; Table Data 1-32 printable bytes; Sub Table 18 = `2`/`3`; MC Enhanced Fleet evaluated, Visa Fleet REVIEW_REQUIRED (SEG111-SME-003), others FAIL.
5. **Classification never implies transaction eligibility** (`transactionEligibility: NOT_EVALUATED`).
6. **AI artifacts** that cite PSPC are judged with rules VR-PSPC-01 to VR-PSPC-15 ([Validation Strategy](pspc-payment-system-product-codes/Validation%20Strategy.md)).

## 6. Memory artifacts

| Artifact | Path |
|---|---|
| Source oracles | [v4.1](../../../../src/main/resources/atl105/pspc-product-code-oracle.json), [v3.7](../../../../src/main/resources/atl105/pspc-product-code-oracle-v3.7.json) |
| Validator | [PaymentSystemProductCodeOracle.java](../../../../src/main/java/com/coreauth/validator/canonical/PaymentSystemProductCodeOracle.java) |
| Generator | [GeneratePaymentSystemProductCodeCoverage.java](../../../../src/main/java/com/coreauth/validator/coverage/GeneratePaymentSystemProductCodeCoverage.java) |
| Test | [PaymentSystemProductCodeOracleTest.java](../../../../src/test/java/com/coreauth/validator/PaymentSystemProductCodeOracleTest.java) |
| Chain packages | [v4.1](../../test-output/test-json/pspc-product-code-chain-package.json), [v3.7](../../test-output/test-json/pspc-v3.7-product-code-chain-package.json) |
| Crosswalks and diff | [v4.1 vs Appendix F](../../test-output/test-json/pspc-appendix-f-crosswalk.csv), [v3.7 vs Appendix F](../../test-output/test-json/pspc-v3.7-appendix-f-crosswalk.csv), [v3.7 → v4.1](../../test-output/test-json/pspc-version-diff-v3.7-v4.1.csv) |
| Status | [training-status.json](../../training-status.json) → `referenceTraining.conexxusPaymentSystemProductCodes` |
| Register | [atl105-communication-register.json](../../registers/atl105-communication-register.json): `SEG111-SME-012` (ANSWERED), `SEG111-SME-003` (OPEN) |

## 7. Coverage result

| Measure | Value |
|---|---:|
| BRs (v4.1 + v3.7 packages) | 421 + 396 = **817** |
| TS / TC / TD | **5,043** each; every BR has a chain |
| Replayed TD outcomes | 4,704 PASS, 320 REVIEW_REQUIRED, 19 FAIL; all match the expected labels |
| PSPC codes classified | 1,000 per version |
| Version-sensitive codes | 62 |
| SME-approved BRs | 0; all remain `REVIEW_REQUIRED` |

Validation run (2026-10-27): `PaymentSystemProductCodeOracleTest` 7/7, plus `AppendixFProductCodeOracleTest`, `AppendixKTableCatalogTest`, `CommunicationRegisterTest`, `RepositoryStructureTest` and `TrainingStatusConsistencyTest`, all green.

## 8. Open items

| Item | Owner | Register |
|---|---|---|
| Which PSPC version the networks apply to Table 034. This would resolve the 62 version-sensitive codes. | SME/TBA | SEG111-SME-012 |
| Packing of PSPC codes inside the `ans32` Table Data | SME/TBA, network contracts | SEG111-SME-012 |
| Visa Fleet EMV 2.0 applicability | SME/TBA | SEG111-SME-003 |
| Element 77 ruling if PSPC names are ever accepted (104 / 142 divergent codes) | BUYPASS SME | — |
| Merchant Dynamic Card Table eligibility | Merchant configuration | — |
| Compare AI Solution PSPC / Table 034 artifacts | Test Team | on delivery |

## 9. Reproduce or retrain

From `regression-artifact-validator`:

```powershell
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage'
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.register.GenerateCommunicationRegisterViews'
mvn '-Dtest=PaymentSystemProductCodeOracleTest,AppendixFProductCodeOracleTest,AppendixKTableCatalogTest,CommunicationRegisterTest' test
```

To add another PSPC version, follow section 6 of the [PSPC Validation Strategy](pspc-payment-system-product-codes/Validation%20Strategy.md#6-maintenance-when-pspc-changes).
