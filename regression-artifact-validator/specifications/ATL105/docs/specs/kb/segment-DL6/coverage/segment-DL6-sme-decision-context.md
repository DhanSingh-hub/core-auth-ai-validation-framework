# Segment DL6 SME Decision Context

This candidate-only note records every open provisional decision gate used by the DL6 coverage package. It does not update the generated SME register.

| Item | Register ID | Affected rules | Decision needed | Current package treatment |
|---|---|---|---|---|
| P-01 | SEGDL6-SME-001 | SEGDL6-R-003 | Confirm Element 166 is intentionally reused for both Start Time and End Time in DL6. | BR/TS/TC/TD stay REVIEW_REQUIRED; no alternate element is invented. |
| P-02 | SEGDL6-SME-002 | AI artifacts, test data | Approve or replace synthesized DL6 fixtures because the supplied AI chain is representative only. | All package data remains synthetic and unapproved. |
| P-03 | SEGDL6-SME-003 | SEGDL6-R-002 | Resolve stated maximum length 9 versus four fields summing to 10. | Marker/length positives are REVIEW_REQUIRED; approved/executed/certified counts stay zero. |
| P-04 | SEGDL6-SME-004 | SEGDL6-R-002 | Resolve Field 1 description, Element 34 segment list, and End Time source inconsistencies. | R-002 remains REVIEW_REQUIRED and candidate-only. |
| P-05 | SEGDL6-SME-005 | SEGDL6-R-007 | Define window behavior across midnight, Start = End, and the applicable time basis. | Behavioral outcomes use OMITTED_PENDING_P05 placeholders. |
| P-06 | SEGDL1-SME-003 | SEGDL6-R-006 | Confirm Table Load Response block-boundary interpretation around the `*` delimiters. | Data Block 4 positive and negative candidates stay REVIEW_REQUIRED. |
