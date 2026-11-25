# ATL105 validation of 43 test cases of the run and their 46 test data files

**Run:** [mastercard_credit_new_16_11](../mastercard_credit_new_16_11/) (MasterCard Credit run of 16 Nov 2026 (plan v12)), build `buypass-atl105-mastercard-credit-generated-923e8c2eac`  
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). The attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the 2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  
**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.

ATL105 result of each test case, every segment and field: [TC-039912](TC-039912.md), [TC-039913](TC-039913.md), [TC-039914](TC-039914.md), [TC-039915](TC-039915.md), [TC-039916](TC-039916.md), [TC-040080](TC-040080.md), [TC-040194](TC-040194.md), [TC-040308](TC-040308.md), [TC-040512](TC-040512.md), [TC-040623](TC-040623.md), [TC-040737](TC-040737.md), [TC-040856](TC-040856.md), [TC-040974](TC-040974.md), [TC-041219](TC-041219.md), [TC-041315](TC-041315.md), [TC-041606](TC-041606.md), [TC-040116](TC-040116.md), [TC-040230](TC-040230.md), [TC-040346](TC-040346.md), [TC-040547](TC-040547.md), [TC-040658](TC-040658.md), [TC-040776](TC-040776.md), [TC-040893](TC-040893.md), [TC-041009](TC-041009.md), [TC-040126](TC-040126.md), [TC-040240](TC-040240.md), [TC-040356](TC-040356.md), [TC-040557](TC-040557.md), [TC-040668](TC-040668.md), [TC-040786](TC-040786.md), [TC-040903](TC-040903.md), [TC-041019](TC-041019.md), [TC-040140](TC-040140.md), [TC-040254](TC-040254.md), [TC-040396](TC-040396.md), [TC-040571](TC-040571.md), [TC-040683](TC-040683.md), [TC-040800](TC-040800.md), [TC-040919](TC-040919.md), [TC-041032](TC-041032.md), [TC-041083](TC-041083.md), [TC-041101](TC-041101.md), [TC-038607](TC-038607.md). Detailed step-by-step guides for 5 test cases: [TC-039912](../Manual%20Validation/TC-039912-Manual-Validation.md), [TC-039913](../Manual%20Validation/TC-039913-Manual-Validation.md), [TC-039914](../Manual%20Validation/TC-039914-Manual-Validation.md), [TC-039915](../Manual%20Validation/TC-039915-Manual-Validation.md), [TC-039916](../Manual%20Validation/TC-039916-Manual-Validation.md).

## 1. Result at a glance

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 38 | At least one test data or test case defect that must be fixed |
| **BLOCKED** | 5 | No defect, but a client value or a run-time value is still missing |
| **REVIEW** | 0 | No defect; a citation must be corrected or a point confirmed with BUYPASS |
| **PASS** | 0 | Nothing found |
| **DEVICE** | 0 | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |
| Total | 43 | 43 send a message to BUYPASS (46 test data files); 0 are device-only or have no test data |

Findings by kind (a test case can have several):

| Kind | Findings | Test cases | Effect on the result |
|---|---:|---:|---|
| Test data defect | 24 | 8 | FAIL |
| Test case defect | 30 | 30 | FAIL |
| Test case issue to correct | 36 | 35 | REVIEW |
| Citation issue | 27 | 22 | REVIEW |
| Awaiting client input / run-time value | 81 | 43 | BLOCKED |
| To confirm with BUYPASS / SME | 21 | 18 | REVIEW |
| Intended violation of a negative test (correct) | 3 | 3 | none |
| AI validator rule not applicable - value checked and correct | 2 | 2 | none |
| Information | 13 | 13 | none |

Data segments in the test data: 100 (46 files), 102 (2 files), 111 (46 files), 130 (11 files), 153 (1 files).

## 2. How every test case and test data file was checked

