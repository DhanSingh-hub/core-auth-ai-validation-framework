# Test cases - BR1 BUYPASS ATL105 Maestro Direct Debit

Build `buypass-atl105-maestro-direct-debit-c63544518a`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 28 |
| Test cases | 278 |
| By status | needs_client_input: 48, requires_review: 26, ready: 6, pending_sme: 198 |
| By polarity | Positive: 221, Negative: 57 |
| Verification | PASS: 249, NOT_APPLICABLE: 29 |
| Verification level | PASS: 249, NOT_APPLICABLE: 29 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 88, FLAG: 161, N/A: 29 |
| Scenarios with test cases | 21 / 28 |
| Validation | passed |

## By type

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

## BR1 BUYPASS ATL105 Maestro Direct Debit


### BR1_TS1 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-003165 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-003191 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC3 | TC-003190 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC4 | TC-003192 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-003193 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC6 | TC-003179 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC7 | TC-003180 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC8 | TC-003189 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC9 | TC-003166 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-003167 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC11 | TC-003168 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC12 | TC-003169 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-003170 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC14 | TC-003181 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC15 | TC-003182 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC16 | TC-003183 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-003171 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC18 | TC-003172 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC19 | TC-003173 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC20 | TC-003174 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC21 | TC-003175 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC22 | TC-003176 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC23 | TC-003184 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC24 | TC-003185 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC25 | TC-003177 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC26 | TC-003178 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC27 | TC-003186 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC28 | TC-003187 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS1_TC29 | TC-003188 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS2 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-003194 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-003221 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC3 | TC-003195 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-003220 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC5 | TC-003222 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-003223 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC7 | TC-003209 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC8 | TC-003210 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC9 | TC-003219 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-003196 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC11 | TC-003197 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC12 | TC-003198 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC13 | TC-003199 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC14 | TC-003200 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC15 | TC-003211 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC16 | TC-003212 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC17 | TC-003213 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC18 | TC-003201 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC19 | TC-003202 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC20 | TC-003203 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC21 | TC-003204 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-003205 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC23 | TC-003206 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC24 | TC-003214 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC25 | TC-003215 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC26 | TC-003207 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC27 | TC-003208 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-003216 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-003217 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS2_TC30 | TC-003218 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS3 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-003224 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC2 | TC-003250 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS3_TC3 | TC-003249 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS3_TC4 | TC-003251 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS3_TC5 | TC-003252 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC6 | TC-003238 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC7 | TC-003239 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC8 | TC-003248 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC9 | TC-003225 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC10 | TC-003226 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC11 | TC-003227 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC12 | TC-003228 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC13 | TC-003229 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS3_TC14 | TC-003240 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC15 | TC-003241 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC16 | TC-003242 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC17 | TC-003230 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC18 | TC-003231 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC19 | TC-003232 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC20 | TC-003233 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC21 | TC-003234 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC22 | TC-003235 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC23 | TC-003243 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC24 | TC-003244 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC25 | TC-003236 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC26 | TC-003237 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC27 | TC-003245 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC28 | TC-003246 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS3_TC29 | TC-003247 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS3_TC30 | TC-003253 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit card, 3 failed swipes → transaction ended (no manual entry) | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS4 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-003254 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC2 | TC-003280 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS4_TC3 | TC-003279 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS4_TC4 | TC-003281 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS4_TC5 | TC-003282 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC6 | TC-003268 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC7 | TC-003269 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC8 | TC-003278 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC9 | TC-003255 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC10 | TC-003256 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC11 | TC-003257 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC12 | TC-003258 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC13 | TC-003259 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS4_TC14 | TC-003270 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC15 | TC-003271 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC16 | TC-003272 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC17 | TC-003260 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC18 | TC-003261 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC19 | TC-003262 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC20 | TC-003263 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC21 | TC-003264 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC22 | TC-003265 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC23 | TC-003273 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC24 | TC-003274 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC25 | TC-003266 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC26 | TC-003267 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC27 | TC-003275 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC28 | TC-003276 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS4_TC29 | TC-003277 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS5 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-003283 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC2 | TC-003310 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS5_TC3 | TC-003297 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC4 | TC-003309 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS5_TC5 | TC-003311 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS5_TC6 | TC-003312 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC7 | TC-003298 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC8 | TC-003299 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC9 | TC-003308 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC10 | TC-003284 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC11 | TC-003285 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC12 | TC-003286 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC13 | TC-003287 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC14 | TC-003288 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS5_TC15 | TC-003300 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC16 | TC-003301 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC17 | TC-003302 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC18 | TC-003289 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC19 | TC-003290 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC20 | TC-003291 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC21 | TC-003292 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC22 | TC-003293 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC23 | TC-003294 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC24 | TC-003303 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC25 | TC-003304 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC26 | TC-003295 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC27 | TC-003296 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC28 | TC-003305 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC29 | TC-003306 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS5_TC30 | TC-003307 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS6 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-003313 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC2 | TC-003339 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS6_TC3 | TC-003338 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS6_TC4 | TC-003340 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS6_TC5 | TC-003341 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC6 | TC-003327 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC7 | TC-003328 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC8 | TC-003337 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC9 | TC-003314 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC10 | TC-003315 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC11 | TC-003316 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC12 | TC-003317 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC13 | TC-003318 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS6_TC14 | TC-003329 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC15 | TC-003330 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC16 | TC-003331 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC17 | TC-003319 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC18 | TC-003320 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC19 | TC-003321 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC20 | TC-003322 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC21 | TC-003323 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC22 | TC-003324 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC23 | TC-003332 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC24 | TC-003333 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC25 | TC-003325 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC26 | TC-003326 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC27 | TC-003334 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC28 | TC-003335 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS6_TC29 | TC-003336 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS7 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-003342 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC2 | TC-003368 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS7_TC3 | TC-003367 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS7_TC4 | TC-003369 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS7_TC5 | TC-003370 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC6 | TC-003356 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC7 | TC-003357 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC8 | TC-003366 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC9 | TC-003343 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC10 | TC-003344 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC11 | TC-003345 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC12 | TC-003346 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC13 | TC-003347 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS7_TC14 | TC-003358 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC15 | TC-003359 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC16 | TC-003360 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC17 | TC-003348 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC18 | TC-003349 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC19 | TC-003350 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC20 | TC-003351 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC21 | TC-003352 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC22 | TC-003353 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC23 | TC-003361 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC24 | TC-003362 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC25 | TC-003354 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC26 | TC-003355 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC27 | TC-003363 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC28 | TC-003364 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS7_TC29 | TC-003365 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS8 BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-003371 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS8_TC2 | TC-003372 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS8_TC3 | TC-003376 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS8_TC4 | TC-003377 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS8_TC5 | TC-003373 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS8_TC6 | TC-003374 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS8_TC7 | TC-003375 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS9 BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-003378 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS9_TC2 | TC-003379 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS9_TC3 | TC-003380 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC4 | TC-003384 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS9_TC5 | TC-003385 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS9_TC6 | TC-003381 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS9_TC7 | TC-003382 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC8 | TC-003383 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS10 BUYPASS ATL105 Maestro Direct Debit Authorization Only – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-003386 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS10_TC2 | TC-003387 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC3 | TC-003391 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-003392 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-003388 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-003389 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC7 | TC-003390 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS11 BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-003393 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS11_TC2 | TC-003394 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS11_TC3 | TC-003398 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS11_TC4 | TC-003399 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS11_TC5 | TC-003395 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS11_TC6 | TC-003396 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS11_TC7 | TC-003397 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS12 BUYPASS ATL105 Maestro Direct Debit Authorization Only – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-003400 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS12_TC2 | TC-003404 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS12_TC3 | TC-003401 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC4 | TC-003406 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS12_TC5 | TC-003407 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS12_TC6 | TC-003402 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS12_TC7 | TC-003403 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS12_TC8 | TC-003405 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS13 BUYPASS ATL105 Maestro Direct Debit Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-003408 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS13_TC2 | TC-003409 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS13_TC3 | TC-003413 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS13_TC4 | TC-003414 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS13_TC5 | TC-003410 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS13_TC6 | TC-003411 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS13_TC7 | TC-003412 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS14 BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-003415 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS14_TC2 | TC-003416 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS14_TC3 | TC-003420 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS14_TC4 | TC-003421 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS14_TC5 | TC-003417 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS14_TC6 | TC-003418 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS14_TC7 | TC-003419 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS15 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-003422 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS15_TC2 | TC-003423 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS15_TC3 | TC-003424 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS16 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-003425 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS16_TC2 | TC-003426 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS16_TC3 | TC-003427 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS17 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-003428 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS17_TC2 | TC-003429 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS17_TC3 | TC-003430 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS18 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-003431 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS18_TC2 | TC-003432 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC3 | TC-003433 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS19 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-003434 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS19_TC2 | TC-003435 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS19_TC3 | TC-003436 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS20 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-003437 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS20_TC2 | TC-003438 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS20_TC3 | TC-003439 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS21 BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-003440 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS21_TC2 | TC-003441 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS21_TC3 | TC-003442 | BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

## Scenarios without test cases

- BR1_TS22 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contact Chip
- BR1_TS23 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contactless Chip
- BR1_TS24 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Magnetic Stripe
- BR1_TS25 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Magnetic Stripe
- BR1_TS26 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Chip Fallback
- BR1_TS27 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Credential on File
- BR1_TS28 BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Mobile Commerce Device

## Follow-on legs (reversal / void / cancellation ...)

28 of 28 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.

## Coverage gaps (7)

| Kind | Scenario / leg / template | Reason |
|---|---|---|
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-EMV_CONTACT | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-EMV_CONTACTLESS | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-MAGSTRIPE | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-EMV_FALLBACK | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-CREDENTIAL_ON_FILE | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-MAESTRO_DIRECT-BALANCE_INQUIRY-CONTACTLESS_MOBILE | no check planned |
