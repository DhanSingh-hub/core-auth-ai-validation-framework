# Business Requirements - buypass-atl105-mastercard

Selection: **platform=BUYPASS, spec=ATL105, network=MASTERCARD**. A Business Requirement (BR) is the selection; every spec-evaluated combination under it is a Scenario (Platform > Spec > Network > Card Program > Transaction Category > Entry Mode).

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **True**
- Validation: **passed**
- Built: 2026-10-26T16:45:22.994378+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1 | BUYPASS ATL105 MasterCard | ATL105 2026-4 | 108 | 23 | 0 | 85 | 48 | 4 | 212 | 785 |

## BR1. BUYPASS ATL105 MasterCard

`BR-9057297F` - status **built**

Programs: Credit 68, Fleet 40. Transaction categories: 11. Entry modes: 8. Scenarios with a pre-authorized variant: 16.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 | ATL105 MasterCard Credit EMV Contact Chip Purchase/Capture | valid |  | single-leg +pre-auth | 0, 4 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 245 | 0 |
| BR1_TS2 | ATL105 MasterCard Credit EMV Contactless Chip Purchase/Capture | valid |  | single-leg +pre-auth | 0, 4 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 245 | 0 |
| BR1_TS3 | ATL105 MasterCard Credit Magnetic Stripe Purchase/Capture | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 216 | 0 |
| BR1_TS4 | ATL105 MasterCard Credit Contactless Magnetic Stripe Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 91 | 020 (program_domain) | Financial Transaction Request | 170 | 0 |
| BR1_TS5 | ATL105 MasterCard Credit Manual / Keyed Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 01 | 020 (program_domain) | Financial Transaction Request | 203 | 0 |
| BR1_TS6 | ATL105 MasterCard Credit Chip Fallback Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 174 | 0 |
| BR1_TS7 | ATL105 MasterCard Credit Credential on File Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 10 | 020 (program_domain) | Financial Transaction Request | 207 | 0 |
| BR1_TS8 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 020 (program_domain) | Financial Transaction Request | 170 | 0 |
| BR1_TS9 | ATL105 MasterCard Credit Manual / Keyed Mail/Phone Purchase | unconfirmed | yes | single-leg | 6 | 01 | 020 (program_domain) | Financial Transaction Request | 175 | 0 |
| BR1_TS10 | ATL105 MasterCard Credit Credential on File Mail/Phone Purchase | unconfirmed | yes | single-leg | 6 | 10 | 020 (program_domain) | Financial Transaction Request | 179 | 0 |
| BR1_TS11 | ATL105 MasterCard Credit EMV Contact Chip Authorization Only | valid |  | single-leg | 3, 5 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 253 | 0 |
| BR1_TS12 | ATL105 MasterCard Credit EMV Contactless Chip Authorization Only | valid |  | single-leg | 3, 5 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 253 | 0 |
| BR1_TS13 | ATL105 MasterCard Credit Magnetic Stripe Authorization Only | valid |  | single-leg | 3, 5 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 223 | 0 |
| BR1_TS14 | ATL105 MasterCard Credit Contactless Magnetic Stripe Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 91 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS15 | ATL105 MasterCard Credit Manual / Keyed Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 01 | 020 (program_domain) | Financial Transaction Request | 211 | 0 |
| BR1_TS16 | ATL105 MasterCard Credit Chip Fallback Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 182 | 0 |
| BR1_TS17 | ATL105 MasterCard Credit Credential on File Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 10 | 020 (program_domain) | Financial Transaction Request | 215 | 0 |
| BR1_TS18 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 82 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS19 | ATL105 MasterCard Credit Manual / Keyed Mail/Phone Authorization Only | unconfirmed | yes | single-leg | B | 01 | 020 (program_domain) | Financial Transaction Request | 176 | 0 |
| BR1_TS20 | ATL105 MasterCard Credit Credential on File Mail/Phone Authorization Only | unconfirmed | yes | single-leg | B | 10 | 020 (program_domain) | Financial Transaction Request | 180 | 0 |
| BR1_TS21 | ATL105 MasterCard Credit EMV Contact Chip Merchandise Return | valid |  | single-leg | 7 | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 231 | 0 |
| BR1_TS22 | ATL105 MasterCard Credit EMV Contactless Chip Merchandise Return | valid |  | single-leg | 7 | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 231 | 0 |
| BR1_TS23 | ATL105 MasterCard Credit Magnetic Stripe Merchandise Return | unconfirmed | yes | single-leg | 7 | 02, 90 | 020 (program_domain) | Financial Transaction Request | 201 | 0 |
| BR1_TS24 | ATL105 MasterCard Credit Contactless Magnetic Stripe Merchandise Return | unconfirmed | yes | single-leg | 7 | 91 | 020 (program_domain) | Financial Transaction Request | 156 | 0 |
| BR1_TS25 | ATL105 MasterCard Credit Manual / Keyed Merchandise Return | unconfirmed | yes | single-leg | 7 | 01 | 020 (program_domain) | Financial Transaction Request | 189 | 0 |
| BR1_TS26 | ATL105 MasterCard Credit Chip Fallback Merchandise Return | unconfirmed | yes | single-leg | 7 | 79, 80 | 020 (program_domain) | Financial Transaction Request | 160 | 0 |
| BR1_TS27 | ATL105 MasterCard Credit Credential on File Merchandise Return | unconfirmed | yes | single-leg | 7 | 10 | 020 (program_domain) | Financial Transaction Request | 193 | 0 |
| BR1_TS28 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Merchandise Return | unconfirmed | yes | single-leg | 7 | 82 | 020 (program_domain) | Financial Transaction Request | 156 | 0 |
| BR1_TS29 | ATL105 MasterCard Credit Magnetic Stripe Void of a Merchandise Return | unconfirmed | yes | multi-leg | U | 02, 90 | 020 (program_domain) | per leg | 190 | 0 |
| BR1_TS30 | ATL105 MasterCard Credit Contactless Magnetic Stripe Void of a Merchandise Return | unconfirmed | yes | multi-leg | U | 91 | 020 (program_domain) | per leg | 145 | 0 |
| BR1_TS31 | ATL105 MasterCard Credit Manual / Keyed Void of a Merchandise Return | unconfirmed | yes | multi-leg | U | 01 | 020 (program_domain) | per leg | 178 | 0 |
| BR1_TS32 | ATL105 MasterCard Credit Chip Fallback Void of a Merchandise Return | unconfirmed | yes | multi-leg | U | 79, 80 | 020 (program_domain) | per leg | 149 | 0 |
| BR1_TS33 | ATL105 MasterCard Credit Credential on File Void of a Merchandise Return | unconfirmed | yes | multi-leg | U | 10 | 020 (program_domain) | per leg | 182 | 0 |
| BR1_TS34 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Void of a Merchandise Return | unconfirmed | yes | multi-leg | U | 82 | 020 (program_domain) | per leg | 145 | 0 |
| BR1_TS35 | ATL105 MasterCard Credit EMV Contact Chip Purchase Reversal | valid |  | multi-leg | 8 | 05, 95 | 020 (program_domain) | per leg | 233 | 0 |
| BR1_TS36 | ATL105 MasterCard Credit EMV Contactless Chip Purchase Reversal | valid |  | multi-leg | 8 | 07, 86 | 020 (program_domain) | per leg | 233 | 0 |
| BR1_TS37 | ATL105 MasterCard Credit Magnetic Stripe Purchase Reversal | valid |  | multi-leg | 8 | 02, 90 | 020 (program_domain) | per leg | 202 | 0 |
| BR1_TS38 | ATL105 MasterCard Credit Contactless Magnetic Stripe Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 91 | 020 (program_domain) | per leg | 157 | 0 |
| BR1_TS39 | ATL105 MasterCard Credit Manual / Keyed Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 01 | 020 (program_domain) | per leg | 190 | 0 |
| BR1_TS40 | ATL105 MasterCard Credit Chip Fallback Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 79, 80 | 020 (program_domain) | per leg | 161 | 0 |
| BR1_TS41 | ATL105 MasterCard Credit Credential on File Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 10 | 020 (program_domain) | per leg | 194 | 0 |
| BR1_TS42 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 82 | 020 (program_domain) | per leg | 157 | 0 |
| BR1_TS43 | ATL105 MasterCard Credit Manual / Keyed Mail/Phone Reversal | unconfirmed | yes | multi-leg | C | 01 | 020 (program_domain) | per leg | 174 | 2 |
| BR1_TS44 | ATL105 MasterCard Credit Credential on File Mail/Phone Reversal | unconfirmed | yes | multi-leg | C | 10 | 020 (program_domain) | per leg | 178 | 2 |
| BR1_TS45 | ATL105 MasterCard Credit EMV Contact Chip Time-out Reversal | valid |  | multi-leg | Z | 05, 95 | 020 (program_domain) | per leg | 252 | 0 |
| BR1_TS46 | ATL105 MasterCard Credit EMV Contactless Chip Time-out Reversal | valid |  | multi-leg | Z | 07, 86 | 020 (program_domain) | per leg | 252 | 0 |
| BR1_TS47 | ATL105 MasterCard Credit Magnetic Stripe Time-out Reversal | valid |  | multi-leg | Z | 02, 90 | 020 (program_domain) | per leg | 221 | 0 |
| BR1_TS48 | ATL105 MasterCard Credit Contactless Magnetic Stripe Time-out Reversal | unconfirmed | yes | multi-leg | Z | 91 | 020 (program_domain) | per leg | 176 | 0 |
| BR1_TS49 | ATL105 MasterCard Credit Manual / Keyed Time-out Reversal | unconfirmed | yes | multi-leg | Z | 01 | 020 (program_domain) | per leg | 209 | 1 |
| BR1_TS50 | ATL105 MasterCard Credit Chip Fallback Time-out Reversal | unconfirmed | yes | multi-leg | Z | 79, 80 | 020 (program_domain) | per leg | 180 | 0 |
| BR1_TS51 | ATL105 MasterCard Credit Credential on File Time-out Reversal | unconfirmed | yes | multi-leg | Z | 10 | 020 (program_domain) | per leg | 213 | 1 |
| BR1_TS52 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Time-out Reversal | unconfirmed | yes | multi-leg | Z | 82 | 020 (program_domain) | per leg | 176 | 0 |
| BR1_TS53 | ATL105 MasterCard Credit EMV Contact Chip Cancellation | valid |  | multi-leg | S | 05, 95 | 020 (program_domain) | per leg | 240 | 0 |
| BR1_TS54 | ATL105 MasterCard Credit EMV Contactless Chip Cancellation | valid |  | multi-leg | S | 07, 86 | 020 (program_domain) | per leg | 240 | 0 |
| BR1_TS55 | ATL105 MasterCard Credit Magnetic Stripe Cancellation | valid |  | multi-leg | S | 02, 90 | 020 (program_domain) | per leg | 210 | 0 |
| BR1_TS56 | ATL105 MasterCard Credit Contactless Magnetic Stripe Cancellation | unconfirmed | yes | multi-leg | S | 91 | 020 (program_domain) | per leg | 165 | 0 |
| BR1_TS57 | ATL105 MasterCard Credit Manual / Keyed Cancellation | unconfirmed | yes | multi-leg | S | 01 | 020 (program_domain) | per leg | 198 | 0 |
| BR1_TS58 | ATL105 MasterCard Credit Chip Fallback Cancellation | unconfirmed | yes | multi-leg | S | 79, 80 | 020 (program_domain) | per leg | 169 | 0 |
| BR1_TS59 | ATL105 MasterCard Credit Credential on File Cancellation | unconfirmed | yes | multi-leg | S | 10 | 020 (program_domain) | per leg | 203 | 0 |
| BR1_TS60 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Cancellation | unconfirmed | yes | multi-leg | S | 82 | 020 (program_domain) | per leg | 165 | 0 |
| BR1_TS61 | ATL105 MasterCard Credit EMV Contact Chip Balance Inquiry | valid |  | single-leg | E | 05, 95 | 020 (program_domain) | EMV Financial Transaction Request | 216 | 0 |
| BR1_TS62 | ATL105 MasterCard Credit EMV Contactless Chip Balance Inquiry | valid |  | single-leg | E | 07, 86 | 020 (program_domain) | EMV Financial Transaction Request | 216 | 0 |
| BR1_TS63 | ATL105 MasterCard Credit Magnetic Stripe Balance Inquiry | unconfirmed | yes | single-leg | E | 02, 90 | 020 (program_domain) | Financial Transaction Request | 186 | 0 |
| BR1_TS64 | ATL105 MasterCard Credit Contactless Magnetic Stripe Balance Inquiry | unconfirmed | yes | single-leg | E | 91 | 020 (program_domain) | Financial Transaction Request | 141 | 0 |
| BR1_TS65 | ATL105 MasterCard Credit Manual / Keyed Balance Inquiry | unconfirmed | yes | single-leg | E | 01 | 020 (program_domain) | Financial Transaction Request | 174 | 0 |
| BR1_TS66 | ATL105 MasterCard Credit Chip Fallback Balance Inquiry | unconfirmed | yes | single-leg | E | 79, 80 | 020 (program_domain) | Financial Transaction Request | 145 | 0 |
| BR1_TS67 | ATL105 MasterCard Credit Credential on File Balance Inquiry | unconfirmed | yes | single-leg | E | 10 | 020 (program_domain) | Financial Transaction Request | 178 | 0 |
| BR1_TS68 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Balance Inquiry | unconfirmed | yes | single-leg | E | 82 | 020 (program_domain) | Financial Transaction Request | 141 | 0 |
| BR1_TS69 | ATL105 MasterCard Fleet EMV Contact Chip Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 05, 95 | 080 (network_specific) | EMV Financial Transaction Request | 444 | 0 |
| BR1_TS70 | ATL105 MasterCard Fleet EMV Contactless Chip Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 07, 86 | 080 (network_specific) | EMV Financial Transaction Request | 439 | 0 |
| BR1_TS71 | ATL105 MasterCard Fleet Magnetic Stripe Purchase/Capture | valid |  | single-leg +pre-auth | 0, 4 | 02, 90 | 080 (network_specific) | Financial Transaction Request | 410 | 0 |
| BR1_TS72 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 91 | 080 (network_specific) | Financial Transaction Request | 340 | 0 |
| BR1_TS73 | ATL105 MasterCard Fleet Manual / Keyed Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 01 | 080 (network_specific) | Financial Transaction Request | 377 | 0 |
| BR1_TS74 | ATL105 MasterCard Fleet Chip Fallback Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 79, 80 | 080 (network_specific) | Financial Transaction Request | 347 | 0 |
| BR1_TS75 | ATL105 MasterCard Fleet Credential on File Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 10 | 080 (network_specific) | Financial Transaction Request | 377 | 0 |
| BR1_TS76 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Purchase/Capture | unconfirmed | yes | single-leg +pre-auth | 0, 4 | 82 | 080 (network_specific) | Financial Transaction Request | 340 | 0 |
| BR1_TS77 | ATL105 MasterCard Fleet EMV Contact Chip Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 05, 95 | 080 (network_specific) | EMV Financial Transaction Request | 460 | 0 |
| BR1_TS78 | ATL105 MasterCard Fleet EMV Contactless Chip Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 07, 86 | 080 (network_specific) | EMV Financial Transaction Request | 455 | 0 |
| BR1_TS79 | ATL105 MasterCard Fleet Magnetic Stripe Authorization Only | valid |  | single-leg | 3, 5 | 02, 90 | 080 (network_specific) | Financial Transaction Request | 426 | 0 |
| BR1_TS80 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 91 | 080 (network_specific) | Financial Transaction Request | 356 | 0 |
| BR1_TS81 | ATL105 MasterCard Fleet Manual / Keyed Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 01 | 080 (network_specific) | Financial Transaction Request | 393 | 0 |
| BR1_TS82 | ATL105 MasterCard Fleet Chip Fallback Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 79, 80 | 080 (network_specific) | Financial Transaction Request | 363 | 0 |
| BR1_TS83 | ATL105 MasterCard Fleet Credential on File Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 10 | 080 (network_specific) | Financial Transaction Request | 393 | 0 |
| BR1_TS84 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Authorization Only | unconfirmed | yes | single-leg | 3, 5 | 82 | 080 (network_specific) | Financial Transaction Request | 356 | 0 |
| BR1_TS85 | ATL105 MasterCard Fleet EMV Contact Chip Merchandise Return | unconfirmed | yes | single-leg | 7 | 05, 95 | 080 (network_specific) | EMV Financial Transaction Request | 431 | 0 |
| BR1_TS86 | ATL105 MasterCard Fleet EMV Contactless Chip Merchandise Return | unconfirmed | yes | single-leg | 7 | 07, 86 | 080 (network_specific) | EMV Financial Transaction Request | 426 | 0 |
| BR1_TS87 | ATL105 MasterCard Fleet Magnetic Stripe Merchandise Return | unconfirmed | yes | single-leg | 7 | 02, 90 | 080 (network_specific) | Financial Transaction Request | 397 | 0 |
| BR1_TS88 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Merchandise Return | unconfirmed | yes | single-leg | 7 | 91 | 080 (network_specific) | Financial Transaction Request | 327 | 0 |
| BR1_TS89 | ATL105 MasterCard Fleet Manual / Keyed Merchandise Return | unconfirmed | yes | single-leg | 7 | 01 | 080 (network_specific) | Financial Transaction Request | 364 | 0 |
| BR1_TS90 | ATL105 MasterCard Fleet Chip Fallback Merchandise Return | unconfirmed | yes | single-leg | 7 | 79, 80 | 080 (network_specific) | Financial Transaction Request | 334 | 0 |
| BR1_TS91 | ATL105 MasterCard Fleet Credential on File Merchandise Return | unconfirmed | yes | single-leg | 7 | 10 | 080 (network_specific) | Financial Transaction Request | 364 | 0 |
| BR1_TS92 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Merchandise Return | unconfirmed | yes | single-leg | 7 | 82 | 080 (network_specific) | Financial Transaction Request | 327 | 0 |
| BR1_TS93 | ATL105 MasterCard Fleet EMV Contact Chip Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 05, 95 | 080 (network_specific) | per leg | 427 | 0 |
| BR1_TS94 | ATL105 MasterCard Fleet EMV Contactless Chip Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 07, 86 | 080 (network_specific) | per leg | 422 | 0 |
| BR1_TS95 | ATL105 MasterCard Fleet Magnetic Stripe Purchase Reversal | valid |  | multi-leg | 8 | 02, 90 | 080 (network_specific) | per leg | 392 | 0 |
| BR1_TS96 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 91 | 080 (network_specific) | per leg | 322 | 0 |
| BR1_TS97 | ATL105 MasterCard Fleet Manual / Keyed Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 01 | 080 (network_specific) | per leg | 359 | 0 |
| BR1_TS98 | ATL105 MasterCard Fleet Chip Fallback Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 79, 80 | 080 (network_specific) | per leg | 329 | 0 |
| BR1_TS99 | ATL105 MasterCard Fleet Credential on File Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 10 | 080 (network_specific) | per leg | 359 | 0 |
| BR1_TS100 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Purchase Reversal | unconfirmed | yes | multi-leg | 8 | 82 | 080 (network_specific) | per leg | 322 | 0 |
| BR1_TS101 | ATL105 MasterCard Fleet EMV Contact Chip Time-out Reversal | unconfirmed | yes | multi-leg | Z | 05, 95 | 080 (network_specific) | per leg | 444 | 0 |
| BR1_TS102 | ATL105 MasterCard Fleet EMV Contactless Chip Time-out Reversal | unconfirmed | yes | multi-leg | Z | 07, 86 | 080 (network_specific) | per leg | 439 | 0 |
| BR1_TS103 | ATL105 MasterCard Fleet Magnetic Stripe Time-out Reversal | valid |  | multi-leg | Z | 02, 90 | 080 (network_specific) | per leg | 409 | 0 |
| BR1_TS104 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Time-out Reversal | unconfirmed | yes | multi-leg | Z | 91 | 080 (network_specific) | per leg | 339 | 0 |
| BR1_TS105 | ATL105 MasterCard Fleet Manual / Keyed Time-out Reversal | unconfirmed | yes | multi-leg | Z | 01 | 080 (network_specific) | per leg | 376 | 0 |
| BR1_TS106 | ATL105 MasterCard Fleet Chip Fallback Time-out Reversal | unconfirmed | yes | multi-leg | Z | 79, 80 | 080 (network_specific) | per leg | 346 | 0 |
| BR1_TS107 | ATL105 MasterCard Fleet Credential on File Time-out Reversal | unconfirmed | yes | multi-leg | Z | 10 | 080 (network_specific) | per leg | 376 | 0 |
| BR1_TS108 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Time-out Reversal | unconfirmed | yes | multi-leg | Z | 82 | 080 (network_specific) | per leg | 339 | 0 |

