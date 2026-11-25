# ATL105 validation of 3 test cases of the run and their 4 test data files

**Run:** [terminal_and_host_operations](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/) (MasterCard Credit run of 19 Nov 2026), build `buypass-atl105-mastercard-credit-generated-6181e6cf0e`  
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). The attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the 2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  
**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.

ATL105 result of each test case, every segment and field: [TC-078259](TC-078259.md), [TC-078299](TC-078299.md), [TC-078269](TC-078269.md). Detailed step-by-step guides for 1 test cases: [TC-078259](../Manual%20Validation/TC-078259-Manual-Validation.md).

## 1. Result at a glance

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 0 | At least one test data or test case defect that must be fixed |
| **BLOCKED** | 2 | No defect, but a client value or a run-time value is still missing |
| **REVIEW** | 1 | No defect; a citation must be corrected or a point confirmed with BUYPASS |
| **PASS** | 0 | Nothing found |
| **DEVICE** | 0 | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |
| Total | 3 | 3 send a message to BUYPASS (4 test data files); 0 are device-only or have no test data |

Findings by kind (a test case can have several):

| Kind | Findings | Test cases | Effect on the result |
|---|---:|---:|---|
| Test case issue to correct | 2 | 2 | REVIEW |
| Citation issue | 3 | 3 | REVIEW |
| Awaiting client input / run-time value | 5 | 2 | BLOCKED |
| To confirm with BUYPASS / SME | 1 | 1 | REVIEW |
| Not checked - request type outside the validator's trained ATL105 rules | 2 | 2 | none |

Data segments in the test data: 100 (1 files), 105 (1 files), 111 (1 files), 119 (1 files), 130 (1 files), Table Load Request fields (1 files).

## 2. How every test case and test data file was checked

1. **Trained AI validator** (`scripts/validate-td-against-atl105-spec.py`, the rules built in the earlier MasterCard, Interlink and Maestro reviews): Data Section 1, all 17 fields of Segment 100, Segment 111 with Tables 005/031/030 and the Credential on File tables, Segment 130 (EMV) with its TLV chip data, Segment 105 (Totals), links between steps (Sequence Number, card, terminal, entry mode), expected Response Codes, and every ATL105 quote in the test case (`spec_basis`) against the page it cites.
2. **Extended checks for this run** (`scripts/validate-run-and-build-manual-validation.py`): the other data segments (101, 102, 104, 108, 123, 145, 153 - Segment Type, Segment Length with each segment's Field Separator rule, Chapter 13 format of every field, TLV structure), every Variable Information table ID, values awaited from the client, each test case's own `rule_checks`, test data vs test case value fidelity for all segments, identical requests across all test cases, Sequence Number uniqueness per terminal, Time-out Reversal flows, negative-test premises and conditional fields.
3. **Classification**: every finding was sorted into the kinds in section 1. Findings of negative tests that are the rule the test deliberately breaks are listed as *intended* (correct). Where the trained rule did not fit a new flow of this run, the value was checked against ATL105 and the finding listed as *AI rule not applicable* (section 9) with the reason.
4. Every ATL105 text quoted in this report (listed in section 11) was found word for word on the stated page when this report was generated.

## 3. Defects in the test data (must be fixed)

None found.

## 4. Defects and issues in the test cases (design, status, expected results)

### 4.1 C-03 - Test case marked 'ready' although its Terminal Identifier is a placeholder

**1 test case, 1 finding** - kind: Test case issue to correct

Rules: `READY_WITH_PLACEHOLDER` (1).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

What is wrong (one example per kind of problem):

- `READY_WITH_PLACEHOLDER` (TC-078259) - The test case status is 'ready', but its Terminal Identifier is still a placeholder.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078259](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) (BROP_TS2_TC1) | - | `READY_WITH_PLACEHOLDER` | test case status | `ready` | Keep the test case out of 'ready' until the real Terminal Identifier is supplied. |

Scenarios: BROP_TS2 (1)

