# Segment DL2 Serialization and Wire-Format Behavior: SME and TBA Learning Note

**Sources:** 12.43, 13.2 · **Benchmark:** Segment 100 [serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## DL2 versus Segment 100 Serialization

| Topic | Segment 100 | DL2 |
|---|---|---|
| Identity | Segment Type `100` | Data Type Indicator `!` |
| Length field | Segment Length | None; max 69 |
| Field delimiter | Field Separator | None; in-band markers `B`, `A`, `F` |
| Empty conditional field | Keep separator | Omit Access Code and Pause Indicator together |
| Terminator | None | `~` |

## Serialization Rules

1. `!` then Dial String Type `1` (`SEGDL2-R-001`, `R-002`).
2. Primary block: Redial Count, optional Access Code + `B`, Phone Number, `A`.
3. Secondary block: same, ending `F` (`SEGDL2-R-003`).
4. `~` last; no Field Separators (`SEGDL2-R-004`); total ≤ 69.
5. Variable values are not padded — the markers define their end (confirm with `SEGDL2-SME-004`).

## Positive, Negative and Boundary Cases

| Case | Expected |
|---|---|
| `!139B5555550100A25555550199F~` | Pass |
| No access codes: `!135555550100A25555550199F~` | Pass |
| 18-digit phone numbers and 12-character access codes (69 chars) | Pass (boundary) |
| 19-digit phone number | Fail `SEGDL2-R-007` |
| Access Code without `B` | Fail `SEGDL2-R-005` |
| `A` and `F` swapped | Fail `SEGDL2-R-007` |
| Field Separator inserted | Fail `SEGDL2-R-004` |
| Converter pads Phone Number to 18 with spaces | `REVIEW_REQUIRED` (`SEGDL2-SME-004`) |

## TBA Artifact Decomposition

```text
BR:  DL2 shall serialize the primary block ending 'A' before the secondary block ending 'F'.
TS:  Phone Load for a merchant with two numbers.
TC:  Serialize, then parse back; compare with the structured input.
TD:  primary {3, "9", "B", "5555550100"}, secondary {2, -, -, "5555550199"}.
Expected: PASS when the round trip is identical.
```
