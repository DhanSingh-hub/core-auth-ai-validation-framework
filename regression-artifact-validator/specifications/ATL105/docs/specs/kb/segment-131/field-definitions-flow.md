# Segment 131 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 131 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG131-R-003 Segment Type is fixed value 131, sourced at the Host (contrast Segment 130's device-sourced Segment Type)}
    R1 -->|Fail| X1[Reject citing SEG131-R-003]
    R1 -->|Pass| R2{SEG131-R-004 Segment Length is 4 digits, one of exactly seven segments across the specification requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131)}
    R2 -->|Fail| X2[Reject citing SEG131-R-004]
    R2 -->|Pass| R3{SEG131-R-008 EMV Chip Data Length (Element 189) and EMV Chip Data (Element 190) are required, mirroring Segment 130's fields, but the field table lists their Source as 'Device' even though Segment 131 as a whole originates at BUYPASS}
    R3 -->|Fail| X3[Reject citing SEG131-R-008]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
