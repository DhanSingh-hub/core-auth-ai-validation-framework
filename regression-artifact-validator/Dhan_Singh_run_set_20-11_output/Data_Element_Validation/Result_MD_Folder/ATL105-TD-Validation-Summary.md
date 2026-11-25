# ATL105 validation of 19 test cases of the run and their 18 test data files

**Run:** [data_element_validation](../../../Dhan_Singh_run_set_20-11/data_element_validation/) (MasterCard Credit run of 19 Nov 2026), build `buypass-atl105-mastercard-credit-generated-35c195d1e7`  
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). The attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the 2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  
**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.

ATL105 result of each test case, every segment and field: [TC-077239](TC-077239.md), [TC-077295](TC-077295.md), [TC-077381](TC-077381.md), [TC-077469](TC-077469.md), [TC-077225](TC-077225.md), [TC-077230](TC-077230.md), [TC-077240](TC-077240.md), [TC-077255](TC-077255.md), [TC-077261](TC-077261.md), [TC-078056](TC-078056.md), [TC-078069](TC-078069.md), [TC-077849](TC-077849.md), [TC-077229](TC-077229.md), [TC-078058](TC-078058.md), [TC-077358](TC-077358.md), [TC-077413](TC-077413.md), [TC-077480](TC-077480.md), [TC-077553](TC-077553.md), [TC-077628](TC-077628.md). Detailed step-by-step guides for 2 test cases: [TC-077239](../Manual%20Validation/TC-077239-Manual-Validation.md), [TC-077295](../Manual%20Validation/TC-077295-Manual-Validation.md).

## 1. Result at a glance

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 17 | At least one test data or test case defect that must be fixed |
| **BLOCKED** | 1 | No defect, but a client value or a run-time value is still missing |
| **REVIEW** | 1 | No defect; a citation must be corrected or a point confirmed with BUYPASS |
| **PASS** | 0 | Nothing found |
| **DEVICE** | 0 | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |
| Total | 19 | 18 send a message to BUYPASS (18 test data files); 1 are device-only or have no test data |

Findings by kind (a test case can have several):

| Kind | Findings | Test cases | Effect on the result |
|---|---:|---:|---|
| Test data defect | 30 | 17 | FAIL |
| Test case defect | 2 | 2 | FAIL |
| Test case issue to correct | 12 | 11 | REVIEW |
| Citation issue | 20 | 18 | REVIEW |
| Awaiting client input / run-time value | 43 | 18 | BLOCKED |
| To confirm with BUYPASS / SME | 4 | 4 | REVIEW |
| Intended violation of a negative test (correct) | 7 | 5 | none |
| Not checked - request type outside the validator's trained ATL105 rules | 1 | 1 | none |

Data segments in the test data: 100 (14 files), 104 (1 files), 105 (3 files), 109 (1 files), 111 (14 files), 114 (1 files), 130 (9 files).

## 2. How every test case and test data file was checked

