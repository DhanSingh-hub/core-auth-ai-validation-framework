# Business Requirements - buypass-atl105-interlink-debit

Selection: **platform=BUYPASS, spec=ATL105, network=INTERLINK, program=DEBIT**. A Business Requirement (BR) is one Platform x Spec x Network x Card Program inside the selection; every spec-evaluated original transaction category x entry mode under it is a Scenario. Follow-on categories are flow legs.

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **True**
- Validation: **passed**
- Built: 2026-10-31T10:18:08.269081+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1 | BUYPASS ATL105 Interlink Debit | ATL105 2026-4 | 28 | 8 | 0 | 20 | 0 | 0 | 104 | 439 |

## BR1. BUYPASS ATL105 Interlink Debit

`BR-EAD513C5` - status **built**

Programs: Debit 28. Transaction categories: 4. Entry modes: 7. Scenarios with a pre-authorized variant: 7.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 306 | 0 |
| BR1_TS2 | BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 283 | 0 |
| BR1_TS3 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 127 | 0 |
| BR1_TS4 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 83 | 0 |
| BR1_TS5 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 79, 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 115 | 0 |
| BR1_TS6 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 10 | 011, 012, 013 (program_domain) | Financial Transaction Request | 113 | 0 |
| BR1_TS7 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 82 | 0 |
| BR1_TS8 | BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 316 | 0 |
| BR1_TS9 | BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 293 | 0 |
| BR1_TS10 | BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe | unconfirmed | yes | single-leg | 3, 5 | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 139 | 0 |
| BR1_TS11 | BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | 3, 5 | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 95 | 0 |
| BR1_TS12 | BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback | unconfirmed | yes | single-leg | 3, 5 | 79, 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 127 | 0 |
| BR1_TS13 | BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File | unconfirmed | yes | single-leg | 3, 5 | 10 | 011, 012, 013 (program_domain) | Financial Transaction Request | 125 | 0 |
| BR1_TS14 | BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 94 | 0 |
| BR1_TS15 | BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip | valid |  | single-leg | 7 | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 306 | 0 |
| BR1_TS16 | BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip | valid |  | single-leg | 7 | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 283 | 0 |
| BR1_TS17 | BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe | unconfirmed | yes | single-leg | 7 | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 124 | 0 |
| BR1_TS18 | BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | 7 | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 79 | 0 |
| BR1_TS19 | BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback | unconfirmed | yes | single-leg | 7 | 79, 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 111 | 0 |
| BR1_TS20 | BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File | unconfirmed | yes | single-leg | 7 | 10 | 011, 012, 013 (program_domain) | Financial Transaction Request | 109 | 0 |
| BR1_TS21 | BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 78 | 0 |
| BR1_TS22 | BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip | valid |  | single-leg | E | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 287 | 0 |
| BR1_TS23 | BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip | valid |  | single-leg | E | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 264 | 0 |
| BR1_TS24 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe | unconfirmed | yes | single-leg | E | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 109 | 0 |
| BR1_TS25 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | E | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 65 | 0 |
| BR1_TS26 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback | unconfirmed | yes | single-leg | E | 79, 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 97 | 0 |
| BR1_TS27 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Credential on File | unconfirmed | yes | single-leg | E | 10 | 011, 012, 013 (program_domain) | Financial Transaction Request | 95 | 0 |
| BR1_TS28 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | E | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 64 | 0 |

### Multi-leg flows (follow-on scenarios and pre-authorized variants)

Each leg sends its own category's Transaction Type code for the merchant's terminal (attended POS / customer-activated). Flows the spec does not back are listed separately as BLOCKED.

**BR1_TS1 BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS2 BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS3 BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS4 BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS5 BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS6 BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 10, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS7 BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

### Follow-on combinations (flow legs, not scenarios)

Reversal, time-out reversal, cancellation, void of a return and mail/phone reversal are steps of the original transaction's flow test cases (follow_on_leg_combinations.csv).

| Leg | Combination | Status | Usable flows | Blocked |
|---|---|---|---|---|
| BR1_LEG1 | Void of a Merchandise Return – EMV Contact Chip | unconfirmed | 1 | 0 |
| BR1_LEG2 | Void of a Merchandise Return – EMV Contactless Chip | unconfirmed | 1 | 0 |
| BR1_LEG3 | Void of a Merchandise Return – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG4 | Void of a Merchandise Return – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG5 | Void of a Merchandise Return – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG6 | Void of a Merchandise Return – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG7 | Void of a Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG8 | Purchase Reversal – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG9 | Purchase Reversal – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG10 | Purchase Reversal – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG11 | Purchase Reversal – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG12 | Purchase Reversal – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG13 | Purchase Reversal – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG14 | Purchase Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG15 | Time-out Reversal – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG16 | Time-out Reversal – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG17 | Time-out Reversal – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG18 | Time-out Reversal – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG19 | Time-out Reversal – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG20 | Time-out Reversal – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG21 | Time-out Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG22 | Cancellation – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG23 | Cancellation – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG24 | Cancellation – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG25 | Cancellation – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG26 | Cancellation – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG27 | Cancellation – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG28 | Cancellation – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |

