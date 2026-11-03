# Segment DL7 Supplemental Terminal Data Segment: SME and TBA Learning Note

Verified against Section 12.48, Appendix W, and Section 13.2 (Elements 24, 84, 232).

## What Segment DL7 Means

DL7 is a small, extensible container: the host sends supplemental terminal settings as tagged entries. Appendix W currently defines two tags — the terminal's site language (ISO 639-2) and its international postal code.

```text
'^' + Segment Length (3) + [ TableID(3) TableLength(3) TableData ]...
```

Compare with Segment 100 and DL1-DL6:

| Question | Segment 100 | DL1-DL6 | DL7 |
|---|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator | Data Type Indicator `^` |
| Length | Segment Length | None | Segment Length (excludes `^`) |
| Terminator | None | `~` | None |
| Field delimiting | Field Separators | None | TLV entries |

## Appendix W (transcribed)

| Field | Attributes | Site Language | Postal Code |
|---|---|---|---|
| Table ID | n3 | `001` | `002` |
| Table Length | n3 | `003` | `013` |
| Table Data | an1 (sic) | ISO 639-2 language code | 13-character international postal code |

Appendix W is in scope for this training pass. The two tables now have a bounded oracle in the
[Appendix W training module](../appendix-w/README.md). The `an1` versus Table Length / described data
width contradiction remains an open source question (`SEGDL7-SME-003`).

## Open Questions

| Topic | Item |
|---|---|
| Is Appendix W in scope? | Resolved for this training pass (`SEGDL7-SME-001`) |
| `an1` vs Table Length 003/013 | `SEGDL7-SME-003` |
| Which message carries DL7; can data span several DL7s? | `SEGDL7-SME-004` |
| Does Segment Length include its own digits; why "Source: Device"? | `SEGDL7-SME-005` |

## TBA Decomposition Example

```text
BR:  Each DL7 Download Data entry shall be Table ID + Table Length + Table Data of exactly Table Length characters (SEGDL7-R-004).
TS:  Terminal with site language 'eng' and postal code 'A1B 2C3'.
TC+: Download Data '001003eng002013A1B 2C3      '. Expected PASS.
TC-: '001004eng' (Table Length 4, 3 data characters). Expected FAIL citing SEGDL7-R-004.
TD:  Structured DL7 JSON with entries [{tableId "001", data "eng"}, {tableId "002", data "A1B 2C3"}].
```

## Source References

Section 12.48: lines 17587-17634 · Appendix W: lines 35814-35830 · [Rule Catalog](coverage/segment-DL7-rule-catalog.json).
