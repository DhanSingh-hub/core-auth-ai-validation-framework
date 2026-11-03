# TC-to-BR Alignment and Negative-Test Effectiveness: First Batch

**Latest bounded-work update:** [Mapping, controls, context and remaining gates](BOUNDED-WORK-UPDATE.md) supersedes the initial seven-unassessed preservation count below. Current evidence is 20 preserved values, four preserved omissions and one orphan-oracle blocker; outcome and full semantic gates remain unresolved. Initial findings below are retained for context; the linked registers and HTML reflect the latest bounded checks.

**Date:** 2026-10-06  
**Run:** October 5 Run1 supplement; immutable producer input retained  
**Disposition:** `REVIEW_REQUIRED`; no semantic match, SME approval or execution coverage is certified.

## Selection and Evidence

The batch contains 100 deterministic records: the first 25 positive, 25 boundary and 25 flow cases, plus 25 negative cases drawn as the first four in each of six field-mutation classes and the sole composed orphan-follow-on case. This is a debugging/assessment batch, not a random or statistically representative sample. Results cannot be extrapolated as a defect or effectiveness percentage for all 21,123 cases.

The source predicates are bound to ATL105 2026-3 extracted text:

- Section 12.1, Standard Message Data Segment field 1: `Fixed value:  100`.
- Chapter 13, Element 86: `Representation: Fixed length of six digits`.

The generator checks these literal clauses within bounded source sections and retains the source hash. It tests Segment 100 identity and six-ASCII-digit format only. It does not establish the complete Sequence Number value range, sequence allocation, lifecycle correlation, transaction applicability, authorization behavior, wire lengths, or full segment validity. JSON type/representation and explicit metadata paths are part of this bounded interpretation, not a universal producer adapter.

## Recommended Order: Evidence Produced and Remaining Gates

| Stage | First-batch result | Remaining boundary |
|---|---|---|
| 1. Interpretation | 131 physical legs resolve using the metadata's explicit message family, segment-friendly name and field key; payload/metadata TC and scenario IDs reconcile | No generic schema/alias adapter is certified. Missing paths yield unsupported/unassessed results rather than guessed field mappings |
| 2. TC-to-scenario/BR objective | 75/100 cases share BR IDs with their scenario; the 25 sampled flow cases lack shared BR linkage. Register includes both sets of links and linked BR statements/source references | Shared IDs are not equivalent business meaning. Every case remains `NOT_PROVEN_SHARED_IDS_ARE_NOT_BEHAVIOR_EQUIVALENCE`; complete BR objectives need source/context adjudication |
| 3. Negative-test effectiveness | 18/25 declared mutation values survive rendering; seven remain unassessed. Two narrow predicates identify intentional invalid Segment Type inputs | Preserved mutations do not prove the correct rule, isolation, expected host outcome, or detection effectiveness. All 25 remain non-certified |
| 4. Expected outcome | Full scan confirms all 21,123 case expected responses are null/empty. A separate 2,005-case queue joins scenario expectations of code `1` to TCs | Scenario code hints are not TC oracles. Verify family/context before asserting decline or approval; do not manufacture responses |
| 5. Payload expansion | 259 passing and two failing source-predicate observations across the selected payloads | These are individual field observations, not 259 passing tests. Broader segment, compatibility, wire and lifecycle validation remains pending |

All 2,005 code-`1` queue rows remain `NOT_ASSESSED`; their labels include financial, EMV, ECA, totals, mail, loyalty and key-load variants. Do not apply financial-decline semantics universally. Communications Test code `1` has approval semantics under its own source context, but this queue was not normalized to source-defined response families and the new Communications Test Java validator was not applied to unsupported request shapes.

## Negative Findings

| Outcome | Cases | Meaning |
|---|---:|---|
| Declared value preserved in physical output | 18 | Original declared mutation value and exact rendered field agree; no assertion of source-invalidity for untested fields |
| Unambiguous rendered-target mapping unavailable | 2 | Composite Card Discretionary Block Data representation requires an explicit mapping check |
| Mandatory-absence assertion not implemented | 4 | Metadata omission alone is not sufficient to prove correct physical absence and requiredness |
| Processor outcome oracle unavailable | 1 | Orphan-follow-on behavior requires authoritative external rules |

The two observed source failures are intentional negative inputs, not independently identified AI defects:

