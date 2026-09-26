# Segment 130 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Segment 130 belongs to the EMV Financial Transaction Request, whose Data Section 3 companion list differs subtly from the generic Financial Transaction Request's list.

## What Is Known

- The generic Financial Transaction Request's Data Section 3 layout table (which enumerates ALL possible companions across variants) lists Segment 130 with the note "This segment is the only segment required for all EMV financial transactions" — i.e., Required specifically in the EMV context, Conditional/absent otherwise.
- Segment 130's companions in the EMV Financial Transaction Request are 101 (Fleet), 102 (Product Code), 104 (Purchase Card), and 111 (Variable Information) — **Segment 103 (EBT) is excluded** from this list, unlike the generic Financial Transaction Request's {101,102,103,104,111}.
- Segment 130 always precedes Segment 131 (EMV Response Data Segment) in the request/response pair, and its CA Public Key File Checksum (Element 187) is echoed back by Segment 131.
- Segment 130 may also appear alongside Segment 123 (NFC Payment Tokenization Data Segment) per the generic companion table, though the exact interaction is not detailed in Section 12.20 itself.

## What Not To Assume

- Do not assume Segment 103 (EBT) can accompany Segment 130 in the same EMV Financial Transaction Request — it is absent from the EMV-specific companion list.
- Do not assume Segment 130's CA Public Key File Checksum is cryptographically validated by this framework — that requires a real key file (`SEG130-SME-002`).
- Do not assume Element 118 in Segment 130 (EMV Additional Information) means the same thing as Element 118 in Segment 112 (Additional Information) — they are independent fields that happen to share a number.

## Open Question

`SEG130-SME-005` (see the [SME/TBA Input Register](../segment-130-sme-tba-input-register.md)): should the existing dedicated AI Solution Team BR package for Segment 130 be treated as canonical for Item 2 comparison purposes?
