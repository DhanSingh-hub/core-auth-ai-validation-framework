# ATL105 Field-Level Test Data Validation - TC-002682

| Item | Value |
|---|---|
| Test case | TC-002682 (BR1_TS7_TC13) |
| Name | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Credential on File Purchase → Settlement (read and cut 222222) |
| Scenario | TS-ATL105-CREDIT-AMERICAN_EXPRESS-PURCHASE-CREDENTIAL_ON_FILE |
| Template / type | T8-PUR-SETTLE / Settlement |
| Objective | Purchase -> Settlement (read and cut 222222) |
| Transaction type | Financial Transaction Request → Totals Request |
| TC status | requires_review (catalog: sme_required) |
| Open items | ["Variable-cut test merchant.","Totals bucket rules are stated only in the FSA/HRA section (p.89)."] |
| Test data file(s) | TD-TC-002682.step1.purchase.json, TD-TC-002682.step2.totals.json |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-03T16:42:27.9274413 |
| Overall verdict | **FAIL** |
| Counts | PASS 124 / FAIL 1 / REVIEW 4 / N/A 10 (individual field checks) |

## What is wrong in this test case and how to fix it

**1 defect(s) to fix.** Each one shows where it is in the test data, what the AI generated, why that is wrong, and the value that should be there.

### Defect 1 – Totals Card Label is 2 characters instead of 4

| | |
|---|---|
| **Where** | `TD-TC-002682.step2.totals.json` line 20 → `Totals Data Segment` → `CardLabel` |
| **What the AI generated** | `TE` |
| **What is wrong** | The Card Label must always be 4 characters. `TE` is the right label (American Express bucket = TE, Mastercard/Visa = CC) but has only 2 characters, so the count and amount fields after it are shifted. |
| **What it should be** | `TE  ` (the label followed by spaces up to 4 characters) |
| **Spec reference** | ATL105 Ch. 13 Element 13 (p.365): fixed length of four alphanumeric characters, left-aligned, space-filled |

### Items to confirm (not defects)

These cannot be decided from the specification alone. They do not make the test data wrong.

| Item | Why it is open | Who confirms |
|---|---|---|
| Terminal ID / merchant number | The value is a placeholder. BUYPASS assigns the real device type, merchant and device number. | Client merchant list |
| Totals password empty | Element 65 says a password is required for totals; the layout marks it optional. | Merchant profile / SME |
| Hardware/software/firmware versions | The format is right, but the values are synthesized. They must be the certified device's real versions. | Device team |
| Totals segment length | Matches only with a trailing separator and a 4-character card label. | SME |

---

# Technical evidence

The tables below are the validator's raw checks behind the explanations above.

## Failing checks (FAIL)

| # | Area | Field | Element | Value | Rule | Finding | Spec source |
|---|---|---|---|---|---|---|---|
| 1 | Seg 105 | CardLabel (width) | 13 | `'TE'` | SEG105-E13-WIDTH | AN, FIXED length 4, left-aligned and space-filled; 'TE' is 2 byte(s) - expected 'TE  '. | 12.6 F15; 13 E13 (p.365) |

## Review checks (REVIEW)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 105 | Password | 65 | `(empty)` | SEG105-E65 | **REVIEW** | Layout marks Password 'C', but Element 65 states 'Password required for totals request' (matched against the merchant profile; default 123456). Confirm whether the merchant requires it. | 12.6 F7; 13 E65 |
| Seg 105 | Hardware/Software/Firmware values | 43/96/39 | `SS16YWLW / XCAVI8UJ / PMHA8LUL` | SEG105-DEVICE-VERSIONS | **REVIEW** | Format is checked; the values must be the certified device's real versions (synthesized here). | 13 E39/E43/E96 |
| Seg 105 | SegmentLength (value) | 84 | `108 (computed 105; with 4-byte Card Label 107)` | E84-SEG105-LENGTH | **REVIEW** | Declared length matches only with a trailing Field Separator after Field 17 and Card Label padded to 4 bytes; 12.6 states FS after Fields 1-14 only - confirm whether the segment ends with an FS. | 12.6 F2 and layout note |

