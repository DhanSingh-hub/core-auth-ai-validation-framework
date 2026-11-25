# ATL105 Test Data Validation – Findings Summary – Authorization Only – Contactless Mobile Commerce Device

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Authorization Only |
| Scenario | BR1_TS18 Authorization Only – Contactless Mobile Commerce Device (scenario status: unconfirmed) |
| Entry mode | Contactless Mobile Commerce Device (Table 005 PAN entry mode `82` = contactless mobile commerce device) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 400, 401, 403, 407, 409) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 5 of the 13 test cases of this scenario (T1, T4, T5, T6, T10), 11 test data files (4 multi-step flows). Not sampled: 6 further host-message test cases (sample limit 5); 1 T8 settlement (Totals Request only); 1 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **1 / 5 test cases FAIL** (2 BLOCKED, 2 REVIEW) – 1 scenario-specific defect (D-14) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 3, Z for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.371, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638 |
| Test objective | The data can prove what the scenario claims: completion of an authorization, time-out reversal | Element 5 p.368, Section 2.3 p.39, Section 2.3.1 p.40 |
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
| T1 – Authorization Only approved | TC-002121 (BR1_TS18_TC1) | 1 | **REVIEW** | 0 | 0 | 3 | – |
| T4 – Partial authorization → Completion for approved amount | TC-002122 (BR1_TS18_TC2) | 2 | **FAIL** | 3 | 2 | 5 | D-14 |
| T5 – Authorization → Completion | TC-002124 (BR1_TS18_TC4) | 2 | **BLOCKED** | 0 | 1 | 4 | – |
| T6 – Completion timed out → Time-out Reversal → Completion resubmitted | TC-002128 (BR1_TS18_TC8) | 4 | **BLOCKED** | 0 | 2 | 6 | – |
| T10 – Authorization released with Time-out Reversal → declined | TC-002130 (BR1_TS18_TC10) | 2 | **REVIEW** | 0 | 0 | 4 | – |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

None – no defect occurs in every test case of this scenario.

---

## 4. Scenario-specific defects

### D-14 – Follow-on step (completion, cancellation, reversal): amount data built from an impossible value

| TC | Step | Finding | Location | Value | Expected |
|---|---|---|---|---|---|
| TC-002122 | 2 (completion) | Segment Length was calculated from the invalid test case value | TD-TC-002122.step2.completion.json -> Standard Message Data Segment -> SegmentLength | `088` | Recalculate the Segment Length after filling in the real values (for example Approved Amount 000001000 -> amount 1000 plus a 6-character Approval Number -> Segment Length 084). |
| TC-002122 | 2 (completion) | The test case value for Nonfuel Amount is too long | test case step 2 request -> Nonfuel Amount (used to build TD-TC-002122.step2.completion.json) | `25672040` | Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |
| TC-002122 | 2 (completion) | The completion amount cannot be larger than the original authorization | test case step 2 request -> Nonfuel Amount | `25672040` | Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |

**TC-002122 in plain words:** step 1 asks for $15.00 and expects Response Code `F` (partial approval). The completion in step 2 must carry the amount from the step 1 response, so the test data correctly uses a run-time expression (`<NonfuelAmount from step 1 response>`). But the test case also holds a provisional value `25672040` = $256,720.40 (8 digits; Element 58 allows at most 7 for MasterCard), larger than the $15.00 that was asked for, and the generator computed the declared Segment Length from it.

Spec:
- Section 12.1 Standard Message Data Segment, p.223 (printed 12-6): *"Identifies the data segment's length, including Segment Type's length and Field Separators."*
- Element 84 Segment Length (Chapter 13), p.406 (printed 13-66): *"This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."*
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"A maximum length of 7 bytes is allowed for all other card types."*
- Section 10.1.2.1 Partial Approval Transaction, p.84 (printed 10-5): *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."*
- Element 121 Partial Approval Indicator (Chapter 13), p.440 (printed 13-100): *"the network can approve an amount that is less than the amount in the Authorization request"*
- Element 6 Approved Amount (Chapter 13), p.368 (printed 13-28): *"Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"*

**What the generator must do next run:** keep the follow-on amount a run-time value taken from the original step's response, make any provisional amount satisfy Element 58 (at most 7 digits, not more than the original amount), and compute the Segment Length after substitution.

### Follow-on steps checked against their original step

#### T4 – Partial authorization → Completion for approved amount (TC-002122)

Step 2 (completion) against step 1 (auth only): no defect on step 1; step 2 has D-14.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0020` (Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021221` (original `021221`) | PASS |
| Same Account Number as the original | `5158996616447676` (original `5158996616447676`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 280520110263212 -> 2805) | `2805` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 088 (using the test case value for Nonfuel Amount, Approval Number) | `088` | FAIL |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T5 – Authorization → Completion (TC-002124)

Step 2 (completion) against step 1 (auth only): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0020` (Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021241` (original `021241`) | PASS |
| Same Account Number as the original | `5167652103124448` (original `5167652103124448`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 271120144428400 -> 2711) | `2711` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T6 – Completion timed out → Time-out Reversal → Completion resubmitted (TC-002128)

