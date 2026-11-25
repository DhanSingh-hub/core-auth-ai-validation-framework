# Manual Validation - 5 test cases of the MasterCard Credit run of 19 Nov 2026

Step-by-step guides to check by hand each test case and its test data in the run [Dhan_Singh_run_set_20-11](../../Dhan_Singh_run_set_20-11/) against BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` - and to confirm or reject what the AI validation reported. The ATL105 text needed for every check is copied into section 11 of each guide with its PDF page and printed page, so the specification does not have to be opened.

**Result:** FAIL 23 · BLOCKED 14.

| Test case | Previous id | Display id | What it tests | Test data | AI result | Can it run as-is? | Guide | ATL105 result (every field) |
|---|---|---|---|---|---|---|---|---|
| TC-076120 | - | BR1_TS5_TC1 | Happy path: Purchase/Capture approved | [TD-TC-076120.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076120.json) | **BLOCKED** | Only after the client / run-time values are filled in | [TC-076120-Manual-Validation.md](TC-076120-Manual-Validation.md) | [TC-076120.md](../Result_MD_Folder/TC-076120.md) |
| TC-076166 | - | BR1_TS5_TC47 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-076166.step1.purchase.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step1.purchase.json), [TD-TC-076166.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json) | **FAIL** | No - fix the defects first | [TC-076166-Manual-Validation.md](TC-076166-Manual-Validation.md) | [TC-076166.md](../Result_MD_Folder/TC-076166.md) |
| TC-076167 | - | BR1_TS5_TC48 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076167.step1.purchase.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step1.purchase.json), [TD-TC-076167.step2.reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json) | **FAIL** | No - fix the defects first | [TC-076167-Manual-Validation.md](TC-076167-Manual-Validation.md) | [TC-076167.md](../Result_MD_Folder/TC-076167.md) |
| TC-076168 | - | BR1_TS5_TC49 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076168.step1.purchase.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step1.purchase.json), [TD-TC-076168.step2.timeout-reversal.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json) | **FAIL** | No - fix the defects first | [TC-076168-Manual-Validation.md](TC-076168-Manual-Validation.md) | [TC-076168.md](../Result_MD_Folder/TC-076168.md) |
| TC-076658 | - | BR1_TS15_TC7 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-076658.json](../../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json) | **BLOCKED** | Only after the client / run-time values are filled in | [TC-076658-Manual-Validation.md](TC-076658-Manual-Validation.md) | [TC-076658.md](../Result_MD_Folder/TC-076658.md) |

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

The same results in compact form (every segment and field with the ATL105 rule, the AI result and the ATL105 text): [Result_MD_Folder](../Result_MD_Folder/ATL105-TD-Validation-Summary.md). Defect sheets of this package (each defect with the actual and expected test data): [../MasterCard_Credit_Defect_Demo_Sheet.md](../MasterCard_Credit_Defect_Demo_Sheet.md).

## How the results were produced

- `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output" --only TC-076120,TC-076166,TC-076167,TC-076168,TC-076658,TC-075618,TC-075654,TC-075699,TC-075657,TC-076586,TC-075650,TC-075755,TC-075656,TC-075757,TC-075685,TC-075670,TC-077181,TC-077186,TC-077193,TC-077219,TC-076596,TC-076613,TC-075751,TC-075667,TC-076423,TC-077185,TC-077206,TC-077218,TC-076725,TC-076723,TC-075601,TC-075810,TC-075855,TC-076597,TC-076780,TC-077179,TC-077203 --guides TC-076120,TC-076166,TC-076167,TC-076168,TC-076658 --sheet MasterCard_Credit_Defect_Demo_Sheet.md --title "MasterCard Credit run set 20-11: defect demo sheet for the BA review" --scope "This sheet repeats the BA analysis of the 16-11 run on the new run set `Dhan_Singh_run_set_20-11` (main MasterCard Credit run, built 19 Nov 2026). It shows what happened to every defect the BA logged for the 16-11 run, every defect the validator finds in the new run with all affected test cases, and the actual and expected test data of 37 representative test cases. Every finding was produced by the ATL105 validator from the test case definitions and their test data files, and every ATL105 text quoted here was found word for word on its PDF page." --groups 20-11 --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_main.md --outro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\outro_main.md`
- The trained ATL105 validator (`scripts/validate-td-against-atl105-spec.py`) and the extended checks ran on all 1,623 test cases of the run, so the checks across test cases see every test case; the documents are written for the test cases above.
- Every ATL105 text in the guides was found on its PDF page in `specifications/ATL105/docs/specs/extracted_text_new.txt` (release 2026-4) when the guides were written.

