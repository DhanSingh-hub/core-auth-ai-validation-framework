# Manual Validation - TC-001867 to TC-001871

Step-by-step guides for checking the five ATL105 test cases and their test data by hand, and for confirming or rejecting what the AI validator reported. Each guide contains the ATL105 text needed for its checks, so the specification does not have to be opened.

| Test case | Guide | Test data | AI result | Can it run as-is? |
|---|---|---|---|---|
| TC-001867 (BR1_TS5_TC1) - Purchase approved | [TC-001867-Manual-Validation.md](TC-001867-Manual-Validation.md) | [TD-TC-001867.json](../test_data/TD-TC-001867.json) | **REVIEW** | Yes, once the real Terminal Identifier is supplied |
| TC-001868 (BR1_TS5_TC3) - 3 failed swipes -> keyed entry -> approved | [TC-001868-Manual-Validation.md](TC-001868-Manual-Validation.md) | [TD-TC-001868.json](../test_data/TD-TC-001868.json) | **REVIEW** | Yes, once the real Terminal Identifier is supplied |
| TC-001869 (BR1_TS5_TC4) - Swiped/keyed at RFID-equipped terminal -> approved | [TC-001869-Manual-Validation.md](TC-001869-Manual-Validation.md) | [TD-TC-001869.json](../test_data/TD-TC-001869.json) | **FAIL** | No - fix the FAIL items first |
| TC-001870 (BR1_TS5_TC5) - Partial approval -> remainder on 2nd card (split tender) | [TC-001870-Manual-Validation.md](TC-001870-Manual-Validation.md) | [TD-TC-001870.step1.purchase.json](../test_data/TD-TC-001870.step1.purchase.json), [TD-TC-001870.step2.split-tender.json](../test_data/TD-TC-001870.step2.split-tender.json) | **FAIL** | No - fix the FAIL items first |
| TC-001871 (BR1_TS5_TC6) - Partial approval -> Purchase Reversal of approved amount | [TC-001871-Manual-Validation.md](TC-001871-Manual-Validation.md) | [TD-TC-001871.step1.purchase.json](../test_data/TD-TC-001871.step1.purchase.json), [TD-TC-001871.step2.reversal.json](../test_data/TD-TC-001871.step2.reversal.json) | **FAIL** | No - fix the FAIL items first |

## How to use a guide

1. Open the guide in preview (right-click the file, Open Preview) next to its test data file.
2. Follow section 2 of the guide: it lists the checks in order and links to each section.
3. In each table, compare the value in the file with the expected value and fill in the Your check column.
4. For each issue in section 9, follow 'How to confirm by hand' and record Agree or Disagree.
5. Complete the sign-off in section 12.

## Sources

- ATL105: BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026), text from `specifications/ATL105/docs/specs/extracted_text_new.txt`. 'PDF p.' is the page in the PDF viewer; 'printed' is the page number in the page footer.
- Test cases: [test_cases/test_cases.jsonl](../test_cases/test_cases.jsonl) (full records) and [test_cases/TEST_CASES.md](../test_cases/TEST_CASES.md) (summary).
- Test data: [test_data](../test_data).
- AI validator reports: [Result_MD_Folder](../Result_MD_Folder) - start with [ATL105-TD-Validation-Key-Findings.md](../Result_MD_Folder/ATL105-TD-Validation-Key-Findings.md).
- Regenerate these guides with `python scripts/build-manual-validation-guides.py` after the test cases or test data change.

