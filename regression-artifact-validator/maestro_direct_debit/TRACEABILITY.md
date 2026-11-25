# Traceability matrix — BR1 BUYPASS ATL105 Maestro Direct Debit

Business Requirement → Test Scenario → Test Case → Test Data.

Generated 2026-10-31 10:24 UTC from this folder's own artifacts only (`requirements_scenarios/business_requirements.json`, `business_scenarios.json`, `follow_on_leg_combinations.csv`, `test_cases/test_cases.jsonl`, `test_cases/test_case_validation.json`, `run_set.json`, `test_data/`). Nothing is inferred. Any link that cannot be resolved is listed in [Unresolved links](#unresolved-links).

## Summary

| Item | Value |
|---|---|
| Business Requirement | `BR-8B4D24D4` (BR1) BUYPASS ATL105 Maestro Direct Debit |
| Spec | ATL105 2026-4 (`SRC-ATL105-PDF-001`) |
| Selection | platform=BUYPASS, spec=ATL105, network=MAESTRO_DIRECT, program=DEBIT |
| Dimension mapping | candidate |
| Build | `buypass-atl105-maestro-direct-debit-c63544518a` |
| Scenarios | 28 (valid: 8, unconfirmed: 20) |
| Scenarios with test cases | 21 / 28 |
| Follow-on combinations (steps inside flow test cases) | 28 |
| Test cases | 278 (needs_client_input: 48, requires_review: 26, ready: 6, pending_sme: 198) |
| Test cases with test data | 249 / 278 |
| Test data files | 536 listed in run_set.json, 536 present in `test_data/` |
| Missing test data files | 0 |
| Unresolved links | 0 |

### Test cases by type

| Type | Test cases |
|---|---|
| T1 Base transaction | 28 |
| T2 Card capture path | 11 |
| T4 Partial approval | 14 |
| T5 Follow-on lifecycle | 49 |
| T6 Time-out & recovery | 56 |
| T8 Settlement | 63 |
| T10 Business-rule decline | 28 |
| T11 Device-only | 29 |

## 1. Business Requirement

**BR1 — BUYPASS ATL105 Maestro Direct Debit** (`BR-8B4D24D4`), status `built`.

Spec citations for the requirement:

| Source | Page | Quote |
|---|---|---|
| Appendix C | p.512 | 40 Maestro Direct - Authorizer Type: Debit |
| anchored:A-SALE-PREAUTH-TRACK | p.81 | If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Authorization Only |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Balance Inquiry |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Merchandise Return |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture |
| 10.14.2.3 Supported Cards | p.161 | The following debit cards are supported for EMV processing: ... Maestro |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Purchase |
| anchored:APPJ-FN2-COF-DEBIT-FOLLOWON | p.638 | also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction |

## 2. Requirement → Scenarios

| Scenario | Name | Status | Test cases | Test data files | Spec basis |
|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contact Chip | valid | 29 | 60 | p.512, p.97, p.161 |
| BR1_TS2 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contactless Chip | valid | 30 | 61 | p.512, p.97, p.161 |
| BR1_TS3 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Magnetic Stripe | unconfirmed | 30 | 60 | p.512, p.97, p.81 |
| BR1_TS4 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Magnetic Stripe | unconfirmed | 29 | 60 | p.512, p.97 |
| BR1_TS5 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Chip Fallback | unconfirmed | 30 | 61 | p.512, p.97 |
| BR1_TS6 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Credential on File | unconfirmed | 29 | 60 | p.512, p.97, p.638 |
| BR1_TS7 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | 29 | 60 | p.512, p.97 |
| BR1_TS8 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contact Chip | valid | 7 | 13 | p.512, p.97, p.161 |
| BR1_TS9 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contactless Chip | valid | 8 | 14 | p.512, p.97, p.161 |
| BR1_TS10 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Magnetic Stripe | unconfirmed | 7 | 13 | p.512, p.97, p.81 |
| BR1_TS11 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Magnetic Stripe | unconfirmed | 7 | 13 | p.512, p.97 |
| BR1_TS12 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Chip Fallback | unconfirmed | 8 | 14 | p.512, p.97 |
| BR1_TS13 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Credential on File | unconfirmed | 7 | 13 | p.512, p.97, p.638 |
| BR1_TS14 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | 7 | 13 | p.512, p.97 |
| BR1_TS15 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contact Chip | valid | 3 | 3 | p.512, p.97, p.161 |
| BR1_TS16 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contactless Chip | valid | 3 | 3 | p.512, p.97, p.161 |
| BR1_TS17 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Magnetic Stripe | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS18 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Magnetic Stripe | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS19 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Chip Fallback | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS20 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Credential on File | unconfirmed | 3 | 3 | p.512, p.97, p.638 |
| BR1_TS21 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS22 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contact Chip | valid | **0** | 0 | p.512, p.97, p.161 |
| BR1_TS23 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contactless Chip | valid | **0** | 0 | p.512, p.97, p.161 |
| BR1_TS24 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Magnetic Stripe | unconfirmed | **0** | 0 | p.512, p.97 |
| BR1_TS25 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Magnetic Stripe | unconfirmed | **0** | 0 | p.512, p.97 |
| BR1_TS26 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Chip Fallback | unconfirmed | **0** | 0 | p.512, p.97 |
| BR1_TS27 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Credential on File | unconfirmed | **0** | 0 | p.512, p.97, p.638 |
| BR1_TS28 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | **0** | 0 | p.512, p.97 |

## 3. Scenario → Test Case → Test Data

Steps show each step's transaction category and its Appendix G code (or special Prompt Code). Expected = the test case's final response-code expectation (tier, value, spec page). Per-step expectations, rule checks and runtime links are in `test_cases/test_cases.jsonl`.

