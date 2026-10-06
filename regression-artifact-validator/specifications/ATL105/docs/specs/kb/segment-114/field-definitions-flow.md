# Segment 114 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 114 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG114-R-003 Segment Type is fixed value 114}
    R1 -->|Fail| X1[Reject citing SEG114-R-003]
    R1 -->|Pass| R2{SEG114-R-004 Segment Length is 4 digits, valid values 0001-1010, representing the segment content length including Segment Type's length and Field Separators}
    R2 -->|Fail| X2[Reject citing SEG114-R-004]
    R2 -->|Pass| R3{"SEG114-R-008 SKU Data (Element 149) is required, alphanumeric, with a maximum length of 1000 characters, and identifies the bar code SKU data"}
    R3 -->|Fail| X3[Reject citing SEG114-R-008]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
