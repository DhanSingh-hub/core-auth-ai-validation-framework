# ATL105 Field-Level Test Data Validation - TC-002472

| Item | Value |
|---|---|
| Test case | TC-002472 (BR1_TS1_TC2) |
| Name | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contact Chip Expired EMV card → sent online → host decides |
| Scenario | TS-ATL105-CREDIT-AMERICAN_EXPRESS-PURCHASE-EMV_CONTACT |
| Template / type | T2-EXPIRED-EMV / Card capture path |
| Objective | Expired EMV card -> sent online -> host decides |
| Transaction type | EMV Financial Transaction Request |
| TC status | needs_client_input (catalog: needs_client_input) |
| Open items | ["Expired EMV test card."] |
| Test data file(s) | TD-TC-002472.json |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-03T16:42:27.413111 |
| Overall verdict | **FAIL** |
| Counts | PASS 119 / FAIL 5 / REVIEW 9 / N/A 10 (individual field checks) |

## What is wrong in this test case and how to fix it

**3 defect(s) to fix.** Each one shows where it is in the test data, what the AI generated, why that is wrong, and the value that should be there.

### Defect 1 – EMV Card Sequence Number is 2 characters instead of 3

| | |
|---|---|
| **Where** | `TD-TC-002472.json` line 47 → `EMV Request Data Segment` → `EMVCardSequenceNumber` |
| **What the AI generated** | `01` |
| **What is wrong** | This field must always be exactly 3 characters, padded with zeros on the left. The AI copied the chip's card sequence number (Tag 5F34 = `01`) without padding it. The number itself is right; only the width is wrong, so every field after it is shifted by one position. |
| **What it should be** | `001`. After the fix, add 1 to the Segment 130 `SegmentLength`. |
| **Spec reference** | ATL105 Ch. 13 Element 188 (p.470): *"Fixed length of three bytes, right-aligned and padded with zeroes … Valid Codes/Values: 000—099"* |

### Defect 2 – EMV Additional Information contains random text instead of `EMVYES` / `EMVNOT`

| | |
|---|---|
| **Where** | `TD-TC-002472.json` line 52 → `EMV Request Data Segment` → `EMVAdditionalInformation` |
| **What the AI generated** | `UUZPL4ZEX557C41THEQ0SGE09OXHWQBHWVN46H5LCRQWU4A9M0DHJ2O5OR72...(len 984)` |
| **What is wrong** | `EMVAdditionalInformationIndicator` = `001` tells the host that this field holds *EMV Table Data*. For that table only two values exist: `EMVYES` or `EMVNOT` (6 characters). The AI ignored the allowed values and filled the field to its maximum size (984) with random characters, which the host cannot interpret. |
| **What it should be** | `EMVAdditionalInformation` = `EMVYES` (or `EMVNOT`), `EMVAdditionalInformationLength` = `006`, and the Segment 130 `SegmentLength` recomputed. (Whether a Table ID/Table Length prefix is also needed is an open SME question – see the findings summary Q-01.) |
| **Spec reference** | Element 191: `001` = EMV table data → Element 118: *"EMV table data is included here … refer to Appendix T"* → Appendix T-1 (p.694): EMV Table Data request values **EMVYES / EMVNOT** |

### Defect 3 – "Expired card" test uses a card that is not expired

| | |
|---|---|
| **Where** | `TD-TC-002472.json` line 15 → `Standard Message Data Segment` → `CardDiscretionaryBlockData` |
| **What the AI generated** | Expiry `2808` (YYMM) = August 2028 |
| **What is wrong** | The purpose of this test is to prove that an **expired** chip card is still sent online and the host decides. The card expiry in the Track 2 data (first 4 digits of `CardDiscretionaryBlockData`) is August 2028, which is in the future, so the card is valid and the test cannot prove what it claims. |
| **What it should be** | A card whose Track 2 expiry is before November 2026 (for example `2510…` = October 2025). The chip data should also contain Tag 5F24 (expiry date) with the same date, and the TVR should flag 'expired application'. The test case lists 'Expired EMV test card' as an open item, so use the client's expired test card. |
| **Spec reference** | ATL105 p.160 (device sends expired EMV cards online); Ch. 13 Element 12: *"merchants must send a value even for expired cards"* |

