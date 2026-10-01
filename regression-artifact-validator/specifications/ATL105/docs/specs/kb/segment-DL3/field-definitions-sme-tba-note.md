# Segment DL3 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL3 — Date and Time Data Segment · **Sources:** 12.44, 11.7.3.2, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL3-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `:` identifies DL3 | R-001 |
| 2 | 25 | Day of the Week | N | 1 | 0 Sunday, 1 Monday, … 6 Saturday; originates at BUYPASS | R-005 |
| 3 | 21 | Current Date | N | 6 | MMDDYY: 01-12, 01-31, 00-99 | R-005 |
| 4 | 22 | Current Time | N | 4 | HHMM; hour "01-24", minute "01-60"; includes time-zone and DST adjustment | R-005 |
| 5 | 23 | Cut Time | N | 4 | HHMM; hour "01-24", minute "01-60" | R-005, R-009 |
| 6 | 65 | Password | N | var ≤ 6 | Right-aligned with spaces when shorter; matched against the merchant profile | R-003, R-006 |
| 7 | 34 | End-of-Data Indicator | A | 1 | `~` | R-001 |

## Findings From the Cross-Check

| Finding | Item |
|---|---|
| Password source: Device (12.44) vs Host (11.7.3.2); purpose "end-of-day function" vs "requesting host totals" | `SEGDL3-SME-002` |
| Date and Time Load Response (11.7.3.2) lists no `~` | `SEGDL3-SME-003` |
| HHMM fields list hour 01-24 / minute 01-60; Element 166 uses 0000-2359 | `SEGDL3-SME-004` |
| Password is typed N but padded with spaces | Treat leading spaces as valid, trailing spaces as invalid (`SEGDL3-R-006`) |

## Cross-Field Checks

- Day of the Week must agree with Current Date (e.g. `093026` is a Wednesday → `3`). The source does not state this as a rule; treat a mismatch as `REVIEW_REQUIRED`, not as a failure.
- Current Date must be a real calendar date (`023026` is invalid).

## Positive, Negative and Boundary Values (synthetic)

| Field | Valid | Invalid | Boundary |
|---|---|---|---|
| Day of the Week | `0`, `6` | `7`, `A` | `0`, `6` |
| Current Date | `093026`, `022828` | `133026`, `023026`, `2026-09` | `010100`, `123199` |
| Current Time | `1405` | `14:05`, `1475`, `2505` | `0000`, `2359`, `2400`, `1460` (`SEGDL3-SME-004`) |
| Password | `  4821`, `482193` | `4821  `, `48A193` | 1 digit right-aligned |