### Scenarios requiring SME confirmation

The spec neither states nor rules out these pairs; the scenario is kept and flagged.

| ID | Scenario | Open pair(s) |
|---|---|---|
| BR1_TS3 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe | program-entry_mode, network-entry_mode |
| BR1_TS4 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS5 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS6 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File | txn_category-entry_mode |
| BR1_TS7 | BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS10 | BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe | program-entry_mode, network-entry_mode |
| BR1_TS11 | BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS12 | BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS13 | BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File | txn_category-entry_mode |
| BR1_TS14 | BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS17 | BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS18 | BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS19 | BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS20 | BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File | txn_category-entry_mode |
| BR1_TS21 | BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS24 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS25 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS26 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS27 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Credential on File | txn_category-entry_mode |
| BR1_TS28 | BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |

### Excluded combinations (not scenarios)

| Combination | Blocking pair | Evidence |
|---|---|---|
| ATL105 Interlink Debit Manual / Keyed Purchase/Capture | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit EMV Contact Chip Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Contactless Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Manual / Keyed Mail/Phone Purchase | Debit x Manual / Keyed; Debit x Mail/Phone Purchase | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Chip Fallback | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Credential on File Mail/Phone Purchase | Debit x Mail/Phone Purchase | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Contactless Mobile Commerce Device | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Manual / Keyed Authorization Only | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit EMV Contact Chip Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Contactless Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Manual / Keyed Mail/Phone Authorization Only | Debit x Manual / Keyed; Debit x Mail/Phone Authorization Only | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Chip Fallback | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Credential on File Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Contactless Mobile Commerce Device | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Manual / Keyed Merchandise Return | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit Manual / Keyed Void of a Merchandise Return | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit Manual / Keyed Purchase Reversal | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit EMV Contact Chip Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Contactless Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Manual / Keyed Mail/Phone Reversal | Debit x Manual / Keyed; Debit x Mail/Phone Reversal | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Chip Fallback | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Credential on File Mail/Phone Reversal | Debit x Mail/Phone Reversal | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Contactless Mobile Commerce Device | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Interlink Debit Manual / Keyed Time-out Reversal | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit Manual / Keyed Cancellation | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit Manual / Keyed Balance Inquiry | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Interlink Debit EMV Contact Chip Account Verification | Debit x Account Verification; Account Verification x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Account Verification | Debit x Account Verification; Account Verification x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Account Verification | Debit x Manual / Keyed; Debit x Account Verification | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Token Registration | Debit x Token Registration; Token Registration x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Token Registration | Debit x Token Registration; Token Registration x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Token Registration | Debit x Manual / Keyed; Debit x Token Registration | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Activate | Debit x Activate; Activate x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Activate | Debit x Activate; Activate x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Activate | Debit x Manual / Keyed; Debit x Activate | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Deactivate | Debit x Deactivate; Deactivate x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Deactivate | Debit x Deactivate; Deactivate x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Deactivate | Debit x Manual / Keyed; Debit x Deactivate | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Balance Merge | Debit x Balance Merge; Balance Merge x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Balance Merge | Debit x Balance Merge; Balance Merge x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Balance Merge | Debit x Manual / Keyed; Debit x Balance Merge | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Replace Card | Debit x Replace Card; Replace Card x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Replace Card | Debit x Replace Card; Replace Card x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Replace Card | Debit x Manual / Keyed; Debit x Replace Card | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Recharge Card | Debit x Recharge Card; Recharge Card x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Recharge Card | Debit x Recharge Card; Recharge Card x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Recharge Card | Debit x Manual / Keyed; Debit x Recharge Card | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip RBC Loyalty Lookup | Debit x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip RBC Loyalty Lookup | Debit x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed RBC Loyalty Lookup | Debit x Manual / Keyed; Debit x RBC Loyalty Lookup | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit EMV Contact Chip Loyalty Advice Function | Debit x Loyalty Advice Function; Loyalty Advice Function x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit EMV Contactless Chip Loyalty Advice Function | Debit x Loyalty Advice Function; Loyalty Advice Function x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Interlink Debit Magnetic Stripe Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Magnetic Stripe Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Manual / Keyed Loyalty Advice Function | Debit x Manual / Keyed; Debit x Loyalty Advice Function | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Chip Fallback Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Credential on File Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Interlink Debit Contactless Mobile Commerce Device Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |

### Spec citations behind these scenarios

-  p.512 (Appendix C): "48 Interlink - Authorizer Type: Debit"
- SRC-ATL105-PDF-001 p.81 (anchored:A-SALE-PREAUTH-TRACK): "If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture"
- SRC-ATL105-PDF-001 p.161 (10.14.2.3 Supported Cards): "The following debit cards are supported for EMV processing: ... Interlink"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Purchase"
- SRC-ATL105-PDF-001 p.638 (anchored:APPJ-FN2-COF-DEBIT-FOLLOWON): "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"

