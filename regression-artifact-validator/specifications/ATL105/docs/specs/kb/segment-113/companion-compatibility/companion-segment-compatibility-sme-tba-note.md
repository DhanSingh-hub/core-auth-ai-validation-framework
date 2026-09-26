# Segment 113 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Segment 113 sits in its own message family, distinct from the Financial Transaction Request's Data Section 3. This note captures what that means for compatibility, mirroring [Segment 108's companion-compatibility note](../../segment-108/companion-compatibility/companion-segment-compatibility-sme-tba-note.md).

## What Is Known

- Segment 113 belongs to the **ECA/TeleCheck® Service Transaction Request**, whose Data Section 3 contains Segment 110 (Check Data Segment, required), Segment 111 (Variable Information Data Segment, optional), and Segment 113 (ECA/TeleCheck® Data Segment, conditional) — see Section 11.3.1.
- The Financial Transaction Request's own Data Section 3 companion list (101 Fleet, 102 Product Code, 103 EBT, 104 Purchase Card, 111 Variable Information) does **not** include Segment 113.
- No specification text documents a "Financial Transaction Request carrying Segment 113" pathway, nor an "ECA/TeleCheck Service Transaction Request carrying Segment 101/102/103/104" pathway.
- Element 63's processing-rule summary for ECA/TeleCheck requests only names Segments 110 and 111 — this is an incomplete summary (confirmed via SME intake), not evidence that 113 is invalid.

## What Not To Assume

- Do not add a `SEG113-R-0xx` rule allowing Segment 113 to appear as a Financial Transaction Request companion — no specification citation supports this.
- Do not assume Segment 113 can combine with Segment 101/102/103/104/108 in one message; treat any such combination as `REVIEW_REQUIRED`, not a supported scenario.
- Do not assume Segment 111 (Variable Information Data Segment) is exclusive to the ECA/TeleCheck family — it is also a documented Financial Transaction Request companion; this note only certifies that the ECA/TeleCheck Service Transaction Request's Data Section 3 is `{110, 111, 113}`.
