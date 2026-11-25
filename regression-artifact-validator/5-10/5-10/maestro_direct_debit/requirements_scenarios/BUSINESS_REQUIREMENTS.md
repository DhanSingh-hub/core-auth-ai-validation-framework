# Business Requirements - maestro-direct-debit

Selection: **platform=BUYPASS, spec=ATL105, network=MAESTRO_DIRECT, program=DEBIT**. A Business Requirement (BR) is one Platform x Spec x Network x Card Program inside the selection; every spec-evaluated original transaction category x entry mode under it is a Scenario. Follow-on categories are flow legs.

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **False**
- Validation: **passed**
- Built: 2026-11-05T14:38:12.366674+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1 | BUYPASS ATL105 Maestro Direct Debit | ATL105 2026-4 | 24 | 8 | 0 | 16 | 0 | 0 | 112 | 404 |

## BR1. BUYPASS ATL105 Maestro Direct Debit

`BR-8B4D24D4` - status **built**

Programs: Debit 24. Transaction categories: 4. Entry modes: 6. Scenarios with a pre-authorized variant: 6.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 301 | 0 |
| BR1_TS2 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 278 | 0 |
| BR1_TS3 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Magnetic Stripe | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 121 | 0 |
| BR1_TS4 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Magnetic Stripe | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 77 | 0 |
| BR1_TS5 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Chip Fallback | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 109 | 0 |
| BR1_TS6 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 76 | 0 |
| BR1_TS7 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 313 | 0 |
| BR1_TS8 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 290 | 0 |
| BR1_TS9 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Magnetic Stripe | unconfirmed | yes | single-leg | 3, 5 | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 133 | 0 |
| BR1_TS10 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | 3, 5 | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 89 | 0 |
| BR1_TS11 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Chip Fallback | unconfirmed | yes | single-leg | 3, 5 | 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 121 | 0 |
| BR1_TS12 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 88 | 0 |
| BR1_TS13 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contact Chip | valid |  | single-leg | 7 | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 303 | 0 |
| BR1_TS14 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – EMV Contactless Chip | valid |  | single-leg | 7 | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 280 | 0 |
| BR1_TS15 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Magnetic Stripe | unconfirmed | yes | single-leg | 7 | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 118 | 0 |
| BR1_TS16 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | 7 | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 73 | 0 |
| BR1_TS17 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Chip Fallback | unconfirmed | yes | single-leg | 7 | 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 105 | 0 |
| BR1_TS18 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 72 | 0 |
| BR1_TS19 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contact Chip | valid |  | single-leg | E | 05, 95 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 284 | 0 |
| BR1_TS20 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – EMV Contactless Chip | valid |  | single-leg | E | 07, 86 | 011, 012, 013 (program_domain) | EMV Financial Transaction Request | 261 | 0 |
| BR1_TS21 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Magnetic Stripe | unconfirmed | yes | single-leg | E | 02, 90 | 011, 012, 013 (program_domain) | Financial Transaction Request | 103 | 0 |
| BR1_TS22 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Magnetic Stripe | unconfirmed | yes | single-leg | E | 91 | 011, 012, 013 (program_domain) | Financial Transaction Request | 59 | 0 |
| BR1_TS23 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Chip Fallback | unconfirmed | yes | single-leg | E | 80 | 011, 012, 013 (program_domain) | Financial Transaction Request | 91 | 0 |
| BR1_TS24 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | E | 82 | 011, 012, 013 (program_domain) | Financial Transaction Request | 58 | 0 |

### Multi-leg flows (follow-on scenarios and pre-authorized variants)

Each leg sends its own category's Transaction Type code for the merchant's terminal (attended POS / customer-activated). Flows the spec does not back are listed separately as BLOCKED.

