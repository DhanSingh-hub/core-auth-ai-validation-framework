# ATL105 validation of the run - all 2,129 test cases and 2,422 test data files

**Run:** [mastercard_credit_new_16_11](../../mastercard_credit_new_16_11/) (MasterCard Credit run of 16 Nov 2026 (plan v12)), build `buypass-atl105-mastercard-credit-generated-923e8c2eac`  
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). The attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the 2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  
**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.

Manual validation document for every test case: [Test cases/README.md](Test%20cases/README.md). Detailed step-by-step guides for 5 test cases: [TC-039912](TC-039912-Manual-Validation.md), [TC-039913](TC-039913-Manual-Validation.md), [TC-039914](TC-039914-Manual-Validation.md), [TC-039915](TC-039915-Manual-Validation.md), [TC-039916](TC-039916-Manual-Validation.md). Every finding of every test case is in the Excel sheet [ATL105_Run_Validation_All_Test_Cases.xlsx](ATL105_Run_Validation_All_Test_Cases.xlsx).

## 1. Result at a glance

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 245 | At least one test data or test case defect that must be fixed |
| **BLOCKED** | 1,748 | No defect, but a client value or a run-time value is still missing |
| **REVIEW** | 0 | No defect; a citation must be corrected or a point confirmed with BUYPASS |
| **PASS** | 0 | Nothing found |
| **DEVICE** | 136 | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |
| Total | 2,129 | 1,990 send a message to BUYPASS (2,422 test data files); 139 are device-only or have no test data |

Findings by kind (a test case can have several):

| Kind | Findings | Test cases | Effect on the result |
|---|---:|---:|---|
| Test data defect | 143 | 103 | FAIL |
| Test case defect | 175 | 167 | FAIL |
| Test case issue to correct | 895 | 784 | REVIEW |
| Citation issue | 1,443 | 1,079 | REVIEW |
| Awaiting client input / run-time value | 4,975 | 1,993 | BLOCKED |
| To confirm with BUYPASS / SME | 1,016 | 896 | REVIEW |
| Intended violation of a negative test (correct) | 152 | 145 | none |
| AI validator rule not applicable - value checked and correct | 38 | 25 | none |
| Information | 814 | 754 | none |

Data segments in the test data: 100 (2,355 files), 101 (14 files), 102 (609 files), 104 (10 files), 105 (67 files), 108 (27 files), 111 (2,355 files), 113 (1 files), 123 (8 files), 130 (616 files), 145 (9 files), 153 (26 files).

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

- `TOTALS_FLOW_MISMATCH` (TC-040137) - Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 cancellation $15.00: cancellation - subtracts from the AO bucket. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-040138) - Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-041074) - Section 9.2 counts each approved transaction into its card bucket: step 1 moto auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 cancellation $15.00: cancellation - subtracts from the AO bucket. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-041075) - Section 9.2 counts each approved transaction into its card bucket: step 1 moto auth only $15.00: Authorization Only - AO bucket, not in the grand total. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-041108) - Section 9.2 counts each approved transaction into its card bucket: step 1 return $15.00: count +1, amount -$15.00; step 2 void return $15.00: count -1, amount +$15.00. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040137](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L372) (BR1_TS11_TC78) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040137.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040137.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040138](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L373) (BR1_TS11_TC79) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040138.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040138.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-041074](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1309) (BR1_TS19_TC6) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-041074.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041074.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-041075](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1310) (BR1_TS19_TC7) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-041075.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041075.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-041108](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1343) (BR1_TS21_TC7) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-041108.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041108.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040251](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L486) (BR1_TS12_TC74) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040251.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040251.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040252](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L487) (BR1_TS12_TC75) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040252.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040252.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040372](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L607) (BR1_TS13_TC81) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040372.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040372.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040373](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L608) (BR1_TS13_TC82) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040373.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040373.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040568](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L803) (BR1_TS14_TC72) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040568.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040568.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040569](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L804) (BR1_TS14_TC73) | 2 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040569.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040569.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-040680](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L915) (BR1_TS15_TC74) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-040680.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040680.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| … | | 16 more in the sheet (Findings, filter on D-01) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS19 (2), BR1_TS20 (2), BR1_TS21 (1), BR1_TS22 (1), BR1_TS23 (1), BR1_TS24 (1), BR1_TS25 (1) …

**How to check by hand:** Open the Totals Request file, read CardLabel / CardTypeTotalCount / CardTypeTotalAmount / GrandTotal and recompute them from the earlier steps of the same test case (Section 9.2).

**Fix:** Compute the totals from the approved steps of the flow; an Authorization Only goes to the AO bucket and is left out of the grand total; a bucket with net count 0 is not sent; count and amount must agree.

### 3.2 D-02 - Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply

**28 test cases, 44 findings, 28 test data files** - kind: Test data defect

Rules: `X_PIN_BLOCK_FORMAT` (28), `FIELD_5` (8), `X_OPTIONAL_FIELD_CONDITION` (8).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- Section 12.1 Standard Message Data Segment, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction."

What is wrong (one example per kind of problem):

- `X_PIN_BLOCK_FORMAT` (TC-040111) - Element 33 carries a 16- to 20-byte Key Serial Number plus a 16-byte PIN block (32-36 characters); 'PIN' has 3 characters.
- `FIELD_5` (TC-040126) - 'J38FQA' is an Approval Number on an original Authorization Only (Transaction Type 3), which asks for a new approval. ATL105 uses Element 5 to match a follow-on to its original (reversals) and says that in a Purchase Request it makes the purchase preauthorized; it does not say how BUYPASS treats an Authorization Only that carries one, so the request contradicts the scenario it is meant to test.
- `X_OPTIONAL_FIELD_CONDITION` (TC-040126) - Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant.
- `X_OPTIONAL_FIELD_CONDITION` (TC-040903) - Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant; Fuel Purchase Amount '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040111](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L346) (BR1_TS11_TC52) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040111.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040111.json#L16) | `PIN` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040126](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | 1 | `FIELD_5` | [TD-TC-040126.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L23) | `J38FQA` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040126](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040126.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040126.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-040903](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138) (BR1_TS17_TC70) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040903.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040903.json) | `Local Date and Local Time '0615251430' o…-0100 (non-p` | Send conditional fields only when their condition holds. |
| [TC-040126](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040126.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040225](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L460) (BR1_TS12_TC48) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040225.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040225.json#L16) | `PIN` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040240](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | 1 | `FIELD_5` | [TD-TC-040240.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L23) | `6FH3NZ` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040240](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040240.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040240](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040240.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040240.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-040341](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L576) (BR1_TS13_TC50) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040341.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040341.json#L16) | `PIN` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040356](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) (BR1_TS13_TC65) | 1 | `FIELD_5` | [TD-TC-040356.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L23) | `C9HTU3` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040356](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) (BR1_TS13_TC65) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040356.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| … | | 32 more in the sheet (Findings, filter on D-02) | | | |

Scenarios: BR1_TS11 (4), BR1_TS12 (4), BR1_TS13 (4), BR1_TS14 (4), BR1_TS15 (4), BR1_TS16 (4), BR1_TS17 (4), BR1_TS18 (4), BR1_TS154 (1), BR1_TS155 (1), BR1_TS156 (1), BR1_TS157 (1), BR1_TS158 (1), BR1_TS159 (1), BR1_TS160 (1) …

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

