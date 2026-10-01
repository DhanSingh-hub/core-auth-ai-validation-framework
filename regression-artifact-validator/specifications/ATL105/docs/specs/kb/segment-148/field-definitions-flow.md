# Segment 148 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 148 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG148-R-002 Segment Type fixed 148, Segment Length includes Segment Type's length; both Host-sourced}
    R1 -->|Fail| X1[Reject citing SEG148-R-002]
    R1 -->|Pass| R2{SEG148-R-004 Available Product Information (Element 240) is a fixed-format 17-character sub-structure: Restriction Code (2-3 AN, fuel product group codes 00/01/06/07/08/10/11/12/13), '=' filler, Restriction Code Amount (1-5 AN), ',' filler, Restriction Code Quantity (1-5 AN), space filler, Restriction Code Unit of Measure (1 AN)}
    R2 -->|Fail| X2[Reject citing SEG148-R-004]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-148-rule-catalog.json](coverage/segment-148-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
