# Segment DL5 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 11.7.4.2, 10.10 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Companion | Relationship | Disposition |
|---|---|---|
| DL4 Software Dial Load | Required, immediately before DL5 (fields 2 and 3 of the Software Load Response) | DL5 without DL4, or before DL4, is an error |
| DL1, DL2, DL3, DL6, DL7, DL8 | Other download responses | Not in a Software Load Response |

See the [DL4 compatibility note](../../segment-DL4/companion-compatibility/companion-segment-compatibility-sme-tba-note.md) for why the "mutually exclusive" claim was withdrawn.

## Training Exercise

| Case | Content | Expected |
|---|---|---|
| A | `)` DL4 DL5 | Pass |
| B | `)` DL5 | Fail `SEGDL5-R-006` |
| C | `)` DL5 DL4 | Fail `SEGDL5-R-006` |
| D | Table Load Response containing DL5 | `REVIEW_REQUIRED` (`SEGDL4-SME-002`) |
