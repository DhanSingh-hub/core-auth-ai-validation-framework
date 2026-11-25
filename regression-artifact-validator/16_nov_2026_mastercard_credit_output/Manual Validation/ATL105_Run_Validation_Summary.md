# ATL105 validation of the run - all 1,861 test cases and 2,147 test data files

**Run:** [mastercard_credit_new - Copy (2)](../mastercard_credit_new%20-%20Copy%20%282%29/) (`16_nov_2026_mastercard_credit_output`), build `buypass-atl105-mastercard-credit-generated-cdf683cfd7`  
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). The PDF attached to the run (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the 2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  
**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.

Manual step-by-step guides for 5 test cases: [TC-020803](TC-020803-Manual-Validation.md), [TC-020804](TC-020804-Manual-Validation.md), [TC-020805](TC-020805-Manual-Validation.md), [TC-020806](TC-020806-Manual-Validation.md), [TC-022362](TC-022362-Manual-Validation.md). Every finding of every test case is in the Excel sheet [ATL105_Run_Validation_All_Test_Cases.xlsx](ATL105_Run_Validation_All_Test_Cases.xlsx).

## 1. Result at a glance

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 154 | At least one test data or test case defect that must be fixed |
| **BLOCKED** | 1,570 | No defect, but a client value or a run-time value is still missing |
| **REVIEW** | 0 | No defect; a citation must be corrected or a point confirmed with BUYPASS |
| **PASS** | 0 | Nothing found |
| **DEVICE** | 137 | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |
| Total | 1,861 | 1,710 send a message to BUYPASS (2,147 test data files); 151 are device-only or have no test data |

Findings by kind (a test case can have several):

| Kind | Findings | Test cases | Effect on the result |
|---|---:|---:|---|
| Test data defect | 144 | 100 | FAIL |
| Test case defect | 71 | 71 | FAIL |
| Test case issue to correct | 897 | 787 | REVIEW |
| Citation issue | 1,322 | 976 | REVIEW |
| Awaiting client input / run-time value | 4,412 | 1,724 | BLOCKED |
| To confirm with BUYPASS / SME | 647 | 559 | REVIEW |
| Intended violation of a negative test (correct) | 157 | 150 | none |
| AI validator rule not applicable - value checked and correct | 38 | 25 | none |
| Information | 643 | 582 | none |

Data segments in the test data: 100 (2,080 files), 101 (1 files), 102 (417 files), 104 (8 files), 105 (67 files), 108 (26 files), 111 (2,080 files), 123 (6 files), 130 (550 files), 145 (9 files), 153 (26 files).

## 2. How every test case and test data file was checked

1. **Trained AI validator** (`scripts/validate-td-against-atl105-spec.py`, the rules built in the earlier MasterCard, Interlink and Maestro reviews): Data Section 1, all 17 fields of Segment 100, Segment 111 with Tables 005/031/030 and the Credential on File tables, Segment 130 (EMV) with its TLV chip data, Segment 105 (Totals), links between steps (Sequence Number, card, terminal, entry mode), expected Response Codes, and every ATL105 quote in the test case (`spec_basis`) against the page it cites.
2. **Extended checks for this run** (`scripts/validate-run-and-build-manual-validation.py`): the other data segments (101, 102, 104, 108, 123, 145, 153 - Segment Type, Segment Length with each segment's Field Separator rule, Chapter 13 format of every field, TLV structure), every Variable Information table ID, values awaited from the client, each test case's own `rule_checks`, test data vs test case value fidelity for all segments, identical requests across all test cases, Sequence Number uniqueness per terminal, Time-out Reversal flows, negative-test premises and conditional fields.
3. **Classification**: every finding was sorted into the kinds in section 1. Findings of negative tests that are the rule the test deliberately breaks are listed as *intended* (correct). Where the trained rule did not fit a new flow of this run, the value was checked against ATL105 and the finding listed as *AI rule not applicable* (section 9) with the reason.
4. Every ATL105 text quoted in this report (listed in section 11) was found word for word on the stated page when this report was generated.

## 3. Defects in the test data (must be fixed)

### 3.1 D-01 - Totals Request: card bucket count/amount does not match the transactions of the flow

**28 test cases, 28 findings, 28 test data files** - kind: Test data defect

Rules: `TOTALS_FLOW_MISMATCH` (28).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total."
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total."
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."
- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Fixed length of five digits"
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places"
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."

What is wrong (one example per kind of problem):

- `TOTALS_FLOW_MISMATCH` (TC-023513) - Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 cancellation $15.00: cancellation - subtracts from the AO bucket. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-023514) - Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-023961) - Section 9.2 counts each approved transaction into its card bucket: step 1 moto auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 cancellation $15.00: cancellation - subtracts from the AO bucket. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-023962) - Section 9.2 counts each approved transaction into its card bucket: step 1 moto auth only $15.00: Authorization Only - AO bucket, not in the grand total. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-023988) - Section 9.2 counts each approved transaction into its card bucket: step 1 return $15.00: count +1, amount -$15.00; step 2 void return $15.00: count -1, amount +$15.00. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023513](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L324) (BR1_TS11_TC77) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023513.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023513.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023514](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L325) (BR1_TS11_TC78) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023514.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023514.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023961](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1262) (BR1_TS19_TC7) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023961.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023961.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023962](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1263) (BR1_TS19_TC8) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023962.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023962.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023988](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1296) (BR1_TS21_TC5) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023988.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023988.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023559](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L438) (BR1_TS12_TC77) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023559.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023559.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023560](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L439) (BR1_TS12_TC78) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023560.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023560.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023609](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L558) (BR1_TS13_TC80) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023609.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023609.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023610](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L559) (BR1_TS13_TC81) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023610.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023610.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023739](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L755) (BR1_TS14_TC72) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023739.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023739.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023740](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L756) (BR1_TS14_TC73) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023740.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023740.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-023783](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L867) (BR1_TS15_TC75) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-023783.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023783.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| … | | 16 more in the sheet (Findings, filter on D-01) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS19 (2), BR1_TS20 (2), BR1_TS21 (1), BR1_TS22 (1), BR1_TS23 (1), BR1_TS24 (1), BR1_TS25 (1) …

**How to check by hand:** Open the Totals Request file, read CardLabel / CardTypeTotalCount / CardTypeTotalAmount / GrandTotal and recompute them from the earlier steps of the same test case (Section 9.2).

**Fix:** Compute the totals from the approved steps of the flow; an Authorization Only goes to the AO bucket and is left out of the grand total; a bucket with net count 0 is not sent; count and amount must agree.

### 3.2 D-02 - Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply

**18 test cases, 34 findings, 18 test data files** - kind: Test data defect

Rules: `X_PIN_BLOCK_FORMAT` (18), `FIELD_5` (8), `X_OPTIONAL_FIELD_CONDITION` (8).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- Section 12.1 Standard Message Data Segment, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction."

What is wrong (one example per kind of problem):

- `X_PIN_BLOCK_FORMAT` (TC-020974) - Element 33 carries a 16- to 20-byte Key Serial Number plus a 16-byte PIN block (32-36 characters); 'PIN' has 3 characters.
- `FIELD_5` (TC-020989) - 'J38FQA' is an Approval Number on a new Authorization Only (Transaction Type 3). Element 5 is required on reversals to match the original, and an Approval Number in a request makes it preauthorized, so BUYPASS would not go out for authorization.
- `X_OPTIONAL_FIELD_CONDITION` (TC-020989) - Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant.
- `X_OPTIONAL_FIELD_CONDITION` (TC-021592) - Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a CARD_NOT_PRESENT (non-petroleum) merchant; Fuel Purchase Amount '1' at a CARD_NOT_PRESENT (non-petroleum) merchant.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020974](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L298) (BR1_TS11_TC51) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-020974.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020974.json#L16) | `PIN` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-020989](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L313) (BR1_TS11_TC66) | 1 | `FIELD_5` | [TD-TC-020989.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020989.json#L23) | `J38FQA` | (empty) - a new Authorization Only carries no Approval Number |
| [TC-020989](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L313) (BR1_TS11_TC66) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-020989.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020989.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-021592](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1091) (BR1_TS17_TC70) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-021592.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021592.json) | `Local Date and Local Time '0615251430' o…-petroleum) ` | Send conditional fields only when their condition holds. |
| [TC-020989](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L313) (BR1_TS11_TC66) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-020989.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020989.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-021060](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L412) (BR1_TS12_TC51) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-021060.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021060.json#L16) | `PIN` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-021075](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L427) (BR1_TS12_TC66) | 1 | `FIELD_5` | [TD-TC-021075.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021075.json#L23) | `6FH3NZ` | (empty) - a new Authorization Only carries no Approval Number |
| [TC-021075](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L427) (BR1_TS12_TC66) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-021075.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021075.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-021075](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L427) (BR1_TS12_TC66) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-021075.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021075.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-021148](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L528) (BR1_TS13_TC50) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-021148.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021148.json#L16) | `PIN` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-021163](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC65) | 1 | `FIELD_5` | [TD-TC-021163.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021163.json#L23) | `C9HTU3` | (empty) - a new Authorization Only carries no Approval Number |
| [TC-021163](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC65) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-021163.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021163.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| … | | 22 more in the sheet (Findings, filter on D-02) | | | |

Scenarios: BR1_TS11 (4), BR1_TS12 (4), BR1_TS13 (4), BR1_TS14 (4), BR1_TS15 (4), BR1_TS16 (4), BR1_TS17 (4), BR1_TS18 (4), BR1_TS58 (1), BR1_TS77 (1)

**How to check by hand:** Open the test data file and read ApprovalNumber, EncryptedPINBlockData, LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount, CashAmount; compare with the ATL105 rule of each field.

**Fix:** Send only the conditional fields whose condition holds: no Approval Number / Local Date and Time on a new authorization, a real DUKPT KSN + PIN block (32-36 characters) or nothing in Element 33, no fuel or pump data outside petroleum.

### 3.3 D-03 - Segment 145 (Enhanced Fleet Request) is not valid: length indicator over 3 digits, segment over its maximum and data not in <tag><len><data> format

**9 test cases, 27 findings, 9 test data files** - kind: Test data defect

Rules: `X_SEG_LENGTH_FIELD` (9), `X_SEG_MAX` (9), `X_TLV_FORMAT` (9).

ATL105:

- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "All or some of the Enhanced Fleet sub-segment data is specified below. Data is in <tag><len><data> format."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings."

What is wrong (one example per kind of problem):

