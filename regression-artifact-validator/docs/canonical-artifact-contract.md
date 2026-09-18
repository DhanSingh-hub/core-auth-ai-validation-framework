# Canonical Artifact Contract

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