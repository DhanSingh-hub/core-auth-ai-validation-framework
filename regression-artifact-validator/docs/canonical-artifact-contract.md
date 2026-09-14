# Canonical Artifact Contract

The validation package uses producer-local IDs plus shared `sourceAnchors`. IDs may differ between the AI solution and the Test Validation solution; source anchors provide the stable semantic identity.

```json
{
  "manifest": {
    "packageId": "ATL105-SEG100-001",
    "specification": "ATL105",
    "specificationVersion": "2026"
  },
  "businessRequirements": [
    {
      "id": "BR-AI-017",
      "title": "ATL105 message identifier",
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
      "sourceAnchors": [{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}],
      "payload": {"request": {}}
    }
  ]
}
```

The validator rejects missing or duplicate IDs, unresolved links, missing source anchors, orphaned artifacts, anchor mismatches, missing expected outcomes, and test data without a payload. `SourceAnchor.canonicalKey()` compares values case-insensitively and trims whitespace, so independently generated terminology can still converge on the same specification identity.

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