- `X_SEG_LENGTH_FIELD` (TC-023424) - Field 2 (Element 84) of Segment 145 is 3 characters long (Len. 3), so '1008' cannot be sent; the segment itself is 1009 characters long.
- `X_SEG_MAX` (TC-023424) - The segment is 1009 characters long; ATL105 allows at most 999.
- `X_TLV_FORMAT` (TC-023424) - ATL105 sends this field as sub-segments <3-digit tag><3-digit length><data>, but at position 1 'SE1Q7F' is not a 3-digit tag followed by a 3-digit length.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L154) (BR1_TS7_TC9) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-023424.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023424.json#L65) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-023424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L154) (BR1_TS7_TC9) | 1 | `X_SEG_MAX` | [TD-TC-023424.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023424.json) | `1009` | Keep the segment within 999 characters. |
| [TC-023424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L154) (BR1_TS7_TC9) | 1 | `X_TLV_FORMAT` | [TD-TC-023424.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023424.json#L66) | `SE1Q7F4VS4SQT9AELSGV660DQ5R0S5WYFGG5JLCA…8M5N6CTSZAF…` | Build the field from the sub-segments defined in the segment's layout tables. |
| [TC-020992](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L316) (BR1_TS11_TC69) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-020992.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020992.json#L61) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-020992](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L316) (BR1_TS11_TC69) | 1 | `X_SEG_MAX` | [TD-TC-020992.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020992.json) | `1009` | Keep the segment within 999 characters. |
| [TC-020992](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L316) (BR1_TS11_TC69) | 1 | `X_TLV_FORMAT` | [TD-TC-020992.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020992.json#L62) | `NL8RTITJBCKG0KB8XVQO3JO28DZBTEE5NW6KEU2A…RFNINH8R867…` | Build the field from the sub-segments defined in the segment's layout tables. |
| [TC-021078](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L430) (BR1_TS12_TC69) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-021078.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021078.json#L61) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-021078](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L430) (BR1_TS12_TC69) | 1 | `X_SEG_MAX` | [TD-TC-021078.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021078.json) | `1009` | Keep the segment within 999 characters. |
| [TC-021078](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L430) (BR1_TS12_TC69) | 1 | `X_TLV_FORMAT` | [TD-TC-021078.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021078.json#L62) | `D36A62ZIVA804FSON6CA28JOLBB5FJUFPEP9CCJ7…0886LRE0A37…` | Build the field from the sub-segments defined in the segment's layout tables. |
| [TC-021166](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L546) (BR1_TS13_TC68) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-021166.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021166.json#L45) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-021166](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L546) (BR1_TS13_TC68) | 1 | `X_SEG_MAX` | [TD-TC-021166.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021166.json) | `1009` | Keep the segment within 999 characters. |
| [TC-021166](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L546) (BR1_TS13_TC68) | 1 | `X_TLV_FORMAT` | [TD-TC-021166.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021166.json#L46) | `O4IEA1ZAV3EV706IN3O9OK46NX1I6SSP95PFCB28…RB0FK87M7MT…` | Build the field from the sub-segments defined in the segment's layout tables. |
| … | | 15 more in the sheet (Findings, filter on D-03) | | | |

Scenarios: BR1_TS7 (3), BR1_TS11 (3), BR1_TS12 (3), BR1_TS13 (3), BR1_TS14 (3), BR1_TS15 (3), BR1_TS16 (3), BR1_TS17 (3), BR1_TS18 (3)

**How to check by hand:** Open the file, count the characters of SegmentLengthIndicator (must be 3) and try to split EnhancedFleetData into 3-digit tag + 3-digit length + data.

**Fix:** Build Segment 145 from real fleet sub-segments (TLV), keep the segment within 999 characters, or drop the segment for a MasterCard Credit (non-fleet) card.

### 3.4 D-09 - A negative test breaks more than the one rule it is meant to break

**10 test cases, 14 findings, 10 test data files** - kind: Test data defect

Rules: `TABLE_030_MISSING` (3), `COF_INDICATORS_MISSING` (3), `SEG130_LENGTH` (3), `FIELD_12` (3), `FIELD_2` (1), `VARINFO_LENGTH` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions."
- Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type."
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file"
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "A Field Separator follows the final EMV Additional Information Section."
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."

What is wrong (one example per kind of problem):

- `TABLE_030_MISSING` (TC-022198) - This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- `COF_INDICATORS_MISSING` (TC-022198) - Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).
- `SEG130_LENGTH` (TC-024323) - '0054' is declared, but the segment adds up to 0201.
- `FIELD_2` (TC-024350) - 'TOTALS REQUEST' fails the card-number check digit (Luhn).
- `FIELD_12` (TC-024355) - '0228' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.
- `VARINFO_LENGTH` (TC-024355) - '' is declared, but the table data 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' is 983 characters long.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-022198](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1733) (BR1_TS7_TC1) | 1 | `TABLE_030_MISSING` | [TD-TC-022198.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022198.json) | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| [TC-022198](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1733) (BR1_TS7_TC1) | 1 | `COF_INDICATORS_MISSING` | [TD-TC-022198.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022198.json) | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| [TC-024323](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1807) (BR1_TS12_TC4) | 1 | `SEG130_LENGTH` | [TD-TC-024323.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024323.json#L7) | `0054` | 0201 |
| [TC-024350](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1834) (BR1_TS1_TC3) | 1 | `FIELD_2` | [TD-TC-024350.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024350.json#L14) | `TOTALS REQUEST` | A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule). |
| [TC-024355](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1839) (BR1_TS11_TC2) | 1 | `FIELD_12` | [TD-TC-024355.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024355.json#L15) | `0228` | A value that meets: AN, variable length 51. |
| [TC-024355](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1839) (BR1_TS11_TC2) | 1 | `VARINFO_LENGTH` | TD-TC-024355.json -> Variable Information Data Segment -> VariableInformationSection[0] | (empty) | 983 |
| [TC-024319](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1803) (BR1_TS17_TC1) | 1 | `TABLE_030_MISSING` | [TD-TC-024319.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024319.json) | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| [TC-024319](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1803) (BR1_TS17_TC1) | 1 | `COF_INDICATORS_MISSING` | [TD-TC-024319.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024319.json) | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| [TC-024324](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1808) (BR1_TS1_TC2) | 1 | `SEG130_LENGTH` | [TD-TC-024324.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024324.json#L7) | `0058` | 0205 |
| [TC-024326](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1810) (BR1_TS17_TC2) | 1 | `TABLE_030_MISSING` | [TD-TC-024326.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024326.json) | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| [TC-024326](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1810) (BR1_TS17_TC2) | 1 | `COF_INDICATORS_MISSING` | [TD-TC-024326.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024326.json) | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| [TC-024349](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1833) (BR1_TS2_TC1) | 1 | `SEG130_LENGTH` | [TD-TC-024349.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024349.json#L7) | `0057` | 0204 |
| … | | 2 more in the sheet (Findings, filter on D-09) | | | |

Scenarios: BR1_TS17 (4), BR1_TS7 (2), BR1_TS1 (2), BR1_TS2 (2), BR1_TS11 (2), BR1_TS12 (1), BR1_TS22 (1)

**How to check by hand:** Compare the failing field with the field the test case deliberately changes (flags DELIBERATE_CONSTRAINT_VIOLATION / method deliberate_violation_*).

**Fix:** Keep every other field valid so the decline can only come from the rule under test.

### 3.5 D-12 - Another data segment is longer than its ATL105 maximum or breaks its TLV layout

**1 test case, 1 finding, 1 test data file** - kind: Test data defect

Rules: `X_SEG_MAX` (1).

ATL105:

- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"

What is wrong (one example per kind of problem):

- `X_SEG_MAX` (TC-021424) - The segment is 156 characters long; ATL105 allows at most 142.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-021424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L860) (BR1_TS15_TC68) | 1 | `X_SEG_MAX` | [TD-TC-021424.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021424.json) | `156` | Keep the segment within 142 characters. |

Scenarios: BR1_TS15 (1)

**How to check by hand:** Add up the segment (section 12.x Field Separator rule) and compare with the maximum length in section 11.1 / 12.x.

**Fix:** Keep the segment within its maximum length and in its layout.

### 3.6 D-13 - Card read from the chip / stripe, but Card Discretionary Block Data holds only an MMYY expiry instead of the Track 2 data after the '='

**37 test cases, 37 findings, 37 test data files** - kind: Test data defect

Rules: `FIELD_12` (37).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."

What is wrong (one example per kind of problem):

- `FIELD_12` (TC-024312) - '0529' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-024312](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1796) (BR1_TS11_TC1) | 1 | `FIELD_12` | [TD-TC-024312.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024312.json#L15) | `0529` | A value that meets: AN, variable length 51. |
| [TC-024314](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1798) (BR1_TS1_TC4) | 1 | `FIELD_12` | [TD-TC-024314.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024314.json#L15) | `0930` | A value that meets: AN, variable length 51. |
| [TC-024315](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1799) (BR1_TS30_TC1) | 1 | `FIELD_12` | [TD-TC-024315.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024315.json#L15) | `0228` | A value that meets: AN, variable length 51. |
| [TC-024316](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1800) (BR1_TS22_TC3) | 1 | `FIELD_12` | [TD-TC-024316.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024316.json#L15) | `0427` | A value that meets: AN, variable length 51. |
| [TC-024317](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1801) (BR1_TS22_TC1) | 1 | `FIELD_12` | [TD-TC-024317.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024317.json#L15) | `0731` | A value that meets: AN, variable length 51. |
| [TC-024320](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1804) (BR1_TS1_TC6) | 1 | `FIELD_12` | [TD-TC-024320.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024320.json#L15) | `0632` | A value that meets: AN, variable length 51. |
| [TC-024321](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1805) (BR1_TS2_TC2) | 1 | `FIELD_12` | [TD-TC-024321.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024321.json#L15) | `0427` | A value that meets: AN, variable length 51. |
| [TC-024322](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1806) (BR1_TS1_TC1) | 1 | `FIELD_12` | [TD-TC-024322.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024322.json#L15) | `0231` | A value that meets: AN, variable length 51. |
| [TC-024323](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1807) (BR1_TS12_TC4) | 1 | `FIELD_12` | [TD-TC-024323.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024323.json#L15) | `0231` | A value that meets: AN, variable length 51. |
| [TC-024324](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1808) (BR1_TS1_TC2) | 1 | `FIELD_12` | [TD-TC-024324.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024324.json#L15) | `1130` | A value that meets: AN, variable length 51. |
| [TC-024325](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1809) (BR1_TS11_TC3) | 1 | `FIELD_12` | [TD-TC-024325.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024325.json#L15) | `0529` | A value that meets: AN, variable length 51. |
| [TC-024329](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1813) (BR1_TS11_TC4) | 1 | `FIELD_12` | [TD-TC-024329.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024329.json#L15) | `0429` | A value that meets: AN, variable length 51. |
| … | | 25 more in the sheet (Findings, filter on D-13) | | | |

Scenarios: BR1_TS1 (4), BR1_TS12 (4), BR1_TS73 (4), BR1_TS11 (3), BR1_TS22 (3), BR1_TS2 (3), BR1_TS58 (3), BR1_TS72 (3), BR1_TS51 (2), BR1_TS30 (1), BR1_TS78 (1), BR1_TS66 (1), BR1_TS59 (1), BR1_TS65 (1), BR1_TS77 (1) …

**How to check by hand:** Open the test data file: Table 005 starts with 05 / 07 / 90 / 91 (card read), but CardDiscretionaryBlockData is 4 digits (MMYY). For a read card it must be the Track 2 data after the '=': expiry YYMM, 3-digit service code, discretionary data.

**Fix:** Send the test card's Track 2 (equivalent) data after the '=' (YYMM + service code 2xx for a chip card + discretionary data), or use a keyed entry mode if only the expiry is known.

### 3.7 D-15 - Positive boundary test: the value is not at the documented limit or is not a valid value

**3 test cases, 3 findings, 3 test data files** - kind: Test data defect

Rules: `X_BOUNDARY_INVALID_VALUE` (1), `X_BOUNDARY_NOT_AT_LIMIT` (1), `FIELD_44` (1).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
- Element 87 Service Level (Chapter 13), PDF p.410 (printed 13-70): "F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present."
- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission"

What is wrong (one example per kind of problem):

- `X_BOUNDARY_INVALID_VALUE` (TC-024336) - Element 87 accepts F, S, N, X, H, O (0-9 reserved for private use; other values reserved for future use). 'A' is not one of them, so an approval cannot be expected.
- `X_BOUNDARY_NOT_AT_LIMIT` (TC-024341) - The test is meant to send Account Number at exactly 24 characters, but '5191561344107494' has 16, so the boundary is not tested.
- `FIELD_44` (TC-024353) - 'A' is not a valid Information Byte (?, 0 or 1).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-024336](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1820) (BR1_TS73_TC1) | 1 | `X_BOUNDARY_INVALID_VALUE` | [TD-TC-024336.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024336.json#L30) | `A` | A valid Service Level, e.g. F (Full serve). |
| [TC-024341](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1825) (BR1_TS58_TC1) | 1 | `X_BOUNDARY_NOT_AT_LIMIT` | [TD-TC-024341.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024341.json#L14) | `5191561344107494` | A valid Account Number of exactly 24 characters, or drop the test if no such value can be approved. |
| [TC-024353](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1837) (BR1_TS12_TC1) | 1 | `FIELD_44` | [TD-TC-024353.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024353.json#L8) | `A` | A value that meets: 1 character: 'A' = not a valid code. |

Scenarios: BR1_TS73 (1), BR1_TS58 (1), BR1_TS12 (1)

**How to check by hand:** Read the objective (field and limit), then the value in the test data: it must have exactly that many characters and be a valid value of the element.

**Fix:** Send a valid value of exactly the documented maximum length; if no such value can be approved (a MasterCard card number is 16 digits, not 24), drop the positive boundary test.

## 4. Defects and issues in the test cases (design, status, expected results)

### 4.1 C-01 - Same request as another test case but a different expected result

**28 test cases, 28 findings, 28 test data files** - kind: Test case defect

Rules: `X_SAME_REQUEST_DIFFERENT_OUTCOME` (28).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response."

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST_DIFFERENT_OUTCOME` (TC-023483) - Apart from the Sequence Number the message is identical to TC-022443 (all segments compared). TC-022443 expects 0/2; this test expects 1.
- `X_SAME_REQUEST_DIFFERENT_OUTCOME` (TC-020974) - Apart from the Sequence Number the message is identical to TC-020970 (all segments compared). TC-020970 expects 1; this test expects 2.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023483](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L222) (BR1_TS9_TC27) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-023483.json vs TD-TC-022443.json | `expected 1 vs 0/2` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-020974](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L298) (BR1_TS11_TC51) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-020974.json vs TD-TC-020970.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-023508](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L251) (BR1_TS10_TC22) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-023508.json vs TD-TC-022476.json | `expected 1 vs 0/2` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-020977](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L301) (BR1_TS11_TC54) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-020977.json vs TD-TC-020972.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-020978](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L302) (BR1_TS11_TC55) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-020978.json vs TD-TC-020973.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021060](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L412) (BR1_TS12_TC51) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021060.json vs TD-TC-021056.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021063](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L415) (BR1_TS12_TC54) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021063.json vs TD-TC-021058.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021064](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L416) (BR1_TS12_TC55) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021064.json vs TD-TC-021059.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021148](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L528) (BR1_TS13_TC50) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021148.json vs TD-TC-021144.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021151](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L531) (BR1_TS13_TC53) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021151.json vs TD-TC-021146.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021152](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L532) (BR1_TS13_TC54) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021152.json vs TD-TC-021147.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-021321](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L729) (BR1_TS14_TC46) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-021321.json vs TD-TC-021317.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| … | | 16 more in the sheet (Findings, filter on C-01) | | | |

Scenarios: BR1_TS11 (3), BR1_TS12 (3), BR1_TS13 (3), BR1_TS14 (3), BR1_TS15 (3), BR1_TS16 (3), BR1_TS17 (3), BR1_TS18 (3), BR1_TS9 (1), BR1_TS10 (1), BR1_TS58 (1), BR1_TS77 (1)

**How to check by hand:** Open both test data files side by side: apart from the Sequence Number they are identical, but one test expects approval and the other a decline.

**Fix:** Give the decline test its own trigger (decline test card / amount / simulator rule) so its request differs.

### 4.2 C-02 - Negative test premise contradicts ATL105 (the rule it claims to break does not exist)

**43 test cases, 43 findings** - kind: Test case defect

Rules: `X_NEG_PREMISE` (43).

ATL105:

- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"
- Section 10.14.3 EMV PIN (Personal Identification Number) Processing, PDF p.162 (printed 10-83): "Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "3 44 Information Byte 1 R"
- Section 12.3 Product Code Data Segment, PDF p.230 (printed 12-13): "3 87 Service Level 1 R Identifies the type of sale."
- Section 12.7 Loyalty Card Data Segment, PDF p.239 (printed 12-22): "12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card."
- Section 10.14.3 EMV PIN (Personal Identification Number) Processing, PDF p.162 (printed 10-83): "In a credit/signature debit transaction, a PIN of all F’s is NOT required since PINs are an optional field for a credit/signature debit transaction."
- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE` (TC-020970) - ATL105 says online PIN is applicable for credit transactions and that a PIN is an optional field for credit - it does not forbid a PIN block on a credit Authorization Only.
- `X_NEG_PREMISE` (TC-020971) - Information Byte (Element 44) is a Required field (R) of Segment 100, so it is sent on every financial request.
- `X_NEG_PREMISE` (TC-020972) - Payment Tender Type (Element 148) is a Required field (R) of Segment 108 whenever the segment is sent.
- `X_NEG_PREMISE` (TC-020973) - Service Level (Element 87) is a Required field (R) of Segment 102 whenever the segment is sent.
- `X_NEG_PREMISE` (TC-023733) - ATL105 Section 10.1.2 lists Mail/Phone Reversal among the point-of-sale transactions supported for credit card processing.
- `X_NEG_PREMISE` (TC-022198) - PDF p.561 (printed Appendix I-12) does not mention Enhanced Fleet Request at all. Section 11.1 lists Segment 145 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-024325) - PDF p.688 (printed Appendix O-1) does not mention NFC Payment Tokenization Data Segment at all. Section 11.1 lists Segment 123 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-024326) - PDF p.728 (printed Appendix AD-1) does not mention Purchase Card Data Segment at all. Section 11.1 lists Segment 104 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-024328) - PDF p.15 (front matter, no printed page number) does not mention Loyalty Card Data Segment at all. Section 11.1 lists Segment 108 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-024329) - PDF p.28 (front matter, no printed page number) does not mention Purchase Card Data Segment at all. Section 11.1 lists Segment 104 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-024330) - PDF p.23 (front matter, no printed page number) does not mention Fleet Data Segment at all. Section 11.1 lists Segment 101 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-024360) - PDF p.142 (printed 10-63) does not mention EMV Request Data Segment at all. Section 11.1 lists Segment 130 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020970](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L294) (BR1_TS11_TC47) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-020971](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L295) (BR1_TS11_TC48) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-020972](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L296) (BR1_TS11_TC49) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-020973](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L297) (BR1_TS11_TC50) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-023733](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L682) (BR1_TS13_TC204) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Mail/Phone Reversal … -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-022198](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1733) (BR1_TS7_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `Enhanced Fleet Request (segment 145) omi… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-024325](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1809) (BR1_TS11_TC3) | - | `X_NEG_PREMISE` | test case test_objective | `NFC Payment Tokenization Data Segment (s… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-024326](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1810) (BR1_TS17_TC2) | - | `X_NEG_PREMISE` | test case test_objective | `Purchase Card Data Segment (segment 104)… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-024328](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1812) (BR1_TS15_TC2) | - | `X_NEG_PREMISE` | test case test_objective | `Loyalty Card Data Segment (segment 108) … requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-024329](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1813) (BR1_TS11_TC4) | - | `X_NEG_PREMISE` | test case test_objective | `Purchase Card Data Segment (segment 104)… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-024330](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1814) (BR1_TS58_TC2) | - | `X_NEG_PREMISE` | test case test_objective | `Fleet Data Segment (segment 101) omitted… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-024360](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1844) (BR1_TS13_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `EMV Request Data Segment (segment 130) o… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| … | | 31 more in the sheet (Findings, filter on C-02) | | | |

Scenarios: BR1_TS11 (6), BR1_TS13 (6), BR1_TS15 (5), BR1_TS17 (5), BR1_TS12 (4), BR1_TS14 (4), BR1_TS16 (4), BR1_TS18 (4), BR1_TS58 (3), BR1_TS77 (1), BR1_TS7 (1)

**How to check by hand:** Read the test objective, then the cited ATL105 rule: the transaction/field is supported or required.

**Fix:** Remove the test or rewrite it around a rule ATL105 states.

### 4.3 C-03 - Test case marked 'ready' although its Terminal Identifier is a placeholder

**606 test cases, 606 findings** - kind: Test case issue to correct

Rules: `READY_WITH_PLACEHOLDER` (606).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

What is wrong (one example per kind of problem):

- `READY_WITH_PLACEHOLDER` (TC-020705) - The test case status is 'ready', but its Terminal Identifier is still a placeholder.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023306](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L6) (BR1_TS1_TC12) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023308](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L8) (BR1_TS1_TC14) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023309](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L9) (BR1_TS1_TC15) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023310](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L10) (BR1_TS1_TC16) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023311](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L11) (BR1_TS1_TC17) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023312](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L12) (BR1_TS1_TC18) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-023313](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L13) (BR1_TS1_TC19) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| … | | 594 more in the sheet (Findings, filter on C-03) | | | |

Scenarios: BR1_TS17 (59), BR1_TS11 (56), BR1_TS12 (56), BR1_TS15 (55), BR1_TS13 (53), BR1_TS14 (50), BR1_TS16 (50), BR1_TS1 (24), BR1_TS9 (23), BR1_TS2 (22), BR1_TS7 (21), BR1_TS3 (19), BR1_TS4 (19), BR1_TS5 (19), BR1_TS6 (19) …

**How to check by hand:** TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.

**Fix:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 4.4 C-04 - Expected Response Code not stated for a step

**276 test cases, 276 findings** - kind: Test case issue to correct

Rules: `EXPECTED_RESPONSE_OPEN` (276).

ATL105:

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"

What is wrong (one example per kind of problem):

- `EXPECTED_RESPONSE_OPEN` (TC-023308) - The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023308](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L8) (BR1_TS1_TC14) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023309](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L9) (BR1_TS1_TC15) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023310](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L10) (BR1_TS1_TC16) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023312](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L12) (BR1_TS1_TC18) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023313](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L13) (BR1_TS1_TC19) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023314](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L14) (BR1_TS1_TC20) | - | `EXPECTED_RESPONSE_OPEN` | test case step 4 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023315](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L15) (BR1_TS1_TC21) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023316](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L16) (BR1_TS1_TC22) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023317](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L17) (BR1_TS1_TC23) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023327](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L32) (BR1_TS2_TC12) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023328](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L33) (BR1_TS2_TC13) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-023329](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L34) (BR1_TS2_TC14) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| … | | 264 more in the sheet (Findings, filter on C-04) | | | |

Scenarios: BR1_TS10 (20), BR1_TS9 (19), BR1_TS1 (9), BR1_TS2 (9), BR1_TS3 (9), BR1_TS4 (9), BR1_TS5 (9), BR1_TS6 (9), BR1_TS7 (9), BR1_TS8 (9), BR1_TS27 (9), BR1_TS21 (8), BR1_TS22 (8), BR1_TS23 (8), BR1_TS24 (8) …

**How to check by hand:** Open tx_legs[i].expect / steps[i].expected_response: no response_code value.

**Fix:** State the expected Response Code (from BUYPASS or a reference run).

### 4.5 C-05 - Test objective / name does not match the message sent or the expected result

**15 test cases, 15 findings** - kind: Test case issue to correct

Rules: `X_OBJECTIVE_MISMATCH` (12), `X_NAME_OUTCOME_MISMATCH` (3).

ATL105:

- Element 78 Prompt Code (Chapter 13), PDF p.400 (printed 13-60): "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters)."
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"

What is wrong (one example per kind of problem):

- `X_OBJECTIVE_MISMATCH` (TC-022280) - The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type '0' (POS Purchase/Capture) and the name says Purchase/Capture.
- `X_OBJECTIVE_MISMATCH` (TC-023461) - The objective says 'Purchase/Capture', but step 1 sends Prompt Code Transaction Type '6' (Mail/Phone Purchase) and the name says Mail/Phone Purchase.
- `X_OBJECTIVE_MISMATCH` (TC-022966) - The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type 'B' (Mail/Phone Authorization Only) and the name says Mail/Phone Authorization Only.
- `X_NAME_OUTCOME_MISMATCH` (TC-024336) - The name ends with '→ declined', but the objective says the value 'is accepted' and the expected Response Code is 0 (approved).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-023461](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L200) (BR1_TS9_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Purchase/Capture with in…= completion` | Correct the objective to Mail/Phone Purchase. |
| [TC-022966](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1260) (BR1_TS19_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to Mail/Phone Authorization Only. |
| [TC-024336](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1820) (BR1_TS73_TC1) | - | `X_NAME_OUTCOME_MISMATCH` | test case name | `ervice Level exactly at its documented m…… → declined` | Correct the name to '→ approved'. |
| [TC-022300](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L29) (BR1_TS2_TC9) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-022320](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L53) (BR1_TS3_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-022342](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L79) (BR1_TS4_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-022362](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L103) (BR1_TS5_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-022382](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L127) (BR1_TS6_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-022402](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L151) (BR1_TS7_TC6) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-022423](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L176) (BR1_TS8_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-023491](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L234) (BR1_TS10_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Purchase/Capture with in…= completion` | Correct the objective to Mail/Phone Purchase. |
| … | | 3 more in the sheet (Findings, filter on C-05) | | | |

Scenarios: BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1), BR1_TS8 (1), BR1_TS9 (1), BR1_TS10 (1), BR1_TS19 (1), BR1_TS20 (1), BR1_TS73 (1), BR1_TS58 (1), BR1_TS12 (1)

**How to check by hand:** Compare test_objective and the name with the Prompt Code Transaction Type in the test data and with the expected Response Code.

**Fix:** Correct the objective / name text.

## 5. Citation issues (the ATL105 text quoted in the test cases)

### 5.1 Q-01 - Quote not found in ATL105 (generator note or other text cited as ATL105)

**79 test cases, 79 findings** - kind: Citation issue

Rules: `QUOTE_NOT_IN_SPEC` (79).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."
- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"

What is wrong (one example per kind of problem):

- `QUOTE_NOT_IN_SPEC` (TC-020707) - The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-022444) - The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction (reversal matching)." for p.402. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-018709) - The test case quotes "Table 042 Cardholder Language" for p.583. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-018720) - The test case quotes "Table 065 Merchant Payment Gateway ID Data" for p.622. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-020968) - The test case quotes "§10.1.2.2/§10.2.2.2 (p.83/97): 'Element No.86 (Sequence Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction.' Element No. 78 (Prompt Code) must contain Transaction Type code 'S' — verified verbatim against raw/pdf_pages/page_0083.txt and page_0097.txt, 2026-10-01." for p.83. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-023549) - The test case quotes "Table 059 Soft Descriptor - Merchant Country" for p.613. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-023551) - The test case quotes "Table 062 Customer Additional Information Data" for p.614. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-023734) - The test case quotes "Not in the list: The following point-of-sale transactions are supported for credit card processing:" for p.83. Not found in ATL105.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| [TC-022444](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L197) (BR1_TS9_TC2) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `The Sequence Number must be the Sequence…l matching).` | Quote the text exactly as it appears on the page. |
| [TC-018709](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L266) (BR1_TS11_TC19) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 042 Cardholder Language` | Quote the text exactly as it appears on the page. |
| [TC-018720](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L277) (BR1_TS11_TC30) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 065 Merchant Payment Gateway ID Data` | Quote the text exactly as it appears on the page. |
| [TC-020968](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L292) (BR1_TS11_TC45) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `§10.1.2.2/§10.2.2.2 (p.83/97): 'Element … 2026-10-01.` | Quote the text exactly as it appears on the page. |
| [TC-023549](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L360) (BR1_TS11_TC113) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 059 Soft Descriptor - Merchant Country` | Quote the text exactly as it appears on the page. |
| [TC-023551](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L362) (BR1_TS11_TC115) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 062 Customer Additional Information Data` | Quote the text exactly as it appears on the page. |
| [TC-023734](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L683) (BR1_TS13_TC205) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Not in the list: The following point-of-… processing:` | Quote the text exactly as it appears: "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time- |
| [TC-020731](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L27) (BR1_TS2_TC7) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| [TC-020755](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L51) (BR1_TS3_TC3) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| [TC-020781](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L77) (BR1_TS4_TC3) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| [TC-020805](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L101) (BR1_TS5_TC3) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| … | | 67 more in the sheet (Findings, filter on Q-01) | | | |

Scenarios: BR1_TS13 (7), BR1_TS11 (6), BR1_TS12 (6), BR1_TS14 (6), BR1_TS15 (6), BR1_TS16 (6), BR1_TS17 (6), BR1_TS18 (6), BR1_TS9 (2), BR1_TS10 (2), BR1_TS19 (2), BR1_TS20 (2), BR1_TS58 (2), BR1_TS77 (2), BR1_TS44 (2) …

**How to check by hand:** Search the quoted text on the cited page.

**Fix:** Quote the ATL105 sentence word for word.

### 5.2 Q-02 - Quote is on a different page than cited

**554 test cases, 560 findings** - kind: Citation issue

Rules: `QUOTE_PAGE_DRIFT` (533), `QUOTE_WRONG_PAGE` (26), `CITATION_OUTDATED_PAGE` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"

What is wrong (one example per kind of problem):

- `QUOTE_PAGE_DRIFT` (TC-020705) - The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).
- `QUOTE_WRONG_PAGE` (TC-020706) - The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- `QUOTE_WRONG_PAGE` (TC-020708) - The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).
- `CITATION_OUTDATED_PAGE` (TC-024355) - The test case cites p.246 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.246 (printed 12-29) does not contain it; it is on PDF p.555 (printed Appendix I-6).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `The Sequence Number must be the Sequence…transaction.` | Cite p.84 instead of p.402. |
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `A TOR must be sent from the same device … in the TOR.` | Cite p.40 instead of p.38. |
| [TC-024355](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1839) (BR1_TS11_TC2) | - | `CITATION_OUTDATED_PAGE` | test case request -> Variable Information Data Segment -> Variable Information (source_page), every step | `p.246` | Change the cited page from 246 to 555. The data values are not affected. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-023306](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L6) (BR1_TS1_TC12) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-023307](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC13) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-023308](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L8) (BR1_TS1_TC14) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-023309](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L9) (BR1_TS1_TC15) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| … | | 548 more in the sheet (Findings, filter on Q-02) | | | |

Scenarios: BR1_TS11 (117), BR1_TS12 (114), BR1_TS58 (60), BR1_TS77 (59), BR1_TS44 (29), BR1_TS1 (28), BR1_TS2 (26), BR1_TS59 (15), BR1_TS78 (15), BR1_TS51 (11), BR1_TS21 (10), BR1_TS22 (10), BR1_TS72 (7), BR1_TS37 (6), BR1_TS65 (5) …

**How to check by hand:** Find the quote: it is on another PDF page.

**Fix:** Correct the page number.

### 5.3 Q-03 - Quote is not word-for-word

**474 test cases, 601 findings** - kind: Citation issue

Rules: `QUOTE_PARAPHRASED` (601).

What is wrong (one example per kind of problem):

- `QUOTE_PARAPHRASED` (TC-023321) - The test case quotes "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." for p.148. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-023485) - The test case quotes "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" for p.161. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018696) - The test case quotes "Table 006 Currency Code Information" for p.555. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018697) - The test case quotes "Table 007 SIC Information" for p.555. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018698) - The test case quotes "Table 008 Balance Information" for p.556. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018699) - The test case quotes "Table 019 Recharge Type Information" for p.562. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018699) - The test case quotes "2 Data Required for PIN on Receipt and Top Up transactions." for p.562. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018700) - The test case quotes "Table 022 Originating Device Type Indicator Information" for p.564. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018701) - The test case quotes "Table 027 Customer Name" for p.566. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018702) - The test case quotes "Table 029 Universal Unique Identifier" for p.567. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018703) - The test case quotes "Table 031 Point-of-Service Terminal Capability Codes" for p.569. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-018704) - The test case quotes "Table 033 Re-Price Data Request Information" for p.578. Not word-for-word: all its words are on the cited page, but not in this form.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023321](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L21) (BR1_TS1_TC27) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-023485](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L224) (BR1_TS9_TC29) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Not in the list: The following point-of-… processing:` | Quote the text exactly as it appears on the page. |
| [TC-018696](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L253) (BR1_TS11_TC6) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 006 Currency Code Information` | Quote the text exactly as it appears on the page. |
| [TC-018697](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L254) (BR1_TS11_TC7) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 007 SIC Information` | Quote the text exactly as it appears on the page. |
| [TC-018698](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L255) (BR1_TS11_TC8) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 008 Balance Information` | Quote the text exactly as it appears on the page. |
| [TC-018699](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L256) (BR1_TS11_TC9) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 019 Recharge Type Information` | Quote the text exactly as it appears on the page. |
| [TC-018699](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L256) (BR1_TS11_TC9) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `2 Data Required for PIN on Receipt and Top Up transactions.` | Quote the text exactly as it appears on the page. |
| [TC-018700](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L257) (BR1_TS11_TC10) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 022 Originating Device Type Indicator Information` | Quote the text exactly as it appears on the page. |
| [TC-018701](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L258) (BR1_TS11_TC11) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 027 Customer Name` | Quote the text exactly as it appears on the page. |
| [TC-018702](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L259) (BR1_TS11_TC12) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 029 Universal Unique Identifier` | Quote the text exactly as it appears on the page. |
| [TC-018703](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L260) (BR1_TS11_TC13) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 031 Point-of-Service Terminal Capability Codes` | Quote the text exactly as it appears on the page. |
| [TC-018704](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L261) (BR1_TS11_TC14) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 033 Re-Price Data Request Information` | Quote the text exactly as it appears on the page. |
| … | | 589 more in the sheet (Findings, filter on Q-03) | | | |

Scenarios: BR1_TS13 (70), BR1_TS17 (66), BR1_TS11 (59), BR1_TS12 (57), BR1_TS14 (57), BR1_TS15 (57), BR1_TS16 (57), BR1_TS18 (57), BR1_TS58 (30), BR1_TS77 (30), BR1_TS44 (27), BR1_TS2 (6), BR1_TS22 (4), BR1_TS9 (3), BR1_TS1 (2) …

**How to check by hand:** Compare the quote with the page text.

**Fix:** Quote word for word.

### 5.4 Q-04 - Citation or device checkpoint from an unrelated section or document

**82 test cases, 82 findings** - kind: Citation issue

Rules: `X_CHECKPOINT_CONTEXT` (39), `X_BASIS_CONTEXT` (30), `QUOTE_CONTEXT` (11), `X_BASIS_PAGE` (2).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)."
- Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8): "A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response."
- Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
- Section 11.8.1 EMV Financial Transaction Request, PDF p.206 (printed 11-43): "This segment is the only segment required for all EMV financial transactions."

What is wrong (one example per kind of problem):

- `X_CHECKPOINT_CONTEXT` (TC-020705) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (GENERAL).
- `X_BASIS_CONTEXT` (TC-022280) - The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.
- `X_CHECKPOINT_CONTEXT` (TC-020851) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (CARD_NOT_PRESENT).
- `QUOTE_CONTEXT` (TC-023510) - The quote is word-for-word, but it comes from Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8); that section is about FSA/HRA healthcare benefit cards.
- `X_CHECKPOINT_CONTEXT` (TC-022175) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Merchant category 5542 · attended POS (MCC_5542).
- `X_CHECKPOINT_CONTEXT` (TC-024271) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Hospitality · attended POS (HOSPITALITY-ATTENDED).
- `X_BASIS_PAGE` (TC-024318) - Segment 130 is required for all EMV financial transactions and step 1 is a chip read (test case entry mode EMV_CONTACT), so the negative test is valid - but PDF p.162 (printed 10-83) does not say so.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |
| [TC-020851](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L147) (BR1_TS7_TC2) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-023510](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L321) (BR1_TS11_TC74) | - | `QUOTE_CONTEXT` | test case spec_basis | `A Split Tender transaction results when …on response.` | Cite Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5) as the main basis for a normal MasterCard credit card. |
| [TC-022175](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1737) (BR1_TS83_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-024271](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1755) (BR1_TS37_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-024318](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1802) (BR1_TS1_TC5) | - | `X_BASIS_PAGE` | test case test_objective | `EMV Request Data Segment (segment 130) o… requires it` | Cite Section 11.8 (Segment 130 required for all EMV financial transactions) as the basis. |
| [TC-023307](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC13) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |
| [TC-020729](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L25) (BR1_TS2_TC5) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-022300](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L29) (BR1_TS2_TC9) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |
| [TC-023326](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L31) (BR1_TS2_TC11) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |
| [TC-020753](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L49) (BR1_TS3_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| … | | 70 more in the sheet (Findings, filter on Q-04) | | | |

Scenarios: BR1_TS1 (5), BR1_TS2 (3), BR1_TS3 (3), BR1_TS4 (3), BR1_TS5 (3), BR1_TS6 (3), BR1_TS7 (3), BR1_TS8 (3), BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2) …

**How to check by hand:** Read the cited text: it covers another transaction, card or industry.

**Fix:** Cite the rule that applies.

## 6. Values awaiting client input or run-time values (BLOCKED, not defects)

### 6.1 I-01 - Terminal Identifier is a generated placeholder

**1,709 test cases, 1,709 findings** - kind: Awaiting client input / run-time value

Rules: `TERMINAL_PLACEHOLDER` (1709).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "West Virginia WV 54"
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "Armed Forces Americas AA 57"
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Michigan MI 26"
- Appendix D. Valid State Codes, PDF p.514 (printed Appendix D-1): "Alabama AL 01"
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Ontario ON 68"
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Northwest Territories NT 67"
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "New Hampshire NH 33"

What is wrong (one example per kind of problem):

- `TERMINAL_PLACEHOLDER` (TC-020705) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- `TERMINAL_PLACEHOLDER` (TC-023318) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'RR54265527479214001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '54' = West Virginia (WV), United States.
- `TERMINAL_PLACEHOLDER` (TC-021862) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'KC57457075622174001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '57' = Armed Forces Americas (AA), US military.
- `TERMINAL_PLACEHOLDER` (TC-022038) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'VZ26598162666071001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '26' = Michigan (MI), United States.
- `TERMINAL_PLACEHOLDER` (TC-024259) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'PW01708669533046001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '01' = Alabama (AL), United States.
- `TERMINAL_PLACEHOLDER` (TC-024267) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'PQ68310350483989001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '68' = Ontario (ON), Canada.
- `TERMINAL_PLACEHOLDER` (TC-024271) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'QU67070249149717001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '67' = Northwest Territories (NT), Canada.
- `TERMINAL_PLACEHOLDER` (TC-024308) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'XX33617647546778001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '33' = New Hampshire (NH), United States.
- `TERMINAL_PLACEHOLDER` (TC-024322) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'AAAAAAAAAAAAAAAAAAAAAAA' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) 'AA' = not a valid State Code.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-023318](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L18) (BR1_TS1_TC24) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `RR54265527479214001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-021862](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1376) (BR1_TS51_TC4) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `KC57457075622174001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-022038](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1579) (BR1_TS72_TC4) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `VZ26598162666071001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-024259](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1721) (BR1_TS82_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `PW01708669533046001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-024267](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1729) (BR1_TS103_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `PQ68310350483989001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-024271](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1755) (BR1_TS37_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `QU67070249149717001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-024308](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1792) (BR1_TS89_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `XX33617647546778001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-024322](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1806) (BR1_TS1_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `AAAAAAAAAAAAAAAAAAAAAAA` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| … | | 1,697 more in the sheet (Findings, filter on I-01) | | | |

Scenarios: BR1_TS17 (125), BR1_TS11 (118), BR1_TS12 (118), BR1_TS13 (117), BR1_TS15 (116), BR1_TS16 (115), BR1_TS14 (111), BR1_TS18 (110), BR1_TS58 (60), BR1_TS77 (59), BR1_TS1 (29), BR1_TS44 (29), BR1_TS2 (27), BR1_TS9 (27), BR1_TS7 (26) …

**How to check by hand:** merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.

**Fix:** Client / BUYPASS to supply the real Terminal Identifier.

### 6.2 I-02 - Value filled in at run time from the previous response (expected for follow-on steps)

**230 test cases, 271 findings, 258 test data files** - kind: Awaiting client input / run-time value

Rules: `RUNTIME_PLACEHOLDER` (255), `SEGMENT_LENGTH_RUNTIME` (16).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled"
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."
- Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
- Element 6 Approved Amount (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "A Field Separator always follows Segment Type and Segment Length."
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "A Product Data Field Delimiter always follows Quantity and Unit Price."
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "A Field Separator follows Product Amount when Product Amount is the very last element in the segment."
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "It has a maximum length of 381 alphanumeric characters"

What is wrong (one example per kind of problem):

- `RUNTIME_PLACEHOLDER` (TC-020706) - The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-023510) - '086' depends on the run-time value(s) Nonfuel Amount.
- `RUNTIME_PLACEHOLDER` (TC-023510) - The field holds the instruction text '<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>' instead of a real Nonfuel Amount. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-024078) - The segment holds run-time values in angle brackets; its length can only be calculated after they are filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-024334) - '063' depends on the run-time value(s) Account Number.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-020706.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020706.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-023510](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L321) (BR1_TS11_TC74) | 2 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-023510.step2.split-tender.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023510.step2.split-tender.json#L7) | `086` | Recalculate the Segment Length after filling in the real values. |
| [TC-023510](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L321) (BR1_TS11_TC74) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-023510.step2.split-tender.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023510.step2.split-tender.json#L19) | `<RemainingBalance = step 1 Nonfuel Amoun…oved Amount>` | Fill in at run time: Amount = requested amount minus the Approved Amount (Element 6) of the partial approval response (p.84 'remaining balance due'); runtime link.. |
| [TC-024078](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1438) (BR1_TS58_TC44) | 2 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-024078.step2.split-tender.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024078.step2.split-tender.json#L7) | `022` | Recalculate after substitution. |
| [TC-024334](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1818) (BR1_TS72_TC1) | 1 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-024334.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024334.json#L7) | `063` | Recalculate the Segment Length after filling in the real values. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-020707.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020707.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-023308](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L8) (BR1_TS1_TC14) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-023308.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023308.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-023312](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L12) (BR1_TS1_TC18) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-023312.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023312.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-023315](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L15) (BR1_TS1_TC21) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-023315.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023315.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-023322](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L22) (BR1_TS1_TC28) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-023322.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023322.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-020730](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L26) (BR1_TS2_TC6) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-020730.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020730.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-020731](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L27) (BR1_TS2_TC7) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-020731.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020731.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| … | | 259 more in the sheet (Findings, filter on I-02) | | | |

Scenarios: BR1_TS11 (9), BR1_TS12 (9), BR1_TS13 (9), BR1_TS14 (9), BR1_TS15 (9), BR1_TS16 (9), BR1_TS17 (9), BR1_TS18 (9), BR1_TS10 (8), BR1_TS77 (8), BR1_TS9 (7), BR1_TS58 (7), BR1_TS1 (6), BR1_TS2 (6), BR1_TS3 (6) …

**How to check by hand:** The field holds text in angle brackets, e.g. <ApprovalNumber from step 1 response>.

**Fix:** The harness substitutes the value from the previous response and recalculates the Segment Length.

### 6.3 I-03 - Variable Information table value awaited from the client (Segment 111 length to recalculate)

**438 test cases, 438 findings, 438 test data files** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_TABLE` (438).

ATL105:

- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "Identifies the length of Variable Information (Element No. 113)."
- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "A Field Separator follows the final Variable Information Section."

What is wrong (one example per kind of problem):

- `X_AWAITING_TABLE` (TC-023307) - Table(s) 082 hold <AWAITING:...> text instead of a value. The Segment 111 Segment Length '045' counts only the table indicator, so it must be recalculated when the value is filled in. The test case lists: step 1: 111 Variable Information Length [4] - Table 082 length follows its awaited data

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023307](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC13) | 1 | `X_AWAITING_TABLE` | [TD-TC-023307.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023307.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023326](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L31) (BR1_TS2_TC11) | 1 | `X_AWAITING_TABLE` | [TD-TC-023326.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023326.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023345](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L55) (BR1_TS3_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-023345.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023345.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023366](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L81) (BR1_TS4_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-023366.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023366.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023385](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L105) (BR1_TS5_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-023385.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023385.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023404](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L129) (BR1_TS6_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-023404.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023404.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023423](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L153) (BR1_TS7_TC8) | 1 | `X_AWAITING_TABLE` | [TD-TC-023423.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023423.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023443](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L178) (BR1_TS8_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-023443.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023443.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023461](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L200) (BR1_TS9_TC5) | 1 | `X_AWAITING_TABLE` | [TD-TC-023461.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023461.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-023491](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L234) (BR1_TS10_TC5) | 1 | `X_AWAITING_TABLE` | [TD-TC-023491.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023491.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-018696](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L253) (BR1_TS11_TC6) | 1 | `X_AWAITING_TABLE` | [TD-TC-018696.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-018696.json) | `Table 006` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-018697](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L254) (BR1_TS11_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-018697.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-018697.json) | `Table 007` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| … | | 426 more in the sheet (Findings, filter on I-03) | | | |

Scenarios: BR1_TS17 (48), BR1_TS11 (44), BR1_TS12 (44), BR1_TS13 (44), BR1_TS14 (44), BR1_TS15 (44), BR1_TS16 (44), BR1_TS18 (44), BR1_TS58 (24), BR1_TS77 (24), BR1_TS44 (23), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1) …

**How to check by hand:** VariableInformationLength / VariableInformation hold <AWAITING:...>; awaiting_inputs lists the value.

**Fix:** Fill in the client value and recalculate Variable Information Length and Segment 111 Segment Length.

### 6.4 I-04 - EMV chip data is generated, not from a card / simulator

**541 test cases, 1,805 findings** - kind: Awaiting client input / run-time value

Rules: `EMV_CA_CHECKSUM_SAMPLE` (541), `EMV_PLACEHOLDER_CRYPTO` (541), `EMV_TVR_CVM_MISMATCH` (525), `EMV_CONTACTLESS_TEMPLATE` (198).

ATL105:

- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."
- Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags."

What is wrong (one example per kind of problem):

- `EMV_CA_CHECKSUM_SAMPLE` (TC-020705) - The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- `EMV_TVR_CVM_MISMATCH` (TC-020705) - TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.
- `EMV_PLACEHOLDER_CRYPTO` (TC-020705) - ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- `EMV_TVR_CVM_MISMATCH` (TC-023319) - TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 440302) say enciphered PIN verified by the card and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.
- `EMV_CONTACTLESS_TEMPLATE` (TC-020729) - The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-020705](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1) (BR1_TS1_TC7) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=2…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-023319](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC25) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=440302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-020729](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L25) (BR1_TS2_TC5) | - | `EMV_CONTACTLESS_TEMPLATE` | EMV Request Data Segment -> EMVChipData (every EMV request) | `22 tags, CVM 9F34 = 5E0302` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-020706](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC8) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=3…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=C…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| … | | 1,793 more in the sheet (Findings, filter on I-04) | | | |

Scenarios: BR1_TS12 (468), BR1_TS11 (350), BR1_TS58 (172), BR1_TS77 (169), BR1_TS2 (106), BR1_TS44 (87), BR1_TS1 (83), BR1_TS59 (52), BR1_TS78 (52), BR1_TS22 (50), BR1_TS51 (30), BR1_TS21 (27), BR1_TS72 (26), BR1_TS73 (16), BR1_TS65 (15) …

**How to check by hand:** See the EMV finding text in the guide / sheet.

**Fix:** Use chip data captured from the client's test card or card simulator.

### 6.5 I-05 - Totals Request device versions are generated placeholders

**67 test cases, 67 findings, 67 test data files** - kind: Awaiting client input / run-time value

Rules: `TOTALS_VERSION_PLACEHOLDER` (67).

ATL105:

- Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters"
- Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters"
- Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters"

What is wrong (one example per kind of problem):

- `TOTALS_VERSION_PLACEHOLDER` (TC-023315) - ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023315](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L15) (BR1_TS1_TC21) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023315.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023315.step3.totals.json) | `3W92CYI0, PWPVVWSL, BF8NGGH9` | Use the versions of the certified device that runs the test. |
| [TC-023316](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L16) (BR1_TS1_TC22) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023316.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023316.step3.totals.json) | `3ARA1AP1, H0G00RY9, E6TBYPQH` | Use the versions of the certified device that runs the test. |
| [TC-023317](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L17) (BR1_TS1_TC23) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023317.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023317.step2.totals.json) | `ZACY3B1W, LLBRLKNU, AVCX58MS` | Use the versions of the certified device that runs the test. |
| [TC-023334](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L39) (BR1_TS2_TC19) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023334.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023334.step3.totals.json) | `7USVQHHW, PT8NDQS3, MVD5E0A1` | Use the versions of the certified device that runs the test. |
| [TC-023335](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L40) (BR1_TS2_TC20) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023335.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023335.step3.totals.json) | `YH2NJSCX, ZTWZ7KKV, JLAVTK3W` | Use the versions of the certified device that runs the test. |
| [TC-023336](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L41) (BR1_TS2_TC21) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023336.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023336.step2.totals.json) | `TD7PTDZQ, W9J1BETE, RAV889GO` | Use the versions of the certified device that runs the test. |
| [TC-023353](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L63) (BR1_TS3_TC15) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023353.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023353.step3.totals.json) | `3TIA2QIU, JQ42M2OH, 6ARFGL9C` | Use the versions of the certified device that runs the test. |
| [TC-023354](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L64) (BR1_TS3_TC16) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023354.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023354.step3.totals.json) | `YL3BK8MO, NEGZGURJ, W1PNJ4OC` | Use the versions of the certified device that runs the test. |
| [TC-023355](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L65) (BR1_TS3_TC17) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023355.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023355.step2.totals.json) | `KD4CO2JF, 9U4D4F8C, VVE7EKRE` | Use the versions of the certified device that runs the test. |
| [TC-023374](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L89) (BR1_TS4_TC15) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023374.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023374.step3.totals.json) | `OA092KJ5, S5AI55QP, KCTVMCCN` | Use the versions of the certified device that runs the test. |
| [TC-023375](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L90) (BR1_TS4_TC16) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023375.step3.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023375.step3.totals.json) | `G9XKHZHP, BHVEV3E7, 8SVPUJDO` | Use the versions of the certified device that runs the test. |
| [TC-023376](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L91) (BR1_TS4_TC17) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-023376.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023376.step2.totals.json) | `63U1OU41, 0FWOYU6D, GPORMFFX` | Use the versions of the certified device that runs the test. |
| … | | 55 more in the sheet (Findings, filter on I-05) | | | |

Scenarios: BR1_TS1 (3), BR1_TS2 (3), BR1_TS3 (3), BR1_TS4 (3), BR1_TS5 (3), BR1_TS6 (3), BR1_TS7 (3), BR1_TS8 (3), BR1_TS9 (3), BR1_TS10 (3), BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2) …

**How to check by hand:** Hardware/Software/Firmware Version hold random strings.

**Fix:** Client to supply the versions of the certified device.

### 6.6 I-06 - Optional segment content is synthesized - real client data needed before an approval can be expected

**65 test cases, 65 findings, 65 test data files** - kind: Awaiting client input / run-time value

Rules: `X_SYNTHESIZED_CONTENT` (65).

ATL105:

- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions."

What is wrong (one example per kind of problem):

- `X_SYNTHESIZED_CONTENT` (TC-022280) - The test case generated the Segment 153 values itself (spec_tlv_composite); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's network-token entitlement and a real provisioned network token.
- `X_SYNTHESIZED_CONTENT` (TC-023424) - The test case generated the Segment 145 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: an enhanced fleet card and its fleet sub-segment data.
- `X_SYNTHESIZED_CONTENT` (TC-020977) - The test case generated the Segment 108 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's loyalty program data and loyalty card.
- `X_SYNTHESIZED_CONTENT` (TC-020990) - The test case generated the Segment 104 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: a purchase (commercial) card and its purchase-card data.
- `X_SYNTHESIZED_CONTENT` (TC-021165) - The test case generated the Segment 123 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: a tokenized card and its token/cryptogram data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022280.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022280.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-023424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L154) (BR1_TS7_TC9) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-023424.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023424.json) | `format_synthesis` | Client to supply an enhanced fleet card and its fleet sub-segment data; keep the test case out of 'ready' until then. |
| [TC-020977](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L301) (BR1_TS11_TC54) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-020977.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020977.json) | `format_synthesis` | Client to supply the merchant's loyalty program data and loyalty card; keep the test case out of 'ready' until then. |
| [TC-020990](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L314) (BR1_TS11_TC67) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-020990.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020990.json) | `format_synthesis` | Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then. |
| [TC-021165](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L545) (BR1_TS13_TC67) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-021165.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021165.json) | `format_synthesis` | Client to supply a tokenized card and its token/cryptogram data; keep the test case out of 'ready' until then. |
| [TC-022300](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L29) (BR1_TS2_TC9) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022300.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022300.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-022320](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L53) (BR1_TS3_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022320.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022320.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-022342](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L79) (BR1_TS4_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022342.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022342.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-022362](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L103) (BR1_TS5_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022362.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022362.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-022382](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L127) (BR1_TS6_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022382.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022382.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-022402](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L151) (BR1_TS7_TC6) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022402.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022402.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-022423](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L176) (BR1_TS8_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-022423.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022423.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| … | | 53 more in the sheet (Findings, filter on I-06) | | | |

Scenarios: BR1_TS15 (8), BR1_TS13 (7), BR1_TS14 (7), BR1_TS16 (7), BR1_TS17 (7), BR1_TS11 (6), BR1_TS12 (6), BR1_TS18 (6), BR1_TS7 (2), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1) …

**How to check by hand:** In test_cases.jsonl the segment fields have method format_synthesis / spec_tlv_composite.

**Fix:** Client to supply the data listed in the finding; keep the test case out of 'ready' until then.

### 6.7 I-07 - Decline trigger needed: the request is identical to the approval test

**41 test cases, 41 findings, 41 test data files** - kind: Awaiting client input / run-time value

Rules: `X_SAME_REQUEST_NEEDS_TRIGGER` (41).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST_NEEDS_TRIGGER` (TC-023324) - Apart from the Sequence Number the message is identical to TC-020705 (all segments compared). TC-020705 expects 0; this test expects 1. The test case asks for a decline trigger in open_items.
- `X_SAME_REQUEST_NEEDS_TRIGGER` (TC-023992) - Apart from the Sequence Number the message is identical to TC-022995 (all segments compared). TC-022995 expects 0/2; this test expects 1. The test case asks for a decline trigger in open_items.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023324](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L24) (BR1_TS1_TC30) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023324.json vs TD-TC-020705.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023992](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1300) (BR1_TS21_TC9) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023992.json vs TD-TC-022995.json | `expected 1 vs 0/2` | Supply the decline trigger so the request differs. |
| [TC-023343](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L48) (BR1_TS2_TC28) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023343.json vs TD-TC-020729.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023363](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L73) (BR1_TS3_TC25) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023363.json vs TD-TC-020753.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023383](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L98) (BR1_TS4_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023383.json vs TD-TC-020779.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023402](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L122) (BR1_TS5_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023402.json vs TD-TC-020803.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023421](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L146) (BR1_TS6_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023421.json vs TD-TC-020827.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023441](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L171) (BR1_TS7_TC26) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023441.json vs TD-TC-020851.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-023460](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L195) (BR1_TS8_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023460.json vs TD-TC-020876.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-020971](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L295) (BR1_TS11_TC48) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-020971.json vs TD-TC-018695.json | `expected 1 vs 2` | Supply the decline trigger so the request differs. |
| [TC-023540](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L351) (BR1_TS11_TC104) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-023540.json vs TD-TC-018695.json | `expected 1 vs 2` | Supply the decline trigger so the request differs. |
| [TC-021057](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L409) (BR1_TS12_TC48) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-021057.json vs TD-TC-018809.json | `expected 1 vs 2` | Supply the decline trigger so the request differs. |
| … | | 29 more in the sheet (Findings, filter on I-07) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1) …