1. **Trained AI validator** (`scripts/validate-td-against-atl105-spec.py`, the rules built in the earlier MasterCard, Interlink and Maestro reviews): Data Section 1, all 17 fields of Segment 100, Segment 111 with Tables 005/031/030 and the Credential on File tables, Segment 130 (EMV) with its TLV chip data, Segment 105 (Totals), links between steps (Sequence Number, card, terminal, entry mode), expected Response Codes, and every ATL105 quote in the test case (`spec_basis`) against the page it cites.
2. **Extended checks for this run** (`scripts/validate-run-and-build-manual-validation.py`): the other data segments (101, 102, 104, 108, 123, 145, 153 - Segment Type, Segment Length with each segment's Field Separator rule, Chapter 13 format of every field, TLV structure), every Variable Information table ID, values awaited from the client, each test case's own `rule_checks`, test data vs test case value fidelity for all segments, identical requests across all test cases, Sequence Number uniqueness per terminal, Time-out Reversal flows, negative-test premises and conditional fields.
3. **Classification**: every finding was sorted into the kinds in section 1. Findings of negative tests that are the rule the test deliberately breaks are listed as *intended* (correct). Where the trained rule did not fit a new flow of this run, the value was checked against ATL105 and the finding listed as *AI rule not applicable* (section 9) with the reason.
4. Every ATL105 text quoted in this report (listed in section 11) was found word for word on the stated page when this report was generated.

## 3. Defects in the test data (must be fixed)

### 3.1 D-02 - Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply

**2 test cases, 2 findings, 2 test data files** - kind: Test data defect

Rules: `X_OPTIONAL_FIELD_CONDITION` (2).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- Section 12.1 Standard Message Data Segment, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction."

What is wrong (one example per kind of problem):

- `X_OPTIONAL_FIELD_CONDITION` (TC-077255) - Fuel Purchase Amount '00000001' at a general (non-petroleum) merchant.
- `X_OPTIONAL_FIELD_CONDITION` (TC-078056) - Local Date and Local Time '0615251430' (Element 49 identifies the date/time of a preauthorized transaction; this step is not one); Pump/Lane Number '1' at a general (non-petroleum) merchant; Fuel Purchase Amount '1' at a general (non-petroleum) merchant.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077255](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) (BRDE_TS1_TC74) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-077255.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) | `Fuel Purchase Amount '00000001' at a gen…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-078056](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) (BRDE_TS22_TC1) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-078056.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) | `Local Date and Local Time '0615251430' (…t; Fuel Purc` | Send conditional fields only when their condition holds. |

Scenarios: BRDE_TS1 (1), BRDE_TS22 (1)

**How to check by hand:** Open the test data file and read ApprovalNumber, EncryptedPINBlockData, LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount, CashAmount; compare with the ATL105 rule of each field.

**Fix:** Send only the conditional fields whose condition holds: no Approval Number / Local Date and Time on a new authorization, a real DUKPT KSN + PIN block (32-36 characters) or nothing in Element 33, no fuel or pump data outside petroleum.

### 3.2 D-09 - A negative test breaks more than the one rule it is meant to break

**6 test cases, 9 findings, 6 test data files** - kind: Test data defect

Rules: `FIELD_105_15` (2), `FIELD_2` (1), `FIELD_5` (1), `X_TD_DIFFERS_FROM_TC` (1), `FIELD_105_16` (1), `FIELD_105_13` (1), `TABLE_030_MISSING` (1), `COF_INDICATORS_MISSING` (1).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Fixed length of five digits"
- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Valid Codes/Values: 00001–99999"
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places"
- Element 13 Card Label (Chapter 13), PDF p.372 (printed 13-32): "Fixed length of four alphanumeric characters; left-aligned and space-filled"
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions."

What is wrong (one example per kind of problem):

- `FIELD_2` (TC-077239) - 'TOTALS REQUEST' fails the card-number check digit (Luhn).
- `FIELD_5` (TC-077295) - '0CI2N6' is sent on a purchase, which makes it preauthorized, so BUYPASS does not go out for authorization.
- `X_TD_DIFFERS_FROM_TC` (TC-077381) - The test case request carries this segment with values, but the test data file does not send it.
- `FIELD_105_16` (TC-077469) - '' Card Type Total Count is required.
- `FIELD_105_15` (TC-077469) - '' Card Type Total Amount is required.
- `FIELD_105_13` (TC-077480) - '' Card Label is required.
- `TABLE_030_MISSING` (TC-077628) - This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- `COF_INDICATORS_MISSING` (TC-077628) - Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077239](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) (BRDE_TS1_TC17) | 1 | `FIELD_2` | [TD-TC-077239.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L14) | `TOTALS REQUEST` | A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule). |
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | 1 | `FIELD_5` | [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L23) | `0CI2N6` | A value that meets: Not sent on a purchase. |
| [TC-077381](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) (BRDE_TS3_TC25) | 1 | `X_TD_DIFFERS_FROM_TC` | [TD-TC-077381.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) | `(absent)` | Regenerate the test data file from the test case. |
| [TC-077469](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) (BRDE_TS4_TC15) | 1 | `FIELD_105_16` | [TD-TC-077469.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) | (empty) | A value that meets: Required. |
| [TC-077469](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) (BRDE_TS4_TC15) | 1 | `FIELD_105_15` | [TD-TC-077469.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) | (empty) | A value that meets: Required. |
| [TC-077480](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) (BRDE_TS4_TC77) | 1 | `FIELD_105_13` | [TD-TC-077480.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) | (empty) | A value that meets: Required. |
| [TC-077628](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406) (BRDE_TS7_TC19) | 1 | `TABLE_030_MISSING` | [TD-TC-077628.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json) | `tables sent: 005, 032, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| [TC-077628](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406) (BRDE_TS7_TC19) | 1 | `COF_INDICATORS_MISSING` | [TD-TC-077628.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json) | `tables sent: 005, 032, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| [TC-077480](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) (BRDE_TS4_TC77) | 1 | `FIELD_105_15` | [TD-TC-077480.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) | (empty) | A value that meets: Required. |

Scenarios: BRDE_TS4 (4), BRDE_TS1 (2), BRDE_TS7 (2), BRDE_TS3 (1)

**How to check by hand:** Compare the failing field with the field the test case deliberately changes (flags DELIBERATE_CONSTRAINT_VIOLATION / method deliberate_violation_*).

**Fix:** Keep every other field valid so the decline can only come from the rule under test.

### 3.3 D-13 - Card read from the chip / stripe, but Card Discretionary Block Data holds only an MMYY expiry instead of the Track 2 data after the '='

**11 test cases, 11 findings, 11 test data files** - kind: Test data defect

Rules: `FIELD_12` (11).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."

What is wrong (one example per kind of problem):

- `FIELD_12` (TC-077295) - '0931' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | 1 | `FIELD_12` | [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L15) | `0931` | A value that meets: AN, variable length 51. |
| [TC-077381](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) (BRDE_TS3_TC25) | 1 | `FIELD_12` | [TD-TC-077381.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L15) | `0427` | A value that meets: AN, variable length 51. |
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | 1 | `FIELD_12` | [TD-TC-077225.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L15) | `0329` | A value that meets: AN, variable length 51. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | 1 | `FIELD_12` | [TD-TC-077230.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L15) | `0130` | A value that meets: AN, variable length 51. |
| [TC-077255](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) (BRDE_TS1_TC74) | 1 | `FIELD_12` | [TD-TC-077255.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L15) | `0428` | A value that meets: AN, variable length 51. |
| [TC-077261](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) (BRDE_TS1_TC75) | 1 | `FIELD_12` | [TD-TC-077261.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L15) | `0529` | A value that meets: AN, variable length 51. |
| [TC-078056](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) (BRDE_TS22_TC1) | 1 | `FIELD_12` | [TD-TC-078056.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L15) | `0432` | A value that meets: AN, variable length 51. |
| [TC-078069](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) (BRDE_TS22_TC49) | 1 | `FIELD_12` | [TD-TC-078069.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json#L20) | `1031` | A value that meets: AN, variable length 51. |
| [TC-077849](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627) (BRDE_TS12_TC23) | 1 | `FIELD_12` | [TD-TC-077849.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json#L15) | `0232` | A value that meets: AN, variable length 51. |
| [TC-077229](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) (BRDE_TS1_TC29) | 1 | `FIELD_12` | [TD-TC-077229.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L15) | `0231` | A value that meets: AN, variable length 51. |
| [TC-078058](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) (BRDE_TS22_TC4) | 1 | `FIELD_12` | [TD-TC-078058.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L15) | `1229` | A value that meets: AN, variable length 51. |

Scenarios: BRDE_TS1 (6), BRDE_TS22 (3), BRDE_TS3 (1), BRDE_TS12 (1)

**How to check by hand:** Open the test data file: Table 005 starts with 05 / 07 / 90 / 91 (card read), but CardDiscretionaryBlockData is 4 digits (MMYY). For a read card it must be the Track 2 data after the '=': expiry YYMM, 3-digit service code, discretionary data.

**Fix:** Send the test card's Track 2 (equivalent) data after the '=' (YYMM + service code 2xx for a chip card + discretionary data), or use a keyed entry mode if only the expiry is known.

### 3.4 D-15 - Positive boundary test: the value is not at the documented limit or is not a valid value

**6 test cases, 8 findings, 6 test data files** - kind: Test data defect

Rules: `X_BOUNDARY_NOT_AT_LIMIT` (2), `FIELD_44` (1), `FIELD_12` (1), `FIELD_41` (1), `FIELD_58` (1), `X_RULE_CHECK` (1), `FIELD_105_44` (1).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
- Element 87 Service Level (Chapter 13), PDF p.410 (printed 13-70): "F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present."
- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission"
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."
- Element 41 Fuel Purchase Amount (Chapter 13), PDF p.385 (printed 13-45): "Representation: Variable length of up to eight digits with two assumed decimal places"
- Element 41 Fuel Purchase Amount (Chapter 13), PDF p.385 (printed 13-45): "Included on any transactions with fuel sales."
- Element 58 Nonfuel Amount (Chapter 13), PDF p.393 (printed 13-53): "A maximum length of 7 bytes is allowed for all other card types."

What is wrong (one example per kind of problem):

- `FIELD_44` (TC-077225) - 'A' is not a valid Information Byte (?, 0 or 1).
- `X_BOUNDARY_NOT_AT_LIMIT` (TC-077230) - The test is meant to send Terminal Identifier at exactly 22 characters, but 'JX41894821001' has 13, so the boundary is not tested.
- `FIELD_12` (TC-077240) - '1030' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.
- `X_BOUNDARY_NOT_AT_LIMIT` (TC-077240) - The test is meant to send Card Discretionary Block Data at exactly 51 characters, but '1030' has 4, so the boundary is not tested.
- `FIELD_41` (TC-077255) - '00000001' is 8 characters long; the field allows at most 7.
- `FIELD_58` (TC-077261) - '00000001' is 8 characters long; the field allows at most 7.
- `X_RULE_CHECK` (TC-077261) - The test case asserts 'Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.)' (value_length_max, expect hold), but in the test data: Nonfuel Amount '00000001' has 8 characters, maximum 7.
- `FIELD_105_44` (TC-077413) - 'A' is not a valid Information Byte (?, 0 or 1).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | 1 | `FIELD_44` | [TD-TC-077225.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L8) | `A` | A value that meets: 1 character: 'A' = not a valid code. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | 1 | `X_BOUNDARY_NOT_AT_LIMIT` | [TD-TC-077230.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L9) | `JX41894821001` | A valid Terminal Identifier of exactly 22 characters, or drop the test if no such value can be approved. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | 1 | `FIELD_12` | [TD-TC-077240.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L15) | `1030` | A value that meets: AN, variable length 51. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | 1 | `X_BOUNDARY_NOT_AT_LIMIT` | [TD-TC-077240.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L15) | `1030` | A valid Card Discretionary Block Data of exactly 51 characters, or drop the test if no such value can be approved. |
| [TC-077255](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) (BRDE_TS1_TC74) | 1 | `FIELD_41` | [TD-TC-077255.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L18) | `00000001` | A value that meets: N, variable length 7 with two assumed decimals: '00000001' = $0.01. |
| [TC-077261](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) (BRDE_TS1_TC75) | 1 | `FIELD_58` | [TD-TC-077261.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L19) | `00000001` | A value that meets: Digits only, at most 7 for MasterCard (8 only for Amex): '00000001' = $0.01. |
| [TC-077261](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) (BRDE_TS1_TC75) | 1 | `X_RULE_CHECK` | test case rule_checks LEG-NONFUEL-AMOUNT-MAX-LENGTH (step 1) | `Nonfuel Amount '00000001' has 8 characters, maximum 7` | Correct the test data (or the rule check) so that the assertion holds. |
| [TC-077413](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) (BRDE_TS4_TC9) | 1 | `FIELD_105_44` | [TD-TC-077413.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json#L8) | `A` | A value that meets: 'A' = not valid. |

Scenarios: BRDE_TS1 (7), BRDE_TS4 (1)

**How to check by hand:** Read the objective (field and limit), then the value in the test data: it must have exactly that many characters and be a valid value of the element.

**Fix:** Send a valid value of exactly the documented maximum length; if no such value can be approved (a MasterCard card number is 16 digits, not 24), drop the positive boundary test.

## 4. Defects and issues in the test cases (design, status, expected results)

### 4.1 C-01 - Same request as another test case but a different expected result

**2 test cases, 2 findings, 2 test data files** - kind: Test case defect

Rules: `X_SAME_REQUEST_DIFFERENT_OUTCOME` (2).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response."

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST_DIFFERENT_OUTCOME` (TC-078069) - Apart from the Sequence Number the message is identical to TC-078060 (all segments compared). TC-078060 expects 0; this test expects 1.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078069](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) (BRDE_TS22_TC49) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-078069.json vs TD-TC-078060.json | `expected 1 vs 0` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-077849](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627) (BRDE_TS12_TC23) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-077849.json vs TD-TC-077848.json | `expected 1 vs 0` | Make the request of the decline test different (decline test card, amount or simulator rule). |