**How to check by hand:** TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.

**Fix:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 4.2 C-04 - Expected Response Code not stated for a step, or several codes accepted

**1 test case, 1 finding** - kind: Test case issue to correct

Rules: `EXPECTED_RESPONSE_OPEN` (1).

ATL105:

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"

What is wrong (one example per kind of problem):

- `EXPECTED_RESPONSE_OPEN` (TC-078299) - The test case accepts any of K = see Element 83; L = see Element 83; M = see Element 83; O = see Element 83; T = see Element 83; Y = see Element 83 for the table load step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078299](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) (BROP_TS9_TC7) | - | `EXPECTED_RESPONSE_OPEN` | test case expected_response.response_code (step 2) | `any of K, L, M, O, T, Y` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |

Scenarios: BROP_TS9 (1)

**How to check by hand:** Open tx_legs[i].expect / steps[i].expected_response: no response_code value; or expected_response.response_code.acceptable_values lists several approval codes (e.g. 0 and 2) for a non-totals step. Approved-Totals codes 5/6/B/D/N/W for a Totals Request are accepted (Element 83, p.404-405).

**Fix:** State the expected Response Code (from BUYPASS or a reference run).

## 5. Citation issues (the ATL105 text quoted in the test cases)

### 5.1 Q-02 - Quote is on a different page than cited

**3 test cases, 3 findings** - kind: Citation issue

Rules: `QUOTE_PAGE_DRIFT` (3).

What is wrong (one example per kind of problem):

- `QUOTE_PAGE_DRIFT` (TC-078259) - The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078259](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) (BROP_TS2_TC1) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-078299](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) (BROP_TS9_TC7) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |
| [TC-078269](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17) (BROP_TS3_TC2) | - | `QUOTE_PAGE_DRIFT` | test case spec_basis | `The following credit cards are supported…. MasterCard` | Cite p.160 instead of p.161. |

Scenarios: BROP_TS2 (1), BROP_TS9 (1), BROP_TS3 (1)

**How to check by hand:** Find the quote: it is on another PDF page.

**Fix:** Correct the page number.

## 6. Values awaiting client input or run-time values (BLOCKED, not defects)

### 6.1 I-01 - Terminal Identifier is a generated placeholder

**2 test cases, 2 findings** - kind: Awaiting client input / run-time value

Rules: `TERMINAL_PLACEHOLDER` (2).

ATL105:

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Oregon OR 41"

What is wrong (one example per kind of problem):

- `TERMINAL_PLACEHOLDER` (TC-078259) - The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX41894821001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '41' = Oregon (OR), United States.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078259](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) (BROP_TS2_TC1) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |
| [TC-078299](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) (BROP_TS9_TC7) | - | `TERMINAL_PLACEHOLDER` | Standard Message Data Segment -> TerminalIdentifier (every step) | `JX41894821001` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. |

Scenarios: BROP_TS2 (1), BROP_TS9 (1)

**How to check by hand:** merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.

**Fix:** Client / BUYPASS to supply the real Terminal Identifier.

### 6.2 I-04 - EMV chip data is generated, not from a card / simulator

**1 test case, 2 findings** - kind: Awaiting client input / run-time value

Rules: `EMV_CA_CHECKSUM_SAMPLE` (1), `EMV_PLACEHOLDER_CRYPTO` (1).

ATL105:

- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."

What is wrong (one example per kind of problem):

- `EMV_CA_CHECKSUM_SAMPLE` (TC-078299) - The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- `EMV_PLACEHOLDER_CRYPTO` (TC-078299) - ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078299](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) (BROP_TS9_TC7) | - | `EMV_CA_CHECKSUM_SAMPLE` | EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request) | `2012091700000100000102171` | Confirm the checksum of the host's current CA Public Key file before execution. |
| [TC-078299](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) (BROP_TS9_TC7) | - | `EMV_PLACEHOLDER_CRYPTO` | EMV Request Data Segment -> EMVChipData (every EMV request) | `9F26=22F91638A4535D60, 9F36=8609, 9F37=C…5=8000008000` | Produce the chip data with a card simulator / HSM before execution. |

