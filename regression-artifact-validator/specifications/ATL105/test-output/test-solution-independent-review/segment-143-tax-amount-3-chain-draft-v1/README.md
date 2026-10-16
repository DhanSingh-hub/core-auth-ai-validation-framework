# Segment 143 Tax Amount 3: Preliminary Review

**Status:** Source-level review open. No AI-to-Test match is approved and no coverage is credited.

The source directly supports the AI claim: when Inclusive/Exclusive for Tax 3 (Element 229) is `N`, Tax Amount 3 (Element 231) is omitted. It also requires Tax Type 3 (Element 230) to be omitted. The official `SEG143-R-006` scenario, case, and data records remain placeholders; see [the review record](segment-143-tax-amount-3-review-v1.json) and [the provenance manifest](manifest-v1.json).

Across the twelve linked AI cases, four candidate payload observations declare Elements 229-231 omitted but do not establish that Element 229 was `N`. Eight cases have no exact observation for these fields, and approved payload copies for `TC-010324` and `TC-010340` are absent from the frozen inventory.

The [supplemental draft](segment-143-tax-amount-3-test-chain-package-v1.json) covers only the source-explicit `N` branch: both associated fields omitted, Tax Type 3 present, or Tax Amount 3 present. All new records remain `REVIEW_REQUIRED`; no complete fixture was generated or executed, and the official baseline is unchanged.
