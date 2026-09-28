# Segment DL5 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[Segment DL5 payload] --> B{Conditional Fields and Cross-Field Depe… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