### BR1_TS1 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-003165 | T1 | T1-PUR | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003165.json` | p.545, p.512, p.97, p.161 |
| BR1_TS1_TC2 | TC-003191 | T1 | T1-DEBIT-CASHBACK | needs_client_input | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003191.json` | p.97, p.375, p.512, p.161 |
| BR1_TS1_TC3 | TC-003190 | T2 | T2-DEBIT-EXPIRED-ONLINE | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003190.json` | p.96, p.512, p.97, p.161 |
| BR1_TS1_TC4 | TC-003192 | T4 | T4-DEBIT-CASHBACK-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003192.json` | p.97, p.98, p.512, p.161 |
| BR1_TS1_TC5 | TC-003193 | T4 | T4-DEBIT-PARTIAL-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003193.step1.purchase.json`<br>`TD-TC-003193.step2.reversal.json` | p.97, p.98, p.512, p.161 |
| BR1_TS1_TC6 | TC-003179 | T5 | T5-PUR-NEXT-PUR | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003179.step1.purchase.json`<br>`TD-TC-003179.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.161 |
| BR1_TS1_TC7 | TC-003180 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003180.step1.purchase.json`<br>`TD-TC-003180.step2.reversal.json`<br>`TD-TC-003180.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.161 |
| BR1_TS1_TC8 | TC-003189 | T5 | T5-DEBIT-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003189.step1.purchase.json`<br>`TD-TC-003189.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.161 |
| BR1_TS1_TC9 | TC-003166 | T6 | T6-PUR-NO-RESPONSE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003166.step1.purchase.json`<br>`TD-TC-003166.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC10 | TC-003167 | T6 | T6-PUR-LATE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003167.step1.purchase.json`<br>`TD-TC-003167.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC11 | TC-003168 | T6 | T6-PUR-UNREADABLE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003168.step1.purchase.json`<br>`TD-TC-003168.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.161 |
| BR1_TS1_TC12 | TC-003169 | T6 | T6-PUR-TOR-NEXT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003169.step1.purchase.json`<br>`TD-TC-003169.step2.timeout-reversal.json`<br>`TD-TC-003169.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC13 | TC-003170 | T6 | T6-PUR-TOR-RETRY | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003170.step1.purchase.json`<br>`TD-TC-003170.step2.timeout-reversal.json`<br>`TD-TC-003170.step3.tor-retry.json`<br>`TD-TC-003170.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.161 |
| BR1_TS1_TC14 | TC-003181 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003181.step1.purchase.json`<br>`TD-TC-003181.step2.timeout-reversal.json`<br>`TD-TC-003181.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.161 |
| BR1_TS1_TC15 | TC-003182 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003182.step1.purchase.json`<br>`TD-TC-003182.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC16 | TC-003183 | T6 | T6-PUR-LATE-RESUBMIT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003183.step1.purchase.json`<br>`TD-TC-003183.step2.timeout-reversal.json`<br>`TD-TC-003183.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC17 | TC-003171 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003171.step1.purchase.json`<br>`TD-TC-003171.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS1_TC18 | TC-003172 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003172.step1.purchase.json`<br>`TD-TC-003172.step2.reversal.json`<br>`TD-TC-003172.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS1_TC19 | TC-003173 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003173.step1.purchase.json`<br>`TD-TC-003173.step2.return.json`<br>`TD-TC-003173.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS1_TC20 | TC-003174 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003174.step1.purchase.json`<br>`TD-TC-003174.step2.timeout-reversal.json`<br>`TD-TC-003174.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC21 | TC-003175 | T8 | T8-PUR-READONLY | needs_client_input | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003175.step1.purchase.json`<br>`TD-TC-003175.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS1_TC22 | TC-003176 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003176.step1.purchase.json`<br>`TD-TC-003176.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS1_TC23 | TC-003184 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003184.step1.purchase.json`<br>`TD-TC-003184.step2.totals.json`<br>`TD-TC-003184.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS1_TC24 | TC-003185 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003185.step1.purchase.json`<br>`TD-TC-003185.step2.totals.json`<br>`TD-TC-003185.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS1_TC25 | TC-003177 | T10 | T10-TOR-UNMATCHED | needs_client_input | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003177.json` | p.40, p.512, p.97, p.161 |
| BR1_TS1_TC26 | TC-003178 | T10 | T10-TOR-AFTER-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003178.step1.purchase.json`<br>`TD-TC-003178.step2.reversal.json`<br>`TD-TC-003178.step3.timeout-reversal.json` | p.40, p.512, p.97, p.161 |
| BR1_TS1_TC27 | TC-003186 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003186.json` | p.404, p.512, p.97, p.161 |
| BR1_TS1_TC28 | TC-003187 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.161 |
| BR1_TS1_TC29 | TC-003188 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS2 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-003194 | T1 | T1-PUR | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003194.json` | p.545, p.512, p.97, p.161 |
| BR1_TS2_TC2 | TC-003221 | T1 | T1-DEBIT-CASHBACK | needs_client_input | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003221.json` | p.97, p.375, p.512, p.161 |
| BR1_TS2_TC3 | TC-003195 | T2 | T2-CL-TO-CONTACT | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003195.json` | p.638, p.512, p.97, p.161 |
| BR1_TS2_TC4 | TC-003220 | T2 | T2-DEBIT-EXPIRED-ONLINE | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003220.json` | p.96, p.512, p.97, p.161 |
| BR1_TS2_TC5 | TC-003222 | T4 | T4-DEBIT-CASHBACK-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003222.json` | p.97, p.98, p.512, p.161 |
| BR1_TS2_TC6 | TC-003223 | T4 | T4-DEBIT-PARTIAL-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003223.step1.purchase.json`<br>`TD-TC-003223.step2.reversal.json` | p.97, p.98, p.512, p.161 |
| BR1_TS2_TC7 | TC-003209 | T5 | T5-PUR-NEXT-PUR | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003209.step1.purchase.json`<br>`TD-TC-003209.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.161 |
| BR1_TS2_TC8 | TC-003210 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003210.step1.purchase.json`<br>`TD-TC-003210.step2.reversal.json`<br>`TD-TC-003210.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.161 |
| BR1_TS2_TC9 | TC-003219 | T5 | T5-DEBIT-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003219.step1.purchase.json`<br>`TD-TC-003219.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.161 |
| BR1_TS2_TC10 | TC-003196 | T6 | T6-PUR-NO-RESPONSE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003196.step1.purchase.json`<br>`TD-TC-003196.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC11 | TC-003197 | T6 | T6-PUR-LATE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003197.step1.purchase.json`<br>`TD-TC-003197.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC12 | TC-003198 | T6 | T6-PUR-UNREADABLE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003198.step1.purchase.json`<br>`TD-TC-003198.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.161 |
| BR1_TS2_TC13 | TC-003199 | T6 | T6-PUR-TOR-NEXT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003199.step1.purchase.json`<br>`TD-TC-003199.step2.timeout-reversal.json`<br>`TD-TC-003199.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC14 | TC-003200 | T6 | T6-PUR-TOR-RETRY | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003200.step1.purchase.json`<br>`TD-TC-003200.step2.timeout-reversal.json`<br>`TD-TC-003200.step3.tor-retry.json`<br>`TD-TC-003200.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.161 |
| BR1_TS2_TC15 | TC-003211 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003211.step1.purchase.json`<br>`TD-TC-003211.step2.timeout-reversal.json`<br>`TD-TC-003211.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.161 |
| BR1_TS2_TC16 | TC-003212 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003212.step1.purchase.json`<br>`TD-TC-003212.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC17 | TC-003213 | T6 | T6-PUR-LATE-RESUBMIT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003213.step1.purchase.json`<br>`TD-TC-003213.step2.timeout-reversal.json`<br>`TD-TC-003213.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC18 | TC-003201 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003201.step1.purchase.json`<br>`TD-TC-003201.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS2_TC19 | TC-003202 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003202.step1.purchase.json`<br>`TD-TC-003202.step2.reversal.json`<br>`TD-TC-003202.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS2_TC20 | TC-003203 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003203.step1.purchase.json`<br>`TD-TC-003203.step2.return.json`<br>`TD-TC-003203.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS2_TC21 | TC-003204 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003204.step1.purchase.json`<br>`TD-TC-003204.step2.timeout-reversal.json`<br>`TD-TC-003204.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC22 | TC-003205 | T8 | T8-PUR-READONLY | needs_client_input | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003205.step1.purchase.json`<br>`TD-TC-003205.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS2_TC23 | TC-003206 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003206.step1.purchase.json`<br>`TD-TC-003206.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS2_TC24 | TC-003214 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003214.step1.purchase.json`<br>`TD-TC-003214.step2.totals.json`<br>`TD-TC-003214.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS2_TC25 | TC-003215 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003215.step1.purchase.json`<br>`TD-TC-003215.step2.totals.json`<br>`TD-TC-003215.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS2_TC26 | TC-003207 | T10 | T10-TOR-UNMATCHED | needs_client_input | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003207.json` | p.40, p.512, p.97, p.161 |
| BR1_TS2_TC27 | TC-003208 | T10 | T10-TOR-AFTER-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003208.step1.purchase.json`<br>`TD-TC-003208.step2.reversal.json`<br>`TD-TC-003208.step3.timeout-reversal.json` | p.40, p.512, p.97, p.161 |
| BR1_TS2_TC28 | TC-003216 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003216.json` | p.404, p.512, p.97, p.161 |
| BR1_TS2_TC29 | TC-003217 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.161 |
| BR1_TS2_TC30 | TC-003218 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS3 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-003224 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003224.json` | p.545, p.512, p.97, p.81 |
| BR1_TS3_TC2 | TC-003250 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003250.json` | p.97, p.375, p.512, p.81 |
| BR1_TS3_TC3 | TC-003249 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003249.json` | p.96, p.512, p.97, p.81 |
| BR1_TS3_TC4 | TC-003251 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003251.json` | p.97, p.98, p.512, p.81 |
| BR1_TS3_TC5 | TC-003252 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003252.step1.purchase.json`<br>`TD-TC-003252.step2.reversal.json` | p.97, p.98, p.512, p.81 |
| BR1_TS3_TC6 | TC-003238 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003238.step1.purchase.json`<br>`TD-TC-003238.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.81 |
| BR1_TS3_TC7 | TC-003239 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003239.step1.purchase.json`<br>`TD-TC-003239.step2.reversal.json`<br>`TD-TC-003239.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.81 |
| BR1_TS3_TC8 | TC-003248 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003248.step1.purchase.json`<br>`TD-TC-003248.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.81 |
| BR1_TS3_TC9 | TC-003225 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003225.step1.purchase.json`<br>`TD-TC-003225.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC10 | TC-003226 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003226.step1.purchase.json`<br>`TD-TC-003226.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC11 | TC-003227 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003227.step1.purchase.json`<br>`TD-TC-003227.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.81 |
| BR1_TS3_TC12 | TC-003228 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003228.step1.purchase.json`<br>`TD-TC-003228.step2.timeout-reversal.json`<br>`TD-TC-003228.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC13 | TC-003229 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003229.step1.purchase.json`<br>`TD-TC-003229.step2.timeout-reversal.json`<br>`TD-TC-003229.step3.tor-retry.json`<br>`TD-TC-003229.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.81 |
| BR1_TS3_TC14 | TC-003240 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003240.step1.purchase.json`<br>`TD-TC-003240.step2.timeout-reversal.json`<br>`TD-TC-003240.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.81 |
| BR1_TS3_TC15 | TC-003241 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003241.step1.purchase.json`<br>`TD-TC-003241.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC16 | TC-003242 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003242.step1.purchase.json`<br>`TD-TC-003242.step2.timeout-reversal.json`<br>`TD-TC-003242.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC17 | TC-003230 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003230.step1.purchase.json`<br>`TD-TC-003230.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.81 |
| BR1_TS3_TC18 | TC-003231 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003231.step1.purchase.json`<br>`TD-TC-003231.step2.reversal.json`<br>`TD-TC-003231.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.81 |
| BR1_TS3_TC19 | TC-003232 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003232.step1.purchase.json`<br>`TD-TC-003232.step2.return.json`<br>`TD-TC-003232.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.81 |
| BR1_TS3_TC20 | TC-003233 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003233.step1.purchase.json`<br>`TD-TC-003233.step2.timeout-reversal.json`<br>`TD-TC-003233.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC21 | TC-003234 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003234.step1.purchase.json`<br>`TD-TC-003234.step2.totals.json` | p.79, p.512, p.97, p.81 |
| BR1_TS3_TC22 | TC-003235 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003235.step1.purchase.json`<br>`TD-TC-003235.step2.totals.json` | p.79, p.512, p.97, p.81 |
| BR1_TS3_TC23 | TC-003243 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003243.step1.purchase.json`<br>`TD-TC-003243.step2.totals.json`<br>`TD-TC-003243.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.81 |
| BR1_TS3_TC24 | TC-003244 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003244.step1.purchase.json`<br>`TD-TC-003244.step2.totals.json`<br>`TD-TC-003244.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.81 |
| BR1_TS3_TC25 | TC-003236 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003236.json` | p.40, p.512, p.97, p.81 |
| BR1_TS3_TC26 | TC-003237 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003237.step1.purchase.json`<br>`TD-TC-003237.step2.reversal.json`<br>`TD-TC-003237.step3.timeout-reversal.json` | p.40, p.512, p.97, p.81 |
| BR1_TS3_TC27 | TC-003245 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003245.json` | p.404, p.512, p.97, p.81 |
| BR1_TS3_TC28 | TC-003246 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.81 |
| BR1_TS3_TC29 | TC-003247 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.81 |
| BR1_TS3_TC30 | TC-003253 | T11 | T11-DEBIT-NO-MANUAL | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.96, p.512, p.97, p.81 |

