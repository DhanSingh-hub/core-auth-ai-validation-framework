# Segment DL1 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment DL1 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL1-R-002 Segment DL1 uses NO Field Separators instead i…}
    R1 -->|Fail| X1[Reject citing SEGDL1-R-002]
    R1 -->|Pass| R2{SEGDL1-R-004 Number of Card Types Element 59 identifies how…}
    R2 -->|Fail| X2[Reject citing SEGDL1-R-004]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
