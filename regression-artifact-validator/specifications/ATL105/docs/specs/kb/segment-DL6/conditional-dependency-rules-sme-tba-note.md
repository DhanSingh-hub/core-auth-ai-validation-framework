# Segment DL6 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL6 — Store and Forward Data Segment · **Sources:** 12.47, 11.7.1.2, Appendix E · **Oracle:** [rule catalog](coverage/segment-DL6-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

The whole segment is conditional; inside it, the two times depend on each other.

| Dependency | Trigger | Consequence | Rule / status |
|---|---|---|---|
| DL1 `173` ↔ DL6 | Card Type list | DL6 present iff `173` | `SEGDL6-R-001` |
| Start ↔ End | Window definition | Start before End (same day) | Derived; midnight crossing `SEGDL6-SME-005` |
| Window → device | Current time in window | Store-and-forward blocked | `SEGDL6-R-007` |

The previous version said "no conditional rules" for DL6 even though DL6 is the only DL segment whose presence is conditioned by another segment's value.

## TBA Decomposition

```text
BR:  Store-and-forward shall be blocked between Start Time and End Time each day (SEGDL6-R-007).
TS:  Window 0100-0500.
TC:  Transaction at 0300 while host unreachable: not stored for forwarding. Expected: blocked.
TC:  Transaction at 0600 while host unreachable: may be stored. Expected: allowed.
```
