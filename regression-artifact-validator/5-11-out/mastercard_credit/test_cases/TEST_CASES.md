# Test cases - BR1 BUYPASS ATL105 MasterCard Credit

Build `mastercard-credit-0e784ee3e7`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 36 |
| Test cases | 488 |
| By status | needs_client_input: 173, requires_review: 141, ready: 102, pending_sme: 70, blocked_spec_gap: 2 |
| By polarity | Positive: 345, Negative: 143 |
| Verification | PASS: 478, NOT_APPLICABLE: 10 |
| Verification level | PASS: 478, NOT_APPLICABLE: 10 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 217, FLAG: 261, N/A: 10 |
| Scenarios with test cases | 36 / 36 |
| Validation | passed |

## By type

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
| T10 Business-rule decline | 136 |
| T11 Device-only | 8 |

## BR1 BUYPASS ATL105 MasterCard Credit


### BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-000258 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-000284 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS1_TC3 | TC-000259 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired EMV card → sent online → host decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC4 | TC-000260 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip EMV online PIN → approved | T3 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-000261 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip EMV offline PIN → approved | T3 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC6 | TC-000262 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC7 | TC-000263 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC8 | TC-000264 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS1_TC9 | TC-000265 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-000266 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC11 | TC-000285 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC12 | TC-000286 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC13 | TC-000267 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC14 | TC-000268 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC15 | TC-000269 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC16 | TC-000270 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-000271 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC18 | TC-000287 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC19 | TC-000288 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC20 | TC-000289 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC21 | TC-000272 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC22 | TC-000273 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC23 | TC-000274 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC24 | TC-000275 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC25 | TC-000276 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC26 | TC-000277 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC27 | TC-000290 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC28 | TC-000291 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC29 | TC-000278 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC30 | TC-000279 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC31 | TC-000280 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS1_TC32 | TC-000281 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC33 | TC-000282 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC34 | TC-000283 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS1_TC35 | TC-000292 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC36 | TC-000293 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-000294 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-000320 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS2_TC3 | TC-000295 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-000296 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired EMV card → sent online → host decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC5 | TC-000297 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip EMV online PIN → approved | T3 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-000298 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC7 | TC-000299 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC8 | TC-000300 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS2_TC9 | TC-000301 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-000302 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC11 | TC-000321 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC12 | TC-000322 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC13 | TC-000303 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC14 | TC-000304 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC15 | TC-000305 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC16 | TC-000306 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC17 | TC-000307 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC18 | TC-000323 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC19 | TC-000324 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC20 | TC-000325 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC21 | TC-000308 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-000309 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC23 | TC-000310 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC24 | TC-000311 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC25 | TC-000312 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC26 | TC-000313 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC27 | TC-000326 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC28 | TC-000327 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC29 | TC-000314 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC30 | TC-000315 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC31 | TC-000316 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS2_TC32 | TC-000317 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC33 | TC-000318 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC34 | TC-000319 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS2_TC35 | TC-000328 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC36 | TC-000329 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-000330 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-000356 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS3_TC3 | TC-000331 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Track 2 unreadable → Track 1 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC4 | TC-000332 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Swiped/keyed at RFID-equipped terminal → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC5 | TC-000333 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-000334 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC7 | TC-000335 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS3_TC8 | TC-000336 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC9 | TC-000337 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC10 | TC-000357 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS3_TC11 | TC-000358 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC12 | TC-000338 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC13 | TC-000339 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC14 | TC-000340 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC15 | TC-000341 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC16 | TC-000342 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS3_TC17 | TC-000359 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC18 | TC-000360 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Line down → Purchase approved off-line (stand-in) → TOR → off-line Purchase forwarded (Deferred 'Y') | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (FLAG) | requires_review |
| BR1_TS3_TC19 | TC-000361 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS3_TC20 | TC-000362 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC21 | TC-000343 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC22 | TC-000344 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC23 | TC-000345 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC24 | TC-000346 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC25 | TC-000347 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS3_TC26 | TC-000348 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC27 | TC-000363 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC28 | TC-000364 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC29 | TC-000349 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC30 | TC-000350 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC31 | TC-000351 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC32 | TC-000352 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS3_TC33 | TC-000353 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC34 | TC-000354 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS3_TC35 | TC-000365 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC36 | TC-000355 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS3_TC37 | TC-000366 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-000367 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC2 | TC-000389 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS4_TC3 | TC-000368 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC4 | TC-000369 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS4_TC5 | TC-000370 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS4_TC6 | TC-000371 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC7 | TC-000372 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC8 | TC-000390 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS4_TC9 | TC-000391 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC10 | TC-000373 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC11 | TC-000374 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC12 | TC-000375 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC13 | TC-000376 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS4_TC14 | TC-000377 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS4_TC15 | TC-000392 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC16 | TC-000393 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS4_TC17 | TC-000394 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS4_TC18 | TC-000378 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS4_TC19 | TC-000379 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC20 | TC-000380 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC21 | TC-000381 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC22 | TC-000382 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS4_TC23 | TC-000383 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS4_TC24 | TC-000395 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC25 | TC-000396 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC26 | TC-000384 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS4_TC27 | TC-000385 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS4_TC28 | TC-000386 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC29 | TC-000387 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS4_TC30 | TC-000397 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC31 | TC-000388 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS4_TC32 | TC-000398 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-000399 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-000426 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS5_TC3 | TC-000400 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed 3 failed swipes → keyed entry → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC4 | TC-000401 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Swiped/keyed at RFID-equipped terminal → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC5 | TC-000402 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC6 | TC-000403 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS5_TC7 | TC-000404 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS5_TC8 | TC-000405 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS5_TC9 | TC-000406 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC10 | TC-000427 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS5_TC11 | TC-000428 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC12 | TC-000407 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC13 | TC-000408 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC14 | TC-000409 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC15 | TC-000410 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC16 | TC-000411 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS5_TC17 | TC-000429 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC18 | TC-000430 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS5_TC19 | TC-000431 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC20 | TC-000412 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC21 | TC-000413 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC22 | TC-000414 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC23 | TC-000415 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC24 | TC-000416 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS5_TC25 | TC-000417 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC26 | TC-000432 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC27 | TC-000433 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC28 | TC-000418 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase with AVS mismatch → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC29 | TC-000419 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase with AVS mismatch → merchant reverses | T9 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS5_TC30 | TC-000420 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS5_TC31 | TC-000421 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS5_TC32 | TC-000422 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC33 | TC-000423 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS5_TC34 | TC-000434 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC35 | TC-000424 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS5_TC36 | TC-000425 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed entry before 3 swipes → refused at device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC37 | TC-000435 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed inside sale with card type 174 → device prompts for card verification data | T11 | - | n/a (no host message) | ready |
| BR1_TS5_TC38 | TC-000436 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-000437 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC2 | TC-000459 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS6_TC3 | TC-000438 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → keyed fallback (79) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC4 | TC-000460 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC5 | TC-000461 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80), Track 2 unreadable → Track 1 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC6 | TC-000439 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC7 | TC-000440 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS6_TC8 | TC-000441 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS6_TC9 | TC-000442 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS6_TC10 | TC-000443 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC11 | TC-000462 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS6_TC12 | TC-000463 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC13 | TC-000444 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC14 | TC-000445 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC15 | TC-000446 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC16 | TC-000447 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS6_TC17 | TC-000448 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS6_TC18 | TC-000464 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC19 | TC-000465 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS6_TC20 | TC-000466 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS6_TC21 | TC-000449 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS6_TC22 | TC-000450 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC23 | TC-000451 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC24 | TC-000452 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC25 | TC-000453 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS6_TC26 | TC-000454 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS6_TC27 | TC-000467 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC28 | TC-000468 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC29 | TC-000455 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS6_TC30 | TC-000456 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS6_TC31 | TC-000457 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC32 | TC-000458 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS6_TC33 | TC-000469 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC34 | TC-000470 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-000471 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC2 | TC-000495 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS7_TC3 | TC-000472 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC4 | TC-000496 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC5 | TC-000497 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC6 | TC-000473 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC7 | TC-000474 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC8 | TC-000475 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC9 | TC-000476 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC10 | TC-000477 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC11 | TC-000498 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC12 | TC-000499 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS7_TC13 | TC-000500 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC14 | TC-000478 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store card (cardholder-initiated) → repeat cardholder-initiated purchase | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC15 | TC-000479 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File $0.00 Account Verification → merchant-initiated purchase | T7 | A -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS7_TC16 | TC-000480 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Recurring standing order: first → merchant-initiated repeat | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC17 | TC-000481 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Recurring subscription: first → merchant-initiated repeat | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC18 | TC-000482 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Installment: first → merchant-initiated installment | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC19 | TC-000483 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC20 | TC-000484 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC21 | TC-000485 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC22 | TC-000486 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC23 | TC-000487 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS7_TC24 | TC-000488 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC25 | TC-000501 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC26 | TC-000502 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC27 | TC-000489 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase with AVS mismatch → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC28 | TC-000490 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase with AVS mismatch → merchant reverses | T9 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC29 | TC-000491 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC30 | TC-000492 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC31 | TC-000493 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC32 | TC-000494 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS7_TC33 | TC-000503 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC34 | TC-000504 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-000505 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-000526 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS8_TC3 | TC-000506 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC4 | TC-000507 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-000508 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC6 | TC-000509 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC7 | TC-000510 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC8 | TC-000527 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC9 | TC-000528 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC10 | TC-000511 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC11 | TC-000512 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC12 | TC-000513 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC13 | TC-000514 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC14 | TC-000515 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS8_TC15 | TC-000529 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC16 | TC-000530 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC17 | TC-000531 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC18 | TC-000516 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC19 | TC-000517 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC20 | TC-000518 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC21 | TC-000519 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC22 | TC-000520 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC23 | TC-000521 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC24 | TC-000532 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC25 | TC-000533 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC26 | TC-000522 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC27 | TC-000523 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC28 | TC-000524 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC29 | TC-000525 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC30 | TC-000534 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC31 | TC-000535 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-000536 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase approved | T1 | 6 | 1: ? (FLAG) | ready |
| BR1_TS9_TC2 | TC-000537 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase → Mail/Phone Reversal | T5 | 6 -> C | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS9_TC3 | TC-000538 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase, no response → Time-out Reversal | T6 | 6 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS9_TC4 | TC-000539 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase with AVS mismatch → approved | T9 | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC5 | TC-000540 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase with AVS mismatch → Mail/Phone Reversal | T9 | 6 -> C | 1: 0 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-000541 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Partial requested on Mail/Phone Purchase → not partially approved | T10 | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC7 | TC-000542 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase → Mail/Phone Reversal declined | T10 | 6 -> C | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS10 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-000543 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase approved | T1 | 6 | 1: ? (FLAG) | pending_sme |
| BR1_TS10_TC2 | TC-000544 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase → Mail/Phone Reversal | T5 | 6 -> C | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC3 | TC-000545 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase, no response → Time-out Reversal | T6 | 6 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-000546 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase with AVS mismatch → approved | T9 | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC5 | TC-000547 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase with AVS mismatch → Mail/Phone Reversal | T9 | 6 -> C | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-000548 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Partial requested on Mail/Phone Purchase → not partially approved | T10 | 6 | 1: 1 (FLAG) | pending_sme |
| BR1_TS10_TC7 | TC-000549 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase → Mail/Phone Reversal declined | T10 | 6 -> C | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-000550 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC2 | TC-000551 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip EMV online PIN → approved | T3 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC3 | TC-000552 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip EMV offline PIN → approved | T3 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC4 | TC-000553 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC5 | TC-000554 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC6 | TC-000555 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC7 | TC-000556 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS11_TC8 | TC-000557 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC9 | TC-000558 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC10 | TC-000559 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS11_TC11 | TC-000560 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS11_TC12 | TC-000561 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC13 | TC-000562 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC14 | TC-000563 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC15 | TC-000564 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC16 | TC-000565 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC17 | TC-000566 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC18 | TC-000567 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-000568 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC2 | TC-000569 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC3 | TC-000570 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip EMV online PIN → approved | T3 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC4 | TC-000571 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC5 | TC-000572 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC6 | TC-000573 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC7 | TC-000574 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS12_TC8 | TC-000575 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC9 | TC-000576 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC10 | TC-000577 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS12_TC11 | TC-000578 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS12_TC12 | TC-000579 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC13 | TC-000580 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC14 | TC-000581 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC15 | TC-000582 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC16 | TC-000583 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC17 | TC-000584 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC18 | TC-000585 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS13 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-000586 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC2 | TC-000587 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Track 2 unreadable → Track 1 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-000588 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC4 | TC-000589 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS13_TC5 | TC-000590 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS13_TC6 | TC-000591 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS13_TC7 | TC-000592 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS13_TC8 | TC-000593 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS13_TC9 | TC-000594 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS13_TC10 | TC-000595 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS13_TC11 | TC-000596 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC12 | TC-000597 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC13 | TC-000598 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC14 | TC-000599 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS13_TC15 | TC-000600 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC16 | TC-000601 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC17 | TC-000602 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC18 | TC-000603 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS14 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-000604 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC2 | TC-000605 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS14_TC3 | TC-000606 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS14_TC4 | TC-000607 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS14_TC5 | TC-000608 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS14_TC6 | TC-000609 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS14_TC7 | TC-000610 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS14_TC8 | TC-000611 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS14_TC9 | TC-000612 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS14_TC10 | TC-000613 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC11 | TC-000614 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC12 | TC-000615 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC13 | TC-000616 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS14_TC14 | TC-000617 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC15 | TC-000618 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC16 | TC-000619 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC17 | TC-000620 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS15 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-000621 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC2 | TC-000622 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed 3 failed swipes → keyed entry → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC3 | TC-000623 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS15_TC4 | TC-000624 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS15_TC5 | TC-000625 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS15_TC6 | TC-000626 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS15_TC7 | TC-000627 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS15_TC8 | TC-000628 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS15_TC9 | TC-000629 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS15_TC10 | TC-000630 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS15_TC11 | TC-000631 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC12 | TC-000632 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC13 | TC-000633 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC14 | TC-000634 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS15_TC15 | TC-000635 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC16 | TC-000636 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC17 | TC-000637 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC18 | TC-000638 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS16 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-000639 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC2 | TC-000640 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → keyed fallback (79) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC3 | TC-000652 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC4 | TC-000653 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80), Track 2 unreadable → Track 1 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC5 | TC-000641 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS16_TC6 | TC-000642 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS16_TC7 | TC-000643 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS16_TC8 | TC-000644 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS16_TC9 | TC-000645 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS16_TC10 | TC-000646 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS16_TC11 | TC-000647 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS16_TC12 | TC-000648 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS16_TC13 | TC-000649 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC14 | TC-000650 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC15 | TC-000651 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC16 | TC-000654 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC17 | TC-000655 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC18 | TC-000656 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC19 | TC-000657 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS17 BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-000658 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC2 | TC-000659 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS17_TC3 | TC-000660 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS17_TC4 | TC-000661 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS17_TC5 | TC-000662 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | needs_client_input |
| BR1_TS17_TC6 | TC-000663 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS17_TC7 | TC-000664 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS17_TC8 | TC-000665 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC9 | TC-000666 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC10 | TC-000667 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC11 | TC-000668 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC12 | TC-000669 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC13 | TC-000670 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC14 | TC-000671 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-000672 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-000673 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC3 | TC-000674 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC4 | TC-000675 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC5 | TC-000676 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC6 | TC-000677 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC7 | TC-000678 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | pending_sme |
| BR1_TS18_TC8 | TC-000679 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC9 | TC-000680 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS18_TC10 | TC-000681 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC11 | TC-000682 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC12 | TC-000683 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC13 | TC-000684 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC14 | TC-000685 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC15 | TC-000686 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC16 | TC-000687 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | pending_sme |

