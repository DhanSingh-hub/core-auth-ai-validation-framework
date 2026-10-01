# Segment 140 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 140 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG140-R-002 Segment Type fixed 140, Segment Length 3 digits, both Device-sourced; fields 3 (Terminal Identifier), 5 (Moneris Terminal Identifier), 6 (Moneris Merchant ID) are Device-sourced echoes, while fields 4 (SPDH Header), 7 (Batch Number), 8-14 (Response Display, Number/Dollar totals) are Moneris-sourced"}
    R1 -->|Fail| X1[Reject citing SEG140-R-002]
    R1 -->|Pass| R2{"SEG140-R-003 Debit Dollar Value (218), Credit Dollar Value (220), and Corrections Dollar Value (222) use format +/-9(16)v99 (signed, up to 16 integer digits, 2 implied decimal digits)"}
    R2 -->|Fail| X2[Reject citing SEG140-R-003]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-140-rule-catalog.json](coverage/segment-140-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
