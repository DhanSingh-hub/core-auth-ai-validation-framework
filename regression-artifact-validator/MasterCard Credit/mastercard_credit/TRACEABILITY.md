# Traceability matrix — BR1 BUYPASS ATL105 MasterCard Credit

Business Requirement → Test Scenario → Test Case → Test Data.

Generated 2026-10-31 10:24 UTC from this folder's own artifacts only (`requirements_scenarios/business_requirements.json`, `business_scenarios.json`, `follow_on_leg_combinations.csv`, `test_cases/test_cases.jsonl`, `test_cases/test_case_validation.json`, `run_set.json`, `test_data/`). Nothing is inferred. Any link that cannot be resolved is listed in [Unresolved links](#unresolved-links).

## Summary

| Item | Value |
|---|---|
| Business Requirement | `BR-12B94100` (BR1) BUYPASS ATL105 MasterCard Credit |
| Spec | ATL105 2026-4 (`SRC-ATL105-PDF-001`) |
| Selection | platform=BUYPASS, spec=ATL105, network=MASTERCARD, program=CREDIT |
| Dimension mapping | candidate |
| Build | `buypass-atl105-mastercard-credit-bada93f456` |
| Scenarios | 36 (valid: 26, unconfirmed: 8, spec_implied: 2) |
| Scenarios with test cases | 36 / 36 |
| Follow-on combinations (steps inside flow test cases) | 34 |
| Test cases | 450 (ready: 180, needs_client_input: 61, requires_review: 146, pending_sme: 61, blocked_spec_gap: 2) |
| Test cases with test data | 408 / 450 |
| Test data files | 839 listed in run_set.json, 839 present in `test_data/` |
| Missing test data files | 0 |
| Unresolved links | 0 |

### Test cases by type

| Type | Test cases |
|---|---|
| T1 Base transaction | 44 |
| T2 Card capture path | 16 |
| T3 Cardholder verification | 6 |
| T4 Partial approval | 42 |
| T5 Follow-on lifecycle | 70 |
| T6 Time-out & recovery | 75 |
| T7 Stored credential | 5 |
| T8 Settlement | 72 |
| T9 Value-added service | 14 |
| T10 Business-rule decline | 66 |
| T11 Device-only | 40 |

## 1. Business Requirement

**BR1 — BUYPASS ATL105 MasterCard Credit** (`BR-12B94100`), status `built`.

Spec citations for the requirement:

| Source | Page | Quote |
|---|---|---|
| Appendix C | p.511 | 03 MasterCard - Authorizer Type: Credit |
| anchored:A-SALE-PREAUTH-TRACK | p.81 | If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent |
| anchored:A-CREDIT-MANUAL | p.81 | Manual entry is allowed on a credit card transaction |
| anchored:A-CREDIT-TRACK | p.81 | The default track that is sent to the host should be Track 2 if the device supports Track 2 |
| 10.1.2 Supported Transaction Types | p.83 | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only |
| 10.1.2 Supported Transaction Types | p.83 | The following point-of-sale transactions are supported for credit card processing: ... Balance Inquiry |
| 10.1.2 Supported Transaction Types | p.83 | The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Authorization Only |
| 10.1.2 Supported Transaction Types | p.83 | The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Purchase |
| 10.1.2 Supported Transaction Types | p.83 | The following point-of-sale transactions are supported for credit card processing: ... Merchandise Return |
| 10.1.2 Supported Transaction Types | p.83 | The following point-of-sale transactions are supported for credit card processing: ... Purchase/Capture |
| scoped:SR-CREDIT-CONTACTLESS-MAGSTRIPE | p.86 | When applied to credit card processing |
| anchored:A-CREDIT-RFID | p.86 | capture and transmit full, unaltered Track 2 data from a card without requiring the card to have contact with the point-of-sale device |
| scoped:SR-CREDIT-MOTO-MANUAL | p.86 | non-face-to-face environment, such as mail order, phone order |
| 10.14.2.3 Supported Cards | p.161 | The following credit cards are supported for EMV processing: ... MasterCard |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return |
| 10.14.2.4 Supported Transaction Types | p.161 | The following point-of-sale transactions are supported for EMV card processing: ... Purchase |
| anchored:A-CREDIT-FALLBACK | p.162 | If a chip card is presented at a chip capable terminal and a chip transaction cannot be performed, then the merchant may process the chip card as a magnetic stripe read |
| scoped:SR-CREDIT-EMV-FALLBACK | p.162 | the merchant may process the chip card as a magnetic stripe read (swipe) |
| scoped:SR-CREDIT-COF | p.575 | transactions (Authorization, Completion, Cancellation, Reversal etc.) |
| anchored:APPJ-FN2-COF-CREDIT | p.638 | Entry Mode 10 is applicable to Visa Credit, MasterCard Credit, Discover Credit, and Amex transactions |

## 2. Requirement → Scenarios

| Scenario | Name | Status | Test cases | Test data files | Spec basis |
|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid | 37 | 72 | p.511, p.83, p.161 |
| BR1_TS2 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid | 37 | 72 | p.511, p.83, p.161 |
| BR1_TS3 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid | 38 | 74 | p.511, p.83, p.81 |
| BR1_TS4 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid | 33 | 66 | p.511, p.83, p.86 |
| BR1_TS5 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid | 39 | 71 | p.511, p.83, p.81 |
| BR1_TS6 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid | 35 | 69 | p.511, p.83, p.162 |
| BR1_TS7 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File | valid | 35 | 72 | p.511, p.83, p.638, p.575 |
| BR1_TS8 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | 32 | 66 | p.511, p.83 |
| BR1_TS9 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed | valid | 6 | 9 | p.511, p.83, p.81, p.86 |
| BR1_TS10 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File | unconfirmed | 6 | 9 | p.511, p.83, p.638 |
| BR1_TS11 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid | 15 | 29 | p.511, p.83, p.161 |
| BR1_TS12 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid | 15 | 29 | p.511, p.83, p.161 |
| BR1_TS13 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid | 15 | 28 | p.511, p.83, p.81 |
| BR1_TS14 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid | 14 | 27 | p.511, p.83, p.86 |
| BR1_TS15 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid | 15 | 28 | p.511, p.83, p.81 |
| BR1_TS16 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid | 16 | 30 | p.511, p.83, p.162 |
| BR1_TS17 | BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File | valid | 11 | 23 | p.511, p.83, p.638, p.575 |
| BR1_TS18 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | 13 | 27 | p.511, p.83 |
| BR1_TS19 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed | valid | 3 | 3 | p.511, p.83, p.81, p.86 |
| BR1_TS20 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File | unconfirmed | 3 | 3 | p.511, p.83, p.638 |
| BR1_TS21 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip | valid | 3 | 3 | p.511, p.83, p.161 |
| BR1_TS22 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip | valid | 3 | 3 | p.511, p.83, p.161 |
| BR1_TS23 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe | spec_implied | 3 | 3 | p.511, p.83, p.81 |
| BR1_TS24 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe | valid | 3 | 3 | p.511, p.83, p.86 |
| BR1_TS25 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed | valid | 3 | 3 | p.511, p.83, p.81 |
| BR1_TS26 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback | valid | 3 | 3 | p.511, p.83, p.162 |
| BR1_TS27 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File | unconfirmed | 3 | 3 | p.511, p.83, p.638 |
| BR1_TS28 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 3 | 3 | p.511, p.83 |
| BR1_TS29 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip | valid | 1 | 1 | p.511, p.83, p.161 |
| BR1_TS30 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip | valid | 1 | 1 | p.511, p.83, p.161 |
| BR1_TS31 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe | spec_implied | 1 | 1 | p.511, p.83, p.81 |
| BR1_TS32 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe | valid | 1 | 1 | p.511, p.83, p.86 |
| BR1_TS33 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed | valid | 1 | 1 | p.511, p.83, p.81 |
| BR1_TS34 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback | valid | 1 | 1 | p.511, p.83, p.162 |
| BR1_TS35 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File | unconfirmed | 1 | 1 | p.511, p.83, p.638 |
| BR1_TS36 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | 1 | 1 | p.511, p.83 |

## 3. Scenario → Test Case → Test Data

Steps show each step's transaction category and its Appendix G code (or special Prompt Code). Expected = the test case's final response-code expectation (tier, value, spec page). Per-step expectations, rule checks and runtime links are in `test_cases/test_cases.jsonl`.

### BR1_TS1 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-001722 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001722.json` | p.545, p.511, p.83, p.161 |
| BR1_TS1_TC2 | TC-001748 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001748.json` | PIPS p.52, p.511, p.83, p.161 |
| BR1_TS1_TC3 | TC-001723 | T2 | T2-EXPIRED-EMV | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001723.json` | p.160, p.511, p.83, p.161 |
| BR1_TS1_TC4 | TC-001724 | T3 | T3-ONLINE-PIN | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001724.json` | p.162, p.511, p.83, p.161 |
| BR1_TS1_TC5 | TC-001725 | T3 | T3-OFFLINE-PIN | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001725.json` | p.162, p.511, p.83, p.161 |
| BR1_TS1_TC6 | TC-001726 | T4 | T4-PUR-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001726.step1.purchase.json`<br>`TD-TC-001726.step2.split-tender.json` | p.84, p.87, p.511, p.83, p.161 |
| BR1_TS1_TC7 | TC-001727 | T4 | T4-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001727.step1.purchase.json`<br>`TD-TC-001727.step2.reversal.json` | p.84, p.511, p.83, p.161 |
| BR1_TS1_TC8 | TC-001728 | T4 | T4-PUR-TOR | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001728.step1.purchase.json`<br>`TD-TC-001728.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83, p.161 |
| BR1_TS1_TC9 | TC-001729 | T4 | T4-PUR-NO-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001729.json` | p.87, p.511, p.83, p.161 |
| BR1_TS1_TC10 | TC-001730 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001730.step1.purchase.json`<br>`TD-TC-001730.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83, p.161 |
| BR1_TS1_TC11 | TC-001749 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001749.step1.purchase.json`<br>`TD-TC-001749.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.161 |
| BR1_TS1_TC12 | TC-001750 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001750.step1.purchase.json`<br>`TD-TC-001750.step2.reversal.json`<br>`TD-TC-001750.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.161 |
| BR1_TS1_TC13 | TC-001731 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001731.step1.purchase.json`<br>`TD-TC-001731.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.161 |
| BR1_TS1_TC14 | TC-001732 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001732.step1.purchase.json`<br>`TD-TC-001732.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.161 |
| BR1_TS1_TC15 | TC-001733 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001733.step1.purchase.json`<br>`TD-TC-001733.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.161 |
| BR1_TS1_TC16 | TC-001734 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001734.step1.purchase.json`<br>`TD-TC-001734.step2.timeout-reversal.json`<br>`TD-TC-001734.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.161 |
| BR1_TS1_TC17 | TC-001735 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001735.step1.purchase.json`<br>`TD-TC-001735.step2.timeout-reversal.json`<br>`TD-TC-001735.step3.tor-retry.json`<br>`TD-TC-001735.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.161 |
| BR1_TS1_TC18 | TC-001751 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001751.step1.purchase.json`<br>`TD-TC-001751.step2.timeout-reversal.json`<br>`TD-TC-001751.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.161 |
| BR1_TS1_TC19 | TC-001752 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001752.step1.purchase.json`<br>`TD-TC-001752.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.161 |
| BR1_TS1_TC20 | TC-001753 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001753.step1.purchase.json`<br>`TD-TC-001753.step2.timeout-reversal.json`<br>`TD-TC-001753.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.161 |
| BR1_TS1_TC21 | TC-001736 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001736.step1.purchase.json`<br>`TD-TC-001736.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.161 |
| BR1_TS1_TC22 | TC-001737 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001737.step1.purchase.json`<br>`TD-TC-001737.step2.reversal.json`<br>`TD-TC-001737.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.161 |
| BR1_TS1_TC23 | TC-001738 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001738.step1.purchase.json`<br>`TD-TC-001738.step2.return.json`<br>`TD-TC-001738.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.161 |
| BR1_TS1_TC24 | TC-001739 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001739.step1.purchase.json`<br>`TD-TC-001739.step2.timeout-reversal.json`<br>`TD-TC-001739.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.161 |
| BR1_TS1_TC25 | TC-001740 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001740.step1.purchase.json`<br>`TD-TC-001740.step2.totals.json` | p.79, p.511, p.83, p.161 |
| BR1_TS1_TC26 | TC-001741 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001741.step1.purchase.json`<br>`TD-TC-001741.step2.totals.json` | p.79, p.511, p.83, p.161 |
| BR1_TS1_TC27 | TC-001754 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001754.step1.purchase.json`<br>`TD-TC-001754.step2.totals.json`<br>`TD-TC-001754.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.161 |
| BR1_TS1_TC28 | TC-001755 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001755.step1.purchase.json`<br>`TD-TC-001755.step2.totals.json`<br>`TD-TC-001755.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.161 |
| BR1_TS1_TC29 | TC-001742 | T9 | T9-FSA | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001742.json` | p.89, p.88, p.511, p.83, p.161 |
| BR1_TS1_TC30 | TC-001743 | T9 | T9-FSA-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001743.step1.purchase.json`<br>`TD-TC-001743.step2.split-tender.json` | p.89, p.88, p.87, p.511, p.83, p.161 |
| BR1_TS1_TC31 | TC-001744 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001744.json` | p.40, p.511, p.83, p.161 |
| BR1_TS1_TC32 | TC-001745 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001745.step1.purchase.json`<br>`TD-TC-001745.step2.reversal.json`<br>`TD-TC-001745.step3.timeout-reversal.json` | p.40, p.511, p.83, p.161 |
| BR1_TS1_TC33 | TC-001746 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001746.step1.purchase.json`<br>`TD-TC-001746.step2.reversal.json` | p.84, p.511, p.83, p.161 |
| BR1_TS1_TC34 | TC-001747 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001747.json` | p.160, p.511, p.83, p.161 |
| BR1_TS1_TC35 | TC-001756 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001756.json` | p.404, p.511, p.83, p.161 |
| BR1_TS1_TC36 | TC-001757 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.161 |
| BR1_TS1_TC37 | TC-001758 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.161 |

