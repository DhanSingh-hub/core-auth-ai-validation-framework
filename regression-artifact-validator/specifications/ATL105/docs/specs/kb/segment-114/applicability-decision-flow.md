# Segment 114 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 114 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG114-R-001 Segment 114 is exclusive to the Loyalty Card Transaction Request's Data Section 3; it is not documented as a companion in the Financial Transaction Request (11.1.1), ECA/TeleCheck Service Transaction Request (11.3.1), or CA Public Key File Load Request (11.9.1) Data Section 3 lists}
    R1 -->|Fail| X1[Reject citing SEG114-R-001]
    R1 -->|Pass| R2{SEG114-R-002 Segment 114 is optional in every Loyalty Card Transaction Request, always in Field No. 5 of Data Section No. 3, alongside the required Segment 108 in Field No. 4}
    R2 -->|Fail| X2[Reject citing SEG114-R-002]
    R2 -->|Pass| R3{SEG114-R-010 Segment 114 may repeat within a single message, once per scanned bar code SKU; it is not limited to zero-or-one occurrence}
    R3 -->|Fail| X3[Reject citing SEG114-R-010]
    R3 -->|Pass| R4{SEG114-R-011 Segment 114 never appears without Segment 108: its only documented context is Data Section 3 of the Loyalty Card Transaction Request, where Segment 108 is required}
    R4 -->|Fail| X4[Reject citing SEG114-R-011]
    R4 -->|Pass| R5{SEG114-R-012 The AI-generated relationship 'The Financial Transaction Request transaction includes SKU Data Segment (Data Segment No. 114)' (source_rule_id REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST) is REJECTED as unsupported by Section 11.1.1's Financial Transaction Request Data Section 3 companion list, which enumerates only Segments 101, 102, 103, 104, and 111}
    R5 -->|Fail| X5[Reject citing SEG114-R-012]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
