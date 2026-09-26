# Segment 132 Coverage Closure — SME and TBA Note

## Segment 132 Specific Notes

- Applicability (Required vs Conditional) within the CA Public Key File Load Request is not yet confirmed — do not certify without `SEG132-SME-001`.
- The 77-byte max length does not cleanly reconcile against the 74-byte sum of documented fields — flag any boundary test as provisional pending `SEG132-SME-002`.
- Field Separator behavior for fields 4-10 is undocumented — do not write serialization tests assuming either delimited or non-delimited encoding without `SEG132-SME-004`.
- This is the third segment in the KB referencing Element 187 (CA Public Key File Checksum), after Segments 130 and 131 — ensure traceability links this segment's checksum field to the same underlying business concept, not treated as unrelated.
