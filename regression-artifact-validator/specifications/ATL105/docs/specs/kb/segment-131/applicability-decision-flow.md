# Segment 131 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 131 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG131-R-001 Section 12.21 states Segment 131 'always appea…}
    R1 -->|Fail| X1[Reject citing SEG131-R-001]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG131-R-002 Segment 131 is response-only and conditional:…}
    R2 -->|Fail| X2[Reject citing SEG131-R-002]
    R2 -->|Pass| R3{SEG131-R-009 The EMV Additional Information Section fields…}
    R3 -->|Fail| X3[Reject citing SEG131-R-009]
    R3 -->|Pass| R4{SEG131-R-012 In the EMV Financial Transaction Response, Seg…}
    R4 -->|Fail| X4[Reject citing SEG131-R-012]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
