# Segment DL5 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL5 — Software IP Load Data Segment · **Sources:** 10.10, 11.7.4 · **Oracle:** [rule catalog](coverage/segment-DL5-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

DL5 shares DL4's eight-step software update lifecycle (10.10). The only difference is step 6: an IP-connected device reaches the device management system at the DL5 IP/URL Address instead of dialing the DL4 phone number.

## Lifecycle Evidence Needed in Test Data

| Step | Evidence |
|---|---|
| Download Indicator `1` in a transaction response | Response record |
| Load request | Request record (type per `SEGDL4-SME-002`) |
| `)` DL4 DL5 | Response record with both segments |
| Up to three load attempts, decline on failure | Device-side evidence (review severity, `SEGDL5-R-007`) |
| Table Load after success | Request/response record |

## SME Questions

1. `SEGDL4-SME-002`: choice between DL4 and DL5 addresses.
2. Is the three-attempt limit per address or overall?
