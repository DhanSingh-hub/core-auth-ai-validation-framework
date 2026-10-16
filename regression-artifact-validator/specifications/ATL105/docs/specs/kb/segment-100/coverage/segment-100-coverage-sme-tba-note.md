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

### Single-Sample Evidence Boundary

A standalone request is not a BR coverage denominator or a complete coverage chain. Without a frozen delivery population and explicit BR -> TS -> TC -> TD identity, record only the rules the sample can directly exercise; do not count it as a confirmed AI/Test BR match or infer lifecycle coverage.

The sanitized Authorization sample screened on 2026-10-16 has three declared segment objects and its declared EMV byte count agrees with the observed hex byte count. Its Element 190 begins with bytes that, interpreted as a packed-BCD prefix, encode 145 bytes; the remaining 145 bytes parse into 22 TLV entries, including required identifier tags and no prohibited PAN/Track-2 tags. However, the current Appendix R contract treats the example's BCD length prefix as separate from the Element 190 TLV value, while this producer object places the prefix inside `EMVChipData`. Do not call the supplied stream malformed or accept the stripped remainder as compliant until the producer adapter's field boundary is confirmed; record prefix placement as `REVIEW_REQUIRED`. Element 188 independently fails its three-digit format and does not match the decoded chip sequence. The producer `TrackData` alias has no declared entry mode or Element 2/12 adapter mapping. The sample has no BR/TS/TC/TD identifiers, completion leg, response evidence, or frozen delivery inventory; therefore it contributes no confirmed BR-coverage numerator. Do not persist its raw track or chip values in review artifacts.

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
