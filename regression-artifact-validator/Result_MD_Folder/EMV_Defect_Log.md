# EMV Defect Log – ATL105 MasterCard Credit (EMV Contact & Contactless)

| Item | Value |
|---|---|
| Purpose | Reviewer log: every defect instance found by the EMV field-level validation, with raw evidence and the exact ATL105 text, so each entry can be checked manually against the spec |
| Generated | 2026-11-03 14:35 |
| Specification | BUYPASS ATL105 Message Format Specifications **2026-4** (PDF page numbers; `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf`) |
| Test data | `C:\Users\F5QGZOJ\Downloads\31-10-out\mastercard_credit\test_data` |
| Test cases | `test_cases/test_cases.jsonl` (EMV_CONTACT + EMV_CONTACTLESS: 112; 104 with test data; 8 device-only T11 have no message) |
| Source of instances | `Result_MD_Folder/EMV_Full_Validation/TC-*_ATL105_Field_Validation.md` (FAIL rows). Values and line numbers below are re-read directly from the TD files. |

## How to use this log

1. Pick a defect section (D-01 … D-11). Read the **ATL105 reference** (open the PDF at the page given) and the **manual check steps**.
2. For each instance row, open the TD file at the listed line and compare the **Generated** value with the **Expected** value.
3. Tick **Reviewer verdict**: `☐ Correct` (defect confirmed) / `☐ Incorrect` (not a defect) and add a note.
4. Defects whose basis is partly outside ATL105 (EMV Book 3, test-solution KB rules, the TC's own objective) are marked in the **Basis** line – judge them on that basis.

Line numbers refer to the TD JSON file as delivered. Chip-tag values are decoded from `EMVChipData` (see Appendix A).

## Summary

| ID | Defect | Basis | Instances (TD files) | Test cases | Reviewer: defect confirmed? |
|---|---|---|---|---|---|
| [D-01](#d-01) | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | ATL105 | 126 | 104 | ☐ Yes ☐ No |
| [D-02](#d-02) | EMV Additional Information (Element 118) does not contain EMV Table Data (Appendix T) | ATL105 | 126 | 104 | ☐ Yes ☐ No |
| [D-03](#d-03) | Expired-card test (T2-EXPIRED-EMV) uses a card that is not expired | Test objective + ATL105 | 2 | 2 | ☐ Yes ☐ No |
| [D-04](#d-04) | Online-PIN test (T3-ONLINE-PIN) carries no PIN block and a signature CVM | Test objective + ATL105 (PIN) + EMV Book 3 (CVM codes) | 4 | 4 | ☐ Yes ☐ No |
| [D-04b](#d-04b) | Offline-PIN test (T3-OFFLINE-PIN) carries a signature CVM | Test objective + EMV Book 3 (CVM codes) | 2 | 2 | ☐ Yes ☐ No |
| [D-05](#d-05) | Split-tender card B: chip Amount Authorized (9F02) = full original amount | ATL105 p.84 + test-solution rule SEG130-R-009 | 4 | 4 | ☐ Yes ☐ No |
| [D-06](#d-06) | Totals Card Label (Element 13) is 2 bytes instead of 4 | ATL105 | 18 | 18 | ☐ Yes ☐ No |
| [D-07](#d-07) | Segment 100 length calculated from an out-of-range (8-digit) runtime amount | ATL105 | 10 | 10 | ☐ Yes ☐ No |
| [D-08](#d-08) | FSA Product Code segment (Segment 102) length is wrong | ATL105 | 4 | 4 | ☐ Yes ☐ No |
| [D-09](#d-09) | Cashback test: chip data ignores the cash amount | Test-solution rule SEG130-R-009 + EMV Book 3 (not an ATL105 field-format rule) | 2 | 2 | ☐ Yes ☐ No |
| [D-10](#d-10) | Totals category sent with count `00000` | ATL105 | 2 | 2 | ☐ Yes ☐ No |
| [D-11](#d-11) | Return after settlement reuses the Totals Request's Sequence Number (TD differs from its TC) | ATL105 + the TC's own value | 4 | 4 | ☐ Yes ☐ No |
| | **Total** | | **304** | **104** | |

---

## D-01

### EMV Card Sequence Number (Element 188) is 2 bytes instead of 3

**Basis:** ATL105  
**Expected:** 3 characters, zero-padded (e.g. `001`), or 3 spaces if Tag 5F34 is absent

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 188 EMV Card Sequence Number | p.477 | Character Type: AN  Maximum Length: 3 bytes. Representation: Fixed length of three bytes, right-aligned and padded with zeroes. Space-filled if EMV Tag 5F34 is not provided by the EMV chip card … Valid Codes/Values: 000—099 … Spaces: Three spaces if EMV Tag 5F34 is not provided |
| 12.20 EMV Request Data Segment, Field 4 | p.270 | 188 EMV Card Sequence Number, Max. Len. 3, C |

**Manual check steps:**

1. Open the TD file at the listed line; count the characters of `EMVCardSequenceNumber`.
2. Read Element 188 on p.477: the representation is *fixed* length 3, zero-padded.
3. Optional: decode Tag 5F34 in `EMVChipData` (search `5F3401`, next 2 hex chars) and confirm the value matches (only width differs).

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-01-001 | TC-001722 | CONTACT | PURCHASE / T1-PUR | `TD-TC-001722.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-002 | TC-001723 | CONTACT | PURCHASE / T2-EXPIRED-EMV | `TD-TC-001723.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-003 | TC-001724 | CONTACT | PURCHASE / T3-ONLINE-PIN | `TD-TC-001724.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-004 | TC-001725 | CONTACT | PURCHASE / T3-OFFLINE-PIN | `TD-TC-001725.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-005 | TC-001726 | CONTACT | PURCHASE / T4-PUR-SPLIT | `TD-TC-001726.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-006 | TC-001726 | CONTACT | PURCHASE / T4-PUR-SPLIT | `TD-TC-001726.step2.split-tender.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-007 | TC-001727 | CONTACT | PURCHASE / T4-PUR-REVERSAL | `TD-TC-001727.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-008 | TC-001728 | CONTACT | PURCHASE / T4-PUR-TOR | `TD-TC-001728.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-009 | TC-001729 | CONTACT | PURCHASE / T4-PUR-NO-PARTIAL | `TD-TC-001729.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-010 | TC-001730 | CONTACT | PURCHASE / T5-PUR-REVERSAL | `TD-TC-001730.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-011 | TC-001731 | CONTACT | PURCHASE / T6-PUR-NO-RESPONSE | `TD-TC-001731.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-012 | TC-001732 | CONTACT | PURCHASE / T6-PUR-LATE | `TD-TC-001732.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-013 | TC-001733 | CONTACT | PURCHASE / T6-PUR-UNREADABLE | `TD-TC-001733.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-014 | TC-001734 | CONTACT | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001734.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-015 | TC-001734 | CONTACT | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001734.step3.next-transaction.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-016 | TC-001735 | CONTACT | PURCHASE / T6-PUR-TOR-RETRY | `TD-TC-001735.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-017 | TC-001736 | CONTACT | PURCHASE / T8-PUR-SETTLE | `TD-TC-001736.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-018 | TC-001737 | CONTACT | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001737.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-019 | TC-001738 | CONTACT | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001738.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-020 | TC-001738 | CONTACT | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001738.step2.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-021 | TC-001739 | CONTACT | PURCHASE / T8-PUR-TOR-SETTLE | `TD-TC-001739.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-022 | TC-001740 | CONTACT | PURCHASE / T8-PUR-READONLY | `TD-TC-001740.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-023 | TC-001741 | CONTACT | PURCHASE / T8-PUR-AUTOCLOSE | `TD-TC-001741.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-024 | TC-001742 | CONTACT | PURCHASE / T9-FSA | `TD-TC-001742.json` | 63 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-025 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step1.purchase.json` | 63 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-026 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step2.split-tender.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-027 | TC-001744 | CONTACT | PURCHASE / T10-TOR-UNMATCHED | `TD-TC-001744.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-028 | TC-001745 | CONTACT | PURCHASE / T10-TOR-AFTER-REVERSAL | `TD-TC-001745.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-029 | TC-001746 | CONTACT | PURCHASE / T10-PARTIAL-REVERSE-REQUESTED | `TD-TC-001746.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-030 | TC-001747 | CONTACT | PURCHASE / T10-CASHBACK | `TD-TC-001747.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-031 | TC-001748 | CONTACT | PURCHASE / T1-FLEET-CARD-AS-CREDIT | `TD-TC-001748.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-032 | TC-001749 | CONTACT | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001749.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-033 | TC-001749 | CONTACT | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001749.step2.next-transaction.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-034 | TC-001750 | CONTACT | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001750.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-035 | TC-001750 | CONTACT | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001750.step3.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-036 | TC-001751 | CONTACT | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001751.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-037 | TC-001751 | CONTACT | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001751.step3.next-transaction.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-038 | TC-001752 | CONTACT | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001752.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-039 | TC-001752 | CONTACT | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001752.step2.resubmitted.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-040 | TC-001753 | CONTACT | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001753.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-041 | TC-001753 | CONTACT | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001753.step3.resubmitted.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-042 | TC-001754 | CONTACT | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001754.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-043 | TC-001754 | CONTACT | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001754.step3.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-044 | TC-001755 | CONTACT | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001755.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-045 | TC-001755 | CONTACT | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001755.step3.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-046 | TC-001756 | CONTACT | PURCHASE / T10-PUR-DECLINED | `TD-TC-001756.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-047 | TC-001759 | CONTACTLESS | PURCHASE / T1-PUR | `TD-TC-001759.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-048 | TC-001760 | CONTACTLESS | PURCHASE / T2-CL-TO-CONTACT | `TD-TC-001760.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-049 | TC-001761 | CONTACTLESS | PURCHASE / T2-EXPIRED-EMV | `TD-TC-001761.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-050 | TC-001762 | CONTACTLESS | PURCHASE / T3-ONLINE-PIN | `TD-TC-001762.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-051 | TC-001763 | CONTACTLESS | PURCHASE / T4-PUR-SPLIT | `TD-TC-001763.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-052 | TC-001763 | CONTACTLESS | PURCHASE / T4-PUR-SPLIT | `TD-TC-001763.step2.split-tender.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-053 | TC-001764 | CONTACTLESS | PURCHASE / T4-PUR-REVERSAL | `TD-TC-001764.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-054 | TC-001765 | CONTACTLESS | PURCHASE / T4-PUR-TOR | `TD-TC-001765.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-055 | TC-001766 | CONTACTLESS | PURCHASE / T4-PUR-NO-PARTIAL | `TD-TC-001766.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-056 | TC-001767 | CONTACTLESS | PURCHASE / T5-PUR-REVERSAL | `TD-TC-001767.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-057 | TC-001768 | CONTACTLESS | PURCHASE / T6-PUR-NO-RESPONSE | `TD-TC-001768.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-058 | TC-001769 | CONTACTLESS | PURCHASE / T6-PUR-LATE | `TD-TC-001769.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-059 | TC-001770 | CONTACTLESS | PURCHASE / T6-PUR-UNREADABLE | `TD-TC-001770.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-060 | TC-001771 | CONTACTLESS | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001771.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-061 | TC-001771 | CONTACTLESS | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001771.step3.next-transaction.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-062 | TC-001772 | CONTACTLESS | PURCHASE / T6-PUR-TOR-RETRY | `TD-TC-001772.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-063 | TC-001773 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE | `TD-TC-001773.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-064 | TC-001774 | CONTACTLESS | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001774.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-065 | TC-001775 | CONTACTLESS | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001775.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-066 | TC-001775 | CONTACTLESS | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001775.step2.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-067 | TC-001776 | CONTACTLESS | PURCHASE / T8-PUR-TOR-SETTLE | `TD-TC-001776.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-068 | TC-001777 | CONTACTLESS | PURCHASE / T8-PUR-READONLY | `TD-TC-001777.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-069 | TC-001778 | CONTACTLESS | PURCHASE / T8-PUR-AUTOCLOSE | `TD-TC-001778.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-070 | TC-001779 | CONTACTLESS | PURCHASE / T9-FSA | `TD-TC-001779.json` | 63 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-071 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step1.purchase.json` | 63 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-072 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step2.split-tender.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-073 | TC-001781 | CONTACTLESS | PURCHASE / T10-TOR-UNMATCHED | `TD-TC-001781.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-074 | TC-001782 | CONTACTLESS | PURCHASE / T10-TOR-AFTER-REVERSAL | `TD-TC-001782.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-075 | TC-001783 | CONTACTLESS | PURCHASE / T10-PARTIAL-REVERSE-REQUESTED | `TD-TC-001783.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-076 | TC-001784 | CONTACTLESS | PURCHASE / T10-CASHBACK | `TD-TC-001784.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-077 | TC-001785 | CONTACTLESS | PURCHASE / T1-FLEET-CARD-AS-CREDIT | `TD-TC-001785.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-078 | TC-001786 | CONTACTLESS | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001786.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-079 | TC-001786 | CONTACTLESS | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001786.step2.next-transaction.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-080 | TC-001787 | CONTACTLESS | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001787.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-081 | TC-001787 | CONTACTLESS | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001787.step3.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-082 | TC-001788 | CONTACTLESS | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001788.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-083 | TC-001788 | CONTACTLESS | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001788.step3.next-transaction.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-084 | TC-001789 | CONTACTLESS | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001789.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-085 | TC-001789 | CONTACTLESS | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001789.step2.resubmitted.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-086 | TC-001790 | CONTACTLESS | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001790.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-087 | TC-001790 | CONTACTLESS | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001790.step3.resubmitted.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-088 | TC-001791 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001791.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-089 | TC-001791 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001791.step3.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-090 | TC-001792 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001792.step1.purchase.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-091 | TC-001792 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001792.step3.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-092 | TC-001793 | CONTACTLESS | PURCHASE / T10-PUR-DECLINED | `TD-TC-001793.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-093 | TC-002020 | CONTACT | AUTH_ONLY / T1-AUT | `TD-TC-002020.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-094 | TC-002021 | CONTACT | AUTH_ONLY / T3-ONLINE-PIN | `TD-TC-002021.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-095 | TC-002022 | CONTACT | AUTH_ONLY / T3-OFFLINE-PIN | `TD-TC-002022.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-096 | TC-002023 | CONTACT | AUTH_ONLY / T4-AUT-COMPLETION | `TD-TC-002023.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-097 | TC-002024 | CONTACT | AUTH_ONLY / T4-AUT-CANCEL | `TD-TC-002024.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-098 | TC-002025 | CONTACT | AUTH_ONLY / T5-AUT-COMPLETION | `TD-TC-002025.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-099 | TC-002026 | CONTACT | AUTH_ONLY / T5-AUT-COMPLETION-DIFF | `TD-TC-002026.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-100 | TC-002027 | CONTACT | AUTH_ONLY / T5-AUT-CANCEL | `TD-TC-002027.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-101 | TC-002028 | CONTACT | AUTH_ONLY / T5-AUT-COMPLETION-REVERSAL | `TD-TC-002028.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-102 | TC-002029 | CONTACT | AUTH_ONLY / T6-AUT-COMPLETION-TOR | `TD-TC-002029.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-103 | TC-002030 | CONTACT | AUTH_ONLY / T8-AUT-COMPLETION-SETTLE | `TD-TC-002030.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-104 | TC-002031 | CONTACT | AUTH_ONLY / T10-AUT-TOR | `TD-TC-002031.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-105 | TC-002032 | CONTACT | AUTH_ONLY / T10-AUT-REVERSE-8 | `TD-TC-002032.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-106 | TC-002033 | CONTACT | AUTH_ONLY / T10-CANCEL-MISMATCH | `TD-TC-002033.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-107 | TC-002035 | CONTACTLESS | AUTH_ONLY / T1-AUT | `TD-TC-002035.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-108 | TC-002036 | CONTACTLESS | AUTH_ONLY / T2-CL-TO-CONTACT | `TD-TC-002036.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-109 | TC-002037 | CONTACTLESS | AUTH_ONLY / T3-ONLINE-PIN | `TD-TC-002037.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-110 | TC-002038 | CONTACTLESS | AUTH_ONLY / T4-AUT-COMPLETION | `TD-TC-002038.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-111 | TC-002039 | CONTACTLESS | AUTH_ONLY / T4-AUT-CANCEL | `TD-TC-002039.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-112 | TC-002040 | CONTACTLESS | AUTH_ONLY / T5-AUT-COMPLETION | `TD-TC-002040.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-113 | TC-002041 | CONTACTLESS | AUTH_ONLY / T5-AUT-COMPLETION-DIFF | `TD-TC-002041.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-114 | TC-002042 | CONTACTLESS | AUTH_ONLY / T5-AUT-CANCEL | `TD-TC-002042.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-115 | TC-002043 | CONTACTLESS | AUTH_ONLY / T5-AUT-COMPLETION-REVERSAL | `TD-TC-002043.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-116 | TC-002044 | CONTACTLESS | AUTH_ONLY / T6-AUT-COMPLETION-TOR | `TD-TC-002044.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-117 | TC-002045 | CONTACTLESS | AUTH_ONLY / T8-AUT-COMPLETION-SETTLE | `TD-TC-002045.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-118 | TC-002046 | CONTACTLESS | AUTH_ONLY / T10-AUT-TOR | `TD-TC-002046.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-119 | TC-002047 | CONTACTLESS | AUTH_ONLY / T10-AUT-REVERSE-8 | `TD-TC-002047.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-120 | TC-002048 | CONTACTLESS | AUTH_ONLY / T10-CANCEL-MISMATCH | `TD-TC-002048.step1.auth-only.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-121 | TC-002140 | CONTACT | RETURN / T1-RET | `TD-TC-002140.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-122 | TC-002141 | CONTACT | RETURN / T5-RET-VOID | `TD-TC-002141.step1.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-123 | TC-002143 | CONTACTLESS | RETURN / T1-RET | `TD-TC-002143.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-124 | TC-002144 | CONTACTLESS | RETURN / T5-RET-VOID | `TD-TC-002144.step1.return.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-125 | TC-002164 | CONTACT | BALANCE_INQUIRY / T1-BAL | `TD-TC-002164.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |
| D-01-126 | TC-002165 | CONTACTLESS | BALANCE_INQUIRY / T1-BAL | `TD-TC-002165.json` | 47 | `01` (2 chars); 5F34=`01` | `001` | ☐ Correct ☐ Incorrect | |

---

## D-02

### EMV Additional Information (Element 118) does not contain EMV Table Data (Appendix T)

**Basis:** ATL105  
**Expected:** Information = `EMVYES` or `EMVNOT` (exact E192 framing value open: see R-14 in the consolidated report)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 191 EMV Additional Information Indicator | p.479 | Valid Codes/Values: 001 EMV table data |
| Element 192 EMV Additional Information Length | p.480 | Fixed length of 3 digits … Valid Codes/Values: 001–985 |
| Element 118 EMV Additional Information | p.439 | When EMV Additional Information Indicator has a value of 001, EMV table data is included here … Please refer to Appendix T. |
| Appendix T-1 EMV Table Data | p.701 | Table ID n3 Fixed value: 001 · Table Length n Fixed · Maximum Length n6 Len.: 6 · Request Valid Codes/Values: EMVYES (device is able to receive EMV Table Data) / EMVNOT (device is unable to receive EMV Table Data). It is only sent in an EMV Transaction request. |

**Manual check steps:**

1. Open the TD file at the listed lines (Indicator, Length, Information).
2. Indicator `001` → E118 p.439 says the content is *EMV table data*, defined in Appendix T.
3. Appendix T-1 p.701: the only request values are `EMVYES` / `EMVNOT` (6 characters).
4. Compare: the generated Information is 984 random characters → not an allowed value. Element 192 = `984` is within 001–985 and equals the content length, so E192 alone is *not* the defect.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-02-001 | TC-001722 | CONTACT | PURCHASE / T1-PUR | `TD-TC-001722.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-002 | TC-001723 | CONTACT | PURCHASE / T2-EXPIRED-EMV | `TD-TC-001723.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-003 | TC-001724 | CONTACT | PURCHASE / T3-ONLINE-PIN | `TD-TC-001724.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-004 | TC-001725 | CONTACT | PURCHASE / T3-OFFLINE-PIN | `TD-TC-001725.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-005 | TC-001726 | CONTACT | PURCHASE / T4-PUR-SPLIT | `TD-TC-001726.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `1J0FVRBA9PUY…MIFT99` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-006 | TC-001726 | CONTACT | PURCHASE / T4-PUR-SPLIT | `TD-TC-001726.step2.split-tender.json` | 50–52 | ind `001`, len `984`, info `SSWN9VUQDGPT…9QRD5X` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-007 | TC-001727 | CONTACT | PURCHASE / T4-PUR-REVERSAL | `TD-TC-001727.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `LN1MLBBYVZDJ…1QMNXU` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-008 | TC-001728 | CONTACT | PURCHASE / T4-PUR-TOR | `TD-TC-001728.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `0XER3AU0AQAQ…415MLV` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-009 | TC-001729 | CONTACT | PURCHASE / T4-PUR-NO-PARTIAL | `TD-TC-001729.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-010 | TC-001730 | CONTACT | PURCHASE / T5-PUR-REVERSAL | `TD-TC-001730.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `13ELX9RB06PA…KNM76H` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-011 | TC-001731 | CONTACT | PURCHASE / T6-PUR-NO-RESPONSE | `TD-TC-001731.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `UJMIP5GWWUC7…JH0PVR` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-012 | TC-001732 | CONTACT | PURCHASE / T6-PUR-LATE | `TD-TC-001732.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `GXZSPE4YRWLA…ER9DHD` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-013 | TC-001733 | CONTACT | PURCHASE / T6-PUR-UNREADABLE | `TD-TC-001733.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `WZHL6GZGB0N3…I4AYOU` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-014 | TC-001734 | CONTACT | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001734.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `2PTM6KTCLKLY…6U3RX4` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-015 | TC-001734 | CONTACT | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001734.step3.next-transaction.json` | 50–52 | ind `001`, len `984`, info `DPSI790EB811…R9NR2M` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-016 | TC-001735 | CONTACT | PURCHASE / T6-PUR-TOR-RETRY | `TD-TC-001735.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `RASAIRSNASXO…K9J4X9` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-017 | TC-001736 | CONTACT | PURCHASE / T8-PUR-SETTLE | `TD-TC-001736.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `MLG7N5ALXUX2…J2NFQH` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-018 | TC-001737 | CONTACT | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001737.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `F5KLARTDCE11…OKWBEV` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-019 | TC-001738 | CONTACT | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001738.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `70GIMLYEZ9C8…YNPBMH` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-020 | TC-001738 | CONTACT | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001738.step2.return.json` | 50–52 | ind `001`, len `984`, info `2US4664ZI573…GDITU1` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-021 | TC-001739 | CONTACT | PURCHASE / T8-PUR-TOR-SETTLE | `TD-TC-001739.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `BQJ49AA9CJRS…RAMCEL` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-022 | TC-001740 | CONTACT | PURCHASE / T8-PUR-READONLY | `TD-TC-001740.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `PS8FQ588A20D…SBK1FZ` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-023 | TC-001741 | CONTACT | PURCHASE / T8-PUR-AUTOCLOSE | `TD-TC-001741.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `SVUS8O5F64M7…C23IM6` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-024 | TC-001742 | CONTACT | PURCHASE / T9-FSA | `TD-TC-001742.json` | 66–68 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-025 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step1.purchase.json` | 66–68 | ind `001`, len `984`, info `5SWFDSNK4PKV…BC5H78` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-026 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step2.split-tender.json` | 50–52 | ind `001`, len `984`, info `QHXNDF2A9NFI…RLOPT7` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-027 | TC-001744 | CONTACT | PURCHASE / T10-TOR-UNMATCHED | `TD-TC-001744.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-028 | TC-001745 | CONTACT | PURCHASE / T10-TOR-AFTER-REVERSAL | `TD-TC-001745.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `65XS0MVLZI05…P4UZ06` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-029 | TC-001746 | CONTACT | PURCHASE / T10-PARTIAL-REVERSE-REQUESTED | `TD-TC-001746.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `QMR7TTJ6TNAS…UJL5WM` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-030 | TC-001747 | CONTACT | PURCHASE / T10-CASHBACK | `TD-TC-001747.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-031 | TC-001748 | CONTACT | PURCHASE / T1-FLEET-CARD-AS-CREDIT | `TD-TC-001748.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-032 | TC-001749 | CONTACT | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001749.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `Y44DFKXZZ3B4…Y006I9` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-033 | TC-001749 | CONTACT | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001749.step2.next-transaction.json` | 50–52 | ind `001`, len `984`, info `OMTYRO7C82I8…KF6D6M` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-034 | TC-001750 | CONTACT | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001750.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `KJOYJU9B6FAU…6MYIRL` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-035 | TC-001750 | CONTACT | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001750.step3.return.json` | 50–52 | ind `001`, len `984`, info `6CG1E0CZEIGG…SFDH1Z` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-036 | TC-001751 | CONTACT | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001751.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `CXMV6BVSE53S…XW4G7X` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-037 | TC-001751 | CONTACT | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001751.step3.next-transaction.json` | 50–52 | ind `001`, len `984`, info `4L3O0O3K1M9B…GGOK6C` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-038 | TC-001752 | CONTACT | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001752.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `93944SKMHGNB…6X1SDO` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-039 | TC-001752 | CONTACT | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001752.step2.resubmitted.json` | 50–52 | ind `001`, len `984`, info `FFZITQJGLWIG…HTR11L` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-040 | TC-001753 | CONTACT | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001753.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `D3QP6HTX5QTP…3DV1ZC` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-041 | TC-001753 | CONTACT | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001753.step3.resubmitted.json` | 50–52 | ind `001`, len `984`, info `25HFJJKGCJ1R…THJK9B` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-042 | TC-001754 | CONTACT | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001754.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `0XWVF9TOT5D6…H4P76G` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-043 | TC-001754 | CONTACT | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001754.step3.return.json` | 50–52 | ind `001`, len `984`, info `L4QPMXX1UJKV…GPSLIY` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-044 | TC-001755 | CONTACT | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001755.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `07N4E637TF1V…9V96F6` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-045 | TC-001755 | CONTACT | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001755.step3.return.json` | 50–52 | ind `001`, len `984`, info `T1OCH0FFD8EU…GZ2PGC` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-046 | TC-001756 | CONTACT | PURCHASE / T10-PUR-DECLINED | `TD-TC-001756.json` | 50–52 | ind `001`, len `984`, info `XPXZRQBX814S…K8MMTF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-047 | TC-001759 | CONTACTLESS | PURCHASE / T1-PUR | `TD-TC-001759.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-048 | TC-001760 | CONTACTLESS | PURCHASE / T2-CL-TO-CONTACT | `TD-TC-001760.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-049 | TC-001761 | CONTACTLESS | PURCHASE / T2-EXPIRED-EMV | `TD-TC-001761.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-050 | TC-001762 | CONTACTLESS | PURCHASE / T3-ONLINE-PIN | `TD-TC-001762.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-051 | TC-001763 | CONTACTLESS | PURCHASE / T4-PUR-SPLIT | `TD-TC-001763.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `IPCK443KAYGR…V8ZV3S` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-052 | TC-001763 | CONTACTLESS | PURCHASE / T4-PUR-SPLIT | `TD-TC-001763.step2.split-tender.json` | 50–52 | ind `001`, len `984`, info `R5ZSEAKMG6OP…86X6NE` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-053 | TC-001764 | CONTACTLESS | PURCHASE / T4-PUR-REVERSAL | `TD-TC-001764.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `1XVVK0TK45AE…QS3BG9` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-054 | TC-001765 | CONTACTLESS | PURCHASE / T4-PUR-TOR | `TD-TC-001765.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `GV0IQYUWSZ4I…TL1VTC` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-055 | TC-001766 | CONTACTLESS | PURCHASE / T4-PUR-NO-PARTIAL | `TD-TC-001766.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-056 | TC-001767 | CONTACTLESS | PURCHASE / T5-PUR-REVERSAL | `TD-TC-001767.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `UK2O2I0MT45N…UTE7EU` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-057 | TC-001768 | CONTACTLESS | PURCHASE / T6-PUR-NO-RESPONSE | `TD-TC-001768.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `GDWI40YBDAYQ…9WBVLY` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-058 | TC-001769 | CONTACTLESS | PURCHASE / T6-PUR-LATE | `TD-TC-001769.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `ZL4KUOV5P5A3…T3AUWZ` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-059 | TC-001770 | CONTACTLESS | PURCHASE / T6-PUR-UNREADABLE | `TD-TC-001770.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `OSTL15Z6RBF6…9R1TGJ` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-060 | TC-001771 | CONTACTLESS | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001771.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `ZVS0GPWZVMC3…FPXIAK` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-061 | TC-001771 | CONTACTLESS | PURCHASE / T6-PUR-TOR-NEXT | `TD-TC-001771.step3.next-transaction.json` | 50–52 | ind `001`, len `984`, info `MIIE9QFY08M5…Z0WH6V` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-062 | TC-001772 | CONTACTLESS | PURCHASE / T6-PUR-TOR-RETRY | `TD-TC-001772.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `F8CF4M1N7J4V…II3KOX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-063 | TC-001773 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE | `TD-TC-001773.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `54NUZL6BDCNL…2HIR1O` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-064 | TC-001774 | CONTACTLESS | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001774.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `91OAYXHWGL9B…0B6QRO` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-065 | TC-001775 | CONTACTLESS | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001775.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `G8Z649YSZMN6…BC58XP` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-066 | TC-001775 | CONTACTLESS | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001775.step2.return.json` | 50–52 | ind `001`, len `984`, info `CAN1IZCPYUQV…7GF8AT` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-067 | TC-001776 | CONTACTLESS | PURCHASE / T8-PUR-TOR-SETTLE | `TD-TC-001776.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `P3I1TGK1MUAG…A2DQE8` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-068 | TC-001777 | CONTACTLESS | PURCHASE / T8-PUR-READONLY | `TD-TC-001777.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `ZLRMECH37CIE…E52C3F` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-069 | TC-001778 | CONTACTLESS | PURCHASE / T8-PUR-AUTOCLOSE | `TD-TC-001778.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `1WU8XJWAG53E…2OAYOY` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-070 | TC-001779 | CONTACTLESS | PURCHASE / T9-FSA | `TD-TC-001779.json` | 66–68 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-071 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step1.purchase.json` | 66–68 | ind `001`, len `984`, info `SY3SV3QK72DV…FI5H2D` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-072 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step2.split-tender.json` | 50–52 | ind `001`, len `984`, info `CRUVICRCVFL7…BXODGJ` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-073 | TC-001781 | CONTACTLESS | PURCHASE / T10-TOR-UNMATCHED | `TD-TC-001781.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-074 | TC-001782 | CONTACTLESS | PURCHASE / T10-TOR-AFTER-REVERSAL | `TD-TC-001782.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `H4E080L3UDQT…7C9V7G` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-075 | TC-001783 | CONTACTLESS | PURCHASE / T10-PARTIAL-REVERSE-REQUESTED | `TD-TC-001783.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `QA4WQVZKCU4P…5JLIZV` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-076 | TC-001784 | CONTACTLESS | PURCHASE / T10-CASHBACK | `TD-TC-001784.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-077 | TC-001785 | CONTACTLESS | PURCHASE / T1-FLEET-CARD-AS-CREDIT | `TD-TC-001785.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-078 | TC-001786 | CONTACTLESS | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001786.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `KFTCT49XKTGY…NHUN9K` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-079 | TC-001786 | CONTACTLESS | PURCHASE / T5-PUR-NEXT-PUR | `TD-TC-001786.step2.next-transaction.json` | 50–52 | ind `001`, len `984`, info `DJ9IQD30Y1K6…RA4JLH` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-080 | TC-001787 | CONTACTLESS | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001787.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `I07O0FP7VVJG…OBASXB` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-081 | TC-001787 | CONTACTLESS | PURCHASE / T5-PUR-VOID-DECLINED-RETURN | `TD-TC-001787.step3.return.json` | 50–52 | ind `001`, len `984`, info `82YO641VYK2C…1CSLJS` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-082 | TC-001788 | CONTACTLESS | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001788.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `BADAK7T0RFPF…LIJTWQ` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-083 | TC-001788 | CONTACTLESS | PURCHASE / T6-PUR-LINE-DOWN | `TD-TC-001788.step3.next-transaction.json` | 50–52 | ind `001`, len `984`, info `40U2TJFFASAN…PPPDD8` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-084 | TC-001789 | CONTACTLESS | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001789.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `Y9OTCFOGDTTQ…3Y2QW0` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-085 | TC-001789 | CONTACTLESS | PURCHASE / T6-PUR-TIMEOUT-DECLINE-RESUBMIT | `TD-TC-001789.step2.resubmitted.json` | 50–52 | ind `001`, len `984`, info `YGEW24UDF8J2…2OVBJQ` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-086 | TC-001790 | CONTACTLESS | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001790.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `60HW8V7EL9NM…A8DQXR` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-087 | TC-001790 | CONTACTLESS | PURCHASE / T6-PUR-LATE-RESUBMIT | `TD-TC-001790.step3.resubmitted.json` | 50–52 | ind `001`, len `984`, info `4X9ZMM8VA33N…21QYGP` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-088 | TC-001791 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001791.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `ZK5TCHRJJHGQ…8WAKC9` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-089 | TC-001791 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001791.step3.return.json` | 50–52 | ind `001`, len `984`, info `D6YDDJ4SMEPI…SFD065` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-090 | TC-001792 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001792.step1.purchase.json` | 50–52 | ind `001`, len `984`, info `I5ZT67W510YR…8PERIP` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-091 | TC-001792 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001792.step3.return.json` | 50–52 | ind `001`, len `984`, info `RR2K9VC176KU…1EBYC9` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-092 | TC-001793 | CONTACTLESS | PURCHASE / T10-PUR-DECLINED | `TD-TC-001793.json` | 50–52 | ind `001`, len `984`, info `EAMHC50ODTH4…GAJQCX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-093 | TC-002020 | CONTACT | AUTH_ONLY / T1-AUT | `TD-TC-002020.json` | 50–52 | ind `001`, len `984`, info `KCBSDCMON3U9…N2UH0B` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-094 | TC-002021 | CONTACT | AUTH_ONLY / T3-ONLINE-PIN | `TD-TC-002021.json` | 50–52 | ind `001`, len `984`, info `KCBSDCMON3U9…N2UH0B` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-095 | TC-002022 | CONTACT | AUTH_ONLY / T3-OFFLINE-PIN | `TD-TC-002022.json` | 50–52 | ind `001`, len `984`, info `KCBSDCMON3U9…N2UH0B` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-096 | TC-002023 | CONTACT | AUTH_ONLY / T4-AUT-COMPLETION | `TD-TC-002023.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `J1T7S7S95Y5N…7TXMV2` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-097 | TC-002024 | CONTACT | AUTH_ONLY / T4-AUT-CANCEL | `TD-TC-002024.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `JVSP5P91VA1G…ZEGXM7` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-098 | TC-002025 | CONTACT | AUTH_ONLY / T5-AUT-COMPLETION | `TD-TC-002025.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `JD66AN96A6P8…JL4VA4` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-099 | TC-002026 | CONTACT | AUTH_ONLY / T5-AUT-COMPLETION-DIFF | `TD-TC-002026.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `F2VQDVSLFK7F…AEEE3F` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-100 | TC-002027 | CONTACT | AUTH_ONLY / T5-AUT-CANCEL | `TD-TC-002027.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `LJ3VZT9PCM9R…VC83NO` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-101 | TC-002028 | CONTACT | AUTH_ONLY / T5-AUT-COMPLETION-REVERSAL | `TD-TC-002028.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `PAXFY3PZ4SSI…57GMHW` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-102 | TC-002029 | CONTACT | AUTH_ONLY / T6-AUT-COMPLETION-TOR | `TD-TC-002029.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `7AATWNU7KE53…GH3Y5X` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-103 | TC-002030 | CONTACT | AUTH_ONLY / T8-AUT-COMPLETION-SETTLE | `TD-TC-002030.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `VFB1YF2FXASY…4MPC1A` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-104 | TC-002031 | CONTACT | AUTH_ONLY / T10-AUT-TOR | `TD-TC-002031.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `PW6FBBVYON9U…8KO3DX` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-105 | TC-002032 | CONTACT | AUTH_ONLY / T10-AUT-REVERSE-8 | `TD-TC-002032.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `06195O6IOVNT…AQ7DUN` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-106 | TC-002033 | CONTACT | AUTH_ONLY / T10-CANCEL-MISMATCH | `TD-TC-002033.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `FH705QC2TLVP…BVC73D` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-107 | TC-002035 | CONTACTLESS | AUTH_ONLY / T1-AUT | `TD-TC-002035.json` | 50–52 | ind `001`, len `984`, info `I3WM3MXS9PPO…IFP72G` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-108 | TC-002036 | CONTACTLESS | AUTH_ONLY / T2-CL-TO-CONTACT | `TD-TC-002036.json` | 50–52 | ind `001`, len `984`, info `I3WM3MXS9PPO…IFP72G` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-109 | TC-002037 | CONTACTLESS | AUTH_ONLY / T3-ONLINE-PIN | `TD-TC-002037.json` | 50–52 | ind `001`, len `984`, info `I3WM3MXS9PPO…IFP72G` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-110 | TC-002038 | CONTACTLESS | AUTH_ONLY / T4-AUT-COMPLETION | `TD-TC-002038.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `7LB8CEDF37JK…SWLWMF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-111 | TC-002039 | CONTACTLESS | AUTH_ONLY / T4-AUT-CANCEL | `TD-TC-002039.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `0IEOC5A1CEB0…489WEW` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-112 | TC-002040 | CONTACTLESS | AUTH_ONLY / T5-AUT-COMPLETION | `TD-TC-002040.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `4F97G3LR9CBZ…42MD2W` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-113 | TC-002041 | CONTACTLESS | AUTH_ONLY / T5-AUT-COMPLETION-DIFF | `TD-TC-002041.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `0J975M4DGTHW…6DHPFS` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-114 | TC-002042 | CONTACTLESS | AUTH_ONLY / T5-AUT-CANCEL | `TD-TC-002042.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `JDHP7V8YQ32O…PO6DDR` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-115 | TC-002043 | CONTACTLESS | AUTH_ONLY / T5-AUT-COMPLETION-REVERSAL | `TD-TC-002043.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `FKE4LYID1PHK…THSP5Y` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-116 | TC-002044 | CONTACTLESS | AUTH_ONLY / T6-AUT-COMPLETION-TOR | `TD-TC-002044.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `P7YPRVKK6IDN…547PQC` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-117 | TC-002045 | CONTACTLESS | AUTH_ONLY / T8-AUT-COMPLETION-SETTLE | `TD-TC-002045.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `KCTN88Q1WFN3…L7GRWO` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-118 | TC-002046 | CONTACTLESS | AUTH_ONLY / T10-AUT-TOR | `TD-TC-002046.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `HU7YXTHB6KWF…ITUV8Z` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-119 | TC-002047 | CONTACTLESS | AUTH_ONLY / T10-AUT-REVERSE-8 | `TD-TC-002047.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `H4POBEJOBG7W…XJDK9N` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-120 | TC-002048 | CONTACTLESS | AUTH_ONLY / T10-CANCEL-MISMATCH | `TD-TC-002048.step1.auth-only.json` | 50–52 | ind `001`, len `984`, info `B5KH55I3KZ70…OK4T9U` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-121 | TC-002140 | CONTACT | RETURN / T1-RET | `TD-TC-002140.json` | 50–52 | ind `001`, len `984`, info `6037QIOWU9XT…KDG0TN` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-122 | TC-002141 | CONTACT | RETURN / T5-RET-VOID | `TD-TC-002141.step1.return.json` | 50–52 | ind `001`, len `984`, info `U5H80I9T31ZF…JL0IKR` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-123 | TC-002143 | CONTACTLESS | RETURN / T1-RET | `TD-TC-002143.json` | 50–52 | ind `001`, len `984`, info `5SR54E6DR0BH…LPMO17` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-124 | TC-002144 | CONTACTLESS | RETURN / T5-RET-VOID | `TD-TC-002144.step1.return.json` | 50–52 | ind `001`, len `984`, info `0LJ5QU865BGP…KWNWGH` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-125 | TC-002164 | CONTACT | BALANCE_INQUIRY / T1-BAL | `TD-TC-002164.json` | 50–52 | ind `001`, len `984`, info `Z073R179X9R2…NRC7UF` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |
| D-02-126 | TC-002165 | CONTACTLESS | BALANCE_INQUIRY / T1-BAL | `TD-TC-002165.json` | 50–52 | ind `001`, len `984`, info `DIFADBN01UGC…0FY7JM` (984 chars) | `EMVYES` / `EMVNOT` | ☐ Correct ☐ Incorrect | |

---

## D-03

### Expired-card test (T2-EXPIRED-EMV) uses a card that is not expired

**Basis:** Test objective + ATL105  
**Expected:** Expiry (YYMM) earlier than `2611`; chip Tag 5F24 should match

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| 10.14 EMV – expiration date | p.160 | The device does not perform expiration date validation in an EMV card transaction. |
| Element 12 Card Discretionary Block Data | p.372 | Note: For all authorizations, merchants must send a value even for expired cards. |

**Manual check steps:**

1. Read the TC objective ("Expired EMV card -> sent online -> host decides").
2. Open the TD file at the listed line; the first 4 digits of `CardDiscretionaryBlockData` are the expiry YYMM (Track 2).
3. Compare with today's date (YYMM `2611`): the expiry is in the future, so the card is not expired.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-03-001 | TC-001723 | CONTACT | PURCHASE / T2-EXPIRED-EMV | `TD-TC-001723.json` | 15 | `311020127370826` → expiry `3110` (2031-10) | expiry < `2611` | ☐ Correct ☐ Incorrect | |
| D-03-002 | TC-001761 | CONTACTLESS | PURCHASE / T2-EXPIRED-EMV | `TD-TC-001761.json` | 15 | `290720142194190` → expiry `2907` (2029-07) | expiry < `2611` | ☐ Correct ☐ Incorrect | |

---

## D-04

### Online-PIN test (T3-ONLINE-PIN) carries no PIN block and a signature CVM

**Basis:** Test objective + ATL105 (PIN) + EMV Book 3 (CVM codes)  
**Expected:** E33 = DUKPT KSN + PIN block (AN36); 9F34 byte 1 = `02`/`42` (e.g. `420302`)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| 10.14.3 EMV PIN | p.162 | Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer. |
| 10.14.3 EMV PIN | p.162 | MasterCard has mandated that Point-of-Sale devices that support EMV PIN must support both online PIN and offline PIN processing … must have the capability to submit a PIN in the authorization request message |
| Element 33 Encrypted PIN Block Data | p.382 | For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block. |
| EMV Book 3, Annex C3 (outside ATL105) | - | CVM code 02 = Enciphered PIN verified online; 1E = Signature (paper) |

**Manual check steps:**

1. Open the TD file: line of `EncryptedPINBlockData` → empty.
2. Decode Tag 9F34 in `EMVChipData` (search `9F3403`, next 6 hex chars). Byte 1 & 0x3F = CVM code; `5E` → `1E` = signature.
3. Read the TC execution step: "the PIN block is sent online (Encrypted PIN Block Data, DUKPT)".
4. Note: PIN is optional for normal credit (p.162); this defect applies only because the TC objective is online PIN.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-04-001 | TC-001724 | CONTACT | PURCHASE / T3-ONLINE-PIN | `TD-TC-001724.json` | E33: 16; chip: 49 | E33 `(empty)`; 9F34 `5E0302` (CVM code 1E) | E33 = KSN+PIN block; 9F34 code 02/42 | ☐ Correct ☐ Incorrect | |
| D-04-002 | TC-001762 | CONTACTLESS | PURCHASE / T3-ONLINE-PIN | `TD-TC-001762.json` | E33: 16; chip: 49 | E33 `(empty)`; 9F34 `5E0302` (CVM code 1E) | E33 = KSN+PIN block; 9F34 code 02/42 | ☐ Correct ☐ Incorrect | |
| D-04-003 | TC-002021 | CONTACT | AUTH_ONLY / T3-ONLINE-PIN | `TD-TC-002021.json` | E33: 16; chip: 49 | E33 `(empty)`; 9F34 `5E0302` (CVM code 1E) | E33 = KSN+PIN block; 9F34 code 02/42 | ☐ Correct ☐ Incorrect | |
| D-04-004 | TC-002037 | CONTACTLESS | AUTH_ONLY / T3-ONLINE-PIN | `TD-TC-002037.json` | E33: 16; chip: 49 | E33 `(empty)`; 9F34 `5E0302` (CVM code 1E) | E33 = KSN+PIN block; 9F34 code 02/42 | ☐ Correct ☐ Incorrect | |

---

## D-04b

### Offline-PIN test (T3-OFFLINE-PIN) carries a signature CVM

**Basis:** Test objective + EMV Book 3 (CVM codes)  
**Expected:** 9F34 byte 1 & 0x3F = `01`/`04` (or `03`/`05` with signature)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 190 EMV Chip Data | p.478 | All data elements must contain the exact values that were present on the card to terminal interface. |
| EMV Book 3, Annex C3 (outside ATL105) | - | CVM code 01 = Plaintext PIN verified offline; 04 = Enciphered PIN verified offline; 03/05 = … and signature; 1E = Signature |

**Manual check steps:**

1. Decode Tag 9F34 in `EMVChipData` (search `9F3403`).
2. Byte 1 & 0x3F: `5E` → `1E` = signature, not offline PIN.
3. E33 empty is correct for offline PIN (PIN verified by the chip).

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-04b-001 | TC-001725 | CONTACT | PURCHASE / T3-OFFLINE-PIN | `TD-TC-001725.json` | E33: 16; chip: 49 | 9F34 `5E0302` (CVM code 1E) | 9F34 code 01/04 (03/05 with signature) | ☐ Correct ☐ Incorrect | |
| D-04b-002 | TC-002022 | CONTACT | AUTH_ONLY / T3-OFFLINE-PIN | `TD-TC-002022.json` | E33: 16; chip: 49 | 9F34 `5E0302` (CVM code 1E) | 9F34 code 01/04 (03/05 with signature) | ☐ Correct ☐ Incorrect | |

---

## D-05

### Split-tender card B: chip Amount Authorized (9F02) = full original amount

**Basis:** ATL105 p.84 + test-solution rule SEG130-R-009  
**Expected:** 9F02 = remainder (requested − step-1 Approved Amount), 12-digit zero-padded

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| 10.1.2.1 Partial Approval Transaction | p.84 | Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale. |
| KB rule SEG130-R-009 (test solution) | - | EMV chip values such as authorized amount … shall agree with the Segment 100 transaction context |
| Generator's own flag on EMVChipData | - | 9F02 = request amount |

**Manual check steps:**

1. Open the step-2 (split-tender) TD file; `NonfuelAmount` is the runtime remainder (always < step-1 amount 1500).
2. Decode Tag 9F02 in step-2 `EMVChipData` (search `9F0206`, next 12 digits) → `000000001500` = $15.00.
3. A remainder of a $15.00 sale can never be $15.00 → card B's chip amount is wrong.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-05-001 | TC-001726 | CONTACT | PURCHASE / T4-PUR-SPLIT | `TD-TC-001726.step2.split-tender.json` | amount: 19; chip: 49 | NonfuelAmount `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`; 9F02 `000000001500` | 9F02 = remainder (< 000000001500) | ☐ Correct ☐ Incorrect | |
| D-05-002 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step2.split-tender.json` | amount: 19; chip: 49 | NonfuelAmount `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`; 9F02 `000000001500` | 9F02 = remainder (< 000000001500) | ☐ Correct ☐ Incorrect | |
| D-05-003 | TC-001763 | CONTACTLESS | PURCHASE / T4-PUR-SPLIT | `TD-TC-001763.step2.split-tender.json` | amount: 19; chip: 49 | NonfuelAmount `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`; 9F02 `000000001500` | 9F02 = remainder (< 000000001500) | ☐ Correct ☐ Incorrect | |
| D-05-004 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step2.split-tender.json` | amount: 19; chip: 49 | NonfuelAmount `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`; 9F02 `000000001500` | 9F02 = remainder (< 000000001500) | ☐ Correct ☐ Incorrect | |

---

## D-06

### Totals Card Label (Element 13) is 2 bytes instead of 4

**Basis:** ATL105  
**Expected:** `CC  ` (CC + 2 spaces)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 13 Card Label | p.372 | Character Type: AN Maximum Length: 4 bytes. Representation: Fixed length of four alphanumeric characters; left-aligned and space-filled |
| 12.6 Totals Data Segment, Field 15 | p.235–237 | 15 13 Card Label 4 R Identifies the card type. |

**Manual check steps:**

1. Open the Totals TD file at the listed line; `CardLabel` = `CC` (2 characters).
2. Element 13 p.372: fixed length 4, left-aligned, space-filled → `CC  `.
3. Optional: the declared Segment Length `108` only reproduces when the label is counted as 4 bytes.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-06-001 | TC-001736 | CONTACT | PURCHASE / T8-PUR-SETTLE | `TD-TC-001736.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-002 | TC-001737 | CONTACT | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001737.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-003 | TC-001738 | CONTACT | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001738.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-004 | TC-001739 | CONTACT | PURCHASE / T8-PUR-TOR-SETTLE | `TD-TC-001739.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-005 | TC-001740 | CONTACT | PURCHASE / T8-PUR-READONLY | `TD-TC-001740.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-006 | TC-001741 | CONTACT | PURCHASE / T8-PUR-AUTOCLOSE | `TD-TC-001741.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-007 | TC-001754 | CONTACT | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001754.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-008 | TC-001755 | CONTACT | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001755.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-009 | TC-001773 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE | `TD-TC-001773.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-010 | TC-001774 | CONTACTLESS | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001774.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-011 | TC-001775 | CONTACTLESS | PURCHASE / T8-PUR-RET-SETTLE | `TD-TC-001775.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-012 | TC-001776 | CONTACTLESS | PURCHASE / T8-PUR-TOR-SETTLE | `TD-TC-001776.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-013 | TC-001777 | CONTACTLESS | PURCHASE / T8-PUR-READONLY | `TD-TC-001777.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-014 | TC-001778 | CONTACTLESS | PURCHASE / T8-PUR-AUTOCLOSE | `TD-TC-001778.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-015 | TC-001791 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001791.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-016 | TC-001792 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001792.step2.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-017 | TC-002030 | CONTACT | AUTH_ONLY / T8-AUT-COMPLETION-SETTLE | `TD-TC-002030.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |
| D-06-018 | TC-002045 | CONTACTLESS | AUTH_ONLY / T8-AUT-COMPLETION-SETTLE | `TD-TC-002045.step3.totals.json` | 20 | `'CC'` (2 chars) | `'CC  '` (4 chars) | ☐ Correct ☐ Incorrect | |

---

## D-07

### Segment 100 length calculated from an out-of-range (8-digit) runtime amount

**Basis:** ATL105  
**Expected:** Length recomputed after substitution; amount ≤ 7 digits and < original (remainder) / = approved (reversal)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 58 Nonfuel Amount | p.393 | Note: A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of 7 bytes is allowed for all other card types. |
| 12.1 Standard Message Data Segment, Field 2 | p.223 | Segment Length – Identifies the data segment's length, including Segment Type's length and Field Separators. |
| Element 84 Segment Length | p.406 | calculated using the lengths of all data elements in the segment plus all Field Separators |

**Manual check steps:**

1. Open the TD file; note `SegmentLength` and the runtime placeholder(s) (`NonfuelAmount`, `ApprovalNumber`).
2. Add up the length of every other transmitted Segment 100 field + 1 Field Separator per field (17 fields) — see 'Fixed part' column.
3. Declared − fixed part = characters reserved for the runtime value(s). Approval Number is always 6 (E5 p.368).
4. If the amount width is 8, no valid MasterCard amount (≤ 7 digits) can produce the declared length.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Calculation | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|---|
| D-07-001 | TC-001726 | CONTACT | PURCHASE / T4-PUR-SPLIT | `TD-TC-001726.step2.split-tender.json` | length: 7 | SegmentLength `093`; runtime ['NonfuelAmount'] | fixed part 85 → runtime 8 (amount 8) | amount ≤ 7 digits → length ≤ 92 | ☐ Correct ☐ Incorrect | |
| D-07-002 | TC-001727 | CONTACT | PURCHASE / T4-PUR-REVERSAL | `TD-TC-001727.step2.reversal.json` | length: 7 | SegmentLength `088`; runtime ['NonfuelAmount', 'ApprovalNumber'] | fixed part 74 → runtime 14 = 6 (Approval) + 8 (amount) | amount ≤ 7 digits → length ≤ 87 | ☐ Correct ☐ Incorrect | |
| D-07-003 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step2.split-tender.json` | length: 7 | SegmentLength `093`; runtime ['NonfuelAmount'] | fixed part 85 → runtime 8 (amount 8) | amount ≤ 7 digits → length ≤ 92 | ☐ Correct ☐ Incorrect | |
| D-07-004 | TC-001763 | CONTACTLESS | PURCHASE / T4-PUR-SPLIT | `TD-TC-001763.step2.split-tender.json` | length: 7 | SegmentLength `093`; runtime ['NonfuelAmount'] | fixed part 85 → runtime 8 (amount 8) | amount ≤ 7 digits → length ≤ 92 | ☐ Correct ☐ Incorrect | |
| D-07-005 | TC-001764 | CONTACTLESS | PURCHASE / T4-PUR-REVERSAL | `TD-TC-001764.step2.reversal.json` | length: 7 | SegmentLength `088`; runtime ['NonfuelAmount', 'ApprovalNumber'] | fixed part 74 → runtime 14 = 6 (Approval) + 8 (amount) | amount ≤ 7 digits → length ≤ 87 | ☐ Correct ☐ Incorrect | |
| D-07-006 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step2.split-tender.json` | length: 7 | SegmentLength `093`; runtime ['NonfuelAmount'] | fixed part 85 → runtime 8 (amount 8) | amount ≤ 7 digits → length ≤ 92 | ☐ Correct ☐ Incorrect | |
| D-07-007 | TC-002023 | CONTACT | AUTH_ONLY / T4-AUT-COMPLETION | `TD-TC-002023.step2.completion.json` | length: 7 | SegmentLength `088`; runtime ['NonfuelAmount', 'ApprovalNumber'] | fixed part 74 → runtime 14 = 6 (Approval) + 8 (amount) | amount ≤ 7 digits → length ≤ 87 | ☐ Correct ☐ Incorrect | |
| D-07-008 | TC-002024 | CONTACT | AUTH_ONLY / T4-AUT-CANCEL | `TD-TC-002024.step2.cancellation.json` | length: 7 | SegmentLength `088`; runtime ['NonfuelAmount', 'ApprovalNumber'] | fixed part 74 → runtime 14 = 6 (Approval) + 8 (amount) | amount ≤ 7 digits → length ≤ 87 | ☐ Correct ☐ Incorrect | |
| D-07-009 | TC-002038 | CONTACTLESS | AUTH_ONLY / T4-AUT-COMPLETION | `TD-TC-002038.step2.completion.json` | length: 7 | SegmentLength `088`; runtime ['NonfuelAmount', 'ApprovalNumber'] | fixed part 74 → runtime 14 = 6 (Approval) + 8 (amount) | amount ≤ 7 digits → length ≤ 87 | ☐ Correct ☐ Incorrect | |
| D-07-010 | TC-002039 | CONTACTLESS | AUTH_ONLY / T4-AUT-CANCEL | `TD-TC-002039.step2.cancellation.json` | length: 7 | SegmentLength `088`; runtime ['NonfuelAmount', 'ApprovalNumber'] | fixed part 74 → runtime 14 = 6 (Approval) + 8 (amount) | amount ≤ 7 digits → length ≤ 87 | ☐ Correct ☐ Incorrect | |

---

## D-08

### FSA Product Code segment (Segment 102) length is wrong

**Basis:** ATL105  
**Expected:** Computed length (see table)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| 12.3 Product Code Data Segment | p.229 | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment … A Product Data Field Delimiter always follows Quantity and Unit Price. |
| Appendix B POS Purchase Example | p.505 | 102 ▲ 049 ▲ S 02 001 G 34170 \ 31199 \ 500 \ 411 U 010 \ 2179 \ 1790 ▲  (the same counting gives 49) |

**Manual check steps:**

1. Open the TD file at the listed line (`Product Code Data Segment` → `SegmentLength`).
2. Count: `102`+FS+`len`+FS (8) + ServiceLevel + NumberofProducts + per product (Code+UoM+Quantity+`\`+UnitPrice+`\`+Amount+FS).
3. Check the method on Appendix B (p.505): it reproduces `049` exactly.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Calculation | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|---|
| D-08-001 | TC-001742 | CONTACT | PURCHASE / T9-FSA | `TD-TC-001742.json` | 29 | `033` | 8 + F01 + 894U01\21500\1500▲ = 29 | `029` | ☐ Correct ☐ Incorrect | |
| D-08-002 | TC-001743 | CONTACT | PURCHASE / T9-FSA-SPLIT | `TD-TC-001743.step1.purchase.json` | 29 | `033` | 8 + F01 + 894U01\21500\1500▲ = 29 | `029` | ☐ Correct ☐ Incorrect | |
| D-08-003 | TC-001779 | CONTACTLESS | PURCHASE / T9-FSA | `TD-TC-001779.json` | 29 | `033` | 8 + F01 + 894U01\21500\1500▲ = 29 | `029` | ☐ Correct ☐ Incorrect | |
| D-08-004 | TC-001780 | CONTACTLESS | PURCHASE / T9-FSA-SPLIT | `TD-TC-001780.step1.purchase.json` | 29 | `033` | 8 + F01 + 894U01\21500\1500▲ = 29 | `029` | ☐ Correct ☐ Incorrect | |

---

## D-09

### Cashback test: chip data ignores the cash amount

**Basis:** Test-solution rule SEG130-R-009 + EMV Book 3 (not an ATL105 field-format rule)  
**Expected:** 9C `09`, 9F02 `000000002000`, 9F03 `000000000500`

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| KB rule SEG130-R-009 (test solution) | - | EMV chip values such as authorized amount, transaction type … shall agree with the Segment 100 transaction context |
| EMV Book 3 (outside ATL105) | - | 9F02 Amount Authorized includes cashback; 9F03 Amount Other = cashback; 9C = 09 purchase with cashback |

**Manual check steps:**

1. Open the TD file: `NonfuelAmount` 1500 and `CashAmount` 500.
2. Decode chip tags 9C, 9F02, 9F03 (search `9C01`, `9F0206`, `9F0306`).
3. Compare: 9C should be 09, 9F02 = 2000, 9F03 = 500.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-09-001 | TC-001747 | CONTACT | PURCHASE / T10-CASHBACK | `TD-TC-001747.json` | cash: 21; chip: 49 | Nonfuel `1500`, Cash `500`; 9C `00`, 9F02 `000000001500`, 9F03 `000000000000` | 9C `09`, 9F02 `000000002000`, 9F03 `000000000500` | ☐ Correct ☐ Incorrect | |
| D-09-002 | TC-001784 | CONTACTLESS | PURCHASE / T10-CASHBACK | `TD-TC-001784.json` | cash: 21; chip: 49 | Nonfuel `1500`, Cash `500`; 9C `00`, 9F02 `000000001500`, 9F03 `000000000000` | 9C `09`, 9F02 `000000002000`, 9F03 `000000000500` | ☐ Correct ☐ Incorrect | |

---

## D-10

### Totals category sent with count `00000`

**Basis:** ATL105  
**Expected:** Category omitted when there is no data (or count ≥ 00001)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 16 Card Type Total Count | p.375 | Fixed length of five digits … Valid Codes/Values: 00001–99999 |
| 12.6 Totals Data Segment – Category Total Data Section | p.237 | Field Nos. 15–17 are sent up to a maximum of 18 times … only when data occurs for a card type. |

**Manual check steps:**

1. Open the Totals TD file; `CardTypeTotalCount` = `00000`.
2. E16 p.375 valid range starts at 00001; 12.6 says the category is sent only when data occurs.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-10-001 | TC-001737 | CONTACT | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001737.step3.totals.json` | 21 | count `00000`, amount `00000000` | category omitted / count ≥ 00001 | ☐ Correct ☐ Incorrect | |
| D-10-002 | TC-001774 | CONTACTLESS | PURCHASE / T8-PUR-REV-SETTLE | `TD-TC-001774.step3.totals.json` | 21 | count `00000`, amount `00000000` | category omitted / count ≥ 00001 | ☐ Correct ☐ Incorrect | |

---

## D-11

### Return after settlement reuses the Totals Request's Sequence Number (TD differs from its TC)

**Basis:** ATL105 + the TC's own value  
**Expected:** New Sequence Number for the Return (the TC value, e.g. `017543`)

**ATL105 / rule reference** (open the PDF at this page):

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 86 Sequence Number | p.409 | Processing Rules: Each transaction has a unique Sequence Number. It is included on all transaction types, and originates at the device. |
| 2.3.1 Sequence Number | p.40 | Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number |

**Manual check steps:**

1. Open step 2 (Totals) and step 3 (Return) TD files; compare `SequenceNumber` (same value, same Terminal Identifier).
2. Open the TC in `test_cases.jsonl` → `steps[2].request` → Sequence Number: the TC expects the next number (method `flow_new_value_required`).
3. The Return is a new transaction (not a follow-on of the Totals request) → it needs its own Sequence Number.

| Log ID | TC | Entry mode | Category / template | TD file | Line(s) | Generated | Expected | Reviewer verdict | Notes |
|---|---|---|---|---|---|---|---|---|---|
| D-11-001 | TC-001754 | CONTACT | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001754.step3.return.json` | return: 22; totals (TD-TC-001754.step2.totals.json): 17 | Return `017542` = Totals `017542` | `017543` (TC value) | ☐ Correct ☐ Incorrect | |
| D-11-002 | TC-001755 | CONTACT | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001755.step3.return.json` | return: 22; totals (TD-TC-001755.step2.totals.json): 17 | Return `017552` = Totals `017552` | `017553` (TC value) | ☐ Correct ☐ Incorrect | |
| D-11-003 | TC-001791 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-RETURN | `TD-TC-001791.step3.return.json` | return: 22; totals (TD-TC-001791.step2.totals.json): 17 | Return `017912` = Totals `017912` | `017913` (TC value) | ☐ Correct ☐ Incorrect | |
| D-11-004 | TC-001792 | CONTACTLESS | PURCHASE / T8-PUR-SETTLE-PARTIAL-RETURN | `TD-TC-001792.step3.return.json` | return: 22; totals (TD-TC-001792.step2.totals.json): 17 | Return `017922` = Totals `017922` | `017923` (TC value) | ☐ Correct ☐ Incorrect | |

### D-11 cross-check: sequence-number reuse across all EMV flows

An independent scan of every multi-leg EMV test case found **4** flows where a Totals Request and a financial request on the same terminal share a Sequence Number – all are listed above.

---

## Appendix A – Decoding EMV chip data (Element 190) by hand

`EMVChipData` is hex. Skip the first 4 hex characters (`0145` = packed BCD byte count, as in the Appendix R-1 example `01\06`, p.697). Then repeat:

1. **Tag:** 1 byte; if the low 5 bits of the first byte are all 1 (e.g. `9F`, `5F`), the tag is 2 bytes (Element 190, p.478).
2. **Length:** next byte = number of value bytes.
3. **Value:** next *length* bytes (2 hex characters each).

Shortcuts: `9F3403` + 6 hex = CVM Results; `9F0206` + 12 digits = Amount Authorized; `9F0306` + 12 = Amount Other; `9C01` + 2 = Transaction Type; `5F3401` + 2 = Card Sequence Number.

PowerShell helper:

```powershell
$f='C:\Users\F5QGZOJ\Downloads\31-10-out\mastercard_credit\test_data\TD-TC-001724.json'
$j = Get-Content $f -Raw | ConvertFrom-Json
$hex = $j.'EMV Financial Transaction Request'.'EMV Request Data Segment'.EMVChipData
$i = 4
while ($i -lt $hex.Length) {
  $tag = $hex.Substring($i,2); $i += 2
  if (([Convert]::ToInt32($tag,16) -band 0x1F) -eq 0x1F) { $tag += $hex.Substring($i,2); $i += 2 }
  $len = [Convert]::ToInt32($hex.Substring($i,2),16); $i += 2
  $val = $hex.Substring($i, $len*2); $i += $len*2
  "{0,-5} len={1,-3} value={2}" -f $tag,$len,$val
}
```

CVM code (9F34 byte 1 AND 0x3F; EMV Book 3 Annex C3): `01` plaintext PIN offline · `02` enciphered PIN online · `03` plaintext PIN offline + signature · `04` enciphered PIN offline · `05` enciphered PIN offline + signature · `1E` signature · `1F` no CVM.

## Appendix B – Counting segment lengths (Element 84)

| Segment | Rule | Reference |
|---|---|---|
| 100 | Every transmitted field + 1 Field Separator after each (17 fields). Example in TD: `089` = 72 data characters + 17 FS. | 12.1 p.223; E84 p.406; App. B p.505 (`078`) |
| 102 | FS after Segment Type and Segment Length; Service Level + Number of Products (no FS); per product: Code+UoM+Quantity `\` Unit Price `\` Amount, then `\` (more products) or FS (last). | 12.3 p.229; App. B p.505 (`049`) |
| 105 | Fields 1–14 each + FS; Fields 15–17 (Card Label 4 + Count 5 + Amount 8) without FS. | 12.6 p.235–237 |
| 111 | Type+FS+Length+FS + all Indicator/Length/Data sections (no FS between) + final FS. | 12.10 p.246 |
| 130 | 6 fixed fields each + FS (chip data counted in **bytes**) + EMV Additional Information section + final FS. | 12.20 p.270 |

## Appendix C – Test cases without test data (not in this log)

TC-001757, TC-001758, TC-001794, TC-001795 (T11 SAF blocked / void disabled), TC-002034, TC-002049 (pre-auth disabled), TC-002142, TC-002145 (return disabled): device-configuration scenarios with no host message (`tx_legs` empty).
