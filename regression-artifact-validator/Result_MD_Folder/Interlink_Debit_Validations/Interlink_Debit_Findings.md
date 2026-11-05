# ATL105 Test Data Validation – Interlink Debit – Findings Summary

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – Interlink Debit – Purchase/Capture |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-3** (`specifications/ATL105/docs/specs`) |
| Test data under review | `C:\Users\F5QGZOJ\Downloads\31-10-out\interlink_debit\test_data` |
| Test case catalog | `C:\Users\F5QGZOJ\Downloads\31-10-out\interlink_debit\test_cases\test_cases.jsonl` |
| Delivery contract | `C:\Users\F5QGZOJ\Downloads\31-10-out\interlink_debit\DELIVERY_CONTRACT.md` – 278 test cases, 536 test-data files, 0 unresolved references |
| Method | Manual field-by-field validation against the ATL105 2026-3 PDF (Elements 58/78/83/111/113/116/118/121/188/191/192, Appendix I, J, T, Z) + catalog-wide `grep` sweeps to size each finding, following the same approach used for American Express Credit and Mastercard Credit |
| Scope of this pass | Purchase/Capture only (`BR1_TS1`–`BR1_TS7`, the 7 entry modes below); **5 test cases sampled per entry mode (35 total)**, same T1/T2/T4/T5/T8 pattern used for Amex and Mastercard. Authorization Only, Merchandise Return and Balance Inquiry scenario groups are out of scope for this pass. |
| Date | 2026-11-04 |
| Overall result | **7 confirmed defects** (ID-01 – ID-07); 2 are EMV-only (hit 2 of 7 entry modes), 2 are delivery-wide (hit all 7 entry modes), 1 is Contactless Magnetic Stripe-only, 1 is Credential on File-only, and 1 is a test-design gap present in every entry mode's "expired card" scenario |

---

## 1. Entry modes in the Interlink Debit Purchase/Capture delivery

| # | Entry mode | Scenario | Table 005 code(s) seen | TCs | TD files | Status |
|---|---|---|---|---|---|---|
| 1 | EMV Contact Chip | `BR1_TS1` | `051` | 29 | 60 | needs_client_input 18 · requires_review 10 · ready 1 |
| 2 | EMV Contactless Chip | `BR1_TS2` | `071`, `861` | 30 | 61 | needs_client_input 19 · requires_review 10 · ready 1 |
| 3 | Magnetic Stripe | `BR1_TS3` | `901` | 30 | 60 | pending_sme 30 |
| 4 | Contactless Magnetic Stripe | `BR1_TS4` | `911` | 29 | 60 | pending_sme 29 |
| 5 | Chip Fallback | `BR1_TS5` | `791` (keyed), `801` (swiped) | 30 | 61 | pending_sme 30 |
| 6 | Credential on File | `BR1_TS6` | `101` | 29 | 60 | pending_sme 29 |
| 7 | Contactless Mobile Commerce Device | `BR1_TS7` | `821` | 29 | 60 | pending_sme 29 |
| | **Total** | | | **206** | **373** | |

All seven entry modes keep `AccountNumber`, `CardDiscretionaryBlockData` and `EncryptedPINBlockData` **blank by design** — per `DELIVERY_CONTRACT.md`, the debit PAN and DUKPT-encrypted PIN block are client-supplied inputs (`awaiting_inputs`), not generator output. Blank PAN/PIN fields are **not** findings in this report; everything below is a problem in content the generator itself produced.

---

## 2. Sample and results (5 test cases per entry mode)

Each entry mode's sample covers the base purchase, the scenario specific to that entry mode (cashback or an entry-mode condition code), partial approval, a reversal, and settlement — the same T1/T2/T4/T5/T8 pattern used for Amex and Mastercard.