### BR1_TS2 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-001759 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001759.json` | p.545, p.511, p.83, p.161 |
| BR1_TS2_TC2 | TC-001785 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001785.json` | PIPS p.52, p.511, p.83, p.161 |
| BR1_TS2_TC3 | TC-001760 | T2 | T2-CL-TO-CONTACT | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001760.json` | p.638, p.511, p.83, p.161 |
| BR1_TS2_TC4 | TC-001761 | T2 | T2-EXPIRED-EMV | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001761.json` | p.160, p.511, p.83, p.161 |
| BR1_TS2_TC5 | TC-001762 | T3 | T3-ONLINE-PIN | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001762.json` | p.162, p.511, p.83, p.161 |
| BR1_TS2_TC6 | TC-001763 | T4 | T4-PUR-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001763.step1.purchase.json`<br>`TD-TC-001763.step2.split-tender.json` | p.84, p.87, p.511, p.83, p.161 |
| BR1_TS2_TC7 | TC-001764 | T4 | T4-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001764.step1.purchase.json`<br>`TD-TC-001764.step2.reversal.json` | p.84, p.511, p.83, p.161 |
| BR1_TS2_TC8 | TC-001765 | T4 | T4-PUR-TOR | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001765.step1.purchase.json`<br>`TD-TC-001765.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83, p.161 |
| BR1_TS2_TC9 | TC-001766 | T4 | T4-PUR-NO-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001766.json` | p.87, p.511, p.83, p.161 |
| BR1_TS2_TC10 | TC-001767 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001767.step1.purchase.json`<br>`TD-TC-001767.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83, p.161 |
| BR1_TS2_TC11 | TC-001786 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001786.step1.purchase.json`<br>`TD-TC-001786.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.161 |
| BR1_TS2_TC12 | TC-001787 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001787.step1.purchase.json`<br>`TD-TC-001787.step2.reversal.json`<br>`TD-TC-001787.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.161 |
| BR1_TS2_TC13 | TC-001768 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001768.step1.purchase.json`<br>`TD-TC-001768.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.161 |
| BR1_TS2_TC14 | TC-001769 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001769.step1.purchase.json`<br>`TD-TC-001769.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.161 |
| BR1_TS2_TC15 | TC-001770 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001770.step1.purchase.json`<br>`TD-TC-001770.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.161 |
| BR1_TS2_TC16 | TC-001771 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001771.step1.purchase.json`<br>`TD-TC-001771.step2.timeout-reversal.json`<br>`TD-TC-001771.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.161 |
| BR1_TS2_TC17 | TC-001772 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001772.step1.purchase.json`<br>`TD-TC-001772.step2.timeout-reversal.json`<br>`TD-TC-001772.step3.tor-retry.json`<br>`TD-TC-001772.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.161 |
| BR1_TS2_TC18 | TC-001788 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001788.step1.purchase.json`<br>`TD-TC-001788.step2.timeout-reversal.json`<br>`TD-TC-001788.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.161 |
| BR1_TS2_TC19 | TC-001789 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001789.step1.purchase.json`<br>`TD-TC-001789.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.161 |
| BR1_TS2_TC20 | TC-001790 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001790.step1.purchase.json`<br>`TD-TC-001790.step2.timeout-reversal.json`<br>`TD-TC-001790.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.161 |
| BR1_TS2_TC21 | TC-001773 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001773.step1.purchase.json`<br>`TD-TC-001773.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.161 |
| BR1_TS2_TC22 | TC-001774 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001774.step1.purchase.json`<br>`TD-TC-001774.step2.reversal.json`<br>`TD-TC-001774.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.161 |
| BR1_TS2_TC23 | TC-001775 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001775.step1.purchase.json`<br>`TD-TC-001775.step2.return.json`<br>`TD-TC-001775.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.161 |
| BR1_TS2_TC24 | TC-001776 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001776.step1.purchase.json`<br>`TD-TC-001776.step2.timeout-reversal.json`<br>`TD-TC-001776.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.161 |
| BR1_TS2_TC25 | TC-001777 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001777.step1.purchase.json`<br>`TD-TC-001777.step2.totals.json` | p.79, p.511, p.83, p.161 |
| BR1_TS2_TC26 | TC-001778 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001778.step1.purchase.json`<br>`TD-TC-001778.step2.totals.json` | p.79, p.511, p.83, p.161 |
| BR1_TS2_TC27 | TC-001791 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001791.step1.purchase.json`<br>`TD-TC-001791.step2.totals.json`<br>`TD-TC-001791.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.161 |
| BR1_TS2_TC28 | TC-001792 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001792.step1.purchase.json`<br>`TD-TC-001792.step2.totals.json`<br>`TD-TC-001792.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.161 |
| BR1_TS2_TC29 | TC-001779 | T9 | T9-FSA | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001779.json` | p.89, p.88, p.511, p.83, p.161 |
| BR1_TS2_TC30 | TC-001780 | T9 | T9-FSA-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001780.step1.purchase.json`<br>`TD-TC-001780.step2.split-tender.json` | p.89, p.88, p.87, p.511, p.83, p.161 |
| BR1_TS2_TC31 | TC-001781 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001781.json` | p.40, p.511, p.83, p.161 |
| BR1_TS2_TC32 | TC-001782 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001782.step1.purchase.json`<br>`TD-TC-001782.step2.reversal.json`<br>`TD-TC-001782.step3.timeout-reversal.json` | p.40, p.511, p.83, p.161 |
| BR1_TS2_TC33 | TC-001783 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001783.step1.purchase.json`<br>`TD-TC-001783.step2.reversal.json` | p.84, p.511, p.83, p.161 |
| BR1_TS2_TC34 | TC-001784 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001784.json` | p.160, p.511, p.83, p.161 |
| BR1_TS2_TC35 | TC-001793 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001793.json` | p.404, p.511, p.83, p.161 |
| BR1_TS2_TC36 | TC-001794 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.161 |
| BR1_TS2_TC37 | TC-001795 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.161 |

### BR1_TS3 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-MAGSTRIPE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-001796 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001796.json` | p.545, p.511, p.83, p.81 |
| BR1_TS3_TC2 | TC-001822 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001822.json` | PIPS p.52, p.511, p.83, p.81 |
| BR1_TS3_TC3 | TC-001797 | T2 | T2-TRACK1 | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001797.json` | p.81, p.365, p.511, p.83 |
| BR1_TS3_TC4 | TC-001798 | T2 | T2-RFID-TERMINAL | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001798.json` | p.86, p.639, p.511, p.83, p.81 |
| BR1_TS3_TC5 | TC-001799 | T4 | T4-PUR-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001799.step1.purchase.json`<br>`TD-TC-001799.step2.split-tender.json` | p.84, p.87, p.511, p.83, p.81 |
| BR1_TS3_TC6 | TC-001800 | T4 | T4-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001800.step1.purchase.json`<br>`TD-TC-001800.step2.reversal.json` | p.84, p.511, p.83, p.81 |
| BR1_TS3_TC7 | TC-001801 | T4 | T4-PUR-TOR | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001801.step1.purchase.json`<br>`TD-TC-001801.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83, p.81 |
| BR1_TS3_TC8 | TC-001802 | T4 | T4-PUR-NO-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001802.json` | p.87, p.511, p.83, p.81 |
| BR1_TS3_TC9 | TC-001803 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001803.step1.purchase.json`<br>`TD-TC-001803.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83, p.81 |
| BR1_TS3_TC10 | TC-001823 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001823.step1.purchase.json`<br>`TD-TC-001823.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.81 |
| BR1_TS3_TC11 | TC-001824 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001824.step1.purchase.json`<br>`TD-TC-001824.step2.reversal.json`<br>`TD-TC-001824.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.81 |
| BR1_TS3_TC12 | TC-001804 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001804.step1.purchase.json`<br>`TD-TC-001804.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS3_TC13 | TC-001805 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001805.step1.purchase.json`<br>`TD-TC-001805.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS3_TC14 | TC-001806 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001806.step1.purchase.json`<br>`TD-TC-001806.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.81 |
| BR1_TS3_TC15 | TC-001807 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001807.step1.purchase.json`<br>`TD-TC-001807.step2.timeout-reversal.json`<br>`TD-TC-001807.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS3_TC16 | TC-001808 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001808.step1.purchase.json`<br>`TD-TC-001808.step2.timeout-reversal.json`<br>`TD-TC-001808.step3.tor-retry.json`<br>`TD-TC-001808.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.81 |
| BR1_TS3_TC17 | TC-001825 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001825.step1.purchase.json`<br>`TD-TC-001825.step2.timeout-reversal.json`<br>`TD-TC-001825.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.81 |
| BR1_TS3_TC18 | TC-001826 | T6 | T6-PUR-STAND-IN | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001826.step1.purchase.json`<br>`TD-TC-001826.step2.timeout-reversal.json`<br>`TD-TC-001826.step3.deferred-purchase.json` | p.40, p.95, p.588, p.511, p.83, p.81 |
| BR1_TS3_TC19 | TC-001827 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001827.step1.purchase.json`<br>`TD-TC-001827.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.81 |
| BR1_TS3_TC20 | TC-001828 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001828.step1.purchase.json`<br>`TD-TC-001828.step2.timeout-reversal.json`<br>`TD-TC-001828.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.81 |
| BR1_TS3_TC21 | TC-001809 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001809.step1.purchase.json`<br>`TD-TC-001809.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.81 |
| BR1_TS3_TC22 | TC-001810 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001810.step1.purchase.json`<br>`TD-TC-001810.step2.reversal.json`<br>`TD-TC-001810.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.81 |
| BR1_TS3_TC23 | TC-001811 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001811.step1.purchase.json`<br>`TD-TC-001811.step2.return.json`<br>`TD-TC-001811.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.81 |
| BR1_TS3_TC24 | TC-001812 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001812.step1.purchase.json`<br>`TD-TC-001812.step2.timeout-reversal.json`<br>`TD-TC-001812.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.81 |
| BR1_TS3_TC25 | TC-001813 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001813.step1.purchase.json`<br>`TD-TC-001813.step2.totals.json` | p.79, p.511, p.83, p.81 |
| BR1_TS3_TC26 | TC-001814 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001814.step1.purchase.json`<br>`TD-TC-001814.step2.totals.json` | p.79, p.511, p.83, p.81 |
| BR1_TS3_TC27 | TC-001829 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001829.step1.purchase.json`<br>`TD-TC-001829.step2.totals.json`<br>`TD-TC-001829.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.81 |
| BR1_TS3_TC28 | TC-001830 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001830.step1.purchase.json`<br>`TD-TC-001830.step2.totals.json`<br>`TD-TC-001830.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.81 |
| BR1_TS3_TC29 | TC-001815 | T9 | T9-FSA | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001815.json` | p.89, p.88, p.511, p.83, p.81 |
| BR1_TS3_TC30 | TC-001816 | T9 | T9-FSA-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001816.step1.purchase.json`<br>`TD-TC-001816.step2.split-tender.json` | p.89, p.88, p.87, p.511, p.83, p.81 |
| BR1_TS3_TC31 | TC-001817 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001817.json` | p.40, p.511, p.83, p.81 |
| BR1_TS3_TC32 | TC-001818 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001818.step1.purchase.json`<br>`TD-TC-001818.step2.reversal.json`<br>`TD-TC-001818.step3.timeout-reversal.json` | p.40, p.511, p.83, p.81 |
| BR1_TS3_TC33 | TC-001819 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001819.step1.purchase.json`<br>`TD-TC-001819.step2.reversal.json` | p.84, p.511, p.83, p.81 |
| BR1_TS3_TC34 | TC-001820 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001820.json` | p.160, p.511, p.83, p.81 |
| BR1_TS3_TC35 | TC-001831 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001831.json` | p.404, p.511, p.83, p.81 |
| BR1_TS3_TC36 | TC-001821 | T11 | T11-EXPIRED | ready | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83 |
| BR1_TS3_TC37 | TC-001832 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.81 |
| BR1_TS3_TC38 | TC-001833 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |

