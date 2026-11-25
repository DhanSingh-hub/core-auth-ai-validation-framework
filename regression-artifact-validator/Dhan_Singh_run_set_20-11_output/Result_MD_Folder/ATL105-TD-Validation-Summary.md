# ATL105 validation of 37 test cases of the run and their 49 test data files

**Run:** [Dhan_Singh_run_set_20-11](../../Dhan_Singh_run_set_20-11/) (MasterCard Credit run of 19 Nov 2026), build `buypass-atl105-mastercard-credit-generated-42c2d5f6ba`  
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). The attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the 2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  
**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.

ATL105 result of each test case, every segment and field: [TC-076120](TC-076120.md), [TC-076166](TC-076166.md), [TC-076167](TC-076167.md), [TC-076168](TC-076168.md), [TC-076658](TC-076658.md), [TC-075618](TC-075618.md), [TC-075654](TC-075654.md), [TC-075699](TC-075699.md), [TC-075657](TC-075657.md), [TC-076586](TC-076586.md), [TC-075650](TC-075650.md), [TC-075755](TC-075755.md), [TC-075656](TC-075656.md), [TC-075757](TC-075757.md), [TC-075685](TC-075685.md), [TC-075670](TC-075670.md), [TC-077181](TC-077181.md), [TC-077186](TC-077186.md), [TC-077193](TC-077193.md), [TC-077219](TC-077219.md), [TC-076596](TC-076596.md), [TC-076613](TC-076613.md), [TC-075751](TC-075751.md), [TC-075667](TC-075667.md), [TC-076423](TC-076423.md), [TC-077185](TC-077185.md), [TC-077206](TC-077206.md), [TC-077218](TC-077218.md), [TC-076725](TC-076725.md), [TC-076723](TC-076723.md), [TC-075601](TC-075601.md), [TC-075810](TC-075810.md), [TC-075855](TC-075855.md), [TC-076597](TC-076597.md), [TC-076780](TC-076780.md), [TC-077179](TC-077179.md), [TC-077203](TC-077203.md). Detailed step-by-step guides for 5 test cases: [TC-076120](../Manual%20Validation/TC-076120-Manual-Validation.md), [TC-076166](../Manual%20Validation/TC-076166-Manual-Validation.md), [TC-076167](../Manual%20Validation/TC-076167-Manual-Validation.md), [TC-076168](../Manual%20Validation/TC-076168-Manual-Validation.md), [TC-076658](../Manual%20Validation/TC-076658-Manual-Validation.md).

## 1. Result at a glance

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 23 | At least one test data or test case defect that must be fixed |
| **BLOCKED** | 14 | No defect, but a client value or a run-time value is still missing |
| **REVIEW** | 0 | No defect; a citation must be corrected or a point confirmed with BUYPASS |
| **PASS** | 0 | Nothing found |
| **DEVICE** | 0 | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |
| Total | 37 | 36 send a message to BUYPASS (49 test data files); 1 are device-only or have no test data |

Findings by kind (a test case can have several):

| Kind | Findings | Test cases | Effect on the result |
|---|---:|---:|---|
| Test data defect | 24 | 17 | FAIL |
| Test case defect | 6 | 6 | FAIL |
| Test case issue to correct | 23 | 21 | REVIEW |
| Citation issue | 47 | 33 | REVIEW |
| Awaiting client input / run-time value | 110 | 37 | BLOCKED |
| To confirm with BUYPASS / SME | 22 | 17 | REVIEW |
| Intended violation of a negative test (correct) | 8 | 7 | none |
| AI validator rule not applicable - value checked and correct | 1 | 1 | none |
| Information | 9 | 9 | none |

Data segments in the test data: 100 (45 files), 101 (1 files), 102 (1 files), 105 (4 files), 108 (3 files), 111 (45 files), 113 (1 files), 130 (22 files), 153 (2 files).

## 2. How every test case and test data file was checked

1. **Trained AI validator** (`scripts/validate-td-against-atl105-spec.py`, the rules built in the earlier MasterCard, Interlink and Maestro reviews): Data Section 1, all 17 fields of Segment 100, Segment 111 with Tables 005/031/030 and the Credential on File tables, Segment 130 (EMV) with its TLV chip data, Segment 105 (Totals), links between steps (Sequence Number, card, terminal, entry mode), expected Response Codes, and every ATL105 quote in the test case (`spec_basis`) against the page it cites.
2. **Extended checks for this run** (`scripts/validate-run-and-build-manual-validation.py`): the other data segments (101, 102, 104, 108, 123, 145, 153 - Segment Type, Segment Length with each segment's Field Separator rule, Chapter 13 format of every field, TLV structure), every Variable Information table ID, values awaited from the client, each test case's own `rule_checks`, test data vs test case value fidelity for all segments, identical requests across all test cases, Sequence Number uniqueness per terminal, Time-out Reversal flows, negative-test premises and conditional fields.
3. **Classification**: every finding was sorted into the kinds in section 1. Findings of negative tests that are the rule the test deliberately breaks are listed as *intended* (correct). Where the trained rule did not fit a new flow of this run, the value was checked against ATL105 and the finding listed as *AI rule not applicable* (section 9) with the reason.
4. Every ATL105 text quoted in this report (listed in section 11) was found word for word on the stated page when this report was generated.

## 3. Defects in the test data (must be fixed)

### 3.1 D-01 - Totals Request: card bucket count/amount does not match the transactions of the flow

**3 test cases, 3 findings, 3 test data files** - kind: Test data defect

Rules: `TOTALS_FLOW_MISMATCH` (3).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total."
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total."
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."
- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Fixed length of five digits"
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places"
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."

What is wrong (one example per kind of problem):

- `TOTALS_FLOW_MISMATCH` (TC-076596) - Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 cancellation $15.00: cancellation - subtracts from the AO bucket. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- `TOTALS_FLOW_MISMATCH` (TC-076597) - Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 completion $15.00: count +1, amount +$15.00. Card Type Total Amount should be 00001500 ($15.00), the file has 00000000; Grand Total should be 00001500, the file has 00000000.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076596](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) (BR1_TS11_TC15) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-076596.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |
| [TC-076597](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) (BR1_TS11_TC16) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-076597.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Count 00001, amount 00001500, grand total 00001500 |
| [TC-076613](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) (BR1_TS12_TC13) | 3 | `TOTALS_FLOW_MISMATCH` | [TD-TC-076613.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Leave out the CC bucket and send Grand Total 00000000 |

Scenarios: BR1_TS11 (2), BR1_TS12 (1)

**How to check by hand:** Open the Totals Request file, read CardLabel / CardTypeTotalCount / CardTypeTotalAmount / GrandTotal and recompute them from the earlier steps of the same test case (Section 9.2).

**Fix:** Compute the totals from the approved steps of the flow; an Authorization Only goes to the AO bucket and is left out of the grand total; a bucket with net count 0 is not sent; count and amount must agree.

### 3.2 D-09 - A negative test breaks more than the one rule it is meant to break

**6 test cases, 7 findings, 6 test data files** - kind: Test data defect

Rules: `LINK_ACCOUNTNUMBER` (1), `FIELD_102` (1), `TABLE_030_MISSING` (1), `COF_INDICATORS_MISSING` (1), `SEG130_LENGTH` (1), `TABLE_005` (1), `VARINFO_LENGTH` (1).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions."
- Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type."
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file"
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "A Field Separator follows the final EMV Additional Information Section."

What is wrong (one example per kind of problem):

- `LINK_ACCOUNTNUMBER` (TC-075685) - Step 2 follows up step 1 but waits for another card ('<AWAITING:AccountNumber>'; awaiting_inputs: "step 2: issuer outcome profile - a test card / simulator rule for which the issuer returns Response Code * (issuer decline) (config/test_data/issuer_outcome_pro"), while step 1 uses card '5197986646906760'. A follow-on is matched to its original by Sequence Number, Account Number and amount, so with another card it no longer matches the original.
- `FIELD_102` (TC-075670) - 'XE6LR2ZPLXTZ9BOKDYPUAB' has State Code '6L' (positions 3-4), which is not in Appendix D.
- `TABLE_030_MISSING` (TC-077181) - This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- `COF_INDICATORS_MISSING` (TC-077181) - Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).
- `SEG130_LENGTH` (TC-077186) - '0054' is declared, but the segment adds up to 0201.
- `TABLE_005` (TC-077193) - '021' has entry mode '02', which does not fit the MAGSTRIPE scenario (expected 90).
- `VARINFO_LENGTH` (TC-077219) - '' is declared, but the table data 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' is 983 characters long.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075685](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) (BR1_TS1_TC95) | 2 | `LINK_ACCOUNTNUMBER` | [TD-TC-075685.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step2.reversal.json#L14) | `<AWAITING:AccountNumber>` | 5197986646906760 |
| [TC-075670](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L71) (BR1_TS1_TC80) | 1 | `FIELD_102` | [TD-TC-075670.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075670.json#L9) | `XE6LR2ZPLXTZ9BOKDYPUAB` | A value that meets: Device Type 'XE' + State Code '6L' (not in Appendix D) + Merchant Number and Device Number 'R2ZPLXTZ9BOKDYPUAB'. |
| [TC-077181](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) (BR1_TS7_TC1) | 1 | `TABLE_030_MISSING` | [TD-TC-077181.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json) | `tables sent: 005, 031` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| [TC-077181](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) (BR1_TS7_TC1) | 1 | `COF_INDICATORS_MISSING` | [TD-TC-077181.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json) | `tables sent: 005, 031` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| [TC-077186](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) (BR1_TS11_TC2) | 1 | `SEG130_LENGTH` | [TD-TC-077186.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L7) | `0054` | 0201 |
| [TC-077193](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) (BR1_TS175_TC1) | 1 | `TABLE_005` | TD-TC-077193.json -> Variable Information Data Segment -> VariableInformationSection[0] | `021` | 021 |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | 1 | `VARINFO_LENGTH` | TD-TC-077219.json -> Variable Information Data Segment -> VariableInformationSection[0] | (empty) | 983 |

Scenarios: BR1_TS1 (3), BR1_TS7 (2), BR1_TS11 (1), BR1_TS175 (1)

**How to check by hand:** Compare the failing field with the field the test case deliberately changes (flags DELIBERATE_CONSTRAINT_VIOLATION / method deliberate_violation_*).

**Fix:** Keep every other field valid so the decline can only come from the rule under test.

### 3.3 D-11 - Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator)

**2 test cases, 2 findings, 2 test data files** - kind: Test data defect

Rules: `COF_INDICATORS_MISSING` (1), `TABLE_005` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions."
- Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type."
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file"
- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "02 Magnetic stripe read with partial track data"
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.639 (printed Appendix J-2): "1 PIN entry capability (including both EMV Contact and Contactless)"

What is wrong (one example per kind of problem):

- `COF_INDICATORS_MISSING` (TC-076423) - Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').
- `TABLE_005` (TC-077185) - '021' has entry mode '02', which does not fit the MAGSTRIPE scenario (expected 90).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076423](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824) (BR1_TS7_TC103) | 1 | `COF_INDICATORS_MISSING` | [TD-TC-076423.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json) | `tables sent: 005, 031, 030, 032, 049, 056` | Add Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| [TC-077185](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) (BR1_TS31_TC1) | 1 | `TABLE_005` | TD-TC-077185.json -> Variable Information Data Segment -> VariableInformationSection[0] | `021` | 021 |

