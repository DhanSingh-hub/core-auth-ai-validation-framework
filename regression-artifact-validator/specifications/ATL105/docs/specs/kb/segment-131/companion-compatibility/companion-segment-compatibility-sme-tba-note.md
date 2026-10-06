# Segment 131 Companion-Segment Compatibility: SME and TBA Note

## What Is Known

- Segment 131 is the response-side EMV counterpart to Segment 130 (request-side).
- In the EMV Financial Transaction Response, Segment 131 follows Segments 112 (Additional Information) and 115/120 (Print Data / Print Data 2) when those are also present.
- Segment 131's CA Public Key File Checksum is an echo of Segment 130's — the two segments are coupled across the request/response pair, not independent.

## What Not To Assume

- Do not assume Segment 131 appears in a plain (non-EMV) Financial Transaction Response — it is EMV-specific.
- Do not assume Segment 131's Data Section/field placement is settled — see `SEG131-SME-001` (open).
- Do not assume the CA Public Key File Checksum can differ between Segment 130's request value and Segment 131's response value under normal conditions — a mismatch would indicate a synchronization defect, not valid data.

## Open Question

`SEG131-SME-001` (see the [SME/TBA Input Register](../segment-131-sme-tba-input-register.md)): does Segment 131 belong to Data Section 2 (per the layout table) or Data Section 3 (per Section 12.21's own text)?
