# Segment 108 SME/TBA Input Register

These questions are required to turn the specification-backed starter catalog into approved Segment 108 business requirements and test data. Each response must cite a source, configuration, or named business owner. Unanswered items remain `REVIEW_REQUIRED` and must not be auto-approved. Answers already collected during the 2026-09-22 training intake are marked `RESOLVED`.

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG108-SME-001 | Confirm whether Segment 108's maximum length is 142 (Section 12.7 / Element 84 table) or 84 (Loyalty Card Transaction Request layout table). | The two spec sources disagree; the discrepancy blocks a confident `SEG108-R-005` max-length check. | Boundary/oversized-segment mutation. | **RESOLVED** — 142 is authoritative; 84 is a spec table typo. |
| SEG108-SME-002 | Provide the Update Code (Element 143) value(s), if any, that represent "Reversal of coupon redeem" and "Reversal of points redeemed" (Section 10.9.3 describes 9-10 advice functions but only 8 codes are documented: A, C, E, I, P, S, T, U). | Needed to complete `SEG108-R-013`'s enumeration and to write correct positive/negative reversal test data. | Update Code enumeration and reversal-flow scenarios. | REVIEW_REQUIRED |
| SEG108-SME-003 | Describe any scenario where Segment 108 (or loyalty data) appears on the response side, with a section/page reference, transaction type, and field layout. | Section 11.2.2 states the Loyalty Card Transaction Response mirrors the generic Financial Transaction Response (implying Segment 108 is request-only), but this was explicitly disputed without a citation during intake. | Response-side structural test(s) if confirmed; otherwise `SEG108-R-024` stands as request-only. | REVIEW_REQUIRED |
| SEG108-SME-004 | Confirm whether Appendix K Table 008 (Loyalty Information — Version 1) and Table 010 (Loyalty Information — Version 2) receipt layouts are in scope for this Segment 108 training pass, or a separate Appendix K workstream. | Section 10.9.4 references these tables for loyalty receipts; they are not deeply transcribed in this KB pass. | Receipt/print-data coverage, if in scope. | REVIEW_REQUIRED |
| SEG108-SME-005 | Provide real AI-generated Segment 108 BR/TS/TC/TD packages, or confirm the location of an external AI pipeline output for Segment 108. | No real AI Solution Team artifact has been ingested for Segment 108 as of this training pass; Item 2 (AI Artifact Comparison) needs real data to certify beyond a placeholder. | AI-to-Test crosswalk / coverage-ratio reporting. | REVIEW_REQUIRED |
| SEG108-SME-006 | Provide real Segment 108 sample JSONs (Loyalty Purchase, Points Redemption, Coupon Redemption, Reversal, Account Inquiry, Totals Report), or approve continued use of synthesized `.synthetic.json` fixtures. | No real Segment 108 test data exists yet; this training pass proceeds with synthetic fixtures pending approval. | Item 1/3 baseline and independence tests. | REVIEW_REQUIRED |

## Already-Resolved Items (2026-09-22 intake)

- Max length 142 vs 84 → **142 is authoritative** (SEG108-SME-001).
- Segment 108 is exclusive to the Loyalty Card Transaction Request (not a Financial Transaction Request companion) → **confirmed**.
- Street Address(144)+Phone(145) substituting for Loyalty Account Number(139) when the card is absent → **catalog only, not code-enforced** (matches how similar business-condition rules are treated for other segments).
- Element 146 (Expiration Date), marked "reserved for future use" → **validator treats it as inert; no format enforced even if populated**.

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real loyalty account numbers, phone numbers, or production terminal credentials; use synthetic or masked values.
