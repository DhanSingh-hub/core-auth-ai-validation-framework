# Segment DL4 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 11.7.4.2, 10.10, 12.45, 12.46 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Companion | Relationship | Disposition |
|---|---|---|
| DL5 Software IP Load | Required alongside DL4 in the Software Load Response (field 3 after DL4 field 2) | DL4 without DL5 is an error |
| DL1, DL2, DL3, DL6 | Table Load / Phone Load / Date and Time Load content | Not in a Software Load Response |
| DL7, DL8 | Table-load related | Not in a Software Load Response |

## Why the "Mutually Exclusive" Claim Was Wrong

| Evidence | Says |
|---|---|
| 11.7.4.2 | Field 2 DL4 `R`, field 3 DL5 `R` |
| 10.10 step 4 | BUYPASS sends DL4 **and** DL5 |
| 10.10 step 5c | Device stores "Software Load Phone Number **or** Software Load IP/URL Address" |

The "or" is a device decision after both segments arrive. The choice rule is open (`SEGDL4-SME-002`).

## Training Exercise

| Case | Content | Expected |
|---|---|---|
| A | Software Load Response: `)` DL4 DL5 | Pass |
| B | Software Load Response: `)` DL4 | Fail `SEGDL4-R-006` |
| C | Software Load Response: `)` DL5 DL4 | Fail (order) |
| D | Software Load Response: `)` DL4 DL5 DL3 | Fail |
| E | Table Load Response containing DL4 | `REVIEW_REQUIRED` (`SEGDL4-SME-002`) |
