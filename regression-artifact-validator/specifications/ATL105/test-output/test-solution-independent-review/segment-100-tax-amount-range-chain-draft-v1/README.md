# Segment 100 Tax Amount Range: Preliminary Review

**Status:** The AI's `1-9999999` value range matches ATL105; all six linked cases declare Element 99 omitted.

ATL105 identifies Element 99 as numeric with two assumed decimal places. The field allows eight bytes for American Express and seven for other card types, while the stated valid-value ceiling is seven digits. The value-range claim is accurate as written; the representation details remain relevant to a complete Test design.

The existing independent `SEG100-R-047` rule concerns total tax semantics, not the numeric range. The symbolic draft adds review-only minimum, maximum, zero, and above-maximum vectors. Tax applicability, currency, serialization, and expected outcomes require authorized review. Element 99 is not Element 74 PC Tax Amount.

See the [review record](segment-100-tax-amount-range-review-v1.json), [draft chain package](segment-100-tax-amount-range-test-chain-package-v1.json), and [provenance manifest](manifest-v1.json). No execution, semantic match, coverage credit, or official baseline change is claimed.
