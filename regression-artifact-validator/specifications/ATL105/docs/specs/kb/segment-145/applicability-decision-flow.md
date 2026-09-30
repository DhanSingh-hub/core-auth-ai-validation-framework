# Segment 145 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 145 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG145-R-001 Segment 145 carries fleet data for enhanced fl…}
    R1 -->|Fail| X1[Reject citing SEG145-R-001]
    R1 -->|Pass| R2{SEG145-R-002 Merchants should NOT send Segment 101 Fleet Da…}
    R2 -->|Fail| X2[Reject citing SEG145-R-002]
    R2 -->|Pass| R3{SEG145-R-003 Only the Prompt Table sub-segment Table ID 004…}
    R3 -->|Fail| X3[Reject citing SEG145-R-003]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