**How to check by hand:** open_items asks for a decline test card / simulator trigger.

**Fix:** Client to supply the decline trigger; until then the test sends the approval test's request.

### 6.8 I-08 - No test data generated for a host test case

**14 test cases, 14 findings** - kind: Awaiting client input / run-time value

Rules: `X_NO_TEST_DATA` (14).

What is wrong (one example per kind of problem):

- `X_NO_TEST_DATA` (TC-021894) - The test case sends a message to BUYPASS (3) but has no test data file (status blocked_no_test_data).
- `X_NO_TEST_DATA` (TC-024318) - The test case sends a message to BUYPASS (EMV Financial Transaction Request) but has no test data file (status blocked_no_test_data).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-021894](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1435) (BR1_TS58_TC41) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-024318](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1802) (BR1_TS1_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-021919](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1460) (BR1_TS59_TC6) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-021934](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1475) (BR1_TS60_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-021964](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1505) (BR1_TS61_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-021980](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1521) (BR1_TS62_TC7) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-021995](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1536) (BR1_TS63_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-022010](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1551) (BR1_TS64_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-022055](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1623) (BR1_TS77_TC39) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-022081](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1649) (BR1_TS78_TC6) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-022096](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1664) (BR1_TS79_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-022127](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1695) (BR1_TS80_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| … | | 2 more in the sheet (Findings, filter on I-08) | | | |

Scenarios: BR1_TS58 (1), BR1_TS59 (1), BR1_TS60 (1), BR1_TS61 (1), BR1_TS62 (1), BR1_TS63 (1), BR1_TS64 (1), BR1_TS77 (1), BR1_TS78 (1), BR1_TS79 (1), BR1_TS80 (1), BR1_TS81 (1), BR1_TS1 (1), BR1_TS51 (1)

**How to check by hand:** test_data_file is empty in test_cases.jsonl; status blocked_no_test_data.

**Fix:** Generate the test data when the missing input is available.

### 6.9 I-09 - Segment 100 value awaited from the client (<AWAITING:...>)

**2 test cases, 2 findings, 2 test data files** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_VALUE` (2).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_AWAITING_VALUE` (TC-024334) - The field holds '<AWAITING:AccountNumber>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-024334](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1818) (BR1_TS72_TC1) | 1 | `X_AWAITING_VALUE` | [TD-TC-024334.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024334.json#L11) | `<AWAITING:AccountNumber>` | Client to supply the value (for Account Number: a MasterCard test card). |
| [TC-024343](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1827) (BR1_TS77_TC1) | 1 | `X_AWAITING_VALUE` | [TD-TC-024343.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024343.json#L11) | `<AWAITING:AccountNumber>` | Client to supply the value (for Account Number: a MasterCard test card). |

Scenarios: BR1_TS72 (1), BR1_TS77 (1)

**How to check by hand:** The field holds <AWAITING:...>; the test case field has method awaiting_client_value.

**Fix:** Client to supply the value (for Account Number: a MasterCard test card).

## 7. Points to confirm with BUYPASS / SME

### 7.1 C-06 - Time-out Reversal sent after a step that is expected to be answered

**64 test cases, 64 findings, 64 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_TOR_AFTER_RESPONSE` (64).

ATL105:

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR"

What is wrong (one example per kind of problem):

- `X_TOR_AFTER_RESPONSE` (TC-020708) - ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0, so the device would not send a TOR.
- `X_TOR_AFTER_RESPONSE` (TC-022446) - ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0/2, so the device would not send a TOR.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-022446](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L199) (BR1_TS9_TC4) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0/2` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023309](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L9) (BR1_TS1_TC15) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023310](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L10) (BR1_TS1_TC16) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023316](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L16) (BR1_TS1_TC22) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023323](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L23) (BR1_TS1_TC29) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-020732](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L28) (BR1_TS2_TC8) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023328](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L33) (BR1_TS2_TC13) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023329](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L34) (BR1_TS2_TC14) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023335](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L40) (BR1_TS2_TC20) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-023342](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L47) (BR1_TS2_TC27) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-020756](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L52) (BR1_TS3_TC4) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| … | | 52 more in the sheet (Findings, filter on C-06) | | | |

