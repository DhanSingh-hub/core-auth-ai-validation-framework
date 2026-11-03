# Appendix D State Code Uses: Preliminary Review

**Status:** Source mapping is aligned; the AI cases do not demonstrate any of the cited values.

Appendix D names Address Line 2 (Element 4), Card Discretionary Block Data (Element 12), and State Code (Element 124) as alphabetical State Code uses. ANSI codes are separately used for Terminal Identifier. See [the review record](dl1-address-line-2-state-code-review-v1.json) and [the symbolic DL1 draft](dl1-address-line-2-state-code-test-chain-package-v1.json).

The existing Appendix D Test chain checks Element 124 in Segment 110, but explicitly leaves Element 12 outside its validator. The DL1 Address Line 2 chain remains a placeholder. None of the five linked AI cases has an exact observation for the relevant state-code value.

The supplemental design references the existing `CA` valid and `ZZ` unlisted candidates by reference; it leaves city and ZIP fixtures symbolic. All records remain `REVIEW_REQUIRED`; no coverage is credited and the official Test baseline is unchanged.
