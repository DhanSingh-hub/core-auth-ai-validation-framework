# Segment 155 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 155 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG155-R-004 Card Status Table 004 valid values: A New Acct…}
    R1 -->|Fail| X1[Reject citing SEG155-R-004]
    R1 -->|Pass| R2{SEG155-R-005 Account Updater Result Code Table 006 is a fix…}
    R2 -->|Fail| X2[Reject citing SEG155-R-005]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-155-rule-catalog.json](coverage/segment-155-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
