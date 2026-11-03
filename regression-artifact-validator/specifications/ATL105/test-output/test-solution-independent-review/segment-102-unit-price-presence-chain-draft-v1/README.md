# Segment 102 Unit Price Presence: Preliminary Review

**Status:** Source-supported required marker; chain evidence remains producer-only.

ATL105 marks Product Code Data Segment field 8 / Element 107 Unit Price as required. The independent `SEG102-R-013` rule covers decimal formatting but its TS/TC/TD are placeholders; it is not a dedicated presence chain. See [the review record](segment-102-unit-price-presence-review-v1.json) and [the presence-only draft](segment-102-unit-price-presence-test-chain-package-v1.json).

Four linked AI cases declare Unit Price omitted; four record `000`. They share the same bounded product metadata, but those observations do not independently establish valid populated amounts or expected behavior. The draft keeps presence separate from the decimal-format rule and all new records remain `REVIEW_REQUIRED`.

No semantic match or coverage credit is claimed. The official Test baseline is unchanged.
