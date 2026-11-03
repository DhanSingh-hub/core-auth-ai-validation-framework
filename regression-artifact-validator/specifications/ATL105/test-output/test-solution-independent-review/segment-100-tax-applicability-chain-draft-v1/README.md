# Segment 100 Tax Applicability: Preliminary Review

**Status:** Six source-aligned conditional claims; all 36 linked cases omit Element 99 and have no exact nested tax-indicator observations.

The grouped obligations remain separate: Segment 102 tax-product totals reported in Segment 100 Element 99; Table 78 local tags 04/05 and other tags 10/11; Visa table 079 local tags 01/02 and other tags 07/08; and the EBT Cash Benefits versus Food Stamps sales-tax distinction. The four nested amount tags are 12 digits with two implied decimals when their indicator equals 1. The source lists `1 = True`; no false or zero indicator behavior is inferred.

The 36 cases are generic transaction contexts. Their Segment 100 Element 99 omission does not establish tax-product totals, Table 78/079 tag pairs, or the EBT benefit context. Some cases show Segment 143 fields or PC Tax Amount omissions; those are distinct evidence. Existing `SEG102-R-016` and `SEG100-R-047` chains remain placeholders. The six-scenario draft is symbolic and `REVIEW_REQUIRED`.

See the [review record](segment-100-tax-applicability-review-v1.json), [draft chain package](segment-100-tax-applicability-test-chain-package-v1.json), and [provenance manifest](manifest-v1.json). No execution, semantic match, coverage credit, or official baseline change is claimed.
