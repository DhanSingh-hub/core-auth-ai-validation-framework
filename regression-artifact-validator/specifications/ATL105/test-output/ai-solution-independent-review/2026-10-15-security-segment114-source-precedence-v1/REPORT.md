# Segment 114 Transaction-Combination Source Precedence

## Controlling Source

ATL105 release 2026-3, Chapter 12, "Data Segments and the Transactions in which They Can Appear," page 12-2, is the specified source for the segment/transaction combination. Its Segment 114 (SKU) row marks inclusion in the Financial Transaction Request, Loyalty Card Transaction Request, and EMV Financial Transaction Request columns.

The table answers where Segment 114 **can be included**. A marked transaction is not thereby required to carry it; requirement/optional status and transaction conditions must be taken from the applicable message-format rules.

## Conflict Resolution

| AI BR | AI statement | AI chain | Table comparison | Disposition |
|---|---|---|---|---|
| `REQ-SRC-ATL105-PDF-001:4485` | Financial Transaction Request includes SKU Segment 114. | `SC-9859` -> `TC-016360`, physically complete per v6. | Table 12-2 permits Segment 114 in Financial Transaction Request. | AI statement aligns with the combination table; the v3 crosswalk missed it because the BR has no numeric element anchor. |
| `REQ-SRC-ATL105-PDF-001:4487` | EMV Financial Transaction Request includes SKU Segment 114. | `SC-9861` -> `TC-016362`, physically complete per v6. | Table 12-2 permits Segment 114 in EMV Financial Transaction Request. | AI statement aligns with the combination table; the v3 crosswalk missed it because the BR has no numeric element anchor. |
| `SEG114-R-001` | Segment 114 is exclusive to Loyalty Card Transaction Request and not a companion in the cited request lists. | Test chain: `BR-RULE-SEG114-R-001` -> `TS-REVIEW-BR-RULE-SEG114-R-001` -> `TC-REVIEW-TS-REVIEW-BR-RULE-SEG114-R-001` -> `TD-REVIEW-TC-REVIEW-TS-REVIEW-BR-RULE-SEG114-R-001`. | Its exclusivity claim conflicts with the Chapter 12 matrix entries for Financial and EMV Financial requests. | Do not use this Test BR to reject AI `:4485` or `:4487` in the segment-combination comparison. The Test BR remains `DRAFT_REVIEW_REQUIRED`, its links are review placeholders, and its applicability/expected outcomes require authorized revision. |

## Precedence Applied

For this segment-combination question, the explicit specification table controls both AI and Test interpretations. This is the user's requested source-precedence rule and supersedes the earlier Test-over-AI tie-break for this case. It does not turn either AI chain into a confirmed semantic match, and does not approve or adjudicate the draft Test BR.

The AI case records are physical artifact evidence only; no Test fixture or host execution is claimed. The Test chain has unresolved applicability, positive/negative cases, expected outcome, and SME fixture.

## Source References

- `docs/specs/extracted_text.txt`, Chapter 12, page 12-2, Segment 114 row and transaction columns.
- `docs/specs/kb/segment-114/segment-114-sme-tba-input-register.md`, `SEG114-SME-002`, reopened 2026-09-29 pending a cited source.
- `test-output/ai-solution-independent-review/2026-10-14-delivery-wide-assessment-v6/br-assessment.jsonl`, AI BR chain records.
- `test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json`, independent Test BR and placeholder chain.
