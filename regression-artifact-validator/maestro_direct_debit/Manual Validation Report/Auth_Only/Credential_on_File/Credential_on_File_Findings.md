# ATL105 Test Data Validation – Findings Summary – Authorization Only – Credential on File

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – Maestro Direct Debit – Authorization Only |
| Scenario | BR1_TS13 Authorization Only – Credential on File (scenario status: unconfirmed) |
| Entry mode | Credential on File (Table 005 PAN entry mode `10` = credential on file) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `maestro_direct_debit/test_data` |
| Test case catalog | `maestro_direct_debit/test_cases/test_cases.jsonl` (lines 244, 245, 246, 247, 248, 249, 250) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | All 7 test cases of this scenario (T1, T5 ×3, T8, T10, T11), 13 test data files (5 multi-step flows); 1 device-only test case without a host message (ATL105 citations checked only) |
| Date | 2026-11-05 |
| Overall result | **6 / 7 test cases FAIL** (1 REVIEW) – 4 defects common to every test case (D-09, D-12, D-15, D-16), plus 3 scenario-specific defects (D-19, D-20, D-21) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 3, S, Z for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | stored credential (10): expiry only, no track data; follow-on steps send the truncated expiry YYMM only; card-number check digit | Appendix J p.638, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 030 (POS Condition Code) for EMV and Credential on File transactions, Tables 032/049/056 (Credential on File indicators); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Appendix I p.568, Appendix I p.575, Appendix I p.589, Section 10.13.2.4.2 p.144 |
| Debit rules (Section 10.2) | Card type 011 in the Prompt Code; PIN block (Element 33, DUKPT) on every request except the Purchase Reversal and follow-on messages; no PIN block on follow-on messages; no manual (keyed) entry; no Time-out Reversal of a Completion | Section 10.2.1.3 p.96, Section 10.2.1.4 p.96, Section 10.2.5.1 p.105, Section 10.2.2 p.97, Appendix E p.521 |
| Totals Request (Segment 105, 17 fields) | Data Section 1 + Segment 105 only; presence, type, length and value of every field (Prompt Code 990, Totals Date, versions, Sequence Number, Card Label 4 characters); Segment Length (14 Field Separators, none in the Category Total Data Section); count, amount and grand total against the approved steps of the flow (Section 9.2) | Section 11.4.1.1 p.178, Section 12.6 p.235, Section 12.6 p.237, Element 13 p.372, Element 16 p.375, Element 15 p.374, Element 42 p.386, Element 105 p.419, Section 9.2 p.72 |
| Test objective | The data can prove what the scenario claims: completion of an authorization, cancellation, time-out reversal | Element 5 p.368, Section 10.1.2.2 p.85, Section 2.3 p.39, Section 2.3.1 p.40 |
| Step-to-step links | Sequence Number, Account Number, terminal and entry mode against the original step | Section 2.3.1 p.40, Appendix J p.638, Element 86 p.409, Section 10.1.2.2 p.85 |
| Expected response codes | Expected Response Code of each step fits the step; open ('?') codes are listed | Element 83 p.404 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the test case executed against the test data | TC catalog |
| Generation fidelity | Every test data value compared with the test case request value | TC catalog |
| Spec citations | Every `spec_basis` quote and cited page of the test case checked against the 2026-4 text | TC catalog |
| Device-only test cases | TC-003412: no host message, so no test data; the ATL105 text each quotes is checked on its page and its device procedure is listed in its report | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note for the message builder.

---

## 2. Results by test case

| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |
|---|---|---|---|---|---|---|---|
| T1 – Authorization Only approved | TC-003408 (BR1_TS13_TC1) | 1 | **FAIL** | 4 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T5 – Authorization → Completion | TC-003409 (BR1_TS13_TC2) | 2 | **FAIL** | 7 | 1 | 5 | D-09, D-12, D-15, D-16 |
| T8 – Authorization → Completion → Settlement | TC-003410 (BR1_TS13_TC5) | 3 | **FAIL** | 10 | 1 | 8 | D-09, D-12, D-15, D-16, D-19, D-20, D-21 |
| T10 – Authorization released with Time-out Reversal → declined | TC-003411 (BR1_TS13_TC6) | 2 | **FAIL** | 7 | 0 | 5 | D-09, D-12, D-15, D-16 |
| T11 – Preauthorization disabled (card type 168) → not offered at the device | TC-003412 (BR1_TS13_TC7) | 0 | **REVIEW** | 0 | 0 | 1 | – |
| T5 – Debit Authorization Only → Authorization Only Reversal | TC-003413 (BR1_TS13_TC3) | 2 | **FAIL** | 7 | 1 | 4 | D-09, D-12, D-15, D-16 |
| T5 – Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | TC-003414 (BR1_TS13_TC4) | 3 | **FAIL** | 10 | 2 | 5 | D-09, D-12, D-15, D-16 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

