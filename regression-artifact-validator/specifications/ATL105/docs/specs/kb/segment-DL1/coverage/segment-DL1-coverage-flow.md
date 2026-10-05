# Segment DL1 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 12.42 / 11.7.1 / 13.2 / Appendix E] --> B[DL1 rule catalog - 12 rules]
    B --> C[Canonical source anchor per rule]
    C --> D{BR carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario links BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test case links scenario and has Given/When/Then?}
    F -->|No| P
    F -->|Yes| G{Test data holds the Table Load Request + Response pair?}
    G -->|No| P
    G -->|Yes| H{Payload proves the behaviour, e.g. 173 with DL6?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule linked to open SEGDL1-SME item?}
    I -->|Yes| R[REVIEW_REQUIRED]
    I -->|No| J[COVERED]
    J --> K[Rule-level report]
    P --> K
    M --> K
    R --> K
    S --> K
```

Rules currently `REVIEW_REQUIRED` by design: `SEGDL1-R-003` and `R-006` (P-02), `R-005` and `R-012` (P-03), and `R-009` (P-04). The provisional [coverage package](../../../../../test-output/test-json/segment-DL1-coverage-package.json) maps 12 scenarios, 27 test cases, and 27 symbolic request/response pairs; they are unapproved and unexecuted, so no rule is certified `COVERED`.

The [AI artifact coverage report](segment-DL1-ai-artifact-coverage-report.md) compares the available one-chain DL1 AI sample to this 12-rule oracle and independently validates its test data. It distinguishes candidate mappings from executed coverage.
