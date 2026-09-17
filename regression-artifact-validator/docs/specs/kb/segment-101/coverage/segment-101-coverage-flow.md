# Segment 101 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 Section 12.2 source + approved scope] --> B[Independent Segment 101 rule catalog<br/>SEG101-R-001..026]
    B --> C[Canonical source anchor]

    C --> D{Business Requirement carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario carries anchor and links to BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test Case carries anchor and links to scenario?}
    F -->|No| P
    F -->|Yes| G{Test Data carries anchor and links to case?}
    G -->|No| P
    G -->|Yes| H{Payload proves the claimed Segment 101 behavior?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule blocked by PROVISIONAL P-01..P-10?}
    I -->|Yes| R[REVIEW_REQUIRED]
    I -->|No| J1{Error-severity rule with only positive coverage?}
    J1 -->|Yes| P
    J1 -->|No| J[COVERED]

    J --> K[Generate rule-level report]
    P --> K
    M --> K
    R --> K
    S --> K

    K --> L{All mandatory Segment 101 rules covered?}
    L -->|Yes| A1[APPROVED]
    L -->|Review items only| A2[APPROVED_WITH_REVIEW_ITEMS]
    L -->|Missing or partial| A3[REJECTED_MISSING_COVERAGE]
    L -->|Broken links| A4[REJECTED_TRACEABILITY]
```

## Human Checkpoints

### Checkpoint 1: Scope approval

The SME/TBA confirms which Segment 101 rules belong in this release (base fields, Fleet Tags, companion compatibility, lifecycle).

### Checkpoint 2: Rule catalog approval

The Test Team confirms that each of the 26 `SEG101-R-###` catalog rows is atomic, sourced (Section 12.2 + element + rule), and testable.

### Checkpoint 3: Artifact mapping approval

The Test Team confirms that source anchors are semantically equivalent even when local IDs differ (the AI producer uses `REQ-SRC-ATL105-PDF-001:###` etc.).

### Checkpoint 4: Semantic evidence approval

The SME/TBA confirms that the payload actually represents the intended fleet-card behavior — e.g., that a "Segment 101 + Segment 145 both present" test data truly encodes both segments simultaneously.

### Checkpoint 5: PROVISIONAL resolution

For every rule flagged `REVIEW_REQUIRED` because of a `PROVISIONAL` item (P-01..P-10), the SME records the resolution before the rule may be re-classified.

### Checkpoint 6: Certification approval

The Test Team decides whether review items block release and records the decision with the report.

## Important Principle

A percentage such as `95% covered` is not sufficient evidence. The report must identify the exact uncovered Segment 101 rule, missing artifact, semantic mismatch, or unresolved business interpretation (PROVISIONAL question).