| Entry mode | TC | Template / scenario | Legs | Result | Defects hit |
|---|---|---|---|---|---|
| EMV Contact | TC-002887 | T1 Purchase approved | 1 | FAIL | ID-01, ID-02 |
| EMV Contact | TC-002912 | T2 Expired debit card → sent online → issuer decides | 1 | FAIL | ID-01, ID-02, ID-04 |
| EMV Contact | TC-002914 | T4 Cashback, partially approved | 1 | FAIL | ID-01, ID-02, ID-03 |
| EMV Contact | TC-002911 | T5 Purchase → Purchase Reversal (no PIN) | 2 | FAIL | ID-01, ID-02 (leg 1) |
| EMV Contact | TC-002893 | T8 Purchase → Settlement | 2 | FAIL | ID-01, ID-02 (leg 1), ID-05 (leg 2) |
| EMV Contactless | TC-002916 | T1 Purchase approved | 1 | FAIL | ID-01, ID-02 |
| EMV Contactless | TC-002917 | T2 Contactless chip → contact chip (86) | 1 | FAIL | ID-01, ID-02 |
| EMV Contactless | TC-002944 | T4 Cashback, partially approved | 1 | FAIL | ID-01, ID-02, ID-03 |
| EMV Contactless | TC-002941 | T5 Purchase → Purchase Reversal (no PIN) | 2 | FAIL | ID-01, ID-02 (leg 1) |
| EMV Contactless | TC-002923 | T8 Purchase → Settlement | 2 | FAIL | ID-01, ID-02 (leg 1), ID-05 (leg 2) |
| Magnetic Stripe | TC-002946 | T1 Purchase approved | 1 | PASS | — |
| Magnetic Stripe | TC-002971 | T2 Expired debit card → sent online → issuer decides | 1 | REVIEW | ID-04 |
| Magnetic Stripe | TC-002973 | T4 Cashback, partially approved | 1 | PASS | — |
| Magnetic Stripe | TC-002970 | T5 Purchase → Purchase Reversal (no PIN) | 2 | PASS | — |
| Magnetic Stripe | TC-002952 | T8 Purchase → Settlement | 2 | FAIL | ID-05 (leg 2) |
| Contactless Magnetic Stripe | TC-002976 | T1 Purchase approved | 1 | FAIL | ID-06 |
| Contactless Magnetic Stripe | TC-003001 | T2 Expired debit card → sent online → issuer decides | 1 | FAIL | ID-06, ID-04 |
| Contactless Magnetic Stripe | TC-003003 | T4 Cashback, partially approved | 1 | FAIL | ID-06 |
| Contactless Magnetic Stripe | TC-003000 | T5 Purchase → Purchase Reversal (no PIN) | 2 | FAIL | ID-06 (both legs) |
| Contactless Magnetic Stripe | TC-002982 | T8 Purchase → Settlement | 2 | FAIL | ID-06 (leg 1), ID-05 (leg 2) |
| Chip Fallback | TC-003005 | T1 Purchase approved | 1 | PASS | — |
| Chip Fallback | TC-003019 | T2 Chip unreadable → swiped fallback (80) | 1 | PASS | — |
| Chip Fallback | TC-003033 | T4 Cashback, partially approved | 1 | PASS | — |
| Chip Fallback | TC-003030 | T5 Purchase → Purchase Reversal (no PIN) | 2 | PASS | — |
| Chip Fallback | TC-003011 | T8 Purchase → Settlement | 2 | FAIL | ID-05 (leg 2) |
| Credential on File | TC-003035 | T1 Purchase approved | 1 | FAIL | ID-07 |
| Credential on File | TC-003060 | T2 Expired debit card → sent online → issuer decides | 1 | FAIL | ID-07, ID-04 |
| Credential on File | TC-003062 | T4 Cashback, partially approved | 1 | FAIL | ID-07 |
| Credential on File | TC-003059 | T5 Purchase → Purchase Reversal (no PIN) | 2 | FAIL | ID-07 (both legs) |
| Credential on File | TC-003041 | T8 Purchase → Settlement | 2 | FAIL | ID-07 (leg 1), ID-05 (leg 2) |
| Contactless Mobile | TC-003064 | T1 Purchase approved | 1 | PASS | — |
| Contactless Mobile | TC-003089 | T2 Expired debit card → sent online → issuer decides | 1 | REVIEW | ID-04 |
| Contactless Mobile | TC-003091 | T4 Cashback, partially approved | 1 | PASS | — |
| Contactless Mobile | TC-003088 | T5 Purchase → Purchase Reversal (no PIN) | 2 | PASS | — |
| Contactless Mobile | TC-003070 | T8 Purchase → Settlement | 2 | FAIL | ID-05 (leg 2) |

### 2.1 Result by entry mode

