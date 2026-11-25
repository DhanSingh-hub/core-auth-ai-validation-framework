# ATL105 Test Data Validation – Findings Summary – Purchase/Capture – Contactless Mobile Commerce Device

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Purchase/Capture |
| Scenario | BR1_TS8 Purchase/Capture – Contactless Mobile Commerce Device (scenario status: unconfirmed) |
| Entry mode | Contactless Mobile Commerce Device (Table 005 PAN entry mode `82` = contactless mobile commerce device) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 255, 256, 260, 261, 272) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 5 of the 32 test cases of this scenario (T1, T4, T5, T6, T10), 8 test data files (3 multi-step flows). Not sampled: 17 further host-message test cases (sample limit 5); 8 T8 settlement (Totals Request only); 2 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **2 / 5 test cases FAIL** (1 BLOCKED, 2 REVIEW) – 2 scenario-specific defects (D-05, D-13) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 8, Z for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.371, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638 |
| Test objective | The data can prove what the scenario claims: split tender, time-out reversal, reversal | Section 10.1.2.1 p.84, Section 2.3 p.39, Section 2.3.1 p.40, Element 5 p.368 |
| Step-to-step links | Sequence Number, Account Number, terminal and entry mode against the original step; new Sequence Number and a different card on the split-tender step | Section 2.3.1 p.40, Appendix J p.638, Element 86 p.409 |
| Expected response codes | Expected Response Code of each step fits the step; open ('?') codes are listed | Element 83 p.404 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the test case executed against the test data | TC catalog |
| Generation fidelity | Every test data value compared with the test case request value | TC catalog |
| Spec citations | Every `spec_basis` quote and cited page of the test case checked against the 2026-4 text | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note for the message builder.

---

## 2. Results by test case

| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |
|---|---|---|---|---|---|---|---|
| T1 – Purchase approved | TC-001976 (BR1_TS8_TC1) | 1 | **REVIEW** | 0 | 0 | 3 | – |
| T4 – Partial approval → remainder on 2nd card (split tender) | TC-001977 (BR1_TS8_TC3) | 2 | **FAIL** | 3 | 1 | 5 | D-05 |
| T5 – Purchase → Purchase Reversal | TC-001981 (BR1_TS8_TC7) | 2 | **BLOCKED** | 0 | 1 | 4 | – |
| T6 – Purchase, no response → Time-out Reversal | TC-001982 (BR1_TS8_TC10) | 2 | **REVIEW** | 0 | 0 | 3 | – |
| T10 – Time-out Reversal with no original → declined (unmatched) | TC-001993 (BR1_TS8_TC26) | 1 | **FAIL** | 1 | 0 | 3 | D-13 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

None – no defect occurs in every test case of this scenario.

---

## 4. Scenario-specific defects

### D-05 – Split tender: the second card's amount data was built from an impossible value (T4)

| TC | Finding | Location | Value | Expected |
|---|---|---|---|---|
| TC-001977 | Step 2 Segment 100 length vs amount | `TD-TC-001977.step2.split-tender.json`, SegmentLength `093` (line 7) | Declared length implies an **8-digit** Nonfuel Amount | Element 58: at most **7 digits** for non-Amex cards (p.393) |
| TC-001977 | TC provisional step 2 amount | `test_cases.jsonl` line 256 → `steps[1].request` → `Nonfuel Amount` | `39262491` | Less than `1500` and at most 7 digits |

The test data itself correctly carries a run-time expression for step 2 `NonfuelAmount` (`<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`). That is BLOCKED until it is filled in, not a defect. The declared Segment Length was generated from the invalid provisional value.

#### D-05 explained with a simple example (for the AI Dev team)

**The story the test is meant to tell**

A customer buys something for **$15.00** and pays with two cards:

1. **Card A** (`5177966242553616`) is asked for $15.00 but only has, say, **$10.00** available. The issuer approves only $10.00 (partial approval, Response Code `F`).
2. The device asks for the **remaining $5.00**, and the customer pays it with **Card B** (`5126931620543326`).

