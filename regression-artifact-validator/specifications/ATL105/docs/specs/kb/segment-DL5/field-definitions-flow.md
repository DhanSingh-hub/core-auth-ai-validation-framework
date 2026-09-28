# Segment DL5 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment DL5 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL5-R-003 Segment DL5 is structurally identical to Segme…}
    R1 -->|Fail| X1[Reject citing SEGDL5-R-003]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