| Entry mode | Sample result | Defects found |
|---|---|---|
| EMV Contact Chip | **5 / 5 FAIL** | ID-01, ID-02 on every EMV request; ID-03 on the cashback TC; ID-04 on the expired-card TC; ID-05 on settlement |
| EMV Contactless Chip | **5 / 5 FAIL** | ID-01, ID-02; ID-03; ID-04; ID-05 |
| Magnetic Stripe | 1 / 5 FAIL, 1 / 5 REVIEW | ID-05 on settlement; ID-04 on the expired-card TC (review) |
| Contactless Magnetic Stripe | **5 / 5 FAIL** | ID-06 on every sampled TC; ID-04 on the expired-card TC; ID-05 on settlement |
| Chip Fallback | 1 / 5 FAIL | ID-05 on settlement only; no entry-mode-specific content defect found in the sample |
| Credential on File | **5 / 5 FAIL** | ID-07 on every sampled TC; ID-04 on the expired-card TC; ID-05 on settlement |
| Contactless Mobile Commerce Device | 1 / 5 FAIL, 1 / 5 REVIEW | ID-05 on settlement; ID-04 on the expired-card TC (review) |

---

## 3. Defects

Certainty: **Certain** = a direct mismatch against an ATL105 rule, confirmed from the PDF text and the actual test-data bytes. **Design gap** = the generator did what it was told, but the test case cannot prove the thing it claims to test.

### ID-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 *(same defect as Amex A-01 / Mastercard D-01)*

| | |
|---|---|
| Where | Every EMV request, `"EMV Request Data Segment" → "EMVCardSequenceNumber"` |
| Generated | `01` |
| Expected | `001` |
| Spec | Element 188, PDF p.470 (printed p.13-137): *"Fixed length of three bytes, right-aligned and padded with zeroes … Note: If EMV Tag 5F34 is not provided by the card, send three spaces … Valid Codes/Values: 000—099."* |
| Sample | 10 / 10 EMV Contact + EMV Contactless TCs (TC-002887, 002912, 002914, 002911, 002893, 002916, 002917, 002944, 002941, 002923) |
| Whole catalog | **90 / 90** EMV request files across the whole Interlink Debit delivery (`grep "EMVCardSequenceNumber": "01"` → 90 files) |
| Certainty | Certain |

### ID-02 – EMV Additional Information (Element 118) is 984 random characters instead of EMV Table Data *(same defect as Amex A-02 / Mastercard D-02)*

| | |
|---|---|
| Where | Every EMV request, `EMVAdditionalInformationIndicator = "001"`, `EMVAdditionalInformationLength = "984"`, `EMVAdditionalInformation` = random A–Z/0–9 text (e.g. `XTV641IPVR6UAAXEE82DW1CD2O6ROBWW3…`) |
| Expected | Appendix T-1 (PDF p.694) Table ID 001 "EMV Table Data": *"EMVYES This code indicates that the device is able to receive EMV Table Data. It is only sent in an EMV Transaction request."* / `EMVNOT` |
| Spec chain | Element 118, PDF p.432 (printed p.13-99): *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here … Please refer to Appendix T."* |
| Sample | 10 / 10 EMV TCs |
| Whole catalog | Same 90 request files as ID-01 (both fields are set by the same EMV-request template) |
| Certainty | Certain |

### ID-03 – Cashback purchase: EMV chip data ignores the Cash Amount *(same defect class as Mastercard D-09)*

| | |
|---|---|
| Where | EMV Contact TC-002913/TC-002914 and EMV Contactless TC-002943/TC-002944 ("Debit Purchase with Cashback") — `EMVChipData` Tag `9F02` (Amount, Authorised) and Tag `9F03` (Amount, Other) |
| Generated | Standard segment `"CashAmount": "500"` ($5.00), but inside the chip blob Tag `9F02 = 000000001500` (still only the $15.00 merchandise amount) and Tag `9F03 = 000000000000` (zero) — byte-for-byte identical to the no-cashback baseline (TC-002887 / TC-002916) apart from the random Tag 9F37 Unpredictable Number |
| Expected | Tag 9F02 (Amount Authorised) should include the cashback, i.e. `000000002000` ($20.00 = $15.00 + $5.00), and Tag 9F03 (Amount, Other) should carry the cashback amount, `000000000500` |
| How verified | Parsed the hex `EMVChipData` field in all four files and diffed the TLV substrings against the matching non-cashback TC for the same entry mode; only the random UN differs |
| Sample | 4 / 4 EMV cashback test-data files (TC-002913, TC-002914, TC-002943, TC-002944) |
| Certainty | Certain — this is the only field in the EMV chip blob that should change for a cashback transaction, and it does not |

