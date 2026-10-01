# Segment DL3 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.44, 11.7.3.2, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL3 versus Segment 100 Serialization

| Topic | Segment 100 | DL3 |
|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator `:` |
| Length field | Segment Length | None; exactly 23 |
| Field delimiter | Field Separator | None; fixed widths |
| Padding | Per element | Password right-aligned with leading spaces |
| Terminator | None | `~` |

## Serialization Rules

1. `:` first (`SEGDL3-R-001`).
2. Day (1), Date (6), Time (4), Cut Time (4), Password (6) in order, no separators (`SEGDL3-R-004`).
3. Password shorter than six digits is right-aligned with spaces (`SEGDL3-R-006`).
4. `~` last in the Table Load Response; Date and Time Load Response per `SEGDL3-SME-003`.

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| `:309302614052300  4821~` | Pass |
| Password left-aligned `4821  ` | Fail `SEGDL3-R-006` |
| Password zero-filled `004821` | `REVIEW_REQUIRED` (Element 65 says spaces) |
| Date `2026-09-30` style | Fail `SEGDL3-R-005` |
| Field Separator between Date and Time | Fail `SEGDL3-R-004` |
| 22 characters in a Table Load Response | Fail `SEGDL3-R-001` |
| 22 characters in a Date and Time Load Response | `REVIEW_REQUIRED` (`SEGDL3-SME-003`) |
