# ATL105 Test Data Validation – Findings Summary – Authorization Only – Chip Fallback

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Authorization Only |
| Scenario | BR1_TS16 Authorization Only – Chip Fallback (scenario status: valid) |
| Entry mode | Chip Fallback (Table 005 PAN entry mode `79` = chip fallback to manual, `80` = chip fallback to swipe) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 373, 374, 375, 377, 386) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 5 of the 16 test cases of this scenario (T1, T2, T4, T5, T2), 7 test data files (2 multi-step flows). Not sampled: 9 further host-message test cases (sample limit 5); 1 T8 settlement (Totals Request only); 1 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **4 / 5 test cases FAIL** (1 REVIEW) – 2 scenario-specific defects (D-08, D-14) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 3 for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); a chip card swiped at a chip terminal is sent as fallback 80; keyed entry (79): expiry MMYY only, no track data; follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.371, Section 10.14.4 p.162, Element 12 p.372, Section 10.1.1.3 p.81, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638 |
| Test objective | The data can prove what the scenario claims: chip fallback to keyed, chip fallback to swipe, completion of an authorization | Appendix J p.638, Section 10.14.4 p.162, Element 5 p.368 |
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
| T1 – Authorization Only approved | TC-002094 (BR1_TS16_TC1) | 1 | **FAIL** | 1 | 0 | 4 | D-08 |
| T2 – Chip unreadable → keyed fallback (79) → approved | TC-002095 (BR1_TS16_TC2) | 1 | **FAIL** | 1 | 0 | 5 | D-08 |
| T4 – Partial authorization → Completion for approved amount | TC-002096 (BR1_TS16_TC5) | 2 | **FAIL** | 4 | 2 | 5 | D-08, D-14 |
| T5 – Authorization → Completion | TC-002098 (BR1_TS16_TC7) | 2 | **FAIL** | 1 | 1 | 5 | D-08 |
| T2 – Chip unreadable → swiped fallback (80) → approved | TC-002107 (BR1_TS16_TC3) | 1 | **REVIEW** | 0 | 0 | 4 | – |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

None – no defect occurs in every test case of this scenario.

---

## 4. Scenario-specific defects

### D-08 – Track data is sent with an entry mode that does not read the card

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002094 | 1 (auth only) | TD-TC-002094.json : 15 | `290720141233712` | '0729' (expiry MMYY), or a swipe entry mode if the card was really read |
| TC-002095 | 1 (auth only) | TD-TC-002095.json : 15 | `290720141233712` | '0729' (expiry MMYY), or a swipe entry mode if the card was really read |
| TC-002096 | 1 (auth only) | TD-TC-002096.step1.auth-only.json : 15 | `311120157853431` | '1131' (expiry MMYY), or a swipe entry mode if the card was really read |
| TC-002098 | 1 (auth only) | TD-TC-002098.step1.auth-only.json : 15 | `320920109369610` | '0932' (expiry MMYY), or a swipe entry mode if the card was really read |

**Why (TC-002094):** Table 005 entry mode '79' = chip fallback to manual: the card number is keyed in, so no magnetic stripe or chip data is read. '290720141233712' is Track 2 data (expiry 2907, service code 201, discretionary data). For a keyed card ATL105 sends only the expiration date, as MMYY.

Spec:
- Element 12 Card Discretionary Block Data (Chapter 13), p.372 (printed 13-32): *"For a card whose number has been manually entered, the expiration date is in MMYY format"*
- Section 10.1.1.3 Manual Entry, p.81 (printed 10-2): *"If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY)."*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.638 (printed Appendix J-1): *"79 Contact and Contactless chip fallback to manual"*

### D-14 – Follow-on step (completion, cancellation, reversal): amount data built from an impossible value

| TC | Step | Finding | Location | Value | Expected |
|---|---|---|---|---|---|
| TC-002096 | 2 (completion) | Segment Length was calculated from the invalid test case value | TD-TC-002096.step2.completion.json -> Standard Message Data Segment -> SegmentLength | `088` | Recalculate the Segment Length after filling in the real values (for example Approved Amount 000001000 -> amount 1000 plus a 6-character Approval Number -> Segment Length 084). |
| TC-002096 | 2 (completion) | The test case value for Nonfuel Amount is too long | test case step 2 request -> Nonfuel Amount (used to build TD-TC-002096.step2.completion.json) | `67336361` | Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |
| TC-002096 | 2 (completion) | The completion amount cannot be larger than the original authorization | test case step 2 request -> Nonfuel Amount | `67336361` | Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |

**TC-002096 in plain words:** step 1 asks for $15.00 and expects Response Code `F` (partial approval). The completion in step 2 must carry the amount from the step 1 response, so the test data correctly uses a run-time expression (`<NonfuelAmount from step 1 response>`). But the test case also holds a provisional value `67336361` = $673,363.61 (8 digits; Element 58 allows at most 7 for MasterCard), larger than the $15.00 that was asked for, and the generator computed the declared Segment Length from it.

