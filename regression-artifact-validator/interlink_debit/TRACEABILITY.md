# Traceability matrix — BR1 BUYPASS ATL105 Interlink Debit

Business Requirement → Test Scenario → Test Case → Test Data.

Generated 2026-10-31 10:24 UTC from this folder's own artifacts only (`requirements_scenarios/business_requirements.json`, `business_scenarios.json`, `follow_on_leg_combinations.csv`, `test_cases/test_cases.jsonl`, `test_cases/test_case_validation.json`, `run_set.json`, `test_data/`). Nothing is inferred. Any link that cannot be resolved is listed in [Unresolved links](#unresolved-links).

## Summary

| Item | Value |
|---|---|
| Business Requirement | `BR-EAD513C5` (BR1) BUYPASS ATL105 Interlink Debit |
| Spec | ATL105 2026-4 (`SRC-ATL105-PDF-001`) |
| Selection | platform=BUYPASS, spec=ATL105, network=INTERLINK, program=DEBIT |
| Dimension mapping | candidate |
| Build | `buypass-atl105-interlink-debit-5d305cd6cb` |
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

**BR1 — BUYPASS ATL105 Interlink Debit** (`BR-EAD513C5`), status `built`.

Spec citations for the requirement:

| Source | Page | Quote |
|---|---|---|
| Appendix C | p.512 | 48 Interlink - Authorizer Type: Debit |
| anchored:A-SALE-PREAUTH-TRACK | p.81 | If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Authorization Only |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Balance Inquiry |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Merchandise Return |
| 10.2.2 Supported Transaction Types | p.97 | The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture |
| 10.14.2.3 Supported Cards | p.161 | The following debit cards are supported for EMV processing: ... Interlink |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Purchase |
| anchored:APPJ-FN2-COF-DEBIT-FOLLOWON | p.638 | also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction |

## 2. Requirement → Scenarios

| Scenario | Name | Status | Test cases | Test data files | Spec basis |
|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip | valid | 29 | 60 | p.512, p.97, p.161 |
| BR1_TS2 | BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip | valid | 30 | 61 | p.512, p.97, p.161 |
| BR1_TS3 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe | unconfirmed | 30 | 60 | p.512, p.97, p.81 |
| BR1_TS4 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe | unconfirmed | 29 | 60 | p.512, p.97 |
| BR1_TS5 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback | unconfirmed | 30 | 61 | p.512, p.97 |
| BR1_TS6 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File | unconfirmed | 29 | 60 | p.512, p.97, p.638 |
| BR1_TS7 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | 29 | 60 | p.512, p.97 |
| BR1_TS8 | BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip | valid | 7 | 13 | p.512, p.97, p.161 |
| BR1_TS9 | BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip | valid | 8 | 14 | p.512, p.97, p.161 |
| BR1_TS10 | BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe | unconfirmed | 7 | 13 | p.512, p.97, p.81 |
| BR1_TS11 | BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe | unconfirmed | 7 | 13 | p.512, p.97 |
| BR1_TS12 | BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback | unconfirmed | 8 | 14 | p.512, p.97 |
| BR1_TS13 | BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File | unconfirmed | 7 | 13 | p.512, p.97, p.638 |
| BR1_TS14 | BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | 7 | 13 | p.512, p.97 |
| BR1_TS15 | BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip | valid | 3 | 3 | p.512, p.97, p.161 |
| BR1_TS16 | BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip | valid | 3 | 3 | p.512, p.97, p.161 |
| BR1_TS17 | BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS18 | BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS19 | BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS20 | BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File | unconfirmed | 3 | 3 | p.512, p.97, p.638 |
| BR1_TS21 | BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 3 | 3 | p.512, p.97 |
| BR1_TS22 | BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip | valid | **0** | 0 | p.512, p.97, p.161 |
| BR1_TS23 | BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip | valid | **0** | 0 | p.512, p.97, p.161 |
| BR1_TS24 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe | unconfirmed | **0** | 0 | p.512, p.97 |
| BR1_TS25 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe | unconfirmed | **0** | 0 | p.512, p.97 |
| BR1_TS26 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback | unconfirmed | **0** | 0 | p.512, p.97 |
| BR1_TS27 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Credential on File | unconfirmed | **0** | 0 | p.512, p.97, p.638 |
| BR1_TS28 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | **0** | 0 | p.512, p.97 |

## 3. Scenario → Test Case → Test Data

Steps show each step's transaction category and its Appendix G code (or special Prompt Code). Expected = the test case's final response-code expectation (tier, value, spec page). Per-step expectations, rule checks and runtime links are in `test_cases/test_cases.jsonl`.