Step 2 (completion) against step 1 (auth only): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0020` (Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021281` (original `021281`) | PASS |
| Same Account Number as the original | `5137775372098485` (original `5137775372098485`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 290820111451702 -> 2908) | `2908` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

Step 3 (timeout reversal) against step 2 (completion): no defect on step 2; step 3 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z020` (Transaction Type 'Z' = Time-out Reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021281` (original `021281`) | PASS |
| Same Account Number as the original | `5137775372098485` (original `5137775372098485`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 2 amount | `1500` (step 2 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original YYMM 2908 -> 2908) | `2908` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 078 | `078` | PASS |

Step 4 (completion resubmitted) against step 1 (auth only): no defect on step 1; step 4 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0020` (Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021282` (original `021281`) | REVIEW |
| Same Account Number as the original | `5137775372098485` (original `5137775372098485`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 290820111451702 -> 2908) | `2908` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T10 – Authorization released with Time-out Reversal → declined (TC-002130)

Step 2 (timeout reversal) against step 1 (auth only): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z020` (Transaction Type 'Z' = Time-out Reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `021301` (original `021301`) | PASS |
| Same Account Number as the original | `5177660594091610` (original `5177660594091610`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 310220176019932 -> 3102) | `3102` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 078 | `078` | PASS |

---

## 5. What passes

Aggregated over all 11 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `02` | – |
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `3020`/`0020`/`Z020`; Account Number `5191864178826274`/`5158996616447676`/`5167652103124448`; Card Discretionary Block Data `311220125996579`/`280520110263212`/`2805`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `021211`/`021221`/`021241`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-002122 step 2); Nonfuel Amount (BLOCKED in TC-002122 step 2); Approval Number (BLOCKED in TC-002122 step 2; BLOCKED in TC-002124 step 2; BLOCKED in TC-002128 step 2; BLOCKED in TC-002128 step 4) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `821`; Variable Information (Table 031) `12` | – |
| Step-to-step links | 23 of 24 checks PASS | Same Sequence Number as the original (REVIEW in TC-002128) |
| Expected response codes | 9 of 9 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions, `2` = Approved - Authorization only, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 55 of 55 hold | – |
| TC-declared `link_defs` | 17 of 17 hold; 5 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 14 of 19 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5158996616447676=2805.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5167652103124448=2711.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5137775372098485=2908.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5177660594091610=3102.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002121, TC-002122, TC-002124, TC-002128, TC-002130 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-02 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002121, TC-002122, TC-002124, TC-002128, TC-002130 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-03 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002121, TC-002122, TC-002124, TC-002128, TC-002130 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-04 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0). | TC-002122, TC-002124, TC-002128 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.545 (printed G-1); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-05 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-002122 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |
| Q-06 | Sequence Number of the resubmitted completion: ATL105 rules conflict | The test case gives the resubmitted completion a new Sequence Number ('021282'), citing the general rule that every forwarded financial request gets a new Sequence Number. Element 86 says the Purchase/Capture of a preauthorized transaction carries the Authorization Only's Sequence Number ('021281'), and Section 2.3.4 warns that a completion resent before its TOR is finished risks being voided by that TOR - which only happens if both carry the same matching data (Sequence Number, Account Number, amount). ATL105 does not say which rule wins for a resubmitted completion. | TC-002128 | Element 86, PDF p.409 (printed 13-69); Section 2.3.4, PDF p.47 (printed 2-12); Section 2.3.1, PDF p.40 (printed 2-5) | BUYPASS / SME |
| Q-07 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-002130 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-08 | Sequence Number of the resubmitted completion: composed as a NEW Sequence Number (p.40 'Each time any financial transaction request is forwarded ... a new Sequence Number'); confirm. | Listed by the test case itself as an open item (input needed before execution). | TC-002128 | – | Client / test case owner |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - Follow-on amount after a partial approval: keep it a run-time value from the original response; any provisional amount must be at most 7 digits and not more than the original, and the Segment Length is computed after substitution (D-14).
2. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-07).
3. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-01, Q-02, Q-04). The data values are not affected.
4. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-03).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS18 | TC-002121 | T1 – Authorization Only approved | REVIEW | [TC-002121_ATL105_Field_Validation.md](./TC-002121_ATL105_Field_Validation.md) |
| BR1_TS18 | TC-002122 | T4 – Partial authorization → Completion for approved amount | FAIL | [TC-002122_ATL105_Field_Validation.md](./TC-002122_ATL105_Field_Validation.md) |
| BR1_TS18 | TC-002124 | T5 – Authorization → Completion | BLOCKED | [TC-002124_ATL105_Field_Validation.md](./TC-002124_ATL105_Field_Validation.md) |
| BR1_TS18 | TC-002128 | T6 – Completion timed out → Time-out Reversal → Completion resubmitted | BLOCKED | [TC-002128_ATL105_Field_Validation.md](./TC-002128_ATL105_Field_Validation.md) |
| BR1_TS18 | TC-002130 | T10 – Authorization released with Time-out Reversal → declined | REVIEW | [TC-002130_ATL105_Field_Validation.md](./TC-002130_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
