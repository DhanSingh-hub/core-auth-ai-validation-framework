# Segment DL5 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL5 — Software IP Load Data Segment · **Sources:** 12.46, 11.7.4.2 · **Oracle:** [rule catalog](coverage/segment-DL5-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL5 behaviour | Evidence |
|---|---|---|
| "Segment DL5 uses a 3-digit Segment Length (Element 84)" | No Segment Length; framed by `$` and `~` | 12.46 layout |
| "An empty middle field keeps its separator" | No separators; all fields fixed width | 12.46 note |
| DL4 and DL5 "mutually exclusive" | Both Required in the Software Load Response | 11.7.4.2 |

## Position Map

| Positions | Field |
|---|---|
| 1 | `$` |
| 2-9 | New Software Version |
| 10-22 | Software Terminal Record ID |
| 23-52 | Software Load IP/URL Address |
| 53-58 | Request Date MMDDYY |
| 59-62 | Request Time HHMM |
| 63 | Software Load Type |
| 64 | `~` |

## Closure Gate

DL5 is not closeable while `SEGDL5-SME-001` to `SEGDL5-SME-003` and `SEGDL4-SME-002` are open.
