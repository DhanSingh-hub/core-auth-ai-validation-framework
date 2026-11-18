# ATL105 Field-Level Test Data Validation - TC-023074

| Item | Value |
|---|---|
| Test case | TC-023074 (BR1_TS35_TC1) |
| Name | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Credential on File Balance Inquiry → approved |
| Scenario | TS-ATL105-CREDIT-MASTERCARD-BALANCE_INQUIRY-CREDENTIAL_ON_FILE |
| Template / type | G-HP-e1ed5b66dc / Happy path |
| Objective | Happy path: Balance Inquiry approved |
| Transaction type | Financial Transaction Request |
| TC status | pending_sme (catalog: grounded) |
| Open items | ["Does a MasterCard credit issuer return a balance? Approval code for Transaction Type E."] |
| Test data file(s) | TD-TC-023074.json |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-16T17:02:39.4565043 |
| Overall verdict | **PASS_WITH_REVIEW** |
| Counts | PASS 105 / FAIL 0 / REVIEW 6 / N/A 11 (individual field checks) |

## What is wrong in this test case and how to fix it

**No defects.** The generated test data matches the ATL105 specification for every field that can be checked from the spec. Only the confirmation items below remain.

### Items to confirm (not defects)

These cannot be decided from the specification alone. They do not make the test data wrong.

| Item | Why it is open | Who confirms |
|---|---|---|
| Terminal ID / merchant number | The value is a placeholder. BUYPASS assigns the real device type, merchant and device number. | Client merchant list |
| Section[3].VariableInformation | This logical validator has no implemented predicate for the selected table. | SME |
| Section[4].VariableInformation | This logical validator has no implemented predicate for the selected table. | SME |
| Section[5].VariableInformation | This logical validator has no implemented predicate for the selected table. | SME |
| Section[6].VariableInformation | This logical validator has no implemented predicate for the selected table. | SME |
| LEG-NONFUEL-AMOUNT-MAX-LENGTH | Unsupported rule_check op. | SME |

---

# Technical evidence

The tables below are the validator's raw checks behind the explanations above.

## Failing checks (FAIL)

None.

## Review checks (REVIEW)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 111 | Section[3].VariableInformation | 113 | `0100100001` | APPI-T030 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Section[4].VariableInformation | 113 | `08001C` | APPI-T032 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Section[5].VariableInformation | 113 | `021C` | APPI-T049 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Section[6].VariableInformation | 113 | `0904C101` | APPI-T056 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| TC rule_checks (step 1) | LEG-NONFUEL-AMOUNT-MAX-LENGTH | - | `-` | value_length_max | **REVIEW** | Unsupported rule_check op. | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) |

