# Segment 111 Companion-Segment Compatibility Decision Flow

```mermaid
flowchart TD
    A[Receive ATL105 request] --> B[Segment 100 present exactly once?]
    B -->|No| X[FAIL: Segment 100 cardinality — handled by Segment 100 module]
    B -->|Yes| C[Decode Prompt Code and Appendix I business conditions]
    C --> D{Does any condition require Variable Information?<br/>AVS, SIC, ZIP, RFID entry mode, MSDI, soft descriptor,<br/>MIT/CIT, digital commerce, transaction fees, etc.}
    D -->|No| E[Segment 111 must be absent]
    D -->|Yes| F[Segment 111 required]

    F --> G{Segment 111 present in Data Section 3?}
    G -->|No| Y[FAIL: missing required companion]
    G -->|Yes| H{Exactly one Segment 111 per message?}
    H -->|Unresolved| R1[REVIEW_REQUIRED: cardinality — P-02]
    H -->|Yes| I[Validate Segment 111 envelope<br/>SEG111-R-001..007]

    E --> J{Segment 111 present anyway?}
    J -->|Yes| Z[REVIEW_REQUIRED or FAIL:<br/>unexpected companion segment]
    J -->|No| K[Continue without Segment 111]

    I --> L[Update Element 63 to include Segment 111]
    K --> L
    L --> M{Element 63 equals actual serialized segment count?}
    M -->|No| W[FAIL: segment-count mismatch]
    M -->|Yes| N[Continue to serialization and lifecycle checks]
```

## Decision Summary

```text
Segment 100 is the baseline (validated by the Segment 100 module).
Segment 111 is condition-driven, not universal.
Segment 111 validates only its own envelope; Appendix I content is out of scope.
Element 63 must count Segment 111 when it is present.
Unresolved cardinality (more than one Segment 111) is reviewed, not auto-passed.
```

## Related Diagram

See the Segment 100
[companion-segment compatibility flow](../../segment-100/companion-compatibility/companion-segment-compatibility-flow.md)
for the parent decision that routes into this one (`Variable information? ->
Require Segment 111`).