Scenarios: BR1_TS1 (5), BR1_TS2 (5), BR1_TS3 (5), BR1_TS4 (5), BR1_TS5 (5), BR1_TS6 (5), BR1_TS7 (5), BR1_TS8 (5), BR1_TS9 (5), BR1_TS10 (5), BR1_TS51 (2), BR1_TS72 (2), BR1_TS82 (2), BR1_TS96 (2), BR1_TS103 (2) …

**How to check by hand:** Read tx_legs: the original step expects a Response Code and has no 'no response' harness directive, yet the next step is a Time-out Reversal.

**Fix:** Make the original step a fault-injected 'no response' (or a late response the harness ignores).

### 7.2 C-07 - 'Different terminal' negative uses a generated terminal that is not set up at BUYPASS

**40 test cases, 40 findings, 40 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_RANDOM_TERMINAL` (40).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `X_NEG_RANDOM_TERMINAL` (TC-020707) - Step 2 uses the generated Terminal Identifier 'XT47320042968001199580' (method deliberate_violation_missing_carry). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.
- `X_NEG_RANDOM_TERMINAL` (TC-024321) - Step 1 uses the generated Terminal Identifier '' (method deliberate_violation_empty). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.
- `X_NEG_RANDOM_TERMINAL` (TC-024322) - Step 1 uses the generated Terminal Identifier 'AAAAAAAAAAAAAAAAAAAAAAA' (method deliberate_violation_length). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020707](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L3) (BR1_TS1_TC9) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020707.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020707.step2.reversal.json#L9) | `XT47320042968001199580` | Use a second real terminal of the same merchant. |
| [TC-024321](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1805) (BR1_TS2_TC2) | 1 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-024321.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024321.json#L9) | (empty) | Use a second real terminal of the same merchant. |
| [TC-024322](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1806) (BR1_TS1_TC1) | 1 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-024322.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024322.json#L9) | `AAAAAAAAAAAAAAAAAAAAAAA` | Use a second real terminal of the same merchant. |
| [TC-020708](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L4) (BR1_TS1_TC10) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020708.step2.timeout-reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020708.step2.timeout-reversal.json#L9) | `CC32845560270643250407` | Use a second real terminal of the same merchant. |
| [TC-020731](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L27) (BR1_TS2_TC7) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020731.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020731.step2.reversal.json#L9) | `OF64126685792914682812` | Use a second real terminal of the same merchant. |
| [TC-020732](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L28) (BR1_TS2_TC8) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020732.step2.timeout-reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020732.step2.timeout-reversal.json#L9) | `VU22033661055981402050` | Use a second real terminal of the same merchant. |
| [TC-020755](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L51) (BR1_TS3_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020755.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020755.step2.reversal.json#L9) | `AJ01230409610867043426` | Use a second real terminal of the same merchant. |
| [TC-020756](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L52) (BR1_TS3_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020756.step2.timeout-reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020756.step2.timeout-reversal.json#L9) | `UB29258513570598510183` | Use a second real terminal of the same merchant. |
| [TC-020781](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L77) (BR1_TS4_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020781.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020781.step2.reversal.json#L9) | `BJ01701389398609013256` | Use a second real terminal of the same merchant. |
| [TC-020782](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L78) (BR1_TS4_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020782.step2.timeout-reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020782.step2.timeout-reversal.json#L9) | `CN55143794088586247460` | Use a second real terminal of the same merchant. |
| [TC-020805](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L101) (BR1_TS5_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020805.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020805.step2.reversal.json#L9) | `VS24671841985066391107` | Use a second real terminal of the same merchant. |
| [TC-020806](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L102) (BR1_TS5_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-020806.step2.timeout-reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020806.step2.timeout-reversal.json#L9) | `QL66203054504417704598` | Use a second real terminal of the same merchant. |
| … | | 28 more in the sheet (Findings, filter on C-07) | | | |

Scenarios: BR1_TS1 (3), BR1_TS2 (3), BR1_TS3 (2), BR1_TS4 (2), BR1_TS5 (2), BR1_TS6 (2), BR1_TS7 (2), BR1_TS8 (2), BR1_TS9 (2), BR1_TS10 (2), BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1) …

**How to check by hand:** Read the follow-on step's Terminal Identifier: method deliberate_violation_missing_carry, a generated value.

**Fix:** Use a second real terminal of the same merchant so the decline proves the same-device rule.

### 7.3 C-08 - Same request as another test case (difference only on the device / response side)

**226 test cases, 226 findings, 226 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SAME_REQUEST` (226).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST` (TC-023358) - Apart from the Sequence Number the message is identical to TC-020753 (all segments compared). TC-020753 expects 0; this test expects 0. What makes the two tests different is not in the message sent to BUYPASS.
- `X_SAME_REQUEST` (TC-023477) - Apart from the Sequence Number the message is identical to TC-022443 (all segments compared). TC-022443 expects 0/2; this test expects 0/2. What makes the two tests different is not in the message sent to BUYPASS.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023358](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L68) (BR1_TS3_TC20) | 1 | `X_SAME_REQUEST` | TD-TC-023358.json vs TD-TC-020753.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023477](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L216) (BR1_TS9_TC21) | 1 | `X_SAME_REQUEST` | TD-TC-023477.json vs TD-TC-022443.json | `expected 0/2 vs 0/2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023378](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L93) (BR1_TS4_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-023378.json vs TD-TC-020779.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023397](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L117) (BR1_TS5_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-023397.json vs TD-TC-020803.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023416](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L141) (BR1_TS6_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-023416.json vs TD-TC-020827.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023436](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L166) (BR1_TS7_TC21) | 1 | `X_SAME_REQUEST` | TD-TC-023436.json vs TD-TC-020851.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023455](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L190) (BR1_TS8_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-023455.json vs TD-TC-020876.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-023502](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L245) (BR1_TS10_TC16) | 1 | `X_SAME_REQUEST` | TD-TC-023502.json vs TD-TC-022476.json | `expected 0/2 vs 0/2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-018703](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L260) (BR1_TS11_TC13) | 1 | `X_SAME_REQUEST` | TD-TC-018703.json vs TD-TC-018695.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-020957](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L281) (BR1_TS11_TC34) | 1 | `X_SAME_REQUEST` | TD-TC-020957.json vs TD-TC-018695.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-020958](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L282) (BR1_TS11_TC35) | 1 | `X_SAME_REQUEST` | TD-TC-020958.json vs TD-TC-018695.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-020959](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L283) (BR1_TS11_TC36) | 1 | `X_SAME_REQUEST` | TD-TC-020959.json vs TD-TC-018695.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| … | | 214 more in the sheet (Findings, filter on C-08) | | | |

Scenarios: BR1_TS16 (22), BR1_TS13 (20), BR1_TS17 (19), BR1_TS11 (18), BR1_TS12 (18), BR1_TS15 (18), BR1_TS18 (18), BR1_TS14 (17), BR1_TS58 (9), BR1_TS77 (9), BR1_TS62 (5), BR1_TS59 (4), BR1_TS60 (4), BR1_TS61 (4), BR1_TS63 (4) …

**How to check by hand:** Compare both test data files: identical apart from the Sequence Number.

**Fix:** Check the difference on the device or in the response; optionally use another card.

### 7.4 C-09 - Segment sent although ATL105 scopes it to other card programs

**10 test cases, 10 findings, 10 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_SCOPE` (10).