### D-09 – Credential on File indicators (Tables 032, 049, 056) are missing

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003408 | 1 (auth only) | TD-TC-003408.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003409 | 1 (auth only) | TD-TC-003409.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003409 | 2 (completion) | TD-TC-003409.step2.completion.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003410 | 1 (auth only) | TD-TC-003410.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003410 | 2 (completion) | TD-TC-003410.step2.completion.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003411 | 1 (auth only) | TD-TC-003411.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003411 | 2 (timeout reversal) | TD-TC-003411.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003413 | 1 (auth only) | TD-TC-003413.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003413 | 2 (cancellation) | TD-TC-003413.step2.cancellation.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003414 | 1 (auth only) | TD-TC-003414.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003414 | 2 (completion) | TD-TC-003414.step2.completion.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003414 | 3 (completion resubmitted) | TD-TC-003414.step3.completion-resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |

**Why (TC-003408):** Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').

Spec:
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, p.575 (printed Appendix I-26): *"It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."*
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, p.589 (printed Appendix I-40): *"This field is required for all subsequent Credential on File transactions."*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.638 (printed Appendix J-1): *"10 Credential on file"*

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003408 | 1 (auth only) | TD-TC-003408.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003409 | 1 (auth only) | TD-TC-003409.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003409 | 2 (completion) | TD-TC-003409.step2.completion.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003410 | 1 (auth only) | TD-TC-003410.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003410 | 2 (completion) | TD-TC-003410.step2.completion.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003411 | 1 (auth only) | TD-TC-003411.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003411 | 2 (timeout reversal) | TD-TC-003411.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003413 | 1 (auth only) | TD-TC-003413.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003413 | 2 (cancellation) | TD-TC-003413.step2.cancellation.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003414 | 1 (auth only) | TD-TC-003414.step1.auth-only.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003414 | 2 (completion) | TD-TC-003414.step2.completion.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003414 | 3 (completion resubmitted) | TD-TC-003414.step3.completion-resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |

**Why (TC-003408):** This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.

Spec:
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

### D-15 – The card data (Account Number, Card Discretionary Block Data) is empty

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003408 | 1 (auth only) | TD-TC-003408.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003409 | 1 (auth only) | TD-TC-003409.step1.auth-only.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003409 | 2 (completion) | TD-TC-003409.step2.completion.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003410 | 1 (auth only) | TD-TC-003410.step1.auth-only.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003410 | 2 (completion) | TD-TC-003410.step2.completion.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003411 | 1 (auth only) | TD-TC-003411.step1.auth-only.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003411 | 2 (timeout reversal) | TD-TC-003411.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003413 | 1 (auth only) | TD-TC-003413.step1.auth-only.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003413 | 2 (cancellation) | TD-TC-003413.step2.cancellation.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003414 | 1 (auth only) | TD-TC-003414.step1.auth-only.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003414 | 2 (completion) | TD-TC-003414.step2.completion.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003414 | 3 (completion resubmitted) | TD-TC-003414.step3.completion-resubmitted.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Maestro Direct Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |

**Why (TC-003408):** This auth only must carry the Account Number and the expiry as MMYY, but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Maestro Direct Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.

Spec:
- Element 2 Account Number (Chapter 13), p.364 (printed 13-24): *"a variable length (up to 24 alphanumeric bytes) Account Number"*
- Element 12 Card Discretionary Block Data (Chapter 13), p.372 (printed 13-32): *"For a card whose number has been manually entered, the expiration date is in MMYY format"*
- Element 12 Card Discretionary Block Data (Chapter 13), p.372 (printed 13-32): *"Note: For all authorizations, merchants must send a value even for expired cards."*
- Section 10.2.5.2 Track Support Requirements, p.105 (printed 10-26): *"The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."*

