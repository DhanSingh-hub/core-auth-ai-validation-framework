# Segment 115 SME/TBA Input Register

These questions are required to turn the specification-backed starter catalog into approved Segment 115 business requirements and test data. Each response must cite a source, configuration, or named business owner. Unanswered items remain `REVIEW_REQUIRED` and must not be auto-approved.

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG115-SME-001 | Confirm Segment 115's maximum length: 1,009 (Section 12.14 opening, corroborated by AI Solution Team BR-249-3) vs 910 (Section 11.1.2 Financial Transaction Response layout table) vs 999 (EMV Financial Transaction Response layout table). | Three conflicting figures block a confident `SEG115-R-005` max-length check. | Boundary/oversized-segment mutation on Segment Length and Print Data. | REVIEW_REQUIRED |
| SEG115-SME-002 | Explain the exact decision logic by which a Financial Transaction Response includes Segment 112, Segment 115, both, or neither. Element 115's own documented values are only 0 (none follows) / 1 (follows), which does not by itself distinguish "112 follows" from "115 follows." | Needed to make `SEG115-R-002` enforceable as more than an inferred rule. | Response-assembly conditional test scenarios. | REVIEW_REQUIRED |
| SEG115-SME-003 | Confirm Print Data (Element 152) length: 999 (Section 12.14 field table) vs 900 bytes (internal `13-data-elements.md` reference). | Needed to decide the enforced upper bound for `SEG115-R-008`. | Print Data boundary mutation. | REVIEW_REQUIRED |
| SEG115-SME-004 | Clarify whether "no other data segments are contained in the Financial Transaction Response" (Section 12.14) forbids Segment 112 from co-occurring with Segment 115, or only means nothing besides 112/115 can appear. The Section 11.1.2 layout table shows both as independently conditional in the same response. | Needed to resolve `SEG115-R-010`, a direct apparent contradiction also flagged by the AI Solution Team's own BR-249-4 statement. | Companion-segment compatibility test (112 + 115 together). | REVIEW_REQUIRED |
| SEG115-SME-005 | Confirm whether Segment 115 is the wire-format vehicle for Segment 108's "Loyalty Print Data" (Account Inquiry / Totals Report responses), and whether Segment 115 can appear in the Loyalty Card Transaction Response. | Needed to resolve `SEG115-R-012` / `SEG115-R-013`; also relevant to Segment 108's still-open `SEG108-SME-003`. | Loyalty-response structural test, if confirmed. | REVIEW_REQUIRED |
| SEG115-SME-006 | Provide the location of a dedicated Segment 115 AI Solution Team BR/TS/TC/TD package and real Financial-Transaction-Response sample data (with print data payloads), or approve continued use of synthesized `.synthetic.json` fixtures. | No dedicated Segment 115 package exists; every AI-to-Test crosswalk entry for the `BR-249-*` statements matched against the wrong segment (100, 101, 103, 104, 108, 111, 113, 123, 130, 135). | AI-to-Test crosswalk / coverage-ratio reporting; baseline and independence tests. | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real production print-data payloads, receipt text, or terminal credentials; use synthetic or masked values.

## Why All Six Items Are Open

No SME/TBA intake has yet occurred for Segment 115. This register is the mechanism for collecting that input.
