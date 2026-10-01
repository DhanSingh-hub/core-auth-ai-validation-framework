# Segment 152 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 152 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG152-R-002 Segment Type fixed 152, Segment Length 4 digits, both Host-sourced}
    R1 -->|Fail| X1[Reject citing SEG152-R-002]
    R1 -->|Pass| R2{"SEG152-R-003 Market Basket Data consists of one DV dataset, up to 10 PI datasets, AND up to 10 PU datasets — one more dataset type than the request (Segment 151, which has no PU datasets)"}
    R2 -->|Fail| X2[Reject citing SEG152-R-003]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-152-rule-catalog.json](coverage/segment-152-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
