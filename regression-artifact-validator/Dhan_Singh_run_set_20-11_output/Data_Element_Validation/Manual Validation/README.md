# Manual Validation - 2 test cases of the MasterCard Credit run of 19 Nov 2026

Step-by-step guides to check by hand each test case and its test data in the run [data_element_validation](../../../Dhan_Singh_run_set_20-11/data_element_validation/) against BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` - and to confirm or reject what the AI validation reported. The ATL105 text needed for every check is copied into section 11 of each guide with its PDF page and printed page, so the specification does not have to be opened.

**Result:** FAIL 17 · BLOCKED 1 · REVIEW 1.

| Test case | Previous id | Display id | What it tests | Test data | AI result | Can it run as-is? | Guide | ATL105 result (every field) |
|---|---|---|---|---|---|---|---|---|
| TC-077239 | - | BRDE_TS1_TC17 | Card Discretionary Block Data one byte over its documented maximum length (51) | [TD-TC-077239.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077239.json) | **FAIL** | No - fix the defects first | [TC-077239-Manual-Validation.md](TC-077239-Manual-Validation.md) | [TC-077239.md](../Result_MD_Folder/TC-077239.md) |
| TC-077295 | - | BRDE_TS1_TC93 | Segment 100 Segment Length one more than its rendered bytes | [TD-TC-077295.json](../../../Dhan_Singh_run_set_20-11/data_element_validation/test_data/TD-TC-077295.json) | **FAIL** | No - fix the defects first | [TC-077295-Manual-Validation.md](TC-077295-Manual-Validation.md) | [TC-077295.md](../Result_MD_Folder/TC-077295.md) |

## How to validate a test case by hand

1. Open the guide in preview (right-click → Open Preview) and its test data file(s) next to it.
2. **Section 1** - what the test case does: objective, steps, Prompt Codes and expected Response Codes.
3. **Section 2** - the manual validation path: the checks in order.
4. **Section 3** - how every value was generated (the `method` and generator note recorded in the test case) and what is still missing before the test can run.
5. **Section 4** - every field of every segment: compare the value in the file with the ATL105 rule and tick **Your check**. The ATL105 text behind each rule is in **section 11**.
6. **Section 5** - recalculate the segment lengths, the card check digit and the expiry.
7. **Sections 6-8** - links between steps, expected Response Codes and the ATL105 text the test case quotes.
8. **Section 9** - confirm or reject each finding with its *How to confirm by hand* steps; **section 10** - checks across all test cases of the run.
9. **Section 12** - sign off.

The same results in compact form (every segment and field with the ATL105 rule, the AI result and the ATL105 text): [Result_MD_Folder](../Result_MD_Folder/ATL105-TD-Validation-Summary.md). Defect sheets of this package (each defect with the actual and expected test data): [../Data_Element_Validation_Defect_Sheet.md](../Data_Element_Validation_Defect_Sheet.md).

## How the results were produced

- `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11\data_element_validation" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output\Data_Element_Validation" --only TC-077239,TC-077295,TC-077381,TC-077469,TC-077225,TC-077230,TC-077240,TC-077255,TC-077261,TC-078056,TC-078069,TC-077849,TC-077229,TC-078058,TC-077358,TC-077413,TC-077480,TC-077553,TC-077628 --guides TC-077239,TC-077295 --sheet Data_Element_Validation_Defect_Sheet.md --title "Run set 20-11, data element validation part: defect sheet for the BA review" --scope "Data element validation part of the run set `Dhan_Singh_run_set_20-11`: negative and boundary tests of single data elements across the request types. The sheet shows every defect the validator finds in this part, with all affected test cases, and the actual and expected test data of 19 representative test cases." --groups 20-11-parts --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_de.md`
- The trained ATL105 validator (`scripts/validate-td-against-atl105-spec.py`) and the extended checks ran on all 1,030 test cases of the run, so the checks across test cases see every test case; the documents are written for the test cases above.
- Every ATL105 text in the guides was found on its PDF page in `specifications/ATL105/docs/specs/extracted_text_new.txt` (release 2026-4) when the guides were written.

