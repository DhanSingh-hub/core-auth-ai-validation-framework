# Business Requirements - mastercard-fleet

Selection: **platform=BUYPASS, spec=ATL105, network=MASTERCARD, program=FLEET**. A Business Requirement (BR) is one Platform x Spec x Network x Card Program inside the selection; every spec-evaluated original transaction category x entry mode under it is a Scenario. Follow-on categories are flow legs.

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **False**
- Validation: **passed**
- Built: 2026-11-05T14:25:27.140369+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1 | BUYPASS ATL105 MasterCard Fleet | ATL105 2026-4 | 24 | 5 | 6 | 12 | 0 | 0 | 120 | 793 |

## BR1. BUYPASS ATL105 MasterCard Fleet

`BR-A47DDF5D` - status **built**

Programs: Fleet 24. Transaction categories: 3. Entry modes: 8. Scenarios with a pre-authorized variant: 8.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip | valid_conditional |  | single-leg +pre-auth | 0, 4 | 05, 95 | 080 (network_specific) | EMV Financial Transaction Request | 587 | 0 |
| BR1_TS2 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip | valid_conditional |  | single-leg +pre-auth | 0, 4 | 07, 86 | 080 (network_specific) | EMV Financial Transaction Request | 558 | 0 |
| BR1_TS3 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 080 (network_specific) | Financial Transaction Request | 411 | 0 |
| BR1_TS4 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 91 | 080 (network_specific) | Financial Transaction Request | 328 | 0 |
| BR1_TS5 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 080 (network_specific) | Financial Transaction Request | 365 | 0 |
| BR1_TS6 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 79, 80 | 080 (network_specific) | Financial Transaction Request | 361 | 0 |
| BR1_TS7 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 10 | 080 (network_specific) | Financial Transaction Request | 361 | 0 |
| BR1_TS8 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 080 (network_specific) | Financial Transaction Request | 327 | 0 |
| BR1_TS9 | BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip | valid_conditional |  | single-leg | 3, 5 | 05, 95 | 080 (network_specific) | EMV Financial Transaction Request | 601 | 0 |
| BR1_TS10 | BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip | valid_conditional |  | single-leg | 3, 5 | 07, 86 | 080 (network_specific) | EMV Financial Transaction Request | 572 | 0 |
| BR1_TS11 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 080 (network_specific) | Financial Transaction Request | 431 | 0 |
| BR1_TS12 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | 3, 5 | 91 | 080 (network_specific) | Financial Transaction Request | 348 | 0 |
| BR1_TS13 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed | valid |  | single-leg | 3, 5 | 01 | 080 (network_specific) | Financial Transaction Request | 385 | 0 |
| BR1_TS14 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback | unconfirmed | yes | single-leg | 3, 5 | 79, 80 | 080 (network_specific) | Financial Transaction Request | 381 | 0 |
| BR1_TS15 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File | unconfirmed | yes | single-leg | 3, 5 | 10 | 080 (network_specific) | Financial Transaction Request | 381 | 0 |
| BR1_TS16 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 080 (network_specific) | Financial Transaction Request | 347 | 0 |
| BR1_TS17 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip | valid_conditional |  | single-leg | 7 | 05, 95 | 080 (network_specific) | EMV Financial Transaction Request | 581 | 0 |
| BR1_TS18 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip | valid_conditional |  | single-leg | 7 | 07, 86 | 080 (network_specific) | EMV Financial Transaction Request | 552 | 0 |
| BR1_TS19 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe | spec_implied |  | single-leg | 7 | 02, 90 | 080 (network_specific) | Financial Transaction Request | 406 | 0 |
| BR1_TS20 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | 7 | 91 | 080 (network_specific) | Financial Transaction Request | 323 | 0 |
| BR1_TS21 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed | valid |  | single-leg | 7 | 01 | 080 (network_specific) | Financial Transaction Request | 360 | 0 |
| BR1_TS22 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback | unconfirmed | yes | single-leg | 7 | 79, 80 | 080 (network_specific) | Financial Transaction Request | 356 | 0 |
| BR1_TS23 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File | unconfirmed | yes | single-leg | 7 | 10 | 080 (network_specific) | Financial Transaction Request | 356 | 0 |
| BR1_TS24 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 080 (network_specific) | Financial Transaction Request | 322 | 0 |

