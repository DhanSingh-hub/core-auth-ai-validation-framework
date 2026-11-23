# Test cases - BR1 BUYPASS ATL105 MasterCard Credit

Build `buypass-atl105-mastercard-credit-generated-42c2d5f6ba`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` generated-2026-11-13.2.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 196 |
| Test cases | 1623 |
| By status | ready: 289, needs_client_input: 597, requires_review: 487, blocked_verification: 31, pending_sme: 217, blocked_no_test_data: 2 |
| By polarity | Positive: 1197, Negative: 419, Boundary: 7 |
| Verification | PASS: 1449, REJECTED_FINAL: 31, NOT_APPLICABLE: 143 |
| Verification level | PASS: 1449, REJECTED_FINAL: 31, NOT_APPLICABLE: 143 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 985, FLAG: 287, INFER: 208, N/A: 143 |
| Scenarios with test cases | 100 / 196 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| happy_path Happy path | 407 |
| alternate_path Alternate path | 89 |
| single_rule_broken Single rule broken | 162 |
| decision_table Decision table | 123 |
| valid_flow Valid flow | 86 |
| invalid_transition Invalid state transition | 85 |
| fault_recovery Fault / recovery | 20 |
| idempotency_retry Idempotency / duplicate / retry | 32 |
| time_based Time-based | 56 |
| running_total Running total / state | 99 |
| response_check Response / oracle check | 297 |
| soft_constraint Soft constraint | 15 |
| ordering Ordering | 6 |
| co_requirement Co-requirement / mutual exclusion | 2 |
| routing Routing | 12 |
| issuer_outcome Issuer outcome | 30 |
| device_checkpoint Device checkpoint | 93 |
| pairwise Interaction (pairwise) | 8 |
| boundary Boundary | 1 |

## BR1 BUYPASS ATL105 MasterCard Credit


### BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-077206 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Encrypted PIN Block Data exactly at its documented maximum length (36 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-077208 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Required field EMV Chip Data omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC3 | TC-077207 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Chip Data one byte over its documented maximum length → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC4 | TC-077219 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Variable Information one byte over its documented maximum length → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC5 | TC-077211 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Card Sequence Number value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC6 | TC-077180 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | blocked_no_test_data |
| BR1_TS1_TC7 | TC-077182 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Network Token Data Request omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS1_TC8 | TC-077187 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS1_TC9 | TC-077190 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Purchase Card Data Segment omitted where requires it → declined | boundary | EMV Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS1_TC10 | TC-075600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC11 | TC-075601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC12 | TC-075602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-075603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC14 | TC-075604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC15 | TC-075605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC16 | TC-075606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-075607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC18 | TC-075608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC19 | TC-075609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC20 | TC-075610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC21 | TC-075611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC22 | TC-075612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC23 | TC-075613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC24 | TC-075614 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC25 | TC-075615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC26 | TC-075616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC27 | TC-075617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC28 | TC-075618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC29 | TC-075619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC30 | TC-075620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC31 | TC-075621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC32 | TC-075622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC33 | TC-075623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC34 | TC-075624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC35 | TC-075625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC36 | TC-075626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC37 | TC-075627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC38 | TC-075628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC39 | TC-075629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC40 | TC-075630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC41 | TC-075631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC42 | TC-075632 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC43 | TC-075633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC44 | TC-075634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC45 | TC-075635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC46 | TC-075636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC47 | TC-075637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC48 | TC-075638 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC49 | TC-075639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC50 | TC-075640 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC51 | TC-075641 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC52 | TC-075642 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC53 | TC-075643 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC54 | TC-075644 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC55 | TC-075645 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC56 | TC-075646 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS1_TC57 | TC-075647 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC58 | TC-075648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC59 | TC-075649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC60 | TC-075650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS1_TC61 | TC-075651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS1_TC62 | TC-075652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC63 | TC-075653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC64 | TC-075654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC65 | TC-075655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC66 | TC-075656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC67 | TC-075657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS1_TC68 | TC-075658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC69 | TC-075659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC70 | TC-075660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC71 | TC-075661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC72 | TC-075662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC73 | TC-075663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC74 | TC-075664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC75 | TC-075665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC76 | TC-075666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS1_TC77 | TC-075667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS1_TC78 | TC-075668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC79 | TC-075669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS1_TC80 | TC-075670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS1_TC81 | TC-075671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS1_TC82 | TC-075672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS1_TC83 | TC-075673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS1_TC84 | TC-075674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS1_TC85 | TC-075675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC86 | TC-075676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS1_TC87 | TC-075677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC88 | TC-075678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS1_TC89 | TC-075679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS1_TC90 | TC-075680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS1_TC91 | TC-075681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC92 | TC-075682 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC93 | TC-075683 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC94 | TC-075684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC95 | TC-075685 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC96 | TC-075686 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC97 | TC-075687 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC98 | TC-075688 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC99 | TC-075689 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC100 | TC-075690 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC101 | TC-075691 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC102 | TC-075692 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC103 | TC-075693 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC104 | TC-075694 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC105 | TC-075695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC106 | TC-075696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC107 | TC-075697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC108 | TC-075698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC109 | TC-075699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS1_TC110 | TC-075700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-077213 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture CA Public Key File Checksum exactly at its documented maximum length (25 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | ready |
| BR1_TS2_TC2 | TC-077212 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture Required field Prompt Code omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC3 | TC-075701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC4 | TC-075702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC5 | TC-075703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-075704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC7 | TC-075705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC8 | TC-075706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC9 | TC-075707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC10 | TC-075708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC11 | TC-075709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC12 | TC-075710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC13 | TC-075711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC14 | TC-075712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC15 | TC-075713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC16 | TC-075714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC17 | TC-075715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC18 | TC-075716 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC19 | TC-075717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC20 | TC-075718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC21 | TC-075719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC22 | TC-075720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC23 | TC-075721 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC24 | TC-075722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC25 | TC-075723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC26 | TC-075724 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC27 | TC-075725 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-075726 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-075727 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC30 | TC-075728 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC31 | TC-075729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC32 | TC-075730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC33 | TC-075731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC34 | TC-075732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC35 | TC-075733 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC36 | TC-075734 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC37 | TC-075735 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC38 | TC-075736 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC39 | TC-075737 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC40 | TC-075738 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC41 | TC-075739 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC42 | TC-075740 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC43 | TC-075741 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC44 | TC-075742 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC45 | TC-075743 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC46 | TC-075744 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC47 | TC-075745 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC48 | TC-075746 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC49 | TC-075747 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS2_TC50 | TC-075748 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC51 | TC-075749 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC52 | TC-075750 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC53 | TC-075751 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS2_TC54 | TC-075752 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS2_TC55 | TC-075753 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC56 | TC-075754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC57 | TC-075755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC58 | TC-075756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC59 | TC-075757 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC60 | TC-075758 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS2_TC61 | TC-075759 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC62 | TC-075760 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with cvm = offline_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC63 | TC-075761 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC64 | TC-075762 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC65 | TC-075763 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC66 | TC-075764 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC67 | TC-075765 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC68 | TC-075766 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS2_TC69 | TC-075767 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS2_TC70 | TC-075768 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC71 | TC-075769 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS2_TC72 | TC-075770 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS2_TC73 | TC-075771 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS2_TC74 | TC-075772 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS2_TC75 | TC-075773 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS2_TC76 | TC-075774 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS2_TC77 | TC-075775 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC78 | TC-075776 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS2_TC79 | TC-075777 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC80 | TC-075778 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS2_TC81 | TC-075779 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS2_TC82 | TC-075780 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS2_TC83 | TC-075781 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC84 | TC-075782 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC85 | TC-075783 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC86 | TC-075784 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC87 | TC-075785 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC88 | TC-075786 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC89 | TC-075787 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC90 | TC-075788 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC91 | TC-075789 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC92 | TC-075790 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC93 | TC-075791 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC94 | TC-075792 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC95 | TC-075793 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC96 | TC-075794 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC97 | TC-075795 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC98 | TC-075796 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC99 | TC-075797 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC100 | TC-075798 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC101 | TC-075799 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS2_TC102 | TC-075800 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-077209 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture Cryptogram Token Data one byte over its documented maximum length (56) → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-077222 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS3_TC3 | TC-075801 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC4 | TC-075802 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC5 | TC-075803 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-075804 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC7 | TC-075805 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC8 | TC-075806 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC9 | TC-075807 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC10 | TC-075808 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC11 | TC-075809 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC12 | TC-075810 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC13 | TC-075811 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC14 | TC-075812 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC15 | TC-075813 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC16 | TC-075814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC17 | TC-075815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC18 | TC-075816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC19 | TC-075817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC20 | TC-075818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC21 | TC-075819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC22 | TC-075820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC23 | TC-075821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC24 | TC-075822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC25 | TC-075823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC26 | TC-075824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC27 | TC-075825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC28 | TC-075826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC29 | TC-075827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC30 | TC-075828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC31 | TC-075829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC32 | TC-075830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC33 | TC-075831 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC34 | TC-075832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC35 | TC-075833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC36 | TC-075834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC37 | TC-075835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC38 | TC-075836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC39 | TC-075837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Access Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC40 | TC-075838 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC41 | TC-075839 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC42 | TC-075840 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC43 | TC-075841 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC44 | TC-075842 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC45 | TC-075843 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Chip Data Length → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC46 | TC-075844 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for EMV Request Data Segment → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC47 | TC-075845 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC48 | TC-075846 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC49 | TC-075847 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC50 | TC-075848 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC51 | TC-075849 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS3_TC52 | TC-075850 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC53 | TC-075851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC54 | TC-075852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC55 | TC-075853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS3_TC56 | TC-075854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS3_TC57 | TC-075855 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC58 | TC-075856 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC59 | TC-075857 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC60 | TC-075858 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC61 | TC-075859 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC62 | TC-075860 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS3_TC63 | TC-075861 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC64 | TC-075862 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC65 | TC-075863 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC66 | TC-075864 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with optional NFC Payment Tokenization Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC67 | TC-075865 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC68 | TC-075866 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC69 | TC-075867 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC70 | TC-075868 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Chip Transaction Report should show both data element'] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC71 | TC-075869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'Contactless CVM Limit varies by Brand/AID'] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC72 | TC-075870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'This field should only be sent for First'] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC73 | TC-075871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'Recommended'] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC74 | TC-075872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'mPOS Devices Phone/Tablet + Dongle with PIN Pad'] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC75 | TC-075873 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: EMV Chip Data Length | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC76 | TC-075874 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: EMV Request Data Segment | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC77 | TC-075875 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Load Type | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC78 | TC-075876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Pause Indicator | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC79 | TC-075877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS3_TC80 | TC-075878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS3_TC81 | TC-075879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC82 | TC-075880 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS3_TC83 | TC-075881 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS3_TC84 | TC-075882 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS3_TC85 | TC-075883 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS3_TC86 | TC-075884 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS3_TC87 | TC-075885 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS3_TC88 | TC-075886 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC89 | TC-075887 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS3_TC90 | TC-075888 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC91 | TC-075889 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS3_TC92 | TC-075890 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS3_TC93 | TC-075891 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS3_TC94 | TC-075892 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for Proprietary Data Load Segment | running_total | - | n/a (no host message) | requires_review |
| BR1_TS3_TC95 | TC-075893 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'ICC Offline Accumulator Balance 2 is retrieved' | running_total | - | n/a (no host message) | requires_review |
| BR1_TS3_TC96 | TC-075894 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Offline Accumulator Balance 1 retrieved by GET DATA' | running_total | - | n/a (no host message) | requires_review |
| BR1_TS3_TC97 | TC-075895 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Expiration Date | ordering | - | n/a (no host message) | requires_review |
| BR1_TS3_TC98 | TC-075896 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Number of Segments | ordering | - | n/a (no host message) | requires_review |
| BR1_TS3_TC99 | TC-075897 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Summary of TOR Processing Rules | ordering | - | n/a (no host message) | requires_review |
| BR1_TS3_TC100 | TC-075898 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for 'For TA-VE, EDATA is processor code, colon, Encrypted' | ordering | - | n/a (no host message) | requires_review |
| BR1_TS3_TC101 | TC-075899 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: co-requirement / mutual exclusion for Sub Table ID "01" — Purpose: Identifies a deferred transaction. | co_requirement | - | n/a (no host message) | requires_review |
| BR1_TS3_TC102 | TC-075900 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: co-requirement / mutual exclusion for POS Additional Information Indicator | co_requirement | - | n/a (no host message) | requires_review |
| BR1_TS3_TC103 | TC-075901 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Card Label | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC104 | TC-075902 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Dial Backup and Diverse Facilities | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC105 | TC-075903 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Dual Credit and Single Debit Messages | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC106 | TC-075904 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Sub Table ID "13" | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC107 | TC-075905 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for PINless Debit Information | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC108 | TC-075906 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Encrypted PIN Block Data | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC109 | TC-075907 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Network Management Message | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC110 | TC-075908 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Response Code | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC111 | TC-075909 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Standard Message Data Segment | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC112 | TC-075910 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for BUYPASS Card Type Code | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC113 | TC-075911 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for 'TransArmor VeriFone Edition (FPE) leaves first 6' | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC114 | TC-075912 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for 'For routing purposes, only the RID portion' | routing | - | n/a (no host message) | requires_review |
| BR1_TS3_TC115 | TC-075913 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC116 | TC-075914 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC117 | TC-075915 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC118 | TC-075916 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC119 | TC-075917 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC120 | TC-075918 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC121 | TC-075919 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC122 | TC-075920 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC123 | TC-075921 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC124 | TC-075922 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC125 | TC-075923 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC126 | TC-075924 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC127 | TC-075925 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC128 | TC-075926 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC129 | TC-075927 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC130 | TC-075928 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC131 | TC-075929 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC132 | TC-075930 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS3_TC133 | TC-075931 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC134 | TC-075932 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture without Sub Table 07 Merchant Supplementary Data (Merchant Advice Code not returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC135 | TC-075933 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture without Sub Table 24 Merchant Supplementary Data (Token Update First Use Indicator not returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC136 | TC-075934 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 01 (New account information available) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC137 | TC-075935 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 02 (Cannot approve at this time, try again later) → declined (to confirm) | issuer_outcome | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC138 | TC-075936 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 03 (Do not try again) → declined (to confirm) | issuer_outcome | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC139 | TC-075937 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 04 (Token requirements not fulfilled for this token type) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC140 | TC-075938 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 21 (Payment Cancellation) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC141 | TC-075939 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 22 (Merchant does not qualify for product code) → declined (to confirm) | issuer_outcome | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC142 | TC-075940 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 24 (Retry after 1 hour) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC143 | TC-075941 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 25 (Retry after 24 hours) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC144 | TC-075942 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 26 (Retry after 2 days) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC145 | TC-075943 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 27 (Retry after 4 days) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC146 | TC-075944 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 28 (Retry after 6 days) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC147 | TC-075945 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 29 (Retry after 8 days) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC148 | TC-075946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 30 (Retry after 10 days) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC149 | TC-075947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 40 (Consumer non-reloadable prepaid card) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC150 | TC-075948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 41 (Consumer single-use virtual card number) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC151 | TC-075949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns 43 (Consumer multi-use virtual card number) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC152 | TC-075950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data, issuer returns Y (First use since update) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC153 | TC-075951 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: CAPK lifecycle within 6 months (terminal certification) [about 'Do not disable or delete CAPK until new'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC154 | TC-075952 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: CAPK lifecycle within 6 months (terminal certification) [about 'New CAPK updates must be implemented within six'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC155 | TC-075953 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Payment Network used for routing must be displayed'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC156 | TC-075954 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Offline declined receipts must contain AID, TVR, IAD'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC157 | TC-075955 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Application PAN must be truncated and printed'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC158 | TC-075956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Payment Network identifies network transaction was routed'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC159 | TC-075957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Debit Prompt value 0 means do not prompt'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC160 | TC-075958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'BUYPASS Card Type appears when Prompt Code 904'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC161 | TC-075959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'For non-TransArmor and TransArmor Token Only, the POS'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC162 | TC-075960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'In Proprietary Load Response, Product'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC163 | TC-075961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Terminal Display/Printer Message can repeat up to three'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC164 | TC-075962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'If Service Code indicates chip, swipe read data'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC165 | TC-075963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Product Discount Amount appears in Proprietary Data Load'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC166 | TC-075964 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'It is recommended that EMV POS support same'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC167 | TC-075965 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Device should prompt user to swipe card'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC168 | TC-075966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Debit Card Type ID prints as DB'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC169 | TC-075967 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour checks [about 'Merchant ID (MID) must be truncated if present'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC170 | TC-075968 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'A bit is set on the merchant profile'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC171 | TC-075969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'Target Percentage and Maximum Target Percentage for Random'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC172 | TC-075970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'If manual entry is used, this fact'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC173 | TC-075971 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'Element No'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC174 | TC-075972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'Downloads must be capable of being requested'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC175 | TC-075973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC176 | TC-075974 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'For some Decline Code values, a device'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC177 | TC-075975 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks [about 'Display the Final Amount to Cardholder, especially important'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC178 | TC-075976 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks [about 'Card Type codes are also used to construct'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC179 | TC-075977 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks [about 'Customer's host/device should have a maximum number'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC180 | TC-075978 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks [about 'An electronic journal is not provided by BUYPASS'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC181 | TC-075979 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks [about 'A Master ID is comprised of specific versions'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC182 | TC-075980 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks [about 'PAN Sequence Number (Tag 5F34) is left blank'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC183 | TC-075981 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks [about 'Print Data 2 Segment is only sent'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC184 | TC-075982 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks [about 'Implementation Phase complete only when card logs, trace'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC185 | TC-075983 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks [about 'Discount Quantity Limit appears in Proprietary Load Response'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC186 | TC-075984 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 5 terminal behaviour checks [about 'Print Data Segment is only sent on Financial'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC187 | TC-075985 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 5 terminal behaviour checks [about 'Local totals are calculated from electronic journal entries'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC188 | TC-075986 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 5 terminal behaviour checks [about 'Decline message must be on receipt'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC189 | TC-075987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 6 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC190 | TC-075988 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 7 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC191 | TC-075989 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 7 terminal behaviour checks-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC192 | TC-075990 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 9 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC193 | TC-075991 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: CA Public Key File Block | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC194 | TC-075992 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Card Table Master | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC195 | TC-075993 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Card Type | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC196 | TC-075994 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Host Prompts Information (cont.) | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC197 | TC-075995 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Encrypted PIN Block Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC198 | TC-075996 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Enhanced Fleet Request | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC199 | TC-075997 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Key Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC200 | TC-075998 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Key ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC201 | TC-075999 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Receipt Text Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC202 | TC-076000 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal Display /Printer Message [about 'Terminal Display/Printer Message data element can be repeated'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC203 | TC-076001 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal Display /Printer Message [about 'TransArmor Financial Transaction Response requires Download Indicator, Account'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC204 | TC-076002 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal Display Communications Message [about 'COMM_TEST_PASSED is the Terminal Display Communications Message'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC205 | TC-076003 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal Display Communications Message [about 'Terminal Display Communications Message is conditional, displaying COMM_TEST_PASSED'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC206 | TC-076004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Token PAN Suffix [about 'Token PAN Suffix identifies the 4 digits'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC207 | TC-076005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Token PAN Suffix [about 'POS device must send PAN in clear'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC208 | TC-076006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: 'Device must support manual and automatic download requests' | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC209 | TC-076007 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device checkpoint for Sub Table ID "01" | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC210 | TC-076008 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device checkpoint for Token PAN Suffix | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC211 | TC-076009 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: terminal / device certification: 12 checks [about 'US Territories or other USD processing locations'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC212 | TC-076010 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: terminal / device certification: 12 checks [about 'Depending on severity/type of failure, another Card Brand'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC213 | TC-076011 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: terminal / device certification: 'Regression testing for MSR functionality can begin' | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC214 | TC-076012 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: CA Public Key File Data Segment | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC215 | TC-076013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Card Discretionary Block Data [about 'When data entry is manually keyed, Expiration Date'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC216 | TC-076014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Card Discretionary Block Data [about 'For an initial manually keyed transaction'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC217 | TC-076015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Card Type | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC218 | TC-076016 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Key ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC219 | TC-076017 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Merchant Trace ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC220 | TC-076018 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Real Time Account Updater Request Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC221 | TC-076019 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Token Requestor ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS3_TC222 | TC-076020 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Mail/Phone Reversal (not supported for this card) → declined (to confirm) | single_rule_broken | C | 1: 1 (FLAG) | requires_review |
| BR1_TS3_TC223 | TC-076021 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Account Verification (not supported for this card) → declined (to confirm) | single_rule_broken | A | 1: 1 (FLAG) | requires_review |

### BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-076022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC2 | TC-076023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC3 | TC-076024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC4 | TC-076025 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC5 | TC-076026 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC6 | TC-076027 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC7 | TC-076028 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC8 | TC-076029 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC9 | TC-076030 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC10 | TC-076031 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC11 | TC-076032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC12 | TC-076033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC13 | TC-076034 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC14 | TC-076035 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC15 | TC-076036 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC16 | TC-076037 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC17 | TC-076038 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC18 | TC-076039 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC19 | TC-076040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC20 | TC-076041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC21 | TC-076042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC22 | TC-076043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC23 | TC-076044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC24 | TC-076045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC25 | TC-076046 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC26 | TC-076047 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC27 | TC-076048 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC28 | TC-076049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC29 | TC-076050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC30 | TC-076051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC31 | TC-076052 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC32 | TC-076053 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC33 | TC-076054 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC34 | TC-076055 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC35 | TC-076056 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC36 | TC-076057 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC37 | TC-076058 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC38 | TC-076059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC39 | TC-076060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC40 | TC-076061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC41 | TC-076062 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC42 | TC-076063 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC43 | TC-076064 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC44 | TC-076065 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC45 | TC-076066 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC46 | TC-076067 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS4_TC47 | TC-076068 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC48 | TC-076069 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC49 | TC-076070 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC50 | TC-076071 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS4_TC51 | TC-076072 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS4_TC52 | TC-076073 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC53 | TC-076074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC54 | TC-076075 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC55 | TC-076076 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC56 | TC-076077 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC57 | TC-076078 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS4_TC58 | TC-076079 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC59 | TC-076080 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC60 | TC-076081 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC61 | TC-076082 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with optional NFC Payment Tokenization Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC62 | TC-076083 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC63 | TC-076084 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC64 | TC-076085 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC65 | TC-076086 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS4_TC66 | TC-076087 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS4_TC67 | TC-076088 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC68 | TC-076089 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS4_TC69 | TC-076090 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS4_TC70 | TC-076091 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS4_TC71 | TC-076092 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS4_TC72 | TC-076093 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS4_TC73 | TC-076094 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS4_TC74 | TC-076095 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC75 | TC-076096 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS4_TC76 | TC-076097 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC77 | TC-076098 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS4_TC78 | TC-076099 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS4_TC79 | TC-076100 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS4_TC80 | TC-076101 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC81 | TC-076102 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC82 | TC-076103 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC83 | TC-076104 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC84 | TC-076105 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC85 | TC-076106 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC86 | TC-076107 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC87 | TC-076108 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC88 | TC-076109 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC89 | TC-076110 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC90 | TC-076111 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC91 | TC-076112 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC92 | TC-076113 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC93 | TC-076114 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC94 | TC-076115 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC95 | TC-076116 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC96 | TC-076117 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC97 | TC-076118 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS4_TC98 | TC-076119 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-076120 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-076121 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC3 | TC-076122 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC4 | TC-076123 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC5 | TC-076124 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC6 | TC-076125 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC7 | TC-076126 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC8 | TC-076127 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC9 | TC-076128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC10 | TC-076129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC11 | TC-076130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC12 | TC-076131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC13 | TC-076132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC14 | TC-076133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC15 | TC-076134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC16 | TC-076135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC17 | TC-076136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC18 | TC-076137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC19 | TC-076138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC20 | TC-076139 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC21 | TC-076140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC22 | TC-076141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC23 | TC-076142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC24 | TC-076143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC25 | TC-076144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC26 | TC-076145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC27 | TC-076146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC28 | TC-076147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC29 | TC-076148 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC30 | TC-076149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC31 | TC-076150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC32 | TC-076151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC33 | TC-076152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC34 | TC-076153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC35 | TC-076154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC36 | TC-076155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC37 | TC-076156 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC38 | TC-076157 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC39 | TC-076158 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC40 | TC-076159 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC41 | TC-076160 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC42 | TC-076161 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC43 | TC-076162 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC44 | TC-076163 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC45 | TC-076164 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC46 | TC-076165 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS5_TC47 | TC-076166 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC48 | TC-076167 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC49 | TC-076168 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC50 | TC-076169 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS5_TC51 | TC-076170 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS5_TC52 | TC-076171 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC53 | TC-076172 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC54 | TC-076173 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC55 | TC-076174 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC56 | TC-076175 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC57 | TC-076176 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS5_TC58 | TC-076177 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC59 | TC-076178 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC60 | TC-076179 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incremental = incremental → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC61 | TC-076180 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC62 | TC-076181 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC63 | TC-076182 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with optional NFC Payment Tokenization Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC64 | TC-076183 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC65 | TC-076184 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC66 | TC-076185 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC67 | TC-076186 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with track = 1 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC68 | TC-076187 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS5_TC69 | TC-076188 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS5_TC70 | TC-076189 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC71 | TC-076190 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS5_TC72 | TC-076191 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS5_TC73 | TC-076192 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS5_TC74 | TC-076193 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS5_TC75 | TC-076194 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS5_TC76 | TC-076195 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC77 | TC-076196 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC78 | TC-076197 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS5_TC79 | TC-076198 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC80 | TC-076199 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS5_TC81 | TC-076200 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS5_TC82 | TC-076201 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS5_TC83 | TC-076202 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC84 | TC-076203 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC85 | TC-076204 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC86 | TC-076205 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC87 | TC-076206 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC88 | TC-076207 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC89 | TC-076208 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC90 | TC-076209 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC91 | TC-076210 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC92 | TC-076211 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC93 | TC-076212 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC94 | TC-076213 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC95 | TC-076214 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC96 | TC-076215 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC97 | TC-076216 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC98 | TC-076217 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC99 | TC-076218 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC100 | TC-076219 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS5_TC101 | TC-076220 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-076221 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC2 | TC-076222 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC3 | TC-076223 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC4 | TC-076224 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC5 | TC-076225 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC6 | TC-076226 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC7 | TC-076227 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC8 | TC-076228 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC9 | TC-076229 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC10 | TC-076230 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC11 | TC-076231 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC12 | TC-076232 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC13 | TC-076233 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC14 | TC-076234 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC15 | TC-076235 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC16 | TC-076236 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC17 | TC-076237 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC18 | TC-076238 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC19 | TC-076239 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC20 | TC-076240 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC21 | TC-076241 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC22 | TC-076242 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC23 | TC-076243 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC24 | TC-076244 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC25 | TC-076245 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC26 | TC-076246 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC27 | TC-076247 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC28 | TC-076248 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC29 | TC-076249 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC30 | TC-076250 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC31 | TC-076251 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC32 | TC-076252 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC33 | TC-076253 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC34 | TC-076254 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC35 | TC-076255 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC36 | TC-076256 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC37 | TC-076257 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC38 | TC-076258 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC39 | TC-076259 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC40 | TC-076260 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC41 | TC-076261 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC42 | TC-076262 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC43 | TC-076263 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Chip Data Length → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC44 | TC-076264 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for EMV Request Data Segment → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC45 | TC-076265 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC46 | TC-076266 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC47 | TC-076267 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC48 | TC-076268 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Network Management Message → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC49 | TC-076269 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC50 | TC-076270 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS6_TC51 | TC-076271 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC52 | TC-076272 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC53 | TC-076273 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC54 | TC-076274 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS6_TC55 | TC-076275 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS6_TC56 | TC-076276 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC57 | TC-076277 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC58 | TC-076278 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC59 | TC-076279 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC60 | TC-076280 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC61 | TC-076281 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS6_TC62 | TC-076282 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC63 | TC-076283 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC64 | TC-076284 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC65 | TC-076285 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with optional NFC Payment Tokenization Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC66 | TC-076286 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC67 | TC-076287 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC68 | TC-076288 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC69 | TC-076289 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS6_TC70 | TC-076290 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS6_TC71 | TC-076291 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC72 | TC-076292 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS6_TC73 | TC-076293 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS6_TC74 | TC-076294 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS6_TC75 | TC-076295 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS6_TC76 | TC-076296 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS6_TC77 | TC-076297 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC78 | TC-076298 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC79 | TC-076299 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS6_TC80 | TC-076300 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC81 | TC-076301 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS6_TC82 | TC-076302 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS6_TC83 | TC-076303 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS6_TC84 | TC-076304 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC85 | TC-076305 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC86 | TC-076306 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC87 | TC-076307 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC88 | TC-076308 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC89 | TC-076309 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC90 | TC-076310 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC91 | TC-076311 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC92 | TC-076312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC93 | TC-076313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC94 | TC-076314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC95 | TC-076315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC96 | TC-076316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC97 | TC-076317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC98 | TC-076318 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC99 | TC-076319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC100 | TC-076320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC101 | TC-076321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS6_TC102 | TC-076322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-077181 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS7_TC2 | TC-077188 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture Purchase Card Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | requires_review |
| BR1_TS7_TC3 | TC-076323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC4 | TC-076324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → approved [about 'Prompt Code example 020 identifies Transaction Type'] | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC5 | TC-076325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC6 | TC-076326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Point-of-Service Entry Mode Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC7 | TC-076327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC8 | TC-076328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-076329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Partial Approval Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC10 | TC-076330 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Market-Specific Data Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC11 | TC-076331 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with User Data Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC12 | TC-076332 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC13 | TC-076333 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC14 | TC-076334 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC15 | TC-076335 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC16 | TC-076336 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC17 | TC-076337 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC18 | TC-076338 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC19 | TC-076339 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC20 | TC-076340 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC21 | TC-076341 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC22 | TC-076342 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC23 | TC-076343 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC24 | TC-076344 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC25 | TC-076345 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC26 | TC-076346 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC27 | TC-076347 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC28 | TC-076348 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC29 | TC-076349 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC30 | TC-076350 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC31 | TC-076351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC32 | TC-076352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Soft Descriptor - Merchant City → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC33 | TC-076353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Soft Descriptor - Merchant State → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC34 | TC-076354 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC35 | TC-076355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC36 | TC-076356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC37 | TC-076357 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC38 | TC-076358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC39 | TC-076359 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC40 | TC-076360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC41 | TC-076361 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC42 | TC-076362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC43 | TC-076363 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC44 | TC-076364 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC45 | TC-076365 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC46 | TC-076366 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC47 | TC-076367 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC48 | TC-076368 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC49 | TC-076369 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC50 | TC-076370 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC51 | TC-076371 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC52 | TC-076372 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC53 | TC-076373 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC54 | TC-076374 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC55 | TC-076375 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | requires_review |
| BR1_TS7_TC56 | TC-076376 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC57 | TC-076377 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC58 | TC-076378 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC59 | TC-076379 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS7_TC60 | TC-076380 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS7_TC61 | TC-076381 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC62 | TC-076382 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC63 | TC-076383 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC64 | TC-076384 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC65 | TC-076385 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC66 | TC-076386 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS7_TC67 | TC-076387 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with cvv2 = mismatch → approved (to confirm) | decision_table | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC68 | TC-076388 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with cvv2 = true → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC69 | TC-076389 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with incremental = original → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC70 | TC-076390 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with initiation = M → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC71 | TC-076391 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC72 | TC-076392 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with optional NFC Payment Tokenization Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC73 | TC-076393 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC74 | TC-076394 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC75 | TC-076395 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC76 | TC-076396 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with special payment = I → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC77 | TC-076397 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with special payment = R → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC78 | TC-076398 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS7_TC79 | TC-076399 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS7_TC80 | TC-076400 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC81 | TC-076401 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS7_TC82 | TC-076402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS7_TC83 | TC-076403 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS7_TC84 | TC-076404 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS7_TC85 | TC-076405 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | ready |
| BR1_TS7_TC86 | TC-076406 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal after 72 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC87 | TC-076407 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal after 73 hours, beyond the 72-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC88 | TC-076408 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal after 72 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS7_TC89 | TC-076409 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal after 73 hours, beyond the 72-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC90 | TC-076410 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS7_TC91 | TC-076411 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS7_TC92 | TC-076412 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS7_TC93 | TC-076413 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC94 | TC-076414 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC95 | TC-076415 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC96 | TC-076416 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC97 | TC-076417 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC98 | TC-076418 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC99 | TC-076419 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC100 | TC-076420 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC101 | TC-076421 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'When initiating MIT recurring transaction, transaction'] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC102 | TC-076422 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC103 | TC-076423 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC104 | TC-076424 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC105 | TC-076425 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC106 | TC-076426 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending Program Identifier Information (PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC107 | TC-076427 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC108 | TC-076428 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC109 | TC-076429 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC110 | TC-076430 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC111 | TC-076431 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS7_TC112 | TC-076432 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending cvv2 = true (CVV Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC113 | TC-076433 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending initiation = M (Transaction Identifier returned) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC114 | TC-076434 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC115 | TC-076435 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending cvv2 = true, issuer returns M (Valid or matched CVC2) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC116 | TC-076436 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending cvv2 = true, issuer returns N (Invalid CVC2 value) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC117 | TC-076437 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending cvv2 = true, issuer returns P (CVC2 not processed) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC118 | TC-076438 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sending cvv2 = true, issuer returns U (Issuer unregistered for CVC2) → approved (to confirm) | issuer_outcome | 0 | 1: 0 (FLAG) | needs_client_input |

### BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-076439 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-076440 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with UPC Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC3 | TC-076441 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Currency Code Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC4 | TC-076442 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Balance Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC5 | TC-076443 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Suspected Fraud Reversal Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC6 | TC-076444 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Card Acceptor Terminal Identifier Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC7 | TC-076445 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Recharge Type Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC8 | TC-076446 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Originating Device Type Indicator Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC9 | TC-076447 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Customer Name → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC10 | TC-076448 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC11 | TC-076449 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with MasterCard UCAF Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC12 | TC-076450 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Paydiant Tender ID → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC13 | TC-076451 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Fraud Enhanced Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC14 | TC-076452 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Final Amount Indicator → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC15 | TC-076453 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Country Subdivision → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC16 | TC-076454 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Cardholder Language → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC17 | TC-076455 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Previous Transaction ID → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC18 | TC-076456 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Soft Descriptor - Merchant Name → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC19 | TC-076457 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with MasterCard Authentication Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC20 | TC-076458 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Additional Security Code → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC21 | TC-076459 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Additional Card ID → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC22 | TC-076460 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Additional TransArmor Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC23 | TC-076461 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Remote Commerce Acceptor Identifier → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC24 | TC-076462 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Experience Provider Value → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC25 | TC-076463 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Soft Descriptor - Merchant Country → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC26 | TC-076464 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Real Time Account Updater Request Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC27 | TC-076465 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC28 | TC-076466 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Service Location Information → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC29 | TC-076467 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Merchant Payment Gateway ID Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC30 | TC-076468 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Digital Commerce Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC31 | TC-076469 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Anticipated Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC32 | TC-076470 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Anticipated Completion Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC33 | TC-076471 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Future Transaction Amount → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC34 | TC-076472 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Discount Program Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC35 | TC-076473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Discount Line-Item Data → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC36 | TC-076474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Staged Wallet ID → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC37 | TC-076475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Approved Amount → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC38 | TC-076476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Card Type → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC39 | TC-076477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Download Indicator → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC40 | TC-076478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for EMV Card Sequence Number → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC41 | TC-076479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for EMV Chip Data → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC42 | TC-076480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'] | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC43 | TC-076481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only'] | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC44 | TC-076482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Sub Table ID "23" → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC45 | TC-076483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for Service Level → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC46 | TC-076484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture via the alternate path for POS Condition Code → approved | alternate_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC47 | TC-076485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC48 | TC-076486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC49 | TC-076487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC50 | TC-076488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC51 | TC-076489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC52 | TC-076490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Payment Tender Type = AX → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC53 | TC-076491 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Service Level = F → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC54 | TC-076492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Tax Type 1 = GST → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC55 | TC-076493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Update Code = A → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC56 | TC-076494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Variable Information Indicator = 001 → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC57 | TC-076495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with avs = true → approved | decision_table | 0 | 1: 4 (ASSERT) | pending_sme |
| BR1_TS8_TC58 | TC-076496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with cof = initial → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC59 | TC-076497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with cvm = online_pin → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC60 | TC-076498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with optional Purchase Card Data Segment → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC61 | TC-076499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with optional Network Token Data Request → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC62 | TC-076500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Product Code Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC63 | TC-076501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Variable Information Data Segment sent → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC64 | TC-076502 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC65 | TC-076503 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC66 | TC-076504 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 0 -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC67 | TC-076505 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS8_TC68 | TC-076506 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | blocked_verification |
| BR1_TS8_TC69 | TC-076507 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC70 | TC-076508 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC71 | TC-076509 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x3 resent → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC72 | TC-076510 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal after 24 hours, at the limit → approved | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC73 | TC-076511 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC74 | TC-076512 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal after 24 hours, at the limit → approved | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS8_TC75 | TC-076513 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal after 25 hours, beyond the 24-hour limit → declined (to confirm) | time_based | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC76 | TC-076514 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS8_TC77 | TC-076515 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS8_TC78 | TC-076516 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS8_TC79 | TC-076517 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Additional Information Data Layouts returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC80 | TC-076518 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Cardholder Additional Information Result Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC81 | TC-076519 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Host Prompts Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC82 | TC-076520 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Network Token Data Response returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC83 | TC-076521 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC84 | TC-076522 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Real Time Account Updater Response Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC85 | TC-076523 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC86 | TC-076524 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC87 | TC-076525 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC88 | TC-076526 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC89 | TC-076527 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC90 | TC-076528 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending Merchant Supplementary Data (Token Update First Use Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC91 | TC-076529 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending Program Identifier Information (BUYPASS Card Type Code and PIN on Receipt Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC92 | TC-076530 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Program Identifier Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC93 | TC-076531 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Re-Price Data Request Information, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC94 | TC-076532 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with MCX Checkout Token, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC95 | TC-076533 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Merchant Tran ID, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC96 | TC-076534 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture with Customer Additional Information Data, response table checked → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC97 | TC-076535 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 0 | 1: 4 (ASSERT) | pending_sme |
| BR1_TS8_TC98 | TC-076536 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purchase/Capture sending initiation = M and Transaction Link Identifier (Transaction Link Identifier and Transaction Link Action Indicator returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-077176 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase Required field Partial Approval Indicator omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS9_TC2 | TC-076537 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase → approved | happy_path | 6 | 1: 0 (ASSERT) | ready |
| BR1_TS9_TC3 | TC-076538 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC4 | TC-076539 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC5 | TC-076540 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-076541 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS9_TC7 | TC-076542 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Time-out Reversal → approved | valid_flow | 6 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS9_TC8 | TC-076543 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 6 -> C -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | ready |
| BR1_TS9_TC9 | TC-076544 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS9_TC10 | TC-076545 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Reversal with no original transaction → declined | invalid_transition | C | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS9_TC11 | TC-076546 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal → approved | fault_recovery | 6 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS9_TC12 | TC-076547 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals include it) → approved | running_total | 6 -> C -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS9_TC13 | TC-076548 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 6 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS9_TC14 | TC-076549 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS9_TC15 | TC-076550 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for Response Code [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC16 | TC-076551 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code B means Approved-Totals with electronic mail' [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC17 | TC-076552 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code C means Declined-Totals with electronic mail' [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC18 | TC-076553 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code 6 means Approved-Totals with electronic mail' [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC19 | TC-076554 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code 7 means Declined-Totals with electronic mail' [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC20 | TC-076555 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Electronic Mail Data Segment [on Mail/Phone Purchase - Manual / Keyed] | ordering | - | n/a (no host message) | requires_review |
| BR1_TS9_TC21 | TC-076556 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal (card issuer declines) → declined | response_check | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC22 | TC-076557 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then Time-out Reversal (card issuer declines) → declined | response_check | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC23 | TC-076558 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC24 | TC-076559 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 6 | 1: 4 (ASSERT) | needs_client_input |
| BR1_TS9_TC25 | TC-076560 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Message Format Version Identifier [on Mail/Phone Purchase - Manual / Keyed] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS9_TC26 | TC-076561 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent with entry mode 05 (EMV Contact Chip, not supported) → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC27 | TC-076562 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent with entry mode 07 (EMV Contactless Chip, not supported) → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC28 | TC-076563 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent with entry mode 02 (Magnetic Stripe, not supported) → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC29 | TC-076564 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent with entry mode 91 (Contactless Magnetic Stripe, not supported) → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC30 | TC-076565 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent with entry mode 79 (Chip Fallback, not supported) → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC31 | TC-076566 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent with entry mode 82 (Contactless Mobile Commerce Device, not supported) → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | requires_review |

### BR1_TS10 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-076567 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC2 | TC-076568 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase → approved [about 'Electronic Mail Request for retrieval can contain'] | happy_path | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC3 | TC-076569 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-076570 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-076571 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-076572 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC7 | TC-076573 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Time-out Reversal → approved | valid_flow | 6 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC8 | TC-076574 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 6 -> C -> C | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC9 | TC-076575 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC10 | TC-076576 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Reversal with no original transaction → declined | invalid_transition | C | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS10_TC11 | TC-076577 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (no response), then Mail/Phone Reversal → approved | fault_recovery | 6 -> C | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC12 | TC-076578 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (no response), then Time-out Reversal → approved | fault_recovery | 6 -> Z | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS10_TC13 | TC-076579 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals include it) → approved | running_total | 6 -> C -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS10_TC14 | TC-076580 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 6 -> Z -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS10_TC15 | TC-076581 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: 0 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS10_TC16 | TC-076582 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Mail/Phone Reversal (card issuer declines) → declined | response_check | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC17 | TC-076583 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, then Time-out Reversal (card issuer declines) → declined | response_check | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC18 | TC-076584 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC19 | TC-076585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | 6 | 1: 4 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-077216 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Ship-from Postal Code one byte over its documented maximum length (10) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC2 | TC-077186 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Required field EMV Chip Data Length sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC3 | TC-077177 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Partial Approval Indicator value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC4 | TC-077220 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Variable Information Indicator value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC5 | TC-076586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC6 | TC-076587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC7 | TC-076588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC8 | TC-076589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC9 | TC-076590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC10 | TC-076591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC11 | TC-076592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS11_TC12 | TC-076593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS11_TC13 | TC-076594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS11_TC14 | TC-076595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | requires_review |
| BR1_TS11_TC15 | TC-076596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS11_TC16 | TC-076597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS11_TC17 | TC-076598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS11_TC18 | TC-076599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only on a POS terminal with offline pin → approved | pairwise | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC19 | TC-076600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC20 | TC-076601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC21 | TC-076602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-077178 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only Product Code one byte over its documented maximum length (3) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS12_TC2 | TC-077215 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only Ship-from Postal Code contains a character outside its character type (AN) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS12_TC3 | TC-076603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC4 | TC-076604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC5 | TC-076605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC6 | TC-076606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC7 | TC-076607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC8 | TC-076608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC9 | TC-076609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS12_TC10 | TC-076610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS12_TC11 | TC-076611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS12_TC12 | TC-076612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS12_TC13 | TC-076613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS12_TC14 | TC-076614 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS12_TC15 | TC-076615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS12_TC16 | TC-076616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only on a POS terminal with offline pin → approved | pairwise | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC17 | TC-076617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC18 | TC-076618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC19 | TC-076619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS13 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-076620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC2 | TC-076621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC3 | TC-076622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC4 | TC-076623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC5 | TC-076624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC6 | TC-076625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC7 | TC-076626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS13_TC8 | TC-076627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS13_TC9 | TC-076628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS13_TC10 | TC-076629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS13_TC11 | TC-076630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS13_TC12 | TC-076631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS13_TC13 | TC-076632 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS13_TC14 | TC-076633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Cancellations of non-gift cards must subtract' | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC15 | TC-076634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC16 | TC-076635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC17 | TC-076636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS14 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-076637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC2 | TC-076638 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC3 | TC-076639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC4 | TC-076640 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC5 | TC-076641 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC6 | TC-076642 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC7 | TC-076643 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS14_TC8 | TC-076644 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS14_TC9 | TC-076645 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS14_TC10 | TC-076646 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS14_TC11 | TC-076647 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS14_TC12 | TC-076648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS14_TC13 | TC-076649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS14_TC14 | TC-076650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC15 | TC-076651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC16 | TC-076652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS15 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-077191 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only Token Requestor ID exactly at its documented maximum length (11 bytes) is accepted → approved | single_rule_broken | Financial Transaction Request | 2 (ASSERT) | ready |
| BR1_TS15_TC2 | TC-076653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC3 | TC-076654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC4 | TC-076655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC5 | TC-076656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC6 | TC-076657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC7 | TC-076658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC8 | TC-076659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS15_TC9 | TC-076660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS15_TC10 | TC-076661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS15_TC11 | TC-076662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS15_TC12 | TC-076663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS15_TC13 | TC-076664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS15_TC14 | TC-076665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS15_TC15 | TC-076666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC16 | TC-076667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC17 | TC-076668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS16 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-076669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC2 | TC-076670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC3 | TC-076671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC4 | TC-076672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC5 | TC-076673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC6 | TC-076674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC7 | TC-076675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS16_TC8 | TC-076676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS16_TC9 | TC-076677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS16_TC10 | TC-076678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS16_TC11 | TC-076679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS16_TC12 | TC-076680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS16_TC13 | TC-076681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS16_TC14 | TC-076682 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC15 | TC-076683 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC16 | TC-076684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS17 BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-077184 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only CAVV Revised Format contains a character outside its character type (AN) → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS17_TC2 | TC-076685 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → approved [about 'Electronic journal should contain all approved transactions, excluding'] | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC3 | TC-076686 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC4 | TC-076687 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC5 | TC-076688 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC6 | TC-076689 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC7 | TC-076690 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC8 | TC-076691 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC9 | TC-076692 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS17_TC10 | TC-076693 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS17_TC11 | TC-076694 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS17_TC12 | TC-076695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS17_TC13 | TC-076696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS17_TC14 | TC-076697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS17_TC15 | TC-076698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS17_TC16 | TC-076699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC17 | TC-076700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC18 | TC-076701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-076702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-076703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC3 | TC-076704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC4 | TC-076705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC5 | TC-076706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC6 | TC-076707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only with Network Token Data Request sent → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC7 | TC-076708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS18_TC8 | TC-076709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture → approved | valid_flow | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC9 | TC-076710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS18_TC10 | TC-076711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Cancellation with no original transaction → declined (to confirm) | invalid_transition | S | 1: 1 (FLAG) | blocked_verification |
| BR1_TS18_TC11 | TC-076712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS18_TC12 | TC-076713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS18_TC13 | TC-076714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS18_TC14 | TC-076715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC15 | TC-076716 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (card issuer declines) → declined | response_check | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC16 | TC-076717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS19 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-076718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only → approved | happy_path | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC2 | TC-076719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC3 | TC-076720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC4 | TC-076721 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC5 | TC-076722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS19_TC6 | TC-076723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only with Network Token Data Request sent → approved | decision_table | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC7 | TC-076724 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS19_TC8 | TC-076725 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase → approved | valid_flow | B -> 6 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS19_TC9 | TC-076726 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | B -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS19_TC10 | TC-076727 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS19_TC11 | TC-076728 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | B -> 6 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS19_TC12 | TC-076729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS19_TC13 | TC-076730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for Response Code [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC14 | TC-076731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code B means Approved-Totals with electronic mail' [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC15 | TC-076732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code C means Declined-Totals with electronic mail' [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC16 | TC-076733 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code 6 means Approved-Totals with electronic mail' [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC17 | TC-076734 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for 'Transaction Code 7 means Declined-Totals with electronic mail' [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC18 | TC-076735 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Electronic Mail Data Segment [on Mail/Phone Authorization Only - Manual / Keyed] | ordering | - | n/a (no host message) | requires_review |
| BR1_TS19_TC19 | TC-076736 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Cancellation (card issuer declines) → declined | response_check | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS19_TC20 | TC-076737 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase (card issuer declines) → declined | response_check | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS19_TC21 | TC-076738 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS19_TC22 | TC-076739 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | B | 1: 3 (ASSERT) | needs_client_input |
| BR1_TS19_TC23 | TC-076740 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns A (Address matches, ZIP does not) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC24 | TC-076741 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns N (Neither ZIP nor address match) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC25 | TC-076742 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns R (System unavailabl e, retry) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC26 | TC-076743 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns S (AVS not supported) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC27 | TC-076744 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns U (Information not available) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC28 | TC-076745 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns W (For U.S., 9-digit ZIP matches, address does not. For non- US, ZIP matches, address does not) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC29 | TC-076746 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns X (For U.S., all digits match. For non-U.S., ZIP and address match) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC30 | TC-076747 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns Y (Address & 5-digit ZIP match) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC31 | TC-076748 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sending avs = true, issuer returns Z (5-digit ZIP matches, address does not) → approved (to confirm) | issuer_outcome | B | 1: 3 (FLAG) | needs_client_input |
| BR1_TS19_TC32 | TC-076749 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Message Format Version Identifier [on Mail/Phone Authorization Only - Manual / Keyed] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS19_TC33 | TC-076750 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 05 (EMV Contact Chip, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | requires_review |
| BR1_TS19_TC34 | TC-076751 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 07 (EMV Contactless Chip, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | requires_review |
| BR1_TS19_TC35 | TC-076752 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 02 (Magnetic Stripe, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | requires_review |
| BR1_TS19_TC36 | TC-076753 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 91 (Contactless Magnetic Stripe, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | requires_review |
| BR1_TS19_TC37 | TC-076754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 79 (Chip Fallback, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | requires_review |
| BR1_TS19_TC38 | TC-076755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization Only sent with entry mode 82 (Contactless Mobile Commerce Device, not supported) → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | requires_review |

### BR1_TS20 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-076756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only → approved [about 'Electronic Mail Request for retrieval can contain'] | happy_path | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-076757 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only → approved [about 'Credit card processing supports Purchase/Capture, Purchase Reversal, Authorization'] | happy_path | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC3 | TC-076758 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC4 | TC-076759 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC5 | TC-076760 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase with Sequence Number different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC6 | TC-076761 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC7 | TC-076762 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only with Network Token Data Request sent → approved | decision_table | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC8 | TC-076763 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS20_TC9 | TC-076764 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase → approved | valid_flow | B -> 6 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS20_TC10 | TC-076765 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | B -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS20_TC11 | TC-076766 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS20_TC12 | TC-076767 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | B -> 6 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS20_TC13 | TC-076768 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS20_TC14 | TC-076769 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Cancellation (card issuer declines) → declined | response_check | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC15 | TC-076770 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only, then Mail/Phone Purchase (card issuer declines) → declined | response_check | B -> 6 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC16 | TC-076771 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC17 | TC-076772 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorization Only sending avs = true (AVS or NVS or Both Information returned) → approved | response_check | B | 1: 3 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-077221 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return Variable Information Indicator one byte over its documented maximum length (3) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS21_TC2 | TC-077183 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return Required field Product Amount sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS21_TC3 | TC-076773 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS21_TC4 | TC-076774 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC5 | TC-076775 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC6 | TC-076776 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS21_TC7 | TC-076777 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS21_TC8 | TC-076778 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS21_TC9 | TC-076779 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS21_TC10 | TC-076780 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS21_TC11 | TC-076781 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS21_TC12 | TC-076782 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS21_TC13 | TC-076783 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS22 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-076784 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS22_TC2 | TC-076785 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC3 | TC-076786 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC4 | TC-076787 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | requires_review |
| BR1_TS22_TC5 | TC-076788 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS22_TC6 | TC-076789 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS22_TC7 | TC-076790 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS22_TC8 | TC-076791 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS22_TC9 | TC-076792 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS22_TC10 | TC-076793 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS22_TC11 | TC-076794 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS23 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-076795 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS23_TC2 | TC-076796 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS23_TC3 | TC-076797 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS23_TC4 | TC-076798 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS23_TC5 | TC-076799 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS23_TC6 | TC-076800 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS23_TC7 | TC-076801 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS23_TC8 | TC-076802 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS23_TC9 | TC-076803 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS23_TC10 | TC-076804 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS24 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-076805 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS24_TC2 | TC-076806 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS24_TC3 | TC-076807 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS24_TC4 | TC-076808 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS24_TC5 | TC-076809 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS24_TC6 | TC-076810 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS24_TC7 | TC-076811 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS24_TC8 | TC-076812 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS24_TC9 | TC-076813 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS24_TC10 | TC-076814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS25 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-076815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS25_TC2 | TC-076816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS25_TC3 | TC-076817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS25_TC4 | TC-076818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS25_TC5 | TC-076819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS25_TC6 | TC-076820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS25_TC7 | TC-076821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS25_TC8 | TC-076822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS25_TC9 | TC-076823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS25_TC10 | TC-076824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS26 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-077189 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return Token PAN Suffix one byte over its documented maximum length (4) → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS26_TC2 | TC-076825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS26_TC3 | TC-076826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS26_TC4 | TC-076827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | requires_review |
| BR1_TS26_TC5 | TC-076828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | ready |
| BR1_TS26_TC6 | TC-076829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | requires_review |
| BR1_TS26_TC7 | TC-076830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS26_TC8 | TC-076831 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS26_TC9 | TC-076832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS26_TC10 | TC-076833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS26_TC11 | TC-076834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS27 BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-076835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS27_TC2 | TC-076836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-076837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC4 | TC-076838 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS27_TC5 | TC-076839 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS27_TC6 | TC-076840 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | pending_sme |
| BR1_TS27_TC7 | TC-076841 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return (no response), then Void of a Merchandise Return → approved | fault_recovery | 7 -> U | 1: no response expected ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS27_TC8 | TC-076842 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS27_TC9 | TC-076843 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS27_TC10 | TC-076844 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS27_TC11 | TC-076845 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS28 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-076846 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS28_TC2 | TC-076847 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return with Sequence Number different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-076848 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return with Terminal Identifier different from the original → declined (to confirm) | single_rule_broken | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC4 | TC-076849 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS28_TC5 | TC-076850 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 7 -> U -> U | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS28_TC6 | TC-076851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Void of a Merchandise Return with no original transaction → declined (to confirm) | invalid_transition | U | 1: 1 (FLAG) | blocked_verification |
| BR1_TS28_TC7 | TC-076852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> U -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 0, 2) (INFER) ; 3: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS28_TC8 | TC-076853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS28_TC9 | TC-076854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: approved (any of 0, 2) (INFER) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS28_TC10 | TC-076855 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS29 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-077218 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | not stated by the spec (FLAG) | requires_review |
| BR1_TS29_TC2 | TC-077210 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry EMV Chip Data Length contains a character outside its character type (N) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS29_TC3 | TC-077217 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry Tax Type 1 value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS29_TC4 | TC-077179 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry required Standard Message Data Segment omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | blocked_no_test_data |
| BR1_TS29_TC5 | TC-076856 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS29_TC6 | TC-076857 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS29_TC7 | TC-076858 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS29_TC8 | TC-076859 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS30 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-077205 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry Update Code one byte over its documented maximum length (1) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS30_TC2 | TC-076860 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS30_TC3 | TC-076861 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS30_TC4 | TC-076862 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS30_TC5 | TC-076863 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS31 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-077185 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted → approved | single_rule_broken | Financial Transaction Request | not stated by the spec (FLAG) | requires_review |
| BR1_TS31_TC2 | TC-076864 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS31_TC3 | TC-076865 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS31_TC4 | TC-076866 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS32 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-076867 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS32_TC2 | TC-076868 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS32_TC3 | TC-076869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS33 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-076870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS33_TC2 | TC-076871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS33_TC3 | TC-076872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS34 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-076873 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | ready |
| BR1_TS34_TC2 | TC-076874 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | requires_review |
| BR1_TS34_TC3 | TC-076875 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS35 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-076876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS35_TC2 | TC-076877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS35_TC3 | TC-076878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS36 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-076879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balance Inquiry → approved | happy_path | E | 1: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS36_TC2 | TC-076880 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: approved (any of 0, 2) (INFER) ; 2: approved (any of 5, 6, B, D, N, W) (INFER) | pending_sme |
| BR1_TS36_TC3 | TC-076881 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS47 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS47_TC1 | TC-076882 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS47_TC2 | TC-076883 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS48 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS48_TC1 | TC-076884 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS48_TC2 | TC-076885 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS49 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS49_TC1 | TC-076886 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS49_TC2 | TC-076887 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS49_TC3 | TC-076888 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Device check: device behaviour: 'An exception report prints after a successful batch' | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS50 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS50_TC1 | TC-076889 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS50_TC2 | TC-076890 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS51 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS51_TC1 | TC-076891 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC2 | TC-076892 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture with incremental = incremental → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC3 | TC-076893 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS52 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS52_TC1 | TC-076894 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS52_TC2 | TC-076895 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS53 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS53_TC1 | TC-076896 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS53_TC2 | TC-076897 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS54 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS54_TC1 | TC-076898 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC2 | TC-076899 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS55 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS55_TC1 | TC-076900 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS55_TC2 | TC-076901 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS56 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS56_TC1 | TC-076902 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS56_TC2 | TC-076903 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS57 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS57_TC1 | TC-076904 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS57_TC2 | TC-076905 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS58 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS58_TC1 | TC-076906 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC2 | TC-076907 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS59 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS59_TC1 | TC-076908 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS59_TC2 | TC-076909 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS60 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS60_TC1 | TC-076910 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS60_TC2 | TC-076911 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS147 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS147_TC1 | TC-077202 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Additional Information exactly at its documented maximum length (984 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC2 | TC-077195 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Product Code contains a character outside its character type (N) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS147_TC3 | TC-077203 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Customer Data contains a character outside its character type (AN) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS147_TC4 | TC-076912 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC5 | TC-076913 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC6 | TC-076914 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS147_TC7 | TC-076915 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS147_TC8 | TC-076916 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS147_TC9 | TC-076917 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS147_TC10 | TC-076918 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS147_TC11 | TC-076919 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS147_TC12 | TC-076920 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC13 | TC-076921 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC14 | TC-076922 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC15 | TC-076923 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS148 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS148_TC1 | TC-077214 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture Loyalty Track 2 Data one byte over its documented maximum length (38) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC2 | TC-077198 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture Information Byte value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC3 | TC-076924 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC4 | TC-076925 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC5 | TC-076926 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS148_TC6 | TC-076927 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC7 | TC-076928 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS148_TC8 | TC-076929 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS148_TC9 | TC-076930 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS148_TC10 | TC-076931 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS148_TC11 | TC-076932 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC12 | TC-076933 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC13 | TC-076934 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC14 | TC-076935 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS149 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS149_TC1 | TC-076936 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC2 | TC-076937 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC3 | TC-076938 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Credit Dollar Value | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC4 | TC-076939 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Response Code | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC5 | TC-076940 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Transaction Attributes Segment | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC6 | TC-076941 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS149_TC7 | TC-076942 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS149_TC8 | TC-076943 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS149_TC9 | TC-076944 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS149_TC10 | TC-076945 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS149_TC11 | TC-076946 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS149_TC12 | TC-076947 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC13 | TC-076948 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC14 | TC-076949 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS149_TC15 | TC-076950 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 2 terminal behaviour checks [about 'PAN must be truncated to last four digits'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC16 | TC-076951 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 2 terminal behaviour checks [about 'Expiration Date must be masked on a receipt's'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC17 | TC-076952 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 4 terminal behaviour checks [about 'Express Code must be masked on all receipts'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC18 | TC-076953 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 4 terminal behaviour checks [about 'Approval phrase including approval code'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC19 | TC-076954 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 6 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC20 | TC-076955 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 8 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC21 | TC-076956 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Prompt Code | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC22 | TC-076957 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Receipt Text Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS149_TC23 | TC-076958 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: RID | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS150 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS150_TC1 | TC-076959 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC2 | TC-076960 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC3 | TC-076961 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS150_TC4 | TC-076962 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS150_TC5 | TC-076963 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS150_TC6 | TC-076964 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS150_TC7 | TC-076965 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS150_TC8 | TC-076966 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS150_TC9 | TC-076967 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC10 | TC-076968 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC11 | TC-076969 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS151 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS151_TC1 | TC-076970 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC2 | TC-076971 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC3 | TC-076972 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS151_TC4 | TC-076973 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS151_TC5 | TC-076974 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS151_TC6 | TC-076975 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS151_TC7 | TC-076976 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS151_TC8 | TC-076977 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS151_TC9 | TC-076978 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC10 | TC-076979 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC11 | TC-076980 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS152 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS152_TC1 | TC-076981 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC2 | TC-076982 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC3 | TC-076983 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS152_TC4 | TC-076984 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS152_TC5 | TC-076985 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS152_TC6 | TC-076986 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS152_TC7 | TC-076987 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS152_TC8 | TC-076988 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS152_TC9 | TC-076989 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC10 | TC-076990 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC11 | TC-076991 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS153 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS153_TC1 | TC-076992 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC2 | TC-076993 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC3 | TC-076994 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | blocked_verification |
| BR1_TS153_TC4 | TC-076995 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal x2 (the second one after the original is already closed) → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS153_TC5 | TC-076996 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS153_TC6 | TC-076997 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS153_TC7 | TC-076998 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS153_TC8 | TC-076999 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS153_TC9 | TC-077000 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC10 | TC-077001 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture (Host Prompts Information returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC11 | TC-077002 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC12 | TC-077003 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS154 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS154_TC1 | TC-077197 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only Purchase Code exactly at its documented maximum length (16 bytes) is accepted → approved | single_rule_broken | EMV Financial Transaction Request | 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC2 | TC-077204 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only Customer Data one byte over its documented maximum length (12) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS154_TC3 | TC-077004 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC4 | TC-077005 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS155 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS155_TC1 | TC-077006 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC2 | TC-077007 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS156 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS156_TC1 | TC-077008 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC2 | TC-077009 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS157 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS157_TC1 | TC-077199 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authorization Only Required field Information Byte omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS157_TC2 | TC-077010 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC3 | TC-077011 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS158 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS158_TC1 | TC-077012 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC2 | TC-077013 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS159 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS159_TC1 | TC-077014 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC2 | TC-077015 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS160 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS160_TC1 | TC-077016 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC2 | TC-077017 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS161 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS161_TC1 | TC-077196 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return Purchase Code one byte over its documented maximum length (16) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS161_TC2 | TC-077200 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return Required field Information Byte sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS161_TC3 | TC-077018 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS162 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS162_TC1 | TC-077019 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS163 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS163_TC1 | TC-077020 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS164 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS164_TC1 | TC-077021 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS165 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS165_TC1 | TC-077022 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS166 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS166_TC1 | TC-077023 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | needs_client_input |

### BR1_TS167 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS167_TC1 | TC-077024 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: approved (any of 0, 2) (INFER) | pending_sme |

### BR1_TS168 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS168_TC1 | TC-077192 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture Product Amount contains a character outside its character type (N) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS168_TC2 | TC-077025 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC3 | TC-077026 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC4 | TC-077027 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC5 | TC-077028 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS168_TC6 | TC-077029 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC7 | TC-077030 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS168_TC8 | TC-077031 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS168_TC9 | TC-077032 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS168_TC10 | TC-077033 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS168_TC11 | TC-077034 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS168_TC12 | TC-077035 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC13 | TC-077036 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC14 | TC-077037 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC15 | TC-077038 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC16 | TC-077039 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS169 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS169_TC1 | TC-077040 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC2 | TC-077041 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC3 | TC-077042 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS169_TC4 | TC-077043 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC5 | TC-077044 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS169_TC6 | TC-077045 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS169_TC7 | TC-077046 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS169_TC8 | TC-077047 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS169_TC9 | TC-077048 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS169_TC10 | TC-077049 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC11 | TC-077050 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (EMV Response Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC12 | TC-077051 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC13 | TC-077052 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC14 | TC-077053 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS170 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS170_TC1 | TC-077054 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC2 | TC-077055 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC3 | TC-077056 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Credit Dollar Value | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC4 | TC-077057 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Response Code | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC5 | TC-077058 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Transaction Attributes Segment | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC6 | TC-077059 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS170_TC7 | TC-077060 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC8 | TC-077061 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS170_TC9 | TC-077062 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS170_TC10 | TC-077063 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS170_TC11 | TC-077064 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS170_TC12 | TC-077065 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS170_TC13 | TC-077066 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: running total / state for Moneris Day End Batch Balance Segment Format (Request) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS170_TC14 | TC-077067 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC15 | TC-077068 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC16 | TC-077069 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC17 | TC-077070 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS170_TC18 | TC-077071 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 2 terminal behaviour checks [about 'Receipt is printed after fuel dispensed if cardholder'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC19 | TC-077072 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 2 terminal behaviour checks [about 'PAN must be truncated to last four digits'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC20 | TC-077073 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 2 terminal behaviour checks [about 'Expiration Date must be masked on a receipt's'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC21 | TC-077074 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 4 terminal behaviour checks [about 'Express Code must be masked on all receipts'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC22 | TC-077075 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 4 terminal behaviour checks [about 'Approval phrase including approval code'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC23 | TC-077076 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 4 terminal behaviour checks [about 'The Daily Exception Log should print at end'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC24 | TC-077077 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 6 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC25 | TC-077078 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 8 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC26 | TC-077079 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Prompt Code | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC27 | TC-077080 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Receipt Text Data [about 'Off-line controller-generated receipt numbers must start with LA0001'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC28 | TC-077081 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Receipt Text Data [about 'A receipt can be printed after fuel'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS170_TC29 | TC-077082 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: RID | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS171 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS171_TC1 | TC-077083 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC2 | TC-077084 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC3 | TC-077085 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS171_TC4 | TC-077086 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC5 | TC-077087 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS171_TC6 | TC-077088 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS171_TC7 | TC-077089 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS171_TC8 | TC-077090 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 4 | 1: 0 (FLAG) | requires_review |
| BR1_TS171_TC9 | TC-077091 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 4 | 1: 1 (FLAG) | requires_review |
| BR1_TS171_TC10 | TC-077092 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC11 | TC-077093 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC12 | TC-077094 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC13 | TC-077095 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS172 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS172_TC1 | TC-077096 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC2 | TC-077097 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC3 | TC-077098 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS172_TC4 | TC-077099 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC5 | TC-077100 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS172_TC6 | TC-077101 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture resent 3 times, at the limit → approved (to confirm) | idempotency_retry | 4 | 1: 0 (FLAG) | pending_sme |
| BR1_TS172_TC7 | TC-077102 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture resent 4 times, beyond the limit of 3 → declined (to confirm) | idempotency_retry | 4 | 1: 1 (FLAG) | pending_sme |
| BR1_TS172_TC8 | TC-077103 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture held offline 55 minutes, at the limit → approved (to confirm) | time_based | 4 | 1: 0 (FLAG) | pending_sme |
| BR1_TS172_TC9 | TC-077104 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture held offline 56 minutes, beyond the 55-minute limit → declined (to confirm) | time_based | 4 | 1: 1 (FLAG) | pending_sme |
| BR1_TS172_TC10 | TC-077105 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (Additional Information Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC11 | TC-077106 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (Host Prompts Information returned) → approved | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC12 | TC-077107 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Online Request outcome'] | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC13 | TC-077108 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'] | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC14 | TC-077109 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS173 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS173_TC1 | TC-077201 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only Encrypted PIN Block Data one byte over its documented maximum length (36) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS173_TC2 | TC-077110 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC3 | TC-077111 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS173_TC4 | TC-077112 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS173_TC5 | TC-077113 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS173_TC6 | TC-077114 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS174 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS174_TC1 | TC-077194 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only Required field Prompt Code sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS174_TC2 | TC-077115 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC3 | TC-077116 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS174_TC4 | TC-077117 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS174_TC5 | TC-077118 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS174_TC6 | TC-077119 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS175 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS175_TC1 | TC-077193 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only Required field Prompt Code omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS175_TC2 | TC-077120 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC3 | TC-077121 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS175_TC4 | TC-077122 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS175_TC5 | TC-077123 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS175_TC6 | TC-077124 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS176 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS176_TC1 | TC-077125 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC2 | TC-077126 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS176_TC3 | TC-077127 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS176_TC4 | TC-077128 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS176_TC5 | TC-077129 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS177 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS177_TC1 | TC-077130 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC2 | TC-077131 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS177_TC3 | TC-077132 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture → approved | valid_flow | 5 -> 4 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS177_TC4 | TC-077133 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |
| BR1_TS177_TC5 | TC-077134 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS178 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS178_TC1 | TC-077135 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS178_TC2 | TC-077136 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS178_TC3 | TC-077137 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS178_TC4 | TC-077138 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS178_TC5 | TC-077139 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS178_TC6 | TC-077140 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture on a CAT terminal with offline pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS179 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS179_TC1 | TC-077141 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS179_TC2 | TC-077142 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS179_TC3 | TC-077143 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS179_TC4 | TC-077144 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS179_TC5 | TC-077145 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS179_TC6 | TC-077146 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture on a CAT terminal with offline pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS180 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS180_TC1 | TC-077147 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS180_TC2 | TC-077148 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS180_TC3 | TC-077149 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS180_TC4 | TC-077150 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS180_TC5 | TC-077151 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |

### BR1_TS181 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS181_TC1 | TC-077152 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS181_TC2 | TC-077153 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS181_TC3 | TC-077154 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | needs_client_input |
| BR1_TS181_TC4 | TC-077155 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS181_TC5 | TC-077156 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | needs_client_input |

### BR1_TS182 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS182_TC1 | TC-077157 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS182_TC2 | TC-077158 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture with Final Amount Indicator → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS182_TC3 | TC-077159 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) | pending_sme |
| BR1_TS182_TC4 | TC-077160 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture, then Purchase/Capture (first card partly approved, balance on a second card) → approved | valid_flow | 4 -> 4 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS182_TC5 | TC-077161 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal x2 (the second one after the original is already closed) → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: approved (any of 0, 2) (INFER) ; 3: 1 (FLAG) | pending_sme |

### BR1_TS183 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS183_TC1 | TC-077162 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS184 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS184_TC1 | TC-077163 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS185 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS185_TC1 | TC-077164 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS186 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS186_TC1 | TC-077165 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS187 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS187_TC1 | TC-077166 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS188 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS188_TC1 | TC-077167 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS189 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS189_TC1 | TC-077168 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS190 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS190_TC1 | TC-077169 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS191 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS191_TC1 | TC-077170 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS192 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS192_TC1 | TC-077171 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS193 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS193_TC1 | TC-077172 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS194 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS194_TC1 | TC-077173 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS195 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS195_TC1 | TC-077174 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS196 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS196_TC1 | TC-077175 | BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

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
- BR1_TS168 G-IT-a00d4f7b35: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS168 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-IT-a00d4f7b35: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-IT-a00d4f7b35: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-IT-a00d4f7b35: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-IT-a00d4f7b35: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS178 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
- BR1_TS179 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)
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
