# Segment DL3 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 11.7.1.2, 11.7.3.2, Appendix E · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Context | Companions | Disposition |
|---|---|---|
| Date and Time Load Response | None | Any other segment is an error |
| Table Load Response | DL1 (Required, first), DL2 (Conditional, before DL3), End-of-Load `*` after DL3, DL6 after `*` when DL1 has `173` | DL3 in Data Block 3 |
| Phone / Software Load Response | — | DL3 not allowed |

DL3 has no segment trigger of its own. A **value** relationship exists with DL1: Card Type `164` (Auto Close) acts relative to the cut time that DL3 carries.

## Training Exercise

| Case | Content | Expected |
|---|---|---|
| A | Date and Time Load Response: DL3 fields | Pass |
| B | Table Load Response: `)` DL1 DL3 `*` | Pass (DL2 omitted) |
| C | Table Load Response: `)` DL1 `*` DL3 | Fail `SEGDL1-R-012` |
| D | Phone Load Response: DL2 DL3 | Fail `SEGDL3-R-007` |
