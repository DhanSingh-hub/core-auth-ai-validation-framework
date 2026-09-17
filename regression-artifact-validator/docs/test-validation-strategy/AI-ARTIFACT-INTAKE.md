# AI Artifact Intake
## Source Selection for Local, Shared, and GitHub Artifacts

The Test Solution validates AI artifacts without changing them. The source location may change; the canonical package contract and validation pipeline do not.

## Supported Sources

### Local workspace

```java
AiArtifactSource.local(Path.of("test-input/ai-solution"));
```

Use for the current local workflow.

### Shared folder

```java
AiArtifactSource.shared(Path.of("\\\\server\\team-share\\ai-artifacts\\segment-100"));
```

Use for a mounted network share, synchronized folder, or controlled team drop location. Preserve the received files unchanged.

### GitHub repository

```java
AiArtifactSource.github(
    "https://github.com/org/ai-artifacts.git",
    "refs/tags/segment-100-v1",
    checkoutPath);
```

The GitHub adapter consumes a checked-out revision. CI or an approved operations step performs checkout and authentication; credentials are not embedded in the validator. Pin a branch, tag, or commit for reproducibility.

## Common Intake Result

Every source resolves to:

```text
AiArtifactIntakeResult
  -> packageRoot
  -> source type
  -> source location
  -> revision
  -> provenance
```

The downstream `CanonicalPackageLoader`, traceability validator, transaction validators, response validators, and reports use `packageRoot` and do not depend on where the files came from.

## Required AI Package Layout

```text
packageRoot/
  manifest.json
  business-requirements/
  test-scenarios/
  test-cases/
  test-data/
  responses/
  traceability/
  metadata/
  schemas/
```

## Intake Rules

1. Preserve the original source files exactly.
2. Record source type, location, revision, received time, and package hash in intake metadata.
3. Do not treat Test Solution fixtures as AI artifacts.
4. Reject unsupported or missing source roots before validation.
5. Pin GitHub revisions rather than validating a moving branch for certification.
6. Mark extraction or source ambiguity as `REVIEW_REQUIRED`.
7. Run the same BR→TS→TC→Test Data validation after every source type.

## Current Boundary

The adapters resolve source locations and provenance. They do not download GitHub repositories or handle credentials. A CI/operations process must provide the checked-out GitHub revision, after which the validator treats it exactly like a local package.