- `X_SEG_LENGTH_FIELD` (TC-039967) - Field 2 (Element 84) of Segment 145 is 3 characters long (Len. 3), so '1008' cannot be sent; the segment itself is 1009 characters long.
- `X_SEG_MAX` (TC-039967) - The segment is 1009 characters long; ATL105 allows at most 999.
- `X_TLV_FORMAT` (TC-039967) - ATL105 sends this field as sub-segments <3-digit tag><3-digit length><data>, but at position 1 'SE1Q7F' is not a 3-digit tag followed by a 3-digit length.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039967](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L202) (BR1_TS7_TC9) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-039967.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039967.json#L65) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-039967](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L202) (BR1_TS7_TC9) | 1 | `X_SEG_MAX` | [TD-TC-039967.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039967.json) | `1009` | Keep the segment within 999 characters. |
| [TC-039967](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L202) (BR1_TS7_TC9) | 1 | `X_TLV_FORMAT` | [TD-TC-039967.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039967.json#L66) | `SE1Q7F4VS4SQT9AELSGV660DQ5R0S5WYFGG5JLCA…8M5N6CTSZAF…` | Build the field from the sub-segments defined in the segment's layout tables. |
| [TC-040129](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L364) (BR1_TS11_TC70) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-040129.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040129.json#L61) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-040129](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L364) (BR1_TS11_TC70) | 1 | `X_SEG_MAX` | [TD-TC-040129.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040129.json) | `1009` | Keep the segment within 999 characters. |
| [TC-040129](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L364) (BR1_TS11_TC70) | 1 | `X_TLV_FORMAT` | [TD-TC-040129.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040129.json#L62) | `NL8RTITJBCKG0KB8XVQO3JO28DZBTEE5NW6KEU2A…RFNINH8R867…` | Build the field from the sub-segments defined in the segment's layout tables. |
| [TC-040243](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L478) (BR1_TS12_TC66) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-040243.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040243.json#L61) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-040243](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L478) (BR1_TS12_TC66) | 1 | `X_SEG_MAX` | [TD-TC-040243.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040243.json) | `1009` | Keep the segment within 999 characters. |
| [TC-040243](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L478) (BR1_TS12_TC66) | 1 | `X_TLV_FORMAT` | [TD-TC-040243.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040243.json#L62) | `D36A62ZIVA804FSON6CA28JOLBB5FJUFPEP9CCJ7…0886LRE0A37…` | Build the field from the sub-segments defined in the segment's layout tables. |
| [TC-040359](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L594) (BR1_TS13_TC68) | 1 | `X_SEG_LENGTH_FIELD` | [TD-TC-040359.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040359.json#L50) | `1008` | Keep the segment within 999 characters so its length fits 3 digits. |
| [TC-040359](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L594) (BR1_TS13_TC68) | 1 | `X_SEG_MAX` | [TD-TC-040359.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040359.json) | `1009` | Keep the segment within 999 characters. |
| [TC-040359](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L594) (BR1_TS13_TC68) | 1 | `X_TLV_FORMAT` | [TD-TC-040359.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040359.json#L51) | `O4IEA1ZAV3EV706IN3O9OK46NX1I6SSP95PFCB28…RB0FK87M7MT…` | Build the field from the sub-segments defined in the segment's layout tables. |
| … | | 15 more in the sheet (Findings, filter on D-03) | | | |

Scenarios: BR1_TS7 (3), BR1_TS11 (3), BR1_TS12 (3), BR1_TS13 (3), BR1_TS14 (3), BR1_TS15 (3), BR1_TS16 (3), BR1_TS17 (3), BR1_TS18 (3)

**How to check by hand:** Open the file, count the characters of SegmentLengthIndicator (must be 3) and try to split EnhancedFleetData into 3-digit tag + 3-digit length + data.

**Fix:** Build Segment 145 from real fleet sub-segments (TLV), keep the segment within 999 characters, or drop the segment for a MasterCard Credit (non-fleet) card.

### 3.4 D-09 - A negative test breaks more than the one rule it is meant to break

**6 test cases, 8 findings, 6 test data files** - kind: Test data defect

Rules: `FIELD_12` (4), `SEG130_LENGTH` (2), `TABLE_005` (1), `VARINFO_LENGTH` (1).

ATL105:

- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "A Field Separator follows the final EMV Additional Information Section."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."
- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "02 Magnetic stripe read with partial track data"
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.639 (printed Appendix J-2): "1 PIN entry capability (including both EMV Contact and Contactless)"
- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "Identifies the length of Variable Information (Element No. 113)."

What is wrong (one example per kind of problem):

- `SEG130_LENGTH` (TC-038603) - '0054' is declared, but the segment adds up to 0201.
- `FIELD_12` (TC-038628) - '0228' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.
- `TABLE_005` (TC-038628) - '021' has entry mode '02', which does not fit the MAGSTRIPE scenario (expected 90).
- `VARINFO_LENGTH` (TC-038635) - '' is declared, but the table data 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' is 983 characters long.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038603](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L12) (BR1_TS11_TC1) | 1 | `SEG130_LENGTH` | [TD-TC-038603.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038603.json#L7) | `0054` | 0201 |
| [TC-038628](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L37) (BR1_TS3_TC1) | 1 | `FIELD_12` | [TD-TC-038628.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038628.json#L15) | `0228` | A value that meets: AN, variable length 51. |
| [TC-038628](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L37) (BR1_TS3_TC1) | 1 | `TABLE_005` | TD-TC-038628.json -> Variable Information Data Segment -> VariableInformationSection[0] | `021` | 021 |
| [TC-038635](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L44) (BR1_TS1_TC3) | 1 | `VARINFO_LENGTH` | TD-TC-038635.json -> Variable Information Data Segment -> VariableInformationSection[0] | (empty) | 983 |
| [TC-038629](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L38) (BR1_TS29_TC2) | 1 | `SEG130_LENGTH` | [TD-TC-038629.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038629.json#L7) | `0057` | 0204 |
| [TC-038635](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L44) (BR1_TS1_TC3) | 1 | `FIELD_12` | [TD-TC-038635.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038635.json#L15) | `0530` | A value that meets: AN, variable length 51. |
| [TC-038636](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L45) (BR1_TS11_TC3) | 1 | `FIELD_12` | [TD-TC-038636.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038636.json#L15) | `0928` | A value that meets: AN, variable length 51. |
| [TC-038637](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L46) (BR1_TS21_TC1) | 1 | `FIELD_12` | [TD-TC-038637.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038637.json#L15) | `0928` | A value that meets: AN, variable length 51. |

Scenarios: BR1_TS11 (2), BR1_TS3 (2), BR1_TS1 (2), BR1_TS29 (1), BR1_TS21 (1)

**How to check by hand:** Compare the failing field with the field the test case deliberately changes (flags DELIBERATE_CONSTRAINT_VIOLATION / method deliberate_violation_*).

**Fix:** Keep every other field valid so the decline can only come from the rule under test.

### 3.5 D-11 - Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator)

**1 test case, 1 finding, 1 test data file** - kind: Test data defect

Rules: `TABLE_005` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "02 Magnetic stripe read with partial track data"
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.639 (printed Appendix J-2): "1 PIN entry capability (including both EMV Contact and Contactless)"

What is wrong (one example per kind of problem):

- `TABLE_005` (TC-038601) - '021' has entry mode '02', which does not fit the MAGSTRIPE scenario (expected 90).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038601](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L10) (BR1_TS31_TC1) | 1 | `TABLE_005` | TD-TC-038601.json -> Variable Information Data Segment -> VariableInformationSection[0] | `021` | 021 |

Scenarios: BR1_TS31 (1)

**How to check by hand:** Open the field named in the finding and apply the ATL105 rule quoted with it.

**Fix:** Correct the value as stated in the finding.

### 3.6 D-12 - Another data segment is longer than its ATL105 maximum or breaks its TLV layout

**1 test case, 1 finding, 1 test data file** - kind: Test data defect

Rules: `X_SEG_MAX` (1).

ATL105:

- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"

What is wrong (one example per kind of problem):

- `X_SEG_MAX` (TC-040673) - The segment is 156 characters long; ATL105 allows at most 142.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040673](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L908) (BR1_TS15_TC67) | 1 | `X_SEG_MAX` | [TD-TC-040673.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040673.json) | `156` | Keep the segment within 142 characters. |

Scenarios: BR1_TS15 (1)

**How to check by hand:** Add up the segment (section 12.x Field Separator rule) and compare with the maximum length in section 11.1 / 12.x.

**Fix:** Keep the segment within its maximum length and in its layout.

### 3.7 D-13 - Card read from the chip / stripe, but Card Discretionary Block Data holds only an MMYY expiry instead of the Track 2 data after the '='

**33 test cases, 33 findings, 33 test data files** - kind: Test data defect

Rules: `FIELD_12` (33).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."

What is wrong (one example per kind of problem):

- `FIELD_12` (TC-038593) - '0529' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038593](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2) (BR1_TS11_TC2) | 1 | `FIELD_12` | [TD-TC-038593.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038593.json#L15) | `0529` | A value that meets: AN, variable length 51. |
| [TC-038594](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L3) (BR1_TS12_TC1) | 1 | `FIELD_12` | [TD-TC-038594.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038594.json#L15) | `0532` | A value that meets: AN, variable length 51. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | 1 | `FIELD_12` | [TD-TC-038598.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038598.json#L15) | `0632` | A value that meets: AN, variable length 51. |
| [TC-038599](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L8) (BR1_TS21_TC2) | 1 | `FIELD_12` | [TD-TC-038599.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038599.json#L15) | `0629` | A value that meets: AN, variable length 51. |
| [TC-038601](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L10) (BR1_TS31_TC1) | 1 | `FIELD_12` | [TD-TC-038601.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038601.json#L15) | `0232` | A value that meets: AN, variable length 51. |
| [TC-038603](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L12) (BR1_TS11_TC1) | 1 | `FIELD_12` | [TD-TC-038603.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038603.json#L15) | `0427` | A value that meets: AN, variable length 51. |
| [TC-038604](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L13) (BR1_TS11_TC4) | 1 | `FIELD_12` | [TD-TC-038604.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038604.json#L15) | `0529` | A value that meets: AN, variable length 51. |
| [TC-038608](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L17) (BR1_TS11_TC5) | 1 | `FIELD_12` | [TD-TC-038608.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038608.json#L15) | `0429` | A value that meets: AN, variable length 51. |
| [TC-038609](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L18) (BR1_TS154_TC3) | 1 | `FIELD_12` | [TD-TC-038609.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038609.json#L15) | `0727` | A value that meets: AN, variable length 51. |
| [TC-038610](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L19) (BR1_TS154_TC4) | 1 | `FIELD_12` | [TD-TC-038610.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038610.json#L15) | `0730` | A value that meets: AN, variable length 51. |
| [TC-038611](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L20) (BR1_TS148_TC1) | 1 | `FIELD_12` | [TD-TC-038611.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038611.json#L15) | `0131` | A value that meets: AN, variable length 51. |
| [TC-038612](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L21) (BR1_TS162_TC1) | 1 | `FIELD_12` | [TD-TC-038612.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038612.json#L15) | `0331` | A value that meets: AN, variable length 51. |
| … | | 21 more in the sheet (Findings, filter on D-13) | | | |

Scenarios: BR1_TS11 (4), BR1_TS1 (4), BR1_TS154 (4), BR1_TS147 (3), BR1_TS29 (3), BR1_TS148 (2), BR1_TS162 (2), BR1_TS161 (2), BR1_TS2 (2), BR1_TS12 (1), BR1_TS21 (1), BR1_TS31 (1), BR1_TS155 (1), BR1_TS157 (1), BR1_TS30 (1) …

**How to check by hand:** Open the test data file: Table 005 starts with 05 / 07 / 90 / 91 (card read), but CardDiscretionaryBlockData is 4 digits (MMYY). For a read card it must be the Track 2 data after the '=': expiry YYMM, 3-digit service code, discretionary data.

**Fix:** Send the test card's Track 2 (equivalent) data after the '=' (YYMM + service code 2xx for a chip card + discretionary data), or use a keyed entry mode if only the expiry is known.

### 3.8 D-15 - Positive boundary test: the value is not at the documented limit or is not a valid value

**1 test case, 1 finding, 1 test data file** - kind: Test data defect

Rules: `FIELD_44` (1).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
- Element 87 Service Level (Chapter 13), PDF p.410 (printed 13-70): "F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present."
- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission"

What is wrong (one example per kind of problem):

- `FIELD_44` (TC-038634) - 'A' is not a valid Information Byte (?, 0 or 1).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038634](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L43) (BR1_TS29_TC1) | 1 | `FIELD_44` | [TD-TC-038634.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038634.json#L8) | `A` | A value that meets: 1 character: 'A' = not a valid code. |

Scenarios: BR1_TS29 (1)

**How to check by hand:** Read the objective (field and limit), then the value in the test data: it must have exactly that many characters and be a valid value of the element.

**Fix:** Send a valid value of exactly the documented maximum length; if no such value can be approved (a MasterCard card number is 16 digits, not 24), drop the positive boundary test.

## 4. Defects and issues in the test cases (design, status, expected results)

### 4.1 C-01 - Same request as another test case but a different expected result

**38 test cases, 38 findings, 38 test data files** - kind: Test case defect

Rules: `X_SAME_REQUEST_DIFFERENT_OUTCOME` (38).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response."

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST_DIFFERENT_OUTCOME` (TC-040035) - Apart from the Sequence Number the message is identical to TC-040009 (all segments compared). TC-040009 expects 0; this test expects 1.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040035](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L270) (BR1_TS9_TC28) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040035.json vs TD-TC-040009.json | `expected 1 vs 0` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040064](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L299) (BR1_TS10_TC22) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040064.json vs TD-TC-040043.json | `expected 1 vs 0` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040111](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L346) (BR1_TS11_TC52) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040111.json vs TD-TC-040107.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040114](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L349) (BR1_TS11_TC55) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040114.json vs TD-TC-040109.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040115](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L350) (BR1_TS11_TC56) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040115.json vs TD-TC-040110.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040225](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L460) (BR1_TS12_TC48) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040225.json vs TD-TC-040221.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040228](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L463) (BR1_TS12_TC51) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040228.json vs TD-TC-040223.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040229](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L464) (BR1_TS12_TC52) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040229.json vs TD-TC-040224.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040341](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L576) (BR1_TS13_TC50) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040341.json vs TD-TC-040337.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040344](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L579) (BR1_TS13_TC53) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040344.json vs TD-TC-040339.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040345](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L580) (BR1_TS13_TC54) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040345.json vs TD-TC-040340.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| [TC-040542](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L777) (BR1_TS14_TC46) | 1 | `X_SAME_REQUEST_DIFFERENT_OUTCOME` | TD-TC-040542.json vs TD-TC-040538.json | `expected 2 vs 1` | Make the request of the decline test different (decline test card, amount or simulator rule). |
| … | | 26 more in the sheet (Findings, filter on C-01) | | | |

Scenarios: BR1_TS11 (3), BR1_TS12 (3), BR1_TS13 (3), BR1_TS14 (3), BR1_TS15 (3), BR1_TS16 (3), BR1_TS17 (3), BR1_TS18 (3), BR1_TS9 (1), BR1_TS10 (1), BR1_TS154 (1), BR1_TS155 (1), BR1_TS156 (1), BR1_TS157 (1), BR1_TS158 (1) …

**How to check by hand:** Open both test data files side by side: apart from the Sequence Number they are identical, but one test expects approval and the other a decline.

**Fix:** Give the decline test its own trigger (decline test card / amount / simulator rule) so its request differs.

### 4.2 C-02 - Negative test premise contradicts ATL105 (the rule it claims to break does not exist)

**54 test cases, 54 findings** - kind: Test case defect

Rules: `X_NEG_PREMISE` (54).

ATL105:

- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"
- Section 10.14.3 EMV PIN (Personal Identification Number) Processing, PDF p.162 (printed 10-83): "Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "3 44 Information Byte 1 R"
- Section 12.3 Product Code Data Segment, PDF p.230 (printed 12-13): "3 87 Service Level 1 R Identifies the type of sale."
- Section 12.7 Loyalty Card Data Segment, PDF p.239 (printed 12-22): "12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data."
- Section 11.2.1 Loyalty Card Transactions - Request, PDF p.175 (printed 11-12): "The Loyalty Card Transaction Request contains Segment Nos. 108 and 114."
- Section 11.2.1 Loyalty Card Transactions - Request, PDF p.175 (printed 11-12): "Contains the loyalty card data required for all loyalty card transactions."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE` (TC-038602) - PDF p.561 (printed Appendix I-12) does not mention Enhanced Fleet Request at all. The test case's own citation for p.561 (spec_basis, ATL105) reads "Indicates suspected fraud in an eCommerce or card not present transaction. It is sent only in a purchase reversal transaction request." - it says nothing about Enhanced Fleet Request. Section 11.1 lists Segment 145 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038604) - PDF p.688 (printed Appendix O-1) does not mention NFC Payment Tokenization Data Segment at all. The test case's own citation for p.688 (spec_basis, ATL105) reads "Payment tokens must not have the same value as a cardholder PAN." - it says nothing about NFC Payment Tokenization Data Segment. Section 11.1 lists Segment 123 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038605) - PDF p.728 (printed Appendix AD-1) does not mention Purchase Card Data Segment at all. The test case's own citation for p.728 (spec_basis, ATL105) reads "The transaction must be from a participating acquirer and must be a card not present transaction as identified by any of the following criteria: • Credential on file transaction" - it says nothing about Purchase Card Data Segment. Section 11.1 lists Segment 104 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038607) - PDF p.15 (front matter, no printed page number) does not mention Loyalty Card Data Segment at all. The test case's own citation for p.15 (spec_basis, from the ATL105 TransArmor specification, not the ATL105 Message Format Specifications) reads "Expiration Date must be included in Card Discretionary Block Data (Element No." - it says nothing about Loyalty Card Data Segment. ATL105 makes Segment 108 required only in the Loyalty Card Transaction Request (Section 11.2.1: it 'Contains the loyalty card data required for all loyalty card transactions'); the Financial Transaction Request layout in Section 11.1 does not list it. This MasterCard Credit transaction carries no loyalty card, so leaving Segment 108 out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038608) - PDF p.28 (front matter, no printed page number) does not mention Purchase Card Data Segment at all. The test case's own citation for p.28 (spec_basis, from the ATL105 TransArmor specification, not the ATL105 Message Format Specifications) reads "Initiate a $1.00 MasterCard Sale transaction with the payment application and swipe the RegiStart Card or initiate the RegiStart administrative function coded on the POS." - it says nothing about Purchase Card Data Segment. Section 11.1 lists Segment 104 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038609) - PDF p.23 (front matter, no printed page number) does not mention Fleet Data Segment at all. The test case's own citation for p.23 (spec_basis, source SRC-BUYPASS-PIPS-PDF-001) reads "Valid payment methods for a Prepay transaction are credit, fleet or proprietary cards." - it says nothing about Fleet Data Segment. Section 11.1 lists Segment 101 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038610) - PDF p.24 (front matter, no printed page number) does not mention Purchase Card Data Segment at all. The test case's own citation for p.24 (spec_basis, source SRC-BUYPASS-PIPS-PDF-001) reads "Valid payment methods for a forced/off-line purchase transaction are: credit, fleet, and proprietary cards." - it says nothing about Purchase Card Data Segment. Section 11.1 lists Segment 104 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038638) - PDF p.73 (printed 9-9) does not mention Loyalty Card Data Segment at all. The test case's own citation for p.73 (spec_basis, source SRC-BUYPASS-PIPS-PDF-001) reads "The Restriction Code value must follow the Expiration Date in the Discretionary Data Field sent to the host." - it says nothing about Loyalty Card Data Segment. ATL105 makes Segment 108 required only in the Loyalty Card Transaction Request (Section 11.2.1: it 'Contains the loyalty card data required for all loyalty card transactions'); the Financial Transaction Request layout in Section 11.1 does not list it. This MasterCard Credit transaction carries no loyalty card, so leaving Segment 108 out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-038639) - PDF p.142 (printed 10-63) does not mention EMV Request Data Segment at all. The test case's own citation for p.142 (spec_basis, from the Fiserv EMV guide, not ATL105) reads "F allback A llow ed?" - it says nothing about EMV Request Data Segment. Section 11.1 lists Segment 130 as conditional (C): it is sent only when the transaction needs its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and BUYPASS has no reason to decline.
- `X_NEG_PREMISE` (TC-040107) - ATL105 says online PIN is applicable for credit transactions and that a PIN is an optional field for credit - it does not forbid a PIN block on a credit Authorization Only.
- `X_NEG_PREMISE` (TC-040108) - Information Byte (Element 44) is a Required field (R) of Segment 100, so it is sent on every financial request.
- `X_NEG_PREMISE` (TC-040109) - Payment Tender Type (Element 148) is a Required field (R) of Segment 108 whenever the segment is sent.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038602](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L11) (BR1_TS7_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `Enhanced Fleet Request (segment 145) omi… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038604](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L13) (BR1_TS11_TC4) | - | `X_NEG_PREMISE` | test case test_objective | `NFC Payment Tokenization Data Segment (s… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038605](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L14) (BR1_TS17_TC3) | - | `X_NEG_PREMISE` | test case test_objective | `Purchase Card Data Segment (segment 104)… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038607](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) (BR1_TS15_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `Loyalty Card Data Segment (segment 108) … requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038608](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L17) (BR1_TS11_TC5) | - | `X_NEG_PREMISE` | test case test_objective | `Purchase Card Data Segment (segment 104)… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038609](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L18) (BR1_TS154_TC3) | - | `X_NEG_PREMISE` | test case test_objective | `Fleet Data Segment (segment 101) omitted… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038610](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L19) (BR1_TS154_TC4) | - | `X_NEG_PREMISE` | test case test_objective | `Purchase Card Data Segment (segment 104)… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038638](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L47) (BR1_TS158_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `Loyalty Card Data Segment (segment 108) … requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-038639](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L48) (BR1_TS13_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `EMV Request Data Segment (segment 130) o… requires it` | Remove the test or rewrite it around a rule ATL105 states. |
| [TC-040107](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L342) (BR1_TS11_TC48) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-040108](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L343) (BR1_TS11_TC49) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-040109](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L344) (BR1_TS11_TC50) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Authorization Only w… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| … | | 42 more in the sheet (Findings, filter on C-02) | | | |

Scenarios: BR1_TS11 (6), BR1_TS13 (6), BR1_TS17 (5), BR1_TS15 (5), BR1_TS12 (4), BR1_TS14 (4), BR1_TS16 (4), BR1_TS18 (4), BR1_TS154 (3), BR1_TS158 (2), BR1_TS7 (1), BR1_TS155 (1), BR1_TS156 (1), BR1_TS157 (1), BR1_TS159 (1) …

**How to check by hand:** Read the test objective, then the cited ATL105 rule: the transaction/field is supported or required.

**Fix:** Remove the test or rewrite it around a rule ATL105 states.

### 4.3 C-03 - Test case marked 'ready' although its Terminal Identifier is a placeholder

**619 test cases, 619 findings** - kind: Test case issue to correct

Rules: `READY_WITH_PLACEHOLDER` (619).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

What is wrong (one example per kind of problem):

- `READY_WITH_PLACEHOLDER` (TC-038592) - The test case status is 'ready', but its Terminal Identifier is still a placeholder.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038592](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1) (BR1_TS9_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038593](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2) (BR1_TS11_TC2) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038594](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L3) (BR1_TS12_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038597](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L6) (BR1_TS17_TC2) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038599](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L8) (BR1_TS21_TC2) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038600](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L9) (BR1_TS17_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038601](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L10) (BR1_TS31_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038602](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L11) (BR1_TS7_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038603](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L12) (BR1_TS11_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038604](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L13) (BR1_TS11_TC4) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-038605](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L14) (BR1_TS17_TC3) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| … | | 607 more in the sheet (Findings, filter on C-03) | | | |

Scenarios: BR1_TS17 (60), BR1_TS11 (57), BR1_TS15 (54), BR1_TS12 (53), BR1_TS13 (53), BR1_TS14 (50), BR1_TS16 (50), BR1_TS9 (24), BR1_TS1 (24), BR1_TS7 (21), BR1_TS3 (20), BR1_TS2 (20), BR1_TS4 (19), BR1_TS5 (19), BR1_TS6 (19) …

**How to check by hand:** TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.

**Fix:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 4.4 C-04 - Expected Response Code not stated for a step, or several codes accepted

**252 test cases, 261 findings** - kind: Test case issue to correct

Rules: `EXPECTED_RESPONSE_OPEN` (261).

ATL105:

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"

What is wrong (one example per kind of problem):

- `EXPECTED_RESPONSE_OPEN` (TC-038601) - The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction.
- `EXPECTED_RESPONSE_OPEN` (TC-039821) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the reversal step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.
- `EXPECTED_RESPONSE_OPEN` (TC-039822) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the timeout reversal step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.
- `EXPECTED_RESPONSE_OPEN` (TC-040014) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the moto reversal step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.
- `EXPECTED_RESPONSE_OPEN` (TC-040133) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the cancellation step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.
- `EXPECTED_RESPONSE_OPEN` (TC-041104) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the return step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.
- `EXPECTED_RESPONSE_OPEN` (TC-041107) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the void return step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.
- `EXPECTED_RESPONSE_OPEN` (TC-041177) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the balance inquiry step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038601](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L10) (BR1_TS31_TC1) | - | `EXPECTED_RESPONSE_OPEN` | test case step 1 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-039821](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L56) (BR1_TS1_TC14) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-039822](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC15) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-040014](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L249) (BR1_TS9_TC7) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-040133](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L368) (BR1_TS11_TC74) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-041104](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1339) (BR1_TS21_TC3) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 1) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-041107](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1342) (BR1_TS21_TC6) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-041177](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1412) (BR1_TS29_TC5) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 1) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-038634](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L43) (BR1_TS29_TC1) | - | `EXPECTED_RESPONSE_OPEN` | test case step 1 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-039823](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC16) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-039825](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L60) (BR1_TS1_TC18) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-039826](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L61) (BR1_TS1_TC19) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| … | | 249 more in the sheet (Findings, filter on C-04) | | | |

Scenarios: BR1_TS27 (10), BR1_TS21 (9), BR1_TS22 (9), BR1_TS23 (9), BR1_TS24 (9), BR1_TS25 (9), BR1_TS26 (9), BR1_TS28 (9), BR1_TS1 (8), BR1_TS2 (8), BR1_TS3 (8), BR1_TS4 (8), BR1_TS5 (8), BR1_TS6 (8), BR1_TS7 (8) …

**How to check by hand:** Open tx_legs[i].expect / steps[i].expected_response: no response_code value; or expected_response.response_code.acceptable_values lists several approval codes (e.g. 0 and 2) for a non-totals step. Approved-Totals codes 5/6/B/D/N/W for a Totals Request are accepted (Element 83, p.404-405).

**Fix:** State the expected Response Code (from BUYPASS or a reference run).

### 4.5 C-05 - Test objective / name does not match the message sent or the expected result

**15 test cases, 15 findings** - kind: Test case issue to correct

Rules: `X_OBJECTIVE_MISMATCH` (12), `X_NAME_OUTCOME_MISMATCH` (3).

ATL105:

- Element 78 Prompt Code (Chapter 13), PDF p.400 (printed 13-60): "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters)."
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"

What is wrong (one example per kind of problem):

- `X_NAME_OUTCOME_MISMATCH` (TC-038618) - The name ends with '→ declined', but the objective says the value 'is accepted' and the expected Response Code is 2 (approved).
- `X_OBJECTIVE_MISMATCH` (TC-039818) - The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type '0' (POS Purchase/Capture) and the name says Purchase/Capture.
- `X_OBJECTIVE_MISMATCH` (TC-040013) - The objective says 'Purchase/Capture', but step 1 sends Prompt Code Transaction Type '6' (Mail/Phone Purchase) and the name says Mail/Phone Purchase.
- `X_OBJECTIVE_MISMATCH` (TC-041072) - The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type 'B' (Mail/Phone Authorization Only) and the name says Mail/Phone Authorization Only.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038618](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L27) (BR1_TS154_TC1) | - | `X_NAME_OUTCOME_MISMATCH` | test case name | `chase Code exactly at its documented max…… → declined` | Correct the name to '→ approved'. |
| [TC-039818](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L53) (BR1_TS1_TC11) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-040013](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L248) (BR1_TS9_TC6) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Purchase/Capture with in…= completion` | Correct the objective to Mail/Phone Purchase. |
| [TC-041072](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1307) (BR1_TS19_TC4) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to Mail/Phone Authorization Only. |
| [TC-038622](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L31) (BR1_TS147_TC1) | - | `X_NAME_OUTCOME_MISMATCH` | test case name | `ional Information exactly at its documen…… → declined` | Correct the name to '→ approved'. |
| [TC-038632](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L41) (BR1_TS2_TC1) | - | `X_NAME_OUTCOME_MISMATCH` | test case name | `A Public Key File Checksum exactly at it…… → declined` | Correct the name to '→ approved'. |
| [TC-039842](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L77) (BR1_TS2_TC7) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-039866](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L101) (BR1_TS3_TC6) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-039892](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L127) (BR1_TS4_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-039916](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-039940](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L175) (BR1_TS6_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| [TC-039964](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L199) (BR1_TS7_TC6) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |
| … | | 3 more in the sheet (Findings, filter on C-05) | | | |

Scenarios: BR1_TS2 (2), BR1_TS154 (1), BR1_TS147 (1), BR1_TS1 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1), BR1_TS8 (1), BR1_TS9 (1), BR1_TS10 (1), BR1_TS19 (1), BR1_TS20 (1)

**How to check by hand:** Compare test_objective and the name with the Prompt Code Transaction Type in the test data and with the expected Response Code.

**Fix:** Correct the objective / name text.

### 4.6 C-10 - Positive test does not send the condition it claims (table, segment or field value)

**33 test cases, 33 findings, 33 test data files** - kind: Test case defect

Rules: `X_CONDITION_NOT_SENT` (33).

ATL105:

- Element 111 Variable Information Indicator (Chapter 13), PDF p.422 (printed 13-82): "Fixed length of 3 alphanumeric characters"
- Element 111 Variable Information Indicator (Chapter 13), PDF p.423 (printed 13-83): "022 Originating Device Type Indicator"
- Element 111 Variable Information Indicator (Chapter 13), PDF p.424 (printed 13-84): "048 MasterCard Authentication Data"
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication."
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID."
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.587 (printed Appendix I-38): "1 = EMV 3-D Secure Version 2.1"
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "A Product Code Data Segment (Data Segment No. 102) must be present in the transaction."
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments."
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager."

What is wrong (one example per kind of problem):

- `X_CONDITION_NOT_SENT` (TC-040069) - The objective "Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information" says the request carries Table 022 Originating Device Type Indicator Information, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 022 Originating Device Type Indicator Information is approved - not that Table 022 Originating Device Type Indicator Information is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 022 with value '00', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040080) - The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040116) - The objective "Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.
- `X_CONDITION_NOT_SENT` (TC-040841) - The objective "Happy path: Authorization Only sending Table 009 Partial Approval Indicator Information" says the request carries Table 009 Partial Approval Indicator Information, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 009 Partial Approval Indicator Information is approved - not that Table 009 Partial Approval Indicator Information is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 009 with value '0', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040842) - The objective "Happy path: Authorization Only sending Table 012 Market-Specific Data Indicator Information" says the request carries Table 012 Market-Specific Data Indicator Information, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 012 Market-Specific Data Indicator Information is approved - not that Table 012 Market-Specific Data Indicator Information is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 012 with value 'M', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040948) - The objective "Response / oracle check: Authorization Only sending Table 021 Settlement Data Acceptance Flag -> response table checked" says the request carries Table 021 Settlement Data Acceptance Flag, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 021 Settlement Data Acceptance Flag is approved - not that Table 021 Settlement Data Acceptance Flag is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 021 with value '0', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040963) - The objective "Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information" says the request carries Table 022 Originating Device Type Indicator Information, but the test data does not contain it and the test case names no missing input (status `pending_sme`). An approval would only prove that the request without Table 022 Originating Device Type Indicator Information is approved - not that Table 022 Originating Device Type Indicator Information is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 022 with value '00', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040974) - The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `pending_sme`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-041009) - The objective "Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `pending_sme`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.
- `X_CONDITION_NOT_SENT` (TC-041210) - The objective "Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information" says the request carries Table 022 Originating Device Type Indicator Information, but the test data does not contain it and the test case names no missing input (status `needs_client_input`). An approval would only prove that the request without Table 022 Originating Device Type Indicator Information is approved - not that Table 022 Originating Device Type Indicator Information is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 022 with value '00', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-041219) - The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `needs_client_input`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040069](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L304) (BR1_TS11_TC10) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040069.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040069.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 022 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| [TC-040080](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040080.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040080.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040116](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) (BR1_TS11_TC57) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040116.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040116.json) | `Tax Type 1: not in the test data; segmen…00, 111, 130` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the |
| [TC-040841](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1076) (BR1_TS17_TC8) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040841.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040841.json) | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056` | Add Table 009 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| [TC-040842](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1077) (BR1_TS17_TC9) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040842.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040842.json) | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056` | Add Table 012 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| [TC-040948](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1183) (BR1_TS17_TC115) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040948.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040948.json) | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056` | Add Table 021 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| [TC-040963](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1198) (BR1_TS18_TC5) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040963.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040963.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 022 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| [TC-040974](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) (BR1_TS18_TC16) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040974.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040974.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-041009](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1244) (BR1_TS18_TC51) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-041009.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041009.json) | `Tax Type 1: not in the test data; segments sent: 100, 111` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the |
| [TC-041210](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1445) (BR1_TS54_TC5) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-041210.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041210.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 022 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| [TC-041219](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-041219.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041219.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040183](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L418) (BR1_TS12_TC6) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040183.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040183.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 022 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. |
| … | | 21 more in the sheet (Findings, filter on C-10) | | | |