1. **Trained AI validator** (`scripts/validate-td-against-atl105-spec.py`, the rules built in the earlier MasterCard, Interlink and Maestro reviews): Data Section 1, all 17 fields of Segment 100, Segment 111 with Tables 005/031/030 and the Credential on File tables, Segment 130 (EMV) with its TLV chip data, Segment 105 (Totals), links between steps (Sequence Number, card, terminal, entry mode), expected Response Codes, and every ATL105 quote in the test case (`spec_basis`) against the page it cites.
2. **Extended checks for this run** (`scripts/validate-run-and-build-manual-validation.py`): the other data segments (101, 102, 104, 108, 123, 145, 153 - Segment Type, Segment Length with each segment's Field Separator rule, Chapter 13 format of every field, TLV structure), every Variable Information table ID, values awaited from the client, each test case's own `rule_checks`, test data vs test case value fidelity for all segments, identical requests across all test cases, Sequence Number uniqueness per terminal, Time-out Reversal flows, negative-test premises and conditional fields.
3. **Classification**: every finding was sorted into the kinds in section 1. Findings of negative tests that are the rule the test deliberately breaks are listed as *intended* (correct). Where the trained rule did not fit a new flow of this run, the value was checked against ATL105 and the finding listed as *AI rule not applicable* (section 9) with the reason.
4. Every ATL105 text quoted in this report (listed in section 11) was found word for word on the stated page when this report was generated.

## 3. Defects in the test data (must be fixed)

### 3.1 D-02 - Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply

**8 test cases, 24 findings, 8 test data files** - kind: Test data defect

Rules: `FIELD_5` (8), `X_PIN_BLOCK_FORMAT` (8), `X_OPTIONAL_FIELD_CONDITION` (8).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- Section 12.1 Standard Message Data Segment, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction."

What is wrong (one example per kind of problem):

- `FIELD_5` (TC-040126) - 'J38FQA' is an Approval Number on an original Authorization Only (Transaction Type 3), which asks for a new approval. ATL105 uses Element 5 to match a follow-on to its original (reversals) and says that in a Purchase Request it makes the purchase preauthorized; it does not say how BUYPASS treats an Authorization Only that carries one, so the request contradicts the scenario it is meant to test.
- `X_PIN_BLOCK_FORMAT` (TC-040126) - Element 33 carries a 16- to 20-byte Key Serial Number plus a 16-byte PIN block (32-36 characters); '16' has 2 characters.
- `X_OPTIONAL_FIELD_CONDITION` (TC-040126) - Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant.
- `X_OPTIONAL_FIELD_CONDITION` (TC-040903) - Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant; Fuel Purchase Amount '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040126](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | 1 | `FIELD_5` | [TD-TC-040126.json](../mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L23) | `J38FQA` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040126](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040126.json](../mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040126](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040126.json](../mastercard_credit_new_16_11/test_data/TD-TC-040126.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-040903](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138) (BR1_TS17_TC70) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040903.json](../mastercard_credit_new_16_11/test_data/TD-TC-040903.json) | `Local Date and Local Time '0615251430' o…-0100 (non-p` | Send conditional fields only when their condition holds. |
| [TC-040240](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | 1 | `FIELD_5` | [TD-TC-040240.json](../mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L23) | `6FH3NZ` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040240](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040240.json](../mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040240](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040240.json](../mastercard_credit_new_16_11/test_data/TD-TC-040240.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-040356](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) (BR1_TS13_TC65) | 1 | `FIELD_5` | [TD-TC-040356.json](../mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L23) | `C9HTU3` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040356](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) (BR1_TS13_TC65) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040356.json](../mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| [TC-040356](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) (BR1_TS13_TC65) | 1 | `X_OPTIONAL_FIELD_CONDITION` | [TD-TC-040356.json](../mastercard_credit_new_16_11/test_data/TD-TC-040356.json) | `Local Date and Local Time '0615251430' o…um) merchant` | Send conditional fields only when their condition holds. |
| [TC-040557](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L792) (BR1_TS14_TC61) | 1 | `FIELD_5` | [TD-TC-040557.json](../mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L23) | `EMUBI3` | (empty) - an original Authorization Only carries no Approval Number |
| [TC-040557](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L792) (BR1_TS14_TC61) | 1 | `X_PIN_BLOCK_FORMAT` | [TD-TC-040557.json](../mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L16) | `16` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| … | | 12 more in the sheet (Findings, filter on D-02) | | | |

Scenarios: BR1_TS11 (3), BR1_TS12 (3), BR1_TS13 (3), BR1_TS14 (3), BR1_TS15 (3), BR1_TS16 (3), BR1_TS17 (3), BR1_TS18 (3)

**How to check by hand:** Open the test data file and read ApprovalNumber, EncryptedPINBlockData, LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount, CashAmount; compare with the ATL105 rule of each field.

**Fix:** Send only the conditional fields whose condition holds: no Approval Number / Local Date and Time on a new authorization, a real DUKPT KSN + PIN block (32-36 characters) or nothing in Element 33, no fuel or pump data outside petroleum.

## 4. Defects and issues in the test cases (design, status, expected results)

### 4.1 C-02 - Negative test premise contradicts ATL105 (the rule it claims to break does not exist)

**1 test case, 1 finding** - kind: Test case defect

Rules: `X_NEG_PREMISE` (1).

ATL105:

- Section 10.1.2 Supported Transaction Types, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"
- Section 10.14.3 EMV PIN (Personal Identification Number) Processing, PDF p.162 (printed 10-83): "Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."
- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "3 44 Information Byte 1 R"
- Section 12.3 Product Code Data Segment, PDF p.230 (printed 12-13): "3 87 Service Level 1 R Identifies the type of sale."
- Section 12.7 Loyalty Card Data Segment, PDF p.239 (printed 12-22): "12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card."
- Section 11.2.1 Loyalty Card Transactions - Request, PDF p.175 (printed 11-12): "The Loyalty Card Transaction Request contains Segment Nos. 108 and 114."
- Section 11.2.1 Loyalty Card Transactions - Request, PDF p.175 (printed 11-12): "Contains the loyalty card data required for all loyalty card transactions."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9."

What is wrong (one example per kind of problem):

- `X_NEG_PREMISE` (TC-038607) - PDF p.15 (front matter, no printed page number) does not mention Loyalty Card Data Segment at all. The test case's own citation for p.15 (spec_basis, from the ATL105 TransArmor specification, not the ATL105 Message Format Specifications) reads "Expiration Date must be included in Card Discretionary Block Data (Element No." - it says nothing about Loyalty Card Data Segment. ATL105 makes Segment 108 required only in the Loyalty Card Transaction Request (Section 11.2.1: it 'Contains the loyalty card data required for all loyalty card transactions'); the Financial Transaction Request layout in Section 11.1 does not list it. This MasterCard Credit transaction carries no loyalty card, so leaving Segment 108 out is correct and BUYPASS has no reason to decline.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-038607](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) (BR1_TS15_TC1) | - | `X_NEG_PREMISE` | test case test_objective | `Loyalty Card Data Segment (segment 108) … requires it` | Remove the test or rewrite it around a rule ATL105 states. |

Scenarios: BR1_TS15 (1)

**How to check by hand:** Read the test objective, then the cited ATL105 rule: the transaction/field is supported or required.

**Fix:** Remove the test or rewrite it around a rule ATL105 states.

### 4.2 C-03 - Test case marked 'ready' although its Terminal Identifier is a placeholder

**35 test cases, 35 findings** - kind: Test case issue to correct

Rules: `READY_WITH_PLACEHOLDER` (35).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

What is wrong (one example per kind of problem):

- `READY_WITH_PLACEHOLDER` (TC-039912) - The test case status is 'ready', but its Terminal Identifier is still a placeholder.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039912](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) (BR1_TS5_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-039913](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) (BR1_TS5_TC2) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-039914](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) (BR1_TS5_TC3) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-039915](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) (BR1_TS5_TC4) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040308](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC17) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040512](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) (BR1_TS14_TC16) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040623](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) (BR1_TS15_TC17) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040737](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) (BR1_TS16_TC16) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| [TC-040856](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) (BR1_TS17_TC23) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |
| … | | 23 more in the sheet (Findings, filter on C-03) | | | |

Scenarios: BR1_TS5 (5), BR1_TS15 (5), BR1_TS11 (4), BR1_TS12 (4), BR1_TS13 (4), BR1_TS14 (4), BR1_TS16 (4), BR1_TS17 (4), BR1_TS19 (1)

**How to check by hand:** TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.

**Fix:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 4.3 C-05 - Test objective / name does not match the message sent or the expected result

**1 test case, 1 finding** - kind: Test case issue to correct

Rules: `X_OBJECTIVE_MISMATCH` (1).

ATL105:

- Element 78 Prompt Code (Chapter 13), PDF p.400 (printed 13-60): "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters)."

What is wrong (one example per kind of problem):

- `X_OBJECTIVE_MISMATCH` (TC-039916) - The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type '0' (POS Purchase/Capture) and the name says Purchase/Capture.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | - | `X_OBJECTIVE_MISMATCH` | test case test_objective | `Decision table: Authorization Only with …ition holds)` | Correct the objective to POS Purchase/Capture. |

Scenarios: BR1_TS5 (1)

