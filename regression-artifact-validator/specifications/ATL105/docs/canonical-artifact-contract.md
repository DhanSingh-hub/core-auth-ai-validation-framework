# Canonical Artifact Contract

## Semantic Evidence Gate Sidecar (Version 1)

`SemanticEvidenceValidator` and `SemanticEvidenceBatch` implement technical
gates for complete declared BR claims, isolated mutations, compatibility,
captured wire bytes and request-bound outcome oracles. The sidecar is Test
Solution-owned evidence, never a producer's self-verdict or a replacement for
the independent-producer rules below. Missing/unsupported evidence returns
`NOT_ASSESSED`; demonstrated contradictions return `FAIL`.

The optional sidecar is an object with `schemaVersion: 1` and `cases`, an array
of `{caseId, observation, evidence}` objects. The runner rejects duplicate JSON
keys, duplicate IDs, unknown versions and IDs outside the selected register.
Inputs are hashed, original registers are unchanged, and the CLI writes only a
new report file. Use a new report path on each replay.

Every gate's `source` contains `file`, `sha256`, `startLine`, `endLine`, and an
exact `quote`. Files must remain within the explicitly supplied trusted-source
root, including after symbolic-link resolution. Hashes and line-bounded quotes
are verified, not merely recorded. The root must contain independently vetted
source/evidence, not an AI intake directory. A hash/quote establishes provenance;
it does not prove that a human interpretation of the quoted prose is correct.

| Gate | Observation | Independent evidence | Technical scope |
|---|---|---|---|
| `brEquivalence` | `brClaims` | `brEquivalence.independentClaims`, `source` | Each claim object requires `claimsComplete: true`, complete context (`specification`, `version`, `messageFamily`, `transactionType`, `cardType`, `lifecycleRole`) and nonempty unique `atoms`. Context must be ATL105/2026-3. Atoms contain `subject`, supported `operator`, `modality`, `units`, typed `value`, `conditions` object, and `exceptions` array. Exact structured equality ignores object-key/atom order, not context, conditions, exceptions, modality or values. IDs and prose similarity are not evidence. |
| `negativeIsolation` | Actual `controlPayload`, `mutantPayload` | `negativeIsolation.source`, `targetPointer`, `targetRule`, nonempty `controlScope` array of `{pointer, rule}` | Exactly one JSON-pointer change; all declared control predicates pass; mutant fails the target and no unrelated declared predicate. Supported predicates: `SEG100_TYPE`, `SEQUENCE_SIX_DIGITS`, `REQUIRED`, `BLOCK_MAX_51`. No universal Element 12 enum is invented. |
| `negativeEffectiveness` | Above plus `controlCanonicalPayload`, `canonicalPayload`, `controlRequest`, `request`, `controlResponse`, `response`, `controlWireHex`, `wireHex` | `negativeIsolation.fullControl` with `scopeComplete`, `source`, `compatibility`, `wireValidation`, `hostOutcome`; mutant contracts at evidence root | Requires matching physical request identities, complete declared valid positive control, full-message wire for both legs, positive ACCEPT and negative REJECT authoritative oracles, and no unrelated mutant compatibility/wire failure. Scoped preservation alone cannot pass this gate. |
| `compatibility` | `canonicalPayload.request` with Section 1/count, `dataSection2.standardSegment`, `dataSection3` array | `compatibility.source`, `messageCategory: STANDARD_FINANCIAL_REQUEST`, explicit `requiredSegments`, `allowedSegments`, optional `expectedOrder` | Source-profile companion allowlist/requirements, Segment 100 cardinality, physical count including Section 3, duplicate and declared-order checks. Producer `testControls` cannot override the independent profile. Other message categories remain unassessed. |
| `wireValidation` | Actual `wireHex` | `wireValidation.source`, explicit scope and framing manifest | `SEGMENT_100` checks actual ASCII/1C bytes, resolved 3-digit length including separators and ordered values. This is bounded, not full-message validation. `FULL_MESSAGE` covers every captured byte against the independent manifest: UINT16 network-order transport body length, five-byte TPDU, nine Section 1 elements (ATL105 first/count last), segment identities/length widths/resolved byte lengths, order, fields and separators. |
| `hostOutcome` | Actual `request`, nonempty observed `response` | `hostOutcome.source`, `expectedResponse`, canonical `requestSha256`, `responseSha256`, `responseFamily`, `oracleKind`, `environment`, `stateFixtureSha256` | Exact response comparison tied to this request/response. Allowed oracle kinds: `SOURCE_DEFINED_DETERMINISTIC`, `INDEPENDENT_HOST_REFERENCE`. Family-membership conflicts remain unassessed for source adjudication, never automatic acceptance. |

BR operators are `required`, `fixed`, `maxLength`, `range`, `enum`, `equals`,
and `prohibited`; modalities are `must`, `must-not`, `should`, `may`. Absence of
conditions/exceptions must be represented explicitly as `{}`/`[]`. The claim
producer is responsible for complete source interpretation; structured equality
does not independently establish prose entailment or full rule-catalog coverage.

