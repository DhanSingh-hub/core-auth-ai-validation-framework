# Segment 119 (Totals with Proprietary Data Load Data Segment) — SME/TBA Learning Note

## Purpose

Segment 119 requests totals while also providing the device's current Card Table Version and Host Discount data. ATL105 Section 12.17 states that it always appears in Data Section 3 Field No. 3 and has a maximum length of 493 alphanumeric characters.

## Message Boundary

| Message part | Segment 119 behavior | Source |
|---|---|---|
| Data Section 1 | Message Format Version Identifier and Number of Segments | Section 11.4.1.2 |
| Data Section 2 | Not present; no Segment 100 | Section 11.4.1.2 |
| Data Section 3 | Segment 119 in Field No. 3 | Section 11.4.1.2 / 12.17 |
| Purpose | Totals plus Card Table Version and Host Discount data | Section 12.17 |
| Origin | Device, except Host Discount Timestamp is host-sourced data | Section 12.17 |

## Field Inventory

| # | Element | Name | Len. | Status |
|---:|---:|---|---:|---|
| 1 | 85 | Segment Type | 3 | Required, fixed `119` |
| 2 | 84 | Segment Length | 3 | Required, 001-493 |
| 3 | 44 | Information Byte | 1 | Required |
| 4 | 102 | Terminal Identifier | Variable | Required |
| 5 | 78 | Prompt Code | 3 | Required, fixed `990` |
| 6 | 32 | Employee Number | 4 | Conditional |
| 7 | 65 | Password | 6 | Conditional |
| 8 | 105 | Totals Date | 6 | Required |
| 9 | 43 | Hardware Version | 4 | Required |
| 10 | 96 | Software Version | 8 | Required |
| 11 | 39 | Firmware Version | 8 | Required |
| 12 | 86 | Sequence Number | 6 | Required |
| 13 | 176 | Device Card Table Version | 35 | Required |
| 14 | 179 | Host Discount Timestamp | 12, `CCYYMMDDHHMM` | Required |
| 15 | 20 | Currency Code | 3 | Optional |
| 16 | 42 | Grand Total | 8 | Required |
| 17-19 | 13, 16, 15 | Card Label, Card Type Total Count, Card Type Total Amount | 4, 5, 8 | Repeated card buckets |

## Serialization Boundary

Fields 1-17 are separated by Field Separators, including a separator after Field 17 and separators for empty fields. Fields 18-20 are not separated by Field Separators; a Field Separator follows the last occurrence of Field 20.

## Card Bucket Rules

The specification defines up to 20 card types in documented order. Card types 1-15 appear in all approved responses. Card types 16-20 appear only when data occurs. Categories marked with `*` are non-financial and their amounts are excluded from Grand Total.

## Manual Certification Boundaries

The ATL105 text does not define merchant-specific proprietary-load selection, settlement cutoff/timezone policy, retry/duplicate policy, or complete totals reconciliation policy. These remain `REVIEW_REQUIRED` and must not be silently inferred.

## TBA Artifact Pattern

```text
BR -> Segment 119 source anchor
TS -> proprietary-load totals request or approved response
TC -> mutate one field or one separator boundary
TD -> converter-ready synthetic request/response with expected result
```
