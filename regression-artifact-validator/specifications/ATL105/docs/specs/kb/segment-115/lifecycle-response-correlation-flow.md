# Segment 115 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment 115 payload] --> B{Lifecycle, Response and Message Correlation in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG115-R-012 Segment 115 is plausibly the wire-format vehicle for 'Loyalty Print Data' returned during a Segment 108 Account Inquiry (Update Code I) or Totals Report (Update Code T) flow, inferred from the shared Loyalty Information Version = 2 trigger condition"}
    R1 -->|Fail| X1[Reject citing SEG115-R-012]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{"SEG115-R-013 Whether Segment 115 can appear in the Loyalty Card Transaction Response (Section 11.2.2, which states it mirrors the generic Financial Transaction Response) is inferred but not explicitly restated for Segment 115"}
    R2 -->|Fail| X2[Reject citing SEG115-R-013]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
