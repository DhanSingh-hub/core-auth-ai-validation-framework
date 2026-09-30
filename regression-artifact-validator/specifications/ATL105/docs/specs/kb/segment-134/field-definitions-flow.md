# Segment 134 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 134 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG134-R-004 Segment Type is fixed value 134, Segment Length is 4 digits}
    R1 -->|Fail| X1[Reject citing SEG134-R-004]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG134-R-005 Settlement Type (Element 198) is required, 1 character, valid values D (Dual message), S (Single message), X (Non-traditional Signature Debit — availability must be confirmed with First Data Project/Relationship Manager)}
    R2 -->|Fail| X2[Reject citing SEG134-R-005]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| R3{SEG134-R-006 Signature Required (Element 199) is required, 1 character, valid values T (required), F (not required), Space (device software logic determines)}
    R3 -->|Fail| X3[Reject citing SEG134-R-006]
    R3 -->|Pass| R4{SEG134-R-007 Receipt Card Description (Element 200) is required, 10 characters, left-justified and space-filled (e.g., 'STAR      ')}
    R4 -->|Fail| X4[Reject citing SEG134-R-007]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-134-rule-catalog.json](coverage/segment-134-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
