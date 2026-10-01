# Segment DL6 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL6 — Store and Forward Data Segment · **Sources:** 11.7.1, 11.7.1.2, 12.47, Appendix E · **Oracle:** [rule catalog](coverage/segment-DL6-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

DL6 correlates **within** one Table Load Response (with DL1) and **over time** at the device (a window that repeats daily). A DL6 fixture is only meaningful with the DL1 that triggered it.

## Correlation Points

| Point | Evidence required |
|---|---|
| DL1 `173` and DL6 in the same response | One Table Load Response record containing both |
| DL6 after the `*` closing block 3, then `*` | Serialized response (`SEGDL6-R-006`, `SEGDL1-SME-003`) |
| Daily window | Device behaviour at times inside and outside the window (`SEGDL6-R-007`) |
| Window reference time | Device time zone (`SEGDL6-SME-005`) |

## SME Questions

1. `SEGDL6-SME-005`: midnight crossing, Start = End, time basis.
2. Does a later Table Load without `173` remove the blocking window at the device?
