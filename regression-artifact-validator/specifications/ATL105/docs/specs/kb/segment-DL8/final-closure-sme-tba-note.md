# Segment DL8 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment · **Sources:** 12.49, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL8-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL8 behaviour | Evidence |
|---|---|---|
| "An empty middle field keeps its separator" | No separators; fixed 26-byte groups | 12.49 |
| "Segment Length … includes the Segment Type and separators" (review question) | Excludes `%`; own-digit counting shared with DL7 | 12.49 field 2; `SEGDL7-SME-005` |
| "Lifecycle: paired messages … correlated values" (generic) | Lifecycle is a host data change that flags all Special terminals | 12.49 |

## Group Position Map (group n, 1-based, after `%` and the 3-digit Segment Length)

| Offset within group | Field |
|---|---|
| 1-10 | RID |
| 11 | Stand-in Indicator |
| 12-23 | Floor Limit |
| 24-26 | BUYPASS RID Card Type |

Group n starts at segment position 5 + 26 × (n − 1).

## Closure Gate

DL8 is not closeable while `SEGDL8-SME-001` to `SEGDL8-SME-003` and `SEGDL7-SME-005` are open.
