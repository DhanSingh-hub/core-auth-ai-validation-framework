# Segment DL1 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment DL1 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL1-R-003 Merchant Name 53,24 , Store Number 98,16 , Add…}
    R1 -->|Fail| X1[Reject citing SEGDL1-R-003]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