Scenarios: BR1_TS7 (1), BR1_TS31 (1)

**How to check by hand:** Open the field named in the finding and apply the ATL105 rule quoted with it.

**Fix:** Correct the value as stated in the finding.

### 3.4 D-13 - Card read from the chip / stripe, but Card Discretionary Block Data holds only an MMYY expiry instead of the Track 2 data after the '='

**7 test cases, 7 findings, 7 test data files** - kind: Test data defect

Rules: `FIELD_12` (7).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."

What is wrong (one example per kind of problem):

- `FIELD_12` (TC-077186) - '0427' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077186](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) (BR1_TS11_TC2) | 1 | `FIELD_12` | [TD-TC-077186.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L15) | `0427` | A value that meets: AN, variable length 51. |
| [TC-077193](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) (BR1_TS175_TC1) | 1 | `FIELD_12` | [TD-TC-077193.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L12) | `1030` | A value that meets: AN, variable length 51. |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | 1 | `FIELD_12` | [TD-TC-077219.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L15) | `0530` | A value that meets: AN, variable length 51. |
| [TC-077185](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) (BR1_TS31_TC1) | 1 | `FIELD_12` | [TD-TC-077185.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json#L15) | `0232` | A value that meets: AN, variable length 51. |
| [TC-077206](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) (BR1_TS1_TC1) | 1 | `FIELD_12` | [TD-TC-077206.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json#L15) | `0927` | A value that meets: AN, variable length 51. |
| [TC-077218](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) (BR1_TS29_TC1) | 1 | `FIELD_12` | [TD-TC-077218.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L15) | `0228` | A value that meets: AN, variable length 51. |
| [TC-077203](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) (BR1_TS147_TC3) | 1 | `FIELD_12` | [TD-TC-077203.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L15) | `0828` | A value that meets: AN, variable length 51. |

Scenarios: BR1_TS1 (2), BR1_TS11 (1), BR1_TS175 (1), BR1_TS31 (1), BR1_TS29 (1), BR1_TS147 (1)

**How to check by hand:** Open the test data file: Table 005 starts with 05 / 07 / 90 / 91 (card read), but CardDiscretionaryBlockData is 4 digits (MMYY). For a read card it must be the Track 2 data after the '=': expiry YYMM, 3-digit service code, discretionary data.

**Fix:** Send the test card's Track 2 (equivalent) data after the '=' (YYMM + service code 2xx for a chip card + discretionary data), or use a keyed entry mode if only the expiry is known.

### 3.5 D-16 - Follow-on step sends the keyed card's MMYY expiry instead of YYMM

**3 test cases, 3 findings, 3 test data files** - kind: Test data defect

Rules: `FIELD_12` (3).

ATL105:

- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- Section 10.1.1.2 Track Support, PDF p.81 (printed 10-2): "All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile."

What is wrong (one example per kind of problem):

- `FIELD_12` (TC-076166) - Step 2 is a reversal of step 1, a keyed card whose expiry is MMYY '0928' (September 2028). A follow-on transaction sends the stored card data as Account Number = YYMM, i.e. '2809'; the file repeats '0928', which read as YYMM is month '28' - not a valid month.
- `FIELD_12` (TC-076167) - Step 2 is a reversal of step 1, a keyed card whose expiry is MMYY '0430' (April 2030). A follow-on transaction sends the stored card data as Account Number = YYMM, i.e. '3004'; the file repeats '0430', which read as YYMM is month '30' - not a valid month.
- `FIELD_12` (TC-076168) - Step 2 is a timeout reversal of step 1, a keyed card whose expiry is MMYY '0929' (September 2029). A follow-on transaction sends the stored card data as Account Number = YYMM, i.e. '2909'; the file repeats '0929', which read as YYMM is month '29' - not a valid month.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076166](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) (BR1_TS5_TC47) | 2 | `FIELD_12` | [TD-TC-076166.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L15) | `0928` | A value that meets: Follow-on transaction: expiry as YYMM only (original MMYY 0928 -> 2809). |
| [TC-076167](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) (BR1_TS5_TC48) | 2 | `FIELD_12` | [TD-TC-076167.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L15) | `0430` | A value that meets: Follow-on transaction: expiry as YYMM only (original MMYY 0430 -> 3004). |
| [TC-076168](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) (BR1_TS5_TC49) | 2 | `FIELD_12` | [TD-TC-076168.step2.timeout-reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L15) | `0929` | A value that meets: Follow-on transaction: expiry as YYMM only (original MMYY 0929 -> 2909). |

Scenarios: BR1_TS5 (3)

**How to check by hand:** Open the follow-on step file (reversal, time-out reversal, cancellation, void, completion) and read CardDiscretionaryBlockData: it repeats the original's MMYY expiry (e.g. 0131 = January 2031). A follow-on transaction is formatted from stored card data as Account Number = YYMM (3101).

**Fix:** Send the expiry as YYMM in every follow-on step: swap month and year of the original's MMYY.

### 3.6 D-15 - Positive boundary test: the value is not at the documented limit or is not a valid value

**2 test cases, 2 findings, 2 test data files** - kind: Test data defect

Rules: `X_BOUNDARY_NOT_AT_LIMIT` (1), `FIELD_44` (1).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
- Element 87 Service Level (Chapter 13), PDF p.410 (printed 13-70): "F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present."
- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission"

What is wrong (one example per kind of problem):

- `X_BOUNDARY_NOT_AT_LIMIT` (TC-077206) - The test is meant to send Encrypted PIN Block Data at exactly 36 characters, but '<AWAITING:EncryptedPINBlockData>' has 32, so the boundary is not tested.
- `FIELD_44` (TC-077218) - 'A' is not a valid Information Byte (?, 0 or 1).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077206](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) (BR1_TS1_TC1) | 1 | `X_BOUNDARY_NOT_AT_LIMIT` | [TD-TC-077206.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json#L16) | `<AWAITING:EncryptedPINBlockData>` | A valid Encrypted PIN Block Data of exactly 36 characters, or drop the test if no such value can be approved. |
| [TC-077218](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) (BR1_TS29_TC1) | 1 | `FIELD_44` | [TD-TC-077218.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L8) | `A` | A value that meets: 1 character: 'A' = not a valid code. |

Scenarios: BR1_TS1 (1), BR1_TS29 (1)

**How to check by hand:** Read the objective (field and limit), then the value in the test data: it must have exactly that many characters and be a valid value of the element.

**Fix:** Send a valid value of exactly the documented maximum length; if no such value can be approved (a MasterCard card number is 16 digits, not 24), drop the positive boundary test.

## 4. Defects and issues in the test cases (design, status, expected results)

### 4.1 C-02 - Negative test premise contradicts ATL105 (the rule it claims to break does not exist)

**2 test cases, 2 findings** - kind: Test case defect

Rules: `X_NEG_PREMISE` (2).

ATL105:

- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"
- Section 10.14.3 EMV PIN (Personal Identification Number) Processing, PDF p.162 (printed 10-83): "Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "3 44 Information Byte 1 R"
- Section 12.3 Product Code Data Segment, PDF p.230 (printed 12-13): "3 87 Service Level 1 R Identifies the type of sale."
- Section 12.7 Loyalty Card Data Segment, PDF p.239 (printed 12-22): "12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE` (TC-075650) - Payment Tender Type (Element 148) is a Required field (R) of Segment 108 whenever the segment is sent.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075650](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC60) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Purchase/Capture wit… -> declined` | Remove or rewrite the test around a rule ATL105 states. |
| [TC-075751](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152) (BR1_TS2_TC53) | - | `X_NEG_PREMISE` | test case test_objective | `Single rule broken: Purchase/Capture wit… -> declined` | Remove or rewrite the test around a rule ATL105 states. |

Scenarios: BR1_TS1 (1), BR1_TS2 (1)

**How to check by hand:** Read the test objective, then the cited ATL105 rule: the transaction/field is supported or required.

**Fix:** Remove the test or rewrite it around a rule ATL105 states.

### 4.2 C-03 - Test case marked 'ready' although its Terminal Identifier is a placeholder

**16 test cases, 16 findings** - kind: Test case issue to correct

Rules: `READY_WITH_PLACEHOLDER` (16).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

What is wrong (one example per kind of problem):

- `READY_WITH_PLACEHOLDER` (TC-076120) - The test case status is 'ready', but its Terminal Identifier is still a placeholder.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076120](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L521) (BR1_TS5_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-076658](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) (BR1_TS15_TC7) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-075654](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC64) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-076586](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) (BR1_TS11_TC5) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-075755](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) (BR1_TS2_TC57) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-075656](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC66) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-075757](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) (BR1_TS2_TC59) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077186](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) (BR1_TS11_TC2) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-075667](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) (BR1_TS1_TC77) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-076423](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824) (BR1_TS7_TC103) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| … | | 4 more in the sheet (Findings, filter on C-03) | | | |

