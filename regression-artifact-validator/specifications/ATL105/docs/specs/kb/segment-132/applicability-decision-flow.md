# Segment 132 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 132 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG132-R-001 In a CA Public Key File Load Request Segment 132 is in Field No. 3; whether Segments 101, 102, 104 and 111 may accompany it is unresolved}
    R1 -->|Fail| X1[Reject citing SEG132-R-001]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
