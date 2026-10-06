# Sequence and Lifecycle Correlation Flow

```mermaid
flowchart TD
    A[Initial financial transaction] --> B[Assign Sequence Number]
    B --> REQ[Send authorization, purchase, or refund request]
    REQ --> D{Host response received?}
    D -->|Approved| E[Store approval and original sequence]
    D -->|Declined| F[Complete decline handling]
    D -->|Timeout or late response| G{Original transaction type?}
    G -->|Eligible purchase| M[Queue TOR type Z]
    G -->|Authorization only| CANCEL[Cancel authorization with type S]

    E --> H{Follow-up flow?}
    H -->|Authorization -> completion| I[Reuse original sequence and approval]
    H -->|Authorization-only cancellation| CANCEL
    H -->|Purchase/capture -> reversal| J[Reuse original sequence and reference]
    H -->|Refund -> void of return| K[Reuse original refund identity]
    H -->|No| L[Reconcile transaction]

    M --> R[Reuse timed-out request sequence]
    R --> N
    CANCEL --> N
    I --> N[Validate lifecycle dependencies]
    J --> N
    K --> N
    N --> O{Sequence and references consistent?}
    O -->|Yes| P[Pass lifecycle validation]
    O -->|No| Q[Reject deterministic mismatch]
```

## Key Principle

A new Sequence Number for a follow-up message usually means the POS has lost the identity of the original transaction. That is a lifecycle defect even when the individual message fields look valid.

An Authorization Only Reversal uses Prompt Code `S` (cancellation), not purchase reversal/void code `8`. A timeout uses TOR code `Z` only for a source-eligible financial purchase; an authorization-only timeout is recovered with cancellation.

Do not model a direct Authorization Only -> code `8` reversal. An Authorization -> Completion -> Void chain is a distinct three-leg question: [SEG100-SME-002](segment-100-sme-tba-input-register.md) remains open on the void target and debit eligibility, so keep that chain `REVIEW_REQUIRED` until resolved.
