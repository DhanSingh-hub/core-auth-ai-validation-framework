# Segment 108 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Unlike Segment 101 (documented mutual exclusion with Segment 145) or Segment 103 (no documented conflict, but shares the Financial Transaction Request's Data Section 3 with 101/102/104/111), Segment 108 sits in a completely separate message family. This note captures what that means for compatibility.

## What Is Known

- Segment 108 belongs to the **Loyalty Card Transaction Request**, whose Data Section 3 contains exactly Segment 108 (required) and Segment 114 (optional, SKU Data Segment) — see Element 63's processing rules and Section 11.2.1.
- The Financial Transaction Request's own Data Section 3 companion list (101 Fleet, 102 Product Code, 103 EBT, 104 Purchase Card, 111 Variable Information) does **not** include Segment 108.
- No specification text documents a "Financial Transaction Request carrying Segment 108" pathway, nor a "Loyalty Card Transaction Request carrying Segment 101/102/103/104" pathway.

## What Not To Assume

- Do not add a `SEG108-R-0xx` rule allowing Segment 108 to appear as a Financial Transaction Request companion — no specification citation supports this.
- Do not assume Segment 108 can combine with Segment 101/102/103/104/111 in one message; treat any such combination as `REVIEW_REQUIRED`, not a supported scenario.
- Do not assume Segment 114 (SKU Data Segment) is Loyalty-only — it is also used elsewhere (e.g., alongside Segment 103's EBT-with-eWIC scenarios per some product catalogs); this note only certifies that Loyalty Card Transaction Request's Data Section 3 is `{108, 114}`.

## Open Question

`SEG108-SME-004` (see the [SME/TBA Input Register](../segment-108-sme-tba-input-register.md)): are Appendix K Table 008/010 loyalty-information layouts (used for receipts) in scope for this segment's companion/compatibility model, or a separate Appendix K workstream?
