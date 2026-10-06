# Segment DL4 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL4 — Software Dial Load Data Segment · **Sources:** 12.45, 11.7.4.2, 10.10 · **Oracle:** [rule catalog](coverage/segment-DL4-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL4 behaviour | Evidence |
|---|---|---|
| "Segment DL4 uses a 3-digit Segment Length (Element 84)" | No Segment Length; framed by `@` and `~` | 12.45 layout |
| "An empty middle field keeps its separator" | No separators | 12.45 note |
| "No lifecycle rules catalogued" | Software update processing is catalogued | `SEGDL4-R-007`, `R-008` |
| DL4 and DL5 "mutually exclusive" | Both Required in the Software Load Response | 11.7.4.2, 10.10 step 4 |

## Position Map (assuming an 18-character phone field, `SEGDL4-SME-003`)

| Positions | Field |
|---|---|
| 1 | `@` |
| 2-9 | New Software Version |
| 10-22 | Software Terminal Record ID |
| 23-40 | Software Load Phone Number |
| 41-46 | Request Date MMDDYY |
| 47-50 | Request Time HHMM |
| 51 | Software Load Type |
| 52 | `~` |

## Closure Gate

DL4 is not closeable while `SEGDL4-SME-001` to `SEGDL4-SME-003` are open.
