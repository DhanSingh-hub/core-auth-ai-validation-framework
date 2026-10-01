# Segment DL5 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL5 payload] --> F1["Field 1 '$' - Element 24"]
    F1 --> F2[Field 2 New Software Version - Element 57, AN fixed 8]
    F2 --> F3[Field 3 Software Terminal Record ID - Element 95, AN fixed 13]
    F3 --> F4[Field 4 Software Load IP/URL Address - Element 114, AN fixed 30]
    F4 --> F5[Field 5 Request Date - Element 92, MMDDYY]
    F5 --> F6[Field 6 Request Time - Element 93, HHMM]
    F6 --> F7["Field 7 Software Load Type - Element 94, 'F' / 'P'"]
    F7 --> F8["Field 8 '~' - Element 34"]
    F8 --> Q{Each field within its definition?}
    Q -->|Width wrong| X5[Fail SEGDL5-R-005]
    Q -->|IP/URL contains '.', ':' or '/'| R3[REVIEW_REQUIRED - SEGDL5-SME-003]
    Q -->|Date/Time/Load Type invalid| X5b[Fail SEGDL5-R-005]
    Q -->|Valid| OK[Fields valid]
```

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
