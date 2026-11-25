# ATL105 Test Data Validation – Findings Summary – Purchase/Capture – Manual/Keyed

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Purchase/Capture |
| Scenario | BR1_TS5 Purchase/Capture – Manual/Keyed (scenario status: valid) |
| Entry mode | Manual/Keyed (Table 005 PAN entry mode `01` = manual/keyed) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 146, 147, 148, 149, 153) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 5 of the 39 test cases of this scenario (T1, T2, T2, T4, T5), 7 test data files (2 multi-step flows). Not sampled: 21 further host-message test cases (sample limit 5); 8 T8 settlement (Totals Request only); 5 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **2 / 5 test cases FAIL** (1 BLOCKED, 2 REVIEW) – 2 scenario-specific defects (D-05, D-06) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 8 for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | keyed entry (01): expiry MMYY only, no track data; follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.372, Section 10.1.1.3 p.81, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638 |
| Test objective | The data can prove what the scenario claims: RFID-equipped terminal, 3 failed swipes then keyed, split tender, reversal | Section 10.1.5 p.86, Appendix J p.639, Section 10.1.1.3 p.81, Section 10.1.2.1 p.84, Element 5 p.368 |
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
| T1 – Purchase approved | TC-001867 (BR1_TS5_TC1) | 1 | **REVIEW** | 0 | 0 | 4 | – |
| T2 – 3 failed swipes → keyed entry → approved | TC-001868 (BR1_TS5_TC3) | 1 | **REVIEW** | 0 | 0 | 5 | – |
| T2 – Swiped/keyed at RFID-equipped terminal → approved | TC-001869 (BR1_TS5_TC4) | 1 | **FAIL** | 1 | 0 | 4 | D-06 |
| T4 – Partial approval → remainder on 2nd card (split tender) | TC-001870 (BR1_TS5_TC5) | 2 | **FAIL** | 3 | 1 | 5 | D-05 |
| T5 – Purchase → Purchase Reversal | TC-001874 (BR1_TS5_TC9) | 2 | **BLOCKED** | 0 | 1 | 5 | – |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

None – no defect occurs in every test case of this scenario.

---

## 4. Scenario-specific defects

### D-05 – Split tender: the second card's amount data was built from an impossible value (T4)

| TC | Finding | Location | Value | Expected |
|---|---|---|---|---|
| TC-001870 | Step 2 Segment 100 length vs amount | `TD-TC-001870.step2.split-tender.json`, SegmentLength `082` (line 7) | Declared length implies an **8-digit** Nonfuel Amount | Element 58: at most **7 digits** for non-Amex cards (p.393) |
| TC-001870 | TC provisional step 2 amount | `test_cases.jsonl` line 149 → `steps[1].request` → `Nonfuel Amount` | `08073891` | Less than `1500` and at most 7 digits |

The test data itself correctly carries a run-time expression for step 2 `NonfuelAmount` (`<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`). That is BLOCKED until it is filled in, not a defect. The declared Segment Length was generated from the invalid provisional value.

#### D-05 explained with a simple example (for the AI Dev team)

**The story the test is meant to tell**

A customer buys something for **$15.00** and pays with two cards:

1. **Card A** (`5193121507140804`) is asked for $15.00 but only has, say, **$10.00** available. The issuer approves only $10.00 (partial approval, Response Code `F`).
2. The device asks for the **remaining $5.00**, and the customer pays it with **Card B** (`5164918772290387`).