**How to check by hand:** Compare test_objective and the name with the Prompt Code Transaction Type in the test data and with the expected Response Code.

**Fix:** Correct the objective / name text.

### 4.4 C-10 - Positive test does not send the condition it claims (table, segment or field value)

**19 test cases, 19 findings, 19 test data files** - kind: Test case defect

Rules: `X_CONDITION_NOT_SENT` (19).

ATL105:

- Element 111 Variable Information Indicator (Chapter 13), PDF p.422 (printed 13-82): "Fixed length of 3 alphanumeric characters"
- Element 111 Variable Information Indicator (Chapter 13), PDF p.424 (printed 13-84): "048 MasterCard Authentication Data"
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication."
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID."
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.587 (printed Appendix I-38): "1 = EMV 3-D Secure Version 2.1"
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "A Product Code Data Segment (Data Segment No. 102) must be present in the transaction."
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments."
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)."
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager."

What is wrong (one example per kind of problem):

- `X_CONDITION_NOT_SENT` (TC-040080) - The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040974) - The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `pending_sme`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-041219) - The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `needs_client_input`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.
- `X_CONDITION_NOT_SENT` (TC-040116) - The objective "Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.
- `X_CONDITION_NOT_SENT` (TC-041009) - The objective "Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `pending_sme`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040080.json](../mastercard_credit_new_16_11/test_data/TD-TC-040080.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040974](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) (BR1_TS18_TC16) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040974.json](../mastercard_credit_new_16_11/test_data/TD-TC-040974.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-041219.json](../mastercard_credit_new_16_11/test_data/TD-TC-041219.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040116](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) (BR1_TS11_TC57) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040116.json](../mastercard_credit_new_16_11/test_data/TD-TC-040116.json) | `Tax Type 1: not in the test data; segmen…00, 111, 130` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the |
| [TC-041009](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1244) (BR1_TS18_TC51) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-041009.json](../mastercard_credit_new_16_11/test_data/TD-TC-041009.json) | `Tax Type 1: not in the test data; segments sent: 100, 111` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040194.json](../mastercard_credit_new_16_11/test_data/TD-TC-040194.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040308](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC17) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040308.json](../mastercard_credit_new_16_11/test_data/TD-TC-040308.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040512](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) (BR1_TS14_TC16) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040512.json](../mastercard_credit_new_16_11/test_data/TD-TC-040512.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040623](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) (BR1_TS15_TC17) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040623.json](../mastercard_credit_new_16_11/test_data/TD-TC-040623.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040737](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) (BR1_TS16_TC16) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040737.json](../mastercard_credit_new_16_11/test_data/TD-TC-040737.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-040856](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) (BR1_TS17_TC23) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-040856.json](../mastercard_credit_new_16_11/test_data/TD-TC-040856.json) | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | 1 | `X_CONDITION_NOT_SENT` | [TD-TC-041315.json](../mastercard_credit_new_16_11/test_data/TD-TC-041315.json) | `Segment 111 tables sent: 005, 030, 031` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 04 |
| … | | 7 more in the sheet (Findings, filter on C-10) | | | |

Scenarios: BR1_TS11 (2), BR1_TS12 (2), BR1_TS13 (2), BR1_TS14 (2), BR1_TS15 (2), BR1_TS16 (2), BR1_TS17 (2), BR1_TS18 (2), BR1_TS54 (1), BR1_TS154 (1), BR1_TS173 (1)

**How to check by hand:** Read test_objective and the name, then open the test data: the claimed table, segment or value is not in the request, and open_items / awaiting_inputs do not ask for it.

**Fix:** Send the claimed data with the segments it depends on, or keep the test out of 'ready' with the missing input named, or re-scope / withdraw it when the condition does not apply.

### 4.5 C-11 - Positive test expects response information that its request does not ask for

**10 test cases, 10 findings, 10 test data files** - kind: Test case defect

Rules: `X_RESPONSE_NOT_INITIATED` (10).

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

What is wrong (one example per kind of problem):

