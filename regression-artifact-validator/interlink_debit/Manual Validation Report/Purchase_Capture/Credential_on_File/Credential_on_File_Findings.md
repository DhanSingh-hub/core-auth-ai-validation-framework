# ATL105 Test Data Validation – Findings Summary – Purchase/Capture – Credential on File

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – Interlink Debit – Purchase/Capture |
| Scenario | BR1_TS6 Purchase/Capture – Credential on File (scenario status: unconfirmed) |
| Entry mode | Credential on File (Table 005 PAN entry mode `10` = credential on file) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `interlink_debit/test_data` |
| Test case catalog | `interlink_debit/test_cases/test_cases.jsonl` (lines 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177) |
| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |
| Scope | All 29 test cases of this scenario (T1 ×2, T2, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2), 60 test data files (21 multi-step flows); 2 device-only test cases without a host message (ATL105 citations checked only) |
| Date | 2026-11-04 |
| Overall result | **27 / 29 test cases FAIL** (2 REVIEW) – 3 defects common to every test case (D-09, D-12, D-15), plus 6 scenario-specific defects (D-14, D-16, D-19, D-20, D-21, D-24) |

Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All scenarios: [../../README.md](../../README.md).

Page numbers are PDF pages of the 2026-4 edition (printed page in brackets where given). The model report used the older 2026-3 edition, so its page numbers differ (see the README).

---

## 1. What was validated

Every field of every test data file was checked against the ATL105 layouts and element definitions:

| Area | Checks | Spec reference |
|---|---|---|
| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present | Section 11.1 p.167 |
| Segment 100 (17 fields) | Presence, character type, length and valid values of every field; Prompt Code Transaction Type(s) 0, 7, 8, Z for the step roles; Segment Length recomputed from the fields plus Field Separators; run-time placeholders | Section 12.1 p.223, Element 5 p.368, Element 33 p.382, Element 44 p.386, Element 58 p.393, Element 78 p.400, Element 84 p.406, Element 86 p.409, Element 102 p.417, Element 121 p.440, Appendix E p.521, Appendix G p.545, Appendix G p.546 |
| Card data (Elements 2 + 12) | stored credential (10): expiry only, no track data; follow-on steps send the truncated expiry YYMM only; card-number check digit | Appendix J p.638, Section 10.1.8.2 p.94, Section 10.1.1.2 p.81, Element 2 p.364 |
| Segment 111 | Table 005 (PAN entry mode + terminal PIN capability), Table 031 (terminal capability), Table 030 (POS Condition Code) for EMV and Credential on File transactions, Tables 032/049/056 (Credential on File indicators); Element 112 = data length; Segment Length | Section 12.10 p.246, Appendix I p.555, Appendix I p.569, Appendix J p.638, Appendix I p.568, Appendix I p.575, Appendix I p.589, Section 10.13.2.4.2 p.144 |
| Debit rules (Section 10.2) | Card type 011 in the Prompt Code; PIN block (Element 33, DUKPT) on every request except the Purchase Reversal and follow-on messages; no PIN block on follow-on messages; no manual (keyed) entry; no Time-out Reversal of a Completion | Section 10.2.1.3 p.96, Section 10.2.1.4 p.96, Section 10.2.5.1 p.105, Section 10.2.2 p.97, Appendix E p.521 |
| Totals Request (Segment 105, 17 fields) | Data Section 1 + Segment 105 only; presence, type, length and value of every field (Prompt Code 990, Totals Date, versions, Sequence Number, Card Label 4 characters); Segment Length (14 Field Separators, none in the Category Total Data Section); count, amount and grand total against the approved steps of the flow (Section 9.2) | Section 11.4.1.1 p.178, Section 12.6 p.235, Section 12.6 p.237, Element 13 p.372, Element 16 p.375, Element 15 p.374, Element 42 p.386, Element 105 p.419, Section 9.2 p.72 |
| Test objective | The data can prove what the scenario claims: expired card, time-out reversal, reversal, partial approval | Section 10.14.2.1 p.160, Element 12 p.372, Section 2.3 p.39, Section 2.3.1 p.40, Section 10.1.2.1 p.84, Element 5 p.368 |
| Step-to-step links | Sequence Number, Account Number, terminal and entry mode against the original step | Section 2.3.1 p.40, Appendix J p.638, Element 86 p.409 |
| Expected response codes | Expected Response Code of each step fits the step; open ('?') codes are listed | Element 83 p.404 |
| TC-declared checks | Every `rule_checks` and `link_defs` entry of the test case executed against the test data | TC catalog |
| Generation fidelity | Every test data value compared with the test case request value | TC catalog |
| Spec citations | Every `spec_basis` quote and cited page of the test case checked against the 2026-4 text | TC catalog |
| Device-only test cases | TC-003057 and TC-003058: no host message, so no test data; the ATL105 text each quotes is checked on its page and its device procedure is listed in its report | TC catalog |

Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note for the message builder.

---

## 2. Results by test case

| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |
|---|---|---|---|---|---|---|---|
| T1 – Purchase approved | TC-003035 (BR1_TS6_TC1) | 1 | **FAIL** | 4 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Purchase, no response → Time-out Reversal | TC-003036 (BR1_TS6_TC9) | 2 | **FAIL** | 7 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Purchase, late approval → Time-out Reversal | TC-003037 (BR1_TS6_TC10) | 2 | **FAIL** | 7 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Purchase, unreadable response → Time-out Reversal | TC-003038 (BR1_TS6_TC11) | 2 | **FAIL** | 7 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Purchase timed out → TOR sent ahead of next Purchase | TC-003039 (BR1_TS6_TC12) | 3 | **FAIL** | 11 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Time-out Reversal unanswered → retried (max 3) | TC-003040 (BR1_TS6_TC13) | 4 | **FAIL** | 13 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T8 – Purchase → Settlement (read and cut 222222) | TC-003041 (BR1_TS6_TC17) | 2 | **FAIL** | 6 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase → Purchase Reversal → Settlement | TC-003042 (BR1_TS6_TC18) | 3 | **FAIL** | 9 | 1 | 8 | D-09, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase + Merchandise Return → Settlement | TC-003043 (BR1_TS6_TC19) | 3 | **FAIL** | 10 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase timed out → TOR → Settlement | TC-003044 (BR1_TS6_TC20) | 3 | **FAIL** | 10 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20, D-21 |
| T8 – Purchase → Read-only totals (333333) | TC-003045 (BR1_TS6_TC21) | 2 | **FAIL** | 6 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20 |
| T8 – Purchase → Auto-close at cut time | TC-003046 (BR1_TS6_TC22) | 2 | **FAIL** | 6 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20 |
| T10 – Time-out Reversal with no original → declined (unmatched) | TC-003047 (BR1_TS6_TC25) | 1 | **FAIL** | 3 | 0 | 4 | D-09, D-12, D-15 |
| T10 – Purchase → Reversal → Time-out Reversal → declined | TC-003048 (BR1_TS6_TC26) | 3 | **FAIL** | 10 | 1 | 5 | D-09, D-12, D-15, D-16 |
| T5 – Purchase → next Purchase on the same device (new Sequence Number) | TC-003049 (BR1_TS6_TC6) | 2 | **FAIL** | 8 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T5 – Purchase → Void declined → Merchandise Return | TC-003050 (BR1_TS6_TC7) | 3 | **FAIL** | 11 | 1 | 6 | D-09, D-12, D-15, D-16 |
| T6 – Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | TC-003051 (BR1_TS6_TC14) | 3 | **FAIL** | 11 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | TC-003052 (BR1_TS6_TC15) | 2 | **FAIL** | 8 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T6 – Purchase late approval → Time-out Reversal → Purchase resubmitted | TC-003053 (BR1_TS6_TC16) | 3 | **FAIL** | 11 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | TC-003054 (BR1_TS6_TC23) | 3 | **FAIL** | 11 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20, D-24 |
| T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | TC-003055 (BR1_TS6_TC24) | 3 | **FAIL** | 11 | 0 | 6 | D-09, D-12, D-15, D-16, D-19, D-20, D-24 |
| T10 – Purchase declined by the issuer | TC-003056 (BR1_TS6_TC27) | 1 | **FAIL** | 4 | 0 | 5 | D-09, D-12, D-15, D-16 |
| T11 – Store and Forward blocking window (card type 173) → no off-line approval inside the window | TC-003057 (BR1_TS6_TC28) | 0 | **REVIEW** | 0 | 0 | 1 | – |
| T11 – Void/Reversal disabled (card type 167) → not offered at the device | TC-003058 (BR1_TS6_TC29) | 0 | **REVIEW** | 0 | 0 | 1 | – |
| T5 – Debit Purchase → Purchase Reversal (no PIN) | TC-003059 (BR1_TS6_TC8) | 2 | **FAIL** | 7 | 1 | 5 | D-09, D-12, D-15, D-16 |
| T2 – Expired debit card → sent online (no device expiry check) → issuer decides | TC-003060 (BR1_TS6_TC3) | 1 | **FAIL** | 4 | 0 | 5 | D-09, D-12, D-15, D-16 |
| T1 – Debit Purchase with Cashback → approved | TC-003061 (BR1_TS6_TC2) | 1 | **FAIL** | 4 | 0 | 4 | D-09, D-12, D-15, D-16 |
| T4 – Debit Purchase with Cashback partially approved → approved amount covers the purchase only | TC-003062 (BR1_TS6_TC4) | 1 | **FAIL** | 4 | 0 | 6 | D-09, D-12, D-15, D-16 |
| T4 – Debit partial approval → Purchase Reversal of the approved amount | TC-003063 (BR1_TS6_TC5) | 2 | **FAIL** | 10 | 2 | 6 | D-09, D-12, D-14, D-15, D-16 |

Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the same in all scenario reports (catalog in the README).

---

## 3. Defects common to every test case

### D-09 – Credential on File indicators (Tables 032, 049, 056) are missing

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003035 | 1 (purchase) | TD-TC-003035.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003036 | 1 (purchase) | TD-TC-003036.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003036 | 2 (timeout reversal) | TD-TC-003036.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003037 | 1 (purchase) | TD-TC-003037.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003037 | 2 (timeout reversal) | TD-TC-003037.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003038 | 1 (purchase) | TD-TC-003038.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003038 | 2 (timeout reversal) | TD-TC-003038.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003039 | 1 (purchase) | TD-TC-003039.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003039 | 2 (timeout reversal) | TD-TC-003039.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003039 | 3 (next transaction) | TD-TC-003039.step3.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003040 | 1 (purchase) | TD-TC-003040.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003040 | 2 (timeout reversal) | TD-TC-003040.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003040 | 3 (tor retry) | TD-TC-003040.step3.tor-retry.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003040 | 4 (tor retry) | TD-TC-003040.step4.tor-retry.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003041 | 1 (purchase) | TD-TC-003041.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003042 | 1 (purchase) | TD-TC-003042.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003042 | 2 (reversal) | TD-TC-003042.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003043 | 1 (purchase) | TD-TC-003043.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003043 | 2 (return) | TD-TC-003043.step2.return.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003044 | 1 (purchase) | TD-TC-003044.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003044 | 2 (timeout reversal) | TD-TC-003044.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003045 | 1 (purchase) | TD-TC-003045.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003046 | 1 (purchase) | TD-TC-003046.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003047 | 1 (timeout reversal) | TD-TC-003047.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003048 | 1 (purchase) | TD-TC-003048.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003048 | 2 (reversal) | TD-TC-003048.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003048 | 3 (timeout reversal) | TD-TC-003048.step3.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003049 | 1 (purchase) | TD-TC-003049.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003049 | 2 (next transaction) | TD-TC-003049.step2.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003050 | 1 (purchase) | TD-TC-003050.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003050 | 2 (reversal) | TD-TC-003050.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003050 | 3 (return) | TD-TC-003050.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003051 | 1 (purchase) | TD-TC-003051.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003051 | 2 (timeout reversal) | TD-TC-003051.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003051 | 3 (next transaction) | TD-TC-003051.step3.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003052 | 1 (purchase) | TD-TC-003052.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003052 | 2 (resubmitted) | TD-TC-003052.step2.resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003053 | 1 (purchase) | TD-TC-003053.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003053 | 2 (timeout reversal) | TD-TC-003053.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003053 | 3 (resubmitted) | TD-TC-003053.step3.resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003054 | 1 (purchase) | TD-TC-003054.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003054 | 3 (return) | TD-TC-003054.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003055 | 1 (purchase) | TD-TC-003055.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003055 | 3 (return) | TD-TC-003055.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003056 | 1 (purchase) | TD-TC-003056.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003059 | 1 (purchase) | TD-TC-003059.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003059 | 2 (reversal) | TD-TC-003059.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003060 | 1 (purchase) | TD-TC-003060.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003061 | 1 (purchase) | TD-TC-003061.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003062 | 1 (purchase) | TD-TC-003062.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003063 | 1 (purchase) | TD-TC-003063.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| TC-003063 | 2 (reversal) | TD-TC-003063.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |

**Why (TC-003035):** Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').

