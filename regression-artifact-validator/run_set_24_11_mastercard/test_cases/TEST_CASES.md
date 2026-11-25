# Test cases - BR1 BUYPASS ATL105 MasterCard Credit

Build `mastercard-credit-generated-491d46d2cd`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` generated-2026-11-13.2.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 196 |
| Test cases | 973 |
| By status | needs_client_input: 585, requires_review: 236, blocked_verification: 2, pending_sme: 149, ready: 1 |
| By polarity | Positive: 699, Negative: 271, Boundary: 3 |
| Verification | PASS: 971, REJECT: 2 |
| Verification level | PASS: 971, REJECT: 2 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 566, FLAG: 176, INFER: 231 |
| Scenarios with test cases | 100 / 196 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| happy_path Happy path | 216 |
| alternate_path Alternate path | 67 |
| single_rule_broken Single rule broken | 120 |
| decision_table Decision table | 54 |
| valid_flow Valid flow | 144 |
| invalid_transition Invalid state transition | 70 |
| fault_recovery Fault / recovery | 26 |
| idempotency_retry Idempotency / duplicate / retry | 8 |
| running_total Running total / state | 74 |
| pairwise Interaction (pairwise) | 14 |
| response_check Response / oracle check | 161 |
| time_based Time-based | 10 |
| issuer_outcome Issuer outcome | 9 |

## BR1 BUYPASS ATL105 MasterCard Credit


### BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-000956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Required field EMV Chip Data omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-000957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Chip Data one byte over its documented maximum length → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC3 | TC-000960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Variable Information one byte over its documented maximum length → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC4 | TC-000969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Card Sequence Number value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-000950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS1_TC6 | TC-000958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | needs_client_input |
| BR1_TS1_TC7 | TC-000001 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC8 | TC-000002 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC9 | TC-000003 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC10 | TC-000004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC11 | TC-000005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC12 | TC-000006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-000007 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC14 | TC-000008 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC15 | TC-000009 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC16 | TC-000010 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-000011 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC18 | TC-000012 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC19 | TC-000013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC20 | TC-000014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC21 | TC-000015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC22 | TC-000016 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC23 | TC-000017 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC24 | TC-000018 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC25 | TC-000019 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC26 | TC-000020 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC27 | TC-000021 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC28 | TC-000022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC29 | TC-000023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC30 | TC-000024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC31 | TC-000025 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC32 | TC-000026 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC33 | TC-000027 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC34 | TC-000028 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC35 | TC-000029 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC36 | TC-000030 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC37 | TC-000031 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC38 | TC-000032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC39 | TC-000033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC40 | TC-000034 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC41 | TC-000035 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC42 | TC-000036 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC43 | TC-000037 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS1_TC44 | TC-000038 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS1_TC45 | TC-000039 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS1_TC46 | TC-000040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture on a POS terminal with online pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC47 | TC-000041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC48 | TC-000042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'All chip data elements in the authorization response'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC49 | TC-000043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'RQ 3100'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC50 | TC-000044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'A maximum of one Issuer Script'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC51 | TC-000045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC52 | TC-000046 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'RQ 3100'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC53 | TC-000047 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'If Offline CAM is not performed, transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC54 | TC-000048 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC55 | TC-000049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Do not mix 1GAC and 2GAC'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC56 | TC-000050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC57 | TC-000051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC58 | TC-000052 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS1_TC59 | TC-000053 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with AVS or NVS or Both Information (match), result returned → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |

### BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-000959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture CA Public Key File Checksum exactly at its documented maximum length (25 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-000970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture Required field Prompt Code omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC3 | TC-000054 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-000055 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC5 | TC-000056 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-000057 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC7 | TC-000058 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC8 | TC-000059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC9 | TC-000060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC10 | TC-000061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC11 | TC-000062 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC12 | TC-000063 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC13 | TC-000064 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC14 | TC-000065 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC15 | TC-000066 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC16 | TC-000067 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC17 | TC-000068 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC18 | TC-000069 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC19 | TC-000070 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC20 | TC-000071 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC21 | TC-000072 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC22 | TC-000073 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC23 | TC-000074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC24 | TC-000075 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC25 | TC-000076 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC26 | TC-000077 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC27 | TC-000078 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-000079 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-000080 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC30 | TC-000081 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC31 | TC-000082 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC32 | TC-000083 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC33 | TC-000084 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC34 | TC-000085 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC35 | TC-000086 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC36 | TC-000087 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC37 | TC-000088 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC38 | TC-000089 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC39 | TC-000090 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC40 | TC-000091 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS2_TC41 | TC-000092 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS2_TC42 | TC-000093 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS2_TC43 | TC-000094 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'All chip data elements in the authorization response'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC44 | TC-000095 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'RQ 3100'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC45 | TC-000096 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'A maximum of one Issuer Script'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC46 | TC-000097 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'RQ 3800'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC47 | TC-000098 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved [about 'RQ 3900'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC48 | TC-000099 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC49 | TC-000100 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Approved outcome'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC50 | TC-000101 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'If Offline CAM is not performed, transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC51 | TC-000102 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC52 | TC-000103 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Do not mix 1GAC and 2GAC'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC53 | TC-000104 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC54 | TC-000105 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC55 | TC-000106 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS2_TC56 | TC-000107 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with AVS or NVS or Both Information (match), result returned → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |

### BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-000972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS3_TC2 | TC-000108 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal (Purchase Reversal with Transaction Link Identifier) → approved | happy_path | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC3 | TC-000109 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal (Purchase Reversal with Universal Unique Identifier) → approved | happy_path | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC4 | TC-000110 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC5 | TC-000111 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-000112 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC7 | TC-000113 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC8 | TC-000114 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC9 | TC-000115 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC10 | TC-000116 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC11 | TC-000117 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC12 | TC-000118 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC13 | TC-000119 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC14 | TC-000120 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Access Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC15 | TC-000121 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC16 | TC-000122 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC17 | TC-000123 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC18 | TC-000124 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Chip Data Length → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC19 | TC-000125 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Request Data Segment → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC20 | TC-000126 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC21 | TC-000127 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC22 | TC-000128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC23 | TC-000129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC24 | TC-000130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC25 | TC-000131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC26 | TC-000132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC27 | TC-000133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC28 | TC-000134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC29 | TC-000135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC30 | TC-000136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC31 | TC-000137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC32 | TC-000138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with track = 1 → approved | decision_table | 0 | 1: 0 (ASSERT) | blocked_verification |
| BR1_TS3_TC33 | TC-000139 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC34 | TC-000140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC35 | TC-000141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC36 | TC-000142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC37 | TC-000143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC38 | TC-000144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC39 | TC-000145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC40 | TC-000146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC41 | TC-000147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC42 | TC-000148 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS3_TC43 | TC-000149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS3_TC44 | TC-000150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS3_TC45 | TC-000151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture on a POS terminal with online pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC46 | TC-000152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC47 | TC-000153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC48 | TC-000154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC49 | TC-000155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC50 | TC-000156 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS3_TC51 | TC-000157 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Account Verification with cof = initial (support to confirm) → approved | happy_path | A | 1: approved (any of 0, 2) (INFER) | requires_review |

### BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-000158 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC2 | TC-000159 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC3 | TC-000160 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC4 | TC-000161 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC5 | TC-000162 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC6 | TC-000163 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC7 | TC-000164 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC8 | TC-000165 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC9 | TC-000166 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC10 | TC-000167 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC11 | TC-000168 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC12 | TC-000169 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC13 | TC-000170 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC14 | TC-000171 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC15 | TC-000172 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC16 | TC-000173 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC17 | TC-000174 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC18 | TC-000175 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC19 | TC-000176 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC20 | TC-000177 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC21 | TC-000178 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC22 | TC-000179 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC23 | TC-000180 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC24 | TC-000181 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC25 | TC-000182 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC26 | TC-000183 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with track = 1 → approved | decision_table | 0 | 1: 0 (ASSERT) | blocked_verification |
| BR1_TS4_TC27 | TC-000184 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC28 | TC-000185 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC29 | TC-000186 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC30 | TC-000187 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC31 | TC-000188 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC32 | TC-000189 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC33 | TC-000190 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC34 | TC-000191 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC35 | TC-000192 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC36 | TC-000193 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS4_TC37 | TC-000194 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS4_TC38 | TC-000195 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS4_TC39 | TC-000196 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture on a POS terminal with online pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC40 | TC-000197 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC41 | TC-000198 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC42 | TC-000199 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC43 | TC-000200 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC44 | TC-000201 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |

### BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-000202 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC2 | TC-000203 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC3 | TC-000204 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC4 | TC-000205 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC5 | TC-000206 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC6 | TC-000207 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC7 | TC-000208 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC8 | TC-000209 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC9 | TC-000210 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC10 | TC-000211 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC11 | TC-000212 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC12 | TC-000213 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC13 | TC-000214 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC14 | TC-000215 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC15 | TC-000216 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC16 | TC-000217 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC17 | TC-000218 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC18 | TC-000219 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC19 | TC-000220 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC20 | TC-000221 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC21 | TC-000222 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC22 | TC-000223 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC23 | TC-000224 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC24 | TC-000225 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incremental = incremental → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC25 | TC-000226 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC26 | TC-000227 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC27 | TC-000228 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with track = 1 → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC28 | TC-000229 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC29 | TC-000230 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC30 | TC-000231 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC31 | TC-000232 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC32 | TC-000233 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS5_TC33 | TC-000234 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC34 | TC-000235 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC35 | TC-000236 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC36 | TC-000237 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC37 | TC-000238 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC38 | TC-000239 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS5_TC39 | TC-000240 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS5_TC40 | TC-000241 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture on a POS terminal with online pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC41 | TC-000242 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC42 | TC-000243 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC43 | TC-000244 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC44 | TC-000245 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC45 | TC-000246 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS5_TC46 | TC-000247 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with AVS or NVS or Both Information (match), result returned → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |

### BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-000963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS6_TC2 | TC-000248 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC3 | TC-000249 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC4 | TC-000250 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC5 | TC-000251 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC6 | TC-000252 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC7 | TC-000253 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC8 | TC-000254 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC9 | TC-000255 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC10 | TC-000256 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC11 | TC-000257 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC12 | TC-000258 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC13 | TC-000259 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC14 | TC-000260 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC15 | TC-000261 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC16 | TC-000262 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC17 | TC-000263 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC18 | TC-000264 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Chip Data Length → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC19 | TC-000265 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Request Data Segment → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC20 | TC-000266 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC21 | TC-000267 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC22 | TC-000268 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC23 | TC-000269 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC24 | TC-000270 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC25 | TC-000271 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC26 | TC-000272 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC27 | TC-000273 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC28 | TC-000274 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC29 | TC-000275 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC30 | TC-000276 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC31 | TC-000277 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC32 | TC-000278 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC33 | TC-000279 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC34 | TC-000280 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC35 | TC-000281 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC36 | TC-000282 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC37 | TC-000283 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS6_TC38 | TC-000284 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC39 | TC-000285 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC40 | TC-000286 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC41 | TC-000287 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC42 | TC-000288 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC43 | TC-000289 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS6_TC44 | TC-000290 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS6_TC45 | TC-000291 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture on a POS terminal with online pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC46 | TC-000292 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC47 | TC-000293 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC48 | TC-000294 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Do not mix 1GAC and 2GAC'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC49 | TC-000295 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Fallback not allowed if transaction is offline declined'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC50 | TC-000296 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC51 | TC-000297 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC52 | TC-000298 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS6_TC53 | TC-000299 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with AVS or NVS or Both Information (match), result returned → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |

### BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-000300 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal (Purchase Reversal with Suspected Fraud Reversal Indicator Information) → approved | happy_path | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC2 | TC-000301 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC3 | TC-000302 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → approved [about 'Prompt Code example 020 identifies Transaction Type'] | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC4 | TC-000303 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC5 | TC-000304 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC6 | TC-000305 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC7 | TC-000306 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC8 | TC-000307 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-000308 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC10 | TC-000309 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC11 | TC-000310 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC12 | TC-000311 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC13 | TC-000312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC14 | TC-000313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC15 | TC-000314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC16 | TC-000315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC17 | TC-000316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC18 | TC-000317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC19 | TC-000318 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC20 | TC-000319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC21 | TC-000320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC22 | TC-000321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC23 | TC-000322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC24 | TC-000323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with initiation = M → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC25 | TC-000324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC26 | TC-000325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with special payment = I → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC27 | TC-000326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with special payment = R → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC28 | TC-000327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC29 | TC-000328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC30 | TC-000329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC31 | TC-000330 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC32 | TC-000331 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS7_TC33 | TC-000332 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC34 | TC-000333 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC35 | TC-000334 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC36 | TC-000335 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC37 | TC-000336 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC38 | TC-000337 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS7_TC39 | TC-000338 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS7_TC40 | TC-000339 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC41 | TC-000340 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC42 | TC-000341 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'When initiating MIT recurring transaction, transaction'] | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC43 | TC-000342 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC44 | TC-000343 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC45 | TC-000344 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS7_TC46 | TC-000345 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with CVV Information (match), result returned → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC47 | TC-000346 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with CVV Information (no match), result returned → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-000347 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-000348 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC3 | TC-000349 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC4 | TC-000350 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC5 | TC-000351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC6 | TC-000352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC7 | TC-000353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC8 | TC-000354 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC9 | TC-000355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC10 | TC-000356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC11 | TC-000357 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC12 | TC-000358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC13 | TC-000359 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC14 | TC-000360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC15 | TC-000361 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC16 | TC-000362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC17 | TC-000363 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC18 | TC-000364 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC19 | TC-000365 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC20 | TC-000366 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC21 | TC-000367 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC22 | TC-000368 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC23 | TC-000369 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC24 | TC-000370 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC25 | TC-000371 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC26 | TC-000372 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC27 | TC-000373 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC28 | TC-000374 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal (partially approved, then Purchase Reversal for the approved amount) → approved | valid_flow | 0 -> 8 | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC29 | TC-000375 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: F (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC30 | TC-000376 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS8_TC31 | TC-000377 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 0 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC32 | TC-000378 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (late_response), then Time-out Reversal (answered late, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC33 | TC-000379 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (late_response), then Time-out Reversal (late partial approval, then Time-out Reversal) → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC34 | TC-000380 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC35 | TC-000381 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC36 | TC-000382 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS8_TC37 | TC-000383 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS8_TC38 | TC-000384 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture on a POS terminal with online pin → approved | pairwise | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC39 | TC-000385 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC40 | TC-000386 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC41 | TC-000387 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC42 | TC-000388 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC43 | TC-000389 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-000946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase Required field Partial Approval Indicator omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC2 | TC-000390 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal (Mail/Phone Reversal with Transaction Link Identifier) → approved | happy_path | 6 -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS9_TC3 | TC-000391 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal (Mail/Phone Reversal with Universal Unique Identifier) → approved | happy_path | 6 -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS9_TC4 | TC-000392 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase → approved | happy_path | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC5 | TC-000393 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-000394 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC7 | TC-000395 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC8 | TC-000396 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase with incremental = completion → approved | decision_table | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC9 | TC-000397 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase with incremental = original → approved | decision_table | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC10 | TC-000398 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS9_TC11 | TC-000399 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 6 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS9_TC12 | TC-000400 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 6 -> C -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS9_TC13 | TC-000401 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS9_TC14 | TC-000402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 6 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS9_TC15 | TC-000403 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals include it) → approved | running_total | 6 -> C -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS9_TC16 | TC-000404 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS9_TC17 | TC-000405 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS10 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-000406 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC2 | TC-000407 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase → approved [about 'Electronic Mail Request for retrieval can contain'] | happy_path | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC3 | TC-000408 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-000409 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-000410 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (no response), then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-000411 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase with incremental = original → approved | decision_table | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC7 | TC-000412 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC8 | TC-000413 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (no response), then Time-out Reversal → approved [technique Valid flow] | valid_flow | 6 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC9 | TC-000414 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 6 -> C -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC10 | TC-000415 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC11 | TC-000416 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (no response), then Time-out Reversal → approved [technique Fault / recovery] | fault_recovery | 6 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC12 | TC-000417 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals include it) → approved | running_total | 6 -> C -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS10_TC13 | TC-000418 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS10_TC14 | TC-000419 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-000949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Required field EMV Chip Data Length sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC2 | TC-000947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Partial Approval Indicator value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC3 | TC-000961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Variable Information Indicator value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC4 | TC-000420 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC5 | TC-000421 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC6 | TC-000422 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS11_TC7 | TC-000423 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS11_TC8 | TC-000424 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS11_TC9 | TC-000425 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS11_TC10 | TC-000426 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS11_TC11 | TC-000427 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC12 | TC-000428 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contact Chip @ General Retail · attended POS #1] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC13 | TC-000429 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contact Chip @ General Retail · attended POS #1] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC14 | TC-000430 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS11_TC15 | TC-000431 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC16 | TC-000432 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contact Chip @ General Retail · attended POS #2] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC17 | TC-000433 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contact Chip @ General Retail · attended POS #2] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC18 | TC-000434 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS11_TC19 | TC-000435 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS11_TC20 | TC-000436 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS11_TC21 | TC-000437 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS11_TC22 | TC-000438 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS11_TC23 | TC-000439 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only on a POS terminal with offline pin → approved | pairwise | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC24 | TC-000440 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC25 | TC-000441 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-000442 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC2 | TC-000443 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC3 | TC-000444 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS12_TC4 | TC-000445 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS12_TC5 | TC-000446 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS12_TC6 | TC-000447 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS12_TC7 | TC-000448 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS12_TC8 | TC-000449 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC9 | TC-000450 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contactless Chip @ General Retail · attended POS #1] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC10 | TC-000451 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contactless Chip @ General Retail · attended POS #1] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC11 | TC-000452 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS12_TC12 | TC-000453 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC13 | TC-000454 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contactless Chip @ General Retail · attended POS #2] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC14 | TC-000455 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contactless Chip @ General Retail · attended POS #2] | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC15 | TC-000456 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS12_TC16 | TC-000457 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS12_TC17 | TC-000458 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS12_TC18 | TC-000459 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS12_TC19 | TC-000460 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS12_TC20 | TC-000461 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only on a POS terminal with offline pin → approved | pairwise | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC21 | TC-000462 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC22 | TC-000463 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS13 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-000464 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation (Cancellation with Transaction Link Identifier) → approved | happy_path | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS13_TC2 | TC-000465 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture (Purchase/Capture with Transaction Link Identifier) → approved | happy_path | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC3 | TC-000466 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation (Cancellation with Universal Unique Identifier) → approved | happy_path | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS13_TC4 | TC-000467 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture (Purchase/Capture with Universal Unique Identifier) → approved | happy_path | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC5 | TC-000468 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC6 | TC-000469 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC7 | TC-000470 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS13_TC8 | TC-000471 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS13_TC9 | TC-000472 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS13_TC10 | TC-000473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS13_TC11 | TC-000474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS13_TC12 | TC-000475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC13 | TC-000476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC14 | TC-000477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC15 | TC-000478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS13_TC16 | TC-000479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC17 | TC-000480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS13_TC18 | TC-000481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS13_TC19 | TC-000482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS13_TC20 | TC-000483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS13_TC21 | TC-000484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS13_TC22 | TC-000485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC23 | TC-000486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS14 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-000487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC2 | TC-000488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC3 | TC-000489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS14_TC4 | TC-000490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS14_TC5 | TC-000491 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS14_TC6 | TC-000492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS14_TC7 | TC-000493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS14_TC8 | TC-000494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS14_TC9 | TC-000495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS14_TC10 | TC-000496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS14_TC11 | TC-000497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS14_TC12 | TC-000498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC13 | TC-000499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS14_TC14 | TC-000500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS14_TC15 | TC-000501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS14_TC16 | TC-000502 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS14_TC17 | TC-000503 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS14_TC18 | TC-000504 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC19 | TC-000505 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS15 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-000506 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC2 | TC-000507 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC3 | TC-000508 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC4 | TC-000509 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC5 | TC-000510 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC6 | TC-000511 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC7 | TC-000512 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS15_TC8 | TC-000513 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS15_TC9 | TC-000514 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS15_TC10 | TC-000515 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS15_TC11 | TC-000516 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS15_TC12 | TC-000517 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC13 | TC-000518 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS15_TC14 | TC-000519 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS15_TC15 | TC-000520 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS15_TC16 | TC-000521 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS15_TC17 | TC-000522 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS15_TC18 | TC-000523 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS15_TC19 | TC-000524 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS16 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-000525 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC2 | TC-000526 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC3 | TC-000527 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC4 | TC-000528 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC5 | TC-000529 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC6 | TC-000530 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC7 | TC-000531 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS16_TC8 | TC-000532 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS16_TC9 | TC-000533 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS16_TC10 | TC-000534 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS16_TC11 | TC-000535 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS16_TC12 | TC-000536 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC13 | TC-000537 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS16_TC14 | TC-000538 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS16_TC15 | TC-000539 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS16_TC16 | TC-000540 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS16_TC17 | TC-000541 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS16_TC18 | TC-000542 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS16_TC19 | TC-000543 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS17 BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-000544 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → approved [about 'Electronic journal should contain all approved transactions, excluding'] | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC2 | TC-000545 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC3 | TC-000546 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC4 | TC-000547 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC5 | TC-000548 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC6 | TC-000549 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC7 | TC-000550 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC8 | TC-000551 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS17_TC9 | TC-000552 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS17_TC10 | TC-000553 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS17_TC11 | TC-000554 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS17_TC12 | TC-000555 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS17_TC13 | TC-000556 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC14 | TC-000557 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS17_TC15 | TC-000558 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation after 72 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS17_TC16 | TC-000559 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS17_TC17 | TC-000560 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS17_TC18 | TC-000561 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS17_TC19 | TC-000562 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS17_TC20 | TC-000563 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-000564 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-000565 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC3 | TC-000566 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC4 | TC-000567 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC5 | TC-000568 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC6 | TC-000569 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC7 | TC-000570 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS18_TC8 | TC-000571 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC9 | TC-000572 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC10 | TC-000573 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC11 | TC-000574 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 3 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS18_TC12 | TC-000575 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 3 -> 3 | 1: F (FLAG) ; 2: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC13 | TC-000576 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS18_TC14 | TC-000577 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS18_TC15 | TC-000578 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS18_TC16 | TC-000579 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS18_TC17 | TC-000580 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS18_TC18 | TC-000581 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC19 | TC-000582 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS19 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-000583 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only → approved | happy_path | B | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS19_TC2 | TC-000584 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC3 | TC-000585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC4 | TC-000586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC5 | TC-000587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC6 | TC-000588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS19_TC7 | TC-000589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase → approved | valid_flow | B -> 6 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS19_TC8 | TC-000590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | B -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS19_TC9 | TC-000591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation after 24 hours, at the limit → approved | time_based | B -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS19_TC10 | TC-000592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS19_TC11 | TC-000593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | B -> 6 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS19_TC12 | TC-000594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS19_TC13 | TC-000595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase (card issuer declines) → declined | response_check | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS19_TC14 | TC-000596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS19_TC15 | TC-000597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns A (Address matches, ZIP does not) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC16 | TC-000598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns N (Neither ZIP nor address match) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC17 | TC-000599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns R (System unavailabl e, retry) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC18 | TC-000600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns S (AVS not supported) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC19 | TC-000601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns U (Information not available) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC20 | TC-000602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns W (For U.S., 9-digit ZIP matches, address does not. For non- US, ZIP matches, address does not) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC21 | TC-000603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns X (For U.S., all digits match. For non-U.S., ZIP and address match) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC22 | TC-000604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns Y (Address & 5-digit ZIP match) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC23 | TC-000605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns Z (5-digit ZIP matches, address does not) → issuer_decides | issuer_outcome | B | 1: issuer_decides (any of 0, 1, 2) (FLAG) | needs_client_input |
| BR1_TS19_TC24 | TC-000606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 05 (EMV Contact Chip, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | needs_client_input |
| BR1_TS19_TC25 | TC-000607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 07 (EMV Contactless Chip, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | needs_client_input |
| BR1_TS19_TC26 | TC-000608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 02 (Magnetic Stripe, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | needs_client_input |
| BR1_TS19_TC27 | TC-000609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 91 (Contactless Magnetic Stripe, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | needs_client_input |
| BR1_TS19_TC28 | TC-000610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 79 (Chip Fallback, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | needs_client_input |
| BR1_TS19_TC29 | TC-000611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 82 (Contactless Mobile Commerce Device, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | needs_client_input |

### BR1_TS20 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-000612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only → approved [about 'Electronic Mail Request for retrieval can contain'] | happy_path | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-000613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC3 | TC-000614 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC4 | TC-000615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC5 | TC-000616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC6 | TC-000617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC7 | TC-000618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS20_TC8 | TC-000619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase → approved | valid_flow | B -> 6 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS20_TC9 | TC-000620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | B -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS20_TC10 | TC-000621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation after 72 hours, at the limit → approved | time_based | B -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS20_TC11 | TC-000622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS20_TC12 | TC-000623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | B -> 6 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS20_TC13 | TC-000624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS20_TC14 | TC-000625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase (card issuer declines) → declined | response_check | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC15 | TC-000626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-000962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return Variable Information Indicator one byte over its documented maximum length (3) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS21_TC2 | TC-000627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS21_TC3 | TC-000628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC4 | TC-000629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC5 | TC-000630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS21_TC6 | TC-000631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS21_TC7 | TC-000632 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS21_TC8 | TC-000633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS21_TC9 | TC-000634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS21_TC10 | TC-000635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS22 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-000636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS22_TC2 | TC-000637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC3 | TC-000638 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC4 | TC-000639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS22_TC5 | TC-000640 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS22_TC6 | TC-000641 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS22_TC7 | TC-000642 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS22_TC8 | TC-000643 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS22_TC9 | TC-000644 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS23 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-000645 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS23_TC2 | TC-000646 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS23_TC3 | TC-000647 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS23_TC4 | TC-000648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS23_TC5 | TC-000649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS23_TC6 | TC-000650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS23_TC7 | TC-000651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS23_TC8 | TC-000652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS24 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-000653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (Void of a Merchandise Return with Transaction Link Identifier) → approved | happy_path | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS24_TC2 | TC-000654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (Void of a Merchandise Return with Universal Unique Identifier) → approved | happy_path | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS24_TC3 | TC-000655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS24_TC4 | TC-000656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS24_TC5 | TC-000657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS24_TC6 | TC-000658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS24_TC7 | TC-000659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS24_TC8 | TC-000660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS24_TC9 | TC-000661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS24_TC10 | TC-000662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS25 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-000663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS25_TC2 | TC-000664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS25_TC3 | TC-000665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS25_TC4 | TC-000666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS25_TC5 | TC-000667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS25_TC6 | TC-000668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS25_TC7 | TC-000669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS25_TC8 | TC-000670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS26 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-000671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS26_TC2 | TC-000672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS26_TC3 | TC-000673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS26_TC4 | TC-000674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS26_TC5 | TC-000675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS26_TC6 | TC-000676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS26_TC7 | TC-000677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS26_TC8 | TC-000678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS27 BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-000679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS27_TC2 | TC-000680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-000681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC4 | TC-000682 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS27_TC5 | TC-000683 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS27_TC6 | TC-000684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS27_TC7 | TC-000685 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS27_TC8 | TC-000686 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS28 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-000687 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS28_TC2 | TC-000688 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-000689 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC4 | TC-000690 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS28_TC5 | TC-000691 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS28_TC6 | TC-000692 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS28_TC7 | TC-000693 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS28_TC8 | TC-000694 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS29 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-000973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | not stated by the spec (FLAG) | needs_client_input |
| BR1_TS29_TC2 | TC-000948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry required Standard Message Data Segment omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS29_TC3 | TC-000695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS29_TC4 | TC-000696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS29_TC5 | TC-000697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS29_TC6 | TC-000698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS30 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-000699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS30_TC2 | TC-000700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS30_TC3 | TC-000701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS30_TC4 | TC-000702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS31 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-000703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS31_TC2 | TC-000704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS31_TC3 | TC-000705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS32 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-000706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS32_TC2 | TC-000707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS32_TC3 | TC-000708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS33 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-000709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS33_TC2 | TC-000710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS33_TC3 | TC-000711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS34 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-000712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS34_TC2 | TC-000713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS34_TC3 | TC-000714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS35 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-000715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS35_TC2 | TC-000716 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS35_TC3 | TC-000717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS36 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-000718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS36_TC2 | TC-000719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS36_TC3 | TC-000720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS47 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS47_TC1 | TC-000721 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS47_TC2 | TC-000722 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS48 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS48_TC1 | TC-000723 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS48_TC2 | TC-000724 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS49 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS49_TC1 | TC-000725 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS49_TC2 | TC-000726 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS50 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS50_TC1 | TC-000727 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS50_TC2 | TC-000728 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS51 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS51_TC1 | TC-000729 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC2 | TC-000730 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture with incremental = incremental → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC3 | TC-000731 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS52 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS52_TC1 | TC-000732 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS52_TC2 | TC-000733 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS53 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS53_TC1 | TC-000734 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS53_TC2 | TC-000735 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS54 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS54_TC1 | TC-000736 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC2 | TC-000737 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS55 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS55_TC1 | TC-000738 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS55_TC2 | TC-000739 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS56 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS56_TC1 | TC-000740 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS56_TC2 | TC-000741 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS57 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS57_TC1 | TC-000742 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS57_TC2 | TC-000743 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS58 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS58_TC1 | TC-000744 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC2 | TC-000745 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS59 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS59_TC1 | TC-000746 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS59_TC2 | TC-000747 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS60 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS60_TC1 | TC-000748 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS60_TC2 | TC-000749 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS147 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS147_TC1 | TC-000968 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Additional Information exactly at its documented maximum length (984 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC2 | TC-000954 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Product Code contains a character outside its character type (N) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS147_TC3 | TC-000750 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC4 | TC-000751 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC5 | TC-000752 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS147_TC6 | TC-000753 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC7 | TC-000754 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC8 | TC-000755 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS148 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS148_TC1 | TC-000971 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture Loyalty Track 2 Data one byte over its documented maximum length (38) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC2 | TC-000965 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture Information Byte value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC3 | TC-000756 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC4 | TC-000757 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC5 | TC-000758 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS148_TC6 | TC-000759 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC7 | TC-000760 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC8 | TC-000761 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS149 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS149_TC1 | TC-000762 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC2 | TC-000763 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC3 | TC-000764 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS149_TC4 | TC-000765 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC5 | TC-000766 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS150 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS150_TC1 | TC-000767 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC2 | TC-000768 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC3 | TC-000769 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS150_TC4 | TC-000770 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC5 | TC-000771 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS151 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS151_TC1 | TC-000772 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC2 | TC-000773 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC3 | TC-000774 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS151_TC4 | TC-000775 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC5 | TC-000776 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS152 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS152_TC1 | TC-000777 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC2 | TC-000778 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC3 | TC-000779 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS152_TC4 | TC-000780 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC5 | TC-000781 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS153 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS153_TC1 | TC-000782 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC2 | TC-000783 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC3 | TC-000784 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS153_TC4 | TC-000785 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC5 | TC-000786 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS154 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS154_TC1 | TC-000787 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC2 | TC-000788 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS155 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS155_TC1 | TC-000789 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC2 | TC-000790 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS156 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS156_TC1 | TC-000791 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC2 | TC-000792 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS157 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS157_TC1 | TC-000966 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authorization Only Required field Information Byte omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS157_TC2 | TC-000793 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC3 | TC-000794 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS158 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS158_TC1 | TC-000795 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC2 | TC-000796 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS159 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS159_TC1 | TC-000797 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC2 | TC-000798 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS160 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS160_TC1 | TC-000799 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC2 | TC-000800 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS161 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS161_TC1 | TC-000964 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return Purchase Code one byte over its documented maximum length (16) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS161_TC2 | TC-000967 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return Required field Information Byte sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS161_TC3 | TC-000801 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS162 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS162_TC1 | TC-000802 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS163 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS163_TC1 | TC-000803 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS164 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS164_TC1 | TC-000804 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS165 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS165_TC1 | TC-000805 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS166 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS166_TC1 | TC-000806 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS167 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS167_TC1 | TC-000807 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | pending_sme |

### BR1_TS168 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS168_TC1 | TC-000951 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture Product Amount contains a character outside its character type (N) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS168_TC2 | TC-000808 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC3 | TC-000809 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC4 | TC-000810 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC5 | TC-000811 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS168_TC6 | TC-000812 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS168_TC7 | TC-000813 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS168_TC8 | TC-000814 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC9 | TC-000815 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC10 | TC-000816 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC11 | TC-000817 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS169 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS169_TC1 | TC-000818 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC2 | TC-000819 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC3 | TC-000820 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC4 | TC-000821 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS169_TC5 | TC-000822 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS169_TC6 | TC-000823 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS169_TC7 | TC-000824 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC8 | TC-000825 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC9 | TC-000826 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC10 | TC-000827 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS170 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS170_TC1 | TC-000828 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC2 | TC-000829 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC3 | TC-000830 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC4 | TC-000831 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS170_TC5 | TC-000832 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS170_TC6 | TC-000833 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS170_TC7 | TC-000834 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC8 | TC-000835 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC9 | TC-000836 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS171 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS171_TC1 | TC-000837 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC2 | TC-000838 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC3 | TC-000839 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC4 | TC-000840 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS171_TC5 | TC-000841 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS171_TC6 | TC-000842 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS171_TC7 | TC-000843 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC8 | TC-000844 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC9 | TC-000845 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS172 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS172_TC1 | TC-000846 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC2 | TC-000847 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC3 | TC-000848 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC4 | TC-000849 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS172_TC5 | TC-000850 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS172_TC6 | TC-000851 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS172_TC7 | TC-000852 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Card data in authorization or clearing transaction'] | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC8 | TC-000853 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC9 | TC-000854 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS173 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS173_TC1 | TC-000955 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only Encrypted PIN Block Data one byte over its documented maximum length (36) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS173_TC2 | TC-000855 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC3 | TC-000856 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS173_TC4 | TC-000857 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS173_TC5 | TC-000858 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) #1] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS173_TC6 | TC-000859 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) #1] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS173_TC7 | TC-000860 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 5 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS173_TC8 | TC-000861 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 5 -> 5 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC9 | TC-000862 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) #2] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS173_TC10 | TC-000863 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) #2] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS173_TC11 | TC-000864 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS173_TC12 | TC-000865 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS174 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS174_TC1 | TC-000953 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only Required field Prompt Code sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS174_TC2 | TC-000866 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC3 | TC-000867 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS174_TC4 | TC-000868 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS174_TC5 | TC-000869 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) #1] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS174_TC6 | TC-000870 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) #1] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS174_TC7 | TC-000871 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 5 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS174_TC8 | TC-000872 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 5 -> 5 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC9 | TC-000873 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved [Authorization Only - EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) #2] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS174_TC10 | TC-000874 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved [Authorization Only - EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) #2] | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS174_TC11 | TC-000875 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS174_TC12 | TC-000876 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS175 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS175_TC1 | TC-000952 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only Required field Prompt Code omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS175_TC2 | TC-000877 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation (Cancellation with Transaction Link Identifier) → approved | happy_path | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS175_TC3 | TC-000878 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Purchase/Capture (Purchase/Capture with Transaction Link Identifier) → approved | happy_path | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS175_TC4 | TC-000879 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation (Cancellation with Universal Unique Identifier) → approved | happy_path | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS175_TC5 | TC-000880 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Purchase/Capture (Purchase/Capture with Universal Unique Identifier) → approved | happy_path | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS175_TC6 | TC-000881 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC7 | TC-000882 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS175_TC8 | TC-000883 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS175_TC9 | TC-000884 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS175_TC10 | TC-000885 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS175_TC11 | TC-000886 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 5 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS175_TC12 | TC-000887 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 5 -> 5 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC13 | TC-000888 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS175_TC14 | TC-000889 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS176 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS176_TC1 | TC-000890 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC2 | TC-000891 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS176_TC3 | TC-000892 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS176_TC4 | TC-000893 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS176_TC5 | TC-000894 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS176_TC6 | TC-000895 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 5 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS176_TC7 | TC-000896 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 5 -> 5 | 1: F (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC8 | TC-000897 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS176_TC9 | TC-000898 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS177 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS177_TC1 | TC-000899 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC2 | TC-000900 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS177_TC3 | TC-000901 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS177_TC4 | TC-000902 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (completed for a higher final amount) → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS177_TC5 | TC-000903 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (completed for a lower final amount) → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS177_TC6 | TC-000904 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Cancellation (partially approved, then Cancellation for the approved amount) → approved | valid_flow | 5 -> S | 1: F (FLAG) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS177_TC7 | TC-000905 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved | valid_flow | 5 -> 5 | 1: F (FLAG) ; 2: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC8 | TC-000906 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS177_TC9 | TC-000907 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS178 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS178_TC1 | TC-000908 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS178_TC2 | TC-000909 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS178_TC3 | TC-000910 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS178_TC4 | TC-000911 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS178_TC5 | TC-000912 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS178_TC6 | TC-000913 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture on a CAT terminal with offline pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS179 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS179_TC1 | TC-000914 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS179_TC2 | TC-000915 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS179_TC3 | TC-000916 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS179_TC4 | TC-000917 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS179_TC5 | TC-000918 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS179_TC6 | TC-000919 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture on a CAT terminal with offline pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS180 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS180_TC1 | TC-000920 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS180_TC2 | TC-000921 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS180_TC3 | TC-000922 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS180_TC4 | TC-000923 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |

### BR1_TS181 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS181_TC1 | TC-000924 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS181_TC2 | TC-000925 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS181_TC3 | TC-000926 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS181_TC4 | TC-000927 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | needs_client_input |

### BR1_TS182 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS182_TC1 | TC-000928 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS182_TC2 | TC-000929 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS182_TC3 | TC-000930 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS182_TC4 | TC-000931 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture (lost_before_host), then Time-out Reversal (the original never reached the host) → declined (to confirm) | invalid_transition | 4 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |

### BR1_TS183 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS183_TC1 | TC-000932 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS184 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS184_TC1 | TC-000933 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS185 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS185_TC1 | TC-000934 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS186 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS186_TC1 | TC-000935 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS187 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS187_TC1 | TC-000936 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS188 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS188_TC1 | TC-000937 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS189 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS189_TC1 | TC-000938 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS190 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS190_TC1 | TC-000939 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS191 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS191_TC1 | TC-000940 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS192 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS192_TC1 | TC-000941 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS193 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS193_TC1 | TC-000942 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS194 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS194_TC1 | TC-000943 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS195 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS195_TC1 | TC-000944 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS196 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS196_TC1 | TC-000945 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

## Scenarios without test cases

- BR1_TS37 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip
- BR1_TS38 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip
- BR1_TS39 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe
- BR1_TS40 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe
- BR1_TS41 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device
- BR1_TS42 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip
- BR1_TS43 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip
- BR1_TS44 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe
- BR1_TS45 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe
- BR1_TS46 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device
- BR1_TS61 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip
- BR1_TS62 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip
- BR1_TS63 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe
- BR1_TS64 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe
- BR1_TS65 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device
- BR1_TS66 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip
- BR1_TS67 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip
- BR1_TS68 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe
- BR1_TS69 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe
- BR1_TS70 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device
- BR1_TS71 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip
- BR1_TS72 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip
- BR1_TS73 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe
- BR1_TS74 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe
- BR1_TS75 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed
- BR1_TS76 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback
- BR1_TS77 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device
- BR1_TS78 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip
- BR1_TS79 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip
- BR1_TS80 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe
- BR1_TS81 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe
- BR1_TS82 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed
- BR1_TS83 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback
- BR1_TS84 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device
- BR1_TS85 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip
- BR1_TS86 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip
- BR1_TS87 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe
- BR1_TS88 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe
- BR1_TS89 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed
- BR1_TS90 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback
- BR1_TS91 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device
- BR1_TS92 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip
- BR1_TS93 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip
- BR1_TS94 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe
- BR1_TS95 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe
- BR1_TS96 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed
- BR1_TS97 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback
- BR1_TS98 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device
- BR1_TS99 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip
- BR1_TS100 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip
- BR1_TS101 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe
- BR1_TS102 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe
- BR1_TS103 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device
- BR1_TS104 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip
- BR1_TS105 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip
- BR1_TS106 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe
- BR1_TS107 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe
- BR1_TS108 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device
- BR1_TS109 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip
- BR1_TS110 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip
- BR1_TS111 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe
- BR1_TS112 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe
- BR1_TS113 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed
- BR1_TS114 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback
- BR1_TS115 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device
- BR1_TS116 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip
- BR1_TS117 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip
- BR1_TS118 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe
- BR1_TS119 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe
- BR1_TS120 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed
- BR1_TS121 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback
- BR1_TS122 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device
- BR1_TS123 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip
- BR1_TS124 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip
- BR1_TS125 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe
- BR1_TS126 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe
- BR1_TS127 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed
- BR1_TS128 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback
- BR1_TS129 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device
- BR1_TS130 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip
- BR1_TS131 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip
- BR1_TS132 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe
- BR1_TS133 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe
- BR1_TS134 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed
- BR1_TS135 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback
- BR1_TS136 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device
- BR1_TS137 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip
- BR1_TS138 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip
- BR1_TS139 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe
- BR1_TS140 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe
- BR1_TS141 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device
- BR1_TS142 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip
- BR1_TS143 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip
- BR1_TS144 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe
- BR1_TS145 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe
- BR1_TS146 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device

## Planned but not generated

- BR1_TS168 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS168 G-IT-90581148ed: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-IT-90581148ed: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-HP-aa31ed0fb6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-HP-22d1e41da1: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-IT-90581148ed: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-IT-90581148ed: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-IT-90581148ed: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS178 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS179 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS180 G-HP-aa31ed0fb6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS180 G-HP-22d1e41da1: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS180 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS181 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS182 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS37 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS38 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS39 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS40 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS41 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS42 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS43 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS44 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS45 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS46 : deferred_pending_sme: context GENERAL_RETAIL-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS61 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS62 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS63 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS64 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS65 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS66 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS67 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS68 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS69 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS70 : deferred_pending_sme: context HOSPITALITY-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS71 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS72 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS73 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS74 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS75 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS76 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS77 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS78 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS79 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS80 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS81 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS82 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS83 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS84 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS85 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS86 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS87 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS88 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS89 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS90 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS91 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS92 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS93 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS94 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS95 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS96 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS97 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS98 : deferred_pending_sme: context SUPERMARKET-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS99 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS100 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS101 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS102 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS103 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS104 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS105 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS106 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS107 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS108 : deferred_pending_sme: context SUPERMARKET-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS109 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS110 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS111 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS112 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS113 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS114 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS115 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS116 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS117 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS118 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS119 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS120 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS121 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS122 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS123 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS124 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS125 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS126 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS127 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS128 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS129 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS130 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS131 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS132 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS133 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS134 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS135 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS136 : deferred_pending_sme: context UTILITIES-ATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS137 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS138 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS139 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS140 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS141 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS142 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS143 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS144 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS145 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)
- BR1_TS146 : deferred_pending_sme: context UTILITIES-UNATTENDED waits for an SME decision (scenario listed, test cases deferred)

## Follow-on legs (reversal / void / cancellation ...)

34 of 34 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
