# ATL105 Test Data Validation – Findings Summary – Purchase/Capture – EMV Contactless Chip

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Purchase/Capture |
| Scenario | BR1_TS2 Purchase/Capture – EMV Contactless Chip (scenario status: valid) |
| Entry mode | EMV Contactless Chip (Table 005 PAN entry mode `07` = contactless chip read) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 38, 40, 41, 42, 46) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 5 of the 37 test cases of this scenario (T1, T2, T3, T4, T5), 7 test data files (2 multi-step flows). Not sampled: 22 further host-message test cases (sample limit 5); 8 T8 settlement (Totals Request only); 2 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **5 / 5 test cases FAIL** – 3 defects common to every test case (D-01, D-02, D-12), plus 3 scenario-specific defects (D-03, D-04, D-05) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step (EMV Financial Transaction Request when Segment 130 is sent; Financial Transaction Request on follow-on steps), Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167, Section 11.8.1 p.205, Section 11.8.1 p.206 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 8 for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.371, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 030 (POS Condition Code) for EMV and Credential on File transactions; Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Appendix I p.568 |
| Segment 130 (9 fields) | Elements 187-192 and 118; EMV Chip Data TLV decode; EMV Additional Information (Appendix T); Segment Length recomputed (chip data counted in binary bytes) | Section 12.20 p.270, Element 187 p.476, Element 188 p.477, Element 189 p.477, Element 190 p.478, Element 191 p.479, Element 192 p.480, Element 118 p.439, Appendix R p.697, Appendix T p.701 |
| EMV cross-field | 9F02 vs amount, 9C vs transaction type, AID (9F06/84) present, 5F2A/9F1A, 5F34 vs Element 188, 9F27, 9F34, Tags 5A/57 absent | Element 190 p.478, Appendix R p.697 |
| Test objective | The data can prove what the scenario claims: expired card, online PIN, split tender, reversal | Section 10.14.2.1 p.160, Element 12 p.372, Section 10.14.3 p.162, Section 10.1.2.1 p.84, Element 5 p.368 |
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
| T1 – Purchase approved | TC-001759 (BR1_TS2_TC1) | 1 | **FAIL** | 3 | 0 | 9 | D-01, D-02, D-12 |
| T2 – Expired EMV card → sent online → host decides | TC-001761 (BR1_TS2_TC4) | 1 | **FAIL** | 4 | 0 | 8 | D-01, D-02, D-03, D-12 |
| T3 – EMV online PIN → approved | TC-001762 (BR1_TS2_TC5) | 1 | **FAIL** | 4 | 0 | 8 | D-01, D-02, D-04, D-12 |
| T4 – Partial approval → remainder on 2nd card (split tender) | TC-001763 (BR1_TS2_TC6) | 2 | **FAIL** | 10 | 1 | 10 | D-01, D-02, D-05, D-12 |
| T5 – Purchase → Purchase Reversal | TC-001767 (BR1_TS2_TC10) | 2 | **FAIL** | 3 | 1 | 10 | D-01, D-02, D-12 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case (6 EMV test data files)

Follow-on reversal files without Segment 130 (`TD-TC-001767.step2.reversal.json`) are allowed: Section 10.14.2.4 Supported Transaction Types (EMV), p.161 (printed 10-82): *"EMV data is not required on Reversal transactions."*

### D-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3

| | |
|---|---|
| Location | `"EMV Request Data Segment" → "EMVCardSequenceNumber"`, **line 47** of each EMV test data file |
| Generated value | `01` |
| Expected | `001` |
| Spec | Element 188 EMV Card Sequence Number (Chapter 13), p.477 (printed 13-137): *"Fixed length of three bytes, right-aligned and padded with zeroes"*<br>Element 188 EMV Card Sequence Number (Chapter 13), p.477 (printed 13-137): *"Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card"* |
| Certainty | **Certain.** The chip data carries Tag 5F34 = `01`, so the value is right but the width is wrong. |
| Side effect | The declared Segment 130 length (`1181`) was computed with the 2-byte value and changes by +1 when corrected. |