### BR1_TS4 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-003254 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003254.json` | p.545, p.512, p.97 |
| BR1_TS4_TC2 | TC-003280 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003280.json` | p.97, p.375, p.512 |
| BR1_TS4_TC3 | TC-003279 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003279.json` | p.96, p.512, p.97 |
| BR1_TS4_TC4 | TC-003281 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003281.json` | p.97, p.98, p.512 |
| BR1_TS4_TC5 | TC-003282 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003282.step1.purchase.json`<br>`TD-TC-003282.step2.reversal.json` | p.97, p.98, p.512 |
| BR1_TS4_TC6 | TC-003268 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003268.step1.purchase.json`<br>`TD-TC-003268.step2.next-transaction.json` | p.40, p.409, p.512, p.97 |
| BR1_TS4_TC7 | TC-003269 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003269.step1.purchase.json`<br>`TD-TC-003269.step2.reversal.json`<br>`TD-TC-003269.step3.return.json` | PIPS p.30, p.546, p.512, p.97 |
| BR1_TS4_TC8 | TC-003278 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003278.step1.purchase.json`<br>`TD-TC-003278.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97 |
| BR1_TS4_TC9 | TC-003255 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003255.step1.purchase.json`<br>`TD-TC-003255.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS4_TC10 | TC-003256 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003256.step1.purchase.json`<br>`TD-TC-003256.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS4_TC11 | TC-003257 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003257.step1.purchase.json`<br>`TD-TC-003257.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97 |
| BR1_TS4_TC12 | TC-003258 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003258.step1.purchase.json`<br>`TD-TC-003258.step2.timeout-reversal.json`<br>`TD-TC-003258.step3.next-transaction.json` | p.39, p.40, p.512, p.97 |
| BR1_TS4_TC13 | TC-003259 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003259.step1.purchase.json`<br>`TD-TC-003259.step2.timeout-reversal.json`<br>`TD-TC-003259.step3.tor-retry.json`<br>`TD-TC-003259.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97 |
| BR1_TS4_TC14 | TC-003270 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003270.step1.purchase.json`<br>`TD-TC-003270.step2.timeout-reversal.json`<br>`TD-TC-003270.step3.next-transaction.json` | p.40, p.187, p.512, p.97 |
| BR1_TS4_TC15 | TC-003271 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003271.step1.purchase.json`<br>`TD-TC-003271.step2.resubmitted.json` | p.549, p.40, p.512, p.97 |
| BR1_TS4_TC16 | TC-003272 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003272.step1.purchase.json`<br>`TD-TC-003272.step2.timeout-reversal.json`<br>`TD-TC-003272.step3.resubmitted.json` | p.43, p.40, p.512, p.97 |
| BR1_TS4_TC17 | TC-003260 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003260.step1.purchase.json`<br>`TD-TC-003260.step2.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS4_TC18 | TC-003261 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003261.step1.purchase.json`<br>`TD-TC-003261.step2.reversal.json`<br>`TD-TC-003261.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS4_TC19 | TC-003262 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003262.step1.purchase.json`<br>`TD-TC-003262.step2.return.json`<br>`TD-TC-003262.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS4_TC20 | TC-003263 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003263.step1.purchase.json`<br>`TD-TC-003263.step2.timeout-reversal.json`<br>`TD-TC-003263.step3.totals.json` | p.78, p.79, p.40, p.512, p.97 |
| BR1_TS4_TC21 | TC-003264 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003264.step1.purchase.json`<br>`TD-TC-003264.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS4_TC22 | TC-003265 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003265.step1.purchase.json`<br>`TD-TC-003265.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS4_TC23 | TC-003273 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003273.step1.purchase.json`<br>`TD-TC-003273.step2.totals.json`<br>`TD-TC-003273.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS4_TC24 | TC-003274 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003274.step1.purchase.json`<br>`TD-TC-003274.step2.totals.json`<br>`TD-TC-003274.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS4_TC25 | TC-003266 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003266.json` | p.40, p.512, p.97 |
| BR1_TS4_TC26 | TC-003267 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003267.step1.purchase.json`<br>`TD-TC-003267.step2.reversal.json`<br>`TD-TC-003267.step3.timeout-reversal.json` | p.40, p.512, p.97 |
| BR1_TS4_TC27 | TC-003275 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003275.json` | p.404, p.512, p.97 |
| BR1_TS4_TC28 | TC-003276 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97 |
| BR1_TS4_TC29 | TC-003277 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS5 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Chip Fallback

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-003283 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003283.json` | p.545, p.512, p.97 |
| BR1_TS5_TC2 | TC-003310 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003310.json` | p.97, p.375, p.512 |
| BR1_TS5_TC3 | TC-003297 | T2 | T2-CHIP-SWIPED | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003297.json` | p.162, p.638, p.512, p.97 |
| BR1_TS5_TC4 | TC-003309 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003309.json` | p.96, p.512, p.97 |
| BR1_TS5_TC5 | TC-003311 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003311.json` | p.97, p.98, p.512 |
| BR1_TS5_TC6 | TC-003312 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003312.step1.purchase.json`<br>`TD-TC-003312.step2.reversal.json` | p.97, p.98, p.512 |
| BR1_TS5_TC7 | TC-003298 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003298.step1.purchase.json`<br>`TD-TC-003298.step2.next-transaction.json` | p.40, p.409, p.512, p.97 |
| BR1_TS5_TC8 | TC-003299 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003299.step1.purchase.json`<br>`TD-TC-003299.step2.reversal.json`<br>`TD-TC-003299.step3.return.json` | PIPS p.30, p.546, p.512, p.97 |
| BR1_TS5_TC9 | TC-003308 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003308.step1.purchase.json`<br>`TD-TC-003308.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97 |
| BR1_TS5_TC10 | TC-003284 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003284.step1.purchase.json`<br>`TD-TC-003284.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS5_TC11 | TC-003285 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003285.step1.purchase.json`<br>`TD-TC-003285.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS5_TC12 | TC-003286 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003286.step1.purchase.json`<br>`TD-TC-003286.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97 |
| BR1_TS5_TC13 | TC-003287 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003287.step1.purchase.json`<br>`TD-TC-003287.step2.timeout-reversal.json`<br>`TD-TC-003287.step3.next-transaction.json` | p.39, p.40, p.512, p.97 |
| BR1_TS5_TC14 | TC-003288 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003288.step1.purchase.json`<br>`TD-TC-003288.step2.timeout-reversal.json`<br>`TD-TC-003288.step3.tor-retry.json`<br>`TD-TC-003288.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97 |
| BR1_TS5_TC15 | TC-003300 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003300.step1.purchase.json`<br>`TD-TC-003300.step2.timeout-reversal.json`<br>`TD-TC-003300.step3.next-transaction.json` | p.40, p.187, p.512, p.97 |
| BR1_TS5_TC16 | TC-003301 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003301.step1.purchase.json`<br>`TD-TC-003301.step2.resubmitted.json` | p.549, p.40, p.512, p.97 |
| BR1_TS5_TC17 | TC-003302 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003302.step1.purchase.json`<br>`TD-TC-003302.step2.timeout-reversal.json`<br>`TD-TC-003302.step3.resubmitted.json` | p.43, p.40, p.512, p.97 |
| BR1_TS5_TC18 | TC-003289 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003289.step1.purchase.json`<br>`TD-TC-003289.step2.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS5_TC19 | TC-003290 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003290.step1.purchase.json`<br>`TD-TC-003290.step2.reversal.json`<br>`TD-TC-003290.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS5_TC20 | TC-003291 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003291.step1.purchase.json`<br>`TD-TC-003291.step2.return.json`<br>`TD-TC-003291.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS5_TC21 | TC-003292 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003292.step1.purchase.json`<br>`TD-TC-003292.step2.timeout-reversal.json`<br>`TD-TC-003292.step3.totals.json` | p.78, p.79, p.40, p.512, p.97 |
| BR1_TS5_TC22 | TC-003293 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003293.step1.purchase.json`<br>`TD-TC-003293.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS5_TC23 | TC-003294 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003294.step1.purchase.json`<br>`TD-TC-003294.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS5_TC24 | TC-003303 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003303.step1.purchase.json`<br>`TD-TC-003303.step2.totals.json`<br>`TD-TC-003303.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS5_TC25 | TC-003304 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003304.step1.purchase.json`<br>`TD-TC-003304.step2.totals.json`<br>`TD-TC-003304.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS5_TC26 | TC-003295 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003295.json` | p.40, p.512, p.97 |
| BR1_TS5_TC27 | TC-003296 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003296.step1.purchase.json`<br>`TD-TC-003296.step2.reversal.json`<br>`TD-TC-003296.step3.timeout-reversal.json` | p.40, p.512, p.97 |
| BR1_TS5_TC28 | TC-003305 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003305.json` | p.404, p.512, p.97 |
| BR1_TS5_TC29 | TC-003306 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97 |
| BR1_TS5_TC30 | TC-003307 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS6 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Credential on File

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-003313 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003313.json` | p.545, p.512, p.97, p.638 |
| BR1_TS6_TC2 | TC-003339 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003339.json` | p.97, p.375, p.512, p.638 |
| BR1_TS6_TC3 | TC-003338 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003338.json` | p.96, p.512, p.97, p.638 |
| BR1_TS6_TC4 | TC-003340 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003340.json` | p.97, p.98, p.512, p.638 |
| BR1_TS6_TC5 | TC-003341 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003341.step1.purchase.json`<br>`TD-TC-003341.step2.reversal.json` | p.97, p.98, p.512, p.638 |
| BR1_TS6_TC6 | TC-003327 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003327.step1.purchase.json`<br>`TD-TC-003327.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.638 |
| BR1_TS6_TC7 | TC-003328 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003328.step1.purchase.json`<br>`TD-TC-003328.step2.reversal.json`<br>`TD-TC-003328.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.638 |
| BR1_TS6_TC8 | TC-003337 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003337.step1.purchase.json`<br>`TD-TC-003337.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.638 |
| BR1_TS6_TC9 | TC-003314 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003314.step1.purchase.json`<br>`TD-TC-003314.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC10 | TC-003315 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003315.step1.purchase.json`<br>`TD-TC-003315.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC11 | TC-003316 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003316.step1.purchase.json`<br>`TD-TC-003316.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.638 |
| BR1_TS6_TC12 | TC-003317 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003317.step1.purchase.json`<br>`TD-TC-003317.step2.timeout-reversal.json`<br>`TD-TC-003317.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC13 | TC-003318 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003318.step1.purchase.json`<br>`TD-TC-003318.step2.timeout-reversal.json`<br>`TD-TC-003318.step3.tor-retry.json`<br>`TD-TC-003318.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.638 |
| BR1_TS6_TC14 | TC-003329 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003329.step1.purchase.json`<br>`TD-TC-003329.step2.timeout-reversal.json`<br>`TD-TC-003329.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.638 |
| BR1_TS6_TC15 | TC-003330 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003330.step1.purchase.json`<br>`TD-TC-003330.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC16 | TC-003331 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003331.step1.purchase.json`<br>`TD-TC-003331.step2.timeout-reversal.json`<br>`TD-TC-003331.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC17 | TC-003319 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003319.step1.purchase.json`<br>`TD-TC-003319.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.638 |
| BR1_TS6_TC18 | TC-003320 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003320.step1.purchase.json`<br>`TD-TC-003320.step2.reversal.json`<br>`TD-TC-003320.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.638 |
| BR1_TS6_TC19 | TC-003321 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003321.step1.purchase.json`<br>`TD-TC-003321.step2.return.json`<br>`TD-TC-003321.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.638 |
| BR1_TS6_TC20 | TC-003322 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003322.step1.purchase.json`<br>`TD-TC-003322.step2.timeout-reversal.json`<br>`TD-TC-003322.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC21 | TC-003323 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003323.step1.purchase.json`<br>`TD-TC-003323.step2.totals.json` | p.79, p.512, p.97, p.638 |
| BR1_TS6_TC22 | TC-003324 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003324.step1.purchase.json`<br>`TD-TC-003324.step2.totals.json` | p.79, p.512, p.97, p.638 |
| BR1_TS6_TC23 | TC-003332 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003332.step1.purchase.json`<br>`TD-TC-003332.step2.totals.json`<br>`TD-TC-003332.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.638 |
| BR1_TS6_TC24 | TC-003333 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003333.step1.purchase.json`<br>`TD-TC-003333.step2.totals.json`<br>`TD-TC-003333.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.638 |
| BR1_TS6_TC25 | TC-003325 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003325.json` | p.40, p.512, p.97, p.638 |
| BR1_TS6_TC26 | TC-003326 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003326.step1.purchase.json`<br>`TD-TC-003326.step2.reversal.json`<br>`TD-TC-003326.step3.timeout-reversal.json` | p.40, p.512, p.97, p.638 |
| BR1_TS6_TC27 | TC-003334 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003334.json` | p.404, p.512, p.97, p.638 |
| BR1_TS6_TC28 | TC-003335 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.638 |
| BR1_TS6_TC29 | TC-003336 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.638 |

