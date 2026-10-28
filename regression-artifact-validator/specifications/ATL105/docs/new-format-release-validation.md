# New-format release validation and complete-validation readiness

## Implemented scope

The Test Solution now has an explicit **V2 multi-chain review adapter** for
ATL105 2026-3 and 2026-4. It implements source-bound structured claims, scoped
field predicates, local token-segment byte comparison and logical lifecycle
checks in one run. It preserves the previous V1 Element 86 sample.

**Complete-validation readiness has started, but remains BLOCKED.** This
implementation does not promote the active 2026-3 baseline, certify all segments,
update model weights or approve source interpretations. It is not a general
natural-language entailment engine or a host runner.

| Release | Independent profiles | Concrete draft chains |
| --- | --- | --- |
| 2026-3 | Sequence, original online-refund indicator, token request tables | 3 BR / 3 TS / 23 TC / 23 TD |
| 2026-4 | Sequence; retired refund source; token request; token response; original-information carry-forward; AF lifecycle; IA Table82; device022 extensions; Discover India056 subtables01-07; Table063 terminal number | 10 BR / 10 TS / 80 TC / 80 TD |

These are **supplemental source-derived drafts**, not approved canonical BR
counts or the whole-spec denominator. They include expected failures and
deliberately unassessed cases. The generated aggregate therefore remains
NOT_ASSESSED even when every decisive expected result agrees.

## Input contract

Root:

```json
{
  "schemaVersion": 2,
  "format": "BA_BUSINESS_CONTEXT_REVIEW_V2",
  "manifest": {
    "packageId": "AI-LOCAL-PACKAGE-ID",
    "producer": "AI_SOLUTION",
    "specification": "ATL105",
    "specificationVersion": "2026-4",
    "strictExecutionContract": false
  },
  "businessRequirements": [],
  "testScenarios": [],
  "testCases": [],
  "testData": []
}
```

Each array must be nonempty in a submitted package. The empty arrays above
illustrate the root shape only, not a valid executable sample. See the generated
[2026-4 concrete package](../test-output/test-solution-independent-review/new-format-release-readiness-v2/2026-4-independent-br-ts-tc-td.json)
and [2026-3 package](../test-output/test-solution-independent-review/new-format-release-readiness-v2/2026-3-independent-br-ts-tc-td.json).

Every record has a globally unique nonblank `id`, `status`,
`businessContext` and nonempty `sourceAnchors`. IDs can be producer-local.
Producer status/approval/confidence fields are retained as claims, never
independent sign-off.

Context includes platform, specification, version, domain, funding/program,
network or generic group, message family, direction, transaction category,
flow/lifecycle role, presence, entry mode and channel. Each package has one
release, but can contain many different contexts. Context must agree along
each explicit edge; related multi-leg observations reside in the lifecycle
observation rather than being guessed from unrelated cases.

The seven named review groups and generic Debit/EBT remain supported. Explicit
NYCE/PULSE/ACCEL/Shazam/Interlink/Maestro/STAR contexts are retained separately,
without assuming STAR equals STAR Signature Debit or asserting token eligibility
from a name. Generic categories have `network: null`; Credit is not a network.
Operational contexts can be retained using explicit not-applicable identities,
but the current payment predicates do not assess those domains.

| Kind | Required fields beyond common identity/context/source |
| --- | --- |
| BR | `statement` and structured `claim` |
| TS | `requirementIds`, business `intent`, nonempty textual `preconditions` |
| TC | `scenarioIds`, nonempty textual `steps`, exactly one assertion for every reached BR |
| TD | `testCaseIds`, `payload.observations` map containing every referenced observation |

An assertion has `requirementId`, `rule`, `observationId`, `expectedResult`
(`PASS`, `FAIL`, `NOT_ASSESSED`) and optional `expectedWireResult`.
Multiple BRs per case and multiple cases per TD are supported. All graph edges,
anchor unions and per-edge contexts are checked; duplicates, orphans, missing
assertions and source-release mixing fail structurally.

Each observation is an explicitly scoped object, not an alternate ATL105 wire
JSON schema. For example:

```json
{
  "observations": {
    "actual": {
      "segmentType": "154",
      "tables": [{"id": "002", "length": "001", "value": "T"}]
    }
  }
}
```

Unknown metadata/fields/observation IDs are preserved. Unreferenced observation
IDs are listed in the report, not silently assessed or discarded. No adapter
for an unspecified Excel/diagram/other AI export is implied by the V2 contract;
new producer shapes need explicit versioned mappings.

## Independent source and release selection

The validator owns the profile catalog, pinned source hashes, page evidence,
structured claims and predicates. An AI claim cannot replace the oracle.
For each rule the validator verifies the trusted-root source path, whole-file
SHA-256, page locators and source phrases, retaining exact page quotes/line
intervals in the report. Changed source bytes fail closed pending review.

- Exact supported source anchor + context + structured claim: the bounded
  semantic gate can PASS.
- A different claim at the exact implemented anchor: FAIL.
- Unknown anchor/profile/context: NOT_ASSESSED.
- Mixed release in manifest/context/anchors: structural error.
- A copied active Appendix X claim under 2026-4: semantic FAIL.
- The correctly declared 2026-4 Reserved source: behavior remains NOT_ASSESSED.
  Retirement does not supply a replacement refund rule or host response code.

Literal source/claim agreement does not prove the BR prose is equivalent,
the claim interpretation has authorized approval, or every operative clause is
represented. The source is the existing extracted text; PDF table-cell/diagram
equivalence has not been visually certified by this adapter.

## Predicate, wire and lifecycle behavior

- Sequence: six ASCII digits, inclusive 000001-999999, in the supported financial
  request family. Not lifecycle uniqueness or card/network eligibility.
- Baseline refund: participating merchant and online-approval intent determine
  the original-return indicator shape/presence. Follow-up/Visa ordering remain
  outside this profile.
- Token153: release-specific table widths/representations, numeric target token,
  target YYMM month, Y/N input indicator, new Table007 Y and 999-byte data cap.
  Baseline token character semantics remain unspecified rather than invented.
- Token154: distinct response table meanings, including T/P indicators. No
  request-expiry semantics are applied to response Table002. Actual retry
  applicability/eligibility remains outside the representation profile.
- Token carry-forward: when both service entitlements are observed true,
  relevant follow-ups preserve the explicitly supplied original information,
  even if a response returns updated PAN/expiry. Completeness and authenticity
  of that observed information are not certified.
- AF: ordered Original/Incremental/Reversal/Completion, approved predecessor,
  matching observed DE39, closed-flow checks and terminal-scoped open-identity
  observations. Missing history is not filled from producer metadata. AF
  identity is not mapped into Table82 N4; DE39 is not guessed to be Element5.
- Table82: indicator082, printed tableID82, length004 and N4 terminal number.
  Discover absence fails; other named-network recommendation-only absence
  remains unassessed, not a universal hard rejection.
- Device022: target values35/36/37; future38-99 are explicitly not rejected by
  this extension. Older values are outside this bounded profile.
- Discover India056: issuer-country, network and recurring/installment context;
  each subtable's initial/subsequent scope, fixed width and explicit value
  domains. Missing recommended data does not become a mandatory rejection.
  Shape does not authenticate cryptograms, unique IDs or transaction currency.
- Table063: scoped Visa/MC/Amex lodging/IA/EV terminal-number presence and N4
  representation. Source "should" continuity mismatch remains review-required,
  not an invented host decline.

For target token segments, optional `wireHex` and `segmentLength` enable local
single-segment byte comparison: request153 has the source-described header FS;
response154 is no-FS; both include actual TLV bytes, N3 declared length and
measured byte agreement. Wrong hex, extra bytes, separators, values or declared
lengths fail. Baseline153 separator placement remains unassessed.

This locally reproduced byte grammar is **not** independently captured converter
execution, full-message transport/TPDU/section validation or host execution.
Unknown framing interpretation still needs the existing physical-evidence
promotion gates; never use a local segment PASS as a wire certification.

