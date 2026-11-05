# ATL105 Test Data Validation – American Express Credit – Findings Summary

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – American Express Credit – Purchase/Capture |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-3** (`specifications/ATL105/docs/specs`) |
| Test data under review | `C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit\test_data` |
| Test case catalog | `C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit\test_cases\test_cases.jsonl` |
| Validator | `regression-artifact-validator` – `TestDataSpecFieldValidationRunner` (extended for American Express and all 8 entry modes) + Appendix R / T / I / J oracles, Appendix D / E / G catalogs |
| Scope | **40 test cases** (5 per entry mode × 8 entry modes), **72 test data files** |
| Date | 2026-11-03 |
| Overall result | **28 / 40 FAIL**, **12 / 40 PASS_WITH_REVIEW**, 0 clean PASS |

**2026-11-04 correction:** A-04's expiry-format finding is withdrawn for the three checked sample flows after direct verification against the ATL105 2026-3 PDF and all six source test-data files. The related A-03 follow-on statement and generator recommendation are corrected below. The overall totals, per-TC F/R counts, entry-mode summaries, linked TC reports and defect log above/below remain historical run output, not recalculated results. The wider 70 + 32 follow-on population and validator behavior require revalidation before publishing revised totals.

Per-test-case evidence: [TC_Reports/README.md](./TC_Reports/README.md) (index) and the 40 `TC_Reports/TC-xxxxxx_ATL105_Field_Validation.md` reports. Sample ID list: [sample_tc_ids.txt](./sample_tc_ids.txt).

**How to read a TC report:** start with the section **"What is wrong in this test case and how to fix it"**. Each defect gives its location (test data file, line number and JSON path), the value the AI generated, what is wrong with it in plain words, the value it should have, and the spec quote. Next comes **"Items to confirm (not defects)"**. The raw field-by-field check tables under **"Technical evidence"** are only needed for audit.

---

## 1. Entry modes in the American Express Credit delivery

The delivery contains **416 test cases** and **790 test data files** in **8 entry modes**.

| # | Entry mode (name) | Code in catalog | Table 005 values used | Test cases | With test data | TD files | Status breakdown |
|---|---|---|---|---|---|---|---|
| 1 | EMV Contact Chip | `EMV_CONTACT` | `051` | 51 | 47 | 99 | ready 23 · requires_review 18 · needs_client_input 10 |
| 2 | EMV Contactless Chip | `EMV_CONTACTLESS` | `071`, `861` | 51 | 47 | 99 | ready 23 · requires_review 18 · needs_client_input 10 |
| 3 | Magnetic Stripe | `MAGSTRIPE` | `901`, `908`, `021` (follow-ons) | 55 | 49 | 101 | ready 29 · requires_review 17 · needs_client_input 9 |
| 4 | Contactless Magnetic Stripe | `CONTACTLESS_MAGSTRIPE` | `911` | 48 | 42 | 93 | ready 24 · requires_review 17 · needs_client_input 7 |
| 5 | Manual / Keyed | `MANUAL` | `011`, `018` | 64 | 55 | 111 | ready 33 · requires_review 20 · needs_client_input 10 · blocked_spec_gap 1 |
| 6 | Chip Fallback | `EMV_FALLBACK` | `791`, `801` | 50 | 46 | 97 | ready 26 · requires_review 17 · needs_client_input 7 |
| 7 | Credential on File | `CREDENTIAL_ON_FILE` | `101` | 51 | 46 | 97 | ready 18 · requires_review 16 · pending_sme 12 · needs_client_input 4 · blocked_spec_gap 1 |
| 8 | Contactless Mobile Commerce Device | `CONTACTLESS_MOBILE` | `821` | 46 | 42 | 93 | **pending_sme 46** |
| | **Total** | | | **416** | **374** | **790** | |

TCs without test data (42) are device-only scenarios with no host message.

---

## 2. Sample and results (5 test cases per entry mode)

For each entry mode the sample covers the base purchase, the scenario specific to that entry mode, split tender, reversal and settlement or time-out reversal. This is the same T1–T5 approach used for Mastercard.

