# Segment 146 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 146 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG146-R-001 Segment Type fixed 146, Segment Length includes Segment Type's length; both Device-sourced (despite this being a response segment, mirroring the sourcing pattern already flagged for Segments 131/136)}
    R1 -->|Fail| X1[Reject citing SEG146-R-001]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG146-R-002 Enhanced Fleet Data (Element 239) is required, max 999 characters, cataloged as: Table 001 (Response Flags, incl. Settlement Indicator: CP/DB/DF/RC/RF/FN), Table 002 (Non-Fuel Product Limits, NOT present in Comdata responses), Table 003 (Fuel Product Limits, using Appendix F product codes), Table 004 (Prompt Formats, using documented edit masks), Table 005 (Customer Information: name/city/state/account code), Table 010 (Additional Response Data: FNAM/LNAM/ATHN/ACCT/DMSG)}
    R2 -->|Fail| X2[Reject citing SEG146-R-002]
    R2 -->|Pass| R3{SEG146-R-004 Table 004 (Prompt Formats) uses a documented edit-mask grammar: '?' (re-prompt), 'A'/'B' (alphanumeric, treated identically since 2015-01-02), 'N' (numeric only), 'I' (free format), 'O' (optional response), 'Z' (capture but don't resend), 'T' (data type N=Number/S=String), 'Mn'/'Xn' (min/max), 'Vn' (exact match), 'Pn' (pattern match using @ /# /* /literal)}
    R3 -->|Fail| X3[Reject citing SEG146-R-004]
    R3 -->|Pass| R4{SEG146-R-005 Table 003 (Fuel Product Limits) uses standard product codes from ATL105 Appendix F (Valid Payment Systems Product Codes) — unlike Table 002's non-fuel category codes}
    R4 -->|Fail| X4[Reject citing SEG146-R-005]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-146-rule-catalog.json](coverage/segment-146-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
