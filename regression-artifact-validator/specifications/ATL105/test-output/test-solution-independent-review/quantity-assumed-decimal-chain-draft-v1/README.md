# Quantity Assumed-Decimal Encoding: Preliminary Review

**Status:** Source claims are supported in both segment tables; neither AI chain is adjudicated as a match.

ATL105 gives the same explicit example for Segment 102 and Segment 157: quantity `0.05` with two assumed decimal places is encoded as `205`; `25` is invalid. The official Test chains for both scopes remain placeholders. See [the review record](quantity-assumed-decimal-review-v1.json), [the supplemental draft](quantity-assumed-decimal-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

All eight linked AI cases have hash-verified candidate observations with Quantity `01` in Segment 102, Unit of Measure `C`, and Product Code `001`. The cases do not establish the assumed decimal context, so `01` cannot be judged from these observations alone. In addition, the four cases linked to the Segment 157 source claim contain no Segment 157 Quantity observation; their chain scope does not match that source anchor.

The draft keeps Segment 102 and Segment 157 separate and proposes the source examples as field-level test designs. All new records remain `REVIEW_REQUIRED`; no execution, semantic match, or coverage credit is claimed. The official baseline is unchanged.