## Leg 1: `TD-TC-002682.step1.purchase.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'Financial Transaction Request' (role purchase, Transaction Type '0') => physical message 'Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55-FINANCIAL-REQUEST | **PASS** | Fixed value 'ATL105' (AN6). | ATL105 11.8.1 F1; 13 E55 |
| Data Section 1 | NumberofSegments | 63 | `02` | E63-EMV-FINANCIAL-REQUEST-COUNT | **PASS** | N up to 2 digits; must equal segments present (2: [100, 111]). | ATL105 11.8.1 F2; 13 E63 |
| Data Section 1 | Section 3 slots | - | `1` | 11.1-SECTION3 | **PASS** | Financial Transaction Request Data Section 3 occupies Field Nos. 4-9 (0..6 segments). | ATL105 11.1 / 11.8.1 p.11-43 |
| Segment presence | Standard Message Data Segment (100) | - | `present` | SEG-100-PRESENCE | **PASS** | Required in Data Section 2 of every Financial Transaction Request. TC decision: REQUIRED. | ATL105 11.1 / 11.8.1 p.11-42; 12.1 |
| Segment presence | EMV Request Data Segment (130) | - | `absent` | SEG130-R-017/R-001 | **NA** | POS entry mode '10' (Credential on file) is not a chip read; no EMV data applies. | ATL105 10.14.2.4; 12.20; 13 E5 |
| Segment presence | Variable Information Data Segment (111) | - | `present` | SEG-111-PRESENCE | **PASS** | Table 031 must be populated for all transactions (BR-569-1). TC decision: REQUIRED_BY_CONDITION. | ATL105 App. I-20 (p.569) |
| Seg 100 | Field layout (17 fields) | - | `17 keys` | LAYOUT-Seg100 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 100 | SegmentType | 85 | `100` | E85-SEG100-TYPE | **PASS** | N3, fixed value 100. | 12.1 F1 |
| Seg 100 | InformationByte | 44 | `0` | SEG100-E44 | **PASS** | AN1: '?' download, '0' single-message, '1' multimessage. | 13 E44 (p.379) |
| Seg 100 | TerminalIdentifier | 102 | `JX72948216050257001` | SEG100-E102-FORMAT | **PASS** | AN up to 22: Device Type(2)+State Code(2)+Merchant No.(6-15)+Device No.(3) => 13..22 chars. | 13 E102 (p.410-411) |
| Seg 100 | TerminalIdentifier.StateCode | 102 | `72` | APPD-ANSI-STATE | **PASS** | Positions 3-4 must be a valid ANSI state code (Appendix D). | 13 E102; App. D |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | PromptCode | 78 | `0020` | SEG100-E78-FORMAT | **PASS** | AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E). | 12.1 F5; 13 E78 |
| Seg 100 | PromptCode.TransactionType | 78 | `0` | APPG-TXN-TYPE | **PASS** | Appendix G: POS Purchase/Capture; TC leg (role purchase) requires '0'. | App. G (p.545) |
| Seg 100 | PromptCode.CardType | 78 | `020` | APPE-CARD-TYPE | **PASS** | Appendix E code 020 = Credit (AMEX credit program). | App. E-4 |
| Seg 100 | AccountNumber | 2 | `340207205306913` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `340207205306913` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `340207` | PAN-IIN-AMEX | **PASS** | American Express IIN 34 / 37, PAN length 15 (TC network AMERICAN_EXPRESS). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `0831` | SEG100-R-042 | **PASS** | AN up to 51; manually entered card => expiration date MMYY. | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `0831` | E12-KEYED-EXPIRY-MMYY | **PASS** | Keyed entry (POS entry 10): expiry is MMYY (13 E12 'manually entered ... MMYY format'); month '08' must be 01-12 (valid, not expired as of 2611). | 13 E12 (p.13-32) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `1500` | SEG100-E58-FORMAT | **PASS** | N, up to 8 digits for AMEX (8 only for American Express), 2 assumed decimals, > 0. | 13 E58 (p.384) |
| Seg 100 | SequenceNumber | 86 | `026821` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `` | BR-368-2 | **PASS** | Must be empty on a new Purchase request (an Approval Number marks it preauthorized). | 13 E5 (p.368) |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (format) | 84 | `077` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `077 (computed 77)` | E84-SEG100-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: sum of transmitted fields (17) + one Field Separator after each. | 12.1 F2; 11.8.1 max 619 |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `101` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `101` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '10' = Credential on file; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `10` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '10' (Credential on file) for entry_mode CREDENTIAL_ON_FILE. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `17` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `026` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `026 (computed 26)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Entry mode | Table 005 vs Element 12 content | 113/12 | `101 / 0831` | APPJ-KEYED-NO-TRACK | **PASS** | PAN entry '10' (Credential on file) means the card was not read; Element 12 must carry the manually entered expiry (MMYY), not Track 2 data. | App. J-1 (p.631); 13 E12 (p.13-32) |
| Message | Total message length | - | `113` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `077` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '077' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `JX72948216050257001 \| TC: JX72948216050257001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [flow_fixed_transaction_code] | - | `0020 \| TC: 0020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `340207205306913 \| TC: 340207205306913` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_expiry_date] | - | `0831 \| TC: 0831` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [scenario_parameter_literal] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [qe_example_value] | - | `026821 \| TC: 026821` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [test_data_config_value] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `101 \| TC: 101` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Segment Type | - | `111 \| TC: 111` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Fleet Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Product Code Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EBT Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Purchase Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Loyalty Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Check Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | ECA/ TeleCheck Data Segment Format ® | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | SKU Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | NFC Payment Tokenization Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Moneris Data Segment (Request) | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Tax by Product Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Enhanced Fleet Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Request InComm OTC Market Basket Data | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Network Token Data Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EV Charging Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |

## Leg 2: `TD-TC-002682.step2.totals.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `Totals Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'Totals Request' (role totals, Transaction Type '') => physical message 'Totals Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55 | **PASS** | Fixed value 'ATL105'. | 11.4.1.1 F1 |
| Data Section 1 | NumberofSegments | 63 | `01` | E63-TOTALS-REQUEST-COUNT | **PASS** | Fixed value 01 (Totals Request carries only Segment 105, no Data Section 2). | 11.4.1.1 F2; 13 E63 |
| Seg 105 | SegmentType | 85 | `105` | E85-SEG105-TYPE | **PASS** | N3, fixed value 105. | 12.6 F1 |
| Seg 105 | InformationByte | 44 | `0` | E44-TOTALS-REQUEST | **PASS** | 0 single-message, 1 multimessage. | 12.6 F3; 13 E44 |
| Seg 105 | TerminalIdentifier | 102 | `JX72948216050257001` | SEG105-E102 | **PASS** | AN 13-22 (Device Type + State Code + Merchant No. + Device No.). | 12.6 F4; 13 E102 |
| Seg 105 | PromptCode | 78 | `990` | E78-TOTALS-REQUEST | **PASS** | Fixed value 990. | 12.6 F5 |
| Seg 105 | EmployeeNumber | 32 | `(empty)` | SEG105-E32 | **NA** | Conditional; not populated (13 E32: use 1111 if no employee numbers are assigned). | 12.6 F6; 13 E32 |
| Seg 105 | Password | 65 | `(empty)` | SEG105-E65 | **REVIEW** | Layout marks Password 'C', but Element 65 states 'Password required for totals request' (matched against the merchant profile; default 123456). Confirm whether the merchant requires it. | 12.6 F7; 13 E65 |
| Seg 105 | TotalsDate | 105 | `222222` | E105-TOTALS-REQUEST | **PASS** | N6: MMDDYY, 111111, 222222 (read and cut), 333333/444444/555555 (most recent/2nd/3rd date with activity), 999999. | 12.6 F8; 13 E105 |
| Seg 105 | TotalsDate vs TC objective | 105 | `222222 vs 222222` | TC-OBJECTIVE-TOTALS-DATE | **PASS** | TC objective 'Purchase -> Settlement (read and cut 222222)' names totals code 222222. | TC; 13 E105 |
| Seg 105 | HardwareVersion | 43 | `SS16YWLW` | E43-TOTALS-REQUEST | **PASS** | AN, fixed 4 or 8. | 12.6 F9; 13 E43 |
| Seg 105 | SoftwareVersion | 96 | `XCAVI8UJ` | E96-TOTALS-REQUEST | **PASS** | AN, fixed 8. | 12.6 F10; 13 E96 |
| Seg 105 | FirmwareVersion | 39 | `PMHA8LUL` | E39-TOTALS-REQUEST | **PASS** | AN, fixed 8. | 12.6 F11; 13 E39 |
| Seg 105 | Hardware/Software/Firmware values | 43/96/39 | `SS16YWLW / XCAVI8UJ / PMHA8LUL` | SEG105-DEVICE-VERSIONS | **REVIEW** | Format is checked; the values must be the certified device's real versions (synthesized here). | 13 E39/E43/E96 |
| Seg 105 | SequenceNumber | 86 | `026822` | E86-TOTALS-REQUEST | **PASS** | N6, 000001-999999. | 12.6 F12; 13 E86 |
| Seg 105 | CurrencyCode | 20 | `840` | SEG105-E20 | **PASS** | N3 per Appendix L (840 = USD). | 12.6 F13; 13 E20; App. L |
| Seg 105 | GrandTotal | 42 | `00001500` | SEG105-E42 | **PASS** | N, fixed 8, two assumed decimals. | 12.6 F14; 13 E42 |
| Seg 105 | CardLabel (value) | 13 | `TE` | SEG105-E13-VALUE | **PASS** | Valid Card Label; MasterCard/Visa bucket = CC, American Express bucket = TE. | 12.6 F15; 13 E13; ATL105 p.69 |
| Seg 105 | CardLabel (width) | 13 | `'TE'` | SEG105-E13-WIDTH | **FAIL** | AN, FIXED length 4, left-aligned and space-filled; 'TE' is 2 byte(s) - expected 'TE  '. | 12.6 F15; 13 E13 (p.365) |
| Seg 105 | CardTypeTotalCount | 16 | `00001` | SEG105-E16 | **PASS** | N, fixed 5, valid 00001-99999. | 12.6 (Category Total Data Section); 13 E16 |
| Seg 105 | CardTypeTotalAmount | 15 | `00001500` | SEG105-E15 | **PASS** | N, fixed 8, two assumed decimals. | 12.6 F17; 13 E15 |
| Seg 105 | GrandTotal vs sum of category amounts | 42/15 | `00001500 vs 00001500` | SEG105-GRAND-TOTAL | **PASS** | With a single card-type bucket, Grand Total equals its total amount. | 12.6; 13 E42/E15 |
| Seg 105 | SegmentLength (format) | 84 | `108` | E84-SEG105-LENGTH | **PASS** | N3, 001-409. | 12.6 F2 |
| Seg 105 | SegmentLength (value) | 84 | `108 (computed 105; with 4-byte Card Label 107)` | E84-SEG105-LENGTH | **REVIEW** | Declared length matches only with a trailing Field Separator after Field 17 and Card Label padded to 4 bytes; 12.6 states FS after Fields 1-14 only - confirm whether the segment ends with an FS. | 12.6 F2 and layout note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Totals Data Segment | - | `present` | TC-SEGMENT-DECISION | **PASS** | TC sends this segment; TD includes it. | TC request |
| TC<->TD | Totals Data Segment.Segment Type [fixed_value] | - | `105 \| TC: 105` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Terminal Identifier [realistic_terminal_id] | - | `JX72948216050257001 \| TC: JX72948216050257001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Prompt Code [flow_fixed_transaction_code] | - | `990 \| TC: 990` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Employee Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Password [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Totals Date [flow_fixed_transaction_code] | - | `222222 \| TC: 222222` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Hardware Version [format_synthesis] | - | `SS16YWLW \| TC: SS16YWLW` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Software Version [format_synthesis] | - | `XCAVI8UJ \| TC: XCAVI8UJ` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Firmware Version [format_synthesis] | - | `PMHA8LUL \| TC: PMHA8LUL` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Sequence Number [qe_example_value] | - | `026822 \| TC: 026822` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Currency Code [qe_example_value] | - | `840 \| TC: 840` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Grand Total [spec_rule_value] | - | `00001500 \| TC: 00001500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Card Label [spec_rule_value] | - | `TE \| TC: TE` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Card Type Total Count [spec_rule_value] | - | `00001 \| TC: 00001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Totals Data Segment.Card Type Total Amount [spec_rule_value] | - | `00001500 \| TC: 00001500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |

## Test objective and flow checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Objective (T8-PUR-SETTLE) | Test objective | - | `Purchase -> Settlement (read and cut 222222)` | T8-PUR-SETTLE | **NA** | catalog_status=sme_required; status=requires_review; open_items=["Variable-cut test merchant.","Totals bucket rules are stated only in the FSA/HRA section (p.89)."] | TC |
| TC rule_checks (step 1) | LEG-TXN-TYPE Prompt Code | - | `0` | value_slice_in | **PASS** | Expected to hold; allowed [0]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-ENTRY-MODE 005 | - | `10` | table_slice_in | **PASS** | Expected to hold; allowed [10]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 2) | LEG-PROMPT-CODE Prompt Code | - | `990` | value_slice_in | **PASS** | Expected to hold; allowed [990]. | special transaction Prompt Code (p.401) |
| TC rule_checks (step 1) | SEG-111-REQUIRED 111 | - | `[100, 111]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 1) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |
| TC rule_checks (step 2) | TOTALS-CARD-LABEL Card Label | - | `TE` | value_slice_in | **PASS** | Expected to hold; allowed [TE]. | Card Label TE for this card type (p.237) |
| TC link_defs (step 2 <- step 1) | Terminal Identifier carried | - | `JX72948216050257001 / JX72948216050257001` | carry_request_field | **PASS** | Value must be identical to step 1. | p.40: A TOR must be sent from the same device as the original financial transaction request (applied to every follow-on and to the next transaction on the same device). |
| Flow (totals step 2) | Totals vs earlier legs | 16/42 | `count 00001, grand 00001500 \| expected count 1, net 1500` | TOTALS-FLOW-CONSISTENCY | **PASS** | Expected from earlier legs assuming every request is approved (step 1 purchase 1500). Host decisions can change the real totals. | 12.6; 13 E15/E16/E42 |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
