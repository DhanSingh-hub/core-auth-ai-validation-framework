# Segment DL5 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment DL5 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL5-R-001 Segment DL5 is used to download an application…}
    R1 -->|Fail| X1[Reject citing SEGDL5-R-001]
    R1 -->|Pass| R2{SEGDL5-R-002 Segment DL5 maximum length is 64 alphanumeric…}
    R2 -->|Fail| X2[Reject citing SEGDL5-R-002]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
