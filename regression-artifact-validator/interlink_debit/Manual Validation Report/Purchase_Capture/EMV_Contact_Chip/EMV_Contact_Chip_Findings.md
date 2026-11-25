# ATL105 Test Data Validation – Findings Summary – Purchase/Capture – EMV Contact Chip

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – Interlink Debit – Purchase/Capture |
| Scenario | BR1_TS1 Purchase/Capture – EMV Contact Chip (scenario status: valid) |
| Entry mode | EMV Contact Chip (Table 005 PAN entry mode `05` = chip read) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `interlink_debit/test_data` |
| Test case catalog | `interlink_debit/test_cases/test_cases.jsonl` (lines 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | All 29 test cases of this scenario (T1 ×2, T2, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2), 60 test data files (21 multi-step flows); 2 device-only test cases without a host message (ATL105 citations checked only) |
| Date | 2026-11-04 |
| Overall result | **27 / 29 test cases FAIL** (2 REVIEW) – 4 defects common to every test case (D-01, D-02, D-12, D-15), plus 7 scenario-specific defects (D-14, D-16, D-18, D-19, D-20, D-21, D-24) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step (EMV Financial Transaction Request when Segment 130 is sent; Financial Transaction Request on follow-on steps), Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167, Section 11.8.1 p.205, Section 11.8.1 p.206 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 7, 8, Z for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number + '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76); follow-on steps send the truncated expiry YYMM only; card-number check digit | Element 12 p.371, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 030 (POS Condition Code) for EMV and Credential on File transactions; Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Appendix I p.568 |
| Debit rules (Section 10.2) | Card type 011 in the Prompt Code; PIN block (Element 33, DUKPT) on every request except the Purchase Reversal and follow-on messages; no PIN block on follow-on messages; no manual (keyed) entry; no Time-out Reversal of a Completion | Section 10.2.1.3 p.96, Section 10.2.1.4 p.96, Section 10.2.5.1 p.105, Section 10.2.2 p.97, Appendix E p.521 |
| Totals Request (Segment 105, 17 fields) | Data Section 1 + Segment 105 only; presence, type, length and value of every field (Prompt Code 990, Totals Date, versions, Sequence Number, Card Label 4 characters); Segment Length (14 Field Separators, none in the Category Total Data Section); count, amount and grand total against the approved steps of the flow (Section 9.2) | Section 11.4.1.1 p.178, Section 12.6 p.235, Section 12.6 p.237, Element 13 p.372, Element 16 p.375, Element 15 p.374, Element 42 p.386, Element 105 p.419, Section 9.2 p.72 |
| Segment 130 (9 fields) | Elements 187-192 and 118; EMV Chip Data TLV decode; EMV Additional Information (Appendix T); Segment Length recomputed (chip data counted in binary bytes) | Section 12.20 p.270, Element 187 p.476, Element 188 p.477, Element 189 p.477, Element 190 p.478, Element 191 p.479, Element 192 p.480, Element 118 p.439, Appendix R p.697, Appendix T p.701 |
| EMV cross-field | 9F02 vs amount (with the cash back when sent), 9F03 vs Cash Amount, 9C vs transaction type, AID (9F06/84) present, 5F2A/9F1A, 5F34 vs Element 188, 9F27, 9F34, Tags 5A/57 absent | Element 190 p.478, Appendix R p.697 |
| Test objective | The data can prove what the scenario claims: expired card, time-out reversal, reversal, partial approval | Section 10.14.2.1 p.160, Element 12 p.372, Section 2.3 p.39, Section 2.3.1 p.40, Section 10.1.2.1 p.84, Element 5 p.368 |
| Step-to-step links | Sequence Number, Account Number, terminal and entry mode against the original step | Section 2.3.1 p.40, Appendix J p.638, Element 86 p.409 |
| Expected response codes | Expected Response Code of each step fits the step; open ('?') codes are listed | Element 83 p.404 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the test case executed against the test data | TC catalog |
| Generation fidelity | Every test data value compared with the test case request value | TC catalog |
| Spec citations | Every `spec_basis` quote and cited page of the test case checked against the 2026-4 text | TC catalog |
| Device-only test cases | TC-002909 and TC-002910: no host message, so no test data; the ATL105 text each quotes is checked on its page and its device procedure is listed in its report | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note for the message builder.

---

## 2. Results by test case

| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |
|---|---|---|---|---|---|---|---|
| T1 – Purchase approved | TC-002887 (BR1_TS1_TC1) | 1 | **FAIL** | 5 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Purchase, no response → Time-out Reversal | TC-002888 (BR1_TS1_TC9) | 2 | **FAIL** | 6 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Purchase, late approval → Time-out Reversal | TC-002889 (BR1_TS1_TC10) | 2 | **FAIL** | 6 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Purchase, unreadable response → Time-out Reversal | TC-002890 (BR1_TS1_TC11) | 2 | **FAIL** | 6 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Purchase timed out → TOR sent ahead of next Purchase | TC-002891 (BR1_TS1_TC12) | 3 | **FAIL** | 11 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Time-out Reversal unanswered → retried (max 3) | TC-002892 (BR1_TS1_TC13) | 4 | **FAIL** | 8 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T8 – Purchase → Settlement (read and cut 222222) | TC-002893 (BR1_TS1_TC17) | 2 | **FAIL** | 7 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase → Purchase Reversal → Settlement | TC-002894 (BR1_TS1_TC18) | 3 | **FAIL** | 8 | 1 | 9 | D-01, D-02, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase + Merchandise Return → Settlement | TC-002895 (BR1_TS1_TC19) | 3 | **FAIL** | 12 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase timed out → TOR → Settlement | TC-002896 (BR1_TS1_TC20) | 3 | **FAIL** | 9 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20, D-21 |
| T8 – Purchase → Read-only totals (333333) | TC-002897 (BR1_TS1_TC21) | 2 | **FAIL** | 7 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase → Auto-close at cut time | TC-002898 (BR1_TS1_TC22) | 2 | **FAIL** | 7 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20 |
| T10 – Time-out Reversal with no original → declined (unmatched) | TC-002899 (BR1_TS1_TC25) | 1 | **FAIL** | 4 | 0 | 5 | D-01, D-02, D-12, D-15 |
| T10 – Purchase → Reversal → Time-out Reversal → declined | TC-002900 (BR1_TS1_TC26) | 3 | **FAIL** | 7 | 1 | 6 | D-01, D-02, D-12, D-15, D-16 |
| T5 – Purchase → next Purchase on the same device (new Sequence Number) | TC-002901 (BR1_TS1_TC6) | 2 | **FAIL** | 10 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T5 – Purchase → Void declined → Merchandise Return | TC-002902 (BR1_TS1_TC7) | 3 | **FAIL** | 11 | 1 | 7 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | TC-002903 (BR1_TS1_TC14) | 3 | **FAIL** | 11 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | TC-002904 (BR1_TS1_TC15) | 2 | **FAIL** | 10 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T6 – Purchase late approval → Time-out Reversal → Purchase resubmitted | TC-002905 (BR1_TS1_TC16) | 3 | **FAIL** | 11 | 0 | 5 | D-01, D-02, D-12, D-15, D-16 |
| T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | TC-002906 (BR1_TS1_TC23) | 3 | **FAIL** | 13 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20, D-24 |
| T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | TC-002907 (BR1_TS1_TC24) | 3 | **FAIL** | 13 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-19, D-20, D-24 |
| T10 – Purchase declined by the issuer | TC-002908 (BR1_TS1_TC27) | 1 | **FAIL** | 5 | 0 | 6 | D-01, D-02, D-12, D-15, D-16 |
| T11 – Store and Forward blocking window (card type 173) → no off-line approval inside the window | TC-002909 (BR1_TS1_TC28) | 0 | **REVIEW** | 0 | 0 | 1 | – |
| T11 – Void/Reversal disabled (card type 167) → not offered at the device | TC-002910 (BR1_TS1_TC29) | 0 | **REVIEW** | 0 | 0 | 1 | – |
| T5 – Debit Purchase → Purchase Reversal (no PIN) | TC-002911 (BR1_TS1_TC8) | 2 | **FAIL** | 6 | 1 | 6 | D-01, D-02, D-12, D-15, D-16 |
| T2 – Expired debit card → sent online (no device expiry check) → issuer decides | TC-002912 (BR1_TS1_TC3) | 1 | **FAIL** | 5 | 0 | 6 | D-01, D-02, D-12, D-15, D-16 |
| T1 – Debit Purchase with Cashback → approved | TC-002913 (BR1_TS1_TC2) | 1 | **FAIL** | 6 | 0 | 5 | D-01, D-02, D-12, D-15, D-16, D-18 |
| T4 – Debit Purchase with Cashback partially approved → approved amount covers the purchase only | TC-002914 (BR1_TS1_TC4) | 1 | **FAIL** | 6 | 0 | 7 | D-01, D-02, D-12, D-15, D-16, D-18 |
| T4 – Debit partial approval → Purchase Reversal of the approved amount | TC-002915 (BR1_TS1_TC5) | 2 | **FAIL** | 9 | 2 | 7 | D-01, D-02, D-12, D-14, D-15, D-16 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case (36 EMV test data files)

Follow-on reversal files without Segment 130 (`TD-TC-002888.step2.timeout-reversal.json`, `TD-TC-002889.step2.timeout-reversal.json`, `TD-TC-002890.step2.timeout-reversal.json`, `TD-TC-002891.step2.timeout-reversal.json`, `TD-TC-002892.step2.timeout-reversal.json`, `TD-TC-002892.step3.tor-retry.json`, `TD-TC-002892.step4.tor-retry.json`, `TD-TC-002894.step2.reversal.json`, `TD-TC-002896.step2.timeout-reversal.json`, `TD-TC-002900.step2.reversal.json`, `TD-TC-002900.step3.timeout-reversal.json`, `TD-TC-002902.step2.reversal.json`, `TD-TC-002903.step2.timeout-reversal.json`, `TD-TC-002905.step2.timeout-reversal.json`, `TD-TC-002911.step2.reversal.json`, `TD-TC-002915.step2.reversal.json`) are allowed: Section 10.14.2.4 Supported Transaction Types (EMV), p.161 (printed 10-82): *"EMV data is not required on Reversal transactions."*

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
| TC-002887 | TD-TC-002887.json | 47 | `01` | `01` | `001` |
| TC-002888 | TD-TC-002888.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002889 | TD-TC-002889.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002890 | TD-TC-002890.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002891 | TD-TC-002891.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002891 | TD-TC-002891.step3.next-transaction.json | 47 | `01` | `01` | `001` |
| TC-002892 | TD-TC-002892.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002893 | TD-TC-002893.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002894 | TD-TC-002894.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002895 | TD-TC-002895.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002895 | TD-TC-002895.step2.return.json | 47 | `01` | `01` | `001` |
| TC-002896 | TD-TC-002896.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002897 | TD-TC-002897.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002898 | TD-TC-002898.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002899 | TD-TC-002899.json | 47 | `01` | `01` | `001` |
| TC-002900 | TD-TC-002900.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002901 | TD-TC-002901.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002901 | TD-TC-002901.step2.next-transaction.json | 47 | `01` | `01` | `001` |
| TC-002902 | TD-TC-002902.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002902 | TD-TC-002902.step3.return.json | 47 | `01` | `01` | `001` |
| TC-002903 | TD-TC-002903.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002903 | TD-TC-002903.step3.next-transaction.json | 47 | `01` | `01` | `001` |
| TC-002904 | TD-TC-002904.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002904 | TD-TC-002904.step2.resubmitted.json | 47 | `01` | `01` | `001` |
| TC-002905 | TD-TC-002905.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002905 | TD-TC-002905.step3.resubmitted.json | 47 | `01` | `01` | `001` |
| TC-002906 | TD-TC-002906.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002906 | TD-TC-002906.step3.return.json | 47 | `01` | `01` | `001` |
| TC-002907 | TD-TC-002907.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002907 | TD-TC-002907.step3.return.json | 47 | `01` | `01` | `001` |
| TC-002908 | TD-TC-002908.json | 47 | `01` | `01` | `001` |
| TC-002911 | TD-TC-002911.step1.purchase.json | 47 | `01` | `01` | `001` |
| TC-002912 | TD-TC-002912.json | 47 | `01` | `01` | `001` |
| TC-002913 | TD-TC-002913.json | 47 | `01` | `01` | `001` |
| TC-002914 | TD-TC-002914.json | 47 | `01` | `01` | `001` |
| TC-002915 | TD-TC-002915.step1.purchase.json | 47 | `01` | `01` | `001` |

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data

| | |
|---|---|
| Location | `"EMV Request Data Segment"`, **lines 50–52** of each EMV test data file |
| Generated value | Indicator `001`, Length `984`, Information = 984 generated A–Z/0–9 characters (e.g. `XTV641IPVR6UAAXEE82D…AYJLZN1D`) |
| Expected | Indicator `001` with EMV Table Data value **`EMVYES`** or **`EMVNOT`** |
| Spec chain | 1. **E191** (p.479): *"001 EMV table data"* → `001` PASS<br>2. **E192** (p.480): *"Valid Codes/Values: 001–985"* → `984` **PASS on its own**<br>3. **E118 EMV Additional Information** (p.439): *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here."*<br>4. **Appendix T-1** (p.701): EMV Table Data – *"Maximum Length n6 Len.: 6"*; request values *"EMVYES This code indicates that the device is able to receive EMV Table Data."* / *"EMVNOT This code indicates that the device is unable to receive EMV Table Data."* → **FAIL** |
| Root cause | The test case marks this value `method: format_synthesis` with flag `UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)`. The generator filled the generic 984-character maximum instead of applying Appendix T. The same generated string is reused across test cases (TC-002887/TC-002899/TC-002908/TC-002912/TC-002913/TC-002914 → `XTV6…`). |
| Certainty | **Certain** that the content is invalid. **Not certain** what the exact corrected Element 192 value is (see §6, Q-01). |

**Clarification on Element 192:** Element 192 = `984` is a valid value in range and equals the actual content length, so the report marks it **PASS**. The defect is the **content** of Element 118. Element 192 is consistent with that content, but the content itself is not allowed for indicator `001`.

#### D-02 evidence by file

| Test case | Test data file | Lines | Indicator | Length | Value (start…end) |
|---|---|---|---|---|---|
| TC-002887 | TD-TC-002887.json | 50–52 | 001 | 984 | `XTV641IPVR6UAAXEE82D…AYJLZN1D` |
| TC-002888 | TD-TC-002888.step1.purchase.json | 50–52 | 001 | 984 | `YCQKPKFLAELHK5V71RAX…P3ZOU3YW` |
| TC-002889 | TD-TC-002889.step1.purchase.json | 50–52 | 001 | 984 | `HFE82CF0O3PVA5T157HH…W4HAILRV` |
| TC-002890 | TD-TC-002890.step1.purchase.json | 50–52 | 001 | 984 | `U59L9UERIBA2P97DT2OC…Y68BKIYT` |
| TC-002891 | TD-TC-002891.step1.purchase.json | 50–52 | 001 | 984 | `VGVD30Z4KEHOY89123V5…WV7KOQ16` |
| TC-002891 | TD-TC-002891.step3.next-transaction.json | 50–52 | 001 | 984 | `WPPSTSOX2VF4FEBPE2O8…9868APHM` |
| TC-002892 | TD-TC-002892.step1.purchase.json | 50–52 | 001 | 984 | `LR4EKHYF8UTY0P4K0V50…A3AB1DV6` |
| TC-002893 | TD-TC-002893.step1.purchase.json | 50–52 | 001 | 984 | `IV5VK4M329QX8NURVAKD…09RYPLUR` |
| TC-002894 | TD-TC-002894.step1.purchase.json | 50–52 | 001 | 984 | `D8GS1KG6BWH2MRWYR0C0…1D41MI55` |
| TC-002895 | TD-TC-002895.step1.purchase.json | 50–52 | 001 | 984 | `6ZA2Y72ZQFSQ1D15NMHA…Q14SPVYZ` |
| TC-002895 | TD-TC-002895.step2.return.json | 50–52 | 001 | 984 | `KG1HCLEWFEH7S9V9WFGV…ZJC5AY5U` |
| TC-002896 | TD-TC-002896.step1.purchase.json | 50–52 | 001 | 984 | `LI73GZ138K4B1UG7SFOP…VRVCMIWA` |
| TC-002897 | TD-TC-002897.step1.purchase.json | 50–52 | 001 | 984 | `V0511MQCQP99E7ESPRXZ…SNQHMFYW` |
| TC-002898 | TD-TC-002898.step1.purchase.json | 50–52 | 001 | 984 | `UKTCBI09U61Q4ANGDPPL…EBKW2RAM` |
| TC-002899 | TD-TC-002899.json | 50–52 | 001 | 984 | `XTV641IPVR6UAAXEE82D…AYJLZN1D` |
| TC-002900 | TD-TC-002900.step1.purchase.json | 50–52 | 001 | 984 | `DBS0ZENP9X9VONJESZ9D…M5GQVIYA` |
| TC-002901 | TD-TC-002901.step1.purchase.json | 50–52 | 001 | 984 | `F0GZ8F3A5GJRGDS5C09O…BSVAFDFN` |
| TC-002901 | TD-TC-002901.step2.next-transaction.json | 50–52 | 001 | 984 | `498C20ITHBDU6KVRAL1F…A6V6LXXG` |
| TC-002902 | TD-TC-002902.step1.purchase.json | 50–52 | 001 | 984 | `AALKJRS7GTIN4A1DVE5K…VLCLDPAL` |
| TC-002902 | TD-TC-002902.step3.return.json | 50–52 | 001 | 984 | `CX4QUJFBMMQF8ZPDGHAM…NCRNPYRY` |
| TC-002903 | TD-TC-002903.step1.purchase.json | 50–52 | 001 | 984 | `GC6LR17UF7OONM822C67…MBBLWRT1` |
| TC-002903 | TD-TC-002903.step3.next-transaction.json | 50–52 | 001 | 984 | `5NPGDYKNIU7NZB9MB9BU…CEOOZ8ZU` |
| TC-002904 | TD-TC-002904.step1.purchase.json | 50–52 | 001 | 984 | `XJLXE2NF43AHV3IMDUV9…YJUCXJSJ` |
| TC-002904 | TD-TC-002904.step2.resubmitted.json | 50–52 | 001 | 984 | `RH3SHLJT3L1TTMQK3FNI…4C0QN084` |
| TC-002905 | TD-TC-002905.step1.purchase.json | 50–52 | 001 | 984 | `B3206UWBR132Z0ZU62NN…N6GQIKUV` |
| TC-002905 | TD-TC-002905.step3.resubmitted.json | 50–52 | 001 | 984 | `GBUD1NJN22OZ6VCIG8KF…V21M2DGE` |
| TC-002906 | TD-TC-002906.step1.purchase.json | 50–52 | 001 | 984 | `W3CSK1HI2Y3JZSME2ZDW…E16KUX0S` |
| TC-002906 | TD-TC-002906.step3.return.json | 50–52 | 001 | 984 | `OU3D4AYK6U9N0MP71D0S…1S8AOSL1` |
| TC-002907 | TD-TC-002907.step1.purchase.json | 50–52 | 001 | 984 | `90PHG7VHBFXT97XFUMC7…I4L06N2N` |
| TC-002907 | TD-TC-002907.step3.return.json | 50–52 | 001 | 984 | `HCAI02WMPOI21TXCMKOU…ZCBCQW2E` |
| TC-002908 | TD-TC-002908.json | 50–52 | 001 | 984 | `XTV641IPVR6UAAXEE82D…AYJLZN1D` |
| TC-002911 | TD-TC-002911.step1.purchase.json | 50–52 | 001 | 984 | `DZGLTDOE4K0F1J6YLSIH…IK3R71Q8` |
| TC-002912 | TD-TC-002912.json | 50–52 | 001 | 984 | `XTV641IPVR6UAAXEE82D…AYJLZN1D` |
| TC-002913 | TD-TC-002913.json | 50–52 | 001 | 984 | `XTV641IPVR6UAAXEE82D…AYJLZN1D` |
| TC-002914 | TD-TC-002914.json | 50–52 | 001 | 984 | `XTV641IPVR6UAAXEE82D…AYJLZN1D` |
| TC-002915 | TD-TC-002915.step1.purchase.json | 50–52 | 001 | 984 | `S9HKV5XSMDSCR3EHEP5W…SPH5QJPD` |

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002887 | 1 (purchase) | TD-TC-002887.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002888 | 1 (purchase) | TD-TC-002888.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002889 | 1 (purchase) | TD-TC-002889.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002890 | 1 (purchase) | TD-TC-002890.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002891 | 1 (purchase) | TD-TC-002891.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002891 | 3 (next transaction) | TD-TC-002891.step3.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002892 | 1 (purchase) | TD-TC-002892.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002893 | 1 (purchase) | TD-TC-002893.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002894 | 1 (purchase) | TD-TC-002894.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002895 | 1 (purchase) | TD-TC-002895.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002895 | 2 (return) | TD-TC-002895.step2.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002896 | 1 (purchase) | TD-TC-002896.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002897 | 1 (purchase) | TD-TC-002897.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002898 | 1 (purchase) | TD-TC-002898.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002899 | 1 (timeout reversal) | TD-TC-002899.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002900 | 1 (purchase) | TD-TC-002900.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002901 | 1 (purchase) | TD-TC-002901.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002901 | 2 (next transaction) | TD-TC-002901.step2.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002902 | 1 (purchase) | TD-TC-002902.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002902 | 3 (return) | TD-TC-002902.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002903 | 1 (purchase) | TD-TC-002903.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002903 | 3 (next transaction) | TD-TC-002903.step3.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002904 | 1 (purchase) | TD-TC-002904.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002904 | 2 (resubmitted) | TD-TC-002904.step2.resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002905 | 1 (purchase) | TD-TC-002905.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002905 | 3 (resubmitted) | TD-TC-002905.step3.resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002906 | 1 (purchase) | TD-TC-002906.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002906 | 3 (return) | TD-TC-002906.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002907 | 1 (purchase) | TD-TC-002907.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002907 | 3 (return) | TD-TC-002907.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002908 | 1 (purchase) | TD-TC-002908.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002911 | 1 (purchase) | TD-TC-002911.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002912 | 1 (purchase) | TD-TC-002912.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002913 | 1 (purchase) | TD-TC-002913.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002914 | 1 (purchase) | TD-TC-002914.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-002915 | 1 (purchase) | TD-TC-002915.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |

**Why (TC-002887):** This step is an EMV transaction (Segment 130 is sent). Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.

Spec:
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

### D-15 – The card data (Account Number, Card Discretionary Block Data) is empty

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002887 | 1 (purchase) | TD-TC-002887.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002888 | 1 (purchase) | TD-TC-002888.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002888 | 2 (timeout reversal) | TD-TC-002888.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002889 | 1 (purchase) | TD-TC-002889.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002889 | 2 (timeout reversal) | TD-TC-002889.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002890 | 1 (purchase) | TD-TC-002890.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002890 | 2 (timeout reversal) | TD-TC-002890.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002891 | 1 (purchase) | TD-TC-002891.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002891 | 2 (timeout reversal) | TD-TC-002891.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002891 | 3 (next transaction) | TD-TC-002891.step3.next-transaction.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002892 | 1 (purchase) | TD-TC-002892.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002892 | 2 (timeout reversal) | TD-TC-002892.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002892 | 3 (tor retry) | TD-TC-002892.step3.tor-retry.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002892 | 4 (tor retry) | TD-TC-002892.step4.tor-retry.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002893 | 1 (purchase) | TD-TC-002893.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002894 | 1 (purchase) | TD-TC-002894.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002894 | 2 (reversal) | TD-TC-002894.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002895 | 1 (purchase) | TD-TC-002895.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002895 | 2 (return) | TD-TC-002895.step2.return.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002896 | 1 (purchase) | TD-TC-002896.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002896 | 2 (timeout reversal) | TD-TC-002896.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002897 | 1 (purchase) | TD-TC-002897.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002898 | 1 (purchase) | TD-TC-002898.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002899 | 1 (timeout reversal) | TD-TC-002899.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002900 | 1 (purchase) | TD-TC-002900.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002900 | 2 (reversal) | TD-TC-002900.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002900 | 3 (timeout reversal) | TD-TC-002900.step3.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002901 | 1 (purchase) | TD-TC-002901.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002901 | 2 (next transaction) | TD-TC-002901.step2.next-transaction.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002902 | 1 (purchase) | TD-TC-002902.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002902 | 2 (reversal) | TD-TC-002902.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002902 | 3 (return) | TD-TC-002902.step3.return.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002903 | 1 (purchase) | TD-TC-002903.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002903 | 2 (timeout reversal) | TD-TC-002903.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002903 | 3 (next transaction) | TD-TC-002903.step3.next-transaction.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002904 | 1 (purchase) | TD-TC-002904.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002904 | 2 (resubmitted) | TD-TC-002904.step2.resubmitted.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002905 | 1 (purchase) | TD-TC-002905.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002905 | 2 (timeout reversal) | TD-TC-002905.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002905 | 3 (resubmitted) | TD-TC-002905.step3.resubmitted.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002906 | 1 (purchase) | TD-TC-002906.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002906 | 3 (return) | TD-TC-002906.step3.return.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002907 | 1 (purchase) | TD-TC-002907.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002907 | 3 (return) | TD-TC-002907.step3.return.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002908 | 1 (purchase) | TD-TC-002908.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002911 | 1 (purchase) | TD-TC-002911.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002911 | 2 (reversal) | TD-TC-002911.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002912 | 1 (purchase) | TD-TC-002912.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002913 | 1 (purchase) | TD-TC-002913.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002914 | 1 (purchase) | TD-TC-002914.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002915 | 1 (purchase) | TD-TC-002915.step1.purchase.json : 14 | `(empty)` | Fill in the data read from the card (entry mode 05 = chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-002915 | 2 (reversal) | TD-TC-002915.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |

**Why (TC-002887):** This purchase must carry the data read from the card (entry mode 05 = chip read), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.

Spec:
- Element 2 Account Number (Chapter 13), p.364 (printed 13-24): *"a variable length (up to 24 alphanumeric bytes) Account Number"*
- Element 12 Card Discretionary Block Data (Chapter 13), p.371 (printed 13-31): *"If the Account Number is obtained from a Track 2 card reader, instead of being manually keyed, the Account Number and Card Discretionary Block Data should consist of all data read from the card between, but not including, the Start Sentinel and End Sentinel."*
- Element 12 Card Discretionary Block Data (Chapter 13), p.371 (printed 13-31): *"Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."*
- Section 10.2.5.2 Track Support Requirements, p.105 (printed 10-26): *"The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."*

---

## 4. Scenario-specific defects

### D-14 – Follow-on step (completion, cancellation, reversal): amount data built from an impossible value

| TC | Step | Finding | Location | Value | Expected |
|---|---|---|---|---|---|
| TC-002915 | 2 (reversal) | Segment Length was calculated from the invalid test case value | TD-TC-002915.step2.reversal.json -> Standard Message Data Segment -> SegmentLength | `068` | Recalculate the Segment Length after filling in the real values (for example Approved Amount 000001000 -> amount 1000 plus a 6-character Approval Number -> Segment Length 064). |
| TC-002915 | 2 (reversal) | The test case value for Nonfuel Amount is too long | test case step 2 request -> Nonfuel Amount (used to build TD-TC-002915.step2.reversal.json) | `37526954` | Reversal amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |
| TC-002915 | 2 (reversal) | The reversal amount cannot be larger than the original sale | test case step 2 request -> Nonfuel Amount | `37526954` | Reversal amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |

**TC-002915 in plain words:** step 1 asks for $15.00 and expects Response Code `F` (partial approval). The reversal in step 2 must carry the amount from the step 1 response, so the test data correctly uses a run-time expression (`<NonfuelAmount from step 1 response>`). But the test case also holds a provisional value `37526954` = $375,269.54 (8 digits; Element 58 allows at most 7 for MasterCard), larger than the $15.00 that was asked for, and the generator computed the declared Segment Length from it.

Spec:
- Section 12.1 Standard Message Data Segment, p.223 (printed 12-6): *"Identifies the data segment's length, including Segment Type's length and Field Separators."*
- Element 84 Segment Length (Chapter 13), p.406 (printed 13-66): *"This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."*
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"A maximum length of 7 bytes is allowed for all other card types."*
- Section 10.1.2.1 Partial Approval Transaction, p.84 (printed 10-5): *"The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a transaction has been partially approved."*
- Element 121 Partial Approval Indicator (Chapter 13), p.440 (printed 13-100): *"the network can approve an amount that is less than the amount in the Authorization request"*
- Element 6 Approved Amount (Chapter 13), p.368 (printed 13-28): *"Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"*

**What the generator must do next run:** keep the follow-on amount a run-time value taken from the original step's response, make any provisional amount satisfy Element 58 (at most 7 digits, not more than the original amount), and compute the Segment Length after substitution.

### D-16 – The debit PIN block (Element 33) is empty

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002887 | 1 (purchase) | TD-TC-002887.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002888 | 1 (purchase) | TD-TC-002888.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002889 | 1 (purchase) | TD-TC-002889.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002890 | 1 (purchase) | TD-TC-002890.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002891 | 1 (purchase) | TD-TC-002891.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002891 | 3 (next transaction) | TD-TC-002891.step3.next-transaction.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002892 | 1 (purchase) | TD-TC-002892.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002893 | 1 (purchase) | TD-TC-002893.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002894 | 1 (purchase) | TD-TC-002894.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002895 | 1 (purchase) | TD-TC-002895.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002895 | 2 (return) | TD-TC-002895.step2.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002896 | 1 (purchase) | TD-TC-002896.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002897 | 1 (purchase) | TD-TC-002897.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002898 | 1 (purchase) | TD-TC-002898.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002900 | 1 (purchase) | TD-TC-002900.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002901 | 1 (purchase) | TD-TC-002901.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002901 | 2 (next transaction) | TD-TC-002901.step2.next-transaction.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002902 | 1 (purchase) | TD-TC-002902.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002902 | 3 (return) | TD-TC-002902.step3.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002903 | 1 (purchase) | TD-TC-002903.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002903 | 3 (next transaction) | TD-TC-002903.step3.next-transaction.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002904 | 1 (purchase) | TD-TC-002904.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002904 | 2 (resubmitted) | TD-TC-002904.step2.resubmitted.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002905 | 1 (purchase) | TD-TC-002905.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002905 | 3 (resubmitted) | TD-TC-002905.step3.resubmitted.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002906 | 1 (purchase) | TD-TC-002906.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002906 | 3 (return) | TD-TC-002906.step3.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002907 | 1 (purchase) | TD-TC-002907.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002907 | 3 (return) | TD-TC-002907.step3.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002908 | 1 (purchase) | TD-TC-002908.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002911 | 1 (purchase) | TD-TC-002911.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002912 | 1 (purchase) | TD-TC-002912.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002913 | 1 (purchase) | TD-TC-002913.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002914 | 1 (purchase) | TD-TC-002914.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-002915 | 1 (purchase) | TD-TC-002915.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |

**Why (TC-002887):** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This purchase (Transaction Type 0) sends no PIN block, although its chip data reports an online PIN (Tag 9F34 = 420300).

Spec:
- Section 10.2.1.3 PIN Entry, p.96 (printed 10-17): *"All transactions—except the Purchase Reversal transaction—require PIN entry."*
- Section 10.2.1.3 PIN Entry, p.96 (printed 10-17): *"When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."*
- Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"Required for debit and EBT."*
- Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."*

### D-18 – Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002913 | 1 (purchase) | TD-TC-002913.json : 49 | `9F02=000000001500, 9F03=000000000000, 9C=00` | Generate the chip data from a purchase with cash back: 9F02 = 000000002000, 9F03 = 000000000500, 9C = 09. |
| TC-002914 | 1 (purchase) | TD-TC-002914.json : 49 | `9F02=000000001500, 9F03=000000000000, 9C=00` | Generate the chip data from a purchase with cash back: 9F02 = 000000002000, 9F03 = 000000000500, 9C = 09. |

**Why (TC-002913):** This purchase sends a Cash Amount of 500 ($5.00) next to the Nonfuel Amount, which excludes cash. The chip data must hold the exact card-to-terminal values of a cash-back purchase (EMV Book 3: 9F02 = total including the cash back, 9F03 = the cash back, 9C = 09). Here: Tag 9F02 (Amount, Authorized) = 000000001500 = $15.00, but the transaction total including the cash back is $20.00 (000000002000); Tag 9F03 (Amount, Other – the cash back) = 000000000000, but the Cash Amount is 500 (000000000500); Tag 9C (Transaction Type) = 00 (purchase), but a purchase with cash back is 09.

Spec:
- Element 17 Cash Amount (Chapter 13), p.375 (printed 13-35): *"Identifies the cash back amount of a transaction."*
- Element 58 Nonfuel Amount (Chapter 13), p.393 (printed 13-53): *"Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount."*
- Appendix R. EMV Chip Data Example, p.697 (printed Appendix R-1): *"9F\02\06\00\00\00\00\09\00\ Amount, authorized (numeric)"*
- Element 190 EMV Chip Data (Chapter 13), p.478 (printed 13-138): *"All data elements must contain the exact values that were present on the card to terminal interface."*

### D-19 – Totals Request: Card Label (Element 13) is not 4 characters

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002893 | 2 (totals) | TD-TC-002893.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002894 | 3 (totals) | TD-TC-002894.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002895 | 3 (totals) | TD-TC-002895.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002896 | 3 (totals) | TD-TC-002896.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002897 | 2 (totals) | TD-TC-002897.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002898 | 2 (totals) | TD-TC-002898.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002906 | 2 (totals) | TD-TC-002906.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-002907 | 2 (totals) | TD-TC-002907.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |

**Why (TC-002893):** Element 13 is a fixed field of four characters, left-aligned and space-filled. 'DB' has 2, so the fields after it shift by 2 on the wire.

Spec:
- Element 13 Card Label (Chapter 13), p.372 (printed 13-32): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*
- Element 13 Card Label (Chapter 13), p.373 (printed 13-33): *"Debit DB"*

### D-20 – Totals Request: Segment 105 length is counted with the wrong layout

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002893 | 2 (totals) | TD-TC-002893.step2.totals.json : 7 | `108` | 107 |
| TC-002894 | 3 (totals) | TD-TC-002894.step3.totals.json : 7 | `108` | 107 |
| TC-002895 | 3 (totals) | TD-TC-002895.step3.totals.json : 7 | `108` | 107 |
| TC-002896 | 3 (totals) | TD-TC-002896.step3.totals.json : 7 | `108` | 107 |
| TC-002897 | 2 (totals) | TD-TC-002897.step2.totals.json : 7 | `108` | 107 |
| TC-002898 | 2 (totals) | TD-TC-002898.step2.totals.json : 7 | `108` | 107 |
| TC-002906 | 2 (totals) | TD-TC-002906.step2.totals.json : 7 | `108` | 107 |
| TC-002907 | 2 (totals) | TD-TC-002907.step2.totals.json : 7 | `108` | 107 |

**Why (TC-002893):** Fields 1-14 add up to 76 characters, plus 14 Field Separators = 90. Fields 15-17 (the Category Total Data Section) are not separated: Card Label 4 (fixed width) + Count 5 + Amount 8 = 17. The Segment Length is therefore 107, not '108'. '108' is what you get with a Field Separator after every one of the 17 fields and the 2-character Card Label, as in Segment 100.

Spec:
- Section 12.6 Totals Data Segment, p.235 (printed 12-18): *"Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14."*
- Section 12.6 Totals Data Segment, p.235 (printed 12-18): *"Field Nos. 15–17 are not separated by Field Separators."*
- Element 13 Card Label (Chapter 13), p.372 (printed 13-32): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*
- Element 84 Segment Length (Chapter 13), p.406 (printed 13-66): *"This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."*

### D-21 – Totals Request: the totals do not match the transactions of the flow

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002896 | 3 (totals) | TD-TC-002896.step3.totals.json : 21 | `count 00001, amount 00000000, grand total 00000000` | Leave out the DB bucket and send Grand Total 00000000 |

**Why (TC-002896):** Section 9.2 counts each approved transaction into its card bucket: step 1 purchase $15.00: no response (timed out), so not in the totals; step 2 timeout reversal $15.00: its original was never approved, so nothing to take out. The DB bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.

Spec:
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"*
- Section 12.6 Totals Data Segment, p.237 (printed 12-20): *"Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."*

### D-24 – A test data value differs from the test case it was generated from

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-002906 | 3 (return) | TD-TC-002906.step3.return.json : 22 | `029062` | '029063' (regenerate the file from the test case) |
| TC-002907 | 3 (return) | TD-TC-002907.step3.return.json : 22 | `029072` | '029073' (regenerate the file from the test case) |

**Why (TC-002906):** The test data file has '029062', but the test case request (the source the file is generated from) has '029063'. The two are out of step, so the file does not send what the test case describes. '029062' is already the Sequence Number of step 2 (totals) on the same terminal, so the terminal would send one Sequence Number twice.

Spec:
- Element 86 Sequence Number (Chapter 13), p.409 (printed 13-69): *"Valid Codes/Values: 000001–999999"*
- Element 86 Sequence Number (Chapter 13), p.409 (printed 13-69): *"Each transaction has a unique Sequence Number."*

### Follow-on steps checked against their original step

#### T6 – Purchase, no response → Time-out Reversal (TC-002888)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028881` (original `028881`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase, late approval → Time-out Reversal (TC-002889)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028891` (original `028891`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase, unreadable response → Time-out Reversal (TC-002890)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028901` (original `028901`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase timed out → TOR sent ahead of next Purchase (TC-002891)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028911` (original `028911`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Time-out Reversal unanswered → retried (max 3) (TC-002892)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028921` (original `028921`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

Step 3 (tor retry) against step 2 (timeout reversal): D-15 applies to step 2; step 3 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028921` (original `028921`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 2 amount | `1500` (step 2 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

Step 4 (tor retry) against step 2 (timeout reversal): D-15 applies to step 2; step 4 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028921` (original `028921`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 2 amount | `1500` (step 2 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T8 – Purchase → Purchase Reversal → Settlement (TC-002894)

Step 2 (reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028941` (original `028941`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T8 – Purchase timed out → TOR → Settlement (TC-002896)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `028961` (original `028961`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T10 – Time-out Reversal with no original → declined (unmatched) (TC-002899)

Step 1 (timeout reversal) against step 1 (timeout reversal): D-01, D-02, D-12 and D-15 apply to step 1; step 1 has D-01, D-02, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `EMV Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | present | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T10 – Purchase → Reversal → Time-out Reversal → declined (TC-002900)

Step 2 (reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029001` (original `029001`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

Step 3 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 3 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029001` (original `029001`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T5 – Purchase → Void declined → Merchandise Return (TC-002902)

Step 2 (reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029021` (original `029021`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T6 – Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase (TC-002903)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029031` (original `029031`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase late approval → Time-out Reversal → Purchase resubmitted (TC-002905)

Step 2 (timeout reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029051` (original `029051`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T5 – Debit Purchase → Purchase Reversal (no PIN) (TC-002911)

Step 2 (reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029111` (original `029111`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T4 – Debit partial approval → Purchase Reversal of the approved amount (TC-002915)

Step 2 (reversal) against step 1 (purchase): D-01, D-02, D-12, D-15 and D-16 apply to step 1; step 2 has D-14 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Segment 130 on a reversal is optional (Section 10.14.2.4, PDF p.161 (printed 10-82)) | absent | PASS |
| Same Sequence Number as the original | `029151` (original `029151`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `05` (original `05`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 068 (using the test case value for Nonfuel Amount, Approval Number) | `068` | FAIL |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

---

## 5. What passes

Aggregated over all 60 test data files of this scenario.

| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |
|---|---|---|
| Message / Data Section 1 | Message type `EMV Financial Transaction Request`/`Financial Transaction Request`/`Totals Request`; Message Format Version Identifier `ATL105`; Number of Segments `03`/`02`/`01` | – |
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `0011`/`Z011`/`8011`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`/`500`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-002915 step 2); Account Number (FAIL in TC-002887 step 1; FAIL in TC-002888 step 1; FAIL in TC-002888 step 2; FAIL in TC-002889 step 1; FAIL in TC-002889 step 2; FAIL in TC-002890 step 1; FAIL in TC-002890 step 2; FAIL in TC-002891 step 1; FAIL in TC-002891 step 2; FAIL in TC-002891 step 3; FAIL in TC-002892 step 1; FAIL in TC-002892 step 2; FAIL in TC-002892 step 3; FAIL in TC-002892 step 4; FAIL in TC-002893 step 1; FAIL in TC-002894 step 1; FAIL in TC-002894 step 2; FAIL in TC-002895 step 1; FAIL in TC-002895 step 2; FAIL in TC-002896 step 1; FAIL in TC-002896 step 2; FAIL in TC-002897 step 1; FAIL in TC-002898 step 1; FAIL in TC-002899 step 1; FAIL in TC-002900 step 1; FAIL in TC-002900 step 2; FAIL in TC-002900 step 3; FAIL in TC-002901 step 1; FAIL in TC-002901 step 2; FAIL in TC-002902 step 1; FAIL in TC-002902 step 2; FAIL in TC-002902 step 3; FAIL in TC-002903 step 1; FAIL in TC-002903 step 2; FAIL in TC-002903 step 3; FAIL in TC-002904 step 1; FAIL in TC-002904 step 2; FAIL in TC-002905 step 1; FAIL in TC-002905 step 2; FAIL in TC-002905 step 3; FAIL in TC-002906 step 1; FAIL in TC-002906 step 3; FAIL in TC-002907 step 1; FAIL in TC-002907 step 3; FAIL in TC-002908 step 1; FAIL in TC-002911 step 1; FAIL in TC-002911 step 2; FAIL in TC-002912 step 1; FAIL in TC-002913 step 1; FAIL in TC-002914 step 1; FAIL in TC-002915 step 1; FAIL in TC-002915 step 2); Card Discretionary Block Data (FAIL in TC-002887 step 1; FAIL in TC-002888 step 1; FAIL in TC-002888 step 2; FAIL in TC-002889 step 1; FAIL in TC-002889 step 2; FAIL in TC-002890 step 1; FAIL in TC-002890 step 2; FAIL in TC-002891 step 1; FAIL in TC-002891 step 2; FAIL in TC-002891 step 3; FAIL in TC-002892 step 1; FAIL in TC-002892 step 2; FAIL in TC-002892 step 3; FAIL in TC-002892 step 4; FAIL in TC-002893 step 1; FAIL in TC-002894 step 1; FAIL in TC-002894 step 2; FAIL in TC-002895 step 1; FAIL in TC-002895 step 2; FAIL in TC-002896 step 1; FAIL in TC-002896 step 2; FAIL in TC-002897 step 1; FAIL in TC-002898 step 1; FAIL in TC-002899 step 1; FAIL in TC-002900 step 1; FAIL in TC-002900 step 2; FAIL in TC-002900 step 3; FAIL in TC-002901 step 1; FAIL in TC-002901 step 2; FAIL in TC-002902 step 1; FAIL in TC-002902 step 2; FAIL in TC-002902 step 3; FAIL in TC-002903 step 1; FAIL in TC-002903 step 2; FAIL in TC-002903 step 3; FAIL in TC-002904 step 1; FAIL in TC-002904 step 2; FAIL in TC-002905 step 1; FAIL in TC-002905 step 2; FAIL in TC-002905 step 3; FAIL in TC-002906 step 1; FAIL in TC-002906 step 3; FAIL in TC-002907 step 1; FAIL in TC-002907 step 3; FAIL in TC-002908 step 1; FAIL in TC-002911 step 1; FAIL in TC-002911 step 2; FAIL in TC-002912 step 1; FAIL in TC-002913 step 1; FAIL in TC-002914 step 1; FAIL in TC-002915 step 1; FAIL in TC-002915 step 2); Encrypted PIN Block Data (FAIL in TC-002887 step 1; FAIL in TC-002888 step 1; FAIL in TC-002889 step 1; FAIL in TC-002890 step 1; FAIL in TC-002891 step 1; FAIL in TC-002891 step 3; FAIL in TC-002892 step 1; FAIL in TC-002893 step 1; FAIL in TC-002894 step 1; FAIL in TC-002895 step 1; FAIL in TC-002895 step 2; FAIL in TC-002896 step 1; FAIL in TC-002897 step 1; FAIL in TC-002898 step 1; FAIL in TC-002900 step 1; FAIL in TC-002901 step 1; FAIL in TC-002901 step 2; FAIL in TC-002902 step 1; FAIL in TC-002902 step 3; FAIL in TC-002903 step 1; FAIL in TC-002903 step 3; FAIL in TC-002904 step 1; FAIL in TC-002904 step 2; FAIL in TC-002905 step 1; FAIL in TC-002905 step 3; FAIL in TC-002906 step 1; FAIL in TC-002906 step 3; FAIL in TC-002907 step 1; FAIL in TC-002907 step 3; FAIL in TC-002908 step 1; FAIL in TC-002911 step 1; FAIL in TC-002912 step 1; FAIL in TC-002913 step 1; FAIL in TC-002914 step 1; FAIL in TC-002915 step 1); Nonfuel Amount (BLOCKED in TC-002915 step 2); Sequence Number (FAIL in TC-002906 step 3; FAIL in TC-002907 step 3); Approval Number (BLOCKED in TC-002894 step 2; BLOCKED in TC-002900 step 2; BLOCKED in TC-002902 step 2; BLOCKED in TC-002911 step 2; BLOCKED in TC-002915 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `051`; Variable Information (Table 031) `12` | – |
| Segment 130 and EMV chip data | Segment Type `130`; Segment Length `1181`; CA Public Key File Checksum `2012091700000100000102171`; EMV Chip Data Length `147`; EMV Chip Data `294 hex characters = 147 bytes, 22 tags`; Tag 5F2A Transaction Currency Code `0840`; Tag 9F1A Terminal Country Code `0840`; Tag 9F27 Cryptogram Information Data `80`; Tag 9F34 CVM Results `420300`; EMV Additional Information Indicator `001`; EMV Additional Information Length `984` | EMV Card Sequence Number (FAIL in TC-002887 step 1; FAIL in TC-002888 step 1; FAIL in TC-002889 step 1; FAIL in TC-002890 step 1; FAIL in TC-002891 step 1; FAIL in TC-002891 step 3; FAIL in TC-002892 step 1; FAIL in TC-002893 step 1; FAIL in TC-002894 step 1; FAIL in TC-002895 step 1; FAIL in TC-002895 step 2; FAIL in TC-002896 step 1; FAIL in TC-002897 step 1; FAIL in TC-002898 step 1; FAIL in TC-002899 step 1; FAIL in TC-002900 step 1; FAIL in TC-002901 step 1; FAIL in TC-002901 step 2; FAIL in TC-002902 step 1; FAIL in TC-002902 step 3; FAIL in TC-002903 step 1; FAIL in TC-002903 step 3; FAIL in TC-002904 step 1; FAIL in TC-002904 step 2; FAIL in TC-002905 step 1; FAIL in TC-002905 step 3; FAIL in TC-002906 step 1; FAIL in TC-002906 step 3; FAIL in TC-002907 step 1; FAIL in TC-002907 step 3; FAIL in TC-002908 step 1; FAIL in TC-002911 step 1; FAIL in TC-002912 step 1; FAIL in TC-002913 step 1; FAIL in TC-002914 step 1; FAIL in TC-002915 step 1); Tag 9F02 Amount, Authorized (FAIL in TC-002913 step 1; FAIL in TC-002914 step 1); Tag 9F03 Amount, Other (FAIL in TC-002913 step 1; FAIL in TC-002914 step 1); Tag 9C Transaction Type (FAIL in TC-002913 step 1; FAIL in TC-002914 step 1); EMV Additional Information (FAIL in TC-002887 step 1; FAIL in TC-002888 step 1; FAIL in TC-002889 step 1; FAIL in TC-002890 step 1; FAIL in TC-002891 step 1; FAIL in TC-002891 step 3; FAIL in TC-002892 step 1; FAIL in TC-002893 step 1; FAIL in TC-002894 step 1; FAIL in TC-002895 step 1; FAIL in TC-002895 step 2; FAIL in TC-002896 step 1; FAIL in TC-002897 step 1; FAIL in TC-002898 step 1; FAIL in TC-002899 step 1; FAIL in TC-002900 step 1; FAIL in TC-002901 step 1; FAIL in TC-002901 step 2; FAIL in TC-002902 step 1; FAIL in TC-002902 step 3; FAIL in TC-002903 step 1; FAIL in TC-002903 step 3; FAIL in TC-002904 step 1; FAIL in TC-002904 step 2; FAIL in TC-002905 step 1; FAIL in TC-002905 step 3; FAIL in TC-002906 step 1; FAIL in TC-002906 step 3; FAIL in TC-002907 step 1; FAIL in TC-002907 step 3; FAIL in TC-002908 step 1; FAIL in TC-002911 step 1; FAIL in TC-002912 step 1; FAIL in TC-002913 step 1; FAIL in TC-002914 step 1; FAIL in TC-002915 step 1) |
| Segment 105 (Totals Request) | Segment Type `105`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `990`; Employee Number `(empty)`; Password `(empty)`; Totals Date `222222`/`333333`/`444444`; Hardware Version `71K2YF7Z`/`C9ZYOXWD`/`C4BVWFTT`; Software Version `WO7CZCLE`/`HAANINL1`/`TA5LB71R`; Firmware Version `PQCR3F0V`/`7W4LGF6Q`/`JEZHQ4A4`; Sequence Number `028932`/`028942`/`028953`; Currency Code `840`; Grand Total `00001500`/`00000000`; Card Type Total Amount `00001500`/`00000000` | Segment Length (FAIL in TC-002893 step 2; FAIL in TC-002894 step 3; FAIL in TC-002895 step 3; FAIL in TC-002896 step 3; FAIL in TC-002897 step 2; FAIL in TC-002898 step 2; FAIL in TC-002906 step 2; FAIL in TC-002907 step 2); Card Label (FAIL in TC-002893 step 2; FAIL in TC-002894 step 3; FAIL in TC-002895 step 3; FAIL in TC-002896 step 3; FAIL in TC-002897 step 2; FAIL in TC-002898 step 2; FAIL in TC-002906 step 2; FAIL in TC-002907 step 2); Card Type Total Count (REVIEW in TC-002894 step 3) |
| Step-to-step links | 76 of 77 checks PASS | Totals match the transactions of the flow (Section 9.2) (FAIL in TC-002896) |
| Expected response codes | 38 of 38 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 312 of 312 hold | – |
| TC-declared `link_defs` | 76 of 76 hold; 11 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | – | TC-002906: Sequence Number; TC-002907: Sequence Number |
| Spec quotes in the test cases | 123 of 152 found word-for-word on the cited page | p.512: Not word-for-word: all its words are on the cited page, but not in this form |

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Exact corrected value of Element 192 / framing of Element 118 for indicator 001 | Appendix T (p.701) lists *"Table ID n3 Fixed value: 001"* and *"Table Length n Fixed"* but never states the Table Length width. The value may be `EMVYES` alone (length `006`) or framed with Table ID + Table Length (longer). | all EMV requests | Appendix T, PDF p.701 (printed T-1) | BUYPASS / SME or a certified device trace |
| Q-02 | Packed-BCD 2-byte prefix (`0145`) inside Element 190 counted in Element 189 (`147`) | Matches the Appendix R example (*"01\06 EMV/Chip Data length (BCD packed)"*, p.697) and Element 190 (*"All fields after the table length are in binary format."*), but the field boundary is not stated explicitly. | all EMV requests | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138); Element 189, PDF p.477 (printed 13-137) | Producer adapter declaration / SME |
| Q-03 | The CA Public Key File Checksum is a sample value | The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value. | TC-002887, TC-002888, TC-002889, TC-002890, TC-002891, TC-002892, TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002899, TC-002900, TC-002901, TC-002902, TC-002903, TC-002904, TC-002905, TC-002906, TC-002907, TC-002908, TC-002911, TC-002912, TC-002913, TC-002914, TC-002915 | Element 187, PDF p.477 (printed 13-137) | Host CA Public Key file |
| Q-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer. | TC-002887, TC-002888, TC-002889, TC-002890, TC-002891, TC-002892, TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002899, TC-002900, TC-002901, TC-002902, TC-002903, TC-002904, TC-002905, TC-002906, TC-002907, TC-002908, TC-002911, TC-002912, TC-002913, TC-002914, TC-002915 | Element 190, PDF p.478 (printed 13-138) | Card simulator / HSM |
| Q-05 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-002887, TC-002888, TC-002889, TC-002890, TC-002891, TC-002892, TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002899, TC-002900, TC-002901, TC-002902, TC-002903, TC-002904, TC-002905, TC-002906, TC-002907, TC-002908, TC-002911, TC-002912, TC-002913, TC-002914, TC-002915 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-06 | The quote cited for p.512 is not word-for-word in ATL105 | The test case quotes "48 Interlink - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form. | TC-002887, TC-002888, TC-002889, TC-002890, TC-002891, TC-002892, TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002899, TC-002900, TC-002901, TC-002902, TC-002903, TC-002904, TC-002905, TC-002906, TC-002907, TC-002908, TC-002909, TC-002910, TC-002911, TC-002912, TC-002913, TC-002914, TC-002915 | – | Test case generator (citation) |
| Q-07 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-002887, TC-002888, TC-002889, TC-002890, TC-002891, TC-002892, TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002899, TC-002900, TC-002901, TC-002902, TC-002903, TC-002904, TC-002905, TC-002906, TC-002907, TC-002908, TC-002911, TC-002912, TC-002913, TC-002914, TC-002915 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-08 | The device version fields of the Totals Request are generated values | ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device. | TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002906, TC-002907 | Element 43, PDF p.386 (printed 13-46); Element 96, PDF p.414 (printed 13-74); Element 39, PDF p.384 (printed 13-44) | Client (versions of the certified device) |
| Q-09 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-002893, TC-002894, TC-002895, TC-002896, TC-002897, TC-002898, TC-002902, TC-002906, TC-002907 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-10 | The totals bucket is sent with a count of 00000 | Element 16 allows 00001-99999, and the category section is sent only when data occurs for a card type. A net count of 00000 suggests the bucket should not be sent at all. | TC-002894 | Element 16, PDF p.375 (printed 13-35); Section 12.6, PDF p.237 (printed 12-20) | BUYPASS / SME |
| Q-11 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S, reversing Authorization Only codes 3 and 5), while this step is a Purchase Reversal (Prompt Code 8020, Transaction Type 8). | TC-002894, TC-002900, TC-002902, TC-002911, TC-002915 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.546 (printed G-2); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-12 | Same request as TC-002887 | The request is identical to TC-002887 (T1-PUR) except the Sequence Number: same card ..., expiry , amount $15.00 and entry mode 051. BUYPASS cannot tell the two tests apart. | TC-002908, TC-002912, TC-002914 | – | Device-level verification during execution |
| Q-13 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-002914, TC-002915 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |
| Q-14 | Variable-cut test merchant. | Listed by the test case itself as an open item (input needed before execution). | TC-002893, TC-002894, TC-002895, TC-002896, TC-002906, TC-002907 | – | Client / test case owner |
| Q-15 | Totals bucket rules are stated only in the FSA/HRA section (p.89). | Listed by the test case itself as an open item (input needed before execution). | TC-002893 | – | Client / test case owner |
| Q-16 | Test-environment cut time; observable only on the next business day. | Listed by the test case itself as an open item (input needed before execution). | TC-002898 | – | Client / test case owner |
| Q-17 | Simulator able to decline a Purchase Reversal. | Listed by the test case itself as an open item (input needed before execution). | TC-002902 | – | Client / test case owner |
| Q-18 | Petroleum spec rule applied to a general credit merchant (SME_QUESTIONS_FLOWS FQ-20). | Listed by the test case itself as an open item (input needed before execution). | TC-002902, TC-002906, TC-002907 | – | Client / test case owner |
| Q-19 | Simulator able to withhold four consecutive responses. | Listed by the test case itself as an open item (input needed before execution). | TC-002903 | – | Client / test case owner |
| Q-20 | The rule applies in an IP environment (p.40): confirm the test terminal connects over IP. | Listed by the test case itself as an open item (input needed before execution). | TC-002903 | – | Client / test case owner |
| Q-21 | The communications test/health message (Transaction Type 900, p.187) is checked on the device; it is not composed. | Listed by the test case itself as an open item (input needed before execution). | TC-002903 | – | Client / test case owner |
| Q-22 | Decline Codes 4I/6C are listed for authorizers AR and DIAL: confirm they apply to Interlink debit (SME_QUESTIONS_FLOWS FQ-19). | Listed by the test case itself as an open item (input needed before execution). | TC-002904 | – | Client / test case owner |
| Q-23 | Simulator able to return Decline Code 4I or 6C. | Listed by the test case itself as an open item (input needed before execution). | TC-002904 | – | Client / test case owner |
| Q-24 | Several partial returns against one purchase are not described (SME_QUESTIONS_FLOWS FQ-04). | Listed by the test case itself as an open item (input needed before execution). | TC-002907 | – | Client / test case owner |
| Q-25 | Decline test card or simulator decline trigger. | Listed by the test case itself as an open item (input needed before execution). | TC-002908 | – | Client / test case owner |
| Q-26 | Decline Codes for Interlink are not listed (p.379 'BUYPASS defines this code'): SME_QUESTIONS_FLOWS FQ-06. | Listed by the test case itself as an open item (input needed before execution). | TC-002908 | – | Client / test case owner |
| Q-27 | Terminal table load with card type 173 and a DL6 window (client). | Listed by the test case itself as an open item (input needed before execution). | TC-002909 | – | Client / test case owner |
| Q-28 | What the device does with a blocked transaction is not stated (SME_QUESTIONS_FLOWS FQ-03). | Listed by the test case itself as an open item (input needed before execution). | TC-002909 | – | Client / test case owner |
| Q-29 | Terminal table load with card type 167 (client). | Listed by the test case itself as an open item (input needed before execution). | TC-002910 | – | Client / test case owner |
| Q-30 | Expired debit test card. | Listed by the test case itself as an open item (input needed before execution). | TC-002912 | – | Client / test case owner |

---

## 7. Recommendations for the AI test data generator

1. **E188:** zero-pad Tag 5F34 to 3 bytes (`001`), or send 3 spaces when 5F34 is absent (D-01).
2. **E191/E192/E118:** for indicator `001`, populate EMV Table Data with `EMVYES` / `EMVNOT` per Appendix T-1 (p.701), and set E192 to the resulting length once Q-01 is confirmed. Do not synthesize free text from the 984 maximum; apply the Appendix T allowed values instead of skipping `valid_values_parsed` (D-02).
3. **Recompute the Segment 130 length** after these fixes. With `EMVYES` and no framing, `1181` becomes `0204` (-978 for E118, +1 for E188).
4. **Scenario fidelity:**
   - Follow-on amount after a partial approval: keep it a run-time value from the original response; any provisional amount must be at most 7 digits and not more than the original, and the Segment Length is computed after substitution (D-14).
5. **Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request (10 digits, values from Appendix Q, for the certified terminal) – Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* (D-12).
6. **Element 2 Account Number and Element 12 Card Discretionary Block Data:** Fill in the Account Number and Element 12 from the client's test card: the data read from the card for card-read entry modes, Account Number + expiry YYMM on follow-on messages, the expiry for stored credentials. Until the card profile is supplied, write an explicit placeholder (for example `<CLIENT_CARD_PAN>`) and keep the test case blocked – never an empty string. (D-15).
7. **Element 33 Encrypted PIN Block Data:** Every debit request except the Purchase Reversal and the follow-on messages: Element 33 = DUKPT Key Serial Number + PIN block (Section 10.2.1.3). Until the client supplies the test PIN and DUKPT key, write an explicit placeholder and keep the test case blocked. (D-16).
8. **Any field – test data value vs test case request value:** Write the test data file from the test case request it belongs to (same values); regenerate the files after any change to the test case. (D-24).
9. **Element 190 EMV Chip Data – Tags 9F02, 9F03 and 9C:** Purchase with cash back: generate the chip data from a cash-back transaction – 9F02 = Nonfuel Amount + Cash Amount, 9F03 = Cash Amount, 9C = `09` (EMV Book 3) – and recalculate Elements 189 and 84 of Segment 130. (D-18).
10. **Element 13 Card Label (Totals Request):** Card Label is a fixed 4-character field, left-aligned and space-filled: `DB` → `DB  ` (two trailing spaces). Recalculate the Segment Length afterwards (D-20). (D-19).
11. **Element 84 Segment Length (Totals Request):** Segment 105 length = Fields 1–14 + 14 Field Separators + the Category Total Data Section, which has no separators (Card Label 4 + Count 5 + Amount 8 = 17) (Section 12.6). Do not count a separator after Fields 15–17 and use the 4-character Card Label (D-19). (D-20).
12. **Elements 16, 15, 42 Card Type Total Count, Card Type Total Amount, Grand Total:** Compute the device totals from the steps before the Totals Request (Section 9.2): approved Purchase/Capture or Completion +1 / +amount; approved Reversal −1 / −amount; Merchandise Return +1 / −amount; Authorization Only into the AO bucket, which the grand total leaves out. A purchase that never got a response and was reversed is not in the totals. A card bucket whose net count is zero is not sent. (D-21).
13. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-09).
14. **Avoid reusing identical generated values** across different test cases (D-02).
15. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-05, Q-06, Q-11). The data values are not affected.
16. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-07).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS1 | TC-002887 | T1 – Purchase approved | FAIL | [TC-002887_ATL105_Field_Validation.md](./TC-002887_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002888 | T6 – Purchase, no response → Time-out Reversal | FAIL | [TC-002888_ATL105_Field_Validation.md](./TC-002888_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002889 | T6 – Purchase, late approval → Time-out Reversal | FAIL | [TC-002889_ATL105_Field_Validation.md](./TC-002889_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002890 | T6 – Purchase, unreadable response → Time-out Reversal | FAIL | [TC-002890_ATL105_Field_Validation.md](./TC-002890_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002891 | T6 – Purchase timed out → TOR sent ahead of next Purchase | FAIL | [TC-002891_ATL105_Field_Validation.md](./TC-002891_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002892 | T6 – Time-out Reversal unanswered → retried (max 3) | FAIL | [TC-002892_ATL105_Field_Validation.md](./TC-002892_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002893 | T8 – Purchase → Settlement (read and cut 222222) | FAIL | [TC-002893_ATL105_Field_Validation.md](./TC-002893_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002894 | T8 – Purchase → Purchase Reversal → Settlement | FAIL | [TC-002894_ATL105_Field_Validation.md](./TC-002894_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002895 | T8 – Purchase + Merchandise Return → Settlement | FAIL | [TC-002895_ATL105_Field_Validation.md](./TC-002895_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002896 | T8 – Purchase timed out → TOR → Settlement | FAIL | [TC-002896_ATL105_Field_Validation.md](./TC-002896_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002897 | T8 – Purchase → Read-only totals (333333) | FAIL | [TC-002897_ATL105_Field_Validation.md](./TC-002897_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002898 | T8 – Purchase → Auto-close at cut time | FAIL | [TC-002898_ATL105_Field_Validation.md](./TC-002898_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002899 | T10 – Time-out Reversal with no original → declined (unmatched) | FAIL | [TC-002899_ATL105_Field_Validation.md](./TC-002899_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002900 | T10 – Purchase → Reversal → Time-out Reversal → declined | FAIL | [TC-002900_ATL105_Field_Validation.md](./TC-002900_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002901 | T5 – Purchase → next Purchase on the same device (new Sequence Number) | FAIL | [TC-002901_ATL105_Field_Validation.md](./TC-002901_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002902 | T5 – Purchase → Void declined → Merchandise Return | FAIL | [TC-002902_ATL105_Field_Validation.md](./TC-002902_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002903 | T6 – Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | FAIL | [TC-002903_ATL105_Field_Validation.md](./TC-002903_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002904 | T6 – Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | FAIL | [TC-002904_ATL105_Field_Validation.md](./TC-002904_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002905 | T6 – Purchase late approval → Time-out Reversal → Purchase resubmitted | FAIL | [TC-002905_ATL105_Field_Validation.md](./TC-002905_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002906 | T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | FAIL | [TC-002906_ATL105_Field_Validation.md](./TC-002906_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002907 | T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | FAIL | [TC-002907_ATL105_Field_Validation.md](./TC-002907_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002908 | T10 – Purchase declined by the issuer | FAIL | [TC-002908_ATL105_Field_Validation.md](./TC-002908_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002909 | T11 – Store and Forward blocking window (card type 173) → no off-line approval inside the window | REVIEW | [TC-002909_ATL105_Field_Validation.md](./TC-002909_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002910 | T11 – Void/Reversal disabled (card type 167) → not offered at the device | REVIEW | [TC-002910_ATL105_Field_Validation.md](./TC-002910_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002911 | T5 – Debit Purchase → Purchase Reversal (no PIN) | FAIL | [TC-002911_ATL105_Field_Validation.md](./TC-002911_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002912 | T2 – Expired debit card → sent online (no device expiry check) → issuer decides | FAIL | [TC-002912_ATL105_Field_Validation.md](./TC-002912_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002913 | T1 – Debit Purchase with Cashback → approved | FAIL | [TC-002913_ATL105_Field_Validation.md](./TC-002913_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002914 | T4 – Debit Purchase with Cashback partially approved → approved amount covers the purchase only | FAIL | [TC-002914_ATL105_Field_Validation.md](./TC-002914_ATL105_Field_Validation.md) |
| BR1_TS1 | TC-002915 | T4 – Debit partial approval → Purchase Reversal of the approved amount | FAIL | [TC-002915_ATL105_Field_Validation.md](./TC-002915_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py --data "interlink_debit" --all
```
