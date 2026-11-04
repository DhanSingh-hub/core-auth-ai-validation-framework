# Business coverage evidence contract, version 1

This contract adds four separate measures to the existing artifact, source and
semantic controls. It does not replace the canonical artifact contract,
`SemanticEvidenceValidator`, native delivery gates, or a business-approval process.
The executable contract validator is [business_coverage.py](../scripts/business_coverage.py).
It uses the existing strict JSON parser and contained reference resolver.
The [companion pilot builder](../scripts/build_companion_coverage_pilot.py)
generates a complete, runnable example with synthetic logical fixtures.

## Input and ownership

An immutable input directory contains `assessment.json` and referenced evidence.
Sources are resolved under the module root by default; `--trusted-root` can select
another controlled source directory. Other evidence is resolved inside the input
directory. Every reference is `{"path": "relative/path", "sha256": "<64 lowercase hex>"}`.
Absolute paths, traversal, symlinks, missing/empty files and hash mismatches fail.
IDs use letters, digits, dot, underscore, colon and hyphen, starting with a
letter/digit; IDs cannot contain slash-delimited ambiguous criterion identities.

Top-level fields:

| Field | Required content |
|---|---|
| `schemaVersion` | Integer `1` |
| `scope` | `id`, `specification`, `version`, `description`, `kind` (`BOUNDED_PILOT` or `BUSINESS_SCOPE`), `outOfScope` string array, optional `review` |
| `sources` | Nonempty array: `id`, `specification`, `version`, file `path`/`sha256`, nonempty `anchors` |
| `requirements` | Nonempty independent requirement inventory, including unresolved and excluded records |
| `scenarios` | Independent or delivered scenarios with explicit requirement links |
| `testCases` | Cases with explicit criteria, intent, steps, expected result and all TD legs |
| `testData` | File-bound data descriptors |
| `semanticPolicy` | Nonempty `TC` and `TD` arrays of required semantic check IDs |
| `semanticAssessments` | Evidence references, at most one per TC/TD ID; omitted artifacts remain unassessed |
| `flows` | Explicit business-flow population, including unresolved/excluded flows |
| `runs` | Selected execution evidence, at most one run per flow; an empty array is valid |

Each source anchor has `id`, integer PDF `page`, and a short exact `excerpt`.
The source text must contain `--- PAGE n ---` markers. Excerpts are whitespace-
normalized and checked only on the stated page. Source and scope releases must
match; supporting documents with different release identities need a separately
reviewed applicability contract rather than silently mixing releases.
Quote location is not proof that the requirement interpretation is approved.
Do not copy restricted full source documents into an evidence bundle.

## Requirement and test-design records

```json
{
  "id": "EMV-130",
  "statement": "An EMV financial request includes Segment 130.",
  "sourceRefs": [{"sourceId": "ATL105-2026-4", "anchorId": "EMV-130"}],
  "applicability": "APPLICABLE",
  "review": null,
  "criteria": [
    {"id": "positive", "variation": "POSITIVE", "description": "Required companion present."},
    {"id": "missing", "variation": "NEGATIVE", "description": "Required companion absent."}
  ]
}
```

`applicability` is `APPLICABLE`, `UNRESOLVED` or `EXCLUDED`. All dispositions,
including exclusions, require recorded review before establishing the denominator.
Every criterion needs a description and a variation: `POSITIVE`, `NEGATIVE`,
`BOUNDARY`, `CONDITIONAL` or `LIFECYCLE`. A variation label is not sufficient:
create distinct criterion IDs for each necessary boundary, conditional branch,
negative rule, or lifecycle role. The reviewer determines adequate criteria from
source, including justified omissions; the engine does not infer a complete
business universe or appropriate variations from test names.

A scenario has `id`, `intent`, nonempty `requirementIds`, optional `review`.
A TC has `id`, `scenarioId`, `intent`, nonempty string-array `steps`,
`expectedOutcome`, nonempty `testDataIds` and nonempty `coverage`:

