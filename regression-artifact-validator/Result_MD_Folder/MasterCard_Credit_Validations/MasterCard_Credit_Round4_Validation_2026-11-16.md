# ATL105 Test Data Validation – MasterCard Credit – Round 4 (2026-11-16, "plan v10" delivery)

| Item | Value |
|---|---|
| For | AI Dev team – please review each item and record your response in §3 / §4 |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **Release 2026-4** (October 09, 2026). Page numbers are **PDF pages** of the 2026-4 PDF (`specifications/ATL105/docs/specs`). |
| Delivery reviewed | `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output\mastercard_credit_new - Copy (2)` – build `buypass-atl105-mastercard-credit-generated-cdf683cfd7`, run `20261113T174819423244Z`, built 2026-11-13 17:48 UTC, state `CANDIDATE`, `tc_generator: generated` ("plan v10"), catalog `generated-2026-11-13.2`, `merchant_provenance: placeholder` |
| Baseline | Round 3 revalidation of the `5-10` delivery – [Consolidated_Revalidation_AllProducts_2026-11-06_Round3.md](../Consolidated_Revalidation_AllProducts_2026-11-06_Round3.md) §2, and the defect register in [Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md](../Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md) (CD-01 – CD-20, RV-xx) |
| Method | (1) Every test-data file of all three packages parsed and checked catalog-wide against the ATL105 layouts / element rules (scripted; full list in [MC_Credit_Round4_Findings.csv](./MC_Credit_Round4_Findings.csv)). (2) Field-by-field `TestDataSpecFieldValidationRunner` (Java, trained Appendix R/T/I/J oracles) on a 35-TC representative sample → [TC_Reports/README.md](./TC_Reports/README.md). (3) Manual inspection of every flagged pattern against the raw JSON and the spec text. |
| Run month for expiry checks | `2611` |

**Result:** the core financial message layer is much better than Round 3 – 11 of the 20 register defects (CD-01 – CD-06, CD-09 – CD-11, CD-14, CD-16) and Round-3 NEW-01 are now fixed. The test data is still **not correct overall**: CD-08 is unchanged and CD-07, CD-13 and CD-18 are only partly fixed. Package traceability CD-19 has regressed and CD-20 is still present. This round also found **15 new defect types**. Most of them come from the new generator's synthetic-fill logic, boundary tests and negative tests, and host-operations messages.

---

## 1. What was delivered

This is a new generator (`--tc-model generated`, "plan v10"), not a refresh of the Round-3 catalog. TC IDs (TC-012108 – TC-024377), names, folder layout and scope all changed, so the prior defects were re-checked **by rule**, not by matching TC IDs.

| Package | Test cases | TCs with test data | TD files (legs) | Message types in TD | TC status | Producer verification |
|---|---:|---:|---:|---|---|---|
| `./` BR1 MasterCard Credit (network) | 1,861 | 1,710 (137 device-only, 14 blocked) | 2,147 | 1,530 Financial Transaction Request, 550 EMV Financial Transaction Request, 67 Totals Request | ready 607 · needs_client_input 646 · requires_review 364 · pending_sme 230 · blocked 14 | PASS 1,724 · N/A 137 |
| `data_element_validation/` | 1,029 | 1,015 | 1,015 | 476 financial (391 EMV) + 539 host/admin (Totals, Totals+Proprietary, E-mail, Proprietary Data Load, CA Public Key, TransArmor, Moneris, Table/Phone/Date-Time/Software Load, Comms Test) | ready 790 · requires_review 165 · pending_sme 34 · blocked 35 · needs_client_input 5 | PASS 1,008 · **REJECTED_FINAL 21** |
| `terminal_and_host_operations/` | 47 | 41 | 42 | 41 host/admin + 1 EMV | needs_client_input 26 · ready 20 · requires_review 1 | PASS 41 · N/A 6 |
| **Total** | **2,937** | **2,766** | **3,204** | | | |

Round 3 (`5-10`) had 630 TCs / 1,335 files for MasterCard Credit. All 3,204 TD files parse as JSON, all 2,937 `test_cases.jsonl` lines parse (RV-21 resolved), and when matched by file name every TD file is referenced by a TC (0 orphan, 0 missing). Path resolution is broken, however (see CD-19).

### Result categories (same as previous rounds)

| Result | Meaning |
|---|---|
| **DEFECT** | Breaks an ATL105 rule, or the data cannot prove what the test case claims. Must be fixed. |
| **REVIEW** | May be acceptable; spec is silent/ambiguous or the item is a convention question. Needs SME/BUYPASS confirmation. |
| **NEG** (CSV only) | A violation that is the *intended* mutation of a negative test – informational, not a defect. |

---

## 2. Summary

| Area | Result |
|---|---|
| Prior register (CD-01 – CD-20, NEW-01) | **12 fixed** · **4 still open / partly fixed** (CD-07, CD-08, CD-13, CD-18) · **1 regressed** (CD-19) · **1 still present in a new form** (CD-20) · **3 not verifiable – scenario no longer generated** (CD-12 expired card, CD-15 cash back, CD-17 RFID terminal) |
| New defects (R4-01 – R4-15) | 15 new defect types: 12 DEFECT and 3 Review-level (R4-09, R4-13, R4-15). |
| Network package, positive/boundary TCs | 109 of 1,710 TCs with test data have at least one DEFECT finding (all are listed in the CSV). The remaining financial legs pass segment-length, element-format, App. J/I/Q, chip-data TLV and follow-on link checks. |
| Data element package | Positive boundary tests use invalid "filler" values (R4-05). Negative tests carry collateral violations (R4-06). EMV/track legs carry expiry-only MMYY E12 (R4-07). Host messages break E102/E48/E63 (R4-08). |
| Package / traceability | **0 of 2,766** TC → TD references resolve as written (`qe_shaped_test_data/…`). The manifest lists 3,928 archived v8 files that were not delivered. The delivery contract's self-check reports 0 test cases. |

