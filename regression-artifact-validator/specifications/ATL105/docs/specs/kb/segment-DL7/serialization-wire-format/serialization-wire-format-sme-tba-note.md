# Segment DL7 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.48, 13.2, Appendix W · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL7 versus Segment 100 and DL1-DL6

| Topic | Segment 100 | DL1-DL6 | DL7 |
|---|---|---|---|
| Identity | Segment Type | Data Type Indicator | `^` |
| Length | Segment Length (includes Segment Type) | None | Segment Length, excludes `^` |
| Terminator | None | `~` | None |
| Inner structure | Field Separators | Fixed/marker fields | TLV entries |

## Serialization Rules

1. `^` first (`SEGDL7-R-001`).
2. Three-digit Segment Length (`SEGDL7-R-006`), excluding `^` (`SEGDL7-R-002`).
3. Download Data as Table ID + Table Length + Table Data entries (`SEGDL7-R-003`, `R-004`), ≤ 100 bytes (`SEGDL7-R-005`).
4. Nothing after the last entry — no `~`.

Trailing spaces in the postal code are data, not padding to strip; the Segment Length must include them.

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| `^028001003eng002013A1B 2C3      ` | Pass (reading A) / REVIEW |
| Trailing `~` added | Fail `SEGDL7-R-001` |
| Segment Length `029` for 28 bytes | Fail `SEGDL7-R-002` (counts `^`) |
| Postal code trailing spaces stripped (entry 6 bytes short) | Fail `SEGDL7-R-004` |
| Download Data 101 bytes | Fail `SEGDL7-R-005` |
