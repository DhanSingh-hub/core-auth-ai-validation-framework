# Segment 150 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 150 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG150-R-003 Segment Type fixed 150, Segment Length 3 digit…}
    R1 -->|Fail| X1[Reject citing SEG150-R-003]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-150-rule-catalog.json](coverage/segment-150-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
