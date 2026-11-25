# Manual Validation - 1 test cases of the MasterCard Credit run of 19 Nov 2026

Step-by-step guides to check by hand each test case and its test data in the run [terminal_and_host_operations](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/) against BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` - and to confirm or reject what the AI validation reported. The ATL105 text needed for every check is copied into section 11 of each guide with its PDF page and printed page, so the specification does not have to be opened.

**Result:** BLOCKED 2 · REVIEW 1.

| Test case | Previous id | Display id | What it tests | Test data | AI result | Can it run as-is? | Guide | ATL105 result (every field) |
|---|---|---|---|---|---|---|---|---|
| TC-078259 | - | BROP_TS2_TC1 | Totals for the current settlement date and end of day (222222) | [TD-TC-078259.json](../../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json) | **BLOCKED** | Only after the client / run-time values are filled in | [TC-078259-Manual-Validation.md](TC-078259-Manual-Validation.md) | [TC-078259.md](../Result_MD_Folder/TC-078259.md) |

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

The same results in compact form (every segment and field with the ATL105 rule, the AI result and the ATL105 text): [Result_MD_Folder](../Result_MD_Folder/ATL105-TD-Validation-Summary.md). Defect sheets of this package (each defect with the actual and expected test data): [../Terminal_Host_Operations_Defect_Sheet.md](../Terminal_Host_Operations_Defect_Sheet.md).

## How the results were produced

- `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11\terminal_and_host_operations" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output\Terminal_Host_Operations" --only TC-078259,TC-078299,TC-078269 --guides TC-078259 --sheet Terminal_Host_Operations_Defect_Sheet.md --title "Run set 20-11, terminal & host operations part: defect sheet for the BA review" --scope "Terminal & host operations part of the run set `Dhan_Singh_run_set_20-11`: totals, downloads, key loads, electronic mail, communications test and device behaviour. The sheet shows every finding of this part, with all affected test cases, and the test data of 3 representative test cases." --groups 20-11-parts --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_th.md`
- The trained ATL105 validator (`scripts/validate-td-against-atl105-spec.py`) and the extended checks ran on all 47 test cases of the run, so the checks across test cases see every test case; the documents are written for the test cases above.
- Every ATL105 text in the guides was found on its PDF page in `specifications/ATL105/docs/specs/extracted_text_new.txt` (release 2026-4) when the guides were written.

