# Segment 119 Firmware Version: Preliminary Review

**Status:** The source supports the AI field mapping and required-presence claim; chain adequacy is partial.

The Segment 119 table confirms field 11 is Element 39 Firmware Version, length 8, required. The independent `SEG119-R-018` TS/TC/TD records are placeholders. See [the review record](segment-119-firmware-version-review-v1.json), [the supplemental chain draft](segment-119-firmware-version-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

Two linked AI cases use the Totals-with-Proprietary-Data-Load context: one declares Firmware Version omitted, and one has an eight-character value. These are producer candidate observations, not independent validation or execution. The other three cases do not establish the same Segment 119 context.

The draft uses a synthetic positive field value and an omission probe. All records remain `REVIEW_REQUIRED`; the official Test baseline is unchanged and no coverage is credited.
