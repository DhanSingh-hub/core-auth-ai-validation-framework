# Segment DL1 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL1 payload after '#'] --> F2[Field 2 Merchant Name - Element 53, AN 24]
    F2 --> F3[Field 3 Store Number - Element 98, N 16, 0000000000000001-9999999999999999]
    F3 --> F4[Field 4 Address Line 1 - Element 3, AN 24]
    F4 --> F5["Field 5 Address Line 2 - Element 4, AN 21: City 1-12, space, State 14-15, space, ZIP 17-21"]
    F5 --> F6["Field 6 Merchant Phone Number - Element 54, AN 13, (nnn)nnn-nnnn"]
    F6 --> F7[Field 7 Number of Card Types - Element 59, N 2, 01-99]
    F7 --> F8[Field 8 Card Type - Element 14, AN 3, repeated N times]
    F8 --> Q1{Each field present and within its element definition?}
    Q1 -->|Missing| X3[Fail SEGDL1-R-003]
    Q1 -->|Address Line 2 not positional| X10[Fail SEGDL1-R-010]
    Q1 -->|Phone or Store Number malformed| X11[Fail SEGDL1-R-011]
    Q1 -->|Card Type not an Appendix E Table Load code| X9[Fail or REVIEW SEGDL1-R-009]
    Q1 -->|Short 'up to N' value| R2[REVIEW_REQUIRED - padding open, SEGDL1-SME-002]
    Q1 -->|All valid| OK[Fields valid]
```

Source: [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
