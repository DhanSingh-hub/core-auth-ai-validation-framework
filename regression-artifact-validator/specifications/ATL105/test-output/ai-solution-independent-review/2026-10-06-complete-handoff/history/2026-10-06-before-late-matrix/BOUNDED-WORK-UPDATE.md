# Bounded Semantic Work: Mapping, Controls, Context and Remaining Gates

**Date:** 2026-10-06  
**Population:** Same 100-case first batch and 2,005 expected-code-1 TC candidates  
**Disposition:** `REVIEW_REQUIRED`; independent coverage remains `NOT_CALCULABLE`.

This update supersedes the initial seven-unassessed preservation count. It does not promote any artifact to approved or executable status. Producer files, independent BR catalogs and approval registers were not changed.

## 1. Composite and Mandatory-Absence Checks

Six technical preservation blockers now have evidence:

| Case | Result | What is proven |
|---|---|---|
| TC-002855 | Declared composite value preserved; 52 characters exceed source maximum 51 | The physical `TrackData` exactly agrees with metadata Account Number + `:` + block; the target value matches the composer descriptor. This is not wire-format approval |
| TC-002856 | Declared composite value preserved | Packing agrees exactly. The enum-negative objective is not established: Element 12 has application-dependent formats, not a universal enumeration |
| TC-002950 | Declared omission preserved | Source-required Segment Type is absent at the explicit metadata key |
| TC-002951 | Declared omission preserved | Source-required Segment Length is absent at the explicit metadata key |
| TC-002952 | Declared omission preserved | Source-required Information Byte is absent at the explicit metadata key |
| TC-002953 | Declared omission preserved | Source-required Terminal Identifier is absent at the explicit metadata key |

Absence assessment requires one intended Segment 100 missing-field descriptor, one matching unavailable-field entry, a unique Standard Segment name established by peer metadata, and actual absence of its exact rendered key. Restored fields fail the preservation check. Missing/ambiguous mappings remain unassessed. Section 12.1's required-field rows are source-gated before publication.

`TC-002951` needs an additional isolation caution: ordinary producer outputs also leave Segment Length unrendered as `derived_at_wire_encoding`. Preserving this negative's declared omission is not evidence that it differs observably from a valid serialized control. Its effectiveness remains blocked until wire encoding and an otherwise-valid serialized baseline are available.

Composite assessment requires exact account/colon/block agreement, not substring matching or a guessed field alias. Changed prefixes or mismatched packing remain unassessed. The colon observation is a producer JSON representation; ATL105's physical Track 1/2 delimiters and combined lengths are separate wire/context checks and are not certified here.

Negative preservation now reconciles as **20 declared values preserved + 4 declared omissions preserved + 1 unassessed = 25**. The remaining case is `TC-004858`, orphan follow-on, requiring a processor outcome oracle. All 25 still lack certified negative effectiveness because complete valid controls, isolated intended-rule detection and outcome evidence are not established.

## 2. BR Objectives and Flow Context

Each selected case now records source-field candidates for linked Element 85/86/12 BRs, its objective gate, and both the testcase and scenario BR link sets. These are navigable source/context leads, not confirmed business equivalence.

The 25 sampled flow cases still have no shared BR links. No mappings were fabricated. Among them:

- **12** have one metadata-declared authorization and completion pair with the same six-digit physical Segment 100 Sequence Number.
- **13** have no unique authorization/completion pair for this bounded check.

The source clause is checked in Element 86's bounded definition. Metadata roles and message labels remain producer claims; matching sequences alone do not validate applicability, approval references, amount carryover, reversals, retries, or host behavior. All flow observations are non-certifying context candidates.

## 3. Valid Controls and Paired Mutations

[Bounded control-pair evidence](semantic-bounded-control-pairs.json) publishes identity, six-digit and 51-character-maximum pairs with the source hash. Executed self-tests also cover:

