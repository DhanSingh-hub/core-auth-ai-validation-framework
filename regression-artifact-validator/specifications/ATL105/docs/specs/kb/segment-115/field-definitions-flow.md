# Segment 115 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 115 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG115-R-003 Segment Type is fixed value 115}
    R1 -->|Fail| X1[Reject citing SEG115-R-003]
    R1 -->|Pass| R2{SEG115-R-004 Segment Length is 4 digits, one of exactly seven segments across the specification requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131)}
    R2 -->|Fail| X2[Reject citing SEG115-R-004]
    R2 -->|Pass| R3{SEG115-R-008 Print Data (Element 152) is required and identifies the print data being transmitted to the device; per Section 12.14's note this field carries the terms & conditions text for Blackhawk phone activation and recharge receipts}
    R3 -->|Fail| X3[Reject citing SEG115-R-008]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