### Items to confirm (not defects)

These cannot be decided from the specification alone. They do not make the test data wrong.

| Item | Why it is open | Who confirms |
|---|---|---|
| Terminal ID / merchant number | The value is a placeholder. BUYPASS assigns the real device type, merchant and device number. | Client merchant list |
| CA Public Key File Checksum | This is a sample value. It must equal the checksum of the CA key file currently loaded at the host. | Host / key management team |
| Chip data length prefix | The 2-byte length prefix at the start of EMVChipData matches the spec example, but the spec does not state explicitly that it is counted in EMVChipDataLength. | SME |
| Chip tags vs Segment 100 values | Appendix R does not state this rule. The important pairs (amount, transaction type, currency) are checked separately in this report. | Informational |
| Cryptogram, ATC, unpredictable number, TVR | These are dummy chip values. A real card or card simulator must produce them. | Card simulator |
| Table Length inside EMV Additional Information | Appendix T does not say how many digits this field has. | BUYPASS / SME |
| Echo of EMV table data | The value must be echoed in later messages. This can only be checked across a full run. | Execution |
| TVR says "online PIN entered" but no PIN is sent | The TVR is a dummy value. Once simulated, it must agree with the cardholder verification method. | Card simulator |
| TVR does not flag the expired card | The TVR is a dummy value. For an expired card it must set 'expired application'. | Card simulator |

---

# Technical evidence

The tables below are the validator's raw checks behind the explanations above.

## Failing checks (FAIL)

| # | Area | Field | Element | Value | Rule | Finding | Spec source |
|---|---|---|---|---|---|---|---|
| 1 | Seg 130 | EMVCardSequenceNumber | 188 | `01` | SEG130-R-006/E188 | AN, FIXED length 3, right-aligned zero-padded (000-099) or 3 spaces when Tag 5F34 absent; '01' is 2 byte(s) - expected '001'. | 12.20 F4; 13 E188 (p.470) |
| 2 | Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-id-match` | BR-SEG100-APPT-TABLE-ID-MATCH | Embedded Table ID 'UUZ' must equal the EMV Additional Information Indicator value '001'. | App. T-1; 13 E191/E192 |
| 3 | Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `emv-table-data` | BR-SEG100-APPT-EMV-TABLE-DATA | This rule only applies when Table ID is '001'; found 'UUZ'. | App. T-1; 13 E191/E192 |
| 4 | Seg 130 | EMVAdditionalInformation | 118 | `UUZPL4ZEX557C41THEQ0SGE09OXHWQBHWVN46H5LCRQWU4A9M0DHJ2O5OR72...(len 984)` | SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA | Table 001 (EMV Table Data) value must be exactly 6 chars 'EMVYES' or 'EMVNOT' (observed 984 random alphanumeric chars; length should be 006). | App. T-1 (p.694); 13 E118 |
| 5 | Objective (T2-EXPIRED-EMV) | Card expiry for expired-card test | 12 | `2808` | T2-EXPIRED-EMV | Expired-card scenario requires Track 2 expiry (YYMM) earlier than 2611; the device must still send it online (p.160, 13 E12 'send a value even for expired cards'). | ATL105 p.160; 13 E12 |

## Review checks (REVIEW)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 130 | CAPublicKeyFileChecksum.Value | 187 | `2012091700000100000102171` | SEG130-R-015 | **REVIEW** | Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a QE-sample (library_provisional) - confirm against the host key file. | 13 E187; App. S |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-PACKED-PREFIX-BOUNDARY | **REVIEW** | Packed-BCD prefix matches the following payload byte count; producer field boundary remains unconfirmed. | App. R-1; 13 E190 |
| Seg 130 | EMVChipData (Appendix R oracle) | 190 | `-` | BR-SEG100-APPR-CROSS-FIELD | **REVIEW** | Agreement between decoded EMV tag values and the corresponding Segment 100 elements is not stated anywhere in Appendix R or in Element 189/190's own definitions, so it is not independently assertable from them alone. | App. R-1; 13 E190 |
| Seg 130 | Tag 9F26/9F10/9F36/9F37/95 | 190 | `B4302C8AE8B7E91C` | SEG130-R-010 | **REVIEW** | Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM. | SEG130-R-010; App. R |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-length-field` | BR-SEG100-APPT-TABLE-LENGTH-FIELD | **REVIEW** | Appendix T states the 'Table Length' field is numeric and fixed-width, but never states the digit count or a concrete value for either table; no independent rule can be asserted without fabricating a width the spec does not provide. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `echo` | BR-SEG100-APPT-ECHO | **REVIEW** | Appendix T-1 states EMV Table Data included in an authorization response must be echoed back on subsequent advice/batch-upload requests, but confirming that echo requires comparing multiple messages across a transaction lifecycle, not a single request/response snapshot. | App. T-1; 13 E191/E192 |
| Objective (T2-EXPIRED-EMV) | TVR byte 3 'Online PIN entered' vs PIN block | 190/33 | `8000048000 / PIN absent` | EMV-TVR-CVM-CONSISTENCY | **REVIEW** | TVR byte 3 bit 3 (0x04) is set (online PIN entered) while Element 33 is empty; TVR is an EMV_PLACEHOLDER - the card simulator value must be consistent with the CVM. | EMV Book 3 Annex C5; 13 E33 |
| Objective (T2-EXPIRED-EMV) | TVR 'Expired application' bit | 190 | `8000048000` | EMV-TVR-EXPIRED | **REVIEW** | Expired card => TVR byte 2 bit 7 (0x40) 'Expired application' should be set (not set); Tag 5F24 absent from chip data. TVR is an EMV_PLACEHOLDER. | EMV Book 3 Annex C5 |

