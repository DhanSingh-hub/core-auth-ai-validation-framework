# Manual Validation - 16 Nov 2026 MasterCard Credit run

Validation of all 1,861 test cases and 2,147 test data files of the run [mastercard_credit_new - Copy (2)](../mastercard_credit_new%20-%20Copy%20%282%29/) against BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the PDF attached to the run (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`, byte-identical to the validator's copy) - plus step-by-step manual validation guides for 5 test cases.

**Overall:** FAIL 154 · BLOCKED 1,570 · REVIEW 0 · PASS 0 · DEVICE (device-only) 137.

| File | What it is |
|---|---|
| [ATL105_Run_Validation_Summary.md](ATL105_Run_Validation_Summary.md) | Detailed analysis of every test case and test data file: each defect with the ATL105 section, PDF page, printed page and exact text, affected test cases and files, how to check by hand and the fix |
| [ATL105_Run_Validation_All_Test_Cases.xlsx](ATL105_Run_Validation_All_Test_Cases.xlsx) | The same results as filterable sheets: Test cases (1 row per test case), Findings (every finding), Defects, Scenarios |
| [TC-020803-Manual-Validation.md](TC-020803-Manual-Validation.md) | BR1_TS5_TC1 - Happy path: Purchase/Capture approved - **BLOCKED** |
| [TC-020804-Manual-Validation.md](TC-020804-Manual-Validation.md) | BR1_TS5_TC2 - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined - **BLOCKED** |
| [TC-020805-Manual-Validation.md](TC-020805-Manual-Validation.md) | BR1_TS5_TC3 - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined - **BLOCKED** |
| [TC-020806-Manual-Validation.md](TC-020806-Manual-Validation.md) | BR1_TS5_TC4 - Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined - **BLOCKED** |
| [TC-022362-Manual-Validation.md](TC-022362-Manual-Validation.md) | BR1_TS5_TC5 - Decision table: Authorization Only with segment 153 sent (the rule's condition holds) - **BLOCKED** |

## The 5 test cases with manual guides

The first five test cases of scenario BR1_TS5 (Purchase/Capture – Manual / Keyed, the scenario of the earlier manual review): one approval, three negative follow-on tests (Purchase Reversal with a different Sequence Number, Purchase Reversal from a different terminal, Time-out Reversal from a different terminal) and one decision-table test with Segment 153.

| Test case | Steps | Test data | AI result | Can it run as-is? |
|---|---|---|---|---|
| [TC-020803](TC-020803-Manual-Validation.md) (BR1_TS5_TC1) | 0 | [TD-TC-020803.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020803.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-020804](TC-020804-Manual-Validation.md) (BR1_TS5_TC2) | 0 -> 8 | [TD-TC-020804.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step1.purchase.json), [TD-TC-020804.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020804.step2.reversal.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-020805](TC-020805-Manual-Validation.md) (BR1_TS5_TC3) | 0 -> 8 | [TD-TC-020805.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020805.step1.purchase.json), [TD-TC-020805.step2.reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020805.step2.reversal.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-020806](TC-020806-Manual-Validation.md) (BR1_TS5_TC4) | 0 -> Z | [TD-TC-020806.step1.purchase.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020806.step1.purchase.json), [TD-TC-020806.step2.timeout-reversal.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-020806.step2.timeout-reversal.json) | **BLOCKED** | Only after the client / run-time values are filled in |
| [TC-022362](TC-022362-Manual-Validation.md) (BR1_TS5_TC5) | 0 | [TD-TC-022362.json](../mastercard_credit_new%20-%20Copy%20%282%29/test_data/TD-TC-022362.json) | **BLOCKED** | Only after the client / run-time values are filled in |

## How to use a guide

1. Open the guide in preview (right-click → Open Preview) next to its test data file(s).
2. Follow section 2 of the guide: it lists the checks in order.
3. In each table compare the value in the file with the expected value and fill in **Your check**.
4. For each finding in section 9 follow **How to confirm by hand** and record Agree / Disagree.
5. Complete the sign-off (section 12).

## How the results were produced

- `scripts/validate-run-and-build-manual-validation.py` runs the trained ATL105 validator (`scripts/validate-td-against-atl105-spec.py`) on every test case, adds the checks this run needs beyond the trained rules, classifies every finding and writes this folder. Re-run it after the test cases or test data change.
- ATL105 text: `specifications/ATL105/docs/specs/extracted_text_new.txt` (release 2026-4). Every quoted text is checked against its PDF page on every run; the script stops if one is not found.