- `X_RESPONSE_NOT_INITIATED` (TC-040140) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-040919) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-041032) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `pending_sme`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.
- `X_RESPONSE_NOT_INITIATED` (TC-041101) - tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `pending_sme`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031, 032, 049, 056 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040140](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) (BR1_TS11_TC81) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040140.json](../mastercard_credit_new_16_11/test_data/TD-TC-040140.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040919](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154) (BR1_TS17_TC86) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040919.json](../mastercard_credit_new_16_11/test_data/TD-TC-040919.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-041032](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1267) (BR1_TS18_TC74) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-041032.json](../mastercard_credit_new_16_11/test_data/TD-TC-041032.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-041101](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1336) (BR1_TS20_TC9) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-041101.json](../mastercard_credit_new_16_11/test_data/TD-TC-041101.json) | `Segment 111 tables sent: 005, 030, 031, … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040254](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L489) (BR1_TS12_TC77) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040254.json](../mastercard_credit_new_16_11/test_data/TD-TC-040254.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040396](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L631) (BR1_TS13_TC105) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040396.json](../mastercard_credit_new_16_11/test_data/TD-TC-040396.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040571](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L806) (BR1_TS14_TC75) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040571.json](../mastercard_credit_new_16_11/test_data/TD-TC-040571.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040683](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L918) (BR1_TS15_TC77) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040683.json](../mastercard_credit_new_16_11/test_data/TD-TC-040683.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-040800](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1035) (BR1_TS16_TC79) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-040800.json](../mastercard_credit_new_16_11/test_data/TD-TC-040800.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |
| [TC-041083](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1318) (BR1_TS19_TC15) | 1 | `X_RESPONSE_NOT_INITIATED` | [TD-TC-041083.json](../mastercard_credit_new_16_11/test_data/TD-TC-041083.json) | `Segment 111 tables sent: 005, 030, 031; … Information` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111  |

Scenarios: BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS19 (1), BR1_TS20 (1)

**How to check by hand:** Read tx_legs[i].response_tables (the Segment 112 table the test expects back), then open the request: the Segment 111 table that initiates it (003 AVS, 004 card verification) is not sent.

**Fix:** Send the initiating table with the client's test card data and assert the result, or drop the response expectation / re-scope the test.

## 5. Citation issues (the ATL105 text quoted in the test cases)

### 5.1 Q-01 - Quote not found in ATL105 or missing (generator note or other text cited as ATL105)

**1 test case, 1 finding** - kind: Citation issue

Rules: `QUOTE_NOT_IN_SPEC` (1).

ATL105:

- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `QUOTE_NOT_IN_SPEC` (TC-039914) - The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039914](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) (BR1_TS5_TC3) | - | `QUOTE_NOT_IN_SPEC` | test case spec_basis | `Plan v7 W2: a follow-on of the same tran…r matching).` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |

Scenarios: BR1_TS5 (1)

**How to check by hand:** Search the quoted text on the cited page.

**Fix:** Quote the ATL105 sentence word for word.

### 5.2 Q-02 - Quote is on a different page than cited

**13 test cases, 13 findings** - kind: Citation issue

Rules: `QUOTE_PAGE_DRIFT` (11), `QUOTE_WRONG_PAGE` (2).

What is wrong (one example per kind of problem):

- `QUOTE_WRONG_PAGE` (TC-039913) - The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- `QUOTE_WRONG_PAGE` (TC-039915) - The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).
- `QUOTE_PAGE_DRIFT` (TC-040080) - The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039913](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) (BR1_TS5_TC2) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `The Sequence Number must be the Sequence…transaction.` | Cite p.84 instead of p.402. |
| [TC-039915](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) (BR1_TS5_TC4) | - | `QUOTE_WRONG_PAGE` | test case spec_basis | `A TOR must be sent from the same device … in the TOR.` | Cite p.40 instead of p.38. |
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-041606](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) (BR1_TS173_TC15) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-040116](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) (BR1_TS11_TC57) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-040230](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465) (BR1_TS12_TC53) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-040126](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) (BR1_TS11_TC67) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-040240](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) (BR1_TS12_TC63) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-040140](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) (BR1_TS11_TC81) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| … | | 1 more in the sheet (Findings, filter on Q-02) | | | |

Scenarios: BR1_TS11 (4), BR1_TS12 (4), BR1_TS5 (2), BR1_TS54 (1), BR1_TS154 (1), BR1_TS173 (1)

**How to check by hand:** Find the quote: it is on another PDF page.

**Fix:** Correct the page number.

### 5.3 Q-03 - Quote is not word-for-word

**11 test cases, 11 findings** - kind: Citation issue

Rules: `QUOTE_PARAPHRASED` (11).

What is wrong (one example per kind of problem):

- `QUOTE_PARAPHRASED` (TC-040080) - The test case quotes "Table 048 MasterCard Authentication Data" for p.586. Not word-for-word: all its words are on the cited page, but not in this form.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040308](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC17) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040512](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) (BR1_TS14_TC16) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040623](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) (BR1_TS15_TC17) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040737](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) (BR1_TS16_TC16) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040856](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) (BR1_TS17_TC23) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-040974](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) (BR1_TS18_TC16) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |
| [TC-041606](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) (BR1_TS173_TC15) | - | `QUOTE_PARAPHRASED` | test case spec_basis | `Table 048 MasterCard Authentication Data` | Quote the text exactly as it appears on the page. |

Scenarios: BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS54 (1), BR1_TS154 (1), BR1_TS173 (1)

**How to check by hand:** Compare the quote with the page text.

**Fix:** Quote word for word.

### 5.4 Q-04 - Citation or device checkpoint from an unrelated section or document

**2 test cases, 2 findings** - kind: Citation issue

Rules: `X_CHECKPOINT_CONTEXT` (1), `X_BASIS_CONTEXT` (1).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)."

What is wrong (one example per kind of problem):

- `X_CHECKPOINT_CONTEXT` (TC-039912) - 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (GENERAL).
- `X_BASIS_CONTEXT` (TC-039916) - The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039912](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) (BR1_TS5_TC1) | - | `X_CHECKPOINT_CONTEXT` | test case device_checkpoints | `MUST: When pumping is complete, the devi…the device h` | Drop checkpoints that do not apply to this merchant. |
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | - | `X_BASIS_CONTEXT` | test case spec_basis (p.312) | `This data is applicable only to subseque…Cancellation` | Cite the Segment 153 layout (p.310) as the basis. |

Scenarios: BR1_TS5 (2)

**How to check by hand:** Read the cited text: it covers another transaction, card or industry.

**Fix:** Cite the rule that applies.

## 6. Values awaiting client input or run-time values (BLOCKED, not defects)

### 6.1 I-01 - Terminal Identifier is a generated placeholder

**43 test cases, 43 findings** - kind: Awaiting client input / run-time value

Rules: `TERMINAL_PLACEHOLDER` (43).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Northwest Territories NT 67"
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "Armed Forces Americas AA 57"
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Michigan MI 26"

What is wrong (one example per kind of problem):

- `TERMINAL_PLACEHOLDER` (TC-039912) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- `TERMINAL_PLACEHOLDER` (TC-041219) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'QU67070249149717001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '67' = Northwest Territories (NT), Canada.
- `TERMINAL_PLACEHOLDER` (TC-041315) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'KC57457075622174001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '57' = Armed Forces Americas (AA), US military.
- `TERMINAL_PLACEHOLDER` (TC-041606) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'VZ26598162666071001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '26' = Michigan (MI), United States.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039912](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) (BR1_TS5_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `QU67070249149717001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `KC57457075622174001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-041606](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) (BR1_TS173_TC15) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `VZ26598162666071001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-039913](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) (BR1_TS5_TC2) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-039914](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) (BR1_TS5_TC3) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-039915](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) (BR1_TS5_TC4) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-040308](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC17) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-040512](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) (BR1_TS14_TC16) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX72948216050257001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| … | | 31 more in the sheet (Findings, filter on I-01) | | | |

Scenarios: BR1_TS5 (5), BR1_TS15 (5), BR1_TS11 (4), BR1_TS12 (4), BR1_TS13 (4), BR1_TS14 (4), BR1_TS16 (4), BR1_TS17 (4), BR1_TS18 (4), BR1_TS54 (1), BR1_TS154 (1), BR1_TS173 (1), BR1_TS19 (1), BR1_TS20 (1)

**How to check by hand:** merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.

**Fix:** Client / BUYPASS to supply the real Terminal Identifier.

### 6.2 I-02 - Value filled in at run time from the previous response (expected for follow-on steps)

**2 test cases, 2 findings, 2 test data files** - kind: Awaiting client input / run-time value

Rules: `RUNTIME_PLACEHOLDER` (2).

ATL105:

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled"

What is wrong (one example per kind of problem):