Scenarios: BR1_TS17 (6), BR1_TS11 (3), BR1_TS12 (3), BR1_TS13 (3), BR1_TS14 (3), BR1_TS15 (3), BR1_TS16 (3), BR1_TS18 (3), BR1_TS54 (2), BR1_TS154 (2), BR1_TS173 (2)

**How to check by hand:** Read test_objective and the name, then open the test data: the claimed table, segment or value is not in the request, and open_items / awaiting_inputs do not ask for it.

**Fix:** Send the claimed data with the segments it depends on, or keep the test out of 'ready' with the missing input named, or re-scope / withdraw it when the condition does not apply.

### 4.7 C-11 - Positive test expects response information that its request does not ask for

**42 test cases, 50 findings, 42 test data files** - kind: Test case defect

Rules: `X_RESPONSE_NOT_INITIATED` (50).

ATL105:

- Element 116 Additional Information Indicator (Chapter 13), PDF p.433 (printed 13-93): "A Financial Transaction Request initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction Response."
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment"
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The authorizer returns a response containing both the transaction authorization Response Code and the AVS information in the Additional Information Data Segment"
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "Address Verification Service (AVS) is a credit card authorization tool used for transactions performed primarily in a non-face-to-face environment, such as mail order, phone order, or customer-activated terminal (CAT)."
- Element 113 Variable Information (Chapter 13), PDF p.426 (printed 13-86): "When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here."
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or"
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "The remaining 00–20 characters contain street address information."
- Appendix K. Additional Information Data Layouts - AVS or NVS or Both Information (Table 003), PDF p.643 (printed Appendix K-4): "Identifies the AVS or NVS or both response from the authorizer."
- Element 111 Variable Information Indicator (Chapter 13), PDF p.423 (printed 13-83): "003 Address verification service (AVS) indicator"
- Appendix I. Variable Information Data Layouts - Card Verification Information (Table 004), PDF p.554 (printed Appendix I-5): "MasterCard:  3 bytes"

What is wrong (one example per kind of problem):

- `X_RESPONSE_NOT_INITIATED` (TC-040140) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040144) - tx_legs[0].response_tables expects Segment 112 Table 004 CVV Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 004. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040170) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `needs_client_input`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040177) - tx_legs[0].response_tables expects Segment 112 Table 004 CVV Information in the response (status `needs_client_input`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 004. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040919) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040923) - tx_legs[0].response_tables expects Segment 112 Table 004 CVV Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 004. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040950) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `needs_client_input`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040957) - tx_legs[0].response_tables expects Segment 112 Table 004 CVV Information in the response (status `needs_client_input`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 004. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-041032) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `pending_sme`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-041036) - tx_legs[0].response_tables expects Segment 112 Table 004 CVV Information in the response (status `pending_sme`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 004. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-041101) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `pending_sme`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040140](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) (BR1_TS11_TC81) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040140.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040140.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040144](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L379) (BR1_TS11_TC85) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040144.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040144.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 004 in Segment 111 with the test card's CVC2 (3 digits for MasterCard), recalculate the Segment 111 length and assert the CVV result in Segment 112 Table 004 - or drop the CVV expectation. |
| [TC-040170](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L405) (BR1_TS11_TC111) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040170.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040170.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040177](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L412) (BR1_TS11_TC118) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040177.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040177.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 004 in Segment 111 with the test card's CVC2 (3 digits for MasterCard), recalculate the Segment 111 length and assert the CVV result in Segment 112 Table 004 - or drop the CVV expectation. |
| [TC-040919](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154) (BR1_TS17_TC86) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040919.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040919.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040923](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1158) (BR1_TS17_TC90) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040923.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040923.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 004 in Segment 111 with the test card's CVC2 (3 digits for MasterCard), recalculate the Segment 111 length and assert the CVV result in Segment 112 Table 004 - or drop the CVV expectation. |
| [TC-040950](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1185) (BR1_TS17_TC117) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040950.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040950.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040957](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1192) (BR1_TS17_TC124) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040957.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040957.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 004 in Segment 111 with the test card's CVC2 (3 digits for MasterCard), recalculate the Segment 111 length and assert the CVV result in Segment 112 Table 004 - or drop the CVV expectation. |
| [TC-041032](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1267) (BR1_TS18_TC74) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-041032.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041032.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-041036](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1271) (BR1_TS18_TC78) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-041036.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041036.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 004 in Segment 111 with the test card's CVC2 (3 digits for MasterCard), recalculate the Segment 111 length and assert the CVV result in Segment 112 Table 004 - or drop the CVV expectation. |
| [TC-041101](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1336) (BR1_TS20_TC9) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-041101.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041101.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040176](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L411) (BR1_TS11_TC117) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040176.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040176.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| … | | 38 more in the sheet (Findings, filter on C-11) | | | |

Scenarios: BR1_TS11 (6), BR1_TS12 (6), BR1_TS13 (6), BR1_TS14 (6), BR1_TS15 (6), BR1_TS16 (6), BR1_TS17 (6), BR1_TS18 (6), BR1_TS19 (1), BR1_TS20 (1)

**How to check by hand:** Read tx_legs[i].response_tables (the Segment 112 table the test expects back), then open the request: the Segment 111 table that initiates it (003 AVS, 004 card verification) is not sent.

**Fix:** Send the initiating table with the client's test card data and assert the result, or drop the response expectation / re-scope the test.

## 5. Citation issues (the ATL105 text quoted in the test cases)

### 5.1 Q-01 - Quote not found in ATL105 or missing (generator note or other text cited as ATL105)

**80 test cases, 82 findings** - kind: Citation issue

Rules: `QUOTE_NOT_IN_SPEC` (79), `QUOTE_MISSING` (3).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."
- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"

What is wrong (one example per kind of problem):

- `QUOTE_MISSING` (TC-038595) - The spec_basis entry gives a rule id and a page but no ATL105 text, so the basis cannot be checked word for word.
- `QUOTE_NOT_IN_SPEC` (TC-039816) - The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040010) - The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction (reversal matching)." for p.402. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040078) - The test case quotes "Table 042 Cardholder Language" for p.583. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040089) - The test case quotes "Table 065 Merchant Payment Gateway ID Data" for p.622. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040105) - The test case quotes "§10.1.2.2/§10.2.2.2 (p.83/97): 'Element No.86 (Sequence Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction.' Element No. 78 (Prompt Code) must contain Transaction Type code 'S' — verified verbatim against raw/pdf_pages/page_0083.txt and page_0097.txt, 2026-10-01." for p.83. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040173) - The test case quotes "Table 059 Soft Descriptor - Merchant Country" for p.613. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040175) - The test case quotes "Table 062 Customer Additional Information Data" for p.614. Not found in ATL105.
- `QUOTE_NOT_IN_SPEC` (TC-040496) - The test case quotes "Not in the list: The following point-of-sale transactions are supported for credit card processing:" for p.83. Not found in ATL105.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038595](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L4) (BR1_TS29_TC4) | - | `QUOTE_MISSING` | test case spec_basis | `p.62` | Add the ATL105 sentence the rule is based on. |
| [TC-039816](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC9) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| [TC-040010](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L245) (BR1_TS9_TC3) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `The Sequence Number must be the Sequence…l matching).` | Quote the text exactly as it appears on the page. |
| [TC-040078](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L313) (BR1_TS11_TC19) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 042 Cardholder Language` | Quote the text exactly as it appears on the page. |
| [TC-040089](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L324) (BR1_TS11_TC30) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 065 Merchant Payment Gateway ID Data` | Quote the text exactly as it appears on the page. |
| [TC-040105](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L340) (BR1_TS11_TC46) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `§10.1.2.2/§10.2.2.2 (p.83/97): 'Element … 2026-10-01.` | Quote the text exactly as it appears on the page. |
| [TC-040173](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L408) (BR1_TS11_TC114) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 059 Soft Descriptor - Merchant Country` | Quote the text exactly as it appears on the page. |
| [TC-040175](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L410) (BR1_TS11_TC116) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Table 062 Customer Additional Information Data` | Quote the text exactly as it appears on the page. |
| [TC-040496](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L731) (BR1_TS13_TC205) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Not in the list: The following point-of-… processing:` | Quote the text exactly as it appears: "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time- |
| [TC-038595](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L4) (BR1_TS29_TC4) | - | `QUOTE_MISSING` | test case spec_basis | `p.112` | Add the ATL105 sentence the rule is based on. |
| [TC-038595](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L4) (BR1_TS29_TC4) | - | `QUOTE_MISSING` | test case spec_basis | `p.545` | Add the ATL105 sentence the rule is based on. |
| [TC-039840](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L75) (BR1_TS2_TC5) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| … | | 70 more in the sheet (Findings, filter on Q-01) | | | |

Scenarios: BR1_TS13 (7), BR1_TS11 (6), BR1_TS12 (6), BR1_TS14 (6), BR1_TS15 (6), BR1_TS16 (6), BR1_TS17 (6), BR1_TS18 (6), BR1_TS29 (3), BR1_TS9 (2), BR1_TS10 (2), BR1_TS19 (2), BR1_TS20 (2), BR1_TS54 (2), BR1_TS154 (2) …

**How to check by hand:** Search the quoted text on the cited page.

**Fix:** Quote the ATL105 sentence word for word.

### 5.2 Q-02 - Quote is on a different page than cited

**622 test cases, 628 findings** - kind: Citation issue

Rules: `QUOTE_PAGE_DRIFT` (601), `QUOTE_WRONG_PAGE` (26), `CITATION_OUTDATED_PAGE` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"

What is wrong (one example per kind of problem):

- `QUOTE_PAGE_DRIFT` (TC-038596) - The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).
- `CITATION_OUTDATED_PAGE` (TC-038635) - The test case cites p.246 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.246 (printed 12-29) does not contain it; it is on PDF p.555 (printed Appendix I-6).
- `QUOTE_WRONG_PAGE` (TC-039815) - The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- `QUOTE_WRONG_PAGE` (TC-039817) - The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038596](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC5) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-038635](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L44) (BR1_TS1_TC3) | - | `CITATION_OUTDATED_PAGE` | test case request -> Variable Information Data Segment -> Variable Information (source_page), every step | `p.246` | Change the cited page from 246 to 555. The data values are not affected. |
| [TC-039815](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L50) (BR1_TS1_TC8) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `The Sequence Number must be the Sequence…transaction.` | Cite p.84 instead of p.402. |
| [TC-039817](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L52) (BR1_TS1_TC10) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `A TOR must be sent from the same device … in the TOR.` | Cite p.40 instead of p.38. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-038604](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L13) (BR1_TS11_TC4) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-038608](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L17) (BR1_TS11_TC5) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-038609](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L18) (BR1_TS154_TC3) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-038610](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L19) (BR1_TS154_TC4) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-038614](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L23) (BR1_TS147_TC4) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-039814](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L49) (BR1_TS1_TC7) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-039815](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L50) (BR1_TS1_TC8) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| … | | 616 more in the sheet (Findings, filter on Q-02) | | | |

Scenarios: BR1_TS11 (116), BR1_TS12 (114), BR1_TS154 (60), BR1_TS173 (59), BR1_TS174 (32), BR1_TS155 (31), BR1_TS54 (30), BR1_TS1 (29), BR1_TS2 (26), BR1_TS147 (11), BR1_TS21 (10), BR1_TS22 (10), BR1_TS168 (7), BR1_TS47 (6), BR1_TS148 (6) …

**How to check by hand:** Find the quote: it is on another PDF page.

**Fix:** Correct the page number.

### 5.3 Q-03 - Quote is not word-for-word

**499 test cases, 625 findings** - kind: Citation issue

Rules: `QUOTE_PARAPHRASED` (625).

What is wrong (one example per kind of problem):

- `QUOTE_PARAPHRASED` (TC-038602) - The test case quotes "A Data max of 8 products can be sent." for p.579. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-038635) - The test case quotes "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." for p.148. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-038636) - The test case quotes "Transaction Transaction Attributes Sequence o SIC Information should be MCC/SIC code of third-party payment provider underlying retailer (Data Element 111, Table ID 007) o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." for p.154. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-038636) - The test case quotes "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." for p.152. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-038636) - The test case quotes "Transaction Transaction Attributes Sequence Additional Data (Elelment 111, Table ID 56, Sub Table ID 01 to 08) if cards are issued in India and amount is above $52. o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." for p.156. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040037) - The test case quotes "Not in the list: The following point-of-sale transactions are supported for EMV card processing:" for p.161. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040066) - The test case quotes "Table 006 Currency Code Information" for p.555. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040067) - The test case quotes "Table 008 Balance Information" for p.556. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040068) - The test case quotes "Table 019 Recharge Type Information" for p.562. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040068) - The test case quotes "2 Data Required for PIN on Receipt and Top Up transactions." for p.562. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040069) - The test case quotes "Table 022 Originating Device Type Indicator Information" for p.564. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-040070) - The test case quotes "Table 027 Customer Name" for p.566. Not word-for-word: all its words are on the cited page, but not in this form.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038602](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L11) (BR1_TS7_TC1) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `A
Data max of 8 products can be sent.` | Quote the text exactly as it appears on the page. |
| [TC-038635](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L44) (BR1_TS1_TC3) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-038636](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L45) (BR1_TS11_TC3) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-038636](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L45) (BR1_TS11_TC3) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-038636](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L45) (BR1_TS11_TC3) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-040037](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L272) (BR1_TS9_TC30) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Not in the list: The following point-of-… processing:` | Quote the text exactly as it appears on the page. |
| [TC-040066](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L301) (BR1_TS11_TC7) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 006 Currency Code Information` | Quote the text exactly as it appears on the page. |
| [TC-040067](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L302) (BR1_TS11_TC8) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 008 Balance Information` | Quote the text exactly as it appears on the page. |
| [TC-040068](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L303) (BR1_TS11_TC9) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 019 Recharge Type Information` | Quote the text exactly as it appears on the page. |
| [TC-040068](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L303) (BR1_TS11_TC9) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `2 Data Required for PIN on Receipt and Top Up transactions.` | Quote the text exactly as it appears on the page. |
| [TC-040069](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L304) (BR1_TS11_TC10) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 022 Originating Device Type Indicator Information` | Quote the text exactly as it appears on the page. |
| [TC-040070](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L305) (BR1_TS11_TC11) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 027 Customer Name` | Quote the text exactly as it appears on the page. |
| … | | 613 more in the sheet (Findings, filter on Q-03) | | | |

Scenarios: BR1_TS13 (70), BR1_TS17 (65), BR1_TS11 (61), BR1_TS12 (57), BR1_TS14 (57), BR1_TS15 (57), BR1_TS16 (57), BR1_TS18 (57), BR1_TS154 (30), BR1_TS173 (30), BR1_TS54 (28), BR1_TS21 (4), BR1_TS9 (3), BR1_TS155 (3), BR1_TS156 (3) …

**How to check by hand:** Compare the quote with the page text.

**Fix:** Quote word for word.

### 5.4 Q-04 - Citation or device checkpoint from an unrelated section or document

**108 test cases, 108 findings** - kind: Citation issue

Rules: `X_CHECKPOINT_CONTEXT` (61), `X_BASIS_CONTEXT` (34), `QUOTE_CONTEXT` (11), `X_BASIS_PAGE` (2).

ATL105:

- Section 11.8.1 EMV Financial Transaction Request, PDF p.206 (printed 11-43): "This segment is the only segment required for all EMV financial transactions."
- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)."
- Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8): "A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response."
- Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."

What is wrong (one example per kind of problem):

- `X_BASIS_PAGE` (TC-038596) - Segment 130 is required for all EMV financial transactions and step 1 is a chip read (test case entry mode EMV_CONTACT), so the negative test is valid - but PDF p.162 (printed 10-83) does not say so.
- `X_BASIS_CONTEXT` (TC-038598) - The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.
- `X_CHECKPOINT_CONTEXT` (TC-039814) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (GENERAL).
- `X_CHECKPOINT_CONTEXT` (TC-039960) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (CARD_NOT_PRESENT-0100).
- `QUOTE_CONTEXT` (TC-040134) - The quote is word-for-word, but it comes from Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8); that section is about FSA/HRA healthcare benefit cards.
- `X_CHECKPOINT_CONTEXT` (TC-041188) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Hospitality · attended POS (HOSPITALITY-ATTENDED).
- `X_CHECKPOINT_CONTEXT` (TC-041797) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE General Retail · unattended CAT (GENERAL_RETAIL-UNATTENDED).
- `X_CHECKPOINT_CONTEXT` (TC-041807) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Hospitality · unattended CAT (HOSPITALITY-UNATTENDED).
- `X_CHECKPOINT_CONTEXT` (TC-041817) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Supermarket · attended POS (SUPERMARKET-ATTENDED).
- `X_CHECKPOINT_CONTEXT` (TC-041838) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Supermarket · unattended CAT (SUPERMARKET-UNATTENDED).
- `X_CHECKPOINT_CONTEXT` (TC-041848) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Utilities · attended POS (UTILITIES-ATTENDED).
- `X_CHECKPOINT_CONTEXT` (TC-041869) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at SAMPLE Utilities · unattended CAT (UTILITIES-UNATTENDED).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038596](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC5) | - | `X_BASIS_PAGE` | test case test_objective | `EMV Request Data Segment (segment 130) o… requires it` | Cite Section 11.8 (Segment 130 required for all EMV financial transactions) as the basis. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |
| [TC-039814](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L49) (BR1_TS1_TC7) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-039960](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L195) (BR1_TS7_TC2) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-040134](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L369) (BR1_TS11_TC75) | - | `QUOTE_CONTEXT` | test case spec_basis | `A Split Tender transaction results when …on response.` | Cite Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5) as the main basis for a normal MasterCard credit card. |
| [TC-041188](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1423) (BR1_TS47_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-041797](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2032) (BR1_TS37_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-041807](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2042) (BR1_TS61_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-041817](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2052) (BR1_TS71_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-041838](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2073) (BR1_TS99_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-041848](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2083) (BR1_TS109_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-041869](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2104) (BR1_TS137_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| … | | 96 more in the sheet (Findings, filter on Q-04) | | | |

Scenarios: BR1_TS1 (5), BR1_TS2 (3), BR1_TS3 (3), BR1_TS4 (3), BR1_TS5 (3), BR1_TS6 (3), BR1_TS7 (3), BR1_TS8 (3), BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2) …

**How to check by hand:** Read the cited text: it covers another transaction, card or industry.

**Fix:** Cite the rule that applies.

## 6. Values awaiting client input or run-time values (BLOCKED, not defects)

### 6.1 I-01 - Terminal Identifier is a generated placeholder

**1,990 test cases, 1,990 findings** - kind: Awaiting client input / run-time value

Rules: `TERMINAL_PLACEHOLDER` (1990).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "Armed Forces Americas AA 57"
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "West Virginia WV 54"
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Northwest Territories NT 67"
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Michigan MI 26"
- Appendix D. Valid State Codes, PDF p.514 (printed Appendix D-1): "Alabama AL 01"
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Ontario ON 68"
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "New Mexico NM 35"

What is wrong (one example per kind of problem):

- `TERMINAL_PLACEHOLDER` (TC-038592) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- `TERMINAL_PLACEHOLDER` (TC-038609) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'KC57457075622174001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '57' = Armed Forces Americas (AA), US military.
- `TERMINAL_PLACEHOLDER` (TC-039831) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'RR54265527479214001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '54' = West Virginia (WV), United States.
- `TERMINAL_PLACEHOLDER` (TC-041188) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'QU67070249149717001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '67' = Northwest Territories (NT), Canada.
- `TERMINAL_PLACEHOLDER` (TC-041569) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'VZ26598162666071001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '26' = Michigan (MI), United States.
- `TERMINAL_PLACEHOLDER` (TC-041786) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'PW01708669533046001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '01' = Alabama (AL), United States.
- `TERMINAL_PLACEHOLDER` (TC-041793) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'PQ68310350483989001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '68' = Ontario (ON), Canada.
- `TERMINAL_PLACEHOLDER` (TC-041797) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'UU35419320602727001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '35' = New Mexico (NM), United States.
- `TERMINAL_PLACEHOLDER` (TC-041817) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'BM20912622000482001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '20' = Kansas (KS), United States.
- `TERMINAL_PLACEHOLDER` (TC-041838) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'LH19957899655578001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '19' = Iowa (IA), United States.
- `TERMINAL_PLACEHOLDER` (TC-041848) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'MW46155503976692001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '46' = South Dakota (SD), United States.
- `TERMINAL_PLACEHOLDER` (TC-041869) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'YJ76768787859870001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '76' = Nunavut (NU), Canada.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038592](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1) (BR1_TS9_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-038609](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L18) (BR1_TS154_TC3) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `KC57457075622174001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-039831](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L66) (BR1_TS1_TC24) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `RR54265527479214001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041188](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1423) (BR1_TS47_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `QU67070249149717001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041569](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1804) (BR1_TS168_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `VZ26598162666071001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041786](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2021) (BR1_TS178_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `PW01708669533046001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041793](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2028) (BR1_TS190_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `PQ68310350483989001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041797](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2032) (BR1_TS37_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `UU35419320602727001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041817](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2052) (BR1_TS71_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `BM20912622000482001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041838](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2073) (BR1_TS99_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `LH19957899655578001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041848](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2083) (BR1_TS109_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `MW46155503976692001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041869](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2104) (BR1_TS137_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `YJ76768787859870001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| … | | 1,978 more in the sheet (Findings, filter on I-01) | | | |

Scenarios: BR1_TS17 (125), BR1_TS11 (119), BR1_TS13 (117), BR1_TS12 (115), BR1_TS15 (115), BR1_TS16 (115), BR1_TS14 (111), BR1_TS18 (110), BR1_TS154 (62), BR1_TS173 (59), BR1_TS155 (32), BR1_TS174 (32), BR1_TS158 (30), BR1_TS54 (30), BR1_TS1 (29) …

**How to check by hand:** merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.

**Fix:** Client / BUYPASS to supply the real Terminal Identifier.

### 6.2 I-02 - Value filled in at run time from the previous response (expected for follow-on steps)

**226 test cases, 267 findings, 254 test data files** - kind: Awaiting client input / run-time value

Rules: `RUNTIME_PLACEHOLDER` (253), `SEGMENT_LENGTH_RUNTIME` (14).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled"
- Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
- Element 6 Approved Amount (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "A Field Separator always follows Segment Type and Segment Length."
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "A Product Data Field Delimiter always follows Quantity and Unit Price."
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "A Field Separator follows Product Amount when Product Amount is the very last element in the segment."
- Section 12.3 Product Code Data Segment, PDF p.229 (printed 12-12): "It has a maximum length of 381 alphanumeric characters"

What is wrong (one example per kind of problem):

- `SEGMENT_LENGTH_RUNTIME` (TC-038631) - '058' depends on the run-time value(s) Account Number.
- `RUNTIME_PLACEHOLDER` (TC-039815) - The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-040134) - '086' depends on the run-time value(s) Nonfuel Amount.
- `RUNTIME_PLACEHOLDER` (TC-040134) - The field holds the instruction text '<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>' instead of a real Nonfuel Amount. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-041341) - The segment holds run-time values in angle brackets; its length can only be calculated after they are filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-041632) - '089' depends on the run-time value(s) Fuel Purchase Amount.
- `RUNTIME_PLACEHOLDER` (TC-041632) - The field holds the instruction text '<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>' instead of a real Fuel Purchase Amount. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038631](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L40) (BR1_TS2_TC2) | 1 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-038631.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038631.json#L7) | `058` | Recalculate the Segment Length after filling in the real values. |
| [TC-039815](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L50) (BR1_TS1_TC8) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039815.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039815.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-040134](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L369) (BR1_TS11_TC75) | 2 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-040134.step2.split-tender.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040134.step2.split-tender.json#L7) | `086` | Recalculate the Segment Length after filling in the real values. |
| [TC-040134](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L369) (BR1_TS11_TC75) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-040134.step2.split-tender.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040134.step2.split-tender.json#L19) | `<RemainingBalance = step 1 Nonfuel Amoun…oved Amount>` | Fill in at run time: Amount = requested amount minus the Approved Amount (Element 6) of the partial approval response (p.84 'remaining balance due'); runtime link. |
| [TC-041341](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1576) (BR1_TS154_TC45) | 2 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-041341.step2.split-tender.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041341.step2.split-tender.json#L7) | `022` | Recalculate after substitution. |
| [TC-041632](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1867) (BR1_TS173_TC41) | 2 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-041632.step2.split-tender.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041632.step2.split-tender.json#L7) | `089` | Recalculate the Segment Length after filling in the real values. |
| [TC-041632](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1867) (BR1_TS173_TC41) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-041632.step2.split-tender.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041632.step2.split-tender.json#L18) | `<RemainingBalance = step 1 Nonfuel Amoun…oved Amount>` | Fill in at run time: Amount = requested amount minus the Approved Amount (Element 6) of the partial approval response (p.84 'remaining balance due'); runtime link. |
| [TC-039816](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC9) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039816.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039816.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-039821](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L56) (BR1_TS1_TC14) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039821.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039821.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-039825](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L60) (BR1_TS1_TC18) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039825.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039825.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-039828](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L63) (BR1_TS1_TC21) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039828.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039828.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-039835](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L70) (BR1_TS1_TC28) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039835.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039835.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| … | | 255 more in the sheet (Findings, filter on I-02) | | | |

