# Segment 113 Coverage Closure — SME and TBA Note

## Why This Package Exists

Segment 113 (ECA/TeleCheck® Data Segment) belongs to its own dedicated **ECA/TeleCheck® Service Transaction Request** message family — it is explicitly absent from the Financial Transaction Request's Data Section 3 companion list (Section 11.1.1: 101, 102, 103, 104, 111 only) and explicitly present in the ECA/TeleCheck Service Transaction Request's own Data Section 3 list (Section 11.3.1: 110, 111, 113). Coverage closure proves that every catalog rule (`SEG113-R-###`) is independently backed by a business requirement, a test scenario, a test case, and test data — not merely referenced by an AI-generated artifact.

## Coverage Classification

| Status | Meaning |
| --- | --- |
| `COVERED` | Rule has a BR, scenario, test case, and test data, all sharing the canonical source anchor. |
| `PARTIALLY_COVERED` | Rule has at least one link in the BR→TS→TC→TD chain, but the chain is incomplete. |
| `REVIEW_REQUIRED` | Rule is blocked by an unresolved SME/TBA question. |
| `MISSING` | Rule has no BR, scenario, test case, or test data at all. |

## Segment 113 Specific Notes

- Element 63's (Number of Segments) processing-rule text is inconsistent with Section 11.3.1's own layout table: the former lists only Segments 110 and 111 for ECA/TeleCheck requests, while the latter explicitly includes Segment 113. SME intake resolved this in favor of the more specific Section 11.3.1 table (`SEG113-SME-001`).
- Element 132 (ECA/TeleCheck Product Code) has no documented enumeration anywhere in the specification or its appendices — confirmed free-form by SME intake (`SEG113-SME-002`), unlike Element 77 (Product Code, Segment 102) which has a dedicated Appendix F table.
- Two rules describe genuine cross-cutting business conditions that are cataloged but NOT code-enforced by the single-segment JSON payload validator, per SME direction: Trace ID (134) required on Void requests (`SEG113-SME-003`), and Extended MICR Data (137) supplementing Segment 110's MICR Data (122) when raw MICR exceeds 50 bytes (`SEG113-SME-004`) — this second one is also a genuine cross-segment (110↔113) dependency, not just a cross-field one.
- Unlike Segment 108, Segment 113 has no field-order reversal quirk — fields 3-9 map directly to elements 131-137 in ascending order.
- Segment 113 does not appear in the ECA/TeleCheck Service Transaction Response (mirrors the generic Financial Transaction Response layout per Section 11.3.2) — unlike Segment 108's disputed response-presence question, this was not contested during SME intake.