```json
{
  "requirementId": "EMV-130",
  "criterionId": "missing",
  "expected": "FAIL"
}
```

A TD has `id` and `file` (a hash-bound reference).
Every TD must have a TC owner. Every TC coverage link must agree with its
scenario's requirements. Every declared data leg must exist; primary-only
evidence cannot close a multileg case.

## Independent semantic evidence

Each assessment entry has `id` equal to a TC/TD ID, an `evidence` reference and
optional `review`. The evidence file contains:

```json
{
  "artifactId": "TD-example",
  "artifactSha256": "<fingerprint of exact TC or TD descriptor>",
  "authority": "INDEPENDENT_TEST_SOLUTION",
  "checks": [
    {"id": "ruleChecks", "status": "PASS", "detail": "Observed predicate matches intended negative result."}
  ],
  "ruleChecks": [
    {
      "requirementId": "EMV-130", "criterionId": "missing",
      "expected": "FAIL", "observed": "FAIL", "detail": "Missing required companion detected."
    }
  ]
}
```

Check IDs must belong to `semanticPolicy` for that artifact kind.
Check statuses and observed predicate results are `PASS`, `FAIL`,
`REVIEW_REQUIRED`, or `NOT_ASSESSED`. Expected predicate results are `PASS` or
`FAIL`, and must agree with the TC's coverage declaration.

Negative TD is intentionally invalid under its target rule: expected `FAIL` plus
observed `FAIL` is successful negative-test evidence, not automatically a TD
defect. Overall semantic checks must also establish that the negative case is
appropriately isolated, applicable and consistent, according to the reviewed
policy. The engine does not create that judgment from a matching predicate.

Complete assessment requires resolved results for **all** policy check IDs and
all linked criterion IDs. Partial checks are counted separately. Acceptance
additionally requires a bound SME/TBA review. Producer self-PASS, absent
assessment, unknown result, or a subset of passing field checks is insufficient.

Use existing independent validators/reviewers to produce these records, retaining
their actual input hashes, limitations and exact artifact/criterion associations.
Do not relabel an offline validator result as host evidence or fill missing
semantic judgments with PASS. This version consumes the normalized contract; it
does not automatically convert legacy reports or native AI delivery formats.

## Review subjects and provenance

Optional review fields are hash-bound references to external decision records:

```json
{
  "subjectSha256": "<fingerprint of subject described below>",
  "decision": "APPROVED",
  "reviewer": "<authorized reviewer identity>",
  "role": "SME",
  "rationale": "<source-backed decision and any scope limitations>"
}
```

Allowed decisions: `APPROVED`, `REVIEW_REQUIRED`, `REJECTED`. Business/semantic
records accept roles `SME` or `TBA`; execution authorization/attestation accepts
`EXECUTION_OWNER`. Do not manufacture such records. The engine verifies their
binding and recorded role, **not the signer's identity or organizational
authority**. Use an approved review/access process outside this local tool.

`fingerprint(value)` is SHA-256 of UTF-8 JSON with sorted keys, compact separators,
ASCII escapes and no non-finite numbers. Use the exported helper rather than
reimplementing serialization. `subject(record)` removes only its `review` field.
`context = review_context(document)` binds scope, source hashes and anchors,
requirements/criteria without reviews, semantic policy, and the complete
scenario/TC/TD/flow inventories. Reordering inventories changes the context.

| Decision | Exact fingerprint subject |
|---|---|
| Scope/universe completeness | `context` |
| Requirement applicability and criteria | `{"context": context, "requirement": subject(requirement)}` |
| Scenario mapping | `{"context": context, "scenario": subject(scenario)}` |
| TC/TD semantic acceptance | `{"context": context, "artifact": artifact, "evidence": assessment.evidence}` |
| Flow applicability/design | `{"context": context, "flow": subject(flow)}` |
| Environment authorization | `{"context": context, "environment": run.environment, "flowId": flow.id}` |
| Execution attestation | `{"context": context, "flow": subject(flow), "run": run without attestation}` |

