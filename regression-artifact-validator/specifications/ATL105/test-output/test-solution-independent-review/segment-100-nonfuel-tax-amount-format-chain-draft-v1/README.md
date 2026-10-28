# Segment 100 Nonfuel and Tax Amount Formats: Preliminary Review

**Status:** Four AI claims cover parallel card-specific widths; two omit the Amex-only qualification, and all 24 linked cases declare the relevant amount field omitted.

ATL105 gives Elements 58 and 99 numeric representations with two assumed decimal places. For each, Amex permits up to eight bytes and other card types up to seven. Element 58's valid-value ceiling is `99999999`; Element 99's is `9999999`, despite the Amex eight-byte capacity. Do not infer an extra valid digit for Element 99.

The AI claims that state the card-specific width are aligned. The two claims saying only “maximum length 8” are incomplete. Existing `SEG100-R-046` and `SEG100-R-047` rules concern net Nonfuel Amount and total Tax Amount semantics, not format boundaries. The draft retains distinct Element 58 and 99 scenarios; all vectors remain `REVIEW_REQUIRED`.

See the [review record](segment-100-nonfuel-tax-amount-format-review-v1.json), [draft package](segment-100-nonfuel-tax-amount-format-test-chain-package-v1.json), and [provenance manifest](manifest-v1.json). No execution, semantic match, coverage credit, or official baseline change is claimed.
