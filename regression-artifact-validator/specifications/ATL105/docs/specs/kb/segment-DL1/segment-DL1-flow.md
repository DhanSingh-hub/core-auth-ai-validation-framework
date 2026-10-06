# Segment DL1 End-to-End Flow

```mermaid
flowchart TD
    A[Merchant profile at BUYPASS] --> B{Load flag = TABL?}
    B -->|No| E1[Downline Load Response = error block + terminating block; no DL1]
    B -->|Yes| C[Device sends Table Load Request: '?' + Terminal Identifier + Load Type 'P' + HW/SW/FW versions]
    C --> D[Host builds Table Load Response]
    D --> F["Data Block 1: ')' + DL1 (Required)"]
    F --> G["DL1: '#' MerchantName StoreNumber AddressLine1 AddressLine2 Phone NumberOfCardTypes CardType x N '~'"]
    G --> H["Data Block 2: DL2 Dial String (Conditional)"]
    H --> I["Data Block 3: DL3 Date and Time (Conditional)"]
    I --> J["End-of-Load '*'"]
    J --> K{Any DL1 Card Type = 173?}
    K -->|Yes| L["Data Block 4: DL6 Store and Forward + End-of-Load '*'"]
    K -->|No| M[No DL6]
    L --> N[Device parses each block by its Data Type Indicator]
    M --> N
    N --> O[Device applies card acceptance and features to the very next transaction]
```

TCP/IP: the Start-of-Data Block Indicator `)` is sent only before Data Block No. 1. Whether `*` appears both after DL3 and after DL6 is open (`SEGDL1-SME-003`).

## Validator Decision Flow (`SegmentDL1PayloadValidator`, planned)

```mermaid
flowchart TD
    P[DL1 payload] --> V1{"Field 1 = '#'?"}
    V1 -->|No| X2[Fail SEGDL1-R-002]
    V1 -->|Yes| V2{Fixed fields 2-6 present at their widths?}
    V2 -->|No| X3[Fail SEGDL1-R-003 / R-006]
    V2 -->|Yes| V3{"Address Line 2 positional and phone '(nnn)nnn-nnnn'?"}
    V3 -->|No| X10[Fail SEGDL1-R-010 / R-011]
    V3 -->|Yes| V4{Number of Card Types 01-99 and equals Card Type count?}
    V4 -->|No| X4[Fail SEGDL1-R-004]
    V4 -->|Yes| V5{Every Card Type an Appendix E Table Load code?}
    V5 -->|No| X9[REVIEW or Fail SEGDL1-R-009]
    V5 -->|Yes| V6{"Last character '~' and total length <= 399?"}
    V6 -->|No| X1[Fail SEGDL1-R-001 / R-002]
    V6 -->|Yes| V7{Card Type 173 present?}
    V7 -->|Yes| V8{DL6 present in same Table Load Response?}
    V8 -->|No| X5[Fail SEGDL1-R-005]
    V8 -->|Yes| OK[Pass]
    V7 -->|No| V9{DL6 present?}
    V9 -->|Yes| X6[Fail SEGDL1-R-005 unexpected DL6]
    V9 -->|No| OK
```
