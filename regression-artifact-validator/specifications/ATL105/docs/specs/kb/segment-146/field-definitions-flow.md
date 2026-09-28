# Segment 146 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 146 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG146-R-001 Segment Type fixed 146, Segment Length include…}
    R1 -->|Fail| X1[Reject citing SEG146-R-001]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG146-R-002 Enhanced Fleet Data Element 239 is required, m…}
    R2 -->|Fail| X2[Reject citing SEG146-R-002]
    R2 -->|Pass| R3{SEG146-R-004 Table 004 Prompt Formats uses a documented edi…}
    R3 -->|Fail| X3[Reject citing SEG146-R-004]
    R3 -->|Pass| R4{SEG146-R-005 Table 003 Fuel Product Limits uses standard pr…}
    R4 -->|Fail| X4[Reject citing SEG146-R-005]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-146-rule-catalog.json](coverage/segment-146-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