### Multi-leg flows (follow-on scenarios and pre-authorized variants)

**BR1_TS1 ATL105 MasterCard Credit EMV Contact Chip Purchase/Capture** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS2 ATL105 MasterCard Credit EMV Contactless Chip Purchase/Capture** (valid)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS3 ATL105 MasterCard Credit Magnetic Stripe Purchase/Capture** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS4 ATL105 MasterCard Credit Contactless Magnetic Stripe Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS5 ATL105 MasterCard Credit Manual / Keyed Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS6 ATL105 MasterCard Credit Chip Fallback Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS7 ATL105 MasterCard Credit Credential on File Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 10, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS8 ATL105 MasterCard Credit Contactless Mobile Commerce Device Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS29 ATL105 MasterCard Credit Magnetic Stripe Void of a Merchandise Return** (unconfirmed)

- *follow_on* - flow `FLOW-RETURN-VOID` - variant status unconfirmed
  - Leg 1 original: Merchandise Return (Transaction Type 7), entry mode 02/90, message Financial Transaction Request
  - Leg 2 follow_on: Void of a Merchandise Return (Transaction Type U), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS30 ATL105 MasterCard Credit Contactless Magnetic Stripe Void of a Merchandise Return** (unconfirmed)

- *follow_on* - flow `FLOW-RETURN-VOID` - variant status unconfirmed
  - Leg 1 original: Merchandise Return (Transaction Type 7), entry mode 91, message Financial Transaction Request
  - Leg 2 follow_on: Void of a Merchandise Return (Transaction Type U), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS31 ATL105 MasterCard Credit Manual / Keyed Void of a Merchandise Return** (unconfirmed)

