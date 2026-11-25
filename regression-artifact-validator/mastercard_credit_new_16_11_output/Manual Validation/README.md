# Manual Validation - MasterCard Credit run of 16 Nov 2026 (plan v12)

Validation of all 2,129 test cases and 2,422 test data files of the run [mastercard_credit_new_16_11](../../mastercard_credit_new_16_11/) against BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`, byte-identical to the validator's copy) - with a manual validation document for every test case and detailed step-by-step guides for 5 test cases.

**Overall:** FAIL 245 · BLOCKED 1,748 · REVIEW 0 · PASS 0 · DEVICE (device-only) 136.

| File | What it is |
|---|---|
| [ATL105_Run_Validation_Summary.md](ATL105_Run_Validation_Summary.md) | Detailed analysis of every test case and test data file: each defect with the ATL105 section, PDF page, printed page and exact text, affected test cases and files, how to check by hand and the fix |
| [ATL105_Run_Validation_All_Test_Cases.xlsx](ATL105_Run_Validation_All_Test_Cases.xlsx) | The same results as filterable sheets: Test cases (1 row per test case), Findings (every finding), Defects, Scenarios |
| [Test cases/README.md](Test%20cases/README.md) | One manual validation document per test case (field-by-field check against ATL105, findings, how the data was generated, what is missing, the ATL105 text), grouped by scenario |
| [TC-039912-Manual-Validation.md](TC-039912-Manual-Validation.md) | BR1_TS5_TC1 - Happy path: Purchase/Capture approved - **BLOCKED** |
| [TC-039913-Manual-Validation.md](TC-039913-Manual-Validation.md) | BR1_TS5_TC2 - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined - **BLOCKED** |
| [TC-039914-Manual-Validation.md](TC-039914-Manual-Validation.md) | BR1_TS5_TC3 - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined - **BLOCKED** |
| [TC-039915-Manual-Validation.md](TC-039915-Manual-Validation.md) | BR1_TS5_TC4 - Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined - **BLOCKED** |
| [TC-039916-Manual-Validation.md](TC-039916-Manual-Validation.md) | BR1_TS5_TC5 - Decision table: Authorization Only with segment 153 sent (the rule's condition holds) - **BLOCKED** |

## The 5 test cases with a detailed step-by-step guide

These guides go further than the per-test-case documents: they walk through every field with the ATL105 extract next to it, recalculate lengths and check digits, and explain each finding.

| Test case | Steps | Test data | AI result | Can it run as-is? |
|---|---|---|---|---|
| [TC-039912](TC-039912-Manual-Validation.md) (BR1_TS5_TC1) | 0 | [TD-TC-039912.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039912.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-039913](TC-039913-Manual-Validation.md) (BR1_TS5_TC2) | 0 -> 8 | [TD-TC-039913.step1.purchase.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json), [TD-TC-039913.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-039914](TC-039914-Manual-Validation.md) (BR1_TS5_TC3) | 0 -> 8 | [TD-TC-039914.step1.purchase.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-039915](TC-039915-Manual-Validation.md) (BR1_TS5_TC4) | 0 -> Z | [TD-TC-039915.step1.purchase.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-039916](TC-039916-Manual-Validation.md) (BR1_TS5_TC5) | 0 | [TD-TC-039916.json](../../mastercard_credit_new_16_11/test_data/TD-TC-039916.json) | **BLOCKED** | Only after the client / run-time values are filled in |

## How to check a test case by hand

1. Open [Test cases/README.md](Test%20cases/README.md), find the test case and open its document in preview (right-click → Open Preview), next to its test data file(s).
2. Follow section 1 of the document: it lists the checks in order.
3. In each table compare the value in the file with the ATL105 rule and the copied ATL105 text, and tick **Your check**.
4. For each finding note Agree / Disagree and complete the sign-off.

## How the results were produced

- `scripts/validate-run-and-build-manual-validation.py` runs the trained ATL105 validator (`scripts/validate-td-against-atl105-spec.py`) on every test case, adds the checks this run needs beyond the trained rules, classifies every finding and writes this folder. Re-run it after the test cases or test data change.
- ATL105 text: `specifications/ATL105/docs/specs/extracted_text_new.txt` (release 2026-4). Every quoted text is checked against its PDF page on every run; the script stops if one is not found.

