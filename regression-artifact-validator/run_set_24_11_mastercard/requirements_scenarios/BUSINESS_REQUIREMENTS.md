# Business Requirements - mastercard-credit-generated

Selection: **platform=BUYPASS, spec=ATL105, network=MASTERCARD, program=CREDIT**. A Business Requirement (BR) is one Platform x Spec x Network x Card Program inside the selection; every spec-evaluated original transaction category x entry mode under it is a Scenario. Follow-on categories are flow legs.

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **True**
- Validation: **passed**
- Built: 2026-11-24T16:06:14.686453+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1 | BUYPASS ATL105 MasterCard Credit | ATL105 2026-4 | 36 | 26 | 0 | 8 | 0 | 0 | 90 | 576 |

## BR1. BUYPASS ATL105 MasterCard Credit

`BR-12B94100` - status **built**

Programs: Credit 36. Transaction categories: 6. Entry modes: 8. Scenarios with a pre-authorized variant: 8.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS2 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS3 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS4 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS5 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS6 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS7 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File | valid |  | single-leg +pre-auth | 0, 4 | 10 | 020 (program_domain) | Financial Transaction Request | 199 | 0 |
| BR1_TS8 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS9 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed | valid |  | single-leg | 6 | 01 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS10 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File | unconfirmed | yes | single-leg | 6 | 10 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS11 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS12 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS13 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS14 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS15 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid |  | single-leg | 3, 5 | 01 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS16 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid |  | single-leg | 3, 5 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS17 | BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File | valid |  | single-leg | 3, 5 | 10 | 020 (program_domain) | Financial Transaction Request | 213 | 0 |
| BR1_TS18 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS19 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed | valid |  | single-leg | B | 01 | 020 (program_domain) | Financial Transaction Request | 177 | 0 |
| BR1_TS20 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File | unconfirmed | yes | single-leg | B | 10 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS21 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip | valid |  | single-leg | 7 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 387 | 0 |
| BR1_TS22 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip | valid |  | single-leg | 7 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 363 | 0 |
| BR1_TS23 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe | spec_implied |  | single-leg | 7 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 218 | 0 |
| BR1_TS24 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe | valid |  | single-leg | 7 | 91 | 020 (program_domain) | Financial Transaction Request | 160 | 0 |
| BR1_TS25 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed | valid |  | single-leg | 7 | 01 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS26 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback | valid |  | single-leg | 7 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS27 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File | unconfirmed | yes | single-leg | 7 | 10 | 020 (program_domain) | Financial Transaction Request | 194 | 0 |
| BR1_TS28 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 020 (program_domain) | Financial Transaction Request | 159 | 0 |
| BR1_TS29 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip | valid |  | single-leg | E | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 367 | 0 |
| BR1_TS30 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip | valid |  | single-leg | E | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 343 | 0 |
| BR1_TS31 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe | spec_implied |  | single-leg | E | 02, 90 | 020 (program_domain) | Financial Transaction Request | 202 | 0 |
| BR1_TS32 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe | valid |  | single-leg | E | 91 | 020 (program_domain) | Financial Transaction Request | 144 | 0 |
| BR1_TS33 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed | valid |  | single-leg | E | 01 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS34 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback | valid |  | single-leg | E | 79, 80 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS35 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File | unconfirmed | yes | single-leg | E | 10 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS36 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | E | 82 | 020 (program_domain) | Financial Transaction Request | 143 | 0 |
| BR1_TS37 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS38 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS39 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS40 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS41 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS42 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS43 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS44 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS45 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS46 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS47 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS48 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS49 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS50 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS51 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS52 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS53 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS54 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS55 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS56 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS57 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS58 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid |  | single-leg | 3, 5 | 01 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS59 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid |  | single-leg | 3, 5 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS60 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS61 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS62 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS63 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS64 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS65 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS66 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS67 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS68 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS69 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS70 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS71 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS72 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS73 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS74 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS75 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS76 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS77 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS78 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS79 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS80 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS81 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS82 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid |  | single-leg | 3, 5 | 01 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS83 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid |  | single-leg | 3, 5 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS84 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS85 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip | valid |  | single-leg | 7 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 387 | 0 |
| BR1_TS86 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip | valid |  | single-leg | 7 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 363 | 0 |
| BR1_TS87 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe | spec_implied |  | single-leg | 7 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 218 | 0 |
| BR1_TS88 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe | valid |  | single-leg | 7 | 91 | 020 (program_domain) | Financial Transaction Request | 160 | 0 |
| BR1_TS89 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed | valid |  | single-leg | 7 | 01 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS90 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback | valid |  | single-leg | 7 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS91 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 020 (program_domain) | Financial Transaction Request | 159 | 0 |
| BR1_TS92 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip | valid |  | single-leg | E | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 367 | 0 |
| BR1_TS93 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip | valid |  | single-leg | E | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 343 | 0 |
| BR1_TS94 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe | spec_implied |  | single-leg | E | 02, 90 | 020 (program_domain) | Financial Transaction Request | 202 | 0 |
| BR1_TS95 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe | valid |  | single-leg | E | 91 | 020 (program_domain) | Financial Transaction Request | 144 | 0 |
| BR1_TS96 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed | valid |  | single-leg | E | 01 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS97 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback | valid |  | single-leg | E | 79, 80 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS98 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | E | 82 | 020 (program_domain) | Financial Transaction Request | 143 | 0 |
| BR1_TS99 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS100 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS101 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS102 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS103 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS104 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS105 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS106 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS107 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS108 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS109 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS110 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS111 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS112 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS113 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS114 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS115 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS116 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS117 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS118 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS119 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS120 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid |  | single-leg | 3, 5 | 01 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS121 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid |  | single-leg | 3, 5 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS122 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS123 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip | valid |  | single-leg | 7 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 387 | 0 |
| BR1_TS124 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip | valid |  | single-leg | 7 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 363 | 0 |
| BR1_TS125 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe | spec_implied |  | single-leg | 7 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 218 | 0 |
| BR1_TS126 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe | valid |  | single-leg | 7 | 91 | 020 (program_domain) | Financial Transaction Request | 160 | 0 |
| BR1_TS127 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed | valid |  | single-leg | 7 | 01 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS128 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback | valid |  | single-leg | 7 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS129 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 020 (program_domain) | Financial Transaction Request | 159 | 0 |
| BR1_TS130 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip | valid |  | single-leg | E | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 367 | 0 |
| BR1_TS131 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip | valid |  | single-leg | E | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 343 | 0 |
| BR1_TS132 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe | spec_implied |  | single-leg | E | 02, 90 | 020 (program_domain) | Financial Transaction Request | 202 | 0 |
| BR1_TS133 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe | valid |  | single-leg | E | 91 | 020 (program_domain) | Financial Transaction Request | 144 | 0 |
| BR1_TS134 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed | valid |  | single-leg | E | 01 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS135 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback | valid |  | single-leg | E | 79, 80 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS136 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | E | 82 | 020 (program_domain) | Financial Transaction Request | 143 | 0 |
| BR1_TS137 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS138 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS139 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS140 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS141 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS142 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS143 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS144 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS145 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS146 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS147 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS148 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS149 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS150 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS151 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS152 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS153 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS154 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS155 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS156 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS157 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS158 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid |  | single-leg | 3, 5 | 01 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS159 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid |  | single-leg | 3, 5 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS160 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS161 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip | valid |  | single-leg | 7 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 387 | 0 |
| BR1_TS162 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip | valid |  | single-leg | 7 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 363 | 0 |
| BR1_TS163 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe | spec_implied |  | single-leg | 7 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 218 | 0 |
| BR1_TS164 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe | valid |  | single-leg | 7 | 91 | 020 (program_domain) | Financial Transaction Request | 160 | 0 |
| BR1_TS165 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed | valid |  | single-leg | 7 | 01 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS166 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback | valid |  | single-leg | 7 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 192 | 0 |
| BR1_TS167 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 7 | 82 | 020 (program_domain) | Financial Transaction Request | 159 | 0 |
| BR1_TS168 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS169 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS170 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS171 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS172 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS173 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 401 | 0 |
| BR1_TS174 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 377 | 0 |
| BR1_TS175 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 237 | 0 |
| BR1_TS176 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid |  | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS177 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS178 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS179 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS180 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS181 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS182 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS183 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS184 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS185 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS186 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS187 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS188 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS189 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |
| BR1_TS190 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 392 | 0 |
| BR1_TS191 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 368 | 0 |
| BR1_TS192 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 224 | 0 |
| BR1_TS193 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid |  | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 165 | 0 |
| BR1_TS194 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid |  | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS195 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid |  | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 197 | 0 |
| BR1_TS196 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 164 | 0 |