## Leg 1: `TD-TC-023074.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'Financial Transaction Request' (role balance_inquiry, Transaction Type 'E') => physical message 'Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
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
| Seg 100 | PromptCode | 78 | `E020` | SEG100-E78-FORMAT | **PASS** | AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E). | 12.1 F5; 13 E78 |
| Seg 100 | PromptCode.TransactionType | 78 | `E` | APPG-TXN-TYPE | **PASS** | Appendix G: Balance Inquiry; TC leg (role balance_inquiry) requires 'E'. | App. G (p.545) |
| Seg 100 | PromptCode.CardType | 78 | `020` | APPE-CARD-TYPE | **PASS** | Appendix E code 020 = Credit (MASTERCARD credit program). | App. E-4 |
| Seg 100 | AccountNumber | 2 | `5131977455679340` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `5131977455679340` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `513197` | PAN-IIN-MASTERCARD | **PASS** | MasterCard IIN ranges 51-55 / 2221-2720 (TC network MASTERCARD). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `0627` | SEG100-R-042 | **PASS** | AN up to 51; manually entered card => expiration date MMYY. | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `0627` | E12-KEYED-EXPIRY-MMYY | **PASS** | Keyed entry (POS entry 10): expiry is MMYY (13 E12 'manually entered ... MMYY format'); month '06' must be 01-12 (valid, not expired as of 2611). | 13 E12 (p.13-32) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `(empty)` | SEG100-E58-COND | **NA** | Balance Inquiry (type E) carries no transaction amount; Element 58 is conditional. | 12.1 F11; 13 E58 |
| Seg 100 | SequenceNumber | 86 | `230741` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `(empty)` | SEG100-E5-COND | **NA** | Not populated; not required for transaction type 'E'. | 13 E5 (p.368) |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (format) | 84 | `074` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `074 (computed 74)` | E84-SEG100-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: sum of transmitted fields (17) + one Field Separator after each. | 12.1 F2; 11.8.1 max 619 |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `101` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `101` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '10' = Credential on file; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `10` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '10' (Credential on file) for entry_mode CREDENTIAL_ON_FILE. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Section[3].VariableInformationIndicator | 111 | `030` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[3].VariableInformationLength | 112 | `010` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (10). | 12.10 F4; 13 E112 |
| Seg 111 | Section[3].VariableInformation | 113 | `0100100001` | APPI-T030 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Section[4].VariableInformationIndicator | 111 | `032` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[4].VariableInformationLength | 112 | `006` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (6). | 12.10 F4; 13 E112 |
| Seg 111 | Section[4].VariableInformation | 113 | `08001C` | APPI-T032 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Section[5].VariableInformationIndicator | 111 | `049` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[5].VariableInformationLength | 112 | `004` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (4). | 12.10 F4; 13 E112 |
| Seg 111 | Section[5].VariableInformation | 113 | `021C` | APPI-T049 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Section[6].VariableInformationIndicator | 111 | `056` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[6].VariableInformationLength | 112 | `008` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (8). | 12.10 F4; 13 E112 |
| Seg 111 | Section[6].VariableInformation | 113 | `0904C101` | APPI-T056 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `69` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `078` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `078 (computed 78)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Entry mode | Table 005 vs Element 12 content | 113/12 | `101 / 0627` | APPJ-KEYED-NO-TRACK | **PASS** | PAN entry '10' (Credential on file) means the card was not read; Element 12 must carry the manually entered expiry (MMYY), not Track 2 data. | App. J-1 (p.631); 13 E12 (p.13-32) |
| Message | Total message length | - | `162` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `074` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '074' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `JX72948216050257001 \| TC: JX72948216050257001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [scenario_parameter_literal] | - | `E020 \| TC: E020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `5131977455679340 \| TC: 5131977455679340` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_expiry_date] | - | `0627 \| TC: 0627` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [qe_example_value] | - | `230741 \| TC: 230741` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [test_data_config_value] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `101 \| TC: 101` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [3] | - | `030 \| TC: 030` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [3] | - | `010 \| TC: 010` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [3] | - | `0100100001 \| TC: 0100100001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [4] | - | `032 \| TC: 032` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [4] | - | `006 \| TC: 006` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [4] | - | `08001C \| TC: 08001C` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [5] | - | `049 \| TC: 049` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [5] | - | `004 \| TC: 004` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [5] | - | `021C \| TC: 021C` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [6] | - | `056 \| TC: 056` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [6] | - | `008 \| TC: 008` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [6] | - | `0904C101 \| TC: 0904C101` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
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

## Test objective and flow checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Objective (G-HP-e1ed5b66dc) | Test objective | - | `Happy path: Balance Inquiry approved` | G-HP-e1ed5b66dc | **NA** | catalog_status=grounded; status=pending_sme; open_items=["Does a MasterCard credit issuer return a balance? Approval code for Transaction Type E."] | TC |
| TC rule_checks (step 1) | LEG-TXN-TYPE Prompt Code | - | `E` | value_slice_in | **PASS** | Expected to hold; allowed [E]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-ENTRY-MODE 005 | - | `10` | table_slice_in | **PASS** | Expected to hold; allowed [10]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 1) | LEG-NONFUEL-AMOUNT-MAX-LENGTH | - | `-` | value_length_max | **REVIEW** | Unsupported rule_check op. | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) |
| TC rule_checks (step 1) | SEG-111-REQUIRED 111 | - | `[100, 111]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 1) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |
| TC rule_checks (step 1) | TABLE-030-VALUE 030 | - | `0100100001` | table_slice_in | **PASS** | Expected to hold; allowed [0100100001]. | TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based transactions (Ex:); DERIVED_FROM_KB: Appendix Q p.691, p.692, p.696 entries matching {'attendance': 'attended', 'operator': 'card_acceptor', 'premise': 'on', 'device': 'pos_terminal', 'customer |
| TC rule_checks (step 1) | TABLE-032-VALUE 032 | - | `08001C` | table_slice_in | **PASS** | Expected to hold; allowed [08001C]. | TABLE_REQUIRED: Table 032 POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08) (p.575: It is merchant's responsibility to send the indicator as Initial ("I") or) |
| TC rule_checks (step 1) | TABLE-049-VALUE 049 | - | `021C` | table_slice_in | **PASS** | Expected to hold; allowed [021C]. | TABLE_REQUIRED: Table 049 Merchant Supplementary Data - Transaction Initiation Indicator (Sub Table 02) (p.589: Note: This field is required for all subsequent Credential on File) |
| TC rule_checks (step 1) | TABLE-056-VALUE 056 | - | `0904C101` | table_slice_in | **PASS** | Expected to hold; allowed [0904C101]. | TABLE_REQUIRED: Table 056 MasterCard Transaction Additional Data - MIT/CIT category (Sub Table 09) (p.144: and Merchant initiated transactions (MIT) for MasterCard card type.); DERIVED_FROM_KB: Sub Table 09 valid value C101 (p.609), unscheduled / initiator C |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
