# Segment DL6 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.47, 11.7.1.2, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL6 versus Segment 100 Serialization

| Topic | Segment 100 | DL6 |
|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator `\` |
| Length field | Segment Length | None; 9 stated, 10 by field sum |
| Field delimiter | Field Separator | None |
| Terminator | None | `~` |
| Container | Data Section 3 | Table Load Response Data Block 4 |

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| `\01000500~` | Pass (length per `SEGDL6-SME-003`) |
| `\00002359~` | Pass (boundary) |
| `\24000500~` | Fail `SEGDL6-R-005` |
| `\0100~` (End Time missing) | Fail `SEGDL6-R-002` |
| FS between the times | Fail `SEGDL6-R-004` |
| `\23000500~` | `REVIEW_REQUIRED` (`SEGDL6-SME-005`) |

Note for JSON test data: `\` must be escaped as `"\\"` in JSON strings.
