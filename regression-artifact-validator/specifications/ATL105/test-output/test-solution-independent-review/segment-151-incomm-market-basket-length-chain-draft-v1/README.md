# Segment 151 InComm Market Basket Length: Preliminary Review

**Status:** The source supports a required four-character length indicator; the linked cases do not establish Segment 151.

ATL105 defines Segment 151 as an InComm OTC Market Basket Input segment. Field 2 / Element 84 is required, four characters, and includes Segment Type length. The independent `SEG151-R-005` chain remains a placeholder, and the full market-basket dataset format is in a separate external document. See [the review record](segment-151-incomm-market-basket-length-review-v1.json), [the symbolic draft](segment-151-incomm-market-basket-length-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

Four linked cases name market-basket data, but the bounded screen finds a Segment Type candidate of `100` with container identity unresolved and no exact Segment 151 Element 84 observation. The other four cases do not name the InComm OTC segment. Two approved payload copies for `TC-013094` are missing.

The draft is symbolic, `REVIEW_REQUIRED`, and unexecuted. It does not invent the external dataset format or computed length; the official Test baseline is unchanged.
