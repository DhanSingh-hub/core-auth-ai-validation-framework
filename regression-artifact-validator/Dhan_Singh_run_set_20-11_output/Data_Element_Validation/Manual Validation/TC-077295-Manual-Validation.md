# TC-077295 (BRDE_TS1_TC93) - Manual Validation Guide

**BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Segment Length one more than its rendered bytes**

A step-by-step path to check this test case and its test data by hand against ATL105 and to confirm or reject what the AI validation reported. The ATL105 text needed for every check is copied into [section 11](#s11) (each line was found on the stated page), so the specification does not have to be opened. The same release is the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` (byte-identical to the validator's copy).

| Item | Value |
|---|---|
| AI validation result | **FAIL** - can it run as-is? No - fix the defects first |
| Findings | 1 Intended violation of a negative test (correct), 2 Test data defect, 4 Awaiting client input / run-time value, 1 Citation issue (section 9) |
| Test case record | [test_cases.jsonl line 73](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (the whole test case is on this one line) |
| Test data | [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| Scenario | BRDE_TS1 - Purchase/Capture – EMV Contact Chip (None) |
| Type / polarity | `T12` Data element & segment validation / Negative |
| Template | `T12-N-C11` |
| Status in the test case | `needs_client_input` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

**How to read this guide**

- **PDF p.** is the page number shown in the PDF viewer; **printed** is the page number in the ATL105 footer.
- Links such as **[EL58]** jump to the ATL105 text copied in [section 11](#s11).
- A **Field Separator** is the separator character that follows a field in the real (wire) message; it counts as one character. The JSON test data does not show it.
- **AI result** is what the AI validation decided; **Your check** is for you (☑ agree, or note what you found).
- **Intended** marks the rule a negative test breaks on purpose - that is correct for the test.

<a id="s1"></a>

## 1. What this test case does

| Item | Value | Field in the test case record |
|---|---|---|
| Objective | Segment 100 Segment Length one more than its rendered bytes | `test_objective` |
| Validation focus | Message Validation | `validation_focus` |
| Merchant | MERCHANT 01 (POS PLACEHOLDER) (id M01, POS), merchant_provenance = placeholder | `merchant` |
| Card | MasterCard Credit - Prompt Code card type 020 | `dimensions` |
| Message chain (Transaction Types) | EMV Financial Transaction Request | `message_chain` |
| Awaiting input | step 1: 100 Encrypted PIN Block Data - value from the client's PIN encryption device / test key profile | `awaiting_inputs` |

| Step | Role | Prompt Code in the file | Expected response | Test data file |
|---|---|---|---|---|
| 1 | purchase | `0020` = POS Purchase/Capture, card type 020 | `1` (ASSERT) | [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |

<a id="s2"></a>

## 2. Manual validation path (do these steps in order)

1. **Open the test case.** Open [test_cases.jsonl line 73](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73). The record is one long line: copy it into a new file, set the language to JSON and format it (Shift+Alt+F). Confirm the id, display id, status and steps in [section 1](#s1).
2. **Open the test data file** next to this guide: [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json).
3. **Check the message structure** ([S11.1](#x-s11-1), [S12.1](#x-s12-1), [S12.10](#x-s12-10)): root `Financial Transaction Request`, Data Section 1 (`MessageFormatVersionIdentifier`, `NumberofSegments`), Segment 100 with its 17 fields, Segment 111 with Tables 005, 030 and 031.
4. **See how each value was produced** ([section 3](#s3)): compare every value in the file with the value and method recorded in the test case.
5. **Check every field** ([section 4](#s4)) and mark the Your check column.
6. **Recalculate** the segment lengths, the card check digit and the expiry ([section 5](#s5)).
7. **Links between steps** ([section 6](#s6)): not applicable - one step.
8. **Check the expected responses** ([section 7](#s7)) and the **ATL105 citations** ([section 8](#s8)).
9. **Confirm each finding** ([section 9](#s9)) with its 'How to confirm by hand' steps.
10. **Check across test cases** ([section 10](#s10)) and **sign off** ([section 12](#s12)).

<a id="s3"></a>

## 3. How the test data was generated

The test data file is the wire-ready copy of the request stored in the test case. For every field the test case records the value, the generation `method`, the ATL105 page it relied on (`source_page`) and notes (`flags`). Segment Length / Number of Segments are calculated when the file is built. Values that exist only after an earlier step has run are written as instruction text in angle brackets.

### 3.1 Step 1 - [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json)

In [test_cases.jsonl line 73](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73): `request["<segment>"]["<field>"]`.

| Segment / field | In the test data file | In the test case | Same? | How it was produced (`method`) | Page used | Notes (`flags`) |
|---|---|---|---|---|---|---|
| DS1 / Message Format Version Identifier (55) | `ATL105` | `ATL105` | Yes | Fixed value from the Chapter 11 message layout (`fixed_value_chapter11_table`) | p.391 (printed 13-51) | - |
| DS1 / Number of Segments (63) | `03` | (none) | Calculated | Not stored in the test case; counted from the segments when the file was built (`derived_segment_count`) | p.395 (printed 13-55) | - |
| 100 / Segment Type (El. 85) | `100` | `100` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.223 (printed 12-6) | - |
| 100 / Segment Length (El. 84) | `093` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.223 (printed 12-6) | DERIVED_AT_WIRE_ENCODING |
| 100 / Information Byte (El. 44) | `0` | `0` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.223 (printed 12-6) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 100 / Terminal Identifier (El. 102) | `JX41894821001` | `JX41894821001` | Yes | Generated Terminal Identifier for the merchant's terminal (placeholder merchant) (`realistic_terminal_id`) | p.223 (printed 12-6) | TERMINAL_ID_PER_MERCHANT |
| 100 / Prompt Code (El. 78) | `0020` | `0020` | Yes | Fixed by the test scenario (`scenario_parameter_literal`) | p.223 (printed 12-6) | AMBIGUOUS_LENGTH, CONSTRAINT_RULE_UNDEFINED |
| 100 / Account Number (El. 2) | `5147295465216009` | `5147295465216009` | Yes | Generated MasterCard test card number (`realistic_pan`) | p.224 (printed 12-7) | - |
| 100 / Card Discretionary Block Data (El. 12) | `0931` | `0931` | Yes | Generated card expiry date (`realistic_expiry_date`) | p.224 (printed 12-7) | CARD_DATA_KEYED |
| 100 / Encrypted PIN Block Data (El. 33) | `<AWAITING:EncryptedPINBlockData>` | (none) | Run-time value (provisional in the test case) | awaiting_client_value (`awaiting_client_value`) | p.224 (printed 12-7) | AWAITING_CLIENT_VALUE |
| 100 / Pump/Lane Number (El. 79) | `1` | `1` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEGMENT_SCENARIO_FIELD_INCLUDED |
| 100 / Fuel Purchase Amount (El. 41) | `1` | `1` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEGMENT_SCENARIO_FIELD_INCLUDED |
| 100 / Nonfuel Amount (El. 58) | `1500` | `1500` | Yes | Fixed by the test scenario (`scenario_parameter_literal`) | p.224 (printed 12-7) | - |
| 100 / Tax Amount (El. 99) | `1` | `1` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEGMENT_SCENARIO_FIELD_INCLUDED |
| 100 / Cash Amount (El. 17) | `1` | `1` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEGMENT_SCENARIO_FIELD_INCLUDED |
| 100 / Sequence Number (El. 86) | `772951` | `772951` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEQUENCE_ALLOCATED |
| 100 / Approval Number (El. 5) | `0CI2N6` | `0CI2N6` | Yes | format_synthesis (`format_synthesis`) | p.224 (printed 12-7) | UNTRUSTED_VALID_VALUES, SEGMENT_SCENARIO_FIELD_INCLUDED |
| 100 / Local Date and Local Time (El. 49) | `0615251430` | `0615251430` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.225 (printed 12-8) | SEGMENT_SCENARIO_FIELD_INCLUDED |
| 100 / Partial Approval Indicator (El. 121) | `1` | `1` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.225 (printed 12-8) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 111 / Segment Type (El. 85) | `111` | `111` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.246 (printed 12-29) | - |
| 111 / Segment Length (El. 84) | `042` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.246 (printed 12-29) | DERIVED_AT_WIRE_ENCODING |
| 111 / Variable Information Indicator (El. 111) | `005` | `005` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information Length (El. 112) | `003` | `003` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information (El. 113) | `051` | `051` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information Indicator [2] (El. 111) | `031` | `031` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.569 (printed Appendix I-20) | MANDATORY_VARIABLE_INFORMATION_TABLE |
| 111 / Variable Information Length [2] (El. 112) | `002` | `002` | Yes | Calculated from the length of the table data (`derived_from_table_data`) | - | - |
| 111 / Variable Information [2] (El. 113) | `12` | `12` | Yes | Certified capability of the merchant's terminal (generator merchant settings) (`realistic_terminal_capability`) | - | TERMINAL_CAPABILITY |
| 111 / Variable Information Indicator [3] (El. 111) | `030` | `030` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | - | TABLE_REQUIRED |
| 111 / Variable Information Length [3] (El. 112) | `010` | `010` | Yes | Calculated from the length of the table data (`derived_from_table_data`) | - | - |
| 111 / Variable Information [3] (El. 113) | `0100000004` | `0100000004` | Yes | spec_rule_value (`spec_rule_value`) | - | TABLE_REQUIRED |
| 130 / Segment Type (El. 85) | `130` | `130` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.270 (printed 12-53) | - |
| 130 / Segment Length (El. 84) | `0204` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.270 (printed 12-53) | DERIVED_AT_WIRE_ENCODING |
| 130 / CA Public Key File Checksum (El. 187) | `2012091700000100000102171` | `2012091700000100000102171` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.270 (printed 12-53) | - |
| 130 / EMV Card Sequence Number (El. 188) | `001` | `001` | Yes | realistic_emv_card_sequence (`realistic_emv_card_sequence`) | p.270 (printed 12-53) | EMV Card Sequence Number = Tag 5F34 of the same card, 3 bytes zero-padded; space-filled when 5F34 is not provided (element 188, p.477), FIXED_WIDTH_RENDERED |
| 130 / EMV Chip Data Length (El. 189) | `147` | `147` | Yes | derived_from_linked_field (`derived_from_linked_field`) | p.270 (printed 12-53) | EMV Chip Data Length = bytes of EMV Chip Data (2-byte length prefix + TLV data) |
| 130 / EMV Chip Data (El. 190) | `01455F2A0208405F340101820258008407A00000…28509F530152` | `01455F2A0208405F340101820258008407A00000…28509F530152` | Yes | Composed from the Segment 153 layout tables with synthesized (placeholder) data (`spec_tlv_composite`) | p.271 (printed 12-54) | EMV_CHIP_DATA_PER_TEST_CASE |
| 130 / EMV Additional Information Indicator (El. 191) | `001` | `001` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.271 (printed 12-54) | - |
| 130 / EMV Additional Information Length (El. 192) | `006` | `006` | Yes | derived_from_linked_field (`derived_from_linked_field`) | p.271 (printed 12-54) | EMV Additional Information Length = character count of EMV Additional Information (element 192, p.480) |
| 130 / EMV Additional Information (El. 118) | `EMVYES` | `EMVYES` | Yes | spec_rule_value (`spec_rule_value`) | p.271 (printed 12-54) | EMV_ADDITIONAL_INFORMATION |

Generator notes used above (full text of `flags`):

- DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized
- QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead
- TERMINAL_ID_PER_MERCHANT: one Terminal Identifier per merchant terminal (element 102, p.417); State Code = numeric ANSI code (Appendix D, p.514)
- AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4
- CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable
- CARD_DATA_KEYED: manually entered card - expiration date MMYY (element 12, p.372; p.81)
- AWAITING_CLIENT_VALUE: Encrypted PIN Block Data is produced by the client's PIN device / test keys; a synthesized literal ('16') is never sent
- SEGMENT_SCENARIO_FIELD_INCLUDED: this field's segment is sent by a segment-level scenario (segment_directives) — its inclusion is asserted by that scenario, not by the generic inclusion profile
- SEQUENCE_ALLOCATED: unique per terminal across the run set; follow-ons carry the original's number (element 86, p.409, BR-409-3)
- UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)
- QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead
- AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='05' (entry_mode), positions [3, 3]='1' (pin_entry_capability)
- MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p569)
- TERMINAL_CAPABILITY: the merchant terminal's certified capability (merchants.json terminal_capability; Table 031, p.569, BR-569-1)
- TABLE_REQUIRED: Table 030 POS Condition Code (p.568: Required for all EMV transactions, all internet based transactions (Ex:); DERIVED_FROM_KB: Appendix Q p.693, p.696 entries matching {'attendance': 'attended', 'operator': 'card_acceptor', 'premise': 'on', 'device': 'ecr', 'customer': 'present', 'card': 'present', 'channel': 'presence', 'security': 'none'}; length 10 from the table layout (p.568)
- EMV Card Sequence Number = Tag 5F34 of the same card, 3 bytes zero-padded; space-filled when 5F34 is not provided (element 188, p.477)
- FIXED_WIDTH_RENDERED: 3 bytes, right-aligned, zero-filled (element 188 representation)
- EMV Chip Data Length = bytes of EMV Chip Data (2-byte length prefix + TLV data)
- EMV_CHIP_DATA_PER_TEST_CASE: AID A0000000041010 for MASTERCARD (Fiserv EMV guide p.44), 9C=09 for PURCHASE (p.144 Table 28), 9F34=5E0302 (p.152), 9F02 = leg total amount, 9F03 = cash amount, 5F34 = EMV Card Sequence Number; ATC / 9A / 9F37 per transaction; TVR byte for PIN entry follows the leg's CVM; application cryptogram / IAD are per-leg SIMULATOR PLACEHOLDERS (EMV_PLACEHOLDER) - a card simulator or the client's test-card keys supply them
- EMV Additional Information Length = character count of EMV Additional Information (element 192, p.480)
- EMV_ADDITIONAL_INFORMATION: indicator 001 content 'EMVYES' (p.701: EMVYES This code indicates that the device is able to receive EMV Table Data.); framing with Table ID/Length pending SME (RV-03)

<a id="s4"></a>

## 4. Field-by-field check

For each row: find the JSON key in the test data file, read the value, apply the ATL105 rule (linked) and compare with the expected value.

### 4.1 Step 1 - [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json)

| # | Field (Element) | JSON key | Value in the file | ATL105 rule | How to check | Expected | AI result | Your check |
|---|---|---|---|---|---|---|---|---|
| DS1-1 | Message Format Version Identifier (55) | `MessageFormatVersionIdentifier` | `ATL105` | Fixed 6 characters: ATL105 ([S11.1](#x-s11-1), [EL55](#x-el55)) | Read the value. | ATL105 | PASS | ☐ |
| DS1-2 | Number of Segments (63) | `NumberofSegments` | `03` | Up to two digits: Segment 100 plus N-1 other segments ([S11.1](#x-s11-1), [EL63](#x-el63)) | Count the segment objects: Standard Message Data Segment, Variable Information Data Segment, EMV Request Data Segment = 3. | 03 | PASS | ☐ |
| 100-1 | Segment Type (85) | `SegmentType` | `100` | Fixed value: 100 ([S12.1](#x-s12-1), [EL85](#x-el85)) | Read the value. | 100 | PASS | ☐ |
| 100-2 | Segment Length (84) | `SegmentLength` | `093` | Length of all 17 fields + all Field Separators ([S12.1](#x-s12-1), [EL84](#x-el84)) | Add up the fields - section 5.1, step 1. | 092 + the run-time value(s) of Encrypted PIN Block Data | FAIL | ☐ |
| 100-3 | Information Byte (44) | `InformationByte` | `0` | One character: ? download, 0 single-message, 1 multimessage ([EL44](#x-el44)) | `0` = single-message transmission. | 0 (single-message) | PASS | ☐ |
| 100-4 | Terminal Identifier (102) | `TerminalIdentifier` | `JX41894821001` | Up to 22 characters: Device Type + ANSI State Code + BUYPASS Merchant Number + Device Number; assigned by BUYPASS ([EL102](#x-el102), [APP-D](#x-app-d)) | 13 characters (at most 22). Device Type `JX`, State Code `41` = Oregon (OR), United States (Appendix D), then `894821001` = BUYPASS Merchant Number (6-15) + Device Number (3). Only BUYPASS can confirm it. | Valid layout; the real value comes from BUYPASS (placeholder here) | PASS | ☐ |
| 100-5 | Prompt Code (78) | `PromptCode.TransactionType + PromptCode.CardType` | `0020` | 4 characters: Transaction Type (Appendix G) + Card Type (Appendix E) ([EL78](#x-el78), [APP-G](#x-app-g), [APP-E](#x-app-e)) | JSON splits it into TransactionType `0` and CardType `020`; on the wire `0020`. Appendix G: `0` = POS Purchase/Capture; Appendix E: `020` = Credit. | 0020 | PASS | ☐ |
| 100-6 | Account Number (2) | `AccountNumber` | `5147295465216009` | Up to 24 characters ([EL2](#x-el2)) | 16 digits (at most 24). MasterCard range 51-55 / 2221-2720 (starts 51); last digit is a Luhn check digit - section 5.3. | MasterCard number, valid check digit | PASS | ☐ |
| 100-7 | Card Discretionary Block Data (12) | `CardDiscretionaryBlockData` | `0931` | Keyed card: expiry MMYY ([EL12](#x-el12), [S10.1.1](#x-s10-1-1)) | `0931` = month 09, year 2031 = September 2031; not expired on November 2026. | MMYY, valid month, not expired | FAIL | ☐ |
| 100-8 | Encrypted PIN Block Data (33) | `EncryptedPINBlockData` | `<AWAITING:EncryptedPINBlockData>` | Conditional (C): encrypted PIN block (DUKPT); this keyed credit transaction has no PIN ([S12.1](#x-s12-1)) | The file has `<AWAITING:EncryptedPINBlockData>`; check why. | (empty) | BLOCKED | ☐ |
| 100-9 | Pump/Lane Number (79) | `PumpLaneNumber` | `1` | Conditional (C): pump or lane number; not a petroleum transaction ([S12.1](#x-s12-1)) | The file has `1`; check why. | (empty) | PASS | ☐ |
| 100-10 | Fuel Purchase Amount (41) | `FuelPurchaseAmount` | `1` | Conditional (C): fuel purchase amount; no fuel ([S12.1](#x-s12-1)) | The file has `1`; check why. | (empty) | PASS | ☐ |
| 100-11 | Nonfuel Amount (58) | `NonfuelAmount` | `1500` | Up to 8 digits, two assumed decimals; at most 7 digits for non-American Express cards ([EL58](#x-el58)) | `1500` = $15.00; digits only, 4 digits. | 1500 ($15.00, scenario amount) | PASS | ☐ |
| 100-12 | Tax Amount (99) | `TaxAmount` | `1` | Conditional (C): tax amount; not sent ([S12.1](#x-s12-1)) | The file has `1`; check why. | (empty) | PASS | ☐ |
| 100-13 | Cash Amount (17) | `CashAmount` | `1` | Conditional (C): cash amount (cash back); not used ([S12.1](#x-s12-1)) | The file has `1`; check why. | (empty) | PASS | ☐ |
| 100-14 | Sequence Number (86) | `SequenceNumber` | `772951` | 6 digits 000001-999999, unique per transaction; follow-ons carry the original's number ([EL86](#x-el86)) | `772951`: 6 digits, not 000000. Unique for this terminal (section 10). | 6 digits, unique | PASS | ☐ |
| 100-15 | Approval Number (5) | `ApprovalNumber` | `0CI2N6` | Conditional: required on reversals; not sent on a purchase or a Time-out Reversal ([EL5](#x-el5)) | The file has `0CI2N6`. | (empty) on a purchase; 6 characters on a reversal | FAIL | ☐ |
| 100-16 | Local Date and Local Time (49) | `LocalDateandLocalTime` | `0615251430` | Conditional (C): local date and time of a preauthorized transaction; not preauthorized ([S12.1](#x-s12-1)) | The file has `0615251430`; check why. | (empty) | PASS | ☐ |
| 100-17 | Partial Approval Indicator (121) | `PartialApprovalIndicator` | `1` | One digit: 0 not supported, 1 supported, 5 card balance only (American Express prepaid) ([EL121](#x-el121)) | `1` = partial approval supported; required on all transactions. | 0 or 1 (merchant setting; 1 here) | PASS | ☐ |
| 111-1 | Segment Type (85) | `SegmentType` | `111` | Fixed value: 111 ([S12.10](#x-s12-10), [EL85](#x-el85)) | Read the value. | 111 | PASS | ☐ |
| 111-2 | Segment Length (84) | `SegmentLength` | `042` | Length incl. Segment Type and Field Separators ([S12.10](#x-s12-10), [EL84](#x-el84)) | Section 5.2, step 1. | 042 | PASS | ☐ |
| 111-3.1 | Variable Information Indicator (111) | `VariableInformationSection[0].VariableInformationIndicator` | `005` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-005](#x-app-i-005)) | `005` = Table 005. | 005 | PASS | ☐ |
| 111-4.1 | Variable Information Length (112) | `VariableInformationSection[0].VariableInformationLength` | `003` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `051` has 3 characters. | 003 | PASS | ☐ |
| 111-5.1 | Variable Information (113) - Table 005 | `VariableInformationSection[0].VariableInformation` | `051` | PAN Entry Mode (1-2) + Terminal PIN Entry Capability (3) ([EL113](#x-el113), [APP-I-005](#x-app-i-005), [APP-J](#x-app-j)) | Positions 1-2 `05` = chip read; position 3 `1` = PIN entry capability (Appendix J). Manual / Keyed scenario: 01. | 011 (01 keyed + 1 PIN capability of a chip terminal) | PASS | ☐ |
| 111-3.2 | Variable Information Indicator (111) | `VariableInformationSection[1].VariableInformationIndicator` | `031` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-031](#x-app-i-031)) | `031` = Table 031. | 031 | PASS | ☐ |
| 111-4.2 | Variable Information Length (112) | `VariableInformationSection[1].VariableInformationLength` | `002` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `12` has 2 characters. | 002 | PASS | ☐ |
| 111-5.2 | Variable Information (113) - Table 031 | `VariableInformationSection[1].VariableInformation` | `12` | Terminal capability, required on every transaction ([APP-I-031](#x-app-i-031)) | `12` = hybrid reader: contact chip and contactless chip. With Table 005 position 3 = 1 (chip terminal, Appendix J note) the two tables describe the same terminal. | 12 (hybrid chip terminal of this merchant) | PASS | ☐ |
| 111-3.3 | Variable Information Indicator (111) | `VariableInformationSection[2].VariableInformationIndicator` | `030` | 3 characters: the table ID ([EL111](#x-el111), [APP-Q](#x-app-q)) | `030` = Table 030. | 030 | PASS | ☐ |
| 111-4.3 | Variable Information Length (112) | `VariableInformationSection[2].VariableInformationLength` | `010` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `0100000004` has 10 characters. | 010 | PASS | ☐ |
| 111-5.3 | Variable Information (113) - Table 030 | `VariableInformationSection[2].VariableInformation` | `0100000004` | POS Condition Code, 10 digits (Appendix Q) ([APP-Q](#x-app-q)) | Terminal Class `010` = attended, card acceptor-operated, on premise; Presentation Type `0000` = customer present, card present, device does not have card retention capability, original presentment; Security Condition `0` = no security concern; Terminal Type `04` = ECR. | 0100000004 (attended, card acceptor-operated, on premise ECR; customer and card present) for an attended store | PASS | ☐ |

<a id="s5"></a>

## 5. Calculations

<a id="s5-1"></a>
### 5.1 Segment 100 length

Rule ([S12.1](#x-s12-1), [EL84](#x-el84)): count every field of the segment - including Segment Type and Segment Length - plus one Field Separator after each of the 17 fields, even when the field is empty.

**Step 1 - TD-TC-077295.json**

| No. | Field | Value counted | Characters | Field Separator | Running total |
|---|---|---|---|---|---|
| 1 | Segment Type | `100` | 3 | 1 | 4 |
| 2 | Segment Length | `093` | 3 | 1 | 8 |
| 3 | Information Byte | `0` | 1 | 1 | 10 |
| 4 | Terminal Identifier | `JX41894821001` | 13 | 1 | 24 |
| 5 | Prompt Code | `0020` | 4 | 1 | 29 |
| 6 | Account Number | `5147295465216009` | 16 | 1 | 46 |
| 7 | Card Discretionary Block Data | `0931` | 4 | 1 | 51 |
| 8 | Encrypted PIN Block Data | (empty) (test case value; the file holds `<AWAITING:EncryptedPINBlockData>`) | 0 | 1 | 52 |
| 9 | Pump/Lane Number | `1` | 1 | 1 | 54 |
| 10 | Fuel Purchase Amount | `1` | 1 | 1 | 56 |
| 11 | Nonfuel Amount | `1500` | 4 | 1 | 61 |
| 12 | Tax Amount | `1` | 1 | 1 | 63 |
| 13 | Cash Amount | `1` | 1 | 1 | 65 |
| 14 | Sequence Number | `772951` | 6 | 1 | 72 |
| 15 | Approval Number | `0CI2N6` | 6 | 1 | 79 |
| 16 | Local Date and Local Time | `0615251430` | 10 | 1 | 90 |
| 17 | Partial Approval Indicator | `1` | 1 | 1 | 92 |

Total **092**; the file declares `093`: **MISMATCH**.

<a id="s5-2"></a>
### 5.2 Segment 111 length

Rule ([S12.10](#x-s12-10), [EL84](#x-el84)): Field Separator after Field 1 and Field 2; none inside or between the tables; one after the final table.

**Step 1**

| Part | Value | Characters | Field Separator | Running total |
|---|---|---|---|---|
| Segment Type | `111` | 3 | 1 | 4 |
| Segment Length | `042` | 3 | 1 | 8 |
| Table 005: indicator + length + data | `005003051` | 9 | 0 | 17 |
| Table 031: indicator + length + data | `03100212` | 8 | 0 | 25 |
| Table 030: indicator + length + data | `0300100100000004` | 16 | 1 | 42 |

Total **042**; the file declares `042`: **match**.

<a id="s5-3"></a>
### 5.3 Card number check digit (Luhn) and expiry

ATL105 refers card numbers to the BUYPASS IIN Reference Manual ([EL2](#x-el2)); MasterCard numbers start with 51-55 or 2221-2720 and end with a Luhn check digit: from the right, double every second digit (subtract 9 above 9) and add everything; the sum must end in 0.

**`5147295465216009`**

| Position | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Digit | 5 | 1 | 4 | 7 | 2 | 9 | 5 | 4 | 6 | 5 | 2 | 1 | 6 | 0 | 0 | 9 |
| Doubled? | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  |
| Value added | 1 | 1 | 8 | 7 | 4 | 9 | 1 | 4 | 3 | 5 | 4 | 1 | 3 | 0 | 0 | 9 |

Sum = 60 → check digit **valid**; starts with `51` → **in** the MasterCard range.

| Step | Card Discretionary Block Data | Format | Meaning | Check |
|---|---|---|---|---|
| 1 | `0931` | MMYY ([EL12](#x-el12)) | September 2031 | month valid; **not expired** on November 2026 |

<a id="s6"></a>

## 6. Links between the steps

Not applicable - this test case has one step.

<a id="s7"></a>

## 7. Expected responses

Response Codes are listed in [EL83X](#x-el83x). Tier: ASSERT = taken from ATL105; FLAG / INFER = not fully grounded, to be confirmed.

| Step | Expected | Tier | Meaning | ATL105 basis | AI result | Your check |
|---|---|---|---|---|---|---|
| 1 | `1` | ASSERT | Declined—All other transactions | [EL83X](#x-el83x) | PASS | ☐ |

<a id="s8"></a>

## 8. Citations written in the test case (`spec_basis`)

ATL105 quotes were checked word for word on the cited page by the AI validation; quotes from other documents (Petroleum Industry Processing Specification, EMV guide) are listed but not checked here.

| # | Source | Page cited | Quote | AI check | Your check |
|---|---|---|---|---|---|
| 1 | ATL105 | p.None (printed ?) | Identifies the length of the segment in which it appears. | - | ☐ |

<a id="s9"></a>

## 9. What is wrong, missing or to confirm - and how to check each finding by hand

| # | Kind | Finding | Step | Severity | Found by |
|---|---|---|---|---|---|
| [1](#i1) | Test data defect | Card Discretionary Block Data is not valid | 1 | FAIL | Trained AI validator |
| [2](#i2) | Test data defect | Approval Number is not valid | 1 | FAIL | Trained AI validator |
| [3](#i3) | Citation issue | The citation of the rule gives no ATL105 page | all | REVIEW | Trained AI validator |
| [4](#i4) | Awaiting client input / run-time value | The CA Public Key File Checksum is a sample value | all | REVIEW | Trained AI validator |
| [5](#i5) | Awaiting client input / run-time value | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | all | REVIEW | Trained AI validator |
| [6](#i6) | Awaiting client input / run-time value | The Terminal Identifier is a placeholder | all | REVIEW | Trained AI validator |
| [7](#i7) | Awaiting client input / run-time value | Encrypted PIN Block Data holds instruction text, not a value | 1 | BLOCKED | Trained AI validator |
| [8](#i8) | Intended violation of a negative test (correct) | Segment Length is not valid | 1 | FAIL | Trained AI validator |

<a id="i1"></a>

### 9.1 Card Discretionary Block Data is not valid - Test data defect

| Item | Value |
|---|---|
| Where | TD-TC-077295.json -> Standard Message Data Segment -> CardDiscretionaryBlockData |
| Value | `0931` |
| ATL105 | [EL12](#x-el12) Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."<br>[EL12](#x-el12) Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2." |
| Why | '0931' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does. |
| What it should be | A value that meets: AN, variable length 51. |
| Defect id (summary / sheet) | D-13 |

**How to confirm by hand**

1. Where: TD-TC-077295.json -> Standard Message Data Segment -> CardDiscretionaryBlockData - value `0931`.
2. ATL105: Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."; Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2.".
3. Compare: '0931' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i2"></a>

### 9.2 Approval Number is not valid - Test data defect

| Item | Value |
|---|---|
| Where | TD-TC-077295.json -> Standard Message Data Segment -> ApprovalNumber |
| Value | `0CI2N6` |
| ATL105 | [EL5](#x-el5) Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization." |
| Why | '0CI2N6' is sent on a purchase, which makes it preauthorized, so BUYPASS does not go out for authorization. |
| What it should be | A value that meets: Not sent on a purchase. |
| Defect id (summary / sheet) | D-09 |

**How to confirm by hand**

1. Where: TD-TC-077295.json -> Standard Message Data Segment -> ApprovalNumber - value `0CI2N6`.
2. ATL105: Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization.".
3. Compare: '0CI2N6' is sent on a purchase, which makes it preauthorized, so BUYPASS does not go out for authorization.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i3"></a>

### 9.3 The citation of the rule gives no ATL105 page - Citation issue

| Item | Value |
|---|---|
| Where | test case spec_basis |
| Value | `Identifies the length of the segment in which it appears.` |
| ATL105 | - |
| Why | The spec_basis entry quotes "Identifies the length of the segment in which it appears." with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence. |
| What it should be | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| Defect id (summary / sheet) | Q-01 |

**How to confirm by hand**

1. Open [test_cases.jsonl line 73](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73), `spec_basis`: the entry with page ? quotes "Identifies the length of the segment in which it appears.".
2. Search that text in the PDF (Ctrl+F) on the cited page.
3. Conclusion: QUOTE_NO_PAGE. The test data is not affected.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i4"></a>

### 9.4 The CA Public Key File Checksum is a sample value - Awaiting client input / run-time value

| Item | Value |
|---|---|
| Where | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) |
| Value | `2012091700000100000102171` |
| ATL105 |  Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device." |
| Why | The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value. |
| What it should be | Confirm the checksum of the host's current CA Public Key file before execution. |
| Defect id (summary / sheet) | I-04 |

**How to confirm by hand**

1. Where: EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) - value `2012091700000100000102171`.
2. ATL105: Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device.".
3. Compare: The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i5"></a>

### 9.5 Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders - Awaiting client input / run-time value

| Item | Value |
|---|---|
| Where | EMV Request Data Segment -> EMVChipData (every EMV request) |
| Value | `9F26=6F1D5B4CA7CC2180, 9F36=9DED, 9F37=B…5=8000008000` |
| ATL105 |  Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface." |
| Why | ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer. |
| What it should be | Produce the chip data with a card simulator / HSM before execution. |
| Defect id (summary / sheet) | I-04 |

**How to confirm by hand**

1. Where: EMV Request Data Segment -> EMVChipData (every EMV request) - value `9F26=6F1D5B4CA7CC2180, 9F36=9DED, 9F37=BC8BBA14, 95=8000008000`.
2. ATL105: Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface.".
3. Compare: ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i6"></a>

### 9.6 The Terminal Identifier is a placeholder - Awaiting client input / run-time value

| Item | Value |
|---|---|
| Where | Standard Message Data Segment -> TerminalIdentifier (every step) |
| Value | `JX41894821001` |
| ATL105 | [EL102](#x-el102) Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."<br>[EL102](#x-el102) Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."<br>[APP-D](#x-app-d) Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Oregon OR 41" |
| Why | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX41894821001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '41' = Oregon (OR), United States. |
| What it should be | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| Defect id (summary / sheet) | I-01 |

**How to confirm by hand**

1. Open [test_cases.jsonl line 73](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73): `merchant.name` = "MERCHANT 01 (POS PLACEHOLDER)", `merchant_provenance` = "placeholder".
2. Read [EL102](#x-el102): "The Terminal Identifier must be assigned by and set up at BUYPASS."
3. Positions 3-4 of `JX41894821001` are `41` = Oregon (OR), United States ([APP-D](#x-app-d)).
4. Conclusion: ask the client / BUYPASS for the real Terminal Identifier before execution.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i7"></a>

### 9.7 Encrypted PIN Block Data holds instruction text, not a value - Awaiting client input / run-time value

| Item | Value |
|---|---|
| Where | TD-TC-077295.json -> Standard Message Data Segment -> EncryptedPINBlockData |
| Value | `<AWAITING:EncryptedPINBlockData>` |
| ATL105 | - |
| Why | The field holds '<AWAITING:EncryptedPINBlockData>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value). |
| What it should be | Client to supply the value (for Account Number: a MasterCard test card). |
| Defect id (summary / sheet) | I-09 |

**How to confirm by hand**

1. Where: TD-TC-077295.json -> Standard Message Data Segment -> EncryptedPINBlockData - value `<AWAITING:EncryptedPINBlockData>`.
2. Compare: The field holds '<AWAITING:EncryptedPINBlockData>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value).

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i8"></a>

### 9.8 Segment Length is not valid - Intended violation of a negative test (correct)

| Item | Value |
|---|---|
| Where | TD-TC-077295.json -> Standard Message Data Segment -> SegmentLength |
| Value | `093` |
| ATL105 | [S12.1](#x-s12-1) Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."<br>[EL84](#x-el84) Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements." |
| Why | '093' is declared, but the fields add up to 092. |
| What it should be | 092 |
| Defect id (summary / sheet) | N-01 |

**How to confirm by hand**

1. Where: TD-TC-077295.json -> Standard Message Data Segment -> SegmentLength - value `093`.
2. ATL105: Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."; Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements.".
3. Compare: '093' is declared, but the fields add up to 092.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="s10"></a>

## 10. Checks across test cases

| Check | Result | Detail |
|---|---|---|
| Sequence Number step 1 `772951` | PASS | new transaction: unique on terminal JX41894821001 across all 1,030 test cases |

<a id="s11"></a>

## 11. ATL105 text used in this guide

Copied from the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). Each line below was found on the stated PDF page when this guide was generated.

<a id="x-s10-1-1"></a>

### [S10.1.1] Section 10.1.1 Card Requirements (10.1.1.1 Expiration Date, 10.1.1.3 Manual Entry)

*PDF p.81 (printed 10-2)*

> 10.1.1.1 Expiration Date
>
> The device performs expiration date validation in a credit card transaction.
>
> Note: Dates up to and including December, 2049 (Format: 1249) are assumed to be in the twenty-first century.
>
> 10.1.1.3 Manual Entry
>
> Manual entry is allowed on a credit card transaction. If the device is unable to read the card's magnetic strip, it should prompt the user to swipe the card again—up to three times. If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY).
>
> Note: If manual entry is used, this fact is indicated on the receipt and journal by an asterisk (\*) before the Account Number.

<a id="x-s11-1"></a>

### [S11.1] Section 11.1 Financial Transactions - layout of a Financial Transaction Request

*PDF p.167-168 (printed 11-4 to 11-5)*

> All Financial Transaction Requests contain Element Nos. 55 and 63.
>
> Note: A Field Separator separates Field Nos. 1 and 2; and a Field Separator follows Field No. 2.

| Field No. | Ele. No. | Element Name | Len. | Entry (R/O/C) | Description | PDF p. |
|---|---|---|---|---|---|---|
| 1 | 55 | Message Format Version Identifier | 6 | R | Identifies the message format type and release version. | 167 |
| 2 | 63 | Number of Segments | 2 | R | Identifies the number of BUYPASS-defined segments included in the message. | 167 |

> All Financial Transaction Requests contain Segment No. 100.

| Field No. | Seg. No. | Segment Name | Max. Len. | Entry (R/O/C) | Description | PDF p. |
|---|---|---|---|---|---|---|
| 3 | 100 | Standard Message Data Segment | 218 | R | Contains the standard data required for all financial transactions. This segment is the only segment required for all financial transactions. | 167 |

*PDF p.168 (printed 11-5):*

> All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9.

| Field No. | Seg. No. | Segment Name | Max. Len. | Entry (R/O/C) | Description | PDF p. |
|---|---|---|---|---|---|---|
| 4–9 | 111 | Variable Information Data Segment | 999 | C | Contains data elements unique to transactions requiring variable information. | 168 |

<a id="x-s12-1"></a>

### [S12.1] Section 12.1 Standard Message Data Segment (Segment 100)

*PDF p.223-225 (printed 12-6 to 12-8)*

> It is the only required data segment on all Financial Transaction Request messages.
>
> Any trailing fields within the data segment that are not needed should not be transmitted. For example, if a data segment has five fields defined, but the last two fields are optional, only three fields should be transmitted with the third field followed by a Field Separator.
>
> Note: All fields are separated by a Field Separator. When a field is not populated, still send the Field Separator.

| Field No. | Element No. | Element Name | Len. | Entry (R/O/C) | Description | PDF p. |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | 3 | R | Identifies the type of data segment being formatted. Fixed value: 100 Source: Device | 223 |
| 2 | 84 | Segment Length | 3 | R | Identifies the data segment’s length, including Segment Type’s length and Field Separators. Source: Device | 223 |
| 3 | 44 | Information Byte | 1 | R | Indicates whether the request is a single-message request or a multimessage request. Source: Device | 223 |
| 4 | 102 | Terminal Identifier | Var. | R | Identifies the device by Device Type, State Code, BUYPASS Merchant Number, and Device Number. Source: Device | 223 |
| 5 | 78 | Prompt Code | 4 | R | Identifies the Transaction Type and Card Type. Source: Device | 223 |
| 6 | 2 | Account Number | Var. | R | Identifies a portion of the cardholder’s account number or identification number. Source: Device | 224 |
| 7 | 12 | Card Discretionary Block Data | 51 | C | Identifies the card’s expiration date and discretionary data. Source: Device | 224 |
| 8 | 33 | Encrypted PIN Block Data | 36 | C | For DUKPT PIN encryption, this is a 16- to 20-byte KSN plus a 16-byte PIN block. Source: Cardholder | 224 |
| 9 | 79 | Pump/Lane Number | 2 | C | Identifies the pump or lane number at the point-of-service location. Source: Device | 224 |
| 10 | 41 | Fuel Purchase Amount | 8 | C | Identifies the net total dollar amount of all fuel purchase amounts less any applicable discounts and/or coupons. Source: Device | 224 |
| 11 | 58 | Nonfuel Amount | 8 | C | Identifies the total net dollar amount of all nonfuel, noncash, and nontax amounts less any applicable discounts and/or coupons. Source: Device | 224 |
| 12 | 99 | Tax Amount | 8 | C | Identifies the total dollar amount of all tax amounts. Source: Device | 224 |
| 13 | 17 | Cash Amount | 8 | C | Identifies the total dollar amount of all cash amounts. Source: Device | 224 |
| 14 | 86 | Sequence Number | 6 | R | Identifies the unique transaction sequence number assigned to and associated with a transaction throughout its life. Source: Device | 224 |
| 15 | 5 | Approval Number | 6 | C | Identifies the assigned Approval Number. Source: Device | 224 |
| 16 | 49 | Local Date and Local Time | 10 | C | Identifies the local date and local time of the preauthorized transaction. Source: Device | 225 |
| 17 | 121 | Partial Approval Indicator | 1 | R | Indicates whether or not a merchant supports credit/debit Partial Approvals. Source: Device | 225 |

<a id="x-s12-10"></a>

### [S12.10] Section 12.10 Variable Information Data Segment (Segment 111)

*PDF p.246 (printed 12-29)*

> This section describes Data Segment No. 111, Variable Information Data Segment.
>
> This data segment can appear in any of the fields in data Section No. 3.
>
> It has a maximum length of 999 alphanumeric characters (01–999/a-z/A–Z).
>
> Note: There is a Field Separator between Field Nos. 1 and 2 and between Field No. 2 and Variable Information Section. Field Separators are not between the elements in Variable Information Section. Field Separators are not between repetitions of Variable Information Section. A Field Separator follows the final Variable Information Section.

| Field No. | Element No. | Element Name | Len. | Entry (R/O/C) | Description | PDF p. |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | 3 | R | Identifies the type of data segment being formatted. Fixed value: 111 Source: Device | 246 |
| 2 | 84 | Segment Length | 3 | R | Identifies the data segment’s length, including Segment Type’s length and Field Separators. Source: Device | 246 |

> Variable Information Section: This section is repeated—by Variable Information Indicator—for a total variable length of up to 991 bytes. The Field Separator that follows Field No. 5 is included with the last repetition.

| Field No. | Element No. | Element Name | Len. | Entry (R/O/C) | Description | PDF p. |
|---|---|---|---|---|---|---|
| 3 | 111 | Variable Information Indicator | 3 | R | Identifies the type of variable information. Source: Device | 246 |
| 4 | 112 | Variable Information Length | 3 | R | Identifies the length of Variable Information (Element No. 113). Source: Device | 246 |
| 5 | 113 | Variable Information | Var. | R | Identifies the variable information. Source: Device | 246 |

<a id="x-el2"></a>

### [EL2] Element 2 Account Number (Chapter 13)

*PDF p.363-365 (printed 13-23 to 13-25)*

> Number: 2 Name: Account Number
>
> Character Type: ANS Maximum Length: Non-TransArmor (all requests): 24 bytes

*PDF p.364 (printed 13-24):*

> Non-TransArmor—Initial Transaction—Request: Format: 123456789012nn…nn Where 123456789012nn…nn = a variable length (up to 24 alphanumeric bytes) Account Number.
>
> Purpose: Non-TransArmor: Identifies the card or account.

*PDF p.365 (printed 13-25):*

> Valid Codes/Values: Credit, debit, and EBT cards: Please refer to the most recent version of BUYPASS® Platform Issuer Identification Number (IIN) Reference Manual.

<a id="x-el5"></a>

### [EL5] Element 5 Approval Number (Chapter 13)

*PDF p.368 (printed 13-28)*

> Number: 5 Name: Approval Number
>
> Character Type: AN Maximum Length: 6 bytes
>
> Representation: Fixed length of six alphanumeric characters; space-filled
>
> Purpose: Identifies the assigned Approval Number.
>
> Processing Rules: Required on reversals for matching information to the original purchase. If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization.
>
> Valid Codes/Values: Any alphanumeric representation, including spaces

<a id="x-el12"></a>

### [EL12] Element 12 Card Discretionary Block Data (Chapter 13)

*PDF p.371-372 (printed 13-31 to 13-32)*

> Number: 12 Name: Card Discretionary Block Data
>
> Character Type: AN Maximum Length: 51 bytes
>
> Representation: Variable length of up to 51 alphanumeric characters
>
> Purpose: Identifies the card’s expiration date and/or discretionary data.

*PDF p.372 (printed 13-32):*

> Valid Codes/Values: For a card whose number has been manually entered, the expiration date is in MMYY format, and the other required data depends upon the application.
>
> Note: For all authorizations, merchants must send a value even for expired cards.

<a id="x-el44"></a>

### [EL44] Element 44 Information Byte (Chapter 13)

*PDF p.386 (printed 13-46)*

> Number: 44 Name: Information Byte
>
> Character Type: AN Maximum Length: 1 byte
>
> Representation: Fixed length of one alphanumeric character
>
> Single-message Request signifies one transaction is performed, and then communication is discontinued.
>
> Valid Codes/Values: Character Description<br>? Download Request<br>0 Single-message transmission<br>1 Multimessage transmission

<a id="x-el55"></a>

### [EL55] Element 55 Message Format Version Identifier (Chapter 13)

*PDF p.391-392 (printed 13-51 to 13-52)*

> Number: 55 Name: Message Format Version Identifier
>
> Character Type: AN Maximum Length: 6 bytes
>
> Representation: Fixed length of six alphanumeric characters
>
> Purpose: Identifies the type and release version of message format.
>
> For Financial Transaction Requests, the following data element and data segment always follow this data element:<br>• Element No. 63, Number of Segments<br>• Data Segment No. 100, Standard Message Data Segment

*PDF p.392 (printed 13-52):*

> Valid Codes/Values: Value Description<br>ATL105 Device/Controller (default)

<a id="x-el58"></a>

### [EL58] Element 58 Nonfuel Amount (Chapter 13)

*PDF p.393 (printed 13-53)*

> Number: 58 Name: Nonfuel Amount
>
> Character Type: N Maximum Length: 8 bytes
>
> Representation: Variable length of up to eight digits with two assumed decimal places
>
> Note: A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of 7 bytes is allowed for all other card types.
>
> Purpose: Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount.
>
> Valid Codes/Values: 1–99999999

<a id="x-el63"></a>

### [EL63] Element 63 Number of Segments (Chapter 13)

*PDF p.395 (printed 13-55)*

> Number: 63 Name: Number of Segments
>
> Character Type: N Maximum Length: 2 bytes
>
> Representation: Variable length of up to two digits
>
> Financial Transaction Requests: This data element is always followed by Data Section No. 2 which contains Data Segment No. 100, Standard Message Data Segment, plus Data Section No. 3 which contains none, one, or more of the of the input data segments listed in Chapter 12.
>
> Valid Codes/Values: The value of this field, call it N, indicates that the transaction will have the 100 segment + N-1 of the input segments listed in Chapter 12.

<a id="x-el78"></a>

### [EL78] Element 78 Prompt Code (Chapter 13)

*PDF p.400-401 (printed 13-60 to 13-61)*

> Number: 78 Name: Prompt Code
>
> Character Type: AN Maximum Length: 4 bytes
>
> Representation: Variable length of three or four alphanumeric characters
>
> Purpose: In the case of a financial transaction, this is a 4-character Prompt Code that identifies the transaction’s Transaction Type (one character) and Card Type (three characters).

*PDF p.401 (printed 13-61):*

> Processing Rules: Must be a valid Transaction Type code and/or Card Type code.
>
> Valid Codes/Values: In the case of a financial transaction, position no. 1 is the Transaction Type (For a list of valid entries, please see Appendix G. Valid Transaction Type Codes.); and position nos. 2–4 are the Card Type (For a list of valid entries, please see Appendix E. Valid Card Type Codes.).

<a id="x-el83x"></a>

### [EL83X] Element 83 Response Code (Chapter 13)

*PDF p.403-404 (printed 13-63 to 13-64)*

> Number: 83 Name: Response Code
>
> Purpose: For financial transactions, it identifies an approval with purchase/capture, an approval without purchase/capture, or a decline.

*PDF p.404 (printed 13-64):*

> 0 Approved—Purchase/Capture
>
> 1 Approved—Communications Test Declined—All other transactions
>
> 2 Approved—Authorization only

<a id="x-el84"></a>

### [EL84] Element 84 Segment Length (Chapter 13)

*PDF p.406-407 (printed 13-66 to 13-67)*

> Number: 84 Name: Segment Length
>
> Character Type: N Maximum Length: 4 bytes
>
> Representation: Variable length of three or four digits
>
> Purpose: Identifies the length of the segment in which it appears.
>
> Processing Rules: This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements.
>
> Note: Segment Length can only have a length of 4 digits when transmitting the following Data Segments. It cannot have a length of 4 digits when sending any other segment.

*PDF p.407 (printed 13-67):*

> 001–218 Standard Message Data Segment (No. 100)
>
> 001–999 Variable Information Data Segment (No. 111)

<a id="x-el85"></a>

### [EL85] Element 85 Segment Type (Chapter 13)

*PDF p.408 (printed 13-68)*

> Number: 85 Name: Segment Type
>
> Character Type: N Maximum Length: 3 bytes
>
> Representation: Fixed length of three digits
>
> 100 Data Segment No. 100, Standard Message Data Segment
>
> 111 Data Segment No. 111, Variable Information Data Segment

<a id="x-el86"></a>

### [EL86] Element 86 Sequence Number (Chapter 13)

*PDF p.409 (printed 13-69)*

> Number: 86 Name: Sequence Number
>
> Character Type: N Maximum Length: 6 bytes
>
> Representation: Fixed length of six digits
>
> Purpose: Note: Identifies a transaction for the life of the transaction. Sequence Number must persist in all the subsequent transactions that are associated with the initial authorization request.
>
> Processing Rules: Each transaction has a unique Sequence Number. It is included on all transaction types, and originates at the device.
>
> In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request.
>
> Valid Codes/Values: 000001–999999

<a id="x-el102"></a>

### [EL102] Element 102 Terminal Identifier (Chapter 13)

*PDF p.417-418 (printed 13-77 to 13-78)*

> Number: 102 Name: Terminal Identifier
>
> Character Type: AN Maximum Length: 22 bytes
>
> Representation: Variable length up to 22 alphanumeric characters
>
> Purpose: Identifies the transaction device by Device Type, State Code (ANSI® Code), BUYPASS Merchant Number, and Device Number.
>
> Processing Rules: Required for all transactions and messages.
>
> The Terminal Identifier must be assigned by and set up at BUYPASS.
>
> Valid Codes/Values: Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel.
>
> State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). Please see Appendix D. Valid State Codes, for a list of valid ANSI® Code entries.
>
> BUYPASS Merchant Number (Starts immediately in Pos. No. 5.) Any valid 6- to 15-character BUYPASS Merchant Number assigned by BUYPASS personnel. The Device Number immediately follows the BUYPASS Merchant Number.
>
> Device Number (Starts immediately after the BUYPASS Merchant Number.)

*PDF p.418 (printed 13-78):*

> Any valid 3-character Device Number assigned by BUYPASS personnel.

<a id="x-el111"></a>

### [EL111] Element 111 Variable Information Indicator (Chapter 13)

*PDF p.422 (printed 13-82)*

> Number: 111 Name: Variable Information Indicator
>
> Character Type: AN Maximum Length: 3 bytes
>
> Representation: Fixed length of 3 alphanumeric characters
>
> Purpose: Identifies the type of variable information in a Variable Information Data Segment.

<a id="x-el112"></a>

### [EL112] Element 112 Variable Information Length (Chapter 13)

*PDF p.425 (printed 13-85)*

> Number: 112 Name: Variable Information Length
>
> Character Type: N Maximum Length: 3 bytes
>
> Representation: Fixed length of 3 bytes
>
> Purpose: Identifies the length of Variable Information (Element No. 113) in the Variable Information Data Segment.
>
> Valid Codes/Values: 001–999

<a id="x-el113"></a>

### [EL113] Element 113 Variable Information (Chapter 13)

*PDF p.426 (printed 13-86)*

> Number: 113 Name: Variable Information
>
> Character Type: AN Maximum Length: 982 bytes
>
> When Element No. 111 (Variable Information Indicator) has a value of 005 (point-of-service entry mode indicator), the actual point-of-service entry mode (swiped/manual/RFID) is included here.

<a id="x-el121"></a>

### [EL121] Element 121 Partial Approval Indicator (Chapter 13)

*PDF p.440 (printed 13-100)*

> Number: 121 Name: Partial Approval Indicator
>
> Character Type: N Maximum Length: 1 byte
>
> Representation: Fixed length of 1 digit
>
> Processing Rules: Required on all transactions.
>
> 0 Not specified (default)/Not supported
>
> 1 Supported This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions.
>
> If the Merchant transmits this value with a transaction—and the corresponding network supports credit/debit/fleet Partial Approval transactions—the network can approve an amount that is less than the amount in the Authorization request; and BUYPASS will send a Partial Approval response.
>
> 5 This value indicates that the Merchant only supports the receipt of the card balance, not Partial Approval transaction processing.
>
> Note: This value is only supported for American Express prepaid cards.

<a id="x-app-d"></a>

### [APP-D] Appendix D. Valid State Codes

*PDF p.514-515 (printed Appendix D-1 to Appendix D-2)*

> The ANSI® State Codes are used in the following data element:<br>• Terminal Identifier (No. 102)
>
> The following table contains a list of valid State Codes for the states of the United States of America:

| State | Alphabetical Code | ANSI® Code | PDF p. |
|---|---|---|---|
| Oregon | OR | 41 | 515 |

<a id="x-app-e"></a>

### [APP-E] Appendix E. Valid Card Type Codes

*PDF p.521 (printed Appendix E-4)*

> The following table contains Card Type codes sent by the device to the host as part of the 4-character Prompt Code used in financial transaction processing:
>
> Note: These codes are preceded by a valid Transaction Type to create a valid Prompt Code. (See Appendix G. Valid Transaction Type Codes.)

| Code | Card Type | Card Type Description | PDF p. |
|---|---|---|---|
| 020 | Credit | Credit | 521 |

<a id="x-app-g"></a>

### [APP-G] Appendix G. Valid Transaction Type Codes

*PDF p.545-546 (printed Appendix G-1 to Appendix G-2)*

> Prompt Codes are transmitted by a device at the time a transaction is initiated. A valid Prompt Code (Element No. 78) is composed of a Transaction Type code and a Card Type code.

| Code | Transaction Type | Transaction Description | PDF p. |
|---|---|---|---|
| 0 | POS Purchase/Capture | Point-of-sale purchase/preauthorized completion | 545 |
| 3 | POS Authorization Only | Point-of-sale authorization only (no draft capture) | 545 |
| 5 | Customer-activated Authorization Only | Customer-activated authorization only (no draft capture) | 545 |
| 8 | Purchase reversal | Purchase reversal/void | 546 |
| S | Cancellation | Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction. | 546 |
| Z | Time-out Reversal | Time-out reversal | 546 |

<a id="x-app-i-005"></a>

### [APP-I-005] Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005)

*PDF p.555 (printed Appendix I-6)*

> Point-of-Service Entry Mode Information

| Field | Attributes | Description | PDF p. |
|---|---|---|---|
| Table ID | n | Fixed value: 005 | 555 |
| Table Length | n | 3 | 555 |
| Table Data | n | Fixed Len.: 3 | 555 |

> Note: The POS Entry Mode field must be populated for all transactions, which include EMV, Magstripe, and Key Entered transactions from all POS devices for any given solution.
>
> Please refer to Appendix J. Valid Point-of-Service Entry Mode Codes for valid entry information.

<a id="x-app-i-031"></a>

### [APP-I-031] Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031)

*PDF p.569 (printed Appendix I-20)*

> Point-of-Service Terminal Capability Codes

| Field | Attributes | Description | PDF p. |
|---|---|---|---|
| Table ID | n3 | Fixed Value: 031 | 569 |
| Table Length | n3 | Fixed Len.: 2 | 569 |
| Table Data | n2 | Identifies the terminal capability code of the point-of-sale terminal. Fixed length of 2 digits. | 569 |

> Terminal Capability values must be populated for all transactions, which include EMV, Magstripe, and Key Entered transactions from all POS devices for any given solution.
>
> Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode.
>
> Valid codes/values:<br>00 = Unknown or unspecified<br>01 = Terminal not used (Example: eCommerce and In App)<br>02 = Magnetic stripe read capability (no contact or contactless chip)<br>03 = Bar code capability<br>04 = Optical character recognition (OCR) read capability<br>05 = Integrated circuit card (ICC) read capability<br>06 = Contactless chip read capability (no contact chip)<br>07 = Contactless magnetic stripe read capability (no contact or contactless chip capability)<br>09 = Terminal does not read card data<br>12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities

<a id="x-app-j"></a>

### [APP-J] Appendix J. Valid Point-of-Service Entry Mode Codes

*PDF p.638-639 (printed Appendix J-1 to Appendix J-2)*

> This appendix lists valid entries for Variable Information (Element No. 113).
>
> A valid 3-digit Point-of-Service Entry Mode code has the following components:<br>• 2-digit PAN Entry Mode code in position nos. 1–2<br>• 1-digit Terminal Capability Mode code in position no. 3
>
> Note: The POS Entry Mode values listed below must persist throughout the lifecycle of the transactions, except as noted below the Valid PAN Entry Mode Codes table.

| Pos. Nos. 1–2 | Description | PDF p. |
|---|---|---|
| 01 | Manual/keyed | 638 |

> Due to credit authorizer (American Express, Diners Club®, Discover® Network, JCB®, MasterCard®, MasterCard® Fleet, Visa®, and Visa® Fleet) and debit authorizer regulatory requirements regarding storage of full track data, previously authorized credit and debit transactions (Voids and Completions) can only pass the account number and expiration date to BUYPASS. These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction, with one exception: They will use a PAN Entry Mode code 02 when referencing an original Purchase or Authorization transaction in which a PAN Entry Mode code 90 was used.

| Position 3 | Description | PDF p. |
|---|---|---|
| 0 | Unspecified | 639 |
| 1 | PIN entry capability (including both EMV Contact and Contactless) | 639 |
| 2 | No PIN entry capability (including both EMV Contact and Contactless) | 639 |
| 8 | Contactless Magnetic Stripe Read Capability | 639 |

> If more than one valid Terminal Capability Mode code exists for a POS device, the following hierarchy logic should be followed:<br>If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8; but<br>For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8. The PIN Entry Capability Mode code 8 is reserved only for Contactless MSR; or<br>If no Contactless MSR is attached, and the POS device has the PIN debit feature enabled, then all transactions will use Terminal PIN Entry Capability Mode code 1; or<br>If no Contactless MSR is attached, and the POS device has the PIN debit feature disabled, then all transactions will use Terminal PIN Entry Capability Mode code 2.

<a id="x-app-q"></a>

### [APP-Q] Appendix Q. Valid National Point-of-Service Condition Codes (Table 030)

*PDF p.568-696 (printed Appendix I-19 to Appendix Q-7)*

> Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions.

*PDF p.690 (printed Appendix Q-1):*

> This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030<br>The National Point-of-Service Condition Codes data element contains the following elements:<br>• Terminal Class (Position Nos. 1–3)<br>• Presentation Type (Position Nos. 4–7)<br>• Security Condition (Position No. 8)<br>• Terminal Type (Position Nos. 9–10)

*PDF p.691 (printed Appendix Q-2):*

> Position No. 1<br>Value Description<br>0 Attended<br>1 Unattended<br>2–7 Reserved for national use<br>8–9 Reserved for private use<br>Position No. 2<br>Value Description<br>0 Customer-operated<br>1 Card acceptor-operated<br>2 Administrative<br>3–7 Reserved for national use<br>8–9 Reserved for private use<br>Position No. 3<br>Value Description<br>0 On premise<br>1 Off premise

*PDF p.692 (printed Appendix Q-3):*

> Value Description<br>00 Administrative terminal<br>01 Point-of-service terminal<br>02 ATM<br>03 Home terminal<br>04 ECR<br>05 Dial terminal<br>06 Traveler's check machine<br>07 Fuel machine

*PDF p.693 (printed Appendix Q-4):*

> 010 04 Attended on premise Card acceptor-operated ECR
>
> 100 01 Unattended on premise customer-operated point-of-service terminal
>
> 100 07 Unattended on premise customer-operated fuel machine

*PDF p.696 (printed Appendix Q-7):*

> Position Nos. 4–7 Presentation Type Description<br>0000 Customer and card present<br>0100 Customer present; card not present<br>1000 Customer not present; card present<br>1100 Customer not present; card not present<br>2100 Mail and/or telephone order<br>8800 Preauthorized purchase
>
> Security Condition<br>Value Description<br>0 No security concern<br>1 Suspected fraud<br>2 Identification verified<br>3 Electronic commerce transaction with digital signature

<a id="s12"></a>

## 12. Sign-off

| Area | AI result | Your result | Comment |
|---|---|---|---|
| Field checks (section 4) | 43 PASS, 3 FAIL, 1 BLOCKED | ☐ Agree ☐ Disagree | |
| Links (section 6) | n/a | ☐ Agree ☐ Disagree | |
| Responses (section 7) | 1 PASS | ☐ Agree ☐ Disagree | |
| Citations (section 8) | n/a | ☐ Agree ☐ Disagree | |
| Findings (section 9) | 1 Intended violation of a negative test (correct), 2 Test data defect, 4 Awaiting client input / run-time value, 1 Citation issue | ☐ Agree ☐ Disagree | |
| **Overall** | **FAIL** - No - fix the defects first | ☐ Agree ☐ Disagree | |

Reviewed by: ____________________    Date: ____________