**BR1_TS1 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS2 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS3 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Magnetic Stripe** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS4 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Magnetic Stripe** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS5 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Chip Fallback** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS6 BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

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
| BR1_LEG6 | Void of a Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG7 | Purchase Reversal – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG8 | Purchase Reversal – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG9 | Purchase Reversal – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG10 | Purchase Reversal – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG11 | Purchase Reversal – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG12 | Purchase Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG13 | Time-out Reversal – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG14 | Time-out Reversal – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG15 | Time-out Reversal – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG16 | Time-out Reversal – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG17 | Time-out Reversal – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG18 | Time-out Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG19 | Cancellation – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG20 | Cancellation – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG21 | Cancellation – Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG22 | Cancellation – Contactless Magnetic Stripe | unconfirmed | 1 | 0 |
| BR1_LEG23 | Cancellation – Chip Fallback | unconfirmed | 1 | 0 |
| BR1_LEG24 | Cancellation – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |

### Scenarios requiring SME confirmation

The spec neither states nor rules out these pairs; the scenario is kept and flagged.

| ID | Scenario | Open pair(s) |
|---|---|---|
| BR1_TS3 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Magnetic Stripe | program-entry_mode, network-entry_mode |
| BR1_TS4 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS5 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS6 | BUYPASS ATL105 Maestro Direct Debit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS9 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Magnetic Stripe | program-entry_mode, network-entry_mode |
| BR1_TS10 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS11 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS12 | BUYPASS ATL105 Maestro Direct Debit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS15 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS16 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS17 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS18 | BUYPASS ATL105 Maestro Direct Debit Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS21 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS22 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Magnetic Stripe | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS23 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Chip Fallback | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS24 | BUYPASS ATL105 Maestro Direct Debit Balance Inquiry – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |

### Excluded combinations (not scenarios)