### BR1_TS4 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-001834 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001834.json` | p.545, p.511, p.83, p.86 |
| BR1_TS4_TC2 | TC-001856 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001856.json` | PIPS p.52, p.511, p.83, p.86 |
| BR1_TS4_TC3 | TC-001835 | T4 | T4-PUR-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001835.step1.purchase.json`<br>`TD-TC-001835.step2.split-tender.json` | p.84, p.87, p.511, p.83, p.86 |
| BR1_TS4_TC4 | TC-001836 | T4 | T4-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001836.step1.purchase.json`<br>`TD-TC-001836.step2.reversal.json` | p.84, p.511, p.83, p.86 |
| BR1_TS4_TC5 | TC-001837 | T4 | T4-PUR-TOR | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001837.step1.purchase.json`<br>`TD-TC-001837.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83, p.86 |
| BR1_TS4_TC6 | TC-001838 | T4 | T4-PUR-NO-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001838.json` | p.87, p.511, p.83, p.86 |
| BR1_TS4_TC7 | TC-001839 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001839.step1.purchase.json`<br>`TD-TC-001839.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83, p.86 |
| BR1_TS4_TC8 | TC-001857 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001857.step1.purchase.json`<br>`TD-TC-001857.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.86 |
| BR1_TS4_TC9 | TC-001858 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001858.step1.purchase.json`<br>`TD-TC-001858.step2.reversal.json`<br>`TD-TC-001858.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.86 |
| BR1_TS4_TC10 | TC-001840 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001840.step1.purchase.json`<br>`TD-TC-001840.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.86 |
| BR1_TS4_TC11 | TC-001841 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001841.step1.purchase.json`<br>`TD-TC-001841.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.86 |
| BR1_TS4_TC12 | TC-001842 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001842.step1.purchase.json`<br>`TD-TC-001842.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.86 |
| BR1_TS4_TC13 | TC-001843 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001843.step1.purchase.json`<br>`TD-TC-001843.step2.timeout-reversal.json`<br>`TD-TC-001843.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.86 |
| BR1_TS4_TC14 | TC-001844 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001844.step1.purchase.json`<br>`TD-TC-001844.step2.timeout-reversal.json`<br>`TD-TC-001844.step3.tor-retry.json`<br>`TD-TC-001844.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.86 |
| BR1_TS4_TC15 | TC-001859 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001859.step1.purchase.json`<br>`TD-TC-001859.step2.timeout-reversal.json`<br>`TD-TC-001859.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.86 |
| BR1_TS4_TC16 | TC-001860 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001860.step1.purchase.json`<br>`TD-TC-001860.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.86 |
| BR1_TS4_TC17 | TC-001861 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001861.step1.purchase.json`<br>`TD-TC-001861.step2.timeout-reversal.json`<br>`TD-TC-001861.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.86 |
| BR1_TS4_TC18 | TC-001845 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001845.step1.purchase.json`<br>`TD-TC-001845.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.86 |
| BR1_TS4_TC19 | TC-001846 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001846.step1.purchase.json`<br>`TD-TC-001846.step2.reversal.json`<br>`TD-TC-001846.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.86 |
| BR1_TS4_TC20 | TC-001847 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001847.step1.purchase.json`<br>`TD-TC-001847.step2.return.json`<br>`TD-TC-001847.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.86 |
| BR1_TS4_TC21 | TC-001848 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001848.step1.purchase.json`<br>`TD-TC-001848.step2.timeout-reversal.json`<br>`TD-TC-001848.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.86 |
| BR1_TS4_TC22 | TC-001849 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001849.step1.purchase.json`<br>`TD-TC-001849.step2.totals.json` | p.79, p.511, p.83, p.86 |
| BR1_TS4_TC23 | TC-001850 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001850.step1.purchase.json`<br>`TD-TC-001850.step2.totals.json` | p.79, p.511, p.83, p.86 |
| BR1_TS4_TC24 | TC-001862 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001862.step1.purchase.json`<br>`TD-TC-001862.step2.totals.json`<br>`TD-TC-001862.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.86 |
| BR1_TS4_TC25 | TC-001863 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001863.step1.purchase.json`<br>`TD-TC-001863.step2.totals.json`<br>`TD-TC-001863.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.86 |
| BR1_TS4_TC26 | TC-001851 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001851.json` | p.40, p.511, p.83, p.86 |
| BR1_TS4_TC27 | TC-001852 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001852.step1.purchase.json`<br>`TD-TC-001852.step2.reversal.json`<br>`TD-TC-001852.step3.timeout-reversal.json` | p.40, p.511, p.83, p.86 |
| BR1_TS4_TC28 | TC-001853 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001853.step1.purchase.json`<br>`TD-TC-001853.step2.reversal.json` | p.84, p.511, p.83, p.86 |
| BR1_TS4_TC29 | TC-001854 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001854.json` | p.160, p.511, p.83, p.86 |
| BR1_TS4_TC30 | TC-001864 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001864.json` | p.404, p.511, p.83, p.86 |
| BR1_TS4_TC31 | TC-001855 | T11 | T11-EXPIRED | ready | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83, p.86 |
| BR1_TS4_TC32 | TC-001865 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.86 |
| BR1_TS4_TC33 | TC-001866 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.86 |

### BR1_TS5 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-MANUAL`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-001867 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001867.json` | p.545, p.511, p.83, p.81 |
| BR1_TS5_TC2 | TC-001894 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001894.json` | PIPS p.52, p.511, p.83, p.81 |
| BR1_TS5_TC3 | TC-001868 | T2 | T2-3SWIPES | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001868.json` | p.81, p.511, p.83 |
| BR1_TS5_TC4 | TC-001869 | T2 | T2-RFID-TERMINAL | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001869.json` | p.86, p.639, p.511, p.83, p.81 |
| BR1_TS5_TC5 | TC-001870 | T4 | T4-PUR-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001870.step1.purchase.json`<br>`TD-TC-001870.step2.split-tender.json` | p.84, p.87, p.511, p.83, p.81 |
| BR1_TS5_TC6 | TC-001871 | T4 | T4-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001871.step1.purchase.json`<br>`TD-TC-001871.step2.reversal.json` | p.84, p.511, p.83, p.81 |
| BR1_TS5_TC7 | TC-001872 | T4 | T4-PUR-TOR | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001872.step1.purchase.json`<br>`TD-TC-001872.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83, p.81 |
| BR1_TS5_TC8 | TC-001873 | T4 | T4-PUR-NO-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001873.json` | p.87, p.511, p.83, p.81 |
| BR1_TS5_TC9 | TC-001874 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001874.step1.purchase.json`<br>`TD-TC-001874.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83, p.81 |
| BR1_TS5_TC10 | TC-001895 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001895.step1.purchase.json`<br>`TD-TC-001895.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.81 |
| BR1_TS5_TC11 | TC-001896 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001896.step1.purchase.json`<br>`TD-TC-001896.step2.reversal.json`<br>`TD-TC-001896.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.81 |
| BR1_TS5_TC12 | TC-001875 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001875.step1.purchase.json`<br>`TD-TC-001875.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS5_TC13 | TC-001876 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001876.step1.purchase.json`<br>`TD-TC-001876.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS5_TC14 | TC-001877 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001877.step1.purchase.json`<br>`TD-TC-001877.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.81 |
| BR1_TS5_TC15 | TC-001878 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001878.step1.purchase.json`<br>`TD-TC-001878.step2.timeout-reversal.json`<br>`TD-TC-001878.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS5_TC16 | TC-001879 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001879.step1.purchase.json`<br>`TD-TC-001879.step2.timeout-reversal.json`<br>`TD-TC-001879.step3.tor-retry.json`<br>`TD-TC-001879.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.81 |
| BR1_TS5_TC17 | TC-001897 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001897.step1.purchase.json`<br>`TD-TC-001897.step2.timeout-reversal.json`<br>`TD-TC-001897.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.81 |
| BR1_TS5_TC18 | TC-001898 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001898.step1.purchase.json`<br>`TD-TC-001898.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.81 |
| BR1_TS5_TC19 | TC-001899 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001899.step1.purchase.json`<br>`TD-TC-001899.step2.timeout-reversal.json`<br>`TD-TC-001899.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.81 |
| BR1_TS5_TC20 | TC-001880 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001880.step1.purchase.json`<br>`TD-TC-001880.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.81 |
| BR1_TS5_TC21 | TC-001881 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001881.step1.purchase.json`<br>`TD-TC-001881.step2.reversal.json`<br>`TD-TC-001881.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.81 |
| BR1_TS5_TC22 | TC-001882 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001882.step1.purchase.json`<br>`TD-TC-001882.step2.return.json`<br>`TD-TC-001882.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.81 |
| BR1_TS5_TC23 | TC-001883 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001883.step1.purchase.json`<br>`TD-TC-001883.step2.timeout-reversal.json`<br>`TD-TC-001883.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.81 |
| BR1_TS5_TC24 | TC-001884 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001884.step1.purchase.json`<br>`TD-TC-001884.step2.totals.json` | p.79, p.511, p.83, p.81 |
| BR1_TS5_TC25 | TC-001885 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001885.step1.purchase.json`<br>`TD-TC-001885.step2.totals.json` | p.79, p.511, p.83, p.81 |
| BR1_TS5_TC26 | TC-001900 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001900.step1.purchase.json`<br>`TD-TC-001900.step2.totals.json`<br>`TD-TC-001900.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.81 |
| BR1_TS5_TC27 | TC-001901 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001901.step1.purchase.json`<br>`TD-TC-001901.step2.totals.json`<br>`TD-TC-001901.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.81 |
| BR1_TS5_TC28 | TC-001886 | T9 | T9-PUR-AVS | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.86) | `TD-TC-001886.json` | p.86, p.511, p.83, p.81 |
| BR1_TS5_TC29 | TC-001887 | T9 | T9-PUR-AVS-REVERSE | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.86) | `TD-TC-001887.step1.purchase.json`<br>`TD-TC-001887.step2.reversal.json` | p.86, p.511, p.83, p.81 |
| BR1_TS5_TC30 | TC-001888 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001888.json` | p.40, p.511, p.83, p.81 |
| BR1_TS5_TC31 | TC-001889 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001889.step1.purchase.json`<br>`TD-TC-001889.step2.reversal.json`<br>`TD-TC-001889.step3.timeout-reversal.json` | p.40, p.511, p.83, p.81 |
| BR1_TS5_TC32 | TC-001890 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001890.step1.purchase.json`<br>`TD-TC-001890.step2.reversal.json` | p.84, p.511, p.83, p.81 |
| BR1_TS5_TC33 | TC-001891 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001891.json` | p.160, p.511, p.83, p.81 |
| BR1_TS5_TC34 | TC-001902 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001902.json` | p.404, p.511, p.83, p.81 |
| BR1_TS5_TC35 | TC-001892 | T11 | T11-EXPIRED | ready | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83 |
| BR1_TS5_TC36 | TC-001893 | T11 | T11-KEYED-BEFORE-3 | requires_review | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83 |
| BR1_TS5_TC37 | TC-001903 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.81 |
| BR1_TS5_TC38 | TC-001904 | T11 | T11-CVD-PROMPT | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |
| BR1_TS5_TC39 | TC-001905 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |

### BR1_TS6 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-EMV_FALLBACK`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-001906 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001906.json` | p.545, p.511, p.83, p.162 |
| BR1_TS6_TC2 | TC-001928 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001928.json` | PIPS p.52, p.511, p.83, p.162 |
| BR1_TS6_TC3 | TC-001907 | T2 | T2-CHIP-KEYED | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001907.json` | p.638, p.162, p.511, p.83 |
| BR1_TS6_TC4 | TC-001929 | T2 | T2-CHIP-SWIPED | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001929.json` | p.162, p.638, p.511, p.83 |
| BR1_TS6_TC5 | TC-001930 | T2 | T2-CHIP-SWIPED-TRACK1 | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001930.json` | p.162, p.81, p.638, p.511, p.83 |
| BR1_TS6_TC6 | TC-001908 | T4 | T4-PUR-SPLIT | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001908.step1.purchase.json`<br>`TD-TC-001908.step2.split-tender.json` | p.84, p.87, p.511, p.83, p.162 |
| BR1_TS6_TC7 | TC-001909 | T4 | T4-PUR-REVERSAL | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001909.step1.purchase.json`<br>`TD-TC-001909.step2.reversal.json` | p.84, p.511, p.83, p.162 |
| BR1_TS6_TC8 | TC-001910 | T4 | T4-PUR-TOR | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001910.step1.purchase.json`<br>`TD-TC-001910.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83, p.162 |
| BR1_TS6_TC9 | TC-001911 | T4 | T4-PUR-NO-PARTIAL | needs_client_input | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001911.json` | p.87, p.511, p.83, p.162 |
| BR1_TS6_TC10 | TC-001912 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001912.step1.purchase.json`<br>`TD-TC-001912.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83, p.162 |
| BR1_TS6_TC11 | TC-001931 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001931.step1.purchase.json`<br>`TD-TC-001931.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.162 |
| BR1_TS6_TC12 | TC-001932 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001932.step1.purchase.json`<br>`TD-TC-001932.step2.reversal.json`<br>`TD-TC-001932.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.162 |
| BR1_TS6_TC13 | TC-001913 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001913.step1.purchase.json`<br>`TD-TC-001913.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.162 |
| BR1_TS6_TC14 | TC-001914 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001914.step1.purchase.json`<br>`TD-TC-001914.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.162 |
| BR1_TS6_TC15 | TC-001915 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001915.step1.purchase.json`<br>`TD-TC-001915.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.162 |
| BR1_TS6_TC16 | TC-001916 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001916.step1.purchase.json`<br>`TD-TC-001916.step2.timeout-reversal.json`<br>`TD-TC-001916.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.162 |
| BR1_TS6_TC17 | TC-001917 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001917.step1.purchase.json`<br>`TD-TC-001917.step2.timeout-reversal.json`<br>`TD-TC-001917.step3.tor-retry.json`<br>`TD-TC-001917.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.162 |
| BR1_TS6_TC18 | TC-001933 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001933.step1.purchase.json`<br>`TD-TC-001933.step2.timeout-reversal.json`<br>`TD-TC-001933.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.162 |
| BR1_TS6_TC19 | TC-001934 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001934.step1.purchase.json`<br>`TD-TC-001934.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.162 |
| BR1_TS6_TC20 | TC-001935 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001935.step1.purchase.json`<br>`TD-TC-001935.step2.timeout-reversal.json`<br>`TD-TC-001935.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.162 |
| BR1_TS6_TC21 | TC-001918 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001918.step1.purchase.json`<br>`TD-TC-001918.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.162 |
| BR1_TS6_TC22 | TC-001919 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001919.step1.purchase.json`<br>`TD-TC-001919.step2.reversal.json`<br>`TD-TC-001919.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.162 |
| BR1_TS6_TC23 | TC-001920 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001920.step1.purchase.json`<br>`TD-TC-001920.step2.return.json`<br>`TD-TC-001920.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.162 |
| BR1_TS6_TC24 | TC-001921 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001921.step1.purchase.json`<br>`TD-TC-001921.step2.timeout-reversal.json`<br>`TD-TC-001921.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.162 |
| BR1_TS6_TC25 | TC-001922 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001922.step1.purchase.json`<br>`TD-TC-001922.step2.totals.json` | p.79, p.511, p.83, p.162 |
| BR1_TS6_TC26 | TC-001923 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001923.step1.purchase.json`<br>`TD-TC-001923.step2.totals.json` | p.79, p.511, p.83, p.162 |
| BR1_TS6_TC27 | TC-001936 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001936.step1.purchase.json`<br>`TD-TC-001936.step2.totals.json`<br>`TD-TC-001936.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.162 |
| BR1_TS6_TC28 | TC-001937 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001937.step1.purchase.json`<br>`TD-TC-001937.step2.totals.json`<br>`TD-TC-001937.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.162 |
| BR1_TS6_TC29 | TC-001924 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001924.json` | p.40, p.511, p.83, p.162 |
| BR1_TS6_TC30 | TC-001925 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001925.step1.purchase.json`<br>`TD-TC-001925.step2.reversal.json`<br>`TD-TC-001925.step3.timeout-reversal.json` | p.40, p.511, p.83, p.162 |
| BR1_TS6_TC31 | TC-001926 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001926.step1.purchase.json`<br>`TD-TC-001926.step2.reversal.json` | p.84, p.511, p.83, p.162 |
| BR1_TS6_TC32 | TC-001927 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001927.json` | p.160, p.511, p.83, p.162 |
| BR1_TS6_TC33 | TC-001938 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001938.json` | p.404, p.511, p.83, p.162 |
| BR1_TS6_TC34 | TC-001939 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.162 |
| BR1_TS6_TC35 | TC-001940 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.162 |

