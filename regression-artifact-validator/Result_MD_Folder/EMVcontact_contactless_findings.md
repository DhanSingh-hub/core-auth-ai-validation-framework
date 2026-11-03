# ATL105 Test Data Validation – Findings Summary

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Purchase/Capture |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-3** (`specifications/ATL105/docs/specs`) |
| Test data under review | `C:\Users\F5QGZOJ\Downloads\31-10-out\mastercard_credit\test_data` |
| Test case catalog | `C:\Users\F5QGZOJ\Downloads\31-10-out\mastercard_credit\test_cases\test_cases.jsonl` |
| Validator | `regression-artifact-validator` – `TestDataSpecFieldValidationRunner` + trained Appendix R / T / I / J oracles, Appendix D / E / G catalogs |
| Scope | 10 test cases (5 EMV Contact, 5 EMV Contactless), 14 test data files (4 two-step flows) |
| Date | 2026-11-02 |
| Overall result | **10 / 10 test cases FAIL** – 2 defects common to every EMV request, plus scenario-specific defects |

Per-test-case evidence: see [README.md](./README.md) (index) and the individual `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder.

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per leg, Element 55 `ATL105`, Element 63 = segments present, Data Section 3 slot count, max message length 4,000 | 11.1, 11.8.1 |
| Segment 100 (17 fields) | Field set and order, R/C presence, format, length, valid values, Segment Length recomputed | 12.1; Ch. 13 E2, E5, E12, E17, E33, E41, E44, E49, E58, E78, E79, E84–86, E99, E102, E121; App. D / E / G |
| Segment 111 | Table 005 (POS entry mode), Table 031 (terminal capability), Element 112 = data length, Segment Length | 12.10; App. I, App. I-20, App. J |
| Segment 130 (9 fields) | E187–E192, E118; EMV chip data TLV decode (App. R oracle); EMV Additional Information (App. T oracle); Segment Length recomputed | 12.20; Ch. 13 E187–E192, E118; App. R, App. T |
| EMV cross-field | 9F02 vs amount, 9C vs transaction type, AID, 5F2A/9F1A, 5F34 vs E188, 9F27, 5A/57 absent | SEG130-R-009, App. R, Ch. 13 E190 |
| Test objective | Data actually matches the scenario (expired card, online PIN, split tender, reversal) | TC template, ATL105 p.84–85, p.160–162 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the TC executed against the TD | TC catalog |
| Generation fidelity | Every TD value compared with the TC request value | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the TC's own objective · **REVIEW** not assertable from the spec / snapshot alone (placeholder, BUYPASS-assigned or runtime value, spec gap) · **N/A** conditional field correctly not populated.

---

## 2. Results by test case

| Scenario | EMV Contact | Result | EMV Contactless | Result |
|---|---|---|---|---|
| T1 – Purchase approved | TC-001722 | FAIL (4 F / 9 R) | TC-001759 | FAIL (4 F / 9 R) |
| T2 – Expired card sent online | TC-001723 | FAIL (5 F / 9 R) | TC-001761 | FAIL (5 F / 10 R) |
| T3 – EMV online PIN | TC-001724 | FAIL (6 F / 8 R) | TC-001762 | FAIL (6 F / 9 R) |
| T4 – Partial approval → split tender | TC-001726 | FAIL (10 F / 21 R) | TC-001763 | FAIL (10 F / 23 R) |
| T5 – Purchase → Purchase Reversal | TC-001730 | FAIL (4 F / 14 R) | TC-001767 | FAIL (4 F / 15 R) |

F = FAIL count, R = REVIEW count. Contact and contactless produce the **same defect pattern** for each scenario.

---

## 3. Defects common to every EMV request (13 test data files)

These affect every file that contains Segment 130. The two reversal legs (`TD-TC-001730.step2.reversal.json`, `TD-TC-001767.step2.reversal.json`) have no Segment 130, which is legal (SEG130-R-017: "EMV data is not required on Reversal transactions").

### D-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3

| | |
|---|---|
| Location | `"EMV Request Data Segment" → "EMVCardSequenceNumber"`, **line 47** of each EMV test data file |
| Generated value | `01` |
| Expected | `001` |
| Spec | Ch. 13 Element 188 (p.470): *"Character Type: AN, Maximum Length: 3 bytes. Representation: **Fixed length of three bytes, right-aligned and padded with zeroes**. Space-filled if EMV Tag 5F34 is not provided … Valid Codes/Values: 000—099"* |
| Certainty | **Certain.** The chip data carries Tag 5F34 = `01`, so the value is right but the width is wrong. |
| Side effect | The declared Segment 130 length (`1181`) was computed with the 2-byte value and also changes by +1 when corrected. |

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data

| | |
|---|---|
| Location | `"EMV Request Data Segment"`, **lines 50–52** of each EMV test data file |
| Generated value | Indicator `001`, Length `984`, Information = 984 random A–Z/0–9 characters (e.g. `XPXZRQBX814S3CAQ0ZNJ…C5K8MMTF`) |
| Expected | Indicator `001` with EMV Table Data value **`EMVYES`** or **`EMVNOT`** |
| Spec chain | 1. **E191** (p.472): `001` = *EMV table data* → PASS<br>2. **E192** (p.473): valid 001–985 → `984` **PASS on its own**<br>3. **E118 EMV Additional Information** (Ch. 13): *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here … Please refer to Appendix T"*<br>4. **Appendix T-1** (p.694): EMV Table Data – *"Maximum Length n6, Len.: 6 … Request Valid Codes/Values: **EMVYES** / **EMVNOT**"* → **FAIL** |
| Root cause | The TC marks this value `method: format_synthesis` with flag `UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped`. The generator filled the generic 984-character maximum instead of applying Appendix T. The same random string is reused across test cases (TC-001722/1723/1724 → `XPXZ…`; TC-001759/1761/1762 → `EAMH…`). |
| Certainty | **Certain** that the content is invalid. **Not certain** what the exact corrected Element 192 value is (see §6, Q-01). |

**Clarification on Element 192:** Element 192 = `984` is a valid value in range and equals the actual content length, so the report marks it **PASS**. The defect is the **content** of Element 118. Element 192 is consistent with that content, but the content itself is not allowed for indicator `001`.

#### D-02 evidence by file

| Entry mode | Test data file | Lines | Indicator | Length | Value (start…end) |
|---|---|---|---|---|---|
| Contact | TD-TC-001722.json | 50–52 | 001 | 984 | `XPXZRQBX814S3CAQ0ZNJ…C5K8MMTF` |
| Contact | TD-TC-001723.json | 50–52 | 001 | 984 | `XPXZRQBX814S3CAQ0ZNJ…C5K8MMTF` |
| Contact | TD-TC-001724.json | 50–52 | 001 | 984 | `XPXZRQBX814S3CAQ0ZNJ…C5K8MMTF` |
| Contact | TD-TC-001726.step1.purchase.json | 50–52 | 001 | 984 | `1J0FVRBA9PUYLZQWAG9W…B6MIFT99` |
| Contact | TD-TC-001726.step2.split-tender.json | 50–52 | 001 | 984 | `SSWN9VUQDGPTG8693ZN5…AT9QRD5X` |
| Contact | TD-TC-001730.step1.purchase.json | 50–52 | 001 | 984 | `13ELX9RB06PAQ9EF0S7L…LHKNM76H` |
| Contactless | TD-TC-001759.json | 50–52 | 001 | 984 | `EAMHC50ODTH426QT444W…Y6GAJQCX` |
| Contactless | TD-TC-001761.json | 50–52 | 001 | 984 | `EAMHC50ODTH426QT444W…Y6GAJQCX` |
| Contactless | TD-TC-001762.json | 50–52 | 001 | 984 | `EAMHC50ODTH426QT444W…Y6GAJQCX` |
| Contactless | TD-TC-001763.step1.purchase.json | 50–52 | 001 | 984 | `IPCK443KAYGREU076B2N…HPV8ZV3S` |
| Contactless | TD-TC-001763.step2.split-tender.json | 50–52 | 001 | 984 | `R5ZSEAKMG6OP2IVSST8F…X986X6NE` |
| Contactless | TD-TC-001767.step1.purchase.json | 50–52 | 001 | 984 | `UK2O2I0MT45NBF125C5J…PEUTE7EU` |

---

## 4. Scenario-specific defects

### D-03 – Expired-card test data uses a non-expired card (T2)

| TC | File / line | Element 12 value | Expiry (YYMM) | Expected |
|---|---|---|---|---|
| TC-001723 (Contact) | TD-TC-001723.json : 15 | `311020127370826` | `3110` = Oct 2031 | earlier than `2611` |
| TC-001761 (Contactless) | TD-TC-001761.json : 15 | `290720142194190` | `2907` = Jul 2029 | earlier than `2611` |

Spec: ATL105 p.160 (*"The device does not perform expiration date validation in an EMV card transaction"*) and Ch. 13 E12 (*"merchants must send a value even for expired cards"*). The test cannot exercise its objective with a valid card. TVR byte 2 "Expired application" is also not set, and Tag 5F24 is absent. The TC already lists *"Expired EMV test card"* as an open client input.

### D-04 – Online-PIN test data carries no PIN and a signature CVM (T3)

| TC | Finding | Location | Value | Expected |
|---|---|---|---|---|
| TC-001724 / TC-001762 | Encrypted PIN Block Data (E33) empty | line 16 | `""` | DUKPT KSN + PIN block (AN36) – Ch. 12.1 F8, Ch. 13 E33; TC execution step *"PIN block is sent online"* |
| TC-001724 / TC-001762 | CVM Results 9F34 = signature | EMVChipData, Tag 9F34 | `5E0302` (CVM code 1E = signature) | CVM code 02 / 42 (enciphered PIN verified online) – EMV Book 3 Annex C3 |

**Clarification – this is not "credit always needs a PIN".** For credit, Element 33 is optional (ATL105 p.162: *"PINs are an optional field for a credit/signature debit transaction"*; Element 33: *"Required for debit and EBT"*). So an empty Element 33 is **correct** for normal credit purchases (e.g. TC-001722, TC-001759), and D-04 is not raised there.

D-04 applies only to the **online-PIN test cases**, because:
- ATL105 p.162 states online PIN *"is applicable for **credit** or debit transactions, in which the PIN is electronically sent to and validated by the card issuer"*, and MasterCard requires PIN-capable devices to *"submit a PIN in the authorization request"*.
- The TC objective is *"EMV online PIN → approved"*, and its execution step is *"the PIN block is sent online (Encrypted PIN Block Data, DUKPT)"*.
- The generated data has no PIN block and a signature CVM, so it is a signature credit purchase (same as T1), not an online-PIN test.

**Classification:** test-objective defect, **blocked by client input**. The TC itself lists *"Test card with a known online PIN and the test DUKPT key (p.57)"* as an open item; without them a valid PIN block cannot be generated.

**Ask to the AI Dev team:** do **not** add a PIN to credit test data in general. For online-PIN test cases only, either populate Element 33 (DUKPT KSN + PIN block) and set 9F34 to online PIN once the test card/key is supplied, or mark the TC **blocked – needs client input** instead of releasing signature data under an online-PIN objective.

### D-05 – Split-tender second card carries the full original amount in chip data (T4)

| TC | Finding | Location | Value | Expected |
|---|---|---|---|---|
| TC-001726 / TC-001763 | Step 2 Tag 9F02 (Amount Authorized) | `*.step2.split-tender.json`, EMVChipData | `000000001500` (= step 1 full amount) | Remaining balance = requested – Approved Amount (E6) of the partial approval (p.84); SEG130-R-009 |
| TC-001726 / TC-001763 | Step 2 Segment 100 length vs amount | `*.step2.split-tender.json`, SegmentLength `093` | Declared length implies an **8-digit** Nonfuel Amount | Element 58: max **7 digits** for non-Amex cards (Ch. 13 E58, p.384) |
| TC-001726 / TC-001763 | TC provisional step 2 amount | test_cases.jsonl | `24677595` | Must be < `1500` and ≤ 7 digits |

The TD itself correctly carries a runtime expression for step 2 `NonfuelAmount` (`<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`). That is REVIEW, not a defect. The chip amount and the declared segment length were generated from the invalid provisional value.

#### D-05 explained with a simple example (for the AI Dev team)

**The story the test is meant to tell**

A customer buys something for **$15.00** and pays with two cards:

1. **Card A** (`5144283846699542`) is asked for $15.00 but only has, say, **$10.00** available. The issuer approves only $10.00 (partial approval, Response Code `F`).
2. The device asks for the **remaining $5.00**, and the customer pays it with **Card B** (`5172304375757090`).

Card B must be charged only **the leftover**: $15.00 − $10.00 = $5.00. The leftover is always **less than $15.00**, because Card A paid something (ATL105 p.84: *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."*).

**What the generated test data contains**

Step 1, Card A (`TD-TC-001726.step1.purchase.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `1500` | $15.00 | ✅ |
| Chip amount, Tag 9F02 | `000000001500` | $15.00 | ✅ matches |

