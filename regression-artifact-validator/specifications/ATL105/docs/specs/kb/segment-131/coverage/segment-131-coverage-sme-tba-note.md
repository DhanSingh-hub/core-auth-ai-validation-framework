# Segment 131 Coverage Closure — SME and TBA Note

## Segment 131 Specific Notes

- The placement contradiction (Section 12.21 "Data Section No. 3" vs the layout table's "Data Section No. 2, Field 17-20") must be resolved before any structural coverage rule can move past `REVIEW_REQUIRED`.
- Segment 131 uses no Field Separators — any traceability test asserting delimiter behavior must be written against this fixed-length format, not copied from a delimited segment's test pattern.
- The EMV Additional Information Section cap is 2,800 bytes for Segment 131, distinct from Segment 130's 2,000-byte cap — do not share a single mutation/boundary test between the two segments.
- CA Public Key File Checksum coverage should be written as a cross-segment (130→131 echo) test, not an isolated Segment 131 field check.
