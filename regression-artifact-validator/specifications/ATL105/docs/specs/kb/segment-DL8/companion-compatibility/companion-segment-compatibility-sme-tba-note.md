# Segment DL8 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 12.49, 11.7.1.2 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Companion | Relationship | Disposition |
|---|---|---|
| DL1 (Table Load Data Block 1) | Same table load, position of DL8 unknown | `REVIEW_REQUIRED` (`SEGDL8-SME-002`) |
| DL6 | Also after DL3 in the table load | Relative order unknown |
| DL7 | Same hybrid framing | No stated relationship |
| Segment 130 (EMV request) | Uses the floor limits DL8 set | Not in the same message |

## Training Exercise

| Case | Content | Expected |
|---|---|---|
| A | Special terminal, table load with DL8 | Pass (position REVIEW) |
| B | Non-Special terminal, table load with DL8 | Fail `SEGDL8-R-001` |
| C | Special terminal, table load without DL8 after a floor-limit change | `REVIEW_REQUIRED` (lifecycle question in the lifecycle note) |