Expected negative predicate/wire failures are compared to the TC's independent
expectation separately. A correctly detected negative is not automatically an
AI defect. Unsupported evidence never becomes PASS simply because the AI
expected NOT_ASSESSED. Full valid-control/isolation/negative-effectiveness
certification remains a separate gate.

## Commands and persistent outputs

From the module root:

```powershell
.\framework.ps1 -Task new-format-readiness-build
.\framework.ps1 -Task new-format-readiness-check
.\framework.ps1 -Task new-format-readiness-test

python.exe .\scripts\new-format-validation.py `
  --input 'C:\review\ai-v2.json' `
  --output 'C:\review\new-format-assessment-001'
```

The output directory must not exist. External input is preserved byte-for-byte,
including BOM, as `input-snapshot.json`. Input is rechecked before publication.
Reports, normalized input and snapshot hashes are bound by a manifest. Existing
V1 callers can submit V2 through `new-format-sample.py --input ...` as well.

CLI exits:

- `0`: every requested supported decisive assertion agrees; **not complete
  readiness, coverage or certification**.
- `1`: malformed structure/evidence, semantic contradiction, mismatched
  expectation or unexpected wire failure.
- `2`: unsupported/unassessed evidence, usage error or output collision.

The generated
[readiness summary](../test-output/test-solution-independent-review/new-format-release-readiness-v2/readiness-summary.json)
links the release runs. The
[crosswalk](../test-output/test-solution-independent-review/new-format-release-readiness-v2/release-crosswalk.json)
accounts only for these implemented supplemental profiles, not every existing
repository artefact. The
[draft lessons](../test-output/test-solution-independent-review/new-format-release-readiness-v2/draft-training-lessons.json)
retain source evidence and remain unapproved. `--check` regenerates in memory
and compares bytes; it does not overwrite evidence.

## Complete-validation readiness gate

Every run emits `completeValidationReady: false`, `status: BLOCKED` and concrete
remaining gates. This prevents a scoped format expansion from being presented as
full readiness. The gate can be redesigned for promotion only when there is real
evidence for the broader scope, not by changing a flag in the AI package.

Remaining work includes:

1. Complete source-to-obligation normalization and actual code/case
   reconciliation across the whole document. The existing605 catalog and
   supplement counts are not a whole-spec denominator.
2. Extend source-backed applicability and physical profiles beyond the bounded
   examples, including presence matrices, authenticated history and service
   entitlements. Preserve source conflicts and external boundaries.
3. Resolve AF identity/approval wire mappings; complete full-message independent
   conversion/capture, isolated-negative effectiveness and host-oracle evidence.
4. Extend the Java canonical semantic runner deliberately; its current typed
   claim gate remains2026-3-only. V2 does not bypass execution readiness.
5. Run a frozen, held-out **real new-format AI delivery** through the supported
   contract; no supplied AI delivery has been represented as validated here.
6. Obtain authorized rule/coverage/training approvals and close mandatory gates
   before changing active baseline/training status.

The existing [migration review](../reports/2026-10-26-atl105-2026-4-restructuring-review/ATL105-2026-4-REVIEW-AND-RESTRUCTURING-PLAN.md)
remains historical plan evidence. Its statements that migration had not started
describe that earlier review, not this newly implemented scoped supplement.

## Implementation verification

On 2026-10-26, the combined Python regression gate passed **83 tests** across
the preserved V1 sample, V2 release validation, all-network review and Mastercard
reference review. It covers source hash refusal, mixed-release refusal, graph/
claim contradictions, all nine navigation groups, fixed byte vectors, negative
expectation handling, lifecycle blockers, input/BOM preservation, both CLI paths,
output manifests and unchanged baseline summary counts. The unit-test discovery
tool did not discover these tests, so they were executed through the existing
standard-library unittest runner:

```powershell
Set-Location .\regression-artifact-validator\scripts
python.exe -m unittest test_new_format_sample test_new_format_validation test_all_network_requirements_review test_mastercard_reference_review -q
```

No Java code changed and no fresh Java full-suite or host run is claimed.
The generated source-bound profile checks run locally; unavailable/unsupported
gates remain unassessed regardless of regression success.
