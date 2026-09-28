# Segment DL8 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment DL8 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL8-R-002 Data Type Indicator is fixed value '%' Element…}
    R1 -->|Fail| X1[Reject citing SEGDL8-R-002]
    R1 -->|Pass| R2{SEGDL8-R-003 Floor Limit Data repeats per RID, for a maximu…}
    R2 -->|Fail| X2[Reject citing SEGDL8-R-003]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
