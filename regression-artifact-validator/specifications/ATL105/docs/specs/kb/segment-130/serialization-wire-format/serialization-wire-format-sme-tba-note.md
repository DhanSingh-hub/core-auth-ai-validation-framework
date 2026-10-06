# Segment 130 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains Segment 130's wire format, including its repeating EMV Additional Information Section — the same repetition pattern already documented for Segment 111's Variable Information Section.

## Fixed Fields vs Repeating Section

Segment 130 has 6 fixed fields (Segment Type, Segment Length, CA Public Key File Checksum, EMV Card Sequence Number, EMV Chip Data Length, EMV Chip Data) followed by zero or more repetitions of a 3-field EMV Additional Information Section (Indicator, Length, Information).

## Field Separator Rules

- A Field Separator appears between every pair of fixed fields (1-2, 2-3, 3-4, 4-5, 5-6), even when a field is empty.
- A Field Separator appears between field 6 and the start of the EMV Additional Information Section.
- **No** Field Separators appear within a repetition (between Indicator/Length/Information) or between repetitions.
- Exactly **one** Field Separator follows the **final** repetition (i.e., it is not duplicated per repetition).

This is structurally identical to Segment 111's Variable Information Section rule (`SEG111-R-007`) — reuse that validator logic pattern rather than reinventing it.

## The 4-Digit Segment Length

Segment 130 requires a 4-digit Segment Length, one of exactly seven segments across the specification with this requirement (103, 114, 115, 118, 120, 130, 131).

## Maximum Length — Confirmed Specification Conflict

The specification states **two different** maximum lengths for Segment 130. This is a genuine internal inconsistency, not an extraction artefact.

| Source | Stated maximum | Wording |
|---|---:|---|
| **Section 12.20** (segment definition) | **3,043** | "It has a maximum length of 3043 alphanumeric characters (001–3043/a-z/A–Z)." |
| **Element 84** valid-codes table (Section 13.2) | **3,043** | `001-3043  EMV Request Data Segment (No.130)` |
| **Section 11.8.1** Data Section 3 table | **9,999** | Max. Len. cell for the Segment 130 row |

The earlier `[PROVISIONAL]` note speculated that the 11.8.1 figure was an OCR artefact of "3043". **It is not.** The layout table's Max. Len. column wraps after three characters, and sibling rows confirm the pattern unambiguously:

```text
103 EBT Data Segment        ->  "3,33" + "4"   =  3,334
151 Incomm Market Basket    ->  "230"  + "9"   =  2,309
130 EMV Request Data Segment->  "999"  + "9"   =  9,999   <-- genuinely 9999
```

**Adopted position:** use **3,043**. It is stated explicitly in the segment's own defining section and corroborated independently by the Element 84 valid-codes table — two sources against one. Independent field arithmetic also supports it: `3 + 4 + 25 + 3 + 3 + 999` fixed fields `+ 2,000` repeating section `+ 6` separators `= 3,043`. That arithmetic reproduces 3,043 *exactly*, which is decisive.

`SEG130-SME-001` remains open **only** to have the Test Team confirm the disposition and raise a specification defect against the 11.8.1 table — not to determine which value to implement.

## Contrast With Segment 131 (Response)

Segment 130 and its response counterpart have **opposite** separator rules. This is the single most common source of EMV wire-format defects.

| Aspect | Segment 130 (Request) | Segment 131 (Response) |
|---|---|---|
| Field Separators | **Present** between fixed fields, even when empty | **Absent** — "When a field is not populated, the next field immediately follows" |
| Maximum length | 3,043 | 3,834 |
| Additional Info Section cap | 2,000 bytes | **2,800 bytes** |
| CA Public Key File Checksum | **Optional** (field 3) | **Required** (field 3), echoed from request |
| EMV Card Sequence Number | Present (field 4, Conditional) | **Not present** |
| Origin | Device | BUYPASS (host) |

A validator that reuses the Segment 130 parser for Segment 131 will mis-parse every response.

## What Not To Assume

- Do not emit a Field Separator between EMV Additional Information Section repetitions — only one follows the last.
- Do not assume the repeating section is required — it is entirely optional (zero repetitions is valid).
- Do not apply Segment 130's separator rules to Segment 131 — the response has none.
- Do not apply the 2,000-byte Additional Information cap to Segment 131 — its cap is 2,800.
- Do not assume Segment 130's TLV-encoded EMV Chip Data can be validated for cryptographic authenticity without a certified EMV kernel/HSM (`SEG130-R-010`).
