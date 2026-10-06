# Segment DL5 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.46, 11.7.4.2, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL5 versus Segment 100 Serialization

| Topic | Segment 100 | DL5 |
|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator `$` |
| Length field | Segment Length | None; 64 (66 per 11.7.4.2) |
| Field delimiter | Field Separator | None; all fields fixed width |
| Terminator | None | `~` |
| Container | Data Section 3 | Software Load Response field 3, after DL4 |

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| 64-character example | Pass |
| IP/URL 29 characters (length 63) | Fail `SEGDL5-R-005` |
| 65 or 66 characters | `REVIEW_REQUIRED` (`SEGDL5-SME-002`) |
| IP/URL with `.` or `:` | `REVIEW_REQUIRED` (`SEGDL5-SME-003`) |
| Field Separator inserted | Fail `SEGDL5-R-004` |
| `@` instead of `$` | Fail `SEGDL5-R-002` |