- `TC-002869`, `enum_invalid`, Segment Type: physical value `000` in the explicitly mapped Standard Segment violates source-fixed `100` and preserves the declared mutation.
- `TC-002970`, `wrong_fixed_value`, Segment Type: physical value `101` violates source-fixed `100` and preserves the declared mutation.

No unexpected failure was found by these two narrow predicates. This does not demonstrate full payload correctness or a zero false-pass rate.

### Unassessed Cases

| Case | Negative class | Target / blocker |
|---|---|---|
| TC-002855 | length_exceeded | Card Discretionary Block Data; composite rendering not yet mapped unambiguously |
| TC-002856 | enum_invalid | Card Discretionary Block Data; composite rendering not yet mapped unambiguously |
| TC-002950 | missing_mandatory | Segment Type; explicit absence/requiredness assessment pending |
| TC-002951 | missing_mandatory | Segment Length; explicit absence/requiredness assessment pending |
| TC-002952 | missing_mandatory | Information Byte; explicit absence/requiredness assessment pending |
| TC-002953 | missing_mandatory | Terminal Identifier; explicit absence/requiredness assessment pending |
| TC-004858 | orphan_followon | No field target; no source-grounded processor outcome oracle |

### Potential Confounders

All 100 selected cases contain `derived_at_wire_encoding` and `spec_unspecified_code` methods, 99 contain `placeholder`, and 26 contain `awaiting_client_value`. These are unresolved-state signals, not automatic business defects. However, without a validated otherwise-correct baseline, unrelated issues could obscure the intended negative failure. Physical preservation is only the first step; effectiveness requires one intended source-supported violation, valid surrounding context, a correct expected outcome, and a validator that detects the intended rule without crediting unrelated errors.

## Next Bounded Work

1. Implement explicit composite/absence mappings for the seven unassessed negatives, using source requiredness and the metadata/rendering contract rather than field-name similarity.
2. For the selected positive, boundary and flow cases, establish objective-level source support for each linked BR; missing flow BR links stay gated rather than receiving fabricated mappings.
3. Establish valid control fixtures and paired mutations. Test the intended rule directly and report unrelated errors separately; do not infer negative effectiveness from any arbitrary failure.
4. Work through the code-`1` queue with evidence-backed family and expected-outcome resolution. The missing TC response stays a blocker for end-to-end assertions.
5. Expand applicable Java payload, compatibility, serialization and lifecycle checks after confirming their supported input contracts. Unsupported inputs are `NOT_ASSESSED`, not an AI semantic failure.

## Deliverables and Reproduction

- [Detailed interactive HTML report](SEMANTIC-FIRST-BATCH-REPORT.html): executive findings, searchable 100-case evidence register, seven negative blockers, paginated 2,005-case outcome-review queue, source quotes and input hashes. Filters and CSV exports never grant semantic acceptance.
- [100-case CSV register](semantic-first-batch-register.csv): intents, both BR-link sets, source-backed observations, rendered-path evidence, mutation status, blockers and confounders.
- [100-case JSON register](semantic-first-batch-register.json): preserved structured evidence and physical payload/metadata hashes.
- [Summary and input hashes](semantic-first-batch-summary.json): selection, source quotes, outcome counts and assurance boundary.
- [2,005-case expected-code-1 queue](expected-code-1-review-register.csv): source metadata and missing TC oracle dispositions; no inferred family or semantic approval.
- [Structured expected-code-1 queue](expected-code-1-review-register.json).

Run `scripts/assess-ai-semantic-batch.py --self-test` for the controlled predicate/mutation checks, then `scripts/assess-ai-semantic-batch.py <review-directory>` for this preserved handoff. The script uses the archive path in the existing intake analysis, case-sensitive JSON, streaming testcase parsing, and extended Windows paths. It changes analysis outputs only. Source-predicate tests include correct and incorrect values, non-ASCII-digit rejection, mutation preservation/change and unsupported roots. No AI input, independent catalog, approval register or Java validator is modified.

Generate the HTML with `scripts/generate-semantic-batch-view.py <review-directory>`. The report embeds all batch and outcome-queue evidence and reuses the existing assessment styling without a web server. `scripts/validate-semantic-batch-view.cjs <review-directory> <jsdom-module-path>` checks case/queue filters, row evidence, blocker links, pagination, CSV contents and non-certification states. Browser checks cover desktop and mobile layout and the seven-blocker filter. This is presentation validation, not an additional semantic verdict.