Spec:
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, p.575 (printed Appendix I-26): *"It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."*
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, p.589 (printed Appendix I-40): *"This field is required for all subsequent Credential on File transactions."*
- Appendix J. Valid Point-of-Service Entry Mode Codes, p.638 (printed Appendix J-1): *"10 Credential on file"*

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003035 | 1 (purchase) | TD-TC-003035.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003036 | 1 (purchase) | TD-TC-003036.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003036 | 2 (timeout reversal) | TD-TC-003036.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003037 | 1 (purchase) | TD-TC-003037.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003037 | 2 (timeout reversal) | TD-TC-003037.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003038 | 1 (purchase) | TD-TC-003038.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003038 | 2 (timeout reversal) | TD-TC-003038.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003039 | 1 (purchase) | TD-TC-003039.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003039 | 2 (timeout reversal) | TD-TC-003039.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003039 | 3 (next transaction) | TD-TC-003039.step3.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003040 | 1 (purchase) | TD-TC-003040.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003040 | 2 (timeout reversal) | TD-TC-003040.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003040 | 3 (tor retry) | TD-TC-003040.step3.tor-retry.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003040 | 4 (tor retry) | TD-TC-003040.step4.tor-retry.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003041 | 1 (purchase) | TD-TC-003041.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003042 | 1 (purchase) | TD-TC-003042.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003042 | 2 (reversal) | TD-TC-003042.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003043 | 1 (purchase) | TD-TC-003043.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003043 | 2 (return) | TD-TC-003043.step2.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003044 | 1 (purchase) | TD-TC-003044.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003044 | 2 (timeout reversal) | TD-TC-003044.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003045 | 1 (purchase) | TD-TC-003045.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003046 | 1 (purchase) | TD-TC-003046.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003047 | 1 (timeout reversal) | TD-TC-003047.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003048 | 1 (purchase) | TD-TC-003048.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003048 | 2 (reversal) | TD-TC-003048.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003048 | 3 (timeout reversal) | TD-TC-003048.step3.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003049 | 1 (purchase) | TD-TC-003049.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003049 | 2 (next transaction) | TD-TC-003049.step2.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003050 | 1 (purchase) | TD-TC-003050.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003050 | 2 (reversal) | TD-TC-003050.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003050 | 3 (return) | TD-TC-003050.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003051 | 1 (purchase) | TD-TC-003051.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003051 | 2 (timeout reversal) | TD-TC-003051.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003051 | 3 (next transaction) | TD-TC-003051.step3.next-transaction.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003052 | 1 (purchase) | TD-TC-003052.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003052 | 2 (resubmitted) | TD-TC-003052.step2.resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003053 | 1 (purchase) | TD-TC-003053.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003053 | 2 (timeout reversal) | TD-TC-003053.step2.timeout-reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003053 | 3 (resubmitted) | TD-TC-003053.step3.resubmitted.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003054 | 1 (purchase) | TD-TC-003054.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003054 | 3 (return) | TD-TC-003054.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003055 | 1 (purchase) | TD-TC-003055.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003055 | 3 (return) | TD-TC-003055.step3.return.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003056 | 1 (purchase) | TD-TC-003056.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003059 | 1 (purchase) | TD-TC-003059.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003059 | 2 (reversal) | TD-TC-003059.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003060 | 1 (purchase) | TD-TC-003060.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003061 | 1 (purchase) | TD-TC-003061.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003062 | 1 (purchase) | TD-TC-003062.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003063 | 1 (purchase) | TD-TC-003063.step1.purchase.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| TC-003063 | 2 (reversal) | TD-TC-003063.step2.reversal.json : 30 | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |

**Why (TC-003035):** This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.

Spec:
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

### D-15 – The card data (Account Number, Card Discretionary Block Data) is empty

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003035 | 1 (purchase) | TD-TC-003035.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003036 | 1 (purchase) | TD-TC-003036.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003036 | 2 (timeout reversal) | TD-TC-003036.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003037 | 1 (purchase) | TD-TC-003037.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003037 | 2 (timeout reversal) | TD-TC-003037.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003038 | 1 (purchase) | TD-TC-003038.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003038 | 2 (timeout reversal) | TD-TC-003038.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003039 | 1 (purchase) | TD-TC-003039.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003039 | 2 (timeout reversal) | TD-TC-003039.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003039 | 3 (next transaction) | TD-TC-003039.step3.next-transaction.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003040 | 1 (purchase) | TD-TC-003040.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003040 | 2 (timeout reversal) | TD-TC-003040.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003040 | 3 (tor retry) | TD-TC-003040.step3.tor-retry.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003040 | 4 (tor retry) | TD-TC-003040.step4.tor-retry.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003041 | 1 (purchase) | TD-TC-003041.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003042 | 1 (purchase) | TD-TC-003042.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003042 | 2 (reversal) | TD-TC-003042.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003043 | 1 (purchase) | TD-TC-003043.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003043 | 2 (return) | TD-TC-003043.step2.return.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003044 | 1 (purchase) | TD-TC-003044.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003044 | 2 (timeout reversal) | TD-TC-003044.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003045 | 1 (purchase) | TD-TC-003045.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003046 | 1 (purchase) | TD-TC-003046.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003047 | 1 (timeout reversal) | TD-TC-003047.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003048 | 1 (purchase) | TD-TC-003048.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003048 | 2 (reversal) | TD-TC-003048.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003048 | 3 (timeout reversal) | TD-TC-003048.step3.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003049 | 1 (purchase) | TD-TC-003049.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003049 | 2 (next transaction) | TD-TC-003049.step2.next-transaction.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003050 | 1 (purchase) | TD-TC-003050.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003050 | 2 (reversal) | TD-TC-003050.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003050 | 3 (return) | TD-TC-003050.step3.return.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003051 | 1 (purchase) | TD-TC-003051.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003051 | 2 (timeout reversal) | TD-TC-003051.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003051 | 3 (next transaction) | TD-TC-003051.step3.next-transaction.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003052 | 1 (purchase) | TD-TC-003052.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003052 | 2 (resubmitted) | TD-TC-003052.step2.resubmitted.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003053 | 1 (purchase) | TD-TC-003053.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003053 | 2 (timeout reversal) | TD-TC-003053.step2.timeout-reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003053 | 3 (resubmitted) | TD-TC-003053.step3.resubmitted.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003054 | 1 (purchase) | TD-TC-003054.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003054 | 3 (return) | TD-TC-003054.step3.return.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003055 | 1 (purchase) | TD-TC-003055.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003055 | 3 (return) | TD-TC-003055.step3.return.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003056 | 1 (purchase) | TD-TC-003056.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003059 | 1 (purchase) | TD-TC-003059.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003059 | 2 (reversal) | TD-TC-003059.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003060 | 1 (purchase) | TD-TC-003060.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003061 | 1 (purchase) | TD-TC-003061.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003062 | 1 (purchase) | TD-TC-003062.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003063 | 1 (purchase) | TD-TC-003063.step1.purchase.json : 14 | `(empty)` | Fill in the Account Number and the expiry as MMYY from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |
| TC-003063 | 2 (reversal) | TD-TC-003063.step2.reversal.json : 14 | `(empty)` | Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field. |

**Why (TC-003035):** This purchase must carry the Account Number and the expiry as MMYY, but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.

Spec:
- Element 2 Account Number (Chapter 13), p.364 (printed 13-24): *"a variable length (up to 24 alphanumeric bytes) Account Number"*
- Element 12 Card Discretionary Block Data (Chapter 13), p.372 (printed 13-32): *"For a card whose number has been manually entered, the expiration date is in MMYY format"*
- Element 12 Card Discretionary Block Data (Chapter 13), p.372 (printed 13-32): *"Note: For all authorizations, merchants must send a value even for expired cards."*
- Section 10.2.5.2 Track Support Requirements, p.105 (printed 10-26): *"The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."*

---

## 4. Scenario-specific defects

### D-14 – Follow-on step (completion, cancellation, reversal): amount data built from an impossible value