- `RUNTIME_PLACEHOLDER` (TC-039913) - The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039913](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) (BR1_TS5_TC2) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039913.step2.reversal.json](../mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |
| [TC-039914](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) (BR1_TS5_TC3) | 2 | `RUNTIME_PLACEHOLDER` | [TD-TC-039914.step2.reversal.json](../mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L23) | `<ApprovalNumber from step 1 response>` | Fill in at run time: Plan v7 W2: Element 5 (p.368) 'Required on reversals for matching information to the original purchase' - the Approval Number returned in the original transaction's RESPONSE (runt |

Scenarios: BR1_TS5 (2)

**How to check by hand:** The field holds text in angle brackets, e.g. <ApprovalNumber from step 1 response>.

**Fix:** The harness substitutes the value from the previous response and recalculates the Segment Length.

### 6.3 I-04 - EMV chip data is generated, not from a card / simulator

**11 test cases, 35 findings** - kind: Awaiting client input / run-time value

Rules: `EMV_CA_CHECKSUM_SAMPLE` (11), `EMV_PLACEHOLDER_CRYPTO` (11), `EMV_TVR_CVM_MISMATCH` (9), `EMV_CONTACTLESS_TEMPLATE` (4).

ATL105:

- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."
- Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags."

What is wrong (one example per kind of problem):

- `EMV_CA_CHECKSUM_SAMPLE` (TC-040080) - The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- `EMV_TVR_CVM_MISMATCH` (TC-040080) - TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.
- `EMV_PLACEHOLDER_CRYPTO` (TC-040080) - ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- `EMV_CONTACTLESS_TEMPLATE` (TC-040194) - The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=D…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `EMV_CONTACTLESS_TEMPLATE` | EMV Request Data Segment -> EMVChipData (every EMV request) | `22 tags, CVM 9F34 = 5E0302` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=2…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=3…5=8000048000` | Produce the chip data with a card simulator / HSM before execution. |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | - | `EMV_TVR_CVM_MISMATCH` | EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request) | `95=8000048000, 9F34=5E0302` | Produce the TVR and the CVM Results together with a card simulator. |
| … | | 23 more in the sheet (Findings, filter on I-04) | | | |

Scenarios: BR1_TS12 (15), BR1_TS11 (11), BR1_TS54 (3), BR1_TS154 (3), BR1_TS173 (3)

**How to check by hand:** See the EMV finding text in the guide / sheet.

**Fix:** Use chip data captured from the client's test card or card simulator.

### 6.4 I-06 - Optional segment content is synthesized - real client data needed before an approval can be expected

**1 test case, 1 finding, 1 test data file** - kind: Awaiting client input / run-time value

Rules: `X_SYNTHESIZED_CONTENT` (1).

ATL105:

- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."

What is wrong (one example per kind of problem):

- `X_SYNTHESIZED_CONTENT` (TC-039916) - The test case generated the Segment 153 values itself (spec_tlv_composite); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's network-token entitlement and a real provisioned network token.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | 1 | `X_SYNTHESIZED_CONTENT` | [TD-TC-039916.json](../mastercard_credit_new_16_11/test_data/TD-TC-039916.json) | `spec_tlv_composite` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |

Scenarios: BR1_TS5 (1)

**How to check by hand:** In test_cases.jsonl the segment fields have method format_synthesis / spec_tlv_composite.

**Fix:** Client to supply the data listed in the finding; keep the test case out of 'ready' until then.

## 7. Points to confirm with BUYPASS / SME

### 7.1 C-06 - Time-out Reversal sent after a step that is expected to be answered

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_TOR_AFTER_RESPONSE` (1).

ATL105:

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR"

What is wrong (one example per kind of problem):

- `X_TOR_AFTER_RESPONSE` (TC-039915) - ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0, so the device would not send a TOR.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039915](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) (BR1_TS5_TC4) | 2 | `X_TOR_AFTER_RESPONSE` | test case tx_legs[0].expect / tx_legs[1] | `step 1 expects 0` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). |

Scenarios: BR1_TS5 (1)

**How to check by hand:** Read tx_legs: the original step expects a Response Code and has no 'no response' harness directive, yet the next step is a Time-out Reversal.

**Fix:** Make the original step a fault-injected 'no response' (or a late response the harness ignores).

### 7.2 C-07 - 'Different terminal' negative uses a generated terminal that is not set up at BUYPASS

**2 test cases, 2 findings, 2 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_NEG_RANDOM_TERMINAL` (2).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

What is wrong (one example per kind of problem):

- `X_NEG_RANDOM_TERMINAL` (TC-039914) - Step 2 uses the generated Terminal Identifier 'VS24671841985066391107' (method deliberate_violation_missing_carry). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039914](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) (BR1_TS5_TC3) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039914.step2.reversal.json](../mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) | `VS24671841985066391107` | Use a second real terminal of the same merchant. |
| [TC-039915](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) (BR1_TS5_TC4) | 2 | `X_NEG_RANDOM_TERMINAL` | [TD-TC-039915.step2.timeout-reversal.json](../mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L9) | `QL66203054504417704598` | Use a second real terminal of the same merchant. |

Scenarios: BR1_TS5 (2)

**How to check by hand:** Read the follow-on step's Terminal Identifier: method deliberate_violation_missing_carry, a generated value.

**Fix:** Use a second real terminal of the same merchant so the decline proves the same-device rule.

### 7.3 C-08 - Same request as another test case (difference only on the device / response side)

**11 test cases, 11 findings, 11 test data files** - kind: To confirm with BUYPASS / SME

Rules: `X_SAME_REQUEST` (11).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST` (TC-040080) - Apart from the Sequence Number the message is identical to TC-040065 (all segments compared). TC-040065 expects 2; this test expects 2. What makes the two tests different is not in the message sent to BUYPASS.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040080](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) (BR1_TS11_TC21) | 1 | `X_SAME_REQUEST` | TD-TC-040080.json vs TD-TC-040065.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040194](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) (BR1_TS12_TC17) | 1 | `X_SAME_REQUEST` | TD-TC-040194.json vs TD-TC-040179.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040308](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) (BR1_TS13_TC17) | 1 | `X_SAME_REQUEST` | TD-TC-040308.json vs TD-TC-040293.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040512](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) (BR1_TS14_TC16) | 1 | `X_SAME_REQUEST` | TD-TC-040512.json vs TD-TC-040497.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040623](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) (BR1_TS15_TC17) | 1 | `X_SAME_REQUEST` | TD-TC-040623.json vs TD-TC-040608.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040737](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) (BR1_TS16_TC16) | 1 | `X_SAME_REQUEST` | TD-TC-040737.json vs TD-TC-040722.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040856](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) (BR1_TS17_TC23) | 1 | `X_SAME_REQUEST` | TD-TC-040856.json vs TD-TC-040837.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-040974](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) (BR1_TS18_TC16) | 1 | `X_SAME_REQUEST` | TD-TC-040974.json vs TD-TC-040959.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-041219](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) (BR1_TS54_TC14) | 1 | `X_SAME_REQUEST` | TD-TC-041219.json vs TD-TC-041206.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-041315](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) (BR1_TS154_TC19) | 1 | `X_SAME_REQUEST` | TD-TC-041315.json vs TD-TC-041301.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |
| [TC-041606](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) (BR1_TS173_TC15) | 1 | `X_SAME_REQUEST` | TD-TC-041606.json vs TD-TC-041592.json | `expected 2 vs 2` | Check the difference on the device / in the response; optionally use another card. |

