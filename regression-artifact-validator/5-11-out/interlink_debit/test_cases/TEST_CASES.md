# Test cases - BR1 BUYPASS ATL105 Interlink Debit

Build `interlink-debit-47c4cd511f`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 24 |
| Test cases | 257 |
| By status | needs_client_input: 60, requires_review: 26, pending_sme: 171 |
| By polarity | Positive: 190, Negative: 67 |
| Verification | PASS: 256, NOT_APPLICABLE: 1 |
| Verification level | PASS: 256, NOT_APPLICABLE: 1 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 118, FLAG: 138, N/A: 1 |
| Scenarios with test cases | 18 / 24 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| T1 Base transaction | 24 |
| T2 Card capture path | 10 |
| T4 Partial approval | 12 |
| T5 Follow-on lifecycle | 42 |
| T6 Time-out & recovery | 48 |
| T8 Settlement | 54 |
| T10 Business-rule decline | 66 |
| T11 Device-only | 1 |

## BR1 BUYPASS ATL105 Interlink Debit


### BR1_TS1 BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-000001 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-000025 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC3 | TC-000024 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC4 | TC-000026 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-000027 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC6 | TC-000015 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC7 | TC-000016 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC8 | TC-000023 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC9 | TC-000002 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-000003 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC11 | TC-000004 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC12 | TC-000005 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-000006 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC14 | TC-000017 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC15 | TC-000018 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC16 | TC-000019 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC17 | TC-000007 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC18 | TC-000008 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC19 | TC-000009 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC20 | TC-000010 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC21 | TC-000011 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC22 | TC-000012 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC23 | TC-000020 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC24 | TC-000021 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC25 | TC-000013 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC26 | TC-000014 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC27 | TC-000022 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC28 | TC-000028 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC29 | TC-000029 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS2 BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-000030 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-000055 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC3 | TC-000031 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-000054 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC5 | TC-000056 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-000057 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC7 | TC-000045 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC8 | TC-000046 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC9 | TC-000053 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-000032 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC11 | TC-000033 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC12 | TC-000034 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC13 | TC-000035 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC14 | TC-000036 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC15 | TC-000047 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC16 | TC-000048 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC17 | TC-000049 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC18 | TC-000037 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC19 | TC-000038 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC20 | TC-000039 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC21 | TC-000040 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-000041 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC23 | TC-000042 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC24 | TC-000050 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC25 | TC-000051 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC26 | TC-000043 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC27 | TC-000044 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC28 | TC-000052 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC29 | TC-000058 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC30 | TC-000059 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS3 BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-000060 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC2 | TC-000084 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS3_TC3 | TC-000083 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS3_TC4 | TC-000085 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS3_TC5 | TC-000086 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC6 | TC-000074 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC7 | TC-000075 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC8 | TC-000082 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC9 | TC-000061 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC10 | TC-000062 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC11 | TC-000063 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS3_TC12 | TC-000064 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC13 | TC-000065 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS3_TC14 | TC-000076 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC15 | TC-000077 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC16 | TC-000078 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS3_TC17 | TC-000066 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC18 | TC-000067 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC19 | TC-000068 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC20 | TC-000069 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC21 | TC-000070 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC22 | TC-000071 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS3_TC23 | TC-000079 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC24 | TC-000080 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS3_TC25 | TC-000072 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC26 | TC-000073 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC27 | TC-000081 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC28 | TC-000087 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit card, 3 failed swipes → transaction ended (no manual entry) | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS3_TC29 | TC-000088 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS3_TC30 | TC-000089 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS4 BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-000090 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC2 | TC-000114 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS4_TC3 | TC-000113 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS4_TC4 | TC-000115 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS4_TC5 | TC-000116 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC6 | TC-000104 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC7 | TC-000105 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC8 | TC-000112 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC9 | TC-000091 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC10 | TC-000092 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC11 | TC-000093 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC12 | TC-000094 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC13 | TC-000095 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS4_TC14 | TC-000106 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC15 | TC-000107 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC16 | TC-000108 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC17 | TC-000096 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC18 | TC-000097 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC19 | TC-000098 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC20 | TC-000099 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC21 | TC-000100 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC22 | TC-000101 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC23 | TC-000109 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC24 | TC-000110 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC25 | TC-000102 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC26 | TC-000103 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC27 | TC-000111 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC28 | TC-000117 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC29 | TC-000118 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS5 BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-000119 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC2 | TC-000144 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS5_TC3 | TC-000133 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC4 | TC-000143 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS5_TC5 | TC-000145 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS5_TC6 | TC-000146 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC7 | TC-000134 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC8 | TC-000135 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC9 | TC-000142 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC10 | TC-000120 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC11 | TC-000121 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC12 | TC-000122 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS5_TC13 | TC-000123 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC14 | TC-000124 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS5_TC15 | TC-000136 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC16 | TC-000137 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC17 | TC-000138 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS5_TC18 | TC-000125 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC19 | TC-000126 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC20 | TC-000127 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC21 | TC-000128 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC22 | TC-000129 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC23 | TC-000130 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS5_TC24 | TC-000139 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC25 | TC-000140 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS5_TC26 | TC-000131 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC27 | TC-000132 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC28 | TC-000141 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC29 | TC-000147 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS5_TC30 | TC-000148 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS6 BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-000149 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC2 | TC-000173 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase with Cashback → approved | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS6_TC3 | TC-000172 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Expired debit card → sent online (no device expiry check) → issuer decides | T2 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS6_TC4 | TC-000174 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase with Cashback partially approved → approved amount covers the purchase only | T4 | 0 | 1: F (ASSERT) | pending_sme |
| BR1_TS6_TC5 | TC-000175 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit partial approval → Purchase Reversal of the approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC6 | TC-000163 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC7 | TC-000164 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC8 | TC-000171 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase → Purchase Reversal (no PIN) | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC9 | TC-000150 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC10 | TC-000151 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC11 | TC-000152 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC12 | TC-000153 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC13 | TC-000154 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS6_TC14 | TC-000165 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC15 | TC-000166 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC16 | TC-000167 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC17 | TC-000155 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC18 | TC-000156 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC19 | TC-000157 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC20 | TC-000158 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC21 | TC-000159 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC22 | TC-000160 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC23 | TC-000168 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC24 | TC-000169 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC25 | TC-000161 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC26 | TC-000162 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC27 | TC-000170 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC28 | TC-000176 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC29 | TC-000177 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Debit Purchase → Purchase Reversal (no PIN) declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS7 BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-000178 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS7_TC2 | TC-000179 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC3 | TC-000182 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC4 | TC-000183 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS7_TC5 | TC-000180 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC6 | TC-000181 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC7 | TC-000184 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC8 | TC-000185 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC9 | TC-000186 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS8 BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-000187 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS8_TC2 | TC-000188 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS8_TC3 | TC-000189 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS8_TC4 | TC-000192 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS8_TC5 | TC-000193 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | needs_client_input |
| BR1_TS8_TC6 | TC-000190 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS8_TC7 | TC-000191 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS8_TC8 | TC-000194 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS8_TC9 | TC-000195 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS8_TC10 | TC-000196 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS9 BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-000197 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS9_TC2 | TC-000198 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS9_TC3 | TC-000201 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS9_TC4 | TC-000202 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS9_TC5 | TC-000199 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS9_TC6 | TC-000200 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS9_TC7 | TC-000203 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS9_TC8 | TC-000204 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS9_TC9 | TC-000205 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS10 BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-000206 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS10_TC2 | TC-000207 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC3 | TC-000210 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-000211 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS10_TC5 | TC-000208 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-000209 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS10_TC7 | TC-000212 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC8 | TC-000213 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS10_TC9 | TC-000214 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS11 BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-000215 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS11_TC2 | TC-000219 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS11_TC3 | TC-000216 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS11_TC4 | TC-000220 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS11_TC5 | TC-000221 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS11_TC6 | TC-000217 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS11_TC7 | TC-000218 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS11_TC8 | TC-000222 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS11_TC9 | TC-000223 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS11_TC10 | TC-000224 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS12 BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-000225 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS12_TC2 | TC-000226 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC3 | TC-000229 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS12_TC4 | TC-000230 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization → Completion unanswered → Completion resent → Decline Code 24 treated as approved | T5 | 3 -> 0 -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 1 (FLAG) | pending_sme |
| BR1_TS12_TC5 | TC-000227 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS12_TC6 | TC-000228 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS12_TC7 | TC-000231 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC8 | TC-000232 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC9 | TC-000233 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Debit Authorization Only → Authorization Only Reversal declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS13 BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-000234 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS13_TC2 | TC-000235 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS13_TC3 | TC-000236 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC4 | TC-000237 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |

### BR1_TS14 BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-000238 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS14_TC2 | TC-000239 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS14_TC3 | TC-000240 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS14_TC4 | TC-000241 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | requires_review |

### BR1_TS15 BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-000242 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS15_TC2 | TC-000243 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS15_TC3 | TC-000244 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS15_TC4 | TC-000245 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS16 BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-000246 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS16_TC2 | TC-000247 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS16_TC3 | TC-000248 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS16_TC4 | TC-000249 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS17 BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-000250 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS17_TC2 | TC-000251 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS17_TC3 | TC-000252 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS17_TC4 | TC-000253 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS18 BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-000254 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS18_TC2 | TC-000255 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC3 | TC-000256 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS18_TC4 | TC-000257 | BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return declined | T10 | 7 -> U | 1: ? (FLAG) ; 2: 1 (ASSERT) | pending_sme |

## Scenarios without test cases

- BR1_TS19 BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip
- BR1_TS20 BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip
- BR1_TS21 BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe
- BR1_TS22 BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe
- BR1_TS23 BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback
- BR1_TS24 BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device

## Follow-on legs (reversal / void / cancellation ...)

24 of 24 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.

## Coverage gaps (6)

| Kind | Scenario / leg / template | Reason |
|---|---|---|
| scenario_without_test_cases | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACT | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACTLESS | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-MAGSTRIPE | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_FALLBACK | no check planned |
| scenario_without_test_cases | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MOBILE | no check planned |
