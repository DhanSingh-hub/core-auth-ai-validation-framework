# Run set 20-11, data element validation part: defect sheet for the BA review

## Purpose and scope

Data element validation part of the run set `Dhan_Singh_run_set_20-11`: negative and boundary tests of single data elements across the request types. The sheet shows every defect the validator finds in this part, with all affected test cases, and the actual and expected test data of 19 representative test cases.

| Item | Value |
|---|---|
| Run | [data_element_validation](../../Dhan_Singh_run_set_20-11/data_element_validation/) - MasterCard Credit run of 19 Nov 2026, build `buypass-atl105-mastercard-credit-generated-35c195d1e7` (only this run was used; its files were checked against the run's own MANIFEST.json - see the appendix) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` |
| Test cases | [TC-077239](#tc-077239), [TC-077295](#tc-077295), [TC-077381](#tc-077381), [TC-077469](#tc-077469), [TC-077225](#tc-077225), [TC-077230](#tc-077230), [TC-077240](#tc-077240), [TC-077255](#tc-077255), [TC-077261](#tc-077261), [TC-078056](#tc-078056), [TC-078069](#tc-078069), [TC-077849](#tc-077849), [TC-077229](#tc-077229), [TC-078058](#tc-078058), [TC-077358](#tc-077358), [TC-077413](#tc-077413), [TC-077480](#tc-077480), [TC-077553](#tc-077553), [TC-077628](#tc-077628) |
| AI result | FAIL 17 · BLOCKED 1 · REVIEW 1 |
| Readiness | 12 `ready`, 2 `needs_client_input`, 2 `requires_review`, 1 `blocked_verification`, 1 `pending_sme`, 1 `blocked_no_test_data` |

- **TC** = test case: what the test says it will exercise (`test_cases.jsonl`, one test case per line).
- **TD** = test data: the request prepared for transmission to the host (`test_data/TD-TC-....json`).
- **DS** = data segment (Segment 100, 111, ...); **on the wire** = actually included in the transmitted request.
- **Element** = ATL105 data element (Chapter 13); **Table** = Variable Information table in Segment 111 (Appendix I).
- **FAIL** = must be fixed; **BLOCKED** = cannot run until a client or run-time value is supplied; **REVIEW** = to correct or to confirm with BUYPASS.

These are composition defects in the delivered test cases and test data, found by reading them against ATL105 - not observed production failures. No host transactions were executed for this review.

## Not checked field by field

453 of the 1,030 test cases of this part send request types the validator has no trained ATL105 rules for: Totals with Proprietary Data Load Request (110), Proprietary Data Load Request (76), Electronic Mail Request (67), CA Public Key File Load Request (54), TransArmor Key Load Request (45), Moneris Key Load Request (28), Software Load Request (26), Table Load Request (26), Date and Time Load Request (9), Phone Load Request (9), Communications Test Request (3). For these only the test case level was checked (status, expected response, citations, identical requests); their test data must be reviewed against their Chapter 11 / 12 layouts by hand. The defects below come from the Financial / EMV Financial Transaction Requests and Totals Requests of this part, which were checked field by field. Back to the main sheet: [MasterCard_Credit_Defect_Demo_Sheet.md](../MasterCard_Credit_Defect_Demo_Sheet.md).

## Defects in this sheet

**In the run** = test cases of the whole run (1,030) with the defect; **shown** = test cases of this sheet. Each defect section lists every affected test case of the run.

| Defect | In the run | Shown | Kind | Defect id | Priority |
|---|---:|---:|---|---|---|
| [1. Negative tests that break a second rule besides the one under test](#d1) | 40 | 6 | Test data defect | D-09 | High |
| [2. Card read from the chip or stripe, but the card data is only an MMYY expiry](#d2) | 457 | 11 | Test data defect | D-13 | High |
| [3. The Terminal Identifier is a generated placeholder, yet the test cases are marked ready](#d3) | 546 | 17 | Awaiting client input / run-time value, Test case issue to correct | I-01, C-03 | High |
| [4. Positive boundary test: the value is not at the documented limit or is not a valid value](#d4) | 33 | 6 | Test data defect | D-15 | High |
| [5. Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply](#d5) | 8 | 2 | Test data defect | D-02 | High |
| [6. Same request as another test case but a different expected result](#d6) | 4 | 2 | Test case defect | C-01 | High |
| [7. 'Different terminal' negative tests use a terminal BUYPASS does not know](#d7) | 18 | 1 | To confirm with BUYPASS / SME | C-07 | Medium |
| [8. Network token data (Segment 153) is synthesized, and its indicator and length need confirmation](#d8) | 4 | 1 | Awaiting client input / run-time value | I-06 | Medium |
| [9. Follow-on values are instruction text to be filled in from the previous response](#d9) | 19 | 1 | Awaiting client input / run-time value | I-02 | Medium |
| [10. EMV chip data is generated, not captured from a card or simulator](#d10) | 391 | 9 | Awaiting client input / run-time value | I-04 | Medium |
| [11. Segment 100 value awaited from the client (<AWAITING:...>)](#d11) | 21 | 2 | Awaiting client input / run-time value | I-09 | Medium |
| [12. Totals Request device versions are generated placeholders](#d12) | 86 | 3 | Awaiting client input / run-time value | I-05 | Medium |
| [13. No test data generated for a host test case](#d13) | 16 | 1 | Awaiting client input / run-time value | I-08 | Medium |
| [14. Expected Response Code not stated for a step, or several codes accepted](#d14) | 128 | 1 | Test case issue to correct | C-04 | Medium |
| [15. Other points to confirm](#d15) | 1 | 1 | To confirm with BUYPASS / SME | S-05 | Medium |
| [16. Same request as another test case (difference only on the device / response side)](#d16) | 94 | 1 | To confirm with BUYPASS / SME | C-08 | Medium |
| [17. Expiry format for stored credentials (entry mode 10) not stated](#d17) | 2 | 1 | To confirm with BUYPASS / SME | S-01 | Medium |
| [18. ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context](#d18) | 1,027 | 18 | Citation issue | Q-01 | Low |

<a id="d1"></a>

## Defect 1: Negative tests that break a second rule besides the one under test

**Priority:** High · **Kind:** Test data defect · **Test cases:** 6 shown (40 in the run) · **Defect id:** D-09

<details><summary>All 40 test cases of the run with this defect (click to expand)</summary>

[TC-077239](#tc-077239), [TC-077241](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L19), [TC-077295](#tc-077295), [TC-077296](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L74), [TC-077297](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L75), [TC-077381](#tc-077381), [TC-077469](#tc-077469), [TC-077471](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L249), [TC-077473](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L251), [TC-077474](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L252), [TC-077475](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L253), [TC-077477](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L255), [TC-077479](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L257), [TC-077480](#tc-077480), [TC-077483](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L261), [TC-077485](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L263), [TC-077487](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L265), [TC-077622](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L400), [TC-077623](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L401), [TC-077624](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L402), [TC-077628](#tc-077628), [TC-077629](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L407), [TC-077878](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L656), [TC-077880](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L658), [TC-077881](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L659), [TC-077882](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L660), [TC-077908](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L686), [TC-077909](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L687), [TC-077910](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L688), [TC-078065](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L843), [TC-078070](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L848), [TC-078075](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L853), [TC-078080](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L858), [TC-078085](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L863), [TC-078090](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L868), [TC-078095](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L873), [TC-078106](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L884), [TC-078141](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L919), [TC-078142](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L920), [TC-078143](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L921)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077239](#tc-077239) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data one byte over its documented maximum length (51) | Card Discretionary Block Data one byte over its documented maximum length (51) | [TD-TC-077239.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json) |
| [TC-077295](#tc-077295) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73)) | `needs_client_input` | Standard Message Data Segment: Segment Length one more than its rendered bytes | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| [TC-077381](#tc-077381) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159)) | `requires_review` | Purchase Card Data Segment: Segment Type differs from its fixed value '104' | Segment Type differs from its fixed value '104' | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) |
| [TC-077469](#tc-077469) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247)) | `ready` | Totals Data Segment: Card Label one byte over its documented maximum length (4) | Card Label one byte over its documented maximum length (4) | [TD-TC-077469.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) |
| [TC-077480](#tc-077480) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258)) | `ready` | Totals Data Segment: Card Type Total Count one below its documented range (00001-99999) | Card Type Total Count one below its documented range (00001-99999) | [TD-TC-077480.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) |
| [TC-077628](#tc-077628) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406)) | `requires_review` | Variable Information Data Segment: POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08): code 'X' outside its documented valid codes I/C | Table 032 POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08): code 'X' outside its documented valid codes I/C | [TD-TC-077628.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077239 | 1 | [TD-TC-077239.json line 14](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L14) → `Standard Message Data Segment → AccountNumber` | `TOTALS REQUEST` | Account Number is not valid |
| TC-077295 | 1 | [TD-TC-077295.json line 23](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `0CI2N6` | Approval Number is not valid |
| TC-077381 | 1 | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) → `Purchase Card Data Segment` | `(absent)` | Purchase Card Data Segment is in the test case but not in the test data |
| TC-077469 | 1 | [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment → CardTypeTotalCount` | `` | Card Type Total Count (Totals) is not valid |
| TC-077469 | 1 | [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment → CardTypeTotalAmount` | `` | Card Type Total Amount (Totals) is not valid |
| TC-077480 | 1 | [TD-TC-077480.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L5) → `Totals Data Segment → CardLabel` | `` | Card Label (Totals) is not valid |
| TC-077480 | 1 | [TD-TC-077480.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L5) → `Totals Data Segment → CardTypeTotalAmount` | `` | Card Type Total Amount (Totals) is not valid |
| TC-077628 | 1 | [TD-TC-077628.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json#L27) → `Variable Information Data Segment` | `tables sent: 005, 032, 031` | Table 030 (POS Condition Code) is missing |
| TC-077628 | 1 | [TD-TC-077628.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json#L27) → `Variable Information Data Segment` | `tables sent: 005, 032, 031` | The Credential on File indicators are missing |

### Expected behavior

- A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule).
- A value that meets: Not sent on a purchase.
- Regenerate the test data file from the test case.
- A value that meets: Required.
- Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.
- Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).

### Why this is a defect

- 'TOTALS REQUEST' fails the card-number check digit (Luhn).
- '0CI2N6' is sent on a purchase, which makes it preauthorized, so BUYPASS does not go out for authorization.
- The test case request carries this segment with values, but the test data file does not send it.
- '' Card Type Total Count is required.
- '' Card Type Total Amount is required.
- '' Card Label is required.
- This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).

**BA explanation:** "A negative test must break exactly one rule, so that the decline can only come from that rule. These requests also break another ATL105 rule (missing Table 030 / Credential on File indicators, wrong segment length, entry mode that does not fit the scenario), so the cause of a decline cannot be told."

### Acceptance criteria

- Every field except the one under test is valid for the scenario; the other rule named in each finding is fixed.
- A regression check validates the whole request of each negative test except its deliberate field.

**Specification reference (ATL105, word for word):**

- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number" ([ATL105 text line 21375](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21375))
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization." ([ATL105 text line 21570](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21570))
- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Fixed length of five digits" ([ATL105 text line 21890](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21890))
- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Valid Codes/Values: 00001–99999" ([ATL105 text line 21894](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21894))
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places" ([ATL105 text line 21868](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21868))
- Element 13 Card Label (Chapter 13), PDF p.372 (printed 13-32): "Fixed length of four alphanumeric characters; left-aligned and space-filled" ([ATL105 text line 21792](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21792))
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type." ([ATL105 text line 13692](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L13692))
- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions." ([ATL105 text line 30379](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L30379))
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)." ([ATL105 text line 30662](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L30662))
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions." ([ATL105 text line 31460](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L31460))
- Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type." ([ATL105 text line 6708](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L6708))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file" ([ATL105 text line 34760](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L34760))

**Test data excerpt - [TC-077239](#tc-077239), step 1, `TD-TC-077239.json` (selected fields, actual then expected):**

```jsonc
// actual - line 14
"AccountNumber": "TOTALS REQUEST",
// expected
"AccountNumber": "TOTALS REQUEST",  // D-09: A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule).
```

<a id="d2"></a>

## Defect 2: Card read from the chip or stripe, but the card data is only an MMYY expiry

**Priority:** High · **Kind:** Test data defect · **Test cases:** 11 shown (457 in the run) · **Defect id:** D-13

<details><summary>All 457 test cases of the run with this defect (click to expand)</summary>

[TC-077223](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1), [TC-077224](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L2), [TC-077225](#tc-077225), [TC-077226](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L4), [TC-077227](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L5), [TC-077228](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L6), [TC-077229](#tc-077229), [TC-077230](#tc-077230), [TC-077231](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L9), [TC-077232](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L10), [TC-077233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L11), [TC-077234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L12), [TC-077235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L13), [TC-077236](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L14), [TC-077237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L15), [TC-077238](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L16), [TC-077242](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L20), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077244](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L22), [TC-077245](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L23), [TC-077246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L24), [TC-077247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L25), [TC-077248](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L26), [TC-077249](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L27), [TC-077250](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L28), [TC-077251](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L29), [TC-077252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L30), [TC-077253](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L31), [TC-077254](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L32), [TC-077255](#tc-077255), [TC-077256](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L34), [TC-077257](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L35), [TC-077258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L36), [TC-077259](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L37), [TC-077260](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L38), [TC-077261](#tc-077261), [TC-077262](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L40), [TC-077263](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L41), [TC-077264](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L42), [TC-077265](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L43), [TC-077266](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L44), [TC-077267](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L45), [TC-077268](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L46), [TC-077269](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L47), [TC-077270](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L48), [TC-077271](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L49), [TC-077272](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L50), [TC-077273](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L51), [TC-077274](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L52), [TC-077275](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L53), [TC-077276](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L54), [TC-077277](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L55), [TC-077278](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L56), [TC-077279](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L57), [TC-077280](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L58), [TC-077281](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L59), [TC-077282](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L60), [TC-077283](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L61), [TC-077284](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L62), [TC-077285](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L63), [TC-077286](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L64), [TC-077287](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L65), [TC-077288](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L66), [TC-077289](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L67), [TC-077290](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L68), [TC-077291](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L69), [TC-077292](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L70), [TC-077293](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L71), [TC-077294](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L72), [TC-077295](#tc-077295), [TC-077296](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L74), [TC-077298](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L76), [TC-077299](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L77), [TC-077300](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L78), [TC-077301](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L79), [TC-077302](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L80), [TC-077303](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L81), [TC-077304](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L82), [TC-077305](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L83), [TC-077306](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L84), [TC-077307](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L85), [TC-077308](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L86), [TC-077309](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L87), [TC-077310](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L88), [TC-077311](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L89), [TC-077312](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L90), [TC-077313](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L91), [TC-077314](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L92), [TC-077315](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L93), [TC-077316](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L94), [TC-077317](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L95), [TC-077318](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L96), [TC-077319](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L97), [TC-077320](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L98), [TC-077321](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L99), [TC-077322](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L100), [TC-077323](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L101), [TC-077324](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L102), [TC-077325](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L103), [TC-077326](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L104), [TC-077327](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L105), [TC-077328](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L106), [TC-077329](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L107), [TC-077330](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L108), [TC-077331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L109), [TC-077332](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L110), [TC-077333](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L111), [TC-077334](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L112), [TC-077335](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L113), [TC-077336](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L114), [TC-077337](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L115), [TC-077338](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L116), [TC-077339](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L117), [TC-077340](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L118), [TC-077341](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L119), [TC-077342](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L120), [TC-077343](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L121), [TC-077344](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L122), [TC-077345](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L123), [TC-077346](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L124), [TC-077347](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L125), [TC-077348](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L126), [TC-077349](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L127), [TC-077350](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L128), [TC-077351](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L129), [TC-077352](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L130), [TC-077353](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L131), [TC-077354](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L132), [TC-077355](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L133), [TC-077356](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L134), [TC-077357](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L135), [TC-077360](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L138), [TC-077361](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L139), [TC-077362](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L140), [TC-077363](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L141), [TC-077364](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L142), [TC-077365](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L143), [TC-077366](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L144), [TC-077367](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L145), [TC-077368](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L146), [TC-077369](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L147), [TC-077370](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L148), [TC-077371](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L149), [TC-077372](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L150), [TC-077373](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L151), [TC-077374](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L152), [TC-077375](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L153), [TC-077376](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L154), [TC-077377](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L155), [TC-077378](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L156), [TC-077379](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L157), [TC-077380](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L158), [TC-077381](#tc-077381), [TC-077382](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L160), [TC-077383](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L161), [TC-077384](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L162), [TC-077385](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L163), [TC-077386](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L164), [TC-077387](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L165), [TC-077388](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L166), [TC-077389](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L167), [TC-077390](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L168), [TC-077391](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L169), [TC-077392](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L170), [TC-077393](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L171), [TC-077394](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L172), [TC-077395](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L173), [TC-077396](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L174), [TC-077397](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L175), [TC-077398](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L176), [TC-077399](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L177), [TC-077400](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L178), [TC-077401](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L179), [TC-077402](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L180), [TC-077403](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L181), [TC-077404](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L182), [TC-077405](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L183), [TC-077406](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L184), [TC-077407](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L185), [TC-077408](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L186), [TC-077492](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L270), [TC-077493](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L271), [TC-077494](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L272), [TC-077495](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L273), [TC-077496](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L274), [TC-077497](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L275), [TC-077498](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L276), [TC-077499](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L277), [TC-077500](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L278), [TC-077501](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L279), [TC-077502](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L280), [TC-077503](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L281), [TC-077504](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L282), [TC-077505](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L283), [TC-077506](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L284), [TC-077507](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L285), [TC-077508](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L286), [TC-077509](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L287), [TC-077510](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L288), [TC-077511](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L289), [TC-077512](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L290), [TC-077513](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L291), [TC-077514](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L292), [TC-077515](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L293), [TC-077516](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L294), [TC-077517](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L295), [TC-077518](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L296), [TC-077519](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L297), [TC-077520](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L298), [TC-077521](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L299), [TC-077522](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L300), [TC-077523](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L301), [TC-077524](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L302), [TC-077525](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L303), [TC-077526](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L304), [TC-077527](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L305), [TC-077528](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L306), [TC-077529](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L307), [TC-077530](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L308), [TC-077531](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L309), [TC-077532](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L310), [TC-077533](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L311), [TC-077534](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L312), [TC-077535](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L313), [TC-077536](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L314), [TC-077537](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L315), [TC-077538](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L316), [TC-077539](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L317), [TC-077542](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L320), [TC-077543](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L321), [TC-077544](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L322), [TC-077545](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L323), [TC-077546](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L324), [TC-077547](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L325), [TC-077609](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L387), [TC-077610](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L388), [TC-077611](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L389), [TC-077612](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L390), [TC-077613](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L391), [TC-077614](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L392), [TC-077615](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L393), [TC-077616](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L394), [TC-077617](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L395), [TC-077618](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L396), [TC-077619](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L397), [TC-077620](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L398), [TC-077621](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L399), [TC-077622](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L400), [TC-077623](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L401), [TC-077624](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L402) and 207 more - every one is in [Defects_in_the_run.csv](Defects_in_the_run.csv)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077295](#tc-077295) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73)) | `needs_client_input` | Standard Message Data Segment: Segment Length one more than its rendered bytes | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| [TC-077381](#tc-077381) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159)) | `requires_review` | Purchase Card Data Segment: Segment Type differs from its fixed value '104' | Segment Type differs from its fixed value '104' | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) |
| [TC-077225](#tc-077225) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3)) | `ready` | Standard Message Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077225.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json) |
| [TC-077230](#tc-077230) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8)) | `ready` | Standard Message Data Segment: Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | [TD-TC-077230.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json) |
| [TC-077255](#tc-077255) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33)) | `ready` | Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) |
| [TC-077261](#tc-077261) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39)) | `blocked_verification` | Standard Message Data Segment: Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077261.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json) |
| [TC-078056](#tc-078056) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834)) | `needs_client_input` | Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted | Maximal field-inclusion profile request is accepted | [TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) |
| [TC-078069](#tc-078069) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847)) | `ready` | Financial Transaction Request – Message structure: SKU Data Segment moved first | segment 114 SKU Data Segment moved first | [TD-TC-078069.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json) |
| [TC-077849](#tc-077849) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627)) | `pending_sme` | NFC Payment Tokenization Data Segment: Cryptogram Token Data value not in its documented valid values | Cryptogram Token Data value not in its documented valid values | [TD-TC-077849.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json) |
| [TC-077229](#tc-077229) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7)) | `ready` | Standard Message Data Segment: Terminal Identifier one byte over its documented maximum length (22) | Terminal Identifier one byte over its documented maximum length (22) | [TD-TC-077229.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json) |
| [TC-078058](#tc-078058) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836)) | `ready` | Financial Transaction Request – Message structure: 104 Purchase Card Data Segment present | 104 Purchase Card Data Segment present | [TD-TC-078058.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077295 | 1 | [TD-TC-077295.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0931` | Card Discretionary Block Data is not valid |
| TC-077381 | 1 | [TD-TC-077381.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0427` | Card Discretionary Block Data is not valid |
| TC-077225 | 1 | [TD-TC-077225.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0329` | Card Discretionary Block Data is not valid |
| TC-077230 | 1 | [TD-TC-077230.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0130` | Card Discretionary Block Data is not valid |
| TC-077255 | 1 | [TD-TC-077255.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0428` | Card Discretionary Block Data is not valid |
| TC-077261 | 1 | [TD-TC-077261.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0529` | Card Discretionary Block Data is not valid |
| TC-078056 | 1 | [TD-TC-078056.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0432` | Card Discretionary Block Data is not valid |
| TC-078069 | 1 | [TD-TC-078069.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json#L20) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `1031` | Card Discretionary Block Data is not valid |
| TC-077849 | 1 | [TD-TC-077849.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0232` | Card Discretionary Block Data is not valid |
| TC-077229 | 1 | [TD-TC-077229.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0231` | Card Discretionary Block Data is not valid |
| TC-078058 | 1 | [TD-TC-078058.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `1229` | Card Discretionary Block Data is not valid |

### Expected behavior

- `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known.

### Why this is a defect

- '0931' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

**BA explanation:** "Table 005 says the card was read (chip or magnetic stripe), so ATL105 expects the track data after the '=' (expiry YYMM, service code, discretionary data). The request sends only a 4-digit MMYY expiry, as for a keyed card - the message contradicts itself."

### Acceptance criteria

- A read card sends its Track 2 (equivalent) data after the '=' in Card Discretionary Block Data, or the test uses a keyed entry mode when only the expiry is known.

**Specification reference (ATL105, word for word):**

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character." ([ATL105 text line 21712](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21712))
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2." ([ATL105 text line 21710](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21710))

**Test data excerpt - [TC-077295](#tc-077295), step 1, `TD-TC-077295.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "093",
// expected
"SegmentLength": "093",  // N-01: different from step None ('092') on purpose - the rule this negative test breaks; keep; D-13/I-01: recalculate: 107 + length of the track data + length of the real Terminal Identifier
// actual - line 15
"CardDiscretionaryBlockData": "0931",
// expected
"CardDiscretionaryBlockData": "<Track 2 data after '=': 3109 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3109 (YYMM), service code, discretionary data - from the client's test card
```

<a id="d3"></a>

## Defect 3: The Terminal Identifier is a generated placeholder, yet the test cases are marked ready

**Priority:** High · **Kind:** Awaiting client input / run-time value, Test case issue to correct · **Test cases:** 17 shown (546 in the run) · **Defect id:** C-03, I-01

<details><summary>All 546 test cases of the run with this defect (click to expand)</summary>

[TC-077223](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1), [TC-077224](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L2), [TC-077225](#tc-077225), [TC-077226](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L4), [TC-077227](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L5), [TC-077228](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L6), [TC-077229](#tc-077229), [TC-077230](#tc-077230), [TC-077231](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L9), [TC-077233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L11), [TC-077234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L12), [TC-077235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L13), [TC-077236](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L14), [TC-077237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L15), [TC-077238](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L16), [TC-077239](#tc-077239), [TC-077240](#tc-077240), [TC-077241](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L19), [TC-077242](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L20), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077244](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L22), [TC-077245](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L23), [TC-077246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L24), [TC-077247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L25), [TC-077248](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L26), [TC-077249](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L27), [TC-077250](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L28), [TC-077251](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L29), [TC-077252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L30), [TC-077253](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L31), [TC-077254](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L32), [TC-077255](#tc-077255), [TC-077256](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L34), [TC-077257](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L35), [TC-077258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L36), [TC-077259](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L37), [TC-077260](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L38), [TC-077261](#tc-077261), [TC-077262](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L40), [TC-077263](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L41), [TC-077264](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L42), [TC-077265](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L43), [TC-077266](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L44), [TC-077267](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L45), [TC-077268](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L46), [TC-077269](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L47), [TC-077270](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L48), [TC-077271](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L49), [TC-077272](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L50), [TC-077273](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L51), [TC-077274](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L52), [TC-077275](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L53), [TC-077276](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L54), [TC-077277](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L55), [TC-077278](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L56), [TC-077279](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L57), [TC-077280](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L58), [TC-077281](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L59), [TC-077282](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L60), [TC-077283](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L61), [TC-077284](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L62), [TC-077285](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L63), [TC-077286](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L64), [TC-077287](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L65), [TC-077288](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L66), [TC-077289](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L67), [TC-077290](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L68), [TC-077291](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L69), [TC-077292](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L70), [TC-077293](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L71), [TC-077294](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L72), [TC-077295](#tc-077295), [TC-077296](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L74), [TC-077297](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L75), [TC-077298](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L76), [TC-077299](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L77), [TC-077300](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L78), [TC-077304](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L82), [TC-077305](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L83), [TC-077306](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L84), [TC-077307](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L85), [TC-077308](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L86), [TC-077309](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L87), [TC-077310](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L88), [TC-077311](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L89), [TC-077312](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L90), [TC-077313](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L91), [TC-077314](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L92), [TC-077315](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L93), [TC-077316](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L94), [TC-077317](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L95), [TC-077318](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L96), [TC-077319](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L97), [TC-077320](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L98), [TC-077321](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L99), [TC-077322](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L100), [TC-077323](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L101), [TC-077324](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L102), [TC-077325](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L103), [TC-077326](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L104), [TC-077327](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L105), [TC-077328](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L106), [TC-077329](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L107), [TC-077330](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L108), [TC-077331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L109), [TC-077332](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L110), [TC-077333](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L111), [TC-077334](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L112), [TC-077335](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L113), [TC-077336](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L114), [TC-077337](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L115), [TC-077338](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L116), [TC-077339](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L117), [TC-077340](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L118), [TC-077341](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L119), [TC-077342](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L120), [TC-077343](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L121), [TC-077344](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L122), [TC-077345](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L123), [TC-077346](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L124), [TC-077347](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L125), [TC-077348](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L126), [TC-077349](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L127), [TC-077350](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L128), [TC-077351](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L129), [TC-077352](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L130), [TC-077353](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L131), [TC-077354](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L132), [TC-077355](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L133), [TC-077356](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L134), [TC-077357](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L135), [TC-077360](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L138), [TC-077361](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L139), [TC-077362](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L140), [TC-077363](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L141), [TC-077364](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L142), [TC-077365](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L143), [TC-077366](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L144), [TC-077367](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L145), [TC-077368](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L146), [TC-077369](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L147), [TC-077370](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L148), [TC-077371](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L149), [TC-077372](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L150), [TC-077373](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L151), [TC-077374](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L152), [TC-077375](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L153), [TC-077376](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L154), [TC-077377](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L155), [TC-077378](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L156), [TC-077379](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L157), [TC-077380](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L158), [TC-077381](#tc-077381), [TC-077382](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L160), [TC-077383](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L161), [TC-077384](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L162), [TC-077385](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L163), [TC-077386](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L164), [TC-077387](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L165), [TC-077388](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L166), [TC-077389](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L167), [TC-077390](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L168), [TC-077391](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L169), [TC-077392](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L170), [TC-077393](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L171), [TC-077394](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L172), [TC-077395](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L173), [TC-077396](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L174), [TC-077397](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L175), [TC-077398](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L176), [TC-077399](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L177), [TC-077400](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L178), [TC-077401](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L179), [TC-077402](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L180), [TC-077403](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L181), [TC-077404](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L182), [TC-077405](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L183), [TC-077406](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L184), [TC-077407](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L185), [TC-077408](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L186), [TC-077411](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L189), [TC-077412](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L190), [TC-077413](#tc-077413), [TC-077414](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L192), [TC-077415](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L193), [TC-077416](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L194), [TC-077417](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L195), [TC-077418](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L196), [TC-077419](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L197), [TC-077421](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L199), [TC-077422](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L200), [TC-077423](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L201), [TC-077424](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L202), [TC-077425](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L203), [TC-077426](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L204), [TC-077427](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L205), [TC-077428](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L206), [TC-077429](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L207), [TC-077430](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L208), [TC-077431](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L209), [TC-077432](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L210), [TC-077433](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L211), [TC-077434](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L212), [TC-077435](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L213), [TC-077436](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L214), [TC-077437](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L215), [TC-077438](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L216), [TC-077439](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L217), [TC-077440](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L218), [TC-077441](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L219), [TC-077442](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L220), [TC-077443](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L221), [TC-077444](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L222), [TC-077445](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L223), [TC-077446](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L224), [TC-077447](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L225), [TC-077448](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L226), [TC-077449](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L227), [TC-077450](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L228), [TC-077451](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L229), [TC-077452](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L230), [TC-077453](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L231), [TC-077454](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L232), [TC-077455](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L233), [TC-077456](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L234), [TC-077457](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L235), [TC-077458](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L236), [TC-077459](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L237), [TC-077460](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L238), [TC-077461](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L239), [TC-077462](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L240), [TC-077463](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L241), [TC-077464](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L242), [TC-077465](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L243), [TC-077466](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L244), [TC-077467](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L245), [TC-077468](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L246), [TC-077469](#tc-077469), [TC-077470](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L248), [TC-077471](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L249), [TC-077472](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L250), [TC-077473](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L251), [TC-077474](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L252), [TC-077475](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L253), [TC-077476](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L254), [TC-077477](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L255), [TC-077478](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L256), [TC-077479](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L257), [TC-077480](#tc-077480), [TC-077481](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L259) and 296 more - every one is in [Defects_in_the_run.csv](Defects_in_the_run.csv)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077239](#tc-077239) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data one byte over its documented maximum length (51) | Card Discretionary Block Data one byte over its documented maximum length (51) | [TD-TC-077239.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json) |
| [TC-077295](#tc-077295) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73)) | `needs_client_input` | Standard Message Data Segment: Segment Length one more than its rendered bytes | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| [TC-077381](#tc-077381) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159)) | `requires_review` | Purchase Card Data Segment: Segment Type differs from its fixed value '104' | Segment Type differs from its fixed value '104' | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) |
| [TC-077469](#tc-077469) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247)) | `ready` | Totals Data Segment: Card Label one byte over its documented maximum length (4) | Card Label one byte over its documented maximum length (4) | [TD-TC-077469.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) |
| [TC-077225](#tc-077225) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3)) | `ready` | Standard Message Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077225.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json) |
| [TC-077230](#tc-077230) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8)) | `ready` | Standard Message Data Segment: Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | [TD-TC-077230.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json) |
| [TC-077240](#tc-077240) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | [TD-TC-077240.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json) |
| [TC-077255](#tc-077255) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33)) | `ready` | Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) |
| [TC-077261](#tc-077261) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39)) | `blocked_verification` | Standard Message Data Segment: Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077261.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json) |
| [TC-078056](#tc-078056) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834)) | `needs_client_input` | Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted | Maximal field-inclusion profile request is accepted | [TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) |
| [TC-078069](#tc-078069) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847)) | `ready` | Financial Transaction Request – Message structure: SKU Data Segment moved first | segment 114 SKU Data Segment moved first | [TD-TC-078069.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json) |
| [TC-077849](#tc-077849) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627)) | `pending_sme` | NFC Payment Tokenization Data Segment: Cryptogram Token Data value not in its documented valid values | Cryptogram Token Data value not in its documented valid values | [TD-TC-077849.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json) |
| + 5 more: [TC-077229](#tc-077229), [TC-078058](#tc-078058), [TC-077413](#tc-077413), [TC-077480](#tc-077480), [TC-077628](#tc-077628) | | | | |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077239 | - | [TD-TC-077239.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077239 | - | test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077295 | - | [TD-TC-077295.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077381 | - | [TD-TC-077381.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `WI28888556001` | The Terminal Identifier is a placeholder |
| TC-077469 | - |  → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077469 | - | test case record [test_cases.jsonl line 247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077225 | - | [TD-TC-077225.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077225 | - | test case record [test_cases.jsonl line 3](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077230 | - | [TD-TC-077230.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077230 | - | test case record [test_cases.jsonl line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077240 | - | [TD-TC-077240.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077240 | - | test case record [test_cases.jsonl line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077255 | - | [TD-TC-077255.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077255 | - | test case record [test_cases.jsonl line 33](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077261 | - | [TD-TC-077261.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-078056 | - | [TD-TC-078056.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-078069 | - | [TD-TC-078069.json line 14](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json#L14) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-078069 | - | test case record [test_cases.jsonl line 847](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077849 | - | [TD-TC-077849.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-077229 | - | [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `AAAAAAAAAAAAAAAAAAAAAAA` | The Terminal Identifier is a placeholder |
| TC-077229 | - | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-078058 | - | [TD-TC-078058.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-078058 | - | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-077413 | - |  → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| + 3 more with the same kind of finding: [TC-077413](#tc-077413), [TC-077480](#tc-077480), [TC-077628](#tc-077628) | | | | |

### Expected behavior

- Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier.
- `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied.

### Why it matters

- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX41894821001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '41' = Oregon (OR), United States.
- The test case status is 'ready', but its Terminal Identifier is still a placeholder.
- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'WI28888556001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '28' = Mississippi (MS), United States.
- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'AAAAAAAAAAAAAAAAAAAAAAA' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) 'AA' = not a valid State Code.

**BA explanation:** "Every request uses a made-up terminal. BUYPASS only accepts terminals it has set up, so these tests cannot run until the real Terminal ID is supplied - and they should not be 'ready' until then."

### Acceptance criteria

- Client / BUYPASS supply the real Terminal Identifier of the merchant's terminal; every step uses it and the Segment 100 length is recalculated.
- A test case with a placeholder terminal has a status other than 'ready'.
- A regression check flags 'ready' test cases whose merchant is a placeholder.

**Specification reference (ATL105, word for word):**

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." ([ATL105 text line 23795](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23795))
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)." ([ATL105 text line 23816](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23816))
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Oregon OR 41" ([ATL105 text line 28244](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L28244))
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Mississippi MS 28" ([ATL105 text line 28231](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L28231))

**Test data excerpt - [TC-077239](#tc-077239), step 1, `TD-TC-077239.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "118",
// expected
"SegmentLength": "118",  // I-01: recalculate: 105 + length of the real Terminal Identifier
// actual - line 9
"TerminalIdentifier": "JX41894821001",
// expected
"TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
```

<a id="d4"></a>

## Defect 4: Positive boundary test: the value is not at the documented limit or is not a valid value

**Priority:** High · **Kind:** Test data defect · **Test cases:** 6 shown (33 in the run) · **Defect id:** D-15

<details><summary>All 33 test cases of the run with this defect (click to expand)</summary>

[TC-077225](#tc-077225), [TC-077230](#tc-077230), [TC-077237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L15), [TC-077240](#tc-077240), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L24), [TC-077252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L30), [TC-077255](#tc-077255), [TC-077256](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L34), [TC-077258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L36), [TC-077261](#tc-077261), [TC-077263](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L41), [TC-077267](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L45), [TC-077268](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L46), [TC-077270](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L48), [TC-077413](#tc-077413), [TC-077418](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L196), [TC-077430](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L208), [TC-077555](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L333), [TC-077598](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L376), [TC-077620](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L398), [TC-077621](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L399), [TC-077659](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L437), [TC-077698](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L476), [TC-077750](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L528), [TC-077818](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L596), [TC-077888](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L666), [TC-077933](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L711), [TC-077977](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L755), [TC-077983](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L761), [TC-078005](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L783), [TC-078011](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L789), [TC-078031](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L809)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077225](#tc-077225) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3)) | `ready` | Standard Message Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077225.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json) |
| [TC-077230](#tc-077230) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8)) | `ready` | Standard Message Data Segment: Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | [TD-TC-077230.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json) |
| [TC-077240](#tc-077240) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | [TD-TC-077240.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json) |
| [TC-077255](#tc-077255) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33)) | `ready` | Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) |
| [TC-077261](#tc-077261) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39)) | `blocked_verification` | Standard Message Data Segment: Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077261.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json) |
| [TC-077413](#tc-077413) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191)) | `ready` | Totals Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077413.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077225 | 1 | [TD-TC-077225.json line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L8) → `Standard Message Data Segment → InformationByte` | `A` | Information Byte is not valid |
| TC-077230 | 1 | [TD-TC-077230.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | Terminal Identifier is not at its documented maximum length |
| TC-077240 | 1 | [TD-TC-077240.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `1030` | Card Discretionary Block Data is not valid |
| TC-077240 | 1 | [TD-TC-077240.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `1030` | Card Discretionary Block Data is not at its documented maximum length |
| TC-077255 | 1 | [TD-TC-077255.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L18) → `Standard Message Data Segment → FuelPurchaseAmount` | `00000001` | Fuel Purchase Amount is not valid |
| TC-077261 | 1 | [TD-TC-077261.json line 19](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L19) → `Standard Message Data Segment → NonfuelAmount` | `00000001` | Nonfuel Amount is not valid |
| TC-077261 | 1 | test case record [test_cases.jsonl line 39](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) → `rule_checks LEG-NONFUEL-AMOUNT-MAX-LENGTH (step 1)` | `rule_checks LEG-NONFUEL-AMOUNT-MAX-LENGTH (step 1)` = `null` | Test data does not meet the test case's own rule check LEG-NONFUEL-AMOUNT-MAX-LENGTH |
| TC-077413 | 1 | [TD-TC-077413.json line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json#L8) → `Totals Data Segment → InformationByte` | `A` | Information Byte (Totals) is not valid |

### Expected behavior

- A value that meets: 1 character: 'A' = not a valid code.
- A valid Terminal Identifier of exactly 22 characters, or drop the test if no such value can be approved.
- A value that meets: AN, variable length 51.
- A valid Card Discretionary Block Data of exactly 51 characters, or drop the test if no such value can be approved.
- A value that meets: N, variable length 7 with two assumed decimals: '00000001' = $0.01.
- A value that meets: Digits only, at most 7 for MasterCard (8 only for Amex): '00000001' = $0.01.
- Correct the test data (or the rule check) so that the assertion holds.
- A value that meets: 'A' = not valid.

### Why this is a defect

- 'A' is not a valid Information Byte (?, 0 or 1).
- The test is meant to send Terminal Identifier at exactly 22 characters, but 'JX41894821001' has 13, so the boundary is not tested.
- '1030' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.
- The test is meant to send Card Discretionary Block Data at exactly 51 characters, but '1030' has 4, so the boundary is not tested.
- '00000001' is 8 characters long; the field allows at most 7.
- The test case asserts 'Nonfuel Amount maximum length 7 for this network (p.393: A maximum length of 7 bytes is allowed for all other card types.)' (value_length_max, expect hold), but in the test data: Nonfuel Amount '00000001' has 8 characters, maximum 7.

### Acceptance criteria

- Send a valid value of exactly the documented maximum length; if no such value can be approved (a MasterCard card number is 16 digits, not 24), drop the positive boundary test.

**Specification reference (ATL105, word for word):**

- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission" ([ATL105 text line 22405](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22405))
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character." ([ATL105 text line 21712](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21712))
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2." ([ATL105 text line 21710](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21710))
- Element 41 Fuel Purchase Amount (Chapter 13), PDF p.385 (printed 13-45): "Representation: Variable length of up to eight digits with two assumed decimal places" ([ATL105 text line 22330](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22330))
- Element 41 Fuel Purchase Amount (Chapter 13), PDF p.385 (printed 13-45): "Included on any transactions with fuel sales." ([ATL105 text line 22349](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22349))
- Element 58 Nonfuel Amount (Chapter 13), PDF p.393 (printed 13-53): "A maximum length of 7 bytes is allowed for all other card types." ([ATL105 text line 22691](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22691))

**Test data excerpt - [TC-077225](#tc-077225), step 1, `TD-TC-077225.json` (selected fields, actual then expected):**

```jsonc
// actual - line 8
"InformationByte": "A",
// expected
"InformationByte": "A",  // D-15: A value that meets: 1 character: 'A' = not a valid code.
```

<a id="d5"></a>

## Defect 5: Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply

**Priority:** High · **Kind:** Test data defect · **Test cases:** 2 shown (8 in the run) · **Defect id:** D-02

<details><summary>All 8 test cases of the run with this defect (click to expand)</summary>

[TC-077249](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L27), [TC-077250](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L28), [TC-077255](#tc-077255), [TC-077256](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L34), [TC-077284](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L62), [TC-077288](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L66), [TC-078056](#tc-078056), [TC-078112](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L890)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077255](#tc-077255) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33)) | `ready` | Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) |
| [TC-078056](#tc-078056) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834)) | `needs_client_input` | Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted | Maximal field-inclusion profile request is accepted | [TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077255 | 1 | [TD-TC-077255.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L18) → `Standard Message Data Segment → FuelPurchaseAmount` | Fuel Purchase Amount '00000001' at a general (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-078056 | 1 | [TD-TC-078056.json line 24](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L24), [TD-TC-078056.json line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L17), [TD-TC-078056.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' (Element 49 identifies the date/time of a preauthorized transaction; this step is not one); Pump/Lane Number '1' at a general (non-petroleum) merchant; Fuel Purchase Amount '1' at a general (non-petroleum) merchant | Conditional fields sent although their condition does not apply |

### Expected behavior

- Send conditional fields only when their condition holds.

### Why this is a defect

- Fuel Purchase Amount '00000001' at a general (non-petroleum) merchant.
- Local Date and Local Time '0615251430' (Element 49 identifies the date/time of a preauthorized transaction; this step is not one); Pump/Lane Number '1' at a general (non-petroleum) merchant; Fuel Purchase Amount '1' at a general (non-petroleum) merchant.

### Acceptance criteria

- Send only the conditional fields whose condition holds: no Approval Number / Local Date and Time on a new authorization, a real DUKPT KSN + PIN block (32-36 characters) or nothing in Element 33, no fuel or pump data outside petroleum.

**Specification reference (ATL105, word for word):**

- Section 12.1 Standard Message Data Segment, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction." ([ATL105 text line 12991](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L12991))

**Test data excerpt - [TC-077255](#tc-077255), step 1, `TD-TC-077255.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "080",
// expected
"SegmentLength": "080",  // D-13/I-01/D-02: recalculate: 055 + length of the track data + length of the real Terminal Identifier
// actual - line 18
"FuelPurchaseAmount": "00000001",
// expected
"FuelPurchaseAmount": "",  // D-15: A value that meets: N, variable length 7 with two assumed decimals: '00000001' = $0.01.; D-02: empty - fuel field at a non-petroleum merchant
```

<a id="d6"></a>

## Defect 6: Same request as another test case but a different expected result

**Priority:** High · **Kind:** Test case defect · **Test cases:** 2 shown (4 in the run) · **Defect id:** C-01

<details><summary>All 4 test cases of the run with this defect (click to expand)</summary>

[TC-077849](#tc-077849), [TC-077852](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L630), [TC-077865](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L643), [TC-078069](#tc-078069)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078069](#tc-078069) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847)) | `ready` | Financial Transaction Request – Message structure: SKU Data Segment moved first | segment 114 SKU Data Segment moved first | [TD-TC-078069.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json) |
| [TC-077849](#tc-077849) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627)) | `pending_sme` | NFC Payment Tokenization Data Segment: Cryptogram Token Data value not in its documented valid values | Cryptogram Token Data value not in its documented valid values | [TD-TC-077849.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078069 | 1 | TD-TC-078069.json vs TD-TC-078060.json | `expected 1 vs 0` | Same request as TC-078060 (BRDE_TS22_TC6) |
| TC-077849 | 1 | TD-TC-077849.json vs TD-TC-077848.json | `expected 1 vs 0` | Same request as TC-077848 (BRDE_TS12_TC2) |

### Expected behavior

- Make the request of the decline test different (decline test card, amount or simulator rule).

### Why this is a defect

- Apart from the Sequence Number the message is identical to TC-078060 (all segments compared). TC-078060 expects 0; this test expects 1.

### Acceptance criteria

- Give the decline test its own trigger (decline test card / amount / simulator rule) so its request differs.

<a id="d7"></a>

## Defect 7: 'Different terminal' negative tests use a terminal BUYPASS does not know

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (18 in the run) · **Defect id:** C-07

<details><summary>All 18 test cases of the run with this defect (click to expand)</summary>

[TC-077229](#tc-077229), [TC-077231](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L9), [TC-077232](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L10), [TC-077301](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L79), [TC-077302](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L80), [TC-077303](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L81), [TC-078050](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L828), [TC-078063](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L841), [TC-078075](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L853), [TC-078076](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L854), [TC-078077](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L855), [TC-078078](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L856), [TC-078079](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L857), [TC-078101](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L879), [TC-078118](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L896), [TC-078126](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L904), [TC-078127](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L905), [TC-078128](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L906)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077229](#tc-077229) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7)) | `ready` | Standard Message Data Segment: Terminal Identifier one byte over its documented maximum length (22) | Terminal Identifier one byte over its documented maximum length (22) | [TD-TC-077229.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077229 | 1 | [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `AAAAAAAAAAAAAAAAAAAAAAA` | The 'different terminal' is a generated value not set up at BUYPASS |

### Expected behavior

- Use a second real terminal of the same merchant.

### Why it matters

- Step 1 uses the generated Terminal Identifier 'AAAAAAAAAAAAAAAAAAAAAAA' (method deliberate_violation_length). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

**BA explanation:** "To prove that a follow-on from another device is declined, the other device must be a real terminal of the same merchant. With a made-up terminal the decline only proves that unknown terminals are rejected."

### Acceptance criteria

- The follow-on step uses a second real Terminal Identifier of the same merchant, assigned by BUYPASS.

**Specification reference (ATL105, word for word):**

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." ([ATL105 text line 23795](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23795))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." ([ATL105 text line 2181](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L2181))

**Test data excerpt - [TC-077229](#tc-077229), step 1, `TD-TC-077229.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "082",
// expected
"SegmentLength": "082",  // D-13/I-01/C-07: recalculate: 055 + length of the track data + length of the real Terminal Identifier
// actual - line 9
"TerminalIdentifier": "AAAAAAAAAAAAAAAAAAAAAAA",
// expected
"TerminalIdentifier": "<second Terminal Identifier of the same merchant, assigned by BUYPASS>",  // N-01: different from step None ('A value that meets: Device Type 'AA' + State Code 'AA' (not in Appendix D) + Merchant Number and Device Number 'AAAAAAAAAAAAAAAAAAA'.') on purpose - the rule this negative test breaks; keep; I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal; C-07: a second real terminal of the same merchant (still different from step None)
```

<a id="d8"></a>

## Defect 8: Network token data (Segment 153) is synthesized, and its indicator and length need confirmation

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 1 shown (4 in the run) · **Defect id:** I-06

<details><summary>All 4 test cases of the run with this defect (click to expand)</summary>

[TC-078058](#tc-078058), [TC-078062](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L840), [TC-078114](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L892), [TC-078117](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L895)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078058](#tc-078058) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836)) | `ready` | Financial Transaction Request – Message structure: 104 Purchase Card Data Segment present | 104 Purchase Card Data Segment present | [TD-TC-078058.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078058 | 1 | [TD-TC-078058.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L27) → `Purchase Card Data Segment` | `format_synthesis` | Purchase Card Data Segment: content is synthesized, not client data |

### Expected behavior

- Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then.

### Why it matters

- The test case generated the Segment 104 values itself (format_synthesis); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: a purchase (commercial) card and its purchase-card data.

**BA explanation:** "The network token was made up by the generator, the token flag says 'token' while the card number is sent in Element 2, and the segment length depends on a Field Separator rule ATL105 does not state clearly."

### Acceptance criteria

- Client supplies a real provisioned token (table 001) with its expiry (table 002).
- BUYPASS confirms which field carries the token when table 004 = Y, and the Segment 153 length rule.

**Specification reference (ATL105, word for word):**

- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on transactions requiring purchase card data." ([ATL105 text line 7976](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L7976))

**Test data excerpt - [TC-078058](#tc-078058), step 1, `TD-TC-078058.json` (selected fields, actual then expected):**

```jsonc
// actual - line 27
"Purchase Card Data Segment": {
// expected
"Purchase Card Data Segment": {  // I-06: Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then.
```

<a id="d9"></a>

## Defect 9: Follow-on values are instruction text to be filled in from the previous response

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 1 shown (19 in the run) · **Defect id:** I-02

<details><summary>All 19 test cases of the run with this defect (click to expand)</summary>

[TC-077233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L11), [TC-077234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L12), [TC-077235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L13), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077304](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L82), [TC-077305](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L83), [TC-077306](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L84), [TC-078051](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L829), [TC-078056](#tc-078056), [TC-078080](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L858), [TC-078081](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L859), [TC-078082](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L860), [TC-078083](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L861), [TC-078084](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L862), [TC-078102](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L880), [TC-078112](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L890), [TC-078129](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L907), [TC-078130](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L908), [TC-078131](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L909)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078056](#tc-078056) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834)) | `needs_client_input` | Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted | Maximal field-inclusion profile request is accepted | [TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078056 | 1 | [TD-TC-078056.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L7) → `Standard Message Data Segment → SegmentLength` | `086` | Segment Length must be recalculated at run time |

### Expected behavior

- Recalculate the Segment Length after filling in the real values.

### Why it matters

- '086' depends on the run-time value(s) Encrypted PIN Block Data.

**BA explanation:** "The follow-on must carry the approval number the host returns for the original. The file holds an instruction text; the test harness must substitute the real value at run time."

### Acceptance criteria

- The harness replaces the instruction text with the value from the previous response before sending.
- The Segment 100 length is recalculated after the substitution.

**Specification reference (ATL105, word for word):**

- Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators." ([ATL105 text line 12859](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L12859))
- Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements." ([ATL105 text line 23312](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23312))

**Test data excerpt - [TC-078056](#tc-078056), step 1, `TD-TC-078056.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "086",
// expected
"SegmentLength": "086",  // I-02: Recalculate the Segment Length after filling in the real values.; D-13/I-01/D-02: recalculate: 089 + length of the track data + length of the real Terminal Identifier
```

<a id="d10"></a>

## Defect 10: EMV chip data is generated, not captured from a card or simulator

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 9 shown (391 in the run) · **Defect id:** I-04

<details><summary>All 391 test cases of the run with this defect (click to expand)</summary>

[TC-077223](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1), [TC-077224](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L2), [TC-077225](#tc-077225), [TC-077226](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L4), [TC-077227](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L5), [TC-077228](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L6), [TC-077229](#tc-077229), [TC-077230](#tc-077230), [TC-077231](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L9), [TC-077232](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L10), [TC-077233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L11), [TC-077234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L12), [TC-077235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L13), [TC-077236](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L14), [TC-077237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L15), [TC-077238](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L16), [TC-077239](#tc-077239), [TC-077240](#tc-077240), [TC-077241](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L19), [TC-077242](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L20), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077244](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L22), [TC-077245](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L23), [TC-077246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L24), [TC-077247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L25), [TC-077248](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L26), [TC-077249](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L27), [TC-077250](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L28), [TC-077251](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L29), [TC-077252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L30), [TC-077253](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L31), [TC-077254](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L32), [TC-077255](#tc-077255), [TC-077256](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L34), [TC-077257](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L35), [TC-077258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L36), [TC-077259](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L37), [TC-077260](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L38), [TC-077261](#tc-077261), [TC-077262](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L40), [TC-077263](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L41), [TC-077264](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L42), [TC-077265](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L43), [TC-077266](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L44), [TC-077267](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L45), [TC-077268](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L46), [TC-077269](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L47), [TC-077270](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L48), [TC-077271](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L49), [TC-077272](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L50), [TC-077273](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L51), [TC-077274](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L52), [TC-077275](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L53), [TC-077276](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L54), [TC-077277](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L55), [TC-077278](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L56), [TC-077279](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L57), [TC-077280](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L58), [TC-077281](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L59), [TC-077282](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L60), [TC-077283](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L61), [TC-077284](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L62), [TC-077285](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L63), [TC-077286](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L64), [TC-077287](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L65), [TC-077288](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L66), [TC-077289](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L67), [TC-077290](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L68), [TC-077291](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L69), [TC-077292](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L70), [TC-077293](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L71), [TC-077294](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L72), [TC-077295](#tc-077295), [TC-077296](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L74), [TC-077298](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L76), [TC-077299](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L77), [TC-077300](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L78), [TC-077301](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L79), [TC-077302](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L80), [TC-077303](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L81), [TC-077304](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L82), [TC-077305](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L83), [TC-077306](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L84), [TC-077307](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L85), [TC-077308](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L86), [TC-077309](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L87), [TC-077310](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L88), [TC-077311](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L89), [TC-077312](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L90), [TC-077313](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L91), [TC-077314](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L92), [TC-077315](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L93), [TC-077316](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L94), [TC-077317](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L95), [TC-077318](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L96), [TC-077319](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L97), [TC-077320](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L98), [TC-077321](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L99), [TC-077322](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L100), [TC-077323](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L101), [TC-077324](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L102), [TC-077325](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L103), [TC-077326](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L104), [TC-077327](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L105), [TC-077328](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L106), [TC-077329](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L107), [TC-077330](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L108), [TC-077331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L109), [TC-077332](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L110), [TC-077333](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L111), [TC-077334](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L112), [TC-077335](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L113), [TC-077336](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L114), [TC-077337](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L115), [TC-077338](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L116), [TC-077339](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L117), [TC-077340](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L118), [TC-077341](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L119), [TC-077342](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L120), [TC-077343](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L121), [TC-077344](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L122), [TC-077345](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L123), [TC-077346](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L124), [TC-077347](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L125), [TC-077348](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L126), [TC-077349](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L127), [TC-077350](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L128), [TC-077351](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L129), [TC-077352](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L130), [TC-077353](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L131), [TC-077354](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L132), [TC-077355](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L133), [TC-077356](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L134), [TC-077357](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L135), [TC-077360](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L138), [TC-077361](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L139), [TC-077362](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L140), [TC-077363](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L141), [TC-077364](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L142), [TC-077365](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L143), [TC-077366](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L144), [TC-077367](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L145), [TC-077368](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L146), [TC-077369](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L147), [TC-077370](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L148), [TC-077371](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L149), [TC-077372](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L150), [TC-077373](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L151), [TC-077374](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L152), [TC-077375](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L153), [TC-077376](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L154), [TC-077377](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L155), [TC-077378](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L156), [TC-077379](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L157), [TC-077380](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L158), [TC-077381](#tc-077381), [TC-077382](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L160), [TC-077383](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L161), [TC-077384](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L162), [TC-077385](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L163), [TC-077386](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L164), [TC-077387](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L165), [TC-077388](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L166), [TC-077389](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L167), [TC-077390](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L168), [TC-077391](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L169), [TC-077392](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L170), [TC-077393](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L171), [TC-077394](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L172), [TC-077395](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L173), [TC-077396](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L174), [TC-077397](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L175), [TC-077398](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L176), [TC-077399](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L177), [TC-077400](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L178), [TC-077401](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L179), [TC-077402](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L180), [TC-077403](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L181), [TC-077404](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L182), [TC-077405](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L183), [TC-077406](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L184), [TC-077407](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L185), [TC-077408](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L186), [TC-077492](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L270), [TC-077493](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L271), [TC-077494](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L272), [TC-077495](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L273), [TC-077496](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L274), [TC-077497](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L275), [TC-077498](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L276), [TC-077499](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L277), [TC-077500](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L278), [TC-077501](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L279), [TC-077502](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L280), [TC-077503](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L281), [TC-077504](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L282), [TC-077505](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L283), [TC-077506](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L284), [TC-077507](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L285), [TC-077508](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L286), [TC-077509](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L287), [TC-077510](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L288), [TC-077511](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L289), [TC-077512](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L290), [TC-077513](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L291), [TC-077514](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L292), [TC-077515](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L293), [TC-077516](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L294), [TC-077517](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L295), [TC-077518](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L296), [TC-077519](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L297), [TC-077520](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L298), [TC-077521](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L299), [TC-077522](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L300), [TC-077523](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L301), [TC-077524](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L302), [TC-077525](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L303), [TC-077526](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L304), [TC-077527](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L305), [TC-077528](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L306), [TC-077529](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L307), [TC-077530](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L308), [TC-077531](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L309), [TC-077532](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L310), [TC-077533](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L311), [TC-077534](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L312), [TC-077535](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L313), [TC-077536](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L314), [TC-077537](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L315), [TC-077538](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L316), [TC-077539](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L317), [TC-077542](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L320), [TC-077543](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L321), [TC-077544](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L322), [TC-077545](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L323), [TC-077546](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L324), [TC-077547](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L325), [TC-077609](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L387), [TC-077610](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L388), [TC-077611](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L389), [TC-077612](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L390), [TC-077613](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L391), [TC-077614](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L392), [TC-077615](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L393), [TC-077616](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L394), [TC-077617](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L395), [TC-077618](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L396), [TC-077619](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L397), [TC-077620](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L398), [TC-077621](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L399) and 141 more - every one is in [Defects_in_the_run.csv](Defects_in_the_run.csv)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077239](#tc-077239) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data one byte over its documented maximum length (51) | Card Discretionary Block Data one byte over its documented maximum length (51) | [TD-TC-077239.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json) |
| [TC-077295](#tc-077295) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73)) | `needs_client_input` | Standard Message Data Segment: Segment Length one more than its rendered bytes | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| [TC-077381](#tc-077381) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159)) | `requires_review` | Purchase Card Data Segment: Segment Type differs from its fixed value '104' | Segment Type differs from its fixed value '104' | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) |
| [TC-077225](#tc-077225) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3)) | `ready` | Standard Message Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077225.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json) |
| [TC-077230](#tc-077230) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8)) | `ready` | Standard Message Data Segment: Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | [TD-TC-077230.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json) |
| [TC-077240](#tc-077240) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | [TD-TC-077240.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json) |
| [TC-077255](#tc-077255) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33)) | `ready` | Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) |
| [TC-077261](#tc-077261) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39)) | `blocked_verification` | Standard Message Data Segment: Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077261.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json) |
| [TC-077229](#tc-077229) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7)) | `ready` | Standard Message Data Segment: Terminal Identifier one byte over its documented maximum length (22) | Terminal Identifier one byte over its documented maximum length (22) | [TD-TC-077229.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077239 | - | [TD-TC-077239.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077239 | - | [TD-TC-077239.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=9BDD6CCFC3C2EE60, 9F36=1AD3, 9F37=F57376F6, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077295 | - | [TD-TC-077295.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077295 | - | [TD-TC-077295.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=6F1D5B4CA7CC2180, 9F36=9DED, 9F37=BC8BBA14, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077381 | - | [TD-TC-077381.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077381 | - | [TD-TC-077381.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=07F42E09136159DF, 9F36=E5C6, 9F37=AC4BE2A3, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077225 | - | [TD-TC-077225.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077225 | - | [TD-TC-077225.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=10E40F248A144726, 9F36=474D, 9F37=32180A66, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077230 | - | [TD-TC-077230.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077230 | - | [TD-TC-077230.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=077BCB0AACD5A10C, 9F36=1A07, 9F37=291CF6D3, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077240 | - | [TD-TC-077240.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077240 | - | [TD-TC-077240.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=17C4BEB2294FCE91, 9F36=00E8, 9F37=4502368A, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077255 | - | [TD-TC-077255.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077255 | - | [TD-TC-077255.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=D2DA1E1BF0755E0D, 9F36=4A53, 9F37=197ABC64, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077261 | - | [TD-TC-077261.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077261 | - | [TD-TC-077261.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=D32061BEEC05FFB2, 9F36=41D2, 9F37=2CFA7590, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-077229 | - | [TD-TC-077229.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-077229 | - | [TD-TC-077229.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=42B3EFDEDF06EDD1, 9F36=4BB2, 9F37=EA771E6A, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |

### Expected behavior

- Confirm the checksum of the host's current CA Public Key file before execution.
- Produce the chip data with a card simulator / HSM before execution.

### Why it matters

- The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.

**BA explanation:** "Cryptogram, counters and terminal results in the chip data are generated values. The issuer cannot validate them, so the test needs chip data from the client's test card or simulator."

### Acceptance criteria

- Element 190 comes from the client's test card or simulator; the CA Public Key checksum comes from the current key load.

**Specification reference (ATL105, word for word):**

- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device." ([ATL105 text line 26381](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L26381))
- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface." ([ATL105 text line 26438](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L26438))

<a id="d11"></a>

## Defect 11: Segment 100 value awaited from the client (<AWAITING:...>)

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 2 shown (21 in the run) · **Defect id:** I-09

<details><summary>All 21 test cases of the run with this defect (click to expand)</summary>

[TC-077233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L11), [TC-077234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L12), [TC-077235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L13), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077295](#tc-077295), [TC-077296](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L74), [TC-077304](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L82), [TC-077305](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L83), [TC-077306](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L84), [TC-078051](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L829), [TC-078056](#tc-078056), [TC-078080](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L858), [TC-078081](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L859), [TC-078082](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L860), [TC-078083](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L861), [TC-078084](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L862), [TC-078102](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L880), [TC-078112](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L890), [TC-078129](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L907), [TC-078130](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L908), [TC-078131](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L909)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077295](#tc-077295) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73)) | `needs_client_input` | Standard Message Data Segment: Segment Length one more than its rendered bytes | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| [TC-078056](#tc-078056) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834)) | `needs_client_input` | Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted | Maximal field-inclusion profile request is accepted | [TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077295 | 1 | [TD-TC-077295.json line 16](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `<AWAITING:EncryptedPINBlockData>` | Encrypted PIN Block Data holds instruction text, not a value |
| TC-078056 | 1 | [TD-TC-078056.json line 16](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `<AWAITING:EncryptedPINBlockData>` | Encrypted PIN Block Data holds instruction text, not a value |

### Expected behavior

- Client to supply the value (for Account Number: a MasterCard test card).

### Why it matters

- The field holds '<AWAITING:EncryptedPINBlockData>' instead of a value: the generator found no valid value and waits for a client value (method awaiting_client_value).

### Acceptance criteria

- Client to supply the value (for Account Number: a MasterCard test card).

**Test data excerpt - [TC-077295](#tc-077295), step 1, `TD-TC-077295.json` (selected fields, actual then expected):**

```jsonc
// actual - line 16
"EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",
// expected
"EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",  // I-09: Client to supply the value (for Account Number: a MasterCard test card).
```

<a id="d12"></a>

## Defect 12: Totals Request device versions are generated placeholders

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 3 shown (86 in the run) · **Defect id:** I-05

<details><summary>All 86 test cases of the run with this defect (click to expand)</summary>

[TC-077411](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L189), [TC-077412](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L190), [TC-077413](#tc-077413), [TC-077414](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L192), [TC-077415](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L193), [TC-077416](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L194), [TC-077417](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L195), [TC-077418](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L196), [TC-077419](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L197), [TC-077420](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L198), [TC-077421](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L199), [TC-077422](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L200), [TC-077423](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L201), [TC-077424](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L202), [TC-077425](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L203), [TC-077426](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L204), [TC-077427](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L205), [TC-077428](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L206), [TC-077429](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L207), [TC-077430](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L208), [TC-077431](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L209), [TC-077432](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L210), [TC-077433](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L211), [TC-077434](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L212), [TC-077435](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L213), [TC-077436](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L214), [TC-077437](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L215), [TC-077438](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L216), [TC-077439](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L217), [TC-077440](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L218), [TC-077441](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L219), [TC-077442](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L220), [TC-077443](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L221), [TC-077444](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L222), [TC-077445](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L223), [TC-077446](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L224), [TC-077447](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L225), [TC-077448](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L226), [TC-077449](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L227), [TC-077450](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L228), [TC-077451](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L229), [TC-077452](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L230), [TC-077453](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L231), [TC-077454](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L232), [TC-077455](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L233), [TC-077456](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L234), [TC-077457](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L235), [TC-077458](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L236), [TC-077459](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L237), [TC-077460](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L238), [TC-077461](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L239), [TC-077462](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L240), [TC-077463](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L241), [TC-077464](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L242), [TC-077465](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L243), [TC-077466](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L244), [TC-077467](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L245), [TC-077468](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L246), [TC-077469](#tc-077469), [TC-077470](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L248), [TC-077471](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L249), [TC-077472](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L250), [TC-077473](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L251), [TC-077474](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L252), [TC-077475](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L253), [TC-077476](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L254), [TC-077477](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L255), [TC-077478](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L256), [TC-077479](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L257), [TC-077480](#tc-077480), [TC-077481](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L259), [TC-077482](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L260), [TC-077483](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L261), [TC-077484](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L262), [TC-077485](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L263), [TC-077486](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L264), [TC-077487](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L265), [TC-077488](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L266), [TC-077489](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L267), [TC-077490](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L268), [TC-077491](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L269), [TC-078156](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L934), [TC-078157](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L935), [TC-078159](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L937), [TC-078160](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L938), [TC-078161](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L939)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077469](#tc-077469) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247)) | `ready` | Totals Data Segment: Card Label one byte over its documented maximum length (4) | Card Label one byte over its documented maximum length (4) | [TD-TC-077469.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) |
| [TC-077413](#tc-077413) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191)) | `ready` | Totals Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077413.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json) |
| [TC-077480](#tc-077480) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258)) | `ready` | Totals Data Segment: Card Type Total Count one below its documented range (00001-99999) | Card Type Total Count one below its documented range (00001-99999) | [TD-TC-077480.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077469 | 1 | [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment` | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | The device version fields of the Totals Request are generated values |
| TC-077413 | 1 | [TD-TC-077413.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json#L5) → `Totals Data Segment` | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | The device version fields of the Totals Request are generated values |
| TC-077480 | 1 | [TD-TC-077480.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L5) → `Totals Data Segment` | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | The device version fields of the Totals Request are generated values |

### Expected behavior

- Use the versions of the certified device that runs the test.

### Why it matters

- ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

### Acceptance criteria

- Client to supply the versions of the certified device.

**Specification reference (ATL105, word for word):**

- Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters" ([ATL105 text line 22384](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22384))
- Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters" ([ATL105 text line 23657](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23657))
- Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters" ([ATL105 text line 22307](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22307))

**Test data excerpt - [TC-077469](#tc-077469), step 1, `TD-TC-077469.json` (selected fields, actual then expected):**

```jsonc
// actual - line 5
"Totals Data Segment": {
// expected
"Totals Data Segment": {  // I-05: Use the versions of the certified device that runs the test.
```

<a id="d13"></a>

## Defect 13: No test data generated for a host test case

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 1 shown (16 in the run) · **Defect id:** I-08

<details><summary>All 16 test cases of the run with this defect (click to expand)</summary>

[TC-077358](#tc-077358), [TC-077359](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L137), [TC-077409](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L187), [TC-077410](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L188), [TC-077540](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L318), [TC-077541](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L319), [TC-077627](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L405), [TC-077644](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L422), [TC-077645](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L423), [TC-077866](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L644), [TC-077867](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L645), [TC-077969](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L747), [TC-077970](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L748), [TC-078063](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L841), [TC-078118](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L896), [TC-078158](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L936)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077358](#tc-077358) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136)) | `blocked_no_test_data` | Product Code Data Segment: Segment Length one more than its rendered bytes | Segment 102 Segment Length one more than its rendered bytes |  |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077358 | - | test case record [test_cases.jsonl line 136](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) → `test_data_file` | `test_data_file` = `null` | No test data was generated for this host test case |

### Expected behavior

- Generate the test data once the missing input is available.

### Why it matters

- The test case sends a message to BUYPASS (EMV Financial Transaction Request) but has no test data file (status blocked_no_test_data).

### Acceptance criteria

- Generate the test data when the missing input is available.

<a id="d14"></a>

## Defect 14: Expected Response Code not stated for a step, or several codes accepted

**Priority:** Medium · **Kind:** Test case issue to correct · **Test cases:** 1 shown (128 in the run) · **Defect id:** C-04

<details><summary>All 128 test cases of the run with this defect (click to expand)</summary>

[TC-077413](#tc-077413), [TC-077418](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L196), [TC-077423](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L201), [TC-077427](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L205), [TC-077430](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L208), [TC-077437](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L215), [TC-077441](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L219), [TC-077446](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L224), [TC-077451](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L229), [TC-077456](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L234), [TC-077457](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L235), [TC-077459](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L237), [TC-077463](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L241), [TC-077467](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L245), [TC-077468](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L246), [TC-077470](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L248), [TC-077476](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L254), [TC-077481](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L259), [TC-077482](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L260), [TC-077484](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L262), [TC-077488](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L266), [TC-077489](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L267), [TC-077550](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L328), [TC-077555](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L333), [TC-077562](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L340), [TC-077568](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L346), [TC-077572](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L350), [TC-077575](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L353), [TC-077580](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L358), [TC-077581](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L359), [TC-077583](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L361), [TC-077588](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L366), [TC-077593](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L371), [TC-077598](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L376), [TC-077602](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L380), [TC-077603](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L381), [TC-077605](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L383), [TC-077651](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L429), [TC-077656](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L434), [TC-077657](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L435), [TC-077659](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L437), [TC-077664](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L442), [TC-077668](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L446), [TC-077673](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L451), [TC-077678](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L456), [TC-077685](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L463), [TC-077690](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L468), [TC-077691](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L469), [TC-077693](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L471), [TC-077698](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L476), [TC-077705](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L483), [TC-077711](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L489), [TC-077716](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L494), [TC-077721](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L499), [TC-077727](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L505), [TC-077732](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L510), [TC-077737](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L515), [TC-077745](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L523), [TC-077750](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L528), [TC-077755](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L533), [TC-077759](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L537), [TC-077762](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L540), [TC-077769](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L547), [TC-077773](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L551), [TC-077778](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L556), [TC-077783](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L561), [TC-077788](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L566), [TC-077789](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L567), [TC-077791](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L569), [TC-077796](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L574), [TC-077801](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L579), [TC-077805](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L583), [TC-077809](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L587), [TC-077810](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L588), [TC-077812](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L590), [TC-077818](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L596), [TC-077823](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L601), [TC-077824](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L602), [TC-077826](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L604), [TC-077830](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L608), [TC-077831](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L609), [TC-077925](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L703), [TC-077930](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L708), [TC-077931](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L709), [TC-077933](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L711), [TC-077938](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L716), [TC-077942](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L720), [TC-077947](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L725), [TC-077952](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L730), [TC-077957](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L735), [TC-077977](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L755), [TC-077983](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L761), [TC-077989](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L767), [TC-077994](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L772), [TC-077999](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L777), [TC-078005](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L783), [TC-078011](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L789), [TC-078016](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L794), [TC-078020](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L798), [TC-078025](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L803), [TC-078031](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L809), [TC-078036](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L814), [TC-078040](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L818), [TC-078045](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L823), [TC-078156](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L934), [TC-078157](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L935), [TC-078162](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L940), [TC-078163](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L941), [TC-078173](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L951), [TC-078174](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L952), [TC-078189](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L967), [TC-078190](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L968), [TC-078208](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L986), [TC-078209](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L987), [TC-078221](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L999), [TC-078222](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1000), [TC-078227](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1005), [TC-078228](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1006), [TC-078233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1011), [TC-078234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1012), [TC-078236](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1014), [TC-078237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1015), [TC-078239](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1017), [TC-078240](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1018), [TC-078245](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1023), [TC-078246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1024), [TC-078251](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1029), [TC-078252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1030)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077413](#tc-077413) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191)) | `ready` | Totals Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077413.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077413 | - | test case record [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) → `expected_response.response_code (step 1)` | `expected_response.response_code (step 1)` = `null` | The test case accepts several Response Codes instead of one |

### Expected behavior

- State the one expected Response Code for this transaction type (from BUYPASS or a reference run).

### Why it matters

- The test case accepts any of 5 = Approved - Totals; 6 = Approved - Totals with electronic mail retrieval pending; B = Approved - Totals with electronic mail retrieval pending and proprietary data pending; D = Approved - Totals with proprietary data pending; N = Approved - Totals with Proprietary Host Discount data pending; W = Approved - Totals with Proprietary Custom Receipt Text pending for the purchase step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

### Acceptance criteria

- State the expected Response Code (from BUYPASS or a reference run).

**Specification reference (ATL105, word for word):**

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "5 Approved—Totals" ([ATL105 text line 23213](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23213))
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "6 Approved—Totals with electronic mail retrieval pending" ([ATL105 text line 23214](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23214))
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "B Approved—Totals with electronic mail retrieval pending and proprietary data pending" ([ATL105 text line 23221](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23221))
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "D Approved—Totals with proprietary data pending" ([ATL105 text line 23227](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23227))
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "N Approved—Totals with Proprietary Host Discount data pending" ([ATL105 text line 23247](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23247))
- Element 83 Response Code (Chapter 13), PDF p.405 (printed 13-65): "W Approved—Totals with Proprietary Custom Receipt Text pending" ([ATL105 text line 23271](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23271))

<a id="d15"></a>

## Defect 15: Other points to confirm

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (1 in the run) · **Defect id:** S-05

<details><summary>All 1 test cases of the run with this defect (click to expand)</summary>

[TC-077480](#tc-077480)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077480](#tc-077480) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258)) | `ready` | Totals Data Segment: Card Type Total Count one below its documented range (00001-99999) | Card Type Total Count one below its documented range (00001-99999) | [TD-TC-077480.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077480 | 1 | [TD-TC-077480.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L20) → `Totals Data Segment → CardTypeTotalCount` | `00000` | The totals bucket is sent with a count of 00000 |

### Expected behavior

- Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent.

### Why it matters

- Element 16 allows 00001-99999, and the category section is sent only when data occurs for a card type. A net count of 00000 suggests the bucket should not be sent at all.

### Acceptance criteria

- Confirm with BUYPASS / SME.

**Specification reference (ATL105, word for word):**

- Element 16 Card Type Total Count (Chapter 13), PDF p.375 (printed 13-35): "Valid Codes/Values: 00001–99999" ([ATL105 text line 21894](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21894))
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type." ([ATL105 text line 13692](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L13692))

**Test data excerpt - [TC-077480](#tc-077480), step 1, `TD-TC-077480.json` (selected fields, actual then expected):**

```jsonc
// actual - line 20
"CardTypeTotalCount": "00000"
// expected
"CardTypeTotalCount": "00000"  // S-05: Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent.
```

<a id="d16"></a>

## Defect 16: Same request as another test case (difference only on the device / response side)

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (94 in the run) · **Defect id:** C-08

<details><summary>All 94 test cases of the run with this defect (click to expand)</summary>

[TC-077553](#tc-077553), [TC-077562](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L340), [TC-077579](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L357), [TC-077580](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L358), [TC-077598](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L376), [TC-077655](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L433), [TC-077659](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L437), [TC-077689](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L467), [TC-077696](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L474), [TC-077698](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L476), [TC-077708](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L486), [TC-077724](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L502), [TC-077748](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L526), [TC-077765](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L543), [TC-077787](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L565), [TC-077788](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L566), [TC-077805](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L583), [TC-077809](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L587), [TC-077815](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L593), [TC-077820](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L598), [TC-077823](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L601), [TC-077826](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L604), [TC-077830](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L608), [TC-077929](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L707), [TC-077933](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L711), [TC-077957](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L735), [TC-078162](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L940), [TC-078165](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L943), [TC-078168](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L946), [TC-078169](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L947), [TC-078170](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L948), [TC-078171](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L949), [TC-078172](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L950), [TC-078173](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L951), [TC-078179](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L957), [TC-078180](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L958), [TC-078181](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L959), [TC-078182](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L960), [TC-078183](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L961), [TC-078184](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L962), [TC-078185](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L963), [TC-078186](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L964), [TC-078187](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L965), [TC-078188](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L966), [TC-078189](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L967), [TC-078190](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L968), [TC-078195](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L973), [TC-078196](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L974), [TC-078197](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L975), [TC-078198](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L976), [TC-078199](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L977), [TC-078200](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L978), [TC-078201](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L979), [TC-078202](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L980), [TC-078203](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L981), [TC-078204](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L982), [TC-078205](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L983), [TC-078206](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L984), [TC-078207](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L985), [TC-078214](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L992), [TC-078215](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L993), [TC-078216](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L994), [TC-078217](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L995), [TC-078218](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L996), [TC-078219](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L997), [TC-078220](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L998), [TC-078221](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L999), [TC-078222](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1000), [TC-078226](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1004), [TC-078228](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1006), [TC-078229](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1007), [TC-078230](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1008), [TC-078231](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1009), [TC-078232](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1010), [TC-078233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1011), [TC-078234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1012), [TC-078235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1013), [TC-078236](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1014), [TC-078237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1015), [TC-078238](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1016), [TC-078239](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1017), [TC-078240](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1018), [TC-078241](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1019), [TC-078242](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1020), [TC-078243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1021), [TC-078244](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1022), [TC-078245](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1023), [TC-078246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1024), [TC-078247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1025), [TC-078248](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1026), [TC-078249](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1027), [TC-078250](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1028), [TC-078251](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1029), [TC-078252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1030)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077553](#tc-077553) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L331)) | `ready` | Electronic Mail Data Segment: Information Byte value not in its documented valid values | Information Byte value not in its documented valid values | [TD-TC-077553.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077553.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077553 | 1 | TD-TC-077553.json vs TD-TC-077550.json | `expected 1 vs 8/9/G/J` | Same request as TC-077550 (BRDE_TS6_TC5) |

### Expected behavior

- Check the difference on the device / in the response; optionally use another card.

### Why it matters

- Apart from the Sequence Number the message is identical to TC-077550 (all segments compared). TC-077550 expects 8/9/G/J; this test expects 1. What makes the two tests different is not in the message sent to BUYPASS.

### Acceptance criteria

- Check the difference on the device or in the response; optionally use another card.

<a id="d17"></a>

## Defect 17: Expiry format for stored credentials (entry mode 10) not stated

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (2 in the run) · **Defect id:** S-01

<details><summary>All 2 test cases of the run with this defect (click to expand)</summary>

[TC-077628](#tc-077628), [TC-077629](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L407)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077628](#tc-077628) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406)) | `requires_review` | Variable Information Data Segment: POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08): code 'X' outside its documented valid codes I/C | Table 032 POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08): code 'X' outside its documented valid codes I/C | [TD-TC-077628.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077628 | - | Card Discretionary Block Data (entry mode 10 steps) | `1229` | Expiry format for stored credentials is not stated |

### Expected behavior

- Confirm the expiry format for entry mode 10 with BUYPASS.

### Why it matters

- The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

### Acceptance criteria

- Confirm with BUYPASS.

**Specification reference (ATL105, word for word):**

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format" ([ATL105 text line 21761](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L21761))
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." ([ATL105 text line 4564](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L4564))

<a id="d18"></a>

## Defect 18: ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context

**Priority:** Low · **Kind:** Citation issue · **Test cases:** 18 shown (1,027 in the run) · **Defect id:** Q-01

<details><summary>All 1,027 test cases of the run with this defect (click to expand)</summary>

[TC-077223](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L1), [TC-077224](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L2), [TC-077225](#tc-077225), [TC-077226](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L4), [TC-077227](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L5), [TC-077228](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L6), [TC-077229](#tc-077229), [TC-077230](#tc-077230), [TC-077231](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L9), [TC-077232](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L10), [TC-077233](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L11), [TC-077234](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L12), [TC-077235](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L13), [TC-077236](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L14), [TC-077237](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L15), [TC-077238](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L16), [TC-077239](#tc-077239), [TC-077240](#tc-077240), [TC-077241](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L19), [TC-077242](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L20), [TC-077243](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L21), [TC-077244](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L22), [TC-077245](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L23), [TC-077246](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L24), [TC-077247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L25), [TC-077248](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L26), [TC-077249](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L27), [TC-077250](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L28), [TC-077251](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L29), [TC-077252](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L30), [TC-077253](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L31), [TC-077254](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L32), [TC-077255](#tc-077255), [TC-077256](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L34), [TC-077257](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L35), [TC-077258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L36), [TC-077259](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L37), [TC-077260](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L38), [TC-077261](#tc-077261), [TC-077262](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L40), [TC-077263](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L41), [TC-077264](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L42), [TC-077265](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L43), [TC-077266](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L44), [TC-077267](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L45), [TC-077268](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L46), [TC-077269](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L47), [TC-077270](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L48), [TC-077271](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L49), [TC-077272](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L50), [TC-077273](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L51), [TC-077274](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L52), [TC-077275](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L53), [TC-077276](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L54), [TC-077277](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L55), [TC-077278](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L56), [TC-077279](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L57), [TC-077280](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L58), [TC-077281](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L59), [TC-077282](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L60), [TC-077283](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L61), [TC-077284](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L62), [TC-077285](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L63), [TC-077286](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L64), [TC-077287](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L65), [TC-077288](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L66), [TC-077289](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L67), [TC-077290](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L68), [TC-077291](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L69), [TC-077292](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L70), [TC-077293](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L71), [TC-077294](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L72), [TC-077295](#tc-077295), [TC-077296](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L74), [TC-077298](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L76), [TC-077299](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L77), [TC-077300](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L78), [TC-077301](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L79), [TC-077302](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L80), [TC-077303](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L81), [TC-077304](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L82), [TC-077305](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L83), [TC-077306](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L84), [TC-077307](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L85), [TC-077308](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L86), [TC-077309](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L87), [TC-077310](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L88), [TC-077311](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L89), [TC-077312](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L90), [TC-077313](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L91), [TC-077314](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L92), [TC-077315](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L93), [TC-077316](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L94), [TC-077317](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L95), [TC-077318](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L96), [TC-077319](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L97), [TC-077320](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L98), [TC-077321](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L99), [TC-077322](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L100), [TC-077323](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L101), [TC-077324](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L102), [TC-077325](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L103), [TC-077326](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L104), [TC-077327](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L105), [TC-077328](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L106), [TC-077329](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L107), [TC-077330](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L108), [TC-077331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L109), [TC-077332](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L110), [TC-077333](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L111), [TC-077334](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L112), [TC-077335](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L113), [TC-077336](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L114), [TC-077337](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L115), [TC-077338](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L116), [TC-077339](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L117), [TC-077340](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L118), [TC-077341](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L119), [TC-077342](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L120), [TC-077343](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L121), [TC-077344](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L122), [TC-077345](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L123), [TC-077346](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L124), [TC-077347](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L125), [TC-077348](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L126), [TC-077349](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L127), [TC-077350](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L128), [TC-077351](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L129), [TC-077352](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L130), [TC-077353](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L131), [TC-077354](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L132), [TC-077355](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L133), [TC-077356](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L134), [TC-077357](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L135), [TC-077358](#tc-077358), [TC-077359](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L137), [TC-077360](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L138), [TC-077361](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L139), [TC-077362](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L140), [TC-077363](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L141), [TC-077364](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L142), [TC-077365](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L143), [TC-077366](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L144), [TC-077367](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L145), [TC-077368](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L146), [TC-077369](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L147), [TC-077370](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L148), [TC-077371](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L149), [TC-077372](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L150), [TC-077373](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L151), [TC-077374](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L152), [TC-077375](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L153), [TC-077376](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L154), [TC-077377](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L155), [TC-077378](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L156), [TC-077379](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L157), [TC-077380](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L158), [TC-077381](#tc-077381), [TC-077382](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L160), [TC-077383](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L161), [TC-077384](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L162), [TC-077385](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L163), [TC-077386](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L164), [TC-077387](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L165), [TC-077388](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L166), [TC-077389](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L167), [TC-077390](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L168), [TC-077391](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L169), [TC-077392](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L170), [TC-077393](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L171), [TC-077394](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L172), [TC-077395](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L173), [TC-077396](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L174), [TC-077397](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L175), [TC-077398](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L176), [TC-077399](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L177), [TC-077400](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L178), [TC-077401](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L179), [TC-077402](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L180), [TC-077403](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L181), [TC-077404](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L182), [TC-077405](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L183), [TC-077406](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L184), [TC-077407](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L185), [TC-077408](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L186), [TC-077409](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L187), [TC-077410](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L188), [TC-077411](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L189), [TC-077412](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L190), [TC-077413](#tc-077413), [TC-077414](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L192), [TC-077415](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L193), [TC-077416](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L194), [TC-077417](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L195), [TC-077418](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L196), [TC-077419](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L197), [TC-077420](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L198), [TC-077421](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L199), [TC-077422](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L200), [TC-077423](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L201), [TC-077424](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L202), [TC-077425](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L203), [TC-077426](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L204), [TC-077427](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L205), [TC-077428](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L206), [TC-077429](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L207), [TC-077430](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L208), [TC-077431](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L209), [TC-077432](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L210), [TC-077433](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L211), [TC-077434](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L212), [TC-077435](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L213), [TC-077436](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L214), [TC-077437](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L215), [TC-077438](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L216), [TC-077439](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L217), [TC-077440](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L218), [TC-077441](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L219), [TC-077442](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L220), [TC-077443](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L221), [TC-077444](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L222), [TC-077445](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L223), [TC-077446](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L224), [TC-077447](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L225), [TC-077448](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L226), [TC-077449](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L227), [TC-077450](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L228), [TC-077451](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L229), [TC-077452](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L230), [TC-077453](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L231), [TC-077454](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L232), [TC-077455](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L233), [TC-077456](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L234), [TC-077457](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L235), [TC-077458](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L236), [TC-077459](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L237), [TC-077460](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L238), [TC-077461](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L239), [TC-077462](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L240), [TC-077463](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L241), [TC-077464](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L242), [TC-077465](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L243), [TC-077466](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L244), [TC-077467](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L245), [TC-077468](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L246), [TC-077469](#tc-077469), [TC-077470](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L248), [TC-077471](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L249), [TC-077472](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L250), [TC-077473](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L251) and 777 more - every one is in [Defects_in_the_run.csv](Defects_in_the_run.csv)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077239](#tc-077239) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data one byte over its documented maximum length (51) | Card Discretionary Block Data one byte over its documented maximum length (51) | [TD-TC-077239.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json) |
| [TC-077295](#tc-077295) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73)) | `needs_client_input` | Standard Message Data Segment: Segment Length one more than its rendered bytes | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) |
| [TC-077381](#tc-077381) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159)) | `requires_review` | Purchase Card Data Segment: Segment Type differs from its fixed value '104' | Segment Type differs from its fixed value '104' | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) |
| [TC-077469](#tc-077469) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247)) | `ready` | Totals Data Segment: Card Label one byte over its documented maximum length (4) | Card Label one byte over its documented maximum length (4) | [TD-TC-077469.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) |
| [TC-077225](#tc-077225) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3)) | `ready` | Standard Message Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077225.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json) |
| [TC-077230](#tc-077230) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8)) | `ready` | Standard Message Data Segment: Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted | [TD-TC-077230.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json) |
| [TC-077240](#tc-077240) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18)) | `ready` | Standard Message Data Segment: Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted | [TD-TC-077240.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json) |
| [TC-077255](#tc-077255) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33)) | `ready` | Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) |
| [TC-077261](#tc-077261) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39)) | `blocked_verification` | Standard Message Data Segment: Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted | [TD-TC-077261.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json) |
| [TC-078056](#tc-078056) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834)) | `needs_client_input` | Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted | Maximal field-inclusion profile request is accepted | [TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) |
| [TC-078069](#tc-078069) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847)) | `ready` | Financial Transaction Request – Message structure: SKU Data Segment moved first | segment 114 SKU Data Segment moved first | [TD-TC-078069.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json) |
| [TC-077849](#tc-077849) ([definition](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627)) | `pending_sme` | NFC Payment Tokenization Data Segment: Cryptogram Token Data value not in its documented valid values | Cryptogram Token Data value not in its documented valid values | [TD-TC-077849.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json) |
| + 6 more: [TC-077229](#tc-077229), [TC-078058](#tc-078058), [TC-077358](#tc-077358), [TC-077413](#tc-077413), [TC-077480](#tc-077480), [TC-077553](#tc-077553) | | | | |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077239 | - | test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Card Discretionary Block Data: AN max 51"` | The citation of the rule gives no ATL105 page |
| TC-077295 | - | test case record [test_cases.jsonl line 73](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Identifies the length of the segment in which it appears."` | The citation of the rule gives no ATL105 page |
| TC-077381 | - | test case record [test_cases.jsonl line 159](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Segment Type: N max 3, fixed 104"` | The citation of the rule gives no ATL105 page |
| TC-077469 | - | test case record [test_cases.jsonl line 247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Card Label: AN max 4"` | The citation of the rule gives no ATL105 page |
| TC-077225 | - | test case record [test_cases.jsonl line 3](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Information Byte: AN max 1"` | The citation of the rule gives no ATL105 page |
| TC-077230 | - | test case record [test_cases.jsonl line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Terminal Identifier: AN max 22"` | The citation of the rule gives no ATL105 page |
| TC-077240 | - | test case record [test_cases.jsonl line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Card Discretionary Block Data: AN max 51"` | The citation of the rule gives no ATL105 page |
| TC-077255 | - | test case record [test_cases.jsonl line 33](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Valid Codes/Values: 1-99999999"` | The citation of the rule gives no ATL105 page |
| TC-077261 | - | test case record [test_cases.jsonl line 39](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Valid Codes/Values: 1-99999999"` | The citation of the rule gives no ATL105 page |
| TC-078056 | - | test case record [test_cases.jsonl line 834](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "maximal"` | The citation of the rule gives no ATL105 page |
| TC-078069 | - | test case record [test_cases.jsonl line 847](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) → `spec_basis` | `spec_basis` = `[{"rule_id": "BR-251-2", "page": 251, "source_id": "SRC-ATL105-PDF-001"}]` | The citation of p.251 has no quoted text |
| TC-077849 | - | test case record [test_cases.jsonl line 627](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Cryptogram Token Data: B64 Variable Length: 28/56 bytes max 56"` | The citation of the rule gives no ATL105 page |
| TC-077229 | - | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Terminal Identifier: AN max 22"` | The citation of the rule gives no ATL105 page |
| TC-078058 | - | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `spec_basis` | `spec_basis` = [{"rule_id": "BR-168-4", "page": 168, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-166-3", "page": 166, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-204-4", "page": 204, "source_id": "SRC-ATL105-PDF-001"}] | The citation of p.168 has no quoted text |
| TC-078058 | - | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `spec_basis` | `spec_basis` = [{"rule_id": "BR-168-4", "page": 168, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-166-3", "page": 166, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-204-4", "page": 204, "source_id": "SRC-ATL105-PDF-001"}] | The citation of p.166 has no quoted text |
| TC-078058 | - | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `spec_basis` | `spec_basis` = [{"rule_id": "BR-168-4", "page": 168, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-166-3", "page": 166, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-204-4", "page": 204, "source_id": "SRC-ATL105-PDF-001"}] | The citation of p.204 has no quoted text |
| TC-077358 | - | test case record [test_cases.jsonl line 136](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Identifies the length of the segment in which it appears."` | The citation of the rule gives no ATL105 page |
| TC-077413 | - | test case record [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Information Byte: AN max 1"` | The citation of the rule gives no ATL105 page |
| TC-077480 | - | test case record [test_cases.jsonl line 258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Valid Codes/Values: 00001-99999"` | The citation of the rule gives no ATL105 page |
| TC-077553 | - | test case record [test_cases.jsonl line 331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L331) → `spec_basis[0]` | `spec_basis[0]` = `p.None: "Information Byte: AN max 1"` | The citation of the rule gives no ATL105 page |

### Expected behavior

- Cite the Chapter 13 page of the element and quote its ATL105 text word for word.
- Add the ATL105 sentence the rule is based on.

### Why it matters

- The spec_basis entry quotes "Card Discretionary Block Data: AN max 51" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- The spec_basis entry gives a rule id and a page but no ATL105 text, so the basis cannot be checked word for word.

**BA explanation:** "Some quotes the generator wrote into the test cases cite the wrong page, give no page, or are not ATL105 text. They do not change the request, but a reviewer following the citation will not find the rule."

### Acceptance criteria

- Every spec_basis quote is ATL105 text, word for word, on the cited page and about this transaction.

<a id="not-defects"></a>

## Not defects (shown so they are not raised again)

One row per kind of finding with its first example; each test case section below lists its own.

| Kind | Finding (first example) | Test cases | Actual | Where | Why it is correct |
|---|---|---|---|---|---|
| Intended violation of a negative test (correct) | Card Discretionary Block Data is not valid | [TC-077239](#tc-077239) | `AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077239.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value that meets: AN, variable length 51.`). |
| Intended violation of a negative test (correct) | Segment Length is not valid | [TC-077295](#tc-077295) | `093` | [TD-TC-077295.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L7) → `Standard Message Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `092`). |
| Intended violation of a negative test (correct) | Segment 105 length is counted with the wrong separators and label width | [TC-077469](#tc-077469), [TC-077480](#tc-077480) | `089` | [TD-TC-077469.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L7) → `Totals Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `088`). |
| Intended violation of a negative test (correct) | Card Label (Element 13) is not 4 characters | [TC-077469](#tc-077469) | `'AAAAA'` | [TD-TC-077469.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L20) → `Totals Data Segment → CardLabel` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `'AAAAA' (AAAAA followed by -1 spaces)`). |
| Intended violation of a negative test (correct) | Card Label (Totals) is not valid | [TC-077469](#tc-077469) | `AAAAA` | [TD-TC-077469.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L20) → `Totals Data Segment → CardLabel` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value that meets: Fixed 4 characters, left-aligned and space-filled; CC = Credit bucket.`). |
| Intended violation of a negative test (correct) | Terminal Identifier is not valid | [TC-077229](#tc-077229) | `AAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value that meets: Device Type 'AA' + State Code 'AA' (not in Appendix D) + Merchant Number and Device Number 'AAAAAAAAAAAAAAAAAAA'.`). |

<a id="test-data"></a>

## Test data - actual and expected, per test case

For each test case: its findings (actual value, where it is, what it should be), the test data as delivered (click to expand) and the expected test data - the same file with every change; each changed line ends with a `//` comment naming the defect id. Values in `<...>` must come from the client, BUYPASS or the previous response.

<a id="tc-077239"></a>

### TC-077239 (BRDE_TS1_TC17) - Card Discretionary Block Data one byte over its documented maximum length (51)

**Test name:** Standard Message Data Segment: Card Discretionary Block Data one byte over its documented maximum length (51). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) · **Every field against ATL105:** [Result_MD_Folder/TC-077239.md](Result_MD_Folder/TC-077239.md) · **Step-by-step guide:** [TC-077239-Manual-Validation.md](Manual%20Validation/TC-077239-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-09 | Account Number is not valid | Test data defect | `TOTALS REQUEST` | [TD-TC-077239.json line 14](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L14) → `Standard Message Data Segment → AccountNumber` | A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule). |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Card Discretionary Block Data: AN max 51"` | test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077239.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=9BDD6CCFC3C2EE60, 9F36=1AD3, 9F37=F57376F6, 95=8000008000` | [TD-TC-077239.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077239.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| N-01 | Card Discretionary Block Data is not valid | Intended violation of a negative test (correct) | `AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077239.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value that meets: AN, variable length 51.`). |

<details><summary>Test data - actual, step 1: TD-TC-077239.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "118",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "TOTALS REQUEST",
      "CardDiscretionaryBlockData": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772391",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601209C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26089BDD6CCFC3C2EE609F2701809F3303E0F0C89F34035E03029F3501229F36021AD39F3704F57376F69F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077239.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "118",  // I-01: recalculate: 105 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "TOTALS REQUEST",  // D-09: A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule).
      "CardDiscretionaryBlockData": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",  // N-01: different from step None ('A value that meets: AN, variable length 51.') on purpose - the rule this negative test breaks; keep
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772391",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601209C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26089BDD6CCFC3C2EE609F2701809F3303E0F0C89F34035E03029F3501229F36021AD39F3704F57376F69F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077295"></a>

### TC-077295 (BRDE_TS1_TC93) - Segment 100 Segment Length one more than its rendered bytes

**Test name:** Standard Message Data Segment: Segment Length one more than its rendered bytes. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `needs_client_input` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 73](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) · **Every field against ATL105:** [Result_MD_Folder/TC-077295.md](Result_MD_Folder/TC-077295.md) · **Step-by-step guide:** [TC-077295-Manual-Validation.md](Manual%20Validation/TC-077295-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0931` | [TD-TC-077295.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-09 | Approval Number is not valid | Test data defect | `0CI2N6` | [TD-TC-077295.json line 23](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L23) → `Standard Message Data Segment → ApprovalNumber` | A value that meets: Not sent on a purchase. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Identifies the length of the segment in which it appears."` | test case record [test_cases.jsonl line 73](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-09 | Encrypted PIN Block Data holds instruction text, not a value | Awaiting client input / run-time value | `<AWAITING:EncryptedPINBlockData>` | [TD-TC-077295.json line 16](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | Client to supply the value (for Account Number: a MasterCard test card). |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077295.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=6F1D5B4CA7CC2180, 9F36=9DED, 9F37=BC8BBA14, 95=8000008000` | [TD-TC-077295.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077295.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| N-01 | Segment Length is not valid | Intended violation of a negative test (correct) | `093` | [TD-TC-077295.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L7) → `Standard Message Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `092`). |

<details><summary>Test data - actual, step 1: TD-TC-077295.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "093",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5147295465216009",
      "CardDiscretionaryBlockData": "0931",
      "EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "772951",
      "ApprovalNumber": "0CI2N6",
      "LocalDateandLocalTime": "0615251430",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601029C01099F02060000000015039F03060000000000019F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26086F1D5B4CA7CC21809F2701809F3303E0F0C89F34035E03029F3501229F36029DED9F3704BC8BBA149F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077295.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "093",  // N-01: different from step None ('092') on purpose - the rule this negative test breaks; keep; D-13/I-01: recalculate: 107 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5147295465216009",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3109 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3109 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",  // I-09: Client to supply the value (for Account Number: a MasterCard test card).
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "772951",
      "ApprovalNumber": "0CI2N6",  // D-09: A value that meets: Not sent on a purchase.
      "LocalDateandLocalTime": "0615251430",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601029C01099F02060000000015039F03060000000000019F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26086F1D5B4CA7CC21809F2701809F3303E0F0C89F34035E03029F3501229F36029DED9F3704BC8BBA149F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077381"></a>

### TC-077381 (BRDE_TS3_TC25) - Segment Type differs from its fixed value '104'

**Test name:** Purchase Card Data Segment: Segment Type differs from its fixed value '104'. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `requires_review` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 159](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) · **Every field against ATL105:** [Result_MD_Folder/TC-077381.md](Result_MD_Folder/TC-077381.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0427` | [TD-TC-077381.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-09 | Purchase Card Data Segment is in the test case but not in the test data | Test data defect | `(absent)` | [TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) → `Purchase Card Data Segment` | Regenerate the test data file from the test case. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Segment Type: N max 3, fixed 104"` | test case record [test_cases.jsonl line 159](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L159) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077381.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=07F42E09136159DF, 9F36=E5C6, 9F37=AC4BE2A3, 95=8000008000` | [TD-TC-077381.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `WI28888556001` | [TD-TC-077381.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077381.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "WI28888556001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5157277059774228",
      "CardDiscretionaryBlockData": "0427",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "773811",
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
          "VariableInformation": "00"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601039C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260807F42E09136159DF9F2701809F3303E0F0C89F34035E03029F3501229F3602E5C69F3704AC4BE2A39F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077381.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13/I-01: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5157277059774228",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2704 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2704 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "773811",
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
          "VariableInformation": "00"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601039C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260807F42E09136159DF9F2701809F3303E0F0C89F34035E03029F3501229F3602E5C69F3704AC4BE2A39F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077469"></a>

### TC-077469 (BRDE_TS4_TC15) - Card Label one byte over its documented maximum length (4)

**Test name:** Totals Data Segment: Card Label one byte over its documented maximum length (4). **What the test does:** step 1 sends `` (?), expected Response Code `1`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) · **Every field against ATL105:** [Result_MD_Folder/TC-077469.md](Result_MD_Folder/TC-077469.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-09 | Card Type Total Count (Totals) is not valid | Test data defect | `` | [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment → CardTypeTotalCount` | A value that meets: Required. |
| D-09 | Card Type Total Amount (Totals) is not valid | Test data defect | `` | [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment → CardTypeTotalAmount` | A value that meets: Required. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Card Label: AN max 4"` | test case record [test_cases.jsonl line 247](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-05 | The device version fields of the Totals Request are generated values | Awaiting client input / run-time value | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment` | Use the versions of the certified device that runs the test. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` |  → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| N-01 | Segment 105 length is counted with the wrong separators and label width | Intended violation of a negative test (correct) | `089` | [TD-TC-077469.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L7) → `Totals Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `088`). |
| N-01 | Card Label (Element 13) is not 4 characters | Intended violation of a negative test (correct) | `'AAAAA'` | [TD-TC-077469.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L20) → `Totals Data Segment → CardLabel` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `'AAAAA' (AAAAA followed by -1 spaces)`). |
| N-01 | Card Label (Totals) is not valid | Intended violation of a negative test (correct) | `AAAAA` | [TD-TC-077469.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L20) → `Totals Data Segment → CardLabel` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value that meets: Fixed 4 characters, left-aligned and space-filled; CC = Credit bucket.`). |

<details><summary>Test data - actual, step 1: TD-TC-077469.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "4C7QT1BE",
      "SoftwareVersion": "6XSCQHWT",
      "FirmwareVersion": "W6T6RW0B",
      "SequenceNumber": "774691",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "AAAAA"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077469.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // I-05: Use the versions of the certified device that runs the test.
      "SegmentType": "105",
      "SegmentLength": "089",  // N-01: different from step None ('088') on purpose - the rule this negative test breaks; keep
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "4C7QT1BE",
      "SoftwareVersion": "6XSCQHWT",
      "FirmwareVersion": "W6T6RW0B",
      "SequenceNumber": "774691",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "AAAAA"  // N-01: different from step None (''AAAAA' (AAAAA followed by -1 spaces)') on purpose - the rule this negative test breaks; keep; N-01: different from step None ('A value that meets: Fixed 4 characters, left-aligned and space-filled; CC = Credit bucket.') on purpose - the rule this negative test breaks; keep
    }
  }
}
```

</details>

<a id="tc-077225"></a>

### TC-077225 (BRDE_TS1_TC7) - Information Byte exactly at its documented maximum length (1 bytes) is accepted

**Test name:** Standard Message Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 3](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) · **Every field against ATL105:** [Result_MD_Folder/TC-077225.md](Result_MD_Folder/TC-077225.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-15 | Information Byte is not valid | Test data defect | `A` | [TD-TC-077225.json line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L8) → `Standard Message Data Segment → InformationByte` | A value that meets: 1 character: 'A' = not a valid code. |
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0329` | [TD-TC-077225.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 3](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Information Byte: AN max 1"` | test case record [test_cases.jsonl line 3](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077225.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=10E40F248A144726, 9F36=474D, 9F37=32180A66, 95=8000008000` | [TD-TC-077225.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077225.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077225.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "A",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5191233100426753",
      "CardDiscretionaryBlockData": "0329",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772251",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601199C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260810E40F248A1447269F2701809F3303E0F0C89F34035E03029F3501229F3602474D9F370432180A669F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077225.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13/I-01: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "A",  // D-15: A value that meets: 1 character: 'A' = not a valid code.
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5191233100426753",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2903 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2903 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772251",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601199C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260810E40F248A1447269F2701809F3303E0F0C89F34035E03029F3501229F3602474D9F370432180A669F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077230"></a>

### TC-077230 (BRDE_TS1_TC14) - Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted

**Test name:** Standard Message Data Segment: Terminal Identifier exactly at its documented maximum length (22 bytes) is accepted. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) · **Every field against ATL105:** [Result_MD_Folder/TC-077230.md](Result_MD_Folder/TC-077230.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0130` | [TD-TC-077230.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-15 | Terminal Identifier is not at its documented maximum length | Test data defect | `JX41894821001` | [TD-TC-077230.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | A valid Terminal Identifier of exactly 22 characters, or drop the test if no such value can be approved. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Terminal Identifier: AN max 22"` | test case record [test_cases.jsonl line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L8) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077230.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=077BCB0AACD5A10C, 9F36=1A07, 9F37=291CF6D3, 95=8000008000` | [TD-TC-077230.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077230.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077230.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5146671598291674",
      "CardDiscretionaryBlockData": "0130",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772301",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601159C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608077BCB0AACD5A10C9F2701809F3303E0F0C89F34035E03029F3501229F36021A079F3704291CF6D39F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077230.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13/I-01: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal; D-15: A valid Terminal Identifier of exactly 22 characters, or drop the test if no such value can be approved.
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5146671598291674",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3001 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3001 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772301",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601159C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608077BCB0AACD5A10C9F2701809F3303E0F0C89F34035E03029F3501229F36021A079F3704291CF6D39F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077240"></a>

### TC-077240 (BRDE_TS1_TC3) - Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted

**Test name:** Standard Message Data Segment: Card Discretionary Block Data exactly at its documented maximum length (51 bytes) is accepted. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) · **Every field against ATL105:** [Result_MD_Folder/TC-077240.md](Result_MD_Folder/TC-077240.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-15 | Card Discretionary Block Data is not valid | Test data defect | `1030` | [TD-TC-077240.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | A value that meets: AN, variable length 51. |
| D-15 | Card Discretionary Block Data is not at its documented maximum length | Test data defect | `1030` | [TD-TC-077240.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | A valid Card Discretionary Block Data of exactly 51 characters, or drop the test if no such value can be approved. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Card Discretionary Block Data: AN max 51"` | test case record [test_cases.jsonl line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L18) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077240.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=17C4BEB2294FCE91, 9F36=00E8, 9F37=4502368A, 95=8000008000` | [TD-TC-077240.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077240.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077240.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5139626045485005",
      "CardDiscretionaryBlockData": "1030",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772401",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601279C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260817C4BEB2294FCE919F2701809F3303E0F0C89F34035E03029F3501229F360200E89F37044502368A9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077240.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5139626045485005",
      "CardDiscretionaryBlockData": "1030",  // D-15: A value that meets: AN, variable length 51.; D-15: A valid Card Discretionary Block Data of exactly 51 characters, or drop the test if no such value can be approved.
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772401",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601279C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260817C4BEB2294FCE919F2701809F3303E0F0C89F34035E03029F3501229F360200E89F37044502368A9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077255"></a>

### TC-077255 (BRDE_TS1_TC74) - Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted

**Test name:** Standard Message Data Segment: Fuel Purchase Amount exactly at its documented range minimum (1-99999999) is accepted. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 33](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) · **Every field against ATL105:** [Result_MD_Folder/TC-077255.md](Result_MD_Folder/TC-077255.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0428` | [TD-TC-077255.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-15 | Fuel Purchase Amount is not valid | Test data defect | `00000001` | [TD-TC-077255.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L18) → `Standard Message Data Segment → FuelPurchaseAmount` | A value that meets: N, variable length 7 with two assumed decimals: '00000001' = $0.01. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Fuel Purchase Amount '00000001' at a general (non-petroleum) merchant | [TD-TC-077255.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L18) → `Standard Message Data Segment → FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 33](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Valid Codes/Values: 1-99999999"` | test case record [test_cases.jsonl line 33](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077255.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=D2DA1E1BF0755E0D, 9F36=4A53, 9F37=197ABC64, 95=8000008000` | [TD-TC-077255.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077255.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077255.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "080",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5134170024635947",
      "CardDiscretionaryBlockData": "0428",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "00000001",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772551",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601249C01009F02060000000015019F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608D2DA1E1BF0755E0D9F2701809F3303E0F0C89F34035E03029F3501229F36024A539F3704197ABC649F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077255.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "080",  // D-13/I-01/D-02: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5134170024635947",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2804 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2804 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",  // D-15: A value that meets: N, variable length 7 with two assumed decimals: '00000001' = $0.01.; D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772551",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601249C01009F02060000000015019F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608D2DA1E1BF0755E0D9F2701809F3303E0F0C89F34035E03029F3501229F36024A539F3704197ABC649F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077261"></a>

### TC-077261 (BRDE_TS1_TC75) - Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted

**Test name:** Standard Message Data Segment: Nonfuel Amount exactly at its documented range minimum (1-99999999) is accepted. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `blocked_verification` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 39](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) · **Every field against ATL105:** [Result_MD_Folder/TC-077261.md](Result_MD_Folder/TC-077261.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0529` | [TD-TC-077261.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-15 | Nonfuel Amount is not valid | Test data defect | `00000001` | [TD-TC-077261.json line 19](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L19) → `Standard Message Data Segment → NonfuelAmount` | A value that meets: Digits only, at most 7 for MasterCard (8 only for Amex): '00000001' = $0.01. |
| D-15 | Test data does not meet the test case's own rule check LEG-NONFUEL-AMOUNT-MAX-LENGTH | Test data defect | `rule_checks LEG-NONFUEL-AMOUNT-MAX-LENGTH (step 1)` = `null` | test case record [test_cases.jsonl line 39](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) → `rule_checks LEG-NONFUEL-AMOUNT-MAX-LENGTH (step 1)` | Correct the test data (or the rule check) so that the assertion holds. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Valid Codes/Values: 1-99999999"` | test case record [test_cases.jsonl line 39](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L39) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077261.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=D32061BEEC05FFB2, 9F36=41D2, 9F37=2CFA7590, 95=8000008000` | [TD-TC-077261.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077261.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077261.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "076",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5114711194843284",
      "CardDiscretionaryBlockData": "0529",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "00000001",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772611",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601269C01009F02060000000000019F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608D32061BEEC05FFB29F2701809F3303E0F0C89F34035E03029F3501229F360241D29F37042CFA75909F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077261.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "076",  // D-13/I-01: recalculate: 059 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5114711194843284",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2905 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2905 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "00000001",  // D-15: A value that meets: Digits only, at most 7 for MasterCard (8 only for Amex): '00000001' = $0.01.
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772611",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601269C01009F02060000000000019F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608D32061BEEC05FFB29F2701809F3303E0F0C89F34035E03029F3501229F360241D29F37042CFA75909F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-078056"></a>

### TC-078056 (BRDE_TS22_TC1) - Maximal field-inclusion profile request is accepted

**Test name:** Financial Transaction Request – Message structure: Maximal field-inclusion profile request is accepted. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `needs_client_input` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 834](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) · **Every field against ATL105:** [Result_MD_Folder/TC-078056.md](Result_MD_Folder/TC-078056.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0432` | [TD-TC-078056.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' (Element 49 identifies the date/time of a preauthorized transaction; this step is not one); Pump/Lane Number '1' at a general (non-petroleum) merchant; Fuel Purchase Amount '1' at a general (non-petroleum) merchant | [TD-TC-078056.json line 24](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L24), [TD-TC-078056.json line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L17), [TD-TC-078056.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "maximal"` | test case record [test_cases.jsonl line 834](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-02 | Segment Length must be recalculated at run time | Awaiting client input / run-time value | `086` | [TD-TC-078056.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L7) → `Standard Message Data Segment → SegmentLength` | Recalculate the Segment Length after filling in the real values. |
| I-09 | Encrypted PIN Block Data holds instruction text, not a value | Awaiting client input / run-time value | `<AWAITING:EncryptedPINBlockData>` | [TD-TC-078056.json line 16](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | Client to supply the value (for Account Number: a MasterCard test card). |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-078056.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-078056.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "086",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5168232454390762",
      "CardDiscretionaryBlockData": "0432",
      "EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "780561",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-078056.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "086",  // I-02: Recalculate the Segment Length after filling in the real values.; D-13/I-01/D-02: recalculate: 089 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5168232454390762",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3204 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3204 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",  // I-09: Client to supply the value (for Account Number: a MasterCard test card).
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "780561",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-078069"></a>

### TC-078069 (BRDE_TS22_TC49) - segment 114 SKU Data Segment moved first

**Test name:** Financial Transaction Request – Message structure: SKU Data Segment moved first. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 847](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) · **Every field against ATL105:** [Result_MD_Folder/TC-078069.md](Result_MD_Folder/TC-078069.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `1031` | [TD-TC-078069.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json#L20) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| C-01 | Same request as TC-078060 (BRDE_TS22_TC6) | Test case defect | `expected 1 vs 0` | TD-TC-078069.json vs TD-TC-078060.json | Make the request of the decline test different (decline test card, amount or simulator rule). |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 847](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of p.251 has no quoted text | Citation issue | `spec_basis` = `[{"rule_id": "BR-251-2", "page": 251, "source_id": "SRC-ATL105-PDF-001"}]` | test case record [test_cases.jsonl line 847](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) → `spec_basis` | Add the ATL105 sentence the rule is based on. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-078069.json line 14](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json#L14) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-078069.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "SKU Data Segment": {
      "SegmentType": "114",
      "SegmentLength": "1010",
      "SKUData": "CLB28O3JSTM7F1ZKL5MYVUG4IYNDHHV56I3ATMXQI1H1009CA535GYWF25W1RLAUQAPS24DWGXD7I5W2SEOZT4QNDWYQOJIHTIGDFAVO3JQUTJ1MVPA29TN4Z241SG6EOE7DROWSM61CWLM9NWK35PYYGSZHI7SDBAGDDXDIY5DFBQCDTWSW0QULKYQIHEAU0MS3ICYOOR7XX8N987OZP6RU13YUEYIWY8SQ02AMI0T1ANMXJUNTJ692NV73GYJHUQ5A6UD15U6YDO04IUB41HSKAM23VPNT1X1YBVIHK7TYIHZO182EBD4USODAE92BCRGPHN91SJSZS51JAXYUDOT046ANXRBAKPQ6A18AAYG02JIR0QK56DVG2P1BPOY5FWSTARVHDU5VFVOKYG1F1DHTJNI94ZUSRXYT90A8JXYDNE8Y1A8770QTGKDRC8H9LJNJ333NN1ENFL4P55WC778CP5LCNEJXFQ06IQ9OPDH5DZ6S4P68M7RZ7M9CQ5UIV9SZSV4E93D4YTC671A632BAPAWIRIQXUQJHJ6LSXNYUI8MCYGTIR1I7ELVZ49F8S60S8DX8KJJK7MK4V1XOEI1SHAR9AR82W7719IO0RVBOSQFYSW6XYWT5KRGGA94VMP8Y9LT1FOSOBSEL4TMTNK7CAEBM1UA5MAL98TWR7VJXPD103VU93C5ZCZGYN60KH2AZVOTG8FAAT7QDTA984H89NT43OE71KKV8WGXH1I7AOH7KVGJHG7V5TALXOW5GUTWFK03LSR83WAQ4AFYBKONLBI4YZZ0AQK4MUWD3GIS0ERLS15MGNHI90AJ7FDR04IJK4DIMH97HV4BBKE1GK2P0X0FBSMTZBPB6I1JRV7T6OGSDYFJZZ3PQMEMH1WZ7LTTYY5QJVP7CKSYHBTSRVPZBG352VCHX75RUPJI4MN2TE27J82PXLSECV940717TS28XTGZENYV96AW4T9J6SZWWKGAMMORAQ6BH9BG1"
    },
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5169510453402756",
      "CardDiscretionaryBlockData": "1031",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "780691",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-078069.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "SKU Data Segment": {
      "SegmentType": "114",
      "SegmentLength": "1010",  // D-13/I-01: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "SKUData": "CLB28O3JSTM7F1ZKL5MYVUG4IYNDHHV56I3ATMXQI1H1009CA535GYWF25W1RLAUQAPS24DWGXD7I5W2SEOZT4QNDWYQOJIHTIGDFAVO3JQUTJ1MVPA29TN4Z241SG6EOE7DROWSM61CWLM9NWK35PYYGSZHI7SDBAGDDXDIY5DFBQCDTWSW0QULKYQIHEAU0MS3ICYOOR7XX8N987OZP6RU13YUEYIWY8SQ02AMI0T1ANMXJUNTJ692NV73GYJHUQ5A6UD15U6YDO04IUB41HSKAM23VPNT1X1YBVIHK7TYIHZO182EBD4USODAE92BCRGPHN91SJSZS51JAXYUDOT046ANXRBAKPQ6A18AAYG02JIR0QK56DVG2P1BPOY5FWSTARVHDU5VFVOKYG1F1DHTJNI94ZUSRXYT90A8JXYDNE8Y1A8770QTGKDRC8H9LJNJ333NN1ENFL4P55WC778CP5LCNEJXFQ06IQ9OPDH5DZ6S4P68M7RZ7M9CQ5UIV9SZSV4E93D4YTC671A632BAPAWIRIQXUQJHJ6LSXNYUI8MCYGTIR1I7ELVZ49F8S60S8DX8KJJK7MK4V1XOEI1SHAR9AR82W7719IO0RVBOSQFYSW6XYWT5KRGGA94VMP8Y9LT1FOSOBSEL4TMTNK7CAEBM1UA5MAL98TWR7VJXPD103VU93C5ZCZGYN60KH2AZVOTG8FAAT7QDTA984H89NT43OE71KKV8WGXH1I7AOH7KVGJHG7V5TALXOW5GUTWFK03LSR83WAQ4AFYBKONLBI4YZZ0AQK4MUWD3GIS0ERLS15MGNHI90AJ7FDR04IJK4DIMH97HV4BBKE1GK2P0X0FBSMTZBPB6I1JRV7T6OGSDYFJZZ3PQMEMH1WZ7LTTYY5QJVP7CKSYHBTSRVPZBG352VCHX75RUPJI4MN2TE27J82PXLSECV940717TS28XTGZENYV96AW4T9J6SZWWKGAMMORAQ6BH9BG1"
    },
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5169510453402756",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3110 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3110 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "780691",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-077849"></a>

### TC-077849 (BRDE_TS12_TC23) - Cryptogram Token Data value not in its documented valid values

**Test name:** NFC Payment Tokenization Data Segment: Cryptogram Token Data value not in its documented valid values. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `pending_sme` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 627](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627) · **Every field against ATL105:** [Result_MD_Folder/TC-077849.md](Result_MD_Folder/TC-077849.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0232` | [TD-TC-077849.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| C-01 | Same request as TC-077848 (BRDE_TS12_TC2) | Test case defect | `expected 1 vs 0` | TD-TC-077849.json vs TD-TC-077848.json | Make the request of the decline test different (decline test card, amount or simulator rule). |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Cryptogram Token Data: B64 Variable Length: 28/56 bytes max 56"` | test case record [test_cases.jsonl line 627](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L627) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077849.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077849.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5179940547045436",
      "CardDiscretionaryBlockData": "0232",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "778491",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "821"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077849.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13/I-01: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5179940547045436",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3202 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3202 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "778491",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "821"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-077229"></a>

### TC-077229 (BRDE_TS1_TC29) - Terminal Identifier one byte over its documented maximum length (22)

**Test name:** Standard Message Data Segment: Terminal Identifier one byte over its documented maximum length (22). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) · **Every field against ATL105:** [Result_MD_Folder/TC-077229.md](Result_MD_Folder/TC-077229.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0231` | [TD-TC-077229.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Terminal Identifier: AN max 22"` | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-077229.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=42B3EFDEDF06EDD1, 9F36=4BB2, 9F37=EA771E6A, 95=8000008000` | [TD-TC-077229.json line 54](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `AAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 23 (this placeholder) plus the length of the real Terminal Identifier. |
| C-07 | The 'different terminal' is a generated value not set up at BUYPASS | To confirm with BUYPASS / SME | `AAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Use a second real terminal of the same merchant. |
| N-01 | Terminal Identifier is not valid | Intended violation of a negative test (correct) | `AAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value that meets: Device Type 'AA' + State Code 'AA' (not in Appendix D) + Merchant Number and Device Number 'AAAAAAAAAAAAAAAAAAA'.`). |

<details><summary>Test data - actual, step 1: TD-TC-077229.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "082",
      "InformationByte": "0",
      "TerminalIdentifier": "AAAAAAAAAAAAAAAAAAAAAAA",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5108703576384621",
      "CardDiscretionaryBlockData": "0231",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772291",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601179C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260842B3EFDEDF06EDD19F2701809F3303E0F0C89F34035E03029F3501229F36024BB29F3704EA771E6A9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077229.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "082",  // D-13/I-01/C-07: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<second Terminal Identifier of the same merchant, assigned by BUYPASS>",  // N-01: different from step None ('A value that meets: Device Type 'AA' + State Code 'AA' (not in Appendix D) + Merchant Number and Device Number 'AAAAAAAAAAAAAAAAAAA'.') on purpose - the rule this negative test breaks; keep; I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal; C-07: a second real terminal of the same merchant (still different from step None)
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5108703576384621",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3102 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3102 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772291",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601179C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260842B3EFDEDF06EDD19F2701809F3303E0F0C89F34035E03029F3501229F36024BB29F3704EA771E6A9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-078058"></a>

### TC-078058 (BRDE_TS22_TC4) - 104 Purchase Card Data Segment present

**Test name:** Financial Transaction Request – Message structure: 104 Purchase Card Data Segment present. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) · **Every field against ATL105:** [Result_MD_Folder/TC-078058.md](Result_MD_Folder/TC-078058.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `1229` | [TD-TC-078058.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of p.168 has no quoted text | Citation issue | `spec_basis` = [{"rule_id": "BR-168-4", "page": 168, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-166-3", "page": 166, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-204-4", "page": 204, "source_id": "SRC-ATL105-PDF-001"}] | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `spec_basis` | Add the ATL105 sentence the rule is based on. |
| Q-01 | The citation of p.166 has no quoted text | Citation issue | `spec_basis` = [{"rule_id": "BR-168-4", "page": 168, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-166-3", "page": 166, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-204-4", "page": 204, "source_id": "SRC-ATL105-PDF-001"}] | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `spec_basis` | Add the ATL105 sentence the rule is based on. |
| Q-01 | The citation of p.204 has no quoted text | Citation issue | `spec_basis` = [{"rule_id": "BR-168-4", "page": 168, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-166-3", "page": 166, "source_id": "SRC-ATL105-PDF-001"}, {"rule_id": "BR-204-4", "page": 204, "source_id": "SRC-ATL105-PDF-001"}] | test case record [test_cases.jsonl line 836](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) → `spec_basis` | Add the ATL105 sentence the rule is based on. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-078058.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| I-06 | Purchase Card Data Segment: content is synthesized, not client data | Awaiting client input / run-time value | `format_synthesis` | [TD-TC-078058.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L27) → `Purchase Card Data Segment` | Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then. |

<details><summary>Test data - actual, step 1: TD-TC-078058.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5189880925009349",
      "CardDiscretionaryBlockData": "1229",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "780581",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Purchase Card Data Segment": {
      "SegmentType": "104",
      "SegmentLength": "086",
      "PurchaseCode": "VZNTKUE7YXJYC0YH",
      "PCTaxAmount": "9625415",
      "PCFreightAmount": "6018115",
      "PCDutyAmount": "5843419",
      "ShiptoCountryCode": "772",
      "ShiptoPostalCode": "H7RUDWBHUW",
      "ShipfromPostalCode": "2W29IS6MSW",
      "DirectMarketingInvoiceNumber": "K2UILSZ7F3"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-078058.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13/I-01: recalculate: 055 + length of the track data + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5189880925009349",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2912 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2912 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "780581",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Purchase Card Data Segment": {  // I-06: Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then.
      "SegmentType": "104",
      "SegmentLength": "086",
      "PurchaseCode": "VZNTKUE7YXJYC0YH",
      "PCTaxAmount": "9625415",
      "PCFreightAmount": "6018115",
      "PCDutyAmount": "5843419",
      "ShiptoCountryCode": "772",
      "ShiptoPostalCode": "H7RUDWBHUW",
      "ShipfromPostalCode": "2W29IS6MSW",
      "DirectMarketingInvoiceNumber": "K2UILSZ7F3"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-077358"></a>

### TC-077358 (BRDE_TS2_TC65) - Segment 102 Segment Length one more than its rendered bytes

**Test name:** Product Code Data Segment: Segment Length one more than its rendered bytes. **What the test does:** .

**Status:** `blocked_no_test_data` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 136](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) · **Every field against ATL105:** [Result_MD_Folder/TC-077358.md](Result_MD_Folder/TC-077358.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Identifies the length of the segment in which it appears."` | test case record [test_cases.jsonl line 136](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-08 | No test data was generated for this host test case | Awaiting client input / run-time value | `test_data_file` = `null` | test case record [test_cases.jsonl line 136](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) → `test_data_file` | Generate the test data once the missing input is available. |

<a id="tc-077413"></a>

### TC-077413 (BRDE_TS4_TC9) - Information Byte exactly at its documented maximum length (1 bytes) is accepted

**Test name:** Totals Data Segment: Information Byte exactly at its documented maximum length (1 bytes) is accepted. **What the test does:** step 1 sends `` (?), expected Response Code 5/6/B/D/N/W.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) · **Every field against ATL105:** [Result_MD_Folder/TC-077413.md](Result_MD_Folder/TC-077413.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-15 | Information Byte (Totals) is not valid | Test data defect | `A` | [TD-TC-077413.json line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json#L8) → `Totals Data Segment → InformationByte` | A value that meets: 'A' = not valid. |
| C-04 | The test case accepts several Response Codes instead of one | Test case issue to correct | `expected_response.response_code (step 1)` = `null` | test case record [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) → `expected_response.response_code (step 1)` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Information Byte: AN max 1"` | test case record [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-05 | The device version fields of the Totals Request are generated values | Awaiting client input / run-time value | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | [TD-TC-077413.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json#L5) → `Totals Data Segment` | Use the versions of the certified device that runs the test. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` |  → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-077413.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "084",
      "InformationByte": "A",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "4C7QT1BE",
      "SoftwareVersion": "6XSCQHWT",
      "FirmwareVersion": "W6T6RW0B",
      "SequenceNumber": "774131",
      "CurrencyCode": "840",
      "GrandTotal": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077413.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // I-05: Use the versions of the certified device that runs the test.
      "SegmentType": "105",
      "SegmentLength": "084",
      "InformationByte": "A",  // D-15: A value that meets: 'A' = not valid.
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "4C7QT1BE",
      "SoftwareVersion": "6XSCQHWT",
      "FirmwareVersion": "W6T6RW0B",
      "SequenceNumber": "774131",
      "CurrencyCode": "840",
      "GrandTotal": "00000000"
    }
  }
}
```

</details>

<a id="tc-077480"></a>

### TC-077480 (BRDE_TS4_TC77) - Card Type Total Count one below its documented range (00001-99999)

**Test name:** Totals Data Segment: Card Type Total Count one below its documented range (00001-99999). **What the test does:** step 1 sends `` (?), expected Response Code `1`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) · **Every field against ATL105:** [Result_MD_Folder/TC-077480.md](Result_MD_Folder/TC-077480.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-09 | Card Label (Totals) is not valid | Test data defect | `` | [TD-TC-077480.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L5) → `Totals Data Segment → CardLabel` | A value that meets: Required. |
| D-09 | Card Type Total Amount (Totals) is not valid | Test data defect | `` | [TD-TC-077480.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L5) → `Totals Data Segment → CardTypeTotalAmount` | A value that meets: Required. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Valid Codes/Values: 00001-99999"` | test case record [test_cases.jsonl line 258](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| I-05 | The device version fields of the Totals Request are generated values | Awaiting client input / run-time value | `4C7QT1BE, 6XSCQHWT, W6T6RW0B` | [TD-TC-077480.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L5) → `Totals Data Segment` | Use the versions of the certified device that runs the test. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` |  → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| S-05 | The totals bucket is sent with a count of 00000 | To confirm with BUYPASS / SME | `00000` | [TD-TC-077480.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L20) → `Totals Data Segment → CardTypeTotalCount` | Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent. |
| N-01 | Segment 105 length is counted with the wrong separators and label width | Intended violation of a negative test (correct) | `089` | [TD-TC-077480.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L7) → `Totals Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `084`). |

<details><summary>Test data - actual, step 1: TD-TC-077480.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "4C7QT1BE",
      "SoftwareVersion": "6XSCQHWT",
      "FirmwareVersion": "W6T6RW0B",
      "SequenceNumber": "774801",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardTypeTotalCount": "00000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077480.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // I-05: Use the versions of the certified device that runs the test.
      "SegmentType": "105",
      "SegmentLength": "089",  // N-01: different from step None ('084') on purpose - the rule this negative test breaks; keep
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "4C7QT1BE",
      "SoftwareVersion": "6XSCQHWT",
      "FirmwareVersion": "W6T6RW0B",
      "SequenceNumber": "774801",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardTypeTotalCount": "00000"  // S-05: Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent.
    }
  }
}
```

</details>

<a id="tc-077553"></a>

### TC-077553 (BRDE_TS6_TC42) - Information Byte value not in its documented valid values

**Test name:** Electronic Mail Data Segment: Information Byte value not in its documented valid values. **What the test does:** step 1 sends `` (?), expected Response Code `1`.

**Status:** `ready` · **AI result:** REVIEW - Yes, after the points to confirm are answered · **Test case record:** [test_cases.jsonl line 331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L331) · **Every field against ATL105:** [Result_MD_Folder/TC-077553.md](Result_MD_Folder/TC-077553.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[0]` = `p.None: "Information Byte: AN max 1"` | test case record [test_cases.jsonl line 331](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L331) → `spec_basis[0]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| C-08 | Same request as TC-077550 (BRDE_TS6_TC5) | To confirm with BUYPASS / SME | `expected 1 vs 8/9/G/J` | TD-TC-077553.json vs TD-TC-077550.json | Check the difference on the device / in the response; optionally use another card. |
| SCOPE | Electronic Mail Request: not checked field by field | Not checked - request type outside the validator's trained ATL105 rules | `Electronic Mail Request` | TD-TC-077553.json | Check the request against its Chapter 11 layout by hand. |

<details><summary>Test data - actual, step 1: TD-TC-077553.json (click to expand)</summary>

```json
{
  "Electronic Mail Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Electronic Mail Data Segment": {
      "SegmentType": "109",
      "SegmentLength": "046",
      "InformationByte": "A",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "981",
      "BlockNumber": "000",
      "EmployeeNumber": "",
      "Password": "",
      "SequenceNumber": "000001",
      "LocalTime": "",
      "ExtractDate": "",
      "ExtractTime": "",
      "TextDataLength": "",
      "TextData": ""
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077553.json (changed lines end with //)</summary>

```jsonc
{
  "Electronic Mail Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Electronic Mail Data Segment": {
      "SegmentType": "109",
      "SegmentLength": "046",
      "InformationByte": "A",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "981",
      "BlockNumber": "000",
      "EmployeeNumber": "",
      "Password": "",
      "SequenceNumber": "000001",
      "LocalTime": "",
      "ExtractDate": "",
      "ExtractTime": "",
      "TextDataLength": "",
      "TextData": ""
    }
  }
}
```

</details>

<a id="tc-077628"></a>

### TC-077628 (BRDE_TS7_TC19) - Table 032 POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08): code 'X' outside its documented valid codes I/C

**Test name:** Variable Information Data Segment: POS Additional Information Indicator - Initial/Credential on File Indicator (Sub Table 08): code 'X' outside its documented valid codes I/C. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `requires_review` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 406](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406) · **Every field against ATL105:** [Result_MD_Folder/TC-077628.md](Result_MD_Folder/TC-077628.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-09 | Table 030 (POS Condition Code) is missing | Test data defect | `tables sent: 005, 032, 031` | [TD-TC-077628.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json#L27) → `Variable Information Data Segment` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| D-09 | The Credential on File indicators are missing | Test data defect | `tables sent: 005, 032, 031` | [TD-TC-077628.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json#L27) → `Variable Information Data Segment` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-077628.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `1229` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-077628.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5111617801756095",
      "CardDiscretionaryBlockData": "1229",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "776281",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "038",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001X"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077628.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5111617801756095",
      "CardDiscretionaryBlockData": "1229",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "776281",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {  // D-09: Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.; D-09: Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-t
      "SegmentType": "111",
      "SegmentLength": "038",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001X"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

## How to demonstrate the defects during the BA discussion

1. **Negative tests that break a second rule besides the one under test** - open [TC-077239](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) and read the test name and objective, then open [TD-TC-077239.json line 14](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L14) → `Standard Message Data Segment → AccountNumber`: actual `TOTALS REQUEST`; expected: A value that meets: Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule).
2. **Card read from the chip or stripe, but the card data is only an MMYY expiry** - open [TC-077295](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) and read the test name and objective, then open [TD-TC-077295.json line 15](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData`: actual `0931`; expected: `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known.
3. **The Terminal Identifier is a generated placeholder, yet the test cases are marked ready** - open [TC-077239](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) and read the test name and objective, then open [TD-TC-077239.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L9) → `Standard Message Data Segment → TerminalIdentifier`: actual `JX41894821001`; expected: Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier.
4. **Positive boundary test: the value is not at the documented limit or is not a valid value** - open [TC-077225](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L3) and read the test name and objective, then open [TD-TC-077225.json line 8](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json#L8) → `Standard Message Data Segment → InformationByte`: actual `A`; expected: A value that meets: 1 character: 'A' = not a valid code.
5. **Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply** - open [TC-077255](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L33) and read the test name and objective, then open [TD-TC-077255.json line 18](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json#L18) → `Standard Message Data Segment → FuelPurchaseAmount`: actual Fuel Purchase Amount '00000001' at a general (non-petroleum) merchant; expected: Send conditional fields only when their condition holds.
6. **Same request as another test case but a different expected result** - open [TC-078069](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L847) and read the test name and objective, then open TD-TC-078069.json vs TD-TC-078060.json: actual `expected 1 vs 0`; expected: Make the request of the decline test different (decline test card, amount or simulator rule).
7. **'Different terminal' negative tests use a terminal BUYPASS does not know** - open [TC-077229](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L7) and read the test name and objective, then open [TD-TC-077229.json line 9](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json#L9) → `Standard Message Data Segment → TerminalIdentifier`: actual `AAAAAAAAAAAAAAAAAAAAAAA`; expected: Use a second real terminal of the same merchant.
8. **Network token data (Segment 153) is synthesized, and its indicator and length need confirmation** - open [TC-078058](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L836) and read the test name and objective, then open [TD-TC-078058.json line 27](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json#L27) → `Purchase Card Data Segment`: actual `format_synthesis`; expected: Client to supply a purchase (commercial) card and its purchase-card data; keep the test case out of 'ready' until then.
9. **Follow-on values are instruction text to be filled in from the previous response** - open [TC-078056](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L834) and read the test name and objective, then open [TD-TC-078056.json line 7](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json#L7) → `Standard Message Data Segment → SegmentLength`: actual `086`; expected: Recalculate the Segment Length after filling in the real values.
10. **EMV chip data is generated, not captured from a card or simulator** - open [TC-077239](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) and read the test name and objective, then open [TD-TC-077239.json line 51](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum`: actual `2012091700000100000102171`; expected: Confirm the checksum of the host's current CA Public Key file before execution.
11. **Segment 100 value awaited from the client (<AWAITING:...>)** - open [TC-077295](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L73) and read the test name and objective, then open [TD-TC-077295.json line 16](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData`: actual `<AWAITING:EncryptedPINBlockData>`; expected: Client to supply the value (for Account Number: a MasterCard test card).
12. **Totals Request device versions are generated placeholders** - open [TC-077469](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L247) and read the test name and objective, then open [TD-TC-077469.json line 5](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json#L5) → `Totals Data Segment`: actual `4C7QT1BE, 6XSCQHWT, W6T6RW0B`; expected: Use the versions of the certified device that runs the test.
13. **No test data generated for a host test case** - open [TC-077358](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) and read the test name and objective, then open test case record [test_cases.jsonl line 136](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L136) → `test_data_file`: actual `test_data_file` = `null`; expected: Generate the test data once the missing input is available.
14. **Expected Response Code not stated for a step, or several codes accepted** - open [TC-077413](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) and read the test name and objective, then open test case record [test_cases.jsonl line 191](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L191) → `expected_response.response_code (step 1)`: actual `expected_response.response_code (step 1)` = `null`; expected: State the one expected Response Code for this transaction type (from BUYPASS or a reference run).
15. **Other points to confirm** - open [TC-077480](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L258) and read the test name and objective, then open [TD-TC-077480.json line 20](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json#L20) → `Totals Data Segment → CardTypeTotalCount`: actual `00000`; expected: Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent.
16. **Same request as another test case (difference only on the device / response side)** - open [TC-077553](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L331) and read the test name and objective, then open TD-TC-077553.json vs TD-TC-077550.json: actual `expected 1 vs 8/9/G/J`; expected: Check the difference on the device / in the response; optionally use another card.
17. **Expiry format for stored credentials (entry mode 10) not stated** - open [TC-077628](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L406) and read the test name and objective, then open Card Discretionary Block Data (entry mode 10 steps): actual `1229`; expected: Confirm the expiry format for entry mode 10 with BUYPASS.
18. **ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context** - open [TC-077239](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) and read the test name and objective, then open test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl#L17) → `spec_basis[0]`: actual `spec_basis[0]` = `p.None: "Card Discretionary Block Data: AN max 51"`; expected: Cite the Chapter 13 page of the element and quote its ATL105 text word for word.

## Summary for BA

**18 defects in 19 of the 19 test cases; 6 of them are high priority.**

High priority:

- Negative tests that break a second rule besides the one under test - 6 test case(s).
- Card read from the chip or stripe, but the card data is only an MMYY expiry - 11 test case(s).
- The Terminal Identifier is a generated placeholder, yet the test cases are marked ready - 17 test case(s).
- Positive boundary test: the value is not at the documented limit or is not a valid value - 6 test case(s).
- Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply - 2 test case(s).
- Same request as another test case but a different expected result - 2 test case(s).

Medium and low priority: 'Different terminal' negative tests use a terminal BUYPASS does not know (1); Network token data (Segment 153) is synthesized, and its indicator and length need confirmation (1); Follow-on values are instruction text to be filled in from the previous response (1); EMV chip data is generated, not captured from a card or simulator (9); Segment 100 value awaited from the client (<AWAITING:...>) (2); Totals Request device versions are generated placeholders (3); No test data generated for a host test case (1); Expected Response Code not stated for a step, or several codes accepted (1); Other points to confirm (1); Same request as another test case (difference only on the device / response side) (1); Expiry format for stored credentials (entry mode 10) not stated (1); ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context (18).

The risk is misleading test results and coverage, not a demonstrated host defect: the requests must match the scenario each test claims, and nothing can run before the real client values are supplied.

<a id="appendix"></a>

## Appendix - run files checked, documents and how this sheet was produced

The run's `MANIFEST.json` lists 1,041 files with their SHA-256; 1,041 of them are in the folder. Files these test cases use:

| File | SHA-256 | Same as MANIFEST.json |
|---|---|---|
| [test_cases/test_cases.jsonl](../../Dhan_Singh_run_set_20-11/data_element_validation/test_cases/test_cases.jsonl) | `b460bf2da7f1c381…` | yes |
| [test_data/TD-TC-077239.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json) | `533e97f5a3d4ae9c…` | yes |
| [test_data/TD-TC-077295.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) | `fafc1a5376c0a01c…` | yes |
| [test_data/TD-TC-077381.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077381.json) | `9cf10402c1666b54…` | yes |
| [test_data/TD-TC-077469.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077469.json) | `d9c59782e4d03f6f…` | yes |
| [test_data/TD-TC-077225.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077225.json) | `61cec1e4c217336f…` | yes |
| [test_data/TD-TC-077230.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077230.json) | `eec3a5969ba59545…` | yes |
| [test_data/TD-TC-077240.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077240.json) | `849d73565435efc5…` | yes |
| [test_data/TD-TC-077255.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077255.json) | `92a9f9d887307c84…` | yes |
| [test_data/TD-TC-077261.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077261.json) | `03788ba4110df098…` | yes |
| [test_data/TD-TC-078056.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078056.json) | `904909730b6a4a9d…` | yes |
| [test_data/TD-TC-078069.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078069.json) | `6b548fd1d5a12ffe…` | yes |
| [test_data/TD-TC-077849.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077849.json) | `0f1cba269c77694e…` | yes |
| [test_data/TD-TC-077229.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077229.json) | `e5d182c30b4d2f45…` | yes |
| [test_data/TD-TC-078058.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-078058.json) | `f48afcfc38b764f4…` | yes |
| [test_data/TD-TC-077413.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077413.json) | `728e9ddb988d07b9…` | yes |
| [test_data/TD-TC-077480.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077480.json) | `9a7cc8b8543bc807…` | yes |
| [test_data/TD-TC-077553.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077553.json) | `c74caaabab8e364b…` | yes |
| [test_data/TD-TC-077628.json](../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077628.json) | `145fdfe7ee65197e…` | yes |

The other 996 test data files of the run were read as well, for the checks across test cases (Sequence Number per terminal, identical requests).

| Document | What it is |
|---|---|
| [Result_MD_Folder/ATL105-TD-Validation-Summary.md](Result_MD_Folder/ATL105-TD-Validation-Summary.md) | Summary: every finding with the ATL105 section, PDF page, printed page and exact text, how to check it by hand and the fix |
| [Result_MD_Folder/TC-077239.md](Result_MD_Folder/TC-077239.md) | BRDE_TS1_TC17: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077295.md](Result_MD_Folder/TC-077295.md) | BRDE_TS1_TC93: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077381.md](Result_MD_Folder/TC-077381.md) | BRDE_TS3_TC25: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077469.md](Result_MD_Folder/TC-077469.md) | BRDE_TS4_TC15: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077225.md](Result_MD_Folder/TC-077225.md) | BRDE_TS1_TC7: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077230.md](Result_MD_Folder/TC-077230.md) | BRDE_TS1_TC14: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077240.md](Result_MD_Folder/TC-077240.md) | BRDE_TS1_TC3: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077255.md](Result_MD_Folder/TC-077255.md) | BRDE_TS1_TC74: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077261.md](Result_MD_Folder/TC-077261.md) | BRDE_TS1_TC75: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-078056.md](Result_MD_Folder/TC-078056.md) | BRDE_TS22_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-078069.md](Result_MD_Folder/TC-078069.md) | BRDE_TS22_TC49: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077849.md](Result_MD_Folder/TC-077849.md) | BRDE_TS12_TC23: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077229.md](Result_MD_Folder/TC-077229.md) | BRDE_TS1_TC29: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-078058.md](Result_MD_Folder/TC-078058.md) | BRDE_TS22_TC4: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077358.md](Result_MD_Folder/TC-077358.md) | BRDE_TS2_TC65: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077413.md](Result_MD_Folder/TC-077413.md) | BRDE_TS4_TC9: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077480.md](Result_MD_Folder/TC-077480.md) | BRDE_TS4_TC77: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077553.md](Result_MD_Folder/TC-077553.md) | BRDE_TS6_TC42: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077628.md](Result_MD_Folder/TC-077628.md) | BRDE_TS7_TC19: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Manual Validation/TC-077239-Manual-Validation.md](Manual%20Validation/TC-077239-Manual-Validation.md) | BRDE_TS1_TC17: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-077295-Manual-Validation.md](Manual%20Validation/TC-077295-Manual-Validation.md) | BRDE_TS1_TC93: step-by-step manual validation guide with the ATL105 extracts |

Produced with (from `core-auth-ai-validation-framework/regression-artifact-validator`): `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11\data_element_validation" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output\Data_Element_Validation" --only TC-077239,TC-077295,TC-077381,TC-077469,TC-077225,TC-077230,TC-077240,TC-077255,TC-077261,TC-078056,TC-078069,TC-077849,TC-077229,TC-078058,TC-077358,TC-077413,TC-077480,TC-077553,TC-077628 --guides TC-077239,TC-077295 --sheet Data_Element_Validation_Defect_Sheet.md --title "Run set 20-11, data element validation part: defect sheet for the BA review" --scope "Data element validation part of the run set `Dhan_Singh_run_set_20-11`: negative and boundary tests of single data elements across the request types. The sheet shows every defect the validator finds in this part, with all affected test cases, and the actual and expected test data of 19 representative test cases." --groups 20-11-parts --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_de.md`. The validator ran on all 1,030 test cases of the run; this sheet covers the test cases above.