### BR1_TS7 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-001941 | T1 | T1-PUR | ready | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001941.json` | p.545, p.511, p.83, p.638 |
| BR1_TS7_TC2 | TC-001965 | T1 | T1-FLEET-CARD-AS-CREDIT | requires_review | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001965.json` | PIPS p.52, p.511, p.83, p.638 |
| BR1_TS7_TC3 | TC-001942 | T5 | T5-PUR-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001942.step1.purchase.json`<br>`TD-TC-001942.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83 |
| BR1_TS7_TC4 | TC-001966 | T5 | T5-PUR-NEXT-PUR | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001966.step1.purchase.json`<br>`TD-TC-001966.step2.next-transaction.json` | p.40, p.409, p.511, p.83, p.638 |
| BR1_TS7_TC5 | TC-001967 | T5 | T5-PUR-VOID-DECLINED-RETURN | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001967.step1.purchase.json`<br>`TD-TC-001967.step2.reversal.json`<br>`TD-TC-001967.step3.return.json` | PIPS p.30, p.546, p.511, p.83, p.638 |
| BR1_TS7_TC6 | TC-001943 | T6 | T6-PUR-NO-RESPONSE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001943.step1.purchase.json`<br>`TD-TC-001943.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.638 |
| BR1_TS7_TC7 | TC-001944 | T6 | T6-PUR-LATE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001944.step1.purchase.json`<br>`TD-TC-001944.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.638 |
| BR1_TS7_TC8 | TC-001945 | T6 | T6-PUR-UNREADABLE | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001945.step1.purchase.json`<br>`TD-TC-001945.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83, p.638 |
| BR1_TS7_TC9 | TC-001946 | T6 | T6-PUR-TOR-NEXT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001946.step1.purchase.json`<br>`TD-TC-001946.step2.timeout-reversal.json`<br>`TD-TC-001946.step3.next-transaction.json` | p.39, p.40, p.511, p.83, p.638 |
| BR1_TS7_TC10 | TC-001947 | T6 | T6-PUR-TOR-RETRY | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001947.step1.purchase.json`<br>`TD-TC-001947.step2.timeout-reversal.json`<br>`TD-TC-001947.step3.tor-retry.json`<br>`TD-TC-001947.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83, p.638 |
| BR1_TS7_TC11 | TC-001968 | T6 | T6-PUR-LINE-DOWN | needs_client_input | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001968.step1.purchase.json`<br>`TD-TC-001968.step2.timeout-reversal.json`<br>`TD-TC-001968.step3.next-transaction.json` | p.40, p.187, p.511, p.83, p.638 |
| BR1_TS7_TC12 | TC-001969 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | requires_review | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001969.step1.purchase.json`<br>`TD-TC-001969.step2.resubmitted.json` | p.549, p.40, p.511, p.83, p.638 |
| BR1_TS7_TC13 | TC-001970 | T6 | T6-PUR-LATE-RESUBMIT | ready | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001970.step1.purchase.json`<br>`TD-TC-001970.step2.timeout-reversal.json`<br>`TD-TC-001970.step3.resubmitted.json` | p.43, p.40, p.511, p.83, p.638 |
| BR1_TS7_TC14 | TC-001948 | T7 | T7-CIT | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001948.step1.purchase.json`<br>`TD-TC-001948.step2.subsequent.json` | p.716, p.638, p.713, p.511, p.83 |
| BR1_TS7_TC15 | TC-001949 | T7 | T7-ZERO-MIT | requires_review | 1. Account Verification [A] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001949.step1.account-verification.json`<br>`TD-TC-001949.step2.subsequent.json` | p.716, p.638, p.713, p.715, p.511, p.83 |
| BR1_TS7_TC16 | TC-001950 | T7 | T7-RECURRING-STANDING | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001950.step1.purchase.json`<br>`TD-TC-001950.step2.subsequent.json` | p.716, p.638, p.714, p.713, p.511, p.83 |
| BR1_TS7_TC17 | TC-001951 | T7 | T7-RECURRING-SUBSCRIPTION | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001951.step1.purchase.json`<br>`TD-TC-001951.step2.subsequent.json` | p.716, p.638, p.714, p.713, p.511, p.83 |
| BR1_TS7_TC18 | TC-001952 | T7 | T7-INSTALLMENT | ready | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001952.step1.purchase.json`<br>`TD-TC-001952.step2.subsequent.json` | p.716, p.638, p.714, p.713, p.511, p.83 |
| BR1_TS7_TC19 | TC-001953 | T8 | T8-PUR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001953.step1.purchase.json`<br>`TD-TC-001953.step2.totals.json` | p.78, p.79, p.89, p.511, p.83, p.638 |
| BR1_TS7_TC20 | TC-001954 | T8 | T8-PUR-REV-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001954.step1.purchase.json`<br>`TD-TC-001954.step2.reversal.json`<br>`TD-TC-001954.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.638 |
| BR1_TS7_TC21 | TC-001955 | T8 | T8-PUR-RET-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001955.step1.purchase.json`<br>`TD-TC-001955.step2.return.json`<br>`TD-TC-001955.step3.totals.json` | p.78, p.79, p.89, p.511, p.83, p.638 |
| BR1_TS7_TC22 | TC-001956 | T8 | T8-PUR-TOR-SETTLE | requires_review | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001956.step1.purchase.json`<br>`TD-TC-001956.step2.timeout-reversal.json`<br>`TD-TC-001956.step3.totals.json` | p.78, p.79, p.40, p.511, p.83, p.638 |
| BR1_TS7_TC23 | TC-001957 | T8 | T8-PUR-READONLY | ready | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001957.step1.purchase.json`<br>`TD-TC-001957.step2.totals.json` | p.79, p.511, p.83, p.638 |
| BR1_TS7_TC24 | TC-001958 | T8 | T8-PUR-AUTOCLOSE | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001958.step1.purchase.json`<br>`TD-TC-001958.step2.totals.json` | p.79, p.511, p.83, p.638 |
| BR1_TS7_TC25 | TC-001971 | T8 | T8-PUR-SETTLE-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001971.step1.purchase.json`<br>`TD-TC-001971.step2.totals.json`<br>`TD-TC-001971.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.638 |
| BR1_TS7_TC26 | TC-001972 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | requires_review | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001972.step1.purchase.json`<br>`TD-TC-001972.step2.totals.json`<br>`TD-TC-001972.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83, p.638 |
| BR1_TS7_TC27 | TC-001959 | T9 | T9-PUR-AVS | needs_client_input | 1. Purchase/Capture [0] | ASSERT 0 (p.86) | `TD-TC-001959.json` | p.86, p.511, p.83, p.638 |
| BR1_TS7_TC28 | TC-001960 | T9 | T9-PUR-AVS-REVERSE | needs_client_input | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.86) | `TD-TC-001960.step1.purchase.json`<br>`TD-TC-001960.step2.reversal.json` | p.86, p.511, p.83, p.638 |
| BR1_TS7_TC29 | TC-001961 | T10 | T10-TOR-UNMATCHED | ready | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001961.json` | p.40, p.511, p.83, p.638 |
| BR1_TS7_TC30 | TC-001962 | T10 | T10-TOR-AFTER-REVERSAL | ready | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001962.step1.purchase.json`<br>`TD-TC-001962.step2.reversal.json`<br>`TD-TC-001962.step3.timeout-reversal.json` | p.40, p.511, p.83, p.638 |
| BR1_TS7_TC31 | TC-001963 | T10 | T10-PARTIAL-REVERSE-REQUESTED | requires_review | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001963.step1.purchase.json`<br>`TD-TC-001963.step2.reversal.json` | p.84, p.511, p.83, p.638 |
| BR1_TS7_TC32 | TC-001964 | T10 | T10-CASHBACK | requires_review | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001964.json` | p.160, p.511, p.83, p.638 |
| BR1_TS7_TC33 | TC-001973 | T10 | T10-PUR-DECLINED | needs_client_input | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-001973.json` | p.404, p.511, p.83, p.638 |
| BR1_TS7_TC34 | TC-001974 | T11 | T11-SAF-BLOCKED | requires_review | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83, p.638 |
| BR1_TS7_TC35 | TC-001975 | T11 | T11-DISABLED-VOID | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.638 |

### BR1_TS8 — BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device

