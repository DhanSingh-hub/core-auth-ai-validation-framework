# Segment DL7 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment · **Sources:** 12.48, 13.2 (Element 232) · **Oracle:** [rule catalog](coverage/segment-DL7-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

The specification gives DL7 no request/response pairing. The only lifecycle hint is Element 232: DL7 holds "all or some" of the Download Data, which suggests a sequence of DL7 segments when the data exceeds 100 bytes. Until `SEGDL7-SME-004` is answered, DL7 has **no** lifecycle rule, and this is recorded as a gap, not as "none exists".

## What a Lifecycle Test Would Need

| Question | Needed from SME |
|---|---|
| Which request triggers DL7? | Message type (Table Load? a separate load?) |
| Where in the response? | Position relative to DL1-DL6 and `*` |
| Multi-segment data | Ordering, continuation marker, reassembly rule |
| Update trigger | Does a host change set a download flag (as for DL8)? |