Scenarios: BR1_TS1 (5), BR1_TS11 (2), BR1_TS2 (2), BR1_TS19 (2), BR1_TS3 (2), BR1_TS5 (1), BR1_TS15 (1), BR1_TS7 (1)

**How to check by hand:** TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.

**Fix:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 4.3 C-04 - Expected Response Code not stated for a step, or several codes accepted

**6 test cases, 6 findings** - kind: Test case issue to correct

Rules: `EXPECTED_RESPONSE_OPEN` (6).

ATL105:

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"

What is wrong (one example per kind of problem):

- `EXPECTED_RESPONSE_OPEN` (TC-076596) - The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction.
- `EXPECTED_RESPONSE_OPEN` (TC-075667) - The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the timeout reversal step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076596](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) (BR1_TS11_TC15) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-075667](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) (BR1_TS1_TC77) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of 0, 2` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-076613](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) (BR1_TS12_TC13) | - | `EXPECTED_RESPONSE_OPEN` | test case step 2 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-077185](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) (BR1_TS31_TC1) | - | `EXPECTED_RESPONSE_OPEN` | test case step 1 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-077218](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) (BR1_TS29_TC1) | - | `EXPECTED_RESPONSE_OPEN` | test case step 1 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| [TC-076780](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) (BR1_TS21_TC10) | - | `EXPECTED_RESPONSE_OPEN` | test case step 1 expected_response | `? (FLAG)` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |

Scenarios: BR1_TS11 (1), BR1_TS12 (1), BR1_TS1 (1), BR1_TS31 (1), BR1_TS29 (1), BR1_TS21 (1)

**How to check by hand:** Open tx_legs[i].expect / steps[i].expected_response: no response_code value; or expected_response.response_code.acceptable_values lists several approval codes (e.g. 0 and 2) for a non-totals step. Approved-Totals codes 5/6/B/D/N/W for a Totals Request are accepted (Element 83, p.404-405).

**Fix:** State the expected Response Code (from BUYPASS or a reference run).

### 4.4 C-05 - Test objective / name does not match the message sent or the expected result

**1 test case, 1 finding** - kind: Test case issue to correct

Rules: `X_OBJECTIVE_MISMATCH` (1).

ATL105:

- Element 78 Prompt Code (Chapter 13), PDF p.400 (printed 13-60): "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters)."

What is wrong (one example per kind of problem):

- `X_OBJECTIVE_MISMATCH` (TC-076723) - The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type 'B' (Mail/Phone Authorization Only) and the name says Mail/Phone Authorization Only.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076723](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) (BR1_TS19_TC6) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to Mail/Phone Authorization Only. |

Scenarios: BR1_TS19 (1)

**How to check by hand:** Compare test_objective and the name with the Prompt Code Transaction Type in the test data and with the expected Response Code.

**Fix:** Correct the objective / name text.

### 4.5 C-10 - Positive test does not send the condition it claims (table, segment or field value)

**4 test cases, 4 findings, 4 test data files** - kind: Test case defect

Rules: `X_CONDITION_NOT_SENT` (4).

ATL105:

- Element 111 Variable Information Indicator (Chapter 13), PDF p.422 (printed 13-82): "Fixed length of 3 alphanumeric characters"
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "A Product Code Data Segment (Data Segment No. 102) must be present in the transaction."
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments."
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager."

What is wrong (one example per kind of problem):

- `X_CONDITION_NOT_SENT` (TC-075654) - The objective "Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.
- `X_CONDITION_NOT_SENT` (TC-075656) - The objective "Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds)" says the request carries Variable Information Indicator = 001, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Variable Information Indicator = 001 is approved - not that Variable Information Indicator = 001 is handled.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075654](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC64) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-075654.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json) | `Tax Type 1: not in the test data; segmen…00, 111, 130` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the |
| [TC-075656](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC66) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-075656.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json) | `Variable Information Indicator: not in t…00, 111, 130` | Send Variable Information Indicator = 001 in Variable Information Data Segment, or re-scope / withdraw the test. |
| [TC-075755](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) (BR1_TS2_TC57) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-075755.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075755.json) | `Tax Type 1: not in the test data; segmen…00, 111, 130` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the |
| [TC-075757](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) (BR1_TS2_TC59) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-075757.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075757.json) | `Variable Information Indicator: not in t…00, 111, 130` | Send Variable Information Indicator = 001 in Variable Information Data Segment, or re-scope / withdraw the test. |

Scenarios: BR1_TS1 (2), BR1_TS2 (2)

**How to check by hand:** Read test_objective and the name, then open the test data: the claimed table, segment or value is not in the request, and open_items / awaiting_inputs do not ask for it.

**Fix:** Send the claimed data with the segments it depends on, or keep the test out of 'ready' with the missing input named, or re-scope / withdraw it when the condition does not apply.

## 5. Citation issues (the ATL105 text quoted in the test cases)

### 5.1 Q-01 - Quote not found in ATL105 or missing (generator note or other text cited as ATL105)

**9 test cases, 11 findings** - kind: Citation issue

Rules: `QUOTE_NO_PAGE` (7), `QUOTE_MISSING` (3), `QUOTE_NOT_IN_SPEC` (1).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `QUOTE_NOT_IN_SPEC` (TC-076167) - The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.
- `QUOTE_NO_PAGE` (TC-077186) - The spec_basis entry quotes "EMV Chip Data Length: N max 3" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077193) - The spec_basis entry quotes "Prompt Code: AN max 4" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077219) - The spec_basis entry quotes "Variable Information: AN max 982" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077185) - The spec_basis entry quotes "Merchant Trace ID: AN max 25" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077206) - The spec_basis entry quotes "Encrypted PIN Block Data: AN max 36" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_NO_PAGE` (TC-077218) - The spec_basis entry quotes "Information Byte: AN max 1" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- `QUOTE_MISSING` (TC-077179) - The spec_basis entry gives a rule id and a page but no ATL105 text, so the basis cannot be checked word for word.
- `QUOTE_NO_PAGE` (TC-077203) - The spec_basis entry quotes "Customer Data: AN max 12" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076167](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) (BR1_TS5_TC48) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| [TC-077186](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) (BR1_TS11_TC2) | - | `QUOTE_NO_PAGE` | test case spec_basis | `EMV Chip Data Length: N max 3` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077193](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) (BR1_TS175_TC1) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Prompt Code: AN max 4` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Variable Information: AN max 982` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077185](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) (BR1_TS31_TC1) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Merchant Trace ID: AN max 25` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077206](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) (BR1_TS1_TC1) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Encrypted PIN Block Data: AN max 36` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077218](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) (BR1_TS29_TC1) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Information Byte: AN max 1` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077179](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) (BR1_TS29_TC4) | - | `QUOTE_MISSING` | test case spec_basis | `p.62` | Add the ATL105 sentence the rule is based on. |
| [TC-077203](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) (BR1_TS147_TC3) | - | `QUOTE_NO_PAGE` | test case spec_basis | `Customer Data: AN max 12` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| [TC-077179](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) (BR1_TS29_TC4) | - | `QUOTE_MISSING` | test case spec_basis | `p.112` | Add the ATL105 sentence the rule is based on. |
| [TC-077179](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) (BR1_TS29_TC4) | - | `QUOTE_MISSING` | test case spec_basis | `p.545` | Add the ATL105 sentence the rule is based on. |

Scenarios: BR1_TS29 (4), BR1_TS1 (2), BR1_TS5 (1), BR1_TS11 (1), BR1_TS175 (1), BR1_TS31 (1), BR1_TS147 (1)

**How to check by hand:** Search the quoted text on the cited page.

**Fix:** Quote the ATL105 sentence word for word.

### 5.2 Q-02 - Quote is on a different page than cited

**21 test cases, 21 findings** - kind: Citation issue

Rules: `QUOTE_PAGE_DRIFT` (18), `QUOTE_WRONG_PAGE` (2), `CITATION_OUTDATED_PAGE` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"

What is wrong (one example per kind of problem):

- `QUOTE_WRONG_PAGE` (TC-076166) - The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- `QUOTE_WRONG_PAGE` (TC-076168) - The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).
- `QUOTE_PAGE_DRIFT` (TC-075618) - The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).
- `CITATION_OUTDATED_PAGE` (TC-077219) - The test case cites p.246 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.246 (printed 12-29) does not contain it; it is on PDF p.555 (printed Appendix I-6).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076166](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) (BR1_TS5_TC47) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `The Sequence Number must be the Sequence…transaction.` | Cite p.84 instead of p.402. |
| [TC-076168](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) (BR1_TS5_TC49) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `A TOR must be sent from the same device … in the TOR.` | Cite p.40 instead of p.38. |
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | - | `CITATION_OUTDATED_PAGE` | test case request -> Variable Information Data Segment -> Variable Information (source_page), every step | `p.246` | Change the cited page from 246 to 555. The data values are not affected. |
| [TC-075654](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC64) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-075699](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) (BR1_TS1_TC109) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-076586](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) (BR1_TS11_TC5) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-075650](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC60) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-075755](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) (BR1_TS2_TC57) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-075656](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC66) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-075757](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) (BR1_TS2_TC59) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| … | | 9 more in the sheet (Findings, filter on Q-02) | | | |

Scenarios: BR1_TS1 (11), BR1_TS11 (3), BR1_TS2 (3), BR1_TS5 (2), BR1_TS12 (1), BR1_TS21 (1)

**How to check by hand:** Find the quote: it is on another PDF page.

**Fix:** Correct the page number.

### 5.3 Q-03 - Quote is not word-for-word

**9 test cases, 13 findings** - kind: Citation issue

Rules: `QUOTE_PARAPHRASED` (13).

ATL105:

- Element 86 Sequence Number (Chapter 13), PDF p.409 (printed 13-69): "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request."

What is wrong (one example per kind of problem):

- `QUOTE_PARAPHRASED` (TC-075618) - The test case quotes "Table 048 MasterCard Authentication Data" for p.586. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-075657) - The test case quotes "Table Data The Service Location Country Code must be present for all transactions when different from the location identified in the Acceptor Country Code." for p.622. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-075657) - The test case quotes "0 = False 1 = True Sub Table ID "04" Local Tax Included Indicator Field Attributes Description Sub-Table an2 Fixed Value:" for p.632. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-075656) - The test case quotes "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." for p.149. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-075656) - The test case quotes "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." for p.152. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-076725) - The test case quotes "In Purchase/Capture, Sequence Number is same as in Authorization Only request." for p.409. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-075601) - The test case quotes "Table 001 UPC Information" for p.552. Not word-for-word: all its words are on the cited page, but not in this form.
- `QUOTE_PARAPHRASED` (TC-075810) - The test case quotes "Table 031 Point-of-Service Terminal Capability Codes" for p.569. Not word-for-word: all its words are on the cited page, but not in this form.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table Data The Service Location Country …ountry Code.` | Quote the text exactly as it appears on the page. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `0 = False 1 = True Sub Table ID "04" Loc…Fixed Value:` | Quote the text exactly as it appears on the page. |
| [TC-075656](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC66) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-075656](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) (BR1_TS1_TC66) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-076725](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126) (BR1_TS19_TC8) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `In Purchase/Capture, Sequence Number is …nly request.` | Quote the text exactly as it appears: "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only re |
| [TC-075601](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC11) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 001 UPC Information` | Quote the text exactly as it appears on the page. |
| [TC-075810](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L211) (BR1_TS3_TC12) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 031 Point-of-Service Terminal Capability Codes` | Quote the text exactly as it appears on the page. |
| [TC-075757](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) (BR1_TS2_TC59) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-075757](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) (BR1_TS2_TC59) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| [TC-077219](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) (BR1_TS1_TC4) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Transaction Transaction Attributes Seque…ransactions.` | Quote the text exactly as it appears on the page. |
| … | | 1 more in the sheet (Findings, filter on Q-03) | | | |

Scenarios: BR1_TS1 (8), BR1_TS2 (2), BR1_TS19 (1), BR1_TS3 (1), BR1_TS11 (1)

**How to check by hand:** Compare the quote with the page text.

**Fix:** Quote word for word.

### 5.4 Q-04 - Citation or device checkpoint from an unrelated section or document

**2 test cases, 2 findings** - kind: Citation issue

Rules: `X_BASIS_CONTEXT` (2).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)."

What is wrong (one example per kind of problem):

- `X_BASIS_CONTEXT` (TC-076658) - The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076658](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) (BR1_TS15_TC7) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |
| [TC-076723](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) (BR1_TS19_TC6) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |

Scenarios: BR1_TS15 (1), BR1_TS19 (1)

**How to check by hand:** Read the cited text: it covers another transaction, card or industry.

**Fix:** Cite the rule that applies.

## 6. Values awaiting client input or run-time values (BLOCKED, not defects)

### 6.1 I-01 - Terminal Identifier is a generated placeholder

**36 test cases, 36 findings** - kind: Awaiting client input / run-time value

Rules: `TERMINAL_PLACEHOLDER` (36).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Oregon OR 41"
- Appendix D. Valid State Codes, PDF p.514 (printed Appendix D-1): "Hawaii HI 15"
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "Tennessee TN 47"

What is wrong (one example per kind of problem):

- `TERMINAL_PLACEHOLDER` (TC-076120) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX41894821001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '41' = Oregon (OR), United States.
- `TERMINAL_PLACEHOLDER` (TC-075670) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'XE6LR2ZPLXTZ9BOKDYPUAB' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '6L' = not a valid State Code.
- `TERMINAL_PLACEHOLDER` (TC-077193) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'VZ15598162001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '15' = Hawaii (HI), United States.
- `TERMINAL_PLACEHOLDER` (TC-077203) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'KC47645707001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '47' = Tennessee (TN), United States.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076120](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L521) (BR1_TS5_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-075670](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L71) (BR1_TS1_TC80) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `XE6LR2ZPLXTZ9BOKDYPUAB` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077193](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) (BR1_TS175_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `VZ15598162001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-077203](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) (BR1_TS147_TC3) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `KC47645707001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-076166](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) (BR1_TS5_TC47) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-076167](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) (BR1_TS5_TC48) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-076168](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) (BR1_TS5_TC49) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-076658](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) (BR1_TS15_TC7) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-075654](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC64) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-075699](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) (BR1_TS1_TC109) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| … | | 24 more in the sheet (Findings, filter on I-01) | | | |

Scenarios: BR1_TS1 (12), BR1_TS5 (4), BR1_TS11 (4), BR1_TS2 (3), BR1_TS7 (2), BR1_TS19 (2), BR1_TS3 (2), BR1_TS15 (1), BR1_TS175 (1), BR1_TS12 (1), BR1_TS31 (1), BR1_TS29 (1), BR1_TS21 (1), BR1_TS147 (1)

**How to check by hand:** merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.

**Fix:** Client / BUYPASS to supply the real Terminal Identifier.

### 6.2 I-02 - Value filled in at run time from the previous response (expected for follow-on steps)

**9 test cases, 10 findings, 9 test data files** - kind: Awaiting client input / run-time value

Rules: `RUNTIME_PLACEHOLDER` (7), `SEGMENT_LENGTH_RUNTIME` (3).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled"
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."

What is wrong (one example per kind of problem):

- `RUNTIME_PLACEHOLDER` (TC-076166) - The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- `SEGMENT_LENGTH_RUNTIME` (TC-075685) - '058' depends on the run-time value(s) Account Number, Card Discretionary Block Data, Approval Number.
- `SEGMENT_LENGTH_RUNTIME` (TC-077193) - '054' depends on the run-time value(s) Account Number.
- `SEGMENT_LENGTH_RUNTIME` (TC-077206) - '072' depends on the run-time value(s) Encrypted PIN Block Data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076166](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) (BR1_TS5_TC47) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-076166.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-075685](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) (BR1_TS1_TC95) | 2 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-075685.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step2.reversal.json#L7) | `058` | Recalculate the Segment Length after filling in the real values. |
| [TC-077193](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) (BR1_TS175_TC1) | 1 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-077193.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L7) | `054` | Recalculate the Segment Length after filling in the real values. |
| [TC-077206](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) (BR1_TS1_TC1) | 1 | `SEGMENT_LENGTH_RUNTIME` | [TD-TC-077206.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json#L7) | `072` | Recalculate the Segment Length after filling in the real values. |
| [TC-076167](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) (BR1_TS5_TC48) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-076167.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-075685](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) (BR1_TS1_TC95) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-075685.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-076596](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) (BR1_TS11_TC15) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-076596.step2.cancellation.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step2.cancellation.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: p.85 'Element No. 5 (Approval Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction' - value from th |
| [TC-076613](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) (BR1_TS12_TC13) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-076613.step2.cancellation.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step2.cancellation.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: p.85 'Element No. 5 (Approval Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction' - value from th |
| [TC-076725](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126) (BR1_TS19_TC8) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-076725.step2.completion.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'If it is included in a Purchase Request, then the Purchase Request is considered preauthorized' - the Authorization Only response's Approval Number  |
| [TC-076597](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) (BR1_TS11_TC16) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-076597.step2.completion.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'If it is included in a Purchase Request, then the Purchase Request is considered preauthorized' - the Authorization Only response's Approval Number  |

Scenarios: BR1_TS1 (3), BR1_TS5 (2), BR1_TS11 (2), BR1_TS175 (1), BR1_TS12 (1), BR1_TS19 (1)

**How to check by hand:** The field holds text in angle brackets, e.g. <ApprovalNumber from step 1 response>.

**Fix:** The harness substitutes the value from the previous response and recalculates the Segment Length.

### 6.3 I-03 - Variable Information table value awaited from the client (Segment 111 length to recalculate)

**3 test cases, 3 findings, 3 test data files** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_TABLE` (3).

