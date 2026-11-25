# ATL105 Test Data Validation – Findings Summary – Balance Inquiry – Contactless Mobile Commerce Device

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Balance Inquiry |
| Scenario | BR1_TS36 Balance Inquiry – Contactless Mobile Commerce Device (scenario status: unconfirmed) |
| Entry mode | Contactless Mobile Commerce Device (Table 005 PAN entry mode `82` = contactless mobile commerce device) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 450) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | The only test case of this scenario (T1), 1 test data file |
| Date | 2026-11-03 |
| Overall result | **0 / 1 test case FAIL** (1 REVIEW) – no defects; review items only |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) E for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.546 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); card-number check digit and MasterCard range | Element 12 p.371, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638 |
| Test objective | The data can prove what the scenario claims:  | TC catalog |
| Step-to-step links | Sequence Number, Account Number, terminal and entry mode against the original step | Section 2.3.1 p.40, Appendix J p.638 |
| Expected response codes | Expected Response Code of each step fits the step; open ('?') codes are listed | Element 83 p.404 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the test case executed against the test data | TC catalog |
| Generation fidelity | Every test data value compared with the test case request value | TC catalog |
| Spec citations | Every `spec_basis` quote and cited page of the test case checked against the 2026-4 text | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note for the message builder.

---

## 2. Results by test case

| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |
|---|---|---|---|---|---|---|---|
| T1 – Balance Inquiry answered | TC-002171 (BR1_TS36_TC1) | 1 | **REVIEW** | 0 | 0 | 4 | – |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

Only one test case is sampled for this scenario, so all its defects are listed in §4.

---

## 4. Defects of the test case

None.

---

## 5. What passes

Based on the single test data file of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `02` | – |
| Segment 100 | Segment Type `100`; Segment Length `085`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `E020`; Account Number `5189525672056098`; Card Discretionary Block Data `271220171094684`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Nonfuel Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `021711`; Approval Number `(empty)`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | – |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `821`; Variable Information (Table 031) `12` | – |
| Expected response codes | – | No expected Response Code is stated (see §6) |
| TC-declared `rule_checks` | 5 of 5 hold | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 2 of 3 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-002171 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-02 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002171 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-03 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002171 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-04 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002171 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-05 | Does a MasterCard credit issuer return a balance? Approval code for Transaction Type E. | Listed by the test case itself as an open item (input needed before execution). | TC-002171 | – | Client / test case owner |

---

## 7. Recommendations for the AI test data generator

1. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-01).
2. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-02, Q-03). The data values are not affected.
3. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-04).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS36 | TC-002171 | T1 – Balance Inquiry answered | REVIEW | [TC-002171_ATL105_Field_Validation.md](./TC-002171_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