- *follow_on* - flow `FLOW-RETURN-VOID` - variant status unconfirmed
  - Leg 1 original: Merchandise Return (Transaction Type 7), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Void of a Merchandise Return (Transaction Type U), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS32 ATL105 MasterCard Credit Chip Fallback Void of a Merchandise Return** (unconfirmed)

- *follow_on* - flow `FLOW-RETURN-VOID` - variant status unconfirmed
  - Leg 1 original: Merchandise Return (Transaction Type 7), entry mode 79/80, message Financial Transaction Request
  - Leg 2 follow_on: Void of a Merchandise Return (Transaction Type U), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS33 ATL105 MasterCard Credit Credential on File Void of a Merchandise Return** (unconfirmed)

- *follow_on* - flow `FLOW-RETURN-VOID` - variant status unconfirmed
  - Leg 1 original: Merchandise Return (Transaction Type 7), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Void of a Merchandise Return (Transaction Type U), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS34 ATL105 MasterCard Credit Contactless Mobile Commerce Device Void of a Merchandise Return** (unconfirmed)

- *follow_on* - flow `FLOW-RETURN-VOID` - variant status unconfirmed
  - Leg 1 original: Merchandise Return (Transaction Type 7), entry mode 82, message Financial Transaction Request
  - Leg 2 follow_on: Void of a Merchandise Return (Transaction Type U), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS35 ATL105 MasterCard Credit EMV Contact Chip Purchase Reversal** (valid)