Scenarios: BRDE_TS22 (1), BRDE_TS12 (1)

**How to check by hand:** Open both test data files side by side: apart from the Sequence Number they are identical, but one test expects approval and the other a decline.

**Fix:** Give the decline test its own trigger (decline test card / amount / simulator rule) so its request differs.

### 4.2 C-03 - Test case marked 'ready' although its Terminal Identifier is a placeholder

**11 test cases, 11 findings** - kind: Test case issue to correct

Rules: `READY_WITH_PLACEHOLDER` (11).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

What is wrong (one example per kind of problem):

- `READY_WITH_PLACEHOLDER` (TC-077239) - The test case status is 'ready', but its Terminal Identifier is still a placeholder.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077239](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) (BRDE_TS1_TC17) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077469](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) (BRDE_TS4_TC15) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077255](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) (BRDE_TS1_TC74) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-078069](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) (BRDE_TS22_TC49) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077229](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) (BRDE_TS1_TC29) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-078058](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) (BRDE_TS22_TC4) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077413](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) (BRDE_TS4_TC9) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077480](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) (BRDE_TS4_TC77) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |

Scenarios: BRDE_TS1 (6), BRDE_TS4 (3), BRDE_TS22 (2)

**How to check by hand:** TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.

**Fix:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 4.3 C-04 - Expected Response Code not stated for a step, or several codes accepted

**1 test case, 1 finding** - kind: Test case issue to correct

Rules: `EXPECTED_RESPONSE_OPEN` (1).

ATL105:

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "5 Approved—Totals"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "6 Approved—Totals with electronic mail retrieval pending"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "B Approved—Totals with electronic mail retrieval pending and proprietary data pending"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "D Approved—Totals with proprietary data pending"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "N Approved—Totals with Proprietary Host Discount data pending"
- Element 83 Response Code (Chapter 13), PDF p.405 (printed 13-65): "W Approved—Totals with Proprietary Custom Receipt Text pending"

What is wrong (one example per kind of problem):

- `EXPECTED_RESPONSE_OPEN` (TC-077413) - The test case accepts any of 5 = Approved - Totals; 6 = Approved - Totals with electronic mail retrieval pending; B = Approved - Totals with electronic mail retrieval pending and proprietary data pending; D = Approved - Totals with proprietary data pending; N = Approved - Totals with Proprietary Host Discount data pending; W = Approved - Totals with Proprietary Custom Receipt Text pending for the purchase step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077413](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) (BRDE_TS4_TC9) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 1) | `any of 5, 6, B, D, N, W` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |

