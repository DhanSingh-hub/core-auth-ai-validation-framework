# Full-Corpus Review Batch 010: Element 16 Card Type Total Count

**Status:** Twelve linked candidate cases were automatically inspected against ATL105's Element 16 domain and Segment 105/119 payload scopes. No semantic match or coverage credit is approved.

ATL105 defines Element 16 as numeric, fixed five digits, valid from 00001 through 99999. The AI claim “numeric and must not exceed 5 bytes” is only partial: it omits fixed width and the nonzero lower bound. The audit reads the AI BR case roster from the supplied review register, parses payloads and sidecars, binds count fields to their actual Segment Type, compares values to the source domain, and records Test-rule candidates without treating ranked leads or producer responses as oracles.

The current roster has 12 cases: four Segment 105 and eight Segment 119 observations. Automated results include three over-width, three nonnumeric, three empty required bucket counts, and three range violations. Duplicate copies and sidecar values are checked against their payloads; raw count values are not written to the report.

The Segment 105 candidate `SEG105-R-015` is a compound bucket/total requirement; the Segment 119 candidate is `SEG119-R-025`. Both current Test chains remain `REVIEW_REQUIRED` placeholders with no independent expected outcomes or execution. This review does not establish AI/Test semantic equivalence or promote the existing candidates.

Run from `regression-artifact-validator`:

```powershell
python scripts/review-element-16-card-type-total-count.py --self-test
python scripts/review-element-16-card-type-total-count.py
mvn.cmd '-Dmaven.compiler.useIncrementalCompilation=false' '-Dtest=TotalsRequestPayloadValidatorTest#validatesCardTypeTotalCountSourceBoundariesInSegments105And119' test
```

For future AI deliveries, use `--ai-br-id`, `--review-v3`, `--run-root`, and `--output` to select the new register, intake root, and report destination. The runner resolves case IDs from the chosen register rather than a hard-coded case list. Authorized Test-owner decisions, fixture readiness, and execution remain open; the official baseline is unchanged and coverage credit is zero.
