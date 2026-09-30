# Segment DL1 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL1 — Merchant Data Segment · **Sources:** 12.42, 13.2, Appendix D, Appendix E · **Oracle:** [rule catalog](coverage/segment-DL1-rule-catalog.json) · **Benchmark:** Segment 100 [account-number note](../segment-100/account-number-sme-tba-note.md) (the core data carrier)

## Core Idea

Every DL1 field is Required and Host-sourced, so the test question is never "is it present?" alone but "does it match its Chapter 13 element definition?". Because DL1 has no separators, a wrong width in one field shifts every later field.

## Element Definitions (Chapter 13)

| Field | Element | Name | Type | Length | Representation / valid values | Rule |
|---|---|---|---|---|---|---|
| 1 | 24 | Data Type Indicator | AN | 1 | Fixed `#` | R-002 |
| 2 | 53 | Merchant Name | AN | 24 | "Fixed length of up to 24" | R-003 |
| 3 | 98 | Store Number | N | 16 | "Fixed length of up to 16 digits"; 0000000000000001-9999999999999999; all-asterisk mask is neither displayed nor printed | R-003, R-011 |
| 4 | 3 | Address Line 1 | AN | 24 | "Fixed length of up to 24" | R-003 |
| 5 | 4 | Address Line 2 | AN | 21 | Pos 1-12 city, 13 space, 14-15 State Code (Appendix D), 16 space, 17-21 ZIP | R-010 |
| 6 | 54 | Merchant Phone Number | AN | 13 | `(nnn)nnn-nnnn` | R-011 |
| 7 | 59 | Number of Card Types | N | 2 | 01-99 | R-004 |
| 8 | 14 | Card Type | AN | 3 | Appendix E "Valid Card Type Codes Used in the Table Load Response" | R-009 |
| 9 | 34 | End-of-Data Indicator | A | 1 | Fixed `~` | R-002 |

## Findings From the Cross-Check

- "Fixed length of up to N" (Elements 3, 53, 98) is ambiguous in a separator-free segment → `SEGDL1-SME-002`.
- Appendix E's introduction ranges (001-086, 127-168) do not cover codes the table lists (088-096, 150, 171, 173, 174) → `SEGDL1-SME-004`.
- Element 4's ZIP positions 17-21 hold 5 characters, so ZIP+4 cannot fit although the purpose mentions ZIP+4. Treat 5-digit ZIP as the testable form.

## Positive, Negative and Boundary Values (synthetic)

| Field | Valid | Invalid | Boundary |
|---|---|---|---|
| Store Number | `0000000000001234` | `000000000000000A`, `0000000000000000` | `0000000000000001`, `9999999999999999` |
| Address Line 2 | `SPRINGFIELD  IL 62701` | `SPRINGFIELD,IL 62701` | 12-character city filling positions 1-12 |
| Phone | `(555)555-0100` | `555-555-0100 ` | — |
| Number of Card Types | `01`, `03`, `99` | `00`, `1`, `A1` | `01`, `99` |
| Card Type | `020`, `164`, `173` | `999`, `20` | Feature code vs card code |

## Security and Test-Data Guidance

Use synthetic merchant names, addresses and `555` phone numbers only. Never copy a production merchant profile.

## Review Checklist

- Does each field fixture cite its element number and rule ID?
- Is there a negative case for each element format, not only for presence?
- Are short-value fixtures kept `REVIEW_REQUIRED` until `SEGDL1-SME-002` is answered?
