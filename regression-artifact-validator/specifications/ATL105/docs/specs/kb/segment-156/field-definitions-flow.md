# Segment 156 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 156 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG156-R-003 EV Transaction Indicator Table 01 is mandatory…}
    R1 -->|Fail| X1[Reject citing SEG156-R-003]
    R1 -->|Pass| R2{SEG156-R-004 Time-based sub-tables 02 Total Time Plugged In…}
    R2 -->|Fail| X2[Reject citing SEG156-R-004]
    R2 -->|Pass| R3{SEG156-R-005 Charging Reason Code Table 07 uses a documente…}
    R3 -->|Fail| X3[Reject citing SEG156-R-005]
    R3 -->|Pass| R4{SEG156-R-006 Connector Type Table 12 uses a documented Visa…}
    R4 -->|Fail| X4[Reject citing SEG156-R-006]
    R4 -->|Pass| R5{SEG156-R-007 Additional numeric measurement sub-tables exis…}
    R5 -->|Fail| X5[Reject citing SEG156-R-007]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-156-rule-catalog.json](coverage/segment-156-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
