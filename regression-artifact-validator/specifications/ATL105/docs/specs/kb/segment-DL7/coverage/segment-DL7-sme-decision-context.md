# Segment DL7 SME Decision Context

Segment DL7 coverage is candidate-only until all open provisional items are resolved. This note summarizes the decisions needed by the generated coverage package; update the generated SME/TBA register rather than editing it by hand.

## Open items

| Item | Register | Decision needed | Current handling |
|---|---|---|---|
| P-01 | `SEGDL7-SME-001` | Confirm Appendix W is in scope for this training pass. | `SEGDL7-R-003` candidates use `OMITTED_PENDING_P01` placeholders and remain `REVIEW_REQUIRED`. |
| P-02 | `SEGDL7-SME-002` | Provide a dedicated DL7 AI/Test package or approve synthesized fixtures. | No phase-one DL7 TC/meta pair exists; all test data is synthetic and unapproved. |
| P-03 | `SEGDL7-SME-003` | Resolve Appendix W `an1` wording versus table lengths `003` and `013`. | Table-data bytes are represented symbolically as `OMITTED_PENDING_P03`. |
| P-04 | `SEGDL7-SME-004` | Identify the message/position carrying DL7 and whether Download Data can span multiple DL7 segments. | Payloads are isolated segment candidates, not executable message fixtures. |
| P-05 | `SEGDL7-SME-005` | Decide whether Element 84 includes its own three digits and why its source is Device. | Segment Length values use `OMITTED_PENDING_P05`; validator equality checks are not asserted. |

## Candidate status

- BR/TS/TC/TD artifacts are independent Test Solution candidates only.
- Approved test-data pairs, executed test cases, and certified rules remain zero.
- The supplied AI catalog has zero records where `segment == "DL7"`; all AI crosswalk rows are review-required with zero confirmed semantic matches.
