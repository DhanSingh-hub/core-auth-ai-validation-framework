# Segment DL2 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 11.7.1.2, 11.7.2.2 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Context | Companions | Disposition |
|---|---|---|
| Phone Load Response | None — DL2 is the only segment | Any other segment is an error |
| Table Load Response | DL1 (Required, before DL2), DL3 (Conditional, after DL2), DL6 (if DL1 has `173`) | DL2 must sit in Data Block 2 |
| Software Load / Date and Time Load Response | — | DL2 not allowed |

DL2 has no cross-segment trigger of its own (no field of DL2 causes another segment to appear). Its position depends on DL1 in a Table Load Response.

## Training Exercise

| Case | Content | Expected |
|---|---|---|
| A | Phone Load Response: DL2 | Pass |
| B | Phone Load Response: DL2 + DL3 | Fail `SEGDL2-R-008` |
| C | Table Load Response: `)` DL1 DL2 DL3 `*` | Pass |
| D | Table Load Response: `)` DL2 DL1 `*` | Fail `SEGDL1-R-012` |
| E | Table Load Response: `)` DL1 `*` (no DL2) | Pass (DL2 is Conditional) |
