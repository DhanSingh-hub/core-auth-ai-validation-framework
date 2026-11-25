# Test cases - BR1 BUYPASS ATL105 MasterCard Fleet

Build `mastercard-fleet-a26a6c0310`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 24 |
| Test cases | 307 |
| By status | needs_client_input: 61, requires_review: 62, ready: 36, pending_sme: 148 |
| By polarity | Positive: 217, Negative: 90 |
| Verification | PASS: 289, NOT_APPLICABLE: 18 |
| Verification level | PASS: 289, NOT_APPLICABLE: 18 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 145, FLAG: 144, N/A: 18 |
| Scenarios with test cases | 24 / 24 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| T1 Base transaction | 29 |
| T2 Card capture path | 4 |
| T5 Follow-on lifecycle | 40 |
| T6 Time-out & recovery | 72 |
| T8 Settlement | 72 |
| T10 Business-rule decline | 72 |
| T11 Device-only | 18 |

## BR1 BUYPASS ATL105 MasterCard Fleet


### BR1_TS1 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-000746 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC2 | TC-000747 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC3 | TC-000761 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC4 | TC-000762 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC5 | TC-000748 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC6 | TC-000749 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC7 | TC-000750 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC8 | TC-000751 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC9 | TC-000752 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-000763 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC11 | TC-000764 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC12 | TC-000765 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC13 | TC-000753 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC14 | TC-000754 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC15 | TC-000755 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC16 | TC-000756 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC17 | TC-000757 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS1_TC18 | TC-000758 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC19 | TC-000766 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC20 | TC-000767 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC21 | TC-000759 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS1_TC22 | TC-000760 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC23 | TC-000768 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC24 | TC-000769 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS1_TC25 | TC-000770 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC26 | TC-000771 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS2 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-000772 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC2 | TC-000773 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC3 | TC-000787 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC4 | TC-000788 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC5 | TC-000774 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC6 | TC-000775 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC7 | TC-000776 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC8 | TC-000777 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC9 | TC-000778 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-000789 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC11 | TC-000790 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC12 | TC-000791 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC13 | TC-000779 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC14 | TC-000780 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC15 | TC-000781 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC16 | TC-000782 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC17 | TC-000783 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |
| BR1_TS2_TC18 | TC-000784 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC19 | TC-000792 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC20 | TC-000793 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC21 | TC-000785 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS2_TC22 | TC-000786 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC23 | TC-000794 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC24 | TC-000795 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS2_TC25 | TC-000796 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC26 | TC-000797 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS3 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-000798 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-000821 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 1 → ID Number and Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC3 | TC-000822 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 2 → Vehicle Number and Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC4 | TC-000823 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 3 → Driver ID and Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC5 | TC-000824 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 4 → Odometer prompted → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-000825 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet purchase, Product Type 5 → no fleet prompts → approved | T1 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC7 | TC-000826 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fleet card, Track 2 unreadable → Track 1 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC8 | TC-000799 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC9 | TC-000813 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS3_TC10 | TC-000814 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC11 | TC-000800 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC12 | TC-000801 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC13 | TC-000802 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC14 | TC-000803 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC15 | TC-000804 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS3_TC16 | TC-000815 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC17 | TC-000816 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS3_TC18 | TC-000817 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC19 | TC-000805 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC20 | TC-000806 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC21 | TC-000807 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC22 | TC-000808 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC23 | TC-000809 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS3_TC24 | TC-000810 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC25 | TC-000818 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC26 | TC-000819 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC27 | TC-000811 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC28 | TC-000812 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS3_TC29 | TC-000820 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC30 | TC-000827 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS3_TC31 | TC-000828 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fuel-only fleet card (Product Restriction Code 2) → non-fuel product not sold | T11 | - | n/a (no host message) | requires_review |
| BR1_TS3_TC32 | TC-000829 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC33 | TC-000830 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS4 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-000831 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC2 | TC-000832 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC3 | TC-000846 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC4 | TC-000847 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC5 | TC-000833 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC6 | TC-000834 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC7 | TC-000835 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS4_TC8 | TC-000836 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC9 | TC-000837 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS4_TC10 | TC-000848 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC11 | TC-000849 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC12 | TC-000850 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS4_TC13 | TC-000838 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC14 | TC-000839 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC15 | TC-000840 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC16 | TC-000841 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC17 | TC-000842 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC18 | TC-000843 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS4_TC19 | TC-000851 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC20 | TC-000852 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS4_TC21 | TC-000844 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC22 | TC-000845 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC23 | TC-000853 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC24 | TC-000854 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS4_TC25 | TC-000855 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS4_TC26 | TC-000856 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS5 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-000857 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-000880 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fleet card, 3 failed swipes → keyed entry with Expiration Date, ID and Odometer → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC3 | TC-000858 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC4 | TC-000872 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS5_TC5 | TC-000873 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC6 | TC-000859 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC7 | TC-000860 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC8 | TC-000861 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC9 | TC-000862 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC10 | TC-000863 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS5_TC11 | TC-000874 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC12 | TC-000875 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS5_TC13 | TC-000876 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC14 | TC-000864 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC15 | TC-000865 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC16 | TC-000866 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC17 | TC-000867 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC18 | TC-000868 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS5_TC19 | TC-000869 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC20 | TC-000877 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC21 | TC-000878 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC22 | TC-000870 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS5_TC23 | TC-000871 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS5_TC24 | TC-000879 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC25 | TC-000881 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC26 | TC-000882 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed fleet account number with a wrong check digit → Mod 10 evaluation fails | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC27 | TC-000883 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC28 | TC-000884 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS6 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-000885 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC2 | TC-000886 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC3 | TC-000900 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC4 | TC-000901 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC5 | TC-000887 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC6 | TC-000888 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC7 | TC-000889 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS6_TC8 | TC-000890 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC9 | TC-000891 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS6_TC10 | TC-000902 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC11 | TC-000903 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC12 | TC-000904 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS6_TC13 | TC-000892 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC14 | TC-000893 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC15 | TC-000894 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC16 | TC-000895 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC17 | TC-000896 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC18 | TC-000897 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS6_TC19 | TC-000905 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC20 | TC-000906 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS6_TC21 | TC-000898 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC22 | TC-000899 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC23 | TC-000907 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC24 | TC-000908 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS6_TC25 | TC-000909 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS6_TC26 | TC-000910 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS7 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-000911 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC2 | TC-000912 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC3 | TC-000926 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC4 | TC-000927 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC5 | TC-000913 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC6 | TC-000914 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC7 | TC-000915 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS7_TC8 | TC-000916 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC9 | TC-000917 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS7_TC10 | TC-000928 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC11 | TC-000929 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC12 | TC-000930 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS7_TC13 | TC-000918 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC14 | TC-000919 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC15 | TC-000920 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC16 | TC-000921 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC17 | TC-000922 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC18 | TC-000923 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS7_TC19 | TC-000931 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC20 | TC-000932 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS7_TC21 | TC-000924 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC22 | TC-000925 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC23 | TC-000933 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC24 | TC-000934 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS7_TC25 | TC-000935 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS7_TC26 | TC-000936 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS8 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-000937 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-000938 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC3 | TC-000952 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC4 | TC-000953 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-000939 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC6 | TC-000940 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC7 | TC-000941 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC8 | TC-000942 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC9 | TC-000943 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS8_TC10 | TC-000954 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC11 | TC-000955 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC12 | TC-000956 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC13 | TC-000944 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC14 | TC-000945 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC15 | TC-000946 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC16 | TC-000947 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC17 | TC-000948 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC18 | TC-000949 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC19 | TC-000957 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC20 | TC-000958 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC21 | TC-000950 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC22 | TC-000951 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC23 | TC-000959 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC24 | TC-000960 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS8_TC25 | TC-000961 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal declined | T10 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC26 | TC-000962 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) declined | T10 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-000963 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS9_TC2 | TC-000964 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC3 | TC-000965 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS9_TC4 | TC-000966 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS9_TC5 | TC-000967 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-000968 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS9_TC7 | TC-000969 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS9_TC8 | TC-000970 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS9_TC9 | TC-000971 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS10 BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-000972 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS10_TC2 | TC-000973 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS10_TC3 | TC-000974 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS10_TC4 | TC-000975 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS10_TC5 | TC-000976 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS10_TC6 | TC-000977 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS10_TC7 | TC-000978 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS10_TC8 | TC-000979 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS10_TC9 | TC-000980 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS11 BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-000981 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC2 | TC-000987 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Fleet card, Track 2 unreadable → Track 1 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC3 | TC-000982 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS11_TC4 | TC-000983 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS11_TC5 | TC-000984 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS11_TC6 | TC-000985 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS11_TC7 | TC-000986 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC8 | TC-000988 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS11_TC9 | TC-000989 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS11_TC10 | TC-000990 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS12 BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-000991 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS12_TC2 | TC-000992 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC3 | TC-000993 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS12_TC4 | TC-000994 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS12_TC5 | TC-000995 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS12_TC6 | TC-000996 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS12_TC7 | TC-000997 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS12_TC8 | TC-000998 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS12_TC9 | TC-000999 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS13 BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-001000 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC2 | TC-001006 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Fleet card, 3 failed swipes → keyed entry with Expiration Date, ID and Odometer → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-001001 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS13_TC4 | TC-001002 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS13_TC5 | TC-001003 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS13_TC6 | TC-001004 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS13_TC7 | TC-001005 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC8 | TC-001007 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS13_TC9 | TC-001008 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS13_TC10 | TC-001009 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | needs_client_input |

