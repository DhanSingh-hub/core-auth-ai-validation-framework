# Segment 115 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 115 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG115-R-001 Segment 115 is response-only: it appears only in the Financial Transaction Response and EMV Financial Transaction Response, never in any Request message}
    R1 -->|Fail| X1[Reject citing SEG115-R-001]
    R1 -->|Pass| R2{"SEG115-R-002 Segment 115 is conditionally included at Field No. 17/18/19 of the Financial Transaction Response's Data Section 2, only when Element 115 (Additional Information Data Segment Flag) indicates it follows AND the request's Loyalty Information Version (Element 150) equals 2"}
    R2 -->|Fail| X2[Reject citing SEG115-R-002]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| R3{"SEG115-R-010 Whether 'no other data segments are contained in the Financial Transaction Response' (Section 12.14) means Segment 115 excludes Segment 112, or only means nothing besides 112 and 115 can appear, is unresolved — the Section 11.1.2 layout table shows Segment 112 (Field 16/17/18) and Segment 115 (Field 17/18/19) as two independently-conditional segments in the SAME response, which appears to contradict a strict reading of 'no other data segments'"}
    R3 -->|Fail| X3[Reject citing SEG115-R-010]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| R4{"SEG115-R-011 In the EMV Financial Transaction Response specifically, Segment 115 may co-occur with Segment 120 (Print Data 2 Segment) at Field 17/18/19, immediately followed by Segment 131 (EMV Response Data Segment) at Field 17/18/19/20"}
    R4 -->|Fail| X4[Reject citing SEG115-R-011]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
