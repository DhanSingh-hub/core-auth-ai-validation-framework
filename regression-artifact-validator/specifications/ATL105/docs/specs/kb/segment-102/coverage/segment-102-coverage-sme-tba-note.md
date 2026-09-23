# Segment 102 Coverage Closure: SME and TBA Learning Note

## Purpose

Coverage closure answers a different question from validation:

```text
Validation: Is this individual Segment 102 artifact valid?
Coverage: Did we test every important Segment 102 rule, including reconciliation with Segment 100?
```

A package can contain valid JSON and still be incomplete because it does not test a required behavior, such as fuel-first ordering or amount reconciliation.

This work package creates an auditable answer for the Segment 102 scope, following the same method as [Segment 100 coverage closure](../../segment-100/coverage/segment-100-coverage-sme-tba-note.md).

## Step 1: Define the Boundary

### Included

- Segment 102 presence and identity
- Segment 102 field values (Service Level, Number of Products, Product Code, Unit of Measure, Quantity, Unit Price, Product Amount)
- Product ordering (fuel/EV first, unique fuel codes, multi-fuel OTR primary-first)
- Reconciliation of Segment 102 Product Amounts against Segment 100 Elements 41, 58, 99, 17
- Mutual exclusivity with Segment 157 and non-applicability to Comdata cards
- Segment 102 serialization (separators, delimiters, length bounds)

### Excluded or separately governed

- Full field-level behavior of Segment 157 (Adjusted Product Code Data Segment)
- Full field-level behavior of Segment 143 (Tax by Product Data Segment) beyond the order/count consistency check
- Merchant-configuration logic outside the message-format specification (e.g., how a POS decides a merchant is a "fuel merchant")
- Complete Appendix F Product Code enumeration (currently partial; see rule catalog `PROVISIONAL` entries)

An excluded item must be marked as outside scope, not silently counted as covered.

## Step 2: Build the Authoritative Rule Catalog

The rule catalog is owned by the Test Team and lives in [segment-102-rule-catalog.json](segment-102-rule-catalog.json). It has one row per independently testable rule, using the same schema as the Segment 100 catalog (`ruleId`, `title`, `class`, `severity`, `sourceAnchor`), plus a `status` field for rows that are `PROVISIONAL` pending SME/spec input.

## Step 3: Map the Artifact Chain

```text
Rule catalog
  -> Business Requirement
  -> Test Scenario
  -> Test Case
  -> Test Data
```

A rule is fully covered only when:

- A BR carries the anchor.
- A scenario carries the anchor and links to the BR.
- A test case carries the anchor and links to the scenario.
- Test data carries the anchor, links to the test case, and (for amount/tax rules) contains values that actually reconcile against Segment 100.

## Step 4: Classify Coverage

| Status | Meaning | Certification impact |
| --- | --- | --- |
| `COVERED` | Complete chain and executable data exist | Eligible for approval |
| `PARTIALLY_COVERED` | Some artifacts exist, but the chain or behavior is incomplete | Cannot certify rule |
| `REVIEW_REQUIRED` | Data or interpretation requires SME/client decision (includes all `PROVISIONAL` catalog rows) | Cannot certify automatically |
| `MISSING` | No usable artifact evidence exists | Certification failure |

## Step 5: Check Semantic Evidence

For each rule, ask:

1. Does the test data contain the relevant Segment 102 field or product entry?
2. Does the payload encode the positive or negative condition described by the case (e.g., fuel listed second instead of first)?
3. For amount rules, does the Segment 102 Product Amount sum actually equal the Segment 100 aggregate amounts in the test data, not just structurally resemble it?
4. Does the expected outcome match the condition?
5. Is the validator actually capable of detecting the intended defect?

## Step 6: Review With SME and TBA Questions

### SME checks

- Is this a real pump/POS product sale (fuel, nonfuel, EV, multi-fuel)?
- Is the Product Code plausible for the described product, given the currently-verified Appendix F range?
- Is the fuel-merchant nonfuel-data expectation realistic for this merchant type?

### TBA checks

- Is the requirement atomic and testable?
- Does it have one canonical source anchor?
- Does the test case isolate one verifiable behavior (ordering, count, reconciliation, exclusivity, or serialization) rather than mixing several?
- Is the acceptance rule explicit, including the exact reconciliation formula?

## Step 7: Approval Decision

```text
APPROVED
APPROVED_WITH_REVIEW_ITEMS
REJECTED_MISSING_COVERAGE
REJECTED_TRACEABILITY
REJECTED_SEMANTIC_MISMATCH
```

A package should not receive overall approval when any mandatory rule is `MISSING`, `PARTIALLY_COVERED`, or unresolved `REVIEW_REQUIRED`.

## What I Need You To Check Before Implementation

1. Confirm the full Appendix F Product Code table (or point to its source) so `SEG102-R-010` can move out of `PROVISIONAL`.
2. Confirm which Product Codes are fuel vs. non-fuel, and which represent EV charging, so `SEG102-R-007`, `SEG102-R-008`, and `SEG102-R-024` can move out of `PROVISIONAL`.
3. Decide whether the fuel-merchant nonfuel-data rule (`SEG102-R-017`) is enforced as a hard rule or left as a review-only flag.
4. Decide whether Segment 143 order/count consistency (`SEG102-R-025`) is in scope for this Segment 102 pass or deferred to its own Segment 143 training pass.
