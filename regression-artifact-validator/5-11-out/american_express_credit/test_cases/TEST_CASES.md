# Test cases - BR1 BUYPASS ATL105 American Express Credit

Build `american-express-credit-75901642a0`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 36 |
| Test cases | 454 |
| By status | needs_client_input: 165, requires_review: 118, ready: 102, pending_sme: 67, blocked_spec_gap: 2 |
| By polarity | Positive: 319, Negative: 135 |
| Verification | PASS: 444, NOT_APPLICABLE: 10 |
| Verification level | PASS: 444, NOT_APPLICABLE: 10 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 208, FLAG: 236, N/A: 10 |
| Scenarios with test cases | 36 / 36 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| T1 Base transaction | 36 |
| T2 Card capture path | 16 |
| T3 Cardholder verification | 2 |
| T4 Partial approval | 42 |
| T5 Follow-on lifecycle | 62 |
| T6 Time-out & recovery | 74 |
| T8 Settlement | 72 |
| T9 Value-added service | 14 |
| T10 Business-rule decline | 128 |
| T11 Device-only | 8 |

## BR1 BUYPASS ATL105 American Express Credit


### BR1_TS1 BUYPASS ATL105 American Express Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-001053 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-001054 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired EMV card → sent online → host decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC3 | TC-001055 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip EMV offline PIN → approved | T3 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC4 | TC-001056 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-001057 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC6 | TC-001058 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS1_TC7 | TC-001059 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC8 | TC-001060 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC9 | TC-001077 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC10 | TC-001078 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC11 | TC-001061 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC12 | TC-001062 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC13 | TC-001063 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC14 | TC-001064 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC15 | TC-001065 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC16 | TC-001079 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-001080 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC18 | TC-001081 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC19 | TC-001066 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC20 | TC-001067 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC21 | TC-001068 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC22 | TC-001069 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC23 | TC-001070 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC24 | TC-001071 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC25 | TC-001082 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC26 | TC-001083 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC27 | TC-001072 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC28 | TC-001073 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC29 | TC-001074 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS1_TC30 | TC-001075 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC31 | TC-001076 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC32 | TC-001084 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC33 | TC-001085 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS2 BUYPASS ATL105 American Express Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-001086 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-001087 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC3 | TC-001088 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired EMV card → sent online → host decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC4 | TC-001089 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC5 | TC-001090 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC6 | TC-001091 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS2_TC7 | TC-001092 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC8 | TC-001093 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC9 | TC-001110 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC10 | TC-001111 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC11 | TC-001094 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC12 | TC-001095 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC13 | TC-001096 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC14 | TC-001097 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC15 | TC-001098 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC16 | TC-001112 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC17 | TC-001113 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC18 | TC-001114 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC19 | TC-001099 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC20 | TC-001100 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC21 | TC-001101 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-001102 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC23 | TC-001103 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC24 | TC-001104 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC25 | TC-001115 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC26 | TC-001116 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC27 | TC-001105 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-001106 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-001107 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS2_TC30 | TC-001108 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC31 | TC-001109 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC32 | TC-001117 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC33 | TC-001118 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS3 BUYPASS ATL105 American Express Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-001119 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-001120 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Swiped/keyed at RFID-equipped terminal → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC3 | TC-001151 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe American Express card swiped → Track 1 sent (default track) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC4 | TC-001152 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe American Express card, Track 1 unreadable → Track 2 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC5 | TC-001121 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-001122 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC7 | TC-001123 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS3_TC8 | TC-001124 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC9 | TC-001125 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC10 | TC-001143 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS3_TC11 | TC-001144 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC12 | TC-001126 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC13 | TC-001127 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC14 | TC-001128 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC15 | TC-001129 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC16 | TC-001130 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS3_TC17 | TC-001145 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC18 | TC-001146 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS3_TC19 | TC-001147 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC20 | TC-001131 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC21 | TC-001132 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC22 | TC-001133 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC23 | TC-001134 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC24 | TC-001135 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS3_TC25 | TC-001136 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC26 | TC-001148 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC27 | TC-001149 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC28 | TC-001137 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC29 | TC-001138 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC30 | TC-001139 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC31 | TC-001140 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS3_TC32 | TC-001141 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC33 | TC-001150 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC34 | TC-001142 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS3_TC35 | TC-001153 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS4 BUYPASS ATL105 American Express Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-001154 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC2 | TC-001155 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC3 | TC-001156 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS4_TC4 | TC-001157 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS4_TC5 | TC-001158 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC6 | TC-001159 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC7 | TC-001175 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS4_TC8 | TC-001176 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC9 | TC-001160 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC10 | TC-001161 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC11 | TC-001162 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC12 | TC-001163 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS4_TC13 | TC-001164 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS4_TC14 | TC-001177 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC15 | TC-001178 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS4_TC16 | TC-001179 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS4_TC17 | TC-001165 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS4_TC18 | TC-001166 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC19 | TC-001167 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC20 | TC-001168 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC21 | TC-001169 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS4_TC22 | TC-001170 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS4_TC23 | TC-001180 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC24 | TC-001181 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC25 | TC-001171 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS4_TC26 | TC-001172 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS4_TC27 | TC-001173 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC28 | TC-001182 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC29 | TC-001174 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS4_TC30 | TC-001183 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS5 BUYPASS ATL105 American Express Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-001184 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-001185 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed 3 failed swipes → keyed entry → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC3 | TC-001186 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Swiped/keyed at RFID-equipped terminal → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC4 | TC-001187 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC5 | TC-001188 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS5_TC6 | TC-001189 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS5_TC7 | TC-001190 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS5_TC8 | TC-001191 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC9 | TC-001210 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS5_TC10 | TC-001211 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC11 | TC-001192 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC12 | TC-001193 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC13 | TC-001194 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC14 | TC-001195 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC15 | TC-001196 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS5_TC16 | TC-001212 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC17 | TC-001213 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS5_TC18 | TC-001214 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC19 | TC-001197 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC20 | TC-001198 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC21 | TC-001199 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC22 | TC-001200 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC23 | TC-001201 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS5_TC24 | TC-001202 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC25 | TC-001215 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC26 | TC-001216 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC27 | TC-001203 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase with AVS mismatch → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC28 | TC-001204 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase with AVS mismatch → merchant reverses | T9 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS5_TC29 | TC-001205 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS5_TC30 | TC-001206 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS5_TC31 | TC-001207 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC32 | TC-001217 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC33 | TC-001208 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS5_TC34 | TC-001209 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed entry before 3 swipes → refused at device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC35 | TC-001218 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed inside sale with card type 174 → device prompts for card verification data | T11 | - | n/a (no host message) | ready |
| BR1_TS5_TC36 | TC-001219 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS6 BUYPASS ATL105 American Express Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-001220 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC2 | TC-001221 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → keyed fallback (79) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC3 | TC-001241 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC4 | TC-001222 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC5 | TC-001223 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS6_TC6 | TC-001224 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS6_TC7 | TC-001225 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS6_TC8 | TC-001226 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC9 | TC-001242 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS6_TC10 | TC-001243 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC11 | TC-001227 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC12 | TC-001228 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC13 | TC-001229 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC14 | TC-001230 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS6_TC15 | TC-001231 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS6_TC16 | TC-001244 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC17 | TC-001245 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS6_TC18 | TC-001246 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS6_TC19 | TC-001232 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS6_TC20 | TC-001233 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC21 | TC-001234 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC22 | TC-001235 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC23 | TC-001236 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS6_TC24 | TC-001237 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS6_TC25 | TC-001247 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC26 | TC-001248 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC27 | TC-001238 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS6_TC28 | TC-001239 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS6_TC29 | TC-001240 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC30 | TC-001249 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC31 | TC-001250 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS7 BUYPASS ATL105 American Express Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-001251 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC2 | TC-001252 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC3 | TC-001269 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC4 | TC-001270 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC5 | TC-001253 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC6 | TC-001254 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC7 | TC-001255 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC8 | TC-001256 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-001257 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC10 | TC-001271 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC11 | TC-001272 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS7_TC12 | TC-001273 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC13 | TC-001258 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC14 | TC-001259 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC15 | TC-001260 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC16 | TC-001261 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC17 | TC-001262 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS7_TC18 | TC-001263 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC19 | TC-001274 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC20 | TC-001275 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC21 | TC-001264 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase with AVS mismatch → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC22 | TC-001265 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase with AVS mismatch → merchant reverses | T9 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC23 | TC-001266 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC24 | TC-001267 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC25 | TC-001268 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC26 | TC-001276 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC27 | TC-001277 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS8 BUYPASS ATL105 American Express Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-001278 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-001279 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC3 | TC-001280 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC4 | TC-001281 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-001282 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC6 | TC-001283 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC7 | TC-001298 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC8 | TC-001299 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC9 | TC-001284 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC10 | TC-001285 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC11 | TC-001286 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC12 | TC-001287 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC13 | TC-001288 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS8_TC14 | TC-001300 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC15 | TC-001301 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC16 | TC-001302 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC17 | TC-001289 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC18 | TC-001290 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC19 | TC-001291 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC20 | TC-001292 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC21 | TC-001293 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC22 | TC-001294 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC23 | TC-001303 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC24 | TC-001304 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC25 | TC-001295 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC26 | TC-001296 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC27 | TC-001297 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC28 | TC-001305 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC29 | TC-001306 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 American Express Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-001307 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase approved | T1 | 6 | 1: ? (FLAG) | ready |
| BR1_TS9_TC2 | TC-001308 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase → Mail/Phone Reversal | T5 | 6 -> C | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS9_TC3 | TC-001309 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase, no response → Time-out Reversal | T6 | 6 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS9_TC4 | TC-001310 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase with AVS mismatch → approved | T9 | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC5 | TC-001311 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase with AVS mismatch → Mail/Phone Reversal | T9 | 6 -> C | 1: 0 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-001312 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Partial requested on Mail/Phone Purchase → not partially approved | T10 | 6 | 1: 1 (FLAG) | requires_review |
| BR1_TS9_TC7 | TC-001313 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase → Mail/Phone Reversal declined | T10 | 6 -> C | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS10 BUYPASS ATL105 American Express Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-001314 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase approved | T1 | 6 | 1: ? (FLAG) | pending_sme |
| BR1_TS10_TC2 | TC-001315 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase → Mail/Phone Reversal | T5 | 6 -> C | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC3 | TC-001316 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase, no response → Time-out Reversal | T6 | 6 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-001317 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase with AVS mismatch → approved | T9 | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC5 | TC-001318 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase with AVS mismatch → Mail/Phone Reversal | T9 | 6 -> C | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-001319 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Partial requested on Mail/Phone Purchase → not partially approved | T10 | 6 | 1: 1 (FLAG) | pending_sme |
| BR1_TS10_TC7 | TC-001320 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase → Mail/Phone Reversal declined | T10 | 6 -> C | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 American Express Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-001321 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC2 | TC-001322 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip EMV offline PIN → approved | T3 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC3 | TC-001323 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC4 | TC-001324 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC5 | TC-001325 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC6 | TC-001326 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC7 | TC-001327 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC8 | TC-001328 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS11_TC9 | TC-001329 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS11_TC10 | TC-001330 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC11 | TC-001331 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC12 | TC-001332 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC13 | TC-001333 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC14 | TC-001334 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC15 | TC-001335 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC16 | TC-001336 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 American Express Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-001337 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC2 | TC-001338 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC3 | TC-001339 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC4 | TC-001340 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC5 | TC-001341 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC6 | TC-001342 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC7 | TC-001343 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC8 | TC-001344 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS12_TC9 | TC-001345 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS12_TC10 | TC-001346 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC11 | TC-001347 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC12 | TC-001348 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC13 | TC-001349 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC14 | TC-001350 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC15 | TC-001351 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS12_TC16 | TC-001352 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS13 BUYPASS ATL105 American Express Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-001353 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC2 | TC-001365 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe American Express card swiped → Track 1 sent (default track) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-001366 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe American Express card, Track 1 unreadable → Track 2 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC4 | TC-001354 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC5 | TC-001355 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS13_TC6 | TC-001356 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS13_TC7 | TC-001357 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS13_TC8 | TC-001358 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS13_TC9 | TC-001359 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS13_TC10 | TC-001360 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS13_TC11 | TC-001361 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC12 | TC-001362 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC13 | TC-001363 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC14 | TC-001364 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS13_TC15 | TC-001367 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC16 | TC-001368 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC17 | TC-001369 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC18 | TC-001370 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS14 BUYPASS ATL105 American Express Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-001371 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC2 | TC-001372 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS14_TC3 | TC-001373 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS14_TC4 | TC-001374 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS14_TC5 | TC-001375 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS14_TC6 | TC-001376 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS14_TC7 | TC-001377 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS14_TC8 | TC-001378 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS14_TC9 | TC-001379 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC10 | TC-001380 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC11 | TC-001381 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC12 | TC-001382 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS14_TC13 | TC-001383 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC14 | TC-001384 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC15 | TC-001385 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC16 | TC-001386 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS15 BUYPASS ATL105 American Express Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-001387 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC2 | TC-001388 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed 3 failed swipes → keyed entry → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC3 | TC-001389 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS15_TC4 | TC-001390 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS15_TC5 | TC-001391 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS15_TC6 | TC-001392 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS15_TC7 | TC-001393 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS15_TC8 | TC-001394 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS15_TC9 | TC-001395 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS15_TC10 | TC-001396 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC11 | TC-001397 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC12 | TC-001398 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC13 | TC-001399 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS15_TC14 | TC-001400 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC15 | TC-001401 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC16 | TC-001402 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS15_TC17 | TC-001403 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS16 BUYPASS ATL105 American Express Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-001404 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC2 | TC-001405 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → keyed fallback (79) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC3 | TC-001416 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC4 | TC-001406 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS16_TC5 | TC-001407 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS16_TC6 | TC-001408 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS16_TC7 | TC-001409 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS16_TC8 | TC-001410 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS16_TC9 | TC-001411 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS16_TC10 | TC-001412 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS16_TC11 | TC-001413 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC12 | TC-001414 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC13 | TC-001415 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC14 | TC-001417 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC15 | TC-001418 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC16 | TC-001419 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS16_TC17 | TC-001420 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS17 BUYPASS ATL105 American Express Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-001421 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS17_TC2 | TC-001422 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS17_TC3 | TC-001423 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS17_TC4 | TC-001424 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | needs_client_input |
| BR1_TS17_TC5 | TC-001425 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS17_TC6 | TC-001426 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS17_TC7 | TC-001427 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC8 | TC-001428 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC9 | TC-001429 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC10 | TC-001430 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC11 | TC-001431 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC12 | TC-001432 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS17_TC13 | TC-001433 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 American Express Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-001434 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-001435 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC3 | TC-001436 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC4 | TC-001437 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC5 | TC-001438 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC6 | TC-001439 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | pending_sme |
| BR1_TS18_TC7 | TC-001440 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC8 | TC-001441 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS18_TC9 | TC-001442 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC10 | TC-001443 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC11 | TC-001444 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC12 | TC-001445 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC13 | TC-001446 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC14 | TC-001447 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Cancellation declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC15 | TC-001448 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Purchase Reversal declined | T10 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 1 (ASSERT) | pending_sme |