### BR1_TS1 — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-002887 | T1 | T1-PUR | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-002887.json` | p.545, p.512, p.97, p.161 |
| BR1_TS1_TC2 | TC-002913 | T1 | T1-DEBIT-CASHBACK | needs_client_input | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-002913.json` | p.97, p.375, p.512, p.161 |
| BR1_TS1_TC3 | TC-002912 | T2 | T2-DEBIT-EXPIRED-ONLINE | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-002912.json` | p.96, p.512, p.97, p.161 |
| BR1_TS1_TC4 | TC-002914 | T4 | T4-DEBIT-CASHBACK-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-002914.json` | p.97, p.98, p.512, p.161 |
| BR1_TS1_TC5 | TC-002915 | T4 | T4-DEBIT-PARTIAL-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-002915.step1.purchase.json`<br>`TD-TC-002915.step2.reversal.json` | p.97, p.98, p.512, p.161 |
| BR1_TS1_TC6 | TC-002901 | T5 | T5-PUR-NEXT-PUR | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002901.step1.purchase.json`<br>`TD-TC-002901.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.161 |
| BR1_TS1_TC7 | TC-002902 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002902.step1.purchase.json`<br>`TD-TC-002902.step2.reversal.json`<br>`TD-TC-002902.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.161 |
| BR1_TS1_TC8 | TC-002911 | T5 | T5-DEBIT-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002911.step1.purchase.json`<br>`TD-TC-002911.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.161 |
| BR1_TS1_TC9 | TC-002888 | T6 | T6-PUR-NO-RESPONSE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002888.step1.purchase.json`<br>`TD-TC-002888.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC10 | TC-002889 | T6 | T6-PUR-LATE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002889.step1.purchase.json`<br>`TD-TC-002889.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC11 | TC-002890 | T6 | T6-PUR-UNREADABLE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-002890.step1.purchase.json`<br>`TD-TC-002890.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.161 |
| BR1_TS1_TC12 | TC-002891 | T6 | T6-PUR-TOR-NEXT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002891.step1.purchase.json`<br>`TD-TC-002891.step2.timeout-reversal.json`<br>`TD-TC-002891.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC13 | TC-002892 | T6 | T6-PUR-TOR-RETRY | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002892.step1.purchase.json`<br>`TD-TC-002892.step2.timeout-reversal.json`<br>`TD-TC-002892.step3.tor-retry.json`<br>`TD-TC-002892.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.161 |
| BR1_TS1_TC14 | TC-002903 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002903.step1.purchase.json`<br>`TD-TC-002903.step2.timeout-reversal.json`<br>`TD-TC-002903.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.161 |
| BR1_TS1_TC15 | TC-002904 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002904.step1.purchase.json`<br>`TD-TC-002904.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC16 | TC-002905 | T6 | T6-PUR-LATE-RESUBMIT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002905.step1.purchase.json`<br>`TD-TC-002905.step2.timeout-reversal.json`<br>`TD-TC-002905.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC17 | TC-002893 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002893.step1.purchase.json`<br>`TD-TC-002893.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS1_TC18 | TC-002894 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002894.step1.purchase.json`<br>`TD-TC-002894.step2.reversal.json`<br>`TD-TC-002894.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS1_TC19 | TC-002895 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002895.step1.purchase.json`<br>`TD-TC-002895.step2.return.json`<br>`TD-TC-002895.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS1_TC20 | TC-002896 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002896.step1.purchase.json`<br>`TD-TC-002896.step2.timeout-reversal.json`<br>`TD-TC-002896.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.161 |
| BR1_TS1_TC21 | TC-002897 | T8 | T8-PUR-READONLY | needs_client_input | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002897.step1.purchase.json`<br>`TD-TC-002897.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS1_TC22 | TC-002898 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-002898.step1.purchase.json`<br>`TD-TC-002898.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS1_TC23 | TC-002906 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002906.step1.purchase.json`<br>`TD-TC-002906.step2.totals.json`<br>`TD-TC-002906.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS1_TC24 | TC-002907 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002907.step1.purchase.json`<br>`TD-TC-002907.step2.totals.json`<br>`TD-TC-002907.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS1_TC25 | TC-002899 | T10 | T10-TOR-UNMATCHED | needs_client_input | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002899.json` | p.40, p.512, p.97, p.161 |
| BR1_TS1_TC26 | TC-002900 | T10 | T10-TOR-AFTER-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002900.step1.purchase.json`<br>`TD-TC-002900.step2.reversal.json`<br>`TD-TC-002900.step3.timeout-reversal.json` | p.40, p.512, p.97, p.161 |
| BR1_TS1_TC27 | TC-002908 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-002908.json` | p.404, p.512, p.97, p.161 |
| BR1_TS1_TC28 | TC-002909 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.161 |
| BR1_TS1_TC29 | TC-002910 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS2 — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-002916 | T1 | T1-PUR | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-002916.json` | p.545, p.512, p.97, p.161 |
| BR1_TS2_TC2 | TC-002943 | T1 | T1-DEBIT-CASHBACK | needs_client_input | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-002943.json` | p.97, p.375, p.512, p.161 |
| BR1_TS2_TC3 | TC-002917 | T2 | T2-CL-TO-CONTACT | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-002917.json` | p.638, p.512, p.97, p.161 |
| BR1_TS2_TC4 | TC-002942 | T2 | T2-DEBIT-EXPIRED-ONLINE | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-002942.json` | p.96, p.512, p.97, p.161 |
| BR1_TS2_TC5 | TC-002944 | T4 | T4-DEBIT-CASHBACK-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-002944.json` | p.97, p.98, p.512, p.161 |
| BR1_TS2_TC6 | TC-002945 | T4 | T4-DEBIT-PARTIAL-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-002945.step1.purchase.json`<br>`TD-TC-002945.step2.reversal.json` | p.97, p.98, p.512, p.161 |
| BR1_TS2_TC7 | TC-002931 | T5 | T5-PUR-NEXT-PUR | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002931.step1.purchase.json`<br>`TD-TC-002931.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.161 |
| BR1_TS2_TC8 | TC-002932 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002932.step1.purchase.json`<br>`TD-TC-002932.step2.reversal.json`<br>`TD-TC-002932.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.161 |
| BR1_TS2_TC9 | TC-002941 | T5 | T5-DEBIT-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002941.step1.purchase.json`<br>`TD-TC-002941.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.161 |
| BR1_TS2_TC10 | TC-002918 | T6 | T6-PUR-NO-RESPONSE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002918.step1.purchase.json`<br>`TD-TC-002918.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC11 | TC-002919 | T6 | T6-PUR-LATE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002919.step1.purchase.json`<br>`TD-TC-002919.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC12 | TC-002920 | T6 | T6-PUR-UNREADABLE | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-002920.step1.purchase.json`<br>`TD-TC-002920.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.161 |
| BR1_TS2_TC13 | TC-002921 | T6 | T6-PUR-TOR-NEXT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002921.step1.purchase.json`<br>`TD-TC-002921.step2.timeout-reversal.json`<br>`TD-TC-002921.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC14 | TC-002922 | T6 | T6-PUR-TOR-RETRY | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002922.step1.purchase.json`<br>`TD-TC-002922.step2.timeout-reversal.json`<br>`TD-TC-002922.step3.tor-retry.json`<br>`TD-TC-002922.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.161 |
| BR1_TS2_TC15 | TC-002933 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002933.step1.purchase.json`<br>`TD-TC-002933.step2.timeout-reversal.json`<br>`TD-TC-002933.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.161 |
| BR1_TS2_TC16 | TC-002934 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002934.step1.purchase.json`<br>`TD-TC-002934.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC17 | TC-002935 | T6 | T6-PUR-LATE-RESUBMIT | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002935.step1.purchase.json`<br>`TD-TC-002935.step2.timeout-reversal.json`<br>`TD-TC-002935.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC18 | TC-002923 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002923.step1.purchase.json`<br>`TD-TC-002923.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS2_TC19 | TC-002924 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002924.step1.purchase.json`<br>`TD-TC-002924.step2.reversal.json`<br>`TD-TC-002924.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS2_TC20 | TC-002925 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002925.step1.purchase.json`<br>`TD-TC-002925.step2.return.json`<br>`TD-TC-002925.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.161 |
| BR1_TS2_TC21 | TC-002926 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002926.step1.purchase.json`<br>`TD-TC-002926.step2.timeout-reversal.json`<br>`TD-TC-002926.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.161 |
| BR1_TS2_TC22 | TC-002927 | T8 | T8-PUR-READONLY | needs_client_input | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002927.step1.purchase.json`<br>`TD-TC-002927.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS2_TC23 | TC-002928 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-002928.step1.purchase.json`<br>`TD-TC-002928.step2.totals.json` | p.79, p.512, p.97, p.161 |
| BR1_TS2_TC24 | TC-002936 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002936.step1.purchase.json`<br>`TD-TC-002936.step2.totals.json`<br>`TD-TC-002936.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS2_TC25 | TC-002937 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002937.step1.purchase.json`<br>`TD-TC-002937.step2.totals.json`<br>`TD-TC-002937.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.161 |
| BR1_TS2_TC26 | TC-002929 | T10 | T10-TOR-UNMATCHED | needs_client_input | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002929.json` | p.40, p.512, p.97, p.161 |
| BR1_TS2_TC27 | TC-002930 | T10 | T10-TOR-AFTER-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002930.step1.purchase.json`<br>`TD-TC-002930.step2.reversal.json`<br>`TD-TC-002930.step3.timeout-reversal.json` | p.40, p.512, p.97, p.161 |
| BR1_TS2_TC28 | TC-002938 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-002938.json` | p.404, p.512, p.97, p.161 |
| BR1_TS2_TC29 | TC-002939 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.161 |
| BR1_TS2_TC30 | TC-002940 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS3 — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-002946 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-002946.json` | p.545, p.512, p.97, p.81 |
| BR1_TS3_TC2 | TC-002972 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-002972.json` | p.97, p.375, p.512, p.81 |
| BR1_TS3_TC3 | TC-002971 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-002971.json` | p.96, p.512, p.97, p.81 |
| BR1_TS3_TC4 | TC-002973 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-002973.json` | p.97, p.98, p.512, p.81 |
| BR1_TS3_TC5 | TC-002974 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-002974.step1.purchase.json`<br>`TD-TC-002974.step2.reversal.json` | p.97, p.98, p.512, p.81 |
| BR1_TS3_TC6 | TC-002960 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002960.step1.purchase.json`<br>`TD-TC-002960.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.81 |
| BR1_TS3_TC7 | TC-002961 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002961.step1.purchase.json`<br>`TD-TC-002961.step2.reversal.json`<br>`TD-TC-002961.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.81 |
| BR1_TS3_TC8 | TC-002970 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002970.step1.purchase.json`<br>`TD-TC-002970.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.81 |
| BR1_TS3_TC9 | TC-002947 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002947.step1.purchase.json`<br>`TD-TC-002947.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC10 | TC-002948 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002948.step1.purchase.json`<br>`TD-TC-002948.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC11 | TC-002949 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-002949.step1.purchase.json`<br>`TD-TC-002949.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.81 |
| BR1_TS3_TC12 | TC-002950 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002950.step1.purchase.json`<br>`TD-TC-002950.step2.timeout-reversal.json`<br>`TD-TC-002950.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC13 | TC-002951 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002951.step1.purchase.json`<br>`TD-TC-002951.step2.timeout-reversal.json`<br>`TD-TC-002951.step3.tor-retry.json`<br>`TD-TC-002951.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.81 |
| BR1_TS3_TC14 | TC-002962 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002962.step1.purchase.json`<br>`TD-TC-002962.step2.timeout-reversal.json`<br>`TD-TC-002962.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.81 |
| BR1_TS3_TC15 | TC-002963 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002963.step1.purchase.json`<br>`TD-TC-002963.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC16 | TC-002964 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002964.step1.purchase.json`<br>`TD-TC-002964.step2.timeout-reversal.json`<br>`TD-TC-002964.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC17 | TC-002952 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002952.step1.purchase.json`<br>`TD-TC-002952.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.81 |
| BR1_TS3_TC18 | TC-002953 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002953.step1.purchase.json`<br>`TD-TC-002953.step2.reversal.json`<br>`TD-TC-002953.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.81 |
| BR1_TS3_TC19 | TC-002954 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002954.step1.purchase.json`<br>`TD-TC-002954.step2.return.json`<br>`TD-TC-002954.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.81 |
| BR1_TS3_TC20 | TC-002955 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002955.step1.purchase.json`<br>`TD-TC-002955.step2.timeout-reversal.json`<br>`TD-TC-002955.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.81 |
| BR1_TS3_TC21 | TC-002956 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002956.step1.purchase.json`<br>`TD-TC-002956.step2.totals.json` | p.79, p.512, p.97, p.81 |
| BR1_TS3_TC22 | TC-002957 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-002957.step1.purchase.json`<br>`TD-TC-002957.step2.totals.json` | p.79, p.512, p.97, p.81 |
| BR1_TS3_TC23 | TC-002965 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002965.step1.purchase.json`<br>`TD-TC-002965.step2.totals.json`<br>`TD-TC-002965.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.81 |
| BR1_TS3_TC24 | TC-002966 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002966.step1.purchase.json`<br>`TD-TC-002966.step2.totals.json`<br>`TD-TC-002966.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.81 |
| BR1_TS3_TC25 | TC-002958 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002958.json` | p.40, p.512, p.97, p.81 |
| BR1_TS3_TC26 | TC-002959 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002959.step1.purchase.json`<br>`TD-TC-002959.step2.reversal.json`<br>`TD-TC-002959.step3.timeout-reversal.json` | p.40, p.512, p.97, p.81 |
| BR1_TS3_TC27 | TC-002967 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-002967.json` | p.404, p.512, p.97, p.81 |
| BR1_TS3_TC28 | TC-002968 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.81 |
| BR1_TS3_TC29 | TC-002969 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.81 |
| BR1_TS3_TC30 | TC-002975 | T11 | T11-DEBIT-NO-MANUAL | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.96, p.512, p.97, p.81 |

### BR1_TS4 — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-002976 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-002976.json` | p.545, p.512, p.97 |
| BR1_TS4_TC2 | TC-003002 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003002.json` | p.97, p.375, p.512 |
| BR1_TS4_TC3 | TC-003001 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003001.json` | p.96, p.512, p.97 |
| BR1_TS4_TC4 | TC-003003 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003003.json` | p.97, p.98, p.512 |
| BR1_TS4_TC5 | TC-003004 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003004.step1.purchase.json`<br>`TD-TC-003004.step2.reversal.json` | p.97, p.98, p.512 |
| BR1_TS4_TC6 | TC-002990 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002990.step1.purchase.json`<br>`TD-TC-002990.step2.next-transaction.json` | p.40, p.409, p.512, p.97 |
| BR1_TS4_TC7 | TC-002991 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002991.step1.purchase.json`<br>`TD-TC-002991.step2.reversal.json`<br>`TD-TC-002991.step3.return.json` | PIPS p.30, p.546, p.512, p.97 |
| BR1_TS4_TC8 | TC-003000 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003000.step1.purchase.json`<br>`TD-TC-003000.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97 |
| BR1_TS4_TC9 | TC-002977 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002977.step1.purchase.json`<br>`TD-TC-002977.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS4_TC10 | TC-002978 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002978.step1.purchase.json`<br>`TD-TC-002978.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS4_TC11 | TC-002979 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-002979.step1.purchase.json`<br>`TD-TC-002979.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97 |
| BR1_TS4_TC12 | TC-002980 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002980.step1.purchase.json`<br>`TD-TC-002980.step2.timeout-reversal.json`<br>`TD-TC-002980.step3.next-transaction.json` | p.39, p.40, p.512, p.97 |
| BR1_TS4_TC13 | TC-002981 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-002981.step1.purchase.json`<br>`TD-TC-002981.step2.timeout-reversal.json`<br>`TD-TC-002981.step3.tor-retry.json`<br>`TD-TC-002981.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97 |
| BR1_TS4_TC14 | TC-002992 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002992.step1.purchase.json`<br>`TD-TC-002992.step2.timeout-reversal.json`<br>`TD-TC-002992.step3.next-transaction.json` | p.40, p.187, p.512, p.97 |
| BR1_TS4_TC15 | TC-002993 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002993.step1.purchase.json`<br>`TD-TC-002993.step2.resubmitted.json` | p.549, p.40, p.512, p.97 |
| BR1_TS4_TC16 | TC-002994 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002994.step1.purchase.json`<br>`TD-TC-002994.step2.timeout-reversal.json`<br>`TD-TC-002994.step3.resubmitted.json` | p.43, p.40, p.512, p.97 |
| BR1_TS4_TC17 | TC-002982 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002982.step1.purchase.json`<br>`TD-TC-002982.step2.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS4_TC18 | TC-002983 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002983.step1.purchase.json`<br>`TD-TC-002983.step2.reversal.json`<br>`TD-TC-002983.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS4_TC19 | TC-002984 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002984.step1.purchase.json`<br>`TD-TC-002984.step2.return.json`<br>`TD-TC-002984.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS4_TC20 | TC-002985 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002985.step1.purchase.json`<br>`TD-TC-002985.step2.timeout-reversal.json`<br>`TD-TC-002985.step3.totals.json` | p.78, p.79, p.40, p.512, p.97 |
| BR1_TS4_TC21 | TC-002986 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-002986.step1.purchase.json`<br>`TD-TC-002986.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS4_TC22 | TC-002987 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-002987.step1.purchase.json`<br>`TD-TC-002987.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS4_TC23 | TC-002995 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002995.step1.purchase.json`<br>`TD-TC-002995.step2.totals.json`<br>`TD-TC-002995.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS4_TC24 | TC-002996 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002996.step1.purchase.json`<br>`TD-TC-002996.step2.totals.json`<br>`TD-TC-002996.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS4_TC25 | TC-002988 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002988.json` | p.40, p.512, p.97 |
| BR1_TS4_TC26 | TC-002989 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-002989.step1.purchase.json`<br>`TD-TC-002989.step2.reversal.json`<br>`TD-TC-002989.step3.timeout-reversal.json` | p.40, p.512, p.97 |
| BR1_TS4_TC27 | TC-002997 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-002997.json` | p.404, p.512, p.97 |
| BR1_TS4_TC28 | TC-002998 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97 |
| BR1_TS4_TC29 | TC-002999 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS5 — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-003005 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003005.json` | p.545, p.512, p.97 |
| BR1_TS5_TC2 | TC-003032 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003032.json` | p.97, p.375, p.512 |
| BR1_TS5_TC3 | TC-003019 | T2 | T2-CHIP-SWIPED | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003019.json` | p.162, p.638, p.512, p.97 |
| BR1_TS5_TC4 | TC-003031 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003031.json` | p.96, p.512, p.97 |
| BR1_TS5_TC5 | TC-003033 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003033.json` | p.97, p.98, p.512 |
| BR1_TS5_TC6 | TC-003034 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003034.step1.purchase.json`<br>`TD-TC-003034.step2.reversal.json` | p.97, p.98, p.512 |
| BR1_TS5_TC7 | TC-003020 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003020.step1.purchase.json`<br>`TD-TC-003020.step2.next-transaction.json` | p.40, p.409, p.512, p.97 |
| BR1_TS5_TC8 | TC-003021 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003021.step1.purchase.json`<br>`TD-TC-003021.step2.reversal.json`<br>`TD-TC-003021.step3.return.json` | PIPS p.30, p.546, p.512, p.97 |
| BR1_TS5_TC9 | TC-003030 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003030.step1.purchase.json`<br>`TD-TC-003030.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97 |
| BR1_TS5_TC10 | TC-003006 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003006.step1.purchase.json`<br>`TD-TC-003006.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS5_TC11 | TC-003007 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003007.step1.purchase.json`<br>`TD-TC-003007.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS5_TC12 | TC-003008 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003008.step1.purchase.json`<br>`TD-TC-003008.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97 |
| BR1_TS5_TC13 | TC-003009 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003009.step1.purchase.json`<br>`TD-TC-003009.step2.timeout-reversal.json`<br>`TD-TC-003009.step3.next-transaction.json` | p.39, p.40, p.512, p.97 |
| BR1_TS5_TC14 | TC-003010 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003010.step1.purchase.json`<br>`TD-TC-003010.step2.timeout-reversal.json`<br>`TD-TC-003010.step3.tor-retry.json`<br>`TD-TC-003010.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97 |
| BR1_TS5_TC15 | TC-003022 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003022.step1.purchase.json`<br>`TD-TC-003022.step2.timeout-reversal.json`<br>`TD-TC-003022.step3.next-transaction.json` | p.40, p.187, p.512, p.97 |
| BR1_TS5_TC16 | TC-003023 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003023.step1.purchase.json`<br>`TD-TC-003023.step2.resubmitted.json` | p.549, p.40, p.512, p.97 |
| BR1_TS5_TC17 | TC-003024 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003024.step1.purchase.json`<br>`TD-TC-003024.step2.timeout-reversal.json`<br>`TD-TC-003024.step3.resubmitted.json` | p.43, p.40, p.512, p.97 |
| BR1_TS5_TC18 | TC-003011 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003011.step1.purchase.json`<br>`TD-TC-003011.step2.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS5_TC19 | TC-003012 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003012.step1.purchase.json`<br>`TD-TC-003012.step2.reversal.json`<br>`TD-TC-003012.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS5_TC20 | TC-003013 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003013.step1.purchase.json`<br>`TD-TC-003013.step2.return.json`<br>`TD-TC-003013.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS5_TC21 | TC-003014 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003014.step1.purchase.json`<br>`TD-TC-003014.step2.timeout-reversal.json`<br>`TD-TC-003014.step3.totals.json` | p.78, p.79, p.40, p.512, p.97 |
| BR1_TS5_TC22 | TC-003015 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003015.step1.purchase.json`<br>`TD-TC-003015.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS5_TC23 | TC-003016 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003016.step1.purchase.json`<br>`TD-TC-003016.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS5_TC24 | TC-003025 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003025.step1.purchase.json`<br>`TD-TC-003025.step2.totals.json`<br>`TD-TC-003025.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS5_TC25 | TC-003026 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003026.step1.purchase.json`<br>`TD-TC-003026.step2.totals.json`<br>`TD-TC-003026.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS5_TC26 | TC-003017 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003017.json` | p.40, p.512, p.97 |
| BR1_TS5_TC27 | TC-003018 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003018.step1.purchase.json`<br>`TD-TC-003018.step2.reversal.json`<br>`TD-TC-003018.step3.timeout-reversal.json` | p.40, p.512, p.97 |
| BR1_TS5_TC28 | TC-003027 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003027.json` | p.404, p.512, p.97 |
| BR1_TS5_TC29 | TC-003028 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97 |
| BR1_TS5_TC30 | TC-003029 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS6 — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-003035 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003035.json` | p.545, p.512, p.97, p.638 |
| BR1_TS6_TC2 | TC-003061 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003061.json` | p.97, p.375, p.512, p.638 |
| BR1_TS6_TC3 | TC-003060 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003060.json` | p.96, p.512, p.97, p.638 |
| BR1_TS6_TC4 | TC-003062 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003062.json` | p.97, p.98, p.512, p.638 |
| BR1_TS6_TC5 | TC-003063 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003063.step1.purchase.json`<br>`TD-TC-003063.step2.reversal.json` | p.97, p.98, p.512, p.638 |
| BR1_TS6_TC6 | TC-003049 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003049.step1.purchase.json`<br>`TD-TC-003049.step2.next-transaction.json` | p.40, p.409, p.512, p.97, p.638 |
| BR1_TS6_TC7 | TC-003050 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003050.step1.purchase.json`<br>`TD-TC-003050.step2.reversal.json`<br>`TD-TC-003050.step3.return.json` | PIPS p.30, p.546, p.512, p.97, p.638 |
| BR1_TS6_TC8 | TC-003059 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003059.step1.purchase.json`<br>`TD-TC-003059.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97, p.638 |
| BR1_TS6_TC9 | TC-003036 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003036.step1.purchase.json`<br>`TD-TC-003036.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC10 | TC-003037 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003037.step1.purchase.json`<br>`TD-TC-003037.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC11 | TC-003038 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003038.step1.purchase.json`<br>`TD-TC-003038.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97, p.638 |
| BR1_TS6_TC12 | TC-003039 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003039.step1.purchase.json`<br>`TD-TC-003039.step2.timeout-reversal.json`<br>`TD-TC-003039.step3.next-transaction.json` | p.39, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC13 | TC-003040 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003040.step1.purchase.json`<br>`TD-TC-003040.step2.timeout-reversal.json`<br>`TD-TC-003040.step3.tor-retry.json`<br>`TD-TC-003040.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97, p.638 |
| BR1_TS6_TC14 | TC-003051 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003051.step1.purchase.json`<br>`TD-TC-003051.step2.timeout-reversal.json`<br>`TD-TC-003051.step3.next-transaction.json` | p.40, p.187, p.512, p.97, p.638 |
| BR1_TS6_TC15 | TC-003052 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003052.step1.purchase.json`<br>`TD-TC-003052.step2.resubmitted.json` | p.549, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC16 | TC-003053 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003053.step1.purchase.json`<br>`TD-TC-003053.step2.timeout-reversal.json`<br>`TD-TC-003053.step3.resubmitted.json` | p.43, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC17 | TC-003041 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003041.step1.purchase.json`<br>`TD-TC-003041.step2.totals.json` | p.78, p.79, p.89, p.512, p.97, p.638 |
| BR1_TS6_TC18 | TC-003042 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003042.step1.purchase.json`<br>`TD-TC-003042.step2.reversal.json`<br>`TD-TC-003042.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.638 |
| BR1_TS6_TC19 | TC-003043 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003043.step1.purchase.json`<br>`TD-TC-003043.step2.return.json`<br>`TD-TC-003043.step3.totals.json` | p.78, p.79, p.89, p.512, p.97, p.638 |
| BR1_TS6_TC20 | TC-003044 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003044.step1.purchase.json`<br>`TD-TC-003044.step2.timeout-reversal.json`<br>`TD-TC-003044.step3.totals.json` | p.78, p.79, p.40, p.512, p.97, p.638 |
| BR1_TS6_TC21 | TC-003045 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003045.step1.purchase.json`<br>`TD-TC-003045.step2.totals.json` | p.79, p.512, p.97, p.638 |
| BR1_TS6_TC22 | TC-003046 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003046.step1.purchase.json`<br>`TD-TC-003046.step2.totals.json` | p.79, p.512, p.97, p.638 |
| BR1_TS6_TC23 | TC-003054 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003054.step1.purchase.json`<br>`TD-TC-003054.step2.totals.json`<br>`TD-TC-003054.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.638 |
| BR1_TS6_TC24 | TC-003055 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003055.step1.purchase.json`<br>`TD-TC-003055.step2.totals.json`<br>`TD-TC-003055.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97, p.638 |
| BR1_TS6_TC25 | TC-003047 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003047.json` | p.40, p.512, p.97, p.638 |
| BR1_TS6_TC26 | TC-003048 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003048.step1.purchase.json`<br>`TD-TC-003048.step2.reversal.json`<br>`TD-TC-003048.step3.timeout-reversal.json` | p.40, p.512, p.97, p.638 |
| BR1_TS6_TC27 | TC-003056 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003056.json` | p.404, p.512, p.97, p.638 |
| BR1_TS6_TC28 | TC-003057 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97, p.638 |
| BR1_TS6_TC29 | TC-003058 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.638 |

### BR1_TS7 — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-003064 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-003064.json` | p.545, p.512, p.97 |
| BR1_TS7_TC2 | TC-003090 | T1 | T1-DEBIT-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-003090.json` | p.97, p.375, p.512 |
| BR1_TS7_TC3 | TC-003089 | T2 | T2-DEBIT-EXPIRED-ONLINE | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003089.json` | p.96, p.512, p.97 |
| BR1_TS7_TC4 | TC-003091 | T4 | T4-DEBIT-CASHBACK-PARTIAL | pending_sme | 1. Purchase/Capture [0] | ASSERT F (p.97) | `TD-TC-003091.json` | p.97, p.98, p.512 |
| BR1_TS7_TC5 | TC-003092 | T4 | T4-DEBIT-PARTIAL-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.98) | `TD-TC-003092.step1.purchase.json`<br>`TD-TC-003092.step2.reversal.json` | p.97, p.98, p.512 |
| BR1_TS7_TC6 | TC-003078 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003078.step1.purchase.json`<br>`TD-TC-003078.step2.next-transaction.json` | p.40, p.409, p.512, p.97 |
| BR1_TS7_TC7 | TC-003079 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003079.step1.purchase.json`<br>`TD-TC-003079.step2.reversal.json`<br>`TD-TC-003079.step3.return.json` | PIPS p.30, p.546, p.512, p.97 |
| BR1_TS7_TC8 | TC-003088 | T5 | T5-DEBIT-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-003088.step1.purchase.json`<br>`TD-TC-003088.step2.reversal.json` | p.546, p.96, p.105, p.512, p.97 |
| BR1_TS7_TC9 | TC-003065 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003065.step1.purchase.json`<br>`TD-TC-003065.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS7_TC10 | TC-003066 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003066.step1.purchase.json`<br>`TD-TC-003066.step2.timeout-reversal.json` | p.39, p.40, p.512, p.97 |
| BR1_TS7_TC11 | TC-003067 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-003067.step1.purchase.json`<br>`TD-TC-003067.step2.timeout-reversal.json` | p.39, p.40, p.45, p.512, p.97 |
| BR1_TS7_TC12 | TC-003068 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003068.step1.purchase.json`<br>`TD-TC-003068.step2.timeout-reversal.json`<br>`TD-TC-003068.step3.next-transaction.json` | p.39, p.40, p.512, p.97 |
| BR1_TS7_TC13 | TC-003069 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-003069.step1.purchase.json`<br>`TD-TC-003069.step2.timeout-reversal.json`<br>`TD-TC-003069.step3.tor-retry.json`<br>`TD-TC-003069.step4.tor-retry.json` | p.39, p.40, p.47, p.512, p.97 |
| BR1_TS7_TC14 | TC-003080 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003080.step1.purchase.json`<br>`TD-TC-003080.step2.timeout-reversal.json`<br>`TD-TC-003080.step3.next-transaction.json` | p.40, p.187, p.512, p.97 |
| BR1_TS7_TC15 | TC-003081 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003081.step1.purchase.json`<br>`TD-TC-003081.step2.resubmitted.json` | p.549, p.40, p.512, p.97 |
| BR1_TS7_TC16 | TC-003082 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003082.step1.purchase.json`<br>`TD-TC-003082.step2.timeout-reversal.json`<br>`TD-TC-003082.step3.resubmitted.json` | p.43, p.40, p.512, p.97 |
| BR1_TS7_TC17 | TC-003070 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003070.step1.purchase.json`<br>`TD-TC-003070.step2.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS7_TC18 | TC-003071 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003071.step1.purchase.json`<br>`TD-TC-003071.step2.reversal.json`<br>`TD-TC-003071.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS7_TC19 | TC-003072 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003072.step1.purchase.json`<br>`TD-TC-003072.step2.return.json`<br>`TD-TC-003072.step3.totals.json` | p.78, p.79, p.89, p.512, p.97 |
| BR1_TS7_TC20 | TC-003073 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003073.step1.purchase.json`<br>`TD-TC-003073.step2.timeout-reversal.json`<br>`TD-TC-003073.step3.totals.json` | p.78, p.79, p.40, p.512, p.97 |
| BR1_TS7_TC21 | TC-003074 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-003074.step1.purchase.json`<br>`TD-TC-003074.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS7_TC22 | TC-003075 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-003075.step1.purchase.json`<br>`TD-TC-003075.step2.totals.json` | p.79, p.512, p.97 |
| BR1_TS7_TC23 | TC-003083 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003083.step1.purchase.json`<br>`TD-TC-003083.step2.totals.json`<br>`TD-TC-003083.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS7_TC24 | TC-003084 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-003084.step1.purchase.json`<br>`TD-TC-003084.step2.totals.json`<br>`TD-TC-003084.step3.return.json` | p.79, p.546, PIPS p.29, p.512, p.97 |
| BR1_TS7_TC25 | TC-003076 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003076.json` | p.40, p.512, p.97 |
| BR1_TS7_TC26 | TC-003077 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-003077.step1.purchase.json`<br>`TD-TC-003077.step2.reversal.json`<br>`TD-TC-003077.step3.timeout-reversal.json` | p.40, p.512, p.97 |
| BR1_TS7_TC27 | TC-003085 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-003085.json` | p.404, p.512, p.97 |
| BR1_TS7_TC28 | TC-003086 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.512, p.97 |
| BR1_TS7_TC29 | TC-003087 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS8 — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-003093 | T1 | T1-AUT | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003093.json` | p.545, p.512, p.97, p.161 |
| BR1_TS8_TC2 | TC-003094 | T5 | T5-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003094.step1.auth-only.json`<br>`TD-TC-003094.step2.completion.json` | p.545, p.512, p.97, p.161 |
| BR1_TS8_TC3 | TC-003098 | T5 | T5-DEBIT-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003098.step1.auth-only.json`<br>`TD-TC-003098.step2.cancellation.json` | p.99, p.512, p.97, p.161 |
| BR1_TS8_TC4 | TC-003099 | T5 | T5-DEBIT-COMPLETION-RESEND | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003099.step1.auth-only.json`<br>`TD-TC-003099.step2.completion.json`<br>`TD-TC-003099.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.161 |
| BR1_TS8_TC5 | TC-003095 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003095.step1.auth-only.json`<br>`TD-TC-003095.step2.completion.json`<br>`TD-TC-003095.step3.totals.json` | p.78, p.79, p.512, p.97, p.161 |
| BR1_TS8_TC6 | TC-003096 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003096.step1.auth-only.json`<br>`TD-TC-003096.step2.timeout-reversal.json` | p.47, p.512, p.97, p.161 |
| BR1_TS8_TC7 | TC-003097 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS9 — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-003100 | T1 | T1-AUT | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003100.json` | p.545, p.512, p.97, p.161 |
| BR1_TS9_TC2 | TC-003101 | T2 | T2-CL-TO-CONTACT | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003101.json` | p.638, p.512, p.97, p.161 |
| BR1_TS9_TC3 | TC-003102 | T5 | T5-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003102.step1.auth-only.json`<br>`TD-TC-003102.step2.completion.json` | p.545, p.512, p.97, p.161 |
| BR1_TS9_TC4 | TC-003106 | T5 | T5-DEBIT-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003106.step1.auth-only.json`<br>`TD-TC-003106.step2.cancellation.json` | p.99, p.512, p.97, p.161 |
| BR1_TS9_TC5 | TC-003107 | T5 | T5-DEBIT-COMPLETION-RESEND | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003107.step1.auth-only.json`<br>`TD-TC-003107.step2.completion.json`<br>`TD-TC-003107.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.161 |
| BR1_TS9_TC6 | TC-003103 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003103.step1.auth-only.json`<br>`TD-TC-003103.step2.completion.json`<br>`TD-TC-003103.step3.totals.json` | p.78, p.79, p.512, p.97, p.161 |
| BR1_TS9_TC7 | TC-003104 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003104.step1.auth-only.json`<br>`TD-TC-003104.step2.timeout-reversal.json` | p.47, p.512, p.97, p.161 |
| BR1_TS9_TC8 | TC-003105 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS10 — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-003108 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003108.json` | p.545, p.512, p.97, p.81 |
| BR1_TS10_TC2 | TC-003109 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003109.step1.auth-only.json`<br>`TD-TC-003109.step2.completion.json` | p.545, p.512, p.97, p.81 |
| BR1_TS10_TC3 | TC-003113 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003113.step1.auth-only.json`<br>`TD-TC-003113.step2.cancellation.json` | p.99, p.512, p.97, p.81 |
| BR1_TS10_TC4 | TC-003114 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003114.step1.auth-only.json`<br>`TD-TC-003114.step2.completion.json`<br>`TD-TC-003114.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.81 |
| BR1_TS10_TC5 | TC-003110 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003110.step1.auth-only.json`<br>`TD-TC-003110.step2.completion.json`<br>`TD-TC-003110.step3.totals.json` | p.78, p.79, p.512, p.97, p.81 |
| BR1_TS10_TC6 | TC-003111 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003111.step1.auth-only.json`<br>`TD-TC-003111.step2.timeout-reversal.json` | p.47, p.512, p.97, p.81 |
| BR1_TS10_TC7 | TC-003112 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.81 |

### BR1_TS11 — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-003115 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003115.json` | p.545, p.512, p.97 |
| BR1_TS11_TC2 | TC-003116 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003116.step1.auth-only.json`<br>`TD-TC-003116.step2.completion.json` | p.545, p.512, p.97 |
| BR1_TS11_TC3 | TC-003120 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003120.step1.auth-only.json`<br>`TD-TC-003120.step2.cancellation.json` | p.99, p.512, p.97 |
| BR1_TS11_TC4 | TC-003121 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003121.step1.auth-only.json`<br>`TD-TC-003121.step2.completion.json`<br>`TD-TC-003121.step3.completion-resubmitted.json` | p.96, p.97, p.512 |
| BR1_TS11_TC5 | TC-003117 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003117.step1.auth-only.json`<br>`TD-TC-003117.step2.completion.json`<br>`TD-TC-003117.step3.totals.json` | p.78, p.79, p.512, p.97 |
| BR1_TS11_TC6 | TC-003118 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003118.step1.auth-only.json`<br>`TD-TC-003118.step2.timeout-reversal.json` | p.47, p.512, p.97 |
| BR1_TS11_TC7 | TC-003119 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS12 — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-003122 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003122.json` | p.545, p.512, p.97 |
| BR1_TS12_TC2 | TC-003126 | T2 | T2-CHIP-SWIPED | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003126.json` | p.162, p.638, p.512, p.97 |
| BR1_TS12_TC3 | TC-003123 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003123.step1.auth-only.json`<br>`TD-TC-003123.step2.completion.json` | p.545, p.512, p.97 |
| BR1_TS12_TC4 | TC-003128 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003128.step1.auth-only.json`<br>`TD-TC-003128.step2.cancellation.json` | p.99, p.512, p.97 |
| BR1_TS12_TC5 | TC-003129 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003129.step1.auth-only.json`<br>`TD-TC-003129.step2.completion.json`<br>`TD-TC-003129.step3.completion-resubmitted.json` | p.96, p.97, p.512 |
| BR1_TS12_TC6 | TC-003124 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003124.step1.auth-only.json`<br>`TD-TC-003124.step2.completion.json`<br>`TD-TC-003124.step3.totals.json` | p.78, p.79, p.512, p.97 |
| BR1_TS12_TC7 | TC-003125 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003125.step1.auth-only.json`<br>`TD-TC-003125.step2.timeout-reversal.json` | p.47, p.512, p.97 |
| BR1_TS12_TC8 | TC-003127 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS13 — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-003130 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003130.json` | p.545, p.512, p.97, p.638 |
| BR1_TS13_TC2 | TC-003131 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003131.step1.auth-only.json`<br>`TD-TC-003131.step2.completion.json` | p.545, p.512, p.97, p.638 |
| BR1_TS13_TC3 | TC-003135 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003135.step1.auth-only.json`<br>`TD-TC-003135.step2.cancellation.json` | p.99, p.512, p.97, p.638 |
| BR1_TS13_TC4 | TC-003136 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003136.step1.auth-only.json`<br>`TD-TC-003136.step2.completion.json`<br>`TD-TC-003136.step3.completion-resubmitted.json` | p.96, p.97, p.512, p.638 |
| BR1_TS13_TC5 | TC-003132 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003132.step1.auth-only.json`<br>`TD-TC-003132.step2.completion.json`<br>`TD-TC-003132.step3.totals.json` | p.78, p.79, p.512, p.97, p.638 |
| BR1_TS13_TC6 | TC-003133 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003133.step1.auth-only.json`<br>`TD-TC-003133.step2.timeout-reversal.json` | p.47, p.512, p.97, p.638 |
| BR1_TS13_TC7 | TC-003134 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.638 |

