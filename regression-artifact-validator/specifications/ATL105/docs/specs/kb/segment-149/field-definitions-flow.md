# Segment 149 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 149 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG149-R-003 Segment Type fixed 149, Segment Length 3 digits, Terminal Identifier (var.), Price Data (var., pipe-delimited tag:value pairs e.g. 'CASS:03.00') all Device-sourced"}
    R1 -->|Fail| X1[Reject citing SEG149-R-003]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-149-rule-catalog.json](coverage/segment-149-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
