# ATL105 AI Test Data – Consolidated Defect Log (American Express Credit, MasterCard Credit, MasterCard Fleet)

| Item | Value |
|---|---|
| For | AI Dev team – please validate each defect and record your response in the register (§2) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **Release 2026-4** (October 09, 2026). Page numbers are **PDF pages** of the 2026-4 PDF. The AMEX source log cited 2026-3; every rule used here is unchanged in 2026-4. |
| AI delivery | `Downloads\31-10-out` – `american_express_credit`, `mastercard_credit`, `mastercard_fleet` (`test_cases\test_cases.jsonl`, `test_data\TD-TC-*.json`, `reports\traceability_matrix_full.json`) |
| Consolidated from | **S1** [AMEX_Credit_Defect_Log.md](./AMEX_CREDIT_Validations/AMEX_Credit_Defect_Log.md) – AMEX Credit, all 416 TCs / 790 TD files<br>**S2** [ATL105_MC_Credit_Consolidated_Defects.md](./ATL105_MC_Credit_Consolidated_Defects.md) – MC Credit, 104 EMV + 5 magstripe TCs + package review (2026-11-02)<br>**S3** `MasterCard_Credit_Consolidated_Defect_Report - Copy.md` – MC Credit, sample of 118 TCs / 185 TD files across all 36 scenarios (2026-11-03)<br>**S4** `ATL105_MC_Fleet_TC-002172-002176_Consolidated_Defects.md` – MC Fleet, TC-002172 – TC-002176 (2026-11-03) |
| Verification | **2026-11-03.** Every defect rule of S1–S4 was re-run against the **full** delivery of all three programs (not only the sample each report used), and every disputed rule was re-read in the 2026-4 spec text. Expiry checks use run month `2611`. |
| Result | **18 confirmed field-level defects** + **2 package defects**, **2 items moved to review** (§5), all source counts reproduced. 10 defects occur in more programs or files than the source reports stated (§6). |

Test case IDs in the tables are written without the `TC-` prefix; `a–b` means every test case from `a` to `b`.

---

## 1. Summary

| Measure | AMEX Credit | MC Credit | MC Fleet |
|---|---|---|---|
| Test cases in delivery / with test data | 416 / 374 | 450 / 408 | 299 / 249 |
| Test data files | 790 | 839 | 569 |
| Confirmed field-level defects present (of CD-01 – CD-18) | 15 | 18 | 10 |
| Package defects (CD-19, CD-20) | CD-19 | CD-19, CD-20 | CD-19 |
| Review items moved out of the defect list | RV-01 | RV-01 | RV-01, RV-02 |

**Systemic defects:** CD-01, CD-02, CD-03, CD-04, CD-05 and CD-09 come from one generator rule each and occur in **every** file of their kind in all three programs. Fixing these six rules removes the large majority of failing files.

### Result categories

| Result | Meaning |
|---|---|
| **Confirmed** | Breaks an ATL105 rule, or the data cannot prove what the test case claims. Reproduced on 2026-11-03. Must be fixed. |
| **Confirmed – wider scope** | Confirmed, and found in more programs/files than the source report listed. |
| **Review** | May be acceptable; the spec is ambiguous or contradicts the source finding. Needs SME/BUYPASS confirmation. |

---

## 2. Defect register

Counts are **test cases / TD files** found on 2026-11-03 (full delivery). `–` = not applicable or not found in that program.