Card B must be charged only **the leftover**: $15.00 − $10.00 = $5.00. The leftover is always **less than $15.00**, because Card A paid something (Section 10.1.2.1 Partial Approval Transaction, p.84 (printed 10-5): *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."*).

**What the generated test data contains**

Step 1, Card A (`TD-TC-001977.step1.purchase.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `1500` | $15.00 | ✅ |

Step 2, Card B (`TD-TC-001977.step2.split-tender.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `<RemainingBalance = step…>` | "Fill in the leftover at run time" (e.g. $5.00) | ✅ correct approach |
| SegmentLength (line 7) | `093` | Calculated as if the amount had **8 digits** | ❌ wrong |

**Problem 1: the length was counted with an impossible amount.** `SegmentLength` = 93 is the character count of Segment 100. All other fields plus separators add up to **85**, so 93 − 85 = **8 characters** were reserved for the amount. The 8-digit value the generator used is in its own test case (`test_cases.jsonl` line 256 → TC-001977 → `steps[1].request` → `Nonfuel Amount`): **`39262491` = $392,624.91**. That is wrong twice:

- It is **larger than the $15.00 bill**, and a leftover can never exceed the bill.
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"A maximum length of 7 bytes is allowed for all other card types."* It has 8 digits.

A real leftover such as $5.00 (`500`, 3 digits) gives a Segment Length of **088**, not 093.

**In one line:** Card B should pay only the leftover (less than $15.00), but the generator built the message length around an impossible amount of **$392,624.91**.

**What the generator must do next run**

| # | Rule | Check before release |
|---|---|---|
| 1 | Step 2 amount = step 1 requested amount − step 1 Approved Amount (Element 6 from the step 1 response). Keep it as a run-time value; never synthesize a random amount. | `1 ≤ step 2 amount < step 1 amount` |
| 2 | Any provisional/placeholder amount must still satisfy Element 58: at most 7 digits for non-Amex cards. | Length check against the card network |
| 3 | Segment 100 `SegmentLength` must be computed **after** the run-time amount is substituted. | Recompute: sum of fields + one separator per field |

### D-13 – A follow-on message (time-out reversal) carries full track data

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-001993 | 1 (timeout reversal) | TD-TC-001993.json : 15 | `270820122676321` | '2708' (the expiry YYMM only) |

**Why (TC-001993):** This timeout reversal is formatted from stored card data after the original transaction, but '270820122676321' is full Track 2 data (expiry, service code and discretionary data). Follow-up transactions use truncated Track 2 (Account Number = YYMM), and full magnetic stripe data must not be stored for later messages.

Spec:
- Section 10.1.1.2 Track Support, p.81 (printed 10-2): *"All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile."*
- Section 10.1.8.1 Data Storage Requirements, p.94 (printed 10-15): *"The merchant device must not store full magnetic stripe data."*
- Section 10.1.8.2 Track Support Requirements, p.94 (printed 10-15): *"The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."*

### Follow-on steps checked against their original step

#### T5 – Purchase → Purchase Reversal (TC-001981)

Step 2 (reversal) against step 1 (purchase): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8020` (Transaction Type '8' = Purchase reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `019811` (original `019811`) | PASS |
| Same Account Number as the original | `5166400875410947` (original `5166400875410947`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 280120198821826 -> 2801) | `2801` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T6 – Purchase, no response → Time-out Reversal (TC-001982)

Step 2 (timeout reversal) against step 1 (purchase): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z020` (Transaction Type 'Z' = Time-out Reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `019821` (original `019821`) | PASS |
| Same Account Number as the original | `5116596932229542` (original `5116596932229542`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `82` (original `82`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 281220116573597 -> 2812) | `2812` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 078 | `078` | PASS |

#### T10 – Time-out Reversal with no original → declined (unmatched) (TC-001993)

Step 1 (timeout reversal) against step 1 (timeout reversal): D-13 applies to step 1; step 1 has D-13.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z020` (Transaction Type 'Z' = Time-out Reversal; Card Type '020' = Credit) | – | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only | `270820122676321` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 089 | `089` | PASS |

---

## 5. What passes

Aggregated over all 8 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `02` | – |
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `0020`/`8020`/`Z020`; Account Number `5197552283587899`/`5177966242553616`/`5126931620543326`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `019761`/`019771`/`019772`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-001977 step 2); Card Discretionary Block Data (FAIL in TC-001993 step 1); Nonfuel Amount (BLOCKED in TC-001977 step 2); Approval Number (BLOCKED in TC-001981 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `821`; Variable Information (Table 031) `12` | – |
| Step-to-step links | 10 of 10 checks PASS | – |
| Expected response codes | 7 of 7 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 42 of 42 hold | – |
| TC-declared `link_defs` | 8 of 8 hold; 2 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 16 of 21 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5166400875410947=2801.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5116596932229542=2812.
- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5114069602255058=270820122676321.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-001976, TC-001977, TC-001981, TC-001982, TC-001993 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-02 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-001976, TC-001977, TC-001981, TC-001982, TC-001993 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-03 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-001976, TC-001977, TC-001981, TC-001982, TC-001993 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-04 | The quote cited for p.87 comes from an unrelated section | The quote is word-for-word, but it comes from Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8); that section is about FSA/HRA healthcare benefit cards. | TC-001977 | Section 10.1.6.1.3, PDF p.87 (printed 10-8); Section 10.1.2.1, PDF p.84 (printed 10-5) | Test case generator (citation) |
| Q-05 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-001977 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |
| Q-06 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S, reversing Authorization Only codes 3 and 5), while this step is a Purchase Reversal (Prompt Code 8020, Transaction Type 8). | TC-001981 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.546 (printed G-2); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - T4 split tender: derive the step 2 amount from the step 1 Approved Amount. Never exceed 7 digits for MasterCard, and compute the Segment Length after substitution (D-05).
   - Follow-on messages (time-out reversal, reversal, completion): send Account Number + expiry YYMM only (truncated Track 2); never copy the full track data of the original (D-13).
2. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-01, Q-02, Q-04, Q-06). The data values are not affected.
3. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-03).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS8 | TC-001976 | T1 – Purchase approved | REVIEW | [TC-001976_ATL105_Field_Validation.md](./TC-001976_ATL105_Field_Validation.md) |
| BR1_TS8 | TC-001977 | T4 – Partial approval → remainder on 2nd card (split tender) | FAIL | [TC-001977_ATL105_Field_Validation.md](./TC-001977_ATL105_Field_Validation.md) |
| BR1_TS8 | TC-001981 | T5 – Purchase → Purchase Reversal | BLOCKED | [TC-001981_ATL105_Field_Validation.md](./TC-001981_ATL105_Field_Validation.md) |
| BR1_TS8 | TC-001982 | T6 – Purchase, no response → Time-out Reversal | REVIEW | [TC-001982_ATL105_Field_Validation.md](./TC-001982_ATL105_Field_Validation.md) |
| BR1_TS8 | TC-001993 | T10 – Time-out Reversal with no original → declined (unmatched) | FAIL | [TC-001993_ATL105_Field_Validation.md](./TC-001993_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