Scenarios: BRDE_TS4 (1)

**How to check by hand:** Open tx_legs[i].expect / steps[i].expected_response: no response_code value; or expected_response.response_code.acceptable_values lists several approval codes (e.g. 0 and 2) for a non-totals step. Approved-Totals codes 5/6/B/D/N/W for a Totals Request are accepted (Element 83, p.404-405).

**Fix:** State the expected Response Code (from BUYPASS or a reference run).

## 5. Citation issues (the ATL105 text quoted in the test cases)

### 5.1 Q-01 - Quote not found in ATL105 or missing (generator note or other text cited as ATL105)

**18 test cases, 20 findings** - kind: Citation issue

Rules: `QUOTE_NO_PAGE` (16), `QUOTE_MISSING` (4).

What is wrong (one example per kind of problem):

- `QUOTE_NO_PAGE` (TC-077239) - The spec_basis entry quotes "Card Discretionary Block Data: AN max 51" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077295) - The spec_basis entry quotes "Identifies the length of the segment in which it appears." with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077381) - The spec_basis entry quotes "Segment Type: N max 3, fixed 104" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077469) - The spec_basis entry quotes "Card Label: AN max 4" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077225) - The spec_basis entry quotes "Information Byte: AN max 1" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077230) - The spec_basis entry quotes "Terminal Identifier: AN max 22" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077255) - The spec_basis entry quotes "Valid Codes/Values: 1-99999999" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-078056) - The spec_basis entry quotes "maximal" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_MISSING` (TC-078069) - The spec_basis entry gives a rule id and a page but no ATL105 text, so the basis cannot be checked word for word.
- `QUOTE_NO_PAGE` (TC-077849) - The spec_basis entry quotes "Cryptogram Token Data: B64 Variable Length: 28/56 bytes max 56" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077239](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) (BRDE_TS1_TC17) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Card Discretionary Block Data: AN max 51` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Identifies the length of the segment in which it appears.` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077381](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) (BRDE_TS3_TC25) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Segment Type: N max 3, fixed 104` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077469](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) (BRDE_TS4_TC15) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Card Label: AN max 4` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Information Byte: AN max 1` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Terminal Identifier: AN max 22` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077255](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) (BRDE_TS1_TC74) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Valid Codes/Values: 1-99999999` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-078056](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) (BRDE_TS22_TC1) | - | `QUOTE_NO_PAGE` | test case spec_basis | `maximal` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-078069](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) (BRDE_TS22_TC49) | - | `QUOTE_MISSING` | test case spec_basis | `p.251` | Add the ATL105 sentence the rule is based on. |
| [TC-077849](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627) (BRDE_TS12_TC23) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Cryptogram Token Data: B64 Variable Leng…bytes max 56` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Card Discretionary Block Data: AN max 51` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077261](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) (BRDE_TS1_TC75) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Valid Codes/Values: 1-99999999` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| … | | 8 more in the sheet (Findings, filter on Q-01) | | | |

Scenarios: BRDE_TS1 (8), BRDE_TS22 (5), BRDE_TS4 (3), BRDE_TS3 (1), BRDE_TS12 (1), BRDE_TS2 (1), BRDE_TS6 (1)

**How to check by hand:** Search the quoted text on the cited page.

**Fix:** Quote the ATL105 sentence word for word.

## 6. Values awaiting client input or run-time values (BLOCKED, not defects)

### 6.1 I-01 - Terminal Identifier is a generated placeholder

**17 test cases, 17 findings** - kind: Awaiting client input / run-time value

Rules: `TERMINAL_PLACEHOLDER` (17).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Oregon OR 41"
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Mississippi MS 28"

What is wrong (one example per kind of problem):

- `TERMINAL_PLACEHOLDER` (TC-077239) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX41894821001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '41' = Oregon (OR), United States.
- `TERMINAL_PLACEHOLDER` (TC-077381) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'WI28888556001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '28' = Mississippi (MS), United States.
- `TERMINAL_PLACEHOLDER` (TC-077229) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'AAAAAAAAAAAAAAAAAAAAAAA' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) 'AA' = not a valid State Code.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077239](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) (BRDE_TS1_TC17) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077381](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) (BRDE_TS3_TC25) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `WI28888556001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077229](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) (BRDE_TS1_TC29) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `AAAAAAAAAAAAAAAAAAAAAAA` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077469](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) (BRDE_TS4_TC15) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077255](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) (BRDE_TS1_TC74) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077261](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) (BRDE_TS1_TC75) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-078056](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) (BRDE_TS22_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-078069](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) (BRDE_TS22_TC49) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| … | | 5 more in the sheet (Findings, filter on I-01) | | | |

Scenarios: BRDE_TS1 (8), BRDE_TS4 (3), BRDE_TS22 (3), BRDE_TS3 (1), BRDE_TS12 (1), BRDE_TS7 (1)

**How to check by hand:** merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.

**Fix:** Client / BUYPASS to supply the real Terminal Identifier.

### 6.2 I-02 - Value filled in at run time from the previous response (expected for follow-on steps)

**1 test case, 1 finding, 1 test data file** - kind: Awaiting client input / run-time value

Rules: `SEGMENT_LENGTH_RUNTIME` (1).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."

What is wrong (one example per kind of problem):

- `SEGMENT_LENGTH_RUNTIME` (TC-078056) - '086' depends on the run-time value(s) Encrypted PIN Block Data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078056](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) (BRDE_TS22_TC1) | 1 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-078056.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L7) | `086` | Recalculate the Segment Length after filling in the real values. |

Scenarios: BRDE_TS22 (1)

**How to check by hand:** The field holds text in angle brackets, e.g. <ApprovalNumber from step 1 response>.

**Fix:** The harness substitutes the value from the previous response and recalculates the Segment Length.

### 6.3 I-04 - EMV chip data is generated, not from a card / simulator

**9 test cases, 18 findings** - kind: Awaiting client input / run-time value

Rules: `EMV_CA_CHECKSUM_SAMPLE` (9), `EMV_PLACEHOLDER_CRYPTO` (9).

ATL105:

- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."

What is wrong (one example per kind of problem):