ATL105:

- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "Identifies the length of Variable Information (Element No. 113)."
- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "A Field Separator follows the final Variable Information Section."
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment"
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or"
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "The remaining 00–20 characters contain street address information."

What is wrong (one example per kind of problem):

- `X_AWAITING_TABLE` (TC-075699) - Table(s) 003 hold <AWAITING:...> text instead of a value. The Segment 111 Segment Length '045' counts only the table indicator, so it must be recalculated when the value is filled in. The test case lists: step 1: 111 Variable Information Length [4] - Table 003 length follows its awaited data

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075699](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) (BR1_TS1_TC109) | 1 | `X_AWAITING_TABLE` | [TD-TC-075699.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075699.json) | `Table 003` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | 1 | `X_AWAITING_TABLE` | [TD-TC-075657.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075657.json) | `Table 003` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |
| [TC-075601](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L2) (BR1_TS1_TC11) | 1 | `X_AWAITING_TABLE` | [TD-TC-075601.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075601.json) | `Table 001` | Fill in the client value, then recalculate Variable Information Length and the Segment 111 length. |

Scenarios: BR1_TS1 (3)

**How to check by hand:** VariableInformationLength / VariableInformation hold <AWAITING:...>; awaiting_inputs lists the value.

**Fix:** Fill in the client value and recalculate Variable Information Length and Segment 111 Segment Length.