### BR1_TS7 — BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-PURCHASE-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-003342 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003342.json` | p.545, p.512, p.97 |
| BR1_TS7_TC2 | TC-003368 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003368.json` | p.97, p.375, p.512 |
| BR1_TS7_TC3 | TC-003367 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003367.json` | p.96, p.512, p.97 |
| BR1_TS7_TC4 | TC-003369 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003369.json` | p.97, p.98, p.512 |
| BR1_TS7_TC5 | TC-003370 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003370.step1.purchase.json`<br>`TD-TC-003370.step2.reversal.json` | p.97, p.98, p.512 |
| BR1_TS7_TC6 | TC-003356 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003356.step1.purchase.json`<br>`TD-TC-003356.step2.next-transaction.json` | p.40, p.409, p.512, p.97 |
| BR1_TS7_TC7 | TC-003357 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003357.step1.purchase.json`<br>`TD-TC-003357.step2.reversal.json`<br>`TD-TC-003357.step3.return.json` | PIPS p.30, p.546, p.512, p.97 |
| BR1_TS7_TC8 | TC-003366 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003366.step1.purchase.json`<br>`TD-TC-003366.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97 |
| BR1_TS7_TC9 | TC-003343 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003343.step1.purchase.json`<br>`TD-TC-003343.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS7_TC10 | TC-003344 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003344.step1.purchase.json`<br>`TD-TC-003344.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS7_TC11 | TC-003345 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003345.step1.purchase.json`<br>`TD-TC-003345.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97 |
| BR1_TS7_TC12 | TC-003346 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003346.step1.purchase.json`<br>`TD-TC-003346.step2.timeout-reversal.json`<br>`TD-TC-003346.step3.next-transaction.json` | p.39, p.40, p.512, p.97 |
| BR1_TS7_TC13 | TC-003347 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003347.step1.purchase.json`<br>`TD-TC-003347.step2.timeout-reversal.json`<br>`TD-TC-003347.step3.tor-retry.json`<br>`TD-TC-003347.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97 |
| BR1_TS7_TC14 | TC-003358 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003358.step1.purchase.json`<br>`TD-TC-003358.step2.timeout-reversal.json`<br>`TD-TC-003358.step3.next-transaction.json` | p.40, p.187, p.512, p.97 |
| BR1_TS7_TC15 | TC-003359 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003359.step1.purchase.json`<br>`TD-TC-003359.step2.resubmitted.json` | p.549, p.40, p.512, p.97 |
| BR1_TS7_TC16 | TC-003360 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003360.step1.purchase.json`<br>`TD-TC-003360.step2.timeout-reversal.json`<br>`TD-TC-003360.step3.resubmitted.json` | p.43, p.40, p.512, p.97 |
| BR1_TS7_TC17 | TC-003348 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003348.step1.purchase.json`<br>`TD-TC-003348.step2.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS7_TC18 | TC-003349 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003349.step1.purchase.json`<br>`TD-TC-003349.step2.reversal.json`<br>`TD-TC-003349.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS7_TC19 | TC-003350 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003350.step1.purchase.json`<br>`TD-TC-003350.step2.return.json`<br>`TD-TC-003350.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS7_TC20 | TC-003351 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003351.step1.purchase.json`<br>`TD-TC-003351.step2.timeout-reversal.json`<br>`TD-TC-003351.step3.totals.json` | p.78, p.79, p.40, p.512, p.97 |
| BR1_TS7_TC21 | TC-003352 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003352.step1.purchase.json`<br>`TD-TC-003352.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS7_TC22 | TC-003353 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003353.step1.purchase.json`<br>`TD-TC-003353.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS7_TC23 | TC-003361 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003361.step1.purchase.json`<br>`TD-TC-003361.step2.totals.json`<br>`TD-TC-003361.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS7_TC24 | TC-003362 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003362.step1.purchase.json`<br>`TD-TC-003362.step2.totals.json`<br>`TD-TC-003362.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS7_TC25 | TC-003354 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003354.json` | p.40, p.512, p.97 |
| BR1_TS7_TC26 | TC-003355 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003355.step1.purchase.json`<br>`TD-TC-003355.step2.reversal.json`<br>`TD-TC-003355.step3.timeout-reversal.json` | p.40, p.512, p.97 |
| BR1_TS7_TC27 | TC-003363 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003363.json` | p.404, p.512, p.97 |
| BR1_TS7_TC28 | TC-003364 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97 |
| BR1_TS7_TC29 | TC-003365 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS8 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-003371 | T1 | T1-AUT | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003371.json` | p.545, p.512, p.97, p.161 |
| BR1_TS8_TC2 | TC-003372 | T5 | T5-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003372.step1.auth-only.json`<br>`TD-TC-003372.step2.completion.json` | p.545, p.512, p.97, p.161 |
| BR1_TS8_TC3 | TC-003376 | T5 | T5-DEBIT-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003376.step1.auth-only.json`<br>`TD-TC-003376.step2.cancellation.json` | p.99, p.512, p.97, p.161 |
| BR1_TS8_TC4 | TC-003377 | T5 | T5-DEBIT-COMPLETION-RESEND | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003377.step1.auth-only.json`<br>`TD-TC-003377.step2.completion.json`<br>`TD-TC-003377.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.161 |
| BR1_TS8_TC5 | TC-003373 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003373.step1.auth-only.json`<br>`TD-TC-003373.step2.completion.json`<br>`TD-TC-003373.step3.totals.json` | p.78, p.79, p.512, p.97, p.161 |
| BR1_TS8_TC6 | TC-003374 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003374.step1.auth-only.json`<br>`TD-TC-003374.step2.timeout-reversal.json` | p.47, p.512, p.97, p.161 |
| BR1_TS8_TC7 | TC-003375 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS9 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-003378 | T1 | T1-AUT | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003378.json` | p.545, p.512, p.97, p.161 |
| BR1_TS9_TC2 | TC-003379 | T2 | T2-CL-TO-CONTACT | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003379.json` | p.638, p.512, p.97, p.161 |
| BR1_TS9_TC3 | TC-003380 | T5 | T5-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003380.step1.auth-only.json`<br>`TD-TC-003380.step2.completion.json` | p.545, p.512, p.97, p.161 |
| BR1_TS9_TC4 | TC-003384 | T5 | T5-DEBIT-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003384.step1.auth-only.json`<br>`TD-TC-003384.step2.cancellation.json` | p.99, p.512, p.97, p.161 |
| BR1_TS9_TC5 | TC-003385 | T5 | T5-DEBIT-COMPLETION-RESEND | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003385.step1.auth-only.json`<br>`TD-TC-003385.step2.completion.json`<br>`TD-TC-003385.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.161 |
| BR1_TS9_TC6 | TC-003381 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003381.step1.auth-only.json`<br>`TD-TC-003381.step2.completion.json`<br>`TD-TC-003381.step3.totals.json` | p.78, p.79, p.512, p.97, p.161 |
| BR1_TS9_TC7 | TC-003382 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003382.step1.auth-only.json`<br>`TD-TC-003382.step2.timeout-reversal.json` | p.47, p.512, p.97, p.161 |
| BR1_TS9_TC8 | TC-003383 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS10 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-003386 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003386.json` | p.545, p.512, p.97, p.81 |
| BR1_TS10_TC2 | TC-003387 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003387.step1.auth-only.json`<br>`TD-TC-003387.step2.completion.json` | p.545, p.512, p.97, p.81 |
| BR1_TS10_TC3 | TC-003391 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003391.step1.auth-only.json`<br>`TD-TC-003391.step2.cancellation.json` | p.99, p.512, p.97, p.81 |
| BR1_TS10_TC4 | TC-003392 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003392.step1.auth-only.json`<br>`TD-TC-003392.step2.completion.json`<br>`TD-TC-003392.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.81 |
| BR1_TS10_TC5 | TC-003388 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003388.step1.auth-only.json`<br>`TD-TC-003388.step2.completion.json`<br>`TD-TC-003388.step3.totals.json` | p.78, p.79, p.512, p.97, p.81 |
| BR1_TS10_TC6 | TC-003389 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003389.step1.auth-only.json`<br>`TD-TC-003389.step2.timeout-reversal.json` | p.47, p.512, p.97, p.81 |
| BR1_TS10_TC7 | TC-003390 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.81 |

