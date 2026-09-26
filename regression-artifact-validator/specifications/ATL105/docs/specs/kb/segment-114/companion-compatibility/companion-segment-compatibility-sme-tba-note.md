# Segment 114 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Segment 114 sits in the same message family as Segment 108 (the Loyalty Card Transaction Request), but with the inverse required/optional relationship: Segment 108 is required and Segment 114 is optional. This note captures what is known and not known about how Segment 114 may combine with other segments.

## What Is Known

- Segment 114 belongs to the **Loyalty Card Transaction Request**, whose Data Section 3 contains exactly Segment 108 (required) and Segment 114 (optional, SKU Data Segment) — see Section 11.2.1's Data Section 3 table.
- Segment 114 is never present without Segment 108, because Segment 114's only documented context (Section 11.2.1) always includes the required Segment 108 in the same Data Section 3.
- The Financial Transaction Request's own Data Section 3 companion list (101 Fleet, 102 Product Code, 103 EBT, 104 Purchase Card, 111 Variable Information — Section 11.1.1) does **not** include Segment 114.
- The ECA/TeleCheck Service Transaction Request's Data Section 3 companion list (110 Check, 111 Variable Information, 113 ECA/TeleCheck — Section 11.3.1) does **not** include Segment 114.
- The CA Public Key File Load Request's Data Section 3 companion list (101, 102, 104, 111, 132 — Section 11.9.1) does **not** include Segment 114.

## What Not To Assume

- Do not add a `SEG114-R-0xx` rule allowing Segment 114 to appear as a Financial Transaction Request, ECA/TeleCheck Service Transaction Request, or CA Public Key File Load Request companion — no specification citation supports this in any of Sections 11.1.1, 11.3.1, or 11.9.1.
- The AI Solution Team's `REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST` statement was **confirmed REJECTED 2026-09-26** (`SEG114-SME-002`) — it must not be adopted into the approved catalog and should be reported back to the AI Solution Team as a defect.
- Do not assume Segment 114 can appear alone (without Segment 108) — no specification text documents a standalone Segment 114 pathway.
- Segment 114 **can repeat** within a message, once per scanned SKU (**confirmed 2026-09-26**, `SEG114-SME-006`) — do not enforce a zero-or-one occurrence limit.

## Resolved Questions (2026-09-26)

- `SEG114-SME-002`: the AI-asserted Financial Transaction Request + Segment 114 relationship is a confirmed defect; rejected.
- `SEG114-SME-006`: Segment 114 can repeat within a single message.
