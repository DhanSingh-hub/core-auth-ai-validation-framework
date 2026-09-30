# Segment DL6 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 12.47, 11.7.1.2 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Companion | Relationship | Disposition |
|---|---|---|
| DL1 | Trigger: Card Type `173` | DL6 iff `173` |
| DL2, DL3 | Precede DL6 in the Table Load Response | Optional; DL6 comes after the `*` closing block 3 |
| DL4, DL5, DL7, DL8 | Not in the Table Load Response layout | Not with DL6 |

Like Segment 100's EMV → 130 rule, DL6 is a companion required by a value in another segment. Unlike Segment 100, there is no Element 63 count; the proof is presence and position.

## Training Exercise

| Case | Content | Expected |
|---|---|---|
| A | DL1 (020, 173) … `*` DL6 `*` | Pass |
| B | DL1 (020, 173) … `*` | Fail `SEGDL6-R-001` |
| C | DL1 (020) … `*` DL6 `*` | Fail `SEGDL6-R-001` |
| D | DL1 (173) … DL6 before DL3 | Fail `SEGDL6-R-006` |
| E | DL1 (173) … `*` DL6 DL6 `*` | Fail (duplicate) |
