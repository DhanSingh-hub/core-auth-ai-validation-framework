# Segment 115 Coverage Closure — SME and TBA Note

## Why This Package Exists

Segment 115 (Print Data Segment) is the first response-only, conditionally-included segment trained in this KB. Coverage closure proves that every catalog rule (`SEG115-R-###`) is independently backed by a business requirement, a test scenario, a test case, and test data — not merely referenced by an AI-generated artifact.

## Coverage Classification

| Status | Meaning |
| --- | --- |
| `COVERED` | Rule has a BR, scenario, test case, and test data, all sharing the canonical source anchor. |
| `PARTIALLY_COVERED` | Rule has at least one link in the BR→TS→TC→TD chain, but the chain is incomplete. |
| `REVIEW_REQUIRED` | Rule is blocked by a `PROVISIONAL` item pending SME/TBA input. |
| `MISSING` | Rule has no BR, scenario, test case, or test data at all. |

## Segment 115 Specific Notes

- Segment 115 is response-only — never present in any Request message. Any "Segment 115 in a request" test case should be a `MUST_FAIL` guard, not a positive scenario.
- Three conflicting maximum-length figures exist (1,009 / 910 / 999) — do not certify a boundary mutation test until `SEG115-SME-001` resolves which governs.
- The "no other data segments are contained" statement (Section 12.14) appears to conflict with the Section 11.1.2 layout table showing Segment 112 and Segment 115 as independently conditional — this is a genuine specification ambiguity (not an AI extraction error) and must be routed to the specification owner, not just the AI Solution Team.
- The connection between Segment 115 and Segment 108's "Loyalty Print Data" is inferred, not confirmed — do not assume it when writing loyalty-specific test scenarios.
- Every AI-to-Test crosswalk entry for the `BR-249-*` statements matched against the wrong segment (101, 103, 104, 108, 111, 113, 123, 130, 135) prior to this training pass, because no Segment 115-specific Test rule existed.