- *follow_on* - flow `FLOW-EMV-SALE-VOID` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS36 ATL105 MasterCard Credit EMV Contactless Chip Purchase Reversal** (valid)

- *follow_on* - flow `FLOW-EMV-SALE-VOID` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS37 ATL105 MasterCard Credit Magnetic Stripe Purchase Reversal** (valid)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 02/90, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS38 ATL105 MasterCard Credit Contactless Magnetic Stripe Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS39 ATL105 MasterCard Credit Manual / Keyed Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS40 ATL105 MasterCard Credit Chip Fallback Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS41 ATL105 MasterCard Credit Credential on File Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS42 ATL105 MasterCard Credit Contactless Mobile Commerce Device Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS43 ATL105 MasterCard Credit Manual / Keyed Mail/Phone Reversal** (unconfirmed)

- *follow_on* - flow `none` - variant status unconfirmed
  - Leg 1 original: Mail/Phone Purchase (Transaction Type 6), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Mail/Phone Reversal (Transaction Type C), entry mode 01, message unknown
  - GAP flow_gap: no flow template for MOTO_REVERSAL (non_emv) in config/business_model.json; leg 2 message and carry links are not known
- *follow_on* - flow `none` - variant status unconfirmed
  - Leg 1 original: Mail/Phone Authorization Only (Transaction Type B), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Mail/Phone Reversal (Transaction Type C), entry mode 01, message unknown
  - GAP flow_gap: no flow template for MOTO_REVERSAL (non_emv) in config/business_model.json; leg 2 message and carry links are not known