Scenario `TS-ATL105-CREDIT-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-001976 | T1 | T1-PUR | pending_sme | 1. Purchase/Capture [0] | ASSERT 0 (p.403) | `TD-TC-001976.json` | p.545, p.511, p.83 |
| BR1_TS8_TC2 | TC-001997 | T1 | T1-FLEET-CARD-AS-CREDIT | pending_sme | 1. Purchase/Capture [0] | FLAG 0 (p.404) | `TD-TC-001997.json` | PIPS p.52, p.511, p.83 |
| BR1_TS8_TC3 | TC-001977 | T4 | T4-PUR-SPLIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001977.step1.purchase.json`<br>`TD-TC-001977.step2.split-tender.json` | p.84, p.87, p.511, p.83 |
| BR1_TS8_TC4 | TC-001978 | T4 | T4-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.84) | `TD-TC-001978.step1.purchase.json`<br>`TD-TC-001978.step2.reversal.json` | p.84, p.511, p.83 |
| BR1_TS8_TC5 | TC-001979 | T4 | T4-PUR-TOR | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.84) | `TD-TC-001979.step1.purchase.json`<br>`TD-TC-001979.step2.timeout-reversal.json` | p.84, p.89, p.511, p.83 |
| BR1_TS8_TC6 | TC-001980 | T4 | T4-PUR-NO-PARTIAL | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.87) | `TD-TC-001980.json` | p.87, p.511, p.83 |
| BR1_TS8_TC7 | TC-001981 | T5 | T5-PUR-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-001981.step1.purchase.json`<br>`TD-TC-001981.step2.reversal.json` | p.546, p.638, p.94, p.511, p.83 |
| BR1_TS8_TC8 | TC-001998 | T5 | T5-PUR-NEXT-PUR | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001998.step1.purchase.json`<br>`TD-TC-001998.step2.next-transaction.json` | p.40, p.409, p.511, p.83 |
| BR1_TS8_TC9 | TC-001999 | T5 | T5-PUR-VOID-DECLINED-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Merchandise Return [7] | FLAG — | `TD-TC-001999.step1.purchase.json`<br>`TD-TC-001999.step2.reversal.json`<br>`TD-TC-001999.step3.return.json` | PIPS p.30, p.546, p.511, p.83 |
| BR1_TS8_TC10 | TC-001982 | T6 | T6-PUR-NO-RESPONSE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001982.step1.purchase.json`<br>`TD-TC-001982.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83 |
| BR1_TS8_TC11 | TC-001983 | T6 | T6-PUR-LATE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001983.step1.purchase.json`<br>`TD-TC-001983.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83 |
| BR1_TS8_TC12 | TC-001984 | T6 | T6-PUR-UNREADABLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] | FLAG 0 (p.45) | `TD-TC-001984.step1.purchase.json`<br>`TD-TC-001984.step2.timeout-reversal.json` | p.39, p.40, p.45, p.511, p.83 |
| BR1_TS8_TC13 | TC-001985 | T6 | T6-PUR-TOR-NEXT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-001985.step1.purchase.json`<br>`TD-TC-001985.step2.timeout-reversal.json`<br>`TD-TC-001985.step3.next-transaction.json` | p.39, p.40, p.511, p.83 |
| BR1_TS8_TC14 | TC-001986 | T6 | T6-PUR-TOR-RETRY | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Time-out Reversal [Z] → 4. Time-out Reversal [Z] | FLAG 0 (p.40) | `TD-TC-001986.step1.purchase.json`<br>`TD-TC-001986.step2.timeout-reversal.json`<br>`TD-TC-001986.step3.tor-retry.json`<br>`TD-TC-001986.step4.tor-retry.json` | p.39, p.40, p.47, p.511, p.83 |
| BR1_TS8_TC15 | TC-002000 | T6 | T6-PUR-LINE-DOWN | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002000.step1.purchase.json`<br>`TD-TC-002000.step2.timeout-reversal.json`<br>`TD-TC-002000.step3.next-transaction.json` | p.40, p.187, p.511, p.83 |
| BR1_TS8_TC16 | TC-002001 | T6 | T6-PUR-TIMEOUT-DECLINE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002001.step1.purchase.json`<br>`TD-TC-002001.step2.resubmitted.json` | p.549, p.40, p.511, p.83 |
| BR1_TS8_TC17 | TC-002002 | T6 | T6-PUR-LATE-RESUBMIT | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002002.step1.purchase.json`<br>`TD-TC-002002.step2.timeout-reversal.json`<br>`TD-TC-002002.step3.resubmitted.json` | p.43, p.40, p.511, p.83 |
| BR1_TS8_TC18 | TC-001987 | T8 | T8-PUR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001987.step1.purchase.json`<br>`TD-TC-001987.step2.totals.json` | p.78, p.79, p.89, p.511, p.83 |
| BR1_TS8_TC19 | TC-001988 | T8 | T8-PUR-REV-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001988.step1.purchase.json`<br>`TD-TC-001988.step2.reversal.json`<br>`TD-TC-001988.step3.totals.json` | p.78, p.79, p.89, p.511, p.83 |
| BR1_TS8_TC20 | TC-001989 | T8 | T8-PUR-RET-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Merchandise Return [7] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001989.step1.purchase.json`<br>`TD-TC-001989.step2.return.json`<br>`TD-TC-001989.step3.totals.json` | p.78, p.79, p.89, p.511, p.83 |
| BR1_TS8_TC21 | TC-001990 | T8 | T8-PUR-TOR-SETTLE | pending_sme | 1. Purchase/Capture [0] → 2. Time-out Reversal [Z] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-001990.step1.purchase.json`<br>`TD-TC-001990.step2.timeout-reversal.json`<br>`TD-TC-001990.step3.totals.json` | p.78, p.79, p.40, p.511, p.83 |
| BR1_TS8_TC22 | TC-001991 | T8 | T8-PUR-READONLY | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.181) | `TD-TC-001991.step1.purchase.json`<br>`TD-TC-001991.step2.totals.json` | p.79, p.511, p.83 |
| BR1_TS8_TC23 | TC-001992 | T8 | T8-PUR-AUTOCLOSE | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] | FLAG — (p.79) | `TD-TC-001992.step1.purchase.json`<br>`TD-TC-001992.step2.totals.json` | p.79, p.511, p.83 |
| BR1_TS8_TC24 | TC-002003 | T8 | T8-PUR-SETTLE-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002003.step1.purchase.json`<br>`TD-TC-002003.step2.totals.json`<br>`TD-TC-002003.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83 |
| BR1_TS8_TC25 | TC-002004 | T8 | T8-PUR-SETTLE-PARTIAL-RETURN | pending_sme | 1. Purchase/Capture [0] → 2. TOTALS [990] → 3. Merchandise Return [7] | FLAG — | `TD-TC-002004.step1.purchase.json`<br>`TD-TC-002004.step2.totals.json`<br>`TD-TC-002004.step3.return.json` | p.79, p.546, PIPS p.29, p.511, p.83 |
| BR1_TS8_TC26 | TC-001993 | T10 | T10-TOR-UNMATCHED | pending_sme | 1. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001993.json` | p.40, p.511, p.83 |
| BR1_TS8_TC27 | TC-001994 | T10 | T10-TOR-AFTER-REVERSAL | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] → 3. Time-out Reversal [Z] | ASSERT 1 (p.40) | `TD-TC-001994.step1.purchase.json`<br>`TD-TC-001994.step2.reversal.json`<br>`TD-TC-001994.step3.timeout-reversal.json` | p.40, p.511, p.83 |
| BR1_TS8_TC28 | TC-001995 | T10 | T10-PARTIAL-REVERSE-REQUESTED | pending_sme | 1. Purchase/Capture [0] → 2. Purchase Reversal [8] | FLAG 1 (p.84) | `TD-TC-001995.step1.purchase.json`<br>`TD-TC-001995.step2.reversal.json` | p.84, p.511, p.83 |
| BR1_TS8_TC29 | TC-001996 | T10 | T10-CASHBACK | pending_sme | 1. Purchase/Capture [0] | FLAG 1 (p.160) | `TD-TC-001996.json` | p.160, p.511, p.83 |
| BR1_TS8_TC30 | TC-002005 | T10 | T10-PUR-DECLINED | pending_sme | 1. Purchase/Capture [0] | ASSERT 1 (p.404) | `TD-TC-002005.json` | p.404, p.511, p.83 |
| BR1_TS8_TC31 | TC-002006 | T11 | T11-SAF-BLOCKED | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.335, p.511, p.83 |
| BR1_TS8_TC32 | TC-002007 | T11 | T11-DISABLED-VOID | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83 |

### BR1_TS9 — BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed

Scenario `TS-ATL105-CREDIT-MASTERCARD-MOTO_PURCHASE-MANUAL`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-002008 | T1 | T1-MOP | ready | 1. Mail/Phone Purchase [6] | FLAG — | `TD-TC-002008.json` | p.545, p.511, p.83, p.81 |
| BR1_TS9_TC2 | TC-002009 | T5 | T5-MOP-REVERSAL | ready | 1. Mail/Phone Purchase [6] → 2. Mail/Phone Reversal [C] | FLAG 0 (p.546) | `TD-TC-002009.step1.moto-purchase.json`<br>`TD-TC-002009.step2.moto-reversal.json` | p.546, p.511, p.83, p.81 |
| BR1_TS9_TC3 | TC-002010 | T6 | T6-MOP-TOR | ready | 1. Mail/Phone Purchase [6] → 2. Time-out Reversal [Z] | FLAG 0 (p.39) | `TD-TC-002010.step1.moto-purchase.json`<br>`TD-TC-002010.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.81 |
| BR1_TS9_TC4 | TC-002011 | T9 | T9-MOP-AVS | needs_client_input | 1. Mail/Phone Purchase [6] | ASSERT 0 (p.86) | `TD-TC-002011.json` | p.86, p.511, p.83, p.81 |
| BR1_TS9_TC5 | TC-002012 | T9 | T9-MOP-AVS-REVERSE | requires_review | 1. Mail/Phone Purchase [6] → 2. Mail/Phone Reversal [C] | FLAG 0 (p.546) | `TD-TC-002012.step1.moto-purchase.json`<br>`TD-TC-002012.step2.moto-reversal.json` | p.86, p.546, p.511, p.83, p.81 |
| BR1_TS9_TC6 | TC-002013 | T10 | T10-MOP-PARTIAL | requires_review | 1. Mail/Phone Purchase [6] | FLAG 1 (p.84) | `TD-TC-002013.json` | p.84, p.511, p.83, p.81 |

### BR1_TS10 — BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File

Scenario `TS-ATL105-CREDIT-MASTERCARD-MOTO_PURCHASE-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-002014 | T1 | T1-MOP | pending_sme | 1. Mail/Phone Purchase [6] | FLAG — | `TD-TC-002014.json` | p.545, p.511, p.83, p.638 |
| BR1_TS10_TC2 | TC-002015 | T5 | T5-MOP-REVERSAL | pending_sme | 1. Mail/Phone Purchase [6] → 2. Mail/Phone Reversal [C] | FLAG 0 (p.546) | `TD-TC-002015.step1.moto-purchase.json`<br>`TD-TC-002015.step2.moto-reversal.json` | p.546, p.511, p.83, p.638 |
| BR1_TS10_TC3 | TC-002016 | T6 | T6-MOP-TOR | pending_sme | 1. Mail/Phone Purchase [6] → 2. Time-out Reversal [Z] | FLAG 0 (p.39) | `TD-TC-002016.step1.moto-purchase.json`<br>`TD-TC-002016.step2.timeout-reversal.json` | p.39, p.40, p.511, p.83, p.638 |
| BR1_TS10_TC4 | TC-002017 | T9 | T9-MOP-AVS | pending_sme | 1. Mail/Phone Purchase [6] | ASSERT 0 (p.86) | `TD-TC-002017.json` | p.86, p.511, p.83, p.638 |
| BR1_TS10_TC5 | TC-002018 | T9 | T9-MOP-AVS-REVERSE | pending_sme | 1. Mail/Phone Purchase [6] → 2. Mail/Phone Reversal [C] | FLAG 0 (p.546) | `TD-TC-002018.step1.moto-purchase.json`<br>`TD-TC-002018.step2.moto-reversal.json` | p.86, p.546, p.511, p.83, p.638 |
| BR1_TS10_TC6 | TC-002019 | T10 | T10-MOP-PARTIAL | pending_sme | 1. Mail/Phone Purchase [6] | FLAG 1 (p.84) | `TD-TC-002019.json` | p.84, p.511, p.83, p.638 |

### BR1_TS11 — BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-002020 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002020.json` | p.545, p.511, p.83, p.161 |
| BR1_TS11_TC2 | TC-002021 | T3 | T3-ONLINE-PIN | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002021.json` | p.162, p.511, p.83, p.161 |
| BR1_TS11_TC3 | TC-002022 | T3 | T3-OFFLINE-PIN | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002022.json` | p.162, p.511, p.83, p.161 |
| BR1_TS11_TC4 | TC-002023 | T4 | T4-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002023.step1.auth-only.json`<br>`TD-TC-002023.step2.completion.json` | p.84, p.85, p.511, p.83, p.161 |
| BR1_TS11_TC5 | TC-002024 | T4 | T4-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002024.step1.auth-only.json`<br>`TD-TC-002024.step2.cancellation.json` | p.84, p.85, p.511, p.83, p.161 |
| BR1_TS11_TC6 | TC-002025 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002025.step1.auth-only.json`<br>`TD-TC-002025.step2.completion.json` | p.545, p.511, p.83, p.161 |
| BR1_TS11_TC7 | TC-002026 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002026.step1.auth-only.json`<br>`TD-TC-002026.step2.completion.json` | p.545, p.511, p.83, p.161 |
| BR1_TS11_TC8 | TC-002027 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002027.step1.auth-only.json`<br>`TD-TC-002027.step2.cancellation.json` | p.85, p.511, p.83, p.161 |
| BR1_TS11_TC9 | TC-002028 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002028.step1.auth-only.json`<br>`TD-TC-002028.step2.completion.json`<br>`TD-TC-002028.step3.reversal.json` | p.95, p.546, p.511, p.83, p.161 |
| BR1_TS11_TC10 | TC-002029 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002029.step1.auth-only.json`<br>`TD-TC-002029.step2.completion.json`<br>`TD-TC-002029.step3.timeout-reversal.json`<br>`TD-TC-002029.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.161 |
| BR1_TS11_TC11 | TC-002030 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002030.step1.auth-only.json`<br>`TD-TC-002030.step2.completion.json`<br>`TD-TC-002030.step3.totals.json` | p.78, p.79, p.511, p.83, p.161 |
| BR1_TS11_TC12 | TC-002031 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002031.step1.auth-only.json`<br>`TD-TC-002031.step2.timeout-reversal.json` | p.47, p.511, p.83, p.161 |
| BR1_TS11_TC13 | TC-002032 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002032.step1.auth-only.json`<br>`TD-TC-002032.step2.reversal.json` | p.85, p.511, p.83, p.161 |
| BR1_TS11_TC14 | TC-002033 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002033.step1.auth-only.json`<br>`TD-TC-002033.step2.cancellation.json` | p.85, p.511, p.83, p.161 |
| BR1_TS11_TC15 | TC-002034 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.161 |

### BR1_TS12 — BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-002035 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002035.json` | p.545, p.511, p.83, p.161 |
| BR1_TS12_TC2 | TC-002036 | T2 | T2-CL-TO-CONTACT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002036.json` | p.638, p.511, p.83, p.161 |
| BR1_TS12_TC3 | TC-002037 | T3 | T3-ONLINE-PIN | needs_client_input | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002037.json` | p.162, p.511, p.83, p.161 |
| BR1_TS12_TC4 | TC-002038 | T4 | T4-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002038.step1.auth-only.json`<br>`TD-TC-002038.step2.completion.json` | p.84, p.85, p.511, p.83, p.161 |
| BR1_TS12_TC5 | TC-002039 | T4 | T4-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002039.step1.auth-only.json`<br>`TD-TC-002039.step2.cancellation.json` | p.84, p.85, p.511, p.83, p.161 |
| BR1_TS12_TC6 | TC-002040 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002040.step1.auth-only.json`<br>`TD-TC-002040.step2.completion.json` | p.545, p.511, p.83, p.161 |
| BR1_TS12_TC7 | TC-002041 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002041.step1.auth-only.json`<br>`TD-TC-002041.step2.completion.json` | p.545, p.511, p.83, p.161 |
| BR1_TS12_TC8 | TC-002042 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002042.step1.auth-only.json`<br>`TD-TC-002042.step2.cancellation.json` | p.85, p.511, p.83, p.161 |
| BR1_TS12_TC9 | TC-002043 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002043.step1.auth-only.json`<br>`TD-TC-002043.step2.completion.json`<br>`TD-TC-002043.step3.reversal.json` | p.95, p.546, p.511, p.83, p.161 |
| BR1_TS12_TC10 | TC-002044 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002044.step1.auth-only.json`<br>`TD-TC-002044.step2.completion.json`<br>`TD-TC-002044.step3.timeout-reversal.json`<br>`TD-TC-002044.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.161 |
| BR1_TS12_TC11 | TC-002045 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002045.step1.auth-only.json`<br>`TD-TC-002045.step2.completion.json`<br>`TD-TC-002045.step3.totals.json` | p.78, p.79, p.511, p.83, p.161 |
| BR1_TS12_TC12 | TC-002046 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002046.step1.auth-only.json`<br>`TD-TC-002046.step2.timeout-reversal.json` | p.47, p.511, p.83, p.161 |
| BR1_TS12_TC13 | TC-002047 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002047.step1.auth-only.json`<br>`TD-TC-002047.step2.reversal.json` | p.85, p.511, p.83, p.161 |
| BR1_TS12_TC14 | TC-002048 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002048.step1.auth-only.json`<br>`TD-TC-002048.step2.cancellation.json` | p.85, p.511, p.83, p.161 |
| BR1_TS12_TC15 | TC-002049 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.161 |