### BR1_TS19 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-000688 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization Only approved | T1 | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC2 | TC-000689 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Cancellation | T5 | B -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS19_TC3 | TC-000690 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Completion | T5 | - | n/a (no host message) | blocked_spec_gap |
| BR1_TS19_TC4 | TC-000691 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization Only declined | T10 | B | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS19_TC5 | TC-000692 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Cancellation declined | T10 | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS20 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-000693 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization Only approved | T1 | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-000694 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Cancellation | T5 | B -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS20_TC3 | TC-000695 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Completion | T5 | - | n/a (no host message) | blocked_spec_gap |
| BR1_TS20_TC4 | TC-000696 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization Only declined | T10 | B | 1: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC5 | TC-000697 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Cancellation declined | T10 | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-000698 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS21_TC2 | TC-000699 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS21_TC3 | TC-000700 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS21_TC4 | TC-000701 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |

### BR1_TS22 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-000702 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS22_TC2 | TC-000703 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS22_TC3 | TC-000704 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS22_TC4 | TC-000705 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |

### BR1_TS23 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-000706 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS23_TC2 | TC-000707 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS23_TC3 | TC-000708 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS23_TC4 | TC-000709 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS24 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-000710 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS24_TC2 | TC-000711 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS24_TC3 | TC-000712 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS24_TC4 | TC-000713 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS25 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-000714 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS25_TC2 | TC-000715 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS25_TC3 | TC-000716 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS25_TC4 | TC-000717 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS26 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-000718 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS26_TC2 | TC-000719 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS26_TC3 | TC-000720 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS26_TC4 | TC-000721 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS27 BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-000722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS27_TC2 | TC-000723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-000724 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS27_TC4 | TC-000725 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS28 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-000726 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS28_TC2 | TC-000727 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-000728 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS28_TC4 | TC-000729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS29 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-000730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Balance Inquiry answered | T1 | E | 1: ? (FLAG) | needs_client_input |
| BR1_TS29_TC2 | TC-000731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS30 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-000732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Balance Inquiry answered | T1 | E | 1: ? (FLAG) | needs_client_input |
| BR1_TS30_TC2 | TC-000733 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS31 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-000734 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS31_TC2 | TC-000735 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS32 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-000736 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS32_TC2 | TC-000737 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS33 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-000738 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Manual / Keyed Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS33_TC2 | TC-000739 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Manual / Keyed Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS34 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-000740 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS34_TC2 | TC-000741 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS35 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-000742 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Credential on File Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS35_TC2 | TC-000743 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Credential on File Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS36 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-000744 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS36_TC2 | TC-000745 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

## Planned but not generated

- BR1_TS1 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS2 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS3 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS4 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS5 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS6 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS7 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only
- BR1_TS8 T10-PARTIAL-CAT: M01: template runs on ['CAT'] terminals only

## Follow-on legs (reversal / void / cancellation ...)

34 of 34 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