**BR1_TS44 ATL105 MasterCard Credit Credential on File Mail/Phone Reversal** (unconfirmed)

- *follow_on* - flow `none` - variant status unconfirmed
  - Leg 1 original: Mail/Phone Purchase (Transaction Type 6), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Mail/Phone Reversal (Transaction Type C), entry mode 10, message unknown
  - GAP flow_gap: no flow template for MOTO_REVERSAL (non_emv) in config/business_model.json; leg 2 message and carry links are not known
- *follow_on* - flow `none` - variant status unconfirmed
  - Leg 1 original: Mail/Phone Authorization Only (Transaction Type B), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Mail/Phone Reversal (Transaction Type C), entry mode 10, message unknown
  - GAP flow_gap: no flow template for MOTO_REVERSAL (non_emv) in config/business_model.json; leg 2 message and carry links are not known

**BR1_TS45 ATL105 MasterCard Credit EMV Contact Chip Time-out Reversal** (valid)

- *follow_on* - flow `FLOW-EMV-TIMEOUT-REVERSAL` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x EMV Contact Chip (excluded)

**BR1_TS46 ATL105 MasterCard Credit EMV Contactless Chip Time-out Reversal** (valid)

- *follow_on* - flow `FLOW-EMV-TIMEOUT-REVERSAL` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x EMV Contactless Chip (excluded)

**BR1_TS47 ATL105 MasterCard Credit Magnetic Stripe Time-out Reversal** (valid)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 02/90, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Magnetic Stripe (excluded)

**BR1_TS48 ATL105 MasterCard Credit Contactless Magnetic Stripe Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Contactless Magnetic Stripe (excluded)

**BR1_TS49 ATL105 MasterCard Credit Manual / Keyed Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Mail/Phone Purchase (Transaction Type 6), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
  - GAP FLOW_CODE_MISMATCH: FLOW-TIMEOUT-REVERSAL step 1 fixes Transaction Type '0', not a code of Mail/Phone Purchase

**BR1_TS50 ATL105 MasterCard Credit Chip Fallback Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Chip Fallback (excluded)

**BR1_TS51 ATL105 MasterCard Credit Credential on File Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Mail/Phone Purchase (Transaction Type 6), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
  - GAP FLOW_CODE_MISMATCH: FLOW-TIMEOUT-REVERSAL step 1 fixes Transaction Type '0', not a code of Mail/Phone Purchase

**BR1_TS52 ATL105 MasterCard Credit Contactless Mobile Commerce Device Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Contactless Mobile Commerce Device (excluded)

**BR1_TS53 ATL105 MasterCard Credit EMV Contact Chip Cancellation** (valid)

- *follow_on* - flow `FLOW-EMV-AUTH-CANCELLATION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS54 ATL105 MasterCard Credit EMV Contactless Chip Cancellation** (valid)

- *follow_on* - flow `FLOW-EMV-AUTH-CANCELLATION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS55 ATL105 MasterCard Credit Magnetic Stripe Cancellation** (valid)

- *follow_on* - flow `FLOW-AUTH-CANCELLATION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS56 ATL105 MasterCard Credit Contactless Magnetic Stripe Cancellation** (unconfirmed)

- *follow_on* - flow `FLOW-AUTH-CANCELLATION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS57 ATL105 MasterCard Credit Manual / Keyed Cancellation** (unconfirmed)

- *follow_on* - flow `FLOW-AUTH-CANCELLATION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS58 ATL105 MasterCard Credit Chip Fallback Cancellation** (unconfirmed)

- *follow_on* - flow `FLOW-AUTH-CANCELLATION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS59 ATL105 MasterCard Credit Credential on File Cancellation** (unconfirmed)

