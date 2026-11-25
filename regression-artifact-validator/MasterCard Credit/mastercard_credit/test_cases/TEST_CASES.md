# Test cases - BR1 BUYPASS ATL105 MasterCard Credit

Build `buypass-atl105-mastercard-credit-bada93f456`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` 2026-10-30.1.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 36 |
| Test cases | 450 |
| By status | ready: 180, needs_client_input: 61, requires_review: 146, pending_sme: 61, blocked_spec_gap: 2 |
| By polarity | Positive: 345, Negative: 105 |
| Verification | PASS: 408, NOT_APPLICABLE: 42 |
| Verification level | PASS: 408, NOT_APPLICABLE: 42 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | ASSERT: 147, FLAG: 261, N/A: 42 |
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
| T10 Business-rule decline | 66 |
| T11 Device-only | 40 |

## BR1 BUYPASS ATL105 MasterCard Credit


### BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS1_TC1 | TC-001722 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC2 | TC-001748 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS1_TC3 | TC-001723 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired EMV card → sent online → host decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC4 | TC-001724 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip EMV online PIN → approved | T3 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC5 | TC-001725 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip EMV offline PIN → approved | T3 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS1_TC6 | TC-001726 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC7 | TC-001727 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS1_TC8 | TC-001728 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS1_TC9 | TC-001729 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS1_TC10 | TC-001730 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS1_TC11 | TC-001749 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS1_TC12 | TC-001750 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC13 | TC-001731 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS1_TC14 | TC-001732 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS1_TC15 | TC-001733 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS1_TC16 | TC-001734 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS1_TC17 | TC-001735 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS1_TC18 | TC-001751 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC19 | TC-001752 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS1_TC20 | TC-001753 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS1_TC21 | TC-001736 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC22 | TC-001737 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC23 | TC-001738 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC24 | TC-001739 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC25 | TC-001740 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS1_TC26 | TC-001741 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS1_TC27 | TC-001754 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC28 | TC-001755 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS1_TC29 | TC-001742 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC30 | TC-001743 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS1_TC31 | TC-001744 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS1_TC32 | TC-001745 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS1_TC33 | TC-001746 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS1_TC34 | TC-001747 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS1_TC35 | TC-001756 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS1_TC36 | TC-001757 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS1_TC37 | TC-001758 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS2_TC1 | TC-001759 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC2 | TC-001785 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS2_TC3 | TC-001760 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS2_TC4 | TC-001761 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Expired EMV card → sent online → host decides | T2 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC5 | TC-001762 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip EMV online PIN → approved | T3 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC6 | TC-001763 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC7 | TC-001764 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS2_TC8 | TC-001765 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS2_TC9 | TC-001766 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS2_TC10 | TC-001767 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS2_TC11 | TC-001786 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS2_TC12 | TC-001787 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC13 | TC-001768 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS2_TC14 | TC-001769 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS2_TC15 | TC-001770 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS2_TC16 | TC-001771 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS2_TC17 | TC-001772 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS2_TC18 | TC-001788 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC19 | TC-001789 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS2_TC20 | TC-001790 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS2_TC21 | TC-001773 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC22 | TC-001774 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC23 | TC-001775 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC24 | TC-001776 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC25 | TC-001777 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS2_TC26 | TC-001778 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS2_TC27 | TC-001791 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC28 | TC-001792 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS2_TC29 | TC-001779 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC30 | TC-001780 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS2_TC31 | TC-001781 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS2_TC32 | TC-001782 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS2_TC33 | TC-001783 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS2_TC34 | TC-001784 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS2_TC35 | TC-001793 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS2_TC36 | TC-001794 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS2_TC37 | TC-001795 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS3_TC1 | TC-001796 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC2 | TC-001822 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS3_TC3 | TC-001797 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Track 2 unreadable → Track 1 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC4 | TC-001798 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Swiped/keyed at RFID-equipped terminal → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS3_TC5 | TC-001799 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC6 | TC-001800 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS3_TC7 | TC-001801 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS3_TC8 | TC-001802 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS3_TC9 | TC-001803 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC10 | TC-001823 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS3_TC11 | TC-001824 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC12 | TC-001804 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC13 | TC-001805 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC14 | TC-001806 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS3_TC15 | TC-001807 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC16 | TC-001808 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS3_TC17 | TC-001825 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC18 | TC-001826 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Line down → Purchase approved off-line (stand-in) → TOR → off-line Purchase forwarded (Deferred 'Y') | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (FLAG) | requires_review |
| BR1_TS3_TC19 | TC-001827 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS3_TC20 | TC-001828 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS3_TC21 | TC-001809 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC22 | TC-001810 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC23 | TC-001811 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC24 | TC-001812 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC25 | TC-001813 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS3_TC26 | TC-001814 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS3_TC27 | TC-001829 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC28 | TC-001830 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS3_TC29 | TC-001815 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe FSA/HRA healthcare purchase → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC30 | TC-001816 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe FSA/HRA partial approval → split tender | T9 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS3_TC31 | TC-001817 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS3_TC32 | TC-001818 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS3_TC33 | TC-001819 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS3_TC34 | TC-001820 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS3_TC35 | TC-001831 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS3_TC36 | TC-001821 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS3_TC37 | TC-001832 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS3_TC38 | TC-001833 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS4_TC1 | TC-001834 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS4_TC2 | TC-001856 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS4_TC3 | TC-001835 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC4 | TC-001836 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS4_TC5 | TC-001837 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS4_TC6 | TC-001838 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS4_TC7 | TC-001839 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC8 | TC-001857 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS4_TC9 | TC-001858 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC10 | TC-001840 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC11 | TC-001841 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC12 | TC-001842 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS4_TC13 | TC-001843 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS4_TC14 | TC-001844 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS4_TC15 | TC-001859 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS4_TC16 | TC-001860 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS4_TC17 | TC-001861 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS4_TC18 | TC-001845 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS4_TC19 | TC-001846 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC20 | TC-001847 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC21 | TC-001848 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC22 | TC-001849 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS4_TC23 | TC-001850 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS4_TC24 | TC-001862 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC25 | TC-001863 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS4_TC26 | TC-001851 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS4_TC27 | TC-001852 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS4_TC28 | TC-001853 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS4_TC29 | TC-001854 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS4_TC30 | TC-001864 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS4_TC31 | TC-001855 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS4_TC32 | TC-001865 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS4_TC33 | TC-001866 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Magnetic Stripe Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS5_TC1 | TC-001867 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC2 | TC-001894 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS5_TC3 | TC-001868 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed 3 failed swipes → keyed entry → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC4 | TC-001869 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Swiped/keyed at RFID-equipped terminal → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS5_TC5 | TC-001870 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC6 | TC-001871 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS5_TC7 | TC-001872 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS5_TC8 | TC-001873 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS5_TC9 | TC-001874 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC10 | TC-001895 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS5_TC11 | TC-001896 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC12 | TC-001875 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC13 | TC-001876 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC14 | TC-001877 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS5_TC15 | TC-001878 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC16 | TC-001879 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS5_TC17 | TC-001897 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC18 | TC-001898 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS5_TC19 | TC-001899 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS5_TC20 | TC-001880 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC21 | TC-001881 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC22 | TC-001882 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC23 | TC-001883 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC24 | TC-001884 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS5_TC25 | TC-001885 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS5_TC26 | TC-001900 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC27 | TC-001901 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS5_TC28 | TC-001886 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase with AVS mismatch → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS5_TC29 | TC-001887 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase with AVS mismatch → merchant reverses | T9 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS5_TC30 | TC-001888 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS5_TC31 | TC-001889 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS5_TC32 | TC-001890 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS5_TC33 | TC-001891 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS5_TC34 | TC-001902 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS5_TC35 | TC-001892 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS5_TC36 | TC-001893 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed entry before 3 swipes → refused at device | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC37 | TC-001903 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS5_TC38 | TC-001904 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Keyed inside sale with card type 174 → device prompts for card verification data | T11 | - | n/a (no host message) | ready |
| BR1_TS5_TC39 | TC-001905 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS6_TC1 | TC-001906 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC2 | TC-001928 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS6_TC3 | TC-001907 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → keyed fallback (79) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC4 | TC-001929 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC5 | TC-001930 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Chip unreadable → swiped fallback (80), Track 2 unreadable → Track 1 sent → approved | T2 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS6_TC6 | TC-001908 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC7 | TC-001909 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS6_TC8 | TC-001910 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | requires_review |
| BR1_TS6_TC9 | TC-001911 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | needs_client_input |
| BR1_TS6_TC10 | TC-001912 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC11 | TC-001931 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS6_TC12 | TC-001932 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC13 | TC-001913 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC14 | TC-001914 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC15 | TC-001915 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS6_TC16 | TC-001916 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS6_TC17 | TC-001917 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS6_TC18 | TC-001933 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS6_TC19 | TC-001934 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS6_TC20 | TC-001935 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS6_TC21 | TC-001918 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS6_TC22 | TC-001919 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC23 | TC-001920 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC24 | TC-001921 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC25 | TC-001922 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS6_TC26 | TC-001923 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS6_TC27 | TC-001936 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC28 | TC-001937 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS6_TC29 | TC-001924 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS6_TC30 | TC-001925 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS6_TC31 | TC-001926 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS6_TC32 | TC-001927 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS6_TC33 | TC-001938 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS6_TC34 | TC-001939 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS6_TC35 | TC-001940 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS7_TC1 | TC-001941 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase approved | T1 | 0 | 1: 0 (ASSERT) | ready |
| BR1_TS7_TC2 | TC-001965 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | requires_review |
| BR1_TS7_TC3 | TC-001942 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS7_TC4 | TC-001966 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS7_TC5 | TC-001967 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC6 | TC-001943 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS7_TC7 | TC-001944 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS7_TC8 | TC-001945 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS7_TC9 | TC-001946 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS7_TC10 | TC-001947 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | ready |
| BR1_TS7_TC11 | TC-001968 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC12 | TC-001969 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS7_TC13 | TC-001970 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | ready |
| BR1_TS7_TC14 | TC-001948 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store card (cardholder-initiated) → repeat cardholder-initiated purchase | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS7_TC15 | TC-001949 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File $0.00 Account Verification → merchant-initiated purchase | T7 | A -> 0 | 1: 0 (FLAG) ; 2: 0 (ASSERT) | requires_review |
| BR1_TS7_TC16 | TC-001950 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Recurring standing order: first → merchant-initiated repeat | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS7_TC17 | TC-001951 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Recurring subscription: first → merchant-initiated repeat | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS7_TC18 | TC-001952 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Installment: first → merchant-initiated installment | T7 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS7_TC19 | TC-001953 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC20 | TC-001954 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC21 | TC-001955 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC22 | TC-001956 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC23 | TC-001957 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | ready |
| BR1_TS7_TC24 | TC-001958 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | requires_review |
| BR1_TS7_TC25 | TC-001971 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC26 | TC-001972 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | requires_review |
| BR1_TS7_TC27 | TC-001959 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase with AVS mismatch → approved | T9 | 0 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS7_TC28 | TC-001960 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase with AVS mismatch → merchant reverses | T9 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS7_TC29 | TC-001961 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | ready |
| BR1_TS7_TC30 | TC-001962 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | ready |
| BR1_TS7_TC31 | TC-001963 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS7_TC32 | TC-001964 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | requires_review |
| BR1_TS7_TC33 | TC-001973 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | needs_client_input |
| BR1_TS7_TC34 | TC-001974 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BR1_TS7_TC35 | TC-001975 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS8_TC1 | TC-001976 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved | T1 | 0 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC2 | TC-001997 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device MasterCard fleet card at a non-fleet merchant → processed as a regular MasterCard transaction | T1 | 0 | 1: 0 (FLAG) | pending_sme |
| BR1_TS8_TC3 | TC-001977 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → remainder on 2nd card (split tender) | T4 | 0 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC4 | TC-001978 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Purchase Reversal of approved amount | T4 | 0 -> 8 | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC5 | TC-001979 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Time-out Reversal | T4 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC6 | TC-001980 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial not supported (indicator 0) → full approval or decline | T4 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC7 | TC-001981 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal | T5 | 0 -> 8 | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC8 | TC-001998 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → next Purchase on the same device (new Sequence Number) | T5 | 0 -> 0 | 1: 0 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC9 | TC-001999 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Void declined → Merchandise Return | T5 | 0 -> 8 -> 7 | 1: 0 (ASSERT) ; 2: 1 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC10 | TC-001982 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC11 | TC-001983 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC12 | TC-001984 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal | T6 | 0 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS8_TC13 | TC-001985 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC14 | TC-001986 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3) | T6 | 0 -> Z -> Z -> Z | 1: no response expected ; 2: no response expected ; 3: no response expected ; 4: 0 (FLAG) | pending_sme |
| BR1_TS8_TC15 | TC-002000 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Fourth consecutive time-out → line down → TOR sent when the line is back → next Purchase | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC16 | TC-002001 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined as timed out (4I/6C) → resubmitted with a new Sequence Number | T6 | 0 -> 0 | 1: 1 (FLAG) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC17 | TC-002002 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase late approval → Time-out Reversal → Purchase resubmitted | T6 | 0 -> Z -> 0 | 1: no response expected ; 2: 0 (FLAG) ; 3: 0 (ASSERT) | pending_sme |
| BR1_TS8_TC18 | TC-001987 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC19 | TC-001988 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal → Settlement | T8 | 0 -> 8 -> 990 | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC20 | TC-001989 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase + Merchandise Return → Settlement | T8 | 0 -> 7 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC21 | TC-001990 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR → Settlement | T8 | 0 -> Z -> 990 | 1: no response expected ; 2: 0 (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC22 | TC-001991 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Read-only totals (333333) | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC23 | TC-001992 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Auto-close at cut time | T8 | 0 -> 990 | 1: 0 (ASSERT) ; 2: ? (FLAG) | pending_sme |
| BR1_TS8_TC24 | TC-002003 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of the full amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC25 | TC-002004 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Settlement (read and cut 222222) → Merchandise Return of part of the amount | T8 | 0 -> 990 -> 7 | 1: 0 (ASSERT) ; 2: ? (FLAG) ; 3: ? (FLAG) | pending_sme |
| BR1_TS8_TC26 | TC-001993 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal with no original → declined (unmatched) | T10 | Z | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC27 | TC-001994 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Reversal → Time-out Reversal → declined | T10 | 0 -> 8 -> Z | 1: 0 (ASSERT) ; 2: 0 (FLAG) ; 3: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC28 | TC-001995 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Reversal of requested amount → declined | T10 | 0 -> 8 | 1: F (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS8_TC29 | TC-001996 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Cashback on MasterCard credit → declined | T10 | 0 | 1: 1 (FLAG) | pending_sme |
| BR1_TS8_TC30 | TC-002005 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase declined by the issuer | T10 | 0 | 1: 1 (ASSERT) | pending_sme |
| BR1_TS8_TC31 | TC-002006 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | pending_sme |
| BR1_TS8_TC32 | TC-002007 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS9 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS9_TC1 | TC-002008 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase approved | T1 | 6 | 1: ? (FLAG) | ready |
| BR1_TS9_TC2 | TC-002009 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase → Mail/Phone Reversal | T5 | 6 -> C | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS9_TC3 | TC-002010 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase, no response → Time-out Reversal | T6 | 6 -> Z | 1: no response expected ; 2: 0 (FLAG) | ready |
| BR1_TS9_TC4 | TC-002011 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase with AVS mismatch → approved | T9 | 6 | 1: 0 (ASSERT) | needs_client_input |
| BR1_TS9_TC5 | TC-002012 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Mail/Phone Purchase with AVS mismatch → Mail/Phone Reversal | T9 | 6 -> C | 1: 0 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS9_TC6 | TC-002013 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Manual / Keyed Partial requested on Mail/Phone Purchase → not partially approved | T10 | 6 | 1: 1 (FLAG) | requires_review |

### BR1_TS10 BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS10_TC1 | TC-002014 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase approved | T1 | 6 | 1: ? (FLAG) | pending_sme |
| BR1_TS10_TC2 | TC-002015 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase → Mail/Phone Reversal | T5 | 6 -> C | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC3 | TC-002016 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase, no response → Time-out Reversal | T6 | 6 -> Z | 1: no response expected ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC4 | TC-002017 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase with AVS mismatch → approved | T9 | 6 | 1: 0 (ASSERT) | pending_sme |
| BR1_TS10_TC5 | TC-002018 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Mail/Phone Purchase with AVS mismatch → Mail/Phone Reversal | T9 | 6 -> C | 1: 0 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS10_TC6 | TC-002019 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Purchase – Credential on File Partial requested on Mail/Phone Purchase → not partially approved | T10 | 6 | 1: 1 (FLAG) | pending_sme |

### BR1_TS11 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS11_TC1 | TC-002020 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC2 | TC-002021 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip EMV online PIN → approved | T3 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS11_TC3 | TC-002022 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip EMV offline PIN → approved | T3 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS11_TC4 | TC-002023 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS11_TC5 | TC-002024 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS11_TC6 | TC-002025 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS11_TC7 | TC-002026 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS11_TC8 | TC-002027 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS11_TC9 | TC-002028 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS11_TC10 | TC-002029 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS11_TC11 | TC-002030 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS11_TC12 | TC-002031 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC13 | TC-002032 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC14 | TC-002033 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS11_TC15 | TC-002034 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contact Chip Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS12 BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS12_TC1 | TC-002035 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC2 | TC-002036 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Contactless chip → contact chip (86) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS12_TC3 | TC-002037 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip EMV online PIN → approved | T3 | 3 | 1: 2 (ASSERT) | needs_client_input |
| BR1_TS12_TC4 | TC-002038 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS12_TC5 | TC-002039 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS12_TC6 | TC-002040 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS12_TC7 | TC-002041 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS12_TC8 | TC-002042 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS12_TC9 | TC-002043 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS12_TC10 | TC-002044 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS12_TC11 | TC-002045 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS12_TC12 | TC-002046 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC13 | TC-002047 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC14 | TC-002048 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS12_TC15 | TC-002049 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS13 BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS13_TC1 | TC-002050 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC2 | TC-002051 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Track 2 unreadable → Track 1 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS13_TC3 | TC-002052 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS13_TC4 | TC-002053 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS13_TC5 | TC-002054 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS13_TC6 | TC-002055 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS13_TC7 | TC-002056 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS13_TC8 | TC-002057 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS13_TC9 | TC-002058 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS13_TC10 | TC-002059 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS13_TC11 | TC-002060 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC12 | TC-002061 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC13 | TC-002062 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS13_TC14 | TC-002063 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS13_TC15 | TC-002064 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS14 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS14_TC1 | TC-002065 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS14_TC2 | TC-002066 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS14_TC3 | TC-002067 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS14_TC4 | TC-002068 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS14_TC5 | TC-002069 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS14_TC6 | TC-002070 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS14_TC7 | TC-002071 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS14_TC8 | TC-002072 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS14_TC9 | TC-002073 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS14_TC10 | TC-002074 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC11 | TC-002075 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC12 | TC-002076 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS14_TC13 | TC-002077 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS14_TC14 | TC-002078 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Magnetic Stripe Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS15 BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS15_TC1 | TC-002079 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC2 | TC-002080 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed 3 failed swipes → keyed entry → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS15_TC3 | TC-002081 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS15_TC4 | TC-002082 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS15_TC5 | TC-002083 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS15_TC6 | TC-002084 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS15_TC7 | TC-002085 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS15_TC8 | TC-002086 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS15_TC9 | TC-002087 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS15_TC10 | TC-002088 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS15_TC11 | TC-002089 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC12 | TC-002090 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC13 | TC-002091 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS15_TC14 | TC-002092 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Expired card → rejected at device (no host message) | T11 | - | n/a (no host message) | ready |
| BR1_TS15_TC15 | TC-002093 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS16 BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS16_TC1 | TC-002094 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC2 | TC-002095 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → keyed fallback (79) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC3 | TC-002107 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80) → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC4 | TC-002108 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Chip unreadable → swiped fallback (80), Track 2 unreadable → Track 1 sent → approved | T2 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS16_TC5 | TC-002096 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | needs_client_input |
| BR1_TS16_TC6 | TC-002097 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | needs_client_input |
| BR1_TS16_TC7 | TC-002098 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS16_TC8 | TC-002099 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS16_TC9 | TC-002100 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS16_TC10 | TC-002101 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS16_TC11 | TC-002102 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS16_TC12 | TC-002103 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS16_TC13 | TC-002104 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC14 | TC-002105 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC15 | TC-002106 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS16_TC16 | TC-002109 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Chip Fallback Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS17 BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS17_TC1 | TC-002110 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | ready |
| BR1_TS17_TC2 | TC-002111 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | ready |
| BR1_TS17_TC3 | TC-002112 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | requires_review |
| BR1_TS17_TC4 | TC-002113 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS17_TC5 | TC-002114 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | ready |
| BR1_TS17_TC6 | TC-002115 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | requires_review |
| BR1_TS17_TC7 | TC-002116 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | requires_review |
| BR1_TS17_TC8 | TC-002117 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC9 | TC-002118 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC10 | TC-002119 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | requires_review |
| BR1_TS17_TC11 | TC-002120 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS18 BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS18_TC1 | TC-002121 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization Only approved | T1 | 3 | 1: 2 (ASSERT) | pending_sme |
| BR1_TS18_TC2 | TC-002122 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Partial authorization → Completion for approved amount | T4 | 3 -> 0 | 1: F (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC3 | TC-002123 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Partial authorization → Cancellation | T4 | 3 -> S | 1: F (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC4 | TC-002124 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC5 | TC-002125 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Pre-authorization → Completion for a different final amount | T5 | 3 -> 0 | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC6 | TC-002126 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Cancellation | T5 | 3 -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS18_TC7 | TC-002127 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Purchase Reversal | T5 | 3 -> 0 -> 8 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: 0 (FLAG) | pending_sme |
| BR1_TS18_TC8 | TC-002128 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Completion timed out → Time-out Reversal → Completion resubmitted | T6 | 3 -> 0 -> Z -> 0 | 1: 2 (ASSERT) ; 2: no response expected ; 3: 0 (FLAG) ; 4: 0 (ASSERT) | pending_sme |
| BR1_TS18_TC9 | TC-002129 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization → Completion → Settlement | T8 | 3 -> 0 -> 990 | 1: 2 (ASSERT) ; 2: 0 (ASSERT) ; 3: ? (FLAG) | pending_sme |
| BR1_TS18_TC10 | TC-002130 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization released with Time-out Reversal → declined | T10 | 3 -> Z | 1: no response expected ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC11 | TC-002131 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Authorization reversed with Purchase Reversal (8) → declined | T10 | 3 -> 8 | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC12 | TC-002132 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Cancellation not matching the authorization → declined | T10 | 3 -> S | 1: 2 (ASSERT) ; 2: 1 (FLAG) | pending_sme |
| BR1_TS18_TC13 | TC-002133 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS19 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS19_TC1 | TC-002134 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization Only approved | T1 | B | 1: 2 (ASSERT) | ready |
| BR1_TS19_TC2 | TC-002135 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Cancellation | T5 | B -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | ready |
| BR1_TS19_TC3 | TC-002136 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Manual / Keyed Mail/Phone Authorization → Completion | T5 | - | n/a (no host message) | blocked_spec_gap |

### BR1_TS20 BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS20_TC1 | TC-002137 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization Only approved | T1 | B | 1: 2 (ASSERT) | pending_sme |
| BR1_TS20_TC2 | TC-002138 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Cancellation | T5 | B -> S | 1: 2 (ASSERT) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS20_TC3 | TC-002139 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Mail/Phone Authorization Only – Credential on File Mail/Phone Authorization → Completion | T5 | - | n/a (no host message) | blocked_spec_gap |

### BR1_TS21 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS21_TC1 | TC-002140 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS21_TC2 | TC-002141 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS21_TC3 | TC-002142 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contact Chip Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS22 BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS22_TC1 | TC-002143 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS22_TC2 | TC-002144 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | requires_review |
| BR1_TS22_TC3 | TC-002145 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – EMV Contactless Chip Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS23 BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS23_TC1 | TC-002146 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS23_TC2 | TC-002147 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS23_TC3 | TC-002148 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS24 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS24_TC1 | TC-002149 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS24_TC2 | TC-002150 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS24_TC3 | TC-002151 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Magnetic Stripe Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS25 BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS25_TC1 | TC-002152 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS25_TC2 | TC-002153 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS25_TC3 | TC-002154 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Manual / Keyed Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS26 BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS26_TC1 | TC-002155 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | ready |
| BR1_TS26_TC2 | TC-002156 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | ready |
| BR1_TS26_TC3 | TC-002157 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Chip Fallback Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |

### BR1_TS27 BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS27_TC1 | TC-002158 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS27_TC2 | TC-002159 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS27_TC3 | TC-002160 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Credential on File Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS28 BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS28_TC1 | TC-002161 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return processed | T1 | 7 | 1: ? (FLAG) | pending_sme |
| BR1_TS28_TC2 | TC-002162 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return | T5 | 7 -> U | 1: ? (FLAG) ; 2: 0 (FLAG) | pending_sme |
| BR1_TS28_TC3 | TC-002163 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | pending_sme |

### BR1_TS29 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS29_TC1 | TC-002164 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contact Chip Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |

### BR1_TS30 BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS30_TC1 | TC-002165 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – EMV Contactless Chip Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |

### BR1_TS31 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe (spec_implied)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS31_TC1 | TC-002166 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Magnetic Stripe Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |

### BR1_TS32 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS32_TC1 | TC-002167 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Magnetic Stripe Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |

### BR1_TS33 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS33_TC1 | TC-002168 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Manual / Keyed Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |

### BR1_TS34 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS34_TC1 | TC-002169 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Chip Fallback Balance Inquiry answered | T1 | E | 1: ? (FLAG) | ready |

### BR1_TS35 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS35_TC1 | TC-002170 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Credential on File Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |

### BR1_TS36 BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device (unconfirmed)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BR1_TS36_TC1 | TC-002171 | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Balance Inquiry – Contactless Mobile Commerce Device Balance Inquiry answered | T1 | E | 1: ? (FLAG) | pending_sme |

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