Scenarios: BR1_TS11 (9), BR1_TS12 (9), BR1_TS13 (9), BR1_TS14 (9), BR1_TS15 (9), BR1_TS16 (9), BR1_TS17 (9), BR1_TS18 (9), BR1_TS10 (8), BR1_TS2 (7), BR1_TS9 (7), BR1_TS154 (7), BR1_TS173 (7), BR1_TS1 (6), BR1_TS3 (6) …

**How to check by hand:** The field holds text in angle brackets, e.g. <ApprovalNumber from step 1 response>.

**Fix:** The harness substitutes the value from the previous response and recalculates the Segment Length.

### 6.3 I-03 - Variable Information table value awaited from the client (Segment 111 length to recalculate)

**62 test cases, 62 findings, 62 test data files** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_TABLE` (62).

ATL105:

- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "Identifies the length of Variable Information (Element No. 113)."
- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "A Field Separator follows the final Variable Information Section."

What is wrong (one example per kind of problem):

- `X_AWAITING_TABLE` (TC-039820) - Table(s) 082 hold <AWAITING:...> text instead of a value. The Segment 111 Segment Length '045' counts only the table indicator, so it must be recalculated when the value is filled in. The test case lists: step 1: 111 Variable Information Length [4] - Table 082 length follows its awaited data

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039820](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC13) | 1 | `X_AWAITING_TABLE` | [TD-TC-039820.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039820.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039844](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L79) (BR1_TS2_TC9) | 1 | `X_AWAITING_TABLE` | [TD-TC-039844.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039844.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039868](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L103) (BR1_TS3_TC8) | 1 | `X_AWAITING_TABLE` | [TD-TC-039868.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039868.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039894](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L129) (BR1_TS4_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-039894.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039894.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039918](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L153) (BR1_TS5_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-039918.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039918.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039942](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L177) (BR1_TS6_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-039942.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039942.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039966](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L201) (BR1_TS7_TC8) | 1 | `X_AWAITING_TABLE` | [TD-TC-039966.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039966.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-039991](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L226) (BR1_TS8_TC7) | 1 | `X_AWAITING_TABLE` | [TD-TC-039991.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039991.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-040013](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L248) (BR1_TS9_TC6) | 1 | `X_AWAITING_TABLE` | [TD-TC-040013.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040013.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-040047](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L282) (BR1_TS10_TC5) | 1 | `X_AWAITING_TABLE` | [TD-TC-040047.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040047.json) | `Table 082` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-040118](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L353) (BR1_TS11_TC59) | 1 | `X_AWAITING_TABLE` | [TD-TC-040118.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040118.json) | `Table 003` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-040122](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L357) (BR1_TS11_TC63) | 1 | `X_AWAITING_TABLE` | [TD-TC-040122.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040122.json) | `Table 004` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| … | | 50 more in the sheet (Findings, filter on I-03) | | | |

Scenarios: BR1_TS11 (5), BR1_TS12 (5), BR1_TS13 (5), BR1_TS14 (5), BR1_TS15 (5), BR1_TS16 (5), BR1_TS17 (5), BR1_TS18 (5), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1) …

**How to check by hand:** VariableInformationLength / VariableInformation hold <AWAITING:...>; awaiting_inputs lists the value.

**Fix:** Fill in the client value and recalculate Variable Information Length and Segment 111 Segment Length.

### 6.4 I-04 - EMV chip data is generated, not from a card / simulator

**607 test cases, 2,049 findings** - kind: Awaiting client input / run-time value

Rules: `EMV_CA_CHECKSUM_SAMPLE` (607), `EMV_PLACEHOLDER_CRYPTO` (607), `EMV_TVR_CVM_MISMATCH` (587), `EMV_CONTACTLESS_TEMPLATE` (248).

ATL105:

- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."
- Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags."

What is wrong (one example per kind of problem):

- `EMV_CA_CHECKSUM_SAMPLE` (TC-038593) - The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- `EMV_TVR_CVM_MISMATCH` (TC-038593) - TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.
- `EMV_PLACEHOLDER_CRYPTO` (TC-038593) - ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- `EMV_CONTACTLESS_TEMPLATE` (TC-038594) - The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.
- `EMV_TVR_CVM_MISMATCH` (TC-039832) - TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 440302) say enciphered PIN verified by the card and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038593](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2) (BR1_TS11_TC2) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-038593](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2) (BR1_TS11_TC2) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-038593](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L2) (BR1_TS11_TC2) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=A…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-038594](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L3) (BR1_TS12_TC1) | - | `EMV_CONTACTLESS_TEMPLATE` | EMV Request Data Segment -> EMVChipData (every EMV request) | `22 tags, CVM 9F34 = 5E0302` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| [TC-039832](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L67) (BR1_TS1_TC25) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=440302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-038594](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L3) (BR1_TS12_TC1) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-038594](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L3) (BR1_TS12_TC1) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-038594](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L3) (BR1_TS12_TC1) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=8…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=6…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-038599](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L8) (BR1_TS21_TC2) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| … | | 2,037 more in the sheet (Findings, filter on I-04) | | | |

Scenarios: BR1_TS12 (456), BR1_TS11 (353), BR1_TS154 (178), BR1_TS173 (169), BR1_TS155 (118), BR1_TS174 (118), BR1_TS2 (99), BR1_TS54 (90), BR1_TS1 (81), BR1_TS22 (36), BR1_TS21 (33), BR1_TS147 (33), BR1_TS148 (28), BR1_TS161 (18), BR1_TS168 (18) …

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

- `TOTALS_VERSION_PLACEHOLDER` (TC-039828) - ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039828](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L63) (BR1_TS1_TC21) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039828.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039828.step3.totals.json) | `3W92CYI0, PWPVVWSL, BF8NGGH9` | Use the versions of the certified device that runs the test. |
| [TC-039829](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L64) (BR1_TS1_TC22) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039829.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039829.step3.totals.json) | `3ARA1AP1, H0G00RY9, E6TBYPQH` | Use the versions of the certified device that runs the test. |
| [TC-039830](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L65) (BR1_TS1_TC23) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039830.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039830.step2.totals.json) | `ZACY3B1W, LLBRLKNU, AVCX58MS` | Use the versions of the certified device that runs the test. |
| [TC-039852](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L87) (BR1_TS2_TC17) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039852.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039852.step3.totals.json) | `7USVQHHW, PT8NDQS3, MVD5E0A1` | Use the versions of the certified device that runs the test. |
| [TC-039853](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L88) (BR1_TS2_TC18) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039853.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039853.step3.totals.json) | `YH2NJSCX, ZTWZ7KKV, JLAVTK3W` | Use the versions of the certified device that runs the test. |
| [TC-039854](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L89) (BR1_TS2_TC19) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039854.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039854.step2.totals.json) | `TD7PTDZQ, W9J1BETE, RAV889GO` | Use the versions of the certified device that runs the test. |
| [TC-039876](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L111) (BR1_TS3_TC16) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039876.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039876.step3.totals.json) | `3TIA2QIU, JQ42M2OH, 6ARFGL9C` | Use the versions of the certified device that runs the test. |
| [TC-039877](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L112) (BR1_TS3_TC17) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039877.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039877.step3.totals.json) | `YL3BK8MO, NEGZGURJ, W1PNJ4OC` | Use the versions of the certified device that runs the test. |
| [TC-039878](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L113) (BR1_TS3_TC18) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039878.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039878.step2.totals.json) | `KD4CO2JF, 9U4D4F8C, VVE7EKRE` | Use the versions of the certified device that runs the test. |
| [TC-039902](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L137) (BR1_TS4_TC15) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039902.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039902.step3.totals.json) | `OA092KJ5, S5AI55QP, KCTVMCCN` | Use the versions of the certified device that runs the test. |
| [TC-039903](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L138) (BR1_TS4_TC16) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039903.step3.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039903.step3.totals.json) | `G9XKHZHP, BHVEV3E7, 8SVPUJDO` | Use the versions of the certified device that runs the test. |
| [TC-039904](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L139) (BR1_TS4_TC17) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-039904.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039904.step2.totals.json) | `63U1OU41, 0FWOYU6D, GPORMFFX` | Use the versions of the certified device that runs the test. |
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

- `X_SYNTHESIZED_CONTENT` (TC-039818) - The test case generated the Segment 153 values itself (spec_tlv_composite); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's network-token entitlement and a real provisioned network token.
- `X_SYNTHESIZED_CONTENT` (TC-039967) - The test case generated the Segment 145 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: an enhanced fleet card and its fleet sub-segment data.
- `X_SYNTHESIZED_CONTENT` (TC-040114) - The test case generated the Segment 108 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's loyalty program data and loyalty card.
- `X_SYNTHESIZED_CONTENT` (TC-040127) - The test case generated the Segment 104 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: a purchase (commercial) card and its purchase-card data.
- `X_SYNTHESIZED_CONTENT` (TC-040358) - The test case generated the Segment 123 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: a tokenized card and its token/cryptogram data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039818](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L53) (BR1_TS1_TC11) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039818.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039818.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039967](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L202) (BR1_TS7_TC9) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039967.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039967.json) | `format_synthesis` | Client to supply an enhanced fleet card and its fleet sub-segment data; keep the test case out of 'ready' until then. |
| [TC-040114](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L349) (BR1_TS11_TC55) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-040114.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040114.json) | `format_synthesis` | Client to supply the merchant's loyalty program data and loyalty card; keep the test case out of 'ready' until then. |
| [TC-040127](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L362) (BR1_TS11_TC68) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-040127.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040127.json) | `format_synthesis` | Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then. |
| [TC-040358](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L593) (BR1_TS13_TC67) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-040358.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040358.json) | `format_synthesis` | Client to supply a tokenized card and its token/cryptogram data; keep the test case out of 'ready' until then. |
| [TC-039842](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L77) (BR1_TS2_TC7) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039842.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039842.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039866](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L101) (BR1_TS3_TC6) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039866.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039866.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039892](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L127) (BR1_TS4_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039892.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039892.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039916](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039916.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039916.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039940](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L175) (BR1_TS6_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039940.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039940.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039964](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L199) (BR1_TS7_TC6) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039964.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039964.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-039989](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L224) (BR1_TS8_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039989.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039989.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| … | | 53 more in the sheet (Findings, filter on I-06) | | | |

Scenarios: BR1_TS15 (8), BR1_TS13 (7), BR1_TS14 (7), BR1_TS16 (7), BR1_TS17 (7), BR1_TS11 (6), BR1_TS12 (6), BR1_TS18 (6), BR1_TS7 (2), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1) …

**How to check by hand:** In test_cases.jsonl the segment fields have method format_synthesis / spec_tlv_composite.

**Fix:** Client to supply the data listed in the finding; keep the test case out of 'ready' until then.

### 6.7 I-07 - Decline trigger needed: the request is identical to the approval test

**73 test cases, 73 findings, 73 test data files** - kind: Awaiting client input / run-time value

Rules: `X_SAME_REQUEST_NEEDS_TRIGGER` (73).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST_NEEDS_TRIGGER` (TC-039837) - Apart from the Sequence Number the message is identical to TC-039814 (all segments compared). TC-039814 expects 0; this test expects 1. The test case asks for a decline trigger in open_items.
- `X_SAME_REQUEST_NEEDS_TRIGGER` (TC-041112) - Apart from the Sequence Number the message is identical to TC-041104 (all segments compared). TC-041104 expects 0/2; this test expects 1. The test case asks for a decline trigger in open_items.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039837](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L72) (BR1_TS1_TC30) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039837.json vs TD-TC-039814.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-041112](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1347) (BR1_TS21_TC11) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-041112.json vs TD-TC-041104.json | `expected 1 vs 0/2` | Supply the decline trigger so the request differs. |
| [TC-039861](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L96) (BR1_TS2_TC26) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039861.json vs TD-TC-039838.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-039886](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L121) (BR1_TS3_TC26) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039886.json vs TD-TC-039862.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-039911](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L146) (BR1_TS4_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039911.json vs TD-TC-039888.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-039935](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L170) (BR1_TS5_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039935.json vs TD-TC-039912.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-039959](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L194) (BR1_TS6_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039959.json vs TD-TC-039936.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-039984](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L219) (BR1_TS7_TC26) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-039984.json vs TD-TC-039960.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-040008](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L243) (BR1_TS8_TC24) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-040008.json vs TD-TC-039985.json | `expected 1 vs 0` | Supply the decline trigger so the request differs. |
| [TC-040108](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L343) (BR1_TS11_TC49) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-040108.json vs TD-TC-040065.json | `expected 1 vs 2` | Supply the decline trigger so the request differs. |
| [TC-040164](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L399) (BR1_TS11_TC105) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-040164.json vs TD-TC-040065.json | `expected 1 vs 2` | Supply the decline trigger so the request differs. |
| [TC-040222](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L457) (BR1_TS12_TC45) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-040222.json vs TD-TC-040179.json | `expected 1 vs 2` | Supply the decline trigger so the request differs. |
| … | | 61 more in the sheet (Findings, filter on I-07) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1) …

