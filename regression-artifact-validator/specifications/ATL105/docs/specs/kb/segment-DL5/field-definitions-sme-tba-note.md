# Segment DL5 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL5 — Software IP Load Data Segment · **Sources:** 12.46, 13.2 · **Oracle:** [rule catalog](coverage/segment-DL5-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md)

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | `$` identifies DL5 | R-002 |
| 2 | 57 | New Software Version | AN | 8 | Fixed length of eight | R-005 |
| 3 | 95 | Software Terminal Record ID | AN | 13 | Fixed length of 13 | R-005 |
| 4 | 114 | Software Load IP/URL Address | AN | 30 | Fixed length of 30; purpose "terminal number used to access the device management system"; values `01-999`, `a-z`, `A-Z` | R-005 |
| 5 | 92 | Software Load Request Date | N | 6 | MMDDYY | R-005 |
| 6 | 93 | Software Load Request Time | N | 4 | HHMM | R-005 |
| 7 | 94 | Software Load Type | A | 1 | `F` / `P` | R-005 |
| 8 | 34 | End-of-Data Indicator | A | 1 | `~` | R-002 |

## Element 114 Problem (`SEGDL5-SME-003`)

The element is named "IP/URL Address" but its definition allows only digits and letters and describes a "terminal number". An IPv4 address needs `.`; a URL needs `:` and `/`. Until the SME answers:

- A 30-character value of letters and digits only is a safe positive case.
- Values containing `.`, `:`, `/` are `REVIEW_REQUIRED`, not negative.
- The padding character for shorter addresses is unknown.

## Positive, Negative and Boundary Values (synthetic)

| Field | Valid | Invalid | Boundary / review |
|---|---|---|---|
| IP/URL Address | `DMSHOST01` + 21 spaces (30) | 29 or 31 characters | `dms.example.test:443` padded (REVIEW) |
| New Software Version | `APP02.10` | 7 or 9 characters | exactly 8 |
| Software Load Type | `F`, `P` | `X` | — |