#### D-01 evidence by file

| Test case | Test data file | Line | Element 188 | Tag 5F34 | Expected |
|---|---|---|---|---|---|
| TC-001759 | TD-TC-001759.json | 47 | `01` | `01` | `001` |
| TC-001761 | TD-TC-001761.json | 47 | `01` | `01` | `001` |
| TC-001762 | TD-TC-001762.json | 47 | `01` | `01` | `001` |
| TC-001763 | TD-TC-001763.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-001763 | TD-TC-001763.step2.split-tender.json | 47 | `01` | `01` | `001` |
| TC-001767 | TD-TC-001767.step1.purchase.json | 47 | `01` | `01` | `001` |

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data

| | |
|---|---|
| Location | `"EMV Request Data Segment"`, **lines 50–52** of each EMV test data file |
| Generated value | Indicator `001`, Length `984`, Information = 984 generated A–Z/0–9 characters (e.g. `EAMHC50ODTH426QT444W…Y6GAJQCX`) |
| Expected | Indicator `001` with EMV Table Data value **`EMVYES`** or **`EMVNOT`** |
| Spec chain | 1. **E191** (p.479): *"001 EMV table data"* → `001` PASS<br>2. **E192** (p.480): *"Valid Codes/Values: 001–985"* → `984` **PASS on its own**<br>3. **E118 EMV Additional Information** (p.439): *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here."*<br>4. **Appendix T-1** (p.701): EMV Table Data – *"Maximum Length n6 Len.: 6"*; request values *"EMVYES This code indicates that the device is able to receive EMV Table Data."* / *"EMVNOT This code indicates that the device is unable to receive EMV Table Data."* → **FAIL** |
| Root cause | The test case marks this value `method: format_synthesis` with flag `UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)`. The generator filled the generic 984-character maximum instead of applying Appendix T. The same generated string is reused across test cases (TC-001759/TC-001761/TC-001762 → `EAMH…`). |
| Certainty | **Certain** that the content is invalid. **Not certain** what the exact corrected Element 192 value is (see §6, Q-01). |

**Clarification on Element 192:** Element 192 = `984` is a valid value in range and equals the actual content length, so the report marks it **PASS**. The defect is the **content** of Element 118. Element 192 is consistent with that content, but the content itself is not allowed for indicator `001`.

#### D-02 evidence by file

| Test case | Test data file | Lines | Indicator | Length | Value (start…end) |
|---|---|---|---|---|---|
| TC-001759 | TD-TC-001759.json | 50–52 | 001 | 984 | `EAMHC50ODTH426QT444W…Y6GAJQCX` |
| TC-001761 | TD-TC-001761.json | 50–52 | 001 | 984 | `EAMHC50ODTH426QT444W…Y6GAJQCX` |
| TC-001762 | TD-TC-001762.json | 50–52 | 001 | 984 | `EAMHC50ODTH426QT444W…Y6GAJQCX` |
| TC-001763 | TD-TC-001763.step1.purchase.json | 50–52 | 001 | 984 | `IPCK443KAYGREU076B2N…HPV8ZV3S` |
| TC-001763 | TD-TC-001763.step2.split-tender.json | 50–52 | 001 | 984 | `R5ZSEAKMG6OP2IVSST8F…X986X6NE` |
| TC-001767 | TD-TC-001767.step1.purchase.json | 50–52 | 001 | 984 | `UK2O2I0MT45NBF125C5J…PEUTE7EU` |

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-001759 | 1 (purchase) | TD-TC-001759.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-001761 | 1 (purchase) | TD-TC-001761.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-001762 | 1 (purchase) | TD-TC-001762.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-001763 | 1 (purchase) | TD-TC-001763.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-001763 | 2 (split tender) | TD-TC-001763.step2.split-tender.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-001767 | 1 (purchase) | TD-TC-001767.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |

**Why (TC-001759):** This step is an EMV transaction (Segment 130 is sent). Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.

This rule was added in this run; the first version of this report did not check it.

Spec:
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

---

## 4. Scenario-specific defects

### D-03 – Expired-card test data uses a card that is not expired (T2)

| TC | File : line | Element 12 value | Expiry (YYMM) | Expected |
|---|---|---|---|---|
| TC-001761 | TD-TC-001761.json : 15 | `290720142194190` | `2907` = July 2029 | earlier than `2611` |

Spec: Section 10.14.2.1 Expiration Date (EMV card requirements), p.160 (printed 10-81): *"The device does not perform expiration date validation in an EMV card transaction."*; Element 12 Card Discretionary Block Data (Chapter 13), p.372 (printed 13-32): *"Note: For all authorizations, merchants must send a value even for expired cards."*. The test cannot exercise its objective with a valid card. TVR (Tag 95 = `8000048000`) byte 2 "Expired application" is not set. Tag 5F24 (Application Expiration Date) is absent. The TC already lists *"Expired EMV test card."* as an open client input.

### D-04 – Online-PIN test data carries no PIN block and a signature CVM (T3)

| TC | Finding | Location | Value | Expected |
|---|---|---|---|---|
| TC-001762 | Encrypted PIN Block Data (Element 33) empty | TD-TC-001762.json line 16 | `""` | DUKPT KSN + PIN block – *"For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."* (p.382) |
| TC-001762 | CVM Results (Tag 9F34) = signature | EMVChipData, Tag 9F34 | `5E0302` (CVM code 1E) | CVM code 02 / 42 (enciphered PIN verified online) – EMV Book 3 |

**Clarification – this is not "credit always needs a PIN".** For credit, Element 33 is optional (Section 10.14.3 EMV PIN (Personal Identification Number) Processing, p.162 (printed 10-83): *"In a credit/signature debit transaction, a PIN of all F’s is NOT required since PINs are an optional field for a credit/signature debit transaction."*; Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"Required for debit and EBT."*). So an empty Element 33 is **correct** for normal credit transactions (e.g. TC-001759), and D-04 is not raised there.

D-04 applies only to the **online-PIN test cases**, because:
- Section 10.14.3 EMV PIN (Personal Identification Number) Processing, p.162 (printed 10-83): *"Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."*; and Section 10.14.3 EMV PIN (Personal Identification Number) Processing, p.162 (printed 10-83): *"MasterCard has mandated that Point-of-Sale devices that support EMV PIN must support both online PIN and offline PIN processing."*
- The TC objective is *"EMV online PIN -> approved"*, and its execution step is *"Cardholder enters the PIN; the PIN block is sent online (Encrypted PIN Block Data, DUKPT)"*.
- The generated data has no PIN block and a signature CVM, so it is a signature credit transaction (same as T1), not an online-PIN test.

**Classification:** test-objective defect, **blocked by client input**. The TC itself lists *"Test card with a known online PIN and the test DUKPT key (p.57)."* as an open item; without them a valid PIN block cannot be generated.

**Ask to the AI Dev team:** do **not** add a PIN to credit test data in general. For online-PIN test cases only, either populate Element 33 (DUKPT KSN + PIN block) and set Tag 9F34 to online PIN once the test card and key are supplied, or mark the TC **blocked – needs client input** instead of releasing signature data under an online-PIN objective.

### D-05 – Split tender: the second card's amount data was built from an impossible value (T4)

