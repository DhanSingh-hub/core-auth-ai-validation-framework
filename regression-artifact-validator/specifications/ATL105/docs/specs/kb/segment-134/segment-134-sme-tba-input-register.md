# Segment 134 SME/TBA Input Register

| ID | Manual input required | Why it is needed | Status |
| --- | --- | --- | --- |
| SEG134-SME-001 | Confirm whether Segment 134 is genuinely an eighth 4-digit-Segment-Length segment, alongside the previously-confirmed seven (103, 114, 115, 118, 120, 130, 131). | Its field table lists Segment Length as 4 characters, but it was not part of the cross-segment "seven segments" list confirmed via Segment 120. | REVIEW_REQUIRED |
| SEG134-SME-002 | Identify which merchant configurations have Non-traditional Signature Debit (Settlement Type `X`) enabled. | The specification explicitly says to check with a First Data Project/Relationship Manager — this is a business configuration, not a technical rule. | REVIEW_REQUIRED |
| SEG134-SME-003 | Provide a dedicated Segment 134 AI Solution Team BR/TS/TC/TD package and real transaction-attribute sample data, or approve synthesized fixtures. | None located. | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception.