### BR1_TS19 BUYPASS ATL105 American Express Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-001449 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization Only approved | T1 | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC2 | TC-001450 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Cancellation | T5 | B -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS19_TC3 | TC-001451 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Completion | T5 | - | n/a (no host message) | blocked_spec_gap |
| BR1_TS19_TC4 | TC-001452 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization Only declined | T10 | B | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS19_TC5 | TC-001453 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Cancellation declined | T10 | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS20 BUYPASS ATL105 American Express Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-001454 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization Only approved | T1 | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-001455 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Cancellation | T5 | B -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS20_TC3 | TC-001456 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Completion | T5 | - | n/a (no host message) | blocked_spec_gap |
| BR1_TS20_TC4 | TC-001457 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization Only declined | T10 | B | 1: 1 (ASSERT) | pending_sme |
| BR1_TS20_TC5 | TC-001458 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Cancellation declined | T10 | B -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 American Express Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-001459 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS21_TC2 | TC-001460 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS21_TC3 | TC-001461 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS21_TC4 | TC-001462 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |

### BR1_TS22 BUYPASS ATL105 American Express Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-001463 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS22_TC2 | TC-001464 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS22_TC3 | TC-001465 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS22_TC4 | TC-001466 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |

### BR1_TS23 BUYPASS ATL105 American Express Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-001467 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS23_TC2 | TC-001468 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS23_TC3 | TC-001469 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS23_TC4 | TC-001470 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS24 BUYPASS ATL105 American Express Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-001471 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS24_TC2 | TC-001472 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS24_TC3 | TC-001473 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS24_TC4 | TC-001474 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS25 BUYPASS ATL105 American Express Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-001475 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS25_TC2 | TC-001476 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS25_TC3 | TC-001477 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS25_TC4 | TC-001478 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS26 BUYPASS ATL105 American Express Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-001479 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS26_TC2 | TC-001480 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS26_TC3 | TC-001481 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS26_TC4 | TC-001482 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS27 BUYPASS ATL105 American Express Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-001483 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS27_TC2 | TC-001484 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-001485 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS27_TC4 | TC-001486 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS28 BUYPASS ATL105 American Express Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-001487 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS28_TC2 | TC-001488 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-001489 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS28_TC4 | TC-001490 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS29 BUYPASS ATL105 American Express Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-001491 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Balance Inquiry answered | T1 | E | 1: ? (FLAG) | needs_client_input |
| BR1_TS29_TC2 | TC-001492 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS30 BUYPASS ATL105 American Express Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-001493 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Balance Inquiry answered | T1 | E | 1: ? (FLAG) | needs_client_input |
| BR1_TS30_TC2 | TC-001494 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS31 BUYPASS ATL105 American Express Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-001495 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS31_TC2 | TC-001496 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS32 BUYPASS ATL105 American Express Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-001497 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS32_TC2 | TC-001498 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS33 BUYPASS ATL105 American Express Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-001499 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Manual / Keyed Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS33_TC2 | TC-001500 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Manual / Keyed Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS34 BUYPASS ATL105 American Express Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-001501 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |
| BR1_TS34_TC2 | TC-001502 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS35 BUYPASS ATL105 American Express Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-001503 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Credential on File Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS35_TC2 | TC-001504 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Credential on File Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

### BR1_TS36 BUYPASS ATL105 American Express Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-001505 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |
| BR1_TS36_TC2 | TC-001506 | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Balance Inquiry declined | T10 | E | 1: 1 (ASSERT) | pending_sme |

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
