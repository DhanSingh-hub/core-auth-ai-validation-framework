# Segment 136 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 136 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG136-R-002 Segment Type is fixed value 136, sourced at th…}
    R1 -->|Fail| X1[Reject citing SEG136-R-002]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG136-R-003 Segment Length Indicator is 3 digits, identifi…}
    R2 -->|Fail| X2[Reject citing SEG136-R-003]
    R2 -->|Pass| R3{SEG136-R-005 Moneris Data Element 213 is required, max 100…}
    R3 -->|Fail| X3[Reject citing SEG136-R-005]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