### Multi-leg flows (follow-on scenarios and pre-authorized variants)

Each leg sends its own category's Transaction Type code for the merchant's terminal (attended POS / customer-activated). Flows the spec does not back are listed separately as BLOCKED.

**BR1_TS1 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS2 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS3 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS4 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS5 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS6 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS7 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 10, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS8 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS37 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS38 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS39 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS40 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS41 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS47 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS48 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS49 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS50 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS51 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS52 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS53 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS61 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS62 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS63 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS64 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS65 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS71 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS72 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS73 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS74 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS75 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS76 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS77 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS99 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS100 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS101 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS102 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS103 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS109 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS110 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS111 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS112 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS113 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS114 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS115 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS137 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS138 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS139 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS140 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS141 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS147 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS148 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS149 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS150 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS151 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS152 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS153 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS168 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS169 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS170 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS171 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS172 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS178 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS179 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS180 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS181 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS182 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS183 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS184 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS185 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS186 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS187 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS188 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS189 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS190 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS191 BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS192 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS193 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS194 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS195 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

**BR1_TS196 BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1); carries Approval Number (BR-368-6, p.368); carries Terminal Identifier (BR-40-2, p.40)

### Follow-on combinations (flow legs, not scenarios)

Reversal, time-out reversal, cancellation, void of a return and mail/phone reversal are steps of the original transaction's flow test cases (follow_on_leg_combinations.csv).

