# ATL105 Test Data Validation – Findings Summary – Merchandise Return – EMV Contact Chip

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Merchandise Return |
| Scenario | BR1_TS21 Merchandise Return – EMV Contact Chip (scenario status: valid) |
| Entry mode | EMV Contact Chip (Table 005 PAN entry mode `05` = chip read) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (lines 419, 420) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | 2 of the 3 test cases of this scenario (T1, T5), 3 test data files (1 multi-step flow). Not sampled: 1 T11 device-only (no host message) |
| Date | 2026-11-03 |
| Overall result | **2 / 2 test cases FAIL** – 3 defects common to every test case (D-01, D-02, D-12) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step (EMV Financial Transaction Request when Segment 130 is sent; Financial Transaction Request on follow-on steps), Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167, Section 11.8.1 p.205, Section 11.8.1 p.206 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 7, U for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.546 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); follow-on steps send the truncated expiry YYMM only; card-number check digit and MasterCard range | Element 12 p.371, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 030 (POS Condition Code) for EMV and Credential on File transactions; Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Appendix I p.568 |
| Segment 130 (9 fields) | Elements 187-192 and 118; EMV Chip Data TLV decode; EMV Additional Information (Appendix T); Segment Length recomputed (chip data counted in binary bytes) | Section 12.20 p.270, Element 187 p.476, Element 188 p.477, Element 189 p.477, Element 190 p.478, Element 191 p.479, Element 192 p.480, Element 118 p.439, Appendix R p.697, Appendix T p.701 |
| EMV cross-field | 9F02 vs amount, 9C vs transaction type, AID (9F06/84) present, 5F2A/9F1A, 5F34 vs Element 188, 9F27, 9F34, Tags 5A/57 absent | Element 190 p.478, Appendix R p.697 |
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
| T1 – Merchandise Return processed | TC-002140 (BR1_TS21_TC1) | 1 | **FAIL** | 3 | 0 | 9 | D-01, D-02, D-12 |
| T5 – Merchandise Return → Void of a Merchandise Return | TC-002141 (BR1_TS21_TC2) | 2 | **FAIL** | 3 | 1 | 8 | D-01, D-02, D-12 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case (2 EMV test data files)

Follow-on reversal files without Segment 130 (`TD-TC-002141.step2.void-return.json`) are allowed: Section 10.14.2.4 Supported Transaction Types (EMV), p.161 (printed 10-82): *"EMV data is not required on Reversal transactions."*

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
| TC-002140 | TD-TC-002140.json | 47 | `01` | `01` | `001` |
| TC-002141 | TD-TC-002141.step1.return.json | 47 | `01` | `01` | `001` |

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data

| | |
|---|---|
| Location | `"EMV Request Data Segment"`, **lines 50–52** of each EMV test data file |
| Generated value | Indicator `001`, Length `984`, Information = 984 generated A–Z/0–9 characters (e.g. `6037QIOWU9XTN9LGN2JF…KYKDG0TN`) |
| Expected | Indicator `001` with EMV Table Data value **`EMVYES`** or **`EMVNOT`** |
| Spec chain | 1. **E191** (p.479): *"001 EMV table data"* → `001` PASS<br>2. **E192** (p.480): *"Valid Codes/Values: 001–985"* → `984` **PASS on its own**<br>3. **E118 EMV Additional Information** (p.439): *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here."*<br>4. **Appendix T-1** (p.701): EMV Table Data – *"Maximum Length n6 Len.: 6"*; request values *"EMVYES This code indicates that the device is able to receive EMV Table Data."* / *"EMVNOT This code indicates that the device is unable to receive EMV Table Data."* → **FAIL** |
| Root cause | The test case marks this value `method: format_synthesis` with flag `UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)`. The generator filled the generic 984-character maximum instead of applying Appendix T. |
| Certainty | **Certain** that the content is invalid. **Not certain** what the exact corrected Element 192 value is (see §6, Q-01). |

**Clarification on Element 192:** Element 192 = `984` is a valid value in range and equals the actual content length, so the report marks it **PASS**. The defect is the **content** of Element 118. Element 192 is consistent with that content, but the content itself is not allowed for indicator `001`.

#### D-02 evidence by file

| Test case | Test data file | Lines | Indicator | Length | Value (start…end) |
|---|---|---|---|---|---|
| TC-002140 | TD-TC-002140.json | 50–52 | 001 | 984 | `6037QIOWU9XTN9LGN2JF…KYKDG0TN` |
| TC-002141 | TD-TC-002141.step1.return.json | 50–52 | 001 | 984 | `U5H80I9T31ZFO5Y07ZNF…HFJL0IKR` |

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002140 | 1 (return) | TD-TC-002140.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002141 | 1 (return) | TD-TC-002141.step1.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |

**Why (TC-002140):** This step is an EMV transaction (Segment 130 is sent). Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.

Spec:
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

---

## 4. Scenario-specific defects

None.

### Follow-on steps checked against their original step

#### T5 – Merchandise Return → Void of a Merchandise Return (TC-002141)

Step 2 (void return) against step 1 (return): D-01, D-02 and D-12 apply to step 1; step 2 has no defect.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `U020` (Transaction Type 'U' = Void of a Merchandise Return; Card Type '020' = Credit) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `021411` (original `021411`) | PASS |
| Same Account Number as the original | `5109464770273677` (original `5109464770273677`) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Follow-on transaction: expiry as YYMM only (original track data 301220140014868 -> 3012) | `3012` | PASS |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | `084` | PASS |

---

## 5. What passes