Scenarios: BR1_TS11 (1), BR1_TS12 (1), BR1_TS13 (1), BR1_TS14 (1), BR1_TS15 (1), BR1_TS16 (1), BR1_TS17 (1), BR1_TS18 (1), BR1_TS54 (1), BR1_TS154 (1), BR1_TS173 (1)

**How to check by hand:** Compare both test data files: identical apart from the Sequence Number.

**Fix:** Check the difference on the device or in the response; optionally use another card.

### 7.4 S-01 - Expiry format for stored credentials (entry mode 10) not stated

**5 test cases, 5 findings** - kind: To confirm with BUYPASS / SME

Rules: `COF_EXPIRY_FORMAT` (5).

ATL105:

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."

What is wrong (one example per kind of problem):

- `COF_EXPIRY_FORMAT` (TC-040856) - The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-040856](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) (BR1_TS17_TC23) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1127` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-040893](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1128) (BR1_TS17_TC60) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0328` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-040903](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138) (BR1_TS17_TC70) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1230` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-040919](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154) (BR1_TS17_TC86) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `1127` | Confirm the expiry format for entry mode 10 with BUYPASS. |
| [TC-041101](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1336) (BR1_TS20_TC9) | - | `COF_EXPIRY_FORMAT` | Card Discretionary Block Data (entry mode 10 steps) | `0428` | Confirm the expiry format for entry mode 10 with BUYPASS. |

Scenarios: BR1_TS17 (4), BR1_TS20 (1)

**How to check by hand:** See finding text.

**Fix:** Confirm with BUYPASS.

### 7.5 S-03 - Segment length: ATL105 Field Separator rule of the segment is unclear

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_SEGMENT_LENGTH_UNCLEAR` (1).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "There is a Field Separator between Field Nos. 1 and 2."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "Field Separators are not there between the elements in Network Token Data Layouts."
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13."
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction."

What is wrong (one example per kind of problem):

- `X_SEGMENT_LENGTH_UNCLEAR` (TC-039916) - '051' assumes a Field Separator after every field (051). ATL105 reads differently for this segment - Field Separator only between Fields 1 and 2 (literal reading of p.310): 049; Field Separator after Fields 1 and 2, none after Field 3: 050.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | 1 | `X_SEGMENT_LENGTH_UNCLEAR` | [TD-TC-039916.json](../mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L50) | `051` | Confirm the Field Separator rule of this segment with BUYPASS. |

Scenarios: BR1_TS5 (1)

**How to check by hand:** Recalculate the length under both readings shown in the finding.

**Fix:** Confirm with BUYPASS.