| Combination | Blocking pair | Evidence |
|---|---|---|
| ATL105 Maestro Direct Debit Manual / Keyed Purchase/Capture | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Purchase/Capture | Debit x Credential on File | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction" |
| ATL105 Maestro Direct Debit EMV Contact Chip Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Contactless Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Manual / Keyed Mail/Phone Purchase | Debit x Manual / Keyed; Debit x Mail/Phone Purchase | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Chip Fallback | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Credential on File Mail/Phone Purchase | Debit x Credential on File; Debit x Mail/Phone Purchase | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Mail/Phone Purchase | Debit x Mail/Phone Purchase; Mail/Phone Purchase x Contactless Mobile Commerce Device | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Manual / Keyed Authorization Only | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Authorization Only | Debit x Credential on File | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction" |
| ATL105 Maestro Direct Debit EMV Contact Chip Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Contactless Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Manual / Keyed Mail/Phone Authorization Only | Debit x Manual / Keyed; Debit x Mail/Phone Authorization Only | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Chip Fallback | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Credential on File Mail/Phone Authorization Only | Debit x Credential on File; Debit x Mail/Phone Authorization Only | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Mail/Phone Authorization Only | Debit x Mail/Phone Authorization Only; Mail/Phone Authorization Only x Contactless Mobile Commerce Device | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Manual / Keyed Merchandise Return | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Merchandise Return | Debit x Credential on File | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction" |
| ATL105 Maestro Direct Debit Manual / Keyed Void of a Merchandise Return | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Void of a Merchandise Return | original Merchandise Return x Credential on File (excluded) | p.638 "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction" |
| ATL105 Maestro Direct Debit Manual / Keyed Purchase Reversal | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Purchase Reversal | original Purchase/Capture x Credential on File (excluded) | p.638 "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction" |
| ATL105 Maestro Direct Debit EMV Contact Chip Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Contactless Magnetic Stripe | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Manual / Keyed Mail/Phone Reversal | Debit x Manual / Keyed; Debit x Mail/Phone Reversal | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Chip Fallback | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Credential on File Mail/Phone Reversal | Debit x Mail/Phone Reversal | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Mail/Phone Reversal | Debit x Mail/Phone Reversal; Mail/Phone Reversal x Contactless Mobile Commerce Device | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 Maestro Direct Debit Manual / Keyed Time-out Reversal | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Time-out Reversal | original Purchase/Capture x Credential on File (excluded); original Mail/Phone Purchase x Credential on File (excluded) | p.638 "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction"; p.638 "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction" |
| ATL105 Maestro Direct Debit Manual / Keyed Cancellation | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Cancellation | original Authorization Only x Credential on File (excluded); original Mail/Phone Authorization Only x Credential on File (excluded) | p.638 "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction"; p.638 "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction" |
| ATL105 Maestro Direct Debit Manual / Keyed Balance Inquiry | Debit x Manual / Keyed | p.96 "Manual entry is not allowed on a debit card transaction" |
| ATL105 Maestro Direct Debit Credential on File Balance Inquiry | Debit x Credential on File | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction" |
| ATL105 Maestro Direct Debit EMV Contact Chip Account Verification | Debit x Account Verification; Account Verification x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Account Verification | Debit x Account Verification; Account Verification x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Account Verification | Debit x Manual / Keyed; Debit x Account Verification | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Account Verification | Debit x Credential on File; Debit x Account Verification | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Account Verification | Debit x Account Verification | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Token Registration | Debit x Token Registration; Token Registration x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Token Registration | Debit x Token Registration; Token Registration x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Token Registration | Debit x Manual / Keyed; Debit x Token Registration | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Token Registration | Debit x Credential on File; Debit x Token Registration | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Token Registration | Debit x Token Registration | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Activate | Debit x Activate; Activate x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Activate | Debit x Activate; Activate x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Activate | Debit x Manual / Keyed; Debit x Activate | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Activate | Debit x Credential on File; Debit x Activate | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Activate | Debit x Activate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Deactivate | Debit x Deactivate; Deactivate x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Deactivate | Debit x Deactivate; Deactivate x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Deactivate | Debit x Manual / Keyed; Debit x Deactivate | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Deactivate | Debit x Credential on File; Debit x Deactivate | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Deactivate | Debit x Deactivate | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Balance Merge | Debit x Balance Merge; Balance Merge x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Balance Merge | Debit x Balance Merge; Balance Merge x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Balance Merge | Debit x Manual / Keyed; Debit x Balance Merge | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Balance Merge | Debit x Credential on File; Debit x Balance Merge | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Balance Merge | Debit x Balance Merge | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Replace Card | Debit x Replace Card; Replace Card x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Replace Card | Debit x Replace Card; Replace Card x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Replace Card | Debit x Manual / Keyed; Debit x Replace Card | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Replace Card | Debit x Credential on File; Debit x Replace Card | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Replace Card | Debit x Replace Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Recharge Card | Debit x Recharge Card; Recharge Card x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Recharge Card | Debit x Recharge Card; Recharge Card x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Recharge Card | Debit x Manual / Keyed; Debit x Recharge Card | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Recharge Card | Debit x Credential on File; Debit x Recharge Card | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Recharge Card | Debit x Recharge Card | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip RBC Loyalty Lookup | Debit x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip RBC Loyalty Lookup | Debit x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed RBC Loyalty Lookup | Debit x Manual / Keyed; Debit x RBC Loyalty Lookup | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File RBC Loyalty Lookup | Debit x Credential on File; Debit x RBC Loyalty Lookup | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device RBC Loyalty Lookup | Debit x RBC Loyalty Lookup | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit EMV Contact Chip Loyalty Advice Function | Debit x Loyalty Advice Function; Loyalty Advice Function x EMV Contact Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit EMV Contactless Chip Loyalty Advice Function | Debit x Loyalty Advice Function; Loyalty Advice Function x EMV Contactless Chip | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 Maestro Direct Debit Magnetic Stripe Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Magnetic Stripe Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Manual / Keyed Loyalty Advice Function | Debit x Manual / Keyed; Debit x Loyalty Advice Function | p.96 "Manual entry is not allowed on a debit card transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Chip Fallback Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Credential on File Loyalty Advice Function | Debit x Credential on File; Debit x Loyalty Advice Function | p.638 "also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"; p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |
| ATL105 Maestro Direct Debit Contactless Mobile Commerce Device Loyalty Advice Function | Debit x Loyalty Advice Function | p.97 "Not in the list: The following point-of-sale transactions are supported for debit card processing:" |

### Spec citations behind these scenarios

-  p.512 (Appendix C): "40 Maestro Direct Debit"
- SRC-ATL105-PDF-001 p.81 (anchored:A-SALE-PREAUTH-TRACK): "If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.97 (10.2.2 Supported Transaction Types): "The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture"
- SRC-ATL105-PDF-001 p.161 (10.14.2.3 Supported Cards): "The following debit cards are supported for EMV processing: ... Maestro"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Purchase"

