# ATL105 Test Data Validation – Findings Summary – Merchandise Return – Credential on File

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Merchandise Return |
| Scenario | BR1_TS27 Merchandise Return – Credential on File (scenario status: unconfirmed) |
| Entry mode | Credential on File (Table 005 PAN entry mode `10` = credential on file) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 437, 438) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 2 of the 3 test cases of this scenario (T1, T5), 3 test data files (1 multi-step flow). Not sampled: 1 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **2 / 2 test cases FAIL** – 2 defects common to every test case (D-09, D-12) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 7, U for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.546 |
| Card data (Elements 2 + 12) | stored credential (10): expiry only, no track data; follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Appendix J p.638, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 030 (POS Condition Code) for EMV and Credential on File transactions, Tables 032/049/056 (Credential on File indicators); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Appendix I p.568, Appendix I p.575, Appendix I p.589, Section 10.13.2.4.2 p.144 |
| Test objective | The data can prove what the scenario claims: void of a return | Appendix G p.546 |
| Step-to-step links | Sequence Number, Account Number, terminal and entry mode against the original step | Section 2.3.1 p.40, Appendix J p.638, Element 86 p.409 |
| Expected response codes | Expected Response Code of each step fits the step; open ('?') codes are listed | Element 83 p.404 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the test case executed against the test data | TC catalog |
| Generation fidelity | Every test data value compared with the test case request value | TC catalog |
| Spec citations | Every `spec_basis` quote and cited page of the test case checked against the 2026-4 text | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note for the message builder.

---

## 2. Results by test case

| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |
|---|---|---|---|---|---|---|---|
| T1 – Merchandise Return processed | TC-002158 (BR1_TS27_TC1) | 1 | **FAIL** | 2 | 0 | 5 | D-09, D-12 |
| T5 – Merchandise Return → Void of a Merchandise Return | TC-002159 (BR1_TS27_TC2) | 2 | **FAIL** | 4 | 1 | 5 | D-09, D-12 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

### D-09 – Credential on File indicators (Tables 032, 049, 056) are missing

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002158 | 1 (return) | TD-TC-002158.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| TC-002159 | 1 (return) | TD-TC-002159.step1.return.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| TC-002159 | 2 (void return) | TD-TC-002159.step2.void-return.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |

**Why (TC-002158):** Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).

Spec:
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, p.575 (printed Appendix I-26): *"It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."*
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, p.589 (printed Appendix I-40): *"This field is required for all subsequent Credential on File transactions."*
- Section 10.13.2.4.2 Stored Credential Use Cases, p.144 (printed 10-65): *"Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type."*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.638 (printed Appendix J-1): *"10 Credential on file"*

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002158 | 1 (return) | TD-TC-002158.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002159 | 1 (return) | TD-TC-002159.step1.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002159 | 2 (void return) | TD-TC-002159.step2.void-return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |

**Why (TC-002158):** This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.

Spec:
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

---

## 4. Scenario-specific defects

None.

### Follow-on steps checked against their original step

#### T5 – Merchandise Return → Void of a Merchandise Return (TC-002159)

Step 2 (void return) against step 1 (return): D-09 and D-12 apply to step 1; step 2 has D-09 and D-12.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `U020` (Transaction Type 'U' = Void of a Merchandise Return; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021591` (original `021591`) | PASS |
| Same Account Number as the original | `5134227363006278` (original `5134227363006278`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original MMYY 0829 -> 2908) | `2908` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |

---

## 5. What passes

Aggregated over all 3 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `02` | – |
| Segment 100 | Segment Type `100`; Segment Length `078`/`084`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `7020`/`U020`; Account Number `5195918548510137`/`5134227363006278`; Card Discretionary Block Data `1228`/`0829`/`2908`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Nonfuel Amount `1500`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `021581`/`021591`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Approval Number (BLOCKED in TC-002159 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `101`; Variable Information (Table 031) `12` | – |
| Step-to-step links | 4 of 4 checks PASS | – |
| Expected response codes | 1 of 1 PASS (`0` = Approved - Purchase/Capture) | – |
| TC-declared `rule_checks` | 15 of 15 hold | – |
| TC-declared `link_defs` | 3 of 3 hold; 0 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 6 of 8 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5134227363006278=2908.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Expiry format for stored credentials is not stated | The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies. | TC-002158, TC-002159 | Element 12, PDF p.372 (printed 13-32); Section 10.1.8.2, PDF p.94 (printed 10-15) | BUYPASS / SME |
| Q-02 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-002158, TC-002159 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-03 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002158, TC-002159 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-04 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002158, TC-002159 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-05 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002158, TC-002159 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - Credential on File (entry mode 10): add Table 032 sub-table 08 (`C`, or `I` on the transaction that stores the credential), Table 049 sub-table 02 (Transaction Initiation Indicator) and Table 056 sub-table 09 (MasterCard MIT/CIT category) (D-09).
2. **Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request (10 digits, values from Appendix Q, for the certified terminal) – Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* (D-12).
3. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-02).
4. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-03, Q-04). The data values are not affected.
5. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-05).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS27 | TC-002158 | T1 – Merchandise Return processed | FAIL | [TC-002158_ATL105_Field_Validation.md](./TC-002158_ATL105_Field_Validation.md) |
| BR1_TS27 | TC-002159 | T5 – Merchandise Return → Void of a Merchandise Return | FAIL | [TC-002159_ATL105_Field_Validation.md](./TC-002159_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
