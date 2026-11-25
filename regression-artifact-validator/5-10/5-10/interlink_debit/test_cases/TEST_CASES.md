# Test cases - BR1 BUYPASS ATL105 Interlink Debit

Build `interlink-debit-edd2ee88d4`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 24 |
| Test cases | 323 |
| By status | needs_client_input: 62, requires_review: 46, pending_sme: 215 |
| By polarity | Positive: 226, Negative: 97 |
| Verification | PASS: 322, NOT_APPLICABLE: 1 |
| Verification level | PASS: 322, NOT_APPLICABLE: 1 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 124, FLAG: 198, N/A: 1 |
| Scenarios with test cases | 24 / 24 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| T1 Base transaction | 30 |
| T2 Card capture path | 10 |
| T4 Partial approval | 12 |
| T5 Follow-on lifecycle | 42 |
| T6 Time-out & recovery | 48 |
| T8 Settlement | 84 |
| T10 Business-rule decline | 96 |
| T11 Device-only | 1 |

## BR1 BUYPASS ATL105 Interlink Debit


### BR1_TS1 BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-004369 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-004393 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC3 | TC-004392 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC4 | TC-004394 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-004395 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC6 | TC-004383 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC7 | TC-004384 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC8 | TC-004391 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC9 | TC-004370 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-004371 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC11 | TC-004372 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC12 | TC-004373 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-004374 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC14 | TC-004385 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC15 | TC-004386 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC16 | TC-004387 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-004375 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC18 | TC-004376 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC19 | TC-004377 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC20 | TC-004378 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC21 | TC-004379 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC22 | TC-004380 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC23 | TC-004388 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC24 | TC-004389 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC25 | TC-004396 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | requires_review |
| BR1_TS1_TC26 | TC-004381 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC27 | TC-004382 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC28 | TC-004390 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC29 | TC-004399 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC30 | TC-004400 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit partial approval → Purchase Reversal of the approved amount → Settlement (read and cut 222222) | T8 | 0 -> 8 -> 990 | 1: F (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC31 | TC-004397 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC32 | TC-004398 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC33 | TC-004401 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase → Purchase Reversal (no PIN) with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS2 BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-004402 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-004427 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC3 | TC-004403 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-004426 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC5 | TC-004428 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-004429 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC7 | TC-004417 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC8 | TC-004418 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC9 | TC-004425 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-004404 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC11 | TC-004405 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC12 | TC-004406 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC13 | TC-004407 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC14 | TC-004408 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC15 | TC-004419 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC16 | TC-004420 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC17 | TC-004421 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC18 | TC-004409 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC19 | TC-004410 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC20 | TC-004411 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC21 | TC-004412 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-004413 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC23 | TC-004414 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC24 | TC-004422 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC25 | TC-004423 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC26 | TC-004430 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | requires_review |
| BR1_TS2_TC27 | TC-004415 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-004416 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-004424 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC30 | TC-004433 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC31 | TC-004434 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit partial approval → Purchase Reversal of the approved amount → Settlement (read and cut 222222) | T8 | 0 -> 8 -> 990 | 1: F (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC32 | TC-004431 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC33 | TC-004432 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC34 | TC-004435 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase → Purchase Reversal (no PIN) with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS3 BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-004436 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC2 | TC-004460 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS3_TC3 | TC-004459 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS3_TC4 | TC-004461 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS3_TC5 | TC-004462 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC6 | TC-004450 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC7 | TC-004451 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC8 | TC-004458 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC9 | TC-004437 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC10 | TC-004438 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC11 | TC-004439 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC12 | TC-004440 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC13 | TC-004441 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS3_TC14 | TC-004452 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC15 | TC-004453 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC16 | TC-004454 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC17 | TC-004442 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC18 | TC-004443 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC19 | TC-004444 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC20 | TC-004445 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC21 | TC-004446 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC22 | TC-004447 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC23 | TC-004455 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC24 | TC-004456 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC25 | TC-004464 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS3_TC26 | TC-004448 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC27 | TC-004449 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC28 | TC-004457 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC29 | TC-004463 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit card, 3 failed swipes → transaction ended (no manual entry) | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS3_TC30 | TC-004467 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC31 | TC-004468 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount → Settlement (read and cut 222222) | T8 | 0 -> 8 -> 990 | 1: F (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC32 | TC-004465 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC33 | TC-004466 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC34 | TC-004469 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS4 BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-004470 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC2 | TC-004494 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS4_TC3 | TC-004493 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS4_TC4 | TC-004495 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS4_TC5 | TC-004496 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC6 | TC-004484 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC7 | TC-004485 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC8 | TC-004492 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC9 | TC-004471 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC10 | TC-004472 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC11 | TC-004473 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC12 | TC-004474 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC13 | TC-004475 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS4_TC14 | TC-004486 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC15 | TC-004487 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC16 | TC-004488 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC17 | TC-004476 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC18 | TC-004477 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC19 | TC-004478 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC20 | TC-004479 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC21 | TC-004480 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC22 | TC-004481 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC23 | TC-004489 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC24 | TC-004490 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC25 | TC-004497 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS4_TC26 | TC-004482 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC27 | TC-004483 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC28 | TC-004491 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC29 | TC-004500 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC30 | TC-004501 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount → Settlement (read and cut 222222) | T8 | 0 -> 8 -> 990 | 1: F (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC31 | TC-004498 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC32 | TC-004499 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC33 | TC-004502 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS5 BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-004503 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC2 | TC-004528 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS5_TC3 | TC-004517 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC4 | TC-004527 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS5_TC5 | TC-004529 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS5_TC6 | TC-004530 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC7 | TC-004518 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC8 | TC-004519 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC9 | TC-004526 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC10 | TC-004504 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC11 | TC-004505 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC12 | TC-004506 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC13 | TC-004507 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC14 | TC-004508 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS5_TC15 | TC-004520 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC16 | TC-004521 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC17 | TC-004522 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC18 | TC-004509 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC19 | TC-004510 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC20 | TC-004511 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC21 | TC-004512 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC22 | TC-004513 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC23 | TC-004514 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC24 | TC-004523 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC25 | TC-004524 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC26 | TC-004531 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS5_TC27 | TC-004515 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC28 | TC-004516 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC29 | TC-004525 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC30 | TC-004534 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC31 | TC-004535 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit partial approval → Purchase Reversal of the approved amount → Settlement (read and cut 222222) | T8 | 0 -> 8 -> 990 | 1: F (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC32 | TC-004532 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC33 | TC-004533 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC34 | TC-004536 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase → Purchase Reversal (no PIN) with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS6 BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-004537 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC2 | TC-004561 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS6_TC3 | TC-004560 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS6_TC4 | TC-004562 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS6_TC5 | TC-004563 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC6 | TC-004551 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC7 | TC-004552 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC8 | TC-004559 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC9 | TC-004538 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC10 | TC-004539 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC11 | TC-004540 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC12 | TC-004541 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC13 | TC-004542 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS6_TC14 | TC-004553 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC15 | TC-004554 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC16 | TC-004555 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC17 | TC-004543 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC18 | TC-004544 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC19 | TC-004545 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC20 | TC-004546 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC21 | TC-004547 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC22 | TC-004548 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC23 | TC-004556 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC24 | TC-004557 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC25 | TC-004564 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → two partial Merchandise Returns → Settlement (read and cut 222222) | T8 | 0 -> 7 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) ; 4: ? (FLAG) | pending_sme |
| BR1_TS6_TC26 | TC-004549 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC27 | TC-004550 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC28 | TC-004558 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC29 | TC-004567 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) → Settlement (read and cut 222222) | T8 | 0 -> 0 -> 990 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC30 | TC-004568 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit partial approval → Purchase Reversal of the approved amount → Settlement (read and cut 222222) | T8 | 0 -> 8 -> 990 | 1: F (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC31 | TC-004565 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC32 | TC-004566 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC33 | TC-004569 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase → Purchase Reversal (no PIN) with the original's Sequence Number changed | T10 | 0 -> 8 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS7 BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-004570 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS7_TC2 | TC-004571 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC3 | TC-004574 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC4 | TC-004575 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS7_TC5 | TC-004572 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC6 | TC-004573 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC7 | TC-004579 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal → Settlement (read and cut 222222) | T8 | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC8 | TC-004576 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-004577 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC10 | TC-004578 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC11 | TC-004580 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC12 | TC-004581 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal with the original's Sequence Number changed | T10 | 3 -> S | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS8 BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-004582 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS8_TC2 | TC-004583 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS8_TC3 | TC-004584 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS8_TC4 | TC-004587 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS8_TC5 | TC-004588 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS8_TC6 | TC-004585 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS8_TC7 | TC-004586 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS8_TC8 | TC-004592 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal → Settlement (read and cut 222222) | T8 | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS8_TC9 | TC-004589 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS8_TC10 | TC-004590 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS8_TC11 | TC-004591 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS8_TC12 | TC-004593 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |
| BR1_TS8_TC13 | TC-004594 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal with the original's Sequence Number changed | T10 | 3 -> S | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS9 BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-004595 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS9_TC2 | TC-004596 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS9_TC3 | TC-004599 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS9_TC4 | TC-004600 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS9_TC5 | TC-004597 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS9_TC6 | TC-004598 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS9_TC7 | TC-004604 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal → Settlement (read and cut 222222) | T8 | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS9_TC8 | TC-004601 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS9_TC9 | TC-004602 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS9_TC10 | TC-004603 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS9_TC11 | TC-004605 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |
| BR1_TS9_TC12 | TC-004606 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal with the original's Sequence Number changed | T10 | 3 -> S | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS10 BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-004607 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS10_TC2 | TC-004608 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC3 | TC-004611 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-004612 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-004609 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-004610 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC7 | TC-004616 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal → Settlement (read and cut 222222) | T8 | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS10_TC8 | TC-004613 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC9 | TC-004614 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC10 | TC-004615 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC11 | TC-004617 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |
| BR1_TS10_TC12 | TC-004618 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal with the original's Sequence Number changed | T10 | 3 -> S | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS11 BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-004619 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS11_TC2 | TC-004623 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS11_TC3 | TC-004620 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS11_TC4 | TC-004624 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS11_TC5 | TC-004625 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS11_TC6 | TC-004621 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS11_TC7 | TC-004622 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS11_TC8 | TC-004629 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal → Settlement (read and cut 222222) | T8 | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS11_TC9 | TC-004626 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS11_TC10 | TC-004627 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS11_TC11 | TC-004628 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS11_TC12 | TC-004630 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |
| BR1_TS11_TC13 | TC-004631 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal with the original's Sequence Number changed | T10 | 3 -> S | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS12 BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-004632 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS12_TC2 | TC-004633 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC3 | TC-004636 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS12_TC4 | TC-004637 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS12_TC5 | TC-004634 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS12_TC6 | TC-004635 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS12_TC7 | TC-004641 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal → Settlement (read and cut 222222) | T8 | 3 -> S -> 990 | 1: 2 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS12_TC8 | TC-004638 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC9 | TC-004639 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC10 | TC-004640 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC11 | TC-004642 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion with the original's Sequence Number changed | T10 | 3 -> 0 | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |
| BR1_TS12_TC12 | TC-004643 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal with the original's Sequence Number changed | T10 | 3 -> S | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS13 BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-004644 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS13_TC2 | TC-004645 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS13_TC3 | TC-004648 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return → Settlement (read and cut 222222) | T8 | 7 -> U -> 990 | 1: ? (FLAG) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS13_TC4 | TC-004646 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC5 | TC-004647 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS13_TC6 | TC-004649 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return with the original's Sequence Number changed | T10 | 7 -> U | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS14 BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-004650 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS14_TC2 | TC-004651 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS14_TC3 | TC-004654 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return → Settlement (read and cut 222222) | T8 | 7 -> U -> 990 | 1: ? (FLAG) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS14_TC4 | TC-004652 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC5 | TC-004653 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |
| BR1_TS14_TC6 | TC-004655 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return with the original's Sequence Number changed | T10 | 7 -> U | 1: ? (FLAG) ; 2: ? (FLAG) | requires_review |

### BR1_TS15 BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-004656 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS15_TC2 | TC-004657 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS15_TC3 | TC-004660 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return → Settlement (read and cut 222222) | T8 | 7 -> U -> 990 | 1: ? (FLAG) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS15_TC4 | TC-004658 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS15_TC5 | TC-004659 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS15_TC6 | TC-004661 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return with the original's Sequence Number changed | T10 | 7 -> U | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS16 BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-004662 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS16_TC2 | TC-004663 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS16_TC3 | TC-004666 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return → Settlement (read and cut 222222) | T8 | 7 -> U -> 990 | 1: ? (FLAG) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS16_TC4 | TC-004664 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS16_TC5 | TC-004665 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS16_TC6 | TC-004667 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return with the original's Sequence Number changed | T10 | 7 -> U | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS17 BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-004668 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS17_TC2 | TC-004669 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS17_TC3 | TC-004672 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return → Settlement (read and cut 222222) | T8 | 7 -> U -> 990 | 1: ? (FLAG) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS17_TC4 | TC-004670 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS17_TC5 | TC-004671 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS17_TC6 | TC-004673 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return with the original's Sequence Number changed | T10 | 7 -> U | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS18 BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-004674 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS18_TC2 | TC-004675 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC3 | TC-004678 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return → Settlement (read and cut 222222) | T8 | 7 -> U -> 990 | 1: ? (FLAG) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS18_TC4 | TC-004676 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC5 | TC-004677 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC6 | TC-004679 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return with the original's Sequence Number changed | T10 | 7 -> U | 1: ? (FLAG) ; 2: ? (FLAG) | pending_sme |

### BR1_TS19 BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-004680 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Debit Balance Inquiry answered | T1 | E | 1: ? (FLAG) | requires_review |
| BR1_TS19_TC2 | TC-004681 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Debit Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS20 BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-004682 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Debit Balance Inquiry answered | T1 | E | 1: ? (FLAG) | requires_review |
| BR1_TS20_TC2 | TC-004683 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Debit Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS21 BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-004684 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Debit Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS21_TC2 | TC-004685 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Debit Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS22 BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-004686 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Debit Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS22_TC2 | TC-004687 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Debit Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS23 BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-004688 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Debit Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS23_TC2 | TC-004689 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Debit Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS24 BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-004690 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Debit Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS24_TC2 | TC-004691 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Debit Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

## Follow-on legs (reversal / void / cancellation ...)

24 of 24 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