Aggregated over all 3 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `EMV Financial Transaction Request`/`Financial Transaction Request`; Message Format Version Identifier `ATL105`; Number of Segments `03`/`02` | – |
| Segment 100 | Segment Type `100`; Segment Length `089`/`084`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `7020`/`U020`; Account Number `5151732161656347`/`5109464770273677`; Card Discretionary Block Data `270720138685725`/`301220140014868`/`3012`; Encrypted PIN Block Data `(empty)`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Nonfuel Amount `1500`; Tax Amount `(empty)`; Cash Amount `(empty)`; Sequence Number `021401`/`021411`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Approval Number (BLOCKED in TC-002141 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `051`; Variable Information (Table 031) `12` | – |
| Segment 130 and EMV chip data | Segment Type `130`; Segment Length `1181`; CA Public Key File Checksum `2012091700000100000102171`; EMV Chip Data Length `147`; EMV Chip Data `294 hex characters = 147 bytes, 22 tags`; Tag 9F02 Amount, Authorized `000000001500`; Tag 9C Transaction Type `20`; Tag 5F2A Transaction Currency Code `0840`; Tag 9F1A Terminal Country Code `0840`; Tag 9F27 Cryptogram Information Data `80`; Tag 9F34 CVM Results `5E0302`; EMV Additional Information Indicator `001`; EMV Additional Information Length `984` | EMV Card Sequence Number (FAIL in TC-002140 step 1; FAIL in TC-002141 step 1); EMV Additional Information (FAIL in TC-002140 step 1; FAIL in TC-002141 step 1) |
| Step-to-step links | 4 of 4 checks PASS | – |
| Expected response codes | 1 of 1 PASS (`0` = Approved - Purchase/Capture) | – |
| TC-declared `rule_checks` | 17 of 17 hold | – |
| TC-declared `link_defs` | 3 of 3 hold; 0 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | Every value, except run-time placeholders (BLOCKED) | – |
| Spec quotes in the test cases | 4 of 8 found word-for-word on the cited page | p.511: Not word-for-word: all its words are on the cited page, but not in this form; p.161: Found on a neighbouring page, not the cited one (p.160) |

**Note for the team that builds the wire message (INFO):**

- **Join Account Number and expiry with '=' in the wire message** – The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. The message builder sends 5109464770273677=3012.

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Exact corrected value of Element 192 / framing of Element 118 for indicator 001 | Appendix T (p.701) lists *"Table ID n3 Fixed value: 001"* and *"Table Length n Fixed"* but never states the Table Length width. The value may be `EMVYES` alone (length `006`) or framed with Table ID + Table Length (longer). | all EMV requests | Appendix T, PDF p.701 (printed T-1) | BUYPASS / SME or a certified device trace |
| Q-02 | Packed-BCD 2-byte prefix (`0145`) inside Element 190 counted in Element 189 (`147`) | Matches the Appendix R example (*"01\06 EMV/Chip Data length (BCD packed)"*, p.697) and Element 190 (*"All fields after the table length are in binary format."*), but the field boundary is not stated explicitly. | all EMV requests | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138); Element 189, PDF p.477 (printed 13-137) | Producer adapter declaration / SME |
| Q-03 | The CA Public Key File Checksum is a sample value | The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value. | TC-002140, TC-002141 | Element 187, PDF p.477 (printed 13-137) | Host CA Public Key file |
| Q-04 | TVR says 'online PIN entered' on a transaction without online PIN | TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator. | TC-002140, TC-002141 | Element 190, PDF p.478 (printed 13-138) | Card simulator |
| Q-05 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer. | TC-002140, TC-002141 | Element 190, PDF p.478 (printed 13-138) | Card simulator / HSM |
| Q-06 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-002140, TC-002141 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-07 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002140, TC-002141 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-08 | The quote cited for p.511 is not word-for-word in ATL105 | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002140, TC-002141 | Appendix C, PDF p.511 (printed C-1) | Test case generator (citation) |
| Q-09 | The quote cited for p.161 is on the neighbouring page | The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160). | TC-002140, TC-002141 | – | Test case generator (citation) |
| Q-10 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002140, TC-002141 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-11 | Marked 'ready' although the terminal is a placeholder | The test case status is 'ready', but its Terminal Identifier is still a placeholder. | TC-002140 | Element 102, PDF p.417 (printed 13-77) | Test case owner |

---

## 7. Recommendations for the AI test data generator

1. **E188:** zero-pad Tag 5F34 to 3 bytes (`001`), or send 3 spaces when 5F34 is absent (D-01).
2. **E191/E192/E118:** for indicator `001`, populate EMV Table Data with `EMVYES` / `EMVNOT` per Appendix T-1 (p.701), and set E192 to the resulting length once Q-01 is confirmed. Do not synthesize free text from the 984 maximum; apply the Appendix T allowed values instead of skipping `valid_values_parsed` (D-02).
3. **Recompute the Segment 130 length** after these fixes. With `EMVYES` and no framing, `1181` becomes `0204` (-978 for E118, +1 for E188).
4. **Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request (10 digits, values from Appendix Q, for the certified terminal) – Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* (D-12).
5. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-06).
6. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-07, Q-08, Q-09). The data values are not affected.
7. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-10).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS21 | TC-002140 | T1 – Merchandise Return processed | FAIL | [TC-002140_ATL105_Field_Validation.md](./TC-002140_ATL105_Field_Validation.md) |
| BR1_TS21 | TC-002141 | T5 – Merchandise Return → Void of a Merchandise Return | FAIL | [TC-002141_ATL105_Field_Validation.md](./TC-002141_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```