Scenarios: BROP_TS9 (2)

**How to check by hand:** See the EMV finding text in the guide / sheet.

**Fix:** Use chip data captured from the client's test card or card simulator.

### 6.3 I-05 - Totals Request device versions are generated placeholders

**1 test case, 1 finding, 1 test data file** - kind: Awaiting client input / run-time value

Rules: `TOTALS_VERSION_PLACEHOLDER` (1).

ATL105:

- Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters"
- Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters"
- Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters"

What is wrong (one example per kind of problem):

- `TOTALS_VERSION_PLACEHOLDER` (TC-078259) - ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078259](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) (BROP_TS2_TC1) | 1 | `TOTALS_VERSION_PLACEHOLDER` | [TD-TC-078259.json](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json) | `FQ4R0WSY, A1R7QXCZ, 6KZUA8XA` | Use the versions of the certified device that runs the test. |

Scenarios: BROP_TS2 (1)

**How to check by hand:** Hardware/Software/Firmware Version hold random strings.

**Fix:** Client to supply the versions of the certified device.

## 7. Points to confirm with BUYPASS / SME

### 7.1 C-08 - Same request as another test case (difference only on the device / response side)

**1 test case, 1 finding, 1 test data file** - kind: To confirm with BUYPASS / SME

Rules: `X_SAME_REQUEST` (1).

What is wrong (one example per kind of problem):

- `X_SAME_REQUEST` (TC-078269) - Apart from the Sequence Number the message is identical to TC-078268 (all segments compared). TC-078268 expects 5/6/B/D/N/W; this test expects 1. What makes the two tests different is not in the message sent to BUYPASS.

| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |
|---|---|---|---|---|---|
| [TC-078269](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17) (BROP_TS3_TC2) | 1 | `X_SAME_REQUEST` | TD-TC-078269.json vs TD-TC-078268.json | `expected 1 vs 5/6/B/D/N/W` | Check the difference on the device / in the response; optionally use another card. |

Scenarios: BROP_TS3 (1)

**How to check by hand:** Compare both test data files: identical apart from the Sequence Number.

**Fix:** Check the difference on the device or in the response; optionally use another card.

## 8. Negative tests - intended violations (correct)

0 negative test cases break their rule as designed (0 findings). They are not defects; check that each test breaks only that rule.

| Rule | Findings | Example |
|---|---:|---|

## 9. AI validator rules that did not apply to this run (value checked - correct)

| Rule | Findings | Test cases | Why the value is correct |
|---|---:|---:|---|

Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator did not read segments 101-153; section 2 point 2 now checks them).

Run-wide checks (all 47 test cases of the run were validated so that these checks see every test case): 13 single-step test cases send the same request as an earlier test case (all segments compared); 1 new transactions checked for a unique Sequence Number per terminal, 0 duplicate(s).

## 10. Result per scenario

| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |
|---|---|---|---:|---:|---:|---:|---:|---:|---|
| BROP_TS2 | Purchase/Capture – EMV Contact Chip | None | 1 | 0 | 1 | 0 | 0 | 0 | - |
| BROP_TS3 | Purchase/Capture – EMV Contact Chip | None | 1 | 0 | 0 | 1 | 0 | 0 | - |
| BROP_TS9 | Purchase/Capture – EMV Contact Chip | None | 1 | 0 | 1 | 0 | 0 | 0 | - |

## 11. ATL105 references used