**How to check by hand:** open_items asks for a decline test card / simulator trigger.

**Fix:** Client to supply the decline trigger; until then the test sends the approval test's request.

### 6.8 I-08 - No test data generated for a host test case

**3 test cases, 3 findings** - kind: Awaiting client input / run-time value

Rules: `X_NO_TEST_DATA` (3).

What is wrong (one example per kind of problem):

- `X_NO_TEST_DATA` (TC-038595) - The test case sends a message to BUYPASS (EMV Financial Transaction Request) but has no test data file (status blocked_no_test_data).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038595](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L4) (BR1_TS29_TC4) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-038596](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L5) (BR1_TS1_TC5) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |
| [TC-038614](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L23) (BR1_TS147_TC4) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |

Scenarios: BR1_TS29 (1), BR1_TS1 (1), BR1_TS147 (1)

**How to check by hand:** test_data_file is empty in test_cases.jsonl; status blocked_no_test_data.

**Fix:** Generate the test data when the missing input is available.

### 6.9 I-09 - Segment 100 value awaited from the client (<AWAITING:...>)

**1 test case, 1 finding, 1 test data file** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_VALUE` (1).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_AWAITING_VALUE` (TC-038631) - The field holds '<AWAITING:AccountNumber>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038631](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L40) (BR1_TS2_TC2) | 1 | `X_AWAITING_VALUE` | [TD-TC-038631.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038631.json#L11) | `<AWAITING:AccountNumber>` | Client to supply the value (for Account Number: a MasterCard test card). |

Scenarios: BR1_TS2 (1)

**How to check by hand:** The field holds <AWAITING:...>; the test case field has method awaiting_client_value.

**Fix:** Client to supply the value (for Account Number: a MasterCard test card).

### 6.10 I-10 - Claimed table not in the test data yet - the test case lists it as client data to supply

**398 test cases, 398 findings, 398 test data files** - kind: Awaiting client input / run-time value

Rules: `X_CONDITION_AWAITED` (398).

ATL105:

- Element 111 Variable Information Indicator (Chapter 13), PDF p.422 (printed 13-82): "Fixed length of 3 alphanumeric characters"
- Element 116 Additional Information Indicator (Chapter 13), PDF p.433 (printed 13-93): "A Financial Transaction Request initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction Response."
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment"
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The authorizer returns a response containing both the transaction authorization Response Code and the AVS information in the Additional Information Data Segment"
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "Address Verification Service (AVS) is a credit card authorization tool used for transactions performed primarily in a non-face-to-face environment, such as mail order, phone order, or customer-activated terminal (CAT)."
- Element 113 Variable Information (Chapter 13), PDF p.426 (printed 13-86): "When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here."
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or"
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "The remaining 00–20 characters contain street address information."
- Appendix K. Additional Information Data Layouts - AVS or NVS or Both Information (Table 003), PDF p.643 (printed Appendix K-4): "Identifies the AVS or NVS or both response from the authorizer."
- Element 111 Variable Information Indicator (Chapter 13), PDF p.423 (printed 13-83): "003 Address verification service (AVS) indicator"

What is wrong (one example per kind of problem):

- `X_CONDITION_AWAITED` (TC-040030) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information; the request does not send AVS data (Table 003) yet and the test case lists it as client data ("AVS-mismatch test card/address.").
- `X_CONDITION_AWAITED` (TC-040066) - The objective says the test sends Table 006 Currency Code Information; the test data does not contain it yet and the test case lists it as client data to supply ("Table 006 Currency Code Information data from the client's test data (the layout gives no single spec value) - p.555").
- `X_CONDITION_AWAITED` (TC-040067) - The objective says the test sends Table 008 Balance Information; the test data does not contain it yet and the test case lists it as client data to supply ("Table 008 Balance Information data from the client's test data (the layout gives no single spec value) - p.556").
- `X_CONDITION_AWAITED` (TC-040068) - The objective says the test sends Table 019 Recharge Type Information; the test data does not contain it yet and the test case lists it as client data to supply ("Table 019 Recharge Type Information data from the client's test data (the layout gives no single spec value) - p.562").
- `X_CONDITION_AWAITED` (TC-040070) - The objective says the test sends Table 027 Customer Name; the test data does not contain it yet and the test case lists it as client data to supply ("Table 027 Customer Name data from the client's test data (the layout gives no single spec value) - p.566").
- `X_CONDITION_AWAITED` (TC-040071) - The objective says the test sends Table 029 Universal Unique Identifier; the test data does not contain it yet and the test case lists it as client data to supply ("Table 029 Universal Unique Identifier data from the client's test data (the layout gives no single spec value) - p.567").
- `X_CONDITION_AWAITED` (TC-040073) - The objective says the test sends Table 033 Re-Price Data Request Information; the test data does not contain it yet and the test case lists it as client data to supply ("Table 033 Re-Price Data Request Information data from the client's test data (the layout gives no single spec value) - p.578").
- `X_CONDITION_AWAITED` (TC-040074) - The objective says the test sends Table 037 MCX Checkout Token; the test data does not contain it yet and the test case lists it as client data to supply ("Table 037 MCX Checkout Token data from the client's test data (the layout gives no single spec value) - p.582").
- `X_CONDITION_AWAITED` (TC-040075) - The objective says the test sends Table 039 Fraud Enhanced Data; the test data does not contain it yet and the test case lists it as client data to supply ("Table 039 Fraud Enhanced Data data from the client's test data (the layout gives no single spec value) - p.582").
- `X_CONDITION_AWAITED` (TC-040076) - The objective says the test sends Table 040 Final Amount Indicator; the test data does not contain it yet and the test case lists it as client data to supply ("Table 040 Final Amount Indicator data from the client's test data (the layout gives no single spec value) - p.583").
- `X_CONDITION_AWAITED` (TC-040077) - The objective says the test sends Table 041 Country Subdivision; the test data does not contain it yet and the test case lists it as client data to supply ("Table 041 Country Subdivision data from the client's test data (the layout gives no single spec value) - p.583").
- `X_CONDITION_AWAITED` (TC-040078) - The objective says the test sends Table 042 Cardholder Language; the test data does not contain it yet and the test case lists it as client data to supply ("Table 042 Cardholder Language data from the client's test data (the layout gives no single spec value) - p.583").

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040030](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L265) (BR1_TS9_TC23) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040030.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040030.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040066](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L301) (BR1_TS11_TC7) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040066.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040066.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 006 Currency Code Information data; then add it to the request and recalculate the lengths. |
| [TC-040067](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L302) (BR1_TS11_TC8) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040067.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040067.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 008 Balance Information data; then add it to the request and recalculate the lengths. |
| [TC-040068](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L303) (BR1_TS11_TC9) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040068.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040068.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 019 Recharge Type Information data; then add it to the request and recalculate the lengths. |
| [TC-040070](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L305) (BR1_TS11_TC11) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040070.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040070.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 027 Customer Name data; then add it to the request and recalculate the lengths. |
| [TC-040071](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L306) (BR1_TS11_TC12) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040071.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040071.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 029 Universal Unique Identifier data; then add it to the request and recalculate the lengths. |
| [TC-040073](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L308) (BR1_TS11_TC14) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040073.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040073.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 033 Re-Price Data Request Information data; then add it to the request and recalculate the lengths. |
| [TC-040074](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L309) (BR1_TS11_TC15) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040074.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040074.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 037 MCX Checkout Token data; then add it to the request and recalculate the lengths. |
| [TC-040075](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L310) (BR1_TS11_TC16) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040075.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040075.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 039 Fraud Enhanced Data data; then add it to the request and recalculate the lengths. |
| [TC-040076](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L311) (BR1_TS11_TC17) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040076.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040076.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 040 Final Amount Indicator data; then add it to the request and recalculate the lengths. |
| [TC-040077](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L312) (BR1_TS11_TC18) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040077.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040077.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 041 Country Subdivision data; then add it to the request and recalculate the lengths. |
| [TC-040078](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L313) (BR1_TS11_TC19) | 1 | `X_CONDITION_AWAITED` | [TD-TC-040078.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040078.json) | `Segment 111 tables sent: 005, 030, 031` | Client to supply the Table 042 Cardholder Language data; then add it to the request and recalculate the lengths. |
| … | | 386 more in the sheet (Findings, filter on I-10) | | | |

Scenarios: BR1_TS17 (42), BR1_TS11 (39), BR1_TS12 (39), BR1_TS13 (39), BR1_TS14 (39), BR1_TS15 (39), BR1_TS16 (39), BR1_TS18 (39), BR1_TS154 (24), BR1_TS173 (24), BR1_TS54 (23), BR1_TS9 (1), BR1_TS10 (1), BR1_TS155 (1), BR1_TS156 (1) …

**How to check by hand:** test_objective names the table; open_items names it as client data; the status is not 'ready'.

**Fix:** Client to supply the table data; then add the table to Segment 111 and recalculate its length.

## 7. Points to confirm with BUYPASS / SME

### 7.1 C-06 - Time-out Reversal sent after a step that is expected to be answered

**62 test cases, 62 findings, 62 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_TOR_AFTER_RESPONSE` (62).

ATL105:

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR"

What is wrong (one example per kind of problem):

- `X_TOR_AFTER_RESPONSE` (TC-039817) - ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0, so the device would not send a TOR.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039817](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L52) (BR1_TS1_TC10) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039822](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC15) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039823](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC16) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039829](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L64) (BR1_TS1_TC22) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039836](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L71) (BR1_TS1_TC29) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039841](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L76) (BR1_TS2_TC6) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039846](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L81) (BR1_TS2_TC11) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039847](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L82) (BR1_TS2_TC12) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039853](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L88) (BR1_TS2_TC18) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039860](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L95) (BR1_TS2_TC25) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039865](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L100) (BR1_TS3_TC5) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-039870](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L105) (BR1_TS3_TC10) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| … | | 50 more in the sheet (Findings, filter on C-06) | | | |

Scenarios: BR1_TS1 (5), BR1_TS2 (5), BR1_TS3 (5), BR1_TS4 (5), BR1_TS5 (5), BR1_TS6 (5), BR1_TS7 (5), BR1_TS8 (5), BR1_TS9 (5), BR1_TS10 (5), BR1_TS47 (2), BR1_TS147 (2), BR1_TS168 (2), BR1_TS178 (2), BR1_TS183 (2) …

**How to check by hand:** Read tx_legs: the original step expects a Response Code and has no 'no response' harness directive, yet the next step is a Time-out Reversal.

**Fix:** Make the original step a fault-injected 'no response' (or a late response the harness ignores).

### 7.2 C-07 - 'Different terminal' negative uses a generated terminal that is not set up at BUYPASS

**39 test cases, 39 findings, 39 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_RANDOM_TERMINAL` (39).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `X_NEG_RANDOM_TERMINAL` (TC-038595) - Step 1 uses the generated Terminal Identifier 'None' (method deliberate_violation_missing_segment). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.
- `X_NEG_RANDOM_TERMINAL` (TC-039816) - Step 2 uses the generated Terminal Identifier 'XT47320042968001199580' (method deliberate_violation_missing_carry). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038595](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L4) (BR1_TS29_TC4) | 1 | `X_NEG_RANDOM_TERMINAL` | - | `None` | Use a second real terminal of the same merchant. |
| [TC-039816](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC9) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039816.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039816.step2.reversal.json#L9) | `XT47320042968001199580` | Use a second real terminal of the same merchant. |
| [TC-039817](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L52) (BR1_TS1_TC10) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039817.step2.timeout-reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039817.step2.timeout-reversal.json#L9) | `CC32845560270643250407` | Use a second real terminal of the same merchant. |
| [TC-039840](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L75) (BR1_TS2_TC5) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039840.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039840.step2.reversal.json#L9) | `OF64126685792914682812` | Use a second real terminal of the same merchant. |
| [TC-039841](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L76) (BR1_TS2_TC6) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039841.step2.timeout-reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039841.step2.timeout-reversal.json#L9) | `VU22033661055981402050` | Use a second real terminal of the same merchant. |
| [TC-039864](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L99) (BR1_TS3_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039864.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039864.step2.reversal.json#L9) | `AJ01230409610867043426` | Use a second real terminal of the same merchant. |
| [TC-039865](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L100) (BR1_TS3_TC5) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039865.step2.timeout-reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039865.step2.timeout-reversal.json#L9) | `UB29258513570598510183` | Use a second real terminal of the same merchant. |
| [TC-039890](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L125) (BR1_TS4_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039890.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039890.step2.reversal.json#L9) | `BJ01701389398609013256` | Use a second real terminal of the same merchant. |
| [TC-039891](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L126) (BR1_TS4_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039891.step2.timeout-reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039891.step2.timeout-reversal.json#L9) | `CN55143794088586247460` | Use a second real terminal of the same merchant. |
| [TC-039914](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) (BR1_TS5_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039914.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) | `VS24671841985066391107` | Use a second real terminal of the same merchant. |
| [TC-039915](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) (BR1_TS5_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039915.step2.timeout-reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L9) | `QL66203054504417704598` | Use a second real terminal of the same merchant. |
| [TC-039938](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L173) (BR1_TS6_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039938.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039938.step2.reversal.json#L9) | `FR14459787002357444489` | Use a second real terminal of the same merchant. |
| … | | 27 more in the sheet (Findings, filter on C-07) | | | |

Scenarios: BR1_TS1 (2), BR1_TS2 (2), BR1_TS3 (2), BR1_TS4 (2), BR1_TS5 (2), BR1_TS6 (2), BR1_TS7 (2), BR1_TS8 (2), BR1_TS9 (2), BR1_TS10 (2), BR1_TS29 (1), BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1) …

**How to check by hand:** Read the follow-on step's Terminal Identifier: method deliberate_violation_missing_carry, a generated value.

**Fix:** Use a second real terminal of the same merchant so the decline proves the same-device rule.

### 7.3 C-08 - Same request as another test case (difference only on the device / response side)

**584 test cases, 584 findings, 584 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SAME_REQUEST` (584).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST` (TC-039881) - Apart from the Sequence Number the message is identical to TC-039862 (all segments compared). TC-039862 expects 0; this test expects 0. What makes the two tests different is not in the message sent to BUYPASS.
- `X_SAME_REQUEST` (TC-041128) - Apart from the Sequence Number the message is identical to TC-041122 (all segments compared). TC-041122 expects 0/2; this test expects 0/2. What makes the two tests different is not in the message sent to BUYPASS.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039881](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L116) (BR1_TS3_TC21) | 1 | `X_SAME_REQUEST` | TD-TC-039881.json vs TD-TC-039862.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-041128](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1363) (BR1_TS23_TC7) | 1 | `X_SAME_REQUEST` | TD-TC-041128.json vs TD-TC-041122.json | `expected 0/2 vs 0/2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-039906](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L141) (BR1_TS4_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-039906.json vs TD-TC-039888.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-039930](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L165) (BR1_TS5_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-039930.json vs TD-TC-039912.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-039954](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L189) (BR1_TS6_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-039954.json vs TD-TC-039936.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-039979](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L214) (BR1_TS7_TC21) | 1 | `X_SAME_REQUEST` | TD-TC-039979.json vs TD-TC-039960.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040003](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L238) (BR1_TS8_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-040003.json vs TD-TC-039985.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040029](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L264) (BR1_TS9_TC22) | 1 | `X_SAME_REQUEST` | TD-TC-040029.json vs TD-TC-040009.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040058](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L293) (BR1_TS10_TC16) | 1 | `X_SAME_REQUEST` | TD-TC-040058.json vs TD-TC-040043.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040066](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L301) (BR1_TS11_TC7) | 1 | `X_SAME_REQUEST` | TD-TC-040066.json vs TD-TC-040065.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040067](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L302) (BR1_TS11_TC8) | 1 | `X_SAME_REQUEST` | TD-TC-040067.json vs TD-TC-040065.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040068](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L303) (BR1_TS11_TC9) | 1 | `X_SAME_REQUEST` | TD-TC-040068.json vs TD-TC-040065.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| … | | 572 more in the sheet (Findings, filter on C-08) | | | |

Scenarios: BR1_TS17 (51), BR1_TS16 (49), BR1_TS13 (47), BR1_TS11 (45), BR1_TS12 (45), BR1_TS15 (45), BR1_TS18 (45), BR1_TS14 (44), BR1_TS154 (35), BR1_TS173 (35), BR1_TS54 (25), BR1_TS155 (10), BR1_TS158 (10), BR1_TS174 (10), BR1_TS175 (10) …

**How to check by hand:** Compare both test data files: identical apart from the Sequence Number.

**Fix:** Check the difference on the device or in the response; optionally use another card.

### 7.4 C-09 - Segment sent although ATL105 scopes it to other card programs

**23 test cases, 23 findings, 23 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_SCOPE` (23).

ATL105:

- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "The Enhanced Fleet Request Segment contains fleet data that applies to the enhanced fleet offerings such as Wex OTR, Comdata, Voyager EMV, and Visa Fleet 2.0."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data."
- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV transactions."

What is wrong (one example per kind of problem):

- `X_SEGMENT_SCOPE` (TC-038623) - ATL105 describes Segment 101 as fleet data, sent only on transactions requiring fleet data. This test uses a MasterCard Credit card (card type 020), which is not a fleet card.
- `X_SEGMENT_SCOPE` (TC-039967) - ATL105 describes Segment 145 as fleet data for enhanced fleet offerings (WEX OTR, Comdata, Voyager EMV, Visa Fleet 2.0). This test uses a MasterCard Credit card (card type 020), which is not a fleet card.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038623](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L32) (BR1_TS154_TC2) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-038623.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038623.json) | `Segment 101` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-039967](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L202) (BR1_TS7_TC9) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-039967.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039967.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-038624](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L33) (BR1_TS147_TC2) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-038624.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038624.json) | `Segment 101` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040129](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L364) (BR1_TS11_TC70) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040129.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040129.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040243](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L478) (BR1_TS12_TC66) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040243.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040243.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040359](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L594) (BR1_TS13_TC68) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040359.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040359.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040560](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L795) (BR1_TS14_TC64) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040560.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040560.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040671](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L906) (BR1_TS15_TC65) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040671.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040671.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040789](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1024) (BR1_TS16_TC68) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040789.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040789.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-040906](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1141) (BR1_TS17_TC73) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-040906.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040906.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-041021](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1256) (BR1_TS18_TC63) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-041021.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041021.json) | `Segment 145` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| [TC-041338](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1573) (BR1_TS154_TC42) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-041338.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041338.json) | `Segment 101` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| … | | 11 more in the sheet (Findings, filter on C-09) | | | |