ATL105:

- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "The Enhanced Fleet Request Segment contains fleet data that applies to the enhanced fleet offerings such as Wex OTR, Comdata, Voyager EMV, and Visa Fleet 2.0."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data."
- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV transactions."

What is wrong (one example per kind of problem):

- `X_SEGMENT_SCOPE` (TC-023424) - ATL105 describes Segment 145 as fleet data for enhanced fleet offerings (WEX OTR, Comdata, Voyager EMV, Visa Fleet 2.0). This test uses a MasterCard Credit card (card type 020), which is not a fleet card.
- `X_SEGMENT_SCOPE` (TC-024347) - ATL105 describes Segment 101 as fleet data, sent only on transactions requiring fleet data. This test uses a MasterCard Credit card (card type 020), which is not a fleet card.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L154) (BR1_TS7_TC9) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-023424.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023424.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-024347](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1831) (BR1_TS72_TC2) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-024347.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024347.json) | `Segment 101` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-020992](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L316) (BR1_TS11_TC69) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-020992.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020992.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021078](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L430) (BR1_TS12_TC69) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021078.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021078.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021166](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L546) (BR1_TS13_TC68) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021166.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021166.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021339](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L747) (BR1_TS14_TC64) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021339.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021339.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021422](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L858) (BR1_TS15_TC66) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021422.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021422.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021512](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L976) (BR1_TS16_TC68) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021512.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021512.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021595](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1094) (BR1_TS17_TC73) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021595.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021595.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-021682](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1209) (BR1_TS18_TC63) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-021682.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-021682.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |

Scenarios: BR1_TS7 (1), BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS72 (1)

**How to check by hand:** Read the segment description in section 11.1 / 12.x and compare with the card program (MasterCard Credit, card type 020).

**Fix:** Confirm with BUYPASS or drop the segment from MasterCard Credit tests.

### 7.5 S-01 - Expiry format for stored credentials (entry mode 10) not stated

**196 test cases, 196 findings** - kind: To confirm with BUYPASS / SME

Rules: `COF_EXPIRY_FORMAT` (196).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."

What is wrong (one example per kind of problem):

- `COF_EXPIRY_FORMAT` (TC-020851) - The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-020851](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L147) (BR1_TS7_TC2) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-020852](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L148) (BR1_TS7_TC3) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1131` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-020853](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L149) (BR1_TS7_TC4) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0828` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-020854](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L150) (BR1_TS7_TC5) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0632` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-022402](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L151) (BR1_TS7_TC6) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023422](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L152) (BR1_TS7_TC7) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023423](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L153) (BR1_TS7_TC8) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023424](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L154) (BR1_TS7_TC9) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023425](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L155) (BR1_TS7_TC10) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0328` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023426](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L156) (BR1_TS7_TC11) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0532` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023427](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L157) (BR1_TS7_TC12) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-023429](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L159) (BR1_TS7_TC14) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1028` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| … | | 184 more in the sheet (Findings, filter on S-01) | | | |

Scenarios: BR1_TS17 (125), BR1_TS7 (25), BR1_TS10 (21), BR1_TS20 (11), BR1_TS27 (10), BR1_TS35 (4)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.6 S-02 - Totals: Authorization Only (AO) bucket after the flow

**10 test cases, 10 findings, 10 test data files** - kind: To confirm with BUYPASS / SME

Rules: `TOTALS_AO_BUCKET` (10).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."

What is wrong (one example per kind of problem):

- `TOTALS_AO_BUCKET` (TC-023514) - Section 12.6 lists Preauthorization (AO) as bucket 4, and the grand total leaves the AO bucket out. This flow has an approved Authorization Only, but only the CC bucket is sent. ATL105 says cancellations subtract from the AO bucket; it does not say whether a completed authorization stays in it.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023514](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L325) (BR1_TS11_TC78) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023514.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023514.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023560](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L439) (BR1_TS12_TC78) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023560.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023560.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023610](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L559) (BR1_TS13_TC81) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023610.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023610.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023740](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L756) (BR1_TS14_TC73) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023740.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023740.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023784](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L868) (BR1_TS15_TC76) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023784.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023784.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023830](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L985) (BR1_TS16_TC77) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023830.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023830.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023874](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1105) (BR1_TS17_TC84) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023874.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023874.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023921](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1218) (BR1_TS18_TC72) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023921.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023921.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023962](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1263) (BR1_TS19_TC8) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023962.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023962.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-023982](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1287) (BR1_TS20_TC7) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-023982.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023982.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |

Scenarios: BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS19 (1), BR1_TS20 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.7 S-03 - Segment length: ATL105 Field Separator rule of the segment is unclear

**52 test cases, 52 findings, 52 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_LENGTH_UNCLEAR` (52).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "There is a Field Separator between Field Nos. 1 and 2."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "Field Separators are not there between the elements in Network Token Data Layouts."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"

What is wrong (one example per kind of problem):

- `X_SEGMENT_LENGTH_UNCLEAR` (TC-022280) - '051' assumes a Field Separator after every field (051). ATL105 reads differently for this segment - Field Separator only between Fields 1 and 2 (literal reading of p.310): 049; Field Separator after Fields 1 and 2, none after Field 3: 050.
- `X_SEGMENT_LENGTH_UNCLEAR` (TC-020972) - '120' assumes a Field Separator after every field (120). ATL105 reads differently for this segment - Field Separators up to Field 13 only (literal reading of p.238): 118.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022280.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022280.json#L61) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-020972](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L296) (BR1_TS11_TC49) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-020972.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020972.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022300](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L29) (BR1_TS2_TC9) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022300.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022300.json#L61) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022320](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L53) (BR1_TS3_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022320.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022320.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022342](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L79) (BR1_TS4_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022342.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022342.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022362](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L103) (BR1_TS5_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022362.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022362.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022382](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L127) (BR1_TS6_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022382.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022382.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022402](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L151) (BR1_TS7_TC6) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022402.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022402.json#L65) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022423](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L176) (BR1_TS8_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022423.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022423.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-020977](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L301) (BR1_TS11_TC54) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-020977.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020977.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-020980](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L304) (BR1_TS11_TC57) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-020980.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020980.json#L7) | `121` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-022497](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L317) (BR1_TS11_TC70) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-022497.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022497.json#L61) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| … | | 40 more in the sheet (Findings, filter on S-03) | | | |

Scenarios: BR1_TS11 (6), BR1_TS15 (6), BR1_TS12 (5), BR1_TS13 (5), BR1_TS14 (5), BR1_TS16 (5), BR1_TS17 (5), BR1_TS18 (5), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1) …

**How to check by hand:** Recalculate the length under both readings shown in the finding.

**Fix:** Confirm with BUYPASS.

### 7.8 S-04 - Network Token Input Indicator 'Y' while Element 2 carries a clear card number

**26 test cases, 26 findings, 26 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_TOKEN_INDICATOR` (26).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN"
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_TOKEN_INDICATOR` (TC-022280) - Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number 515864…7836 and the token is only in table 001. ATL105 does not say which field carries the token when the indicator is Y.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-022280](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC11) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022280.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022280.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022300](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L29) (BR1_TS2_TC9) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022300.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022300.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022320](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L53) (BR1_TS3_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022320.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022320.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022342](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L79) (BR1_TS4_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022342.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022342.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022362](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L103) (BR1_TS5_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022362.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022362.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022382](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L127) (BR1_TS6_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022382.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022382.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022402](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L151) (BR1_TS7_TC6) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022402.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022402.json#L66) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022423](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L176) (BR1_TS8_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022423.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022423.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022497](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L317) (BR1_TS11_TC70) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022497.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022497.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-023530](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L341) (BR1_TS11_TC94) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-023530.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023530.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-022545](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L431) (BR1_TS12_TC70) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-022545.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022545.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-023576](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L455) (BR1_TS12_TC94) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-023576.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023576.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| … | | 14 more in the sheet (Findings, filter on S-04) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1) …

**How to check by hand:** Parse Network Token Data table 004.

**Fix:** Confirm with BUYPASS which field carries the token when the indicator is Y.

### 7.9 S-05 - Other points to confirm

**15 test cases, 15 findings** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_PREMISE_CONFIRM` (15).

ATL105:

- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"
- Appendix G. Valid Transaction Type Codes, PDF p.546 (printed Appendix G-2): "A Account Verification Account verification"
- Appendix G. Valid Transaction Type Codes, PDF p.546 (printed Appendix G-2): "S Cancellation Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction."
- Section 12.19 NFC Payment Tokenization Data Segment, PDF p.267 (printed 12-50): "It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "When a merchant is entitled to use both First Data provisioned Network Tokens and Auth Optimization service, the merchant is required to send the same and exact information from the original transaction request in all the follow-up transactions"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE_CONFIRM` (TC-023734) - Account Verification is not in the Section 10.1.2 list for credit, but Appendix G defines Transaction Type A; confirm.
- `X_NEG_PREMISE_CONFIRM` (TC-021889) - Appendix G defines Cancellation (S) for credit Authorization Only transactions; the cited text does not forbid it here.
- `X_NEG_PREMISE_CONFIRM` (TC-024319) - p.267 requires Segment 123 only on transactions involving tokenized data (MasterCard Token, DSRP or Visa TAVV data). This test sends a card number without any token data, so leaving the segment out may be correct and BUYPASS may approve.
- `X_NEG_PREMISE_CONFIRM` (TC-024320) - p.310 requires the same Segment 153 data only in the follow-up transactions of a merchant entitled to both First Data Network Tokens and Auth Optimization. This test sends an original transaction without any network token data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023734](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L683) (BR1_TS13_TC205) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Account Verification… -> declined` | Confirm with BUYPASS. |
| [TC-021889](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1430) (BR1_TS58_TC36) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-024319](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1803) (BR1_TS17_TC1) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `NFC Payment Tokenization Data Segment (s… requires it` | Confirm with BUYPASS, or send a transaction for which the segment is required. |
| [TC-024320](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1804) (BR1_TS1_TC6) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Network Token Data Request (segment 153)… requires it` | Confirm with BUYPASS, or send a transaction for which the segment is required. |
| [TC-021918](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1459) (BR1_TS59_TC5) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-021933](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1474) (BR1_TS60_TC4) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-021963](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1504) (BR1_TS61_TC4) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-021979](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1520) (BR1_TS62_TC6) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-021994](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1535) (BR1_TS63_TC4) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-022009](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1550) (BR1_TS64_TC4) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-022050](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1618) (BR1_TS77_TC34) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-022080](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1648) (BR1_TS78_TC5) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| … | | 3 more in the sheet (Findings, filter on S-05) | | | |

Scenarios: BR1_TS13 (1), BR1_TS58 (1), BR1_TS59 (1), BR1_TS60 (1), BR1_TS61 (1), BR1_TS62 (1), BR1_TS63 (1), BR1_TS64 (1), BR1_TS77 (1), BR1_TS78 (1), BR1_TS79 (1), BR1_TS80 (1), BR1_TS81 (1), BR1_TS17 (1), BR1_TS1 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS / SME.

