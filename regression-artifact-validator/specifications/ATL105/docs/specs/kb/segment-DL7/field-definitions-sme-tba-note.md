# Segment DL7 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment · **Sources:** 12.48, 13.2, Appendix W · **Oracle:** [rule catalog](coverage/segment-DL7-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `^` identifies DL7 | R-001 |
| 2 | 84 | Segment Length | N | 3 | Three digits (DL7 is not a 4-digit segment); excludes `^` | R-002, R-006 |
| 3 | 232 | Download Data | AN | ≤ 100 | "All or some of the Download Data"; any alphanumeric | R-003, R-005 |

## Appendix W Entries

| Part | Attributes | Table 001 | Table 002 |
|---|---|---|---|
| Table ID | n3 | `001` | `002` |
| Table Length | n3 | `003` | `013` |
| Table Data | an1 (sic) | ISO 639-2 language code (e.g. `eng`, `fra`, `spa`) | 13-character international postal code |

## Positive, Negative and Boundary Values (synthetic)

| Item | Valid | Invalid | Boundary / review |
|---|---|---|---|
| Site Language entry | `001003eng` | `001002en`, `001003en ` | Non-ISO 639-2 code `xxx` (REVIEW) |
| Postal Code entry | `002013A1B 2C3      ` | `002013A1B2C3` (12 data chars) | Postal code shorter than 13 without padding (REVIEW) |
| Segment Length | `028` | `28`, `0028` | `100`+3 per `SEGDL7-SME-005` |
| Download Data | 28 bytes | 101 bytes | 100 bytes |

Use synthetic postal codes only; do not use a real site's postcode.
