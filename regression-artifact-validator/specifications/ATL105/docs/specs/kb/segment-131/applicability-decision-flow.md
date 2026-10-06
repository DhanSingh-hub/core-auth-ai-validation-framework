# Segment 131 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 131 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG131-R-001 Section 12.21 states Segment 131 'always appears in Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response's own layout table places it at Field No. 17/18/19/20 of Data Section No. 2 (following Segments 112, 115, and 120) — these two placement statements are contradictory"}
    R1 -->|Fail| X1[Reject citing SEG131-R-001]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG131-R-002 Segment 131 is response-only and conditional: it follows in the EMV Financial Transaction Response only when EMV data is required, alongside Segments 112 and 115/120}
    R2 -->|Fail| X2[Reject citing SEG131-R-002]
    R2 -->|Pass| R3{"SEG131-R-009 The EMV Additional Information Section (fields 6-8: Indicator, Length, Information) repeats per EMV Additional Information Indicator for a maximum total length of 2,800 bytes — a DIFFERENT cap than Segment 130's 2,000-byte limit for the structurally identical section"}
    R3 -->|Fail| X3[Reject citing SEG131-R-009]
    R3 -->|Pass| R4{"SEG131-R-012 In the EMV Financial Transaction Response, Segment 131 always follows Segment 130's response counterpart context — i.e., it appears after Segments 112 (Field 16/17/18) and 115/120 (Field 17/18/19) when those are also present"}
    R4 -->|Fail| X4[Reject citing SEG131-R-012]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