**Systemic root causes (fix once, removes most findings):**
1. **Synthetic max-length filling of optional content.** This is the CD-02 root cause again. It causes R4-01, R4-03, R4-04, R4-05 and R4-09.
2. **Data-element carrier template.** The template uses MMYY expiry, filler `A`, PAN `TOTALS REQUEST` and drops sibling fields. It causes R4-05, R4-06 and R4-07.
3. **Path writer for TC/trace references.** All references use `qe_shaped_test_data/` and only leg 1 is traced. This causes CD-19.
4. **Host-message identity rules.** The download requests, CA Public Key File Load and TransArmor load use the wrong Terminal Identifier width and Device Type (R4-08).

---

## 3. Prior defect register – Round 4 status

Counts are **network package** unless stated. "Legs" = TD files.

### ✅ Fixed in Round 4

| ID | Defect (Round 1 wording) | Round-4 evidence |
|---|---|---|
| CD-01 | EMV Card Sequence Number `01` instead of `001` | **550/550** EMV legs send `001` (3 digits) and match Tag 5F34 (E188 p.477). |
| CD-02 | EMV Additional Information = 984 random characters | **549/550** EMV legs: indicator `001`, length `006`, `EMVYES`. The other leg is the negative test TC-024346. ⚠ **Regressed in the data-element package** – see R4-05 (TC-020506). |
| CD-03 | Table 030 missing on EMV / COF | Table 030 on **550/550** EMV legs and **245/248** COF legs (App. I p.568). The 3 exceptions are negative "segment omitted" TCs (R4-06). Values decode correctly per App. Q (e.g. `0100000001`, MOTO `0102100001`, unattended `1000000001`). |
| CD-04 | COF Tables 032 / 049 / 056 missing (056 still blank in Round 3) | **245/248** COF legs carry Table 032 = `08001C`, Table 049 = `021C` and Table 056 = `0904C101`. All formats match App. I p.575, p.589 and p.609. The 7 initial-COF (`08001I`) legs correctly use their real entry mode, not `10`. |
| CD-05 | Chip fallback `79` sent with swiped Track 2 | All **211** `791` legs carry a 4-character expiry. None carries track data. |
| CD-06 | Unmatched TOR carries full track data | All 15 standalone TORs and 115 follow-on TORs send YYMM only (10.1.8.2 p.94–95). No TOR carries Seg 130. |
| CD-09 | Seg 102 length `033` vs `029` | **410** Seg 102 instances declare `029` = 12.3 arithmetic (p.229). Product amount = Seg 100 total. |
| CD-10 | Totals Card Label 2 bytes | **47/47** category blocks send `"CC  "` (E13 p.372). |
| CD-11 | Totals category with count `00000` | **47/47** counts are `00001`. Purchase→Reversal and Purchase→TOR flows correctly omit the category. See R4-13 for the bucket question. |
| CD-14 | Offline-PIN test with signature CVM | EMV offline-PIN legs send `9F34 = 440302` (11 legs). |
| CD-16 | "Track 1" test carries Track 2 | TC-022594 / TC-022730 send `B5113711878437156` + `TEST/CARDHOLDER^270410167649565`. This is the correct Track 1 split (E2 p.365, E12 p.371). |
| NEW-01 (R3) | Void of Merchandise Return without Approval Number | **56/56** void-of-return legs carry `<ApprovalNumber from step 1 response>`. |
| RV-12 | Completion legs without Seg 130 | Resolved. Captures after an EMV authorization are now EMV requests with Seg 130. |
| RV-21 | Malformed `test_cases.jsonl` lines | Resolved. 0 malformed lines in all three catalogs. |

### ⚠️ Still open / partly fixed

