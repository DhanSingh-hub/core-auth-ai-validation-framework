# Segment DL3 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL3 payload] --> F1["Field 1 ':' - Element 24"]
    F1 --> F2["Field 2 Day of the Week - Element 25, N1, 0-6 (0 = Sunday)"]
    F2 --> F3[Field 3 Current Date - Element 21, N6, MMDDYY]
    F3 --> F4[Field 4 Current Time - Element 22, N4, HHMM, TZ/DST adjusted]
    F4 --> F5[Field 5 Cut Time - Element 23, N4, HHMM]
    F5 --> F6[Field 6 Password - Element 65, N up to 6, right-aligned space-filled]
    F6 --> F7["Field 7 '~' - Element 34"]
    F7 --> Q{Each field within its definition?}
    Q -->|Day outside 0-6, bad month/day| X5[Fail SEGDL3-R-005]
    Q -->|Hour/minute boundary| R4[REVIEW_REQUIRED - SEGDL3-SME-004]
    Q -->|Password not right-aligned digits| X6[Fail SEGDL3-R-006]
    Q -->|Password source question| R2[REVIEW_REQUIRED - SEGDL3-SME-002]
    Q -->|Valid| OK[Fields valid]
```

Source: [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