| TC | Step | Finding | Location | Value | Expected |
|---|---|---|---|---|---|
| TC-003063 | 2 (reversal) | Segment Length was calculated from the invalid test case value | TD-TC-003063.step2.reversal.json -> Standard Message Data Segment -> SegmentLength | `068` | Recalculate the Segment Length after filling in the real values (for example Approved Amount 000001000 -> amount 1000 plus a 6-character Approval Number -> Segment Length 064). |
| TC-003063 | 2 (reversal) | The test case value for Nonfuel Amount is too long | test case step 2 request -> Nonfuel Amount (used to build TD-TC-003063.step2.reversal.json) | `01835614` | Reversal amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |
| TC-003063 | 2 (reversal) | The reversal amount cannot be larger than the original sale | test case step 2 request -> Nonfuel Amount | `01835614` | Reversal amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000). |

**TC-003063 in plain words:** step 1 asks for $15.00 and expects Response Code `F` (partial approval). The reversal in step 2 must carry the amount from the step 1 response, so the test data correctly uses a run-time expression (`<NonfuelAmount from step 1 response>`). But the test case also holds a provisional value `01835614` = $18,356.14 (8 digits; Element 58 allows at most 7 for MasterCard), larger than the $15.00 that was asked for, and the generator computed the declared Segment Length from it.

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
| TC-003035 | 1 (purchase) | TD-TC-003035.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003036 | 1 (purchase) | TD-TC-003036.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003037 | 1 (purchase) | TD-TC-003037.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003038 | 1 (purchase) | TD-TC-003038.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003039 | 1 (purchase) | TD-TC-003039.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003039 | 3 (next transaction) | TD-TC-003039.step3.next-transaction.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003040 | 1 (purchase) | TD-TC-003040.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003041 | 1 (purchase) | TD-TC-003041.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003042 | 1 (purchase) | TD-TC-003042.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003043 | 1 (purchase) | TD-TC-003043.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003043 | 2 (return) | TD-TC-003043.step2.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003044 | 1 (purchase) | TD-TC-003044.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003045 | 1 (purchase) | TD-TC-003045.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003046 | 1 (purchase) | TD-TC-003046.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003048 | 1 (purchase) | TD-TC-003048.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003049 | 1 (purchase) | TD-TC-003049.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003049 | 2 (next transaction) | TD-TC-003049.step2.next-transaction.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003050 | 1 (purchase) | TD-TC-003050.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003050 | 3 (return) | TD-TC-003050.step3.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003051 | 1 (purchase) | TD-TC-003051.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003051 | 3 (next transaction) | TD-TC-003051.step3.next-transaction.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003052 | 1 (purchase) | TD-TC-003052.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003052 | 2 (resubmitted) | TD-TC-003052.step2.resubmitted.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003053 | 1 (purchase) | TD-TC-003053.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003053 | 3 (resubmitted) | TD-TC-003053.step3.resubmitted.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003054 | 1 (purchase) | TD-TC-003054.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003054 | 3 (return) | TD-TC-003054.step3.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003055 | 1 (purchase) | TD-TC-003055.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003055 | 3 (return) | TD-TC-003055.step3.return.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003056 | 1 (purchase) | TD-TC-003056.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003059 | 1 (purchase) | TD-TC-003059.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003060 | 1 (purchase) | TD-TC-003060.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003061 | 1 (purchase) | TD-TC-003061.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003062 | 1 (purchase) | TD-TC-003062.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |
| TC-003063 | 1 (purchase) | TD-TC-003063.step1.purchase.json : 16 | `(empty)` | Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input). |

**Why (TC-003035):** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This purchase (Transaction Type 0) sends no PIN block.

Spec:
- Section 10.2.1.3 PIN Entry, p.96 (printed 10-17): *"All transactions—except the Purchase Reversal transaction—require PIN entry."*
- Section 10.2.1.3 PIN Entry, p.96 (printed 10-17): *"When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."*
- Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"Required for debit and EBT."*
- Element 33 Encrypted PIN Block Data (Chapter 13), p.382 (printed 13-42): *"For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."*

### D-19 – Totals Request: Card Label (Element 13) is not 4 characters

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003041 | 2 (totals) | TD-TC-003041.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003042 | 3 (totals) | TD-TC-003042.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003043 | 3 (totals) | TD-TC-003043.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003044 | 3 (totals) | TD-TC-003044.step3.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003045 | 2 (totals) | TD-TC-003045.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003046 | 2 (totals) | TD-TC-003046.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003054 | 2 (totals) | TD-TC-003054.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |
| TC-003055 | 2 (totals) | TD-TC-003055.step2.totals.json : 20 | `'DB'` | 'DB  ' (DB followed by 2 spaces) |

**Why (TC-003041):** Element 13 is a fixed field of four characters, left-aligned and space-filled. 'DB' has 2, so the fields after it shift by 2 on the wire.

Spec:
- Element 13 Card Label (Chapter 13), p.372 (printed 13-32): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*
- Element 13 Card Label (Chapter 13), p.373 (printed 13-33): *"Debit DB"*

### D-20 – Totals Request: Segment 105 length is counted with the wrong layout

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003041 | 2 (totals) | TD-TC-003041.step2.totals.json : 7 | `108` | 107 |
| TC-003042 | 3 (totals) | TD-TC-003042.step3.totals.json : 7 | `108` | 107 |
| TC-003043 | 3 (totals) | TD-TC-003043.step3.totals.json : 7 | `108` | 107 |
| TC-003044 | 3 (totals) | TD-TC-003044.step3.totals.json : 7 | `108` | 107 |
| TC-003045 | 2 (totals) | TD-TC-003045.step2.totals.json : 7 | `108` | 107 |
| TC-003046 | 2 (totals) | TD-TC-003046.step2.totals.json : 7 | `108` | 107 |
| TC-003054 | 2 (totals) | TD-TC-003054.step2.totals.json : 7 | `108` | 107 |
| TC-003055 | 2 (totals) | TD-TC-003055.step2.totals.json : 7 | `108` | 107 |

**Why (TC-003041):** Fields 1-14 add up to 76 characters, plus 14 Field Separators = 90. Fields 15-17 (the Category Total Data Section) are not separated: Card Label 4 (fixed width) + Count 5 + Amount 8 = 17. The Segment Length is therefore 107, not '108'. '108' is what you get with a Field Separator after every one of the 17 fields and the 2-character Card Label, as in Segment 100.

Spec:
- Section 12.6 Totals Data Segment, p.235 (printed 12-18): *"Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14."*
- Section 12.6 Totals Data Segment, p.235 (printed 12-18): *"Field Nos. 15–17 are not separated by Field Separators."*
- Element 13 Card Label (Chapter 13), p.372 (printed 13-32): *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*
- Element 84 Segment Length (Chapter 13), p.406 (printed 13-66): *"This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."*

### D-21 – Totals Request: the totals do not match the transactions of the flow

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003044 | 3 (totals) | TD-TC-003044.step3.totals.json : 21 | `count 00001, amount 00000000, grand total 00000000` | Leave out the DB bucket and send Grand Total 00000000 |

**Why (TC-003044):** Section 9.2 counts each approved transaction into its card bucket: step 1 purchase $15.00: no response (timed out), so not in the totals; step 2 timeout reversal $15.00: its original was never approved, so nothing to take out. The DB bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.