Spec:
- Section 12.1 Standard Message Data Segment, p.223 (printed 12-6): *"Identifies the data segment's length, including Segment Type's length and Field Separators."*
- Element 84 Segment Length (Chapter 13), p.406 (printed 13-66): *"This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."*
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"A maximum length of 7 bytes is allowed for all other card types."*
- Section 10.1.2.1 Partial Approval Transaction, p.84 (printed 10-5): *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."*
- Element 121 Partial Approval Indicator (Chapter 13), p.440 (printed 13-100): *"the network can approve an amount that is less than the amount in the Authorization request"*
- Element 6 Approved Amount (Chapter 13), p.368 (printed 13-28): *"Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"*

**What the generator must do next run:** keep the follow-on amount a run-time value taken from the original step's response, make any provisional amount satisfy Element 58 (at most 7 digits, not more than the original amount), and compute the Segment Length after substitution.

### Follow-on steps checked against their original step

#### T4 – Partial authorization → Completion for approved amount (TC-002096)

Step 2 (completion) against step 1 (auth only): D-08 applies to step 1; step 2 has D-14.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0020` (Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `020961` (original `020961`) | PASS |
| Same Account Number as the original | `5158407936477799` (original `5158407936477799`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `79` (original `79`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 311120157853431 -> 3111) | `3111` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 088 (using the test case value for Nonfuel Amount, Approval Number) | `088` | FAIL |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T5 – Authorization → Completion (TC-002098)

Step 2 (completion) against step 1 (auth only): D-08 applies to step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `0020` (Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `020981` (original `020981`) | PASS |
| Same Account Number as the original | `5117757462735907` (original `5117757462735907`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `79` (original `79`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 320920109369610 -> 3209) | `3209` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

---

## 5. What passes

Aggregated over all 7 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `02` | – |
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `3020`/`0020`; Account Number `5176689985215877`/`5158407936477799`/`5117757462735907`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `020941`/`020951`/`020961`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-002096 step 2); Card Discretionary Block Data (FAIL in TC-002094 step 1; FAIL in TC-002095 step 1; FAIL in TC-002096 step 1; FAIL in TC-002098 step 1); Nonfuel Amount (BLOCKED in TC-002096 step 2); Approval Number (BLOCKED in TC-002096 step 2; BLOCKED in TC-002098 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `791`/`801`; Variable Information (Table 031) `12` | – |
| Step-to-step links | 8 of 8 checks PASS | – |
| Expected response codes | 7 of 7 PASS (`0` = Approved - Purchase/Capture, `2` = Approved - Authorization only, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 35 of 35 hold | – |
| TC-declared `link_defs` | 5 of 5 hold; 3 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 20 of 25 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5158407936477799=3111.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5117757462735907=3209.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002094, TC-002095, TC-002096, TC-002098, TC-002107 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-02 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002094, TC-002095, TC-002096, TC-002098, TC-002107 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-03 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002094, TC-002095, TC-002096, TC-002098, TC-002107 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-04 | Marked 'ready' although the terminal is a placeholder | The test case status is 'ready', but its Terminal Identifier is still a placeholder. | TC-002094, TC-002095, TC-002098, TC-002107 | Element 102, PDF p.417 (printed 13-77) | Test case owner |
| Q-05 | Same request as TC-002094 | The request is identical to TC-002094 (T1-AUT) except the Sequence Number: same card 517668...5877, expiry 290720141233712, amount $15.00 and entry mode 791. What makes this test different (Receipt and journal mark manual entry with '\*' before the Account Number (p.81)) happens on the device and is not visible in the message sent to BUYPASS. | TC-002095 | – | Device-level verification during execution |
| Q-06 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0). | TC-002096, TC-002098 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.545 (printed G-1); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-07 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-002096 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - Entry modes that do not read the card (01 keyed, 79 chip fallback to manual): send only the expiry MMYY in Element 12; never generate track data for them (D-08).
   - Follow-on amount after a partial approval: keep it a run-time value from the original response; any provisional amount must be at most 7 digits and not more than the original, and the Segment Length is computed after substitution (D-14).
2. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-01, Q-02, Q-06). The data values are not affected.
3. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-03).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS16 | TC-002094 | T1 – Authorization Only approved | FAIL | [TC-002094_ATL105_Field_Validation.md](./TC-002094_ATL105_Field_Validation.md) |
| BR1_TS16 | TC-002095 | T2 – Chip unreadable → keyed fallback (79) → approved | FAIL | [TC-002095_ATL105_Field_Validation.md](./TC-002095_ATL105_Field_Validation.md) |
| BR1_TS16 | TC-002096 | T4 – Partial authorization → Completion for approved amount | FAIL | [TC-002096_ATL105_Field_Validation.md](./TC-002096_ATL105_Field_Validation.md) |
| BR1_TS16 | TC-002098 | T5 – Authorization → Completion | FAIL | [TC-002098_ATL105_Field_Validation.md](./TC-002098_ATL105_Field_Validation.md) |
| BR1_TS16 | TC-002107 | T2 – Chip unreadable → swiped fallback (80) → approved | REVIEW | [TC-002107_ATL105_Field_Validation.md](./TC-002107_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
