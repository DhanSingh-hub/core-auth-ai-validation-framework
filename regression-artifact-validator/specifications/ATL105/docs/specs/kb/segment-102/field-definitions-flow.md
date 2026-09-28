# Segment 102 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 102 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG102-R-001 Segment Type is 102}
    R1 -->|Fail| X1[Reject citing SEG102-R-001]
    R1 -->|Pass| R2{SEG102-R-003 Service Level uses an allowed value}
    R2 -->|Fail| X2[Reject citing SEG102-R-003]
    R2 -->|Pass| R3{SEG102-R-004 Number of Products is 01-10 and zero-padded}
    R3 -->|Fail| X3[Reject citing SEG102-R-004]
    R3 -->|Pass| R4{SEG102-R-010 Product Code is a valid value per Appendix F}
    R4 -->|Fail| X4[Reject citing SEG102-R-010]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| R5{SEG102-R-011 Unit of Measure uses an allowed value}
    R5 -->|Fail| X5[Reject citing SEG102-R-011]
    R5 -->|Pass| R6{SEG102-R-012 Quantity preserves the assumed-decimal-place l…}
    R6 -->|Fail| X6[Reject citing SEG102-R-012]
    R6 -->|Pass| R7{SEG102-R-013 Unit Price preserves the assumed-decimal-place…}
    R7 -->|Fail| X7[Reject citing SEG102-R-013]
    R7 -->|Pass| R8{SEG102-R-014 Product Amount is present for every product en…}
    R8 -->|Fail| X8[Reject citing SEG102-R-014]
    R8 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