Full wire contracts use `encoding: US-ASCII`, `fieldSeparatorHex: 1c`,
`transportLengthConvention: UINT16_BE_BODY_INCLUDING_TPDU`, `tpduHex`,
`section1Fields` (nine ordered strings), and `segments` (each has `fields`,
including explicit segment type and resolved length). Bounded Segment 100
contracts instead use `orderedFields`. Calculation placeholders, other encodings
and unsupported transport layouts do not become valid no-op results.

Canonical request/response hashes use SHA-256 of compact UTF-8 JSON with object
keys recursively sorted; array order remains significant. The state-fixture hash
is required oracle metadata, not a substitute for storing/reviewing that fixture.
An offline response comparison does not itself establish that a processor was
contacted, that oracle authority is genuine, or that host-state replay occurred.
Keep those provenance/approval obligations with the Test Validation Team.

From the module, run after compiling:

```text
java -cp <module-classes-and-dependencies> com.coreauth.validator.canonical.SemanticEvidenceBatch <register.json> <new-report.json> <trusted-source-root> [sidecar.json]
```

Reports always retain `executionCertified: false` and do not grant semantic/SME
approval. `allTechnicalGatesPassed` is an evidence-check result, not an approved
crosswalk, a semantic coverage percentage or execution certification. SME items,
including unresolved source interpretations and response-family conflicts, stay
`REVIEW_REQUIRED` until independently adjudicated.

Full negative-effectiveness checking also forces `Segment100PayloadValidator`
on the actual positive control, regardless of producer opt-in flags. Its current
core-field scope is the single-Segment-100 financial profile. Controls with
companion segments remain unassessed here because the existing core validator's
count model does not cover Section 3; compatibility/framing checks do not
substitute for those companion semantics. The full manifest is an independent
reference, not a universal ATL105 serializer or proof of every segment's field
rules. Report this support boundary rather than treating unsupported profiles
as fully validated controls.

The validation package uses producer-local IDs plus shared `sourceAnchors`. IDs may differ between the AI solution and the Test Validation solution; source anchors provide the stable semantic identity.

```json
{
  "manifest": {
    "packageId": "ATL105-SEG100-001",
    "specification": "ATL105",
    "specificationVersion": "2026",
    "artifactContractVersion": "2",
    "strictExecutionContract": true
  },
  "businessRequirements": [
    {
      "id": "BR-AI-017",
      "title": "ATL105 message identifier",
      "category": "core-structure",
      "applicability": "all-financial-requests",
      "priority": "high",
      "executionStatus": "EXECUTION_READY",
      "sourceAnchors": [
        {
          "specification": "ATL105",
          "version": "2026",
          "section": "1",
          "element": "55",
          "rule": "message-format-identifier"
        }
      ]
    }
  ],
  "requirementCrosswalk": [
    {
      "testRequirementId": "BR-SEG100-BASE-001",
      "aiRequirementIds": ["BR-AI-017"],
      "matchStatus": "CONFIRMED",
      "matchReason": "Shared ATL105 source anchor and equivalent rule",
      "reviewOwner": null
    },
    {
      "testRequirementId": "BR-SEG100-TX-0",
      "aiRequirementIds": ["BR-AI-042"],
      "matchStatus": "REVIEW_REQUIRED",
      "matchReason": "Potential transaction-context match; SME decision deferred",
      "reviewOwner": "SME-SEG100"
    }
  ],
  "testScenarios": [
    {
      "id": "SCN-TV-009",
      "requirementIds": ["BR-AI-017"],
      "sourceAnchors": [{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}]
    }
  ],
  "testCases": [
    {
      "id": "TC-OTHER-42",
      "scenarioIds": ["SCN-TV-009"],
      "expectedOutcome": "PASS",
      "sourceAnchors": [{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}]
    }
  ],
  "testData": [
    {
      "id": "TD-LOCAL-7",
      "testCaseIds": ["TC-OTHER-42"],
      "expectedValidation": "PASS",
      "fileName": "TD-LOCAL-7.json",
      "readiness": "EXECUTABLE",
      "sourceAnchors": [{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}],
      "payload": {"request": {}},
      "response": {"financialResponse": {"responseCode": "00"}}
    }
  ]
}
```

The validator rejects missing or duplicate IDs, unresolved links, missing source anchors, orphaned artifacts, anchor mismatches, missing expected outcomes, and test data without a payload. `SourceAnchor.canonicalKey()` compares values case-insensitively and trims whitespace, so independently generated terminology can still converge on the same specification identity.

## Independent Producer and Matching Rules

The AI Solution and Test Solution are independent producers. They may use different local IDs, descriptions, generation logic, artifact counts, and internal workflows. Neither producer may copy requirements or test artifacts from the other. The canonical contract is a comparison boundary, not a shared generation source.