| Entry mode | TC | Template / scenario | Legs | Result | F / R |
|---|---|---|---|---|---|
| EMV Contact | TC-002471 | T1 Purchase approved | 1 | FAIL | 4 / 8 |
| EMV Contact | TC-002472 | T2 Expired EMV card | 1 | FAIL | 5 / 9 |
| EMV Contact | TC-002473 | T3 EMV offline PIN | 1 | FAIL | 5 / 8 |
| EMV Contact | TC-002474 | T4 Split tender | 2 | FAIL | 11 / 21 |
| EMV Contact | TC-002478 | T5 Purchase → Reversal | 2 | FAIL | 4 / 14 |
| EMV Contactless | TC-002505 | T1 Purchase approved | 1 | FAIL | 4 / 9 |
| EMV Contactless | TC-002506 | T2 Contactless → contact (86) | 1 | FAIL | 4 / 8 |
| EMV Contactless | TC-002507 | T2 Expired EMV card | 1 | FAIL | 5 / 10 |
| EMV Contactless | TC-002508 | T4 Split tender | 2 | FAIL | 11 / 23 |
| EMV Contactless | TC-002512 | T5 Purchase → Reversal | 2 | FAIL | 4 / 15 |
| Magnetic Stripe | TC-002539 | T1 Purchase approved | 1 | PASS_WITH_REVIEW | 0 / 1 |
| Magnetic Stripe | TC-002540 | T2 Swiped at RFID-equipped terminal | 1 | FAIL | 1 / 1 |
| Magnetic Stripe | TC-002541 | T4 Split tender | 2 | FAIL | 1 / 8 |
| Magnetic Stripe | TC-002545 | T5 Purchase → Reversal | 2 | PASS_WITH_REVIEW | 0 / 7 |
| Magnetic Stripe | TC-002551 | T8 Purchase → Settlement | 2 | FAIL | 1 / 4 |
| Contactless MSR | TC-002575 | T1 Purchase approved | 1 | PASS_WITH_REVIEW | 0 / 1 |
| Contactless MSR | TC-002576 | T4 Split tender | 2 | FAIL | 1 / 8 |
| Contactless MSR | TC-002580 | T5 Purchase → Reversal | 2 | PASS_WITH_REVIEW | 0 / 7 |
| Contactless MSR | TC-002581 | T6 No response → Time-out Reversal | 2 | PASS_WITH_REVIEW | 0 / 2 |
| Contactless MSR | TC-002586 | T8 Purchase → Settlement | 2 | FAIL | 1 / 4 |
| Manual / Keyed | TC-002606 | T1 Purchase approved | 1 | PASS_WITH_REVIEW | 0 / 1 |
| Manual / Keyed | TC-002607 | T2 3 failed swipes → keyed | 1 | PASS_WITH_REVIEW | 0 / 1 |
| Manual / Keyed | TC-002609 | T4 Split tender | 2 | FAIL | 1 / 8 |
| Manual / Keyed | TC-002613 | T5 Purchase → Reversal | 2 | FAIL | 2 / 7 |
| Manual / Keyed | TC-002619 | T8 Purchase → Settlement | 2 | FAIL | 1 / 4 |
| Chip Fallback | TC-002643 | T1 Purchase approved | 1 | FAIL | 2 / 1 |
| Chip Fallback | TC-002644 | T2 Chip unreadable → keyed fallback (79) | 1 | FAIL | 2 / 1 |
| Chip Fallback | TC-002645 | T4 Split tender | 2 | FAIL | 5 / 8 |
| Chip Fallback | TC-002649 | T5 Purchase → Reversal | 2 | FAIL | 3 / 7 |
| Chip Fallback | TC-002655 | T8 Purchase → Settlement | 2 | FAIL | 3 / 4 |
| Credential on File | TC-002675 | T1 Purchase approved | 1 | PASS_WITH_REVIEW | 0 / 1 |
| Credential on File | TC-002676 | T5 Purchase → Reversal | 2 | FAIL | 2 / 7 |
| Credential on File | TC-002677 | T6 No response → Time-out Reversal | 2 | FAIL | 2 / 2 |
| Credential on File | TC-002682 | T8 Purchase → Settlement | 2 | FAIL | 1 / 4 |
| Credential on File | TC-002688 | T9 Purchase with AVS mismatch | 1 | PASS_WITH_REVIEW | 0 / 2 |
| Contactless Mobile | TC-002703 | T1 Purchase approved | 1 | PASS_WITH_REVIEW | 0 / 2 |
| Contactless Mobile | TC-002704 | T4 Split tender | 2 | FAIL | 1 / 10 |
| Contactless Mobile | TC-002708 | T5 Purchase → Reversal | 2 | PASS_WITH_REVIEW | 0 / 8 |
| Contactless Mobile | TC-002709 | T6 No response → Time-out Reversal | 2 | PASS_WITH_REVIEW | 0 / 3 |
| Contactless Mobile | TC-002714 | T8 Purchase → Settlement | 2 | FAIL | 1 / 5 |

