# MasterCard Fleet – defect fix verification (previous run 31-10-out → new run 5-11-out)

| Item | Value |
|---|---|
| Previous run | `C:/Users/F7OKAGW/Downloads/31-10-out/mastercard_fleet` – 299 test cases, 569 test data files |
| New run | `5-11-out/mastercard_fleet` – 307 test cases, 633 test data files |
| Defect log(s) | `Defect MD file/` Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) and MasterCard_Fleet_Consolidated_Defects.md (FL-xx) |
| Test case matching | IDs are renumbered in the new run; test cases are matched by transaction category + entry mode + template (unique in both runs), flow steps by step number and role. 267 of 299 previous test cases have a match; 32 were removed, 40 are new |

Status: **FIXED** – every previously failing file now passes the rule · **FIXED – value awaiting client input** – the generator now builds the field correctly, but its value can only come from the client (listed in the test case's `awaiting_inputs`, status `needs_client_input`) · **PARTIALLY FIXED** – some files pass, some still fail · **NOT FIXED** – still failing · **CLOSED – test cases removed** – the affected test cases are no longer in the catalog.

## 1. Summary

| Defect | Source ID | What was wrong | Previous run: TCs / files (logged) | New run: TCs / files still failing | Status |
|---|---|---|---|---|---|
| [CD-01](#cd-01) | FL-03 | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | 60 / 78 (60 / 78) | 0 / 0 | **FIXED** |
| [CD-02](#cd-02) | FL-04 | EMV Additional Information (Element 118) is not EMVYES / EMVNOT | 60 / 78 (60 / 78) | 0 / 0 | **FIXED** |
| [CD-03](#cd-03) | – | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | 90 / 139 (90 / 139) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-04](#cd-04) | – | Credential-on-File indicators (Tables 032, 049, 056) missing | 30 / 61 (30 / 61) | 0 / 0 | **FIXED – value awaiting client input** |
| [CD-05](#cd-05) | – | Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data | 30 / 39 (30 / 39) | 0 / 0 | **FIXED** |
| [CD-06](#cd-06) | – | Unmatched time-out reversal carries full track data | 5 / 5 (5 / 5) | 0 / 0 | **FIXED** |
| [CD-09](#cd-09) | FL-02 | Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 | 249 / 497 (249 / 497) | 0 / 0 | **FIXED** |
| [CD-10](#cd-10) | – | Totals Card Label (Element 13) is 2 bytes instead of 4 | 72 / 72 (72 / 72) | 0 / 0 | **FIXED** |
| [CD-11](#cd-11) | – | Totals card-type category sent with count 00000 | 8 / 8 (8 / 8) | 0 / 0 | **FIXED** |
| [CD-16](#cd-16) | – | 'Track 1' test carries Track 2 data | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| [CD-19](#cd-19) | O31-01 | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 320 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |

## 2. Defect details

<a id="cd-01"></a>

### CD-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 – FIXED

| | |
|---|---|
| Field | E188, Seg 130 (source ID FL-03) |
| Previous run | 60 test cases / 78 files failing (log: 60 / 78) |
| Previously failing files in the new run | 76 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `001` (76 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: `TD-TC-002172.json` line 78 → `01`. New (TC-000746): [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L83) line 83 → `001`<br>Expected: 001 (Tag 5F34 zero-padded to 3) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002185 (1 timeout reversal) | `TD-TC-002185.json` line 78 | `01` | TC-000759 | [TD-TC-000759.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000759.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-002211 (1 timeout reversal) | `TD-TC-002211.json` line 78 | `01` | TC-000785 | [TD-TC-000785.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000785.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-002172 (1 purchase) | `TD-TC-002172.json` line 78 | `01` | TC-000746 | [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L83) line 83 | `001` | FIXED |
| 4 | TC-002173 (1 purchase) | `TD-TC-002173.step1.purchase.json` line 78 | `01` | TC-000747 | [TD-TC-000747.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000747.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 5 | TC-002174 (1 purchase) | `TD-TC-002174.step1.purchase.json` line 78 | `01` | TC-000748 | [TD-TC-000748.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000748.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 6 | TC-002175 (1 purchase) | `TD-TC-002175.step1.purchase.json` line 78 | `01` | TC-000749 | [TD-TC-000749.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000749.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 7 | TC-002176 (1 purchase) | `TD-TC-002176.step1.purchase.json` line 78 | `01` | TC-000750 | [TD-TC-000750.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000750.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 8 | TC-002177 (1 purchase) | `TD-TC-002177.step1.purchase.json` line 78 | `01` | TC-000751 | [TD-TC-000751.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 9 | TC-002177 (3 next transaction) | `TD-TC-002177.step3.next-transaction.json` line 78 | `01` | TC-000751 | [TD-TC-000751.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step3.next-transaction.json#L83) line 83 | `001` | FIXED |
| 10 | TC-002178 (1 purchase) | `TD-TC-002178.step1.purchase.json` line 78 | `01` | TC-000752 | [TD-TC-000752.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 11 | TC-002179 (1 purchase) | `TD-TC-002179.step1.purchase.json` line 78 | `01` | TC-000753 | [TD-TC-000753.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000753.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 12 | TC-002180 (1 purchase) | `TD-TC-002180.step1.purchase.json` line 78 | `01` | TC-000754 | [TD-TC-000754.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 13 | TC-002181 (1 purchase) | `TD-TC-002181.step1.purchase.json` line 78 | `01` | TC-000755 | [TD-TC-000755.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 14 | TC-002181 (2 return) | `TD-TC-002181.step2.return.json` line 78 | `01` | TC-000755 | [TD-TC-000755.step2.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step2.return.json#L83) line 83 | `001` | FIXED |
| 15 | TC-002182 (1 purchase) | `TD-TC-002182.step1.purchase.json` line 78 | `01` | TC-000756 | [TD-TC-000756.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 16 | TC-002183 (1 purchase) | `TD-TC-002183.step1.purchase.json` line 78 | `01` | TC-000757 | [TD-TC-000757.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000757.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 17 | TC-002184 (1 purchase) | `TD-TC-002184.step1.purchase.json` line 78 | `01` | TC-000758 | [TD-TC-000758.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000758.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 18 | TC-002186 (1 purchase) | `TD-TC-002186.step1.purchase.json` line 78 | `01` | TC-000760 | [TD-TC-000760.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000760.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 19 | TC-002187 (1 purchase) | `TD-TC-002187.step1.purchase.json` line 78 | `01` | TC-000761 | [TD-TC-000761.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000761.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 20 | TC-002187 (2 next transaction) | `TD-TC-002187.step2.next-transaction.json` line 78 | `01` | TC-000761 | [TD-TC-000761.step2.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000761.step2.next-transaction.json#L83) line 83 | `001` | FIXED |
| 21 | TC-002188 (1 purchase) | `TD-TC-002188.step1.purchase.json` line 78 | `01` | TC-000762 | [TD-TC-000762.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000762.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 22 | TC-002188 (3 return) | `TD-TC-002188.step3.return.json` line 78 | `01` | TC-000762 | [TD-TC-000762.step3.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000762.step3.return.json#L83) line 83 | `001` | FIXED |
| 23 | TC-002189 (1 purchase) | `TD-TC-002189.step1.purchase.json` line 78 | `01` | TC-000763 | [TD-TC-000763.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000763.step1.purchase.json#L83) line 83 | `001` | FIXED |
| 24 | TC-002189 (3 next transaction) | `TD-TC-002189.step3.next-transaction.json` line 78 | `01` | TC-000763 | [TD-TC-000763.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000763.step3.next-transaction.json#L83) line 83 | `001` | FIXED |
| 25 | TC-002190 (1 purchase) | `TD-TC-002190.step1.purchase.json` line 78 | `01` | TC-000764 | [TD-TC-000764.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000764.step1.purchase.json#L83) line 83 | `001` | FIXED |

… 53 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-02"></a>

### CD-02 – EMV Additional Information (Element 118) is not EMVYES / EMVNOT – FIXED

| | |
|---|---|
| Field | E191/E192/E118, Seg 130 (source ID FL-04) |
| Previous run | 60 test cases / 78 files failing (log: 60 / 78) |
| Previously failing files in the new run | 76 fixed, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `EMVYES / length 006` (76 files); `Segment 130 is no longer sent on this step` (2 files) |
| Notes | Segment 130 is no longer sent on this step (2) |
| Check it yourself | Previous: `TD-TC-002172.json` line 83 → `S6KXPIA1QC1V6AWD…A2KLKQ2PMZ (984 cha…length 984 (52 chars)`. New (TC-000746): [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L88) line 88 → `EMVYES / length 006`<br>Expected: EMVYES or EMVNOT / length 006 |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002185 (1 timeout reversal) | `TD-TC-002185.json` line 83 | `S6KXPIA1QC1V6AWD…A2KLKQ2PMZ (984 chars) / length 984` | TC-000759 | [TD-TC-000759.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000759.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 2 | TC-002211 (1 timeout reversal) | `TD-TC-002211.json` line 83 | `IW2TSOBIYLIJBP24…QLSMFR1F62 (984 chars) / length 984` | TC-000785 | [TD-TC-000785.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000785.json) | `Segment 130 is no longer sent on this step` | CHANGED |
| 3 | TC-002172 (1 purchase) | `TD-TC-002172.json` line 83 | `S6KXPIA1QC1V6AWD…A2KLKQ2PMZ (984 chars) / length 984` | TC-000746 | [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 4 | TC-002173 (1 purchase) | `TD-TC-002173.step1.purchase.json` line 83 | `2M6XFE1UTKNTHGOR…ME6PIBBVXF (984 chars) / length 984` | TC-000747 | [TD-TC-000747.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000747.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 5 | TC-002174 (1 purchase) | `TD-TC-002174.step1.purchase.json` line 83 | `S5B0PHAC04MPE7UD…DRSCR288J9 (984 chars) / length 984` | TC-000748 | [TD-TC-000748.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000748.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 6 | TC-002175 (1 purchase) | `TD-TC-002175.step1.purchase.json` line 83 | `R12G25W5I9XGNSU1…4LXABXTSHU (984 chars) / length 984` | TC-000749 | [TD-TC-000749.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000749.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 7 | TC-002176 (1 purchase) | `TD-TC-002176.step1.purchase.json` line 83 | `7VJDGR7J19VKJG1N…0BA1EJQE5L (984 chars) / length 984` | TC-000750 | [TD-TC-000750.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000750.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 8 | TC-002177 (1 purchase) | `TD-TC-002177.step1.purchase.json` line 83 | `B8NFJINCDRPJRIIQ…U4DH87R631 (984 chars) / length 984` | TC-000751 | [TD-TC-000751.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 9 | TC-002177 (3 next transaction) | `TD-TC-002177.step3.next-transaction.json` line 83 | `7JHJ760UCHSGC4I1…KOS18I948K (984 chars) / length 984` | TC-000751 | [TD-TC-000751.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step3.next-transaction.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 10 | TC-002178 (1 purchase) | `TD-TC-002178.step1.purchase.json` line 83 | `6GJB5RKNCMPSYFTS…XXS28HIL1Q (984 chars) / length 984` | TC-000752 | [TD-TC-000752.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 11 | TC-002179 (1 purchase) | `TD-TC-002179.step1.purchase.json` line 83 | `29EK0ONBM4JKCJ9P…DVKIOG3TXK (984 chars) / length 984` | TC-000753 | [TD-TC-000753.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000753.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 12 | TC-002180 (1 purchase) | `TD-TC-002180.step1.purchase.json` line 83 | `VPUO1JA2M5TT2HWB…JKL316JL85 (984 chars) / length 984` | TC-000754 | [TD-TC-000754.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 13 | TC-002181 (1 purchase) | `TD-TC-002181.step1.purchase.json` line 83 | `FYWY63BSANQTFAPT…3HRP2MW1AK (984 chars) / length 984` | TC-000755 | [TD-TC-000755.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 14 | TC-002181 (2 return) | `TD-TC-002181.step2.return.json` line 83 | `3ION9J9NDK9CH5ZI…8E3YSVEWOH (984 chars) / length 984` | TC-000755 | [TD-TC-000755.step2.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step2.return.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 15 | TC-002182 (1 purchase) | `TD-TC-002182.step1.purchase.json` line 83 | `OSKSEKRJP6XML5FF…N0DT5NF3SH (984 chars) / length 984` | TC-000756 | [TD-TC-000756.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 16 | TC-002183 (1 purchase) | `TD-TC-002183.step1.purchase.json` line 83 | `YYHP3VGILA1WIP8S…FUH01NDKL3 (984 chars) / length 984` | TC-000757 | [TD-TC-000757.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000757.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 17 | TC-002184 (1 purchase) | `TD-TC-002184.step1.purchase.json` line 83 | `U29AO92NR22DCAU0…1J5UKTWCTR (984 chars) / length 984` | TC-000758 | [TD-TC-000758.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000758.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 18 | TC-002186 (1 purchase) | `TD-TC-002186.step1.purchase.json` line 83 | `IPRZX96GH8OJSZWI…S26LL2GS23 (984 chars) / length 984` | TC-000760 | [TD-TC-000760.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000760.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 19 | TC-002187 (1 purchase) | `TD-TC-002187.step1.purchase.json` line 83 | `1FUM9C6M2TK0QGFV…1LKXKM7FFA (984 chars) / length 984` | TC-000761 | [TD-TC-000761.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000761.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 20 | TC-002187 (2 next transaction) | `TD-TC-002187.step2.next-transaction.json` line 83 | `G5AU9QXZ43P5U2WQ…EI5W2OOBRM (984 chars) / length 984` | TC-000761 | [TD-TC-000761.step2.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000761.step2.next-transaction.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 21 | TC-002188 (1 purchase) | `TD-TC-002188.step1.purchase.json` line 83 | `8EWC6KFYJUXANY3U…K39JXE28TK (984 chars) / length 984` | TC-000762 | [TD-TC-000762.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000762.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 22 | TC-002188 (3 return) | `TD-TC-002188.step3.return.json` line 83 | `1LVII3OH31VYISMB…KHK0HQ1FUS (984 chars) / length 984` | TC-000762 | [TD-TC-000762.step3.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000762.step3.return.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 23 | TC-002189 (1 purchase) | `TD-TC-002189.step1.purchase.json` line 83 | `NBBOX10M78XUECPN…29PKTW0BSN (984 chars) / length 984` | TC-000763 | [TD-TC-000763.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000763.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 24 | TC-002189 (3 next transaction) | `TD-TC-002189.step3.next-transaction.json` line 83 | `53BUML33O4NEG3ZN…ILEO0WARLX (984 chars) / length 984` | TC-000763 | [TD-TC-000763.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000763.step3.next-transaction.json#L88) line 88 | `EMVYES / length 006` | FIXED |
| 25 | TC-002190 (1 purchase) | `TD-TC-002190.step1.purchase.json` line 83 | `Z69WENICYCR9PXNO…8WB71K3F2U (984 chars) / length 984` | TC-000764 | [TD-TC-000764.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000764.step1.purchase.json#L88) line 88 | `EMVYES / length 006` | FIXED |

… 53 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-03"></a>

### CD-03 – Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Table 030, Seg 111 |
| Previous run | 90 test cases / 139 files failing (log: 90 / 139) |
| Previously failing files in the new run | 137 awaiting client input, 2 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `030 sent, length 010, value empty` (137 files); `Segment 130 is no longer sent on this step (no longer an EMV request)` (2 files) |
| Notes | value listed in awaiting_inputs; length 010 is declared for the value to come, so the file cannot be sent until the code is filled in (137); Segment 130 is no longer sent on this step (no longer an EMV request) (2) |
| Check it yourself | Previous: `TD-TC-002172.json` line 61 → `not sent (tables: 005=051, 031=12)`. New (TC-000746): [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L75) line 75 → `030 sent, length 010, value empty`<br>Expected: 10 digits from the client's certified terminal |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002172 (1 purchase) | `TD-TC-002172.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000746 | [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 2 | TC-002173 (1 purchase) | `TD-TC-002173.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000747 | [TD-TC-000747.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000747.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 3 | TC-002174 (1 purchase) | `TD-TC-002174.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000748 | [TD-TC-000748.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000748.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 4 | TC-002175 (1 purchase) | `TD-TC-002175.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000749 | [TD-TC-000749.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000749.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 5 | TC-002176 (1 purchase) | `TD-TC-002176.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000750 | [TD-TC-000750.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000750.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 6 | TC-002177 (1 purchase) | `TD-TC-002177.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000751 | [TD-TC-000751.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 7 | TC-002177 (3 next transaction) | `TD-TC-002177.step3.next-transaction.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000751 | [TD-TC-000751.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step3.next-transaction.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 8 | TC-002178 (1 purchase) | `TD-TC-002178.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000752 | [TD-TC-000752.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 9 | TC-002179 (1 purchase) | `TD-TC-002179.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000753 | [TD-TC-000753.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000753.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 10 | TC-002180 (1 purchase) | `TD-TC-002180.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000754 | [TD-TC-000754.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 11 | TC-002181 (1 purchase) | `TD-TC-002181.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000755 | [TD-TC-000755.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 12 | TC-002181 (2 return) | `TD-TC-002181.step2.return.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000755 | [TD-TC-000755.step2.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step2.return.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 13 | TC-002182 (1 purchase) | `TD-TC-002182.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000756 | [TD-TC-000756.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 14 | TC-002183 (1 purchase) | `TD-TC-002183.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000757 | [TD-TC-000757.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000757.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 15 | TC-002184 (1 purchase) | `TD-TC-002184.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000758 | [TD-TC-000758.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000758.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 16 | TC-002186 (1 purchase) | `TD-TC-002186.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000760 | [TD-TC-000760.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000760.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 17 | TC-002187 (1 purchase) | `TD-TC-002187.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000761 | [TD-TC-000761.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000761.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 18 | TC-002187 (2 next transaction) | `TD-TC-002187.step2.next-transaction.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000761 | [TD-TC-000761.step2.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000761.step2.next-transaction.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 19 | TC-002188 (1 purchase) | `TD-TC-002188.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000762 | [TD-TC-000762.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000762.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 20 | TC-002188 (3 return) | `TD-TC-002188.step3.return.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000762 | [TD-TC-000762.step3.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000762.step3.return.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 21 | TC-002189 (1 purchase) | `TD-TC-002189.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000763 | [TD-TC-000763.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000763.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 22 | TC-002189 (3 next transaction) | `TD-TC-002189.step3.next-transaction.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000763 | [TD-TC-000763.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000763.step3.next-transaction.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 23 | TC-002190 (1 purchase) | `TD-TC-002190.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000764 | [TD-TC-000764.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000764.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 24 | TC-002190 (2 resubmitted) | `TD-TC-002190.step2.resubmitted.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000764 | [TD-TC-000764.step2.resubmitted.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000764.step2.resubmitted.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |
| 25 | TC-002191 (1 purchase) | `TD-TC-002191.step1.purchase.json` line 61 | `not sent (tables: 005=051, 031=12)` | TC-000765 | [TD-TC-000765.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000765.step1.purchase.json#L75) line 75 | `030 sent, length 010, value empty` | AWAITING CLIENT INPUT |

… 114 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-04"></a>

### CD-04 – Credential-on-File indicators (Tables 032, 049, 056) missing – FIXED – value awaiting client input

| | |
|---|---|
| Field | E113 Tables 032/049/056, Seg 111 |
| Previous run | 30 test cases / 61 files failing (log: 30 / 61) |
| Previously failing files in the new run | 61 awaiting client input |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (awaiting client)` (61 files) |
| Check it yourself | Previous: `TD-TC-002337.json` line 61 → `032-08 C/I missing; 049-02 missing; 056-09 missing`. New (TC-000911): [TD-TC-000911.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000911.json#L80) line 80 → `032-08 C/I 08001C; 049-02 021C; 056-…ng client) (62 chars)`<br>Expected: 032-08 = C (I on the storing step), 049-02 = C/M, 056-09 MIT/CIT category |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002337 (1 purchase) | `TD-TC-002337.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000911 | [TD-TC-000911.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000911.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 2 | TC-002338 (1 purchase) | `TD-TC-002338.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000912 | [TD-TC-000912.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000912.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 3 | TC-002338 (2 reversal) | `TD-TC-002338.step2.reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000912 | [TD-TC-000912.step2.reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000912.step2.reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 4 | TC-002339 (1 purchase) | `TD-TC-002339.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000913 | [TD-TC-000913.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000913.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 5 | TC-002339 (2 timeout reversal) | `TD-TC-002339.step2.timeout-reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000913 | [TD-TC-000913.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000913.step2.timeout-reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 6 | TC-002340 (1 purchase) | `TD-TC-002340.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000914 | [TD-TC-000914.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000914.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 7 | TC-002340 (2 timeout reversal) | `TD-TC-002340.step2.timeout-reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000914 | [TD-TC-000914.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000914.step2.timeout-reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 8 | TC-002341 (1 purchase) | `TD-TC-002341.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000915 | [TD-TC-000915.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000915.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 9 | TC-002341 (2 timeout reversal) | `TD-TC-002341.step2.timeout-reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000915 | [TD-TC-000915.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000915.step2.timeout-reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 10 | TC-002342 (1 purchase) | `TD-TC-002342.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000916 | [TD-TC-000916.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000916.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 11 | TC-002342 (2 timeout reversal) | `TD-TC-002342.step2.timeout-reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000916 | [TD-TC-000916.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000916.step2.timeout-reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 12 | TC-002342 (3 next transaction) | `TD-TC-002342.step3.next-transaction.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000916 | [TD-TC-000916.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000916.step3.next-transaction.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 13 | TC-002343 (1 purchase) | `TD-TC-002343.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000917 | [TD-TC-000917.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000917.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 14 | TC-002343 (2 timeout reversal) | `TD-TC-002343.step2.timeout-reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000917 | [TD-TC-000917.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000917.step2.timeout-reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 15 | TC-002343 (3 tor retry) | `TD-TC-002343.step3.tor-retry.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000917 | [TD-TC-000917.step3.tor-retry.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000917.step3.tor-retry.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 16 | TC-002343 (4 tor retry) | `TD-TC-002343.step4.tor-retry.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000917 | [TD-TC-000917.step4.tor-retry.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000917.step4.tor-retry.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 17 | TC-002344 (1 purchase) | `TD-TC-002344.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000918 | [TD-TC-000918.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000918.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 18 | TC-002345 (1 purchase) | `TD-TC-002345.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000919 | [TD-TC-000919.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000919.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 19 | TC-002345 (2 reversal) | `TD-TC-002345.step2.reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000919 | [TD-TC-000919.step2.reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000919.step2.reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 20 | TC-002346 (1 purchase) | `TD-TC-002346.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000920 | [TD-TC-000920.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000920.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 21 | TC-002346 (2 return) | `TD-TC-002346.step2.return.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000920 | [TD-TC-000920.step2.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000920.step2.return.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 22 | TC-002347 (1 purchase) | `TD-TC-002347.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000921 | [TD-TC-000921.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000921.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 23 | TC-002347 (2 timeout reversal) | `TD-TC-002347.step2.timeout-reversal.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000921 | [TD-TC-000921.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000921.step2.timeout-reversal.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 24 | TC-002348 (1 purchase) | `TD-TC-002348.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000922 | [TD-TC-000922.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000922.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |
| 25 | TC-002349 (1 purchase) | `TD-TC-002349.step1.purchase.json` line 61 | `032-08 C/I missing; 049-02 missing; 056-09 missing` | TC-000923 | [TD-TC-000923.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000923.step1.purchase.json#L80) line 80 | `032-08 C/I 08001C; 049-02 021C; 056-09 empty (…ng client) (62 chars)` | AWAITING CLIENT INPUT |

… 36 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-05"></a>

### CD-05 – Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data – FIXED

| | |
|---|---|
| Field | E12 + Table 005 |
| Previous run | 30 test cases / 39 files failing (log: 30 / 39) |
| Previously failing files in the new run | 39 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `1231 with entry mode 79` (4 files); `0130 with entry mode 79` (3 files); `0127 with entry mode 79` (3 files) |
| Check it yourself | Previous: `TD-TC-002311.json` line 15 → `30122011622125893912 with entry mode 79`. New (TC-000885): [TD-TC-000885.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000885.json#L15) line 15 → `1230 with entry mode 79`<br>Expected: expiry MMYY only |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002311 (1 purchase) | `TD-TC-002311.json` line 15 | `30122011622125893912 with entry mode 79` | TC-000885 | [TD-TC-000885.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000885.json#L15) line 15 | `1230 with entry mode 79` | FIXED |
| 2 | TC-002312 (1 purchase) | `TD-TC-002312.step1.purchase.json` line 15 | `27102014699140707012 with entry mode 79` | TC-000886 | [TD-TC-000886.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000886.step1.purchase.json#L15) line 15 | `1027 with entry mode 79` | FIXED |
| 3 | TC-002313 (1 purchase) | `TD-TC-002313.step1.purchase.json` line 15 | `32112014690248806012 with entry mode 79` | TC-000887 | [TD-TC-000887.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000887.step1.purchase.json#L15) line 15 | `1132 with entry mode 79` | FIXED |
| 4 | TC-002314 (1 purchase) | `TD-TC-002314.step1.purchase.json` line 15 | `31032018952784899312 with entry mode 79` | TC-000888 | [TD-TC-000888.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000888.step1.purchase.json#L15) line 15 | `0331 with entry mode 79` | FIXED |
| 5 | TC-002315 (1 purchase) | `TD-TC-002315.step1.purchase.json` line 15 | `28022011941296974912 with entry mode 79` | TC-000889 | [TD-TC-000889.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000889.step1.purchase.json#L15) line 15 | `0228 with entry mode 79` | FIXED |
| 6 | TC-002316 (1 purchase) | `TD-TC-002316.step1.purchase.json` line 15 | `27112017163036916912 with entry mode 79` | TC-000890 | [TD-TC-000890.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000890.step1.purchase.json#L15) line 15 | `1127 with entry mode 79` | FIXED |
| 7 | TC-002316 (3 next transaction) | `TD-TC-002316.step3.next-transaction.json` line 15 | `27112017163036916912 with entry mode 79` | TC-000890 | [TD-TC-000890.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000890.step3.next-transaction.json#L15) line 15 | `1127 with entry mode 79` | FIXED |
| 8 | TC-002317 (1 purchase) | `TD-TC-002317.step1.purchase.json` line 15 | `32032010289984626012 with entry mode 79` | TC-000891 | [TD-TC-000891.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000891.step1.purchase.json#L15) line 15 | `0332 with entry mode 79` | FIXED |
| 9 | TC-002318 (1 purchase) | `TD-TC-002318.step1.purchase.json` line 15 | `30012017747390666512 with entry mode 79` | TC-000892 | [TD-TC-000892.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000892.step1.purchase.json#L15) line 15 | `0130 with entry mode 79` | FIXED |
| 10 | TC-002319 (1 purchase) | `TD-TC-002319.step1.purchase.json` line 15 | `29012013272131756112 with entry mode 79` | TC-000893 | [TD-TC-000893.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000893.step1.purchase.json#L15) line 15 | `0129 with entry mode 79` | FIXED |
| 11 | TC-002320 (1 purchase) | `TD-TC-002320.step1.purchase.json` line 15 | `27012010906599450812 with entry mode 79` | TC-000894 | [TD-TC-000894.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000894.step1.purchase.json#L15) line 15 | `0127 with entry mode 79` | FIXED |
| 12 | TC-002320 (2 return) | `TD-TC-002320.step2.return.json` line 15 | `27012010906599450812 with entry mode 79` | TC-000894 | [TD-TC-000894.step2.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000894.step2.return.json#L15) line 15 | `0127 with entry mode 79` | FIXED |
| 13 | TC-002321 (1 purchase) | `TD-TC-002321.step1.purchase.json` line 15 | `32082013951104687512 with entry mode 79` | TC-000895 | [TD-TC-000895.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000895.step1.purchase.json#L15) line 15 | `0832 with entry mode 79` | FIXED |
| 14 | TC-002322 (1 purchase) | `TD-TC-002322.step1.purchase.json` line 15 | `29042017035172370112 with entry mode 79` | TC-000896 | [TD-TC-000896.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000896.step1.purchase.json#L15) line 15 | `0429 with entry mode 79` | FIXED |
| 15 | TC-002323 (1 purchase) | `TD-TC-002323.step1.purchase.json` line 15 | `32022015943172221112 with entry mode 79` | TC-000897 | [TD-TC-000897.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000897.step1.purchase.json#L15) line 15 | `0232 with entry mode 79` | FIXED |
| 16 | TC-002324 (1 timeout reversal) | `TD-TC-002324.json` line 15 | `29102011879473385612 with entry mode 79` | TC-000898 | [TD-TC-000898.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000898.json#L15) line 15 | `2910 with entry mode 79` | FIXED |
| 17 | TC-002325 (1 purchase) | `TD-TC-002325.step1.purchase.json` line 15 | `31112016742925610712 with entry mode 79` | TC-000899 | [TD-TC-000899.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000899.step1.purchase.json#L15) line 15 | `1131 with entry mode 79` | FIXED |
| 18 | TC-002326 (1 purchase) | `TD-TC-002326.step1.purchase.json` line 15 | `27092013216543841712 with entry mode 79` | TC-000900 | [TD-TC-000900.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000900.step1.purchase.json#L15) line 15 | `0927 with entry mode 79` | FIXED |
| 19 | TC-002326 (2 next transaction) | `TD-TC-002326.step2.next-transaction.json` line 15 | `27092013216543841712 with entry mode 79` | TC-000900 | [TD-TC-000900.step2.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000900.step2.next-transaction.json#L15) line 15 | `0927 with entry mode 79` | FIXED |
| 20 | TC-002327 (1 purchase) | `TD-TC-002327.step1.purchase.json` line 15 | `31012012854199099812 with entry mode 79` | TC-000901 | [TD-TC-000901.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000901.step1.purchase.json#L15) line 15 | `0131 with entry mode 79` | FIXED |
| 21 | TC-002327 (3 return) | `TD-TC-002327.step3.return.json` line 15 | `31012012854199099812 with entry mode 79` | TC-000901 | [TD-TC-000901.step3.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000901.step3.return.json#L15) line 15 | `0131 with entry mode 79` | FIXED |
| 22 | TC-002328 (1 purchase) | `TD-TC-002328.step1.purchase.json` line 15 | `31122019969329111112 with entry mode 79` | TC-000902 | [TD-TC-000902.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000902.step1.purchase.json#L15) line 15 | `1231 with entry mode 79` | FIXED |
| 23 | TC-002328 (3 next transaction) | `TD-TC-002328.step3.next-transaction.json` line 15 | `31122019969329111112 with entry mode 79` | TC-000902 | [TD-TC-000902.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000902.step3.next-transaction.json#L15) line 15 | `1231 with entry mode 79` | FIXED |
| 24 | TC-002329 (1 purchase) | `TD-TC-002329.step1.purchase.json` line 15 | `29092013969193484312 with entry mode 79` | TC-000903 | [TD-TC-000903.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000903.step1.purchase.json#L15) line 15 | `0929 with entry mode 79` | FIXED |
| 25 | TC-002329 (2 resubmitted) | `TD-TC-002329.step2.resubmitted.json` line 15 | `29092013969193484312 with entry mode 79` | TC-000903 | [TD-TC-000903.step2.resubmitted.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000903.step2.resubmitted.json#L15) line 15 | `0929 with entry mode 79` | FIXED |

… 14 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-06"></a>

### CD-06 – Unmatched time-out reversal carries full track data – FIXED

| | |
|---|---|
| Field | E12 |
| Previous run | 5 test cases / 5 files failing (log: 5 / 5) |
| Previously failing files in the new run | 5 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `2906` (1 file); `2904` (1 file); `3009` (1 file) |
| Check it yourself | Previous: `TD-TC-002185.json` line 15 → `32042016331628434012`. New (TC-000759): [TD-TC-000759.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000759.json#L15) line 15 → `2906`<br>Expected: 2906 (expiry YYMM only) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002185 (1 timeout reversal) | `TD-TC-002185.json` line 15 | `32042016331628434012` | TC-000759 | [TD-TC-000759.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000759.json#L15) line 15 | `2906` | FIXED |
| 2 | TC-002211 (1 timeout reversal) | `TD-TC-002211.json` line 15 | `27032012221070231512` | TC-000785 | [TD-TC-000785.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000785.json#L15) line 15 | `2904` | FIXED |
| 3 | TC-002237 (1 timeout reversal) | `TD-TC-002237.json` line 15 | `29021012032812538512` | TC-000811 | [TD-TC-000811.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000811.json#L15) line 15 | `3009` | FIXED |
| 4 | TC-002270 (1 timeout reversal) | `TD-TC-002270.json` line 15 | `32022016015587614712` | TC-000844 | [TD-TC-000844.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000844.json#L15) line 15 | `3202` | FIXED |
| 5 | TC-002376 (1 timeout reversal) | `TD-TC-002376.json` line 15 | `29012016137156059712` | TC-000950 | [TD-TC-000950.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000950.json#L15) line 15 | `2901` | FIXED |

---

<a id="cd-09"></a>

### CD-09 – Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 – FIXED

| | |
|---|---|
| Field | E84, Seg 102 (source ID FL-02) |
| Previous run | 249 test cases / 497 files failing (log: 249 / 497) |
| Previously failing files in the new run | 497 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `029` (481 files); `027` (16 files) |
| Check it yourself | Previous: `TD-TC-002172.json` line 49 → `033`. New (TC-000746): [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L49) line 49 → `029`<br>Expected: 029 (12.3: FS after type/length and the last amount, '\' after quantity and unit price) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002172 (1 purchase) | `TD-TC-002172.json` line 49 | `033` | TC-000746 | [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L49) line 49 | `029` | FIXED |
| 2 | TC-002173 (1 purchase) | `TD-TC-002173.step1.purchase.json` line 49 | `033` | TC-000747 | [TD-TC-000747.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000747.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 3 | TC-002173 (2 reversal) | `TD-TC-002173.step2.reversal.json` line 49 | `033` | TC-000747 | [TD-TC-000747.step2.reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000747.step2.reversal.json#L49) line 49 | `029` | FIXED |
| 4 | TC-002174 (1 purchase) | `TD-TC-002174.step1.purchase.json` line 49 | `033` | TC-000748 | [TD-TC-000748.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000748.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 5 | TC-002174 (2 timeout reversal) | `TD-TC-002174.step2.timeout-reversal.json` line 49 | `033` | TC-000748 | [TD-TC-000748.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000748.step2.timeout-reversal.json#L49) line 49 | `029` | FIXED |
| 6 | TC-002175 (1 purchase) | `TD-TC-002175.step1.purchase.json` line 49 | `033` | TC-000749 | [TD-TC-000749.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000749.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 7 | TC-002175 (2 timeout reversal) | `TD-TC-002175.step2.timeout-reversal.json` line 49 | `033` | TC-000749 | [TD-TC-000749.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000749.step2.timeout-reversal.json#L49) line 49 | `029` | FIXED |
| 8 | TC-002176 (1 purchase) | `TD-TC-002176.step1.purchase.json` line 49 | `033` | TC-000750 | [TD-TC-000750.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000750.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 9 | TC-002176 (2 timeout reversal) | `TD-TC-002176.step2.timeout-reversal.json` line 49 | `033` | TC-000750 | [TD-TC-000750.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000750.step2.timeout-reversal.json#L49) line 49 | `029` | FIXED |
| 10 | TC-002177 (1 purchase) | `TD-TC-002177.step1.purchase.json` line 49 | `033` | TC-000751 | [TD-TC-000751.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 11 | TC-002177 (2 timeout reversal) | `TD-TC-002177.step2.timeout-reversal.json` line 49 | `033` | TC-000751 | [TD-TC-000751.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step2.timeout-reversal.json#L49) line 49 | `029` | FIXED |
| 12 | TC-002177 (3 next transaction) | `TD-TC-002177.step3.next-transaction.json` line 49 | `033` | TC-000751 | [TD-TC-000751.step3.next-transaction.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000751.step3.next-transaction.json#L49) line 49 | `029` | FIXED |
| 13 | TC-002178 (1 purchase) | `TD-TC-002178.step1.purchase.json` line 49 | `033` | TC-000752 | [TD-TC-000752.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 14 | TC-002178 (2 timeout reversal) | `TD-TC-002178.step2.timeout-reversal.json` line 49 | `033` | TC-000752 | [TD-TC-000752.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step2.timeout-reversal.json#L49) line 49 | `029` | FIXED |
| 15 | TC-002178 (3 tor retry) | `TD-TC-002178.step3.tor-retry.json` line 49 | `033` | TC-000752 | [TD-TC-000752.step3.tor-retry.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step3.tor-retry.json#L49) line 49 | `029` | FIXED |
| 16 | TC-002178 (4 tor retry) | `TD-TC-002178.step4.tor-retry.json` line 49 | `033` | TC-000752 | [TD-TC-000752.step4.tor-retry.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000752.step4.tor-retry.json#L49) line 49 | `029` | FIXED |
| 17 | TC-002179 (1 purchase) | `TD-TC-002179.step1.purchase.json` line 49 | `033` | TC-000753 | [TD-TC-000753.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000753.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 18 | TC-002180 (1 purchase) | `TD-TC-002180.step1.purchase.json` line 49 | `033` | TC-000754 | [TD-TC-000754.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 19 | TC-002180 (2 reversal) | `TD-TC-002180.step2.reversal.json` line 49 | `033` | TC-000754 | [TD-TC-000754.step2.reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step2.reversal.json#L49) line 49 | `029` | FIXED |
| 20 | TC-002181 (1 purchase) | `TD-TC-002181.step1.purchase.json` line 49 | `033` | TC-000755 | [TD-TC-000755.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 21 | TC-002181 (2 return) | `TD-TC-002181.step2.return.json` line 49 | `033` | TC-000755 | [TD-TC-000755.step2.return.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step2.return.json#L49) line 49 | `029` | FIXED |
| 22 | TC-002182 (1 purchase) | `TD-TC-002182.step1.purchase.json` line 49 | `033` | TC-000756 | [TD-TC-000756.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 23 | TC-002182 (2 timeout reversal) | `TD-TC-002182.step2.timeout-reversal.json` line 49 | `033` | TC-000756 | [TD-TC-000756.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step2.timeout-reversal.json#L49) line 49 | `029` | FIXED |
| 24 | TC-002183 (1 purchase) | `TD-TC-002183.step1.purchase.json` line 49 | `033` | TC-000757 | [TD-TC-000757.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000757.step1.purchase.json#L49) line 49 | `029` | FIXED |
| 25 | TC-002184 (1 purchase) | `TD-TC-002184.step1.purchase.json` line 49 | `033` | TC-000758 | [TD-TC-000758.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000758.step1.purchase.json#L49) line 49 | `029` | FIXED |

… 472 more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).

---

<a id="cd-10"></a>

### CD-10 – Totals Card Label (Element 13) is 2 bytes instead of 4 – FIXED

| | |
|---|---|
| Field | E13, Seg 105 |
| Previous run | 72 test cases / 72 files failing (log: 72 / 72) |
| Previously failing files in the new run | 64 fixed, 8 changed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `'FL  '` (64 files); `no card-type category is sent any more (nothing occurred…card type) (75 chars)` (8 files) |
| Notes | no card-type category is sent any more (nothing occurred for the card type) (8) |
| Check it yourself | Previous: `TD-TC-002179.step2.totals.json` line 20 → `'FL'`. New (TC-000753): [TD-TC-000753.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000753.step2.totals.json#L20) line 20 → `'FL  '`<br>Expected: 'FL  ' |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002180 (3 totals) | `TD-TC-002180.step3.totals.json` line 20 | `'FL'` | TC-000754 | [TD-TC-000754.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 2 | TC-002206 (3 totals) | `TD-TC-002206.step3.totals.json` line 20 | `'FL'` | TC-000780 | [TD-TC-000780.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000780.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 3 | TC-002232 (3 totals) | `TD-TC-002232.step3.totals.json` line 20 | `'FL'` | TC-000806 | [TD-TC-000806.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000806.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 4 | TC-002265 (3 totals) | `TD-TC-002265.step3.totals.json` line 20 | `'FL'` | TC-000839 | [TD-TC-000839.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000839.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 5 | TC-002291 (3 totals) | `TD-TC-002291.step3.totals.json` line 20 | `'FL'` | TC-000865 | [TD-TC-000865.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000865.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 6 | TC-002319 (3 totals) | `TD-TC-002319.step3.totals.json` line 20 | `'FL'` | TC-000893 | [TD-TC-000893.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000893.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 7 | TC-002345 (3 totals) | `TD-TC-002345.step3.totals.json` line 20 | `'FL'` | TC-000919 | [TD-TC-000919.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000919.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 8 | TC-002371 (3 totals) | `TD-TC-002371.step3.totals.json` line 20 | `'FL'` | TC-000945 | [TD-TC-000945.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000945.step3.totals.json) | `no card-type category is sent any more (nothin…card type) (75 chars)` | CHANGED |
| 9 | TC-002179 (2 totals) | `TD-TC-002179.step2.totals.json` line 20 | `'FL'` | TC-000753 | [TD-TC-000753.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000753.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 10 | TC-002181 (3 totals) | `TD-TC-002181.step3.totals.json` line 20 | `'FL'` | TC-000755 | [TD-TC-000755.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000755.step3.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 11 | TC-002182 (3 totals) | `TD-TC-002182.step3.totals.json` line 20 | `'FL'` | TC-000756 | [TD-TC-000756.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step3.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 12 | TC-002183 (2 totals) | `TD-TC-002183.step2.totals.json` line 20 | `'FL'` | TC-000757 | [TD-TC-000757.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000757.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 13 | TC-002184 (2 totals) | `TD-TC-002184.step2.totals.json` line 20 | `'FL'` | TC-000758 | [TD-TC-000758.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000758.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 14 | TC-002192 (2 totals) | `TD-TC-002192.step2.totals.json` line 20 | `'FL'` | TC-000766 | [TD-TC-000766.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000766.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 15 | TC-002193 (2 totals) | `TD-TC-002193.step2.totals.json` line 20 | `'FL'` | TC-000767 | [TD-TC-000767.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000767.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 16 | TC-002205 (2 totals) | `TD-TC-002205.step2.totals.json` line 20 | `'FL'` | TC-000779 | [TD-TC-000779.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000779.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 17 | TC-002207 (3 totals) | `TD-TC-002207.step3.totals.json` line 20 | `'FL'` | TC-000781 | [TD-TC-000781.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000781.step3.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 18 | TC-002208 (3 totals) | `TD-TC-002208.step3.totals.json` line 20 | `'FL'` | TC-000782 | [TD-TC-000782.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000782.step3.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 19 | TC-002209 (2 totals) | `TD-TC-002209.step2.totals.json` line 20 | `'FL'` | TC-000783 | [TD-TC-000783.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000783.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 20 | TC-002210 (2 totals) | `TD-TC-002210.step2.totals.json` line 20 | `'FL'` | TC-000784 | [TD-TC-000784.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000784.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 21 | TC-002218 (2 totals) | `TD-TC-002218.step2.totals.json` line 20 | `'FL'` | TC-000792 | [TD-TC-000792.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000792.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 22 | TC-002219 (2 totals) | `TD-TC-002219.step2.totals.json` line 20 | `'FL'` | TC-000793 | [TD-TC-000793.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000793.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 23 | TC-002231 (2 totals) | `TD-TC-002231.step2.totals.json` line 20 | `'FL'` | TC-000805 | [TD-TC-000805.step2.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000805.step2.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 24 | TC-002233 (3 totals) | `TD-TC-002233.step3.totals.json` line 20 | `'FL'` | TC-000807 | [TD-TC-000807.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000807.step3.totals.json#L20) line 20 | `'FL  '` | FIXED |
| 25 | TC-002234 (3 totals) | `TD-TC-002234.step3.totals.json` line 20 | `'FL'` | TC-000808 | [TD-TC-000808.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000808.step3.totals.json#L20) line 20 | `'FL  '` | FIXED |

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
| Check it yourself | Previous: `TD-TC-002180.step3.totals.json` line 21 → `Card Label 'FL', count 00000, amount 00000000`. New (TC-000754): [TD-TC-000754.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step3.totals.json#L19) line 19 → `category not sent`<br>Expected: category only when data occurs |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002180 (3 totals) | `TD-TC-002180.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000754 | [TD-TC-000754.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000754.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 2 | TC-002206 (3 totals) | `TD-TC-002206.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000780 | [TD-TC-000780.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000780.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 3 | TC-002232 (3 totals) | `TD-TC-002232.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000806 | [TD-TC-000806.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000806.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 4 | TC-002265 (3 totals) | `TD-TC-002265.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000839 | [TD-TC-000839.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000839.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 5 | TC-002291 (3 totals) | `TD-TC-002291.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000865 | [TD-TC-000865.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000865.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 6 | TC-002319 (3 totals) | `TD-TC-002319.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000893 | [TD-TC-000893.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000893.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 7 | TC-002345 (3 totals) | `TD-TC-002345.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000919 | [TD-TC-000919.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000919.step3.totals.json#L19) line 19 | `category not sent` | FIXED |
| 8 | TC-002371 (3 totals) | `TD-TC-002371.step3.totals.json` line 21 | `Card Label 'FL', count 00000, amount 00000000` | TC-000945 | [TD-TC-000945.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000945.step3.totals.json#L19) line 19 | `category not sent` | FIXED |

---

<a id="cd-16"></a>

### CD-16 – 'Track 1' test carries Track 2 data – FIXED

| | |
|---|---|
| Field | E2 / E12 |
| Previous run | 2 test cases / 2 files failing (log: 2 / 2) |
| Previously failing files in the new run | 2 fixed |
| New run, all test cases | 0 test cases / 0 files still failing |
| What the new files contain | `B5567295678754243 / TEST/CARDHOLDER^3210101702924422854423743570712` (1 file); `B5560878723104998 / TEST/CARDHOLDER^2708101541185946328878100220112` (1 file) |
| Check it yourself | Previous: `TD-TC-002254.json` line 14 → `5567295678754243 / 32101017058393581512`. New (TC-000826): [TD-TC-000826.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000826.json#L14) line 14 → `B5567295678754243 / TEST/CARDHOLDER^…3743570712 (67 chars)`<br>Expected: B<PAN> / NAME^YYMM<service code><discretionary data> (Track 1, at most 76) |

| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |
|---|---|---|---|---|---|---|---|
| 1 | TC-002254 (1 purchase) | `TD-TC-002254.json` line 14 | `5567295678754243 / 32101017058393581512` | TC-000826 | [TD-TC-000826.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000826.json#L14) line 14 | `B5567295678754243 / TEST/CARDHOLDER^3210101702…3743570712 (67 chars)` | FIXED |
| 2 | TC-002412 (1 auth only) | `TD-TC-002412.json` line 14 | `5560878723104998 / 27081018943478964112` | TC-000987 | [TD-TC-000987.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000987.json#L14) line 14 | `B5560878723104998 / TEST/CARDHOLDER^2708101541…8100220112 (67 chars)` | FIXED |

---

<a id="cd-19"></a>

### CD-19 – Traceability matrix points to a folder that is not delivered; follow-on legs never traced – FIXED

| Check | Previous run | New run |
|---|---|---|
| Trace rows (`reports/traceability_matrix_full.json` → `flat_rows`) | 299 | 307 |
| Folders the test data paths point to | `qe_shaped_test_data/` | `test_data/` |
| `flat_rows` whose test data path does not exist | 249 | 0 |
| Trace entries (all levels) with a missing path / of them marked `file_exists: true` | 498 / 249 | 0 / 0 |
| Delivered test data files never referenced by the trace | 320 of 569 | 0 of 633 |
| Entries with `file_exists: false` | 50 | 18 – ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)), ? ((no test data)) |

Check it yourself: open `5-11-out/mastercard_fleet/reports/traceability_matrix_full.json` and search `"test_data_file"` – the paths now start with `test_data/` (previously `qe_shaped_test_data/`, a folder that was never delivered), and every leg file of a flow is listed.

---

## 3. Review items of the previous log (not defects)

| ID | Item | Previous run | New run | Result |
|---|---|---|---|---|
| RV-01 | Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY | YYMM: 44 | YYMM: 48 | Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME) – e.g. [TD-TC-000858.step2.reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000858.step2.reversal.json#L15) line 15 |
| RV-02 | Fleet Data Segment (Segment 101) length 035 (021 / 030 / 035 depend on the spec reading) | 035: 492, 031: 5 | 035: 556, 031: 5 | Unchanged – still a review item for SME / BUYPASS – e.g. [TD-TC-000746.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000746.json#L29) line 29 |

## 4. Seen while re-checking (not in the previous log)

The Totals Request of two settlement flows does not match the transactions before it (Section 9.2, p.72; the same rule is defect D-21 of the Interlink Debit report). It was not in the previous log for this program.

Previous run: 16 test cases; new run: 16 test cases.

| New TC | Template | Totals file | Actual | Expected |
|---|---|---|---|---|
| TC-000756 | T8-PUR-TOR-SETTLE | [TD-TC-000756.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000782 | T8-PUR-TOR-SETTLE | [TD-TC-000782.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000782.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000808 | T8-PUR-TOR-SETTLE | [TD-TC-000808.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000808.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000841 | T8-PUR-TOR-SETTLE | [TD-TC-000841.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000841.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000867 | T8-PUR-TOR-SETTLE | [TD-TC-000867.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000867.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000895 | T8-PUR-TOR-SETTLE | [TD-TC-000895.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000895.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000921 | T8-PUR-TOR-SETTLE | [TD-TC-000921.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000921.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000947 | T8-PUR-TOR-SETTLE | [TD-TC-000947.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000947.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Leave out the FL bucket and send Grand Total 00000000 |
| TC-000967 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000967.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000967.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000976 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000976.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000976.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000985 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000985.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000985.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-000995 | T8-AUT-COMPLETION-SETTLE | [TD-TC-000995.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000995.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001004 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001004.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-001004.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001014 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001014.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-001014.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001023 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001023.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-001023.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |
| TC-001032 | T8-AUT-COMPLETION-SETTLE | [TD-TC-001032.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-001032.step3.totals.json#L21) line 21 | count 00001, amount 00000000, grand total 00000000 | Count 00001, amount 00001500, grand total 00001500 |

#### Explained step by step (new run)

**TC-000756 – T8-PUR-TOR-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 11

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-000756.step1.purchase.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step1.purchase.json#L19) line 19 | purchase (Transaction Type `0`) | `1500` = $15.00 | no response (time-out) | no response (timed out), so not in the totals |
| 2 | [TD-TC-000756.step2.timeout-reversal.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step2.timeout-reversal.json#L19) line 19 | timeout reversal (Transaction Type `Z`) | `1500` = $15.00 | Response Code `0` | its original was never approved, so nothing to take out |

So the FL bucket holds **count 0, amount $0.00** – nothing occurred for the card type, so Fields 15–17 are not sent (p.237).

Totals Request [TD-TC-000756.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'FL  '` | `not sent` | **WRONG** |
| Card Type Total Count (Element 16) | 21 | `00001` | `not sent` | **WRONG** |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `not sent` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00000000` | OK |
| Segment Length (Element 84) | 7 | `107` | `090` | **WRONG** |

The test case itself says for this step: *"the timed-out purchase is not in the totals"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 11 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

**TC-000967 – T8-AUT-COMPLETION-SETTLE (EMV Contact Chip)**, `test_cases.jsonl` line 222

| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |
|---|---|---|---|---|---|
| 1 | [TD-TC-000967.step1.auth-only.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000967.step1.auth-only.json#L19) line 19 | auth only (Transaction Type `3`) | `1500` = $15.00 | Response Code `2` | Authorization Only - AO bucket, not in the grand total |
| 2 | [TD-TC-000967.step2.completion.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000967.step2.completion.json#L19) line 19 | completion (Transaction Type `0`) | `1500` = $15.00 | Response Code `0` | count +1, amount +$15.00 |

So the FL bucket holds **count 1, amount $15.00**.

Totals Request [TD-TC-000967.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000967.step3.totals.json):

| Field | Line | Actual (file) | Expected | Result |
|---|---|---|---|---|
| Card Label (Element 13) | 20 | `'FL  '` | `'FL  '` | OK |
| Card Type Total Count (Element 16) | 21 | `00001` | `00001` | OK |
| Card Type Total Amount (Element 15) | 22 | `00000000` | `00001500` | **WRONG** |
| Grand Total (Element 42) | 19 | `00000000` | `00001500` | **WRONG** |
| Segment Length (Element 84) | 7 | `107` | `107` | OK |

The test case itself says for this step: *"the completion is in the totals; the authorization only is not (no draft capture)"* – the generated values contradict it.

Where the wrong values come from: `test_cases.jsonl` line 222 → `steps[2].request` → `"Totals Data Segment"` → `"Grand Total"` = `00000000` (`method: qe_example_value`), `"Card Type Total Count"` = `00001` (`method: qe_example_value`), `"Card Type Total Amount"` = `00000000` (`method: qe_example_value`). The generator copied a fixed example for this template instead of computing the totals from the steps (the settlement templates that pass use `method: spec_rule_value`).

Re-run (from `regression-artifact-validator`): `python scripts/verify-defect-fixes.py`