- *follow_on* - flow `FLOW-AUTH-CANCELLATION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS60 ATL105 MasterCard Credit Contactless Mobile Commerce Device Cancellation** (unconfirmed)

- *follow_on* - flow `FLOW-AUTH-CANCELLATION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 follow_on: Cancellation (Transaction Type S), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-97-7, p.83)

**BR1_TS69 ATL105 MasterCard Fleet EMV Contact Chip Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS70 ATL105 MasterCard Fleet EMV Contactless Chip Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-EMV-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS71 ATL105 MasterCard Fleet Magnetic Stripe Purchase/Capture** (valid)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status valid
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 02/90, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS72 ATL105 MasterCard Fleet Contactless Magnetic Stripe Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 91, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS73 ATL105 MasterCard Fleet Manual / Keyed Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 01, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS74 ATL105 MasterCard Fleet Chip Fallback Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 79/80, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS75 ATL105 MasterCard Fleet Credential on File Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 10, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS76 ATL105 MasterCard Fleet Contactless Mobile Commerce Device Purchase/Capture** (unconfirmed)

- *preauthorized* - flow `FLOW-AUTH-COMPLETION` - variant status unconfirmed
  - Leg 1 original: Authorization Only (Transaction Type 3/5), entry mode 82, message Financial Transaction Request
  - Leg 2 completion: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-1)

