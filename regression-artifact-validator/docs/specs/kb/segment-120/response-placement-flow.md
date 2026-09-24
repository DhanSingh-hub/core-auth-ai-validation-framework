# Response-Only Placement and Ordering Dependency Flow

```mermaid
flowchart TD
    A[Message being assembled] --> B{Request or Response?}
    B -->|Request| X1[REJECT SEG120-R-006: Segment 120 must not appear on a request]
    B -->|Response| C{Financial Transaction Response<br/>or EMV Financial Transaction Response?}
    C -->|Neither| X2[REVIEW_REQUIRED: unconfirmed message-type applicability]
    C -->|Yes| D[Segment 120 may be included]

    D --> E{Ordering relative to other Section 3 companions?}
    E --> F["Always last (BR-263-2) — AUTHORITATIVE"]
    E -.->|previously appeared to conflict with| G["AI template: 3rd of 9-10 —<br/>now assessed as a numeric-sort artifact,<br/>P-01 resolved 2026-09-23"]

    F --> I{Baseline validator ordering check}
    I --> J["HARD-ENFORCED when an explicit segment<br/>order is present in test data"]
```

## Key Distinction

This topic combines two related but separate questions:

1. **Applicability** (`SEG120-R-006`): is Segment 120 even legal in this
   message? Answer: only in a Financial Transaction Response or EMV
   Financial Transaction Response — never a request. This is confirmed by
   three independent AI-generated requirements (`BR-263-1` and both
   `SEGMENT_USED_IN_TRANSACTION` relationship requirements) and is
   hard-enforced when a message-type signal is available.
2. **Ordering** (`SEG120-R-007`): given that Segment 120 is legal in this
   message, where must it sit relative to the other Section 3 companion
   segments? Answer: **at the end**, per `BR-263-2`. This was briefly
   thought to conflict with the AI-generated message template's ordering,
   but that ordering was determined to be a numeric-sort extraction
   artifact (`P-01`, resolved 2026-09-23) — see the
   [coverage SME note](coverage/segment-120-coverage-sme-tba-note.md).
   `SEG120-R-007` is now hard-enforced when an explicit segment order is
   present in test data.

Conflating these two would either over-reject (treating every response with
Segment 120 not literally last as invalid before the ordering question was
settled) or under-validate (never checking applicability at all). Keep them
as two separate rule IDs and two separate test categories.