Scenarios: BR1_TS154 (2), BR1_TS147 (1), BR1_TS7 (1), BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS155 (1), BR1_TS156 (1), BR1_TS157 (1), BR1_TS158 (1) …

**How to check by hand:** Read the segment description in section 11.1 / 12.x and compare with the card program (MasterCard Credit, card type 020).

**Fix:** Confirm with BUYPASS or drop the segment from MasterCard Credit tests.

### 7.5 S-01 - Expiry format for stored credentials (entry mode 10) not stated

**196 test cases, 196 findings** - kind: To confirm with BUYPASS / SME

Rules: `COF_EXPIRY_FORMAT` (196).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."

What is wrong (one example per kind of problem):

- `COF_EXPIRY_FORMAT` (TC-038597) - The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038597](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L6) (BR1_TS17_TC2) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0527` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-038600](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L9) (BR1_TS17_TC1) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0630` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-038602](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L11) (BR1_TS7_TC1) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1129` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-038605](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L14) (BR1_TS17_TC3) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1130` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039960](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L195) (BR1_TS7_TC2) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039961](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L196) (BR1_TS7_TC3) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1131` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039962](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L197) (BR1_TS7_TC4) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0828` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039963](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L198) (BR1_TS7_TC5) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0632` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039964](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L199) (BR1_TS7_TC6) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039965](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L200) (BR1_TS7_TC7) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039966](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L201) (BR1_TS7_TC8) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-039967](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L202) (BR1_TS7_TC9) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |
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

- `TOTALS_AO_BUCKET` (TC-040138) - Section 12.6 lists Preauthorization (AO) as bucket 4, and the grand total leaves the AO bucket out. This flow has an approved Authorization Only, but only the CC bucket is sent. ATL105 says cancellations subtract from the AO bucket; it does not say whether a completed authorization stays in it.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040138](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L373) (BR1_TS11_TC79) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040138.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040138.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-040252](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L487) (BR1_TS12_TC75) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040252.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040252.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-040373](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L608) (BR1_TS13_TC82) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040373.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040373.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-040569](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L804) (BR1_TS14_TC73) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040569.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040569.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-040681](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L916) (BR1_TS15_TC75) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040681.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040681.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-040798](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1033) (BR1_TS16_TC77) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040798.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040798.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-040917](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1152) (BR1_TS17_TC84) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-040917.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040917.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-041030](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1265) (BR1_TS18_TC72) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-041030.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041030.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-041075](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1310) (BR1_TS19_TC7) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-041075.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041075.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| [TC-041099](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1334) (BR1_TS20_TC7) | 2 | `TOTALS_AO_BUCKET` | [TD-TC-041099.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041099.step2.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |

Scenarios: BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS19 (1), BR1_TS20 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.7 S-03 - Segment length: ATL105 Field Separator rule of the segment is unclear

**53 test cases, 53 findings, 53 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_LENGTH_UNCLEAR` (53).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "There is a Field Separator between Field Nos. 1 and 2."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "Field Separators are not there between the elements in Network Token Data Layouts."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."

What is wrong (one example per kind of problem):

- `X_SEGMENT_LENGTH_UNCLEAR` (TC-038611) - '121' assumes a Field Separator after every field (121). ATL105 reads differently for this segment - Field Separators up to Field 13 only (literal reading of p.238): 119.
- `X_SEGMENT_LENGTH_UNCLEAR` (TC-039818) - '051' assumes a Field Separator after every field (051). ATL105 reads differently for this segment - Field Separator only between Fields 1 and 2 (literal reading of p.310): 049; Field Separator after Fields 1 and 2, none after Field 3: 050.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038611](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L20) (BR1_TS148_TC1) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-038611.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038611.json#L7) | `121` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039818](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L53) (BR1_TS1_TC11) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039818.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039818.json#L61) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-038625](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L34) (BR1_TS30_TC1) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-038625.json](../../mastercard_credit_new_16_11/test_data/TD-TC-038625.json#L7) | `122` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039842](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L77) (BR1_TS2_TC7) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039842.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039842.json#L61) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039866](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L101) (BR1_TS3_TC6) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039866.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039866.json#L50) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039892](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L127) (BR1_TS4_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039892.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039892.json#L50) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039916](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039916.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L50) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039940](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L175) (BR1_TS6_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039940.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039940.json#L50) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039964](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L199) (BR1_TS7_TC6) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039964.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039964.json#L65) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-039989](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L224) (BR1_TS8_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039989.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039989.json#L50) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-040109](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L344) (BR1_TS11_TC50) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-040109.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040109.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-040114](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L349) (BR1_TS11_TC55) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-040114.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040114.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |
| … | | 41 more in the sheet (Findings, filter on S-03) | | | |

Scenarios: BR1_TS15 (6), BR1_TS11 (5), BR1_TS12 (5), BR1_TS13 (5), BR1_TS14 (5), BR1_TS16 (5), BR1_TS17 (5), BR1_TS18 (5), BR1_TS148 (1), BR1_TS30 (1), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1) …

**How to check by hand:** Recalculate the length under both readings shown in the finding.

**Fix:** Confirm with BUYPASS.

### 7.8 S-04 - Network Token Input Indicator 'Y' while Element 2 carries a clear card number

**26 test cases, 26 findings, 26 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_TOKEN_INDICATOR` (26).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN"
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_TOKEN_INDICATOR` (TC-039818) - Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number 515864…7836 and the token is only in table 001. ATL105 does not say which field carries the token when the indicator is Y.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039818](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L53) (BR1_TS1_TC11) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039818.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039818.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039842](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L77) (BR1_TS2_TC7) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039842.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039842.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039866](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L101) (BR1_TS3_TC6) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039866.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039866.json#L51) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039892](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L127) (BR1_TS4_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039892.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039892.json#L51) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039916](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039916.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L51) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039940](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L175) (BR1_TS6_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039940.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039940.json#L51) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039964](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L199) (BR1_TS7_TC6) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039964.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039964.json#L66) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-039989](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L224) (BR1_TS8_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039989.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039989.json#L51) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-040130](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L365) (BR1_TS11_TC71) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-040130.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040130.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-040154](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L389) (BR1_TS11_TC95) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-040154.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040154.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-040244](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L479) (BR1_TS12_TC67) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-040244.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040244.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-040268](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L503) (BR1_TS12_TC91) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-040268.json](../../mastercard_credit_new_16_11/test_data/TD-TC-040268.json#L62) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| … | | 14 more in the sheet (Findings, filter on S-04) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS1 (1), BR1_TS2 (1), BR1_TS3 (1), BR1_TS4 (1), BR1_TS5 (1), BR1_TS6 (1), BR1_TS7 (1) …

**How to check by hand:** Parse Network Token Data table 004.

**Fix:** Confirm with BUYPASS which field carries the token when the indicator is Y.

### 7.9 S-05 - Other points to confirm

**15 test cases, 15 findings** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_PREMISE_CONFIRM` (15).

ATL105:

- Section 12.19 NFC Payment Tokenization Data Segment, PDF p.267 (printed 12-50): "It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "When a merchant is entitled to use both First Data provisioned Network Tokens and Auth Optimization service, the merchant is required to send the same and exact information from the original transaction request in all the follow-up transactions"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."
- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"
- Appendix G. Valid Transaction Type Codes, PDF p.546 (printed Appendix G-2): "A Account Verification Account verification"
- Appendix G. Valid Transaction Type Codes, PDF p.546 (printed Appendix G-2): "S Cancellation Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE_CONFIRM` (TC-038597) - p.267 requires Segment 123 only on transactions involving tokenized data (MasterCard Token, DSRP or Visa TAVV data). This test sends a card number without any token data, so leaving the segment out may be correct and BUYPASS may approve.
- `X_NEG_PREMISE_CONFIRM` (TC-038598) - p.310 requires the same Segment 153 data only in the follow-up transactions of a merchant entitled to both First Data Network Tokens and Auth Optimization. This test sends an original transaction without any network token data.
- `X_NEG_PREMISE_CONFIRM` (TC-040496) - Account Verification is not in the Section 10.1.2 list for credit, but Appendix G defines Transaction Type A; confirm.
- `X_NEG_PREMISE_CONFIRM` (TC-041333) - Appendix G defines Cancellation (S) for credit Authorization Only transactions; the cited text does not forbid it here.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038597](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L6) (BR1_TS17_TC2) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `NFC Payment Tokenization Data Segment (s… requires it` | Confirm with BUYPASS, or send a transaction for which the segment is required. |
| [TC-038598](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L7) (BR1_TS1_TC6) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Network Token Data Request (segment 153)… requires it` | Confirm with BUYPASS, or send a transaction for which the segment is required. |
| [TC-040496](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L731) (BR1_TS13_TC205) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Account Verification… -> declined` | Confirm with BUYPASS. |
| [TC-041333](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1568) (BR1_TS154_TC37) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041366](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1601) (BR1_TS155_TC9) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041397](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1632) (BR1_TS156_TC8) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041440](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1675) (BR1_TS157_TC9) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041469](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1704) (BR1_TS158_TC10) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041497](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1732) (BR1_TS159_TC8) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041525](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1760) (BR1_TS160_TC8) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041624](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1859) (BR1_TS173_TC33) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| [TC-041658](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1893) (BR1_TS174_TC8) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `Single rule broken: Cancellation sent wh… -> declined` | Confirm with BUYPASS. |
| … | | 3 more in the sheet (Findings, filter on S-05) | | | |

Scenarios: BR1_TS17 (1), BR1_TS1 (1), BR1_TS13 (1), BR1_TS154 (1), BR1_TS155 (1), BR1_TS156 (1), BR1_TS157 (1), BR1_TS158 (1), BR1_TS159 (1), BR1_TS160 (1), BR1_TS173 (1), BR1_TS174 (1), BR1_TS175 (1), BR1_TS176 (1), BR1_TS177 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS / SME.

### 7.10 S-06 - Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts

**8 test cases, 8 findings, 8 test data files** - kind: To confirm with BUYPASS / SME

Rules: `TOTALS_NEGATIVE_NET` (8).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places"

What is wrong (one example per kind of problem):

- `TOTALS_NEGATIVE_NET` (TC-041109) - Section 9.2: a Merchandise Return increases the count and decreases the dollar amount, so the card bucket nets to count 00001 and amount -$15.00. Card Type Total Amount (Element 15) is a fixed-length unsigned 8-digit amount and ATL105 does not say how a negative net amount is sent; the file sends 00000000.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-041109](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1344) (BR1_TS21_TC8) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041109.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041109.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041118](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1353) (BR1_TS22_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041118.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041118.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041127](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1362) (BR1_TS23_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041127.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041127.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041136](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1371) (BR1_TS24_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041136.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041136.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041145](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1380) (BR1_TS25_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041145.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041145.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041154](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1389) (BR1_TS26_TC7) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041154.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041154.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041164](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1399) (BR1_TS27_TC7) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041164.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041164.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |
| [TC-041173](../../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1408) (BR1_TS28_TC6) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-041173.step2.totals.json](../../mastercard_credit_new_16_11/test_data/TD-TC-041173.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |

Scenarios: BR1_TS21 (1), BR1_TS22 (1), BR1_TS23 (1), BR1_TS24 (1), BR1_TS25 (1), BR1_TS26 (1), BR1_TS27 (1), BR1_TS28 (1)

**How to check by hand:** Open the Totals Request file: count 00001 is right (a return increases the count), the amount nets to minus the return amount, and the file sends 00000000.

**Fix:** Confirm with BUYPASS how a negative net amount is reported in the Totals Request.

## 8. Negative tests - intended violations (correct)

145 negative test cases break their rule as designed (152 findings). They are not defects; check that each test breaks only that rule.

| Rule | Findings | Example |
|---|---:|---|
| `FIELD_EMPTY` | 45 | TC-038592 step 1: Partial Approval Indicator is empty - Required field Partial Approval Indicator omitted |
| `LINK_TERMINALIDENTIFIER` | 38 | TC-039816 step 2: Step 2: same terminal as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined |
| `LINK_SEQUENCENUMBER` | 28 | TC-039815 step 2: Step 2: same sequence number as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined |
| `TABLE_005` | 13 | TC-038635 step 1: Table 005 value is not valid - Variable Information one byte over its documented maximum length (982) |
| `X_FIELD_FORMAT` | 9 | TC-038594 step 1: Product Code Data Segment: Product Code breaks its Chapter 13 format - Product Code one byte over its documented maximum length (3) |
| `SEG130_MISSING` | 4 | TC-040037 step 1: The EMV Request Data Segment (130) is missing - Single rule broken: Mail/Phone Purchase with entry mode 05 (EMV Contact Chip, not supported) -> declined |
| `EMV_CSN_WIDTH` | 3 | TC-038626 step 1: EMV Card Sequence Number (Element 188) does not match Tag 5F34 in the chip data - The deliberately broken EMV Chip Data has no readable Tag 5F34, so Element 188 cannot be checked against it. |
| `FIELD_189` | 2 | TC-038603 step 1: EMV Chip Data Length (Element 189) does not match the chip data - Required field EMV Chip Data Length sent empty |
| `EMV_CHIP_DATA_FORMAT` | 2 | TC-038626 step 1: EMV Chip Data (Element 190) is not valid - EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field spe |
| `X_VI_TABLE_ID` | 2 | TC-038636 step 1: Variable Information Indicator 'AAA' is not an ATL105 table ID - Variable Information Indicator value not in its documented valid values |
| `FIELD_121` | 1 | TC-038593 step 1: Partial Approval Indicator is not valid - Partial Approval Indicator value not in its documented valid values |
| `FIELD_41` | 1 | TC-038616 step 1: Fuel Purchase Amount is not valid - Fuel Purchase Amount one byte over its documented maximum length (8) |
| `FIELD_2` | 1 | TC-038617 step 1: Account Number is not valid - Account Number one byte over its documented maximum length (24) |
| `FIELD_44` | 1 | TC-038621 step 1: Information Byte is not valid - Information Byte value not in its documented valid values |
| `X_SEG_MAX` | 1 | TC-038628 step 1: NFC Payment Tokenization Data Segment is longer than its maximum (186) - Cryptogram Token Data one byte over its documented maximum length (56) |
| `SEG111_LENGTH` | 1 | TC-038635 step 1: Segment 111 length is wrong - The declared Segment Length matches the content; the deliberately over-long field makes the segment longer than its 3-digit Segment Length can express. |

## 9. AI validator rules that did not apply to this run (value checked - correct)

| Rule | Findings | Test cases | Why the value is correct |
|---|---:|---:|---|
| `FIELD_12` | 12 | 12 | This step completes the Authorization Only of step 1 (preauthorized Purchase/Capture). Credit completions send Account Number = YYMM (Section 10.1.8.2), which is what the file has; the AI rule treated the step as a new purchase. |
| `FIELD_78` | 11 | 11 | The remaining balance after a partially approved Authorization Only is a new Authorization Only on the second card (Transaction Type 3, approval code 2). ATL105 supports partial approval for POS Authorization Only (Section 10.1.2.1); the AI rule only knew split tender after a purchase. |
| `RESPONSE_CODE` | 11 | 11 | The remaining balance after a partially approved Authorization Only is a new Authorization Only on the second card (Transaction Type 3, approval code 2). ATL105 supports partial approval for POS Authorization Only (Section 10.1.2.1); the AI rule only knew split tender after a purchase. |
| `EMV_CASHBACK_AMOUNTS` | 2 | 2 | The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization Only. The real problem of this test - optional fields filled without their condition - is D-02. |
| `TABLE_005` | 2 | 2 | A completion of an original read with full track data (entry mode 90) uses PAN Entry Mode 02 (Appendix J, p.638); the AI rule treated the step as a new magnetic-stripe purchase. |

Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator did not read segments 101-153; section 2 point 2 now checks them).

Run-wide checks: 1,112 single-step test cases send the same request as an earlier test case (all segments compared); 1,946 new transactions checked for a unique Sequence Number per terminal, 0 duplicate(s).

## 10. Result per scenario

| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |
|---|---|---|---:|---:|---:|---:|---:|---:|---|
| BR1_TS1 | Purchase/Capture – EMV Contact Chip | GENERAL | 30 | 5 | 25 | 0 | 0 | 0 | D-13 (4), D-09 (2) |
| BR1_TS2 | Purchase/Capture – EMV Contactless Chip | GENERAL | 26 | 2 | 24 | 0 | 0 | 0 | D-13 (2) |
| BR1_TS3 | Purchase/Capture – Magnetic Stripe | GENERAL | 27 | 1 | 24 | 0 | 0 | 2 | D-09 (2) |
| BR1_TS4 | Purchase/Capture – Contactless Magnetic Stripe | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS5 | Purchase/Capture – Manual / Keyed | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS6 | Purchase/Capture – Chip Fallback | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS7 | Purchase/Capture – Credential on File | CARD_NOT_PRESENT-0100 | 26 | 2 | 24 | 0 | 0 | 0 | D-03 (3), C-02 (1) |
| BR1_TS8 | Purchase/Capture – Contactless Mobile Commerce Device | GENERAL | 24 | 0 | 24 | 0 | 0 | 0 | - |
| BR1_TS9 | Mail/Phone Purchase – Manual / Keyed | CARD_NOT_PRESENT-2100 | 35 | 1 | 27 | 0 | 0 | 7 | C-01 (1) |
| BR1_TS10 | Mail/Phone Purchase – Credential on File | CARD_NOT_PRESENT-2100 | 22 | 1 | 21 | 0 | 0 | 0 | C-01 (1) |
| BR1_TS11 | Authorization Only – EMV Contact Chip | GENERAL | 119 | 24 | 95 | 0 | 0 | 0 | C-02 (6), C-11 (6), D-13 (4) |
| BR1_TS12 | Authorization Only – EMV Contactless Chip | GENERAL | 115 | 20 | 95 | 0 | 0 | 0 | C-11 (6), C-02 (4), D-02 (4) |
| BR1_TS13 | Authorization Only – Magnetic Stripe | GENERAL | 205 | 21 | 96 | 0 | 0 | 88 | C-02 (6), C-11 (6), D-02 (4) |
| BR1_TS14 | Authorization Only – Contactless Magnetic Stripe | GENERAL | 111 | 19 | 92 | 0 | 0 | 0 | C-11 (6), C-02 (4), D-02 (4) |
| BR1_TS15 | Authorization Only – Manual / Keyed | GENERAL | 115 | 21 | 94 | 0 | 0 | 0 | C-11 (6), C-02 (5), D-02 (4) |
| BR1_TS16 | Authorization Only – Chip Fallback | GENERAL | 115 | 19 | 96 | 0 | 0 | 0 | C-11 (6), C-02 (4), D-02 (4) |
| BR1_TS17 | Authorization Only – Credential on File | CARD_NOT_PRESENT-0100 | 125 | 23 | 102 | 0 | 0 | 0 | C-10 (6), C-11 (6), C-02 (5) |
| BR1_TS18 | Authorization Only – Contactless Mobile Commerce Device | GENERAL | 110 | 19 | 91 | 0 | 0 | 0 | C-11 (6), C-02 (4), D-02 (4) |
| BR1_TS19 | Mail/Phone Authorization Only – Manual / Keyed | CARD_NOT_PRESENT-2100 | 24 | 3 | 14 | 0 | 0 | 7 | D-01 (2), C-11 (1) |
| BR1_TS20 | Mail/Phone Authorization Only – Credential on File | CARD_NOT_PRESENT-2100 | 11 | 3 | 8 | 0 | 0 | 0 | D-01 (2), C-11 (1) |
| BR1_TS21 | Merchandise Return – EMV Contact Chip | GENERAL | 11 | 3 | 8 | 0 | 0 | 0 | D-13 (1), D-09 (1), D-01 (1) |
| BR1_TS22 | Merchandise Return – EMV Contactless Chip | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS23 | Merchandise Return – Magnetic Stripe | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS24 | Merchandise Return – Contactless Magnetic Stripe | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS25 | Merchandise Return – Manual / Keyed | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS26 | Merchandise Return – Chip Fallback | GENERAL | 10 | 1 | 9 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS27 | Merchandise Return – Credential on File | CARD_NOT_PRESENT-0100 | 10 | 1 | 9 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS28 | Merchandise Return – Contactless Mobile Commerce Device | GENERAL | 9 | 1 | 8 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS29 | Balance Inquiry – EMV Contact Chip | GENERAL | 5 | 3 | 2 | 0 | 0 | 0 | D-13 (3), D-09 (1), D-15 (1) |
| BR1_TS30 | Balance Inquiry – EMV Contactless Chip | GENERAL | 2 | 1 | 1 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS31 | Balance Inquiry – Magnetic Stripe | GENERAL | 2 | 1 | 1 | 0 | 0 | 0 | D-13 (1), D-11 (1) |
| BR1_TS32 | Balance Inquiry – Contactless Magnetic Stripe | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS33 | Balance Inquiry – Manual / Keyed | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS34 | Balance Inquiry – Chip Fallback | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS35 | Balance Inquiry – Credential on File | CARD_NOT_PRESENT-0100 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS36 | Balance Inquiry – Contactless Mobile Commerce Device | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS37 | Purchase/Capture – EMV Contact Chip | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS38 | Purchase/Capture – EMV Contactless Chip | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS39 | Purchase/Capture – Magnetic Stripe | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS40 | Purchase/Capture – Contactless Magnetic Stripe | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS41 | Purchase/Capture – Contactless Mobile Commerce Device | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS42 | Authorization Only – EMV Contact Chip | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS43 | Authorization Only – EMV Contactless Chip | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS44 | Authorization Only – Magnetic Stripe | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS45 | Authorization Only – Contactless Magnetic Stripe | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS46 | Authorization Only – Contactless Mobile Commerce Device | GENERAL_RETAIL-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS47 | Purchase/Capture – EMV Contact Chip | HOSPITALITY-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS48 | Purchase/Capture – EMV Contactless Chip | HOSPITALITY-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS49 | Purchase/Capture – Magnetic Stripe | HOSPITALITY-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS50 | Purchase/Capture – Contactless Magnetic Stripe | HOSPITALITY-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS51 | Purchase/Capture – Manual / Keyed | HOSPITALITY-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS52 | Purchase/Capture – Chip Fallback | HOSPITALITY-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS53 | Purchase/Capture – Contactless Mobile Commerce Device | HOSPITALITY-ATTENDED | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS54 | Authorization Only – EMV Contact Chip | HOSPITALITY-ATTENDED | 30 | 2 | 28 | 0 | 0 | 0 | C-10 (2) |
| BR1_TS55 | Authorization Only – EMV Contactless Chip | HOSPITALITY-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS56 | Authorization Only – Magnetic Stripe | HOSPITALITY-ATTENDED | 4 | 0 | 3 | 0 | 0 | 1 | - |
| BR1_TS57 | Authorization Only – Contactless Magnetic Stripe | HOSPITALITY-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS58 | Authorization Only – Manual / Keyed | HOSPITALITY-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS59 | Authorization Only – Chip Fallback | HOSPITALITY-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS60 | Authorization Only – Contactless Mobile Commerce Device | HOSPITALITY-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS61 | Purchase/Capture – EMV Contact Chip | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS62 | Purchase/Capture – EMV Contactless Chip | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS63 | Purchase/Capture – Magnetic Stripe | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS64 | Purchase/Capture – Contactless Magnetic Stripe | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS65 | Purchase/Capture – Contactless Mobile Commerce Device | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS66 | Authorization Only – EMV Contact Chip | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS67 | Authorization Only – EMV Contactless Chip | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS68 | Authorization Only – Magnetic Stripe | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS69 | Authorization Only – Contactless Magnetic Stripe | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS70 | Authorization Only – Contactless Mobile Commerce Device | HOSPITALITY-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS71 | Purchase/Capture – EMV Contact Chip | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS72 | Purchase/Capture – EMV Contactless Chip | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS73 | Purchase/Capture – Magnetic Stripe | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS74 | Purchase/Capture – Contactless Magnetic Stripe | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS75 | Purchase/Capture – Manual / Keyed | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS76 | Purchase/Capture – Chip Fallback | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS77 | Purchase/Capture – Contactless Mobile Commerce Device | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS78 | Authorization Only – EMV Contact Chip | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS79 | Authorization Only – EMV Contactless Chip | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS80 | Authorization Only – Magnetic Stripe | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS81 | Authorization Only – Contactless Magnetic Stripe | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS82 | Authorization Only – Manual / Keyed | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS83 | Authorization Only – Chip Fallback | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS84 | Authorization Only – Contactless Mobile Commerce Device | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS85 | Merchandise Return – EMV Contact Chip | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS86 | Merchandise Return – EMV Contactless Chip | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS87 | Merchandise Return – Magnetic Stripe | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS88 | Merchandise Return – Contactless Magnetic Stripe | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS89 | Merchandise Return – Manual / Keyed | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS90 | Merchandise Return – Chip Fallback | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS91 | Merchandise Return – Contactless Mobile Commerce Device | SUPERMARKET-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS99 | Purchase/Capture – EMV Contact Chip | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS100 | Purchase/Capture – EMV Contactless Chip | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS101 | Purchase/Capture – Magnetic Stripe | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS102 | Purchase/Capture – Contactless Magnetic Stripe | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS103 | Purchase/Capture – Contactless Mobile Commerce Device | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS104 | Authorization Only – EMV Contact Chip | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS105 | Authorization Only – EMV Contactless Chip | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS106 | Authorization Only – Magnetic Stripe | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS107 | Authorization Only – Contactless Magnetic Stripe | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS108 | Authorization Only – Contactless Mobile Commerce Device | SUPERMARKET-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS109 | Purchase/Capture – EMV Contact Chip | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS110 | Purchase/Capture – EMV Contactless Chip | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS111 | Purchase/Capture – Magnetic Stripe | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS112 | Purchase/Capture – Contactless Magnetic Stripe | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS113 | Purchase/Capture – Manual / Keyed | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS114 | Purchase/Capture – Chip Fallback | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS115 | Purchase/Capture – Contactless Mobile Commerce Device | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS116 | Authorization Only – EMV Contact Chip | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS117 | Authorization Only – EMV Contactless Chip | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS118 | Authorization Only – Magnetic Stripe | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS119 | Authorization Only – Contactless Magnetic Stripe | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS120 | Authorization Only – Manual / Keyed | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS121 | Authorization Only – Chip Fallback | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS122 | Authorization Only – Contactless Mobile Commerce Device | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS123 | Merchandise Return – EMV Contact Chip | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS124 | Merchandise Return – EMV Contactless Chip | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS125 | Merchandise Return – Magnetic Stripe | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS126 | Merchandise Return – Contactless Magnetic Stripe | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS127 | Merchandise Return – Manual / Keyed | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS128 | Merchandise Return – Chip Fallback | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS129 | Merchandise Return – Contactless Mobile Commerce Device | UTILITIES-ATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS137 | Purchase/Capture – EMV Contact Chip | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS138 | Purchase/Capture – EMV Contactless Chip | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS139 | Purchase/Capture – Magnetic Stripe | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS140 | Purchase/Capture – Contactless Magnetic Stripe | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS141 | Purchase/Capture – Contactless Mobile Commerce Device | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS142 | Authorization Only – EMV Contact Chip | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS143 | Authorization Only – EMV Contactless Chip | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS144 | Authorization Only – Magnetic Stripe | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS145 | Authorization Only – Contactless Magnetic Stripe | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS146 | Authorization Only – Contactless Mobile Commerce Device | UTILITIES-UNATTENDED | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS147 | Purchase/Capture – EMV Contact Chip | PETROLEUM-ATTENDED | 14 | 3 | 11 | 0 | 0 | 0 | D-13 (3) |
| BR1_TS148 | Purchase/Capture – EMV Contactless Chip | PETROLEUM-ATTENDED | 8 | 2 | 6 | 0 | 0 | 0 | D-13 (2) |
| BR1_TS149 | Purchase/Capture – Magnetic Stripe | PETROLEUM-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS150 | Purchase/Capture – Contactless Magnetic Stripe | PETROLEUM-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS151 | Purchase/Capture – Manual / Keyed | PETROLEUM-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS152 | Purchase/Capture – Chip Fallback | PETROLEUM-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS153 | Purchase/Capture – Contactless Mobile Commerce Device | PETROLEUM-ATTENDED | 6 | 0 | 6 | 0 | 0 | 0 | - |
| BR1_TS154 | Authorization Only – EMV Contact Chip | PETROLEUM-ATTENDED | 62 | 8 | 54 | 0 | 0 | 0 | D-13 (4), C-02 (3), C-10 (2) |
| BR1_TS155 | Authorization Only – EMV Contactless Chip | PETROLEUM-ATTENDED | 32 | 3 | 29 | 0 | 0 | 0 | D-13 (1), C-02 (1), D-02 (1) |
| BR1_TS156 | Authorization Only – Magnetic Stripe | PETROLEUM-ATTENDED | 43 | 2 | 26 | 0 | 0 | 15 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS157 | Authorization Only – Contactless Magnetic Stripe | PETROLEUM-ATTENDED | 29 | 3 | 26 | 0 | 0 | 0 | D-13 (1), C-02 (1), D-02 (1) |
| BR1_TS158 | Authorization Only – Manual / Keyed | PETROLEUM-ATTENDED | 30 | 3 | 27 | 0 | 0 | 0 | C-02 (2), D-02 (1), C-01 (1) |
| BR1_TS159 | Authorization Only – Chip Fallback | PETROLEUM-ATTENDED | 28 | 2 | 26 | 0 | 0 | 0 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS160 | Authorization Only – Contactless Mobile Commerce Device | PETROLEUM-ATTENDED | 28 | 2 | 26 | 0 | 0 | 0 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS161 | Merchandise Return – EMV Contact Chip | PETROLEUM-ATTENDED | 7 | 2 | 5 | 0 | 0 | 0 | D-13 (2) |
| BR1_TS162 | Merchandise Return – EMV Contactless Chip | PETROLEUM-ATTENDED | 5 | 2 | 3 | 0 | 0 | 0 | D-13 (2) |
| BR1_TS163 | Merchandise Return – Magnetic Stripe | PETROLEUM-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS164 | Merchandise Return – Contactless Magnetic Stripe | PETROLEUM-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS165 | Merchandise Return – Manual / Keyed | PETROLEUM-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS166 | Merchandise Return – Chip Fallback | PETROLEUM-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS167 | Merchandise Return – Contactless Mobile Commerce Device | PETROLEUM-ATTENDED | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS168 | Purchase/Capture – EMV Contact Chip | PETROLEUM-UNATTENDED | 7 | 0 | 7 | 0 | 0 | 0 | - |
| BR1_TS169 | Purchase/Capture – EMV Contactless Chip | PETROLEUM-UNATTENDED | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS170 | Purchase/Capture – Magnetic Stripe | PETROLEUM-UNATTENDED | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS171 | Purchase/Capture – Contactless Magnetic Stripe | PETROLEUM-UNATTENDED | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS172 | Purchase/Capture – Contactless Mobile Commerce Device | PETROLEUM-UNATTENDED | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS173 | Authorization Only – EMV Contact Chip | PETROLEUM-UNATTENDED | 59 | 4 | 55 | 0 | 0 | 0 | C-10 (2), C-02 (1), D-02 (1) |
| BR1_TS174 | Authorization Only – EMV Contactless Chip | PETROLEUM-UNATTENDED | 32 | 2 | 30 | 0 | 0 | 0 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS175 | Authorization Only – Magnetic Stripe | PETROLEUM-UNATTENDED | 45 | 2 | 27 | 0 | 0 | 16 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS176 | Authorization Only – Contactless Magnetic Stripe | PETROLEUM-UNATTENDED | 29 | 2 | 27 | 0 | 0 | 0 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS177 | Authorization Only – Contactless Mobile Commerce Device | PETROLEUM-UNATTENDED | 29 | 2 | 27 | 0 | 0 | 0 | C-02 (1), D-02 (1), C-01 (1) |
| BR1_TS178 | Purchase/Capture – EMV Contact Chip | MCC_5542 | 3 | 0 | 3 | 0 | 0 | 0 | - |
| BR1_TS179 | Purchase/Capture – EMV Contactless Chip | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS180 | Purchase/Capture – Magnetic Stripe | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS181 | Purchase/Capture – Contactless Magnetic Stripe | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS182 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_5542 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS183 | Purchase/Capture – EMV Contact Chip | MCC_6051 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS184 | Purchase/Capture – EMV Contactless Chip | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS185 | Purchase/Capture – Magnetic Stripe | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS186 | Purchase/Capture – Contactless Magnetic Stripe | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS187 | Purchase/Capture – Manual / Keyed | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS188 | Purchase/Capture – Chip Fallback | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS189 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_6051 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS190 | Purchase/Capture – EMV Contact Chip | MCC_6211 | 4 | 0 | 4 | 0 | 0 | 0 | - |
| BR1_TS191 | Purchase/Capture – EMV Contactless Chip | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS192 | Purchase/Capture – Magnetic Stripe | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS193 | Purchase/Capture – Contactless Magnetic Stripe | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS194 | Purchase/Capture – Manual / Keyed | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS195 | Purchase/Capture – Chip Fallback | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS196 | Purchase/Capture – Contactless Mobile Commerce Device | MCC_6211 | 1 | 0 | 1 | 0 | 0 | 0 | - |

## 11. ATL105 references used

