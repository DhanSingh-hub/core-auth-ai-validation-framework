# Segment DL8 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment · **Sources:** 12.49, 11.7, 13.2 (Element 30) · **Oracle:** [rule catalog](coverage/segment-DL8-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

DL8 is the only DL segment whose lifecycle starts with a **host-side data change**: "when this data is changed, the table download flag will be set for all terminals that have this special set". The test must therefore cover many terminals, not one request/response pair.

## Lifecycle Evidence Needed

| Step | Evidence | Status |
|---|---|---|
| Floor-limit change at the host | Precondition | Source-stated |
| Download flag set for every Special terminal | Per-terminal state | Source-stated (`SEGDL8-R-001`) |
| Download Indicator `1` on the next response | Response record | Inferred from 11.7 |
| Table load with DL8 | Request/response record | Position open (`SEGDL8-SME-002`) |
| Non-Special terminal unaffected | Negative record | Source-stated |

## SME Questions

1. Is "table download flag" the same as the `TABL` load flag?
2. Is DL8 sent on every table load for a Special terminal, or only after a change?
