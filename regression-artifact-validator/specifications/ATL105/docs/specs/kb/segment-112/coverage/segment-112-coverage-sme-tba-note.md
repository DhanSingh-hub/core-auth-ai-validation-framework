# Segment 112 Coverage Closure: SME and TBA Learning Note

## Purpose

Coverage closure answers a different question from validation:

```text
Validation: Is this individual Segment 112 payload valid?
Coverage: Did we test every important Segment 112 rule?
```

A package can contain valid Segment 112 JSON and still be incomplete because it does not test a required behavior (for example, only ever exercising one triad, never the 990-byte boundary, or never the undocumented-code review path).

## Step 1: Define the Boundary

### Included

- Segment 112 core structure (Segment Type, Segment Length, 999-byte cap)
- The repeating Additional Information Section (Elements 116/117/118, 990-byte cap)
- Element 115 applicability (Segment 112 present only when Element 115 = 1)
- Fixed-length Element 116 codes (`012`, `013`, `017`, `018`) and their exact-length enforcement
- Documented Element 116 codes (`001`, `003`-`013`, `016`-`032`, `034`-`047`) as an allowlist
- The undocumented-code review path (`014`, `015`, `033`)

### Excluded or separately governed (for this pass)

- Appendix K's 47 per-code sub-table layouts (deferred to a follow-on Phase 2 workstream, same treatment as Segment 108's loyalty-receipt Appendix K tables)
- Segment 111's own rule catalog (referenced only for the companion-compatibility contrast note, not re-derived here)
- Client approval decisions that are not specification rules

An excluded item must be marked as outside scope, not silently counted as covered.

## Step 2: Build the Authoritative Rule Catalog

The rule catalog is owned by the Test Team, not generated from AI output. See [segment-112-rule-catalog.json](segment-112-rule-catalog.json) for the current 10-rule draft.

Example:

```text
SEG112-R-006
ATL105 / 2026-3 / 12.11 / Segment 112 / additional-information-section-repetition-max-990
Additional Information Section repeats by Indicator for a max of 990 bytes.
class = serialization
severity = error
```

## Step 3: Map the Artifact Chain

```text
Rule catalog
  -> Business Requirement (see additional-information-value-catalog-business-requirements.md)
  -> Test Scenario (not yet authored for Segment 112)
  -> Test Case (not yet authored)
  -> Test Data (not yet authored)
```

Local IDs may differ between producers. The source anchor must be the common identity.

## Step 4: Classify Coverage

Same taxonomy as Segment 100: `COVERED`, `PARTIALLY_COVERED`, `REVIEW_REQUIRED`, `MISSING`. As of this documentation pass, every Segment 112 rule is at best `PARTIALLY_COVERED` (BR exists; scenario/test-case/test-data do not yet exist) or `REVIEW_REQUIRED` (open SME items). None should be marked `COVERED` yet.

## Step 5: Check Semantic Evidence

For each rule, ask:

1. Does the test data contain the relevant triad(s) or segment-level condition?
2. Does the payload encode the positive or negative condition described by the case (e.g., a triad exactly at the 990-byte boundary, not merely "a small triad")?
3. Does the expected outcome match the condition (PASS, FAIL, or REVIEW_REQUIRED — not collapsing REVIEW_REQUIRED into PASS)?
4. Is the validator actually capable of detecting the intended defect (e.g., a length-mismatch between Element 117 and Element 118)?
5. Is the result deterministic without asking an AI model to judge it?

## Step 6: Review With SME and TBA Questions

### SME checks

- Is this a real host-response behavior (not a request-side assumption borrowed from Segment 111)?
- Is the Element 116 code genuinely applicable to the transaction context being modeled?
- Are the undocumented codes (`014`, `015`, `033`) actually out of scope, or has new specification guidance since resolved them?

### TBA checks

- Is the requirement atomic and testable?
- Does it have one canonical source anchor?
- Does the scenario vary a meaningful boundary (990-byte cap, 999-byte cap, fixed-length code exactness)?
- Does the test case isolate one verifiable behavior?
- Is the acceptance rule explicit, including the `REVIEW_REQUIRED` disposition for open items?

## Step 7: Approval Decision

Recommended decision values (same as Segment 100):

```text
APPROVED
APPROVED_WITH_REVIEW_ITEMS
REJECTED_MISSING_COVERAGE
REJECTED_TRACEABILITY
REJECTED_SEMANTIC_MISMATCH
```

Segment 112 is not eligible for `APPROVED` until the SME/TBA Input Register items are resolved and scenario/test-case/test-data artifacts exist. `APPROVED_WITH_REVIEW_ITEMS` is the most this documentation pass can currently support, and only once scenario/test-case/test-data artifacts are authored.

## What I Need You To Check Before Implementation

- Confirm the 7 open items in the [SME/TBA Input Register](../segment-112-sme-tba-input-register.md).
- Confirm whether the existing cross-segment AI extraction (see the [AI-to-Test requirement crosswalk](segment-112-ai-to-test-requirement-crosswalk.md)) is acceptable as the AI Solution input for Item 2, or whether a dedicated Segment 112 AI package should be produced first.
