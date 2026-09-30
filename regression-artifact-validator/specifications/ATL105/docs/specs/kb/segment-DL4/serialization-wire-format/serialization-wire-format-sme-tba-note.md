# Segment DL4 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.45, 11.7.4.2, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL4 versus Segment 100 Serialization

| Topic | Segment 100 | DL4 |
|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator `@` |
| Length field | Segment Length | None; max 52 |
| Field delimiter | Field Separator | None |
| Variable field | Delimited by FS | Software Load Phone Number — boundary open |
| Terminator | None | `~` |
| Container | Data Section 3 | Software Load Response Data Block 1, before DL5 |

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| 52-character example | Pass |
| New Software Version 7 characters | Fail `SEGDL4-R-005` |
| Phone Number 10 characters with no padding (length 44) | `REVIEW_REQUIRED` (`SEGDL4-SME-003`) |
| Software Load Type `X` | Fail `SEGDL4-R-005` |
| Field Separator inserted | Fail `SEGDL4-R-004` |
| `~` missing | Fail `SEGDL4-R-002` |