Card B must be charged only **the leftover**: $15.00 − $10.00 = $5.00. The leftover is always **less than $15.00**, because Card A paid something (Section 10.1.2.1 Partial Approval Transaction, p.84 (printed 10-5): *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."*).

**What the generated test data contains**

Step 1, Card A (`TD-TC-001870.step1.purchase.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `1500` | $15.00 | ✅ |

Step 2, Card B (`TD-TC-001870.step2.split-tender.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `<RemainingBalance = step…>` | "Fill in the leftover at run time" (e.g. $5.00) | ✅ correct approach |
| SegmentLength (line 7) | `082` | Calculated as if the amount had **8 digits** | ❌ wrong |

**Problem 1: the length was counted with an impossible amount.** `SegmentLength` = 82 is the character count of Segment 100. All other fields plus separators add up to **74**, so 82 − 74 = **8 characters** were reserved for the amount. The 8-digit value the generator used is in its own test case (`test_cases.jsonl` line 149 → TC-001870 → `steps[1].request` → `Nonfuel Amount`): **`08073891` = $80,738.91**. That is wrong twice:

- It is **larger than the $15.00 bill**, and a leftover can never exceed the bill.
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"A maximum length of 7 bytes is allowed for all other card types."* It has 8 digits.

A real leftover such as $5.00 (`500`, 3 digits) gives a Segment Length of **077**, not 082.

**In one line:** Card B should pay only the leftover (less than $15.00), but the generator built the message length around an impossible amount of **$80,738.91**.

**What the generator must do next run**

| # | Rule | Check before release |
|---|---|---|
| 1 | Step 2 amount = step 1 requested amount − step 1 Approved Amount (Element 6 from the step 1 response). Keep it as a run-time value; never synthesize a random amount. | `1 ≤ step 2 amount < step 1 amount` |
| 2 | Any provisional/placeholder amount must still satisfy Element 58: at most 7 digits for non-Amex cards. | Length check against the card network |
| 3 | Segment 100 `SegmentLength` must be computed **after** the run-time amount is substituted. | Recompute: sum of fields + one separator per field |

### D-06 – The terminal is described two different ways in the same message (Table 005 vs Table 031)

| TC | File : lines | Table 005 | Table 031 | Expected |
|---|---|---|---|---|
| TC-001869 | TD-TC-001869.json : 34, 39 | `018` | `12` | Keep Table 005 = 018 and change Table 031 to 07, which means contactless magnetic stripe read (no contact or contactless chip). Use a separate RFID terminal, not the hybrid chip terminal used by the other test cases. |

Table 005 '018' says: manual/keyed entry on a terminal with code 8 (contactless magnetic stripe read capability), which Appendix J note 1 reserves for a terminal with a contactless magstripe reader and no chip. Table 031 '12' says: hybrid reader: contact chip and contactless chip, so the terminal has a chip reader. Both cannot be true for the same terminal: a chip-capable terminal must send 1 (not 8), and a terminal without a chip cannot be 12. The test case itself says: "Terminal has a contactless (RFID) receiver attached and no chip reader; the card is swiped or keyed".

Spec:
- Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031), p.569 (printed Appendix I-20): *"12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities"*
- Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031), p.569 (printed Appendix I-20): *"07 = Contactless magnetic stripe read capability (no contact or contactless chip capability)"*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.639 (printed Appendix J-2): *"If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8"*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.639 (printed Appendix J-2): *"8 Contactless Magnetic Stripe Read Capability"*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.639 (printed Appendix J-2): *"For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8."*
- Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031), p.569 (printed Appendix I-20): *"Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode."*

### Follow-on steps checked against their original step

#### T5 – Purchase → Purchase Reversal (TC-001874)