F = FAIL count, R = REVIEW count (individual checks).

### 2.1 Result by entry mode

| Entry mode | FAIL | PASS_WITH_REVIEW | Main defects |
|---|---|---|---|
| EMV Contact Chip | **5 / 5** | 0 | A-01, A-02 on every EMV request; A-06, A-07, A-08, A-09 by scenario |
| EMV Contactless Chip | **5 / 5** | 0 | A-01, A-02; A-06, A-07, A-08 |
| Magnetic Stripe | 3 / 5 | 2 | A-10, A-06, A-05 |
| Contactless Magnetic Stripe | 2 / 5 | 3 | A-06, A-05 |
| Manual / Keyed | 3 / 5 | 2 | A-04, A-06, A-05 |
| Chip Fallback | **5 / 5** | 0 | A-03 on every leg with `79`; A-06, A-05 |
| Credential on File | 3 / 5 | 2 | A-04, A-05 |
| Contactless Mobile Commerce Device | 2 / 5 | 3 | A-06, A-05. All 46 TCs are `pending_sme` (Q-03). |

---

## 3. Defects

Certainty: **Certain** = a direct spec rule is violated. **High** = the spec rule is clear, but the scenario wording leaves a small room for interpretation.

### A-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 *(same as Mastercard D-01)*

| | |
|---|---|
| Where | Every EMV request, `"EMV Request Data Segment" → "EMVCardSequenceNumber"` |
| Generated | `01` |
| Expected | `001` |
| Spec | 13 E188 (p.470): *"Fixed length of three bytes, right-aligned and padded with zeroes … Valid Codes/Values: 000—099"* |
| Sample | 10 / 10 EMV Contact + Contactless TCs |
| Whole catalog | **116 / 116** EMV request files |
| Certainty | Certain |

### A-02 – EMV Additional Information (Element 118) contains 984 random characters *(same as Mastercard D-02)*

| | |
|---|---|
| Where | Every EMV request, Element 191 = `001`, Element 192 = `984`, Element 118 = random A–Z/0–9 text (e.g. `UUZPL4ZEX557C41THEQ0…`) |
| Expected | EMV Table Data **`EMVYES`** / **`EMVNOT`** (Appendix T-1, p.694) |
| Spec chain | E191 `001` = EMV table data → E118 *"EMV table data is included here … refer to Appendix T"* → App. T-1 request values `EMVYES` / `EMVNOT` |
| Sample | 10 / 10 EMV TCs (3 FAIL checks each: E118 value + App. T oracle table-id + table-data) |
| Whole catalog | **116 / 116** EMV request files |
| Certainty | Certain that the content is invalid. The exact E192 value depends on Q-01. |

### A-03 – Chip Fallback uses POS entry `79` (fallback to **manual**) but sends **swiped Track 2 data** *(new for Amex)*

| | |
|---|---|
| Where | Chip Fallback test data, Table 005 `791` and Element 12 = full Track 2 (e.g. TC-002643 `310720162917830` = expiry 3107 + service code 201 + discretionary data) |
| Spec | App. J-1 (p.631): **`79` = "Contact and Contactless chip fallback to manual"**; **`80` = "Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)"**. 13 E12 (p.13-32): *"For a card whose number has been manually entered, the expiration date is in MMYY format"*. |
| Contradiction | `79` says the PAN was keyed. Track 2 data (service code + discretionary data) can only come from a swipe. The two fields contradict each other. |
| Expected | Swiped fallback (T1 purchase and most flows): Table 005 **`801`** + Track 2 data. Keyed fallback (TC-002644 *"Chip unreadable → keyed fallback (79)"*): **`791`** + Element 12 = **MMYY expiry only** (e.g. `0731`). |
| Follow-on correction (2026-11-04) | The previously reported `2809` on TC-002649's reversal means September 2028 in YYMM. Section 10.1.8.2 specifies YYMM for credit purchase reversals; do not change it to `0928` solely because the original entry mode was `79`. This corrects the format assertion, not an independent revalidation of that TC or its original purchase data. See A-04 evidence below. |
| Sample | 5 / 5 Chip Fallback TCs (TC-002643, 2644, 2645 both legs, 2649, 2655) |
| Whole catalog | **54 / 54** legs with `79` carry Track 2 data. The generator uses `80` correctly in only 2 TCs. |
| Certainty | Certain |

