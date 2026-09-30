# Segment DL8 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.49, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL8 versus Segment 100 and DL1-DL6

| Topic | Segment 100 | DL1-DL6 | DL8 |
|---|---|---|---|
| Identity | Segment Type | Data Type Indicator | `%` |
| Length | Segment Length | None | Segment Length, excludes `%` |
| Terminator | None | `~` | None |
| Inner structure | Field Separators | Fixed/marker fields | 1-24 fixed 26-byte groups |

## Serialization Rules

1. `%` first (`SEGDL8-R-002`).
2. Three-digit Segment Length (`SEGDL8-R-005`).
3. 1-24 groups of RID (10) + Stand-in (1) + Floor Limit (12, zero-filled) + Card Type (3) (`SEGDL8-R-003`, `R-004`).
4. Nothing after the last group.

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| Two-group example | Pass (Segment Length reading per `SEGDL7-SME-005`) |
| One group | Pass (boundary) |
| 24 groups (624 bytes) | Pass (boundary) |
| 25 groups | Fail `SEGDL8-R-003` |
| Floor Limit not zero-filled (`5000`) | Fail `SEGDL8-R-004` / `R-005` |
| Stand-in `4` | Fail `SEGDL8-R-004` |
| Trailing `~` | Fail `SEGDL8-R-002` |