A new release, altered requirement, changed payload hash, criterion, flow
population or artifact descriptor invalidates existing bindings. Reassess
affected evidence rather than regenerating approvals automatically.

## Execution evidence and readiness

A flow has `id`, `applicability`, optional `review`, nonempty `steps` and nonempty
`prerequisites` (unique string IDs). A step has `id`, `caseId`, and a nonempty
`assertions` list of expected response predicates:
`{"pointer": "/responseCode", "expected": "00"}`.
These are reviewed flow expectations, not values chosen after seeing a response.
Expected outcomes can legitimately include declines in negative flows.

For readiness before a run, a flow may declare `environmentId` and a `readiness`
object mapping prerequisite IDs to bound evidence files. Records identify the
same prerequisite/environment and status `READY`, `NOT_READY` or `UNKNOWN`.
Missing, unknown and not-ready prerequisites remain blockers. All ready without
a run is `NOT_RUN`, not executed. These observations are not environment
authorization; a run still requires its own bound authorization and prerequisites.

A selected run has:

- `id`, `flowId`, an `environment` file reference, `authorization` review and
  `attestation` review.
- Environment content: `id`, `mode` (`HOST` or `AUTHORIZED_SIMULATOR`). Offline
  logical fixtures are not E2E execution.
- `prerequisites`: object mapping every required prerequisite ID to evidence.
  Each evidence file contains matching `id`, `environmentId` and `status: READY`.
- `steps`: nonempty array. Each step has its declared `id`, `caseSha256` binding
  the exact case descriptor, `dataHashes` binding **all** TD IDs to their file
  digests, nonempty `request`/`response` file references, and `assertions` identical
  to the flow's predeclared expectations.

Response files are JSON; pointers are strict JSON pointers. Values are compared
with their types, and missing targets fail intake rather than silently passing.
The attestation binds all requests/responses and the case/data associations; the
tool cannot independently prove that a request reached the claimed environment.
Full runtime regeneration, cryptographic authenticity and trusted capture remain
the responsibility of authorized infrastructure and review.

No run plus required prerequisites is `BLOCKED`. An authorized run covering only
some steps is `INCOMPLETE`, counted as attempted but not fully executed. A
completed run is `PASS` only when all expected response assertions match;
otherwise `FAIL`. An explicit unknown/excluded flow cannot have a run.
Retries must be retained separately; supply one explicitly selected run per flow,
not an automatically cherry-picked historical pass.

## Metrics and exit behavior

| Measure | Numerator / denominator | Gate |
|---|---|---|
| Scenario completeness | Applicable requirements with reviewed scenario mappings / applicable requirements | Reviewed complete universe and reviewed disposition for every requirement |
| Rule adequacy | Applicable rules meeting every criterion through reviewed scenario mappings and accepted TC/all-TD evidence / applicable rules | Same denominator gate |
| TC and TD semantics, separately | Accepted artifacts / fully assessed artifacts | Same denominator gate; expose total population, failed and not-fully-assessed counts |
| E2E pass rate | Passed flows / attempted flows | Established baseline and reviewed applicability/design of every flow |
| E2E execution coverage | Completely executed flows, including failed completed flows / applicable flows | Same flow gate |

Unresolved applicability blocks official percentages. Zero denominators yield
`null`, never a fabricated 0% or 100%. Provisional scenario/criterion percentages
and bounded-check pass rates are separate, explicitly **not business approval**.
When reporting semantics, always include assessment completeness: a high pass
rate on a small assessed subset does not certify the unassessed population.
Execution exposes blocked, not-run, incomplete, failed and unresolved counts.

Outputs: `assessment-results.json`, `REPORT.md`, `requirements.csv`,
`semantics.csv`, `flows.csv`. No payload or secrets are copied into reports.
Outputs must be new and outside the input bundle. Exit `0` means assessment
generation succeeded, **not** release approval. Invalid/stale evidence exits `2`
and no success-shaped assessment is published. Review gaps remain explicit
statuses, not invented validator failures or claims of certification.
