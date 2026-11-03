# Segment 120 Print Data Length: Preliminary Review

**Status:** The source supports the required four-character length field; the AI chain is out of source scope.

ATL105 says Print Data 2 Segment 120 is only sent on Financial Transaction Response messages requiring large amounts of print data. Field 2 / Element 84 is required, four characters, and includes Segment Type and Field Separators. The independent `SEG120-R-002` chain is still a placeholder. See [the review record](segment-120-print-data-length-review-v1.json), [the symbolic response draft](segment-120-print-data-length-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

All five linked AI cases are request or Electronic Mail contexts. The bounded screen contains no exact Segment 120 Element 84 observation in any case; two approved payload copies for `TC-012688` are absent from the frozen inventory.

The supplemental design is limited to a response-side presence/omission probe and remains `REVIEW_REQUIRED`. No execution, semantic match, or coverage credit is claimed; the official Test baseline is unchanged.
