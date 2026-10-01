# Segment DL8 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment · **Sources:** 12.49, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL8-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `%` identifies DL8 | R-002 |
| 2 | 84 | Segment Length | N | 3 | Three digits; excludes `%` | R-002, R-005 |
| 3 | 233 | RID | AN | 10 | Fixed 10; "a valid EMV Registered Application Provider identifier" | R-004 |
| 4 | 234 | Stand-in Indicator | N | 1 | 1 No stand-in, 2 Domestic only, 3 Domestic & Foreign | R-004 |
| 5 | 235 | Floor Limit | N | 12 | Fixed 12; 000000000000-999999999999; maximum allowable stand-in value | R-004 |
| 6 | 236 | BUYPASS RID Card Type | AN | 3 | Fixed 3; appears only in DL8; no values listed | R-004 |

## Findings From the Cross-Check

| Finding | Item |
|---|---|
| An EMV RID is 5 bytes; 10 AN suggests hexadecimal text | `SEGDL8-SME-003` |
| Element 236 has no valid-value list | `SEGDL8-SME-003` |
| Floor Limit decimal places are not stated (other amount elements use two assumed decimals) | Keep amount semantics `REVIEW_REQUIRED` |
| Stand-in `1` with a non-zero Floor Limit | `SEGDL8-SME-003` |

## Positive, Negative and Boundary Values

| Field | Valid | Invalid | Boundary / review |
|---|---|---|---|
| RID | `A000000003`, `A000000004` (public RIDs) | 9 or 11 characters | Lower-case hex (REVIEW) |
| Stand-in Indicator | `1`, `2`, `3` | `0`, `4`, `A` | — |
| Floor Limit | `000000005000` | `5000`, `00000000500A` | `000000000000`, `999999999999` |
| BUYPASS RID Card Type | `020` | 2 or 4 characters | Codes outside Appendix E (REVIEW) |

RIDs are public registered identifiers, not cardholder data; floor-limit amounts must still be synthetic.