Spec:
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."*
- Section 9.2 Local Totals, p.72 (printed 9-8): *"The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"*
- Section 12.6 Totals Data Segment, p.237 (printed 12-20): *"Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."*

### D-24 – A test data value differs from the test case it was generated from

| TC | Step | File : line | Value | What it should be |
|---|---|---|---|---|
| TC-003054 | 3 (return) | TD-TC-003054.step3.return.json : 22 | `030542` | '030543' (regenerate the file from the test case) |
| TC-003055 | 3 (return) | TD-TC-003055.step3.return.json : 22 | `030552` | '030553' (regenerate the file from the test case) |

**Why (TC-003054):** The test data file has '030542', but the test case request (the source the file is generated from) has '030543'. The two are out of step, so the file does not send what the test case describes. '030542' is already the Sequence Number of step 2 (totals) on the same terminal, so the terminal would send one Sequence Number twice.

Spec:
- Element 86 Sequence Number (Chapter 13), p.409 (printed 13-69): *"Valid Codes/Values: 000001–999999"*
- Element 86 Sequence Number (Chapter 13), p.409 (printed 13-69): *"Each transaction has a unique Sequence Number."*

### Follow-on steps checked against their original step

#### T6 – Purchase, no response → Time-out Reversal (TC-003036)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030361` (original `030361`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase, late approval → Time-out Reversal (TC-003037)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030371` (original `030371`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase, unreadable response → Time-out Reversal (TC-003038)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030381` (original `030381`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase timed out → TOR sent ahead of next Purchase (TC-003039)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030391` (original `030391`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Time-out Reversal unanswered → retried (max 3) (TC-003040)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030401` (original `030401`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

