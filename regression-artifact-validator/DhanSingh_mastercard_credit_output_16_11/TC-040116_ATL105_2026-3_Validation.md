# TC-040116 - validation of the test case and its test data against ATL105 release 2026-3

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) EMV Contact Chip Authorization Only with Tax Type 1 = GST → approved**

| Item | Value |
|---|---|
| Test case | TC-040116 (BR1_TS11_TC57) - [test_cases.jsonl line 351](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) (the whole test case is on this line) |
| Test data | [TD-TC-040116.json](mastercard_credit_new_16_11/test_data/TD-TC-040116.json) |
| Objective | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) |
| Type / status | Decision table - Positive - status `ready` |
| Expected result | Step 1 sends Prompt Code `3020` (POS Authorization Only), expects Response Code `2` (ASSERT) |
| Specification | [BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf) - BUYPASS Platform ATL105 Message Format Specifications, release **2026-3** (August 07, 2026), 722 PDF pages |
| How the references were checked | Every ATL105 text below was found word for word (letters and digits) on the stated page of the 2026-3 text ([extracted_text.txt](../specifications/ATL105/docs/specs/extracted_text.txt), 722 pages) and, page by page, in the text read directly from the 2026-3 PDF with pypdf. PDF p. = page in the PDF viewer; printed = page number in the page footer. Click a reference to see the exact text; the line link opens it in the text file. |

<a id="v"></a>
## 1. Result

**FAIL - the test data is well-formed, but it does not contain what the test case says it tests.**