| TC | Finding | Location | Value | Expected |
|---|---|---|---|---|
| TC-001763 | Step 2 Tag 9F02 (Amount, Authorized) | `TD-TC-001763.step2.split-tender.json`, EMVChipData | `000000001500` (= step 1 full amount) | Remaining balance = requested – Approved Amount (Element 6) of the partial approval (p.84) |
| TC-001763 | Step 2 Segment 100 length vs amount | `TD-TC-001763.step2.split-tender.json`, SegmentLength `093` (line 7) | Declared length implies an **8-digit** Nonfuel Amount | Element 58: at most **7 digits** for non-Amex cards (p.393) |
| TC-001763 | TC provisional step 2 amount | `test_cases.jsonl` line 42 → `steps[1].request` → `Nonfuel Amount` | `59877108` | Less than `1500` and at most 7 digits |

The test data itself correctly carries a run-time expression for step 2 `NonfuelAmount` (`<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`). That is BLOCKED until it is filled in, not a defect. The chip amount and the declared Segment Length were generated from the invalid provisional value.

#### D-05 explained with a simple example (for the AI Dev team)

**The story the test is meant to tell**

A customer buys something for **$15.00** and pays with two cards:

1. **Card A** (`5119561504398924`) is asked for $15.00 but only has, say, **$10.00** available. The issuer approves only $10.00 (partial approval, Response Code `F`).
2. The device asks for the **remaining $5.00**, and the customer pays it with **Card B** (`5180378702966947`).

