# Segment 151 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 151 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG151-R-004 Segment Type fixed 151, sourced at Host despit…}
    R1 -->|Fail| X1[Reject citing SEG151-R-004]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG151-R-005 Segment Length is 4 digits not 3 , max 2300 ch…}
    R2 -->|Fail| X2[Reject citing SEG151-R-005]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-151-rule-catalog.json](coverage/segment-151-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