| Leg | Combination | Status | Usable flows | Blocked |
|---|---|---|---|---|
| BR1_LEG1 | Void of a Merchandise Return – EMV Contact Chip | unconfirmed | 1 | 0 |
| BR1_LEG2 | Void of a Merchandise Return – EMV Contactless Chip | unconfirmed | 1 | 0 |
| BR1_LEG3 | Void of a Merchandise Return – Magnetic Stripe | spec_implied | 1 | 0 |
| BR1_LEG4 | Void of a Merchandise Return – Contactless Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG5 | Void of a Merchandise Return – Manual / Keyed | valid | 1 | 0 |
| BR1_LEG6 | Void of a Merchandise Return – Chip Fallback | valid | 1 | 0 |
| BR1_LEG7 | Void of a Merchandise Return – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG8 | Void of a Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG9 | Purchase Reversal – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG10 | Purchase Reversal – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG11 | Purchase Reversal – Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG12 | Purchase Reversal – Contactless Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG13 | Purchase Reversal – Manual / Keyed | valid | 1 | 0 |
| BR1_LEG14 | Purchase Reversal – Chip Fallback | valid | 1 | 0 |
| BR1_LEG15 | Purchase Reversal – Credential on File | valid | 1 | 0 |
| BR1_LEG16 | Purchase Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG17 | Mail/Phone Reversal – Manual / Keyed | valid | 1 | 0 |
| BR1_LEG18 | Mail/Phone Reversal – Credential on File | unconfirmed | 1 | 0 |
| BR1_LEG19 | Time-out Reversal – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG20 | Time-out Reversal – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG21 | Time-out Reversal – Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG22 | Time-out Reversal – Contactless Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG23 | Time-out Reversal – Manual / Keyed | valid | 2 | 0 |
| BR1_LEG24 | Time-out Reversal – Chip Fallback | valid | 1 | 0 |
| BR1_LEG25 | Time-out Reversal – Credential on File | valid | 2 | 0 |
| BR1_LEG26 | Time-out Reversal – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |
| BR1_LEG27 | Cancellation – EMV Contact Chip | valid | 1 | 0 |
| BR1_LEG28 | Cancellation – EMV Contactless Chip | valid | 1 | 0 |
| BR1_LEG29 | Cancellation – Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG30 | Cancellation – Contactless Magnetic Stripe | valid | 1 | 0 |
| BR1_LEG31 | Cancellation – Manual / Keyed | valid | 2 | 0 |
| BR1_LEG32 | Cancellation – Chip Fallback | valid | 1 | 0 |
| BR1_LEG33 | Cancellation – Credential on File | valid | 2 | 0 |
| BR1_LEG34 | Cancellation – Contactless Mobile Commerce Device | unconfirmed | 1 | 0 |

### Scenarios requiring SME confirmation

The spec neither states nor rules out these pairs; the scenario is kept and flagged.

