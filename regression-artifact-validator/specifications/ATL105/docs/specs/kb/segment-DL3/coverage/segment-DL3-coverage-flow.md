# Segment DL3 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 12.44 / 11.7.3 / 13.2] --> B[DL3 rule catalog - 9 rules]
    B --> C[Canonical source anchor per rule]
    C --> D{BR carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario links BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test case links scenario and has Given/When/Then?}
    F -->|No| P
    F -->|Yes| G{Test data holds request, response and device time zone?}
    G -->|No| P
    G -->|Yes| H{Payload proves the behaviour?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule linked to open SEGDL3-SME item?}
    I -->|Yes| R[REVIEW_REQUIRED]
    I -->|No| J[COVERED]
```

Rules currently `REVIEW_REQUIRED` by design: `SEGDL3-R-003`, `R-006` (P-02), `R-001`, `R-007` (P-03), `R-005` (P-04), and review-severity `R-009` pending device-level evidence.

The independent candidate package maps all 9 rules to canonical BRs and scenarios, and defines 18 candidate test cases with linked symbolic request/response records. It is not an AI-produced test package, is not SME-approved, and has not been executed. See the [coverage report](segment-DL3-ai-artifact-coverage-report.md) for the separate 12-requirement supplied AI catalog, the one-chain phase-one sample, and open validation results.