### 7.6 S-04 - Network Token Input Indicator 'Y' while Element 2 carries a clear card number

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_TOKEN_INDICATOR` (1).

ATL105:

- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN"
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

What is wrong (one example per kind of problem):

- `X_TOKEN_INDICATOR` (TC-039916) - Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number 510058…7353 and the token is only in table 001. ATL105 does not say which field carries the token when the indicator is Y.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-039916](../mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) (BR1_TS5_TC5) | 1 | `X_TOKEN_INDICATOR` | [TD-TC-039916.json](../mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L51) | `Y` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |

Scenarios: BR1_TS5 (1)

**How to check by hand:** Parse Network Token Data table 004.

**Fix:** Confirm with BUYPASS which field carries the token when the indicator is Y.

## 8. Negative tests - intended violations (correct)

3 negative test cases break their rule as designed (3 findings). They are not defects; check that each test breaks only that rule.

| Rule | Findings | Example |
|---|---:|---|
| `LINK_TERMINALIDENTIFIER` | 2 | TC-039914 step 2: Step 2: same terminal as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined |
| `LINK_SEQUENCENUMBER` | 1 | TC-039913 step 2: Step 2: same sequence number as the original - check failed - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined |

## 9. AI validator rules that did not apply to this run (value checked - correct)

| Rule | Findings | Test cases | Why the value is correct |
|---|---:|---:|---|
| `EMV_CASHBACK_AMOUNTS` | 2 | 2 | The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization Only. The real problem of this test - optional fields filled without their condition - is D-02. |

Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator did not read segments 101-153; section 2 point 2 now checks them).

Run-wide checks (all 2,129 test cases of the run were validated so that these checks see every test case): 1,112 single-step test cases send the same request as an earlier test case (all segments compared); 1,946 new transactions checked for a unique Sequence Number per terminal, 0 duplicate(s).

## 10. Result per scenario

| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |
|---|---|---|---:|---:|---:|---:|---:|---:|---|
| BR1_TS5 | Purchase/Capture – Manual / Keyed | GENERAL | 5 | 0 | 5 | 0 | 0 | 0 | - |
| BR1_TS11 | Authorization Only – EMV Contact Chip | GENERAL | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS12 | Authorization Only – EMV Contactless Chip | GENERAL | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS13 | Authorization Only – Magnetic Stripe | GENERAL | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS14 | Authorization Only – Contactless Magnetic Stripe | GENERAL | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS15 | Authorization Only – Manual / Keyed | GENERAL | 5 | 5 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS16 | Authorization Only – Chip Fallback | GENERAL | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS17 | Authorization Only – Credential on File | CARD_NOT_PRESENT-0100 | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS18 | Authorization Only – Contactless Mobile Commerce Device | GENERAL | 4 | 4 | 0 | 0 | 0 | 0 | D-02 (3), C-10 (2), C-11 (1) |
| BR1_TS19 | Mail/Phone Authorization Only – Manual / Keyed | CARD_NOT_PRESENT-2100 | 1 | 1 | 0 | 0 | 0 | 0 | C-11 (1) |
| BR1_TS20 | Mail/Phone Authorization Only – Credential on File | CARD_NOT_PRESENT-2100 | 1 | 1 | 0 | 0 | 0 | 0 | C-11 (1) |
| BR1_TS54 | Authorization Only – EMV Contact Chip | HOSPITALITY-ATTENDED | 1 | 1 | 0 | 0 | 0 | 0 | C-10 (1) |
| BR1_TS154 | Authorization Only – EMV Contact Chip | PETROLEUM-ATTENDED | 1 | 1 | 0 | 0 | 0 | 0 | C-10 (1) |
| BR1_TS173 | Authorization Only – EMV Contact Chip | PETROLEUM-UNATTENDED | 1 | 1 | 0 | 0 | 0 | 0 | C-10 (1) |

## 11. ATL105 references used

| Reference | Section | PDF p. | Printed | Text (verified on the page) |
|---|---|---:|---|---|
| APPD-24 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Maryland MD 24 |
| APPD-26 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Michigan MI 26 |
| APPD-57 | Appendix D. Valid State Codes | 516 | Appendix D-3 | Armed Forces Americas AA 57 |
| APPD-66 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Nova Scotia NS 66 |
| APPD-67 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Northwest Territories NT 67 |
| APPD-72 | Appendix D. Valid State Codes | 517 | Appendix D-4 | Yukon YT 72 |
| APPE-020 | Appendix E. Valid Card Type Codes | 521 | Appendix E-4 | 020 Credit Credit |
| APPG-0 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion |
| APPG-3 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 3 POS Authorization Only Point-of-sale authorization only (no draft capture) |
| APPG-8 | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | 8 Purchase reversal Purchase reversal/void |
| APPG-B | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | B Mail/Phone Authorization Only Mail/Telephone authorization only |
| APPG-Z | Appendix G. Valid Transaction Type Codes | 546 | Appendix G-2 | Z Time-out Reversal Time-out reversal |
| APPI-003-ADDR | Appendix I. Variable Information Data Layouts - AVS Information (Table 003) | 554 | Appendix I-5 | The remaining 00–20 characters contain street address information. |
| APPI-003-ZIP | Appendix I. Variable Information Data Layouts - AVS Information (Table 003) | 554 | Appendix I-5 | ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or |
| APPI-005 | Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005) | 555 | Appendix I-6 | Point-of-Service Entry Mode Information |
| APPI-006 | Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006) | 555 | Appendix I-6 | Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)] |
| APPI-030 | Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030) | 568 | Appendix I-19 | Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions. |
| APPI-031 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | Terminal Capability values must be populated for all transactions |
| APPI-031-12 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | 12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities |
| APPI-048 | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 586 | Appendix I-37 | This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication. |
| APPI-048-LEN | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 586 | Appendix I-37 | Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID. |
| APPI-048-PP | Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048) | 587 | Appendix I-38 | 1 = EMV 3-D Secure Version 2.1 |
| APPJ-01 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 01 Manual/keyed |
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
| APPQ | Appendix Q. Valid National Point-of-Service Condition Codes | 690 | Appendix Q-1 | This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030 The National Point-of-Service Condition Codes data element contains the following elements: • Terminal Class (Position Nos. 1–3) • Presentation Type (Position Nos. 4–7) • Security Condition (Position No. 8) • Terminal Type (Position Nos. 9–10) |
| APPQ-010-04 | Appendix Q. Valid National Point-of-Service Condition Codes | 693 | Appendix Q-4 | 010 04 Attended on premise Card acceptor-operated ECR |
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
| EL111 | Element 111 Variable Information Indicator (Chapter 13) | 422 | 13-82 | Fixed length of 3 alphanumeric characters |
| EL111-003 | Element 111 Variable Information Indicator (Chapter 13) | 423 | 13-83 | 003 Address verification service (AVS) indicator |
| EL111-048 | Element 111 Variable Information Indicator (Chapter 13) | 424 | 13-84 | 048 MasterCard Authentication Data |
| EL113-003 | Element 113 Variable Information (Chapter 13) | 426 | 13-86 | When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here. |
| EL116-INIT | Element 116 Additional Information Indicator (Chapter 13) | 433 | 13-93 | A Financial Transaction Request initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction Response. |
| EL118-EMV | Element 118 EMV Additional Information (Chapter 13) | 439 | 13-99 | When EMV Additional Information Indicator has a value of 001, EMV table data is included here. |
| EL12 | Element 12 Card Discretionary Block Data (Chapter 13) | 372 | 13-32 | For a card whose number has been manually entered, the expiration date is in MMYY format |
| EL12-MAX | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2. |
| EL12-TRACK2 | Element 12 Card Discretionary Block Data (Chapter 13) | 371 | 13-31 | Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character. |
| EL121 | Element 121 Partial Approval Indicator (Chapter 13) | 440 | 13-100 | This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions. |
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
| EL33-FMT | Element 33 Encrypted PIN Block Data (Chapter 13) | 382 | 13-42 | For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block. |
| EL41 | Element 41 Fuel Purchase Amount (Chapter 13) | 385 | 13-45 | Representation: Variable length of up to eight digits with two assumed decimal places |
| EL41-USE | Element 41 Fuel Purchase Amount (Chapter 13) | 385 | 13-45 | Included on any transactions with fuel sales. |
| EL44 | Element 44 Information Byte (Chapter 13) | 386 | 13-46 | 0 Single-message transmission |
| EL5-FMT | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | Fixed length of six alphanumeric characters; space-filled |
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
| EL84 | Element 84 Segment Length (Chapter 13) | 406 | 13-66 | This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| EL86-FMT | Element 86 Sequence Number (Chapter 13) | 409 | 13-69 | Valid Codes/Values: 000001–999999 |
| EL86-REV | Element 86 Sequence Number (Chapter 13) | 409 | 13-69 | In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request. |
| EL99 | Element 99 Tax Amount (Chapter 13) | 415 | 13-75 | Identifies the transaction tax amount in Data Segment No. 100, Standard Message Data Segment. |
| S10.1.1.2-FOLLOW | Section 10.1.1.2 Track Support | 81 | 10-2 | All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile. |
| S10.1.1.3 | Section 10.1.1.3 Manual Entry | 81 | 10-2 | If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY). |
| S10.1.2-LIST | Section 10.1.2 Supported Transaction Types | 83 | 10-4 | The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal |
| S10.1.2.1-SEQ | Section 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | The Sequence Number must be the Sequence Number contained in the original transaction request. |
| S10.1.3-REQ | Section 10.1.3 Address Verification Service | 86 | 10-7 | The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment |
| S10.1.3-RESP | Section 10.1.3 Address Verification Service | 86 | 10-7 | The authorizer returns a response containing both the transaction authorization Response Code and the AVS information in the Additional Information Data Segment |
| S10.1.3-USE | Section 10.1.3 Address Verification Service | 86 | 10-7 | Address Verification Service (AVS) is a credit card authorization tool used for transactions performed primarily in a non-face-to-face environment, such as mail order, phone order, or customer-activated terminal (CAT). |
| S10.1.8.2 | Section 10.1.8.2 Track Support Requirements | 94 | 10-15 | The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM). |
| S10.14.3-ONLINE | Section 10.14.3 EMV PIN (Personal Identification Number) Processing | 162 | 10-83 | Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer. |
| S11.1-143 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | 143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager. |
| S11.1-153 | Section 11.1 Financial Transactions (Data Section No. 3) | 169 | 11-6 | 153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction. |
| S11.1-DS1 | Section 11.1 Financial Transactions (Data Section No. 1) | 167 | 11-4 | All Financial Transaction Requests contain Element Nos. 55 and 63. |
| S11.1-DS3 | Section 11.1 Financial Transactions (Data Section No. 3) | 168 | 11-5 | All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9. |
| S11.2.1-108 | Section 11.2.1 Loyalty Card Transactions - Request | 175 | 11-12 | The Loyalty Card Transaction Request contains Segment Nos. 108 and 114. |
| S11.2.1-108R | Section 11.2.1 Loyalty Card Transactions - Request | 175 | 11-12 | Contains the loyalty card data required for all loyalty card transactions. |
| S11.8-EMV | Section 11.8.1 EMV Financial Transaction Request | 205 | 11-42 | The following table illustrates the layout of an EMV Financial Transaction Request: |
| S12.1-FS | Section 12.1 Standard Message Data Segment | 223 | 12-6 | When a field is not populated, still send the Field Separator. |
| S12.1-LEN | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.1-ROW16 | Section 12.1 Standard Message Data Segment | 225 | 12-8 | Identifies the local date and local time of the preauthorized transaction. |
| S12.1-ROW3 | Section 12.1 Standard Message Data Segment | 223 | 12-6 | 3 44 Information Byte 1 R |
| S12.1-TYPE | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Fixed value: 100 |
| S12.10-FS | Section 12.10 Variable Information Data Segment | 246 | 12-29 | A Field Separator follows the final Variable Information Section. |
| S12.10-LEN | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Identifies the length of Variable Information (Element No. 113). |
| S12.10-TYPE | Section 12.10 Variable Information Data Segment | 246 | 12-29 | Fixed value: 111 |
| S12.20-ALWAYS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | It always appears in Data Section No. 3 of an EMV Financial Transaction Request. |
| S12.20-FS | Section 12.20 EMV Request Data Segment | 270 | 12-53 | A Field Separator follows the final EMV Additional Information Section. |
| S12.20-LEN | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
| S12.20-TYPE | Section 12.20 EMV Request Data Segment | 270 | 12-53 | Fixed value: 130 |
| S12.3-ROW3 | Section 12.3 Product Code Data Segment | 230 | 12-13 | 3 87 Service Level 1 R Identifies the type of sale. |
| S12.30-102 | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | A Product Code Data Segment (Data Segment No. 102) must be present in the transaction. |
| S12.30-CAN | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax). |
| S12.30-ORDER | Section 12.30 Tax by Product Data Segment | 285 | 12-68 | Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments. |
| S12.38-FS | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | There is a Field Separator between Field Nos. 1 and 2. |
| S12.38-LEN | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | 2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length |
| S12.38-NOFS | Section 12.38 Network Token Data Request Segment | 310 | 12-93 | Field Separators are not there between the elements in Network Token Data Layouts. |
| S12.38-T004 | Section 12.38 Network Token Data Request Segment | 312 | 12-95 | Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN |
| S12.38-T006 | Section 12.38 Network Token Data Request Segment | 312 | 12-95 | This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation). |
| S12.7-FS | Section 12.7 Loyalty Card Data Segment | 238 | 12-21 | All fields are separated by Field Separators, with a Field Separator following Field No. 13. |
| S12.7-ROW12 | Section 12.7 Loyalty Card Data Segment | 239 | 12-22 | 12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card. |
| S2.3-TOR | Section 2.3 Unsuccessful Financial Transactions | 39 | 2-4 | A time-out reversal (TOR) is created for an unsuccessful financial transaction. |
| S2.3.1-DEVICE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. |
| S2.3.1-LATE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored. |
| S2.3.1-MATCH | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response. |
| S2.3.1-QUEUE | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR |
| S2.3.1-TIMEOUT | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The customer’s 30-second response interval expires without a response from BUYPASS. |
| S2.3.1-TOR-SEQ | Section 2.3.1 Time-out by Customer's Host/Controller/POS System | 40 | 2-5 | The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. |

Regenerate: `python scripts/validate-run-and-build-manual-validation.py --run DhanSingh_mastercard_credit_output_16_11\mastercard_credit_new_16_11 --only TC-039912,TC-039913,TC-039914,TC-039915,TC-039916,TC-040080,TC-040194,TC-040308,TC-040512,TC-040623,TC-040737,TC-040856,TC-040974,TC-041219,TC-041315,TC-041606,TC-040116,TC-040230,TC-040346,TC-040547,TC-040658,TC-040776,TC-040893,TC-041009,TC-040126,TC-040240,TC-040356,TC-040557,TC-040668,TC-040786,TC-040903,TC-041019,TC-040140,TC-040254,TC-040396,TC-040571,TC-040683,TC-040800,TC-040919,TC-041032,TC-041083,TC-041101,TC-038607 --guides TC-039912,TC-039913,TC-039914,TC-039915,TC-039916 --package DhanSingh_mastercard_credit_output_16_11 --sheet MasterCard_Credit_Defect_Demo_Sheet.md --title "MasterCard Credit (plan v12) - defect demo sheet: 5 manual-validation test cases and the BA defect log (up to 19-11-2026)" --scope "One sheet for the demo. It combines (1) the manual validation of TC-039912 to TC-039916 (scenario BR1_TS5, manual/keyed purchase) and (2) every test case of the BA defect log: Table 048 MasterCard Authentication Data claimed but not sent (11 test cases) and Tax Type 1 = GST claimed but not sent, with Segments 102 and 143 missing (8) - AI-team register N17-01; original Authorization Only requests that carry an Approval Number (8) - N17-03; and, raised on 19-11-2026, AVS information expected in the response while Table 003 is not sent (10) and TC-038607, a negative test that leaves out Segment 108 although nothing makes it mandatory. All 43 come from the run mastercard_credit_new_16_11; every field of their test data was checked against ATL105 by the validator, which now detects each of these defect kinds. Each defect is explained the way the BA note does, and the actual and expected test data of every test case follow."`.