**BR1_TS93 ATL105 MasterCard Fleet EMV Contact Chip Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-EMV-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS94 ATL105 MasterCard Fleet EMV Contactless Chip Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-EMV-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS95 ATL105 MasterCard Fleet Magnetic Stripe Purchase Reversal** (valid)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 02/90, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS96 ATL105 MasterCard Fleet Contactless Magnetic Stripe Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS97 ATL105 MasterCard Fleet Manual / Keyed Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS98 ATL105 MasterCard Fleet Chip Fallback Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS99 ATL105 MasterCard Fleet Credential on File Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS100 ATL105 MasterCard Fleet Contactless Mobile Commerce Device Purchase Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-SALE-VOID` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request
  - Leg 2 follow_on: Purchase Reversal (Transaction Type 8), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-402-3)

**BR1_TS101 ATL105 MasterCard Fleet EMV Contact Chip Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-EMV-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 05/95, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 05/95, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x EMV Contact Chip (excluded)

**BR1_TS102 ATL105 MasterCard Fleet EMV Contactless Chip Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-EMV-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 07/86, message EMV Financial Transaction Request, segment(s) 130
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 07/86, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x EMV Contactless Chip (excluded)

**BR1_TS103 ATL105 MasterCard Fleet Magnetic Stripe Time-out Reversal** (valid)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status valid
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 02/90, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 02/90, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Magnetic Stripe (excluded)

**BR1_TS104 ATL105 MasterCard Fleet Contactless Magnetic Stripe Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 91, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 91, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Contactless Magnetic Stripe (excluded)

**BR1_TS105 ATL105 MasterCard Fleet Manual / Keyed Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 01, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 01, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Manual / Keyed (excluded)

**BR1_TS106 ATL105 MasterCard Fleet Chip Fallback Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 79/80, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 79/80, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Chip Fallback (excluded)

**BR1_TS107 ATL105 MasterCard Fleet Credential on File Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 10, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 10, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Credential on File (excluded)

**BR1_TS108 ATL105 MasterCard Fleet Contactless Mobile Commerce Device Time-out Reversal** (unconfirmed)

- *follow_on* - flow `FLOW-TIMEOUT-REVERSAL` - variant status unconfirmed
  - Leg 1 original: Purchase/Capture (Transaction Type 0/4), entry mode 82, message Financial Transaction Request
  - Leg 2 follow_on: Time-out Reversal (Transaction Type Z), entry mode 82, message Financial Transaction Request; carries Sequence Number (BR-38-9); carries Terminal Identifier (BR-38-4, p.38)
- Originals not available: Mail/Phone Purchase x Contactless Mobile Commerce Device (excluded)

### Scenarios requiring SME confirmation

The spec neither states nor rules out these pairs; the scenario is kept and flagged.

| ID | Scenario | Open pair(s) |
|---|---|---|
| BR1_TS4 | ATL105 MasterCard Credit Contactless Magnetic Stripe Purchase/Capture | txn_category-entry_mode |
| BR1_TS5 | ATL105 MasterCard Credit Manual / Keyed Purchase/Capture | txn_category-entry_mode |
| BR1_TS6 | ATL105 MasterCard Credit Chip Fallback Purchase/Capture | txn_category-entry_mode |
| BR1_TS7 | ATL105 MasterCard Credit Credential on File Purchase/Capture | txn_category-entry_mode |
| BR1_TS8 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Purchase/Capture | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS9 | ATL105 MasterCard Credit Manual / Keyed Mail/Phone Purchase | txn_category-entry_mode |
| BR1_TS10 | ATL105 MasterCard Credit Credential on File Mail/Phone Purchase | txn_category-entry_mode |
| BR1_TS14 | ATL105 MasterCard Credit Contactless Magnetic Stripe Authorization Only | txn_category-entry_mode |
| BR1_TS15 | ATL105 MasterCard Credit Manual / Keyed Authorization Only | txn_category-entry_mode |
| BR1_TS16 | ATL105 MasterCard Credit Chip Fallback Authorization Only | txn_category-entry_mode |
| BR1_TS17 | ATL105 MasterCard Credit Credential on File Authorization Only | txn_category-entry_mode |
| BR1_TS18 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Authorization Only | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS19 | ATL105 MasterCard Credit Manual / Keyed Mail/Phone Authorization Only | txn_category-entry_mode |
| BR1_TS20 | ATL105 MasterCard Credit Credential on File Mail/Phone Authorization Only | txn_category-entry_mode |
| BR1_TS23 | ATL105 MasterCard Credit Magnetic Stripe Merchandise Return | txn_category-entry_mode |
| BR1_TS24 | ATL105 MasterCard Credit Contactless Magnetic Stripe Merchandise Return | txn_category-entry_mode |
| BR1_TS25 | ATL105 MasterCard Credit Manual / Keyed Merchandise Return | txn_category-entry_mode |
| BR1_TS26 | ATL105 MasterCard Credit Chip Fallback Merchandise Return | txn_category-entry_mode |
| BR1_TS27 | ATL105 MasterCard Credit Credential on File Merchandise Return | txn_category-entry_mode |
| BR1_TS28 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Merchandise Return | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS29 | ATL105 MasterCard Credit Magnetic Stripe Void of a Merchandise Return | txn_category-entry_mode |
| BR1_TS30 | ATL105 MasterCard Credit Contactless Magnetic Stripe Void of a Merchandise Return | txn_category-entry_mode |
| BR1_TS31 | ATL105 MasterCard Credit Manual / Keyed Void of a Merchandise Return | txn_category-entry_mode |
| BR1_TS32 | ATL105 MasterCard Credit Chip Fallback Void of a Merchandise Return | txn_category-entry_mode |
| BR1_TS33 | ATL105 MasterCard Credit Credential on File Void of a Merchandise Return | txn_category-entry_mode |
| BR1_TS34 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Void of a Merchandise Return | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS38 | ATL105 MasterCard Credit Contactless Magnetic Stripe Purchase Reversal | txn_category-entry_mode |
| BR1_TS39 | ATL105 MasterCard Credit Manual / Keyed Purchase Reversal | txn_category-entry_mode |
| BR1_TS40 | ATL105 MasterCard Credit Chip Fallback Purchase Reversal | txn_category-entry_mode |
| BR1_TS41 | ATL105 MasterCard Credit Credential on File Purchase Reversal | txn_category-entry_mode |
| BR1_TS42 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Purchase Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS43 | ATL105 MasterCard Credit Manual / Keyed Mail/Phone Reversal | txn_category-entry_mode |
| BR1_TS44 | ATL105 MasterCard Credit Credential on File Mail/Phone Reversal | txn_category-entry_mode |
| BR1_TS48 | ATL105 MasterCard Credit Contactless Magnetic Stripe Time-out Reversal | txn_category-entry_mode |
| BR1_TS49 | ATL105 MasterCard Credit Manual / Keyed Time-out Reversal | txn_category-entry_mode |
| BR1_TS50 | ATL105 MasterCard Credit Chip Fallback Time-out Reversal | txn_category-entry_mode |
| BR1_TS51 | ATL105 MasterCard Credit Credential on File Time-out Reversal | txn_category-entry_mode |
| BR1_TS52 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Time-out Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS56 | ATL105 MasterCard Credit Contactless Magnetic Stripe Cancellation | txn_category-entry_mode |
| BR1_TS57 | ATL105 MasterCard Credit Manual / Keyed Cancellation | txn_category-entry_mode |
| BR1_TS58 | ATL105 MasterCard Credit Chip Fallback Cancellation | txn_category-entry_mode |
| BR1_TS59 | ATL105 MasterCard Credit Credential on File Cancellation | txn_category-entry_mode |
| BR1_TS60 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Cancellation | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS63 | ATL105 MasterCard Credit Magnetic Stripe Balance Inquiry | txn_category-entry_mode |
| BR1_TS64 | ATL105 MasterCard Credit Contactless Magnetic Stripe Balance Inquiry | txn_category-entry_mode |
| BR1_TS65 | ATL105 MasterCard Credit Manual / Keyed Balance Inquiry | txn_category-entry_mode |
| BR1_TS66 | ATL105 MasterCard Credit Chip Fallback Balance Inquiry | txn_category-entry_mode |
| BR1_TS67 | ATL105 MasterCard Credit Credential on File Balance Inquiry | txn_category-entry_mode |
| BR1_TS68 | ATL105 MasterCard Credit Contactless Mobile Commerce Device Balance Inquiry | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS69 | ATL105 MasterCard Fleet EMV Contact Chip Purchase/Capture | program-entry_mode, network-entry_mode |
| BR1_TS70 | ATL105 MasterCard Fleet EMV Contactless Chip Purchase/Capture | program-entry_mode, network-entry_mode |
| BR1_TS72 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Purchase/Capture | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS73 | ATL105 MasterCard Fleet Manual / Keyed Purchase/Capture | txn_category-entry_mode |
| BR1_TS74 | ATL105 MasterCard Fleet Chip Fallback Purchase/Capture | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS75 | ATL105 MasterCard Fleet Credential on File Purchase/Capture | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS76 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Purchase/Capture | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS77 | ATL105 MasterCard Fleet EMV Contact Chip Authorization Only | program-entry_mode, network-entry_mode |
| BR1_TS78 | ATL105 MasterCard Fleet EMV Contactless Chip Authorization Only | program-entry_mode, network-entry_mode |
| BR1_TS80 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Authorization Only | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS81 | ATL105 MasterCard Fleet Manual / Keyed Authorization Only | txn_category-entry_mode |
| BR1_TS82 | ATL105 MasterCard Fleet Chip Fallback Authorization Only | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS83 | ATL105 MasterCard Fleet Credential on File Authorization Only | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS84 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Authorization Only | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS85 | ATL105 MasterCard Fleet EMV Contact Chip Merchandise Return | program-entry_mode, network-entry_mode |
| BR1_TS86 | ATL105 MasterCard Fleet EMV Contactless Chip Merchandise Return | program-entry_mode, network-entry_mode |
| BR1_TS87 | ATL105 MasterCard Fleet Magnetic Stripe Merchandise Return | txn_category-entry_mode |
| BR1_TS88 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Merchandise Return | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS89 | ATL105 MasterCard Fleet Manual / Keyed Merchandise Return | txn_category-entry_mode |
| BR1_TS90 | ATL105 MasterCard Fleet Chip Fallback Merchandise Return | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS91 | ATL105 MasterCard Fleet Credential on File Merchandise Return | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS92 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Merchandise Return | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS93 | ATL105 MasterCard Fleet EMV Contact Chip Purchase Reversal | program-entry_mode, network-entry_mode |
| BR1_TS94 | ATL105 MasterCard Fleet EMV Contactless Chip Purchase Reversal | program-entry_mode, network-entry_mode |
| BR1_TS96 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Purchase Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS97 | ATL105 MasterCard Fleet Manual / Keyed Purchase Reversal | txn_category-entry_mode |
| BR1_TS98 | ATL105 MasterCard Fleet Chip Fallback Purchase Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS99 | ATL105 MasterCard Fleet Credential on File Purchase Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS100 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Purchase Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS101 | ATL105 MasterCard Fleet EMV Contact Chip Time-out Reversal | program-entry_mode, network-entry_mode |
| BR1_TS102 | ATL105 MasterCard Fleet EMV Contactless Chip Time-out Reversal | program-entry_mode, network-entry_mode |
| BR1_TS104 | ATL105 MasterCard Fleet Contactless Magnetic Stripe Time-out Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS105 | ATL105 MasterCard Fleet Manual / Keyed Time-out Reversal | txn_category-entry_mode |
| BR1_TS106 | ATL105 MasterCard Fleet Chip Fallback Time-out Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS107 | ATL105 MasterCard Fleet Credential on File Time-out Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |
| BR1_TS108 | ATL105 MasterCard Fleet Contactless Mobile Commerce Device Time-out Reversal | program-entry_mode, network-entry_mode, txn_category-entry_mode |

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
| ATL105 MasterCard Credit EMV Contact Chip Void of a Merchandise Return | Void of a Merchandise Return x EMV Contact Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Credit EMV Contactless Chip Void of a Merchandise Return | Void of a Merchandise Return x EMV Contactless Chip | p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
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
| ATL105 MasterCard Fleet EMV Contact Chip Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return; Void of a Merchandise Return x EMV Contact Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
| ATL105 MasterCard Fleet EMV Contactless Chip Void of a Merchandise Return | MasterCard (Fleet) x Void of a Merchandise Return; Void of a Merchandise Return x EMV Contactless Chip | p.51 "Not in the list: The following transactions are supported for the MasterCard fleet card:"; p.161 "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" |
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

-  p.511 (Appendix C): "03 MasterCard - Authorizer Type: Credit"
-  p.522 (Appendix E): "080 MasterCard fleet - Card Type Description: FLEET"
- SRC-ATL105-PDF-001 p.81 (anchored:A-SALE-PREAUTH-TRACK): "If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent"
- SRC-ATL105-PDF-001 p.81 (anchored:A-CREDIT-MANUAL): "Manual entry is allowed on a credit card transaction"
- SRC-ATL105-PDF-001 p.81 (anchored:A-CREDIT-TRACK): "The default track that is sent to the host should be Track 2 if the device supports Track 2"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Authorization Only Reversal"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Authorization Only"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Purchase"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Mail/Phone Reversal"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Purchase Reversal"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Purchase/Capture"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Time-out Reversal"
- SRC-ATL105-PDF-001 p.83 (10.1.2 Supported Transaction Types): "The following point-of-sale transactions are supported for credit card processing: ... Void of Merchandise Return"
- SRC-ATL105-PDF-001 p.86 (anchored:A-CREDIT-RFID): "capture and transmit full, unaltered Track 2 data from a card without requiring the card to have contact with the point-of-sale device"
- SRC-ATL105-PDF-001 p.161 (10.14.2.3 Supported Cards): "The following credit cards are supported for EMV processing: ... MasterCard"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Authorization Only"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Balance Inquiry"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Cancellation"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Merchandise Return"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Purchase"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Purchase Reversal"
- SRC-ATL105-PDF-001 p.161 (10.14.2.4 Supported Transaction Types): "The following point-of-sale transactions are supported for EMV card processing: ... Time-out Reversal (TOR)"
- SRC-ATL105-PDF-001 p.162 (anchored:A-CREDIT-FALLBACK): "If a chip card is presented at a chip capable terminal and a chip transaction cannot be performed, then the merchant may process the chip card as a magnetic stripe read"
- SRC-ATL105-PDF-001 p.289 (anchored:SME-MCFLEET-EMV): "Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV"
- SRC-ATL105-PDF-001 p.638 (anchored:APPJ-FN2-COF-CREDIT): "Entry Mode 10 is applicable to Visa Credit, MasterCard Credit, Discover Credit, and Amex transactions"
- SRC-ATL105-PDF-001 p.638 (derived:follow_on_entry): "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction (original: AUTH_ONLY x MAGSTRIPE is supported)"
- SRC-ATL105-PDF-001 p.638 (derived:follow_on_entry): "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction (original: PURCHASE x MAGSTRIPE is supported)"
- SRC-ATL105-PDF-001 p.638 (derived:follow_on_entry): "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction (original: PURCHASE, MOTO_PURCHASE x MAGSTRIPE is supported)"
- SRC-BUYPASS-PIPS-PDF-001 p.31 (derived:tor_follows_original): "Please refer to the current version of the appropriate message format specifications for rules and definitions for POS time-out reversal processing / ATL105 p.39: "A TOR is generated when any of the following transactions is unsuccessful" (original PURCHASE is supported)"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Authorization Only Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Purchase/Capture Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Return/Refund Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.51 (7.1.1. Supported Transactions): "The following transactions are supported for the MasterCard fleet card: ... POS Reversal/Void Transaction"
- SRC-BUYPASS-PIPS-PDF-001 p.52 (anchored:A-PIPS-MCFLEET-MANUAL): "Manual entry is allowed on MasterCard fleet card transactions"
- SRC-BUYPASS-PIPS-PDF-001 p.53 (anchored:A-PIPS-MCFLEET-TRACK): "The default track that is sent to BUYPASS on an original transaction should be Track 2"

