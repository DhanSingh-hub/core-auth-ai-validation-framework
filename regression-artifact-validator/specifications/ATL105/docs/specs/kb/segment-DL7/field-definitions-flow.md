# Segment DL7 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL7 payload] --> F1["Field 1 '^' - Element 24"]
    F1 --> F2[Field 2 Segment Length - Element 84, N3, excludes '^']
    F2 --> F3[Field 3 Download Data - Element 232, AN up to 100]
    F3 --> E[Entry: Table ID n3 + Table Length n3 + Table Data]
    E --> Q{Table ID?}
    Q -->|001| L[Site Language: Table Length 003, ISO 639-2 code]
    Q -->|002| P[Postal Code: Table Length 013, international postal code]
    Q -->|Other| R1[REVIEW_REQUIRED - not in Appendix W]
    L --> C{Data length = Table Length?}
    P --> C
    C -->|No| X4[Fail SEGDL7-R-004]
    C -->|Yes| N{More entries?}
    N -->|Yes| E
    N -->|No| OK[Fields valid]
```

Source: [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
