# Response-Only Placement and Ordering: SME and TBA Learning Note

## Two Questions, Not One

"Where does Segment 120 belong?" is really two independent questions that
must not be merged into a single rule:

```text
Question 1 (Applicability): Is Segment 120 legal in THIS message?
  -> only Financial Transaction Response / EMV Financial Transaction Response
  -> never a request

Question 2 (Ordering): Given that it IS legal here, where does it sit
relative to the other Section 3 companion segments?
  -> AT THE END, per BR-263-2 (P-01 resolved 2026-09-23)
```

## SME Reasoning for Applicability (`SEG120-R-006`)

1. Is the message under test a request or a response?
2. If a response, is it a plain Financial Transaction Response or an EMV
   Financial Transaction Response? (Both are confirmed valid contexts.)
3. Would a negative test — Segment 120 appearing on a *request* — be a
   meaningful test case to add, given that none currently exists in the AI
   Solution's approved test-case catalog for this segment?

## SME Reasoning for Ordering (`SEG120-R-007`, `P-01` — RESOLVED 2026-09-23)

This was the headline open question for Segment 120 and has now been resolved by closer
reading of the AI Solution's own artifacts:

| Source | Claim |
| --- | --- |
| `BR-263-2` (spec page 263) + `SC-1839` / `TC-4305`, `TC-4306` | Segment 120 always appears at the end of a Financial Transaction Response |
| `docs/atl105_complete_templates.json`, `Financial Transaction Response` template | Segment 120 is 3rd of 9 segments (112, 115, **120**, 134, 136, 146, 148, 152, 155) |
| `docs/atl105_complete_templates.json`, `EMV Financial Transaction Response` template | Segment 120 is 3rd of 10 segments (112, 115, **120**, 131, 134, 136, 146, 148, 152, 155) |

**Resolution:** both template segment lists are in strict ascending segment-number order —
not merely for Segment 120, but for every segment in both lists. This matches the pattern of the
spec's own numeric cross-reference appendices (e.g. the "Alphabetical List of Data Element Names"
tables, which group segments by number), and no other spec section documents genuine
field-by-field wire order for these response messages. This is assessed as a **numeric-sort
extraction artifact**, not evidence of true wire order.

`BR-263-2` — the literal, first-party specification statement — is therefore treated as
authoritative: **Segment 120 is the final segment of Data Section 3** in a Financial Transaction
Response or EMV Financial Transaction Response. `SEG120-R-007` is now hard-enforced when an
explicit segment order is available in test data. This resolution should be revisited if a real
(non-synthetic) message is ever observed with a companion segment following Segment 120.

## TBA Rule Decomposition

```text
BR (applicability, confirmed):
  Segment 120 shall be present only within a Financial Transaction Response
  or EMV Financial Transaction Response message and shall not appear on a
  request.

BR (ordering, confirmed 2026-09-23):
  Segment 120 shall be the final segment of the Data Section 3 companion
  sequence in a Financial Transaction Response or EMV Financial Transaction
  Response.
```

## Current Validator Boundary

`Segment120PayloadValidator` enforces applicability (`SEG120-R-006`) when a
message-type signal is available in test data, treating its absence as
indeterminate rather than a failure. Following the `P-01` resolution, it
also enforces "last segment" ordering (`SEG120-R-007`) when an explicit
segment order is available in test data; a mutation category for reordering
Segment 120 away from last position is included in the Item 5 standard
mutation set.

## Review Checklist

- Is a negative "Segment 120 on a request" test case present, given that none
  currently exists in the AI Solution's approved catalog?
- Is the `P-01` resolution (literal spec text over AI-template ordering)
  documented wherever ordering-sensitive test data is authored, so a future
  contributor does not accidentally "fix" test fixtures to match the
  template's numeric-sort artifact instead?
- Does a negative "Segment 120 not last" test case exist to exercise
  `SEG120-R-007`'s hard enforcement? (Currently: no — see the
  [AI coverage report](coverage/segment-120-ai-coverage-report.md).)
