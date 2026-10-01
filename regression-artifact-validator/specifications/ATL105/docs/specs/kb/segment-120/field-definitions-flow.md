# Segment 120 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 120 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG120-R-001 Segment Type is 120}
    R1 -->|Fail| X1[Reject citing SEG120-R-001]
    R1 -->|Pass| R2{"SEG120-R-002 Segment Length is present, exactly 4 digits (zero-padded), and equals the segment's actual encoded length including the Segment Type field and Field Separators"}
    R2 -->|Fail| X2[Reject citing SEG120-R-002]
    R2 -->|Pass| R3{SEG120-R-003 Print Data is required and must be present whenever Segment 120 is included}
    R3 -->|Fail| X3[Reject citing SEG120-R-003]
    R3 -->|Pass| R4{SEG120-R-008 Print Data may contain '\' as a line delimiter between lines of receipt text for Blackhawk phone activation/recharge}
    R4 -->|Fail| X4[Reject citing SEG120-R-008]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-120-rule-catalog.json](coverage/segment-120-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