| ID | Defect | Element / field | AMEX Credit | MC Credit | MC Fleet | Source IDs | Severity | Status | AI team: Agree / Disagree | AI team: Fix plan / comment |
|---|---|---|---|---|---|---|---|---|---|---|
| [CD-01](#cd-01) | EMV Card Sequence Number `01` instead of `001` | E188, Seg 130 | 94 / 116 | 104 / 126 | 60 / 78 | S1 A-01 · S2 D-01, O31-02 · S3 D-01 · S4 FL-03 | High | Confirmed | | |
| [CD-02](#cd-02) | EMV Additional Information is 984 random characters instead of `EMVYES` / `EMVNOT` | E191/E192/E118, Seg 130 | 94 / 116 | 104 / 126 | 60 / 78 | S1 A-02 · S2 D-02 · S3 D-02 · S4 FL-04 | High | Confirmed | | |
| [CD-03](#cd-03) | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | E113 Table 030, Seg 111 | 140 / 204 | 158 / 223 | 90 / 139 | S3 D-12 | High | Confirmed – wider scope | | |
| [CD-04](#cd-04) | Credential-on-File indicators (Tables 032, 049, 056) missing | E113 Tables 032/049/056, Seg 111 | 46 / 88 | 54 / 97 | 30 / 61 | S3 D-09 | High | Confirmed – wider scope | | |
| [CD-05](#cd-05) | Chip fallback entry mode `79` (fallback to **manual**) sent with swiped Track 2 data | E12 + Table 005 | 44 / 54 | 47 / 57 | 30 / 39 | S1 A-03 · S3 D-08 | High | Confirmed – wider scope | | |
| [CD-06](#cd-06) | Unmatched time-out reversal carries full track data | E12 | 5 / 5 | 5 / 5 | 5 / 5 | S3 D-13 | Medium | Confirmed – wider scope | | |
| [CD-07](#cd-07) | Runtime amount after partial approval: provisional value is 8 digits and larger than the sale; Segment Length calculated from it | E58 / E84, Seg 100 | 31 / 31 | 31 / 31 | – | S1 A-06 · S2 D-07, M-03, M-04 · S3 D-05, D-14 | High | Confirmed – wider scope | | |
| [CD-08](#cd-08) | Split-tender card B chip amount (Tag 9F02) = full original amount | Tag 9F02, Seg 130 | 4 / 4 | 4 / 4 | – | S1 A-07 · S2 D-05 · S3 D-05 | High | Confirmed | | |
| [CD-09](#cd-09) | Product Code Data Segment length `033`, spec arithmetic gives `029` | E84, Seg 102 | 6 / 6 | 6 / 6 | 249 / 497 | S2 D-08 · S4 FL-02 | Medium | Confirmed – wider scope | | |
| [CD-10](#cd-10) | Totals Card Label 2 bytes instead of 4 (`TE`/`CC` → `TE  `/`CC  `) | E13, Seg 105 | 72 / 72 | 72 / 72 | 72 / 72 | S1 A-05 · S2 D-06 | Medium | Confirmed – wider scope | | |
| [CD-11](#cd-11) | Totals card-type category sent with count `00000` | E16, Seg 105 | 8 / 8 | 8 / 8 | 8 / 8 | S2 D-10 | Medium | Confirmed – wider scope | | |
| [CD-12](#cd-12) | Expired-card test uses a card that is not expired | E12 (+ Tag 5F24, TVR) | 2 / 2 | 2 / 2 | – | S1 A-08 · S2 D-03 · S3 D-03 | High | Confirmed | | |
| [CD-13](#cd-13) | Online-PIN test has no PIN block and a signature CVM | E33, Tag 9F34 | – | 4 / 4 | – | S2 D-04 · S3 D-04 | High (blocked by client input) | Confirmed | | |
| [CD-14](#cd-14) | Offline-PIN test has a signature CVM | Tag 9F34 | 2 / 2 | 2 / 2 | – | S1 A-09 · S2 D-04b · S3 D-11 | Medium | Confirmed | | |
| [CD-15](#cd-15) | Cashback test: chip data ignores the cash amount | Tags 9C, 9F02, 9F03 | – | 2 / 2 | – | S2 D-09 | Medium | Confirmed | | |
| [CD-16](#cd-16) | "Track 1" test carries Track 2 data | E2 / E12 | – | 4 / 4 | 2 / 2 | S2 M-01 · S3 D-07 | High | Confirmed – wider scope | | |
| [CD-17](#cd-17) | Terminal described two ways: Table 005 capability `8` with Table 031 `12` | Seg 111 Tables 005/031 | 2 / 2 | 2 / 2 | – | S1 A-10 · S2 M-02 · S3 D-06 | High | Confirmed | | |
| [CD-18](#cd-18) | AVS test sends no address data (Table 003) | E113 Table 003, Seg 111 | 8 / 8 | 8 / 8 | – | S3 D-10 | High | Confirmed – wider scope | | |
| [CD-19](#cd-19) | Traceability matrix points to a folder that is not delivered (`qe_shaped_test_data/`) with `file_exists: true`; follow-on legs never traced | `reports/traceability_matrix_full.json` | 374 rows; 416 files untraced | 408 rows; 431 files untraced | 249 rows; 320 files untraced | S2 O31-01 · S4 O31-01 | High | Confirmed | | |
| [CD-20](#cd-20) | MC Credit package mixes two builds (export 450 TCs vs embedded zip / README 241 TCs) | `BR_SCE_Test_cases.zip`, `README.md` | – | whole package | – | S2 O31-03 | High | Confirmed | | |
| [RV-01](#rv-01) | Keyed / COF follow-on expiry sent as YYMM while the original is MMYY (`79` follow-ons: see CD-05) | E12 | 58 / 70 | 60 / 72 | 34 / 44 | S1 A-04, A-03b | – | **Review** (was a defect in S1) | | |
| [RV-02](#rv-02) | Fleet Data Segment length `035` (`021` / `030` / `035` depend on spec reading) | E84, Seg 101 | – | – | 244 / 492 | S4 FL-01, R-07 | – | **Review** | | |

---

## 3. Field-level defects in detail

Each defect lists **where**, **generated vs expected**, **the ATL105 rule**, **the fix**, **how it was verified** and **the related test cases**.

<a id="cd-01"></a>

### CD-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3

| | |
|---|---|
| Where | Every EMV request file: `"EMV Request Data Segment"` → `"EMVCardSequenceNumber"` (line 47; line 63 on FSA files; line 78 on Fleet files) |
| Generated | `01` |
| Expected | `001` |
| ATL105 rule | **E188, p.477:** *"Fixed length of three bytes, right-aligned and padded with zeroes. Space-filled if EMV Tag 5F34 is not provided … Valid Codes/Values: 000—099."* 12.20 Field 4 (p.270). |
| Why | Tag 5F34 in the chip data is `01`, so the value is right but the width is wrong. The declared Segment 130 length (`1181`) was counted with 2 bytes. |
| Fix | Zero-pad Tag 5F34 to 3 bytes (`001`); 3 spaces when 5F34 is absent. Recompute the Segment 130 length (+1). |
| Verified | Every EMV request in all three programs has a 2-character value (AMEX 116/116, MC Credit 126/126, Fleet 78/78). Also 90 files each in Interlink Debit and Maestro Direct Debit: **500 files / 402 TCs** across all five programs, as stated in S2 (O31-02). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-01 | 94 / 116 | 002471–002502, 002505–002536, 002745–002756, 002758–002769, 002855, 002856, 002858, 002859, 002879, 002880 |
| MC Credit | D-01 / O31-02 | 104 / 126 | 001722–001756, 001759–001793, 002020–002033, 002035–002048, 002140, 002141, 002143, 002144, 002164, 002165 |
| MC Fleet | FL-03 | 60 / 78 | 002172–002194, 002198–002220, 002389–002394, 002397–002402, 002455, 002457 |

<a id="cd-02"></a>

### CD-02 – EMV Additional Information (Element 118) does not contain EMV Table Data

| | |
|---|---|
| Where | Every EMV request file: `"EMVAdditionalInformationIndicator"`, `"EMVAdditionalInformationLength"`, `"EMVAdditionalInformation"` (value on line 52; line 68 on FSA files; line 83 on Fleet files) |
| Generated | Indicator `001`, Length `984`, Information = 984 random A–Z/0–9 characters (e.g. `XPXZRQBX814S3CAQ0ZNJ…C5K8MMTF`); the same string is reused across test cases |
| Expected | Indicator `001`, Information **`EMVYES`** or **`EMVNOT`**, Length `006` (framing: see RV-03) |
| ATL105 rule | **E191 (p.479):** `001` = *"EMV table data"*. **E118 (p.439):** *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here … Please refer to Appendix T."* **Appendix T-1 (p.701):** EMV Table Data *"Maximum Length n6"*, request values `EMVYES` / `EMVNOT`. |
| Why E192 = `984` is not the defect | E192 is in range (`001–985`) and equals the content length. The **content** is not allowed for indicator `001`. |
| Root cause | `method: format_synthesis`, flag `UNTRUSTED_VALID_VALUES` – the generator filled the generic 984 maximum instead of applying Appendix T. |
| Fix | Send `EMVYES`/`EMVNOT`, set E192 to its length, recompute Segment 130 (with CD-01: `1181` → `0204`). |
| Verified | No EMV request in any of the three programs contains `EMVYES`/`EMVNOT` (AMEX 116, MC Credit 126, Fleet 78). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-02 | 94 / 116 | same as CD-01 |
| MC Credit | D-02 | 104 / 126 | same as CD-01 |
| MC Fleet | FL-04 | 60 / 78 | same as CD-01 |

<a id="cd-03"></a>

### CD-03 – Table 030 (POS Condition Code) is missing on EMV and Credential-on-File requests

| | |
|---|---|
| Where | `"Variable Information Data Segment"` → `"VariableInformationSection"` (line 30) |
| Generated | Tables sent: `005`, `031` only |
| Expected | Table 030 (10 digits, values from Appendix Q for the certified terminal) on every EMV request and every Credential-on-File request |
| ATL105 rule | **Appendix I, Table 030, p.568:** *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* The rule does not depend on the card type. |
| Fix | Add Table 030 to every EMV request (Segment 130 present) and every request with entry mode `10`; recompute the Segment 111 length. |
| Verified | Missing in **every** EMV and COF request of all three programs: AMEX 116 EMV + 88 COF, MC Credit 126 + 97, Fleet 78 + 61. S3 reported 46 TCs / 61 files from its MC Credit sample only. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | – (new) | 140 / 204 | all EMV TCs of CD-01 + COF 002675–002700, 002739–002744, 002827–002835, 002852, 002853, 002873, 002874, 002885 |
| MC Credit | S3 D-12 | 158 / 223 | all EMV TCs of CD-01 + COF 001941–001973, 002014–002019, 002110–002119, 002137, 002138, 002158, 002159, 002170 |
| MC Fleet | – (new) | 90 / 139 | all EMV TCs of CD-01 + COF 002337–002359, 002439–002444, 002467 |

<a id="cd-04"></a>

### CD-04 – Credential-on-File indicators (Tables 032, 049, 056) are missing

| | |
|---|---|
| Where | `"Variable Information Data Segment"` → `"VariableInformationSection"` (line 30), every step with Table 005 entry mode `10` (including reversals, completions, cancellations) |
| Generated | Tables sent: `005`, `031` only |
| Expected | Table 032 sub-table 08 = `C` (`I` on the step that stores the credential); Table 049 sub-table 02 (Transaction Initiation Indicator `C`/`M`); for MasterCard also Table 056 sub-table 09 (MIT/CIT category) |
| ATL105 rule | **App. I Table 032-08, p.575:** *"It is merchant's responsibility to send the indicator as Initial ("I") or Subsequent Credential on File ("C") … for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."* **App. I Table 049-02, p.589:** *"This field is required for all subsequent Credential on File transactions."* **10.13.2.4.2, p.144:** Table 056-09 *"… for MasterCard card type."* |
| Note for AMEX | Table 056-09 is stated for MasterCard only. For AMEX the confirmed gap is Tables 032-08 and 049-02. |
| Fix | Add the three tables (two for AMEX) on every COF step. |
| Verified | None of the three tables is present on any COF step (AMEX 88, MC Credit 97, Fleet 61 files). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | – (new) | 46 / 88 | 002675–002700, 002739–002744, 002827–002835, 002852, 002853, 002873, 002874, 002885 |
| MC Credit | S3 D-09 | 54 / 97 | 001941–001973, 002014–002019, 002110–002119, 002137, 002138, 002158, 002159, 002170 |
| MC Fleet | – (new) | 30 / 61 | 002337–002359, 002439–002444, 002467 |

<a id="cd-05"></a>

### CD-05 – Chip fallback entry mode `79` (fallback to manual) is sent with swiped Track 2 data

| | |
|---|---|
| Where | Table 005 = `791` (line 34) with `"CardDiscretionaryBlockData"` (line 15) |
| Generated | Track 2 data, e.g. `291220166504315` = expiry `2912` (YYMM) + service code `201` + discretionary data |
| Expected | Keyed fallback: `005=791` + E12 = expiry **MMYY** only (e.g. `1229`). Or, if the card is really swiped: `005=801` and keep the track data. |
| ATL105 rule | **App. J, p.638:** `79` = *"Contact and Contactless chip fallback to manual"*; `80` = *"Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)"*. **E12, p.372:** *"For a card whose number has been manually entered, the expiration date is in MMYY format."* **10.1.1.3, p.81:** manual entry of Account Number and Expiration Date (MMYY). |
| Fix | Entry modes where the card is not read (`01`, `79`): E12 = MMYY only. Use `80` for swiped fallback. |
| Verified | Every non-follow-on `79` request in all three programs carries track data. AMEX matches S1 exactly (54 files / 44 TCs). MC Credit: 57 files (S3 sample: 12). Fleet: 39 files (not in S4 scope). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-03 | 44 / 54 | 002643–002663, 002665–002672, 002813–002824, 002870, 002871, 002884 |
| MC Credit | S3 D-08 | 47 / 57 | 001906–001928, 001931–001938, 002094–002106, 002155, 002156, 002169 |
| MC Fleet | – (new) | 30 / 39 | 002311–002333, 002431–002436, 002465 |

<a id="cd-06"></a>

### CD-06 – Unmatched time-out reversal carries full track data

| | |
|---|---|
| Where | Single-leg time-out reversal (`T10-TOR-UNMATCHED`, Prompt Code `Z…`), `"CardDiscretionaryBlockData"` (line 15) |
| Generated | Full Track 2 data, e.g. `300120114060327` (TC-001851) |
| Expected | Expiry only, YYMM (truncated Track 2), e.g. `3001` |
| ATL105 rule | **10.1.1.2, p.81:** *"All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile."* **10.1.8.1, p.94:** *"The merchant device must not store full magnetic stripe data."* **10.1.8.2, p.94–95:** the host message contains *"the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). No other Card Discretionary Data field follows …"* for Credit Completions, Purchase Reversals and Time Out Reversals. |
| Fix | Follow-on messages carry Account Number + expiry YYMM only; never copy the original's full track data. |
| Verified | 5 unmatched TORs per program carry 15–20 characters of track data. In the EMV ones (AMEX 002492/002526, MC 001744/001781, Fleet 002185/002211) the TOR is also built as an EMV request with Segment 130. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | – (new) | 5 / 5 | 002492, 002526, 002559, 002592, 002720 |
| MC Credit | S3 D-13 | 5 / 5 | 001744, 001781, 001817, 001851, 001993 |
| MC Fleet | – (new) | 5 / 5 | 002185, 002211, 002237, 002270, 002376 |

<a id="cd-07"></a>

### CD-07 – Runtime amounts after a partial approval: impossible provisional value and Segment Length calculated from it

| | |
|---|---|
| Where | Step 2 of split-tender, reversal, completion and cancellation flows: `"SegmentLength"` (line 7) and `test_cases.jsonl` → `steps[n].request` → `Nonfuel Amount` |
| Generated | TD amount = runtime placeholder (correct), e.g. `<RemainingBalance = …>`, `<NonfuelAmount from step 1 response>`. The TC stores an **8-digit** provisional value (e.g. `24677595` = $246,775.95; AMEX `74141657`), and the declared Segment Length was calculated from it (`093`/`092` split tender, `088`/`087` follow-on with Approval Number, `082`/`081` keyed). |
| Expected | Amount = 1500 − step-1 Approved Amount (split tender), or = step-1 Approved Amount (reversal/completion/cancellation), leading zeros removed. It is always < $15.00 (1–4 digits). The Segment Length must be calculated **after** substitution, e.g. MC split tender `086–089`, MC follow-on `081–084`. |
| ATL105 rule | **E58, p.393:** max **7 bytes** for all cards except American Express (8). **10.1.2.1, p.84:** remaining balance / approved partial amount. **E6, p.368:** Approved Amount 9 digits, zero-filled. **12.1, p.223 / E84, p.406:** length includes Segment Type and all Field Separators. |
| Note for AMEX | E58 allows 8 digits for AMEX, but the amount still cannot exceed the $15.00 sale, so the length is still wrong. |
| Fix | Derive provisional values from E6 with the business relation (remainder < original; reversal/completion = approved); ≤ 7 digits for MasterCard; calculate the Segment Length after substitution. See also RV-07 (placeholder name). |
| Verified | All 31 runtime-amount legs per program have a length that only fits an 8-digit amount, and all 31 TC provisional values are 8 digits. S1 reported only the 10 split-tender legs; S2 10 + 2; S3 14 (sample). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-06 (split only) | 31 / 31 | 002474, 002475, 002491, 002508, 002509, 002525, 002541, 002542, 002558, 002576, 002577, 002609, 002610, 002645, 002646, 002704, 002705, 002747, 002748, 002760, 002761, 002772, 002773, 002787, 002788, 002801, 002802, 002815, 002816, 002838, 002839 |
| MC Credit | S2 D-07, M-03, M-04 · S3 D-05, D-14 | 31 / 31 | 001726, 001727, 001743, 001763, 001764, 001780, 001799, 001800, 001816, 001835, 001836, 001870, 001871, 001908, 001909, 001977, 001978, 002023, 002024, 002038, 002039, 002052, 002053, 002066, 002067, 002081, 002082, 002096, 002097, 002122, 002123 |

<a id="cd-08"></a>

### CD-08 – Split-tender card B chip amount (Tag 9F02) is the full original amount

| | |
|---|---|
| Where | `*.step2.split-tender.json`, `"EMVChipData"` (line 49) → Tag 9F02 (search `9F0206`) |
| Generated | `000000001500` ($15.00 = step-1 amount of card A) |
| Expected | Card B's remainder, 12 digits (e.g. `000000000500` for $5.00) |
| ATL105 rule | **10.1.2.1, p.84:** the POS device *"must prompt for payment of the remaining balance due"*. **E190, p.478:** chip data must hold *"the exact values that were present on the card to terminal interface"*. KB rule SEG130-R-009 (9F02 = request amount). |
| Fix | Build step-2 Tag 9F02 from the step-2 amount at run time. |
| Verified | 4 / 4 EMV split-tender step-2 files per program. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-07 | 4 / 4 | 002474, 002491, 002508, 002525 |
| MC Credit | S2 D-05 · S3 D-05 | 4 / 4 | 001726, 001743, 001763, 001780 |

<a id="cd-09"></a>

### CD-09 – Product Code Data Segment (Segment 102) length is `033`, spec arithmetic gives `029`

| | |
|---|---|
| Where | `"Product Code Data Segment"` → `"SegmentLength"` (line 49 on Fleet files) |
| Generated | `033` |
| Expected | `029` = `102`▲`029`▲ (8) + `F` (1) + `01` (2) + `400` `U` `01` `\` `21500` `\` `1500` ▲ (18) |
| ATL105 rule | **12.3, p.229:** *"A Field Separator always follows Segment Type and Segment Length … A Product Data Field Delimiter always follows Quantity and Unit Price … A Field Separator follows Product Amount when Product Amount is the very last element."* The same counting reproduces the **Appendix B example (p.505/508)** length `049`. |
| Why `033` | The generator put a separator after every field, adding 4 that 12.3 does not allow (after Service Level, Number of Products, Product Code, Unit of Measure). |
| Fix | Apply the 12.3 separator/delimiter rules. |
| Verified | Every Segment 102 in the delivery is `033` vs a calculated `029`: AMEX 6 FSA files (not checked in S1), MC Credit 6 (S2: 4 EMV), Fleet 497 (S4: 9). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | – (new) | 6 / 6 | 002490, 002491, 002524, 002525, 002557, 002558 |
| MC Credit | S2 D-08 | 6 / 6 | 001742, 001743, 001779, 001780, 001815, 001816 |
| MC Fleet | FL-02 | 249 / 497 | every Fleet TC with test data (all request legs) |

<a id="cd-10"></a>

### CD-10 – Totals Card Label (Element 13) is 2 bytes instead of 4

| | |
|---|---|
| Where | Every `*.totals.json`, `"Totals Data Segment"` → `"CardLabel"` (line 20) |
| Generated | `TE` (AMEX), `CC` (MC Credit / Fleet) – 2 characters |
| Expected | `TE  ` / `CC  ` (value + 2 spaces) |
| ATL105 rule | **E13, p.372:** *"Fixed length of four alphanumeric characters; left-aligned and space-filled."* 12.6 Field 15 (p.235–237). |
| Note | The declared Totals Segment Length (`108`) only fits a 4-byte label (see RV-11), so the generator counted the padding but did not emit it. |
| Verified | All 72 Totals legs per program. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-05 | 72 / 72 | 002484–002489, 002500, 002501, 002518–002523, 002534, 002535, 002551–002556, 002568, 002569, 002586–002591, 002601, 002602, 002619–002624, 002637, 002638, 002655–002660, 002670, 002671, 002682–002687, 002698, 002699, 002714–002719, 002728, 002729, 002753, 002766, 002778, 002793, 002807, 002821, 002832, 002844 |
| MC Credit | S2 D-06 (18 EMV) | 72 / 72 | 001736–001741, 001754, 001755, 001773–001778, 001791, 001792, 001809–001814, 001829, 001830, 001845–001850, 001862, 001863, 001880–001885, 001900, 001901, 001918–001923, 001936, 001937, 001953–001958, 001971, 001972, 001987–001992, 002003, 002004, 002030, 002045, 002059, 002073, 002088, 002103, 002116, 002129 |
| MC Fleet | – (new) | 72 / 72 | 002179–002184, 002192, 002193, 002205–002210, 002218, 002219, 002231–002236, 002244, 002245, 002264–002269, 002277, 002278, 002290–002295, 002303, 002304, 002318–002323, 002331, 002332, 002344–002349, 002357, 002358, 002370–002375, 002383, 002384, 002393, 002401, 002409, 002418, 002426, 002435, 002443, 002451 |

<a id="cd-11"></a>

### CD-11 – Totals card-type category sent with a zero count

| | |
|---|---|
| Where | Purchase → Reversal → Settlement flows (`T8-PUR-REV-SETTLE`), `*.step3.totals.json` |
| Generated | CardLabel present, CardTypeTotalCount `00000`, CardTypeTotalAmount `00000000` |
| Expected | Omit the card-type category (the only purchase was reversed) and recompute the Segment Length |
| ATL105 rule | **E16, p.375:** valid **`00001–99999`**. **12.6, p.237:** Fields 15–17 *"are sent … only when data occurs for a card type."* |
| Verified | 8 / 8 per program (one per entry mode). S2 reported the 2 EMV ones. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | – (new) | 8 / 8 | 002485, 002519, 002552, 002587, 002620, 002656, 002683, 002715 |
| MC Credit | S2 D-10 | 8 / 8 | 001737, 001774, 001810, 001846, 001881, 001919, 001954, 001988 |
| MC Fleet | – (new) | 8 / 8 | 002180, 002206, 002232, 002265, 002291, 002319, 002345, 002371 |

<a id="cd-12"></a>

### CD-12 – Expired-card test uses a card that is not expired

| | |
|---|---|
| Where | `"CardDiscretionaryBlockData"` (line 15); chip Tags 5F24 and 95 |
| Generated | AMEX 002472 `2808`, 002507 `3006`; MC 001723 `3110`, 001761 `2907` (all in the future). No Tag 5F24; TVR byte 2 "Expired application" not set. |
| Expected | Expiry earlier than the run month (`2611`), matching Tag 5F24 and TVR byte 2 bit 7 |
| ATL105 rule | **10.14.2.1, p.160:** *"The device does not perform expiration date validation in an EMV card transaction."* **E12, p.372:** *"For all authorizations, merchants must send a value even for expired cards."* |
| Fix | Use an expired test card (open client input listed in the TC); mark the TC blocked until supplied. |
| Verified | 2 / 2 per program. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-08 | 2 / 2 | 002472, 002507 |
| MC Credit | S2 D-03 · S3 D-03 | 2 / 2 | 001723, 001761 |

<a id="cd-13"></a>

### CD-13 – Online-PIN test has no PIN block and a signature CVM

| | |
|---|---|
| Where | `"EncryptedPINBlockData"` (line 16); `"EMVChipData"` → Tag 9F34 |
| Generated | E33 empty; 9F34 = `5E0302` → CVM code `1E` = signature |
| Expected | DUKPT KSN + PIN block in E33; 9F34 CVM code `02` (e.g. `420302`) |
| ATL105 rule | **10.14.3, p.162:** *"Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."* **E33, p.382:** *"For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."* An empty E33 is correct for ordinary credit purchases (p.162) – this applies only to online-PIN tests. |
| Fix | Blocked by client input (online-PIN test card + DUKPT key). Until then mark the TC **blocked**. |
| Verified | 4 / 4 MC Credit `T3-ONLINE-PIN` TCs. AMEX and Fleet have no online-PIN template. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| MC Credit | S2 D-04 · S3 D-04 | 4 / 4 | 001724, 001762, 002021, 002037 |

<a id="cd-14"></a>

### CD-14 – Offline-PIN test has a signature CVM

| | |
|---|---|
| Where | `"EMVChipData"` (line 49) → Tag 9F34 (search `9F3403`) |
| Generated | `5E0302` (`5E` AND `3F` = CVM code `1E` = signature) |
| Expected | CVM code `01` or `04` (PIN verified by the card), e.g. `410302` / `440302` |
| ATL105 rule | **10.14.3, p.162:** *"Offline PIN: … the PIN entered by the consumer is matched to that on an application housed on the EMV chip card."* **E190, p.478** (exact card-to-terminal values). CVM codes: EMV Book 3 Annex C3. |
| Verified | 2 / 2 `T3-OFFLINE-PIN` TCs in AMEX and MC Credit. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-09 | 2 / 2 | 002473, 002746 |
| MC Credit | S2 D-04b · S3 D-11 | 2 / 2 | 001725, 002022 |

<a id="cd-15"></a>

### CD-15 – Cashback test: chip data ignores the cash amount

| | |
|---|---|
| Where | `T10-CASHBACK`, Segment 100 + `"EMVChipData"` |
| Generated | NonfuelAmount `1500` + CashAmount `500`; chip 9C `00`, 9F02 `000000001500`, 9F03 `000000000000` |
| Expected | 9C `09` (purchase with cashback), 9F02 `000000002000`, 9F03 `000000000500` |
| Basis | SEG130-R-009 (chip values agree with Segment 100); E190 p.478; EMV Book 3 tag definitions |
| Verified | 2 / 2. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| MC Credit | S2 D-09 | 2 / 2 | 001747, 001784 |

<a id="cd-16"></a>

### CD-16 – "Track 1" test carries Track 2 data

| | |
|---|---|
| Where | `"AccountNumber"` (line 14) and `"CardDiscretionaryBlockData"` (line 15) |
| Generated | e.g. `5159417392576181` / `300510138259706` – no format code `B`, no `^` (Track 2 form, identical to the Track 2 test except the Sequence Number) |
| Expected | Unaltered Track 1: Account Number `B5159417392576181`, CDBD = data after the first `^` (name, `^`, expiry, service code, discretionary), combined ≤ 76 |
| ATL105 rule | **10.1.1.2, p.81:** *"If the device cannot read Track 2, then the device should send unaltered Track 1 before attempting manual entry."* **E12, p.371:** Track 1 splits at the first `^`; combined ≤ 76 (Track 1) / 37 (Track 2). |
| Verified | Every `T2-TRACK1` / `T2-CHIP-SWIPED-TRACK1` TC: MC Credit 4 (S2/S3 listed 001797, 002051), Fleet 2 (new). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| MC Credit | S2 M-01 · S3 D-07 | 4 / 4 | 001797, 001930, 002051, 002108 |
| MC Fleet | – (new) | 2 / 2 | 002254, 002412 |

<a id="cd-17"></a>

### CD-17 – Terminal described two ways: Table 005 capability `8` with Table 031 `12`

| | |
|---|---|
| Where | `T2-RFID-TERMINAL`, Segment 111: Table 005 (line 34) and Table 031 (line 39) |
| Generated | `005=908` (swipe) / `005=018` (keyed) with `031=12` (hybrid contact + contactless chip) |
| Expected | Keep `908`/`018` and set Table 031 = `07` (contactless magstripe, no chip), on a separate RFID terminal ID – or use capability `1` if the terminal really has a chip |
| ATL105 rule | **App. J, p.639:** code `8` only *"If a Contactless MSR is attached to the POS device and it has no chip capability"*; *"For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8."* **App. I Table 031, p.569:** `07` = no chip; `12` = hybrid chip; *"Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode."* |
| Verified | 2 / 2 in AMEX and MC Credit; none in Fleet. |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | A-10 | 2 / 2 | 002540 (`908`), 002608 (`018`) |
| MC Credit | S2 M-02 · S3 D-06 | 2 / 2 | 001798 (`908`), 001869 (`018`) |

<a id="cd-18"></a>

### CD-18 – AVS test sends no address data (Table 003)

| | |
|---|---|
| Where | AVS templates (`T9-PUR-AVS`, `T9-MOP-AVS`, and the step 1 of `*-AVS-REVERSE`), Segment 111 (line 30) |
| Generated | Tables sent: `005`, `031` only |
| Expected | Table 003 with the (mismatching) street address / ZIP Code supplied by the client for the test card |
| ATL105 rule | **10.1.3, p.86:** *"The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment."* **E113, p.426:** indicator `003` = address information (street, ZIP). |
| Verified | 8 / 8 AVS request legs in AMEX and MC Credit (S3 sample: 3). |

| Program | Source ID | TCs / files | Test cases |
|---|---|---|---|
| AMEX Credit | – (new) | 8 / 8 | 002625, 002626, 002688, 002689, 002736, 002737, 002742, 002743 |
| MC Credit | S3 D-10 | 8 / 8 | 001886, 001887, 001959, 001960, 002011, 002012, 002017, 002018 |

---

## 4. Package and traceability defects

<a id="cd-19"></a>

### CD-19 – Traceability matrix references a folder that is not in the delivery (S2/S4 O31-01)

| | |
|---|---|
| Where | `reports/traceability_matrix_full.json` (and `TRACEABILITY.md`, `traceability_matrix.md`) in every program folder |
| Generated | Every trace row points to `qe_shaped_test_data/TD-TC-xxxxxx…json` with `"file_exists": true` |
| Actual delivery | There is no `qe_shaped_test_data/` folder; files are in `test_data/`. Only one TD file per TC is referenced, so follow-on legs are never traced. |
| Fix | Rebuild every trace representation from the final delivered paths, one entry per request leg; compute `file_exists` against the delivered package. |

| Program | Trace rows (path missing, `file_exists: true`) | TD files delivered | TD files never traced |
|---|---|---|---|
| AMEX Credit | 374 | 790 | 416 |
| MC Credit | 408 | 839 | 431 |
| MC Fleet | 249 | 569 | 320 |
| Interlink Debit | 249 | 536 | 287 |
| Maestro Direct Debit | 249 | 536 | 287 |
| **Total** | **1,529** | **3,270** | **1,741** |

Verified 2026-11-03: every referenced path is missing in all five programs; figures identical to S2.

<a id="cd-20"></a>

### CD-20 – MC Credit package mixes two builds (S2 O31-03)

| | |
|---|---|
| Current export | `test_cases/test_cases.jsonl` = **450 TCs**; `test_data/` = **839 TD files** |
| Embedded archive | `BR_SCE_Test_cases.zip` → `test_cases/test_cases.jsonl` = **241 TCs** (older build, no TD files) |
| Documentation | `mastercard_credit/README.md` describes the previous 241-TC delivery |
| Fix | Ship one authoritative build with a manifest (build ID, TC count, TD count, hashes); remove or label the old archive; regenerate README / DELIVERY_CONTRACT. |
| Verified | 450 vs 241 lines confirmed. |

---

## 5. Review items (data may be acceptable – confirmation needed)

<a id="rv-01"></a>

### RV-01 – Keyed / COF / `79` follow-on expiry sent as YYMM while the original is MMYY (was S1 A-04 and A-03b)

| | |
|---|---|
| What the data does | Original keyed/COF request: E12 = MMYY (e.g. `1231`). Follow-on (reversal, TOR, completion, cancellation, void return): E12 = same date as **YYMM** (`3112`). |
| Why S1 called it a defect | E12 (p.372) keyed expiry is MMYY, and *"In the event of a 'Retry' of a 'Follow-on' transaction … use the same credentials for the retry as were used in the initial attempt."* |
| Why it is now REVIEW | **10.1.8.2 (p.94–95)**, written for *"all electronic credit transactions"*: the host message *"should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)"* and this *"applies only to … Credit Completions, Credit POS Purchase Reversals, Credit Time Out Reversals"*. So YYMM on these follow-ons is what the spec describes. The "same credentials" sentence covers a **retry of a follow-on**, not a follow-on vs the original purchase. S3 also lists the stored-credential expiry format as open (Q-15). |
| Still open | 10.1.8.2 does not list **cancellations** (`S`) and **void of return** (`U`), and does not say whether it applies when the original was keyed. |
| A-03b | The `79` follow-ons (S1 A-03b, 32 files) are resolved together with CD-05; no separate fix. |
| Owner | BUYPASS / SME |

Counts below are the keyed (`01`) and Credential-on-File (`10`) follow-ons; AMEX matches S1 A-04 exactly (70 files).

| Program | TCs / files | Test cases |
|---|---|---|
| AMEX Credit | 58 / 70 | 002610, 002611, 002613–002618, 002620, 002622, 002626, 002628, 002629, 002633, 002634, 002636, 002676–002681, 002683, 002685, 002689, 002691, 002692, 002694, 002695, 002697, 002734, 002735, 002737, 002740, 002741, 002743, 002801–002810, 002828–002835, 002850, 002853, 002868, 002874 |
| MC Credit | 60 / 72 | 001871, 001872, 001874–001879, 001881, 001883, 001887, 001889, 001890, 001896, 001897, 001899, 001942–001947, 001954, 001956, 001960, 001962, 001963, 001967, 001968, 001970, 002009, 002010, 002012, 002015, 002016, 002018, 002081–002091, 002111–002119, 002135, 002138, 002153, 002159 |
| MC Fleet | 34 / 44 | 002284–002289, 002291, 002293, 002297, 002299, 002300, 002302, 002338–002343, 002345, 002347, 002351, 002353, 002354, 002356, 002423–002427, 002440–002444 |

<a id="rv-02"></a>

### RV-02 – Fleet Data Segment (Segment 101) length `035` (was S4 FL-01, R-07)

| | |
|---|---|
| Generated | `035` = 21 + 14 Field Separators for empty fields 5–18 (including 5 Fleet Tags) |
| Readings | `021`: trailing empty fields not sent (12.1 p.223). `030`: keep separators for fields 5–13 (12.2 note p.226) but none for Fleet Tags (p.227: *"Sent only in Auth Completion message"*). `035`: keep all 18 slots (p.226 note). No Fleet example in the spec; KB marks it SME TBA. |
| Verified | 492 of 497 Fleet request files carry `035` (244 TCs). |
| Owner | SME / BUYPASS |

### Other review items (merged from S2 R-01 – R-21, S3 Q-01 – Q-26, S4 R-01 – R-17)

| ID | Item | Programs | Reference (2026-4) | Source IDs | Owner |
|---|---|---|---|---|---|
| RV-03 | Exact E192 value / E118 framing for Table 001 (`EMVYES` alone = `006`, or framed with Table ID + Table Length) – affects the CD-02 fix | All | App. T p.701 | S2 R-14 · S3 Q-01 · S4 R-13 | BUYPASS / SME |
| RV-04 | Packed-BCD 2-byte prefix (`0145`) inside E190 counted in E189 (`147`); matches App. R but the boundary is not stated | All | E189/E190 p.477–478; App. R p.697 | S2 R-15 · S4 R-14 | SME |
| RV-05 | CA Public Key File Checksum `2012091700000100000102171` is a QE sample | All EMV | E187 p.476 | S2 R-16 · S3 Q-02 · S4 R-15 | Host CA key file |
| RV-06 | Cryptogram / IAD / ATC / UN / TVR are placeholders; TVR byte 3 "online PIN entered" set on non-PIN transactions | All EMV | App. R; E190 p.478 | S2 R-17 · S3 Q-03, Q-04 · S4 R-16 | Card simulator |
| RV-07 | Placeholder `<NonfuelAmount from step 1 response>`: the response has no Nonfuel Amount; the value comes from E6 Approved Amount (9 digits, zero-filled) | AMEX, MC Credit | 11.1.2 p.170; E6 p.368 | S2 R-11 | AI team |
| RV-08 | Terminal Identifier `JX72948216050257001` is a placeholder (State Code `72` = Yukon); TCs marked `ready` anyway | All | E102 p.417; App. D p.517 | S2 R-02, R-03 · S3 Q-08, Q-09 · S4 R-02, R-03 | Client / BUYPASS |
| RV-09 | Outdated / inexact citations: Table 005 cited p.548 (now App. I p.555, App. J p.638–639); p.511 quote not verbatim; split-tender quote from healthcare 10.1.6.1.3 (p.87) instead of 10.1.2.1 (p.84); Approval Number cites p.85 (type `S`) instead of E5 p.368 | All | see items | S2 R-01, R-04, R-06, R-08 · S3 Q-05 – Q-07, Q-10, Q-12 · S4 R-01, R-09 | AI team |
| RV-10 | Partial Approval Indicator `1` on reversal/TOR legs – ATL105 silent | All | E121 p.440 | S2 R-09 · S4 R-11 | SME |
| RV-11 | Totals Segment Length `108` only matches with a trailing Field Separator after Field 17; Totals Password (E65) empty; Totals HW/SW/FW versions synthesized | All | 12.6 p.235–237; E65 p.396 | S2 R-18 – R-20 | SME / merchant profile |
| RV-12 | Completion legs sent without Segment 130 – ATL105 exempts only reversals (p.161) | All | p.161; E5 p.368 | S2 R-12 · S3 Q-17 | SME |
| RV-13 | Contactless chip data identical to the contact template (no 9F6E / 9F6D / 9F7C / 9F66) | All contactless | App. R p.697 | S2 R-13 · S3 Q-13 | Contactless kernel trace |
| RV-14 | Resubmitted completion Sequence Number: new number (p.40) vs authorization's number (E86 p.409, 2.3.4 p.47) | All | E86 p.409; p.40; p.47 | S3 Q-18, Q-24 | BUYPASS / SME |
| RV-15 | Expected Response Code left open (`?`) in some TCs | MC Credit | E83 p.404 | S3 Q-16 | Test case owner |
| RV-16 | Request identical to another TC except the Sequence Number (difference is device-only) | MC Credit | 10.1.1.3 p.81 | S3 Q-14 | Device-level verification |
| RV-17 | Open client inputs: expired EMV card, online-PIN card + DUKPT key, partial-approval cards A/B, AVS card/address, Fleet card profile (card type 080), Fleet AID (SME-EMV-01), Fleet product type (PIPS p.56) | All | – | S2 R-07 · S3 Q-11, Q-19 – Q-23 · S4 R-04, R-05, R-08 | Client |
| RV-18 | Fleet: Service Level `F` on a non-fuel sale (E87 also offers `O`); TOR tests need a host harness and have no paired response template | MC Fleet | E87 p.410; p.40–47 | S4 R-06, R-17 | SME / harness |
| RV-19 | Completion higher than the authorization – allowed, and with what tolerance? MC credit balance inquiry – does the issuer return a balance? | MC Credit | – | S3 Q-25, Q-26 | Client |
| RV-20 | **INFO:** in follow-ons, Account Number and expiry are joined with `=` on the wire (e.g. `5199141082730948=2708`) | All | 10.1.8.2 p.94 | S2 R-10 · S4 R-12 | Wire builder |
| RV-21 | MC Credit `test_cases.jsonl` lines 3–4 (TC-001722, TC-001725) are malformed JSON (`{{` / missing `{`) – re-confirmed 2026-11-03 | MC Credit | – | S2 R-21 | AI team |
| RV-22 | Observation: entry mode `82` (mobile) requests without Segment 130 (84 legs) | AMEX | – | S1 observation | SME |

---

## 6. Verification notes (differences from the source reports)

| # | Finding | Effect in this log |
|---|---|---|
| 1 | S2 and S3 are both MasterCard Credit reports but **reuse the same IDs for different defects** (e.g. S2 D-06 = Totals Card Label, S3 D-06 = Table 005 vs 031; S2 D-08 = Segment 102, S3 D-08 = entry mode 79). | Single numbering CD-xx / RV-xx; cross-reference in §7. |
| 2 | S1 A-04 (70 rows) and A-03b (32 rows) conflict with ATL105 10.1.8.2 (p.94–95), which specifies YYMM storage format for credit completions, reversals and TORs. | Moved to review **RV-01**; A-03b is fixed by CD-05. |
| 3 | S3 reviewed a **sample** (118 of 450 MC Credit TCs). Re-run on the full delivery: Table 030 158 TCs (S3: 46), COF tables 54 (20), entry mode 79 47 (11), runtime amounts 31 (14), AVS 8 (3), unmatched TOR with track data 5 (2). | Full-delivery counts used. |
| 4 | S2 reviewed EMV + 5 magstripe TCs. Full delivery: Totals Card Label 72 legs (S2: 18), zero-count category 8 (2), Segment 102 6 (4), Track 1 test 4 TCs (1). | Full-delivery counts used. |
| 5 | S1 did not check Table 030, COF tables, AVS Table 003, Segment 102, zero-count totals, unmatched-TOR track data or follow-on runtime amounts. All of these also fail in AMEX. | Added to CD-03, CD-04, CD-06, CD-07, CD-09, CD-11, CD-18. |
| 6 | S4 covered 5 Fleet TCs. The same rules fail across the whole Fleet delivery (E188/E118 60 TCs, Segment 102 249 TCs), plus Table 030, COF tables, entry mode 79, Totals Card Label, zero-count totals, unmatched TOR and Track 1. | Fleet columns use full-delivery counts. |
| 7 | All other source figures reproduced exactly: AMEX A-01/A-02 116, A-03 54, A-05 72, A-06/A-07/A-08/A-09/A-10; MC Credit E188/E118 126 files / 104 TCs; O31-01 1,529 rows / 1,741 untraced files; O31-02 500 files / 402 TCs; O31-03 450 vs 241. | – |

---

## 7. Source ID cross-reference

| Source | Source ID | Consolidated ID |
|---|---|---|
| S1 AMEX Credit | A-01, A-02, A-03, A-03b, A-04, A-05, A-06, A-07, A-08, A-09, A-10 | CD-01, CD-02, CD-05, RV-01 (fixed by CD-05), RV-01, CD-10, CD-07, CD-08, CD-12, CD-14, CD-17 |
| S2 MC Credit (11-02) | D-01, D-02, D-03, D-04, D-04b, D-05, D-06, D-07, D-08, D-09, D-10 | CD-01, CD-02, CD-12, CD-13, CD-14, CD-08, CD-10, CD-07, CD-09, CD-15, CD-11 |
| S2 MC Credit (11-02) | M-01, M-02, M-03, M-04, O31-01, O31-02, O31-03 | CD-16, CD-17, CD-07, CD-07, CD-19, CD-01, CD-20 |
| S3 MC Credit (11-03) | D-01, D-02, D-03, D-04, D-05, D-06, D-07 | CD-01, CD-02, CD-12, CD-13, CD-07 + CD-08, CD-17, CD-16 |
| S3 MC Credit (11-03) | D-08, D-09, D-10, D-11, D-12, D-13, D-14 | CD-05, CD-04, CD-18, CD-14, CD-03, CD-06, CD-07 |
| S4 MC Fleet | FL-01, FL-02, FL-03, FL-04, O31-01 | RV-02, CD-09, CD-01, CD-02, CD-19 |

---

## 8. What is correct (passes in all checked test data)

| Area | Result |
|---|---|
| E55 `ATL105`; E63 = segments present; message type per leg | PASS (all programs) |
| Segment 100: 17 fields in spec order; Segment Length correct wherever no runtime placeholder is involved | PASS (except CD-07) |
| E78 Prompt Code per leg (`0`, `3`, `7`, `E`, `8`, `Z`, `S`, `U`, Totals `990`); card types `020` Credit, `080` MC fleet | PASS |
| E2 PAN: Luhn, correct IIN/length per card scheme; Track 2 combined ≤ 37 | PASS (except CD-16) |
| E44 `0`; E86 Sequence Number unique, carried on follow-ons; E121 `0`/`1`/`5`; E5 empty on purchases | PASS |
| Segment 111 length; Table 005 entry mode = TC leg; Table 031 present | PASS (except CD-03, CD-04, CD-17, CD-18) |
| Segment 130 only on chip-read EMV requests; E187 N25; E189 = chip byte count; TLV well-formed; 5F2A/9F1A `0840` | PASS (except CD-01, CD-02, CD-06) |
| Follow-on legs: same card, terminal, sequence number and amount relation as the original | PASS (expiry format: RV-01) |
| Fleet Segment 101 fields (Odometer, Vehicle Number) and Segment 102 values (code, UoM, quantity, price, amount = Nonfuel Amount) | PASS (lengths: CD-09, RV-02) |

---

## 9. Generator fix checklist (to prevent repeats in the next run)

| # | Rule for the generator | Fixes |
|---|---|---|
| 1 | Pad fixed-length AN fields to full width and emit the same value used for the Segment Length: E188 → `001`, E13 → `TE  ` / `CC  `. | CD-01, CD-10 |
| 2 | Never synthesize free text for coded fields; apply appendix values (App. T: `EMVYES`/`EMVNOT`). Do not skip `valid_values_parsed`; do not reuse random values across TCs. | CD-02 |
| 3 | Segment 111 per transaction type: Table 030 on every EMV and COF request; Tables 032-08 / 049-02 (+ 056-09 for MasterCard) on every COF step; Table 003 on AVS tests; Table 005 digit 3 consistent with Table 031. | CD-03, CD-04, CD-17, CD-18 |
| 4 | Card data per entry mode and step role: `01`/`79` → MMYY only; `80` for swiped fallback; follow-ons → Account Number + YYMM only; Track 1 tests → `B…^…` data. | CD-05, CD-06, CD-16 |
| 5 | Runtime amounts: derive from step-1 E6; provisional values ≤ 7 digits for MasterCard and smaller than the original; calculate Segment Length after substitution. | CD-07 |
| 6 | Build chip data per leg from that leg's Segment 100: 9F02 = total, 9F03 = cash, 9C from transaction type (`00` / `09` cashback / `20` return / `31` balance inquiry). | CD-08, CD-15 |
| 7 | Segment-specific separator rules (Seg 102: FS after type/length, `\` after quantity/price, FS after the last amount). | CD-09 |
| 8 | Totals: send card-type categories only when count ≥ `00001`. | CD-11 |
| 9 | Data must prove the test objective (expired card, online/offline PIN CVM); if client input is missing, mark the TC **blocked**. | CD-12, CD-13, CD-14 |
| 10 | Traceability from final delivered paths, one entry per request leg; one authoritative build per program with a manifest. | CD-19, CD-20 |
| 11 | Cite 2026-4 pages with verbatim quotes. | RV-09 |

### How the verification was run

A Python check (kept outside the repository) loaded every `test_cases.jsonl` record (repairing MC Credit lines 3–4 in memory) and every TD leg of `american_express_credit`, `mastercard_credit` and `mastercard_fleet`, applied the rules above per leg, and counted test cases and files. Traceability was checked by resolving every `test_data_file` path in `reports/traceability_matrix_full.json` against the program folder. The AMEX figures can also be reproduced with [build_amex_defect_log.py](./AMEX_CREDIT_Validations/build_amex_defect_log.py).
