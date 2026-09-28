# Segment DL6 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment DL6 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL6-R-003 Start Time and End Time both use Element 166 a…}
    R1 -->|Fail| X1[Reject citing SEGDL6-R-003]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