### BR1_TS14 — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-003137 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-003137.json` | p.545, p.512, p.97 |
| BR1_TS14_TC2 | TC-003138 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-003138.step1.auth-only.json`<br>`TD-TC-003138.step2.completion.json` | p.545, p.512, p.97 |
| BR1_TS14_TC3 | TC-003142 | T5 | T5-DEBIT-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.99) | `TD-TC-003142.step1.auth-only.json`<br>`TD-TC-003142.step2.cancellation.json` | p.99, p.512, p.97 |
| BR1_TS14_TC4 | TC-003143 | T5 | T5-DEBIT-COMPLETION-RESEND | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase/Capture [0] | FLAG 1 (p.96) | `TD-TC-003143.step1.auth-only.json`<br>`TD-TC-003143.step2.completion.json`<br>`TD-TC-003143.step3.completion-resubmitted.json` | p.96, p.97, p.512 |
| BR1_TS14_TC5 | TC-003139 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-003139.step1.auth-only.json`<br>`TD-TC-003139.step2.completion.json`<br>`TD-TC-003139.step3.totals.json` | p.78, p.79, p.512, p.97 |
| BR1_TS14_TC6 | TC-003140 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-003140.step1.auth-only.json`<br>`TD-TC-003140.step2.timeout-reversal.json` | p.47, p.512, p.97 |
| BR1_TS14_TC7 | TC-003141 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS15 — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-003144 | T1 | T1-RET | needs_client_input | 1. Merchandise Return [7] | FLAG — | `TD-TC-003144.json` | p.546, p.512, p.97, p.161 |
| BR1_TS15_TC2 | TC-003145 | T5 | T5-RET-VOID | requires_review | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003145.step1.return.json`<br>`TD-TC-003145.step2.void-return.json` | p.546, p.512, p.97, p.161 |
| BR1_TS15_TC3 | TC-003146 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS16 — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-003147 | T1 | T1-RET | needs_client_input | 1. Merchandise Return [7] | FLAG — | `TD-TC-003147.json` | p.546, p.512, p.97, p.161 |
| BR1_TS16_TC2 | TC-003148 | T5 | T5-RET-VOID | requires_review | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003148.step1.return.json`<br>`TD-TC-003148.step2.void-return.json` | p.546, p.512, p.97, p.161 |
| BR1_TS16_TC3 | TC-003149 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.161 |

### BR1_TS17 — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-003150 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003150.json` | p.546, p.512, p.97 |
| BR1_TS17_TC2 | TC-003151 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003151.step1.return.json`<br>`TD-TC-003151.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS17_TC3 | TC-003152 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS18 — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-003153 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003153.json` | p.546, p.512, p.97 |
| BR1_TS18_TC2 | TC-003154 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003154.step1.return.json`<br>`TD-TC-003154.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS18_TC3 | TC-003155 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS19 — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-003156 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003156.json` | p.546, p.512, p.97 |
| BR1_TS19_TC2 | TC-003157 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003157.step1.return.json`<br>`TD-TC-003157.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS19_TC3 | TC-003158 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS20 — BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-003159 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003159.json` | p.546, p.512, p.97, p.638 |
| BR1_TS20_TC2 | TC-003160 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003160.step1.return.json`<br>`TD-TC-003160.step2.void-return.json` | p.546, p.512, p.97, p.638 |
| BR1_TS20_TC3 | TC-003161 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97, p.638 |

