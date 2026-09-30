# Segment 143 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 143 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG143-R-001 When a Financial Transaction request includes Segment 143, a Product Code Data Segment (Segment 102) MUST also be present, and each entry in Segment 102 should have a corresponding entry in Segment 143, in the same order}
    R1 -->|Fail| X1[Reject citing SEG143-R-001]
    R1 -->|Pass| R2{SEG143-R-005 Tax by Product Data repeats per product for a maximum of 10 products, total variable length up to 360 bytes; each product entry contains Product Code (77) plus up to 3 tax sub-entries (Inclusive/Exclusive flag, Tax Type, Tax Amount)}
    R2 -->|Fail| X2[Reject citing SEG143-R-005]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
