# Segment DL7 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment DL7 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL7-R-002 Segment Length Indicator Element 84 for Segmen…}
    R1 -->|Fail| X1[Reject citing SEGDL7-R-002]
    R1 -->|Pass| R2{SEGDL7-R-003 Download Data Element 232, max 100 characters…}
    R2 -->|Fail| X2[Reject citing SEGDL7-R-003]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