### BR1_TS21 — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-003162 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-003162.json` | p.546, p.512, p.97 |
| BR1_TS21_TC2 | TC-003163 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-003163.step1.return.json`<br>`TD-TC-003163.step2.void-return.json` | p.546, p.512, p.97 |
| BR1_TS21_TC3 | TC-003164 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.512, p.97 |

### BR1_TS22 — BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

### BR1_TS23 — BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

### BR1_TS24 — BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

### BR1_TS25 — BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

### BR1_TS26 — BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_FALLBACK`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

### BR1_TS27 — BUYPASS ATL105 Interlink Debit Balance Inquiry – Credential on File

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

### BR1_TS28 — BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device

Scenario `TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-EAD513C5`).

_No test case._ Reason: no check planned.

## 4. Follow-on combinations → test cases

Follow-on categories (reversal, time-out reversal, cancellation, void, ...) are steps inside the flow test cases of their original scenario, not scenarios of their own. A combination is traced to every test case of the same entry mode that has a later step of that category.

| Leg | Combination | Category | Entry mode | Status | Flows (spec) | Exercised by |
|---|---|---|---|---|---|---|
| BR1_LEG1 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-EMV_CONTACT` | Void of a Merchandise Return | EMV Contact Chip | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-EMV-RETURN-VOID] | BR1_TS15_TC2 |
| BR1_LEG2 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-EMV_CONTACTLESS` | Void of a Merchandise Return | EMV Contactless Chip | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-EMV-RETURN-VOID] | BR1_TS16_TC2 |
| BR1_LEG3 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-MAGSTRIPE` | Void of a Merchandise Return | Magnetic Stripe | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS17_TC2 |
| BR1_LEG4 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-CONTACTLESS_MAGSTRIPE` | Void of a Merchandise Return | Contactless Magnetic Stripe | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS18_TC2 |
| BR1_LEG5 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-EMV_FALLBACK` | Void of a Merchandise Return | Chip Fallback | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS19_TC2 |
| BR1_LEG6 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-CREDENTIAL_ON_FILE` | Void of a Merchandise Return | Credential on File | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS20_TC2 |
| BR1_LEG7 | `TS-ATL105-DEBIT-INTERLINK-VOID_RETURN-CONTACTLESS_MOBILE` | Void of a Merchandise Return | Contactless Mobile Commerce Device | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS21_TC2 |
| BR1_LEG8 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-EMV_CONTACT` | Purchase Reversal | EMV Contact Chip | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-EMV-SALE-VOID] | BR1_TS1_TC5, BR1_TS1_TC7, BR1_TS1_TC8, BR1_TS1_TC18, BR1_TS1_TC26 |
| BR1_LEG9 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-EMV_CONTACTLESS` | Purchase Reversal | EMV Contactless Chip | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-EMV-SALE-VOID] | BR1_TS2_TC6, BR1_TS2_TC8, BR1_TS2_TC9, BR1_TS2_TC19, BR1_TS2_TC27 |
| BR1_LEG10 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-MAGSTRIPE` | Purchase Reversal | Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS3_TC5, BR1_TS3_TC7, BR1_TS3_TC8, BR1_TS3_TC18, BR1_TS3_TC26 |
| BR1_LEG11 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-CONTACTLESS_MAGSTRIPE` | Purchase Reversal | Contactless Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS4_TC5, BR1_TS4_TC7, BR1_TS4_TC8, BR1_TS4_TC18, BR1_TS4_TC26 |
| BR1_LEG12 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-EMV_FALLBACK` | Purchase Reversal | Chip Fallback | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS5_TC6, BR1_TS5_TC8, BR1_TS5_TC9, BR1_TS5_TC19, BR1_TS5_TC27 |
| BR1_LEG13 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-CREDENTIAL_ON_FILE` | Purchase Reversal | Credential on File | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS6_TC5, BR1_TS6_TC7, BR1_TS6_TC8, BR1_TS6_TC18, BR1_TS6_TC26 |
| BR1_LEG14 | `TS-ATL105-DEBIT-INTERLINK-REVERSAL-CONTACTLESS_MOBILE` | Purchase Reversal | Contactless Mobile Commerce Device | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS7_TC5, BR1_TS7_TC7, BR1_TS7_TC8, BR1_TS7_TC18, BR1_TS7_TC26 |
| BR1_LEG15 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-EMV_CONTACT` | Time-out Reversal | EMV Contact Chip | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-EMV-TIMEOUT-REVERSAL] | BR1_TS1_TC9, BR1_TS1_TC10, BR1_TS1_TC11, BR1_TS1_TC12, BR1_TS1_TC13, BR1_TS1_TC14, BR1_TS1_TC16, BR1_TS1_TC20, BR1_TS1_TC26, BR1_TS8_TC6 |
| BR1_LEG16 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-EMV_CONTACTLESS` | Time-out Reversal | EMV Contactless Chip | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-EMV-TIMEOUT-REVERSAL] | BR1_TS2_TC10, BR1_TS2_TC11, BR1_TS2_TC12, BR1_TS2_TC13, BR1_TS2_TC14, BR1_TS2_TC15, BR1_TS2_TC17, BR1_TS2_TC21, BR1_TS2_TC27, BR1_TS9_TC7 |
| BR1_LEG17 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-MAGSTRIPE` | Time-out Reversal | Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS3_TC9, BR1_TS3_TC10, BR1_TS3_TC11, BR1_TS3_TC12, BR1_TS3_TC13, BR1_TS3_TC14, BR1_TS3_TC16, BR1_TS3_TC20, BR1_TS3_TC26, BR1_TS10_TC6 |
| BR1_LEG18 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-CONTACTLESS_MAGSTRIPE` | Time-out Reversal | Contactless Magnetic Stripe | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS4_TC9, BR1_TS4_TC10, BR1_TS4_TC11, BR1_TS4_TC12, BR1_TS4_TC13, BR1_TS4_TC14, BR1_TS4_TC16, BR1_TS4_TC20, BR1_TS4_TC26, BR1_TS11_TC6 |
| BR1_LEG19 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-EMV_FALLBACK` | Time-out Reversal | Chip Fallback | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS5_TC10, BR1_TS5_TC11, BR1_TS5_TC12, BR1_TS5_TC13, BR1_TS5_TC14, BR1_TS5_TC15, BR1_TS5_TC17, BR1_TS5_TC21, BR1_TS5_TC27, BR1_TS12_TC7 |
| BR1_LEG20 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-CREDENTIAL_ON_FILE` | Time-out Reversal | Credential on File | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS6_TC9, BR1_TS6_TC10, BR1_TS6_TC11, BR1_TS6_TC12, BR1_TS6_TC13, BR1_TS6_TC14, BR1_TS6_TC16, BR1_TS6_TC20, BR1_TS6_TC26, BR1_TS13_TC6 |
| BR1_LEG21 | `TS-ATL105-DEBIT-INTERLINK-TIMEOUT_REVERSAL-CONTACTLESS_MOBILE` | Time-out Reversal | Contactless Mobile Commerce Device | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS7_TC9, BR1_TS7_TC10, BR1_TS7_TC11, BR1_TS7_TC12, BR1_TS7_TC13, BR1_TS7_TC14, BR1_TS7_TC16, BR1_TS7_TC20, BR1_TS7_TC26, BR1_TS14_TC6 |
| BR1_LEG22 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-EMV_CONTACT` | Cancellation | EMV Contact Chip | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-EMV-AUTH-CANCELLATION] | BR1_TS8_TC3 |
| BR1_LEG23 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-EMV_CONTACTLESS` | Cancellation | EMV Contactless Chip | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-EMV-AUTH-CANCELLATION] | BR1_TS9_TC4 |
| BR1_LEG24 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-MAGSTRIPE` | Cancellation | Magnetic Stripe | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS10_TC3 |
| BR1_LEG25 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-CONTACTLESS_MAGSTRIPE` | Cancellation | Contactless Magnetic Stripe | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS11_TC3 |
| BR1_LEG26 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-EMV_FALLBACK` | Cancellation | Chip Fallback | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS12_TC4 |
| BR1_LEG27 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-CREDENTIAL_ON_FILE` | Cancellation | Credential on File | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS13_TC3 |
| BR1_LEG28 | `TS-ATL105-DEBIT-INTERLINK-CANCELLATION-CONTACTLESS_MOBILE` | Cancellation | Contactless Mobile Commerce Device | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS14_TC3 |