- `EMV_CA_CHECKSUM_SAMPLE` (TC-077239) - The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- `EMV_PLACEHOLDER_CRYPTO` (TC-077239) - ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077239](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) (BRDE_TS1_TC17) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-077239](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) (BRDE_TS1_TC17) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=9BDD6CCFC3C2EE60, 9F36=1AD3, 9F37=F…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=6F1D5B4CA7CC2180, 9F36=9DED, 9F37=B…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-077381](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) (BRDE_TS3_TC25) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-077381](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) (BRDE_TS3_TC25) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=07F42E09136159DF, 9F36=E5C6, 9F37=A…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-077225](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) (BRDE_TS1_TC7) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=10E40F248A144726, 9F36=474D, 9F37=3…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-077230](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) (BRDE_TS1_TC14) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=077BCB0AACD5A10C, 9F36=1A07, 9F37=2…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-077240](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) (BRDE_TS1_TC3) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=17C4BEB2294FCE91, 9F36=00E8, 9F37=4…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| … | | 6 more in the sheet (Findings, filter on I-04) | | | |

Scenarios: BRDE_TS1 (16), BRDE_TS3 (2)

**How to check by hand:** See the EMV finding text in the guide / sheet.

**Fix:** Use chip data captured from the client's test card or card simulator.

### 6.4 I-05 - Totals Request device versions are generated placeholders

**3 test cases, 3 findings, 3 test data files** - kind: Awaiting client input / run-time value

Rules: `TOTALS_VERSION_PLACEHOLDER` (3).

ATL105:

- Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters"
- Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters"
- Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters"

What is wrong (one example per kind of problem):

- `TOTALS_VERSION_PLACEHOLDER` (TC-077469) - ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077469](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) (BRDE_TS4_TC15) | 1 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-077469.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | Use the versions of the certified device that runs the test. |
| [TC-077413](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) (BRDE_TS4_TC9) | 1 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-077413.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json) | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | Use the versions of the certified device that runs the test. |
| [TC-077480](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) (BRDE_TS4_TC77) | 1 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-077480.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | Use the versions of the certified device that runs the test. |

Scenarios: BRDE_TS4 (3)

**How to check by hand:** Hardware/Software/Firmware Version hold random strings.

**Fix:** Client to supply the versions of the certified device.

### 6.5 I-06 - Optional segment content is synthesized - real client data needed before an approval can be expected

**1 test case, 1 finding, 1 test data file** - kind: Awaiting client input / run-time value

Rules: `X_SYNTHESIZED_CONTENT` (1).

ATL105:

- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data."

What is wrong (one example per kind of problem):

- `X_SYNTHESIZED_CONTENT` (TC-078058) - The test case generated the Segment 104 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: a purchase (commercial) card and its purchase-card data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078058](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) (BRDE_TS22_TC4) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-078058.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json) | `format_synthesis` | Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then. |

Scenarios: BRDE_TS22 (1)

**How to check by hand:** In test_cases.jsonl the segment fields have method format_synthesis / spec_tlv_composite.

**Fix:** Client to supply the data listed in the finding; keep the test case out of 'ready' until then.

### 6.6 I-08 - No test data generated for a host test case

**1 test case, 1 finding** - kind: Awaiting client input / run-time value

Rules: `X_NO_TEST_DATA` (1).

What is wrong (one example per kind of problem):

- `X_NO_TEST_DATA` (TC-077358) - The test case sends a message to BUYPASS (EMV Financial Transaction Request) but has no test data file (status blocked_no_test_data).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077358](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) (BRDE_TS2_TC65) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |

Scenarios: BRDE_TS2 (1)

**How to check by hand:** test_data_file is empty in test_cases.jsonl; status blocked_no_test_data.

**Fix:** Generate the test data when the missing input is available.

### 6.7 I-09 - Segment 100 value awaited from the client (<AWAITING:...>)

**2 test cases, 2 findings, 2 test data files** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_VALUE` (2).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_AWAITING_VALUE` (TC-077295) - The field holds '<AWAITING:EncryptedPINBlockData>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077295](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) (BRDE_TS1_TC93) | 1 | `X_AWAITING_VALUE` | [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L16) | `<AWAITING:EncryptedPINBlockData>` | Client to supply the value (for Account Number: a MasterCard test card). |
| [TC-078056](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) (BRDE_TS22_TC1) | 1 | `X_AWAITING_VALUE` | [TD-TC-078056.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L16) | `<AWAITING:EncryptedPINBlockData>` | Client to supply the value (for Account Number: a MasterCard test card). |

Scenarios: BRDE_TS1 (1), BRDE_TS22 (1)

**How to check by hand:** The field holds <AWAITING:...>; the test case field has method awaiting_client_value.

**Fix:** Client to supply the value (for Account Number: a MasterCard test card).

## 7. Points to confirm with BUYPASS / SME

### 7.1 C-07 - 'Different terminal' negative uses a generated terminal that is not set up at BUYPASS

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_RANDOM_TERMINAL` (1).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `X_NEG_RANDOM_TERMINAL` (TC-077229) - Step 1 uses the generated Terminal Identifier 'AAAAAAAAAAAAAAAAAAAAAAA' (method deliberate_violation_length). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077229](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) (BRDE_TS1_TC29) | 1 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-077229.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) | `AAAAAAAAAAAAAAAAAAAAAAA` | Use a second real terminal of the same merchant. |

Scenarios: BRDE_TS1 (1)

**How to check by hand:** Read the follow-on step's Terminal Identifier: method deliberate_violation_missing_carry, a generated value.

**Fix:** Use a second real terminal of the same merchant so the decline proves the same-device rule.

### 7.2 C-08 - Same request as another test case (difference only on the device / response side)

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_SAME_REQUEST` (1).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST` (TC-077553) - Apart from the Sequence Number the message is identical to TC-077550 (all segments compared). TC-077550 expects 8/9/G/J; this test expects 1. What makes the two tests different is not in the message sent to BUYPASS.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077553](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L331) (BRDE_TS6_TC42) | 1 | `X_SAME_REQUEST` | TD-TC-077553.json vs TD-TC-077550.json | `expected 1 vs 8/9/G/J` | Check the difference on the device / in the response; optionally use another card. |

Scenarios: BRDE_TS6 (1)

**How to check by hand:** Compare both test data files: identical apart from the Sequence Number.

**Fix:** Check the difference on the device or in the response; optionally use another card.

### 7.3 S-01 - Expiry format for stored credentials (entry mode 10) not stated

**1 test case, 1 finding** - kind: To confirm with BUYPASS / SME

Rules: `COF_EXPIRY_FORMAT` (1).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."

What is wrong (one example per kind of problem):

- `COF_EXPIRY_FORMAT` (TC-077628) - The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077628](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406) (BRDE_TS7_TC19) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1229` | Confirm the expiry format for entry mode 10 with BUYPASS. |

Scenarios: BRDE_TS7 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.4 S-05 - Other points to confirm

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `TOTALS_ZERO_BUCKET` (1).

