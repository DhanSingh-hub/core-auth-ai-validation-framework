# Segment DL2 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL2 — Dial String Data Segment · **Sources:** 12.43, 11.7.2.2, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL2-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL2 behaviour | Evidence |
|---|---|---|
| "Segment DL2 uses a 3-digit Segment Length (Element 84)" | DL2 has no Segment Length; it is framed by `!` and `~` | 12.43 layout |
| "An empty middle field keeps its separator" | No separators; an absent Access Code/Pause Indicator is simply not emitted | 12.43 note; Element 1 |
| "No conditional rules catalogued" | Access Code / Pause Indicator pairing is conditional | Element 1 → `SEGDL2-R-005` |

## Round-Trip Test

Because DL2 has variable-length values without separators, closure requires a **round trip**: serialize the structured values, parse the string back, and compare. Any difference is a closure failure. Round-trip cases:

| Case | Serialized primary | Parses back? |
|---|---|---|
| No access code | `35555550100A` | Yes |
| Access code `9` | `39B5555550100A` | Yes, if the last `B` ends the Access Code |
| Access code with pauses `9BB` | `39BBB5555550100A` | Yes under the "last `B`" rule; confirm (`SEGDL2-SME-004`) |
| Access code without `B` | `395555550100A` | No — `9` merges into the phone number → Fail `SEGDL2-R-005` |

## Closure Gate

DL2 is not closeable while `SEGDL2-SME-001` to `SEGDL2-SME-004` are open. Until then, `R-003`, `R-004`, `R-005`, `R-007` and `R-008` stay `REVIEW_REQUIRED`.

## SME/TBA Review Questions

- Does every serialized DL2 round-trip to the same structured values?
- Is `A` always before `F` and `~` last?
- Is the message/load-flag context recorded with the fixture?