### ID-04 – "Expired debit card" test case has no rule check that can ever prove its objective

| | |
|---|---|
| Where | The `T2` "Expired debit card → sent online (no device expiry check) → issuer decides" test case in every entry mode (e.g. TC-002912 EMV Contact, TC-002942 EMV Contactless, TC-002971 Magnetic Stripe, TC-003001 Contactless Magnetic Stripe, TC-003031 Chip Fallback, TC-003060 Credential on File, TC-003089 Contactless Mobile) |
| Observation | `AccountNumber`/`CardDiscretionaryBlockData` are correctly blank (`awaiting_inputs` lists "Account Number – awaiting a client-supplied value", and the catalog's own `flags` field adds `NEEDS_CLIENT_INPUT: Expired debit test card.` — this part is by design, not a defect). But the test case's `rule_checks` array only asserts generic entry-mode / transaction-type / Table 005 / Table 031 fields; none of them assert an expiry condition, a decline/referral response, or any field that would differ once the client supplies the expired card. For the EMV entry modes, the EMV chip blob itself also carries no Tag `5F24` (Application Expiration Date) and is otherwise byte-identical to the "Purchase approved" baseline except the random Unpredictable Number. |
| Why it matters | Even after the client supplies the "expired debit test card," there is nothing in the generated test case that will fail if the file is run with a non-expired card instead — the test case cannot verify its own named objective |
| Sample | 7 / 7 sampled "expired card" TCs across all 7 entry modes |
| Certainty | Design gap (certain that the rule_checks/chip data carry no expiry assertion; not a claim that the blank PAN/PIN themselves are wrong) |

### ID-05 – Totals Request Card Label (Element 13, Segment 105 Field 15) is 2 bytes instead of the spec's fixed 4 bytes

| | |
|---|---|
| Where | Every `"Totals Request"` test-data file, `"Totals Data Segment" → "CardLabel"` |
| Generated | `"DB"` (2 characters) |
| Expected | 4-byte fixed field, so `"DB  "` (`DB` + 2 trailing spaces) |
| Spec | Chapter 12.6, "Totals Data Segment Format," Data Segment No. 105, PDF p.234 (printed p.12-20): *"15 13 Card Label 4 R Identifies the card type … Source: Device"* — Field 15 has Len. **4**; the Debit bucket label is `(DB)` per the Category Total Data Section list (5. Debit (DB)). The Totals Request message legitimately carries this field (Field Nos. 15–17 are sent by the device, chapter 11.4.1.1, PDF p.175) |
| Sample | 7 / 7 sampled T8 settlement TCs (one per entry mode: TC-002893, TC-002923, TC-002952, TC-002982, TC-003011, TC-003041, TC-003070) |
| Whole catalog | **63 / 63** Totals Request files in the Interlink Debit delivery (`grep "CardLabel": "DB"` → 63 files) |
| Certainty | Certain |

### ID-06 – Contactless Magnetic Stripe entry mode uses Terminal PIN Entry Capability code `1` instead of the spec-reserved `8`

| | |
|---|---|
| Where | Every Contactless Magnetic Stripe test-data file, `"Variable Information Data Segment" → VariableInformationIndicator "005"`, value `911` |
| Generated | Position 3 (Terminal PIN Entry Capability Mode) = `1` ("PIN entry capability, including both EMV Contact and Contactless") |
| Expected | Position 3 = `8` ("Contactless Magnetic Stripe Read Capability") |
| Spec | Appendix J, PDF pp.631–632: Pos. Nos. 1–2 code `91` = *"Full contactless magnetic stripe read with track data sent to the host."* Position 3 table footnote: *"If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8 … The PIN Entry Capability Mode code 8 is reserved only for Contactless MSR."* Code `1` is reserved for EMV Contact/Contactless devices only ("For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8"). |
| Sample | 5 / 5 sampled Contactless Magnetic Stripe TCs (TC-002976, TC-003001, TC-003003, TC-003000 both legs, TC-002982 both legs) |
| Whole catalog | **67 / 67** test-data files carrying Table 005 value `911` in the Interlink Debit delivery |
| Certainty | Certain |

### ID-07 – Credential on File transactions never carry the required Initial/Subsequent COF indicator

| | |
|---|---|
| Where | Every Credential on File test-data file — the `"Variable Information Data Segment"` only ever contains Table IDs `005` (POS Entry Mode = `10`, correct) and `031` (Terminal Capability) |
| Missing | Data Element 111, Table ID **032** ("POS Additional Information Indicator" / "POS Additional Data Information"), Sub-Table ID `08`, value `I` (Initial COF establishment) or `C` (Subsequent Credential on File) |
| Spec | Appendix I, PDF p.562 (printed Appendix I-20) confirms Table ID `032` is "POS Additional Information Indicator." Sub Table ID 08 "Purpose: Initial/Credential on File Indicator," valid values `I`/`C` (PDF pp.567–568, printed Appendix I-25/26): *"It is merchant's responsibility to send the indicator as Initial ('I') or Subsequent Credential on File ('C') from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."* Appendix Z (Stored Credential), PDF pp.706–707, repeats the same requirement: *"Data Element 111, Table ID 32 – POS Additional Data Information, Sub table ID 08 should have value 'C' for Subsequent Credential on File"* and confirms POS Entry Mode `10` (which this delivery does use) is specifically the code for these transactions. |
| Sample | 7 / 7 sampled Credential on File test-data files (TC-003035, TC-003060, TC-003062, TC-003059 both legs, TC-003041 both legs) |
| Whole catalog | Table ID `032` appears in **0 / 536** test-data files in the whole Interlink Debit delivery, while **67** files use the Credential on File entry-mode code `101` — none of them carry the required indicator |
| Certainty | Certain that Table ID 032/Sub-Table 08 is required and absent. The spec names the table twice with a one-digit difference in surrounding prose ("POS Additional Information Indicator" vs "POS Additional Data Information") but both citations agree on Table ID 032 and Sub-Table 08, so this is not a case of two different tables. |

---

## 4. What was checked and found correct (no defect)

- **Chip Fallback keyed vs. swiped split (Table 005 `791`/`801`)**: the baseline, cashback and reversal TCs correctly use `791` (keyed fallback) and the one TC that specifically exercises "Chip unreadable → swiped fallback" (TC-003019) correctly uses `801`, matching Appendix J. No contradiction was found in this sample (unlike the Amex A-03 case), because Interlink Debit's `AccountNumber`/`CardDiscretionaryBlockData` are blank client inputs on every entry mode, so there is no generated track/PAN content to contradict the entry-mode code.
- **Credential on File POS Entry Mode**: correctly uses `10` (not `02`/`90`), matching the Appendix J footnote and Appendix Z guidance for subsequent COF transactions.
- **`NonfuelAmount`/`CashAmount` representation**: Element 58, PDF p.386 (printed p.13-53), defines Nonfuel Amount as *variable* length up to 8 digits, so unpadded values like `"1500"`/`"500"` are correct; they are not supposed to be zero-padded like the fixed 8-byte Totals fields.
- **EMV chip data length**: `EMVChipDataLength` (147) matches the actual byte length of the `EMVChipData` hex string in every sampled EMV file.
- **`PartialApprovalIndicator: "1"`** on every request is the terminal's partial-approval *capability* flag, not a claim that the specific transaction is partially approved. Element 121, PDF p.433 (printed p.13-100): *"Purpose: Indicates whether or not a merchant supports credit/debit/fleet Partial Approvals. Processing Rules: Required on all transactions … 1 Supported — This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions."* Present on both partial and non-partial TCs by design, not a defect.

---

## 5. Recommendations

1. Fix the shared EMV-request template so `EMVCardSequenceNumber` is zero-padded to 3 bytes (ID-01) and `EMVAdditionalInformation` carries `EMVYES`/`EMVNOT` instead of random filler (ID-02). This single template fix clears 90 files across EMV Contact and EMV Contactless.
2. Fix the cashback branch of the EMV chip-data generator so Tag 9F02 includes the cashback amount and Tag 9F03 carries the cashback amount instead of zero (ID-03).
3. Add a rule check to the "Expired debit card" scenario template (all 7 entry modes) that asserts an expiry-dependent response/decline condition, so the test case can fail if a non-expired card is substituted (ID-04).
4. Pad `CardLabel` to 4 bytes (`"DB  "`) in the Totals Request template (ID-05) — clears 63 files.
5. Correct the Contactless Magnetic Stripe entry-mode template to send Terminal PIN Entry Capability code `8` instead of `1` (ID-06) — clears 67 files.
6. Add Data Element 111, Table ID 032, Sub-Table 08 (`C`, since these are all subsequent/stored-credential uses) to the Credential on File template (ID-07) — clears 67 files.