| ID | Scenario | Open pair(s) |
|---|---|---|
| BR1_TS8 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS10 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File | txn_category-entry_mode |
| BR1_TS18 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS20 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File | txn_category-entry_mode |
| BR1_TS27 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File | txn_category-entry_mode |
| BR1_TS28 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS35 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File | txn_category-entry_mode |
| BR1_TS36 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS41 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS46 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS53 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS60 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS65 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS70 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS77 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS84 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS91 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS98 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS103 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS108 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS115 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS122 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS129 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS136 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS141 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS146 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS153 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS160 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS167 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS172 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS177 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS182 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS189 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS196 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | program-entry_mode, network-entry_mode, txn_category-entry_mode |

### Excluded combinations (not scenarios)

| Combination | Blocking pair | Evidence |
|---|---|---|
| ATL105 MasterCard Credit EMV Contact Chip Mail/Phone Purchase | Mail/Phone Purchase x EMV Contact Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Mail/Phone Purchase | Mail/Phone Purchase x EMV Contactless Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Mail/Phone Purchase | Mail/Phone Purchase x Magnetic Stripe | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Mail/Phone Purchase | Mail/Phone Purchase x Contactless Magnetic Stripe | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Chip Fallback Mail/Phone Purchase | Mail/Phone Purchase x Chip Fallback | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Mail/Phone Purchase | Mail/Phone Purchase x Contactless Mobile Commerce Device | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit EMV Contact Chip Mail/Phone Authorization Only | Mail/Phone Authorization Only x EMV Contact Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Mail/Phone Authorization Only | Mail/Phone Authorization Only x EMV Contactless Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Mail/Phone Authorization Only | Mail/Phone Authorization Only x Magnetic Stripe | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Mail/Phone Authorization Only | Mail/Phone Authorization Only x Contactless Magnetic Stripe | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Chip Fallback Mail/Phone Authorization Only | Mail/Phone Authorization Only x Chip Fallback | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Mail/Phone Authorization Only | Mail/Phone Authorization Only x Contactless Mobile Commerce Device | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit EMV Contact Chip Mail/Phone Reversal | Mail/Phone Reversal x EMV Contact Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Mail/Phone Reversal | Mail/Phone Reversal x EMV Contactless Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Mail/Phone Reversal | Mail/Phone Reversal x Magnetic Stripe | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Mail/Phone Reversal | Mail/Phone Reversal x Contactless Magnetic Stripe | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Chip Fallback Mail/Phone Reversal | Mail/Phone Reversal x Chip Fallback | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Mail/Phone Reversal | Mail/Phone Reversal x Contactless Mobile Commerce Device | p.695 "Mail and/or telephone order (customer not present)" |
| ATL105 MasterCard Credit EMV Contact Chip Account Verification | Credit x Account Verification; Account Verification x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Account Verification | Credit x Account Verification; Account Verification x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Account Verification | Credit x Account Verification | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Account Verification | Credit x Account Verification | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Account Verification | Credit x Account Verification | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Account Verification | Credit x Account Verification | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Account Verification | Credit x Account Verification | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Account Verification | Credit x Account Verification | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Token Registration | Credit x Token Registration; Token Registration x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Token Registration | Credit x Token Registration; Token Registration x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Token Registration | Credit x Token Registration | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Token Registration | Credit x Token Registration | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Token Registration | Credit x Token Registration | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Token Registration | Credit x Token Registration | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Token Registration | Credit x Token Registration | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Token Registration | Credit x Token Registration | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Activate | Credit x Activate; Activate x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Activate | Credit x Activate; Activate x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Activate | Credit x Activate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Activate | Credit x Activate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Activate | Credit x Activate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Activate | Credit x Activate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Activate | Credit x Activate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Activate | Credit x Activate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Deactivate | Credit x Deactivate; Deactivate x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Deactivate | Credit x Deactivate; Deactivate x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Deactivate | Credit x Deactivate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Deactivate | Credit x Deactivate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Deactivate | Credit x Deactivate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Deactivate | Credit x Deactivate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Deactivate | Credit x Deactivate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Deactivate | Credit x Deactivate | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Balance Merge | Credit x Balance Merge; Balance Merge x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Balance Merge | Credit x Balance Merge; Balance Merge x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Balance Merge | Credit x Balance Merge | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Balance Merge | Credit x Balance Merge | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Balance Merge | Credit x Balance Merge | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Balance Merge | Credit x Balance Merge | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Balance Merge | Credit x Balance Merge | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Balance Merge | Credit x Balance Merge | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Replace Card | Credit x Replace Card; Replace Card x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Replace Card | Credit x Replace Card; Replace Card x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Replace Card | Credit x Replace Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Replace Card | Credit x Replace Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Replace Card | Credit x Replace Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Replace Card | Credit x Replace Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Replace Card | Credit x Replace Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Replace Card | Credit x Replace Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Recharge Card | Credit x Recharge Card; Recharge Card x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Recharge Card | Credit x Recharge Card; Recharge Card x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Recharge Card | Credit x Recharge Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Recharge Card | Credit x Recharge Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Recharge Card | Credit x Recharge Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Recharge Card | Credit x Recharge Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Recharge Card | Credit x Recharge Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Recharge Card | Credit x Recharge Card | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip RBC Loyalty Lookup | Credit x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip RBC Loyalty Lookup | Credit x RBC Loyalty Lookup; RBC Loyalty Lookup x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe RBC Loyalty Lookup | Credit x RBC Loyalty Lookup | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe RBC Loyalty Lookup | Credit x RBC Loyalty Lookup | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed RBC Loyalty Lookup | Credit x RBC Loyalty Lookup | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback RBC Loyalty Lookup | Credit x RBC Loyalty Lookup | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File RBC Loyalty Lookup | Credit x RBC Loyalty Lookup | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device RBC Loyalty Lookup | Credit x RBC Loyalty Lookup | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit EMV Contact Chip Loyalty Advice Function | Credit x Loyalty Advice Function; Loyalty Advice Function x EMV Contact Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Loyalty Advice Function | Credit x Loyalty Advice Function; Loyalty Advice Function x EMV Contactless Chip | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit Magnetic Stripe Loyalty Advice Function | Credit x Loyalty Advice Function | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Magnetic Stripe Loyalty Advice Function | Credit x Loyalty Advice Function | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Manual / Keyed Loyalty Advice Function | Credit x Loyalty Advice Function | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Chip Fallback Loyalty Advice Function | Credit x Loyalty Advice Function | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Credential on File Loyalty Advice Function | Credit x Loyalty Advice Function | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |
| ATL105 MasterCard Credit Contactless Mobile Commerce Device Loyalty Advice Function | Credit x Loyalty Advice Function | p.83 "Not in the list: The following point-of-sale transactions are supported for credit card processing:" |

