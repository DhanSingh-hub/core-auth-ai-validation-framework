# Segment DL3 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL3 — Date and Time Data Segment · **Sources:** 11.7.3, 11.7.3.2, 11.7.1.2, 13.2 (Elements 23, 48, 65) · **Oracle:** [rule catalog](coverage/segment-DL3-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

DL3 values are used later: Current Time sets the device clock, Cut Time schedules settlement, and Password is sent back to BUYPASS in Totals and Electronic Mail Requests. DL3 lifecycle evidence therefore spans the load exchange **and** a later request that uses the loaded values.

## Lifecycle Steps

| Step | Message | Assert | Rule |
|---|---|---|---|
| 1 | Date and Time Load Request | `?` + Terminal Identifier + Load Type `D` | `SEGDL3-R-008` |
| 2 | Date and Time Load Response | DL3 fields; time adjusted for device time zone/DST | `SEGDL3-R-007`, `R-005` |
| 1' | Table Load Request/Response | DL3 as Data Block 3 (Conditional) | `SEGDL3-R-007` |
| 3 | Totals Request (automatic cut time) | Sent 30 minutes before Cut Time if not settled | `SEGDL3-R-009` |
| 4 | Totals / Electronic Mail Request | Password matches the merchant profile | Element 65, `SEGDL3-SME-002` |

## Correlation Points

- Current Time in DL3 must reflect the requesting device's time zone, not BUYPASS host time. Test data must record the device time zone.
- The Password loaded in DL3 is the value later compared by the host in Totals Requests. If the Password is really Device-sourced (12.44), the host cannot load it — this is why `SEGDL3-SME-002` matters.

## SME Questions

1. `SEGDL3-SME-002`: Password source and purpose.
2. `SEGDL3-SME-003`: `~` in the Date and Time Load Response.
3. Does a Date and Time Load ever return an error block (for example for an unknown Terminal Identifier)?
