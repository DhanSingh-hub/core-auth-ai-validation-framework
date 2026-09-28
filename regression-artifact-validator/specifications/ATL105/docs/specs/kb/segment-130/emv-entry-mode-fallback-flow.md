# EMV Entry Mode and Fallback Decision Flow

```mermaid
flowchart TD
    A[Card presented at device] --> B{Device EMV certified/chip capable?}
    B -->|No| C[Swipe - Entry Mode 90 MSR]
    B -->|Yes| D{Card has a chip?}

    D -->|No| C
    D -->|Yes| E[Attempt chip read]

    E --> F{Chip readable AND viable matching AID obtained?}
    F -->|Yes| G[EMV transaction - contact or contactless]
    F -->|No| H[Swipe - Entry Mode 80 FALLBACK]

    G --> I{Transaction class?}
    I -->|Reversal / TOR| J[Segment 130 NOT required<br/>Section 10.14.2.4]
    I -->|Purchase / Auth Only / Return /<br/>Balance Inquiry / Cancellation| K[Segment 130 REQUIRED<br/>SEG130-R-001]

    H --> L[Segment 130 ABSENT<br/>magnetic stripe path]
    C --> M[Segment 130 ABSENT<br/>not an EMV transaction]

    K --> N[Build EMV Financial Transaction Request]
    J --> N
    L --> O[Build standard Financial Transaction Request]
    M --> O

    N --> P{PIN mode?}
    P -->|Online PIN| Q[PIN block sent to issuer]
    P -->|Offline PIN| R[PIN field carries all Fs]
    P -->|No PIN - credit/signature debit| S[PIN optional - all Fs NOT required]

    Q --> T[Message complete]
    R --> T
    S --> T
```

## Two Distinct Reasons Segment 130 May Be Absent

```text
+---------------------------+-------------------------------------------+
| Cause                     | Meaning                                   |
+---------------------------+-------------------------------------------+
| Entry mode 80 (fallback)  | Chip read failed / no viable AID.         |
| Entry mode 90 (MSR)       | Not an EMV transaction at all.            |
+---------------------------+-------------------------------------------+
| Reversal / TOR            | IS an EMV transaction, but Section        |
|                           | 10.14.2.4 waives the EMV data requirement.|
+---------------------------+-------------------------------------------+
```

These must produce **different** validator diagnostics. A single generic "Segment 130 missing" message is insufficient.

## Liability-Shift Trap

```text
chip card + chip-capable device + swipe
    -> MUST be entry mode 80
    -> sending 90 instead is structurally valid
       but forfeits liability-shift protection
```

This is a business defect that no syntax check will catch — it requires the device/card capability context to be present in the test data.
