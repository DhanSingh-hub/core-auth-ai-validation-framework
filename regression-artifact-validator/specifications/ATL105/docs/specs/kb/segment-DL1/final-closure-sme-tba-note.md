# Segment DL1 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL1 — Merchant Data Segment · **Sources:** 12.42, 11.7.1.2, 13.2 (Element 84) · **Oracle:** [rule catalog](coverage/segment-DL1-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Why Closure Is Separate

Field rules prove each value is valid. Closure proves the whole segment parses without positional drift and sits correctly in the Table Load Response.

## Corrections to the Previous Closure Note

The earlier generated note applied numbered-segment rules that do **not** apply to DL1:

| Earlier statement | Correct DL1 behaviour | Evidence |
|---|---|---|
| "Segment DL1 uses a 3-digit Segment Length (Element 84)" | DL1 has **no** Segment Length; it is framed by `#` and `~` | 12.42 layout; Element 84 is not in the DL1 field list |
| "An empty middle field keeps its separator" | DL1 has no separators; "when a field is not populated, the next field immediately follows" | 12.42 note |
| "Repeating content uses different separator rules" | The Card Type repetition is bounded only by Number of Card Types | 12.42 field 7/8 |

## Serialization Rules

| Rule | Check |
|---|---|
| `SEGDL1-R-001` | Length ≤ 399 = 102 + 3 × N with N ≤ 99 (if fields are full width) |
| `SEGDL1-R-002` | First character `#`, last character `~` |
| `SEGDL1-R-006` | No Field Separator anywhere in the segment |
| `SEGDL1-R-004` | Exactly N Card Types before `~` |
| `SEGDL1-R-012` | Data Block 1 after `)`; End-of-Load `*` after the last block |

## Closure Gate

DL1 is not closeable while these remain open:

- `SEGDL1-SME-001` — no AI/Test package or approved synthetic fixtures.
- `SEGDL1-SME-002` — padding of "up to N" fields (blocks the parser and length mutations).
- `SEGDL1-SME-003` — End-of-Load count with DL6.
- `SEGDL1-SME-004` — complete Appendix E Table Load Card Type set.

## Certification Meaning

- Serialization closure proves DL1 parses without desynchronization.
- Lifecycle closure proves DL1 belongs to a Table Load Request/Response pair and that DL6 appears exactly when `173` is present.
- Device behaviour after the load (card acceptance, feature activation) is out of scope for message validation.

## SME/TBA Review Questions

- Is there any Field Separator or Segment Length in the serialized DL1? (There must not be.)
- Does the parse of fields 2-6 land exactly on Number of Card Types?
- Does the `~` come immediately after the N-th Card Type?
- Are all provisional items resolved or kept at `REVIEW_REQUIRED`?