### D-16 – The debit PIN block (Element 33) is empty

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003408 | 1 (auth only) | TD-TC-003408.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003409 | 1 (auth only) | TD-TC-003409.step1.auth-only.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003410 | 1 (auth only) | TD-TC-003410.step1.auth-only.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003411 | 1 (auth only) | TD-TC-003411.step1.auth-only.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003413 | 1 (auth only) | TD-TC-003413.step1.auth-only.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003414 | 1 (auth only) | TD-TC-003414.step1.auth-only.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |

**Why (TC-003408):** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This auth only (Transaction Type 3) sends no PIN block.

Spec:
- Section 10.2.1.3 PIN Entry, p.96 (printed 10-17): *"All transactions—except the Purchase Reversal transaction—require PIN entry."*
- Section 10.2.1.3 PIN Entry, p.96 (printed 10-17): *"When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."*
- Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"Required for debit and EBT."*
- Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."*

---

## 4. Scenario-specific defects

### D-19 – Totals Request: Card Label (Element 13) is not 4 characters

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003410 | 3 (totals) | TD-TC-003410.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |

**Why (TC-003410):** Element 13 is a fixed field of four characters, left-aligned and space-filled. 'DB' has 2, so the fields after it shift by 2 on the wire.

Spec:
- Element 13 Card Label (Chapter 13), p.372 (printed 13-32): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*
- Element 13 Card Label (Chapter 13), p.373 (printed 13-33): *"Debit DB"*

### D-20 – Totals Request: Segment 105 length is counted with the wrong layout

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003410 | 3 (totals) | TD-TC-003410.step3.totals.json : 7 | `108` | 107 |

**Why (TC-003410):** Fields 1-14 add up to 76 characters, plus 14 Field Separators = 90. Fields 15-17 (the Category Total Data Section) are not separated: Card Label 4 (fixed width) + Count 5 + Amount 8 = 17. The Segment Length is therefore 107, not '108'. '108' is what you get with a Field Separator after every one of the 17 fields and the 2-character Card Label, as in Segment 100.

Spec:
- Section 12.6 Totals Data Segment, p.235 (printed 12-18): *"Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14."*
- Section 12.6 Totals Data Segment, p.235 (printed 12-18): *"Field Nos. 15–17 are not separated by Field Separators."*
- Element 13 Card Label (Chapter 13), p.372 (printed 13-32): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*
- Element 84 Segment Length (Chapter 13), p.406 (printed 13-66): *"This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."*

### D-21 – Totals Request: the totals do not match the transactions of the flow

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003410 | 3 (totals) | TD-TC-003410.step3.totals.json : 21 | `count 00001, amount 00000000, grand total 00000000` | Count 00001, amount 00001500, grand total 00001500 |

**Why (TC-003410):** Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 completion $15.00: count +1, amount +$15.00. Card Type Total Amount should be 00001500 ($15.00), the file has 00000000; Grand Total should be 00001500, the file has 00000000.

Spec:
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"*
- Section 12.6 Totals Data Segment, p.237 (printed 12-20): *"Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."*

### Follow-on steps checked against their original step

#### T5 – Authorization → Completion (TC-003409)