ATL105:

- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Valid Codes/Values: 00001–99999"
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."

What is wrong (one example per kind of problem):

- `TOTALS_ZERO_BUCKET` (TC-077480) - Element 16 allows 00001-99999, and the category section is sent only when data occurs for a card type. A net count of 00000 suggests the bucket should not be sent at all.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077480](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) (BRDE_TS4_TC77) | 1 | `TOTALS_ZERO_BUCKET` | [TD-TC-077480.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L20) | `00000` | Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent. |

Scenarios: BRDE_TS4 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS / SME.

## 8. Negative tests - intended violations (correct)

5 negative test cases break their rule as designed (7 findings). They are not defects; check that each test breaks only that rule.

| Rule | Findings | Example |
|---|---:|---|
| `TOTALS_SEGMENT_LENGTH` | 2 | TC-077469 step 1: Segment 105 length is counted with the wrong separators and label width - Card Label one byte over its documented maximum length (4) |
| `FIELD_12` | 1 | TC-077239 step 1: Card Discretionary Block Data is not valid - Card Discretionary Block Data one byte over its documented maximum length (51) |
| `FIELD_84` | 1 | TC-077295 step 1: Segment Length is not valid - Segment 100 Segment Length one more than its rendered bytes |
| `TOTALS_CARD_LABEL_WIDTH` | 1 | TC-077469 step 1: Card Label (Element 13) is not 4 characters - Card Label one byte over its documented maximum length (4) |
| `FIELD_105_13` | 1 | TC-077469 step 1: Card Label (Totals) is not valid - Card Label one byte over its documented maximum length (4) |
| `FIELD_102` | 1 | TC-077229 step 1: Terminal Identifier is not valid - Terminal Identifier one byte over its documented maximum length (22) |

## 9. AI validator rules that did not apply to this run (value checked - correct)

| Rule | Findings | Test cases | Why the value is correct |
|---|---:|---:|---|

Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator did not read segments 101-153; section 2 point 2 now checks them).

Run-wide checks (all 1,030 test cases of the run were validated so that these checks see every test case): 98 single-step test cases send the same request as an earlier test case (all segments compared); 462 new transactions checked for a unique Sequence Number per terminal, 1 duplicate(s).

## 10. Result per scenario

| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |
|---|---|---|---:|---:|---:|---:|---:|---:|---|
| BRDE_TS1 | Purchase/Capture – EMV Contact Chip | None | 8 | 8 | 0 | 0 | 0 | 0 | D-15 (7), D-13 (6), D-09 (2) |
| BRDE_TS2 | Purchase/Capture – EMV Contact Chip | None | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BRDE_TS3 | Purchase/Capture – EMV Contact Chip | None | 1 | 1 | 0 | 0 | 0 | 0 | D-13 (1), D-09 (1) |
| BRDE_TS4 |  – All | None | 3 | 3 | 0 | 0 | 0 | 0 | D-09 (4), D-15 (1) |
| BRDE_TS6 |  – All | None | 1 | 0 | 0 | 1 | 0 | 0 | - |
| BRDE_TS7 | Purchase/Capture – Credential on File | None | 1 | 1 | 0 | 0 | 0 | 0 | D-09 (2) |
| BRDE_TS12 | Purchase/Capture – Contactless Mobile Commerce Device | None | 1 | 1 | 0 | 0 | 0 | 0 | D-13 (1), C-01 (1) |
| BRDE_TS22 | Purchase/Capture – Contactless Magnetic Stripe | None | 3 | 3 | 0 | 0 | 0 | 0 | D-13 (3), D-02 (1), C-01 (1) |

## 11. ATL105 references used