Step 2 (reversal) against step 1 (purchase): no defect on step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8020` (Transaction Type '8' = Purchase reversal; Card Type '020' = Credit) | – | PASS |
| Same Sequence Number as the original | `018741` (original `018741`) | PASS |
| Same Account Number as the original | `5135265235494909` (original `5135265235494909`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `01` (original `01`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original MMYY 0327 -> 2703) | `2703` | PASS |
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
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `0020`/`8020`; Account Number `5100586947757353`/`5157031214639283`/`5193121507140804`; Card Discretionary Block Data `0730`/`0928`/`0129`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `018671`/`018681`/`018691`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-001870 step 2); Nonfuel Amount (BLOCKED in TC-001870 step 2); Approval Number (BLOCKED in TC-001874 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `011`/`018`; Variable Information (Table 031) `12` | – |
| Step-to-step links | 6 of 6 checks PASS | – |
| Expected response codes | 7 of 7 PASS (`0` = Approved - Purchase/Capture, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 37 of 37 hold | – |
| TC-declared `link_defs` | 5 of 5 hold; 2 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 22 of 27 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5135265235494909=2703.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-001867, TC-001868, TC-001869, TC-001870, TC-001874 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-02 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-001867, TC-001868, TC-001869, TC-001870, TC-001874 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-03 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-001867, TC-001868, TC-001869, TC-001870, TC-001874 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-04 | Marked 'ready' although the terminal is a placeholder | The test case status is 'ready', but its Terminal Identifier is still a placeholder. | TC-001867, TC-001868, TC-001869, TC-001874 | Element 102, PDF p.417 (printed 13-77) | Test case owner |
| Q-05 | Same request as TC-001867 | The request is identical to TC-001867 (T1-PUR) except the Sequence Number: same card 510058...7353, expiry 0730, amount $15.00 and entry mode 011. What makes this test different (Device prompts to swipe again after each of the first three failed reads; no host message meanwhile (p.81); Manual-entry prompt asks for Account Number and Expiration Date (MMYY) (p.81); Receipt and journal mark manual entry with '\*' before the Account Number (p.81)) happens on the device and is not visible in the message sent to BUYPASS. | TC-001868 | Section 10.1.1.3, PDF p.81 (printed 10-2) | Device-level verification during execution |
| Q-06 | The quote cited for p.87 comes from an unrelated section | The quote is word-for-word, but it comes from Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8); that section is about FSA/HRA healthcare benefit cards. | TC-001870 | Section 10.1.6.1.3, PDF p.87 (printed 10-8); Section 10.1.2.1, PDF p.84 (printed 10-5) | Test case generator (citation) |
| Q-07 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-001870 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |
| Q-08 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S, reversing Authorization Only codes 3 and 5), while this step is a Purchase Reversal (Prompt Code 8020, Transaction Type 8). | TC-001874 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.546 (printed G-2); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-09 | Terminal JX72948216050257001 uses more than one terminal capability code (Table 005, position 3) | code 1 in TC-001722, TC-001723, TC-001724, TC-001726, TC-001730, TC-001759, TC-001761, TC-001762, TC-001763, TC-001767, TC-001796, TC-001797, TC-001799, TC-001803, TC-001834, TC-001835, TC-001839, TC-001840, TC-001851, TC-001867, TC-001868, TC-001870, TC-001874, TC-001906, TC-001907, TC-001908, TC-001912, TC-001929, TC-001941, TC-001942, TC-001943, TC-001948, TC-001959, TC-001976, TC-001977, TC-001981, TC-001982, TC-001993, TC-002008, TC-002009, TC-002010, TC-002011, TC-002013, TC-002014, TC-002015, TC-002016, TC-002017, TC-002019, TC-002020, TC-002021, TC-002022, TC-002023, TC-002025, TC-002035, TC-002036, TC-002037, TC-002038, TC-002040, TC-002050, TC-002051, TC-002052, TC-002054, TC-002058, TC-002065, TC-002066, TC-002068, TC-002072, TC-002074, TC-002079, TC-002080, TC-002081, TC-002083, TC-002087, TC-002094, TC-002095, TC-002096, TC-002098, TC-002107, TC-002110, TC-002111, TC-002112, TC-002115, TC-002117, TC-002121, TC-002122, TC-002124, TC-002128, TC-002130, TC-002134, TC-002135, TC-002137, TC-002138, TC-002140, TC-002141, TC-002143, TC-002144, TC-002146, TC-002147, TC-002149, TC-002150, TC-002152, TC-002153, TC-002155, TC-002156, TC-002158, TC-002159, TC-002161, TC-002162, TC-002164, TC-002165, TC-002166, TC-002167, TC-002168, TC-002169, TC-002170, TC-002171; code 8 in TC-001798, TC-001869. Appendix J note 1 says all transactions from a device use one code. | TC-001867, TC-001868, TC-001869, TC-001870, TC-001874 | Appendix J, PDF p.639 (printed J-2) | Test design |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - T4 split tender: derive the step 2 amount from the step 1 Approved Amount. Never exceed 7 digits for MasterCard, and compute the Segment Length after substitution (D-05).
   - RFID terminal test: Keep Table 005 = 018 and change Table 031 to 07, which means contactless magnetic stripe read (no contact or contactless chip). Use a separate RFID terminal, not the hybrid chip terminal used by the other test cases. (D-06).
2. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-01, Q-02, Q-06, Q-08). The data values are not affected.
3. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-03).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS5 | TC-001867 | T1 – Purchase approved | REVIEW | [TC-001867_ATL105_Field_Validation.md](./TC-001867_ATL105_Field_Validation.md) |
| BR1_TS5 | TC-001868 | T2 – 3 failed swipes → keyed entry → approved | REVIEW | [TC-001868_ATL105_Field_Validation.md](./TC-001868_ATL105_Field_Validation.md) |
| BR1_TS5 | TC-001869 | T2 – Swiped/keyed at RFID-equipped terminal → approved | FAIL | [TC-001869_ATL105_Field_Validation.md](./TC-001869_ATL105_Field_Validation.md) |
| BR1_TS5 | TC-001870 | T4 – Partial approval → remainder on 2nd card (split tender) | FAIL | [TC-001870_ATL105_Field_Validation.md](./TC-001870_ATL105_Field_Validation.md) |
| BR1_TS5 | TC-001874 | T5 – Purchase → Purchase Reversal | BLOCKED | [TC-001874_ATL105_Field_Validation.md](./TC-001874_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
