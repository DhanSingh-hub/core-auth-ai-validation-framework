# Segment DL5 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL5 — Software IP Load Data Segment · **Sources:** 12.45, 12.46, 11.7.4.2, 10.10 · **Oracle:** [rule catalog](coverage/segment-DL5-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

DL5 has no Conditional fields. Its dependencies are with the device model, with DL4, and with the device's connection type.

| Dependency | Trigger | Consequence | Rule / status |
|---|---|---|---|
| Device model → DL5 | Vendor-managed device | No DL5 | `SEGDL5-R-001` |
| DL4 → DL5 order | Software Load Response | DL5 immediately after DL4 | `SEGDL5-R-006` |
| DL4 ↔ DL5 values | Same scheduled load | Shared fields expected to agree | Derived, `REVIEW_REQUIRED` |
| Connection type → address used | IP-connected device | DL5 address used | 10.10 step 5c; `SEGDL4-SME-002` |

The previous note recorded "no conditional rules"; the DL4 → DL5 ordering and device-model gate were missing.