### A-04 – Withdrawn: MMYY purchase / YYMM credit follow-on is not itself a defect

**Revalidated on 2026-11-04.** The original finding incorrectly applied the manual-entry MMYY rule to credit reversals and timeout reversals, without considering their transaction-specific YYMM rule. It also misapplied the retry rule. The three sample date-format findings are withdrawn; this is not a clean PASS for every other field in those TCs.

#### Direct ATL105 specification evidence

Source: [BUYPASS Platform ATL105 Message Format Specifications 2026-3](../../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf), release dated August 07, 2026. The PDF itself was read for this correction, not just the knowledge-base summary. PDF page numbers below are 1-based; printed section-page numbers are also provided.

| Requirement | Location | Exact relevant wording |
|---|---|---|
| Manual-entry expiry | Element 12, Valid Codes/Values; PDF p.365, printed p.13-32 | "For a card whose number has been manually entered, the expiration date is in MMYY format, and the other required data depends upon the application." |
| Follow-up representation | Section 10.1.1.2, Track Support; PDF p.79, printed p.10-2 | "All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile." |
| Credit follow-on expiry and account delimiter | Section 10.1.8.2, Track Support Requirements; PDF p.92, printed p.10-15 | "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." |
| No additional discretionary data | Continuation of the preceding sentence, PDF pp.92-93, printed pp.10-15 to 10-16 | "No other Card Discretionary Data field follows the 4-digit Expiration Date." |

Section 10.1.8.2 on PDF p.93 explicitly lists the applicable credit transactions: **Credit Completions, Credit POS Purchase Reversals, Credit Time Out Reversals, Credit POS Cancellations, and Credit Cancellations**. Section 10.1.8 explicitly includes American Express in its electronic-credit scope.

**Why the formats differ:** the specification defines a manual-entry representation for the original keyed transaction and a truncated-track representation for the listed credit follow-ons. It does not contain a single sentence saying "convert every original MMYY to YYMM"; that mapping follows from these two explicit requirements. Changing representation does not change the underlying expiry date. The MMYY rule does not independently establish a COF-specific format; the COF purchase values below are the observed non-track test-data values, while the credit follow-on rule explicitly supplies the YYMM expectation.

#### Source test-data verification and date calculation

All six files below were read from the test-data directory identified at the start of this report. Each value is the actual `CardDiscretionaryBlockData` value, not a proposed replacement. The comparison parses the original as `(month = first 2 digits, year = last 2)` and the follow-on as `(month = last 2 digits, year = first 2)`, and checks that both months are in 01-12.

| TC | Source files | Original E12 interpreted as MMYY | Follow-on E12 interpreted as YYMM | Calculation / result |
|---|---|---|---|---|
| TC-002613 Manual to Reversal | `TD-TC-002613.step1.purchase.json`; `TD-TC-002613.step2.reversal.json` | `1231`: month 12, year 31 | `3112`: month 12, year 31 | `(12, 31) = (12, 31)`; both Dec 2031; same date, valid month |
| TC-002676 COF to Reversal | `TD-TC-002676.step1.purchase.json`; `TD-TC-002676.step2.reversal.json` | `0130`: month 01, year 30 | `3001`: month 01, year 30 | `(01, 30) = (01, 30)`; both Jan 2030; same date, valid month |
| TC-002677 COF to Timeout Reversal | `TD-TC-002677.step1.purchase.json`; `TD-TC-002677.step2.timeout-reversal.json` | `0628`: month 06, year 28 | `2806`: month 06, year 28 | `(06, 28) = (06, 28)`; both Jun 2028; same date, valid month |

**Result:** retain the reported follow-on expiry values `3112`, `3001`, and `2806`; do not replace them with the purchase's MMYY strings. In YYMM, `3112` has month **12**, not month 31. This check verifies date equivalence and the listed follow-on expiry format, not serialized `PAN=YYMM` delimiters or complete message conformance.

#### Why the retry quote did not prove A-04