### Spec citations behind these scenarios

-  p.511 (Appendix C): "03 MasterCard Credit"
- SRC-ATL105-PDF-001 p.81 (anchored:A-SALE-PREAUTH-TRACK): "If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent"
- SRC-ATL105-PDF-001 p.81 (anchored:A-CREDIT-MANUAL): "Manual entry is allowed on a credit card transaction"
- SRC-ATL105-PDF-001 p.81 (anchored:A-CREDIT-TRACK): "The default track that is sent to the host should be Track 2 if the device supports Track 2"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Authorization Only"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Purchase"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Purchase/Capture"
- SRC-ATL105-PDF-001 p.86 (scoped:SR-CREDIT-CONTACTLESS-MAGSTRIPE): "When applied to credit card processing"
- SRC-ATL105-PDF-001 p.86 (anchored:A-CREDIT-RFID): "capture and transmit full, unaltered Track 2 data from a card without requiring the card to have contact with the point-of-sale device"
- SRC-ATL105-PDF-001 p.86 (scoped:SR-CREDIT-MOTO-MANUAL): "non-face-to-face environment, such as mail order, phone order"
- SRC-ATL105-PDF-001 p.161 (10.14.2.3 Supported Cards): "The following credit cards are supported for EMV processing: ... MasterCard"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Purchase"
- SRC-ATL105-PDF-001 p.162 (anchored:A-CREDIT-FALLBACK): "If a chip card is presented at a chip capable terminal and a chip transaction cannot be performed, then the merchant may process the chip card as a magnetic stripe read"
- SRC-ATL105-PDF-001 p.162 (scoped:SR-CREDIT-EMV-FALLBACK): "the merchant may process the chip card as a magnetic stripe read (swipe)"
- SRC-ATL105-PDF-001 p.575 (scoped:SR-CREDIT-COF): "transactions (Authorization, Completion, Cancellation, Reversal etc.)"
- SRC-ATL105-PDF-001 p.638 (anchored:APPJ-FN2-COF-CREDIT): "Entry Mode 10 is applicable to Visa Credit, MasterCard Credit, Discover Credit, and Amex transactions"