Step 2 (completion) against step 1 (auth only): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0011` (Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `034091` (original `034091`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T8 – Authorization → Completion → Settlement (TC-003410)

Step 2 (completion) against step 1 (auth only): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0011` (Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `034101` (original `034101`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T10 – Authorization released with Time-out Reversal → declined (TC-003411)

Step 2 (timeout reversal) against step 1 (auth only): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `034111` (original `034111`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T5 – Debit Authorization Only → Authorization Only Reversal (TC-003413)

Step 2 (cancellation) against step 1 (auth only): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `S011` (Transaction Type 'S' = Cancellation; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `034131` (original `034131`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |

#### T5 – Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved (TC-003414)

Step 2 (completion) against step 1 (auth only): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0011` (Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `034141` (original `034141`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

Step 3 (completion resubmitted) against step 2 (completion): D-09, D-12 and D-15 apply to step 2; step 3 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0011` (Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `034141` (original `034141`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 2 amount | `1500` (step 2 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 2 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |

---

## 5. What passes

Aggregated over all 13 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`/`Totals Request`; Message Format Version Identifier `ATL105`; Number of Segments `02`/`01` | – |
| Segment 100 | Segment Type `100`; Segment Length `058`/`064`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `3011`/`0011`/`Z011`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Nonfuel Amount `1500`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `034081`/`034091`/`034101`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Account Number (FAIL in TC-003408 step 1; FAIL in TC-003409 step 1; FAIL in TC-003409 step 2; FAIL in TC-003410 step 1; FAIL in TC-003410 step 2; FAIL in TC-003411 step 1; FAIL in TC-003411 step 2; FAIL in TC-003413 step 1; FAIL in TC-003413 step 2; FAIL in TC-003414 step 1; FAIL in TC-003414 step 2; FAIL in TC-003414 step 3); Card Discretionary Block Data (FAIL in TC-003408 step 1; FAIL in TC-003409 step 1; FAIL in TC-003409 step 2; FAIL in TC-003410 step 1; FAIL in TC-003410 step 2; FAIL in TC-003411 step 1; FAIL in TC-003411 step 2; FAIL in TC-003413 step 1; FAIL in TC-003413 step 2; FAIL in TC-003414 step 1; FAIL in TC-003414 step 2; FAIL in TC-003414 step 3); Encrypted PIN Block Data (FAIL in TC-003408 step 1; FAIL in TC-003409 step 1; FAIL in TC-003410 step 1; FAIL in TC-003411 step 1; FAIL in TC-003413 step 1; FAIL in TC-003414 step 1); Approval Number (BLOCKED in TC-003409 step 2; BLOCKED in TC-003410 step 2; BLOCKED in TC-003413 step 2; BLOCKED in TC-003414 step 2; BLOCKED in TC-003414 step 3) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `101`; Variable Information (Table 031) `12` | – |
| Segment 105 (Totals Request) | Segment Type `105`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `990`; Employee Number `(empty)`; Password `(empty)`; Totals Date `222222`; Hardware Version `N73RGAHT`; Software Version `73TXILSD`; Firmware Version `YTULOKQF`; Sequence Number `034102`; Currency Code `840`; Grand Total `00000000`; Card Type Total Count `00001`; Card Type Total Amount `00000000` | Segment Length (FAIL in TC-003410 step 3); Card Label (FAIL in TC-003410 step 3) |
| Step-to-step links | 24 of 25 checks PASS | Totals match the transactions of the flow (Section 9.2) (FAIL in TC-003410) |
| Expected response codes | 10 of 10 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions, `2` = Approved - Authorization only) | – |
| TC-declared `rule_checks` | 62 of 62 hold | – |
| TC-declared `link_defs` | 17 of 17 hold; 6 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 27 of 34 found word-for-word on the cited page | p.512: Not word-for-word: all its words are on the cited page, but not in this form |

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-003408, TC-003409, TC-003410, TC-003411, TC-003413, TC-003414 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-02 | The quote cited for p.512 is not word-for-word in ATL105 | The test case quotes "40 Maestro Direct - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form. | TC-003408, TC-003409, TC-003410, TC-003411, TC-003412, TC-003413, TC-003414 | – | Test case generator (citation) |
| Q-03 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-003408, TC-003409, TC-003410, TC-003411, TC-003413, TC-003414 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-04 | Credential on File with a PIN debit card is not described for this network | ATL105 requires PIN entry on every debit transaction except the Purchase Reversal, but a stored-credential transaction has no cardholder at the terminal to enter a PIN. ATL105 describes debit Credential on File transactions (bill payment, recurring, installment) only for Accel and STAR (Element 12). | TC-003408, TC-003409, TC-003410, TC-003411, TC-003413, TC-003414 | Section 10.2.1.3, PDF p.96 (printed 10-17); Element 12, PDF p.372 (printed 13-32) | BUYPASS / SME |
| Q-05 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0). | TC-003409, TC-003410, TC-003414 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.545 (printed G-1); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-06 | The device version fields of the Totals Request are generated values | ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device. | TC-003410 | Element 43, PDF p.386 (printed 13-46); Element 96, PDF p.414 (printed 13-74); Element 39, PDF p.384 (printed 13-44) | Client (versions of the certified device) |
| Q-07 | The Authorization Only step has no AO bucket in the totals | Section 12.6 lists Preauthorization (AO) as bucket 4, and the grand total leaves the AO bucket out. This flow has an approved Authorization Only, but only the DB bucket is sent. ATL105 says cancellations subtract from the AO bucket; it does not say whether a completed authorization stays in it. | TC-003410 | Section 12.6, PDF p.237 (printed 12-20); Section 9.2, PDF p.72 (printed 9-8) | BUYPASS / SME |
| Q-08 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-003410, TC-003411 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-09 | Variable-cut test merchant. | Listed by the test case itself as an open item (input needed before execution). | TC-003410 | – | Client / test case owner |
| Q-10 | Terminal table load with card type 168 (client). | Listed by the test case itself as an open item (input needed before execution). | TC-003412 | – | Client / test case owner |
| Q-11 | Simulator able to withhold a Completion response and return Decline Code 24. | Listed by the test case itself as an open item (input needed before execution). | TC-003414 | – | Client / test case owner |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - Credential on File (entry mode 10): add Table 032 sub-table 08 (`C`, or `I` on the transaction that stores the credential) and Table 049 sub-table 02 (Transaction Initiation Indicator) (D-09).
2. **Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request (10 digits, values from Appendix Q, for the certified terminal) – Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* (D-12).
3. **Element 2 Account Number and Element 12 Card Discretionary Block Data:** Fill in the Account Number and Element 12 from the client's test card: the data read from the card for card-read entry modes, Account Number + expiry YYMM on follow-on messages, the expiry for stored credentials. Until the card profile is supplied, write an explicit placeholder (for example `<CLIENT_CARD_PAN>`) and keep the test case blocked – never an empty string. (D-15).
4. **Element 33 Encrypted PIN Block Data:** Every debit request except the Purchase Reversal and the follow-on messages: Element 33 = DUKPT Key Serial Number + PIN block (Section 10.2.1.3). Until the client supplies the test PIN and DUKPT key, write an explicit placeholder and keep the test case blocked. (D-16).
5. **Element 13 Card Label (Totals Request):** Card Label is a fixed 4-character field, left-aligned and space-filled: `DB` → `DB  ` (two trailing spaces). Recalculate the Segment Length afterwards (D-20). (D-19).
6. **Element 84 Segment Length (Totals Request):** Segment 105 length = Fields 1–14 + 14 Field Separators + the Category Total Data Section, which has no separators (Card Label 4 + Count 5 + Amount 8 = 17) (Section 12.6). Do not count a separator after Fields 15–17 and use the 4-character Card Label (D-19). (D-20).
7. **Elements 16, 15, 42 Card Type Total Count, Card Type Total Amount, Grand Total:** Compute the device totals from the steps before the Totals Request (Section 9.2): approved Purchase/Capture or Completion +1 / +amount; approved Reversal −1 / −amount; Merchandise Return +1 / −amount; Authorization Only into the AO bucket, which the grand total leaves out. A purchase that never got a response and was reversed is not in the totals. A card bucket whose net count is zero is not sent. (D-21).
8. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-08).
9. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-01, Q-02, Q-05). The data values are not affected.
10. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-03).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS13 | TC-003408 | T1 – Authorization Only approved | FAIL | [TC-003408_ATL105_Field_Validation.md](./TC-003408_ATL105_Field_Validation.md) |
| BR1_TS13 | TC-003409 | T5 – Authorization → Completion | FAIL | [TC-003409_ATL105_Field_Validation.md](./TC-003409_ATL105_Field_Validation.md) |
| BR1_TS13 | TC-003410 | T8 – Authorization → Completion → Settlement | FAIL | [TC-003410_ATL105_Field_Validation.md](./TC-003410_ATL105_Field_Validation.md) |
| BR1_TS13 | TC-003411 | T10 – Authorization released with Time-out Reversal → declined | FAIL | [TC-003411_ATL105_Field_Validation.md](./TC-003411_ATL105_Field_Validation.md) |
| BR1_TS13 | TC-003412 | T11 – Preauthorization disabled (card type 168) → not offered at the device | REVIEW | [TC-003412_ATL105_Field_Validation.md](./TC-003412_ATL105_Field_Validation.md) |
| BR1_TS13 | TC-003413 | T5 – Debit Authorization Only → Authorization Only Reversal | FAIL | [TC-003413_ATL105_Field_Validation.md](./TC-003413_ATL105_Field_Validation.md) |
| BR1_TS13 | TC-003414 | T5 – Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | FAIL | [TC-003414_ATL105_Field_Validation.md](./TC-003414_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py --data "maestro_direct_debit" --all
```