| ID | Status | Round-4 evidence |
|---|---|---|
| **CD-07** | **PARTLY FIXED** | The impossible 8-digit provisional amount is gone. Step-2 amounts are now runtime tokens (`<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`). However, the declared **Seg 100 length on all 11 split-tender step-2 legs** (`086` / `075`) still assumes a 1-digit amount. The fixed part is 85 / 74 bytes, while the real remainder of a $15.00 sale is 1–4 digits. On the 2 petroleum split legs the Seg 102 length (`022`) is also precomputed from tokens (TC-024078, TC-024188). DELIVERY_CONTRACT does not tell the harness to recompute segment lengths after token substitution (E84 p.406; 12.1 p.223). |
| **CD-08** | **STILL NOT FIXED** | All **5/5** EMV split-tender step-2 legs (TC-023510, TC-023556, TC-024078, TC-024188, TC-024304) send `9F02 = 000000000000`, while `NonfuelAmount` is the runtime remaining balance. Card B's chip amount must equal the step-2 amount (E190 p.478; SEG130-R-009). The value changed from `…1500`/`…750` to all zeros, which is still wrong. The Java runner flags the same issue (SPLIT-TENDER-9F02, SEG130-R-009). |
| **CD-13** | **PARTLY FIXED** | Online-PIN EMV legs now send `9F34 = 420300` (online PIN). 7 of the 8 online-PIN TCs are `needs_client_input` and 1 is `pending_sme`, with an `awaiting_inputs` note. **E33 is still an empty string** in all 8 TDs. DELIVERY_CONTRACT requires the explicit `<AWAITING:…>` placeholder and says "never an empty field". 6 of the 8 TCs are non-EMV credit (magstripe, contactless magstripe, keyed, fallback, COF, mobile); see R4-11. |
| **CD-18** | **PARTLY FIXED** | The 8 `… with avs = true` TCs now send Table 003 as `<AWAITING:…>`: 7 are `needs_client_input` and 1 is `pending_sme`, which is acceptable under the contract. **12 new `… (AVS or NVS or Both Information returned) → approved` TCs (9 `ready`) send no Table 003 at all.** The AVS result they assert cannot be returned: 10.1.3 p.86 says "the device sends a transaction request … containing the AVS information in the Variable Information Data Segment". Examples: TC-023516, TC-023562, TC-023633, TC-023786. |
| **CD-19** | **REGRESSED** (fixed in Rounds 2–3) | Every `test_data_file` / `test_data_leg_files` entry in `test_cases.jsonl`, `test_cases.csv` and every `traceability_matrix_full.json` path is `qe_shaped_test_data/TD-TC-…json`. That folder is not delivered; files are in `test_data/`. **0/1,710 (network), 0/1,015 (data element) and 0/41 (operations)** references resolve as written. The matrix still states `"file_exists": true` on all 1,710 network rows. Only leg 1 of each flow is traced: **437 network leg files are never traced** (TOR 115, cancellation 93, reversal 68, totals 67, void-return 56, MOTO reversal 15, capture 12, split-tender 11), plus 1 in operations. |
| **CD-20** | **STILL PRESENT (new form)** | `MANIFEST.json` reports `single_build: false`, `test_cases: 0`, `test_data_references: 0` and `test_data_files: 5971`. It lists 6,077 files, of which **3,928 are `archive/v8_2026-11-13/…`** and are not in the delivery. README links to `archive/v8_2026-11-13/` and `../docs/ACCEPTANCE_CONTEXT_DESIGN.md`, neither of which is delivered. `DELIVERY_CONTRACT.md` is byte-identical in all three packages, and its "Reference check" reports *Test cases: 0, Distinct test-data references: 0, Trace entries: 0*. The self-check never ran against the delivered content. |

### ⛔ Not verifiable – scenario no longer generated

| ID | Observation |
|---|---|
| CD-12 | No expired-card test exists. The only "expir" TC is the device check TC-023615. Expired-card host behaviour is no longer covered (10.14.2.1 p.160; E12 p.372). |
| CD-15 | No cash-back scenario exists. On the EMV legs among the 8 decision-table TCs that do populate Cash Amount (R4-04), the generator now derives `9C = 09`, `9F02 = Seg 100 total` and `9F03 = cash` correctly (e.g. TD-TC-020989: `9F02 = 000000001503`, `9F03 = 000000000001`). |
| CD-17 | No RFID / contactless-MSR-only terminal scenario exists. No Table 005 uses capability `8`, and Table 031 is always `12` or `00`. |

### Review items carried over

| ID | Round-4 status |
|---|---|
| RV-01 | Unchanged convention: keyed/COF original is MMYY and the listed credit follow-on is YYMM (e.g. TC-021571 `1228` → `2812`, TC-022444 `0329` → `2903`). The Java runner's `FOLLOW-ON-SAME-EXPIRY` / `E12-KEYED-EXPIRY-MMYY` FAILs on these 2 sample TCs use the pre-RV-01 rule and are **not** defects. |
| RV-03 | Still open. E118 = `EMVYES`, E192 = `006`. The Java Appendix T oracle expects embedded Table-ID framing and fails 22 of the 35 sample TCs on that rule; for 20 of them it is the only FAIL. |
| RV-04 / RV-05 | Unchanged: E189 `147` includes the 2-byte BCD prefix `0145`, and the CA key checksum `2012091700000100000102171` is still the QE sample. |
| RV-06 | Unchanged: TVR `95 = 8000048000` (byte 3 "online PIN entered") on **536** EMV legs without a PIN. |
| RV-08 | Unchanged: placeholder TID `JX72948216050257001` on 1,516 legs, and `merchant_provenance: placeholder`. |
| RV-11 | Changed: Seg 105 now declares `107` with a category block (was `108`) and `090` without. This is consistent with no FS after field 17. 12.6 is silent on this, while 12.17 (Seg 119) explicitly adds a trailing FS. SME to confirm. |
| RV-13 | Unchanged: **200/200** contactless EMV legs carry no contactless-specific tags (9F6E / 9F6D / 9F7C / 9F66). |

---

## 4. New defects found in Round 4

Severity follows the previous rounds: **High** = message invalid, or the test cannot prove its objective. **Medium** = the message is wrong but the test intent survives, or the issue is limited. **Low** = cosmetic.

