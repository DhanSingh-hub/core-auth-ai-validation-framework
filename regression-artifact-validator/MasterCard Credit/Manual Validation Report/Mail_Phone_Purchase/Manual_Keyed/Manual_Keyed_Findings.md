# ATL105 Test Data Validation – Findings Summary – Mail/Phone Purchase – Manual/Keyed

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Mail/Phone Purchase |
| Scenario | BR1_TS9 Mail/Phone Purchase – Manual/Keyed (scenario status: valid) |
| Entry mode | Manual/Keyed (Table 005 PAN entry mode `01` = manual/keyed) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 287, 288, 289, 290, 292) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 5 of the 6 test cases of this scenario (T1, T5, T6, T9, T10), 7 test data files (2 multi-step flows). Not sampled: 1 further host-message test cases (sample limit 5) |
| Date | 2026-11-03 |
| Overall result | **1 / 5 test cases FAIL** (1 BLOCKED, 3 REVIEW) – 1 scenario-specific defect (D-10) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 6, C, Z for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | keyed entry (01): expiry MMYY only, no track data; follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.372, Section 10.1.1.3 p.81, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 003 (AVS address) on AVS tests; Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Element 113 p.426 |
| Test objective | The data can prove what the scenario claims: AVS, time-out reversal, reversal, partial approval | Section 10.1.3 p.86, Section 2.3 p.39, Section 2.3.1 p.40, Section 10.1.2.1 p.84, Element 5 p.368 |
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
| T1 – Mail/Phone Purchase approved | TC-002008 (BR1_TS9_TC1) | 1 | **REVIEW** | 0 | 0 | 5 | – |
| T5 – Mail/Phone Purchase → Mail/Phone Reversal | TC-002009 (BR1_TS9_TC2) | 2 | **BLOCKED** | 0 | 1 | 6 | – |
| T6 – Mail/Phone Purchase, no response → Time-out Reversal | TC-002010 (BR1_TS9_TC3) | 2 | **REVIEW** | 0 | 0 | 4 | – |
| T9 – Mail/Phone Purchase with AVS mismatch → approved | TC-002011 (BR1_TS9_TC4) | 1 | **FAIL** | 1 | 0 | 3 | D-10 |
| T10 – Partial requested on Mail/Phone Purchase → not partially approved | TC-002013 (BR1_TS9_TC6) | 1 | **REVIEW** | 0 | 0 | 4 | – |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

None – no defect occurs in every test case of this scenario.

---

## 4. Scenario-specific defects

### D-10 – The AVS test sends no address data (Table 003)

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002011 | 1 (moto purchase) | TD-TC-002011.json : 30 | `tables sent: 005, 031` | Add Table 003 with the mismatching street address/ZIP Code that the client supplies for the test card. |

**Why (TC-002011):** The test case objective is an Address Verification (AVS) mismatch, but no request carries AVS data: Table 003 (street address, ZIP Code) is not in the Variable Information Data Segment. Without it the issuer cannot verify an address, so the test cannot produce an AVS result. Apart from the Sequence Number, the request is identical to TC-002008 (T1-MOP), so BUYPASS cannot tell the two tests apart.

Spec:
- Section 10.1.3 Address Verification Service, p.86 (printed 10-7): *"The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment"*
- Element 113 Variable Information (Chapter 13), p.426 (printed 13-86): *"When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here."*

### Follow-on steps checked against their original step

#### T5 – Mail/Phone Purchase → Mail/Phone Reversal (TC-002009)

Step 2 (moto reversal) against step 1 (moto purchase): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `C020` (Transaction Type 'C' = Mail/Phone Reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `020091` (original `020091`) | PASS |
| Same Account Number as the original | `5101364293624216` (original `5101364293624216`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `01` (original `01`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original MMYY 1129 -> 2911) | `2911` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T6 – Mail/Phone Purchase, no response → Time-out Reversal (TC-002010)

Step 2 (timeout reversal) against step 1 (moto purchase): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z020` (Transaction Type 'Z' = Time-out Reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `020101` (original `020101`) | PASS |
| Same Account Number as the original | `5197661371515223` (original `5197661371515223`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `01` (original `01`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original MMYY 0432 -> 3204) | `3204` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 078 | `078` | PASS |

---

## 5. What passes

Aggregated over all 7 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `02` | – |
| Segment 100 | Segment Type `100`; Segment Length `078`/`084`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `6020`/`C020`/`Z020`; Account Number `5134086264881704`/`5101364293624216`/`5197661371515223`; Card Discretionary Block Data `1028`/`1129`/`2911`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Nonfuel Amount `1500`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `020081`/`020091`/`020101`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Approval Number (BLOCKED in TC-002009 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `011`; Variable Information (Table 031) `12` | – |
| Step-to-step links | 8 of 8 checks PASS | – |
| Expected response codes | 4 of 4 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions) | – |
| TC-declared `rule_checks` | 35 of 35 hold | – |
| TC-declared `link_defs` | 6 of 6 hold; 1 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 17 of 22 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5101364293624216=2911.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5197661371515223=3204.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-002008, TC-002009 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-02 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002008, TC-002009, TC-002010, TC-002011, TC-002013 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-03 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002008, TC-002009, TC-002010, TC-002011, TC-002013 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-04 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002008, TC-002009, TC-002010, TC-002011, TC-002013 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-05 | Marked 'ready' although the terminal is a placeholder | The test case status is 'ready', but its Terminal Identifier is still a placeholder. | TC-002008, TC-002009, TC-002010 | Element 102, PDF p.417 (printed 13-77) | Test case owner |
| Q-06 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a Mail/Phone Reversal (Prompt Code C020, Transaction Type C). | TC-002009 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.546 (printed G-2); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-07 | Same request as TC-002008 | The request is identical to TC-002008 (T1-MOP) except the Sequence Number: same card 513408...1704, expiry 1028, amount $15.00 and entry mode 011. BUYPASS cannot tell the two tests apart. | TC-002013 | – | Device-level verification during execution |
| Q-08 | AVS-mismatch test card/address. | Listed by the test case itself as an open item (input needed before execution). | TC-002011 | – | Client / test case owner |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - AVS tests: send Table 003 with the street address / ZIP Code the client supplies for the test card (D-10).
2. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-01).
3. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-02, Q-03, Q-06). The data values are not affected.
4. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-04).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS9 | TC-002008 | T1 – Mail/Phone Purchase approved | REVIEW | [TC-002008_ATL105_Field_Validation.md](./TC-002008_ATL105_Field_Validation.md) |
| BR1_TS9 | TC-002009 | T5 – Mail/Phone Purchase → Mail/Phone Reversal | BLOCKED | [TC-002009_ATL105_Field_Validation.md](./TC-002009_ATL105_Field_Validation.md) |
| BR1_TS9 | TC-002010 | T6 – Mail/Phone Purchase, no response → Time-out Reversal | REVIEW | [TC-002010_ATL105_Field_Validation.md](./TC-002010_ATL105_Field_Validation.md) |
| BR1_TS9 | TC-002011 | T9 – Mail/Phone Purchase with AVS mismatch → approved | FAIL | [TC-002011_ATL105_Field_Validation.md](./TC-002011_ATL105_Field_Validation.md) |
| BR1_TS9 | TC-002013 | T10 – Partial requested on Mail/Phone Purchase → not partially approved | REVIEW | [TC-002013_ATL105_Field_Validation.md](./TC-002013_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
