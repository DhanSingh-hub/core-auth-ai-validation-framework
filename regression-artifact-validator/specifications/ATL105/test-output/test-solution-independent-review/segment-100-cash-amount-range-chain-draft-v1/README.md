# Segment 100 Cash Amount Range: Preliminary Review

**Status:** Both grouped AI claims are source-aligned; all 12 linked cases declare Element 17 omitted.

ATL105 defines Element 17 as numeric, variable-length up to eight digits with two assumed decimal places, identifying transaction cash-back amount. When supported, the optional Interlink Cash Back Fee is included in the field. One AI claim reports only the eight-byte maximum; the second includes the decimal representation and purpose.

The independent `SEG100-R-048` rule concerns total cash semantics, not the numeric range. The symbolic draft adds review-only minimum, maximum, zero, and over-maximum vectors. Cash-back context, currency encoding, optional fee handling, and expected outcomes require authorized review. Segment 157 reconciliation remains separate.

See the [review record](segment-100-cash-amount-range-review-v1.json), [draft chain package](segment-100-cash-amount-range-test-chain-package-v1.json), and [provenance manifest](manifest-v1.json). No execution, semantic match, coverage credit, or official baseline change is claimed.
