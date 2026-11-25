# MasterCard Credit – defect fix verification (previous run 31-10-out → new run 5-11-out)

| Item | Value |
|---|---|
| Previous run | `C:/Users/F7OKAGW/Downloads/31-10-out/mastercard_credit` – 450 test cases, 839 test data files (identical copy in the workspace: `MasterCard Credit/mastercard_credit/test_data`) |
| New run | `5-11-out/mastercard_credit` – 488 test cases, 961 test data files |
| Defect log(s) | `Defect MD file/` Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) and MasterCard_Credit_Consolidated_Defect_Report.md (D-xx) |
| Test case matching | IDs are renumbered in the new run; test cases are matched by transaction category + entry mode + template (unique in both runs), flow steps by step number and role. 418 of 450 previous test cases have a match; 32 were removed, 70 are new |

Status: **FIXED** – every previously failing file now passes the rule · **FIXED – value awaiting client input** – the generator now builds the field correctly, but its value can only come from the client (listed in the test case's `awaiting_inputs`, status `needs_client_input`) · **PARTIALLY FIXED** – some files pass, some still fail · **NOT FIXED** – still failing · **CLOSED – test cases removed** – the affected test cases are no longer in the catalog.

## 1. Summary

| Defect | Source ID | What was wrong | Previous run: TCs / files (logged) | New run: TCs / files still failing | Status |
|---|---|---|---|---|---|
| [CD-01](#cd-01) | D-01 | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | 104 / 126 (104 / 126) | 0 / 0 | **FIXED** |
| [CD-02](#cd-02) | D-02 | EMV Additional Information (Element 118) is not EMVYES / EMVNOT | 104 / 126 (104 / 126) | 0 / 0 | **FIXED** |
| [CD-03](#cd-03) | D-12 | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | 158 / 223 (158 / 223) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-04](#cd-04) | D-09 | Credential-on-File indicators (Tables 032, 049, 056) missing | 54 / 97 (54 / 97) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-05](#cd-05) | D-08 | Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data | 47 / 57 (47 / 57) | 0 / 0 | **FIXED** |
| [CD-06](#cd-06) | D-13 | Unmatched time-out reversal carries full track data | 5 / 5 (5 / 5) | 0 / 0 | **FIXED** |
| [CD-07](#cd-07) | D-05, D-14 | Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it | 31 / 31 (31 / 31) | 0 / 0 | **FIXED** |
| [CD-08](#cd-08) | D-05 | Split-tender card B chip amount (Tag 9F02) is the full original amount | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| [CD-09](#cd-09) | S2 D-08 | Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 | 6 / 6 (6 / 6) | 0 / 0 | **FIXED** |
| [CD-10](#cd-10) | S2 D-06 | Totals Card Label (Element 13) is 2 bytes instead of 4 | 72 / 72 (72 / 72) | 0 / 0 | **FIXED** |
| [CD-11](#cd-11) | S2 D-10 | Totals card-type category sent with count 00000 | 8 / 8 (8 / 8) | 0 / 0 | **FIXED** |
| [CD-12](#cd-12) | D-03 | Expired-card test uses a card that is not expired | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-13](#cd-13) | D-04 | Online-PIN test has no PIN block and a signature CVM | 4 / 4 (4 / 4) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-14](#cd-14) | D-11 | Offline-PIN test has a signature CVM | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-15](#cd-15) | S2 D-09 | Cashback test: chip data ignores the cash amount | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-16](#cd-16) | D-07 | 'Track 1' test carries Track 2 data | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| [CD-17](#cd-17) | D-06 | Terminal described two ways: Table 005 capability 8 with Table 031 12 | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-18](#cd-18) | D-10 | AVS test sends no address data (Table 003) | 8 / 8 (8 / 8) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-19](#cd-19) | S2 O31-01 | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 408 trace rows with a missing path; 431 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |
| [CD-20](#cd-20) | S2 O31-03 | MC Credit package mixes two builds | 450 TCs + archive with 241 TCs | 488 TCs, one build, MANIFEST | **FIXED** |

## 2. Defect details

<a id="cd-01"></a>

### CD-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 – FIXED

| | |
|---|---|
| Field | E188, Seg 130 (source ID D-01) |
| Previous run | 104 test cases / 126 files failing (log: 104 / 126) |
| Previously failing files in the new run | 124 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `001` (124 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: [TD-TC-001722.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001722.json#L47) line 47 → `01`. New (TC-000258): [TD-TC-000258.json](../5-11-out/mastercard_credit/test_data/TD-TC-000258.json#L52) line 52 → `001`<br>Expected: 001 (Tag 5F34 zero-padded to 3) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001744 (1 timeout reversal) | [TD-TC-001744.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001744.json#L47) line 47 | `01` | TC-000280 | [TD-TC-000280.json](../5-11-out/mastercard_credit/test_data/TD-TC-000280.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-001781 (1 timeout reversal) | [TD-TC-001781.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001781.json#L47) line 47 | `01` | TC-000316 | [TD-TC-000316.json](../5-11-out/mastercard_credit/test_data/TD-TC-000316.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-001722 (1 purchase) | [TD-TC-001722.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001722.json#L47) line 47 | `01` | TC-000258 | [TD-TC-000258.json](../5-11-out/mastercard_credit/test_data/TD-TC-000258.json#L52) line 52 | `001` | FIXED |
| 4 | TC-001723 (1 purchase) | [TD-TC-001723.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001723.json#L47) line 47 | `01` | TC-000259 | [TD-TC-000259.json](../5-11-out/mastercard_credit/test_data/TD-TC-000259.json#L52) line 52 | `001` | FIXED |
| 5 | TC-001724 (1 purchase) | [TD-TC-001724.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001724.json#L47) line 47 | `01` | TC-000260 | [TD-TC-000260.json](../5-11-out/mastercard_credit/test_data/TD-TC-000260.json#L52) line 52 | `001` | FIXED |
| 6 | TC-001725 (1 purchase) | [TD-TC-001725.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001725.json#L47) line 47 | `01` | TC-000261 | [TD-TC-000261.json](../5-11-out/mastercard_credit/test_data/TD-TC-000261.json#L52) line 52 | `001` | FIXED |
| 7 | TC-001726 (1 purchase) | [TD-TC-001726.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step1.purchase.json#L47) line 47 | `01` | TC-000262 | [TD-TC-000262.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 8 | TC-001726 (2 split tender) | [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L47) line 47 | `01` | TC-000262 | [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L52) line 52 | `001` | FIXED |
| 9 | TC-001727 (1 purchase) | [TD-TC-001727.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001727.step1.purchase.json#L47) line 47 | `01` | TC-000263 | [TD-TC-000263.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000263.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 10 | TC-001728 (1 purchase) | [TD-TC-001728.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001728.step1.purchase.json#L47) line 47 | `01` | TC-000264 | [TD-TC-000264.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000264.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 11 | TC-001729 (1 purchase) | [TD-TC-001729.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001729.json#L47) line 47 | `01` | TC-000265 | [TD-TC-000265.json](../5-11-out/mastercard_credit/test_data/TD-TC-000265.json#L52) line 52 | `001` | FIXED |
| 12 | TC-001730 (1 purchase) | [TD-TC-001730.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001730.step1.purchase.json#L47) line 47 | `01` | TC-000266 | [TD-TC-000266.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000266.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 13 | TC-001731 (1 purchase) | [TD-TC-001731.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001731.step1.purchase.json#L47) line 47 | `01` | TC-000267 | [TD-TC-000267.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000267.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 14 | TC-001732 (1 purchase) | [TD-TC-001732.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001732.step1.purchase.json#L47) line 47 | `01` | TC-000268 | [TD-TC-000268.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000268.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 15 | TC-001733 (1 purchase) | [TD-TC-001733.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001733.step1.purchase.json#L47) line 47 | `01` | TC-000269 | [TD-TC-000269.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000269.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 16 | TC-001734 (1 purchase) | [TD-TC-001734.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001734.step1.purchase.json#L47) line 47 | `01` | TC-000270 | [TD-TC-000270.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000270.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 17 | TC-001734 (3 next transaction) | [TD-TC-001734.step3.next-transaction.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001734.step3.next-transaction.json#L47) line 47 | `01` | TC-000270 | [TD-TC-000270.step3.next-transaction.json](../5-11-out/mastercard_credit/test_data/TD-TC-000270.step3.next-transaction.json#L52) line 52 | `001` | FIXED |
| 18 | TC-001735 (1 purchase) | [TD-TC-001735.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001735.step1.purchase.json#L47) line 47 | `01` | TC-000271 | [TD-TC-000271.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000271.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 19 | TC-001736 (1 purchase) | [TD-TC-001736.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001736.step1.purchase.json#L47) line 47 | `01` | TC-000272 | [TD-TC-000272.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000272.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 20 | TC-001737 (1 purchase) | [TD-TC-001737.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001737.step1.purchase.json#L47) line 47 | `01` | TC-000273 | [TD-TC-000273.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000273.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 21 | TC-001738 (1 purchase) | [TD-TC-001738.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step1.purchase.json#L47) line 47 | `01` | TC-000274 | [TD-TC-000274.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 22 | TC-001738 (2 return) | [TD-TC-001738.step2.return.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step2.return.json#L47) line 47 | `01` | TC-000274 | [TD-TC-000274.step2.return.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step2.return.json#L52) line 52 | `001` | FIXED |
| 23 | TC-001739 (1 purchase) | [TD-TC-001739.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001739.step1.purchase.json#L47) line 47 | `01` | TC-000275 | [TD-TC-000275.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 24 | TC-001740 (1 purchase) | [TD-TC-001740.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001740.step1.purchase.json#L47) line 47 | `01` | TC-000276 | [TD-TC-000276.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000276.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 25 | TC-001741 (1 purchase) | [TD-TC-001741.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001741.step1.purchase.json#L47) line 47 | `01` | TC-000277 | [TD-TC-000277.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000277.step1.purchase.json#L52) line 52 | `001` | FIXED |

… 101 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-02"></a>

### CD-02 – EMV Additional Information (Element 118) is not EMVYES / EMVNOT – FIXED

| | |
|---|---|
| Field | E191/E192/E118, Seg 130 (source ID D-02) |
| Previous run | 104 test cases / 126 files failing (log: 104 / 126) |
| Previously failing files in the new run | 124 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `EMVYES / length 006` (124 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: [TD-TC-001722.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001722.json#L52) line 52 → `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 cha…length 984 (52 chars)`. New (TC-000258): [TD-TC-000258.json](../5-11-out/mastercard_credit/test_data/TD-TC-000258.json#L57) line 57 → `EMVYES / length 006`<br>Expected: EMVYES or EMVNOT / length 006 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001744 (1 timeout reversal) | [TD-TC-001744.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001744.json#L52) line 52 | `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 chars) / length 984` | TC-000280 | [TD-TC-000280.json](../5-11-out/mastercard_credit/test_data/TD-TC-000280.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-001781 (1 timeout reversal) | [TD-TC-001781.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001781.json#L52) line 52 | `EAMHC50ODTH426QT…RLY6GAJQCX (984 chars) / length 984` | TC-000316 | [TD-TC-000316.json](../5-11-out/mastercard_credit/test_data/TD-TC-000316.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-001722 (1 purchase) | [TD-TC-001722.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001722.json#L52) line 52 | `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 chars) / length 984` | TC-000258 | [TD-TC-000258.json](../5-11-out/mastercard_credit/test_data/TD-TC-000258.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 4 | TC-001723 (1 purchase) | [TD-TC-001723.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001723.json#L52) line 52 | `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 chars) / length 984` | TC-000259 | [TD-TC-000259.json](../5-11-out/mastercard_credit/test_data/TD-TC-000259.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 5 | TC-001724 (1 purchase) | [TD-TC-001724.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001724.json#L52) line 52 | `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 chars) / length 984` | TC-000260 | [TD-TC-000260.json](../5-11-out/mastercard_credit/test_data/TD-TC-000260.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 6 | TC-001725 (1 purchase) | [TD-TC-001725.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001725.json#L52) line 52 | `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 chars) / length 984` | TC-000261 | [TD-TC-000261.json](../5-11-out/mastercard_credit/test_data/TD-TC-000261.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 7 | TC-001726 (1 purchase) | [TD-TC-001726.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step1.purchase.json#L52) line 52 | `1J0FVRBA9PUYLZQW…EIB6MIFT99 (984 chars) / length 984` | TC-000262 | [TD-TC-000262.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 8 | TC-001726 (2 split tender) | [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L52) line 52 | `SSWN9VUQDGPTG869…H3AT9QRD5X (984 chars) / length 984` | TC-000262 | [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 9 | TC-001727 (1 purchase) | [TD-TC-001727.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001727.step1.purchase.json#L52) line 52 | `LN1MLBBYVZDJ06X4…1SWV1QMNXU (984 chars) / length 984` | TC-000263 | [TD-TC-000263.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000263.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 10 | TC-001728 (1 purchase) | [TD-TC-001728.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001728.step1.purchase.json#L52) line 52 | `0XER3AU0AQAQ3SBW…N2CC415MLV (984 chars) / length 984` | TC-000264 | [TD-TC-000264.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000264.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 11 | TC-001729 (1 purchase) | [TD-TC-001729.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001729.json#L52) line 52 | `XPXZRQBX814S3CAQ…5QC5K8MMTF (984 chars) / length 984` | TC-000265 | [TD-TC-000265.json](../5-11-out/mastercard_credit/test_data/TD-TC-000265.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 12 | TC-001730 (1 purchase) | [TD-TC-001730.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001730.step1.purchase.json#L52) line 52 | `13ELX9RB06PAQ9EF…KWLHKNM76H (984 chars) / length 984` | TC-000266 | [TD-TC-000266.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000266.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 13 | TC-001731 (1 purchase) | [TD-TC-001731.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001731.step1.purchase.json#L52) line 52 | `UJMIP5GWWUC7MO1Q…Q9TXJH0PVR (984 chars) / length 984` | TC-000267 | [TD-TC-000267.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000267.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 14 | TC-001732 (1 purchase) | [TD-TC-001732.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001732.step1.purchase.json#L52) line 52 | `GXZSPE4YRWLACCQR…7WLSER9DHD (984 chars) / length 984` | TC-000268 | [TD-TC-000268.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000268.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 15 | TC-001733 (1 purchase) | [TD-TC-001733.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001733.step1.purchase.json#L52) line 52 | `WZHL6GZGB0N3X22J…HZCJI4AYOU (984 chars) / length 984` | TC-000269 | [TD-TC-000269.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000269.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 16 | TC-001734 (1 purchase) | [TD-TC-001734.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001734.step1.purchase.json#L52) line 52 | `2PTM6KTCLKLY1F0R…2TYW6U3RX4 (984 chars) / length 984` | TC-000270 | [TD-TC-000270.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000270.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 17 | TC-001734 (3 next transaction) | [TD-TC-001734.step3.next-transaction.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001734.step3.next-transaction.json#L52) line 52 | `DPSI790EB811QBPA…YZ89R9NR2M (984 chars) / length 984` | TC-000270 | [TD-TC-000270.step3.next-transaction.json](../5-11-out/mastercard_credit/test_data/TD-TC-000270.step3.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 18 | TC-001735 (1 purchase) | [TD-TC-001735.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001735.step1.purchase.json#L52) line 52 | `RASAIRSNASXO0AQI…8ZERK9J4X9 (984 chars) / length 984` | TC-000271 | [TD-TC-000271.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000271.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 19 | TC-001736 (1 purchase) | [TD-TC-001736.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001736.step1.purchase.json#L52) line 52 | `MLG7N5ALXUX2GSQ8…L9VFJ2NFQH (984 chars) / length 984` | TC-000272 | [TD-TC-000272.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000272.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 20 | TC-001737 (1 purchase) | [TD-TC-001737.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001737.step1.purchase.json#L52) line 52 | `F5KLARTDCE11RRD4…SAAWOKWBEV (984 chars) / length 984` | TC-000273 | [TD-TC-000273.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000273.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 21 | TC-001738 (1 purchase) | [TD-TC-001738.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step1.purchase.json#L52) line 52 | `70GIMLYEZ9C8GY17…TDMHYNPBMH (984 chars) / length 984` | TC-000274 | [TD-TC-000274.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 22 | TC-001738 (2 return) | [TD-TC-001738.step2.return.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step2.return.json#L52) line 52 | `2US4664ZI573RM2K…352VGDITU1 (984 chars) / length 984` | TC-000274 | [TD-TC-000274.step2.return.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step2.return.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 23 | TC-001739 (1 purchase) | [TD-TC-001739.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001739.step1.purchase.json#L52) line 52 | `BQJ49AA9CJRSA4G4…XI7IRAMCEL (984 chars) / length 984` | TC-000275 | [TD-TC-000275.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 24 | TC-001740 (1 purchase) | [TD-TC-001740.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001740.step1.purchase.json#L52) line 52 | `PS8FQ588A20D1NUT…BN56SBK1FZ (984 chars) / length 984` | TC-000276 | [TD-TC-000276.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000276.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 25 | TC-001741 (1 purchase) | [TD-TC-001741.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001741.step1.purchase.json#L52) line 52 | `SVUS8O5F64M7BHDS…Z4ZRC23IM6 (984 chars) / length 984` | TC-000277 | [TD-TC-000277.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000277.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |

… 101 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-03"></a>

### CD-03 – Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Table 030, Seg 111 (source ID D-12) |
| Previous run | 158 test cases / 223 files failing (log: 158 / 223) |
| Previously failing files in the new run | 221 awaiting client input, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `030 sent, length 010, value empty` (221 files); `Segment 130 is no longer sent on this step (no longer an EMV request)` (2 files) |
| Notes | value listed in awaiting_inputs; length 010 is declared for the value to come, so the file cannot be sent until the code is filled in (221); Segment 130 is no longer sent on this step (no longer an EMV request) (2) |
| Check it yourself | Previous: [TD-TC-001722.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001722.json#L30) line 30 → `not sent (tables: 005=051, 031=12)`. New (TC-000258): [TD-TC-000258.json](../5-11-out/mastercard_credit/test_data/TD-TC-000258.json#L44) line 44 → `030 sent, length 010, value empty`<br>Expected: 10 digits from the client's certified terminal |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001722 (1 purchase) | [TD-TC-001722.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001722.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000258 | [TD-TC-000258.json](../5-11-out/mastercard_credit/test_data/TD-TC-000258.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-001723 (1 purchase) | [TD-TC-001723.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001723.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000259 | [TD-TC-000259.json](../5-11-out/mastercard_credit/test_data/TD-TC-000259.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-001724 (1 purchase) | [TD-TC-001724.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001724.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000260 | [TD-TC-000260.json](../5-11-out/mastercard_credit/test_data/TD-TC-000260.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-001725 (1 purchase) | [TD-TC-001725.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001725.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000261 | [TD-TC-000261.json](../5-11-out/mastercard_credit/test_data/TD-TC-000261.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-001726 (1 purchase) | [TD-TC-001726.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000262 | [TD-TC-000262.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-001726 (2 split tender) | [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000262 | [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-001727 (1 purchase) | [TD-TC-001727.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001727.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000263 | [TD-TC-000263.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000263.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-001728 (1 purchase) | [TD-TC-001728.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001728.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000264 | [TD-TC-000264.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000264.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 9 | TC-001729 (1 purchase) | [TD-TC-001729.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001729.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000265 | [TD-TC-000265.json](../5-11-out/mastercard_credit/test_data/TD-TC-000265.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 10 | TC-001730 (1 purchase) | [TD-TC-001730.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001730.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000266 | [TD-TC-000266.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000266.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 11 | TC-001731 (1 purchase) | [TD-TC-001731.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001731.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000267 | [TD-TC-000267.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000267.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 12 | TC-001732 (1 purchase) | [TD-TC-001732.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001732.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000268 | [TD-TC-000268.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000268.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 13 | TC-001733 (1 purchase) | [TD-TC-001733.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001733.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000269 | [TD-TC-000269.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000269.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 14 | TC-001734 (1 purchase) | [TD-TC-001734.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001734.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000270 | [TD-TC-000270.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000270.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 15 | TC-001734 (3 next transaction) | [TD-TC-001734.step3.next-transaction.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001734.step3.next-transaction.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000270 | [TD-TC-000270.step3.next-transaction.json](../5-11-out/mastercard_credit/test_data/TD-TC-000270.step3.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 16 | TC-001735 (1 purchase) | [TD-TC-001735.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001735.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000271 | [TD-TC-000271.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000271.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 17 | TC-001736 (1 purchase) | [TD-TC-001736.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001736.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000272 | [TD-TC-000272.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000272.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 18 | TC-001737 (1 purchase) | [TD-TC-001737.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001737.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000273 | [TD-TC-000273.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000273.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 19 | TC-001738 (1 purchase) | [TD-TC-001738.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000274 | [TD-TC-000274.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 20 | TC-001738 (2 return) | [TD-TC-001738.step2.return.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step2.return.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000274 | [TD-TC-000274.step2.return.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step2.return.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 21 | TC-001739 (1 purchase) | [TD-TC-001739.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001739.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000275 | [TD-TC-000275.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 22 | TC-001740 (1 purchase) | [TD-TC-001740.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001740.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000276 | [TD-TC-000276.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000276.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 23 | TC-001741 (1 purchase) | [TD-TC-001741.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001741.step1.purchase.json#L30) line 30 | `not sent (tables: 005=051, 031=12)` | TC-000277 | [TD-TC-000277.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000277.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 24 | TC-001742 (1 purchase) | [TD-TC-001742.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001742.json#L41) line 41 | `not sent (tables: 005=051, 031=12, 012=M)` | TC-000278 | [TD-TC-000278.json](../5-11-out/mastercard_credit/test_data/TD-TC-000278.json#L60) line 60 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 25 | TC-001743 (1 purchase) | [TD-TC-001743.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001743.step1.purchase.json#L41) line 41 | `not sent (tables: 005=051, 031=12, 012=M)` | TC-000279 | [TD-TC-000279.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000279.step1.purchase.json#L60) line 60 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |

… 198 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-04"></a>

### CD-04 – Credential-on-File indicators (Tables 032, 049, 056) missing – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Tables 032/049/056, Seg 111 (source ID D-09) |
| Previous run | 54 test cases / 97 files failing (log: 54 / 97) |
| Previously failing files in the new run | 97 awaiting client input |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (awaiting client)` (93 files); `032-08 C/I 08001C; 049-02 021M; 056-09 empty (awaiting client)` (4 files) |
| Check it yourself | Previous: [TD-TC-001941.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001941.json#L30) line 30 → `032-08 C/I missing; 049-02 missing; 056-09 missing`. New (TC-000471): [TD-TC-000471.json](../5-11-out/mastercard_credit/test_data/TD-TC-000471.json#L49) line 49 → `032-08 C/I 08001C; 049-02 021C; 056-…ng client) (62 chars)`<br>Expected: 032-08 = C (I on the storing step), 049-02 = C/M, 056-09 MIT/CIT category |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001941 (1 purchase) | [TD-TC-001941.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001941.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000471 | [TD-TC-000471.json](../5-11-out/mastercard_credit/test_data/TD-TC-000471.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 2 | TC-001942 (1 purchase) | [TD-TC-001942.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001942.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000472 | [TD-TC-000472.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000472.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 3 | TC-001942 (2 reversal) | [TD-TC-001942.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001942.step2.reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000472 | [TD-TC-000472.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000472.step2.reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 4 | TC-001943 (1 purchase) | [TD-TC-001943.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001943.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000473 | [TD-TC-000473.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000473.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 5 | TC-001943 (2 timeout reversal) | [TD-TC-001943.step2.timeout-reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001943.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000473 | [TD-TC-000473.step2.timeout-reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000473.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 6 | TC-001944 (1 purchase) | [TD-TC-001944.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001944.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000474 | [TD-TC-000474.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000474.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 7 | TC-001944 (2 timeout reversal) | [TD-TC-001944.step2.timeout-reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001944.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000474 | [TD-TC-000474.step2.timeout-reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000474.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 8 | TC-001945 (1 purchase) | [TD-TC-001945.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001945.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000475 | [TD-TC-000475.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000475.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 9 | TC-001945 (2 timeout reversal) | [TD-TC-001945.step2.timeout-reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001945.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000475 | [TD-TC-000475.step2.timeout-reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000475.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 10 | TC-001946 (1 purchase) | [TD-TC-001946.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001946.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000476 | [TD-TC-000476.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000476.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 11 | TC-001946 (2 timeout reversal) | [TD-TC-001946.step2.timeout-reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001946.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000476 | [TD-TC-000476.step2.timeout-reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000476.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 12 | TC-001946 (3 next transaction) | [TD-TC-001946.step3.next-transaction.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001946.step3.next-transaction.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000476 | [TD-TC-000476.step3.next-transaction.json](../5-11-out/mastercard_credit/test_data/TD-TC-000476.step3.next-transaction.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 13 | TC-001947 (1 purchase) | [TD-TC-001947.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001947.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000477 | [TD-TC-000477.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000477.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 14 | TC-001947 (2 timeout reversal) | [TD-TC-001947.step2.timeout-reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001947.step2.timeout-reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000477 | [TD-TC-000477.step2.timeout-reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000477.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 15 | TC-001947 (3 tor retry) | [TD-TC-001947.step3.tor-retry.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001947.step3.tor-retry.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000477 | [TD-TC-000477.step3.tor-retry.json](../5-11-out/mastercard_credit/test_data/TD-TC-000477.step3.tor-retry.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 16 | TC-001947 (4 tor retry) | [TD-TC-001947.step4.tor-retry.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001947.step4.tor-retry.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000477 | [TD-TC-000477.step4.tor-retry.json](../5-11-out/mastercard_credit/test_data/TD-TC-000477.step4.tor-retry.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 17 | TC-001948 (2 subsequent) | [TD-TC-001948.step2.subsequent.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001948.step2.subsequent.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000478 | [TD-TC-000478.step2.subsequent.json](../5-11-out/mastercard_credit/test_data/TD-TC-000478.step2.subsequent.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 18 | TC-001949 (2 subsequent) | [TD-TC-001949.step2.subsequent.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001949.step2.subsequent.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000479 | [TD-TC-000479.step2.subsequent.json](../5-11-out/mastercard_credit/test_data/TD-TC-000479.step2.subsequent.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021M; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 19 | TC-001950 (2 subsequent) | [TD-TC-001950.step2.subsequent.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001950.step2.subsequent.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000480 | [TD-TC-000480.step2.subsequent.json](../5-11-out/mastercard_credit/test_data/TD-TC-000480.step2.subsequent.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021M; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 20 | TC-001951 (2 subsequent) | [TD-TC-001951.step2.subsequent.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001951.step2.subsequent.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000481 | [TD-TC-000481.step2.subsequent.json](../5-11-out/mastercard_credit/test_data/TD-TC-000481.step2.subsequent.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021M; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 21 | TC-001952 (2 subsequent) | [TD-TC-001952.step2.subsequent.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001952.step2.subsequent.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000482 | [TD-TC-000482.step2.subsequent.json](../5-11-out/mastercard_credit/test_data/TD-TC-000482.step2.subsequent.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021M; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 22 | TC-001953 (1 purchase) | [TD-TC-001953.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001953.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000483 | [TD-TC-000483.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000483.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 23 | TC-001954 (1 purchase) | [TD-TC-001954.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001954.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000484 | [TD-TC-000484.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000484.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 24 | TC-001954 (2 reversal) | [TD-TC-001954.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001954.step2.reversal.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000484 | [TD-TC-000484.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000484.step2.reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 25 | TC-001955 (1 purchase) | [TD-TC-001955.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001955.step1.purchase.json#L30) line 30 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000485 | [TD-TC-000485.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000485.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |

… 72 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-05"></a>

### CD-05 – Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data – FIXED

| | |
|---|---|
| Field | E12 + Table 005 (source ID D-08) |
| Previous run | 47 test cases / 57 files failing (log: 47 / 57) |
| Previously failing files in the new run | 57 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `0531 with entry mode 79` (7 files); `1229 with entry mode 79` (5 files); `0729 with entry mode 79` (4 files) |
| Check it yourself | Previous: [TD-TC-001906.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001906.json#L15) line 15 → `291220166504315 with entry mode 79`. New (TC-000437): [TD-TC-000437.json](../5-11-out/mastercard_credit/test_data/TD-TC-000437.json#L15) line 15 → `1229 with entry mode 79`<br>Expected: expiry MMYY only |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001906 (1 purchase) | [TD-TC-001906.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001906.json#L15) line 15 | `291220166504315 with entry mode 79` | TC-000437 | [TD-TC-000437.json](../5-11-out/mastercard_credit/test_data/TD-TC-000437.json#L15) line 15 | `1229 with entry mode 79` | FIXED |
| 2 | TC-001907 (1 purchase) | [TD-TC-001907.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001907.json#L15) line 15 | `291220166504315 with entry mode 79` | TC-000438 | [TD-TC-000438.json](../5-11-out/mastercard_credit/test_data/TD-TC-000438.json#L15) line 15 | `1229 with entry mode 79` | FIXED |
| 3 | TC-001908 (1 purchase) | [TD-TC-001908.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001908.step1.purchase.json#L15) line 15 | `290720106815923 with entry mode 79` | TC-000439 | [TD-TC-000439.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000439.step1.purchase.json#L15) line 15 | `0729 with entry mode 79` | FIXED |
| 4 | TC-001908 (2 split tender) | [TD-TC-001908.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001908.step2.split-tender.json#L15) line 15 | `310620133641193 with entry mode 79` | TC-000439 | [TD-TC-000439.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000439.step2.split-tender.json#L15) line 15 | `0631 with entry mode 79` | FIXED |
| 5 | TC-001909 (1 purchase) | [TD-TC-001909.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001909.step1.purchase.json#L15) line 15 | `300320131444930 with entry mode 79` | TC-000440 | [TD-TC-000440.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000440.step1.purchase.json#L15) line 15 | `0330 with entry mode 79` | FIXED |
| 6 | TC-001910 (1 purchase) | [TD-TC-001910.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001910.step1.purchase.json#L15) line 15 | `311220150085719 with entry mode 79` | TC-000441 | [TD-TC-000441.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000441.step1.purchase.json#L15) line 15 | `1231 with entry mode 79` | FIXED |
| 7 | TC-001911 (1 purchase) | [TD-TC-001911.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001911.json#L15) line 15 | `270420115867428 with entry mode 79` | TC-000442 | [TD-TC-000442.json](../5-11-out/mastercard_credit/test_data/TD-TC-000442.json#L15) line 15 | `0427 with entry mode 79` | FIXED |
| 8 | TC-001912 (1 purchase) | [TD-TC-001912.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001912.step1.purchase.json#L15) line 15 | `280620191993128 with entry mode 79` | TC-000443 | [TD-TC-000443.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000443.step1.purchase.json#L15) line 15 | `0628 with entry mode 79` | FIXED |
| 9 | TC-001913 (1 purchase) | [TD-TC-001913.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001913.step1.purchase.json#L15) line 15 | `290720121260733 with entry mode 79` | TC-000444 | [TD-TC-000444.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000444.step1.purchase.json#L15) line 15 | `0729 with entry mode 79` | FIXED |
| 10 | TC-001914 (1 purchase) | [TD-TC-001914.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001914.step1.purchase.json#L15) line 15 | `280220134077436 with entry mode 79` | TC-000445 | [TD-TC-000445.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000445.step1.purchase.json#L15) line 15 | `0228 with entry mode 79` | FIXED |
| 11 | TC-001915 (1 purchase) | [TD-TC-001915.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001915.step1.purchase.json#L15) line 15 | `290120171570299 with entry mode 79` | TC-000446 | [TD-TC-000446.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000446.step1.purchase.json#L15) line 15 | `0129 with entry mode 79` | FIXED |
| 12 | TC-001916 (1 purchase) | [TD-TC-001916.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001916.step1.purchase.json#L15) line 15 | `310320178873546 with entry mode 79` | TC-000447 | [TD-TC-000447.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000447.step1.purchase.json#L15) line 15 | `0331 with entry mode 79` | FIXED |
| 13 | TC-001916 (3 next transaction) | [TD-TC-001916.step3.next-transaction.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001916.step3.next-transaction.json#L15) line 15 | `310320178873546 with entry mode 79` | TC-000447 | [TD-TC-000447.step3.next-transaction.json](../5-11-out/mastercard_credit/test_data/TD-TC-000447.step3.next-transaction.json#L15) line 15 | `0331 with entry mode 79` | FIXED |
| 14 | TC-001917 (1 purchase) | [TD-TC-001917.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001917.step1.purchase.json#L15) line 15 | `290120185135278 with entry mode 79` | TC-000448 | [TD-TC-000448.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000448.step1.purchase.json#L15) line 15 | `0129 with entry mode 79` | FIXED |
| 15 | TC-001918 (1 purchase) | [TD-TC-001918.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001918.step1.purchase.json#L15) line 15 | `291020176443971 with entry mode 79` | TC-000449 | [TD-TC-000449.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000449.step1.purchase.json#L15) line 15 | `1029 with entry mode 79` | FIXED |
| 16 | TC-001919 (1 purchase) | [TD-TC-001919.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001919.step1.purchase.json#L15) line 15 | `310520163798522 with entry mode 79` | TC-000450 | [TD-TC-000450.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000450.step1.purchase.json#L15) line 15 | `0531 with entry mode 79` | FIXED |
| 17 | TC-001920 (1 purchase) | [TD-TC-001920.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001920.step1.purchase.json#L15) line 15 | `310520122134445 with entry mode 79` | TC-000451 | [TD-TC-000451.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000451.step1.purchase.json#L15) line 15 | `0531 with entry mode 79` | FIXED |
| 18 | TC-001920 (2 return) | [TD-TC-001920.step2.return.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001920.step2.return.json#L15) line 15 | `310520122134445 with entry mode 79` | TC-000451 | [TD-TC-000451.step2.return.json](../5-11-out/mastercard_credit/test_data/TD-TC-000451.step2.return.json#L15) line 15 | `0531 with entry mode 79` | FIXED |
| 19 | TC-001921 (1 purchase) | [TD-TC-001921.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001921.step1.purchase.json#L15) line 15 | `310220153240990 with entry mode 79` | TC-000452 | [TD-TC-000452.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000452.step1.purchase.json#L15) line 15 | `0231 with entry mode 79` | FIXED |
| 20 | TC-001922 (1 purchase) | [TD-TC-001922.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001922.step1.purchase.json#L15) line 15 | `300120112432277 with entry mode 79` | TC-000453 | [TD-TC-000453.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000453.step1.purchase.json#L15) line 15 | `0130 with entry mode 79` | FIXED |
| 21 | TC-001923 (1 purchase) | [TD-TC-001923.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001923.step1.purchase.json#L15) line 15 | `290520116095457 with entry mode 79` | TC-000454 | [TD-TC-000454.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000454.step1.purchase.json#L15) line 15 | `0529 with entry mode 79` | FIXED |
| 22 | TC-001924 (1 timeout reversal) | [TD-TC-001924.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001924.json#L15) line 15 | `291220196383320 with entry mode 79` | TC-000455 | [TD-TC-000455.json](../5-11-out/mastercard_credit/test_data/TD-TC-000455.json#L15) line 15 | `2912 with entry mode 79` | FIXED |
| 23 | TC-001925 (1 purchase) | [TD-TC-001925.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001925.step1.purchase.json#L15) line 15 | `270920114096176 with entry mode 79` | TC-000456 | [TD-TC-000456.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000456.step1.purchase.json#L15) line 15 | `0927 with entry mode 79` | FIXED |
| 24 | TC-001926 (1 purchase) | [TD-TC-001926.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001926.step1.purchase.json#L15) line 15 | `291220189307660 with entry mode 79` | TC-000457 | [TD-TC-000457.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000457.step1.purchase.json#L15) line 15 | `1229 with entry mode 79` | FIXED |
| 25 | TC-001927 (1 purchase) | [TD-TC-001927.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001927.json#L15) line 15 | `280720118943139 with entry mode 79` | TC-000458 | [TD-TC-000458.json](../5-11-out/mastercard_credit/test_data/TD-TC-000458.json#L15) line 15 | `0728 with entry mode 79` | FIXED |

… 32 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-06"></a>

### CD-06 – Unmatched time-out reversal carries full track data – FIXED

| | |
|---|---|
| Field | E12 (source ID D-13) |
| Previous run | 5 test cases / 5 files failing (log: 5 / 5) |
| Previously failing files in the new run | 5 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `3010` (1 file); `2806` (1 file); `2803` (1 file) |
| Check it yourself | Previous: [TD-TC-001744.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001744.json#L15) line 15 → `301120131866600`. New (TC-000280): [TD-TC-000280.json](../5-11-out/mastercard_credit/test_data/TD-TC-000280.json#L15) line 15 → `3010`<br>Expected: 3010 (expiry YYMM only) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001744 (1 timeout reversal) | [TD-TC-001744.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001744.json#L15) line 15 | `301120131866600` | TC-000280 | [TD-TC-000280.json](../5-11-out/mastercard_credit/test_data/TD-TC-000280.json#L15) line 15 | `3010` | FIXED |
| 2 | TC-001781 (1 timeout reversal) | [TD-TC-001781.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001781.json#L15) line 15 | `290520153424984` | TC-000316 | [TD-TC-000316.json](../5-11-out/mastercard_credit/test_data/TD-TC-000316.json#L15) line 15 | `2806` | FIXED |
| 3 | TC-001817 (1 timeout reversal) | [TD-TC-001817.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001817.json#L15) line 15 | `280410133904840` | TC-000351 | [TD-TC-000351.json](../5-11-out/mastercard_credit/test_data/TD-TC-000351.json#L15) line 15 | `2803` | FIXED |
| 4 | TC-001851 (1 timeout reversal) | [TD-TC-001851.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001851.json#L15) line 15 | `300120114060327` | TC-000384 | [TD-TC-000384.json](../5-11-out/mastercard_credit/test_data/TD-TC-000384.json#L15) line 15 | `3001` | FIXED |
| 5 | TC-001993 (1 timeout reversal) | [TD-TC-001993.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001993.json#L15) line 15 | `270820122676321` | TC-000522 | [TD-TC-000522.json](../5-11-out/mastercard_credit/test_data/TD-TC-000522.json#L15) line 15 | `2708` | FIXED |

---

<a id="cd-07"></a>

### CD-07 – Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it – FIXED

| | |
|---|---|
| Field | E58 / E84, Seg 100 (source ID D-05, D-14) |
| Previous run | 31 test cases / 31 files failing (log: 31 / 31) |
| Previously failing files in the new run | 31 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `SegmentLength 083; provisional amount 750` (21 files); `SegmentLength 088; provisional amount 750` (8 files); `SegmentLength 077; provisional amount 750` (2 files) |
| Check it yourself | Previous: [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L7) line 7 → `SegmentLength 093; provisional amount 24677595`. New (TC-000262): [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L7) line 7 → `SegmentLength 088; provisional amount 750`<br>Expected: Segment Length for an amount of at most 4 digits (086–089) or computed at run time; provisional amount ≤ 1500 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001726 (2 split tender) | [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 24677595` | TC-000262 | [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 2 | TC-001727 (2 reversal) | [TD-TC-001727.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001727.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 01162051` | TC-000263 | [TD-TC-000263.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000263.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 3 | TC-001743 (2 split tender) | [TD-TC-001743.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001743.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 83968206` | TC-000279 | [TD-TC-000279.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000279.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 4 | TC-001763 (2 split tender) | [TD-TC-001763.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001763.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 59877108` | TC-000298 | [TD-TC-000298.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000298.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 5 | TC-001764 (2 reversal) | [TD-TC-001764.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001764.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 35427385` | TC-000299 | [TD-TC-000299.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000299.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 6 | TC-001780 (2 split tender) | [TD-TC-001780.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001780.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 75633738` | TC-000315 | [TD-TC-000315.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000315.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 7 | TC-001799 (2 split tender) | [TD-TC-001799.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001799.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 55105548` | TC-000333 | [TD-TC-000333.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000333.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 8 | TC-001800 (2 reversal) | [TD-TC-001800.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001800.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 63096215` | TC-000334 | [TD-TC-000334.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000334.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 9 | TC-001816 (2 split tender) | [TD-TC-001816.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001816.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 23975365` | TC-000350 | [TD-TC-000350.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000350.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 10 | TC-001835 (2 split tender) | [TD-TC-001835.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001835.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 10988538` | TC-000368 | [TD-TC-000368.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000368.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 11 | TC-001836 (2 reversal) | [TD-TC-001836.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001836.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 55839358` | TC-000369 | [TD-TC-000369.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000369.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 12 | TC-001870 (2 split tender) | [TD-TC-001870.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001870.step2.split-tender.json#L7) line 7 | `SegmentLength 082; provisional amount 08073891` | TC-000402 | [TD-TC-000402.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000402.step2.split-tender.json#L7) line 7 | `SegmentLength 077; provisional amount 750` | FIXED |
| 13 | TC-001871 (2 reversal) | [TD-TC-001871.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001871.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 73622739` | TC-000403 | [TD-TC-000403.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000403.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 14 | TC-001908 (2 split tender) | [TD-TC-001908.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001908.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 18989100` | TC-000439 | [TD-TC-000439.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000439.step2.split-tender.json#L7) line 7 | `SegmentLength 077; provisional amount 750` | FIXED |
| 15 | TC-001909 (2 reversal) | [TD-TC-001909.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001909.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 60022345` | TC-000440 | [TD-TC-000440.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000440.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 16 | TC-001977 (2 split tender) | [TD-TC-001977.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001977.step2.split-tender.json#L7) line 7 | `SegmentLength 093; provisional amount 39262491` | TC-000506 | [TD-TC-000506.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000506.step2.split-tender.json#L7) line 7 | `SegmentLength 088; provisional amount 750` | FIXED |
| 17 | TC-001978 (2 reversal) | [TD-TC-001978.step2.reversal.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001978.step2.reversal.json#L7) line 7 | `SegmentLength 088; provisional amount 56127242` | TC-000507 | [TD-TC-000507.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000507.step2.reversal.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 18 | TC-002023 (2 completion) | [TD-TC-002023.step2.completion.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002023.step2.completion.json#L7) line 7 | `SegmentLength 088; provisional amount 62862543` | TC-000553 | [TD-TC-000553.step2.completion.json](../5-11-out/mastercard_credit/test_data/TD-TC-000553.step2.completion.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 19 | TC-002024 (2 cancellation) | [TD-TC-002024.step2.cancellation.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002024.step2.cancellation.json#L7) line 7 | `SegmentLength 088; provisional amount 02760066` | TC-000554 | [TD-TC-000554.step2.cancellation.json](../5-11-out/mastercard_credit/test_data/TD-TC-000554.step2.cancellation.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 20 | TC-002038 (2 completion) | [TD-TC-002038.step2.completion.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002038.step2.completion.json#L7) line 7 | `SegmentLength 088; provisional amount 90964076` | TC-000571 | [TD-TC-000571.step2.completion.json](../5-11-out/mastercard_credit/test_data/TD-TC-000571.step2.completion.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 21 | TC-002039 (2 cancellation) | [TD-TC-002039.step2.cancellation.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002039.step2.cancellation.json#L7) line 7 | `SegmentLength 088; provisional amount 09955890` | TC-000572 | [TD-TC-000572.step2.cancellation.json](../5-11-out/mastercard_credit/test_data/TD-TC-000572.step2.cancellation.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 22 | TC-002052 (2 completion) | [TD-TC-002052.step2.completion.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002052.step2.completion.json#L7) line 7 | `SegmentLength 088; provisional amount 41581895` | TC-000588 | [TD-TC-000588.step2.completion.json](../5-11-out/mastercard_credit/test_data/TD-TC-000588.step2.completion.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 23 | TC-002053 (2 cancellation) | [TD-TC-002053.step2.cancellation.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002053.step2.cancellation.json#L7) line 7 | `SegmentLength 088; provisional amount 39014290` | TC-000589 | [TD-TC-000589.step2.cancellation.json](../5-11-out/mastercard_credit/test_data/TD-TC-000589.step2.cancellation.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 24 | TC-002066 (2 completion) | [TD-TC-002066.step2.completion.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002066.step2.completion.json#L7) line 7 | `SegmentLength 088; provisional amount 08347613` | TC-000605 | [TD-TC-000605.step2.completion.json](../5-11-out/mastercard_credit/test_data/TD-TC-000605.step2.completion.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |
| 25 | TC-002067 (2 cancellation) | [TD-TC-002067.step2.cancellation.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002067.step2.cancellation.json#L7) line 7 | `SegmentLength 088; provisional amount 96500944` | TC-000606 | [TD-TC-000606.step2.cancellation.json](../5-11-out/mastercard_credit/test_data/TD-TC-000606.step2.cancellation.json#L7) line 7 | `SegmentLength 083; provisional amount 750` | FIXED |

… 6 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-08"></a>

### CD-08 – Split-tender card B chip amount (Tag 9F02) is the full original amount – FIXED

| | |
|---|---|
| Field | Tag 9F02, Seg 130 (source ID D-05) |
| Previous run | 4 test cases / 4 files failing (log: 4 / 4) |
| Previously failing files in the new run | 4 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F02 = 000000000750` (4 files) |
| Check it yourself | Previous: [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L49) line 49 → `9F02 = 000000001500`. New (TC-000262): [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L54) line 54 → `9F02 = 000000000750`<br>Expected: card B's remainder (< 000000001500) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001726 (2 split tender) | [TD-TC-001726.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001726.step2.split-tender.json#L49) line 49 | `9F02 = 000000001500` | TC-000262 | [TD-TC-000262.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000262.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |
| 2 | TC-001743 (2 split tender) | [TD-TC-001743.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001743.step2.split-tender.json#L49) line 49 | `9F02 = 000000001500` | TC-000279 | [TD-TC-000279.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000279.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |
| 3 | TC-001763 (2 split tender) | [TD-TC-001763.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001763.step2.split-tender.json#L49) line 49 | `9F02 = 000000001500` | TC-000298 | [TD-TC-000298.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000298.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |
| 4 | TC-001780 (2 split tender) | [TD-TC-001780.step2.split-tender.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001780.step2.split-tender.json#L49) line 49 | `9F02 = 000000001500` | TC-000315 | [TD-TC-000315.step2.split-tender.json](../5-11-out/mastercard_credit/test_data/TD-TC-000315.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |

---

<a id="cd-09"></a>

### CD-09 – Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 – FIXED

| | |
|---|---|
| Field | E84, Seg 102 (source ID S2 D-08) |
| Previous run | 6 test cases / 6 files failing (log: 6 / 6) |
| Previously failing files in the new run | 6 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `029` (6 files) |
| Check it yourself | Previous: [TD-TC-001742.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001742.json#L29) line 29 → `033`. New (TC-000278): [TD-TC-000278.json](../5-11-out/mastercard_credit/test_data/TD-TC-000278.json#L29) line 29 → `029`<br>Expected: 029 (12.3: FS after type/length and the last amount, '\' after quantity and unit price) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001742 (1 purchase) | [TD-TC-001742.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001742.json#L29) line 29 | `033` | TC-000278 | [TD-TC-000278.json](../5-11-out/mastercard_credit/test_data/TD-TC-000278.json#L29) line 29 | `029` | FIXED |
| 2 | TC-001743 (1 purchase) | [TD-TC-001743.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001743.step1.purchase.json#L29) line 29 | `033` | TC-000279 | [TD-TC-000279.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000279.step1.purchase.json#L29) line 29 | `029` | FIXED |
| 3 | TC-001779 (1 purchase) | [TD-TC-001779.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001779.json#L29) line 29 | `033` | TC-000314 | [TD-TC-000314.json](../5-11-out/mastercard_credit/test_data/TD-TC-000314.json#L29) line 29 | `029` | FIXED |
| 4 | TC-001780 (1 purchase) | [TD-TC-001780.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001780.step1.purchase.json#L29) line 29 | `033` | TC-000315 | [TD-TC-000315.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000315.step1.purchase.json#L29) line 29 | `029` | FIXED |
| 5 | TC-001815 (1 purchase) | [TD-TC-001815.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001815.json#L29) line 29 | `033` | TC-000349 | [TD-TC-000349.json](../5-11-out/mastercard_credit/test_data/TD-TC-000349.json#L29) line 29 | `029` | FIXED |
| 6 | TC-001816 (1 purchase) | [TD-TC-001816.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001816.step1.purchase.json#L29) line 29 | `033` | TC-000350 | [TD-TC-000350.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000350.step1.purchase.json#L29) line 29 | `029` | FIXED |

---

<a id="cd-10"></a>

### CD-10 – Totals Card Label (Element 13) is 2 bytes instead of 4 – FIXED

| | |
|---|---|
| Field | E13, Seg 105 (source ID S2 D-06) |
| Previous run | 72 test cases / 72 files failing (log: 72 / 72) |
| Previously failing files in the new run | 64 fixed, 8 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `'CC  '` (64 files); `no card-type category is sent any more (nothing occurred…card type) (75 chars)` (8 files) |
| Notes | no card-type category is sent any more (nothing occurred for the card type) (8) |
| Check it yourself | Previous: [TD-TC-001736.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001736.step2.totals.json#L20) line 20 → `'CC'`. New (TC-000272): [TD-TC-000272.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000272.step2.totals.json#L20) line 20 → `'CC  '`<br>Expected: 'CC  ' |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001737 (3 totals) | [TD-TC-001737.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001737.step3.totals.json#L20) line 20 | `'CC'` | TC-000273 | [TD-TC-000273.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000273.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 2 | TC-001774 (3 totals) | [TD-TC-001774.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001774.step3.totals.json#L20) line 20 | `'CC'` | TC-000309 | [TD-TC-000309.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000309.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 3 | TC-001810 (3 totals) | [TD-TC-001810.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001810.step3.totals.json#L20) line 20 | `'CC'` | TC-000344 | [TD-TC-000344.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000344.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 4 | TC-001846 (3 totals) | [TD-TC-001846.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001846.step3.totals.json#L20) line 20 | `'CC'` | TC-000379 | [TD-TC-000379.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000379.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 5 | TC-001881 (3 totals) | [TD-TC-001881.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001881.step3.totals.json#L20) line 20 | `'CC'` | TC-000413 | [TD-TC-000413.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000413.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 6 | TC-001919 (3 totals) | [TD-TC-001919.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001919.step3.totals.json#L20) line 20 | `'CC'` | TC-000450 | [TD-TC-000450.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000450.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 7 | TC-001954 (3 totals) | [TD-TC-001954.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001954.step3.totals.json#L20) line 20 | `'CC'` | TC-000484 | [TD-TC-000484.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000484.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 8 | TC-001988 (3 totals) | [TD-TC-001988.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001988.step3.totals.json#L20) line 20 | `'CC'` | TC-000517 | [TD-TC-000517.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000517.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 9 | TC-001736 (2 totals) | [TD-TC-001736.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001736.step2.totals.json#L20) line 20 | `'CC'` | TC-000272 | [TD-TC-000272.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000272.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 10 | TC-001738 (3 totals) | [TD-TC-001738.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001738.step3.totals.json#L20) line 20 | `'CC'` | TC-000274 | [TD-TC-000274.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000274.step3.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 11 | TC-001739 (3 totals) | [TD-TC-001739.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001739.step3.totals.json#L20) line 20 | `'CC'` | TC-000275 | [TD-TC-000275.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step3.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 12 | TC-001740 (2 totals) | [TD-TC-001740.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001740.step2.totals.json#L20) line 20 | `'CC'` | TC-000276 | [TD-TC-000276.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000276.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 13 | TC-001741 (2 totals) | [TD-TC-001741.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001741.step2.totals.json#L20) line 20 | `'CC'` | TC-000277 | [TD-TC-000277.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000277.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 14 | TC-001754 (2 totals) | [TD-TC-001754.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001754.step2.totals.json#L20) line 20 | `'CC'` | TC-000290 | [TD-TC-000290.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000290.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 15 | TC-001755 (2 totals) | [TD-TC-001755.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001755.step2.totals.json#L20) line 20 | `'CC'` | TC-000291 | [TD-TC-000291.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000291.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 16 | TC-001773 (2 totals) | [TD-TC-001773.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001773.step2.totals.json#L20) line 20 | `'CC'` | TC-000308 | [TD-TC-000308.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000308.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 17 | TC-001775 (3 totals) | [TD-TC-001775.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001775.step3.totals.json#L20) line 20 | `'CC'` | TC-000310 | [TD-TC-000310.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000310.step3.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 18 | TC-001776 (3 totals) | [TD-TC-001776.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001776.step3.totals.json#L20) line 20 | `'CC'` | TC-000311 | [TD-TC-000311.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000311.step3.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 19 | TC-001777 (2 totals) | [TD-TC-001777.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001777.step2.totals.json#L20) line 20 | `'CC'` | TC-000312 | [TD-TC-000312.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000312.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 20 | TC-001778 (2 totals) | [TD-TC-001778.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001778.step2.totals.json#L20) line 20 | `'CC'` | TC-000313 | [TD-TC-000313.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000313.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 21 | TC-001791 (2 totals) | [TD-TC-001791.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001791.step2.totals.json#L20) line 20 | `'CC'` | TC-000326 | [TD-TC-000326.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000326.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 22 | TC-001792 (2 totals) | [TD-TC-001792.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001792.step2.totals.json#L20) line 20 | `'CC'` | TC-000327 | [TD-TC-000327.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000327.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 23 | TC-001809 (2 totals) | [TD-TC-001809.step2.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001809.step2.totals.json#L20) line 20 | `'CC'` | TC-000343 | [TD-TC-000343.step2.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000343.step2.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 24 | TC-001811 (3 totals) | [TD-TC-001811.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001811.step3.totals.json#L20) line 20 | `'CC'` | TC-000345 | [TD-TC-000345.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000345.step3.totals.json#L20) line 20 | `'CC  '` | FIXED |
| 25 | TC-001812 (3 totals) | [TD-TC-001812.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001812.step3.totals.json#L20) line 20 | `'CC'` | TC-000346 | [TD-TC-000346.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000346.step3.totals.json#L20) line 20 | `'CC  '` | FIXED |

… 47 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-11"></a>

### CD-11 – Totals card-type category sent with count 00000 – FIXED

| | |
|---|---|
| Field | E16, Seg 105 (source ID S2 D-10) |
| Previous run | 8 test cases / 8 files failing (log: 8 / 8) |
| Previously failing files in the new run | 8 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `category not sent` (8 files) |
| Check it yourself | Previous: [TD-TC-001737.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001737.step3.totals.json#L21) line 21 → `Card Label 'CC', count 00000, amount 00000000`. New (TC-000273): [TD-TC-000273.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000273.step3.totals.json#L19) line 19 → `category not sent`<br>Expected: category only when data occurs |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001737 (3 totals) | [TD-TC-001737.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001737.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000273 | [TD-TC-000273.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000273.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 2 | TC-001774 (3 totals) | [TD-TC-001774.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001774.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000309 | [TD-TC-000309.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000309.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 3 | TC-001810 (3 totals) | [TD-TC-001810.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001810.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000344 | [TD-TC-000344.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000344.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 4 | TC-001846 (3 totals) | [TD-TC-001846.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001846.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000379 | [TD-TC-000379.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000379.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 5 | TC-001881 (3 totals) | [TD-TC-001881.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001881.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000413 | [TD-TC-000413.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000413.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 6 | TC-001919 (3 totals) | [TD-TC-001919.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001919.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000450 | [TD-TC-000450.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000450.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 7 | TC-001954 (3 totals) | [TD-TC-001954.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001954.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000484 | [TD-TC-000484.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000484.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 8 | TC-001988 (3 totals) | [TD-TC-001988.step3.totals.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001988.step3.totals.json#L21) line 21 | `Card Label 'CC', count 00000, amount 00000000` | TC-000517 | [TD-TC-000517.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000517.step3.totals.json#L19) line 19 | `category not sent` | FIXED |

---

<a id="cd-12"></a>

### CD-12 – Expired-card test uses a card that is not expired – FIXED

| | |
|---|---|
| Field | E12 (+ Tag 5F24, TVR) (source ID D-03) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `251220127370826 (expiry YYMM 2512); Tag 5F24 251231; TVR…d bit set) (85 chars)` (1 file); `251220142194190 (expiry YYMM 2512); Tag 5F24 251231; TVR…d bit set) (85 chars)` (1 file) |
| Check it yourself | Previous: [TD-TC-001723.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001723.json#L15) line 15 → `311020127370826 (expiry YYMM 3110); …t not set) (89 chars)`. New (TC-000259): [TD-TC-000259.json](../5-11-out/mastercard_credit/test_data/TD-TC-000259.json#L15) line 15 → `251220127370826 (expiry YYMM 2512); …d bit set) (85 chars)`<br>Expected: expiry before 2611 (YYMM); Tag 5F24 = same expiry; TVR byte 2 bit 7 set |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001723 (1 purchase) | [TD-TC-001723.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001723.json#L15) line 15 | `311020127370826 (expiry YYMM 3110); Tag 5F24 a…t not set) (89 chars)` | TC-000259 | [TD-TC-000259.json](../5-11-out/mastercard_credit/test_data/TD-TC-000259.json#L15) line 15 | `251220127370826 (expiry YYMM 2512); Tag 5F24 2…d bit set) (85 chars)` | FIXED |
| 2 | TC-001761 (1 purchase) | [TD-TC-001761.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001761.json#L15) line 15 | `290720142194190 (expiry YYMM 2907); Tag 5F24 a…t not set) (89 chars)` | TC-000296 | [TD-TC-000296.json](../5-11-out/mastercard_credit/test_data/TD-TC-000296.json#L15) line 15 | `251220142194190 (expiry YYMM 2512); Tag 5F24 2…d bit set) (85 chars)` | FIXED |

---

<a id="cd-13"></a>

### CD-13 – Online-PIN test has no PIN block and a signature CVM – FIXED – value awaiting client input

| | |
|---|---|
| Field | E33, Tag 9F34 (source ID D-04) |
| Previous run | 4 test cases / 4 files failing (log: 4 / 4) |
| Previously failing files in the new run | 4 awaiting client input |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `E33 '' / 9F34 420300` (4 files) |
| Notes | PIN block listed in awaiting_inputs (4) |
| Check it yourself | Previous: [TD-TC-001724.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001724.json#L16) line 16 → `E33 '' / 9F34 5E0302`. New (TC-000260): [TD-TC-000260.json](../5-11-out/mastercard_credit/test_data/TD-TC-000260.json#L16) line 16 → `E33 '' / 9F34 420300`<br>Expected: DUKPT KSN + PIN block / 9F34 CVM code 02 (e.g. 420300) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001724 (1 purchase) | [TD-TC-001724.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001724.json#L16) line 16 | `E33 '' / 9F34 5E0302` | TC-000260 | [TD-TC-000260.json](../5-11-out/mastercard_credit/test_data/TD-TC-000260.json#L16) line 16 | `E33 '' / 9F34 420300` | AWAITING CLIENT INPUT |
| 2 | TC-001762 (1 purchase) | [TD-TC-001762.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001762.json#L16) line 16 | `E33 '' / 9F34 5E0302` | TC-000297 | [TD-TC-000297.json](../5-11-out/mastercard_credit/test_data/TD-TC-000297.json#L16) line 16 | `E33 '' / 9F34 420300` | AWAITING CLIENT INPUT |
| 3 | TC-002021 (1 auth only) | [TD-TC-002021.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002021.json#L16) line 16 | `E33 '' / 9F34 5E0302` | TC-000551 | [TD-TC-000551.json](../5-11-out/mastercard_credit/test_data/TD-TC-000551.json#L16) line 16 | `E33 '' / 9F34 420300` | AWAITING CLIENT INPUT |
| 4 | TC-002037 (1 auth only) | [TD-TC-002037.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002037.json#L16) line 16 | `E33 '' / 9F34 5E0302` | TC-000570 | [TD-TC-000570.json](../5-11-out/mastercard_credit/test_data/TD-TC-000570.json#L16) line 16 | `E33 '' / 9F34 420300` | AWAITING CLIENT INPUT |

---

<a id="cd-14"></a>

### CD-14 – Offline-PIN test has a signature CVM – FIXED

| | |
|---|---|
| Field | Tag 9F34 (source ID D-11) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F34 = 440302` (2 files) |
| Check it yourself | Previous: [TD-TC-001725.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001725.json#L49) line 49 → `9F34 = 5E0302`. New (TC-000261): [TD-TC-000261.json](../5-11-out/mastercard_credit/test_data/TD-TC-000261.json#L54) line 54 → `9F34 = 440302`<br>Expected: CVM code 01 or 04 (e.g. 410302 / 440302) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001725 (1 purchase) | [TD-TC-001725.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001725.json#L49) line 49 | `9F34 = 5E0302` | TC-000261 | [TD-TC-000261.json](../5-11-out/mastercard_credit/test_data/TD-TC-000261.json#L54) line 54 | `9F34 = 440302` | FIXED |
| 2 | TC-002022 (1 auth only) | [TD-TC-002022.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002022.json#L49) line 49 | `9F34 = 5E0302` | TC-000552 | [TD-TC-000552.json](../5-11-out/mastercard_credit/test_data/TD-TC-000552.json#L54) line 54 | `9F34 = 440302` | FIXED |

---

<a id="cd-15"></a>

### CD-15 – Cashback test: chip data ignores the cash amount – FIXED

| | |
|---|---|
| Field | Tags 9C, 9F02, 9F03 (source ID S2 D-09) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F02 000000002000, 9F03 000000000500, 9C 09 (Cash Amount 500)` (2 files) |
| Check it yourself | Previous: [TD-TC-001747.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001747.json#L49) line 49 → `9F02 000000001500, 9F03 000000000000…mount 500) (61 chars)`. New (TC-000283): [TD-TC-000283.json](../5-11-out/mastercard_credit/test_data/TD-TC-000283.json#L54) line 54 → `9F02 000000002000, 9F03 000000000500…mount 500) (61 chars)`<br>Expected: 9F02 000000002000, 9F03 000000000500, 9C 09 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001747 (1 purchase) | [TD-TC-001747.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001747.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-000283 | [TD-TC-000283.json](../5-11-out/mastercard_credit/test_data/TD-TC-000283.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |
| 2 | TC-001784 (1 purchase) | [TD-TC-001784.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001784.json#L49) line 49 | `9F02 000000001500, 9F03 000000000000, 9C 00 (C…mount 500) (61 chars)` | TC-000319 | [TD-TC-000319.json](../5-11-out/mastercard_credit/test_data/TD-TC-000319.json#L54) line 54 | `9F02 000000002000, 9F03 000000000500, 9C 09 (C…mount 500) (61 chars)` | FIXED |

---

<a id="cd-16"></a>

### CD-16 – 'Track 1' test carries Track 2 data – FIXED

| | |
|---|---|
| Field | E2 / E12 (source ID D-07) |
| Previous run | 4 test cases / 4 files failing (log: 4 / 4) |
| Previously failing files in the new run | 4 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `B5159417392576181 / TEST/CARDHOLDER^300510138259706` (1 file); `B5179087533825270 / TEST/CARDHOLDER^300220175091909` (1 file); `B5113711878437156 / TEST/CARDHOLDER^270410167649565` (1 file) |
| Check it yourself | Previous: [TD-TC-001797.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001797.json#L14) line 14 → `5159417392576181 / 300510138259706`. New (TC-000331): [TD-TC-000331.json](../5-11-out/mastercard_credit/test_data/TD-TC-000331.json#L14) line 14 → `B5159417392576181 / TEST/CARDHOLDER^…0138259706 (51 chars)`<br>Expected: B<PAN> / NAME^YYMM<service code><discretionary data> (Track 1, at most 76) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001797 (1 purchase) | [TD-TC-001797.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001797.json#L14) line 14 | `5159417392576181 / 300510138259706` | TC-000331 | [TD-TC-000331.json](../5-11-out/mastercard_credit/test_data/TD-TC-000331.json#L14) line 14 | `B5159417392576181 / TEST/CARDHOLDER^300510138259706` | FIXED |
| 2 | TC-001930 (1 purchase) | [TD-TC-001930.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001930.json#L14) line 14 | `5179087533825270 / 300220175091909` | TC-000461 | [TD-TC-000461.json](../5-11-out/mastercard_credit/test_data/TD-TC-000461.json#L14) line 14 | `B5179087533825270 / TEST/CARDHOLDER^300220175091909` | FIXED |
| 3 | TC-002051 (1 auth only) | [TD-TC-002051.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002051.json#L14) line 14 | `5113711878437156 / 270410167649565` | TC-000587 | [TD-TC-000587.json](../5-11-out/mastercard_credit/test_data/TD-TC-000587.json#L14) line 14 | `B5113711878437156 / TEST/CARDHOLDER^270410167649565` | FIXED |
| 4 | TC-002108 (1 auth only) | [TD-TC-002108.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002108.json#L14) line 14 | `5100427103377852 / 311020175359649` | TC-000653 | [TD-TC-000653.json](../5-11-out/mastercard_credit/test_data/TD-TC-000653.json#L14) line 14 | `B5100427103377852 / TEST/CARDHOLDER^311020175359649` | FIXED |

---

<a id="cd-17"></a>

### CD-17 – Terminal described two ways: Table 005 capability 8 with Table 031 12 – FIXED

| | |
|---|---|
| Field | Seg 111 Tables 005/031 (source ID D-06) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `005 = 908, 031 = 07` (1 file); `005 = 018, 031 = 07` (1 file) |
| Check it yourself | Previous: [TD-TC-001798.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001798.json#L39) line 39 → `005 = 908, 031 = 12`. New (TC-000332): [TD-TC-000332.json](../5-11-out/mastercard_credit/test_data/TD-TC-000332.json#L39) line 39 → `005 = 908, 031 = 07`<br>Expected: capability 8 only with 031 = 07 (no chip) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001798 (1 purchase) | [TD-TC-001798.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001798.json#L39) line 39 | `005 = 908, 031 = 12` | TC-000332 | [TD-TC-000332.json](../5-11-out/mastercard_credit/test_data/TD-TC-000332.json#L39) line 39 | `005 = 908, 031 = 07` | FIXED |
| 2 | TC-001869 (1 purchase) | [TD-TC-001869.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001869.json#L39) line 39 | `005 = 018, 031 = 12` | TC-000401 | [TD-TC-000401.json](../5-11-out/mastercard_credit/test_data/TD-TC-000401.json#L39) line 39 | `005 = 018, 031 = 07` | FIXED |

---

<a id="cd-18"></a>

### CD-18 – AVS test sends no address data (Table 003) – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Table 003, Seg 111 (source ID D-10) |
| Previous run | 8 test cases / 8 files failing (log: 8 / 8) |
| Previously failing files in the new run | 8 awaiting client input |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `003 sent, value empty` (8 files) |
| Notes | value listed in awaiting_inputs (8) |
| Check it yourself | Previous: [TD-TC-001886.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001886.json#L30) line 30 → `not sent (tables: 005=011, 031=12)`. New (TC-000418): [TD-TC-000418.json](../5-11-out/mastercard_credit/test_data/TD-TC-000418.json#L44) line 44 → `003 sent, value empty`<br>Expected: AVS address data from the client |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-001886 (1 purchase) | [TD-TC-001886.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001886.json#L30) line 30 | `not sent (tables: 005=011, 031=12)` | TC-000418 | [TD-TC-000418.json](../5-11-out/mastercard_credit/test_data/TD-TC-000418.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-001887 (1 purchase) | [TD-TC-001887.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001887.step1.purchase.json#L30) line 30 | `not sent (tables: 005=011, 031=12)` | TC-000419 | [TD-TC-000419.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000419.step1.purchase.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-001959 (1 purchase) | [TD-TC-001959.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001959.json#L30) line 30 | `not sent (tables: 005=101, 031=12)` | TC-000489 | [TD-TC-000489.json](../5-11-out/mastercard_credit/test_data/TD-TC-000489.json#L64) line 64 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-001960 (1 purchase) | [TD-TC-001960.step1.purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-001960.step1.purchase.json#L30) line 30 | `not sent (tables: 005=101, 031=12)` | TC-000490 | [TD-TC-000490.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000490.step1.purchase.json#L64) line 64 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-002011 (1 moto purchase) | [TD-TC-002011.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002011.json#L30) line 30 | `not sent (tables: 005=011, 031=12)` | TC-000539 | [TD-TC-000539.json](../5-11-out/mastercard_credit/test_data/TD-TC-000539.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-002012 (1 moto purchase) | [TD-TC-002012.step1.moto-purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002012.step1.moto-purchase.json#L30) line 30 | `not sent (tables: 005=011, 031=12)` | TC-000540 | [TD-TC-000540.step1.moto-purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000540.step1.moto-purchase.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-002017 (1 moto purchase) | [TD-TC-002017.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002017.json#L30) line 30 | `not sent (tables: 005=101, 031=12)` | TC-000546 | [TD-TC-000546.json](../5-11-out/mastercard_credit/test_data/TD-TC-000546.json#L64) line 64 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-002018 (1 moto purchase) | [TD-TC-002018.step1.moto-purchase.json](../MasterCard%20Credit/mastercard_credit/test_data/TD-TC-002018.step1.moto-purchase.json#L30) line 30 | `not sent (tables: 005=101, 031=12)` | TC-000547 | [TD-TC-000547.step1.moto-purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000547.step1.moto-purchase.json#L64) line 64 | `003 sent, value empty` | AWAITING CLIENT INPUT |

---

<a id="cd-19"></a>

### CD-19 – Traceability matrix points to a folder that is not delivered; follow-on legs never traced – FIXED

| Check | Previous run | New run |
|---|---|---|
| Trace rows (`reports/traceability_matrix_full.json` → `flat_rows`) | 450 | 488 |
| Folders the test data paths point to | `qe_shaped_test_data/` | `test_data/` |
| `flat_rows` whose test data path does not exist | 408 | 0 |
| Trace entries (all levels) with a missing path / of them marked `file_exists: true` | 816 / 408 | 0 / 0 |
| Delivered test data files never referenced by the trace | 431 of 839 | 0 of 961 |
| Entries with `file_exists: false` | 42 | 10 – ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)) |

Check it yourself: open `5-11-out/mastercard_credit/reports/traceability_matrix_full.json` and search `"test_data_file"` – the paths now start with `test_data/` (previously `qe_shaped_test_data/`, a folder that was never delivered), and every leg file of a flow is listed.

---

<a id="cd-20"></a>

### CD-20 – MC Credit package mixes two builds – FIXED

| Check | Previous run | New run |
|---|---|---|
| `test_cases.jsonl` test cases / `test_data` files | 450 / 839 | 488 / 961 |
| Embedded archives (test cases inside) | `BR_SCE_Test_cases.zip` (241) | none |
| `README.md` of an older build | present | none |
| Build IDs in the test cases | `buypass-atl105-mastercard-credit-bada93f456` (450) | `mastercard-credit-0e784ee3e7` (488) |
| `MANIFEST.json` | none | single_build = True, test_cases = 488, test_data_files = 961, foreign_archives = [] |
| Test case lines that are not valid JSON | 0 | 0 |

---

## 3. Review items of the previous log (not defects)

| ID | Item | Previous run | New run | Result |
|---|---|---|---|---|
| RV-01 | Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY | YYMM: 72 | YYMM: 86 | Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME) – e.g. [TD-TC-000403.step2.reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000403.step2.reversal.json#L15) line 15 |

## 4. Seen while re-checking (not in the previous log)

The Totals Request of two settlement flows does not match the transactions before it (Section 9.2, p.72; the same rule is defect D-21 of the Interlink Debit report). It was not in the previous log for this program.

Previous run: 16 test cases; new run: 16 test cases.

| New TC | Template | Totals file | Actual | Expected |
|---|---|---|---|---|
| TC-000275 | T8-PUR-TOR-SETTLE | [TD-TC-000275.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000311 | T8-PUR-TOR-SETTLE | [TD-TC-000311.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000311.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000346 | T8-PUR-TOR-SETTLE | [TD-TC-000346.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000346.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000381 | T8-PUR-TOR-SETTLE | [TD-TC-000381.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000381.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000415 | T8-PUR-TOR-SETTLE | [TD-TC-000415.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000415.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000452 | T8-PUR-TOR-SETTLE | [TD-TC-000452.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000452.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000486 | T8-PUR-TOR-SETTLE | [TD-TC-000486.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000486.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000519 | T8-PUR-TOR-SETTLE | [TD-TC-000519.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000519.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the CC bucket and send Grand Total 00000000 |
| TC-000560 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000560.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000560.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000578 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000578.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000578.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000595 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000595.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000595.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000612 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000612.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000612.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000630 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000630.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000630.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000648 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000648.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000648.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000664 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000664.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000664.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000680 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000680.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000680.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |

#### Explained step by step (new run)

**TC-000275 – T8-PUR-TOR-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 18

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-000275.step1.purchase.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step1.purchase.json#L19) line 19 | purchase (Transaction Type `0`) | `1500` = $15.00 | no response (time-out) | no response (timed out), so not in the totals |
| 2 | [TD-TC-000275.step2.timeout-reversal.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step2.timeout-reversal.json#L19) line 19 | timeout reversal (Transaction Type `Z`) | `1500` = $15.00 | Response Code `0` | its original was never approved, so nothing to take out |

So the CC bucket holds **count 0, amount $0.00** – nothing occurred for the card type, so Fields 15–17 are not sent (p.237).

Totals Request [TD-TC-000275.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'CC  '` | `not sent` | **WRONG** |
| Card Type Total Count (Element 16) | 21 | `00001` | `not sent` | **WRONG** |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `not sent` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00000000` | OK |
| Segment Length (Element 84) | 7 | `107` | `090` | **WRONG** |

The test case itself says for this step: *"the timed-out purchase is not in the totals"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 18 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

**TC-000560 – T8-AUT-COMPLETION-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 303

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-000560.step1.auth-only.json](../5-11-out/mastercard_credit/test_data/TD-TC-000560.step1.auth-only.json#L19) line 19 | auth only (Transaction Type `3`) | `1500` = $15.00 | Response Code `2` | Authorization Only - AO bucket, not in the grand total |
| 2 | [TD-TC-000560.step2.completion.json](../5-11-out/mastercard_credit/test_data/TD-TC-000560.step2.completion.json#L19) line 19 | completion (Transaction Type `0`) | `1500` = $15.00 | Response Code `0` | count +1, amount +$15.00 |

So the CC bucket holds **count 1, amount $15.00**.

Totals Request [TD-TC-000560.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000560.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'CC  '` | `'CC  '` | OK |
| Card Type Total Count (Element 16) | 21 | `00001` | `00001` | OK |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `00001500` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00001500` | **WRONG** |
| Segment Length (Element 84) | 7 | `107` | `107` | OK |

The test case itself says for this step: *"the completion is in the totals; the authorization only is not (no draft capture)"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 303 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

Re-run (from `regression-artifact-validator`): `python scripts/verify-defect-fixes.py`

