# Segment DL6 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL6 — Store and Forward Data Segment · **Sources:** 12.47, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL6-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `\` identifies DL6 | R-002 |
| 2 | 166 | Start Time | N | 4 | "Start Time or End Time", HHMM, 0000-2359 | R-003, R-005 |
| 3 | 166 | End Time | N | 4 | Same element | R-003, R-005 |
| 4 | 34 | End-of-Data Indicator | A | 1 | `~` | R-002 |

## Element 166 Evidence (`SEGDL6-SME-001`)

- **For intentional reuse:** the element's own name is "Start Time or End Time", so two fields using it is by design.
- **Against completeness:** its purpose ("the start time or end time the device uses custom receipt text or host discount data") and processing rule ("Appears in Proprietary Data Load Segment (Segment No. 118)") do not mention DL6.

The SME item stays open, but test data can already use HHMM 0000-2359 for both fields.

## Positive, Negative and Boundary Values (synthetic)

| Field | Valid | Invalid | Boundary |
|---|---|---|---|
| Start Time | `0100` | `2400`, `0160`, `1:00` | `0000`, `2359` |
| End Time | `0500` | `2500`, `05:0` | `0000`, `2359` |
| Start vs End | `0100`/`0500` | — | `2300`/`0500` (crosses midnight), `0100`/`0100` (`SEGDL6-SME-005`) |

DL3 Elements 22/23 list hours 01-24; Element 166 uses 0000-2359. Do not copy DL3's time boundaries into DL6 (Lesson L6).