### BR1_TS13 — BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-MAGSTRIPE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-002050 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002050.json` | p.545, p.511, p.83, p.81 |
| BR1_TS13_TC2 | TC-002051 | T2 | T2-TRACK1 | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002051.json` | p.81, p.365, p.511, p.83 |
| BR1_TS13_TC3 | TC-002052 | T4 | T4-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002052.step1.auth-only.json`<br>`TD-TC-002052.step2.completion.json` | p.84, p.85, p.511, p.83, p.81 |
| BR1_TS13_TC4 | TC-002053 | T4 | T4-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002053.step1.auth-only.json`<br>`TD-TC-002053.step2.cancellation.json` | p.84, p.85, p.511, p.83, p.81 |
| BR1_TS13_TC5 | TC-002054 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002054.step1.auth-only.json`<br>`TD-TC-002054.step2.completion.json` | p.545, p.511, p.83, p.81 |
| BR1_TS13_TC6 | TC-002055 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002055.step1.auth-only.json`<br>`TD-TC-002055.step2.completion.json` | p.545, p.511, p.83, p.81 |
| BR1_TS13_TC7 | TC-002056 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002056.step1.auth-only.json`<br>`TD-TC-002056.step2.cancellation.json` | p.85, p.511, p.83, p.81 |
| BR1_TS13_TC8 | TC-002057 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002057.step1.auth-only.json`<br>`TD-TC-002057.step2.completion.json`<br>`TD-TC-002057.step3.reversal.json` | p.95, p.546, p.511, p.83, p.81 |
| BR1_TS13_TC9 | TC-002058 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002058.step1.auth-only.json`<br>`TD-TC-002058.step2.completion.json`<br>`TD-TC-002058.step3.timeout-reversal.json`<br>`TD-TC-002058.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.81 |
| BR1_TS13_TC10 | TC-002059 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002059.step1.auth-only.json`<br>`TD-TC-002059.step2.completion.json`<br>`TD-TC-002059.step3.totals.json` | p.78, p.79, p.511, p.83, p.81 |
| BR1_TS13_TC11 | TC-002060 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002060.step1.auth-only.json`<br>`TD-TC-002060.step2.timeout-reversal.json` | p.47, p.511, p.83, p.81 |
| BR1_TS13_TC12 | TC-002061 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002061.step1.auth-only.json`<br>`TD-TC-002061.step2.reversal.json` | p.85, p.511, p.83, p.81 |
| BR1_TS13_TC13 | TC-002062 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002062.step1.auth-only.json`<br>`TD-TC-002062.step2.cancellation.json` | p.85, p.511, p.83, p.81 |
| BR1_TS13_TC14 | TC-002063 | T11 | T11-EXPIRED | ready | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83 |
| BR1_TS13_TC15 | TC-002064 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |

### BR1_TS14 — BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-002065 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002065.json` | p.545, p.511, p.83, p.86 |
| BR1_TS14_TC2 | TC-002066 | T4 | T4-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002066.step1.auth-only.json`<br>`TD-TC-002066.step2.completion.json` | p.84, p.85, p.511, p.83, p.86 |
| BR1_TS14_TC3 | TC-002067 | T4 | T4-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002067.step1.auth-only.json`<br>`TD-TC-002067.step2.cancellation.json` | p.84, p.85, p.511, p.83, p.86 |
| BR1_TS14_TC4 | TC-002068 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002068.step1.auth-only.json`<br>`TD-TC-002068.step2.completion.json` | p.545, p.511, p.83, p.86 |
| BR1_TS14_TC5 | TC-002069 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002069.step1.auth-only.json`<br>`TD-TC-002069.step2.completion.json` | p.545, p.511, p.83, p.86 |
| BR1_TS14_TC6 | TC-002070 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002070.step1.auth-only.json`<br>`TD-TC-002070.step2.cancellation.json` | p.85, p.511, p.83, p.86 |
| BR1_TS14_TC7 | TC-002071 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002071.step1.auth-only.json`<br>`TD-TC-002071.step2.completion.json`<br>`TD-TC-002071.step3.reversal.json` | p.95, p.546, p.511, p.83, p.86 |
| BR1_TS14_TC8 | TC-002072 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002072.step1.auth-only.json`<br>`TD-TC-002072.step2.completion.json`<br>`TD-TC-002072.step3.timeout-reversal.json`<br>`TD-TC-002072.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.86 |
| BR1_TS14_TC9 | TC-002073 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002073.step1.auth-only.json`<br>`TD-TC-002073.step2.completion.json`<br>`TD-TC-002073.step3.totals.json` | p.78, p.79, p.511, p.83, p.86 |
| BR1_TS14_TC10 | TC-002074 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002074.step1.auth-only.json`<br>`TD-TC-002074.step2.timeout-reversal.json` | p.47, p.511, p.83, p.86 |
| BR1_TS14_TC11 | TC-002075 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002075.step1.auth-only.json`<br>`TD-TC-002075.step2.reversal.json` | p.85, p.511, p.83, p.86 |
| BR1_TS14_TC12 | TC-002076 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002076.step1.auth-only.json`<br>`TD-TC-002076.step2.cancellation.json` | p.85, p.511, p.83, p.86 |
| BR1_TS14_TC13 | TC-002077 | T11 | T11-EXPIRED | ready | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83, p.86 |
| BR1_TS14_TC14 | TC-002078 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.86 |

### BR1_TS15 — BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-MANUAL`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-002079 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002079.json` | p.545, p.511, p.83, p.81 |
| BR1_TS15_TC2 | TC-002080 | T2 | T2-3SWIPES | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002080.json` | p.81, p.511, p.83 |
| BR1_TS15_TC3 | TC-002081 | T4 | T4-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002081.step1.auth-only.json`<br>`TD-TC-002081.step2.completion.json` | p.84, p.85, p.511, p.83, p.81 |
| BR1_TS15_TC4 | TC-002082 | T4 | T4-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002082.step1.auth-only.json`<br>`TD-TC-002082.step2.cancellation.json` | p.84, p.85, p.511, p.83, p.81 |
| BR1_TS15_TC5 | TC-002083 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002083.step1.auth-only.json`<br>`TD-TC-002083.step2.completion.json` | p.545, p.511, p.83, p.81 |
| BR1_TS15_TC6 | TC-002084 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002084.step1.auth-only.json`<br>`TD-TC-002084.step2.completion.json` | p.545, p.511, p.83, p.81 |
| BR1_TS15_TC7 | TC-002085 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002085.step1.auth-only.json`<br>`TD-TC-002085.step2.cancellation.json` | p.85, p.511, p.83, p.81 |
| BR1_TS15_TC8 | TC-002086 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002086.step1.auth-only.json`<br>`TD-TC-002086.step2.completion.json`<br>`TD-TC-002086.step3.reversal.json` | p.95, p.546, p.511, p.83, p.81 |
| BR1_TS15_TC9 | TC-002087 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002087.step1.auth-only.json`<br>`TD-TC-002087.step2.completion.json`<br>`TD-TC-002087.step3.timeout-reversal.json`<br>`TD-TC-002087.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.81 |
| BR1_TS15_TC10 | TC-002088 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002088.step1.auth-only.json`<br>`TD-TC-002088.step2.completion.json`<br>`TD-TC-002088.step3.totals.json` | p.78, p.79, p.511, p.83, p.81 |
| BR1_TS15_TC11 | TC-002089 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002089.step1.auth-only.json`<br>`TD-TC-002089.step2.timeout-reversal.json` | p.47, p.511, p.83, p.81 |
| BR1_TS15_TC12 | TC-002090 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002090.step1.auth-only.json`<br>`TD-TC-002090.step2.reversal.json` | p.85, p.511, p.83, p.81 |
| BR1_TS15_TC13 | TC-002091 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002091.step1.auth-only.json`<br>`TD-TC-002091.step2.cancellation.json` | p.85, p.511, p.83, p.81 |
| BR1_TS15_TC14 | TC-002092 | T11 | T11-EXPIRED | ready | device (no host message) | N/A — | — (device: no host message) | p.81, p.511, p.83 |
| BR1_TS15_TC15 | TC-002093 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |

### BR1_TS16 — BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-EMV_FALLBACK`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-002094 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002094.json` | p.545, p.511, p.83, p.162 |
| BR1_TS16_TC2 | TC-002095 | T2 | T2-CHIP-KEYED | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002095.json` | p.638, p.162, p.511, p.83 |
| BR1_TS16_TC3 | TC-002107 | T2 | T2-CHIP-SWIPED | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002107.json` | p.162, p.638, p.511, p.83 |
| BR1_TS16_TC4 | TC-002108 | T2 | T2-CHIP-SWIPED-TRACK1 | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002108.json` | p.162, p.81, p.638, p.511, p.83 |
| BR1_TS16_TC5 | TC-002096 | T4 | T4-AUT-COMPLETION | needs_client_input | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002096.step1.auth-only.json`<br>`TD-TC-002096.step2.completion.json` | p.84, p.85, p.511, p.83, p.162 |
| BR1_TS16_TC6 | TC-002097 | T4 | T4-AUT-CANCEL | needs_client_input | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002097.step1.auth-only.json`<br>`TD-TC-002097.step2.cancellation.json` | p.84, p.85, p.511, p.83, p.162 |
| BR1_TS16_TC7 | TC-002098 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002098.step1.auth-only.json`<br>`TD-TC-002098.step2.completion.json` | p.545, p.511, p.83, p.162 |
| BR1_TS16_TC8 | TC-002099 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002099.step1.auth-only.json`<br>`TD-TC-002099.step2.completion.json` | p.545, p.511, p.83, p.162 |
| BR1_TS16_TC9 | TC-002100 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002100.step1.auth-only.json`<br>`TD-TC-002100.step2.cancellation.json` | p.85, p.511, p.83, p.162 |
| BR1_TS16_TC10 | TC-002101 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002101.step1.auth-only.json`<br>`TD-TC-002101.step2.completion.json`<br>`TD-TC-002101.step3.reversal.json` | p.95, p.546, p.511, p.83, p.162 |
| BR1_TS16_TC11 | TC-002102 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002102.step1.auth-only.json`<br>`TD-TC-002102.step2.completion.json`<br>`TD-TC-002102.step3.timeout-reversal.json`<br>`TD-TC-002102.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.162 |
| BR1_TS16_TC12 | TC-002103 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002103.step1.auth-only.json`<br>`TD-TC-002103.step2.completion.json`<br>`TD-TC-002103.step3.totals.json` | p.78, p.79, p.511, p.83, p.162 |
| BR1_TS16_TC13 | TC-002104 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002104.step1.auth-only.json`<br>`TD-TC-002104.step2.timeout-reversal.json` | p.47, p.511, p.83, p.162 |
| BR1_TS16_TC14 | TC-002105 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002105.step1.auth-only.json`<br>`TD-TC-002105.step2.reversal.json` | p.85, p.511, p.83, p.162 |
| BR1_TS16_TC15 | TC-002106 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002106.step1.auth-only.json`<br>`TD-TC-002106.step2.cancellation.json` | p.85, p.511, p.83, p.162 |
| BR1_TS16_TC16 | TC-002109 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.162 |

