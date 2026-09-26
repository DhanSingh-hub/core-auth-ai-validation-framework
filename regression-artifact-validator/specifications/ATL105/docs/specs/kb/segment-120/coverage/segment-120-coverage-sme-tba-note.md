# Segment 120 Coverage Closure: SME and TBA Learning Note

## Purpose

Coverage closure answers a different question from validation:

```text
Validation: Is this individual Segment 120 envelope valid?
Coverage: Did we test every rule in the Segment 120 catalog, with both
          positive and negative evidence?
```

Segment 120 is a useful teaching case because its two coverage directions point in opposite
directions at once: the AI Solution's generated requirements map cleanly onto the Test Solution
catalog (100%), yet the generated *test cases* provide zero negative evidence and one requirement
directly contradicts the AI Solution's own message-template data.

## Step 1: Define the Boundary

### Included

- Segment Type, Segment Length, and Print Data field-level rules (`SEG120-R-001..003`)
- The 1,009-character total-length cap (`SEG120-R-004`)
- Field-separator placement between fields 1/2 and 2/3, with no trailing separator (`SEG120-R-005`)
- Applicability to Financial Transaction Response / EMV Financial Transaction Response only (`SEG120-R-006`)
- Segment 120's position as the final Data Section 3 segment (`SEG120-R-007`, provisional)
- The Blackhawk `\` line-delimiter convention inside Print Data (`SEG120-R-008`, provisional)

### Excluded or separately governed

- Full receipt/print-formatting business logic beyond the `\` delimiter convention
- Any Table-ID-style sub-structure (Segment 120's Print Data is unstructured text, unlike Segment 111's Table-ID repetitions)
- Client-specific host configuration that decides *when* "large amounts of print data" require Segment 120 versus Segment 115 (Print Data Segment)

## Step 2: Two Independent Questions, Two Reports

This coverage folder deliberately keeps two separate artifacts rather than one blended score:

1. **[AI Solution coverage report](segment-120-ai-coverage-report.md)** — for each Test rule, is there adequate AI-generated evidence (REQ, scenario, test case, including a negative case)? Answer: **0% COVERED**, because zero negative test cases exist anywhere in the approved catalog for Segment 120.
2. **[AI vs Test Solution analysis](segment-120-ai-vs-test-solution-analysis.md)** — for each AI-generated requirement, does a Test rule exist that explains it? Answer: **100%**, because Segment 120's small, well-scoped requirement set (13 items) mapped cleanly during catalog construction.

Reporting only one of these numbers would be misleading in opposite directions: quoting 100%
alone would hide the complete absence of negative testing; quoting 0% alone would hide that the
requirement extraction itself is clean and traceable.

## Step 3: The Ordering Question Was Resolved by Re-Reading the AI's Own Artifacts

Unlike Segment 100/101/111, Segment 120's coverage work initially surfaced what looked like an
**internal inconsistency inside the AI Solution's own artifacts**:

- `BR-263-2` (spec page 263): "The Print Data 2 Segment always appears at the end of a Financial
  Transaction response."
- The same pipeline run generated `SC-1839` / `TC-4305`, `TC-4306` asserting exactly that rule.
- The same pipeline run also produced `docs/atl105_complete_templates.json`, whose
  `Financial Transaction Response` template lists Segment 120 3rd of 9 segments — and its
  `EMV Financial Transaction Response` template lists it 3rd of 10 — well before Segments 134,
  136, 146, 148, 152, and 155.

**Resolution (2026-09-23):** re-examining `atl105_complete_templates.json` shows both response
templates order their segments in **strict ascending segment-number order**
(112, 115, 120, 134, 136, 146, 148, 152, 155 / EMV variant inserts 131 before 134). This matches
the pattern of the spec's own "Alphabetical List of Data Element Names" cross-reference appendix,
which also groups segments by number rather than transmission order — and no other spec section
documents true field-by-field wire order for these response messages. This is assessed as a
**numeric-sort artifact of the AI extraction process**, not genuine evidence of wire order.
`BR-263-2` is therefore treated as authoritative, and `SEG120-R-007` ("Segment 120 is the final
segment") is now hard-enforced in the baseline validator when an explicit segment order is
available in test data.

This resolution should be **revisited** if a real (non-synthetic) message is ever observed with a
companion segment following Segment 120 in the actual wire bytes.

## Step 4: Check Semantic Evidence, Not Just Chain Completeness

For each rule, ask:

1. Does the AI-generated test data contain the relevant field, length boundary, or ordering
   condition?
2. Does at least one test case violate the rule on purpose (a negative case)? For Segment 120,
   the answer is uniformly **no** — worth escalating as a pipeline-wide gap, not a
   Segment-120-specific one.
3. Does the expected outcome match the condition, and is the validator capable of catching the
   intended defect deterministically?

## Step 5: SME and TBA Review Questions

### SME checks

- ~~Which ordering is authoritative?~~ Resolved: the literal spec sentence (`BR-263-2`), per
  `P-01`'s resolution above.
- ~~Is Segment Length's field width fixed or variable?~~ Resolved: always exactly 4 digits, per
  Element 84 spec text (`P-02-WIDTH`).
- Is the 999-character Print Data cap independent of the 1,009-character total cap, or is one of
  the two numbers a typo (`P-02-RESIDUAL`, still open)?
- Does the Blackhawk `\` delimiter need structural validation, or is it business content outside
  this module's scope? (`P-03`)
- Can Segment 120 legally repeat within one response? (`P-04`)

### TBA checks

- Is each rule atomic and testable against one rule ID?
- Does the rule catalog correctly attribute `REQ-SRC-ATL105-PDF-001:1261` to Segment 120 even
  though the AI pipeline's own bucketer tagged it `UNASSIGNED`?
- Should the Test Team request that the AI Solution pipeline generate negative test cases before
  the next coverage cycle, given that 0 of 106 Segment 120 test cases — and, per the Segment 101
  report, 0 of 305 Segment 101 test cases — are negative?

## Step 6: Approval Decision

```text
APPROVED
APPROVED_WITH_REVIEW_ITEMS
REJECTED_MISSING_NEGATIVE_COVERAGE
REJECTED_MISSING_COVERAGE
REJECTED_TRACEABILITY
```

Given zero negative test cases and two rules still blocked by open PROVISIONAL items, the current
package-level decision for Segment 120 is **`REVIEW_REQUIRED`**, pending:

1. SME resolution of `P-02-RESIDUAL` (the 999-vs-1000 arithmetic gap), `P-03` (delimiter scope),
   and `P-04` (cardinality). `P-01` (ordering) and the width portion of `P-02` were resolved on
   2026-09-23 by deeper spec reading and no longer require SME input, though the resolution should
   be spot-checked against a real message if one becomes available.
2. Negative test case generation for all 8 rules from the AI Solution pipeline (or from the Test
   Solution's own mutation framework, see [Item 5/6](../../../SEGMENT-100-TRAINING-METHODOLOGY.md)).
3. Correction of the `REQ-SRC-ATL105-PDF-001:1261` bucketing gap upstream, or a durable note in
   this catalog if it will not be corrected.

## What I Need You To Check Before Full Certification

1. Is the 8-rule envelope-plus-ordering boundary correct for the current phase?
2. Does the `P-01` resolution (treating the AI template ordering as a numeric-sort artifact)
   hold up against any real (non-synthetic) message you have access to?
3. Should the Test Solution's own mutation framework (Items 5-7) be relied on to supply the
   missing negative cases, given that the AI Solution pipeline currently supplies none?