The Test Solution owns comparison and coverage decisions. AI output is preserved unchanged, resolved through an intake adapter, and compared with the independently generated Test Solution package by canonical source anchors and validated business meaning.

The only valid confirmed match is an evidence-supported business-equivalent match. Shared words, shared element numbers, text similarity, or AI confidence are insufficient. They may create a review candidate only. Unresolved, partial, conflicting, or heuristic matches must remain `REVIEW_REQUIRED` and must not count as confirmed coverage.

## Implementation Plan

1. **Freeze the contract:** require `manifest`, producer-local IDs, artifact type, lifecycle status, and complete `sourceAnchors` for BR, TS, TC, and TD artifacts.
2. **Build producer adapters:** normalize AI and Test Solution formats into the canonical Java model without changing the original files.
3. **Validate each package independently:** run schema, anchor, graph, readiness, negative-path, and mutation checks before comparison.
4. **Implement deterministic matching:** compare canonical anchor keys first, then validate business context, applicability, transaction or lifecycle conditions, and rule equivalence.
5. **Generate the crosswalk:** retain AI IDs, Test Solution IDs, anchor key, disposition, reason, evidence, and review owner for every comparison record.
6. **Separate coverage metrics:** report total, eligible, anchorable, confirmed, review-required, AI-only, and Test-only populations with explicit denominators.
7. **Create the review queue:** route unresolved or conflicting records to the appropriate SME; never auto-add AI requirements to the Test Solution.
8. **Certify the comparison engine:** use known matches, known non-matches, conflicting anchors, missing anchors, duplicate anchors, and mutation cases as regression tests.
9. **Make Java authoritative:** use the anchor-based Java comparison as the coverage authority; use PowerShell and HTML only to transform and present validated results.
10. **Repeat per segment:** apply the same process to every ATL105 segment and retain segment-specific rules only as controlled addenda to the common contract.

Requirement crosswalk entries are optional for legacy packages, but when present they must use `CONFIRMED`, `REVIEW_REQUIRED`, or `MISSING`. Confirmed entries must reference existing AI requirement IDs. `REVIEW_REQUIRED` entries must include a `reviewOwner`; unresolved SME decisions must remain review-required and must not be promoted to confirmed coverage.

When `manifest.strictExecutionContract` is `true`, the validator also requires:

- BR, TS, TC, and TD statuses from `DRAFT`, `REVIEW_REQUIRED`, `APPROVED_FOR_REVIEW`, `EXECUTION_READY`, `BLOCKED`, or `SCHEDULED`.
- One `fileName` and readiness value (`EXECUTABLE`, `EXTERNAL_FIXTURE_REQUIRED`, or `REVIEW_REQUIRED`) for every test-data artifact.
- A `testDataFile` on every test case.
- Object `request` and `response` envelopes for every test-data artifact.
- One common ATL105 source anchor, including the same specification version, through the complete `BR -> TS -> TC -> TD` chain.
- No `EXECUTION_READY` or `EXECUTABLE` status when the required chain or envelopes are incomplete.

`SCHEDULED` is distinct from `BLOCKED`: it marks a BR/TS/TC that is correctly specified and certifiable, but not yet live in production before a stated `notBeforeDate` (ISO-8601 date). This check runs regardless of `strictExecutionContract`: any BR, TS, or TC with `SCHEDULED` status must include `notBeforeDate`, and none may be `EXECUTION_READY` while `notBeforeDate` is still in the future.

## Segment 100 Context-Level BR Categories

The authoritative Segment 100 baseline should classify requirements under these categories:

- `TRANSACTION_TYPE`: the 13 standard financial transaction-type meanings and lifecycle relationships.
- `COMPANION_DOMAIN`: EMV, fleet, fuel, eWIC/EBT, purchase-card, NFC/token, and variable-information companion segments.
- `LIFECYCLE`: completion, reversal, timeout, retry, cancellation, void, and refund behavior.
- `RESPONSE_NETWORK`: response-code behavior, authorizer context, and payment-network family rules.
- `APPENDIX_APPLICABILITY`: appendix and annexure applicability, conditional data, and specialized-domain boundaries.
- `CORE_STRUCTURE`: Segment 100 identity, framing, length, ordering, required fields, and serialization.

These context-level BRs are distinct from field-level source rules. A field rule may support a context BR, but it does not replace the context BR in the baseline.

## Test-Case Filtering

Test cases may include optional execution metadata:

```json
{
  "category": "compatibility",
  "tags": ["segment-100", "missing-companion"],
  "priority": "high",
  "status": "active"
}
```

`CanonicalTestCaseFilter` supports filtering by category, tag, priority, status, and expected outcome. Criteria are combined with AND semantics; omitted criteria match any value. For example, the remaining Segment 100 package can select only active, high-priority compatibility failures tagged `missing-companion`.

Implementation entry points:

- `CanonicalPackageLoader` loads one package JSON file.
- `CanonicalTraceabilityValidator` validates the package graph.
- `CanonicalArtifactPackage` and its artifact classes define the Java contract.