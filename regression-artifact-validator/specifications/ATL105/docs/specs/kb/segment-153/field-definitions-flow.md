# Segment 153 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 153 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG153-R-002 Segment Type fixed 153, Segment Length 3 digit…}
    R1 -->|Fail| X1[Reject citing SEG153-R-002]
    R1 -->|Pass| R2{SEG153-R-003 Network Token Data Element 239 is required, ma…}
    R2 -->|Fail| X2[Reject citing SEG153-R-003]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-153-rule-catalog.json](coverage/segment-153-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