### 6.4 I-04 - EMV chip data is generated, not from a card / simulator

**22 test cases, 48 findings** - kind: Awaiting client input / run-time value

Rules: `EMV_CA_CHECKSUM_SAMPLE` (22), `EMV_PLACEHOLDER_CRYPTO` (22), `EMV_CONTACTLESS_TEMPLATE` (4).

ATL105:

- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."
- Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags."

What is wrong (one example per kind of problem):

- `EMV_CA_CHECKSUM_SAMPLE` (TC-075618) - The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- `EMV_PLACEHOLDER_CRYPTO` (TC-075618) - ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- `EMV_CONTACTLESS_TEMPLATE` (TC-075755) - The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=50652082A4178133, 9F36=187B, 9F37=3…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-075755](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) (BR1_TS2_TC57) | - | `EMV_CONTACTLESS_TEMPLATE` | EMV Request Data Segment -> EMVChipData (every EMV request) | `22 tags, CVM 9F34 = 5E0302` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| [TC-075654](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC64) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-075654](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) (BR1_TS1_TC64) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=46D9B2E7B76B4AA6, 9F36=923E, 9F37=0…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-075699](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) (BR1_TS1_TC109) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-075699](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) (BR1_TS1_TC109) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=06A2AE10B04417ED, 9F36=1F7D, 9F37=3…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-075657](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) (BR1_TS1_TC67) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=2A2AE6FF4C1DBDD7, 9F36=92C5, 9F37=F…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-076586](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) (BR1_TS11_TC5) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-076586](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) (BR1_TS11_TC5) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=6065F4A5762643F9, 9F36=F2BB, 9F37=7…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-075650](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC60) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| … | | 36 more in the sheet (Findings, filter on I-04) | | | |

Scenarios: BR1_TS1 (22), BR1_TS2 (9), BR1_TS11 (8), BR1_TS12 (3), BR1_TS29 (2), BR1_TS21 (2), BR1_TS147 (2)

**How to check by hand:** See the EMV finding text in the guide / sheet.

**Fix:** Use chip data captured from the client's test card or card simulator.

### 6.5 I-05 - Totals Request device versions are generated placeholders

**4 test cases, 4 findings, 4 test data files** - kind: Awaiting client input / run-time value

Rules: `TOTALS_VERSION_PLACEHOLDER` (4).

ATL105:

- Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters"
- Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters"
- Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters"

What is wrong (one example per kind of problem):

- `TOTALS_VERSION_PLACEHOLDER` (TC-076596) - ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076596](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) (BR1_TS11_TC15) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-076596.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json) | `H86V8MTA, C33JC0M4, UHYXXEE0` | Use the versions of the certified device that runs the test. |
| [TC-076613](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) (BR1_TS12_TC13) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-076613.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json) | `PCWCHE0E, UTFOYCO7, 7WO2I7PD` | Use the versions of the certified device that runs the test. |
| [TC-076597](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) (BR1_TS11_TC16) | 3 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-076597.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) | `MZ3AAYCX, QQND0U7W, TTHXJWLT` | Use the versions of the certified device that runs the test. |
| [TC-076780](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) (BR1_TS21_TC10) | 2 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-076780.step2.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json) | `EC1AYF6Q, ICIUZVQS, 73FB5C66` | Use the versions of the certified device that runs the test. |

Scenarios: BR1_TS11 (2), BR1_TS12 (1), BR1_TS21 (1)

**How to check by hand:** Hardware/Software/Firmware Version hold random strings.

**Fix:** Client to supply the versions of the certified device.

### 6.6 I-06 - Optional segment content is synthesized - real client data needed before an approval can be expected

**3 test cases, 3 findings, 3 test data files** - kind: Awaiting client input / run-time value

Rules: `X_SYNTHESIZED_CONTENT` (3).

ATL105:

- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"

What is wrong (one example per kind of problem):

- `X_SYNTHESIZED_CONTENT` (TC-076658) - The test case generated the Segment 153 values itself (spec_tlv_composite); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's network-token entitlement and a real provisioned network token.
- `X_SYNTHESIZED_CONTENT` (TC-075855) - The test case generated the Segment 108 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's loyalty program data and loyalty card.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076658](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) (BR1_TS15_TC7) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-076658.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| [TC-075855](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L256) (BR1_TS3_TC57) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-075855.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075855.json) | `format_synthesis` | Client to supply the merchant's loyalty program data and loyalty card; keep the test case out of 'ready' until then. |
| [TC-076723](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) (BR1_TS19_TC6) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-076723.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |

Scenarios: BR1_TS15 (1), BR1_TS19 (1), BR1_TS3 (1)

**How to check by hand:** In test_cases.jsonl the segment fields have method format_synthesis / spec_tlv_composite.

**Fix:** Client to supply the data listed in the finding; keep the test case out of 'ready' until then.

### 6.7 I-07 - Decline trigger needed: the request is identical to the approval test

**1 test case, 1 finding, 1 test data file** - kind: Awaiting client input / run-time value

Rules: `X_SAME_REQUEST_NEEDS_TRIGGER` (1).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST_NEEDS_TRIGGER` (TC-075855) - Apart from the Sequence Number the message is identical to TC-075853 (all segments compared). TC-075853 expects 1; this test expects 0. The test case asks for a decline trigger in open_items.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075855](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L256) (BR1_TS3_TC57) | 1 | `X_SAME_REQUEST_NEEDS_TRIGGER` | TD-TC-075855.json vs TD-TC-075853.json | `expected 0 vs 1` | Supply the decline trigger so the request differs. |

Scenarios: BR1_TS3 (1)

**How to check by hand:** open_items asks for a decline test card / simulator trigger.

**Fix:** Client to supply the decline trigger; until then the test sends the approval test's request.

### 6.8 I-08 - No test data generated for a host test case

**1 test case, 1 finding** - kind: Awaiting client input / run-time value

Rules: `X_NO_TEST_DATA` (1).

What is wrong (one example per kind of problem):

- `X_NO_TEST_DATA` (TC-077179) - The test case sends a message to BUYPASS (EMV Financial Transaction Request) but has no test data file (status blocked_no_test_data).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077179](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) (BR1_TS29_TC4) | - | `X_NO_TEST_DATA` | test case test_data_file | `blocked_no_test_data` | Generate the test data once the missing input is available. |

Scenarios: BR1_TS29 (1)

**How to check by hand:** test_data_file is empty in test_cases.jsonl; status blocked_no_test_data.

**Fix:** Generate the test data when the missing input is available.

### 6.9 I-09 - Segment 100 value awaited from the client (<AWAITING:...>)

**3 test cases, 4 findings, 3 test data files** - kind: Awaiting client input / run-time value

Rules: `X_AWAITING_VALUE` (4).

ATL105:

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_AWAITING_VALUE` (TC-075685) - The field holds '<AWAITING:AccountNumber>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075685](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) (BR1_TS1_TC95) | 2 | `X_AWAITING_VALUE` | [TD-TC-075685.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step2.reversal.json#L14) | `<AWAITING:AccountNumber>` | Client to supply the value (for Account Number: a MasterCard test card). |
| [TC-075685](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) (BR1_TS1_TC95) | 2 | `X_AWAITING_VALUE` | [TD-TC-075685.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step2.reversal.json#L15) | `<AWAITING:CardDiscretionaryBlockData>` | Client to supply the value (for Account Number: a MasterCard test card). |
| [TC-077193](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) (BR1_TS175_TC1) | 1 | `X_AWAITING_VALUE` | [TD-TC-077193.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L11) | `<AWAITING:AccountNumber>` | Client to supply the value (for Account Number: a MasterCard test card). |
| [TC-077206](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) (BR1_TS1_TC1) | 1 | `X_AWAITING_VALUE` | [TD-TC-077206.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json#L16) | `<AWAITING:EncryptedPINBlockData>` | Client to supply the value (for Account Number: a MasterCard test card). |

Scenarios: BR1_TS1 (3), BR1_TS175 (1)

**How to check by hand:** The field holds <AWAITING:...>; the test case field has method awaiting_client_value.

**Fix:** Client to supply the value (for Account Number: a MasterCard test card).

## 7. Points to confirm with BUYPASS / SME

### 7.1 C-06 - Time-out Reversal sent after a step that is expected to be answered

**2 test cases, 2 findings, 2 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_TOR_AFTER_RESPONSE` (2).

ATL105:

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR"

What is wrong (one example per kind of problem):

- `X_TOR_AFTER_RESPONSE` (TC-076168) - ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0, so the device would not send a TOR.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076168](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) (BR1_TS5_TC49) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |
| [TC-075667](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) (BR1_TS1_TC77) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |

Scenarios: BR1_TS5 (1), BR1_TS1 (1)

**How to check by hand:** Read tx_legs: the original step expects a Response Code and has no 'no response' harness directive, yet the next step is a Time-out Reversal.

**Fix:** Make the original step a fault-injected 'no response' (or a late response the harness ignores).

### 7.2 C-07 - 'Different terminal' negative uses a generated terminal that is not set up at BUYPASS

**3 test cases, 3 findings, 3 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_RANDOM_TERMINAL` (3).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `X_NEG_RANDOM_TERMINAL` (TC-076167) - Step 2 uses the generated Terminal Identifier 'VS24671841985066391107' (method deliberate_violation_missing_carry). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.
- `X_NEG_RANDOM_TERMINAL` (TC-077179) - Step 1 uses the generated Terminal Identifier 'None' (method deliberate_violation_missing_segment). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076167](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) (BR1_TS5_TC48) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-076167.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L9) | `VS24671841985066391107` | Use a second real terminal of the same merchant. |
| [TC-077179](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) (BR1_TS29_TC4) | 1 | `X_NEG_RANDOM_TERMINAL` | - | `None` | Use a second real terminal of the same merchant. |
| [TC-076168](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) (BR1_TS5_TC49) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-076168.step2.timeout-reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L9) | `QL66203054504417704598` | Use a second real terminal of the same merchant. |

