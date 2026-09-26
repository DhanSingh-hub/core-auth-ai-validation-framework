# Segment 100 Coverage Closure: SME and TBA Learning Note

## Purpose

Coverage closure answers a different question from validation:

```text
Validation: Is this individual artifact valid?
Coverage: Did we test every important Segment 100 rule?
```

A package can contain valid JSON and still be incomplete because it does not test a required behavior.

This work package creates an auditable answer for the Segment 100 scope.

## Step 1: Define the Boundary

Before counting coverage, state exactly what is included.

### Included

- TCP/IP header assumptions relevant to an ATL105 request
- Data Section 1 identity and segment count
- Segment 100 presence and identity
- Segment 100 field order and serialization behavior
- Required core fields
- Prompt Code and transaction context
- Conditional Segment 100 fields
- Segment 100 lifecycle correlation
- Companion-segment decisions that affect Segment 100

### Excluded or separately governed

- Full field-level behavior of Segment 103 EBT
- Full EMV Segment 130 field rules
- Merchant-specific host configuration not stated in ATL105
- Client approval decisions that are not specification rules

An excluded item must be marked as outside scope, not silently counted as covered.

## Step 2: Build the Authoritative Rule Catalog

The rule catalog is owned by the Test Team. It should contain one row per independently testable rule.

Recommended columns:

| Column | Purpose |
| --- | --- |
| Rule ID | Human-readable catalog identifier |
| Source anchor | Stable ATL105 identity |
| Rule statement | Requirement in testable language |
| Rule class | structure, field, serialization, compatibility, lifecycle |
| Severity | error, review, informational |
| Required evidence | What artifacts must exist |
| Approval state | draft, approved, retired |

Example:

```text
SEG100-R-001
ATL105 / 2026-3 / 12.1 / Segment 100 / segment-type
Segment 100 Segment Type must be 100.
class = field
severity = error
```

The catalog is not generated from the AI package. It is the independent reference against which AI output is measured.

## Step 3: Map the Artifact Chain

Each rule must be traceable through:

```text
Rule catalog
  -> Business Requirement
  -> Test Scenario
  -> Test Case
  -> Test Data
```

The local IDs may differ between producers. The source anchor must be the common identity.

A rule is fully covered only when:

- A BR carries the anchor.
- A scenario carries the anchor and links to the BR.
- A test case carries the anchor and links to the scenario.
- Test data carries the anchor and links to the test case.
- The test data exercises the claimed behavior.

## Step 4: Classify Coverage

| Status | Meaning | Certification impact |
| --- | --- | --- |
| `COVERED` | Complete chain and executable data exist | Eligible for approval |
| `PARTIALLY_COVERED` | Some artifacts exist, but the chain or behavior is incomplete | Cannot certify rule |
| `REVIEW_REQUIRED` | Data or interpretation requires SME/client decision | Cannot certify automatically |
| `MISSING` | No usable artifact evidence exists | Certification failure |

Do not convert `REVIEW_REQUIRED` into `COVERED` merely because a test case exists.

## Step 5: Check Semantic Evidence

Traceability alone is insufficient. A test case may claim to test Segment 100 order while the payload retains the wrong order.

For each rule, ask:

1. Does the test data contain the relevant field or segment?
2. Does the payload encode the positive or negative condition described by the case?
3. Does the expected outcome match the condition?
4. Is the validator actually capable of detecting the intended defect?
5. Is the result deterministic without asking an AI model to judge it?

## Step 6: Review With SME and TBA Questions

### SME checks

- Is this a real POS/card behavior?
- Is the transaction context correct?
- Are lifecycle relationships realistic?
- Is the field conditional in this business flow?
- Is the companion segment truly required or only possible?

### TBA checks

- Is the requirement atomic and testable?
- Does it have one canonical source anchor?
- Does the scenario vary a meaningful business condition?
- Does the test case isolate one behavior?
- Does the test data prove the expected result?
- Is the acceptance rule explicit?

## Step 7: Approval Decision

The Test Team should approve each rule, not only the package total.

Recommended decision values:

```text
APPROVED
APPROVED_WITH_REVIEW_ITEMS
REJECTED_MISSING_COVERAGE
REJECTED_TRACEABILITY
REJECTED_SEMANTIC_MISMATCH
```

A package should not receive overall approval when any mandatory rule is `MISSING`, `PARTIALLY_COVERED`, or unresolved `REVIEW_REQUIRED`.

## What I Need You To Check Before Implementation

Please confirm these decisions:

1. Is this Segment 100 boundary correct for the current phase?
2. Should lifecycle rules be mandatory in the first coverage report?
3. Should companion segments be represented in the Segment 100 catalog as compatibility rules only, or should their own field rules also be included now?
4. Should any `REVIEW_REQUIRED` rule block overall approval?
5. Should the report use the four statuses above, or do you want a separate `NOT_APPLICABLE` status?
6. Should the approval report be generated under `test-output/traceability-matrix/segment-100/`?