### Multi-leg flows (follow-on scenarios and pre-authorized variants)

Each leg sends its own category's Transaction Type code for the merchant's terminal (attended POS / customer-activated). Flows the spec does not back are listed separately as BLOCKED.

**BR1_TS1 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip** (valid_conditional)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid_conditional
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS2 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip** (valid_conditional)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid_conditional
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS3 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS4 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS5 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS6 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS7 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 10, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS8 BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

### Follow-on combinations (flow legs, not scenarios)

Reversal, time-out reversal, cancellation, void of a return and mail/phone reversal are steps of the original transaction's flow test cases (follow_on_leg_combinations.csv).

| Leg | Combination | Status | Usable flows | Blocked |
|---|---|---|---|---|
| BR1_LEG1 | Purchase Reversal – EMV Contact Chip | valid_conditional | 1 | 0 |
| BR1_LEG2 | Purchase Reversal – EMV Contactless Chip | valid_conditional | 1 | 0 |
| BR1_LEG3 | Purchase Reversal – Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG4 | Purchase Reversal – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG5 | Purchase Reversal – Manual / Keyed | valid | 1 | 0 |
| BR1_LEG6 | Purchase Reversal – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG7 | Purchase Reversal – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG8 | Purchase Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG9 | Time-out Reversal – EMV Contact Chip | valid_conditional | 1 | 0 |
| BR1_LEG10 | Time-out Reversal – EMV Contactless Chip | valid_conditional | 1 | 0 |
| BR1_LEG11 | Time-out Reversal – Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG12 | Time-out Reversal – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG13 | Time-out Reversal – Manual / Keyed | valid | 1 | 0 |
| BR1_LEG14 | Time-out Reversal – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG15 | Time-out Reversal – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG16 | Time-out Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |

### Scenarios requiring SME confirmation

The spec neither states nor rules out these pairs; the scenario is kept and flagged.

| ID | Scenario | Open pair(s) |
|---|---|---|
| BR1_TS4 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS6 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS7 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS8 | BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS12 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS14 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS15 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS16 | BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS20 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS22 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS23 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS24 | BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |

### Excluded combinations (not scenarios)

