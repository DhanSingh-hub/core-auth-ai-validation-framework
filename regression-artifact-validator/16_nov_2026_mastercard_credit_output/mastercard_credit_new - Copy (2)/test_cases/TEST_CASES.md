# Test cases - BR1 BUYPASS ATL105 MasterCard Credit

Build `buypass-atl105-mastercard-credit-generated-cdf683cfd7`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` generated-2026-11-13.2.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 109 |
| Test cases | 1861 |
| By status | ready: 607, needs_client_input: 646, requires_review: 364, pending_sme: 230, blocked_no_test_data: 14 |
| By polarity | Positive: 1466, Negative: 392, Boundary: 3 |
| Verification | PASS: 1724, NOT_APPLICABLE: 137 |
| Verification level | PASS: 1724, NOT_APPLICABLE: 137 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 1293, FLAG: 234, INFER: 197, N/A: 137 |
| Scenarios with test cases | 109 / 109 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| happy_path Happy path | 384 |
| single_rule_broken Single rule broken | 175 |
| decision_table Decision table | 240 |
| valid_flow Valid flow | 66 |
| invalid_transition Invalid state transition | 100 |
| fault_recovery Fault / recovery | 20 |
| idempotency_retry Idempotency / duplicate / retry | 32 |
| running_total Running total / state | 83 |
| pairwise Interaction (pairwise) | 47 |
| response_check Response / oracle check | 426 |
| ordering Ordering | 6 |
| device_checkpoint Device checkpoint | 93 |
| alternate_path Alternate path | 126 |
| time_based Time-based | 40 |
| soft_constraint Soft constraint | 6 |
| co_requirement Co-requirement / mutual exclusion | 2 |
| routing Routing | 12 |
| arithmetic Arithmetic consistency | 2 |
| boundary Boundary | 1 |

## BR1 BUYPASS ATL105 MasterCard Credit


### BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-024322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Terminal Identifier one byte over its documented maximum length… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC2 | TC-024324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture EMV Chip Data Length one byte over its documented maximum lengt… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC3 | TC-024350 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Card Discretionary Block Data one byte over its documented maxi… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC4 | TC-024314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Required field Product Code sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS1_TC5 | TC-024318 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture EMV Request Data Segment (segment 130) omitted where p.162 requ… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | blocked_no_test_data |
| BR1_TS1_TC6 | TC-024320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture Network Token Data Request (segment 153) omitted where p.310 re… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS1_TC7 | TC-020705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC8 | TC-020706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal with Sequence Number diffe… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS1_TC9 | TC-020707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal with Terminal Identifier d… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS1_TC10 | TC-020708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal with Terminal Identifier d… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS1_TC11 | TC-022280 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC12 | TC-023306 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC13 | TC-023307 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC14 | TC-023308 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS1_TC15 | TC-023309 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS1_TC16 | TC-023310 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS1_TC17 | TC-023311 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS1_TC18 | TC-023312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS1_TC19 | TC-023313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS1_TC20 | TC-023314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS1_TC21 | TC-023315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS1_TC22 | TC-023316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS1_TC23 | TC-023317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS1_TC24 | TC-023318 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contact Chip Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC25 | TC-023319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC26 | TC-023320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC27 | TC-023321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC28 | TC-023322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS1_TC29 | TC-023323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS1_TC30 | TC-023324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-024349 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture EMV Chip Data Length contains a character outside its chara… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS2_TC2 | TC-024321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture Required field Terminal Identifier sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS2_TC3 | TC-024356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture Required field Variable Information Indicator sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS2_TC4 | TC-024351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture Information Byte value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS2_TC5 | TC-020729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC6 | TC-020730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Sequence Number d… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS2_TC7 | TC-020731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal with Terminal Identifi… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS2_TC8 | TC-020732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal with Terminal Identifi… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS2_TC9 | TC-022300 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC10 | TC-023325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC11 | TC-023326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC12 | TC-023327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS2_TC13 | TC-023328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS2_TC14 | TC-023329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal x2 (the second one aft… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS2_TC15 | TC-023330 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS2_TC16 | TC-023331 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS2_TC17 | TC-023332 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS2_TC18 | TC-023333 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS2_TC19 | TC-023334 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal, then Totals Request (totals includ… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS2_TC20 | TC-023335 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal, then Totals Request (totals includ… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS2_TC21 | TC-023336 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS2_TC22 | TC-023337 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contactless Chip Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC23 | TC-023338 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC24 | TC-023339 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC25 | TC-023340 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC26 | TC-023341 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS2_TC27 | TC-023342 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS2_TC28 | TC-023343 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-020753 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-020754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence Number differ… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS3_TC3 | TC-020755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal Identifier di… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS3_TC4 | TC-020756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal with Terminal Identifier di… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS3_TC5 | TC-022320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC6 | TC-023344 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC7 | TC-023345 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC8 | TC-023346 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS3_TC9 | TC-023347 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS3_TC10 | TC-023348 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second one after th… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS3_TC11 | TC-023349 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS3_TC12 | TC-023350 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS3_TC13 | TC-023351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS3_TC14 | TC-023352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS3_TC15 | TC-023353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS3_TC16 | TC-023354 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS3_TC17 | TC-023355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS3_TC18 | TC-023356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Summary of TOR Processing Rules (p.47) | ordering | - | n/a (no host message) | requires_review |
| BR1_TS3_TC19 | TC-023357 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Magnetic Stripe Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC20 | TC-023358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC21 | TC-023359 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC22 | TC-023360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC23 | TC-023361 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS3_TC24 | TC-023362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS3_TC25 | TC-023363 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC26 | TC-023364 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 3 checks p.44-47 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-020779 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC2 | TC-020780 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Sequence N… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS4_TC3 | TC-020781 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal with Terminal I… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS4_TC4 | TC-020782 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal with Terminal I… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS4_TC5 | TC-022342 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC6 | TC-023365 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC7 | TC-023366 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC8 | TC-023367 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS4_TC9 | TC-023368 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS4_TC10 | TC-023369 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal x2 (the second… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS4_TC11 | TC-023370 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS4_TC12 | TC-023371 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS4_TC13 | TC-023372 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS4_TC14 | TC-023373 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS4_TC15 | TC-023374 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal, then Totals Request (totals… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS4_TC16 | TC-023375 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal, then Totals Request (totals… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS4_TC17 | TC-023376 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS4_TC18 | TC-023377 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC19 | TC-023378 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC20 | TC-023379 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC21 | TC-023380 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (Merchant Data Segment returned, content per p.89-15… → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC22 | TC-023381 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS4_TC23 | TC-023382 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS4_TC24 | TC-023383 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-020803 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-020804 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS5_TC3 | TC-020805 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS5_TC4 | TC-020806 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS5_TC5 | TC-022362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC6 | TC-023384 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC7 | TC-023385 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC8 | TC-023386 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS5_TC9 | TC-023387 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS5_TC10 | TC-023388 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal x2 (the second one after the… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS5_TC11 | TC-023389 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS5_TC12 | TC-023390 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS5_TC13 | TC-023391 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS5_TC14 | TC-023392 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS5_TC15 | TC-023393 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS5_TC16 | TC-023394 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS5_TC17 | TC-023395 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS5_TC18 | TC-023396 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Manual / Keyed Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC19 | TC-023397 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC20 | TC-023398 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC21 | TC-023399 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC22 | TC-023400 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS5_TC23 | TC-023401 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS5_TC24 | TC-023402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-020827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC2 | TC-020828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal with Sequence Number differen… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS6_TC3 | TC-020829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal with Terminal Identifier diff… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS6_TC4 | TC-020830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal with Terminal Identifier diff… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS6_TC5 | TC-022382 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC6 | TC-023403 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC7 | TC-023404 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC8 | TC-023405 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS6_TC9 | TC-023406 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS6_TC10 | TC-023407 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal x2 (the second one after the… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS6_TC11 | TC-023408 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS6_TC12 | TC-023409 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS6_TC13 | TC-023410 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS6_TC14 | TC-023411 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS6_TC15 | TC-023412 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS6_TC16 | TC-023413 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal, then Totals Request (totals include it) → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS6_TC17 | TC-023414 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS6_TC18 | TC-023415 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Chip Fallback Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC19 | TC-023416 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC20 | TC-023417 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC21 | TC-023418 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC22 | TC-023419 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS6_TC23 | TC-023420 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS6_TC24 | TC-023421 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-022198 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture Enhanced Fleet Request (segment 145) omitted where p.561 requ… → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS7_TC2 | TC-020851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC3 | TC-020852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal with Sequence Number dif… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS7_TC4 | TC-020853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal with Terminal Identifier… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS7_TC5 | TC-020854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal with Terminal Identifier… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS7_TC6 | TC-022402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with Network Token Data Request (segment 153) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC7 | TC-023422 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC8 | TC-023423 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-023424 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture with Enhanced Fleet Request (segment 145) sent → approved | decision_table | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC10 | TC-023425 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS7_TC11 | TC-023426 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | ready |
| BR1_TS7_TC12 | TC-023427 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal x2 (the second one after… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS7_TC13 | TC-023428 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | ready |
| BR1_TS7_TC14 | TC-023429 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | ready |
| BR1_TS7_TC15 | TC-023430 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS7_TC16 | TC-023431 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (no response), then Time-out Reversal x3 (resent) → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | ready |
| BR1_TS7_TC17 | TC-023432 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal, then Totals Request (totals include… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS7_TC18 | TC-023433 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal, then Totals Request (totals include… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS7_TC19 | TC-023434 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS7_TC20 | TC-023435 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Credential on File Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC21 | TC-023436 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC22 | TC-023437 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC23 | TC-023438 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC24 | TC-023439 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS7_TC25 | TC-023440 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS7_TC26 | TC-023441 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | ready |

### BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-020876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-020877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Seq… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC3 | TC-020878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal with Ter… → declined (to confirm) | single_rule_broken | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC4 | TC-020879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal with Ter… → declined (to confirm) | single_rule_broken | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-022423 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture with Network Token Data Request (segment 153)… → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC6 | TC-023442 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture with Partial Approval Indicator = 0 → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC7 | TC-023443 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture with incremental = completion → approved | decision_table | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC8 | TC-023444 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS8_TC9 | TC-023445 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS8_TC10 | TC-023446 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal x2 (the… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS8_TC11 | TC-023447 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC12 | TC-023448 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (no response), then Purchase Reversal → approved | fault_recovery | 0 -> 8 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS8_TC13 | TC-023449 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal → approved | fault_recovery | 0 -> Z | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS8_TC14 | TC-023450 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (no response), then Time-out Reversal x3 (res… → approved | idempotency_retry | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: no response expected | pending_sme |
| BR1_TS8_TC15 | TC-023451 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal, then Totals Request… → approved | running_total | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS8_TC16 | TC-023452 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal, then Totals Request… → approved | running_total | 0 -> Z -> 990 | 1: 0 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS8_TC17 | TC-023453 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Totals Request (totals include it) → approved | running_total | 0 -> 990 | 1: 0 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS8_TC18 | TC-023454 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture on a CAT terminal with online pin → approved | pairwise | 4 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC19 | TC-023455 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC20 | TC-023456 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (Merchant Data Segment returned, content per… → approved [cites p.190] | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC21 | TC-023457 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (Merchant Data Segment returned, content per… → approved [cites p.89] | response_check | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC22 | TC-023458 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal (card issuer declines) → declined | response_check | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC23 | TC-023459 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture, then Time-out Reversal (card issuer declines) → declined | response_check | 0 -> Z | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC24 | TC-023460 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-022443 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase → approved | happy_path | 6 | 1: no response expected | ready |
| BR1_TS9_TC2 | TC-022444 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Number di… → declined (to confirm) | single_rule_broken | 6 -> C | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS9_TC3 | TC-022445 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Identifie… → declined (to confirm) | single_rule_broken | 6 -> C | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS9_TC4 | TC-022446 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal with Terminal Identifier… → declined (to confirm) | single_rule_broken | 6 -> Z | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS9_TC5 | TC-023461 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase with incremental = completion → approved | decision_table | 6 | 1: no response expected | needs_client_input |
| BR1_TS9_TC6 | TC-023462 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: no response expected ; 2: no response expected | ready |
| BR1_TS9_TC7 | TC-023463 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal → approved | valid_flow | 6 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS9_TC8 | TC-023464 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the origina… → declined | invalid_transition | 6 -> C -> C | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | ready |
| BR1_TS9_TC9 | TC-023465 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal x2 (the second one after… → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: 1 (FLAG) | ready |
| BR1_TS9_TC10 | TC-023466 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Reversal with no original transaction → declined | invalid_transition | C | 1: 1 (ASSERT) | ready |
| BR1_TS9_TC11 | TC-023467 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (no response), then Time-out Reversal → approved | fault_recovery | 6 -> Z | 1: no response expected ; 2: no response expected | ready |
| BR1_TS9_TC12 | TC-023468 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals include… → approved | running_total | 6 -> C -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS9_TC13 | TC-023469 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal, then Totals Request (totals include i… → approved | running_total | 6 -> Z -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS9_TC14 | TC-023470 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS9_TC15 | TC-023471 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for Response Code (p.403) [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC16 | TC-023472 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-11 (p.404) [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC17 | TC-023473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-12 (p.404) [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC18 | TC-023474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-7 (p.404) [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC19 | TC-023475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-8 (p.404) [on Mail/Phone Purchase - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS9_TC20 | TC-023476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Electronic Mail Data Segment (segment 109) (p.67) [on Mail/Phone Purchase - Manual / Keyed] | ordering | - | n/a (no host message) | requires_review |
| BR1_TS9_TC21 | TC-023477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase on a POS terminal with offline pin → approved | pairwise | 6 | 1: no response expected | ready |
| BR1_TS9_TC22 | TC-023478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (AVS or NVS or Both Information returned) → approved | response_check | 6 | 1: no response expected | ready |
| BR1_TS9_TC23 | TC-023479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Mail/Phone Reversal (card issuer declines) → declined | response_check | 6 -> C | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS9_TC24 | TC-023480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 6 | 1: no response expected | ready |
| BR1_TS9_TC25 | TC-023481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 6 | 1: no response expected | ready |
| BR1_TS9_TC26 | TC-023482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase, then Time-out Reversal (card issuer declines) → declined | response_check | 6 -> Z | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS9_TC27 | TC-023483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | ready |
| BR1_TS9_TC28 | TC-023484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Message Format Version Identifier as stated on p.184 [on Mail/Phone Purchase - Manual / Keyed] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS9_TC29 | TC-023485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 05 (EMV Contact Chip, not su… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC30 | TC-023486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 07 (EMV Contactless Chip, no… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC31 | TC-023487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 02 (Magnetic Stripe, not sup… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC32 | TC-023488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 91 (Contactless Magnetic Str… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC33 | TC-023489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 79 (Chip Fallback, not suppo… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |
| BR1_TS9_TC34 | TC-023490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Purchase sent with entry mode 82 (Contactless Mobile Comme… → declined (to confirm) | single_rule_broken | 6 | 1: 1 (FLAG) | ready |

### BR1_TS10 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-022476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase → approved | happy_path | 6 | 1: no response expected | pending_sme |
| BR1_TS10_TC2 | TC-022477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Sequence Numbe… → declined (to confirm) | single_rule_broken | 6 -> C | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC3 | TC-022478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal with Terminal Ident… → declined (to confirm) | single_rule_broken | 6 -> C | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-022479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal with Terminal Identif… → declined (to confirm) | single_rule_broken | 6 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-023491 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase with incremental = completion → approved | decision_table | 6 | 1: no response expected | pending_sme |
| BR1_TS10_TC6 | TC-023492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal → approved | valid_flow | 6 -> C | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC7 | TC-023493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal → approved | valid_flow | 6 -> Z | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC8 | TC-023494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal x2 (the second one after the ori… → declined | invalid_transition | 6 -> C -> C | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC9 | TC-023495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal x2 (the second one af… → declined (to confirm) | invalid_transition | 6 -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC10 | TC-023496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Reversal with no original transaction → declined | invalid_transition | C | 1: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC11 | TC-023497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (no response), then Mail/Phone Reversal → approved | fault_recovery | 6 -> C | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC12 | TC-023498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (no response), then Time-out Reversal → approved | fault_recovery | 6 -> Z | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC13 | TC-023499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal, then Totals Request (totals inc… → approved | running_total | 6 -> C -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS10_TC14 | TC-023500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal, then Totals Request (totals inclu… → approved | running_total | 6 -> Z -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS10_TC15 | TC-023501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Totals Request (totals include it) → approved | running_total | 6 -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS10_TC16 | TC-023502 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase on a POS terminal with offline pin → approved | pairwise | 6 | 1: no response expected | pending_sme |
| BR1_TS10_TC17 | TC-023503 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (AVS or NVS or Both Information returned) → approved | response_check | 6 | 1: no response expected | pending_sme |
| BR1_TS10_TC18 | TC-023504 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Mail/Phone Reversal (card issuer declines) → declined | response_check | 6 -> C | 1: no response expected ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC19 | TC-023505 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 6 | 1: no response expected | pending_sme |
| BR1_TS10_TC20 | TC-023506 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (Merchant Data Segment returned, content per p.89-156) → approved | response_check | 6 | 1: no response expected | pending_sme |
| BR1_TS10_TC21 | TC-023507 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase, then Time-out Reversal (card issuer declines) → declined | response_check | 6 -> Z | 1: no response expected ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC22 | TC-023508 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Purchase (card issuer declines) → declined | response_check | 6 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-024312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Loyalty Track 2 Data one byte over its documented maximum len… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC2 | TC-024355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Variable Information one byte over its documented maximum len… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS11_TC3 | TC-024325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only NFC Payment Tokenization Data Segment (segment 123) omitted w… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS11_TC4 | TC-024329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only Purchase Card Data Segment (segment 104) omitted where p.28 r… → declined | boundary | EMV Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS11_TC5 | TC-018695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC6 | TC-018696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC7 | TC-018697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC8 | TC-018698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC9 | TC-018699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC10 | TC-018700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC11 | TC-018701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC12 | TC-018702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC13 | TC-018703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC14 | TC-018704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC15 | TC-018705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC16 | TC-018706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC17 | TC-018707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC18 | TC-018708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC19 | TC-018709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC20 | TC-018710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC21 | TC-018711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC22 | TC-018712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC23 | TC-018713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC24 | TC-018714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC25 | TC-018715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC26 | TC-020956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC27 | TC-018717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC28 | TC-018718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC29 | TC-018719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC30 | TC-018720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC31 | TC-018721 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC32 | TC-018722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC33 | TC-018723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC34 | TC-020957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC35 | TC-020958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC36 | TC-020959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC37 | TC-020960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [Authorization Only - EMV Contact Chip p.71 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC38 | TC-020961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [Authorization Only - EMV Contact Chip p.71 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC39 | TC-020962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for EMV Chip Data Component Name: Valu… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC40 | TC-020963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - EMV Contact Chip p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC41 | TC-020964 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - EMV Contact Chip p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC42 | TC-020965 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC43 | TC-020966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC44 | TC-020967 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only via the alternate path for Table 030 POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS11_TC45 | TC-020968 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation with Sequence Number differen… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS11_TC46 | TC-020969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation with Terminal Identifier diff… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS11_TC47 | TC-020970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Encrypted PIN Block Data sent where it is n… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC48 | TC-020971 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Information Byte sent where it is not allow… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC49 | TC-020972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Payment Tender Type sent where it is not al… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC50 | TC-020973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS11_TC51 | TC-020974 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC52 | TC-020975 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC53 | TC-020976 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC54 | TC-020977 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC55 | TC-020978 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC56 | TC-020979 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC57 | TC-020980 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC58 | TC-020981 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC59 | TC-020982 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC60 | TC-020983 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC61 | TC-020984 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC62 | TC-020985 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS11_TC63 | TC-020986 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC64 | TC-020987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC65 | TC-020988 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC66 | TC-020989 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Standard Message Data Segment (segment 100) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC67 | TC-020990 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Purchase Card Data Segment (segment 104) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC68 | TC-020991 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional EMV Request Data Segment (segment 130) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC69 | TC-020992 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC70 | TC-022497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with optional Network Token Data Request (segment 153) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC71 | TC-020994 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Variable Information Data Segment (segment 111) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC72 | TC-022498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC73 | TC-023509 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS11_TC74 | TC-023510 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC75 | TC-023511 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS11_TC76 | TC-023512 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation after 25 hours (beyond the 24… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC77 | TC-023513 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS11_TC78 | TC-023514 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS11_TC79 | TC-023515 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contact Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC80 | TC-023516 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC81 | TC-023517 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC82 | TC-023518 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Additional Information Data Segment returned, content per p.… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC83 | TC-023519 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC84 | TC-023520 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC85 | TC-023521 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS11_TC86 | TC-023522 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC87 | TC-023523 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Date and Time Data Segment returned, content per p.195) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC88 | TC-023524 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Dial String Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC89 | TC-023525 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (EMV Response Data Segment returned, content per p.44-209) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC90 | TC-023526 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (EMV Response Data Segment returned, content per p.89-176) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC91 | TC-023527 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC92 | TC-023528 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC93 | TC-023529 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC94 | TC-023530 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Network Token Data Response returned, content per p.339-340) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC95 | TC-023531 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC96 | TC-023532 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Real Time Account Updater Response Segment returned, content… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC97 | TC-023533 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Software Dial Load Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC98 | TC-023534 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Store and Forward Data Segment returned, content per p.83-14… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC99 | TC-023535 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC100 | TC-023536 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Attributes Segment returned, content per p.104-1… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC101 | TC-023537 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Attributes Segment returned, content per p.46-60… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC102 | TC-023538 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC103 | TC-023539 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (Transaction Link Identifier, Transaction Link Action Indicat… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC104 | TC-023540 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS11_TC105 | TC-023541 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 001 UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC106 | TC-023542 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 002 Program Identifier Information, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC107 | TC-023543 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 017 Suspected Fraud Reversal Indicator Information… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC108 | TC-023544 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 018 Card Acceptor Terminal Identifier Information,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC109 | TC-023545 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 034 Name Verification Service (NVS) Information, r… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC110 | TC-023546 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 036 MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC111 | TC-023547 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 038 Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC112 | TC-023548 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 047 Soft Descriptor - Merchant Name, response tabl… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC113 | TC-023549 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 059 Soft Descriptor - Merchant Country, response t… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC114 | TC-023550 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 060 Real Time Account Updater Request Data, respon… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC115 | TC-023551 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 062 Customer Additional Information Data, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC116 | TC-023552 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 066 Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC117 | TC-023553 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 067 Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC118 | TC-023554 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Table 080 Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-024353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only Information Byte exactly at its documented maximum length… → declined | single_rule_broken | EMV Financial Transaction Request | 2 (ASSERT) | ready |
| BR1_TS12_TC2 | TC-024358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only Required field Account Number omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS12_TC3 | TC-024352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only Tax Type 1 contains a character outside its character typ… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS12_TC4 | TC-024323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only Required field EMV Chip Data Length sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS12_TC5 | TC-018809 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC6 | TC-018810 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC7 | TC-018811 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC8 | TC-018812 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC9 | TC-018813 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC10 | TC-018814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 022 Originating Device Type Indicator Informat… → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC11 | TC-018815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC12 | TC-018816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC13 | TC-018817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC14 | TC-018818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC15 | TC-018819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC16 | TC-018820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC17 | TC-018821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC18 | TC-018822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC19 | TC-018823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC20 | TC-018824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC21 | TC-018825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC22 | TC-018826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC23 | TC-018827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC24 | TC-018828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC25 | TC-018829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC26 | TC-021042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC27 | TC-018831 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC28 | TC-018832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC29 | TC-018833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC30 | TC-018834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC31 | TC-018835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC32 | TC-018836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC33 | TC-018837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC34 | TC-021043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC35 | TC-021044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC36 | TC-021045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC37 | TC-021046 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [Authorization Only - EMV Contactless Chip p.71 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC38 | TC-021047 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for EMV Card Sequence Number → approved [Authorization Only - EMV Contactless Chip p.71 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC39 | TC-021048 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for EMV Chip Data Component Name:… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC40 | TC-021049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_… → approved [Authorization Only - EMV Contactless Chip p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC41 | TC-021050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_… → approved [Authorization Only - EMV Contactless Chip p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC42 | TC-021051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB_TABLE_… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC43 | TC-021052 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC44 | TC-021053 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only via the alternate path for Table 030 POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS12_TC45 | TC-021054 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation with Sequence Number diff… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS12_TC46 | TC-021055 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation with Terminal Identifier… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS12_TC47 | TC-021056 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Encrypted PIN Block Data sent where it… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC48 | TC-021057 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Information Byte sent where it is not a… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC49 | TC-021058 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Payment Tender Type sent where it is no… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC50 | TC-021059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Service Level sent where it is not allo… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS12_TC51 | TC-021060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC52 | TC-021061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC53 | TC-021062 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC54 | TC-021063 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC55 | TC-021064 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC56 | TC-021065 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC57 | TC-021066 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC58 | TC-021067 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC59 | TC-021068 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC60 | TC-021069 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC61 | TC-021070 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC62 | TC-021071 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS12_TC63 | TC-021072 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC64 | TC-021073 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC65 | TC-021074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC66 | TC-021075 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Standard Message Data Segment (segment 100) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC67 | TC-021076 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Purchase Card Data Segment (segment 104) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC68 | TC-021077 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional EMV Request Data Segment (segment 130) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC69 | TC-021078 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC70 | TC-022545 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with optional Network Token Data Request (segment 153) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC71 | TC-021080 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Variable Information Data Segment (segment 111) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC72 | TC-022546 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC73 | TC-023555 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS12_TC74 | TC-023556 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Authorization Only (first card partly approved, bal… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC75 | TC-023557 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS12_TC76 | TC-023558 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation after 25 hours (beyond th… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC77 | TC-023559 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include i… → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS12_TC78 | TC-023560 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS12_TC79 | TC-023561 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) EMV Contactless Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC80 | TC-023562 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC81 | TC-023563 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC82 | TC-023564 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Additional Information Data Segment returned, content pe… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC83 | TC-023565 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information retur… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC84 | TC-023566 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC85 | TC-023567 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS12_TC86 | TC-023568 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC87 | TC-023569 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Date and Time Data Segment returned, content per p.195) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC88 | TC-023570 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Dial String Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC89 | TC-023571 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (EMV Response Data Segment returned, content per p.44-209) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC90 | TC-023572 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (EMV Response Data Segment returned, content per p.89-176) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC91 | TC-023573 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC92 | TC-023574 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC93 | TC-023575 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC94 | TC-023576 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Network Token Data Response returned, content per p.339-… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC95 | TC-023577 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC96 | TC-023578 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Real Time Account Updater Response Segment returned, con… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC97 | TC-023579 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Software Dial Load Data Segment returned, content per p.… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC98 | TC-023580 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Store and Forward Data Segment returned, content per p.8… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC99 | TC-023581 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC100 | TC-023582 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned, content per p.1… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC101 | TC-023583 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Attributes Segment returned, content per p.4… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC102 | TC-023584 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC103 | TC-023585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (Transaction Link Identifier, Transaction Link Action Ind… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC104 | TC-023586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS12_TC105 | TC-023587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 001 UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC106 | TC-023588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 002 Program Identifier Information, response t… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC107 | TC-023589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 017 Suspected Fraud Reversal Indicator Informa… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC108 | TC-023590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 018 Card Acceptor Terminal Identifier Informat… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC109 | TC-023591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 034 Name Verification Service (NVS) Informatio… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC110 | TC-023592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 036 MasterCard UCAF Data, response table check… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC111 | TC-023593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 038 Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC112 | TC-023594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 047 Soft Descriptor - Merchant Name, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC113 | TC-023595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 059 Soft Descriptor - Merchant Country, respon… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC114 | TC-023596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 060 Real Time Account Updater Request Data, re… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC115 | TC-023597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 062 Customer Additional Information Data, resp… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC116 | TC-023598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 066 Digital Commerce Data, response table chec… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC117 | TC-023599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 067 Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC118 | TC-023600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Authorization Only with Table 080 Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS13 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-024360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only EMV Request Data Segment (segment 130) omitted where p.142 req… → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS13_TC2 | TC-018923 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-018924 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC4 | TC-018925 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC5 | TC-018926 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC6 | TC-018927 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC7 | TC-018928 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC8 | TC-018929 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC9 | TC-018930 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC10 | TC-018931 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC11 | TC-018932 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC12 | TC-018933 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC13 | TC-018934 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC14 | TC-018935 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC15 | TC-018936 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC16 | TC-018937 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC17 | TC-018938 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC18 | TC-018939 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC19 | TC-018940 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC20 | TC-018941 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC21 | TC-018942 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC22 | TC-018943 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC23 | TC-021128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC24 | TC-018945 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC25 | TC-018946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC26 | TC-018947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC27 | TC-018948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC28 | TC-018949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC29 | TC-018950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC30 | TC-018951 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC31 | TC-021129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Access Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC32 | TC-021130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC33 | TC-021131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC34 | TC-021132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC35 | TC-021133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC36 | TC-021134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Chip Data Component Name: Value… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC37 | TC-021135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Chip Data Length → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC38 | TC-021136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for EMV Request Data Segment (segment 1… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC39 | TC-021137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - Magnetic Stripe p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC40 | TC-021138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - Magnetic Stripe p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC41 | TC-021139 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC42 | TC-021140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC43 | TC-021141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only via the alternate path for Table 030 POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS13_TC44 | TC-021142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation with Sequence Number different… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS13_TC45 | TC-021143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation with Terminal Identifier diffe… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS13_TC46 | TC-021144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent where it is no… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC47 | TC-021145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Information Byte sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC48 | TC-021146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Payment Tender Type sent where it is not all… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC49 | TC-021147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS13_TC50 | TC-021148 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC51 | TC-021149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC52 | TC-021150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC53 | TC-021151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC54 | TC-021152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC55 | TC-021153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC56 | TC-021154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC57 | TC-021155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC58 | TC-021156 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC59 | TC-021157 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC60 | TC-021158 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC61 | TC-021159 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS13_TC62 | TC-021160 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC63 | TC-021161 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC64 | TC-021162 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC65 | TC-021163 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Standard Message Data Segment (segment 100) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC66 | TC-021164 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Purchase Card Data Segment (segment 104) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC67 | TC-021165 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional NFC Payment Tokenization Data Segment (segment 1… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC68 | TC-021166 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC69 | TC-022593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with optional Network Token Data Request (segment 153) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC70 | TC-021168 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Variable Information Data Segment (segment 111) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC71 | TC-022594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC72 | TC-023601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.313 of ATL105 | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC73 | TC-023602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.639 of ATL105 | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC74 | TC-023603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Load Type of ATL105 | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC75 | TC-023604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Pause Indicator of ATL105 | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC76 | TC-023605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS13_TC77 | TC-023606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC78 | TC-023607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS13_TC79 | TC-023608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation after 25 hours (beyond the 24… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC80 | TC-023609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS13_TC81 | TC-023610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS13_TC82 | TC-023611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for Proprietary Data Load Segment (segment 118) (p.199) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC83 | TC-023612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-72-2 (p.72) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC84 | TC-023613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-FISERV-EMV-GUIDE-130-1 (p.130) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC85 | TC-023614 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-FISERV-EMV-GUIDE-135-1 (p.135) | running_total | - | n/a (no host message) | requires_review |
| BR1_TS13_TC86 | TC-023615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Expiration Date (p.718) | ordering | - | n/a (no host message) | requires_review |
| BR1_TS13_TC87 | TC-023616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for TransArmor Load Data Segment (segment 116) (p.67) | ordering | - | n/a (no host message) | requires_review |
| BR1_TS13_TC88 | TC-023617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for rule BR-718-6 (p.718) | ordering | - | n/a (no host message) | requires_review |
| BR1_TS13_TC89 | TC-023618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: co-requirement / mutual exclusion for ENT-TABLE-I-049-SUB-PURPOSE_IDENTIFIES_A_DEFERRED_TRANSAC… | co_requirement | - | n/a (no host message) | requires_review |
| BR1_TS13_TC90 | TC-023619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: co-requirement / mutual exclusion for Table 032 POS Additional Information Indicator (p.79) | co_requirement | - | n/a (no host message) | requires_review |
| BR1_TS13_TC91 | TC-023620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Card Label (p.46) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC92 | TC-023621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Dial Backup and Diverse Facilities (p.49) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC93 | TC-023622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 (p.577) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC94 | TC-023623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for ENT-TABLE-K-017-SUB-PINLESS_DEBIT_INFORMATION (p.54) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC95 | TC-023624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Encrypted PIN Block Data (p.74) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC96 | TC-023625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Network Management Message (p.43) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC97 | TC-023626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Response Code (p.88) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC98 | TC-023627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Standard Message Data Segment (segment 100) (p.74) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC99 | TC-023628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for Table 016 BUYPASS Card Type Code (p.656) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC100 | TC-023629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for rule BR-ATL105-TRANSARMOR-14-2 (p.14) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC101 | TC-023630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for rule BR-FISERV-EMV-GUIDE-125-1 (p.125) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC102 | TC-023631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: routing for rule BR-FISERV-EMV-GUIDE-87-8 (p.87) | routing | - | n/a (no host message) | requires_review |
| BR1_TS13_TC103 | TC-023632 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Magnetic Stripe Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC104 | TC-023633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC105 | TC-023634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC106 | TC-023635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Additional Information Data Segment returned, content per p.1… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC107 | TC-023636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC108 | TC-023637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC109 | TC-023638 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS13_TC110 | TC-023639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC111 | TC-023640 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Date and Time Data Segment returned, content per p.195) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC112 | TC-023641 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Dial String Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC113 | TC-023642 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC114 | TC-023643 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC115 | TC-023644 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC116 | TC-023645 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Network Token Data Response returned, content per p.339-340) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC117 | TC-023646 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC118 | TC-023647 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Real Time Account Updater Response Segment returned, content… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC119 | TC-023648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Software Dial Load Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC120 | TC-023649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Store and Forward Data Segment returned, content per p.83-140) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC121 | TC-023650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC122 | TC-023651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Attributes Segment returned, content per p.104-18… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC123 | TC-023652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Attributes Segment returned, content per p.46-607) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC124 | TC-023653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC125 | TC-023654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (Transaction Link Identifier, Transaction Link Action Indicato… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC126 | TC-023655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS13_TC127 | TC-023656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 001 UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC128 | TC-023657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 002 Program Identifier Information, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC129 | TC-023658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 017 Suspected Fraud Reversal Indicator Information,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC130 | TC-023659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 018 Card Acceptor Terminal Identifier Information,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC131 | TC-023660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 034 Name Verification Service (NVS) Information, re… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC132 | TC-023661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 036 MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC133 | TC-023662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 038 Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC134 | TC-023663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 047 Soft Descriptor - Merchant Name, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC135 | TC-023664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 059 Soft Descriptor - Merchant Country, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC136 | TC-023665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 060 Real Time Account Updater Request Data, respons… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC137 | TC-023666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 062 Customer Additional Information Data, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC138 | TC-023667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 066 Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC139 | TC-023668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 067 Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC140 | TC-023669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Authorization Only with Table 080 Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS13_TC141 | TC-023670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: CAPK lifecycle within 6 months (terminal certification) [cites p.96] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC142 | TC-023671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: CAPK lifecycle within 6 months (terminal certification) [cites p.95] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC143 | TC-023672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 10 checks p.163-175 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC144 | TC-023673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.100-102 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC145 | TC-023674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.102-108 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC146 | TC-023675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.108-115 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC147 | TC-023676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.115-161 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC148 | TC-023677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.161-163 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC149 | TC-023678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.18-39 of BUYPASS-DCT | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC150 | TC-023679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.343-354 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC151 | TC-023680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.364-394 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC152 | TC-023681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.37-50 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC153 | TC-023682 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.400-416 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC154 | TC-023683 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.416-469 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC155 | TC-023684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.470-476 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC156 | TC-023685 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.50-75 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC157 | TC-023686 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.75-98 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC158 | TC-023687 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.81-91 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC159 | TC-023688 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.91-93 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC160 | TC-023689 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 12 checks p.98-100 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC161 | TC-023690 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.138 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC162 | TC-023691 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.160 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC163 | TC-023692 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.171-172 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC164 | TC-023693 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.189-200 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC165 | TC-023694 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 2 checks p.547-548 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC166 | TC-023695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 3 checks p.27-32 of ATL105-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC167 | TC-023696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 3 checks p.34 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC168 | TC-023697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 3 checks p.66 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC169 | TC-023698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 3 checks p.8-11 of BUYPASS-DCT | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC170 | TC-023699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 4 checks p.266 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC171 | TC-023700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 4 checks p.476-484 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC172 | TC-023701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 5 checks p.252 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC173 | TC-023702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 5 checks p.71-76 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC174 | TC-023703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 5 checks p.93-94 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC175 | TC-023704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 6 checks p.518-522 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC176 | TC-023705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 7 checks p.254-262 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC177 | TC-023706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 7 checks p.57-77 of ATL105-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC178 | TC-023707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: 9 checks p.39-43 of BUYPASS-DCT | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC179 | TC-023708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: CA Public Key File Block of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC180 | TC-023709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Card Table Master of BUYPASS-DCT | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC181 | TC-023710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Card Type of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC182 | TC-023711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: ENT-TABLE-K-020-SUB-HOST_PROMPTS_INFORMATION_CONT of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC183 | TC-023712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Encrypted PIN Block Data of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC184 | TC-023713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Enhanced Fleet Request (segment 145) of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC185 | TC-023714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Key ID of ATL105-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC186 | TC-023715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display /Printer Message of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC187 | TC-023716 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display /Printer Message of ATL105-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC188 | TC-023717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display Communications Message of ATL105 [cites p.59] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC189 | TC-023718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Terminal Display Communications Message of ATL105 [cites p.188] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC190 | TC-023719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Token PAN Suffix of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC191 | TC-023720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Token PAN Suffix of ATL105-TRANSARMOR | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC192 | TC-023721 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: Vehicle ID# of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC193 | TC-023722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device behaviour: rule BR-BUYPASS-MTAP-44-2 (p.44) of BUYPASS-MTAP | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC194 | TC-023723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device checkpoint for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 (p.577) | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC195 | TC-023724 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: device checkpoint for TransArmor Load Data Segment (segment 116) (p.46) | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC196 | TC-023725 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: 12 checks p.24-33 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC197 | TC-023726 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: 12 checks p.33-36 of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC198 | TC-023727 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: rule BR-FISERV-EMV-GUIDE-36-7 (p.36) of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC199 | TC-023728 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: terminal / device certification: rule BR-FISERV-EMV-GUIDE-4-3 (p.4) of FISERV-EMV-GUIDE | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC200 | TC-023729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: CA Public Key File Data Segment (segment 132) as stated on p.33 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC201 | TC-023730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Merchant Trace ID as stated on p.503 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC202 | TC-023731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Table 060 Real Time Account Updater Request Data as stated on p.35 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC203 | TC-023732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Token Requestor ID as stated on p.16 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS13_TC204 | TC-023733 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Mail/Phone Reversal (not supported for this card) → declined (to confirm) | single_rule_broken | C | 1: 1 (FLAG) | ready |
| BR1_TS13_TC205 | TC-023734 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Account Verification (not supported for this card) → declined (to confirm) | single_rule_broken | A | 1: 1 (FLAG) | ready |

### BR1_TS14 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-019127 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC2 | TC-019128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC3 | TC-019129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC4 | TC-019130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC5 | TC-019131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC6 | TC-019132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 022 Originating Device Type Indicator I… → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC7 | TC-019133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC8 | TC-019134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC9 | TC-019135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 031 Point-of-Service Terminal Capabilit… → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC10 | TC-019136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC11 | TC-019137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC12 | TC-019138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC13 | TC-019139 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC14 | TC-019140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC15 | TC-019141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC16 | TC-019142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC17 | TC-019143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC18 | TC-019144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC19 | TC-019145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC20 | TC-019146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC21 | TC-019147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC22 | TC-021304 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC23 | TC-019149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC24 | TC-019150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodg… → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC25 | TC-019151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC26 | TC-019152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC27 | TC-019153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC28 | TC-019154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC29 | TC-019155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC30 | TC-021305 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC31 | TC-021306 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC32 | TC-021307 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC33 | TC-021308 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC34 | TC-021309 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for EMV Chip Data Component… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC35 | TC-021310 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB… → approved [Authorization Only - Contactless Magnetic Stripe p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC36 | TC-021311 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB… → approved [Authorization Only - Contactless Magnetic Stripe p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC37 | TC-021312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC38 | TC-021313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC39 | TC-021314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only via the alternate path for Table 030 POS Condition… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS14_TC40 | TC-021315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation with Sequence Numb… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS14_TC41 | TC-021316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation with Terminal Iden… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS14_TC42 | TC-021317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data sent wh… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC43 | TC-021318 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Information Byte sent where it i… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC44 | TC-021319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Payment Tender Type sent where i… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC45 | TC-021320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Service Level sent where it is n… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS14_TC46 | TC-021321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC47 | TC-021322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC48 | TC-021323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC49 | TC-021324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC50 | TC-021325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC51 | TC-021326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC52 | TC-021327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC53 | TC-021328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC54 | TC-021329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC55 | TC-021330 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC56 | TC-021331 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC57 | TC-021332 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS14_TC58 | TC-021333 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC59 | TC-021334 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC60 | TC-021335 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC61 | TC-021336 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Standard Message Data Segment (segme… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC62 | TC-021337 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Purchase Card Data Segment (segment… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC63 | TC-021338 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional NFC Payment Tokenization Data Segmen… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC64 | TC-021339 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC65 | TC-022729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with optional Network Token Data Request (segment… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC66 | TC-021341 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Variable Information Data Segment (segment 11… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC67 | TC-022730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC68 | TC-023735 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS14_TC69 | TC-023736 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Authorization Only (first card partly approv… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC70 | TC-023737 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation after 24 hours (at the 24 hour… → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS14_TC71 | TC-023738 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation after 25 hours (be… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC72 | TC-023739 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation, then Totals Request (totals in… → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS14_TC73 | TC-023740 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS14_TC74 | TC-023741 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Magnetic Stripe Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC75 | TC-023742 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC76 | TC-023743 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC77 | TC-023744 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Additional Information Data Segment returned, con… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC78 | TC-023745 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (BUYPASS Card Type Code, PIN on Receipt Informatio… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC79 | TC-023746 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC80 | TC-023747 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS14_TC81 | TC-023748 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Cardholder Additional Information Result Code ret… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC82 | TC-023749 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Date and Time Data Segment returned, content per… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC83 | TC-023750 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Dial String Data Segment returned, content per p.… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC84 | TC-023751 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC85 | TC-023752 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC86 | TC-023753 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC87 | TC-023754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Network Token Data Response returned, content per… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC88 | TC-023755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC89 | TC-023756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Real Time Account Updater Response Segment return… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC90 | TC-023757 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Software Dial Load Data Segment returned, content… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC91 | TC-023758 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Store and Forward Data Segment returned, content… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC92 | TC-023759 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC93 | TC-023760 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned, content… → approved [cites p.104] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC94 | TC-023761 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Attributes Segment returned, content… → approved [cites p.62] | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC95 | TC-023762 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC96 | TC-023763 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (Transaction Link Identifier, Transaction Link Act… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC97 | TC-023764 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS14_TC98 | TC-023765 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 001 UPC Information, response table che… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC99 | TC-023766 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 002 Program Identifier Information, res… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC100 | TC-023767 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 017 Suspected Fraud Reversal Indicator… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC101 | TC-023768 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 018 Card Acceptor Terminal Identifier I… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC102 | TC-023769 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 034 Name Verification Service (NVS) Inf… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC103 | TC-023770 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 036 MasterCard UCAF Data, response tabl… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC104 | TC-023771 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 038 Paydiant Tender ID, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC105 | TC-023772 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 047 Soft Descriptor - Merchant Name, re… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC106 | TC-023773 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 059 Soft Descriptor - Merchant Country,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC107 | TC-023774 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 060 Real Time Account Updater Request D… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC108 | TC-023775 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 062 Customer Additional Information Dat… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC109 | TC-023776 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 066 Digital Commerce Data, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC110 | TC-023777 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 067 Anticipated Amount, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS14_TC111 | TC-023778 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Authorization Only with Table 080 Staged Wallet ID, response table ch… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS15 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-024327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only Token PAN Suffix one byte over its documented maximum length (4) → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS15_TC2 | TC-024328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only Loyalty Card Data Segment (segment 108) omitted where p.15 requ… → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS15_TC3 | TC-019238 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC4 | TC-019239 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC5 | TC-019240 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC6 | TC-019241 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC7 | TC-019242 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC8 | TC-019243 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC9 | TC-019244 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC10 | TC-019245 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC11 | TC-019246 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC12 | TC-019247 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC13 | TC-019248 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC14 | TC-019249 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC15 | TC-019250 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC16 | TC-019251 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC17 | TC-019252 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC18 | TC-019253 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC19 | TC-019254 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC20 | TC-019255 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC21 | TC-019256 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC22 | TC-019257 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC23 | TC-019258 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC24 | TC-021387 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC25 | TC-019260 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC26 | TC-019261 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC27 | TC-019262 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC28 | TC-019263 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC29 | TC-019264 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC30 | TC-019265 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC31 | TC-019266 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC32 | TC-021388 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC33 | TC-021389 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC34 | TC-021390 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC35 | TC-021391 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC36 | TC-021392 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for EMV Chip Data Component Name: Value… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC37 | TC-021393 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - Manual / Keyed p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC38 | TC-021394 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - Manual / Keyed p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC39 | TC-021395 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC40 | TC-021396 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC41 | TC-021397 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only via the alternate path for Table 030 POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS15_TC42 | TC-021398 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation with Sequence Number different… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS15_TC43 | TC-021399 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation with Terminal Identifier differ… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS15_TC44 | TC-021400 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Encrypted PIN Block Data sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC45 | TC-021401 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Information Byte sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC46 | TC-021402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Payment Tender Type sent where it is not allo… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC47 | TC-021403 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS15_TC48 | TC-021404 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC49 | TC-021405 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC50 | TC-021406 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC51 | TC-021407 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC52 | TC-021408 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC53 | TC-021409 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC54 | TC-021410 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC55 | TC-021411 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC56 | TC-021412 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC57 | TC-021413 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC58 | TC-021414 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC59 | TC-021415 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS15_TC60 | TC-021416 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC61 | TC-021417 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC62 | TC-021418 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC63 | TC-021419 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Standard Message Data Segment (segment 100) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC64 | TC-021420 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Purchase Card Data Segment (segment 104) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC65 | TC-021421 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional NFC Payment Tokenization Data Segment (segment 12… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC66 | TC-021422 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC67 | TC-022775 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with optional Network Token Data Request (segment 153) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC68 | TC-021424 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Loyalty Card Data Segment (segment 108) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC69 | TC-021425 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Variable Information Data Segment (segment 111) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC70 | TC-022776 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC71 | TC-023779 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS15_TC72 | TC-023780 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Authorization Only (first card partly approved, balance o… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC73 | TC-023781 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS15_TC74 | TC-023782 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation after 25 hours (beyond the 24 h… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC75 | TC-023783 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS15_TC76 | TC-023784 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS15_TC77 | TC-023785 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Manual / Keyed Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC78 | TC-023786 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC79 | TC-023787 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Account Type returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC80 | TC-023788 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC81 | TC-023789 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Additional Information Data Segment returned, content per p.17… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC82 | TC-023790 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC83 | TC-023791 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC84 | TC-023792 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS15_TC85 | TC-023793 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC86 | TC-023794 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Date and Time Data Segment returned, content per p.195) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC87 | TC-023795 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Dial String Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC88 | TC-023796 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC89 | TC-023797 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC90 | TC-023798 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC91 | TC-023799 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Merchant Data Segment returned, content per p.4) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC92 | TC-023800 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Network Token Data Response returned, content per p.339-340) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC93 | TC-023801 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC94 | TC-023802 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Real Time Account Updater Response Segment returned, content p… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC95 | TC-023803 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Software Dial Load Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC96 | TC-023804 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Store and Forward Data Segment returned, content per p.83-140) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC97 | TC-023805 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC98 | TC-023806 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Attributes Segment returned, content per p.104-182) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC99 | TC-023807 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Attributes Segment returned, content per p.46-607) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC100 | TC-023808 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC101 | TC-023809 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (Transaction Link Identifier, Transaction Link Action Indicator… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC102 | TC-023810 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS15_TC103 | TC-023811 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 001 UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC104 | TC-023812 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 002 Program Identifier Information, response table c… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC105 | TC-023813 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 017 Suspected Fraud Reversal Indicator Information,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC106 | TC-023814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 018 Card Acceptor Terminal Identifier Information, r… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC107 | TC-023815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 034 Name Verification Service (NVS) Information, res… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC108 | TC-023816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 036 MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC109 | TC-023817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 038 Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC110 | TC-023818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 047 Soft Descriptor - Merchant Name, response table… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC111 | TC-023819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 059 Soft Descriptor - Merchant Country, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC112 | TC-023820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 060 Real Time Account Updater Request Data, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC113 | TC-023821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 062 Customer Additional Information Data, response t… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC114 | TC-023822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 066 Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC115 | TC-023823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 067 Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS15_TC116 | TC-023824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Authorization Only with Table 080 Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS16 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-019351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC2 | TC-019352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC3 | TC-019353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC4 | TC-019354 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC5 | TC-019355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC6 | TC-019356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC7 | TC-019357 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC8 | TC-019358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC9 | TC-019359 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC10 | TC-019360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC11 | TC-019361 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC12 | TC-019362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC13 | TC-019363 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC14 | TC-019364 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC15 | TC-019365 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC16 | TC-019366 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC17 | TC-019367 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC18 | TC-019368 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC19 | TC-019369 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC20 | TC-019370 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC21 | TC-019371 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC22 | TC-021473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC23 | TC-019373 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC24 | TC-019374 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC25 | TC-019375 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC26 | TC-019376 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC27 | TC-019377 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC28 | TC-019378 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC29 | TC-019379 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC30 | TC-021474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC31 | TC-021475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC32 | TC-021476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC33 | TC-021477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Card Sequence Number → approved [Authorization Only - Chip Fallback p.71 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC34 | TC-021478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Card Sequence Number → approved [Authorization Only - Chip Fallback p.71 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC35 | TC-021479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Chip Data Component Name: Value (… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC36 | TC-021480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Chip Data Length → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC37 | TC-021481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for EMV Request Data Segment (segment 130) → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC38 | TC-021482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - Chip Fallback p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC39 | TC-021483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 → approved [Authorization Only - Chip Fallback p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC40 | TC-021484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC41 | TC-021485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Network Management Message → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC42 | TC-021486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC43 | TC-021487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only via the alternate path for Table 030 POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS16_TC44 | TC-021488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation with Sequence Number different f… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS16_TC45 | TC-021489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation with Terminal Identifier differe… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS16_TC46 | TC-021490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Encrypted PIN Block Data sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC47 | TC-021491 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Information Byte sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC48 | TC-021492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Payment Tender Type sent where it is not allow… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC49 | TC-021493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS16_TC50 | TC-021494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC51 | TC-021495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC52 | TC-021496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC53 | TC-021497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC54 | TC-021498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC55 | TC-021499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC56 | TC-021500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC57 | TC-021501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC58 | TC-021502 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC59 | TC-021503 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC60 | TC-021504 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC61 | TC-021505 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS16_TC62 | TC-021506 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC63 | TC-021507 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC64 | TC-021508 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC65 | TC-021509 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Standard Message Data Segment (segment 100) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC66 | TC-021510 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Purchase Card Data Segment (segment 104) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC67 | TC-021511 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional NFC Payment Tokenization Data Segment (segment 123) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC68 | TC-021512 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC69 | TC-022823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with optional Network Token Data Request (segment 153) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC70 | TC-021514 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Variable Information Data Segment (segment 111) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC71 | TC-022824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC72 | TC-023825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS16_TC73 | TC-023826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Authorization Only (first card partly approved, balance on… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC74 | TC-023827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation after 24 hours (at the 24 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS16_TC75 | TC-023828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation after 25 hours (beyond the 24 ho… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC76 | TC-023829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS16_TC77 | TC-023830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS16_TC78 | TC-023831 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Chip Fallback Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC79 | TC-023832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC80 | TC-023833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC81 | TC-023834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Additional Information Data Segment returned, content per p.177) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC82 | TC-023835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC83 | TC-023836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC84 | TC-023837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS16_TC85 | TC-023838 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC86 | TC-023839 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Date and Time Data Segment returned, content per p.195) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC87 | TC-023840 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Dial String Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC88 | TC-023841 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC89 | TC-023842 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC90 | TC-023843 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC91 | TC-023844 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Network Token Data Response returned, content per p.339-340) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC92 | TC-023845 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC93 | TC-023846 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Real Time Account Updater Response Segment returned, content pe… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC94 | TC-023847 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Software Dial Load Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC95 | TC-023848 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Store and Forward Data Segment returned, content per p.83-140) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC96 | TC-023849 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC97 | TC-023850 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Attributes Segment returned, content per p.104-182) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC98 | TC-023851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Attributes Segment returned, content per p.46-607) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC99 | TC-023852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC100 | TC-023853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (Transaction Link Identifier, Transaction Link Action Indicator… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC101 | TC-023854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS16_TC102 | TC-023855 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 001 UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC103 | TC-023856 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 002 Program Identifier Information, response table ch… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC104 | TC-023857 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 017 Suspected Fraud Reversal Indicator Information, r… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC105 | TC-023858 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 018 Card Acceptor Terminal Identifier Information, re… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC106 | TC-023859 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 034 Name Verification Service (NVS) Information, resp… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC107 | TC-023860 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 036 MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC108 | TC-023861 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 038 Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC109 | TC-023862 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 047 Soft Descriptor - Merchant Name, response table c… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC110 | TC-023863 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 059 Soft Descriptor - Merchant Country, response tabl… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC111 | TC-023864 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 060 Real Time Account Updater Request Data, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC112 | TC-023865 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 062 Customer Additional Information Data, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC113 | TC-023866 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 066 Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC114 | TC-023867 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 067 Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS16_TC115 | TC-023868 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Authorization Only with Table 080 Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS17 BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-024319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only NFC Payment Tokenization Data Segment (segment 123) omitted… → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS17_TC2 | TC-024326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only Purchase Card Data Segment (segment 104) omitted where p.72… → declined | single_rule_broken | Financial Transaction Request | 1 (FLAG) | ready |
| BR1_TS17_TC3 | TC-019466 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC4 | TC-019467 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 005 Point-of-Service Entry Mode Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC5 | TC-019468 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC6 | TC-019469 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC7 | TC-019470 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC8 | TC-019471 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 009 Partial Approval Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC9 | TC-019472 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 012 Market-Specific Data Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC10 | TC-019473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 014 User Data Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC11 | TC-019474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC12 | TC-019475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC13 | TC-019476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC14 | TC-019477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC15 | TC-019478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC16 | TC-019479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC17 | TC-019480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC18 | TC-019481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC19 | TC-019482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC20 | TC-019483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC21 | TC-019484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC22 | TC-019485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC23 | TC-019486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC24 | TC-019487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC25 | TC-019488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC26 | TC-019489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC27 | TC-019490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC28 | TC-021560 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC29 | TC-019492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC30 | TC-019493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 057 Soft Descriptor - Merchant City → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC31 | TC-019494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 058 Soft Descriptor - Merchant State → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC32 | TC-019495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC33 | TC-019496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC34 | TC-019497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC35 | TC-019498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 070 Anticipated Completion Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC36 | TC-019499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC37 | TC-019500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC38 | TC-019501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC39 | TC-021561 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC40 | TC-021562 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC41 | TC-021563 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Download Indicator → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC42 | TC-021564 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for EMV Card Sequence Number → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC43 | TC-021565 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for EMV Chip Data Component Name: Va… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC44 | TC-021566 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID… → approved [Authorization Only - Credential on File p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC45 | TC-021567 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID… → approved [Authorization Only - Credential on File p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC46 | TC-021568 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for ENT-TABLE-I-049-SUB-SUB_TABLE_ID… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC47 | TC-021569 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC48 | TC-021570 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only via the alternate path for Table 030 POS Condition Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS17_TC49 | TC-021571 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation with Sequence Number differ… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS17_TC50 | TC-021572 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation with Terminal Identifier di… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS17_TC51 | TC-021573 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Encrypted PIN Block Data sent where it is… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC52 | TC-021574 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Information Byte sent where it is not all… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC53 | TC-021575 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Payment Tender Type sent where it is not… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC54 | TC-021576 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Service Level sent where it is not allowed → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | ready |
| BR1_TS17_TC55 | TC-021577 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC56 | TC-021578 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC57 | TC-021579 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC58 | TC-021580 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC59 | TC-021581 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC60 | TC-021582 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC61 | TC-021583 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC62 | TC-021584 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC63 | TC-021585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC64 | TC-021586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC65 | TC-021587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | needs_client_input |
| BR1_TS17_TC66 | TC-021588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC67 | TC-021589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC68 | TC-021590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC69 | TC-021591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with initiation = M → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC70 | TC-021592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Standard Message Data Segment (segment 100) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC71 | TC-021593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Purchase Card Data Segment (segment 104) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC72 | TC-021594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional NFC Payment Tokenization Data Segment (segmen… → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC73 | TC-021595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Enhanced Fleet Request (segment 145) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC74 | TC-022869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with optional Network Token Data Request (segment 153) → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC75 | TC-021597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Variable Information Data Segment (segment 111) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC76 | TC-022870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with special payment = I → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC77 | TC-022871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with special payment = R → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC78 | TC-022872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC79 | TC-023869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS17_TC80 | TC-023870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Authorization Only (first card partly approved, balan… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC81 | TC-023871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation after 72 hours (at the 72 hour limit) → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS17_TC82 | TC-023872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation after 73 hours (beyond the… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC83 | TC-023873 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS17_TC84 | TC-023874 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS17_TC85 | TC-023875 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Credential on File Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC86 | TC-023876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC87 | TC-023877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Additional Information Data Layouts returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC88 | TC-023878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Additional Information Data Segment returned, content per… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC89 | TC-023879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (BUYPASS Card Type Code, PIN on Receipt Information returne… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC90 | TC-023880 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC91 | TC-023881 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS17_TC92 | TC-023882 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Cardholder Additional Information Result Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC93 | TC-023883 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Date and Time Data Segment returned, content per p.195) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC94 | TC-023884 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Dial String Data Segment returned, content per p.189) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC95 | TC-023885 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC96 | TC-023886 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC97 | TC-023887 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Merchant Advice Code, DAF Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC98 | TC-023888 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Merchant Data Segment returned, content per p.713-716) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC99 | TC-023889 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Network Token Data Response returned, content per p.339-34… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC100 | TC-023890 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC101 | TC-023891 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Real Time Account Updater Response Segment returned, conte… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC102 | TC-023892 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Software Dial Load Data Segment returned, content per p.18… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC103 | TC-023893 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Store and Forward Data Segment returned, content per p.83-… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC104 | TC-023894 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC105 | TC-023895 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Attributes Segment returned, content per p.104… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC106 | TC-023896 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Attributes Segment returned, content per p.46-… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC107 | TC-023897 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Attributes Segment returned, content per p.713) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC108 | TC-023898 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC109 | TC-023899 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (Transaction Link Identifier, Transaction Link Action Indic… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC110 | TC-023900 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | ready |
| BR1_TS17_TC111 | TC-023901 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 001 UPC Information, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC112 | TC-023902 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 002 Program Identifier Information, response tab… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC113 | TC-023903 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 017 Suspected Fraud Reversal Indicator Informati… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC114 | TC-023904 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 018 Card Acceptor Terminal Identifier Informatio… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC115 | TC-023905 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 021 Settlement Data Acceptance Flag, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC116 | TC-023906 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 034 Name Verification Service (NVS) Information,… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC117 | TC-023907 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 036 MasterCard UCAF Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC118 | TC-023908 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 038 Paydiant Tender ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC119 | TC-023909 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 047 Soft Descriptor - Merchant Name, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC120 | TC-023910 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 059 Soft Descriptor - Merchant Country, response… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC121 | TC-023911 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 060 Real Time Account Updater Request Data, resp… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC122 | TC-023912 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 062 Customer Additional Information Data, respon… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC123 | TC-023913 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 066 Digital Commerce Data, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC124 | TC-023914 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 067 Anticipated Amount, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC125 | TC-023915 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Authorization Only with Table 080 Staged Wallet ID, response table checked → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-019585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-019586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC3 | TC-019587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC4 | TC-019588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC5 | TC-019589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC6 | TC-019590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 022 Originating Device Type Indi… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC7 | TC-019591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC8 | TC-019592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC9 | TC-019593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 031 Point-of-Service Terminal Ca… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC10 | TC-019594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 033 Re-Price Data Request Inform… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC11 | TC-019595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC12 | TC-019596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC13 | TC-019597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 040 Final Amount Indicator → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC14 | TC-019598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC15 | TC-019599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC16 | TC-019600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC17 | TC-019601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 048 MasterCard Authentication Da… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC18 | TC-019602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC19 | TC-019603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC20 | TC-019604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC21 | TC-019605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 053 Remote Commerce Acceptor Ide… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC22 | TC-021648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC23 | TC-019607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC24 | TC-019608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 063 Visa®/MC/AMEX Incremental Au… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC25 | TC-019609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC26 | TC-019610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 065 Merchant Payment Gateway ID… → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC27 | TC-019611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC28 | TC-019612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC29 | TC-019613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC30 | TC-021649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Approved Amount → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC31 | TC-021650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Card Type → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC32 | TC-021651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Download Indicat… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC33 | TC-021652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for EMV Card Sequenc… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC34 | TC-021653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for EMV Chip Data Co… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC35 | TC-021654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for ENT-TABLE-I-032-… → approved [Authorization Only - Contactless Mobile Commerce Device p.577 #1] | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC36 | TC-021655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for ENT-TABLE-I-032-… → approved [Authorization Only - Contactless Mobile Commerce Device p.577 #2] | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC37 | TC-021656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for ENT-TABLE-I-049-… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC38 | TC-021657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Service Level → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC39 | TC-021658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only via the alternate path for Table 030 POS Co… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC40 | TC-021659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation with Sequen… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC41 | TC-021660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation with Termin… → declined (to confirm) | single_rule_broken | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC42 | TC-021661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC43 | TC-021662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Information Byte sent whe… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC44 | TC-021663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Payment Tender Type sent… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC45 | TC-021664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Service Level sent where… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS18_TC46 | TC-021665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC47 | TC-021666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC48 | TC-021667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Partial Approval Indicator = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC49 | TC-021668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Payment Tender Type = AX → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC50 | TC-021669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC51 | TC-021670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Tax Type 1 = GST → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC52 | TC-021671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Update Code = A → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC53 | TC-021672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with avs = true → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC54 | TC-021673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cof = initial → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC55 | TC-021674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvm = offline_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC56 | TC-021675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvm = online_pin → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC57 | TC-021676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvv2 = mismatch → approved (to confirm) | decision_table | 3 | 1: 2 (FLAG) | pending_sme |
| BR1_TS18_TC58 | TC-021677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with cvv2 = true → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC59 | TC-021678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with incremental = incremental → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC60 | TC-021679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with incremental = original → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC61 | TC-021680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Standard Message Data Segment… → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC62 | TC-021681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Purchase Card Data Segment (s… → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC63 | TC-021682 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Enhanced Fleet Request (segme… → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC64 | TC-022920 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with optional Network Token Data Request (s… → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC65 | TC-021684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Variable Information Data Segment (seg… → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC66 | TC-022921 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with track = 1 → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC67 | TC-023916 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS18_TC68 | TC-023917 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Authorization Only (first card partly… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC69 | TC-023918 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation after 24 hours (at the 2… → approved | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS18_TC70 | TC-023919 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation after 25 ho… → declined (to confirm) | time_based | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC71 | TC-023920 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation, then Totals Request (to… → approved | running_total | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS18_TC72 | TC-023921 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Totals Request (totals include it) → approved | running_total | 3 -> 990 | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS18_TC73 | TC-023922 | BUYPASS ATL105 MasterCard Credit MERCHANT 07 (CAT PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC74 | TC-023923 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC75 | TC-023924 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Additional Information Data Layouts return… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC76 | TC-023925 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Additional Information Data Segment return… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC77 | TC-023926 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (BUYPASS Card Type Code, PIN on Receipt Inf… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC78 | TC-023927 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (CVV Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC79 | TC-023928 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only, then Cancellation (card issuer declines) → declined | response_check | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC80 | TC-023929 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Cardholder Additional Information Result C… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC81 | TC-023930 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Date and Time Data Segment returned, conte… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC82 | TC-023931 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Dial String Data Segment returned, content… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC83 | TC-023932 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC84 | TC-023933 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Merchant Advice Code returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC85 | TC-023934 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Merchant Advice Code, DAF Indicator return… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC86 | TC-023935 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Network Token Data Response returned, cont… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC87 | TC-023936 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (PIN on Receipt Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC88 | TC-023937 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Real Time Account Updater Response Segment… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC89 | TC-023938 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Software Dial Load Data Segment returned,… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC90 | TC-023939 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Store and Forward Data Segment returned, c… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC91 | TC-023940 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Token Update First Use Indicator returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC92 | TC-023941 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned, c… → approved [cites p.104] | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC93 | TC-023942 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Attributes Segment returned, c… → approved [cites p.62] | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC94 | TC-023943 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC95 | TC-023944 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (Transaction Link Identifier, Transaction L… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC96 | TC-023945 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC97 | TC-023946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 001 UPC Information, response ta… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC98 | TC-023947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 002 Program Identifier Informati… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC99 | TC-023948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 017 Suspected Fraud Reversal Ind… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC100 | TC-023949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 018 Card Acceptor Terminal Ident… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC101 | TC-023950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 034 Name Verification Service (N… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC102 | TC-023951 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 036 MasterCard UCAF Data, respon… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC103 | TC-023952 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 038 Paydiant Tender ID, response… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC104 | TC-023953 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 047 Soft Descriptor - Merchant N… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC105 | TC-023954 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 059 Soft Descriptor - Merchant C… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC106 | TC-023955 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 060 Real Time Account Updater Re… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC107 | TC-023956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 062 Customer Additional Informat… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC108 | TC-023957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 066 Digital Commerce Data, respo… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC109 | TC-023958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 067 Anticipated Amount, response… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC110 | TC-023959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Authorization Only with Table 080 Staged Wallet ID, response t… → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS19 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-024313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only Required field Sequence Number omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS19_TC2 | TC-021730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only → approved | happy_path | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC3 | TC-021731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Sequence Number… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS19_TC4 | TC-021732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation with Terminal Identi… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | ready |
| BR1_TS19_TC5 | TC-022966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only with Network Token Data Request (segment 153) sent → approved | decision_table | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC6 | TC-023960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: no response expected | ready |
| BR1_TS19_TC7 | TC-023961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals incl… → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS19_TC8 | TC-023962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: no response expected | requires_review |
| BR1_TS19_TC9 | TC-023963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for Response Code (p.403) [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC10 | TC-023964 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-11 (p.404) [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC11 | TC-023965 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-12 (p.404) [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC12 | TC-023966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-7 (p.404) [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC13 | TC-023967 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: running total / state for rule BR-404-8 (p.404) [on Mail/Phone Authorization Only - Manual / Keyed] | running_total | - | n/a (no host message) | requires_review |
| BR1_TS19_TC14 | TC-023968 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: ordering for Electronic Mail Data Segment (segment 109) (p.67) [on Mail/Phone Authorization Only - Manual / Keyed] | ordering | - | n/a (no host message) | requires_review |
| BR1_TS19_TC15 | TC-023969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only on a POS terminal with offline pin → approved | pairwise | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC16 | TC-023970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC17 | TC-023971 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only, then Cancellation (card issuer declines) → declined | response_check | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | ready |
| BR1_TS19_TC18 | TC-023972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | ready |
| BR1_TS19_TC19 | TC-023973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Device check: Message Format Version Identifier as stated on p.184 [on Mail/Phone Authorization Only - Manual / Keyed] | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS19_TC20 | TC-023974 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 05 (EMV Contact Ch… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC21 | TC-023975 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 07 (EMV Contactles… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC22 | TC-023976 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 02 (Magnetic Strip… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC23 | TC-023977 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 91 (Contactless Ma… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC24 | TC-023978 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 79 (Chip Fallback,… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |
| BR1_TS19_TC25 | TC-023979 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Mail/Phone Authorization Only sent with entry mode 82 (Contactless Mo… → declined (to confirm) | single_rule_broken | B | 1: 1 (FLAG) | ready |

### BR1_TS20 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-021754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only → approved | happy_path | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-021755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation with Sequence Nu… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC3 | TC-021756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation with Terminal Id… → declined (to confirm) | single_rule_broken | B -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS20_TC4 | TC-022987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only with Network Token Data Request (segment 153) se… → approved | decision_table | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC5 | TC-023980 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation → approved | valid_flow | B -> S | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS20_TC6 | TC-023981 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation, then Totals Request (totals… → approved | running_total | B -> S -> 990 | 1: 2 (ASSERT) ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS20_TC7 | TC-023982 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Totals Request (totals include it) → approved | running_total | B -> 990 | 1: 2 (ASSERT) ; 2: no response expected | pending_sme |
| BR1_TS20_TC8 | TC-023983 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only on a POS terminal with offline pin → approved | pairwise | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC9 | TC-023984 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | response_check | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC10 | TC-023985 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only, then Cancellation (card issuer declines) → declined | response_check | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC11 | TC-023986 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Mail/Phone Authorization Only (card issuer declines) → declined | response_check | B | 1: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-022995 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS21_TC2 | TC-022996 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Sequence… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC3 | TC-022997 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return with Terminal… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS21_TC4 | TC-023987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS21_TC5 | TC-023988 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (tota… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS21_TC6 | TC-023989 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS21_TC7 | TC-023990 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS21_TC8 | TC-023991 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | requires_review |
| BR1_TS21_TC9 | TC-023992 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS22 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-024317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return Partial Approval Indicator one byte over its documented m… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS22_TC2 | TC-024348 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return EMV Chip Data Component Name: Value (ANSB ...255) The dat… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS22_TC3 | TC-024316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return Partial Approval Indicator value not in its documented va… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS22_TC4 | TC-024357 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return Variable Information Indicator value not in its documente… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS22_TC5 | TC-023004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS22_TC6 | TC-023005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Sequ… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC7 | TC-023006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return with Term… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS22_TC8 | TC-023993 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS22_TC9 | TC-023994 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return, then Totals Request (… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS22_TC10 | TC-023995 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS22_TC11 | TC-023996 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS22_TC12 | TC-023997 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | requires_review |
| BR1_TS22_TC13 | TC-023998 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS23 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-023013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS23_TC2 | TC-023014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Sequence… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS23_TC3 | TC-023015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return with Terminal… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS23_TC4 | TC-023999 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS23_TC5 | TC-024000 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Request (total… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS23_TC6 | TC-024001 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS23_TC7 | TC-024002 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS23_TC8 | TC-024003 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS23_TC9 | TC-024004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS24 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-023022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS24_TC2 | TC-023023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return wi… → declined (to confirm) [Merchandise Return - Contactless Magnetic Stripe p.546 #1] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS24_TC3 | TC-023024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return wi… → declined (to confirm) [Merchandise Return - Contactless Magnetic Stripe p.546 #2] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS24_TC4 | TC-024005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS24_TC5 | TC-024006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return, then Totals Re… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS24_TC6 | TC-024007 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS24_TC7 | TC-024008 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS24_TC8 | TC-024009 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return (card issuer de… → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS24_TC9 | TC-024010 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS25 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-023031 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS25_TC2 | TC-023032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Sequence N… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS25_TC3 | TC-023033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return with Terminal I… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS25_TC4 | TC-024011 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS25_TC5 | TC-024012 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS25_TC6 | TC-024013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS25_TC7 | TC-024014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS25_TC8 | TC-024015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS25_TC9 | TC-024016 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS26 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-023040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return → approved | happy_path | 7 | 1: no response expected | ready |
| BR1_TS26_TC2 | TC-023041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return with Sequence Nu… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS26_TC3 | TC-023042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return with Terminal Id… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | ready |
| BR1_TS26_TC4 | TC-024017 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | ready |
| BR1_TS26_TC5 | TC-024018 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return, then Totals Request (totals… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | requires_review |
| BR1_TS26_TC6 | TC-024019 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS26_TC7 | TC-024020 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | ready |
| BR1_TS26_TC8 | TC-024021 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | ready |
| BR1_TS26_TC9 | TC-024022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | ready |

### BR1_TS27 BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-023049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return → approved | happy_path | 7 | 1: no response expected | pending_sme |
| BR1_TS27_TC2 | TC-023050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return with Sequen… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-023051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return with Termin… → declined (to confirm) | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS27_TC4 | TC-024023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS27_TC5 | TC-024024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return (no response), then Void of a Merchandise Return → approved | fault_recovery | 7 -> U | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS27_TC6 | TC-024025 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return, then Totals Request (to… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS27_TC7 | TC-024026 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS27_TC8 | TC-024027 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | pending_sme |
| BR1_TS27_TC9 | TC-024028 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return, then Void of a Merchandise Return (card issuer declines) → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS27_TC10 | TC-024029 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS28 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-023059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return → approved | happy_path | 7 | 1: no response expected | pending_sme |
| BR1_TS28_TC2 | TC-023060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Re… → declined (to confirm) [Merchandise Return - Contactless Mobile Commerce Device p.546 #1] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-023061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Re… → declined (to confirm) [Merchandise Return - Contactless Mobile Commerce Device p.546 #2] | single_rule_broken | 7 -> U | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS28_TC4 | TC-024030 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS28_TC5 | TC-024031 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return, then To… → approved | running_total | 7 -> U -> 990 | 1: no response expected ; 2: no response expected ; 3: no response expected | pending_sme |
| BR1_TS28_TC6 | TC-024032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Totals Request (totals include it) → approved | running_total | 7 -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS28_TC7 | TC-024033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | pending_sme |
| BR1_TS28_TC8 | TC-024034 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return (card is… → declined | response_check | 7 -> U | 1: no response expected ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS28_TC9 | TC-024035 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Merchandise Return (card issuer declines) → declined | response_check | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS29 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-024036 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS30 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-024315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Balance Inquiry Required field Partial Approval Indicator omitted → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | ready |
| BR1_TS30_TC2 | TC-024037 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contactless Chip Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS31 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-024038 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Magnetic Stripe Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS32 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-024039 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Magnetic Stripe Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS33 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-024040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS34 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-024041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Chip Fallback Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | ready |

### BR1_TS35 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-023074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry → approved | happy_path | E | 1: no response expected | pending_sme |
| BR1_TS35_TC2 | TC-024042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry, then Totals Request (totals include it) → approved | running_total | E -> 990 | 1: no response expected ; 2: no response expected | pending_sme |
| BR1_TS35_TC3 | TC-024043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | pending_sme |
| BR1_TS35_TC4 | TC-024044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry (card issuer declines) → declined | response_check | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS36 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-024045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Contactless Mobile Commerce Device Balance Inquiry on a POS terminal with offline pin → approved | pairwise | E | 1: no response expected | pending_sme |

### BR1_TS37 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS37_TC1 | TC-024271 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS37_TC2 | TC-024272 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS37_TC3 | TC-024273 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS37_TC4 | TC-024274 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS37_TC5 | TC-024275 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |
| BR1_TS37_TC6 | TC-024276 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS38 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS38_TC1 | TC-024361 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS39 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS39_TC1 | TC-024362 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS40 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS40_TC1 | TC-024363 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS41 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS41_TC1 | TC-024364 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS42 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS42_TC1 | TC-024365 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS43 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS43_TC1 | TC-024366 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS44 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS44_TC1 | TC-024277 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC2 | TC-024278 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC3 | TC-024279 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC4 | TC-024280 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC5 | TC-024281 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC6 | TC-024282 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC7 | TC-024283 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC8 | TC-024284 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC9 | TC-024285 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC10 | TC-024286 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC11 | TC-024287 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC12 | TC-024288 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC13 | TC-024289 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC14 | TC-024290 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC15 | TC-024291 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC16 | TC-024292 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC17 | TC-024293 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC18 | TC-024294 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC19 | TC-024295 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC20 | TC-024296 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC21 | TC-024297 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC22 | TC-024298 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC23 | TC-024299 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC24 | TC-024300 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC25 | TC-024301 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC26 | TC-024302 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC27 | TC-024303 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS44_TC28 | TC-024304 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS44_TC29 | TC-024305 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS45 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS45_TC1 | TC-024367 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS EMV Contactless Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS46 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS46_TC1 | TC-024306 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Device check: device behaviour: rule BR-64-6 (p.64) of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS46_TC2 | TC-024307 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Device check: ENT-TABLE-I-079-SUB-SUB_TABLE_ID_09 as stated on p.730 | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS47 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS47_TC1 | TC-024368 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Magnetic Stripe Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS48 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS48_TC1 | TC-024369 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Manual / Keyed Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS49 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS49_TC1 | TC-024370 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Chip Fallback Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS50 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS50_TC1 | TC-024371 | BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality · attended POS Contactless Mobile Commerce Device Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS51 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS51_TC1 | TC-024333 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture Product Amount contains a character outside its character type… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS51_TC2 | TC-024359 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture Required field Service Level sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS51_TC3 | TC-024338 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture EMV Request Data Segment (segment 130) omitted where p.85 requi… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | blocked_no_test_data |
| BR1_TS51_TC4 | TC-021862 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC5 | TC-024056 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS51_TC6 | TC-024057 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS51_TC7 | TC-024058 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS51_TC8 | TC-024059 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the original i… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS51_TC9 | TC-024060 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS51_TC10 | TC-024061 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |
| BR1_TS51_TC11 | TC-024062 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture on a POS terminal with offline pin → approved | pairwise | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC12 | TC-024063 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS51_TC13 | TC-024064 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 0 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS52 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS52_TC1 | TC-024346 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture Additional Information one byte over its documented maximum… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS52_TC2 | TC-024065 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS52_TC3 | TC-024066 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Purchase/Capture, then Purchase Reversal x2 (the second one after the origin… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS53 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS53_TC1 | TC-024067 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS53_TC2 | TC-024068 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the original is… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS54 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS54_TC1 | TC-024069 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS54_TC2 | TC-024070 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Purchase/Capture, then Purchase Reversal x2 (the second one after the… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS55 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS55_TC1 | TC-024071 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS55_TC2 | TC-024072 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Purchase/Capture, then Purchase Reversal x2 (the second one after the original is… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS56 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS56_TC1 | TC-024073 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS56_TC2 | TC-024074 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Purchase/Capture, then Purchase Reversal x2 (the second one after the original is a… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS57 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS57_TC1 | TC-024075 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase Reversal with no original transaction → declined | invalid_transition | 8 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS57_TC2 | TC-024076 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Purchase/Capture, then Purchase Reversal x2 (the second one af… → declined | invalid_transition | 0 -> 8 -> 8 | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |

### BR1_TS58 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS58_TC1 | TC-024341 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only Account Number exactly at its documented maximum length (24 b… → declined | single_rule_broken | EMV Financial Transaction Request | 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC2 | TC-024330 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only Fleet Data Segment (segment 101) omitted where p.23 requires… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | needs_client_input |
| BR1_TS58_TC3 | TC-024331 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only Purchase Card Data Segment (segment 104) omitted where p.24 r… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (FLAG) | needs_client_input |
| BR1_TS58_TC4 | TC-019788 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC5 | TC-019789 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 006 Currency Code Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC6 | TC-019790 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 007 SIC Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC7 | TC-019791 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 008 Balance Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC8 | TC-019792 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC9 | TC-019793 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC10 | TC-019794 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 027 Customer Name → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC11 | TC-019795 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC12 | TC-019796 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC13 | TC-019797 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC14 | TC-019798 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC15 | TC-019799 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC16 | TC-019800 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 041 Country Subdivision → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC17 | TC-019801 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 042 Cardholder Language → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC18 | TC-019802 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC19 | TC-019803 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC20 | TC-019804 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 050 Additional Security Code → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC21 | TC-019805 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 051 Additional Card ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC22 | TC-019806 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC23 | TC-019807 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC24 | TC-021884 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC25 | TC-019809 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC26 | TC-019810 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC27 | TC-019811 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 064 Service Location Information → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC28 | TC-019812 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC29 | TC-019813 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC30 | TC-019814 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 075 Discount Program Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC31 | TC-019815 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC32 | TC-021885 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS58_TC33 | TC-021886 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS58_TC34 | TC-021887 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only via the alternate path for TransArmor Load Data Segment (segm… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS58_TC35 | TC-021888 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Encrypted PIN Block Data sent where it is n… → declined (to confirm) | single_rule_broken | 3 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS58_TC36 | TC-021889 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS58_TC37 | TC-021890 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC38 | TC-021891 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Information Byte = 0 → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC39 | TC-021892 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Service Level = F → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC40 | TC-021893 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with optional EMV Request Data Segment (segment 130) → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC41 | TC-021894 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS58_TC42 | TC-021895 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC43 | TC-024077 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 3 -> S | 1: 2 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS58_TC44 | TC-024078 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 3 -> 3 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC45 | TC-024079 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is a… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS58_TC46 | TC-024080 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Purchase/Capture (not allowed after this o… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS58_TC47 | TC-024081 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS58_TC48 | TC-024082 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS58_TC49 | TC-024083 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS58_TC50 | TC-024084 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (held offline 55 minutes) (at the 55 minute limi… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS58_TC51 | TC-024085 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (held offline 56 minutes) (beyond the 55 minute… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS58_TC52 | TC-024086 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (EMV Response Data Segment returned, content per p.44-209) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC53 | TC-024087 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (EMV Response Data Segment returned, content per p.89-176) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC54 | TC-024088 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC55 | TC-024089 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC56 | TC-024090 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC57 | TC-024091 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Store and Forward Data Segment returned, content per p.83-14… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC58 | TC-024092 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Transaction Attributes Segment returned, content per p.104-1… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC59 | TC-024093 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Transaction Attributes Segment returned, content per p.46-60… → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC60 | TC-024094 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS58_TC61 | TC-024095 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 3 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS59 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS59_TC1 | TC-024339 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only Fuel Purchase Amount one byte over its documented maximum… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS59_TC2 | TC-021915 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS59_TC3 | TC-021916 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS59_TC4 | TC-021917 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only via the alternate path for TransArmor Load Data Segment (… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS59_TC5 | TC-021918 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS59_TC6 | TC-021919 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS59_TC7 | TC-021920 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS59_TC8 | TC-024096 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS59_TC9 | TC-024097 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only, then Purchase/Capture (not allowed after th… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS59_TC10 | TC-024098 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS59_TC11 | TC-024099 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS59_TC12 | TC-024100 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS59_TC13 | TC-024101 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (held offline 55 minutes) (at the 55 minute… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS59_TC14 | TC-024102 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (held offline 56 minutes) (beyond the 55 min… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS59_TC15 | TC-024103 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS59_TC16 | TC-024104 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS60 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS60_TC1 | TC-021930 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS60_TC2 | TC-021931 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS60_TC3 | TC-021932 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Segment (segme… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS60_TC4 | TC-021933 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS60_TC5 | TC-021934 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS60_TC6 | TC-021935 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS60_TC7 | TC-024105 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: EMV Request Data Segment (segment 130) of FISERV-EMV-GUIDE | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC8 | TC-024106 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is al… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS60_TC9 | TC-024107 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed after this or… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS60_TC10 | TC-024108 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS60_TC11 | TC-024109 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS60_TC12 | TC-024110 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS60_TC13 | TC-024111 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS60_TC14 | TC-024112 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the 55 minute l… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS60_TC15 | TC-024113 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: running total / state for Moneris Day End Batch Balance Segment Format (Request) (segment 139)… | running_total | - | n/a (no host message) | requires_review |
| BR1_TS60_TC16 | TC-024114 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: arithmetic consistency for Product Code Data Segment (segment 102) (p.3) | arithmetic | - | n/a (no host message) | requires_review |
| BR1_TS60_TC17 | TC-024115 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS60_TC18 | TC-024116 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS60_TC19 | TC-024117 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.104-128 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC20 | TC-024118 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.129-134 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC21 | TC-024119 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.136-180 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC22 | TC-024120 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.16-28 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC23 | TC-024121 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.29-41 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC24 | TC-024122 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.43-52 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC25 | TC-024123 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.53-69 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC26 | TC-024124 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.69-73 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC27 | TC-024125 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.74-95 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC28 | TC-024126 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 12 checks p.95-104 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC29 | TC-024127 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device behaviour: 2 checks p.183 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS60_TC30 | TC-024128 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Device check: device checkpoint for Service Level (p.49) | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS61 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS61_TC1 | TC-021960 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS61_TC2 | TC-021961 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS61_TC3 | TC-021962 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Se… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS61_TC4 | TC-021963 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS61_TC5 | TC-021964 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS61_TC6 | TC-021965 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS61_TC7 | TC-024129 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the or… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS61_TC8 | TC-024130 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed a… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS61_TC9 | TC-024131 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS61_TC10 | TC-024132 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit o… → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS61_TC11 | TC-024133 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the lim… → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS61_TC12 | TC-024134 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS61_TC13 | TC-024135 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS61_TC14 | TC-024136 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS61_TC15 | TC-024137 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS62 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS62_TC1 | TC-024335 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only Required field Account Number omitted → declined | single_rule_broken | Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS62_TC2 | TC-021975 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS62_TC3 | TC-021976 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS62_TC4 | TC-021977 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for TransArmor Load Data Segment (segmen… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS62_TC5 | TC-021978 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only via the alternate path for rule BR-BUYPASS-PIPS-63-1 (p.63) → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS62_TC6 | TC-021979 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS62_TC7 | TC-021980 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS62_TC8 | TC-021981 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS62_TC9 | TC-024138 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only, then Cancellation x2 (the second one after the original is alr… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS62_TC10 | TC-024139 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only, then Purchase/Capture (not allowed after this ori… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS62_TC11 | TC-024140 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS62_TC12 | TC-024141 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS62_TC13 | TC-024142 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS62_TC14 | TC-024143 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS62_TC15 | TC-024144 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (held offline 56 minutes) (beyond the 55 minute li… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS62_TC16 | TC-024145 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS62_TC17 | TC-024146 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS63 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS63_TC1 | TC-021991 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS63_TC2 | TC-021992 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS63_TC3 | TC-021993 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only via the alternate path for TransArmor Load Data Segment (segment… → approved | alternate_path | 3 | 1: 2 (ASSERT) | requires_review |
| BR1_TS63_TC4 | TC-021994 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS63_TC5 | TC-021995 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS63_TC6 | TC-021996 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS63_TC7 | TC-024147 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only, then Cancellation x2 (the second one after the original is alre… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS63_TC8 | TC-024148 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only, then Purchase/Capture (not allowed after this orig… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS63_TC9 | TC-024149 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS63_TC10 | TC-024150 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS63_TC11 | TC-024151 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS63_TC12 | TC-024152 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | requires_review |
| BR1_TS63_TC13 | TC-024153 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (held offline 56 minutes) (beyond the 55 minute lim… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | requires_review |
| BR1_TS63_TC14 | TC-024154 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS63_TC15 | TC-024155 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS64 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS64_TC1 | TC-022006 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only via the alternate path for ENT-TABLE-I-032-… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS64_TC2 | TC-022007 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS64_TC3 | TC-022008 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only via the alternate path for TransArmor Load… → approved | alternate_path | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS64_TC4 | TC-022009 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS64_TC5 | TC-022010 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 3 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS64_TC6 | TC-022011 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only with Product Code Data Segment (segment 102… → approved | decision_table | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS64_TC7 | TC-024156 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after… → declined | invalid_transition | 3 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS64_TC8 | TC-024157 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (not al… → declined (to confirm) | invalid_transition | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS64_TC9 | TC-024158 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS64_TC10 | TC-024159 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (resent) sent 3 times (at the… → approved (to confirm) | idempotency_retry | 3 | 1: 2 (FLAG) | pending_sme |
| BR1_TS64_TC11 | TC-024160 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (resent) sent 4 times (beyond… → declined (to confirm) | idempotency_retry | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS64_TC12 | TC-024161 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (held offline 55 minutes) (at… → approved (to confirm) | time_based | 3 | 1: 2 (FLAG) | pending_sme |
| BR1_TS64_TC13 | TC-024162 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (held offline 56 minutes) (bey… → declined (to confirm) | time_based | 3 | 1: 1 (FLAG) | pending_sme |
| BR1_TS64_TC14 | TC-024163 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS64_TC15 | TC-024164 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Authorization Only (Reserved Indicator Information returned) → approved | response_check | 3 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS65 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS65_TC1 | TC-024342 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return Account Number one byte over its documented maximum length (2… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS65_TC2 | TC-023198 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return → approved | happy_path | 7 | 1: no response expected | needs_client_input |
| BR1_TS65_TC3 | TC-024165 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return → approved | valid_flow | 7 -> U | 1: no response expected ; 2: no response expected | requires_review |
| BR1_TS65_TC4 | TC-024166 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one after t… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | requires_review |
| BR1_TS65_TC5 | TC-024167 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS65_TC6 | TC-024168 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Merchandise Return on a POS terminal with offline pin → approved | pairwise | 7 | 1: no response expected | needs_client_input |

### BR1_TS66 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS66_TC1 | TC-024337 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Merchandise Return Service Level value not in its documented valid values → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS66_TC2 | TC-024169 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Merchandise Return, then Void of a Merchandise Return x2 (the second one aft… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | requires_review |
| BR1_TS66_TC3 | TC-024170 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contactless Chip Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS67 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS67_TC1 | TC-024171 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second one after th… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS67_TC2 | TC-024172 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Magnetic Stripe Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS68 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS68_TC1 | TC-024173 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Merchandise Return, then Void of a Merchandise Return x2 (the second… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS68_TC2 | TC-024174 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Magnetic Stripe Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS69 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS69_TC1 | TC-024175 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Merchandise Return, then Void of a Merchandise Return x2 (the second one after the… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS69_TC2 | TC-024176 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Manual / Keyed Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS70 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS70_TC1 | TC-024177 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Merchandise Return, then Void of a Merchandise Return x2 (the second one after the… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS70_TC2 | TC-024178 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Chip Fallback Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS71 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS71_TC1 | TC-024179 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Merchandise Return, then Void of a Merchandise Return x2 (the… → declined | invalid_transition | 7 -> U -> U | 1: no response expected ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS71_TC2 | TC-024180 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS Contactless Mobile Commerce Device Void of a Merchandise Return with no original transaction → declined | invalid_transition | U | 1: 1 (ASSERT) | pending_sme |

### BR1_TS72 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS72_TC1 | TC-024334 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture Prompt Code one byte over its documented maximum length (4) → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS72_TC2 | TC-024347 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture Customer Data contains a character outside its character type (… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS72_TC3 | TC-024354 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture Required field EMV Chip Data Component Name: Value (ANSB ...255… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS72_TC4 | TC-022038 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS72_TC5 | TC-024181 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture with incremental = completion → approved | decision_table | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS72_TC6 | TC-024182 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 4 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS72_TC7 | TC-024183 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 4 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS72_TC8 | TC-024184 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |
| BR1_TS72_TC9 | TC-024185 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture (Merchant Data Segment returned, content per p.6-715) → approved | response_check | 4 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS72_TC10 | TC-024186 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Purchase/Capture (card issuer declines) → declined | response_check | 4 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS73 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS73_TC1 | TC-024336 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Purchase/Capture Service Level exactly at its documented maximum length (1 b… → declined | single_rule_broken | EMV Financial Transaction Request | 0 (ASSERT) | needs_client_input |
| BR1_TS73_TC2 | TC-024345 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Purchase/Capture Information Byte one byte over its documented maximum lengt… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS73_TC3 | TC-024340 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Purchase/Capture Fuel Purchase Amount contains a character outside its chara… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS73_TC4 | TC-024344 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Purchase/Capture Required field Information Byte sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |

### BR1_TS74 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS74_TC1 | TC-022172 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS75 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS75_TC1 | TC-022173 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS76 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS76_TC1 | TC-022174 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 4 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS77 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS77_TC1 | TC-024343 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only Required field Prompt Code sent empty → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS77_TC2 | TC-019956 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC3 | TC-019957 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 006 Currency Code Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC4 | TC-019958 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 007 SIC Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC5 | TC-019959 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 008 Balance Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC6 | TC-019960 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 019 Recharge Type Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC7 | TC-019961 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 022 Originating Device Type Indicator Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC8 | TC-019962 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 027 Customer Name → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC9 | TC-019963 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 029 Universal Unique Identifier → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC10 | TC-019964 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 031 Point-of-Service Terminal Capability Codes → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC11 | TC-019965 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 033 Re-Price Data Request Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC12 | TC-019966 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 037 MCX Checkout Token → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC13 | TC-019967 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 039 Fraud Enhanced Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC14 | TC-019968 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 041 Country Subdivision → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC15 | TC-019969 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 042 Cardholder Language → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC16 | TC-019970 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 046 Previous Transaction ID → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC17 | TC-019971 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 048 MasterCard Authentication Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC18 | TC-019972 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 050 Additional Security Code → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC19 | TC-019973 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 051 Additional Card ID → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC20 | TC-019974 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 052 Additional TransArmor Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC21 | TC-019975 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 053 Remote Commerce Acceptor Identifier → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC22 | TC-022045 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 054 Merchant Tran ID → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC23 | TC-019977 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 055 Experience Provider Value → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC24 | TC-019978 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC25 | TC-019979 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 064 Service Location Information → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC26 | TC-019980 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 065 Merchant Payment Gateway ID Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC27 | TC-019981 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 073 Future Transaction Amount → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC28 | TC-019982 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 075 Discount Program Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC29 | TC-019983 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Table 076 Discount Line-Item Data → approved | happy_path | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC30 | TC-022046 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS77_TC31 | TC-022047 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS77_TC32 | TC-022048 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only via the alternate path for TransArmor Load Data Segment (segm… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS77_TC33 | TC-022049 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Encrypted PIN Block Data sent where it is n… → declined (to confirm) | single_rule_broken | 5 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS77_TC34 | TC-022050 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS77_TC35 | TC-022051 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC36 | TC-022052 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Information Byte = 0 → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC37 | TC-022053 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Service Level = F → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC38 | TC-022054 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with optional EMV Request Data Segment (segment 130) → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC39 | TC-022055 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS77_TC40 | TC-022056 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC41 | TC-024187 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only, then Cancellation → approved | valid_flow | 5 -> S | 1: 2 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS77_TC42 | TC-024188 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance… → approved | valid_flow | 5 -> 5 | 1: 2 (FLAG) ; 2: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC43 | TC-024189 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only, then Cancellation x2 (the second one after the original is a… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS77_TC44 | TC-024190 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only, then Purchase/Capture (not allowed after this o… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS77_TC45 | TC-024191 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS77_TC46 | TC-024192 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS77_TC47 | TC-024193 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS77_TC48 | TC-024194 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (held offline 55 minutes) (at the 55 minute limi… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS77_TC49 | TC-024195 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (held offline 56 minutes) (beyond the 55 minute… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS77_TC50 | TC-024196 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only on a CAT terminal with offline pin → approved | pairwise | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC51 | TC-024197 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (EMV Response Data Segment returned, content per p.44-209) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC52 | TC-024198 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (EMV Response Data Segment returned, content per p.89-176) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC53 | TC-024199 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Host Prompts Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC54 | TC-024200 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC55 | TC-024201 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC56 | TC-024202 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Store and Forward Data Segment returned, content per p.83-14… → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC57 | TC-024203 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Transaction Attributes Segment returned, content per p.104-1… → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC58 | TC-024204 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Transaction Attributes Segment returned, content per p.46-60… → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC59 | TC-024205 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (Transaction Identifier returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS77_TC60 | TC-024206 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contact Chip Authorization Only (card issuer declines) → declined | response_check | 5 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS78 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS78_TC1 | TC-024332 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only Product Amount one byte over its documented maximum lengt… → declined | single_rule_broken | EMV Financial Transaction Request | 1 (ASSERT) | needs_client_input |
| BR1_TS78_TC2 | TC-022077 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS78_TC3 | TC-022078 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS78_TC4 | TC-022079 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only via the alternate path for TransArmor Load Data Segment (… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS78_TC5 | TC-022080 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS78_TC6 | TC-022081 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS78_TC7 | TC-022082 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS78_TC8 | TC-024207 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only, then Cancellation x2 (the second one after the original… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS78_TC9 | TC-024208 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only, then Purchase/Capture (not allowed after th… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS78_TC10 | TC-024209 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS78_TC11 | TC-024210 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS78_TC12 | TC-024211 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS78_TC13 | TC-024212 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only (held offline 55 minutes) (at the 55 minute… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS78_TC14 | TC-024213 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only (held offline 56 minutes) (beyond the 55 min… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS78_TC15 | TC-024214 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS78_TC16 | TC-024215 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT EMV Contactless Chip Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS79 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS79_TC1 | TC-022092 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS79_TC2 | TC-022093 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS79_TC3 | TC-022094 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Segment (segme… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS79_TC4 | TC-022095 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS79_TC5 | TC-022096 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS79_TC6 | TC-022097 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS79_TC7 | TC-024216 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: EMV Request Data Segment (segment 130) of FISERV-EMV-GUIDE | soft_constraint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC8 | TC-024217 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the original is al… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS79_TC9 | TC-024218 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed after this or… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS79_TC10 | TC-024219 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS79_TC11 | TC-024220 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit of 3) → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS79_TC12 | TC-024221 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS79_TC13 | TC-024222 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55 minute limit) → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS79_TC14 | TC-024223 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the 55 minute l… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS79_TC15 | TC-024224 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: running total / state for Moneris Day End Batch Balance Segment Format (Request) (segment 139)… | running_total | - | n/a (no host message) | requires_review |
| BR1_TS79_TC16 | TC-024225 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: arithmetic consistency for Product Code Data Segment (segment 102) (p.3) | arithmetic | - | n/a (no host message) | requires_review |
| BR1_TS79_TC17 | TC-024226 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS79_TC18 | TC-024227 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS79_TC19 | TC-024228 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.104-128 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC20 | TC-024229 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.129-134 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC21 | TC-024230 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.136-180 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC22 | TC-024231 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.16-28 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC23 | TC-024232 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.29-41 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC24 | TC-024233 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.43-52 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC25 | TC-024234 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.53-69 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC26 | TC-024235 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.69-73 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC27 | TC-024236 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.74-95 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC28 | TC-024237 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 12 checks p.95-104 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC29 | TC-024238 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 2 checks p.183 of BUYPASS-PIPS | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC30 | TC-024239 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device behaviour: 4 checks p.61-63 of ATL105 | device_checkpoint | - | n/a (no host message) | requires_review |
| BR1_TS79_TC31 | TC-024240 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Device check: device checkpoint for Service Level (p.49) | device_checkpoint | - | n/a (no host message) | requires_review |

### BR1_TS80 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS80_TC1 | TC-022123 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only via the alternate path for ENT-TABLE-I-032-SUB-SUB… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS80_TC2 | TC-022124 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS80_TC3 | TC-022125 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only via the alternate path for TransArmor Load Data Se… → approved | alternate_path | 5 | 1: 2 (ASSERT) | requires_review |
| BR1_TS80_TC4 | TC-022126 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS80_TC5 | TC-022127 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS80_TC6 | TC-022128 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only with Product Code Data Segment (segment 102) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS80_TC7 | TC-024241 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only, then Cancellation x2 (the second one after the or… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS80_TC8 | TC-024242 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only, then Purchase/Capture (not allowed a… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | needs_client_input |
| BR1_TS80_TC9 | TC-024243 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS80_TC10 | TC-024244 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only (resent) sent 3 times (at the limit o… → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS80_TC11 | TC-024245 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only (resent) sent 4 times (beyond the lim… → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS80_TC12 | TC-024246 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only (held offline 55 minutes) (at the 55… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | requires_review |
| BR1_TS80_TC13 | TC-024247 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only (held offline 56 minutes) (beyond the… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | requires_review |
| BR1_TS80_TC14 | TC-024248 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS80_TC15 | TC-024249 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Magnetic Stripe Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | needs_client_input |

### BR1_TS81 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS81_TC1 | TC-022138 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only via the alternate path for ENT-TABLE-I-032-… → approved | alternate_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS81_TC2 | TC-022139 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only via the alternate path for Prompt Code → approved | alternate_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS81_TC3 | TC-022140 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only via the alternate path for TransArmor Load… → approved | alternate_path | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS81_TC4 | TC-022141 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Cancellation (not allowed here) → declined | single_rule_broken | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS81_TC5 | TC-022142 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only with Fleet Data Segment (segment 101) sent → approved | decision_table | 5 | 1: 2 (ASSERT) | blocked_no_test_data |
| BR1_TS81_TC6 | TC-022143 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only with Product Code Data Segment (segment 102… → approved | decision_table | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS81_TC7 | TC-024250 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only, then Cancellation x2 (the second one after… → declined | invalid_transition | 5 -> S -> S | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS81_TC8 | TC-024251 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only, then Purchase/Capture (not al… → declined (to confirm) | invalid_transition | 5 -> 4 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS81_TC9 | TC-024252 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Cancellation with no original transaction → declined | invalid_transition | S | 1: 1 (ASSERT) | pending_sme |
| BR1_TS81_TC10 | TC-024253 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only (resent) sent 3 times (at the… → approved (to confirm) | idempotency_retry | 5 | 1: 2 (FLAG) | pending_sme |
| BR1_TS81_TC11 | TC-024254 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only (resent) sent 4 times (beyond… → declined (to confirm) | idempotency_retry | 5 | 1: 1 (FLAG) | pending_sme |
| BR1_TS81_TC12 | TC-024255 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only (held offline 55 minutes) (at… → approved (to confirm) | time_based | 5 | 1: 2 (FLAG) | pending_sme |
| BR1_TS81_TC13 | TC-024256 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only (held offline 56 minutes) (bey… → declined (to confirm) | time_based | 5 | 1: 1 (FLAG) | pending_sme |
| BR1_TS81_TC14 | TC-024257 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only (Host-based Purchase Restriction returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS81_TC15 | TC-024258 | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · unattended CAT Contactless Mobile Commerce Device Authorization Only (Reserved Indicator Information returned) → approved | response_check | 5 | 1: 2 (ASSERT) | pending_sme |

### BR1_TS82 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS82_TC1 | TC-024259 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS82_TC2 | TC-024260 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS82_TC3 | TC-024261 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS82_TC4 | TC-024262 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS83 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS83_TC1 | TC-022175 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS84 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS84_TC1 | TC-022176 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS85 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS85_TC1 | TC-022177 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS86 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS86_TC1 | TC-022178 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS87 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS87_TC1 | TC-022179 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS88 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS88_TC1 | TC-022180 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5542 · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS89 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS89_TC1 | TC-024308 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS89_TC2 | TC-024309 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS89_TC3 | TC-024310 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS89_TC4 | TC-024311 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS90 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS90_TC1 | TC-024372 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS91 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS91_TC1 | TC-024373 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS92 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS92_TC1 | TC-024374 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS93 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS93_TC1 | TC-024375 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS94 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS94_TC1 | TC-024376 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS95 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS95_TC1 | TC-024377 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 5552 · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS96 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS96_TC1 | TC-024263 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS96_TC2 | TC-024264 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS96_TC3 | TC-024265 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS96_TC4 | TC-024266 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS97 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS97_TC1 | TC-022181 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS98 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS98_TC1 | TC-022182 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS99 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS99_TC1 | TC-022183 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS100 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS100_TC1 | TC-022184 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS101 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS101_TC1 | TC-022185 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS102 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS102_TC1 | TC-022186 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6051 · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

### BR1_TS103 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS103_TC1 | TC-024267 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS EMV Contact Chip Purchase/Capture, then Purchase Reversal → approved | valid_flow | 0 -> 8 | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS103_TC2 | TC-024268 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | valid_flow | 0 -> Z | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |
| BR1_TS103_TC3 | TC-024269 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS EMV Contact Chip Purchase/Capture, then Time-out Reversal x2 (the second one after t… → declined (to confirm) | invalid_transition | 0 -> Z -> Z | 1: 0 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS103_TC4 | TC-024270 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm) | invalid_transition | Z | 1: 1 (FLAG) | needs_client_input |

### BR1_TS104 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS104_TC1 | TC-022187 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS EMV Contactless Chip Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS105 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS105_TC1 | TC-022188 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS106 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS106_TC1 | TC-022189 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS Contactless Magnetic Stripe Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS107 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS107_TC1 | TC-022190 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS Manual / Keyed Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS108 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS108_TC1 | TC-022191 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS Chip Fallback Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | needs_client_input |

### BR1_TS109 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS109_TC1 | TC-022192 | BUYPASS ATL105 MasterCard Credit SAMPLE Merchant category 6211 · attended POS Contactless Mobile Commerce Device Purchase/Capture → approved | happy_path | 0 | 1: 0 (ASSERT) | pending_sme |

## Planned but not generated

- BR1_TS9 G-PW-b0807763ec: M01: template runs on ['CAT'] terminals only; M07 (fallback): Mail/Phone legs are attended POS only (p.83); M02 (fallback): template runs on ['CAT'] terminals only
- BR1_TS10 G-PW-b0807763ec: M01: template runs on ['CAT'] terminals only; M07 (fallback): Mail/Phone legs are attended POS only (p.83); M02 (fallback): template runs on ['CAT'] terminals only
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
- BR1_TS58 G-PW-afd238a6b1: S-PETROLEUM-ATTENDED-01: template runs on ['CAT'] terminals only
- BR1_TS72 G-VF-35e1056df6: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS72 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS72 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS72 G-PW-5e4736a61a: S-PETROLEUM-UNATTENDED-01: template runs on ['POS'] terminals only
- BR1_TS73 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS73 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS74 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS74 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS75 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS75 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS76 G-IT-65b0a0c6f9: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)
- BR1_TS76 G-IT-126b74fca3: S-PETROLEUM-UNATTENDED-01: a leg category is not CAT-allowed (p.83)

## Follow-on legs (reversal / void / cancellation ...)

34 of 34 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
