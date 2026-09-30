# Segment DL2 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL2 — Dial String Data Segment · **Sources:** 12.43, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL2-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Core Idea

DL2 mixes fixed one-character markers (`!`, `1`, `B`, `A`, `F`, `~`) with two variable-length values (Access Code, Phone Number). Its correctness depends as much on the markers as on the numbers.

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `!` identifies DL2 | R-001 |
| 2 | 28 | Dial String Type | N | 1 | `1` = transaction dial strings (fixed) | R-002, R-007 |
| 3, 8 | 82 | Redial Count | N | 1 | 1-3 | R-006 |
| 4, 9 | 1 | Access Code | AN | var ≤ 12 | Any number; `B` (each `B` = 1-second pause) | R-005 |
| 5, 10 | 66 | Pause Indicator | AN | 1 | `B` | R-005 |
| 6, 11 | 75 | Phone Number | N | var ≤ 18 | Digits; "all data up to the following C (Log-on Indicator) or A (Delimiter)" | R-007 |
| 7 | 27 | Dial String Terminator | AN | 1 | `A` = end of first dial string | R-002, R-007 |
| 12 | 27 | Dial String Terminator | AN | 1 | `F` = end of second dial string | R-003, R-007 |
| 13 | 34 | End-of-Data Indicator | A | 1 | `~` | R-001 |

## Findings From the Cross-Check

- Element 75 mentions a `C` (Log-on Indicator) terminator that never appears in the DL2 layout, and does not mention `F` → `SEGDL2-SME-004`.
- Element 1 allows `B` inside the Access Code, which makes the Access Code / Pause Indicator boundary depend on the last `B` → `SEGDL2-SME-004`.
- Element 82 restricts Redial Count to 1-3 even though the field is one digit wide → test `0` and `4` as negatives.

## Positive, Negative and Boundary Values (synthetic)

| Field | Valid | Invalid | Boundary |
|---|---|---|---|
| Redial Count | `1`, `3` | `0`, `4`, `A` | `1`, `3` |
| Access Code | `9`, `9BB` | `9#`, 13 characters | 12 characters |
| Pause Indicator | `B` | `b`, `,` | — |
| Phone Number | `5555550100` | `555-555-0100`, 19 digits | 1 digit, 18 digits |
| Terminators | `A` then `F` | `F` then `A`; `A` twice | — |

## Security and Test-Data Guidance

Use `555` numbers and synthetic access codes only; never copy a production dial profile.
