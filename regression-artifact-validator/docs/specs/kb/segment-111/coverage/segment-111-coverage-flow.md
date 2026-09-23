# Segment 111 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 2026-3, Section 12.10] --> B[Independent Segment 111 rule catalog<br/>SEG111-R-001..007]
    B --> C[Canonical source anchor]

    C --> D{Business Requirement carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario carries anchor and links to BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test Case carries anchor and links to scenario?}
    F -->|No| P
    F -->|Yes| G{Test Data carries anchor and links to case?}
    G -->|No| P
    G -->|Yes| H{Payload proves the claimed envelope behavior?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule falls inside Appendix I Table-ID content?}
    I -->|Yes| R[REVIEW_REQUIRED: out of envelope scope]
    I -->|No| J[COVERED]

    J --> K[Generate rule-level report]
    P --> K
    M --> K
    R --> K
    S --> K

    K --> L{All mandatory envelope rules covered?}
    L -->|Yes| A1[APPROVED]
    L -->|Review items only, e.g. P-01/P-02| A2[APPROVED_WITH_REVIEW_ITEMS]
    L -->|Missing or partial| A3[REJECTED_MISSING_COVERAGE]
    L -->|Broken links| A4[REJECTED_TRACEABILITY]
```

## Human Checkpoints

### Checkpoint 1: Scope approval

The SME/TBA confirms that the Segment 111 rule catalog covers the envelope
only (`SEG111-R-001..007`) and that Appendix I Table-ID content remains
explicitly out of scope for this release.

### Checkpoint 2: Rule catalog approval

The Test Team confirms that each catalog row is atomic, sourced to Section
12.10, and testable.

### Checkpoint 3: Artifact mapping approval

The Test Team confirms that source anchors are semantically equivalent even
when local IDs differ between the AI-generated package and the canonical
catalog.

### Checkpoint 4: Semantic evidence approval

The SME/TBA confirms that the payload actually represents the intended
envelope behavior — for example, that a "length mismatch" negative fixture
truly declares a length that disagrees with its value, not merely that a
test case is labeled as negative.

### Checkpoint 5: Certification approval

The Test Team decides whether `P-01`, `P-02`, and any other review items
block release, and records the decision alongside the report.

## Important Principle

A percentage such as "100% mutation detection" is not sufficient evidence of
full coverage. The report must identify the exact rule, the exact provisional
item, and the exact reason a rule is not yet `COVERED`.
