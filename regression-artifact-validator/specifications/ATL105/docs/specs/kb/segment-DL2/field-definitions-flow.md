# Segment DL2 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL2 payload] --> F1["Field 1 Data Type Indicator - Element 24 = '!'"]
    F1 --> F2["Field 2 Dial String Type - Element 28 = '1'"]
    F2 --> P[Primary block]
    P --> P3[Field 3 Redial Count - Element 82, N1, 1-3]
    P3 --> P4["Field 4 Access Code - Element 1, <=12, digits and 'B' (C)"]
    P4 --> P5["Field 5 Pause Indicator - Element 66 = 'B' (C)"]
    P5 --> P6[Field 6 Phone Number - Element 75, N <=18]
    P6 --> P7["Field 7 Dial String Terminator - Element 27 = 'A'"]
    P7 --> S[Secondary block fields 8-11 - same elements and rules]
    S --> S12["Field 12 Dial String Terminator - Element 27 = 'F'"]
    S12 --> E["Field 13 End-of-Data Indicator - Element 34 = '~'"]
    E --> Q{Every element within its definition?}
    Q -->|Fixed value wrong| X1[Fail SEGDL2-R-001 / R-002 / R-007]
    Q -->|Redial Count outside 1-3| X6[Fail SEGDL2-R-006]
    Q -->|Phone Number non-digit or > 18| X7[Fail SEGDL2-R-007]
    Q -->|Valid| OK[Fields valid]
```

Source: [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
