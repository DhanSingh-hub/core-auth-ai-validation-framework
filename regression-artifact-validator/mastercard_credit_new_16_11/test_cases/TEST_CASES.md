# Test cases - BR1 BUYPASS ATL105 MasterCard Credit

Build `buypass-atl105-mastercard-credit-generated-923e8c2eac`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` generated-2026-11-13.2.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 196 |
| Test cases | 2129 |
| By status | ready: 619, blocked_no_test_data: 3, needs_client_input: 861, requires_review: 363, pending_sme: 283 |
| By polarity | Negative: 428, Boundary: 5, Positive: 1696 |
| Verification | PASS: 1993, NOT_APPLICABLE: 136 |
| Verification level | PASS: 1993, NOT_APPLICABLE: 136 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 1546, FLAG: 245, INFER: 202, N/A: 136 |
| Scenarios with test cases | 182 / 196 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| single_rule_broken Single rule broken | 183 |
| boundary Boundary | 1 |
| happy_path Happy path | 495 |
| decision_table Decision table | 283 |
| valid_flow Valid flow | 63 |
| invalid_transition Invalid state transition | 98 |
| fault_recovery Fault / recovery | 20 |
| idempotency_retry Idempotency / duplicate / retry | 32 |
| running_total Running total / state | 83 |
| pairwise Interaction (pairwise) | 63 |
| response_check Response / oracle check | 522 |
| ordering Ordering | 6 |
| device_checkpoint Device checkpoint | 91 |
| alternate_path Alternate path | 126 |
| time_based Time-based | 40 |
| soft_constraint Soft constraint | 7 |
| co_requirement Co-requirement / mutual exclusion | 2 |
| routing Routing | 12 |
| arithmetic Arithmetic consistency | 2 |

## BR1 BUYPASS ATL105 MasterCard Credit


### BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-038627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Required field EMV Chip Data Component Name: Value (ANSB ...255… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC2 | TC-038626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture EMV Chip Data Component Name: Value (ANSB ...255) The data of e… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC3 | TC-038635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Variable Information one byte over its documented maximum length → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC4 | TC-038630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture EMV Card Sequence Number value not in its documented valid valu… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC5 | TC-038596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | blocked_no_test_data |
| BR1_TS1_TC6 | TC-038598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Network Token Data Request omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS1_TC7 | TC-039814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC8 | TC-039815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal with Sequence Number diffe… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS1_TC9 | TC-039816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal with Terminal Identifier d… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS1_TC10 | TC-039817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal with Terminal Identifier d… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS1_TC11 | TC-039818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC12 | TC-039819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC13 | TC-039820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC14 | TC-039821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS1_TC15 | TC-039822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS1_TC16 | TC-039823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS1_TC17 | TC-039824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS1_TC18 | TC-039825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS1_TC19 | TC-039826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS1_TC20 | TC-039827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS1_TC21 | TC-039828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS1_TC22 | TC-039829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS1_TC23 | TC-039830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS1_TC24 | TC-039831 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contact Chip Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC25 | TC-039832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC26 | TC-039833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC27 | TC-039834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC28 | TC-039835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS1_TC29 | TC-039836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS1_TC30 | TC-039837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-038632 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture CA Public Key File Checksum exactly at its documented maxim… → declined | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | ready |
| BR1_TS2_TC2 | TC-038631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture Required field Prompt Code omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC3 | TC-039838 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC4 | TC-039839 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Sequence Number d… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS2_TC5 | TC-039840 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Terminal Identifi… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS2_TC6 | TC-039841 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal with Terminal Identifi… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS2_TC7 | TC-039842 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC8 | TC-039843 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC9 | TC-039844 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC10 | TC-039845 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS2_TC11 | TC-039846 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS2_TC12 | TC-039847 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal x2 (the second one aft… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS2_TC13 | TC-039848 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS2_TC14 | TC-039849 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS2_TC15 | TC-039850 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS2_TC16 | TC-039851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS2_TC17 | TC-039852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals includ… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS2_TC18 | TC-039853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal, then Totals Request (totals includ… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS2_TC19 | TC-039854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS2_TC20 | TC-039855 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contactless Chip Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC21 | TC-039856 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC22 | TC-039857 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC23 | TC-039858 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC24 | TC-039859 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS2_TC25 | TC-039860 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS2_TC26 | TC-039861 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-038628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture Cryptogram Token Data one byte over its documented maximum lengt… → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-039862 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC3 | TC-039863 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence Number differ… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS3_TC4 | TC-039864 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal Identifier di… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS3_TC5 | TC-039865 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal with Terminal Identifier di… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS3_TC6 | TC-039866 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC7 | TC-039867 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC8 | TC-039868 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC9 | TC-039869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS3_TC10 | TC-039870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS3_TC11 | TC-039871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after th… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS3_TC12 | TC-039872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS3_TC13 | TC-039873 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS3_TC14 | TC-039874 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS3_TC15 | TC-039875 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS3_TC16 | TC-039876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS3_TC17 | TC-039877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS3_TC18 | TC-039878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS3_TC19 | TC-039879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Summary of TOR Processing Rules | ordering | - | n/a (no host message) | requires_review |
| BR1_TS3_TC20 | TC-039880 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Magnetic Stripe Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC21 | TC-039881 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC22 | TC-039882 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC23 | TC-039883 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC24 | TC-039884 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS3_TC25 | TC-039885 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS3_TC26 | TC-039886 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC27 | TC-039887 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 3 terminal behaviour checks [about 'Customer's host/device should have a maximum number of'…] | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-039888 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC2 | TC-039889 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence N… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS4_TC3 | TC-039890 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal I… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS4_TC4 | TC-039891 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal with Terminal I… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS4_TC5 | TC-039892 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC6 | TC-039893 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC7 | TC-039894 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC8 | TC-039895 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS4_TC9 | TC-039896 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS4_TC10 | TC-039897 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS4_TC11 | TC-039898 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS4_TC12 | TC-039899 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS4_TC13 | TC-039900 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS4_TC14 | TC-039901 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS4_TC15 | TC-039902 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS4_TC16 | TC-039903 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal, then Totals Request (totals… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS4_TC17 | TC-039904 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS4_TC18 | TC-039905 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC19 | TC-039906 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC20 | TC-039907 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC21 | TC-039908 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC22 | TC-039909 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS4_TC23 | TC-039910 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS4_TC24 | TC-039911 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-039912 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-039913 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS5_TC3 | TC-039914 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS5_TC4 | TC-039915 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS5_TC5 | TC-039916 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC6 | TC-039917 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC7 | TC-039918 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC8 | TC-039919 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS5_TC9 | TC-039920 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS5_TC10 | TC-039921 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal x2 (the second one after the… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS5_TC11 | TC-039922 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS5_TC12 | TC-039923 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS5_TC13 | TC-039924 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS5_TC14 | TC-039925 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS5_TC15 | TC-039926 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS5_TC16 | TC-039927 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS5_TC17 | TC-039928 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS5_TC18 | TC-039929 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Manual / Keyed Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC19 | TC-039930 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC20 | TC-039931 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC21 | TC-039932 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC22 | TC-039933 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS5_TC23 | TC-039934 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS5_TC24 | TC-039935 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-039936 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC2 | TC-039937 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal with Sequence Number differen… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS6_TC3 | TC-039938 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal with Terminal Identifier diff… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS6_TC4 | TC-039939 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal with Terminal Identifier diff… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS6_TC5 | TC-039940 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC6 | TC-039941 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC7 | TC-039942 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC8 | TC-039943 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS6_TC9 | TC-039944 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS6_TC10 | TC-039945 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal x2 (the second one after the… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS6_TC11 | TC-039946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS6_TC12 | TC-039947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS6_TC13 | TC-039948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS6_TC14 | TC-039949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS6_TC15 | TC-039950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS6_TC16 | TC-039951 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS6_TC17 | TC-039952 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS6_TC18 | TC-039953 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Chip Fallback Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC19 | TC-039954 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC20 | TC-039955 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC21 | TC-039956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC22 | TC-039957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS6_TC23 | TC-039958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS6_TC24 | TC-039959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-038602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture Enhanced Fleet Request omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS7_TC2 | TC-039960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC3 | TC-039961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal with Sequence Number dif… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS7_TC4 | TC-039962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal with Terminal Identifier… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS7_TC5 | TC-039963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal with Terminal Identifier… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS7_TC6 | TC-039964 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC7 | TC-039965 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC8 | TC-039966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-039967 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with Enhanced Fleet Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC10 | TC-039968 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS7_TC11 | TC-039969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS7_TC12 | TC-039970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal x2 (the second one after… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS7_TC13 | TC-039971 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS7_TC14 | TC-039972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS7_TC15 | TC-039973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS7_TC16 | TC-039974 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS7_TC17 | TC-039975 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal, then Totals Request (totals include… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS7_TC18 | TC-039976 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal, then Totals Request (totals include… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS7_TC19 | TC-039977 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS7_TC20 | TC-039978 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Credential on File Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC21 | TC-039979 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC22 | TC-039980 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC23 | TC-039981 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC24 | TC-039982 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS7_TC25 | TC-039983 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS7_TC26 | TC-039984 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-039985 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-039986 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Seq… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC3 | TC-039987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Ter… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC4 | TC-039988 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal with Ter… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-039989 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture with Network Token Data Request sent → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC6 | TC-039990 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC7 | TC-039991 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC8 | TC-039992 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS8_TC9 | TC-039993 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS8_TC10 | TC-039994 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal x2 (the… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS8_TC11 | TC-039995 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC12 | TC-039996 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS8_TC13 | TC-039997 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS8_TC14 | TC-039998 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x3 (res… → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | pending_sme |
| BR1_TS8_TC15 | TC-039999 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal, then Totals Request… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS8_TC16 | TC-040000 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal, then Totals Request… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS8_TC17 | TC-040001 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS8_TC18 | TC-040002 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC19 | TC-040003 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC20 | TC-040004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC21 | TC-040005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC22 | TC-040006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC23 | TC-040007 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC24 | TC-040008 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-038592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase Required field Partial Approval Indicator omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS9_TC2 | TC-040009 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase → approved | happy_path | 6 | 1: 0 (ASSERT) | ready |
| BR1_TS9_TC3 | TC-040010 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Number di… → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS9_TC4 | TC-040011 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Identifie… → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS9_TC5 | TC-040012 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal with Terminal Identifier… → declined (to confirm) | single_rule_broken | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS9_TC6 | TC-040013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase with incremental = completion → approved | decision_table | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC7 | TC-040014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS9_TC8 | TC-040015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal → approved | valid_flow | 6 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS9_TC9 | TC-040016 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the origina… → declined | invalid_transition | 6 -> C -> C | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | ready |
| BR1_TS9_TC10 | TC-040017 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal x2 (the second one after… → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS9_TC11 | TC-040018 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Reversal with no original transaction → declined | invalid_transition | C | 1: 1 (ASSERT) | ready |
| BR1_TS9_TC12 | TC-040019 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal → approved | fault_recovery | 6 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS9_TC13 | TC-040020 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals include… → approved | running_total | 6 -> C -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS9_TC14 | TC-040021 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal, then Totals Request (totals include i… → approved | running_total | 6 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS9_TC15 | TC-040022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS9_TC16 | TC-040023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for Response Code [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC17 | TC-040024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code B means Approved-Totals with electronic mail'… [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC18 | TC-040025 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code C means Declined-Totals with electronic mail'… [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC19 | TC-040026 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code 6 means Approved-Totals with electronic mail'… [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC20 | TC-040027 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code 7 means Declined-Totals with electronic mail'… [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC21 | TC-040028 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Electronic Mail Data Segment [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | ordering | - | n/a (no host message) | requires_review |
| BR1_TS9_TC22 | TC-040029 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase on a POS terminal with offline pin → approved | pairwise | 6 | 1: 0 (ASSERT) | ready |
| BR1_TS9_TC23 | TC-040030 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (AVS or NVS or Both Information returned) → approved | response_check | 6 | 1: 0 (ASSERT) | ready |
| BR1_TS9_TC24 | TC-040031 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal (card issuer declines) → declined | response_check | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS9_TC25 | TC-040032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 6 | 1: 0 (ASSERT) | ready |
| BR1_TS9_TC26 | TC-040033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 6 | 1: 0 (ASSERT) | ready |
| BR1_TS9_TC27 | TC-040034 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal (card issuer declines) → declined | response_check | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS9_TC28 | TC-040035 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | ready |
| BR1_TS9_TC29 | TC-040036 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Message Format Version Identifier [on Mail/Phone Purchase - Manual / Keyed @ Mail Order · mail and/or telephone order] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS9_TC30 | TC-040037 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 05 (EMV Contact Chip, not su… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC31 | TC-040038 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 07 (EMV Contactless Chip, no… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC32 | TC-040039 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 02 (Magnetic Stripe, not sup… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC33 | TC-040040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 91 (Contactless Magnetic Str… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC34 | TC-040041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 79 (Chip Fallback, not suppo… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC35 | TC-040042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 82 (Contactless Mobile Comme… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |

### BR1_TS10 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-040043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase → approved | happy_path | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC2 | TC-040044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Numbe… → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC3 | TC-040045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Ident… → declined (to confirm) | single_rule_broken | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-040046 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal with Terminal Identif… → declined (to confirm) | single_rule_broken | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-040047 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase with incremental = completion → approved | decision_table | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC6 | TC-040048 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS10_TC7 | TC-040049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal → approved | valid_flow | 6 -> Z | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS10_TC8 | TC-040050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the ori… → declined | invalid_transition | 6 -> C -> C | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC9 | TC-040051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal x2 (the second one af… → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC10 | TC-040052 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Reversal with no original transaction → declined | invalid_transition | C | 1: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC11 | TC-040053 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (no response), then Mail/Phone Reversal → approved | fault_recovery | 6 -> C | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC12 | TC-040054 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (no response), then Time-out Reversal → approved | fault_recovery | 6 -> Z | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC13 | TC-040055 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals inc… → approved | running_total | 6 -> C -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS10_TC14 | TC-040056 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal, then Totals Request (totals inclu… → approved | running_total | 6 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS10_TC15 | TC-040057 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS10_TC16 | TC-040058 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase on a POS terminal with offline pin → approved | pairwise | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC17 | TC-040059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (AVS or NVS or Both Information returned) → approved | response_check | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC18 | TC-040060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal (card issuer declines) → declined | response_check | 6 -> C | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC19 | TC-040061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (Merchant Data Segment returned) → approved [about 'Load flag in merchant's profile must be set'…] | response_check | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC20 | TC-040062 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (Merchant Data Segment returned) → approved [about 'Approved TOR transactions are subtracted from count and'…] | response_check | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC21 | TC-040063 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal (card issuer declines) → declined | response_check | 6 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC22 | TC-040064 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-038603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Required field EMV Chip Data Length sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC2 | TC-038593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Partial Approval Indicator value not in its documented valid… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC3 | TC-038636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Variable Information Indicator value not in its documented va… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC4 | TC-038604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only NFC Payment Tokenization Data Segment omitted where requires… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS11_TC5 | TC-038608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Purchase Card Data Segment omitted where requires it → declined | boundary | EMV Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS11_TC6 | TC-040065 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC7 | TC-040066 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC8 | TC-040067 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC9 | TC-040068 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC10 | TC-040069 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC11 | TC-040070 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC12 | TC-040071 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC13 | TC-040072 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC14 | TC-040073 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC15 | TC-040074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC16 | TC-040075 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC17 | TC-040076 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC18 | TC-040077 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC19 | TC-040078 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC20 | TC-040079 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC21 | TC-040080 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC22 | TC-040081 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC23 | TC-040082 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC24 | TC-040083 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC25 | TC-040084 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC26 | TC-040085 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC27 | TC-040086 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC28 | TC-040087 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC29 | TC-040088 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC30 | TC-040089 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC31 | TC-040090 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC32 | TC-040091 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC33 | TC-040092 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC34 | TC-040093 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC35 | TC-040094 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC36 | TC-040095 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC37 | TC-040096 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC38 | TC-040097 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported during'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC39 | TC-040098 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC40 | TC-040099 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for EMV Chip Data Component Name: Valu… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC41 | TC-040100 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC42 | TC-040101 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC43 | TC-040102 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC44 | TC-040103 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC45 | TC-040104 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC46 | TC-040105 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation with Sequence Number differen… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS11_TC47 | TC-040106 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation with Terminal Identifier diff… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS11_TC48 | TC-040107 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Encrypted PIN Block Data sent where it is n… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC49 | TC-040108 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Information Byte sent where it is not allow… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC50 | TC-040109 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Payment Tender Type sent where it is not al… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC51 | TC-040110 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC52 | TC-040111 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC53 | TC-040112 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC54 | TC-040113 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC55 | TC-040114 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC56 | TC-040115 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC57 | TC-040116 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC58 | TC-040117 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC59 | TC-040118 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC60 | TC-040119 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC61 | TC-040120 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC62 | TC-040121 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC63 | TC-040122 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS11_TC64 | TC-040123 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC65 | TC-040124 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC66 | TC-040125 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC67 | TC-040126 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC68 | TC-040127 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC69 | TC-040128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional EMV Request Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC70 | TC-040129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC71 | TC-040130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC72 | TC-040131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC73 | TC-040132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC74 | TC-040133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS11_TC75 | TC-040134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC76 | TC-040135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS11_TC77 | TC-040136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation after 25 hours (beyond the 24… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC78 | TC-040137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS11_TC79 | TC-040138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS11_TC80 | TC-040139 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contact Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC81 | TC-040140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC82 | TC-040141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC83 | TC-040142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC84 | TC-040143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC85 | TC-040144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC86 | TC-040145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS11_TC87 | TC-040146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC88 | TC-040147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC89 | TC-040148 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC90 | TC-040149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC91 | TC-040150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'EMV POS PINless is only supported in a'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC92 | TC-040151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC93 | TC-040152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC94 | TC-040153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC95 | TC-040154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC96 | TC-040155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC97 | TC-040156 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Real Time Account Updater Response Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC98 | TC-040157 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC99 | TC-040158 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC100 | TC-040159 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC101 | TC-040160 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC102 | TC-040161 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC103 | TC-040162 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC104 | TC-040163 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Link Identifier, Transaction Link Action Indicat… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC105 | TC-040164 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS11_TC106 | TC-040165 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC107 | TC-040166 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Program Identifier Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC108 | TC-040167 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Suspected Fraud Reversal Indicator Information, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC109 | TC-040168 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Card Acceptor Terminal Identifier Information, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC110 | TC-040169 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Name Verification Service (NVS) Information, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC111 | TC-040170 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC112 | TC-040171 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC113 | TC-040172 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Soft Descriptor - Merchant Name, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC114 | TC-040173 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Soft Descriptor - Merchant Country, response table check… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC115 | TC-040174 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Real Time Account Updater Request Data, response table c… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC116 | TC-040175 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Customer Additional Information Data, response table che… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC117 | TC-040176 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC118 | TC-040177 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC119 | TC-040178 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-038594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only Product Code one byte over its documented maximum length… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS12_TC2 | TC-040179 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC3 | TC-040180 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC4 | TC-040181 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC5 | TC-040182 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC6 | TC-040183 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC7 | TC-040184 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC8 | TC-040185 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC9 | TC-040186 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC10 | TC-040187 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC11 | TC-040188 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC12 | TC-040189 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC13 | TC-040190 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC14 | TC-040191 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC15 | TC-040192 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC16 | TC-040193 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC17 | TC-040194 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC18 | TC-040195 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC19 | TC-040196 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC20 | TC-040197 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC21 | TC-040198 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC22 | TC-040199 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC23 | TC-040200 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC24 | TC-040201 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC25 | TC-040202 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC26 | TC-040203 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC27 | TC-040204 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC28 | TC-040205 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC29 | TC-040206 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC30 | TC-040207 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC31 | TC-040208 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC32 | TC-040209 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC33 | TC-040210 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC34 | TC-040211 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported during'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC35 | TC-040212 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC36 | TC-040213 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for EMV Chip Data Component Name:… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC37 | TC-040214 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC38 | TC-040215 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC39 | TC-040216 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC40 | TC-040217 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC41 | TC-040218 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC42 | TC-040219 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation with Sequence Number diff… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS12_TC43 | TC-040220 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation with Terminal Identifier… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS12_TC44 | TC-040221 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Encrypted PIN Block Data sent where it… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC45 | TC-040222 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Information Byte sent where it is not a… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC46 | TC-040223 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Payment Tender Type sent where it is no… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC47 | TC-040224 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Service Level sent where it is not allo… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC48 | TC-040225 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC49 | TC-040226 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC50 | TC-040227 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC51 | TC-040228 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC52 | TC-040229 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC53 | TC-040230 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC54 | TC-040231 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC55 | TC-040232 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC56 | TC-040233 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC57 | TC-040234 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC58 | TC-040235 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC59 | TC-040236 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS12_TC60 | TC-040237 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC61 | TC-040238 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC62 | TC-040239 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC63 | TC-040240 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC64 | TC-040241 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC65 | TC-040242 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional EMV Request Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC66 | TC-040243 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC67 | TC-040244 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC68 | TC-040245 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC69 | TC-040246 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC70 | TC-040247 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS12_TC71 | TC-040248 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Authorization Only (first card partly approved, bal… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC72 | TC-040249 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS12_TC73 | TC-040250 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation after 25 hours (beyond th… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC74 | TC-040251 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include i… → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS12_TC75 | TC-040252 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS12_TC76 | TC-040253 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contactless Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC77 | TC-040254 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC78 | TC-040255 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC79 | TC-040256 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC80 | TC-040257 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information retur… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC81 | TC-040258 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC82 | TC-040259 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS12_TC83 | TC-040260 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC84 | TC-040261 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC85 | TC-040262 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC86 | TC-040263 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC87 | TC-040264 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'EMV POS PINless is only supported in a'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC88 | TC-040265 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC89 | TC-040266 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC90 | TC-040267 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC91 | TC-040268 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC92 | TC-040269 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC93 | TC-040270 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Real Time Account Updater Response Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC94 | TC-040271 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC95 | TC-040272 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC96 | TC-040273 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC97 | TC-040274 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC98 | TC-040275 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC99 | TC-040276 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC100 | TC-040277 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Link Identifier, Transaction Link Action Ind… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC101 | TC-040278 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS12_TC102 | TC-040279 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC103 | TC-040280 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Program Identifier Information, response table check… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC104 | TC-040281 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Suspected Fraud Reversal Indicator Information, resp… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC105 | TC-040282 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Card Acceptor Terminal Identifier Information, respo… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC106 | TC-040283 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Name Verification Service (NVS) Information, respons… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC107 | TC-040284 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC108 | TC-040285 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC109 | TC-040286 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Soft Descriptor - Merchant Name, response table chec… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC110 | TC-040287 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Soft Descriptor - Merchant Country, response table c… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC111 | TC-040288 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Real Time Account Updater Request Data, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC112 | TC-040289 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Customer Additional Information Data, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC113 | TC-040290 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC114 | TC-040291 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC115 | TC-040292 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS13 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-038639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only EMV Request Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS13_TC2 | TC-040293 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-040294 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC4 | TC-040295 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC5 | TC-040296 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC6 | TC-040297 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC7 | TC-040298 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC8 | TC-040299 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC9 | TC-040300 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC10 | TC-040301 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC11 | TC-040302 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC12 | TC-040303 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC13 | TC-040304 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC14 | TC-040305 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC15 | TC-040306 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC16 | TC-040307 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC17 | TC-040308 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC18 | TC-040309 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC19 | TC-040310 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC20 | TC-040311 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC21 | TC-040312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC22 | TC-040313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC23 | TC-040314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC24 | TC-040315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC25 | TC-040316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC26 | TC-040317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC27 | TC-040318 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC28 | TC-040319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC29 | TC-040320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC30 | TC-040321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC31 | TC-040322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Access Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC32 | TC-040323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC33 | TC-040324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC34 | TC-040325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC35 | TC-040326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC36 | TC-040327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Chip Data Component Name: Value… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC37 | TC-040328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Chip Data Length → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC38 | TC-040329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Request Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC39 | TC-040330 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC40 | TC-040331 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC41 | TC-040332 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC42 | TC-040333 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC43 | TC-040334 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC44 | TC-040335 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation with Sequence Number different… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS13_TC45 | TC-040336 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation with Terminal Identifier diffe… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS13_TC46 | TC-040337 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent where it is no… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC47 | TC-040338 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Information Byte sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC48 | TC-040339 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Payment Tender Type sent where it is not all… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC49 | TC-040340 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC50 | TC-040341 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC51 | TC-040342 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC52 | TC-040343 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC53 | TC-040344 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC54 | TC-040345 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC55 | TC-040346 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC56 | TC-040347 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC57 | TC-040348 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC58 | TC-040349 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC59 | TC-040350 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC60 | TC-040351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC61 | TC-040352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS13_TC62 | TC-040353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC63 | TC-040354 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC64 | TC-040355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC65 | TC-040356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC66 | TC-040357 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC67 | TC-040358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional NFC Payment Tokenization Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC68 | TC-040359 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC69 | TC-040360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC70 | TC-040361 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC71 | TC-040362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC72 | TC-040363 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Chip Transaction Report should show both data element'…] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC73 | TC-040364 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'This field should only be sent for First'…] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC74 | TC-040365 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'mPOS Devices Phone/Tablet + Dongle with PIN Pad'…] | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC75 | TC-040366 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Load Type | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC76 | TC-040367 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Pause Indicator | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC77 | TC-040368 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS13_TC78 | TC-040369 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC79 | TC-040370 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS13_TC80 | TC-040371 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation after 25 hours (beyond the 24… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC81 | TC-040372 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS13_TC82 | TC-040373 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS13_TC83 | TC-040374 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for Proprietary Data Load Segment | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC84 | TC-040375 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Cancellations of non-gift cards must subtract from the'… | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC85 | TC-040376 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'ICC Offline Accumulator Balance 2 is retrieved by'… | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC86 | TC-040377 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Offline Accumulator Balance 1 retrieved by GET DATA'… | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC87 | TC-040378 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Expiration Date | ordering | - | n/a (no host message) | requires_review |
| BR1_TS13_TC88 | TC-040379 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for TransArmor Load Data Segment | ordering | - | n/a (no host message) | requires_review |
| BR1_TS13_TC89 | TC-040380 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for 'For TA-VE, EDATA is processor code, colon, Encrypted'… | ordering | - | n/a (no host message) | requires_review |
| BR1_TS13_TC90 | TC-040381 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: co-requirement / mutual exclusion for Sub Table ID "01" — Purpose: Identifies a deferred transa… | co_requirement | - | n/a (no host message) | requires_review |
| BR1_TS13_TC91 | TC-040382 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: co-requirement / mutual exclusion for POS Additional Information Indicator | co_requirement | - | n/a (no host message) | requires_review |
| BR1_TS13_TC92 | TC-040383 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Card Label | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC93 | TC-040384 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Dial Backup and Diverse Facilities | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC94 | TC-040385 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Sub Table ID "13" | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC95 | TC-040386 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for PINless Debit Information | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC96 | TC-040387 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Encrypted PIN Block Data | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC97 | TC-040388 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Network Management Message | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC98 | TC-040389 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Response Code | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC99 | TC-040390 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Standard Message Data Segment | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC100 | TC-040391 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for BUYPASS Card Type Code | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC101 | TC-040392 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for 'TransArmor VeriFone Edition (FPE) leaves first 6 and'… | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC102 | TC-040393 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for 'For routing purposes, only the RID portion of'… | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC103 | TC-040394 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for 'If sent as Dual Message, Fiserv can route'… | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC104 | TC-040395 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Magnetic Stripe Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC105 | TC-040396 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC106 | TC-040397 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC107 | TC-040398 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC108 | TC-040399 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC109 | TC-040400 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC110 | TC-040401 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS13_TC111 | TC-040402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC112 | TC-040403 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC113 | TC-040404 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC114 | TC-040405 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC115 | TC-040406 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC116 | TC-040407 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC117 | TC-040408 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC118 | TC-040409 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC119 | TC-040410 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Real Time Account Updater Response Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC120 | TC-040411 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC121 | TC-040412 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC122 | TC-040413 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC123 | TC-040414 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC124 | TC-040415 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC125 | TC-040416 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC126 | TC-040417 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Link Identifier, Transaction Link Action Indicato… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC127 | TC-040418 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS13_TC128 | TC-040419 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC129 | TC-040420 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Program Identifier Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC130 | TC-040421 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Suspected Fraud Reversal Indicator Information, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC131 | TC-040422 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Card Acceptor Terminal Identifier Information, response t… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC132 | TC-040423 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Name Verification Service (NVS) Information, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC133 | TC-040424 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC134 | TC-040425 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC135 | TC-040426 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Soft Descriptor - Merchant Name, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC136 | TC-040427 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Soft Descriptor - Merchant Country, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC137 | TC-040428 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Real Time Account Updater Request Data, response table ch… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC138 | TC-040429 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Customer Additional Information Data, response table chec… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC139 | TC-040430 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC140 | TC-040431 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC141 | TC-040432 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC142 | TC-040433 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: CAPK lifecycle within 6 months (terminal certification) [about 'Do not disable or delete CAPK until new'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC143 | TC-040434 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: CAPK lifecycle within 6 months (terminal certification) [about 'New CAPK updates must be implemented within six'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC144 | TC-040435 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Authorization Mode must be printed: 'Issuer' for online/Stand-In'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC145 | TC-040436 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'RQ 6600: Offline Decline receipt data must be'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC146 | TC-040437 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Contactless CVM Limit varies by Brand/AID'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC147 | TC-040438 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Transaction Amount must be printed on the total'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC148 | TC-040439 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Debit Prompt value 0 means do not prompt'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC149 | TC-040440 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'BUYPASS Card Type appears when Prompt Code 904'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC150 | TC-040441 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'For non-TransArmor and TransArmor Token Only, the POS'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC151 | TC-040442 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Implementation Phase complete only when card logs, trace'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC152 | TC-040443 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'In Proprietary Load Response, Product'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC153 | TC-040444 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Terminal Display/Printer Message can repeat up to three'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC154 | TC-040445 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Product Discount Amount appears in Proprietary Data Load'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC155 | TC-040446 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Issuer may decline transactions for consumers with a'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC156 | TC-040447 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'If cardholder does not accept partial amount, prompt'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC157 | TC-040448 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Device should prompt user to swipe card up'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC158 | TC-040449 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Debit Card Type ID prints as DB'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC159 | TC-040450 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 12 terminal behaviour checks [about 'Mandates to print or not print receipts have'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC160 | TC-040451 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'A bit is set on the merchant profile'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC161 | TC-040452 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'If manual entry is used, this fact is'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC162 | TC-040453 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'Element No. 101, Terminal Display/Printer Message, can be'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC163 | TC-040454 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'Downloads must be capable of being requested by'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC164 | TC-040455 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 2 terminal behaviour checks [about 'For some Decline Code values, a device can'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC165 | TC-040456 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 3 terminal behaviour checks-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC166 | TC-040457 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 3 terminal behaviour checks [about 'Card Type codes are also used to construct'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC167 | TC-040458 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 3 terminal behaviour checks [about 'An electronic journal is not provided by BUYPASS'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC168 | TC-040459 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 3 terminal behaviour checks [about 'A Master ID is comprised of specific versions'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC169 | TC-040460 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 4 terminal behaviour checks [about 'Print Data 2 Segment is only sent on'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC170 | TC-040461 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 4 terminal behaviour checks [about 'Discount Quantity Limit appears in Proprietary Load Response'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC171 | TC-040462 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 5 terminal behaviour checks [about 'Print Data Segment is only sent on Financial'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC172 | TC-040463 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 5 terminal behaviour checks [about 'Local totals are calculated from electronic journal entries'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC173 | TC-040464 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 5 terminal behaviour checks [about 'Decline message must be on receipt for a'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC174 | TC-040465 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 6 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC175 | TC-040466 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 7 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC176 | TC-040467 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 7 terminal behaviour checks-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC177 | TC-040468 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 9 terminal behaviour checks [about 'If card lacks Application Preferred Name or terminal'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC178 | TC-040469 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: 9 terminal behaviour checks [about 'PROMPT CODE is the link from the RULES'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC179 | TC-040470 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: CA Public Key File Block | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC180 | TC-040471 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Card Table Master | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC181 | TC-040472 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Card Type | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC182 | TC-040473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Host Prompts Information (cont.) | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC183 | TC-040474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Encrypted PIN Block Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC184 | TC-040475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Enhanced Fleet Request | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC185 | TC-040476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Key ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC186 | TC-040477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display /Printer Message [about 'Terminal Display/Printer Message data element can be repeated'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC187 | TC-040478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display /Printer Message [about 'TransArmor Financial Transaction Response requires Download Indicator, Account'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC188 | TC-040479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display Communications Message [about 'COMM_TEST_PASSED is the Terminal Display Communications Message in'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC189 | TC-040480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display Communications Message [about 'Terminal Display Communications Message is conditional, displaying COMM_TEST_PASSED'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC190 | TC-040481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Token PAN Suffix [about 'Token PAN Suffix identifies the 4 digits of'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC191 | TC-040482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Token PAN Suffix [about 'POS device must send PAN in clear during'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC192 | TC-040483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Vehicle ID# | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC193 | TC-040484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 'Device must support manual and automatic download requests'… | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC194 | TC-040485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device checkpoint for Sub Table ID "01" | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC195 | TC-040486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device checkpoint for TransArmor Load Data Segment | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC196 | TC-040487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: 12 checks [about 'US Territories or other USD processing locations not'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC197 | TC-040488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: 12 checks [about 'Depending on severity/type of failure, another Card Brand'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC198 | TC-040489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: 'Regression testing for MSR functionality can begin once'… | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC199 | TC-040490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: 'US Territories or other USD processing locations not'… | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC200 | TC-040491 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: CA Public Key File Data Segment | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC201 | TC-040492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Merchant Trace ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC202 | TC-040493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Real Time Account Updater Request Data | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC203 | TC-040494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Token Requestor ID | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC204 | TC-040495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Mail/Phone Reversal (not supported for this card) → declined (to confirm) | single_rule_broken | C | 1: 1 (FLAG) | ready |
| BR1_TS13_TC205 | TC-040496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Account Verification (not supported for this card) → declined (to confirm) | single_rule_broken | A | 1: 1 (FLAG) | ready |

### BR1_TS14 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-040497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC2 | TC-040498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC3 | TC-040499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC4 | TC-040500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC5 | TC-040501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC6 | TC-040502 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC7 | TC-040503 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC8 | TC-040504 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC9 | TC-040505 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC10 | TC-040506 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC11 | TC-040507 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC12 | TC-040508 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC13 | TC-040509 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC14 | TC-040510 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC15 | TC-040511 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC16 | TC-040512 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC17 | TC-040513 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC18 | TC-040514 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC19 | TC-040515 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC20 | TC-040516 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC21 | TC-040517 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC22 | TC-040518 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC23 | TC-040519 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC24 | TC-040520 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC25 | TC-040521 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC26 | TC-040522 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC27 | TC-040523 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC28 | TC-040524 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC29 | TC-040525 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC30 | TC-040526 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC31 | TC-040527 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC32 | TC-040528 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC33 | TC-040529 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC34 | TC-040530 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for EMV Chip Data Component… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC35 | TC-040531 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC36 | TC-040532 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC37 | TC-040533 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC38 | TC-040534 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC39 | TC-040535 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC40 | TC-040536 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation with Sequence Numb… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS14_TC41 | TC-040537 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation with Terminal Iden… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS14_TC42 | TC-040538 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent wh… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC43 | TC-040539 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Information Byte sent where it i… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC44 | TC-040540 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Payment Tender Type sent where i… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC45 | TC-040541 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Service Level sent where it is n… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC46 | TC-040542 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC47 | TC-040543 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC48 | TC-040544 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC49 | TC-040545 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC50 | TC-040546 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC51 | TC-040547 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC52 | TC-040548 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC53 | TC-040549 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC54 | TC-040550 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC55 | TC-040551 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC56 | TC-040552 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC57 | TC-040553 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS14_TC58 | TC-040554 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC59 | TC-040555 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC60 | TC-040556 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC61 | TC-040557 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC62 | TC-040558 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC63 | TC-040559 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional NFC Payment Tokenization Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC64 | TC-040560 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC65 | TC-040561 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC66 | TC-040562 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC67 | TC-040563 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC68 | TC-040564 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS14_TC69 | TC-040565 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Authorization Only (first card partly approv… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC70 | TC-040566 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation after 24 hours (at the 24 hour… → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS14_TC71 | TC-040567 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation after 25 hours (be… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC72 | TC-040568 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals in… → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS14_TC73 | TC-040569 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS14_TC74 | TC-040570 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Magnetic Stripe Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC75 | TC-040571 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC76 | TC-040572 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC77 | TC-040573 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC78 | TC-040574 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (BUYPASS Card Type Code, PIN on Receipt Informatio… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC79 | TC-040575 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC80 | TC-040576 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS14_TC81 | TC-040577 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Cardholder Additional Information Result Code ret… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC82 | TC-040578 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC83 | TC-040579 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC84 | TC-040580 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC85 | TC-040581 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC86 | TC-040582 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC87 | TC-040583 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC88 | TC-040584 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC89 | TC-040585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Real Time Account Updater Response Segment return… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC90 | TC-040586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC91 | TC-040587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC92 | TC-040588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC93 | TC-040589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC94 | TC-040590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC95 | TC-040591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC96 | TC-040592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Link Identifier, Transaction Link Act… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC97 | TC-040593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS14_TC98 | TC-040594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC99 | TC-040595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Program Identifier Information, response tabl… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC100 | TC-040596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Suspected Fraud Reversal Indicator Informatio… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC101 | TC-040597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Card Acceptor Terminal Identifier Information… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC102 | TC-040598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Name Verification Service (NVS) Information,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC103 | TC-040599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC104 | TC-040600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC105 | TC-040601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Soft Descriptor - Merchant Name, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC106 | TC-040602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Soft Descriptor - Merchant Country, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC107 | TC-040603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Real Time Account Updater Request Data, respo… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC108 | TC-040604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Customer Additional Information Data, respons… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC109 | TC-040605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC110 | TC-040606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC111 | TC-040607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS15 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-038607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only Loyalty Card Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS15_TC2 | TC-040608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC3 | TC-040609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC4 | TC-040610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC5 | TC-040611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC6 | TC-040612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC7 | TC-040613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC8 | TC-040614 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC9 | TC-040615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC10 | TC-040616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC11 | TC-040617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC12 | TC-040618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC13 | TC-040619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC14 | TC-040620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC15 | TC-040621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC16 | TC-040622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC17 | TC-040623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC18 | TC-040624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC19 | TC-040625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC20 | TC-040626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC21 | TC-040627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC22 | TC-040628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC23 | TC-040629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC24 | TC-040630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC25 | TC-040631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC26 | TC-040632 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC27 | TC-040633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC28 | TC-040634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC29 | TC-040635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC30 | TC-040636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC31 | TC-040637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC32 | TC-040638 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC33 | TC-040639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC34 | TC-040640 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC35 | TC-040641 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for EMV Chip Data Component Name: Value… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC36 | TC-040642 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC37 | TC-040643 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC38 | TC-040644 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC39 | TC-040645 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC40 | TC-040646 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC41 | TC-040647 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation with Sequence Number different… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS15_TC42 | TC-040648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation with Terminal Identifier differ… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS15_TC43 | TC-040649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Encrypted PIN Block Data sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC44 | TC-040650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Information Byte sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC45 | TC-040651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Payment Tender Type sent where it is not allo… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC46 | TC-040652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC47 | TC-040653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC48 | TC-040654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC49 | TC-040655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC50 | TC-040656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC51 | TC-040657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC52 | TC-040658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC53 | TC-040659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC54 | TC-040660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC55 | TC-040661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC56 | TC-040662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC57 | TC-040663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC58 | TC-040664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS15_TC59 | TC-040665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC60 | TC-040666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC61 | TC-040667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC62 | TC-040668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC63 | TC-040669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC64 | TC-040670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional NFC Payment Tokenization Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC65 | TC-040671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC66 | TC-040672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC67 | TC-040673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Loyalty Card Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC68 | TC-040674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC69 | TC-040675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC70 | TC-040676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS15_TC71 | TC-040677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Authorization Only (first card partly approved, balance o… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC72 | TC-040678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS15_TC73 | TC-040679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation after 25 hours (beyond the 24 h… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC74 | TC-040680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS15_TC75 | TC-040681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS15_TC76 | TC-040682 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Manual / Keyed Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC77 | TC-040683 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC78 | TC-040684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Account Type returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC79 | TC-040685 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC80 | TC-040686 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC81 | TC-040687 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC82 | TC-040688 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC83 | TC-040689 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS15_TC84 | TC-040690 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC85 | TC-040691 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC86 | TC-040692 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC87 | TC-040693 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC88 | TC-040694 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC89 | TC-040695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC90 | TC-040696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Merchant Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC91 | TC-040697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC92 | TC-040698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC93 | TC-040699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Real Time Account Updater Response Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC94 | TC-040700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC95 | TC-040701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC96 | TC-040702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC97 | TC-040703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC98 | TC-040704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC99 | TC-040705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC100 | TC-040706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Link Identifier, Transaction Link Action Indicator… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC101 | TC-040707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS15_TC102 | TC-040708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC103 | TC-040709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Program Identifier Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC104 | TC-040710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Suspected Fraud Reversal Indicator Information, response t… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC105 | TC-040711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Card Acceptor Terminal Identifier Information, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC106 | TC-040712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Name Verification Service (NVS) Information, response tabl… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC107 | TC-040713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC108 | TC-040714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC109 | TC-040715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Soft Descriptor - Merchant Name, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC110 | TC-040716 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Soft Descriptor - Merchant Country, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC111 | TC-040717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Real Time Account Updater Request Data, response table che… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC112 | TC-040718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Customer Additional Information Data, response table check… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC113 | TC-040719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC114 | TC-040720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC115 | TC-040721 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS16 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-040722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC2 | TC-040723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC3 | TC-040724 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC4 | TC-040725 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC5 | TC-040726 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC6 | TC-040727 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC7 | TC-040728 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC8 | TC-040729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC9 | TC-040730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC10 | TC-040731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC11 | TC-040732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC12 | TC-040733 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC13 | TC-040734 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC14 | TC-040735 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC15 | TC-040736 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC16 | TC-040737 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC17 | TC-040738 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC18 | TC-040739 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC19 | TC-040740 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC20 | TC-040741 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC21 | TC-040742 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC22 | TC-040743 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC23 | TC-040744 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC24 | TC-040745 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC25 | TC-040746 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC26 | TC-040747 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC27 | TC-040748 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC28 | TC-040749 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC29 | TC-040750 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC30 | TC-040751 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC31 | TC-040752 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC32 | TC-040753 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC33 | TC-040754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Card Sequence Number → approved [about 'Card insert and tap should be supported during'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC34 | TC-040755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Card Sequence Number → approved [about 'Cancel Fallback if an EMV card is inserted'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC35 | TC-040756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Chip Data Component Name: Value (… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC36 | TC-040757 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Chip Data Length → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC37 | TC-040758 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Request Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC38 | TC-040759 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC39 | TC-040760 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC40 | TC-040761 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC41 | TC-040762 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Network Management Message → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC42 | TC-040763 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC43 | TC-040764 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC44 | TC-040765 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation with Sequence Number different f… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS16_TC45 | TC-040766 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation with Terminal Identifier differe… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS16_TC46 | TC-040767 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Encrypted PIN Block Data sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC47 | TC-040768 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Information Byte sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC48 | TC-040769 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Payment Tender Type sent where it is not allow… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC49 | TC-040770 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC50 | TC-040771 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC51 | TC-040772 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC52 | TC-040773 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC53 | TC-040774 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC54 | TC-040775 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC55 | TC-040776 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC56 | TC-040777 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC57 | TC-040778 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC58 | TC-040779 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC59 | TC-040780 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC60 | TC-040781 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC61 | TC-040782 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS16_TC62 | TC-040783 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC63 | TC-040784 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC64 | TC-040785 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC65 | TC-040786 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC66 | TC-040787 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC67 | TC-040788 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional NFC Payment Tokenization Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC68 | TC-040789 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC69 | TC-040790 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC70 | TC-040791 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC71 | TC-040792 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC72 | TC-040793 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS16_TC73 | TC-040794 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Authorization Only (first card partly approved, balance on… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC74 | TC-040795 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS16_TC75 | TC-040796 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation after 25 hours (beyond the 24 ho… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC76 | TC-040797 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS16_TC77 | TC-040798 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS16_TC78 | TC-040799 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Chip Fallback Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC79 | TC-040800 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC80 | TC-040801 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC81 | TC-040802 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC82 | TC-040803 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC83 | TC-040804 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC84 | TC-040805 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS16_TC85 | TC-040806 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC86 | TC-040807 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC87 | TC-040808 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC88 | TC-040809 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC89 | TC-040810 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC90 | TC-040811 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC91 | TC-040812 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC92 | TC-040813 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC93 | TC-040814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Real Time Account Updater Response Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC94 | TC-040815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC95 | TC-040816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC96 | TC-040817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC97 | TC-040818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC98 | TC-040819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC99 | TC-040820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC100 | TC-040821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Link Identifier, Transaction Link Action Indicator… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC101 | TC-040822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS16_TC102 | TC-040823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC103 | TC-040824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Program Identifier Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC104 | TC-040825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Suspected Fraud Reversal Indicator Information, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC105 | TC-040826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Card Acceptor Terminal Identifier Information, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC106 | TC-040827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Name Verification Service (NVS) Information, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC107 | TC-040828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC108 | TC-040829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC109 | TC-040830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Soft Descriptor - Merchant Name, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC110 | TC-040831 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Soft Descriptor - Merchant Country, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC111 | TC-040832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Real Time Account Updater Request Data, response table chec… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC112 | TC-040833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Customer Additional Information Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC113 | TC-040834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC114 | TC-040835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC115 | TC-040836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS17 BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-038600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only CAVV Revised Format contains a character outside its charac… → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS17_TC2 | TC-038597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only NFC Payment Tokenization Data Segment omitted where require… → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS17_TC3 | TC-038605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only Purchase Card Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS17_TC4 | TC-040837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC5 | TC-040838 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Point-of-Service Entry Mode Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC6 | TC-040839 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC7 | TC-040840 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC8 | TC-040841 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Partial Approval Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC9 | TC-040842 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Market-Specific Data Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC10 | TC-040843 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with User Data Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC11 | TC-040844 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC12 | TC-040845 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC13 | TC-040846 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC14 | TC-040847 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC15 | TC-040848 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC16 | TC-040849 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC17 | TC-040850 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC18 | TC-040851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC19 | TC-040852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC20 | TC-040853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC21 | TC-040854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC22 | TC-040855 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC23 | TC-040856 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC24 | TC-040857 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC25 | TC-040858 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC26 | TC-040859 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC27 | TC-040860 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC28 | TC-040861 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC29 | TC-040862 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC30 | TC-040863 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Soft Descriptor - Merchant City → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC31 | TC-040864 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Soft Descriptor - Merchant State → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC32 | TC-040865 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC33 | TC-040866 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC34 | TC-040867 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC35 | TC-040868 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC36 | TC-040869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC37 | TC-040870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC38 | TC-040871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC39 | TC-040872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC40 | TC-040873 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC41 | TC-040874 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC42 | TC-040875 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC43 | TC-040876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for EMV Chip Data Component Name: Va… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC44 | TC-040877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC45 | TC-040878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC46 | TC-040879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC47 | TC-040880 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC48 | TC-040881 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC49 | TC-040882 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation with Sequence Number differ… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS17_TC50 | TC-040883 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation with Terminal Identifier di… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS17_TC51 | TC-040884 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Encrypted PIN Block Data sent where it is… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC52 | TC-040885 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Information Byte sent where it is not all… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC53 | TC-040886 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Payment Tender Type sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC54 | TC-040887 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC55 | TC-040888 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC56 | TC-040889 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC57 | TC-040890 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC58 | TC-040891 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC59 | TC-040892 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC60 | TC-040893 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC61 | TC-040894 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC62 | TC-040895 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC63 | TC-040896 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC64 | TC-040897 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC65 | TC-040898 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS17_TC66 | TC-040899 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC67 | TC-040900 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC68 | TC-040901 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC69 | TC-040902 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with initiation = M → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC70 | TC-040903 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC71 | TC-040904 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC72 | TC-040905 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional NFC Payment Tokenization Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC73 | TC-040906 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC74 | TC-040907 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC75 | TC-040908 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC76 | TC-040909 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with special payment = I → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC77 | TC-040910 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with special payment = R → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC78 | TC-040911 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC79 | TC-040912 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS17_TC80 | TC-040913 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Authorization Only (first card partly approved, balan… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC81 | TC-040914 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation after 72 hours (at the 72 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS17_TC82 | TC-040915 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation after 73 hours (beyond the… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC83 | TC-040916 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS17_TC84 | TC-040917 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS17_TC85 | TC-040918 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Credential on File Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC86 | TC-040919 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC87 | TC-040920 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC88 | TC-040921 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Additional Information Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC89 | TC-040922 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returne… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC90 | TC-040923 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC91 | TC-040924 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS17_TC92 | TC-040925 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC93 | TC-040926 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC94 | TC-040927 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC95 | TC-040928 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC96 | TC-040929 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC97 | TC-040930 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC98 | TC-040931 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Merchant Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC99 | TC-040932 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC100 | TC-040933 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC101 | TC-040934 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Real Time Account Updater Response Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC102 | TC-040935 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC103 | TC-040936 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC104 | TC-040937 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC105 | TC-040938 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC106 | TC-040939 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC107 | TC-040940 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Attributes Segment returned) → approved [about 'When initiating MIT recurring transaction, transaction must be'…] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC108 | TC-040941 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC109 | TC-040942 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Link Identifier, Transaction Link Action Indic… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC110 | TC-040943 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS17_TC111 | TC-040944 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC112 | TC-040945 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Program Identifier Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC113 | TC-040946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Suspected Fraud Reversal Indicator Information, respon… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC114 | TC-040947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Card Acceptor Terminal Identifier Information, respons… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC115 | TC-040948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Settlement Data Acceptance Flag, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC116 | TC-040949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Name Verification Service (NVS) Information, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC117 | TC-040950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC118 | TC-040951 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC119 | TC-040952 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Soft Descriptor - Merchant Name, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC120 | TC-040953 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Soft Descriptor - Merchant Country, response table che… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC121 | TC-040954 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Real Time Account Updater Request Data, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC122 | TC-040955 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Customer Additional Information Data, response table c… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC123 | TC-040956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC124 | TC-040957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC125 | TC-040958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-040959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-040960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC3 | TC-040961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC4 | TC-040962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC5 | TC-040963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Originating Device Type Indicator Info… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC6 | TC-040964 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC7 | TC-040965 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC8 | TC-040966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Point-of-Service Terminal Capability C… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC9 | TC-040967 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC10 | TC-040968 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC11 | TC-040969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC12 | TC-040970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC13 | TC-040971 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC14 | TC-040972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC15 | TC-040973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC16 | TC-040974 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC17 | TC-040975 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC18 | TC-040976 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC19 | TC-040977 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC20 | TC-040978 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC21 | TC-040979 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC22 | TC-040980 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC23 | TC-040981 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC24 | TC-040982 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC25 | TC-040983 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC26 | TC-040984 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC27 | TC-040985 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC28 | TC-040986 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC29 | TC-040987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC30 | TC-040988 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC31 | TC-040989 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC32 | TC-040990 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Download Indicat… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC33 | TC-040991 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for EMV Card Sequenc… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC34 | TC-040992 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for EMV Chip Data Co… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC35 | TC-040993 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'Field is optional with value 'Y' if merchant'…] | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC36 | TC-040994 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Sub Table ID "13" → approved [about 'If transaction must be debit only and not'…] | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC37 | TC-040995 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Sub Table ID "23" → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC38 | TC-040996 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC39 | TC-040997 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for POS Condition Co… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC40 | TC-040998 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation with Sequen… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC41 | TC-040999 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation with Termin… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC42 | TC-041000 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC43 | TC-041001 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Information Byte sent whe… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC44 | TC-041002 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Payment Tender Type sent… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC45 | TC-041003 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Service Level sent … → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC46 | TC-041004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC47 | TC-041005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC48 | TC-041006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC49 | TC-041007 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC50 | TC-041008 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC51 | TC-041009 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC52 | TC-041010 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC53 | TC-041011 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC54 | TC-041012 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC55 | TC-041013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC56 | TC-041014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC57 | TC-041015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | pending_sme |
| BR1_TS18_TC58 | TC-041016 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC59 | TC-041017 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC60 | TC-041018 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC61 | TC-041019 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Standard Message Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC62 | TC-041020 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Purchase Card Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC63 | TC-041021 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Enhanced Fleet Request → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC64 | TC-041022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Network Token Data Request → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC65 | TC-041023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Variable Information Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC66 | TC-041024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC67 | TC-041025 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS18_TC68 | TC-041026 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Authorization Only (first card partly… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC69 | TC-041027 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation after 24 hours (at the 2… → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS18_TC70 | TC-041028 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation after 25 ho… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC71 | TC-041029 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation, then Totals Request (to… → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS18_TC72 | TC-041030 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS18_TC73 | TC-041031 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC74 | TC-041032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC75 | TC-041033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Additional Information Data Layouts return… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC76 | TC-041034 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Additional Information Data Segment return… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC77 | TC-041035 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (BUYPASS Card Type Code, PIN on Receipt Inf… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC78 | TC-041036 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC79 | TC-041037 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC80 | TC-041038 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Cardholder Additional Information Result C… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC81 | TC-041039 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Date and Time Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC82 | TC-041040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Dial String Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC83 | TC-041041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC84 | TC-041042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC85 | TC-041043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Merchant Advice Code, DAF Indicator return… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC86 | TC-041044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Network Token Data Response returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC87 | TC-041045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC88 | TC-041046 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Real Time Account Updater Response Segment… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC89 | TC-041047 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Software Dial Load Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC90 | TC-041048 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC91 | TC-041049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC92 | TC-041050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC93 | TC-041051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC94 | TC-041052 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC95 | TC-041053 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Link Identifier, Transaction L… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC96 | TC-041054 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC97 | TC-041055 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC98 | TC-041056 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Program Identifier Information, respon… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC99 | TC-041057 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Suspected Fraud Reversal Indicator Inf… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC100 | TC-041058 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Card Acceptor Terminal Identifier Info… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC101 | TC-041059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Name Verification Service (NVS) Inform… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC102 | TC-041060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with MasterCard UCAF Data, response table c… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC103 | TC-041061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Paydiant Tender ID, response table che… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC104 | TC-041062 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Soft Descriptor - Merchant Name, respo… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC105 | TC-041063 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Soft Descriptor - Merchant Country, re… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC106 | TC-041064 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Real Time Account Updater Request Data… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC107 | TC-041065 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Customer Additional Information Data,… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC108 | TC-041066 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Digital Commerce Data, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC109 | TC-041067 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Anticipated Amount, response table che… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC110 | TC-041068 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Staged Wallet ID, response table check… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS19 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-041069 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only → approved | happy_path | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC2 | TC-041070 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Sequence Number… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS19_TC3 | TC-041071 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Terminal Identi… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS19_TC4 | TC-041072 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only with Network Token Data Request sent → approved | decision_table | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC5 | TC-041073 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS19_TC6 | TC-041074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals incl… → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS19_TC7 | TC-041075 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS19_TC8 | TC-041076 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for Response Code [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC9 | TC-041077 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code B means Approved-Totals with electronic mail'… [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC10 | TC-041078 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code C means Declined-Totals with electronic mail'… [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC11 | TC-041079 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code 6 means Approved-Totals with electronic mail'… [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC12 | TC-041080 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for 'Transaction Code 7 means Declined-Totals with electronic mail'… [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC13 | TC-041081 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Electronic Mail Data Segment [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | ordering | - | n/a (no host message) | requires_review |
| BR1_TS19_TC14 | TC-041082 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only on a POS terminal with offline pin → approved | pairwise | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC15 | TC-041083 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC16 | TC-041084 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation (card issuer declines) → declined | response_check | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS19_TC17 | TC-041085 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | ready |
| BR1_TS19_TC18 | TC-041086 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Message Format Version Identifier [on Mail/Phone Authorization Only - Manual / Keyed @ Mail Order · mail and/or telephone order] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS19_TC19 | TC-041087 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 05 (EMV Contact Ch… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC20 | TC-041088 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 07 (EMV Contactles… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC21 | TC-041089 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 02 (Magnetic Strip… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC22 | TC-041090 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 91 (Contactless Ma… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC23 | TC-041091 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 79 (Chip Fallback,… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC24 | TC-041092 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 82 (Contactless Mo… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |

### BR1_TS20 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-041093 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only → approved | happy_path | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-041094 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation with Sequence Nu… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC3 | TC-041095 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation with Terminal Id… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC4 | TC-041096 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only with Network Token Data Request sent → approved | decision_table | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC5 | TC-041097 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS20_TC6 | TC-041098 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals… → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS20_TC7 | TC-041099 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS20_TC8 | TC-041100 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only on a POS terminal with offline pin → approved | pairwise | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC9 | TC-041101 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC10 | TC-041102 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation (card issuer declines) → declined | response_check | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC11 | TC-041103 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-038637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return Variable Information Indicator one byte over its documented m… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS21_TC2 | TC-038599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return Required field Product Amount sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS21_TC3 | TC-041104 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS21_TC4 | TC-041105 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Sequence… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC5 | TC-041106 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Terminal… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC6 | TC-041107 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS21_TC7 | TC-041108 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (tota… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS21_TC8 | TC-041109 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS21_TC9 | TC-041110 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS21_TC10 | TC-041111 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | requires_review |
| BR1_TS21_TC11 | TC-041112 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS22 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-041113 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS22_TC2 | TC-041114 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Sequ… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC3 | TC-041115 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Term… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC4 | TC-041116 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS22_TC5 | TC-041117 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS22_TC6 | TC-041118 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS22_TC7 | TC-041119 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS22_TC8 | TC-041120 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | requires_review |
| BR1_TS22_TC9 | TC-041121 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS23 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-041122 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS23_TC2 | TC-041123 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Sequence… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS23_TC3 | TC-041124 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Terminal… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS23_TC4 | TC-041125 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS23_TC5 | TC-041126 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Request (total… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS23_TC6 | TC-041127 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS23_TC7 | TC-041128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS23_TC8 | TC-041129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS23_TC9 | TC-041130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS24 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-041131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS24_TC2 | TC-041132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return wi… → declined (to confirm) [about 'Quantity has maximum of three assumed decimal places'] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS24_TC3 | TC-041133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return wi… → declined (to confirm) [about 'A response received after the 30-second interval expires'…] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS24_TC4 | TC-041134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS24_TC5 | TC-041135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Re… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS24_TC6 | TC-041136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS24_TC7 | TC-041137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS24_TC8 | TC-041138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (card issuer de… → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS24_TC9 | TC-041139 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS25 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-041140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS25_TC2 | TC-041141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Sequence N… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS25_TC3 | TC-041142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Terminal I… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS25_TC4 | TC-041143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS25_TC5 | TC-041144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS25_TC6 | TC-041145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS25_TC7 | TC-041146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS25_TC8 | TC-041147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS25_TC9 | TC-041148 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS26 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-038606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return Token PAN Suffix one byte over its documented maximum length (4) → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS26_TC2 | TC-041149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS26_TC3 | TC-041150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return with Sequence Nu… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS26_TC4 | TC-041151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return with Terminal Id… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS26_TC5 | TC-041152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS26_TC6 | TC-041153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS26_TC7 | TC-041154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS26_TC8 | TC-041155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS26_TC9 | TC-041156 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS26_TC10 | TC-041157 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS27 BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-041158 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return → approved | happy_path | 7 | 1: no response expected | pending_sme |
| BR1_TS27_TC2 | TC-041159 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return with Sequen… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-041160 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return with Termin… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC4 | TC-041161 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS27_TC5 | TC-041162 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return (no response), then Void of a Merchandise Return → approved | fault_recovery | 7 -> U | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS27_TC6 | TC-041163 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return, then Totals Request (to… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS27_TC7 | TC-041164 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS27_TC8 | TC-041165 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | pending_sme |
| BR1_TS27_TC9 | TC-041166 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS27_TC10 | TC-041167 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS28 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-041168 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: no response expected | pending_sme |
| BR1_TS28_TC2 | TC-041169 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Re… → declined (to confirm) [about 'Quantity has maximum of three assumed decimal places'] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-041170 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Re… → declined (to confirm) [about 'A response received after the 30-second interval expires'…] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC4 | TC-041171 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS28_TC5 | TC-041172 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return, then To… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS28_TC6 | TC-041173 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS28_TC7 | TC-041174 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | pending_sme |
| BR1_TS28_TC8 | TC-041175 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return (card is… → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS28_TC9 | TC-041176 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS29 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-038634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 byt… → declined | single_rule_broken | EMV Financial Transaction Request | ? (FLAG) | ready |
| BR1_TS29_TC2 | TC-038629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Balance Inquiry EMV Chip Data Length contains a character outside its character… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS29_TC3 | TC-038633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Balance Inquiry Tax Type 1 value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS29_TC4 | TC-038595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Balance Inquiry required Standard Message Data Segment omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | blocked_no_test_data |
| BR1_TS29_TC5 | TC-041177 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS30 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-038625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Balance Inquiry Update Code one byte over its documented maximum length (1) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS30_TC2 | TC-041178 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS31 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-038601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Balance Inquiry Merchant Trace ID exactly at its documented maximum length (25 by… → declined | single_rule_broken | Financial Transaction Request | ? (FLAG) | ready |
| BR1_TS31_TC2 | TC-041179 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS32 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-041180 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS33 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-041181 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS34 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-041182 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS35 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-041183 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry → approved | happy_path | E | 1: no response expected | pending_sme |
| BR1_TS35_TC2 | TC-041184 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS35_TC3 | TC-041185 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | pending_sme |
| BR1_TS35_TC4 | TC-041186 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS36 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-041187 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | pending_sme |

### BR1_TS37 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS37_TC1 | TC-041797 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS38 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS38_TC1 | TC-041798 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS39 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS39_TC1 | TC-041799 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS40 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS40_TC1 | TC-041800 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS41 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS41_TC1 | TC-041801 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS42 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS42_TC1 | TC-041802 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS43 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS43_TC1 | TC-041803 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS44 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS44_TC1 | TC-041804 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS45 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS45_TC1 | TC-041805 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS46 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS46_TC1 | TC-041806 | BUYPASS ATL105 MasterCard Credit SAMPLE General Retail · unattended CAT Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS47 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS47_TC1 | TC-041188 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS47_TC2 | TC-041189 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS47_TC3 | TC-041190 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS47_TC4 | TC-041191 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS47_TC5 | TC-041192 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |
| BR1_TS47_TC6 | TC-041193 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS48 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS48_TC1 | TC-041194 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS48_TC2 | TC-041195 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS49 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS49_TC1 | TC-041196 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS49_TC2 | TC-041197 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS50 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS50_TC1 | TC-041198 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS50_TC2 | TC-041199 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS51 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS51_TC1 | TC-041200 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC2 | TC-041201 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS52 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS52_TC1 | TC-041202 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS52_TC2 | TC-041203 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS53 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS53_TC1 | TC-041204 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS53_TC2 | TC-041205 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS54 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS54_TC1 | TC-041206 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC2 | TC-041207 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC3 | TC-041208 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC4 | TC-041209 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC5 | TC-041210 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC6 | TC-041211 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC7 | TC-041212 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC8 | TC-041213 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC9 | TC-041214 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC10 | TC-041215 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC11 | TC-041216 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC12 | TC-041217 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC13 | TC-041218 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC14 | TC-041219 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC15 | TC-041220 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC16 | TC-041221 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC17 | TC-041222 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC18 | TC-041223 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC19 | TC-041224 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC20 | TC-041225 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC21 | TC-041226 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC22 | TC-041227 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC23 | TC-041228 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC24 | TC-041229 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC25 | TC-041230 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC26 | TC-041231 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC27 | TC-041232 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC28 | TC-041233 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS54_TC29 | TC-041234 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS54_TC30 | TC-041235 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS55 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS55_TC1 | TC-041236 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS55_TC2 | TC-041237 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS55_TC3 | TC-041238 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS56 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS56_TC1 | TC-041239 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS56_TC2 | TC-041240 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Magnetic Stripe Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS56_TC3 | TC-041241 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS56_TC4 | TC-041242 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Device check: device behaviour: 'An exception report prints after a successful batch'… | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS57 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS57_TC1 | TC-041243 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS57_TC2 | TC-041244 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS57_TC3 | TC-041245 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS58 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS58_TC1 | TC-041246 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC2 | TC-041247 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC3 | TC-041248 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS59 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS59_TC1 | TC-041249 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS59_TC2 | TC-041250 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS59_TC3 | TC-041251 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS60 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS60_TC1 | TC-041252 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS60_TC2 | TC-041253 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS60_TC3 | TC-041254 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS61 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS61_TC1 | TC-041807 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS62 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS62_TC1 | TC-041808 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS63 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS63_TC1 | TC-041809 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS64 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS64_TC1 | TC-041810 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS65 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS65_TC1 | TC-041811 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS66 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS66_TC1 | TC-041812 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS67 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS67_TC1 | TC-041813 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS68 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS68_TC1 | TC-041814 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS69 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS69_TC1 | TC-041815 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS70 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS70_TC1 | TC-041816 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · unattended CAT Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS71 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS71_TC1 | TC-041817 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS72 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS72_TC1 | TC-041818 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS73 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS73_TC1 | TC-041819 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS74 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS74_TC1 | TC-041820 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS75 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS75_TC1 | TC-041821 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS76 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS76_TC1 | TC-041822 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS77 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS77_TC1 | TC-041823 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS78 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS78_TC1 | TC-041824 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS79 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS79_TC1 | TC-041825 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS80 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS80_TC1 | TC-041826 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS81 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS81_TC1 | TC-041827 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS82 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS82_TC1 | TC-041828 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS83 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS83_TC1 | TC-041829 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS84 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS84_TC1 | TC-041830 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS85 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS85_TC1 | TC-041831 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS86 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS86_TC1 | TC-041832 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS87 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS87_TC1 | TC-041833 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS88 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS88_TC1 | TC-041834 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS89 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS89_TC1 | TC-041835 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS90 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS90_TC1 | TC-041836 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS91 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS91_TC1 | TC-041837 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · attended POS Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: no response expected | pending_sme |

### BR1_TS99 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS99_TC1 | TC-041838 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS100 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS100_TC1 | TC-041839 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS101 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS101_TC1 | TC-041840 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS102 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS102_TC1 | TC-041841 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS103 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS103_TC1 | TC-041842 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS104 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS104_TC1 | TC-041843 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS105 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS105_TC1 | TC-041844 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS106 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS106_TC1 | TC-041845 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS107 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS107_TC1 | TC-041846 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS108 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS108_TC1 | TC-041847 | BUYPASS ATL105 MasterCard Credit SAMPLE Supermarket · unattended CAT Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS109 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS109_TC1 | TC-041848 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS110 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS110_TC1 | TC-041849 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS111 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS111_TC1 | TC-041850 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS112 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS112_TC1 | TC-041851 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS113 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS113_TC1 | TC-041852 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS114 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS114_TC1 | TC-041853 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS115 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS115_TC1 | TC-041854 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS116 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS116_TC1 | TC-041855 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS117 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS117_TC1 | TC-041856 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS118 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS118_TC1 | TC-041857 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS119 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS119_TC1 | TC-041858 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS120 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS120_TC1 | TC-041859 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS121 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS121_TC1 | TC-041860 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS122 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS122_TC1 | TC-041861 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS123 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS123_TC1 | TC-041862 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS124 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS124_TC1 | TC-041863 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS125 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS125_TC1 | TC-041864 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS126 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS126_TC1 | TC-041865 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS127 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS127_TC1 | TC-041866 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS128 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS128_TC1 | TC-041867 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |

### BR1_TS129 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS129_TC1 | TC-041868 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · attended POS Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: no response expected | pending_sme |

### BR1_TS137 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS137_TC1 | TC-041869 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS138 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS138_TC1 | TC-041870 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS139 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS139_TC1 | TC-041871 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS140 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS140_TC1 | TC-041872 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS141 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS141_TC1 | TC-041873 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS142 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS142_TC1 | TC-041874 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS143 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS143_TC1 | TC-041875 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS144 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS144_TC1 | TC-041876 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS145 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS145_TC1 | TC-041877 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS146 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS146_TC1 | TC-041878 | BUYPASS ATL105 MasterCard Credit SAMPLE Utilities · unattended CAT Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS147 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS147_TC1 | TC-038622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Additional Information exactly at its documented maximum length… → declined | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | ready |
| BR1_TS147_TC2 | TC-038624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Customer Data contains a character outside its character type (… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS147_TC3 | TC-038613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Service Level value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS147_TC4 | TC-038614 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture EMV Request Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | blocked_no_test_data |
| BR1_TS147_TC5 | TC-041255 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC6 | TC-041256 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS147_TC7 | TC-041257 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS147_TC8 | TC-041258 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS147_TC9 | TC-041259 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the original i… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS147_TC10 | TC-041260 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS147_TC11 | TC-041261 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |
| BR1_TS147_TC12 | TC-041262 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC13 | TC-041263 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS147_TC14 | TC-041264 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS148 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS148_TC1 | TC-038611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture Loyalty Track 2 Data one byte over its documented maximum l… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS148_TC2 | TC-038621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture Information Byte value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS148_TC3 | TC-041265 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC4 | TC-041266 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC5 | TC-041267 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the origin… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS148_TC6 | TC-041268 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC7 | TC-041269 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS148_TC8 | TC-041270 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS149 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS149_TC1 | TC-041271 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC2 | TC-041272 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS149_TC3 | TC-041273 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the original is… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS149_TC4 | TC-041274 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC5 | TC-041275 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS149_TC6 | TC-041276 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS150 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS150_TC1 | TC-041277 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC2 | TC-041278 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS150_TC3 | TC-041279 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS150_TC4 | TC-041280 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC5 | TC-041281 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS150_TC6 | TC-041282 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS151 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS151_TC1 | TC-041283 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC2 | TC-041284 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS151_TC3 | TC-041285 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase/Capture, then Purchase Reversal x2 (the second one after the original is… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS151_TC4 | TC-041286 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC5 | TC-041287 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS151_TC6 | TC-041288 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS152 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS152_TC1 | TC-041289 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC2 | TC-041290 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS152_TC3 | TC-041291 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase/Capture, then Purchase Reversal x2 (the second one after the original is a… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS152_TC4 | TC-041292 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC5 | TC-041293 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS152_TC6 | TC-041294 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS153 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS153_TC1 | TC-041295 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC2 | TC-041296 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS153_TC3 | TC-041297 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal x2 (the second one af… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS153_TC4 | TC-041298 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC5 | TC-041299 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS153_TC6 | TC-041300 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS154 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS154_TC1 | TC-038618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Purchase Code exactly at its documented maximum length (16 by… → declined | single_rule_broken | EMV Financial Transaction Request | 2 (ASSERT) | ready |
| BR1_TS154_TC2 | TC-038623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Customer Data one byte over its documented maximum length (12) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS154_TC3 | TC-038609 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only Fleet Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | needs_client_input |
| BR1_TS154_TC4 | TC-038610 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only Purchase Card Data Segment omitted where requires it → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | needs_client_input |
| BR1_TS154_TC5 | TC-041301 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC6 | TC-041302 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC7 | TC-041303 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC8 | TC-041304 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC9 | TC-041305 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC10 | TC-041306 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC11 | TC-041307 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC12 | TC-041308 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC13 | TC-041309 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC14 | TC-041310 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC15 | TC-041311 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC16 | TC-041312 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC17 | TC-041313 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC18 | TC-041314 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC19 | TC-041315 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC20 | TC-041316 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC21 | TC-041317 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC22 | TC-041318 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC23 | TC-041319 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC24 | TC-041320 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC25 | TC-041321 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC26 | TC-041322 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC27 | TC-041323 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC28 | TC-041324 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC29 | TC-041325 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC30 | TC-041326 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC31 | TC-041327 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC32 | TC-041328 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC33 | TC-041329 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS154_TC34 | TC-041330 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS154_TC35 | TC-041331 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS154_TC36 | TC-041332 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Encrypted PIN Block Data sent where it is n… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS154_TC37 | TC-041333 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS154_TC38 | TC-041334 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC39 | TC-041335 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC40 | TC-041336 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC41 | TC-041337 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with optional EMV Request Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC42 | TC-041338 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC43 | TC-041339 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC44 | TC-041340 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS154_TC45 | TC-041341 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC46 | TC-041342 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is a… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS154_TC47 | TC-041343 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Purchase/Capture (not allowed after this o… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS154_TC48 | TC-041344 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS154_TC49 | TC-041345 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS154_TC50 | TC-041346 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS154_TC51 | TC-041347 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (held offline 55 minutes) (at the 55 minute limi… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS154_TC52 | TC-041348 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (held offline 56 minutes) (beyond the 55 minute… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS154_TC53 | TC-041349 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC54 | TC-041350 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'EMV POS PINless is only supported in a'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC55 | TC-041351 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC56 | TC-041352 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC57 | TC-041353 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC58 | TC-041354 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC59 | TC-041355 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC60 | TC-041356 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC61 | TC-041357 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS154_TC62 | TC-041358 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS155 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS155_TC1 | TC-038616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only Fuel Purchase Amount one byte over its documented maximum… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS155_TC2 | TC-041359 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC3 | TC-041360 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC4 | TC-041361 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC5 | TC-041362 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS155_TC6 | TC-041363 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS155_TC7 | TC-041364 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS155_TC8 | TC-041365 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Encrypted PIN Block Data sent where it… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS155_TC9 | TC-041366 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS155_TC10 | TC-041367 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC11 | TC-041368 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC12 | TC-041369 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC13 | TC-041370 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with optional EMV Request Data Segment → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC14 | TC-041371 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC15 | TC-041372 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC16 | TC-041373 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS155_TC17 | TC-041374 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only, then Purchase/Capture (not allowed after th… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS155_TC18 | TC-041375 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS155_TC19 | TC-041376 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS155_TC20 | TC-041377 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS155_TC21 | TC-041378 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (held offline 55 minutes) (at the 55 minute… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS155_TC22 | TC-041379 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (held offline 56 minutes) (beyond the 55 min… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS155_TC23 | TC-041380 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC24 | TC-041381 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'EMV POS PINless is only supported in a'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC25 | TC-041382 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC26 | TC-041383 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC27 | TC-041384 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC28 | TC-041385 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC29 | TC-041386 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC30 | TC-041387 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC31 | TC-041388 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS155_TC32 | TC-041389 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS156 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS156_TC1 | TC-041390 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC2 | TC-041391 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC3 | TC-041392 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC4 | TC-041393 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS156_TC5 | TC-041394 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS156_TC6 | TC-041395 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS156_TC7 | TC-041396 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent where it is no… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS156_TC8 | TC-041397 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS156_TC9 | TC-041398 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC10 | TC-041399 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC11 | TC-041400 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC12 | TC-041401 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC13 | TC-041402 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC14 | TC-041403 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: EMV Request Data Segment | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC15 | TC-041404 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is al… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS156_TC16 | TC-041405 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed after this or… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS156_TC17 | TC-041406 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS156_TC18 | TC-041407 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS156_TC19 | TC-041408 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS156_TC20 | TC-041409 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS156_TC21 | TC-041410 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the 55 minute l… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS156_TC22 | TC-041411 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: running total / state for Moneris Day End Batch Balance Segment Format (Request) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS156_TC23 | TC-041412 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: arithmetic consistency for Product Code Data Segment | arithmetic | - | n/a (no host message) | requires_review |
| BR1_TS156_TC24 | TC-041413 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC25 | TC-041414 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC26 | TC-041415 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC27 | TC-041416 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC28 | TC-041417 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC29 | TC-041418 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC30 | TC-041419 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS156_TC31 | TC-041420 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS156_TC32 | TC-041421 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'Terminal should support maintenance function to print/display stored'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC33 | TC-041422 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'POS/OPT transaction indicator may be printed on all'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC34 | TC-041423 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'Terminal should send Cash Self Service Price per'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC35 | TC-041424 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'Controller/device should allow configuration of preauthorization amounts'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC36 | TC-041425 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'A receipt must be printed for POS Return/Refund'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC37 | TC-041426 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'If EMV card swiped first and Service Code'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC38 | TC-041427 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'Device must interrogate Product Restriction Code to determine'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC39 | TC-041428 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'Discretionary Data Service Prompt last position: 1 ='…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC40 | TC-041429 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'For Magstripe transactions, device must interpret Restriction Code'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC41 | TC-041430 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 12 terminal behaviour checks [about 'If Track 2 data cannot be collected by'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC42 | TC-041431 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: 2 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS156_TC43 | TC-041432 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device checkpoint for Service Level | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS157 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS157_TC1 | TC-038619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only Required field Information Byte omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS157_TC2 | TC-041433 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC3 | TC-041434 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC4 | TC-041435 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC5 | TC-041436 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS157_TC6 | TC-041437 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS157_TC7 | TC-041438 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Se… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS157_TC8 | TC-041439 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent wh… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS157_TC9 | TC-041440 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS157_TC10 | TC-041441 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC11 | TC-041442 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC12 | TC-041443 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC13 | TC-041444 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC14 | TC-041445 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC15 | TC-041446 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the or… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS157_TC16 | TC-041447 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed a… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS157_TC17 | TC-041448 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS157_TC18 | TC-041449 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit o… → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS157_TC19 | TC-041450 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the lim… → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS157_TC20 | TC-041451 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS157_TC21 | TC-041452 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS157_TC22 | TC-041453 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC23 | TC-041454 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC24 | TC-041455 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC25 | TC-041456 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC26 | TC-041457 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC27 | TC-041458 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC28 | TC-041459 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS157_TC29 | TC-041460 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS158 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS158_TC1 | TC-038638 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only Loyalty Card Data Segment omitted where requires it → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | needs_client_input |
| BR1_TS158_TC2 | TC-041461 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC3 | TC-041462 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC4 | TC-041463 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC5 | TC-041464 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS158_TC6 | TC-041465 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS158_TC7 | TC-041466 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS158_TC8 | TC-041467 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for 'Default track sent on original tran… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS158_TC9 | TC-041468 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Encrypted PIN Block Data sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS158_TC10 | TC-041469 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS158_TC11 | TC-041470 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC12 | TC-041471 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC13 | TC-041472 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC14 | TC-041473 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC15 | TC-041474 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC16 | TC-041475 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only, then Cancellation x2 (the second one after the original is alr… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS158_TC17 | TC-041476 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only, then Purchase/Capture (not allowed after this ori… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS158_TC18 | TC-041477 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS158_TC19 | TC-041478 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS158_TC20 | TC-041479 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS158_TC21 | TC-041480 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS158_TC22 | TC-041481 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (held offline 56 minutes) (beyond the 55 minute li… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS158_TC23 | TC-041482 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC24 | TC-041483 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC25 | TC-041484 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC26 | TC-041485 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC27 | TC-041486 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC28 | TC-041487 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC29 | TC-041488 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS158_TC30 | TC-041489 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS159 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS159_TC1 | TC-041490 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC2 | TC-041491 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC3 | TC-041492 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC4 | TC-041493 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS159_TC5 | TC-041494 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS159_TC6 | TC-041495 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS159_TC7 | TC-041496 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Encrypted PIN Block Data sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS159_TC8 | TC-041497 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS159_TC9 | TC-041498 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC10 | TC-041499 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC11 | TC-041500 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC12 | TC-041501 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC13 | TC-041502 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC14 | TC-041503 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only, then Cancellation x2 (the second one after the original is alre… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS159_TC15 | TC-041504 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only, then Purchase/Capture (not allowed after this orig… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS159_TC16 | TC-041505 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS159_TC17 | TC-041506 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS159_TC18 | TC-041507 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS159_TC19 | TC-041508 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS159_TC20 | TC-041509 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (held offline 56 minutes) (beyond the 55 minute lim… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS159_TC21 | TC-041510 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC22 | TC-041511 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC23 | TC-041512 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC24 | TC-041513 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC25 | TC-041514 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC26 | TC-041515 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC27 | TC-041516 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS159_TC28 | TC-041517 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS160 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS160_TC1 | TC-041518 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC2 | TC-041519 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Point-of-Service Terminal Capability C… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC3 | TC-041520 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC4 | TC-041521 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC5 | TC-041522 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC6 | TC-041523 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only via the alternate path for TransArmor Load… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC7 | TC-041524 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS160_TC8 | TC-041525 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS160_TC9 | TC-041526 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC10 | TC-041527 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC11 | TC-041528 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC12 | TC-041529 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Fleet Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC13 | TC-041530 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Product Code Data Segment sent → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC14 | TC-041531 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS160_TC15 | TC-041532 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (not al… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS160_TC16 | TC-041533 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS160_TC17 | TC-041534 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (resent) sent 3 times (at the… → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | pending_sme |
| BR1_TS160_TC18 | TC-041535 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (resent) sent 4 times (beyond… → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS160_TC19 | TC-041536 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (held offline 55 minutes) (at… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | pending_sme |
| BR1_TS160_TC20 | TC-041537 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (held offline 56 minutes) (bey… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS160_TC21 | TC-041538 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC22 | TC-041539 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC23 | TC-041540 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC24 | TC-041541 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC25 | TC-041542 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC26 | TC-041543 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC27 | TC-041544 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS160_TC28 | TC-041545 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS161 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS161_TC1 | TC-038615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return Purchase Code one byte over its documented maximum length (16) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS161_TC2 | TC-038620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return Required field Information Byte sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS161_TC3 | TC-041546 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |
| BR1_TS161_TC4 | TC-041547 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS161_TC5 | TC-041548 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one after t… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | requires_review |
| BR1_TS161_TC6 | TC-041549 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS161_TC7 | TC-041550 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS162 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS162_TC1 | TC-038612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return Service Level one byte over its documented maximum length… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS162_TC2 | TC-038617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return Account Number one byte over its documented maximum lengt… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS162_TC3 | TC-041551 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one aft… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | requires_review |
| BR1_TS162_TC4 | TC-041552 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS162_TC5 | TC-041553 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS163 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS163_TC1 | TC-041554 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second one after th… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS163_TC2 | TC-041555 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS163_TC3 | TC-041556 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS164 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS164_TC1 | TC-041557 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS164_TC2 | TC-041558 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS164_TC3 | TC-041559 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS165 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS165_TC1 | TC-041560 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return x2 (the second one after the… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS165_TC2 | TC-041561 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS165_TC3 | TC-041562 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS166 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS166_TC1 | TC-041563 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Merchandise Return, then Void of a Merchandise Return x2 (the second one after the… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS166_TC2 | TC-041564 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS166_TC3 | TC-041565 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS167 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS167_TC1 | TC-041566 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return x2 (the… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS167_TC2 | TC-041567 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | pending_sme |
| BR1_TS167_TC3 | TC-041568 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | pending_sme |

### BR1_TS168 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS168_TC1 | TC-041569 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC2 | TC-041570 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC3 | TC-041571 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS168_TC4 | TC-041572 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS168_TC5 | TC-041573 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |
| BR1_TS168_TC6 | TC-041574 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS168_TC7 | TC-041575 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS169 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS169_TC1 | TC-041576 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC2 | TC-041577 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC3 | TC-041578 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS169_TC4 | TC-041579 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS170 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS170_TC1 | TC-041580 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC2 | TC-041581 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC3 | TC-041582 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS170_TC4 | TC-041583 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS171 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS171_TC1 | TC-041584 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC2 | TC-041585 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC3 | TC-041586 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS171_TC4 | TC-041587 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS172 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS172_TC1 | TC-041588 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC2 | TC-041589 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC3 | TC-041590 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Purchase/Capture (Merchant Data Segment returned) → approved | response_check | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS172_TC4 | TC-041591 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS173 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS173_TC1 | TC-041592 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC2 | TC-041593 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Currency Code Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC3 | TC-041594 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Balance Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC4 | TC-041595 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Recharge Type Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC5 | TC-041596 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Originating Device Type Indicator Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC6 | TC-041597 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Customer Name → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC7 | TC-041598 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Universal Unique Identifier → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC8 | TC-041599 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC9 | TC-041600 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Re-Price Data Request Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC10 | TC-041601 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with MCX Checkout Token → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC11 | TC-041602 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Fraud Enhanced Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC12 | TC-041603 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Country Subdivision → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC13 | TC-041604 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Cardholder Language → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC14 | TC-041605 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Previous Transaction ID → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC15 | TC-041606 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC16 | TC-041607 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Additional Security Code → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC17 | TC-041608 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Additional Card ID → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC18 | TC-041609 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Additional TransArmor Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC19 | TC-041610 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Remote Commerce Acceptor Identifier → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC20 | TC-041611 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Merchant Tran ID → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC21 | TC-041612 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Experience Provider Value → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC22 | TC-041613 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC23 | TC-041614 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Service Location Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC24 | TC-041615 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Merchant Payment Gateway ID Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC25 | TC-041616 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Anticipated Completion Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC26 | TC-041617 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Future Transaction Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC27 | TC-041618 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Discount Program Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC28 | TC-041619 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Discount Line-Item Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC29 | TC-041620 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS173_TC30 | TC-041621 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS173_TC31 | TC-041622 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS173_TC32 | TC-041623 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Encrypted PIN Block Data sent where it is n… → declined (to confirm) | single_rule_broken | 5 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS173_TC33 | TC-041624 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS173_TC34 | TC-041625 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC35 | TC-041626 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Information Byte = 0 → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC36 | TC-041627 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Service Level = F → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC37 | TC-041628 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with optional EMV Request Data Segment → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC38 | TC-041629 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Fleet Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC39 | TC-041630 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with Product Code Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC40 | TC-041631 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS173_TC41 | TC-041632 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 5 -> 5 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC42 | TC-041633 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is a… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS173_TC43 | TC-041634 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only, then Purchase/Capture (not allowed after this o… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS173_TC44 | TC-041635 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS173_TC45 | TC-041636 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS173_TC46 | TC-041637 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS173_TC47 | TC-041638 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (held offline 55 minutes) (at the 55 minute limi… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS173_TC48 | TC-041639 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (held offline 56 minutes) (beyond the 55 minute… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS173_TC49 | TC-041640 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC50 | TC-041641 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC51 | TC-041642 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'EMV POS PINless is only supported in a'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC52 | TC-041643 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC53 | TC-041644 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC54 | TC-041645 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC55 | TC-041646 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC56 | TC-041647 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC57 | TC-041648 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC58 | TC-041649 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS173_TC59 | TC-041650 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS174 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS174_TC1 | TC-041651 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC2 | TC-041652 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC3 | TC-041653 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Future Transaction Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC4 | TC-041654 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS174_TC5 | TC-041655 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS174_TC6 | TC-041656 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS174_TC7 | TC-041657 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Encrypted PIN Block Data sent where it… → declined (to confirm) | single_rule_broken | 5 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS174_TC8 | TC-041658 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS174_TC9 | TC-041659 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC10 | TC-041660 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Information Byte = 0 → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC11 | TC-041661 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Service Level = F → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC12 | TC-041662 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with optional EMV Request Data Segment → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC13 | TC-041663 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Fleet Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC14 | TC-041664 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only with Product Code Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC15 | TC-041665 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS174_TC16 | TC-041666 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only, then Purchase/Capture (not allowed after th… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS174_TC17 | TC-041667 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS174_TC18 | TC-041668 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS174_TC19 | TC-041669 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS174_TC20 | TC-041670 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (held offline 55 minutes) (at the 55 minute… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS174_TC21 | TC-041671 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (held offline 56 minutes) (beyond the 55 min… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS174_TC22 | TC-041672 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC23 | TC-041673 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'All EMV Financial Transaction Responses contain Data Section'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC24 | TC-041674 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (EMV Response Data Segment returned) → approved [about 'EMV POS PINless is only supported in a'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC25 | TC-041675 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC26 | TC-041676 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC27 | TC-041677 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC28 | TC-041678 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC29 | TC-041679 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC30 | TC-041680 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC31 | TC-041681 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS174_TC32 | TC-041682 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS175 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS175_TC1 | TC-041683 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC2 | TC-041684 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC3 | TC-041685 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC4 | TC-041686 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS175_TC5 | TC-041687 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS175_TC6 | TC-041688 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Segment → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS175_TC7 | TC-041689 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent where it is no… → declined (to confirm) | single_rule_broken | 5 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS175_TC8 | TC-041690 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS175_TC9 | TC-041691 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC10 | TC-041692 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC11 | TC-041693 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC12 | TC-041694 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Fleet Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC13 | TC-041695 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only with Product Code Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC14 | TC-041696 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: device behaviour: EMV Request Data Segment | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC15 | TC-041697 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is al… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS175_TC16 | TC-041698 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed after this or… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS175_TC17 | TC-041699 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS175_TC18 | TC-041700 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS175_TC19 | TC-041701 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS175_TC20 | TC-041702 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS175_TC21 | TC-041703 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the 55 minute l… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS175_TC22 | TC-041704 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: running total / state for Moneris Day End Batch Balance Segment Format (Request) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS175_TC23 | TC-041705 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: arithmetic consistency for Product Code Data Segment | arithmetic | - | n/a (no host message) | requires_review |
| BR1_TS175_TC24 | TC-041706 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC25 | TC-041707 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC26 | TC-041708 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC27 | TC-041709 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC28 | TC-041710 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC29 | TC-041711 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC30 | TC-041712 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC31 | TC-041713 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS175_TC32 | TC-041714 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS175_TC33 | TC-041715 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'Terminal should support maintenance function to print/display stored'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC34 | TC-041716 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'POS/OPT transaction indicator may be printed on all'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC35 | TC-041717 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'Terminal should send Cash Self Service Price per'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC36 | TC-041718 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'Controller/device should allow configuration of preauthorization amounts'] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC37 | TC-041719 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'A receipt must be printed for POS Return/Refund'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC38 | TC-041720 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'If EMV card swiped first and Service Code'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC39 | TC-041721 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'Device must interrogate Product Restriction Code to determine'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC40 | TC-041722 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'Discretionary Data Service Prompt last position: 1 ='…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC41 | TC-041723 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'For Magstripe transactions, device must interpret Restriction Code'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC42 | TC-041724 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 12 terminal behaviour checks [about 'If Track 2 data cannot be collected by'…] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC43 | TC-041725 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 2 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC44 | TC-041726 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: 4 terminal behaviour checks | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS175_TC45 | TC-041727 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Device check: device checkpoint for Service Level | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS176 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS176_TC1 | TC-041728 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC2 | TC-041729 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Point-of-Service Terminal Capability Codes → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC3 | TC-041730 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Future Transaction Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC4 | TC-041731 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS176_TC5 | TC-041732 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS176_TC6 | TC-041733 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Se… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS176_TC7 | TC-041734 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent wh… → declined (to confirm) | single_rule_broken | 5 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS176_TC8 | TC-041735 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS176_TC9 | TC-041736 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC10 | TC-041737 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC11 | TC-041738 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC12 | TC-041739 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Fleet Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC13 | TC-041740 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only with Product Code Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC14 | TC-041741 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the or… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS176_TC15 | TC-041742 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed a… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS176_TC16 | TC-041743 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS176_TC17 | TC-041744 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit o… → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS176_TC18 | TC-041745 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the lim… → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS176_TC19 | TC-041746 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS176_TC20 | TC-041747 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS176_TC21 | TC-041748 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC22 | TC-041749 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC23 | TC-041750 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC24 | TC-041751 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC25 | TC-041752 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC26 | TC-041753 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC27 | TC-041754 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC28 | TC-041755 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS176_TC29 | TC-041756 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS177 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS177_TC1 | TC-041757 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC2 | TC-041758 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Point-of-Service Terminal Capability C… → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC3 | TC-041759 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Future Transaction Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC4 | TC-041760 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only via the alternate path for Sub Table ID "01" → approved | alternate_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC5 | TC-041761 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC6 | TC-041762 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only via the alternate path for TransArmor Load… → approved | alternate_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC7 | TC-041763 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data… → declined (to confirm) | single_rule_broken | 5 | 1: 1 (FLAG) | pending_sme |
| BR1_TS177_TC8 | TC-041764 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS177_TC9 | TC-041765 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC10 | TC-041766 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Information Byte = 0 → approved | decision_table | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC11 | TC-041767 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Service Level = F → approved | decision_table | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC12 | TC-041768 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Fleet Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC13 | TC-041769 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only with Product Code Data Segment sent → approved | decision_table | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC14 | TC-041770 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS177_TC15 | TC-041771 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (not al… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS177_TC16 | TC-041772 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS177_TC17 | TC-041773 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (resent) sent 3 times (at the… → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | pending_sme |
| BR1_TS177_TC18 | TC-041774 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (resent) sent 4 times (beyond… → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | pending_sme |
| BR1_TS177_TC19 | TC-041775 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (held offline 55 minutes) (at… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | pending_sme |
| BR1_TS177_TC20 | TC-041776 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (held offline 56 minutes) (bey… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | pending_sme |
| BR1_TS177_TC21 | TC-041777 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC22 | TC-041778 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Host Prompts Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC23 | TC-041779 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC24 | TC-041780 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC25 | TC-041781 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Store and Forward Data Segment returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC26 | TC-041782 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned) → approved [about 'Before 1st GENERATE AC command, card may simply'…] | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC27 | TC-041783 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned) → approved [about 'Preauthorization Request format must indicate transaction was initiated'…] | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC28 | TC-041784 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (Transaction Identifier returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS177_TC29 | TC-041785 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT (fuel machine) Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS178 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS178_TC1 | TC-041786 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS178_TC2 | TC-041787 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS178_TC3 | TC-041788 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS179 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS179_TC1 | TC-041879 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT EMV Contactless Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS180 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS180_TC1 | TC-041880 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS181 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS181_TC1 | TC-041881 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS182 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS182_TC1 | TC-041882 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS183 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS183_TC1 | TC-041789 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS183_TC2 | TC-041790 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS183_TC3 | TC-041791 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS183_TC4 | TC-041792 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS184 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS184_TC1 | TC-041883 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS185 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS185_TC1 | TC-041884 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS186 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS186_TC1 | TC-041885 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS187 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS187_TC1 | TC-041886 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS188 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS188_TC1 | TC-041887 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS189 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS189_TC1 | TC-041888 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 (cryptocurrency purchase) · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS190 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS190_TC1 | TC-041793 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS190_TC2 | TC-041794 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS190_TC3 | TC-041795 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS190_TC4 | TC-041796 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS191 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS191_TC1 | TC-041889 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS192 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS192_TC1 | TC-041890 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS193 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS193_TC1 | TC-041891 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS194 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS194_TC1 | TC-041892 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS195 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS195_TC1 | TC-041893 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS196 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS196_TC1 | TC-041894 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 (high risk security) · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

## Scenarios without test cases

- BR1_TS92 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip
- BR1_TS93 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip
- BR1_TS94 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe
- BR1_TS95 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe
- BR1_TS96 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed
- BR1_TS97 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback
- BR1_TS98 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device
- BR1_TS130 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip
- BR1_TS131 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip
- BR1_TS132 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe
- BR1_TS133 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe
- BR1_TS134 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed
- BR1_TS135 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback
- BR1_TS136 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device

## Planned but not generated

- BR1_TS9 G-PW-a539563ea8: M01: template runs on ['CAT'] terminals only; M07 (fallback): Mail/Phone legs are attended POS only (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS10 G-PW-a539563ea8: M01: template runs on ['CAT'] terminals only; M07 (fallback): Mail/Phone legs are attended POS only (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS19 G-PW-549b15c954: M01: template runs on ['CAT'] terminals only; M07 (fallback): Mail/Phone legs are attended POS only (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS20 G-PW-549b15c954: M01: template runs on ['CAT'] terminals only; M07 (fallback): Mail/Phone legs are attended POS only (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS21 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS22 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS23 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS24 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS25 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS26 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS27 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS28 G-PW-e8835bb6a0: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS29 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS30 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS31 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS32 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS33 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS34 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS35 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS36 G-PW-80ec30a7b4: M01: template runs on ['CAT'] terminals only; M07 (fallback): a leg category is not CAT-allowed (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS154 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS155 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS156 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS157 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS158 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS159 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS160 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS168 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS168 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS168 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS168 G-PW-5e4736a61a: S-PETROLEUM-UNATTENDED-01: template runs on ['POS'] terminals only
- BR1_TS169 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS169 G-PW-5e4736a61a: S-PETROLEUM-UNATTENDED-01: template runs on ['POS'] terminals only
- BR1_TS170 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS170 G-PW-5e4736a61a: S-PETROLEUM-UNATTENDED-01: template runs on ['POS'] terminals only
- BR1_TS171 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS171 G-PW-5e4736a61a: S-PETROLEUM-UNATTENDED-01: template runs on ['POS'] terminals only
- BR1_TS172 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS172 G-PW-5e4736a61a: S-PETROLEUM-UNATTENDED-01: template runs on ['POS'] terminals only
- BR1_TS178 G-VF-35e1056df6: S-MCC_5542-01: a leg category is not CAT-allowed (p.83)

## Follow-on legs (reversal / void / cancellation ...)

34 of 34 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.

## Coverage gaps (14)

| Kind | Scenario / leg / template | Reason |
|---|---|---|
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_CONTACT@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_CONTACTLESS@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-MAGSTRIPE@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-MANUAL@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_FALLBACK@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CONTACTLESS_MOBILE@SUPERMARKET-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_CONTACT@UTILITIES-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_CONTACTLESS@UTILITIES-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-MAGSTRIPE@UTILITIES-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE@UTILITIES-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-MANUAL@UTILITIES-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-EMV_FALLBACK@UTILITIES-ATTENDED | no check planned |
| scenario_without_test_cases | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CONTACTLESS_MOBILE@UTILITIES-ATTENDED | no check planned |
