# Segment 114 SME/TBA Input Register

These questions are required to turn the specification-backed starter catalog into approved Segment 114 business requirements and test data. Each response must cite a source, configuration, or named business owner. Unanswered items remain `REVIEW_REQUIRED` and must not be auto-approved.

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG114-SME-001 | Confirm whether Segment 114's maximum length is 1010 (Section 12.13 opening statement and its own valid-values range) or 1009 (the Loyalty Card Transaction Request layout table, Section 11.2.1). | The two spec sources disagree; the discrepancy blocks a confident `SEG114-R-005` max-length check. | Boundary/oversized-segment mutation on Segment Length and SKU Data. | **RESOLVED** — 1010 is authoritative; 1009 is a spec table typo. |
| SEG114-SME-002 | Confirm or reject the AI Solution Team's asserted relationship `REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST` ("The Financial Transaction Request transaction includes SKU Data Segment"). No citation in Sections 11.1.1, 11.3.1, or 11.9.1 supports Segment 114 outside the Loyalty Card Transaction Request. | If the AI statement is simply wrong, it must be excluded from the approved catalog and reported back to the AI Solution Team as a defect. If a real configuration exists, it needs a citation before it can be added. | Companion-segment compatibility rule and negative test scenario (Financial Transaction Request + Segment 114 should be rejected/flagged). | **RESOLVED** — rejected as an AI defect; Segment 114 is Loyalty Card Transaction Request-exclusive. Report back to the AI Solution Team. |
| SEG114-SME-003 | Describe any scenario where Segment 114 (or SKU data) appears on the response side, with a section/page reference. Section 11.2.2 states the Loyalty Card Transaction Response mirrors the generic Financial Transaction Response (implying Segment 114 is request-only). This mirrors the still-open Segment 108 question `SEG108-SME-003`. | Needed to decide whether `SEG114-R-013` (request-only) stands as written. | Response-side structural test(s) if confirmed; otherwise `SEG114-R-013` stands. | **RESOLVED** — confirmed request-only; `SEG114-R-013` stands as written. |
| SEG114-SME-004 | Confirm the Segment Type (Element 85) fixed value is `114` even though Section 12.13, unlike Section 12.14 (Segment 115), does not print an explicit "Fixed value: 114" phrase. | Needed to decide whether `SEG114-R-003` is a hard constant check or a context-derived (non-enforced) field. | Segment Type field-value mutation (`MUT-001` equivalent). | **RESOLVED** — enforce `SegmentType == 114` as a hard rule. |
| SEG114-SME-005 | Provide the location of a dedicated Segment 114 AI Solution Team BR/TS/TC/TD package and real bar-code SKU sample data, or approve continued use of synthesized `.synthetic.json` fixtures. As of this training pass, Segment 114 only appears as a cross-reference inside Segment 100/101/103/108 AI artifact packages — no dedicated Segment 114 package was found. | Item 2 (AI Artifact Comparison) and Item 3 (Independence) need real data to certify beyond a placeholder. | AI-to-Test crosswalk / coverage-ratio reporting; baseline and independence tests. | **RESOLVED** — proceed with synthesized `.synthetic.json` fixtures pending real data. |
| SEG114-SME-006 | Confirm Segment 114 appears at most once per message. The Data Section 3 table shows a single Field No. 5 row (not a repeating structure), but there is no explicit "exactly one" statement as there is for Segment 108 (`SEG108-R-021`). | Needed to decide whether `SEG114-R-010` is enforced as a hard occurrence limit or left as `REVIEW_REQUIRED`. | Duplicate-occurrence mutation test. | **RESOLVED** — Segment 114 CAN repeat, once per scanned SKU; `SEG114-R-010` is not a hard zero-or-one limit. |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real production bar-code SKU data or production terminal credentials; use synthetic or masked values.

## Already-Resolved Items (2026-09-26 intake)

- Max length 1010 vs 1009 → **1010 is authoritative** (`SEG114-SME-001`).
- Financial Transaction Request + Segment 114 relationship → **rejected as an AI defect**; Segment 114 is Loyalty Card Transaction Request-exclusive (`SEG114-SME-002`).
- Segment 114 in the Loyalty Card Transaction Response → **confirmed request-only, never in the response** (`SEG114-SME-003`).
- Segment Type fixed value 114 → **confirmed enforceable as a hard rule** despite the missing explicit citation (`SEG114-SME-004`).
- AI artifacts / real SKU sample data → **proceed with synthesized `.synthetic.json` fixtures** pending real data (`SEG114-SME-005`).
- Repeated occurrence → **Segment 114 can repeat**, once per scanned SKU; not limited to zero-or-one (`SEG114-SME-006`).

All 6 items resolved during the first SME/TBA intake for Segment 114 (2026-09-26) — a faster resolution than Segment 108's first intake (3 of 9 resolved), because the Segment 108 training pass had already established the discrepancy-resolution pattern (table typo vs dedicated section) that Segment 114 reused for `SEG114-SME-001`.