| What | Result |
|---|---|
| Fields sent (Data Section 1, Segments 100, 111, 130) | Every field has the right format, length and code for an EMV contact chip Authorization Only ([section 3](#s3)) |
| Tax Type 1 = GST (the condition the test claims) | **Missing** - Segment 102 (Product Code) and Segment 143 (Tax by Product) are not in the request, so GST is never sent ([section 5](#s5)) |
| Values that are placeholders | Terminal Identifier, CA Public Key File Checksum and the chip cryptogram data were generated; they must be replaced before the test can run ([section 3](#s3), [section 4](#s4)) |
| Test case record | Status `ready` with a placeholder terminal; one citation on the wrong page ([section 6](#s6)) |
| To confirm | GST is a Canadian tax type, but the chip data says US terminal and US dollar ([section 4](#s4)) |

<a id="s2"></a>
## 2. What the test case claims and what the request sends

| | The test case says | The test data sends | Match? |
|---|---|---|---|
| Transaction | Authorization Only (objective and name) | Prompt Code `3020`: Transaction Type `3` = POS Authorization Only, Card Type `020` | Yes - [Element 78, p.393 (13-60)](#r3-el78)<br>[Appendix G, p.538 (App. G-1)](#r3-appg-3)<br>[Appendix E, p.514 (App. E-4)](#r3-appe-020) |
| Entry mode | EMV Contact Chip | Table 005 `051` (05 = chip read) and Segment 130 (EMV) | Yes - [Appendix J, p.631 (App. J-1)](#r3-appj-05)<br>[Section 11.8.1, p.202 (11-42)](#r3-s11-8-emv) |
| Condition | Tax Type 1 = GST ("the rule's condition holds") | Segments sent: 100, 111, 130 - no Segment 102, no Segment 143, no Tax Type anywhere | **No** - [Section 12.30, p.282 (12-68)](#r3-s12-30-102)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can)<br>[Section 11.1, p.165 (11-5)](#r3-s11-1-143) |
| Expected result | Response Code `2` (Approved - Authorization only) | - | [Element 83, p.397 (13-64)](#r3-el83-2) |

An approval of this request would only prove that a plain EMV Authorization Only is approved - not that a request with Tax Type 1 = GST is handled. ATL105 also says the host only checks the format of Segment 143 ([Section 12.30, p.282 (12-68)](#r3-s12-30-format)), so even a correct GST request proves format acceptance, not tax processing.

<a id="s3"></a>
## 3. Every field of the test data, checked against ATL105 2026-3

File: [TD-TC-040116.json](mastercard_credit_new_16_11/test_data/TD-TC-040116.json) (root `EMV Financial Transaction Request`). *Test case* = the value and the generator's method recorded for the field in the test case (the page it cites is a **2026-4** page number - see [section 8](#s8)).

| # | Segment | Field | Element | Name | Value in the test data | Test case: value / how generated | Result | Why (the check made) | ATL105 2026-3 reference |
|---|---|---|---|---|---|---|---|---|---|
| 1 | Message | - | - | Message type | `EMV Financial Transaction Request` | -<br>- | Correct | Segment 130 is sent only in an EMV Financial Transaction Request | [Section 12.20, p.267 (12-53)](#r3-s12-20-always)<br>[Section 11.8.1, p.202 (11-42)](#r3-s11-8-emv) |
| 2 | Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105`<br>- | Correct | Fixed 6 characters: ATL105 | [Section 11.1, p.164 (11-4)](#r3-s11-1-ds1)<br>[Element 55, p.384 (13-51)](#r3-el55) |
| 3 | Data Section 1 | 2 | 63 | Number of Segments | `03` | -<br>- | Correct for what is sent - **becomes 05** | Up to 2 digits; equals the number of segments sent (3)<br>**But:** once Segments 102 and 143 are added (section 5) the message has 5 segments, so this must be `05`. | [Section 11.1, p.164 (11-4)](#r3-s11-1-ds1)<br>[Element 63, p.388 (13-55)](#r3-el63) |
| 4 | 100 | 1 | 85 | Segment Type | `100` | `100`<br>`fixed_value`, cites p.223 | Correct | Fixed value 100 | [Section 12.1, p.220 (12-6)](#r3-s12-1-type) |
| 5 | 100 | 2 | 84 | Segment Length | `089` | -<br>`derived_at_wire_encoding`, cites p.223 - DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered … | Correct | Length of all 17 fields + 17 Field Separators = 089 | [Section 12.1, p.220 (12-6)](#r3-s12-1-len)<br>[Element 84, p.399 (13-66)](#r3-el84) |
| 6 | 100 | 3 | 44 | Information Byte | `0` | `0`<br>`test_data_config_value`, cites p.223 - QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is … | Correct | 1 character: '0' = single-message transmission | [Element 44, p.379 (13-46)](#r3-el44) |
| 7 | 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001`<br>`realistic_terminal_id`, cites p.223 - TERMINAL_ID_PER_MERCHANT: one Terminal Identifier per merchant terminal (element 102, p.417); State Code = … | Format correct - **placeholder value** | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001'<br>**But:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. | [Element 102, p.410 (13-77)](#r3-el102)<br>[Element 102, p.410 (13-77)](#r3-el102-state)<br>[Appendix D, p.510 (App. D-4)](#r3-appd-72) |
| 8 | 100 | 5 | 78 | Prompt Code | `3020` | `3020`<br>`scenario_parameter_literal`, cites p.223 - AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4 | Correct | Transaction Type '3' = POS Authorization Only; Card Type '020' = Credit | [Element 78, p.393 (13-60)](#r3-el78)<br>[Appendix G, p.538 (App. G-1)](#r3-appg-3)<br>[Appendix E, p.514 (App. E-4)](#r3-appe-020) |
| 9 | 100 | 6 | 2 | Account Number | `5178588476140867` | `5178588476140867`<br>`realistic_pan`, cites p.224 | Correct | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | [Element 2, p.357 (13-24)](#r3-el2) |
| 10 | 100 | 7 | 12 | Card Discretionary Block Data | `320120108983647` | `320120108983647`<br>`realistic_track2_discretionary` - CARD_DATA_TRACK2: Track 2 read - Account Number = data before '=', Card Discretionary Block Data = data after … | Correct | Track 2 data: expiry 3201 = January 2032; service code 201 (chip card, ISO/IEC 7813); Account Number + '=' + this field = 32 characters (at most 37) | [Element 12, p.364 (13-31)](#r3-el12-track2)<br>[Element 12, p.364 (13-31)](#r3-el12-max) |
| 11 | 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Optional - not sent (the Field Separator is still sent) | [Section 12.1, p.220 (12-6)](#r3-s12-1-fs) |
| 12 | 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Optional - not sent (the Field Separator is still sent) | [Section 12.1, p.220 (12-6)](#r3-s12-1-fs) |
| 13 | 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Optional - not sent (the Field Separator is still sent) | [Section 12.1, p.220 (12-6)](#r3-s12-1-fs) |
| 14 | 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500`<br>`scenario_parameter_literal`, cites p.224 | Correct | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | [Element 58, p.386 (13-53)](#r3-el58) |
| 15 | 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Optional - not sent (the Field Separator is still sent) | [Section 12.1, p.220 (12-6)](#r3-s12-1-fs) |
| 16 | 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Optional - not sent (the Field Separator is still sent) | [Section 12.1, p.220 (12-6)](#r3-s12-1-fs) |
| 17 | 100 | 14 | 86 | Sequence Number | `401161` | `401161`<br>`qe_example_value`, cites p.224 - SEQUENCE_ALLOCATED: unique per terminal across the run set; follow-ons carry the original's number (element … | Correct | 6 digits, 000001-999999 | [Element 86, p.402 (13-69)](#r3-el86-fmt) |
| 18 | 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Not sent on a new transaction | [Element 5, p.361 (13-28)](#r3-el5-pre) |
| 19 | 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)`<br>`omitted` - CONDITIONAL_FIELD_OMITTED | Correct | Optional - not sent (the Field Separator is still sent) | [Section 12.1, p.220 (12-6)](#r3-s12-1-fs) |
| 20 | 100 | 17 | 121 | Partial Approval Indicator | `1` | `1`<br>`test_data_config_value`, cites p.225 - QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered … | Correct | '1' = partial approval supported | [Element 121, p.433 (13-100)](#r3-el121) |
| 21 | 111 | 1 | 85 | Segment Type | `111` | `111`<br>`fixed_value`, cites p.246 | Correct | Fixed value 111 | [Section 12.10, p.243 (12-29)](#r3-s12-10-type) |
| 22 | 111 | 2 | 84 | Segment Length | `042` | -<br>`derived_at_wire_encoding`, cites p.246 - DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered … | Correct | 3 (type) + 3 (length) + 33 (tables) + 3 Field Separators = 042 | [Section 12.10, p.243 (12-29)](#r3-s12-10-fs)<br>[Element 84, p.399 (13-66)](#r3-el84) |
| 23 | 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `005`<br>`axis_table_placement`, cites p.555 - AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='05' (entry_mode), positions [3, 3]='1' … | Correct | 3-character table ID (Table 005) | [Element 111, p.415 (13-82)](#r3-el111) |
| 24 | 111 | 4 [1] | 112 | Variable Information Length | `003` | `003`<br>`axis_table_placement`, cites p.555 - AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='05' (entry_mode), positions [3, 3]='1' … | Correct | 3 digits = length of the table data (003) | [Section 12.10, p.243 (12-29)](#r3-s12-10-len) |
| 25 | 111 | 5 [1] | 113 | Variable Information (Table 005) | `051` | `051`<br>`axis_table_placement`, cites p.555 - AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='05' (entry_mode), positions [3, 3]='1' … | Correct | '05' = chip read; terminal capability '1' = PIN entry capability | [Appendix I, p.548 (App. I-6)](#r3-appi-005)<br>[Appendix J, p.631 (App. J-1)](#r3-appj-05)<br>[Appendix J, p.632 (App. J-2)](#r3-appj-pin1) |
| 26 | 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `031`<br>`fixed_value`, cites p.569 - MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p569) | Correct | 3-character table ID (Table 031) | [Element 111, p.415 (13-82)](#r3-el111) |
| 27 | 111 | 4 [2] | 112 | Variable Information Length | `002` | `002`<br>`derived_from_table_data` | Correct | 3 digits = length of the table data (002) | [Section 12.10, p.243 (12-29)](#r3-s12-10-len) |
| 28 | 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12`<br>`realistic_terminal_capability` - TERMINAL_CAPABILITY: the merchant terminal's certified capability (merchants.json terminal_capability; Table … | Correct | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | [Appendix I, p.562 (App. I-20)](#r3-appi-031)<br>[Appendix I, p.562 (App. I-20)](#r3-appi-031-12) |
| 29 | 111 | 3 [3] | 111 | Variable Information Indicator | `030` | `030`<br>`fixed_value` - TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based … | Correct | 3-character table ID (Table 030) | [Element 111, p.415 (13-82)](#r3-el111) |
| 30 | 111 | 4 [3] | 112 | Variable Information Length | `010` | `010`<br>`derived_from_table_data` | Correct | 3 digits = length of the table data (010) | [Section 12.10, p.243 (12-29)](#r3-s12-10-len) |
| 31 | 111 | 5 [3] | 113 | Variable Information (Table 030) | `0100000004` | `0100000004`<br>`spec_rule_value` - TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based … | Correct | 10 digits (Appendix Q): Terminal Class '0' = attended, '1' = card acceptor-operated, '0' = on premise; Presentation Type '0' = customer present, '0' = card present, '0' = device does not have card retention capability, '0' = original presentment; Security Condition '0' = no security concern; Terminal Type '04' = ECR | [Appendix I, p.561 (App. I-19)](#r3-appi-030)<br>[Appendix Q, p.683 (App. Q-1)](#r3-appq)<br>[Appendix Q, p.684 (App. Q-2)](#r3-appq-class)<br>[Appendix Q, p.689 (App. Q-7)](#r3-appq-pres)<br>[Appendix Q, p.689 (App. Q-7)](#r3-appq-sec)<br>[Appendix Q, p.685 (App. Q-3)](#r3-appq-type)<br>[Appendix Q, p.686 (App. Q-4)](#r3-appq-010-04) |
| 32 | 130 | 1 | 85 | Segment Type | `130` | `130`<br>`fixed_value`, cites p.270 | Correct | Fixed value 130 | [Section 12.20, p.267 (12-53)](#r3-s12-20-type) |
| 33 | 130 | 2 | 84 | Segment Length | `0204` | -<br>`derived_at_wire_encoding`, cites p.270 - DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered … | Correct | 4 digits: Fields 1-6 + 6 Field Separators + EMV Additional Information Section + 1 Field Separator = 0204 (EMV Chip Data counted as 147 binary bytes) | [Section 12.20, p.267 (12-53)](#r3-s12-20-len)<br>[Section 12.20, p.267 (12-53)](#r3-s12-20-fs)<br>[Element 190, p.471 (13-138)](#r3-el190-bin) |
| 34 | 130 | 3 | 187 | CA Public Key File Checksum | `2012091700000100000102171` | `2012091700000100000102171`<br>`test_data_config_value`, cites p.270 | Format correct - **placeholder value** | Numeric, fixed length 25; the checksum from the last CA Public Key File Load<br>**But:** The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value. | [Element 187, p.469 (13-136)](#r3-el187)<br>[Element 187, p.469 (13-136)](#r3-el187-req)<br>[Element 187, p.470 (13-137)](#r3-el187-use) |
| 35 | 130 | 4 | 188 | EMV Card Sequence Number | `001` | `001`<br>`realistic_emv_card_sequence`, cites p.270 - EMV Card Sequence Number = Tag 5F34 of the same card, 3 bytes zero-padded; space-filled when 5F34 is not … | Correct | Fixed 3 bytes, right-aligned, zero-filled; Tag 5F34 = 01 -> '001' | [Element 188, p.470 (13-137)](#r3-el188)<br>[Element 188, p.470 (13-137)](#r3-el188-val) |
| 36 | 130 | 5 | 189 | EMV Chip Data Length | `147` | `147`<br>`derived_from_linked_field`, cites p.270 - EMV Chip Data Length = bytes of EMV Chip Data (2-byte length prefix + TLV data) | Correct | 3 digits = bytes of EMV Chip Data: 147 (2-byte BCD length + 145 bytes of TLV data) | [Element 189, p.470 (13-137)](#r3-el189)<br>[Appendix R, p.690 (App. R-1)](#r3-appr-pkt) |
| 37 | 130 | 6 | 190 | EMV Chip Data | `294 hex characters = 147 bytes, 22 tags` | `01455F2A0208405F... (294)`<br>`spec_tlv_composite`, cites p.271 - EMV_CHIP_DATA_PER_TEST_CASE: AID A0000000041010 for MASTERCARD (Fiserv EMV guide p.44), 9C=00 for AUTH_ONLY … | Format correct - **placeholder value** | 2-byte BCD length + TLV data; Tag 9F06 or 84 present; no Tag 5A or 57<br>**But:** TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator. ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer. | [Element 190, p.471 (13-138)](#r3-el190-tlv)<br>[Element 190, p.471 (13-138)](#r3-el190-aid)<br>[Element 190, p.471 (13-138)](#r3-el190-no5a57)<br>[Appendix R, p.690 (App. R-1)](#r3-appr-pkt)<br>[Element 190, p.471 (13-138)](#r3-el190-exact) |
| 38 | 130 | 6 / 9F02 | 190 | Tag 9F02 Amount, Authorized | `000000001500` | -<br>- | Correct | = Nonfuel Amount 1500 as 12 digits | [Appendix R, p.690 (App. R-1)](#r3-appr-9f02)<br>[Element 190, p.471 (13-138)](#r3-el190-exact) |
| 39 | 130 | 6 / 9F03 | 190 | Tag 9F03 Amount, Other | `000000000000` | -<br>- | Correct | No cash back, so zero (EMV Book 3) | [Element 17, p.368 (13-35)](#r3-el17)<br>[Element 190, p.471 (13-138)](#r3-el190-exact) |
| 40 | 130 | 6 / 9C | 190 | Tag 9C Transaction Type | `00` | -<br>- | Correct | 00 = purchase of goods/services (Transaction Type 3; EMV Book 3) | [Appendix R, p.690 (App. R-1)](#r3-appr-9c)<br>[Element 190, p.471 (13-138)](#r3-el190-exact) |
| 41 | 130 | 6 / 5F2A | 190 | Tag 5F2A Transaction Currency Code | `0840` | -<br>- | Correct for a US terminal - **to confirm** | 0840 = United States (Table 006 default 840)<br>**But:** the test claims Tax Type GST, which ATL105 lists for Canadian transactions, and the placeholder terminal's State Code 72 = Yukon (Canada); confirm the merchant's country and currency. | [Appendix I, p.548 (App. I-6)](#r3-appi-006)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can)<br>[Appendix D, p.510 (App. D-4)](#r3-appd-72) |
| 42 | 130 | 6 / 9F1A | 190 | Tag 9F1A Terminal Country Code | `0840` | -<br>- | Correct for a US terminal - **to confirm** | 0840 = United States (Table 006 default 840)<br>**But:** the test claims Tax Type GST, which ATL105 lists for Canadian transactions, and the placeholder terminal's State Code 72 = Yukon (Canada); confirm the merchant's country and currency. | [Appendix I, p.548 (App. I-6)](#r3-appi-006)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can)<br>[Appendix D, p.510 (App. D-4)](#r3-appd-72) |
| 43 | 130 | 6 / 9F27 | 190 | Tag 9F27 Cryptogram Information Data | `80` | -<br>- | Correct | ARQC (go online) (EMV Book 3) | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 44 | 130 | 6 / 9F34 | 190 | Tag 9F34 CVM Results | `5E0302` | -<br>- | Correct | CVM code 1E = signature (EMV Book 3) | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 45 | 130 | 7 | 191 | EMV Additional Information Indicator | `001` | `001`<br>`qe_example_value`, cites p.271 | Correct | 001 = EMV table data | [Element 191, p.472 (13-139)](#r3-el191) |
| 46 | 130 | 8 | 192 | EMV Additional Information Length | `006` | `006`<br>`derived_from_linked_field`, cites p.271 - EMV Additional Information Length = character count of EMV Additional Information (element 192, p.480) | Correct | 3 digits, 001-985, = length of Element 118 (006) | [Element 192, p.473 (13-140)](#r3-el192) |
| 47 | 130 | 9 | 118 | EMV Additional Information | `EMVYES` | `EMVYES`<br>`spec_rule_value`, cites p.271 - EMV_ADDITIONAL_INFORMATION: indicator 001 content 'EMVYES' (p.701: EMVYES This code indicates that the device … | Correct | Indicator 001 = EMV Table Data: EMVYES or EMVNOT (6 characters) | [Element 118, p.432 (13-99)](#r3-el118-emv)<br>[Appendix T, p.694 (App. T-1)](#r3-appt-1)<br>[Appendix T, p.694 (App. T-1)](#r3-appt-1-not)<br>[Appendix T, p.694 (App. T-1)](#r3-appt-1-len) |
| 48 | 102 | all | - | Product Code Data Segment | (not in the request) | in the test case library, not sent | **Missing** | The objective says Tax Type 1 = GST. Tax Type travels only in Segment 143, and Segment 143 needs Segment 102 - see [section 5](#s5) for every field it must contain. | [Section 12.30, p.282 (12-68)](#r3-s12-30-102)<br>[Section 12.3, p.226 (12-12)](#r3-s12-3-rules)<br>[Section 12.3, p.226 (12-12)](#r3-s12-3-total3) |
| 49 | 143 | all | - | Tax by Product Data Segment | (not in the request) | in the test case library, not sent | **Missing** | The objective says Tax Type 1 = GST. Tax Type travels only in Segment 143, and Segment 143 needs Segment 102 - see [section 5](#s5) for every field it must contain. | [Section 12.30, p.282 (12-68)](#r3-s12-30-rules)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can)<br>[Section 11.1, p.165 (11-5)](#r3-s11-1-143)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-format) |

**Count:** 41 Correct · 1 Correct for what is sent - **becomes 05** · 3 Format correct - **placeholder value** · 2 Correct for a US terminal - **to confirm** · 2 **Missing**.

<a id="s4"></a>
## 4. EMV Chip Data (Element 190) tag by tag

`EMVChipData` starts with `0145` = BCD length 145 bytes of TLV data; with the 2-byte length that is 147 bytes = `EMVChipDataLength` `147` ([Appendix R, p.690 (App. R-1)](#r3-appr-pkt)<br>[Element 189, p.470 (13-137)](#r3-el189)). ATL105 requires the exact values read from the card ([Element 190, p.471 (13-138)](#r3-el190-exact)), a Tag 9F06 or 84 ([Element 190, p.471 (13-138)](#r3-el190-aid)) and no Tag 5A or 57 ([Element 190, p.471 (13-138)](#r3-el190-no5a57)) - none of 5A/57 is present.

| Tag | Name | Value | Meaning | Check | Result | Reference |
|---|---|---|---|---|---|---|
| 5F2A | Transaction Currency Code | `0840` | 840 = United States (US dollar) | Consistent with ATL105's default currency 840; **but** the test claims Tax Type GST, which ATL105 lists for Canadian transactions, and the placeholder terminal's State Code 72 = Yukon (Canada) | **To confirm** | [Appendix I, p.548 (App. I-6)](#r3-appi-006)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can)<br>[Appendix D, p.510 (App. D-4)](#r3-appd-72) |
| 5F34 | PAN Sequence Number | `01` | PAN sequence 1 | = EMV Card Sequence Number `001` | Correct | [Element 188, p.470 (13-137)](#r3-el188)<br>[Element 188, p.470 (13-137)](#r3-el188-val) |
| 82 | Application Interchange Profile | `5800` | Application Interchange Profile | From the card | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 84 | Dedicated File (DF) Name | `A0000000041010` | MasterCard AID (A0000000041010) | MasterCard credit application | Correct | [Element 190, p.471 (13-138)](#r3-el190-aid) |
| 95 | Terminal Verification Results | `8000048000` | offline data authentication was not performed; online PIN entered; transaction exceeds floor limit | **Online PIN entered** is set, but CVM Results 9F34 = 5E0302 = signature and Encrypted PIN Block Data is empty | **Generated - inconsistent** | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9A | Transaction Date | `260101` | 2026-01-01 | Set by the terminal at run time; a fixed generated date here | Generated value | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9C | Transaction Type | `00` | 00 = goods and services | Authorization Only of a purchase | Correct | [Appendix R, p.690 (App. R-1)](#r3-appr-9c) |
| 9F02 | Amount, Authorized | `000000001500` | 15.00 | = Nonfuel Amount `1500` (15.00) | Correct | [Appendix R, p.690 (App. R-1)](#r3-appr-9f02)<br>[Element 58, p.386 (13-53)](#r3-el58) |
| 9F03 | Amount, Other | `000000000000` | 0.00 | No cash back (Cash Amount empty) | Correct | [Element 17, p.368 (13-35)](#r3-el17) |
| 9F06 | Application Identifier (AID) | `A0000000041010` | MasterCard AID (A0000000041010) | MasterCard credit application | Correct | [Element 190, p.471 (13-138)](#r3-el190-aid) |
| 9F10 | Issuer Application Data | `010103A40000DAC0` | generated by the test generator | Must come from the card / simulator | **Generated placeholder** | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F1A | Terminal Country Code | `0840` | 840 = United States | Consistent with ATL105's default currency 840; **but** the test claims Tax Type GST, which ATL105 lists for Canadian transactions, and the placeholder terminal's State Code 72 = Yukon (Canada) | **To confirm** | [Appendix I, p.548 (App. I-6)](#r3-appi-006)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can)<br>[Appendix D, p.510 (App. D-4)](#r3-appd-72) |
| 9F1E | Interface Device Serial Number | `3132333435363738` | ASCII '12345678' | Device serial - generated | Generated value | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F26 | Application Cryptogram | `B4302C8AE8B7E91C` | generated by the test generator | Must come from the card / simulator | **Generated placeholder** | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F27 | Cryptogram Information Data | `80` | ARQC (go online) | An Authorization Only goes online for approval | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F33 | Terminal Capabilities | `E0F0C8` | manual key entry, magnetic stripe, IC with contacts, plaintext PIN for ICC verification, enciphered PIN for online verification, signature, enciphered PIN for offline verification, SDA, DDA, CDA | Supports IC with contacts and signature - matches the chip read and CVM | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F34 | CVM Results | `5E0302` | CVM 5E = signature, condition 03, result 02 | Consistent with the empty Encrypted PIN Block Data (Element 33) | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F35 | Terminal Type | `22` | 22 = attended, offline with online capability, merchant-operated | Matches Table 030 'attended, card acceptor-operated' | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F36 | Application Transaction Counter | `01D0` | generated by the test generator | Must come from the card / simulator | **Generated placeholder** | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F37 | Unpredictable Number | `733C27AF` | generated by the test generator | Must come from the card / simulator | **Generated placeholder** | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F41 | Transaction Sequence Counter | `012850` | - | - | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |
| 9F53 | Transaction Category Code | `52` | 'R' (MasterCard transaction category: retail) | Matches General Retail | Correct | [Element 190, p.471 (13-138)](#r3-el190-exact) |

<a id="s5"></a>
## 5. What is missing - Tax Type 1 = GST (Segments 102 and 143)

**Why it is missing:** the objective is *"Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)"*, but the request contains Segments 100, 111, 130 only. In ATL105 the Tax Type exists only in the Tax by Product Data Segment (No. 143), and that segment requires the Product Code Data Segment (No. 102). Neither is in the request, `NumberofSegments` is `03`, and the test case lists nothing as missing (`open_items` and `awaiting_inputs` are empty) although its status is `ready`.

**ATL105 2026-3 rules that apply:**

- Section 12.30 lists the rules for a request with Segment 143: "When a Financial Transaction request includes the Tax by Product Segment, the following rules should be followed:" - [Section 12.30, p.282 (12-68)](#r3-s12-30-rules)
- Rule 1 - Segment 102 must be present: "A Product Code Data Segment (Data Segment No. 102) must be present in the transaction." - [Section 12.30, p.282 (12-68)](#r3-s12-30-102)
- Rule 2 - same products, same order: "Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments." - [Section 12.30, p.282 (12-68)](#r3-s12-30-order)
- Only the format is validated: "The only validation that will be done for this segment is whether the format is correct. No judgement will be made of the values contained." - [Section 12.30, p.282 (12-68)](#r3-s12-30-format)
- GST is a Canadian tax type: "For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)." - [Section 12.30, p.282 (12-68)](#r3-s12-30-can)
- Section 11.1 - Segment 143 is conditional, limited applicability: "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager." - [Section 11.1, p.165 (11-5)](#r3-s11-1-143)
- Section 12.3 - product amounts must add up to the Segment 100 amounts: "The total of product amounts in the segment must equal the total of the amounts [Element No. 41 (Fuel Purchase Amount), Element No. 58 (Nonfuel Amount), Element No. 99 (Tax Amount) and Element No. 17 (Cash Amount)] in the Standard Message Data Segment" - [Section 12.3, p.226 (12-12)](#r3-s12-3-total3)
- Section 12.3 - tax product codes are reported in Tax Amount: "When tax product codes are included in the segment, the total dollar amount of tax products is also reported in Element No. 99 (Tax Amount)" - [Section 12.3, p.226 (12-12)](#r3-s12-3-taxprod)

**What the request must contain instead** (one product, GST included in the price - the pattern of ATL105's own example [Section 12.30, p.284 (12-70)](#r3-s12-30-ex1)):

Segment 102 - Product Code Data Segment:

| Field | Element | Name | Value | Why | ATL105 2026-3 reference |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | `102` | Fixed value | [Section 12.3, p.227 (12-13)](#r3-s12-3-type) |
| 2 | 84 | Segment Length | `029` | 3 (type) + FS + 3 (length) + FS + data (1+2+3+1+2+\+5+\+4) + FS | [Section 12.3, p.226 (12-12)](#r3-s12-3-fs)<br>[Section 12.3, p.226 (12-12)](#r3-s12-3-delim)<br>[Section 12.3, p.226 (12-12)](#r3-s12-3-last) |
| 3 | 87 | Service Level | `O` | O = Other/Fuel not present (no fuel in this sale) | [Element 87, p.403 (13-70)](#r3-el87-val) |
| 4 | 62 | Number of Products | `01` | One product; single digits are zero-filled | [Element 62, p.387 (13-54)](#r3-el62) |
| 5 | 77 | Product Code | `400` | 400 = General merchandise (client to confirm) | [Appendix F, p.528 (App. F-13)](#r3-appf-400)<br>[Element 77, p.393 (13-60)](#r3-el77) |
| 6 | 106 | Unit of Measure | `U` | U = Unit | [Element 106, p.413 (13-80)](#r3-el106) |
| 7 | 81 | Quantity | `01` | Leading digit = number of decimals: 0 decimals, quantity 1 | [Element 81, p.395 (13-62)](#r3-el81)<br>[Section 12.3, p.227 (12-13)](#r3-s12-3-qty) |
| 8 | 107 | Unit Price | `21500` | Leading 2 = two decimals: 15.00 | [Element 107, p.414 (13-81)](#r3-el107) |
| 9 | 76 | Product Amount | `1500` | 15.00 = Nonfuel Amount, so the product total equals the Segment 100 amounts | [Element 76, p.392 (13-59)](#r3-el76)<br>[Section 12.3, p.226 (12-12)](#r3-s12-3-total3) |

Segment 143 - Tax by Product Data Segment:

| Field | Element | Name | Value | Why | ATL105 2026-3 reference |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | `143` | Fixed value | [Section 12.30, p.282 (12-68)](#r3-s12-30-type) |
| 2 | 84 | Segment Length | `020` | Counted like Segment 102 (type, FS, length, FS, data, FS). ATL105's one-product examples show `023` for 21 characters, so confirm the counting with BUYPASS | [Section 12.30, p.282 (12-68)](#r3-s12-30-len)<br>[Section 12.30, p.284 (12-70)](#r3-s12-30-ex1) |
| 3 | 62 | Number of Products | `01` | Same count as Segment 102 | [Section 12.30, p.283 (12-69)](#r3-s12-30-f3) |
| 4 | 77 | Product Code | `400` | Same product as Segment 102 | [Section 12.30, p.283 (12-69)](#r3-s12-30-f4)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-order) |
| 5 | 223 | Inclusive/Exclusive for Tax 1 | `I` | GST included in the 15.00 price | [Section 12.30, p.283 (12-69)](#r3-s12-30-f5) |
| 6 | 224 | Tax Type 1 | `GST` | The value the test claims; valid for Canadian transactions | [Section 12.30, p.283 (12-69)](#r3-s12-30-f6)<br>[Section 12.30, p.282 (12-68)](#r3-s12-30-can) |
| 7 | 225 | Tax Amount 1 | `71` | Example: 5% GST included in 15.00 = 0.71 (client to supply the real amount) | [Section 12.30, p.283 (12-69)](#r3-s12-30-f7) |

Wire form: `102▲029▲O01400U01\21500\1500▲` and `143▲020▲01400IGST71▲` (▲ = Field Separator). `NumberofSegments` becomes `05` ([Element 63, p.388 (13-55)](#r3-el63)). Segment 100 stays as it is: Nonfuel Amount 1500 = the product total, Tax Amount stays empty because the GST is inclusive and no tax product code is sent ([Section 12.3, p.226 (12-12)](#r3-s12-3-total3)<br>[Section 12.3, p.226 (12-12)](#r3-s12-3-taxprod)<br>[Element 58, p.386 (13-53)](#r3-el58-def)).

<a id="s6"></a>
## 6. The test case record (test_cases.jsonl)

| Check | In the test case | Result | Why | ATL105 2026-3 reference |
|---|---|---|---|---|
| The quote cited for p.161 is on the neighbouring page (Q-02) | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | **Citation issue** | The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160). Fix: Cite p.160 instead of p.161. | - |
| Marked 'ready' although the terminal is a placeholder (C-03) | `status` = ready | **Test case issue to correct** | The test case status is 'ready', but its Terminal Identifier is still a placeholder. Fix: Keep the test case out of 'ready' until the real Terminal Identifier is supplied. | [Element 102, p.410 (13-77)](#r3-el102) |
| Expected Response Code | `tx_legs[0].expect.response_code` = `2` | Correct code for an approved Authorization Only | Element 83: 2 = Approved - Authorization only. Valid only once the request really carries GST | [Element 83, p.397 (13-64)](#r3-el83-2) |

**ATL105 text the test case quotes (`spec_basis`).** The test case was generated from release 2026-4, so its page numbers are 2026-4 pages; the table shows where each text is in both releases.

| # | Cited page | Source | Quote | In 2026-4 | In 2026-3 | Result |
|---|---|---|---|---|---|---|
| 0 | p.156 | `SRC-FISERV-EMV-GUIDE-PDF-001` | 1 Not valid for contactless ATM transactions Table 53 Card Transaction Qualifiers (Tag 9F6C) - Byte 2 b8 b7 b6 b5 b4 b3 b2 b1 Meaning | - | - | Not ATL105 - from another document, not checked against ATL105 |
| 1 | p.511 | ATL105 | 03 MasterCard Credit | p.511 (Appendix C-1) | [p.504](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=504) (Appendix C-1) | Correct (2026-4 page) |
| 2 | p.83 | ATL105 | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only | p.83 (10-4) | [p.81](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=81) (10-4) | Correct (2026-4 page) |
| 3 | p.161 | ATL105 | The following credit cards are supported for EMV processing: ... MasterCard | p.160 (10-81) | [p.157](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=157) (10-80) | **Wrong page** - the text is on p.160 in 2026-4 |
| 4 | p.83 | ATL105 | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only | p.83 (10-4) | [p.81](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=81) (10-4) | Correct (2026-4 page) |

**The test case's own `rule_checks`, evaluated on the test data:**

| Rule check | What it asserts | Result |
|---|---|---|
| `LEG-TXN-TYPE` | Appendix G Transaction Type of the leg (element 78 p.400-401) | Holds |
| `LEG-CARD-TYPE` | Appendix E card type (element 78 p.400-401) | Holds |
| `LEG-ENTRY-MODE` | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) | Holds |
| `LEG-EMV-SEGMENT` | Segment 130 required for EMV (p.203/206) | Holds |
| `LEG-NONFUEL-AMOUNT-MAX-LENGTH` | Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.) | Holds |
| `CTX-TERMINAL-CLASS` | acceptance context terminal 010 'Attended on premise Card acceptor-operated ECR' (Appendix Q p.693) | Holds |
| `CTX-TERMINAL-TYPE` | acceptance context terminal 04 'Attended on premise Card acceptor-operated ECR' (Appendix Q p.693) | Holds |
| `SEG-111-REQUIRED` | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] | Holds |
| `TABLE-031-POPULATED` | Terminal Capability values must be populated for all transactions (p.569) | Holds |
| `TABLE-030-VALUE` | TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based transactions (Ex:); DERIVED_FROM_KB: Appendix Q p.693, p.696 entries matching {'attendance': 'attended', 'operator': 'card_acceptor', 'premise': 'on', 'device': 'ecr', 'customer': 'present', 'c | Holds |

<a id="s7"></a>
## 7. The test data as it should be

Same file; every changed or added line ends with a `//` comment. Values in `<...>` must come from BUYPASS or the client.

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "05",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5178588476140867",
      "CardDiscretionaryBlockData": "320120108983647",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401161",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704733C27AF9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    },
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

<a id="s8"></a>
## 8. Traceability - test case page citations against 2026-3

The generator recorded a source page for each field. Those numbers are **2026-4** pages; this table shows where the same rule is in 2026-3.

| Field (test case) | Cited page (2026-4) | Same rule in 2026-3 | Text |
|---|---|---|---|
| 100 Segment Type | p.223 | [Section 12.1, p.220 (12-6)](#r3-s12-1-type) (2026-4: p.223) | "Fixed value: 100" |
| 100 Segment Length | p.223 | [Section 12.1, p.220 (12-6)](#r3-s12-1-len) (2026-4: p.223) | "Identifies the data segment's length, including Segment Type's length and Field Separators." |
| 100 Information Byte | p.223 | [Element 44, p.379 (13-46)](#r3-el44) (2026-4: p.386) | "0 Single-message transmission" |
| 100 Terminal Identifier | p.223 | [Element 102, p.410 (13-77)](#r3-el102) (2026-4: p.417) | "The Terminal Identifier must be assigned by and set up at BUYPASS." |
| 100 Prompt Code | p.223 | [Element 78, p.393 (13-60)](#r3-el78) (2026-4: p.400) | "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (thre" |
| 100 Account Number | p.224 | [Element 2, p.357 (13-24)](#r3-el2) (2026-4: p.364) | "a variable length (up to 24 alphanumeric bytes) Account Number" |
| 100 Nonfuel Amount | p.224 | [Element 58, p.386 (13-53)](#r3-el58) (2026-4: p.393) | "A maximum length of 7 bytes is allowed for all other card types." |
| 100 Sequence Number | p.224 | [Element 86, p.402 (13-69)](#r3-el86-fmt) (2026-4: p.409) | "Valid Codes/Values: 000001–999999" |
| 100 Partial Approval Indicator | p.225 | [Element 121, p.433 (13-100)](#r3-el121) (2026-4: p.440) | "This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions." |
| 111 Segment Type | p.246 | [Section 12.10, p.243 (12-29)](#r3-s12-10-type) (2026-4: p.246) | "Fixed value: 111" |
| 111 Segment Length | p.246 | [Section 12.10, p.243 (12-29)](#r3-s12-10-fs) (2026-4: p.246) | "A Field Separator follows the final Variable Information Section." |
| 111 Variable Information Indicator | p.555 | [Element 111, p.415 (13-82)](#r3-el111) (2026-4: p.422) | "Fixed length of 3 alphanumeric characters" |
| 111 Variable Information Length | p.555 | [Section 12.10, p.243 (12-29)](#r3-s12-10-len) (2026-4: p.246) | "Identifies the length of Variable Information (Element No. 113)." |
| 111 Variable Information (Table 005) | p.555 | [Appendix I, p.548 (App. I-6)](#r3-appi-005) (2026-4: p.555) | "Point-of-Service Entry Mode Information" |
| 111 Variable Information Indicator | p.569 | [Element 111, p.415 (13-82)](#r3-el111) (2026-4: p.422) | "Fixed length of 3 alphanumeric characters" |
| 130 Segment Type | p.270 | [Section 12.20, p.267 (12-53)](#r3-s12-20-type) (2026-4: p.270) | "Fixed value: 130" |
| 130 Segment Length | p.270 | [Section 12.20, p.267 (12-53)](#r3-s12-20-len) (2026-4: p.270) | "Identifies the data segment's length, including Segment Type's length and Field Separators." |
| 130 CA Public Key File Checksum | p.270 | [Element 187, p.469 (13-136)](#r3-el187) (2026-4: p.476) | "Fixed length of 25 bytes" |
| 130 EMV Card Sequence Number | p.270 | [Element 188, p.470 (13-137)](#r3-el188) (2026-4: p.477) | "Fixed length of three bytes, right-aligned and padded with zeroes" |
| 130 EMV Chip Data Length | p.270 | [Element 189, p.470 (13-137)](#r3-el189) (2026-4: p.477) | "Identifies the length of the following field, EMV Chip Data, in an EMV transaction request and response." |
| 130 EMV Chip Data | p.271 | [Element 190, p.471 (13-138)](#r3-el190-tlv) (2026-4: p.478) | "EMV data elements include a series of TLV (Tag/Length/Value) combinations which are required for processing EMV transact" |
| 130 EMV Additional Information Indicator | p.271 | [Element 191, p.472 (13-139)](#r3-el191) (2026-4: p.479) | "001 EMV table data" |
| 130 EMV Additional Information Length | p.271 | [Element 192, p.473 (13-140)](#r3-el192) (2026-4: p.480) | "Valid Codes/Values: 001–985" |
| 130 EMV Additional Information | p.271 | [Element 118, p.432 (13-99)](#r3-el118-emv) (2026-4: p.439) | "When EMV Additional Information Indicator has a value of 001, EMV table data is included here." |

<a id="s9"></a>
## 9. ATL105 2026-3 text used in this report

Each text was searched on the stated page of the 2026-3 text file and of the 2026-3 PDF itself.

<a id="r3-s11-1-ds1"></a>
**Section 11.1 Financial Transactions (Data Section No. 1)** - [PDF p.164](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=164) (printed 11-4) · 2026-4: p.167 · [text line 7567](../specifications/ATL105/docs/specs/extracted_text.txt#L7567) · found in the 2026-3 PDF on this page

> All Financial Transaction Requests contain Element Nos. 55 and 63.

<a id="r3-s11-1-143"></a>
**Section 11.1 Financial Transactions (Data Section No. 3)** - [PDF p.165](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=165) (printed 11-5) · 2026-4: p.168 · [text line 7676](../specifications/ATL105/docs/specs/extracted_text.txt#L7676) · found in the 2026-3 PDF on this page

> 143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager.

<a id="r3-s11-8-emv"></a>
**Section 11.8.1 EMV Financial Transaction Request** - [PDF p.202](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=202) (printed 11-42) · 2026-4: p.205 · [text line 9983](../specifications/ATL105/docs/specs/extracted_text.txt#L9983) · found in the 2026-3 PDF on this page

> The following table illustrates the layout of an EMV Financial Transaction Request:

<a id="r3-s12-1-fs"></a>
**Section 12.1 Standard Message Data Segment** - [PDF p.220](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=220) (printed 12-6) · 2026-4: p.223 · [text line 11220](../specifications/ATL105/docs/specs/extracted_text.txt#L11220) · found in the 2026-3 PDF on this page

> When a field is not populated, still send the Field Separator.

<a id="r3-s12-1-len"></a>
**Section 12.1 Standard Message Data Segment** - [PDF p.220](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=220) (printed 12-6) · 2026-4: p.223 · [text line 11244](../specifications/ATL105/docs/specs/extracted_text.txt#L11244) · found in the 2026-3 PDF on this page

> Identifies the data segment's length, including Segment Type's length and Field Separators.

<a id="r3-s12-1-type"></a>
**Section 12.1 Standard Message Data Segment** - [PDF p.220](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=220) (printed 12-6) · 2026-4: p.223 · [text line 11240](../specifications/ATL105/docs/specs/extracted_text.txt#L11240) · found in the 2026-3 PDF on this page

> Fixed value: 100

<a id="r3-s12-3-delim"></a>
**Section 12.3 Product Code Data Segment** - [PDF p.226](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=226) (printed 12-12) · 2026-4: p.229 · [text line 11599](../specifications/ATL105/docs/specs/extracted_text.txt#L11599) · found in the 2026-3 PDF on this page

> A Product Data Field Delimiter always follows Quantity and Unit Price.

<a id="r3-s12-3-fs"></a>
**Section 12.3 Product Code Data Segment** - [PDF p.226](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=226) (printed 12-12) · 2026-4: p.229 · [text line 11596](../specifications/ATL105/docs/specs/extracted_text.txt#L11596) · found in the 2026-3 PDF on this page

> A Field Separator always follows Segment Type and Segment Length.

<a id="r3-s12-3-last"></a>
**Section 12.3 Product Code Data Segment** - [PDF p.226](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=226) (printed 12-12) · 2026-4: p.229 · [text line 11597](../specifications/ATL105/docs/specs/extracted_text.txt#L11597) · found in the 2026-3 PDF on this page

> A Field Separator follows Product Amount when Product Amount is the very last element in the segment.

<a id="r3-s12-3-rules"></a>
**Section 12.3 Product Code Data Segment** - [PDF p.226](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=226) (printed 12-12) · 2026-4: p.229 · [text line 11566](../specifications/ATL105/docs/specs/extracted_text.txt#L11566) · found in the 2026-3 PDF on this page

> When a Financial Transaction request includes the Product Code Data Segment, the products within this segment must meet the following requirements:

<a id="r3-s12-3-taxprod"></a>
**Section 12.3 Product Code Data Segment** - [PDF p.226](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=226) (printed 12-12) · 2026-4: p.229 · [text line 11576](../specifications/ATL105/docs/specs/extracted_text.txt#L11576) · found in the 2026-3 PDF on this page

> When tax product codes are included in the segment, the total dollar amount of tax products is also reported in Element No. 99 (Tax Amount)

<a id="r3-s12-3-total3"></a>
**Section 12.3 Product Code Data Segment** - [PDF p.226](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=226) (printed 12-12) · 2026-4: p.229 · [text line 11572](../specifications/ATL105/docs/specs/extracted_text.txt#L11572) · found in the 2026-3 PDF on this page

> The total of product amounts in the segment must equal the total of the amounts [Element No. 41 (Fuel Purchase Amount), Element No. 58 (Nonfuel Amount), Element No. 99 (Tax Amount) and Element No. 17 (Cash Amount)] in the Standard Message Data Segment

<a id="r3-s12-3-qty"></a>
**Section 12.3 Product Code Data Segment - Field 7 Quantity** - [PDF p.227](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=227) (printed 12-13) · 2026-4: p.230 · [text line 11669](../specifications/ATL105/docs/specs/extracted_text.txt#L11669) · found in the 2026-3 PDF on this page

> Quantity value must contain a digit for every position of assumed decimal places.

<a id="r3-s12-3-type"></a>
**Section 12.3 Product Code Data Segment - Field 1 Segment Type** - [PDF p.227](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=227) (printed 12-13) · 2026-4: p.230 · [text line 11633](../specifications/ATL105/docs/specs/extracted_text.txt#L11633) · found in the 2026-3 PDF on this page

> Fixed value: 102

<a id="r3-s12-10-fs"></a>
**Section 12.10 Variable Information Data Segment** - [PDF p.243](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=243) (printed 12-29) · 2026-4: p.246 · [text line 12577](../specifications/ATL105/docs/specs/extracted_text.txt#L12577) · found in the 2026-3 PDF on this page

> A Field Separator follows the final Variable Information Section.

<a id="r3-s12-10-len"></a>
**Section 12.10 Variable Information Data Segment** - [PDF p.243](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=243) (printed 12-29) · 2026-4: p.246 · [text line 12615](../specifications/ATL105/docs/specs/extracted_text.txt#L12615) · found in the 2026-3 PDF on this page

> Identifies the length of Variable Information (Element No. 113).

<a id="r3-s12-10-type"></a>
**Section 12.10 Variable Information Data Segment** - [PDF p.243](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=243) (printed 12-29) · 2026-4: p.246 · [text line 12596](../specifications/ATL105/docs/specs/extracted_text.txt#L12596) · found in the 2026-3 PDF on this page

> Fixed value: 111

<a id="r3-s12-20-always"></a>
**Section 12.20 EMV Request Data Segment** - [PDF p.267](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (printed 12-53) · 2026-4: p.270 · [text line 14012](../specifications/ATL105/docs/specs/extracted_text.txt#L14012) · found in the 2026-3 PDF on this page

> It always appears in Data Section No. 3 of an EMV Financial Transaction Request.

<a id="r3-s12-20-fs"></a>
**Section 12.20 EMV Request Data Segment** - [PDF p.267](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (printed 12-53) · 2026-4: p.270 · [text line 14021](../specifications/ATL105/docs/specs/extracted_text.txt#L14021) · found in the 2026-3 PDF on this page

> A Field Separator follows the final EMV Additional Information Section.

<a id="r3-s12-20-len"></a>
**Section 12.20 EMV Request Data Segment** - [PDF p.267](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (printed 12-53) · 2026-4: p.270 · [text line 14042](../specifications/ATL105/docs/specs/extracted_text.txt#L14042) · found in the 2026-3 PDF on this page

> Identifies the data segment's length, including Segment Type's length and Field Separators.

<a id="r3-s12-20-type"></a>
**Section 12.20 EMV Request Data Segment** - [PDF p.267](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (printed 12-53) · 2026-4: p.270 · [text line 14040](../specifications/ATL105/docs/specs/extracted_text.txt#L14040) · found in the 2026-3 PDF on this page

> Fixed value: 130

<a id="r3-s12-30-102"></a>
**Section 12.30 Tax by Product Data Segment** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14937](../specifications/ATL105/docs/specs/extracted_text.txt#L14937) · found in the 2026-3 PDF on this page

> A Product Code Data Segment (Data Segment No. 102) must be present in the transaction.

<a id="r3-s12-30-can"></a>
**Section 12.30 Tax by Product Data Segment** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14948](../specifications/ATL105/docs/specs/extracted_text.txt#L14948) · found in the 2026-3 PDF on this page

> For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax).

<a id="r3-s12-30-format"></a>
**Section 12.30 Tax by Product Data Segment** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14946](../specifications/ATL105/docs/specs/extracted_text.txt#L14946) · found in the 2026-3 PDF on this page

> The only validation that will be done for this segment is whether the format is correct. No judgement will be made of the values contained.

<a id="r3-s12-30-len"></a>
**Section 12.30 Tax by Product Data Segment - Field 2 Segment Length** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14985](../specifications/ATL105/docs/specs/extracted_text.txt#L14985) · found in the 2026-3 PDF on this page

> Identifies the data segment’s length, including Segment Type’s length and Field Separators.

<a id="r3-s12-30-order"></a>
**Section 12.30 Tax by Product Data Segment** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14938](../specifications/ATL105/docs/specs/extracted_text.txt#L14938) · found in the 2026-3 PDF on this page

> Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments.

<a id="r3-s12-30-rules"></a>
**Section 12.30 Tax by Product Data Segment** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14935](../specifications/ATL105/docs/specs/extracted_text.txt#L14935) · found in the 2026-3 PDF on this page

> When a Financial Transaction request includes the Tax by Product Segment, the following rules should be followed:

<a id="r3-s12-30-type"></a>
**Section 12.30 Tax by Product Data Segment - Field 1 Segment Type** - [PDF p.282](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=282) (printed 12-68) · 2026-4: p.285 · [text line 14980](../specifications/ATL105/docs/specs/extracted_text.txt#L14980) · found in the 2026-3 PDF on this page

> Fixed value: 143

<a id="r3-s12-30-f3"></a>
**Section 12.30 Tax by Product Data Segment - Field 3 Number of Products** - [PDF p.283](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=283) (printed 12-69) · 2026-4: p.286 · [text line 15016](../specifications/ATL105/docs/specs/extracted_text.txt#L15016) · found in the 2026-3 PDF on this page

> Identifies the count of products reported for this transaction. Should match the number of products from the Product Code Data Segment.

<a id="r3-s12-30-f4"></a>
**Section 12.30 Tax by Product Data Segment - Field 4 Product Code** - [PDF p.283](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=283) (printed 12-69) · 2026-4: p.286 · [text line 15026](../specifications/ATL105/docs/specs/extracted_text.txt#L15026) · found in the 2026-3 PDF on this page

> Identifies the product code from the same entry in the Product Code Data Segment.

<a id="r3-s12-30-f5"></a>
**Section 12.30 Tax by Product Data Segment - Field 5 Inclusive/Exclusive for Tax 1** - [PDF p.283](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=283) (printed 12-69) · 2026-4: p.286 · [text line 15032](../specifications/ATL105/docs/specs/extracted_text.txt#L15032) · found in the 2026-3 PDF on this page

> Identifies whether this tax is inclusive (“I”) or exclusive (“E”) to the product amount in the Product Code Data Segment.

<a id="r3-s12-30-f6"></a>
**Section 12.30 Tax by Product Data Segment - Field 6 Tax Type 1** - [PDF p.283](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=283) (printed 12-69) · 2026-4: p.286 · [text line 15041](../specifications/ATL105/docs/specs/extracted_text.txt#L15041) · found in the 2026-3 PDF on this page

> Identifies the tax type for this tax item. If the Inclusive/Exclusive flag is “N”, this field is omitted.

<a id="r3-s12-30-f7"></a>
**Section 12.30 Tax by Product Data Segment - Field 7 Tax Amount 1** - [PDF p.283](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=283) (printed 12-69) · 2026-4: p.286 · [text line 15050](../specifications/ATL105/docs/specs/extracted_text.txt#L15050) · found in the 2026-3 PDF on this page

> Identifies the first tax amount for this product. Decimal places are assumed by the currency of the transaction.

<a id="r3-s12-30-ex1"></a>
**Section 12.30 Tax by Product Data Segment - example** - [PDF p.284](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=284) (printed 12-70) · 2026-4: p.287 · [text line 15143](../specifications/ATL105/docs/specs/extracted_text.txt#L15143) · found in the 2026-3 PDF on this page

> The product “020” had an inclusive GST of 2.00 and no other tax. Therefore the tax amount is followed by a FS.

<a id="r3-el2"></a>
**Element 2 Account Number (Chapter 13)** - [PDF p.357](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=357) (printed 13-24) · 2026-4: p.364 · [text line 19430](../specifications/ATL105/docs/specs/extracted_text.txt#L19430) · found in the 2026-3 PDF on this page

> a variable length (up to 24 alphanumeric bytes) Account Number

<a id="r3-el5-pre"></a>
**Element 5 Approval Number (Chapter 13)** - [PDF p.361](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=361) (printed 13-28) · 2026-4: p.368 · [text line 19621](../specifications/ATL105/docs/specs/extracted_text.txt#L19621) · found in the 2026-3 PDF on this page

> If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization.

<a id="r3-el12-max"></a>
**Element 12 Card Discretionary Block Data (Chapter 13)** - [PDF p.364](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=364) (printed 13-31) · 2026-4: p.371 · [text line 19758](../specifications/ATL105/docs/specs/extracted_text.txt#L19758) · found in the 2026-3 PDF on this page

> Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2.

<a id="r3-el12-track2"></a>
**Element 12 Card Discretionary Block Data (Chapter 13)** - [PDF p.364](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=364) (printed 13-31) · 2026-4: p.371 · [text line 19760](../specifications/ATL105/docs/specs/extracted_text.txt#L19760) · found in the 2026-3 PDF on this page

> Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character.

<a id="r3-el17"></a>
**Element 17 Cash Amount (Chapter 13)** - [PDF p.368](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=368) (printed 13-35) · 2026-4: p.375 · [text line 19946](../specifications/ATL105/docs/specs/extracted_text.txt#L19946) · found in the 2026-3 PDF on this page

> Identifies the cash back amount of a transaction.

<a id="r3-el44"></a>
**Element 44 Information Byte (Chapter 13)** - [PDF p.379](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=379) (printed 13-46) · 2026-4: p.386 · [text line 20438](../specifications/ATL105/docs/specs/extracted_text.txt#L20438) · found in the 2026-3 PDF on this page

> 0 Single-message transmission

<a id="r3-el55"></a>
**Element 55 Message Format Version Identifier (Chapter 13)** - [PDF p.384](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=384) (printed 13-51) · 2026-4: p.391 · [text line 20626](../specifications/ATL105/docs/specs/extracted_text.txt#L20626) · found in the 2026-3 PDF on this page

> Fixed length of six alphanumeric characters

<a id="r3-el58"></a>
**Element 58 Nonfuel Amount (Chapter 13)** - [PDF p.386](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=386) (printed 13-53) · 2026-4: p.393 · [text line 20717](../specifications/ATL105/docs/specs/extracted_text.txt#L20717) · found in the 2026-3 PDF on this page

> A maximum length of 7 bytes is allowed for all other card types.

<a id="r3-el58-def"></a>
**Element 58 Nonfuel Amount (Chapter 13)** - [PDF p.386](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=386) (printed 13-53) · 2026-4: p.393 · [text line 20719](../specifications/ATL105/docs/specs/extracted_text.txt#L20719) · found in the 2026-3 PDF on this page

> Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount.

<a id="r3-el62"></a>
**Element 62 Number of Products (Chapter 13)** - [PDF p.387](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=387) (printed 13-54) · 2026-4: p.394 · [text line 20789](../specifications/ATL105/docs/specs/extracted_text.txt#L20789) · found in the 2026-3 PDF on this page

> Always precede single digits (1–9) with a zero (01–09).

<a id="r3-el63"></a>
**Element 63 Number of Segments (Chapter 13)** - [PDF p.388](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=388) (printed 13-55) · 2026-4: p.395 · [text line 20808](../specifications/ATL105/docs/specs/extracted_text.txt#L20808) · found in the 2026-3 PDF on this page

> Variable length of up to two digits

<a id="r3-el76"></a>
**Element 76 Product Amount (Chapter 13)** - [PDF p.392](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=392) (printed 13-59) · 2026-4: p.399 · [text line 20971](../specifications/ATL105/docs/specs/extracted_text.txt#L20971) · found in the 2026-3 PDF on this page

> Representation: Variable length of up to 12 digits with two assumed decimal places

<a id="r3-el77"></a>
**Element 77 Product Code (Chapter 13)** - [PDF p.393](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=393) (printed 13-60) · 2026-4: p.400 · [text line 21019](../specifications/ATL105/docs/specs/extracted_text.txt#L21019) · found in the 2026-3 PDF on this page

> Representation: Fixed length of three digits

<a id="r3-el78"></a>
**Element 78 Prompt Code (Chapter 13)** - [PDF p.393](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=393) (printed 13-60) · 2026-4: p.400 · [text line 21055](../specifications/ATL105/docs/specs/extracted_text.txt#L21055) · found in the 2026-3 PDF on this page

> this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters).

<a id="r3-el81"></a>
**Element 81 Quantity (Chapter 13)** - [PDF p.395](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=395) (printed 13-62) · 2026-4: p.402 · [text line 21142](../specifications/ATL105/docs/specs/extracted_text.txt#L21142) · found in the 2026-3 PDF on this page

> Processing Rules: The leading number indicates the number of assumed decimal places. There is a maximum number of three assumed decimal places.

<a id="r3-el83-2"></a>
**Element 83 Response Code (Chapter 13)** - [PDF p.397](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=397) (printed 13-64) · 2026-4: p.404 · [text line 21225](../specifications/ATL105/docs/specs/extracted_text.txt#L21225) · found in the 2026-3 PDF on this page

> 2 Approved—Authorization only

<a id="r3-el84"></a>
**Element 84 Segment Length (Chapter 13)** - [PDF p.399](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=399) (printed 13-66) · 2026-4: p.406 · [text line 21325](../specifications/ATL105/docs/specs/extracted_text.txt#L21325) · found in the 2026-3 PDF on this page

> This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements.

<a id="r3-el86-fmt"></a>
**Element 86 Sequence Number (Chapter 13)** - [PDF p.402](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=402) (printed 13-69) · 2026-4: p.409 · [text line 21485](../specifications/ATL105/docs/specs/extracted_text.txt#L21485) · found in the 2026-3 PDF on this page

> Valid Codes/Values: 000001–999999

<a id="r3-el87-val"></a>
**Element 87 Service Level (Chapter 13)** - [PDF p.403](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=403) (printed 13-70) · 2026-4: p.410 · [text line 21515](../specifications/ATL105/docs/specs/extracted_text.txt#L21515) · found in the 2026-3 PDF on this page

> F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present.

<a id="r3-el102"></a>
**Element 102 Terminal Identifier (Chapter 13)** - [PDF p.410](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=410) (printed 13-77) · 2026-4: p.417 · [text line 21797](../specifications/ATL105/docs/specs/extracted_text.txt#L21797) · found in the 2026-3 PDF on this page

> The Terminal Identifier must be assigned by and set up at BUYPASS.

<a id="r3-el102-state"></a>
**Element 102 Terminal Identifier (Chapter 13)** - [PDF p.410](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=410) (printed 13-77) · 2026-4: p.417 · [text line 21818](../specifications/ATL105/docs/specs/extracted_text.txt#L21818) · found in the 2026-3 PDF on this page

> State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code).

<a id="r3-el106"></a>
**Element 106 Unit of Measure (Chapter 13)** - [PDF p.413](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=413) (printed 13-80) · 2026-4: p.420 · [text line 21935](../specifications/ATL105/docs/specs/extracted_text.txt#L21935) · found in the 2026-3 PDF on this page

> Valid Codes/Values: Value Description C Case/Carton G Gallon H Hours I Imperial Gallons K Kilogram L Liter

<a id="r3-el107"></a>
**Element 107 Unit Price (Chapter 13)** - [PDF p.414](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=414) (printed 13-81) · 2026-4: p.421 · [text line 21973](../specifications/ATL105/docs/specs/extracted_text.txt#L21973) · found in the 2026-3 PDF on this page

> Processing Rules: The leading number indicates the number of assumed decimal places. There is a maximum number of three assumed decimal places.

<a id="r3-el111"></a>
**Element 111 Variable Information Indicator (Chapter 13)** - [PDF p.415](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=415) (printed 13-82) · 2026-4: p.422 · [text line 22028](../specifications/ATL105/docs/specs/extracted_text.txt#L22028) · found in the 2026-3 PDF on this page

> Fixed length of 3 alphanumeric characters

<a id="r3-el118-emv"></a>
**Element 118 EMV Additional Information (Chapter 13)** - [PDF p.432](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=432) (printed 13-99) · 2026-4: p.439 · [text line 22783](../specifications/ATL105/docs/specs/extracted_text.txt#L22783) · found in the 2026-3 PDF on this page

> When EMV Additional Information Indicator has a value of 001, EMV table data is included here.

<a id="r3-el121"></a>
**Element 121 Partial Approval Indicator (Chapter 13)** - [PDF p.433](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=433) (printed 13-100) · 2026-4: p.440 · [text line 22836](../specifications/ATL105/docs/specs/extracted_text.txt#L22836) · found in the 2026-3 PDF on this page

> This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions.

<a id="r3-el187"></a>
**Element 187 CA Public Key File Checksum (Chapter 13)** - [PDF p.469](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (printed 13-136) · 2026-4: p.476 · [text line 24306](../specifications/ATL105/docs/specs/extracted_text.txt#L24306) · found in the 2026-3 PDF on this page

> Fixed length of 25 bytes

<a id="r3-el187-req"></a>
**Element 187 CA Public Key File Checksum (Chapter 13)** - [PDF p.469](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (printed 13-136) · 2026-4: p.476 · [text line 24308](../specifications/ATL105/docs/specs/extracted_text.txt#L24308) · found in the 2026-3 PDF on this page

> Required in the EMV Financial Transaction Request.

<a id="r3-el187-use"></a>
**Element 187 CA Public Key File Checksum (Chapter 13)** - [PDF p.470](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (printed 13-137) · 2026-4: p.477 · [text line 24319](../specifications/ATL105/docs/specs/extracted_text.txt#L24319) · found in the 2026-3 PDF on this page

> Once received, the checksum must be used for all subsequent EMV transaction requests from this device.

<a id="r3-el188"></a>
**Element 188 EMV Card Sequence Number (Chapter 13)** - [PDF p.470](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (printed 13-137) · 2026-4: p.477 · [text line 24330](../specifications/ATL105/docs/specs/extracted_text.txt#L24330) · found in the 2026-3 PDF on this page

> Fixed length of three bytes, right-aligned and padded with zeroes

<a id="r3-el188-val"></a>
**Element 188 EMV Card Sequence Number (Chapter 13)** - [PDF p.470](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (printed 13-137) · 2026-4: p.477 · [text line 24339](../specifications/ATL105/docs/specs/extracted_text.txt#L24339) · found in the 2026-3 PDF on this page

> Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card

<a id="r3-el189"></a>
**Element 189 EMV Chip Data Length (Chapter 13)** - [PDF p.470](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (printed 13-137) · 2026-4: p.477 · [text line 24348](../specifications/ATL105/docs/specs/extracted_text.txt#L24348) · found in the 2026-3 PDF on this page

> Identifies the length of the following field, EMV Chip Data, in an EMV transaction request and response.

<a id="r3-el190-aid"></a>
**Element 190 EMV Chip Data (Chapter 13)** - [PDF p.471](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (printed 13-138) · 2026-4: p.478 · [text line 24381](../specifications/ATL105/docs/specs/extracted_text.txt#L24381) · found in the 2026-3 PDF on this page

> the Application ID (AID) TLV (tag 9F06) or Dedicated File Name (tag 84) must be included in Data Element No. 190 (EMV Chip Data).

<a id="r3-el190-bin"></a>
**Element 190 EMV Chip Data (Chapter 13)** - [PDF p.471](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (printed 13-138) · 2026-4: p.478 · [text line 24370](../specifications/ATL105/docs/specs/extracted_text.txt#L24370) · found in the 2026-3 PDF on this page

> All fields after the table length are in binary format.

<a id="r3-el190-exact"></a>
**Element 190 EMV Chip Data (Chapter 13)** - [PDF p.471](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (printed 13-138) · 2026-4: p.478 · [text line 24375](../specifications/ATL105/docs/specs/extracted_text.txt#L24375) · found in the 2026-3 PDF on this page

> All data elements must contain the exact values that were present on the card to terminal interface.

<a id="r3-el190-no5a57"></a>
**Element 190 EMV Chip Data (Chapter 13)** - [PDF p.471](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (printed 13-138) · 2026-4: p.478 · [text line 24383](../specifications/ATL105/docs/specs/extracted_text.txt#L24383) · found in the 2026-3 PDF on this page

> Tag ‘5A’ (PAN) and Tag ‘57’ (Track 2 equivalent data) must not be included in Data Element 190 — EMV Chip Data.

<a id="r3-el190-tlv"></a>
**Element 190 EMV Chip Data (Chapter 13)** - [PDF p.471](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (printed 13-138) · 2026-4: p.478 · [text line 24373](../specifications/ATL105/docs/specs/extracted_text.txt#L24373) · found in the 2026-3 PDF on this page

> EMV data elements include a series of TLV (Tag/Length/Value) combinations which are required for processing EMV transactions.

<a id="r3-el191"></a>
**Element 191 EMV Additional Information Indicator (Chapter 13)** - [PDF p.472](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=472) (printed 13-139) · 2026-4: p.479 · [text line 24433](../specifications/ATL105/docs/specs/extracted_text.txt#L24433) · found in the 2026-3 PDF on this page

> 001 EMV table data

<a id="r3-el192"></a>
**Element 192 EMV Additional Information Length (Chapter 13)** - [PDF p.473](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=473) (printed 13-140) · 2026-4: p.480 · [text line 24454](../specifications/ATL105/docs/specs/extracted_text.txt#L24454) · found in the 2026-3 PDF on this page

> Valid Codes/Values: 001–985

<a id="r3-appd-72"></a>
**Appendix D. Valid State Codes** - [PDF p.510](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=510) (printed Appendix D-4) · 2026-4: p.517 · [text line 26217](../specifications/ATL105/docs/specs/extracted_text.txt#L26217) · found in the 2026-3 PDF on this page

> Yukon YT 72

<a id="r3-appe-020"></a>
**Appendix E. Valid Card Type Codes** - [PDF p.514](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=514) (printed Appendix E-4) · 2026-4: p.521 · [text line 26396](../specifications/ATL105/docs/specs/extracted_text.txt#L26396) · found in the 2026-3 PDF on this page

> 020 Credit Credit

<a id="r3-appf-400"></a>
**Appendix F. Payment Systems Product Codes - Merchandise Codes** - [PDF p.528](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=528) (printed Appendix F-13) · 2026-4: p.535 · [text line 26908](../specifications/ATL105/docs/specs/extracted_text.txt#L26908) · found in the 2026-3 PDF on this page

> 400 General merchandise

<a id="r3-appg-3"></a>
**Appendix G. Valid Transaction Type Codes** - [PDF p.538](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=538) (printed Appendix G-1) · 2026-4: p.545 · [text line 27281](../specifications/ATL105/docs/specs/extracted_text.txt#L27281) · found in the 2026-3 PDF on this page

> 3 POS Authorization Only Point-of-sale authorization only (no draft capture)

<a id="r3-appi-005"></a>
**Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005)** - [PDF p.548](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=548) (printed Appendix I-6) · 2026-4: p.555 · [text line 27670](../specifications/ATL105/docs/specs/extracted_text.txt#L27670) · found in the 2026-3 PDF on this page

> Point-of-Service Entry Mode Information

<a id="r3-appi-006"></a>
**Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006)** - [PDF p.548](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=548) (printed Appendix I-6) · 2026-4: p.555 · [text line 27688](../specifications/ATL105/docs/specs/extracted_text.txt#L27688) · found in the 2026-3 PDF on this page

> Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)]

<a id="r3-appi-030"></a>
**Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030)** - [PDF p.561](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=561) (printed Appendix I-19) · 2026-4: p.568 · [text line 28220](../specifications/ATL105/docs/specs/extracted_text.txt#L28220) · found in the 2026-3 PDF on this page

> Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions.

<a id="r3-appi-031"></a>
**Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031)** - [PDF p.562](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=562) (printed Appendix I-20) · 2026-4: p.569 · [text line 28259](../specifications/ATL105/docs/specs/extracted_text.txt#L28259) · found in the 2026-3 PDF on this page

> Terminal Capability values must be populated for all transactions

<a id="r3-appi-031-12"></a>
**Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031)** - [PDF p.562](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=562) (printed Appendix I-20) · 2026-4: p.569 · [text line 28275](../specifications/ATL105/docs/specs/extracted_text.txt#L28275) · found in the 2026-3 PDF on this page

> 12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities

<a id="r3-appj-05"></a>
**Appendix J. Valid Point-of-Service Entry Mode Codes** - [PDF p.631](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=631) (printed Appendix J-1) · 2026-4: p.638 · [text line 32504](../specifications/ATL105/docs/specs/extracted_text.txt#L32504) · found in the 2026-3 PDF on this page

> 05 Integrated circuit card (ICC) read – CVV data reliable (used for EMV chip read)

<a id="r3-appj-pin1"></a>
**Appendix J. Valid Point-of-Service Entry Mode Codes** - [PDF p.632](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=632) (printed Appendix J-2) · 2026-4: p.639 · [text line 32547](../specifications/ATL105/docs/specs/extracted_text.txt#L32547) · found in the 2026-3 PDF on this page

> 1 PIN entry capability (including both EMV Contact and Contactless)

<a id="r3-appq"></a>
**Appendix Q. Valid National Point-of-Service Condition Codes** - [PDF p.683](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=683) (printed Appendix Q-1) · 2026-4: p.690 · [text line 35158](../specifications/ATL105/docs/specs/extracted_text.txt#L35158) · found in the 2026-3 PDF on this page

> This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030 The National Point-of-Service Condition Codes data element contains the following elements: • Terminal Class (Position Nos. 1–3) • Presentation Type (Position Nos. 4–7) • Security Condition (Position No. 8) • Terminal Type (Position Nos. 9–10)

<a id="r3-appq-class"></a>
**Appendix Q. Valid National Point-of-Service Condition Codes** - [PDF p.684](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=684) (printed Appendix Q-2) · 2026-4: p.691 · [text line 35189](../specifications/ATL105/docs/specs/extracted_text.txt#L35189) · found in the 2026-3 PDF on this page

> Position No. 1 Value Description 0 Attended 1 Unattended 2–7 Reserved for national use 8–9 Reserved for private use Position No. 2 Value Description 0 Customer-operated 1 Card acceptor-operated 2 Administrative 3–7 Reserved for national use 8–9 Reserved for private use Position No. 3 Value Description 0 On premise 1 Off premise

<a id="r3-appq-type"></a>
**Appendix Q. Valid National Point-of-Service Condition Codes** - [PDF p.685](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=685) (printed Appendix Q-3) · 2026-4: p.692 · [text line 35225](../specifications/ATL105/docs/specs/extracted_text.txt#L35225) · found in the 2026-3 PDF on this page

> Value Description 00 Administrative terminal 01 Point-of-service terminal 02 ATM 03 Home terminal 04 ECR 05 Dial terminal 06 Traveler's check machine 07 Fuel machine

<a id="r3-appq-010-04"></a>
**Appendix Q. Valid National Point-of-Service Condition Codes** - [PDF p.686](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=686) (printed Appendix Q-4) · 2026-4: p.693 · [text line 35279](../specifications/ATL105/docs/specs/extracted_text.txt#L35279) · found in the 2026-3 PDF on this page

> 010 04 Attended on premise Card acceptor-operated ECR

<a id="r3-appq-pres"></a>
**Appendix Q. Valid National Point-of-Service Condition Codes** - [PDF p.689](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=689) (printed Appendix Q-7) · 2026-4: p.696 · [text line 35376](../specifications/ATL105/docs/specs/extracted_text.txt#L35376) · found in the 2026-3 PDF on this page

> Position Nos. 4–7 Presentation Type Description 0000 Customer and card present 0100 Customer present; card not present 1000 Customer not present; card present 1100 Customer not present; card not present 2100 Mail and/or telephone order 8800 Preauthorized purchase

<a id="r3-appq-sec"></a>
**Appendix Q. Valid National Point-of-Service Condition Codes** - [PDF p.689](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=689) (printed Appendix Q-7) · 2026-4: p.696 · [text line 35387](../specifications/ATL105/docs/specs/extracted_text.txt#L35387) · found in the 2026-3 PDF on this page

> Security Condition Value Description 0 No security concern 1 Suspected fraud 2 Identification verified 3 Electronic commerce transaction with digital signature

<a id="r3-appr-9c"></a>
**Appendix R. EMV Chip Data Example** - [PDF p.690](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=690) (printed Appendix R-1) · 2026-4: p.697 · [text line 35426](../specifications/ATL105/docs/specs/extracted_text.txt#L35426) · found in the 2026-3 PDF on this page

> 9C\01\00\ Transaction type

<a id="r3-appr-9f02"></a>
**Appendix R. EMV Chip Data Example** - [PDF p.690](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=690) (printed Appendix R-1) · 2026-4: p.697 · [text line 35419](../specifications/ATL105/docs/specs/extracted_text.txt#L35419) · found in the 2026-3 PDF on this page

> 9F\02\06\00\00\00\00\09\00\ Amount, authorized (numeric)

<a id="r3-appr-pkt"></a>
**Appendix R. EMV Chip Data Example** - [PDF p.690](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=690) (printed Appendix R-1) · 2026-4: p.697 · [text line 35416](../specifications/ATL105/docs/specs/extracted_text.txt#L35416) · found in the 2026-3 PDF on this page

> 01\06 EMV/Chip Data length (BCD packed)

<a id="r3-appt-1"></a>
**Appendix T. EMV Additional Information Data Layouts - EMV Table Data** - [PDF p.694](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694) (printed Appendix T-1) · 2026-4: p.701 · [text line 35581](../specifications/ATL105/docs/specs/extracted_text.txt#L35581) · found in the 2026-3 PDF on this page

> EMVYES This code indicates that the device is able to receive EMV Table Data.

<a id="r3-appt-1-len"></a>
**Appendix T. EMV Additional Information Data Layouts - EMV Table Data** - [PDF p.694](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694) (printed Appendix T-1) · 2026-4: p.701 · [text line 35572](../specifications/ATL105/docs/specs/extracted_text.txt#L35572) · found in the 2026-3 PDF on this page

> Maximum Length n6 Len.: 6

<a id="r3-appt-1-not"></a>
**Appendix T. EMV Additional Information Data Layouts - EMV Table Data** - [PDF p.694](../specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694) (printed Appendix T-1) · 2026-4: p.701 · [text line 35585](../specifications/ATL105/docs/specs/extracted_text.txt#L35585) · found in the 2026-3 PDF on this page

> EMVNOT This code indicates that the device is unable to receive EMV Table Data.

## 10. 2026-3 compared with 2026-4 for this test case

- 96 of the 96 ATL105 texts used here are word for word the same in 2026-3 and 2026-4; only the page numbers differ (2026-3 pages are earlier, e.g. Section 12.30 Tax by Product is p.282-284 in 2026-3 and p.285-287 in 2026-4).
- So the result is the same under both releases: the fields sent are correct, Tax Type 1 = GST is missing, and the placeholders must be replaced.
- Produced with `scripts/validate-run-and-build-manual-validation.py` (the validator) plus the 2026-3 page lookup, on mastercard_credit_new_16_11.