| Reference | Section | PDF p. | Printed | Text (verified on the page) |
|---|---|---:|---|---|
| APPD-41 | Appendix D. Valid State Codes | 515 | Appendix D-2 | Oregon OR 41 |
| APPE-020 | Appendix E. Valid Card Type Codes | 521 | Appendix E-4 | 020 Credit Credit |
| APPG-0 | Appendix G. Valid Transaction Type Codes | 545 | Appendix G-1 | 0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion |
| APPI-005 | Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005) | 555 | Appendix I-6 | Point-of-Service Entry Mode Information |
| APPI-006 | Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006) | 555 | Appendix I-6 | Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)] |
| APPI-030 | Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030) | 568 | Appendix I-19 | Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions. |
| APPI-031 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | Terminal Capability values must be populated for all transactions |
| APPI-031-12 | Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031) | 569 | Appendix I-20 | 12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities |
| APPJ-05 | Appendix J. Valid Point-of-Service Entry Mode Codes | 638 | Appendix J-1 | 05 Integrated circuit card (ICC) read – CVV data reliable (used for EMV chip read) |
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
| EL20 | Element 20 Currency Code (Chapter 13) | 376 | 13-36 | Fixed length of three digits |
| EL39 | Element 39 Firmware Version (Chapter 13) | 384 | 13-44 | Fixed length of eight alphanumeric characters |
| EL42 | Element 42 Grand Total (Chapter 13) | 386 | 13-46 | Fixed length of eight digits with two assumed decimal places |
| EL43 | Element 43 Hardware Version (Chapter 13) | 386 | 13-46 | Fixed length of four or eight alphanumeric characters |
| EL44 | Element 44 Information Byte (Chapter 13) | 386 | 13-46 | 0 Single-message transmission |
| EL5-PRE | Element 5 Approval Number (Chapter 13) | 368 | 13-28 | If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization. |
| EL55 | Element 55 Message Format Version Identifier (Chapter 13) | 391 | 13-51 | Fixed length of six alphanumeric characters |
| EL58 | Element 58 Nonfuel Amount (Chapter 13) | 393 | 13-53 | A maximum length of 7 bytes is allowed for all other card types. |
| EL63 | Element 63 Number of Segments (Chapter 13) | 395 | 13-55 | Variable length of up to two digits |
| EL78 | Element 78 Prompt Code (Chapter 13) | 400 | 13-60 | this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters). |
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
| EL96 | Element 96 Software Version (Chapter 13) | 414 | 13-74 | Fixed length of eight alphanumeric characters |
| S11.1-DS1 | Section 11.1 Financial Transactions (Data Section No. 1) | 167 | 11-4 | All Financial Transaction Requests contain Element Nos. 55 and 63. |
| S11.4-TOTALS | Section 11.4.1.1 Totals Request | 178 | 11-15 | Note: Totals Request messages do not contain Data Section No. 2 [Standard Message Data Segment (Data Segment No. 100)]. |
| S11.8-EMV | Section 11.8.1 EMV Financial Transaction Request | 205 | 11-42 | The following table illustrates the layout of an EMV Financial Transaction Request: |
| S12.1-FS | Section 12.1 Standard Message Data Segment | 223 | 12-6 | When a field is not populated, still send the Field Separator. |
| S12.1-LEN | Section 12.1 Standard Message Data Segment | 223 | 12-6 | Identifies the data segment's length, including Segment Type's length and Field Separators. |
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
| S9.2-GT | Section 9.2 Local Totals | 72 | 9-8 | The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only |
| S9.2-PUR | Section 9.2 Local Totals | 72 | 9-8 | A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total. |
| S9.2-RET | Section 9.2 Local Totals | 72 | 9-8 | A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total. |
| S9.2-REV | Section 9.2 Local Totals | 72 | 9-8 | A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total. |

Regenerate: `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11\terminal_and_host_operations" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output\Terminal_Host_Operations" --only TC-078259,TC-078299,TC-078269 --guides TC-078259 --sheet Terminal_Host_Operations_Defect_Sheet.md --title "Run set 20-11, terminal & host operations part: defect sheet for the BA review" --scope "Terminal & host operations part of the run set `Dhan_Singh_run_set_20-11`: totals, downloads, key loads, electronic mail, communications test and device behaviour. The sheet shows every finding of this part, with all affected test cases, and the test data of 3 representative test cases." --groups 20-11-parts --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_th.md`.

