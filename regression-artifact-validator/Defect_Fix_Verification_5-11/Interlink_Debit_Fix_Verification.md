# Interlink Debit – defect fix verification (previous run 31-10-out → new run 5-11-out)

| Item | Value |
|---|---|
| Previous run | `C:/Users/F7OKAGW/Downloads/31-10-out/interlink_debit` – 278 test cases, 536 test data files (identical copy in the workspace: `interlink_debit/test_data`) |
| New run | `5-11-out/interlink_debit` – 257 test cases, 532 test data files |
| Defect log(s) | `Defect MD file/` Interlink_Debit_Consolidated_Defect_Report.md (D-xx) |
| Test case matching | IDs are renumbered in the new run; test cases are matched by transaction category + entry mode + template (unique in both runs), flow steps by step number and role. 215 of 278 previous test cases have a match; 63 were removed, 42 are new |

Status: **FIXED** – every previously failing file now passes the rule · **FIXED – value awaiting client input** – the generator now builds the field correctly, but its value can only come from the client (listed in the test case's `awaiting_inputs`, status `needs_client_input`) · **PARTIALLY FIXED** – some files pass, some still fail · **NOT FIXED** – still failing · **CLOSED – test cases removed** – the affected test cases are no longer in the catalog.

## 1. Summary

| Defect | Source ID | What was wrong | Previous run: TCs / files (logged) | New run: TCs / files still failing | Status |
|---|---|---|---|---|---|
| [D-01](#d-01) | = CD-01 | EMV Card Sequence Number (Element 188) has the wrong width | 72 / 90 (72 / 90) | 0 / 0 | **FIXED** |
| [D-02](#d-02) | = CD-02 | EMV Additional Information (Element 118) does not contain EMV Table Data | 72 / 90 (72 / 90) | 0 / 0 | **FIXED** |
| [D-09](#d-09) | = CD-04 | Credential on File indicators (Tables 032, 049, 056) are missing | 35 / 67 (35 / 67) | 0 / 0 | **CLOSED – test cases removed** |
| [D-12](#d-12) | = CD-03 | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | 107 / 157 (107 / 157) | 0 / 0 | **FIXED – value awaiting client input** |
| [D-14](#d-14) | = CD-07 | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | 7 / 7 (7 / 7) | 0 / 0 | **FIXED** |
| [D-15](#d-15) | – | The card data (Account Number, Card Discretionary Block Data) is empty | 249 / 473 (249 / 473) | 0 / 0 | **FIXED – value awaiting client input** |
| [D-16](#d-16) | – | The debit PIN block (Element 33) is empty | 242 / 305 (242 / 305) | 0 / 0 | **FIXED – value awaiting client input** |
| [D-17](#d-17) | – | A keyed (manual) entry mode is used for a debit card | 34 / 43 (34 / 43) | 0 / 0 | **FIXED** |
| [D-18](#d-18) | = CD-15 | Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| [D-19](#d-19) | = CD-10 | Totals Request: Card Label (Element 13) is not 4 characters | 63 / 63 (63 / 63) | 0 / 0 | **FIXED** |
| [D-20](#d-20) | – | Totals Request: Segment 105 length is counted with the wrong layout | 63 / 63 (63 / 63) | 0 / 0 | **FIXED** |
| [D-21](#d-21) | – | Totals Request: the totals do not match the transactions of the flow | 14 / 14 (14 / 14) | 12 / 12 | **NOT FIXED** |
| [D-24](#d-24) | – | A test data value differs from the test case it was generated from | 14 / 14 (14 / 14) | 0 / 0 | **FIXED** |
| [CD-19](#cd-19) | – | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 287 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |

## 2. Defect details

<a id="d-01"></a>

### D-01 – EMV Card Sequence Number (Element 188) has the wrong width – FIXED

| | |
|---|---|
| Field | Element 188 EMV Card Sequence Number (source ID = CD-01) |
| Previous run | 72 test cases / 90 files failing (log: 72 / 90) |
| Previously failing files in the new run | 88 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `001` (88 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L47) line 47 → `01`. New (TC-000001): [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L52) line 52 → `001`<br>Expected: 001 (Tag 5F34 zero-padded to 3) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002899 (1 timeout reversal) | [TD-TC-002899.json](../interlink_debit/test_data/TD-TC-002899.json#L47) line 47 | `01` | TC-000013 | [TD-TC-000013.json](../5-11-out/interlink_debit/test_data/TD-TC-000013.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-002929 (1 timeout reversal) | [TD-TC-002929.json](../interlink_debit/test_data/TD-TC-002929.json#L47) line 47 | `01` | TC-000043 | [TD-TC-000043.json](../5-11-out/interlink_debit/test_data/TD-TC-000043.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-002887 (1 purchase) | [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L47) line 47 | `01` | TC-000001 | [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L52) line 52 | `001` | FIXED |
| 4 | TC-002888 (1 purchase) | [TD-TC-002888.step1.purchase.json](../interlink_debit/test_data/TD-TC-002888.step1.purchase.json#L47) line 47 | `01` | TC-000002 | [TD-TC-000002.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000002.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 5 | TC-002889 (1 purchase) | [TD-TC-002889.step1.purchase.json](../interlink_debit/test_data/TD-TC-002889.step1.purchase.json#L47) line 47 | `01` | TC-000003 | [TD-TC-000003.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000003.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 6 | TC-002890 (1 purchase) | [TD-TC-002890.step1.purchase.json](../interlink_debit/test_data/TD-TC-002890.step1.purchase.json#L47) line 47 | `01` | TC-000004 | [TD-TC-000004.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000004.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 7 | TC-002891 (1 purchase) | [TD-TC-002891.step1.purchase.json](../interlink_debit/test_data/TD-TC-002891.step1.purchase.json#L47) line 47 | `01` | TC-000005 | [TD-TC-000005.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 8 | TC-002891 (3 next transaction) | [TD-TC-002891.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002891.step3.next-transaction.json#L47) line 47 | `01` | TC-000005 | [TD-TC-000005.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step3.next-transaction.json#L52) line 52 | `001` | FIXED |
| 9 | TC-002892 (1 purchase) | [TD-TC-002892.step1.purchase.json](../interlink_debit/test_data/TD-TC-002892.step1.purchase.json#L47) line 47 | `01` | TC-000006 | [TD-TC-000006.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 10 | TC-002893 (1 purchase) | [TD-TC-002893.step1.purchase.json](../interlink_debit/test_data/TD-TC-002893.step1.purchase.json#L47) line 47 | `01` | TC-000007 | [TD-TC-000007.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 11 | TC-002894 (1 purchase) | [TD-TC-002894.step1.purchase.json](../interlink_debit/test_data/TD-TC-002894.step1.purchase.json#L47) line 47 | `01` | TC-000008 | [TD-TC-000008.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 12 | TC-002895 (1 purchase) | [TD-TC-002895.step1.purchase.json](../interlink_debit/test_data/TD-TC-002895.step1.purchase.json#L47) line 47 | `01` | TC-000009 | [TD-TC-000009.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 13 | TC-002895 (2 return) | [TD-TC-002895.step2.return.json](../interlink_debit/test_data/TD-TC-002895.step2.return.json#L47) line 47 | `01` | TC-000009 | [TD-TC-000009.step2.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step2.return.json#L52) line 52 | `001` | FIXED |
| 14 | TC-002896 (1 purchase) | [TD-TC-002896.step1.purchase.json](../interlink_debit/test_data/TD-TC-002896.step1.purchase.json#L47) line 47 | `01` | TC-000010 | [TD-TC-000010.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 15 | TC-002897 (1 purchase) | [TD-TC-002897.step1.purchase.json](../interlink_debit/test_data/TD-TC-002897.step1.purchase.json#L47) line 47 | `01` | TC-000011 | [TD-TC-000011.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 16 | TC-002898 (1 purchase) | [TD-TC-002898.step1.purchase.json](../interlink_debit/test_data/TD-TC-002898.step1.purchase.json#L47) line 47 | `01` | TC-000012 | [TD-TC-000012.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 17 | TC-002900 (1 purchase) | [TD-TC-002900.step1.purchase.json](../interlink_debit/test_data/TD-TC-002900.step1.purchase.json#L47) line 47 | `01` | TC-000014 | [TD-TC-000014.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000014.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 18 | TC-002901 (1 purchase) | [TD-TC-002901.step1.purchase.json](../interlink_debit/test_data/TD-TC-002901.step1.purchase.json#L47) line 47 | `01` | TC-000015 | [TD-TC-000015.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 19 | TC-002901 (2 next transaction) | [TD-TC-002901.step2.next-transaction.json](../interlink_debit/test_data/TD-TC-002901.step2.next-transaction.json#L47) line 47 | `01` | TC-000015 | [TD-TC-000015.step2.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step2.next-transaction.json#L52) line 52 | `001` | FIXED |
| 20 | TC-002902 (1 purchase) | [TD-TC-002902.step1.purchase.json](../interlink_debit/test_data/TD-TC-002902.step1.purchase.json#L47) line 47 | `01` | TC-000016 | [TD-TC-000016.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 21 | TC-002902 (3 return) | [TD-TC-002902.step3.return.json](../interlink_debit/test_data/TD-TC-002902.step3.return.json#L47) line 47 | `01` | TC-000016 | [TD-TC-000016.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step3.return.json#L52) line 52 | `001` | FIXED |
| 22 | TC-002903 (1 purchase) | [TD-TC-002903.step1.purchase.json](../interlink_debit/test_data/TD-TC-002903.step1.purchase.json#L47) line 47 | `01` | TC-000017 | [TD-TC-000017.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 23 | TC-002903 (3 next transaction) | [TD-TC-002903.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002903.step3.next-transaction.json#L47) line 47 | `01` | TC-000017 | [TD-TC-000017.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step3.next-transaction.json#L52) line 52 | `001` | FIXED |
| 24 | TC-002904 (1 purchase) | [TD-TC-002904.step1.purchase.json](../interlink_debit/test_data/TD-TC-002904.step1.purchase.json#L47) line 47 | `01` | TC-000018 | [TD-TC-000018.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 25 | TC-002904 (2 resubmitted) | [TD-TC-002904.step2.resubmitted.json](../interlink_debit/test_data/TD-TC-002904.step2.resubmitted.json#L47) line 47 | `01` | TC-000018 | [TD-TC-000018.step2.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step2.resubmitted.json#L52) line 52 | `001` | FIXED |

… 65 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-02"></a>

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data – FIXED

| | |
|---|---|
| Field | Element 118 EMV Additional Information (with Elements 191/192) (source ID = CD-02) |
| Previous run | 72 test cases / 90 files failing (log: 72 / 90) |
| Previously failing files in the new run | 88 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `EMVYES / length 006` (88 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L52) line 52 → `XTV641IPVR6UAAXE…AXAYJLZN1D (984 cha…length 984 (52 chars)`. New (TC-000001): [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L57) line 57 → `EMVYES / length 006`<br>Expected: EMVYES or EMVNOT / length 006 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002899 (1 timeout reversal) | [TD-TC-002899.json](../interlink_debit/test_data/TD-TC-002899.json#L52) line 52 | `XTV641IPVR6UAAXE…AXAYJLZN1D (984 chars) / length 984` | TC-000013 | [TD-TC-000013.json](../5-11-out/interlink_debit/test_data/TD-TC-000013.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-002929 (1 timeout reversal) | [TD-TC-002929.json](../interlink_debit/test_data/TD-TC-002929.json#L52) line 52 | `4CBJTZSW6O5XCAHY…O4XNG41SFB (984 chars) / length 984` | TC-000043 | [TD-TC-000043.json](../5-11-out/interlink_debit/test_data/TD-TC-000043.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-002887 (1 purchase) | [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L52) line 52 | `XTV641IPVR6UAAXE…AXAYJLZN1D (984 chars) / length 984` | TC-000001 | [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 4 | TC-002888 (1 purchase) | [TD-TC-002888.step1.purchase.json](../interlink_debit/test_data/TD-TC-002888.step1.purchase.json#L52) line 52 | `YCQKPKFLAELHK5V7…70P3ZOU3YW (984 chars) / length 984` | TC-000002 | [TD-TC-000002.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000002.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 5 | TC-002889 (1 purchase) | [TD-TC-002889.step1.purchase.json](../interlink_debit/test_data/TD-TC-002889.step1.purchase.json#L52) line 52 | `HFE82CF0O3PVA5T1…VNW4HAILRV (984 chars) / length 984` | TC-000003 | [TD-TC-000003.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000003.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 6 | TC-002890 (1 purchase) | [TD-TC-002890.step1.purchase.json](../interlink_debit/test_data/TD-TC-002890.step1.purchase.json#L52) line 52 | `U59L9UERIBA2P97D…ANY68BKIYT (984 chars) / length 984` | TC-000004 | [TD-TC-000004.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000004.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 7 | TC-002891 (1 purchase) | [TD-TC-002891.step1.purchase.json](../interlink_debit/test_data/TD-TC-002891.step1.purchase.json#L52) line 52 | `VGVD30Z4KEHOY891…UYWV7KOQ16 (984 chars) / length 984` | TC-000005 | [TD-TC-000005.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 8 | TC-002891 (3 next transaction) | [TD-TC-002891.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002891.step3.next-transaction.json#L52) line 52 | `WPPSTSOX2VF4FEBP…Z49868APHM (984 chars) / length 984` | TC-000005 | [TD-TC-000005.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step3.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 9 | TC-002892 (1 purchase) | [TD-TC-002892.step1.purchase.json](../interlink_debit/test_data/TD-TC-002892.step1.purchase.json#L52) line 52 | `LR4EKHYF8UTY0P4K…UIA3AB1DV6 (984 chars) / length 984` | TC-000006 | [TD-TC-000006.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 10 | TC-002893 (1 purchase) | [TD-TC-002893.step1.purchase.json](../interlink_debit/test_data/TD-TC-002893.step1.purchase.json#L52) line 52 | `IV5VK4M329QX8NUR…D209RYPLUR (984 chars) / length 984` | TC-000007 | [TD-TC-000007.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 11 | TC-002894 (1 purchase) | [TD-TC-002894.step1.purchase.json](../interlink_debit/test_data/TD-TC-002894.step1.purchase.json#L52) line 52 | `D8GS1KG6BWH2MRWY…KI1D41MI55 (984 chars) / length 984` | TC-000008 | [TD-TC-000008.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 12 | TC-002895 (1 purchase) | [TD-TC-002895.step1.purchase.json](../interlink_debit/test_data/TD-TC-002895.step1.purchase.json#L52) line 52 | `6ZA2Y72ZQFSQ1D15…7VQ14SPVYZ (984 chars) / length 984` | TC-000009 | [TD-TC-000009.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 13 | TC-002895 (2 return) | [TD-TC-002895.step2.return.json](../interlink_debit/test_data/TD-TC-002895.step2.return.json#L52) line 52 | `KG1HCLEWFEH7S9V9…TTZJC5AY5U (984 chars) / length 984` | TC-000009 | [TD-TC-000009.step2.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step2.return.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 14 | TC-002896 (1 purchase) | [TD-TC-002896.step1.purchase.json](../interlink_debit/test_data/TD-TC-002896.step1.purchase.json#L52) line 52 | `LI73GZ138K4B1UG7…8ZVRVCMIWA (984 chars) / length 984` | TC-000010 | [TD-TC-000010.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 15 | TC-002897 (1 purchase) | [TD-TC-002897.step1.purchase.json](../interlink_debit/test_data/TD-TC-002897.step1.purchase.json#L52) line 52 | `V0511MQCQP99E7ES…JWSNQHMFYW (984 chars) / length 984` | TC-000011 | [TD-TC-000011.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 16 | TC-002898 (1 purchase) | [TD-TC-002898.step1.purchase.json](../interlink_debit/test_data/TD-TC-002898.step1.purchase.json#L52) line 52 | `UKTCBI09U61Q4ANG…G5EBKW2RAM (984 chars) / length 984` | TC-000012 | [TD-TC-000012.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 17 | TC-002900 (1 purchase) | [TD-TC-002900.step1.purchase.json](../interlink_debit/test_data/TD-TC-002900.step1.purchase.json#L52) line 52 | `DBS0ZENP9X9VONJE…DSM5GQVIYA (984 chars) / length 984` | TC-000014 | [TD-TC-000014.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000014.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 18 | TC-002901 (1 purchase) | [TD-TC-002901.step1.purchase.json](../interlink_debit/test_data/TD-TC-002901.step1.purchase.json#L52) line 52 | `F0GZ8F3A5GJRGDS5…HBBSVAFDFN (984 chars) / length 984` | TC-000015 | [TD-TC-000015.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 19 | TC-002901 (2 next transaction) | [TD-TC-002901.step2.next-transaction.json](../interlink_debit/test_data/TD-TC-002901.step2.next-transaction.json#L52) line 52 | `498C20ITHBDU6KVR…CFA6V6LXXG (984 chars) / length 984` | TC-000015 | [TD-TC-000015.step2.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step2.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 20 | TC-002902 (1 purchase) | [TD-TC-002902.step1.purchase.json](../interlink_debit/test_data/TD-TC-002902.step1.purchase.json#L52) line 52 | `AALKJRS7GTIN4A1D…H3VLCLDPAL (984 chars) / length 984` | TC-000016 | [TD-TC-000016.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 21 | TC-002902 (3 return) | [TD-TC-002902.step3.return.json](../interlink_debit/test_data/TD-TC-002902.step3.return.json#L52) line 52 | `CX4QUJFBMMQF8ZPD…IZNCRNPYRY (984 chars) / length 984` | TC-000016 | [TD-TC-000016.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step3.return.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 22 | TC-002903 (1 purchase) | [TD-TC-002903.step1.purchase.json](../interlink_debit/test_data/TD-TC-002903.step1.purchase.json#L52) line 52 | `GC6LR17UF7OONM82…SUMBBLWRT1 (984 chars) / length 984` | TC-000017 | [TD-TC-000017.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 23 | TC-002903 (3 next transaction) | [TD-TC-002903.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002903.step3.next-transaction.json#L52) line 52 | `5NPGDYKNIU7NZB9M…E9CEOOZ8ZU (984 chars) / length 984` | TC-000017 | [TD-TC-000017.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step3.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 24 | TC-002904 (1 purchase) | [TD-TC-002904.step1.purchase.json](../interlink_debit/test_data/TD-TC-002904.step1.purchase.json#L52) line 52 | `XJLXE2NF43AHV3IM…FAYJUCXJSJ (984 chars) / length 984` | TC-000018 | [TD-TC-000018.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 25 | TC-002904 (2 resubmitted) | [TD-TC-002904.step2.resubmitted.json](../interlink_debit/test_data/TD-TC-002904.step2.resubmitted.json#L52) line 52 | `RH3SHLJT3L1TTMQK…Y74C0QN084 (984 chars) / length 984` | TC-000018 | [TD-TC-000018.step2.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step2.resubmitted.json#L57) line 57 | `EMVYES / length 006` | FIXED |

… 65 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-09"></a>

### D-09 – Credential on File indicators (Tables 032, 049, 056) are missing – CLOSED – test cases removed

| | |
|---|---|
| Field | Element 113 Tables 032 / 049 / 056 (Credential on File indicators) (source ID = CD-04) |
| Previous run | 35 test cases / 67 files failing (log: 35 / 67) |
| Previously failing files in the new run | 67 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| Not in the new catalog | 67 files of test cases that were removed: Credential on File (67) |
| Check it yourself | Previous: [TD-TC-003035.json](../interlink_debit/test_data/TD-TC-003035.json#L30) line 30 → `032-08 C/I missing; 049-02 missing`. New: template / entry mode no longer in the catalog<br>Expected: Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003035 (1 purchase) | [TD-TC-003035.json](../interlink_debit/test_data/TD-TC-003035.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 2 | TC-003036 (1 purchase) | [TD-TC-003036.step1.purchase.json](../interlink_debit/test_data/TD-TC-003036.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 3 | TC-003036 (2 timeout reversal) | [TD-TC-003036.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-003036.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 4 | TC-003037 (1 purchase) | [TD-TC-003037.step1.purchase.json](../interlink_debit/test_data/TD-TC-003037.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 5 | TC-003037 (2 timeout reversal) | [TD-TC-003037.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-003037.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 6 | TC-003038 (1 purchase) | [TD-TC-003038.step1.purchase.json](../interlink_debit/test_data/TD-TC-003038.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 7 | TC-003038 (2 timeout reversal) | [TD-TC-003038.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-003038.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 8 | TC-003039 (1 purchase) | [TD-TC-003039.step1.purchase.json](../interlink_debit/test_data/TD-TC-003039.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 9 | TC-003039 (2 timeout reversal) | [TD-TC-003039.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-003039.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 10 | TC-003039 (3 next transaction) | [TD-TC-003039.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-003039.step3.next-transaction.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 11 | TC-003040 (1 purchase) | [TD-TC-003040.step1.purchase.json](../interlink_debit/test_data/TD-TC-003040.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 12 | TC-003040 (2 timeout reversal) | [TD-TC-003040.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-003040.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 13 | TC-003040 (3 tor retry) | [TD-TC-003040.step3.tor-retry.json](../interlink_debit/test_data/TD-TC-003040.step3.tor-retry.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 14 | TC-003040 (4 tor retry) | [TD-TC-003040.step4.tor-retry.json](../interlink_debit/test_data/TD-TC-003040.step4.tor-retry.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 15 | TC-003041 (1 purchase) | [TD-TC-003041.step1.purchase.json](../interlink_debit/test_data/TD-TC-003041.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 16 | TC-003042 (1 purchase) | [TD-TC-003042.step1.purchase.json](../interlink_debit/test_data/TD-TC-003042.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 17 | TC-003042 (2 reversal) | [TD-TC-003042.step2.reversal.json](../interlink_debit/test_data/TD-TC-003042.step2.reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 18 | TC-003043 (1 purchase) | [TD-TC-003043.step1.purchase.json](../interlink_debit/test_data/TD-TC-003043.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 19 | TC-003043 (2 return) | [TD-TC-003043.step2.return.json](../interlink_debit/test_data/TD-TC-003043.step2.return.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 20 | TC-003044 (1 purchase) | [TD-TC-003044.step1.purchase.json](../interlink_debit/test_data/TD-TC-003044.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 21 | TC-003044 (2 timeout reversal) | [TD-TC-003044.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-003044.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 22 | TC-003045 (1 purchase) | [TD-TC-003045.step1.purchase.json](../interlink_debit/test_data/TD-TC-003045.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 23 | TC-003046 (1 purchase) | [TD-TC-003046.step1.purchase.json](../interlink_debit/test_data/TD-TC-003046.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 24 | TC-003047 (1 timeout reversal) | [TD-TC-003047.json](../interlink_debit/test_data/TD-TC-003047.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 25 | TC-003048 (1 purchase) | [TD-TC-003048.step1.purchase.json](../interlink_debit/test_data/TD-TC-003048.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing` | – | – | template / entry mode no longer in the catalog | REMOVED |

… 42 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-12"></a>

### D-12 – Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions – FIXED – value awaiting client input

| | |
|---|---|
| Field | Element 113 Table 030 (POS Condition Code) (source ID = CD-03) |
| Previous run | 107 test cases / 157 files failing (log: 107 / 157) |
| Previously failing files in the new run | 88 awaiting client input, 2 changed, 67 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `030 sent, length 010, value empty` (88 files); `Segment 130 is no longer sent on this step (no longer an EMV request)` (2 files) |
| Notes | value listed in awaiting_inputs; length 010 is declared for the value to come, so the file cannot be sent until the code is filled in (88); Segment 130 is no longer sent on this step (no longer an EMV request) (2) |
| Not in the new catalog | 67 files of test cases that were removed: Credential on File (67) |
| Check it yourself | Previous: [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L30) line 30 → `not sent (tables: 005=051, 031=12)`. New (TC-000001): [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L44) line 44 → `030 sent, length 010, value empty`<br>Expected: 10 digits from the client's certified terminal |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002887 (1 purchase) | [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000001 | [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-002888 (1 purchase) | [TD-TC-002888.step1.purchase.json](../interlink_debit/test_data/TD-TC-002888.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000002 | [TD-TC-000002.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000002.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-002889 (1 purchase) | [TD-TC-002889.step1.purchase.json](../interlink_debit/test_data/TD-TC-002889.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000003 | [TD-TC-000003.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000003.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-002890 (1 purchase) | [TD-TC-002890.step1.purchase.json](../interlink_debit/test_data/TD-TC-002890.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000004 | [TD-TC-000004.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000004.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-002891 (1 purchase) | [TD-TC-002891.step1.purchase.json](../interlink_debit/test_data/TD-TC-002891.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000005 | [TD-TC-000005.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-002891 (3 next transaction) | [TD-TC-002891.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002891.step3.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000005 | [TD-TC-000005.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step3.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-002892 (1 purchase) | [TD-TC-002892.step1.purchase.json](../interlink_debit/test_data/TD-TC-002892.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000006 | [TD-TC-000006.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-002893 (1 purchase) | [TD-TC-002893.step1.purchase.json](../interlink_debit/test_data/TD-TC-002893.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000007 | [TD-TC-000007.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 9 | TC-002894 (1 purchase) | [TD-TC-002894.step1.purchase.json](../interlink_debit/test_data/TD-TC-002894.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000008 | [TD-TC-000008.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 10 | TC-002895 (1 purchase) | [TD-TC-002895.step1.purchase.json](../interlink_debit/test_data/TD-TC-002895.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000009 | [TD-TC-000009.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 11 | TC-002895 (2 return) | [TD-TC-002895.step2.return.json](../interlink_debit/test_data/TD-TC-002895.step2.return.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000009 | [TD-TC-000009.step2.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step2.return.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 12 | TC-002896 (1 purchase) | [TD-TC-002896.step1.purchase.json](../interlink_debit/test_data/TD-TC-002896.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000010 | [TD-TC-000010.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 13 | TC-002897 (1 purchase) | [TD-TC-002897.step1.purchase.json](../interlink_debit/test_data/TD-TC-002897.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000011 | [TD-TC-000011.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 14 | TC-002898 (1 purchase) | [TD-TC-002898.step1.purchase.json](../interlink_debit/test_data/TD-TC-002898.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000012 | [TD-TC-000012.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 15 | TC-002900 (1 purchase) | [TD-TC-002900.step1.purchase.json](../interlink_debit/test_data/TD-TC-002900.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000014 | [TD-TC-000014.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000014.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 16 | TC-002901 (1 purchase) | [TD-TC-002901.step1.purchase.json](../interlink_debit/test_data/TD-TC-002901.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000015 | [TD-TC-000015.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 17 | TC-002901 (2 next transaction) | [TD-TC-002901.step2.next-transaction.json](../interlink_debit/test_data/TD-TC-002901.step2.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000015 | [TD-TC-000015.step2.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step2.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 18 | TC-002902 (1 purchase) | [TD-TC-002902.step1.purchase.json](../interlink_debit/test_data/TD-TC-002902.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000016 | [TD-TC-000016.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 19 | TC-002902 (3 return) | [TD-TC-002902.step3.return.json](../interlink_debit/test_data/TD-TC-002902.step3.return.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000016 | [TD-TC-000016.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step3.return.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 20 | TC-002903 (1 purchase) | [TD-TC-002903.step1.purchase.json](../interlink_debit/test_data/TD-TC-002903.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000017 | [TD-TC-000017.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 21 | TC-002903 (3 next transaction) | [TD-TC-002903.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002903.step3.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000017 | [TD-TC-000017.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step3.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 22 | TC-002904 (1 purchase) | [TD-TC-002904.step1.purchase.json](../interlink_debit/test_data/TD-TC-002904.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000018 | [TD-TC-000018.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 23 | TC-002904 (2 resubmitted) | [TD-TC-002904.step2.resubmitted.json](../interlink_debit/test_data/TD-TC-002904.step2.resubmitted.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000018 | [TD-TC-000018.step2.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step2.resubmitted.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 24 | TC-002905 (1 purchase) | [TD-TC-002905.step1.purchase.json](../interlink_debit/test_data/TD-TC-002905.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000019 | [TD-TC-000019.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000019.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 25 | TC-002905 (3 resubmitted) | [TD-TC-002905.step3.resubmitted.json](../interlink_debit/test_data/TD-TC-002905.step3.resubmitted.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000019 | [TD-TC-000019.step3.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000019.step3.resubmitted.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |

… 132 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-14"></a>

### D-14 – Follow-on step (completion, cancellation, reversal): amount data built from an impossible value – FIXED

| | |
|---|---|
| Field | Element 58 Nonfuel Amount (test case value), Element 84 Segment Length (source ID = CD-07) |
| Previous run | 7 test cases / 7 files failing (log: 7 / 7) |
| Previously failing files in the new run | 6 fixed, 1 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `SegmentLength 063; provisional amount 750` (6 files) |
| Not in the new catalog | 1 file of test cases that were removed: Credential on File (1) |
| Check it yourself | Previous: [TD-TC-002915.step2.reversal.json](../interlink_debit/test_data/TD-TC-002915.step2.reversal.json#L7) line 7 → `SegmentLength 068; provisional amount 37526954`. New (TC-000027): [TD-TC-000027.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000027.step2.reversal.json#L7) line 7 → `SegmentLength 063; provisional amount 750`<br>Expected: Segment Length for an amount of at most 4 digits (153–156) or computed at run time; provisional amount ≤ 1500 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002915 (2 reversal) | [TD-TC-002915.step2.reversal.json](../interlink_debit/test_data/TD-TC-002915.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 37526954` | TC-000027 | [TD-TC-000027.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000027.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 2 | TC-002945 (2 reversal) | [TD-TC-002945.step2.reversal.json](../interlink_debit/test_data/TD-TC-002945.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 20538542` | TC-000057 | [TD-TC-000057.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000057.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 3 | TC-002974 (2 reversal) | [TD-TC-002974.step2.reversal.json](../interlink_debit/test_data/TD-TC-002974.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 61626942` | TC-000086 | [TD-TC-000086.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000086.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 4 | TC-003004 (2 reversal) | [TD-TC-003004.step2.reversal.json](../interlink_debit/test_data/TD-TC-003004.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 94756011` | TC-000116 | [TD-TC-000116.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000116.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 5 | TC-003034 (2 reversal) | [TD-TC-003034.step2.reversal.json](../interlink_debit/test_data/TD-TC-003034.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 86250835` | TC-000146 | [TD-TC-000146.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000146.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 6 | TC-003092 (2 reversal) | [TD-TC-003092.step2.reversal.json](../interlink_debit/test_data/TD-TC-003092.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 02853141` | TC-000175 | [TD-TC-000175.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000175.step2.reversal.json#L7) line 7 | `SegmentLength 063; provisional amount 750` | FIXED |
| 7 | TC-003063 (2 reversal) | [TD-TC-003063.step2.reversal.json](../interlink_debit/test_data/TD-TC-003063.step2.reversal.json#L7) line 7 | `SegmentLength 068; provisional amount 01835614` | – | – | template / entry mode no longer in the catalog | REMOVED |

---

<a id="d-15"></a>

### D-15 – The card data (Account Number, Card Discretionary Block Data) is empty – FIXED – value awaiting client input

| | |
|---|---|
| Field | Element 2 Account Number and Element 12 Card Discretionary Block Data |
| Previous run | 249 test cases / 473 files failing (log: 249 / 473) |
| Previously failing files in the new run | 406 awaiting client input, 67 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `<AWAITING:AccountNumber> / <AWAITING:CardDiscretionaryBlockData>` (406 files) |
| Not in the new catalog | 67 files of test cases that were removed: Credential on File (67) |
| Check it yourself | Previous: [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L14) line 14 → `(empty) / (empty)`. New (TC-000001): [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L14) line 14 → `<AWAITING:AccountNumber> / <AWAITING…BlockData> (64 chars)`<br>Expected: card data (or an explicit placeholder while awaiting the client card) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002887 (1 purchase) | [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L14) line 14 | `(empty) / (empty)` | TC-000001 | [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 2 | TC-002888 (1 purchase) | [TD-TC-002888.step1.purchase.json](../interlink_debit/test_data/TD-TC-002888.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000002 | [TD-TC-000002.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000002.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 3 | TC-002888 (2 timeout reversal) | [TD-TC-002888.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-002888.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000002 | [TD-TC-000002.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000002.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 4 | TC-002889 (1 purchase) | [TD-TC-002889.step1.purchase.json](../interlink_debit/test_data/TD-TC-002889.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000003 | [TD-TC-000003.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000003.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 5 | TC-002889 (2 timeout reversal) | [TD-TC-002889.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-002889.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000003 | [TD-TC-000003.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000003.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 6 | TC-002890 (1 purchase) | [TD-TC-002890.step1.purchase.json](../interlink_debit/test_data/TD-TC-002890.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000004 | [TD-TC-000004.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000004.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 7 | TC-002890 (2 timeout reversal) | [TD-TC-002890.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-002890.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000004 | [TD-TC-000004.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000004.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 8 | TC-002891 (1 purchase) | [TD-TC-002891.step1.purchase.json](../interlink_debit/test_data/TD-TC-002891.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000005 | [TD-TC-000005.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 9 | TC-002891 (2 timeout reversal) | [TD-TC-002891.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-002891.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000005 | [TD-TC-000005.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 10 | TC-002891 (3 next transaction) | [TD-TC-002891.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002891.step3.next-transaction.json#L14) line 14 | `(empty) / (empty)` | TC-000005 | [TD-TC-000005.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step3.next-transaction.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 11 | TC-002892 (1 purchase) | [TD-TC-002892.step1.purchase.json](../interlink_debit/test_data/TD-TC-002892.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000006 | [TD-TC-000006.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 12 | TC-002892 (2 timeout reversal) | [TD-TC-002892.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-002892.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000006 | [TD-TC-000006.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 13 | TC-002892 (3 tor retry) | [TD-TC-002892.step3.tor-retry.json](../interlink_debit/test_data/TD-TC-002892.step3.tor-retry.json#L14) line 14 | `(empty) / (empty)` | TC-000006 | [TD-TC-000006.step3.tor-retry.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step3.tor-retry.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 14 | TC-002892 (4 tor retry) | [TD-TC-002892.step4.tor-retry.json](../interlink_debit/test_data/TD-TC-002892.step4.tor-retry.json#L14) line 14 | `(empty) / (empty)` | TC-000006 | [TD-TC-000006.step4.tor-retry.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step4.tor-retry.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 15 | TC-002893 (1 purchase) | [TD-TC-002893.step1.purchase.json](../interlink_debit/test_data/TD-TC-002893.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000007 | [TD-TC-000007.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 16 | TC-002894 (1 purchase) | [TD-TC-002894.step1.purchase.json](../interlink_debit/test_data/TD-TC-002894.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000008 | [TD-TC-000008.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 17 | TC-002894 (2 reversal) | [TD-TC-002894.step2.reversal.json](../interlink_debit/test_data/TD-TC-002894.step2.reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000008 | [TD-TC-000008.step2.reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step2.reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 18 | TC-002895 (1 purchase) | [TD-TC-002895.step1.purchase.json](../interlink_debit/test_data/TD-TC-002895.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000009 | [TD-TC-000009.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 19 | TC-002895 (2 return) | [TD-TC-002895.step2.return.json](../interlink_debit/test_data/TD-TC-002895.step2.return.json#L14) line 14 | `(empty) / (empty)` | TC-000009 | [TD-TC-000009.step2.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step2.return.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 20 | TC-002896 (1 purchase) | [TD-TC-002896.step1.purchase.json](../interlink_debit/test_data/TD-TC-002896.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000010 | [TD-TC-000010.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 21 | TC-002896 (2 timeout reversal) | [TD-TC-002896.step2.timeout-reversal.json](../interlink_debit/test_data/TD-TC-002896.step2.timeout-reversal.json#L14) line 14 | `(empty) / (empty)` | TC-000010 | [TD-TC-000010.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step2.timeout-reversal.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 22 | TC-002897 (1 purchase) | [TD-TC-002897.step1.purchase.json](../interlink_debit/test_data/TD-TC-002897.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000011 | [TD-TC-000011.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 23 | TC-002898 (1 purchase) | [TD-TC-002898.step1.purchase.json](../interlink_debit/test_data/TD-TC-002898.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000012 | [TD-TC-000012.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 24 | TC-002899 (1 timeout reversal) | [TD-TC-002899.json](../interlink_debit/test_data/TD-TC-002899.json#L14) line 14 | `(empty) / (empty)` | TC-000013 | [TD-TC-000013.json](../5-11-out/interlink_debit/test_data/TD-TC-000013.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |
| 25 | TC-002900 (1 purchase) | [TD-TC-002900.step1.purchase.json](../interlink_debit/test_data/TD-TC-002900.step1.purchase.json#L14) line 14 | `(empty) / (empty)` | TC-000014 | [TD-TC-000014.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000014.step1.purchase.json#L14) line 14 | `<AWAITING:AccountNumber> / <AWAITING:CardDiscr…BlockData> (64 chars)` | AWAITING CLIENT INPUT |

… 448 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-16"></a>

### D-16 – The debit PIN block (Element 33) is empty – FIXED – value awaiting client input

| | |
|---|---|
| Field | Element 33 Encrypted PIN Block Data |
| Previous run | 242 test cases / 305 files failing (log: 242 / 305) |
| Previously failing files in the new run | 262 awaiting client input, 43 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `<AWAITING:EncryptedPINBlockData>` (262 files) |
| Not in the new catalog | 43 files of test cases that were removed: Credential on File (43) |
| Check it yourself | Previous: [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L16) line 16 → `(empty)`. New (TC-000001): [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L16) line 16 → `<AWAITING:EncryptedPINBlockData>`<br>Expected: DUKPT KSN + PIN block (or an explicit placeholder while awaiting the client key) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002887 (1 purchase) | [TD-TC-002887.json](../interlink_debit/test_data/TD-TC-002887.json#L16) line 16 | `(empty)` | TC-000001 | [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 2 | TC-002888 (1 purchase) | [TD-TC-002888.step1.purchase.json](../interlink_debit/test_data/TD-TC-002888.step1.purchase.json#L16) line 16 | `(empty)` | TC-000002 | [TD-TC-000002.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000002.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 3 | TC-002889 (1 purchase) | [TD-TC-002889.step1.purchase.json](../interlink_debit/test_data/TD-TC-002889.step1.purchase.json#L16) line 16 | `(empty)` | TC-000003 | [TD-TC-000003.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000003.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 4 | TC-002890 (1 purchase) | [TD-TC-002890.step1.purchase.json](../interlink_debit/test_data/TD-TC-002890.step1.purchase.json#L16) line 16 | `(empty)` | TC-000004 | [TD-TC-000004.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000004.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 5 | TC-002891 (1 purchase) | [TD-TC-002891.step1.purchase.json](../interlink_debit/test_data/TD-TC-002891.step1.purchase.json#L16) line 16 | `(empty)` | TC-000005 | [TD-TC-000005.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 6 | TC-002891 (3 next transaction) | [TD-TC-002891.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002891.step3.next-transaction.json#L16) line 16 | `(empty)` | TC-000005 | [TD-TC-000005.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000005.step3.next-transaction.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 7 | TC-002892 (1 purchase) | [TD-TC-002892.step1.purchase.json](../interlink_debit/test_data/TD-TC-002892.step1.purchase.json#L16) line 16 | `(empty)` | TC-000006 | [TD-TC-000006.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000006.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 8 | TC-002893 (1 purchase) | [TD-TC-002893.step1.purchase.json](../interlink_debit/test_data/TD-TC-002893.step1.purchase.json#L16) line 16 | `(empty)` | TC-000007 | [TD-TC-000007.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 9 | TC-002894 (1 purchase) | [TD-TC-002894.step1.purchase.json](../interlink_debit/test_data/TD-TC-002894.step1.purchase.json#L16) line 16 | `(empty)` | TC-000008 | [TD-TC-000008.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 10 | TC-002895 (1 purchase) | [TD-TC-002895.step1.purchase.json](../interlink_debit/test_data/TD-TC-002895.step1.purchase.json#L16) line 16 | `(empty)` | TC-000009 | [TD-TC-000009.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 11 | TC-002895 (2 return) | [TD-TC-002895.step2.return.json](../interlink_debit/test_data/TD-TC-002895.step2.return.json#L16) line 16 | `(empty)` | TC-000009 | [TD-TC-000009.step2.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step2.return.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 12 | TC-002896 (1 purchase) | [TD-TC-002896.step1.purchase.json](../interlink_debit/test_data/TD-TC-002896.step1.purchase.json#L16) line 16 | `(empty)` | TC-000010 | [TD-TC-000010.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 13 | TC-002897 (1 purchase) | [TD-TC-002897.step1.purchase.json](../interlink_debit/test_data/TD-TC-002897.step1.purchase.json#L16) line 16 | `(empty)` | TC-000011 | [TD-TC-000011.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 14 | TC-002898 (1 purchase) | [TD-TC-002898.step1.purchase.json](../interlink_debit/test_data/TD-TC-002898.step1.purchase.json#L16) line 16 | `(empty)` | TC-000012 | [TD-TC-000012.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 15 | TC-002900 (1 purchase) | [TD-TC-002900.step1.purchase.json](../interlink_debit/test_data/TD-TC-002900.step1.purchase.json#L16) line 16 | `(empty)` | TC-000014 | [TD-TC-000014.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000014.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 16 | TC-002901 (1 purchase) | [TD-TC-002901.step1.purchase.json](../interlink_debit/test_data/TD-TC-002901.step1.purchase.json#L16) line 16 | `(empty)` | TC-000015 | [TD-TC-000015.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 17 | TC-002901 (2 next transaction) | [TD-TC-002901.step2.next-transaction.json](../interlink_debit/test_data/TD-TC-002901.step2.next-transaction.json#L16) line 16 | `(empty)` | TC-000015 | [TD-TC-000015.step2.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000015.step2.next-transaction.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 18 | TC-002902 (1 purchase) | [TD-TC-002902.step1.purchase.json](../interlink_debit/test_data/TD-TC-002902.step1.purchase.json#L16) line 16 | `(empty)` | TC-000016 | [TD-TC-000016.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 19 | TC-002902 (3 return) | [TD-TC-002902.step3.return.json](../interlink_debit/test_data/TD-TC-002902.step3.return.json#L16) line 16 | `(empty)` | TC-000016 | [TD-TC-000016.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000016.step3.return.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 20 | TC-002903 (1 purchase) | [TD-TC-002903.step1.purchase.json](../interlink_debit/test_data/TD-TC-002903.step1.purchase.json#L16) line 16 | `(empty)` | TC-000017 | [TD-TC-000017.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 21 | TC-002903 (3 next transaction) | [TD-TC-002903.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-002903.step3.next-transaction.json#L16) line 16 | `(empty)` | TC-000017 | [TD-TC-000017.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000017.step3.next-transaction.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 22 | TC-002904 (1 purchase) | [TD-TC-002904.step1.purchase.json](../interlink_debit/test_data/TD-TC-002904.step1.purchase.json#L16) line 16 | `(empty)` | TC-000018 | [TD-TC-000018.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 23 | TC-002904 (2 resubmitted) | [TD-TC-002904.step2.resubmitted.json](../interlink_debit/test_data/TD-TC-002904.step2.resubmitted.json#L16) line 16 | `(empty)` | TC-000018 | [TD-TC-000018.step2.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000018.step2.resubmitted.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 24 | TC-002905 (1 purchase) | [TD-TC-002905.step1.purchase.json](../interlink_debit/test_data/TD-TC-002905.step1.purchase.json#L16) line 16 | `(empty)` | TC-000019 | [TD-TC-000019.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000019.step1.purchase.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |
| 25 | TC-002905 (3 resubmitted) | [TD-TC-002905.step3.resubmitted.json](../interlink_debit/test_data/TD-TC-002905.step3.resubmitted.json#L16) line 16 | `(empty)` | TC-000019 | [TD-TC-000019.step3.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000019.step3.resubmitted.json#L16) line 16 | `<AWAITING:EncryptedPINBlockData>` | AWAITING CLIENT INPUT |

… 280 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-17"></a>

### D-17 – A keyed (manual) entry mode is used for a debit card – FIXED

| | |
|---|---|
| Field | Element 113 Table 005 (PAN entry mode) |
| Previous run | 34 test cases / 43 files failing (log: 34 / 43) |
| Previously failing files in the new run | 43 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `801` (43 files) |
| Check it yourself | Previous: [TD-TC-003005.json](../interlink_debit/test_data/TD-TC-003005.json#L34) line 34 → `791`. New (TC-000119): [TD-TC-000119.json](../5-11-out/interlink_debit/test_data/TD-TC-000119.json#L34) line 34 → `801`<br>Expected: a card-read entry mode (e.g. 80 for chip fallback to swipe) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-003005 (1 purchase) | [TD-TC-003005.json](../interlink_debit/test_data/TD-TC-003005.json#L34) line 34 | `791` | TC-000119 | [TD-TC-000119.json](../5-11-out/interlink_debit/test_data/TD-TC-000119.json#L34) line 34 | `801` | FIXED |
| 2 | TC-003006 (1 purchase) | [TD-TC-003006.step1.purchase.json](../interlink_debit/test_data/TD-TC-003006.step1.purchase.json#L34) line 34 | `791` | TC-000120 | [TD-TC-000120.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000120.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 3 | TC-003007 (1 purchase) | [TD-TC-003007.step1.purchase.json](../interlink_debit/test_data/TD-TC-003007.step1.purchase.json#L34) line 34 | `791` | TC-000121 | [TD-TC-000121.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000121.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 4 | TC-003008 (1 purchase) | [TD-TC-003008.step1.purchase.json](../interlink_debit/test_data/TD-TC-003008.step1.purchase.json#L34) line 34 | `791` | TC-000122 | [TD-TC-000122.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000122.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 5 | TC-003009 (1 purchase) | [TD-TC-003009.step1.purchase.json](../interlink_debit/test_data/TD-TC-003009.step1.purchase.json#L34) line 34 | `791` | TC-000123 | [TD-TC-000123.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000123.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 6 | TC-003009 (3 next transaction) | [TD-TC-003009.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-003009.step3.next-transaction.json#L34) line 34 | `791` | TC-000123 | [TD-TC-000123.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000123.step3.next-transaction.json#L34) line 34 | `801` | FIXED |
| 7 | TC-003010 (1 purchase) | [TD-TC-003010.step1.purchase.json](../interlink_debit/test_data/TD-TC-003010.step1.purchase.json#L34) line 34 | `791` | TC-000124 | [TD-TC-000124.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000124.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 8 | TC-003011 (1 purchase) | [TD-TC-003011.step1.purchase.json](../interlink_debit/test_data/TD-TC-003011.step1.purchase.json#L34) line 34 | `791` | TC-000125 | [TD-TC-000125.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000125.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 9 | TC-003012 (1 purchase) | [TD-TC-003012.step1.purchase.json](../interlink_debit/test_data/TD-TC-003012.step1.purchase.json#L34) line 34 | `791` | TC-000126 | [TD-TC-000126.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000126.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 10 | TC-003013 (1 purchase) | [TD-TC-003013.step1.purchase.json](../interlink_debit/test_data/TD-TC-003013.step1.purchase.json#L34) line 34 | `791` | TC-000127 | [TD-TC-000127.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000127.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 11 | TC-003013 (2 return) | [TD-TC-003013.step2.return.json](../interlink_debit/test_data/TD-TC-003013.step2.return.json#L34) line 34 | `791` | TC-000127 | [TD-TC-000127.step2.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000127.step2.return.json#L34) line 34 | `801` | FIXED |
| 12 | TC-003014 (1 purchase) | [TD-TC-003014.step1.purchase.json](../interlink_debit/test_data/TD-TC-003014.step1.purchase.json#L34) line 34 | `791` | TC-000128 | [TD-TC-000128.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000128.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 13 | TC-003015 (1 purchase) | [TD-TC-003015.step1.purchase.json](../interlink_debit/test_data/TD-TC-003015.step1.purchase.json#L34) line 34 | `791` | TC-000129 | [TD-TC-000129.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000129.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 14 | TC-003016 (1 purchase) | [TD-TC-003016.step1.purchase.json](../interlink_debit/test_data/TD-TC-003016.step1.purchase.json#L34) line 34 | `791` | TC-000130 | [TD-TC-000130.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000130.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 15 | TC-003018 (1 purchase) | [TD-TC-003018.step1.purchase.json](../interlink_debit/test_data/TD-TC-003018.step1.purchase.json#L34) line 34 | `791` | TC-000132 | [TD-TC-000132.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000132.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 16 | TC-003020 (1 purchase) | [TD-TC-003020.step1.purchase.json](../interlink_debit/test_data/TD-TC-003020.step1.purchase.json#L34) line 34 | `791` | TC-000134 | [TD-TC-000134.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000134.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 17 | TC-003020 (2 next transaction) | [TD-TC-003020.step2.next-transaction.json](../interlink_debit/test_data/TD-TC-003020.step2.next-transaction.json#L34) line 34 | `791` | TC-000134 | [TD-TC-000134.step2.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000134.step2.next-transaction.json#L34) line 34 | `801` | FIXED |
| 18 | TC-003021 (1 purchase) | [TD-TC-003021.step1.purchase.json](../interlink_debit/test_data/TD-TC-003021.step1.purchase.json#L34) line 34 | `791` | TC-000135 | [TD-TC-000135.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000135.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 19 | TC-003021 (3 return) | [TD-TC-003021.step3.return.json](../interlink_debit/test_data/TD-TC-003021.step3.return.json#L34) line 34 | `791` | TC-000135 | [TD-TC-000135.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000135.step3.return.json#L34) line 34 | `801` | FIXED |
| 20 | TC-003022 (1 purchase) | [TD-TC-003022.step1.purchase.json](../interlink_debit/test_data/TD-TC-003022.step1.purchase.json#L34) line 34 | `791` | TC-000136 | [TD-TC-000136.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000136.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 21 | TC-003022 (3 next transaction) | [TD-TC-003022.step3.next-transaction.json](../interlink_debit/test_data/TD-TC-003022.step3.next-transaction.json#L34) line 34 | `791` | TC-000136 | [TD-TC-000136.step3.next-transaction.json](../5-11-out/interlink_debit/test_data/TD-TC-000136.step3.next-transaction.json#L34) line 34 | `801` | FIXED |
| 22 | TC-003023 (1 purchase) | [TD-TC-003023.step1.purchase.json](../interlink_debit/test_data/TD-TC-003023.step1.purchase.json#L34) line 34 | `791` | TC-000137 | [TD-TC-000137.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000137.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 23 | TC-003023 (2 resubmitted) | [TD-TC-003023.step2.resubmitted.json](../interlink_debit/test_data/TD-TC-003023.step2.resubmitted.json#L34) line 34 | `791` | TC-000137 | [TD-TC-000137.step2.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000137.step2.resubmitted.json#L34) line 34 | `801` | FIXED |
| 24 | TC-003024 (1 purchase) | [TD-TC-003024.step1.purchase.json](../interlink_debit/test_data/TD-TC-003024.step1.purchase.json#L34) line 34 | `791` | TC-000138 | [TD-TC-000138.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000138.step1.purchase.json#L34) line 34 | `801` | FIXED |
| 25 | TC-003024 (3 resubmitted) | [TD-TC-003024.step3.resubmitted.json](../interlink_debit/test_data/TD-TC-003024.step3.resubmitted.json#L34) line 34 | `791` | TC-000138 | [TD-TC-000138.step3.resubmitted.json](../5-11-out/interlink_debit/test_data/TD-TC-000138.step3.resubmitted.json#L34) line 34 | `801` | FIXED |

… 18 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-18"></a>

### D-18 – Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back – FIXED

| | |
|---|---|
| Field | Element 190 EMV Chip Data – Tags 9F02, 9F03 and 9C (source ID = CD-15) |
| Previous run | 4 test cases / 4 files failing (log: 4 / 4) |
| Previously failing files in the new run | 4 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F02 000000002000, 9F03 000000000500, 9C 09 (Cash Amount 500)` (4 files) |
| Check it yourself | Previous: [TD-TC-002913.json](../interlink_debit/test_data/TD-TC-002913.json#L49) line 49 → `9F02 000000001500, 9F03 000000000000…mount 500) (61 chars)`. New (TC-000025): [TD-TC-000025.json](../5-11-out/interlink_debit/test_data/TD-TC-000025.json#L54) line 54 → `9F02 000000002000, 9F03 000000000500…mount 500) (61 chars)`<br>Expected: 9F02 000000002000, 9F03 000000000500, 9C 09 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002913 (1 purchase) | [TD-TC-002913.json](../interlink_debit/test_data/TD-TC-002913.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-000025 | [TD-TC-000025.json](../5-11-out/interlink_debit/test_data/TD-TC-000025.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 2 | TC-002914 (1 purchase) | [TD-TC-002914.json](../interlink_debit/test_data/TD-TC-002914.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-000026 | [TD-TC-000026.json](../5-11-out/interlink_debit/test_data/TD-TC-000026.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 3 | TC-002943 (1 purchase) | [TD-TC-002943.json](../interlink_debit/test_data/TD-TC-002943.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-000055 | [TD-TC-000055.json](../5-11-out/interlink_debit/test_data/TD-TC-000055.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 4 | TC-002944 (1 purchase) | [TD-TC-002944.json](../interlink_debit/test_data/TD-TC-002944.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-000056 | [TD-TC-000056.json](../5-11-out/interlink_debit/test_data/TD-TC-000056.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |

---

<a id="d-19"></a>

### D-19 – Totals Request: Card Label (Element 13) is not 4 characters – FIXED

| | |
|---|---|
| Field | Element 13 Card Label (Totals Request) (source ID = CD-10) |
| Previous run | 63 test cases / 63 files failing (log: 63 / 63) |
| Previously failing files in the new run | 48 fixed, 6 changed, 9 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `'DB  '` (48 files); `no card-type category is sent any more (nothing occurred…card type) (75 chars)` (6 files) |
| Notes | no card-type category is sent any more (nothing occurred for the card type) (6) |
| Not in the new catalog | 9 files of test cases that were removed: Credential on File (9) |
| Check it yourself | Previous: [TD-TC-002893.step2.totals.json](../interlink_debit/test_data/TD-TC-002893.step2.totals.json#L20) line 20 → `'DB'`. New (TC-000007): [TD-TC-000007.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step2.totals.json#L20) line 20 → `'DB  '`<br>Expected: 'DB  ' |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002894 (3 totals) | [TD-TC-002894.step3.totals.json](../interlink_debit/test_data/TD-TC-002894.step3.totals.json#L20) line 20 | `'DB'` | TC-000008 | [TD-TC-000008.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 2 | TC-002924 (3 totals) | [TD-TC-002924.step3.totals.json](../interlink_debit/test_data/TD-TC-002924.step3.totals.json#L20) line 20 | `'DB'` | TC-000038 | [TD-TC-000038.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000038.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 3 | TC-002953 (3 totals) | [TD-TC-002953.step3.totals.json](../interlink_debit/test_data/TD-TC-002953.step3.totals.json#L20) line 20 | `'DB'` | TC-000067 | [TD-TC-000067.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000067.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 4 | TC-002983 (3 totals) | [TD-TC-002983.step3.totals.json](../interlink_debit/test_data/TD-TC-002983.step3.totals.json#L20) line 20 | `'DB'` | TC-000097 | [TD-TC-000097.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000097.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 5 | TC-003012 (3 totals) | [TD-TC-003012.step3.totals.json](../interlink_debit/test_data/TD-TC-003012.step3.totals.json#L20) line 20 | `'DB'` | TC-000126 | [TD-TC-000126.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000126.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 6 | TC-003071 (3 totals) | [TD-TC-003071.step3.totals.json](../interlink_debit/test_data/TD-TC-003071.step3.totals.json#L20) line 20 | `'DB'` | TC-000156 | [TD-TC-000156.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000156.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 7 | TC-002893 (2 totals) | [TD-TC-002893.step2.totals.json](../interlink_debit/test_data/TD-TC-002893.step2.totals.json#L20) line 20 | `'DB'` | TC-000007 | [TD-TC-000007.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 8 | TC-002895 (3 totals) | [TD-TC-002895.step3.totals.json](../interlink_debit/test_data/TD-TC-002895.step3.totals.json#L20) line 20 | `'DB'` | TC-000009 | [TD-TC-000009.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 9 | TC-002896 (3 totals) | [TD-TC-002896.step3.totals.json](../interlink_debit/test_data/TD-TC-002896.step3.totals.json#L20) line 20 | `'DB'` | TC-000010 | [TD-TC-000010.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 10 | TC-002897 (2 totals) | [TD-TC-002897.step2.totals.json](../interlink_debit/test_data/TD-TC-002897.step2.totals.json#L20) line 20 | `'DB'` | TC-000011 | [TD-TC-000011.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 11 | TC-002898 (2 totals) | [TD-TC-002898.step2.totals.json](../interlink_debit/test_data/TD-TC-002898.step2.totals.json#L20) line 20 | `'DB'` | TC-000012 | [TD-TC-000012.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 12 | TC-002906 (2 totals) | [TD-TC-002906.step2.totals.json](../interlink_debit/test_data/TD-TC-002906.step2.totals.json#L20) line 20 | `'DB'` | TC-000020 | [TD-TC-000020.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000020.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 13 | TC-002907 (2 totals) | [TD-TC-002907.step2.totals.json](../interlink_debit/test_data/TD-TC-002907.step2.totals.json#L20) line 20 | `'DB'` | TC-000021 | [TD-TC-000021.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000021.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 14 | TC-002923 (2 totals) | [TD-TC-002923.step2.totals.json](../interlink_debit/test_data/TD-TC-002923.step2.totals.json#L20) line 20 | `'DB'` | TC-000037 | [TD-TC-000037.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000037.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 15 | TC-002925 (3 totals) | [TD-TC-002925.step3.totals.json](../interlink_debit/test_data/TD-TC-002925.step3.totals.json#L20) line 20 | `'DB'` | TC-000039 | [TD-TC-000039.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000039.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 16 | TC-002926 (3 totals) | [TD-TC-002926.step3.totals.json](../interlink_debit/test_data/TD-TC-002926.step3.totals.json#L20) line 20 | `'DB'` | TC-000040 | [TD-TC-000040.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000040.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 17 | TC-002927 (2 totals) | [TD-TC-002927.step2.totals.json](../interlink_debit/test_data/TD-TC-002927.step2.totals.json#L20) line 20 | `'DB'` | TC-000041 | [TD-TC-000041.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000041.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 18 | TC-002928 (2 totals) | [TD-TC-002928.step2.totals.json](../interlink_debit/test_data/TD-TC-002928.step2.totals.json#L20) line 20 | `'DB'` | TC-000042 | [TD-TC-000042.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000042.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 19 | TC-002936 (2 totals) | [TD-TC-002936.step2.totals.json](../interlink_debit/test_data/TD-TC-002936.step2.totals.json#L20) line 20 | `'DB'` | TC-000050 | [TD-TC-000050.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000050.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 20 | TC-002937 (2 totals) | [TD-TC-002937.step2.totals.json](../interlink_debit/test_data/TD-TC-002937.step2.totals.json#L20) line 20 | `'DB'` | TC-000051 | [TD-TC-000051.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000051.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 21 | TC-002952 (2 totals) | [TD-TC-002952.step2.totals.json](../interlink_debit/test_data/TD-TC-002952.step2.totals.json#L20) line 20 | `'DB'` | TC-000066 | [TD-TC-000066.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000066.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 22 | TC-002954 (3 totals) | [TD-TC-002954.step3.totals.json](../interlink_debit/test_data/TD-TC-002954.step3.totals.json#L20) line 20 | `'DB'` | TC-000068 | [TD-TC-000068.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000068.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 23 | TC-002955 (3 totals) | [TD-TC-002955.step3.totals.json](../interlink_debit/test_data/TD-TC-002955.step3.totals.json#L20) line 20 | `'DB'` | TC-000069 | [TD-TC-000069.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000069.step3.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 24 | TC-002956 (2 totals) | [TD-TC-002956.step2.totals.json](../interlink_debit/test_data/TD-TC-002956.step2.totals.json#L20) line 20 | `'DB'` | TC-000070 | [TD-TC-000070.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000070.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |
| 25 | TC-002957 (2 totals) | [TD-TC-002957.step2.totals.json](../interlink_debit/test_data/TD-TC-002957.step2.totals.json#L20) line 20 | `'DB'` | TC-000071 | [TD-TC-000071.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000071.step2.totals.json#L20) line 20 | `'DB  '` | FIXED |

… 38 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-20"></a>

### D-20 – Totals Request: Segment 105 length is counted with the wrong layout – FIXED

| | |
|---|---|
| Field | Element 84 Segment Length (Totals Request) |
| Previous run | 63 test cases / 63 files failing (log: 63 / 63) |
| Previously failing files in the new run | 54 fixed, 9 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `107 (Card Label 'DB  ')` (48 files); `090 (no card-type category)` (6 files) |
| Not in the new catalog | 9 files of test cases that were removed: Credential on File (9) |
| Check it yourself | Previous: [TD-TC-002893.step2.totals.json](../interlink_debit/test_data/TD-TC-002893.step2.totals.json#L7) line 7 → `108 (Card Label 'DB')`. New (TC-000007): [TD-TC-000007.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step2.totals.json#L7) line 7 → `107 (Card Label 'DB  ')`<br>Expected: 107 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002893 (2 totals) | [TD-TC-002893.step2.totals.json](../interlink_debit/test_data/TD-TC-002893.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000007 | [TD-TC-000007.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000007.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 2 | TC-002894 (3 totals) | [TD-TC-002894.step3.totals.json](../interlink_debit/test_data/TD-TC-002894.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000008 | [TD-TC-000008.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000008.step3.totals.json#L7) line 7 | `090 (no card-type category)` | FIXED |
| 3 | TC-002895 (3 totals) | [TD-TC-002895.step3.totals.json](../interlink_debit/test_data/TD-TC-002895.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000009 | [TD-TC-000009.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000009.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 4 | TC-002896 (3 totals) | [TD-TC-002896.step3.totals.json](../interlink_debit/test_data/TD-TC-002896.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000010 | [TD-TC-000010.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 5 | TC-002897 (2 totals) | [TD-TC-002897.step2.totals.json](../interlink_debit/test_data/TD-TC-002897.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000011 | [TD-TC-000011.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000011.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 6 | TC-002898 (2 totals) | [TD-TC-002898.step2.totals.json](../interlink_debit/test_data/TD-TC-002898.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000012 | [TD-TC-000012.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000012.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 7 | TC-002906 (2 totals) | [TD-TC-002906.step2.totals.json](../interlink_debit/test_data/TD-TC-002906.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000020 | [TD-TC-000020.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000020.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 8 | TC-002907 (2 totals) | [TD-TC-002907.step2.totals.json](../interlink_debit/test_data/TD-TC-002907.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000021 | [TD-TC-000021.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000021.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 9 | TC-002923 (2 totals) | [TD-TC-002923.step2.totals.json](../interlink_debit/test_data/TD-TC-002923.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000037 | [TD-TC-000037.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000037.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 10 | TC-002924 (3 totals) | [TD-TC-002924.step3.totals.json](../interlink_debit/test_data/TD-TC-002924.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000038 | [TD-TC-000038.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000038.step3.totals.json#L7) line 7 | `090 (no card-type category)` | FIXED |
| 11 | TC-002925 (3 totals) | [TD-TC-002925.step3.totals.json](../interlink_debit/test_data/TD-TC-002925.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000039 | [TD-TC-000039.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000039.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 12 | TC-002926 (3 totals) | [TD-TC-002926.step3.totals.json](../interlink_debit/test_data/TD-TC-002926.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000040 | [TD-TC-000040.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000040.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 13 | TC-002927 (2 totals) | [TD-TC-002927.step2.totals.json](../interlink_debit/test_data/TD-TC-002927.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000041 | [TD-TC-000041.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000041.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 14 | TC-002928 (2 totals) | [TD-TC-002928.step2.totals.json](../interlink_debit/test_data/TD-TC-002928.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000042 | [TD-TC-000042.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000042.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 15 | TC-002936 (2 totals) | [TD-TC-002936.step2.totals.json](../interlink_debit/test_data/TD-TC-002936.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000050 | [TD-TC-000050.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000050.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 16 | TC-002937 (2 totals) | [TD-TC-002937.step2.totals.json](../interlink_debit/test_data/TD-TC-002937.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000051 | [TD-TC-000051.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000051.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 17 | TC-002952 (2 totals) | [TD-TC-002952.step2.totals.json](../interlink_debit/test_data/TD-TC-002952.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000066 | [TD-TC-000066.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000066.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 18 | TC-002953 (3 totals) | [TD-TC-002953.step3.totals.json](../interlink_debit/test_data/TD-TC-002953.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000067 | [TD-TC-000067.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000067.step3.totals.json#L7) line 7 | `090 (no card-type category)` | FIXED |
| 19 | TC-002954 (3 totals) | [TD-TC-002954.step3.totals.json](../interlink_debit/test_data/TD-TC-002954.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000068 | [TD-TC-000068.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000068.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 20 | TC-002955 (3 totals) | [TD-TC-002955.step3.totals.json](../interlink_debit/test_data/TD-TC-002955.step3.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000069 | [TD-TC-000069.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000069.step3.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 21 | TC-002956 (2 totals) | [TD-TC-002956.step2.totals.json](../interlink_debit/test_data/TD-TC-002956.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000070 | [TD-TC-000070.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000070.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 22 | TC-002957 (2 totals) | [TD-TC-002957.step2.totals.json](../interlink_debit/test_data/TD-TC-002957.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000071 | [TD-TC-000071.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000071.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 23 | TC-002965 (2 totals) | [TD-TC-002965.step2.totals.json](../interlink_debit/test_data/TD-TC-002965.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000079 | [TD-TC-000079.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000079.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 24 | TC-002966 (2 totals) | [TD-TC-002966.step2.totals.json](../interlink_debit/test_data/TD-TC-002966.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000080 | [TD-TC-000080.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000080.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |
| 25 | TC-002982 (2 totals) | [TD-TC-002982.step2.totals.json](../interlink_debit/test_data/TD-TC-002982.step2.totals.json#L7) line 7 | `108 (Card Label 'DB')` | TC-000096 | [TD-TC-000096.step2.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000096.step2.totals.json#L7) line 7 | `107 (Card Label 'DB  ')` | FIXED |

… 38 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="d-21"></a>

### D-21 – Totals Request: the totals do not match the transactions of the flow – NOT FIXED

| | |
|---|---|
| Field | Elements 16, 15, 42 Card Type Total Count, Card Type Total Amount, Grand Total |
| Previous run | 14 test cases / 14 files failing (log: 14 / 14) |
| Previously failing files in the new run | 12 not fixed, 2 removed |
| New run, all test cases | 12 test cases / 12 files still failing |
| Not in the new catalog | 2 files of test cases that were removed: Credential on File (2) |
| Check it yourself | Previous: [TD-TC-002896.step3.totals.json](../interlink_debit/test_data/TD-TC-002896.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`. New (TC-000010): [TD-TC-000010.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`<br>Expected: Leave out the DB bucket and send Grand Total 00000000 |

#### Explained step by step (new run)

**TC-000010 – T8-PUR-TOR-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 10

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-000010.step1.purchase.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step1.purchase.json#L19) line 19 | purchase (Transaction Type `0`) | `1500` = $15.00 | no response (time-out) | no response (timed out), so not in the totals |
| 2 | [TD-TC-000010.step2.timeout-reversal.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step2.timeout-reversal.json#L19) line 19 | timeout reversal (Transaction Type `Z`) | `1500` = $15.00 | Response Code `0` | its original was never approved, so nothing to take out |

So the DB bucket holds **count 0, amount $0.00** – nothing occurred for the card type, so Fields 15–17 are not sent (p.237).

Totals Request [TD-TC-000010.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'DB  '` | `not sent` | **WRONG** |
| Card Type Total Count (Element 16) | 21 | `00001` | `not sent` | **WRONG** |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `not sent` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00000000` | OK |
| Segment Length (Element 84) | 7 | `107` | `090` | **WRONG** |

The test case itself says for this step: *"the timed-out purchase is not in the totals"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 10 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

**TC-000180 – T8-AUT-COMPLETION-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 180

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-000180.step1.auth-only.json](../5-11-out/interlink_debit/test_data/TD-TC-000180.step1.auth-only.json#L19) line 19 | auth only (Transaction Type `3`) | `1500` = $15.00 | Response Code `2` | Authorization Only - AO bucket, not in the grand total |
| 2 | [TD-TC-000180.step2.completion.json](../5-11-out/interlink_debit/test_data/TD-TC-000180.step2.completion.json#L19) line 19 | completion (Transaction Type `0`) | `1500` = $15.00 | Response Code `0` | count +1, amount +$15.00 |

So the DB bucket holds **count 1, amount $15.00**.

Totals Request [TD-TC-000180.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000180.step3.totals.json):

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
| 1 | TC-002896 (3 totals) | [TD-TC-002896.step3.totals.json](../interlink_debit/test_data/TD-TC-002896.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000010 | [TD-TC-000010.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 2 | TC-002926 (3 totals) | [TD-TC-002926.step3.totals.json](../interlink_debit/test_data/TD-TC-002926.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000040 | [TD-TC-000040.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000040.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 3 | TC-002955 (3 totals) | [TD-TC-002955.step3.totals.json](../interlink_debit/test_data/TD-TC-002955.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000069 | [TD-TC-000069.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000069.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 4 | TC-002985 (3 totals) | [TD-TC-002985.step3.totals.json](../interlink_debit/test_data/TD-TC-002985.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000099 | [TD-TC-000099.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000099.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 5 | TC-003014 (3 totals) | [TD-TC-003014.step3.totals.json](../interlink_debit/test_data/TD-TC-003014.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000128 | [TD-TC-000128.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000128.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 6 | TC-003073 (3 totals) | [TD-TC-003073.step3.totals.json](../interlink_debit/test_data/TD-TC-003073.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000158 | [TD-TC-000158.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000158.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 7 | TC-003095 (3 totals) | [TD-TC-003095.step3.totals.json](../interlink_debit/test_data/TD-TC-003095.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000180 | [TD-TC-000180.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000180.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 8 | TC-003103 (3 totals) | [TD-TC-003103.step3.totals.json](../interlink_debit/test_data/TD-TC-003103.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000190 | [TD-TC-000190.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000190.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 9 | TC-003110 (3 totals) | [TD-TC-003110.step3.totals.json](../interlink_debit/test_data/TD-TC-003110.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000199 | [TD-TC-000199.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000199.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 10 | TC-003117 (3 totals) | [TD-TC-003117.step3.totals.json](../interlink_debit/test_data/TD-TC-003117.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000208 | [TD-TC-000208.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000208.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 11 | TC-003124 (3 totals) | [TD-TC-003124.step3.totals.json](../interlink_debit/test_data/TD-TC-003124.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000217 | [TD-TC-000217.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000217.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 12 | TC-003139 (3 totals) | [TD-TC-003139.step3.totals.json](../interlink_debit/test_data/TD-TC-003139.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | TC-000227 | [TD-TC-000227.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000227.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | NOT FIXED |
| 13 | TC-003044 (3 totals) | [TD-TC-003044.step3.totals.json](../interlink_debit/test_data/TD-TC-003044.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 14 | TC-003132 (3 totals) | [TD-TC-003132.step3.totals.json](../interlink_debit/test_data/TD-TC-003132.step3.totals.json#L21) line 21 | `count 00001, amount 00000000, grand total 00000000` | – | – | template / entry mode no longer in the catalog | REMOVED |

---

<a id="d-24"></a>

### D-24 – A test data value differs from the test case it was generated from – FIXED

| | |
|---|---|
| Field | Any field – test data value vs test case request value |
| Previous run | 14 test cases / 14 files failing (log: 14 / 14) |
| Previously failing files in the new run | 12 fixed, 2 removed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `000203 (test case: 000203)` (1 file); `000213 (test case: 000213)` (1 file); `000503 (test case: 000503)` (1 file) |
| Not in the new catalog | 2 files of test cases that were removed: Credential on File (2) |
| Check it yourself | Previous: [TD-TC-002906.step3.return.json](../interlink_debit/test_data/TD-TC-002906.step3.return.json#L22) line 22 → `029062 (test case: 029063)`. New (TC-000020): [TD-TC-000020.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000020.step3.return.json#L22) line 22 → `000203 (test case: 000203)`<br>Expected: test data value = test case value |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002906 (3 return) | [TD-TC-002906.step3.return.json](../interlink_debit/test_data/TD-TC-002906.step3.return.json#L22) line 22 | `029062 (test case: 029063)` | TC-000020 | [TD-TC-000020.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000020.step3.return.json#L22) line 22 | `000203 (test case: 000203)` | FIXED |
| 2 | TC-002907 (3 return) | [TD-TC-002907.step3.return.json](../interlink_debit/test_data/TD-TC-002907.step3.return.json#L22) line 22 | `029072 (test case: 029073)` | TC-000021 | [TD-TC-000021.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000021.step3.return.json#L22) line 22 | `000213 (test case: 000213)` | FIXED |
| 3 | TC-002936 (3 return) | [TD-TC-002936.step3.return.json](../interlink_debit/test_data/TD-TC-002936.step3.return.json#L22) line 22 | `029362 (test case: 029363)` | TC-000050 | [TD-TC-000050.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000050.step3.return.json#L22) line 22 | `000503 (test case: 000503)` | FIXED |
| 4 | TC-002937 (3 return) | [TD-TC-002937.step3.return.json](../interlink_debit/test_data/TD-TC-002937.step3.return.json#L22) line 22 | `029372 (test case: 029373)` | TC-000051 | [TD-TC-000051.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000051.step3.return.json#L22) line 22 | `000513 (test case: 000513)` | FIXED |
| 5 | TC-002965 (3 return) | [TD-TC-002965.step3.return.json](../interlink_debit/test_data/TD-TC-002965.step3.return.json#L22) line 22 | `029652 (test case: 029653)` | TC-000079 | [TD-TC-000079.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000079.step3.return.json#L22) line 22 | `000793 (test case: 000793)` | FIXED |
| 6 | TC-002966 (3 return) | [TD-TC-002966.step3.return.json](../interlink_debit/test_data/TD-TC-002966.step3.return.json#L22) line 22 | `029662 (test case: 029663)` | TC-000080 | [TD-TC-000080.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000080.step3.return.json#L22) line 22 | `000803 (test case: 000803)` | FIXED |
| 7 | TC-002995 (3 return) | [TD-TC-002995.step3.return.json](../interlink_debit/test_data/TD-TC-002995.step3.return.json#L22) line 22 | `029952 (test case: 029953)` | TC-000109 | [TD-TC-000109.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000109.step3.return.json#L22) line 22 | `001093 (test case: 001093)` | FIXED |
| 8 | TC-002996 (3 return) | [TD-TC-002996.step3.return.json](../interlink_debit/test_data/TD-TC-002996.step3.return.json#L22) line 22 | `029962 (test case: 029963)` | TC-000110 | [TD-TC-000110.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000110.step3.return.json#L22) line 22 | `001103 (test case: 001103)` | FIXED |
| 9 | TC-003025 (3 return) | [TD-TC-003025.step3.return.json](../interlink_debit/test_data/TD-TC-003025.step3.return.json#L22) line 22 | `030252 (test case: 030253)` | TC-000139 | [TD-TC-000139.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000139.step3.return.json#L22) line 22 | `001393 (test case: 001393)` | FIXED |
| 10 | TC-003026 (3 return) | [TD-TC-003026.step3.return.json](../interlink_debit/test_data/TD-TC-003026.step3.return.json#L22) line 22 | `030262 (test case: 030263)` | TC-000140 | [TD-TC-000140.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000140.step3.return.json#L22) line 22 | `001403 (test case: 001403)` | FIXED |
| 11 | TC-003083 (3 return) | [TD-TC-003083.step3.return.json](../interlink_debit/test_data/TD-TC-003083.step3.return.json#L22) line 22 | `030832 (test case: 030833)` | TC-000168 | [TD-TC-000168.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000168.step3.return.json#L22) line 22 | `001683 (test case: 001683)` | FIXED |
| 12 | TC-003084 (3 return) | [TD-TC-003084.step3.return.json](../interlink_debit/test_data/TD-TC-003084.step3.return.json#L22) line 22 | `030842 (test case: 030843)` | TC-000169 | [TD-TC-000169.step3.return.json](../5-11-out/interlink_debit/test_data/TD-TC-000169.step3.return.json#L22) line 22 | `001693 (test case: 001693)` | FIXED |
| 13 | TC-003054 (3 return) | [TD-TC-003054.step3.return.json](../interlink_debit/test_data/TD-TC-003054.step3.return.json#L22) line 22 | `030542 (test case: 030543)` | – | – | template / entry mode no longer in the catalog | REMOVED |
| 14 | TC-003055 (3 return) | [TD-TC-003055.step3.return.json](../interlink_debit/test_data/TD-TC-003055.step3.return.json#L22) line 22 | `030552 (test case: 030553)` | – | – | template / entry mode no longer in the catalog | REMOVED |

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

Check it yourself: open `5-11-out/interlink_debit/reports/traceability_matrix_full.json` and search `"test_data_file"` – the paths now start with `test_data/` (previously `qe_shaped_test_data/`, a folder that was never delivered), and every leg file of a flow is listed.

---

Re-run (from `regression-artifact-validator`): `python scripts/verify-defect-fixes.py`