### 7.10 S-06 - Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts

**8 test cases, 8 findings, 8 test data files** - kind: To confirm with BUYPASS / SME

Rules: `TOTALS_NEGATIVE_NET` (8).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places"

What is wrong (one example per kind of problem):

- `TOTALS_NEGATIVE_NET` (TC-023989) - Section 9.2: a Merchandise Return increases the count and decreases the dollar amount, so the card bucket nets to count 00001 and amount -$15.00. Card Type Total Amount (Element 15) is a fixed-length unsigned 8-digit amount and ATL105 does not say how a negative net amount is sent; the file sends 00000000.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-023989](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1297) (BR1_TS21_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-023989.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023989.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-023995](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1306) (BR1_TS22_TC10) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-023995.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-023995.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-024001](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1315) (BR1_TS23_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-024001.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024001.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-024007](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1324) (BR1_TS24_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-024007.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024007.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-024013](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1333) (BR1_TS25_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-024013.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024013.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-024019](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1342) (BR1_TS26_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-024019.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024019.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-024026](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1352) (BR1_TS27_TC7) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-024026.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024026.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-024032](../mastercard_credit_new%20-%20Copy%20%282%29/test_cases/test_cases.jsonl#L1361) (BR1_TS28_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-024032.step2.totals.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-024032.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |

Scenarios: BR1_TS21 (1), BR1_TS22 (1), BR1_TS23 (1), BR1_TS24 (1), BR1_TS25 (1), BR1_TS26 (1), BR1_TS27 (1), BR1_TS28 (1)

**How to check by hand:** Open the Totals Request file: count 00001 is right (a return increases the count), the amount nets to minus the return amount, and the file sends 00000000.

**Fix:** Confirm with BUYPASS how a negative net amount is reported in the Totals Request.

## 8. Negative tests - intended violations (correct)

150 negative test cases break their rule as designed (157 findings). They are not defects; check that each test breaks only that rule.

| Rule | Findings | Example |
|---|---:|---|
| `FIELD_EMPTY` | 46 | TC-023466 step 1: Approval Number is empty - There is no original transaction, so there is no Approval Number to copy. |
| `LINK_TERMINALIDENTIFIER` | 38 | TC-020707 step 2: Step 2: same terminal as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined |
| `LINK_SEQUENCENUMBER` | 28 | TC-020706 step 2: Step 2: same sequence number as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined |
| `TABLE_005` | 13 | TC-023485 step 1: Table 005 value is not valid - Single rule broken: Mail/Phone Purchase with entry mode 05 (EMV Contact Chip, not supported) -> declined |
| `X_FIELD_FORMAT` | 5 | TC-024312 step 1: Loyalty Card Data Segment: Loyalty Track 2 Data breaks its Chapter 13 format - Loyalty Track 2 Data one byte over its documented maximum length (38) |
| `SEG130_MISSING` | 4 | TC-023485 step 1: The EMV Request Data Segment (130) is missing - Single rule broken: Mail/Phone Purchase with entry mode 05 (EMV Contact Chip, not supported) -> declined |
| `FIELD_189` | 3 | TC-024323 step 1: EMV Chip Data Length (Element 189) does not match the chip data - Required field EMV Chip Data Length sent empty |
| `FIELD_121` | 2 | TC-024316 step 1: Partial Approval Indicator is not valid - Partial Approval Indicator value not in its documented valid values |
| `CARD_DATA_MISSING` | 2 | TC-024335 step 1: The card data is empty (Account Number) - Required field Account Number omitted |
| `FIELD_41` | 2 | TC-024339 step 1: Fuel Purchase Amount is not valid - Fuel Purchase Amount one byte over its documented maximum length (8) |
| `FIELD_44` | 2 | TC-024345 step 1: Information Byte is not valid - Information Byte one byte over its documented maximum length (1) |
| `EMV_CSN_WIDTH` | 2 | TC-024348 step 1: EMV Card Sequence Number (Element 188) is 3 bytes instead of 3 - The deliberately broken EMV Chip Data has no readable Tag 5F34, so Element 188 cannot be checked against it. |
| `EMV_CHIP_DATA_FORMAT` | 2 | TC-024348 step 1: EMV Chip Data (Element 190) is not valid - EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field spe |
| `X_VI_TABLE_ID` | 2 | TC-024356 step 1: Variable Information Indicator '' is not an ATL105 table ID - Required field Variable Information Indicator sent empty |
| `FIELD_102` | 1 | TC-024322 step 1: Terminal Identifier is not valid - Terminal Identifier one byte over its documented maximum length (22) |
| `FIELD_78` | 1 | TC-024334 step 1: Prompt Code is not valid - Prompt Code one byte over its documented maximum length (4) |
| `FIELD_2` | 1 | TC-024342 step 1: Account Number is not valid - Account Number one byte over its documented maximum length (24) |
| `EMV_ADDL_INFO_CONTENT` | 1 | TC-024346 step 1: EMV Additional Information (Element 118) does not contain EMV Table Data - Additional Information one byte over its documented maximum length (984) |
| `FIELD_12` | 1 | TC-024350 step 1: Card Discretionary Block Data is not valid - Card Discretionary Block Data one byte over its documented maximum length (51) |
| `SEG111_LENGTH` | 1 | TC-024355 step 1: Segment 111 length is wrong - The declared Segment Length matches the content; the deliberately over-long field makes the segment longer than its 3-digit Segment Length can express. |

## 9. AI validator rules that did not apply to this run (value checked - correct)

| Rule | Findings | Test cases | Why the value is correct |
|---|---:|---:|---|
| `FIELD_12` | 12 | 12 | This step completes the Authorization Only of step 1 (preauthorized Purchase/Capture). Credit completions send Account Number = YYMM (Section 10.1.8.2), which is what the file has; the AI rule treated the step as a new purchase. |
| `FIELD_78` | 11 | 11 | The remaining balance after a partially approved Authorization Only is a new Authorization Only on the second card (Transaction Type 3, approval code 2). ATL105 supports partial approval for POS Authorization Only (Section 10.1.2.1); the AI rule only knew split tender after a purchase. |
| `RESPONSE_CODE` | 11 | 11 | The remaining balance after a partially approved Authorization Only is a new Authorization Only on the second card (Transaction Type 3, approval code 2). ATL105 supports partial approval for POS Authorization Only (Section 10.1.2.1); the AI rule only knew split tender after a purchase. |
| `EMV_CASHBACK_AMOUNTS` | 2 | 2 | The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization Only. The real problem of this test - optional fields filled without their condition - is D-02. |
| `TABLE_005` | 2 | 2 | A completion of an original read with full track data (entry mode 90) uses PAN Entry Mode 02 (Appendix J, p.638); the AI rule treated the step as a new magnetic-stripe purchase. |

Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator did not read segments 101-153; section 2 point 2 now checks them).

Run-wide checks: 535 single-step test cases send the same request as an earlier test case (all segments compared); 1,665 new transactions checked for a unique Sequence Number per terminal, 0 duplicate(s).

## 10. Result per scenario

| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |
|---|---|---|---:|---:|---:|---:|---:|---:|---|
| BR1_TS1 | Purchase/Capture – EMV Contact Chip | GENERAL | 30 | 5 | 25 | 0 | 0 | 0 | D-13 (4), D-09 (2) |
| BR1_TS2 | Purchase/Capture – EMV Contactless Chip | GENERAL | 28 | 4 | 24 | 0 | 0 | 0 | D-13 (3), D-09 (2) |
| BR1_TS3 | Purchase/Capture – Magnetic Stripe | GENERAL | 26 | 0 | 24 | 0 | 0 | 2 | - |
| BR1_TS4 | Purchase/Capture – Contactless Magnetic Stripe | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS5 | Purchase/Capture – Manual / Keyed | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS6 | Purchase/Capture – Chip Fallback | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS7 | Purchase/Capture – Credential on File | CARD_NOT_PRESENT | 26 | 2 | 24 | 0 | 0 | 0 | D-03 (3), D-09 (2), C-02 (1) |
| BR1_TS8 | Purchase/Capture – Contactless Mobile Commerce Device | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS9 | Mail/Phone Purchase – Manual / Keyed | CARD_NOT_PRESENT | 34 | 1 | 26 | 0 | 0 | 7 | C-01 (1) |
| BR1_TS10 | Mail/Phone Purchase – Credential on File | CARD_NOT_PRESENT | 22 | 1 | 21 | 0 | 0 | 0 | C-01 (1) |
| BR1_TS11 | Authorization Only – EMV Contact Chip | GENERAL | 118 | 15 | 103 | 0 | 0 | 0 | C-02 (6), D-02 (4), C-01 (3) |
| BR1_TS12 | Authorization Only – EMV Contactless Chip | GENERAL | 118 | 15 | 103 | 0 | 0 | 0 | C-02 (4), D-02 (4), D-13 (4) |
| BR1_TS13 | Authorization Only – Magnetic Stripe | GENERAL | 205 | 13 | 104 | 0 | 0 | 88 | C-02 (6), D-02 (4), C-01 (3) |
| BR1_TS14 | Authorization Only – Contactless Magnetic Stripe | GENERAL | 111 | 11 | 100 | 0 | 0 | 0 | C-02 (4), D-02 (4), C-01 (3) |
| BR1_TS15 | Authorization Only – Manual / Keyed | GENERAL | 116 | 13 | 103 | 0 | 0 | 0 | C-02 (5), D-02 (4), C-01 (3) |
| BR1_TS16 | Authorization Only – Chip Fallback | GENERAL | 115 | 11 | 104 | 0 | 0 | 0 | C-02 (4), D-02 (4), C-01 (3) |
| BR1_TS17 | Authorization Only – Credential on File | CARD_NOT_PRESENT | 125 | 13 | 112 | 0 | 0 | 0 | C-02 (5), D-02 (4), D-09 (4) |
| BR1_TS18 | Authorization Only – Contactless Mobile Commerce Device | GENERAL | 110 | 11 | 99 | 0 | 0 | 0 | C-02 (4), D-02 (4), C-01 (3) |
| BR1_TS19 | Mail/Phone Authorization Only – Manual / Keyed | CARD_NOT_PRESENT | 25 | 2 | 16 | 0 | 0 | 7 | D-01 (2) |
| BR1_TS20 | Mail/Phone Authorization Only – Credential on File | CARD_NOT_PRESENT | 11 | 2 | 9 | 0 | 0 | 0 | D-01 (2) |
| BR1_TS21 | Merchandise Return – EMV Contact Chip | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS22 | Merchandise Return – EMV Contactless Chip | GENERAL | 13 | 5 | 8 | 0 | 0 | 0 | D-13 (3), D-01 (1), D-09 (1) |
| BR1_TS23 | Merchandise Return – Magnetic Stripe | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS24 | Merchandise Return – Contactless Magnetic Stripe | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS25 | Merchandise Return – Manual / Keyed | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS26 | Merchandise Return – Chip Fallback | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS27 | Merchandise Return – Credential on File | CARD_NOT_PRESENT | 10 | 1 | 9 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS28 | Merchandise Return – Contactless Mobile Commerce Device | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS29 | Balance Inquiry – EMV Contact Chip | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS30 | Balance Inquiry – EMV Contactless Chip | GENERAL | 2 | 1 | 1 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS31 | Balance Inquiry – Magnetic Stripe | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS32 | Balance Inquiry – Contactless Magnetic Stripe | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS33 | Balance Inquiry – Manual / Keyed | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS34 | Balance Inquiry – Chip Fallback | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS35 | Balance Inquiry – Credential on File | CARD_NOT_PRESENT | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS36 | Balance Inquiry – Contactless Mobile Commerce Device | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS37 | Purchase/Capture – EMV Contact Chip | HOSPITALITY-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS38 | Purchase/Capture – EMV Contactless Chip | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS39 | Purchase/Capture – Magnetic Stripe | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS40 | Purchase/Capture – Contactless Magnetic Stripe | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS41 | Purchase/Capture – Manual / Keyed | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS42 | Purchase/Capture – Chip Fallback | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS43 | Purchase/Capture – Contactless Mobile Commerce Device | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS44 | Authorization Only – EMV Contact Chip | HOSPITALITY-ATTENDED | 29 | 0 | 29 | 0 | 0 | 0 | - |
| BR1_TS45 | Authorization Only – EMV Contactless Chip | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS46 | Authorization Only – Magnetic Stripe | HOSPITALITY-ATTENDED | 2 | 0 | 0 | 0 | 0 | 2 | - |
| BR1_TS47 | Authorization Only – Contactless Magnetic Stripe | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS48 | Authorization Only – Manual / Keyed | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS49 | Authorization Only – Chip Fallback | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS50 | Authorization Only – Contactless Mobile Commerce Device | HOSPITALITY-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS51 | Purchase/Capture – EMV Contact Chip | PETROLEUM-ATTENDED | 13 | 2 | 11 | 0 | 0 | 0 | D-13 (2) |
| BR1_TS52 | Purchase/Capture – EMV Contactless Chip | PETROLEUM-ATTENDED | 3 | 1 | 2 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS53 | Purchase/Capture – Magnetic Stripe | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS54 | Purchase/Capture – Contactless Magnetic Stripe | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS55 | Purchase/Capture – Manual / Keyed | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS56 | Purchase/Capture – Chip Fallback | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS57 | Purchase/Capture – Contactless Mobile Commerce Device | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS58 | Authorization Only – EMV Contact Chip | PETROLEUM-ATTENDED | 61 | 5 | 56 | 0 | 0 | 0 | C-02 (3), D-13 (3), D-02 (1) |
| BR1_TS59 | Authorization Only – EMV Contactless Chip | PETROLEUM-ATTENDED | 16 | 1 | 15 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS60 | Authorization Only – Magnetic Stripe | PETROLEUM-ATTENDED | 30 | 0 | 15 | 0 | 0 | 15 | - |
| BR1_TS61 | Authorization Only – Contactless Magnetic Stripe | PETROLEUM-ATTENDED | 15 | 0 | 15 | 0 | 0 | 0 | - |
| BR1_TS62 | Authorization Only – Manual / Keyed | PETROLEUM-ATTENDED | 17 | 0 | 17 | 0 | 0 | 0 | - |
| BR1_TS63 | Authorization Only – Chip Fallback | PETROLEUM-ATTENDED | 15 | 0 | 15 | 0 | 0 | 0 | - |
| BR1_TS64 | Authorization Only – Contactless Mobile Commerce Device | PETROLEUM-ATTENDED | 15 | 0 | 15 | 0 | 0 | 0 | - |
| BR1_TS65 | Merchandise Return – EMV Contact Chip | PETROLEUM-ATTENDED | 6 | 1 | 5 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS66 | Merchandise Return – EMV Contactless Chip | PETROLEUM-ATTENDED | 3 | 1 | 2 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS67 | Merchandise Return – Magnetic Stripe | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS68 | Merchandise Return – Contactless Magnetic Stripe | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS69 | Merchandise Return – Manual / Keyed | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS70 | Merchandise Return – Chip Fallback | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS71 | Merchandise Return – Contactless Mobile Commerce Device | PETROLEUM-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS72 | Purchase/Capture – EMV Contact Chip | PETROLEUM-UNATTENDED | 10 | 3 | 7 | 0 | 0 | 0 | D-13 (3) |
| BR1_TS73 | Purchase/Capture – EMV Contactless Chip | PETROLEUM-UNATTENDED | 4 | 4 | 0 | 0 | 0 | 0 | D-13 (4), D-15 (1) |
| BR1_TS74 | Purchase/Capture – Magnetic Stripe | PETROLEUM-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS75 | Purchase/Capture – Contactless Magnetic Stripe | PETROLEUM-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS76 | Purchase/Capture – Contactless Mobile Commerce Device | PETROLEUM-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS77 | Authorization Only – EMV Contact Chip | PETROLEUM-UNATTENDED | 60 | 3 | 57 | 0 | 0 | 0 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS78 | Authorization Only – EMV Contactless Chip | PETROLEUM-UNATTENDED | 16 | 1 | 15 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS79 | Authorization Only – Magnetic Stripe | PETROLEUM-UNATTENDED | 31 | 0 | 15 | 0 | 0 | 16 | - |
| BR1_TS80 | Authorization Only – Contactless Magnetic Stripe | PETROLEUM-UNATTENDED | 15 | 0 | 15 | 0 | 0 | 0 | - |
| BR1_TS81 | Authorization Only – Contactless Mobile Commerce Device | PETROLEUM-UNATTENDED | 15 | 0 | 15 | 0 | 0 | 0 | - |
| BR1_TS82 | Purchase/Capture – EMV Contact Chip | MCC_5542 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS83 | Purchase/Capture – EMV Contactless Chip | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS84 | Purchase/Capture – Magnetic Stripe | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS85 | Purchase/Capture – Contactless Magnetic Stripe | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS86 | Purchase/Capture – Manual / Keyed | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS87 | Purchase/Capture – Chip Fallback | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS88 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS89 | Purchase/Capture – EMV Contact Chip | MCC_5552 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS90 | Purchase/Capture – EMV Contactless Chip | MCC_5552 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS91 | Purchase/Capture – Magnetic Stripe | MCC_5552 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS92 | Purchase/Capture – Contactless Magnetic Stripe | MCC_5552 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS93 | Purchase/Capture – Manual / Keyed | MCC_5552 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS94 | Purchase/Capture – Chip Fallback | MCC_5552 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS95 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_5552 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS96 | Purchase/Capture – EMV Contact Chip | MCC_6051 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS97 | Purchase/Capture – EMV Contactless Chip | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS98 | Purchase/Capture – Magnetic Stripe | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS99 | Purchase/Capture – Contactless Magnetic Stripe | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS100 | Purchase/Capture – Manual / Keyed | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS101 | Purchase/Capture – Chip Fallback | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS102 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS103 | Purchase/Capture – EMV Contact Chip | MCC_6211 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS104 | Purchase/Capture – EMV Contactless Chip | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS105 | Purchase/Capture – Magnetic Stripe | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS106 | Purchase/Capture – Contactless Magnetic Stripe | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS107 | Purchase/Capture – Manual / Keyed | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS108 | Purchase/Capture – Chip Fallback | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS109 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |

