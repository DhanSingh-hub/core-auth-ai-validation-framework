# Segment DL2 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment DL2 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL2-R-002 Dial String Type is fixed value 1 Primary Phon…}
    R1 -->|Fail| X1[Reject citing SEGDL2-R-002]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
