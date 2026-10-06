# Additional TransArmor Data (Segment 111 Companion): SME/TBA Learning Note

## Core Idea

Segment 116 is not the only place TransArmor-related data travels. Appendix I-53/54 independently documents "Additional TransArmor Data" as Table ID `052` inside **Segment 111** (Variable Information Data Segment) — a completely different segment from 116. This table exists because "in some TransArmor implementations, a longer Key ID and/or a Special device type can be specified using this Data Element" (Segment 111, Table ID 52 note).

```text
TransArmor implementation needs a longer Key ID or a special device type
  -> Segment 111 (not Segment 116)
  -> Variable Information Indicator = 052 (Additional TransArmor Data)
  -> TLV format, first 3 digits = length
  -> Sub-Table 01 (KSN) and/or Sub-Table 02 (Device Type)
```

## Source-Confirmed Facts

| Field | Fixed value / length | Requirement |
|---|---|---|
| Table ID | `052` | Fixed |
| Table Length | 3 bytes | — |
| Table Data | up to 100 alphanumeric digits, TLV | Contains sub-tables required by various TransArmor implementations |
| Sub-Table 01 (KSN) | up to 40 bytes | Required for AES DUKPT, TDES, and Ingenico OnGuard encryption types |
| Sub-Table 02 (Device Type) | up to 8 bytes | Required for AES DUKPT; optional for TDES and Ingenico OnGuard |

**Confirmed example** (Appendix I, page 596): `111+051+0520370102412345678123456780000000102003NCR+`

## SME Questions

1. Is this Segment 111 sub-table always sent alongside a Segment 116 Key/Key ID Load request, or can it appear independently (for example, in an ordinary financial transaction that merely needs to declare an extended Key ID)?
2. Which specific TransArmor implementations (beyond "AES DUKPT, TDES, Ingenico OnGuard") use this sub-table, and is that list exhaustive?
3. Does the "Special device type" mentioned in the Table ID 52 note correspond one-to-one with the Sub-Table 02 (Device Type) content?

## TBA Rule Pattern

```text
BR: A device using AES DUKPT encryption shall include both the KSN and Device Type sub-tables when sending Additional TransArmor Data.
TS: Device sends Segment 111 with Variable Information Indicator 052 for an AES DUKPT-encrypted transaction.
TC: Validate Table ID, Table Length, and the required/optional sub-tables per encryption type.
TD: Sanitized converter-ready Segment 111 fixture using the confirmed Appendix I example as a template.
```

## Current Boundary

This table's field definitions are independently confirmed (Appendix I-53/54) and are not blocked by the missing external TransArmor document. What remains open is only its relationship to the Segment 116 flow (`SEG116-SME-004`) — do not assume the two must always travel together.
