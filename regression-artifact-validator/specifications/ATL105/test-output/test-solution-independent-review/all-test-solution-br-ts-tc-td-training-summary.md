# All Independent Test Solution BR -> TS -> TC -> TD Training Evidence

This is an aggregate of existing Test Solution JSON evidence only. AI input is excluded. The aggregate is review-required and not execution-ready until every chain is independently validated.

| Artifact | Count |
|---|---:|
| BR | 725 |
| TS | 1343 |
| TC | 1424 |
| TD | 1416 |
| Unlinked candidates with resolved TC path | 21 |
| Synthetic review fixture drafts | 18 |

## Segment evidence

| Segment | BR | TS | TC | Linked TD | Unlinked TD candidates | Source files |
|---|---:|---:|---:|---:|---:|---:|
|100|170|164|194|186|22|63|
|101|13|7|11|4|0|2|
|103|12|7|8|3|0|1|
|104|3|2|2|0|0|1|
|108|9|5|6|3|0|1|
|111|4|3|3|3|0|2|
|113|7|3|3|2|0|1|
|120|0|0|0|0|0|1|
|CONTEXT_REVIEW_REQUIRED|0|0|1|1|0|1|
|DL3|9|9|18|18|0|1|
|DL4|8|8|18|18|0|1|
|DL5|7|7|14|14|0|1|
|DL6|7|7|14|14|0|1|
|DL7|6|6|12|12|0|1|
|DL8|5|5|10|10|0|1|
|UNSEGMENTED|465|1110|1110|1110|0|5|

Unlinked TD candidates are retained in provenance but excluded from canonical `testData[]` counts until linked to a test case.

Excluded partial/legacy files: **125**. They need normalization before they can contribute to the canonical chain.