Element 12 on PDF p.365 (printed p.13-32) says: "the merchant should use the same credentials for the retry as were used in the initial attempt." Its preceding context is a **retry of a follow-on transaction**, with a reference to Appendix AD, Real Time Account Updater. It compares a follow-on retry with the initial attempt of that follow-on; it does not require the original purchase and its reversal to have identical four-character expiry representations.

**Scope limitation:** the previously reported **70** Manual/COF follow-on legs plus **32** follow-ons of `79` transactions are historical candidate counts, not revalidated defects or verified passes. Reassess them by transaction type, source expiry, account/token identity and retry context. The specification separately notes TransArmor-Verifone Edition and offline/in-flight exceptions; do not generalize this three-flow check to those profiles without reviewing their applicable rules. The linked [defect log](./AMEX_Credit_Defect_Log.md), TC reports and validator have not been regenerated or changed by this documentation correction.

### A-05 – Totals Card Label is 2 bytes instead of 4 (Element 13) *(same as Mastercard D-06)*

| | |
|---|---|
| Where | Every Totals Request leg, `"Totals Data Segment" → "CardLabel"` |
| Generated | `TE`. This is the correct American Express bucket (ATL105 p.69: *"TE American Express"*). |
| Expected | `TE  ` (TE + 2 spaces) |
| Spec | 13 E13 (p.365): fixed length of four alphanumeric characters, left-aligned, space-filled |
| Sample | 6 / 6 settlement TCs (TC-002551, 2586, 2619, 2655, 2682, 2714) |
| Whole catalog | **72 / 72** Totals legs (9 per entry mode) |
| Certainty | Certain |

### A-06 – Split-tender step 2 Segment Length built from an impossible amount *(Amex variant of Mastercard D-05/D-07)*

| | |
|---|---|
| Where | Step 2 (`*.step2.split-tender.json`) of every split-tender TC. `NonfuelAmount` is correctly a runtime expression (`<RemainingBalance = …>`), but `SegmentLength` was calculated with the TC's provisional amount. |
| Generated | `SegmentLength` `092` (swiped/chip) / `081` (keyed). This implies an **8-digit** amount. The TC provisional values are `74141657`, `84966759`, `38371908`, `54525183`, `48621242`, `15681335`, `74475683` (e.g. $741,416.57). |
| Why it fails for Amex | Element 58 allows 8 digits for American Express, so the width alone is legal. But the remainder of a **$15.00** purchase can be at most `1499` (4 digits). The length must therefore be **085–088** (keyed **074–077**). No valid remainder can produce `092` / `081`. |
| Sample | 7 / 7 split-tender TCs (2474, 2508, 2541, 2576, 2609, 2645, 2704) |
| Certainty | Certain |

### A-07 – Split-tender card B chip amount = full original amount *(same as Mastercard D-05)*

TC-002474 (contact) and TC-002508 (contactless), step 2 Tag 9F02 = `000000001500` ($15.00, copied from card A). Card B pays only the remainder (< $15.00), so 9F02 must be built from the step 2 amount (SEG130-R-009, p.84). **Certain.**

### A-08 – Expired-card test uses a non-expired card *(same as Mastercard D-03)*

| TC | Element 12 expiry (YYMM) | Expected |
|---|---|---|
| TC-002472 (Contact) | `2808` = Aug 2028 | earlier than `2611` |
| TC-002507 (Contactless) | `3006` = Jun 2030 | earlier than `2611` |

The TVR "expired application" bit is not set, and Tag 5F24 is absent. **Certain** (test-objective defect). Both TCs are `needs_client_input`.

### A-09 – Offline-PIN test carries a signature CVM *(same as Mastercard D-04b)*

TC-002473 *"EMV offline PIN → approved"*: Tag 9F34 = `5E0302` (CVM 1E = signature). Expected CVM code 01/04 (offline plaintext/enciphered PIN), per EMV Book 3 Annex C3. **Certain** (test-objective defect).

### A-10 – "RFID-equipped terminal" uses terminal capability `8` on a chip-capable device *(new for Amex)*

| | |
|---|---|
| Where | TC-002540 (Magnetic Stripe, *"Swiped/keyed at RFID-equipped terminal"*): Table 005 `908`, Table 031 `12` |
| Spec | App. J-2 (p.632) footnote 1: *"If a Contactless MSR is attached to the POS device and it has **no chip capability**, then all transactions will use … code 8; … For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8. The PIN Entry Capability Mode code 8 is reserved only for Contactless MSR"*. Table 031 `12` declares a hybrid contact + contactless chip terminal. |
| Expected | Either `901` on the chip-capable terminal (Table 031 `12`), or keep `908` and declare a non-chip terminal in Table 031 |
| Whole catalog | 2 legs (MAGSTRIPE `908`, MANUAL `018`) |
| Certainty | High. The spec hierarchy is explicit; the scenario's intended terminal profile should be confirmed. |

