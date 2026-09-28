# Segment 130 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Segment 130 belongs to the EMV Financial Transaction Request, whose Data Section 3 companion list differs subtly from the generic Financial Transaction Request's list.

## What Is Known

- The EMV Financial Transaction Request's Data Section 3 table places all companions in **Field Nos. 4–8** (five slots), versus Field Nos. 4–9 (six slots) for the generic Financial Transaction Request. Segment 130 consumes one of those five slots, so at most **four** other Section 3 segments can accompany it.
- Segment 130 is marked `R` in that table with the note "This segment is the only segment required for all EMV financial transactions" — Required in the EMV context, absent otherwise.
- The authoritative Data Section 3 **table** for the EMV Financial Transaction Request lists: **101, 102, 103, 104, 111, 123, 130, 135, 143, 145, 146, 151, 152, 153**. Chapter 12's segment/transaction matrix independently corroborates each of these for the EMV Financial Transaction Request column.
- Segment 130 always precedes Segment 131 (EMV Response Data Segment) in the request/response pair, and its CA Public Key File Checksum (Element 187) is echoed back by Segment 131.

### ⚠️ Correction — Segment 103 (EBT) **is** a permitted companion

An earlier revision of this note asserted that Segment 103 (EBT) was excluded from the EMV companion list. **That was incorrect.** The claim was derived from the abbreviated prose bullet list in Section 11.8.1, which names only 101/102/104/111/130. That prose list is a partial summary, not the normative list — it is immediately followed by the sentence "See the Data Section No. 3 table below for specifics on what segments may be included."

Two independent authoritative sources include 103:

| Source | Evidence |
|---|---|
| Section 11.8.1 Data Section 3 **table** | `103  EBT Data Segment  3,334  C  Contains EBT-specific data.` |
| Chapter 12 segment/transaction matrix | `103 EBT  X    X` — column 1 = Financial Transaction Request, column 5 = EMV Financial Transaction Request |

**Do not** reject an EMV Financial Transaction Request solely because it carries Segment 103.

## Applicability Exception — EMV Reversals

Section 10.14.2.4 states plainly: **"EMV data is not required on Reversal transactions."** Segment 130's "required" status is therefore scoped to EMV authorization-class requests, not to every message carrying an EMV Prompt Code. A Purchase Reversal or Time-out Reversal in an EMV flow may legitimately omit Segment 130.

## What Not To Assume

- Do not assume the Section 11.8.1 prose bullet list is the complete companion list — the table below it is normative.
- Do not assume Segment 130 is required on EMV reversals (Section 10.14.2.4 exempts them).
- Do not assume more than four companions can accompany Segment 130 — Field Nos. 4–8 cap the section at five segments total.
- Do not assume Segment 130's CA Public Key File Checksum is cryptographically validated by this framework — that requires a real key file (`SEG130-SME-002`).
- Do not assume Element 118 in Segment 130 (EMV Additional Information) means the same thing as Element 118 in Segment 112 (Additional Information) — they are independent fields that happen to share a number.

## Open Question

`SEG130-SME-005` (see the [SME/TBA Input Register](../segment-130-sme-tba-input-register.md)): should the existing dedicated AI Solution Team BR package for Segment 130 be treated as canonical for Item 2 comparison purposes?
