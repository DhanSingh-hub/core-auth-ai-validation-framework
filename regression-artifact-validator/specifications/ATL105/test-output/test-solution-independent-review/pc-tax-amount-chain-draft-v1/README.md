# PC Tax Amount: Preliminary Review

**Status:** Source purpose statement aligned; AI chain evidence is insufficient.

ATL105 defines PC Tax Amount (Segment 104, Element 74) as numeric, up to seven digits, with two assumed decimal places. It identifies the transaction tax amount in the Purchase Card Data Segment and is distinct from Element 99 Tax Amount. The independent `SEG104-R-008` chain is still a placeholder. See [the review record](pc-tax-amount-review-v1.json), [the field-format draft](pc-tax-amount-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

All three linked AI cases list PC Tax Amount but the hash-bound screen declares Element 74 absent in each. They therefore do not demonstrate a populated amount, its format, or its meaning.

The draft covers only numeric type, length, and assumed decimals. It does not decide when Segment 104 applies or whether Element 74 is required. All records remain `REVIEW_REQUIRED`; the official Test baseline is unchanged.
