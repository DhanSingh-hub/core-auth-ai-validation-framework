# Segment 112 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 112 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG112-R-004 Segment 112 appears only at the end of a Finan…}
    R1 -->|Fail| X1[Reject citing SEG112-R-004]
    R1 -->|Pass| R2{SEG112-R-010 Segment 112 is required in a Financial Transac…}
    R2 -->|Fail| X2[Reject citing SEG112-R-010]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-112-rule-catalog.json](coverage/segment-112-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
