# Maestro Direct Debit – defect fix verification (previous run 31-10-out → new run 5-11-out)

| Item | Value |
|---|---|
| Previous run | `C:/Users/F7OKAGW/Downloads/31-10-out/maestro_direct_debit` – 278 test cases, 536 test data files (identical copy in the workspace: `maestro_direct_debit/test_data`) |
| New run | `5-11-out/maestro_direct_debit` – 257 test cases, 532 test data files |
| Defect log(s) | none – Maestro was missed in the previous logs; its defects were found now in the previous run: [maestro_direct_debit/Manual Validation Report/Consolidated_Defect_Report.md](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md) (D-xx) |
| Test case matching | IDs are renumbered in the new run; test cases are matched by transaction category + entry mode + template (unique in both runs), flow steps by step number and role. 215 of 278 previous test cases have a match; 63 were removed, 42 are new |

Status: **FIXED** – every previously failing file now passes the rule · **FIXED – value awaiting client input** – the generator now builds the field correctly, but its value can only come from the client (listed in the test case's `awaiting_inputs`, status `needs_client_input`) · **PARTIALLY FIXED** – some files pass, some still fail · **NOT FIXED** – still failing · **CLOSED – test cases removed** – the affected test cases are no longer in the catalog.

## 1. Summary

| Defect | Source ID | What was wrong | Previous run: TCs / files (logged) | New run: TCs / files still failing | Status |
|---|---|---|---|---|---|
| [D-01](#d-01) | = CD-01 | EMV Card Sequence Number (Element 188) has the wrong width | 72 / 90 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-02](#d-02) | = CD-02 | EMV Additional Information (Element 118) does not contain EMV Table Data | 72 / 90 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-09](#d-09) | = CD-04 | Credential on File indicators (Tables 032, 049, 056) are missing | 35 / 67 (missed – not in the previous logs) | 0 / 0 | **CLOSED – test cases removed** |
| [D-12](#d-12) | = CD-03 | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | 107 / 157 (missed – not in the previous logs) | 0 / 0 | **FIXED – value awaiting client input** |
| [D-14](#d-14) | = CD-07 | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | 7 / 7 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-15](#d-15) | – | The card data (Account Number, Card Discretionary Block Data) is empty | 249 / 473 (missed – not in the previous logs) | 0 / 0 | **FIXED – value awaiting client input** |
| [D-16](#d-16) | – | The debit PIN block (Element 33) is empty | 242 / 305 (missed – not in the previous logs) | 0 / 0 | **FIXED – value awaiting client input** |
| [D-17](#d-17) | – | A keyed (manual) entry mode is used for a debit card | 34 / 43 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-18](#d-18) | = CD-15 | Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back | 4 / 4 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-19](#d-19) | = CD-10 | Totals Request: Card Label (Element 13) is not 4 characters | 63 / 63 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-20](#d-20) | – | Totals Request: Segment 105 length is counted with the wrong layout | 63 / 63 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [D-21](#d-21) | – | Totals Request: the totals do not match the transactions of the flow | 14 / 14 (missed – not in the previous logs) | 12 / 12 | **NOT FIXED** |
| [D-24](#d-24) | – | A test data value differs from the test case it was generated from | 14 / 14 (missed – not in the previous logs) | 0 / 0 | **FIXED** |
| [CD-19](#cd-19) | – | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 287 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |

## 2. Defect details

<a id="d-01"></a>

### D-01 – EMV Card Sequence Number (Element 188) has the wrong width – FIXED

| | |
|---|---|
| Field | Element 188 EMV Card Sequence Number (source ID = CD-01) |
| Previous run | 72 test cases / 90 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 88 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `001` (88 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L47) line 47 → `01`. New (TC-001507): [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L52) line 52 → `001`<br>Expected: 001 (Tag 5F34 zero-padded to 3) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003177 (1 timeout reversal) | [TD-TC-003177.json](../maestro_direct_debit/test_data/TD-TC-003177.json#L47) line 47 | `01` | TC-001519 | [TD-TC-001519.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001519.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-003207 (1 timeout reversal) | [TD-TC-003207.json](../maestro_direct_debit/test_data/TD-TC-003207.json#L47) line 47 | `01` | TC-001549 | [TD-TC-001549.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001549.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-003165 (1 purchase) | [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L47) line 47 | `01` | TC-001507 | [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L52) line 52 | `001` | FIXED |
| 4 | TC-003166 (1 purchase) | [TD-TC-003166.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003166.step1.purchase.json#L47) line 47 | `01` | TC-001508 | [TD-TC-001508.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001508.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 5 | TC-003167 (1 purchase) | [TD-TC-003167.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003167.step1.purchase.json#L47) line 47 | `01` | TC-001509 | [TD-TC-001509.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001509.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 6 | TC-003168 (1 purchase) | [TD-TC-003168.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003168.step1.purchase.json#L47) line 47 | `01` | TC-001510 | [TD-TC-001510.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001510.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 7 | TC-003169 (1 purchase) | [TD-TC-003169.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003169.step1.purchase.json#L47) line 47 | `01` | TC-001511 | [TD-TC-001511.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 8 | TC-003169 (3 next transaction) | [TD-TC-003169.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003169.step3.next-transaction.json#L47) line 47 | `01` | TC-001511 | [TD-TC-001511.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step3.next-transaction.json#L52) line 52 | `001` | FIXED |
| 9 | TC-003170 (1 purchase) | [TD-TC-003170.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003170.step1.purchase.json#L47) line 47 | `01` | TC-001512 | [TD-TC-001512.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 10 | TC-003171 (1 purchase) | [TD-TC-003171.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003171.step1.purchase.json#L47) line 47 | `01` | TC-001513 | [TD-TC-001513.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 11 | TC-003172 (1 purchase) | [TD-TC-003172.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003172.step1.purchase.json#L47) line 47 | `01` | TC-001514 | [TD-TC-001514.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 12 | TC-003173 (1 purchase) | [TD-TC-003173.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003173.step1.purchase.json#L47) line 47 | `01` | TC-001515 | [TD-TC-001515.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 13 | TC-003173 (2 return) | [TD-TC-003173.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003173.step2.return.json#L47) line 47 | `01` | TC-001515 | [TD-TC-001515.step2.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step2.return.json#L52) line 52 | `001` | FIXED |
| 14 | TC-003174 (1 purchase) | [TD-TC-003174.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003174.step1.purchase.json#L47) line 47 | `01` | TC-001516 | [TD-TC-001516.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 15 | TC-003175 (1 purchase) | [TD-TC-003175.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003175.step1.purchase.json#L47) line 47 | `01` | TC-001517 | [TD-TC-001517.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 16 | TC-003176 (1 purchase) | [TD-TC-003176.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003176.step1.purchase.json#L47) line 47 | `01` | TC-001518 | [TD-TC-001518.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 17 | TC-003178 (1 purchase) | [TD-TC-003178.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003178.step1.purchase.json#L47) line 47 | `01` | TC-001520 | [TD-TC-001520.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001520.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 18 | TC-003179 (1 purchase) | [TD-TC-003179.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003179.step1.purchase.json#L47) line 47 | `01` | TC-001521 | [TD-TC-001521.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 19 | TC-003179 (2 next transaction) | [TD-TC-003179.step2.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003179.step2.next-transaction.json#L47) line 47 | `01` | TC-001521 | [TD-TC-001521.step2.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step2.next-transaction.json#L52) line 52 | `001` | FIXED |
| 20 | TC-003180 (1 purchase) | [TD-TC-003180.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003180.step1.purchase.json#L47) line 47 | `01` | TC-001522 | [TD-TC-001522.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 21 | TC-003180 (3 return) | [TD-TC-003180.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003180.step3.return.json#L47) line 47 | `01` | TC-001522 | [TD-TC-001522.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step3.return.json#L52) line 52 | `001` | FIXED |
| 22 | TC-003181 (1 purchase) | [TD-TC-003181.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003181.step1.purchase.json#L47) line 47 | `01` | TC-001523 | [TD-TC-001523.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 23 | TC-003181 (3 next transaction) | [TD-TC-003181.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003181.step3.next-transaction.json#L47) line 47 | `01` | TC-001523 | [TD-TC-001523.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step3.next-transaction.json#L52) line 52 | `001` | FIXED |
| 24 | TC-003182 (1 purchase) | [TD-TC-003182.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003182.step1.purchase.json#L47) line 47 | `01` | TC-001524 | [TD-TC-001524.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 25 | TC-003182 (2 resubmitted) | [TD-TC-003182.step2.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003182.step2.resubmitted.json#L47) line 47 | `01` | TC-001524 | [TD-TC-001524.step2.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step2.resubmitted.json#L52) line 52 | `001` | FIXED |

… 65 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-02"></a>

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data – FIXED

| | |
|---|---|
| Field | Element 118 EMV Additional Information (with Elements 191/192) (source ID = CD-02) |
| Previous run | 72 test cases / 90 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 88 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `EMVYES / length 006` (88 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L52) line 52 → `79JKJS8XQOD3B02M…XZ6KH66MJC (984 cha…length 984 (52 chars)`. New (TC-001507): [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L57) line 57 → `EMVYES / length 006`<br>Expected: EMVYES or EMVNOT / length 006 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003177 (1 timeout reversal) | [TD-TC-003177.json](../maestro_direct_debit/test_data/TD-TC-003177.json#L52) line 52 | `79JKJS8XQOD3B02M…XZ6KH66MJC (984 chars) / length 984` | TC-001519 | [TD-TC-001519.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001519.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-003207 (1 timeout reversal) | [TD-TC-003207.json](../maestro_direct_debit/test_data/TD-TC-003207.json#L52) line 52 | `WANM8BEIKU2EMI5F…8EEPV5QCP2 (984 chars) / length 984` | TC-001549 | [TD-TC-001549.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001549.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-003165 (1 purchase) | [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L52) line 52 | `79JKJS8XQOD3B02M…XZ6KH66MJC (984 chars) / length 984` | TC-001507 | [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 4 | TC-003166 (1 purchase) | [TD-TC-003166.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003166.step1.purchase.json#L52) line 52 | `XD3B9ERSHUFGGMUM…1RNP595D3H (984 chars) / length 984` | TC-001508 | [TD-TC-001508.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001508.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 5 | TC-003167 (1 purchase) | [TD-TC-003167.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003167.step1.purchase.json#L52) line 52 | `EZYXO95NZDIUXHXU…WWITX71M9A (984 chars) / length 984` | TC-001509 | [TD-TC-001509.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001509.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 6 | TC-003168 (1 purchase) | [TD-TC-003168.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003168.step1.purchase.json#L52) line 52 | `V25O3QK6878KAXZX…1356MTOJTZ (984 chars) / length 984` | TC-001510 | [TD-TC-001510.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001510.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 7 | TC-003169 (1 purchase) | [TD-TC-003169.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003169.step1.purchase.json#L52) line 52 | `OUBUQ8QNBG8Q99ML…5DHCTM09M3 (984 chars) / length 984` | TC-001511 | [TD-TC-001511.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 8 | TC-003169 (3 next transaction) | [TD-TC-003169.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003169.step3.next-transaction.json#L52) line 52 | `R9UHLCNJ8Z2RQYRU…J19K94C0OB (984 chars) / length 984` | TC-001511 | [TD-TC-001511.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step3.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 9 | TC-003170 (1 purchase) | [TD-TC-003170.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003170.step1.purchase.json#L52) line 52 | `8GKPQREY5MLWG8J1…9W3VK0XTWE (984 chars) / length 984` | TC-001512 | [TD-TC-001512.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 10 | TC-003171 (1 purchase) | [TD-TC-003171.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003171.step1.purchase.json#L52) line 52 | `DP5I00016T58AANU…Y4XI9918CR (984 chars) / length 984` | TC-001513 | [TD-TC-001513.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 11 | TC-003172 (1 purchase) | [TD-TC-003172.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003172.step1.purchase.json#L52) line 52 | `UHKMEMWVWLKBQF12…E73OWBNT2T (984 chars) / length 984` | TC-001514 | [TD-TC-001514.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 12 | TC-003173 (1 purchase) | [TD-TC-003173.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003173.step1.purchase.json#L52) line 52 | `1CAAGEWSKWBG6VA3…SYI5U1PTU1 (984 chars) / length 984` | TC-001515 | [TD-TC-001515.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 13 | TC-003173 (2 return) | [TD-TC-003173.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003173.step2.return.json#L52) line 52 | `MEHEL6K02AATOI8F…SABF9RPXLZ (984 chars) / length 984` | TC-001515 | [TD-TC-001515.step2.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step2.return.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 14 | TC-003174 (1 purchase) | [TD-TC-003174.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003174.step1.purchase.json#L52) line 52 | `4EQF9JKVRT57BLEX…PF36Y5IUH9 (984 chars) / length 984` | TC-001516 | [TD-TC-001516.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 15 | TC-003175 (1 purchase) | [TD-TC-003175.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003175.step1.purchase.json#L52) line 52 | `ABMMMJGVJI9W7OLC…C7SQLSUNZV (984 chars) / length 984` | TC-001517 | [TD-TC-001517.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 16 | TC-003176 (1 purchase) | [TD-TC-003176.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003176.step1.purchase.json#L52) line 52 | `9KQGI1NH1I2NPRGO…RG8OYX2JOR (984 chars) / length 984` | TC-001518 | [TD-TC-001518.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 17 | TC-003178 (1 purchase) | [TD-TC-003178.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003178.step1.purchase.json#L52) line 52 | `W8EWGNVR6ET3F0FO…AMFIZAP6AH (984 chars) / length 984` | TC-001520 | [TD-TC-001520.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001520.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 18 | TC-003179 (1 purchase) | [TD-TC-003179.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003179.step1.purchase.json#L52) line 52 | `RK8CNY0Z2IGQV685…0754L7JLXD (984 chars) / length 984` | TC-001521 | [TD-TC-001521.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 19 | TC-003179 (2 next transaction) | [TD-TC-003179.step2.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003179.step2.next-transaction.json#L52) line 52 | `RCBHHAY8W7UVIUTY…6QNKGVF7UO (984 chars) / length 984` | TC-001521 | [TD-TC-001521.step2.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step2.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 20 | TC-003180 (1 purchase) | [TD-TC-003180.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003180.step1.purchase.json#L52) line 52 | `UDRGN25555LFGRWS…Q0WQOXSI37 (984 chars) / length 984` | TC-001522 | [TD-TC-001522.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 21 | TC-003180 (3 return) | [TD-TC-003180.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003180.step3.return.json#L52) line 52 | `KB8CSG4AUSA7C8FR…VJPAMHV8C4 (984 chars) / length 984` | TC-001522 | [TD-TC-001522.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step3.return.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 22 | TC-003181 (1 purchase) | [TD-TC-003181.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003181.step1.purchase.json#L52) line 52 | `VORMJXFY5BU3EC5P…I8MXZ06QAG (984 chars) / length 984` | TC-001523 | [TD-TC-001523.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 23 | TC-003181 (3 next transaction) | [TD-TC-003181.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003181.step3.next-transaction.json#L52) line 52 | `5Y3SQ7G6TMOU3KAJ…Y7RICUA6G4 (984 chars) / length 984` | TC-001523 | [TD-TC-001523.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step3.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 24 | TC-003182 (1 purchase) | [TD-TC-003182.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003182.step1.purchase.json#L52) line 52 | `AGCKCKCW4DM9RRSD…ICYBSMWQT7 (984 chars) / length 984` | TC-001524 | [TD-TC-001524.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 25 | TC-003182 (2 resubmitted) | [TD-TC-003182.step2.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003182.step2.resubmitted.json#L52) line 52 | `TIDDSXNXWDW135H6…LV39WS6AGA (984 chars) / length 984` | TC-001524 | [TD-TC-001524.step2.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step2.resubmitted.json#L57) line 57 | `EMVYES / length 006` | FIXED |

… 65 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-09"></a>

### D-09 – Credential on File indicators (Tables 032, 049, 056) are missing – CLOSED – test cases removed

| | |
|---|---|
| Field | Element 113 Tables 032 / 049 / 056 (Credential on File indicators) (source ID = CD-04) |
| Previous run | 35 test cases / 67 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 67 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| Not in the new catalog | 67 files of test cases that were removed: Credential on File (67) |
| Check it yourself | Previous: [TD-TC-003313.json](../maestro_direct_debit/test_data/TD-TC-003313.json#L30) line 30 → `032-08 C/I missing; 049-02 missing`. New: template / entry mode no longer in the catalog<br>Expected: Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003313 (1 purchase) | [TD-TC-003313.json](../maestro_direct_debit/test_data/TD-TC-003313.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 2 | TC-003314 (1 purchase) | [TD-TC-003314.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003314.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 3 | TC-003314 (2 timeout reversal) | [TD-TC-003314.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003314.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 4 | TC-003315 (1 purchase) | [TD-TC-003315.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003315.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 5 | TC-003315 (2 timeout reversal) | [TD-TC-003315.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003315.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 6 | TC-003316 (1 purchase) | [TD-TC-003316.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003316.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 7 | TC-003316 (2 timeout reversal) | [TD-TC-003316.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003316.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 8 | TC-003317 (1 purchase) | [TD-TC-003317.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003317.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 9 | TC-003317 (2 timeout reversal) | [TD-TC-003317.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003317.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 10 | TC-003317 (3 next transaction) | [TD-TC-003317.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003317.step3.next-transaction.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 11 | TC-003318 (1 purchase) | [TD-TC-003318.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003318.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 12 | TC-003318 (2 timeout reversal) | [TD-TC-003318.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003318.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 13 | TC-003318 (3 tor retry) | [TD-TC-003318.step3.tor-retry.json](../maestro_direct_debit/test_data/TD-TC-003318.step3.tor-retry.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 14 | TC-003318 (4 tor retry) | [TD-TC-003318.step4.tor-retry.json](../maestro_direct_debit/test_data/TD-TC-003318.step4.tor-retry.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 15 | TC-003319 (1 purchase) | [TD-TC-003319.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003319.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 16 | TC-003320 (1 purchase) | [TD-TC-003320.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003320.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 17 | TC-003320 (2 reversal) | [TD-TC-003320.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003320.step2.reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 18 | TC-003321 (1 purchase) | [TD-TC-003321.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003321.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 19 | TC-003321 (2 return) | [TD-TC-003321.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003321.step2.return.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 20 | TC-003322 (1 purchase) | [TD-TC-003322.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003322.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 21 | TC-003322 (2 timeout reversal) | [TD-TC-003322.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003322.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 22 | TC-003323 (1 purchase) | [TD-TC-003323.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003323.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 23 | TC-003324 (1 purchase) | [TD-TC-003324.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003324.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 24 | TC-003325 (1 timeout reversal) | [TD-TC-003325.json](../maestro_direct_debit/test_data/TD-TC-003325.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 25 | TC-003326 (1 purchase) | [TD-TC-003326.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003326.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |

… 42 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-12"></a>

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions – FIXED – value awaiting client input

| | |
|---|---|
| Field | Element 113 Table 030 (POS Condition Code) (source ID = CD-03) |
| Previous run | 107 test cases / 157 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 88 awaiting client input, 2 changed, 67 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `030 sent, length 010, value empty` (88 files); `Segment 130 is no longer sent on this step (no longer an EMV request)` (2 files) |
| Notes | value listed in awaiting_inputs; length 010 is declared for the value to come, so the file cannot be sent until the code is filled in (88); Segment 130 is no longer sent on this step (no longer an EMV request) (2) |
| Not in the new catalog | 67 files of test cases that were removed: Credential on File (67) |
| Check it yourself | Previous: [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L30) line 30 → `not sent (tables: 005=051, 031=12)`. New (TC-001507): [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L44) line 44 → `030 sent, length 010, value empty`<br>Expected: 10 digits from the client's certified terminal |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003165 (1 purchase) | [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001507 | [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-003166 (1 purchase) | [TD-TC-003166.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003166.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001508 | [TD-TC-001508.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001508.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-003167 (1 purchase) | [TD-TC-003167.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003167.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001509 | [TD-TC-001509.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001509.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-003168 (1 purchase) | [TD-TC-003168.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003168.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001510 | [TD-TC-001510.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001510.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-003169 (1 purchase) | [TD-TC-003169.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003169.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001511 | [TD-TC-001511.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-003169 (3 next transaction) | [TD-TC-003169.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003169.step3.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001511 | [TD-TC-001511.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step3.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-003170 (1 purchase) | [TD-TC-003170.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003170.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001512 | [TD-TC-001512.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-003171 (1 purchase) | [TD-TC-003171.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003171.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001513 | [TD-TC-001513.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 9 | TC-003172 (1 purchase) | [TD-TC-003172.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003172.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001514 | [TD-TC-001514.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 10 | TC-003173 (1 purchase) | [TD-TC-003173.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003173.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001515 | [TD-TC-001515.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 11 | TC-003173 (2 return) | [TD-TC-003173.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003173.step2.return.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001515 | [TD-TC-001515.step2.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step2.return.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 12 | TC-003174 (1 purchase) | [TD-TC-003174.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003174.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001516 | [TD-TC-001516.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 13 | TC-003175 (1 purchase) | [TD-TC-003175.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003175.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001517 | [TD-TC-001517.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 14 | TC-003176 (1 purchase) | [TD-TC-003176.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003176.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001518 | [TD-TC-001518.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 15 | TC-003178 (1 purchase) | [TD-TC-003178.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003178.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001520 | [TD-TC-001520.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001520.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 16 | TC-003179 (1 purchase) | [TD-TC-003179.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003179.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001521 | [TD-TC-001521.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 17 | TC-003179 (2 next transaction) | [TD-TC-003179.step2.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003179.step2.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001521 | [TD-TC-001521.step2.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step2.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 18 | TC-003180 (1 purchase) | [TD-TC-003180.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003180.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001522 | [TD-TC-001522.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 19 | TC-003180 (3 return) | [TD-TC-003180.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003180.step3.return.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001522 | [TD-TC-001522.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step3.return.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 20 | TC-003181 (1 purchase) | [TD-TC-003181.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003181.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001523 | [TD-TC-001523.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 21 | TC-003181 (3 next transaction) | [TD-TC-003181.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003181.step3.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001523 | [TD-TC-001523.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step3.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 22 | TC-003182 (1 purchase) | [TD-TC-003182.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003182.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001524 | [TD-TC-001524.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 23 | TC-003182 (2 resubmitted) | [TD-TC-003182.step2.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003182.step2.resubmitted.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001524 | [TD-TC-001524.step2.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step2.resubmitted.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 24 | TC-003183 (1 purchase) | [TD-TC-003183.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003183.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001525 | [TD-TC-001525.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001525.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 25 | TC-003183 (3 resubmitted) | [TD-TC-003183.step3.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003183.step3.resubmitted.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-001525 | [TD-TC-001525.step3.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001525.step3.resubmitted.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |

… 132 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-14"></a>

### D-14 – Follow-on step (completion, cancellation, reversal): amount data built from an impossible value – FIXED

| | |
|---|---|
| Field | Element 58 Nonfuel Amount (test case value), Element 84 Segment Length (source ID = CD-07) |
| Previous run | 7 test cases / 7 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 6 fixed, 1 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `SegmentLength 063; provisional amount 750` (6 files) |
| Not in the new catalog | 1 file of test cases that were removed: Credential on File (1) |
| Check it yourself | Previous: [TD-TC-003193.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003193.step2.reversal.json#L7) line 7 → `SegmentLength 068; provisional amount 23750295`. New (TC-001533): [TD-TC-001533.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001533.step2.reversal.json#L7) line 7 → `SegmentLength 063; provisional amount 750`<br>Expected: Segment Length for an amount of at most 4 digits (153–156) or computed at run time; provisional amount ≤ 1500 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003193 (2 reversal) | [TD-TC-003193.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003193.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 23750295` | TC-001533 | [TD-TC-001533.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001533.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 2 | TC-003223 (2 reversal) | [TD-TC-003223.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003223.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 24810480` | TC-001563 | [TD-TC-001563.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001563.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 3 | TC-003252 (2 reversal) | [TD-TC-003252.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003252.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 91788689` | TC-001592 | [TD-TC-001592.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001592.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 4 | TC-003282 (2 reversal) | [TD-TC-003282.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003282.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 72825892` | TC-001622 | [TD-TC-001622.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001622.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 5 | TC-003312 (2 reversal) | [TD-TC-003312.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003312.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 42192367` | TC-001652 | [TD-TC-001652.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001652.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 6 | TC-003370 (2 reversal) | [TD-TC-003370.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003370.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 79696584` | TC-001681 | [TD-TC-001681.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001681.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 7 | TC-003341 (2 reversal) | [TD-TC-003341.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003341.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 53217984` | – | – | template / entry mode no longer in the catalog | REMOVED |

---

<a id="d-15"></a>

### D-15 – The card data (Account Number, Card Discretionary Block Data) is empty – FIXED – value awaiting client input

| | |
|---|---|
| Field | Element 2 Account Number and Element 12 Card Discretionary Block Data |
| Previous run | 249 test cases / 473 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 406 awaiting client input, 67 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `<AWAITING:AccountNumber> / <AWAITING:CardDiscretionaryBlockData>` (406 files) |
| Not in the new catalog | 67 files of test cases that were removed: Credential on File (67) |
| Check it yourself | Previous: [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L14) line 14 → `(empty) / (empty)`. New (TC-001507): [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L14) line 14 → `<AWAITING:AccountNumber> / <AWAITING…BlockData> (64 chars)`<br>Expected: card data (or an explicit placeholder while awaiting the client card) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003165 (1 purchase) | [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L14) line 14 | `(empty) / (empty)` | TC-001507 | [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 2 | TC-003166 (1 purchase) | [TD-TC-003166.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003166.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001508 | [TD-TC-001508.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001508.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 3 | TC-003166 (2 timeout reversal) | [TD-TC-003166.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003166.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001508 | [TD-TC-001508.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001508.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 4 | TC-003167 (1 purchase) | [TD-TC-003167.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003167.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001509 | [TD-TC-001509.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001509.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 5 | TC-003167 (2 timeout reversal) | [TD-TC-003167.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003167.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001509 | [TD-TC-001509.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001509.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 6 | TC-003168 (1 purchase) | [TD-TC-003168.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003168.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001510 | [TD-TC-001510.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001510.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 7 | TC-003168 (2 timeout reversal) | [TD-TC-003168.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003168.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001510 | [TD-TC-001510.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001510.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 8 | TC-003169 (1 purchase) | [TD-TC-003169.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003169.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001511 | [TD-TC-001511.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 9 | TC-003169 (2 timeout reversal) | [TD-TC-003169.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003169.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001511 | [TD-TC-001511.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 10 | TC-003169 (3 next transaction) | [TD-TC-003169.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003169.step3.next-transaction.json#L14) line 14 | `(empty) / (empty)` | TC-001511 | [TD-TC-001511.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step3.next-transaction.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 11 | TC-003170 (1 purchase) | [TD-TC-003170.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003170.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001512 | [TD-TC-001512.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 12 | TC-003170 (2 timeout reversal) | [TD-TC-003170.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003170.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001512 | [TD-TC-001512.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 13 | TC-003170 (3 tor retry) | [TD-TC-003170.step3.tor-retry.json](../maestro_direct_debit/test_data/TD-TC-003170.step3.tor-retry.json#L14) line 14 | `(empty) / (empty)` | TC-001512 | [TD-TC-001512.step3.tor-retry.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step3.tor-retry.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 14 | TC-003170 (4 tor retry) | [TD-TC-003170.step4.tor-retry.json](../maestro_direct_debit/test_data/TD-TC-003170.step4.tor-retry.json#L14) line 14 | `(empty) / (empty)` | TC-001512 | [TD-TC-001512.step4.tor-retry.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step4.tor-retry.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 15 | TC-003171 (1 purchase) | [TD-TC-003171.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003171.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001513 | [TD-TC-001513.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 16 | TC-003172 (1 purchase) | [TD-TC-003172.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003172.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001514 | [TD-TC-001514.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 17 | TC-003172 (2 reversal) | [TD-TC-003172.step2.reversal.json](../maestro_direct_debit/test_data/TD-TC-003172.step2.reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001514 | [TD-TC-001514.step2.reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step2.reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 18 | TC-003173 (1 purchase) | [TD-TC-003173.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003173.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001515 | [TD-TC-001515.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 19 | TC-003173 (2 return) | [TD-TC-003173.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003173.step2.return.json#L14) line 14 | `(empty) / (empty)` | TC-001515 | [TD-TC-001515.step2.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step2.return.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 20 | TC-003174 (1 purchase) | [TD-TC-003174.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003174.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001516 | [TD-TC-001516.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 21 | TC-003174 (2 timeout reversal) | [TD-TC-003174.step2.timeout-reversal.json](../maestro_direct_debit/test_data/TD-TC-003174.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-001516 | [TD-TC-001516.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 22 | TC-003175 (1 purchase) | [TD-TC-003175.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003175.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001517 | [TD-TC-001517.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 23 | TC-003176 (1 purchase) | [TD-TC-003176.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003176.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001518 | [TD-TC-001518.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 24 | TC-003177 (1 timeout reversal) | [TD-TC-003177.json](../maestro_direct_debit/test_data/TD-TC-003177.json#L14) line 14 | `(empty) / (empty)` | TC-001519 | [TD-TC-001519.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001519.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 25 | TC-003178 (1 purchase) | [TD-TC-003178.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003178.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-001520 | [TD-TC-001520.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001520.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |

… 448 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-16"></a>

### D-16 – The debit PIN block (Element 33) is empty – FIXED – value awaiting client input

| | |
|---|---|
| Field | Element 33 Encrypted PIN Block Data |
| Previous run | 242 test cases / 305 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 262 awaiting client input, 43 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `<AWAITING:EncryptedPINBlockData>` (262 files) |
| Not in the new catalog | 43 files of test cases that were removed: Credential on File (43) |
| Check it yourself | Previous: [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L16) line 16 → `(empty)`. New (TC-001507): [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L16) line 16 → `<AWAITING:EncryptedPINBlockData>`<br>Expected: DUKPT KSN + PIN block (or an explicit placeholder while awaiting the client key) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003165 (1 purchase) | [TD-TC-003165.json](../maestro_direct_debit/test_data/TD-TC-003165.json#L16) line 16 | `(empty)` | TC-001507 | [TD-TC-001507.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001507.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 2 | TC-003166 (1 purchase) | [TD-TC-003166.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003166.step1.purchase.json#L16) line 16 | `(empty)` | TC-001508 | [TD-TC-001508.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001508.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 3 | TC-003167 (1 purchase) | [TD-TC-003167.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003167.step1.purchase.json#L16) line 16 | `(empty)` | TC-001509 | [TD-TC-001509.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001509.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 4 | TC-003168 (1 purchase) | [TD-TC-003168.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003168.step1.purchase.json#L16) line 16 | `(empty)` | TC-001510 | [TD-TC-001510.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001510.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 5 | TC-003169 (1 purchase) | [TD-TC-003169.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003169.step1.purchase.json#L16) line 16 | `(empty)` | TC-001511 | [TD-TC-001511.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 6 | TC-003169 (3 next transaction) | [TD-TC-003169.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003169.step3.next-transaction.json#L16) line 16 | `(empty)` | TC-001511 | [TD-TC-001511.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001511.step3.next-transaction.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 7 | TC-003170 (1 purchase) | [TD-TC-003170.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003170.step1.purchase.json#L16) line 16 | `(empty)` | TC-001512 | [TD-TC-001512.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001512.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 8 | TC-003171 (1 purchase) | [TD-TC-003171.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003171.step1.purchase.json#L16) line 16 | `(empty)` | TC-001513 | [TD-TC-001513.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 9 | TC-003172 (1 purchase) | [TD-TC-003172.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003172.step1.purchase.json#L16) line 16 | `(empty)` | TC-001514 | [TD-TC-001514.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 10 | TC-003173 (1 purchase) | [TD-TC-003173.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003173.step1.purchase.json#L16) line 16 | `(empty)` | TC-001515 | [TD-TC-001515.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 11 | TC-003173 (2 return) | [TD-TC-003173.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003173.step2.return.json#L16) line 16 | `(empty)` | TC-001515 | [TD-TC-001515.step2.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step2.return.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 12 | TC-003174 (1 purchase) | [TD-TC-003174.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003174.step1.purchase.json#L16) line 16 | `(empty)` | TC-001516 | [TD-TC-001516.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 13 | TC-003175 (1 purchase) | [TD-TC-003175.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003175.step1.purchase.json#L16) line 16 | `(empty)` | TC-001517 | [TD-TC-001517.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 14 | TC-003176 (1 purchase) | [TD-TC-003176.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003176.step1.purchase.json#L16) line 16 | `(empty)` | TC-001518 | [TD-TC-001518.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 15 | TC-003178 (1 purchase) | [TD-TC-003178.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003178.step1.purchase.json#L16) line 16 | `(empty)` | TC-001520 | [TD-TC-001520.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001520.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 16 | TC-003179 (1 purchase) | [TD-TC-003179.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003179.step1.purchase.json#L16) line 16 | `(empty)` | TC-001521 | [TD-TC-001521.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 17 | TC-003179 (2 next transaction) | [TD-TC-003179.step2.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003179.step2.next-transaction.json#L16) line 16 | `(empty)` | TC-001521 | [TD-TC-001521.step2.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001521.step2.next-transaction.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 18 | TC-003180 (1 purchase) | [TD-TC-003180.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003180.step1.purchase.json#L16) line 16 | `(empty)` | TC-001522 | [TD-TC-001522.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 19 | TC-003180 (3 return) | [TD-TC-003180.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003180.step3.return.json#L16) line 16 | `(empty)` | TC-001522 | [TD-TC-001522.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001522.step3.return.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 20 | TC-003181 (1 purchase) | [TD-TC-003181.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003181.step1.purchase.json#L16) line 16 | `(empty)` | TC-001523 | [TD-TC-001523.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 21 | TC-003181 (3 next transaction) | [TD-TC-003181.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003181.step3.next-transaction.json#L16) line 16 | `(empty)` | TC-001523 | [TD-TC-001523.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001523.step3.next-transaction.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 22 | TC-003182 (1 purchase) | [TD-TC-003182.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003182.step1.purchase.json#L16) line 16 | `(empty)` | TC-001524 | [TD-TC-001524.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 23 | TC-003182 (2 resubmitted) | [TD-TC-003182.step2.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003182.step2.resubmitted.json#L16) line 16 | `(empty)` | TC-001524 | [TD-TC-001524.step2.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001524.step2.resubmitted.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 24 | TC-003183 (1 purchase) | [TD-TC-003183.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003183.step1.purchase.json#L16) line 16 | `(empty)` | TC-001525 | [TD-TC-001525.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001525.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 25 | TC-003183 (3 resubmitted) | [TD-TC-003183.step3.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003183.step3.resubmitted.json#L16) line 16 | `(empty)` | TC-001525 | [TD-TC-001525.step3.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001525.step3.resubmitted.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |

… 280 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-17"></a>

### D-17 – A keyed (manual) entry mode is used for a debit card – FIXED

| | |
|---|---|
| Field | Element 113 Table 005 (PAN entry mode) |
| Previous run | 34 test cases / 43 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 43 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `801` (43 files) |
| Check it yourself | Previous: [TD-TC-003283.json](../maestro_direct_debit/test_data/TD-TC-003283.json#L34) line 34 → `791`. New (TC-001625): [TD-TC-001625.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001625.json#L34) line 34 → `801`<br>Expected: a card-read entry mode (e.g. 80 for chip fallback to swipe) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003283 (1 purchase) | [TD-TC-003283.json](../maestro_direct_debit/test_data/TD-TC-003283.json#L34) line 34 | `791` | TC-001625 | [TD-TC-001625.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001625.json#L34) line 34 | `801` | FIXED |
| 2 | TC-003284 (1 purchase) | [TD-TC-003284.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003284.step1.purchase.json#L34) line 34 | `791` | TC-001626 | [TD-TC-001626.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001626.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 3 | TC-003285 (1 purchase) | [TD-TC-003285.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003285.step1.purchase.json#L34) line 34 | `791` | TC-001627 | [TD-TC-001627.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001627.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 4 | TC-003286 (1 purchase) | [TD-TC-003286.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003286.step1.purchase.json#L34) line 34 | `791` | TC-001628 | [TD-TC-001628.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001628.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 5 | TC-003287 (1 purchase) | [TD-TC-003287.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003287.step1.purchase.json#L34) line 34 | `791` | TC-001629 | [TD-TC-001629.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001629.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 6 | TC-003287 (3 next transaction) | [TD-TC-003287.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003287.step3.next-transaction.json#L34) line 34 | `791` | TC-001629 | [TD-TC-001629.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001629.step3.next-transaction.json#L34) line 34 | `801` | FIXED |
| 7 | TC-003288 (1 purchase) | [TD-TC-003288.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003288.step1.purchase.json#L34) line 34 | `791` | TC-001630 | [TD-TC-001630.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001630.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 8 | TC-003289 (1 purchase) | [TD-TC-003289.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003289.step1.purchase.json#L34) line 34 | `791` | TC-001631 | [TD-TC-001631.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001631.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 9 | TC-003290 (1 purchase) | [TD-TC-003290.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003290.step1.purchase.json#L34) line 34 | `791` | TC-001632 | [TD-TC-001632.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001632.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 10 | TC-003291 (1 purchase) | [TD-TC-003291.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003291.step1.purchase.json#L34) line 34 | `791` | TC-001633 | [TD-TC-001633.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001633.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 11 | TC-003291 (2 return) | [TD-TC-003291.step2.return.json](../maestro_direct_debit/test_data/TD-TC-003291.step2.return.json#L34) line 34 | `791` | TC-001633 | [TD-TC-001633.step2.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001633.step2.return.json#L34) line 34 | `801` | FIXED |
| 12 | TC-003292 (1 purchase) | [TD-TC-003292.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003292.step1.purchase.json#L34) line 34 | `791` | TC-001634 | [TD-TC-001634.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001634.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 13 | TC-003293 (1 purchase) | [TD-TC-003293.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003293.step1.purchase.json#L34) line 34 | `791` | TC-001635 | [TD-TC-001635.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001635.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 14 | TC-003294 (1 purchase) | [TD-TC-003294.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003294.step1.purchase.json#L34) line 34 | `791` | TC-001636 | [TD-TC-001636.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001636.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 15 | TC-003296 (1 purchase) | [TD-TC-003296.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003296.step1.purchase.json#L34) line 34 | `791` | TC-001638 | [TD-TC-001638.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001638.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 16 | TC-003298 (1 purchase) | [TD-TC-003298.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003298.step1.purchase.json#L34) line 34 | `791` | TC-001640 | [TD-TC-001640.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001640.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 17 | TC-003298 (2 next transaction) | [TD-TC-003298.step2.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003298.step2.next-transaction.json#L34) line 34 | `791` | TC-001640 | [TD-TC-001640.step2.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001640.step2.next-transaction.json#L34) line 34 | `801` | FIXED |
| 18 | TC-003299 (1 purchase) | [TD-TC-003299.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003299.step1.purchase.json#L34) line 34 | `791` | TC-001641 | [TD-TC-001641.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001641.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 19 | TC-003299 (3 return) | [TD-TC-003299.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003299.step3.return.json#L34) line 34 | `791` | TC-001641 | [TD-TC-001641.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001641.step3.return.json#L34) line 34 | `801` | FIXED |
| 20 | TC-003300 (1 purchase) | [TD-TC-003300.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003300.step1.purchase.json#L34) line 34 | `791` | TC-001642 | [TD-TC-001642.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001642.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 21 | TC-003300 (3 next transaction) | [TD-TC-003300.step3.next-transaction.json](../maestro_direct_debit/test_data/TD-TC-003300.step3.next-transaction.json#L34) line 34 | `791` | TC-001642 | [TD-TC-001642.step3.next-transaction.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001642.step3.next-transaction.json#L34) line 34 | `801` | FIXED |
| 22 | TC-003301 (1 purchase) | [TD-TC-003301.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003301.step1.purchase.json#L34) line 34 | `791` | TC-001643 | [TD-TC-001643.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001643.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 23 | TC-003301 (2 resubmitted) | [TD-TC-003301.step2.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003301.step2.resubmitted.json#L34) line 34 | `791` | TC-001643 | [TD-TC-001643.step2.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001643.step2.resubmitted.json#L34) line 34 | `801` | FIXED |
| 24 | TC-003302 (1 purchase) | [TD-TC-003302.step1.purchase.json](../maestro_direct_debit/test_data/TD-TC-003302.step1.purchase.json#L34) line 34 | `791` | TC-001644 | [TD-TC-001644.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001644.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 25 | TC-003302 (3 resubmitted) | [TD-TC-003302.step3.resubmitted.json](../maestro_direct_debit/test_data/TD-TC-003302.step3.resubmitted.json#L34) line 34 | `791` | TC-001644 | [TD-TC-001644.step3.resubmitted.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001644.step3.resubmitted.json#L34) line 34 | `801` | FIXED |

… 18 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-18"></a>

### D-18 – Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back – FIXED

| | |
|---|---|
| Field | Element 190 EMV Chip Data – Tags 9F02, 9F03 and 9C (source ID = CD-15) |
| Previous run | 4 test cases / 4 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 4 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F02 000000002000, 9F03 000000000500, 9C 09 (Cash Amount 500)` (4 files) |
| Check it yourself | Previous: [TD-TC-003191.json](../maestro_direct_debit/test_data/TD-TC-003191.json#L49) line 49 → `9F02 000000001500, 9F03 000000000000…mount 500) (61 chars)`. New (TC-001531): [TD-TC-001531.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001531.json#L54) line 54 → `9F02 000000002000, 9F03 000000000500…mount 500) (61 chars)`<br>Expected: 9F02 000000002000, 9F03 000000000500, 9C 09 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003191 (1 purchase) | [TD-TC-003191.json](../maestro_direct_debit/test_data/TD-TC-003191.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-001531 | [TD-TC-001531.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001531.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 2 | TC-003192 (1 purchase) | [TD-TC-003192.json](../maestro_direct_debit/test_data/TD-TC-003192.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-001532 | [TD-TC-001532.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001532.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 3 | TC-003221 (1 purchase) | [TD-TC-003221.json](../maestro_direct_debit/test_data/TD-TC-003221.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-001561 | [TD-TC-001561.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001561.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 4 | TC-003222 (1 purchase) | [TD-TC-003222.json](../maestro_direct_debit/test_data/TD-TC-003222.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-001562 | [TD-TC-001562.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001562.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |

---

<a id="d-19"></a>

### D-19 – Totals Request: Card Label (Element 13) is not 4 characters – FIXED

| | |
|---|---|
| Field | Element 13 Card Label (Totals Request) (source ID = CD-10) |
| Previous run | 63 test cases / 63 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 48 fixed, 6 changed, 9 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `'DB  '` (48 files); `no card-type category is sent any more (nothing occurred…card type) (75 chars)` (6 files) |
| Notes | no card-type category is sent any more (nothing occurred for the card type) (6) |
| Not in the new catalog | 9 files of test cases that were removed: Credential on File (9) |
| Check it yourself | Previous: [TD-TC-003171.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003171.step2.totals.json#L20) line 20 → `'DB'`. New (TC-001513): [TD-TC-001513.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step2.totals.json#L20) line 20 → `'DB  '`<br>Expected: 'DB  ' |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003172 (3 totals) | [TD-TC-003172.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003172.step3.totals.json#L20) line 20 | `'DB'` | TC-001514 | [TD-TC-001514.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 2 | TC-003202 (3 totals) | [TD-TC-003202.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003202.step3.totals.json#L20) line 20 | `'DB'` | TC-001544 | [TD-TC-001544.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001544.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 3 | TC-003231 (3 totals) | [TD-TC-003231.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003231.step3.totals.json#L20) line 20 | `'DB'` | TC-001573 | [TD-TC-001573.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001573.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 4 | TC-003261 (3 totals) | [TD-TC-003261.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003261.step3.totals.json#L20) line 20 | `'DB'` | TC-001603 | [TD-TC-001603.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001603.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 5 | TC-003290 (3 totals) | [TD-TC-003290.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003290.step3.totals.json#L20) line 20 | `'DB'` | TC-001632 | [TD-TC-001632.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001632.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 6 | TC-003349 (3 totals) | [TD-TC-003349.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003349.step3.totals.json#L20) line 20 | `'DB'` | TC-001662 | [TD-TC-001662.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001662.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 7 | TC-003171 (2 totals) | [TD-TC-003171.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003171.step2.totals.json#L20) line 20 | `'DB'` | TC-001513 | [TD-TC-001513.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 8 | TC-003173 (3 totals) | [TD-TC-003173.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003173.step3.totals.json#L20) line 20 | `'DB'` | TC-001515 | [TD-TC-001515.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 9 | TC-003174 (3 totals) | [TD-TC-003174.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003174.step3.totals.json#L20) line 20 | `'DB'` | TC-001516 | [TD-TC-001516.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 10 | TC-003175 (2 totals) | [TD-TC-003175.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003175.step2.totals.json#L20) line 20 | `'DB'` | TC-001517 | [TD-TC-001517.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 11 | TC-003176 (2 totals) | [TD-TC-003176.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003176.step2.totals.json#L20) line 20 | `'DB'` | TC-001518 | [TD-TC-001518.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 12 | TC-003184 (2 totals) | [TD-TC-003184.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003184.step2.totals.json#L20) line 20 | `'DB'` | TC-001526 | [TD-TC-001526.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001526.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 13 | TC-003185 (2 totals) | [TD-TC-003185.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003185.step2.totals.json#L20) line 20 | `'DB'` | TC-001527 | [TD-TC-001527.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001527.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 14 | TC-003201 (2 totals) | [TD-TC-003201.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003201.step2.totals.json#L20) line 20 | `'DB'` | TC-001543 | [TD-TC-001543.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001543.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 15 | TC-003203 (3 totals) | [TD-TC-003203.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003203.step3.totals.json#L20) line 20 | `'DB'` | TC-001545 | [TD-TC-001545.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001545.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 16 | TC-003204 (3 totals) | [TD-TC-003204.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003204.step3.totals.json#L20) line 20 | `'DB'` | TC-001546 | [TD-TC-001546.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001546.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 17 | TC-003205 (2 totals) | [TD-TC-003205.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003205.step2.totals.json#L20) line 20 | `'DB'` | TC-001547 | [TD-TC-001547.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001547.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 18 | TC-003206 (2 totals) | [TD-TC-003206.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003206.step2.totals.json#L20) line 20 | `'DB'` | TC-001548 | [TD-TC-001548.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001548.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 19 | TC-003214 (2 totals) | [TD-TC-003214.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003214.step2.totals.json#L20) line 20 | `'DB'` | TC-001556 | [TD-TC-001556.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001556.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 20 | TC-003215 (2 totals) | [TD-TC-003215.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003215.step2.totals.json#L20) line 20 | `'DB'` | TC-001557 | [TD-TC-001557.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001557.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 21 | TC-003230 (2 totals) | [TD-TC-003230.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003230.step2.totals.json#L20) line 20 | `'DB'` | TC-001572 | [TD-TC-001572.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001572.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 22 | TC-003232 (3 totals) | [TD-TC-003232.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003232.step3.totals.json#L20) line 20 | `'DB'` | TC-001574 | [TD-TC-001574.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001574.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 23 | TC-003233 (3 totals) | [TD-TC-003233.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003233.step3.totals.json#L20) line 20 | `'DB'` | TC-001575 | [TD-TC-001575.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001575.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 24 | TC-003234 (2 totals) | [TD-TC-003234.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003234.step2.totals.json#L20) line 20 | `'DB'` | TC-001576 | [TD-TC-001576.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001576.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 25 | TC-003235 (2 totals) | [TD-TC-003235.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003235.step2.totals.json#L20) line 20 | `'DB'` | TC-001577 | [TD-TC-001577.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001577.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |

… 38 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-20"></a>

### D-20 – Totals Request: Segment 105 length is counted with the wrong layout – FIXED

| | |
|---|---|
| Field | Element 84 Segment Length (Totals Request) |
| Previous run | 63 test cases / 63 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 54 fixed, 9 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `107 (Card Label 'DB  ')` (48 files); `090 (no card-type category)` (6 files) |
| Not in the new catalog | 9 files of test cases that were removed: Credential on File (9) |
| Check it yourself | Previous: [TD-TC-003171.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003171.step2.totals.json#L7) line 7 → `108 (Card Label 'DB')`. New (TC-001513): [TD-TC-001513.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step2.totals.json#L7) line 7 → `107 (Card Label 'DB  ')`<br>Expected: 107 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003171 (2 totals) | [TD-TC-003171.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003171.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001513 | [TD-TC-001513.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001513.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 2 | TC-003172 (3 totals) | [TD-TC-003172.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003172.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001514 | [TD-TC-001514.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001514.step3.totals.json#L7) line 7 | `090 (no card-type category)` | FIXED |
| 3 | TC-003173 (3 totals) | [TD-TC-003173.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003173.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001515 | [TD-TC-001515.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001515.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 4 | TC-003174 (3 totals) | [TD-TC-003174.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003174.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001516 | [TD-TC-001516.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 5 | TC-003175 (2 totals) | [TD-TC-003175.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003175.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001517 | [TD-TC-001517.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001517.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 6 | TC-003176 (2 totals) | [TD-TC-003176.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003176.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001518 | [TD-TC-001518.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001518.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 7 | TC-003184 (2 totals) | [TD-TC-003184.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003184.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001526 | [TD-TC-001526.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001526.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 8 | TC-003185 (2 totals) | [TD-TC-003185.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003185.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001527 | [TD-TC-001527.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001527.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 9 | TC-003201 (2 totals) | [TD-TC-003201.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003201.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001543 | [TD-TC-001543.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001543.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 10 | TC-003202 (3 totals) | [TD-TC-003202.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003202.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001544 | [TD-TC-001544.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001544.step3.totals.json#L7) line 7 | `090 (no card-type category)` | FIXED |
| 11 | TC-003203 (3 totals) | [TD-TC-003203.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003203.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001545 | [TD-TC-001545.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001545.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 12 | TC-003204 (3 totals) | [TD-TC-003204.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003204.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001546 | [TD-TC-001546.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001546.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 13 | TC-003205 (2 totals) | [TD-TC-003205.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003205.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001547 | [TD-TC-001547.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001547.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 14 | TC-003206 (2 totals) | [TD-TC-003206.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003206.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001548 | [TD-TC-001548.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001548.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 15 | TC-003214 (2 totals) | [TD-TC-003214.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003214.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001556 | [TD-TC-001556.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001556.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 16 | TC-003215 (2 totals) | [TD-TC-003215.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003215.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001557 | [TD-TC-001557.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001557.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 17 | TC-003230 (2 totals) | [TD-TC-003230.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003230.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001572 | [TD-TC-001572.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001572.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 18 | TC-003231 (3 totals) | [TD-TC-003231.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003231.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001573 | [TD-TC-001573.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001573.step3.totals.json#L7) line 7 | `090 (no card-type category)` | FIXED |
| 19 | TC-003232 (3 totals) | [TD-TC-003232.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003232.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001574 | [TD-TC-001574.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001574.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 20 | TC-003233 (3 totals) | [TD-TC-003233.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003233.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001575 | [TD-TC-001575.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001575.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 21 | TC-003234 (2 totals) | [TD-TC-003234.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003234.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001576 | [TD-TC-001576.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001576.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 22 | TC-003235 (2 totals) | [TD-TC-003235.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003235.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001577 | [TD-TC-001577.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001577.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 23 | TC-003243 (2 totals) | [TD-TC-003243.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003243.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001585 | [TD-TC-001585.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001585.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 24 | TC-003244 (2 totals) | [TD-TC-003244.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003244.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001586 | [TD-TC-001586.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001586.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 25 | TC-003260 (2 totals) | [TD-TC-003260.step2.totals.json](../maestro_direct_debit/test_data/TD-TC-003260.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-001602 | [TD-TC-001602.step2.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001602.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |

… 38 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-21"></a>

### D-21 – Totals Request: the totals do not match the transactions of the flow – NOT FIXED

| | |
|---|---|
| Field | Elements 16, 15, 42 Card Type Total Count, Card Type Total Amount, Grand Total |
| Previous run | 14 test cases / 14 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 12 not fixed, 2 removed |
| New run, all test cases | 12 test cases / 12 files still failing |
| Not in the new catalog | 2 files of test cases that were removed: Credential on File (2) |
| Check it yourself | Previous: [TD-TC-003174.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003174.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`. New (TC-001516): [TD-TC-001516.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`<br>Expected: Leave out the DB bucket and send Grand Total 00000000 |

#### Explained step by step (new run)

**TC-001516 – T8-PUR-TOR-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 10

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-001516.step1.purchase.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step1.purchase.json#L19) line 19 | purchase (Transaction Type `0`) | `1500` = $15.00 | no response (time-out) | no response (timed out), so not in the totals |
| 2 | [TD-TC-001516.step2.timeout-reversal.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step2.timeout-reversal.json#L19) line 19 | timeout reversal (Transaction Type `Z`) | `1500` = $15.00 | Response Code `0` | its original was never approved, so nothing to take out |

So the DB bucket holds **count 0, amount $0.00** – nothing occurred for the card type, so Fields 15–17 are not sent (p.237).

Totals Request [TD-TC-001516.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'DB  '` | `not sent` | **WRONG** |
| Card Type Total Count (Element 16) | 21 | `00001` | `not sent` | **WRONG** |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `not sent` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00000000` | OK |
| Segment Length (Element 84) | 7 | `107` | `090` | **WRONG** |

The test case itself says for this step: *"the timed-out purchase is not in the totals"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 10 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

**TC-001686 – T8-AUT-COMPLETION-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 180

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-001686.step1.auth-only.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001686.step1.auth-only.json#L19) line 19 | auth only (Transaction Type `3`) | `1500` = $15.00 | Response Code `2` | Authorization Only - AO bucket, not in the grand total |
| 2 | [TD-TC-001686.step2.completion.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001686.step2.completion.json#L19) line 19 | completion (Transaction Type `0`) | `1500` = $15.00 | Response Code `0` | count +1, amount +$15.00 |

So the DB bucket holds **count 1, amount $15.00**.

Totals Request [TD-TC-001686.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001686.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'DB  '` | `'DB  '` | OK |
| Card Type Total Count (Element 16) | 21 | `00001` | `00001` | OK |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `00001500` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00001500` | **WRONG** |
| Segment Length (Element 84) | 7 | `107` | `107` | OK |

The test case itself says for this step: *"the completion is in the totals; the authorization only is not (no draft capture)"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 180 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003174 (3 totals) | [TD-TC-003174.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003174.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001516 | [TD-TC-001516.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 2 | TC-003204 (3 totals) | [TD-TC-003204.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003204.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001546 | [TD-TC-001546.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001546.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 3 | TC-003233 (3 totals) | [TD-TC-003233.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003233.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001575 | [TD-TC-001575.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001575.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 4 | TC-003263 (3 totals) | [TD-TC-003263.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003263.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001605 | [TD-TC-001605.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001605.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 5 | TC-003292 (3 totals) | [TD-TC-003292.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003292.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001634 | [TD-TC-001634.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001634.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 6 | TC-003351 (3 totals) | [TD-TC-003351.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003351.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001664 | [TD-TC-001664.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001664.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 7 | TC-003373 (3 totals) | [TD-TC-003373.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003373.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001686 | [TD-TC-001686.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001686.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 8 | TC-003381 (3 totals) | [TD-TC-003381.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003381.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001696 | [TD-TC-001696.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001696.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 9 | TC-003388 (3 totals) | [TD-TC-003388.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003388.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001705 | [TD-TC-001705.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001705.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 10 | TC-003395 (3 totals) | [TD-TC-003395.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003395.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001714 | [TD-TC-001714.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001714.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 11 | TC-003402 (3 totals) | [TD-TC-003402.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003402.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001723 | [TD-TC-001723.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001723.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 12 | TC-003417 (3 totals) | [TD-TC-003417.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003417.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-001733 | [TD-TC-001733.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001733.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 13 | TC-003322 (3 totals) | [TD-TC-003322.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003322.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 14 | TC-003410 (3 totals) | [TD-TC-003410.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003410.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | – | – | template / entry mode no longer in the catalog | REMOVED |

---

<a id="d-24"></a>

### D-24 – A test data value differs from the test case it was generated from – FIXED

| | |
|---|---|
| Field | Any field – test data value vs test case request value |
| Previous run | 14 test cases / 14 files failing (missed – not in the previous logs) |
| Previously failing files in the new run | 12 fixed, 2 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `015263 (test case: 015263)` (1 file); `015273 (test case: 015273)` (1 file); `015563 (test case: 015563)` (1 file) |
| Not in the new catalog | 2 files of test cases that were removed: Credential on File (2) |
| Check it yourself | Previous: [TD-TC-003184.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003184.step3.return.json#L22) line 22 → `031842 (test case: 031843)`. New (TC-001526): [TD-TC-001526.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001526.step3.return.json#L22) line 22 → `015263 (test case: 015263)`<br>Expected: test data value = test case value |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003184 (3 return) | [TD-TC-003184.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003184.step3.return.json#L22) line 22 | `031842 (test case: 031843)` | TC-001526 | [TD-TC-001526.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001526.step3.return.json#L22) line 22 | `015263 (test case: 015263)` | FIXED |
| 2 | TC-003185 (3 return) | [TD-TC-003185.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003185.step3.return.json#L22) line 22 | `031852 (test case: 031853)` | TC-001527 | [TD-TC-001527.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001527.step3.return.json#L22) line 22 | `015273 (test case: 015273)` | FIXED |
| 3 | TC-003214 (3 return) | [TD-TC-003214.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003214.step3.return.json#L22) line 22 | `032142 (test case: 032143)` | TC-001556 | [TD-TC-001556.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001556.step3.return.json#L22) line 22 | `015563 (test case: 015563)` | FIXED |
| 4 | TC-003215 (3 return) | [TD-TC-003215.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003215.step3.return.json#L22) line 22 | `032152 (test case: 032153)` | TC-001557 | [TD-TC-001557.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001557.step3.return.json#L22) line 22 | `015573 (test case: 015573)` | FIXED |
| 5 | TC-003243 (3 return) | [TD-TC-003243.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003243.step3.return.json#L22) line 22 | `032432 (test case: 032433)` | TC-001585 | [TD-TC-001585.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001585.step3.return.json#L22) line 22 | `015853 (test case: 015853)` | FIXED |
| 6 | TC-003244 (3 return) | [TD-TC-003244.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003244.step3.return.json#L22) line 22 | `032442 (test case: 032443)` | TC-001586 | [TD-TC-001586.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001586.step3.return.json#L22) line 22 | `015863 (test case: 015863)` | FIXED |
| 7 | TC-003273 (3 return) | [TD-TC-003273.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003273.step3.return.json#L22) line 22 | `032732 (test case: 032733)` | TC-001615 | [TD-TC-001615.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001615.step3.return.json#L22) line 22 | `016153 (test case: 016153)` | FIXED |
| 8 | TC-003274 (3 return) | [TD-TC-003274.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003274.step3.return.json#L22) line 22 | `032742 (test case: 032743)` | TC-001616 | [TD-TC-001616.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001616.step3.return.json#L22) line 22 | `016163 (test case: 016163)` | FIXED |
| 9 | TC-003303 (3 return) | [TD-TC-003303.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003303.step3.return.json#L22) line 22 | `033032 (test case: 033033)` | TC-001645 | [TD-TC-001645.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001645.step3.return.json#L22) line 22 | `016453 (test case: 016453)` | FIXED |
| 10 | TC-003304 (3 return) | [TD-TC-003304.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003304.step3.return.json#L22) line 22 | `033042 (test case: 033043)` | TC-001646 | [TD-TC-001646.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001646.step3.return.json#L22) line 22 | `016463 (test case: 016463)` | FIXED |
| 11 | TC-003361 (3 return) | [TD-TC-003361.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003361.step3.return.json#L22) line 22 | `033612 (test case: 033613)` | TC-001674 | [TD-TC-001674.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001674.step3.return.json#L22) line 22 | `016743 (test case: 016743)` | FIXED |
| 12 | TC-003362 (3 return) | [TD-TC-003362.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003362.step3.return.json#L22) line 22 | `033622 (test case: 033623)` | TC-001675 | [TD-TC-001675.step3.return.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001675.step3.return.json#L22) line 22 | `016753 (test case: 016753)` | FIXED |
| 13 | TC-003332 (3 return) | [TD-TC-003332.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003332.step3.return.json#L22) line 22 | `033322 (test case: 033323)` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 14 | TC-003333 (3 return) | [TD-TC-003333.step3.return.json](../maestro_direct_debit/test_data/TD-TC-003333.step3.return.json#L22) line 22 | `033332 (test case: 033333)` | – | – | template / entry mode no longer in the catalog | REMOVED |

---

<a id="cd-19"></a>

### CD-19 – Traceability matrix points to a folder that is not delivered; follow-on legs never traced – FIXED

| Check | Previous run | New run |
|---|---|---|
| Trace rows (`reports/traceability_matrix_full.json` → `flat_rows`) | 285 | 263 |
| Folders the test data paths point to | `qe_shaped_test_data/` | `test_data/` |
| `flat_rows` whose test data path does not exist | 249 | 0 |
| Trace entries (all levels) with a missing path / of them marked `file_exists: true` | 498 / 249 | 0 / 0 |
| Delivered test data files never referenced by the trace | 287 of 536 | 0 of 532 |
| Entries with `file_exists: false` | 29 | 1 – ? ((no test data)) |

Check it yourself: open `5-11-out/maestro_direct_debit/reports/traceability_matrix_full.json` and search `"test_data_file"` – the paths now start with `test_data/` (previously `qe_shaped_test_data/`, a folder that was never delivered), and every leg file of a flow is listed.

---

Re-run (from `regression-artifact-validator`): `python scripts/verify-defect-fixes.py`

