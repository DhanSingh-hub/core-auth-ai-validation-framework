# Segment DL4 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL4 payload] --> F1["Field 1 '@' - Element 24"]
    F1 --> F2[Field 2 New Software Version - Element 57, AN fixed 8]
    F2 --> F3[Field 3 Software Terminal Record ID - Element 95, AN fixed 13]
    F3 --> F4[Field 4 Software Load Phone Number - Element 91, AN variable up to 18]
    F4 --> F5[Field 5 Software Load Request Date - Element 92, N6 MMDDYY]
    F5 --> F6[Field 6 Software Load Request Time - Element 93, N4 HHMM]
    F6 --> F7["Field 7 Software Load Type - Element 94, 'F' full / 'P' partial"]
    F7 --> F8["Field 8 '~' - Element 34"]
    F8 --> Q{Each field within its definition?}
    Q -->|Wrong width for 57/95| X5a[Fail SEGDL4-R-005]
    Q -->|Phone Number shorter than 18| R3[REVIEW_REQUIRED - SEGDL4-SME-003]
    Q -->|Invalid date/time| X5b[Fail SEGDL4-R-005]
    Q -->|Load Type not F/P| X5c[Fail SEGDL4-R-005]
    Q -->|Valid| OK[Fields valid]
```

Source: [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
