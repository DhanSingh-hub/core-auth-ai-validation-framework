# Segment DL1 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.42, 11.7.1.2, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Purpose

A DL1 JSON object can be valid while the serialized segment is not. DL1 serialization has none of Segment 100's separator machinery, so the risks are different: positional drift, wrong widths, and a wrong Card Type count.

## DL1 versus Segment 100 Serialization

| Topic | Segment 100 | DL1 |
|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator `#` |
| Length field | Segment Length (Element 84) | None; max 399 |
| Field delimiter | Field Separator | None |
| Empty middle field | Keep its separator | Not applicable — every field is Required; an unpopulated field is skipped, which shifts later fields |
| Trailing optional suffix | May be omitted | Not applicable |
| Terminator | None | End-of-Data `~` |
| Container | Data Section 3, counted by Element 63 | Table Load Response Data Block 1, after `)` |

## Serialization Rules

1. Emit `#` first and `~` last (`SEGDL1-R-002`).
2. Emit fields 2-8 in layout order with no Field Separators (`SEGDL1-R-006`).
3. Number of Card Types is two digits (`01`-`99`) and equals the number of 3-character Card Types that follow (`SEGDL1-R-004`).
4. Total length ≤ 399 (`SEGDL1-R-001`).
5. In the Table Load Response, DL1 follows `)`; for TCP/IP no further `)` is sent before later blocks (`SEGDL1-R-012`).
6. Padding for "up to N" fields is open (`SEGDL1-SME-002`); the converter must not invent one silently.

## Positive, Negative and Boundary Cases

| Case | Expected result |
|---|---|
| 111-character DL1 with 3 Card Types | Pass |
| 399-character DL1 with 99 Card Types | Pass (boundary) |
| 100 Card Types | Fail `SEGDL1-R-001` / `R-004` |
| Field Separator inserted between fields | Fail `SEGDL1-R-006` |
| `~` missing | Fail `SEGDL1-R-002` |
| Number of Card Types `03`, two codes | Fail `SEGDL1-R-004` |
| Merchant Name 20 characters, unpadded | `REVIEW_REQUIRED` (`SEGDL1-SME-002`) |
| Segment Length inserted after `#` | Fail `SEGDL1-R-002` |

## TBA Artifact Decomposition

```text
BR:  DL1 shall be serialized without Field Separators, starting with '#' and ending with '~'.
TS:  Table Load Response for a merchant with three Card Types.
TC:  Serialize and inspect: '#' at position 1, Number of Card Types at 100-101, '~' at 111.
TD:  Structured DL1 values from the synthetic example in the serialization flow.
Expected: PASS; FAIL if any FS byte appears or '~' is not at position 102 + 3N.
```
