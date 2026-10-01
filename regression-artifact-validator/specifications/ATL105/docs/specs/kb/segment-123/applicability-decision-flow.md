# Segment 123 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 123 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG123-R-003 Segment 123 maximum length is 186 alphanumeric characters; originates at the Device}
    R1 -->|Fail| X1[Reject citing SEG123-R-003]
    R1 -->|Pass| R2{SEG123-R-005 Segment 123 is required on all initial and recurring transactions involving tokenized data, and on transactions that include MasterCard Token/DSRP or Visa TAVV data}
    R2 -->|Fail| X2[Reject citing SEG123-R-005]
    R2 -->|Pass| R3{"SEG123-R-011 When both MasterCard DSRP cryptogram and SecureCode/Identity Check 3DS AAV are present in the same request, the AAV must be carried in Segment 111 Table ID 36 (UCAF) and the Token/DSRP cryptogram must be carried in Segment 123 Element 237 (TAVV) — the two fields are not interchangeable and both may be required simultaneously"}
    R3 -->|Fail| X3[Reject citing SEG123-R-011]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