- A source-required key present versus deliberately absent, and refusal when unavailable-field mapping is missing.
- Exact composite packing versus changed account prefix.
- Authorization/completion sequence agreement versus a changed follow-on sequence.
- Unsupported message roots and non-ASCII digits.
- Source-heading code-1 candidates versus unresolved aliases and unlisted review-matrix pairs.

These controls are valid **within their predicate scope only**. They are not valid full ATL105 messages and do not certify end-to-end negative effectiveness. The existing batch still contains wire-derivation fields in all 100 cases, placeholders in 99, unspecified-code methods in all 100, and awaiting-client values in 26. Those possible confounders remain separate from intended-rule observations.

## 4. Code-1 Context and Outcome Evidence

All 2,005 TC candidates were checked for physical output references, payload/metadata joins and exact source-heading context candidates.

| Context disposition | Cases |
|---|---:|
| Exact-heading context candidate | 880 |
| Producer alias/context unresolved | 1,098 |
| No physical-output reference | 27 |
| Total | 2,005 |

The 880 candidates comprise financial 715, electronic-mail 54, proprietary-load 40 and totals 71. These are provisional context groupings, not accepted source-family mappings. Missing TC expected responses block every candidate's end-to-end outcome assertion; no host response was invented.

The bounded Element 83 source confirms `1 Approved—Communications Test` and `Declined—All other transactions`. The current response matrix is itself `SOURCE_DERIVED_REVIEW_REQUIRED` and lists code 1 for financial and Communications Test only. **165** mail/proprietary/totals context candidates therefore have a matrix/source interpretation concern. They are marked `NOT_LISTED_IN_REVIEW_MATRIX_SOURCE_ADJUDICATION_REQUIRED`, not globally invalid, rejected, or accepted. The matrix hash is retained alongside producer and source hashes.

Unrecognized labels are never silently merged. For example, `Financial Transactions` is not converted automatically into an exact `Financial Transaction Request` context. Physical-root agreement verifies the received representation; it does not prove the producer's business classification.

## 5. Expanded Supported Checks and Remaining Gates

Implemented in this bounded assessment: source-fixed Segment 100 identity, Sequence Number ASCII-digit format, four required-field omission checks, exact composite preservation, a 51-character maximum observation, source-scoped authorization/completion sequence observations and code-1 context evidence. Paired tests pass for their supported inputs.

Not completed or certified:

| Gate | Reason / next prerequisite |
|---|---|
| Full TC-to-BR equivalence | Source/context adjudication of complete BR objectives; missing flow BR links remain unresolved |
| Full negative isolation | Valid full-message baseline and applicable intended-rule validator, with unrelated failures reported separately |
| Orphan processor behavior | Authoritative external processor rules and expected outcome |
| Segment compatibility | Supported source-grounded message/segment interpretation and applicability contract; metadata claims alone are insufficient |
| Wire serialization | Actual wire artifact/encoding and trusted length/framing contract; intermediate null derived lengths are not wire evidence |
| Full Java payload-validation campaign | Verified adapter contracts for these producer shapes, followed by applicable validators; no blanket Java pass/fail was inferred |
| Code-1 host outcomes | Source-backed family and expected TC oracle; all TC responses remain missing |

The broader gates are explicit remaining work, not completed validations. This prevents missing or unsupported checks from becoming an implicit PASS.

## Outputs and Verification

- [Updated HTML report](SEMANTIC-FIRST-BATCH-REPORT.html): current preservation count, one remaining blocker, flow/context observations and filterable code-1 context states.
- [Batch summary](semantic-first-batch-summary.json), [CSV](semantic-first-batch-register.csv), and [structured register](semantic-first-batch-register.json).
- [Code-1 queue CSV](expected-code-1-review-register.csv) and [JSON](expected-code-1-review-register.json), with physical/context evidence and hashes where files are available.
- [Bounded control pairs](semantic-bounded-control-pairs.json).

The same producer hashes and deterministic 100-case selection are retained. Controlled tests, actual-data reconciliation and presentation regressions verify this bounded work; no run-wide semantic coverage, human approval or execution certification is claimed.