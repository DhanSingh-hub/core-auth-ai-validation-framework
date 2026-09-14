# Core Auth Regression Test Strategy
## Expected Questions and Answers

Use this document as a presenter’s preparation guide. Answers should be adapted to the audience, but the decisions below are the current strategy position.

## Strategy and Scope

### Is this an ATL105-only solution?

No. The end goal is a reusable Core Auth Regression Test Solution for any available specification. ATL105 is the first proof-of-concept specification used to establish the specification-pack approach.

### What is the current ATL105 scope?

The current delivery target is complete ATL105: all applicable segments, message categories, transaction flows, response rules, appendices, dependencies, lifecycle rules, and serialization rules. Segment 100 is the first foundation module, not the final scope.

### What is the Test Solution responsible for?

It generates canonical Test Team artifacts from approved specification rules and independently validates AI-generated artifacts. It validates structure, provenance, traceability, semantics, coverage, and approval evidence.

### Does it execute the generated tests?

The current strategy focuses on artifact generation, normalization, validation, traceability, coverage, and reporting. Execution and failure triage remain separate capabilities unless explicitly integrated.

## AI Output Validation

### How do we know an AI requirement is correct?

The Test Solution compares it with an independent rule catalog derived from the specification. AI IDs, statements, confidence scores, and expected results are evidence under test, not the source of truth.

### What if the AI uses a different JSON format?

An approved, versioned adapter normalizes the AI format into the canonical model. If no adapter exists, the result is `FORMAT_UNSUPPORTED` or `NORMALIZED_WITH_REVIEW`.

### What if the AI JSON is valid but semantically wrong?

It may pass syntax validation but fail semantic validation. The Test Solution compares the actual payload with independent ATL105 rules and reports `REJECTED_SEMANTIC_MISMATCH`.

### What information is required from the AI Solution Team?

The team must provide schemas, format/version metadata, ID conventions, source-reference conventions, expected-outcome definitions, lifecycle representation, serialization rules, synthetic-data confirmation, known limitations, and one complete package considered correct.

## Traceability and Coverage

### How are different AI IDs matched?

By canonical source anchors, not by matching local IDs:

```text
Specification + version + section + segment + element + rule
```

### What does complete traceability mean?

```text
Independent source rule
  -> Business Requirement
      -> Test Scenario
          -> Test Case
              -> Test Data
```

Every link must resolve, anchors must agree, and the test data must prove the claimed behavior.

### What is the coverage denominator?

The Test Validation solution proposes using an independent atomic rule catalog and independently derived scenario baseline. AI-reported coverage and AI-approved scenario counts are evidence under test, not authoritative denominators. This coverage model is pending Fiserv Leadership approval.

### What is Rule Coverage?

```text
Rule Coverage = mandatory rules with complete executable evidence
                / mandatory rules in the independent rule catalog
                x 100
```

Additional metrics include requirement, scenario, test-case, test-data, traceability, semantic-execution, parameter, and segment coverage.

### Why not use the AI Solution’s coverage percentage?

The AI Solution may use a different denominator or omit missing rules. Independent validation must calculate coverage against specification-derived rules and accepted scope.

## Human Review and Dependencies

### Is every AI artifact manually reviewed?

No. The Test Team manually reviews only a small, risk-based handful of high-risk artifacts. The sample should represent high business, financial, lifecycle, dependency, ambiguity, sensitive-data, or serialization risk.

### What does manual sample validation compare?

```text
AI artifact
  <> canonical Test Solution artifact
  <> independent specification rule
  <> expected business behavior
```

The sample validates compatible results between the AI Solution and Test Solution before broader acceptance. It does not replace automated validation or formal approval.

### What happens when no SME is available?

Technical validation may continue for source-verifiable, structural, traceability, and executable checks. Ambiguous or interpretive items remain provisional, and business certification is blocked until an SME/TBA or delegated approver is available.

### What is the current dependency?

The current RAID log contains two dependencies:

- `D-001`: manual validation of a small high-risk AI artifact sample, owned by Coforge Test Team.
- `D-002`: availability of an SME/TBA or delegated business approver, with Owner currently blank and status `Blocked`.

## ATL105 Foundation

### Why was Segment 100 selected first?

It establishes reusable patterns for message identity, segment count, serialization, field dependencies, lifecycle correlation, compatibility, traceability, and coverage. The pattern is then expanded to every ATL105 segment and message category.

### What does the ATL105 Foundation Plan include?

- Specification profile and source rule catalog
- All ATL105 segments and message categories
- BR, scenario, case, and test-data derivation
- Approval gates
- Traceability and coverage
- Dependency and RAID governance
- Segment-by-segment expansion

### What is the manual-review policy for unknown combinations?

Unknown or ambiguous combinations are held for manual review. They are not automatically approved or rejected.

## Difficult Questions

### Can we certify the AI Solution today?

We can validate defined rules and identify defects deterministically. Complete certification requires the complete ATL105 rule catalog, all in-scope segment modules, resolved SME dependencies, completed high-risk sample comparison, and approval of the proposed coverage model.

### Does this solution trust another AI to validate the first AI?

No. The final Test Validation decision is based on deterministic schemas, source anchors, independent rules, executable checks, coverage, and human approval. AI assistance may support generation or prioritization, but it does not self-approve.

### What happens when AI and Test Solution outputs disagree?

The difference is classified as a format, adapter, provenance, traceability, semantic, coverage, or business-interpretation finding. It remains unresolved until evidence and the accountable review decision are recorded.

### What is the most important limitation today?

The platform strategy is broader than the current implementation. ATL105 is being expanded module by module, and SME availability is currently a blocked dependency for business approval.
