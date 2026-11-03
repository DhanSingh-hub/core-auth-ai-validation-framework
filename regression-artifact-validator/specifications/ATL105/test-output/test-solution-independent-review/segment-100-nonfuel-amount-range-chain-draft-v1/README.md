# Segment 100 Nonfuel Amount Range: Preliminary Review

**Status:** The AI range claim is incomplete; all six linked cases declare Element 58 omitted.

ATL105 Element 58 is numeric with two assumed decimal places. Its general valid-value range is 1-99999999, but the eight-digit width is permitted for American Express only; other card types are limited to seven digits. The AI claim omits that card-specific representation distinction.

The existing independent `SEG100-R-046` rule concerns net nonfuel amount semantics, not range or width. The six hash-bound AI cases provide no Element 58 amount evidence. The symbolic draft separates Amex and non-Amex width boundaries; its values and outcomes remain `REVIEW_REQUIRED` pending card context, decimal interpretation, and authorized Test review.

See the [review record](segment-100-nonfuel-amount-range-review-v1.json), [draft chain package](segment-100-nonfuel-amount-range-test-chain-package-v1.json), and [provenance manifest](manifest-v1.json). No execution, semantic match, coverage credit, or official baseline change is claimed.