## Leg 1: `TD-TC-002472.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `EMV Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'EMV Financial Transaction Request' (role purchase, Transaction Type '0') => physical message 'EMV Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55-FINANCIAL-REQUEST | **PASS** | Fixed value 'ATL105' (AN6). | ATL105 11.8.1 F1; 13 E55 |
| Data Section 1 | NumberofSegments | 63 | `03` | E63-EMV-FINANCIAL-REQUEST-COUNT | **PASS** | N up to 2 digits; must equal segments present (3: [100, 111, 130]). | ATL105 11.8.1 F2; 13 E63 |
| Data Section 1 | Section 3 slots | - | `2` | SEG130-R-018 | **PASS** | EMV request Data Section 3 occupies Field Nos. 4-8 (1..5 segments). | ATL105 11.1 / 11.8.1 p.11-43 |
| Segment presence | Standard Message Data Segment (100) | - | `present` | SEG-100-PRESENCE | **PASS** | Required in Data Section 2 of every Financial Transaction Request. TC decision: REQUIRED. | ATL105 11.1 / 11.8.1 p.11-42; 12.1 |
| Segment presence | EMV Request Data Segment (130) | - | `present` | SEG-130-PRESENCE | **PASS** | Always appears in Data Section 3 of an EMV request. TC decision: REQUIRED. | ATL105 12.20; SEG130-R-001 |
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
| Seg 100 | AccountNumber | 2 | `348632195076683` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `348632195076683` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `348632` | PAN-IIN-AMEX | **PASS** | American Express IIN 34 / 37, PAN length 15 (TC network AMERICAN_EXPRESS). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `280820136098417` | SEG100-R-042 | **PASS** | AN up to 51; Track 2 data after '=' => YYMM expiry + 3-digit service code + discretionary. | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `2808` | TRACK2-EXPIRY-YYMM | **PASS** | Expiry YYMM; month '08' must be 01-12 (valid, not expired as of 2611). | 13 E12; Track 2 (ISO 7813) |
| Seg 100 | CardDiscretionaryBlockData.ServiceCode | 12 | `201` | TRACK2-SERVICE-CODE-CHIP | **PASS** | Service code 1st digit 2/6 = chip card (required for chip read / chip fallback, POS entry 05). | ISO 7813 |
| Seg 100 | Account+Discretionary (Track 2) | 2+12 | `31` | TRACK2-COMBINED-37 | **PASS** | Account Number + '=' + Card Discretionary Block Data must not exceed 37 for Track 2. | 13 E12 (p.364) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `1500` | SEG100-E58-FORMAT | **PASS** | N, up to 8 digits for AMEX (8 only for American Express), 2 assumed decimals, > 0. | 13 E58 (p.384) |
| Seg 100 | SequenceNumber | 86 | `024721` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `` | BR-368-2 | **PASS** | Must be empty on a new Purchase request (an Approval Number marks it preauthorized). | 13 E5 (p.368) |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (format) | 84 | `088` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `088 (computed 88)` | E84-SEG100-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: sum of transmitted fields (17) + one Field Separator after each. | 12.1 F2; 11.8.1 max 619 |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `051` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `051` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '05' = ICC read - CVV data reliable; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `05` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '05' (ICC read - CVV data reliable) for entry_mode EMV_CONTACT. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `17` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `026` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `026 (computed 26)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Seg 130 | Field layout (9 fields) | - | `9 keys` | LAYOUT-Seg130 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 130 | SegmentType | 85 | `130` | SEG130-R-002 | **PASS** | N3, fixed value 130. | 12.20 F1 |
| Seg 130 | CAPublicKeyFileChecksum | 187 | `2012091700000100000102171` | SEG130-R-005 | **PASS** | N, fixed length 25; required in the EMV Financial Transaction Request (13 E187). | 12.20 F3; 13 E187 (p.469) |
| Seg 130 | CAPublicKeyFileChecksum.Value | 187 | `2012091700000100000102171` | SEG130-R-015 | **REVIEW** | Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a QE-sample (library_provisional) - confirm against the host key file. | 13 E187; App. S |
| Seg 130 | EMVCardSequenceNumber | 188 | `01` | SEG130-R-006/E188 | **FAIL** | AN, FIXED length 3, right-aligned zero-padded (000-099) or 3 spaces when Tag 5F34 absent; '01' is 2 byte(s) - expected '001'. | 12.20 F4; 13 E188 (p.470) |
| Seg 130 | EMVChipDataLength | 189 | `145` | SEG130-R-007 | **PASS** | N3, 000-999. | 12.20 F5; 13 E189 |
| Seg 130 | EMVChipData | 190 | `01435F2A0208405F340101820258008406A0000000250195058000048000...(len 290)` | SEG130-R-008 | **PASS** | ANSB up to 999 bytes; hex-nibble representation of binary TLV (145 bytes). | 12.20 F6; 13 E190 (p.471) |
| Seg 130 | EMVChipDataLength vs EMVChipData | 189/190 | `145 vs 145` | BR-SEG100-APPR-CHIP-LENGTH | **PASS** | Element 189 must equal the byte length of Element 190. | 13 E189/E190 |
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
| Seg 130 | AID (9F06/84) | 190 | `A00000002501` | BR-SEG100-APPR-MANDATORY-IDENTIFIER | **PASS** | AID present; AMEX RID A000000025 expected for network AMEX. | 13 E190; App. R |
| Seg 130 | Tag 9F06 vs Tag 84 | 190 | `A00000002501 vs A00000002501` | EMV-AID-DFNAME | **PASS** | Terminal AID and DF Name should identify the same application. | EMV Book 1 |
| Seg 130 | Tag 5F2A / 9F1A | 190 | `0840 / 0840` | SEG130-R-009 | **PASS** | USD currency (0840) and US terminal country (0840). | App. R-1 example; SEG130-R-009 |
| Seg 130 | Element 188 value vs Tag 5F34 | 188/190 | `01 vs 01` | E188-5F34 | **PASS** | Element 188 must carry the chip's Tag 5F34 value (wire form '001'; width checked separately). | 13 E188 (p.470) |
| Seg 130 | Tag 9F27 (CID) | 190 | `80` | EMV-CID-ARQC | **PASS** | Online request => Cryptogram Information Data 80 (ARQC). | EMV Book 3 |
| Seg 130 | Tag 9F26/9F10/9F36/9F37/95 | 190 | `B4302C8AE8B7E91C` | SEG130-R-010 | **REVIEW** | Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM. | SEG130-R-010; App. R |
| Seg 130 | Decoded TLV | 190 | `5F2A=0840 5F34=01 82=5800 84=A00000002501 95=8000048000 9A=260101 9C=00 9F02=000000001500 9F03=000000000000 9F06=A00000002501 9F10=010103A40000DAC0 9F1A=0840 9F1E=3132333435363738 9F26=B4302C8AE8B7E91C 9F27=80 9F33=E0F0C8 9F34=5E0302 9F35=22 9F36=01D0 9F37=A0ABC79C 9F41=012850 9F53=52` | BR-SEG100-APPR-TLV-FORMAT | **PASS** | 22 TLV entries decoded. | 13 E190 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `indicator-format` | BR-SEG100-APPT-INDICATOR-FORMAT | **PASS** | EMV Additional Information Indicator is exactly 3 numeric digits with value '001' or '002'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `length-format` | BR-SEG100-APPT-LENGTH-FORMAT | **PASS** | EMV Additional Information Length is exactly 3 numeric digits in range 001-985. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-id-match` | BR-SEG100-APPT-TABLE-ID-MATCH | **FAIL** | Embedded Table ID 'UUZ' must equal the EMV Additional Information Indicator value '001'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `emv-table-data` | BR-SEG100-APPT-EMV-TABLE-DATA | **FAIL** | This rule only applies when Table ID is '001'; found 'UUZ'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `carc` | BR-SEG100-APPT-CARC | **NA** | Not applicable: Indicator is '001'. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `table-length-field` | BR-SEG100-APPT-TABLE-LENGTH-FIELD | **REVIEW** | Appendix T states the 'Table Length' field is numeric and fixed-width, but never states the digit count or a concrete value for either table; no independent rule can be asserted without fabricating a width the spec does not provide. | App. T-1; 13 E191/E192 |
| Seg 130 | EMV Additional Information (Appendix T oracle) | 191/192/118 | `echo` | BR-SEG100-APPT-ECHO | **REVIEW** | Appendix T-1 states EMV Table Data included in an authorization response must be echoed back on subsequent advice/batch-upload requests, but confirming that echo requires comparing multiple messages across a transaction lifecycle, not a single request/response snapshot. | App. T-1; 13 E191/E192 |
| Seg 130 | EMVAdditionalInformationIndicator | 191 | `001` | E191 | **PASS** | N3; 001 EMV table data (App. T also defines 002 CARC). | 12.20 F7; 13 E191 (p.472) |
| Seg 130 | EMVAdditionalInformationLength | 192 | `984` | E192 | **PASS** | N3 001-985 and equal to the length of Element 118 (984). | 12.20 F8; 13 E192 (p.473) |
| Seg 130 | EMVAdditionalInformation | 118 | `UUZPL4ZEX557C41THEQ0SGE09OXHWQBHWVN46H5LCRQWU4A9M0DHJ2O5OR72...(len 984)` | SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA | **FAIL** | Table 001 (EMV Table Data) value must be exactly 6 chars 'EMVYES' or 'EMVNOT' (observed 984 random alphanumeric chars; length should be 006). | App. T-1 (p.694); 13 E118 |
| Seg 130 | EMV Additional Information Section total | - | `990` | SEG130-R-011 | **PASS** | Repeated section total max 2,000 bytes. | 12.20 |
| Seg 130 | SegmentLength (format) | 84 | `1179` | SEG130-R-003/R-004 | **PASS** | N4, range 1-3043. | 12.20 F2; SEG130-R-013 |
| Seg 130 | SegmentLength (value) | 84 | `1179 (computed 1179)` | SEG130-R-003/R-004 | **PASS** | Must include Segment Type, all fields and Field Separators: 6 fixed fields each + Field Separator (chip data counted as binary bytes) + EMV Additional Information section + final Field Separator. | 12.20 F2; SEG130-R-013 |
| Entry mode | Table 005 vs Element 12 content | 113/12 | `051 / 280820136098417` | APPJ-READ-HAS-TRACK | **PASS** | PAN entry '05' (ICC read - CVV data reliable) means the card was read; the original request must carry the unaltered Track 2 data after '='. | App. J-1; 13 E12 (p.364) |
| Message | Total message length | - | `1303` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `088` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '088' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `JX72948216050257001 \| TC: JX72948216050257001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [scenario_parameter_literal] | - | `0020 \| TC: 0020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `348632195076683 \| TC: 348632195076683` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_track2_discretionary] | - | `280820136098417 \| TC: 280820136098417` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [scenario_parameter_literal] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [qe_example_value] | - | `024721 \| TC: 024721` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [test_data_config_value] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.Segment Type [fixed_value] | - | `130 \| TC: 130` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.Segment Length | 84 | `1179` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '1179' (verified in spec checks). | TC method |
| TC<->TD | EMV Request Data Segment.CA Public Key File Checksum [test_data_config_value] | - | `2012091700000100000102171 \| TC: 2012091700000100000102171` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Card Sequence Number [realistic_emv_card_sequence] | - | `01 \| TC: 01` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Chip Data Length [derived_from_linked_field] | - | `145 \| TC: 145` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Chip Data [spec_tlv_composite] | - | `01435F2A0208405F340101820258008406A0000000250195058000048000...(len 290) \| TC: 01435F2A0208405F340101820258008406A0000000250195058000048000...(len 290)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information Indicator [qe_example_value] | - | `001 \| TC: 001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information Length [derived_from_linked_field] | - | `984 \| TC: 984` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | EMV Request Data Segment.EMV Additional Information [format_synthesis] | - | `UUZPL4ZEX557C41THEQ0SGE09OXHWQBHWVN46H5LCRQWU4A9M0DHJ2O5OR72...(len 984) \| TC: UUZPL4ZEX557C41THEQ0SGE09OXHWQBHWVN46H5LCRQWU4A9M0DHJ2O5OR72...(len 984)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `051 \| TC: 051` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Segment Type | - | `111 \| TC: 111` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Fleet Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Product Code Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
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
| Objective (T2-EXPIRED-EMV) | Test objective | - | `Expired EMV card -> sent online -> host decides` | T2-EXPIRED-EMV | **NA** | catalog_status=needs_client_input; status=needs_client_input; open_items=["Expired EMV test card."] | TC |
| Objective (T2-EXPIRED-EMV) | TVR byte 3 'Online PIN entered' vs PIN block | 190/33 | `8000048000 / PIN absent` | EMV-TVR-CVM-CONSISTENCY | **REVIEW** | TVR byte 3 bit 3 (0x04) is set (online PIN entered) while Element 33 is empty; TVR is an EMV_PLACEHOLDER - the card simulator value must be consistent with the CVM. | EMV Book 3 Annex C5; 13 E33 |
| Objective (T2-EXPIRED-EMV) | Card expiry for expired-card test | 12 | `2808` | T2-EXPIRED-EMV | **FAIL** | Expired-card scenario requires Track 2 expiry (YYMM) earlier than 2611; the device must still send it online (p.160, 13 E12 'send a value even for expired cards'). | ATL105 p.160; 13 E12 |
| Objective (T2-EXPIRED-EMV) | TVR 'Expired application' bit | 190 | `8000048000` | EMV-TVR-EXPIRED | **REVIEW** | Expired card => TVR byte 2 bit 7 (0x40) 'Expired application' should be set (not set); Tag 5F24 absent from chip data. TVR is an EMV_PLACEHOLDER. | EMV Book 3 Annex C5 |
| TC rule_checks (step 1) | LEG-TXN-TYPE Prompt Code | - | `0` | value_slice_in | **PASS** | Expected to hold; allowed [0]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-ENTRY-MODE 005 | - | `05` | table_slice_in | **PASS** | Expected to hold; allowed [05]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 1) | LEG-EMV-SEGMENT 130 | - | `[100, 111, 130]` | segment_present | **PASS** | Expected to hold. | Segment 130 required for EMV (p.203/206) |
| TC rule_checks (step 1) | SEG-111-REQUIRED 111 | - | `[100, 111, 130]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 1) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