## 11. ATL105 references used

| Reference | Section | PDF p. | Printed | Text (verified on the page) |
|---|---|---:|---|---|
| APPD-01 | Appendix D. Valid State Codes | 514 | Appendix D-1 | Alabama AL 01 |
| APPD-26 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Michigan MI 26 |
| APPD-33 | Appendix D. Valid State Codes | 515 | Appendix D-2 | New Hampshire NH 33 |
| APPD-54 | Appendix D. Valid State Codes | 516 | Appendix D-3 | West Virginia WV 54 |
| APPD-57 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Armed Forces Americas AA 57 |
| APPD-67 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Northwest Territories NT 67 |
| APPD-68 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Ontario ON 68 |
| APPD-72 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Yukon YT 72 |
| APPG-A | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | A Account Verification Account verification |
| APPG-S | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | S Cancellation Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction. |
| APPI-005 | Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005) | 555 | Appendix I-6 | Point-of-Service Entry Mode Information |
| APPI-030 | Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030) | 568 | Appendix I-19 | Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions. |
| APPI-032-08 | Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08 | 575 | Appendix I-26 | It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.). |
| APPI-049-02 | Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02 | 589 | Appendix I-40 | This field is required for all subsequent Credential on File transactions. |
| APPJ-10 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 10 Credential on file |
| APPR-NOTE | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | The below table does not contain complete list of tags. |
| EL102 | Element 102 Terminal Identifier (Chapter 13) | 417 | 13-77 | The Terminal Identifier must be assigned by and set up at BUYPASS. |
| EL102-STATE | Element 102 Terminal Identifier (Chapter 13) | 417 | 13-77 | State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). |
| EL12 | Element 12 Card Discretionary Block Data (Chapter 13) | 372 | 13-32 | For a card whose number has been manually entered, the expiration date is in MMYY format |
| EL12-MAX | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2. |
| EL12-TRACK2 | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character. |
| EL15 | Element 15 Card Type Total Amount (Chapter 13) | 374 | 13-34 | Fixed length of eight digits with two assumed decimal places |
| EL16 | Element 16 Card Type Total Count (Chapter 13) | 375 | 13-35 | Fixed length of five digits |
| EL187-USE | Element 187 CA Public Key File Checksum (Chapter 13) | 477 | 13-137 | Once received, the checksum must be used for all subsequent EMV transaction requests from this device. |
| EL190-EXACT | Element 190 EMV Chip Data (Chapter 13) | 478 | 13-138 | All data elements must contain the exact values that were present on the card to terminal interface. |
| EL2 | Element 2 Account Number (Chapter 13) | 364 | 13-24 | a variable length (up to 24 alphanumeric bytes) Account Number |
| EL33-FMT | Element 33 Encrypted PIN Block Data (Chapter 13) | 382 | 13-42 | For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block. |
| EL39 | Element 39 Firmware Version (Chapter 13) | 384 | 13-44 | Fixed length of eight alphanumeric characters |
| EL43 | Element 43 Hardware Version (Chapter 13) | 386 | 13-46 | Fixed length of four or eight alphanumeric characters |
| EL44 | Element 44 Information Byte (Chapter 13) | 386 | 13-46 | 0 Single-message transmission |
| EL5-FMT | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Fixed length of six alphanumeric characters; space-filled |
| EL5-PRE | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization. |
| EL5-REV | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Required on reversals for matching information to the original purchase. |
| EL6 | Element 6 Approved Amount (Chapter 13) | 368 | 13-28 | Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned |
| EL78 | Element 78 Prompt Code (Chapter 13) | 400 | 13-60 | this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters). |
| EL83-0 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 0 Approved—Purchase/Capture |
| EL83-2 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 2 Approved—Authorization only |
| EL84 | Element 84 Segment Length (Chapter 13) | 406 | 13-66 | This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| EL86-REV | Element 86 Sequence Number (Chapter 13) | 409 | 13-69 | In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request. |
| EL87-VAL | Element 87 Service Level (Chapter 13) | 410 | 13-70 | F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present. |
| EL96 | Element 96 Software Version (Chapter 13) | 414 | 13-74 | Fixed length of eight alphanumeric characters |
| S10.1.2-LIST | Section 10.1.2 Supported Transaction Types | 83 | 10-4 | The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal |
| S10.1.2.1-REMAIN | Section 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale. |
| S10.1.2.1-SEQ | Section 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | The Sequence Number must be the Sequence Number contained in the original transaction request. |
| S10.1.6.1.3 | Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service) | 87 | 10-8 | A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response. |
| S10.1.8.2 | Section 10.1.8.2 Track Support Requirements | 94 | 10-15 | The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). |
| S10.13.2.4.2-MC | Section 10.13.2.4.2 Stored Credential Use Cases | 144 | 10-65 | Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type. |
| S10.14.3-ONLINE | Section 10.14.3 EMV PIN (Personal Identification Number) Processing | 162 | 10-83 | Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer. |
| S10.14.3-OPT | Section 10.14.3 EMV PIN (Personal Identification Number) Processing | 162 | 10-83 | In a credit/signature debit transaction, a PIN of all F’s is NOT required since PINs are an optional field for a credit/signature debit transaction. |
| S11.1-101 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data. |
| S11.1-104 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data. |
| S11.1-123 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions. |
| S11.1-145 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings. |
| S11.1-153 | Section 11.1 Financial Transactions (Data Section No. 3) | 169 | 11-6 | 153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction. |
| S11.1-DS3 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9. |
| S11.8-130 | Section 11.8.1 EMV Financial Transaction Request | 206 | 11-43 | This segment is the only segment required for all EMV financial transactions. |
| S12.1-LEN | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.1-ROW16 | Section 12.1 Standard Message Data Segment | 225 | 12-8 | Identifies the local date and local time of the preauthorized transaction. |
| S12.1-ROW3 | Section 12.1 Standard Message Data Segment | 223 | 12-6 | 3 44 Information Byte 1 R |
| S12.10-FS | Section 12.10 Variable Information Data Segment | 246 | 12-29 | A Field Separator follows the final Variable Information Section. |
| S12.10-LEN | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Identifies the length of Variable Information (Element No. 113). |
| S12.19-FS | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | All fields are separated by Field Separator. |
| S12.19-MAX | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | It has a maximum length of 186 alphanumeric characters |
| S12.19-REQ | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data. |
| S12.2-FS | Section 12.2 Fleet Data Segment | 226 | 12-9 | All fields are separated by Field Separators. |
| S12.20-FS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | A Field Separator follows the final EMV Additional Information Section. |
| S12.20-LEN | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.3-DELIM | Section 12.3 Product Code Data Segment | 229 | 12-12 | A Product Data Field Delimiter always follows Quantity and Unit Price. |
| S12.3-FS | Section 12.3 Product Code Data Segment | 229 | 12-12 | A Field Separator always follows Segment Type and Segment Length. |
| S12.3-LAST | Section 12.3 Product Code Data Segment | 229 | 12-12 | A Field Separator follows Product Amount when Product Amount is the very last element in the segment. |
| S12.3-MAX | Section 12.3 Product Code Data Segment | 229 | 12-12 | It has a maximum length of 381 alphanumeric characters |
| S12.3-ROW3 | Section 12.3 Product Code Data Segment | 230 | 12-13 | 3 87 Service Level 1 R Identifies the type of sale. |
| S12.3-TOTAL | Section 12.3 Product Code Data Segment | 229 | 12-12 | The total of product amounts in the segment must equal the total of the amounts |
| S12.31-LEN | Section 12.31 Enhanced Fleet Request Segment | 289 | 12-72 | 2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length |
| S12.31-MC | Section 12.31 Enhanced Fleet Request Segment | 289 | 12-72 | Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV transactions. |
| S12.31-SCOPE | Section 12.31 Enhanced Fleet Request Segment | 289 | 12-72 | The Enhanced Fleet Request Segment contains fleet data that applies to the enhanced fleet offerings such as Wex OTR, Comdata, Voyager EMV, and Visa Fleet 2.0. |
| S12.31-TLV | Section 12.31 Enhanced Fleet Request Segment | 289 | 12-72 | All or some of the Enhanced Fleet sub-segment data is specified below. Data is in <tag><len><data> format. |
| S12.38-ENTITLED | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | When a merchant is entitled to use both First Data provisioned Network Tokens and Auth Optimization service, the merchant is required to send the same and exact information from the original transaction request in all the follow-up transactions |
| S12.38-FS | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | There is a Field Separator between Field Nos. 1 and 2. |
| S12.38-LEN | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | 2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length |
| S12.38-NOFS | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | Field Separators are not there between the elements in Network Token Data Layouts. |
| S12.38-T001 | Section 12.38 Network Token Data Request Segment | 311 | 12-94 | Table Data n Max. Len.: 19 Identifies the Network Token Data. |
| S12.38-T002 | Section 12.38 Network Token Data Request Segment | 311 | 12-94 | Identifies the Network Token Expiration Date. Format: YYMM |
| S12.38-T004 | Section 12.38 Network Token Data Request Segment | 312 | 12-95 | Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN |
| S12.38-T006 | Section 12.38 Network Token Data Request Segment | 312 | 12-95 | This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation). |
| S12.38-TLV | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | All or some of the Network Token sub-segment data is specified below. Data is in <tag><len><data> format. |
| S12.38-UCOM | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | This segment is used to send Network Token transaction information from Ucom to BUYPASS. |
| S12.5-FS | Section 12.5 Purchase Card Data Segment | 233 | 12-16 | All fields are separated by Field Separators. |
| S12.5-MAX | Section 12.5 Purchase Card Data Segment | 233 | 12-16 | It has a maximum length of 86 alphanumeric characters |
| S12.6-CAT | Section 12.6 Totals Data Segment | 237 | 12-20 | Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type. |
| S12.7-FS | Section 12.7 Loyalty Card Data Segment | 238 | 12-21 | All fields are separated by Field Separators, with a Field Separator following Field No. 13. |
| S12.7-MAX | Section 12.7 Loyalty Card Data Segment | 238 | 12-21 | It has a maximum length of 142 alphanumeric characters |
| S12.7-ROW12 | Section 12.7 Loyalty Card Data Segment | 239 | 12-22 | 12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card. |
| S2.3-NEVER | Section 2.3 Unsuccessful Financial Transactions | 39 | 2-4 | A TOR never initiates a new financial transaction request. |
| S2.3-TOR | Section 2.3 Unsuccessful Financial Transactions | 39 | 2-4 | A time-out reversal (TOR) is created for an unsuccessful financial transaction. |
| S2.3.1-ANYRESP | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | Any response from BUYPASS to a TOR, whether it is an approval or a decline, is considered a valid response |
| S2.3.1-DEVICE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. |
| S2.3.1-LATE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored. |
| S2.3.1-MATCH | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response. |
| S2.3.1-QUEUE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR |
| S2.3.1-TIMEOUT | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The customer’s 30-second response interval expires without a response from BUYPASS. |
| S2.3.1-UNMATCHED | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | BUYPASS declines a TOR request as an unmatched reversal when BUYPASS has not received the original financial transaction request related to the TOR; or when the original financial transaction has already been reversed. |
| S9.2-GT | Section 9.2 Local Totals | 72 | 9-8 | The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only |
| S9.2-PUR | Section 9.2 Local Totals | 72 | 9-8 | A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total. |
| S9.2-RET | Section 9.2 Local Totals | 72 | 9-8 | A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total. |
| S9.2-REV | Section 9.2 Local Totals | 72 | 9-8 | A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total. |

Regenerate: `python scripts/validate-run-and-build-manual-validation.py` (writes this folder again).