### BR1_TS11 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-003393 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003393.json` | p.545, p.512, p.97 |
| BR1_TS11_TC2 | TC-003394 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003394.step1.auth-only.json`<br>`TD-TC-003394.step2.completion.json` | p.545, p.512, p.97 |
| BR1_TS11_TC3 | TC-003398 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003398.step1.auth-only.json`<br>`TD-TC-003398.step2.cancellation.json` | p.99, p.512, p.97 |
| BR1_TS11_TC4 | TC-003399 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003399.step1.auth-only.json`<br>`TD-TC-003399.step2.completion.json`<br>`TD-TC-003399.step3.completion-resubmitted.json` | p.96, p.97, p.512 |
| BR1_TS11_TC5 | TC-003395 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003395.step1.auth-only.json`<br>`TD-TC-003395.step2.completion.json`<br>`TD-TC-003395.step3.totals.json` | p.78, p.79, p.512, p.97 |
| BR1_TS11_TC6 | TC-003396 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003396.step1.auth-only.json`<br>`TD-TC-003396.step2.timeout-reversal.json` | p.47, p.512, p.97 |
| BR1_TS11_TC7 | TC-003397 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS12 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – Chip Fallback

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-003400 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003400.json` | p.545, p.512, p.97 |
| BR1_TS12_TC2 | TC-003404 | T2 | T2-CHIP-SWIPED | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003404.json` | p.162, p.638, p.512, p.97 |
| BR1_TS12_TC3 | TC-003401 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003401.step1.auth-only.json`<br>`TD-TC-003401.step2.completion.json` | p.545, p.512, p.97 |
| BR1_TS12_TC4 | TC-003406 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003406.step1.auth-only.json`<br>`TD-TC-003406.step2.cancellation.json` | p.99, p.512, p.97 |
| BR1_TS12_TC5 | TC-003407 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003407.step1.auth-only.json`<br>`TD-TC-003407.step2.completion.json`<br>`TD-TC-003407.step3.completion-resubmitted.json` | p.96, p.97, p.512 |
| BR1_TS12_TC6 | TC-003402 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003402.step1.auth-only.json`<br>`TD-TC-003402.step2.completion.json`<br>`TD-TC-003402.step3.totals.json` | p.78, p.79, p.512, p.97 |
| BR1_TS12_TC7 | TC-003403 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003403.step1.auth-only.json`<br>`TD-TC-003403.step2.timeout-reversal.json` | p.47, p.512, p.97 |
| BR1_TS12_TC8 | TC-003405 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS13 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – Credential on File

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-003408 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003408.json` | p.545, p.512, p.97, p.638 |
| BR1_TS13_TC2 | TC-003409 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003409.step1.auth-only.json`<br>`TD-TC-003409.step2.completion.json` | p.545, p.512, p.97, p.638 |
| BR1_TS13_TC3 | TC-003413 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003413.step1.auth-only.json`<br>`TD-TC-003413.step2.cancellation.json` | p.99, p.512, p.97, p.638 |
| BR1_TS13_TC4 | TC-003414 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003414.step1.auth-only.json`<br>`TD-TC-003414.step2.completion.json`<br>`TD-TC-003414.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.638 |
| BR1_TS13_TC5 | TC-003410 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003410.step1.auth-only.json`<br>`TD-TC-003410.step2.completion.json`<br>`TD-TC-003410.step3.totals.json` | p.78, p.79, p.512, p.97, p.638 |
| BR1_TS13_TC6 | TC-003411 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003411.step1.auth-only.json`<br>`TD-TC-003411.step2.timeout-reversal.json` | p.47, p.512, p.97, p.638 |
| BR1_TS13_TC7 | TC-003412 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.638 |

### BR1_TS14 — BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-AUTH_ONLY-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-003415 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003415.json` | p.545, p.512, p.97 |
| BR1_TS14_TC2 | TC-003416 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003416.step1.auth-only.json`<br>`TD-TC-003416.step2.completion.json` | p.545, p.512, p.97 |
| BR1_TS14_TC3 | TC-003420 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003420.step1.auth-only.json`<br>`TD-TC-003420.step2.cancellation.json` | p.99, p.512, p.97 |
| BR1_TS14_TC4 | TC-003421 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003421.step1.auth-only.json`<br>`TD-TC-003421.step2.completion.json`<br>`TD-TC-003421.step3.completion-resubmitted.json` | p.96, p.97, p.512 |
| BR1_TS14_TC5 | TC-003417 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003417.step1.auth-only.json`<br>`TD-TC-003417.step2.completion.json`<br>`TD-TC-003417.step3.totals.json` | p.78, p.79, p.512, p.97 |
| BR1_TS14_TC6 | TC-003418 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003418.step1.auth-only.json`<br>`TD-TC-003418.step2.timeout-reversal.json` | p.47, p.512, p.97 |
| BR1_TS14_TC7 | TC-003419 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS15 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-003422 | T1 | T1-RET | needs_client_input | 1. Merchandise Return [7] | FLAG — | `TD-TC-003422.json` | p.546, p.512, p.97, p.161 |
| BR1_TS15_TC2 | TC-003423 | T5 | T5-RET-VOID | requires_review | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003423.step1.return.json`<br>`TD-TC-003423.step2.void-return.json` | p.546, p.512, p.97, p.161 |
| BR1_TS15_TC3 | TC-003424 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS16 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-003425 | T1 | T1-RET | needs_client_input | 1. Merchandise Return [7] | FLAG — | `TD-TC-003425.json` | p.546, p.512, p.97, p.161 |
| BR1_TS16_TC2 | TC-003426 | T5 | T5-RET-VOID | requires_review | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003426.step1.return.json`<br>`TD-TC-003426.step2.void-return.json` | p.546, p.512, p.97, p.161 |
| BR1_TS16_TC3 | TC-003427 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS17 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-003428 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003428.json` | p.546, p.512, p.97 |
| BR1_TS17_TC2 | TC-003429 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003429.step1.return.json`<br>`TD-TC-003429.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS17_TC3 | TC-003430 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS18 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-003431 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003431.json` | p.546, p.512, p.97 |
| BR1_TS18_TC2 | TC-003432 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003432.step1.return.json`<br>`TD-TC-003432.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS18_TC3 | TC-003433 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS19 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Chip Fallback

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-003434 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003434.json` | p.546, p.512, p.97 |
| BR1_TS19_TC2 | TC-003435 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003435.step1.return.json`<br>`TD-TC-003435.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS19_TC3 | TC-003436 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS20 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Credential on File

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-003437 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003437.json` | p.546, p.512, p.97, p.638 |
| BR1_TS20_TC2 | TC-003438 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003438.step1.return.json`<br>`TD-TC-003438.step2.void-return.json` | p.546, p.512, p.97, p.638 |
| BR1_TS20_TC3 | TC-003439 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.638 |