| Reference | Section | PDF p. | Printed | Text (verified on the page) |
|---|---|---:|---|---|
| APPD-01 | Appendix D. Valid State Codes | 514 | Appendix D-1 | Alabama AL 01 |
| APPD-09 | Appendix D. Valid State Codes | 514 | Appendix D-1 | Connecticut CT 09 |
| APPD-11 | Appendix D. Valid State Codes | 514 | Appendix D-1 | District of Columbia DC 11 |
| APPD-14 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Puerto Rico PR 14 |
| APPD-15 | Appendix D. Valid State Codes | 514 | Appendix D-1 | Hawaii HI 15 |
| APPD-19 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Iowa IA 19 |
| APPD-20 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Kansas KS 20 |
| APPD-22 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Louisiana LA 22 |
| APPD-23 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Maine ME 23 |
| APPD-24 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Maryland MD 24 |
| APPD-26 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Michigan MI 26 |
| APPD-29 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Missouri MO 29 |
| APPD-30 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Montana MT 30 |
| APPD-31 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Nebraska NE 31 |
| APPD-32 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Nevada NV 32 |
| APPD-35 | Appendix D. Valid State Codes | 515 | Appendix D-2 | New Mexico NM 35 |
| APPD-36 | Appendix D. Valid State Codes | 515 | Appendix D-2 | New York NY 36 |
| APPD-37 | Appendix D. Valid State Codes | 515 | Appendix D-2 | North Carolina NC 37 |
| APPD-40 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Oklahoma OK 40 |
| APPD-43 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Guam GU 43 |
| APPD-44 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Rhode Island RI 44 |
| APPD-46 | Appendix D. Valid State Codes | 516 | Appendix D-3 | South Dakota SD 46 |
| APPD-47 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Tennessee TN 47 |
| APPD-52 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Virgin Islands VI 52 |
| APPD-53 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Washington WA 53 |
| APPD-54 | Appendix D. Valid State Codes | 516 | Appendix D-3 | West Virginia WV 54 |
| APPD-55 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Wisconsin WI 55 |
| APPD-57 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Armed Forces Americas AA 57 |
| APPD-59 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Armed Forces Pacific AP 59 |
| APPD-60 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Military State--Reserved XX 60 |
| APPD-64 | Appendix D. Valid State Codes | 517 | Appendix D-4 | New Brunswick NB 64 |
| APPD-65 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Newfoundland and Labrador NL 65 |
| APPD-66 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Nova Scotia NS 66 |
| APPD-67 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Northwest Territories NT 67 |
| APPD-68 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Ontario ON 68 |
| APPD-70 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Quebec QC 70 |
| APPD-72 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Yukon YT 72 |
| APPD-76 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Nunavut NU 76 |
| APPE-020 | Appendix E. Valid Card Type Codes | 521 | Appendix E-4 | 020 Credit Credit |
| APPG-0 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion |
| APPG-3 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 3 POS Authorization Only Point-of-sale authorization only (no draft capture) |
| APPG-6 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 6 Mail/Phone Purchase Mail/Telephone order |
| APPG-7 | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | 7 Merchandise return Merchandise return/refund |
| APPG-8 | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | 8 Purchase reversal Purchase reversal/void |
| APPG-A | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | A Account Verification Account verification |
| APPG-B | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | B Mail/Phone Authorization Only Mail/Telephone authorization only |
| APPG-C | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | C Mail/Phone Reversal Mail/Telephone reversal/void |
| APPG-E | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | E Balance Inquiry Balance Inquiry—Credit, EBT cash benefits and stored value card |
| APPG-S | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | S Cancellation Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction. |
| APPG-U | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | U Void of a Merchandise Return Void of a merchandise return |
| APPG-Z | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | Z Time-out Reversal Time-out reversal |
| APPI-003-ADDR | Appendix I. Variable Information Data Layouts - AVS Information (Table 003) | 554 | Appendix I-5 | The remaining 00–20 characters contain street address information. |
| APPI-003-ZIP | Appendix I. Variable Information Data Layouts - AVS Information (Table 003) | 554 | Appendix I-5 | ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or |
| APPI-004 | Appendix I. Variable Information Data Layouts - Card Verification Information (Table 004) | 554 | Appendix I-5 | MasterCard:  3 bytes |
| APPI-005 | Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005) | 555 | Appendix I-6 | Point-of-Service Entry Mode Information |
| APPI-006 | Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006) | 555 | Appendix I-6 | Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)] |
| APPI-030 | Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030) | 568 | Appendix I-19 | Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions. |
| APPI-031 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | Terminal Capability values must be populated for all transactions |
| APPI-031-12 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | 12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities |
| APPI-048 | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 586 | Appendix I-37 | This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication. |
| APPI-048-LEN | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 586 | Appendix I-37 | Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID. |
| APPI-048-PP | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 587 | Appendix I-38 | 1 = EMV 3-D Secure Version 2.1 |
| APPJ-01 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 01 Manual/keyed |
| APPJ-02 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 02 Magnetic stripe read with partial track data |
| APPJ-05 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 05 Integrated circuit card (ICC) read – CVV data reliable (used for EMV chip read) |
| APPJ-07 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 07 Contactless chip read (used for contactless chip) |
| APPJ-10 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 10 Credential on file |
| APPJ-79 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 79 Contact and Contactless chip fallback to manual |
| APPJ-82 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 82 Contactless mobile commerce device |
| APPJ-90 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 90 Magnetic stripe read with full track data |
| APPJ-91 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 91 Full contactless magnetic stripe read with track data |
| APPJ-PERSIST | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction |
| APPJ-PIN1 | Appendix J. Valid Point-of-Service Entry Mode Codes | 639 | Appendix J-2 | 1 PIN entry capability (including both EMV Contact and Contactless) |
| APPK-003 | Appendix K. Additional Information Data Layouts - AVS or NVS or Both Information (Table 003) | 643 | Appendix K-4 | Identifies the AVS or NVS or both response from the authorizer. |
| APPK-004 | Appendix K. Additional Information Data Layouts - CVV Information (Table 004) | 648 | Appendix K-9 | Identifies the CVV response from the authorizer. |
| APPQ | Appendix Q. Valid National Point-of-Service Condition Codes | 690 | Appendix Q-1 | This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030 The National Point-of-Service Condition Codes data element contains the following elements: • Terminal Class (Position Nos. 1–3) • Presentation Type (Position Nos. 4–7) • Security Condition (Position No. 8) • Terminal Type (Position Nos. 9–10) |
| APPQ-010-04 | Appendix Q. Valid National Point-of-Service Condition Codes | 693 | Appendix Q-4 | 010 04 Attended on premise Card acceptor-operated ECR |
| APPQ-100-01 | Appendix Q. Valid National Point-of-Service Condition Codes | 693 | Appendix Q-4 | 100 01 Unattended on premise customer-operated point-of-service terminal |
| APPQ-100-07 | Appendix Q. Valid National Point-of-Service Condition Codes | 693 | Appendix Q-4 | 100 07 Unattended on premise customer-operated fuel machine |
| APPQ-CLASS | Appendix Q. Valid National Point-of-Service Condition Codes | 691 | Appendix Q-2 | Position No. 1 Value Description 0 Attended 1 Unattended 2–7 Reserved for national use 8–9 Reserved for private use Position No. 2 Value Description 0 Customer-operated 1 Card acceptor-operated 2 Administrative 3–7 Reserved for national use 8–9 Reserved for private use Position No. 3 Value Description 0 On premise 1 Off premise |
| APPQ-PRES | Appendix Q. Valid National Point-of-Service Condition Codes | 696 | Appendix Q-7 | Position Nos. 4–7 Presentation Type Description 0000 Customer and card present 0100 Customer present; card not present 1000 Customer not present; card present 1100 Customer not present; card not present 2100 Mail and/or telephone order 8800 Preauthorized purchase |
| APPQ-SEC | Appendix Q. Valid National Point-of-Service Condition Codes | 696 | Appendix Q-7 | Security Condition Value Description 0 No security concern 1 Suspected fraud 2 Identification verified 3 Electronic commerce transaction with digital signature |
| APPQ-TYPE | Appendix Q. Valid National Point-of-Service Condition Codes | 692 | Appendix Q-3 | Value Description 00 Administrative terminal 01 Point-of-service terminal 02 ATM 03 Home terminal 04 ECR 05 Dial terminal 06 Traveler's check machine 07 Fuel machine |
| APPR-9C | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | 9C\01\00\ Transaction type |
| APPR-9F02 | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | 9F\02\06\00\00\00\00\09\00\ Amount, authorized (numeric) |
| APPR-NOTE | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | The below table does not contain complete list of tags. |
| APPR-PKT | Appendix R. EMV Chip Data Example | 697 | Appendix R-1 | 01\06 EMV/Chip Data length (BCD packed) |
| APPT-1 | Appendix T. EMV Additional Information Data Layouts - EMV Table Data | 701 | Appendix T-1 | EMVYES This code indicates that the device is able to receive EMV Table Data. |
| APPT-1-LEN | Appendix T. EMV Additional Information Data Layouts - EMV Table Data | 701 | Appendix T-1 | Maximum Length n6 Len.: 6 |
| APPT-1-NOT | Appendix T. EMV Additional Information Data Layouts - EMV Table Data | 701 | Appendix T-1 | EMVNOT This code indicates that the device is unable to receive EMV Table Data. |
| EL102 | Element 102 Terminal Identifier (Chapter 13) | 417 | 13-77 | The Terminal Identifier must be assigned by and set up at BUYPASS. |
| EL102-STATE | Element 102 Terminal Identifier (Chapter 13) | 417 | 13-77 | State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). |
| EL105 | Element 105 Totals Date (Chapter 13) | 419 | 13-79 | Fixed length of six digits |
| EL106 | Element 106 Unit of Measure (Chapter 13) | 420 | 13-80 | Valid Codes/Values: Value Description C Case/Carton G Gallon H Hours I Imperial Gallons K Kilogram L Liter |
| EL107 | Element 107 Unit Price (Chapter 13) | 421 | 13-81 | Processing Rules: The leading number indicates the number of assumed decimal places. There is a maximum number of three assumed decimal places. |
| EL111 | Element 111 Variable Information Indicator (Chapter 13) | 422 | 13-82 | Fixed length of 3 alphanumeric characters |
| EL111-001 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 001 Universal Product Code (UPC) indicator |
| EL111-002 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 002 Program Identifier indicator |
| EL111-003 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 003 Address verification service (AVS) indicator |
| EL111-004 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 004 Card verification (CVV, CVV2, CVC2, CID, DTVV) |
| EL111-006 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 006 Currency code indicator |
| EL111-008 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 008 Balance transfer—stored value gift card account |
| EL111-009 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 009 Partial Approval Indicator |
| EL111-012 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 012 Market-Specific Data indicator |
| EL111-014 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 014 User Data Information |
| EL111-017 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 017 Suspected Fraud Reversal indicator |
| EL111-018 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 018 Card Acceptor Terminal Identifier Information |
| EL111-019 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 019 Recharge Type Information |
| EL111-021 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 021 Settlement Data Acceptance Flag |
| EL111-022 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 022 Originating Device Type Indicator |
| EL111-027 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 027 Customer Name |
| EL111-034 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 034 Name Verification Service (NVS) Indicator |
| EL111-036 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 036 MasterCard UCAF Data |
| EL111-037 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 037 MCX Checkout Token |
| EL111-038 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 038 Paydiant Tender ID |
| EL111-039 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 039 Fraud Enhanced Data |
| EL111-040 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 040 Final Amount Indicator |
| EL111-041 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 041 Country Subdivision |
| EL111-042 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 042 Cardholder Language |
| EL111-046 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 046 Previous Transaction ID |
| EL111-047 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 047 Soft Descriptor – Merchant Name |
| EL111-048 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 048 MasterCard Authentication Data |
| EL111-050 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 050 Additional Security Code |
| EL111-051 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 051 Additional Card ID |
| EL111-052 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 052 Additional TransArmor Data |
| EL111-053 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 053 Remote Commerce Acceptor Identifier |
| EL111-054 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 054 Merchant Tran ID |
| EL111-060 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 060 Real Time Account Updater Request Data |
| EL111-062 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 062 Customer Additional Information |
| EL111-063 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 063 Visa® Incremental Auth Lodging Data |
| EL111-064 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 064 Service Location Information |
| EL111-065 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 065 Merchant Payment Gateway ID Data |
| EL111-066 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 066 Digital Commerce Data |
| EL111-067 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 067 Anticipated Amount |
| EL111-073 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 073 Future Transaction Amount |
| EL111-075 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 075 Discount Program Data |
| EL111-076 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 076 Discount Line-Item Data |
| EL111-080 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 080 Staged Wallet ID |
| EL113-003 | Element 113 Variable Information (Chapter 13) | 426 | 13-86 | When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here. |
| EL116-INIT | Element 116 Additional Information Indicator (Chapter 13) | 433 | 13-93 | A Financial Transaction Request initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction Response. |
| EL118-EMV | Element 118 EMV Additional Information (Chapter 13) | 439 | 13-99 | When EMV Additional Information Indicator has a value of 001, EMV table data is included here. |
| EL12 | Element 12 Card Discretionary Block Data (Chapter 13) | 372 | 13-32 | For a card whose number has been manually entered, the expiration date is in MMYY format |
| EL12-MAX | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2. |
| EL12-TRACK1 | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number is considered the data before the first ^ character, and Card Discretionary Block Data is considered the data after the first ^ character. |
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
| EL5-FMT | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Fixed length of six alphanumeric characters; space-filled |
| EL5-PRE | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization. |
| EL5-REV | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Required on reversals for matching information to the original purchase. |
| EL55 | Element 55 Message Format Version Identifier (Chapter 13) | 391 | 13-51 | Fixed length of six alphanumeric characters |
| EL58 | Element 58 Nonfuel Amount (Chapter 13) | 393 | 13-53 | A maximum length of 7 bytes is allowed for all other card types. |
| EL58-PURPOSE | Element 58 Nonfuel Amount (Chapter 13) | 393 | 13-53 | Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount. |
| EL6 | Element 6 Approved Amount (Chapter 13) | 368 | 13-28 | Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned |
| EL62 | Element 62 Number of Products (Chapter 13) | 394 | 13-54 | Always precede single digits (1–9) with a zero (01–09). |
| EL63 | Element 63 Number of Segments (Chapter 13) | 395 | 13-55 | Variable length of up to two digits |
| EL76 | Element 76 Product Amount (Chapter 13) | 399 | 13-59 | Representation: Variable length of up to 12 digits with two assumed decimal places |
| EL77 | Element 77 Product Code (Chapter 13) | 400 | 13-60 | Representation: Fixed length of three digits |
| EL78 | Element 78 Prompt Code (Chapter 13) | 400 | 13-60 | this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters). |
| EL79 | Element 79 Pump/Lane Number (Chapter 13) | 401 | 13-61 | Representation: Variable length of up to two digits Purpose: Identifies the pump or lane number at the point-of-sale location. |
| EL79-USE | Element 79 Pump/Lane Number (Chapter 13) | 402 | 13-62 | Required on all fuel transactions. |
| EL81 | Element 81 Quantity (Chapter 13) | 402 | 13-62 | Processing Rules: The leading number indicates the number of assumed decimal places. There is a maximum number of three assumed decimal places. |
| EL81-EX | Element 81 Quantity (Chapter 13) | 402 | 13-62 | Examples: 010 = 10, where 0 is the number of assumed decimal places and 10 represents the quantity of 10. 34170 = 4.170, where 3 is the number of assumed decimal places and 4170 represents the quantity of 4.170. |
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
| EL86-PREAUTH | Element 86 Sequence Number (Chapter 13) | 409 | 13-69 | In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request. |
| EL86-REV | Element 86 Sequence Number (Chapter 13) | 409 | 13-69 | In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request. |
| EL87-VAL | Element 87 Service Level (Chapter 13) | 410 | 13-70 | F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present. |
| EL96 | Element 96 Software Version (Chapter 13) | 414 | 13-74 | Fixed length of eight alphanumeric characters |
| EL99 | Element 99 Tax Amount (Chapter 13) | 415 | 13-75 | Identifies the transaction tax amount in Data Segment No. 100, Standard Message Data Segment. |
| S10.1.1.2-FOLLOW | Section 10.1.1.2 Track Support | 81 | 10-2 | All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile. |
| S10.1.1.3 | Section 10.1.1.3 Manual Entry | 81 | 10-2 | If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY). |
| S10.1.2-LIST | Section 10.1.2 Supported Transaction Types | 83 | 10-4 | The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal |
| S10.1.2.1-REMAIN | Section 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale. |
| S10.1.2.1-SEQ | Section 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | The Sequence Number must be the Sequence Number contained in the original transaction request. |
| S10.1.2.2 | Section 10.1.2.2 Authorization Only Reversal Transaction | 85 | 10-6 | Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. |
| S10.1.2.2-SEQ | Section 10.1.2.2 Authorization Only Reversal Transaction | 85 | 10-6 | Element No.86 (Sequence Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. |
| S10.1.3-REQ | Section 10.1.3 Address Verification Service | 86 | 10-7 | The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment |
| S10.1.3-RESP | Section 10.1.3 Address Verification Service | 86 | 10-7 | The authorizer returns a response containing both the transaction authorization Response Code and the AVS information in the Additional Information Data Segment |
| S10.1.3-USE | Section 10.1.3 Address Verification Service | 86 | 10-7 | Address Verification Service (AVS) is a credit card authorization tool used for transactions performed primarily in a non-face-to-face environment, such as mail order, phone order, or customer-activated terminal (CAT). |
| S10.1.6.1.3 | Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service) | 87 | 10-8 | A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response. |
| S10.1.8.2 | Section 10.1.8.2 Track Support Requirements | 94 | 10-15 | The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). |
| S10.14.2.4-REV | Section 10.14.2.4 Supported Transaction Types (EMV) | 161 | 10-82 | EMV data is not required on Reversal transactions. |
| S10.14.3-ONLINE | Section 10.14.3 EMV PIN (Personal Identification Number) Processing | 162 | 10-83 | Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer. |
| S10.14.3-OPT | Section 10.14.3 EMV PIN (Personal Identification Number) Processing | 162 | 10-83 | In a credit/signature debit transaction, a PIN of all F’s is NOT required since PINs are an optional field for a credit/signature debit transaction. |
| S11.1-101 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data. |
| S11.1-104 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data. |
| S11.1-123 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions. |
| S11.1-143 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager. |
| S11.1-145 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings. |
| S11.1-153 | Section 11.1 Financial Transactions (Data Section No. 3) | 169 | 11-6 | 153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction. |
| S11.1-DS1 | Section 11.1 Financial Transactions (Data Section No. 1) | 167 | 11-4 | All Financial Transaction Requests contain Element Nos. 55 and 63. |
| S11.1-DS3 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9. |
| S11.2.1-108 | Section 11.2.1 Loyalty Card Transactions - Request | 175 | 11-12 | The Loyalty Card Transaction Request contains Segment Nos. 108 and 114. |
| S11.2.1-108R | Section 11.2.1 Loyalty Card Transactions - Request | 175 | 11-12 | Contains the loyalty card data required for all loyalty card transactions. |
| S11.4-TOTALS | Section 11.4.1.1 Totals Request | 178 | 11-15 | Note: Totals Request messages do not contain Data Section No. 2 [Standard Message Data Segment (Data Segment No. 100)]. |
| S11.8-130 | Section 11.8.1 EMV Financial Transaction Request | 206 | 11-43 | This segment is the only segment required for all EMV financial transactions. |
| S11.8-EMV | Section 11.8.1 EMV Financial Transaction Request | 205 | 11-42 | The following table illustrates the layout of an EMV Financial Transaction Request: |
| S12.1-FS | Section 12.1 Standard Message Data Segment | 223 | 12-6 | When a field is not populated, still send the Field Separator. |
| S12.1-LEN | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.1-ROW16 | Section 12.1 Standard Message Data Segment | 225 | 12-8 | Identifies the local date and local time of the preauthorized transaction. |
| S12.1-ROW3 | Section 12.1 Standard Message Data Segment | 223 | 12-6 | 3 44 Information Byte 1 R |
| S12.1-TYPE | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Fixed value: 100 |
| S12.10-FS | Section 12.10 Variable Information Data Segment | 246 | 12-29 | A Field Separator follows the final Variable Information Section. |
| S12.10-LEN | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Identifies the length of Variable Information (Element No. 113). |
| S12.10-TYPE | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Fixed value: 111 |
| S12.19-FS | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | All fields are separated by Field Separator. |
| S12.19-MAX | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | It has a maximum length of 186 alphanumeric characters |
| S12.19-REQ | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data. |
| S12.2-FS | Section 12.2 Fleet Data Segment | 226 | 12-9 | All fields are separated by Field Separators. |
| S12.20-ALWAYS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | It always appears in Data Section No. 3 of an EMV Financial Transaction Request. |
| S12.20-FS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | A Field Separator follows the final EMV Additional Information Section. |
| S12.20-LEN | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.20-TYPE | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Fixed value: 130 |
| S12.3-DELIM | Section 12.3 Product Code Data Segment | 229 | 12-12 | A Product Data Field Delimiter always follows Quantity and Unit Price. |
| S12.3-FS | Section 12.3 Product Code Data Segment | 229 | 12-12 | A Field Separator always follows Segment Type and Segment Length. |
| S12.3-LAST | Section 12.3 Product Code Data Segment | 229 | 12-12 | A Field Separator follows Product Amount when Product Amount is the very last element in the segment. |
| S12.3-MAX | Section 12.3 Product Code Data Segment | 229 | 12-12 | It has a maximum length of 381 alphanumeric characters |
| S12.3-ROW3 | Section 12.3 Product Code Data Segment | 230 | 12-13 | 3 87 Service Level 1 R Identifies the type of sale. |
| S12.3-TOTAL | Section 12.3 Product Code Data Segment | 229 | 12-12 | The total of product amounts in the segment must equal the total of the amounts |
| S12.30-102 | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | A Product Code Data Segment (Data Segment No. 102) must be present in the transaction. |
| S12.30-CAN | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax). |
| S12.30-ORDER | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments. |
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
| S12.6-FS | Section 12.6 Totals Data Segment | 235 | 12-18 | Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14. |
| S12.6-NOFS | Section 12.6 Totals Data Segment | 235 | 12-18 | Field Nos. 15–17 are not separated by Field Separators. |
| S12.6-PROMPT | Section 12.6 Totals Data Segment | 235 | 12-18 | Identifies the format type for the requested totals. Fixed value: 990 |
| S12.6-TYPE | Section 12.6 Totals Data Segment | 235 | 12-18 | Identifies the type of data segment being formatted. Fixed value: 105 |
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
| S2.3.1-SEQ | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number |
| S2.3.1-TIMEOUT | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The customer’s 30-second response interval expires without a response from BUYPASS. |
| S2.3.1-TOR-SEQ | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. |
| S2.3.1-UNMATCHED | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | BUYPASS declines a TOR request as an unmatched reversal when BUYPASS has not received the original financial transaction request related to the TOR; or when the original financial transaction has already been reversed. |
| S9.2-GT | Section 9.2 Local Totals | 72 | 9-8 | The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only |
| S9.2-PUR | Section 9.2 Local Totals | 72 | 9-8 | A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total. |
| S9.2-RET | Section 9.2 Local Totals | 72 | 9-8 | A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total. |
| S9.2-REV | Section 9.2 Local Totals | 72 | 9-8 | A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total. |

Regenerate: `python scripts/validate-run-and-build-manual-validation.py`.