### 3.1 Defect frequency in the sample (40 TCs)

| Defect | Description | TCs affected | Catalog-wide |
|---|---|---|---|
| A-02 | E118 random text instead of `EMVYES`/`EMVNOT` | 10 | 116 EMV requests |
| A-01 | E188 `01` instead of `001` | 10 | 116 EMV requests |
| A-06 | Split step 2 Segment Length from impossible amount | 7 | all split-tender TCs |
| A-05 | Totals Card Label `TE` instead of `TE  ` | 6 | 72 Totals legs |
| A-03 | Chip fallback `79` with swiped Track 2 data | 5 | 54 legs |
| A-04 | Keyed/COF follow-on expiry MMYY → YYMM | 3 | 70 legs (+32 A-03b) |
| A-07 | Split card B 9F02 = full amount | 2 | EMV split TCs |
| A-08 | Expired-card test with a valid card | 2 | – |
| A-09 | Offline-PIN test with signature CVM | 1 | – |
| A-10 | Capability `8` on chip-capable terminal | 1 | 2 legs |

---

## 4. What passes (all 40 TCs)

| Area | Result |
|---|---|
| Message type per leg, E55 `ATL105`, E63 = segments present, message ≤ 4,000 | PASS |
| Segment 100: 17 fields in spec order, Segment Length recomputed exactly (`088` swiped/chip, `077` keyed, `083` reversal with Approval Number, `077` time-out reversal) | PASS |
| **Amex PAN:** 15 digits, IIN 34/37, Luhn valid | PASS |
| Prompt Code `0020` / `8020` / `Z020`: Appendix G type + Appendix E `020` Credit | PASS |
| E58 Nonfuel Amount `1500` within the 8-digit Amex limit; E121 = `1` | PASS |
| **Keyed / COF purchase legs:** Element 12 = MMYY expiry only (`0127`, `0731`, `0329` …) | PASS |
| Swiped / chip legs: Track 2 expiry + service code (`201` chip, `101` magstripe), PAN + Track 2 ≤ 37 | PASS |
| Table 005 per entry mode: `051`, `071`, `861`, `901`, `911`, `011`, `101`, `821` valid in App. J; matches TC `entry_code` | PASS |
| **Magnetic stripe `90` → follow-on `02`** (TC-002545 reversal `021`) | PASS. App. J-1 footnote 1: *"They will use a PAN Entry Mode code 02 when referencing an original … in which a PAN Entry Mode code 90 was used."* |
| Table 031 populated (`12`) on all transactions | PASS (App. I-20) |
| Segment 130 only on chip-read requests (05/07/86); absent on swipe/keyed/COF/mobile and on reversals | PASS / N/A (SEG130-R-001/017) |
| EMV TLV (App. R oracle): well-formed, **AID `A000000025` (American Express RID)**, 9F02 = amount, 9C = 00, 5F2A/9F1A = 0840, 9F27 = 80, no 5A/57 | PASS |
| Totals: Prompt Code `990`, Totals Date `222222`, Card Label value `TE`, count/amount | PASS (width = A-05) |
| Follow-ons: same PAN, same PAI, Approval Number as runtime link | PASS / REVIEW |
| TC `rule_checks` and `link_defs` executed against TD | PASS (runtime links REVIEW) |
| TD value = TC request value (generation fidelity) | PASS, except runtime placeholders (REVIEW) |

---

## 5. Open questions / review items (not defects)