### BR1_TS21 — BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-RETURN-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-003440 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003440.json` | p.546, p.512, p.97 |
| BR1_TS21_TC2 | TC-003441 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003441.step1.return.json`<br>`TD-TC-003441.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS21_TC3 | TC-003442 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS22 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

### BR1_TS23 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

### BR1_TS24 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

### BR1_TS25 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

### BR1_TS26 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Chip Fallback

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

### BR1_TS27 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Credential on File

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

### BR1_TS28 — BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-8B4D24D4`).

_No test case._ Reason: no check planned.

## 4. Follow-on combinations → test cases

Follow-on categories (reversal, time-out reversal, cancellation, void, ...) are steps inside the flow test cases of their original scenario, not scenarios of their own. A combination is traced to every test case of the same entry mode that has a later step of that category.

| Leg | Combination | Category | Entry mode | Status | Flows (spec) | Exercised by |
|---|---|---|---|---|---|---|
| BR1_LEG1 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-EMV_CONTACT` | Void of a Merchandise Return | EMV Contact Chip | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-EMV-RETURN-VOID] | BR1_TS15_TC2 |
| BR1_LEG2 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-EMV_CONTACTLESS` | Void of a Merchandise Return | EMV Contactless Chip | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-EMV-RETURN-VOID] | BR1_TS16_TC2 |
| BR1_LEG3 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-MAGSTRIPE` | Void of a Merchandise Return | Magnetic Stripe | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS17_TC2 |
| BR1_LEG4 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-CONTACTLESS_MAGSTRIPE` | Void of a Merchandise Return | Contactless Magnetic Stripe | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS18_TC2 |
| BR1_LEG5 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-EMV_FALLBACK` | Void of a Merchandise Return | Chip Fallback | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS19_TC2 |
| BR1_LEG6 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-CREDENTIAL_ON_FILE` | Void of a Merchandise Return | Credential on File | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS20_TC2 |
| BR1_LEG7 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-VOID_RETURN-CONTACTLESS_MOBILE` | Void of a Merchandise Return | Contactless Mobile Commerce Device | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS21_TC2 |
| BR1_LEG8 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-EMV_CONTACT` | Purchase Reversal | EMV Contact Chip | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-EMV-SALE-VOID] | BR1_TS1_TC5, BR1_TS1_TC7, BR1_TS1_TC8, BR1_TS1_TC18, BR1_TS1_TC26 |
| BR1_LEG9 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-EMV_CONTACTLESS` | Purchase Reversal | EMV Contactless Chip | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-EMV-SALE-VOID] | BR1_TS2_TC6, BR1_TS2_TC8, BR1_TS2_TC9, BR1_TS2_TC19, BR1_TS2_TC27 |
| BR1_LEG10 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-MAGSTRIPE` | Purchase Reversal | Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS3_TC5, BR1_TS3_TC7, BR1_TS3_TC8, BR1_TS3_TC18, BR1_TS3_TC26 |
| BR1_LEG11 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-CONTACTLESS_MAGSTRIPE` | Purchase Reversal | Contactless Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS4_TC5, BR1_TS4_TC7, BR1_TS4_TC8, BR1_TS4_TC18, BR1_TS4_TC26 |
| BR1_LEG12 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-EMV_FALLBACK` | Purchase Reversal | Chip Fallback | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS5_TC6, BR1_TS5_TC8, BR1_TS5_TC9, BR1_TS5_TC19, BR1_TS5_TC27 |
| BR1_LEG13 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-CREDENTIAL_ON_FILE` | Purchase Reversal | Credential on File | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS6_TC5, BR1_TS6_TC7, BR1_TS6_TC8, BR1_TS6_TC18, BR1_TS6_TC26 |
| BR1_LEG14 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-REVERSAL-CONTACTLESS_MOBILE` | Purchase Reversal | Contactless Mobile Commerce Device | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS7_TC5, BR1_TS7_TC7, BR1_TS7_TC8, BR1_TS7_TC18, BR1_TS7_TC26 |
| BR1_LEG15 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-EMV_CONTACT` | Time-out Reversal | EMV Contact Chip | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-EMV-TIMEOUT-REVERSAL] | BR1_TS1_TC9, BR1_TS1_TC10, BR1_TS1_TC11, BR1_TS1_TC12, BR1_TS1_TC13, BR1_TS1_TC14, BR1_TS1_TC16, BR1_TS1_TC20, BR1_TS1_TC26, BR1_TS8_TC6 |
| BR1_LEG16 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-EMV_CONTACTLESS` | Time-out Reversal | EMV Contactless Chip | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-EMV-TIMEOUT-REVERSAL] | BR1_TS2_TC10, BR1_TS2_TC11, BR1_TS2_TC12, BR1_TS2_TC13, BR1_TS2_TC14, BR1_TS2_TC15, BR1_TS2_TC17, BR1_TS2_TC21, BR1_TS2_TC27, BR1_TS9_TC7 |
| BR1_LEG17 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-MAGSTRIPE` | Time-out Reversal | Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS3_TC9, BR1_TS3_TC10, BR1_TS3_TC11, BR1_TS3_TC12, BR1_TS3_TC13, BR1_TS3_TC14, BR1_TS3_TC16, BR1_TS3_TC20, BR1_TS3_TC26, BR1_TS10_TC6 |
| BR1_LEG18 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-CONTACTLESS_MAGSTRIPE` | Time-out Reversal | Contactless Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS4_TC9, BR1_TS4_TC10, BR1_TS4_TC11, BR1_TS4_TC12, BR1_TS4_TC13, BR1_TS4_TC14, BR1_TS4_TC16, BR1_TS4_TC20, BR1_TS4_TC26, BR1_TS11_TC6 |
| BR1_LEG19 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-EMV_FALLBACK` | Time-out Reversal | Chip Fallback | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS5_TC10, BR1_TS5_TC11, BR1_TS5_TC12, BR1_TS5_TC13, BR1_TS5_TC14, BR1_TS5_TC15, BR1_TS5_TC17, BR1_TS5_TC21, BR1_TS5_TC27, BR1_TS12_TC7 |
| BR1_LEG20 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-CREDENTIAL_ON_FILE` | Time-out Reversal | Credential on File | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS6_TC9, BR1_TS6_TC10, BR1_TS6_TC11, BR1_TS6_TC12, BR1_TS6_TC13, BR1_TS6_TC14, BR1_TS6_TC16, BR1_TS6_TC20, BR1_TS6_TC26, BR1_TS13_TC6 |
| BR1_LEG21 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-TIMEOUT_REVERSAL-CONTACTLESS_MOBILE` | Time-out Reversal | Contactless Mobile Commerce Device | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS7_TC9, BR1_TS7_TC10, BR1_TS7_TC11, BR1_TS7_TC12, BR1_TS7_TC13, BR1_TS7_TC14, BR1_TS7_TC16, BR1_TS7_TC20, BR1_TS7_TC26, BR1_TS14_TC6 |
| BR1_LEG22 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-EMV_CONTACT` | Cancellation | EMV Contact Chip | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-EMV-AUTH-CANCELLATION] | BR1_TS8_TC3 |
| BR1_LEG23 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-EMV_CONTACTLESS` | Cancellation | EMV Contactless Chip | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-EMV-AUTH-CANCELLATION] | BR1_TS9_TC4 |
| BR1_LEG24 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-MAGSTRIPE` | Cancellation | Magnetic Stripe | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS10_TC3 |
| BR1_LEG25 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-CONTACTLESS_MAGSTRIPE` | Cancellation | Contactless Magnetic Stripe | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS11_TC3 |
| BR1_LEG26 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-EMV_FALLBACK` | Cancellation | Chip Fallback | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS12_TC4 |
| BR1_LEG27 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-CREDENTIAL_ON_FILE` | Cancellation | Credential on File | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS13_TC3 |
| BR1_LEG28 | `TS-ATL105-DEBIT-MAESTRO_DIRECT-CANCELLATION-CONTACTLESS_MOBILE` | Cancellation | Contactless Mobile Commerce Device | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS14_TC3 |

## 5. Gaps and unresolved links

### Scenarios without test cases

- BR1_TS22 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contact Chip: no check planned
- BR1_TS23 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contactless Chip: no check planned
- BR1_TS24 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Magnetic Stripe: no check planned
- BR1_TS25 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Magnetic Stripe: no check planned
- BR1_TS26 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Chip Fallback: no check planned
- BR1_TS27 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Credential on File: no check planned
- BR1_TS28 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Mobile Commerce Device: no check planned

### Test cases without test data

These test cases have no host message (device-only checks, or blocked by a spec gap), so they have no request test data.

- BR1_TS1_TC28 TC-003187 (device, requires_review): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS1_TC29 TC-003188 (device, ready): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS10_TC7 TC-003390 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS11_TC7 TC-003397 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS12_TC8 TC-003405 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS13_TC7 TC-003412 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS14_TC7 TC-003419 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS15_TC3 TC-003424 (device, ready): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS16_TC3 TC-003427 (device, ready): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS17_TC3 TC-003430 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS18_TC3 TC-003433 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS19_TC3 TC-003436 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS2_TC29 TC-003217 (device, requires_review): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS2_TC30 TC-003218 (device, ready): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS20_TC3 TC-003439 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS21_TC3 TC-003442 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS3_TC28 TC-003246 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS3_TC29 TC-003247 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS3_TC30 TC-003253 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit card, 3 failed swipes → transaction ended (no manual entry)
- BR1_TS4_TC28 TC-003276 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS4_TC29 TC-003277 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS5_TC29 TC-003306 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS5_TC30 TC-003307 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS6_TC28 TC-003335 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS6_TC29 TC-003336 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS7_TC28 TC-003364 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS7_TC29 TC-003365 (device, pending_sme): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS8_TC7 TC-003375 (device, ready): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS9_TC8 TC-003383 (device, ready): BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Preauthorization disabled (card type 168) → not offered at the device

### Missing test data files

- None.

### Unresolved links

- None. Every test case resolves to its scenario and to this Business Requirement.