| ID | Defect | Element / rule | Scope (TCs / legs) | Severity |
|---|---|---|---|---|
| [R4-01](#r4-01) | Optional segments filled with random, max-length synthetic data | Seg 104, 108, 123, 145 | 37 positive TCs / 45 findings (+10 negative TCs) – network | High |
| [R4-02](#r4-02) | Segments not permitted / not applicable for MasterCard Credit | §11.1.1, §11.8.1, 12.2 | 36 legs (108 ×26, 145 ×9, 101 ×1) + 4 SKU legs (dev) | High |
| [R4-03](#r4-03) | Encrypted PIN Block Data sent as the literal `PIN` or `16` | E33 p.382 | 28 network legs + dev carriers | High |
| [R4-04](#r4-04) | "Authorization Only with optional Seg 100" sends a random Approval Number and Local Date/Time on an original authorization | E5 p.368, E49 p.388 | 8 TCs (7 `ready`) | High |
| [R4-05](#r4-05) | Boundary tests that expect acceptance use invalid values | E44, E87, E58, E86, E105, E191, Seg 102 | ≥ 34 positive boundary/profile TCs (3 network + ≥ 31 data element) | High |
| [R4-06](#r4-06) | Negative tests carry collateral violations (more than one rule broken) | various | 29 data element + 9 network negative TCs | Medium |
| [R4-07](#r4-07) | Chip / track-read legs carry an expiry-only MMYY E12 | E2/E12 p.364–372; App. J p.638 | 438 dev legs + 41 network TCs (TC-024312 – TC-024360) | Medium |
| [R4-08](#r4-08) | Host-operations requests break Terminal Identifier / segment-count rules | E102 p.417, E48 p.388, Seg 132 p.274, E63 | operations 20 legs; dev 244 legs | High |
| [R4-09](#r4-09) | Device/host-assigned values filled with random strings | E39/E43/E96, Seg 118/119, Moneris | 1,136 field occurrences | Medium (Review) |
| [R4-10](#r4-10) | Delivery-contract violations for client inputs | DELIVERY_CONTRACT; E112 | 8 + 46 TCs | Medium |
| [R4-11](#r4-11) | Generated combinations that cannot occur | 10.14.3 p.162; App. I p.586 | 24 + 8 + 6 + 10 | Medium |
| [R4-12](#r4-12) | "Segment omitted" negatives claim requirements that do not apply | p.561, p.267, p.15/p.23 (TOC) | 9 of 12 network TCs (N-C7) | Medium |
| [R4-13](#r4-13) | Totals bucket for Auth Only / Return / Balance Inquiry flows is `CC` with count 1, amount 0 | E13 p.372–373; E15/E16 | 37 legs | Review |
| [R4-14](#r4-14) | Boundary TC names say "→ declined" but objective and expected code say accepted; "24-byte Account Number" test sends 16 bytes | naming / E2 | 3 network TCs | Medium |
| [R4-15](#r4-15) | "Sending Table NNN" tests include tables scoped to other programs | App. I p.552–553 | 8–11 TCs per table | Review |

<a id="r4-01"></a>
### R4-01 – Optional segments filled with random, max-length synthetic data (same root cause as CD-02)

| | |
|---|---|
| Where | `Loyalty Card Data Segment` (108), `Purchase Card Data Segment` (104), `NFC Payment Tokenization Data Segment` (123), `Enhanced Fleet Request` (145) – decision-table / "optional segment present → approved" TCs |
| Generated | **108:** `CouponAmount 91577796` ($915,777.96 coupon on a $15.00 sale), `PointstoRedeem 269486`, `CouponID 9427764446198811108`, and `PaymentTenderType "AX"` (American Express) or `"VS"` (Visa) on a MasterCard transaction (E148 p.450: `MC` = MasterCard) – TC-020977, TC-020980, TC-020972. **104:** `PCTaxAmount 4653199`, `PCFreightAmount 0662246`, `PCDutyAmount 4555977` on a $15.00 sale; `ShiptoCountryCode "407"` (not a valid country code); `ShiptoPostalCode "6LNQYAPB8T"`; `PurchaseCode "7UDLSFXCXQ2C5OU1"`; segment length `086` = the 86-byte maximum, i.e. every field filled to its max length – TC-020990, TC-021076. **145:** `EnhancedFleetData` = 999 random A–Z/0–9 characters – TC-020992, TC-021078, TC-023424. **123:** random `CAVVRevisedFormat`, `TokenRequestorID`, AMEX `SafeKeyData`, `TAVVCryptogram`, sent on magnetic-stripe / keyed / fallback / COF transactions – TC-021165, TC-021338, TC-024327 |
| Expected | Values a real terminal would send for the transaction: amounts consistent with the $15.00 sale, a valid ISO country code (E89 "any valid postal country code"), a MasterCard tender, and Enhanced-Fleet prompt TLVs (12.31). Otherwise, mark the field `<AWAITING:…>` and the TC `needs_client_input`. |
| ATL105 rule | 12.5 p.233–234 (E72/E73/E74 amounts, E89 country, E90/E88 postal); 12.7 p.238–239; 12.19 p.267 ("required on all initial and recurring transactions **involving tokenized data**"); 12.31 (Enhanced Fleet TLV prompt data) |
| Why | `format_synthesis`, the same mechanism that produced CD-02's 984 random characters. These TCs are `ready` and expect *approved*, but the content contradicts the transaction. |
| Scope | 45 DEFECT findings on 37 positive TCs, plus 18 on 10 negative TCs. See CSV check `SEG-OPT-RAND`. |

<a id="r4-02"></a>
### R4-02 – Segments not permitted / not applicable for MasterCard Credit

| | |
|---|---|
| Generated | Loyalty Card Data Segment 108 inside **Financial** and **EMV Financial Transaction Requests** (26 legs: 19 FTR + 7 EMV). Enhanced Fleet Request 145 on MasterCard Credit (9 legs). Fleet Data Segment 101 on MasterCard Credit (1 leg, TC-024347). Data element package: SKU Data Segment 114 in (EMV) Financial Transaction Requests (4 legs). |
| ATL105 rule | §11.1.1 (p.166–170) and §11.8.1 (p.206–207) list the segments allowed in Data Section 3: 101, 102, 103, 104, 111, 123, (130), 135, 143, 145, 146, 151, 152, 153. **108 and 114 are not in either list.** Seg 108 belongs to §11.2 Loyalty Card Transactions (p.174). 12.2 p.226: "The Fleet Data Segment is included in all **fleet card** transaction requests". Seg 145 is an enhanced-fleet offering (12.31). The Java runner fails TC-020977 on `11.8.1-SECTION3`. |
| Fix | Remove 108 and 114 from financial requests. Exclude fleet segments from non-fleet card types (the brand-applicability resolver already excludes WEX/Voyager rules; extend it to segment applicability). |

<a id="r4-03"></a>
### R4-03 – Encrypted PIN Block Data sent as the literal `PIN` or `16`

| | |
|---|---|
| Generated | `"EncryptedPINBlockData": "PIN"` – 10 positive decision-table TCs "… Authorization Only with Encrypted PIN Block Data = PIN → approved" (7 `ready`), e.g. TC-020974; also 10 negative variants. `"EncryptedPINBlockData": "16"` – the 8 "optional Seg 100" TCs (R4-04) and the data element carriers TC-020240/020241, TC-020557, TC-020613. The chip data on the same legs still has `9F34 = 5E0302` (signature). |
| Expected | DUKPT KSN (16–20) + 16-byte PIN block = 32–36 characters, or `<AWAITING:EncryptedPINBlockData>` until the client supplies a PIN/key (CD-13). The CVM must match. |
| ATL105 rule | E33 p.382 ("16- to 20-byte KSN plus a 16-byte PIN block"); 12.1 F8 p.224 (len 36). Java: `SEG100-E33-FORMAT` FAIL on TC-020974 and TC-020989. |
| Why | The rule text "PIN" (meaning *a PIN is entered*) was used as the field value. |

<a id="r4-04"></a>
### R4-04 – "Authorization Only with optional Standard Message Data Segment" populates every conditional Seg 100 field

| | |
|---|---|
| Where | TC-020989, TC-021075, TC-021163, TC-021336, TC-021419, TC-021509 and 2 more (8 TCs: 7 `ready`, 1 `pending_sme`; expected **approved**) |
| Generated | e.g. TD-TC-020989: `ApprovalNumber "J38FQA"` (random) and `LocalDateandLocalTime "0615251430"` on an **original Authorization Only** (TT 3); `EncryptedPINBlockData "16"`; `PumpLaneNumber "1"`, `FuelPurchaseAmount "1"`, `TaxAmount "1"`, `CashAmount "1"` at a GENERAL (non-fuel) merchant. |
| ATL105 rule | E5 p.368: "If it [Approval Number] is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization." E49 p.388: Local Date/Time "is only sent in a POS and CAT Capture transaction." E79 p.402: Pump/Lane "Required on all fuel transactions". |
| Fix | "Optional field present" tests must use a value that is valid **in that transaction context**. Approval Number and Local Date/Time belong only on capture/follow-on legs. |

<a id="r4-05"></a>
### R4-05 – Boundary / profile tests that expect acceptance use invalid values

Every BD1/BD2/P7/P9 TC claims "… is accepted". Where a response code is asserted, it is `0`. These TDs cannot be accepted:

| Pattern | Example TCs | Generated | Rule broken |
|---|---|---|---|
| Information Byte "at max length (1)" | TC-020170 (FTR); TC-012110, TC-012247, TC-012464, TC-012516 (Totals, E-mail, Proprietary Data Load, Totals + Proprietary); network TC-024353 | `"A"` – **identical** to the N-C5 "value not in valid values → declined" test TC-020173 | E44 p.386: `?`, `0`, `1` |
| Service Level "at max length (1)" | network TC-024336 | `"A"` | E87 (F/S/N/X/H/O); Java `SEG102-E87` FAIL |
| Fuel / Tax / Cash Amount "at max length (8)" | TC-020197, TC-020208, TC-020215 | `"00000000"` (zero) | Range 1–99999999 (E41/E58/E17/E99) |
| Nonfuel / Fuel / Cash "range min/max" | TC-020200, TC-020201, TC-020206, TC-020218, TC-020219 | `"00000001"`, `"99999999"` (8 bytes) | E58 p.393: "maximum length of 7 bytes … for all other card types" (8 is AMEX only) – card type is MasterCard 020 |
| Number of Products "at max length (2)" / "range max (10)" | TC-020268, TC-020275 | `"00"` / `"10"` with one product present | 12.3 p.229 (01–10, must equal the products sent) |
| Totals Date "at max length (6)" | TC-012127 | `"000000"` | E105 p.419 (MMDDYY or 111111/222222/…/999999) |
| Sequence Number "at limit" | TC-012272, TC-012422 + 3 more (host messages) | `"000000"` | E86 p.409: 000001–999999 |
| EMV Additional Information Indicator "at max length (3)" | **TC-020506** | indicator `000`, length `984`, E118 = **the original CD-02 984-character random string** (`XPXZRQBX814S3CAQ0ZNJ…`), Seg 130 length `1182` | E191 p.479: only `001`; App. T p.701 |
| Variable Information Length "range min/max (001–999)" | TC-020423, TC-020424 | VIL unchanged (`003`); Table 005 value changed to `000` instead | Test does not exercise its claim; `000` contradicts the EMV request |
| Maximal field profile (P7) | TC-020557, TC-020613 | PIN block `"16"`, Local Date/Time on a TT-0 purchase | E33, E49 |
| Purchase Card segment present (P9) | TC-022264, TC-022266 | Random R4-01 content | R4-01 |

Also: **21 data element TCs are `REJECTED_FINAL` / `blocked_verification` but still ship a TD file** (e.g. TC-020206, TC-020249 – TC-020251). The README states "0 validation findings" for all three BRs.

<a id="r4-06"></a>
### R4-06 – Negative tests carry collateral violations

A negative test must break exactly one rule, so the decline can be attributed to it. These TDs break more than one:

| Pattern | Example TCs | Collateral violation |
|---|---|---|
| CDBD over-length / charset / impossible month | TC-020184, TC-020186, TC-020242 | `AccountNumber` replaced with `"TOTALS REQUEST"` (the loyalty totals rule of E2 p.366) on an EMV purchase |
| Seg 100 length ±1 | TC-020240, TC-020241 | PIN block `"16"` (R4-03) |
| Totals Card Label tests (N-C2/3/4/5/8) | TC-012166 – TC-012171 | Card Type Total Count and Amount (fields 16–17, R) dropped |
| Totals Card Type Total Count / Amount tests | TC-012172 – TC-012184 | Card Label (field 15, R) dropped; declared lengths do not match |
| COF indicator code `X` | TC-020431, TC-020432 | Table 030 and the sibling COF tables (049/056 or 032/056) dropped on an entry-mode-10 leg (CD-03/CD-04 rules) |
| "Additional Information sent empty / omitted" | TC-020522, TC-020537 – TC-020539, TC-020611, TC-020655 – TC-020657 | E192 left at `001` while E118 is empty |
| "Required field X omitted" (N-C1) vs "X sent empty" (N-C4) | e.g. TC-020571 vs the N-C4 twin | Same wire bytes (FS-delimited field present but empty). These are duplicates. |
| Main "segment omitted" (R4-12) | TC-022198, TC-024319 | Table 030/032/049/056 dropped on COF legs |

The validator also flagged 210 negative TDs where it could not see the named mutation. These are mostly checks the scripted validator does not implement (fixed Segment Type values, charset on Pump/Lane/Approval, Seg 102 fields). They were spot-checked and are **not** counted as defects.

<a id="r4-07"></a>
### R4-07 – Chip / track-read legs carry an expiry-only MMYY E12

| | |
|---|---|
| Generated | Data element package: **438 legs** carry a 4-character `CardDiscretionaryBlockData` in **MMYY** form (e.g. `0830`, `0329`): 374 with Table 005 `051` (EMV chip), 34 `821` and 30 `911`. In YYMM form these values have an invalid month. 6 more `021` (partial track) legs use the same MMYY form (review). The same carrier is used by 41 network TCs, TC-024312 – TC-024360 (e.g. TC-024336 `071` / `0927`). |
| Expected | For a chip/track read, E2/E12 carry the data read between the sentinels: Track-2 equivalent with YYMM + service code + discretionary data. The network package does this for the same entry modes (15 characters, e.g. `290520101440570`). MMYY is the manual-entry format. |
| ATL105 rule | E2 p.364, E12 p.371 (track read: "all data read from the card between … Start Sentinel and End Sentinel"); E12 p.372 (MMYY = manually entered); App. J p.638 (`05` = "ICC read – CVV data reliable"). Java runner: `APPJ-READ-HAS-TRACK` and `TRACK2-EXPIRY-YYMM` FAIL on TC-024336. |
| Effect | Every data element test on these entry modes carries a second, unintended error. The positive boundary tests in this family cannot be accepted. |

<a id="r4-08"></a>
### R4-08 – Host-operations requests break Terminal Identifier / segment-count rules

| Message | Generated | Expected | Rule | Scope |
|---|---|---|---|---|
| CA Public Key File Load Request (Seg 132) | TID `JX72948216050257001` (19 bytes); SegmentLength `094` | TID **13 bytes, Device Type `+*`**; segment max **77** | Seg 132 F4 p.274; E48 p.388 ("Device Type must be '+*'"); 12.22 p.274 | operations TC-022221, TC-020696; data element 54 legs |
| TransArmor Key Load Request (Seg 116) | TID `JX…` | Device Type **`++`** | E102 p.417 | operations TC-022220, TC-020694; data element 45 legs |
| Table / Phone / Date-and-Time / Software Load Request | TID 19 bytes | **13 bytes** | E102 p.417 note | operations TC-022222 – TC-022225, TC-022227, TC-022228; data element 70 legs |
| Proprietary Data Load Request (Seg 118) | `NumberofSegments "1"` | `"01"` (2 digits) | E63 / §11 Data Section 1 | operations 10 legs (e.g. TC-022215, TC-022216); data element 75 legs |

Of these, the data element BD1 "accepted" tests carry the same identity errors: CA key 11, TransArmor 10, downloads 18, Proprietary Data Load 14.

<a id="r4-09"></a>
### R4-09 – Device/host-assigned values filled with random strings (Review)

Hardware/Software/Firmware Versions are random 8-character strings (e.g. `FQ4R0WSY`, `A1R7QXCZ`). This affects 334/326/328 occurrences across Totals, Totals + Proprietary, TransArmor, CA Public Key, Table and Software Load. Seg 118/119 `DeviceCardTableVersion` / `CardTableLoadVersion` are 30 random digits and `LoadControlKey` is 30 random characters (79). Moneris `SPDHHeader`, terminal and merchant IDs are random (23 each). These are device-certified or BUYPASS-assigned values (E39, E43, E96; 12.16, 12.17). They should be client inputs (`<AWAITING:…>`), not synthetic values in `ready` TCs. This extends RV-11.

<a id="r4-10"></a>
### R4-10 – Delivery-contract violations for client inputs

- **8 online-PIN TCs** send E33 as an empty string. The contract requires `<AWAITING:…>` and says "never an empty field". See CD-13.
- **46 TCs** carry `<AWAITING:…>` table content (Tables 001 – 082) but have status `pending_sme`. The contract says such TCs "have status `needs_client_input` and are not executable". Examples: TC-023443, TC-023491, TC-019586.
- Awaiting tables replace the **Variable Information Length** itself with a placeholder and count it as 0 bytes in the Seg 111 length. E112 is a fixed 3-digit field (12.10 F4 p.246), so the declared Seg 111 lengths on the **438 network legs** that carry awaiting tables are provisional by at least 3 bytes per table. The Java runner fails `SEG111-E112` and `E84-SEG111-LENGTH` on TC-020981.

<a id="r4-11"></a>
### R4-11 – Generated combinations that cannot occur

| Combination | TCs | Why |
|---|---:|---|
| "… with offline pin" on Magnetic Stripe, Contactless Magstripe, Manual, Chip Fallback (`79` keyed) and COF | 24 | Offline PIN is verified by the chip application (10.14.3 p.162). None of these entry modes has a chip interaction. |
| "… with offline pin" on EMV contactless / contactless mobile | 8 | Review: contactless kernels use online PIN / CDCVM, not offline PIN. |
| "… on a CAT terminal with online pin" on non-EMV credit (magstripe, contactless magstripe, keyed, fallback, COF, mobile) | 6 | Review: credit online PIN is described only for EMV (10.14.3). E33 is "required for debit and EBT". |
| Table 048 (MasterCard 3-DS Authentication Data) on card-present Authorization Only (`05`, `07`, `90`, `91`, `01`, `79`, `82`) | 10 | App. I p.586–587: applies to merchants sending **e-commerce** transactions with 3-D Secure. |

<a id="r4-12"></a>
### R4-12 – "Segment omitted" negatives claim requirements that do not apply

9 of the 12 network N-C7 TCs (`deliberate_violation_missing_segment`, all recording `violated_element_name: "Prompt Code"`) claim that an optional or inapplicable segment is required. They cite pages that state no such requirement. Examples:
- TC-022198: "Credential on File Purchase/Capture Enhanced Fleet Request (segment 145) omitted where p.561 requires it". p.561 is App. I Table 017, and Seg 145 is not required on MasterCard Credit.
- TC-024319 / TC-024325: Seg 123 "omitted where p.267 requires it". p.267 requires Seg 123 only for tokenized data, and the carrier is not tokenized.
- TC-024328: Loyalty 108 "where p.15 requires". TC-024330: Fleet 101 "where p.23 requires". TC-024326, TC-024329, TC-024331: Purchase Card 104 "where p.72 / p.28 / p.24 requires". p.15 is a revision-history page, p.23 / p.24 / p.28 are table-of-contents pages, and p.72 is the §9 electronic-journal grand-total text.
- TC-024320: Network Token 153 "where p.310 requires". 12.38 makes Seg 153 conditional.

The 3 "EMV Request Data Segment (130) omitted" TCs (TC-024318, TC-024338, TC-024360) are valid negatives. In the invalid ones the TDs also drop the COF tables (R4-06), and expected codes are `FLAG` tier, so the decline cannot be attributed to the claimed omission.

<a id="r4-13"></a>
### R4-13 – Totals bucket for non-sale flows (Review)

Totals after Authorization Only (TT 3/B), Authorization Only → Cancellation, Merchandise Return, Return → Void and Balance Inquiry flows all send `CardLabel "CC  "`, `CardTypeTotalCount 00001`, `CardTypeTotalAmount 00000000`, `GrandTotal 00000000`. This affects 37 legs, e.g. TC-023988 (return voided, yet count 1). E13 p.372–373 defines a separate **`AO` (Authorization only)** label. A count of 1 with amount 0 does not represent a $15.00 transaction, and a voided return should net to no category (same logic as the CD-11 fix). SME to confirm the expected device totals.

<a id="r4-14"></a>
### R4-14 – Boundary TC naming / implementation mismatch

The 3 network BD1 TCs are named "… → declined", but their objective is "… is accepted" and the expected response is `0`/`2`:
- TC-024336
- TC-024341
- TC-024353

TC-024341, "Account Number exactly at its documented maximum length (24 bytes)", sends a **16-byte** PAN (`5191561344107494`). The 24-byte boundary is not exercised.

<a id="r4-15"></a>
### R4-15 – "Sending Table NNN" tests include tables scoped to other programs (Review)

The network package sends 52 different App. I tables besides 005/030/031/032/049/056, mostly as `<AWAITING:…>`, in 8–11 TCs each. Some of these tables are scoped to other programs. For example, Table 001 (UPC – "All Blackhawk transactions", InComm) and Table 002 (Program Identifier – stored value / ECA / WIC; App. I p.552–553) are not MasterCard Credit fields. The README states that other-brand rules were removed. Table applicability should get the same treatment.

---

## 5. Data element validation sub-package (summary)

Items already covered in §4 are R4-05, R4-06, R4-07 and R4-08. In addition:
- Mutations are generally well formed. Segment Length is recomputed after each mutation, e.g. `079`/`078`/`077` for IB `AA`/`A`/`""`, so only the targeted rule is broken where no carrier defect applies.
- Each leg gets a new PAN, sequence number and unpredictable number. The PANs are valid MasterCard IINs and Luhn-valid.
- 14 TCs are `blocked_no_test_data` and correctly ship no TD. Of the 36 N-C7 structural tests (missing / wrong-type / count / length / order), 32 are `ready` with TD.

## 6. Terminal & host operations sub-package (summary)

The operations package has 47 TCs / 42 TD files. The structural defects are in R4-08 (CA key, TransArmor and download identity, Proprietary Data Load segment count) and R4-09 (synthetic version/key values). Other observations:
- Totals Request (`990`, Totals Date `222222`/`333333`/`111111`, Seg 105 `090`) and E-mail Request (`981`/`996`, Seg 109 `052`) TDs are structurally consistent.
- Communications Test sends `COMMTEST` (E120 p.481).
- Several "declined" operations TCs have TDs byte-identical to their positive twins, e.g. TC-020679 = TC-022211. The decline depends on the merchant profile, and these TCs are correctly `needs_client_input`.

## 7. Field-by-field sample (Java runner)

[TC_Reports/README.md](./TC_Reports/README.md) covers 35 TCs and 48 legs: one approved purchase per entry mode, plus reversal, TOR, cancellation, void, split tender, totals, COF, MOTO, balance inquiry, Track 1, online/offline PIN, AVS, unmatched TOR and the R4-01/03/04/05 examples. The result is 25 FAIL and 10 PASS_WITH_REVIEW. How to read it:
- The Appendix T framing rule (2 checks) fails on 22 TCs, and for 20 of them it is the only FAIL. This is **RV-03**, not a new defect.
- The `FOLLOW-ON-SAME-EXPIRY` FAIL on TC-021571 / TC-022444 is a pre-RV-01 rule and not a defect.
- The `APPG-TXN-TYPE` FAIL on TC-024336 is a runner false positive: TT `4` is correct for a CAT purchase.
- All other FAILs match the scripted findings: R4-03, R4-07, CD-08, R4-02, R4-10 and R4-05.
- The runner's header cites spec 2026-3. Every rule used is unchanged in 2026-4 (verified in Round 1).

## 8. Overall assessment and recommendations

1. **Fix the package writer first (CD-19 / CD-20).** No TC→TD path resolves as written, and the manifest, README and contract describe a different package. These must be fixed before any coverage or "ready" figure can be trusted.
2. **Stop synthetic filling.** Remove `format_synthesis` from optional segments, PIN blocks, versions and keys (R4-01, R4-03, R4-04, R4-09). Where a real value is unknown, use `<AWAITING:…>` and `needs_client_input`, as the contract already prescribes.
3. **Repair the data element carrier template.** Use track-equivalent E12 for chip/track entry modes (R4-07), valid boundary values (R4-05), and no collateral changes (R4-06). Then reconcile the 21 `REJECTED_FINAL` TCs.
4. **Close the carried-over open items.**
   - CD-08: build card B's 9F02 from the runtime remainder.
   - CD-07: state that segment lengths are recomputed after token substitution.
   - CD-13: use the `<AWAITING>` PIN block.
   - CD-18: add Table 003 to the "AVS … returned" TCs, or downgrade them.
5. **Restore the removed scenarios.** Re-add expired card (CD-12), cash back (CD-15) and contactless-MSR-only terminal (CD-17). Add chip fallback to swipe (`80`), which has 0 legs. Remove the impossible combinations (R4-11, R4-12).
6. **Fix the host messages.** Apply E102/E48 identity rules and E63 width to all host messages (R4-08).

---

### Evidence files
- [MC_Credit_Round4_Findings.csv](./MC_Credit_Round4_Findings.csv) lists every finding: 2,193 rows, of which 455 DEFECT, 123 REVIEW and 1,615 NEG. NEG means the intended mutation of a negative test and is informational. Columns: package, check, severity, TC, test type, polarity, status, TD file, detail.
- [TC_Reports/](./TC_Reports/) holds the 35 per-TC field validation reports and their index.
- Earlier MasterCard Credit rounds:
  - [ATL105_MC_Credit_Consolidated_Defects.md](../ATL105_MC_Credit_Consolidated_Defects.md) (Round 1)
  - [Consolidated_Revalidation_AllProducts_2026-11-05.md](../Consolidated_Revalidation_AllProducts_2026-11-05.md) (Round 2)
  - [Consolidated_Revalidation_AllProducts_2026-11-06_Round3.md](../Consolidated_Revalidation_AllProducts_2026-11-06_Round3.md) (Round 3)
