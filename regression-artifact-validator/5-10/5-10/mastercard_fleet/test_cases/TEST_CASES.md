# Test cases - BR1 BUYPASS ATL105 MasterCard Fleet

Build `mastercard-fleet-f8e91ab05e`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 24 |
| Test cases | 339 |
| By status | needs_client_input: 61, requires_review: 78, ready: 36, pending_sme: 164 |
| By polarity | Positive: 233, Negative: 106 |
| Verification | PASS: 321, NOT_APPLICABLE: 18 |
| Verification level | PASS: 321, NOT_APPLICABLE: 18 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 145, FLAG: 176, N/A: 18 |
| Scenarios with test cases | 24 / 24 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| T1 Base transaction | 29 |
| T2 Card capture path | 4 |
| T5 Follow-on lifecycle | 40 |
| T6 Time-out & recovery | 72 |
| T8 Settlement | 88 |
| T10 Business-rule decline | 88 |
| T11 Device-only | 18 |

## BR1 BUYPASS ATL105 MasterCard Fleet


### BR1_TS1 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-003429 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-003430 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC3 | TC-003444 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC4 | TC-003445 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC5 | TC-003431 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC6 | TC-003432 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC7 | TC-003433 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC8 | TC-003434 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC9 | TC-003435 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-003446 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC11 | TC-003447 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC12 | TC-003448 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-003436 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC14 | TC-003437 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC15 | TC-003438 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC16 | TC-003439 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC17 | TC-003440 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC18 | TC-003441 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC19 | TC-003449 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC20 | TC-003450 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC21 | TC-003453 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | requires_review |
| BR1_TS1_TC22 | TC-003442 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS1_TC23 | TC-003443 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC24 | TC-003451 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC25 | TC-003452 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS1_TC26 | TC-003456 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC27 | TC-003454 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC28 | TC-003455 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC29 | TC-003457 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS2 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-003458 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-003459 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC3 | TC-003473 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-003474 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC5 | TC-003460 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC6 | TC-003461 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC7 | TC-003462 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC8 | TC-003463 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC9 | TC-003464 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-003475 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC11 | TC-003476 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC12 | TC-003477 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC13 | TC-003465 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC14 | TC-003466 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC15 | TC-003467 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC16 | TC-003468 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC17 | TC-003469 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC18 | TC-003470 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC19 | TC-003478 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC20 | TC-003479 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC21 | TC-003482 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-003471 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS2_TC23 | TC-003472 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC24 | TC-003480 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC25 | TC-003481 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS2_TC26 | TC-003485 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC27 | TC-003483 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-003484 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-003486 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS3 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-003487 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-003510 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 1 → ID Number and Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC3 | TC-003511 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 2 → Vehicle Number and Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC4 | TC-003512 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 3 → Driver ID and Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC5 | TC-003513 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 4 → Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-003514 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 5 → no fleet prompts → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC7 | TC-003515 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet card, Track 2 unreadable → Track 1 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC8 | TC-003488 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC9 | TC-003502 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS3_TC10 | TC-003503 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC11 | TC-003489 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC12 | TC-003490 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC13 | TC-003491 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC14 | TC-003492 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC15 | TC-003493 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS3_TC16 | TC-003504 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC17 | TC-003505 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS3_TC18 | TC-003506 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC19 | TC-003494 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC20 | TC-003495 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC21 | TC-003496 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC22 | TC-003497 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC23 | TC-003498 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS3_TC24 | TC-003499 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC25 | TC-003507 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC26 | TC-003508 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC27 | TC-003518 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | requires_review |
| BR1_TS3_TC28 | TC-003500 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC29 | TC-003501 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS3_TC30 | TC-003509 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC31 | TC-003516 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS3_TC32 | TC-003517 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fuel-only fleet card (Product Restriction Code 2) → non-fuel product not sold | T11 | - | n/a (no host message) | requires_review |
| BR1_TS3_TC33 | TC-003521 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC34 | TC-003519 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC35 | TC-003520 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC36 | TC-003522 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS4 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-003523 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC2 | TC-003524 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC3 | TC-003538 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC4 | TC-003539 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC5 | TC-003525 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC6 | TC-003526 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC7 | TC-003527 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC8 | TC-003528 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC9 | TC-003529 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS4_TC10 | TC-003540 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC11 | TC-003541 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC12 | TC-003542 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC13 | TC-003530 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC14 | TC-003531 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC15 | TC-003532 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC16 | TC-003533 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC17 | TC-003534 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC18 | TC-003535 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC19 | TC-003543 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC20 | TC-003544 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC21 | TC-003547 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS4_TC22 | TC-003536 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC23 | TC-003537 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC24 | TC-003545 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC25 | TC-003546 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS4_TC26 | TC-003550 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC27 | TC-003548 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC28 | TC-003549 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC29 | TC-003551 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS5 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-003552 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-003575 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fleet card, 3 failed swipes → keyed entry with Expiration Date, ID and Odometer → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC3 | TC-003553 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC4 | TC-003567 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS5_TC5 | TC-003568 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC6 | TC-003554 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC7 | TC-003555 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC8 | TC-003556 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC9 | TC-003557 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC10 | TC-003558 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS5_TC11 | TC-003569 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC12 | TC-003570 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS5_TC13 | TC-003571 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC14 | TC-003559 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC15 | TC-003560 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC16 | TC-003561 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC17 | TC-003562 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC18 | TC-003563 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS5_TC19 | TC-003564 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC20 | TC-003572 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC21 | TC-003573 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC22 | TC-003578 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | requires_review |
| BR1_TS5_TC23 | TC-003565 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS5_TC24 | TC-003566 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS5_TC25 | TC-003574 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC26 | TC-003576 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC27 | TC-003577 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed fleet account number with a wrong check digit → Mod 10 evaluation fails | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC28 | TC-003581 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC29 | TC-003579 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC30 | TC-003580 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC31 | TC-003582 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS6 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-003583 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC2 | TC-003584 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC3 | TC-003598 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC4 | TC-003599 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC5 | TC-003585 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC6 | TC-003586 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC7 | TC-003587 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC8 | TC-003588 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC9 | TC-003589 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS6_TC10 | TC-003600 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC11 | TC-003601 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC12 | TC-003602 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC13 | TC-003590 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC14 | TC-003591 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC15 | TC-003592 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC16 | TC-003593 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC17 | TC-003594 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC18 | TC-003595 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC19 | TC-003603 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC20 | TC-003604 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC21 | TC-003607 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS6_TC22 | TC-003596 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC23 | TC-003597 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC24 | TC-003605 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC25 | TC-003606 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS6_TC26 | TC-003610 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC27 | TC-003608 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC28 | TC-003609 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC29 | TC-003611 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS7 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-003612 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC2 | TC-003613 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC3 | TC-003627 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC4 | TC-003628 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC5 | TC-003614 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC6 | TC-003615 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC7 | TC-003616 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC8 | TC-003617 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC9 | TC-003618 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS7_TC10 | TC-003629 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC11 | TC-003630 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC12 | TC-003631 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC13 | TC-003619 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC14 | TC-003620 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC15 | TC-003621 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC16 | TC-003622 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC17 | TC-003623 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC18 | TC-003624 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC19 | TC-003632 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC20 | TC-003633 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC21 | TC-003636 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS7_TC22 | TC-003625 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC23 | TC-003626 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC24 | TC-003634 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC25 | TC-003635 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS7_TC26 | TC-003639 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC27 | TC-003637 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC28 | TC-003638 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC29 | TC-003640 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS8 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-003641 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-003642 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC3 | TC-003656 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC4 | TC-003657 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-003643 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC6 | TC-003644 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC7 | TC-003645 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC8 | TC-003646 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC9 | TC-003647 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS8_TC10 | TC-003658 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC11 | TC-003659 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC12 | TC-003660 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC13 | TC-003648 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC14 | TC-003649 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC15 | TC-003650 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC16 | TC-003651 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC17 | TC-003652 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC18 | TC-003653 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC19 | TC-003661 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC20 | TC-003662 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC21 | TC-003665 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS8_TC22 | TC-003654 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC23 | TC-003655 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC24 | TC-003663 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC25 | TC-003664 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS8_TC26 | TC-003668 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC27 | TC-003666 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC28 | TC-003667 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC29 | TC-003669 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-003670 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS9_TC2 | TC-003671 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC3 | TC-003672 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS9_TC4 | TC-003673 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS9_TC5 | TC-003674 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-003675 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC7 | TC-003676 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS9_TC8 | TC-003677 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC9 | TC-003678 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC10 | TC-003679 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS10 BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-003680 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS10_TC2 | TC-003681 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS10_TC3 | TC-003682 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS10_TC4 | TC-003683 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS10_TC5 | TC-003684 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS10_TC6 | TC-003685 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS10_TC7 | TC-003686 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS10_TC8 | TC-003687 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS10_TC9 | TC-003688 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS10_TC10 | TC-003689 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS11 BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-003690 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC2 | TC-003696 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Fleet card, Track 2 unreadable → Track 1 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC3 | TC-003691 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS11_TC4 | TC-003692 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS11_TC5 | TC-003693 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS11_TC6 | TC-003694 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS11_TC7 | TC-003695 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC8 | TC-003697 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS11_TC9 | TC-003698 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC10 | TC-003699 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC11 | TC-003700 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS12 BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-003701 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS12_TC2 | TC-003702 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC3 | TC-003703 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS12_TC4 | TC-003704 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC5 | TC-003705 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS12_TC6 | TC-003706 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS12_TC7 | TC-003707 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS12_TC8 | TC-003708 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC9 | TC-003709 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC10 | TC-003710 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS13 BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-003711 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC2 | TC-003717 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Fleet card, 3 failed swipes → keyed entry with Expiration Date, ID and Odometer → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-003712 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS13_TC4 | TC-003713 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS13_TC5 | TC-003714 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS13_TC6 | TC-003715 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS13_TC7 | TC-003716 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC8 | TC-003718 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS13_TC9 | TC-003719 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC10 | TC-003720 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC11 | TC-003721 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS14 BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-003722 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS14_TC2 | TC-003723 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS14_TC3 | TC-003724 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS14_TC4 | TC-003725 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS14_TC5 | TC-003726 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS14_TC6 | TC-003727 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS14_TC7 | TC-003728 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS14_TC8 | TC-003729 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS14_TC9 | TC-003730 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS14_TC10 | TC-003731 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS15 BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-003732 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS15_TC2 | TC-003733 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS15_TC3 | TC-003734 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS15_TC4 | TC-003735 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS15_TC5 | TC-003736 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS15_TC6 | TC-003737 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS15_TC7 | TC-003738 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS15_TC8 | TC-003739 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS15_TC9 | TC-003740 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS15_TC10 | TC-003741 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS16 BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-003742 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS16_TC2 | TC-003743 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS16_TC3 | TC-003744 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS16_TC4 | TC-003745 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS16_TC5 | TC-003746 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS16_TC6 | TC-003747 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS16_TC7 | TC-003748 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS16_TC8 | TC-003749 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS16_TC9 | TC-003750 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS16_TC10 | TC-003751 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS17 BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-003752 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS17_TC2 | TC-003753 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-003754 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS18_TC2 | TC-003755 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS19 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-003756 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS19_TC2 | TC-003757 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS20 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-003758 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS20_TC2 | TC-003759 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-003760 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS21_TC2 | TC-003761 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS22 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-003762 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS22_TC2 | TC-003763 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS23 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-003764 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS23_TC2 | TC-003765 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS24 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-003766 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS24_TC2 | TC-003767 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

## Planned but not generated

- BR1_TS17 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS17 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS17 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS17 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS18 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS18 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS18 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS18 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS19 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS19 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS19 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS19 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS20 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS20 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS20 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS20 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS21 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS21 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS21 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS21 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS22 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS22 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS22 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS22 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS23 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS23 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS23 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS23 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS24 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS24 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS24 T5-RET-VOID-SETTLE: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS24 T5-RET-VOID-WRONG-SEQ: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping

## Follow-on legs (reversal / void / cancellation ...)

16 of 16 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
