# Segment 100 Fuel Purchase Amount Range: Preliminary Review

**Status:** Both grouped AI claims match ATL105; all 11 linked cases declare Element 41 omitted.

ATL105 sets Element 41 valid values to `1-99999999`, with an eight-digit width for American Express and seven digits for other card types. It is variable-length with two assumed decimal places and applies to transactions with fuel sales. The AI claims capture the range and brand-specific widths but omit the decimal and applicability details.

The independent `SEG100-R-045` rule concerns net fuel amount semantics, not range or width. The symbolic draft separates Amex and non-Amex boundaries; fuel-sale context, decimal serialization, discounts, and expected outcomes require authorized review.

See the [review record](segment-100-fuel-amount-range-review-v1.json), [draft chain package](segment-100-fuel-amount-range-test-chain-package-v1.json), and [provenance manifest](manifest-v1.json). No execution, semantic match, coverage credit, or official baseline change is claimed.