### BR1_TS17 — BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-002110 | T1 | T1-AUT | ready | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002110.json` | p.545, p.511, p.83, p.638 |
| BR1_TS17_TC2 | TC-002111 | T5 | T5-AUT-COMPLETION | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002111.step1.auth-only.json`<br>`TD-TC-002111.step2.completion.json` | p.545, p.511, p.83, p.638 |
| BR1_TS17_TC3 | TC-002112 | T5 | T5-AUT-COMPLETION-DIFF | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002112.step1.auth-only.json`<br>`TD-TC-002112.step2.completion.json` | p.545, p.511, p.83, p.638 |
| BR1_TS17_TC4 | TC-002113 | T5 | T5-AUT-CANCEL | ready | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002113.step1.auth-only.json`<br>`TD-TC-002113.step2.cancellation.json` | p.85, p.511, p.83, p.638 |
| BR1_TS17_TC5 | TC-002114 | T5 | T5-AUT-COMPLETION-REVERSAL | ready | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002114.step1.auth-only.json`<br>`TD-TC-002114.step2.completion.json`<br>`TD-TC-002114.step3.reversal.json` | p.95, p.546, p.511, p.83, p.638 |
| BR1_TS17_TC6 | TC-002115 | T6 | T6-AUT-COMPLETION-TOR | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002115.step1.auth-only.json`<br>`TD-TC-002115.step2.completion.json`<br>`TD-TC-002115.step3.timeout-reversal.json`<br>`TD-TC-002115.step4.completion-resubmitted.json` | p.47, p.511, p.83, p.638 |
| BR1_TS17_TC7 | TC-002116 | T8 | T8-AUT-COMPLETION-SETTLE | requires_review | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002116.step1.auth-only.json`<br>`TD-TC-002116.step2.completion.json`<br>`TD-TC-002116.step3.totals.json` | p.78, p.79, p.511, p.83, p.638 |
| BR1_TS17_TC8 | TC-002117 | T10 | T10-AUT-TOR | requires_review | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002117.step1.auth-only.json`<br>`TD-TC-002117.step2.timeout-reversal.json` | p.47, p.511, p.83, p.638 |
| BR1_TS17_TC9 | TC-002118 | T10 | T10-AUT-REVERSE-8 | requires_review | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002118.step1.auth-only.json`<br>`TD-TC-002118.step2.reversal.json` | p.85, p.511, p.83, p.638 |
| BR1_TS17_TC10 | TC-002119 | T10 | T10-CANCEL-MISMATCH | requires_review | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002119.step1.auth-only.json`<br>`TD-TC-002119.step2.cancellation.json` | p.85, p.511, p.83, p.638 |
| BR1_TS17_TC11 | TC-002120 | T11 | T11-DISABLED-PREAUTH | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.638 |

### BR1_TS18 — BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device

Scenario `TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-002121 | T1 | T1-AUT | pending_sme | 1. Authorization Only [3] | ASSERT 2 (p.403) | `TD-TC-002121.json` | p.545, p.511, p.83 |
| BR1_TS18_TC2 | TC-002122 | T4 | T4-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002122.step1.auth-only.json`<br>`TD-TC-002122.step2.completion.json` | p.84, p.85, p.511, p.83 |
| BR1_TS18_TC3 | TC-002123 | T4 | T4-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002123.step1.auth-only.json`<br>`TD-TC-002123.step2.cancellation.json` | p.84, p.85, p.511, p.83 |
| BR1_TS18_TC4 | TC-002124 | T5 | T5-AUT-COMPLETION | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002124.step1.auth-only.json`<br>`TD-TC-002124.step2.completion.json` | p.545, p.511, p.83 |
| BR1_TS18_TC5 | TC-002125 | T5 | T5-AUT-COMPLETION-DIFF | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] | FLAG 0 (p.545) | `TD-TC-002125.step1.auth-only.json`<br>`TD-TC-002125.step2.completion.json` | p.545, p.511, p.83 |
| BR1_TS18_TC6 | TC-002126 | T5 | T5-AUT-CANCEL | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 0 (p.85) | `TD-TC-002126.step1.auth-only.json`<br>`TD-TC-002126.step2.cancellation.json` | p.85, p.511, p.83 |
| BR1_TS18_TC7 | TC-002127 | T5 | T5-AUT-COMPLETION-REVERSAL | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Purchase Reversal [8] | FLAG 0 (p.546) | `TD-TC-002127.step1.auth-only.json`<br>`TD-TC-002127.step2.completion.json`<br>`TD-TC-002127.step3.reversal.json` | p.95, p.546, p.511, p.83 |
| BR1_TS18_TC8 | TC-002128 | T6 | T6-AUT-COMPLETION-TOR | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. Time-out Reversal [Z] → 4. Purchase/Capture [0] | ASSERT 0 (p.404) | `TD-TC-002128.step1.auth-only.json`<br>`TD-TC-002128.step2.completion.json`<br>`TD-TC-002128.step3.timeout-reversal.json`<br>`TD-TC-002128.step4.completion-resubmitted.json` | p.47, p.511, p.83 |
| BR1_TS18_TC9 | TC-002129 | T8 | T8-AUT-COMPLETION-SETTLE | pending_sme | 1. Authorization Only [3] → 2. Purchase/Capture [0] → 3. TOTALS [990] | FLAG — (p.181) | `TD-TC-002129.step1.auth-only.json`<br>`TD-TC-002129.step2.completion.json`<br>`TD-TC-002129.step3.totals.json` | p.78, p.79, p.511, p.83 |
| BR1_TS18_TC10 | TC-002130 | T10 | T10-AUT-TOR | pending_sme | 1. Authorization Only [3] → 2. Time-out Reversal [Z] | FLAG 1 (p.47) | `TD-TC-002130.step1.auth-only.json`<br>`TD-TC-002130.step2.timeout-reversal.json` | p.47, p.511, p.83 |
| BR1_TS18_TC11 | TC-002131 | T10 | T10-AUT-REVERSE-8 | pending_sme | 1. Authorization Only [3] → 2. Purchase Reversal [8] | FLAG 1 (p.85) | `TD-TC-002131.step1.auth-only.json`<br>`TD-TC-002131.step2.reversal.json` | p.85, p.511, p.83 |
| BR1_TS18_TC12 | TC-002132 | T10 | T10-CANCEL-MISMATCH | pending_sme | 1. Authorization Only [3] → 2. Cancellation [S] | FLAG 1 (p.85) | `TD-TC-002132.step1.auth-only.json`<br>`TD-TC-002132.step2.cancellation.json` | p.85, p.511, p.83 |
| BR1_TS18_TC13 | TC-002133 | T11 | T11-DISABLED-PREAUTH | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83 |

### BR1_TS19 — BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed

Scenario `TS-ATL105-CREDIT-MASTERCARD-MOTO_AUTH_ONLY-MANUAL`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-002134 | T1 | T1-MOA | ready | 1. Mail/Phone Authorization Only [B] | ASSERT 2 (p.403) | `TD-TC-002134.json` | p.546, p.511, p.83, p.81 |
| BR1_TS19_TC2 | TC-002135 | T5 | T5-MOA-CANCEL | ready | 1. Mail/Phone Authorization Only [B] → 2. Cancellation [S] | FLAG 0 (p.368) | `TD-TC-002135.step1.moto-auth-only.json`<br>`TD-TC-002135.step2.cancellation.json` | p.368, p.511, p.83, p.81 |
| BR1_TS19_TC3 | TC-002136 | T5 | T5-MOA-COMPLETION | blocked_spec_gap | host (no host message) | N/A — | — (host: no host message) | p.546, p.511, p.83, p.81 |

### BR1_TS20 — BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File

Scenario `TS-ATL105-CREDIT-MASTERCARD-MOTO_AUTH_ONLY-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-002137 | T1 | T1-MOA | pending_sme | 1. Mail/Phone Authorization Only [B] | ASSERT 2 (p.403) | `TD-TC-002137.json` | p.546, p.511, p.83, p.638 |
| BR1_TS20_TC2 | TC-002138 | T5 | T5-MOA-CANCEL | pending_sme | 1. Mail/Phone Authorization Only [B] → 2. Cancellation [S] | FLAG 0 (p.368) | `TD-TC-002138.step1.moto-auth-only.json`<br>`TD-TC-002138.step2.cancellation.json` | p.368, p.511, p.83, p.638 |
| BR1_TS20_TC3 | TC-002139 | T5 | T5-MOA-COMPLETION | blocked_spec_gap | host (no host message) | N/A — | — (host: no host message) | p.546, p.511, p.83, p.638 |

### BR1_TS21 — BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-002140 | T1 | T1-RET | ready | 1. Merchandise Return [7] | FLAG — | `TD-TC-002140.json` | p.546, p.511, p.83, p.161 |
| BR1_TS21_TC2 | TC-002141 | T5 | T5-RET-VOID | requires_review | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002141.step1.return.json`<br>`TD-TC-002141.step2.void-return.json` | p.546, p.511, p.83, p.161 |
| BR1_TS21_TC3 | TC-002142 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.161 |

### BR1_TS22 — BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-002143 | T1 | T1-RET | ready | 1. Merchandise Return [7] | FLAG — | `TD-TC-002143.json` | p.546, p.511, p.83, p.161 |
| BR1_TS22_TC2 | TC-002144 | T5 | T5-RET-VOID | requires_review | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002144.step1.return.json`<br>`TD-TC-002144.step2.void-return.json` | p.546, p.511, p.83, p.161 |
| BR1_TS22_TC3 | TC-002145 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.161 |

### BR1_TS23 — BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-MAGSTRIPE`, status `spec_implied`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-002146 | T1 | T1-RET | ready | 1. Merchandise Return [7] | FLAG — | `TD-TC-002146.json` | p.546, p.511, p.83, p.81 |
| BR1_TS23_TC2 | TC-002147 | T5 | T5-RET-VOID | ready | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002147.step1.return.json`<br>`TD-TC-002147.step2.void-return.json` | p.546, p.511, p.83, p.81 |
| BR1_TS23_TC3 | TC-002148 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |

### BR1_TS24 — BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-002149 | T1 | T1-RET | ready | 1. Merchandise Return [7] | FLAG — | `TD-TC-002149.json` | p.546, p.511, p.83, p.86 |
| BR1_TS24_TC2 | TC-002150 | T5 | T5-RET-VOID | ready | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002150.step1.return.json`<br>`TD-TC-002150.step2.void-return.json` | p.546, p.511, p.83, p.86 |
| BR1_TS24_TC3 | TC-002151 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.86 |

### BR1_TS25 — BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-MANUAL`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-002152 | T1 | T1-RET | ready | 1. Merchandise Return [7] | FLAG — | `TD-TC-002152.json` | p.546, p.511, p.83, p.81 |
| BR1_TS25_TC2 | TC-002153 | T5 | T5-RET-VOID | ready | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002153.step1.return.json`<br>`TD-TC-002153.step2.void-return.json` | p.546, p.511, p.83, p.81 |
| BR1_TS25_TC3 | TC-002154 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.81 |

### BR1_TS26 — BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-EMV_FALLBACK`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-002155 | T1 | T1-RET | ready | 1. Merchandise Return [7] | FLAG — | `TD-TC-002155.json` | p.546, p.511, p.83, p.162 |
| BR1_TS26_TC2 | TC-002156 | T5 | T5-RET-VOID | ready | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002156.step1.return.json`<br>`TD-TC-002156.step2.void-return.json` | p.546, p.511, p.83, p.162 |
| BR1_TS26_TC3 | TC-002157 | T11 | T11-DISABLED-RETURN | ready | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.162 |

### BR1_TS27 — BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-002158 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-002158.json` | p.546, p.511, p.83, p.638 |
| BR1_TS27_TC2 | TC-002159 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002159.step1.return.json`<br>`TD-TC-002159.step2.void-return.json` | p.546, p.511, p.83, p.638 |
| BR1_TS27_TC3 | TC-002160 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83, p.638 |

### BR1_TS28 — BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device

Scenario `TS-ATL105-CREDIT-MASTERCARD-RETURN-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-002161 | T1 | T1-RET | pending_sme | 1. Merchandise Return [7] | FLAG — | `TD-TC-002161.json` | p.546, p.511, p.83 |
| BR1_TS28_TC2 | TC-002162 | T5 | T5-RET-VOID | pending_sme | 1. Merchandise Return [7] → 2. Void of a Merchandise Return [U] | FLAG 0 (p.546) | `TD-TC-002162.step1.return.json`<br>`TD-TC-002162.step2.void-return.json` | p.546, p.511, p.83 |
| BR1_TS28_TC3 | TC-002163 | T11 | T11-DISABLED-RETURN | pending_sme | device (no host message) | N/A — | — (device: no host message) | p.520, p.511, p.83 |

### BR1_TS29 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_CONTACT`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-002164 | T1 | T1-BAL | ready | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002164.json` | p.546, p.511, p.83, p.161 |

### BR1_TS30 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_CONTACTLESS`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-002165 | T1 | T1-BAL | ready | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002165.json` | p.546, p.511, p.83, p.161 |

### BR1_TS31 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-MAGSTRIPE`, status `spec_implied`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-002166 | T1 | T1-BAL | ready | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002166.json` | p.546, p.511, p.83, p.81 |

### BR1_TS32 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-002167 | T1 | T1-BAL | ready | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002167.json` | p.546, p.511, p.83, p.86 |

### BR1_TS33 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-MANUAL`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-002168 | T1 | T1-BAL | ready | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002168.json` | p.546, p.511, p.83, p.81 |

### BR1_TS34 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_FALLBACK`, status `valid`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-002169 | T1 | T1-BAL | ready | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002169.json` | p.546, p.511, p.83, p.162 |

### BR1_TS35 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CREDENTIAL_ON_FILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-002170 | T1 | T1-BAL | pending_sme | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002170.json` | p.546, p.511, p.83, p.638 |

### BR1_TS36 — BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device

Scenario `TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CONTACTLESS_MOBILE`, status `unconfirmed`, requirement BR1 (`BR-12B94100`).

| Test case | ID | Type | Template | Status | Steps | Expected | Test data | Spec basis |
|---|---|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-002171 | T1 | T1-BAL | pending_sme | 1. Balance Inquiry [E] | FLAG — | `TD-TC-002171.json` | p.546, p.511, p.83 |

## 4. Follow-on combinations → test cases

Follow-on categories (reversal, time-out reversal, cancellation, void, ...) are steps inside the flow test cases of their original scenario, not scenarios of their own. A combination is traced to every test case of the same entry mode that has a later step of that category.

