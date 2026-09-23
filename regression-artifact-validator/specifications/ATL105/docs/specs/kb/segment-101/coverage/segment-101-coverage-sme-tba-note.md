# Segment 101 Coverage Closure: SME and TBA Learning Note

## Purpose

Coverage closure answers a different question from validation:

```text
Validation: Is this individual Segment 101 artifact valid?
Coverage: Did we test every important Segment 101 rule?
```

A package can contain valid JSON and still be incomplete because it does not test a required behavior of the Fleet Data Segment.

This work package creates an auditable answer for the Segment 101 scope. It follows the same 7-step model as the [Segment 100 coverage closure note](../../segment-100/coverage/segment-100-coverage-sme-tba-note.md).

## Step 1: Define the Boundary

Before counting coverage, state exactly what is included.

### Included

- Segment 101 base fields 1..13 (Segment Type, Segment Length, Odometer, Vehicle Number, Job Number, Driver/Identification Number, Fleet Employee Number, License #, Job ID, Department #, Customer Data, User ID, Vehicle ID#)
- Segment 101 Fleet Tag fields 14..18 (Auth Completion 0220 + Host Prompts only)
- Fleet Tag TLV format and per-code payload rules (17 codes)
- Segment 101 serialization behavior (field order, empty non-trailing separators, trailing-optional omission, Segment Length)
- Segment 101 companion-segment compatibility with Segment 100 (required sibling), Segment 145 (forbidden coexistence), and feature companions 102/103/104/111/123/130/135
- Segment 101 lifecycle correlation on Auth-then-Completion and Reversal/Void flows
- Element 63 count contribution when Segment 101 is present

### Excluded or separately governed

- Full field-level behavior of Segment 145 (Enhanced Fleet) — handled by its own catalog; only the mutual-exclusion boundary is in scope here
- Full EMV Segment 130 rules — Segment 130 companion presence is in scope only when triggered alongside a fleet transaction
- Fleet-program specifics (BUYPASS® Petroleum Industry Processing Specifications) — deferred to a separate reference (PROVISIONAL P-07)
- Merchant-specific fleet-program contract configuration not stated in ATL105
- Client approval decisions that are not specification rules

An excluded item must be marked as outside scope, not silently counted as covered.

## Step 2: Build the Authoritative Rule Catalog

The rule catalog for Segment 101 is owned by the Test Team. It is defined in [segment-101-rule-catalog.json](segment-101-rule-catalog.json) with 26 rules (`SEG101-R-001` through `SEG101-R-026`) plus 10 provisional items (`P-01` through `P-10`).

Recommended columns (already realized in the JSON):

| Column | Purpose |
| --- | --- |
| Rule ID | `SEG101-R-###` |
| Source anchor | ATL105 / 2026-3 / Section 12.2 / segment 101 / element / rule |
| Rule statement | Requirement in testable language |
| Rule class | structure, field, serialization, compatibility, applicability, lifecycle, metadata |
| Severity | error, review, informational |
| Provisional | Reference to `P-##` when the rule needs SME input |

The catalog is not generated from the AI package. It is the independent reference against which AI output is measured.

## Step 3: Map the Artifact Chain

Each Segment 101 rule must be traceable through:

```text
Rule catalog (SEG101-R-###)
  -> Business Requirement
  -> Test Scenario
  -> Test Case
  -> Test Data
```

The local IDs may differ between producers (the AI solution uses `REQ-SRC-ATL105-PDF-001:###`, `SC-####`, `TC-#####`). The canonical source anchor (Section 12.2 + segment 101 + element + rule) must be the common identity.

A rule is fully covered only when:

- A BR carries the anchor.
- A scenario carries the anchor and links to the BR.
- A test case carries the anchor and links to the scenario.
- Test data carries the anchor and links to the test case.
- The test data exercises the claimed behavior (positive or negative).

## Step 4: Classify Coverage

| Status | Meaning | Certification impact |
| --- | --- | --- |
| `COVERED` | Complete chain and executable data exist; positive and at least one negative case present for error-severity rules | Eligible for approval |
| `PARTIALLY_COVERED` | Some artifacts exist, but the chain or behavior is incomplete (e.g., no negative case for an error-severity rule, or missing test data) | Cannot certify rule |
| `REVIEW_REQUIRED` | Data or interpretation requires SME/client decision (rule references a `PROVISIONAL` item, or Fleet Tag semantics unresolved) | Cannot certify automatically |
| `MISSING` | No usable artifact evidence exists for this Segment 101 rule | Certification failure |

Do not convert `REVIEW_REQUIRED` into `COVERED` merely because a test case exists.

## Step 5: Check Semantic Evidence

Traceability alone is insufficient. A test case may claim to test Segment 101 field order while the payload retains the wrong order.

For each Segment 101 rule, ask:

1. Does the test data contain the relevant Segment 101 field or Fleet Tag position?
2. Does the payload encode the positive or negative condition described by the case?
3. Does the expected outcome match the condition (e.g., FAIL for Segment 101 + Segment 145 coexistence)?
4. Is the validator actually capable of detecting the intended defect?
5. Is the result deterministic without asking an AI model to judge it?

## Step 6: Review With SME and TBA Questions

### SME checks

- Is this a real fleet-card / fleet-program behavior?
- Is the transaction context correct (Financial Request vs. Auth Completion)?
- Are Fleet Tags only used in Auth Completion with Host Prompts?
- Are companion segments treated correctly (Segment 100 required, Segment 145 forbidden)?
- Is the field conditional in this fleet flow (issuer requirement)?

### TBA checks

- Is the requirement atomic and testable?
- Does it have one canonical source anchor (Section 12.2 + element + rule)?
- Does the scenario vary a meaningful fleet-business condition?
- Does the test case isolate one Segment 101 behavior?
- Does the test data prove the expected result?
- Is the acceptance rule explicit?

## Step 7: Approval Decision

The Test Team should approve each Segment 101 rule, not only the package total.

Recommended decision values:

```text
APPROVED
APPROVED_WITH_REVIEW_ITEMS
REJECTED_MISSING_COVERAGE
REJECTED_TRACEABILITY
REJECTED_SEMANTIC_MISMATCH
```

A package should not receive overall approval when any mandatory Segment 101 rule is `MISSING`, `PARTIALLY_COVERED`, or an unresolved `REVIEW_REQUIRED`.

## What The Reviewer Should Check Before Implementation

1. Is this Segment 101 boundary correct for the current phase?
2. Should the Fleet Tag rules block overall approval when `PROVISIONAL P-01` and `P-02` are unresolved?
3. Should the coverage report be generated under `docs/specs/kb/segment-101/coverage/`?
4. Should any `REVIEW_REQUIRED` rule (e.g., unresolved `PROVISIONAL` item) block overall approval?
5. Should the report use the four statuses above, or should a `NOT_APPLICABLE` status be added for non-fleet flows?
