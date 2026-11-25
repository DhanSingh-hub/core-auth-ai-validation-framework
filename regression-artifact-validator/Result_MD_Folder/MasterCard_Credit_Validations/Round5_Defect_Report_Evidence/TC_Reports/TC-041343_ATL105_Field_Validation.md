# ATL105 Field-Level Test Data Validation - TC-041343

| Item | Value |
|---|---|
| Test case | TC-041343 (BR1_TS154_TC47) |
| Name | BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only, then Purchase/Capture (not allowed after this o… → declined (to confirm) |
| Scenario | TS-ATL105-CREDIT-MASTERCARD-AUTH_ONLY-EMV_CONTACT@PETROLEUM-ATTENDED |
| Template / type | G-IT-d32476c4da / Invalid state transition |
| Objective | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined |
| Transaction type | EMV Financial Transaction Request → EMV Financial Transaction Request |
| TC status | needs_client_input (catalog: grounded) |
| Open items | ["Decline test card or simulator decline trigger for the declined leg.","Decline Code values are defined by BUYPASS (element 26, p.379); Appendix H lists only the codes permitting clerk/customer intervention - the expected Decline Code is an SME/BUYPASS input.","Device behaviour after a declined follow-on (e.g. a declined reversal) beyond displaying the decline is not stated - SME question."] |
| Test data file(s) | TD-TC-041343.step1.auth-only.json, TD-TC-041343.step2.purchase.json |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-17T16:15:29.6762735 |
| Overall verdict | **FAIL** |
| Counts | PASS 300 / FAIL 4 / REVIEW 24 / N/A 20 (individual field checks) |

## What is wrong in this test case and how to fix it

**2 defect(s) to fix.** Each one shows where it is in the test data, what the AI generated, why that is wrong, and the value that should be there.

### Defect 1 – EMV Additional Information contains random text instead of `EMVYES` / `EMVNOT`

| | |
|---|---|
| **Where** | `TD-TC-041343.step1.auth-only.json` line 68 → `EMV Request Data Segment` → `EMVAdditionalInformation` |
| **What the AI generated** | 984 random letters/digits |
| **What is wrong** | `EMVAdditionalInformationIndicator` = `001` tells the host that this field holds *EMV Table Data*. For that table only two values exist: `EMVYES` or `EMVNOT` (6 characters). The AI ignored the allowed values and filled the field to its maximum size (984) with random characters, which the host cannot interpret. |
| **What it should be** | `EMVAdditionalInformation` = `EMVYES` (or `EMVNOT`), `EMVAdditionalInformationLength` = `006`, and the Segment 130 `SegmentLength` recomputed. (Whether a Table ID/Table Length prefix is also needed is an open SME question – see the findings summary Q-01.) |
| **Spec reference** | Element 191: `001` = EMV table data → Element 118: *"EMV table data is included here … refer to Appendix T"* → Appendix T-1 (p.694): EMV Table Data request values **EMVYES / EMVNOT** |

### Defect 2 – EMV Additional Information contains random text instead of `EMVYES` / `EMVNOT`

| | |
|---|---|
| **Where** | `TD-TC-041343.step2.purchase.json` line 68 → `EMV Request Data Segment` → `EMVAdditionalInformation` |
| **What the AI generated** | 984 random letters/digits |
| **What is wrong** | `EMVAdditionalInformationIndicator` = `001` tells the host that this field holds *EMV Table Data*. For that table only two values exist: `EMVYES` or `EMVNOT` (6 characters). The AI ignored the allowed values and filled the field to its maximum size (984) with random characters, which the host cannot interpret. |
| **What it should be** | `EMVAdditionalInformation` = `EMVYES` (or `EMVNOT`), `EMVAdditionalInformationLength` = `006`, and the Segment 130 `SegmentLength` recomputed. (Whether a Table ID/Table Length prefix is also needed is an open SME question – see the findings summary Q-01.) |
| **Spec reference** | Element 191: `001` = EMV table data → Element 118: *"EMV table data is included here … refer to Appendix T"* → Appendix T-1 (p.694): EMV Table Data request values **EMVYES / EMVNOT** |

### Items to confirm (not defects)

These cannot be decided from the specification alone. They do not make the test data wrong.

| Item | Why it is open | Who confirms |
|---|---|---|
| Terminal ID / merchant number | The value is a placeholder. BUYPASS assigns the real device type, merchant and device number. | Client merchant list |
| Section[3].VariableInformation | This logical validator has no implemented predicate for the selected table. | SME |
| CA Public Key File Checksum | This is a sample value. It must equal the checksum of the CA key file currently loaded at the host. | Host / key management team |
| Chip data length prefix | The 2-byte length prefix at the start of EMVChipData matches the spec example, but the spec does not state explicitly that it is counted in EMVChipDataLength. | SME |
| Chip tags vs Segment 100 values | Appendix R does not state this rule. The important pairs (amount, transaction type, currency) are checked separately in this report. | Informational |
| Cryptogram, ATC, unpredictable number, TVR | These are dummy chip values. A real card or card simulator must produce them. | Card simulator |
| Table Length inside EMV Additional Information | Appendix T does not say how many digits this field has. | BUYPASS / SME |
| Echo of EMV table data | The value must be echoed in later messages. This can only be checked across a full run. | Execution |
| Value taken from the previous response | Correctly left as a run-time value (e.g. Approval Number from step 1). It can only be checked during execution. | Execution |
| Length reserved for a run-time value | The declared length leaves room for a run-time value of a plausible size. | Execution |
| Segment 100 length with a run-time value | Cannot be confirmed until the run-time value is filled in. | Execution |
| TVR says "online PIN entered" but no PIN is sent | The TVR is a dummy value. Once simulated, it must agree with the cardholder verification method. | Card simulator |
| LEG-NONFUEL-AMOUNT-MAX-LENGTH | Unsupported rule_check op. | SME |
| Value taken from the previous response | Correctly left as a run-time value (e.g. Approval Number from step 1). It can only be checked during execution. | Execution |

---

# Technical evidence

The tables below are the validator's raw checks behind the explanations above.

## Failing checks (FAIL)

| # | Area | Field | Element | Value | Rule | Finding | Spec source |
|---|---|---|---|---|---|---|---|
| 1 | Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-id-match` | BR-SEG100-APPT-TABLE-ID-MATCH | Embedded Table ID 'EMV' must equal the EMV Additional Information Indicator value '001'. | App. T-1; 13 E191/E192 |
| 2 | Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `emv-table-data` | BR-SEG100-APPT-EMV-TABLE-DATA | This rule only applies when Table ID is '001'; found 'EMV'. | App. T-1; 13 E191/E192 |
| 3 | Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-id-match` | BR-SEG100-APPT-TABLE-ID-MATCH | Embedded Table ID 'EMV' must equal the EMV Additional Information Indicator value '001'. | App. T-1; 13 E191/E192 |
| 4 | Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `emv-table-data` | BR-SEG100-APPT-EMV-TABLE-DATA | This rule only applies when Table ID is '001'; found 'EMV'. | App. T-1; 13 E191/E192 |

## Review checks (REVIEW)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `KC / 457075622174 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 111 | Section[3].VariableInformation | 113 | `0100000004` | APPI-T030 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 130 | CAPublicKeyFileChecksum.Value | 187 | `2012091700000100000102171` | SEG130-R-015 | **REVIEW** | Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a QE-sample (library_provisional) - confirm against the host key file. | 13 E187; App. S |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PACKED-PREFIX-BOUNDARY | **REVIEW** | Packed-BCD prefix matches the following payload byte count; producer field boundary remains unconfirmed. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-CROSS-FIELD | **REVIEW** | Agreement between decoded EMV tag values and the corresponding Segment 100 elements is not stated anywhere in Appendix R or in Element 189/190's own definitions, so it is not independently assertable from them alone. | App. R-1; 13 E190 |
| Seg 130 | Tag 9F26/9F10/9F36/9F37/95 | 190 | `B4302C8AE8B7E91C` | SEG130-R-010 | **REVIEW** | Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM. | SEG130-R-010; App. R |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-length-field` | BR-SEG100-APPT-TABLE-LENGTH-FIELD | **REVIEW** | Appendix T states the 'Table Length' field is numeric and fixed-width, but never states the digit count or a concrete value for either table; no independent rule can be asserted without fabricating a width the spec does not provide. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `echo` | BR-SEG100-APPT-ECHO | **REVIEW** | Appendix T-1 states EMV Table Data included in an authorization response must be echoed back on subsequent advice/batch-upload requests, but confirming that echo requires comparing multiple messages across a transaction lifecycle, not a single request/response snapshot. | App. T-1; 13 E191/E192 |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `KC / 457075622174 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | ApprovalNumber | 5 | `<ApprovalNumber from step 1 response>` | SEG100-E5-FOLLOW-ON | **REVIEW** | Required on this follow-on (purchase) to match the original (AN6 fixed, space-filled); runtime link to the original response - must resolve to the 6-character Approval Number at execution. | 13 E5 (p.368); p.85 |
| Seg 100 | SegmentLength (implied runtime width) | 84 | `084` | E84-SEG100-RUNTIME-WIDTH | **REVIEW** | Declared length implies the runtime value(s) [ApprovalNumber] occupy 6 character(s) (consistent with Element 5 fixed length 6). | 12.1 F2; 13 E5/E58 |
| Seg 100 | SegmentLength (value) | 84 | `084 (computed with placeholder text: 115)` | E84-SEG100-LENGTH | **REVIEW** | Contains a runtime value; declared length cannot be verified until resolved (sum of transmitted fields (17) + one Field Separator after each). | 12.1 F2; 11.8.1 max 619 |
| Seg 111 | Section[3].VariableInformation | 113 | `0100000004` | APPI-T030 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 130 | CAPublicKeyFileChecksum.Value | 187 | `2012091700000100000102171` | SEG130-R-015 | **REVIEW** | Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a QE-sample (library_provisional) - confirm against the host key file. | 13 E187; App. S |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PACKED-PREFIX-BOUNDARY | **REVIEW** | Packed-BCD prefix matches the following payload byte count; producer field boundary remains unconfirmed. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-CROSS-FIELD | **REVIEW** | Agreement between decoded EMV tag values and the corresponding Segment 100 elements is not stated anywhere in Appendix R or in Element 189/190's own definitions, so it is not independently assertable from them alone. | App. R-1; 13 E190 |
| Seg 130 | Tag 9F26/9F10/9F36/9F37/95 | 190 | `B4302C8AE8B7E91C` | SEG130-R-010 | **REVIEW** | Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM. | SEG130-R-010; App. R |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-length-field` | BR-SEG100-APPT-TABLE-LENGTH-FIELD | **REVIEW** | Appendix T states the 'Table Length' field is numeric and fixed-width, but never states the digit count or a concrete value for either table; no independent rule can be asserted without fabricating a width the spec does not provide. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `echo` | BR-SEG100-APPT-ECHO | **REVIEW** | Appendix T-1 states EMV Table Data included in an authorization response must be echoed back on subsequent advice/batch-upload requests, but confirming that echo requires comparing multiple messages across a transaction lifecycle, not a single request/response snapshot. | App. T-1; 13 E191/E192 |
| TC<->TD | Standard Message Data Segment.Approval Number [flow_response_runtime_value] | - | `<ApprovalNumber from step 1 response> \| TC: KDUK67` | TC-TD-FIDELITY | **REVIEW** | TD carries the runtime expression; TC carries a provisional value. | TC request |
| Objective (G-IT-d32476c4da) | TVR byte 3 'Online PIN entered' vs PIN block | 190/33 | `8000048000 / PIN absent` | EMV-TVR-CVM-CONSISTENCY | **REVIEW** | TVR byte 3 bit 3 (0x04) is set (online PIN entered) while Element 33 is empty; TVR is an EMV_PLACEHOLDER - the card simulator value must be consistent with the CVM. | EMV Book 3 Annex C5; 13 E33 |
| TC rule_checks (step 1) | LEG-NONFUEL-AMOUNT-MAX-LENGTH | - | `-` | value_length_max | **REVIEW** | Unsupported rule_check op. | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) |
| TC rule_checks (step 2) | LEG-NONFUEL-AMOUNT-MAX-LENGTH | - | `-` | value_length_max | **REVIEW** | Unsupported rule_check op. | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) |
| TC link_defs (step 2 <- step 1) | Approval Number from step 1 response | - | `<ApprovalNumber from step 1 response>` | carry_response_field | **REVIEW** | Correctly left as a runtime link (response values are processor-generated). | p.368: Plan v7 W2: Element 5 (p.368) 'If it is included in a Purchase Request, then the Purchase Request is considered preauthorized' - the Authorization Only response's Approval Number (runtime link). |

## Leg 1: `TD-TC-041343.step1.auth-only.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `EMV Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'EMV Financial Transaction Request' (role auth_only, Transaction Type '3') => physical message 'EMV Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55-FINANCIAL-REQUEST | **PASS** | Fixed value 'ATL105' (AN6). | ATL105 11.8.1 F1; 13 E55 |
| Data Section 1 | NumberofSegments | 63 | `04` | E63-EMV-FINANCIAL-REQUEST-COUNT | **PASS** | N up to 2 digits; must equal segments present (4: [100, 102, 111, 130]). | ATL105 11.8.1 F2; 13 E63 |
| Data Section 1 | Section 3 slots | - | `3` | SEG130-R-018 | **PASS** | EMV request Data Section 3 occupies Field Nos. 4-8 (1..5 segments). | ATL105 11.1 / 11.8.1 p.11-43 |
| Segment presence | Standard Message Data Segment (100) | - | `present` | SEG-100-PRESENCE | **PASS** | Required in Data Section 2 of every Financial Transaction Request. TC decision: REQUIRED. | ATL105 11.1 / 11.8.1 p.11-42; 12.1 |
| Segment presence | EMV Request Data Segment (130) | - | `present` | SEG-130-PRESENCE | **PASS** | Always appears in Data Section 3 of an EMV request. TC decision: REQUIRED. | ATL105 12.20; SEG130-R-001 |
| Segment presence | Variable Information Data Segment (111) | - | `present` | SEG-111-PRESENCE | **PASS** | Table 031 must be populated for all transactions (BR-569-1). TC decision: REQUIRED_BY_CONDITION. | ATL105 App. I-20 (p.569) |
| Seg 100 | Field layout (17 fields) | - | `17 keys` | LAYOUT-Seg100 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 100 | SegmentType | 85 | `100` | E85-SEG100-TYPE | **PASS** | N3, fixed value 100. | 12.1 F1 |
| Seg 100 | InformationByte | 44 | `0` | SEG100-E44 | **PASS** | AN1: '?' download, '0' single-message, '1' multimessage. | 13 E44 (p.379) |
| Seg 100 | TerminalIdentifier | 102 | `KC57457075622174001` | SEG100-E102-FORMAT | **PASS** | AN up to 22: Device Type(2)+State Code(2)+Merchant No.(6-15)+Device No.(3) => 13..22 chars. | 13 E102 (p.410-411) |
| Seg 100 | TerminalIdentifier.StateCode | 102 | `57` | APPD-ANSI-STATE | **PASS** | Positions 3-4 must be a valid ANSI state code (Appendix D). | 13 E102; App. D |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `KC / 457075622174 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | PromptCode | 78 | `3020` | SEG100-E78-FORMAT | **PASS** | AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E). | 12.1 F5; 13 E78 |
| Seg 100 | PromptCode.TransactionType | 78 | `3` | APPG-TXN-TYPE | **PASS** | Appendix G: POS Authorization Only; TC leg (role auth_only) requires '3'. | App. G (p.545) |
| Seg 100 | PromptCode.CardType | 78 | `020` | APPE-CARD-TYPE | **PASS** | Appendix E code 020 = Credit (MASTERCARD credit program). | App. E-4 |
| Seg 100 | AccountNumber | 2 | `5159670865811317` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `5159670865811317` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `515967` | PAN-IIN-MASTERCARD | **PASS** | MasterCard IIN ranges 51-55 / 2221-2720 (TC network MASTERCARD). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `310620117107731` | SEG100-R-042 | **PASS** | AN up to 51; Track 2 data after '=' => YYMM expiry + 3-digit service code + discretionary. | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `3106` | TRACK2-EXPIRY-YYMM | **PASS** | Expiry YYMM; month '06' must be 01-12 (valid, not expired as of 2611). | 13 E12; Track 2 (ISO 7813) |
| Seg 100 | CardDiscretionaryBlockData.ServiceCode | 12 | `201` | TRACK2-SERVICE-CODE-CHIP | **PASS** | Service code 1st digit 2/6 = chip card (required for chip read / chip fallback, POS entry 05). | ISO 7813 |
| Seg 100 | Account+Discretionary (Track 2) | 2+12 | `32` | TRACK2-COMBINED-37 | **PASS** | Account Number + '=' + Card Discretionary Block Data must not exceed 37 for Track 2. | 13 E12 (p.364) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `1500` | SEG100-E58-FORMAT | **PASS** | N, up to 7 digits for MASTERCARD (8 only for American Express), 2 assumed decimals, > 0. | 13 E58 (p.384) |
| Seg 100 | SequenceNumber | 86 | `413431` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `(empty)` | SEG100-E5-COND | **NA** | Not populated; not required for transaction type '3'. | 13 E5 (p.368) |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (format) | 84 | `089` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `089 (computed 89)` | E84-SEG100-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: sum of transmitted fields (17) + one Field Separator after each. | 12.1 F2; 11.8.1 max 619 |
| Seg 102 | SegmentType | 85 | `102` | E85-SEG102-TYPE | **PASS** | N3, fixed value 102. | 12.3 F1 |
| Seg 102 | ServiceLevel | 87 | `F` | SEG102-E87 | **PASS** | A1: F full, S self, N mini, X maxi, H high-speed, O other/fuel not present (0-9 private). | 12.3 F3; 13 E87 |
| Seg 102 | NumberofProducts | 62 | `01` | SEG102-E62 | **PASS** | N2 01-10 (leading zero) and equal to the products present (1). | 12.3 F4; 13 E62 |
| Seg 102 | ProductCode | 77 | `400` | SEG102-E77 | **PASS** | N3 product code (Appendix F). | 12.3 F5; 13 E77; App. F |
| Seg 102 | UnitofMeasure | 106 | `U` | SEG102-E106 | **PASS** | A1: C,G,H,I,K,L,M,P,Q,U,W,Z,O (0-9 private). | 12.3 F6; 13 E106 |
| Seg 102 | Quantity | 81 | `01` | SEG102-E81 | **PASS** | N up to 9; leading digit = assumed decimals (0-3) and a digit for every decimal position. | 12.3 F7; 13 E81 |
| Seg 102 | UnitPrice | 107 | `21500` | SEG102-E107 | **PASS** | N up to 9; leading digit = assumed decimals (0-3). | 12.3 F8; 13 E107 |
| Seg 102 | ProductAmount | 76 | `1500` | SEG102-E76 | **PASS** | N 1-999999999999, two assumed decimals. | 12.3 F9; 13 E76 |
| Seg 102 | Quantity x UnitPrice vs ProductAmount | 81/107/76 | `1 x 15.00 = 15.00 vs 15.00` | SEG102-EXTENSION | **PASS** | Product Amount normally equals Quantity x Unit Price (discounts/coupons use negative codes). | 12.3; 13 E76/E81/E107 |
| Seg 102 | Sum of ProductAmount vs Segment 100 amounts | 76 / 41+58+99+17 | `1500 vs 1500` | SEG102-TOTAL | **PASS** | The total of product amounts must equal Elements 41 + 58 + 99 + 17 in Segment 100. | 12.3 (p.226) |
| Seg 102 | SegmentLength (format) | 84 | `029` | E84-SEG102-LENGTH | **PASS** | N3, range 1-381. | 12.3 F2; App. B |
| Seg 102 | SegmentLength (value) | 84 | `029 (computed 29)` | E84-SEG102-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+service level+count+per product (code+UoM+qty+'\'+price+'\'+amount+delimiter/FS); formula reproduces Appendix B example length 049. | 12.3 F2; App. B |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `051` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `051` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '05' = ICC read - CVV data reliable; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `05` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '05' (ICC read - CVV data reliable) for entry_mode EMV_CONTACT. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Section[3].VariableInformationIndicator | 111 | `030` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[3].VariableInformationLength | 112 | `010` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (10). | 12.10 F4; 13 E112 |
| Seg 111 | Section[3].VariableInformation | 113 | `0100000004` | APPI-T030 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `33` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `042` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `042 (computed 42)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Seg 130 | Field layout (9 fields) | - | `9 keys` | LAYOUT-Seg130 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 130 | SegmentType | 85 | `130` | SEG130-R-002 | **PASS** | N3, fixed value 130. | 12.20 F1 |
| Seg 130 | CAPublicKeyFileChecksum | 187 | `2012091700000100000102171` | SEG130-R-005 | **PASS** | N, fixed length 25; required in the EMV Financial Transaction Request (13 E187). | 12.20 F3; 13 E187 (p.469) |
| Seg 130 | CAPublicKeyFileChecksum.Value | 187 | `2012091700000100000102171` | SEG130-R-015 | **REVIEW** | Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a QE-sample (library_provisional) - confirm against the host key file. | 13 E187; App. S |
| Seg 130 | EMVCardSequenceNumber | 188 | `001` | SEG130-R-006/E188 | **PASS** | AN, FIXED length 3, right-aligned zero-padded (000-099) or 3 spaces when Tag 5F34 absent. | 12.20 F4; 13 E188 (p.470) |
| Seg 130 | EMVChipDataLength | 189 | `147` | SEG130-R-007 | **PASS** | N3, 000-999. | 12.20 F5; 13 E189 |
| Seg 130 | EMVChipData | 190 | `01455F2A0208405F340101820258008407A0000000041010950580000480...(len 294)` | SEG130-R-008 | **PASS** | ANSB up to 999 bytes; hex-nibble representation of binary TLV (147 bytes). | 12.20 F6; 13 E190 (p.471) |
| Seg 130 | EMVChipDataLength vs EMVChipData | 189/190 | `147 vs 147` | BR-SEG100-APPR-CHIP-LENGTH | **PASS** | Element 189 must equal the byte length of Element 190. | 13 E189/E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PACKED-PREFIX-BOUNDARY | **REVIEW** | Packed-BCD prefix matches the following payload byte count; producer field boundary remains unconfirmed. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-TLV-FORMAT | **PASS** | Fully decoded 22 TLV entries with no leftover bytes. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-MANDATORY-IDENTIFIER | **PASS** | Application Identifier and/or Dedicated File Name tag is present as required. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PROHIBITED-SENSITIVE-TAG | **PASS** | Neither PAN (tag 5A) nor Track 2 equivalent data (tag 57) is present, as required. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-DUPLICATE-TAG-REVIEW | **PASS** | No tag appears more than once in the decoded TLV stream. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-EMV-COMPANION | **PASS** | Transaction is EMV and Element 189/190 are present as required. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-CROSS-FIELD | **REVIEW** | Agreement between decoded EMV tag values and the corresponding Segment 100 elements is not stated anywhere in Appendix R or in Element 189/190's own definitions, so it is not independently assertable from them alone. | App. R-1; 13 E190 |
| Seg 130 | Tag 9F02 vs Segment 100 amounts | 190/41+58+99+17 | `000000001500 vs 1500` | SEG130-R-009 | **PASS** | Amount Authorized (9F02) must equal the Segment 100 transaction total (NonfuelAmount=1500). | SEG130-R-009 (KB: REVIEW_REQUIRED formula); generator rule '9F02 = request amount' |
| Seg 130 | Tag 9C vs PromptCode.TransactionType | 190/78 | `00 vs 3` | SEG130-R-009 | **PASS** | Transaction type '3' (POS Authorization Only) => 9C = 00 (00 goods/services, 09 purchase with cashback, 20 refund/return, 30/31 available-funds/balance inquiry). | SEG130-R-009; EMV Book 3 Annex A (ISO 8583 processing code) |
| Seg 130 | Tag 9F03 vs CashAmount | 190/17 | `000000000000 vs (empty)` | SEG130-R-009 | **PASS** | Amount, Other (9F03) carries the cashback amount; must equal Segment 100 Cash Amount (Element 17). | SEG130-R-009; EMV Book 3 |
| Seg 130 | AID (9F06/84) | 190 | `A0000000041010` | BR-SEG100-APPR-MANDATORY-IDENTIFIER | **PASS** | AID present; MASTERCARD RID A000000004 expected for network MASTERCARD. | 13 E190; App. R |
| Seg 130 | Tag 9F06 vs Tag 84 | 190 | `A0000000041010 vs A0000000041010` | EMV-AID-DFNAME | **PASS** | Terminal AID and DF Name should identify the same application. | EMV Book 1 |
| Seg 130 | Tag 5F2A / 9F1A | 190 | `0840 / 0840` | SEG130-R-009 | **PASS** | USD currency (0840) and US terminal country (0840). | App. R-1 example; SEG130-R-009 |
| Seg 130 | Element 188 value vs Tag 5F34 | 188/190 | `001 vs 01` | E188-5F34 | **PASS** | Element 188 must carry the chip's Tag 5F34 value (wire form '001'; width checked separately). | 13 E188 (p.470) |
| Seg 130 | Tag 9F27 (CID) | 190 | `80` | EMV-CID-ARQC | **PASS** | Online request => Cryptogram Information Data 80 (ARQC). | EMV Book 3 |
| Seg 130 | Tag 9F26/9F10/9F36/9F37/95 | 190 | `B4302C8AE8B7E91C` | SEG130-R-010 | **REVIEW** | Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM. | SEG130-R-010; App. R |
| Seg 130 | Decoded TLV | 190 | `5F2A=0840 5F34=01 82=5800 84=A0000000041010 95=8000048000 9A=260101 9C=00 9F02=000000001500 9F03=000000000000 9F06=A0000000041010 9F10=010103A40000DAC0 9F1A=0840 9F1E=3132333435363738 9F26=B4302C8AE8B7E91C 9F27=80 9F33=E0F0C8 9F34=5E0302 9F35=22 9F36=01D0 9F37=C19C7A30 9F41=012850 9F53=52` | BR-SEG100-APPR-TLV-FORMAT | **PASS** | 22 TLV entries decoded. | 13 E190 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `indicator-format` | BR-SEG100-APPT-INDICATOR-FORMAT | **PASS** | EMV Additional Information Indicator is exactly 3 numeric digits with value '001' or '002'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `length-format` | BR-SEG100-APPT-LENGTH-FORMAT | **PASS** | EMV Additional Information Length is exactly 3 numeric digits in range 001-985. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-id-match` | BR-SEG100-APPT-TABLE-ID-MATCH | **FAIL** | Embedded Table ID 'EMV' must equal the EMV Additional Information Indicator value '001'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `emv-table-data` | BR-SEG100-APPT-EMV-TABLE-DATA | **FAIL** | This rule only applies when Table ID is '001'; found 'EMV'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `carc` | BR-SEG100-APPT-CARC | **NA** | Not applicable: Indicator is '001'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-length-field` | BR-SEG100-APPT-TABLE-LENGTH-FIELD | **REVIEW** | Appendix T states the 'Table Length' field is numeric and fixed-width, but never states the digit count or a concrete value for either table; no independent rule can be asserted without fabricating a width the spec does not provide. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `echo` | BR-SEG100-APPT-ECHO | **REVIEW** | Appendix T-1 states EMV Table Data included in an authorization response must be echoed back on subsequent advice/batch-upload requests, but confirming that echo requires comparing multiple messages across a transaction lifecycle, not a single request/response snapshot. | App. T-1; 13 E191/E192 |
| Seg 130 | EMVAdditionalInformationIndicator | 191 | `001` | E191 | **PASS** | N3; 001 EMV table data (App. T also defines 002 CARC). | 12.20 F7; 13 E191 (p.472) |
| Seg 130 | EMVAdditionalInformationLength | 192 | `006` | E192 | **PASS** | N3 001-985 and equal to the length of Element 118 (6). | 12.20 F8; 13 E192 (p.473) |
| Seg 130 | EMVAdditionalInformation | 118 | `EMVYES` | SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA | **PASS** | Table 001 (EMV Table Data) value must be exactly 6 chars 'EMVYES' or 'EMVNOT' (observed 6 random alphanumeric chars; length should be 006). | App. T-1 (p.694); 13 E118 |
| Seg 130 | EMV Additional Information Section total | - | `12` | SEG130-R-011 | **PASS** | Repeated section total max 2,000 bytes. | 12.20 |
| Seg 130 | SegmentLength (format) | 84 | `0204` | SEG130-R-003/R-004 | **PASS** | N4, range 1-3043. | 12.20 F2; SEG130-R-013 |
| Seg 130 | SegmentLength (value) | 84 | `0204 (computed 204)` | SEG130-R-003/R-004 | **PASS** | Must include Segment Type, all fields and Field Separators: 6 fixed fields each + Field Separator (chip data counted as binary bytes) + EMV Additional Information section + final Field Separator. | 12.20 F2; SEG130-R-013 |
| Entry mode | Table 005 vs Element 12 content | 113/12 | `051 / 310620117107731` | APPJ-READ-HAS-TRACK | **PASS** | PAN entry '05' (ICC read - CVV data reliable) means the card was read; the original request must carry the unaltered Track 2 data after '='. | App. J-1; 13 E12 (p.364) |
| Message | Total message length | - | `374` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `089` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '089' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `KC57457075622174001 \| TC: KC57457075622174001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [flow_fixed_transaction_code] | - | `3020 \| TC: 3020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `5159670865811317 \| TC: 5159670865811317` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_track2_discretionary] | - | `310620117107731 \| TC: 310620117107731` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [scenario_parameter_literal] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [qe_example_value] | - | `413431 \| TC: 413431` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [test_data_config_value] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.Segment Type [fixed_value] | - | `130 \| TC: 130` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.Segment Length | 84 | `0204` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '0204' (verified in spec checks). | TC method |
| TC<->TD | EMV Request Data Segment.CA Public Key File Checksum [test_data_config_value] | - | `2012091700000100000102171 \| TC: 2012091700000100000102171` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Card Sequence Number [realistic_emv_card_sequence] | - | `001 \| TC: 001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Chip Data Length [derived_from_linked_field] | - | `147 \| TC: 147` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Chip Data [spec_tlv_composite] | - | `01455F2A0208405F340101820258008407A0000000041010950580000480...(len 294) \| TC: 01455F2A0208405F340101820258008407A0000000041010950580000480...(len 294)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information Indicator [qe_example_value] | - | `001 \| TC: 001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information Length [derived_from_linked_field] | - | `006 \| TC: 006` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information [spec_rule_value] | - | `EMVYES \| TC: EMVYES` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `051 \| TC: 051` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [3] | - | `030 \| TC: 030` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [3] | - | `010 \| TC: 010` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [3] | - | `0100000004 \| TC: 0100000004` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Segment Type | - | `111 \| TC: 111` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Fleet Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Product Code Data Segment | - | `present` | TC-SEGMENT-DECISION | **PASS** | TC sends this segment; TD includes it. | TC request |
| TC<->TD | Product Code Data Segment.Segment Type [fixed_value] | - | `102 \| TC: 102` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Service Level [qe_example_value] | - | `F \| TC: F` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Number of Products [spec_rule_value] | - | `01 \| TC: 01` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Product Code [spec_rule_value] | - | `400 \| TC: 400` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Unit of Measure [spec_rule_value] | - | `U \| TC: U` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Quantity [spec_rule_value] | - | `01 \| TC: 01` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Unit Price [spec_rule_value] | - | `21500 \| TC: 21500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Product Amount [spec_rule_value] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EBT Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Purchase Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Loyalty Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | SKU Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Moneris Data Segment (Request) | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Tax by Product Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Enhanced Fleet Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Request InComm OTC Market Basket Data | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Network Token Data Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EV Charging Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |

## Leg 2: `TD-TC-041343.step2.purchase.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `EMV Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'EMV Financial Transaction Request' (role purchase, Transaction Type '0') => physical message 'EMV Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55-FINANCIAL-REQUEST | **PASS** | Fixed value 'ATL105' (AN6). | ATL105 11.8.1 F1; 13 E55 |
| Data Section 1 | NumberofSegments | 63 | `04` | E63-EMV-FINANCIAL-REQUEST-COUNT | **PASS** | N up to 2 digits; must equal segments present (4: [100, 102, 111, 130]). | ATL105 11.8.1 F2; 13 E63 |
| Data Section 1 | Section 3 slots | - | `3` | SEG130-R-018 | **PASS** | EMV request Data Section 3 occupies Field Nos. 4-8 (1..5 segments). | ATL105 11.1 / 11.8.1 p.11-43 |
| Segment presence | Standard Message Data Segment (100) | - | `present` | SEG-100-PRESENCE | **PASS** | Required in Data Section 2 of every Financial Transaction Request. TC decision: REQUIRED. | ATL105 11.1 / 11.8.1 p.11-42; 12.1 |
| Segment presence | EMV Request Data Segment (130) | - | `present` | SEG-130-PRESENCE | **PASS** | Always appears in Data Section 3 of an EMV request. TC decision: REQUIRED. | ATL105 12.20; SEG130-R-001 |
| Segment presence | Variable Information Data Segment (111) | - | `present` | SEG-111-PRESENCE | **PASS** | Table 031 must be populated for all transactions (BR-569-1). TC decision: REQUIRED_BY_CONDITION. | ATL105 App. I-20 (p.569) |
| Seg 100 | Field layout (17 fields) | - | `17 keys` | LAYOUT-Seg100 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 100 | SegmentType | 85 | `100` | E85-SEG100-TYPE | **PASS** | N3, fixed value 100. | 12.1 F1 |
| Seg 100 | InformationByte | 44 | `0` | SEG100-E44 | **PASS** | AN1: '?' download, '0' single-message, '1' multimessage. | 13 E44 (p.379) |
| Seg 100 | TerminalIdentifier | 102 | `KC57457075622174001` | SEG100-E102-FORMAT | **PASS** | AN up to 22: Device Type(2)+State Code(2)+Merchant No.(6-15)+Device No.(3) => 13..22 chars. | 13 E102 (p.410-411) |
| Seg 100 | TerminalIdentifier.StateCode | 102 | `57` | APPD-ANSI-STATE | **PASS** | Positions 3-4 must be a valid ANSI state code (Appendix D). | 13 E102; App. D |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `KC / 457075622174 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | PromptCode | 78 | `0020` | SEG100-E78-FORMAT | **PASS** | AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E). | 12.1 F5; 13 E78 |
| Seg 100 | PromptCode.TransactionType | 78 | `0` | APPG-TXN-TYPE | **PASS** | Appendix G: POS Purchase/Capture; TC leg (role purchase) requires '0'. | App. G (p.545) |
| Seg 100 | PromptCode.CardType | 78 | `020` | APPE-CARD-TYPE | **PASS** | Appendix E code 020 = Credit (MASTERCARD credit program). | App. E-4 |
| Seg 100 | AccountNumber | 2 | `5159670865811317` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `5159670865811317` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `515967` | PAN-IIN-MASTERCARD | **PASS** | MasterCard IIN ranges 51-55 / 2221-2720 (TC network MASTERCARD). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `3106` | SEG100-R-042 | **PASS** | AN up to 51; follow-on carries the expiration date only (4 digits). | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `3106` | TRACK2-EXPIRY-YYMM | **PASS** | Expiry YYMM; month '06' must be 01-12 (valid, not expired as of 2611). | 13 E12; Track 2 (ISO 7813) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `1500` | SEG100-E58-FORMAT | **PASS** | N, up to 7 digits for MASTERCARD (8 only for American Express), 2 assumed decimals, > 0. | 13 E58 (p.384) |
| Seg 100 | SequenceNumber | 86 | `413431` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `<ApprovalNumber from step 1 response>` | SEG100-E5-FOLLOW-ON | **REVIEW** | Required on this follow-on (purchase) to match the original (AN6 fixed, space-filled); runtime link to the original response - must resolve to the 6-character Approval Number at execution. | 13 E5 (p.368); p.85 |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (implied runtime width) | 84 | `084` | E84-SEG100-RUNTIME-WIDTH | **REVIEW** | Declared length implies the runtime value(s) [ApprovalNumber] occupy 6 character(s) (consistent with Element 5 fixed length 6). | 12.1 F2; 13 E5/E58 |
| Seg 100 | SegmentLength (format) | 84 | `084` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `084 (computed with placeholder text: 115)` | E84-SEG100-LENGTH | **REVIEW** | Contains a runtime value; declared length cannot be verified until resolved (sum of transmitted fields (17) + one Field Separator after each). | 12.1 F2; 11.8.1 max 619 |
| Seg 102 | SegmentType | 85 | `102` | E85-SEG102-TYPE | **PASS** | N3, fixed value 102. | 12.3 F1 |
| Seg 102 | ServiceLevel | 87 | `F` | SEG102-E87 | **PASS** | A1: F full, S self, N mini, X maxi, H high-speed, O other/fuel not present (0-9 private). | 12.3 F3; 13 E87 |
| Seg 102 | NumberofProducts | 62 | `01` | SEG102-E62 | **PASS** | N2 01-10 (leading zero) and equal to the products present (1). | 12.3 F4; 13 E62 |
| Seg 102 | ProductCode | 77 | `400` | SEG102-E77 | **PASS** | N3 product code (Appendix F). | 12.3 F5; 13 E77; App. F |
| Seg 102 | UnitofMeasure | 106 | `U` | SEG102-E106 | **PASS** | A1: C,G,H,I,K,L,M,P,Q,U,W,Z,O (0-9 private). | 12.3 F6; 13 E106 |
| Seg 102 | Quantity | 81 | `01` | SEG102-E81 | **PASS** | N up to 9; leading digit = assumed decimals (0-3) and a digit for every decimal position. | 12.3 F7; 13 E81 |
| Seg 102 | UnitPrice | 107 | `21500` | SEG102-E107 | **PASS** | N up to 9; leading digit = assumed decimals (0-3). | 12.3 F8; 13 E107 |
| Seg 102 | ProductAmount | 76 | `1500` | SEG102-E76 | **PASS** | N 1-999999999999, two assumed decimals. | 12.3 F9; 13 E76 |
| Seg 102 | Quantity x UnitPrice vs ProductAmount | 81/107/76 | `1 x 15.00 = 15.00 vs 15.00` | SEG102-EXTENSION | **PASS** | Product Amount normally equals Quantity x Unit Price (discounts/coupons use negative codes). | 12.3; 13 E76/E81/E107 |
| Seg 102 | Sum of ProductAmount vs Segment 100 amounts | 76 / 41+58+99+17 | `1500 vs 1500` | SEG102-TOTAL | **PASS** | The total of product amounts must equal Elements 41 + 58 + 99 + 17 in Segment 100. | 12.3 (p.226) |
| Seg 102 | SegmentLength (format) | 84 | `029` | E84-SEG102-LENGTH | **PASS** | N3, range 1-381. | 12.3 F2; App. B |
| Seg 102 | SegmentLength (value) | 84 | `029 (computed 29)` | E84-SEG102-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+service level+count+per product (code+UoM+qty+'\'+price+'\'+amount+delimiter/FS); formula reproduces Appendix B example length 049. | 12.3 F2; App. B |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `051` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `051` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '05' = ICC read - CVV data reliable; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `05` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '05' (ICC read - CVV data reliable) for entry_mode EMV_CONTACT. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Section[3].VariableInformationIndicator | 111 | `030` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[3].VariableInformationLength | 112 | `010` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (10). | 12.10 F4; 13 E112 |
| Seg 111 | Section[3].VariableInformation | 113 | `0100000004` | APPI-T030 | **REVIEW** | This logical validator has no implemented predicate for the selected table. | App. I |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `33` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `042` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `042 (computed 42)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Seg 130 | Field layout (9 fields) | - | `9 keys` | LAYOUT-Seg130 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 130 | SegmentType | 85 | `130` | SEG130-R-002 | **PASS** | N3, fixed value 130. | 12.20 F1 |
| Seg 130 | CAPublicKeyFileChecksum | 187 | `2012091700000100000102171` | SEG130-R-005 | **PASS** | N, fixed length 25; required in the EMV Financial Transaction Request (13 E187). | 12.20 F3; 13 E187 (p.469) |
| Seg 130 | CAPublicKeyFileChecksum.Value | 187 | `2012091700000100000102171` | SEG130-R-015 | **REVIEW** | Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a QE-sample (library_provisional) - confirm against the host key file. | 13 E187; App. S |
| Seg 130 | EMVCardSequenceNumber | 188 | `001` | SEG130-R-006/E188 | **PASS** | AN, FIXED length 3, right-aligned zero-padded (000-099) or 3 spaces when Tag 5F34 absent. | 12.20 F4; 13 E188 (p.470) |
| Seg 130 | EMVChipDataLength | 189 | `147` | SEG130-R-007 | **PASS** | N3, 000-999. | 12.20 F5; 13 E189 |
| Seg 130 | EMVChipData | 190 | `01455F2A0208405F340101820258008407A0000000041010950580000480...(len 294)` | SEG130-R-008 | **PASS** | ANSB up to 999 bytes; hex-nibble representation of binary TLV (147 bytes). | 12.20 F6; 13 E190 (p.471) |
| Seg 130 | EMVChipDataLength vs EMVChipData | 189/190 | `147 vs 147` | BR-SEG100-APPR-CHIP-LENGTH | **PASS** | Element 189 must equal the byte length of Element 190. | 13 E189/E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PACKED-PREFIX-BOUNDARY | **REVIEW** | Packed-BCD prefix matches the following payload byte count; producer field boundary remains unconfirmed. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-TLV-FORMAT | **PASS** | Fully decoded 22 TLV entries with no leftover bytes. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-MANDATORY-IDENTIFIER | **PASS** | Application Identifier and/or Dedicated File Name tag is present as required. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PROHIBITED-SENSITIVE-TAG | **PASS** | Neither PAN (tag 5A) nor Track 2 equivalent data (tag 57) is present, as required. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-DUPLICATE-TAG-REVIEW | **PASS** | No tag appears more than once in the decoded TLV stream. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-EMV-COMPANION | **PASS** | Transaction is EMV and Element 189/190 are present as required. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-CROSS-FIELD | **REVIEW** | Agreement between decoded EMV tag values and the corresponding Segment 100 elements is not stated anywhere in Appendix R or in Element 189/190's own definitions, so it is not independently assertable from them alone. | App. R-1; 13 E190 |
| Seg 130 | Tag 9F02 vs Segment 100 amounts | 190/41+58+99+17 | `000000001500 vs 1500` | SEG130-R-009 | **PASS** | Amount Authorized (9F02) must equal the Segment 100 transaction total (NonfuelAmount=1500). | SEG130-R-009 (KB: REVIEW_REQUIRED formula); generator rule '9F02 = request amount' |
| Seg 130 | Tag 9C vs PromptCode.TransactionType | 190/78 | `00 vs 0` | SEG130-R-009 | **PASS** | Transaction type '0' (POS Purchase/Capture) => 9C = 00 (00 goods/services, 09 purchase with cashback, 20 refund/return, 30/31 available-funds/balance inquiry). | SEG130-R-009; EMV Book 3 Annex A (ISO 8583 processing code) |
| Seg 130 | Tag 9F03 vs CashAmount | 190/17 | `000000000000 vs (empty)` | SEG130-R-009 | **PASS** | Amount, Other (9F03) carries the cashback amount; must equal Segment 100 Cash Amount (Element 17). | SEG130-R-009; EMV Book 3 |
| Seg 130 | AID (9F06/84) | 190 | `A0000000041010` | BR-SEG100-APPR-MANDATORY-IDENTIFIER | **PASS** | AID present; MASTERCARD RID A000000004 expected for network MASTERCARD. | 13 E190; App. R |
| Seg 130 | Tag 9F06 vs Tag 84 | 190 | `A0000000041010 vs A0000000041010` | EMV-AID-DFNAME | **PASS** | Terminal AID and DF Name should identify the same application. | EMV Book 1 |
| Seg 130 | Tag 5F2A / 9F1A | 190 | `0840 / 0840` | SEG130-R-009 | **PASS** | USD currency (0840) and US terminal country (0840). | App. R-1 example; SEG130-R-009 |
| Seg 130 | Element 188 value vs Tag 5F34 | 188/190 | `001 vs 01` | E188-5F34 | **PASS** | Element 188 must carry the chip's Tag 5F34 value (wire form '001'; width checked separately). | 13 E188 (p.470) |
| Seg 130 | Tag 9F27 (CID) | 190 | `80` | EMV-CID-ARQC | **PASS** | Online request => Cryptogram Information Data 80 (ARQC). | EMV Book 3 |
| Seg 130 | Tag 9F26/9F10/9F36/9F37/95 | 190 | `B4302C8AE8B7E91C` | SEG130-R-010 | **REVIEW** | Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM. | SEG130-R-010; App. R |
| Seg 130 | Decoded TLV | 190 | `5F2A=0840 5F34=01 82=5800 84=A0000000041010 95=8000048000 9A=260101 9C=00 9F02=000000001500 9F03=000000000000 9F06=A0000000041010 9F10=010103A40000DAC0 9F1A=0840 9F1E=3132333435363738 9F26=B4302C8AE8B7E91C 9F27=80 9F33=E0F0C8 9F34=5E0302 9F35=22 9F36=01D0 9F37=83EA8927 9F41=012850 9F53=52` | BR-SEG100-APPR-TLV-FORMAT | **PASS** | 22 TLV entries decoded. | 13 E190 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `indicator-format` | BR-SEG100-APPT-INDICATOR-FORMAT | **PASS** | EMV Additional Information Indicator is exactly 3 numeric digits with value '001' or '002'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `length-format` | BR-SEG100-APPT-LENGTH-FORMAT | **PASS** | EMV Additional Information Length is exactly 3 numeric digits in range 001-985. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-id-match` | BR-SEG100-APPT-TABLE-ID-MATCH | **FAIL** | Embedded Table ID 'EMV' must equal the EMV Additional Information Indicator value '001'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `emv-table-data` | BR-SEG100-APPT-EMV-TABLE-DATA | **FAIL** | This rule only applies when Table ID is '001'; found 'EMV'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `carc` | BR-SEG100-APPT-CARC | **NA** | Not applicable: Indicator is '001'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-length-field` | BR-SEG100-APPT-TABLE-LENGTH-FIELD | **REVIEW** | Appendix T states the 'Table Length' field is numeric and fixed-width, but never states the digit count or a concrete value for either table; no independent rule can be asserted without fabricating a width the spec does not provide. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `echo` | BR-SEG100-APPT-ECHO | **REVIEW** | Appendix T-1 states EMV Table Data included in an authorization response must be echoed back on subsequent advice/batch-upload requests, but confirming that echo requires comparing multiple messages across a transaction lifecycle, not a single request/response snapshot. | App. T-1; 13 E191/E192 |
| Seg 130 | EMVAdditionalInformationIndicator | 191 | `001` | E191 | **PASS** | N3; 001 EMV table data (App. T also defines 002 CARC). | 12.20 F7; 13 E191 (p.472) |
| Seg 130 | EMVAdditionalInformationLength | 192 | `006` | E192 | **PASS** | N3 001-985 and equal to the length of Element 118 (6). | 12.20 F8; 13 E192 (p.473) |
| Seg 130 | EMVAdditionalInformation | 118 | `EMVYES` | SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA | **PASS** | Table 001 (EMV Table Data) value must be exactly 6 chars 'EMVYES' or 'EMVNOT' (observed 6 random alphanumeric chars; length should be 006). | App. T-1 (p.694); 13 E118 |
| Seg 130 | EMV Additional Information Section total | - | `12` | SEG130-R-011 | **PASS** | Repeated section total max 2,000 bytes. | 12.20 |
| Seg 130 | SegmentLength (format) | 84 | `0204` | SEG130-R-003/R-004 | **PASS** | N4, range 1-3043. | 12.20 F2; SEG130-R-013 |
| Seg 130 | SegmentLength (value) | 84 | `0204 (computed 204)` | SEG130-R-003/R-004 | **PASS** | Must include Segment Type, all fields and Field Separators: 6 fixed fields each + Field Separator (chip data counted as binary bytes) + EMV Additional Information section + final Field Separator. | 12.20 F2; SEG130-R-013 |
| Message | Total message length | - | `369` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `084` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '084' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `KC57457075622174001 \| TC: KC57457075622174001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [flow_fixed_transaction_code] | - | `0020 \| TC: 0020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `5159670865811317 \| TC: 5159670865811317` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_expiry_date] | - | `3106 \| TC: 3106` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [scenario_parameter_literal] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [flow_carried_value] | - | `413431 \| TC: 413431` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [flow_response_runtime_value] | - | `<ApprovalNumber from step 1 response> \| TC: KDUK67` | TC-TD-FIDELITY | **REVIEW** | TD carries the runtime expression; TC carries a provisional value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [test_data_config_value] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.Segment Type [fixed_value] | - | `130 \| TC: 130` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.Segment Length | 84 | `0204` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '0204' (verified in spec checks). | TC method |
| TC<->TD | EMV Request Data Segment.CA Public Key File Checksum [test_data_config_value] | - | `2012091700000100000102171 \| TC: 2012091700000100000102171` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Card Sequence Number [realistic_emv_card_sequence] | - | `001 \| TC: 001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Chip Data Length [derived_from_linked_field] | - | `147 \| TC: 147` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Chip Data [spec_tlv_composite] | - | `01455F2A0208405F340101820258008407A0000000041010950580000480...(len 294) \| TC: 01455F2A0208405F340101820258008407A0000000041010950580000480...(len 294)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information Indicator [qe_example_value] | - | `001 \| TC: 001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information Length [derived_from_linked_field] | - | `006 \| TC: 006` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information [spec_rule_value] | - | `EMVYES \| TC: EMVYES` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `051 \| TC: 051` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [3] | - | `030 \| TC: 030` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [3] | - | `010 \| TC: 010` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [3] | - | `0100000004 \| TC: 0100000004` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Segment Type | - | `111 \| TC: 111` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Fleet Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Product Code Data Segment | - | `present` | TC-SEGMENT-DECISION | **PASS** | TC sends this segment; TD includes it. | TC request |
| TC<->TD | Product Code Data Segment.Segment Type [fixed_value] | - | `102 \| TC: 102` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Service Level [qe_example_value] | - | `F \| TC: F` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Number of Products [spec_rule_value] | - | `01 \| TC: 01` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Product Code [spec_rule_value] | - | `400 \| TC: 400` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Unit of Measure [spec_rule_value] | - | `U \| TC: U` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Quantity [spec_rule_value] | - | `01 \| TC: 01` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Unit Price [spec_rule_value] | - | `21500 \| TC: 21500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Product Code Data Segment.Product Amount [spec_rule_value] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EBT Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Purchase Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Loyalty Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | SKU Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Moneris Data Segment (Request) | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Tax by Product Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Enhanced Fleet Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Request InComm OTC Market Basket Data | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Network Token Data Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EV Charging Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |

## Test objective and flow checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Objective (G-IT-d32476c4da) | Test objective | - | `Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined` | G-IT-d32476c4da | **NA** | catalog_status=grounded; status=needs_client_input; open_items=["Decline test card or simulator decline trigger for the declined leg.","Decline Code values are defined by BUYPASS (element 26, p.379); Appendix H lists only the codes permitting clerk/customer intervention - the expected Decline Code is an SME/BUYPASS input.","Device behaviour after a declined follow-on (e.g. a declined reversal) beyond displaying the decline is not stated - SME question."] | TC |
| Objective (G-IT-d32476c4da) | TVR byte 3 'Online PIN entered' vs PIN block | 190/33 | `8000048000 / PIN absent` | EMV-TVR-CVM-CONSISTENCY | **REVIEW** | TVR byte 3 bit 3 (0x04) is set (online PIN entered) while Element 33 is empty; TVR is an EMV_PLACEHOLDER - the card simulator value must be consistent with the CVM. | EMV Book 3 Annex C5; 13 E33 |
| TC rule_checks (step 1) | LEG-TXN-TYPE Prompt Code | - | `3` | value_slice_in | **PASS** | Expected to hold; allowed [3]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-ENTRY-MODE 005 | - | `05` | table_slice_in | **PASS** | Expected to hold; allowed [05]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 1) | LEG-EMV-SEGMENT 130 | - | `[100, 102, 111, 130]` | segment_present | **PASS** | Expected to hold. | Segment 130 required for EMV (p.203/206) |
| TC rule_checks (step 2) | LEG-TXN-TYPE Prompt Code | - | `0` | value_slice_in | **PASS** | Expected to hold; allowed [0]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 2) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 2) | LEG-ENTRY-MODE 005 | - | `05` | table_slice_in | **PASS** | Expected to hold; allowed [05]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 1) | LEG-NONFUEL-AMOUNT-MAX-LENGTH | - | `-` | value_length_max | **REVIEW** | Unsupported rule_check op. | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) |
| TC rule_checks (step 2) | LEG-NONFUEL-AMOUNT-MAX-LENGTH | - | `-` | value_length_max | **REVIEW** | Unsupported rule_check op. | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) |
| TC rule_checks (step 1) | CTX-TERMINAL-CLASS 030 | - | `010` | table_slice_in | **PASS** | Expected to hold; allowed [010]. | acceptance context terminal 010 'Attended on premise Card acceptor-operated ECR' (Appendix Q p.693) |
| TC rule_checks (step 1) | CTX-TERMINAL-TYPE 030 | - | `04` | table_slice_in | **PASS** | Expected to hold; allowed [04]. | acceptance context terminal 04 'Attended on premise Card acceptor-operated ECR' (Appendix Q p.693) |
| TC rule_checks (step 2) | CTX-TERMINAL-CLASS 030 | - | `010` | table_slice_in | **PASS** | Expected to hold; allowed [010]. | acceptance context terminal 010 'Attended on premise Card acceptor-operated ECR' (Appendix Q p.693) |
| TC rule_checks (step 2) | CTX-TERMINAL-TYPE 030 | - | `04` | table_slice_in | **PASS** | Expected to hold; allowed [04]. | acceptance context terminal 04 'Attended on premise Card acceptor-operated ECR' (Appendix Q p.693) |
| TC rule_checks (step 1) | SEG-102-REQUIRED 102 | - | `[100, 102, 111, 130]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'All fuel merchants must send fuel and nonfuel product data.' (p.229) [BR-229-11] |
| TC rule_checks (step 1) | SEG-111-REQUIRED 111 | - | `[100, 102, 111, 130]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 1) | SEG-102-TOTAL  | - | `1500 vs 1500` | product_total_equals_amounts | **PASS** | Expected to hold. | the total of product amounts in Segment 102 equals elements 41+58+99+17 of Segment 100 (p.229) |
| TC rule_checks (step 1) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |
| TC rule_checks (step 1) | TABLE-030-VALUE 030 | - | `0100000004` | table_slice_in | **PASS** | Expected to hold; allowed [0100000004]. | TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based transactions (Ex:); DERIVED_FROM_KB: Appendix Q p.693, p.696 entries matching {'attendance': 'attended', 'operator': 'card_acceptor', 'premise': 'on', 'device': 'ecr', 'customer': 'present', 'c |
| TC rule_checks (step 2) | SEG-102-REQUIRED 102 | - | `[100, 102, 111, 130]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'All fuel merchants must send fuel and nonfuel product data.' (p.229) [BR-229-11] |
| TC rule_checks (step 2) | SEG-111-REQUIRED 111 | - | `[100, 102, 111, 130]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 2) | SEG-102-TOTAL  | - | `1500 vs 1500` | product_total_equals_amounts | **PASS** | Expected to hold. | the total of product amounts in Segment 102 equals elements 41+58+99+17 of Segment 100 (p.229) |
| TC rule_checks (step 2) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |
| TC rule_checks (step 2) | TABLE-030-VALUE 030 | - | `0100000004` | table_slice_in | **PASS** | Expected to hold; allowed [0100000004]. | TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based transactions (Ex:); DERIVED_FROM_KB: Appendix Q p.693, p.696 entries matching {'attendance': 'attended', 'operator': 'card_acceptor', 'premise': 'on', 'device': 'ecr', 'customer': 'present', 'c |
| TC link_defs (step 2 <- step 1) | Sequence Number carried | - | `413431 / 413431` | carry_request_field | **PASS** | Value must be identical to step 1. | p.402: Sequence Number must persist in all subsequent transactions associated with the initial authorization request. |
| TC link_defs (step 2 <- step 1) | Approval Number from step 1 response | - | `<ApprovalNumber from step 1 response>` | carry_response_field | **REVIEW** | Correctly left as a runtime link (response values are processor-generated). | p.368: Plan v7 W2: Element 5 (p.368) 'If it is included in a Purchase Request, then the Purchase Request is considered preauthorized' - the Authorization Only response's Approval Number (runtime link). |
| TC link_defs (step 2 <- step 1) | Terminal Identifier carried | - | `KC57457075622174001 / KC57457075622174001` | carry_request_field | **PASS** | Value must be identical to step 1. | p.40: Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching). |
| Flow (step 2 purchase of step 1) | AccountNumber = original | 2 | `5159670865811317 / 5159670865811317` | FOLLOW-ON-SAME-CARD | **PASS** | A follow-on must identify the same card as the transaction it matches. | 13 E2 / E5 (matching to the original) |
| Flow (step 2 purchase of step 1) | Expiry = original expiry | 12 | `3106 / 3106` | FOLLOW-ON-SAME-EXPIRY | **PASS** | Follow-on must carry the original card's expiration date in the same form as the original; retries/follow-ons use the same credentials as the initial attempt. | 13 E12 (p.364-365, p.13-32) |
| Flow (step 2 purchase of step 1) | POS entry mode = original | 113 | `051 / 051` | FOLLOW-ON-SAME-ENTRY | **PASS** | Follow-on reports the original's Table 005 entry mode; values must persist throughout the lifecycle (App. J note). | App. J-1 (p.631) note + footnote 1; TC tx_legs |
| Flow (step 2 purchase of step 1) | PartialApprovalIndicator = original | 121 | `1 / 1` | FOLLOW-ON-SAME-PAI | **PASS** | Element 121 is required on all transactions; follow-on keeps the original merchant setting. | 13 E121 (p.440) |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
