# Segment DL4 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL4 — Software Dial Load Data Segment · **Sources:** 12.45, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL4-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `@` identifies DL4 | R-002 |
| 2 | 57 | New Software Version | AN | 8 | Fixed length of eight | R-005 |
| 3 | 95 | Software Terminal Record ID | AN | 13 | Fixed length of 13 | R-005 |
| 4 | 91 | Software Load Phone Number | AN | ≤ 18 | Variable length up to 18; "used in DL4" only | R-005 |
| 5 | 92 | Software Load Request Date | N | 6 | MMDDYY | R-005 |
| 6 | 93 | Software Load Request Time | N | 4 | HHMM | R-005 |
| 7 | 94 | Software Load Type | A | 1 | `F` full application load, `P` partial application load | R-005 |
| 8 | 34 | End-of-Data Indicator | A | 1 | `~` | R-002 |

Elements 57, 92, 93, 94 and 95 are shared with DL5 and have the same rules there.

## Findings From the Cross-Check

- Element 91 is variable length in a separator-free segment followed by a numeric date → `SEGDL4-SME-003`.
- Element 94 is typed `A` but its representation says "one alphanumeric character"; valid values `F`/`P` are alphabetic, so no conflict in practice.
- Element 91 is AN (not N like Element 75), so a phone number with dial characters is possible; keep non-digit values `REVIEW_REQUIRED`.

## Positive, Negative and Boundary Values (synthetic)

| Field | Valid | Invalid | Boundary |
|---|---|---|---|
| New Software Version | `APP02.10` | `APP2.1` (6), 9 characters | exactly 8 |
| Software Terminal Record ID | `STR0000000123` | 12 or 14 characters | exactly 13 |
| Software Load Phone Number | `5555550142` (+ padding per SME-003) | 19 characters | 18 characters |
| Request Date | `101526` | `131526`, `2026-10-15` | `123199` |
| Request Time | `0200` | `2:00`, `2560` | `0000`, `2359` |
| Software Load Type | `F`, `P` | `X`, `f` | — |