Card B must be charged only **the leftover**: $15.00 − $10.00 = $5.00. The leftover is always **less than $15.00**, because Card A paid something (Section 10.1.2.1 Partial Approval Transaction, p.84 (printed 10-5): *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."*).

**What the generated test data contains**

Step 1, Card A (`TD-TC-001763.step1.purchase.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `1500` | $15.00 | ✅ |
| Chip amount, Tag 9F02 | `000000001500` | $15.00 | ✅ matches |

Step 2, Card B (`TD-TC-001763.step2.split-tender.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `<RemainingBalance = step…>` | "Fill in the leftover at run time" (e.g. $5.00) | ✅ correct approach |
| Chip amount, Tag 9F02 | `000000001500` | **$15.00** | ❌ wrong |
| SegmentLength (line 7) | `093` | Calculated as if the amount had **8 digits** | ❌ wrong |

**Problem 1: Card B's chip says $15.00.** The request asks Card B to pay the leftover (e.g. $5.00), but Card B's chip data says it is paying the full $15.00, copied from Card A. It is like a receipt for the second card showing "$15.00" when only $5.00 was owed. Whatever Card A gets approved, the leftover is always less than $15.00, so `1500` on Card B is always wrong.

**Problem 2: the length was counted with an impossible amount.** `SegmentLength` = 93 is the character count of Segment 100. All other fields plus separators add up to **85**, so 93 − 85 = **8 characters** were reserved for the amount. The 8-digit value the generator used is in its own test case (`test_cases.jsonl` line 42 → TC-001763 → `steps[1].request` → `Nonfuel Amount`): **`59877108` = $598,771.08**. That is wrong twice:

- It is **larger than the $15.00 bill**, and a leftover can never exceed the bill.
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"A maximum length of 7 bytes is allowed for all other card types."* It has 8 digits.

A real leftover such as $5.00 (`500`, 3 digits) gives a Segment Length of **088**, not 093.

**In one line:** Card B should pay only the leftover (less than $15.00), but the generator gave Card B's chip **$15.00** and built the message length around an impossible amount of **$598,771.08**.

**What the generator must do next run**

| # | Rule | Check before release |
|---|---|---|
| 1 | Step 2 amount = step 1 requested amount − step 1 Approved Amount (Element 6 from the step 1 response). Keep it as a run-time value; never synthesize a random amount. | `1 ≤ step 2 amount < step 1 amount` |
| 2 | Any provisional/placeholder amount must still satisfy Element 58: at most 7 digits for non-Amex cards. | Length check against the card network |
| 3 | Step 2 chip Tag 9F02 must be built from the **step 2** amount (Card B's own transaction), not copied from step 1. | `9F02 == step 2 NonfuelAmount` (12 digits, zero-padded) |
| 4 | Segment 100 `SegmentLength` must be computed **after** the run-time amount is substituted. | Recompute: sum of fields + one separator per field |

### Follow-on steps checked against their original step

#### T5 – Purchase → Purchase Reversal (TC-001767)

Step 2 (reversal) against step 1 (purchase): D-01, D-02 and D-12 apply to step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8020` (Transaction Type '8' = Purchase reversal; Card Type '020' = Credit) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `017671` (original `017671`) | PASS |
| Same Account Number as the original | `5180891616558428` (original `5180891616558428`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `07` (original `07`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 310120155181106 -> 3101) | `3101` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

---

## 5. What passes

Aggregated over all 7 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `EMV Financial Transaction Request`/`Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `03`/`02` | – |
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `0020`/`8020`; Account Number `5139485532100688`/`5119561504398924`/`5180378702966947`; Card Discretionary Block Data `290720142194190`/`270520164766496`/`320920175165938`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `017591`/`017611`/`017621`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-001763 step 2); Nonfuel Amount (BLOCKED in TC-001763 step 2); Approval Number (BLOCKED in TC-001767 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `071`; Variable Information (Table 031) `12` | – |
| Segment 130 and EMV chip data | Segment Type `130`; Segment Length `1181`; CA Public Key File Checksum `2012091700000100000102171`; EMV Chip Data Length `147`; EMV Chip Data `294 hex characters = 147 bytes, 22 tags`; Tag 9C Transaction Type `00`; Tag 5F2A Transaction Currency Code `0840`; Tag 9F1A Terminal Country Code `0840`; Tag 9F27 Cryptogram Information Data `80`; Tag 9F34 CVM Results `5E0302`; EMV Additional Information Indicator `001`; EMV Additional Information Length `984` | EMV Card Sequence Number (FAIL in TC-001759 step 1; FAIL in TC-001761 step 1; FAIL in TC-001762 step 1; FAIL in TC-001763 step 1; FAIL in TC-001763 step 2; FAIL in TC-001767 step 1); Tag 9F02 Amount, Authorized (FAIL in TC-001763 step 2); EMV Additional Information (FAIL in TC-001759 step 1; FAIL in TC-001761 step 1; FAIL in TC-001762 step 1; FAIL in TC-001763 step 1; FAIL in TC-001763 step 2; FAIL in TC-001767 step 1) |
| Step-to-step links | 6 of 6 checks PASS | – |
| Expected response codes | 7 of 7 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 42 of 42 hold | – |
| TC-declared `link_defs` | 5 of 5 hold; 2 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 15 of 25 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form; p.161: Found on a neighbouring page, not the cited one (p.160) |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5180891616558428=3101.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Exact corrected value of Element 192 / framing of Element 118 for indicator 001 | Appendix T (p.701) lists *"Table ID n3 Fixed value: 001"* and *"Table Length n Fixed"* but never states the Table Length width. The value may be `EMVYES` alone (length `006`) or framed with Table ID + Table Length (longer). | all EMV requests | Appendix T, PDF p.701 (printed T-1) | BUYPASS / SME or a certified device trace |
| Q-02 | Packed-BCD 2-byte prefix (`0145`) inside Element 190 counted in Element 189 (`147`) | Matches the Appendix R example (*"01\06 EMV/Chip Data length (BCD packed)"*, p.697) and Element 190 (*"All fields after the table length are in binary format."*), but the field boundary is not stated explicitly. | all EMV requests | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138); Element 189, PDF p.477 (printed 13-137) | Producer adapter declaration / SME |
| Q-03 | The CA Public Key File Checksum is a sample value | The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Element 187, PDF p.477 (printed 13-137) | Host CA Public Key file |
| Q-04 | TVR says 'online PIN entered' on a transaction without online PIN | TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Element 190, PDF p.478 (printed 13-138) | Card simulator |
| Q-05 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Element 190, PDF p.478 (printed 13-138) | Card simulator / HSM |
| Q-06 | Contactless chip data reuses the contact tag set | The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Appendix R, PDF p.697 (printed R-1) | MasterCard contactless (M/Chip) kernel trace |
| Q-07 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-08 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-09 | The quote cited for p.161 is on the neighbouring page | The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160). | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | – | Test case generator (citation) |
| Q-10 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-11 | Marked 'ready' although the terminal is a placeholder | The test case status is 'ready', but its Terminal Identifier is still a placeholder. | TC-001759, TC-001767 | Element 102, PDF p.417 (printed 13-77) | Test case owner |
| Q-12 | The quote cited for p.87 comes from an unrelated section | The quote is word-for-word, but it comes from Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8); that section is about FSA/HRA healthcare benefit cards. | TC-001763 | Section 10.1.6.1.3, PDF p.87 (printed 10-8); Section 10.1.2.1, PDF p.84 (printed 10-5) | Test case generator (citation) |
| Q-13 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-001763 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |
| Q-14 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S, reversing Authorization Only codes 3 and 5), while this step is a Purchase Reversal (Prompt Code 8020, Transaction Type 8). | TC-001767 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.546 (printed G-2); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |

