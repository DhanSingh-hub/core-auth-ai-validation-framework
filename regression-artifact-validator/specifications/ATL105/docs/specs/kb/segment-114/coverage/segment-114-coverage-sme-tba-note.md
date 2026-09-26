# Segment 114 Coverage Closure — SME and TBA Note

## Why This Package Exists

Segment 114 (SKU Data Segment) is the simplest segment trained so far — only 3 fields, and no field-level decision logic. It is not a companion of the generic Financial Transaction Request (unlike Segments 101, 102, 103, 104, 111); it belongs to the Loyalty Card Transaction Request's Data Section 3, where it is the sole **optional** companion of the required Segment 108. Coverage closure proves that every catalog rule (`SEG114-R-###`) is independently backed by a business requirement, a test scenario, a test case, and test data — not merely referenced by an AI-generated artifact.

## Coverage Classification

| Status | Meaning |
| --- | --- |
| `COVERED` | Rule has a BR, scenario, test case, and test data, all sharing the canonical source anchor. |
| `PARTIALLY_COVERED` | Rule has at least one link in the BR→TS→TC→TD chain, but the chain is incomplete. |
| `REVIEW_REQUIRED` | Rule is blocked by a `PROVISIONAL` item pending SME/TBA input. |
| `MISSING` | Rule has no BR, scenario, test case, or test data at all. |

## Segment 114 Specific Notes

- Segment 114's message family is exclusive: it is never listed among the Financial Transaction Request's (11.1.1), ECA/TeleCheck Service Transaction Request's (11.3.1), or CA Public Key File Load Request's (11.9.1) Data Section 3 companions. The AI Solution Team's requirement catalog nonetheless contains a statement asserting a Financial Transaction Request relationship (`REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST`) — this was **confirmed REJECTED 2026-09-26** (`SEG114-SME-002`) as an AI defect, not a certified rule.
- Segment 114's maximum length had a two-source conflict: Section 12.13 says 1010; the Loyalty Card Transaction Request layout table (Section 11.2.1) says 1009. **RESOLVED 2026-09-26** (`SEG114-SME-001`): 1010 is authoritative; 1009 is a spec table typo — the same resolution pattern already applied to Segment 108 (142 vs 84).
- Unlike every other field in Segment 108's catalog, Segment 114's Segment Type fixed value (114) is not backed by an explicit "Fixed value: 114" citation in Section 12.13 (contrast Section 12.14 for Segment 115, which does print one). **RESOLVED 2026-09-26** (`SEG114-SME-004`): enforced as a hard rule regardless.
- Segment 114 requires a 4-digit Segment Length, unlike Segment 108's 3-digit length — confirmed by two independent sources (Section 12.13 itself, and Segment 120's cross-segment Segment Length rule catalog).
- Segment 114 **can repeat** within a message, once per scanned SKU. **RESOLVED 2026-09-26** (`SEG114-SME-006`): not limited to zero-or-one occurrence.
- Prior to this training pass, `ENT-SEG-114` already carried three independently-recorded traceability gaps (`REQUIREMENT_ONLY`, `SCENARIO_ONLY`, `TEST_CASE_NO_DATA`) — see the [coverage closure flow](segment-114-coverage-flow.md#current-baseline-pre-sme-intake) for the baseline this training pass must close. These traceability gaps (BR→TS→TC→TD chain completeness) are independent of the six now-resolved SME/TBA rule questions and still require Item 2-4 work.