| Combination | Blocking pair | Evidence |
|---|---|---|
| ATL105 MasterCard Fleet EMV Contact Chip Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase; Mail/Phone Purchase x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase; Mail/Phone Purchase x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase; Mail/Phone Purchase x Magnetic Stripe | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase; Mail/Phone Purchase x Contactless Magnetic Stripe | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Manual / Keyed Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase; Mail/Phone Purchase x Chip Fallback | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Credential on File Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Mail/Phone Purchase | MasterCard (Fleet) x Mail/Phone Purchase; Mail/Phone Purchase x Contactless Mobile Commerce Device | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet EMV Contact Chip Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only; Mail/Phone Authorization Only x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only; Mail/Phone Authorization Only x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Magnetic Stripe | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Contactless Magnetic Stripe | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Manual / Keyed Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Chip Fallback | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Credential on File Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Mail/Phone Authorization Only | MasterCard (Fleet) x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Contactless Mobile Commerce Device | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet EMV Contact Chip Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Magnetic Stripe Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal; Mail/Phone Reversal x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal; Mail/Phone Reversal x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal; Mail/Phone Reversal x Magnetic Stripe | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal; Mail/Phone Reversal x Contactless Magnetic Stripe | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Manual / Keyed Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal; Mail/Phone Reversal x Chip Fallback | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet Credential on File Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Mail/Phone Reversal | MasterCard (Fleet) x Mail/Phone Reversal; Mail/Phone Reversal x Contactless Mobile Commerce Device | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Fleet EMV Contact Chip Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Magnetic Stripe Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Cancellation | MasterCard (Fleet) x Cancellation | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Magnetic Stripe Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Balance Inquiry | MasterCard (Fleet) x Balance Inquiry | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Account Verification | MasterCard (Fleet) x Account Verification; Account Verification x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Account Verification | MasterCard (Fleet) x Account Verification; Account Verification x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Account Verification | MasterCard (Fleet) x Account Verification | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Account Verification | MasterCard (Fleet) x Account Verification | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Account Verification | MasterCard (Fleet) x Account Verification | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Account Verification | MasterCard (Fleet) x Account Verification | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Account Verification | MasterCard (Fleet) x Account Verification | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Account Verification | MasterCard (Fleet) x Account Verification | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Token Registration | MasterCard (Fleet) x Token Registration; Token Registration x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Token Registration | MasterCard (Fleet) x Token Registration; Token Registration x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Token Registration | MasterCard (Fleet) x Token Registration | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Token Registration | MasterCard (Fleet) x Token Registration | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Token Registration | MasterCard (Fleet) x Token Registration | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Token Registration | MasterCard (Fleet) x Token Registration | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Token Registration | MasterCard (Fleet) x Token Registration | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Token Registration | MasterCard (Fleet) x Token Registration | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Activate | MasterCard (Fleet) x Activate; Activate x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Activate | MasterCard (Fleet) x Activate; Activate x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Activate | MasterCard (Fleet) x Activate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Activate | MasterCard (Fleet) x Activate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Activate | MasterCard (Fleet) x Activate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Activate | MasterCard (Fleet) x Activate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Activate | MasterCard (Fleet) x Activate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Activate | MasterCard (Fleet) x Activate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Deactivate | MasterCard (Fleet) x Deactivate; Deactivate x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Deactivate | MasterCard (Fleet) x Deactivate; Deactivate x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Deactivate | MasterCard (Fleet) x Deactivate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Deactivate | MasterCard (Fleet) x Deactivate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Deactivate | MasterCard (Fleet) x Deactivate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Deactivate | MasterCard (Fleet) x Deactivate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Deactivate | MasterCard (Fleet) x Deactivate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Deactivate | MasterCard (Fleet) x Deactivate | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Balance Merge | MasterCard (Fleet) x Balance Merge; Balance Merge x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Balance Merge | MasterCard (Fleet) x Balance Merge; Balance Merge x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Balance Merge | MasterCard (Fleet) x Balance Merge | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Balance Merge | MasterCard (Fleet) x Balance Merge | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Balance Merge | MasterCard (Fleet) x Balance Merge | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Balance Merge | MasterCard (Fleet) x Balance Merge | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Balance Merge | MasterCard (Fleet) x Balance Merge | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Balance Merge | MasterCard (Fleet) x Balance Merge | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Replace Card | MasterCard (Fleet) x Replace Card; Replace Card x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Replace Card | MasterCard (Fleet) x Replace Card; Replace Card x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Replace Card | MasterCard (Fleet) x Replace Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Replace Card | MasterCard (Fleet) x Replace Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Replace Card | MasterCard (Fleet) x Replace Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Replace Card | MasterCard (Fleet) x Replace Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Replace Card | MasterCard (Fleet) x Replace Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Replace Card | MasterCard (Fleet) x Replace Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Recharge Card | MasterCard (Fleet) x Recharge Card; Recharge Card x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Recharge Card | MasterCard (Fleet) x Recharge Card; Recharge Card x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Recharge Card | MasterCard (Fleet) x Recharge Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Recharge Card | MasterCard (Fleet) x Recharge Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Recharge Card | MasterCard (Fleet) x Recharge Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Recharge Card | MasterCard (Fleet) x Recharge Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Recharge Card | MasterCard (Fleet) x Recharge Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Recharge Card | MasterCard (Fleet) x Recharge Card | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device RBC Loyalty Lookup | MasterCard (Fleet) x RBC Loyalty Lookup | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet EMV Contact Chip Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function; Loyalty Advice Function x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function; Loyalty Advice Function x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet Magnetic Stripe Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Magnetic Stripe Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Manual / Keyed Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Chip Fallback Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Credential on File Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |
| ATL105 MasterCard Fleet Contactless Mobile Commerce Device Loyalty Advice Function | MasterCard (Fleet) x Loyalty Advice Function | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:" |

### Spec citations behind these scenarios

-  p.522 (Appendix E): "080 MasterCard fleet - Card Type Description: FLEET"
- SRC-ATL105-PDF-001 p.81 (anchored:A-SALE-PREAUTH-TRACK): "If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Purchase"
- SRC-ATL105-PDF-001 p.289 (scoped:SR-MCFLEET-EMV): "Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Authorization Only Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Purchase/Capture Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Return/Refund Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.52 (anchored:A-PIPS-MCFLEET-MANUAL): "Manual entry is allowed on MasterCard fleet card transactions"
- SRC-BUYPASS-PIPS-PDF-001 p.53 (anchored:A-PIPS-MCFLEET-TRACK): "The default track that is sent to BUYPASS on an original transaction should be Track 2"