### BR1_TS14 BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-001010 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS14_TC2 | TC-001011 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS14_TC3 | TC-001012 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS14_TC4 | TC-001013 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS14_TC5 | TC-001014 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS14_TC6 | TC-001015 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS14_TC7 | TC-001016 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS14_TC8 | TC-001017 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS14_TC9 | TC-001018 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS15 BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-001019 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS15_TC2 | TC-001020 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS15_TC3 | TC-001021 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS15_TC4 | TC-001022 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS15_TC5 | TC-001023 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS15_TC6 | TC-001024 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS15_TC7 | TC-001025 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS15_TC8 | TC-001026 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS15_TC9 | TC-001027 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS16 BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-001028 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS16_TC2 | TC-001029 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS16_TC3 | TC-001030 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS16_TC4 | TC-001031 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS16_TC5 | TC-001032 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS16_TC6 | TC-001033 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS16_TC7 | TC-001034 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Expired fleet card → expiration date validated at the device | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS16_TC8 | TC-001035 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only declined | T10 | 3 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS16_TC9 | TC-001036 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion declined | T10 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 1 (ASSERT) | pending_sme |

### BR1_TS17 BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-001037 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS17_TC2 | TC-001038 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS18 BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip (valid_conditional)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-001039 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | needs_client_input |
| BR1_TS18_TC2 | TC-001040 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS19 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-001041 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS19_TC2 | TC-001042 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS20 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-001043 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS20_TC2 | TC-001044 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS21 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-001045 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS21_TC2 | TC-001046 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | needs_client_input |

### BR1_TS22 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-001047 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS22_TC2 | TC-001048 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS23 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-001049 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS23_TC2 | TC-001050 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

### BR1_TS24 BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-001051 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS24_TC2 | TC-001052 | BUYPASS ATL105 MasterCard Fleet MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return declined | T10 | 7 | 1: 1 (ASSERT) | pending_sme |

## Planned but not generated

- BR1_TS17 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS17 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS18 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS18 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS19 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS19 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS20 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS20 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS21 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS21 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS22 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS22 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS23 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS23 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS24 T5-RET-VOID: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping
- BR1_TS24 T5-RET-VOID-DECLINED: follow-on transaction(s) ['VOID_RETURN'] not supported for this program/network in the dimension mapping

## Follow-on legs (reversal / void / cancellation ...)

16 of 16 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
