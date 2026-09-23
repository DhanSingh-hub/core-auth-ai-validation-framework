# Sequence and Lifecycle Correlation Flow

```mermaid
flowchart TD
    A[Initial POS transaction] --> B[Assign Sequence Number]
    B --> C[Send authorization or purchase request]
    C --> D{Host response received?}
    D -->|Approved| E[Store approval and original sequence]
    D -->|Declined| F[Complete decline handling]
    D -->|Timeout or late response| G[Create reversal/void request]

    E --> H{Follow-up flow?}
    H -->|Completion| I[Reuse original sequence and approval]
    H -->|Purchase void| J[Reuse original sequence and reference]
    H -->|Refund void| K[Reuse original refund identity]
    H -->|No| L[Reconcile transaction]

    G --> M[Reuse timed-out request sequence]
    I --> N[Validate lifecycle dependencies]
    J --> N
    K --> N
    M --> N
    N --> O{Sequence and references consistent?}
    O -->|Yes| P[Pass lifecycle validation]
    O -->|No| Q[Reject deterministic mismatch]
```

## Key Principle

A new Sequence Number for a follow-up message usually means the POS has lost the identity of the original transaction. That is a lifecycle defect even when the individual message fields look valid.
