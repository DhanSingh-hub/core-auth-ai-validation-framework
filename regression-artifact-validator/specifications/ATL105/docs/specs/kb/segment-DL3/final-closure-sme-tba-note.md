# Segment DL3 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL3 — Date and Time Data Segment · **Sources:** 12.44, 11.7.3.2 · **Oracle:** [rule catalog](coverage/segment-DL3-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL3 behaviour | Evidence |
|---|---|---|
| "Segment DL3 uses a 3-digit Segment Length (Element 84)" | No Segment Length; framed by `:` and `~` | 12.44 layout |
| "An empty middle field keeps its separator" | No separators; all fields fixed width and Required | 12.44 note |
| "The catalog contains no serialization rules" | `SEGDL3-R-004` now catalogues the no-separator rule | 12.44 note |
| "No lifecycle rules catalogued" | Date and Time Load lifecycle and automatic cut time are catalogued | `SEGDL3-R-008`, `R-009` |
| Password "Device-sourced" presented as settled | Conflicting sources | `SEGDL3-SME-002` |

## Position Map (Table Load Response form)

| Positions | Field |
|---|---|
| 1 | `:` |
| 2 | Day of the Week |
| 3-8 | Current Date MMDDYY |
| 9-12 | Current Time HHMM |
| 13-16 | Cut Time HHMM |
| 17-22 | Password |
| 23 | `~` |

These positions match the Pos. column of 11.7.3.2 for fields 1-6.

## Closure Gate

DL3 is not closeable while `SEGDL3-SME-001` to `SEGDL3-SME-004` are open.