Step 2, Card B (`TD-TC-001726.step2.split-tender.json`):

| Field | Value | Meaning | OK? |
|---|---|---|---|
| NonfuelAmount (line 19) | `<RemainingBalance = …>` | "Fill in the leftover at runtime" (e.g. $5.00) | ✅ correct approach |
| Chip amount, Tag 9F02 | `000000001500` | **$15.00** | ❌ wrong |
| SegmentLength (line 7) | `093` | Calculated as if the amount had **8 digits** | ❌ wrong |

**Problem 1: Card B's chip says $15.00.** The request asks Card B to pay the leftover (e.g. $5.00), but Card B's chip data says it is paying the full $15.00, copied from Card A. It is like a receipt for the second card showing "$15.00" when only $5.00 was owed. Whatever Card A gets approved, the leftover is always less than $15.00, so `1500` on Card B is always wrong.

**Problem 2: the length was counted with an impossible amount.** `SegmentLength` = 93 is the character count of Segment 100. All other fields plus separators add up to **85**, so 93 − 85 = **8 characters** were reserved for the amount. The 8-digit value the AI used is in its own test case (`test_cases.jsonl` → TC-001726 → `steps[1].request` → `Nonfuel Amount`): **`24677595` = $246,775.95**. That is wrong twice:

- It is **larger than the $15.00 bill**, and a leftover can never exceed the bill.
- ATL105 Element 58 (p.384) allows at most **7 digits** for Mastercard: *"A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of 7 bytes is allowed for all other card types."*

