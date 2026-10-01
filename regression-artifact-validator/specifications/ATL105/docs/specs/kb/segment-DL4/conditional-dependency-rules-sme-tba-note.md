# Segment DL4 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL4 — Software Dial Load Data Segment · **Sources:** 12.45, 12.46, 11.7.4.2, 10.10 · **Oracle:** [rule catalog](coverage/segment-DL4-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

DL4 has no Conditional fields. Its dependencies are with the device model, with DL5, and with time.

| Dependency | Trigger | Consequence | Rule / status |
|---|---|---|---|
| Device model → DL4 | Vendor-managed device | No DL4 | `SEGDL4-R-001` |
| DL4 ↔ DL5 | Software Load Response | Both segments present | `SEGDL4-R-006` |
| DL4 ↔ DL5 values | Same scheduled load | Version, Record ID, Date, Time, Load Type expected to agree | Derived, `REVIEW_REQUIRED` |
| Request Date/Time → attempt | Scheduled time reached | Device dials Software Load Phone Number | `SEGDL4-R-007` |
| Attempts → decline | 3 unsuccessful attempts | Device prints "decline" | `SEGDL4-R-008` |
| Download Indicator → request | Response with Download Indicator `1` | Device requests the load | `SEGDL4-R-007` |

The previous version of this note recorded "no conditional rules" — the device-model and DL4/DL5 dependencies were missing.

## SME Questions

1. Must DL4 and DL5 carry identical Version / Record ID / Date / Time / Load Type?
2. What happens if the Request Date/Time is already in the past when received?
3. `SEGDL4-SME-002`: how does the device choose dial (DL4) vs IP (DL5)?
