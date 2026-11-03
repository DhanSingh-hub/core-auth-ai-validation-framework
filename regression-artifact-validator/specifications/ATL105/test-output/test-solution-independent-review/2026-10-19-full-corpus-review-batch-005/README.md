# Full-Corpus Review Batch 005: Data Type Indicator

**Preliminary disposition:** The one-byte AN format claim is partly source-aligned; the extracted allowed-values claim is malformed and cannot be adjudicated as written.

ATL105 defines Element 24 as a fixed one-character AN field. Its list maps `#`, `!`, `:`, `@`, `$`, `\`, `^`, and `%` to DL1–DL8. The values `&` and `K` have key-load response meanings, including two contexts for `&`. The DL segment rules vary in framing: DL1–DL6 use an End-of-Data Indicator, while DL7/DL8 use Segment Length.

The full Test model has eight source-anchored, segment-specific marker/framing rules, all still draft. No direct Element 24 Test rule was found for the `&`/`K` key-response meanings. Both AI BRs have one scenario but zero cases. The malformed AI enum is retained verbatim in the [review record](data-type-indicator-format-values-review.json); its intended list is not reconstructed by guesswork.

See the [batch manifest](manifest.json). The Test baseline/package are unchanged; no semantic match, execution, or coverage credit is claimed.
