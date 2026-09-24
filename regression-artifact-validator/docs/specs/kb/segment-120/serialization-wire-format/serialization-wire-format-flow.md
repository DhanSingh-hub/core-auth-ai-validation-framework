# Segment 120 Serialization and Wire-Format Flow

Segment 120 is conditionally included in Data Section 3 of a response
message only. This deliverable validates the envelope: field shapes, the
computed-length relationship, and separator placement.

```mermaid
flowchart TD
    A[Business condition: large print data required] --> B[Structured test-data JSON]
    B --> C[Validate JSON schema: segmentType, segmentLength, printData]
    C --> D{Envelope field values valid?<br/>SEG120-R-001..003}
    D -->|No| X[FAIL: envelope defect]
    D -->|Yes| E[Compute structural length:<br/>3 (Segment Type) + len(printData) + 2 separators]
    E --> F{Declared segmentLength equals computed length?}
    F -->|No| Y1[FAIL SEG120-R-002: SegmentLength mismatch]
    F -->|Yes| G{Computed length <= 1009?}
    G -->|No| Y2[FAIL SEG120-R-004: exceeds maximum]
    G -->|Yes| H[Count field separators in serialized form]
    H --> I{Exactly 2 separators: after field 1, after field 2;<br/>none after field 3?}
    I -->|No| Y3[FAIL SEG120-R-005: malformed separator placement]
    I -->|Yes| J[Segment 120 envelope validation passes]
    J --> K[Hand off to applicability check<br/>SEG120-R-006 and companion-compatibility]
```

## Contrast With Segment 111's Serialization

Segment 111 (Variable Information) requires exactly one **trailing**
separator after its final repetition. Segment 120 requires **no** trailing
separator after Print Data — its last field is simply the end of the
segment. This is a deliberate, spec-confirmed difference (Section 12.18:
"There is a Field Separator between Field Nos. 1 and 2 and between Field
Nos. 2 and 3" — nothing after Field 3), not an oversight, and a validator
copied from Segment 111 without adjustment would incorrectly require a
trailing separator that does not exist here.

## Layered Decision

```text
Business meaning (large print data required)
  -> logical JSON (segmentType, segmentLength, printData)
  -> serialized Segment 120 envelope (2 separators, no trailing)
  -> complete Financial Transaction Response payload
  -> executable test input
```

A failure at any layer must identify the representation, field, rule ID, and
source anchor involved. See the
[Segment Length Encoding note](../segment-length-encoding-sme-tba-note.md)
for the length-specific reasoning this flow depends on.
