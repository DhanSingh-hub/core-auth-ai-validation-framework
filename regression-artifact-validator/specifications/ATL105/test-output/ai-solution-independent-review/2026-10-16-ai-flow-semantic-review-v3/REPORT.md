# AI Flow Semantic Review Worklist

This is a hash-bound review queue, not a semantic approval report. All AI BRs remain pending individual obligation review.

| Measure | Count |
|---|---:|
| AI BRs in review register | 6,880 |
| Linked multileg flow cases | 28 |
| Logical flow steps | 67 |
| Declared physical payload copies | 134 |
| Sequence equality observations | 26 |
| Approval bindings blocked on missing actual response | 15 |
| Approval bindings under flow-specific source review | 13 |
| Full semantic reviews completed | 0 |
| Confirmed semantic matches | 0 |

## Evidence Rules

- Per-leg transaction codes, sequence shape, approval shape, path, and SHA-256 are recorded without copying field values.
- Sequence equality is a deterministic observation only; the row remains `NOT_ASSESSED` until its source rule and scenario are reviewed.
- An `expectedResponse` declaration is not an actual response. A response-shaped producer payload is not host evidence without provenance.
- Authorization completion/cancellation approval binding remains blocked without the actual Authorization response and provenance.
- Approval binding for purchase, return, and timeout reversals remains `REVIEW_REQUIRED` until the applicable source-specific rule is adjudicated.
- No BR is promoted to a semantic match by this builder.

## Files

- `all-ai-br-semantic-review-register.csv`: all AI BRs, linked flow cases/legs, and reviewer decision placeholders.
- `flow-case-binding-review.csv`: each flow case and adjacent-leg transaction/sequence/approval dispositions.
- `flow-leg-evidence.csv`: each logical request/response leg with physical copies and hash-bound structural observations.
- `manifest.json`: output hashes and exact frozen input hashes.