Step 3 (tor retry) against step 2 (timeout reversal): D-09, D-12 and D-15 apply to step 2; step 3 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030401` (original `030401`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 2 amount | `1500` (step 2 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

Step 4 (tor retry) against step 2 (timeout reversal): D-09, D-12 and D-15 apply to step 2; step 4 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030401` (original `030401`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 2 amount | `1500` (step 2 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T8 – Purchase → Purchase Reversal → Settlement (TC-003042)

Step 2 (reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030421` (original `030421`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T8 – Purchase timed out → TOR → Settlement (TC-003044)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030441` (original `030441`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T10 – Time-out Reversal with no original → declined (unmatched) (TC-003047)

Step 1 (timeout reversal) against step 1 (timeout reversal): D-09, D-12 and D-15 apply to step 1; step 1 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T10 – Purchase → Reversal → Time-out Reversal → declined (TC-003048)

Step 2 (reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030481` (original `030481`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

Step 3 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 3 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030481` (original `030481`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T5 – Purchase → Void declined → Merchandise Return (TC-003050)

Step 2 (reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030501` (original `030501`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T6 – Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase (TC-003051)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030511` (original `030511`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T6 – Purchase late approval → Time-out Reversal → Purchase resubmitted (TC-003053)

Step 2 (timeout reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `Z011` (Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030531` (original `030531`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | `(empty)` | PASS |
| Segment Length: Length of all 17 fields + 17 Field Separators = 058 | `058` | PASS |

#### T5 – Debit Purchase → Purchase Reversal (no PIN) (TC-003059)

Step 2 (reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030591` (original `030591`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
| Amount = step 1 amount | `1500` (step 1 `1500`) | PASS |
| Card Discretionary Block Data: Required with the Account Number (see the card data finding) | `(empty)` | FAIL |
| Partial Approval Indicator: '1' = partial approval supported | `1` | PASS |
| Approval Number (Element 5) is a run-time link to the step 1 response | `<ApprovalNumber from step 1 response>` | BLOCKED |
| Segment Length: Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | `064` | PASS |
| Test case cites p.85 for the Approval Number rule | Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct. | REVIEW |

#### T4 – Debit partial approval → Purchase Reversal of the approved amount (TC-003063)

Step 2 (reversal) against step 1 (purchase): D-09, D-12, D-15 and D-16 apply to step 1; step 2 has D-09, D-12, D-14 and D-15.

| Check | Value | Result |
|---|---|---|
| Physical message `Financial Transaction Request`, Prompt Code `8011` (Transaction Type '8' = Purchase reversal; Card Type '011' = Debit checking) | – | PASS |
| Same Sequence Number as the original | `030631` (original `030631`) | PASS |
| Same Account Number as the original | `` (original ``) | PASS |
| Same terminal as the original | `JX72948216050257001` (original `JX72948216050257001`) | PASS |
| Same POS entry mode as the original | `10` (original `10`) | PASS |
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
| Message / Data Section 1 | Message type `Financial Transaction Request`/`Totals Request`; Message Format Version Identifier `ATL105`; Number of Segments `02`/`01` | – |
| Segment 100 | Segment Type `100`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `0011`/`Z011`/`8011`; Pump/Lane Number `(empty)`; Fuel Purchase Amount `(empty)`; Tax Amount `(empty)`; Cash Amount `(empty)`/`500`; Local Date and Local Time `(empty)`; Partial Approval Indicator `1` | Segment Length (FAIL in TC-003063 step 2); Account Number (FAIL in TC-003035 step 1; FAIL in TC-003036 step 1; FAIL in TC-003036 step 2; FAIL in TC-003037 step 1; FAIL in TC-003037 step 2; FAIL in TC-003038 step 1; FAIL in TC-003038 step 2; FAIL in TC-003039 step 1; FAIL in TC-003039 step 2; FAIL in TC-003039 step 3; FAIL in TC-003040 step 1; FAIL in TC-003040 step 2; FAIL in TC-003040 step 3; FAIL in TC-003040 step 4; FAIL in TC-003041 step 1; FAIL in TC-003042 step 1; FAIL in TC-003042 step 2; FAIL in TC-003043 step 1; FAIL in TC-003043 step 2; FAIL in TC-003044 step 1; FAIL in TC-003044 step 2; FAIL in TC-003045 step 1; FAIL in TC-003046 step 1; FAIL in TC-003047 step 1; FAIL in TC-003048 step 1; FAIL in TC-003048 step 2; FAIL in TC-003048 step 3; FAIL in TC-003049 step 1; FAIL in TC-003049 step 2; FAIL in TC-003050 step 1; FAIL in TC-003050 step 2; FAIL in TC-003050 step 3; FAIL in TC-003051 step 1; FAIL in TC-003051 step 2; FAIL in TC-003051 step 3; FAIL in TC-003052 step 1; FAIL in TC-003052 step 2; FAIL in TC-003053 step 1; FAIL in TC-003053 step 2; FAIL in TC-003053 step 3; FAIL in TC-003054 step 1; FAIL in TC-003054 step 3; FAIL in TC-003055 step 1; FAIL in TC-003055 step 3; FAIL in TC-003056 step 1; FAIL in TC-003059 step 1; FAIL in TC-003059 step 2; FAIL in TC-003060 step 1; FAIL in TC-003061 step 1; FAIL in TC-003062 step 1; FAIL in TC-003063 step 1; FAIL in TC-003063 step 2); Card Discretionary Block Data (FAIL in TC-003035 step 1; FAIL in TC-003036 step 1; FAIL in TC-003036 step 2; FAIL in TC-003037 step 1; FAIL in TC-003037 step 2; FAIL in TC-003038 step 1; FAIL in TC-003038 step 2; FAIL in TC-003039 step 1; FAIL in TC-003039 step 2; FAIL in TC-003039 step 3; FAIL in TC-003040 step 1; FAIL in TC-003040 step 2; FAIL in TC-003040 step 3; FAIL in TC-003040 step 4; FAIL in TC-003041 step 1; FAIL in TC-003042 step 1; FAIL in TC-003042 step 2; FAIL in TC-003043 step 1; FAIL in TC-003043 step 2; FAIL in TC-003044 step 1; FAIL in TC-003044 step 2; FAIL in TC-003045 step 1; FAIL in TC-003046 step 1; FAIL in TC-003047 step 1; FAIL in TC-003048 step 1; FAIL in TC-003048 step 2; FAIL in TC-003048 step 3; FAIL in TC-003049 step 1; FAIL in TC-003049 step 2; FAIL in TC-003050 step 1; FAIL in TC-003050 step 2; FAIL in TC-003050 step 3; FAIL in TC-003051 step 1; FAIL in TC-003051 step 2; FAIL in TC-003051 step 3; FAIL in TC-003052 step 1; FAIL in TC-003052 step 2; FAIL in TC-003053 step 1; FAIL in TC-003053 step 2; FAIL in TC-003053 step 3; FAIL in TC-003054 step 1; FAIL in TC-003054 step 3; FAIL in TC-003055 step 1; FAIL in TC-003055 step 3; FAIL in TC-003056 step 1; FAIL in TC-003059 step 1; FAIL in TC-003059 step 2; FAIL in TC-003060 step 1; FAIL in TC-003061 step 1; FAIL in TC-003062 step 1; FAIL in TC-003063 step 1; FAIL in TC-003063 step 2); Encrypted PIN Block Data (FAIL in TC-003035 step 1; FAIL in TC-003036 step 1; FAIL in TC-003037 step 1; FAIL in TC-003038 step 1; FAIL in TC-003039 step 1; FAIL in TC-003039 step 3; FAIL in TC-003040 step 1; FAIL in TC-003041 step 1; FAIL in TC-003042 step 1; FAIL in TC-003043 step 1; FAIL in TC-003043 step 2; FAIL in TC-003044 step 1; FAIL in TC-003045 step 1; FAIL in TC-003046 step 1; FAIL in TC-003048 step 1; FAIL in TC-003049 step 1; FAIL in TC-003049 step 2; FAIL in TC-003050 step 1; FAIL in TC-003050 step 3; FAIL in TC-003051 step 1; FAIL in TC-003051 step 3; FAIL in TC-003052 step 1; FAIL in TC-003052 step 2; FAIL in TC-003053 step 1; FAIL in TC-003053 step 3; FAIL in TC-003054 step 1; FAIL in TC-003054 step 3; FAIL in TC-003055 step 1; FAIL in TC-003055 step 3; FAIL in TC-003056 step 1; FAIL in TC-003059 step 1; FAIL in TC-003060 step 1; FAIL in TC-003061 step 1; FAIL in TC-003062 step 1; FAIL in TC-003063 step 1); Nonfuel Amount (BLOCKED in TC-003063 step 2); Sequence Number (FAIL in TC-003054 step 3; FAIL in TC-003055 step 3); Approval Number (BLOCKED in TC-003042 step 2; BLOCKED in TC-003048 step 2; BLOCKED in TC-003050 step 2; BLOCKED in TC-003059 step 2; BLOCKED in TC-003063 step 2) |
| Segment 111 | Segment Type `111`; Segment Length `026`; Variable Information Indicator `005`/`031`; Variable Information Length `003`/`002`; Variable Information (Table 005) `101`; Variable Information (Table 031) `12` | – |
| Segment 105 (Totals Request) | Segment Type `105`; Information Byte `0`; Terminal Identifier `JX72948216050257001`; Prompt Code `990`; Employee Number `(empty)`; Password `(empty)`; Totals Date `222222`/`333333`/`444444`; Hardware Version `LAD6MY4G`/`K7JFI7B0`/`35FTNUUX`; Software Version `R2O6EV4A`/`3GUF87DS`/`0AGF5OFH`; Firmware Version `LJJ1DR0I`/`WITGCMTA`/`S695GZ30`; Sequence Number `030412`/`030422`/`030433`; Currency Code `840`; Grand Total `00001500`/`00000000`; Card Type Total Amount `00001500`/`00000000` | Segment Length (FAIL in TC-003041 step 2; FAIL in TC-003042 step 3; FAIL in TC-003043 step 3; FAIL in TC-003044 step 3; FAIL in TC-003045 step 2; FAIL in TC-003046 step 2; FAIL in TC-003054 step 2; FAIL in TC-003055 step 2); Card Label (FAIL in TC-003041 step 2; FAIL in TC-003042 step 3; FAIL in TC-003043 step 3; FAIL in TC-003044 step 3; FAIL in TC-003045 step 2; FAIL in TC-003046 step 2; FAIL in TC-003054 step 2; FAIL in TC-003055 step 2); Card Type Total Count (REVIEW in TC-003042 step 3) |
| Step-to-step links | 76 of 77 checks PASS | Totals match the transactions of the flow (Section 9.2) (FAIL in TC-003044) |
| Expected response codes | 38 of 38 PASS (`0` = Approved - Purchase/Capture, `1` = Declined - all other transactions, `F` = Approved - Partial approval) | – |
| TC-declared `rule_checks` | 285 of 285 hold | – |
| TC-declared `link_defs` | 76 of 76 hold; 11 run-time link(s) (BLOCKED until filled in) | – |
| Generation fidelity (test data value = test case request value) | – | TC-003054: Sequence Number; TC-003055: Sequence Number |
| Spec quotes in the test cases | 123 of 152 found word-for-word on the cited page | p.512: Not word-for-word: all its words are on the cited page, but not in this form |

---

## 6. Open questions / review items (not defects)

| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |
|---|---|---|---|---|---|
| Q-01 | Outdated page cited for the POS Entry Mode table (Table 005) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. | TC-003035, TC-003036, TC-003037, TC-003038, TC-003039, TC-003040, TC-003041, TC-003042, TC-003043, TC-003044, TC-003045, TC-003046, TC-003047, TC-003048, TC-003049, TC-003050, TC-003051, TC-003052, TC-003053, TC-003054, TC-003055, TC-003056, TC-003059, TC-003060, TC-003061, TC-003062, TC-003063 | Appendix I, PDF p.555 (printed I-6) | Test case generator (citation) |
| Q-02 | The quote cited for p.512 is not word-for-word in ATL105 | The test case quotes "48 Interlink - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form. | TC-003035, TC-003036, TC-003037, TC-003038, TC-003039, TC-003040, TC-003041, TC-003042, TC-003043, TC-003044, TC-003045, TC-003046, TC-003047, TC-003048, TC-003049, TC-003050, TC-003051, TC-003052, TC-003053, TC-003054, TC-003055, TC-003056, TC-003057, TC-003058, TC-003059, TC-003060, TC-003061, TC-003062, TC-003063 | – | Test case generator (citation) |
| Q-03 | The Terminal Identifier is a placeholder | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | TC-003035, TC-003036, TC-003037, TC-003038, TC-003039, TC-003040, TC-003041, TC-003042, TC-003043, TC-003044, TC-003045, TC-003046, TC-003047, TC-003048, TC-003049, TC-003050, TC-003051, TC-003052, TC-003053, TC-003054, TC-003055, TC-003056, TC-003059, TC-003060, TC-003061, TC-003062, TC-003063 | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | Client merchant list / BUYPASS |
| Q-04 | Credential on File with a PIN debit card is not described for this network | ATL105 requires PIN entry on every debit transaction except the Purchase Reversal, but a stored-credential transaction has no cardholder at the terminal to enter a PIN. ATL105 describes debit Credential on File transactions (bill payment, recurring, installment) only for Accel and STAR (Element 12). | TC-003035, TC-003036, TC-003037, TC-003038, TC-003039, TC-003040, TC-003041, TC-003042, TC-003043, TC-003044, TC-003045, TC-003046, TC-003047, TC-003048, TC-003049, TC-003050, TC-003051, TC-003052, TC-003053, TC-003054, TC-003055, TC-003056, TC-003059, TC-003060, TC-003061, TC-003062, TC-003063 | Section 10.2.1.3, PDF p.96 (printed 10-17); Element 12, PDF p.372 (printed 13-32) | BUYPASS / SME |
| Q-05 | The device version fields of the Totals Request are generated values | ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device. | TC-003041, TC-003042, TC-003043, TC-003044, TC-003045, TC-003046, TC-003054, TC-003055 | Element 43, PDF p.386 (printed 13-46); Element 96, PDF p.414 (printed 13-74); Element 39, PDF p.384 (printed 13-44) | Client (versions of the certified device) |
| Q-06 | The test case does not state the expected Response Code | The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction. | TC-003041, TC-003042, TC-003043, TC-003044, TC-003045, TC-003046, TC-003050, TC-003054, TC-003055 | Element 83, PDF p.404 (printed 13-64) | Test case owner |
| Q-07 | The totals bucket is sent with a count of 00000 | Element 16 allows 00001-99999, and the category section is sent only when data occurs for a card type. A net count of 00000 suggests the bucket should not be sent at all. | TC-003042 | Element 16, PDF p.375 (printed 13-35); Section 12.6, PDF p.237 (printed 12-20) | BUYPASS / SME |
| Q-08 | The Approval Number rule cites the wrong section | p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S, reversing Authorization Only codes 3 and 5), while this step is a Purchase Reversal (Prompt Code 8020, Transaction Type 8). | TC-003042, TC-003048, TC-003050, TC-003059, TC-003063 | Section 10.1.2.2, PDF p.85 (printed 10-6); Appendix G, PDF p.546 (printed G-2); Element 5, PDF p.368 (printed 13-28) | Test case generator (citation) |
| Q-09 | Same request as TC-003035 | The request is identical to TC-003035 (T1-PUR) except the Sequence Number: same card ..., expiry , amount $15.00 and entry mode 101. BUYPASS cannot tell the two tests apart. | TC-003056, TC-003060, TC-003062 | – | Device-level verification during execution |
| Q-10 | Step 1 needs a partial-approval test card | Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00. | TC-003062, TC-003063 | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 83, PDF p.404 (printed 13-64); Element 121, PDF p.440 (printed 13-100) | Client test card or simulator trigger |
| Q-11 | Variable-cut test merchant. | Listed by the test case itself as an open item (input needed before execution). | TC-003041, TC-003042, TC-003043, TC-003044, TC-003054, TC-003055 | – | Client / test case owner |
| Q-12 | Totals bucket rules are stated only in the FSA/HRA section (p.89). | Listed by the test case itself as an open item (input needed before execution). | TC-003041 | – | Client / test case owner |
| Q-13 | Test-environment cut time; observable only on the next business day. | Listed by the test case itself as an open item (input needed before execution). | TC-003046 | – | Client / test case owner |
| Q-14 | Simulator able to decline a Purchase Reversal. | Listed by the test case itself as an open item (input needed before execution). | TC-003050 | – | Client / test case owner |
| Q-15 | Petroleum spec rule applied to a general credit merchant (SME_QUESTIONS_FLOWS FQ-20). | Listed by the test case itself as an open item (input needed before execution). | TC-003050, TC-003054, TC-003055 | – | Client / test case owner |
| Q-16 | Simulator able to withhold four consecutive responses. | Listed by the test case itself as an open item (input needed before execution). | TC-003051 | – | Client / test case owner |
| Q-17 | The rule applies in an IP environment (p.40): confirm the test terminal connects over IP. | Listed by the test case itself as an open item (input needed before execution). | TC-003051 | – | Client / test case owner |
| Q-18 | The communications test/health message (Transaction Type 900, p.187) is checked on the device; it is not composed. | Listed by the test case itself as an open item (input needed before execution). | TC-003051 | – | Client / test case owner |
| Q-19 | Decline Codes 4I/6C are listed for authorizers AR and DIAL: confirm they apply to Interlink debit (SME_QUESTIONS_FLOWS FQ-19). | Listed by the test case itself as an open item (input needed before execution). | TC-003052 | – | Client / test case owner |
| Q-20 | Simulator able to return Decline Code 4I or 6C. | Listed by the test case itself as an open item (input needed before execution). | TC-003052 | – | Client / test case owner |
| Q-21 | Several partial returns against one purchase are not described (SME_QUESTIONS_FLOWS FQ-04). | Listed by the test case itself as an open item (input needed before execution). | TC-003055 | – | Client / test case owner |
| Q-22 | Decline test card or simulator decline trigger. | Listed by the test case itself as an open item (input needed before execution). | TC-003056 | – | Client / test case owner |
| Q-23 | Decline Codes for Interlink are not listed (p.379 'BUYPASS defines this code'): SME_QUESTIONS_FLOWS FQ-06. | Listed by the test case itself as an open item (input needed before execution). | TC-003056 | – | Client / test case owner |
| Q-24 | Terminal table load with card type 173 and a DL6 window (client). | Listed by the test case itself as an open item (input needed before execution). | TC-003057 | – | Client / test case owner |
| Q-25 | What the device does with a blocked transaction is not stated (SME_QUESTIONS_FLOWS FQ-03). | Listed by the test case itself as an open item (input needed before execution). | TC-003057 | – | Client / test case owner |
| Q-26 | Terminal table load with card type 167 (client). | Listed by the test case itself as an open item (input needed before execution). | TC-003058 | – | Client / test case owner |
| Q-27 | Expired debit test card. | Listed by the test case itself as an open item (input needed before execution). | TC-003060 | – | Client / test case owner |

---

## 7. Recommendations for the AI test data generator

1. **Scenario fidelity:**
   - Credential on File (entry mode 10): add Table 032 sub-table 08 (`C`, or `I` on the transaction that stores the credential) and Table 049 sub-table 02 (Transaction Initiation Indicator) (D-09).
   - Follow-on amount after a partial approval: keep it a run-time value from the original response; any provisional amount must be at most 7 digits and not more than the original, and the Segment Length is computed after substitution (D-14).
2. **Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request (10 digits, values from Appendix Q, for the certified terminal) – Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."* (D-12).
3. **Element 2 Account Number and Element 12 Card Discretionary Block Data:** Fill in the Account Number and Element 12 from the client's test card: the data read from the card for card-read entry modes, Account Number + expiry YYMM on follow-on messages, the expiry for stored credentials. Until the card profile is supplied, write an explicit placeholder (for example `<CLIENT_CARD_PAN>`) and keep the test case blocked – never an empty string. (D-15).
4. **Element 33 Encrypted PIN Block Data:** Every debit request except the Purchase Reversal and the follow-on messages: Element 33 = DUKPT Key Serial Number + PIN block (Section 10.2.1.3). Until the client supplies the test PIN and DUKPT key, write an explicit placeholder and keep the test case blocked. (D-16).
5. **Any field – test data value vs test case request value:** Write the test data file from the test case request it belongs to (same values); regenerate the files after any change to the test case. (D-24).
6. **Element 13 Card Label (Totals Request):** Card Label is a fixed 4-character field, left-aligned and space-filled: `DB` → `DB  ` (two trailing spaces). Recalculate the Segment Length afterwards (D-20). (D-19).
7. **Element 84 Segment Length (Totals Request):** Segment 105 length = Fields 1–14 + 14 Field Separators + the Category Total Data Section, which has no separators (Card Label 4 + Count 5 + Amount 8 = 17) (Section 12.6). Do not count a separator after Fields 15–17 and use the 4-character Card Label (D-19). (D-20).
8. **Elements 16, 15, 42 Card Type Total Count, Card Type Total Amount, Grand Total:** Compute the device totals from the steps before the Totals Request (Section 9.2): approved Purchase/Capture or Completion +1 / +amount; approved Reversal −1 / −amount; Merchandise Return +1 / −amount; Authorization Only into the AO bucket, which the grand total leaves out. A purchase that never got a response and was reversed is not in the totals. A card bucket whose net count is zero is not sent. (D-21).
9. **Expected responses:** replace the open `?` Response Codes with the code each step must return (Q-06).
10. **Citations:** cite the 2026-4 pages and the section that matches the transaction (Q-01, Q-02, Q-08). The data values are not affected.
11. **Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution (Q-03).

---

## 8. Report index

| Scenario | TC | Test | Result | Report |
|---|---|---|---|---|
| BR1_TS6 | TC-003035 | T1 – Purchase approved | FAIL | [TC-003035_ATL105_Field_Validation.md](./TC-003035_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003036 | T6 – Purchase, no response → Time-out Reversal | FAIL | [TC-003036_ATL105_Field_Validation.md](./TC-003036_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003037 | T6 – Purchase, late approval → Time-out Reversal | FAIL | [TC-003037_ATL105_Field_Validation.md](./TC-003037_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003038 | T6 – Purchase, unreadable response → Time-out Reversal | FAIL | [TC-003038_ATL105_Field_Validation.md](./TC-003038_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003039 | T6 – Purchase timed out → TOR sent ahead of next Purchase | FAIL | [TC-003039_ATL105_Field_Validation.md](./TC-003039_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003040 | T6 – Time-out Reversal unanswered → retried (max 3) | FAIL | [TC-003040_ATL105_Field_Validation.md](./TC-003040_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003041 | T8 – Purchase → Settlement (read and cut 222222) | FAIL | [TC-003041_ATL105_Field_Validation.md](./TC-003041_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003042 | T8 – Purchase → Purchase Reversal → Settlement | FAIL | [TC-003042_ATL105_Field_Validation.md](./TC-003042_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003043 | T8 – Purchase + Merchandise Return → Settlement | FAIL | [TC-003043_ATL105_Field_Validation.md](./TC-003043_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003044 | T8 – Purchase timed out → TOR → Settlement | FAIL | [TC-003044_ATL105_Field_Validation.md](./TC-003044_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003045 | T8 – Purchase → Read-only totals (333333) | FAIL | [TC-003045_ATL105_Field_Validation.md](./TC-003045_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003046 | T8 – Purchase → Auto-close at cut time | FAIL | [TC-003046_ATL105_Field_Validation.md](./TC-003046_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003047 | T10 – Time-out Reversal with no original → declined (unmatched) | FAIL | [TC-003047_ATL105_Field_Validation.md](./TC-003047_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003048 | T10 – Purchase → Reversal → Time-out Reversal → declined | FAIL | [TC-003048_ATL105_Field_Validation.md](./TC-003048_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003049 | T5 – Purchase → next Purchase on the same device (new Sequence Number) | FAIL | [TC-003049_ATL105_Field_Validation.md](./TC-003049_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003050 | T5 – Purchase → Void declined → Merchandise Return | FAIL | [TC-003050_ATL105_Field_Validation.md](./TC-003050_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003051 | T6 – Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | FAIL | [TC-003051_ATL105_Field_Validation.md](./TC-003051_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003052 | T6 – Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | FAIL | [TC-003052_ATL105_Field_Validation.md](./TC-003052_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003053 | T6 – Purchase late approval → Time-out Reversal → Purchase resubmitted | FAIL | [TC-003053_ATL105_Field_Validation.md](./TC-003053_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003054 | T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | FAIL | [TC-003054_ATL105_Field_Validation.md](./TC-003054_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003055 | T8 – Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | FAIL | [TC-003055_ATL105_Field_Validation.md](./TC-003055_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003056 | T10 – Purchase declined by the issuer | FAIL | [TC-003056_ATL105_Field_Validation.md](./TC-003056_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003057 | T11 – Store and Forward blocking window (card type 173) → no off-line approval inside the window | REVIEW | [TC-003057_ATL105_Field_Validation.md](./TC-003057_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003058 | T11 – Void/Reversal disabled (card type 167) → not offered at the device | REVIEW | [TC-003058_ATL105_Field_Validation.md](./TC-003058_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003059 | T5 – Debit Purchase → Purchase Reversal (no PIN) | FAIL | [TC-003059_ATL105_Field_Validation.md](./TC-003059_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003060 | T2 – Expired debit card → sent online (no device expiry check) → issuer decides | FAIL | [TC-003060_ATL105_Field_Validation.md](./TC-003060_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003061 | T1 – Debit Purchase with Cashback → approved | FAIL | [TC-003061_ATL105_Field_Validation.md](./TC-003061_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003062 | T4 – Debit Purchase with Cashback partially approved → approved amount covers the purchase only | FAIL | [TC-003062_ATL105_Field_Validation.md](./TC-003062_ATL105_Field_Validation.md) |
| BR1_TS6 | TC-003063 | T4 – Debit partial approval → Purchase Reversal of the approved amount | FAIL | [TC-003063_ATL105_Field_Validation.md](./TC-003063_ATL105_Field_Validation.md) |

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py --data "interlink_debit" --all
```
