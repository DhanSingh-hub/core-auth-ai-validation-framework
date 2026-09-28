# Segment 102 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 102 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG102-R-006 A maximum of ten products is allowed in the se…}
    R1 -->|Fail| X1[Reject citing SEG102-R-006]
    R1 -->|Pass| R2{SEG102-R-018 Segment 102 and Segment 157 are mutually exclu…}
    R2 -->|Fail| X2[Reject citing SEG102-R-018]
    R2 -->|Pass| R3{SEG102-R-019 Segment 102 is not used for Comdata card trans…}
    R3 -->|Fail| X3[Reject citing SEG102-R-019]
    R3 -->|Pass| R4{SEG102-R-023 Segment 102 total length does not exceed 381 c…}
    R4 -->|Fail| X4[Reject citing SEG102-R-023]
    R4 -->|Pass| R5{SEG102-R-025 Segment 143 product order and count match Segm…}
    R5 -->|Fail| X5[Reject citing SEG102-R-025]
    R5 -.->|Provisional| P5[REVIEW_REQUIRED]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