| Reference | Section | PDF p. | Printed | Text (verified on the page) |
|---|---|---:|---|---|
| APPD-28 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Mississippi MS 28 |
| APPD-41 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Oregon OR 41 |
| APPE-020 | Appendix E. Valid Card Type Codes | 521 | Appendix E-4 | 020 Credit Credit |
| APPG-0 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion |
| APPI-005 | Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005) | 555 | Appendix I-6 | Point-of-Service Entry Mode Information |
| APPI-006 | Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006) | 555 | Appendix I-6 | Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)] |
| APPI-030 | Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030) | 568 | Appendix I-19 | Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions. |
| APPI-031 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | Terminal Capability values must be populated for all transactions |
| APPI-031-12 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | 12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities |
| APPI-032-08 | Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08 | 575 | Appendix I-26 | It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.). |
| APPI-049-02 | Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02 | 589 | Appendix I-40 | This field is required for all subsequent Credential on File transactions. |
| APPJ-05 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 05 Integrated circuit card (ICC) read – CVV data reliable (used for EMV chip read) |
| APPJ-10 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 10 Credential on file |
| APPJ-82 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 82 Contactless mobile commerce device |
| APPJ-91 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 91 Full contactless magnetic stripe read with track data |
| APPJ-PIN1 | Appendix J. Valid Point-of-Service Entry Mode Codes | 639 | Appendix J-2 | 1 PIN entry capability (including both EMV Contact and Contactless) |
| APPQ | Appendix Q. Valid National Point-of-Service Condition Codes | 690 | Appendix Q-1 | This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030 The National Point-of-Service Condition Codes data element contains the following elements: • Terminal Class (Position Nos. 1–3) • Presentation Type (Position Nos. 4–7) • Security Condition (Position No. 8) • Terminal Type (Position Nos. 9–10) |
| APPQ-010-04 | Appendix Q. Valid National Point-of-Service Condition Codes | 693 | Appendix Q-4 | 010 04 Attended on premise Card acceptor-operated ECR |
| APPQ-CLASS | Appendix Q. Valid National Point-of-Service Condition Codes | 691 | Appendix Q-2 | Position No. 1 Value Description 0 Attended 1 Unattended 2–7 Reserved for national use 8–9 Reserved for private use Position No. 2 Value Description 0 Customer-operated 1 Card acceptor-operated 2 Administrative 3–7 Reserved for national use 8–9 Reserved for private use Position No. 3 Value Description 0 On premise 1 Off premise |
| APPQ-PRES | Appendix Q. Valid National Point-of-Service Condition Codes | 696 | Appendix Q-7 | Position Nos. 4–7 Presentation Type Description 0000 Customer and card present 0100 Customer present; card not present 1000 Customer not present; card present 1100 Customer not present; card not present 2100 Mail and/or telephone order 8800 Preauthorized purchase |
| APPQ-SEC | Appendix Q. Valid National Point-of-Service Condition Codes | 696 | Appendix Q-7 | Security Condition Value Description 0 No security concern 1 Suspected fraud 2 Identification verified 3 Electronic commerce transaction with digital signature |
| APPQ-TYPE | Appendix Q. Valid National Point-of-Service Condition Codes | 692 | Appendix Q-3 | Value Description 00 Administrative terminal 01 Point-of-service terminal 02 ATM 03 Home terminal 04 ECR 05 Dial terminal 06 Traveler's check machine 07 Fuel machine |
| APPR-9C | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | 9C\01\00\ Transaction type |
| APPR-9F02 | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | 9F\02\06\00\00\00\00\09\00\ Amount, authorized (numeric) |
| APPR-PKT | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | 01\06 EMV/Chip Data length (BCD packed) |
| APPT-1 | Appendix T. EMV Additional Information Data Layouts - EMV Table Data | 701 | Appendix T-1 | EMVYES This code indicates that the device is able to receive EMV Table Data. |
| APPT-1-LEN | Appendix T. EMV Additional Information Data Layouts - EMV Table Data | 701 | Appendix T-1 | Maximum Length n6 Len.: 6 |
| APPT-1-NOT | Appendix T. EMV Additional Information Data Layouts - EMV Table Data | 701 | Appendix T-1 | EMVNOT This code indicates that the device is unable to receive EMV Table Data. |
| EL102 | Element 102 Terminal Identifier (Chapter 13) | 417 | 13-77 | The Terminal Identifier must be assigned by and set up at BUYPASS. |
| EL102-STATE | Element 102 Terminal Identifier (Chapter 13) | 417 | 13-77 | State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). |
| EL105 | Element 105 Totals Date (Chapter 13) | 419 | 13-79 | Fixed length of six digits |
| EL111 | Element 111 Variable Information Indicator (Chapter 13) | 422 | 13-82 | Fixed length of 3 alphanumeric characters |
| EL118-EMV | Element 118 EMV Additional Information (Chapter 13) | 439 | 13-99 | When EMV Additional Information Indicator has a value of 001, EMV table data is included here. |
| EL12 | Element 12 Card Discretionary Block Data (Chapter 13) | 372 | 13-32 | For a card whose number has been manually entered, the expiration date is in MMYY format |
| EL12-MAX | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2. |
| EL12-TRACK2 | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character. |
| EL121 | Element 121 Partial Approval Indicator (Chapter 13) | 440 | 13-100 | This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions. |
| EL13 | Element 13 Card Label (Chapter 13) | 372 | 13-32 | Fixed length of four alphanumeric characters; left-aligned and space-filled |
| EL15 | Element 15 Card Type Total Amount (Chapter 13) | 374 | 13-34 | Fixed length of eight digits with two assumed decimal places |
| EL16 | Element 16 Card Type Total Count (Chapter 13) | 375 | 13-35 | Fixed length of five digits |
| EL16-VAL | Element 16 Card Type Total Count (Chapter 13) | 375 | 13-35 | Valid Codes/Values: 00001–99999 |
| EL17 | Element 17 Cash Amount (Chapter 13) | 375 | 13-35 | Identifies the cash back amount of a transaction. |
| EL187 | Element 187 CA Public Key File Checksum (Chapter 13) | 476 | 13-136 | Fixed length of 25 bytes |
| EL187-REQ | Element 187 CA Public Key File Checksum (Chapter 13) | 476 | 13-136 | Required in the EMV Financial Transaction Request. |
| EL187-USE | Element 187 CA Public Key File Checksum (Chapter 13) | 477 | 13-137 | Once received, the checksum must be used for all subsequent EMV transaction requests from this device. |
| EL188 | Element 188 EMV Card Sequence Number (Chapter 13) | 477 | 13-137 | Fixed length of three bytes, right-aligned and padded with zeroes |
| EL188-VAL | Element 188 EMV Card Sequence Number (Chapter 13) | 477 | 13-137 | Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card |
| EL189 | Element 189 EMV Chip Data Length (Chapter 13) | 477 | 13-137 | Identifies the length of the following field, EMV Chip Data, in an EMV transaction request and response. |
| EL190-AID | Element 190 EMV Chip Data (Chapter 13) | 478 | 13-138 | the Application ID (AID) TLV (tag 9F06) or Dedicated File Name (tag 84) must be included in Data Element No. 190 (EMV Chip Data). |
| EL190-BIN | Element 190 EMV Chip Data (Chapter 13) | 478 | 13-138 | All fields after the table length are in binary format. |
| EL190-EXACT | Element 190 EMV Chip Data (Chapter 13) | 478 | 13-138 | All data elements must contain the exact values that were present on the card to terminal interface. |
| EL190-NO5A57 | Element 190 EMV Chip Data (Chapter 13) | 478 | 13-138 | Tag ‘5A’ (PAN) and Tag ‘57’ (Track 2 equivalent data) must not be included in Data Element 190 — EMV Chip Data. |
| EL190-TLV | Element 190 EMV Chip Data (Chapter 13) | 478 | 13-138 | EMV data elements include a series of TLV (Tag/Length/Value) combinations which are required for processing EMV transactions. |
| EL191 | Element 191 EMV Additional Information Indicator (Chapter 13) | 479 | 13-139 | 001 EMV table data |
| EL192 | Element 192 EMV Additional Information Length (Chapter 13) | 480 | 13-140 | Valid Codes/Values: 001–985 |
| EL2 | Element 2 Account Number (Chapter 13) | 364 | 13-24 | a variable length (up to 24 alphanumeric bytes) Account Number |
| EL20 | Element 20 Currency Code (Chapter 13) | 376 | 13-36 | Fixed length of three digits |
| EL33-FMT | Element 33 Encrypted PIN Block Data (Chapter 13) | 382 | 13-42 | For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block. |
| EL39 | Element 39 Firmware Version (Chapter 13) | 384 | 13-44 | Fixed length of eight alphanumeric characters |
| EL41 | Element 41 Fuel Purchase Amount (Chapter 13) | 385 | 13-45 | Representation: Variable length of up to eight digits with two assumed decimal places |
| EL41-USE | Element 41 Fuel Purchase Amount (Chapter 13) | 385 | 13-45 | Included on any transactions with fuel sales. |
| EL42 | Element 42 Grand Total (Chapter 13) | 386 | 13-46 | Fixed length of eight digits with two assumed decimal places |
| EL43 | Element 43 Hardware Version (Chapter 13) | 386 | 13-46 | Fixed length of four or eight alphanumeric characters |
| EL44 | Element 44 Information Byte (Chapter 13) | 386 | 13-46 | 0 Single-message transmission |
| EL5-PRE | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization. |
| EL5-REV | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Required on reversals for matching information to the original purchase. |
| EL55 | Element 55 Message Format Version Identifier (Chapter 13) | 391 | 13-51 | Fixed length of six alphanumeric characters |
| EL58 | Element 58 Nonfuel Amount (Chapter 13) | 393 | 13-53 | A maximum length of 7 bytes is allowed for all other card types. |
| EL58-PURPOSE | Element 58 Nonfuel Amount (Chapter 13) | 393 | 13-53 | Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount. |
| EL63 | Element 63 Number of Segments (Chapter 13) | 395 | 13-55 | Variable length of up to two digits |
| EL78 | Element 78 Prompt Code (Chapter 13) | 400 | 13-60 | this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters). |
| EL79 | Element 79 Pump/Lane Number (Chapter 13) | 401 | 13-61 | Representation: Variable length of up to two digits Purpose: Identifies the pump or lane number at the point-of-sale location. |
| EL79-USE | Element 79 Pump/Lane Number (Chapter 13) | 402 | 13-62 | Required on all fuel transactions. |
| EL83-0 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 0 Approved—Purchase/Capture |
| EL83-1 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 1 Approved—Communications Test Declined—All other transactions |
| EL83-2 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 2 Approved—Authorization only |
| EL83-5 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 5 Approved—Totals |
| EL83-6 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 6 Approved—Totals with electronic mail retrieval pending |
| EL83-B | Element 83 Response Code (Chapter 13) | 404 | 13-64 | B Approved—Totals with electronic mail retrieval pending and proprietary data pending |
| EL83-D | Element 83 Response Code (Chapter 13) | 404 | 13-64 | D Approved—Totals with proprietary data pending |
| EL83-N | Element 83 Response Code (Chapter 13) | 404 | 13-64 | N Approved—Totals with Proprietary Host Discount data pending |
| EL83-W | Element 83 Response Code (Chapter 13) | 405 | 13-65 | W Approved—Totals with Proprietary Custom Receipt Text pending |
| EL84 | Element 84 Segment Length (Chapter 13) | 406 | 13-66 | This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| EL86-FMT | Element 86 Sequence Number (Chapter 13) | 409 | 13-69 | Valid Codes/Values: 000001–999999 |
| EL87-VAL | Element 87 Service Level (Chapter 13) | 410 | 13-70 | F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present. |
| EL96 | Element 96 Software Version (Chapter 13) | 414 | 13-74 | Fixed length of eight alphanumeric characters |
| EL99 | Element 99 Tax Amount (Chapter 13) | 415 | 13-75 | Identifies the transaction tax amount in Data Segment No. 100, Standard Message Data Segment. |
| S10.1.1.3 | Section 10.1.1.3 Manual Entry | 81 | 10-2 | If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY). |
| S10.1.8.2 | Section 10.1.8.2 Track Support Requirements | 94 | 10-15 | The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). |
| S10.13.2.4.2-MC | Section 10.13.2.4.2 Stored Credential Use Cases | 144 | 10-65 | Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type. |
| S11.1-104 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data. |
| S11.1-DS1 | Section 11.1 Financial Transactions (Data Section No. 1) | 167 | 11-4 | All Financial Transaction Requests contain Element Nos. 55 and 63. |
| S11.4-TOTALS | Section 11.4.1.1 Totals Request | 178 | 11-15 | Note: Totals Request messages do not contain Data Section No. 2 [Standard Message Data Segment (Data Segment No. 100)]. |
| S11.8-EMV | Section 11.8.1 EMV Financial Transaction Request | 205 | 11-42 | The following table illustrates the layout of an EMV Financial Transaction Request: |
| S12.1-FS | Section 12.1 Standard Message Data Segment | 223 | 12-6 | When a field is not populated, still send the Field Separator. |
| S12.1-LEN | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.1-ROW16 | Section 12.1 Standard Message Data Segment | 225 | 12-8 | Identifies the local date and local time of the preauthorized transaction. |
| S12.1-TYPE | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Fixed value: 100 |
| S12.10-FS | Section 12.10 Variable Information Data Segment | 246 | 12-29 | A Field Separator follows the final Variable Information Section. |
| S12.10-LEN | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Identifies the length of Variable Information (Element No. 113). |
| S12.10-TYPE | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Fixed value: 111 |
| S12.20-ALWAYS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | It always appears in Data Section No. 3 of an EMV Financial Transaction Request. |
| S12.20-FS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | A Field Separator follows the final EMV Additional Information Section. |
| S12.20-LEN | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.20-TYPE | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Fixed value: 130 |
| S12.6-CAT | Section 12.6 Totals Data Segment | 237 | 12-20 | Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type. |
| S12.6-FS | Section 12.6 Totals Data Segment | 235 | 12-18 | Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14. |
| S12.6-NOFS | Section 12.6 Totals Data Segment | 235 | 12-18 | Field Nos. 15–17 are not separated by Field Separators. |
| S12.6-PROMPT | Section 12.6 Totals Data Segment | 235 | 12-18 | Identifies the format type for the requested totals. Fixed value: 990 |
| S12.6-TYPE | Section 12.6 Totals Data Segment | 235 | 12-18 | Identifies the type of data segment being formatted. Fixed value: 105 |
| S2.3.1-DEVICE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. |
| S2.3.1-MATCH | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response. |
| S9.2-GT | Section 9.2 Local Totals | 72 | 9-8 | The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only |
| S9.2-PUR | Section 9.2 Local Totals | 72 | 9-8 | A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total. |
| S9.2-RET | Section 9.2 Local Totals | 72 | 9-8 | A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total. |
| S9.2-REV | Section 9.2 Local Totals | 72 | 9-8 | A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total. |

Regenerate: `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11\data_element_validation" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output\Data_Element_Validation" --only TC-077239,TC-077295,TC-077381,TC-077469,TC-077225,TC-077230,TC-077240,TC-077255,TC-077261,TC-078056,TC-078069,TC-077849,TC-077229,TC-078058,TC-077358,TC-077413,TC-077480,TC-077553,TC-077628 --guides TC-077239,TC-077295 --sheet Data_Element_Validation_Defect_Sheet.md --title "Run set 20-11, data element validation part: defect sheet for the BA review" --scope "Data element validation part of the run set `Dhan_Singh_run_set_20-11`: negative and boundary tests of single data elements across the request types. The sheet shows every defect the validator finds in this part, with all affected test cases, and the actual and expected test data of 19 representative test cases." --groups 20-11-parts --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_de.md`.

