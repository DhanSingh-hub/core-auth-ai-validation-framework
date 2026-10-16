# Sequence Number Review: Draft Findings

**Status:** Preliminary semantic comparison started. No pair is approved or counted as coverage.

The source-grounded draft chain is [sequence-number-test-chain-package-v2.json](sequence-number-test-chain-package-v2.json). The initial chain sketch is superseded. Preliminary pair findings are in [sequence-number-preliminary-semantic-review-v2.json](sequence-number-preliminary-semantic-review-v2.json), with the scoped Approval Number clause in [sequence-number-review-update-v3.json](sequence-number-review-update-v3.json), case-copy analysis in [sequence-number-review-update-v4.json](sequence-number-review-update-v4.json), and response-code source review in [sequence-number-response-oracle-review-v1.json](sequence-number-response-oracle-review-v1.json). The raw conflict triage is in [sequence-number-conflict-triage-v1.json](sequence-number-conflict-triage-v1.json); current provenance is in [sequence-number-review-manifest-v5.json](sequence-number-review-manifest-v5.json).

| AI BR | Obligation | Preliminary result |
|---|---|---|
| `:3251` | Segment 100 Sequence Number is required and present | Source marks Element 86 `R`; linked cases contain omission variants. Partial overlap with broader Test rules; producer response code is not an independent oracle. |
| `:6098` | Six-digit numeric format and transaction lifetime | Source definition supports the claim. Six AI cases all use `000001`; four are Segment 100 and two Segment 109. No boundary or lifetime case. Segment applicability remains unresolved. |
| `:1937` | Reuse across transactions associated with initial authorization | Source clause aligns. Six AI cases are separate records with the same value, not linked lifecycle assertions. |

## Test Chain

The official package’s `SEG100-R-019` and `SEG100-R-037` scenario/case/data records are placeholders. A separate draft package, [sequence-number-test-chain-package-v2.json](sequence-number-test-chain-package-v2.json), adds four linked scenarios, twelve cases and twelve data designs for presence, format, distinct new requests and Authorization Only Reversal. It reuses authored completion/void/TOR chains by reference rather than duplicating them.

Authored Test fixtures include matching and mismatching lifecycle pairs. Their referenced file hashes verify, but their Test Data readiness remains `REVIEW_REQUIRED`; no test ran. Section 10.1.2.2 supports Approval Number identity only for Authorization Only Reversal (Prompt Code `S`) when Approval Number was present in the original. That scope must not be generalized to completion or ordinary void flows; `SEG100-R-037`'s composite applicability remains open.

## Evidence and Limits

[sequence-number-preliminary-semantic-review-v2.json](sequence-number-preliminary-semantic-review-v2.json) records the BR-level findings. [case-review-v2/sequence-number-case-payload-bindings.jsonl](case-review-v2/sequence-number-case-payload-bindings.jsonl) binds linked AI cases to catalog occurrences, payload routes and sidecars. Across the eight approved/candidate case IDs, requirement/scenario links and request objects, including Sequence Number values, agree. Two pairs differ only in review metadata; six also differ in producer `expected_response` oracle content. Keep those oracle conflicts open; no producer response is independently validated. Seven `:3251` cases omit the Segment 100 field by design; Sequence Number values in other segments do not satisfy the Segment 100 claim.

The source establishes the six-digit range `000001–999999`, but the AI cases observed here mostly repeat `000001`. Appendix G supports code `1` as a general decline value and `8/9/G/J` as Electronic Mail response meanings; it does not select the expected response for these individual cases. The affected cases also lack a constructed response template. Producer response claims remain unvalidated. Hash agreement is artifact identity, not semantic correctness or execution. The official Test baseline remains unchanged, coverage credit is zero, and the external Authorization attachment was not used as an oracle.

Next: resolve the six case-specific response oracles and two review-metadata authority questions, have the Test owner review the draft vectors and lifecycle chains, and confirm the applicable scope of `SEG100-R-037` before adjudicating any pair.
