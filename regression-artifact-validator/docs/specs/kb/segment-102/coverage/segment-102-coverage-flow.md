# Segment 102 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 source and approved scope] --> B[Independent Segment 102 rule catalog]
    B --> C[Canonical source anchor]

    C --> D{Business Requirement carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario carries anchor and links to BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test Case carries anchor and links to scenario?}
    F -->|No| P
    F -->|Yes| G{Test Data carries anchor and links to case?}
    G -->|No| P
    G -->|Yes| H{Payload proves the claimed behavior, including Segment 100 reconciliation?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule is PROVISIONAL pending Appendix F or SME input?}
    I -->|Yes| R[REVIEW_REQUIRED]
    I -->|No| J[COVERED]

    J --> K[Generate rule-level report]
    P --> K
    M --> K
    R --> K
    S --> K

    K --> L{All mandatory rules covered?}
    L -->|Yes| A1[APPROVED]
    L -->|Review items only| A2[APPROVED_WITH_REVIEW_ITEMS]
    L -->|Missing or partial| A3[REJECTED_MISSING_COVERAGE]
    L -->|Broken links| A4[REJECTED_TRACEABILITY]
```

## Human Checkpoints

### Checkpoint 1: Scope approval

The SME/TBA confirms which Segment 102 rules belong in this release, including whether Segment 143 (Tax by Product) consistency is in scope.

### Checkpoint 2: Rule catalog approval

The Test Team confirms that each catalog row is atomic, sourced, and testable, and that `PROVISIONAL` rows are visibly tracked.

### Checkpoint 3: Artifact mapping approval

The Test Team confirms that source anchors are semantically equivalent even when local IDs differ.

### Checkpoint 4: Semantic evidence approval

The SME/TBA confirms that the payload represents real pump/POS product behavior, and that the Segment 102-to-Segment 100 amount reconciliation actually holds in the test data, not merely that both segments are present.

### Checkpoint 5: Certification approval

The Test Team decides whether review items (including open Appendix F/product-classification items) block release and records the decision with the report.

## Important Principle

A percentage such as `95% covered` is not sufficient evidence. The report must identify the exact uncovered rule, missing artifact, semantic mismatch, or unresolved business interpretation.