| Leg | Combination | Category | Entry mode | Status | Flows (spec) | Exercised by |
|---|---|---|---|---|---|---|
| BR1_LEG1 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-EMV_CONTACT` | Void of a Merchandise Return | EMV Contact Chip | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-EMV-RETURN-VOID] | BR1_TS21_TC2 |
| BR1_LEG2 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-EMV_CONTACTLESS` | Void of a Merchandise Return | EMV Contactless Chip | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-EMV-RETURN-VOID] | BR1_TS22_TC2 |
| BR1_LEG3 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-MAGSTRIPE` | Void of a Merchandise Return | Magnetic Stripe | spec_implied | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS23_TC2 |
| BR1_LEG4 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-CONTACTLESS_MAGSTRIPE` | Void of a Merchandise Return | Contactless Magnetic Stripe | valid | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS24_TC2 |
| BR1_LEG5 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-MANUAL` | Void of a Merchandise Return | Manual / Keyed | valid | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS25_TC2 |
| BR1_LEG6 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-EMV_FALLBACK` | Void of a Merchandise Return | Chip Fallback | valid | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS26_TC2 |
| BR1_LEG7 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-CREDENTIAL_ON_FILE` | Void of a Merchandise Return | Credential on File | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS27_TC2 |
| BR1_LEG8 | `TS-ATL105-CREDIT-MASTERCARD-VOID_RETURN-CONTACTLESS_MOBILE` | Void of a Merchandise Return | Contactless Mobile Commerce Device | unconfirmed | follow_on: Merchandise Return (7) -> Void of a Merchandise Return (U) [FLOW-RETURN-VOID] | BR1_TS28_TC2 |
| BR1_LEG9 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-EMV_CONTACT` | Purchase Reversal | EMV Contact Chip | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-EMV-SALE-VOID] | BR1_TS1_TC7, BR1_TS1_TC10, BR1_TS1_TC12, BR1_TS1_TC22, BR1_TS1_TC32, BR1_TS1_TC33, BR1_TS11_TC9, BR1_TS11_TC13 |
| BR1_LEG10 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-EMV_CONTACTLESS` | Purchase Reversal | EMV Contactless Chip | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-EMV-SALE-VOID] | BR1_TS2_TC7, BR1_TS2_TC10, BR1_TS2_TC12, BR1_TS2_TC22, BR1_TS2_TC32, BR1_TS2_TC33, BR1_TS12_TC9, BR1_TS12_TC13 |
| BR1_LEG11 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-MAGSTRIPE` | Purchase Reversal | Magnetic Stripe | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS3_TC6, BR1_TS3_TC9, BR1_TS3_TC11, BR1_TS3_TC22, BR1_TS3_TC32, BR1_TS3_TC33, BR1_TS13_TC8, BR1_TS13_TC12 |
| BR1_LEG12 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-CONTACTLESS_MAGSTRIPE` | Purchase Reversal | Contactless Magnetic Stripe | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS4_TC4, BR1_TS4_TC7, BR1_TS4_TC9, BR1_TS4_TC19, BR1_TS4_TC27, BR1_TS4_TC28, BR1_TS14_TC7, BR1_TS14_TC11 |
| BR1_LEG13 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-MANUAL` | Purchase Reversal | Manual / Keyed | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS5_TC6, BR1_TS5_TC9, BR1_TS5_TC11, BR1_TS5_TC21, BR1_TS5_TC29, BR1_TS5_TC31, BR1_TS5_TC32, BR1_TS15_TC8, BR1_TS15_TC12 |
| BR1_LEG14 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-EMV_FALLBACK` | Purchase Reversal | Chip Fallback | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS6_TC7, BR1_TS6_TC10, BR1_TS6_TC12, BR1_TS6_TC22, BR1_TS6_TC30, BR1_TS6_TC31, BR1_TS16_TC10, BR1_TS16_TC14 |
| BR1_LEG15 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-CREDENTIAL_ON_FILE` | Purchase Reversal | Credential on File | valid | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS7_TC3, BR1_TS7_TC5, BR1_TS7_TC20, BR1_TS7_TC28, BR1_TS7_TC30, BR1_TS7_TC31, BR1_TS17_TC5, BR1_TS17_TC9 |
| BR1_LEG16 | `TS-ATL105-CREDIT-MASTERCARD-REVERSAL-CONTACTLESS_MOBILE` | Purchase Reversal | Contactless Mobile Commerce Device | unconfirmed | follow_on: Purchase/Capture (0/4) -> Purchase Reversal (8) [FLOW-SALE-VOID] | BR1_TS8_TC4, BR1_TS8_TC7, BR1_TS8_TC9, BR1_TS8_TC19, BR1_TS8_TC27, BR1_TS8_TC28, BR1_TS18_TC7, BR1_TS18_TC11 |
| BR1_LEG17 | `TS-ATL105-CREDIT-MASTERCARD-MOTO_REVERSAL-MANUAL` | Mail/Phone Reversal | Manual / Keyed | valid | follow_on: Mail/Phone Purchase (6) -> Mail/Phone Reversal (C) [FLOW-MOTO-REVERSAL] | BR1_TS9_TC2, BR1_TS9_TC5 |
| BR1_LEG18 | `TS-ATL105-CREDIT-MASTERCARD-MOTO_REVERSAL-CREDENTIAL_ON_FILE` | Mail/Phone Reversal | Credential on File | unconfirmed | follow_on: Mail/Phone Purchase (6) -> Mail/Phone Reversal (C) [FLOW-MOTO-REVERSAL] | BR1_TS10_TC2, BR1_TS10_TC5 |
| BR1_LEG19 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-EMV_CONTACT` | Time-out Reversal | EMV Contact Chip | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-EMV-TIMEOUT-REVERSAL] | BR1_TS1_TC8, BR1_TS1_TC13, BR1_TS1_TC14, BR1_TS1_TC15, BR1_TS1_TC16, BR1_TS1_TC17, BR1_TS1_TC18, BR1_TS1_TC20, BR1_TS1_TC24, BR1_TS1_TC32, BR1_TS11_TC10, BR1_TS11_TC12 |
| BR1_LEG20 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-EMV_CONTACTLESS` | Time-out Reversal | EMV Contactless Chip | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-EMV-TIMEOUT-REVERSAL] | BR1_TS2_TC8, BR1_TS2_TC13, BR1_TS2_TC14, BR1_TS2_TC15, BR1_TS2_TC16, BR1_TS2_TC17, BR1_TS2_TC18, BR1_TS2_TC20, BR1_TS2_TC24, BR1_TS2_TC32, BR1_TS12_TC10, BR1_TS12_TC12 |
| BR1_LEG21 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-MAGSTRIPE` | Time-out Reversal | Magnetic Stripe | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS3_TC7, BR1_TS3_TC12, BR1_TS3_TC13, BR1_TS3_TC14, BR1_TS3_TC15, BR1_TS3_TC16, BR1_TS3_TC17, BR1_TS3_TC18, BR1_TS3_TC20, BR1_TS3_TC24, BR1_TS3_TC32, BR1_TS13_TC9 … (+1) |
| BR1_LEG22 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-CONTACTLESS_MAGSTRIPE` | Time-out Reversal | Contactless Magnetic Stripe | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS4_TC5, BR1_TS4_TC10, BR1_TS4_TC11, BR1_TS4_TC12, BR1_TS4_TC13, BR1_TS4_TC14, BR1_TS4_TC15, BR1_TS4_TC17, BR1_TS4_TC21, BR1_TS4_TC27, BR1_TS14_TC8, BR1_TS14_TC10 |
| BR1_LEG23 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-MANUAL` | Time-out Reversal | Manual / Keyed | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] / follow_on: Mail/Phone Purchase (6) -> Time-out Reversal (Z) [FLOW-TIMEOUT-R | BR1_TS5_TC7, BR1_TS5_TC12, BR1_TS5_TC13, BR1_TS5_TC14, BR1_TS5_TC15, BR1_TS5_TC16, BR1_TS5_TC17, BR1_TS5_TC19, BR1_TS5_TC23, BR1_TS5_TC31, BR1_TS9_TC3, BR1_TS15_TC9 … (+1) |
| BR1_LEG24 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-EMV_FALLBACK` | Time-out Reversal | Chip Fallback | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS6_TC8, BR1_TS6_TC13, BR1_TS6_TC14, BR1_TS6_TC15, BR1_TS6_TC16, BR1_TS6_TC17, BR1_TS6_TC18, BR1_TS6_TC20, BR1_TS6_TC24, BR1_TS6_TC30, BR1_TS16_TC11, BR1_TS16_TC13 |
| BR1_LEG25 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-CREDENTIAL_ON_FILE` | Time-out Reversal | Credential on File | valid | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] / follow_on: Mail/Phone Purchase (6) -> Time-out Reversal (Z) [FLOW-TIMEOUT-R | BR1_TS7_TC6, BR1_TS7_TC7, BR1_TS7_TC8, BR1_TS7_TC9, BR1_TS7_TC10, BR1_TS7_TC11, BR1_TS7_TC13, BR1_TS7_TC22, BR1_TS7_TC30, BR1_TS10_TC3, BR1_TS17_TC6, BR1_TS17_TC8 |
| BR1_LEG26 | `TS-ATL105-CREDIT-MASTERCARD-TIMEOUT_REVERSAL-CONTACTLESS_MOBILE` | Time-out Reversal | Contactless Mobile Commerce Device | unconfirmed | follow_on: Purchase/Capture (0/4) -> Time-out Reversal (Z) [FLOW-TIMEOUT-REVERSAL] | BR1_TS8_TC5, BR1_TS8_TC10, BR1_TS8_TC11, BR1_TS8_TC12, BR1_TS8_TC13, BR1_TS8_TC14, BR1_TS8_TC15, BR1_TS8_TC17, BR1_TS8_TC21, BR1_TS8_TC27, BR1_TS18_TC8, BR1_TS18_TC10 |
| BR1_LEG27 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-EMV_CONTACT` | Cancellation | EMV Contact Chip | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-EMV-AUTH-CANCELLATION] | BR1_TS11_TC5, BR1_TS11_TC8, BR1_TS11_TC14 |
| BR1_LEG28 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-EMV_CONTACTLESS` | Cancellation | EMV Contactless Chip | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-EMV-AUTH-CANCELLATION] | BR1_TS12_TC5, BR1_TS12_TC8, BR1_TS12_TC14 |
| BR1_LEG29 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-MAGSTRIPE` | Cancellation | Magnetic Stripe | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS13_TC4, BR1_TS13_TC7, BR1_TS13_TC13 |
| BR1_LEG30 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-CONTACTLESS_MAGSTRIPE` | Cancellation | Contactless Magnetic Stripe | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS14_TC3, BR1_TS14_TC6, BR1_TS14_TC12 |
| BR1_LEG31 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-MANUAL` | Cancellation | Manual / Keyed | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] / follow_on: Mail/Phone Authorization Only (B) -> Cancellation (S) [FLOW-AUTH-C | BR1_TS15_TC4, BR1_TS15_TC7, BR1_TS15_TC13, BR1_TS19_TC2 |
| BR1_LEG32 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-EMV_FALLBACK` | Cancellation | Chip Fallback | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS16_TC6, BR1_TS16_TC9, BR1_TS16_TC15 |
| BR1_LEG33 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-CREDENTIAL_ON_FILE` | Cancellation | Credential on File | valid | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] / follow_on: Mail/Phone Authorization Only (B) -> Cancellation (S) [FLOW-AUTH-C | BR1_TS17_TC4, BR1_TS17_TC10, BR1_TS20_TC2 |
| BR1_LEG34 | `TS-ATL105-CREDIT-MASTERCARD-CANCELLATION-CONTACTLESS_MOBILE` | Cancellation | Contactless Mobile Commerce Device | unconfirmed | follow_on: Authorization Only (3/5) -> Cancellation (S) [FLOW-AUTH-CANCELLATION] | BR1_TS18_TC3, BR1_TS18_TC6, BR1_TS18_TC12 |

## 5. Gaps and unresolved links

### Scenarios without test cases

- None.

### Test cases without test data

These test cases have no host message (device-only checks, or blocked by a spec gap), so they have no request test data.

- BR1_TS1_TC36 TC-001757 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS1_TC37 TC-001758 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS11_TC15 TC-002034 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS12_TC15 TC-002049 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS13_TC14 TC-002063 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Expired card → rejected at device (no host message)
- BR1_TS13_TC15 TC-002064 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS14_TC13 TC-002077 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Expired card → rejected at device (no host message)
- BR1_TS14_TC14 TC-002078 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS15_TC14 TC-002092 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Expired card → rejected at device (no host message)
- BR1_TS15_TC15 TC-002093 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS16_TC16 TC-002109 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS17_TC11 TC-002120 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS18_TC13 TC-002133 (device, pending_sme): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Preauthorization disabled (card type 168) → not offered at the device
- BR1_TS19_TC3 TC-002136 (host, blocked_spec_gap): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Completion
- BR1_TS2_TC36 TC-001794 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS2_TC37 TC-001795 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS20_TC3 TC-002139 (host, blocked_spec_gap): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Completion
- BR1_TS21_TC3 TC-002142 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS22_TC3 TC-002145 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS23_TC3 TC-002148 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS24_TC3 TC-002151 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS25_TC3 TC-002154 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS26_TC3 TC-002157 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS27_TC3 TC-002160 (device, pending_sme): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS28_TC3 TC-002163 (device, pending_sme): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Return/Refund disabled (card type 166) → not offered at the device
- BR1_TS3_TC36 TC-001821 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired card → rejected at device (no host message)
- BR1_TS3_TC37 TC-001832 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS3_TC38 TC-001833 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS4_TC31 TC-001855 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired card → rejected at device (no host message)
- BR1_TS4_TC32 TC-001865 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS4_TC33 TC-001866 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS5_TC35 TC-001892 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Expired card → rejected at device (no host message)
- BR1_TS5_TC36 TC-001893 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed entry before 3 swipes → refused at device
- BR1_TS5_TC37 TC-001903 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS5_TC38 TC-001904 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed inside sale with card type 174 → device prompts for card verification data
- BR1_TS5_TC39 TC-001905 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS6_TC34 TC-001939 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS6_TC35 TC-001940 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS7_TC34 TC-001974 (device, requires_review): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS7_TC35 TC-001975 (device, ready): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Void/Reversal disabled (card type 167) → not offered at the device
- BR1_TS8_TC31 TC-002006 (device, pending_sme): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Store and Forward blocking window (card type 173) → no off-line approval inside the window
- BR1_TS8_TC32 TC-002007 (device, pending_sme): BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Void/Reversal disabled (card type 167) → not offered at the device

### Missing test data files

- None.

### Unresolved links

- None. Every test case resolves to its scenario and to this Business Requirement.