Scenarios: BR1_TS5 (2), BR1_TS29 (1)

**How to check by hand:** Read the follow-on step's Terminal Identifier: method deliberate_violation_missing_carry, a generated value.

**Fix:** Use a second real terminal of the same merchant so the decline proves the same-device rule.

### 7.3 C-08 - Same request as another test case (difference only on the device / response side)

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_SAME_REQUEST` (1).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST` (TC-075810) - Apart from the Sequence Number the message is identical to TC-075801 (all segments compared). TC-075801 expects 0; this test expects 0. What makes the two tests different is not in the message sent to BUYPASS.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075810](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L211) (BR1_TS3_TC12) | 1 | `X_SAME_REQUEST` | TD-TC-075810.json vs TD-TC-075801.json | `expected 0 vs 0` | Check the difference on the device / in the response; optionally use another card. |

Scenarios: BR1_TS3 (1)

**How to check by hand:** Compare both test data files: identical apart from the Sequence Number.

**Fix:** Check the difference on the device or in the response; optionally use another card.

### 7.4 C-09 - Segment sent although ATL105 scopes it to other card programs

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_SCOPE` (1).

ATL105:

- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "The Enhanced Fleet Request Segment contains fleet data that applies to the enhanced fleet offerings such as Wex OTR, Comdata, Voyager EMV, and Visa Fleet 2.0."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data."

What is wrong (one example per kind of problem):

- `X_SEGMENT_SCOPE` (TC-077203) - ATL105 describes Segment 101 as fleet data, sent only on transactions requiring fleet data. This test uses a MasterCard Credit card (card type 020), which is not a fleet card.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077203](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) (BR1_TS147_TC3) | 1 | `X_SEGMENT_SCOPE` | [TD-TC-077203.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json) | `Segment 101` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |

Scenarios: BR1_TS147 (1)

**How to check by hand:** Read the segment description in section 11.1 / 12.x and compare with the card program (MasterCard Credit, card type 020).

**Fix:** Confirm with BUYPASS or drop the segment from MasterCard Credit tests.

### 7.5 C-12 - Table 048 (MasterCard 3-D Secure authentication data) sent on a POS transaction

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_TABLE_SCOPE` (1).

ATL105:

- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication."
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID."
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.587 (printed Appendix I-38): "1 = EMV 3-D Secure Version 2.1"
- Element 111 Variable Information Indicator (Chapter 13), PDF p.424 (printed 13-84): "048 MasterCard Authentication Data"

What is wrong (one example per kind of problem):

- `X_TABLE_SCOPE` (TC-075618) - Table 048 = '1' (Program Protocol 1 = EMV 3-D Secure Version 2.1). ATL105 applies this field to merchants sending e-commerce transactions with 3-D Secure / EMV 3-D Secure authentication. This step is a POS Purchase/Capture (Prompt Code 0020, Table 005 entry mode 05 = chip read) at a POS terminal, not an e-commerce transaction, so no 3-D Secure authentication took place and an approval does not show that BUYPASS processes MasterCard authentication data.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-075618](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) (BR1_TS1_TC28) | 1 | `X_TABLE_SCOPE` | [TD-TC-075618.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json) | `1` | Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the test. |

Scenarios: BR1_TS1 (1)

**How to check by hand:** Open the test data: Segment 111 carries Table 048 (Program Protocol), while Prompt Code and Table 005 show a POS card-present or keyed transaction, not an e-commerce (3-D Secure) transaction.

**Fix:** Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the test.

### 7.6 S-01 - Expiry format for stored credentials (entry mode 10) not stated

**2 test cases, 2 findings** - kind: To confirm with BUYPASS / SME

Rules: `COF_EXPIRY_FORMAT` (2).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."

What is wrong (one example per kind of problem):

- `COF_EXPIRY_FORMAT` (TC-077181) - The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077181](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) (BR1_TS7_TC1) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1131` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-076423](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824) (BR1_TS7_TC103) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Confirm the expiry format for entry mode 10 with BUYPASS. |

Scenarios: BR1_TS7 (2)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.7 S-02 - Totals: Authorization Only (AO) bucket after the flow

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `TOTALS_AO_BUCKET` (1).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."

What is wrong (one example per kind of problem):

- `TOTALS_AO_BUCKET` (TC-076597) - Section 12.6 lists Preauthorization (AO) as bucket 4, and the grand total leaves the AO bucket out. This flow has an approved Authorization Only, but only the CC bucket is sent. ATL105 says cancellations subtract from the AO bucket; it does not say whether a completed authorization stays in it.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076597](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) (BR1_TS11_TC16) | 3 | `TOTALS_AO_BUCKET` | [TD-TC-076597.step3.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) | `CC  ` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |

Scenarios: BR1_TS11 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.8 S-03 - Segment length: ATL105 Field Separator rule of the segment is unclear

**5 test cases, 5 findings, 5 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_LENGTH_UNCLEAR` (5).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "There is a Field Separator between Field Nos. 1 and 2."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "Field Separators are not there between the elements in Network Token Data Layouts."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"

What is wrong (one example per kind of problem):

- `X_SEGMENT_LENGTH_UNCLEAR` (TC-076658) - '051' assumes a Field Separator after every field (051). ATL105 reads differently for this segment - Field Separator only between Fields 1 and 2 (literal reading of p.310): 049; Field Separator after Fields 1 and 2, none after Field 3: 050.
- `X_SEGMENT_LENGTH_UNCLEAR` (TC-075650) - '120' assumes a Field Separator after every field (120). ATL105 reads differently for this segment - Field Separators up to Field 13 only (literal reading of p.238): 118.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076658](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) (BR1_TS15_TC7) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-076658.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-075650](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) (BR1_TS1_TC60) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-075650.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-075751](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152) (BR1_TS2_TC53) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-075751.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075751.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-076723](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) (BR1_TS19_TC6) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-076723.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json#L45) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |
| [TC-075855](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L256) (BR1_TS3_TC57) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-075855.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-075855.json#L7) | `120` | Confirm the Field Separator rule of this segment with BUYPASS. |

Scenarios: BR1_TS15 (1), BR1_TS1 (1), BR1_TS2 (1), BR1_TS19 (1), BR1_TS3 (1)

**How to check by hand:** Recalculate the length under both readings shown in the finding.

**Fix:** Confirm with BUYPASS.

### 7.9 S-04 - Network Token Input Indicator 'Y' while Element 2 carries a clear card number

**2 test cases, 2 findings, 2 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_TOKEN_INDICATOR` (2).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN"
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_TOKEN_INDICATOR` (TC-076658) - Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number 514031…3116 and the token is only in table 001. ATL105 does not say which field carries the token when the indicator is Y.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076658](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) (BR1_TS15_TC7) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-076658.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |
| [TC-076723](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) (BR1_TS19_TC6) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-076723.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json#L46) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |

Scenarios: BR1_TS15 (1), BR1_TS19 (1)

**How to check by hand:** Parse Network Token Data table 004.

**Fix:** Confirm with BUYPASS which field carries the token when the indicator is Y.

### 7.10 S-05 - Other points to confirm

**3 test cases, 3 findings, 2 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_PREMISE_CONFIRM` (1), `X_MOTO_COMPLETION_CODE` (1), `EMV_COMPLETION_NO_CHIP_DATA` (1).

ATL105:

- Section 12.19 NFC Payment Tokenization Data Segment, PDF p.267 (printed 12-50): "It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions."
- Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Section 10.14.2.4 Supported Transaction Types (EMV), PDF p.161 (printed 10-82): "EMV data is not required on Reversal transactions."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE_CONFIRM` (TC-077181) - p.267 requires Segment 123 only on transactions involving tokenized data (MasterCard Token, DSRP or Visa TAVV data). This test sends a card number without any token data, so leaving the segment out may be correct and BUYPASS may approve.
- `X_MOTO_COMPLETION_CODE` (TC-076725) - Step 2 completes the Mail/Phone Authorization Only (Transaction Type B) of step 1 with Prompt Code '6020' (Transaction Type 6 = Mail/Phone Purchase) and the Approval Number. Appendix G names a completion code only for POS (0 = point-of-sale purchase/preauthorized completion) and customer-activated (4) purchases; Element 5 says a Purchase Request carrying an Approval Number is preauthorized. ATL105 does not say which Transaction Type completes a Mail/Phone Authorization Only.
- `EMV_COMPLETION_NO_CHIP_DATA` (TC-076597) - ATL105 says EMV data is not required on reversal transactions, but it says nothing about completions. The completion is a preauthorized Purchase/Capture (it carries the authorization's Approval Number).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-077181](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) (BR1_TS7_TC1) | - | `X_NEG_PREMISE_CONFIRM` | test case test_objective | `NFC Payment Tokenization Data Segment (s… requires it` | Confirm with BUYPASS, or send a transaction for which the segment is required. |
| [TC-076725](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126) (BR1_TS19_TC8) | 2 | `X_MOTO_COMPLETION_CODE` | [TD-TC-076725.step2.completion.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L10) | `6020` | Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0. |
| [TC-076597](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) (BR1_TS11_TC16) | 2 | `EMV_COMPLETION_NO_CHIP_DATA` | TD-TC-076597.step2.completion.json | `(absent)` | Confirm with BUYPASS whether a completion of an EMV authorization is sent without Segment 130. |

Scenarios: BR1_TS7 (1), BR1_TS19 (1), BR1_TS11 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS / SME.

### 7.11 S-06 - Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `TOTALS_NEGATIVE_NET` (1).

ATL105:

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places"

What is wrong (one example per kind of problem):

- `TOTALS_NEGATIVE_NET` (TC-076780) - Section 9.2: a Merchandise Return increases the count and decreases the dollar amount, so the card bucket nets to count 00001 and amount -$15.00. Card Type Total Amount (Element 15) is a fixed-length unsigned 8-digit amount and ATL105 does not say how a negative net amount is sent; the file sends 00000000.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-076780](../../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) (BR1_TS21_TC10) | 2 | `TOTALS_NEGATIVE_NET` | [TD-TC-076780.step2.totals.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json) | `count 00001, amount 00000000, grand total 00000000` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |

Scenarios: BR1_TS21 (1)

**How to check by hand:** Open the Totals Request file: count 00001 is right (a return increases the count), the amount nets to minus the return amount, and the file sends 00000000.

**Fix:** Confirm with BUYPASS how a negative net amount is reported in the Totals Request.

## 8. Negative tests - intended violations (correct)

7 negative test cases break their rule as designed (8 findings). They are not defects; check that each test breaks only that rule.

| Rule | Findings | Example |
|---|---:|---|
| `LINK_TERMINALIDENTIFIER` | 2 | TC-076167 step 2: Step 2: same terminal as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined |
| `LINK_SEQUENCENUMBER` | 1 | TC-076166 step 2: Step 2: same sequence number as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined |
| `FIELD_189` | 1 | TC-077186 step 1: EMV Chip Data Length (Element 189) does not match the chip data - Required field EMV Chip Data Length sent empty |
| `FIELD_EMPTY` | 1 | TC-077193 step 1: Prompt Code is empty - Required field Prompt Code omitted |
| `SEG111_LENGTH` | 1 | TC-077219 step 1: Segment 111 length is wrong - The declared Segment Length matches the content; the deliberately over-long field makes the segment longer than its 3-digit Segment Length can express. |
| `TABLE_005` | 1 | TC-077219 step 1: Table 005 value is not valid - Variable Information one byte over its documented maximum length (982) |
| `X_FIELD_FORMAT` | 1 | TC-077203 step 1: Fleet Data Segment: Customer Data breaks its Chapter 13 format - Customer Data contains a character outside its character type (AN) |

## 9. AI validator rules that did not apply to this run (value checked - correct)

| Rule | Findings | Test cases | Why the value is correct |
|---|---:|---:|---|
| `FIELD_12` | 1 | 1 | This step completes the Authorization Only of step 1 (preauthorized Purchase/Capture). Credit completions send Account Number = YYMM (Section 10.1.8.2), which is what the file has; the AI rule treated the step as a new purchase. |

Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator did not read segments 101-153; section 2 point 2 now checks them).

Run-wide checks (all 1,623 test cases of the run were validated so that these checks see every test case): 220 single-step test cases send the same request as an earlier test case (all segments compared); 1,462 new transactions checked for a unique Sequence Number per terminal, 0 duplicate(s).

## 10. Result per scenario

| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |
|---|---|---|---:|---:|---:|---:|---:|---:|---|
| BR1_TS1 | Purchase/Capture – EMV Contact Chip | GENERAL | 12 | 7 | 5 | 0 | 0 | 0 | D-09 (3), C-10 (2), D-13 (2) |
| BR1_TS2 | Purchase/Capture – EMV Contactless Chip | GENERAL | 3 | 3 | 0 | 0 | 0 | 0 | C-10 (2), C-02 (1) |
| BR1_TS3 | Purchase/Capture – Magnetic Stripe | GENERAL | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS5 | Purchase/Capture – Manual / Keyed | GENERAL | 4 | 3 | 1 | 0 | 0 | 0 | D-16 (3) |
| BR1_TS7 | Purchase/Capture – Credential on File | CARD_NOT_PRESENT-0100 | 2 | 2 | 0 | 0 | 0 | 0 | D-09 (2), D-11 (1) |
| BR1_TS11 | Authorization Only – EMV Contact Chip | GENERAL | 4 | 3 | 1 | 0 | 0 | 0 | D-01 (2), D-13 (1), D-09 (1) |
| BR1_TS12 | Authorization Only – EMV Contactless Chip | GENERAL | 1 | 1 | 0 | 0 | 0 | 0 | D-01 (1) |
| BR1_TS15 | Authorization Only – Manual / Keyed | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS19 | Mail/Phone Authorization Only – Manual / Keyed | CARD_NOT_PRESENT-2100 | 2 | 0 | 2 | 0 | 0 | 0 | - |
| BR1_TS21 | Merchandise Return – EMV Contact Chip | GENERAL | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BR1_TS29 | Balance Inquiry – EMV Contact Chip | GENERAL | 2 | 1 | 1 | 0 | 0 | 0 | D-15 (1), D-13 (1) |
| BR1_TS31 | Balance Inquiry – Magnetic Stripe | GENERAL | 1 | 1 | 0 | 0 | 0 | 0 | D-13 (1), D-11 (1) |
| BR1_TS147 | Purchase/Capture – EMV Contact Chip | PETROLEUM-ATTENDED | 1 | 1 | 0 | 0 | 0 | 0 | D-13 (1) |
| BR1_TS175 | Authorization Only – Magnetic Stripe | PETROLEUM-UNATTENDED | 1 | 1 | 0 | 0 | 0 | 0 | D-13 (1), D-09 (1) |

## 11. ATL105 references used

