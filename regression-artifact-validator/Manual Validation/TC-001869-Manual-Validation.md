# TC-001869 (BR1_TS5_TC4) - Manual Validation Guide

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Manual / Keyed Swiped/keyed at RFID-equipped terminal → approved**

This guide is a step-by-step path for checking this test case and its test data by hand against ATL105, so you can confirm or reject what the AI validator reported. The ATL105 text needed for every check is copied into [section 11](#s11); each copied line was checked against the ATL105 text on the stated page, so you do not need to open the specification.

| Item | Value |
|---|---|
| AI validator result | **FAIL** - can it run as-is? No - fix the FAIL items first ([AI report](../Result_MD_Folder/TC-001869.md)) |
| Test case record | [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148) (the whole test case is on this one line) |
| Test case summary | [TEST_CASES.md line 214](../test_cases/TEST_CASES.md#L214), [test_cases.csv line 150](../test_cases/test_cases.csv#L150) |
| Test data | [TD-TC-001869.json](../test_data/TD-TC-001869.json) |
| Template / type | `T2-RFID-TERMINAL` / T2 - Card capture path |
| Status in the test case | `ready` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

## How to read this guide

- **PDF p.** is the page number shown by the PDF viewer; **printed** is the page number in the ATL105 page footer.
- Links such as **[EL58]** jump to the ATL105 text copied in [section 11](#s11).
- A **Field Separator** is the separator character that follows a field in the real (wire) message; it counts as one character.
- **AI result** is what the AI validator decided. **Your check** is for you: write ☑ when you agree, or note what you found.
- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

<a id="s1"></a>

## 1. What this test case does

| Item | Value | Field in the test case record |
|---|---|---|
| Objective | Swiped/keyed at RFID-equipped terminal -> approved | `test_objective` |
| Validation focus | Card capture path | `validation_focus` |
| Scenario | TS-ATL105-CREDIT-MASTERCARD-PURCHASE-MANUAL (BR1_TS5) | `scenario_id` |
| Card | MasterCard Credit | `dimension_labels` |
| Transaction / entry mode | Purchase/Capture / Manual / Keyed | `dimension_labels` |
| Merchant | MERCHANT 01 (POS PLACEHOLDER) (id M01), merchant_provenance = placeholder | `merchant, merchant_provenance` |
| Transaction Type per step | 0 | `message_chain` |
| How to run step 1 | Terminal has a contactless (RFID) receiver attached and no chip reader; the card is swiped or keyed | `tx_legs[0].execution` |

| Step | Role | Prompt Code | Expected Response Code | Test data file |
|---|---|---|---|---|
| 1 | purchase | `0020` = POS Purchase/Capture, card type 020 | `0` = Approved—Purchase/Capture (ASSERT) | [TD-TC-001869.json](../test_data/TD-TC-001869.json) |

<a id="s2"></a>

## 2. Manual validation path (do these steps in order)

1. **Open the test case.** Open [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148). The record is one long line: in VS Code press Ctrl+G, enter 148, copy the line into a new file, set the language to JSON and press Shift+Alt+F to format it. Confirm the id `TC-001869`, display id `BR1_TS5_TC4`, template, status and steps shown in [section 1](#s1). The same test case is summarised in [TEST_CASES.md line 214](../test_cases/TEST_CASES.md#L214).
2. **Open the test data file** next to this guide: [TD-TC-001869.json](../test_data/TD-TC-001869.json).
3. **Check the message structure** ([S11.1](#x-s11-1), [S12.1](#x-s12-1), [S12.10](#x-s12-10)): the root is `Financial Transaction Request` with `MessageFormatVersionIdentifier` and `NumberofSegments` (Data Section 1), `Standard Message Data Segment` (Segment 100, 17 fields in ATL105 order) and `Variable Information Data Segment` (Segment 111 with Tables 005 and 031).
4. **See how each value was produced** ([section 3](#s3)): compare every value in the test data file with the value and generation method recorded in the test case.
5. **Check every field** ([section 4](#s4)): read the value in the file, apply the ATL105 rule shown, compare with the expected value and mark the Your check column.
6. **Recalculate** the segment lengths, the card number check digit and the expiry dates ([section 5](#s5)).
7. **Links between steps** ([section 6](#s6)): not applicable - this test case has one step.
8. **Check the expected Response Codes** ([section 7](#s7)).
9. **Check the ATL105 citations written in the test case** ([section 8](#s8)).
10. **Confirm each issue the AI reported** ([section 9](#s9)) with its 'How to confirm by hand' steps.
11. **Review the checks across the five test cases** ([section 10](#s10)).
12. **Record your conclusion** ([section 12](#s12)).

<a id="s3"></a>

## 3. How the test data was generated

The test data file is the wire-ready copy of the request stored in the test case. For each field the test case records the value, the method the generator used (`method`), the ATL105 page it relied on (`source_page`) and its notes (`flags`). Fields without a value in the test case are sent empty - their Field Separator is still sent. Segment Length and Number of Segments are not stored in the test case; they are calculated when the file is built. Values that only exist after step 1 has run are written as instruction text in angle brackets.

### 3.1 Step 1 - [TD-TC-001869.json](../test_data/TD-TC-001869.json)

Where to look in [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148): `request["<segment name>"]["<field name>"]` and `data_section_1.elements["55"]` / `["63"]`.

| Field | Value in the test data file | Value in the test case | Same? | How it was produced (`method`) | ATL105 page used by the generator | Generator notes (`flags`) |
|---|---|---|---|---|---|---|
| Message Format Version Identifier (Element 55) | `ATL105` | `ATL105` | Yes | Fixed value from the Chapter 11 message layout (`fixed_value_chapter11_table`) | p.391 (printed 13-51) | - |
| Number of Segments (Element 63) | `02` | (none) | Calculated when the file was built | Not stored in the test case; counted from the segments when the file was built (`derived_segment_count`) | p.395 (printed 13-55) | - |
| 100 / 1. Segment Type (Element 85) | `100` | `100` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.223 (printed 12-6) | - |
| 100 / 2. Segment Length (Element 84) | `078` | (none) | Calculated when the file was built | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.223 (printed 12-6) | DERIVED_AT_WIRE_ENCODING |
| 100 / 3. Information Byte (Element 44) | `0` | `0` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.223 (printed 12-6) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 100 / 4. Terminal Identifier (Element 102) | `JX72948216050257001` | `JX72948216050257001` | Yes | Generated Terminal Identifier for the merchant's terminal (placeholder merchant) (`realistic_terminal_id`) | p.223 (printed 12-6) | TERMINAL_ID_PER_MERCHANT |
| 100 / 5. Prompt Code (Element 78) | `0020` | `0020` | Yes | Fixed by the test scenario (`scenario_parameter_literal`) | p.223 (printed 12-6) | AMBIGUOUS_LENGTH, CONSTRAINT_RULE_UNDEFINED |
| 100 / 6. Account Number (Element 2) | `5157031214639283` | `5157031214639283` | Yes | Generated MasterCard test card number (`realistic_pan`) | p.224 (printed 12-7) | - |
| 100 / 7. Card Discretionary Block Data (Element 12) | `0928` | `0928` | Yes | Generated card expiry date (`realistic_expiry_date`) | - | CARD_DATA_KEYED |
| 100 / 8. Encrypted PIN Block Data (Element 33) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 9. Pump/Lane Number (Element 79) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 10. Fuel Purchase Amount (Element 41) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 11. Nonfuel Amount (Element 58) | `1500` | `1500` | Yes | Fixed by the test scenario (`scenario_parameter_literal`) | p.224 (printed 12-7) | - |
| 100 / 12. Tax Amount (Element 99) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 13. Cash Amount (Element 17) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 14. Sequence Number (Element 86) | `018691` | `018691` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEQUENCE_ALLOCATED |
| 100 / 15. Approval Number (Element 5) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 16. Local Date and Local Time (Element 49) | (empty) | (none) | Yes (empty) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / 17. Partial Approval Indicator (Element 121) | `1` | `1` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.225 (printed 12-8) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 111 / 1. Segment Type (Element 85) | `111` | `111` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.246 (printed 12-29) | - |
| 111 / 2. Segment Length (Element 84) | `026` | (none) | Calculated when the file was built | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.246 (printed 12-29) | DERIVED_AT_WIRE_ENCODING |
| 111 / 3. Variable Information Indicator - Table 005 (Element 111) | `005` | `005` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.548 (printed Appendix H-2) - outdated: Table 005 is on p.555 (see section 9) | AXIS_TABLE_PLACEMENT |
| 111 / 4. Variable Information Length - Table 005 (Element 112) | `003` | `003` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.548 (printed Appendix H-2) - outdated: Table 005 is on p.555 (see section 9) | AXIS_TABLE_PLACEMENT |
| 111 / 5. Variable Information - Table 005 (Element 113) | `018` | `018` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.548 (printed Appendix H-2) - outdated: Table 005 is on p.555 (see section 9) | AXIS_TABLE_PLACEMENT |
| 111 / 3. Variable Information Indicator - Table 031 (Element 111) | `031` | `031` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.569 (printed Appendix I-20) | MANDATORY_VARIABLE_INFORMATION_TABLE |
| 111 / 4. Variable Information Length - Table 031 (Element 112) | `002` | `002` | Yes | Calculated from the length of the table data (`derived_from_table_data`) | - | - |
| 111 / 5. Variable Information - Table 031 (Element 113) | `12` | `12` | Yes | Certified capability of the merchant's terminal (generator merchant settings) (`realistic_terminal_capability`) | - | TERMINAL_CAPABILITY |

Generator notes used above (full text from `flags`):

- DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized
- QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead
- TERMINAL_ID_PER_MERCHANT: one Terminal Identifier per merchant terminal (element 102, p.417); State Code = numeric ANSI code (Appendix D, p.514)
- AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4
- CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable
- CARD_DATA_KEYED: manually entered card - expiration date MMYY (element 12, p.372; p.81)
- CONDITIONAL_FIELD_OMITTED
- SEQUENCE_ALLOCATED: unique per terminal across the run set; follow-ons carry the original's number (element 86, p.409, BR-409-3)
- QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead
- AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='8' (pin_entry_capability)
- MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p569)
- TERMINAL_CAPABILITY: the merchant terminal's certified capability (merchants.json terminal_capability; Table 031, p.569, BR-569-1)

<a id="s4"></a>

## 4. Field-by-field check

For each row: find the JSON key in the test data file (Segment 100 keys are under `Standard Message Data Segment`, Segment 111 keys under `Variable Information Data Segment`), read the value, apply the ATL105 rule (its text is linked) and compare with the expected value.

### 4.1 Step 1 - [TD-TC-001869.json](../test_data/TD-TC-001869.json)

| # | Field (Element) | JSON key | Value in the file | ATL105 rule | How to check | Expected | AI result | Your check |
|---|---|---|---|---|---|---|---|---|
| DS1-1 | Message Format Version Identifier (55) | `MessageFormatVersionIdentifier` | `ATL105` | Fixed length of six alphanumeric characters; valid value ATL105 ([S11.1](#x-s11-1), [EL55](#x-el55)) | Read the value: it must be exactly ATL105. | ATL105 | PASS | ☐ |
| DS1-2 | Number of Segments (63) | `NumberofSegments` | `02` | Up to two digits; N means Segment 100 plus N-1 other segments ([S11.1](#x-s11-1), [EL63](#x-el63)) | Count the segment objects in the file: Standard Message Data Segment and Variable Information Data Segment = 2. | 02 | PASS | ☐ |
| 100-1 | Segment Type (85) | `SegmentType` | `100` | Fixed value: 100 ([S12.1](#x-s12-1), [EL85](#x-el85)) | Read the value. | 100 | PASS | ☐ |
| 100-2 | Segment Length (84) | `SegmentLength` | `078` | Length of all fields of the segment, including the Segment Type, plus all Field Separators ([S12.1](#x-s12-1), [EL84](#x-el84)) | Add up the 17 field lengths and 17 Field Separators - see section 5.1, step 1. | 078 | PASS | ☐ |
| 100-3 | Information Byte (44) | `InformationByte` | `0` | One character: ? Download Request, 0 Single-message transmission, 1 Multimessage transmission ([EL44](#x-el44)) | Find `0` in the Element 44 list: single-message transmission. | 0 or 1 (this test data uses 0 = single-message) | PASS | ☐ |
| 100-4 | Terminal Identifier (102) | `TerminalIdentifier` | `JX72948216050257001` | Up to 22 characters: Device Type + ANSI State Code + BUYPASS Merchant Number + Device Number; assigned by BUYPASS ([EL102](#x-el102), [APP-D](#x-app-d)) | Count the characters: 19 (at most 22). Split it: Device Type `JX` (positions 1-2); State Code `72` (positions 3-4) = Yukon (YT), Canada in Appendix D; then `948216050257001` (15 characters) = BUYPASS Merchant Number (6 to 15 characters) followed by the 3-character Device Number, so 9 to 18 characters. Only BUYPASS can confirm the Device Type, Merchant Number and Device Number. | Valid layout; the real value must come from BUYPASS (this one is a placeholder) | PASS | ☐ |
| 100-5 | Prompt Code (78) | `PromptCode.TransactionType + PromptCode.CardType` | `0020` | 4 characters: position 1 Transaction Type (Appendix G), positions 2-4 Card Type (Appendix E) ([EL78](#x-el78), [APP-G](#x-app-g), [APP-E](#x-app-e)) | The JSON splits the Prompt Code into TransactionType `0` and CardType `020`; on the wire they are joined as `0020`. Find `0` in Appendix G (POS Purchase/Capture) and `020` in Appendix E (Credit). | 0020 (POS Purchase/Capture, Credit) | PASS | ☐ |
| 100-6 | Account Number (2) | `AccountNumber` | `5157031214639283` | Up to 24 characters (non-TransArmor request) ([EL2](#x-el2)) | Count the digits: 16 (at most 24). Card-industry checks (ATL105 refers to the BUYPASS IIN Reference Manual): a MasterCard number starts with 51-55 or 2221-2720 (this one starts with 51) and its last digit is a check digit - see section 5.3. | MasterCard number with a valid check digit | PASS | ☐ |
| 100-7 | Card Discretionary Block Data (12) | `CardDiscretionaryBlockData` | `0928` | Keyed card: expiration date in MMYY format ([EL12](#x-el12), [S10.1.1](#x-s10-1-1)) | `0928` = month 09, year 2028 = September 2028. The month must be 01-12 and, for an approval test, the card must not be expired on the test date (November 2026). | MMYY with a valid month, not expired (September 2028) | PASS | ☐ |
| 100-8 | Encrypted PIN Block Data (33) | `EncryptedPINBlockData` | (empty) | Conditional (C): encrypted PIN block (DUKPT); this keyed credit transaction has no PIN ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-9 | Pump/Lane Number (79) | `PumpLaneNumber` | (empty) | Conditional (C): pump or lane number; not used by this test case ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-10 | Fuel Purchase Amount (41) | `FuelPurchaseAmount` | (empty) | Conditional (C): fuel purchase amount; this sale has no fuel ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-11 | Nonfuel Amount (58) | `NonfuelAmount` | `1500` | Up to 8 digits with two assumed decimals; at most 7 digits for non-American Express cards ([EL58](#x-el58)) | `1500` = $15.00 (the last two digits are cents): digits only, 4 digits (at most 7 for MasterCard; 8 only for American Express). | 1500 ($15.00) | PASS | ☐ |
| 100-12 | Tax Amount (99) | `TaxAmount` | (empty) | Conditional (C): tax amount; not sent by this test case ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-13 | Cash Amount (17) | `CashAmount` | (empty) | Conditional (C): cash amount (cash back); not used by this test case ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-14 | Sequence Number (86) | `SequenceNumber` | `018691` | Fixed length of six digits, 000001-999999; unique per transaction ([EL86](#x-el86)) | `018691` has 6 digits and is not 000000. No other new transaction on this terminal may use the same number (see section 10). | 6 digits, unique (018691) | PASS | ☐ |
| 100-15 | Approval Number (5) | `ApprovalNumber` | (empty) | Conditional: required on reversals; not sent on a purchase ([EL5](#x-el5)) | Must be empty on a purchase: an Approval Number in a Purchase Request makes it preauthorized, and BUYPASS does not go out for authorization. | (empty) | PASS | ☐ |
| 100-16 | Local Date and Local Time (49) | `LocalDateandLocalTime` | (empty) | Conditional (C): local date and time of a preauthorized transaction; this is not a preauthorized transaction ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-17 | Partial Approval Indicator (121) | `PartialApprovalIndicator` | `1` | One digit: 0 not supported, 1 supported, 5 card balance only (American Express prepaid) ([EL121](#x-el121)) | `1` = partial approval supported. Required on all transactions. | 1 | PASS | ☐ |
| 111-1 | Segment Type (85) | `SegmentType` | `111` | Fixed value: 111 ([S12.10](#x-s12-10), [EL85](#x-el85)) | Read the value. | 111 | PASS | ☐ |
| 111-2 | Segment Length (84) | `SegmentLength` | `026` | Length of the segment including the Segment Type and all Field Separators ([S12.10](#x-s12-10), [EL84](#x-el84)) | Add it up as in section 5.2, step 1. | 026 | PASS | ☐ |
| 111-3.1 | Variable Information Indicator (111) | `VariableInformationSection[0].VariableInformationIndicator` | `005` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-005](#x-app-i-005)) | `005` = Table 005 (Point-of-Service Entry Mode Information). | 005 | PASS | ☐ |
| 111-4.1 | Variable Information Length (112) | `VariableInformationSection[0].VariableInformationLength` | `003` | 3 digits: number of characters in Variable Information; Table 005 data is 3 characters ([EL112](#x-el112), [S12.10](#x-s12-10), [APP-I-005](#x-app-i-005)) | Count the characters of `018`: 3, written as `003`. | 003 | PASS | ☐ |
| 111-5.1 | Variable Information (113) - Table 005 | `VariableInformationSection[0].VariableInformation` | `018` | 3 digits: PAN Entry Mode (positions 1-2) + Terminal PIN Entry Capability (position 3) ([EL113](#x-el113), [APP-I-005](#x-app-i-005), [APP-J](#x-app-j), [S10.1.5](#x-s10-1-5)) | Positions 1-2 `01` = manual/keyed (Appendix J PAN Entry Mode). Position 3 `8` = contactless magnetic stripe read capability (Appendix J Terminal PIN Entry Capability). The scenario is Manual / Keyed, so positions 1-2 must be 01. An RFID receiver is attached, so the entry mode must still be populated correctly for a keyed card. | 01 + the terminal's PIN entry capability code (see row 111-C) | PASS | ☐ |
| 111-3.2 | Variable Information Indicator (111) | `VariableInformationSection[1].VariableInformationIndicator` | `031` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-031](#x-app-i-031)) | `031` = Table 031 (Point-of-Service Terminal Capability Codes). | 031 | PASS | ☐ |
| 111-4.2 | Variable Information Length (112) | `VariableInformationSection[1].VariableInformationLength` | `002` | 3 digits: number of characters in Variable Information; Table 031 data is 2 characters ([EL112](#x-el112), [S12.10](#x-s12-10), [APP-I-031](#x-app-i-031)) | Count the characters of `12`: 2, written as `002`. | 002 | PASS | ☐ |
| 111-5.2 | Variable Information (113) - Table 031 | `VariableInformationSection[1].VariableInformation` | `12` | 2 digits: the terminal's certified capability; required on every transaction ([APP-I-031](#x-app-i-031)) | `12` = hybrid reader: contact chip and contactless chip (Table 031). It must describe the terminal's certified capability, which does not change with the entry mode. | The terminal's certified capability code (2 digits) | PASS | ☐ |
| 111-C | Consistency: Table 005 position 3 vs Table 031 | `both tables` | `8 / 12` | Both tables describe the same terminal ([APP-J](#x-app-j), [APP-I-031](#x-app-i-031)) | Table 005 position 3 is `8` (contactless magnetic stripe read capability); Table 031 is `12` (hybrid reader: contact chip and contactless chip). Appendix J note 1: a device with a contactless MSR and no chip uses 8; a chip device (EMV contact or contactless) uses 1, not 8. Both tables must describe the same terminal. The test case says: "Terminal has a contactless (RFID) receiver attached and no chip reader; the card is swiped or keyed". | Table 031 = 07 (contactless magnetic stripe, no chip) | FAIL | ☐ |

<a id="s5"></a>

## 5. Calculations

<a id="s5-1"></a>

### 5.1 Segment 100 length

Rule ([S12.1](#x-s12-1), [EL84](#x-el84)): the Segment Length counts every field of the segment - including the Segment Type and the Segment Length field itself - plus all Field Separators. Each of the 17 fields is followed by a Field Separator, even when the field is empty.

**Step 1 - TD-TC-001869.json**

| No. | Field | Value counted | Characters | Field Separator | Running total |
|---|---|---|---|---|---|
| 1 | Segment Type | `100` | 3 | 1 | 4 |
| 2 | Segment Length | `078` | 3 | 1 | 8 |
| 3 | Information Byte | `0` | 1 | 1 | 10 |
| 4 | Terminal Identifier | `JX72948216050257001` | 19 | 1 | 30 |
| 5 | Prompt Code | `0020` | 4 | 1 | 35 |
| 6 | Account Number | `5157031214639283` | 16 | 1 | 52 |
| 7 | Card Discretionary Block Data | `0928` | 4 | 1 | 57 |
| 8 | Encrypted PIN Block Data | (empty) | 0 | 1 | 58 |
| 9 | Pump/Lane Number | (empty) | 0 | 1 | 59 |
| 10 | Fuel Purchase Amount | (empty) | 0 | 1 | 60 |
| 11 | Nonfuel Amount | `1500` | 4 | 1 | 65 |
| 12 | Tax Amount | (empty) | 0 | 1 | 66 |
| 13 | Cash Amount | (empty) | 0 | 1 | 67 |
| 14 | Sequence Number | `018691` | 6 | 1 | 74 |
| 15 | Approval Number | (empty) | 0 | 1 | 75 |
| 16 | Local Date and Local Time | (empty) | 0 | 1 | 76 |
| 17 | Partial Approval Indicator | `1` | 1 | 1 | 78 |

Total: 61 characters of data + 17 Field Separators = **078**. The file declares `078`: **match**.

<a id="s5-2"></a>

### 5.2 Segment 111 length

Rule ([S12.10](#x-s12-10), [EL84](#x-el84)): a Field Separator follows Field 1 (Segment Type) and Field 2 (Segment Length). Inside the Variable Information Section there is no Field Separator between the indicator, length and data, nor between tables; one Field Separator follows the final table.

**Step 1 - TD-TC-001869.json**

| Part | Value | Characters | Field Separator after it | Running total |
|---|---|---|---|---|
| 1. Segment Type | `111` | 3 | 1 | 4 |
| 2. Segment Length | `026` | 3 | 1 | 8 |
| Table 005: indicator + length + data | `005003018` | 9 | 0 | 17 |
| Table 031: indicator + length + data | `03100212` | 8 | 1 | 26 |

Total **026**. The file declares `026`: **match**.

<a id="s5-3"></a>

### 5.3 Card number check digit (Luhn)

ATL105 does not define card numbers itself: Element 2 refers to the BUYPASS Issuer Identification Number (IIN) Reference Manual ([EL2](#x-el2)). The two checks below are card-industry rules: a MasterCard number starts with 51-55 or 2221-2720, and its last digit is a Luhn check digit. Luhn: starting from the right, double every second digit (subtract 9 when the result is above 9) and add all the digits; the total must end in 0.

**`5157031214639283`** (step 1)

| Position | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Digit | 5 | 1 | 5 | 7 | 0 | 3 | 1 | 2 | 1 | 4 | 6 | 3 | 9 | 2 | 8 | 3 |
| Doubled? | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  |
| Value added | 1 | 1 | 1 | 7 | 0 | 3 | 2 | 2 | 2 | 4 | 3 | 3 | 9 | 2 | 7 | 3 |

Sum = 50, which ends in 0: check digit **valid**. Starts with `51`: **in** the MasterCard range.

<a id="s5-4"></a>

### 5.4 Expiry dates

Keyed cards send the expiry as MMYY ([EL12](#x-el12), [S10.1.1](#x-s10-1-1)); dates up to December 2049 are 21st-century dates.

| Step | Value in the file | Format | Meaning | Check |
|---|---|---|---|---|
| 1 | `0928` | MMYY | September 2028 | Month 09 is valid; **not expired** on November 2026 |

<a id="s6"></a>

## 6. Links between the steps

Not applicable - this test case has one step.

<a id="s7"></a>

## 7. Expected responses

Response Code values are listed in [EL83](#x-el83). The tier shows how sure the generator is: ASSERT = taken from ATL105; FLAG = not fully grounded, to be confirmed.

| Step | Expected Response Code | Meaning (Element 83) | Tier | Page cited by the test case | AI result | Your check |
|---|---|---|---|---|---|---|
| 1 | `0` | Approved—Purchase/Capture | ASSERT | p.403 (printed 13-63) | PASS | ☐ |

- The test case also accepts `F` (approval, partial amount approved) when Partial Approval Indicator=1 and the network approves less than the requested amount (Element 121 p.440).

<a id="s8"></a>

## 8. ATL105 citations written in the test case

The test case lists its ATL105 sources in `spec_basis` ([test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148)). Find each quote in the ATL105 text linked in the last column.

| # | Page cited | Quote in the test case | AI finding | Compare with | Your check |
|---|---|---|---|---|---|
| 1 | p.86 (printed 10-7) | Even if the transaction is swiped, manually keyed, or uses a card type for which RFID is not applicable, Point-of-Sale Entry Mode must be populated correctly in the transaction request from the point-of-sale when an RFID receiver is present. | PASS - Found word-for-word on the cited page | [S10.1.5](#x-s10-1-5) | ☐ |
| 2 | p.639 (printed Appendix J-2) | If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8 | PASS - Found word-for-word on the cited page | [APP-J](#x-app-j) | ☐ |
| 3 | p.511 (printed Appendix C-1) | 03 MasterCard - Authorizer Type: Credit | REVIEW - Not word-for-word: all its words are on the cited page, but not in this form | [APP-C](#x-app-c) | ☐ |
| 4 | p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Purchase/Capture | PASS - Found word-for-word on the cited page | [S10.1.2](#x-s10-1-2) | ☐ |
| 5 | p.81 (printed 10-2) | Manual entry is allowed on a credit card transaction | PASS - Found word-for-word on the cited page | [S10.1.1](#x-s10-1-1) | ☐ |

The generator also records an ATL105 page for each field (`source_page`, section 3). Each page should hold the rule for that field:

| Page used by the generator | Fields | Holds the rule? | Compare with |
|---|---|---|---|
| p.223 (printed 12-6) | 100 / 1. Segment Type, 100 / 2. Segment Length, 100 / 3. Information Byte, 100 / 4. Terminal Identifier, 100 / 5. Prompt Code | Yes | [S12.1](#x-s12-1) |
| p.224 (printed 12-7) | 100 / 6. Account Number, 100 / 11. Nonfuel Amount, 100 / 14. Sequence Number | Yes | [S12.1](#x-s12-1) |
| p.225 (printed 12-8) | 100 / 17. Partial Approval Indicator | Yes | [S12.1](#x-s12-1) |
| p.246 (printed 12-29) | 111 / 1. Segment Type, 111 / 2. Segment Length | Yes | [S12.10](#x-s12-10) |
| p.391 (printed 13-51) | Message Format Version Identifier | Yes | [EL55](#x-el55) |
| p.395 (printed 13-55) | Number of Segments | Yes | [EL63](#x-el63) |
| p.548 (printed Appendix H-2) | 111 / 3. Variable Information Indicator - Table 005, 111 / 4. Variable Information Length - Table 005, 111 / 5. Variable Information - Table 005 | No - outdated: Table 005 is on p.555 (see section 9) | [APP-H-548](#x-app-h-548) |
| p.569 (printed Appendix I-20) | 111 / 3. Variable Information Indicator - Table 031 | Yes | [APP-I-031](#x-app-i-031) |

<a id="s9"></a>

## 9. What is wrong or missing - and how to confirm each issue by hand

These are the issues the AI validator reported for TC-001869, numbered as in the [AI report](../Result_MD_Folder/TC-001869.md).

| # | Type | Issue | Step | AI result |
|---|---|---|---|---|
| [1](#i1) | Wrong | The terminal is described two different ways in the same message | 1 | FAIL |
| [2](#i2) | To confirm | Outdated page cited for the POS Entry Mode table (Table 005) | all | REVIEW |
| [3](#i3) | To confirm | The quote cited for p.511 is not word-for-word in ATL105 | all | REVIEW |
| [4](#i4) | Missing - client input (real Terminal Identifier) | The Terminal Identifier is a placeholder | all | REVIEW |
| [5](#i5) | To confirm | Marked 'ready' although the terminal is a placeholder | all | REVIEW |

<a id="i1"></a>

### 9.1 The terminal is described two different ways in the same message - FAIL

| Item | Value |
|---|---|
| Step | 1 |
| Where | TD-TC-001869.json -> Variable Information Data Segment -> Table 005 and Table 031 |
| Value | `Table 005 = 018, Table 031 = 12` |
| ATL105 | [APP-I-031](#x-app-i-031) Appendix I, PDF p.569 (printed I-20): "12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities"<br>[APP-I-031](#x-app-i-031) Appendix I, PDF p.569 (printed I-20): "07 = Contactless magnetic stripe read capability (no contact or contactless chip capability)"<br>[APP-J](#x-app-j) Appendix J, PDF p.639 (printed J-2): "If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8"<br>[APP-J](#x-app-j) Appendix J, PDF p.639 (printed J-2): "8 Contactless Magnetic Stripe Read Capability"<br>[APP-J](#x-app-j) Appendix J, PDF p.639 (printed J-2): "For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8."<br>[APP-I-031](#x-app-i-031) Appendix I, PDF p.569 (printed I-20): "Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode." |
| Why (AI) | Table 005 '018' says: manual/keyed entry on a terminal with code 8 (contactless magnetic stripe read capability), which Appendix J note 1 reserves for a terminal with a contactless magstripe reader and no chip. Table 031 '12' says: hybrid reader: contact chip and contactless chip, so the terminal has a chip reader. Both cannot be true for the same terminal: a chip-capable terminal must send 1 (not 8), and a terminal without a chip cannot be 12. The test case itself says: "Terminal has a contactless (RFID) receiver attached and no chip reader; the card is swiped or keyed". |
| What it should be (AI) | Keep Table 005 = 018 and change Table 031 to 07, which means contactless magnetic stripe read (no contact or contactless chip). Use a separate RFID terminal, not the hybrid chip terminal used by the other test cases. |

**How to confirm by hand**

1. Open `TD-TC-001869.json`, `Variable Information Data Segment` → `VariableInformationSection[0]` (Table 005): `VariableInformation` = `018`, so position 3 = `8`.
2. Find position 3 in [APP-J](#x-app-j) (Terminal PIN Entry Capability Mode codes): `8` = contactless magnetic stripe read capability. Note 1 under that table: code 8 is used when a contactless MSR is attached and the device has no chip capability; for EMV contact and contactless the code is 1, not 8.
3. Open `VariableInformationSection[1]` (Table 031): `VariableInformation` = `12`. Find it in [APP-I-031](#x-app-i-031): `12` = hybrid reader: contact chip and contactless chip.
4. Read how the test case describes the terminal: [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148), `tx_legs[0].execution` = "Terminal has a contactless (RFID) receiver attached and no chip reader; the card is swiped or keyed".
5. Conclusion: Table 005 (code 8) describes a terminal without a chip reader, while Table 031 (`12`) describes a terminal with a chip reader. One terminal cannot be both. For a contactless-magstripe terminal without a chip, Table 031 must be `07` ([APP-I-031](#x-app-i-031)).

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i2"></a>

### 9.2 Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

| Item | Value |
|---|---|
| Where | test case request -> Variable Information Data Segment -> Variable Information Indicator / Length / Information (source_page), every step |
| Value | `p.548` |
| ATL105 | [APP-I-005](#x-app-i-005) Appendix I, PDF p.555 (printed I-6): "Point-of-Service Entry Mode Information" |
| Why (AI) | The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition. |
| What it should be (AI) | Change the cited page from 548 to 555. The data values are not affected. |

**How to confirm by hand**

1. Open [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148). In `request["Variable Information Data Segment"]["Variable Information Indicator"]` (and `Variable Information Length`, `Variable Information`) `source_page` = 548.
2. Read [APP-H-548](#x-app-h-548): in release 2026-4, PDF p.548 (printed Appendix H-2) is the decline-code table of Appendix H, not Table 005.
3. Read [APP-I-005](#x-app-i-005): Table 005 (Point-of-Service Entry Mode Information) is on PDF p.555 (printed Appendix I-6).
4. Conclusion: the page number is out of date (Table 005 was on p.548 in the older 2026-3 edition). The data values are not affected.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i3"></a>

### 9.3 The quote cited for p.511 is not word-for-word in ATL105 - REVIEW

| Item | Value |
|---|---|
| Where | test case spec_basis |
| Value | `03 MasterCard - Authorizer Type: Credit` |
| ATL105 | [APP-C](#x-app-c) Appendix C, PDF p.511 (printed C-1): "03 MasterCard® Credit" |
| Why (AI) | The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form. |
| What it should be (AI) | Quote the text exactly as it appears: "03 MasterCard® Credit". |

**How to confirm by hand**

1. Open [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148), `spec_basis`: the entry with `page` 511 quotes "03 MasterCard - Authorizer Type: Credit".
2. Compare it with the ATL105 text in [APP-C](#x-app-c): the row reads "03 MasterCard® Credit".
3. Conclusion: Not word-for-word: all its words are on the cited page, but not in this form. The meaning is the same; only the wording of the quote should be corrected.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i4"></a>

### 9.4 The Terminal Identifier is a placeholder - REVIEW

| Item | Value |
|---|---|
| Where | Standard Message Data Segment -> TerminalIdentifier (every step) |
| Value | `JX72948216050257001` |
| ATL105 | [EL102](#x-el102) Element 102, PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."<br>[EL102](#x-el102) Element 102, PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."<br>[APP-D](#x-app-d) Appendix D, PDF p.517 (printed D-4): "Yukon YT 72" |
| Why (AI) | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. |
| What it should be (AI) | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |

**How to confirm by hand**

1. Open [test_cases.jsonl line 148](../test_cases/test_cases.jsonl#L148): `merchant.name` = "MERCHANT 01 (POS PLACEHOLDER)", `merchant_provenance` = "placeholder", and `flags` contains "MERCHANT_PLACEHOLDER: merchant list not yet supplied by the client".
2. Read [EL102](#x-el102): "The Terminal Identifier must be assigned by and set up at BUYPASS." A generated value is not assigned by BUYPASS.
3. Positions 3-4 of `JX72948216050257001` are `72`. Find `72` in [APP-D](#x-app-d): Yukon (YT), Canada.
4. Conclusion: ask the client/BUYPASS for the real Terminal Identifier of this merchant before execution and confirm its location.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i5"></a>

### 9.5 Marked 'ready' although the terminal is a placeholder - REVIEW

| Item | Value |
|---|---|
| Where | test case status |
| Value | `ready` |
| ATL105 | [EL102](#x-el102) Element 102, PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." |
| Why (AI) | The test case status is 'ready', but its Terminal Identifier is still a placeholder. |
| What it should be (AI) | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |

**How to confirm by hand**

1. Open [TEST_CASES.md line 214](../test_cases/TEST_CASES.md#L214): the Status column is `ready` (the same as `status` in the test case record).
2. The previous issue shows that the Terminal Identifier is a placeholder, so BUYPASS cannot accept the message as-is.
3. Conclusion: the status should not be 'ready' until the real Terminal Identifier is supplied.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="s10"></a>

## 10. Checks across the five test cases

All five test cases use the same terminal, so some rules can only be checked by comparing them.

| Check | AI result | Detail | ATL105 | Your check |
|---|---|---|---|---|
| Every new transaction on the terminal has its own Sequence Number | PASS | 6 new transactions; all Sequence Numbers are different | [EL86](#x-el86) | ☐ |
| Terminal JX72948216050257001 keeps one Table 031 value | PASS | Table 031 = 12 in all 7 messages | [APP-I-031](#x-app-i-031) | ☐ |
| Terminal JX72948216050257001 uses more than one terminal capability code (Table 005, position 3) | REVIEW | code 1 in TC-001867, TC-001868, TC-001870, TC-001871; code 8 in TC-001869. Appendix J note 1 says all transactions from a device use one code. | [APP-J](#x-app-j) | ☐ |
| TC-001868 sends the same request as TC-001867 | REVIEW | The request is identical to TC-001867 (T1-PUR) except the Sequence Number: same card 510058...7353, expiry 0730, amount $15.00 and entry mode 011. What makes this test different (the device asks for a re-swipe after each of the first three failed reads; after the third failed read it prompts for manual entry of Account Number and Expiration Date (MMYY); the receipt and journal show '*' before the Account Number) happens on the device and is not visible in the message sent to BUYPASS. | [S10.1.1](#x-s10-1-1) | ☐ |

Sequence Numbers sent on the terminal (a reversal reuses the number of its original on purpose):

| Test case | Step | Role | Sequence Number |
|---|---|---|---|
| TC-001867 | 1 | purchase | `018671` |
| TC-001868 | 1 | purchase | `018681` |
| TC-001869 | 1 | purchase | `018691` |
| TC-001870 | 1 | purchase | `018701` |
| TC-001870 | 2 | split tender | `018702` |
| TC-001871 | 1 | purchase | `018711` |
| TC-001871 | 2 | reversal | `018711` |

<a id="s11"></a>

## 11. ATL105 text used in this guide

Copied from the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). Each line below was found on the stated PDF page when this guide was generated. Table rows keep the ATL105 column order.

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

<a id="x-s10-1-2"></a>

### [S10.1.2] Section 10.1.2 Supported Transaction Types

*PDF p.83 (printed 10-4)*

> The following point-of-sale transactions are supported for credit card processing:<br>• Purchase/Capture<br>• Purchase Reversal<br>• Authorization Only<br>• Merchandise Return<br>• Time-out Reversal<br>• Mail/Phone Purchase<br>• Mail/Phone Authorization Only<br>• Mail/Phone Reversal<br>• Void of Merchandise Return<br>• Balance Inquiry<br>• Partial Approval<br>• Authorization Only Reversal

<a id="x-s10-1-5"></a>

### [S10.1.5] Section 10.1.5 Radio Frequency Identification Service

*PDF p.86 (printed 10-7)*

> Radio frequency identification (RFID) service is an optional service.
>
> RFID processing requires that the appropriate values be sent in Point-of-Sale Entry Mode (Contained in Element No. 113, Variable Information.) on all transactions when an RFID receiver is connected to the point-of-sale device or controller. Even if the transaction is swiped, manually keyed, or uses a card type for which RFID is not applicable, Point-of-Sale Entry Mode must be populated correctly in the transaction request from the point-of-sale when an RFID receiver is present.

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

<a id="x-el83"></a>

### [EL83] Element 83 Response Code (Chapter 13)

*PDF p.403-404 (printed 13-63 to 13-64)*

> Number: 83 Name: Response Code
>
> Character Type: AN Maximum Length: 1 byte
>
> Purpose: For financial transactions, it identifies an approval with purchase/capture, an approval without purchase/capture, or a decline.

*PDF p.404 (printed 13-64):*

> 0 Approved—Purchase/Capture
>
> F Approved—Partial approval

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

<a id="x-app-c"></a>

### [APP-C] Appendix C. Valid Authorizer Codes

*PDF p.511 (printed Appendix C-1)*

> The following table contains a list of valid entries for Element No. 7, Authorizer Code:

| Authorizer Code | Authorizer Name | Authorizer Type | PDF p. |
|---|---|---|---|
| 03 | MasterCard® | Credit | 511 |

<a id="x-app-d"></a>

### [APP-D] Appendix D. Valid State Codes

*PDF p.514-517 (printed Appendix D-1 to Appendix D-4)*

> The ANSI® State Codes are used in the following data element:<br>• Terminal Identifier (No. 102)

*PDF p.517 (printed Appendix D-4):*

> The following table contains a list of valid State codes for the provinces of Canada:

| Province of Canada | Alphabetical Code | ANSI® Code | PDF p. |
|---|---|---|---|
| Yukon | YT | 72 | 517 |

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

<a id="x-app-h-548"></a>

### [APP-H-548] What is on PDF p.548 - the page the test case cites for Table 005

*PDF p.548 (printed Appendix H-2)*

> The following table contains a list of valid Decline Codes that can be used to prompt a clerk/customer to reenter indicated data:
>
> Decline Codes Prompting Data Reentry

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

<a id="s12"></a>

## 12. Sign-off

| Area | AI result | Your result | Comment |
|---|---|---|---|
| Field checks (section 4) | 27 PASS | ☐ Agree ☐ Disagree | |
| Links between steps (section 6) | not applicable | ☐ Agree ☐ Disagree | |
| Expected responses (section 7) | 1 PASS | ☐ Agree ☐ Disagree | |
| Citations (section 8) | 4 PASS, 1 REVIEW | ☐ Agree ☐ Disagree | |
| Issues (section 9) | 1 FAIL, 4 REVIEW | ☐ Agree ☐ Disagree | |
| **Overall** | **FAIL** - No - fix the FAIL items first | ☐ Agree ☐ Disagree | |

Reviewed by: ____________________    Date: ____________