## 5. Gaps and unresolved links

### Scenarios without test cases

- BR1_TS22 BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip: no check planned
- BR1_TS23 BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip: no check planned
- BR1_TS24 BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe: no check planned
- BR1_TS25 BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe: no check planned
- BR1_TS26 BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback: no check planned
- BR1_TS27 BUYPASS ATL105 Interlink Debit Balance Inquiry – Credential on File: no check planned
- BR1_TS28 BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device: no check planned

### Test cases without test data

These test cases have no host message (device-only checks, or blocked by a spec gap), so they have no request test data.

- BR1_TS1_TC28 TC-002909 (device, requires_review): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS1_TC29 TC-002910 (device, ready): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS10_TC7 TC-003112 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS11_TC7 TC-003119 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS12_TC8 TC-003127 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS13_TC7 TC-003134 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS14_TC7 TC-003141 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS15_TC3 TC-003146 (device, ready): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS16_TC3 TC-003149 (device, ready): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS17_TC3 TC-003152 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS18_TC3 TC-003155 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS19_TC3 TC-003158 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS2_TC29 TC-002939 (device, requires_review): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS2_TC30 TC-002940 (device, ready): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS20_TC3 TC-003161 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS21_TC3 TC-003164 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS3_TC28 TC-002968 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS3_TC29 TC-002969 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS3_TC30 TC-002975 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit card, 3 failed swipes → transaction ended (no manual entry)
- BR1_TS4_TC28 TC-002998 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS4_TC29 TC-002999 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS5_TC29 TC-003028 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS5_TC30 TC-003029 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS6_TC28 TC-003057 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS6_TC29 TC-003058 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS7_TC28 TC-003086 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS7_TC29 TC-003087 (device, pending_sme): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS8_TC7 TC-003097 (device, ready): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS9_TC8 TC-003105 (device, ready): BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Preauthorization disabled (card type 168) → not offered at the device

### Missing test data files

- None.

### Unresolved links

- None. Every test case resolves to its scenario and to this Business Requirement.
