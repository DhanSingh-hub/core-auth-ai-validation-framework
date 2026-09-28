# Segment 115 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 115 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG115-R-001 Segment 115 is response-only: it appears only…}
    R1 -->|Fail| X1[Reject citing SEG115-R-001]
    R1 -->|Pass| R2{SEG115-R-002 Segment 115 is conditionally included at Field…}
    R2 -->|Fail| X2[Reject citing SEG115-R-002]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| R3{SEG115-R-010 Whether 'no other data segments are contained…}
    R3 -->|Fail| X3[Reject citing SEG115-R-010]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| R4{SEG115-R-011 In the EMV Financial Transaction Response spec…}
    R4 -->|Fail| X4[Reject citing SEG115-R-011]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
