# Segment DL1 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 11.7.1.2, 12.42, 12.47, 12.49 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Core Mental Model

```text
Merchant profile (TABL, Card Types, Special flags)
  -> Table Load Response block set
  -> DL1 (always) + DL2? + DL3? + DL6 (iff Card Type 173)
  -> End-of-Load '*'
```

## Compatibility Baseline

| Companion | Relationship to DL1 | Disposition | Rule |
|---|---|---|---|
| DL2 Dial String | Conditional in Table Load Response (Data Block 2) | Present or absent; if present follows DL1 | `SEGDL1-R-012` |
| DL3 Date and Time | Conditional in Table Load Response (Data Block 3) | Present or absent; if present follows DL2/DL1 | `SEGDL1-R-012` |
| DL6 Store and Forward | Required when DL1 has Card Type `173`; prohibited otherwise | Missing or unexpected DL6 is an error | `SEGDL1-R-005` |
| DL4 / DL5 Software Load | Software Load Response only | Presence with DL1 is an error | `SEGDL1-R-007` |
| DL7 Supplemental Terminal | Placement not defined in 11.7.1.2 | `REVIEW_REQUIRED` | — |
| DL8 EMV Floor Limits | "Inclusion in a table load" per 12.49, but no position in 11.7.1.2 | `REVIEW_REQUIRED` | — |
| Segment 100 and numbered segments | Not part of any download response | Presence is an error | `SEGDL1-R-007` |

## Training Exercise

| Case | Context | Expected |
|---|---|---|
| A | DL1 Card Types 020, 011 | `)` DL1 `*` (DL2/DL3 optional); no DL6 |
| B | DL1 Card Types 020, 173 | DL1 … `*` DL6 `*`; count of `*` per `SEGDL1-SME-003` |
| C | DL1 with 173, DL6 missing | Fail `SEGDL1-R-005` |
| D | DL1 without 173, DL6 present | Fail `SEGDL1-R-005` |
| E | DL1 + DL4 in one response | Fail `SEGDL1-R-007` |
| F | DL1 + DL8 | `REVIEW_REQUIRED` |

## Common Analysis Errors

- Treating DL2/DL3 as required because they are in the layout.
- Counting segments with Element 63 — download responses have no Data Section 1.
- Accepting DL6 because it is well formed, without checking DL1 for `173`.
