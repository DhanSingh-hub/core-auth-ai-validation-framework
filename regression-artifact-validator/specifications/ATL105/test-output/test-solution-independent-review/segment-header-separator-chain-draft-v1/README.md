# Product Segment Header Separators: Preliminary Review

**Status:** Both BR statements are source-supported in their specific segment tables; chain adequacy remains unresolved.

ATL105 states that Field Separators follow Segment Type and Segment Length in both Segment 102 and Segment 157. The independent Test package has corresponding rules, but both chains are placeholders. See [the review record](segment-header-separator-review-v1.json), [the symbolic draft](segment-header-separator-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

Each AI BR links five cases across multiple transaction contexts. Only one case per BR names a Product Code Data Segment, and neither supplies raw serialized wire evidence. For the Segment 157 BR, even that case names the separate Segment 102 Product Code Data Segment. The bounded JSON screen therefore cannot verify separator placement.

The draft uses symbolic separator tokens, not wire bytes, and covers only the two header separators. All records remain `REVIEW_REQUIRED`; no semantic match or coverage credit is claimed, and the official Test baseline is unchanged.
