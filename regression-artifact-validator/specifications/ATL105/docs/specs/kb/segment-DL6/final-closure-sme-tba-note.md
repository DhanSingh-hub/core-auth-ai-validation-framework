# Segment DL6 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL6 — Store and Forward Data Segment · **Sources:** 12.47, 11.7.1.2 · **Oracle:** [rule catalog](coverage/segment-DL6-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL6 behaviour | Evidence |
|---|---|---|
| "Segment DL6 uses a 3-digit Segment Length (Element 84)" | No Segment Length; framed by `\` and `~` | 12.47 layout |
| "An empty middle field keeps its separator" | No separators; both times Required | 12.47 note |
| Length 9 accepted without comment | Fields sum to 10 | `SEGDL6-SME-003` |

## Position Map

| Positions | Field |
|---|---|
| 1 | `\` |
| 2-5 | Start Time HHMM |
| 6-9 | End Time HHMM |
| 10 | `~` |

## Closure Gate

DL6 is not closeable while `SEGDL6-SME-001` to `SEGDL6-SME-005` and `SEGDL1-SME-003` are open.