| Reference | Section | PDF p. | Printed | Text (verified on the page) |
|---|---|---:|---|---|
| APPD-15 | Appendix D. Valid State Codes | 514 | Appendix D-1 | Hawaii HI 15 |
| APPD-24 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Maryland MD 24 |
| APPD-41 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Oregon OR 41 |
| APPD-47 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Tennessee TN 47 |
| APPD-66 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Nova Scotia NS 66 |
| APPE-020 | Appendix E. Valid Card Type Codes | 521 | Appendix E-4 | 020 Credit Credit |
| APPG-0 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion |
| APPG-3 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 3 POS Authorization Only Point-of-sale authorization only (no draft capture) |
| APPG-6 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 6 Mail/Phone Purchase Mail/Telephone order |
| APPG-7 | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | 7 Merchandise return Merchandise return/refund |
| APPG-8 | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | 8 Purchase reversal Purchase reversal/void |
| APPG-B | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | B Mail/Phone Authorization Only Mail/Telephone authorization only |
| APPG-E | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | E Balance Inquiry Balance Inquiry—Credit, EBT cash benefits and stored value card |
| APPG-S | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | S Cancellation Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction. |
| APPG-Z | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | Z Time-out Reversal Time-out reversal |
| APPI-003-ADDR | Appendix I. Variable Information Data Layouts - AVS Information (Table 003) | 554 | Appendix I-5 | The remaining 00–20 characters contain street address information. |
| APPI-003-ZIP | Appendix I. Variable Information Data Layouts - AVS Information (Table 003) | 554 | Appendix I-5 | ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or |
| APPI-005 | Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005) | 555 | Appendix I-6 | Point-of-Service Entry Mode Information |
| APPI-006 | Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006) | 555 | Appendix I-6 | Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)] |
| APPI-030 | Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030) | 568 | Appendix I-19 | Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions. |
| APPI-031 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | Terminal Capability values must be populated for all transactions |
| APPI-031-12 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | 12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities |
| APPI-032-08 | Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08 | 575 | Appendix I-26 | It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.). |
| APPI-048 | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 586 | Appendix I-37 | This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication. |
| APPI-048-LEN | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 586 | Appendix I-37 | Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID. |
| APPI-048-PP | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 587 | Appendix I-38 | 1 = EMV 3-D Secure Version 2.1 |
| APPI-049-02 | Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02 | 589 | Appendix I-40 | This field is required for all subsequent Credential on File transactions. |
| APPJ-01 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 01 Manual/keyed |
| APPJ-02 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 02 Magnetic stripe read with partial track data |
| APPJ-05 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 05 Integrated circuit card (ICC) read – CVV data reliable (used for EMV chip read) |
| APPJ-07 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 07 Contactless chip read (used for contactless chip) |
| APPJ-10 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 10 Credential on file |
| APPJ-90 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 90 Magnetic stripe read with full track data |
| APPJ-PERSIST | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction |
| APPJ-PIN1 | Appendix J. Valid Point-of-Service Entry Mode Codes | 639 | Appendix J-2 | 1 PIN entry capability (including both EMV Contact and Contactless) |
| APPQ | Appendix Q. Valid National Point-of-Service Condition Codes | 690 | Appendix Q-1 | This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030 The National Point-of-Service Condition Codes data element contains the following elements: • Terminal Class (Position Nos. 1–3) • Presentation Type (Position Nos. 4–7) • Security Condition (Position No. 8) • Terminal Type (Position Nos. 9–10) |
| APPQ-010-04 | Appendix Q. Valid National Point-of-Service Condition Codes | 693 | Appendix Q-4 | 010 04 Attended on premise Card acceptor-operated ECR |
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
| EL111 | Element 111 Variable Information Indicator (Chapter 13) | 422 | 13-82 | Fixed length of 3 alphanumeric characters |
| EL111-048 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 048 MasterCard Authentication Data |
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
| EL39 | Element 39 Firmware Version (Chapter 13) | 384 | 13-44 | Fixed length of eight alphanumeric characters |
| EL42 | Element 42 Grand Total (Chapter 13) | 386 | 13-46 | Fixed length of eight digits with two assumed decimal places |
| EL43 | Element 43 Hardware Version (Chapter 13) | 386 | 13-46 | Fixed length of four or eight alphanumeric characters |
| EL44 | Element 44 Information Byte (Chapter 13) | 386 | 13-46 | 0 Single-message transmission |
| EL5-FMT | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Fixed length of six alphanumeric characters; space-filled |
| EL5-PRE | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization. |
| EL5-REV | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Required on reversals for matching information to the original purchase. |
| EL55 | Element 55 Message Format Version Identifier (Chapter 13) | 391 | 13-51 | Fixed length of six alphanumeric characters |
| EL58 | Element 58 Nonfuel Amount (Chapter 13) | 393 | 13-53 | A maximum length of 7 bytes is allowed for all other card types. |
| EL63 | Element 63 Number of Segments (Chapter 13) | 395 | 13-55 | Variable length of up to two digits |
| EL78 | Element 78 Prompt Code (Chapter 13) | 400 | 13-60 | this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters). |
| EL79 | Element 79 Pump/Lane Number (Chapter 13) | 401 | 13-61 | Representation: Variable length of up to two digits Purpose: Identifies the pump or lane number at the point-of-sale location. |
| EL79-USE | Element 79 Pump/Lane Number (Chapter 13) | 402 | 13-62 | Required on all fuel transactions. |
| EL83-0 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 0 Approved—Purchase/Capture |
| EL83-1 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 1 Approved—Communications Test Declined—All other transactions |
| EL83-2 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 2 Approved—Authorization only |
| EL83-4 | Element 83 Response Code (Chapter 13) | 404 | 13-64 | 4 Approved—Purchase/Capture with AVS |
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
| S10.1.1.2-FOLLOW | Section 10.1.1.2 Track Support | 81 | 10-2 | All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile. |
| S10.1.1.3 | Section 10.1.1.3 Manual Entry | 81 | 10-2 | If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY). |
| S10.1.2-LIST | Section 10.1.2 Supported Transaction Types | 83 | 10-4 | The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal |
| S10.1.2.1-SEQ | Section 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | The Sequence Number must be the Sequence Number contained in the original transaction request. |
| S10.1.2.2 | Section 10.1.2.2 Authorization Only Reversal Transaction | 85 | 10-6 | Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. |
| S10.1.2.2-SEQ | Section 10.1.2.2 Authorization Only Reversal Transaction | 85 | 10-6 | Element No.86 (Sequence Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. |
| S10.1.3-REQ | Section 10.1.3 Address Verification Service | 86 | 10-7 | The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment |
| S10.1.8.2 | Section 10.1.8.2 Track Support Requirements | 94 | 10-15 | The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). |
| S10.13.2.4.2-MC | Section 10.13.2.4.2 Stored Credential Use Cases | 144 | 10-65 | Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type. |
| S10.14.2.4-REV | Section 10.14.2.4 Supported Transaction Types (EMV) | 161 | 10-82 | EMV data is not required on Reversal transactions. |
| S10.14.3-ONLINE | Section 10.14.3 EMV PIN (Personal Identification Number) Processing | 162 | 10-83 | Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer. |
| S11.1-101 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data. |
| S11.1-123 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions. |
| S11.1-143 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager. |
| S11.1-153 | Section 11.1 Financial Transactions (Data Section No. 3) | 169 | 11-6 | 153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction. |
| S11.1-DS1 | Section 11.1 Financial Transactions (Data Section No. 1) | 167 | 11-4 | All Financial Transaction Requests contain Element Nos. 55 and 63. |
| S11.4-TOTALS | Section 11.4.1.1 Totals Request | 178 | 11-15 | Note: Totals Request messages do not contain Data Section No. 2 [Standard Message Data Segment (Data Segment No. 100)]. |
| S11.8-EMV | Section 11.8.1 EMV Financial Transaction Request | 205 | 11-42 | The following table illustrates the layout of an EMV Financial Transaction Request: |
| S12.1-FS | Section 12.1 Standard Message Data Segment | 223 | 12-6 | When a field is not populated, still send the Field Separator. |
| S12.1-LEN | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.1-ROW3 | Section 12.1 Standard Message Data Segment | 223 | 12-6 | 3 44 Information Byte 1 R |
| S12.1-TYPE | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Fixed value: 100 |
| S12.10-FS | Section 12.10 Variable Information Data Segment | 246 | 12-29 | A Field Separator follows the final Variable Information Section. |
| S12.10-LEN | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Identifies the length of Variable Information (Element No. 113). |
| S12.10-TYPE | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Fixed value: 111 |
| S12.19-REQ | Section 12.19 NFC Payment Tokenization Data Segment | 267 | 12-50 | It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data. |
| S12.20-ALWAYS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | It always appears in Data Section No. 3 of an EMV Financial Transaction Request. |
| S12.20-FS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | A Field Separator follows the final EMV Additional Information Section. |
| S12.20-LEN | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.20-TYPE | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Fixed value: 130 |
| S12.3-ROW3 | Section 12.3 Product Code Data Segment | 230 | 12-13 | 3 87 Service Level 1 R Identifies the type of sale. |
| S12.30-102 | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | A Product Code Data Segment (Data Segment No. 102) must be present in the transaction. |
| S12.30-CAN | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax). |
| S12.30-ORDER | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments. |
| S12.31-SCOPE | Section 12.31 Enhanced Fleet Request Segment | 289 | 12-72 | The Enhanced Fleet Request Segment contains fleet data that applies to the enhanced fleet offerings such as Wex OTR, Comdata, Voyager EMV, and Visa Fleet 2.0. |
| S12.38-FS | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | There is a Field Separator between Field Nos. 1 and 2. |
| S12.38-LEN | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | 2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length |
| S12.38-NOFS | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | Field Separators are not there between the elements in Network Token Data Layouts. |
| S12.38-T004 | Section 12.38 Network Token Data Request Segment | 312 | 12-95 | Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN |
| S12.38-T006 | Section 12.38 Network Token Data Request Segment | 312 | 12-95 | This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation). |
| S12.6-CAT | Section 12.6 Totals Data Segment | 237 | 12-20 | Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type. |
| S12.6-FS | Section 12.6 Totals Data Segment | 235 | 12-18 | Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14. |
| S12.6-NOFS | Section 12.6 Totals Data Segment | 235 | 12-18 | Field Nos. 15–17 are not separated by Field Separators. |
| S12.6-PROMPT | Section 12.6 Totals Data Segment | 235 | 12-18 | Identifies the format type for the requested totals. Fixed value: 990 |
| S12.6-TYPE | Section 12.6 Totals Data Segment | 235 | 12-18 | Identifies the type of data segment being formatted. Fixed value: 105 |
| S12.7-FS | Section 12.7 Loyalty Card Data Segment | 238 | 12-21 | All fields are separated by Field Separators, with a Field Separator following Field No. 13. |
| S12.7-MAX | Section 12.7 Loyalty Card Data Segment | 238 | 12-21 | It has a maximum length of 142 alphanumeric characters |
| S12.7-ROW12 | Section 12.7 Loyalty Card Data Segment | 239 | 12-22 | 12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card. |
| S2.3-TOR | Section 2.3 Unsuccessful Financial Transactions | 39 | 2-4 | A time-out reversal (TOR) is created for an unsuccessful financial transaction. |
| S2.3.1-DEVICE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. |
| S2.3.1-LATE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored. |
| S2.3.1-MATCH | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response. |
| S2.3.1-QUEUE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR |
| S2.3.1-TIMEOUT | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The customer’s 30-second response interval expires without a response from BUYPASS. |
| S2.3.1-TOR-SEQ | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. |
| S9.2-GT | Section 9.2 Local Totals | 72 | 9-8 | The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only |
| S9.2-PUR | Section 9.2 Local Totals | 72 | 9-8 | A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total. |
| S9.2-RET | Section 9.2 Local Totals | 72 | 9-8 | A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total. |
| S9.2-REV | Section 9.2 Local Totals | 72 | 9-8 | A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total. |

Regenerate: `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output" --only TC-076120,TC-076166,TC-076167,TC-076168,TC-076658,TC-075618,TC-075654,TC-075699,TC-075657,TC-076586,TC-075650,TC-075755,TC-075656,TC-075757,TC-075685,TC-075670,TC-077181,TC-077186,TC-077193,TC-077219,TC-076596,TC-076613,TC-075751,TC-075667,TC-076423,TC-077185,TC-077206,TC-077218,TC-076725,TC-076723,TC-075601,TC-075810,TC-075855,TC-076597,TC-076780,TC-077179,TC-077203 --guides TC-076120,TC-076166,TC-076167,TC-076168,TC-076658 --sheet MasterCard_Credit_Defect_Demo_Sheet.md --title "MasterCard Credit run set 20-11: defect demo sheet for the BA review" --scope "This sheet repeats the BA analysis of the 16-11 run on the new run set `Dhan_Singh_run_set_20-11` (main MasterCard Credit run, built 19 Nov 2026). It shows what happened to every defect the BA logged for the 16-11 run, every defect the validator finds in the new run with all affected test cases, and the actual and expected test data of 37 representative test cases. Every finding was produced by the ATL105 validator from the test case definitions and their test data files, and every ATL105 text quoted here was found word for word on its PDF page." --groups 20-11 --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_main.md --outro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\outro_main.md`.