| ID | Item | Why it cannot be decided from the spec | Needed from |
|---|---|---|---|
| Q-01 | Exact corrected E192 / framing of E118 for Table 001 | App. T-1 never states the Table Length width (same as Mastercard Q-01) | BUYPASS / SME |
| Q-02 | Contactless chip payload (TC-002505/2507/2508/2512) | Same tag set and values as the contact template; no Expresspay-specific tags; CVM = signature. App. R tag list is not exhaustive. | Amex contactless (Expresspay) kernel trace |
| Q-03 | **Contactless Mobile Commerce Device (`82`)** | All 46 TCs are `pending_sme`. TD is sent as a non-EMV request with Track 2 data and no Segment 130/token data. ATL105 does not define the Amex mobile-wallet data profile. | SME decision before these TCs are executed |
| Q-04 | Amex default track | TC conformance note: *"American Express default track is Track 1 (p.81, p.94); Track 2 is sent … Track 1 variant pending SME (SME-CARD-01)"* | SME |
| Q-05 | AVS test TC-002688 | No AVS data (App. K code 003) in the request; TC is `needs_client_input` (open item "AVS-mismatch test card/address") | Client test card/address |
| Q-06 | Terminal Identifier `JX72948216050257001`, CA key checksum, cryptogram/ATC/UN/TVR | BUYPASS-assigned / host-specific / simulator values. TVR byte 3 "online PIN entered" is set on non-PIN transactions. | Client merchant list, host key file, card simulator |
| Q-07 | Totals Segment 105 length `108` and empty Password | Matches only with a trailing FS and a 4-byte label; Element 65 says the password is required for totals | SME / merchant profile |

---

## 6. Recommendations for the AI test data generator

1. **E188:** zero-pad Tag 5F34 to 3 bytes (`001`).
2. **E118:** for indicator `001` send `EMVYES`/`EMVNOT` (App. T-1), recompute E192 and the Segment 130 length.
3. **Chip fallback:** swiped fallback → Table 005 `80x` + Track 2. Keyed fallback → `79x` + Element 12 **MMYY only**. Never combine `79` with track data.
4. **Credit follow-ons:** apply section 10.1.8.2's **YYMM-only** expiry representation for the listed credit completions, purchase/timeout reversals and cancellations, with serialized `PAN=YYMM` and no additional discretionary data. Do not copy a keyed purchase's MMYY string unchanged. Preserve the underlying expiry/account identity; assess updater, retry and processing-profile exceptions separately. See the corrected A-04 source evidence.
5. **Totals:** pad Card Label to 4 bytes (`TE  `).
6. **Split tender:** compute Segment Length after the runtime remainder is substituted (or leave it runtime-derived). Never use a provisional amount ≥ the original. Build card B's 9F02 from card B's amount.
7. **Scenario fidelity:** expired-card tests need an expired card (and 5F24/TVR). Offline-PIN tests need CVM 01/04. "RFID terminal" tests need a Table 031 profile consistent with capability `8`.
8. Resolve Q-03 (mobile `82`) and Q-04 (Track 1) with the SME before releasing those TCs.

---

## 7. Validator changes made for this run

`TestDataSpecFieldValidationRunner` was extended so the Amex delivery and non-EMV entry modes are judged against the correct rules. Mastercard results are unchanged, except that the split-tender TCs now also report the already-known D-05/D-07 length defect.

| Change | Spec basis |
|---|---|
| Network `AMERICAN_EXPRESS` handled as Amex: 8-digit E58, PAI `5`, IIN 34/37 (15 digits), AID RID `A000000025`, Totals label `TE`, Expresspay wording | 13 E58, E121; App. R; p.69 |
| Historical implementation: Element 12 keyed expiry read as **MMYY** for POS entry 01/10/79; service-code chip check only for chip/fallback-to-swipe modes. **2026-11-04:** entry-mode-only expiry validation must be reassessed against the transaction-specific credit follow-on YYMM rule; no validator change or rerun is claimed here. | 13 E12 p.13-32; section 10.1.8.2 pp.10-15 to 10-16; corrected A-04 |
| Segment 130 absence is N/A for non-chip entry modes (was FAIL) | SEG130-R-001 |
| Follow-on `90 → 02` accepted | App. J-1 footnote 1 |
| New entry-mode consistency checks: keyed code vs track data, chip fallback `79`/`80`, capability `8` vs Table 031, mobile `82` profile (REVIEW) | App. J-1, J-2 |
| New flow check: split-tender step 2 Segment Length vs the possible remainder width; chip checks only for EMV legs | 12.1 F2; 13 E58; p.84 |
| New objective check for the AVS template (T9-PUR-AVS) | App. K code 003 |

Re-run (from `regression-artifact-validator`):

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner' "-Dexec.args=C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit\test_data C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit\test_cases\test_cases.jsonl Result_MD_Folder\AMEX_CREDIT_Validations\TC_Reports @Result_MD_Folder\AMEX_CREDIT_Validations\sample_tc_ids.txt"
```
