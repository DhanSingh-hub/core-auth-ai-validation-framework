# TC-020804 (BR1_TS5_TC2) - Manual Validation Guide

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm)**

A step-by-step path to check this test case and its test data by hand against ATL105 and to confirm or reject what the AI validation reported. The ATL105 text needed for every check is copied into [section 11](#s11) (each line was found on the stated page), so the specification does not have to be opened. The same release is attached to the run as `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` (byte-identical to the validator's copy).

| Item | Value |
|---|---|
| AI validation result | **BLOCKED** - can it run as-is? Only after the client / run-time values are filled in |
| Findings | 1 Information, 2 Awaiting client input / run-time value, 1 Intended violation of a negative test (correct), 1 Citation issue, 1 Test case issue to correct (section 9) |
| Test case record | [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100) (the whole test case is on this one line) |
| Test data | [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json), [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json) |
| Scenario | BR1_TS5 - Purchase/Capture – Manual / Keyed (GENERAL) |
| Type / polarity | `single_rule_broken` Single rule broken / Negative |
| Template | `G-NR-3bf2f87eb9` |
| Status in the test case | `ready` |
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
| Objective | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | `test_objective` |
| Validation focus | Single rule broken | `validation_focus` |
| Merchant | MERCHANT 01 (POS PLACEHOLDER) (id M01, POS), merchant_provenance = placeholder | `merchant` |
| Card | MasterCard Credit - Prompt Code card type 020 | `dimensions` |
| Message chain (Transaction Types) | 0 -> 8 | `message_chain` |

| Step | Role | Prompt Code in the file | Expected response | Test data file |
|---|---|---|---|---|
| 1 | purchase | `0020` = POS Purchase/Capture, card type 020 | `0` (ASSERT) | [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json) |
| 2 | reversal | `8020` = Purchase reversal, card type 020 | `1` (FLAG) | [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json) |

<a id="s2"></a>

## 2. Manual validation path (do these steps in order)

1. **Open the test case.** Open [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100). The record is one long line: copy it into a new file, set the language to JSON and format it (Shift+Alt+F). Confirm the id, display id, status and steps in [section 1](#s1).
2. **Open the test data files** next to this guide: [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json), [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json).
3. **Check the message structure** ([S11.1](#x-s11-1), [S12.1](#x-s12-1), [S12.10](#x-s12-10)): root `Financial Transaction Request`, Data Section 1 (`MessageFormatVersionIdentifier`, `NumberofSegments`), Segment 100 with its 17 fields, Segment 111 with Tables 005 and 031.
4. **See how each value was produced** ([section 3](#s3)): compare every value in the file with the value and method recorded in the test case.
5. **Check every field** ([section 4](#s4)) and mark the Your check column.
6. **Recalculate** the segment lengths, the card check digit and the expiry ([section 5](#s5)).
7. **Check the links between the steps** ([section 6](#s6)).
8. **Check the expected responses** ([section 7](#s7)) and the **ATL105 citations** ([section 8](#s8)).
9. **Confirm each finding** ([section 9](#s9)) with its 'How to confirm by hand' steps.
10. **Check across test cases** ([section 10](#s10)) and **sign off** ([section 12](#s12)).

<a id="s3"></a>

## 3. How the test data was generated

The test data file is the wire-ready copy of the request stored in the test case. For every field the test case records the value, the generation `method`, the ATL105 page it relied on (`source_page`) and notes (`flags`). Segment Length / Number of Segments are calculated when the file is built. Values that exist only after an earlier step has run are written as instruction text in angle brackets.

### 3.1 Step 1 - [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json)

In [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100): `steps[0].request["<segment>"]["<field>"]`.

| Segment / field | In the test data file | In the test case | Same? | How it was produced (`method`) | Page used | Notes (`flags`) |
|---|---|---|---|---|---|---|
| DS1 / Message Format Version Identifier (55) | `ATL105` | `ATL105` | Yes | Fixed value from the Chapter 11 message layout (`fixed_value_chapter11_table`) | p.391 (printed 13-51) | - |
| DS1 / Number of Segments (63) | `02` | (none) | Calculated | Not stored in the test case; counted from the segments when the file was built (`derived_segment_count`) | p.395 (printed 13-55) | - |
| 100 / Segment Type (El. 85) | `100` | `100` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.223 (printed 12-6) | - |
| 100 / Segment Length (El. 84) | `078` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.223 (printed 12-6) | DERIVED_AT_WIRE_ENCODING |
| 100 / Information Byte (El. 44) | `0` | `0` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.223 (printed 12-6) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 100 / Terminal Identifier (El. 102) | `JX72948216050257001` | `JX72948216050257001` | Yes | Generated Terminal Identifier for the merchant's terminal (placeholder merchant) (`realistic_terminal_id`) | p.223 (printed 12-6) | TERMINAL_ID_PER_MERCHANT |
| 100 / Prompt Code (El. 78) | `0020` | `0020` | Yes | Fixed by the flow template for this step (`flow_fixed_transaction_code`) | p.223 (printed 12-6) | AMBIGUOUS_LENGTH, CONSTRAINT_RULE_UNDEFINED, FLOW_FIXED_ELEMENT |
| 100 / Account Number (El. 2) | `5187270512804249` | `5187270512804249` | Yes | Generated MasterCard test card number (`realistic_pan`) | p.224 (printed 12-7) | - |
| 100 / Card Discretionary Block Data (El. 12) | `0928` | `0928` | Yes | Generated card expiry date (`realistic_expiry_date`) | - | CARD_DATA_KEYED |
| 100 / Encrypted PIN Block Data (El. 33) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Pump/Lane Number (El. 79) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Fuel Purchase Amount (El. 41) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Nonfuel Amount (El. 58) | `1500` | `1500` | Yes | Fixed by the test scenario (`scenario_parameter_literal`) | p.224 (printed 12-7) | - |
| 100 / Tax Amount (El. 99) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Cash Amount (El. 17) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Sequence Number (El. 86) | `208041` | `208041` | Yes | Sequence Number allocated by the generator (unique per terminal across the run) (`qe_example_value`) | p.224 (printed 12-7) | SEQUENCE_ALLOCATED |
| 100 / Approval Number (El. 5) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED, RESPONSE_ORIGIN_FIELD_SUPPRESSED |
| 100 / Local Date and Local Time (El. 49) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Partial Approval Indicator (El. 121) | `1` | `1` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.225 (printed 12-8) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 111 / Segment Type (El. 85) | `111` | `111` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.246 (printed 12-29) | - |
| 111 / Segment Length (El. 84) | `026` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.246 (printed 12-29) | DERIVED_AT_WIRE_ENCODING |
| 111 / Variable Information Indicator (El. 111) | `005` | `005` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information Length (El. 112) | `003` | `003` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information (El. 113) | `011` | `011` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information Indicator [2] (El. 111) | `031` | `031` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.569 (printed Appendix I-20) | MANDATORY_VARIABLE_INFORMATION_TABLE |
| 111 / Variable Information Length [2] (El. 112) | `002` | `002` | Yes | Calculated from the length of the table data (`derived_from_table_data`) | - | - |
| 111 / Variable Information [2] (El. 113) | `12` | `12` | Yes | Certified capability of the merchant's terminal (generator merchant settings) (`realistic_terminal_capability`) | - | TERMINAL_CAPABILITY |

### 3.2 Step 2 - [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json)

In [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100): `steps[1].request["<segment>"]["<field>"]`.

| Segment / field | In the test data file | In the test case | Same? | How it was produced (`method`) | Page used | Notes (`flags`) |
|---|---|---|---|---|---|---|
| DS1 / Message Format Version Identifier (55) | `ATL105` | `ATL105` | Yes | Fixed value from the Chapter 11 message layout (`fixed_value_chapter11_table`) | p.391 (printed 13-51) | - |
| DS1 / Number of Segments (63) | `02` | (none) | Calculated | Not stored in the test case; counted from the segments when the file was built (`derived_segment_count`) | p.395 (printed 13-55) | - |
| 100 / Segment Type (El. 85) | `100` | `100` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.223 (printed 12-6) | - |
| 100 / Segment Length (El. 84) | `084` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.223 (printed 12-6) | DERIVED_AT_WIRE_ENCODING |
| 100 / Information Byte (El. 44) | `0` | `0` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.223 (printed 12-6) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 100 / Terminal Identifier (El. 102) | `JX72948216050257001` | `JX72948216050257001` | Yes | Generated Terminal Identifier for the merchant's terminal (placeholder merchant) (`realistic_terminal_id`) | p.223 (printed 12-6) | TERMINAL_ID_PER_MERCHANT |
| 100 / Prompt Code (El. 78) | `8020` | `8020` | Yes | Fixed by the flow template for this step (`flow_fixed_transaction_code`) | p.223 (printed 12-6) | AMBIGUOUS_LENGTH, CONSTRAINT_RULE_UNDEFINED, FLOW_FIXED_ELEMENT |
| 100 / Account Number (El. 2) | `5187270512804249` | `5187270512804249` | Yes | Generated MasterCard test card number (`realistic_pan`) | p.224 (printed 12-7) | - |
| 100 / Card Discretionary Block Data (El. 12) | `2809` | `2809` | Yes | Generated card expiry date (`realistic_expiry_date`) | - | CARD_DATA_FOLLOW_ON |
| 100 / Encrypted PIN Block Data (El. 33) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Pump/Lane Number (El. 79) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Fuel Purchase Amount (El. 41) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Nonfuel Amount (El. 58) | `1500` | `1500` | Yes | Copied from step 1 (`flow_carried_value`) | p.224 (printed 12-7) | FLOW_LINK |
| 100 / Tax Amount (El. 99) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Cash Amount (El. 17) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Sequence Number (El. 86) | `000002` | `000002` | Yes | Deliberately NOT copied from step 1 (the rule this negative test breaks) (`deliberate_violation_missing_carry`) | p.224 (printed 12-7) | DELIBERATE_CONSTRAINT_VIOLATION |
| 100 / Approval Number (El. 5) | `<ApprovalNumber from step 1 response>` | `7IYI8R` | Run-time value (provisional in the test case) | Comes from the step 1 response at run time; the test case only holds a provisional value (`flow_response_runtime_value`) | p.224 (printed 12-7) | UNTRUSTED_VALID_VALUES, FLOW_REQUIRED_FIELD_INCLUDED, FLOW_LINK_NEEDS_RUNTIME_VALUE, FLOW_RESPONSE_SHAPE_FILLED_VIA |
| 100 / Local Date and Local Time (El. 49) | (empty) | (none) | Yes (not sent) | Not used in this transaction - sent empty (`omitted`) | - | CONDITIONAL_FIELD_OMITTED |
| 100 / Partial Approval Indicator (El. 121) | `1` | `1` | Yes | Value configured once for all test data (client setting) (`test_data_config_value`) | p.225 (printed 12-8) | QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE |
| 111 / Segment Type (El. 85) | `111` | `111` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.246 (printed 12-29) | - |
| 111 / Segment Length (El. 84) | `026` | (none) | Calculated | Not stored in the test case; calculated from the other fields when the file was built (`derived_at_wire_encoding`) | p.246 (printed 12-29) | DERIVED_AT_WIRE_ENCODING |
| 111 / Variable Information Indicator (El. 111) | `005` | `005` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information Length (El. 112) | `003` | `003` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information (El. 113) | `011` | `011` | Yes | Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3 (`axis_table_placement`) | p.555 (printed Appendix I-6) | AXIS_TABLE_PLACEMENT |
| 111 / Variable Information Indicator [2] (El. 111) | `031` | `031` | Yes | Fixed value set by ATL105 for this field (`fixed_value`) | p.569 (printed Appendix I-20) | MANDATORY_VARIABLE_INFORMATION_TABLE |
| 111 / Variable Information Length [2] (El. 112) | `002` | `002` | Yes | Calculated from the length of the table data (`derived_from_table_data`) | - | - |
| 111 / Variable Information [2] (El. 113) | `12` | `12` | Yes | Certified capability of the merchant's terminal (generator merchant settings) (`realistic_terminal_capability`) | - | TERMINAL_CAPABILITY |

Generator notes used above (full text of `flags`):

- DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized
- QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead
- TERMINAL_ID_PER_MERCHANT: one Terminal Identifier per merchant terminal (element 102, p.417); State Code = numeric ANSI code (Appendix D, p.514)
- AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4
- CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable
- FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'
- CARD_DATA_KEYED: manually entered card - expiration date MMYY (element 12, p.372; p.81)
- CONDITIONAL_FIELD_OMITTED
- SEQUENCE_ALLOCATED: unique per terminal across the run set; follow-ons carry the original's number (element 86, p.409, BR-409-3)
- RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied
- QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead
- AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='1' (pin_entry_capability)
- MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p569)
- TERMINAL_CAPABILITY: the merchant terminal's certified capability (merchants.json terminal_capability; Table 031, p.569, BR-569-1)
- FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '8'
- CARD_DATA_FOLLOW_ON: follow-on transaction - Account Number followed by the 4-digit Expiration Date YYMM only, no other discretionary data (credit p.94-95, debit p.105, PIPS 7.1.11)
- FLOW_LINK: 'Nonfuel Amount' is equal relative to step 1
- DELIBERATE_CONSTRAINT_VIOLATION: 'Sequence Number' deliberately NOT carry_request_field from step 1 — The Sequence Number must be the Sequence Number contained in the original transaction.
- UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)
- FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile
- FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec
- FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time

<a id="s4"></a>

## 4. Field-by-field check

For each row: find the JSON key in the test data file, read the value, apply the ATL105 rule (linked) and compare with the expected value.

### 4.1 Step 1 - [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json)

| # | Field (Element) | JSON key | Value in the file | ATL105 rule | How to check | Expected | AI result | Your check |
|---|---|---|---|---|---|---|---|---|
| DS1-1 | Message Format Version Identifier (55) | `MessageFormatVersionIdentifier` | `ATL105` | Fixed 6 characters: ATL105 ([S11.1](#x-s11-1), [EL55](#x-el55)) | Read the value. | ATL105 | PASS | ☐ |
| DS1-2 | Number of Segments (63) | `NumberofSegments` | `02` | Up to two digits: Segment 100 plus N-1 other segments ([S11.1](#x-s11-1), [EL63](#x-el63)) | Count the segment objects: Standard Message Data Segment, Variable Information Data Segment = 2. | 02 | PASS | ☐ |
| 100-1 | Segment Type (85) | `SegmentType` | `100` | Fixed value: 100 ([S12.1](#x-s12-1), [EL85](#x-el85)) | Read the value. | 100 | PASS | ☐ |
| 100-2 | Segment Length (84) | `SegmentLength` | `078` | Length of all 17 fields + all Field Separators ([S12.1](#x-s12-1), [EL84](#x-el84)) | Add up the fields - section 5.1, step 1. | 078 | PASS | ☐ |
| 100-3 | Information Byte (44) | `InformationByte` | `0` | One character: ? download, 0 single-message, 1 multimessage ([EL44](#x-el44)) | `0` = single-message transmission. | 0 (single-message) | PASS | ☐ |
| 100-4 | Terminal Identifier (102) | `TerminalIdentifier` | `JX72948216050257001` | Up to 22 characters: Device Type + ANSI State Code + BUYPASS Merchant Number + Device Number; assigned by BUYPASS ([EL102](#x-el102), [APP-D](#x-app-d)) | 19 characters (at most 22). Device Type `JX`, State Code `72` = Yukon (YT), Canada (Appendix D), then `948216050257001` = BUYPASS Merchant Number (6-15) + Device Number (3). Only BUYPASS can confirm it. | Valid layout; the real value comes from BUYPASS (placeholder here) | PASS | ☐ |
| 100-5 | Prompt Code (78) | `PromptCode.TransactionType + PromptCode.CardType` | `0020` | 4 characters: Transaction Type (Appendix G) + Card Type (Appendix E) ([EL78](#x-el78), [APP-G](#x-app-g), [APP-E](#x-app-e)) | JSON splits it into TransactionType `0` and CardType `020`; on the wire `0020`. Appendix G: `0` = POS Purchase/Capture; Appendix E: `020` = Credit. | 0020 | PASS | ☐ |
| 100-6 | Account Number (2) | `AccountNumber` | `5187270512804249` | Up to 24 characters ([EL2](#x-el2)) | 16 digits (at most 24). MasterCard range 51-55 / 2221-2720 (starts 51); last digit is a Luhn check digit - section 5.3. | MasterCard number, valid check digit | PASS | ☐ |
| 100-7 | Card Discretionary Block Data (12) | `CardDiscretionaryBlockData` | `0928` | Keyed card: expiry MMYY ([EL12](#x-el12), [S10.1.1](#x-s10-1-1)) | `0928` = month 09, year 2028 = September 2028; not expired on November 2026. | MMYY, valid month, not expired | PASS | ☐ |
| 100-8 | Encrypted PIN Block Data (33) | `EncryptedPINBlockData` | (empty) | Conditional (C): encrypted PIN block (DUKPT); this keyed credit transaction has no PIN ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-9 | Pump/Lane Number (79) | `PumpLaneNumber` | (empty) | Conditional (C): pump or lane number; not a petroleum transaction ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-10 | Fuel Purchase Amount (41) | `FuelPurchaseAmount` | (empty) | Conditional (C): fuel purchase amount; no fuel ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-11 | Nonfuel Amount (58) | `NonfuelAmount` | `1500` | Up to 8 digits, two assumed decimals; at most 7 digits for non-American Express cards ([EL58](#x-el58)) | `1500` = $15.00; digits only, 4 digits. | 1500 ($15.00, scenario amount) | PASS | ☐ |
| 100-12 | Tax Amount (99) | `TaxAmount` | (empty) | Conditional (C): tax amount; not sent ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-13 | Cash Amount (17) | `CashAmount` | (empty) | Conditional (C): cash amount (cash back); not used ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-14 | Sequence Number (86) | `SequenceNumber` | `208041` | 6 digits 000001-999999, unique per transaction; follow-ons carry the original's number ([EL86](#x-el86)) | `208041`: 6 digits, not 000000. Unique for this terminal (section 10). | 6 digits, unique | PASS | ☐ |
| 100-15 | Approval Number (5) | `ApprovalNumber` | (empty) | Conditional: required on reversals; not sent on a purchase or a Time-out Reversal ([EL5](#x-el5)) | Not sent on a purchase (it would make the purchase preauthorized). | (empty) | PASS | ☐ |
| 100-16 | Local Date and Local Time (49) | `LocalDateandLocalTime` | (empty) | Conditional (C): local date and time of a preauthorized transaction; not preauthorized ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-17 | Partial Approval Indicator (121) | `PartialApprovalIndicator` | `1` | One digit: 0 not supported, 1 supported, 5 card balance only (American Express prepaid) ([EL121](#x-el121)) | `1` = partial approval supported; required on all transactions. | 0 or 1 (merchant setting; 1 here) | PASS | ☐ |
| 111-1 | Segment Type (85) | `SegmentType` | `111` | Fixed value: 111 ([S12.10](#x-s12-10), [EL85](#x-el85)) | Read the value. | 111 | PASS | ☐ |
| 111-2 | Segment Length (84) | `SegmentLength` | `026` | Length incl. Segment Type and Field Separators ([S12.10](#x-s12-10), [EL84](#x-el84)) | Section 5.2, step 1. | 026 | PASS | ☐ |
| 111-3.1 | Variable Information Indicator (111) | `VariableInformationSection[0].VariableInformationIndicator` | `005` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-005](#x-app-i-005)) | `005` = Table 005. | 005 | PASS | ☐ |
| 111-4.1 | Variable Information Length (112) | `VariableInformationSection[0].VariableInformationLength` | `003` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `011` has 3 characters. | 003 | PASS | ☐ |
| 111-5.1 | Variable Information (113) - Table 005 | `VariableInformationSection[0].VariableInformation` | `011` | PAN Entry Mode (1-2) + Terminal PIN Entry Capability (3) ([EL113](#x-el113), [APP-I-005](#x-app-i-005), [APP-J](#x-app-j)) | Positions 1-2 `01` = manual/keyed; position 3 `1` = PIN entry capability (Appendix J). Manual / Keyed scenario: 01. | 011 (01 keyed + 1 PIN capability of a chip terminal) | PASS | ☐ |
| 111-3.2 | Variable Information Indicator (111) | `VariableInformationSection[1].VariableInformationIndicator` | `031` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-031](#x-app-i-031)) | `031` = Table 031. | 031 | PASS | ☐ |
| 111-4.2 | Variable Information Length (112) | `VariableInformationSection[1].VariableInformationLength` | `002` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `12` has 2 characters. | 002 | PASS | ☐ |
| 111-5.2 | Variable Information (113) - Table 031 | `VariableInformationSection[1].VariableInformation` | `12` | Terminal capability, required on every transaction ([APP-I-031](#x-app-i-031)) | `12` = hybrid reader: contact chip and contactless chip. With Table 005 position 3 = 1 (chip terminal, Appendix J note) the two tables describe the same terminal. | 12 (hybrid chip terminal of this merchant) | PASS | ☐ |

### 4.2 Step 2 - [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json)

| # | Field (Element) | JSON key | Value in the file | ATL105 rule | How to check | Expected | AI result | Your check |
|---|---|---|---|---|---|---|---|---|
| DS1-1 | Message Format Version Identifier (55) | `MessageFormatVersionIdentifier` | `ATL105` | Fixed 6 characters: ATL105 ([S11.1](#x-s11-1), [EL55](#x-el55)) | Read the value. | ATL105 | PASS | ☐ |
| DS1-2 | Number of Segments (63) | `NumberofSegments` | `02` | Up to two digits: Segment 100 plus N-1 other segments ([S11.1](#x-s11-1), [EL63](#x-el63)) | Count the segment objects: Standard Message Data Segment, Variable Information Data Segment = 2. | 02 | PASS | ☐ |
| 100-1 | Segment Type (85) | `SegmentType` | `100` | Fixed value: 100 ([S12.1](#x-s12-1), [EL85](#x-el85)) | Read the value. | 100 | PASS | ☐ |
| 100-2 | Segment Length (84) | `SegmentLength` | `084` | Length of all 17 fields + all Field Separators ([S12.1](#x-s12-1), [EL84](#x-el84)) | Add up the fields - section 5.1, step 2. | 084 (the run-time Approval Number is always 6 characters) | PASS | ☐ |
| 100-3 | Information Byte (44) | `InformationByte` | `0` | One character: ? download, 0 single-message, 1 multimessage ([EL44](#x-el44)) | `0` = single-message transmission. | 0 (single-message) | PASS | ☐ |
| 100-4 | Terminal Identifier (102) | `TerminalIdentifier` | `JX72948216050257001` | Up to 22 characters: Device Type + ANSI State Code + BUYPASS Merchant Number + Device Number; assigned by BUYPASS ([EL102](#x-el102), [APP-D](#x-app-d)) | 19 characters (at most 22). Device Type `JX`, State Code `72` = Yukon (YT), Canada (Appendix D), then `948216050257001` = BUYPASS Merchant Number (6-15) + Device Number (3). Only BUYPASS can confirm it. Follow-on of step 1: step 1 used `JX72948216050257001` - same device. | Valid layout; the real value comes from BUYPASS (placeholder here); same as step 1 | PASS | ☐ |
| 100-5 | Prompt Code (78) | `PromptCode.TransactionType + PromptCode.CardType` | `8020` | 4 characters: Transaction Type (Appendix G) + Card Type (Appendix E) ([EL78](#x-el78), [APP-G](#x-app-g), [APP-E](#x-app-e)) | JSON splits it into TransactionType `8` and CardType `020`; on the wire `8020`. Appendix G: `8` = Purchase reversal; Appendix E: `020` = Credit. | 8020 | PASS | ☐ |
| 100-6 | Account Number (2) | `AccountNumber` | `5187270512804249` | Up to 24 characters ([EL2](#x-el2)) | 16 digits (at most 24). MasterCard range 51-55 / 2221-2720 (starts 51); last digit is a Luhn check digit - section 5.3. Same card as step 1: `5187270512804249`. | MasterCard number, valid check digit; same as step 1 | PASS | ☐ |
| 100-7 | Card Discretionary Block Data (12) | `CardDiscretionaryBlockData` | `2809` | Follow-on credit message: expiry YYMM only, joined to the Account Number by '=' ([S10.1.8.2](#x-s10-1-8-2), [EL12](#x-el12)) | Step 1 sent MMYY `0928`; as YYMM `2809`. | 2809 | PASS | ☐ |
| 100-8 | Encrypted PIN Block Data (33) | `EncryptedPINBlockData` | (empty) | Conditional (C): encrypted PIN block (DUKPT); this keyed credit transaction has no PIN ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-9 | Pump/Lane Number (79) | `PumpLaneNumber` | (empty) | Conditional (C): pump or lane number; not a petroleum transaction ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-10 | Fuel Purchase Amount (41) | `FuelPurchaseAmount` | (empty) | Conditional (C): fuel purchase amount; no fuel ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-11 | Nonfuel Amount (58) | `NonfuelAmount` | `1500` | Up to 8 digits, two assumed decimals; at most 7 digits for non-American Express cards ([EL58](#x-el58)) | `1500` = $15.00; digits only, 4 digits. | 1500 (same as step 1) | PASS | ☐ |
| 100-12 | Tax Amount (99) | `TaxAmount` | (empty) | Conditional (C): tax amount; not sent ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-13 | Cash Amount (17) | `CashAmount` | (empty) | Conditional (C): cash amount (cash back); not used ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-14 | Sequence Number (86) | `SequenceNumber` | `000002` | 6 digits 000001-999999, unique per transaction; follow-ons carry the original's number ([EL86](#x-el86), [S10.1.2.1](#x-s10-1-2-1)) | `000002`: 6 digits, not 000000. A reversal keeps the original's number: step 1 has `208041` - **different**. | deliberately different from 208041 **(intended violation)** | PASS | ☐ |
| 100-15 | Approval Number (5) | `ApprovalNumber` | `<ApprovalNumber from step 1 response>` | Conditional: required on reversals; not sent on a purchase or a Time-out Reversal ([EL5](#x-el5)) | The file holds `<ApprovalNumber from step 1 response>`: at run time the Approval Number of the step 1 response (6 characters). | Approval Number of the step 1 response | BLOCKED | ☐ |
| 100-16 | Local Date and Local Time (49) | `LocalDateandLocalTime` | (empty) | Conditional (C): local date and time of a preauthorized transaction; not preauthorized ([S12.1](#x-s12-1)) | Must be empty; the Field Separator is still sent. | (empty) | PASS | ☐ |
| 100-17 | Partial Approval Indicator (121) | `PartialApprovalIndicator` | `1` | One digit: 0 not supported, 1 supported, 5 card balance only (American Express prepaid) ([EL121](#x-el121)) | `1` = partial approval supported; required on all transactions. | 0 or 1 (merchant setting; 1 here) | PASS | ☐ |
| 111-1 | Segment Type (85) | `SegmentType` | `111` | Fixed value: 111 ([S12.10](#x-s12-10), [EL85](#x-el85)) | Read the value. | 111 | PASS | ☐ |
| 111-2 | Segment Length (84) | `SegmentLength` | `026` | Length incl. Segment Type and Field Separators ([S12.10](#x-s12-10), [EL84](#x-el84)) | Section 5.2, step 2. | 026 | PASS | ☐ |
| 111-3.1 | Variable Information Indicator (111) | `VariableInformationSection[0].VariableInformationIndicator` | `005` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-005](#x-app-i-005)) | `005` = Table 005. | 005 | PASS | ☐ |
| 111-4.1 | Variable Information Length (112) | `VariableInformationSection[0].VariableInformationLength` | `003` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `011` has 3 characters. | 003 | PASS | ☐ |
| 111-5.1 | Variable Information (113) - Table 005 | `VariableInformationSection[0].VariableInformation` | `011` | PAN Entry Mode (1-2) + Terminal PIN Entry Capability (3) ([EL113](#x-el113), [APP-I-005](#x-app-i-005), [APP-J](#x-app-j)) | Positions 1-2 `01` = manual/keyed; position 3 `1` = PIN entry capability (Appendix J). Manual / Keyed scenario: 01. A follow-on keeps the original's PAN Entry Mode (step 1: `01`). | 011 (01 keyed + 1 PIN capability of a chip terminal) | PASS | ☐ |
| 111-3.2 | Variable Information Indicator (111) | `VariableInformationSection[1].VariableInformationIndicator` | `031` | 3 characters: the table ID ([EL111](#x-el111), [APP-I-031](#x-app-i-031)) | `031` = Table 031. | 031 | PASS | ☐ |
| 111-4.2 | Variable Information Length (112) | `VariableInformationSection[1].VariableInformationLength` | `002` | 3 digits = length of the table data ([EL112](#x-el112), [S12.10](#x-s12-10)) | `12` has 2 characters. | 002 | PASS | ☐ |
| 111-5.2 | Variable Information (113) - Table 031 | `VariableInformationSection[1].VariableInformation` | `12` | Terminal capability, required on every transaction ([APP-I-031](#x-app-i-031)) | `12` = hybrid reader: contact chip and contactless chip. With Table 005 position 3 = 1 (chip terminal, Appendix J note) the two tables describe the same terminal. | 12 (hybrid chip terminal of this merchant) | PASS | ☐ |

<a id="s5"></a>

## 5. Calculations

<a id="s5-1"></a>
### 5.1 Segment 100 length

Rule ([S12.1](#x-s12-1), [EL84](#x-el84)): count every field of the segment - including Segment Type and Segment Length - plus one Field Separator after each of the 17 fields, even when the field is empty.

**Step 1 - TD-TC-020804.step1.purchase.json**

| No. | Field | Value counted | Characters | Field Separator | Running total |
|---|---|---|---|---|---|
| 1 | Segment Type | `100` | 3 | 1 | 4 |
| 2 | Segment Length | `078` | 3 | 1 | 8 |
| 3 | Information Byte | `0` | 1 | 1 | 10 |
| 4 | Terminal Identifier | `JX72948216050257001` | 19 | 1 | 30 |
| 5 | Prompt Code | `0020` | 4 | 1 | 35 |
| 6 | Account Number | `5187270512804249` | 16 | 1 | 52 |
| 7 | Card Discretionary Block Data | `0928` | 4 | 1 | 57 |
| 8 | Encrypted PIN Block Data | (empty) | 0 | 1 | 58 |
| 9 | Pump/Lane Number | (empty) | 0 | 1 | 59 |
| 10 | Fuel Purchase Amount | (empty) | 0 | 1 | 60 |
| 11 | Nonfuel Amount | `1500` | 4 | 1 | 65 |
| 12 | Tax Amount | (empty) | 0 | 1 | 66 |
| 13 | Cash Amount | (empty) | 0 | 1 | 67 |
| 14 | Sequence Number | `208041` | 6 | 1 | 74 |
| 15 | Approval Number | (empty) | 0 | 1 | 75 |
| 16 | Local Date and Local Time | (empty) | 0 | 1 | 76 |
| 17 | Partial Approval Indicator | `1` | 1 | 1 | 78 |

Total **078**; the file declares `078`: **match**.

**Step 2 - TD-TC-020804.step2.reversal.json**

| No. | Field | Value counted | Characters | Field Separator | Running total |
|---|---|---|---|---|---|
| 1 | Segment Type | `100` | 3 | 1 | 4 |
| 2 | Segment Length | `084` | 3 | 1 | 8 |
| 3 | Information Byte | `0` | 1 | 1 | 10 |
| 4 | Terminal Identifier | `JX72948216050257001` | 19 | 1 | 30 |
| 5 | Prompt Code | `8020` | 4 | 1 | 35 |
| 6 | Account Number | `5187270512804249` | 16 | 1 | 52 |
| 7 | Card Discretionary Block Data | `2809` | 4 | 1 | 57 |
| 8 | Encrypted PIN Block Data | (empty) | 0 | 1 | 58 |
| 9 | Pump/Lane Number | (empty) | 0 | 1 | 59 |
| 10 | Fuel Purchase Amount | (empty) | 0 | 1 | 60 |
| 11 | Nonfuel Amount | `1500` | 4 | 1 | 65 |
| 12 | Tax Amount | (empty) | 0 | 1 | 66 |
| 13 | Cash Amount | (empty) | 0 | 1 | 67 |
| 14 | Sequence Number | `000002` | 6 | 1 | 74 |
| 15 | Approval Number | `7IYI8R` (test case value; the file holds `<ApprovalNumber from step 1 response>`) | 6 | 1 | 81 |
| 16 | Local Date and Local Time | (empty) | 0 | 1 | 82 |
| 17 | Partial Approval Indicator | `1` | 1 | 1 | 84 |

Total **084**; the file declares `084`: **match**. A real Approval Number is always 6 characters, so the length stays the same after substitution.

<a id="s5-2"></a>
### 5.2 Segment 111 length

Rule ([S12.10](#x-s12-10), [EL84](#x-el84)): Field Separator after Field 1 and Field 2; none inside or between the tables; one after the final table.

**Step 1**

| Part | Value | Characters | Field Separator | Running total |
|---|---|---|---|---|
| Segment Type | `111` | 3 | 1 | 4 |
| Segment Length | `026` | 3 | 1 | 8 |
| Table 005: indicator + length + data | `005003011` | 9 | 0 | 17 |
| Table 031: indicator + length + data | `03100212` | 8 | 1 | 26 |

Total **026**; the file declares `026`: **match**.

**Step 2**

| Part | Value | Characters | Field Separator | Running total |
|---|---|---|---|---|
| Segment Type | `111` | 3 | 1 | 4 |
| Segment Length | `026` | 3 | 1 | 8 |
| Table 005: indicator + length + data | `005003011` | 9 | 0 | 17 |
| Table 031: indicator + length + data | `03100212` | 8 | 1 | 26 |

Total **026**; the file declares `026`: **match**.

<a id="s5-3"></a>
### 5.3 Card number check digit (Luhn) and expiry

ATL105 refers card numbers to the BUYPASS IIN Reference Manual ([EL2](#x-el2)); MasterCard numbers start with 51-55 or 2221-2720 and end with a Luhn check digit: from the right, double every second digit (subtract 9 above 9) and add everything; the sum must end in 0.

**`5187270512804249`**

| Position | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Digit | 5 | 1 | 8 | 7 | 2 | 7 | 0 | 5 | 1 | 2 | 8 | 0 | 4 | 2 | 4 | 9 |
| Doubled? | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  | ×2 |  |
| Value added | 1 | 1 | 7 | 7 | 4 | 7 | 0 | 5 | 2 | 2 | 7 | 0 | 8 | 2 | 8 | 9 |

Sum = 70 → check digit **valid**; starts with `51` → **in** the MasterCard range.

| Step | Card Discretionary Block Data | Format | Meaning | Check |
|---|---|---|---|---|
| 1 | `0928` | MMYY ([EL12](#x-el12)) | September 2028 | month valid; **not expired** on November 2026 |
| 2 | `2809` | YYMM ([S10.1.8.2](#x-s10-1-8-2)) | September 2028 | step 1 MMYY `0928` → YYMM `2809`: **same** |

<a id="s6"></a>

## 6. Links between the steps

### 6.2 Step 2 (reversal) compared with step 1

| Check | Step 1 | Step 2 | Result | Expected | ATL105 | Your check |
|---|---|---|---|---|---|---|
| Sequence Number | `208041` | `000002` | **different - intended** | same as the original | [EL86](#x-el86), [S10.1.2.1](#x-s10-1-2-1) | ☐ |
| Account Number | `5187270512804249` | `5187270512804249` | same | same as the original | [S2.3.1](#x-s2-3-1) | ☐ |
| Terminal Identifier (same device) | `JX72948216050257001` | `JX72948216050257001` | same | same as the original | [S2.3.1](#x-s2-3-1), [EL102](#x-el102) | ☐ |
| Nonfuel Amount | `1500` | `1500` | same | same as the original | [S2.3.1](#x-s2-3-1), [S10.1.2.1](#x-s10-1-2-1) | ☐ |
| Expiry (MMYY → YYMM) | `0928` | `2809` | same | `2809` | [S10.1.8.2](#x-s10-1-8-2) | ☐ |
| Approval Number | (empty) | `<ApprovalNumber from step 1 response>` | run-time placeholder | Approval Number of the step 1 response | [EL5](#x-el5) | ☐ |
| Prompt Code | `0020` | `8020` | Purchase reversal | 8020 | [APP-G](#x-app-g) | ☐ |
| PAN Entry Mode (Table 005 pos. 1-2) | `01` | `01` | same | same as the original | [APP-J](#x-app-j) | ☐ |

Link rules written in the test case (`tx_legs[1].link_defs`):

| Type | Field | Page cited | Rule text in the test case |
|---|---|---|---|
| `carry_response_field` | Approval Number | p.368 (printed 13-28) | Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runtime link, never invented). |
| `carry_request_field` | Terminal Identifier | p.40 (printed 2-5) | Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching). |
| `amount_relation` | Nonfuel Amount | p.84 (printed 10-5) | Plan v7 W2: p.84 'The amount in a Reversal (Void) request must be the amount approved in the original transaction response' (full approval: equal to the original requested amount). |

**Clean-up after this negative test:** step 1 is approved and the broken follow-on is expected to be declined, so step 1 stays approved on the host. Send a correct Purchase Reversal (same terminal, Sequence Number `208041`) afterwards, or include the purchase in the totals check.

<a id="s7"></a>

## 7. Expected responses

Response Codes are listed in [EL83X](#x-el83x). Tier: ASSERT = taken from ATL105; FLAG / INFER = not fully grounded, to be confirmed.

| Step | Expected | Tier | Meaning | ATL105 basis | AI result | Your check |
|---|---|---|---|---|---|---|
| 1 | `0` | ASSERT | Approved—Purchase/Capture | [EL83X](#x-el83x) | PASS | ☐ |
| 2 | `1` | FLAG | Declined—All other transactions | [EL83X](#x-el83x) | PASS | ☐ |

<a id="s8"></a>

## 8. Citations written in the test case (`spec_basis`)

ATL105 quotes were checked word for word on the cited page by the AI validation; quotes from other documents (Petroleum Industry Processing Specification, EMV guide) are listed but not checked here.

| # | Source | Page cited | Quote | AI check | Your check |
|---|---|---|---|---|---|
| 1 | ATL105 | p.546 (printed Appendix G-2) | 8 Purchase reversal Purchase reversal/void | PASS - Found word-for-word on the cited page | ☐ |
| 2 | ATL105 | p.402 (printed 13-62) | The Sequence Number must be the Sequence Number contained in the original transaction. | REVIEW - Found on a different page, not the cited one (p.84, p.89, p.98) | ☐ |
| 3 | ATL105 | p.402 (printed 13-62) | Variable length of up to nine digits with a maximum of three assumed decimal places Purpose: | PASS - Found word-for-word on the cited page | ☐ |
| 4 | ATL105 | p.45 (printed 2-10) | The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. | PASS - Found word-for-word on the cited page | ☐ |
| 5 | ATL105 | p.409 (printed 13-69) | In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture identifies the Sequence Number in the transaction's Authorization Only request. | PASS - Found word-for-word on the cited page | ☐ |
| 6 | ATL105 | p.511 (printed Appendix C-1) | 03 MasterCard Credit | PASS - Found word-for-word on the cited page | ☐ |
| 7 | ATL105 | p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Purchase/Capture | PASS - Found word-for-word on the cited page | ☐ |
| 8 | ATL105 | p.81 (printed 10-2) | Manual entry is allowed on a credit card transaction | PASS - Found word-for-word on the cited page | ☐ |

<a id="s9"></a>

## 9. What is wrong, missing or to confirm - and how to check each finding by hand

| # | Kind | Finding | Step | Severity | Found by |
|---|---|---|---|---|---|
| [1](#i1) | Test case issue to correct | Marked 'ready' although the terminal is a placeholder | all | REVIEW | Trained AI validator |
| [2](#i2) | Citation issue | The quote cited for p.402 is on a different page | all | REVIEW | Trained AI validator |
| [3](#i3) | Awaiting client input / run-time value | The Terminal Identifier is a placeholder | all | REVIEW | Trained AI validator |
| [4](#i4) | Awaiting client input / run-time value | Approval Number holds instruction text, not a value | 2 | BLOCKED | Trained AI validator |
| [5](#i5) | Intended violation of a negative test (correct) | Step 2: same sequence number as the original - check failed | 2 | FAIL | Trained AI validator |
| [6](#i6) | Information | Join Account Number and expiry with '=' in the wire message | 2 | INFO | Trained AI validator |

<a id="i1"></a>

### 9.1 Marked 'ready' although the terminal is a placeholder - Test case issue to correct

| Item | Value |
|---|---|
| Where | test case status |
| Value | `ready` |
| ATL105 | [EL102](#x-el102) Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." |
| Why | The test case status is 'ready', but its Terminal Identifier is still a placeholder. |
| What it should be | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| Defect id (summary / sheet) | C-03 |

**How to confirm by hand**

1. Open [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100): `status` = `ready`.
2. The previous finding shows the Terminal Identifier is a placeholder, so BUYPASS cannot accept the message.
3. Conclusion: the status should not be 'ready' until the real Terminal Identifier is supplied.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i2"></a>

### 9.2 The quote cited for p.402 is on a different page - Citation issue

| Item | Value |
|---|---|
| Where | test case spec_basis |
| Value | `The Sequence Number must be the Sequence…transaction.` |
| ATL105 | - |
| Why | The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98). |
| What it should be | Cite p.84 instead of p.402. |
| Defect id (summary / sheet) | Q-02 |

**How to confirm by hand**

1. Open [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100), `spec_basis`: the entry with page 402 quotes "The Sequence Number must be the Sequence Number contained in the original transaction.".
2. Search that text in the PDF (Ctrl+F) on the cited page. It is on PDF p.84 (printed 10-5), PDF p.89 (printed 10-10), PDF p.98 (printed 10-19) - see [S10.1.2.1](#x-s10-1-2-1).
3. Conclusion: Found on a different page, not the cited one. The test data is not affected.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i3"></a>

### 9.3 The Terminal Identifier is a placeholder - Awaiting client input / run-time value

| Item | Value |
|---|---|
| Where | Standard Message Data Segment -> TerminalIdentifier (every step) |
| Value | `JX72948216050257001` |
| ATL105 | [EL102](#x-el102) Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."<br>[EL102](#x-el102) Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."<br>[APP-D](#x-app-d) Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72" |
| Why | The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada. |
| What it should be | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| Defect id (summary / sheet) | I-01 |

**How to confirm by hand**

1. Open [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100): `merchant.name` = "MERCHANT 01 (POS PLACEHOLDER)", `merchant_provenance` = "placeholder".
2. Read [EL102](#x-el102): "The Terminal Identifier must be assigned by and set up at BUYPASS."
3. Positions 3-4 of `JX72948216050257001` are `72` = Yukon (YT), Canada ([APP-D](#x-app-d)).
4. Conclusion: ask the client / BUYPASS for the real Terminal Identifier before execution.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i4"></a>

### 9.4 Approval Number holds instruction text, not a value - Awaiting client input / run-time value

| Item | Value |
|---|---|
| Where | TD-TC-020804.step2.reversal.json -> Standard Message Data Segment -> ApprovalNumber |
| Value | `<ApprovalNumber from step 1 response>` |
| ATL105 | [EL5](#x-el5) Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."<br>[EL5](#x-el5) Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled" |
| Why | The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in. |
| What it should be | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runtime link, never invented). |
| Defect id (summary / sheet) | I-02 |

**How to confirm by hand**

1. Open [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json#L23): `ApprovalNumber` = `<ApprovalNumber from step 1 response>` - instruction text in angle brackets, not a value.
2. Read [EL5](#x-el5): "Required on reversals for matching information to the original purchase"; six alphanumeric characters.
3. Conclusion: expected for a follow-on step - the harness fills in the Approval Number of the step 1 response before sending. Not a defect.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i5"></a>

### 9.5 Step 2: same sequence number as the original - check failed - Intended violation of a negative test (correct)

| Item | Value |
|---|---|
| Where | TD-TC-020804.step2.reversal.json -> Standard Message Data Segment -> SequenceNumber |
| Value | `000002` |
| ATL105 | [EL86](#x-el86) Element 86 Sequence Number (Chapter 13), PDF p.409 (printed 13-69): "In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request."<br>[S10.1.2.1](#x-s10-1-2-1) Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "The Sequence Number must be the Sequence Number contained in the original transaction request." |
| Why | Step 2 has '000002' and step 1 has '208041'. |
| What it should be | 208041 |
| Defect id (summary / sheet) | N-01 |

**How to confirm by hand**

1. Open [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json#L22) and [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json#L22): step 1 `208041`, step 2 `000002`.
2. Read [EL86](#x-el86), [S10.1.2.1](#x-s10-1-2-1): a reversal must carry the original's Sequence Number.
3. Open [test_cases.jsonl line 100](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L100): `steps[1].request["Standard Message Data Segment"]["Sequence Number"]` has `method` = `deliberate_violation_missing_carry` and the flag DELIBERATE_CONSTRAINT_VIOLATION.
4. Conclusion: the difference is the rule this negative test breaks on purpose - **intended, correct**.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="i6"></a>

### 9.6 Join Account Number and expiry with '=' in the wire message - Information

| Item | Value |
|---|---|
| Where | TD-TC-020804.step2.reversal.json -> AccountNumber, CardDiscretionaryBlockData |
| Value | `5187270512804249 / 2809` |
| ATL105 | [S10.1.8.2](#x-s10-1-8-2) Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." |
| Why | The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way. |
| What it should be | The message builder sends 5187270512804249=2809. |
| Defect id (summary / sheet) | INFO |

**How to confirm by hand**

1. In [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json#L14): `AccountNumber` = `5187270512804249`, `CardDiscretionaryBlockData` = `2809`.
2. Read [S10.1.8.2](#x-s10-1-8-2): the two items "must be separated by an “=” sign, rather than a Field Separator".
3. Conclusion: on the wire `5187270512804249=2809`; the Segment Length does not change. Information only.

Your conclusion: ☐ Agree ☐ Disagree - note: ____________

<a id="s10"></a>

## 10. Checks across test cases

| Check | Result | Detail |
|---|---|---|
| Sequence Number step 1 `208041` | PASS | new transaction: unique on terminal JX72948216050257001 across all 1,861 test cases |
| Sequence Number step 2 `000002` | PASS | follow-on: carries / is meant to carry the step 1 number |

<a id="s11"></a>

## 11. ATL105 text used in this guide

Copied from the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). Each line below was found on the stated PDF page when this guide was generated.

<a id="x-s2-3-1"></a>

### [S2.3.1] Section 2.3.1 Time-out by Customer's Host/Controller/POS System

*PDF p.40 (printed 2-5)*

> A time-out occurs at the customer’s host/controller/POS system when either of the following situations occurs:<br>• The customer’s 30-second response interval expires without a response from BUYPASS.<br>• A response from BUYPASS is received after the customer’s 30-second response interval expires.
>
> If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored.
>
> Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR, which is queued in the customer’s host/controller/POS system until the next financial transaction is initiated for that device. A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.
>
> The Sequence Number, Account Number, and dollar amount—assigned by the customer’s host/controller/POS system—are the elements used to match a request with its respective response. The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number by the customer’s host/controller/POS system.
>
> Any response from BUYPASS to a TOR, whether it is an approval or a decline, is considered a valid response and should clear the TOR from the customer’s host/controller/POS queue.
>
> BUYPASS declines a TOR request as an unmatched reversal when BUYPASS has not received the original financial transaction request related to the TOR; or when the original financial transaction has already been reversed.

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

<a id="x-s10-1-2-1"></a>

### [S10.1.2.1] Section 10.1.2.1 Partial Approval Transaction

*PDF p.84 (printed 10-5)*

> A Partial Approval transaction enables a merchant to receive approval for the amount available on the presented credit card when the account balance is more or less than the amount of the Transaction Request.
>
> Credit networks supporting the Partial Approval transaction return an approval containing the following information:<br>Note: This information is returned when a Partial Approval Indicator value of 1 is sent in Partial Approval Indicator (Element No. 121).<br>• A Response Code (Element No. 83) value of F (Indicates “approval, partial amount approved.”)<br>• Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale.<br>• The card balance—if returned by the issuer—is in the Additional Information Data Segment (Segment No. 112).
>
> Partial Approval transactions are supported for:<br>• POS Purchase<br>• POS Authorization Only<br>• CAT Authorization Only
>
> The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a transaction has been partially approved. BUYPASS supports reversal of a partially approved original authorization and NOT a partial reversal of an original transaction.
>
> The amount in a TOR request must be the amount requested in the original transaction response when a transaction has been partially approved.
>
> The Sequence Number must be the Sequence Number contained in the original transaction request.

<a id="x-s10-1-8-2"></a>

### [S10.1.8.2] Section 10.1.8.2 Track Support Requirements

*PDF p.94-95 (printed 10-15 to 10-16)*

> The host message should only contain the following card data information in the Account Number and Card Discretionary Block Data fields:<br>• Account Number<br>• Expiration Date (Field format: YYMM)
>
> Note: These two items must be separated by an “=” sign, rather than a Field Separator.
>
> • The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). No other

*PDF p.95 (printed 10-16):*

> Card Discretionary Data field follows the 4-digit Expiration Date. This scenario applies only to the following credit transactions:<br>• Credit Completions<br>• Credit POS Purchase Reversals<br>• Credit Time Out Reversals<br>• Credit POS Cancellations<br>• Credit Cancellations

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

*PDF p.514-517 (printed Appendix D-1 to Appendix D-4)*

> The ANSI® State Codes are used in the following data element:<br>• Terminal Identifier (No. 102)
>
> The following table contains a list of valid State Codes for the states of the United States of America:

| State | Alphabetical Code | ANSI® Code | PDF p. |
|---|---|---|---|
| Maryland | MD | 24 | 515 |

*PDF p.517 (printed Appendix D-4):*

> The following table contains a list of valid State codes for the provinces of Canada:

| Province of Canada | Alphabetical Code | ANSI® Code | PDF p. |
|---|---|---|---|
| Nova Scotia | NS | 66 | 517 |

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
| Field checks (section 4) | 55 PASS, 1 BLOCKED | ☐ Agree ☐ Disagree | |
| Links (section 6) | 1 FAIL, 3 PASS | ☐ Agree ☐ Disagree | |
| Responses (section 7) | 2 PASS | ☐ Agree ☐ Disagree | |
| Citations (section 8) | 7 PASS, 1 REVIEW | ☐ Agree ☐ Disagree | |
| Findings (section 9) | 1 Information, 2 Awaiting client input / run-time value, 1 Intended violation of a negative test (correct), 1 Citation issue, 1 Test case issue to correct | ☐ Agree ☐ Disagree | |
| **Overall** | **BLOCKED** - Only after the client / run-time values are filled in | ☐ Agree ☐ Disagree | |

Reviewed by: ____________________    Date: ____________