---

## 7. Recommendations for the AI test data generator

1. **E188:** zero-pad Tag 5F34 to 3 bytes (`001`), or send 3 spaces when 5F34 is absent (D-01).
2. **E191/E192/E118:** for indicator `001`, populate EMV Table Data with `EMVYES` / `EMVNOT` per Appendix T-1 (p.701), and set E192 to the resulting length once Q-01 is confirmed. Do not synthesize free text from the 984 maximum; apply the Appendix T allowed values instead of skipping `valid_values_parsed` (D-02).
3. **Recompute the Segment 130 length** after these fixes. With `EMVYES` and no framing, `1181` becomes `0204` (-978 for E118, +1 for E188).
4. **Scenario fidelity:**
   - T2 expired card: use a card whose expiry is earlier than the run date (before `2611` as YYMM), and include Tag 5F24 and the TVR bit "expired application" in the chip data (D-03).
   - T3 online PIN: populate E33 (DUKPT KSN + PIN block) and set 9F34 to online PIN (`42xxxx` / `02xxxx`), or mark the test case blocked until the client supplies the card and key (D-04).
   - T4 split tender: derive the step 2 amount and Tag 9F02 from the step 1 Approved Amount. Never exceed 7 digits for MasterCard, and compute the Segment Length after substitution (D-05).
5. **Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request (10 digits, values from Appendix Q, for the certified terminal) – Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* (D-12).
6. **Contactless:** generate contactless-specific chip data rather than reusing the contact template (Q-06).
7. **Avoid reusing identical generated values** across different test cases (D-02).
8. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-07, Q-08, Q-09, Q-12, Q-14). The data values are not affected.
9. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-10).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS2 | TC-001759 | T1 – Purchase approved | FAIL | [TC-001759_ATL105_Field_Validation.md](./TC-001759_ATL105_Field_Validation.md) |
| BR1_TS2 | TC-001761 | T2 – Expired EMV card → sent online → host decides | FAIL | [TC-001761_ATL105_Field_Validation.md](./TC-001761_ATL105_Field_Validation.md) |
| BR1_TS2 | TC-001762 | T3 – EMV online PIN → approved | FAIL | [TC-001762_ATL105_Field_Validation.md](./TC-001762_ATL105_Field_Validation.md) |
| BR1_TS2 | TC-001763 | T4 – Partial approval → remainder on 2nd card (split tender) | FAIL | [TC-001763_ATL105_Field_Validation.md](./TC-001763_ATL105_Field_Validation.md) |
| BR1_TS2 | TC-001767 | T5 – Purchase → Purchase Reversal | FAIL | [TC-001767_ATL105_Field_Validation.md](./TC-001767_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
