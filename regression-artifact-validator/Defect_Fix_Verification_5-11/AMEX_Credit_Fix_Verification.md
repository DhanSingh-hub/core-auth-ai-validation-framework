# American Express Credit – defect fix verification (previous run 31-10-out → new run 5-11-out)

| Item | Value |
|---|---|
| Previous run | `C:/Users/F7OKAGW/Downloads/31-10-out/american_express_credit` – 416 test cases, 790 test data files |
| New run | `5-11-out/american_express_credit` – 454 test cases, 912 test data files |
| Defect log(s) | `Defect MD file/` Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) and AMEX_Credit_Defect_Log.md (A-xx) |
| Test case matching | IDs are renumbered in the new run; test cases are matched by transaction category + entry mode + template (unique in both runs), flow steps by step number and role. 384 of 416 previous test cases have a match; 32 were removed, 70 are new |

Status: **FIXED** – every previously failing file now passes the rule · **FIXED – value awaiting client input** – the generator now builds the field correctly, but its value can only come from the client (listed in the test case's `awaiting_inputs`, status `needs_client_input`) · **PARTIALLY FIXED** – some files pass, some still fail · **NOT FIXED** – still failing · **CLOSED – test cases removed** – the affected test cases are no longer in the catalog.

## 1. Summary

| Defect | Source ID | What was wrong | Previous run: TCs / files (logged) | New run: TCs / files still failing | Status |
|---|---|---|---|---|---|
| [CD-01](#cd-01) | A-01 | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | 94 / 116 (94 / 116) | 0 / 0 | **FIXED** |
| [CD-02](#cd-02) | A-02 | EMV Additional Information (Element 118) is not EMVYES / EMVNOT | 94 / 116 (94 / 116) | 0 / 0 | **FIXED** |
| [CD-03](#cd-03) | – | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | 140 / 204 (140 / 204) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-04](#cd-04) | – | Credential-on-File indicators (Tables 032, 049, 056) missing | 46 / 88 (46 / 88) | 0 / 0 | **FIXED** |
| [CD-05](#cd-05) | A-03, A-03b | Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data | 44 / 54 (44 / 54) | 0 / 0 | **FIXED** |
| [CD-06](#cd-06) | – | Unmatched time-out reversal carries full track data | 5 / 5 (5 / 5) | 0 / 0 | **FIXED** |
| [CD-07](#cd-07) | A-06 | Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it | 31 / 31 (31 / 31) | 0 / 0 | **FIXED** |
| [CD-08](#cd-08) | A-07 | Split-tender card B chip amount (Tag 9F02) is the full original amount | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| [CD-09](#cd-09) | – | Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 | 6 / 6 (6 / 6) | 0 / 0 | **FIXED** |
| [CD-10](#cd-10) | A-05 | Totals Card Label (Element 13) is 2 bytes instead of 4 | 72 / 72 (72 / 72) | 0 / 0 | **FIXED** |
| [CD-11](#cd-11) | – | Totals card-type category sent with count 00000 | 8 / 8 (8 / 8) | 0 / 0 | **FIXED** |
| [CD-12](#cd-12) | A-08 | Expired-card test uses a card that is not expired | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-14](#cd-14) | A-09 | Offline-PIN test has a signature CVM | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-16](#cd-16) | – | 'Track 1' test carries Track 2 data | 2 / 2 (not in the log) | 0 / 0 | **FIXED** |
| [CD-17](#cd-17) | A-10 | Terminal described two ways: Table 005 capability 8 with Table 031 12 | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-18](#cd-18) | – | AVS test sends no address data (Table 003) | 8 / 8 (8 / 8) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-19](#cd-19) | – | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 374 trace rows with a missing path; 416 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |

## 2. Defect details

<a id="cd-01"></a>

### CD-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 – FIXED

| | |
|---|---|
| Field | E188, Seg 130 (source ID A-01) |
| Previous run | 94 test cases / 116 files failing (log: 94 / 116) |
| Previously failing files in the new run | 114 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `001` (114 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: `TD-TC-002471.json` line 47 → `01`. New (TC-001053): [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L52) line 52 → `001`<br>Expected: 001 (Tag 5F34 zero-padded to 3) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002492 (1 timeout reversal) | `TD-TC-002492.json` line 47 | `01` | TC-001074 | [TD-TC-001074.json](../5-11-out/american_express_credit/test_data/TD-TC-001074.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-002526 (1 timeout reversal) | `TD-TC-002526.json` line 47 | `01` | TC-001107 | [TD-TC-001107.json](../5-11-out/american_express_credit/test_data/TD-TC-001107.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-002471 (1 purchase) | `TD-TC-002471.json` line 47 | `01` | TC-001053 | [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L52) line 52 | `001` | FIXED |
| 4 | TC-002472 (1 purchase) | `TD-TC-002472.json` line 47 | `01` | TC-001054 | [TD-TC-001054.json](../5-11-out/american_express_credit/test_data/TD-TC-001054.json#L52) line 52 | `001` | FIXED |
| 5 | TC-002473 (1 purchase) | `TD-TC-002473.json` line 47 | `01` | TC-001055 | [TD-TC-001055.json](../5-11-out/american_express_credit/test_data/TD-TC-001055.json#L52) line 52 | `001` | FIXED |
| 6 | TC-002474 (1 purchase) | `TD-TC-002474.step1.purchase.json` line 47 | `01` | TC-001056 | [TD-TC-001056.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 7 | TC-002474 (2 split tender) | `TD-TC-002474.step2.split-tender.json` line 47 | `01` | TC-001056 | [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L52) line 52 | `001` | FIXED |
| 8 | TC-002475 (1 purchase) | `TD-TC-002475.step1.purchase.json` line 47 | `01` | TC-001057 | [TD-TC-001057.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001057.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 9 | TC-002476 (1 purchase) | `TD-TC-002476.step1.purchase.json` line 47 | `01` | TC-001058 | [TD-TC-001058.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001058.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 10 | TC-002477 (1 purchase) | `TD-TC-002477.json` line 47 | `01` | TC-001059 | [TD-TC-001059.json](../5-11-out/american_express_credit/test_data/TD-TC-001059.json#L52) line 52 | `001` | FIXED |
| 11 | TC-002478 (1 purchase) | `TD-TC-002478.step1.purchase.json` line 47 | `01` | TC-001060 | [TD-TC-001060.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001060.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 12 | TC-002479 (1 purchase) | `TD-TC-002479.step1.purchase.json` line 47 | `01` | TC-001061 | [TD-TC-001061.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001061.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 13 | TC-002480 (1 purchase) | `TD-TC-002480.step1.purchase.json` line 47 | `01` | TC-001062 | [TD-TC-001062.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001062.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 14 | TC-002481 (1 purchase) | `TD-TC-002481.step1.purchase.json` line 47 | `01` | TC-001063 | [TD-TC-001063.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001063.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 15 | TC-002482 (1 purchase) | `TD-TC-002482.step1.purchase.json` line 47 | `01` | TC-001064 | [TD-TC-001064.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001064.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 16 | TC-002482 (3 next transaction) | `TD-TC-002482.step3.next-transaction.json` line 47 | `01` | TC-001064 | [TD-TC-001064.step3.next-transaction.json](../5-11-out/american_express_credit/test_data/TD-TC-001064.step3.next-transaction.json#L52) line 52 | `001` | FIXED |
| 17 | TC-002483 (1 purchase) | `TD-TC-002483.step1.purchase.json` line 47 | `01` | TC-001065 | [TD-TC-001065.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001065.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 18 | TC-002484 (1 purchase) | `TD-TC-002484.step1.purchase.json` line 47 | `01` | TC-001066 | [TD-TC-001066.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001066.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 19 | TC-002485 (1 purchase) | `TD-TC-002485.step1.purchase.json` line 47 | `01` | TC-001067 | [TD-TC-001067.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001067.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 20 | TC-002486 (1 purchase) | `TD-TC-002486.step1.purchase.json` line 47 | `01` | TC-001068 | [TD-TC-001068.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 21 | TC-002486 (2 return) | `TD-TC-002486.step2.return.json` line 47 | `01` | TC-001068 | [TD-TC-001068.step2.return.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step2.return.json#L52) line 52 | `001` | FIXED |
| 22 | TC-002487 (1 purchase) | `TD-TC-002487.step1.purchase.json` line 47 | `01` | TC-001069 | [TD-TC-001069.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 23 | TC-002488 (1 purchase) | `TD-TC-002488.step1.purchase.json` line 47 | `01` | TC-001070 | [TD-TC-001070.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001070.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 24 | TC-002489 (1 purchase) | `TD-TC-002489.step1.purchase.json` line 47 | `01` | TC-001071 | [TD-TC-001071.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001071.step1.purchase.json#L52) line 52 | `001` | FIXED |
| 25 | TC-002490 (1 purchase) | `TD-TC-002490.json` line 63 | `01` | TC-001072 | [TD-TC-001072.json](../5-11-out/american_express_credit/test_data/TD-TC-001072.json#L68) line 68 | `001` | FIXED |

… 91 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-02"></a>

### CD-02 – EMV Additional Information (Element 118) is not EMVYES / EMVNOT – FIXED

| | |
|---|---|
| Field | E191/E192/E118, Seg 130 (source ID A-02) |
| Previous run | 94 test cases / 116 files failing (log: 94 / 116) |
| Previously failing files in the new run | 114 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `EMVYES / length 006` (114 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: `TD-TC-002471.json` line 52 → `UUZPL4ZEX557C41T…VSBRB1RXVS (984 cha…length 984 (52 chars)`. New (TC-001053): [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L57) line 57 → `EMVYES / length 006`<br>Expected: EMVYES or EMVNOT / length 006 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002492 (1 timeout reversal) | `TD-TC-002492.json` line 52 | `UUZPL4ZEX557C41T…VSBRB1RXVS (984 chars) / length 984` | TC-001074 | [TD-TC-001074.json](../5-11-out/american_express_credit/test_data/TD-TC-001074.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-002526 (1 timeout reversal) | `TD-TC-002526.json` line 52 | `FLGXWU00ESWZREHM…YA1ZICI8OV (984 chars) / length 984` | TC-001107 | [TD-TC-001107.json](../5-11-out/american_express_credit/test_data/TD-TC-001107.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-002471 (1 purchase) | `TD-TC-002471.json` line 52 | `UUZPL4ZEX557C41T…VSBRB1RXVS (984 chars) / length 984` | TC-001053 | [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 4 | TC-002472 (1 purchase) | `TD-TC-002472.json` line 52 | `UUZPL4ZEX557C41T…VSBRB1RXVS (984 chars) / length 984` | TC-001054 | [TD-TC-001054.json](../5-11-out/american_express_credit/test_data/TD-TC-001054.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 5 | TC-002473 (1 purchase) | `TD-TC-002473.json` line 52 | `UUZPL4ZEX557C41T…VSBRB1RXVS (984 chars) / length 984` | TC-001055 | [TD-TC-001055.json](../5-11-out/american_express_credit/test_data/TD-TC-001055.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 6 | TC-002474 (1 purchase) | `TD-TC-002474.step1.purchase.json` line 52 | `4XVOHJV4HGC614XU…QYKWX1RA34 (984 chars) / length 984` | TC-001056 | [TD-TC-001056.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 7 | TC-002474 (2 split tender) | `TD-TC-002474.step2.split-tender.json` line 52 | `VS077FHWAIR8WULT…1CD0X1UNB0 (984 chars) / length 984` | TC-001056 | [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 8 | TC-002475 (1 purchase) | `TD-TC-002475.step1.purchase.json` line 52 | `73EU4QSCCEZTQXAZ…R4Y0D7NTCJ (984 chars) / length 984` | TC-001057 | [TD-TC-001057.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001057.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 9 | TC-002476 (1 purchase) | `TD-TC-002476.step1.purchase.json` line 52 | `EZ71EF2ZQLMF7Q40…V0NCGAX7NW (984 chars) / length 984` | TC-001058 | [TD-TC-001058.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001058.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 10 | TC-002477 (1 purchase) | `TD-TC-002477.json` line 52 | `UUZPL4ZEX557C41T…VSBRB1RXVS (984 chars) / length 984` | TC-001059 | [TD-TC-001059.json](../5-11-out/american_express_credit/test_data/TD-TC-001059.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 11 | TC-002478 (1 purchase) | `TD-TC-002478.step1.purchase.json` line 52 | `4HH8I2CJ24VJSJ5O…LEQCX0T32Z (984 chars) / length 984` | TC-001060 | [TD-TC-001060.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001060.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 12 | TC-002479 (1 purchase) | `TD-TC-002479.step1.purchase.json` line 52 | `3WBE749HYRUQG7X0…RLJ5UHZ3W3 (984 chars) / length 984` | TC-001061 | [TD-TC-001061.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001061.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 13 | TC-002480 (1 purchase) | `TD-TC-002480.step1.purchase.json` line 52 | `5ZTGQLE08BYZ8G0O…6XSHND9NWY (984 chars) / length 984` | TC-001062 | [TD-TC-001062.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001062.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 14 | TC-002481 (1 purchase) | `TD-TC-002481.step1.purchase.json` line 52 | `TC5MYEF4NYFT4GNH…ZB6YKX6OQM (984 chars) / length 984` | TC-001063 | [TD-TC-001063.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001063.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 15 | TC-002482 (1 purchase) | `TD-TC-002482.step1.purchase.json` line 52 | `046V1F2CO33J6BPC…G677WC5RCK (984 chars) / length 984` | TC-001064 | [TD-TC-001064.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001064.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 16 | TC-002482 (3 next transaction) | `TD-TC-002482.step3.next-transaction.json` line 52 | `EMC3GEA45VSLGCN3…7VUKFFGAS7 (984 chars) / length 984` | TC-001064 | [TD-TC-001064.step3.next-transaction.json](../5-11-out/american_express_credit/test_data/TD-TC-001064.step3.next-transaction.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 17 | TC-002483 (1 purchase) | `TD-TC-002483.step1.purchase.json` line 52 | `KF7UXAG7GWF9SMLS…SFVH1TS9T3 (984 chars) / length 984` | TC-001065 | [TD-TC-001065.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001065.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 18 | TC-002484 (1 purchase) | `TD-TC-002484.step1.purchase.json` line 52 | `0TH23SY83FN8V49H…3WXSC2O8DZ (984 chars) / length 984` | TC-001066 | [TD-TC-001066.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001066.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 19 | TC-002485 (1 purchase) | `TD-TC-002485.step1.purchase.json` line 52 | `BN847NQH5KLVT1LJ…TEHJ9Y4HR3 (984 chars) / length 984` | TC-001067 | [TD-TC-001067.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001067.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 20 | TC-002486 (1 purchase) | `TD-TC-002486.step1.purchase.json` line 52 | `O1LS23AIPVCO15Q5…77FLVRFWK2 (984 chars) / length 984` | TC-001068 | [TD-TC-001068.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 21 | TC-002486 (2 return) | `TD-TC-002486.step2.return.json` line 52 | `H4DJ7IZHBO3FHTHA…J6X28R0898 (984 chars) / length 984` | TC-001068 | [TD-TC-001068.step2.return.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step2.return.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 22 | TC-002487 (1 purchase) | `TD-TC-002487.step1.purchase.json` line 52 | `AMKI11EP6GIAS6HW…UNIBE47EVD (984 chars) / length 984` | TC-001069 | [TD-TC-001069.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 23 | TC-002488 (1 purchase) | `TD-TC-002488.step1.purchase.json` line 52 | `OQAUSO3K0YURVB4O…2JCT0TW2P9 (984 chars) / length 984` | TC-001070 | [TD-TC-001070.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001070.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 24 | TC-002489 (1 purchase) | `TD-TC-002489.step1.purchase.json` line 52 | `RSMOSBBFGTXEHCZ7…Q2DUAPCO3V (984 chars) / length 984` | TC-001071 | [TD-TC-001071.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001071.step1.purchase.json#L57) line 57 | `EMVYES / length 006` | FIXED |
| 25 | TC-002490 (1 purchase) | `TD-TC-002490.json` line 68 | `UUZPL4ZEX557C41T…VSBRB1RXVS (984 chars) / length 984` | TC-001072 | [TD-TC-001072.json](../5-11-out/american_express_credit/test_data/TD-TC-001072.json#L73) line 73 | `EMVYES / length 006` | FIXED |

… 91 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-03"></a>

### CD-03 – Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Table 030, Seg 111 |
| Previous run | 140 test cases / 204 files failing (log: 140 / 204) |
| Previously failing files in the new run | 202 awaiting client input, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `030 sent, length 010, value empty` (202 files); `Segment 130 is no longer sent on this step (no longer an EMV request)` (2 files) |
| Notes | value listed in awaiting_inputs; length 010 is declared for the value to come, so the file cannot be sent until the code is filled in (202); Segment 130 is no longer sent on this step (no longer an EMV request) (2) |
| Check it yourself | Previous: `TD-TC-002471.json` line 30 → `not sent (tables: 005=051, 031=12)`. New (TC-001053): [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L44) line 44 → `030 sent, length 010, value empty`<br>Expected: 10 digits from the client's certified terminal |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002471 (1 purchase) | `TD-TC-002471.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001053 | [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-002472 (1 purchase) | `TD-TC-002472.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001054 | [TD-TC-001054.json](../5-11-out/american_express_credit/test_data/TD-TC-001054.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-002473 (1 purchase) | `TD-TC-002473.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001055 | [TD-TC-001055.json](../5-11-out/american_express_credit/test_data/TD-TC-001055.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-002474 (1 purchase) | `TD-TC-002474.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001056 | [TD-TC-001056.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-002474 (2 split tender) | `TD-TC-002474.step2.split-tender.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001056 | [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-002475 (1 purchase) | `TD-TC-002475.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001057 | [TD-TC-001057.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001057.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-002476 (1 purchase) | `TD-TC-002476.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001058 | [TD-TC-001058.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001058.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-002477 (1 purchase) | `TD-TC-002477.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001059 | [TD-TC-001059.json](../5-11-out/american_express_credit/test_data/TD-TC-001059.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 9 | TC-002478 (1 purchase) | `TD-TC-002478.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001060 | [TD-TC-001060.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001060.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 10 | TC-002479 (1 purchase) | `TD-TC-002479.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001061 | [TD-TC-001061.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001061.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 11 | TC-002480 (1 purchase) | `TD-TC-002480.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001062 | [TD-TC-001062.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001062.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 12 | TC-002481 (1 purchase) | `TD-TC-002481.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001063 | [TD-TC-001063.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001063.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 13 | TC-002482 (1 purchase) | `TD-TC-002482.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001064 | [TD-TC-001064.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001064.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 14 | TC-002482 (3 next transaction) | `TD-TC-002482.step3.next-transaction.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001064 | [TD-TC-001064.step3.next-transaction.json](../5-11-out/american_express_credit/test_data/TD-TC-001064.step3.next-transaction.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 15 | TC-002483 (1 purchase) | `TD-TC-002483.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001065 | [TD-TC-001065.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001065.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 16 | TC-002484 (1 purchase) | `TD-TC-002484.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001066 | [TD-TC-001066.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001066.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 17 | TC-002485 (1 purchase) | `TD-TC-002485.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001067 | [TD-TC-001067.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001067.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 18 | TC-002486 (1 purchase) | `TD-TC-002486.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001068 | [TD-TC-001068.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 19 | TC-002486 (2 return) | `TD-TC-002486.step2.return.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001068 | [TD-TC-001068.step2.return.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step2.return.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 20 | TC-002487 (1 purchase) | `TD-TC-002487.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001069 | [TD-TC-001069.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 21 | TC-002488 (1 purchase) | `TD-TC-002488.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001070 | [TD-TC-001070.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001070.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 22 | TC-002489 (1 purchase) | `TD-TC-002489.step1.purchase.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001071 | [TD-TC-001071.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001071.step1.purchase.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 23 | TC-002490 (1 purchase) | `TD-TC-002490.json` line 41 | `not sent (tables: 005=051, 031=12, 012=M)` | TC-001072 | [TD-TC-001072.json](../5-11-out/american_express_credit/test_data/TD-TC-001072.json#L60) line 60 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 24 | TC-002491 (1 purchase) | `TD-TC-002491.step1.purchase.json` line 41 | `not sent (tables: 005=051, 031=12, 012=M)` | TC-001073 | [TD-TC-001073.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001073.step1.purchase.json#L60) line 60 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 25 | TC-002491 (2 split tender) | `TD-TC-002491.step2.split-tender.json` line 30 | `not sent (tables: 005=051, 031=12)` | TC-001073 | [TD-TC-001073.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001073.step2.split-tender.json#L44) line 44 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |

… 179 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-04"></a>

### CD-04 – Credential-on-File indicators (Tables 032, 049, 056) missing – FIXED

| | |
|---|---|
| Field | E113 Tables 032/049/056, Seg 111 |
| Previous run | 46 test cases / 88 files failing (log: 46 / 88) |
| Previously failing files in the new run | 88 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `032-08 C/I 08001C; 049-02 021C` (88 files) |
| Check it yourself | Previous: `TD-TC-002675.json` line 30 → `032-08 C/I missing; 049-02 missing`. New (TC-001251): [TD-TC-001251.json](../5-11-out/american_express_credit/test_data/TD-TC-001251.json#L49) line 49 → `032-08 C/I 08001C; 049-02 021C`<br>Expected: 032-08 = C (I on the storing step), 049-02 = C/M |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002675 (1 purchase) | `TD-TC-002675.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001251 | [TD-TC-001251.json](../5-11-out/american_express_credit/test_data/TD-TC-001251.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 2 | TC-002676 (1 purchase) | `TD-TC-002676.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001252 | [TD-TC-001252.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001252.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 3 | TC-002676 (2 reversal) | `TD-TC-002676.step2.reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001252 | [TD-TC-001252.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001252.step2.reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 4 | TC-002677 (1 purchase) | `TD-TC-002677.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001253 | [TD-TC-001253.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001253.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 5 | TC-002677 (2 timeout reversal) | `TD-TC-002677.step2.timeout-reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001253 | [TD-TC-001253.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001253.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 6 | TC-002678 (1 purchase) | `TD-TC-002678.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001254 | [TD-TC-001254.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001254.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 7 | TC-002678 (2 timeout reversal) | `TD-TC-002678.step2.timeout-reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001254 | [TD-TC-001254.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001254.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 8 | TC-002679 (1 purchase) | `TD-TC-002679.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001255 | [TD-TC-001255.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001255.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 9 | TC-002679 (2 timeout reversal) | `TD-TC-002679.step2.timeout-reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001255 | [TD-TC-001255.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001255.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 10 | TC-002680 (1 purchase) | `TD-TC-002680.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001256 | [TD-TC-001256.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001256.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 11 | TC-002680 (2 timeout reversal) | `TD-TC-002680.step2.timeout-reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001256 | [TD-TC-001256.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001256.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 12 | TC-002680 (3 next transaction) | `TD-TC-002680.step3.next-transaction.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001256 | [TD-TC-001256.step3.next-transaction.json](../5-11-out/american_express_credit/test_data/TD-TC-001256.step3.next-transaction.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 13 | TC-002681 (1 purchase) | `TD-TC-002681.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001257 | [TD-TC-001257.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001257.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 14 | TC-002681 (2 timeout reversal) | `TD-TC-002681.step2.timeout-reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001257 | [TD-TC-001257.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001257.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 15 | TC-002681 (3 tor retry) | `TD-TC-002681.step3.tor-retry.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001257 | [TD-TC-001257.step3.tor-retry.json](../5-11-out/american_express_credit/test_data/TD-TC-001257.step3.tor-retry.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 16 | TC-002681 (4 tor retry) | `TD-TC-002681.step4.tor-retry.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001257 | [TD-TC-001257.step4.tor-retry.json](../5-11-out/american_express_credit/test_data/TD-TC-001257.step4.tor-retry.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 17 | TC-002682 (1 purchase) | `TD-TC-002682.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001258 | [TD-TC-001258.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001258.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 18 | TC-002683 (1 purchase) | `TD-TC-002683.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001259 | [TD-TC-001259.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001259.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 19 | TC-002683 (2 reversal) | `TD-TC-002683.step2.reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001259 | [TD-TC-001259.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001259.step2.reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 20 | TC-002684 (1 purchase) | `TD-TC-002684.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001260 | [TD-TC-001260.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001260.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 21 | TC-002684 (2 return) | `TD-TC-002684.step2.return.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001260 | [TD-TC-001260.step2.return.json](../5-11-out/american_express_credit/test_data/TD-TC-001260.step2.return.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 22 | TC-002685 (1 purchase) | `TD-TC-002685.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001261 | [TD-TC-001261.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001261.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 23 | TC-002685 (2 timeout reversal) | `TD-TC-002685.step2.timeout-reversal.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001261 | [TD-TC-001261.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001261.step2.timeout-reversal.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 24 | TC-002686 (1 purchase) | `TD-TC-002686.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001262 | [TD-TC-001262.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001262.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |
| 25 | TC-002687 (1 purchase) | `TD-TC-002687.step1.purchase.json` line 30 | `032-08 C/I missing; 049-02 missing` | TC-001263 | [TD-TC-001263.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001263.step1.purchase.json#L49) line 49 | `032-08 C/I 08001C; 049-02 021C` | FIXED |

… 63 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-05"></a>

### CD-05 – Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data – FIXED

| | |
|---|---|
| Field | E12 + Table 005 (source ID A-03, A-03b) |
| Previous run | 44 test cases / 54 files failing (log: 44 / 54) |
| Previously failing files in the new run | 54 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `0530 with entry mode 79` (5 files); `0731 with entry mode 79` (3 files); `0928 with entry mode 79` (3 files) |
| Check it yourself | Previous: `TD-TC-002643.json` line 15 → `310720162917830 with entry mode 79`. New (TC-001220): [TD-TC-001220.json](../5-11-out/american_express_credit/test_data/TD-TC-001220.json#L15) line 15 → `0731 with entry mode 79`<br>Expected: expiry MMYY only |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002643 (1 purchase) | `TD-TC-002643.json` line 15 | `310720162917830 with entry mode 79` | TC-001220 | [TD-TC-001220.json](../5-11-out/american_express_credit/test_data/TD-TC-001220.json#L15) line 15 | `0731 with entry mode 79` | FIXED |
| 2 | TC-002644 (1 purchase) | `TD-TC-002644.json` line 15 | `310720162917830 with entry mode 79` | TC-001221 | [TD-TC-001221.json](../5-11-out/american_express_credit/test_data/TD-TC-001221.json#L15) line 15 | `0731 with entry mode 79` | FIXED |
| 3 | TC-002645 (1 purchase) | `TD-TC-002645.step1.purchase.json` line 15 | `280720131342660 with entry mode 79` | TC-001222 | [TD-TC-001222.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001222.step1.purchase.json#L15) line 15 | `0728 with entry mode 79` | FIXED |
| 4 | TC-002645 (2 split tender) | `TD-TC-002645.step2.split-tender.json` line 15 | `280320103664349 with entry mode 79` | TC-001222 | [TD-TC-001222.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001222.step2.split-tender.json#L15) line 15 | `0328 with entry mode 79` | FIXED |
| 5 | TC-002646 (1 purchase) | `TD-TC-002646.step1.purchase.json` line 15 | `281120194408221 with entry mode 79` | TC-001223 | [TD-TC-001223.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001223.step1.purchase.json#L15) line 15 | `1128 with entry mode 79` | FIXED |
| 6 | TC-002647 (1 purchase) | `TD-TC-002647.step1.purchase.json` line 15 | `290520113169479 with entry mode 79` | TC-001224 | [TD-TC-001224.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001224.step1.purchase.json#L15) line 15 | `0529 with entry mode 79` | FIXED |
| 7 | TC-002648 (1 purchase) | `TD-TC-002648.json` line 15 | `281220140152334 with entry mode 79` | TC-001225 | [TD-TC-001225.json](../5-11-out/american_express_credit/test_data/TD-TC-001225.json#L15) line 15 | `1228 with entry mode 79` | FIXED |
| 8 | TC-002649 (1 purchase) | `TD-TC-002649.step1.purchase.json` line 15 | `280920166397326 with entry mode 79` | TC-001226 | [TD-TC-001226.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001226.step1.purchase.json#L15) line 15 | `0928 with entry mode 79` | FIXED |
| 9 | TC-002650 (1 purchase) | `TD-TC-002650.step1.purchase.json` line 15 | `270220179517972 with entry mode 79` | TC-001227 | [TD-TC-001227.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001227.step1.purchase.json#L15) line 15 | `0227 with entry mode 79` | FIXED |
| 10 | TC-002651 (1 purchase) | `TD-TC-002651.step1.purchase.json` line 15 | `270720105804330 with entry mode 79` | TC-001228 | [TD-TC-001228.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001228.step1.purchase.json#L15) line 15 | `0727 with entry mode 79` | FIXED |
| 11 | TC-002652 (1 purchase) | `TD-TC-002652.step1.purchase.json` line 15 | `311120186020898 with entry mode 79` | TC-001229 | [TD-TC-001229.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001229.step1.purchase.json#L15) line 15 | `1131 with entry mode 79` | FIXED |
| 12 | TC-002653 (1 purchase) | `TD-TC-002653.step1.purchase.json` line 15 | `321020167015542 with entry mode 79` | TC-001230 | [TD-TC-001230.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001230.step1.purchase.json#L15) line 15 | `1032 with entry mode 79` | FIXED |
| 13 | TC-002653 (3 next transaction) | `TD-TC-002653.step3.next-transaction.json` line 15 | `321020167015542 with entry mode 79` | TC-001230 | [TD-TC-001230.step3.next-transaction.json](../5-11-out/american_express_credit/test_data/TD-TC-001230.step3.next-transaction.json#L15) line 15 | `1032 with entry mode 79` | FIXED |
| 14 | TC-002654 (1 purchase) | `TD-TC-002654.step1.purchase.json` line 15 | `290520125913268 with entry mode 79` | TC-001231 | [TD-TC-001231.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001231.step1.purchase.json#L15) line 15 | `0529 with entry mode 79` | FIXED |
| 15 | TC-002655 (1 purchase) | `TD-TC-002655.step1.purchase.json` line 15 | `300820145527994 with entry mode 79` | TC-001232 | [TD-TC-001232.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001232.step1.purchase.json#L15) line 15 | `0830 with entry mode 79` | FIXED |
| 16 | TC-002656 (1 purchase) | `TD-TC-002656.step1.purchase.json` line 15 | `300920179035073 with entry mode 79` | TC-001233 | [TD-TC-001233.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001233.step1.purchase.json#L15) line 15 | `0930 with entry mode 79` | FIXED |
| 17 | TC-002657 (1 purchase) | `TD-TC-002657.step1.purchase.json` line 15 | `291120194752901 with entry mode 79` | TC-001234 | [TD-TC-001234.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001234.step1.purchase.json#L15) line 15 | `1129 with entry mode 79` | FIXED |
| 18 | TC-002657 (2 return) | `TD-TC-002657.step2.return.json` line 15 | `291120194752901 with entry mode 79` | TC-001234 | [TD-TC-001234.step2.return.json](../5-11-out/american_express_credit/test_data/TD-TC-001234.step2.return.json#L15) line 15 | `1129 with entry mode 79` | FIXED |
| 19 | TC-002658 (1 purchase) | `TD-TC-002658.step1.purchase.json` line 15 | `310820192763930 with entry mode 79` | TC-001235 | [TD-TC-001235.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001235.step1.purchase.json#L15) line 15 | `0831 with entry mode 79` | FIXED |
| 20 | TC-002659 (1 purchase) | `TD-TC-002659.step1.purchase.json` line 15 | `310920151486638 with entry mode 79` | TC-001236 | [TD-TC-001236.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001236.step1.purchase.json#L15) line 15 | `0931 with entry mode 79` | FIXED |
| 21 | TC-002660 (1 purchase) | `TD-TC-002660.step1.purchase.json` line 15 | `290920185082152 with entry mode 79` | TC-001237 | [TD-TC-001237.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001237.step1.purchase.json#L15) line 15 | `0929 with entry mode 79` | FIXED |
| 22 | TC-002661 (1 timeout reversal) | `TD-TC-002661.json` line 15 | `270120116895779 with entry mode 79` | TC-001238 | [TD-TC-001238.json](../5-11-out/american_express_credit/test_data/TD-TC-001238.json#L15) line 15 | `2701 with entry mode 79` | FIXED |
| 23 | TC-002662 (1 purchase) | `TD-TC-002662.step1.purchase.json` line 15 | `300720104853396 with entry mode 79` | TC-001239 | [TD-TC-001239.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001239.step1.purchase.json#L15) line 15 | `0730 with entry mode 79` | FIXED |
| 24 | TC-002663 (1 purchase) | `TD-TC-002663.step1.purchase.json` line 15 | `320420187827232 with entry mode 79` | TC-001240 | [TD-TC-001240.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001240.step1.purchase.json#L15) line 15 | `0432 with entry mode 79` | FIXED |
| 25 | TC-002665 (1 purchase) | `TD-TC-002665.step1.purchase.json` line 15 | `310420110137195 with entry mode 79` | TC-001242 | [TD-TC-001242.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001242.step1.purchase.json#L15) line 15 | `0431 with entry mode 79` | FIXED |

… 29 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-06"></a>

### CD-06 – Unmatched time-out reversal carries full track data – FIXED

| | |
|---|---|
| Field | E12 |
| Previous run | 5 test cases / 5 files failing (log: 5 / 5) |
| Previously failing files in the new run | 5 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `2812` (1 file); `3104` (1 file); `3006` (1 file) |
| Check it yourself | Previous: `TD-TC-002492.json` line 15 → `321020112631516`. New (TC-001074): [TD-TC-001074.json](../5-11-out/american_express_credit/test_data/TD-TC-001074.json#L15) line 15 → `2812`<br>Expected: 2812 (expiry YYMM only) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002492 (1 timeout reversal) | `TD-TC-002492.json` line 15 | `321020112631516` | TC-001074 | [TD-TC-001074.json](../5-11-out/american_express_credit/test_data/TD-TC-001074.json#L15) line 15 | `2812` | FIXED |
| 2 | TC-002526 (1 timeout reversal) | `TD-TC-002526.json` line 15 | `280520167110159` | TC-001107 | [TD-TC-001107.json](../5-11-out/american_express_credit/test_data/TD-TC-001107.json#L15) line 15 | `3104` | FIXED |
| 3 | TC-002559 (1 timeout reversal) | `TD-TC-002559.json` line 15 | `280510161470700` | TC-001139 | [TD-TC-001139.json](../5-11-out/american_express_credit/test_data/TD-TC-001139.json#L15) line 15 | `3006` | FIXED |
| 4 | TC-002592 (1 timeout reversal) | `TD-TC-002592.json` line 15 | `291220162638600` | TC-001171 | [TD-TC-001171.json](../5-11-out/american_express_credit/test_data/TD-TC-001171.json#L15) line 15 | `2912` | FIXED |
| 5 | TC-002720 (1 timeout reversal) | `TD-TC-002720.json` line 15 | `300720195582237` | TC-001295 | [TD-TC-001295.json](../5-11-out/american_express_credit/test_data/TD-TC-001295.json#L15) line 15 | `3007` | FIXED |

---

<a id="cd-07"></a>

### CD-07 – Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it – FIXED

| | |
|---|---|
| Field | E58 / E84, Seg 100 (source ID A-06) |
| Previous run | 31 test cases / 31 files failing (log: 31 / 31) |
| Previously failing files in the new run | 31 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `SegmentLength 082; provisional amount 750` (21 files); `SegmentLength 087; provisional amount 750` (8 files); `SegmentLength 076; provisional amount 750` (2 files) |
| Check it yourself | Previous: `TD-TC-002474.step2.split-tender.json` line 7 → `SegmentLength 092; provisional amount 74141657`. New (TC-001056): [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L7) line 7 → `SegmentLength 087; provisional amount 750`<br>Expected: Segment Length for an amount of at most 4 digits (085–088) or computed at run time; provisional amount ≤ 1500 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002474 (2 split tender) | `TD-TC-002474.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 74141657` | TC-001056 | [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 2 | TC-002475 (2 reversal) | `TD-TC-002475.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 31607415` | TC-001057 | [TD-TC-001057.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001057.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 3 | TC-002491 (2 split tender) | `TD-TC-002491.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 34217483` | TC-001073 | [TD-TC-001073.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001073.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 4 | TC-002508 (2 split tender) | `TD-TC-002508.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 84966759` | TC-001089 | [TD-TC-001089.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001089.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 5 | TC-002509 (2 reversal) | `TD-TC-002509.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 84482775` | TC-001090 | [TD-TC-001090.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001090.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 6 | TC-002525 (2 split tender) | `TD-TC-002525.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 31402325` | TC-001106 | [TD-TC-001106.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001106.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 7 | TC-002541 (2 split tender) | `TD-TC-002541.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 38371908` | TC-001121 | [TD-TC-001121.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001121.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 8 | TC-002542 (2 reversal) | `TD-TC-002542.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 06008344` | TC-001122 | [TD-TC-001122.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001122.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 9 | TC-002558 (2 split tender) | `TD-TC-002558.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 30220906` | TC-001138 | [TD-TC-001138.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001138.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 10 | TC-002576 (2 split tender) | `TD-TC-002576.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 54525183` | TC-001155 | [TD-TC-001155.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001155.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 11 | TC-002577 (2 reversal) | `TD-TC-002577.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 37114694` | TC-001156 | [TD-TC-001156.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001156.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 12 | TC-002609 (2 split tender) | `TD-TC-002609.step2.split-tender.json` line 7 | `SegmentLength 081; provisional amount 48621242` | TC-001187 | [TD-TC-001187.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001187.step2.split-tender.json#L7) line 7 | `SegmentLength 076; provisional amount 750` | FIXED |
| 13 | TC-002610 (2 reversal) | `TD-TC-002610.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 41110171` | TC-001188 | [TD-TC-001188.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001188.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 14 | TC-002645 (2 split tender) | `TD-TC-002645.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 15681335` | TC-001222 | [TD-TC-001222.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001222.step2.split-tender.json#L7) line 7 | `SegmentLength 076; provisional amount 750` | FIXED |
| 15 | TC-002646 (2 reversal) | `TD-TC-002646.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 04912081` | TC-001223 | [TD-TC-001223.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001223.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 16 | TC-002704 (2 split tender) | `TD-TC-002704.step2.split-tender.json` line 7 | `SegmentLength 092; provisional amount 74475683` | TC-001279 | [TD-TC-001279.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001279.step2.split-tender.json#L7) line 7 | `SegmentLength 087; provisional amount 750` | FIXED |
| 17 | TC-002705 (2 reversal) | `TD-TC-002705.step2.reversal.json` line 7 | `SegmentLength 087; provisional amount 68843103` | TC-001280 | [TD-TC-001280.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001280.step2.reversal.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 18 | TC-002747 (2 completion) | `TD-TC-002747.step2.completion.json` line 7 | `SegmentLength 087; provisional amount 32070075` | TC-001323 | [TD-TC-001323.step2.completion.json](../5-11-out/american_express_credit/test_data/TD-TC-001323.step2.completion.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 19 | TC-002748 (2 cancellation) | `TD-TC-002748.step2.cancellation.json` line 7 | `SegmentLength 087; provisional amount 90399681` | TC-001324 | [TD-TC-001324.step2.cancellation.json](../5-11-out/american_express_credit/test_data/TD-TC-001324.step2.cancellation.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 20 | TC-002760 (2 completion) | `TD-TC-002760.step2.completion.json` line 7 | `SegmentLength 087; provisional amount 50593435` | TC-001339 | [TD-TC-001339.step2.completion.json](../5-11-out/american_express_credit/test_data/TD-TC-001339.step2.completion.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 21 | TC-002761 (2 cancellation) | `TD-TC-002761.step2.cancellation.json` line 7 | `SegmentLength 087; provisional amount 29668158` | TC-001340 | [TD-TC-001340.step2.cancellation.json](../5-11-out/american_express_credit/test_data/TD-TC-001340.step2.cancellation.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 22 | TC-002772 (2 completion) | `TD-TC-002772.step2.completion.json` line 7 | `SegmentLength 087; provisional amount 23309759` | TC-001354 | [TD-TC-001354.step2.completion.json](../5-11-out/american_express_credit/test_data/TD-TC-001354.step2.completion.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 23 | TC-002773 (2 cancellation) | `TD-TC-002773.step2.cancellation.json` line 7 | `SegmentLength 087; provisional amount 42472814` | TC-001355 | [TD-TC-001355.step2.cancellation.json](../5-11-out/american_express_credit/test_data/TD-TC-001355.step2.cancellation.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 24 | TC-002787 (2 completion) | `TD-TC-002787.step2.completion.json` line 7 | `SegmentLength 087; provisional amount 53028711` | TC-001372 | [TD-TC-001372.step2.completion.json](../5-11-out/american_express_credit/test_data/TD-TC-001372.step2.completion.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |
| 25 | TC-002788 (2 cancellation) | `TD-TC-002788.step2.cancellation.json` line 7 | `SegmentLength 087; provisional amount 99721446` | TC-001373 | [TD-TC-001373.step2.cancellation.json](../5-11-out/american_express_credit/test_data/TD-TC-001373.step2.cancellation.json#L7) line 7 | `SegmentLength 082; provisional amount 750` | FIXED |

… 6 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-08"></a>

### CD-08 – Split-tender card B chip amount (Tag 9F02) is the full original amount – FIXED

| | |
|---|---|
| Field | Tag 9F02, Seg 130 (source ID A-07) |
| Previous run | 4 test cases / 4 files failing (log: 4 / 4) |
| Previously failing files in the new run | 4 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F02 = 000000000750` (4 files) |
| Check it yourself | Previous: `TD-TC-002474.step2.split-tender.json` line 49 → `9F02 = 000000001500`. New (TC-001056): [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L54) line 54 → `9F02 = 000000000750`<br>Expected: card B's remainder (< 000000001500) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002474 (2 split tender) | `TD-TC-002474.step2.split-tender.json` line 49 | `9F02 = 000000001500` | TC-001056 | [TD-TC-001056.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001056.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |
| 2 | TC-002491 (2 split tender) | `TD-TC-002491.step2.split-tender.json` line 49 | `9F02 = 000000001500` | TC-001073 | [TD-TC-001073.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001073.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |
| 3 | TC-002508 (2 split tender) | `TD-TC-002508.step2.split-tender.json` line 49 | `9F02 = 000000001500` | TC-001089 | [TD-TC-001089.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001089.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |
| 4 | TC-002525 (2 split tender) | `TD-TC-002525.step2.split-tender.json` line 49 | `9F02 = 000000001500` | TC-001106 | [TD-TC-001106.step2.split-tender.json](../5-11-out/american_express_credit/test_data/TD-TC-001106.step2.split-tender.json#L54) line 54 | `9F02 = 000000000750` | FIXED |

---

<a id="cd-09"></a>

### CD-09 – Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 – FIXED

| | |
|---|---|
| Field | E84, Seg 102 |
| Previous run | 6 test cases / 6 files failing (log: 6 / 6) |
| Previously failing files in the new run | 6 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `029` (6 files) |
| Check it yourself | Previous: `TD-TC-002490.json` line 29 → `033`. New (TC-001072): [TD-TC-001072.json](../5-11-out/american_express_credit/test_data/TD-TC-001072.json#L29) line 29 → `029`<br>Expected: 029 (12.3: FS after type/length and the last amount, '\' after quantity and unit price) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002490 (1 purchase) | `TD-TC-002490.json` line 29 | `033` | TC-001072 | [TD-TC-001072.json](../5-11-out/american_express_credit/test_data/TD-TC-001072.json#L29) line 29 | `029` | FIXED |
| 2 | TC-002491 (1 purchase) | `TD-TC-002491.step1.purchase.json` line 29 | `033` | TC-001073 | [TD-TC-001073.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001073.step1.purchase.json#L29) line 29 | `029` | FIXED |
| 3 | TC-002524 (1 purchase) | `TD-TC-002524.json` line 29 | `033` | TC-001105 | [TD-TC-001105.json](../5-11-out/american_express_credit/test_data/TD-TC-001105.json#L29) line 29 | `029` | FIXED |
| 4 | TC-002525 (1 purchase) | `TD-TC-002525.step1.purchase.json` line 29 | `033` | TC-001106 | [TD-TC-001106.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001106.step1.purchase.json#L29) line 29 | `029` | FIXED |
| 5 | TC-002557 (1 purchase) | `TD-TC-002557.json` line 29 | `033` | TC-001137 | [TD-TC-001137.json](../5-11-out/american_express_credit/test_data/TD-TC-001137.json#L29) line 29 | `029` | FIXED |
| 6 | TC-002558 (1 purchase) | `TD-TC-002558.step1.purchase.json` line 29 | `033` | TC-001138 | [TD-TC-001138.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001138.step1.purchase.json#L29) line 29 | `029` | FIXED |

---

<a id="cd-10"></a>

### CD-10 – Totals Card Label (Element 13) is 2 bytes instead of 4 – FIXED

| | |
|---|---|
| Field | E13, Seg 105 (source ID A-05) |
| Previous run | 72 test cases / 72 files failing (log: 72 / 72) |
| Previously failing files in the new run | 64 fixed, 8 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `'TE  '` (64 files); `no card-type category is sent any more (nothing occurred…card type) (75 chars)` (8 files) |
| Notes | no card-type category is sent any more (nothing occurred for the card type) (8) |
| Check it yourself | Previous: `TD-TC-002484.step2.totals.json` line 20 → `'TE'`. New (TC-001066): [TD-TC-001066.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001066.step2.totals.json#L20) line 20 → `'TE  '`<br>Expected: 'TE  ' |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002485 (3 totals) | `TD-TC-002485.step3.totals.json` line 20 | `'TE'` | TC-001067 | [TD-TC-001067.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001067.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 2 | TC-002519 (3 totals) | `TD-TC-002519.step3.totals.json` line 20 | `'TE'` | TC-001100 | [TD-TC-001100.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001100.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 3 | TC-002552 (3 totals) | `TD-TC-002552.step3.totals.json` line 20 | `'TE'` | TC-001132 | [TD-TC-001132.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001132.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 4 | TC-002587 (3 totals) | `TD-TC-002587.step3.totals.json` line 20 | `'TE'` | TC-001166 | [TD-TC-001166.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001166.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 5 | TC-002620 (3 totals) | `TD-TC-002620.step3.totals.json` line 20 | `'TE'` | TC-001198 | [TD-TC-001198.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001198.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 6 | TC-002656 (3 totals) | `TD-TC-002656.step3.totals.json` line 20 | `'TE'` | TC-001233 | [TD-TC-001233.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001233.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 7 | TC-002683 (3 totals) | `TD-TC-002683.step3.totals.json` line 20 | `'TE'` | TC-001259 | [TD-TC-001259.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001259.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 8 | TC-002715 (3 totals) | `TD-TC-002715.step3.totals.json` line 20 | `'TE'` | TC-001290 | [TD-TC-001290.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001290.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 9 | TC-002484 (2 totals) | `TD-TC-002484.step2.totals.json` line 20 | `'TE'` | TC-001066 | [TD-TC-001066.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001066.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 10 | TC-002486 (3 totals) | `TD-TC-002486.step3.totals.json` line 20 | `'TE'` | TC-001068 | [TD-TC-001068.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001068.step3.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 11 | TC-002487 (3 totals) | `TD-TC-002487.step3.totals.json` line 20 | `'TE'` | TC-001069 | [TD-TC-001069.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step3.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 12 | TC-002488 (2 totals) | `TD-TC-002488.step2.totals.json` line 20 | `'TE'` | TC-001070 | [TD-TC-001070.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001070.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 13 | TC-002489 (2 totals) | `TD-TC-002489.step2.totals.json` line 20 | `'TE'` | TC-001071 | [TD-TC-001071.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001071.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 14 | TC-002500 (2 totals) | `TD-TC-002500.step2.totals.json` line 20 | `'TE'` | TC-001082 | [TD-TC-001082.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001082.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 15 | TC-002501 (2 totals) | `TD-TC-002501.step2.totals.json` line 20 | `'TE'` | TC-001083 | [TD-TC-001083.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001083.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 16 | TC-002518 (2 totals) | `TD-TC-002518.step2.totals.json` line 20 | `'TE'` | TC-001099 | [TD-TC-001099.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001099.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 17 | TC-002520 (3 totals) | `TD-TC-002520.step3.totals.json` line 20 | `'TE'` | TC-001101 | [TD-TC-001101.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001101.step3.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 18 | TC-002521 (3 totals) | `TD-TC-002521.step3.totals.json` line 20 | `'TE'` | TC-001102 | [TD-TC-001102.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001102.step3.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 19 | TC-002522 (2 totals) | `TD-TC-002522.step2.totals.json` line 20 | `'TE'` | TC-001103 | [TD-TC-001103.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001103.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 20 | TC-002523 (2 totals) | `TD-TC-002523.step2.totals.json` line 20 | `'TE'` | TC-001104 | [TD-TC-001104.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001104.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 21 | TC-002534 (2 totals) | `TD-TC-002534.step2.totals.json` line 20 | `'TE'` | TC-001115 | [TD-TC-001115.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001115.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 22 | TC-002535 (2 totals) | `TD-TC-002535.step2.totals.json` line 20 | `'TE'` | TC-001116 | [TD-TC-001116.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001116.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 23 | TC-002551 (2 totals) | `TD-TC-002551.step2.totals.json` line 20 | `'TE'` | TC-001131 | [TD-TC-001131.step2.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001131.step2.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 24 | TC-002553 (3 totals) | `TD-TC-002553.step3.totals.json` line 20 | `'TE'` | TC-001133 | [TD-TC-001133.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001133.step3.totals.json#L20) line 20 | `'TE  '` | FIXED |
| 25 | TC-002554 (3 totals) | `TD-TC-002554.step3.totals.json` line 20 | `'TE'` | TC-001134 | [TD-TC-001134.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001134.step3.totals.json#L20) line 20 | `'TE  '` | FIXED |

… 47 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-11"></a>

### CD-11 – Totals card-type category sent with count 00000 – FIXED

| | |
|---|---|
| Field | E16, Seg 105 |
| Previous run | 8 test cases / 8 files failing (log: 8 / 8) |
| Previously failing files in the new run | 8 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `category not sent` (8 files) |
| Check it yourself | Previous: `TD-TC-002485.step3.totals.json` line 21 → `Card Label 'TE', count 00000, amount 00000000`. New (TC-001067): [TD-TC-001067.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001067.step3.totals.json#L19) line 19 → `category not sent`<br>Expected: category only when data occurs |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002485 (3 totals) | `TD-TC-002485.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001067 | [TD-TC-001067.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001067.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 2 | TC-002519 (3 totals) | `TD-TC-002519.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001100 | [TD-TC-001100.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001100.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 3 | TC-002552 (3 totals) | `TD-TC-002552.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001132 | [TD-TC-001132.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001132.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 4 | TC-002587 (3 totals) | `TD-TC-002587.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001166 | [TD-TC-001166.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001166.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 5 | TC-002620 (3 totals) | `TD-TC-002620.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001198 | [TD-TC-001198.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001198.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 6 | TC-002656 (3 totals) | `TD-TC-002656.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001233 | [TD-TC-001233.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001233.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 7 | TC-002683 (3 totals) | `TD-TC-002683.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001259 | [TD-TC-001259.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001259.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 8 | TC-002715 (3 totals) | `TD-TC-002715.step3.totals.json` line 21 | `Card Label 'TE', count 00000, amount 00000000` | TC-001290 | [TD-TC-001290.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001290.step3.totals.json#L19) line 19 | `category not sent` | FIXED |

---

<a id="cd-12"></a>

### CD-12 – Expired-card test uses a card that is not expired – FIXED

| | |
|---|---|
| Field | E12 (+ Tag 5F24, TVR) (source ID A-08) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `251220136098417 (expiry YYMM 2512); Tag 5F24 251231; TVR…d bit set) (85 chars)` (1 file); `251220126537857 (expiry YYMM 2512); Tag 5F24 251231; TVR…d bit set) (85 chars)` (1 file) |
| Check it yourself | Previous: `TD-TC-002472.json` line 15 → `280820136098417 (expiry YYMM 2808); …t not set) (89 chars)`. New (TC-001054): [TD-TC-001054.json](../5-11-out/american_express_credit/test_data/TD-TC-001054.json#L15) line 15 → `251220136098417 (expiry YYMM 2512); …d bit set) (85 chars)`<br>Expected: expiry before 2611 (YYMM); Tag 5F24 = same expiry; TVR byte 2 bit 7 set |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002472 (1 purchase) | `TD-TC-002472.json` line 15 | `280820136098417 (expiry YYMM 2808); Tag 5F24 a…t not set) (89 chars)` | TC-001054 | [TD-TC-001054.json](../5-11-out/american_express_credit/test_data/TD-TC-001054.json#L15) line 15 | `251220136098417 (expiry YYMM 2512); Tag 5F24 2…d bit set) (85 chars)` | FIXED |
| 2 | TC-002507 (1 purchase) | `TD-TC-002507.json` line 15 | `300620126537857 (expiry YYMM 3006); Tag 5F24 a…t not set) (89 chars)` | TC-001088 | [TD-TC-001088.json](../5-11-out/american_express_credit/test_data/TD-TC-001088.json#L15) line 15 | `251220126537857 (expiry YYMM 2512); Tag 5F24 2…d bit set) (85 chars)` | FIXED |

---

<a id="cd-14"></a>

### CD-14 – Offline-PIN test has a signature CVM – FIXED

| | |
|---|---|
| Field | Tag 9F34 (source ID A-09) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `9F34 = 440302` (2 files) |
| Check it yourself | Previous: `TD-TC-002473.json` line 49 → `9F34 = 5E0302`. New (TC-001055): [TD-TC-001055.json](../5-11-out/american_express_credit/test_data/TD-TC-001055.json#L54) line 54 → `9F34 = 440302`<br>Expected: CVM code 01 or 04 (e.g. 410302 / 440302) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002473 (1 purchase) | `TD-TC-002473.json` line 49 | `9F34 = 5E0302` | TC-001055 | [TD-TC-001055.json](../5-11-out/american_express_credit/test_data/TD-TC-001055.json#L54) line 54 | `9F34 = 440302` | FIXED |
| 2 | TC-002746 (1 auth only) | `TD-TC-002746.json` line 49 | `9F34 = 5E0302` | TC-001322 | [TD-TC-001322.json](../5-11-out/american_express_credit/test_data/TD-TC-001322.json#L54) line 54 | `9F34 = 440302` | FIXED |

---

<a id="cd-16"></a>

### CD-16 – 'Track 1' test carries Track 2 data – FIXED

| | |
|---|---|
| Field | E2 / E12 |
| Previous run | 2 test cases / 2 files failing (not in the log) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `B341936513370869 / TEST/CARDHOLDER^280810131102429` (1 file); `B347723110975819 / TEST/CARDHOLDER^311210146556691` (1 file) |
| Check it yourself | Previous: `TD-TC-002573.json` line 14 → `341936513370869 / 280810131102429`. New (TC-001151): [TD-TC-001151.json](../5-11-out/american_express_credit/test_data/TD-TC-001151.json#L14) line 14 → `B341936513370869 / TEST/CARDHOLDER^280810131102429`<br>Expected: B<PAN> / NAME^YYMM<service code><discretionary data> (Track 1, at most 76) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002573 (1 purchase) | `TD-TC-002573.json` line 14 | `341936513370869 / 280810131102429` | TC-001151 | [TD-TC-001151.json](../5-11-out/american_express_credit/test_data/TD-TC-001151.json#L14) line 14 | `B341936513370869 / TEST/CARDHOLDER^280810131102429` | FIXED |
| 2 | TC-002784 (1 auth only) | `TD-TC-002784.json` line 14 | `347723110975819 / 311210146556691` | TC-001365 | [TD-TC-001365.json](../5-11-out/american_express_credit/test_data/TD-TC-001365.json#L14) line 14 | `B347723110975819 / TEST/CARDHOLDER^311210146556691` | FIXED |

---

<a id="cd-17"></a>

### CD-17 – Terminal described two ways: Table 005 capability 8 with Table 031 12 – FIXED

| | |
|---|---|
| Field | Seg 111 Tables 005/031 (source ID A-10) |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `005 = 908, 031 = 07` (1 file); `005 = 018, 031 = 07` (1 file) |
| Check it yourself | Previous: `TD-TC-002540.json` line 39 → `005 = 908, 031 = 12`. New (TC-001120): [TD-TC-001120.json](../5-11-out/american_express_credit/test_data/TD-TC-001120.json#L39) line 39 → `005 = 908, 031 = 07`<br>Expected: capability 8 only with 031 = 07 (no chip) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002540 (1 purchase) | `TD-TC-002540.json` line 39 | `005 = 908, 031 = 12` | TC-001120 | [TD-TC-001120.json](../5-11-out/american_express_credit/test_data/TD-TC-001120.json#L39) line 39 | `005 = 908, 031 = 07` | FIXED |
| 2 | TC-002608 (1 purchase) | `TD-TC-002608.json` line 39 | `005 = 018, 031 = 12` | TC-001186 | [TD-TC-001186.json](../5-11-out/american_express_credit/test_data/TD-TC-001186.json#L39) line 39 | `005 = 018, 031 = 07` | FIXED |

---

<a id="cd-18"></a>

### CD-18 – AVS test sends no address data (Table 003) – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Table 003, Seg 111 |
| Previous run | 8 test cases / 8 files failing (log: 8 / 8) |
| Previously failing files in the new run | 8 awaiting client input |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `003 sent, value empty` (8 files) |
| Notes | value listed in awaiting_inputs (8) |
| Check it yourself | Previous: `TD-TC-002625.json` line 30 → `not sent (tables: 005=011, 031=12)`. New (TC-001203): [TD-TC-001203.json](../5-11-out/american_express_credit/test_data/TD-TC-001203.json#L44) line 44 → `003 sent, value empty`<br>Expected: AVS address data from the client |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002625 (1 purchase) | `TD-TC-002625.json` line 30 | `not sent (tables: 005=011, 031=12)` | TC-001203 | [TD-TC-001203.json](../5-11-out/american_express_credit/test_data/TD-TC-001203.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-002626 (1 purchase) | `TD-TC-002626.step1.purchase.json` line 30 | `not sent (tables: 005=011, 031=12)` | TC-001204 | [TD-TC-001204.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001204.step1.purchase.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-002688 (1 purchase) | `TD-TC-002688.json` line 30 | `not sent (tables: 005=101, 031=12)` | TC-001264 | [TD-TC-001264.json](../5-11-out/american_express_credit/test_data/TD-TC-001264.json#L59) line 59 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-002689 (1 purchase) | `TD-TC-002689.step1.purchase.json` line 30 | `not sent (tables: 005=101, 031=12)` | TC-001265 | [TD-TC-001265.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001265.step1.purchase.json#L59) line 59 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-002736 (1 moto purchase) | `TD-TC-002736.json` line 30 | `not sent (tables: 005=011, 031=12)` | TC-001310 | [TD-TC-001310.json](../5-11-out/american_express_credit/test_data/TD-TC-001310.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-002737 (1 moto purchase) | `TD-TC-002737.step1.moto-purchase.json` line 30 | `not sent (tables: 005=011, 031=12)` | TC-001311 | [TD-TC-001311.step1.moto-purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001311.step1.moto-purchase.json#L44) line 44 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-002742 (1 moto purchase) | `TD-TC-002742.json` line 30 | `not sent (tables: 005=101, 031=12)` | TC-001317 | [TD-TC-001317.json](../5-11-out/american_express_credit/test_data/TD-TC-001317.json#L59) line 59 | `003 sent, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-002743 (1 moto purchase) | `TD-TC-002743.step1.moto-purchase.json` line 30 | `not sent (tables: 005=101, 031=12)` | TC-001318 | [TD-TC-001318.step1.moto-purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001318.step1.moto-purchase.json#L59) line 59 | `003 sent, value empty` | AWAITING CLIENT INPUT |

---

<a id="cd-19"></a>

### CD-19 – Traceability matrix points to a folder that is not delivered; follow-on legs never traced – FIXED

| Check | Previous run | New run |
|---|---|---|
| Trace rows (`reports/traceability_matrix_full.json` → `flat_rows`) | 416 | 454 |
| Folders the test data paths point to | `qe_shaped_test_data/` | `test_data/` |
| `flat_rows` whose test data path does not exist | 374 | 0 |
| Trace entries (all levels) with a missing path / of them marked `file_exists: true` | 748 / 374 | 0 / 0 |
| Delivered test data files never referenced by the trace | 416 of 790 | 0 of 912 |
| Entries with `file_exists: false` | 42 | 10 – ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)) |

Check it yourself: open `5-11-out/american_express_credit/reports/traceability_matrix_full.json` and search `"test_data_file"` – the paths now start with `test_data/` (previously `qe_shaped_test_data/`, a folder that was never delivered), and every leg file of a flow is listed.

---

## 3. Review items of the previous log (not defects)

| ID | Item | Previous run | New run | Result |
|---|---|---|---|---|
| RV-01 | Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY | YYMM: 70 | YYMM: 84 | Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME) – e.g. [TD-TC-001188.step2.reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001188.step2.reversal.json#L15) line 15 |

## 4. Seen while re-checking (not in the previous log)

The Totals Request of two settlement flows does not match the transactions before it (Section 9.2, p.72; the same rule is defect D-21 of the Interlink Debit report). It was not in the previous log for this program.

Previous run: 16 test cases; new run: 16 test cases.

| New TC | Template | Totals file | Actual | Expected |
|---|---|---|---|---|
| TC-001069 | T8-PUR-TOR-SETTLE | [TD-TC-001069.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001102 | T8-PUR-TOR-SETTLE | [TD-TC-001102.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001102.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001134 | T8-PUR-TOR-SETTLE | [TD-TC-001134.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001134.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001168 | T8-PUR-TOR-SETTLE | [TD-TC-001168.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001168.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001200 | T8-PUR-TOR-SETTLE | [TD-TC-001200.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001200.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001235 | T8-PUR-TOR-SETTLE | [TD-TC-001235.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001235.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001261 | T8-PUR-TOR-SETTLE | [TD-TC-001261.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001261.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001292 | T8-PUR-TOR-SETTLE | [TD-TC-001292.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001292.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the TE bucket and send Grand Total 00000000 |
| TC-001329 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001329.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001329.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001345 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001345.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001345.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001360 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001360.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001360.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001378 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001378.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001378.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001395 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001395.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001395.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001412 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001412.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001412.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001426 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001426.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001426.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001441 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001441.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001441.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |

#### Explained step by step (new run)

**TC-001069 – T8-PUR-TOR-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 17

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-001069.step1.purchase.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step1.purchase.json#L19) line 19 | purchase (Transaction Type `0`) | `1500` = $15.00 | no response (time-out) | no response (timed out), so not in the totals |
| 2 | [TD-TC-001069.step2.timeout-reversal.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step2.timeout-reversal.json#L19) line 19 | timeout reversal (Transaction Type `Z`) | `1500` = $15.00 | Response Code `0` | its original was never approved, so nothing to take out |

So the TE bucket holds **count 0, amount $0.00** – nothing occurred for the card type, so Fields 15–17 are not sent (p.237).

Totals Request [TD-TC-001069.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'TE  '` | `not sent` | **WRONG** |
| Card Type Total Count (Element 16) | 21 | `00001` | `not sent` | **WRONG** |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `not sent` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00000000` | OK |
| Segment Length (Element 84) | 7 | `107` | `090` | **WRONG** |

The test case itself says for this step: *"the timed-out purchase is not in the totals"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 17 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

**TC-001329 – T8-AUT-COMPLETION-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 277

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-001329.step1.auth-only.json](../5-11-out/american_express_credit/test_data/TD-TC-001329.step1.auth-only.json#L19) line 19 | auth only (Transaction Type `3`) | `1500` = $15.00 | Response Code `2` | Authorization Only - AO bucket, not in the grand total |
| 2 | [TD-TC-001329.step2.completion.json](../5-11-out/american_express_credit/test_data/TD-TC-001329.step2.completion.json#L19) line 19 | completion (Transaction Type `0`) | `1500` = $15.00 | Response Code `0` | count +1, amount +$15.00 |

So the TE bucket holds **count 1, amount $15.00**.

Totals Request [TD-TC-001329.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001329.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'TE  '` | `'TE  '` | OK |
| Card Type Total Count (Element 16) | 21 | `00001` | `00001` | OK |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `00001500` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00001500` | **WRONG** |
| Segment Length (Element 84) | 7 | `107` | `107` | OK |

The test case itself says for this step: *"the completion is in the totals; the authorization only is not (no draft capture)"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 277 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

Re-run (from `regression-artifact-validator`): `python scripts/verify-defect-fixes.py`