A real leftover such as $5.00 (`500`, 3 digits) gives a Segment Length of about **088**, not 093.

**In one line:** Card B should pay only the leftover (less than $15.00), but the AI gave Card B's chip **$15.00** and built the message length around an impossible amount of **$246,775.95**.

The contactless test (TC-001763) has exactly the same problem with the same values.

**What the generator must do next run**

| # | Rule | Check before release |
|---|---|---|
| 1 | Step 2 amount = step 1 requested amount − step 1 Approved Amount (Element 6 from the step 1 response). Keep it as a runtime value; never synthesize a random amount. | `1 ≤ step 2 amount < step 1 amount` |
| 2 | Any provisional/placeholder amount must still satisfy Element 58: ≤ 7 digits for non-Amex cards. | Length check against the card network |
| 3 | Step 2 chip Tag 9F02 must be built from the **step 2** amount (Card B's own transaction), not copied from step 1. | `9F02 == step 2 NonfuelAmount` (12-digit, zero-padded) |
| 4 | Segment 100 `SegmentLength` must be computed **after** the runtime amount is substituted. | Recompute: sum of fields + one separator per field |

### T5 – Purchase → Reversal (TC-001730 / TC-001767)

Only D-01 and D-02 apply (on the purchase leg). The reversal leg is correct:

| Check | Result |
|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8020` | PASS |
| Segment 130 absent on reversal | PASS (SEG130-R-017) |
| Sequence Number, Terminal Identifier, Account Number, expiry, Table 005, amount, Partial Approval Indicator = original | PASS |
| Approval Number (E5) is a runtime link to the step 1 response | REVIEW. Declared length `084` implies exactly 6 characters, consistent with E5 fixed length 6. |

---

## 5. What passes in all test cases

| Area | Result |
|---|---|
| Message type, E55 `ATL105`, E63 segment count, message length ≤ 4,000 | PASS |
| Segment 100: all 17 fields present in spec order; Segment Length `089` recomputed exactly | PASS |
| E44 `0`, E86 6-digit unique per terminal, E121 `1`, E5 empty on purchases | PASS |
| E78 Prompt Code `0020` (App. G type 0 Purchase, App. E 020 Credit) | PASS |
| E2 PAN: Luhn, MasterCard IIN, ≤ 24; E2 + E12 Track 2 ≤ 37; service code 2xx (chip) | PASS |
| E102 Terminal ID 13–22 chars, state code `72` valid ANSI (App. D) | PASS (format); assignment is REVIEW |
| Segment 111: Table 005 `051` (contact) / `071` (contactless) per App. J; Table 031 `12` (App. I-20); Segment Length `026` | PASS |
| Segment 130: E187 N25, E189 = chip bytes (147), Segment Length `1181` consistent with generated values | PASS |
| EMV chip TLV (App. R oracle): well-formed, AID A0000000041010 present, no 5A/57, no duplicates; 9F02 = amount, 9C = 00, 5F2A/9F1A = 0840 | PASS |
| TC-declared `rule_checks` and `link_defs` | PASS (runtime links are REVIEW) |
| TD value = TC request value (generation fidelity) | PASS, except runtime placeholders (REVIEW) |

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it cannot be decided from the spec | Needed from |
|---|---|---|---|
| Q-01 | Exact corrected value of E192 / framing of E118 for Table 001 | Appendix T-1 lists *Table ID n3* and *Table Length n, Fixed* but never states the Table Length width. The value may be `EMVYES` alone (length `006`) or framed with Table ID + Table Length (longer). | BUYPASS / SME or a certified device trace |
| Q-02 | Packed-BCD 2-byte prefix (`0145`) inside E190 counted in E189 (`147`) | Matches the App. R-1 example (`01\06`), but the field boundary is not stated explicitly | Producer adapter declaration / SME |
| Q-03 | Contactless chip payload (TC-001759/1761/1762/1763/1767) | Identical tag set and values to the contact template (no 9F6E/9F6D/9F7C, CVM = signature). App. R says its tag list is not exhaustive. | MasterCard contactless (PayPass) kernel trace |
| Q-04 | Terminal Identifier `JX72948216050257001` | Device Type, Merchant and Device numbers are BUYPASS-assigned; merchant is a placeholder | Client merchant list |
| Q-05 | CA Public Key File Checksum `2012091700000100000102171` | Must equal the host's current CA key file checksum; the value is a QE sample | Host CA key file |
| Q-06 | Cryptogram / IAD / ATC / UN / TVR | EMV placeholders; authenticity needs a card simulator / HSM (SEG130-R-010) | Card simulator |
| Q-07 | TVR byte 3 "online PIN entered" set on non-PIN transactions | TVR is a placeholder; must be consistent with the CVM once simulated | Card simulator |
| Q-08 | `test_cases.jsonl` lines 3–4 (TC-001722, TC-001725) are malformed JSON (`{{` / missing `{`) | Parsed by brace repair; the source file was not modified | Fix the catalog file |

---

## 7. Recommendations for the AI test data generator

1. **E188:** zero-pad Tag 5F34 to 3 bytes (`001`), or send 3 spaces when 5F34 is absent.
2. **E191/E192/E118:** for indicator `001`, populate EMV Table Data with `EMVYES` / `EMVNOT` per Appendix T-1, and set E192 to the resulting length once Q-01 is confirmed. Do not synthesize free text from the 984 maximum. Apply Appendix T allowed values instead of skipping `valid_values_parsed`.
3. **Recompute Segment 130 length** after fixes 1–2. With `EMVYES` and no framing, `1181` becomes `0204` (−978 for E118, +1 for E188).
4. **Scenario fidelity:**
   - T2: use a card with expiry earlier than the run date, and include Tag 5F24 and TVR "expired application".
   - T3 online PIN: populate E33 (DUKPT) and set 9F34 to online PIN (`42xxxx`/`02xxxx`).
   - T4: derive the step 2 amount and Tag 9F02 from the step 1 Approved Amount. Never exceed 7 digits for MasterCard, and compute the Segment Length after substitution.
5. **Contactless:** generate contactless-specific chip data rather than reusing the contact template (Q-03).
6. **Avoid reusing identical random values** across different test cases (D-02).

---

## 8. Report index

| Entry mode | TC | Scenario | Report |
|---|---|---|---|
| EMV Contact | TC-001722 | T1 Purchase approved | [TC-001722_ATL105_Field_Validation.md](./TC-001722_ATL105_Field_Validation.md) |
| EMV Contact | TC-001723 | T2 Expired card | [TC-001723_ATL105_Field_Validation.md](./TC-001723_ATL105_Field_Validation.md) |
| EMV Contact | TC-001724 | T3 Online PIN | [TC-001724_ATL105_Field_Validation.md](./TC-001724_ATL105_Field_Validation.md) |
| EMV Contact | TC-001726 | T4 Split tender | [TC-001726_ATL105_Field_Validation.md](./TC-001726_ATL105_Field_Validation.md) |
| EMV Contact | TC-001730 | T5 Purchase → Reversal | [TC-001730_ATL105_Field_Validation.md](./TC-001730_ATL105_Field_Validation.md) |
| EMV Contactless | TC-001759 | T1 Purchase approved | [TC-001759_ATL105_Field_Validation.md](./TC-001759_ATL105_Field_Validation.md) |
| EMV Contactless | TC-001761 | T2 Expired card | [TC-001761_ATL105_Field_Validation.md](./TC-001761_ATL105_Field_Validation.md) |
| EMV Contactless | TC-001762 | T3 Online PIN | [TC-001762_ATL105_Field_Validation.md](./TC-001762_ATL105_Field_Validation.md) |
| EMV Contactless | TC-001763 | T4 Split tender | [TC-001763_ATL105_Field_Validation.md](./TC-001763_ATL105_Field_Validation.md) |
| EMV Contactless | TC-001767 | T5 Purchase → Reversal | [TC-001767_ATL105_Field_Validation.md](./TC-001767_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner' "-Dexec.args=<test_data dir> <test_cases.jsonl> Result_MD_Folder TC-001722 TC-001723 TC-001724 TC-001726 TC-001730 TC-001759 TC-001761 TC-001762 TC-001763 TC-001767"
```

---

## 9. Full run – all EMV Contact and Contactless test cases (2026-11-02)

After the 10-case sample, the same field-level validation was run on **every** EMV Contact and EMV Contactless test case in the MasterCard Credit catalog. The detailed per-test-case reports and index are in [EMV_Full_Validation/README.md](./EMV_Full_Validation/README.md).

### 9.1 Coverage

| Measure | Value |
|---|---|
| EMV Contact + Contactless test cases in the catalog | **112** (56 contact, 56 contactless) |
| Test cases with test data, validated field by field | **104 (92.9%)** |
| Test cases without test data | 8. All are device-configuration scenarios (T11 SAF blocked / disabled void / disabled preauth / disabled return) with no host message, so there is nothing to validate. |
| Test data files validated | **210** (126 EMV requests, 66 follow-on Financial Transaction Requests, 18 Totals Requests) |
| Message types covered | EMV Financial Transaction Request, Financial Transaction Request (reversal, time-out reversal, cancellation, void, completion), Totals Request |
| Transaction types covered | Purchase (0), Auth Only (3), Return (7), Balance Inquiry (E), Purchase Reversal (8), Time-out Reversal (Z), Cancellation (S), Void of Return (U), Completion (0 + Approval Number), Totals (990) |
| Result | **104 of 104 test cases FAIL**, because D-01 and D-02 occur in every EMV request |

| Entry mode | Purchase | Auth Only | Return | Balance Inquiry | Total with TD |
|---|---|---|---|---|---|
| EMV Contact | 35 | 14 | 2 | 1 | 52 |
| EMV Contactless | 35 | 14 | 2 | 1 | 52 |

### 9.2 Defect frequency across all 104 test cases

| Defect | Description | TCs affected | % of TCs |
|---|---|---|---|
| D-01 | EMV Card Sequence Number `01` instead of `001` (E188) | 104 | **100%** |
| D-02 | EMV Additional Information not `EMVYES`/`EMVNOT` (E118, App. T) | 104 | **100%** |
| D-06 | Totals Card Label `CC` instead of 4-byte `CC  ` (E13) | 18 | 17.3% |
| D-07 | Segment 100 length built from an out-of-range runtime amount (8 digits) | 10 | 9.6% |
| D-04 | Online-PIN test without PIN block / with signature CVM | 4 | 3.8% |
| D-05 | Split-tender card B chip amount = full original amount | 4 | 3.8% |
| D-08 | Product Code segment (FSA) length `033`, spec arithmetic gives `029` | 4 | 3.8% |
| D-03 | Expired-card test with a non-expired card | 2 | 1.9% |
| D-04b | Offline-PIN test with a signature CVM (9F34 `5E0302`) | 2 | 1.9% |
| D-09 | Cashback test: chip data ignores the cash amount | 2 | 1.9% |
| D-10 | Totals category with count `00000` | 2 | 1.9% |

### 9.3 New defects found in the full run

#### D-06 – Totals Card Label is 2 bytes instead of 4 (Element 13)

| | |
|---|---|
| Where | All 18 Totals Request legs, `"Totals Data Segment" → "CardLabel"` (TC-001736–1741, 1754, 1755, 1773–1778, 1791, 1792, 2030, 2045) |
| Generated | `CC` |
| Expected | `CC  ` (CC + 2 spaces) |
| Spec | Ch. 13 Element 13 (p.365): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*. 12.6 Field 15: Card Label, Len. 4, R. |
| Note | The declared Segment Length `108` is only reproducible with a 4-byte Card Label plus a trailing Field Separator. So the generator counted the padding but did not emit it. Same pattern as D-01. |

#### D-07 – Runtime amounts are 8-digit random values, and segment lengths were built from them

| | |
|---|---|
| Where | 10 test cases: split tender (TC-001726, 1763), FSA split (TC-001743, 1780), partial-approval reversal (TC-001727, 1764), auth completion / cancellation (TC-002023, 2024, 2038, 2039) |
| What | Follow-on legs correctly use a runtime placeholder for `NonfuelAmount` (e.g. `<NonfuelAmount from step 1 response>`). But the AI's own test case stores an 8-digit provisional value (e.g. `24677595`, `83968206`), and the declared Segment 100 length was computed from it. |
| Example | TC-002023 step 2: declared length `088` leaves 14 characters for Approval Number (6) + amount (**8**). |
| Spec | Element 58 (p.384): *"A maximum length of 7 bytes is allowed for all other card types"* (8 only for American Express). No valid MasterCard amount can produce these lengths. |
| Fix | Provisional amounts must respect Element 58 (≤ 7 digits) and the business relation (remainder < original; completion/reversal ≤ authorized). Compute the segment length after substitution. |

#### D-08 – FSA Product Code segment length is wrong (Segment 102)

| | |
|---|---|
| Where | TC-001742, 1743, 1779, 1780 (FSA/HRA), `"Product Code Data Segment" → "SegmentLength"` |
| Generated | `033` |
| Expected | `029` = `102`▲`029`▲ + `F` + `01` + `894` `U` `01` `\` `21500` `\` `1500` ▲ |
| Spec | 12.3: FS after Segment Type and Segment Length only; Product Data Field Delimiter `\` after Quantity and Unit Price; FS after the last Product Amount. The same formula reproduces the Appendix B example length `049` exactly. |
| Passes | Product code 894 (QHP), Table 012 = `M` (healthcare), product total = Segment 100 amount. |

#### D-09 – Cashback test: chip data ignores the cash amount (T10-CASHBACK)

| | |
|---|---|
| Where | TC-001747 (contact), TC-001784 (contactless) |
| Generated | Segment 100 `NonfuelAmount` 1500 + `CashAmount` 500, but chip 9C = `00`, 9F02 = `000000001500`, 9F03 = `000000000000` |
| Expected | 9C = `09` (purchase with cashback), 9F02 = `000000002000` (total incl. cash), 9F03 = `000000000500` (cash) |
| Basis | SEG130-R-009 (chip values must agree with Segment 100) and EMV Book 3 tag definitions. This is an EMV consistency check, not an ATL105 field-format rule. |

#### D-10 – Totals category sent with a zero count

| | |
|---|---|
| Where | TC-001737, TC-001774 (Purchase → Reversal → Settlement), Totals leg |
| Generated | `CardLabel` CC, `CardTypeTotalCount` `00000`, `CardTypeTotalAmount` `00000000` |
| Spec | Element 16 valid values **00001–99999**. 12.6: Fields 15–17 *"are sent … only when data occurs for a card type"*. If the purchase was reversed, the CC category should be omitted (and Number of Segments / length recomputed). |

#### D-04b – Offline-PIN test carries a signature CVM

TC-001725 (contact purchase) and TC-002022 (contact auth only): 9F34 = `5E0302` (CVM code 1E = signature). Offline PIN should be CVM code 01/04 (optionally +signature 03/05). Element 33 is correctly empty for offline PIN. (The catalog has no contactless offline-PIN test case.)

### 9.4 Additional review items from the full run (not defects)

| Item | Count | Why it is REVIEW |
|---|---|---|
| Completion legs sent without Segment 130 | 14 legs | ATL105 exempts only reversals from EMV data (10.14.2.4). Completions carry the original Approval Number (preauthorized), so EMV data is probably not needed, but this needs SME confirmation. |
| Totals Password (E65) empty | 18 legs | Layout marks it C, but Element 65 says *"Password required for totals request"*. Depends on the merchant profile. |
| Totals Hardware/Software/Firmware versions | 18 legs | Format is valid; the values are synthesized and must be the certified device's real versions. |
| Totals Segment Length `108` | 18 legs | Matches only with a 4-byte Card Label and a trailing Field Separator; 12.6 does not state a trailing FS. |
| Contactless chip profile identical to contact | 61 legs | See Q-03. |

### 9.5 Completion status for the tracker

| Scope | Status |
|---|---|
| Field-level validation of all EMV Contact + Contactless test data | **100% of test cases with test data (104/104); 92.9% of all EMV test cases (104/112), the remaining 8 are device-only** |
| Defects documented and shared | 11 defect types (D-01 to D-10, D-04b) |
| Pending | AI team fixes → re-validation; SME answers on Q-01 to Q-08 and the review items above |

Re-run the full scope (from `regression-artifact-validator`; the ID file lists the 112 EMV test case IDs):

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner' "-Dexec.args=<test_data dir> <test_cases.jsonl> Result_MD_Folder\EMV_Full_Validation @<file with TC IDs>"
```
