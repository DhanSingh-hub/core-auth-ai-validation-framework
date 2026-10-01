# Segment 143 Coverage Closure — SME and TBA Note

## Segment 143 Specific Notes

- This is the most structurally complex segment trained in this session — coverage tests must specifically exercise: (a) the dual-delimiter scheme (Field Separator vs Tax by Product Field Delimiter), (b) the "N" flag's field-omission behavior, (c) early termination (fewer than 3 taxes per product), and (d) the mandatory pairing with Segment 102.
- Do not treat this as a simple field-presence segment — most of its correctness lives in delimiter placement, not field values.
- Use the 5 worked examples in Section 12.30 as the canonical positive test fixtures before inventing new ones.
